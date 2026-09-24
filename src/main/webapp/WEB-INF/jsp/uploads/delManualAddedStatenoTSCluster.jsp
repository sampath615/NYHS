<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload CDC Cluster IDs</title>
<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<%@ include file="../common/MainMenuIncludes.jsp"%>

	<link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/assignments/assignments.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/upload/upload.css?v=${buildDate}" rel="stylesheet" type="text/css" />

</head>

<body class="delManualAddedStatenoTSCluster">
	<div id="floatContent"  style="background-color: transparent" >
			<%@ include file="../common/Menu.jsp"%>
			<div id="delManualAddedStatenoTSClusterPage" style="display:none">
		
				<div id="tabs">
				<ul id="manualAddedStatenoTab">
					<li id="delManualAddedStatenoTab"><a href="#viewManualAddedStatenoTab">Manually Added Stateno for Time-Space Cluster</a></li>				
					<li id="listPersonViewTab" class="displayNone"><a href="#personDetailsTab" >Recent Demo View</a></li>
					<li id="listDocumentViewTab" class="displayNone"><a href="#documentDetailsTab" >Document Timeline</a></li>					
				</ul>
				
					<div id="viewManualAddedStatenoTab">
						<div id="delManualAddedStatenoTransactionMessage" class="transactionMessage"><span class="message"></span></div>
					
						<div class="alignCenter pB10" id="clusterIdSection">
							<span class="bold">Cluster ID :</span>
							<span class="mL10"><select onchange="loadManualAddedStatenosTSCluster(this)">
								<option value="">Select</option>
								<c:forEach items="${clusterIds}" var="clusterId">
									<option value="${clusterId}">${clusterId}</option>
								</c:forEach>
							</select></span>
						</div>
						
						<div id="clusterCommonInfo" class="displayNone" style="margin-left:60px;margin-bottom: 50px;">
							<div class=" width90Perc">
								<div class="fieldHolder20"><div><span>Dx Start Dt:</span><span>${clusterInfo.dx_start_date}</span></div></div>
								<div class="fieldHolder20"><div><span>Dx End Dt:</span></div></div>
								<div class="fieldHolder20"><div><span>Create Dt:</span></div></div>
								<div class="fieldHolder33"><div><span>Counties:</span></div></div>
								<div class="fieldHolder100"><div><span>Description:</span></div></div>								
							</div>
						</div>
 						
				   		<div id="manualAddedStatenoTSClusterInfo" class="jqGridTableSection">
							<table id="manualAddedStatenoTSClusterInfoTable"></table>
							<div id="manualAddedStatenoTSClusterInfoPager"></div>
						</div>
						
						<div id="manageDocuments" class="button-row displayNone pT20" style=" text-align:center"> 
							<a id="deleteStatenos" class="a_disable fadedCancelButton"  style="color: red; margin-left:5px" onclick="deletManualAddedStatenos();"> DELETE </a>
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
	
	<div id='docViewDlg'>	</div>	
	<div id='docSecondViewDlg'>	</div>	
	
   <div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
   </div>	

	<%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/uploads/fileUpload.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>	
    <script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/search/search.js?v=${buildDate}"></script>    
    <script src="${staticPath}/js/main.js"></script>	
</body>