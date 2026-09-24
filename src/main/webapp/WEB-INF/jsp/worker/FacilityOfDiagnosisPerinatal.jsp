<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="facAddrsFieldsPerinatalDx">
	<div style="width:99%; margin-top:2px; margin-bottom:5px; margin-right:5px;">
		<span><form:checkbox path="rawAcrfDoc.facilityDxSameAsProvidingPerinatal" id="rawAcrfDoc_facilityDxSameAsProvidingPerinatal" class="fcltyPerinatalSameAsCurrent" value="true"/> Check if <U>SAME as Facility Providing Information</U> </span>
<%-- 		<span class="mL65"><form:checkbox path="rawAcrfDoc.facilityDxAids" id="rawAcrfDoc_facilityDxAids" class="facilityDxAids" value="SAMEASHIV"/> SAME as HIV Address</span> --%>
	</div>

	<div style="width:100%; border:1px dotted;" class="container"></div>

	<div style="width:100%; margin-top:5px; margin-right:5px;" class="checkForValue facilityDiagnosisPerinatalElements">
		<div style="float:left; width:70%; padding-bottom:5px;"> 
			<span class="hdrFont"> <spring:message code="label.facilityName" /></span>
			<span class="mL30"> 
				<form:select onchange="loadFacilityAddressDetails('#rawAcrfDoc_facilityPerinatalDx', this)" path="rawAcrfDoc.facilityPerinatalDxEharsUid" id="rawAcrfDoc_facilityPerinatalDxEharsUid"  addrsVar="facilityUid" class="standardInputText " maxlength="80" title="Facility Name">
					<form:option value="" >--select--</form:option>
					<c:forEach items="${subFacilities}" var="facility">
						<form:option value="${facility.facility_uid}" label="${facility.facility_name}" name="${facility.facility_name}"></form:option>
					</c:forEach>
				</form:select>  
				<form:hidden path="rawAcrfDoc.facilityPerinatalDxId" id="rawAcrfDoc_facilityPerinatalDxId" addrsVar="facilityId"/>
				<form:hidden path="rawAcrfDoc.facilityPerinatalDxName" id="rawAcrfDoc_facilityPerinatalDxName" addrsVar="facilityName"/>
			</span>
			<span ><a class="mailFacility" href="#"></a></span>
		</div>	
		<div style="float:right;width:29%; border-left:1px dotted; padding-bottom:5px;"> 
			<span class="mL20" > <spring:message code="label.phoneNumber" />: <form:input path="rawAcrfDoc.facilityPhonePerinatalDx" id="rawAcrfDoc_facilityPerinatalDxPhone" addrsVar="facilityPhone"  class="pNumber standardInputMedText mL5 readonly" maxlength="20"  title="Phone Number"/> </span>
			<div class="v_error rawAcrfDoc_facilityPhonePerinatalDx mL20"></div>
		</div>
	</div>

	<div style="width:100%; border:1px dotted; " class="container"></div>

	<div id="PTHistAIDS" class="facilityDiagnosisAidsElements">
		<div style="float:left; width:99%; padding-bottom:5px; border-bottom:1px dotted;" class="mR5 mT5 checkForValue"> 
			<span class="hdrFont"> *<spring:message code="label.streetAddress" /> </span>
			<span class="mL17"> <form:input path="rawAcrfDoc.facilityAddressPerinatalDx" id="rawAcrfDoc_facilityPerinatalDxAddress" class="standardInputText readonly"  addrsVar="streetAddrs" maxlength="100"  title="Street Address"/> </span>
		</div>	
	
		<!-- <div style="width:100%; border:1px dotted; " class="container"></div> -->
	
		<div style="float:left; width:99%; margin-right:5px;" class="checkForValue"> 
			<div style="float:left; width:19%; border-right:1px dotted;  padding-bottom:5px;"> 
				<span class="mL5 hdrFont"> <spring:message code="label.city" /></span><br/>
				<span class="mL5"> <form:input path="rawAcrfDoc.facilityCityPerinatalDx" id="rawAcrfDoc_facilityPerinatalDxCity" class="standardInputMedText readonly" addrsVar="cityAddrs" maxlength="30"  title="City" /> </span>
			</div>
			<div style="float:left; width:20%; border-right:1px dotted;  padding-bottom:7px;"> 
				<span class="mL20 hdrFont"> <spring:message code="label.county" /></span> <br/>
				<span class="mL20"> 
					<form:select path="rawAcrfDoc.facilityCountyFipsPerinatalDx" id="rawAcrfDoc_facilityPerinatalDxCountyFips" class="standardInputSmallText readonly" maxlength="3" title="County Fips" addrsVar="countyAddrs" >
									<form:option value="" label="--select--"></form:option>
									<form:options items="${sessionScope.countyMap}"/>
								</form:select>	 
				</span>
			</div>
			
			<div style="float:left; width:20%; border-right:1px dotted;  padding-bottom:7px;"> 
				<span class="mL20 hdrFont"> <spring:message code="label.state" /></span><br/>
				<span class="mL20"> 
					<form:select path="rawAcrfDoc.facilityStatePerinatalDx" id="rawAcrfDoc_facilityPerinatalDxState" class="standardInputSmallText2 readonly" addrsVar="stateAddrs" maxlength="2"  title="State" >
									<form:option value="" label="--select--"></form:option>
									<form:options items="${sessionScope.stateMap}"/>
								</form:select>	 
				</span>
			</div>
			<div style="float:left; width:19%; border-right:1px dotted;  padding-bottom:7px;"> 	
				<span class="mL20 hdrFont"> <spring:message code="label.country" /></span><br/>
				<span class="mL20"> <form:select path="rawAcrfDoc.facilityCountryPerinatalDx" id="rawAcrfDoc_facilityPerinatalDxCountry" class="standardInputSmallText readonly" addrsVar="countryAddrs" maxlength="7"  title="Country" >
				<form:option value=""></form:option>
					<form:options items="${codedCountries}"/>
					<form:option value="OTH">Other</form:option>
					<form:option value="UNK">Unknown</form:option>
				</form:select>  </span>
				
			</div>
			
			<div style="float:left; width:21%; padding-bottom:7px;"> 
				<span class="mL20 hdrFont"> <spring:message code="label.zip" /></span> <br/>
				<span class="mL20"> <form:input path="rawAcrfDoc.facilityZipcodePerinatalDx" id="rawAcrfDoc_facilityPerinatalDxZipcode" class="standardInputSmallText2 readonly" addrsVar="zipAddrs" maxlength="5"  title="Zip Code" /> </span>
				<span class="mL2"> <form:input path="rawAcrfDoc.facilityPlus4PerinatalDx" id="rawAcrfDoc_facilityPerinatalDxPlus4" class="standardInputSmallText1 readonly" addrsVar="plus4Addrs" maxlength="4"  title="Plus4" /> </span>			
			</div>	
		</div>
	</div>
	
	<div style="width:100%; border:1px dotted; " class="container"></div>

	<div id="PTHistAIDSRadio" style="margin-right:7px;">
		<div style="padding-bottom:5px;">
			<span class="hdrFont"> Facility Type</span>  <form:input class="standardInputText readonly" path="rawAcrfDoc.facilityPerinatalDxTypeCode" id="rawAcrfDoc_facilityPerinatalDxTypeCode" addrsVar="facilityTypeCD" title="Facility Type"/>
		</div>		
	</div>	

	<div style="width:100%; border:1px dotted; " class="container"></div>

	<div style="width:99%; padding-bottom:5px; padding-top:2px; "  class="mR5" >
		<div style="float:left; width:66%; "> 			
			<div >
				<span class="hdrFont"> Provider Name : </span>
			</div>
			<div style="float:left; width: 180px">
				<span class=" hdrFont"> <spring:message code="label.firstname" /></span>  <br/> <span class="mL10 "> <form:input path="rawAcrfDoc.providerFnamePerinatalDx" id="rawAcrfDoc.providerFnamePerinatalDx" class="standardInputSmallText " cssErrorClass="error" maxlength="25" title="First Name"/> </span>
			</div>
			<div style="float:left; width: 180px">
				<span class="mL10 hdrFont"> <spring:message code="label.middleName" /></span> <br/> <span class="mL10 "> <form:input path="rawAcrfDoc.providerMnamePerinatalDx" id="rawAcrfDoc.providerMnamePerinatalDx"  class="standardInputSmallText" cssErrorClass="error" maxlength="25"  title="Middle Name"/> </span>
			</div>
			<div style="float:left">
				<span class="mL10 hdrFont"> <spring:message code="label.lastname" /></span> <br/> <span class="mL10 "> <form:input path="rawAcrfDoc.providerLnamePerinatalDx" id="rawAcrfDoc.providerLnamePerinatalDx" class="standardInputSmallText" cssErrorClass="error" maxlength="25"  title="Last Name"/> </span>
			</div>		
			<div style="float:left">
				<span class="mL10 hdrFont"> <spring:message code="label.suffix" /></span> <br/> <span class="mL10 "> <form:input path="rawAcrfDoc.providerSuffixPerinatalDx" id="rawAcrfDoc.providerSuffixPerinatalDx" class="standardInputSmallText1" cssErrorClass="error" maxlength="10" title="Suffix"/> </span>
			</div>			
		</div>
		<div class="mL5" style="float:left; width:16%; border-right:1px dotted; border-left:1px dotted;"> 
			<span>&nbsp;</span>
			<span class=" hdrFont" > Provider Phone </span><form:input path="rawAcrfDoc.providerPhonePerinatalDx" id="rawAcrfDoc_providerPhonePerinatalDx"  class="pNumber standardInputSmallText mL5 font10pt mask" maxlength="20" title="Provider Phone"/>  
		</div>
		<div style="float:left; width:17%;"> 
			<span class="hdrFont mL20"> Specialty </span><br/>
			<span class="mL20"> 
				<form:select path="rawAcrfDoc.providerSpecialtyPerinatalDx" id="rawAcrfDoc.providerSpecialtyPerinatalDx" class="standardInputSmallText" maxlength="50" title="Specialty">
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
	