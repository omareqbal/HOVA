<%-- 
    Document   : applicant
    Created on : 30 Mar, 2019, 6:14:56 PM
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
            if(Integer.parseInt(String.valueOf(session.getAttribute("type")))!=0){
                response.sendRedirect("index.jsp");
            }
        %>
        <div class='container'>
            <br/>
            <div class="row">
                <div class="col-md">
                    <h3>Welcome Applicant, <%=session.getAttribute("name") %> ( <%=session.getAttribute("userid") %> )</h3>
                </div>
                <div class="col-sm">
                    <a class="btn btn-primary right" href="Logout.jsp">Logout</a>
                </div>
            </div>
                <br/>
        <div class='row'>
            <h4>New Booking</h4>
        </div>
        
        <br/>
        
        <%
            List<String> rooms=Booking.getRooms();
        %>
        <div class="row">
        Select Room
        <select id="room" class='form-control'>
            <%
             for(String s:rooms){
             %>
             <option value="<%=s %>"><%=s %></option>
            <%
             }
            %>
        </select>
       
        </div>
        <br/>
         Select Date
        <div class='row'>
        <input id="datepicker" class="datepicker" data-date-format="mm/dd/yyyy" width="276" />
        </div>
        <br/>
        <div class='row'>
            <button onclick="showslots()" class='btn btn-primary'>Show Slots</button>
        </div>
        <br/>
        <div id='slots' class='row'>
            
        </div>
        <br/>
        <div class="row">
            <input type="checkbox" name="av_cell">AV Cell Required
        </div>
        <br/>
        <div class="row">
            <button class="btn btn-primary" onclick="book()">Book</button>
        </div>
        <br/>
        <div class='row'>
            <h4>Previous Bookings</h4>
            <% List<Booking> res=Booking.getBookingsForApplicant(String.valueOf(session.getAttribute("userid"))); %>
             
            <table class="table table-bordered" >
            <thead>
              <tr>
                <th scope="col">Booking ID</th>
                <th scope="col">Slot</th>
                <th scope="col">Room ID</th>
                <th scope="col">Floor</th>
                <th scope="col">Building</th>
                <th scope="col">Dept Approval</th>
                <th scope="col">Authority Approval</th>
                <th scope="col">AV Cell Approval</th>
                <th scope="col">Security Approval</th>
                
              </tr>
            </thead>
            <tbody>
            <% for(Booking b:res){
                String slot=Booking.getSlot(b.booking_id);
            %>
                 <tr>
                    <td><%=b.booking_id %></td>
                    <td><%=slot %></td>
                    <td><%=b.room_id %></td>
                    <td><%=b.floor %></td>
                    <td><%=b.building %></td>
                    <td><%=b.dept %></td>
                    <td><%=b.authority %></td>
                    <td><%=b.av_cell %></td>
                    <td><%=b.security %></td>

                  </tr>

             <%  }
            %>
        </div>
    </div>
         <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6/jquery.min.js" type="text/javascript"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="Stylesheet" type="text/css" />
     <script>
        $("#datepicker").datepicker();
        function showslots(){
            
            room=$('#room').val();
            date=$('#datepicker').val();
            if(date=="")
                alert("Select a date.");
            else{
                $.ajax({
                    url:"availableslots.jsp",
                    type:'POST',
                    data:{"room_id":room,"date":date},
                    datatype:"html",
                    success:function(response){
                        $("#slots").html(response);
                    }
                });
            }
            
        }
        
        function book(){
            var slots=[];
            $("input:checkbox[name='avail_slot']:checked").each(function(){
                slots.push($(this).val());
            });
            time_slots=slots.join(",");
            room=$('#room').val();
            date=$('#datepicker').val();
            av_cell=0;
            if($("input[name='av_cell']").is(':checked'))
                av_cell=1;
            if(date=="")
                alert("Select a date.");
            else if(time_slots=="")
                alert("Select a slot.");
            else{
                $.ajax({
                    url:'book.jsp',
                    type:'POST',
                    data:{'room_id':room,'date':date,'time_slots':time_slots,"av_cell":av_cell},
                    success:function(){
                        alert('Booked.');
                        location.reload();
                    }
                });
            }
            
        }
    </script>
    </body>
</html>
