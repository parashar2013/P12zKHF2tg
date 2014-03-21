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
        <div class="container" id="content-container">
            <h3>Appointments for ${doctor}</h3><hr>
            
            <table class="table">
                <thead>
                    <tr>
                        <th>Patient health Card</th>
                        <th>Date and Time</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="appointment" items="${appointmentList}">
                    <tr>
                        <td>${appointment[0]}</td>
                        <td>${appointment[1]}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            
        </div>
    </jsp:attribute>
</t:base_template>