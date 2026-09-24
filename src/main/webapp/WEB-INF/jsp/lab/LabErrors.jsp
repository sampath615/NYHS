<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Lab Errors</title>
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/lab/laftDetails.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
</head>

<body class="labErrors">
<%@ include file="../common/Menu.jsp"%>
	<div id="labErrorsPage" style="display:none">
      	<ul id="labErrorsTabs">
			<li><a href="#viewLabDocumentErrorsTab"  id="documentErrorsTab">Lab Errors</a></li>
			<li><a href="#labDocumentTab"  id="listLabDocumentViewTab" style="display:none">Document View</a></li>
		</ul>
	   <div id="viewLabDocumentErrorsTab">
	   
	   		<div class="alignCenter pB10">
	   			<span class="bold">Document Type :</span>
	   			<span class="mL10"><select onchange="loadDocumentErrorsData(this)">
	   				<option value="">Select</option>
	   				<c:if test="${userPermissions['174'] != null}" >
	   					<option value="HIV_LAB">HIV_LAB</option>
	   				</c:if>
	   				<c:if test="${userPermissions['157'] != null}" >
	   					<option value="AIRS">AIRS</option>
	   				</c:if>
	   				<c:if test="${userPermissions['195'] != null}" >
	   					<option value="PRF">PRF</option>
	   				</c:if>
	   			</select></span>
	   		</div>
	   
	   		<div id="LabErrorDetailsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   		
	   		<div id="labDocumentErrors" class="jqGridTableSection">
				<table id="labDocumentErrorsTable"></table>
				<table id="labDocumentErrorsPager"></table>
			</div>
			
			<div id="manageDocuments" class="button-row displayNone pT20" style=" text-align:center"> 
				<a id="addNotesToDocuments" class="a_disable fadedAddButton"
					tabindex="8" style="color: #FFFFFF;" onclick="addNotesToDocuments(this);"> Add Note </a>
				<a id="repriseDocuments" class="a_disable fadedResetButton"
					tabindex="9" style="color: #FFFFFF;" onclick="repriseDocuments(this);"> REPRISE </a> 
				<a id="deleteDocuments" class="a_disable fadedCancelButton"  style="color: red; margin-left:5px"
					tabindex="10" onclick="deleteDocuments(this);"> DELETE </a>
<!-- 				<a id="addProviderAddress" class="a_disable fadedAddButton"  style="margin-left:5px" -->
<!-- 					tabindex="10" onclick="addProviderDetails(this);"> Add Provider </a> -->
				<a id="doNotReport" class="a_disable fadedCancelButton"  style="color: #FFFFFF;margin-left:5px"
					tabindex="10" onclick="doNotReport(this);"> Do Not Report </a>
				<a id="addUnitsOfMeasure" class="a_disable fadedAddButton"  style="color: #FFFFFF;margin-left:5px"
					tabindex="10" onclick="addUnitsOfMeasure(this);"> Add Units of Measure </a>
				<a id="updateLabAddressData" class="a_disable fadedAddButton"  style="color: #FFFFFF;margin-left:5px"
					tabindex="10" onclick="updateLabAddressData(this);"> Update Lab Data </a>	
				<a id="disbleProcessErrors" class="a_disable fadedResetButton"  style="color: #FFFFFF;margin-left:5px"
				tabindex="10" onclick="disbleProcessErrors(this);"> Disable Process errors  </a>	
					
			</div>
	   </div>
	   <div id="labDocumentTab">
	   		<div id="LabDocumentEditTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   		<div id="labDocumentContent">
	   		
	   		</div>
	   </div>
   </div>
   <div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
		<div id="requestCommentsText">
   				<label style="padding-left:0">Comments </label></br>
         		<textarea id="requestComments"  cols="40" rows="4" maxLength="3000" title="<spring:message code='label.requestComments' />"></textarea>
	  	</div>
   </div>

   <div id="confirmAddNotesDialog" >
		<h4 id="confirmAddNotesContent"></h4>
		<div id="addNotesText">
   				<label style="padding-left:0">Add Notes </label></br>
         		<textarea id="addNotes"  cols="40" rows="4" maxLength="3000" title="Add Notes"></textarea>
	  	</div>
   </div> 
   
   <div id="confirmUpdateLabDataDialog" >
   
   		<h4 id="confirmUpdateLabDataContent"></h4>
   
   		<div class="alignCenter pB10">
   			<span class="bold">Address Type :</span>
   			<span class="mL10"><select id="hivLabAddressType" onchange="loadAddressFields(this)">
   				<option value="">Select</option>
   				<option value="PRSN">Person Address</option>
   				<option value="PROV">Provider</option>
   			</select></span>
   		</div>
   </div>  
   
   <div id="documentErrorCategoryDlg" class="displayNone">
   		<h4 id="confirmAddNotesContent">Select the Error code to by pass</h4>
   		<div id="documentErrorCategoryText">
   				
	  	</div>
		
   </div>
   
   <div id="confirmUnitsOfMeasureDialog" class="displayNone" >
		<h4 id="confirmUnitsOfMeasureContent"></h4>
		<div id="unitsOfMeasureText">
				<span class="bold mL10">Unit Measure Code: </span>
   				<span class="mL10"><input type="text"  id="editedUnitsOfMeasure" title="Units of Measure" /></span>
	  	</div>
   </div>   
   
   <div id="statenoDetails" class="displayNone">	
		<div id="dcContent"> 
			<span>
			<span >STATENO to be associated:</span>	
			<span class="mL10"><input type="text" class="required" name="associateStateno" id ="associateStateno" value="" maxlength="10" title="Stateno" /></span>
			</span><br/><br/>
			<span>
			<span >Comments</span>
         	<span class="mL10"><textarea id="addToStatenoRequestComments"  cols="40" rows="4" maxLength="3000" title="<spring:message code='label.requestComments' />"></textarea></span>
         	</span>
		</div>
	</div>
	
   <div id='docViewDlg'>	</div>
   <%@ include file="../common/addressComboDialog.jsp" %>
   
    <%@ include file="../common/commonScriptsIncludes.html"%>
    <script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
    <script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/reports/labCommonUtil.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/lab/laftDetails.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
    <script src="${staticPath}/js/review/prfReview.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body> 
