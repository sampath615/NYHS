<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<form:form id="newSubFacForm" name="newSubFacForm" modelAttribute="subFacilityVO">
	<div id="grandSearchForm" >
		<div class="grandDetailsDiv" id="grandInfoDiv">
				<div class="leftdiv">	
					<div class="standardInputDiv marginBottom10">
						<div class="label150mrg">
							<label class="standardInputTitle"   style="font-size: 12px"><spring:message code="label.masterFacilityName" /></label><span class="requiredInd" title="Required Field">*</span>
						</div>
						<div class="inputFieldInDialogWindow">
							<input id="newMasterFacilityName" tabindex="21" name="masterFacilityName" class="standardInputMedText required fieldLevelHelp" value="" type="text" maxLength="100" title="<spring:message code='title.facilityName' />">
						</div>
					</div>
				</div>
			</div>
	</div>
	<div id="newSubFacDiv" title="Add New Sub facility And Standardize">
		<div class="stdDetailsDiv" id="stdInfoDiv">
	        <div class="leftDiv">
				<div class="standardInputDiv">
					<div class="label150mrg">
						<label class="standardInputTitle"><spring:message code="label.facilityName" /></label><span class="requiredInd" title="Required Field">*</span>
					</div>
					<div class="inputFieldInDialogWindow">
						<input id="addFacName" name="addFacName" tabindex="22" class="required standardInputMedText fieldLevelHelp" value="" type="text" maxLength="80" title="<spring:message code='title.facilityName' />">
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="label150mrg">
						<label class="standardInputTitle"><spring:message code="label.department" /></label>
					</div>
					<div class="inputFieldInDialogWindow">
						<input id="addFacDept" name="addFacDept"  tabindex="24" class="standardInputMedText fieldLevelHelp" value="" type="text" maxLength="100" title="<spring:message code='title.department' />">
					</div>
				</div>
			</div>
			<div class="rightDiv">
				<div class="standardInputDiv">
					<div class="label150mrg">
						<label class="standardInputTitle">NPI</label>
					</div>
					<div class="inputFieldInDialogWindow">
						<input id="addFacNpi" name="addFacNpi"  tabindex="27" class="standardInputMedText fieldLevelHelp" value="" type="text" title="NPI">
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="label150mrg">
						<label class="standardInputTitle"><spring:message code="label.phoneNumber" /></label><span class="requiredInd" title="Required Field">*</span>
					</div>
					<div class="inputFieldInDialogWindow">
						<input id="addFacPhone" name="addFacPhone" tabindex="25"  class="standardInputMedText required mask phoneMask phoneField" type="text" maxlength="13"  title="<spring:message code='title.phoneNumber' />">
						<input id="addFacPhonePlus4" name="addFacPhonePlus4" tabindex="26"  class="standardInputMedText extensionField" type="text" maxlength="4"  title="<spring:message code='title.phoneNumberExtension' />">
					</div>
				</div>
				<div class="standardInputDiv">
					
				</div>
			</div>
		
			<div id="FacilityAddresswidgetFields" class="addMarginTop widgetTargetFields">
				<form:form modelAttribute="addressFieldVO">
					<div class="leftDiv">
						<div class="standardInputDiv">
							<div class="label150mrg">
								<label for="addMdlDlgStreet" class="standardInputTitle"><spring:message code="label.streetAddress" /><span class="requiredInd" title="Required Field">*</span></label>
							</div>
							<div class="inputFieldInDialogWindow">
								<input type="text" size="35" maxlength="100" tabindex="-1" name="addMdlDlgStreet" id="addMdlDlgStreetTarget" class="required standardInputMedText newAddress fieldLevelHelp widgetAddressFieldsReadonly" title="<spring:message code='title.streetAddress' />"/>
							</div>
						</div>
						<div class="standardInputDiv">
							<div class="label150mrg">
								<label for="addMdlDlgCity" class="standardInputTitle"><spring:message code="label.city" /><span class="requiredInd" title="Required Field">*</span></label>
							</div>
							<div class="inputFieldInDialogWindow">
								<input type="text"  id="addMdlDlgCityTarget" name="addMdlDlgCity" tabindex="-1" class="required standardInputMedText fieldLevelHelp newCity widgetAddressFieldsReadonly"  title="<spring:message code='title.city' />"></input>
							</div>
						</div>
						<div class="standardInputDiv">
							<div class="label150mrg">
								<label for="state" class="standardInputTitle"><spring:message code="label.state" /><span class="requiredInd" title="Required Field">*</span></label>
							</div>
							<div class="inputFieldInDialogWindow">
								<spring:message code="title.state" var="state_title"/>
								<form:select path="state" id="addMdlDlgStateTarget" tabindex="29" cssClass="required standardInputMedText newState widgetAddressFieldsReadonly" cssStyle="WIDTH:50px"  title="${state_title}">
									<form:option value=""></form:option>
									<form:options items="${sessionScope.stateMap}"/>
								</form:select>
							</div>
						</div>
					</div>
					<div class="rightDiv">
					<div class="standardInputDiv">	
						<a href="javascript:openAddressWidget('FacilityAddresswidgetFields','required')" class="changeAddressLinks">Add Address </a>
						<a href="javascript:clearDialogFields('FacilityAddresswidgetFields')" class="changeAddressLinks">Reset Address </a>
<!-- 						<span href="#" onclick="clearAddress('addMdlDlg')" class="resetAddressFieldsLink">Reset Address Fields</span></div> -->
					</div>
						<div class="standardInputDiv">
							<div class="label150mrg">
								<label for="county" class="standardInputTitle"><spring:message code="label.county" /><span class="requiredInd" title="Required Field">*</span></label>
							</div>
							<div class="inputFieldInDialogWindow">
								<spring:message code="title.county" var="county_title"/>
								<form:select path="county" id="addMdlDlgCountyTarget" tabindex="30" cssClass="required standardInputMedText newCounty widgetAddressFieldsReadonly" cssStyle="WIDTH:180px"  title="${county_title}">
										<form:option value=""></form:option>
										<form:options items="${sessionScope.countyMap}"/>
									</form:select>
							</div>
						</div>
						<div class="standardInputDiv">
							<div class="label150mrg">
								<label for="addMdlDlgZip" class="standardInputTitle"><spring:message code="label.zip" /><span class="requiredInd" title="Required Field">*</span></label>
							</div>
							<div class="inputFieldInDialogWindow">
								<input type="text"  id="addMdlDlgZipTarget" name="addMdlDlgZip" tabindex="31" class="newZip required standardInputMedText fieldLevelHelp widgetAddressFieldsReadonly"  title="<spring:message code='title.zip' />"></input>
							</div>
						</div>
					</div>
				</form:form>
			</div>
	
			<div class="clearBoth">
				<br />
			</div>
			<div class="button-row">
				<a class="actionButtons a_demo_search a_demo_save" style ="color:#FFFFFF; margin-left: 5px;" onclick="submitNewStdResults();"> Submit </a>
				<a class="actionButtons a_demo_search a_demo_cancel" style ="color:#FFFFFF; margin-left: 5px;" onclick="closeStdDialog(this);"> Cancel </a>
				<a class="actionButtons a_demo_search a_demo_reset" style="color:#FFFFFF; margin-left: 5px;" onclick="clearDialogFields('newSubFacForm');"> Reset </a> 
			</div>
			<div>&nbsp;&nbsp;</div>
		</div>
	</div>
</form:form>