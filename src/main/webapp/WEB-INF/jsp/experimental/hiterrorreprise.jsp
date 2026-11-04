<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HIT Errors Reprise</title>
<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<%@ include file="../common/MainMenuIncludes.jsp"%>
<link href="${staticPath}/css/common.css" rel="stylesheet"
	type="text/css" />
<link href="${staticPath}/css/lab/laftDetails.css?v=${buildDate}"
	rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/person/person.css?v=${buildDate}"
	rel="stylesheet" type="text/css" />
</head>

<body class="hiterrorreprise">
	<%@ include file="../common/Menu.jsp"%>
	<div id="labErrorsPage" >
		<ul id="hiterrorTabs">
			<li><a href="#viewHitErrorErrorsTab" id="documentErrorsTab">HIT	Errors</a></li>
		</ul>

		<div id="viewHitErrorErrorsTab">

			<div class="alignCenter pB10">
				<span class="bold">HIE Document Type :</span> <span class="mL10"><select name="documentType" id="ddHitDocument"
					onChange="loadHitDocumentErrorsData(this)">
						<option value="ALL">ALL</option>
						<option value="CCD">CCD</option>
						<option value="HL7">HL7</option>
				</select></span>
			</div>
			
			<div id="HitErrorRepriseDetailsTransactionMessage"
				class="transactionMessage">
				<span class="message"></span>
			</div>

			<div id="hiterrorreprise" class="jqGridTableSection">
				<table id="hiterrorrepriseTable"></table>
				<table id="hiterrorreprisePager"></table>
			</div>

			<div id="manageDocuments" class="button-row displayNone pT20"
				style="text-align: center">
				<a id="repriseDocuments" class="a_disable fadedResetButton"
					tabindex="9" style="color: #FFFFFF;"
					onclick="repriseDocuments(this);"> REPRISE ON</a> <a
					id="deleteDocuments" class="a_disable fadedCancelButton"
					style="color: red; margin-left: 5px" tabindex="10"
					onclick="deleteDocuments(this);"> REPRISE OFF</a>
			</div>
		</div>

	</div>
	
	<div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>		
   </div>

	<%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
	<script	src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
	<script	src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/reports/labCommonUtil.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/utils/hiterrorreprise.js?v=${buildDate}"></script>
	 <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body>