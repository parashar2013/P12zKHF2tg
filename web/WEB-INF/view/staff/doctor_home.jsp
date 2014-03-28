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
            <table class="table">
                <tbody>
                <c:forEach var="doctor" items="${doctorList}">
                    <tr>
                        <td>${doctor.name}</td>
                        <td><a href="${context}/staff/visit_records?doctor_id=${doctor.id}">Records</a></td>
                        <td><a href="${context}/staff/patient_home?doctor_id=${doctor.id}&pg=patients">Patients</a></td>
                        <td><a href="${context}/staff/doctor_home?doctor_id=${doctor.id}&pg=appointments">Appointments</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:attribute>
</t:base_template>