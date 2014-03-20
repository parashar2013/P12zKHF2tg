<%-- 
    Document   : staff_home
    Created on : Mar 8, 2014, 3:11:09 PM
    Author     : Parashar
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base_template>
    <jsp:attribute name="title">Home</jsp:attribute>
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <h3>Doctors</h3>
            <ul>
                <c:forEach var="doctor" items="${doctorList}">
                    <li>
                        ${doctor.name}
                        <a href="${context}/staff/home?doctor_id=${doctor.id}&pg=patients">Patients</a>
                        <a href="${context}/staff/home?doctor_id=${doctor.id}&pg=appointments">Appointments</a>
                    </li>
                </c:forEach>
            <ul/>
        </div>
    </jsp:attribute>
</t:base_template>