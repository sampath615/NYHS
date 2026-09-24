<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<title>Provider Portal File Review</title>
<%@ include file="../../common/common_includes.jsp"%>
<%@ include file="../../common/commonStylesIncludes.html"%>
<%@ include file="../../common/MainMenuIncludes.jsp"%>
<link href="${staticPath}/css/common.css" rel="stylesheet"
	type="text/css" />
</head>
<style>
#reviewContent{
 	border: 1px solid black;
    margin: 0 auto;
    overflow: auto;
/*     width: 1150px; */

}
</style>
<body>
	<div id="headerContent">
		<%@ include file="../../common/Menu.jsp"%>
	</div>

	<div id="clericalReview">

		<div id="commonGridTab">
			<ul id="tabLinks">
				<li><a  href="#fileReviewTab" id="fileReviewTabLink">File Review</a>  </li>
			</ul>
			<div id="fileReviewTab" style="clear: both; min-height:150px">
			
			
			
				<div id="documentFileCategorySelection" title="Filter by Document type" >
						<div  style="margin:10px auto">
					
								<div id="fileTypeSection" class="floatLeft" style="height:25px">
									<label class="bold">Document Type:</label>
										
									<select name="clericalDocumentFileType" id="clericalDocumentFileType" class="required standardInputSmallText" title="Document file type" onchange="loadDocumentFileNames(this)">
										<option value="">Select</option>
										<option value="ALL">ALL</option>
										<option value="O">OOC</option>
										<option value="D">DNLTC</option>
										<option value="P">SNP</option>
									</select>
								</div>
							
							
							
								<div class="mL50 floatLeft" id="fileNameSection">
									<label class="bold">File Name:</label>
									<select name="documentFileName" id="documentFileName" class="required standardInputText readonly" title="Document file name" onchange="toggleDisplayResults()">
										<option value="">Select</option>
										<option value="ALL">ALL</option>
										<c:forEach items="${fileNames}" var="fileName">
											<option value="${fileName}">${fileName}</option>
										</c:forEach>
									</select>
								</div>
								
								<div class="mL50 floatLeft">
									<input type="button" onclick="loadRawOOCDocumentsForReview()" 		title="Review File" value="Review" class="inlineEditButton"	id="loadQualitStageClericalDocumentsDetails">
								</div>
						</div>
					</div>
			
			
			
				<div class="clearBoth" ></div>
			
			
				<div id="fileReviewTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				
				<div id="providerPortalReview" class="displayNone" >
							<div id="reviewContent">
							
								<table id="documentReviewTable"></table>
								
								<div id="documentReviewTablePager"></div>
								
								
							
							</div>
							
							<div id="manageActionButtons" class="button-row displayNone mT20 mB20" style=" text-align:center"> 
								<a id="processDocuments" class="actionButtons a_demo_search a_demo_accept"
									tabindex="8" style="color: #FFFFFF;" onclick="confirmProcessOOCDocumentsFromReview(this);"> PROCESS </a>
								<a id="rejectDocuments" class="actionButtons a_demo_search a_demo_cancel"
									tabindex="9" style="color: #FFFFFF;" onclick="confirmRejectOOCDocumentsFromReview(this);"> REJECT </a> 
							</div>
							
					
	             </div>
            </div>
          </div>
	</div>
	
	<div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
		
		<div id="facilityCategorySection">
			<span class="bold">Facility Category : </span>
			<span>
				<select id="facilityCategories" name="facilityCategories" class="standardinputMedText" title="Facility Category">
					<option value="" selected="true"> -- Select --</option>
					<option value="HICAPP - Anthony Jordan">HICAPP - Anthony Jordan</option>
					<option value="HICAPP - Cornerstone">HICAPP - Cornerstone</option>
					<option value="HICAPP - Community Health Buffalo">HICAPP - Community Health Buffalo</option>
					<option value="General">General</option>
				</select>	
			</span>
		</div>
		
   	</div>
   
	<%@ include file="../../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/review/providerPortalReview.js?v=${buildDate}"></script>
</body>
</html>