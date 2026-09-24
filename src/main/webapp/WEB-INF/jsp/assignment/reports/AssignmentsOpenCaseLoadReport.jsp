<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
	<meta http-equiv="cache-control" content="no-cache" />
	
	<%@ include file="../../common/common_includes.jsp" %> 
    <%@ include file="../../common/commonStylesIncludes.html" %> 
    <%@ include file="../../common/MainMenuIncludes.jsp" %>
        
	<link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/assignments/assignments.css?v=${buildDate}" rel="stylesheet" type="text/css" />	
	
	<title>Open Caseload</title>
  	<style>
		.ui-jqgrid .ui-jqgrid-htable th div {overflow: hidden; position:relative; height:auto;white-space: pre-line;}
		.ui-jqgrid-btable tr td{text-align:center !important;}
	</style>
</head>

<body class="openCaseLoadReport" bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../../common/Menu.jsp" %>
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="openCaseLoadReportId"><a href="#openCaseLoadReportTab">Open Caseload</a></li>				
					</ul>
					
 					<div id="openCaseLoadReportTab" style="clear: both; height: 880px;">
						<div id="openCaseLoadTransactionMessage" class="transactionMessage"><span class="message"></span></div>
							
						
						<div class="container">
 							<div>
								<button onclick="onBtExport()" style="margin-bottom: 5px; font-weight: bold;">Export to Excel</button>
							</div>
						<div id="loadOpenCaseloadGrid" style="height: 642px; width:1700;" class="ag-theme-balham"></div>						
						</div>
						<div><br>*Subset of open assignments<br>^based on worker assignment date; ^^based on date opened in the system
						<br>**Stas is a Central Office worker who is currently assisting with New Rochelle surveillance
						<br>Staff also have MMP responsibilities<br>+ County staff<br><br>
						Assignment- a request sent to field staff to conduct medical record review. The goal is to ascertain the individual's HIV status and/or obtain missing information required for National case reporting for confirmed cases. An assignment will be created if: a) we have received a new report to the registry on an individual with an unknown HIV status, or b) a confirmed case is missing key information.
						<br><br>
						Assignments on records that do not meet cases definition can be generated if, for example, we receive an incomplete algorithm or non-detectable viral load. In this scenario there is the possibility that the individual is a case; a review is necessary to avoid missing any suspected.					
						<br>
						- Cases being held for Central Office review are incomplete algorithm assignments based on a negative Step 2 result received in isolation. These were flagged for review and followup with providers to educate on correct ordering of the algorithm
						<br>
						Created 25MAR19						
						</div>
				</div>		
		</div> 
	</div>	
	</div>
    <%@ include file="../../common/commonScriptsIncludes.html" %>
    <%@ include file="../../common/commonagGridScriptsIncludes.html" %>
    <script src="${staticPath}/js/main.js"></script>
	<script src="${staticPath}/js/assignments/assignmentOpenCaseloadReport.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body>
</html>