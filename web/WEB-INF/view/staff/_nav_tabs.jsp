<%-- 
    Document   : _nav_tabs
    Created on : Mar 16, 2014, 12:06:12 AM
    Author     : Parashar
--%>

<%
    String activeTab = request.getParameter("active_tab");
    
    String homeTabClass = "", insertTabClass = "", searchTabClass = "";
    
    if (activeTab != null) {
        if (activeTab.equals("doctor")){
            homeTabClass = "class='active'";
        } else if (activeTab.equals("patient")) {
            insertTabClass = "class='active'";
        }
    }
    
%>

<li <%=homeTabClass%>><a href="${context}/staff/doctor_home.jsp">Doctors</a></li>
<li <%=insertTabClass%>><a href="${context}/staff/patient_home.jsp">Patients</a></li>
