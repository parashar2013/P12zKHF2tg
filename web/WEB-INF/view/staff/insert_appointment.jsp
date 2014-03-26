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
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <h3>Insert New Appointment</h3>
            <form action="${context}/staff/new_appointment?doctor_id=${doctor_id}" method="POST">
                Health Card
                <input name="health_card" type="text"> <br>
                Year
                <input name="year" type="text">
                Month
                <input name="month" type="text">
                Day
                <input name="day" type="text"> <br>
                Hour
                <input name="hour" type="text">
                Minute
                <input name="minute" type="text"> <br>
                <button type="submit" id="submit">Submit</button>
            </form>
        </div>
    </jsp:attribute>
</t:base_template>
