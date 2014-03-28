<%-- 
    Document   : index
    Created on : Jan 21, 2014, 2:41:52 PM
    Author     : Parashar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base_template>
    <jsp:attribute name="scripts"><script src="${context}/js/login.js"></script></jsp:attribute>
    
    <jsp:attribute name="title">Login</jsp:attribute>
    
    <jsp:attribute name="content">
        <div class="container" id="content-container">
            <form class="form-signin" action="login" method="get">
                <h2 class="form-signin-heading">Login to HospitalDB</h2><hr>

                <div class="btn-group" data-toggle="buttons" id="doctor-patient-buttons">
                    <label class="btn btn-primary active">
                      <input type="radio" class="radio" name="login_type" id="login_patient" value="patient"> Patient
                    </label>
                    <label class="btn btn-primary">
                      <input type="radio" class="radio" name="login_type" id="login_doctor" value="employee" checked> Employee
                    </label>
                </div>

                <input type="text" name="login_id" id="login_id" class="form-control" value="2"> <!--placeholder="Health Card" maxlength="10">-->
                <input type="text" name="login_password" id="login_password" class="form-control" value="test"> <!--placeholder="Password">-->

                <label class="checkbox">
                    <input type="checkbox" name="remember_me"> Remember me
                </label>
                <button class="btn btn-lg btn-block btn-primary" type="submit" id="submit">Login</button>
            </form>
        </div> <!-- /container -->
    </jsp:attribute>
</t:base_template>
