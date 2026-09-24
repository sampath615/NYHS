<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<title>OOC/DNLTC Follow-Up</title>
<%@ include file="../../common/common_includes.jsp"%>
<%@ include file="../../common/commonStylesIncludes.html"%>
<%@ include file="../../common/MainMenuIncludes.jsp"%>
<link href="${staticPath}/css/common.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css" media="screen" href="${staticPath}/css/common/datePicker/bootstrap-datetimepicker.css">	
<link href="${staticPath}/css/common/datePicker/bootstrap.css" rel="stylesheet">
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />


<style>



#reviewContent {
    margin: 0 auto;
    min-height: 90px;
}


</style>
</head>

<body class="oocDNLTCFollowup">
	<div id="headerContent">
		<%@ include file="../../common/Menu.jsp"%>
	</div>

	<div id="clericalReview">

		<div id="commonGridTab">
			<ul id="tabLinks">
				<li><a  href="#fileReviewTab" id="fileReviewTabLink">OOC/DNLTC Follow-Up</a>  </li>
				
				<li id="listPersonViewTab" style="display: none;"><a
					href="#personDetailsTab">Recent Demo View</a></li>
				<li id="listDocumentViewTab" style="display: none;"><a
					href="#documentDetailsTab" id="documentTimeline_search">Document Timeline</a></li>
				<li id="listLabDocumentViewTab" style="display: none;"><a
					href="#labDocumentTab">Document View</a></li>
			   <li id="listEharsPersonViewTab"  style="display: none;"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>
			</ul>
			<div id="fileReviewTab" style="clear: both;">
			
			
			
				<div id="documentFileCategorySelection" title="Filter by Document type" >
						<div  style="margin:10px auto">
					
								<div class="mL50 floatLeft" id="fileNameSection">
									<label class="bold">File Name:</label>
									<select name="documentFileName" id="documentFileName" class="required standardInputText" title="Document file name" onchange="changeOOCDNLTCCriteria()">
										<option value="">Select</option>
										
										<c:forEach items="${oocDnltcFiles}" var="file">
											<option value="${file.FILENAME}">${file.FILENAME}  (${file.SUBMITTEDBY})  (${file.SUBMITTEDDATE})</option>
										</c:forEach>
									</select>
								</div>
								
								<div class="mL50 floatLeft">
									<input type="button" onclick="loadOOCDNLTCFollowupData()" 		title="Review Data" value="Review" class="inlineEditButton"	id="loadFollowupData">
								</div>
						</div>
					</div>
			
			
			
				<div class="clearBoth" ></div>
			
			
				<div id="fileReviewTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				
				<div id="providerPortalReview" class="displayNone" >
							
							<div id="reviewContent">
							
							
							</div>
							
	             </div>
            </div>
            
            
            <div id="personDetailsTab" style="clear: both; overflow: scroll">
				<!-- Tab content will be build dynamically  -->
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
			
			<div id="labDocumentTab" style="clear: both; width:100%" class="positionAbsolute">
		
				<div id="documentEditTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				<div id="documentContent" class="clearBoth">
				
				</div>
			</div>
			<div id="eharsPersonDetailsTab" style="clear: both; overflow: scroll">
		
		   </div>		
          </div>
	</div>
	
	
	<div id="documentSelectionDialog" class="displayNone">
			<div style="text-align:center; font-weight:bold;margin:5px">
				  	<span class="searchFacilityTxt"> Document Type :</span>
					<span>
							<select id="addDocumentToStateno" class="searchOptions" onchange="addDocumentToStateno(this.value)">
								<option value="">Select</option> 
								<c:if  test="${userPermissions['106'] != null}" >
									<option value="A06.34" testType="MMP">MMP</option>
									<option value="A06.50" testType="MISSING_DATA">Other database/Other</option>
									<option value="A06" testType="OOS_TESTING">OOS Testing</option>
								</c:if>
				
<!-- 							<option value="A06.18" testType="RECORD_SEARCH">Record Search</option> -->
							</select>
					</span>
			</div>
		</div>
		
		
	<div id='docViewDlg'>	</div>
	<div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
		<div id="requestCommentsText" style="display:none">
   			<label style="padding-left:0"><spring:message code="label.comments" /> </label></br>
         		<textarea id="requestComments" class="required" cols="40" class="" rows="4" style="border: thin solid #003366;" title="<spring:message code='label.requestComments' />"></textarea>
	  </div>  
   </div>
	<%@ include file="../../common/addressComboDialog.jsp" %>		
	<%@ include file="../../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/review/providerPortalReview.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
	<script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
	<script type="text/javascript" src="${staticPath}/js/common/datePicker/bootstrap.js"></script>
	<script type="text/javascript" src="${staticPath}/js/common/datePicker/bootstrap-datetimepicker.js"></script>
	<script src="${staticPath}/js/scan/pdf.js"></script>
	<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	
	
</body>
</html>