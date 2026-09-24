<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%-- This jsp belongs to "PT Info" First Tab  "HIV Diagnosis"--%> 
		<div style="float:left; width:100%; padding-bottom:2px;" class="container"> 
			<span><form:checkbox path="rawAcrfDoc.resPediatricSeroDxSameAsCurrent" id="rawAcrfDoc_resPediatricSeroDxSameAsCurrent" class="dxPediatricSeroSameAsCurrent" value="true"/> Check if <U>SAME as Current Address</U> </span>	
<%-- 			<span class="mL65"><form:checkbox path="rawAcrfDoc.resHivDx" id="rawAcrfDoc_resHivDx" class="resHivDx" value="SAMEASAIDS"/> SAME as AIDS Address</span>			 --%>
		</div>
		
		<div style="width:100%; border:1px dotted;" class="container"></div>
		
		<div id="resAddrsFieldsPediatricDx">
			<div style="float:left; width:99%; padding-bottom:5px; border-bottom:1px dotted;" class="mL5 mR5 mT5 container checkForValue">
				<span class="hdrFont"> Address Type </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.perinatalAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_perinatalAddressTypeCode"  value="RES" title="Address Type" /> Residential </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.perinatalAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_perinatalAddressTypeCode" value="BA" title="Address Type" /> Bad Address </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.perinatalAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_perinatalAddressTypeCode" value="CF" title="Address Type" /> Correctional Facility </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.perinatalAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_perinatalAddressTypeCode" value="FH" title="Address Type" /> Foster Home </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.perinatalAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_perinatalAddressTypeCode" value="HLES" title="Address Type" /> Homeless </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.perinatalAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_perinatalAddressTypeCode" value="MIL" title="Address Type" /> Military </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.perinatalAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_perinatalAddressTypeCode" value="OTH" title="Address Type" /> Other </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.perinatalAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_perinatalAddressTypeCode" value="POST" title="Address Type" /> Postal </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.perinatalAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_perinatalAddressTypeCode" value="SHEL" title="Address Type" /> Shelter </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.perinatalAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_perinatalAddressTypeCode" value="TEMP" title="Address Type" /> Temporary </span>
				
			</div>
			<div style="float:left; width:99%; padding-bottom:5px; border-bottom:1px dotted;" class="mL5 mR5 mT5 container checkForValue">
			
				<div style="float:left; width:50%; border-right:1px dotted;"> 
					<span class="hdrFont"> <spring:message code="label.streetAddress" /> </span>
					<span class="mL17"> <form:input path="rawAcrfDoc.resPediatricSeroAddress" id="rawAcrfDoc_resPediatricSeroAddress" class="standardInputText widgetAddressFieldsReadonly newAddress" addrsVar="streetAddrs" maxlength="62" title="Street Address"/> </span>
					<div class="v_error rawAcrfDoc_resPediatricSeroAddress"></div>
				</div>
				<div style="float:left; width:29%; border-right:1px dotted;"> 
					<span class="hdrFont mL5"> Address Date </span>
					<span class="mL17"> <form:input path="rawAcrfDoc.resPediatricSeroAddressDate" id="rawAcrfDoc_resPediatricSeroAddressDate" datevalidations="partialDate"  addrsVar="addressDate" class="standardInputSmallText dPicker mask" maxlength="10" title="Address Date"/> </span>
					<div class="v_error rawAcrfDoc_resPediatricSeroAddressDate"></div>
				</div>
				<div style="float:left; width:19%; "> 
					<span class="mL5"> <button value="Edit" onclick="openAddressWidget('resAddrsFieldsPediatricDx');return false;" class="editAddress">Edit Address</button> </span>
				</div>
			
			
			 
				
			</div>	
			<!-- <div style="width:100%; border:1px dotted;" class="container"></div> -->	
				
			<div style="float:left; width:99%; padding-bottom:5px; margin-left:5px; margin-right:5px;" class="container checkForValue" id="PediatricTabAddress"> 
				<div style="float:left; width:19%; border-right:1px dotted;"> 
					<span class="mL5 hdrFont"> <spring:message code="label.city" /></span><br/>
					<span class="mL5"> <form:input path="rawAcrfDoc.resPediatricSeroCity" id="rawAcrfDoc_resPediatricSeroCity" class="standardInputMedText widgetAddressFieldsReadonly newCity" addrsVar="cityAddrs" maxlength="30" title="City"/> </span>
					<div class="v_error rawAcrfDoc_resPediatricSeroCity"></div>
				</div>
				<div style="float:left; width:20%; border-right:1px dotted;"> 
					<span class="mL20 hdrFont"> <spring:message code="label.county" /></span><br/>
					<span class="mL20"> 
						<form:select path="rawAcrfDoc.resPediatricSeroCountyFips" id="rawAcrfDoc_resPediatricSeroCountyFips" class="standardInputMedText widgetAddressFieldsReadonly newCounty" addrsVar="countyAddrs" maxlength="3" title="County Fips" >
									<form:option value="" label="--select--"></form:option>
									<form:options items="${sessionScope.countyMap}"/>
								</form:select> 
					</span>
				</div>
				
				<div style="float:left; width:19%; border-right:1px dotted;"> 
					<span class="mL20 hdrFont"> <spring:message code="label.state" /></span><br/>
					<span class="mL20"> 
						<form:select path="rawAcrfDoc.resPediatricSeroState" id="rawAcrfDoc_resPediatricSeroState" class="standardInputSmallText widgetAddressFieldsReadonly newState" addrsVar="stateAddrs" maxlength="2" title="State" >
									<form:option value="" label="--select--"></form:option>
									<form:options items="${sessionScope.stateMap}"/>
								</form:select> 
					</span>
					<div class="v_error rawAcrfDoc_resHivState"></div>
				</div>
				<div style="float:left; width:20%; border-right:1px dotted;  ">
					<span class="mL20 hdrFont"> <spring:message code="label.country" /></span><br/>
					<span class="mL20"> <form:select path="rawAcrfDoc.resPediatricSeroCountry" id="rawAcrfDoc_resPediatricSeroCountry" class="standardInputSmallText  " addrsVar="countryAddrs" maxlength="7" title="Country">
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
					<span class="mL20"> <form:input path="rawAcrfDoc.resPediatricSeroZipcode" id="rawAcrfDoc_resPediatricSeroZipcode" class="standardInputSmallText2 widgetAddressFieldsReadonly newZip" addrsVar="zipAddrs" maxlength="5" title="Zip Code"/> </span>
					<span class="mL2"> <form:input path="rawAcrfDoc.resPediatricSeroPlus4" id="rawAcrfDoc_resPediatricSeroPlus4" class="standardInputSmallText1" addrsVar="plus4Addrs" maxlength="4" title="Plus4"/> </span>
					<div class="v_error rawAcrfDoc_resPediatricSeroZipcode"></div>
				</div>	
			</div>
			<div class="v_error PediatricTabAddress"> </div>
		</div>