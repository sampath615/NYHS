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

<body class="cdcPendingReturnDataPage">
<%@ include file="../common/Menu.jsp"%>
	<div id="recordSearchReviewPage" class="pageView">
      	<ul id="recordSearchReviewTabs">
			<li><a href="#viewRecordSearchReviewTab"  id="recordSearchStatnosTab">CDC Return File</a></li>
		</ul>
	   <div id="viewRecordSearchReviewTab">
	   
	   		
	   		<div id="recordSearchTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   		
	   		
	   		<div class="marginAuto">
	   		
	   		<c:choose>
	   			<c:when test="${fileNames != null && fileNames != '[]' }">
	   				<div class="alignCenter">
						<label style="font-family: Arial,Helvetica,sans-serif;font-size: 10pt; font-weight: bold"> CDC Pending Return File: </label>
		               <select class="required" name="inputFileName" id="inputFileName" style="margin-left: 10px; background: none repeat scroll 0 0 #ffffff;border-color: #cccccc #999999 #999999 #cccccc;border-radius: 8px;border-style: solid;border-width: 1px;font-family: Arial,Helvetica,sans-serif;font-size: 10pt; margin-right: auto;padding: 1px;width: 350px;" title="File name" onChange="loadCDCReturnData(this)">
		                       <option value="">Select</option>
								<c:forEach items="${fileNames}" var="cdcFile">
									<option value="${cdcFile.SENT_FILE_NAME}">${cdcFile.SENT_FILE_NAME}  (${cdcFile.GENERATEDBY})  (${cdcFile.GENERATEDATE})</option>
								</c:forEach>
		               </select>	   				
	   				</div>
               </c:when>
               <c:otherwise>
               		<div class="bold alignCenter mT20 font15pt">
							There is no files pending
						</div>
               </c:otherwise>
             </c:choose>
               
	   
	   		<div id="pendingCDCReturnData" class="marginAuto clearBoth mT20">
				<table id="pendingCDCReturnDataTable" class=""></table>
				<table id="pendingCDCReturnDataPager"></table>
			</div>
			
			<div id="actionButtons" class="mT20 displayNone" style="text-align:center">
										
										<a	class="actionButtons a_demo_search a_demo_print" id="generateCDCFile"
										style="color: #FFFFFF; margin-left: 5px;"
										onclick="downloadGeneratdCDCFile();"> Download CDC File </a>
										
										
										
										<a	class="actionButtons a_demo_search a_demo_reject" id="generateCDCFile"
										style="color: #FFFFFF; margin-left: 5px;"
										onclick="rejectCDCFile();"> Reject CDC File </a>
										
										
								</div>
								
					
				</div>				
	   </div>
	   
	  
		
		
   </div>

	
	   
   <div id="confirmMessageDialog" title="Please Confirm?" >
		<h4 id="confirmMessageContent"></h4>
	  
   </div>
      
    <%@ include file="../common/commonScriptsIncludes.html"%>
        <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/review/recordSearchReview.js?v=${buildDate}"></script>

</body> 
