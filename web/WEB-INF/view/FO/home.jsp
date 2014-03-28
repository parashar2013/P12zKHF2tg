<%-- 
    Document   : financial_officer_home
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
            <form action="${context}/FO/checkDoctor" method="POST">
                <select name="doctorId" class="form-control" style="float: left; width: auto;">
                    <c:forEach var="doctor" items="${doctors}">
                        <option value="${doctor.id}" <c:if test="${doctor.id == doctorId}">selected</c:if>>${doctor.name}</option>
                    </c:forEach>
                </select>
                <select name="health_card" class="form-control" style="float: left; width: auto;">
                    <option value="NA">All Patients</option>
                    <c:forEach var="patient" items="${patients}">
                        <option value="${patient}" <c:if test="${patient == health_card}">selected</c:if>>${patient}</option>
                    </c:forEach>
                </select>
                <br><br>
                <input type="date" name="date1" value="${date1}"> to <input type="date" name="date2" value="${date2}"><br><br>
                <button type="submit" class="btn btn-default">Submit</button>
            </form>
            <c:if test="${dName != NULL}">
                <br>
                <h3>Results for ${dName}</h3>
                Visit count: ${count}<br><br>
                <table class="table">
                    <thead>
                        <tr>
                            <th>Health Card</th>
                            <th>Duration</th>
                            <th>Diagnosis</th>
                            <th>Prescriptions</th>
                            <th>Scheduling of Treatment</th>
                            <th>Date of Visit</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${summary}">
                        <tr>
                            <td>${item.healthCard}</td>
                            <td>${item.duration}</td>
                            <td>${item.diagnosis}</td>
                            <td>${item.prescriptions}</td>
                            <td>${item.treatment}</td>
                            <td>${item.dateAndTime}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </jsp:attribute>
</t:base_template>
