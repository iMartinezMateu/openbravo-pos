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
    Document   : productsAjaxNextLevel
    Created on : Apr 23, 2009, 12:57:09 PM
    Author     : jaroslawwozniak
--%>

<%@page contentType="text/javascript" pageEncoding="UTF-8"
        import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>d">
<link rel=StyleSheet href="../layout.css" type="text/css" media=screen>
<span>
    <% boolean rowodd = false; %>
    <c:forEach var="category" items="${subcategories}" varStatus="nr">
        <% rowodd = !rowodd; %>
        <tr class="<%= rowodd ? "odd" : "even" %>">
            <td class="category" colspan="4" onclick="retrieveURLforCategories('productAjaxAction.do?categoryId=${category.id}&mode=1', '${category.id}');update();">${category.name}</td>
        </tr>
        <tr>
            <td colspan="4"><div id="${category.id}"></div></td>
        </tr>
    </c:forEach>
    <% ArrayList products = (ArrayList) request.getSession().getAttribute("products");%>
    <c:forEach var="product" items="${products}" varStatus="nr">
        <% rowodd = !rowodd; %>
        <tr id="${nr.count - 1}" class="<%= rowodd ? "odd" : "even" %>">
            <td class="name">${product.name}</td>
            <td class="normal"><fmt:formatNumber type="currency" value="${product.priceSell + product.priceSell * rates[nr.count - 1]}" maxFractionDigits="2" minFractionDigits="2"/></td>
            <td class="normal"></td>
            <td><input value="Add" type="submit" class="floor" onclick="ajaxAddProduct('<%=request.getSession().getAttribute("place")%>', ${nr.count - 1}, '${product.name}', '${product.id}', 0);"/></td>
        </tr>
    </c:forEach>
</span>
