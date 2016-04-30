<%--
   Openbravo POS is a point of sales application designed for touch screens.
   Copyright (C) 2007-2009 Openbravo, S.L.
   http://sourceforge.net/projects/openbravopos

    This file is part of Openbravo POS.

    Openbravo POS is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Openbravo POS is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.
 --%>
<%-- 
    Document   : showFloors
    Created on : Nov 10, 2008, 1:24:13 PM
    Author     : jaroslawwozniak
--%>


<%@page pageEncoding="UTF-8"
        import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="javascript; charset=UTF-8">
        <meta name = "viewport" content = "user-scalable=no, width=device-width">
            <title><bean:message key="floors" /></title>
        <link rel=StyleSheet href="layout.css" type='text/css' media=screen>
        <script type='text/javascript' src='a.js'></script>
        <script type='text/javascript' src='tableScript.js'></script>
    </head>
    <body>
        <jsp:useBean id="floorId" scope="request" type="java.lang.String" />
        <center>
        <img src="images/logo.gif" alt="Openbravo" class="logo" /><br>
        </center>
        <div>
        <center>
            <form name="FloorForm" method="post" class="pad">
                <html:select property="floorId" value="${floorId}" onchange="saveFloorId(this.value);retrieveURL( 'sec/floorAjaxAction.do?floorId=' + this.value, 'ble');"  >
                    <html:options collection="floors" property="id" labelProperty="name"  />
                </html:select>
            </form>
        
            <div class="pad2">
                <span id="ble">
                    <logic:present name="places">
                            <input type="hidden" name="floorId" value="0" />
                            <% ArrayList places = (ArrayList) request.getSession().getAttribute("places");%>
                            <c:forEach var="place" items="${places}">
                                <c:set var="var" value="false" />
                                <c:forEach var="busy" items="${busy}">
                                    <c:if test="${place.id == busy.id}">
                                        <input type=submit name="id" value="${place.name}" onclick="getLocation('${place.id}');" class="busy">
                                        <c:set var="var" value="true" />
                                    </c:if>
                                </c:forEach>
                                    <c:if test="${var == false}">
                                       <input type=submit name="id" value="${place.name}" onclick="getLocation('${place.id}');" class="notbusy">
                                    </c:if>
                               
                            </c:forEach>
                    </logic:present>
                </span>
            </div>
            </center>
        
        <div class="bottom">
            <form action="logout.do">
               <center> <input type="submit" id="d" value="Logout" style="width:100px;"></center>              
            </form>
        </div>
        </div>
    </body>

</html>
