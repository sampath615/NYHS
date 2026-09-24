<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Record Search</title>
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
</head>
<body>
<%@ include file="../common/Menu.jsp"%>
	<div id="recordSearchReviewPage" class="pageView">
      	<ul id="recordSearchReviewTabs">
			<li><a href="#viewRecordSearchReviewTab"  id="recordSearchStatnosTab">CDC Upload Return File</a></li>
		</ul>
	   <div id="viewRecordSearchReviewTab">
	   		
			<c:choose>
				<c:when test="${TRANSACTION_RESULT == 'TRANSACTION_SUCCESS'}">
					<div id="recordSearchTransactionMessage" class="transactionMessage successTransactionMessage">
						<span class="message"><c:out value="${TRANSACTION_MESSAGE}" /></span>
					</div>
				</c:when>
				<c:when test="${TRANSACTION_RESULT == 'TRANSACTION_FAILURE'}">
					<div id="recordSearchTransactionMessage" class="transactionMessage errorTransactionMessage">
						<span class="message"><c:out value="${TRANSACTION_MESSAGE}" /></span>
					</div>
				</c:when>
			</c:choose>
	   
	   		<c:choose>
	   			<c:when test="${fileNames != null && fileNames != '[]' }">
	   					<form id="uploadCDCReturnFileSection"
								action="${contextPath}/web/uploadCDCFileData" method="post"
								enctype="multipart/form-data">
								
								<div style="text-align:right">
				
									<a class="pdfDownload bold" href="${staticPath}/docs/record_search_return_template.txt" download="record_search_return_template.txt">Template</a>
								</div>
								
								
			   					<div id="" style="background-color: #ebebeb; text-align:center; padding-top:25px; height: 150px; width:600px; border: solid 1px #999; margin: 0 auto; margin-top:25px;">
						                      <div style="margin-bottom:10px">
						                                      <label style="font-family: Arial,Helvetica,sans-serif;font-size: 10pt; font-weight: bold"> Return File: </label>
						                                      <select class="required" name="inputFileName" id="inputFileName" style="margin-left: 10px; background: none repeat scroll 0 0 #ffffff;border-color: #cccccc #999999 #999999 #cccccc;border-radius: 8px;border-style: solid;border-width: 1px;font-family: Arial,Helvetica,sans-serif;font-size: 10pt; margin-right: auto;padding: 1px;width: 350px;" title="File name">
						                                                      <option value="">Select</option>
																				<c:forEach items="${fileNames}" var="cdcFile">
																					<option value="${cdcFile.SENT_FILE_NAME}">${cdcFile.SENT_FILE_NAME}  (${cdcFile.GENERATEDBY})  (${cdcFile.GENERATEDATE})</option>
																				</c:forEach>
						                                      </select>
						                                      <div class="v_error inputFileName"></div>
						                      </div>
						                      <div>
						                                      <label style="font-family: Arial,Helvetica,sans-serif;font-size: 10pt; font-weight: bold"> Upload File: </label>
						                                      <input type="file" name="uploadedFile" id="uploadedFile" class="required" style="margin-left: 10px; background: none repeat scroll 0 0 #ffffff;border-color: #cccccc #999999 #999999 #cccccc;border-radius: 8px;border-style: solid;border-width: 1px;font-family: Arial,Helvetica,sans-serif;font-size: 10pt; margin-right: auto;padding: 1px;width: 350px; " title="Upload Return File"/>
						                                      <div class="v_error uploadedFile"></div>
						                      </div>
						                      <br/>
						                      	<a class="actionButtons a_demo_search a_demo_save " style="color:#FFFFFF; margin-left: 5px;" onclick="uploadCDCReturnFile();"> Save </a>
						                      
						      </div>
						</form>
	   			</c:when>
	   			<c:otherwise>
	   				<div class="bold alignCenter mT20 font15pt">
						There is no pending files to upload	
					</div>
	   			
	   			</c:otherwise>
	   		
	   		</c:choose>
	   				
	   				
								
					
								
	   </div>
	   
	  
		
		
   </div>

   
    <%@ include file="../common/commonScriptsIncludes.html"%>
    
    <script src="${staticPath}/js/review/recordSearchReview.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body> 
