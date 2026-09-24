<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<!-- A comment to, hopefully, get the server to recompile the JSP -->
<link href="${staticPath}/css/common/datePicker/bootstrap.css" rel="stylesheet">
<title>Raw CCD Table Explorer</title>
</head>
<h1>Raw CCD Table Explorer</h1>
Hi there from experiment2.jsp!
<body>
	<div class="floatRight">
			<span class="mR20 bold"><a href="${contextPath}/web/getAssignmentsTally">Application Home</a></span>
			<span class="mR20 bold"><a href="${contextPath}/web/manualQuartzJob">Manual Job</a></span>
			<span class="mR20 bold"><a href="${contextPath}/web/experimentHome">Experiment Home</a></span>
	</div>	
<jsp:include page="/web/getCcdTable.html"/>
<script src="${staticPath}/js/scheduler/schedulerMetaData.js?v=${buildDate}"></script>
<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body>

