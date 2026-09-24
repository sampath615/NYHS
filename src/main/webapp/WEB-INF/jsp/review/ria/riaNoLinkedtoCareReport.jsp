<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Not Linked to Care</title>
<%@ include file="../../common/common_includes.jsp" %> 
<%@ include file="../../common/commonStylesIncludes.html" %> 
<%@ include file="../../common/MainMenuIncludes.jsp" %>
<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/common/select2.css" rel="stylesheet"	type="text/css" /> 
</head>
<body class="reviewRiaPage">
<%@ include file="../../common/Menu.jsp"%>
	<div id="reviewRiaRecordsPage" style="display:none" class="pageView">
		<ul id="riaPageTabs">
			<li id="riaReviewTab"><a href="#viewRiaReviewTab"  >Not linked to Care</a></li>
			<li id="listPersonViewTab" style="display: none;"><a href="#personDetailsTab">Recent Demo View</a></li>
			<li id="listDocumentViewTab" style="display: none;"><a href="#documentDetailsTab" id="documentTimeline_search">Document Timeline</a></li>
			<li id="listLabDocumentViewTab" style="display: none;"><a href="#labDocumentTab">Document View</a></li>
			<li id="listEharsPersonViewTab"  style="display: none;"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>
		</ul>
		<div id="viewRiaReviewTab" style="min-height:200px">
	   		<div id="riaReviewTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   		
	   		<div id="riaNoLinkedFormData" class=" mB20 centerFlexRowHeaders">
	   								<div > 
	   									
										<div style="margin-top:19px">
											<span
												class="bold font12" id="textToDate" >RIA Flagged Date</span>
										</div>
									</div>
	   							
	   								<div class="mL10"> 
	   									<div>
											<span
												class="bold font12 displayNone" id="riaFromDateLabel" >Start Date: </span><br/>
												</div>
										<div class="mT5">
											<input id="riaNoLinkedtoCareFromDate"  class="required select2Input  date-picker " dependentField = "riaNoLinkedtoCareFromDateLabel" title="Start Date" placeholder="Start Date" />
										</div>
									</div>
										<div  class="mL10">
												<div> 
													<span
														class="font12 bold displayNone" id="riaToDateLabel" >End date: </span><br/>
												</div>
												<div class="mT5">
													<input id="riaNoLinkedtoCaretoDate"  class="required  select2Input date-picker form-control " dependentField = "riaNoLinkedtoCaretoDateLabel" title="End date" placeholder="End Date"  
															  />
												</div>
										</div>
											  
							
	   		
	   			<div  class="mL10">
					<div style="margin-top:19px">
						<input type="button" onclick="getRiaReportNotLinkedToCare();" class="valignMiddle submitBtn" title="RIA Report" value="submit"	id="loadRiaReport">
					</div>
				</div>
			</div>
			<!-- <div class="alignCenter marginAuto pB10"  style="width:500px" id="riaNoLinkedtoCareDataReport">
		   		</div> -->
		   		
		   		<div id="riaRecords" class="marginBottom20">
					<table id="riaRecordsTable"></table>
					<table id="riaRecordsPager"></table>
					<div id="riaNote" style="color:red;margin-left:160px;display: none;"> Note* No labs since diagnosis date </div>
				</div>
			
		</div>
		<div id="personDetailsTab" style="clear: both; overflow: scroll">
		<!-- Content will build dynamically -->
		</div>
		<div id="documentDetailsTab" style = "clear : both">
		
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
		<div id="eharsPersonDetailsTab" style="clear: both; overflow : scroll"></div>
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
			<%@ include file="../../email/emailWindow.jsp"%>
		</div>
	</div>

	<c:if test="${userPermissions['144'] != null}">
	
	 <%@ include file="../../common/addressComboDialog.jsp" %>
	
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
   
	<%@ include file="../../common/commonScriptsIncludes.html"%>
    <script src="${staticPath}/js/scan/pdf.js"></script>
	<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/review/RiaReview.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/review/RiaReports.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
    <script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
	<script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/select2.js"></script>
</body>
</html>