<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
</head>
<body class="assignments">
	<div id="assignmentsSearchCriteria">
		<div style="margin-top: 5px" class="assignmentsClassficationHeader">
			<form:form modelAttribute="assignmentsHomeVO"
				action="getAssignmentsList">
				<div class="assignmentsClassfication" style="margin-bottom: 5px;">

					<div>
						<font class="standardInputTitle">Assignments For :</font> <font>${sessionScope.UserObj.userName}</font>
						<div id="assignmentRoleType" class="inlineBlock">
							<font class="standardInputTitle"><spring:message
									code="label.role" /> :</font>

							<%-- 								<c:when test="${fn:length(sessionScope.UserObj.regionMap) == 1}"> --%>
							<%-- 									<font>${sessionScope.UserObj.regionMap[assignmentsHomeVO.workerRegionId]}</font> --%>
							<%-- 									<form:hidden path="workerRegionId"/> --%>
							<%-- 								</c:when> --%>
							<%-- 			userRoleCodes					<c:otherwise> --%>



							<form:select path="workerRoleCode"
								onchange="reloadRegionsList(this.value);">
								<c:if test="${fn:length(userRoleCodes) > 1}">
									<form:option value="0" label="Select" />
								</c:if>
								<form:options items="${userRoleCodes}" />
							</form:select>
						</div>
						
						
						<div id="assignmentWorkerRegion"
							class="<c:if test='${regionMapforSelectedRole == null}'>displayNone</c:if>">
							<font class="standardInputTitle"><spring:message
									code="label.region" /> :</font>
									
																			
							<form:select path="workerRegionId" class="required" onchange="reloadPageWithWorkerRegion(this);">
								<form:option value="0" label="Select" />
								<c:forEach items="${regionMapforSelectedRole}" var="workerRegion">
									<form:option value="${workerRegion.workerRegionId}" cssClass="${workerRegion.regionCode}" >${workerRegion.regionDescription}</form:option> 											
								</c:forEach>
							</form:select>
						</div>
						
					</div>
					
					<div id="roleAndRegionBasedContent" class="displayNone">
					
					</div>
					
			
				</div>
			</form:form>
		</div>
	</div>

	<div id="assignReassignModalDiv">
		<div id="selectAssigneeDiv"
			style="margin-top: 15px; text-align: center;">
			<form:form modelAttribute="assignmentsHomeVO"
				id="assignReassignModalForm">
				<font class="standardInputTitle" id="workersFont">Select
					Worker :</font>
				<form:select path="workerId" id="assigneeId">
					
				</form:select>
			</form:form>

			<div style="margin-top: 30px;">
				<ul id="menu-css" style="padding: 0px;">
					<li><a href="#" onClick="javascript:setAssignReassignWorker(true);">OK</a></li>
					<li><a href="javascript:closeAssignReassignModal();">Cancel</a></li>
				</ul>
			</div>
		</div>


		<div id="assignReassignConfirmDiv" style="display: none;">
			<div id="assignReassignConfirmText"
				style="margin-top: 15px; text-align: justify;"></div>
			<div id="assignReassignAssignments" style="margin-top: 20px;">
				<table id="assignReassignConfirmTable"></table>
				<div id="assignReassignConfirmPagerDiv"></div>
			</div>
	
			<div id="assignReassignCommentsDiv"
				style="text-align: center; width: 70%; margin-left: auto; margin-right: auto; border: thin dotted #003366; margin-top: 20px;">
				<div style="margin-bottom: 15px; margin-top: 15px;">
					<a style="vertical-align: top; margin-top: 5px;"><spring:message
							code="label.comments" /></a>
					<textarea id="assignReassignText" cols="40" rows="4"
						style="border: thin solid #003366;"></textarea>
				</div>
			</div>
	
			<div id="acrfWarningText"
				style="margin-top: 15px; text-align: justify;"></div>
			<div style="margin-top: 30px; text-align: center;">
				<ul id="menu-css" style="padding: 0px;">
					<li><a href="javascript:submitAssignReassign();">Submit</a></li>
					<li><a href="javascript:closeAssignReassignModal();">Cancel</a></li>
				</ul>
			</div>
		</div>
	
	</div>


<script type="text/javascript">
	var _workerRoleCode = '<c:out value="${param.workerRoleCode}" />';
	var _workerRegionId = '<c:out value="${param.regionName}" />';
	var _assignmentType = '<c:out value="${param.assignmentType}" />';
	var _workerId = '<c:out value="${param.workerId}" />';
</script>

</body>

</html>