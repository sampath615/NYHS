<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<title>Worker Metrics Reports</title>
	<%@ include file="../../common/common_includes.jsp" %> 
	<%@ include file="../../common/commonStylesIncludes.html" %> 
	<%@ include file="../../common/commonScriptsIncludes.html" %>
	<%@ include file="../../common/MainMenuIncludes.jsp" %> 

	<link href="${staticPath}/css/common/datePicker/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" media="screen" href="${staticPath}/css/common/datePicker/bootstrap-datetimepicker.css">
	<link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/jquerycombobox.css" rel="stylesheet" type="text/css" />
</head>



<body bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

		<div id="floatContent"  style="height: 100%;">
			<%@ include file="../../common/Menu.jsp" %>
			<%@ include file="workerMetricsSearchTabDetail.jsp"%>
		</div>

	    <script type="text/javascript" src="${staticPath}/js/common/jqBarGraph.1.1.js"></script>
	   

		<script src="${staticPath}/js/main.js"></script>
		<script src="${staticPath}/js/jquery/jquery.collapsible.js"></script>
		<script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
		<script src="${staticPath}/js/reports/workerMetrics/workerMetrics.js?v=${buildDate}"></script>
		<script type="text/javascript">
		  $(function() {
		    $('.effectiveDateTimePicker').datetimepicker({
		      language: 'en',
		      pickTime: false
		    });
		  });
		  $(document).ready(function() {
		  	var loadPageManageUserWithDefaultWorkerId = '<c:out value="${param.workerId}" />';
		  	if(loadPageManageUserWithDefaultWorkerId){
		  		manageUser(loadPageManageUserWithDefaultWorkerId);
		  	}
		  });	 
		</script> 	 
		<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
		<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
		<script type="text/javascript" src="${staticPath}/js/common/datePicker/bootstrap.js"></script>
		<script type="text/javascript" src="${staticPath}/js/common/datePicker/bootstrap-datetimepicker.js"></script>
</body>
</html>