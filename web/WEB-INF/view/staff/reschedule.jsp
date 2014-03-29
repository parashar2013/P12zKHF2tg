<%-- 
    Document   : insert_appointment
    Created on : 24-Mar-2014, 9:59:32 PM
    Author     : Babanani
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base_template>
    <jsp:attribute name="title">Reschedule</jsp:attribute>
    <jsp:attribute name="nav">
        <jsp:include page="_nav_tabs.jsp"><jsp:param name="active_tab" value="doctor" /></jsp:include>
    </jsp:attribute>
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <h3>Reschedule Appointment</h3>
            <form action="${context}/staff/reschedule" method="POST">
                ${errorMsg}
                <input type="hidden" name="health_card" value="${health_card}">
                <input type="hidden" name="doctor_id" value="${doctor_id}">
                <input type="hidden" name="date_and_time" value="${date_and_time}">
                Date<br>
                <input type="date" name="date"> <br>
                Time<br>
                <input type="time" name="time"> <br><br>
                <button type="submit" id="submit">Submit</button>
            </form>
        </div>
    </jsp:attribute>
</t:base_template>
