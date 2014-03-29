<%-- 
    Document   : doctor_home
    Created on : Mar 8, 2014, 3:10:40 PM
    Author     : Parashar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:base_template>
    <jsp:attribute name="title">Patient Record</jsp:attribute>
    
    <jsp:attribute name="nav">
        <jsp:include page="_nav_tabs.jsp"><jsp:param name="active_tab" value="home" /></jsp:include>
    </jsp:attribute>
    
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <h2 class="page-header">${patient.name} <span style="color: #777; float:right;">Patient Record</span></h2>
            
            <h3 class="sub-header">Patient Info</h3>
            <b>Address:</b> ${patient.address} <br>
            <b>Phone Number:</b> ${patient.phone_number} <br>
            <b>Default Doctor:</b> ${patient.default_doctor_name} <br>
            <b>Current Health:</b> ${patient.current_health}
            
            <br><br>
            <h3 class="sub-header">Past Visits</h3>
            <table class="table">
                <thead>
                    <tr>
                        <th>Doctor</th>
                        <th>Diagnosis</th>
                        <th>Prescriptions</th>
                        <th>Date and Time</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="visit" items="${visitList}">
                    <tr>
                        <td>${visit.name}</td>
                        <td>${visit.diagnosis}</td>
                        <td>${visit.prescriptions}</td>
                        <td>${visit.date_and_time}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            
            <br>
            <h3 class="sub-header">Other doctors who have permission to view this patient</h3>
            <ul>
                <c:forEach var="doctor" items="${doctorsWithPermissionList}">
                    <li>${doctor.name}
                        <c:if test="${isDefaultDoctor}">
                        <a href="${context}/doctor/revoke-permission?health_card=${patient.health_card}&doctor_id=${doctor.id}"
                           title="Revoke permission to view this patient from this doctor">(Revoke)</a>
                        </c:if>
                    </li>
                </c:forEach>
            </ul>

            <br>
            <h3 class="sub-header">Give permission to another doctor ${isDefaultDoctor}</h3>
            <c:if test="${isDefaultDoctor}">
                <form role="form" action="${context}/doctor/give-permission" method="post">
                    <div class="form-group">
                      <select class="form-control selectpicker" id="doctor_id" name="doctor_id" style="display: inline-block; width: 200px;">
                          <c:forEach var="doctor" items="${doctorsWithoutPermissionList}">
                              <option value="${doctor.id}">${doctor.name}</option>
                          </c:forEach>
                      </select>
                        <input type="hidden" id="health_card" name="health_card" value="${patient.health_card}">
                      <button style="display: inline; width: 150px;" class="btn btn-block btn-primary" type="submit" id="submit">Give Permission</button>
                    </div>
                </form>
            </c:if>
            <c:if test="${not isDefaultDoctor}">
                <div>Only the default doctor can grant permissions</div>
            </c:if>

        </div>
    </jsp:attribute>
</t:base_template>
