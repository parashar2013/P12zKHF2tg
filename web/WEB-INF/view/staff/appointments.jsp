<%-- 
    Document   : appointments
    Created on : 19-Mar-2014, 3:51:02 PM
    Author     : Babanani
--%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
    
<t:base_template>
    <jsp:attribute name="title">Appointments for ${doctor}</jsp:attribute>
    <jsp:attribute name="nav">
        <jsp:include page="_nav_tabs.jsp"><jsp:param name="active_tab" value="doctor" /></jsp:include>
    </jsp:attribute>
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <h3>Appointments for ${doctor.name}</h3><br>
            <a href="${context}/staff/insert_appointment?doctor_id=${doctor.id}">New Appointment</a>
            <hr>
                
            <table class="table">
                <thead>
                    <tr>
                        <th>Patient Health Card</th>
                        <th>Patient Name</th>
                        <th>Date and Time</th>
                        <th>Delete</th>
                        <th>Reschedule</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="appointment" items="${appointmentList}">
                        <tr>
                            <td>${appointment.health_card}</td>
                            <td>${appointment.name}</td>
                            <td>${appointment.date_and_time}</td>
                            <td><a href="${context}/staff/delete_appointment?health_card=${appointment.health_card}&date_and_time=${appointment.date_and_time}&doctor_id=${doctor.id}">Delete</a></td>
                            <td><a href="${context}/staff/reschedule_page?health_card=${appointment.health_card}&date_and_time=${appointment.date_and_time}&doctor_id=${doctor.id}">Reschedule</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
                
        </div>
    </jsp:attribute>
</t:base_template>