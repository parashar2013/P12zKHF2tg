<%-- 
    Document   : index
    Created on : Jan 21, 2014, 2:41:52 PM
    Author     : Parashar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
        
        <!-- CSS -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="css/style.css">
        
        <!-- JS -->
        <script src="http://code.jquery.com/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
    
        <title>Login | HospitalDB</title>
    </head>
    <body>
        <div class="container">

            <form class="form-signin" action="login" method="get">
                <h2 class="form-signin-heading">Login to HospitalDB</h2><hr>
                
                <div class="btn-group" data-toggle="buttons" id="doctor-patient-buttons">
                    <label class="btn btn-primary active">
                      <input type="radio" class="radio" name="login_type" id="login_patient" value="employee" checked> Employee
                    </label>
                    <label class="btn btn-primary">
                      <input type="radio" class="radio" name="login_type" id="login_doctor" value="doctor"> Doctor
                    </label>
                </div>

                <input type="text" name="login_id" id="login_id" class="form-control" placeholder="Health Card" maxlength="10">
                <input type="password" name="login_password" id="login_password" class="form-control" placeholder="Password">
                
                <label class="checkbox">
                    <input type="checkbox" name="remember_me"> Remember me
                </label>
                <button class="btn btn-lg btn-block btn-primary" type="submit" id="submit">Login</button>
            </form>

        </div> <!-- /container -->
    </body>
</html>
