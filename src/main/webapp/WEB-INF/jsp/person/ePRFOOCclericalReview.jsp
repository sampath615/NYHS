<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<title>Clerical Review</title>
<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<%@ include file="../common/MainMenuIncludes.jsp"%>
<link href="${staticPath}/css/common.css" rel="stylesheet"
	type="text/css" />
<link href="${staticPath}/css/person/clinicalReview.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/person/mergePerson.css?v=${buildDate}" rel="stylesheet" type="text/css" />	
<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
</head>

<body>
	<div id="headerContent">
		<%@ include file="../common/Menu.jsp"%>
	</div>

	<div id="clericalReview">

		
		<div id="personMergerGrid">
			
			
			
			<ul id="mergePersonTabLinks">
				<li><a  href="#personDetailsTab" id="personViewTabLink">Recent Demo View</a>  </li>
				<li><a  href="#documentDetailsCompareTab" id="documentDetailsTabLink">Document Timeline</a>  </li>
				
			</ul>
			<div id="personDetailsTab" style="clear: both;">
				<div id="clericalReviewTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				
				 <div id="actionButtons" class="mergeButtons" align="center">                  
                                <span><a class="actionButtons a_demo_search a_demo_continue"  id="mergeStatenoPairs" onClick="mergeDocIdToStateno()" style="color: #FFFFFF; margin-left: 5px;"> Match</a></span>  
                                <span class="pL20"><a class="actionButtons a_demo_search a_demo_cancel" id="skipStatenoPairs" onClick="loadNewDocIdAndStateno()" style="color: #FFFFFF; margin-left: 5px;"> Skip</a></span>
								 <span class="pL20"><a class="actionButtons a_disable fadedRejectButton" id="rejectStatenoPairs" onClick="rejectClericalDocId(this)" style="color: #FFFFFF; margin-left: 5px;"> Not a match </a></span>
        		</div>
        		<div class="clearBoth"> </div>
        		<div style="margin:10px 0" id="clericalReviewMatrix"><span>Total number of Clerical Review documents available :</span><span id="clericalReviewCount"></span></div>
        		
        		<div class="clearBoth"> </div>
        		
                <div id="personViewcomparePage">
                	<div class="personViewData" id="personView1">
                	</div>
                	<div class="personViewData" id="personView2">
                	</div>
                	<div class="clearBoth"> </div>
                	<div class="floatLeft"  style="width:48%"></div>
                	<div id="navigationButtons" style="height:40px; padding-top:5px;float:right;width:48%">
					      <div id="backwardNavigation" class="backwardImage" style="width:15%; float:left; padding-top:15px; padding-left:40px"></div>
						  <div id="forwardNavigation" class="forwardImage" style="width:20%; float:left; padding-top:15px"></div>
					  </div>
                </div>
            </div>
            <div id="documentDetailsCompareTab" style="clear: both;">
                    <div id="documentTimeLine1" class="documentDetails">
                           <div id="documentDetailsDiv1">
                                   <table id="documentDetails1"></table>
                           </div>
                    </div>
                    <div id="documentTimelineBorder" ></div>
                    <div id="documentTimeLine2" >
                           <div id="documentDetailsDiv2">
                                   <table id="documentDetails2"></table>
                           </div>
                    </div>
            </div>
               </div>
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		
		
	</div>
	<div id="confirmMessageDialog" title="Please Confirm?" >
		<h4 id="confirmMessageContent"></h4>
		<div id="requestCommentsText" style="display:none">
   			<label style="padding-left:0">Comments </label></br>
         	<textarea id="requestComments"  cols="40" rows="4" style="border: thin solid #003366;" class="required" maxLength="3000" title="Comments"></textarea>
	  </div>  
   </div>
	</div>
	
	<div id="personTableContainerModal">
		<table id="personFieldValuesTbl"></table>
	</div>
	<div id='docViewDlg'>  </div>
	
	<%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/scan/pdf.js"></script>
	<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
	<script src="${staticPath}/js/person/clinicalReview.js?v=${buildDate}"></script>
</body>
</html>