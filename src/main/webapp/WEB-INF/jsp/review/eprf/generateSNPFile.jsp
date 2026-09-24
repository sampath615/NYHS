<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<title>Generate SNP File</title>
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
				<li><a  href="#fileReviewTab" id="fileReviewTabLink">Generate SNP File</a>  </li>
			</ul>
			<div id="fileReviewTab" style="clear: both;">
			
			
			
				<div id="documentFileCategorySelection" title="Filter by Document type" >
						<div  style="margin:10px auto">
					
								<div class="mL50 floatLeft" id="fileNameSection">
									<label class="bold">File Name:</label>
									<select name="documentFileName" id="documentFileName" class="required standardInputText" title="Document file name" onchange="javascript:reviewContentDiv.addClass('displayNone')">
										<option value="">Select</option>
										
										<c:forEach items="${snpFiles}" var="snpFile">
											<option value="${snpFile.FILENAME}">${snpFile.FILENAME}  (${snpFile.SUBMITTEDBY})  (${snpFile.SUBMITTEDDATE})</option>
										</c:forEach>
									</select>
								</div>
								
								<div class="mL50 floatLeft">
									<input type="button" onclick="loadSNPDataForTheSelectedFile()" 		title="Generate File" value="Submit" class="inlineEditButton"	id="loadSNPFileData">
								</div>
						</div>
					</div>
			
			
			
				<div class="clearBoth" ></div>
			
			
				<div id="fileReviewTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				
				<div id="providerPortalReview" class="displayNone" >
							
							<div class="alignCenter pB20">
								<span class="bold hdr1Font feedBackTitle"></span>
							</div>
						
						
							<div id="reviewContent">
							
							
							</div>
							
	             </div>
            </div>
          </div>
	</div>
	
	<%@ include file="../../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/review/providerPortalReview.js?v=${buildDate}"></script>
</body>
</html>