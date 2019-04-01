<%-- 
    Document   : index
    Created on : 6 Mar, 2018, 11:21:22 AM
    Author     : vishal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Auditorium and Room Booking</title>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
        
         <link rel="stylesheet" href="indexCSS.css"> 
    </head>
    <body>
        <%
            if(!(session.getAttribute("userid")==null || session.getAttribute("userid").equals(""))){
                int type=Integer.parseInt(String.valueOf(session.getAttribute("type")));
                if(type==0)
                    response.sendRedirect("applicant.jsp");
                else if(type==1)
                    response.sendRedirect("authority.jsp");
                else if(type==2)
                    response.sendRedirect("av_cell.jsp");
                else if(type==3)
                    response.sendRedirect("department.jsp");
                else if(type==4)
                    response.sendRedirect("security.jsp");
                
                
            }
        %>
        <h1 align="center">Auditorium and Room Booking System</h1>
    <div class="container">
		<div class="row">
			<div class="panel panel-primary">
				<div class="panel-body">
					<form method="POST" action="Login.jsp" role="form">
						<div class="form-group">
							<h2>Sign in</h2>
						</div>
						<div class="formEnter"> Username
							<input id="signinEmail" type="text" maxlength="50" class="form-control" name="username">
						</div>
						<div class="form-group">
							Password
							<input id="signinPassword" type="password" maxlength="25" class="form-control" name="password">
						</div>
                                                <br/>Login As:
                                                <div class="radio">
                                                    <label><input type="radio" name="type" value="0" checked>Applicant</label>
                                                </div>
                                                <div class="radio">
                                                    <label><input type="radio" name="type" value="1">Authority</label>
                                                </div>
                                                <div class="radio">
                                                    <label><input type="radio" name="type" value="2">AV Cell</label>
                                                </div>
                                                <div class="radio">
                                                    <label><input type="radio" name="type" value="3">Department Office</label>
                                                </div>
                                                <div class="radio">
                                                    <label><input type="radio" name="type" value="4">Security</label>
                                                </div>
                                                <div class="form-group" style="padding-top: 12px;">
                                                    <button id="signinSubmit" type="submit" class="btn btn-success btn-block">Sign in</button>
                                                </div>
						
					</form>
				</div>
			</div>
		</div>
	</div>
        
    </body>
</html>
