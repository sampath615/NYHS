<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="facAddrsFieldsHIVDx">
	<div style="width:99%; margin-top:2px; margin-bottom:5px; margin-right:5px;">
		<span><form:checkbox path="rawAcrfDoc.facilityDxSameAsProviding" id="rawAcrfDoc_facilityDxSameAsProvidingHiv" class="fcltyHivSameAsCurrent" value="true"/> Check if <U>SAME as Facility Providing Information</U> </span>
		<span class="mL65"><form:checkbox path="rawAcrfDoc.faciltyDxHiv" id="rawAcrfDoc_faciltyDxHiv" class="faciltyDxHiv" value="SAMEASAIDS"/>Same as AIDS Diagnosing Facility Address</span>
	</div>

	<div style="width:100%; border:1px dotted;" class="container"></div>


	<div style="width:100%; margin-top:5px; margin-right:5px;" class="checkForValue facilityDiagnosisHivElements">
		<div style="float:left; width:70%; padding-bottom:5px;"> 
			<span class="hdrFont"> <spring:message code="label.facilityName" /></span>
			<span class="mL30"> 
				<form:select onchange="loadFacilityAddressDetails('#rawAcrfDoc_facilityHivDx', this)" path="rawAcrfDoc.facilityHivDxEharsUid" id="rawAcrfDoc_facilityHivDxEharsUid" value="${acrfFormVO.rawAcrfDoc.facilityHivDxName}" addrsVar="facilityUid" class="standardInputText " maxlength="80" title="Facility Name">
					<form:option value="" >--select--</form:option>
					<c:forEach items="${subFacilities}" var="facility">
						<form:option value="${facility.facility_uid}" label="${facility.facility_name}" name="${facility.facility_name}"></form:option>
					</c:forEach>
				</form:select> 
				<form:hidden path="rawAcrfDoc.facilityHivDxId" id="rawAcrfDoc_facilityHivDxId" addrsVar="facilityId"/>
				<form:hidden path="rawAcrfDoc.facilityHivDxName" id="rawAcrfDoc_facilityHivDxName" addrsVar="facilityName"/>
			 </span>
			 <span ><a class="mailFacility" href="#"></a></span>
		</div>	
		<div style="float:right;width:29%; border-left:1px dotted; padding-bottom:5px;"> 
			<span class="mL20" > <spring:message code="label.phoneNumber" /> <form:input path="rawAcrfDoc.facilityPhoneHivDx" id="rawAcrfDoc_facilityHivDxPhone"  value="${acrfFormVO.rawAcrfDoc.facilityPhoneHivDx}" class="standardInputMedText mL5 readonly" addrsVar="facilityPhone" maxlength="20" title="Phone Number"/> </span>
			<div class="v_error rawAcrfDoc_facilityPhoneHivDx mL20"></div>
		</div>
	</div>
	
	<div style="width:100%; border:1px dotted; " class="container"></div>
	
		<div style="float:left; width:99%; padding-bottom:5px; border-bottom:1px dotted;" class="mR5 mT5 checkForValue facilityDiagnosisHivElements"> 
			<span class="hdrFont"> *<spring:message code="label.streetAddress" /> </span>
			<span class="mL17"> <form:input path="rawAcrfDoc.facilityAddressHivDx" id="rawAcrfDoc_facilityHivDxAddress" value="${acrfFormVO.rawAcrfDoc.facilityAddressHivDx}" class="standardInputText readonly" addrsVar="streetAddrs" maxlength="100" title="Street Address"/> </span>
			<div class="v_error rawAcrfDoc_facilityHivDxAddress"></div>
		</div>	
	
		<!-- <div style="width:100%; border:1px dotted; " class="container"></div> -->
	
		<div style="float:left; width:99%; margin-right:5px;" class="checkForValue facilityDiagnosisHivElements"> 
			<div style="float:left; width:19%; border-right:1px dotted;  padding-bottom:5px;"> 
				<span class="mL5 hdrFont"> <spring:message code="label.city" /></span><br/>
				<span class="mL5"> <form:input path="rawAcrfDoc.facilityCityHivDx" id="rawAcrfDoc_facilityHivDxCity" value="${acrfFormVO.rawAcrfDoc.facilityCityHivDx}" class="standardInputMedText readonly" addrsVar="cityAddrs" maxlength="30" title="City" /> </span>
			</div>
			<div style="float:left; width:20%; border-right:1px dotted;  padding-bottom:7px;"> 
				<span class="mL20 hdrFont"> <spring:message code="label.county" /></span><br/>
				<span class="mL20"> 
					<form:select path="rawAcrfDoc.facilityCountyFipsHivDx" id="rawAcrfDoc_facilityHivDxCountyFips" value="${acrfFormVO.rawAcrfDoc.facilityCountyFipsHivDx}" class="standardInputSmallText readonly" addrsVar="countyAddrs" maxlength="3" title="County Fips" >
									<form:option value="" label="--select--"></form:option>
									<form:options items="${sessionScope.countyMap}"/>
								</form:select>	  
				</span>
			</div>
			
			<div style="float:left; width:20%; border-right:1px dotted;  padding-bottom:7px;"> 
				<span class="mL20 hdrFont"> <spring:message code="label.state" /></span><br/>
				<span class="mL20"> 
					<form:select path="rawAcrfDoc.facilityStateHivDx" id="rawAcrfDoc_facilityHivDxState" value="${acrfFormVO.rawAcrfDoc.facilityStateHivDx}" class="standardInputSmallText readonly" addrsVar="stateAddrs" maxlength="2" title="State" >
									<form:option value="" label="--select--"></form:option>
									<form:options items="${sessionScope.stateMap}"/>
								</form:select>	
					 
				</span>
			</div>
			<div style="float:left; width:19%; border-right:1px dotted;  padding-bottom:7px;">
				<span class="mL20 hdrFont"> <spring:message code="label.country" /></span><br/>
				<span class="mL20"> <form:select path="rawAcrfDoc.facilityCountryHivDx" id="rawAcrfDoc_facilityHivDxCountry" value="${acrfFormVO.rawAcrfDoc.facilityCountryHivDx}" class="standardInputSmallText readonly" addrsVar="countryAddrs" maxlength="7" title="Country" >
				<form:option value=""></form:option>
					<form:options items="${codedCountries}"/>
					<form:option value="OTH">Other</form:option>
					<form:option value="UNK">Unknown</form:option>
				</form:select>  </span>
				
			</div>
			
			<div style="float:left; width:21%; padding-bottom:7px;"> 
				<span class="mL20 hdrFont"> <spring:message code="label.zip" /></span><br/>
				<span class="mL20"> <form:input path="rawAcrfDoc.facilityZipcodeHivDx" id="rawAcrfDoc_facilityHivDxZipcode" value="${acrfFormVO.rawAcrfDoc.facilityZipcodeHivDx}" class="standardInputSmallText2 readonly" addrsVar="zipAddrs" maxlength="5" title="Zip Code" /> </span>
				<span class="mL2"> <form:input path="rawAcrfDoc.facilityPlus4HivDx" id="rawAcrfDoc_facilityHivDxPlus4" value="${acrfFormVO.rawAcrfDoc.facilityPlus4HivDx}" class="standardInputSmallText1 readonly" addrsVar="plus4Addrs" maxlength="4" title="plus4" /> </span>			
			</div>	
		</div>

	<div style="width:100%; border:1px dotted; " class="container"></div>

	<div style="margin-right:7px;">
		<div style="padding-bottom:5px;">
			<span class="hdrFont"> Facility Type</span>  <form:input class="standardInputText readonly" path="rawAcrfDoc.facilityHivDxTypeCode" id="rawAcrfDoc_facilityHivDxTypeCode" value="${acrfFormVO.rawAcrfDoc.facilityHivDxTypeCode}" addrsVar="facilityTypeCD" title="Facility Type"/>
		</div>	
	</div>	


	<div style="width:100%; border:1px dotted; " class="container"></div>

	<div style="width:99%; padding-bottom:5px; padding-top:2px;"  class="mR5" >
		<div style="float:left; width:66%;"> 
			<div>
				<span class="hdrFont"> Provider Name :</span>
			</div>
			<div style="float:left; width: 180px">
				<span class="hdrFont"> <spring:message code="label.firstname" /></span>  <br/> <span class="mL10 "> <form:input path="rawAcrfDoc.providerFnameHivDx" id="rawAcrfDoc_providerFnameHivDx" value="${acrfFormVO.rawAcrfDoc.providerFnameHivDx}" class="standardInputSmallText " cssErrorClass="error" maxlength="25" title="First Name"/> </span>
				<div class="v_error rawAcrfDoc_providerFnameHivDx mL10"></div>
			</div>
			<div style="float:left; width: 180px">
				<span class="mL10 hdrFont"> <spring:message code="label.middleName" /></span> <br/> <span class="mL10 "> <form:input path="rawAcrfDoc.providerMnameHivDx" id="rawAcrfDoc_providerMnameHivDx"  value="${acrfFormVO.rawAcrfDoc.providerMnameHivDx}" class="standardInputSmallText" cssErrorClass="error" maxlength="25" title="Middle Name"/> </span>
				<div class="v_error rawAcrfDoc_providerMnameHivDx mL10"></div>
			</div>
			<div style="float:left; width: 180px">
				<span class="mL10 hdrFont"> <spring:message code="label.lastname" /></span> <br/> <span class="mL10 "> <form:input path="rawAcrfDoc.providerLnameHivDx" id="rawAcrfDoc_providerLnameHivDx" value="${acrfFormVO.rawAcrfDoc.providerLnameHivDx}" class="standardInputSmallText" cssErrorClass="error" maxlength="25" title="Last Name"/> </span>
				<div class="v_error rawAcrfDoc_providerLnameHivDx mL10"></div>
			</div>		
			<div style="float:left">
				<span class="mL10 hdrFont"> <spring:message code="label.suffix" /></span> <br/> <span class="mL10 "> <form:input path="rawAcrfDoc.providerSuffixHivDx"  id="rawAcrfDoc_providerSuffixHivDx" value="${acrfFormVO.rawAcrfDoc.providerSuffixHivDx}" class="standardInputSmallText1" cssErrorClass="error" maxlength="10" title="Suffix"/> </span>
				<div class="v_error rawAcrfDoc_providerSuffixHivDx mL10"></div>
			</div>
			
		</div>
		<div class="mL5" style="float:left; width:16%; border-right:1px dotted; border-left:1px dotted;"> 
			<span>&nbsp;</span>
			<span class=" hdrFont" > Provider Phone </span> <form:input path="rawAcrfDoc.providerPhoneHivDx" id="rawAcrfDoc_providerPhoneHivDx"  value="${acrfFormVO.rawAcrfDoc.providerPhoneHivDx}" class="standardInputSmallText mL5 font10pt mask" maxlength="20" title="Provider Phone"/>  
		</div>
		<div style="float:left; width:17%;"> 
			<span class="hdrFont mL20">Specialty</span><br/>
			<span class="mL20"> 
				<form:select path="rawAcrfDoc.providerSpecialty" value="${acrfFormVO.rawAcrfDoc.providerSpecialty}" class="standardInputSmallText" maxlength="50" title="Specialty">
					<form:option value="" >--select--</form:option>
					<c:forEach items="${providerSpecialities}" var="speciality">
						<form:option value="${speciality.lookup_cd}" label="${speciality.lookup_name}" name="${speciality.lookup_name}"></form:option>
					</c:forEach>
				</form:select>
			
			 </span>
		</div>	
	</div>
	<div style="width:100%; " class="container"></div>
</div>
