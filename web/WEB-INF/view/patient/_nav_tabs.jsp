<%-- 
    Document   : _nav_tabs
    Created on : Mar 16, 2014, 12:06:12 AM
    Author     : Parashar
--%>

<%
    String activeTab = request.getParameter("active_tab");
    
    String homeTabClass = "", insertTabClass = "", searchTabClass = "";
    
    if (activeTab != null) {
        if (activeTab.equals("home")){
            homeTabClass = "class='active'";
        } else if (activeTab.equals("insert-record")) {
            insertTabClass = "class='active'";
        } else if (activeTab.equals("search")) {
            searchTabClass = "class='active'";
        }
    }
    
%>

<li <%=homeTabClass%>><a href="${context}/patient/home">Past Appointments</a></li>
<li <%=insertTabClass%>><a href="${context}/patient/profile">Update Profile</a></li>
