<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<title>${DOCUMENT_TYPE} Clerical Review</title>
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
<link href="${staticPath}/css/common/select2.css" rel="stylesheet"	type="text/css" />
</head>

<body class="qualityStageMatchReview">
	<div id="headerContent">
		<%@ include file="../../common/Menu.jsp"%>
	</div>

	<div id="clericalReview">

		
		
		
		<div id="personMergerGrid">
			
			
			
			<ul id="mergePersonTabLinks">
				<li><a  href="#nbsDocumentsToReviewTab" id="nbsReviewTabLink">CLIMS Match Review</a>  </li>
				<li><a  href="#clericalReviewTab" id="personViewTabLink" class="displayNone">Match Review</a>  </li>
				
			</ul>
			<div id="nbsDocumentsToReviewTab" class="jqGridTableSection ">
				<div id="nbsReviewTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				
				<div class=" mB20 centerFlexRowHeaders">
						<div class="mL10"> 
							<div>
								<span
									class="bold font12 displayNone" id="climsBirthYearLabel" >Birth Year: </span><br/>
							</div>
							<div class="mT5">
								<select id="climsBirthYear"  class="required select2Input standardInputSmallText" dependentField = "climsBirthYearLabel"  title="Birth Year" placeholder="Birth Year">
									<option value="">Select</option>
									<option value="ALL">ALL</option>
								
									<c:forEach items="${BIRTH_YEARS}" var="birthYear">
										<option value="${birthYear}">${birthYear}</option>
									</c:forEach>
										
								
								</select>
							</div>
						</div>
						
							  
					
		
				
		   		</div>
		   		
				<table id="nbsDocumentReviewTable"></table>
				<div id="nbsDocumentReviewPager"></div>
			</div>
			<div id="clericalReviewTab" style="clear: both;">
			
			
				<div id="clericalReviewTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				
				<div id="clericalPage" class="displayNone" >
					 <div id="actionButtons" class="mergeButtons" align="center">                  
	                                <span><a class="actionButtons a_demo_search a_demo_continue"  id="mergeStatenoPairs" onClick="mergeDocIdToStateno()" style="color: #FFFFFF; margin-left: 5px;"> Match</a></span>  
	        		</div>
	        		<div class="clearBoth"> </div>
	        		<div style="margin:25px 0 10px 0; "><span>Current Quality Stage Status: </span><span id="currentQualitStageStatusCode" class="bold"></span>
	        		
	        		</div>
	        		
	        		<div class="clearBoth"> </div>
	        		
	                <div id="personViewcomparePage">
	                	<div class="personViewData" id="personView1" style="height:650px">
	                	</div>
	                	<div class="personViewData" id="personView2" style="height:650px">
	                	</div>
	                	<div class="clearBoth"> </div>
	                	<div class="floatLeft"  style="width:48%"></div>
	                	<div id="navigationButtons" style="height:40px; padding-top:5px;float:right;width:48%">
						      <div id="backwardNavigation" class="backwardImage" style="width:15%; float:left; padding-top:15px; padding-left:40px"></div>
							  <div id="forwardNavigation" class="forwardImage" style="width:20%; float:left; padding-top:15px"></div>
						  </div>
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
	
	<div id="personTableContainerModal">
		<table id="personFieldValuesTbl"></table>
	</div>
	
	
	<div id='docViewDlg'>  </div>
	
	
	
	<script type="text/javascript">
		var qsReviewDocumentType = '${DOCUMENT_TYPE}';
	</script>
	<%@ include file="../../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/scan/pdf.js"></script>
	<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/search/search.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
	<script src="${staticPath}/js/review/nbs/climsDataFluxStatusReview.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/select2.js"></script>
</body>
</html>