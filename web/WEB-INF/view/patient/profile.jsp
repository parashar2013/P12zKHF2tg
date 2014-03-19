<%-- 
    Document   : patient_profile
    Created on : Mar 8, 2014, 3:10:40 PM
    Author     : Parashar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:base_template>
    <jsp:attribute name="title">Home</jsp:attribute>
    
    <jsp:attribute name="nav">
        <jsp:include page="_nav_tabs.jsp"><jsp:param name="active_tab" value="profile" /></jsp:include>
    </jsp:attribute>
    
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <h3>Update Profile</h3><hr>
            Shinkuu Hadoken (You can't update the profile but you can at least see it)<br><br>
            Name: ${patientProfile.name}<br>
            Health Card = ${patientProfile.healthCard}<br>
            Address = ${patientProfile.address}<br>
            Phone Number = ${patientProfile.phoneNumber}<br>
            SIN = ${patientProfile.sinNumber}<br>
            Number Of Visits = ${patientProfile.numberOfVisits}<br>
            Default Doctor Id = ${patientProfile.defaultDoctorId}<br>
            Current Health = ${patientProfile.currentHealth}<br>
            Password = ${patientProfile.password}<br>
        </div>
    </jsp:attribute>
</t:base_template>
