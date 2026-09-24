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
	

	<title>Lab Report</title>
</head>

<body bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../../common/Menu.jsp" %>
		<div id="smallContentTab">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="riaActivityReport" ><a href="#labReportTab">Lab Report</a></li>
						<li id="listPersonViewTab" style="display: none;"><a
				href="#personDetailsTab">Recent Demo View</a></li>
			<li id="listDocumentViewTab" style="display: none;"><a
				href="#documentDetailsTab" id="documentTimeline_search">Document Timeline</a></li>
				<li id="listEharsPersonViewTab"  style="display: none;"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>
			</ul>
					
					<div id="labReportTab" style="clear: both;min-height:200px">
						<div id="reportTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div id="StatusCriteria" class="pT10 " >
							<form action="getLabReportList" id="loadLabReportData">
		
									
									<div id="createDateRange" class="marginBottom20 centerFlexRowHeaders" style="height:30px">
										
										
										<div class="">
											<span
												class="bold font12 displayNone" id="startDateLabel" >Date range: </span><br/>
											<select id="dateRange" class="required select2Input   mT5"  title="Date range"  >
												<option value=""></option>
												<option value="0-30">0-30 Days</option>
												<option value="31-60">31-60 Days</option>
												<option value="61-90">61-90 Days</option>
												<option value="91-120">91-120 Days</option>
												<option value="121-180">121-180 Days</option>
												<option value="180">>180 Days</option>
											</select>
											
										</div>
										
										<div class=" mL10">
										<div style="margin-top:19px">
											<input type="button" onclick="getLabReport();" class="valignMiddle submitBtn "		title="Generate report" value="submit"	id="loadReport">
										</div>
										</div>
									</div>
									
							
								</form>
								
							</div>
							
<!-- 							<div class="floatRight"><a href="javascript:downloadLabReport()" class="floatRight mT10 marginBot10 displayNone" id="downloadPage">Download</a></div>     -->
							<div id="labReportRecords" class="clearBoth" style="margin:20px auto">
								<table id="labReportTable"></table>
								<table id="labReportPager"></table>
                                    
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
	
	<%@ include file="../../common/commonScriptsIncludes.html" %>
    <script src="${staticPath}/js/reports/lab/NoAdditionalReport.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.fileDownload.js"></script>
 
</body>
</html>


