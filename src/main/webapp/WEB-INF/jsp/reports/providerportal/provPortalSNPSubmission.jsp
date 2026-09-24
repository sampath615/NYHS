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
	
	<title>SNP Submissions Report</title>
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
						<li id="listProvPortalSNPRptTab"><a href="#provPortalSNPRptTab">SNP Submissions Report</a></li>				
					</ul>
					
 					<div id="provPortalSNPRptTab" style="clear: both; height: 750px;">
						<div id="provPortalRptTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div id="provPortalSNPRptCriteria" class="pT10 pB20">
							<form action="getProvPortalSNPRptList" id="loadProvPortalSNPRptData">
		
									<div id="dateRange" class="floatLeft" style="height:25px">
										<font class="standardInputTitle" id="workersFont" >Date: </font>
										<font class="standardInputTitle" id="workersFont" >From: </font>
										<input id="fromDate" class="required  date-picker"  readonly/>
										<font class="standardInputTitle" id="workersFont" >To: </font>
										<input id="toDate" class=" required date-picker" readonly/>
									</div>
									
									<div class="floatLeft">
										<font class="standardInputTitle" id="portalProviderNames">&nbsp; Provider Names :</font> 
										<select name="portalProvider" id="portalProvider" class="required" title="Provider Name">
											<option  value="" >Select</option>
											<option  value="ALL" >ALL</option>															
 											<c:forEach items="${snpSubmittedProvs}" var="provder">
												<option value="${provder.PORTAL_PROV_ID}" >${provder.PROVIDER_NAME}</option>
											</c:forEach>
										</select>
									</div>									
									
										<div class="mL50 floatLeft">
											<input type="button" onclick="getProvPortalSNPRptData();" title="Provider Portal SNP Submission Report" value="SNP Submission Report"  id="loadProvPortalSNPRpt">
											<input type="button" onclick="clearProvPortalSNPData();" title="Clear" value="Clear">											
										</div>								
								</form>
								
								
						</div>
						
						<div id="provPortalSNPeport" class="container displayNone">
 							<div class="example-header">
 								<label class="standardInputTitle pb10 mL2">Quick Search</label>
								<input type="text" id="filter-text-box" class="standardInputText" placeholder="Search in data..." oninput="onFilterTextBoxChanged()">
							</div>			
 							<div id="myGrid" style="height: 650px; width:1660px;" class="ag-theme-balham"></div>						
						</div>								
					</div>

				</div>		
		</div> 
	</div>	
		
    <%@ include file="../../common/commonScriptsIncludes.html" %>
    <%@ include file="../../common/commonagGridScriptsIncludes.html" %>        
    <script src="${staticPath}/js/main.js"></script>
		<script src="${staticPath}/js/reports/provPortal/provPortalFileSubmissionRpt.js?v=${buildDate}"></script>
	  <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body>
</html>