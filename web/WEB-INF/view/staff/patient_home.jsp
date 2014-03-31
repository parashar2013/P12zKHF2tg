<%-- 
    Document   : staff_home
    Created on : Mar 8, 2014, 3:11:09 PM
    Author     : Parashar
--%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
    
<t:base_template>
    <jsp:attribute name="title">Patients</jsp:attribute>
    <jsp:attribute name="nav">
        <jsp:include page="_nav_tabs.jsp"><jsp:param name="active_tab" value="doctor" /></jsp:include>
    </jsp:attribute>
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <h3>Patients</h3><br>
            <a href="${context}/staff/patient_info?newp=1&noerr=1&doctor_id=${doctor_id}">New Patient</a><hr>
            <font color="green">${errorMsg}</font>
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
                            <td><a href="${context}/staff/patient_info?noerr=1&health_card=${patient.healthCard}&doctor_id=${doctor_id}">${patient.name}</a></td>
                            <td>${patient.address}</td>
                            <td>${patient.phoneNumber}</td>
                            <td>${patient.numberOfVisits}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
                
        </div>
    </jsp:attribute>
</t:base_template>