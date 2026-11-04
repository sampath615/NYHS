<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<meta http-equiv="cache-control" content="no-cache" />
	
	<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
	
	<%@ include file="../common/common_includes.jsp" %> 
    <%@ include file="../common/commonStylesIncludes.html" %> 
    <%@ include file="../common/MainMenuIncludes.jsp" %>
        
	<link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css"/>
	<link href="${staticPath}/css/jquerycombobox.css" rel="stylesheet" type="text/css" />	
	<link href="${staticPath}/css/assignments/assignments.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common/datePicker/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" media="screen" href="${staticPath}/css/common/datePicker/bootstrap-datetimepicker.css">
	
	<title>Assignment Management</title>
</head>

<body bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png" id="assignmentManagement">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../common/Menu.jsp" %>
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="listAssignmentsTab"><a href="#assignmentsTab" id="loadAssignmentsTab">Assignments</a></li>				
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
						<jsp:include page="AssignmentsList.jsp" />
					</div>		
											
					<div id="assignmentDetalsTab" style="clear: both;">	
						<div id="assignmentDetailsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						<div id="assignmentDetailsData">
						
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
				<div id="eharsPersonDetailsTab" style="clear: both; overflow: scroll"> </div>
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
	   
	   <div id="confirmMessageDialog" title="Please Confirm?" >
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
								<option value="${reason.REASON_CODE}" class="${reason.FLAG}"  DOC_FLAG="${reason.DOC_FLAG}">${reason.REASON_DESC}</option>
							</c:forEach>
		          		</select>
	          		</span>
			  </div> 
		</div>
	</div>	
	
	
	<div id="printDialog" title="Print Preview?" >
		
		<a href="javascript:printPage('#printDialogContent', 'Assignment Details')" class="changeAddressLinks floatRight mT10 marginBot10">Print</a>
		<div class="clearBoth"></div>
		
		<div id="printDialogContent" style="background-color:white;">
		
		
		</div>
	</div>	
	
	<div id="addEditProvFacAddrsModal" class="userAddressDtls" >
			<p>
				<font class="required" title="Required Field">*</font> Indicates
				Required Field.
			</p>
			<jsp:include page="addEditProviderFacilityAddressDetails.jsp"></jsp:include>
	</div>
	
	<div id="laftFollowupConfirmDialog" title="Please Confirm?" class="displayNone">
		<h4 id="laftFollowupConfirmDialogContent"> Do you want to mark this Document for LAFT follow-up? </h4>
		<div>
			<label style="padding-left:0">Reason </label>
			<select id="laftFollowupReasons" name="laftFollowupReasons" class="required standardinputMedText">
				<option value=""> -- Select --</option>
				<c:forEach items="${laftFollowupReasons}" var="reason">
					<option value="${reason.REASON_ID}">${reason.REASON_DESC}</option>
				</c:forEach>
			</select>	
		</div>
		
	   <br/>
	   <div id="laftFollowupDocMarkComments">
			<span style="padding-left:0">Comments </span><br/>
   			<textarea id="laftFollowupRequestComments" class="required" cols="55" rows="4" style="border: thin solid #003366;" maxLength="3000" title="<spring:message code='label.requestComments' />"></textarea>
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
	<script src="${staticPath}/js/assignments/assignments.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/assignments/CommonAssignmentUtil.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>	
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jspdf.min.js" type="text/javascript"></script>
	<script src="${staticPath}/js/common/html2canvas.js"></script>
	
	
	<script type="text/javascript" src="${staticPath}/js/common/datePicker/bootstrap.js"></script>
	<script type="text/javascript" src="${staticPath}/js/common/datePicker/bootstrap-datetimepicker.js"></script> 		
</body>
</html>


