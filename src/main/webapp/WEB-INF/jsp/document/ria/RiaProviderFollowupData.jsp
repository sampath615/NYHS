<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<div class="container" id="providerFollowUpDetails"
			style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
			
			<div>
				<div id="psDetailsHeadingDiv" class=" divHeadingCls"
					style="background: #DDDDDD; width:100%; height:20px">
					
					<div class="floatLeft">	
						<span class=" mL2">Provider Follow-up</span>
					</div>
					
					<div style="float:right; margin-right:20px;">
						<span><img class="cloningIcons" src="/hpn/ctrldocs/nyehms/images/plus.png" onclick="javascript:addProviderFollowUpData()"> </span>
					</div>
				
				</div>

				
			</div>
			
			
			
			
			
			<div id="riaProviderFollowUpContent" >
			<c:forEach items="${stdRiaDocVO.riaProviderFollowupList}" var="riaProviderFollowupList" varStatus="profFollowUpIndex">
			 
					
						<div style="width:100%;  padding-top:3px; border-bottom:1px solid black " class="riaProviderFollowUpInfo" >
							<form:hidden path="riaProviderFollowupList[${profFollowUpIndex.index}].RIA_PROVIDER_FOLLOW_UP_ID" class="riaProvFollowUpFields"/>
							<div class="deleteRiaProviderFollowUp displayNone">
								<div style="float:left width:2%;">
									<span class="mL2" style="float:right; margin-top:4px;margin-right:10px"><img class="cloningIcons" onclick="deleteProvFollowUp(this);" src="/hpn/ctrldocs/nyehms/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"></span>
								</div>
							</div>
						
							<div class="mLRTB pB20">
							
								<div class="fieldHolder25" >
									<div>
										<span class="bold">  Date of contact
											
											<form:input  path="riaProviderFollowupList[${profFollowUpIndex.index}].DATE_OF_CONTACT" id="riaProviderFollowupList${profFollowUpIndex.index}_DATE_OF_CONTACT" class="mL20 standardInputSmallText1 mask dPicker riaProvFollowUpFields"   title="Date of Contact" />
											 
										</span>
									</div>
								</div>
							
								<div class="fieldHolder25" >
									<div>
									<span class="mL5  bold">Reason for Contact</span> 
										
										
										<c:set var="refColumnData" value="${riaDropDownList.CONTACT_REASON}"/>
										
										<form:select  path="riaProviderFollowupList[${profFollowUpIndex.index}].CONTACT_REASON" id="riaProviderFollowupList${profFollowUpIndex.index}_CONTACT_REASON" class="mL20 standardInputMedText riaProvFollowUpFields"   title="Reason for Contact">
											<form:option value="">Select</form:option>
											<c:forEach items="${refColumnData}" var="contactReasonData">
													<form:option value="${contactReasonData.LOOKUP_CD}">${contactReasonData.LOOKUP_NAME}</form:option>
												</c:forEach>
										</form:select>
										
									</div>
								</div>
							
							
								<div class="fieldHolder25">
									<div>
									<span class=" bold">Provider</span> 
									
										<form:input  path="riaProviderFollowupList[${profFollowUpIndex.index}].PROVIDER" id="riaProviderFollowupList${profFollowUpIndex.index}_PROVIDER" class="mL20 standardInputLongText riaProvFollowUpFields"   title="Provider"/>
									</div>
								</div>
								
								<div class="fieldHolder25">
									<div>
										<span class="bold">  Provider Role
											<c:set var="refColumnData" value="${riaDropDownList.PROVIDER_ROLE}"/>
										
											<form:select  path="riaProviderFollowupList[${profFollowUpIndex.index}].PROVIDER_ROLE" id="riaProviderFollowupList${profFollowUpIndex.index}_PROVIDER_ROLE" class="mL20 standardInputMedText riaProvFollowUpFields"   title="Provider role">
												<form:option value="">Select</form:option>
											<c:forEach items="${refColumnData}" var="providerRoleData">
													<form:option value="${providerRoleData.LOOKUP_CD}">${providerRoleData.LOOKUP_NAME}</form:option>
												</c:forEach>
										</form:select>
										</span>
									</div>
								</div>
						</div>
						<div class="mLRTB pB20 clearBoth">
							
							
							<div class="fieldHolder25">
								<div>
								<span class="bold">Facility</span> 
								
									<form:select  path="riaProviderFollowupList[${profFollowUpIndex.index}].FACILITY" id="riaProviderFollowupList${profFollowUpIndex.index}_FACILITY" class="mL20 standardInputLongText riaProvFollowUpFields"   title="Reason for Contact">
										<form:option value="" >--select--</form:option>
										<c:forEach items="${subFacilities}" var="facility">
											<form:option value="${facility.facility_uid}" label="${facility.facility_name}" name="${facility.facility_name}"></form:option>
										</c:forEach>
									</form:select> 
									
								</div>
							</div>
							
							
							<div class="fieldHolder25">
								<div>
								<span class="mL5  bold">Contact Person</span> 
								
									<form:input  path="riaProviderFollowupList[${profFollowUpIndex.index}].CONTACT_PERSON" id="riaProviderFollowupList${profFollowUpIndex.index}_CONTACT_PERSON" class="mL20 standardInputMedText riaProvFollowUpFields"   title="Contact Person"/>
								</div>
							</div>
							
							<div class="fieldHolder25">
								<div>
									<span class="bold">  RIA Materials Provided
										
										<form:select  path="riaProviderFollowupList[${profFollowUpIndex.index}].RIA_MATERIALS_PROVIDED" id="riaProviderFollowupList${profFollowUpIndex.index}_RIA_MATERIALS_PROVIDED" class="mL20 standardInputMedText riaProvFollowUpFields"   title="Provider role">
											<form:option value="" label="Select"></form:option>
											<form:option value="Y" label="Yes"></form:option>
											<form:option value="N" label="No"></form:option>
										</form:select> 
									</span>
								</div>
							</div>
						
						</div>
						
						
				</div>
			</c:forEach>
			</div>
			
		