<%-- 
    Document   : doctor_home
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
            <h3>Current Patients</h3><hr>
            
            <table class="table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Address</th>
                        <th>Phone Number</th>
                        <th>Number of Visits</th>
                    </tr>
                </thead>
                <tbody
                <c:forEach var="patient" items="${patientList}">
                    <tr>
                        <td><a href="#">${patient.name}</a></td>
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
