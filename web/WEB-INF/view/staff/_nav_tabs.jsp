<%-- 
    Document   : _nav_tabs
    Created on : Mar 16, 2014, 12:06:12 AM
    Author     : Parashar
--%>

<%
    String activeTab = request.getParameter("active_tab");
    
    String homeTabClass = "", assignTabClass = "";
    
    if (activeTab != null) {
        if (activeTab.equals("doctor")){
            homeTabClass = "class='active'";
        } else if (activeTab.equals("appointment")) {
            assignTabClass = "class='active'";
        }
    }
    
%>

<li <%=homeTabClass%>><a href="${context}/staff/doctor_home">Doctors</a></li>
<li <%=assignTabClass%>><a href="${context}/staff/doctor_home">Assign</a></li>
