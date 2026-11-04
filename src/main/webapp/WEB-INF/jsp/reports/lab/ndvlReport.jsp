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

	<title>Undetectable Viral Load Report</title>

</head>

<body bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png" class="ndvlReport">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../../common/Menu.jsp" %>
		<div id="smallContentTab">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="riaReviewTab" ><a href="#ndvlReportTab">NDVL Report</a></li>
						<li id="listPersonViewTab" style="display: none;"><a
				href="#personDetailsTab">Recent Demo View</a></li>
			<li id="listDocumentViewTab" style="display: none;"><a
				href="#documentDetailsTab" id="documentTimeline_search">Document Timeline</a></li>
				<li id="listLabDocumentViewTab" style="display: none;"><a
				href="#labDocumentTab">Document View</a></li>
			</ul>
					
					<div id="ndvlReportTab" style="clear: both;min-height:200px">
						<div id="ndvlReportTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						    
						    <div class="floatRight"><a href="javascript:downloadNdvlReport()" class="floatRight mT10 marginBot10" id="downloadPage">Download</a></div>
							<div id="ndvlRecords" class="clearBoth" style="margin:20px auto">
								<table id="ndvlRecordsTable"></table>
								<table id="ndvlRecordsPager"></table>
                                    
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
					
							
					</div>		
											
										
			</div> 
		</div>
		
		
		<div id='docViewDlg'>	</div>
		<div id='docSecondViewDlg'>	</div>
		
			
	   
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
	
	<c:if test="${userPermissions['144'] != null}">
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
	<%@ include file="../../common/addressComboDialog.jsp" %>
   </c:if>
	<%@ include file="../../common/commonScriptsIncludes.html" %>
    <script src="${staticPath}/js/reports/lab/ndvlReport.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.fileDownload.js"></script>
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
</html>


