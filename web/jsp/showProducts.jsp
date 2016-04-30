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
    Document   : showProducts
    Created on : Nov 17, 2008, 3:30:52 PM
    Author     : jaroslawwozniak
--%>


<%@ page pageEncoding="UTF-8" import="java.util.ArrayList, com.openbravo.pos.ticket.ProductInfoExt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="javascript; charset=UTF-8">
        <meta name = "viewport" content = "user-scalable=no, width=device-width">          
        <title><bean:message key="edit" /></title>
        <link rel=StyleSheet href="../layout.css" type="text/css" media=screen>
        <script type="text/javascript" src="../tableScript.js"></script>
        <script type="text/javascript" src="../a.js"></script>
    </head>
    <body>
        <jsp:useBean id="placeName" scope="request" type="java.lang.String"/>
        <jsp:useBean id="rates" scope="request" type="java.util.List"/>
        <div class="logo">
            <center>
            <img src="../images/logo.gif" alt="Openbravo" class="logo"/>
            <br>
            </center>
            <a href="showPlace.do?id=<%=request.getSession().getAttribute("place")%>&floorId=<%=request.getSession().getAttribute("floorId")%>" ><img alt="back" src="../images/back.png" class="back">../<%=placeName%>/Adding Products</a><br>
        </div>
        <br>
        <div class="pad">
            <center>
        <form action="#" method="get" >
            <html:select property="categoryId" value="id"
                         onchange="retrieveURL( 'productAjaxAction.do?categoryId=' + this.value, 'productSpan');rememberCategory(this.value);" >
                <html:options collection="categories" property="id" labelProperty="name"  />
            </html:select>
        </form>

        <span id="productSpan">
           
        <div id="notification" class="notification"></div>
            <table class="pickme" id="tab">
                <thead>
                    <tr>
                        <th class="name"><bean:message  key="item" /></th>
                        <th class="normal"><bean:message  key="price" /></th>
                        <th class="normal"></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <% boolean rowodd = false; %>
                    <c:forEach var="category" items="${subcategories}" varStatus="nr">
                        <% rowodd = !rowodd; %>
                        <tr class="<%= rowodd ? "odd" : "even" %>">
                            <td class="category" colspan="4" onclick="retrieveURLforCategories('productAjaxAction.do?categoryId=${category.id}&mode=1', '${category.id}');">${category.name}</td>
                        </tr>
                        <tr><td colspan="4"><div id="${category.id}"></div></td>

                        </tr>
                    </c:forEach>
                    <% ArrayList products = (ArrayList) request.getSession().getAttribute("products");%>
                    
                    <c:forEach var="product" items="${products}" varStatus="nr">
                        <% rowodd = !rowodd; %>
                        <tr class="<%= rowodd ? "odd" : "even" %>">
                            <div id="pro${nr.count - 1}">
                            <td class="name">${product.name}</td>
                            <td class="normal"><fmt:formatNumber type="currency" value="${product.priceSell + product.priceSell * rates[nr.count - 1]}" maxFractionDigits="2" minFractionDigits="2"/></td>
                            <td class="normal"></td>
                            <td><input value="Add" type="submit" class="floor" onclick="ajaxAddProduct('<%=request.getSession().getAttribute("place")%>', ${nr.count - 1}, '${product.name}', '${product.id}', 0);"/></td>
                            </div>
                        </tr>
                        <tr>
                            <td colspan="4"><div id="aux${nr.count - 1}"></div></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </span>
        </center>
        </div>
    </body>
</html>
