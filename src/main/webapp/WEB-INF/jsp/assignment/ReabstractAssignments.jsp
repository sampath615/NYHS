<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<meta http-equiv="cache-control" content="no-cache" />
	
	<%@ include file="../common/common_includes.jsp" %> 
    <%@ include file="../common/commonStylesIncludes.html" %> 
    <%@ include file="../common/MainMenuIncludes.jsp" %>
        
	<link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/jquerycombobox.css" rel="stylesheet" type="text/css" />		
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/assignments/assignments.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common/datePicker/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" media="screen" href="${staticPath}/css/common/datePicker/bootstrap-datetimepicker.css">	
	
	<title>Reabstraction Assignments</title>
	
</head>

<body id="reabstractAssignment" bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../common/Menu.jsp" %>
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="listAssignmentsTab"><a href="#assignmentsTab" id="loadAssignmentsTab">Reabstraction Assignments</a></li>				
						<li id="listAssignmentDetailsViewTab" style="display: none;"><a href="#assignmentDetalsTab">Assignment Details</a>  </li>
						<li id="listPersonViewTab" style="display: none;"><a href="#personDetailsTab">Recent Demo View</a>  </li>
						<li id="listDocumentViewTab" style="display: none;"><a href="#documentDetailsTab">Document Timeline</a>  </li>
						<li id="listEharsPersonViewTab"  style="display: none;"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>
						<li id="listLabDocumentViewTab" style="display: none;">
							<a href="#labDocumentTab">Document View</a> 
							<span class="ui-icon ui-icon-close" role="presentation" title="Close Document" onclick="closeTab('listLabDocumentViewTab', 'documentDetailsTab')">Close Document</span>
						</li>
						<li id="listACRFViewTab" style="display: none;">
							<a href="#acrfDocumentTab" id="crfDocumentTab">ACRF</a>
							<span class="ui-icon ui-icon-close" role="presentation" title="Close ACRF" onclick="closeTab('listACRFViewTab', 'assignmentDetalsTab')">Close ACRF</span>
						</li>
					</ul>
					
					<div id="assignmentsTab" style="clear: both;">
						<div id="assignmentListTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
					<form:form modelAttribute="assignmentsHomeVO" action="getReabstractAssignmentsList" id="reloadedReabstractAssignmentsSearchCriteria">
						<div class="assignmentsClassfication" style="margin-bottom: 5px;">
		
							<div>
								<font class="standardInputTitle">Assignments For:</font> <font>${sessionScope.UserObj.userName}</font>
								<div id="assignmentRoleType" class="inlineBlock">
									<font class="standardInputTitle"><spring:message code="label.role" /> :</font>
										<form:select path="workerRoleCode" onchange="changeUserRole(this.value);">
											<form:option value="0" label="Select" />
											<form:options items="${userRoleCodes}" />
										</form:select>
										
									<font class="standardInputTitle" id="workersFont">&nbsp; Regions :</font> 
									<select name="regions" id="regions" class="required" title="Region"  >
										<option  value="ALL" >ALL</option>
										<c:forEach items="${regionsList}" var="regionDetails">
											<option value="${regionDetails.key}" >${regionDetails.value}</option>
										</c:forEach>
									</select>
								</div>											
								
							</div>
							
		 					<div id="roleAndRegionBasedContent" class="displayNone">
								<br>
								<font class="standardInputTitle">Choose Reabstraction Status :</font>
								<form:select name="reabAssignmentType" id="reabAssignmentType" path="assignmentType" class="required" title="Assignment Status">
									<form:option value="" label="Select" />
								</form:select>
								
								<input type="hidden" id="workerRegionId" value="" />

 								<font class="standardInputTitle">Workers :</font>
								<form:select name="reabWorkers" id="reabWorkers" path="workerId" class="required" title="Workers">
									<form:option value="-1" label="All Workers"/>
									<form:options items="${workers}" />
								</form:select>
								
								<font class="standardInputTitle"></font>
								<input type="button" onclick="createAssignmentTable();" title="Refresh Assignments" value="Refresh Assignments"	id="loadAssign">
								
							</div>		
												
						</div>
						
						<!-- Assignment List results -->
						<div id="assignmentsTableDiv" style="display: none;" class="jqGridTableSection mT20">
						
							<table id="AssignmentsTable"></table>
							<div id="AssignmentsPagerDiv"></div>
						
						</div>
						<div id="closedReabstractionCompletedMsg" class="displayNone mL5">
							<label class="reabcrfDiffYes">Note: Assignments(Highlighted in red font) Field Staff and Re-abstracion CRF is different</label>
							<label class="reabcrfDiffNo"> Assignments(Highlighted in green font) Field Staff and Re-abstracion CRF is same.</label>
						</div>
						
						<div style="clear: both;"></div>
						<div class="alignCenter mT20" >
						
							<span class="mB15 mT10 mR5 displayNone" id="superAssignDiv">
									<a class="a_demo_search assignmentActionButtons colorWhite" onclick="reabAssign();">Assign </a>
							</span>
							
							<span class="mB15 mT10 mR5 displayNone " id="assignmentListPrint">
									<a class="a_demo_search a_demo_print colorWhite" id="assignmentListPrintBtn" onclick="printAssignmentList();">Print </a>
							</span>
						
						</div>						
						
					</form:form>
				</div>				
							
					<div id="assignmentDetalsTab" style="clear: both;">	
						<div id="assignmentDetailsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						<div id="assignmentDetailsData">
						
						</div>			
					</div>		
					<div id="personDetailsTab" style="clear: both;">						
						 <!-- Tab content will be build dynamically  -->
					</div>
					<div id="personTableContainerModal">
						<table id="personFieldValuesTbl"></table>
					</div>		
						
					<div id="documentDetailsTab" style="clear: both;">
						<form name="documentForm">
							<div id="documentDetailsDiv">
								<table id="documentDetails"></table>
								<div id="documentDetailsPagerDiv"></div>
							</div>
						</form>
					</div>		
								
					<div id="eharsPersonDetailsTab" style="clear: both; overflow: scroll"></div>
					<div id='docViewDlg'>  </div>
					<div id='docSecondViewDlg'>	</div>				
					<div id="labDocumentTab" style="clear: both;">								
						<!-- Tab content will be build dynamically  -->						 						
					</div>	
					
					<div id="acrfDocumentTab" style="clear: both;">				
						  <!-- Tab content will be build dynamically  -->
					</div>				
			</div>		
											
		</div> 
		<div id="confirmMessageDialogForCloseAssignment" title="Please Confirm?" >
			<h4 id="confirmMessageContentForCloseAssignment"></h4>
	   </div>			
	</div>	
		
	<div id="editor"></div>

 	<div id="confirmMessageDialog" title="Please Confirm?" >
		<h4 id="confirmMessageContent"></h4>
		<div id="requestCommentsText" style="display:none">
   			<label style="padding-left:0">Comments </label></br>
         	<textarea id="requestComments"  cols="40" rows="4" style="border: thin solid #003366;" class="required" maxLength="3000" title="Comments"></textarea>
	  </div>  
   </div>
   
	<div id="printDialog" title="Print Preview?" >
		
		<a href="javascript:printPage('#printDialogContent', 'Assignment Details')" class="changeAddressLinks floatRight mT10 marginBot10">Print</a>
		<div class="clearBoth"></div>
		
		<div id="printDialogContent" style="background-color:white;">
		
		
		</div>
	</div>	  
	
	<div id="docAssignmentDiffViewDlg" title="Field Staff (top) and Reabstraction (bottom) CRF comparision">
		<div id="acrfDocumentDiffTab1" style="clear: both; height:400px;"></div>	
		<div id="acrfDocumentDiffTab2" style="clear: both; height:400px;"></div>	
	</div>	
	
	<div id="reabReviewDialog" title="Reabstraction Review" >
		<h4 id="confirmMessageContent"></h4>
		
		<div>
   			<label class="reabSecHeader" style="padding-left:0px;">Review Comments </label><br><br>
         	<textarea id="reabReviewComments" class="required" cols="88" rows="6" style="border: thin solid #003366;" maxLength="4000" 
         				title="Review Comments"></textarea><br><br>
			<div>
				<a class="enabledColor a_continue fadedCancelButton" href="#" onclick="javascript:submitReabReviewComments()" id="saveReabReviewComments" >Submit</a>
			</div>	 			
	  	</div>  
  	</div>
	
	 <div id="assignReassignModalDiv">
		<div id="selectAssigneeDiv" style="margin-top: 15px; text-align: center;">
			<form:form modelAttribute="assignmentsHomeVO" id="assignReassignModalForm">
				<font class="standardInputTitle" id="workersFont">Select Worker :</font>
				<form:select path="workerId" id="assigneeId">
					
				</form:select>
			</form:form>

			<div style="margin-top: 30px;">
				<ul id="menu-css" style="padding: 0px;">
					<li><a href="#" onClick="javascript:setAssignReabstractWorker(true);">OK</a></li>
					<li><a href="javascript:closeAssignReassignModal();">Cancel</a></li>
				</ul>
			</div>
		</div>


		<div id="assignReassignConfirmDiv" style="display: none;">
			<div id="assignReassignConfirmText" style="margin-top: 15px; text-align: justify;"></div>
			<div id="assignReassignAssignments" style="margin-top: 20px;">
				<table id="assignReassignConfirmTable"></table>
				<div id="assignReassignConfirmPagerDiv"></div>
			</div>
	
			<div id="assignReassignCommentsDiv"
				style="text-align: center; width: 70%; margin-left: auto; margin-right: auto; border: thin dotted #003366; margin-top: 20px;">
				<div style="margin-bottom: 15px; margin-top: 15px;">
					<a style="vertical-align: top; margin-top: 5px;"><spring:message code="label.comments" /></a>
					<textarea id="assignReassignText" cols="40" rows="4" style="border: thin solid #003366;"></textarea>
				</div>
			</div>
	
			<div id="acrfWarningText" style="margin-top: 15px; text-align: justify;"></div>
			<div style="margin-top: 30px; text-align: center;">
				<ul id="menu-css" style="padding: 0px;">
					<li><a href="javascript:submitAssignReabstract();">Submit</a></li>
					<li><a href="javascript:closeAssignReassignModal();">Cancel</a></li>
				</ul>
			</div>
		</div>
	
	</div>
	
	<%@ include file="../common/addressComboDialog.jsp" %>		

    <%@ include file="../common/commonScriptsIncludes.html" %>
    <script src="${staticPath}/js/common/jquery.maskedinput.js"></script>	
	<script src="${staticPath}/js/main.js"></script>
	<script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
	<script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/scan/pdf.js"></script>
	<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script> 
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/assignments/assignmentReabstractDbJsMap.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/assignments/assignmentReabstract.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/assignments/CommonAssignmentUtil.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>	
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jspdf.min.js" type="text/javascript"></script>
	<script src="${staticPath}/js/common/html2canvas.js"></script>
	
	<script type="text/javascript" src="${staticPath}/js/common/datePicker/bootstrap.js"></script>
	<script type="text/javascript" src="${staticPath}/js/common/datePicker/bootstrap-datetimepicker.js"></script> 		
	
</body>
</html>


