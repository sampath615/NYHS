<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Submit CLEP Lab Results</title>
<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<%@ include file="../common/MainMenuIncludes.jsp"%>

<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/reports/labReports.css?v=${buildDate}" rel="stylesheet" type="text/css" />

</head>

<body>
	<%@ include file="../common/Menu.jsp"%>
	<div id="commonTemplate" class="pageView">
		<ul id="submitCLEPResultTabs">
			<li><a href="#submitCLEPResultContent"
				id="submitCLEPResultTab">Enter Audit Form</a></li>

		</ul>
		<div id="submitCLEPResultContent" style="padding: 1em;height: 1225px;" >

			<div id="clepLabResultData" class="pB20" >
			
			<div style="padding-left:26%; " class="headingCls">
                               CLEP Audit - Laboratory Reporting of HIV-Related Results Tested On-Site
            </div>
            
            <%@ include file="../lab/CLEPLabResultsForm.jsp"%>

			</div>		
			
		
		</div>

	</div>
   
   <div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
   </div>
	
	<%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/lab/CLEPLabResults.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>	
	<script src="${staticPath}/js/common/html2canvas.js"></script>
	<script src="${staticPath}/js/common/jspdf.min.js"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>	
</body>