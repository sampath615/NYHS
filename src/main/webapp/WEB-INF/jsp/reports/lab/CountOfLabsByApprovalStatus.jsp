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
	
	<title>Count of Labs By Approval Status</title>
  	<style>
		.ui-jqgrid .ui-jqgrid-htable th div {overflow: hidden; position:relative; height:auto;white-space: pre-line;}
		.ui-jqgrid-btable tr td{text-align:center !important;}
	</style>
</head>

<body class="countOfLabsByApporvalStatus" bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../../common/Menu.jsp" %>
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="countOfLabsByApporvalStatus"><a href="#countOfLabsByApporvalStatusTab">Count Of Labs By Approval Status</a></li>				
					</ul>
					
 					<div id="countOfLabsByApporvalStatusTab" style="clear: both; height: 750px;">
						<div id="labOpsRptTransactionMessage" class="transactionMessage"><span class="message"></span></div>
							
<!-- 				   		<div id="countOfLabsByApporvalStatusRpt" class="floatLeft mT20 mL20 jqGridTableSection">
							<table id="countOfLabsByApporvalStatusTable"></table>
							<div id="countOfLabsByApporvalStatusPager"></div>
						</div>	 -->
						
						<div class="container">
 							<div>
								<button onclick="onBtExport()" style="margin-bottom: 5px; font-weight: bold;">Export to Excel</button>
							</div>
						<div id="myGrid" style="height: 600px; width:1600px;" class="ag-theme-balham"></div>						
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
</body>
</html>