<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<title>Person Search</title>
	<%@ include file="../../common/common_includes.jsp" %> 
	<%@ include file="../../common/commonStylesIncludes.html" %> 
	<%@ include file="../../common/MainMenuIncludes.jsp" %> 
	
	<link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />	
	
</head>

<body bgcolor="#e4e4e4" id="personSearch" background="${staticPath}/images/NYEHMSVirusBackground.png" class="search">

		<div id="floatContent"  style="height: 100%;">
			<%@ include file="nbsSearchTabDetail.jsp" %>
		</div>



			
		
	   
				
		
</body>
</html>

