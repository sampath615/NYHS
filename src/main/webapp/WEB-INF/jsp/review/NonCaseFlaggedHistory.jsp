<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Non-case History</title>
<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<%@ include file="../common/MainMenuIncludes.jsp"%>

<link href="${staticPath}/css/common.css" rel="stylesheet"
	type="text/css" />
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />

</head>

<body class="assignmentReview">
	<%@ include file="../common/Menu.jsp"%>
	<div id="nonCaseReviewPage" class="pageView">
		<ul id="nonCaseReviewPageTab">
			<li><a href="#nonCaseDisplayContent"
				id="nonCaseDisplayContentTab" onclick="searchForNonCaseHistory('Y')">Non-case Flagged History</a></li>
			<li><a href="#nonCaseDisplayContent"
				id="unflaggedNonCaseDisplayContentTab" onclick="searchForNonCaseHistory('N')">Unflagged Non-case History</a></li>
			<li id="listPersonViewTab" style="display: none;"><a
				href="#personDetailsTab">Recent Demo View</a></li>
			<li id="listDocumentViewTab" style="display: none;"><a
				href="#documentDetailsTab" id="documentTimeline_search">Document Timeline</a></li>
			<li id="listEharsPersonViewTab"  style="display: none;"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>	
		</ul>
		<div id="nonCaseDisplayContent">
		
				<div id="nonCaseFlagCriteria" class="pT10 pB20">
							<div id="loadNonCaseFlaggedData" class="displayNone">
									<div id="createDateRange" class="floatLeft" style="height:25px">
										<font
											class="standardInputTitle" id="textToDate" >Date: </font>
											
										<font
											class="standardInputTitle" id="textToDate" >From: </font>
										<input id="fromDate"
											class="required  date-picker" title="From date" readonly />
											<font
											class="standardInputTitle" id="textToDate" >To: </font>
											<input id="toDate" class="required date-picker" title="To date" readonly 
												  />
									</div>
									
									<div class="mL50 floatLeft">
										<input type="button" onclick="searchForNonCaseHistory();" 		title="Non Case History" value="Non Case History" 		id="searchForNonCaseHistory">
									</div>
							</div>
							<div id="nonCaseHistoryData" class="jqGridTableSection clearBoth pT20">
								<table id="nonCaseHistoryTable"></table>
								<table id="nonCaseHistoryPager"></table>
							</div>
		

		
		</div>
	</div>
	<div id="personDetailsTab" style="clear: both; overflow: scroll">
			<!-- Tab content will be build dynamically  -->
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
			
								
				<div> 
					<label class="redNote">Note:</label><span class="textDecorationWithGray">Documents highlighted in gray font are Discard documents</span>
				</div>
				<div> 
					<label class="redNote">Note:</label><span class="textDecorationLineThrough">Documents with line through and with gray font are Deleted documents</span>
				</div>
			
		</div>
		<div id="eharsPersonDetailsTab" style="clear: both; overflow: scroll">
		
		</div>

</div>
<div id='docViewDlg'>	</div>
<div id="confirmMessageDialog" >
			<h4 id="confirmMessageContent"></h4>
			<div id="requestCommentsText" style="display:none">
	   			<span class="boldHeaders valignTop" ><spring:message code="label.comments" /></span>
          		<span class="mL35"><textarea id="requestComments" class="required" cols="40" class="" rows="4" style="border: thin solid #003366;" title="<spring:message code='label.requestComments' />"></textarea></span>
		  </div>  
		  
			  
	   </div>

	
	<%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/reports/noncase.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	    <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body>
		
	
	
	
