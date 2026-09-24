<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- This jsp belongs to "TTH" Fourth Tab --%> 
<!-- page4 first block starts -->

<span class="mL10 hdrFont"> X. Birth History (for patients exposed perinatally with or without consequent infection) </span>

<div class="roundedBorderFont mL10" style="width:98%; background:#DDDDDD" >

	<div style="float:left; width:99%;" class="container checkForValue pB5"> 
		<div style="float:left; width:29%; border-right:1px dotted;   background-color:#DBEAF9;"> 
					<span class="hdrFont "> Residence at Birth</span>
		</div>
		<div style="float:left; width:40%; border-right:1px dotted;  "> 
				<span class="mL5"> Birth History Available </span>
				<span class="mL10"><form:radiobutton path="rawAcrfDoc.birthHistAvailable" id="rawAcrfDoc_birthHistAvailable" value="Y" title="Birth History Available" /> Yes </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthHistAvailable" id="rawAcrfDoc_birthHistAvailable" value="N" title="Birth History Available" /> No </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthHistAvailable" id="rawAcrfDoc_birthHistAvailable" value="U" title="Birth History Available" /> Unknown</span>
				<div class="v_error patient_birthHistAvailable"></div>	
		</div>
		<div style="float:left; width:30%; "> 
				<span><form:checkbox path="rawAcrfDoc.resBthSameAsCurrent" id="rawAcrfDoc_resBthSameAsCurrent" class="resBthSameAsCurrent" value="true"/> Check if <U>SAME</U> as Current Address</span>	
		</div>
	</div>
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	
			
	<div id="resAddrsFieldsBthDx">
			<div style="float:left; width:99%; padding-bottom:5px; border-bottom:1px dotted;" class="mL5 mR5 mT5 container checkForValue">
				<span class="hdrFont"> Address Type </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.bthAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_bthAddressTypeCode"  value="RES" title="Address Type" /> Residential </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.bthAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_bthAddressTypeCode" value="BA" title="Address Type" /> Bad Address </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.bthAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_bthAddressTypeCode" value="CF" title="Address Type" /> Correctional Facility </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.bthAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_bthAddressTypeCode" value="FH" title="Address Type" /> Foster Home </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.bthAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_bthAddressTypeCode" value="HLES" title="Address Type" /> Homeless </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.bthAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_bthAddressTypeCode" value="MIL" title="Address Type" /> Military </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.bthAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_bthAddressTypeCode" value="OTH" title="Address Type" /> Other </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.bthAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_bthAddressTypeCode" value="POST" title="Address Type" /> Postal </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.bthAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_bthAddressTypeCode" value="SHEL" title="Address Type" /> Shelter </span>
				<span class="chkboxLabelfont mL10"> <form:radiobutton path="rawAcrfDoc.bthAddressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_bthAddressTypeCode" value="TEMP" title="Address Type" /> Temporary </span>
				
			</div>
		
				<div style="float:left; width:99%; " class="pB5 container checkForValue">
						
							<div style="float:left; width:50%; border-right:1px dotted;"> 
								<span class="hdrFont"> <spring:message code="label.streetAddress" /> </span>
								<span class="mL17"> <form:input path="rawAcrfDoc.resBthAddress" id="rawAcrfDoc_resBthAddress" class="standardInputText widgetAddressFieldsReadonly newAddress" addrsVar="streetAddrs" maxlength="62" title="Street Address"/> </span>
								<div class="v_error rawAcrfDoc_resBthAddress"></div>
							</div>
			
							<div style="float:left; width:19%; "> 
								<span class="mL5"> <button value="Edit" onclick="openAddressWidget('resAddrsFieldsBthDx');return false;" class="editAddress">Edit Address</button> </span>
							</div>
						
						
						 
							
				</div>	
				
				<div style="width:99.9%; border:1px dotted; " class="container"></div>		
			
				<div style="float:left; width:99%;" class="container" id="birthresidenceAddress"> 
							<div style="float:left; width:19%; border-right:1px dotted;"> 
								<span class="mL5 hdrFont"> <spring:message code="label.city" /></span><br/>
								<span class="mL5"> <form:input path="rawAcrfDoc.resBthCity" id="rawAcrfDoc_resBthCity" class="standardInputMedText widgetAddressFieldsReadonly newCity" addrsVar="cityAddrs" maxlength="30" title="City"/> </span>
								<div class="v_error rawAcrfDoc_resBthCity"></div>
							</div>
							<div style="float:left; width:20%; border-right:1px dotted;"> 
								<span class="mL20 hdrFont"> <spring:message code="label.county" /></span><br/>
								<span class="mL20"> 
									<form:select path="rawAcrfDoc.resBthCountyFips" id="rawAcrfDoc_resBthCountyFips" class="standardInputMedText widgetAddressFieldsReadonly newCounty" addrsVar="countyAddrs" maxlength="3" title="County Fips" >
												<form:option value="" label="--select--"></form:option>
												<form:options items="${sessionScope.countyMap}"/>
											</form:select> 
								</span>
							</div>
							
							<div style="float:left; width:19%; border-right:1px dotted;"> 
								<span class="mL20 hdrFont"> <spring:message code="label.state" /></span><br/>
								<span class="mL20"> 
									<form:select path="rawAcrfDoc.resBthState" id="rawAcrfDoc_resBthState" class="standardInputSmallText widgetAddressFieldsReadonly newState" addrsVar="stateAddrs" maxlength="2" title="State" >
												<form:option value="" label="--select--"></form:option>
												<form:options items="${sessionScope.stateMap}"/>
											</form:select> 
								</span>
								<div class="v_error rawAcrfDoc_resBthState"></div>
							</div>
							<div style="float:left; width:20%; border-right:1px dotted;  ">
								<span class="mL20 hdrFont"> <spring:message code="label.country" /></span><br/>
								<span class="mL20"> <form:select path="rawAcrfDoc.resBthCountry" id="rawAcrfDoc_resBthCountry" class="standardInputSmallText  " addrsVar="countryAddrs" maxlength="7" title="Country">
											<form:option value=""></form:option>
								<form:options items="${codedCountries}"/>
								<form:option value="OTH">Other</form:option>
								<form:option value="UNK">Unknown</form:option>
							</form:select> 
									 </span>
								<div class="v_error rawAcrfDoc_resBthCountry"></div>
							</div>
							
							<div style="float:left; width:21%;"> 
								<span class="mL20 hdrFont"> <spring:message code="label.zip" /></span> <br/>
								<span class="mL20"> <form:input path="rawAcrfDoc.resBthZipcode" id="rawAcrfDoc_resBthZipcode" class="standardInputSmallText2 widgetAddressFieldsReadonly newZip" addrsVar="zipAddrs" maxlength="5" title="Zip Code"/> </span>
								<span class="mL2"> <form:input path="rawAcrfDoc.resBthPlus4" id="rawAcrfDoc_resBthPlus4" class="standardInputSmallText1" addrsVar="plus4Addrs" maxlength="4" title="Plus4"/> </span>
								<div class="v_error rawAcrfDoc_resBthZipcode"></div>
							</div>	
						</div>
			</div>
			<div style="width:99.9%; border:1px dotted; " class="container"></div>
			
			<div id="facAddrsFieldsBthDx">
			
			
					<div style="float:left; width:99%;" class="container checkForValue pB5"> 
						<div style="float:left; width:29%; border-right:1px dotted;   background-color:#DBEAF9;"> 
									<span class="hdrFont "> Facility of Birth</span>
						</div>
						<div style="float:left; width:70%;  "> 
								<span><form:checkbox path="rawAcrfDoc.facilityAtBthSameAsProviding" id="rawAcrfDoc_facilityAtBthSameAsProviding" class="fcltyBthSameAsCurrent" value="true"/> Check if <U>SAME</U> as facility providing information</span>	
						</div>
					</div>
					
			
		
			
			
			
					<div style="width:99.9%; border:1px dotted; " class="container"></div>
			
			
			
					<div style="width:99%; margin-top:5px; margin-left:5px;  margin-right:5px;" class="container checkForValue pB5">
						<div style="float:left; width:68%; "> 
							<div>
								<span class="hdrFont"> Facility Name of Birth</span>
								<span class="mL30"> 
								<form:select onchange="loadFacilityAddressDetails('#rawAcrfDoc_facilityAtBthDx', this)" path="rawAcrfDoc.facilityAtBthDxEharsUid" id="rawAcrfDoc_facilityAtBthDxEharsUid"  addrsVar="facilityUid" class="standardInputText " maxlength="80" title="Facility Name">
									<form:option value="" >--select--</form:option>
									<c:forEach items="${subFacilities}" var="facility">
										<form:option value="${facility.facility_uid}" label="${facility.facility_name}" name="${facility.facility_name}"></form:option>
									</c:forEach>
								</form:select> 
				<%-- 				<form:hidden path="rawAcrfDoc.providingFacilityId" id="rawAcrfDoc_providingFacilityId" addrsVar="facilityId"/> --%>
								<form:hidden path="rawAcrfDoc.facilityAtBthDxName" id="rawAcrfDoc_facilityAtBthDxName" addrsVar="facilityName"/>		
								
								</span>
								<span ><a class="mailFacility" href="#"></a></span>
							</div>
							<span>(if child was born at home, enter "home birth")</span>	
							
							<div class="v_error rawAcrfDoc_facilityAtBthDxEharsUid"></div>
						</div>	
						<div style="float:left; border-left:1px dotted; "> 
							<span class="hdrFont mL20" style="text-align:center; "> <spring:message code="label.phoneNumber" /></span> <form:input path="rawAcrfDoc.facilityPhoneAtBthDx" id="rawAcrfDoc_facilityAtBthDxPhone" addrsVar="facilityPhone" class="standardInputMedText pNumber font10pt readonly" maxlength="20" title="Phone Number"/>  
						</div>
					</div>
		
					<div style="width:99.9%; border:1px dotted; " class="container"></div>
				
					<div style="float:left; width:99%; " class="mL5 mR5 mT5 checkForValue pB5"> 
						<span class="hdrFont"> *<spring:message code="label.streetAddress" /></span>
						<span class="mL17"> <form:input path="rawAcrfDoc.facilityAddressAtBthDx" id="rawAcrfDoc_facilityAtBthDxAddress" class="standardInputText readonly" addrsVar="streetAddrs" maxlength="100" title="Street Address" /> </span>
						<div class="v_error rawAcrfDoc_facilityAddressAtBthDx"></div>
					</div>	
		
					<div style="width:99.9%; border:1px dotted; " class="container"></div>
		
					<div style="float:left; width:99%; margin-left:5px; margin-right:5px; margin-top:2px;" class="container checkForValue pB5"> 
						<div style="float:left; width:19%; border-right:1px dotted;  "> 
							<span class="mL5 hdrFont"> <spring:message code="label.city" /> </span>
							<span class="mL5"> <form:input path="rawAcrfDoc.facilityCityAtBthDx" id="rawAcrfDoc_facilityAtBthDxCity" class="standardInputSmallText readonly" addrsVar="cityAddrs" maxlength="30" title="City" /> </span>
							<div class="v_error rawAcrfDoc_facilityCityAtBthDx"></div>
						</div>
						<div style="float:left; width:22%; border-right:1px dotted;  "> 
							<span class="mL10 hdrFont"> <spring:message code="label.county" /> </span> 
							<span class="mL5"> 
							<form:select path="rawAcrfDoc.facilityCountyFipsAtBthDx" id="rawAcrfDoc_facilityAtBthDxCountyFips" class="standardInputSmallText readonly" addrsVar="countyAddrs" maxlength="3" title="County Fips" >
														<form:option value="" label="--select--"></form:option>
														<form:options items="${sessionScope.countyMap}"/>
													</form:select>
							</span>
						</div>
						
						<div style="float:left; width:15%; border-right:1px dotted;  "> 
							<span class="mL10 hdrFont"> <spring:message code="label.state" /> </span>
							<span class="mL5"> 
								<form:select path="rawAcrfDoc.facilityStateAtBthDx" id="rawAcrfDoc_facilityAtBthDxState" class="standardInputSmallText2 readonly" addrsVar="stateAddrs" maxlength="2" title="State" >
														<form:option value="" label="--select--"></form:option>
														<form:options items="${sessionScope.stateMap}"/>
													</form:select>	
							 </span>
						</div>
						<div style="float:left; width:17%; border-right:1px dotted;  ">
							<span class="mL10 hdrFont"> <spring:message code="label.country" /></span>
							<span class="mL5"> 
								<form:select path="rawAcrfDoc.facilityCountryAtBthDx" id="rawAcrfDoc_facilityAtBthDxCountry" addrsVar="countryAddrs"  class="standardInputSmallText2 readonly" maxlength="7" title="Country" >
										<form:option value=""></form:option>
										<form:options items="${codedCountries}"/>
										<form:option value="OTH">Other</form:option>
										<form:option value="UNK">Unknown</form:option>
									</form:select>				
							 </span>		
						</div>
						
						<div style="float:left; "> 
							<span class="mL10 hdrFont"> <spring:message code="label.zip" /> </span>
							<span class="mL5"> <form:input path="rawAcrfDoc.facilityZipcodeAtBthDx" id="rawAcrfDoc_facilityAtBthDxZipcode" class="standardInputSmallText2 readonly" addrsVar="zipAddrs" maxlength="5" title="Zip Code" /> </span>
							<span class="mL2"> <form:input path="rawAcrfDoc.facilityPlus4AtBthDx" id="rawAcrfDoc_facilityAtBthDxPlus4" class="standardInputSmallText1 readonly" addrsVar="plus4Addrs" maxlength="4" title="Plus4" /> </span>		
						</div>	
					</div>
		
				<div style="width:99.9%; border:1px dotted;" class="container"></div>
				
				<div style="margin-right:7px; " class="pB5">
					<div style="padding-bottom:5px;" class="mL10">
						<span class="hdrFont"> Facility Type</span> <form:input class="standardInputText readonly" path="rawAcrfDoc.facilityAtBthDxTypeCode" id="rawAcrfDoc_facilityAtBthDxTypeCode" addrsVar="facilityTypeCD"  title="Facility Type"/>
					</div>		
				
				</div>	
	
	
		</div>
	
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	
	
	<div style="float:left; width:99%;" class="container pB5"> 
		<div style="float:left; width:29%; border-right:1px dotted;  background-color:#DBEAF9;"> 
					<span class="hdrFont "> Birth History</span>
		</div>
		<div style="float:left; width:40%; border-right:1px dotted;  ">
		
				<span class="mL10"> Birth Weight:</span>
				<span class="mL5">
					<form:input path="rawAcrfDoc.birthWeightLbs" id="rawAcrfDoc_birthWeightLbs" class="standardInputVerySmall nOnly"  maxlength="10"  title="Birth Weight lbs"/>lbs
				</span>
				<span class="mL5">
					<form:input path="rawAcrfDoc.birthWeightOz" id="rawAcrfDoc_birthWeightOz" class="standardInputVerySmall nOnly"  maxlength="10"  title="Birth Weight oz"/>oz
				</span>
				<span class="mL5">
					<form:input path="rawAcrfDoc.birthWeightGram" id="rawAcrfDoc_birthWeightGram" class="standardInputVerySmall nOnly"  maxlength="10"  title="Birth Weight grams"/>grams
				</span>
		</div>
		<div style="float:left; width:30%; "> 
				<span class="mL5"> Type </span>
				<span class="mL10"><form:radiobutton path="rawAcrfDoc.birthType" id="rawAcrfDoc_birthType" value="1" title="Birth Type" /> 1-Single </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthType" id="rawAcrfDoc_birthType" value="2" title="Birth Type" /> 2-Twin </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthType" id="rawAcrfDoc_birthType" value="3" title="Birth Type" /> 3-More than two</span>	
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthType" id="rawAcrfDoc_birthType" value="9" title="Birth Type" /> 9-Unknown</span>
		</div>
	</div>
	
	<div style="width:99.9%; border:1px dotted;" class="container "></div>
	<div style="float:left; width:99%;" class="container pB5 toggleSection" dependentElement="bthDeliveryDepElement">
	
				<div> 
				<span class="mL5"> Delivery </span>
				<span class="mL10"><form:radiobutton path="rawAcrfDoc.birthDelivery" id="rawAcrfDoc_birthDelivery" value="1" title="Delivery" /> 1-Vaginal </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthDelivery" id="rawAcrfDoc_birthDelivery" value="2" title="Delivery" /> 2-Cesarean </span>
<%-- 				<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthDelivery" id="rawAcrfDoc_birthDelivery" value="3" title="Delivery" /> 3-Nonelective Cesarean</span>	 --%>
<%-- 				<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthDelivery" id="rawAcrfDoc_birthDelivery" value="4" title="Delivery" /> 4-Cesarean, unknown type</span> --%>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthDelivery" id="rawAcrfDoc_birthDelivery" value="9" title="Delivery" /> 9-Unknown</span>
				</div>
				<div class="clearBoth pT5 bthDeliveryDepElement">
					<div>
					
					<span class="mL5 bold"> If Cesarean delivery, mark all the following indications that apply. </span>
					</div>
					<div class="floatLeft flw30">
						<span class=""><form:checkbox path="rawAcrfDoc.csRsnHiv" id="rawAcrfDoc_csRsnHiv" class="cesareanRsns" value="true"/></span><span class="mL5">HIV indication (high viral load)</span><br/>
						<span class=""><form:checkbox path="rawAcrfDoc.csRsnLongLabor" id="rawAcrfDoc_csRsnLongLabor" class="cesareanRsns" value="true"/></span><span class="mL5">Prolonged labor or failure to progress</span><br/>
						<span class=""><form:checkbox path="rawAcrfDoc.csRsnAbruptiaPrevia" id="rawAcrfDoc_csRsnAbruptiaPrevia" class="cesareanRsns" value="true"/></span><span class="mL5">Placenta abruptia or p. previa</span><br/>
						<span class=""><form:checkbox path="rawAcrfDoc.csRsnNotSpecified" id="rawAcrfDoc_csRsnNotSpecified" class="cesareanRsns" value="true"/></span><span class="mL5">Not specified</span><br/>
					</div>
					<div class="floatLeft flw30 toggleSection" dependentElement="bthDeliveryOthDepElement">
						<span class=""><form:checkbox path="rawAcrfDoc.csRsnPrevCs" id="rawAcrfDoc_csRsnPrevCs" class="cesareanRsns" value="true"/></span><span class="mL5">Previous Cesarean (repeat)</span><br/>
						<span class=""><form:checkbox path="rawAcrfDoc.csRsnPref" id="rawAcrfDoc_csRsnPref" class="cesareanRsns" value="true"/></span><span class="mL5">Birthing person's or physician's preference</span><br/>
						<span class=""><form:checkbox path="rawAcrfDoc.csRsnOther" id="rawAcrfDoc_csRsnOther" class="cesareanRsns otherElement" value="true"/></span><span class="mL5">Other (e.g., herpes, disproportion) (Specify)</span><br/>
						<span class="bthDeliveryOthDepElement">
							<form:input path="rawAcrfDoc.csRsnOtherSpecify" id="rawAcrfDoc_csRsnOtherSpecify" value="${acrfFormVO.rawAcrfDoc.csRsnOtherSpecify}" class="standardInputSmallText otherDepElement" maxlength="100" title="Cesarean delivery other reason"/>
						</span><br/>
					</div>
					<div class="floatLeft flw30">
						<span class=""><form:checkbox path="rawAcrfDoc.csRsnMalpresentation" id="rawAcrfDoc_csRsnMalpresentation" class="cesareanRsns" value="true"/></span><span class="mL5">Malpresentation (breech, transverse)</span><br/>
						<span class=""><form:checkbox path="rawAcrfDoc.csRsnFetalDistress" id="rawAcrfDoc_csRsnFetalDistress" class="cesareanRsns" value="true"/></span><span class="mL5">Fetal distress</span><br/>
					</div>
				</div>
	</div>
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%;" class="container pB5"> 
		<div style="float:left; width:29%; border-right:1px dotted;  background-color:#DBEAF9;"> 
					<span class="hdrFont "> Birth Information</span>
		</div>
		<div class="clearBoth  mL10">
				<div class="floatLeft flw30">
					<span >&nbsp;</span><br/>
					<span >Rupture of membranes</span><br/>
					<span >Delivery</span>
				</div>
				
				<div class="floatLeft flw30">
					<span >Date</span><br/>
					<span ><form:input path="rawAcrfDoc.ruptureDt" id="rawAcrfDoc_ruptureDt" class="standardInputSmallText  dPicker mask " datevalidations="partialDateForAll" title="Rupture Date" /></span><br/>
					<span ><form:input path="rawAcrfDoc.deliveryDate" id="rawAcrfDoc_deliveryDate" class="standardInputSmallText  dPicker mask" datevalidations="partialDateForAll" title="Delivery Date" /></span>
				</div>
				
				<div class="floatLeft fLW35">
					<span >Time (use military time: noon = 12:00; midnight = 00:00)</span><br/>
					<span ><form:input path="rawAcrfDoc.ruptureTime" id="rawAcrfDoc_ruptureTime" class="standardInputSmallText    "  title="Rupture time" /></span><br/>
					<span ><form:input path="rawAcrfDoc.deliveryTime" id="rawAcrfDoc_deliveryTime" class="standardInputSmallText " title="Delivery time" /></span>
				</div>
		
		</div>
		
	</div>
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	<div style="float:left; width:99%;" class="container pB5 toggleSection" dependentElement="congenitalDisordersDepElement"> 
		
		<div style="float:left; width:50%;   ">
		
				<span class="mL5"> Congenital Disorders:</span>
				<span class="mL10"><form:radiobutton path="rawAcrfDoc.congenitalDisorders" id="rawAcrfDoc_congenitalDisorders" value="Y" title="Congenital Disorders" /> Yes </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.congenitalDisorders" id="rawAcrfDoc_congenitalDisorders" value="N" title="Congenital Disorders" /> No </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.congenitalDisorders" id="rawAcrfDoc_congenitalDisorders" value="U" title="Congenital Disorders" /> Unknown</span>
		</div>
		<div style="float:left; " class="congenitalDisordersDepElement"> 
				<span class="mL5"> If YES, specify types </span>
				<span class="mL10"><form:input path="rawAcrfDoc.congenitalDisordersCdl5" id="rawAcrfDoc_congenitalDisordersCdl5" class="standardInputText"  title="Congenital Disorders types" /> </span>
				
		</div>
		
	</div>
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%;" class="container pB5"> 
		<div style="float:left; width:40%; border-right:1px dotted;  ">
		
				<span class="mL5"> Neonatal Status:</span>
				<span class="mL10"><form:radiobutton path="rawAcrfDoc.neonatalStatus" id="rawAcrfDoc_neonatalStatus" value="1" title="Birth Defects" /> Full-term </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.neonatalStatus" id="rawAcrfDoc_neonatalStatus" value="2" title="Birth Defects" /> Premature </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.neonatalStatus" id="rawAcrfDoc_neonatalStatus" value="9" title="Birth Defects" /> Unknown</span>
		</div>
		<div style="float:left; width:59%; "> 
				<span class="mL5"> Neonatal Gestational Age in Weeks </span>
				<span class="mL10"><form:input path="rawAcrfDoc.neonatalGestAgeInWeeks" id="rawAcrfDoc_neonatalGestAgeInWeeks" class="standardInputVerySmall"  title="Neonatal Gestational Age" /> (99 = Unknown, 00 = None)</span>
				
		</div>
	</div>
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	
	<div class="toggleSection" dependentElement="screenSubstanceDepElement" style="width:95%" >
		<div style="float:left; width:99%; background:#DDDDDD" class="roundedBorderFont container pB5"> 
			<div style="border-right:1px dotted;  ">
			
					<span class="mL5 bold"> Was a toxicology screen done on the infant after birth?</span>
					<span class="mL10"><form:radiobutton path="rawAcrfDoc.screenSubstance" id="rawAcrfDoc_screenSubstance" value="Y" title="Was a toxicology screen done on the infant after birth" /> Yes </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.screenSubstance" id="rawAcrfDoc_screenSubstance" value="N" title="Was a toxicology screen done on the infant after birth" /> No </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.screenSubstance" id="rawAcrfDoc_screenSubstance" value="U" title="Was a toxicology screen done on the infant after birth" /> Unknown</span>
			</div>
			<div class="clearBoth"> 
					<span class="mL5">(If screening for the same substance was done on more than one occasion, record additional dates and results in Comments)</span>
			</div>
		</div>

		<div style="float:left; width:99%;" class="roundedBorderFont container pB5 mT10 screenSubstanceDepElement">
		
			<table border="1" cellpadding="0" cellspacing="0" class="subStanceTable" style="width:85%; margin: 20px auto">
			
			<thead>
			
				<tr>
			 		<th class="textAlignLeft" style="width:30%">
						<span class="mL5"> Substance name</span>
					</th>
					<th class="textAlignCenter"  style="width:18%">
						<span class=""> Not screened</span>
					</th>
					<th class="textAlignCenter"   style="width:18%">
						<span class=""> Date of screen</span>
					</th>
					<th class="textAlignCenter"   style="width:34%">
						<span class=""> Result</span>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach  items="${acrfFormVO.rawAcrfDoc.screenSubstanceList}" var="subStance" varStatus="statusHNT">
					<tr class="subStanceSection">	
					 	<td class="textAlignLeft">
							<span class="mL5"> <c:out value="${subStance.subStanceName}" /></span>
							
							<form:hidden path="rawAcrfDoc.screenSubstanceList[${statusHNT.index}].subStancecode" substancecode="${subStance.refCd}" class="displayNone substanceCode" />
							
							<c:if test="${subStance.refCd == '17'}">
								<form:input path="rawAcrfDoc.screenSubstanceList[${statusHNT.index}].otherDetails" id="rawAcrfDoc_screenSubstanceList${statusHNT.index}_otherDetails" class="substanceDataText standardInputSmallText  " title="Other Substance" />
							</c:if>
							
							
							
						</td>
						<td class="textAlignCenter">
							<span class=""> <form:checkbox path="rawAcrfDoc.screenSubstanceList[${statusHNT.index}].screeningDone" value="N"  class="substanceData"/></span>
						</td>
						<td class="textAlignCenter">
							<span class=""> <form:input path="rawAcrfDoc.screenSubstanceList[${statusHNT.index}].screenDate" id="rawAcrfDoc_screenSubstanceList${statusHNT.index}_screenDate" class="substanceDate toxicDateAfterDel standardInputSmallText  dPicker mask" datevalidations="partialDateForAll" title="Screen date" /></span>
							<br/>
						<span class="mL5 v_error rawAcrfDoc_screenSubstanceList${statusHNT.index}_screenDate"></span>
						</td>
						<td class="textAlignCenter">
							<span class=""><form:radiobutton path="rawAcrfDoc.screenSubstanceList[${statusHNT.index}].screenResult" id="rawAcrfDoc_screenSubstanceList${statusHNT.index}_screenResult" value="P" title="Substance Result"   class="substanceData"/> Positive</span>
							<span class="mL5"><form:radiobutton path="rawAcrfDoc.screenSubstanceList[${statusHNT.index}].screenResult" id="rawAcrfDoc_screenSubstanceList${statusHNT.index}_screenResult" value="N" title="Substance Result"   class="substanceData"/> Negative</span>
							<span class="mL5"><form:radiobutton path="rawAcrfDoc.screenSubstanceList[${statusHNT.index}].screenResult" id="rawAcrfDoc_screenSubstanceList${statusHNT.index}_screenResult" value="U" title="Substance Result"   class="substanceData"/> Unknown</span>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	
	
	</div>
	
	
</div>


<br/>