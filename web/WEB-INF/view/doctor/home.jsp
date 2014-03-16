<%-- 
    Document   : doctor_home
    Created on : Mar 8, 2014, 3:10:40 PM
    Author     : Parashar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="active_tab" value="home" scope="request" />

<t:base_template>
    <jsp:attribute name="title">Home</jsp:attribute>
    
    <jsp:attribute name="nav">
        <jsp:include page="_nav_tabs.jsp"><jsp:param name="active_tab" value="home" /></jsp:include>
    </jsp:attribute>
    
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <h3>Current Patients</h3><hr>
            
            
        </div>
    </jsp:attribute>
</t:base_template>
