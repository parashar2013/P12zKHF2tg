<%-- 
    Document   : _nav_tabs
    Created on : Mar 16, 2014, 12:06:12 AM
    Author     : Parashar
--%>

<%
    String activeTab = request.getParameter("active_tab");
    
    String homeTabClass = "";
    
    if (activeTab != null) {
        if (activeTab.equals("home")){
            homeTabClass = "class='active'";
        }
    }
    
%>

<li <%=homeTabClass%>><a href="${context}/FO/home">Home</a></li>
