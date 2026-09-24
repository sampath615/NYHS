<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<form id="newFacilityProvAddressForm">
	<div id="newFacilityProvAddress" class="stdDetailsDiv">
		
		<div class="leftDiv providerDetails">
			<div class="standardInputDiv" provCrit="providerDetails">
				<div class="addAddressFieldLabel">
					<label for="addProvFName"><spring:message code="label.firstname" /><span class="requiredInd" title="Required Field">*</span></label>
				</div>
				<div class="addAddressInputField">
					<input id="addProvFName" name="addProvFName"  tabindex="21" class="standardInputMedText mR50 required fieldLevelHelp" value="" type="text" maxlength="100" title="<spring:message code='title.firstName' />">
				</div>
			</div>
			<div class="standardInputDiv" provCrit="providerDetails">
				<div class="addAddressFieldLabel">
					<label for="addProvLName"><spring:message code="label.lastname" /><span class="requiredInd" title="Required Field">*</span></label>
				</div>
				<div class="addAddressInputField">
					<input id="addProvLName" name="addProvLName"  tabindex="23"  class="standardInputMedText mR50 required fieldLevelHelp" value="" type="text" maxlength="100" title="<spring:message code='title.lastName' />">
				</div>
			</div>
			<div class="standardInputDiv NPIField" provCrit="providerDetails">
				<div class="addAddressFieldLabel">
					<label for="addProvNPI">NPI</label>
				</div>
				<div class="addAddressInputField">
					<input id="addProvNPI" name="addProvNPI"  tabindex="25"  class="standardInputMedText mR50 fieldLevelHelp" value="" type="text" title="NPI">
				</div>
			</div>
			
		</div>
		<div class="rightDiv providerDetails">
			<div class="standardInputDiv" provCrit="providerDetails">
				<div class="addAddressFieldLabel" style="width:120px">
					<label for="addProvMName"><spring:message code="label.middleName" /></label>
				</div>
				<div class="addAddressInputField">
					<input id="addProvMName" name="addProvMName" tabindex="22"   class="standardInputMedText mR50 fieldLevelHelp fieldLevelHelp"  value="" type="text" maxlength="100" title="<spring:message code='title.middleName' />">
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="addAddressFieldLabel" style="width:120px">
					<label for="addProvPhone"><spring:message code="label.phoneNumber" /><span class="requiredInd" title="Required Field">*</span></label>
				</div>
				<div class="addAddressInputField">
					<input id="addProvPhone" name="addProvPhone" tabindex="24"  class="standardInputMedText required mask phoneMask phoneField" type="text" maxlength="13"  title="<spring:message code='title.phoneNumber' />">
					<input id="addProvPhoneExtension" name="addProvPhoneExtension" tabindex="25"  class="standardInputMedText mR50 extensionField" type="text" maxlength="10"  title="<spring:message code='title.phoneNumberExtension' />">
				</div>
			</div>				
		</div>
		
		<div class="leftDiv facilityDetails">
				<div class="standardInputDiv">
					<div class="addAddressFieldLabel">
						<label for="addFacName"><spring:message code="label.facilityName" /></label><span class="requiredInd" title="Required Field">*</span>
					</div>
					<div class="addAddressInputField">
						<input id="addFacName" name="addFacName" tabindex="22" class="required standardInputMedText fieldLevelHelp" value="" type="text" maxLength="80" title="<spring:message code='title.facilityName' />">
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="addAddressFieldLabel">
						<label for="addFacDept"><spring:message code="label.department" /></label>
					</div>
					<div class="addAddressInputField">
						<input id="addFacDept" name="addFacDept"  tabindex="24" class="standardInputMedText fieldLevelHelp" value="" type="text" maxLength="100" title="<spring:message code='title.department' />">
					</div>
				</div>
			</div>
			<div class="rightDiv facilityDetails">
				<div class="standardInputDiv">
					<div class="addAddressFieldLabel" style="width:120px">
						<label for="addFacNpi">NPI</label>
					</div>
					<div class="inputFieldInDialogWindow">
						<input id="addFacNpi" name="addFacNpi"  tabindex="27" class="standardInputMedText fieldLevelHelp" value="" type="text" title="NPI">
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="addAddressFieldLabel" style="width:120px">
						<label for="addFacPhone"><spring:message code="label.phoneNumber" /></label><span class="requiredInd" title="Required Field">*</span>
					</div>
					<div class="inputFieldInDialogWindow">
						<input id="addFacPhone" name="addFacPhone" tabindex="25"  class="standardInputMedText required mask phoneMask phoneField" type="text" maxlength="13"  title="<spring:message code='title.phoneNumber' />">
						<input id="addFacPhonePlus4" name="addFacPhonePlus4" tabindex="26"  class="standardInputMedText extensionField" type="text" maxlength="4"  title="<spring:message code='title.phoneNumberExtension' />">
					</div>
				</div>
				<div class="standardInputDiv">
					
				</div>
			</div>
			
		<div class="clearBoth"></div>
			<div class="widgetTargetFields" id="assignmentAddressFields">
			<form:form modelAttribute="addressFieldVO" >
				<div class="leftDiv">
					<div class="standardInputDiv">
						<div class="addAddressFieldLabel">
							<label for="assignmentStreetAddress"><spring:message code="label.streetAddress" /><span class="requiredInd" title="Required Field">*</span></label>
						</div>
						<div class="addAddressInputField">
							<input type="text" size="35" maxlength="80" tabindex="26"  name="assignmentStreetAddress" id="assignmentStreetAddress" class="required standardInputMedText newAddress fieldLevelHelp widgetAddressFieldsReadonly " title="<spring:message code='title.streetAddress' />" />
						</div>
					</div>
					<div class="standardInputDiv">
						<div class="addAddressFieldLabel">
							<label for="assignmentCity"><spring:message code="label.city" /><span class="requiredInd" title="Required Field">*</span></label>
						</div>
						<div class="addAddressInputField">
							<input type="text" id="assignmentCity" name="assignmentCity" tabindex="27" class="required standardInputMedText newCity widgetAddressFieldsReadonly " title="<spring:message code='title.city' />" ></input>
						</div>
					</div>
					<div class="standardInputDiv">
						<div class="addAddressFieldLabel">
							<label for="state"><spring:message code="label.state" /><span class="requiredInd" title="Required Field">*</span></label>
						</div>
						<div class="addAddressInputField">
							<spring:message code="title.state" var="state_title"/>
							<form:select path="state" id="assignmentState" tabindex="28" cssClass="required standardInputMedText fieldLevelHelp newState widgetAddressFieldsReadonly " cssStyle="WIDTH:50px" title="${state_title}" >
								<form:option value=""></form:option>
								<form:options items="${sessionScope.stateMap}"/>
							</form:select>
						</div>
					</div>
				</div>
				
				
				<div class="rightDiv">
					<div class="standardInputDiv">	
						<a href="javascript:openAddressWidget('assignmentAddressFields','required')" class="changeAddressLinks">Add Address </a>
						<a href="javascript:clearDialogFields('assignmentAddressFields')" class="changeAddressLinks">Reset Address </a>
<!-- 						<span href="#" onclick="clearAddress('addMdlDlg')" class="resetAddressFieldsLink">Reset Address Fields</span></div> -->
					</div>
					<div class="standardInputDiv">
						<div class="addAddressFieldLabel">
							<label for="county"><spring:message code="label.county" /><span class="requiredInd" title="Required Field">*</span></label>
						</div>
						<div class="addAddressInputField">
							<spring:message code="title.county" var="county_title"/>
							<form:select path="county" id="assignmentCounty" tabindex="29" cssClass="required standardInputMedText newCounty widgetAddressFieldsReadonly "  cssStyle="WIDTH:180px" title="${county_title}" disabled="disabled">
									<form:option value=""></form:option>
									<form:options items="${sessionScope.countyMap}"/>
								</form:select>
						</div>
					</div>
					<div class="standardInputDiv">
						<div class="addAddressFieldLabel">
							<label for="assignmentZipCode"><spring:message code="label.zip" /><span class="requiredInd" title="Required Field">*</span></label>
						</div>
						<div class="addAddressInputField">
							<input type="text" id="assignmentZipCode" name="assignmentZipCode"  tabindex="30" class="newZip required standardInputMedText widgetAddressFieldsReadonly "  title="<spring:message code='title.zip' />"></input>
						</div>
					</div>
					
				
				</div>
			</form:form>
			</div>
		<div style="text-align: center; margin-bottom:12px; clear: both;"><br/>
			<a class="actionButtons a_demo_search a_demo_save" style="color:#FFFFFF; margin-left: 5px;" onclick="submitRawDetails();"> Submit </a>
			<a class="actionButtons a_demo_search a_demo_cancel" style="color:#FFFFFF; margin-left: 5px;" onclick="closeStdDialog(this);"> Cancel </a>    
			<a class="actionButtons a_demo_search a_demo_reset" style="color:#FFFFFF; margin-left: 5px;" onclick="clearDialogFields('newFacilityProvAddress');"> Reset </a> 
	    </div>
	</div>
</form>	

