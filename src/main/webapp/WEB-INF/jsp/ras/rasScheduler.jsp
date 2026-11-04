<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RAS Sierra Job</title>
<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<%@ include file="../common/MainMenuIncludes.jsp"%>

<link href="${staticPath}/css/common.css" rel="stylesheet"
	type="text/css" />


</head>

<body class="assignmentReview">
	<%@ include file="../common/Menu.jsp"%>
	<div  class="pageView " id="commonTemplate" >
		<ul id="rasJobTab">
			<li><a href="#rasJobCriteria"
				id="uploadUpdatedDispositionsTab">Run RAS Job</a></li>

		</ul>
		<div id="rasJobCriteria" >

			<div id="RASJobTransactionMessage" class="transactionMessage successTransactionMessage">
				<span class="message"><c:out value="${TRANSACTION_MESSAGE}" /></span>
			</div>
	
			<div id="rasJobCriteriaFields" class="pT20 pB20">
		
				
				
				<form method="post" id="rasJobCriteriaForm">
					<div class="alignCenter">
						
						<span class="bold "> From DocumentId :</span>
						<span class="mL10"> <input type="text"	id="minDocId" name="minDocId" class="required" title="Document_Id"></span>
						<span class="mL10"> <input
							type="button" class="inlineEditButton" value="Run" onclick="return runRasJob();"/>

						</span>
							
					</div>
				</form>

			</div>
			
			<div id="rasSchedulerResponse" class="marginAuto width75Perc">
			
			</div>
		</div>
	</div>
	
	<%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/RAS/rasJob.js?v=${buildDate}"></script>
</body>