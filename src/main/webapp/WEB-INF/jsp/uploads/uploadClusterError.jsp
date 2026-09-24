<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload CDC Cluster IDs</title>
<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<%@ include file="../common/MainMenuIncludes.jsp"%>

	<link href="${staticPath}/css/upload/upload.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/common.css" rel="stylesheet"	type="text/css" />

</head>

<body class="uploadClusterError">
	<%@ include file="../common/Menu.jsp"%>
	<div id="uploadClusterErrorsPage" style="display:none">
	
		<ul id="uploadClusterErrorsTab">
			<li><a href="#viewUploadClusterErrorsTab" id="clusterDocumentErrorsTab">Errors From Upload</a></li>
		</ul>
		
		<div id="viewUploadClusterErrorsTab">
		
			<div class="alignCenter pB10" id="clusterIdSection">
				<span class="bold">Cluster ID :</span>
				<span class="mL10"><select onchange="loadErrorClusterDocuments(this)">
					<option value="">Select</option>
					<c:forEach items="${clusterIds}" var="clusterId">
						<option value="${clusterId}">${clusterId}</option>
					</c:forEach>
				</select></span>
			</div>
			
			<div id="uploadClusterErrorDetailsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
			
	   		<div id="uploadClusterDocumentErrors" class="jqGridTableSection">
				<table id="uploadClusterDocumentErrorsTable"></table>
				<table id="uploadClusterDocumentErrorsPager"></table>
			</div>
						
		</div>
		
	</div>



	<%@ include file="../common/commonScriptsIncludes.html"%>
    <script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
    <script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>	
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/uploads/fileUpload.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/html2canvas.js"></script>
	<script src="${staticPath}/js/common/jspdf.min.js"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>		
</body>