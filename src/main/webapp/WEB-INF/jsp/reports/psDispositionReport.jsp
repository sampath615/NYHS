<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<meta http-equiv="cache-control" content="no-cache" />
	
	<%@ include file="../common/common_includes.jsp" %> 
    <%@ include file="../common/commonStylesIncludes.html" %> 
    <%@ include file="../common/MainMenuIncludes.jsp" %>
        
	<link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/assignments/assignments.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	
	<link href="${staticPath}/css/common/select2.css" rel="stylesheet"	type="text/css" />

	<title>${REPORT_NAME}</title>
	
	<script type="text/javascript">
		var reportName = '${REPORT_NAME}';
	</script>

</head>

<body bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../common/Menu.jsp" %>
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="psMessageReport" ><a href="#psMessageReportTab">${REPORT_NAME}</a></li>
						<li id="listPersonViewTab" style="display: none;"><a
				href="#personDetailsTab">Recent Demo View</a></li>
			<li id="listDocumentViewTab" style="display: none;"><a
				href="#documentDetailsTab" id="documentTimeline_search">Document Timeline</a></li>
				<li id="listEharsPersonViewTab"  style="display: none;"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>
			</ul>
					
					<div id="psMessageReportTab" style="clear: both;min-height:200px">
						<div id="psMessageTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div id="StatusCriteria" class="pT10 " >
							<form action="getPsMessageReportsList" id="loadPsMessageReportsData">
		
									
									<div id="createDateRange" class="marginBottom20 centerFlexRowHeaders" style="height:85px">
										
										
										<div class="">
											<span
												class="bold font12 displayNone" id="startDateLabel" >Start Date: </span><br/>
											<input id="psMessageFromDate" class="required select2Input  date-picker mT5"  title="Start Date"  dependentField = "startDateLabel" placeholder="Start Date"/>
											
										</div>
										<div class=" mL10">
												<span class=""> 
													<span
														class="font12 bold displayNone" id="endDateLabel" >End date: </span><br/>
													<input id="psMessageToDate"  class="required  select2Input date-picker form-control mT5" dependentField = "endDateLabel" title="End date" placeholder="End Date" 
															  />
												</span>
										</div>
										<div class=" mL10">	  
										<div>
										<span
											class="font12 bold displayNone" id="personCountyLabel">Person County: </span><br/>
										</div>
										<div class="mT5">
										<select id="psMessageCounty" class="required  width220 form-control valignMiddle " title="Person County"  multiple="multiple" dependentField = "personCountyLabel">
												  <option value="ALL">ALL</option>
												 <c:forEach items="${countyMap}" var="countyDetails">
											        <option value="${countyDetails.key}">${countyDetails.value}</option>
											    </c:forEach>
										</select>
										</div>
										
										</div>
										<c:if test="${REPORT_TYPE == 'disposition'}">
										<div class=" mL10">
										<div class="">
										<span
											class="font12 bold displayNone" id="messageTypeLabel" >Message Type: </span><br/>
										</div>
										<div class="mT5">
										<select id="psMessageType" class="required  form-control width280 valignMiddle mT5" title="Message Type"  multiple="multiple"  dependentField = "messageTypeLabel">
											<option value=""></option>
											<c:forEach items="${psMessageTypes}" var="messageType">
												<option value="${messageType.PS_MSG_TYPE}" class="${messageType.DOCUMENT_TYPE_CODE}">${messageType.PS_MSG_TYPE_DESCRIPTION}</option>
											</c:forEach>
										</select>
										</div>
										</div>
										<div class="floatLeft mL10">
										<div>
										<span
											class="font12 bold displayNone" id="dispositionsLabel" >Dispositions: </span><br/>
										</div>
										<div class="mT5">
										<select id="psMessageDispositions" class="required  form-control width300 standardInputLongText valignMiddle " title="Dispositions"  multiple="multiple"  dependentField = "dispositionsLabel">
											<option value=""></option>
											<option value="NO_DISPO">No Dispositions</option>
											<c:forEach items="${dispositionCodeValues}" var="dispositionCode">
												<option value="${dispositionCode.DISPOSITION_CODE}">${dispositionCode.DISPOSITION_DESC}</option>
											</c:forEach>
										</select>
										 
										</div>
										</div>
										</c:if>
										
										<div class=" mL10">
										<div style="margin-top:19px">
											<input type="button" onclick="getPsMessageReport('${REPORT_TYPE}');" class="valignMiddle submitBtn"		title="PS Message Report" value="submit"	id="loadpsMessageReport">
										</div>
										</div>
									</div>
									
							
								</form>
								
							</div>
							
							<div style="width:800px" class="marginAuto pB20 displayNone" id="downloadPsMessageReport">
								<div style="float:right">
							
									<input type="button" onclick="doGeneratePsMessageReport('${REPORT_TYPE}');" class="valignMiddle downloadBtn"		title="PS Message Report" value="Download"	id="downloadPsMessageReport">
							
								</div>
							</div>    
							<div id="psMessageRecords" class="clearBoth" style="margin:20px auto">
								<table id="psMessagesTable"></table>
								<table id="psMessagesPager"></table>
                                    
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
							<div id="eharsPersonDetailsTab" style="clear: both; overflow: scroll">
		
		   					</div>
					
							
					</div>		
											
										
			</div> 
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
	   </div>	
	
	<%@ include file="../common/commonScriptsIncludes.html" %>
    <script src="${staticPath}/js/reports/PsDispositionReport.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
		<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
		
	<script src="${staticPath}/js/common/select2.js"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.fileDownload.js"></script>
 
</body>
</html>


