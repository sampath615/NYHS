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

	<title>Search History Report</title>

</head>

<body class="followupHivSurvey" bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">
	<div id="floatContent"  style="background-color: transparent; height: 700px;" >
		<%@ include file="../common/Menu.jsp"%>
		
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="searchHistoryReportTab"><a href="#searchHistoryReport">Search History Report</a></li>	
					</ul>
					
 					<div id="searchHistoryReport" style="clear: both;">
				 		<div id="submitSearchHistoryTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				 		
						<div id="searchHistoryReportCriteria" class="pT10 pB20">
							<form action="getSearchHistoryReport" id="loadSearchHistoryReportData">
		
									<div class="floatLeft">
										<font class="standardInputTitle" id="workersFont">&nbsp;Search Type</font> 
										<select name="searchType" id="searchType" class="required" title="Search Type" onchange="changeSearchType(this)" >
											<option  value="" >Select</option>
											<option  value="All" >All</option>
											<option  value="dateRange" >Date Range</option>
										</select>
                   						<div class="pL10 v_error searchType"></div>											
									</div>
												
									<div id="dateRange" class="floatLeft" style="height:25px">
										<font class="standardInputTitle" id="textToDate" >Date From </font>
										<input id="fromDate" class="date-picker" value="" readonly/>
										<font class="standardInputTitle" id="textToDate" >To </font>
										<input id="toDate" class="date-picker" value=""readonly/>
									</div>
									
									<div class="floatLeft">
										<font class="standardInputTitle" id="workersFont">&nbsp;Worker</font> 
										<select name="worker" id="worker" class="required" title="Worker">
											<option  value="0" >All</option>										
											<c:forEach items="${workers}" var="worker">
												<option  value="${worker.key}" >${worker.value}</option>
											</c:forEach>
										</select>
                   						<div class="pL10 v_error worker"></div>											
									</div>
																		
									<div class="mL50 floatLeft">
										<input type="button" onclick="getSearchHistoryReportData();" title="Search History Report" value="Search History Report"  id="searchHistoryReportData">
									</div>								
							</form>
						</div>				 		
						
						<div id="searchHistoryReportDataGrid" class="container displayNone">
							<div class="example-header">
 								<label class="standardInputTitle pb10 mL2">Quick Search</label>
								<input type="text" id="filter-text-box" class="standardInputlabText1" placeholder="Search in data..." oninput="onFilterTextBoxChanged()">
 								<!-- <button onclick="onBtExport()" style="margin-bottom: 5px; font-weight: bold;float: right;">Export to Excel</button> -->
							</div>			
							<div id="myGrid" style="height: 600px; width:1500px;" class="ag-theme-balham"></div>						
						</div>
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
	<script src="${staticPath}/js/reports/searchHistoryReport.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>	
	<script src="${staticPath}/js/common/html2canvas.js"></script>
	<script src="${staticPath}/js/common/jspdf.min.js"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>	
</body>