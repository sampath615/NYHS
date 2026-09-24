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

<body>
<%@ include file="../common/Menu.jsp"%>
	<div id="labHEDMaintanance" style="display:none">
      	<ul id="labHEDTabs">
			<li><a href="#viewHEDDetailsTab"  id="documentErrorsTab">HED Details</a></li>
			<li><a href="#labDocumentTab"  id="listLabDocumentViewTab" style="display:none">Document View</a></li>
		</ul>
	   <div id="viewHEDDetailsTab">
	   		<div id="HEDDetailsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   		
	   		<div id="HEDDetails" style="margin-bottom:18px;">
				<table id="HEDDetailsTable"></table>
				<table id="HEDDetailsPager"></table>
			</div>
			
			<div id="manageDocuments" class="button-row" style="display:block;  text-align:center"> 
				<a id="holdDocuments" class="a_disable fadedResetButton"
					tabindex="9" style="color: #FFFFFF;" onclick="holdDocuments(this);"> HOLD </a> 
				<a id="unholdDocuments" class="a_disable fadedResetButton"
					tabindex="9" style="color: #FFFFFF;" onclick="unholdDocuments(this);"> UNHOLD </a>
				<a id="deleteDocuments" class="a_disable fadedCancelButton"  style="color: red; margin-left:5px"
					tabindex="10" onclick="deleteDocuments(this);"> DELETE </a>
			</div>
	   </div>
	   <div id="labDocumentTab">
	   		
	   </div>
   </div>
   <div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
		<div id="requestCommentsText">
   				<label style="padding-left:0">Comments </label></br>
         		<textarea id="requestComments"  cols="40" rows="4" maxLength="3000" title="<spring:message code='label.requestComments' />"></textarea>
	  	</div>
   </div>
   <div id='docViewDlg'>	</div>
    <%@ include file="../common/commonScriptsIncludes.html"%>
    <script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/lab/HEDDetails.js?v=${buildDate}"></script>
</body> 
