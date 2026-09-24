<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<title>UCP NYC Review</title>
<%@ include file="../../common/common_includes.jsp"%>
<%@ include file="../../common/commonStylesIncludes.html"%>
<%@ include file="../../common/MainMenuIncludes.jsp"%>
<link href="${staticPath}/css/common.css" rel="stylesheet"
	type="text/css" />
<link href="${staticPath}/css/person/clinicalReview.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/person/mergePerson.css?v=${buildDate}" rel="stylesheet" type="text/css" />	
<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/person/unMergePerson.css?v=${buildDate}"
	rel="stylesheet" type="text/css" />
	
	
<style type="text/css">
	#FlaggingSection_NYEHMS{
		display:none;
	}
	#FlaggingSection_NYC{
		display:none;
	}

</style>
</head>



<body class="reviewNYCData">
	<div id="headerContent">
		<%@ include file="../../common/Menu.jsp"%>
	</div>

	<div id="clericalReview">

		
		
		
		<div id="personMergerGrid">
			
			
			
			<ul id="mergePersonTabLinks">
				<li><a  href="#nycFileCompareReviewTab" id="personViewTabLink">UCP Review</a>  </li>
				<li><a  href="#documentDetailsCompareTab" id="documentDetailsTabLink">Document Timeline</a>  </li>
				
			</ul>
			<div id="nycFileCompareReviewTab" class="clearBoth">
			
				<div class="clearBoth" ></div>
			
			
				<div id="fileReviewTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				
				<div id="clericalPage"  class="displayNone">
					 <div id="actionButtons" class="mergeButtons" align="center">                  
	                                <span><a class="actionButtons a_demo_search a_demo_continue"  id="mergeStatenoPairs" onClick="agreeNYCDocument()" style="color: #FFFFFF; margin-left: 5px;"> Agree</a></span>  
	                                <span class="pL20"><a class="actionButtons a_demo_search a_demo_cancel" id="skipStatenoPairs" onClick="loadADAPReviewContent()" style="color: #FFFFFF; margin-left: 5px;"> Skip</a></span>
									 <span class="pL20"><a class="actionButtons a_demo_search a_demo_reject" id="rejectStatenoPairs" onClick="rejectNYCDocumentAsMatch(this)" style="color: #FFFFFF; margin-left: 5px;"> DisAgree </a></span>
	        		</div>
	        		<div class="clearBoth"> </div>
	        		<div style="margin:25px 0 10px 0; ">
	        		
<!-- 	        		<span>Current Quality Stage Status: </span><span id="currentQualitStageStatusCode" class="bold"></span> -->
	        		
	        		<span class="marginLeft30">Total Number of Documents Available for Review: </span><span id="totalDocumentsToReview" class="bold" style="color:orange;"></span>
	        		
	        		</div>
	        		
	        		<div class="clearBoth"> </div>	        		
	        		
	                <div id="personViewcomparePage">
	                	<div class="personViewData" id="personView1">
	                	</div>
	                	<div class="personViewData" id="personView2">
	                	</div>
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
		
		
		<div id="requestCitynoText" class="displayNone">
   			<label class="bold">Cityno: </label>
         	<input id="reviewCityno" style="border: thin solid #003366;" class="required standardInputMedText mL40 " title="cityno" maxlength="10"/>
	  	</div>
	  	
	  	
		<div id="requestCommentsText" class="displayNone mT20">
   			<label  class="bold" style="vertical-align:top">Comments </label>
         	<textarea id="requestComments"  cols="40" rows="4" style="border: thin solid #003366;" class="required mL20" maxLength="3000" title="Comments"></textarea>
	  	</div>
		  
   </div>
	
	<div id="personTableContainerModal">
		<table id="personFieldValuesTbl"></table>
	</div>
	
	
	<div id='docViewDlg'>  </div>
	
	
	<%@ include file="../../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/scan/pdf.js"></script>
	<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
		<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/review/adapReview.js?v=${buildDate}"></script>
</body>
</html>