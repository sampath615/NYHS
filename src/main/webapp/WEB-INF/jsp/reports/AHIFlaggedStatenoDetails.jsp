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
	<link href="${staticPath}/css/common/datePicker/bootstrap.css" rel="stylesheet">
	
	<title>AHI Flagged Statenos Details</title>
	
</head>

<body id="ahiFlaggedStatenosDetails" bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../common/Menu.jsp" %>
		<div id="mainContentDiv">		
					
				<div id="tabs">
					<ul id="Tabs">
						<li id="listAhiUnderReviewStatenosTab"><a href="#ahiUnderReviewTab">AHI Under Review</a></li>			
						<li id="listAhiUnflaggedStatenosTab"><a href="#ahiUnflaggedTab">AHI Unflagged</a></li>
<!-- 						<li id="listAhiFollowupReqStatenosTab"><a href="#ahiFollowupReqTab">AHI Follow-up Required</a></li>			 -->
						<li id="listAhiFollowupCompStatenosTab"><a href="#ahiFollowupCompTab">AHI Follow-up Completed</a></li>		
						<li id="listPersonViewTab" class="displayNone"><a href="#personDetailsTab" >Recent Demo View</a></li>
						<li id="listDocumentViewTab" class="displayNone"><a href="#documentDetailsTab" >Document Timeline</a></li>
						<li id="listEharsPersonViewTab"  class="displayNone"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>
						<li id="listAhiAlertTrackingTab" class="displayNone"><a href="#ahiAlertTrackingTab">AHI Alert Tracking</a></li>
						
					</ul>
					
					<div id="ahiUnderReviewTab" style="clear: both;">
						<div id="ahiFlaggedTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						<div class="container">
							<div class="example-header">
								<label class="standardInputTitle pb10 mL20">Quick Search</label>
								<input type="text" id="filter-text-box3" class="standardInputlabText1 float: right;" placeholder="Search in data..." oninput="onFilterTextBoxChanged3()">
								<button onclick="onBtExport3()" style="margin-bottom: 5px; font-weight: bold;float: right;">Export to Excel</button>
							</div>						
							<div id="myGrid3" style="height: 700px; width:1760px;" class="ag-theme-balham"></div>						
						</div>
					</div>
					
					<div id="ahiUnflaggedTab" style="clear: both;">
						<div class="container">
							<div class="example-header">
								<label class="standardInputTitle pb10 mL20">Quick Search</label>
								<input type="text" id="filter-text-box4" class="standardInputlabText1 float: right;" placeholder="Search in data..." oninput="onFilterTextBoxChanged4()">
								<button onclick="onBtExport4()" style="margin-bottom: 5px; font-weight: bold;float: right;">Export to Excel</button>
							</div>						
							<div id="myGrid4" style="height: 700px; width:1760px;" class="ag-theme-balham"></div>						
						</div>
					</div>
					
					<div id="ahiFollowupReqTab" style="clear: both;">
						<div class="container">
<!-- 							<div class="example-header"> -->
<!-- 								<label class="pb10 boldFont" style="padding-left:.1px;">AHI Flagged</label> -->
<!-- 								<label class="standardInputTitle pb10 mL20">Quick Search</label> -->
<!-- 								<input type="text" id="filter-text-box" class="standardInputlabText1 float: right;" placeholder="Search in data..." oninput="onFilterTextBoxChanged()"> -->
<!-- 								<button onclick="onBtExport()" style="margin-bottom: 5px; font-weight: bold;float: right;">Export to Excel</button> -->
<!-- 							</div>						 -->
<!-- 							<div id="myGrid" style="height: 700px; width:1760px;" class="ag-theme-balham"></div>						 -->
						</div>
					</div>
					
					<div id="ahiFollowupCompTab" style="clear: both;">
						<div class="container">
							<div class="example-header">
								<label class="pb10 boldFont" style="padding-left:.1px; ">All follow-up Completed</label>
								<input type="checkbox" class= "mL40" name="followupCompWithPRF" id="followupCompWithPRF" onclick="showHideFollowupCompWithPRF()"/>
								<label class="pb10 boldFont" style="padding-left:.1px; ">PRF Received only</label>
								<input type="checkbox" class= "mL40" name="followupCompWithoutPRF" id="followupCompWithoutPRF" onclick="showHideFollowupCompWithoutPRF()"/>
								<label class="pb10 boldFont" style="padding-left:.1px; ">PRF Not Received only</label>
								<label class="pb10 boldFont mL20">Quick Search</label>
								<input type="text" id="filter-text-box2" class="standardInputlabText1 mL10 float: right;" placeholder="Search in data..." oninput="onFilterTextBoxChanged2()">
								<button onclick="onBtExport2()" style="margin-bottom: 5px; font-weight: bold;float: right;">Export to Excel</button>
							</div>						
							<div id="myGrid2" style="height: 700px; width:1760px;" class="ag-theme-balham"></div>						
						</div> 
					</div>
					
					<div id="personDetailsTab" style="clear: both; overflow: scroll">

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
		            
					<div id="ahiAlertTrackingTab" style="clear: both;">
						<div id="ahiFlaggedTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						<div id="ahiAlertDetailsData"></div>						
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
   	</div>
   	
	</div>	
    <%@ include file="../common/commonScriptsIncludes.html" %>
	<%@ include file="../common/commonagGridScriptsIncludes.html" %>    
    <script src="${staticPath}/js/main.js"></script>
	<script src="${staticPath}/js/reports/ahiFlaggedStatenoDetails.js?v=${buildDate}"></script>    
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>	
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/search/search.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>	  
	
</body>
</html>


