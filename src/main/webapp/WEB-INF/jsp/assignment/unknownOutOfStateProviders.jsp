<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	
	<title><c:out value="${title}" /></title>
	
	
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
</head>

<body>
<%@ include file="../common/Menu.jsp"%>
	<div id="unknownOutOfStateProvidersPage" style="display:none" class="pageView">
      	<ul id="unknownOutOfStateProvidersTabs">
			<li><a href="#viewUnknownOutOfStateProvidersTab"  id="unknownOutOfStateProviderDocumentsTab"><c:out value="${title}" /></a></li>
			<li><a href="#labDocumentTab"  id="listLabDocumentViewTab" style="display:none">Document View</a></li>
			<li id="listPersonViewTab" style="display: none;"><a
				href="#personDetailsTab">Recent Demo View</a></li>
			<li id="listDocumentViewTab" style="display: none;"><a
				href="#documentDetailsTab" id="documentTimeline_search">Document Timeline</a></li>
			<li id="listEharsPersonViewTab"  style="display: none;"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>
		</ul>
	   <div id="viewUnknownOutOfStateProvidersTab">
	   		<div id="unknownOutOfStateProvidersTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   		
	   		
	   		<div id="unknownOOSProvidersSearchCriteria" class="pT10 pB20">
							<form action="loadUnknownProviders" id="loadUnknownProviders">
		
								
									<div class="alignCenter">
										  	<span>
												<font class="standardInputTitle" id="workersFont">&nbsp;
													Document Type :</font> 
												<select name="documentType" id="documentType" class="required" title="Document Type"  >
													<option  value="" >Select</option>
													<c:forEach items="${documentTypes}" var="documentType">
														<option value="${documentType}" >${documentType}</option>
													</c:forEach>
													
												</select>
											</span>
											
											<c:if test="${actionName == 'unknowOOSProviders'}">
												<span class="mL50 ">
													<font class="standardInputTitle" id="workersFont">&nbsp;
														Category :</font> 
													<select name="category" id="category" class="required" title="Process Status"  >
														<option  value="" >Select</option>
														<option  value="5" >Out of state</option>
														<option  value="4" >Unknown</option>
														
													</select>
												</span>
											</c:if>
											
											<span class="mL50 ">
												<input type="button" onclick="searchUnknownProviders('${actionName}');" 		title="Search" value="Search">
											</span>
										</div>
										
										
										
									
									
								
								</form>
								
							</div>
	   		
	   		<div id="unknownOutOfStateProviderDocuments" style="margin-bottom:18px;overflow-x:scroll;overflow-y:auto">
				<table id="unknownOutOfStateProvidersTable"></table>
				<table id="unknownOutOfStateProvidersPager"></table>
			</div>
	   </div>
	   <div id="labDocumentTab" class="documentShowTab positionAbsolute" style="width:100%">
	   		<div id="LabDocumentEditTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   		<div id="documentContent">
	   		
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
		<div id="requestCommentsText" style="display:none">
   			<label style="padding-left:0">Comments </label></br>
         	<textarea id="requestComments"  cols="40" rows="4" style="border: thin solid #003366;" class="required" maxLength="3000" title="Comments"></textarea>
	  </div>  
   </div>
	
	<div id="personTableContainerModal">
		<table id="personFieldValuesTbl"></table>
	</div>
	
	
	<div id='docViewDlg'>  </div>
	
	
   
  	<%@ include file="../common/addressComboDialog.jsp" %>
    <%@ include file="../common/commonScriptsIncludes.html"%>
    <script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
    <script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/scan/pdf.js"></script>
	<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/reports/labCommonUtil.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/assignments/unknownOutOfStateProviders.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/person/commonDocEdit.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body> 
