<%-- 
    Document   : visit_records.jsp
    Created on : 24-Mar-2014, 8:09:15 PM
    Author     : Babanani
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base_template>
    <jsp:attribute name="title">Visit Records for ${patient}</jsp:attribute>
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <h3>Visit Records for ${patient}</h3><hr>
            
            <table class="table">
                <thead>
                    <tr>
                        <th>Duration</th>
                        <th>Health Card</th>
                        <th>Doctor Id</th>
                        <th>Diagnosis</th>
                        <th>Prescriptions</th>
                        <th>Treatment</th>
                        <th>Comments</th>
                        <th>Date and Time</th>
                        <th>Last Modified</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="visit" items="${visitList}">
                    <tr>
                        <td>${visit.duration}</td>
                        <td>${visit.visitPK.healthCard}</td>
                        <td>${visit.doctorId}</td>
                        <td>${visit.diagnosis}</td>                                            
                        <td>${visit.prescriptions}</td>
                        <td>${visit.treatment}</td>
                        <td>${visit.comments}</td>
                        <td>${visit.visitPK.dateAndTime}</td>  
                        <td>${visit.lastModified}</td>                        
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            
        </div>
    </jsp:attribute>
</t:base_template>