<%-- 
    Document   : user_data_form
    Created on : Jan 21, 2014, 2:43:48 PM
    Author     : Parashar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>User Data Form</h1>
        
        <form method='post' action='UserDataServlet'>
            What's your name: <input type="text" name="name"><br>
            What's your favorite color: <input type="text" name="color">
            <br>
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
