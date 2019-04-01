<%-- 
    Document   : authority.jsp
    Created on : 30 Mar, 2019, 6:08:35 PM
    Author     : vishal
--%>

<%@page import="Source.Booking,java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

        <title>Auditorium and Room Booking</title>
    </head>
    <body>
        <%
            if(session.getAttribute("userid")==null || session.getAttribute("userid").equals("")){
                response.sendRedirect("index.jsp");
            }
            if(Integer.parseInt(String.valueOf(session.getAttribute("type")))!=2){
                response.sendRedirect("index.jsp");
            }
        %>
        <div class="container">
            <br/>
            <div class="row">
                <div class="col-md">
                    <h4>Welcome AV Cell, <%=session.getAttribute("name") %> ( <%=session.getAttribute("userid") %> )</h4>
                </div>
                <div class="col-sm">
                    <a class="btn btn-primary right" href="Logout.jsp">Logout</a>
                </div>
            </div>
        <% List<Booking> res=Booking.getPendingForAVCell(); %>
        <br/>
        <h5>Pending Requests</h5> 
        <table class="table table-bordered table-hover" >
        <thead>
          <tr>
            <th scope="col">Booking ID</th>
            <th scope="col">Applicant ID</th>
            <th scope="col">Applicant Name</th>
            <th scope="col">Applicant Phone</th>
            <th scope="col">Applicant Email</th>
            <th scope="col">Slot</th>
            <th scope="col">Room ID</th>
            <th scope="col">Floor</th>
            <th scope="col">Building</th>
            <th scope="col">Action</th>
          </tr>
        </thead>
        <tbody>
        <% for(Booking b:res){
            String slot=Booking.getSlot(b.booking_id);
        %>
             <tr>
                <td><%=b.booking_id %></td>
                <td><%=b.applicant_id %></td>
                <td><%=b.applicant_name %></td>
                <td><%=b.applicant_phone %></td>
                <td><%=b.applicant_email %></td>
                <td style="width:200px;"><%=slot %></td>
                <td><%=b.room_id %></td>
                <td><%=b.floor %></td>
                <td><%=b.building %></td>
                <td><button onclick="actionByAVCell(<%=b.booking_id %>,'<%=session.getAttribute("userid") %>',1)" class="btn btn-success btn-block">Approve</button>
                  <%--  <button onclick="actionByAVCell(<%=b.booking_id %>,'<%=session.getAttribute("userid") %>',0)" class="btn btn-danger btn-block">Reject</button>--%>
</td>
                
              </tr>

         <%  }
        %>
        
    </tbody>
  </table>
        </div>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6/jquery.min.js" type="text/javascript"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="Stylesheet" type="text/css" />

               <script>
            function actionByAVCell(booking_id,user_id,action){
                $.ajax({
                    url:"avcellapproval.jsp",
                    type:"POST",
                    data:{"booking_id":booking_id,"user_id":user_id,"action":action},
                    success:function(response){
                        if(action==1)
                            alert("Booking Approved.");
                        else
                            alert("Booking Rejected.");
                        location.reload();
                       }
                });
            }
        </script>

    </body>
</html>
