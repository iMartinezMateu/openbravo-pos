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
    Document   : placeAjax
    Created on : Feb 2, 2009, 1:26:41 PM
    Author     : jaroslawwozniak
--%>

<%@page contentType="text/javascript" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
 <span>
<input type="text" id="input${lineNo}" size="3" onchange="getIndexBackByEditing('${lineNo}', '${place}');" value="<fmt:formatNumber type="number" value="${line.multiply}" maxFractionDigits="2" minFractionDigits="0"/>" /> <fmt:formatNumber type="currency" value="${line.value}" maxFractionDigits="2" minFractionDigits="2"/>

 </span>
 <span>
     Total:  <fmt:formatNumber type="currency" value="${total}" maxFractionDigits="2" minFractionDigits="2" />
 </span>
