<%-- 
    Document   : _nav_tabs
    Created on : Mar 16, 2014, 12:06:12 AM
    Author     : Parashar
--%>

<%
    String activeTab = request.getParameter("active_tab");
    
    String homeTabClass = "", insertTabClass = "", searchTabClass = "", searchPatientsTabClass = "";
    
    if (activeTab != null) {
        if (activeTab.equals("home")){
            homeTabClass = "class='active'";
        } else if (activeTab.equals("insert-record")) {
            insertTabClass = "class='active'";
        } else if (activeTab.equals("search")) {
            searchTabClass = "class='active'";
        } else if (activeTab.equals("search-patients")) {
            searchPatientsTabClass = "class='active'";
        }
    }
    
%>

<li <%=homeTabClass%>><a href="${context}/doctor/home">Current Patients</a></li>
<li <%=searchPatientsTabClass%>><a href="${context}/doctor/search-patients">Search Patients</a></li>
<li <%=insertTabClass%>><a href="${context}/doctor/insert-record">Insert Visit Record</a></li>
<li <%=searchTabClass%>><a href="${context}/doctor/search">Search Visits</a></li>
