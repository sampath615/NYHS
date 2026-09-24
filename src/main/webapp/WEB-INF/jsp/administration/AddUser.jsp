<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<form:form id="addUserDetailsDiv" title="Adding New USer Profile" modelAttribute="nyehmsWorkerRegionsViewVO">
	<div class="stdDetailsDiv" id="stdInfoDiv">
	       <div id="myErrorContainer"></div>
		   <div class="leftDiv">	
				<div class="standardInputDiv">
					<div class="label180mrg">
						<span class="standardInputTitle"><spring:message code="label.userID" /></span>
					</div>        
					<div class="floatLeft">
						<span id ="addUserLegacyId"></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="label180mrg">
						<span class="standardInputTitle"><spring:message code="label.firstname" /></span>
					</div>        
					<div class="floatLeft">
						<input id ="addUserFirstName"  name="addUserFirstName" class="required standardInputMedText fieldLevelHelp" tabindex="1" value="" type="text" maxLength="50" title="<spring:message code='title.firstName' />" />
					</div>	 
				</div>
				<div class="standardInputDiv">
					<div class="label180mrg">
						<span class="standardInputTitle"><spring:message code="label.middleName" /></span>
					</div>        
					<div class="floatLeft">
						<input id ="addUserMiddleName" name ="addUserMiddleName"  class="standardInputMedText fieldLevelHelp" tabindex="2" value="" type="text" maxLength="50"  title="<spring:message code='title.middleName' />" />
					</div>	 
				</div>
				<div class="standardInputDiv">
					<div class="label180mrg">
						<span class="standardInputTitle"><spring:message code="label.lastname" /></span>
					</div>        
					<div class="floatLeft">
						<input id ="addUserLastName" name ="addUserLastName"  class="required  standardInputMedText fieldLevelHelp" tabindex="3" value="" type="text" maxLength="50"  title="<spring:message code='title.lastName' />"/>
					</div>	 
				</div>
				<div class="standardInputDiv">
					<div class="label180mrg">
					     <span class="standardInputTitle"><spring:message code="label.region" /></span>
					</div>        
					<div class="floatLeft">
						<spring:message code="title.region" var="region_title"/>
				       <form:select path="regionDescription" id="addUserRegionDesc" name="addUserRegionDesc" class="required standardInputMedText " tabindex="4" onchange="updateRegionRoles('permissionsInfoDiv', 'addUserRegionDesc' , 'addUserRoleDesc' );" title="${region_title}">
				          <form:option value="" label="--- Select ---" multiple="true" />
						  <form:options items="${regionTypes}"/>
					   </form:select>
				   </div> 
				</div>
				<div class="standardInputDiv">
					<div class="label180mrg">
					     <span class="standardInputTitle">VPN Access</span>
					</div>        
					<div class="floatLeft">
				       <form:select path="vpnAccess" id="vpnAccess" name="vpnAccess" class="standardInputMedText " tabindex="4" onchange="enableDisableVpnApproveDate(this);" title="VPN Access">
						  <option value="N">No</option>
						  <option value="Y">Yes</option>
					   </form:select>
				   </div> 
				</div>		
				
				<div class="standardInputDiv">
				
					<div class="label180mrg">
					     <span class="standardInputTitle">C&S Trainer</span>
					</div>        
					<div class="floatLeft">
				       <form:select path="cnfSecTrngBy" id="cnfSecTrngBy" class="" tabindex="4"  title="Security Training Trainer">
							  	<form:option value="" label="--- Select ---" />
								
								<c:forEach var="trainerDetailsMap" items="${trainerList}" >
										<form:option value="${trainerDetailsMap.TRAINER_ID}" label="${trainerDetailsMap.TRAINER_NAME}" />
								</c:forEach>
									
							  
						   </form:select>
				   </div> 
				</div>
				
						
				<div class="standardInputDiv">
					<div class="label180mrg">
					     <span class="standardInputTitle">Telecommute</span>
					</div>        
					<div class="floatLeft">
				       <form:select path="telecommute" id="telecommute" name="telecommute" class="standardInputMedText " tabindex="4" onchange="enableDisableTelecommuteDate(this);" title="Telecommute">
						  <option value="N">No</option>
						  <option value="Y">Yes</option>
					   </form:select>
				   </div> 
				</div>	
				<div class="standardInputDiv">
				
					<div class="label180mrg">
					     <span class="standardInputTitle">Telework Trainer</span>
					</div>        
					<div class="floatLeft">
				       <form:select path="tlcmmtTrngBy" id="tlcmmtTrngBy" class="" tabindex="4"  title="TeleCommunicate Trainer">
							  <form:option value="" label="--- Select ---" />
								
								<c:forEach var="trainerDetailsMap" items="${trainerList}" >
										<form:option value="${trainerDetailsMap.TRAINER_ID}" label="${trainerDetailsMap.TRAINER_NAME}" />
								</c:forEach>
									
						   </form:select>
				   </div> 
				</div>			
				
				
				
		  </div>
		   <div class="rightDiv">	
				<div class="standardInputDiv">
					<div class="label180mrg">
						<span class="standardInputTitle"><spring:message code="label.email" /></span>
					</div>        
					<div class="floatLeft">
						<input id ="addUserEmail" name="addUserEmail" class="standardInputMedText required fieldLevelHelp"  tabindex="5" type="text" style="width: 190px;" maxLength="500"  title="<spring:message code='title.email' />"/>
					</div>	 
				</div>
				<div class="standardInputDiv">
					<div class="label180mrg">
						<span class="standardInputTitle"><spring:message code="label.startDate" /></span>
					</div>        
					<div class="floatLeft effectiveDateTimePicker">
						<input id ="addUserStartDate" name="addUserStartDate" class="standardInputMedText compareDate dPicker required readonly" tabindex="-1" value="${startDate}" type="text" style="width: 190px;" title="<spring:message code='title.startDate' />"/>
						<span class="add-on">
								 <span class="icon-calendar" data-date-icon="icon-calendar" data-time-icon="icon-time" tabindex="6">
      							</span>	
      							</span>
					</div>
				</div>
				<div class="standardInputDiv">
					
				</div>
				<!-- 
				<div class="standardInputDiv">
					<div class="label180mrg">
						<span class="standardInputTitle"><spring:message code="label.macAddress" /></span>
					</div>        
					<div class="floatLeft">
						<input id ="addUserMacAddress" name="addUserMacAddress" class="standardInputMedText  mask fieldLevelHelp" tabindex="8" value="" type="text" title="<spring:message code='title.macAddress' />"/>
						<a id="helpWithMacAdd" class="tooltip" title="Help to find Mac Address" onClick="$('#helpWithMacAddDialog').dialog('open');"></a>					
						
					</div>	 
				</div>
				 -->
				<div class="standardInputDiv">
					<div class="label180mrg">
					     <span class="standardInputTitle"><spring:message code="label.userType" /></span>
					</div>
					<div class="floatLeft">
						<spring:message code="title.userType" var="userType_title"/>
				       <form:select path="userTypeDesc" id="addUserTypeDesc" name="addUserTypeDesc" class="required standardInputMedText " tabindex="4" onchange="enableDisableRegion(this);" title="${userType_title}">
				          <form:option value="" label="--- Select ---" />
						  <form:options items="${userTypes}"/>
					   </form:select>
				   </div>        
				</div>
				<div id = "userFacilityGroupName" class="standardInputDiv">
					<div class="label180mrg">
					     <span class="standardInputTitle">User Facility Name</span>
					</div>
					<div class="floatLeft">
						<spring:message code="title.userType" var="userType_title"/>
				       <form:select path="userFacilityGroupDesc" id="addUserFacilityName" name="addUserFacilityName" class="standardInputMedText " tabindex="4"  title="User Facility Name">
				          <form:option value="" label="--- Select ---" />
						  <form:options items="${userFacilityNames}"/>
					   </form:select>
				   </div>        
				</div>
				<div class="standardInputDiv">
					<div class="label180mrg">
					     <span class="standardInputTitle"><spring:message code="label.role" /></span>
					</div>
					<div class="floatLeft">
						<spring:message code="title.role" var="role_title"/>
				       <form:select path="roleDescription" id="addUserRoleDesc" name="addUserRoleDesc" class="standardInputMedText required" tabindex="9" onchange="getRegionRolePermissions('permissionsInfoDiv', 'addUserRegionDesc' , 'addUserRoleDesc' );" title="${role_title}">
				          <form:option value="" label="--- Select ---" multiple="true"/>
						  <c:forEach items="${nyehmsRole}" var="nyehmsRoleObj">  
							   <form:option label="${nyehmsRoleObj.roleDescription}" value="${nyehmsRoleObj.roleCode}" isExpandable="${nyehmsRoleObj.isExpandable}" />  
						   </c:forEach> 
						</form:select>
				   </div>        
				</div>

				<div class="standardInputDiv">
					<div class="label180mrg">
						<span class="standardInputTitle">VPN Approve Date</span>
					</div>        
					<div class="floatLeft effectiveDateTimePicker">
						<input id ="vpnApproveDate" name="vpnApproveDate" class="standardInputMedText compareDate dPicker readonly" tabindex="-1" value="${vpnApproveDate}" type="text" style="width: 190px;" title="VPN Approve Date"/>
						<span id="vpnApproveDateIcon" class="add-on">
								 <span class="icon-calendar" data-date-icon="icon-calendar" data-time-icon="icon-time" tabindex="6">
      							</span>	
      							</span>
					</div>
				</div>
				
				<div class="standardInputDiv">
					<div class="label180mrg">
						<span class="standardInputTitle"><spring:message code="label.cnsDate" /></span>
					</div>        
					<div class="floatLeft effectiveDateTimePicker">
						<input id ="addUserCNSDate" name="addUserCNSDate" class="standardInputMedText compareDate dPicker readonly" tabindex="-1"  dateValidations="noFutureDate" type="text" style="width: 190px;"  title="<spring:message code='title.cnsDate' />"/>
						<span class="add-on">
								 <span class="icon-calendar" data-date-icon="icon-calendar" data-time-icon="icon-time" tabindex="7" >
      							</span>	
      							</span>
					</div>
				</div>
				
				<div class="standardInputDiv">
					<div class="label180mrg">
						<span class="standardInputTitle">Telecommute Trng Date</span>
					</div>        
					<div class="floatLeft effectiveDateTimePicker">
						<input id ="telecommuteTrainingDate" name="telecommuteTrainingDate" class="standardInputMedText compareDate dPicker readonly" tabindex="-1" value="${telecommuteTrainingDate}" type="text" style="width: 190px;" title="Telecommute Trng Date"/>
						<span id="teleCommuteDateIcon" class="add-on">
								 <span class="icon-calendar" data-date-icon="icon-calendar" data-time-icon="icon-time" tabindex="6">
      							</span>	
      							</span>
					</div>
				</div>
				
				
			
                <div class="standardInputDiv">&nbsp;</div>
 		  </div>
 		  
 		  
 		  <div class="clearBoth pT10">
 		  		<div class="flw50">
						<div class="flw50">
							<label class="standardInputTitle">Attestation Received</label>
						</div>
						<div class="inputFieldInDialogWindow flw50">
							<form:select path="cnfSecTrngAtstn" id="cnfSecTrngAtstn" class="" tabindex="4"  title="Training Attestation">
							  <option value=""></option>
							  <option value="N">No</option>
							  <option value="Y">Yes</option>
						   </form:select>
						</div>
					</div>
				
							
							
					 <div class="flw50">
						<div class="flw50">
							<label class="standardInputTitle">Telework attestation Received</label>
						</div>
						<div class="inputFieldInDialogWindow flw50">
							<form:select path="tlcmmtTrngAtstn" id="tlcmmtTrngAtstn" class="" tabindex="4"  title="Telecommute training Attestation">
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
							<label class="standardInputTitle">Start date in bureau:</label>
						</div>
						<div class="flw50 effectiveDateTimePicker pT3">
							<input id="bureauStartDate" name="bureauStartDate"
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
							<label class="standardInputTitle">Start date in current position:</label>
						</div>
						<div class="flw50 effectiveDateTimePicker pT3">
							<input id="positionStartDate" name="positionStartDate"
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
					<div  class="flw100">
						<div class=fieldHolder75>
							<label class="standardInputTitle">Will this individual have access to a desktop PC at a physical location within their office to access NYEHMS?</label>
						</div>
						<div class="fieldHolder25    ">
						   <select name="pcAccessAtLocation" id="pcAccessAtLocation" class="mL10" tabindex="4"  title="access to a desktop PC at a physical location">
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
							<label class="standardInputTitle">Will this individual have VPN/remote desktop/terminal server access?</label>
						</div>
						<div class="fieldHolder25    ">
						   <select name="remoteTerminalAccess" id="remoteTerminalAccess" class="mL10" tabindex="4"  title="VPN/remote desktop/terminal server access?">
							  <option value=""></option>
							  <option value="N">No</option>
							  <option value="Y">Yes</option>
						   </select>
						</div>
					</div>
				</div>	
				
				<div class="clearBoth pT10">
					<div  class="flw100">
						<span class="standardInputTitle">What bureau is this individual associated with? </label>
						</span>
						<span>
						   <form:select path="bureauName" id="bureauName" class="mL10" tabindex="4" onchange="enableDisableTelecommuteDate(this);" title="Bureau name">
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
						   <form:select path="regOffice" id="regOffice" class="mL10" tabindex="4" title="County/Regional Office">
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
						<div class="label180mrg">
							<span class="standardInputTitle">Comments</span>
						</div>        
						<div class="floatLeft ">
							<textarea id="userComments" cols="100" rows="4" title="User Comments"></textarea>
							
						</div>
					</div>
				</div>
		  
		  <!-- load permissions dynamically on change of role -->
			<div id="permissionsInfoDiv" class="marginLeft30 pT10">
			</div>
		
 		  <div id="error"></div>
		  <div style="clear: both;"><br />	</div>
		  <div>&nbsp;&nbsp;</div>
		  <div class="button-row">
			     <a class="a_demo_search" style="color: #FFFFFF; margin-left: 5px;" tabindex="10" onclick="submitAddUser();"> Submit </a>
			     <!-- <a class="a_demo_search" style="color: #FFFFFF; margin-left: 5px;" onclick="cancelAddUser();"> Cancel </a> -->
		  </div>		
		  <div>&nbsp;&nbsp;</div>
		  
		  <!-- 
		  <div id="helpWithMacAddDialog">
			<div style="margin-top:10px">
				The MAC address is a 6-digit hexadecimal number, which is normally written as a twelve character alphanumeric string with a colon (:) or other separating character after every second character. 
			</div>
			<div class="pB10">
				Example of a MAC address: 00:00:00:a1:2b:cc. 
			</div>
			<div class="pB10">
				In most cases it is not labeled as "MAC address" it may be known as the Physical Address.
			</div>
			<div>				
				<ol>
					<li class="pB5">Click on the Start button. </li>
					<li class="pB5"> In the Run text box, type cmd. This will open the command window.</li>
					<li class="pB5"> At the command prompt, type ipconfig /all.</li>
					<li class="pB5"> Copy down or type the "Physical Address" (aka the MAC address) from the "Ethernet adapter Wireless Network Connection" section and send to requestor</li>
					<li class="pB5"> Close the command prompt window.</li>
				</ol>			
			</div>
		</div>
		 -->
		 
	</div>
</form:form>