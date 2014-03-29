<%-- 
    Document   : visit_records.jsp
    Created on : 24-Mar-2014, 8:09:15 PM
    Author     : Babanani
--%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
    
<t:base_template>
    <jsp:attribute name="title">Assign Doctor to Patient</jsp:attribute>
    <jsp:attribute name="nav">
        <jsp:include page="_nav_tabs.jsp"><jsp:param name="active_tab" value="assign" /></jsp:include>
    </jsp:attribute>
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <form action="${context}/staff/assign" method="POST">
                ${errorMsg}
                Doctor<br>
                <select class="form-control" style="width: auto;" name="doctor_id">
                    <c:forEach var="doctor" items="${doctorList}">
                        <option value="${doctor.id}">${doctor.name}</option>
                    </c:forEach>
                </select><br>
                Patient<br>
                <select class="form-control" style="width: auto;" name="patient_id">
                    <c:forEach var="patient" items="${patientList}">
                        <option value="${patient.healthCard}">${patient.healthCard} - ${patient.name}</option>
                    </c:forEach>
                </select><br>
                <button type="submit" id="submit">Assign</button>
            </form>
        </div>
    </jsp:attribute>
</t:base_template>