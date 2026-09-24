	<link rel="stylesheet" href="${staticPath}/css/reports/toggles-light.css" type="text/css"/>

<div id="adminManageProfPageTab">
	<form:form modelAttribute="nyehmsWorkerRegionsViewVO"
		id="managenyehmsWorkerRegionsViewVO">
		<div class="userDetailsDiv">
           <div style="float: right; padding-right: 70px; padding-top: 2px;color:#00BFFF">*Please save the details to effect the changes</div>
			<div id="userDetailsInputFieldsDiv" class="pT10">
				<div>
					<div class="fieldHolder25">
						<div class="labelInDialogWindowProf  ">
							<label class="manageUserInputTitle"><spring:message
									code="label.userID" /></label>
						</div>
						<div class="inputFieldInDialogWindow pT3">
							<span id="mngProlegacyId" >${nyehmsWorkerRegionsViewVO.legacyUid}</span>
						</div>
					</div>
					<div class="fieldHolder35" >
						<div class="flw40  ">
							<label class="manageUserInputTitle"><spring:message
									code="label.firstname" /></label>
						</div>
						<div class="flw60 pT3">
							<spring:message code="title.firstName" var="firstname_title" />
							<form:input id="mngProfirstName" class="standardInputText required fieldLevelHelp" style="width:160px" tabindex="11" 
								path="firstName" title="${firstname_title}"  maxLength="50"></form:input>
						</div>
					</div>
					<div class="flw40">
						<div class="flw40  ">
							<label class="manageUserInputTitle"><spring:message
									code="label.lastname" /></label>
						</div>
						<div class="flw60 pT3">
							<input id="mngProlastName" class="standardInputText required fieldLevelHelp" style="width:160px" tabindex="12" type="text"
								title="<spring:message code='title.lastName' />"   maxLength="50"/>
						</div>
						
						
					</div>
				</div>
				<div class="clearBoth pT10">
					<div class="fieldHolder25">
						<div class="flw40  ">
							<label class="manageUserInputTitle"><spring:message
									code="label.locked" /></label>
						</div>
						<div class="flw60">
							<div class="toggle-light">
							<div class=toggle data-checkbox=userLockedStatus tabindex="15" ></div>
							</div>
							<input type=checkbox disabled=disabled class=userLockedStatus>
						</div>
					</div>
					<div class="fieldHolder35">
						<div class="flw40  ">
							<label class="manageUserInputTitle">User Type</label>
						</div>
						<div class="flw60 pT3">
					       <form:select path="userTypeCode" id="mngProUserType" class="required  readonly" style="width:200px" tabindex="4" onchange="enableDisableRegion(this);" title="${userType_title}">
							  <form:options items="${userTypes}"/>
						   </form:select>							
						</div>
					</div>
					
					<div class="flw40">
						<div class="flw40  ">
							<label class="manageUserInputTitle"><spring:message
									code="label.email" /></label>
						</div>
						<div class="flw60 pT3">
							<input id="mngProEmailAddress" class="standardInputText  required fieldLevelHelp" tabindex="13"  style="width:200px"
								type="text" title="<spring:message code='title.email' />"  maxLength="500"/>
						</div>
					</div>
				</div>	
				
				
				
				
				
				
				
					
				
				
				
				
				<div class="clearBoth pT10">
					
					<div class="fieldHolder25">
						<div class="flw40">
							<label class="manageUserInputTitle">Telecommute</label>
						</div>
						<div class="inputFieldInDialogWindow flw60">
							<form:select path="telecommute" id="mngProTelecommute" class="mL5" tabindex="4" onchange="enableDisableTelecommuteDate(this);" title="VPN Access">
							  <option value="N">No</option>
							  <option value="Y">Yes</option>
						   </form:select>
						</div>
					</div>
					
					<div class="fieldHolder35">
						<div class="flw40">
							<label class="manageUserInputTitle">VPN Access</label>
						</div>
						<div class="inputFieldInDialogWindow flw60">
							<form:select path="vpnAccess" id="mngProVpnAccess" class="" tabindex="4" onchange="enableDisableVpnApproveDate(this);" title="VPN Access">
							  <option value="N">No</option>
							  <option value="Y">Yes</option>
						   </form:select>
						</div>
					</div>
					
						
					
					
					
					<div class="fieldHolder40">
						<div class="flw40">
							<label class="manageUserInputTitle">VPN Approve Date</label>
						</div>
						<div class="flw60 effectiveDateTimePicker pT3">
							<input id="mngProVpnApproveDate"
								class="standardInputVerySmall readonly" readonly dateValidations="noFutureDate" data-format="MM/dd/yyyy" type="text"
								title="VPN Approve Date" tabindex="-1"/>
								<span id="vpnApproveDateIcon" class="add-on">
									 <span class="icon-calendar" data-date-icon="icon-calendar" data-time-icon="icon-time" tabindex="14">
	      							</span>	
	      							</span>
						</div>
					</div>
				</div>
				
				<div class="clearBoth pT10">
							
					<div class="flw40 ">
						<div class="flw50">
							<label class="manageUserInputTitle">Telecommute Trng Date</label>
						</div>
						<div class="flw50 effectiveDateTimePicker pT3">
							<input id="mngProTelecommuteTrngDate"
								class="standardInputVerySmall readonly" readonly dateValidations="noFutureDate" data-format="MM/dd/yyyy" type="text"
								title="Telecommute Trng Date" tabindex="-1" />
								<span id="teleCommuteDateIcon" class="add-on">
									 <span class="icon-calendar" data-date-icon="icon-calendar" data-time-icon="icon-time" tabindex="14">
	      							</span>	
	      							</span>
						</div>
					</div>
					
					<div class="flw30">
						<div class="flw40">
							<label class="manageUserInputTitle">Trainer</label>
						</div>
						<div class="inputFieldInDialogWindow flw60">
							<form:select path="tlcmmtTrngBy" id="mngTlcmmtTrngBy" class="" tabindex="4"  title="TeleCommunicate Trainer">
							  <form:option value="" label="--- Select ---" />
								
								<c:forEach var="trainerDetailsMap" items="${trainerList}" >
										<form:option value="${trainerDetailsMap.TRAINER_ID}" label="${trainerDetailsMap.TRAINER_NAME}" />
								</c:forEach>
									
						   </form:select>
						</div>
					</div>
					<div class="flw30">
						<div class="flw60">
							<label class="manageUserInputTitle">Attestation Received</label>
						</div>
						<div class="inputFieldInDialogWindow flw40">
							<form:select path="tlcmmtTrngAtstn" id="mngTlcmmtTrngAtstn" class="" tabindex="4"  title="Telecommute training Attestation">
							  <option value=""></option>
							  <option value="N">No</option>
							  <option value="Y">Yes</option>
						   </form:select>
						</div>
					</div>
				</div>
				
				<div class="clearBoth pT10">
				
				
					
				
							
							
					 <div class="flw40">
						<div class="flw50">
							<label class="manageUserInputTitle">C&S Trng Date</label>
						</div>
						<div class="inputFieldInDialogWindow effectiveDateTimePicker pT3 flw50">
							<input id="mngProCNSTrainingDate"
								class="standardInputVerySmall readonly" readonly dateValidations="noFutureDate" data-format="MM/dd/yyyy" type="text"
								title="<spring:message code='title.cnsDate' />" tabindex="-1"/>
								<span class="add-on">
									 <span class="icon-calendar" data-date-icon="icon-calendar" data-time-icon="icon-time" tabindex="14">
	      							</span>	
	      							</span>
						</div>
					</div>
					
					 <div class="flw30">
						<div class="flw40">
							<label class="manageUserInputTitle">Trainer</label>
						</div>
						<div class="inputFieldInDialogWindow flw60">
							<form:select path="cnfSecTrngBy" id="mngCnfSecTrngBy" class="" tabindex="4"  title="Security Training Trainer">
							  	<form:option value="" label="--- Select ---" />
								
								<c:forEach var="trainerDetailsMap" items="${trainerList}" >
										<form:option value="${trainerDetailsMap.TRAINER_ID}" label="${trainerDetailsMap.TRAINER_NAME}" />
								</c:forEach>
									
							  
						   </form:select>
						</div>
					</div>
					
					<div class="flw30">
						<div class="flw60">
							<label class="manageUserInputTitle">Attestation Received</label>
						</div>
						<div class="inputFieldInDialogWindow flw40">
							<form:select path="cnfSecTrngAtstn" id="mngCnfSecTrngAtstn" class="" tabindex="4"  title="Training Attestation">
							  <option value=""></option>
							  <option value="N">No</option>
							  <option value="Y">Yes</option>
						   </form:select>
						</div>
					</div>
				</div>
				
				
				
				
				<div class="clearBoth pT10">
					<div class="flw50 ">
						<div class="flw50">
							<label class="manageUserInputTitle">Start date in bureau:</label>
						</div>
						<div class="flw50 effectiveDateTimePicker pT3">
							<input id="mngBureauStartDate" name="bureauStartDate"
								class="standardInputVerySmall readonly" readonly  data-format="MM/dd/yyyy" type="text"
								title="Start date in bureau" tabindex="-1"/>
								<span id="vpnApproveDateIcon" class="add-on">
									 <span class="icon-calendar" data-date-icon="icon-calendar" data-time-icon="icon-time" tabindex="14">
	      							</span>	
	      							</span>
						</div>
					</div>		
					
					<div class="flw50 ">
						<div class="flw50">
							<label class="manageUserInputTitle">Start date in current position:</label>
						</div>
						<div class="flw50 effectiveDateTimePicker pT3">
							<input id="mngPositionStartDate" name="positionStartDate"
								class="standardInputVerySmall readonly" readonly  data-format="MM/dd/yyyy" type="text"
								title="Start date in current position" tabindex="-1"/>
								<span id="teleCommuteDateIcon" class="add-on">
									 <span class="icon-calendar" data-date-icon="icon-calendar" data-time-icon="icon-time" tabindex="14">
	      							</span>	
	      							</span>
						</div>
					</div>	
				</div>
				<div class="clearBoth pT10">			
					
				</div>
				
				<div class="clearBoth pT10">
					<div  class="flw100">
						<div class=fieldHolder75>
							<label class="manageUserInputTitle">Will this individual have access to a desktop PC at a physical location within their office to access NYEHMS?</label>
						</div>
						<div class="fieldHolder25    ">
						   <select name="pcAccessAtLocation" id="mngPcAccessAtLocation" class="mL10" tabindex="4"  title="access to a desktop PC at a physical location">
							  <option value=""></option>
							  <option value="N">No</option>
							  <option value="Y">Yes</option>
						   </select>
						</div>
					</div>
				</div>	
				
				<div class="clearBoth pT10">
					<div  class="flw100">
						<div class=fieldHolder75>
							<label class="manageUserInputTitle">Will this individual have VPN/remote desktop/terminal server access?</label>
						</div>
						<div class="fieldHolder25    ">
						   <select name="remoteTerminalAccess" id="mngRemoteTerminalAccess" class="mL10" tabindex="4"  title="VPN/remote desktop/terminal server access?">
							  <option value=""></option>
							  <option value="N">No</option>
							  <option value="Y">Yes</option>
						   </select>
						</div>
					</div>
				</div>	
				
				<div class="clearBoth pT10">
					<div  class="flw100">
						<span class="manageUserInputTitle">What bureau is this individual associated with? </label>
						</span>
						<span >
						   <form:select path="bureauName" id="mngBureauName" class="mL10" tabindex="4" title="Bureau name">
					          <form:option value="" label="--- Select ---" />
							  <c:forEach items="${bureauList}" var="bureau">  
								   <form:option label="${bureau.BUREAU_DESC}" value="${bureau.BUREAU_CD}"  />  
							   </c:forEach> 
							</form:select>
						
						
						   
						</span>
					</div>
				</div>				
				
				<div class="clearBoth pT10">
					<div  class="flw100">
						<span class="manageUserInputTitle">County/Regional Office? </label>
						</span>
						<span >
						   <form:select path="regOffice" id="mngRegOffice" class="mL10" tabindex="4" title="County/Regional Office">
					          <form:option value="" label="--- Select ---" />
							  <c:forEach items="${regOfficeList}" var="regOffice">  
								   <form:option label="${regOffice.ADMIN_COUNTY_REGION_DESC}" value="${regOffice.ADMIN_COUNTY_REGION_CD}"  />  
							   </c:forEach> 
							</form:select>
						
						
						   
						</span>
					</div>
				</div>	
				
				
				
				<div class="clearBoth pT10">
					<div  class="flw100">
						<div class=fieldHolder25>
							<label class="manageUserInputTitle">Comments </label>
						</div>
						<div class="fieldHolder75    ">
						   <textarea id="userComments" cols="100" rows="4" title="User Comments"></textarea>
						</div>
					</div>
				</div>
				
				
				
				
				
				<div class="clearBoth pT10">
					<div id = "userFacilityGroupName" class="flw50">
						<div class="flw50  ">
							<label class="standardInputTitle">User Facility Name</label>
						</div>
						<div class="flw50">
					       <form:select path="userFacilityGroupCode" id="mngProUserFacilityGroupCode" name="mngProUserFacilityGroupCode" class="standardInputMedText readonly" tabindex="4"  title="User Facility Name">
					          <form:option value="" label="--- Select ---" />
							  <form:options items="${userFacilityNames}"/>
						   </form:select>		
						</div>
					</div>
				</div>
				
				
												
				<div class="clearBoth">&nbsp;&nbsp;</div>
				<div>&nbsp;&nbsp;</div>
				<div class="button-row marginBottom20">
					<a class="actionButtons a_demo_search a_demo_save" style="color: #FFFFFF;"  tabindex="16"
						onclick="saveManageProfResults();"> Save </a>
				</div>
			</div>
			
			<div id="gridLayoutsDiv">
				<!-- 
				
				 -->
				<div id="userCommentsProfile">
					<table id="usersCommentsTable"></table>
					<div id="userCommentsPagerDiv"></div>
				</div>
				
				<div class="manageRegionRoleTableDiv mT20">
					<table id="manageRegionRoleResultsTable"></table>
					<div id="manageRegionRoleResultsPagerDiv"></div>
				</div>
				
				
			</div>

			<div>&nbsp;&nbsp;</div>
		</div>
	</form:form>
</div>
<input type="hidden" id="manageUserId" value="${param.userId}">
<input type="hidden" id="manageLegacyUid" value="${param.legacyUid}">
<input type="hidden" id="managendDate" value="${param.endDate}">

<form:form id="addRegionRoleDialog" title="Adding New Region Role "
	modelAttribute="nyehmsWorkerRegionsViewVO">
	<div class="stdDetailsDiv" id="stdInfoDiv">
		<div id="addRegionRoleDetails">
			<div class="leftDiv">
				<div class="standardInputDiv">
					<div class="label110mrg">
						<label class="standardInputTitle"><spring:message
								code="label.region" /></label>
					</div>
					<div class="inputFieldInDialogWindow">
						<spring:message code="title.region" var="region_title" />
						<form:select path="regionDescription" id="manageRegionDesc"
							class="required standardInputMedText" tabindex="24"
							onchange="updateRegionRoles('permissionsInfoDiv', 'manageRegionDesc', 'manageRoleDesc');"
							title="${region_title}">
							<form:option value="" label="--- Select ---" multiple="true" />
							<form:options items="${regionTypes}" />
						</form:select>
					</div>
				</div>
			</div>
			<div class="rightDiv">
				<div class="standardInputDiv">
					<div class="label110mrg">
						<label class="standardInputTitle"><spring:message
								code="label.role" /></label>
					</div>
					<div class="inputFieldInDialogWindow">
						<spring:message code="title.role" var="role_title" />
						<form:select path="roleDescription" id="manageRoleDesc"
							class="required standardInputMedText" tabindex="25"
							onchange="getRegionRolePermissions('permissionsInfoDiv', 'manageRegionDesc', 'manageRoleDesc');"
							title="${role_title}">
							<form:option value="" label="--- Select ---" multiple="true" />
							<c:forEach items="${nyehmsRole}" var="nyehmsRoleObj">
								<form:option label="${nyehmsRoleObj.roleDescription}"
									value="${nyehmsRoleObj.roleCode}"
									isExpandable="${nyehmsRoleObj.isExpandable}" />
							</c:forEach>
						</form:select>
					</div>
				</div>
			</div>
			<br />
			<br />
			<br />
			<!-- load permissions dynamically on change of role -->
			<div id="permissionsInfoDiv"></div>
		</div>
		<div class="clearBoth"></div>
		<div class="button-row" style="margin-bottom: 12px; margin-top: 55px;">
			<a class="actionButtons a_demo_search a_demo_add" tabindex="26" style="color: #FFFFFF; margin-left: 5px;"
				onclick="addRegionRoles();"> ADD </a> 
				<a class="actionButtons a_demo_search a_demo_cancel" tabindex="27" style="color: #FFFFFF; margin-left: 5px;"
				onclick="closeStdDialog(this);"> Cancel </a>
		</div>
	</div>
</form:form>


<form:form id="updateRegionRoleDetailsDialog"
	title="Updating Region Role details "
	modelAttribute="nyehmsWorkerRegionsViewVO">
	<div class="stdDetailsDiv" id="stdInfoDiv">
		
		<div class="standardInputDiv">
			<div class="labelInDialogWindow">
				<label class="standardInputTitle"><spring:message
						code="label.region" /></label>
			</div>
			<div class="inputFieldInDialogWindow">
				<span id="regionDescription"></span>
			</div>
		</div>

		<div class="standardInputDiv">
			<div class="labelInDialogWindow">
				<label class="standardInputTitle"><spring:message
						code="label.role" /></label>
			</div>
			<div class="inputFieldInDialogWindow">
				<span id="roleDescription"></span>
			</div>
		</div>

		<div class="standardInputDiv displayNone" id="regionRoleRetiredDate">
			<div class="labelInDialogWindow">
				<label class="standardInmanageUserInputTitlemessage
						code="label.endDate" /></label>
			</div>
			<div class="inputFieldInDialogWindow effectiveDateTimePicker">
				<input id="wrEndDate" name="wrEndDate"
					class="standardInputMedText dPicker required readonly" readonly  value="" dateValidations="noPastDate" type="text"
					title="<spring:message code='title.endDate' />"  tabindex="-1"/>
					<span class="add-on">
								 <span class="icon-calendar" data-date-icon="icon-calendar" data-time-icon="icon-time" tabindex="28">
      							</span>	
      							</span>
			</div>
		</div>
		
		
		<div id="selectedPermissionsInfoDiv"></div>
		<div class="clearBoth"></div>
		<div class="button-row" style="margin-bottom: 20px; margin-top: 25px;">
			<a class="actionButtons a_demo_search a_demo_save" tabindex="29" style="color: #FFFFFF; margin-left: 5px;"
				onclick="updateRegionRoleDetails(this);"> Update </a> <a
				class="actionButtons a_demo_search a_demo_cancel" tabindex="30" style="color: #FFFFFF; margin-left: 5px;"
				onclick="closeStdDialog(this);"> Cancel </a>
				
				<a
				class="actionButtons a_demo_search a_demo_retire" id="retireRegionPermission" tabindex="31" style="color: #FFFFFF; margin-left: 5px;"
				onclick="retireRegionRole(this);">Retire</a>
		</div>
	</div>
</form:form>

<div id="delRegionRoleDialog" title="">
	<p id="InformationalMsg">
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>Are you sure you
		want to retire the Region Role?
	</p>
	<div>&nbsp;&nbsp;</div>
	<div class="button-row">
		<a id="retireRegRoleButton" class="a_demo_search"
			style="color: #FFFFFF; margin-left: 5px;" tabindex="32" 
			onclick="retireRegionRole();"> OK </a> <a id="saveUserInfoButton"
			class="a_demo_search" tabindex="33" style="color: #FFFFFF; margin-left: 5px;"
			onclick="saveUserInfo();"> OK </a>
	</div>
	<div>&nbsp;&nbsp;</div>
</div>
