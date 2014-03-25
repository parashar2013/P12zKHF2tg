<%-- 
    Document   : doctor_home.jsp
    Created on : 24-Mar-2014, 7:52:34 PM
    Author     : Babanani
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base_template>
    <jsp:attribute name="title">Home</jsp:attribute>
    <jsp:attribute name="nav">
        <jsp:include page="_nav_tabs.jsp"><jsp:param name="active_tab" value="doctor" /></jsp:include>
    </jsp:attribute>
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