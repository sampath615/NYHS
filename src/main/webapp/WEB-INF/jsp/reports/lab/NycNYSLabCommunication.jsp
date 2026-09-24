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
	
	<title>NYS-NYC Lab communication  Report</title>
  	<style>
		.ui-jqgrid .ui-jqgrid-htable th div {overflow: hidden; position:relative; height:auto;white-space: pre-line;}
	/* 	.ui-jqgrid-btable tr td{text-align:center !important;} */
	</style>
</head>

<body bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../../common/Menu.jsp" %>
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="listNycNysLabCommTab"><a href="#nycNysLabCommRptTab">NYS-NYC Lab communication  Report</a></li>				
					</ul>
					
 					<div id="nycNysLabCommRptTab" style="clear: both; height: 750px;">
						<div id="labOpsRptTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div id="nycNysLabCommRptCriteria" class="pT10 pB20">
							<form action="getNycNysLabCommRptList" id="loadNycNysLabCommRptData">
<!-- 
									<div class="floatLeft">
										<font class="standardInputTitle">&nbsp; NYC Service Flag:</font> 
										<select name="nycServiceFlag" id="nycServiceFlag" class="required" title="NYC Service Flag">
											<option  value="" >Select</option>
											<option  value="Y" >Yes</option>
											<option  value="N" >No</option>
										</select>
									</div>		
 -->											
									<div id="dateRange" class="floatLeft" style="height:25px">
										<font class="standardInputTitle" id="workersFont" >Communication Dates: </font>
										<font class="standardInputTitle" id="workersFont" >From: </font>
										<input id="fromDate" class="required  date-picker"  readonly/>
										<font class="standardInputTitle" id="workersFont" >To: </font>
										<input id="toDate" class=" required date-picker" readonly/>
									</div>
									
										<div class="mL50 floatLeft">
											<input type="button" onclick="getNycNysCommRptDataAg('Y');" title="Communication Report" value="Lab Communication Report"  id="loadNycNysLabCommRpt">
											<input type="button" onclick="clearNycNysCommData();" title="Clear" value="Clear">											
										</div>								
								</form>
								
						</div>
							
						<div id="nycNysLabCommRpt" class="container displayNone">
 							<div class="example-header">
 								<label class="standardInputTitle pb10 mL2">Quick Search</label>
								<input type="text" id="filter-text-box" class="standardInputText" placeholder="Search in data..." oninput="onFilterTextBoxChanged()">
								<label class="standardInputTitle pb10 mL10">NYC Service Flag</label>
								<input type="checkbox" name="showHideNYCServiceLab" id="showHideNYCServiceLab" onclick="showHideshowHideNYCServiceLab()"/>
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
</body>
</html>