// ========================================================================
// Copyright 2006 Mort Bay Consulting Pty. Ltd.
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

package org.mortbay.cometd;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.cometd.Channel;
import org.cometd.ChannelListener;
import org.cometd.Client;
import org.cometd.DataFilter;
import org.cometd.Message;
import org.cometd.SubscriptionListener;
import org.mortbay.log.Log;
import org.mortbay.util.LazyList;


/* ------------------------------------------------------------ */
/** A Bayuex Channel
 * 
 * @author gregw
 *
 */
public class ChannelImpl implements Channel
{
    protected AbstractBayeux _bayeux;
    private ClientImpl[] _subscribers=new ClientImpl[0]; // copy on write
    private DataFilter[] _dataFilters=new DataFilter[0]; // copy on write
    private SubscriptionListener[] _subscriptionListeners=new SubscriptionListener[0]; // copy on write
    private ChannelId _id;
    private ConcurrentMap<String,ChannelImpl> _children = new ConcurrentHashMap<String, ChannelImpl>();
    private ChannelImpl _wild;
    private ChannelImpl _wildWild;
    private boolean _persistent;
    private int _split;

    /* ------------------------------------------------------------ */
    ChannelImpl(String id,AbstractBayeux bayeux)
    {
        _id=new ChannelId(id);
        _bayeux=bayeux;
    }

    /* ------------------------------------------------------------ */
    public void addChild(ChannelImpl channel)
    {
        ChannelId child=channel.getChannelId();
        if (!_id.isParentOf(child))
        {
            throw new IllegalArgumentException(_id+" not parent of "+child);
        }
        
        String next = child.getSegment(_id.depth());

        if ((child.depth()-_id.depth())==1)
        {
            // add the channel to this channels
            ChannelImpl old = _children.putIfAbsent(next,channel);

            if (old!=null)
                throw new IllegalArgumentException("Already Exists");

            if (ChannelId.WILD.equals(next))
                _wild=channel;
            else if (ChannelId.WILDWILD.equals(next))
                _wildWild=channel;
                
        }
        else
        {
            ChannelImpl branch=_children.get(next);
                branch=(ChannelImpl)_bayeux.getChannel((_id.depth()==0?"/":(_id.toString()+"/"))+next,true);
            
            branch.addChild(channel);
        }

        _bayeux.addChannel(channel);
    }
    
    /* ------------------------------------------------------------ */
    /**
     * @param filter
     */
    public void addDataFilter(DataFilter filter)
    {
        synchronized(this)
        {
            _dataFilters=(DataFilter[])LazyList.addToArray(_dataFilters,filter,null);
        }
    }

    /* ------------------------------------------------------------ */
    /* ------------------------------------------------------------ */
    /**
     * @return
     */
    public ChannelId getChannelId()
    {
        return _id;
    }
    
    /* ------------------------------------------------------------ */
    public ChannelImpl getChild(ChannelId id)
    {
        String next=id.getSegment(_id.depth());
        if (next==null)
            return null;
        
        ChannelImpl channel = _children.get(next);
        
        if (channel==null || channel.getChannelId().depth()==id.depth())
        {
            return channel;
        }
        return channel.getChild(id);
    }

    /* ------------------------------------------------------------ */
     public void getChannels(List<Channel> list)
     {
         list.add(this);
         for (ChannelImpl channel: _children.values())
             channel.getChannels(list);
     }

     /* ------------------------------------------------------------ */
     public int getChannelCount()
     {
         int count = 1;
         
         for(ChannelImpl channel: _children.values())
             count += channel.getChannelCount();
         
         return count;
     }
     
    /* ------------------------------------------------------------ */
    /**
     * @return
     */
    public String getId()
    {
        return _id.toString();
    }

    
    /* ------------------------------------------------------------ */
    public boolean isPersistent()
    {
        return _persistent;
    }

    /* ------------------------------------------------------------ */
    public void publish(Client fromClient, Object data, String msgId)
    {
        _bayeux.doPublish(getChannelId(),fromClient,data,msgId);   
    }
    
    /* ------------------------------------------------------------ */
    public boolean remove()
    {
        return _bayeux.removeChannel(this);
    }
    
    /* ------------------------------------------------------------ */
    public boolean doRemove(ChannelImpl channel)
    {
        ChannelId channelId = channel.getChannelId();
        String key = channelId.getSegment(channelId.depth()-1);
        if (_children.containsKey(key))
        {
            ChannelImpl child = _children.get(key);
            
            synchronized (this)
            {
                synchronized (child)
                {
                    if (!child.isPersistent() && child.getSubscriberCount()==0 && child.getChannelCount()==1)
                    {
                        _children.remove(key);
                        return true;
                    }
                    else
                        return false;
                }
                
            }
        }
        else
        {
            for (ChannelImpl child : _children.values())
            {
                if (child.doRemove(channel))
                    return true;
            }
        }
        return false;
    }
    
    /* ------------------------------------------------------------ */
    /* ------------------------------------------------------------ */
    /**
     * @param filter
     */
    public DataFilter removeDataFilter(DataFilter filter)
    {
        synchronized(this)
        {
            _dataFilters=(DataFilter[])LazyList.removeFromArray(_dataFilters,filter);
            return filter;
        }
    }

    /* ------------------------------------------------------------ */
    public void setPersistent(boolean persistent)
    {
        _persistent=persistent;
    }

    /* ------------------------------------------------------------ */
    /**
     * @param client
     */
    public void subscribe(Client client)
    {
        if (!(client instanceof ClientImpl))
            throw new IllegalArgumentException("Client instance not obtained from Bayeux.newClient()");
        
        synchronized (this)
        {
            for (ClientImpl c : _subscribers)
            {
                if (client.equals(c))
                    return;
            }
            _subscribers=(ClientImpl[])LazyList.addToArray(_subscribers,client,null);
            
            for (SubscriptionListener l : _subscriptionListeners)
                l.subscribed(client, this);
        }
        
        ((ClientImpl)client).addSubscription(this);
    }

    /* ------------------------------------------------------------ */
    @Override
    public String toString()
    {
        return _id.toString();
    }

    /* ------------------------------------------------------------ */
    /**
     * @param client
     */
    public void unsubscribe(Client client)
    {
        if (!(client instanceof ClientImpl))
            throw new IllegalArgumentException("Client instance not obtained from Bayeux.newClient()");
        ((ClientImpl)client).removeSubscription(this);
        synchronized(this)
        {
            _subscribers=(ClientImpl[])LazyList.removeFromArray(_subscribers,client);
            
            for (SubscriptionListener l : _subscriptionListeners)
                l.unsubscribed(client,this);
            
            if (!_persistent && _subscribers.length==0 && _children.size()==0)
                remove();
        }
    }

    /* ------------------------------------------------------------ */
    protected void doDelivery(ChannelId to, Client from, Message msg)
    {
        int tail = to.depth()-_id.depth();
        
        Object data = msg.getData();
        Object old = data;
        
        DataFilter[] filters=null;
        
        try
        {
            switch(tail)
            {
                case 0:      
                {
                    synchronized(this)
                    {
                        filters=_dataFilters;
                    }
                    for (DataFilter filter: filters)
                        data=filter.filter(from,this,data);
                }
                break;

                case 1:
                    if (_wild!=null)  
                    {
                        synchronized(_wild)
                        {
                            filters=_wild._dataFilters;
                        }
                        for (DataFilter filter: filters)
                            data=filter.filter(from,this,data);
                    }

                default:
                    if (_wildWild!=null)  
                    {
                        synchronized(_wildWild)
                        {
                            filters=_wildWild._dataFilters;
                        }
                        for (DataFilter filter: filters)
                        {
                            data=filter.filter(from,this,data);
                        }
                    }
            }
        }
        catch (IllegalStateException e)
        {
            Log.debug(e);
            return;
        }
        if (data!=old)
            msg.put(AbstractBayeux.DATA_FIELD,data);
        
        ClientImpl[] subscribers;

        switch(tail)
        {
            case 0:
                synchronized (this)
                {
                    subscribers=_subscribers;
                    _split++;
                }
                if (subscribers.length>0)
                {
                    // fair delivery 
                    int split=_split%_subscribers.length;
                    for (int i=split;i<subscribers.length;i++)
                        subscribers[i].doDelivery(from,msg);
                    for (int i=0;i<split;i++)
                        subscribers[i].doDelivery(from,msg);
                }                
                break;

            case 1:
                if (_wild!=null)
                {
                    synchronized (_wild)
                    {
                        subscribers=_wild._subscribers;
                    }
                    for (ClientImpl client: subscribers)
                    {
                        client.doDelivery(from,msg);
                    }
                }

            default:
            {
                if (_wildWild!=null)
                {
                    synchronized (_wildWild)
                    {
                        subscribers=_wildWild._subscribers;
                    }
                    for (ClientImpl client: subscribers)
                    {
                        client.doDelivery(from,msg);
                    }
                }
                String next = to.getSegment(_id.depth());
                ChannelImpl channel = _children.get(next);
                if (channel!=null)
                    channel.doDelivery(to,from,msg);
            }
        }
    }

    /* ------------------------------------------------------------ */
    public Collection<Client> getSubscribers()
    {
        synchronized(this)
        {
            return Arrays.asList((Client[])_subscribers);
        }
    }

    /* ------------------------------------------------------------ */
    public int getSubscriberCount()
    {
        synchronized(this)
        {
            return _subscribers.length;
        }
    }


    /* ------------------------------------------------------------ */
    /* (non-Javadoc)
     * @see dojox.cometd.Channel#getFilters()
     */
    public Collection<DataFilter> getDataFilters()
    {
        synchronized(this)
        {
            return Arrays.asList(_dataFilters);
        }
    }

    /* ------------------------------------------------------------ */
    public void addListener(ChannelListener listener)
    {
        synchronized(this)
        {
            if (listener instanceof SubscriptionListener)
                _subscriptionListeners=(SubscriptionListener[])LazyList.addToArray(_subscriptionListeners,listener,null);
        }
    }
    
}
