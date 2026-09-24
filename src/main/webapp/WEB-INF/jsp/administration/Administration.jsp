<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>




<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Admin Home</title>
	
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	
	<link href="${staticPath}/css/common/datePicker/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" media="screen" href="${staticPath}/css/common/datePicker/bootstrap-datetimepicker.css">	
	<link href="${staticPath}/css/administration/administration.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	

</head>

<body class="manageUser">
<%@ include file="../common/Menu.jsp"%>
	<div id="adminPage">
      	<ul id="administrationTabs">
			<li><a href="#adminPageTab" id="searchManage">Admin Page</a></li>
			<li><a href="#adminPageTab"  id="manageUser" class="displayNone" >Manage User</a></li>
		</ul>
	   <div id="adminPageTab">
	   		<div id="searchAdminDetailsTab">
	   			<div id="adminManageTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	   			
				<%@ include file="../administration/SearchAdminForm.jsp"%>
			</div>
			<div id="manageUserTab" class="displayNone">
		  		<%@ include file="../administration/ManageProfile.jsp"%>
		    </div>	 
	  </div>
	  	
   </div>
   
   
   <div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
   </div>
 	<%@ include file="../common/commonScriptsIncludes.html" %>
 	<script type="text/javascript">
	 
	  $(document).ready(function() {
	  	var loadPageManageUserWithDefaultWorkerId = '<c:out value="${param.workerId}" />';
	  	if(loadPageManageUserWithDefaultWorkerId){
	  		manageUser(loadPageManageUserWithDefaultWorkerId);
	  	}
	  });
	  
	</script> 
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
	<script src="${staticPath}/js/administration/administration.js?v=${buildDate}"></script>
	<script type="text/javascript" src="${staticPath}/js/common/datePicker/bootstrap.js"></script>
	<script type="text/javascript" src="${staticPath}/js/common/datePicker/bootstrap-datetimepicker.js"></script>
		<script src="${staticPath}/js/common/jquery.toggles.js"></script> 

</body>