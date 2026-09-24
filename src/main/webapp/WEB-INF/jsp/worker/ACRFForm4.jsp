<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- This jsp belongs to "TTH" Fourth Tab --%> 
<!-- page4 first block starts -->

<span class="mL10 hdrFont"> X. Treatment/Services Referrals (record all dates as mm/dd/yyyy) </span>
<div class="roundedBorderFont mL10" style="width:98%; background:#DDDDDD" >

	<div style="width:99%; margin-left:5px;  margin-right:5px;">
		<div style="float:left; width:40%;" id="patient_informed_hiv_infection"> 
			<span class="mL5"> Has this patient been informed of his/her HIV infection? </span></br>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.informedOfHiv" id="rawAcrfDoc_informedOfHiv" value="Y" title="Informed HIV infection" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.informedOfHiv" id="rawAcrfDoc_informedOfHiv" value="N" title="Informed HIV infection" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.informedOfHiv" id="rawAcrfDoc_informedOfHiv" value="U" title="Informed HIV infection" /> Unknown</span>
			<div class="v_error patient_informed_hiv_infection"></div>	
		</div>
		<div style="float:left; width:59%; border-left:1px dotted; " id="patient_partner_notified"> 
			<span class="mL20"> This patient's partners will be notified about their HIV exposure and counseled by: </span></br>
			<span class="mL20"><form:radiobutton path="rawAcrfDoc.partnersNotifiedBy" id="rawAcrfDoc_partnersNotifiedBy" value="H" title="Notified about their HIV exposure" /> 1-Health Dept </span>
			<span class="mL20"><form:radiobutton path="rawAcrfDoc.partnersNotifiedBy" id="rawAcrfDoc_partnersNotifiedBy" value="D" title="Notified about their HIV exposure" /> 2-Physician/Provider </span>
			<span class="mL20"><form:radiobutton path="rawAcrfDoc.partnersNotifiedBy" id="rawAcrfDoc_partnersNotifiedBy" value="P" title="Notified about their HIV exposure" /> 3-Patient </span>
			<span class="mL20"><form:radiobutton path="rawAcrfDoc.partnersNotifiedBy" id="rawAcrfDoc_partnersNotifiedBy" value="U" title="Notified about their HIV exposure" /> 9-Unknown</span>
			<div class="v_error patient_partner_notified"></div>			
		</div>		
	</div>
	
	<c:if test="${acrfRevison > 2016}">
	<div style="width:99.9%; border:1px dotted;" class="container"></div>
	         
		<div style="width:99%; margin-left:5px;  margin-right:5px;">
			<div style="float:left; width:100%;" id="non_lab_med_care"> 
				<span class="mL5"> Evidence of receipt of HIV medical care other than laboratory test result(select one; record additional evidence in Comments) </span></br>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.nonLabMedCare" id="rawAcrfDoc_nonLabMedCare" value="1" title="Informed HIV infection" /> 1-Yes, documented </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.nonLabMedCare" id="rawAcrfDoc_nonLabMedCare" value="2" title="Informed HIV infection" /> 2-Yes, client self-report, only </span>
				<span class="mL10">Date of medical visit or prescription <form:input path="rawAcrfDoc.nonLabMedCareDate" id="rawAcrfDoc_nonLabMedCareDate" datevalidations="partialDateForAll"  class="standardInputSmallText dPicker mask datepicker maskDate" maxlength="15" title="Date" />  </span>
				
				
				<div class="v_error non_lab_med_care"></div>
				<div class="v_error rawAcrfDoc_nonLabMedCareDate mL10"></div>	
			</div>
		</div>
	</c:if>

	<div style="width:100%; background-color:#DBEAF9; margin-top:44px;">
			<span class="hdrFont mL5"> For Female Patient</span>
	</div>		

	<div style="width:99%; margin-left:5px;  margin-right:5px;">
		<div id="patient_referred_for_gynecological" style="float:left; width:40%; padding-bottom:5px;"> 
			<span class="mL5"> This patient is receiving or has been referred for gynecological or  </span></br>
			<span class="mL5"> obstetrical services</span>		
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.refferedForObgyn" class="radioRequired" id="rawAcrfDoc_refferedForObgyn" value="Y" title="Obstetrical services" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.refferedForObgyn" class="radioRequired" id="rawAcrfDoc_refferedForObgyn" value="N" title="Obstetrical services" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.refferedForObgyn" class="radioRequired" id="rawAcrfDoc_refferedForObgyn" value="U" title="Obstetrical services" /> Unknown</span>
			<div class="v_error patient_referred_for_gynecological"></div>	
		</div>
		
		<div id="patient_currently_pregnant" style="float:left; width:30%; border-left:1px dotted; padding-bottom:5px; "> 
			<span class="mL5"> Is this patient currently pregnant? </span></br>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.currentlyPregnant" class="radioRequired" id="rawAcrfDoc_currentlyPregnant" value="Y" title="Currently pregnant" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.currentlyPregnant" class="radioRequired" id="rawAcrfDoc_currentlyPregnant" value="N" title="Currently pregnant" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.currentlyPregnant" class="radioRequired" id="rawAcrfDoc_currentlyPregnant" value="U" title="Currently pregnant" /> Unknown</span>
			<div class="v_error patient_currently_pregnant"></div>
			
		</div>	
		<div id="patient_delivered_live_born_infants" style="float:left; width:29%; border-left:1px dotted; padding-bottom:5px; "> 
			<span class="mL5"> Has this patient delivered live-born infants?</span></br>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.liveBirth" class="radioRequired" id="rawAcrfDoc_liveBirth" value="Y" title="Delivered live-born infants" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.liveBirth" class="radioRequired" id="rawAcrfDoc_liveBirth" value="N" title="Delivered live-born infants" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.liveBirth" class="radioRequired" id="rawAcrfDoc_liveBirth" value="U" title="Delivered live-born infants" /> Unknown</span>
			<div class="v_error patient_delivered_live_born_infants"></div>			
		</div>
	</div>
	
	<div style="width:100%; background-color:#DBEAF9; margin-top:44px;">
			<span class="hdrFont mL5"> For Children of Patient (record most recent birth in these boxes; record additional or multiple births in Comments)</span>
	</div>
	
	<div style="width:99%; margin:5px;">
		<div style="float:left; width:28%; padding-bottom:5px; border-right:1px dotted;"> 
			<span class="mL5"> Child's Name</span>
			<span class="mL10"> <form:input path="rawAcrfDoc.childName" id="rawAcrfDoc_childName" value="${acrfFormVO.rawAcrfDoc.childName}" class="standardInputSmallText" maxlength="150" title="Name"/> </span>
			<div class="v_error rawAcrfDoc_childName"></div>		
		</div>	
		<div style="float:left; width:36%; padding-bottom:5px; "> 
			<span class="mL20"> Child's Last Name Soundex</span>
			<span class="mL10"> <form:input path="rawAcrfDoc.childSoundex" id="rawAcrfDoc_childSoundex" value="${acrfFormVO.rawAcrfDoc.childSoundex}" class="standardInputSmallText" maxlength="150" title="Soundex"/>  </span>
			<div class="v_error rawAcrfDoc_childSoundex"></div>		
		</div>
		<div style="float:left; padding-bottom:5px; border-left:1px dotted;"> 
			<span class="mL20"> Child's Date of Birth</span>
			<span class="mL10"> <form:input path="rawAcrfDoc.childDob" id="rawAcrfDoc_childDob" datevalidations="partialDateForAll"  value="${acrfFormVO.rawAcrfDoc.childDob}" class="standardInputSmallText dPicker mask datepicker maskDate" maxlength="15" title="Date" />  </span>
			<div class="v_error rawAcrfDoc_childDob mL10"></div>
		</div>		
	</div>

	<div style="width:99.9%; border:1px dotted;" class="container"></div>

	<div style="width:99%; margin-top:5px; margin-left:5px;  margin-right:5px;">
		<c:if test="${acrfRevison < 2023}">
			<div style="float:left; width:38%; padding-bottom:5px; border-right:1px dotted;"> 
				<span class="mL5"> Child's Coded ID</span>
				<span class="mL10"><form:input path="rawAcrfDoc.childCodedId" id="rawAcrfDoc_childCodedId" value="${acrfFormVO.rawAcrfDoc.childCodedId}" class="standardInputSmallText" maxlength="20" title="Coded ID"/>  </span>
			</div>	
		</c:if>
		<div style="float:left; width:61%; padding-bottom:5px; "> 
			<span class="mL20"> Child's State Number</span>
			<span class="mL10"> <form:input path="rawAcrfDoc.childStateno" id="rawAcrfDoc_childStateno" value="${acrfFormVO.rawAcrfDoc.childStateno}" class="standardInputSmallText" maxlength="15" title="State Number"/>  </span>
		</div>	
	</div>	

	<div style="width:99.9%; border:1px dotted;" class="container"></div>

	<div style="width:99%; margin-top:5px; margin-left:5px;  margin-right:5px; padding-bottom:5px;">
		<div style="float:left; width:80%;  ">
			<span class="mL5"> Facility Name of Birth (if child was born at home, enter "home birth")</span>
			<span class="mL10"> 
				<form:select onchange="loadFacilityAddressDetails('#rawAcrfDoc_childrenFacility', this)" path="rawAcrfDoc.childrenFacilityEharsUid" id="rawAcrfDoc_childrenFacilityEharsUid"  addrsVar="facilityUid" class="standardInputText " maxlength="80" title="Facility Name">
					<form:option value="" >--select--</form:option>
					<c:forEach items="${subFacilities}" var="facility">
						<form:option value="${facility.facility_uid}" label="${facility.facility_name}" name="${facility.facility_name}"></form:option>
						
					</c:forEach>
					<form:option value="HOME_BIRTH" label="Home birth" name="Home birth"></form:option>
				</form:select> 
				<form:hidden path="rawAcrfDoc.childrenFacilityId" id="rawAcrfDoc_childrenFacilityId" addrsVar="facilityId"/>
				<form:hidden path="rawAcrfDoc.childrenFacilityName" id="rawAcrfDoc_childrenFacilityName" addrsVar="facilityName"/>		
				
			</span>
			<span ><a class="mailFacility" href="#"></a></span>
			<div class="v_error rawAcrfDoc_hospitalEharsFacilityEharsUid"></div>  
		</div>
		
	</div>		
	<div style="width:99.9%; border:1px dotted;" class="container" ></div>
	
	<div style="width:99%; padding-top:3px;" id="childrenAddress">
		<div style="float:left; width:50%; border-right:1px dotted;  padding-bottom:3px;"> 
			<span class="mL5"> <spring:message code="label.streetAddress" /></span>
			<span class="mL5"> <form:input path="rawAcrfDoc.childrenFacilityStreetAddress" id="rawAcrfDoc_childrenFacilityAddress"  class="standardInputText readonly" addrsVar="streetAddrs"  maxlength="80" title="Street Address"/> </span>
			<div class="v_error rawAcrfDoc_childrenFacilityAddress"></div>		
		</div>	
		<div style="float:left; width:30%;  border-left:1px dotted;"> 
			<span class="mL20"> <spring:message code="label.phoneNumber" /></span>
			<span class="mL10"> <form:input path="rawAcrfDoc.childrenFacilityPhone" id="rawAcrfDoc_childrenFacilityPhone" addrsVar="facilityPhone"  class="standardInputSmallText font10pt readonly" maxlength="20" title="Phone Number"/> </span>
			<div class="v_error rawAcrfDoc_childrenFacilityPhone"></div>		
		</div>
	</div>	
	
	<div style="width:99.9%; border:1px dotted;" class="container" ></div>
	
	<div style="width:99%; padding-top:3px;" id="childrenAddress">
		<div style="float:left; width:19%; "> 
			<span class="mL5"> <spring:message code="label.city" /></span>
			<span class="mL5"> <form:input path="rawAcrfDoc.childrenFacilityCity" id="rawAcrfDoc_childrenFacilityCity"  class="standardInputSmallText readonly" addrsVar="cityAddrs"  maxlength="50" title="City"/> </span>
		</div>
		<div style="float:left; width:20%; border-left:1px dotted;"> 
			<span class="mL5"> <spring:message code="label.county" /></span>
			<span class="mL5">  
		  					<form:select path="rawAcrfDoc.childrenFacilityCounty" id="rawAcrfDoc_childrenFacilityCountyFips"  class="standardInputSmallText readonly" addrsVar="countyAddrs" maxlength="50" title="County Fips" >
									<form:option value="" label="--select--"></form:option>
									<form:options items="${sessionScope.countyMap}"/>
								</form:select> 
			</span>
		</div>	
		<div style="float:left; width:15%; border-left:1px dotted;"> 
			<span class="mL5"> <spring:message code="label.state" /></span>
			<span class="mL5">  
							<form:select path="rawAcrfDoc.childrenFacilityState" id="rawAcrfDoc_childrenFacilityState"  class="standardInputSmallText2 readonly" addrsVar="stateAddrs" maxlength="50" title="State" >
									<form:option value="" label="--select--"></form:option>
									<form:options items="${sessionScope.stateMap}"/>
								</form:select> 
			</span>
		</div>	
		<div style="float:left; border-left:1px dotted;width:22%;"> 
			<span class="mL5"> Country</span>
			<span class="mL5"> <form:select path="rawAcrfDoc.childrenFacilityCountry" id="rawAcrfDoc_childrenFacilityCountry"  addrsVar="countryAddrs"  class="standardInputSmallText readonly" maxlength="50" title="Country">
				<form:option value=""></form:option>
					<form:options items="${codedCountries}"/>
					<form:option value="OTH">Other</form:option>
					<form:option value="UNK">Unknown</form:option>
				</form:select> </span>
		</div>	
		<div style="float:left; border-left:1px dotted;  "> 
			<span class="mL20"> <spring:message code="label.zip" /></span>
			<span class="mL10"> <form:input path="rawAcrfDoc.childrenFacilityZipCode" id="rawAcrfDoc_childrenFacilityZipcode"  class="standardInputSmallText readonly" addrsVar="zipAddrs"  maxlength="10" title="Zip Code"/> </span>
			<div class="v_error rawAcrfDoc_childrenFacilityZipcode"></div>		
		</div>	
	</div>	
	
	<div style="width:99.9%; border:1px dotted;" class="container" ></div>
	<div style="width:99%; margin-top:3px; margin-left:5px;  margin-right:5px;">
		<div style="float:left; width:70%;  "> 
			<span class=""> Facility Type</span>
			<span class="mL10"> <form:input path="rawAcrfDoc.childrenFacilityType" id="rawAcrfDoc_childrenFacilityTypeCode"  class="standardInputText font10pt readonly" addrsVar="facilityTypeCD"  maxlength="20" title="Facility Type"/> </span>
			<div class="v_error rawAcrfDoc_childrenFacilityCode"></div>		
		</div>
	</div>
	<div style="width:99.9%;" class="container"></div>
</div>

<!-- Page3 Second block -->

<br/>

<span class="mL10 hdrFont"> XI. Antiretroviral Use History (record all dates as mm/dd/yyyy) </span>
<div class="roundedBorderFont mL10" style="width:98%; background:#DDDDDD" >
<%-- 	<c:if test="${acrfFormVO.rawAcrfDoc.isOldAcrf != 'Y'}"> --%>
		<c:if test="${acrfRevison > 2013}">
		<div style="width:99%; margin-left:5px;  margin-right:5px; padding-bottom:5px">
			<div style="float:left; width:70%;"> 
				<span class="mL5"> Main source of antiretroviral(ARV) user information (select one): </span></br>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.mainARVSource" id="rawTTHDocVO_mainARVSource" value="PT_INTV" /> Patient Interview </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.mainARVSource" id="rawTTHDocVO_mainARVSource" value="MED_REC" /> Medical Record Review </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.mainARVSource" id="rawTTHDocVO_mainARVSource" value="PROV_RPT"/> Provider Report</span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.mainARVSource" id="rawTTHDocVO_mainARVSource" value="NHM_E"/> NHM&E</span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.mainARVSource" id="rawTTHDocVO_mainARVSource" value="OTH" /> Other</span>	
			</div>
			<div style="float:left; width:29%; border-left:1px dotted; padding-bottom:5px "> 
				<span class="mL20"> Date patient reported information </span></br>
				<span class="mL20"> <form:input path="rawAcrfDoc.arvPatientDateReported" id="rawTTHDocVO_arvPatientDateReported" datevalidations="partialDateForAll" value="${rawAcrfDoc.arvPatientDateReported}" class="standardInputMedText dPicker mask datepicker maskDate" title="Date" /> </span>
				<div class="v_error rawTTHDocVO_arvPatientDateReported mL20"></div>
			</div>
		</div>
	</c:if>
	<div style="width:99.9%; border:1px dotted;" class="container"></div>
	
	<div style="width:99%; margin-left:5px;  margin-right:5px; padding-bottom:5px">
		<div id="takenArvs" style="width:60%; border-right:1px dotted;"> 
				<span class="mL5"> Ever taken any ARVs?  </span>
				<span class="mL20"><form:radiobutton path="rawAcrfDoc.takenArvs" id="rawTTHDocVO_takenArvs" class="takenArvs" value="Y" title="ARVs" /> Yes </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.takenArvs" id="rawTTHDocVO_takenArvs" class="takenArvs" value="N" title="ARVs" /> No </span>
<%-- 				<c:if test="${acrfFormVO.rawAcrfDoc.isOldAcrf == 'Y'}"> --%>
				<c:if test="${acrfRevison == 2013}">
	 			<span class="mL5"><form:radiobutton path="rawAcrfDoc.takenArvs" id="rawTTHDocVO_takenArvs" class="takenArvs" value="R" title="ARVs" /> Refused</span>	 
				</c:if>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.takenArvs" id="rawTTHDocVO_takenArvs" class="takenArvs" value="U" title="ARVs" /> Unknown</span>
				<div class="v_error takenArvs"></div>	
			</div>
<%-- 			<c:if test="${acrfFormVO.rawAcrfDoc.isOldAcrf == 'Y'}"> --%>
				<c:if test="${acrfRevison == 2013}">
				<!-- Old Condition -->
				<div style="float:left; width:39%; padding-bottom:5px "> 
					<span class="mL20"> If Yes, ARV medications: </span>
					<span class="mL5"> 
						
						<form:select path="rawAcrfDoc.arvMeds" id="rawTTHDocVO_arvMeds"  class="standardInputMedText" maxlength="200"  title="ARV medications" multiple="true" >
								<c:forEach items="${codedArvsMedications}" var="arvCategory">
									<form:option value="${arvCategory.LOOKUP_CD}">${arvCategory.ARV_DESC}</form:option>
								</c:forEach>
								
						</form:select> 
		 				<script type="text/javascript"> 
		 					arvMedications = '${acrfFormVO.rawAcrfDoc.arvMeds}';
		 					$('#rawTTHDocVO_arvMeds').val(arvMedications.split(','));
						</script>
						
						</span>
					<div class="v_error rawTTHDocVO_arvMeds mL5"></div>
					<br>
					<div style="color:#F00">Note: User can select more than one ARV by using Control key</div>
				</div>
			</c:if>	
		</div>
<%-- 		<c:if test="${acrfFormVO.rawAcrfDoc.isOldAcrf == 'Y'}"> --%>
		<c:if test="${acrfRevison == 2013}">
		 	<div style="width:99.9%; border:1px dotted;" class="container"></div>
			<div style="width:99%; margin-left:5px;  margin-right:5px; padding-top:5px; padding-bottom:5px;"> 
					<span class="mL5"> Dates ARVs taken </span>
					<span class="mL50"> Date first began: </span>
					<span class="mL5"> <form:input path="rawAcrfDoc.dateArvsBegan" id="rawTTHDocVO_dateArvsBegan" datevalidations="partialDateForAll" value="${rawAcrfDoc.dateArvsBegan}" class="standardInputMedText dPicker mask datepicker maskDate" maxlength="15" title="Date" /> </span>
					<span class="mL100"> Date of last use: </span>
					<span class="mL5"> <form:input path="rawAcrfDoc.dateLastUsedArvs" id="rawTTHDocVO_dateLastUsedArvs" datevalidations="partialDateForAll" value="${rawAcrfDoc.dateLastUsedArvs}" class="standardInputMedText dPicker mask datepicker maskDate" maxlength="15" title="Date"/> </span>	
					<span class="mL5"><form:checkbox path="rawAcrfDoc.currentArvs" id="rawTTHDocVO_currentArvs" value="Y" title="Current Arvs" /> Current </span>
					<div class="v_error rawTTHDocVO_dateArvsBegan"></div>
					<div class="v_error rawTTHDocVO_dateLastUsedArvs"></div>
			</div>
		</c:if>
<%-- 	<c:if test="${acrfFormVO.rawAcrfDoc.isOldAcrf != 'Y'}"> --%>
	<c:if test="${acrfRevison > 2013}">
		<div style="width:99.9%; border:1px dotted;" class="container"></div>
		
		<div style="width:99%; margin-left:5px;  margin-right:5px; padding-bottom:5px">
			<div style=" width:99%; margin-right:5px; padding-bottom:5px"> 
				<div class="floatLeft"><span class="mL5"> If yes, reason for ARV use (select all that apply) </span></div></br>
				<c:set var="arvMedicationsIndex" value="0" />
				<c:forEach items="${acrfFormVO.arvMedicationsList}" var="arvMedicationsList" varStatus="arvUseTypeIndex">
					<div style="width:99%;  padding-top:3px; " class="arvMedicationsDiv" >
						<div class="floatLeft" style="width:10%">
							<span class="mL5"><form:checkbox path="arvMedicationsList[${arvUseTypeIndex.index}].arvUseType" class="arvUseType" id="arvMedicationsList${arvUseTypeIndex.index}_arvUseType" value="${arvMedicationsList.arvUseType}" checked="checked"/> ${arvMedicationsList.arvUseType} </span>
						</div>
						<c:if test="${arvMedicationsList.arvUseType =='OTHER' }">
							<div class="floatLeft" style="width:32%;">
								<span> <form:input path="arvMedicationsList[${arvUseTypeIndex.index}].otherArvUseType" elementType="text" id="arvMedicationsList${arvUseTypeIndex.index}_otherArvUseType" title="Arv other user type" class="standardInputText"/> </span>
							</div>
							<br/><br/>
							<div class="clearBoth" ></div>
							<div class="floatLeft" style="width:10%">
								&nbsp;
							</div>
						</c:if>
						<div class="floatLeft" style="width:32%; border-left:1px dotted;">
							<span class="mL5 valignTop">ARV medications:</span> 
								<form:select  path="arvMedicationsList[${arvUseTypeIndex.index}].arvMedications" id="arvMedicationsList${arvUseTypeIndex.index}_arvMedications" value="${fn:split(arvMedicationsList.arvMedications,',')}"  class="standardInputMedText" maxlength="200"  title="ARV medications" multiple="true" >
									<c:forEach items="${codedArvsMedications}" var="arvCategory">
										<form:option value="${arvCategory.LOOKUP_CD}">${arvCategory.ARV_DESC}</form:option>
									</c:forEach>
								</form:select> 
								
								<script type="text/javascript"> 
				 					arvMedications = '${arvMedicationsList.arvMedications}';
				 					$('#arvMedicationsList${arvUseTypeIndex.index}_arvMedications').val(arvMedications.split(','));
								</script>
						
						</div>
						<div class="floatLeft" style="width:26%; border-left:1px dotted;">
							<span class="mL5">Date began: <form:input path="arvMedicationsList[${arvUseTypeIndex.index}].arvMedicationsDateBegan" elementType="date" id="arvMedicationsList${arvUseTypeIndex.index}_arvMedicationsDateBegan" class="standardInputSmallText dPicker mask datepicker maskDate" datevalidations="partialDateForAll" title="Date began"/> </span>
						</div>
						<div class="floatLeft" style="width:26%; border-left:1px dotted;">
							<span class="mL5">Date of last use: <form:input path="arvMedicationsList[${arvUseTypeIndex.index}].arvMedicationsDateOfLastUse" elementType="date" id="arvMedicationsList${arvUseTypeIndex.index}_arvMedicationsDateOfLastUse" class="standardInputSmallText dPicker mask datepicker maskDate" datevalidations="partialDateForAll" title="Date of last use"/> </span>
						</div>
						<form:hidden id="arvMedicationsList${arvUseTypeIndex.index}_arvSrcId" path="arvMedicationsList[${arvUseTypeIndex.index}].arvSrcId" />
					</div>
					<div class="v_error arvMedicationsList${arvUseTypeIndex.index}_arvMedications"></div>
					<div class="v_error arvMedicationsList${arvUseTypeIndex.index}_otherArvUseType"></div>
					<div class="v_error arvMedicationsList${arvUseTypeIndex.index}_arvMedicationsDateBegan"></div>
					<div class="v_error arvMedicationsList${arvUseTypeIndex.index}_arvMedicationsDateOfLastUse"></div>
					<div class="container" style="width:99.9%; border:1px dotted;"></div>
					<c:set var="arvMedicationsIndex" value="${arvMedicationsIndex+1}" />
				</c:forEach>
				<c:forEach items="${arvUseTypeList}" var="arvUseType" varStatus="arvUseTypeIndex">
					<div style="width:99%;  padding-top:3px;" class="arvMedicationsDiv">
						<div class="floatLeft" style="width:10%">
							<span class="mL5"><form:checkbox path="arvMedicationsList[${arvMedicationsIndex}].arvUseType" class="arvUseType" id="arvMedicationsList${arvMedicationsIndex}_arvUseType" value="${arvUseType}" /> ${arvUseType} </span>
						</div>
						<c:if test="${arvUseType =='OTHER' }">
							<div class="floatLeft" style="width:32%;">
								<span> <form:input path="arvMedicationsList[${arvMedicationsIndex}].otherArvUseType" elementType="text" id="arvMedicationsList${arvMedicationsIndex}_otherArvUseType" title="Arv other user type" class="standardInputText"/> </span>
							</div>
							<br/><br/>
							<div class="clearBoth" ></div>
						
							<div class="floatLeft" style="width:10%">
								&nbsp;
							</div>
					    </c:if>
						<div class="floatLeft" style="width:32%; border-left:1px dotted;">
							<span class="mL5 valignTop">ARV medications: </span>
							<form:select path="arvMedicationsList[${arvMedicationsIndex}].arvMedications" id="arvMedicationsList${arvMedicationsIndex}_arvMedications"  class="standardInputMedText" maxlength="200"  title="ARV medications" multiple="true" >
								<c:forEach items="${codedArvsMedications}" var="arvCategory">
									<form:option value="${arvCategory.LOOKUP_CD}">${arvCategory.ARV_DESC}</form:option>
								</c:forEach>
							</form:select> 
						</div>
						<div class="floatLeft" style="width:26%; border-left:1px dotted;">
							<span class="mL5">Date began: <form:input path="arvMedicationsList[${arvMedicationsIndex}].arvMedicationsDateBegan" elementType="date" id="arvMedicationsList${arvMedicationsIndex}_arvMedicationsDateBegan" class="standardInputSmallText dPicker mask datepicker maskDate" datevalidations="partialDateForAll" title="Date began"/> </span>
						</div>
						<div class="floatLeft" style="width:26%; border-left:1px dotted;">
							<span class="mL5">Date of last use: <form:input path="arvMedicationsList[${arvMedicationsIndex}].arvMedicationsDateOfLastUse" elementType="date" id="arvMedicationsList${arvMedicationsIndex}_arvMedicationsDateOfLastUse" class="standardInputSmallText dPicker mask datepicker maskDate" datevalidations="partialDateForAll" title="Date of last use"/> </span>
						</div>
						<form:hidden id="arvMedicationsList${arvMedicationsIndex}_arvSrcId" path="arvMedicationsList[${arvMedicationsIndex}].arvSrcId" />
					</div><br/>
					<div class="v_error arvMedicationsList${arvMedicationsIndex}_arvMedications"></div>
					<div class="v_error arvMedicationsList${arvMedicationsIndex}_otherArvUseType"></div>
					<div class="v_error arvMedicationsList${arvMedicationsIndex}_arvMedicationsDateBegan"></div>
					<div class="v_error arvMedicationsList${arvMedicationsIndex}_arvMedicationsDateOfLastUse"></div>
					<div class="container" style="width:99.9%; border:1px dotted;"></div>
					
					<c:set var="arvMedicationsIndex" value="${arvMedicationsIndex+1}" />
				</c:forEach>
				
				
			</div>
		</div>
	</c:if>









</div>
<br/>
<span class="mL10 hdrFont"> XII. HIV Testing History (record all dates as mm/dd/yyyy) </span>
<div class="roundedBorderFont mL10" style="width:98%; background:#DDDDDD" >
	<div style="width:99%; margin-left:5px;  margin-right:5px; padding-bottom:5px">
		<div style="float:left; width:70%;"> 
			<span class="mL5"> Main source of testing history information (select one) </span></br>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.testTreatmentHistorySource" id="rawTTHDocVO_testTreatmentHistorySource" value="PT_INTV" /> Patient Interview </span>
			<c:choose>
				<c:when test="${(acrfFormVO.rawAcrfDoc.testTreatmentHistorySource != '' &&  acrfFormVO.rawAcrfDoc.testTreatmentHistorySource != null ) || acrfRevison > 2020}">
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.testTreatmentHistorySource" id="rawTTHDocVO_testTreatmentHistorySource" value="MED_REC"  /> Medical Record Review </span>
				</c:when>
				<c:otherwise>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.testTreatmentHistorySource" id="rawTTHDocVO_testTreatmentHistorySource" value="MED_REC" checked="true"/> Medical Record Review </span>
				</c:otherwise>
			</c:choose>
			
			
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.testTreatmentHistorySource" id="rawTTHDocVO_testTreatmentHistorySource" value="PROV_RPT"  /> Provider Report</span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.testTreatmentHistorySource" id="rawTTHDocVO_testTreatmentHistorySource" value="NHM_E_PEMS" /> NHM&E</span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.testTreatmentHistorySource" id="rawTTHDocVO_testTreatmentHistorySource" value="OTH"  /> Other</span>	
		</div>
		<div style="float:left; width:29%; border-left:1px dotted; padding-bottom:5px "> 
			<span class="mL20"> Date patient reported information </span></br>
			<span class="mL20"> <form:input path="rawAcrfDoc.dateReported" id="rawTTHDocVO_dateReported" datevalidations="partialDateForAll" value="${rawAcrfDoc.dateReported}" class="standardInputMedText dPicker mask datepicker maskDate" title="Date" /> </span>
			<div class="v_error rawTTHDocVO_dateReported mL20"></div>
		</div>		
	</div>
	<div style="width:99.9%; border:1px dotted;" class="container"></div>

	<div style="width:99%; margin-left:5px;  margin-right:5px; padding-bottom:5px;">
		<div>
			<div style="float:left; width:60%; padding-bottom:5px;" id="ever_had_previous_positive_hiv_test"> 
				<span class="mL5"> Ever had previous positive HIV test result? </span>
				<span class="mL20"><form:radiobutton path="rawAcrfDoc.prevPosHivTest" id="rawTTHDocVO_prevPosHivTest" value="Y" title="positive HIV" /> Yes </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.prevPosHivTest" id="rawTTHDocVO_prevPosHivTest" value="N" title="positive HIV" /> No </span>
	<%-- 			<c:if test="${acrfFormVO.rawAcrfDoc.isOldAcrf == 'Y'}"> --%>
				<c:if test="${acrfRevison == 2013}">
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.prevPosHivTest" id="rawTTHDocVO_prevPosHivTest" value="R" title="positive HIV" /> Refused</span>
				</c:if>	
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.prevPosHivTest" id="rawTTHDocVO_prevPosHivTest" value="U" title="positive HIV" /> Unknown</span>
				<div class="v_error ever_had_previous_positive_hiv_test"></div>		
			</div>
			<div style="float:left; width:39%; padding-bottom:5px; padding-top:5px;"> 
				<span class="mL20"> Date of first positive HIV test result</span>
				<span class="mL5"> <form:input path="rawAcrfDoc.dateFirstPosHivTest" id="rawTTHDocVO_dateFirstPosHivTest" datevalidations="partialDateForAll" value="${rawAcrfDoc.dateFirstPosHivTest}" class="standardInputMedText dPicker mask datepicker maskDate" maxlength="15" title="Date" /> </span>
				<div class="v_error rawTTHDocVO_dateFirstPosHivTest mL5"></div>
			</div>
		</div>
		<c:if test="${acrfRevison > 2022}">
			<div class="clearBoth mT10">
				<div style="float:left; width:80%; padding-bottom:5px;" > 
					<span class="mL5"> Was the first positive test result from a self-test performed by the patient? </span>
					<span class="mL20"><form:radiobutton path="rawAcrfDoc.posHivTestSelfTest" id="rawTTHDocVO_posHivTestSelfTest" value="Y" title="positive HIV result from a self test?" /> Yes </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.posHivTestSelfTest" id="rawTTHDocVO_posHivTestSelfTest" value="N" title="positive HIV result from a self test?" /> No </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.posHivTestSelfTest" id="rawTTHDocVO_posHivTestSelfTest" value="U" title="positive HIV result from a self test?" /> Unknown</span>
				</div>
			</div>	
		</c:if>	
	</div>
	<div style="width:99.9%; border:1px dotted;" class="container"></div>	
	
	<div style="width:99%; margin-left:5px;  margin-right:5px; padding-top:5px;" >
		<div>
			<div style="float:left; width:52%;" id="ever_had_negative_hiv_test"> 
				<span class="mL5"> Ever had a negative HIV test result? </span>
				<span class="mL20"><form:radiobutton path="rawAcrfDoc.negHivTest" id="rawTTHDocVO_negHivTest" value="Y" title="Negative HIV test" /> Yes </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.negHivTest" id="rawTTHDocVO_negHivTest" value="N" title="Negative HIV test" /> No </span>
	<%-- 			<c:if test="${acrfFormVO.rawAcrfDoc.isOldAcrf == 'Y'}"> --%>
				<c:if test="${acrfRevison == 2013}">
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.negHivTest" id="rawTTHDocVO_negHivTest" value="R" title="Negative HIV test" /> Refused</span>
				</c:if>	
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.negHivTest" id="rawTTHDocVO_negHivTest" value="U" title="Negative HIV test" /> Unknown</span>
				<div class="v_error ever_had_negative_hiv_test"></div>	
			</div>
			<div style="float:left; width:32%; "> 
				<span class="mL20"> Date of last negative HIV test result(if date is from </span></br>
				<span class="mL20">a lab test with test type, enter in Lab Data section) </span>			
			</div>
	
			<div style="float:left; width:16%;"> 
				<span class="mL5"> <form:input path="rawAcrfDoc.dateLastNegHivTest" id="rawTTHDocVO_dateLastNegHivTest" datevalidations="partialDateForAll" value="${rawAcrfDoc.dateLastNegHivTest}" class="standardInputSmallText dPicker mask datepicker maskDate" maxlength="15" title="Date" /> </span>
				<div class="v_error rawTTHDocVO_dateLastNegHivTest mL5"></div>
			</div>
		</div>
		<c:if test="${acrfRevison > 2022}">
			<div class="clearBoth mT10">
				<div style="float:left; width:80%; padding-bottom:5px;" > 
					<span class="mL5"> Was the last negative test result from a self-test performed by the patient? </span>
					<span class="mL20"><form:radiobutton path="rawAcrfDoc.negHivTestSelfTest" id="rawTTHDocVO_negHivTestSelfTest" value="Y" title="negative HIV result from a self test?" /> Yes </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.negHivTestSelfTest" id="rawTTHDocVO_negHivTestSelfTest" value="N" title="negative HIV result from a self test?" /> No </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.negHivTestSelfTest" id="rawTTHDocVO_negHivTestSelfTest" value="U" title="negative HIV result from a self test?" /> Unknown</span>
				</div>
			</div>	
		</c:if>				
	</div>
	<div style="width:99.9%; border:1px dotted;" class="container"></div>

	<div style="width:99%; margin-left:5px;  margin-right:5px; padding-top:5px; padding-bottom:5px;" >
		<div>
			<div class="floatLeft" style="width:60%">
				<span class="mL5"> Number of negative HIV test results within the 24 months before the first positive test result </span>
				<span class="mL5"> <form:input path="rawAcrfDoc.numNegTestBeforePos" id="rawTTHDocVO_numNegTestBeforePos" class="nOnly standardInputMedText " maxlength="5" title="Number of negative HIV tests"/> </span>
				<div class="v_error rawTTHDocVO_numNegTestBeforePos"></div>
			</div>
			<div id="number_of_negative_tests" class="floatLeft" style="width:30%">
<%-- 				<c:if test="${acrfFormVO.rawAcrfDoc.isOldAcrf == 'Y'}"> --%>
				<c:if test="${acrfRevison == 2013}">
					<span class="mL20"><form:radiobutton path="rawAcrfDoc.numNegTestUnkRef" id="rawTTHDocVO_numNegTestUnkRef" value="R" title="Negative HIV test Reference"/> Refused </span>
				</c:if>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.numNegTestUnkRef" id="rawTTHDocVO_numNegTestUnkRef" value="U" title="Negative HIV test Reference"/>Unknown</span>
				
				<div class="v_error number_of_negative_tests mL20"></div>
			</div>
		</div>
		<c:if test="${acrfRevison > 2022}">
			<div class="clearBoth mT10">
				<div class="floatLeft" style="width:60%">
					<span class="mL5"> How many of these negative test results were from self-tests performed by the patient? </span>
					<span class="mL5"> <form:input path="rawAcrfDoc.numNegTestBySelfTest" id="rawTTHDocVO_numNegTestBySelfTest" class="nOnly standardInputMedText " maxlength="5" title="Number of negative HIV tests from Self-tests"/> </span>
					<div class="v_error rawTTHDocVO_numNegTestBySelfTest"></div>
				</div>
				<div id="number_of_negative_self_tests" class="floatLeft" style="width:30%">
						<span class="mL5"><form:radiobutton path="rawAcrfDoc.numNegTestBySelfTestUnkRef" id="rawTTHDocVO_numNegTestBySelfTestUnkRef" value="U" title="number of Negative HIV test by Selft Test"/>Unknown</span>
					<div class="v_error rawTTHDocVO_numNegTestBySelfTestUnkRef mL20"></div>
				</div>
			</div>
		</c:if>				
	</div>
	<div style="width:99.9%; border:1px dotted;" class="container"></div>
</div>
<br/>

<span class="mL10 hdrFont"> XIII. Comments </span>
<div class="roundedBorder mL10" style="width:98%; height:187px; background:#DDDDDD; padding-left:2px;" >
	<span>  <form:textarea path="rawAcrfDoc.comments" id="rawAcrfDoc_comments" value="${acrfFormVO.rawAcrfDoc.comments}" ROWS="8" COLS="165" maxlength="255" title="Comments"/> </span>
	<div class="v_error rawAcrfDoc_comments"></div>
</div>

<br/>

<span class="mL10 hdrFont"> XIV. Local / Optional Fields </span>
<div class="roundedBorder mL10" style="width:98%; height:187px; background:#DDDDDD; padding-left:2px;" >
	<span>  <form:textarea path="rawAcrfDoc.localOptionalFields" id="rawAcrfDoc_localOptionalFields" value="${acrfFormVO.rawAcrfDoc.localOptionalFields}" ROWS="8" COLS="165" maxlength="4000" title="Comments"/> </span>
</div>
<c:if test="${acrfRevison > 2016}">
	<div style="width:98%; background:#DDDDDD" class="roundedBorder mL10 container mT10">
		<p class="mL20 mR10"> 
			This report to CDC is authorized by law (Sections 304 and 306 of the Public Health Service Act, 42 USC 242b and 242k). Response in this case is voluntary for federal government purposes, but may be mandatory under state and local statutes. Your cooperation is necessary for the understanding and control of HIV. Information in CDC's National HIV Surveillance System that would permit identification of any individual on whom a record is maintained, is collected with a guarantee that it will be held in confidence, will be used only for the purposes stated in the assurance on file at the local health department, and will not otherwise be disclosed or released without the consent of the individual in accordance with Section 308(d) of the Public Health Service Act (42 USC 242m).
			
		</p>
	</div>
</c:if>
<br/>