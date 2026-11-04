<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<meta http-equiv="cache-control" content="no-cache" />
	
	<%@ include file="../../common/common_includes.jsp" %> 
    <%@ include file="../../common/commonStylesIncludes.html" %> 
    <%@ include file="../../common/MainMenuIncludes.jsp" %>
        
	<link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
		<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/assignments/assignments.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	
	<title>Assignment Reports</title>
</head>

<body bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../../common/Menu.jsp" %>
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="listAssignmentsAHIEnabledTab" ><a href="#assignmentsAHIEnabledTab">AHI enabled</a></li>
						<li id="listAssignmentsAHIEnabledTab" ><a href="#assignmentsAHIDisabledTab" onClick="getDisabledReport('assignmentsAHIDisabledTab')">AHI disabled</a></li>
						<li id="listAssignmentsAHIEnabledTab" ><a href="#assignmentsAHI90DaysTab" onClick="getNintyDaysReport('assignmentsAHI90DaysTab')">AHI >90 days</a></li>
						<li id="listPersonViewTab" class="displayNone"><a href="#personDetailsTab" >Recent Demo View</a></li>
						<li id="listDocumentViewTab" class="displayNone"><a href="#documentDetailsTab" >Document Timeline</a></li>
						 <li id="listEharsPersonViewTab" class="displayNone"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>				
					</ul>
					
					<div id="assignmentsAHIEnabledTab" style="clear: both;">
						<div id="assignmentReportsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div class="floatRight"><a href="javascript:printAssignmentReports()" class="floatRight mT10 marginBot10" id="printPage">Print</a></div>
							
						<div class="ahiReportTable">
							<jsp:include page="ahiAssignmentReportTable.jsp"></jsp:include>
						</div>
					</div>
					<div id="assignmentsAHIDisabledTab" style="clear: both;">
						<div id="assignmentReportsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div class="floatRight"><a href="javascript:printAssignmentReports()" class="floatRight mT10 marginBot10" id="printPage">Print</a></div>
							
						<div class="ahiReportTable">
						</div>
					</div>
					<div id="assignmentsAHI90DaysTab" style="clear: both;">
						<div id="assignmentReportsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div class="floatRight"><a href="javascript:printAssignmentReports()" class="floatRight mT10 marginBot10" id="printPage">Print</a></div>

						<div class="ahiReportTable">
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
					<div id="eharsPersonDetailsTab" style="clear: both; overflow: scroll">
					</div>
				</div>		
											
										
			</div> 
		</div>	
				<div id='docViewDlg'>	</div>
		<div id="confirmMessageDialog" >
			<h4 id="confirmMessageContent"></h4>
			<div id="requestCommentsText" style="display:none" >
	   			<label style="padding-left:0"><spring:message code="label.comments" /> </label></br>
          		<textarea id="requestComments" class="required" cols="40" class="" rows="4" style="border: thin solid #003366;" title="<spring:message code='label.requestComments' />"></textarea>
		  </div>  
	   </div>
	<div id="printDialog" title="Print Preview?" class="displayNone">
		
		<a href="javascript:printPage('#printDialogContent','Assignments Report')" class="changeAddressLinks floatRight mT10 marginBot10">Print</a>
		<div class="clearBoth"></div>
		
		<div id="printDialogContent" style="background-color:white;" class="tableReferenceDetails">
		
		
		</div>
	</div>		
	
	
		
    <%@ include file="../../common/commonScriptsIncludes.html" %>
    <script src="${staticPath}/js/common/html2canvas.js"></script>
    <script src="${staticPath}/js/common/jspdf.min.js"></script>
    <script src="${staticPath}/js/main.js"></script>
    <script src="${staticPath}/js/assignments/assignmentReportsByAHI.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/scan/pdf.js"></script>
	<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/search/search.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
				
	  
	  		
</body>
</html>


