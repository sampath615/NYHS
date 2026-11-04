<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<title>Person Search</title>
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	
	<link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/jquerycombobox.css" rel="stylesheet" type="text/css" />	
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />	
	<link href="${staticPath}/css/common/datePicker/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" media="screen" href="${staticPath}/css/common/datePicker/bootstrap-datetimepicker.css">
	<link href="${staticPath}/css/common/autoComplete.css" rel="stylesheet" type="text/css" />
	
</head>

<body bgcolor="#e4e4e4" id="personSearch" background="${staticPath}/images/NYEHMSVirusBackground.png" class="search">

		<div id="floatContent"  style="height: 100%;">
			<%@ include file="../common/Menu.jsp" %>
			<%@ include file="personSearchTabDetail.jsp" %>
		</div>

		<div id='docViewDlg'>	</div>
		<div id='docSecondViewDlg'>	</div>
		
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
								<option value="${reason.REASON_CODE}" class="${reason.FLAG}"  DOC_FLAG="${reason.DOC_FLAG}">${reason.REASON_DESC}</option>
							</c:forEach>
		          		</select>
	          		</span>
			  </div>
			  <div id="requiredTempParameters" class="displayNone" class="mT10">
			  
			  </div>
	   </div>
	   
	   <div id="confirmMessageForAssignment" >
			<h4 id="confirmMessageForAssignmentContent"></h4>
	   </div>
	   
	   
	   
	   	<div id="documentSelectionDialog" class="displayNone">
			<div style="text-align:center; font-weight:bold;margin:5px">
				  	<span class="searchFacilityTxt"> Document Type :</span>
					<span>
							<select id="addDocumentToStateno" class="searchOptions" onchange="addDocumentToStateno(this.value)">
								<option value="">Select</option> 
								<c:if  test="${userPermissions['106'] != null}" >
									<option value="A06.34" testType="MMP">MMP</option>
<!-- 									<option value="A06" testType="OOS_TESTING">OOS Testing</option> -->
									<option value="A06.50" testType="MISSING_DATA">Other database/Other</option>
								</c:if>
								<c:if  test="${userPermissions['122'] != null}" >
									<option value="SCAN_CRF_CO" testType="SCAN_CRF_CO">SCAN_CRF_CO</option>
								</c:if>
								<c:if  test="${userPermissions['124'] != null}" >
									<option value="SCAN_CRF_CDCO" testType="SCAN_CRF_CDCO">SCAN_CRF_CDCO</option>
								</c:if>
								<c:if  test="${userPermissions['125'] != null}" >
									<option value="SCAN_CRF_BRO" testType="SCAN_CRF_BRO">SCAN_CRF_BRO</option>
								</c:if>
								<c:if  test="${userPermissions['128'] != null}" >
									<option value="SCAN_CRF_CNYRO" testType="SCAN_CRF_CNYRO">SCAN_CRF_CNYRO</option>
								</c:if>
								<c:if  test="${userPermissions['126'] != null}" >
									<option value="SCAN_CRF_LIRO" testType="SCAN_CRF_LIRO">SCAN_CRF_LIRO</option>
								</c:if>
								<c:if  test="${userPermissions['127'] != null}" >
									<option value="SCAN_CRF_NRRO" testType="SCAN_CRF_NRRO">SCAN_CRF_NRRO</option>
								</c:if>
								<c:if  test="${userPermissions['123'] != null}" >
									<option value="SCAN_CRF_RRO" testType="SCAN_CRF_RRO">SCAN_CRF_RRO</option>
								</c:if>
								<c:if  test="${userPermissions['129'] != null}" >
									<option value="SCAN_CRF_RRCO" testType="SCAN_CRF_RRCO">SCAN_CRF_RRCO</option>
								</c:if>
								<c:if  test="${userPermissions['130'] != null}" >
									<option value="SCAN_PRF" testType="SCAN_RRO">SCAN_PRF</option>
								</c:if>
								
								
								
								
								
<!-- 							<option value="A06.18" testType="RECORD_SEARCH">Record Search</option> -->
							</select>
					</span>
			</div>
		</div>
		
	<div id="addToClusterDialog">
		<%@ include file="../common/addClusterToStateno.jsp" %>	
	</div>		
	
	<div id="emailViewTab" class="height325">
		<input type="hidden" id="emailSubject" value="Request to add a new standardized facility" />
		<div id="standardMessageCryptModal" class="displayNone">
			<%@ include file="../email/emailWindow.jsp" %>
		</div>
	</div>		
		
	
	
	
	
	
<!-- 	<div id="nonCaseConfirmDialog" title="Please Confirm?" class="displayNone"> -->
<!-- 		<h4 id="nonCaseConfirmDialogContent"> Do you want to create a Non case Document? </h4> -->
<!-- 		<div> -->
<!-- 			<select id="nonCaseReasons" name="nonCaseReasons" class="required standardinputMedText"> -->
<!-- 				<option value=""> -- Select --</option> -->
<%-- 				<c:forEach items="${nonCaseReasons}" var="reason"> --%>
<%-- 					<option value="${reason.REASON_CODE}">${reason.REASON_DESC}</option> --%>
<%-- 				</c:forEach> --%>
<!-- 			</select>	 -->
<!-- 		</div> -->
		
<!-- 	   <br/> -->
<!-- 	   <div id="nonCaseConfirmMergeStNotext"> -->
<!-- 			<span style="padding-left:0">Comments </span><br/> -->
<%--    			<textarea id="nonCaseConfirmRequestComments" class="required" cols="40" rows="4" style="border: thin solid #003366;" maxLength="3000" title="<spring:message code='label.requestComments' />"></textarea> --%>
<!-- 	  </div>    -->
<!--    </div> -->
	
	
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
			
	
	<div id="laftInitialReviewCommentsDialog" title="Additional Initial Comments" class="displayNone">
			<span style="padding-left:0">Comments </span><br/>
   			<textarea id="laftInitialReviewComment" class="required" cols="70" rows="4" style="border: thin solid #003366;" maxLength="3000" title="<spring:message code='label.requestComments' />"></textarea>
   </div>
		
			
		
	   <%@ include file="../common/addressComboDialog.jsp" %>
	   
	   <%@ include file="../common/commonScriptsIncludes.html" %>
	   
	   <script src="${staticPath}/js/main.js"></script>
		<script src="${staticPath}/js/jquery/jquery.collapsible.js"></script>
		<script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
		<script src="${staticPath}/js/scan/pdf.js"></script>
		<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
		<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
		<script src="${staticPath}/js/search/search.js?v=${buildDate}"></script>
		<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
		<script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
		<script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
		<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
		<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
	   	<script src="${staticPath}/js/common/autoComplete.js"></script> 				
				
				
		
</body>
</html>

