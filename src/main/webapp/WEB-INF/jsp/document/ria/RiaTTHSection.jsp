<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="psDetailsHeadingDiv" class="divHeadingCls"
				style="background: #DDDDDD;">
				<span class=" mL2">Testing & Treatment History</span>
			</div>
<div class="mLRTB pB20">
				<div class="fieldHolder25">
					<span class=" bold">No previous test </span> 
						<span
							class="mL10">  <form:radiobutton   path="NO_PREVIOUS_TEST"  id="NO_PREVIOUS_TEST" value="Y" title="No previous test? Yes" /> Yes </span>
					 	<span class="mL5"><form:radiobutton   path="NO_PREVIOUS_TEST"  id="NO_PREVIOUS_TEST" value="N" title="No previous test? No"  /> No </span>
						
					</div>
				<div class="fieldHolder25">
					<span class="fLW50  bold"> Date of last negative HIV test</span> <span
						class="fLW50"> <form:input path="NYS_LAST_NEG_DT"
							id="NYS_LAST_NEG_DT" title="Date of last negative HIV test"
							class="standardInputSmallText2  mask dPicker " maxlength="10" />
					</span>
					<div class="v_error mR10 NYS_LAST_NEG_DT "></div>
				</div>
				<div class="fieldHolder25">

					<span class="bold"> Date of initial appointment</span> <span class="mL10">
						<form:input path="INITIAL_APPOINTMENT_DATE"
							id="INITIAL_APPOINTMENT_DATE" title="Date of initial appointment"
							class="standardInputSmallText2  mask dPicker " maxlength="10" />
					</span>
					<div class="v_error mR10 INITIAL_APPOINTMENT_DATE "></div>
				</div>
				
			</div>
			<div class="mLRTB pB20 clearBoth">
				
				<div class="fieldHolder25">
					<span class=" bold">Did patient take PrEP prior to infection? </span> 
						<span
							class="mL10">  <form:radiobutton   path="PREP_TAKEN"  id="PREP_TAKEN" value="Y" title="Did patient take PrEP prior to infection? Yes" onclick="enablePrepDependentFields(this)"/> Yes </span>
					 	<span class="mL5"><form:radiobutton   path="PREP_TAKEN"  id="PREP_TAKEN" value="N" title="Did patient take PrEP prior to infection? No"  onclick="enablePrepDependentFields(this)"/> No </span>
						
					</div>
					
					<div>
					
						<div class="fieldHolder25" >
		
								<span class=" bold">PrEP Adherence </span> 
								
								<span
									class="mL10"> 
									
									
									<form:select path="PREP_ADHERENCE" id="PREP_ADHERENCE"
		 							title="PrEP Adherence" class="standardInputSmallText1 " dependentField="PREP_DEPENDENT_FIELDS"  onchange="showPreAdheranceFields(this)"> 
		 								<form:option value="">Select</form:option> 
		 								<form:option value="Y">Yes</form:option> 
		 								<form:option value="N">No</form:option>
		 						</form:select> 
								</span>
			
							</div>
							
								<div class="fieldHolder25" id="PREP_DEPENDENT_FIELDS">
			
									<span class="  bold">Non Adherence reason</span> 
									
									<span
										class="mL10"> 
										<c:set var="refColumnData" value="${riaDropDownList.PREP_NON_ADHERENCE_REASON}"/>						
										<form:select path="PREP_NON_ADHERENCE_REASON"
				 							id="PREP_NON_ADHERENCE_REASON" title="Non Adherence reason" 
				 							class="standardInputMedText "  dependentField="ADHERENCE_OTHER_FIELDS"  onchange="showOtherFields(this)"> 
				 							<form:option value="">Select</form:option> 
				 							<c:forEach items="${refColumnData}" var="codedData"> 
				 								<form:option value="${codedData.LOOKUP_CD}">${codedData.LOOKUP_NAME}</form:option> 
				 							</c:forEach> 
				 						</form:select> 
				 						
				 						
									</span>
				
								</div>
								
								<div class="fieldHolder25" id="ADHERENCE_OTHER_FIELDS">
									
									<span class="bold"> Non Adherence Other reason</span> <span class="mL10">
										<form:input path="PREP_NON_ADHER_RSN_OTHER_TEXT"
											id="PREP_NON_ADHER_RSN_OTHER_TEXT" title="Non Adherence Other reason"
											class="standardInputSmallText "  />
									</span>
									
									
								</div>
								
								
						</div>
							
				
			</div>
			<div class="mLRTB pB20 clearBoth">
				<div class="fieldHolder25">

					<span class="bold"> Ever taken any ARVs? </span> <span class="mL20"><form:radiobutton  
							path="ARVS_TAKEN" id="ARVS_TAKEN" class="takenArvs" value="Y"
							title="Ever taken any ARVs? Yes" onClick="enableARVFields(this)"/> Yes </span> <span class="mL5"><form:radiobutton  
							path="ARVS_TAKEN" id="ARVS_TAKEN" class="takenArvs" value="N"
							title="Ever taken any ARVs? No" onClick="enableARVFields(this)"/> No </span> <span class="mL5"><form:radiobutton  
							path="ARVS_TAKEN" id="ARVS_TAKEN" class="takenArvs" value="U"
							title="Ever taken any ARVs? Unknown" onClick="enableARVFields(this)"/> Unknown</span>

				</div>
				<div id="ARV_FIELDS">
					<div class="fieldHolder25">
						<span class="valignTop bold"> If Yes, ARV medications: </span> <span
							class="mL5"> 
							<form:select path="ARV_MEDICATION"
								id="ARV_MEDICATION" class="standardInputMedText" maxlength="200"
								title="ARV medications" multiple="true">
								<c:forEach items="${codedArvsMedications}" var="arvCategory">
									<form:option value="${arvCategory.LOOKUP_CD}">${arvCategory.ARV_DESC}</form:option>
								</c:forEach>
								
	
							</form:select> <script type="text/javascript"> 
			 					arvMedications = '${stdRiaDocVO.ARV_MEDICATION}';
			 					$('#ARV_MEDICATION').val(arvMedications.split(','));
							</script>
	
						</span>
	
					</div>
					<div class="fieldHolder25">
						<span class="bold"> Treatment Start Date</span> <span
							class="mL10"> <form:input path="ARV_BEGIN_DATE"
								id="ARV_BEGIN_DATE" title="ARV Treatment Start Date"
								class="standardInputSmallText2  mask dPicker " maxlength="10" />
						</span>
						<div class="v_error mR10 ARV_BEGIN_DATE "></div>
					</div>
					
				</div>
			</div>

			<div class="clearBoth mL10 pT10 pB20">
				
				<div class="flw25">
					<span class="bold"> Source of treatment</span> <span
						class="mL10">
						
						<c:set var="refColumnData" value="${riaDropDownList.TREATMENT_SOURCE}"/>						
						<form:select path="TREATMENT_SOURCE"
 							id="TREATMENT_SOURCE" title="Source of treatment information" 
 							class="standardInputSmallText2 " dependentField="TREATMENT_SOURCE_FIELDS"  onchange="showOtherFields(this)"> 
 							<form:option value="">Select</form:option> 
 							<c:forEach items="${refColumnData}" var="codedData"> 
 								<form:option value="${codedData.LOOKUP_CD}">${codedData.LOOKUP_NAME}</form:option> 
 							</c:forEach> 
 						</form:select> 
 					</span>
				</div>
				<div class="flw25" id="TREATMENT_SOURCE_FIELDS">
					<span class="bold"> Other treatment</span> <span class="mL10">
						<form:input path="TREATMENT_SOURCE_OTHER_TEXT"
							id="TREATMENT_SOURCE_OTHER_TEXT" title="Source of treatment other"
							class="standardInputSmallText " maxlength="10" />
					</span>

				</div>
								
				<div class="flw40">

					<span class="flw25   bold"> Treating Facility Name</span> 
					
					<span class="flw60 ">
							
						<form:select  path="TREATMENT_FACILITY_NAME"   class="standardInputText "  title="Treating Facility Name">
							<form:option value="" >--select--</form:option>
							<c:forEach items="${subFacilities}" var="facility">
								<form:option value="${facility.facility_uid}" label="${facility.facility_name}" name="${facility.facility_name}"></form:option>
							</c:forEach>
						</form:select> 
						</span>
						<span class="flw15"><a class="mailFacility" href="#" style="top:0px !important;"></a></span>
				</div>

				
				
				
				
			</div>
			<div class="clearBoth mL10 pT10 pB20">
				<div class="flw25">

					<span class="bold"> Facility Type</span> <span class="mL10">
						<c:set var="refColumnData" value="${riaDropDownList.DIAGNOSIS_SETTING_TYPE}"/>
							<form:select path="TREATMENT_FACILITY_TYPE"
									title=" Treating Facility Type"
									class="standardInputMedText " dependentField="TREATMENT_FACILITY_TYPE_FIELDS" onchange="showOtherFields(this)">
								<form:option value="">Select</form:option>
								<c:forEach items="${refColumnData}" var="diagnosisSettingData">
										<form:option value="${diagnosisSettingData.LOOKUP_CD}">${diagnosisSettingData.LOOKUP_NAME}</form:option>
									</c:forEach>
							</form:select>
					</span>
				</div>
				
				<div class="flw25" id="TREATMENT_FACILITY_TYPE_FIELDS">
								<span class="bold">Other </span> 
									<span
										class="mL10"> 
									<form:input path="TREATMENT_FAC_TYPE_OTHER_TEXT"
 											title="Treating Facility Type other text" 
 											class="standardInputSmallText" /> 
										
									</span>
							</div>
							
				
			</div>
			
			<div class="mLRTB pB20 clearBoth">
				<div class="fieldHolder30">
				<span class="fLW35 bold">RAP Provider </span> 
					<span
						class="fLW65"><form:select path="TTH_RAP_PROVIDER" class="standardInputMedText "  dependentField="TTH_RAP_PROVIDER_FIELDS"  onchange="showRiaDependentFields(this)">
						
							<form:option value=""></form:option>
							<form:option value="Y">Yes</form:option>
							<form:option value="N">No</form:option>
						</form:select>
						</span>
						</div>

				<div id="TTH_RAP_PROVIDER_FIELDS">
					<div class="fieldHolder70">
							<span class="bold">Provider First name </span> <span
							class="mL10"><form:input path="TTH_RAP_PROVIDER_FNAME"
								class="standardInputMedText "  title="TTH RAP Provider First name" /> </span>
					
							<span class="mL20 bold">Provider Middle name </span> <span
							class="mL10"><form:input path="TTH_RAP_PROVIDER_MNAME"
								class="standardInputMedText " title="TTH RAP Provider Middle name"/> </span>
							
							<span class="mL20 bold">Provider Last name </span> <span
							class="mL10"><form:input path="TTH_RAP_PROVIDER_LNAME"
								class="standardInputMedText rapLname" title="TTH RAP Provider Last name"/> </span>
					</div>
				</div>
			</div>