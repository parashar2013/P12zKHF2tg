<%-- 
    Document   : _nav_tabs
    Created on : Mar 16, 2014, 12:06:12 AM
    Author     : Parashar
--%>

<%
    String activeTab = request.getParameter("active_tab");
    
    
    String homeTabClass = "", insertTabClass = "", searchTabClass = "";
    
    if (activeTab.equals("home")){
        homeTabClass = "class='active'";
    } else if (activeTab.equals("insert-record")) {
        insertTabClass = "class='active'";
    } else if (activeTab.equals("search")) {
        searchTabClass = "class='active'";
    }
    
%>

<li <%=homeTabClass%>><a href="${context}/doctor/home">Home</a></li>
<li <%=insertTabClass%>><a href="${context}/doctor/insert-record">Insert Record</a></li>
<li <%=searchTabClass%>><a href="${context}/doctor/search">Search</a></li>
