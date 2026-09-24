<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<html>
<head>
<title>Cluster Partner PS Review</title>
<%@ include file="../../common/common_includes.jsp"%>
<%@ include file="../../common/commonStylesIncludes.html"%>
<%@ include file="../../common/MainMenuIncludes.jsp"%>
<link href="${staticPath}/css/common.css" rel="stylesheet"
	type="text/css" />
<link href="${staticPath}/css/person/person.css?v=${buildDate}" rel="stylesheet" type="text/css" />

</head>

<body class="psCluster">
	<div id="headerContent">
		<%@ include file="../../common/Menu.jsp"%>
	</div>
	<div id="clusterPartnerReviewGrid" class="pageView">
		<ul id="reviewPageTabLinks">
			<li><a href="#clusterPartnerReviewTab"
				id="clusterPartnerReviewTabLink" >PS review</a></li>
		</ul>

		<div id="clusterPartnerReviewTab" class="jqGridTableSection ">
			
			<div id="clusterPartnerReviewTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				
			<table id="clusterPartnerReviewTable"></table>
			<div id="clusterPartnerReviewPager"></div>
		</div>
		
		
	</div>

	<div id='docViewDlg'>	</div>
	<div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
		
		<div id="requiredTempParameters" class="displayNone" class="mT10">
			  
		</div>
		
<!-- 		<div id="psReject"> -->
<!-- 			<label style="padding-left:0">Reason </label> -->
<!-- 			<select id="rejectReason" name="rejectReason" class="required standardinputMedText" title="Reject reason"> -->
<!-- 				<option value=""> -- Select --</option> -->
<%-- 				<c:forEach items="${psClusterPartnerRejectReasons}" var="reason"> --%>
<%-- 					<option value="${reason.LOOKUP_CD}">${reason.LOOKUP_NAME}</option> --%>
<%-- 				</c:forEach> --%>
<!-- 			</select>	 -->
<!-- 		</div>   -->
   </div>
   

	<%@ include file="../../common/addressComboDialog.jsp" %>
	<%@ include file="../../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/review/clusterPartner/clusterPartnerPSReview.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/person.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
	<script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
</body>
</html>