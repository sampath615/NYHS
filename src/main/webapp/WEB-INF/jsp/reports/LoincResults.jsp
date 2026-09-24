<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Lab Reports</title>
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	<link href="${staticPath}/css/reports/labReports.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<!-- 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="http://wenzhixin.net.cn/p/bootstrap-table/src/bootstrap-table.css">
 -->
</head>

<body>
<%@ include file="../common/Menu.jsp"%>
	<div id="loincPage">
      	<ul id="loincMaintenanceTabs">
			<li><a href="#LabTestResultsTab" id="LabTestResults">Lab Test Results</a></li>

		</ul>
		

  <div id="LabTestResultsTab">
		<div id="labAcceptedTestResult">
	  <table id="labAcceptedTestResultTable"></table>
	  <table id="labAcceptedTestResultPager"></table>
 </div>
<div class="mT150">   </div>
<div class="button-row" style="height:50px;padding-left:450px">
     <a onclick="alert()" class="actionButtons a_continue enabledArrowButton a_demo_add" style="color: #FFFFFF;" id="add_button">Add</a>
</div>
<div class="mT50">   </div>
<div id="labLoincCodeDescription">
	 <table id="labLoincCodeDescriptionTable"></table>
	 <table id="labLoincCodeDescriptionPager"></table>
</div>

</div>
</div>
  <%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/reports/labReports.js?v=${buildDate}"></script>
	<!-- 
	<script src="http://wenzhixin.net.cn/p/bootstrap-table/docs/assets/bootstrap/js/bootstrap.min.js"></script>
	<script src="http://wenzhixin.net.cn/p/bootstrap-table/src/bootstrap-table.js"></script>
	 -->
</body> 