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
	

	<title>RIA Activity Report</title>
</head>

<body bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../../common/Menu.jsp" %>
		<div id="smallContentTab">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="riaActivityReport" ><a href="#riaActivityReportTab">RIA Activity</a></li>
						<li id="listPersonViewTab" style="display: none;"><a
				href="#personDetailsTab">Recent Demo View</a></li>
			<li id="listDocumentViewTab" style="display: none;"><a
				href="#documentDetailsTab" id="documentTimeline_search">Document Timeline</a></li>
				<li id="listEharsPersonViewTab"  style="display: none;"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>
			</ul>
					
					<div id="riaActivityReportTab" style="clear: both;min-height:200px">
						<div id="riaTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div id="StatusCriteria" class="pT10 " >
							<form action="getRiaAcitivityReportsList" id="loadRiaActivityReportsData">
		
									
									<div id="createDateRange" class="marginBottom20 centerFlexRowHeaders" style="height:30px">
										
										
										<div class="">
											<span
												class="bold font12 displayNone" id="startDateLabel" >Start Date: </span><br/>
											<input id="riaActivityFromDate" class="required select2Input  date-picker mT5"  title="Start Date"  dependentField = "startDateLabel" placeholder="Start Date"/>
											
										</div>
										<div class=" mL10">
												<span class=""> 
													<span
														class="font12 bold displayNone" id="endDateLabel" >End date: </span><br/>
													<input id="riaActivityToDate"  class="required  select2Input date-picker form-control mT5" dependentField = "endDateLabel" title="End date" placeholder="End Date" 
															  />
												</span>
										</div>
										
										<div class=" mL10">
										<div style="margin-top:19px">
											<input type="button" onclick="getRiaActivityReport();" class="valignMiddle submitBtn "		title="RIA Activity report" value="submit"	id="loadRiaActivityReport">
										</div>
										</div>
									</div>
									
							
								</form>
								
							</div>
							
							<div id="RiaActivityRecords" class="container displayNone">
	 							<div class="example-header">
	 								<label class="standardInputTitle pb10 mL2">Quick Search</label>
									<input type="text" id="filter-text-box" class="standardInputText" placeholder="Search in data..." oninput="onFilterTextBoxChanged()">
									<button onclick="onBtExport()" style="margin-bottom: 5px; font-weight: bold;">Export</button>
								</div>			
	 							<div id="myGrid" style="height: 600px; width:800px;" class="ag-theme-balham"></div>						
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
    <%@ include file="../../common/commonagGridScriptsIncludes.html" %>        
    <script src="${staticPath}/js/main.js"></script>    	
    <script src="${staticPath}/js/reports/ria/RiaActivityReport.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
		<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
		
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.fileDownload.js"></script>
 
</body>
</html>


