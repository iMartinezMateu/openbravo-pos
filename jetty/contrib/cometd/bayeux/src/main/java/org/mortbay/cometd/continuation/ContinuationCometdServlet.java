//========================================================================
//Copyright 2007 Mort Bay Consulting Pty. Ltd.
//------------------------------------------------------------------------
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at 
//http://www.apache.org/licenses/LICENSE-2.0
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
//========================================================================

package org.mortbay.cometd.continuation;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cometd.Bayeux;
import org.cometd.Extension;
import org.cometd.Message;
import org.mortbay.cometd.AbstractBayeux;
import org.mortbay.cometd.AbstractCometdServlet;
import org.mortbay.cometd.ClientImpl;
import org.mortbay.cometd.JSONTransport;
import org.mortbay.cometd.MessageImpl;
import org.mortbay.cometd.Transport;
import org.mortbay.util.ArrayQueue;
import org.mortbay.util.StringUtil;
import org.mortbay.util.ajax.Continuation;
import org.mortbay.util.ajax.ContinuationSupport;


public class ContinuationCometdServlet extends AbstractCometdServlet
{
    /* ------------------------------------------------------------ */
    @Override
    protected AbstractBayeux newBayeux()
    {
        return new ContinuationBayeux();
    }

    /* ------------------------------------------------------------ */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Look for an existing client and protect from context restarts
        Object clientObj=request.getAttribute(CLIENT_ATTR);
        ContinuationClient client=(clientObj instanceof ClientImpl)?(ContinuationClient)clientObj:null;
        Transport transport=null;
        boolean connect=false;
        int received=-1;
        
        // Have we seen this request before
        if (client!=null)
        {
            // yes - extract saved properties
            transport=(Transport)request.getAttribute(TRANSPORT_ATTR);
            transport.setResponse(response);
        }
        else
        {
            Message[] messages = getMessages(request);
            received=messages.length;

            /* check jsonp parameter */
            String jsonpParam=request.getParameter("jsonp");

            // Handle all messages
            try
            {
                for (Message message : messages)
                {
                    if (jsonpParam!=null)
                        message.put("jsonp",jsonpParam);

                    if (client==null)
                    {   
                        client=(ContinuationClient)_bayeux.getClient((String)message.get(AbstractBayeux.CLIENT_FIELD));

                        // If no client,  SHOULD be a handshake, so force a transport and handle
                        if (client==null)
                        {
                            // Setup a browser ID
                            String browser_id=browserId(request);
                            if (browser_id==null)
                                browser_id=newBrowserId(request,response);

                            if (transport==null)
                            {
                                transport=_bayeux.newTransport(client,message);
                                transport.setResponse(response);
                            }
                            _bayeux.handle(null,transport,message);
                            message=null;

                            continue;
                        }
                        else
                        {
                            String browser_id=browserId(request);
                            if (browser_id!=null && (client.getBrowserId()==null || !client.getBrowserId().equals(browser_id)))
                                client.setBrowserId(browser_id);

                            // resolve transport
                            if (transport==null)
                            {
                                transport=_bayeux.newTransport(client,message);
                                transport.setResponse(response);
                            }

                            // Tell client to hold messages as a response is likely to be sent.
                            if (!transport.resumePoll())
                                client.responsePending();
                        }
                    }

                    String channel=_bayeux.handle(client,transport,message);
                    connect|=AbstractBayeux.META_CONNECT.equals(channel);
                }
            }
            finally
            {
                if (transport!=null && client!=null && !transport.resumePoll())
                    client.responded();
                
                for (Message message : messages)
                    ((MessageImpl)message).decRef();
            }
        }

        Message pollReply=null;
        // Do we need to wait for messages
        if (transport!=null)
        {
            pollReply=transport.getPollReply();
            if (pollReply!=null)
            {
                if (_bayeux.isLogDebug())
                    _bayeux.logDebug("doPost: transport is polling");
                long timeout=client.getTimeout();
                if (timeout==0)
                    timeout=_bayeux.getTimeout();

                Continuation continuation=ContinuationSupport.getContinuation(request,client);

                // Get messages or wait
                synchronized (client)
                {
                    if (!client.hasMessages() && !continuation.isPending()&& received<=1)
                    {
                        // save state and suspend
                        ((ContinuationClient)client).setContinuation(continuation);
                        request.setAttribute(CLIENT_ATTR,client);
                        request.setAttribute(TRANSPORT_ATTR,transport);
                        continuation.suspend(timeout);
                    }
                    
                    if (!continuation.isPending())
                        client.access();

                    continuation.reset();
                }

                ((ContinuationClient)client).setContinuation(null);
                transport.setPollReply(null);

                for (Extension e:_bayeux.getExtensions())
                    pollReply=e.sendMeta(pollReply);
            }
            else if (client!=null)
            {
                client.access();
            }
        }

        // Send any messages.
        if (client!=null) 
        { 
            synchronized(client)
            {
                client.doDeliverListeners();
                ArrayQueue<Message> messages= (ArrayQueue)client.getQueue();
                int size=messages.size();
                boolean flushed=false;
                
                try
                {
                    if (pollReply!=null)
                    {
                        // can we bypass response generation?
                        if (_refsThreshold>0 && size==1 && transport instanceof JSONTransport)
                        {
                            MessageImpl message = (MessageImpl)messages.peek();
                            
                            // is there a response already prepared
                            ByteBuffer buffer = message.getBuffer();
                            if (buffer!=null)
                            {
                                request.setAttribute("org.mortbay.jetty.ResponseBuffer",buffer);
                                ((MessageImpl)message).decRef();
                                flushed=true;
                            }
                            else if (message.getRefs()>=_refsThreshold)
                            {                                
                                byte[] contentBytes = ("[{\""+Bayeux.SUCCESSFUL_FIELD+"\":true,\""+
                                        Bayeux.CHANNEL_FIELD+"\":\""+Bayeux.META_CONNECT+"\"},"+
                                        message.getJSON()+"]").getBytes(StringUtil.__UTF8);
                                int contentLength = contentBytes.length;

                                String headerString = "HTTP/1.1 200 OK\r\n"+
                                "Content-Type: text/json; charset=utf-8\r\n" +
                                "Content-Length: " + contentLength + "\r\n" +
                                "\r\n";

                                byte[] headerBytes = headerString.getBytes(StringUtil.__UTF8);

                                buffer = ByteBuffer.allocateDirect(headerBytes.length+contentLength);
                                buffer.put(headerBytes);
                                buffer.put(contentBytes);
                                buffer.flip();

                                message.setBuffer(buffer);
                                request.setAttribute("org.mortbay.jetty.ResponseBuffer",buffer);
                                ((MessageImpl)message).decRef();
                                flushed=true;
                            }
                            else
                                transport.send(pollReply);
                        }
                        else
                            transport.send(pollReply);                        
                    }
                    
                    if (!flushed)
                    {
                        Message message = null;
                        for (int i = 0;i<size;i++)
                        {
                            message=messages.getUnsafe(i);
                            transport.send(message);
                        }

                        transport.complete();
                        flushed=true;
                    }
                }
                finally
                {
                    if (flushed)
                        messages.clear();
                }
            }
            
            if (transport.resumePoll())
                client.resume();
        }
        else if (transport!=null)
        {
            transport.complete();
        }   
    }
}
