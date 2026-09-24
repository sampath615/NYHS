<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<title>User Guide</title>
	<%@ include file="../common/common_includes.jsp" %> 
	 
	<%@ include file="../common/commonScriptsIncludes.html" %>

	<link href="${staticPath}/css/jquerycombobox.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/userguide/userGuide.css" rel="stylesheet" type="text/css" />
	<script src="${staticPath}/js/userguide/webUserGuide.js?v=${buildDate}"></script>
</head>

<body bgcolor="#fff">

	<div id="floatContent"  style="height: 95%;">		
		<%@ include file="webUserGuide.jsp"%>
	</div>		
	
</body>
</html>