<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<link href="${staticPath}/css/mainmenu/search.css?v=${buildDate}"   rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
<head>
<title>Experimental Page 3</title>
</head>
<h1>Experimental Page 3</h1>
<body>
	<div class="floatRight">
			<span class="mR20 bold"><a href="${contextPath}/web/getAssignmentsTally">Application Home</a></span>
			<span class="mR20 bold"><a href="${contextPath}/web/manualQuartzJob">Manual Job</a></span>
			<span class="mR20 bold"><a href="${contextPath}/web/experimentHome">Experiment Home</a></span>
	</div>	
<%@ include file="../common/commonScriptsIncludes.html"%>
<script src="${staticPath}/js/scheduler/schedulerMetaData.js?v=${buildDate}"></script>
<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body>

