<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<title>Record Search</title>
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	
	<link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/jquerycombobox.css" rel="stylesheet" type="text/css" />	
	<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />	
	
</head>

<body bgcolor="#e4e4e4" id="personSearch" background="${staticPath}/images/NYEHMSVirusBackground.png" class="search recordSearchFeedBack">

		<div id="floatContent"  style="height: 100%;">
			<%@ include file="../common/Menu.jsp" %>
			<%@ include file="RecordSearchTabDetail.jsp" %>
		</div>
		<div id='docViewDlg'>	</div>
		<div id="confirmMessageDialog" >
			<h4 id="confirmMessageContent"></h4>
			<div id="requestCommentsText" style="display:none">
	   			<label style="padding-left:0"><spring:message code="label.comments" /> </label></br>
          		<textarea id="requestComments" class="required" cols="40" class="" rows="4" style="border: thin solid #003366;" title="<spring:message code='label.requestComments' />"></textarea>
		  </div>  
	   </div>
	   
	    <div id="documentSelectionDialog" class="displayNone">
			<div style="text-align:center; font-weight:bold;margin:5px">
				  	<span class="searchFacilityTxt"> Document Type :</span>
					<span>
							<select id="addDocumentToStateno" class="searchOptions" onchange="addDocumentToStateno(this.value)">
								<option value="">Select</option> 
								<option value="A06.18" testType="RECORD_SEARCH">RECORD_SEARCH</option>
							</select>
					</span>
			</div>
		</div>
		
		
		
		
		
		
	   <%@ include file="../common/addressComboDialog.jsp" %>
	   
	   <%@ include file="../common/commonScriptsIncludes.html" %>
	   
	   <script src="${staticPath}/js/main.js"></script>
		<script src="${staticPath}/js/jquery/jquery.collapsible.js"></script>
		<script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
		<script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
		<script src="${staticPath}/js/scan/pdf.js"></script>
		<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
		<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
		<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
		<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
		<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
		<script src="${staticPath}/js/review/recordSearchReview.js?v=${buildDate}"></script>
		
</body>
</html>