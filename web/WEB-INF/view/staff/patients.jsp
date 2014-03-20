<%-- 
    Document   : patients
    Created on : 19-Mar-2014, 3:51:22 PM
    Author     : Babanani
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base_template>
    <jsp:attribute name="title">Patients for ${doctor}</jsp:attribute>
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <h3>Patients for ${doctor}</h3><hr>
            
            <table class="table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Address</th>
                        <th>Phone Number</th>
                        <th>Number of Visits</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="patient" items="${patientList}">
                    <tr>
                        <td><a href="${context}/patient/info?healthCard=${patient.healthCard}">${patient.name}</a></td>
                        <td>${patient.address}</td>
                        <td>${patient.phoneNumber}</td>
                        <td>${patient.numberOfVisits}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            
        </div>
    </jsp:attribute>
</t:base_template>
