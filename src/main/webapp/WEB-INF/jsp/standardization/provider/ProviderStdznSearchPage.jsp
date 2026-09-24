<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Provider Search Page</title>

<%@ include file="../../common/common_includes.jsp"%>
<%@ include file="../../common/commonStylesIncludes.html"%>
<%@ include file="../../common/MainMenuIncludes.jsp"%>

<link
	href="${staticPath}/css/standardization/provider/providerSearchPage.css?v=${buildDate}"
	rel="stylesheet" type="text/css" />

</head>
<body>
	<div id="headerContent">
		<%@ include file="../../common/Menu.jsp"%>
	</div>
	<div id="containerBody">
		<div id="providerSearch">

			<ul>
				<li><a href="#searchPageTab">Provider Search</a></li>
			</ul>

			<div id="searchPageTab">
				<div id="providerSearchFormElements">
					<helpContext class="referenceTarget"
						context="providerSearchFormManual"
						ref="help/standardization/provider/searchAndManage/providerSearchForm" />
					<div id="accordion" style="width: 950px; padding-left:22px">
						<h3>Search Criteria</h3>
						<div style="height: 210px;">
							<span class="searchFacilityTxt"> Search Provider</span> <span>
								<select id="searchOptions" class="searchOptions"
								onchange="changeSearchCrit(this.value)">
									<!-- <option value="0">Select</option> -->
									<option value="1">Provider</option>
									<option value="2">Provider Address</option>
									<option value="3">Raw Provider Address</option>
							</select>
							</span>
							<div style="margin-top: 10px;">
								<jsp:include page="search/SubProvSearchCriteria.jsp"></jsp:include>
							</div>
							<div
								style="text-align: center; margin-bottom: 12px; clear: both;">
								<br /> <a class="a_demo_search"
									style="color: #FFFFFF; margin-left: 5px;"
									onclick="dispSearchResults();"> Search </a>
									
									<a class="a_demo_search a_demo_reset"
									style="color: #FFFFFF; margin-left: 5px;"
									onclick="resetSearch()();"> Reset </a>
							</div>
								<div>
									<label class="redNote">Note:</label><label>Use '*' for wild card search criteria's</label>
								</div>
							
						</div>
					</div>
				</div>
				<div style="clear: both;">
					<br />
				</div>

				<div id="srchResultsDiv" style="padding-left:22px">
					<helpContext class="referenceTarget"
						context="providerSearchResultsDataGridManual"
						ref="help/standardization/provider/searchAndManage/providerSearchResultsDataGrid" />
					<div id="workTypeRadioDiv">
						<c:if  test="${userPermissions['39'] != null}" >
							Choose Work Area Type : &nbsp; <input type="radio"
								name="selWorkTypeRadio" value="SubProv"
								onclick="setFacWorkType(this.value)" id="subProvOption"><font
								id="subProvText">Provider</font> <input type="radio"
								name="selWorkTypeRadio" value="SubAddrs"
								onclick="setFacWorkType(this.value)" id="subAddrsOption"><font
								id="subAddrsText">Provider Address</font> <input type="radio"
								name="selWorkTypeRadio" value="RawProv"
								onclick="setFacWorkType(this.value)" id="rawProvOption"><font
								id="rawProvText">Raw Provider Address</font>
						</c:if>
					</div>

					<table id="searchResultsTable"></table>
					<div id="searchResultsPagerDiv"></div>
				</div>
				<br />

				<c:if  test="${userPermissions['39'] != null}" >
					<!-- Work Area -->
					<div id="workArea">
						<div style="margin-bottom: 20px;"></div>
						<!-- Buttons  -->
						<div style="text-align: center; margin-bottom: 30px;"
							id="actionButtons">
							<a onclick="confirmForUnlink();"
								class="actionButtons a_continue enabledArrowButton"
								style="color: #FFFFFF;" id="unLink">Un-Link</a> <a
								onclick="checkForMerge();"
								class="actionButtons a_continue enabledArrowButton"
								style="color: #FFFFFF;" id="merge">Merge</a>
						</div>
					</div>
				</c:if>

				<!-- Confirm Dialog -->
				<div id="confirmDialog">
					<div id="confirmSubProviderDiv">
						<table id="confirmSubProviderTable"></table>
						<div id="confirmSubProviderPager"></div>


						<div class="alignCenter marginBottom30 marginTop20">
							<a  id="mergingProviderButton" class="a_disable fadedSubmitButton mergingButton"
								href="javascript:confirmMerge();">OK</a> 
								<a id="mergingProviderCancelButton" class="actionButtons a_demo_search a_demo_cancel" style="color:#FFFFFF" href="#"
								onclick="closeStdDialog(this)">Cancel</a>
						</div>
					</div>
					<div id="confirmSubProviderAddressDiv">
						<table id="confirmSubProviderAddressTable"></table>
						<div id="confirmSubProviderAddressPager"></div>

						<div class="alignCenter marginBottom30 marginTop20">
							<a id="mergingProviderAddressButton" class="a_disable fadedSubmitButton mergingButton"
								href="javascript:loadConfirmGrand();">OK</a> 
								<a id="mergingProviderAddressCancelButton" class="actionButtons a_demo_search a_demo_cancel" style="color:#FFFFFF" href="#"
								onclick="closeStdDialog(this)">Cancel</a>
						</div>
					</div>
					<div id="confirmRawProviderAddressDiv">
						<table id="confirmRawProviderAddressTable"></table>
						<div id="confirmRawProviderAddressPager"></div>
					</div>
				</div>

			</div>


		</div>
	</div>
	<div id="multiValueStringDialog">
		<table id="multiValueStringTable"></table>
	</div>
	
	<div id="confirmMessageDialog">
		<h4 id="confirmMessageContent"></h4>
	</div>
	<%@ include file="../../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
	<script
		src="${staticPath}/js/standardization/provider/providerStdznSearchPage.js?v=${buildDate}"></script>
</body>
</html>