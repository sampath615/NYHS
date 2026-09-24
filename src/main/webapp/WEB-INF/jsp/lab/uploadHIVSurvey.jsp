<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload HIV Survey</title>
<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<%@ include file="../common/MainMenuIncludes.jsp"%>

<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />

</head>

<body class="assignmentReview">
	<%@ include file="../common/Menu.jsp"%>
	<div id="fileUploadsPage" class="pageView">
		<ul id="fileUploadTab">
			<li><a href="#uploadHIVSurveyContent" id="uploadHIVSurveyTab">Upload HIV Survey</a></li>
		</ul>
		
		<div id="uploadHIVSurveyContent">
			<c:choose>
				<c:when test="${TRANSACTION_RESULT == 'TRANSACTION_SUCCESS'}">
					<div id="fileUploadTransactionMessage" class="transactionMessage successTransactionMessage">
						<span class="message"><c:out value="${TRANSACTION_MESSAGE}" /></span>
					</div>
				</c:when>
				<c:when test="${TRANSACTION_RESULT == 'TRANSACTION_FAILURE'}">
					<div id="fileUploadTransactionMessage" class="transactionMessage errorTransactionMessage">
						<span class="message"><c:out value="${TRANSACTION_MESSAGE}" /></span>
					</div>
				</c:when>
			</c:choose>

			<div id="assignmentsReviewSearchCriteria" class="pT20 pB20">
			
				<div style="text-align:right">
					<a class="pdfDownload bold" href="${staticPath}/docs/ClepAuditSurveyDataUpload.csv">Template</a>
				</div>
				
				<form:form modelAttribute="fileBean" action="uploadHIVSurveysFile" method="post" enctype="multipart/form-data">
					<div class="alignCenter">
						
						<span class="bold "> Upload File :</span>
						<span class="mL10"> <input type="file"	id="uploadHIVSurveyFile" name="fileData" ></span>
						
						<span> <input
							type="submit" class="inlineEditButton" value="Upload File" onclick="return validateHIVSurveyFile();"/>
						</span>
						
						<div class="v_error uploadHIVSurveyFile"></div>
						
						<div><font>***The filename must be less than 255 characters, only contain alphanumeric characters (Aa-Zz and 0-9) and a maximum of one period(.)</font></div>		
											
					</div>
				</form:form>

			</div>
			
			<c:if test="${resultMap != null}">

				<div class="marginAuto width75Perc">
				
						<div class="pB20">
							<span class="floatLeft bold hdr1Font">Error Messages</span>
						</div>
>							
						<table class=" bordered" border="1" cellspacing="0" id="uploadHIVSurveyDataErrors">
						
							<tr><th class="width15Perc">PFI</th><th>Error Message</th></tr>
							
							<c:forEach items="${resultMap}" var="rowError">
								<tr>
									<td class="width15Perc">${rowError.key}</td>
									
									<td>${rowError.value}</td>
								</tr>
							
							</c:forEach>
						
						</table>
						
				</div>
			
			</c:if>
			
		</div>
	</div>



	
	<%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/lab/CLEPLabResults.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/html2canvas.js"></script>
	<script src="${staticPath}/js/common/jspdf.min.js"></script>
</body>