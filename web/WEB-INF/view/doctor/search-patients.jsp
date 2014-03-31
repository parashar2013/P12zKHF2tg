<%-- 
    Document   : search
    Created on : Mar 12, 2014, 10:27:59 PM
    Author     : Parashar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:base_template>
    <jsp:attribute name="title">Search Patients</jsp:attribute>
    
    <jsp:attribute name="nav">
        <jsp:include page="_nav_tabs.jsp"><jsp:param name="active_tab" value="search-patients" /></jsp:include>
    </jsp:attribute>
        
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <form action="${context}/doctor/search-patients-do" method="POST">
                Name:<br>
                <input type="text" name="name" value="${name}"><br>
                Health Card<br>
                <input type="text" name="health_card" value="${health_card}"><br>
                <!--Date of Visit:<br>
                <input type="date" name="date" value="${date}">--><br>
                <button type="submit" id="submit">Search</button>
            </form>
            <br><br>
            <h4>Search Results</h4>
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
                        <td><a href="${context}/patient/info?healthCard=${patient.health_card}">${patient.name}</a></td>
                        <td>${patient.address}</a></td>
                        <td>${patient.phone_number}</a></td>
                        <td>${patient.number_of_visits}</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:attribute>
</t:base_template>
