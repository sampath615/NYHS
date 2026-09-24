<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<title>${DOCUMENT_TYPE} Clerical Review</title>
<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<%@ include file="../common/MainMenuIncludes.jsp"%>
<link href="${staticPath}/css/common.css" rel="stylesheet"
	type="text/css" />
<link href="${staticPath}/css/person/clinicalReview.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/person/mergePerson.css?v=${buildDate}" rel="stylesheet" type="text/css" />	
<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/person/unMergePerson.css?v=${buildDate}"
	rel="stylesheet" type="text/css" />
</head>

<body class="qualityStageMatchReview">
	<div id="headerContent">
		<%@ include file="../common/Menu.jsp"%>
	</div>

	<div id="clericalReview">

		
		
		
		<div id="personMergerGrid">
			
			
			
			<ul id="mergePersonTabLinks">
				<li><a  href="#clericalReviewTab" id="personViewTabLink">Cluster Review</a>  </li>				
			</ul>
			<div id="clericalReviewTab" style="clear: both;">
			
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
			
				<form action="updateClusterFile" id="updateClusterResolveDate" method="post">
			
				<div id="documentFileCategorySelection" title="Filter by Document type" >
						<div  style="margin:10px auto">
								
								<div class="floatLeft" id="fileNameSection">
									<label class="bold">Cluster Ids:</label>
									<select name="clusterPartnerId" id="clusterPartnerId" class="required standardInputSmallText " title="Cluster Id" onChange="updateClusterFileDate(this)" >
										<option value="">Select</option>
										<c:forEach items="${clusterPartnerIdList}" var="clusterPartnerId">
											<option value="${clusterPartnerId}">${clusterPartnerId}</option>
										</c:forEach>
										
										
									</select>
								</div>
								
								<div id="resolveClusterFileSection" class="displayNone">
								
									<div class="floatLeft">
										<label class="bold">Resolve date:</label>
										<input type="text" name="clusterFileResolveDate"  id="clusterFileResolveDate" datevalidations="partialDate" class="standardInputMedText dPicker mask datepicker maskDate" maxlength="10" title="Cluster file resolve date" />
				
									</div>
									
									
									<div class="mL50 floatLeft">
										<input type="submit" onclick="return resolveClusterFile()" 		title="Resolve Clust File" value="Resolve Clust File" 		id="resolveClusterFileBtn">
									</div>
								
								</div>
						</div>
					</div>
					</form>
			
			
			
				<div class="clearBoth" ></div>
				
				
            </div>
            
               </div>
	</div>

	<%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/scan/pdf.js"></script>
	<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/search/search.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
	
	<script src="${staticPath}/js/person/ClusterPrtrStatusReview.js?v=${buildDate}"></script>
</body>
</html>