<%-- 
    Document   : doctor_home
    Created on : Mar 8, 2014, 3:10:40 PM
    Author     : Parashar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base_template>
    <jsp:attribute name="title">Home</jsp:attribute>
    <jsp:attribute name="nav">
        <li class="active"><a href="home.jsp">Home</a></li>
        <li><a href="insert-record.jsp">Insert Record</a></li>
        <li><a href="search.jsp">Search</a></li>
    </jsp:attribute>
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            Doctor home page placeholder
        </div>
    </jsp:attribute>
</t:base_template>
