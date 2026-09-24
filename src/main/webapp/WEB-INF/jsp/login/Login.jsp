<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="/hpn/ctrldocs/nyehms/css/common/commoncss.css?v=1"  rel="stylesheet" type="text/css" />

<title>Login</title>
</head>

<br><br>

<center> NYEHMS Login </center>
    <div id="loginPageerrorMessage" class="alignCenter">
        <c:out value="${TRANSACTION_MESSAGE}" />
    </div>


    <form method=post action="/doh2/applinks/nyehms/web/login"> 
    <div id="loginPage">
        <div class="standardInputDiv">
            <div class="label110mrg">
                <span class="standardInputTitle">User*</span>
            </div>        
            <div class="floatLeft">
                <input type="text" name="user" style="width: 240px" />
            </div>   
        </div>
        <div class="standardInputDiv alignCenter">
            <input type="submit" value="Submit">
        </div>
    </div>
    
    
    </form>



</body>
</html>