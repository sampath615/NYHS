<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	
	<title>VL Review</title>
	
	
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
</head>

<body>
<%@ include file="../common/Menu.jsp"%>
	<div id="vlDocsPage" style="display:none" class="pageView">
      	<ul id="vlDocsTabs">
			<li><a href="#viewVlDocsTab"  id="unknownOutOfStateProviderDocumentsTab">VL Review</a></li>
			<li id="listPersonViewTab" style="display: none;"><a
				href="#personDetailsTab">Recent Demo View</a></li>
			<li id="listDocumentViewTab" style="display: none;"><a
				href="#documentDetailsTab" id="documentTimeline_search">Document Timeline</a></li>
			<li id="listEharsPersonViewTab"  style="display: none;"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>
		</ul>
	   <div id="viewVlDocsTab">
	   		<div id="vlDocsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   		
	   		
	   		<div id="unknownOutOfStateProviderDocuments" style="margin-bottom:18px;overflow-y:auto">
				<table id="vlDocsTable"></table>
				<table id="vlDocsPager"></table>
			</div>
			
			<div id="manageDocuments" class="button-row pT20" style=" text-align:center"> 
				<a id="disbleProcessVLDoc" class="a_disable fadedResetButton"  style="margin-left:5px; color: #FFFFFF;"
				tabindex="10" onclick="disbleProcessVLDocs(this);"> Process  </a>	
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
		<div id="eharsPersonDetailsTab" style="clear: both; overflow: scroll">
		
		</div>
		
		
   </div>
   
   
   <div id="confirmMessageDialog" title="Please Confirm?" >
		<h4 id="confirmMessageContent"></h4>
		<div class="mT10">
		   			<span class="boldHeaders" >Review Category:</span>
	          		<span class="mL10">
		          		<select id="reviewCategory" class="required standardInputText" title="Review Category">
		          			<option value=""></option>
		          			<c:forEach items="${reviewCategories}" var="reviewCategory">
		          				<option value="${reviewCategory['REVIEW_ACTION']}" >${reviewCategory['REVIEW_DESC']}</option>
		          			</c:forEach>
						</select>
	          		</span>
			  </div>
			  
		<div class="mT20">
		   			<span class="boldHeaders" >Comments:</span>
	          		<span class="mL10">
		          		<textarea id="requestComments"  cols="40" rows="4" style="border: thin solid #003366;" class="required" maxLength="3000" title="Comments"></textarea>
	          		</span>
			  </div>	  
   </div>
	
	<div id="personTableContainerModal">
		<table id="personFieldValuesTbl"></table>
	</div>
	
	
	<div id='docViewDlg'>  </div>
	
	<div id="historyInformationDialog">
		<table id="statenoHitoryTable"></table>
		<div id="statenoHitoryPagerDiv"></div>
	</div>
   
  	<%@ include file="../common/addressComboDialog.jsp" %>
    <%@ include file="../common/commonScriptsIncludes.html"%>
    <script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
    <script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/scan/pdf.js"></script>
	<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/reports/labCommonUtil.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/assignments/vlReview.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/person/commonDocEdit.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body> 
