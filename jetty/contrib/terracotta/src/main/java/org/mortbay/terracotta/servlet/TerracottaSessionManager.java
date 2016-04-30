// ========================================================================
// Copyright 2004-2008 Mort Bay Consulting Pty. Ltd.
// ------------------------------------------------------------------------
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ========================================================================

package org.mortbay.terracotta.servlet;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tc.object.bytecode.Manageable;
import com.tc.object.bytecode.Manager;
import com.tc.object.bytecode.ManagerUtil;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.handler.ContextHandler;
import org.mortbay.jetty.servlet.AbstractSessionManager;
import org.mortbay.log.Log;

/**
 * A specialized SessionManager to be used with <a href="http://www.terracotta.org">Terracotta</a>.
 *
 * @see TerracottaSessionIdManager
 */
public class TerracottaSessionManager extends AbstractSessionManager implements Runnable
{
    /**
     * The local cache of session objects.
     */
    private Map<String, Session> _sessions;
    /**
     * The distributed shared SessionData map.
     * Putting objects into the map result in the objects being sent to Terracotta, and any change
     * to the objects are also replicated, recursively.
     * Getting objects from the map result in the objects being fetched from Terracotta.
     * The locking of this object in the cluster is automatically handled by Terracotta.
     */
    private Map<String, SessionData> _sessionDatas;
    /**
     * The distributed shared session expirations map, needed for scavenging.
     * In particular it supports removal of sessions that have been orphaned by nodeA
     * (for example because it crashed) by virtue of scavenging performed by nodeB.
     */
    private Map<String, MutableLong> _sessionExpirations;

    private long _scavengePeriodMs = 30000;
    private ScheduledExecutorService _scheduler;
    private ScheduledFuture<?> _scavenger;

    public void doStart() throws Exception
    {
        super.doStart();

        _sessions = Collections.synchronizedMap(new HashMap<String, Session>());
        _sessionDatas = newSharedMap("sessionData:" + canonicalize(_context.getContextPath()) + ":" + virtualHostFrom(_context));
        _sessionExpirations = newSharedMap("sessionExpirations:" + canonicalize(_context.getContextPath()) + ":" + virtualHostFrom(_context));
        _scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduleScavenging();
    }

    private Map newSharedMap(String name)
    {
        // We want to partition the session data among contexts, so we need to have different roots for
        // different contexts, and each root must have a different name, since roots with the same name are shared.
        Lock.lock(name);
        try
        {
            Map result = (Map)ManagerUtil.lookupOrCreateRootNoDepth(name, new Hashtable());
            ((Manageable)result).__tc_managed().disableAutoLocking();
            return result;
        }
        finally
        {
            Lock.unlock(name);
        }
    }

    private void scheduleScavenging()
    {
        if (_scavenger != null)
        {
            _scavenger.cancel(true);
            _scavenger = null;
        }
        long scavengePeriod = getScavengePeriodMs();
        if (scavengePeriod > 0 && _scheduler != null)
            _scavenger = _scheduler.scheduleWithFixedDelay(this, scavengePeriod, scavengePeriod, TimeUnit.MILLISECONDS);
    }

    public void doStop() throws Exception
    {
        if (_scavenger != null) _scavenger.cancel(true);
        if (_scheduler != null) _scheduler.shutdownNow();
        super.doStop();
    }

    public void run()
    {
        scavenge();
    }

    public void enter(Request request)
    {
        String requestedSessionId = request.getRequestedSessionId();
        if (requestedSessionId == null) return;

        enter(getIdManager().getClusterId(requestedSessionId));
    }

    protected void enter(String clusterId)
    {
        Lock.lock(newLockId(clusterId));
    }

    public void exit(Request request)
    {
        String clusterId = null;
        String requestedSessionId = request.getRequestedSessionId();
        if (requestedSessionId == null)
        {
            HttpSession session = request.getSession(false);
            if (session != null) clusterId = getIdManager().getClusterId(session.getId());
        }
        else
        {
            clusterId = getIdManager().getClusterId(requestedSessionId);
        }

        if (clusterId != null) exit(clusterId);
    }

    protected void exit(String clusterId)
    {
        Lock.unlock(newLockId(clusterId));
    }

    @Override
    protected void addSession(AbstractSessionManager.Session session, boolean created)
    {
        /**
         * SESSION LOCKING
         * This is an entry point for session locking.
         * We enter here when a new session is requested via request.getSession(true).
         */
        if (created) enter(((Session)session).getClusterId());
        super.addSession(session, created);
    }

    protected void addSession(AbstractSessionManager.Session session)
    {
        /**
         * SESSION LOCKING
         * When this method is called, we already hold the session lock.
         * See {@link #addSession(AbstractSessionManager.Session, boolean)}
         */
        String clusterId = getClusterId(session);
        Session tcSession = (Session)session;
        SessionData sessionData = tcSession.getSessionData();
        _sessionExpirations.put(clusterId, sessionData._expiration);
        _sessionDatas.put(clusterId, sessionData);
        _sessions.put(clusterId, tcSession);
        Log.debug("Added session {} with id {}", tcSession, clusterId);
    }

    @Override
    public Cookie access(HttpSession session, boolean secure)
    {
        Cookie cookie = super.access(session, secure);
        Log.debug("Accessed session {} with id {}", session, session.getId());
        return cookie;
    }

    @Override
    public void complete(HttpSession session)
    {
        super.complete(session);
        Log.debug("Completed session {} with id {}", session, session.getId());
    }

    protected void removeSession(String clusterId)
    {
        /**
         * SESSION LOCKING
         * When this method is called, we already hold the session lock.
         * Either the scavenger acquired it, or the user invalidated
         * the existing session and thus {@link #enter(String)} was called.
         */

        // Remove locally cached session
        Session session = _sessions.remove(clusterId);
        Log.debug("Removed session {} with id {}", session, clusterId);

        // It may happen that one node removes its expired session data,
        // so that when this node does the same, the session data is already gone
        SessionData sessionData = _sessionDatas.remove(clusterId);
        Log.debug("Removed session data {} with id {}", sessionData, clusterId);

        // Remove the expiration entry used in scavenging
        _sessionExpirations.remove(clusterId);
    }

    public void setScavengePeriodMs(long ms)
    {
        this._scavengePeriodMs = ms;
        scheduleScavenging();
    }

    public long getScavengePeriodMs()
    {
        return _scavengePeriodMs;
    }

    public AbstractSessionManager.Session getSession(String clusterId)
    {
        Session result = null;

        /**
         * SESSION LOCKING
         * This is an entry point for session locking.
         * We lookup the session given the id, and if it exist we hold the lock.
         * We unlock on end of method, since this method can be called outside
         * an {@link #enter(String)}/{@link #exit(String)} pair.
         */
        enter(clusterId);
        try
        {
            // Need to synchronize because we use a get-then-put that must be atomic
            // on the local session cache
            synchronized (_sessions)
            {
                result = _sessions.get(clusterId);
                if (result == null)
                {
                    Log.debug("Session with id {} --> local cache miss", clusterId);

                    // Lookup the distributed shared sessionData object.
                    // This will migrate the session data to this node from the Terracotta server
                    // We have not grabbed the distributed lock associated with this session yet,
                    // so another node can migrate the session data as well. This is no problem,
                    // since just after this method returns the distributed lock will be grabbed by
                    // one node, the session data will be changed and the lock released.
                    // The second node contending for the distributed lock will then acquire it,
                    // and the session data information will be migrated lazily by Terracotta means.
                    // We are only interested in having a SessionData reference locally.
                    Log.debug("Distributed session data with id {} --> lookup", clusterId);
                    SessionData sessionData = _sessionDatas.get(clusterId);
                    if (sessionData == null)
                    {
                        Log.debug("Distributed session data with id {} --> not found", clusterId);
                    }
                    else
                    {
                        Log.debug("Distributed session data with id {} --> found", clusterId);
                        // Wrap the migrated session data and cache the Session object
                        result = new Session(sessionData);
                        _sessions.put(clusterId, result);
                    }
                }
                else
                {
                    Log.debug("Session with id {} --> local cache hit", clusterId);
                    if (!_sessionExpirations.containsKey(clusterId))
                    {
                        // A session is present in the local cache, but it has been expired
                        // or invalidated on another node, perform local clean up.
                        _sessions.remove(clusterId);
                        result = null;
                        Log.debug("Session with id {} --> local cache stale");
                    }
                }
            }
        }
        finally
        {
            /**
             * SESSION LOCKING
             */
            exit(clusterId);
        }
        return result;
    }

    protected String newLockId(String clusterId)
    {
        StringBuilder builder = new StringBuilder(clusterId);
        builder.append(":").append(canonicalize(_context.getContextPath()));
        builder.append(":").append(virtualHostFrom(_context));
        return builder.toString();
    }

    // TODO: This method is not needed, only used for testing
    public Map getSessionMap()
    {
        return Collections.unmodifiableMap(_sessions);
    }

    // TODO: rename to getSessionsCount()
    // TODO: also, not used if not by superclass for unused statistics data
    public int getSessions()
    {
        return _sessions.size();
    }

    protected Session newSession(HttpServletRequest request)
    {
        return new Session(request);
    }

    protected void invalidateSessions()
    {
        // Do nothing.
        // We don't want to remove and invalidate all the sessions,
        // because this method is called from doStop(), and just
        // because this context is stopping does not mean that we
        // should remove the session from any other node (remember
        // the session map is shared)
    }

    private void scavenge()
    {
        Thread thread = Thread.currentThread();
        ClassLoader old_loader = thread.getContextClassLoader();
        if (_loader != null) thread.setContextClassLoader(_loader);
        try
        {
            long now = System.currentTimeMillis();
            Log.debug(this + " scavenging at {}, scavenge period {}", now, getScavengePeriodMs());

            // Detect the candidates that may have expired already, checking the estimated expiration time.
            Set<String> candidates = new HashSet<String>();
            String lockId = "scavenge:" + canonicalize(_context.getContextPath()) + ":" + virtualHostFrom(_context);
            Lock.lock(lockId);
            try
            {
                for (Map.Entry<String, MutableLong> entry : _sessionExpirations.entrySet())
                {
                    String sessionId = entry.getKey();
                    long expirationTime = entry.getValue().value;
                    Log.debug("Estimated expiration time {} for session {}", expirationTime, sessionId);
                    if (expirationTime > 0 && expirationTime < now) candidates.add(sessionId);
                }
            }
            finally
            {
                Lock.unlock(lockId);
            }
            Log.debug("Scavenging detected {} candidate sessions to expire", candidates.size());

            // Now validate that the candidates that do expire are really expired,
            // grabbing the session lock for each candidate
            for (String sessionId : candidates)
            {
                Session candidate = (Session)getSession(sessionId);
                // Here we grab the lock to avoid anyone else interfering
                enter(sessionId);
                try
                {
                    long maxInactiveTime = candidate.getMaxIdlePeriodMs();
                    // Exclude sessions that never expire
                    if (maxInactiveTime > 0)
                    {
                        // The lastAccessedTime is fetched from Terracotta, so we're sure it is up-to-date.
                        long lastAccessedTime = candidate.getLastAccessedTime();
                        // Since we write the shared lastAccessedTime every scavenge period,
                        // take that in account before considering the session expired
                        long expirationTime = lastAccessedTime + maxInactiveTime + getScavengePeriodMs();
                        if (expirationTime < now)
                        {
                            Log.debug("Scavenging expired session {}, expirationTime {}", candidate.getClusterId(), expirationTime);
                            // Calling timeout() result in calling removeSession(), that will clean the data structures
                            candidate.timeout();
                        }
                        else
                        {
                            Log.debug("Scavenging skipping candidate session {}, expirationTime {}", candidate.getClusterId(), expirationTime);
                        }
                    }
                }
                finally
                {
                    exit(sessionId);
                }
            }

            int sessionCount = getSessions();
            if (sessionCount < _minSessions) _minSessions = sessionCount;
            if (sessionCount > _maxSessions) _maxSessions = sessionCount;
        }
        finally
        {
            thread.setContextClassLoader(old_loader);
        }
    }

    private String canonicalize(String contextPath)
    {
        if (contextPath == null) return "";
        return contextPath.replace('/', '_').replace('.', '_').replace('\\', '_');
    }

    private String virtualHostFrom(ContextHandler.SContext context)
    {
        String result = "0.0.0.0";
        if (context == null) return result;

        String[] vhosts = context.getContextHandler().getVirtualHosts();
        if (vhosts == null || vhosts.length == 0 || vhosts[0] == null) return result;

        return vhosts[0];
    }

    class Session extends AbstractSessionManager.Session
    {
        private static final long serialVersionUID = -2134521374206116367L;

        private final SessionData _sessionData;
        private long _lastUpdate;

        protected Session(HttpServletRequest request)
        {
            super(request);
            _sessionData = new SessionData(getClusterId(), _maxIdleMs);
            _lastAccessed = _sessionData.getCreationTime();
        }

        protected Session(SessionData sd)
        {
            super(sd.getCreationTime(), sd.getId());
            _sessionData = sd;
            _lastAccessed = getLastAccessedTime();
            initValues();
        }

        public SessionData getSessionData()
        {
            return _sessionData;
        }

        @Override
        public long getCookieSetTime()
        {
            return _sessionData.getCookieTime();
        }

        @Override
        protected void cookieSet()
        {
            _sessionData.setCookieTime(getLastAccessedTime());
        }

        @Override
        public long getLastAccessedTime()
        {
            if (!isValid()) throw new IllegalStateException();
            return _sessionData.getPreviousAccessTime();
        }

        @Override
        public long getCreationTime() throws IllegalStateException
        {
            if (!isValid()) throw new IllegalStateException();
            return _sessionData.getCreationTime();
        }

        // Overridden for visibility
        @Override
        protected String getClusterId()
        {
            return super.getClusterId();
        }

        protected Map newAttributeMap()
        {
            // It is important to never return a new attribute map here (as other Session implementations do),
            // but always return the shared attributes map, so that a new session created on a different cluster
            // node is immediately filled with the session data from Terracotta.
            return _sessionData.getAttributeMap();
        }

        @Override
        protected void access(long time)
        {
            // The local previous access time is always updated via the super.access() call.
            // If the requests are steady and within the scavenge period, the distributed shared access times
            // are never updated. If only one node gets hits, other nodes reach the expiration time and the
            // scavenging on other nodes will believe the session is expired, since the distributed shared
            // access times have never been updated.
            // Therefore we need to update the distributed shared access times once in a while, no matter what.
            long previousAccessTime = getPreviousAccessTime();
            if (time - previousAccessTime > getScavengePeriodMs())
            {
                Log.debug("Out-of-date update of distributed access times: previous {} - current {}", previousAccessTime, time);
                updateAccessTimes(time);
            }
            else
            {
                if (time - _lastUpdate > getScavengePeriodMs())
                {
                    Log.debug("Periodic update of distributed access times: last update {} - current {}", _lastUpdate, time);
                    updateAccessTimes(time);
                }
                else
                {
                    Log.debug("Skipping update of distributed access times: previous {} - current {}", previousAccessTime, time);
                }
            }
            super.access(time);
        }

        /**
         * Updates the shared distributed access times that need to be updated
         *
         * @param time the update value
         */
        private void updateAccessTimes(long time)
        {
            _sessionData.setPreviousAccessTime(_accessed);
            if (getMaxIdlePeriodMs() > 0) _sessionData.setExpirationTime(time + getMaxIdlePeriodMs());
            _lastUpdate = time;
        }

        // Overridden for visibility
        @Override
        protected void timeout()
        {
            super.timeout();
            Log.debug("Timed out session {} with id {}", this, getClusterId());
        }

        @Override
        public void invalidate()
        {
            super.invalidate();
            Log.debug("Invalidated session {} with id {}", this, getClusterId());
        }

        private long getMaxIdlePeriodMs()
        {
            return _maxIdleMs;
        }

        private long getPreviousAccessTime()
        {
            return super.getLastAccessedTime();
        }
    }

    /**
     * The session data that is distributed to cluster nodes via Terracotta.
     */
    public static class SessionData
    {
        private final String _id;
        private final Map _attributes;
        private final long _creation;
        private final MutableLong _expiration;
        private long _previousAccess;
        private long _cookieTime;

        public SessionData(String sessionId, long maxIdleMs)
        {
            _id = sessionId;
            // Don't need synchronization, as we grab a distributed session id lock
            // when this map is accessed.
            _attributes = new HashMap();
            _creation = System.currentTimeMillis();
            _expiration = new MutableLong();
            // Set expiration time to negative value if the session never expires
            _expiration.value = maxIdleMs > 0 ? _creation + maxIdleMs : -1L;
        }

        public String getId()
        {
            return _id;
        }

        protected Map getAttributeMap()
        {
            return _attributes;
        }

        public long getCreationTime()
        {
            return _creation;
        }

        public long getExpirationTime()
        {
            return _expiration.value;
        }

        public void setExpirationTime(long time)
        {
            _expiration.value = time;
        }

        public long getCookieTime()
        {
            return _cookieTime;
        }

        public void setCookieTime(long time)
        {
            _cookieTime = time;
        }

        public long getPreviousAccessTime()
        {
            return _previousAccess;
        }

        public void setPreviousAccessTime(long time)
        {
            _previousAccess = time;
        }
    }

    private static class Lock
    {
        private static final ThreadLocal<Map<String, Integer>> nestings = new ThreadLocal<Map<String, Integer>>()
        {
            @Override
            protected Map<String, Integer> initialValue()
            {
                return new HashMap<String, Integer>();
            }
        };

        private Lock()
        {
        }

        public static void lock(String lockId)
        {
            Integer nestingLevel = nestings.get().get(lockId);
            if (nestingLevel == null) nestingLevel = 0;
            if (nestingLevel < 0)
                throw new AssertionError("Lock(" + lockId + ") nest level = " + nestingLevel + ", thread " + Thread.currentThread() + ": " + nestings.get());
            if (nestingLevel == 0)
            {
                ManagerUtil.beginLock(lockId, Manager.LOCK_TYPE_WRITE);
                Log.debug("Lock({}) acquired by thread {}", lockId, Thread.currentThread().getName());
            }
            nestings.get().put(lockId, nestingLevel + 1);
            Log.debug("Lock({}) nestings {}", lockId, nestings.get());
        }

        public static void unlock(String lockId)
        {
            Integer nestingLevel = nestings.get().get(lockId);
            if (nestingLevel == null || nestingLevel < 1)
                throw new AssertionError("Lock(" + lockId + ") nest level = " + nestingLevel + ", thread " + Thread.currentThread() + ": " + nestings.get());
            if (nestingLevel == 1)
            {
                ManagerUtil.commitLock(lockId);
                Log.debug("Lock({}) released by thread {}", lockId, Thread.currentThread().getName());
                nestings.get().remove(lockId);
            }
            else
            {
                nestings.get().put(lockId, nestingLevel - 1);
            }
            Log.debug("Lock({}) nestings {}", lockId, nestings.get());
        }
    }

    private static class MutableLong
    {
        private long value;
    }
}
