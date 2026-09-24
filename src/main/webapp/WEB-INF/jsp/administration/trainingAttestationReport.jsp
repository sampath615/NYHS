<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<%@ include file="../common/MainMenuIncludes.jsp"%>

<link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/reports/labReports.css?v=${buildDate}" rel="stylesheet" type="text/css" />

	<title>C&S Attestation Report</title>

</head>

<body class="followupHivSurvey" bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">
	<div id="floatContent"  style="background-color: transparent; height: 700px;" >
		<%@ include file="../common/Menu.jsp"%>
		
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="adminModuleReportTab"><a href="#adminModuleReport">C&S Attestation Report</a></li>	
						<li id="userDetailsTab" class="displayNone"><a href="#userDetails">User Details</a></li>	
					</ul>
					
 					<div id="adminModuleReport" style="clear: both;">
				 		<div id="adminModuleReportTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				 		
<!-- 						<div id="adminModuleReportCriteria" class="pT10 pB20"> -->
<%-- 							<form action="getAdminModuleReport" id="loadAdminModuleReportData"> --%>
		
<!-- 									<div id="dateRange" class="floatLeft" style="height:25px"> -->
										
<!-- 										<span id="adminDateRange" > -->
										
<!-- 											<font class="standardInputTitle mL10" id="textToDate" >Date: </font> -->
<!-- 											<font class="standardInputTitle" id="textToDate" >From: </font> -->
<!-- 											<input id="fromDate" class="required  date-picker" value="" readonly/> -->
<!-- 											<font class="standardInputTitle" id="textToDate" >To: </font> -->
<!-- 											<input id="toDate" class="required date-picker" value="" readonly/> -->
<!-- 										</span> -->
<!-- 									</div> -->
									
<!-- 									<div class="mL50 floatLeft" id="adminReportBtn"> -->
<!-- 										<input type="button" onclick="getAdminModuleReportData();" title="Generate Report" value="Generate Report"  id="adminModuleReportData"> -->
<!-- 									</div>								 -->
<%-- 							</form> --%>
<!-- 						</div>				 		 -->
						
						<div id="adminModuleReportDataGrid" class="container displayNone">
 							<div class="example-header">
 								<label class="standardInputTitle pb10 mL2">Quick Search</label>
								<input type="text" id="filter-text-box" class="standardInputlabText1" placeholder="Search in data..." oninput="onFilterTextBoxChanged()">
 								<button onclick="onBtExport()" style="margin-bottom: 5px; font-weight: bold;float: right;">Export to Excel</button>
							</div>			
 							<div id="myGrid" style="height: 650px; width:1760px;" class="ag-theme-balham"></div>						
						</div>
					</div>
					
					<div id="userDetails" style="clear: both;height: 1500px;">
 				 		<div id="userDetailsData"></div>		
					</div>							
	 
				</div> 
		</div>		

	</div>
	
   <div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
   </div>	
   
	<%@ include file="../common/commonScriptsIncludes.html"%>
	<%@ include file="../common/commonagGridScriptsIncludes.html"%>
 	<script src="${staticPath}/js/main.js"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
 	<script src="${staticPath}/js/administration/trainingAttestationReport.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>	
	<script src="${staticPath}/js/common/html2canvas.js"></script>
	<script src="${staticPath}/js/common/jspdf.min.js"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>	
</body>