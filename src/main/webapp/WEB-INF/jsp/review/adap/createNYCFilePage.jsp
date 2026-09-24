<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<title>Create NYC File</title>
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
				<li><a  href="#fileReviewTab" id="fileReviewTabLink">Create NYC File</a>  </li>
			</ul>
			<div id="fileReviewTab" style="clear: both;">
			
				
					<div id="fileReviewTransactionMessage" class="transactionMessage successTransactionMessage">
						<span class="message"></span>
					</div>
				
			
				<div id="documentFileCategorySelection" title="Filter by Document type" >
						<div  style="margin:10px auto">
								
								
								<div class="floatLeft">
									<div class="mL50 floatLeft" id="fileNameSection">
										<label class="bold">File Name:</label>
										<select name="documentFileName" id="documentFileName" class="required standardInputText" title="Document file name" >
											<option value="">Select</option>
											
											<c:forEach items="${fileNames}" var="fileName">
												<option value="${fileName}">${fileName}</option>
											</c:forEach>
										</select>
									</div>
									
									<div class="mL50 floatLeft">
										<input type="button" onclick="generateNYCFileData('notShipped')" 		title="Generate NYC File" value="Submit" class="inlineEditButton"	id="generateNYCData">
									</div>
								</div>
								<div class="floatLeft mL20">
									<input type="button" onclick="cleanNYCFileData('notShippedAllFiles')" 		title="Sweep For NYC Data" value="Sweep For NYC Data" class="inlineEditButton"	id="sweepNYCData">
								</div>
						</div>
						
						
			
					</div>
			
									
						<div class="clearBoth mT40 mL30">
						<br />
						<br />	
							<label class="redNote">Note:</label><label><span class="bold">Sweep For NYC Data : </span>Create a file where assignment address or document address changed to NYC.</label>
						</div>
			
			
	             
	             
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