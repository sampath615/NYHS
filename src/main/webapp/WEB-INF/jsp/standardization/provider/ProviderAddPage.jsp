<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<title>Provider Standardization </title>
<%@ include file="../../common/common_includes.jsp"%>
<%@ include file="../../common/commonStylesIncludes.html"%>
<%@ include file="../../common/MainMenuIncludes.jsp"%>

<link href="${staticPath}/css/jquerycombobox.css" rel="stylesheet"
	type="text/css" />

<link
	href="${staticPath}/css/standardization/provider/providerStdznHome.css?v=${buildDate}"
	rel="stylesheet" type="text/css" />
</head>

<body>
	<div id="headerContent">
		<%@ include file="../../common/Menu.jsp"%>
	</div>

	<div id="containerBody">
		<!-- <div id="floatContent" style="height: 600px; width: 1024px; margin: 0px auto; clear: both;"> -->
		<div id="providerStdz" class="gridLayout">
					<ul id="providerUnstandardizedResults">
						<li><a id="providerStandardizationLiTab"
							href="#providerStandardizationTab" searchFor="2">Provider
								Standardization</a></li>
					</ul>
			<div id="providerStandardizationTab">
				<div id="providerStandardizationElements">
					<div id="providerSearchFormElements">
						<div id="provAccordion"
							style="width: 950px; clear: both;">
							<h3><span id="searchForProviderAccordian">Search For Provider</span><span id="searchForStandardProviderAccordian">Search For Provider Address</span></h3>
							<div style="height: 250px;">
								<div id="errorDiv" class="errorblock" style="display: none;">
									All search fields are blank.</div>
								<div>
									<jsp:include page="search/SubProvSearchCriteria.jsp"></jsp:include>
								</div>
								<div
									style="text-align: center; margin-bottom: 12px; clear: both;">
									<br /> <a class="a_demo_search"
										style="color: #FFFFFF; margin-left: 5px;"
										onclick="dispSearchResults();"> Search </a>
										<a class="actionButtons a_demo_search a_demo_reset"
										style="color: #FFFFFF; margin-left: 5px;"
										onclick="resetFields();"> Reset </a>
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
					<div id="subProvSearchResults"
						style="display: none;">
						<table id="subProvAddrsTable"></table>
						<div id="subProvAddrsPager" style="color: #FFFFFF"></div>
						
						<c:if  test="${userPermissions['40'] != null}" >
							<div id="actionButtons">
								<div id="rawProvAddrStandardization">
									<a class="actionButtons a_disable fadedAddButton"
										id="newAddressBtn" style="color: #FFFFFF; margin-left: 5px;"
										onclick="addAddress();"> New Address </a> <a
										class="actionButtons a_demo_search a_demo_add" id="newProviderAddressBtn"
										style="color: #FFFFFF; margin-left: 5px;"
										onclick="addProviderAndAddress();"> New Provider & Address
									</a>
								</div>
								<div id="orphanProvAddrStandardization">
									<a class="actionButtons a_disable fadedAddButton"
										id="newProviderBtn" style="color: #FFFFFF; margin-left: 5px;"
										onclick="addProvider();"> New Provider</a>
								</div>
							</div>
						</c:if>
					</div>
				</div>

			</div>
		</div>

		<div id="addProvAddrsModal" class="userAddressDtls"
			title="Adding New Provider and Address">
			<p>
				<font class="required" title="Required Field">*</font> Indicates
				Required Field.
			</p>
			<jsp:include page="add/AddProviderAndAddress.jsp"></jsp:include>
		</div>
		<form id="provNewReload" action="" method="post"></form>
		<input type="hidden" id="newPageType" value="${param.id}">

	</div>
	<div id="confirmDialog">
		<div id="confirmRawProviderAddressDiv">
			<table id="confirmRawProviderAddressTable"></table>
			<div id="confirmRawProviderAddressPager"></div>
		</div>
	</div>
	<div id="confirmMessageDialog">
		<h4 id="confirmMessageContent"></h4>
	</div>
	
	<div id="multiValueStringDialog">
		<table id="multiValueStringTable"></table>
	</div>
	<%@ include file="../../common/addressComboDialog.jsp" %>
	
	
	<%@ include file="../../common/commonScriptsIncludes.html"%>
	
	
	
	<script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
	<script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script
		src="${staticPath}/js/standardization/provider/providerAdd.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>

</body>
</html>