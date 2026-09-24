<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Assignment Review</title>
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/assignments/assignments.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />

</head>

<body class="assignmentReview">
<%@ include file="../common/Menu.jsp"%>
	<div id="assignmentReviewPage"  class="pageView">
      	<ul id="assignmentReviewTabs">
			<li><a href="#viewClosedAssignmentsTab"  id="closedAssignmentsTab">Assignment Review</a></li>
			<li id="listAssignmentDetailsViewTab" style="display: none;"><a href="#assignmentDetalsTab">Assignment Details</a>  </li>
			
			<li id="listPersonViewTab" style="display: none;"><a href="#personDetailsTab">Recent Demo View</a>  </li>
			<li id="listDocumentViewTab" style="display: none;"><a href="#documentDetailsTab">Document Timeline</a>  </li>
			<li id="listEharsPersonViewTab"  style="display: none;"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>
			
			<li id="listACRFViewTab" style="display: none;">
							<a href="#acrfDocumentTab" id="crfDocumentTab">ACRF</a>
							<span class="ui-icon ui-icon-close" role="presentation" title="Close ACRF" onclick="closeTab('listACRFViewTab', 'assignmentDetalsTab')">Close ACRF</span>
						</li>
		</ul>
	   <div id="viewClosedAssignmentsTab">
	   		<div id="assignmentsReviewTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   			   		<div id="assignmentsReviewSearchCriteria" class="pT10 pB20">
							<form action="assignmentsReviewForm" id="assignmentsReviewForm" >
									<div class="alignCenter">
											
										  	<span class="mL50 closureCategoryDiv">
												<font class="standardInputTitle" id="workersFont">&nbsp;
													Closure Category :</font> 
												<select name="colsureCategory" id="colsureCategory" class="required" title="Document Type"  onChange="searchClosedAssignments(this)">
													<option  value="" >Select</option>
													
													<c:forEach items="${closureCategoryList}" var="closureCategoryDetails">
														<option value="${closureCategoryDetails.code}">${closureCategoryDetails.desc} (${closureCategoryDetails.count})</option> 											
													</c:forEach>
													<option value="OOJ">OOS/OOJ Residence at Dx OR OOS/OOJ Facility at Dx</option>
													
												</select>
											</span>
										</div>
								</form>
								
							</div>
	   		<div id="assignmentsForReview" class="jqGridTableSection displayNone" style="margin-bottom:18px;overflow-x:scroll;overflow-y:auto">
				<table id="assignmentsReviewTable"></table>
				<table id="assignmentsReviewPager"></table>
			</div>
	   </div>
	   <div id="assignmentDetalsTab" style="clear: both;">	
			<div id="assignmentDetailsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
			<div id="assignmentDetailsData">
			
			</div>
			<div class="alignCenter mT20 clearBoth" >
				<span
					class="mB15 mT10 mR5 pT20 pB20"
					id="superReopenDiv">
					<a class="a_demo_search assignmentActionButtons colorWhite mL10 displayNone" onclick="confirmApproveClosedAssignment();" id="approveClosedAssignment"> Approve </a>
					<a class="a_demo_search assignmentActionButtons colorWhite mL10 displayNone" onclick="confirmAgreeClosedAssignment();" id="agreeClosedAssignment" > Agree </a>
					<a class="a_demo_search assignmentActionButtons colorWhite mL10 displayNone" onclick="reOpenClosedAssignment();" id="reOpenClosedAssignment"> Reopen </a>
					<a class="a_demo_search assignmentActionButtons colorWhite mL10 displayNone" onclick="loadAssignRawFacProvDialog();" id="changeAddressAssignment"> Change Address </a>
					<a  class="enabledColor a_continue enabledReadonlyButton mL10 displayNone" style="color: #FFFFFF;"  href="#"  id="acrfFormClosedAssignment">View ACRF</a>
				</span>			
			</div>
			<!-- Tab content will be build dynamically  -->
			<%-- <jsp:include page="AssignmentDetails.jsp" /> --%>
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
			
		<div id="eharsPersonDetailsTab" style="clear: both; overflow: scroll">
		
		</div>	
				
						
		<div id="acrfDocumentTab" style="clear: both;">				
					  <!-- Tab content will be build dynamically  -->
				</div>		
   </div>
   
   <div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
		<div id="requestCommentsText" style="display:none">
	   			<span class="boldHeaders valignTop" ><spring:message code="label.comments" /></span>
          		<span class="mL35"><textarea id="requestComments" class="required" cols="40" class="" rows="4" style="border: thin solid #003366;" title="<spring:message code='label.requestComments' />"></textarea></span>
		  </div>  
		  
			  <div id="optionalParameters" style="display:none" class="mT10">
		   			<span class="boldHeaders" >Reason</span>
	          		<span class="mL10">
		          		<select id="discardReason" class="required standardInputText" title="Discard Reason">
		          			<option value=""></option>
		          			<c:forEach items="${nonCaseReasons}" var="reason">
								<option value="${reason.REASON_CODE}" class="${reason.FLAG}" DOC_FLAG="${reason.DOC_FLAG}">${reason.REASON_DESC}</option>
							</c:forEach>
		          		</select>
	          		</span>
			  </div>
   </div>
   <div id="assignReassignModalDiv">
		<div id="assignReassignConfirmDiv" style="display: none;">
				<div id="assignReassignAssignments" style="margin-top: 20px;">
					<table id="assignReassignConfirmTable"></table>
				</div>
		
				<div id="assignReassignCommentsDiv"
					style="text-align: center; width: 70%; margin-left: auto; margin-right: auto; border: thin dotted #003366; margin-top: 20px;">
					<div style="margin-bottom: 15px; margin-top: 15px;">
						<a style="vertical-align: top; margin-top: 5px;"><spring:message
								code="label.comments" /></a>
						<textarea id="assignReassignText" cols="40" rows="4"
							style="border: thin solid #003366;"></textarea>
					</div>
				</div>
		
				<div id="acrfWarningText"
					style="margin-top: 15px; text-align: justify;"></div>
				<div style="margin-top: 30px; text-align: center;">
					<ul id="menu-css" style="padding: 0px;">
						<li><a href="javascript:reopenAssignments();">Reopen</a></li>
						<li><a href="javascript:closeAssignReassignModal();">Cancel</a></li>
					</ul>
				</div>
			</div>   
  	 </div>
  	 
  	 <div id="printDialog" title="Print Preview?" >
		
		<a href="javascript:printPage('#printDialogContent', 'Document details')" class="changeAddressLinks floatRight mT10 marginBot10">Print</a>
		<div class="clearBoth"></div>
		
		

		<div id="printDialogContent" style="background-color:white;">
			<div class="documentDescription">
				<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
					<span class=" mL2">Document Details</span>
				</div>
				
				<div style="padding-top:5px; padding-bottom:5px; border:1px dotted;" class="container">	 
					<div style="float:left; padding-left:10px;width:49%; ">
						<div class="mT10 container">
							<span style="float:left; width:45%">
								Document Id
							</span>
							<span style="float:left">
								<input name="triggeringDocumentId" id="triggeringDocumentId" class="standardInputMedText" />
							</span>
						</div>
						<div class="mT10 container">
							<span style="float:left; width:45%">
								STATENO :
							</span>
							<span style="float:left">
								<input name="triggeringDocumentStateno" id="triggeringDocumentStateno" class="standardInputMedText" />
							</span>
						</div>
					</div>
				
				
				<div style="float:left; width:49%; padding-bottom:5px; padding-left:10px; border-left:1px dotted;">
				
					
						<div class="mT10 container">
							<span style="float:left; width:40%">
								Source Id :
							</span>
							<span style="float:left">
								<input name="triggeringDocumentSourceCode" id="triggeringDocumentSourceCode" class="standardInputMedText" />
							</span>
						</div>
						
						<div class="mT10 container">
							<span style="float:left; width:40%">
								eHARS STATENO :
							</span>
							<span style="float:left">
								<input id="triggeringDocumentEharsStateno" name="triggeringDocumentEharsStateno" class="standardInputSmallText"/> 
							</span>
						</div>
						
					
				</div>
				</div>
			</div>
		
		</div>
	</div>	
	
  	 <div id="addEditProvFacAddrsModal" class="userAddressDtls displayNone" >
			<p>
				<font class="required" title="Required Field">*</font> Indicates
				Required Field.
			</p>
			<jsp:include page="addEditProviderFacilityAddressDetails.jsp"></jsp:include>
		</div>
		
		<div id='docViewDlg'>  </div>		
		<%@ include file="../common/addressComboDialog.jsp" %>	
		
		
    <%@ include file="../common/commonScriptsIncludes.html"%>
    <script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
    <script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
    <script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/scan/pdf.js"></script>
	<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/reports/labCommonUtil.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/review/assignmentsReview.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/assignments/CommonAssignmentUtil.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/person/commonDocEdit.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body> 
