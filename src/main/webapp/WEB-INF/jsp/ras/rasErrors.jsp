<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>RAS Errors</title>
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/lab/laftDetails.css?v=${buildDate}" rel="stylesheet" type="text/css" />
</head>

<body class="rasErrors">
<%@ include file="../common/Menu.jsp"%>
	<div id="rasErrorsPage" style="display:none">
      	<ul id="rasErrorsTabs">
			<li><a href="#viewRasDocumentErrorsTab"  id="documentErrorsTab">RAS Errors</a></li>
		</ul>
	   <div id="viewRasDocumentErrorsTab">
	   
	   		<div id="RasErrorDetailsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   		
	   		<div id="rasDocumentErrors" class="jqGridTableSection">
				<table id="rasDocumentErrorsTable"></table>
				<table id="rasDocumentErrorsPager"></table>
			</div>
			
			<div id="manageDocuments" class="button-row pT20" style=" text-align:center"> 
				<a id="repriseDocuments" class="a_disable fadedResetButton"
					tabindex="9" style="color: #FFFFFF;" onclick="repriseDocuments(this);"> REPRISE </a> 
				<a id="deleteDocuments" class="a_disable fadedCancelButton"  style="color: red; margin-left:5px"
					tabindex="10" onclick="deleteDocuments(this);"> DELETE </a>
				
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


   
    <%@ include file="../common/commonScriptsIncludes.html"%>
    <script src="${staticPath}/js/lab/rasErrorDetails.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body> 
