<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload LAFT Survey Response</title>
<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<%@ include file="../common/MainMenuIncludes.jsp"%>

<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />

</head>

<body class="laftSurveyResponse">
	<%@ include file="../common/Menu.jsp"%>
	
		<div id="mainContentDiv"">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="addPfiReconcileTab"><a href="#addPfiReconcile">Add PFI</a></li>			
					</ul>
 					
					<div id="addPfiReconcile" style="width:1700px;height: 750px;">
						<div id="LAFTReconcileTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div class="container" style="width:1400px; float:left;">
							
							<div class="alignCenter">
								<label class="standardInputTitle">Collection Period</label>
								<select id="collectionPeriod" name="collectionPeriod"  class="mL10 standardInputMedText required" name="Collection Period">
									<c:forEach items="${collPeriodList}" var="collectionPeriod">
										<option value="${collectionPeriod.key}" >${collectionPeriod.value}</option>
									</c:forEach>
								</select>
							</div><br>	
							
							<div class="alignCenter">
								<label class="standardInputTitle">PFI</label>
								<input id="userEnteredPfi" class="mL35 formSmallInputText required" type="text" maxLength="6" title="PFI"/>
								<input id="searchPfi" name="searchPfi"  class="mL25 btn btn-primary" value="Search PFI" type="button" onclick="getPFILabData();" >
								<input id="clearPfiData" name="clearPfiData"  class="mL25 btn btn-primary" value="Clear" type="button" onclick="clearPFISearchData();">
								<div class="v_error userEnteredPfi mL20"></div>
							</div><br><br>
													
							<div id="pfiLabDetails" class="alignCenter displayNone">
								
									<label class="standardInputTitle"><spring:message code="label.CLIA" /></label>
									<input id="pfiLabClia" class="mL25 standardInputSmallText readonly" type="text" maxLength="50"/>
									
								<label class="standardInputTitle"><spring:message code="label.labName" /></label>
								<input id="pfiLabName" style="margin-left:25px;width:500px;" class="standardInputText readonly" type="text" maxLength="100"/><br><br>
	
								<label class="standardInputTitle">LAFT Staff Assigned</label>
								<input id="pfiLabLaftStaff" class="mL25 standardInputSmallText1 readonly" value="" type="text" maxLength="50"/>
									
								<label class="standardInputTitle">Approval Status</label>
								<input id="pfiLabApprovalStatus" class="mL25 standardInputText readonly" value="" type="text"/><br><br>								
	
							
								<label class="standardInputTitle"><spring:message code="label.address" /></label>
								<input id="pfiLabAddress"  class="mL15 standardInputSmallText readonly" type="text" maxLength="200"/>
						
								<label class="standardInputTitle"><spring:message code="label.city" /></label>
								<input id="pfiLabCity" class="mL25 standardInputSmallText readonly" type="text" maxLength="80"/>
							
								<label class="standardInputTitle"><spring:message code="label.state" /></label>
								<input id="pfiLabState"  class="mL25 standardInputSmallText1  readOnly" type="text" maxLength="5"/>
							
								<label class="standardInputTitle"><spring:message code="label.zip" /></label>
								<input id="pfiLabZip" class="mL25 standardInputSmallText1  readOnly" type="text" maxLength="10"/>
							
								<label class="standardInputTitle"><spring:message code="label.country" /></label>
								<input id="pfiLabCountry"  class="mL25 standardInputSmallText1  readOnly" type="text" maxLength="40"/><br><br>
	
								 <div class="button-row">
							 				<input type="button" id="sButton" class="button rounded effect-3 blue"  style="background-color: #62BBE8;" value="Add PFI" onclick="addReconcilePFi()"/>	
								</div>		

						</div>	
													
				
						</div>
						
					</div>

				</div>		
		</div> 	

   <div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
   </div>		
   	
	<%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/lab/LAFTCountReconcile.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/html2canvas.js"></script>
	<script src="${staticPath}/js/common/jspdf.min.js"></script>
</body>