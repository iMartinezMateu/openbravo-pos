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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mortbay.jetty.HttpMethods;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.client.ContentExchange;
import org.mortbay.jetty.client.HttpClient;
import org.mortbay.jetty.servlet.SessionHandler;
import org.mortbay.jetty.webapp.WebAppContext;
import org.testng.annotations.Test;

/**
 * @version $Revision: 1256 $ $Date: 2008-10-18 01:50:04 +1100 (Sat, 18 Oct 2008) $
 */
public class TIMSessionMigrationTest
{
    @Test
    public void testSessionMigration() throws Exception
    {
        File warDir = new File(System.getProperty("java.io.tmpdir"), "sessionMigration");
        warDir.deleteOnExit();
        warDir.mkdir();
        File webInfDir = new File(warDir, "WEB-INF");
        webInfDir.deleteOnExit();
        webInfDir.mkdir();
        File webXml = new File(webInfDir, "web.xml");
        webXml.deleteOnExit();
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<web-app xmlns=\"http://java.sun.com/xml/ns/j2ee\"\n" +
                "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "         xsi:schemaLocation=\"http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd\"\n" +
                "         version=\"2.4\">\n" +
                "\n" +
                "</web-app>";
        FileWriter w = new FileWriter(webXml);
        w.write(xml);
        w.close();

        Random random = new Random(System.nanoTime());
        String contextPath = "/migration";
        String servletMapping = "/server";
        int port1 = random.nextInt(50000) + 10000;
        Server server1 = new Server(port1);
        WebAppContext context1 = new WebAppContext(warDir.getCanonicalPath(), contextPath);
        context1.setSessionHandler(new SessionHandler());
        context1.addServlet(TestServlet.class, servletMapping);
        server1.setHandler(context1);
        server1.start();
        try
        {
            int port2 = random.nextInt(50000) + 10000;
            Server server2 = new Server(port2);
            WebAppContext context2 = new WebAppContext(warDir.getCanonicalPath(), contextPath);
            context2.setSessionHandler(new SessionHandler());
            context2.addServlet(TestServlet.class, servletMapping);
            server2.setHandler(context2);
            server2.start();
            try
            {
                HttpClient client = new HttpClient();
                client.setConnectorType(HttpClient.CONNECTOR_SOCKET);
                client.start();
                try
                {
                    // Perform one request to server1 to create a session
                    int value = 1;
                    ContentExchange exchange1 = new ContentExchange(true);
                    exchange1.setMethod(HttpMethods.POST);
                    exchange1.setURL("http://localhost:" + port1 + contextPath + servletMapping + "?action=set&value=" + value);
                    client.send(exchange1);
                    exchange1.waitForDone();
                    assert exchange1.getResponseStatus() == HttpServletResponse.SC_OK : exchange1.getResponseStatus();
                    String sessionCookie = exchange1.getResponseFields().getStringField("Set-Cookie");
                    System.out.println("sessionCookie = " + sessionCookie);

                    // Perform a request to server2 using the session cookie from the previous request
                    // This should migrate the session from server1 to server2.
                    ContentExchange exchange2 = new ContentExchange(true);
                    exchange2.setMethod(HttpMethods.GET);
                    exchange2.setURL("http://localhost:" + port2 + contextPath + servletMapping + "?action=get");
                    exchange2.getRequestFields().add("Cookie", sessionCookie);
                    client.send(exchange2);
                    exchange2.waitForDone();
                    assert exchange2.getResponseStatus() == HttpServletResponse.SC_OK;
                    String response = exchange2.getResponseContent();
                    assert response.trim().equals(String.valueOf(value));
                }
                finally
                {
                    client.stop();
                }
            }
            finally
            {
                server2.stop();
            }
        }
        finally
        {
            server1.stop();
        }
    }

    public static class TestServlet extends HttpServlet
    {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            doPost(request, response);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            HttpSession session = request.getSession(false);
            if (session == null) session = request.getSession(true);

            String action = request.getParameter("action");
            if ("set".equals(action))
            {
                int value = Integer.parseInt(request.getParameter("value"));
                session.setAttribute("value", value);
                PrintWriter writer = response.getWriter();
                writer.println(value);
                writer.flush();
            }
            else if ("get".equals(action))
            {
                int value = (Integer)session.getAttribute("value");
                PrintWriter writer = response.getWriter();
                writer.println(value);
                writer.flush();
            }
        }
    }
}
