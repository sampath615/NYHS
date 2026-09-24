<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%

pageContext.setAttribute("ASSIGNMENT_30_DAY_NOTE", EnvironmentProperties.envProperties.getProperty("ASSIGNMENT_30_DAY_NOTE"));

%>

<c:set var="userPermissions_workerRegion"
	value="${sessionScope.userPermissions_workerRegion}" />
<script type="text/javascript">
	userPermissionsJSON = ${userPermissions_workerRegion};
</script>	
	
<!-- Assignment Search criteria -->
<div id="asssignmentsStatusCriteria" style="margin-top: 10px;">
	<font class="standardInputTitle">Choose Assignment Status :</font>
	<form:select path="assignmentType"
		onchange="assignmentCategory(this.value);" class="required" title="Assignment Status">
		<form:option value="" label="Select" />
		<form:option value="OPEN_UNV" label="Unviewed" />
		<form:options items="${assignmentTypes}" />
	</form:select>
	<!--To Handle 'MY_NSA' status-->
	<input type="hidden" id="myNsa" value="${myNsa}" /> <font
		class="standardInputTitle" id="textFromDate" style="display: none;"></font>
	<input type="text" id="assignmentFromDate"
		class="standardInputSmallText" value="${startDate}" readonly
		style="width: 80px; display: none;" /> <font
		class="standardInputTitle" id="textToDate" style="display: none;"></font>
	<input type="text" id="assignmentToDate" class="standardInputSmallText"
		value="${endDate}" readonly style="width: 80px; display: none;" />

	<form:hidden path="workerRoleCode" />

	<input type="hidden" id="userWorkerId"
		value="${sessionScope.UserObj.workerId}"> <span
		id="workerSelection"
		class="<c:if test='${param.roleCode == 2}'>displayNone</c:if>">
		<font class="standardInputTitle" id="workersFont">&nbsp;
			Workers :</font> <form:select path="workerId">
			<c:if test="${param.roleCode != 2}">
				<form:option value="-1" label="ALL Workers" />
			</c:if>
			<form:options items="${workers}" />
		</form:select>

	</span> <input type="button" onclick="createAssignmentTable();"
		title="Refresh Assignments" value="Refresh Assignments"
		id="loadAssign">
</div>

<!-- Assignment List results -->
<div id="assignmentsTableDiv" style="display: none;" class="jqGridTableSection mT20">

	<table id="AssignmentsTable"></table>
	<div id="AssignmentsPagerDiv"></div>

</div>
<div id="openAssignment90DaysNote" class="displayNone mL5">
	<label class="highlightOrangeRedColor"><c:out value="${ASSIGNMENT_30_DAY_NOTE}" /></label>
</div>

<div style="clear: both;"></div>
<div class="alignCenter mT20" >


<c:if test="${userPermissions_workerRegion['55'] != null}">
<!-- Worker permissions -->
	<span class="mB15 mT10 mR5 displayNone"
		id="workerReqReassignDiv">
		<a class="a_demo_search assignmentActionButtons colorWhite" onclick="requestReassign();"> Request Reassign </a>
	</span>
	<span
		class="mB15 mT10 mR5 displayNone"
		id="workerRevokeReassignDiv">
		<a class="a_demo_search assignmentActionButtons colorWhite" onclick="revokeReassign();"> Revoke Reassign </a>
	</span>
</c:if>

<c:if test="${userPermissions_workerRegion['54'] != null}">
<!-- Supervisor permissions -->
	<span
		class="mB15 mT10 mR5 displayNone"
		id="superReqReassignDiv">
		<a class="a_demo_search assignmentActionButtons colorWhite" onclick="acceptReassign();"> Accept Reassign </a> 
		<a class="a_demo_search assignmentActionButtons colorWhite mL5" onclick="rejectReassign();">Reject Reassign</a>
	</span>
</c:if>
<c:if test="${userPermissions_workerRegion['53'] != null}">
<!-- Supervisor permissions -->
	<span
		class="mB15 mT10 mR5 displayNone"
		id="superAssignDiv">
		<a class="a_demo_search assignmentActionButtons colorWhite" onclick="assign();">
			Assign </a>
	</span>
	<span
		class="mB15 mT10 mR5 displayNone"
		id="superReassignDiv">
		<a class="a_demo_search assignmentActionButtons colorWhite" onclick="reAssign();">
			Reassign </a>
	</span>
	<span
		class="mB15 mT10 mR5 displayNone"
		id="superReopenDiv">
		<a class="a_demo_search assignmentActionButtons colorWhite " onclick="reOpen();">
			Reopen </a>
	</span>
	
	
	
	<span class="mB15 mT10 mR5 displayNone"
		id="superCloseAssignmentDiv">
		<a class="a_demo_search assignmentActionButtons colorWhite a_continue fadedCancelButton" onclick="adminCloseAssignment();"> Close Assignment </a>
	</span>
	
	

</c:if>


<span class="mB15 mT10 mR5 displayNone "
		id="assignmentListPrint">
		<a class="a_demo_search a_demo_print colorWhite" id="assignmentListPrintBtn" onclick="printAssignmentList();">
			Print </a>
	</span>


</div>