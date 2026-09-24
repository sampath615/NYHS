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
	<div id="provFacSearchPage" style="display:none">
      	<ul id="searchTabs">
			<li><a href="#viewSearchTab"  id="searchTab">Search</a></li>
		</ul>
	   <div id="viewSearchTab">
	   		<div id="searchTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   		
	   		<div style="height: 100px" id="providerCriteria">
	   			<div style="width:20%; float: left;">
		   			<span style="margin:5px">Select Role</span>	
		   			<select class="required standardInputMedText" name="providerCategory" id="providerCategory" tabindex="1" title="Provider Criteria">
						<option value="">Select</option>								
						<option value="998">Provider OOS</option>
						<option value="999">Provider Unknown</option>
					</select>
				</div>
				<div class="button-row alignCenter mL20" id="searchButton" style="width:20%; float: left">
					<a tabindex="3" id="searchAssignmentFormResultsButton" onclick="searchProvFacResultsTable();" style="color: #FFFFFF; margin-left: 5px;" class="a_demo_search"> Search </a>
				</div>
	   		</div>   
			
			<div id="searchProvFacResults" style="margin-bottom:18px;">
				<table id="searchProvFacResultsTable"></table>
			</div>
	   </div>
   </div>
   
    <%@ include file="../common/commonScriptsIncludes.html"%>
    <script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/reports/labCommonUtil.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/lab/laftDetails.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body> 
