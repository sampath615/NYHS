<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload PS Return Message</title>
<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<%@ include file="../common/MainMenuIncludes.jsp"%>

<link href="${staticPath}/css/common.css" rel="stylesheet"
	type="text/css" />


</head>

<body class="assignmentReview">
	<%@ include file="../common/Menu.jsp"%>
	<div id="fileUploadsPage" class="pageView">
		<ul id="fileUploadTab">
			<li><a href="#uploadUpdatedDispositionsContent"
				id="uploadUpdatedDispositionsTab">Upload Updated HIV Data</a></li>

		</ul>
		<div id="uploadUpdatedDispositionsContent">

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
			
			
				<form:form modelAttribute="fileBean"
					action="updatedHIVDataWithNYCData" method="post"
					enctype="multipart/form-data">
					<div class="alignCenter">
						
						<span class="bold "> Upload File :</span>
						<span class="mL10"> <input type="file"	id="updatedDispositionFile" name="fileData" ></span>
						
						<span> <input
							type="submit" class="inlineEditButton" value="Upload File" onclick="return validateUploadFile();"/>

						</span>
						
						
						<div class="v_error updatedDispositionFile "></div>
						
						<div><font>***The filename must be less than 255 characters, only contain alphanumeric characters (Aa-Zz and 0-9) and a maximum of one period(.)</font></div>		
											
					</div>
				</form:form>

			</div>
			
			
			
			
			
			<c:if test="${resultMap != null}">


				<div class="marginAuto width75Perc">
				
						<div class="pB20">
							<span class="floatLeft bold hdr1Font">Failed Messages</span>
						</div>
						
						
						<div>
							<span class="floatRight bold "><a href="updatedDispositionsResults.pdf" >Download</a></span>
						</div>
							
							
						<table class=" bordered" border="1" cellspacing="0" id="uploadDispositionResults">
						
							<tr><th class="width25Perc">Document Id</th><th class="width75Perc">Message</th></tr>
							
							<c:forEach items="${resultMap}" var="docIdMessage">
								<tr>
									<td class="width25Perc">${docIdMessage.key}</td>
									
									<td class="width75Perc" >${docIdMessage.value}</td>
								</tr>
							
							</c:forEach>
						
						</table>
						
				</div>
				
			
			
			</c:if>
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		</div>
	</div>





	
	<%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/uploads/fileUpload.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/html2canvas.js"></script>
	<script src="${staticPath}/js/common/jspdf.min.js"></script>
</body>