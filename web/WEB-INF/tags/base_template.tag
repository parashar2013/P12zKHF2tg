<%-- 
    Document   : base_template
    Created on : Mar 9, 2014, 11:01:54 AM
    Author     : Parashar
--%>

<%@tag description="Overall page template" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@ attribute name="title" %>
<%@ attribute name="content" fragment="true" %>

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
    
        <title>${title} | HospitalDB</title>
    </head>
    <body>
        <div class="navbar navbar-default navbar-fixed-top" role="navigation">
            <div class="container">
              <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">HospitalDB</a>
              </div>
              <div class="collapse navbar-collapse">
                  <ul class="nav navbar-nav">
                      <li class="active"><a href="#">Home</a></li>
                  </ul>
              </div><!--/.nav-collapse -->
            </div>
        </div>
        
        <jsp:invoke fragment="content"/>
    </body>
</html>