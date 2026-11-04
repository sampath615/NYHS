<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	
	<title>CDESS Messages Review</title>
	
	
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
</head>

<body class="reviewPsMessagesPage">
<%@ include file="../common/Menu.jsp"%>
	<div id="cdessMessagesPage" style="display:none" class="pageView">
      	<ul id="psMessageTabs">
			<li><a href="#viewPsMessageReviewTab"  id="psMessageReviewTab">PS Messages</a></li>
			<li id="listPersonViewTab" style="display: none;"><a
				href="#personDetailsTab">Recent Demo View</a></li>
			<li id="listDocumentViewTab" style="display: none;"><a
				href="#documentDetailsTab" id="documentTimeline_search">Document Timeline</a></li>
			<li id="listEharsPersonViewTab"  style="display: none;"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>
		</ul>
	   <div id="viewPsMessageReviewTab">
	   		<div id="psMessageTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   		<div id="psReviewTabsByStatusContent" style="width:100%">
		   		<ul id="psReviewTabsByStatus">
					<li id="unReviewedPSTab"><a href="#psReviewRecordsTab"  onClick="loadPsMessagesReviewTable('No','ALL_MSG_TYPE','ALL_ELIG')">Pending</a></li>
					<li id="reviewedPSTab"><a href="#psReviewRecordsTab"   onClick="loadPsMessagesReviewTable('YES')">Resolved</a></li>
					
						
				</ul>
				<div id="psReviewRecordsTab">
			
				<div class="alignCenter pB10" id="psMessageFilterTypeContent">
							<span class="bold">Message Type :</span> <span class="mL10">
								<select id="psMessageType"
									onchange="loadSelectedFiterTypepsMsgData()">
										<option value="ALL_MSG_TYPE" class="ELIGIBLE">All</option>
										<option value="PRF_PS_REQUEST" class="ELIGIBLE">PRF PS Request</option>
										<option value="900_MS_INCOMP" class="ELIGIBLE">900 MS Incomp</option>
										<option value="POSSIBLE_AHI" class="ELIGIBLE">Possible AHI</option>
								</select>
							</span>
							<span class="bold mL50">Filter Type :</span> <span class="mL10">
								<select id="psMessageFilterType"
									onchange="loadSelectedFiterTypepsMsgData()">
										<option value="ALL_ELIG" class="ELIGIBLE">All</option>
										<option value="LAFT_NEEDS_REVIEWED" class="ELIGIBLE">LAFT TO BE REVIEWED</option>
								</select>
							</span>
						</div>
					
				<!-- 	<div class="alignCenter pB10" id="psMessageFilterTypeContent">
							<span class="bold">Filter Type :</span> <span class="mL10">
							<select id="psMessageFilterType"
								onchange="loadSelectedFiterTypepsMsgData(this)">
									<option value="ALL_ELIG" class="ELIGIBLE">All</option>
									<option value="LAFT_NEEDS_REVIEWED" class="ELIGIBLE">LAFT TO BE REVIEWED</option>
							</select></span>
						</div> -->
					<div id="psMessages" class="marginBottom20">
                        <table id="psMessagesTable"></table>
						<table id="psMessagesPager"></table>
					</div>
				</div>
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
   
   
   <div id="confirmMessageDialog" title="Please Confirm?" >
		<h4 id="confirmMessageContent"></h4>
		<div id="requestCommentsText" style="display:none">
   			<label style="padding-left:0">Comments </label></br>
         	<textarea id="requestComments"  cols="40" rows="4" style="border: thin solid #003366;" class="required" maxLength="3000" title="Comments"></textarea>
	  </div>  
   </div>
	
	<div id="personTableContainerModal">
		<table id="personFieldValuesTbl"></table>
	</div>
	
	
	<div id='docViewDlg'>  </div>
	
    <%@ include file="../common/commonScriptsIncludes.html"%>
    <script src="${staticPath}/js/scan/pdf.js"></script>
	<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/review/PsMessageReview.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body> 
