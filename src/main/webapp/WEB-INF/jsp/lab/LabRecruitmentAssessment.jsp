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

	<title>Lab Recruitment Assessment</title>

</head>

<body class="labRecruitmentAssessment" bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">
	<div id="floatContent"  style="background-color: transparent; height: 700px;" >
		<%@ include file="../common/Menu.jsp"%>
		
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="labRecruitmentAssessmentTab"><a href="#labRecruitmentAssessment">Lab Recruitment Assessment</a></li>	
						<li id="editClepLabResultTab" class="displayNone"><a href="#editClepLabResult">Review CLEP Audit</a></li>					
						<li id="assignLabRecruitmentAssessmentTab" class="displayNone"><a href="#assignLabRecruitmentAssessment">Assign Lab Recruitment Assessment</a></li>			
					</ul>
					
 					<div id="labRecruitmentAssessment" style="clear: both;">
				 		<div id="submitCLEPResultTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div class="container">
							<div class="example-header">
								<label class="standardInputTitle pb10 mL2">Quick Search</label>
								<input type="text" id="filter-text-box" class="standardInputlabText1 float: right;" placeholder="Search in data..." oninput="onFilterTextBoxChanged()">
								<button onclick="onBtExport()" style="margin-bottom: 5px; font-weight: bold;float: right;">Export to Excel</button>
							</div>						
							<div id="myGrid" style="height: 700px; width:1760px;" class="ag-theme-balham"></div>						
						</div>
					</div>	
					<div id="editClepLabResult" style="clear: both;height: 1200px;">
 				 		<div id="editClepLabResultData"></div>		
					</div>		
 					<div id="assignLabRecruitmentAssessment" style="clear: both;height: 1200px;">
 				 		<div id="editLabRecruitmentAssessmentData"></div>		
					</div>		 
			</div> 
		</div>		

	</div>
	
   <div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
   </div>	
   
	<%@ include file="../common/commonScriptsIncludes.html"%>
	<%@ include file="../common/commonagGridScriptsIncludes.html" %>
 	<script src="${staticPath}/js/main.js"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/lab/CLEPLabResults.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>	
	<script src="${staticPath}/js/common/html2canvas.js"></script>
	<script src="${staticPath}/js/common/jspdf.min.js"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>	
</body>