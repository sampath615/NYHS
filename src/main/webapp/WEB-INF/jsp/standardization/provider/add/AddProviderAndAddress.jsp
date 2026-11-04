<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<form id="newProvAddressForm">
	<div id="newProvAddress" class="stdDetailsDiv">
		
		<div class="leftDiv">
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
			<div class="standardInputDiv" provCrit="providerDetails">
				<div class="addAddressFieldLabel">
					<label for="addProvSpeciality"><spring:message code="label.speciality" /></label>
				</div>
				<div class="addAddressInputField">
					<input id="addProvSpeciality" name="addProvSpeciality" tabindex="35"   class="standardInputMedText mR50 fieldLevelHelp fieldLevelHelp"  style="width:268px" value="" type="text" maxlength="100" title="<spring:message code='title.speciality' />">
				</div>
			</div>			
		</div>
		<div class="rightDiv">
			<div class="standardInputDiv" provCrit="providerDetails">
				<div class="addAddressFieldLabel" style="width:120px">
					<label for="addProvMName"><spring:message code="label.middleName" /></label>
				</div>
				<div class="addAddressInputField">
					<input id="addProvMName" name="addProvMName" tabindex="22"   class="standardInputMedText mR50 fieldLevelHelp fieldLevelHelp"  value="" type="text" maxlength="100" title="<spring:message code='title.middleName' />">
				</div>
			</div>
			<div class="standardInputDiv NPIField" provCrit="providerDetails">
				<div class="addAddressFieldLabel" style="width:120px">
					<label for="addProvNPI">NPI</label>
				</div>
				<div class="addAddressInputField">
					<input id="addProvNPI" name="addProvNPI"  tabindex="25"  class="standardInputMedText mR50 fieldLevelHelp" value="" type="text" title="NPI">
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
		<div class="clearBoth"></div>
		<div id="ProviderAddresswidgetFields" class="widgetTargetFields">
			<form:form modelAttribute="addressFieldVO">
				<div class="leftDiv">
					<div class="standardInputDiv">
						<div class="addAddressFieldLabel">
							<label for="addMdlDlgStreet"><spring:message code="label.streetAddress" /><span class="requiredInd" title="Required Field">*</span></label>
						</div>
						<div class="addAddressInputField">
							<input type="text" size="35" maxlength="80" tabindex="-1"  name="addMdlDlgStreet" id="addMdlDlgStreetTarget" class="required standardInputMedText newAddress fieldLevelHelp widgetAddressFieldsReadonly" title="<spring:message code='title.streetAddress' />"/>
						</div>
					</div>
					<div class="standardInputDiv">
						<div class="addAddressFieldLabel">
							<label for="addMdlDlgCity"><spring:message code="label.city" /><span class="requiredInd" title="Required Field">*</span></label>
						</div>
						<div class="addAddressInputField">
							<input type="text"   id="addMdlDlgCityTarget" name="addMdlDlgCity" tabindex="-1" class="required standardInputMedText newCity widgetAddressFieldsReadonly"  title="<spring:message code='title.city' />"></input>
						</div>
					</div>
					<div class="standardInputDiv">
						<div class="addAddressFieldLabel">
							<label for="state"><spring:message code="label.state" /><span class="requiredInd" title="Required Field">*</span></label>
						</div>
						<div class="addAddressInputField">
							<spring:message code="title.state" var="state_title"/>
							<form:select path="state" id="addMdlDlgStateTarget" tabindex="28" cssClass="required standardInputMedText fieldLevelHelp newState widgetAddressFieldsReadonly" cssStyle="WIDTH:50px" title="${state_title}">
								<form:option value=""></form:option>
								<form:options items="${sessionScope.stateMap}"/>
							</form:select>
						</div>
					</div>
				</div>
				
				
				<div class="rightDiv">
					<div class="standardInputDiv">	
						<a href="javascript:openAddressWidget('ProviderAddresswidgetFields', 'required')" class="changeAddressLinks">Add / Edit Address </a>
						<a href="javascript:clearDialogFields('ProviderAddresswidgetFields')" class="changeAddressLinks">Reset Address </a>
<!-- 						<span href="#" onclick="clearAddress('addMdlDlg')" class="resetAddressFieldsLink">Reset Address Fields</span></div> -->
					</div>
					<div class="standardInputDiv">
						<div class="addAddressFieldLabel">
							<label for="county"><spring:message code="label.county" /><span class="requiredInd" title="Required Field">*</span></label>
						</div>
						<div class="addAddressInputField">
							<spring:message code="title.county" var="county_title"/>
							<form:select path="county" id="addMdlDlgCountyTarget" tabindex="29" cssClass="required standardInputMedText newCounty widgetAddressFieldsReadonly" cssStyle="WIDTH:180px" title="${county_title}">
									<form:option value=""></form:option>
									<form:options items="${sessionScope.countyMap}"/>
								</form:select>
						</div>
					</div>
					<div class="standardInputDiv">
						<div class="addAddressFieldLabel">
							<label for="addMdlDlgZip"><spring:message code="label.zip" /><span class="requiredInd" title="Required Field">*</span></label>
						</div>
						<div class="addAddressInputField">
							<input type="text" id="addMdlDlgZipTarget" name="addMdlDlgZip"  tabindex="30" class="newZip required standardInputMedText widgetAddressFieldsReadonly" title="<spring:message code='title.zip' />"></input>
						</div>
					</div>
					
				</div>
			</form:form>
		</div>
		<div style="text-align: center; margin-bottom:12px; clear: both;"><br/>
			<a class="actionButtons a_demo_search a_demo_save" style="color:#FFFFFF; margin-left: 5px;" onclick="submitAddProvAddrs();"> Submit </a>
			<a class="actionButtons a_demo_search a_demo_cancel" style="color:#FFFFFF; margin-left: 5px;" onclick="closeStdDialog(this);"> Cancel </a>    
			<a class="actionButtons a_demo_search a_demo_reset" style="color:#FFFFFF; margin-left: 5px;" onclick="clearDialogFields('newProvAddressForm');"> Reset </a> 
	    </div>
	</div>
</form>	

