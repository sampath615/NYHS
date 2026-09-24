<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${title}</title>
<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<%@ include file="../common/MainMenuIncludes.jsp"%>

<link href="${staticPath}/css/common.css" rel="stylesheet"
	type="text/css" />


</head>

<body class="assignmentReview">
	<%@ include file="../common/Menu.jsp"%>
	<div id="nonCaseReviewPage" class="pageView">
		<ul id="nonCaseReviewPageTab">
			<li><a href="#nonCaseDisplayContent"
				id="nonCaseDisplayContentTab">${title}</a></li>

		</ul>
		<div id="nonCaseDisplayContent">

			<div class="marginAuto width85Perc">
			
						
					<table class=" bordered" border="1" cellspacing="0" id="undeletedEharsStatenosList">
					
						<tr>
							<th class="width15Perc">NYEHMS STATENO</th>
							<th class="width15Perc">NYS eHARS STAENO</th>
							<th class="width20Perc">Comments</th>
							<th class="width20Perc">Non-Case Reason</th>
							<th class="width15Perc">Flagged By</th>
							<th class="width15Perc">Flagged Date</th>
						</tr>
						
						<c:forEach items="${nonCaseDocEharsStatenos}" var="nonCaseData">
							<tr>
								<td class="width10Perc">${nonCaseData.CURRENT_STATENO}</td>
								<td class="width10Perc" >${nonCaseData.EHARS_STATENO}</td>
								<td class="width30Perc" >${nonCaseData.REQUEST_COMMENTS}</td>
								<td class="width25Perc" >${nonCaseData.REASON_DESC}</td>
								<td class="width10Perc" >${nonCaseData.FLAGGED_BY}</td>
								<td class="width10Perc" >${nonCaseData.FLAGGED_DATE}</td>
								
							</tr>
						
						</c:forEach>
					
					</table>
					
			</div>
		</div>
	</div>





	
	<%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/uploads/fileUpload.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/html2canvas.js"></script>
	<script src="${staticPath}/js/common/jspdf.min.js"></script>
</body>
		
	
	
	
