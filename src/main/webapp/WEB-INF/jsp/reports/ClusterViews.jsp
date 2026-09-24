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
	
	<title>Cluster Views</title>
	
</head>

<body id="clusterView" bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../common/Menu.jsp" %>
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="listClusterTab"><a href="#clusterTab">Cluster View</a></li>			
						<li id="listPersonViewTab" class="displayNone"><a href="#personDetailsTab" >Recent Demo View</a></li>
						<li id="listDocumentViewTab" class="displayNone"><a href="#documentDetailsTab" >Document Timeline</a></li>				
					</ul>
					
					<div id="clusterTab" style="clear: both;">
						<div id="clusterReportsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div id="clusterTypeCriteria" class="pT10 pB20">
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
											<select name="clusterId" id="clusterId" class="required" title="Cluster ID"  >
												<option  value="" >Select</option>
											</select>
	                   						<div class="pL10 v_error clusterId"></div>															
										</div>										
										
										<div class="mL50 floatLeft">
											<input type="button" onclick="getClusterReportsByTypeAndId();" title="Cluster View" value="Cluster View" id="loadClusterReports">
										</div>
							</form>
								<!-- <div class="floatRight"><a href="javascript:printClusterReport()" class="floatRight marginBot10" id="printPage">Print</a></div> -->
						</div>
							
							<div id="ClusterTableContents" class="panel-body panel-body-table displayNone" style="text-align:center; border: 1px solid slategray">
                                          														
                               <div id="clusterReportData">

									<div class="tableReferenceDetails" id="ClusterReport">
										 <div class=" width90Perc pagebreak inlineBlock solidBorder2Black textAlignCenter ClusterReportByTypeAndId marginTop20 marginBottom10">
										 
										 <div class=" width90Perc font12pt">
<%--			 								<div class="mT20 fieldHolder25">
												<div><span>Cluster Type:</span> <span id="cluster_type"> ${clusterRptObj.clusterIdReportHeaderDetails.cluster_type_desc} </span></div>																						
												<div><span>Cluster ID: </span> <span id="cluster_rpt_id">${clusterId} </span></div>
												<div><span>Cluster ID Create Date: </span> <span id="cluster_date"> ${clusterRptObj.clusterIdReportHeaderDetails.cluster_create_date} </span></div>
												<div><span>Report Date: </span> <span id="cluster_rpt_dt"> ${clusterRptObj.clusterIdReportHeaderDetails.cluster_rpt_date}</span></div>
											</div>
 											<div class="mT20 fieldHolder75">								
												<div class="fieldHolder49">
													<div><span>Earliest eHARS Dx Date: </span> <span id="earliest_ehars_dx_date">${clusterRptObj.clusterIdReportHeaderDetails.lst_dx_dt} (${clusterRptObj.clusterIdReportHeaderDetails.lst_source}) </span></div>
													<div><span>NYEHMS STATENO:  </span> <span id="earliest_nyehms_stateno">${clusterRptObj.clusterIdReportHeaderDetails.l_nyehms_stateno} </span></div>
													<div><span>eHARS STATENO:  </span> <span id="earliest_ehars_stateno">${clusterRptObj.clusterIdReportHeaderDetails.l_ehars_stateno} </span></div>
													<div><span>CITYNO:  </span> <span id="earliest_citino">${clusterRptObj.clusterIdReportHeaderDetails.l_cityno} </span></div>									
												</div>
												<div class="fieldHolder49">
													<div><span>Latest eHARS Dx Date:  </span> <span id="latest_ehars_dx_date">${clusterRptObj.clusterIdReportHeaderDetails.grt_dx_dt} (${clusterRptObj.clusterIdReportHeaderDetails.grt_source})</span></div>
													<div><span>NYEHMS STATENO:  </span> <span id="latest_nyehms_stateno">${clusterRptObj.clusterIdReportHeaderDetails.g_nyehms_stateno} </span></div>
													<div><span>eHARS STATENO:  </span> <span id="latest_ehars_stateno"> ${clusterRptObj.clusterIdReportHeaderDetails.g_ehars_stateno} </span></div>
													<div><span>CITYNO:  </span> <span id="latest_citino">${clusterRptObj.clusterIdReportHeaderDetails.g_cityno} </span></div>									
												</div>
											</div> --%>
											<div class="fieldHolder100">
												<%-- <div class="mL50 textAlignLeft"><span>Cluster Description: </span> <span id="cluster_desc"> ${clusterRptObj.clusterIdReportHeaderDetails.cluster_desc}</span></div> --%>
												<div class= "alignRight"><span><b>Partial dates defaulted to 01 for eHARS date calculations. If multiple STATENOs result in same eHARS calculation date, one is randomly choosen for display.</b></span></div>																						
											</div>							
												
										 </div>
										
										<div style="overflow-x:auto;" class="clearBoth mT20">													                            							
											<table id ="clusterRptTable" > </table>
											<div id="clusterReportTablePager"></div>	
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
   			<label style="padding-left:0">Comments </label></br>
         	<textarea id="requestComments"  cols="40" rows="4" style="border: thin solid #003366;" class="required" maxLength="3000" title="Comments"></textarea>
	  </div>  
   </div>
	
    <%@ include file="../common/commonScriptsIncludes.html" %>
    <script src="${staticPath}/js/main.js"></script>
	<script src="${staticPath}/js/reports/clusterReports.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/search/search.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>	  
	
</body>
</html>


