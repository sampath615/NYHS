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
	
	<title>LAFT Follow-up Documents</title>
	
</head>

<body id="flaggedLaftFollowupData" bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../common/Menu.jsp" %>
		<div id="mainContentDiv"">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="listflaggedLaftFollowupTab"><a href="#flaggedLaftFollowupTab">LAFT Follow-up Documents</a></li>			
						<li id="listflaggedLaftResolvedTab"><a href="#flaggedLaftResolvedTab">LAFT Resolved Documents</a></li>							
						<li id="listPersonViewTab" class="displayNone"><a href="#personDetailsTab" >Recent Demo View</a></li>
						<li id="listDocumentViewTab" class="displayNone"><a href="#documentDetailsTab" >Document Timeline</a></li>
						 <li id="listEharsPersonViewTab" class="displayNone"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>										
					</ul>
 					
					<div id="flaggedLaftFollowupTab" style="clear: both;">
						<div id="flaggedLaftFollowupTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
				   		<div id="flaggedLaftFollowup" class="jqGridTableSection">
							<table id="flaggedLaftFollowupTable"></table>
							<table id="flaggedLaftFollowupPager"></table>
						</div>						
						
							<div id="manageDocuments" class="button-row pT20" style=" text-align:center"> 
								<a id="updateDocuments" class="a_disable fadedResetButton"
									tabindex="9" style="color: #FFFFFF;" onclick="updateLaftFollowupDocuments(this);"> COMMENT / RESOLVE </a> 
							</div>						
						
					</div>
					
					<div id="flaggedLaftResolvedTab" style="clear: both;">
						
				   		<div id="flaggedLaftResolved" class="jqGridTableSection">
							<table id="flaggedLaftResolvedTable"></table>
							<table id="flaggedLaftResolvedPager"></table>
						</div>						
						<div> 
							<label class="redNote">Note:</label><span class="highlightRowRedColor">Stateno highlighted in red font are no longer Active</span>
						</div>
					</div>
					
					<div id="personDetailsTab" style="clear: both; overflow: scroll"></div>					
						
					<div id="documentDetailsTab" style="clear: both;">
					
						<div id="associateDocToStatenoTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<form name="documentForm" class="documentDetails">
							<div id="documentDetailsDiv">
								<table id="documentDetails"></table>
								<div id="documentDetailsPagerDiv"></div>
							</div>
						</form>
					</div>	
					<div id="eharsPersonDetailsTab" style="clear: both; overflow: scroll">
		
		  			 </div>												
				</div>		
											
										
		</div> 
	</div>	
	
	<div id='docViewDlg'>	</div>		
	
   <div id="confirmMessageDialog" >
		
		<h4 id="confirmMessageContent"></h4>
		<div>
			<label style="padding-left:0">Resolution</label>
			<select id="resolutionId" name="resolutionId" class="standardinputMedText">
				<option value=""> -- Select --</option>
				<c:forEach items="${laftFollowupResolutions}" var="resolution">
					<option value="${resolution.RESOLUTION_ID}">${resolution.RESOLUTION_DESC}</option>
				</c:forEach>
			</select>	
		</div>
	  	
	  	<div class="clearBoth mT10">
	  		<span class="highlightGreenColor bold">*Do not select resolution if only adding comments</span>
	  	</div>
		
		<div id="resolutionCommentsText">
   			<br><label style="padding-left:0">Comments </label><br>
         	<textarea id="resolutionComments"  cols="40" rows="4" style="border: thin solid #003366;" class="required" maxLength="3000" title="Comments"></textarea>
	  	</div>
	  
   </div>		
		
    <%@ include file="../common/commonScriptsIncludes.html" %>
    <script src="${staticPath}/js/main.js"></script>
	<script src="${staticPath}/js/lab/flaggedLaftFollowupDocuments.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/search/search.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>	  
	
</body>
</html>


