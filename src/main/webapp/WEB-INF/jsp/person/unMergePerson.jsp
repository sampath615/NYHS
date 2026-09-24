<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<html>
<head>
<title>Unmerge</title>
<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<%@ include file="../common/MainMenuIncludes.jsp"%>
<link href="${staticPath}/css/common.css" rel="stylesheet"
	type="text/css" />
<link href="${staticPath}/css/person/unMergePerson.css?v=${buildDate}"
	rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
</head>

<body class="unmerge">
	<div id="headerContent">
		<%@ include file="../common/Menu.jsp"%>
	</div>

	<div id="personUnMergeGrid">
		<ul id="unMergePersonTabLinks">
			<li><a href="#badlyMergedStatenosTab"
				id="badlyMergedStatenosTabLink"  onclick="badlyMergedTable()">Bad Merge</a></li>
			<li><a style="display: none;" href="#unMergeComparisionTab" id="unMergeComparisionTabLink">Bad Merge review</a>
			</li>
		</ul>

		<div id="badlyMergedStatenosTab" class="jqGridTableSection ">
			<table id="badlyMergedStatenosTable"></table>
			<div id="badlyMergedStatenosTablePager"></div>
		</div>
		<div id="unMergeComparisionTab" style="width:100%">
			<jsp:include page="unMerge/ComparisonPage.jsp"></jsp:include>
		</div>
		
	</div>

	<div id='docViewDlg'>	</div>
	<div id='docSecondViewDlg'>	</div>		
	
	<div id="historyInformationDialog">
		<table id="statenoHitoryTable"></table>
		<div id="statenoHitoryPagerDiv"></div>
	</div>	
	
	<div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
		<div id="requestCommentsText" style="display:none">
   			<label style="padding-left:0">Comments </label></br>
         		<textarea id="requestComments" class="required" cols="40" rows="4" style="border: thin solid #003366;" maxLength="3000" title="<spring:message code='label.requestComments' />"></textarea>
	  </div>  
   </div>
	   
   <div id="unMergeStNoDialog" title="Please Confirm?" >
   		
	  <div id="unMergeStNotext">
   			
	  </div>   
   </div>
   
   
   <div id="confirmMergeStNoDialog" title="Please Confirm?" >
			<h4 id="confirmMergeContentContent"> Which STATENO should be retained? </h4>
			<div id="confirmMergeStNoRadios">	   			
				<input type="radio" requiredgroup="requiredgroup" name="mergeStateNo" id="mergeStNo1" value="STATENO1" title="Merging STATENO" />
				<span id="mergeStNo1Label"></span><br>
				<input type="radio" requiredgroup="requiredgroup" name="mergeStateNo" id="mergeStNo2" value="STATENO2" title="Merging STATENO"/>
				<span id="mergeStNo2label"></span>  
		  </div>
		  <div style="color:red"><br>Note: Don't forget to check eHARS for RIDR documents</div>
		  </br>
		  <div id="confirmMergeStNotext">
	   			<span style="padding-left:0">Comments </span></br>
          		<textarea id="confirmMergeRequestComments" class="" cols="40" rows="4" style="border: thin solid #003366;" maxLength="3000" title="<spring:message code='label.requestComments' />"></textarea>
		  </div>   
	   </div>
	
	<%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/person/unMergePerson.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/scan/pdf.js"></script>
	<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/search/search.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
</body>
</html>