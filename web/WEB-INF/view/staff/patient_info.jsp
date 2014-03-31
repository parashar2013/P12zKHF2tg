<%-- 
    Document   : patient_info.jsp
    Created on : 20-Mar-2014, 1:15:59 PM
    Author     : Babanani
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base_template>
    <jsp:attribute name="title">Patient Info</jsp:attribute>
    <jsp:attribute name="nav">
        <jsp:include page="_nav_tabs.jsp"><jsp:param name="active_tab" value="doctor" /></jsp:include>
    </jsp:attribute>
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <form action="${context}/staff/patient_info">
                <input type="hidden" name="namePage" value="patient_info" />
                <input type="hidden" name="doctor_id" value="${doctor_id}" />
                <font color="red">${errorMsg}</font>
                Name<br>
                <input type="text" name="name" value="${patientProfile.name}"><br>
                Health Card<br>
                <input type="text" name="health_card" value="${patientProfile.healthCard}"><br>
                Address<br>
                <input type="text" name="address" value="${patientProfile.address}"><br>
                Phone Number<br>
                <input type="text" name="phone_number" value="${patientProfile.phoneNumber}"><br>
                SIN<br>
                <input type="text" name="sin_number" value="${patientProfile.sinNumber}"><br>
                Number Of Visits<br>
                <input type="text" name="number_of_visits" value="${patientProfile.numberOfVisits}" readonly><br>
                Default Doctor<br>
                <select name="default_doctor">
                    <c:forEach var="doctor" items="${doctorList}">
                        <option value="${doctor.id}" <c:if test="${doctor.id == patientProfile.defaultDoctorId}">selected</c:if>>${doctor.name}</option>
                    </c:forEach>
                </select><br>
                Current Health<br>
                <input type="text" name="current_health" value="${patientProfile.currentHealth}"><br>
                Password<br>
                <input type="text" name="password" value="${patientProfile.password}"><br><br>
                
                <button type="submit" id="submit">Update Profile</button>
            </form>
        </div>
    </jsp:attribute>
</t:base_template>
