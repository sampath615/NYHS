<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<script>
	window.onload = formSubmit;
	function formSubmit() {
		document.forms[0].elements["browserName"].value = navigator.appName;
		document.forms[0].submit();
	}
</script>
</head>
<body>
        <FORM ACTION="index2.jsp" METHOD="POST">
            <INPUT TYPE="HIDDEN" NAME="browserName" VALUE="">
        </FORM>
</body>
</html>