<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<meta http-equiv="cache-control" content="no-cache" />
	
	<%@ include file="../common/common_includes.jsp" %> 
    <%@ include file="../common/commonStylesIncludes.html" %> 
    <%@ include file="../common/MainMenuIncludes.jsp" %>
        
	<link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/assignments/assignments.css?v=${buildDate}" rel="stylesheet" type="text/css" />	
	<title>${reportType} Reports</title>
</head>

<body bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png" class="${category}">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../common/Menu.jsp" %>
		<div id="mainContentDiv">		
		<div id="tabs">
			<ul id="Tabs">
				<li id="listMergesTab" ><a href="#mergesTab">${reportType} Reports</a></li>
			</ul>
			
			<div id="mergesTab" style="clear: both;">
				<div id="labReportsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				
					<div id="labReportCriteria" class="pT10 pB20">
						<form action="getMergeReports" id="loadMergeReportsData">
							<div id="createDateRange" class="floatLeft" style="height:25px">
								<font
									class="standardInputTitle" id="textToDate" >Date: </font>
									
								<font
									class="standardInputTitle" id="textToDate" >From: </font>
								<input id="fromDate"
									class="required  date-picker" title="From date" readonly />
									<font
									class="standardInputTitle" id="textToDate" >To: </font>
									<input id="toDate" class="required date-picker" title="To date" readonly />
							</div>
									
							<div class="mL50 floatLeft">
								<input type="button" onclick="getMergeReports('${category}');" title="Generate Reports" value="Generate Reports" id="loadMergeReports">
							</div>
						</form>
					</div>
					
					<div id="tableContents" class="clearBoth panel-body panel-body-table displayNone" style="text-align:center;">
					</div>								
				</div>		
			</div>											
		</div> 
	</div>	
	
		
		
		
    <%@ include file="../common/commonScriptsIncludes.html" %>
    <script src="${staticPath}/js/reports/mergeReports.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.fileDownload.js"></script>				
	  
	  		
</body>
</html>


