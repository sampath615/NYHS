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

	
<title>Provider Report By PRF Received Monthly</title>
<style>
	.ui-datepicker-calendar {
    display: none;
    }
    
	</style>
</head>
<body bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">
  <%@ include file="../../common/Menu.jsp" %>
 <div id="floatContent"  style="background-color: transparent" >
    <div id="mainContentDiv">
    	<div id = "tabs">
    		<ul id="Tabs">
    			<li id="providerReportTab"><a href="#providerReportbyPRFReceivedTab">Provider Report By PRF Received Monthly</a></li>
    		</ul>
    		
    		<div id="providerReportbyPRFReceivedTab"  style="clear: both; height: 700px;  width: 1000px;">
    			
    			<div id="providerReportsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				
					   <div id="provReportByPrfRec" class="pT20 pB20">
					   		<form action="getProvReportByPRFRec" id ="loadProvReportByPRFRecData">
					   				<div id="dateRange" class="floatLeft" style="height:25px">
					   					<font class="standardInputTitle" id="textToDate" >Date: </font>
										<font class="standardInputTitle" id="textToDate" >From: </font>
										<input id="fromYear" class="required  date-picker"  readonly/>
										<font class="standardInputTitle" id="workersFont" >To: </font>
										<input id="toYear" class="required date-picker" readonly/>
					   				</div>
					   				
					   				<div class="mL50 floatLeft">
											<input type="button" onclick="getProvReportByPRFReceived();" title="Generate Report" value="Generate Report"  id="loadProvReportByPRFRec">										
										</div>	
					   		</form>
					   		 <div class ="mL140 floatLeft">
								<button onclick="onBtExport()" style="margin-bottom: 5px; font-weight: bold;">Export</button>
							</div>
					   </div>
				      
				      <div class="clear" style="clear: both;"></div>
					 <div class="floatLeft mT20 mL20">
					        
					     <div id="loadProvReportByPRFRecGrid"  style="height: 550px;width:800px;" class="ag-theme-balham"></div>
					 </div>								 
			</div>
    	</div>
    </div>

</div>



    <%@ include file="../../common/commonScriptsIncludes.html" %>
    <%@ include file="../../common/commonagGridScriptsIncludes.html" %>
    <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/main.js"></script>
	<script src="${staticPath}/js/reports/providerReport/providerReport.js?v=${buildDate}"></script>
	<%-- <script src="${staticPath}/js/common/jquery.fileDownload.js"></script> --%>
</body>
</html>