
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div id="accordion">
	<h3>Search</h3>
	<div>
		<form:form id="searchAdminForm" name="searchAdminForm"
			modelAttribute="nyehmsWorkerRegionsViewVO">
			<div style="height: 250px;">
				<div id="errorAdminDiv" class="errorblock" style="display: none;">
					All search fields are blank.</div>
				<div class="stdDetailsDiv" id="stdInfoDiv">
					<div class="leftDiv">
						<div class="standardInputDiv">
							<div class="floatLeft">
								<label class="standardInputTitle"><spring:message
										code="label.userID" /></label>
							</div>
							<div class="floatRight">
								<input id="legacyUid" name="legacyUid"
									class="standardInputMedText mR50 fieldLevelHelp" value="" type="text" maxLength="10" 
									tabindex="1" title="<spring:message code='title.userID' />">
							</div>
						</div>
						<div class="standardInputDiv">
							<div class="floatLeft">
								<label class="standardInputTitle"><spring:message
										code="label.firstname" /></label>
							</div>
							<div class="floatRight">
								<input id="firstName" name="firstName"
									class="standardInputMedText mR50 fieldLevelHelp" value="" maxLength="50" type="text"
									tabindex="2" title="<spring:message code='title.firstName' />">
							</div>
						</div>
						<div class="standardInputDiv">
							<div class="floatLeft">
								<label class="standardInputTitle"><spring:message
										code="label.lastname" /></label>
							</div>
							<div class="floatRight">
								<input id="lastName" name="lastName"
									class="standardInputMedText mR50 fieldLevelHelp" value="" maxLength="50" type="text"
									tabindex="3" title="<spring:message code='title.lastName' />">
							</div>
						</div>
						<div class="standardInputDiv">
							<div class="floatLeft">
								<label class="standardInputTitle"><spring:message
										code="label.region" /></label>
							</div>
							<div class="floatRight">
								<spring:message code="title.region" var="region_title" />
								<form:select path="regionDescription" id="srchRegionDesc"
									class="standardInputMedText mR50" tabindex="4" title="${region_title}">
									<form:option value="" label="--- Select ---" />
									<form:options items="${regionTypes}" />
								</form:select>
							</div>
						</div>
					</div>
					<div class="rightDiv">
						<div class="standardInputDiv">
							<div class="floatLeft">
								<span class="standardInputTitle"><spring:message
										code="label.middleName" /></span>
							</div>
							<div class="floatRight">
								<input id="middleName" name="middleName"
									class="standardInputMedText fieldLevelHelp" value="" maxLength="50" type="text"
									tabindex="5" title="<spring:message code='title.middleName' />" />
							</div>
						</div>

						<div class="standardInputDiv">
							<div class="floatLeft">
								<span class="standardInputTitle"><spring:message
										code="label.email" /></span>
							</div>
							<div class="floatRight">
								<input id="emailAddress" name="emailAddress"
									class="standardInputMedText fieldLevelHelp" type="text" maxLength="500" style="width: 190px;"
									tabindex="6" title="<spring:message code='title.email' />" />
							</div>
						</div>
						<div class="standardInputDiv"></div>
						<div class="standardInputDiv">
							<div class="floatLeft">
								<label class="standardInputTitle"><spring:message
										code="label.role" /></label>
							</div>
							<div class="floatRight">
								<spring:message code="title.role" var="role_title" />

								<form:select path="roleDescription" class="standardInputMedText"
									tabindex="7" title="${role_title}" id="srchRoleDesc">
									<form:option value="" label="--- Select ---" />
									<c:forEach items="${nyehmsRole}" var="nyehmsRoleObj">
										<form:option label="${nyehmsRoleObj.roleDescription}"
											value="${nyehmsRoleObj.roleCode}"
											isExpandable="${nyehmsRoleObj.isExpandable}" />
									</c:forEach>
								</form:select>
							</div>
						</div>
						<!--
			<div class="standardInputDiv">
				<div style="float: left">
					<label class="standardInputTitle"><spring:message code="label.status" /></label>
				</div>
				<div style="float: right">
					<spring:message code="title.status" var="status_title"/>
				    <form:select path="status" class="standardInputMedText mR50"  title="${status_title}">
				        <form:option value="" label="--- Select ---" />
						<form:options items="${statusTypes}"/>
					</form:select>
				</div>
			</div>-->
					</div>
					<div style="clear: both;">
						<br />
					</div>
					<!-- dummy div -->
					<br />
					<div style="text-align: center;">
						<a class="a_demo_search" style="color: #FFFFFF; margin-left: 5px;"
							onclick="displaySearchResults();"
							tabindex="8" id="searchAdminFormResultsButton"> Search </a>
					</div>
					<br />
					<br />					
					<div>
						<label class="redNote">Note:</label><label>A blank search will result in all possible search results.</label>
					</div>
					<div>&nbsp;&nbsp;</div>
				</div>
			</div>
		</form:form>
	</div>
</div>
<div style="clear: both;">
	<br />
</div>
<div id="usersSearchResultsDiv">
	<table id="usersSearchResultsTable"></table>
	<div id="usersSearchResultsPagerDiv"></div>
</div>
<div>&nbsp;&nbsp;</div>

<c:if  test="${userPermissions['27'] != null}" >
	<div id="manageButtons" class="button-row">
		<a id="manageUserEdit" class="a_disable fadedManageButton"
			tabindex="9" style="color: #FFFFFF;" onclick="editUser(this);"> MANAGE USER </a> 
			<a id="terminateUser" class="a_disable fadedCancelButton"  style="color: red; margin-left:5px"
			tabindex="10" onclick="terminateUser(this);"> TERMINATE USER </a>
		<!--<a class="a_demo_search" style ="color:#FFFFFF; margin-left: 5px;" onclick="manageRole();"> MANAGE REGIONROLE </a> -->
	</div>
</c:if>
<div id="permissionsDialog">
	<div id="showRegionRolePermissionsDiv">
		<table id="regionRolePermissionsTable"></table>
		<div id="regionRolePermissionsPager"></div>
	</div>
</div>
<form id="manageProfileLink" action="" method="post"></form>
<div>&nbsp;&nbsp;</div>
<div>&nbsp;&nbsp;</div>