<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	
	<title>LAFT Count Reconcile</title>
	
</head>

<body id="laftCountReconcileData" class="laftCountReconcile" bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../common/Menu.jsp" %>
		<div id="mainContentDiv"">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="listReconcileDashboardTab"><a href="#reconcileDashboardTab">Count Reconcile Dashboard</a></li>			
						<li id="listCurOpenSurveysTab" class="displayNone"><a href="#curOpenSurveysTab">Currently Open Labs</a></li>	
						<li id="listSurveyReportedTab" class="displayNone"><a href="#surveyReportedTab">Test Type Counts</a></li>	
						<li id="listOfaccessionTab" class="displayNone"><a href="#accessionListTab">PFI Accession List</a></li>	
					</ul>
 					
					<div id="reconcileDashboardTab" style="clear: both;">
						<div id="LAFTReconcileTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div style="float:left; width:350px">
							<label class="standardInputTitle">Collection Period</label>
							<select id="collectionPeriod" name="collectionPeriod"  class="mL10 standardInputMedText required" onchange="loadReconcileDashboardData(this.value)" name="Collection Period">
								<c:forEach items="${collPeriodList}" var="collectionPeriod">
									<option value="${collectionPeriod.key}" >${collectionPeriod.value}</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="container" style="width:800px;">
							<div class="example-header">
								<label class="standardInputTitle pb10 mL2">Quick Search</label>
								<input type="text" id="filter-text-box" class="standardInputlabText1 float: right;" placeholder="Search in data..." oninput="onFilterTextBoxChanged()">
								<button onclick="onBtExport()" style="margin-bottom: 5px; font-weight: bold;float: right;">Export to Excel</button>
							</div>						
							<div id="myGrid" style="height: 500px; width:800px;" class="ag-theme-balham"></div>						
						</div>
					</div>
 					
					<div id="curOpenSurveysTab" style="clear: both;">
						<div id="LAFTReconcileTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div class="container" style="width:1500px;">
							<div class="example-header">
								
								<label class="standardInputTitle pb10 mL2">Quick Search</label>
								<input type="text" id="filter-text-box2" class="standardInputlabText1 float: right;" placeholder="Search in data..." oninput="onFilterTextBoxChanged2()">
								<button onclick="onBtExport2()" style="margin-bottom: 5px; font-weight: bold;float: right;">Export to Excel</button>
							</div>						
							<div id="myGrid2" style="height: 700px; width:1500px;" class="ag-theme-balham"></div>						
						</div>
					</div>
					
 					
					<div id="surveyReportedTab" style="clear: both;">
						<div id="LAFTReconcileTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div class="container" style="width:1700px;">
						<div class="container" style="width:900px; float:left;">
							<div class="example-header">
								<label class="standardInputTitle pb10 mL2">Quick Search</label>
								<input type="text" id="filter-text-box3" class="standardInputlabText1 float: right;" placeholder="Search in data..." oninput="onFilterTextBoxChanged3()">
								<button onclick="refreshCurrentCount()" style="margin-bottom: 5px; font-weight: bold;">Refresh Current Count</button>
								<button onclick="getAccessionList()" style="margin-bottom: 5px; font-weight: bold;">Accession List</button>
								<button onclick="deletePFI()" style="margin-bottom: 5px; font-weight: bold;">Delete PFI</button>
								<button onclick="onBtExport3()" style="margin-bottom: 5px; font-weight: bold;float: right;">Export to Excel</button>
							</div>						
							<div id="myGrid3" style="height: 700px; width:900px;" class="ag-theme-balham"></div>						
						</div>
						<div class="container" style="width:800px; float:right;">
						
							<div class="container" style="padding:7px;">
								<div style="float:left; width:50%">
									<label class="standardInputTitle">Collection Period</label>
									<input id="selectedCollectionPeriod" class="mL10 standardInputSmallText readonly"/>
								</div>
								<div style="float:left; width:25%">
									<label class="standardInputTitle">PFI</label>
									<input id="labPfi" class="mL10 standardInputSmallText1 readonly"/>
								</div>
								<div style="float:left; width:25%">
									<label class="standardInputTitle">CLIA</label>
									<input id="labClia" class="mL10 standardInputSmallText2 readonly"/>
								</div>								

							</div>
						
							<div class="container" style="padding:7px;">
								<div style="float:left; width:75%">
									<label class="standardInputTitle">Facility Name</label>
									<input id="labFacilityName" class="mL10 standardInputText readonly"/>
								</div>							
								<div style="float:left; width:25%">
									<label class="standardInputTitle">LAFT Staff</label>
									<input id="laftUserId" class="mL10 standardInputSmallText1 readonly"/>
								</div>
							</div>
						
							<div class="container" style="padding:7px;">
								<div style="float:left; width:65%">
									<label class="standardInputTitle">Facility Address</label>
									<input id="labFacilityAddress" class="mL10 standardInputText readonly"/>
								</div>
								<div style="float:left; width:35%">
									<label class="standardInputTitle">Survey Completed Date</label>
									<input id="surveyCompletedDate" class="mL10 standardInputSmallText2 readonly" />
								</div>								
							</div>
						
							<div class="container" style="padding:7px;">
								<div style="float:left; width:35%">
									<label class="standardInputTitle">Lab Staff</label>
									<input id="labStaff" class="mL10 standardInputSmallText readonly"/>
								</div>
								<div style="float:left; width:30%">
									<label class="standardInputTitle">Phone No</label>
									<input id="labPhoneNum" class="mL10 standardInputSmallText2 readonly"/>
								</div>
								<div style="float:left; width:35%">
									<label class="standardInputTitle">E-mail</label>
									<input id="labEmail" class="mL10 standardInputMedText readonly"/>
								</div>								
							</div>
						
							<div class="container" style="padding:7px;">
								<div style="float:left; width:90%">
									<label class="standardInputTitle" style="vertical-align: top;">Comments</label>
									<textarea id="labComments" cols="80" rows="4" class="mL10 readonly roundedBorder" disabled="disabled"
												style="width:85%; height:50px; border:1px solid gray; background-color: #d3d3d382"></textarea>
								</div>
							</div>
		
							<div class="container" style="padding:7px;">
								<div style="float:left; width:28%">
									<label class="standardInputTitle">Contact Date</label>
									<input id="labContactDate" class="mL10 standardInputSmallText2 date-picker" readonly="true" />
								</div>
								<div style="float:left; width:15%">
									<label class="standardInputTitle">Reconciled</label>
									<input id="reconciled" type=checkbox class="mL2"/>
								</div>
								<div style="float:left; width:30%">
									<label class="standardInputTitle">Reconciled Date</label>
									<input id="labReconciledDate" class="mL10 standardInputSmallText2 readonly" />
								</div>
								<div style="float:left; width:25%">
									<label class="standardInputTitle">Accession Compare</label>
									<input id="accessionCompare" type=checkbox class="mL2"/>
								</div>							
							</div>
						
							<div class="container" style="padding:7px;">
								<div style="float:left; width:95%">
									<label class="standardInputTitle" style="vertical-align: top;">BHAE Comments</label>
									<textarea id="bhaeComments" cols="80" rows="6" class="mL10 readonly roundedBorder"
												style="width:80%; height:70px; border:1px solid gray;" maxlength="4000"></textarea>
								</div>
							</div>

							<div class="container" style="padding:7px;">
								<div style="float:right; width:50%">
								<button id="saveLaftCountReconcile" onclick="saveLabReconcileData()" style="margin-bottom: 5px;margin-top: 10px; font-weight: bold;">Save</button>										
								</div>
							</div>
									
							<div class="container" style="padding:7px;">
								<div style="float:left"><span class="assignmentHeaders">BHAE Comments History</span></div>
								<div class="clearBoth" ></div>
								<div id="bhaeCommentsHistory" class="bhaeComments roundedBorder" style="height:285px;width:95%;border:1px solid gray;overflow-y: scroll;resize: none;">
								</div>					
							</div>		
		
						</div>
						</div>
					</div>

 					
					<div id="accessionListTab" style="clear: both;">
						<div id="LAFTReconcileTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div class="container" style="width:1500px;">
							<div class="example-header">
								
								<label class="standardInputTitle pb10 mL2">Quick Search</label>
								<input type="text" id="filter-text-box4" class="standardInputlabText1 float: right;" placeholder="Search in data..." oninput="onFilterTextBoxChanged4()">
								<button onclick="onBtExport4()" style="margin-bottom: 5px; font-weight: bold;float: right;">Export to Excel</button>
							</div>						
							<div id="myGrid4" style="height: 700px; width:1500px;" class="ag-theme-balham"></div>						
						</div>
					</div>
										
				</div>		
		</div> 
	</div>	
	
   <div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
   </div>		
	
    <%@ include file="../common/commonScriptsIncludes.html" %>
	<%@ include file="../common/commonagGridScriptsIncludes.html" %>     
    <script src="${staticPath}/js/main.js"></script>
	<script src="${staticPath}/js/lab/LAFTCountReconcile.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/search/search.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>	  
	
</body>
</html>


