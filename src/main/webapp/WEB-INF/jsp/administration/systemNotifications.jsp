<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>




<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>NYEHMS Notifications</title>
	
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	
	<link href="${staticPath}/css/common/datePicker/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" media="screen" href="${staticPath}/css/common/datePicker/bootstrap-datetimepicker.css">	
	<link href="${staticPath}/css/administration/administration.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common/autoComplete.css" rel="stylesheet" type="text/css" />
	

</head>

<body>
<%@ include file="../common/Menu.jsp"%>
	<div id="adminPage">
      	<ul id="administrationTabs">
			<li><a href="#adminPageTab" id="searchManage">Manage Email Notifications</a></li>
		</ul>
	   <div id="adminPageTab">
	   		<div id="searchAdminDetailsTab">
	   			<div id="adminManageTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   			
	   			<div class="alignCenter pB10">
	   					<div>
				   			<span class="bold">Document Type :</span>
				   			<span class="mL10"><select onchange="manageEmailNotifications(this)" id="notificationType">
				   				<option value="">Select</option>
				   				<option value="ACRF_RISK">ACRF_RISK</option>
				   				<option value="AHI">AHI</option>
				   				<option value="AHI_NOTIFICATION">AHI_NOTIFICATION</option>
				   				<option value="AIRS_AGENCY">AIRS_AGENCY</option>
				   				<option value="AIRS_UPLOAD_ERR">AIRS_UPLOAD_ERR</option>
				   				<option value="ASSIGNMENT">ASSIGNMENT</option>
				   				<option value="ASSIGNMENT_AHI">ASSIGNMENT_AHI</option>
				   				<option value="COMATCH">COMATCH</option>
				   				<option value="EHARS_NOTIFICATION">EHARS_NOTIFICATION</option>
				   				<option value="EPRF_OOC_SUMMARY">EPRF_OOC_SUMMARY</option>
				   				<option value="FILE_REMINDER">FILE_REMINDER</option>
				   				<option value="HIT_NOTIFICATION">HIT_NOTIFICATION</option>
				   				<option value="MONTHLY_LOINC">Monthly Loinc report</option>
				   				<option value="NIGHTLY_JOB">NIGHTLY_JOB</option>
				   				<option value="PP_ERR">PP_ERR</option>
				   				<option value="PP_OOC_SUB">PP_OOC_SUB</option>
								<option value="PRF_REVIEW">PRF_REVIEW</option>
								<option value="PS_MSG_DEAD_DISPO">PS_MSG_DEAD_DISPO</option>
				   				<option value="PS_MESSAGE">PS_MESSAGE</option>
								<option value="PS_MSG_UPLOAD">PS_MSG_UPLOAD</option>
				   				<option value="PS_NYC_PROV_FAC">PS_NYC_PROV_FAC</option>
								<option value="RAS_DOC_ERR">RAS_DOC_ERR</option>
								<option value="RAS_NOTIFICATION">RAS_NOTIFICATION</option>
								<option value="RECORD_SEARCH_UPLOAD">RECORD_SEARCH_UPLOAD</option>
								<option value="RES_LINKING_ERR">RES_LINKING_ERR</option>
								<option value="WEEKLY_LOINC">Weekly Lab report</option>
								<option value="LAFT_RESOLVE_COMMENTS">LAFT_RESOLVE_COMMENTS</option>
								
								
				   			</select></span>
				   			
			   			</div>
			   			
			   			<div style="width:900px; margin:0px auto" id="showNotificationsList">
			   					
			   					
			   			
			   			</div>
	   		</div>
	   		
	   		
			</div>
	  </div>
   </div>
   
   
   <div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
   </div>
 	<%@ include file="../common/commonScriptsIncludes.html" %>
	<script src="${staticPath}/js/administration/administration.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/autoComplete.js"></script>
</body>