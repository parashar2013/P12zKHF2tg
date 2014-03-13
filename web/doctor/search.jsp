<%-- 
    Document   : search
    Created on : Mar 12, 2014, 10:27:59 PM
    Author     : Parashar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base_template>
    <jsp:attribute name="title">Search</jsp:attribute>
    <jsp:attribute name="nav">
        <li><a href="home.jsp">Home</a></li>
        <li><a href="insert-record.jsp">Insert Record</a></li>
        <li class="active"><a href="search.jsp">Search</a></li>
    </jsp:attribute>
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            Doctor search page placeholder<br>
            <input type="text" />
        </div>
    </jsp:attribute>
</t:base_template>
