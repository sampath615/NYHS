<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
	<link href="${staticPath}/css/docs/clusterForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common/datePicker/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" media="screen" href="${staticPath}/css/common/datePicker/bootstrap-datetimepicker.css">	
	
	<title>Cluster Reports</title>
	
</head>

<body id="clusterReport" class="clusterReport" bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../common/Menu.jsp" %>
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="listClusterTab"><a href="#clusterTab">Cluster Report</a></li>			
						<li id="listPersonViewTab" class="displayNone"><a href="#personDetailsTab" >Recent Demo View</a></li>
						<li id="listDocumentViewTab" class="displayNone"><a href="#documentDetailsTab" >Document Timeline</a></li>
						<li id="listEharsPersonViewTab" class="displayNone"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>				
					</ul>
					
					<div id="clusterTab" style="clear: both;">
						<div id="clusterReportsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div id="clusterTypeCriteria" class="pT10">
							<form action="getClusterReportsList" id="loadClusterReportsData">
		
										<div class="floatLeft">
											<font class="standardInputTitle" id="workersFont">&nbsp;	Cluster Type :</font> 
											<select name="clusterType" id="clusterType" class="required" title="Cluster Type" onchange="changeClusterType(this)" >
												<option  value="" >Select</option>
												<c:forEach items="${clusterTypeList}" var="clusterType">
													<option value="${clusterType.key}" >${clusterType.value}</option>
												</c:forEach>
											</select>
	                   						<div class="pL10 v_error clusterType"></div>											
										</div>
										
										<div class="floatLeft displayNone" id="clusterIdSection">
											<font class="standardInputTitle" id="workersFont">&nbsp;	Cluster ID:</font> 
											<select name="clusterId" id="clusterId" class="required" title="Cluster ID" onchange="getClusterDisplayInfo(this)" >
												<option  value="" >Select</option>
											</select>
	                   						<div class="pL10 v_error clusterId"></div>															
										</div>										
										
 										<div class="floatLeft displayNone" id="clusterCriteriaDiv">
											<a class="pL10 tooltip" title="Cluster Criteria" href="#" onclick="javascript:openClusterCriteriaDialog()">Criteria Info</a>
										</div>
										
										<div class="mL50 floatLeft">
											<input type="button" onclick="getClusterReportsByTypeAndId('A');" title="Cluster Report" value="Cluster Report" id="loadClusterReports">
											<!--  if any changes to this onclick function update confirmRejectClusterPsMessage js method -->
										</div>
 										<br><br>
								</form>
								
								<!-- <div class="floatRight"><a href="javascript:printClusterReport()" class="floatRight marginBot10" id="printPage">Print</a></div> -->
							</div>
							
							<div id="ClusterTableContents" class="panel-body panel-body-table displayNone">
                                          														
                                          														
                                          							<div id="clusterReportData">

																			<div class="tableReferenceDetails" id="ClusterReport">
																				 <div class=" width97Perc pagebreak inlineBlock textAlignCenter ClusterReportByTypeAndId marginBottom10">

																				<div style="overflow-x:auto;" class="clearBoth mT20">													                            							
																					<table id ="clusterRptTable" > </table>
																					<div id="clusterReportTablePager"></div>	
																				</div>
																				
																				<div id="openAssignment90DaysNote" class="floatLeft mT10">
																					<label class="highlightOrangeRedColor">Note: Cluster partner documents highlighted in orange font are not reviewed.</label>
																				</div>
																																								
																				
																				</div>
																			</div>
													
													
													</div>	
                                          														
                                          														
                            </div>
                            
							</div>
							
							<div id="personDetailsTab" style="clear: both; overflow: scroll">

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
							<div id="eharsPersonDetailsTab" style="clear: both; overflow: scroll">
							</div>
					</div>		
											
										
			</div> 
		</div>	
		
	<div id='docViewDlg'>	</div>	
	<div id='docSecondViewDlg'>	</div>	
	<div id="printDialog" title="Print Preview?" >
		
		<a href="javascript:printPage('#printDialogContent','Cluster Report')" class="floatRight mT10 marginBot10">Print</a>
		<div class="clearBoth"></div>
		
		<div id="printDialogContent" style="background-color:white;" class="tableReferenceDetails">
		
		
		</div>
	</div>	
	<div id="editor"></div>		
		   	
	<div id="addToClusterDialog">
		<%@ include file="../common/addClusterToStateno.jsp" %>	
	</div>

 	<div id="confirmMessageDialog" title="Please Confirm?" >
		<h4 id="confirmMessageContent"></h4>
		<div id="requestCommentsText" style="display:none">
   			<label style="padding-left:0">Comments </label><br/>
         	<textarea id="requestComments"  cols="40" rows="4" style="border: thin solid #003366;" class="required" maxLength="3000" title="Comments"></textarea>
	  </div>
	  
	  <div id="requiredTempParameters" class="displayNone" class="mT10">  </div>
   </div>
	
	<%@ include file="../common/addressComboDialog.jsp" %>
	
    <%@ include file="../common/commonScriptsIncludes.html" %>
    <script src="${staticPath}/js/main.js"></script>
	<script src="${staticPath}/js/reports/clusterReports.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/review/clusterPartner/clusterPartnerPSReview.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/search/search.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>	  
	<script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
	<script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>	
</body>

	<div id="clusterCriteriaDialog">
		<div id="clusterCriteriaContent" style="background-color:white;" ></div>
	</div>
		
</html>


