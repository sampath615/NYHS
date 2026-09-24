<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<meta http-equiv="cache-control" content="no-cache" />
	
	<%@ include file="../common/common_includes.jsp" %> 
    <%@ include file="../common/commonStylesIncludes.html" %> 
    <%@ include file="../common/MainMenuIncludes.jsp" %>
        
	<link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/assignments/assignments.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	
	<title>Assignment Reports</title>
	<style>
	.ui-datepicker-calendar {
    display: none;
    }
	</style>
</head>

<body bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../common/Menu.jsp" %>
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="listAssignmentsTab"><a href="#assignmentsTab">Assignments Reports</a></li>				
					</ul>
					
					<div id="assignmentsTab" style="clear: both;">
						<div id="assignmentReportsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div id="asssignmentsStatusCriteria" class="pT10 pB20">
							<form action="getAssignmentsReportsList" id="loadAssignmentReportsData">
		
								
									<div id="dateRange" class="floatLeft" style="height:25px">
										<font
											class="standardInputTitle" id="textToDate" >Date: </font>
											
										<font
											class="standardInputTitle" id="textToDate" >From: </font>
										<input id="fromDate"
											class="required  date-picker"  readonly/>
											<font
											class="standardInputTitle" id="textToDate" >To: </font>
											<input id="toDate" class=" date-picker"
												  />
									</div>
									
									<div class="floatLeft">
										<font class="standardInputTitle" id="workersFont">&nbsp;
											Regions :</font> 
										<select name="regions" id="regions" class="required" title="Region"  >
											<option  value="ALL" >ALL</option>
											<c:forEach items="${regionsList}" var="regionDetails">
												<option value="${regionDetails.key}" >${regionDetails.value}</option>
											</c:forEach>
											
										</select>
									</div>
										
										
										<div class="mL50 floatLeft">
											<input type="button" onclick="searchAssignmentReports();" 		title="Assignment Reports" value="Assignment Reports" 		id="loadAssignmentReports">
										</div>
									
									
								
								</form>
								
								<div class="floatRight"><a href="javascript:printAssignmentReports()" class="floatRight mT10 marginBot10" id="printPage">Print</a></div>
							</div>
							
							<div id="tableContents" class="panel-body panel-body-table displayNone" style="text-align:center; border: 1px solid slategray">
                                                                      
                                    
                            </div>
							
							
							
							
							
							
							
							</div>

					</div>		
											
										
			</div> 
		</div>	
		
		
	<div id="printDialog" title="Print Preview?" >
		
		<a href="javascript:printPage('#printDialogContent','Assignments Report')" class="changeAddressLinks floatRight mT10 marginBot10">Print</a>
		<div class="clearBoth"></div>
		
		<div id="printDialogContent" style="background-color:white;" class="tableReferenceDetails">
		
		
		</div>
	</div>		
	<div id="editor"></div>	
		
    <%@ include file="../common/commonScriptsIncludes.html" %>
    <script src="${staticPath}/js/main.js"></script>
		<script src="${staticPath}/js/assignments/assignmentReports.js?v=${buildDate}"></script>
	  <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body>
</html>


