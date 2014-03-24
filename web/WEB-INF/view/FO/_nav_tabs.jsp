<%-- 
    Document   : _nav_tabs
    Created on : Mar 16, 2014, 12:06:12 AM
    Author     : Parashar
--%>

<%
    String activeTab = request.getParameter("active_tab");
    
    String dTabClass = "", vTabClass = "";
    
    if (activeTab != null) {
        if (activeTab.equals("doctor")){
            dTabClass = "class='active'";
        } else if (activeTab.equals("visit")) {
            vTabClass = "class='active'";
        }
    }
    
%>

<li <%=dTabClass%>><a href="${context}/FO/home">Spy on Doctors</a></li>
<li <%=vTabClass%>><a href="${context}/FO/visit">Spy on Visits</a></li>
