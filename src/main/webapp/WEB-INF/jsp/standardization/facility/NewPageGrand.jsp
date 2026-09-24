<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	
<%@ include file="../../common/MainMenuIncludes.jsp" %>

<link href="${staticPath}/css/standardization/facility/newpage.css" rel="stylesheet" type="text/css" />

<script src="${staticPath}/js/standardization/facility/newPageGrand.js"></script>
<script>setJsVariables("${contextPath}");</script>

</head>

<body>
   <%@ include file="../../common/Menu.jsp"%>
	<div id="grandContent" style="margin-left: 150px; width: 1024px; clear: both;">
		<ul>
			<li>
				<a href="#grandPageTab">Master Page</a>
			</li>
		</ul>
		<div id="grandPageTab"  style="max-height: 600px;overflow: scroll; clear: both;">
			<div id="stdRecords" style="margin-left: 20px; margin-top: 5px; ">
				<table id="newPageStdOrphanTable"></table>
				<div id="newPageStdOrphanPagerDiv"></div>
			</div>			
			<div style="clear: both; "></div><br/>

			<div id="accordion"
				style="width: 950px; margin-left: 20px; clear: both;">
				<h3>Search For Master Facility</h3>
				<div style="height: 150px;">
					<div id="errorDiv" class="errorblock" style="display: none;">
						All search fields are blank.</div>
					<div>
						<%@ include file="search/GrandSearchCriteria.jsp"%>
					</div>
				</div>
			</div>
			
			<div>&nbsp;&nbsp;</div>
			<div id="searchResults" style="margin-left: 20px;">
				<table id="newPageGrandTable"></table>
				<div id="newPageGrandPagerDiv"></div>
				<br/>
				<div class="button-row" style="margin-bottom: 15px;">
					<a class="a_demo_search" style="color: #FFFFFF; margin-left: 5px;" 	onclick="standardize();"> Standardize </a> 
					<a class="a_demo_search" style="color: #FFFFFF; margin-left: 5px;" onclick="newGrdFacility();"> New Master Facility </a>
				</div>
			</div>
			
			<div id="newPageGrdDialog" title="Add New Master facility And Standardize">
				<jsp:include page="add/NewPageGrandDialog.jsp"></jsp:include>
			</div>
			<div id="newPageGrandStdDialog">
				<jsp:include page="confirm/NewPageGrdStandardizeDialog.jsp"></jsp:include>
			</div>
			<form id="newPageGrandReload" action="" method="post">
			</form>
		</div>
		<input type="hidden" id="newPageType" value="${param.id}">
	</div>
</body>
</html>