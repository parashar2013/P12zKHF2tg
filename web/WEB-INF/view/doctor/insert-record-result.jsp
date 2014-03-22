<%-- 
    Document   : insert-record
    Created on : Mar 12, 2014, 10:50:22 PM
    Author     : Parashar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base_template>
    <jsp:attribute name="title">Insert Record</jsp:attribute>
    
    <jsp:attribute name="nav">
        <jsp:include page="_nav_tabs.jsp"><jsp:param name="active_tab" value="insert-record" /></jsp:include>
    </jsp:attribute>
        
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <h3>Record inserted successfully</h3>
        </div>
    </jsp:attribute>
</t:base_template>
