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
						<li id="listAssignmentsTab" ><a href="#assignmentsTab">Assignments Reports</a></li>
						<li id="listPersonViewTab" class="displayNone"><a href="#personDetailsTab" >Recent Demo View</a></li>
						<li id="listDocumentViewTab" class="displayNone"><a href="#documentDetailsTab" >Document Timeline</a></li>
						 <li id="listEharsPersonViewTab" class="displayNone"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>				
					</ul>
					
					<div id="assignmentsTab" style="clear: both;">
						<div id="assignmentReportsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div id="asssignmentsStatusCriteria" class="pT10 pB20">
							<form action="getAssignmentsReportsList" id="loadAssignmentReportsData">
		
								
									<div id="documentTypeSection" class="floatLeft" style="height:25px">
										<font
											class="standardInputTitle" id="textToDate" >Document Type: </font>
											
										<select id="documentType" class="required" onchange="changeSearchCrieria(this)" title="Document Type">
											<option value="" reference="">Select</option>
<!-- 											<option value="ADAP" reference="file" displayName="ADAP">ADAP</option> -->
											<c:if test="${userPermissions['157'] != null}" >
											<option value="AIRS"  reference="date" displayName="AIRS">AIRS</option>
											</c:if>
											<c:if test="${userPermissions['167'] != null}" >
												<option value="DOCCS" reference="file" displayName="DOCCS">DOCCS</option>
											</c:if>
											<c:if test="${userPermissions['169'] != null}" >
												<option value="DTH" reference="file" displayName="Death">Death</option>
											</c:if>
											<c:if test="${userPermissions['174'] != null}" >
												<option value="RESISTANCE" reference="date" displayName="Resistance">RESISTANCE</option>
											</c:if>
											<c:if test="${userPermissions['174'] != null}" >
												<option value="HIV_LAB" reference="date" displayName="Laboratory Reports">HIV_LAB</option>
											</c:if>
											<c:if test="${userPermissions['177'] != null}" >
												<option value="INS" reference="file" displayName="Insurance">Insurance</option>
											</c:if>
											<c:if test="${userPermissions['178'] != null}" >
												<option value="INS_OLD" reference="file" displayName="Insurance Old">Insurance Old</option>
											</c:if>
											<c:if test="${userPermissions['187'] != null}" >
												<option value="NYC_DTH" reference="file" displayName="NYC Death">NYC Death</option>
											</c:if>
											<c:if test="${userPermissions['190'] != null}" >
												<option value="NYC_PRF"  reference="date" displayName="NYC Provider Report Form">NYC Provider</option>
											</c:if>
											<c:if test="${userPermissions['195'] != null}" >
												<option value="PRF"  reference="date" displayName="Provider Report Form">Provider</option>
											</c:if>
											<c:if test="${userPermissions['203'] != null}" >
												<option value="STD_CONF"  reference="file" displayName="STD_CONF">STD_CONF</option>
											</c:if>
											<c:if test="${userPermissions['204'] != null}" >
												<option value="STD_LAB"  reference="date" displayName="STD_HIV Lab Report">STD_HIV Lab Report</option>
											</c:if>
											<c:if test="${userPermissions['205'] != null}" >
												<option value="STD_OLD"  reference="file" displayName="STD_Old">STD Old</option>
											</c:if>
											<c:if test="${userPermissions['208'] != null}" >
												<option value="TB"  reference="file" displayName="TB">TB</option>
											</c:if>
											
										</select>
									</div>
									
									<div id="createDateRange" class="floatLeft displayNone" style="height:25px">
										<font
											class="standardInputTitle" id="textToDate" >Date: </font>
											
										<font
											class="standardInputTitle" id="textToDate" >From: </font>
										<input id="fromDate"
											class="required  date-picker" title="From date" readonly />
											<font
											class="standardInputTitle" id="textToDate" >To: </font>
											<input id="toDate" class="required date-picker" title="To date" readonly 
												  />
									</div>
									
									
										<div class="floatLeft displayNone" id="fileNameSection">
											<font class="standardInputTitle" id="workersFont">&nbsp;
												File Name:</font> 
											<select name="fileName" id="fileName" class="required" title="File Name"  >
												<option  value="" >Select</option>
												<option  value="Mbr_Eligible_2016" >2016</option>
											</select>
										</div>
										
										
										<div class="mL50 floatLeft">
											<input type="button" onclick="searchAssignmentReports();" 		title="Assignment Reports" value="Assignment Reports" 		id="loadAssignmentReports">
										</div>
										
										<div class="mL50 floatLeft displayNone" id="changeDisplayFileName">
										
											<a href="javascript:changeFileName()" class=" anchorLinks" id="changeDisplayFileName">Change File Name</a>
										</div>
									
									
								
								</form>
								
								<div class="floatRight"><a href="javascript:printAssignmentReports()" class="floatRight mT10 marginBot10" id="printPage">Print</a></div>
							</div>
							
							<div id="tableContents" class="clearBoth panel-body panel-body-table displayNone" style="text-align:center; border: 1px solid slategray">
                                                                      
                                    
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
	
	
	
	<div id="addContact" title="Add New Contact" style="display:none;width:80%">
		
		<div class="standardInputDiv">
			<div class="fieldHolder33">
				<font class="labReportTitle">Display Name:</font>
			</div>
			<div class="">
				<input maxlength="100" class="standardInputSmallText required" type="text" id="AssignmentReportDisplayFileName" name="AssignmentReportDisplayFileName" title="Display File name"/>
			</div>
		</div>
	
	</div>
	
	
	
		
		
    <%@ include file="../../common/commonScriptsIncludes.html" %>
    <script src="${staticPath}/js/common/html2canvas.js"></script>
    <script src="${staticPath}/js/common/jspdf.min.js"></script>
    <script src="${staticPath}/js/main.js"></script>
    <script src="${staticPath}/js/assignments/assignmentReportsByDocument.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/scan/pdf.js"></script>
	<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/search/search.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
				
	  
	  		
</body>
</html>


