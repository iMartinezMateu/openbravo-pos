// ========================================================================
// Copyright 2007 Mort Bay Consulting Pty. Ltd.
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
//========================================================================

package org.cometd.demo;


import java.util.Set;

import org.mortbay.cometd.AbstractBayeux;
import org.mortbay.cometd.ClientImpl;
import org.mortbay.cometd.continuation.ContinuationCometdServlet;
import org.mortbay.cometd.ext.TimesyncExtension;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.handler.MovedContextHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.security.SslSocketConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.resource.Resource;
import org.mortbay.resource.ResourceCollection;

import org.cometd.Message;


/* ------------------------------------------------------------ */
/** Main class for cometd demo.
 * 
 * This is of use when running demo in a terracotta cluster
 * 
 * @author gregw
 *
 */
public class CometdDemo
{
    private static int _testHandshakeFailure;
    
    /* ------------------------------------------------------------ */
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        int port = args.length==0?8080:Integer.parseInt(args[0]);
     
        String base="../../..";
        
        // Manually contruct context to avoid hassles with webapp classloaders for now.
        Server server = new Server();
        SelectChannelConnector connector=new SelectChannelConnector();
        connector.setPort(port);
        server.addConnector(connector);
        SocketConnector bconnector=new SocketConnector();
        bconnector.setPort(port+1);
        server.addConnector(bconnector);
        
        SslSocketConnector connector2=new SslSocketConnector();
        connector2.setPort(port-80+443);
        connector2.setKeystore(base+"/etc/keystore");
        connector2.setPassword("OBF:1vny1zlo1x8e1vnw1vn61x8g1zlu1vn4");
        connector2.setKeyPassword("OBF:1u2u1wml1z7s1z7a1wnl1u2g");
        connector2.setTruststore(base+"/etc/keystore");
        connector2.setTrustPassword("OBF:1vny1zlo1x8e1vnw1vn61x8g1zlu1vn4");
        server.addConnector(connector2);  

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        server.setHandler(contexts);
        
        MovedContextHandler moved = new MovedContextHandler(contexts,"/","/cometd");
        moved.setDiscardPathInfo(true);
        
        Context context = new Context(contexts,"/cometd",Context.NO_SECURITY|Context.SESSIONS);
        
        context.setBaseResource(new ResourceCollection(new Resource[]
        {
            Resource.newResource("./src/main/webapp/"),
            Resource.newResource("./target/cometd-demo-6.1.12/"),
        }));
        
        // Demo bayeux session manager
        // context.getSessionHandler().setSessionManager(new BayeuxSessionManager());
        // context.addServlet(com.acme.SessionDump.class,"/session");
        // context.addServlet(com.acme.Dump.class,"/dump");
        
        // Cometd servlet
        ContinuationCometdServlet cometd_servlet=new ContinuationCometdServlet();
        ServletHolder cometd_holder = new ServletHolder(cometd_servlet);
        cometd_holder.setInitParameter("filters","/WEB-INF/filters.json");
        cometd_holder.setInitParameter("timeout","180000");
        cometd_holder.setInitParameter("interval","0");
        cometd_holder.setInitParameter("maxInterval","10000");
        cometd_holder.setInitParameter("multiFrameInterval","1500");
        cometd_holder.setInitParameter("directDeliver","true");
        cometd_holder.setInitParameter("logLevel","1");
        
        context.addServlet(cometd_holder, "/cometd/*");
        context.addServlet("org.mortbay.jetty.servlet.DefaultServlet", "/");
        context.addEventListener(new BayeuxServicesListener());
        
        server.start();
        
        AbstractBayeux bayeux = cometd_servlet.getBayeux();
        bayeux.addExtension(new TimesyncExtension());
        
        bayeux.setSecurityPolicy(new AbstractBayeux.DefaultPolicy(){
            public boolean canHandshake(Message message)
            {
                if (_testHandshakeFailure<0)
                {
                    _testHandshakeFailure++;
                    return false;
                }
                return true;
            }
            
        });
        
        while (true)
        {
            Thread.sleep(2000);
            Set<String> ids=bayeux.getClientIDs();
            ClientImpl[] clients=new ClientImpl[ids.size()];
            int i=0;
            for (String id : ids)
            {
                clients[i]=(ClientImpl)bayeux.getClient(id);
                i++;
            }
            i=0;
        }
    }
}
