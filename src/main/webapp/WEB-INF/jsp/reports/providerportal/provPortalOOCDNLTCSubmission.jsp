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
	<link href="${staticPath}/css/common/datePicker/bootstrap.css" rel="stylesheet">  
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	
	<title>OOC/DNLTC Submissions Report</title>
  	<style>
		.ui-jqgrid .ui-jqgrid-htable th div {overflow: hidden; position:relative; height:auto;white-space: pre-line;}
		.ui-jqgrid-btable tr td{text-align:center !important;}
	</style>
</head>

<body bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../../common/Menu.jsp" %>
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="listProvPortalOOCDNLTCRptTab"><a href="#provPortalOOCDNLTCRptTab">OOC/DNLTC Submissions Report</a></li>		
						<li id="listPersonViewTab" class="displayNone"><a href="#personDetailsTab" >Recent Demo View</a></li>
						<li id="listDocumentViewTab" class="displayNone"><a href="#documentDetailsTab" >Document Timeline</a></li>	
					</ul>
					
 					<div id="provPortalOOCDNLTCRptTab" style="clear: both; height: 750px;">
						<div id="provPortalRptTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div id="provPortalOOCDNLTCRptCriteria" class="pT10 pB20">
							<form action="getProvPortalOOCDNLTCRptList" id="loadProvPortalOOCDNLTCRptData">
		
									<div id="dateRange" class="floatLeft" style="height:25px">
										<font class="standardInputTitle" id="workersFont" >Date: </font>
										<font class="standardInputTitle" id="workersFont" >From: </font>
										<input id="fromDate" class="required  date-picker"  readonly/>
										<font class="standardInputTitle" id="workersFont" >To: </font>
										<input id="toDate" class=" required date-picker" readonly/>
									</div>
									
									<div class="floatLeft">
										<font class="standardInputTitle">&nbsp; File Type :</font> 
										<select name="portalFileType" id="portalFileType" class="required" title="File Type" onchange="changeFileType(this)">
											<option  value="" >Select</option>
											<option  value="ALL" >ALL</option>														
											<option  value="O" >OOC</option>	
											<option  value="D" >DNLTC</option>
										</select>
										<div class="pL10 v_error portalFileType"></div>
									</div>									
 
									<div class="floatLeft displayNone" id="providerNameSection">
										<font class="standardInputTitle" id="portalProviderNames">&nbsp; Provider Names :</font> 
										<select name="portalProvider" id="portalProvider" class="required" title="Provider Name"  onchange="changeProviderName()" >
											<option  value="" >Select</option>
										</select>
									</div>									
 									
										<div class="mL50 floatLeft">
											<input type="button" onclick="getProvPortalOOCDNLTCRptData();" title="Provider Portal OOC/DNLTC Submission Report" value="OOC/DNLTC Submission Report"  id="loadProvPortalOOCDNLTCRpt">
											<input type="button" onclick="clearProvPortalOOCDNLTCData();" title="Clear" value="Clear">											
										</div>								
								</form>
								
								
						</div>
						
						<div id="provPortalOOCDNLTCReport" class="container displayNone">
 							<div class="example-header">
 								<label class="standardInputTitle pb10 mL2">Quick Search</label>
								<input type="text" id="filter-text-box" class="standardInputText" placeholder="Search in data..." oninput="onFilterTextBoxChanged()">
							</div>			
 							<div id="myGrid" style="height: 300px; width:1660px;" class="ag-theme-balham"></div>	
 							
 							<div><label class="standardInputTitle pb10 mL2">Care Status (Shared with Provider)</label></div>
 							<div class="example-header">
 								<label class="standardInputTitle pb10 mL2">Quick Search</label>
								<input type="text" id="filter-text-box2" class="standardInputText" placeholder="Search in data..." oninput="onFilterTextBoxChanged2()">
							</div>		 							
 							<div id="myGrid2" style="height: 300px; width:1660px;" class="ag-theme-balham"></div>						
						</div>									
										
					</div>

					<div id="personDetailsTab" style="clear: both; overflow: scroll">

					</div>
					<div id="personTableContainerModal">
						<table id="personFieldValuesTbl"></table>
					</div>
			
					<div id="documentDetailsTab" style="clear: both;">
					
						<div id="associateDocToStatenoTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<form name="documentForm" class="documentDetails">
							<div id="documentDetailsDiv">
								<table id="documentDetails"></table>
								<div id="documentDetailsPagerDiv"></div>
							</div>
						</form>
					</div>
					
				</div>		
		</div> 
	</div>	
	
	<div id="printDialog" title="Print Preview?" class="displayNone">
		
		<div id="printDialogContent" style="background-color:white;" class="tableReferenceDetails">
		
		</div>
	</div>		
	
	<div id='docViewDlg'>  </div>
		
    <%@ include file="../../common/commonScriptsIncludes.html" %>
    <%@ include file="../../common/commonagGridScriptsIncludes.html" %>            
    	<script src="${staticPath}/js/main.js"></script>
		<script src="${staticPath}/js/reports/provPortal/provPortalFileSubmissionRpt.js?v=${buildDate}"></script>
	  	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	  	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
		<script src="${staticPath}/js/search/search.js?v=${buildDate}"></script>
		<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>	  	
</body>
</html>