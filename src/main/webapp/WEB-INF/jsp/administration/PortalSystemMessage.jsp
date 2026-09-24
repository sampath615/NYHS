<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Portal System Message</title>

	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	
	<link href="${staticPath}/css/administration/administration.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common/datePicker/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" media="screen" href="${staticPath}/css/common/datePicker/bootstrap-datetimepicker.css">

</head>

<body>
	<%@ include file="../common/Menu.jsp"%>
	<div id="systemMessagingPage" >
		<ul id="portalSysytemMessageStatusTab">
			<li><a href="#adminManageProfPageTab" id="activeSystemMessageTab" status="Active">Active Messages</a></li>
			<li><a href="#adminManageProfPageTab" id="inActiveSystemMessageTab" status="InActive">Inactive Messages</a></li>
		</ul>
		<div id="adminManageProfPageTab">
			<div class="systemMessageDetailsDiv" >
				<div id="gridLayoutsDiv">
	
					<div class="systemMessageManageTableDiv" >
					   <table id="systemMessageManageResultsTable"></table>
					   <div id="systemMessageManageResultsPagerDiv"></div>
					</div>

					<c:if  test="${userPermissions['26'] != null}" >
						<div style="margin:10px 0 10 326px" class="displaynone" id="actionButtons">
							 <a class="a_disable fadedSaveButton" id="updateSystemMessage" tabindex="1" style="color: #FFFFFF;display:inline-block" 	onclick="updateSystemMessage()"> Update </a> 
							 <a class="a_disable fadedCancelButton" id="deleteSystemMessage" tabindex="2" style="color: #FFFFFF;display:inline-block" 	onclick="deletedSystemMessage()"> Delete </a> 
							 <a class="actionButtons a_demo_search a_demo_add" tabindex="3" id="newSystemMessage" style="color: #FFFFFF;display:inline-block" 	onclick="newSystemMessage()"> New Message </a>
						</div>
					</c:if>
				</div> 
			</div>
		</div>
	</div>
	
	
	<div id="historyInformationDialog" >
		<div id="systemMessageHistoryDiv">
			<div id="systemMessageHistory">
				<table id="systemMessageHistoryTable"></table>
				<div id="systemMessageHistoryPager"></div>
			</div>	
			
			
			<form id="saveSystemMessage" class="displaynone" href="#">
				
					
				<div id="systemMessagingDetails">
					<div class="standardInputDiv bottom10">
						<div class="label160">
							<label class="standardInputTitle">Module Name</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<select name="moduleName" id="moduleName" class="required" title="Module Name">
								<option  value="" >Select</option>
								<c:forEach items="${moduleNames}" var="moduleName">
									<option value="${moduleName.PP_MODULE_CD}" >${moduleName.PP_MODULE_NAME}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="standardInputDiv bottom10">
						<div class="label160">
							<label class="standardInputTitle"><spring:message code="label.effectiveStartDate" /></label>
						</div>
						<div class="inputFieldInDialogWindow effectiveDateTimePicker">
							<input class=" required readonly" data-format="MM/dd/yyyy HH:mm:ss PP" tabindex="-1" name="effectiveStartDate" readonly id="effectiveStartDate" dateValidations="noPastDate" title="<spring:message code='title.effectiveStartDate' />"/>
								<span id="effectiveStartDatePickerIcon" class="add-on">
								 <span class="icon-calendar" data-date-icon="icon-calendar" data-time-icon="icon-time" tabindex="4">
      							</span>
      							</span>
							
						</div>
					</div>
					<div class="standardInputDiv bottom10">
						<div class="label160">
							<label class="standardInputTitle"><spring:message code="label.effectiveEndDate" /></label>
						</div>
						<div class="inputFieldInDialogWindow effectiveDateTimePicker">
							<input class=" readonly" data-format="MM/dd/yyyy HH:mm:ss PP" tabindex="-1" name="effectiveEndDate" readonly id="effectiveEndDate" dateValidations="noPastDate" title="<spring:message code='title.effectiveEndDate' />"/>
								<span class="add-on">
								 <span class="icon-calendar" data-date-icon="icon-calendar" data-time-icon="icon-time" tabindex="5">
      							</span>	
      							</span>
						</div>
					</div>
					<div class="bottom10">
						<div class="label160">
							<label class="standardInputTitle"><spring:message code="label.messageBody" /></label>
						</div>
						<div class="inputFieldInDialogWindow">
							<textarea id="messageBody" class="required" name="messageBody" tabindex="6" title="<spring:message code='title.messageBody'/>"   maxlength="4000"></textarea>
						</div>
					</div>
					 
					<div class="button-row">
						 <a class="actionButtons a_demo_search a_demo_save" tabindex="7" style="color: #FFFFFF;display:inline-block" id="messageAction" 	onclick="saveSystemMessage();">Save</a> 
					</div>
				</div>
			</form>	
			
			
			
			
			
			
		</div>
	</div>

	
  <%@ include file="../common/commonScriptsIncludes.html" %> 
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
	<script src="${staticPath}/js/administration/portalsystemMessage.js?v=${buildDate}"></script>
	<script type="text/javascript" src="${staticPath}/js/common/datePicker/bootstrap.js"></script>
	<script type="text/javascript" src="${staticPath}/js/common/datePicker/bootstrap-datetimepicker.js"></script> 
	
	<script type="text/javascript">
	  $(function() {
	    $('.effectiveDateTimePicker').datetimepicker({
	      language: 'en',
	      pick12HourFormat: true
	    });
	  });
	</script>
</body>
