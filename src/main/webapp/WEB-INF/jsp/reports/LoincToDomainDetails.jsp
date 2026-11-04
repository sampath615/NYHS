<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>LOINC / Result Maintenance</title>
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	<link href="${staticPath}/css/reports/labReports.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${staticPath}/css/reports/toggles-light.css" type="text/css"/>
</head>

<body>
<%@ include file="../common/Menu.jsp"%>
	<div id="loincPage">
      	<ul id="loincMaintenanceTabs">
			<li><a href="#TestResultsTab" id="TestResultsDomain">Test Results Domain</a></li>

		</ul>
		
  <div id="TestResultsTab">
  	<div id="SearchElements">
					
	 <div id="loincAccordion" style="width: 90%; margin:10px auto; clear: both;">
			 <h3><span id="searchForLoincAccordian">LOINC Search</span></h3>
						<div style="height: 250px;">
							<div id="errorDiv" class="errorblock" style="display: none;">
								All search fields are blank.</div>
							<div style="border: 1px ridge lightgrey; height:75px; padding-right:10px" >
								<form:form id="searchLoincForm" name="searchLoincForm">
							   <div class="personDetailsDiv" id="LoincInfoDiv" style="dispaly:none;">
								
									<div class="leftDiv">
										<div class="standardInputDiv">
											<div class="floatLeft">
												<label style="margin-left:10px">LOINC Code</label>
											</div>
											<div class="floatRight">
												<input id="loincCode" name="loincCode" tabindex="1" class="standardInputMedText mR50" value="" type="text"/> 
											</div>
										</div>
										
										<div class="standardInputDiv">
											<div class="floatLeft">
												<label style="margin-left:10px">Test type</label>
											</div>
											<div class="floatRight">
												<select id="testType" name="testType" tabindex="3" class="standardInputMedText mR50" >
													<option value="">------Select-------</option>
													<c:forEach items="${loincTestTypes}" var="testType">
														<option value="${testType}">${testType}</option>
													</c:forEach>
												 </select> 
											</div>
										</div>
										
										
									</div>
									<div class="rightDiv">
										<div class="standardInputDiv">
											<div class="floatLeft">
												<label >LOINC Description</label>
											</div>
											<div class="floatRight">
												<input id="loincDesc" name="loincDesc" class="standardInputMedText mR50" value="" type="text" tabindex="2"/>
											</div>
										</div>
										
										<div class="standardInputDiv">
											<div class="floatLeft">
												<label >Units of Measurement</label>
											</div>
											<div class="floatRight">
												<select id="unitOfResult" name="unitOfResult" tabindex="3" class="standardInputMedText mR50" >
													<option value="select">------Select-------</option>
													<c:forEach items="${unitsOfMeasurements}" var="unitOfResult">
														<option value="${unitOfResult}">${unitOfResult}</option>
													</c:forEach>
												 </select> 
				 
											</div>
										</div>
									
									
									</div>

									
									
								 </div>			
		
	                             </form:form>
					           </div>
				
			<div style="text-align: center; margin-bottom: 12px; clear: both;">
						<br /> <a class="a_demo_search"
							style="color: #FFFFFF; margin-left: 5px;"
							onclick="displaySearchResults();" tabindex="5"> Search </a>
							
					</div>
				</div>
	</div>
</div>

<div id="ListOfAddedDomainsTransactionMessage" class="transactionMessage" style="padding-bottom:15px"><span class="message"></span></div>
<div id="LoincCodeDescription" class="jqGridTableSection">
	 <table id="LoincCodeDescriptionTable"></table>
	 <table id="LoincCodeDescriptionPager"></table>
</div>

</div>
</div>
</div>

<div id="addLoincDetailsDialog" >
	 <table id="DomainsNotAssoicatedToLoincCodeTable"></table>
	 <table id="DomainsNotAssoicatedToLoincCodePager"></table>
     <div style="text-align: center; margin-bottom:12px; clear: both;"><br/>
			<a class="actionButtons a_demo_search a_demo_save" style="color:#FFFFFF; margin-left: 5px;" onclick="submitLoincCodes();"> Submit </a>
	 </div>
</div>		
<div id="addAtrOfloincDetailsDialog" ></div>
<div id="addLabDetailsDialog" >
	<div id="addingLabDetails">
	
	</div>
</div>
<div id="confirmMessageDialog" >
			<h4 id="confirmMessageContent"></h4>
			<div id="requestCommentsText" style="display:none">
	   			<label style="padding-left:0">Requester Comments </label></br>
          		<textarea id="requestComments"  cols="40" rows="4" style="border: thin solid #003366;" title="<spring:message code='label.requestComments' />"></textarea>
		  </div>  
</div>	

  <%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/reports/labCommonUtil.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/reports/LoincResults.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.toggles.js"></script>
	<script type="text/javascript">
		var eharsTestResults = ${sessionScope.eharsTestResults};
	</script>
</body> 