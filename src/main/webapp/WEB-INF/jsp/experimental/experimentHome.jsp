<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<link href="${staticPath}/css/mainmenu/search.css?v=${buildDate}"   rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
<head>
<title>Experimental Pages</title>
</head>
<h1>Experimental Developer-Only Pages</h1>
<body>
	<div class="floatRight">
			<span class="mR20 bold"><a href="${contextPath}/web/getAssignmentsTally">Application Home</a></span>
			<span class="mR20 bold"><a href="${contextPath}/web/manualQuartzJob">Manual Job</a></span>
	</div>	
	<table>
	<tr>
	  <th>Experimental<br>Application<br>Name</th>
	  <th>Description</th>
	</tr>
	<tr>
	  <td><a href="experiment1">Experiment 1</a></td>
	  <td>Process Validation Reports</td>
	</tr>
	<tr>
	  <td><a href="${contextPath}/web/experiment2">Experiment 2</a></td>
	  <td>CCD Table Explorer</td>
	</tr>
	<tr>
	  <td><a href="${contextPath}/web/experiment3">Experiment 3</a></td>
	  <td>Spring MVC Explorations</td>
	</tr>
	<tr>
	  <td><a href="${contextPath}/web/hitracecodemapper">HIT RACE CODE MAPPER</a></td>
	  <td>HIT Race Code Mapper</td>
	</tr>
	<tr>
	  <td><a href="${contextPath}/web/hiterrorreprise">HIT ERROR REPRISE</a></td>
	  <td>HIT Error Reprise Page</td>
	</tr>
	<tr>
	  <td><a href="getCcdTableRows">Get CCD Table Row Counts</a></td>
	  <td>Real CCD Table Explorer</td>
	</tr>
	<!--
	<tr>
	  <td><a href="${contextPath}/web/simpleManualQuartzJob">Manual Job (Simple Version)</a></td>
	  <td>How simple can it get?</td>
	</tr>
	-->
	<tr>
	  <td><a href="${contextPath}/web/simplestManualQuartzJob">Manual Job (Simple Version)</a></td>
	  <td>How simple can it get?</td>
	</tr>	
	</table>

<%@ include file="../common/commonScriptsIncludes.html"%>
<script src="${staticPath}/js/scheduler/schedulerMetaData.js?v=${buildDate}"></script>
<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body>
