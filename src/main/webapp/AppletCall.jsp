<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:useBean id="userInitObj" scope="session" class="gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.UserInitObject"></jsp:useBean>
<title>Applet Call</title>
</head>

<body>

<applet code="NyehmsSecuredApplet.class" archive="nyehmssecappj.jar" width="200" height="100" name="nyehmsApplet" MAYSCRIPT>

	<param name="userID" value=${userInitObj.userId}>
	<param name="userEnv" value=${userInitObj.userEnv}>
	<param name="urlAddress" value=	"web/appInitializeContinue">

</applet>
</body>

</html>