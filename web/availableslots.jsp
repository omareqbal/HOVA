<%-- 
    Document   : authorityapproval
    Created on : 31 Mar, 2019, 3:59:40 PM
    Author     : omar
--%>
<%@page import="Source.Booking,java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
           String room_id=request.getParameter("room_id");
           String date=request.getParameter("date");
           List<String> slots=Booking.getAvailableSlots(room_id,date);
        %>
        <% for(String s:slots){
        %>
        <input type="checkbox" name="avail_slot" value="<%=s%>"><%=s%>
        <%
            }
        %>
    </body>
</html>
