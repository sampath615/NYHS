<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>




<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Adding New User</title>


	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %>

	<link href="${staticPath}/css/administration/administration.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common/datePicker/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" media="screen" href="${staticPath}/css/common/datePicker/bootstrap-datetimepicker.css">	
	
	



</head>

<body>

<%@ include file="../common/Menu.jsp"%>
<!-- 	<div id="newUserPage" style="margin-left: 150px; width: 1024px;  clear: both;"> --><!--added rzp05 9/2/2020  -->
     <div id="newUserPage" style="margin: auto; width: 1024px;  clear: both;">
		<ul>
			<li>
			    <a href="#newUserPageTab">Creating New User Profile</a>
			</li>
		</ul>
		<div id="newUserPageTab">
			<div id="newUserPageAccordion" style="width: 950px; clear: both;">
				<h3>Validate</h3>
				  <div style="height: 550px;">
					<div id="errorVerifyDiv" class="errorblock" style="display: none;">
						All fields are blank.</div>
					<div class="stdDetailsDiv">
						<div class="leftDiv">
							<div class="standardInputDiv">
								<div style="float: left">
									<font class="standardInputTitle"><spring:message code="label.userID" /></font>
								</div>
								<div style="float: right">
									<input id="verifyUserId" class ="standardInputMedText fieldLevelHelp" tabindex="1" value="" type="text" maxLength="10"/>
								</div>
							</div>
						</div>
						<div class="rightDiv">
							<div class="standardInputDiv">
								<p id="userExistsMsg" style="color: red">
									<span style="float: left; margin: 0 7px 20px 0;"></span>
								</p>
							</div>
						</div>
						<div>
						  <div class="standardInputDiv">&nbsp;</div>
						</div>
						<div>&nbsp;&nbsp;</div>
						<div class="button-row">
							<a class="actionButtons a_demo_search a_demo_accept" style="color: #FFFFFF;" tabindex="2"  onclick="ValidateUser();"> Validate </a> 
						</div>
						<div>&nbsp;&nbsp;</div>
					</div>
				</div>
		   </div>
		   <div id="addUserVisibleDiv">
		    <div id="newUserDetailsAccordion" style="width: 950px; clear: both;">
		    <h3>New User Details</h3>
		      <div id="addUserDiv">
				<jsp:include page="AddUser.jsp"></jsp:include>
	          </div>
	        </div>
	       </div>
	       <form id="verifyUserReload" action="" method="post">
		   </form>
		</div>
   </div>
	<%@ include file="../common/commonScriptsIncludes.html" %> 
	<script type="text/javascript">
	  $(function() {
	    $('.effectiveDateTimePicker').datetimepicker({
	      language: 'en',
	      pickTime: false
	    });
	  });
	 </script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
	<script src="${staticPath}/js/administration/administration.js?v=${buildDate}"></script>
	<script type="text/javascript" src="${staticPath}/js/common/datePicker/bootstrap.js"></script>
	<script type="text/javascript" src="${staticPath}/js/common/datePicker/bootstrap-datetimepicker.js"></script>
	
</body>
	