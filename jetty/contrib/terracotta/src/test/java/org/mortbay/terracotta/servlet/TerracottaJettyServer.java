// ========================================================================
// Copyright 2004-2005 Mort Bay Consulting Pty. Ltd.
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

import java.util.concurrent.TimeUnit;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.SessionIdManager;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.SessionHandler;

/**
 * @version $Revision: 1256 $ $Date: 2008-10-18 01:50:04 +1100 (Sat, 18 Oct 2008) $
 */
public class TerracottaJettyServer
{
    private final Server server;
    private final int maxInactivePeriod;
    private final int scavengePeriod;
    private final ContextHandlerCollection contexts;
    private SessionIdManager sessionIdManager;

    public TerracottaJettyServer(int port)
    {
        this(port, 30, 10);
    }

    public TerracottaJettyServer(int port, int maxInactivePeriod, int scavengePeriod)
    {
        this.server = new Server(port);
        this.maxInactivePeriod = maxInactivePeriod;
        this.scavengePeriod = scavengePeriod;
        this.contexts = new ContextHandlerCollection();
        this.sessionIdManager = new TerracottaSessionIdManager(server);
    }

    public void start() throws Exception
    {
        // server -> contexts collection -> context handler -> session handler -> servlet handler
        server.setHandler(contexts);
        server.start();
    }

    public Context addContext(String contextPath)
    {
        Context context = new Context(contexts, contextPath);

        TerracottaSessionManager sessionManager = new TerracottaSessionManager();
        sessionManager.setIdManager(sessionIdManager);
        sessionManager.setMaxInactiveInterval(maxInactivePeriod);
        sessionManager.setScavengePeriodMs(TimeUnit.SECONDS.toMillis(scavengePeriod));

        SessionHandler sessionHandler = new TerracottaSessionHandler(sessionManager);
        sessionManager.setSessionHandler(sessionHandler);
        context.setSessionHandler(sessionHandler);

        return context;
    }

    public void stop() throws Exception
    {
        server.stop();
    }
}
