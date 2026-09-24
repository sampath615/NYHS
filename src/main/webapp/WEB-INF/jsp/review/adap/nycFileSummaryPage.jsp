<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<title>NYC File Summary</title>
<%@ include file="../../common/common_includes.jsp"%>
<%@ include file="../../common/commonStylesIncludes.html"%>
<%@ include file="../../common/MainMenuIncludes.jsp"%>
<link href="${staticPath}/css/common.css" rel="stylesheet"
	type="text/css" />
	
<style>


#reviewContent{
 	border: 1px solid black;
    margin: 0 auto;
    overflow: auto;
    width: 350px;
	max-height:530px
}


</style>
</head>

<body>
	<div id="headerContent">
		<%@ include file="../../common/Menu.jsp"%>
	</div>

	<div id="clericalReview">

		<div id="commonGridTab">
			<ul id="tabLinks">
				<li><a  href="#fileReviewTab" id="fileReviewTabLink">NYC File Summary</a>  </li>
			</ul>
			<div id="fileReviewTab" style="clear: both;">
			
			
			
				<div id="documentFileCategorySelection" title="Filter by Document type" >
						<div  style="margin:10px auto">
					
								<div class="mL50 floatLeft" id="fileNameSection">
									<label class="bold">File Name:</label>
									<select name="documentFileName" id="documentFileName" class="required standardInputText" title="Document file name"  onchange="enableSummaryButtons(this)">
										<option value="">Select</option>
										
										<c:forEach items="${fileNames}" var="fileName">
											<option value="${fileName}">${fileName}</option>
										</c:forEach>
									</select>
								</div>
								
								<div class="mL50 floatLeft displayNone" id="summaryButtons">
									<input type="button" onclick="generateNYCFileSummary()" 		title="Generate NYC File Summary" value="Summary" class="inlineEditButton"	id="generateNYCData">
									<input type="button" onclick="generateNYCFileDiscrepancyFileData()" 		title="Discrepancy File" value="Discrepancy File" class="mL50 inlineEditButton"	id="generateNYCDiscrepancyFile">
								</div>
						</div>
					</div>
			
		
            </div>
          </div>
	</div>
	
	<%@ include file="../../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.fileDownload.js"></script>
	<script src="${staticPath}/js/review/adapReview.js?v=${buildDate}"></script>
</body>
</html>