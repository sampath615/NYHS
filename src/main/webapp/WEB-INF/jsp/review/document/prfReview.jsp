<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>PRF Review</title>
	<%@ include file="../../common/common_includes.jsp" %> 
	<%@ include file="../../common/commonStylesIncludes.html" %> 
	<%@ include file="../../common/MainMenuIncludes.jsp" %> 
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/review/prfReview.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	
	
</head>

<body>
<%@ include file="../../common/Menu.jsp"%>
	<div id="prfReviewPage" style="display:none">
      	<ul id="prfReviewTabs">
			<li><a href="#viewPrfReviewDocumentsTab"  id="prfDocumentsTab">PRF Review</a></li>
			<li><a href="#labDocumentTab"  id="listLabDocumentViewTab" style="display:none">Document View</a></li>
		</ul>
	   <div id="viewPrfReviewDocumentsTab">
	   		<div id="prfReviewDetailsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   		
	   		<div id="prfReviewDocuments" style="margin-bottom:18px;" class="jqGridTableSection">
				<table id="prfReviewDocumentsTable"></table>
				<table id="prfReviewDocumentsPager"></table>
			</div>
	   </div>
	   <div id="labDocumentTab">
	   		<div id="LabDocumentEditTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   		<div id="documentContent">
	   		
	   		</div>
	   </div>
   </div>
   <div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
   </div>
   <div id='docViewDlg'>	</div>
   <%@ include file="../../common/addressComboDialog.jsp" %>

   
    <%@ include file="../../common/commonScriptsIncludes.html"%>
    <script src="${staticPath}/js/review/prfReview.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
        <script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
    <script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
    
    
</body> 
