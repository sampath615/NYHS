<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Record Search</title>
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
</head>

<body class="recordSearchPage">
<%@ include file="../common/Menu.jsp"%>
	<div id="recordSearchReviewPage" style="display:none" class="pageView">
      	<ul id="recordSearchReviewTabs">
			<li><a href="#viewRecordSearchReviewTab"  id="recordSearchStatnosTab">Record Search STATENOS</a></li>
			<li id="listPersonViewTab" style="display: none;"><a
				href="#personDetailsTab">Recent Demo View</a></li>
			<li id="listDocumentViewTab" style="display: none;"><a
				href="#documentDetailsTab" id="documentTimeline_search">Document Timeline</a></li>
			<li id="listEharsPersonViewTab"  style="display: none;"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>
			<li id="listLabDocumentViewTab" style="display: none;"><a href="#labDocumentTab">Document View</a></li>
			
		</ul>
	   <div id="viewRecordSearchReviewTab" class="jqGridTableSection">
	   
	   		
	   		<div id="recordSearchTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   
	   		<div id="recordSearchStatnos" >
				<table id="recordSearchStatnosTable"></table>
				<table id="recordSearchStatnosPager"></table>
			</div>
			
			
			<div id="confirmMessageDialog" title="Please Confirm?">
				<h4 id="confirmMessageContent"></h4>
				<div id="requestCommentsText" style="display:none" class="customInput">
		   			<label style="padding-left:0">Comments </label></br>
		         	<textarea id="requestComments"  cols="40" rows="4" style="border: thin solid #003366;" class="required" maxLength="3000" title="Comments"></textarea>
			  </div>
			  
			  <div id="fileNameSection" style="display:none"  class="customInput">
		   			<label style="padding-left:0" class="bold">Name of the File </label>
		         	<input id="cdcFileName"  class="required standardInputMedText mL10" title="File Name">
			  </div>
			  
			    
		   </div>
   
					<br />
					
					<div>
					
						<div class="floatLeft width60">
							<label class="redNote bold">Note:</label>
						</div>
						<div class="floatLeft">
							<label class="bold">For records highlighted in green an unsuccessful attempt was made to generate CDC file as there is no valid data </label>
							
							<br/><br/>
							
							<label class="bold">Origin(Manual-Warning): User chose to flag STATENO with no new information </label>
							
						</div>
					
					
					</div>
			
			<div id="actionButtons" class="clearBoth pT20" style="text-align:center">
					<a	class="actionButtons a_disable fadedCancelButton" id="unFlagStatenos"
										style="color: #FFFFFF; margin-left: 5px;"
										onclick="unFlagRecordSearchStatenos();"> Unflag </a>
										
										<a	class="actionButtons a_disable fadedSaveButton" id="generateCDCFile"
										style="color: #FFFFFF; margin-left: 5px;"
										onclick="generateCDCFile();"> Generate CDC File </a>
										
										<a	class="actionButtons a_demo_search a_demo_save" id="generateCDCFileForAllRecords"
										style="color: #FFFFFF; margin-left: 5px;"
										onclick="generateCDCFileForAllRecords();"> Generate CDC File For All Records</a>
										
										
								</div>
								
					
								
	   </div>
	   
	   <div id="personDetailsTab" style="clear: both; overflow: scroll">
			<!-- Tab content will be build dynamically  -->
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
		
		
		<div id="labDocumentTab" style="clear: both;width:100%" class="positionAbsolute">
		
			<div id="documentEditTransactionMessage" class="transactionMessage"><span class="message"></span></div>
			<div id="documentContent" class="clearBoth ">
			
			</div>
		</div>
		<div id="eharsPersonDetailsTab" style="clear: both; overflow: scroll">
		
		</div>
		
   </div>
   
   
   
	
	<div id="personTableContainerModal">
		<table id="personFieldValuesTbl"></table>
	</div>
	
	
	<div id='docViewDlg'>  </div>
	
	<div id="documentSelectionDialog" class="displayNone">
			<div style="text-align:center; font-weight:bold;margin:5px">
				  	<span class="searchFacilityTxt"> Document Type :</span>
					<span>
							<select id="addDocumentToStateno" class="searchOptions" onchange="addDocumentToStateno(this.value)">
								<option value="">Select</option> 
								<option value="A06.18" testType="RECORD_SEARCH">RECORD_SEARCH</option>
							</select>
					</span>
			</div>
	</div>
	
	
	<div id="historyInformationDialog">
		<table id="statenoHitoryTable"></table>
		<div id="statenoHitoryPagerDiv"></div>
	</div>	
   
    <%@ include file="../common/commonScriptsIncludes.html"%>
    
    <%@ include file="../common/addressComboDialog.jsp" %>
	
	<script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
	<script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/scan/pdf.js"></script>
	<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.fileDownload.js"></script>
    <script src="${staticPath}/js/review/recordSearchReview.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
    <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body> 
