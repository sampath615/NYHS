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
	
	<title>PS Message Reports</title>
	


</head>

<body bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../common/Menu.jsp" %>
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="psMessageReport" ><a href="#psMessageReportTab">PS Message Reports</a></li>
			</ul>
					
					<div id="psMessageReportTab" style="clear: both;">
						<div id="psMessageTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div id="StatusCriteria" class="pT10 pB20">
							<form action="getPsMessageReportsList" id="loadPsMessageReportsData">
		
								
									<div id="documentTypeSection" class="floatLeft" style="height:25px">
										<label
											class="standardInputTitle bold" id="textToDate" >Time Period: </label>
											
										<select id="documentType" class="required mL20" onchange="changeSearchCrieria(this)" title="Document Type">
											<option value="">Select</option>
											<option value="Monthly"  >Monthly</option>
											<option value="Weekly" >Weekly</option>
										</select>
									</div>
									
									<div id="createDateRange" class="floatLeft displayNone" style="height:25px">
										<label
											class="standardInputTitle bold" id="textToDate" >Date: </label>
											
										<label
											class="standardInputTitle bold" id="textToDate" >From: </label>
										<input id="fromDate"
											class="required  date-picker " title="From date" readonly />
										<input id="fromDate_monthly" type="hidden"
											 title="From date" readonly />
										
										
										<input type="hidden" id="fullDateFrom"
											class="" title="From date" readonly />
											<label
											class="standardInputTitle bold" id="textToDate" >To: </label>
											<input id="toDate" class="required date-picker form-control" title="To date" readonly 
												  />
												  <input type="hidden" id="fullDateTo"
											class="" title="To date" readonly />
											<input id="toDate_monthly" type="hidden"
											 title="To date" readonly />
										
											
										<span class="mL20 ">
											<input type="button" onclick="getPsMessageReport();" 		title="PS Message Report" value="submit"	id="loadpsMessageReport">
										</span>
									</div>
									
									
										
										
										
										
									
									
									
								
								</form>
								
								<div class="floatRight"><a href="javascript:downloadPsMessageReport()" class="floatRight mT10 marginBot10" id="downloadPage">Download</a></div>
							</div>
							
							<div id="tableContents" class="clearBoth panel-body panel-body-table displayNone" style="text-align:center; border: 1px solid slategray">
                                                                      
                                    
                            </div>
							
							
							
							
							
							
							
							</div>
														
							
					
							
					</div>		
											
										
			</div> 
		</div>	
	
	<%@ include file="../common/commonScriptsIncludes.html" %>
    <script src="${staticPath}/js/reports/PsMessageReport.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
 
</body>
</html>


