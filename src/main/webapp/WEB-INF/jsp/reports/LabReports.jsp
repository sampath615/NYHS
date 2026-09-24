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
	<link rel="stylesheet" href="${staticPath}/css/reports/toggles-light.css" type="text/css"/>
</head>

<body>
<%@ include file="../common/Menu.jsp"%>
	<div id="labPage">
      	<ul id="labMaintenanceTabs">
			<li><a href="#labMaintenanceTab"  onclick="reloadLabGrid()" id="searchManage">Search Lab</a></li>
			<li><a href="#manageLabDetailsTab" id="manageLabDetails">Manage Lab Details</a></li>

		</ul>
	   <div id="labMaintenanceTab">
	   		<div id="addLabFacilityDetailsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   		<div id="searchLabForm">
				<%@ include file="search/SearchlabForm.jsp"%>
			</div>
			
			<div id="labFacilitiesResult" class="jqGridTableSection">
				<table id="clinicalaboratoriesTable"></table>
				<table id="clinicalaboratoriesPager"></table>
			</div>
			
			<div id="manageButtons" class="button-row">
				<a class="actionButtons a_disable fadedManageButton"
					style="color: #FFFFFF;" onclick="editLabFacility(this);" id="manageLabFacility"> MANAGE LAB FACILITY </a>
				<a href="#" class="actionButtons a_demo_search a_demo_add" style="color: #FFFFFF;margin-left: 5px;" onclick="addAndEditCliaFacilities('add')">ADD LAB FACILITY</a>
			</div>
	  </div>
	  
	  <div id="manageLabDetailsTab">
	   		<%@ include file="ManageLabDetails.jsp"%>
	  </div>
	  
	 

   </div>
   	<%@ include file="../common/addressComboDialog.jsp" %>
   		
    <%@ include file="addNewContact.jsp"%>
    <%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
	<script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.toggles.js"></script>
	<script src="${staticPath}/js/reports/labCommonUtil.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/reports/labReports.js?v=${buildDate}"></script>
		
	<!-- 
	<script src="http://wenzhixin.net.cn/p/bootstrap-table/docs/assets/bootstrap/js/bootstrap.min.js"></script>
	<script src="http://wenzhixin.net.cn/p/bootstrap-table/src/bootstrap-table.js"></script>
	 -->
	
</body> 
