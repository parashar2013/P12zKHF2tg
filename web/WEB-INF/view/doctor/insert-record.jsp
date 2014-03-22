<%-- 
    Document   : insert-record
    Created on : Mar 12, 2014, 10:50:22 PM
    Author     : Parashar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base_template>
    <jsp:attribute name="title">Insert Record</jsp:attribute>
    
    <jsp:attribute name="nav">
        <jsp:include page="_nav_tabs.jsp"><jsp:param name="active_tab" value="insert-record" /></jsp:include>
    </jsp:attribute>
        
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <h3>Insert Patient Visit Record</h3><hr>
            <br>
            ${ass}
            <br><br>
            <form role="form" id="insert-visit-record" action="${context}/doctor/insert" method="POST">
              <div class="form-group">
                <label for="health_card">Appointment</label>
                <select class="form-control" id="appointment" name="appointment">
                    <c:forEach var="patient" items="${patients}">
                        <c:forEach var="appt" items="${patient.appointments}">
                            <option value="${patient.healthCard} - ${appt.appointmentPK.dateAndTime}">${patient.name} - ${appt.appointmentPK.dateAndTime}</option>
                        </c:forEach>
                    </c:forEach>
                </select>
              </div>
              <div class="form-group">
                <label for="diagnosis">Diagnosis</label>
                <textarea class="form-control" id="diagnosis" name="diagnosis" rows="4" cols="80"></textarea>
              </div>
              <div class="form-group">
                <label for="prescriptions">Prescriptions</label>
                <input type="text" class="form-control" id="prescriptions" name="prescriptions" placeholder="">
              </div>
              <div class="form-group">
                <label for="prescriptions">Duration</label>
                <input type="text" class="form-control" id="duration" name="duration" placeholder="">
              </div>
              <div class="form-group">
                <label for="diagnosis">Scheduling of Treatment</label>
                <input type="date" class="form-control" id="treatment" name="treatment">
              </div>
              <div class="form-group">
                <label for="comments">Comments (not visible to patient)</label>
                <textarea class="form-control" id="comments" name="comments" rows="4" cols="80"></textarea>
              </div>
              <button type="submit" class="btn btn-default">Submit</button>
            </form>
            
        </div>
    </jsp:attribute>
</t:base_template>
