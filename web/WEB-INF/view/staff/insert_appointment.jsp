<%-- 
    Document   : insert_appointment
    Created on : 24-Mar-2014, 9:59:32 PM
    Author     : Babanani
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base_template>
    <jsp:attribute name="title">New Appointment</jsp:attribute>
    <jsp:attribute name="nav">
        <jsp:include page="_nav_tabs.jsp"><jsp:param name="active_tab" value="doctor" /></jsp:include>
    </jsp:attribute>
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <h3>Insert New Appointment</h3>
            <form action="${context}/staff/new_appointment?doctor_id=${doctor_id}" method="POST">
                ${errorMsg}
                Patient<br>
                <select class="form-control" style="width: auto;" name="health_card">
                    <c:forEach var="patient" items="${patientList}">
                        <option value="${patient.health_card}">${patient.health_card} - ${patient.name}</option>
                    </c:forEach>
                </select><br>
                Date<br>
                <input type="date" name="date"> <br>
                Time<br>
                <input type="time" name="time"> <br><br>
                <button type="submit" id="submit">Submit</button>
            </form>
        </div>
    </jsp:attribute>
</t:base_template>
