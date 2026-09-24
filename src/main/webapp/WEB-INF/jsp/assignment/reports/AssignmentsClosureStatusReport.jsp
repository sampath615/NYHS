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
	
	<title>Assignment Closure Status Report</title>
  	<style>
		.ui-jqgrid .ui-jqgrid-htable th div {overflow: hidden; position:relative; height:auto;white-space: pre-line;}
		.ui-jqgrid-btable tr td{text-align:center !important;
}
	</style>
</head>

<body bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../../common/Menu.jsp" %>
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="listAssignmentsTab"><a href="#assignmentsTab">Assignments Closure Status Report</a></li>				
					</ul>
					
 					<div id="assignmentsTab" style="clear: both; height: 750px;">
						<div id="assignmentsClosureStatusReportTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div id="asssignmentsStatusCriteria" class="pT10 pB20">
							<form action="getAssignmentsClosureStatusReportsList" id="loadAssignmentsClosureStatusReportData">
		
								
									<div id="dateRange" class="floatLeft" style="height:25px">
										<font class="standardInputTitle" id="textToDate" >Date: </font>
										<font class="standardInputTitle" id="textToDate" >From: </font>
										<input id="fromDate" class="required  date-picker"  readonly/>
										<font class="standardInputTitle" id="textToDate" >To: </font>
										<input id="toDate" class=" required date-picker" readonly/>
									</div>
									
									<div class="floatLeft">
										<font class="standardInputTitle" id="workersFont">&nbsp; Regions :</font> 
										<select name="regions" id="regions" class="required" title="Region"  >
											<option  value="ALL" >ALL</option>
											<c:forEach items="${regionsList}" var="regionDetails">
												<option value="${regionDetails.key}" >${regionDetails.value}</option>
											</c:forEach>
										</select>
									</div>									
									
										<div class="mL50 floatLeft">
											<input type="button" onclick="getAssignmentsClosureStatusData();" title="Assignment Closure Status Report" value="Assignment Closure Status Report"  id="loadAssignmentsClosureStatusReport">
											<input type="button" onclick="clearClosureStatusDates();" title="Clear" value="Clear">											
										</div>
								
								</form>
								
								<div class="floatRight"><a href="javascript:void(0)">Download</a></div>
								
						</div>
							
				   		<div id="assignmentClosureStatuesReport" style="overflow-x:auto;" class="clearBoth mT5">
							<table id="assignmentClosureStatuesReportTable"></table>
						</div>							
					</div>

				</div>		
		</div> 
	</div>	
		
    <%@ include file="../../common/commonScriptsIncludes.html" %>
    <script src="${staticPath}/js/main.js"></script>
		<script src="${staticPath}/js/assignments/assignmentsClosureStatusReport.js?v=${buildDate}"></script>
	  <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body>
</html>


