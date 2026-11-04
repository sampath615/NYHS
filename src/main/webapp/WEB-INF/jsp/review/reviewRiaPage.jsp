<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	
	<title>CDESS Messages Review</title>
	
	
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
</head>

<body class="reviewRiaPage">
<%@ include file="../common/Menu.jsp"%>
	<div id="reviewRiaRecordsPage" style="display:none" class="pageView">
      	<ul id="riaPageTabs">
			<li id="riaReviewTab"><a href="#viewRiaReviewTab"  >RIA Records</a></li>
			<li id="listPersonViewTab" style="display: none;"><a
				href="#personDetailsTab">Recent Demo View</a></li>
			<li id="listDocumentViewTab" style="display: none;"><a
				href="#documentDetailsTab" id="documentTimeline_search">Document Timeline</a></li>
				<li id="listLabDocumentViewTab" style="display: none;"><a
				href="#labDocumentTab">Document View</a></li>
			<li id="listEharsPersonViewTab"  style="display: none;"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>
		</ul>
	   <div id="viewRiaReviewTab">
	   		<div id="riaReviewTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   		<div id="riaTabsByEligibility">
	   		<ul id="riaRecordsByEligibleTabs">
				<li id="unReviewedTab"><a href="#riaRecordsTab"   onClick="resetFilterType()">RIA Eligible</a></li>
				<li  ><a
					href="#riaRecordsTab" onClick="loadRiaDocumentForReview('RIA_ELIGIBLE','N')">Not Eligible</a></li>
				<li  ><a
					href="#riaRecordsTab" onClick="loadRiaDocumentForReview('TELE_HEALTH','Y')">Telehealth</a></li>
				<li><a
					href="#riaRecordsTab"    onClick="loadRiaDocumentForReview('COMPLETED','Y')">Completed - Eligible </a></li>
				<li><a
					href="#riaRecordsTab"    onClick="loadRiaDocumentForReview('RIA_INELIGIBLE_COMPLETED','Y')">Completed - Ineligible </a></li>
			</ul>
			<div id="riaRecordsTab">
				
				<div class="alignCenter pB10" id="riaFollowupFilterTypeContent">
		   			<span class="bold">Filter Type :</span>
		   			<span class="mL10"><select id="riaFollowupFilterType" onchange="loadDocumentErrorsData(this)">
		   				<option value="ALL_ELIG" class="ELIGIBLE">All</option>
		   				<option value="BHAE" class="ELIGIBLE">BHAE Follow-up</option>
		   				<option value="OMD" class="ELIGIBLE">OMD Follow-up</option>
		   				<option value="PS" class="ELIGIBLE">PS Follow-up</option>
		   				<option value="FIRST_GENOTYPE_DATE" class="ELIGIBLE">Missing Genotype</option>
		   				<option value="ARV_BEGIN_DATE" class="ELIGIBLE">Missing Treatment Start Date </option>
		   				<option value="NO_PS_MSG" class="ELIGIBLE">No PS Message </option>
		   				<option value="SOUNDEX_CHECK_DATE" class="ELIGIBLE">CDC Soundex completed</option>
		   				<option value="ALL_INELIG" class="IN-ELIGIBLE">All</option>
		   				<option value="NOT_HIV_FLAG" class="IN-ELIGIBLE">Not HIV</option>
		   				<option value="OOJ_CASE_FLAG" class="IN-ELIGIBLE">OOJ Case</option>
		   				<option value="OOJ_PROVIDER_FLAG" class="IN-ELIGIBLE">OOJ Provider</option>
		   				<option value="NON_PROMPT_FLAG" class="IN-ELIGIBLE">Non Prompt</option>
		   				<option value="PT_ON_PREP_FLAG" class="IN-ELIGIBLE">PREP</option>
		   				<option value="DECEASED" class="IN-ELIGIBLE">Deceased</option>
		   				<option value="FEDERAL_FACILITY" class="IN-ELIGIBLE">Federal Facility</option>
		   				<option value="NDVL_AT_DX" class="IN-ELIGIBLE">NDVL at time of dx</option>
		   				<option value="PERINATAL" class="IN-ELIGIBLE">Perinatal</option>
		   				<option value="PKP_FLAG" class="IN-ELIGIBLE">PKP</option>
		   			</select></span>
		   		</div>
			
			
		   		<div id="riaRecords" class="marginBottom20">
					<table id="riaRecordsTable"></table>
					<table id="riaRecordsPager"></table>
				</div>
			</div>
			</div>
	   </div>
	   <div id="personDetailsTab" style="clear: both; overflow: scroll">
			<!-- Tab content will be build dynamically  -->
		</div>
		<div id="documentDetailsTab" style="clear: both;">
	
			
			
			<form name="documentForm" class="documentDetails">
				<div id="documentDetailsDiv">
					<table id="documentDetails"></table>
					<div id="documentDetailsPagerDiv"></div>
				</div>
			</form>
		</div>
		<div id="labDocumentTab" style="clear: both;width:1600px" class="positionAbsolute">
			
			<c:if test="${userPermissions['144'] != null}">	
				<div style="text-align:right">
					
						<a class="riaActivityGid bold" href="#" onclick="showRiaActivityContent()">Activity Tracker</a>
				</div>
			</c:if>
			
			<div id="documentEditTransactionMessage" class="transactionMessage"><span class="message"></span></div>
			<div id="documentContent" class="clearBoth ">
			
			</div>
		</div>
		<div id="eharsPersonDetailsTab" style="clear: both; overflow: scroll">
		
		   </div>
		
   </div>
   
   
   <div id="confirmMessageDialog" title="Please Confirm?" >
		<h4 id="confirmMessageContent"></h4>
		<div id="requestCommentsText" style="display:none">
   			<label style="padding-left:0">Comments </label></br>
         	<textarea id="requestComments"  cols="40" rows="4" style="border: thin solid #003366;" class="required" maxLength="3000" title="Comments"></textarea>
	  </div>  
	  
	  <div id="optionalParameters" style="display:none" class="mT10">
   			<span class="boldHeaders" >Closure Category</span>
         		<span class="mL10">
         		
         		
         		<c:set var="refColumnData" value="${riaDropDownList.CASE_CLOSURE_CD}"/>
				<select name="closureCategory" id="closureCategory"
						title=" Closure Category"
						class="required standardInputMedText " >
					<option value="">Select</option>
					<c:forEach items="${refColumnData}" var="diagnosisSettingData">
							<option value="${diagnosisSettingData.LOOKUP_CD}">${diagnosisSettingData.LOOKUP_NAME}</option>
						</c:forEach>
				</select>
         		
         		
         		</span>
	  </div>
   </div>
	
	<div id="personTableContainerModal">
		<table id="personFieldValuesTbl"></table>
	</div>
	
	
	<div id='docViewDlg'>  </div>
	
	<div id="emailViewTab" class="height325">
		<input type="hidden" id="emailSubject" value="Request to add a new standardized facility" />
		<div id="standardMessageCryptModal" class="displayNone">
			<%@ include file="../email/emailWindow.jsp" %>
		</div>
	</div>
	
	<c:if test="${userPermissions['144'] != null}">
	
	<%@ include file="../common/addressComboDialog.jsp" %>
	
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
   </c:if>
	
    <%@ include file="../common/commonScriptsIncludes.html"%>
    <script src="${staticPath}/js/scan/pdf.js"></script>
	<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/review/RiaReview.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
    		<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
    				<script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
		<script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
</body> 
