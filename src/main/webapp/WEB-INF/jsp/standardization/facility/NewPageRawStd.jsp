<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Facility Standardization</title>
	<%@ include file="../../common/common_includes.jsp" %> 
	<%@ include file="../../common/commonStylesIncludes.html" %> 
	<%@ include file="../../common/MainMenuIncludes.jsp" %> 
		

	<link href="${staticPath}/css/jquerycombobox.css" rel="stylesheet" type="text/css" />	
	<link href="${staticPath}/css/standardization/facility/newpage.css?v=${buildDate}" rel="stylesheet" type="text/css" />


</head>
<body>
	<%@ include file="../../common/Menu.jsp"%>
	<div id="newPageRawStd" class="unStandardizeFacility">
		<ul id="unStandardizationTabs">
			<li>
				<a id="rawFacilityStandardizationTab"  href="#newPageTab" searchFor="rawFacility">Raw Facility Standardization</a>
			</li>
			<li>
				<a id="orphanFacilityStandardizationTab" href="#newPageTab" searchFor="orphanFacility">Sub Facility without Master</a>
			</li>
		</ul>
		<div id="newPageTab">
			
			<div id="newPage" style="margin-left: 20px; margin-top: 5px;">
				<div id="rawFacilityDiv">
					<helpContext class="referenceTarget" context="rawFacilityDataGridManual" ref="help/standardization/facility/unStandardized/rawFacilityDataGrid" />
					<table id="newPageTable"></table>
					<div id="newPagePagerDiv"></div>
				</div>
				<div id="orphanSubFacilityDiv">
					<helpContext class="referenceTarget" context="orphanFacilityDataGridManual" ref="help/standardization/facility/unStandardized/orphanFacilityDataGrid" />
					<table id="orphanSubFacilityTable"></table>
					<div id="orphanSubFacilityPagerDiv"></div>
				</div>
			</div>
			<div id="facilitySearchFormElements">
				<helpContext class="referenceTarget" context="facilitySearchFormManual" ref="help/standardization/facility/unStandardized/facilitySearchForm" />
				<div id="accordion" style="width: 950px; margin-left: 20px; clear: both;" >
					<h3><span id="searchForFacilityAccordian">Search For Master Facility</span><span id="searchForSubFacilityAccordian">Search For Sub Facility</span></h3>
					<div style="height: 285px;">
						<div>
							<%@ include file="search/StdznSearchCriteria.jsp"%>
						</div>
					</div>
				</div>
			</div>
			<div style="clear: both;">
				<br />
			</div>
			
			<div id="searchResultsDiv" style="margin-left: 20px;" >
				<helpContext class="referenceTarget" context="facilitySearchResultsDataGridManual" ref="help/standardization/facility/unStandardized/facilitySearchResultsDataGrid" />
				<table id="searchResultsTable"></table>
				<div id="searchResultsPagerDiv"></div>
				<br />
				<c:if  test="${userPermissions['42'] != null}" >
					<div id="actionButtons" class="button-row" style="margin-bottom: 15px;">
					    <a id="standardize" class="actionButtons a_disable fadedStandardFacilityButton" style ="color:#FFFFFF; margin-left: 5px;" onclick="standardize();" title="Standardize"> Standardize </a>
						<div id="rawFacilityStandardizationButtons">
							<a id="newStdFacility" class="actionButtons a_disable fadedAddButton" style ="color:#FFFFFF; margin-left: 5px;" onclick="newStdFacility();" title="Add and Standardize"> New Sub Facility </a>
							<a id="newMasterAndStdFacility" class="actionButtons a_demo_search a_demo_add" style ="color:#FFFFFF; margin-left: 5px;" onclick="newMasterAndStdFacility();" title="Add and Standardize"> New Master & Sub Facility </a>
						</div>
						<div id="orphanFacilityStandardizationButtons">
							<a id="newMasterFacility" class="actionButtons a_disable fadedAddButton" style ="color:#FFFFFF; margin-left: 5px;" onclick="newMasterFacility();" title="Add and Standardize"> New Master Facility </a>
						</div>
					</div>
				</c:if>
			</div>
			
			<div id="newPageStdDialog" title="Add New Sub facility And Standardize">
				<p><font class="required" title="Required Field">*</font> Indicates Required Field.</p>
				<jsp:include page="add/NewPageStdDialog.jsp"></jsp:include>
			</div>
			<div id="newPageStandardizeDialog">
				<jsp:include page="confirm/StandardizeDialog.jsp"></jsp:include>
			</div>
			
			<div id="newPageGrdDialog" title="Add New Master facility And Standardize">
				<jsp:include page="add/NewPageGrandDialog.jsp"></jsp:include>
			</div>
			
			
			<form id="newPageReload" action="" method="post">
			</form>
			<input type="hidden" id="newPageType" value="${param.id}">
		</div>
	</div>
	<div id="rawFaciltiesDialog">
		<div id="rawFacilitiesDiv">
			<table id="rawFacilityResultsTable"></table>
			<div id="rawFacilitiesDetailsPager"></div>
		</div>
	</div>
	<div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
	</div>
	<div id="multiValueStringDialog">
		<table id="multiValueStringTable"></table>
	</div>
	
	<%@ include file="../../common/addressComboDialog.jsp" %>
	
	<%@ include file="../../common/commonScriptsIncludes.html" %> 
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
	<script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
	<script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/standardization/facility/newPageRawStd.js?v=${buildDate}"></script>
</body>
</html>