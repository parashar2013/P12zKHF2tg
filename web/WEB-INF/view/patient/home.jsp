<%-- 
    Document   : patient_home
    Created on : Mar 8, 2014, 3:10:40 PM
    Author     : Parashar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:base_template>
    <jsp:attribute name="title">Home</jsp:attribute>
    
    <jsp:attribute name="nav">
        <jsp:include page="_nav_tabs.jsp"><jsp:param name="active_tab" value="home" /></jsp:include>
    </jsp:attribute>
    
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <h3>Past Appointments</h3><hr>
            <table class="table">
                <thead>
                    <tr>
                        <th>Doctor ID</th>
                        <th>Date and Time</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="appointment" items="${appointmentList}">
                    <tr>
                        <td>${appointment.doctorId}</td>
                        <td>${appointment.appointmentPK.dateAndTime}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            
        </div>
    </jsp:attribute>
</t:base_template>
