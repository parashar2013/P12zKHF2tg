<%-- 
    Document   : patient_profile
    Created on : Mar 8, 2014, 3:10:40 PM
    Author     : Parashar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:base_template>
    <jsp:attribute name="scripts">
    <script>
        $(function() {
          $("#treatment").datepicker();
        });
    </script>
    </jsp:attribute>
    
    <jsp:attribute name="title">Edit Patient Visit Record</jsp:attribute>
    
    <jsp:attribute name="nav">
        <jsp:include page="_nav_tabs.jsp"></jsp:include>
    </jsp:attribute>
    
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <h3>Edit Patient Visit Record</h3><hr>
            <h4 style="color: green;">${result}</h4>
            
            <form role="form" id="insert-visit-record" action="${context}/doctor/edit-record-do" method="POST">
              <div class="form-group">
                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${visit.name}" readonly>
              </div>
              <div class="form-group">
                <label for="diagnosis">Diagnosis</label>
                <textarea class="form-control" id="diagnosis" name="diagnosis" rows="4" cols="80">${visit.diagnosis}</textarea>
              </div>
              <div class="form-group">
                <label for="prescriptions">Prescriptions</label>
                <input type="text" class="form-control" id="prescriptions" name="prescriptions" value="${visit.prescriptions}">
              </div>
              <div class="form-group">
                <label for="prescriptions">Duration</label>
                <input type="text" class="form-control" id="duration" name="duration" value="30" readonly>
              </div>
              <div class="form-group">
                <label for="diagnosis">Scheduling of Treatment</label>
                <input type="text" class="form-control" id="treatment" name="treatment" value="${visit.treatment_date}">
              </div>
              <div class="form-group">
                <label for="comments">Comments (not visible to patient)</label>
                <textarea class="form-control" id="comments" name="comments" rows="4" cols="80">${visit.comments}</textarea>
              </div>
              <input type="hidden" id="health_card" name="health_card" value="${visit.health_card}">
              <input type="hidden" id="doctor_id" name="doctor_id" value="${visit.doctor_id}">
              <input type="hidden" id="date_and_time" name="date_and_time" value="${visit.date_and_time}">
              <button type="submit" class="btn btn-default">Submit</button>
            </form>
            
        </div>
    </jsp:attribute>
</t:base_template>
