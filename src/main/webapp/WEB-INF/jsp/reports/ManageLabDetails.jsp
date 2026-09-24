<div id="labFacilityDetailsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
<div class="container paddingBottom5" style="width:95%; height:25px" id="lastImportedEclrsDateSection">			
	<div style="float:right">
		<span> Last Submitted date to ECLRS: </span> <span id="lastImportedEclrsDate"></span> 
	</div>						
</div>
<div style="clear:both"></div>
<div id="savelabFacilityDetails">


</div>

<div id="jqGridTableSection" class="jqGridTableSection">
	<div id="labContactTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	
	<div id="addContactDetails" >
	 	 	<table id="editNewContact"></table>
	 	 	<div id="editNewContactPager"></div>
	</div>
	  
	
	<div id="labPermitTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	<div id="labPermitResult"  >
		<table id="labPermitsTable"></table>
		<table id="labPermitsPager"></table>
	</div>
	
	<div id="labLoincDetailsTransactionMessage" class="transactionMessage"><span class="message"></span></div>			
	<div id="labLoincCodesResult"  >
		<table id="labLoincCodesTable"></table>
		<table id="labLoincCodesPager"></table>
	</div>
	
	<!-- 			
	<div id="labLocalCodeTransactionMessage" class="transactionMessage"><span class="message"></span></div>			
	<div id="labLocalCodesResult"  >
		<table id="labLocalCodesTable"></table>
		<table id="labLocalCodesPager"></table>
	</div>
	 -->
	
	<!--
	<div id="labProficiencyTestTransactionMessage" class="transactionMessage"><span class="message"></span></div>			
	<div id="labProficiencyTestResult"  >
		<table id="labProficiencyTestTable"></table>
		<table id="labProficiencyTestPager"></table>
	</div>
	-->
	
	<div id="labSubmissionTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	<div id="addSubmission"  >
		<table id="addSubmissionTable"></table>
		<div id="editaddSubmissionTablePager"></div>
	</div>
		 
	<div id="labCommunicationHistoryMessage" class="transactionMessage"><span class="message"></span></div>
	<div id="labCommunicationHistory" class="jqGridTableSection">
			<table id="labCommunicationHistoryTable"></table>
			<table id="labCommunicationHistoryPager"></table>
	</div>
</div>
<div id="addLabDetailsDialog" >
	<div id="stdInfoDiv">
		<div id="addingLabDetails" >
				
		</div>
	</div>			
</div>

<div id="addLabFacilityDetailsDialog" >
	<div id="cliaAndAddedPfiDetails">
		<div id="addLabDetails">
			<div class="leftDiv">
				<div class="standardInputDiv">
						<div class="label110mrg">
							<label class="standardInputTitle"><spring:message code="label.CLIA" /></label>
						</div>
						<div class="inputFieldInDialogWindow">
							<input type="text" name="addClia" id="addClia" class="required standardInputMedText" tabindex="20" maxlength="50" title="Clinical laboratory"/>
							<a href="#" class="dropdownImage" onClick="return false"></a>
						</div>
				</div>
			</div>
		</div>
		<div class="clearBoth"></div>
		
		<div id="pfiDetails">
			<table id="addNewLabDetailsToClia"></table>
		</div>
		<div class="clearBoth"></div>
		<div id="addPFIToClia">
			<a href="#" onclick="addPFIDetails()">Add PFI details</a>
		</div> 	
		
		<div class="AddContactNote">
			<label class="requiredInd">Note:</label> 
			<label> Facilities will not be added to this CLIA until users clicks on below SAVE button</label>
		</div>		
		
		<div class="button-row" style="margin-bottom: 12px; margin-top: 35px;">
			<a id="addCliaAndPFI" class="a_demo_search a_demo_save" style="color: #FFFFFF; margin-left: 5px;" onclick="addCliaWithFacilities();"> Save </a> 
			<a id="changeCliaForPFI" class="a_demo_search a_demo_save" style="color: #FFFFFF; margin-left: 5px;" onclick="changeClia();"> Change </a>
			<a class="a_demo_search a_demo_cancel" style="color: #FFFFFF; margin-left: 5px;" onclick="closeStdDialog(this);"> Cancel </a>
		</div>
	</div>
	
	
	
	<div id="addFacilityDetails" >
		<div class="stdDetailsDiv" id="stdInfoDiv">
			<div id="facilityDetails" >
				<form:form modelAttribute="addressFieldVO">
					<div id="newLabFacilityDetails">
						
						 <div class="labReportTitle"><b>Lab Information:</b></div>
						<div class="labFacilityName">
							<div style="float:left; width:45%">
								<div class="standardInputDiv">
									<div  style="width:10%; float:left;">
										<label for="addPfi"><spring:message code="label.PFI" /><span class="requiredInd" title="Required Field">*</span></label>
									</div>
									<div class="labReportInput">
										<input id="addPfi" name="addPfi"  class="required standardInputMedText mR50" value="" type="text" tabindex="21" title="<spring:message code='title.PFI' />">
									</div>
								</div>
							</div>	
							<div style="float:left; width:45%">
								<div class="standardInputDiv">
									<div class="labReportLabel1">
										<label for="addLabName"><spring:message code="label.labName" /><span class="requiredInd" title="Required Field">*</span></label>
									</div>
									<div class="labReportInput">
									    	<input id="addLabName" name="addLabName" class="required standardInputMedText mR50" value="" type="text" tabindex="22" title="<spring:message code='title.labName' />">
									</div>
								</div>
							</div>
							<div style="float:left; width:45%">
								<div class="standardInputDiv">
									<div style="width:24%; float:left;">
										<span class="labReportTitle">LAFT Staff Assigned:</span>
									</div>
									<div class="labReportInput">
										<input id="addLaftStaffAssigned" maxlength="100" class="standardInputlabText ui-autocomplete-input" type="text" name="addLaftStaffAssigned" autocomplete="off" title="LAFT Staff assigned" value=""/>
									</div>
								</div>
							</div>
						</div>
						<div class="labReportTitle"><b>Address Information:</b></div>
						<div class="widgetTargetFields" id="physicalFacilitywidgetFields">
						    <div class="labFacilityAddress1">
								<div style="float:left; width: 60%">
									<div class="standardInputDiv">
										<div class="labReportLabel1">
											<label for="addMdlDlgStreet"><spring:message code="label.streetAddress" /><span class="requiredInd" title="Required Field">*</span></label>
										</div>
										<div class="labReportInput">
											<input type="text" size="35" maxlength="80" tabindex="-1"  name="addMdlDlgStreet" id="addMdlDlgStreetTarget" class="required standardInputlabText newAddress fieldLevelHelp widgetAddressFieldsReadonly" title="<spring:message code='title.streetAddress' />"/>
										</div>
									</div>
								</div>
								<div style="float:left; width: 40%">
									<div class="standardInputDiv">
										<div class="labReportLabel1">
											<label for="addMdlDlgCity"><spring:message code="label.city" /><span class="requiredInd" title="Required Field">*</span></label>
										</div>
										<div class="labReportInput">
											<input type="text"  id="addMdlDlgCityTarget" name="addMdlDlgCity" tabindex="-1" class="required standardInputMedText newCity widgetAddressFieldsReadonly"  title="<spring:message code='title.city' />"></input>
										</div>
									</div>
								</div>
							</div>
							<div class="labFacilityAddress2" style="padding-bottom:10px;">
							
								<div class="placeHolder">
									<div class="standardInputDiv">
										<div class="labReportLabel"  style="width:25.5%">
											<label for="state"><spring:message code="label.state" /><span class="requiredInd" title="Required Field">*</span></label>
										</div>
										<div class="labReportInput">
											<span>
												<spring:message code="title.state" var="state_title"/>
											<form:select path="state" id="addMdlDlgStateTarget" tabindex="28" cssClass="required standardInputMedText fieldLevelHelp newState widgetAddressFieldsReadonly" cssStyle="WIDTH:50px"  title="${state_title}">
												<form:option value=""></form:option>
												<form:options items="${sessionScope.stateMap}"/>
											</form:select>
											</span>
										</div>
									</div>
								</div>
								<div class="placeHolder">
								
									<div class="standardInputDiv">
										<div class="labReportLabel1">
											<label for="county"><spring:message code="label.county" /><span class="requiredInd" title="Required Field">*</span></label>
										</div>
										<div class="labReportInput">
											<spring:message code="title.county" var="county_title"/>
											<form:select path="county" id="addMdlDlgCountyTarget" tabindex="29" cssClass="required standardInputMedText newCounty widgetAddressFieldsReadonly" cssStyle="WIDTH:180px"  title="${county_title}">
												<form:option value=""></form:option>
												<form:options items="${sessionScope.countyMap}"/>
											</form:select>
										</div>
									</div>									
								</div>	
								<div class="placeHolder">
									
									<div class="standardInputDiv">
										<div class="labReportLabel">
											<label for="addMdlDlgZip"><spring:message code="label.zip" /><span class="requiredInd" title="Required Field">*</span></label>
										</div>
										<div class="labReportInput">
											<input type="text"  id="addMdlDlgZipTarget" name="addMdlDlgZip"  tabindex="30" class="newZip required standardInputMedText widgetAddressFieldsReadonly"  title="<spring:message code='title.zip' />"></input>
										</div>
									</div>
								</div>	
																
							</div>				
							<div class="" style="float:right;">
								<a href="javascript:openAddressWidget('physicalFacilitywidgetFields','required')" class="changeAddressLinks">Add Address </a>
								<a href="javascript:clearDialogFields('physicalFacilitywidgetFields')" class="changeAddressLinks">Reset Address </a>
							</div>
						</div>	
						
					</div>
					<div class="clearBoth"></div>			
					<div id="addContactDetails">
				  	 	<table id="addNewContact"></table>
				  	 	<div id="addNewContactPager"></div>
				   </div>
					<div style="text-align: center; margin-bottom:12px; clear: both; margin-bottom:20px"><br/>
						<a class="actionButtons a_demo_search a_demo_save a_demo_add" style="color:#FFFFFF; margin-left: 5px;" onclick="addFacility();"> Add </a>
						<a class="actionButtons a_demo_search a_demo_cancel" style="color:#FFFFFF; margin-left: 5px;" onclick="togglePfiScreenToClia(true);"> Cancel </a>    
						<a class="actionButtons a_demo_search a_demo_reset" style="color:#FFFFFF; margin-left: 5px;" onclick="clearAddFacilityDetails('facilityDetails');"> Reset </a> 
					</div>
					<div id="confirmMessageDialog">
						<h4 id="confirmMessageContent"></h4>
					</div>
				</form:form>
			</div>
		</div>			
	</div>
	
	
	
				
</div>
