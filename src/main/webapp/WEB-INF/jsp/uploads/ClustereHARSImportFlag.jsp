<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
	<meta http-equiv="cache-control" content="no-cache" />
	
	<%@ include file="../common/common_includes.jsp" %> 
    <%@ include file="../common/commonStylesIncludes.html" %> 
    <%@ include file="../common/MainMenuIncludes.jsp" %>
        
	<link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/assignments/assignments.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/docs/clusterForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common/datePicker/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" media="screen" href="${staticPath}/css/common/datePicker/bootstrap-datetimepicker.css">	
	
	<title>Cluster eHARS Import Flag</title>
	
</head>

<body id="clustereHARSImportFlag" bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../common/Menu.jsp" %>
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="listeHARSImportFlagTab"><a href="#clusterTab">Cluster eHARS Import Flag</a></li>			
					</ul>
					
					<div id="clusterTab" style="clear: both;">
						<div id="eHARSImportFlagTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
				   		<div id="eHARSImportFlag" class="jqGridTableSection">
							<table id="eHARSImportFlagTable"></table>
							<table id="eHARSImportFlagPager"></table>
						</div>						
						
						
						<c:if  test="${userPermissions['136'] != null }">
							<div id="manageDocuments" class="button-row pT20" style=" text-align:center"> 
								<a id="updateDocuments" class="a_disable fadedResetButton"
									tabindex="9" style="color: #FFFFFF;" onclick="updateDocuments(this);"> UPDATE </a> 
							</div>						
						</c:if>
						
					</div>
							
				</div>		
											
										
		</div> 
	</div>	
	
   <div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
   </div>		
		
    <%@ include file="../common/commonScriptsIncludes.html" %>
    <script src="${staticPath}/js/main.js"></script>
	<script src="${staticPath}/js/reports/clusterEharsImportFlag.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/search/search.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>	  
	
</body>
</html>


