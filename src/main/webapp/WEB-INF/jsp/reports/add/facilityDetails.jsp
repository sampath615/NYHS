<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div id="newProvAddressForm">
	<div id="newProvAddress">
		
		<div class="leftDiv">
			<div class="standardInputDiv" >
				<div class="addAddressFieldLabel">
					<label for="addPfi"><spring:message code="label.PFI" /><span class="requiredInd" title="Required Field">*</span></label>
				</div>
				<div class="addAddressInputField">
					<input id="addPfi" name="addPfi"  class="required standardInputMedText mR50" value="" type="text" tabindex="2" title="<spring:message code='title.PFI' />">
				</div>
			</div>
			<div class="standardInputDiv" >
				<div class="addAddressFieldLabel">
					<label for="addPrimaryContactFullName"><spring:message code="label.title.primaryContact" /><span class="requiredInd" title="Required Field">*</span></label>
				</div>
				<div class="addAddressInputField">
					<input id="addPrimaryContactFullName" name="addPrimaryContactFullName"  class="required standardInputMedText mR50" value="" type="text" tabindex="2" title="<spring:message code='title.title.primaryContact' />">
				</div>
			</div>
			<div class="standardInputDiv" >
				<div class="addAddressFieldLabel">
					<label for="addDirector"><spring:message code="label.Director" /><span class="requiredInd" title="Required Field">*</span></label>
				</div>
				<div class="addAddressInputField">
					<input id="addDirector" name="addDirector"  class="required standardInputMedText mR50" value="" type="text" tabindex="2" title="<spring:message code='title.Director' />">
				</div>
			</div>
		</div>
		<div class="rightDiv">
			<div class="standardInputDiv">
				<div class="addAddressFieldLabel">
					<label for="addLabName"><spring:message code="label.labName" /></label>
				</div>
				<div class="addAddressInputField">
					<input id="addLabName" name="addLabName" class="required standardInputMedText mR50" value="" type="text" tabindex="3" title="<spring:message code='title.labName' />">
				</div>
			</div>
			<div class="standardInputDiv" >
				<div class="addAddressFieldLabel">
					<label for="addItContactName"><spring:message code="label.itContact" /><span class="requiredInd" title="Required Field">*</span></label>
				</div>
				<div class="addAddressInputField">
					<input id="addItContactName" name="addItContactName"  class="required standardInputMedText mR50" value="" type="text" tabindex="2" title="<spring:message code='title.itContact' />">
				</div>
			</div>
		</div>
		<div class="clearBoth"></div>
		<div class="widgetTargetFields" id="FacilityAddresswidgetFields">
			<form:form modelAttribute="addressFieldVO" >
				<div class="leftDiv">
					<div class="standardInputDiv">
						<div class="addAddressFieldLabel">
							<label for="addMdlDlgStreet"><spring:message code="label.streetAddress" /><span class="requiredInd" title="Required Field">*</span></label>
						</div>
						<div class="addAddressInputField">
							<input type="text" size="35" maxlength="80" tabindex="-1"  name="addMdlDlgStreet" id="addMdlDlgStreetTarget" class="required standardInputMedText newAddress fieldLevelHelp  widgetAddressFieldsReadonly" title="<spring:message code='title.streetAddress' />"/>
						</div>
					</div>
					<div class="standardInputDiv">
						<div class="addAddressFieldLabel">
							<label for="addMdlDlgCity"><spring:message code="label.city" /><span class="requiredInd" title="Required Field">*</span></label>
						</div>
						<div class="addAddressInputField">
							<input type="text" id="addMdlDlgCityTarget" name="addMdlDlgCity" tabindex="-1" class="required standardInputMedText newCity widgetAddressFieldsReadonly" title="<spring:message code='title.city' />"></input>
						</div>
					</div>
					<div class="standardInputDiv">
						<div class="addAddressFieldLabel">
							<label for="state"><spring:message code="label.state" /><span class="requiredInd" title="Required Field">*</span></label>
						</div>
						<div class="addAddressInputField">
							<spring:message code="title.state" var="state_title"/>
							<form:select path="state" id="addMdlDlgStateTarget" tabindex="28" cssClass="required standardInputMedText fieldLevelHelp newState widgetAddressFieldsReadonly " cssStyle="WIDTH:50px" title="${state_title}">
								<form:option value=""></form:option>
								<form:options items="${sessionScope.stateMap}"/>
							</form:select>
						</div>
					</div>
				</div>
				
				
				<div class="rightDiv">
					<div class="standardInputDiv">	
						<a href="javascript:openAddressWidget('FacilityAddresswidgetFields', 'required')" class="changeAddressLinks">Add Address </a>
						<a href="javascript:clearDialogFields('FacilityAddresswidgetFields')" class="changeAddressLinks">Reset Address </a>
<!-- 						<span href="#" onclick="clearAddress('addMdlDlg')" class="resetAddressFieldsLink">Reset Address Fields</span></div> -->
					</div>
					<div class="standardInputDiv">
						<div class="addAddressFieldLabel">
							<label for="county"><spring:message code="label.county" /><span class="requiredInd" title="Required Field">*</span></label>
						</div>
						<div class="addAddressInputField">
							<spring:message code="title.county" var="county_title"/>
							<form:select path="county" id="addMdlDlgCountyTarget" tabindex="29" cssClass="required standardInputMedText newCounty widgetAddressFieldsReadonly " cssStyle="WIDTH:180px"  title="${county_title}">
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
			<a class="actionButtons a_demo_search a_demo_reset" style="color:#FFFFFF; margin-left: 5px;" onclick="clearDialogFields('newProvAddress');"> Reset </a> 
	    </div>
	</div>
</div>	


