<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Clerical Review</title>
	<%@ include file="../../common/common_includes.jsp" %> 
	<%@ include file="../../common/commonStylesIncludes.html" %> 
	<%@ include file="../../common/MainMenuIncludes.jsp" %> 
	<link href="${staticPath}/css/standardization/provider/providerStdznHome.css?v=${buildDate}" rel="stylesheet" type="text/css" />

</head>
<body>
	<%@ include file="../../common/Menu.jsp"%>
	<div id="newPageRawStd" class="newPageRawStdProvider">
		<ul>
			<li>
			   <a href="#newPageTab">Clerical Review</a>
			</li>
		</ul>

		<div id="newPageTab">
			
			<div id="newPage" style="margin-left: 20px; margin-top: 5px;">
				<div id="rawProviderAddressDiv">
					<table id="newPageTable"></table>
					<div id="newPagePagerDiv"></div>
				</div>
			</div>
			<div style="clear: both;">
				<br />
			</div>
			<div id="searchResultsDiv" style="margin-left: 20px;" >
				<table id="searchResultsTable"></table>
				<div id="searchResultsPagerDiv"></div>
				<br />
				
					<div id="actionButtons" class="displayNone button-row" style="margin-bottom: 15px;">
					
						
					    <a id="standardize" class="actionButtons a_disable fadedContinueButton" style ="color:#FFFFFF; margin-left: 5px;" onclick="standardize();" title="Standardize"> Match </a>
						<a id="notAMatch" class="actionButtons a_demo_search a_demo_reject" style ="color:#FFFFFF; margin-left: 5px;" onclick="rawProviderAddressNotAMatch();" title="un Standardize"> Not a match </a>
					</div>
			</div>
			<div id="newPageStandardizeDialog">
				<jsp:include page="confirm/RawToRawStandardizeDialog.jsp"></jsp:include>
			</div>
		</div>
	</div>
	<div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
	</div>
	
		
	<div id="multiValueStringDialog">
		<table id="multiValueStringTable"></table>
	</div>
	
	
	<%@ include file="../../common/commonScriptsIncludes.html" %> 
	<script src="${staticPath}/js/standardization/provider/providerManualReview.js?v=${buildDate}"></script>
</body>
</html>