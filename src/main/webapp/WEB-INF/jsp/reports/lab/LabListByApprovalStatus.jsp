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
	
	<title>Lab List By Approval Status</title>
  	<style>
		.ui-jqgrid .ui-jqgrid-htable th div {overflow: hidden; position:relative; height:auto;white-space: pre-line;}
	/* 	.ui-jqgrid-btable tr td{text-align:center !important;} */
	</style>
</head>

<body class="labListByApporvalStatus" bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../../common/Menu.jsp" %>
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="labListByApporvalStatus"><a href="#labListByApporvalStatusTab">Lab List By Approval Status</a></li>				
					</ul>
					
 					<div id="labListByApporvalStatusTab" style="clear: both; height: 750px;">
						<div id="labOpsRptTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div id="labListByApprovalStatusCriteria" class="pT10 pB20">
							<form action="getLabListByApprovalStatus" id="loadListOfLabByApprovalStatusData">
		
									<div class="floatLeft">
										<font class="standardInputTitle">&nbsp; Approval Status :</font> 
										<select name="rptApprovalStatus" id="rptApprovalStatus" class="required" title="Approval Status">
											<option  value="0" >All</option>
											<c:forEach items="${approvalStatusList}" var="approvalStatus">
												<option value="${approvalStatus.key}" >${approvalStatus.value}</option>
											</c:forEach>
										</select>
									</div>	
									
										<div class="mL50 floatLeft">
											<input type="button" onclick="getLabListByApprovalStatusData();" title="List of Lab by Approval Status" value="List of Lab by Approval Status"  id="loadListOfLabByApprovalStatus">
										</div>								
							</form>
						</div>

						<div id="labListByApporvalStatusRpt" class="container displayNone">
 							<div class="example-header">
 								<label class="standardInputTitle pb10 mL2">Quick Search</label>
								<input type="text" id="filter-text-box" class="standardInputText" placeholder="Search in data..." oninput="onFilterTextBoxChanged()">
 								<button onclick="onBtExport()" style="margin-bottom: 5px; font-weight: bold;float: right;">Export to Excel</button>
							</div>			
 							<div id="myGrid" style="height: 650px; width:1760px;" class="ag-theme-balham"></div>						
						</div>						
					</div>

				</div>		
		</div> 
	</div>	
		
    <%@ include file="../../common/commonScriptsIncludes.html" %>
    <%@ include file="../../common/commonagGridScriptsIncludes.html" %>
    <script src="${staticPath}/js/main.js"></script>
		<script src="${staticPath}/js/reports/lab/labOpsReports.js?v=${buildDate}"></script>
	  <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	  <script src="${staticPath}/js/common/agGridLicense.js?v=${buildDate}"></script>
</body>
</html>