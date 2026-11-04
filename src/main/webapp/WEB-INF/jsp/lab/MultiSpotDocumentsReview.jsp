<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Combining Lab Reports</title>
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %>
	<style type="text/css">
		#button-row{
		display:none;
		}
	</style> 
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />

</head>

<body>
<%@ include file="../common/Menu.jsp"%>
	<div id="multiSpotDocumentsReviewPage"  class="pageView">
      	<ul id="multiSpotDocumentsReviewTabs">
			<li><a href="#viewMultiSpotDocumentsTab"  id="multiSpotDocumentsTab">Combining Lab Reports</a></li>
		</ul>
	   <div id="viewMultiSpotDocumentsTab">
	   		<div id="multiSpotDocumentsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   			   		<div id="multiSpotSearchCriteria" class="pT10 pB20">
							<form action="multiSpotReviewForm" id="multiSpotReviewForm" >
									<div class="alignCenter">
											
										  	<span class="mL50">
												<font class="standardInputTitle" id="workersFont">&nbsp;
													Pairing Category :</font> 
												<select name="multiSpotCategory" id="multiSpotCategory" class="required" title="Multi spot Category"  onChange="searchForMultiSpotDocuments(this)">
													<option  value="" >Select</option>
<!-- 													<option  value="Multi spot" >Multispot</option> -->
													<c:forEach items="${loincPfiCombinedData}" var="pfiLoincData">
														<option loincs="${pfiLoincData.LOINCS}" newDocument="${pfiLoincData.NEW_DOCUMENT}" groupCount="${pfiLoincData.GROUP_COUNT}" pfi="${pfiLoincData.PFI}"  value="${pfiLoincData.GROUP_ID}">${pfiLoincData.CATEGORY_NAME}</option>
													</c:forEach>
												</select>
											</span>
										</div>
								</form>
								
							</div>
	   		<div id="multiSpotDocumentsForReview" class="displayNone" style="margin-bottom:18px;overflow-x:scroll;overflow-y:auto">
				<table id="multiSpotDocumentsReviewTable"></table>
				<table id="multiSpotDocumentsReviewPager"></table>
			</div>
			
			<div id="manageDocuments" class="button-row displayNone" style=" text-align:center"> 
				<a id="processDocumentsAsGroup" class="a_disable fadedResetButton"
					tabindex="8" style="color: #FFFFFF;" onclick="processDocumentsAsGroup();"> Process </a>
				<a id="deleteDocuments" class="a_disable fadedCancelButton"  style="color: red; margin-left:5px"
					tabindex="10" onclick="deleteDocuments(this)"> DELETE </a>
				<a id="changeLoinc" class="a_disable fadedResetButton"  style="color: red; margin-left:5px"
					tabindex="10" onclick="changeLoinc(this)"> Change Loinc </a>
	
			</div>
	   </div>
   </div>
   

	
	
	<div id="confirmMergeStNoDialog" title="Please Confirm?" >
			<h4 id="confirmMessageStNoContent"> Which Document content you want to retain? </h4>
			<div id="confirmMergeStNoRadios">	   			
				<input type="radio" requiredgroup="requiredgroup" name="multiStepRetainDocument" id="multiStepRetainDocument1" value="" title="Document1" />
				<span id="multiStepRetainDocument1Label"></span><br>
				<input type="radio" requiredgroup="requiredgroup" name="multiStepRetainDocument" id="multiStepRetainDocument2" value="" title="Document2"/>
				<span id="multiStepRetainDocument2Label"></span>  
		  </div>
	   </div>  
	<div id='docViewDlg'>  </div>		
	
	<div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
   </div>		
		
    <%@ include file="../common/commonScriptsIncludes.html"%>
    <script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/reports/labCommonUtil.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/lab/multiSpotDocumentsReview.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body> 
