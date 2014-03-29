<%-- 
    Document   : patient_profile
    Created on : Mar 8, 2014, 3:10:40 PM
    Author     : Parashar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:base_template>
    <jsp:attribute name="title">Home</jsp:attribute>
    
    <jsp:attribute name="nav">
        <jsp:include page="_nav_tabs.jsp"><jsp:param name="active_tab" value="profile" /></jsp:include>
    </jsp:attribute>
    
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <form action="${context}/patient/updateProfile" method="get">
                <h3>Update Profile</h3><hr>
                
                ${congrats}
                
                Name<br>
                <input type="text" name="name" value="${patientProfile.name}"><br>
                Address<br>
                <input type="text" name="address" value="${patientProfile.address}"><br>
                Phone Number<br>
                <input type="text" name="phone" value="${patientProfile.phoneNumber}"><br>
                Health Card<br>
                <input type="text" name="hCard" value="${patientProfile.healthCard}" readonly><br>
                SIN<br>
                <input type="text" name="SIN" value="${patientProfile.sinNumber}" readonly><br>
                Number Of Visits<br>
                <input type="text" name="numVisits" value="${patientProfile.numberOfVisits}" readonly><br>
                Default Doctor Id<br>
                <input type="text" name="defaultDoctorId" value="${patientProfile.defaultDoctorId}" readonly><br>
                Current Health<br>
                <input type="text" name="curHealth" value="${patientProfile.currentHealth}" readonly><br>
                Password<br>
                <input type="text" name="pw" value="${patientProfile.password}"><br><br>
                
                <button type="submit" id="submit">Update Profile</button>
            </form>
        </div>
    </jsp:attribute>
</t:base_template>
