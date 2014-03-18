<%-- 
    Document   : doctor_home
    Created on : Mar 8, 2014, 3:10:40 PM
    Author     : Parashar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:base_template>
    <jsp:attribute name="title">Patient Info</jsp:attribute>
    
    <jsp:attribute name="nav">
        <jsp:include page="_nav_tabs.jsp"></jsp:include>
    </jsp:attribute>
    
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <h3>${patient.name}</h3><hr>

            
            address = ${patient.address} <br>
            phoneNumber = ${patient.phoneNumber} <br>
            Default Doctor = ... <br>
            currentHealth = ${patient.currentHealth}
            
        </div>
    </jsp:attribute>
</t:base_template>
