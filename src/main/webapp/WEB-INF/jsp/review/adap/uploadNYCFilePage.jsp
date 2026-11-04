<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<title>Upload NYC Feedback File</title>
<%@ include file="../../common/common_includes.jsp"%>
<%@ include file="../../common/commonStylesIncludes.html"%>
<%@ include file="../../common/MainMenuIncludes.jsp"%>
<link href="${staticPath}/css/common.css" rel="stylesheet"
	type="text/css" />
	
<style>


#reviewContent{
 	border: 1px solid black;
    margin: 0 auto;
    width: 715px;
	height:100px
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
				<li><a  href="#fileReviewTab" id="fileReviewTabLink">Upload NYC Feedback File</a>  </li>
			</ul>
			<div id="fileReviewTab" style="clear: both;">
			
			
				<c:choose>
				<c:when test="${TRANSACTION_RESULT == 'TRANSACTION_SUCCESS'}">
					<div id="fileReviewTransactionMessage" class="transactionMessage successTransactionMessage">
						<span class="message"><c:out value="${TRANSACTION_MESSAGE}" /></span>
					</div>
				</c:when>
				<c:when test="${TRANSACTION_RESULT == 'TRANSACTION_FAILURE'}">
					<div id="fileReviewTransactionMessage" class="transactionMessage errorTransactionMessage">
						<span class="message"><c:out value="${TRANSACTION_MESSAGE}" /></span>
					</div>
				</c:when>
			</c:choose>
				
				<form id="uploadNYCReturnFileSection"
											action="${contextPath}/web/uploadNYCReturnFileData" method="post"
											enctype="multipart/form-data">
<!-- 				<div id="documentFileCategorySelection" title="Filter by Document type" > -->
<!-- 						<div  style="margin:10px auto"> -->
					
<!-- 								<div class="mL50 floatLeft" id="fileNameSection"> -->
<!-- 									<label class="bold">File Name:</label> -->
<!-- 									<select name="documentFileName" id="documentFileName" class="required standardInputText" title="Document file name" onchange="toggleUploadSection(this)"> -->
<!-- 										<option value="">Select</option> -->
										
<%-- 										<c:forEach items="${fileNames}" var="fileName"> --%>
<%-- 											<option value="${fileName}">${fileName}</option> --%>
<%-- 										</c:forEach> --%>
<!-- 									</select> -->
<!-- 								</div> -->

<!-- 						</div> -->
<!-- 					</div> -->
			
			
			
<!-- 				<div class="clearBoth" ></div> -->
			
				
				
			
				
				
				
				<div id="adapReviewContent"  >
							
							<div class="alignCenter pB20">
								<span class="bold hdr1Font feedBackTitle"></span>
							</div>
						
						
							<div id="reviewContent">
									
										<div style="border:1px dotted; padding : 0 30 30 30">
												
											
												<div class=" clearBoth pT10 ">
														<div class="floatLeft width45Perc">
														<span class="font10pt bold">Upload Return File</span>
														</div>
														<div class="floatLeft width45Perc" >
																<span>
																<input type="file" name="uploadedFile" id="uploadedFile" class="required" style="margin-left: 10px; background: none repeat scroll 0 0 #ffffff;border-color: #cccccc #999999 #999999 #cccccc;border-radius: 8px;border-style: solid;border-width: 1px;font-family: Arial,Helvetica,sans-serif;font-size: 10pt; margin-right: auto;padding: 1px;width: 350px; " title="Upload Return File"/>
							                                      <div class="v_error uploadedFile"></div>
																
																</span>
														</div>
																
												</div>
											
											
											</div>
											
											<div id="actionButtons" class="clearBoth mergeButtons mT20" align="center">                  
										                <span><a class="actionButtons a_demo_search a_demo_continue"  onClick="uploadNYCReturnFile()" style="color: #FFFFFF; margin-left: 5px;"> Upload Return File</a></span>  
<!-- 										                <span><a class="actionButtons a_demo_search a_demo_save"  onClick="generateNYCFileData('shipped')" style="color: #FFFFFF; margin-left: 5px;" title="Generate NYC File"> Generate NYC File</a></span> -->
											</div>
										
							</div>
							
	             </div>
	             </form>
							
	             
	             <div id="confirmMessageDialog" title="Please Confirm?" >
						<h4 id="confirmMessageContent"></h4>
						<div id="requestCommentsText" style="display:none" class="customInput">
				   			<label style="padding-left:0">Comments </label></br>
				         	<textarea id="requestComments"  cols="40" rows="4" style="border: thin solid #003366;" class="required" maxLength="3000" title="Comments"></textarea>
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