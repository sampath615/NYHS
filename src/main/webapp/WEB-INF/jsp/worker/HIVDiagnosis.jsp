<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%-- This jsp belongs to "PT Info" First Tab  "HIV Diagnosis"--%> 
		<div style="float:left; width:100%; padding-bottom:2px;" class="container"> 
			<span><form:checkbox path="rawAcrfDoc.resDxSameAsCurrent" id="rawAcrfDoc_resDxSameAsCurrent" class="dxHivSameAsCurrent" value="true"/> Check if <U>SAME as Current Address</U> </span>
			<span class="mL65"><form:checkbox path="rawAcrfDoc.resHivDx" id="rawAcrfDoc_resHivDx" class="resHivDx" value="SAMEASAIDS"/> Same as AIDS Residence at Diagnosis</span>			
		</div>
		

		<div style="width:100%; border:1px dotted;" class="container"></div>
		
		<div id="resAddrsFieldsHIVDx">
			<div style="float:left; width:99%; padding-bottom:5px; border-bottom:1px dotted;" class="mL5 mR5 mT5 container checkForValue">
				<span class="hdrFont"> Address Type </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.hivAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_hivAddressTypeCode"  value="RES" title="Address Type" /> Residential </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.hivAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_hivAddressTypeCode" value="BA" title="Address Type" /> Bad Address </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.hivAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_hivAddressTypeCode" value="CF" title="Address Type" /> Correctional Facility </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.hivAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_hivAddressTypeCode" value="FH" title="Address Type" /> Foster Home </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.hivAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_hivAddressTypeCode" value="HLES" title="Address Type" /> Homeless </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.hivAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_hivAddressTypeCode" value="MIL" title="Address Type" /> Military </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.hivAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_hivAddressTypeCode" value="OTH" title="Address Type" /> Other </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.hivAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_hivAddressTypeCode" value="POST" title="Address Type" /> Postal </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.hivAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_hivAddressTypeCode" value="SHEL" title="Address Type" /> Shelter </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.hivAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_hivAddressTypeCode" value="TEMP" title="Address Type" /> Temporary </span>
				
			</div>
			<div style="float:left; width:99%; padding-bottom:5px; border-bottom:1px dotted;" class="mL5 mR5 mT5 container checkForValue">
			
				<div style="float:left; width:50%; border-right:1px dotted;"> 
					<span class="hdrFont"> <spring:message code="label.streetAddress" /> </span>
					<span class="mL17"> <form:input path="rawAcrfDoc.resHivAddress" id="rawAcrfDoc_resHivAddress" value="${acrfFormVO.rawAcrfDoc.resHivAddress}" class="standardInputText widgetAddressFieldsReadonly newAddress" addrsVar="streetAddrs" maxlength="62" title="Street Address"/> </span>
					<div class="v_error rawAcrfDoc_resHivAddress"></div>
				</div>
<%-- 				<c:if test="${acrfFormVO.rawAcrfDoc.isOldAcrf != 'Y'}"> --%>
				<c:if test="${acrfRevison > 2013 && acrfRevison < 2020}">
					<div style="float:left; width:29%; border-right:1px dotted;"> 
						<span class="hdrFont mL5"> Address Date </span>
						<span class="mL17"> <form:input path="rawAcrfDoc.resHivAddressDate" id="rawAcrfDoc_resHivAddressDate" datevalidations="partialDate" value="${acrfFormVO.rawAcrfDoc.resHivAddressDate}" addrsVar="addressDate" class="standardInputSmallText dPicker mask" maxlength="10" title="Address Date"/> </span>
						<div class="v_error rawAcrfDoc_resHivAddressDate"></div>
					</div>
				</c:if>
				<div style="float:left; width:19%; "> 
					<span class="mL5"> <button value="Edit" onclick="openAddressWidget('resAddrsFieldsHIVDx');return false;" class="editAddress">Edit Address</button> </span>
				</div>
			
			
			 
				
			</div>	
			<!-- <div style="width:100%; border:1px dotted;" class="container"></div> -->	
				
			<div style="float:left; width:99%; padding-bottom:5px; margin-left:5px; margin-right:5px;" class="container checkForValue" id="HIVTabAddress"> 
				<div style="float:left; width:19%; border-right:1px dotted;"> 
					<span class="mL5 hdrFont"> <spring:message code="label.city" /></span><br/>
					<span class="mL5"> <form:input path="rawAcrfDoc.resHivCity" id="rawAcrfDoc_resHivCity" value="${acrfFormVO.rawAcrfDoc.resHivCity}" class="standardInputMedText widgetAddressFieldsReadonly newCity" addrsVar="cityAddrs" maxlength="30" title="City"/> </span>
					<div class="v_error rawAcrfDoc_resHivCity"></div>
				</div>
				<div style="float:left; width:20%; border-right:1px dotted;"> 
					<span class="mL20 hdrFont"> <spring:message code="label.county" /></span><br/>
					<span class="mL20"> 
						<form:select path="rawAcrfDoc.resHivCountyFips" id="rawAcrfDoc_resHivCountyFips" value="${acrfFormVO.rawAcrfDoc.resHivCountyFips}" class="standardInputMedText widgetAddressFieldsReadonly newCounty" addrsVar="countyAddrs" maxlength="3" title="County Fips" >
									<form:option value="" label="--select--"></form:option>
									<form:options items="${sessionScope.countyMap}"/>
								</form:select> 
					</span>
				</div>
				
				<div style="float:left; width:19%; border-right:1px dotted;"> 
					<span class="mL20 hdrFont"> <spring:message code="label.state" /></span><br/>
					<span class="mL20"> 
						<form:select path="rawAcrfDoc.resHivState" id="rawAcrfDoc_resHivState" value="${acrfFormVO.rawAcrfDoc.resHivState}" class="standardInputSmallText widgetAddressFieldsReadonly newState" addrsVar="stateAddrs" maxlength="2" title="State" >
									<form:option value="" label="--select--"></form:option>
									<form:options items="${sessionScope.stateMap}"/>
								</form:select> 
					</span>
					<div class="v_error rawAcrfDoc_resHivState"></div>
				</div>
				<div style="float:left; width:20%; border-right:1px dotted;  ">
					<span class="mL20 hdrFont"> <spring:message code="label.country" /></span><br/>
					<span class="mL20"> <form:select path="rawAcrfDoc.resHivCountry" id="rawAcrfDoc_resHivCountry" value="${acrfFormVO.rawAcrfDoc.resHivCountry}" class="standardInputSmallText  " addrsVar="countryAddrs" maxlength="7" title="Country">
								<form:option value=""></form:option>
					<form:options items="${codedCountries}"/>
					<form:option value="OTH">Other</form:option>
					<form:option value="UNK">Unknown</form:option>
				</form:select> 
						 </span>
					<div class="v_error rawAcrfDoc_resHivCountry"></div>
				</div>
				
				<div style="float:left; width:21%;"> 
					<span class="mL20 hdrFont"> <spring:message code="label.zip" /></span> <br/>
					<span class="mL20"> <form:input path="rawAcrfDoc.resHivZipcode" id="rawAcrfDoc_resHivZipcode" value="${acrfFormVO.rawAcrfDoc.resHivZipcode}"  class="standardInputSmallText2 widgetAddressFieldsReadonly newZip" addrsVar="zipAddrs" maxlength="5" title="Zip Code"/> </span>
					<span class="mL2"> <form:input path="rawAcrfDoc.resHivPlus4" id="rawAcrfDoc_resHivPlus4" value="${acrfFormVO.rawAcrfDoc.resHivPlus4}" class="standardInputSmallText1" addrsVar="plus4Addrs" maxlength="4" title="Plus4"/> </span>
					<div class="v_error rawAcrfDoc_resHivZipcode"></div>
				</div>	
			</div>
			<div class="v_error HIVTabAddress"> </div>
		</div>