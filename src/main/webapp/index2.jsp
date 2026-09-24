<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="javax.servlet.RequestDispatcher" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
</head>
<body>
         
<% 
RequestDispatcher dispatcher = request.getRequestDispatcher("web/appInitialize");
request.setAttribute("browserName", request.getParameter("browserName"));
dispatcher.forward(request, response);
%>
</body>
</html>






