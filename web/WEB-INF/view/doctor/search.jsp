<%-- 
    Document   : search
    Created on : Mar 12, 2014, 10:27:59 PM
    Author     : Parashar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:base_template>
    <jsp:attribute name="title">Search</jsp:attribute>
    
    <jsp:attribute name="nav">
        <jsp:include page="_nav_tabs.jsp"><jsp:param name="active_tab" value="search" /></jsp:include>
    </jsp:attribute>
        
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <form action="${context}/doctor/search-do" method="POST">
                Name:<br>
                <input type="text" name="name" value="${name}"><br>
                Diagnosis:<br>
                <input type="text" name="diagnosis" value="${diagnosis}"><br>
                Comments:<br>
                <input type="text" name="comments" value="${comments}"><br>
                Prescriptions:<br>
                <input type="text" name="prescriptions" value="${prescriptions}"><br>
                Date of Visit:<br>
                <input type="date" name="date1" value="${date1}"> to <input type="date" name="date2" value="${date2}"><br>
                Scheduling of Treatment<br>
                <input type="date" name="date3" value="${date3}"> to <input type="date" name="date4" value="${date4}"><br><br>
                <button type="submit" id="submit">Search</button>
            </form>
            <br><br>
            <h4>Search Results</h4>
            <table class="table">
                <thead>
                    <tr>
                        <th>Patient</th>
                        <th>Diagnosis</th>
                        <th>Prescriptions</th>
                        <th>Comments</th>
                        <th>Scheduling of Treatment</th>
                        <th>Date of Visit</th>
                        <th>Edit</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="visit" items="${vList}">
                    <tr>
                        <td>${visit.name}</td>
                        <td>${visit.diagnosis}</td>
                        <td>${visit.prescriptions}</td>
                        <td>${visit.comments}</td>
                        <td>${visit.treatment}</td>
                        <td>${visit.date_and_time}</td>
                        <td><a href="${context}/doctor/edit-record?health_card=${visit.health_card}&date_and_time=${visit.date_and_time}">Edit</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:attribute>
</t:base_template>
