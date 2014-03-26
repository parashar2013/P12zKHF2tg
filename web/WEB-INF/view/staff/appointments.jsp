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
    <jsp:attribute name="content">
         <div class="container" id="button-container">
            <a href="${context}/staff/insert_appointment.jsp?doctor_id=${doctor.id}">New Appointment</a>
        </div>       
        <div class="container" id="content-container">
            <h3>Appointments for ${doctor.name}</h3><hr>
            
            <table class="table">
                <thead>
                    <tr>
                        <th>Patient Health Card</th>
                        <th>Patient Name</th>
                        <th>Date and Time</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="appointment" items="${appointmentList}">
                    <tr>
                        <td>${appointment[0]}</td>
                        <td>${appointment[1]}</td>
                        <td>${appointment[2]}</td>
                        <td><a href="${context}/staff/delete_appointment?health_card=${appointment[0]}&date_and_time=${appointment[2]}&doctor_id=${doctor.id}">Delete</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            
        </div>
    </jsp:attribute>
</t:base_template>