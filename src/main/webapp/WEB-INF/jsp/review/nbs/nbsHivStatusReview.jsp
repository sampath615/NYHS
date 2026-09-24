<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<html>
<head>
<title>NBS Review</title>
<%@ include file="../../common/common_includes.jsp"%>
<%@ include file="../../common/commonStylesIncludes.html"%>
<%@ include file="../../common/MainMenuIncludes.jsp"%>
<link href="${staticPath}/css/common.css" rel="stylesheet"
	type="text/css" />
<link href="${staticPath}/css/person/clinicalReview.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/person/unMergePerson.css?v=${buildDate}"
	rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/common/select2.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript">
var reviewType = '${REVIEW_TYPE}';
</script>

</head>

<body class="nbsReviewPage">
	<div id="headerContent">
		<%@ include file="../../common/Menu.jsp"%>
	</div>
	<div id="personMergerGrid" class="pageView">
		<ul id="reviewPageTabLinks">
			<li><a href="#nbsDocumentsToReviewTab"
				id="nbsDocumentsToReviewTabLink"  onclick="loadDocumentsToReview()">NBS Review</a></li>
			<li><a style="display: none;" href="#clericalReviewTab" id="nbsComparisionTabLink">Document review</a>
			</li>
		</ul>

		<div id="nbsDocumentsToReviewTab" class="jqGridTableSection ">
			
			<div id="nbsReviewTransactionMessage" class="transactionMessage"><span class="message"></span></div>
			<c:if test="${REVIEW_TYPE == 'NBS_HIV'}">
			<div class=" mB20 centerFlexRowHeaders">
						<div class="mL10"> 
							<div>
								<span
									class="bold font12 displayNone" id="nbsBirthYearLabel" >Birth Year: </span><br/>
							</div>
							<div class="mT5">
								<select id="nbsBirthYear"  class="required select2Input " dependentField = "nbsBirthYearLabel"  title="Birth Year" placeholder="Birth Date" >
								
									<option value="ALL">ALL</option>
								
									<c:forEach items="${BIRTH_YEARS}" var="birthYear">
										<option value="${birthYear}">${birthYear}</option>
									</c:forEach>
										
								
								</select>
							</div>
						</div>
						
							  
					
		
				
		   		</div>
			</c:if>
		
		
		
			
			<table id="nbsDocumentReviewTable"></table>
			<div id="nbsDocumentReviewPager"></div>
		</div>
		<div id="clericalReviewTab" style="width:100%">

				<div id="reviewTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				<div id="actionButtons" class="mergeButton" align="center">
				<c:choose>
					<c:when test="${REVIEW_TYPE == 'NBS_HIV'}">
						<span><a class="actionButtons a_demo_search a_demo_continue" onclick="matchNbsToClims();" id="matchNbsToClims" style="color: #FFFFFF; margin-left: 5px;"> Match</a></span>
					</c:when>
					<c:when test="${REVIEW_TYPE == 'NBS_IND' }">
   						<span><a class="actionButtons a_demo_search a_demo_continue" onclick="confirmExposure();" id="confirmExposure" style="color: #FFFFFF; margin-left: 5px;"> Exposure</a></span>
   						<span><a class="actionButtons a_demo_search a_demo_continue" onclick="confirmNotExposure();" id="confirmNotExposure" style="color: #FFFFFF; margin-left: 5px;"> Not Exposure</a></span>
	   				</c:when>
				</c:choose>
						<span class="pL20"><a class="actionButtons a_demo_search a_demo_cancel" onclick="skipNbsReview();" id="skipNbsReview" style="color: #FFFFFF; margin-left: 5px;"> Skip</a></span>					
				</div>
			    <div id="personViewcomparePage">
                
                		
                		<div id="personView1" class="personViewData leftComparisonTab" style="overflow-y:auto">
								
				
						</div>
						<div id="personView2" class="personViewData rightComparisonTab">
								
				
						</div>
					
				</div>
				
				      
			   				
			   				
                
                
                
        </div>
		
	</div>

	<div id='docViewDlg'>	</div>
	<div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
		<div id="requestCommentsText" style="display:none">
   			<label style="padding-left:0">Comments </label></br>
         		<textarea id="requestComments" class="required" cols="40" rows="4" style="border: thin solid #003366;" maxLength="3000" title="<spring:message code='label.requestComments' />"></textarea>
	  </div>  
   </div>
   

	
	<%@ include file="../../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/review/nbs/nbsReview.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/scan/pdf.js"></script>
	<script src="${staticPath}/js/scan/scanner.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/search/search.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
	<script src="${staticPath}/js/common/select2.js"></script>
</body>
</html>