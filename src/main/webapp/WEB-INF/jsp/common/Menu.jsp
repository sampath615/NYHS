<%@ page import="gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="header">
	<div class="logo">
		<a href="${contextPath}/web/getAssignmentsTally"><img src="${staticPath}/images/mainmenu/logo.fw.png" width="315" height="130"/></a>
	</div>
	
	<div style= "margin-right:50px">
		<div class="buttons">
			<table width="100%" border="0" cellspacing="5" cellpadding="5">
				<tr>
					<c:if  test="${userPermissions['76'] != null}" >
						<td><a class="a_demo_search"  href="${contextPath}/web/getSearchView"> SEARCH </a></td>
					</c:if>
					<td><a class="actionButtons a_demo_search a_demo_save" onClick="userGuideModal();"> USER GUIDE </a></td>
					<td><a class="a_demo_four" href="${contextPath}/web/helpline"> HELPLINE </a></td>
			<%-- 		<td><a class="a_demo_mess" href="${contextPath}/web/messageBox"> MESSAGING <span id='messCount' class='badge'><span></a></td>		 --%>			
					<td><a class="a_demo_log" href="${contextPath}/web/logout"> LOG OUT </a></td>
				</tr>
			</table>
			<c:if test="${sessionScope.UserObj.userFullName != ''}">
				<div class="welcomeMessage">
					<div id="welcomeUserDetails">
						Welcome 
					  	<span class="bold"><c:out value="${sessionScope.UserObj.userFullName}"/></span>
					  	<c:if test="${sessionScope.UserObj.lastLogin != ''}">
					  		<span id="lastLoginTimeStamp">Last Login <c:out value="${sessionScope.UserObj.lastLogin}" /></span>
					  	</c:if>
					</div>
				</div>
			</c:if>     
	    </div>
	</div>    
</div>

<div id="menuNavigation">
	<ul id="nav">
	</ul>
</div>

<!-- #nav2 END-->

<!--=====================================
/Top Fixed Navigation Menu END 
======================================-->

<div class="menubar">
	<div  id="scroll-text"></div>
</div>

<!--
============= 
ONLY FOR DEMO 
=============
 -->
<div class="clear" style="clear: both;"></div>

<!-- Warning message -->
	



<!-- This is for Session Timeout message modal ------------------------------------------------------------------------------- -->
	
<div id="sessionTimeOut" title="Session Expired"></div>

<!---------------------------------------------------------------------------------------------------------------------------- -->
