<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="cache-control" content="no-cache" />
	
	<%@ include file="../../common/common_includes.jsp" %> 
    <%@ include file="../../common/commonStylesIncludes.html" %> 
    <%@ include file="../../common/MainMenuIncludes.jsp" %>
        
	<link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />	
	
	
<title>Provider Report</title>

</head>
<body class="providerReportByYear" bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">
     <%@ include file="../../common/Menu.jsp" %>
 <div id="floatContent"  style="background-color: transparent" >
    <div id="mainContentDiv">
    	<div id = "tabs">
    		<ul id="Tabs">
    			<li id="providerReportTab"><a href="#providerReportbyYearTab"> Provider Report By Year</a></li>
				<li id="listPersonViewTab" class="displayNone"><a href="#personDetailsTab" >Recent Demo View</a></li>
				<li id="listDocumentViewTab" class="displayNone"><a href="#documentDetailsTab" >Document Timeline</a></li>
    			
    		</ul>
    		
    		<div id="providerReportbyYearTab"  style="clear: both; height: 700px; width: 1700px;">
    			
    			<div id="providerReportsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				
					   <div id="provReportByYear" class="pT20 pB20">
					   		<form action="getProvReportByYear" id ="loadProvReportByYearData">
					   		
								<div id="provRptRoleType" class="inlineBlock floatLeft" style="height:25px">
									<font class="standardInputTitle"><spring:message code="label.role" /> :</font>
									<select name="roles" id="roles" class="required" title="Role" onchange="reloadRegionsList(this.value);">
										<c:if test="${fn:length(userRoleCodes) > 1}">
											<option value="0" label="Select" />
										</c:if>
										<c:forEach items="${userRoleCodes}" var="userRole">
											<option value="${userRole.key}" >${userRole.value}</option>
										</c:forEach>											
									</select>
								</div>				   				 

								<div id="provRptRgnArea" class="displayNone floatLeft">
								<div id="provRptRoleRegion" class="floatLeft" style="height:25px">
									<font class="standardInputTitle"><spring:message code="label.region" /> :</font>
									<select id="regionId" name="regionId" class="required">

									</select>
								</div>				 

 								<div class="floatLeft" style="height:25px">
								<font class="standardInputTitle">&nbsp; Case Type :</font> 
									<select name="caseType" id="caseType" class="required" title="Case Type">
										<option  value="ALL" >ALL</option>		
										<option  value="900" >900 dx</option>															
										<option  value="950" >950 dx</option>															
										<option  value="CON" >Concurrent dx</option>															
									</select>	
								</div>
																
				   				<div class="floatLeft" style="height:25px">
									<font class="standardInputTitle">&nbsp; AHI :</font> 
									<select name="ahiFlag" id="ahiFlag" class="required" title="AHI Flag">
										<option  value="ALL" >ALL</option>		
										<option  value="Y" >Yes</option>															
										<option  value="N" >No</option>	
									</select>
				   				</div>
														   									   		
				   				<div class="floatLeft" style="height:25px">
									<font class="standardInputTitle">&nbsp; PRF Submitted :</font> 
									<select name="prfSubmitted" id="prfSubmitted" class="required" title="PRF Submitted">
										<option  value="ALL" >ALL</option>		
										<option  value="PRF" >Yes</option>															
										<option  value="No PRF" >No</option>	
									</select>
				   				</div>
														   									   		
					   				<div id="dateRange" class="floatLeft" style="height:25px">
					   					<font class="standardInputTitle" id="textToDate" >Year: </font>
										<font class="standardInputTitle" id="textToDate" >From: </font>
										<input id="fromYear" class="required  date-picker" readonly/>
										<font class="standardInputTitle" id="workersFont" >To: </font>
										<input id="toYear" class="required date-picker" readonly/>
					   				</div>
					   				
					   				<div class="mL50 floatLeft">
											<input type="button" onclick="getProvReportByYear();" title="Generate Report" value="Generate Report"  id="loadProvReportByYear">	
									</div>	
								 </div>
								</form>
								<div id="provRptExportBtn" class="mL50 displayNone floatLeft">
										<button onclick="onBtExport()" style="margin-bottom: 5px; font-weight: bold;">Export</button>
								</div>	
					   </div>
					 <div class="clear" style="clear: both;"></div>
					 <div class="floatLeft mT20 mL20">
					      
					      <div id="loadProvReportByYrGrid"  style="height: 600px;width:1400px;" class="ag-theme-balham"></div>
					 </div>		
    		</div>
			<div id="personDetailsTab" style="clear: both; overflow: scroll">

			</div>
			<div id="personTableContainerModal">
				<table id="personFieldValuesTbl"></table>
			</div>
	
			<div id="documentDetailsTab" style="clear: both;">
			
				<div id="associateDocToStatenoTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				
				<form name="documentForm" class="documentDetails">
					<div id="documentDetailsDiv">
						<table id="documentDetails"></table>
						<div id="documentDetailsPagerDiv"></div>
					</div>
				</form>
			</div>		    		 
    	</div>
    </div>

</div>


 <%@ include file="../../common/commonScriptsIncludes.html" %>
 <%@ include file="../../common/commonagGridScriptsIncludes.html" %>
    <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/main.js"></script>
	<script src="${staticPath}/js/reports/providerReport/providerReport.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>	
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/search/search.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>	  	
</body>
</html>