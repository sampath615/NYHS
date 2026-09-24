<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<link href="${staticPath}/css/mainmenu/search.css?v=${buildDate}"   rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
<head>
<title>Load Matching Data</title>
</head>
<body>
	<div class="floatRight">
			<span class="mR20 bold"><a href="${contextPath}/web/getAssignmentsTally">Application Home</a></span>
		</div>	
		<div class="clearBoth"></div>
<%-- 	<c:if test="${TRANSACTION_MESSAGE != null}"> --%>

		<div id="schedulerTransactionMessage" class="transactionMessage successTransactionMessage"><span class="message"><c:out value="${TRANSACTION_MESSAGE}" /></span></div>
		
      
      <div id="assignmentsReviewSearchCriteria" class="pT20 pB20">
      
      <form:form modelAttribute="fileBean"
					action="readFileColumnNames" method="post"
					enctype="multipart/form-data">
					<div class="alignCenter">
						
						<span class="bold "> Upload File :</span>
						<span class="mL10"> <input type="file"	id="updatedDispositionFile" name="fileData" ></span>
						
						<span> <input
							type="submit" class="inlineEditButton" value="Upload File" onclick="return uploadRecordSearchFile();"/>

						</span>
						
						
						<div class="v_error updatedDispositionFile "></div>
						
						<div><font>***The filename must be less than 255 characters, only contain alphanumeric characters (Aa-Zz and 0-9) and a maximum of one period(.)</font></div>		
											
					</div>
		</form:form>
		
		
		
<!-- 		MATCH_COLUMNS_LIST -->
		
		<c:if test="${columnList != null}">
			
			
				<div style=" margin:0px auto; width: 35%">
	            <div class="">
	                <span class=" ml10">Column Mapping: </span>
<!-- 	                <span class="ml10 showMultiData" id="nycCITYNO_docViewer"></span> -->
	                
	                 <form:form modelAttribute="fileBean"
					action="uploadDatafluxMatchingData" method="post"
					enctype="multipart/form-data">
						<div
							class=' nycCITYNO_docViewer'>
							<table id="dataFluxMatchInputs"
								class='statenoHistory ' style="width:100%; border:1px solid black" >
								
								<tr>
										<td class="historyTableHeader" style="border:1px solid black">Upload File</td>
										<td class="historyTableHeader" style="border:1px solid black"><input type="file"	id="updatedDispositionFile" name="fileData" ></td>
								</tr>
								
								<c:forEach var="item" items="${columnList}">
									<tr>
										<td class="historyTableHeader" style="border:1px solid black"><c:out value="${item}" /></td>
										<td  style="border:1px solid black">
											<select  name="${item}"  >
												<option value=""></option>
												<c:forEach var="matchColumn" items="${MATCH_COLUMNS_LIST}">
													<option value="${matchColumn}">${matchColumn}</option>
												</c:forEach>
											</select>
										</td>
									</tr>
								</c:forEach>
								
								
								<tr>
									<td colspan="2" align="center">
										<input
							type="submit" class="inlineEditButton" value="Load Data" />
									</td>
								</tr>
								
							</table>
						</div>
						</form:form>
	                
	                
	            </div>
            </div>
			
		
		</c:if>
      
      
      </div>
      
      
		
		
		
<%-- 	</c:if> --%>
	
	
	
	<%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/uploads/fileUpload.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/html2canvas.js"></script>
	<script src="${staticPath}/js/common/jspdf.min.js"></script>
</body>

