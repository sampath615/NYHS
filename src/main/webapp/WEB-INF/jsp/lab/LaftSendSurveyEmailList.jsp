<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
	<meta http-equiv="cache-control" content="no-cache" />
	
	<%@ include file="../common/common_includes.jsp" %> 
    <%@ include file="../common/commonStylesIncludes.html" %> 
    <%@ include file="../common/MainMenuIncludes.jsp" %>
        
	<link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/assignments/assignments.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/docs/clusterForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	
	<title>LAFT Count Reconcile</title>
	
</head>

<body id="laftSendSurvey" class="laftSendSurveyEmail" bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../common/Menu.jsp" %>
		<div id="mainContentDiv"">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="listSendSurveyEmailTab"><a href="#sendSurveyEmailTab">Send Survey Email</a></li>			
					</ul>
 					
					<div id="sendSurveyEmailTab" style="width:1700px;height: 750px;">
						<div id="LAFTReconcileTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div class="container" style="width:1300px; float:left;">
							
							<div class="example-header">
								<label class="standardInputTitle pb10 mL2">Quick Search</label>
								<input type="text" id="filter-text-box4" class="standardInputlabText1 float: right;" placeholder="Search in data..." oninput="onFilterTextBoxChanged4()">
								<label>Reconciliation Period</label>
								<input id="reconciliationPeriod" class="mL10 standardInputText readonly" 
										style="border: none; font-weight: bold;float: center;background-color: #f3f3ec00 !important" value=""/>
								<button onclick="onBtExport4()" style="margin-bottom: 5px; font-weight: bold;float: right;">Export to Excel</button>
							</div>						
							<div id="myGrid4" style="height: 700px; width:1300px;" class="ag-theme-balham"></div>						
						</div>
						
						<div id="submitSurveyEmailParameters" class="container" style="width:380px; float:right;">
						
							<div>
								<label>Survey link</label>
								<input id="surveyLink" maxlength="80" class="mL10 standardInputText required" title="Survey link"/>
							</div>
							<br>
							
							<div>
								<label>Survey complete date</label>
								<input id="surveyCompleteDate" class="mL10 date-picker required" readonly="true" title="Survey complete date" />
							</div>													
							<br>
							
							<button id="sendSurveyEmail" onclick="sendSurveyEmailtoLab()" style="margin-bottom: 5px;margin-top: 10px; font-weight: bold;">Send Survey Email</button>										
						</div>					
											
					</div>

				</div>		
		</div> 
	</div>	
	
   <div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
   </div>		
	
    <%@ include file="../common/commonScriptsIncludes.html" %>
	<%@ include file="../common/commonagGridScriptsIncludes.html" %>     
    <script src="${staticPath}/js/main.js"></script>
	<script src="${staticPath}/js/lab/LAFTCountReconcile.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/search/search.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>	  
	
</body>
</html>