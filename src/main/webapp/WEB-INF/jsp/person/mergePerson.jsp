<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<html>
<head>
<title>Merge</title>
<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<%@ include file="../common/MainMenuIncludes.jsp"%>
<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/person/mergePerson.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
</head>

<body class="merge">
	<div id="headerContent">
		<%@ include file="../common/Menu.jsp"%>
	</div>


		<div id="personMergerGrid" >
			<ul id="mergePersonTabLinks">
				<li><a href="#flaggingTab" id="flaggingTabLink" >Flagging</a></li>
				<li><a style="display: none;" href="#personDetailsTab" id="personViewTabLink">Recent Demo View</a>  </li>
				<li><a style="display: none;" href="#documentDetailsCompareTab" id="documentDetailsTabLink">Document Timeline</a>  </li>
				
			</ul>
			<div id="flaggingTab">
				<div id="providerSearchFormElements">
					<div id="provAccordion">
						<h3><span id="searchForStandardProviderAccordian">Pair the STATENOs</span></h3>
						<div style="height: 350px;">
							<jsp:include page="add/submitStatenosPairs.jsp"></jsp:include>
						</div>
					</div>
				</div>
				
				
				
				<div id="mergeEditTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				
				<div id="flaggerStatenosDetails" class="jqGridTableSection">
<!-- 					<div><span class="flagFont alignCenter"> * STATENOs highlighted in black color are badly merged. </span></div> -->
					<table id="flaggedStatenosTable"></table>
					<div id="flaggedStatenosTablePager"></div>
				</div>
			</div>
			<div id="personDetailsTab" style="clear: both;">
			<c:if  test="${userPermissions['15'] != null}" >	
			   <div id="actionButtons" class="mergeButton" align="center">                  
                                <span><a class="actionButtons a_demo_search a_demo_continue" onclick="checkStatenoPairsForMerging();" id="mergeStatenoPairs" style="color: #FFFFFF; margin-left: 5px;"> Merge</a><span>  
                                <span class="pL20"><a class="actionButtons a_demo_search a_demo_reject" onclick="checkStatenoPairsForRejecting();" id="rejectStatenoPairs" style="color: #FFFFFF; margin-left: 5px;"> Not a match </a><span>
                                <span class="pL20"><a class="actionButtons a_demo_search a_demo_cancel" onclick="skipStatenosFlagging();" id="skipStatenoPairs" style="color: #FFFFFF; margin-left: 5px;"> Skip</a><span>
                </div>
                <div align="center" class="mT5 pB5 highlightRowRedColor"><span id="mergeNote"></span></div>
            </c:if>    
                <div id="personViewcomparePage">
                </div>
                       </div>
                       <div id="documentDetailsCompareTab" style="clear: both;">
                               <div id="documentTimeLine1">
                                      <div id="documentDetailsDiv1">
                                              <table id="documentDetails1"></table>
                                      </div>
                               </div>
                               <div id="documentTimelineBorder" ></div>
                               <div id="documentTimeLine2">
                                      <div id="documentDetailsDiv2">
                                              <table id="documentDetails2"></table>
                                      </div>
                               </div>
                       </div>
               </div>
               
               <div id="personTableContainerModal1">
			         <div id="personFieldValuesView1"  style="width:50%; float:left; border-right:1px solid black; padding-top:1em">
                       <table id="personFieldValuesTbl1"></table>
                     </div>
                     <div id="personFieldValuesView2"  style="width:46%; float:right; padding-top:1em">
                       <table id="personFieldValuesTbl2"></table>
                      </div>
               </div>
               <div id='docViewDlg'>  </div>
			   <div id='docSecondViewDlg'>	</div>	               
		
		<div id="historyInformationDialog">
			<table id="statenoPairHitoryTable"></table>
			<div id="statenoPairHitoryPagerDiv"></div>
		</div>	
		
		<div id="showSimilarFlaggedPairsDialog">
			<table id="showSimilarFlaggedPairsTable"></table>
			<div id="showSimilarFlaggedPairsPagerDiv"></div>
		</div>	
		<div id="confirmMessageDialog" title="Please Confirm?" >
			<h4 id="confirmMessageContent"></h4>
			
			
			<div id="rejectCategoryDiv" class="customInput displayNone pB20">	   			
				<select name="rejectCategory" id="rejectCategory" class="required">
					<option value="">Select</option>
					<option value="M">Reject Minimal Info</option>
					<option value="D">Reject Different Than</option>
				</select>
				
				<div style="color:red"><br><span class="bold">NOTE: Reject Different Than</span> Will create two RIDR ACRFs</div>  
		  </div>
			
			
			
			<div id="requestCommentsText" style="display:none">
	   			<label style="padding-left:0">Comments </label></br>
          		<textarea id="requestComments" class="required" cols="40" rows="4" style="border: thin solid #003366;" maxLength="3000" title="<spring:message code='label.requestComments' />"></textarea>
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
	   			<span style="padding-left:0">Comments </span><br/>
          		<textarea id="confirmRequestComments" class="" cols="40" rows="4" style="border: thin solid #003366;" maxLength="3000" title="<spring:message code='label.requestComments' />"></textarea>
		  </div>   
	   </div>
	
	<%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/scan/pdf.js"></script>
	<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/mergePerson.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>	
</body>
</html>