<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="personHIVDetailsForm" class="container clear" style="clear:both" id="prfProviderSection">	
				<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
					<span class=" mL2">Provider Information</span>					
				</div>
				<div style="border: 1px dotted; height:455px">
					<div class="personHIVDetailsLeft">
						<div class="personTitleField italic">Person completing the form:</div>
						<div class="pL30 pT10">
							<span class="personLabelDoc4">Name </span>
							<span> 
								<span ><form:input path="COMPLETER_LNAME" class="standardInputSmallText mL20"  maxlength="25"/></span> 
								<span ><form:input path="COMPLETER_FNAME" class="standardInputSmallText"  maxlength="20"/></span>
								<span ><form:input path="COMPLETER_MNAME" class="standardInputSmallText"  maxlength="1"/></span>
								<span class="labelunderneath4"> Last</span>
								<span class="labelunderneath4"> First</span>
								<span class="labelunderneath4"> Middle</span>
							</span>
						</div>
						<div class="pL30 pT10">
							<span class="riskLabelField4">Title</span>
							<span ><form:input path="COMPLETER_TITLE" class="standardInputMedText"  maxlength="30"/></span> 
						</div>
						<div class="pL30 pT10">
							<span class="riskLabelField4">Telephone number</span>
							<span> 
								<form:input path="COMPLETER_PHONE" class="pNumber standardInputMedText"  maxlength="20"/>
								Extn <form:input path="COMPLETER_PHONE_EXT" class="nOnly standardInputSmallText1"  maxlength="5"/>
							</span>
						</div>

						<div class="pL30 pT10">
							<span class="riskLabelField4">E-mail address</span>
							<span ><form:input path="COMPLETER_EMAIL" class="standardInputMedText"  maxlength="50"/></span>
							<div class="v_error COMPLETER_EMAIL"></div> 
						</div><br>
						<!--  Facility details  -->
						<div id="prfDocumentFacilityAddressDtls" class="facilityDetails">
							<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD;">
								<span class="mL2">Facility Details</span>
							</div><br>
							<div class="pL30 pT10">
								<span class="riskLabelField4">Facility Name</span>
								<span ><form:input path="FACILITY_NAME" class="standardInputText  facilityFields" maxlength="80" /></span> 
							</div>
							<div class="pL30 pT10">
								<span class="riskLabelField4">Department/clinic</span>
								<span ><form:input path="FACILITY_DEPT" class="standardInputSmallText2  facilityFields" maxlength="40" /></span> 
							</div>
							<div id="prfFacilityAddressSection" style="border: 1px dotted;" class="mT10 pB10">
								<div class="pL30 pT10">
									<span class="riskLabelField4">Facility address</span>
									<span ><form:input path="FACILITY_ADDRESS" class="standardInputText newAddress widgetAddressFieldsReadonly  facilityFields"  maxlength="62"/></span>
									<span class="labelunderneath3 dispAddressStdRaw"> Street (Std)</span> <br>
								</div>
								<div class="pL30 pT10">
									<span class="riskLabelField4">City</span>
									<span><form:input path="FACILITY_CITY" class="oAdrDlg standardInputSmallText1 newCity widgetAddressFieldsReadonly  facilityFields"  maxlength="30"/></span>
									<span class="pL30">County</span>
									<span class="pL30">
										<form:select path="FACILITY_COUNTY"  cssClass="oAdrDlg standardInputSmallText1 newCounty widgetAddressFieldsReadonly  facilityFields" > 
															<form:option value=""></form:option>
															<form:options items="${sessionScope.countyMap}"/>
														</form:select>
									</span>
									
										 <div style="float: right;" class="mr5">
					 					 	<a href="javascript:openAddressWidget('prfFacilityAddressSection');" class="inlineEditButton displayNone editAddress">Edit</a>
										 </div>
									
									<br>
								</div>	
							
								<div class="pL30 pT10">
									<span class="riskLabelField4">State</span>
									<span>
										<form:select path="FACILITY_STATE" class="oAdrDlg standardInputSmallText1 newState widgetAddressFieldsReadonly  facilityFields">
											<form:option value=""></form:option>
											<form:options items="${sessionScope.stateMap}"/>
										</form:select> 
									</span>
									<span class="pL30"> Zip </span>
									<span class="pL30"><form:input path="FACILITY_ZIP" class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly  facilityFields" maxlength="5"/>
										<form:input path="FACILITY_PLUS4" class="standardInputSmallText1 facilityFields" maxlength="4"/></span>	
									<br>
								</div>
							</div>
							<div class="pL30 pT10">
								<span class="riskLabelField4">Date form Completed</span>
								<fmt:formatDate value="${stdPRFDocVO.COMPLETED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedcompletedDate" ></fmt:formatDate>
								<span ><form:input path="COMPLETED_DATE_TEXT" value="${theFormattedcompletedDate}" class="dPicker standardInputMedText" datevalidations="completeDate" maxlength="10" title="Date form completed"/></span>
								<div class="v_error COMPLETED_DATE_TEXT"></div> 
							</div>
						</div>	
					</div>
					<!-- Other Contact Details -->
					<div class="personHIVDetailsRight">
						<div class="personTitleFieldRight">Office contact person for information related to partner notification<br><center>(if other than the person completing the form):<center></div>	
						<div class="pL30 pT10">
							<span class="personLabelDoc4">Name </span>
							<span> 
								<span ><form:input path="CONTACT_LNAME" class="mL20 standardInputSmallText"  maxlength="25"/></span> 
								<span ><form:input path="CONTACT_FNAME" class="standardInputSmallText"  maxlength="20"/></span>
								<span ><form:input path="CONTACT_MNAME" class="standardInputSmallText"  maxlength="1"/></span>
								<span class="labelunderneath4"> Last</span>
								<span class="labelunderneath4"> First</span>
								<span class="labelunderneath4"> Middle</span>
							</span>
						</div>
						<div class="pL30 pT10">
							<span class="riskLabelField4">Title</span>
							<span ><form:input path="CONTACT_TITLE" class="standardInputSmallText"  maxlength="30"/></span> 
						</div>
						<div class="pL30 pT10">
							<span class="riskLabelField4">Telephone number</span>
							<span> 
								<form:input path="CONTACT_PHONE" class="pNumber standardInputSmallText2"  maxlength="25"/>
								Extn <form:input path="CONTACT_PHONE_EXT" class="nOnly standardInputSmallText1"  maxlength="5"/>
							</span>
						</div><br><br>	
						<div class="personTitleFieldRight"><center>Medical Provider:<br>To the best of my knowledge,<br>I certify that the above information is correct.<center></div>
						<div class="pL30 pT10">	
							<span class="riskLabelField4"> Has Provider Signed Form</span> 
								<div> 
									<span> <form:checkbox path="PROV_SIGNED" value="true" class="otherFieldDependentCBox" dependentField="PROV_SIGNED_DATE_TEXT"/> Yes </span>
								</div>
						</div>	
						<div class="pL30 pT10">
							<span class="">Print Name</span>
							<span ><form:input path="PROV_LNAME" class="mL20 standardInputSmallText3 readonly"  maxlength="80"/>
								<form:input path="PROV_FNAME" class="ml10 standardInputSmallText3 readonly"  maxlength="80"/>
								<form:input path="PROV_MI" class="ml10 standardInputSmallText3 readonly"  maxlength="1"/></span> 
						</div>
						<div class="pL30 pT10">
							<span class="riskLabelField4">Date</span>
							<fmt:formatDate value="${stdPRFDocVO.PROV_SIGNED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedprovSignedDate" ></fmt:formatDate>
							<span ><form:input path="PROV_SIGNED_DATE_TEXT" value="${theFormattedprovSignedDate}" class="dPicker standardInputMedText" datevalidations="completeDate"  maxlength="10" title="Provider signed date"/></span>
							<div class="v_error PROV_SIGNED_DATE_TEXT"></div> 
						</div>			
					</div>
				</div>	
			</div>