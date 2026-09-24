<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- This jsp belongs to "TTH" Fourth Tab --%> 
<!-- page4 first block starts -->

<span class="mL10 hdrFont"> Birth History (for Perinatal Cases only) </span>

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
	<div style="float:left; width:99%;" class="container pB5"> 
				<span class="mL5"> Delivery </span>
				<span class="mL10"><form:radiobutton path="rawAcrfDoc.birthDelivery" id="rawAcrfDoc_birthDelivery" value="1" title="Delivery" /> 1-Vaginal </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthDelivery" id="rawAcrfDoc_birthDelivery" value="2" title="Delivery" /> 2-Elective Cesarean </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthDelivery" id="rawAcrfDoc_birthDelivery" value="3" title="Delivery" /> 3-Nonelective Cesarean</span>	
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthDelivery" id="rawAcrfDoc_birthDelivery" value="4" title="Delivery" /> 4-Cesarean, unknown type</span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthDelivery" id="rawAcrfDoc_birthDelivery" value="9" title="Delivery" /> 9-Unknown</span>
	</div>
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	
	
	<div style="float:left; width:99%;" class="container pB5 changeSelection"> 
		<div style="float:left; width:40%; border-right:1px dotted;  ">
		
				<span class="mL5"> Birth Defects:</span>
				<span class="mL10"><form:radiobutton path="rawAcrfDoc.birthDefects" id="rawAcrfDoc_birthDefects" value="Y" title="Birth Defects" /> Yes </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthDefects" id="rawAcrfDoc_birthDefects" value="N" title="Birth Defects" /> No </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthDefects" id="rawAcrfDoc_birthDefects" value="U" title="Birth Defects" /> Unknown</span>
		</div>
		<div style="float:left; width:59%; "> 
				<span class="mL5"> If yes, specify types </span>
				<span class="mL10">
				
					<form:select  path="rawAcrfDoc.birthDefectsTypes" id="rawAcrfDoc_birthDefectsTypes"   class="standardInputMedText"  title="Birth Defects types" multiple="true" >
						<c:forEach items="${birthDefectTypeList}" var="birthDefect">
							<form:option value="${birthDefect.LOOKUP_CD}">${birthDefect.LOOKUP_NAME}</form:option>
						</c:forEach>
					</form:select> 
					 
				
				</span>
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
	
	<div style="float:left; width:99%;" class="container pB5 "> 
		<div style="float:left; width:50%; border-right:1px dotted;  ">
		
				<span class="mL5"> Prenatal Care-Month of Pregnancy Prenatal Care Began</span>
				<span class="mL10"><form:input path="rawAcrfDoc.prenatalCareBeginMonth" id="rawAcrfDoc_prenatalCareBeginMonth" class="standardInputVerySmall"  title="Prenatal Care month began" /> (99 = Unknown, 00 = None)</span>
		</div>
		<div style="float:left; width:49%; "> 
				<span class="mL5"> Prenatal Care-Total Number of Prenatal Care Visits</span>
				<span class="mL10"><form:input path="rawAcrfDoc.prenatalCareNoVisits" id="rawAcrfDoc_prenatalCareNoVisits" class="standardInputVerySmall"  title="Total Number of Prenatal Care Visits" /> (99 = Unknown, 00 = None)</span>
				
		</div>
	</div>
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%;" class="container pB5 changeSelection"> 
		<div style="float:left; width:50%; border-right:1px dotted;  ">
				<div>
					<span class="mL5"> Did mother receive any antiretrovirals (ARVs) prior to this pregnancy?</span>
					<span class="mL10"><form:radiobutton path="rawAcrfDoc.motherRecvArvPriorPregnancy" id="rawAcrfDoc_motherRecvArvPriorPregnancy" value="Y" title="ARVs prior to this pregnancy" /> Yes </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvArvPriorPregnancy" id="rawAcrfDoc_motherRecvArvPriorPregnancy" value="N" title="ARVs prior to this pregnancy" /> No </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvArvPriorPregnancy" id="rawAcrfDoc_motherRecvArvPriorPregnancy" value="R" title="ARVs prior to this pregnancy" /> Refused</span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvArvPriorPregnancy" id="rawAcrfDoc_motherRecvArvPriorPregnancy" value="U" title="ARVs prior to this pregnancy" /> Unknown</span>
				</div>	
				<div class="mT10">
					
					<div class="floatLeft">
					
						<span class="mL5"> Date began</span>
						<span class="mL10"><form:input path="rawAcrfDoc.arvPriorPregnancyBeganDate" id="rawAcrfDoc_arvPriorPregnancyBeganDate" class="standardInputSmallText  dPicker mask momInfo" datevalidations="partialDateForAll" title="ARVs Date began" /> </span>
						
						<div class="v_error rawAcrfDoc_arvPriorPregnancyBeganDate"></div>
					</div>
					<div class="floatLeft">
						<span class="mL10"> Date of last use</span>
						<span class="mL10"><form:input path="rawAcrfDoc.arvPriorPregnancyLastUseDate" id="rawAcrfDoc_arvPriorPregnancyLastUseDate" class="standardInputSmallText  dPicker mask momInfo" datevalidations="partialDateForAll" title="ARVs Date of last use" /> </span>
						
						<div class="v_error rawAcrfDoc_arvPriorPregnancyLastUseDate"></div>
					</div>
				</div>
		</div>
		<div style="float:left; width:49%; "> 
				<span class="mL5"> If yes, specify all ARVs</span>
				<span class="mL10">
					
					<form:select  path="rawAcrfDoc.motherRecvArvPriorPregnancyNotes" id="rawAcrfDoc_motherRecvArvPriorPregnancyNotes"   class="standardInputMedText"  title="ARVs prior to this pregnancy" multiple="true" >
						<c:forEach items="${codedArvsMedications}" var="arvCategory">
							<form:option value="${arvCategory.LOOKUP_CD}">${arvCategory.ARV_DESC}</form:option>
						</c:forEach>
					</form:select> 
					
					
				</span>
				
		</div>
	</div>
	
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%;" class="container pB5 changeSelection"> 
		<div style="float:left; width:50%; border-right:1px dotted;  ">
				<div>
					<span class="mL5"> Did mother receive any ARVs during pregnancy?</span>
					<span class="mL10"><form:radiobutton path="rawAcrfDoc.motherRecvArvDuringPregnancy" id="rawAcrfDoc_motherRecvArvDuringPregnancy" value="Y" title="ARVs during pregnancy" /> Yes </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvArvDuringPregnancy" id="rawAcrfDoc_motherRecvArvDuringPregnancy" value="N" title="ARVs during pregnancy" /> No </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvArvDuringPregnancy" id="rawAcrfDoc_motherRecvArvDuringPregnancy" value="R" title="ARVs during pregnancy" /> Refused</span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvArvDuringPregnancy" id="rawAcrfDoc_motherRecvArvDuringPregnancy" value="U" title="ARVs during pregnancy" /> Unknown</span>
				</div>	
				<div class="mT10">
					
					<div class="floatLeft">
					
						<span class="mL5"> Date began</span>
						<span class="mL10"><form:input path="rawAcrfDoc.arvDuringPregnancyBeganDate" id="rawAcrfDoc_arvDuringPregnancyBeganDate" class="standardInputSmallText  dPicker mask momInfo" datevalidations="partialDateForAll" title="ARVs Date began" /> </span>
						<div class="v_error rawAcrfDoc_arvDuringPregnancyBeganDate"></div>
					</div>
					<div class="floatLeft">
					
						<span class="mL10"> Date of last use</span>
						<span class="mL10"><form:input path="rawAcrfDoc.arvDuringPregnancyLastUseDate" id="rawAcrfDoc_arvDuringPregnancyLastUseDate" class="standardInputSmallText  dPicker mask momInfo" datevalidations="partialDateForAll" title="ARVs Date of last use" /> </span>
						<div class="v_error rawAcrfDoc_arvDuringPregnancyLastUseDate"></div>
					</div>
				</div>
		</div>
		<div style="float:left; width:49%; "> 
				<span class="mL5"> If yes, specify all ARVs</span>
				<span class="mL10">
				
					
					<form:select  path="rawAcrfDoc.motherRecvArvDuringPregnancyNotes" id="rawAcrfDoc_motherRecvArvDuringPregnancyNotes"   class="standardInputMedText"  title="ARVs during pregnancy" multiple="true" >
						<c:forEach items="${codedArvsMedications}" var="arvCategory">
							<form:option value="${arvCategory.LOOKUP_CD}">${arvCategory.ARV_DESC}</form:option>
						</c:forEach>
					</form:select> 
					
			 </span>
				
		</div>
	</div>
	
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%;" class="container pB5 changeSelection"> 
		<div style="float:left; width:50%; border-right:1px dotted;  ">
				<div>
					<span class="mL5"> Did mother receive any ARVs during labor/delivery?</span>
					<span class="mL10"><form:radiobutton path="rawAcrfDoc.motherRecvArvLaborPregnancy" id="rawAcrfDoc_motherRecvArvLaborPregnancy" value="Y" title="ARVs during delivery" /> Yes </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvArvLaborPregnancy" id="rawAcrfDoc_motherRecvArvLaborPregnancy" value="N" title="ARVs during delivery" /> No </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvArvLaborPregnancy" id="rawAcrfDoc_motherRecvArvLaborPregnancy" value="R" title="ARVs during delivery" /> Refused</span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvArvLaborPregnancy" id="rawAcrfDoc_motherRecvArvLaborPregnancy" value="U" title="ARVs during delivery" /> Unknown</span>
				</div>	
				<div class="mT10">
					<div class="floatLeft">
						<span class="mL5"> Date began</span>
						<span class="mL10"><form:input path="rawAcrfDoc.arvLaborPregnancyBeganDate" id="rawAcrfDoc_arvLaborPregnancyBeganDate" class="standardInputSmallText  dPicker mask momInfo" datevalidations="partialDateForAll" title="ARVs Date began" /> </span>
						<div class="v_error rawAcrfDoc_arvLaborPregnancyBeganDate"></div>
					</div>
					<div class="floatLeft">
						<span class="mL10"> Date of last use</span>
						<span class="mL10"><form:input path="rawAcrfDoc.arvLaborPregnancyLastUseDate" id="rawAcrfDoc_arvLaborPregnancyLastUseDate" class="standardInputSmallText  dPicker mask momInfo" datevalidations="partialDateForAll" title="ARVs Date of last use" /> </span>
						<div class="v_error rawAcrfDoc_arvLaborPregnancyLastUseDate"></div>
					</div>
					
				</div>
		</div>
		<div style="float:left; width:49%; "> 
				<span class="mL5"> If yes, specify all ARVs</span>
				<span class="mL10">
					
					<form:select  path="rawAcrfDoc.motherRecvArvLaborPregnancyNotes" id="rawAcrfDoc_motherRecvArvLaborPregnancyNotes"   class="standardInputMedText"  title="ARVs during delivery" multiple="true" >
						<c:forEach items="${codedArvsMedications}" var="arvCategory">
							<form:option value="${arvCategory.LOOKUP_CD}">${arvCategory.ARV_DESC}</form:option>
						</c:forEach>
					</form:select> 
					
					
				 </span>
				
		</div>
	</div>
	
	
	<script type="text/javascript"> 
			arvMedications = '${acrfFormVO.rawAcrfDoc.motherRecvArvPriorPregnancyNotes}';
			$('#rawAcrfDoc_motherRecvArvPriorPregnancyNotes').val(arvMedications.split(','));
			arvMedications = '${acrfFormVO.rawAcrfDoc.motherRecvArvDuringPregnancyNotes}';
			$('#rawAcrfDoc_motherRecvArvDuringPregnancyNotes').val(arvMedications.split(','));
			arvMedications = '${acrfFormVO.rawAcrfDoc.motherRecvArvLaborPregnancyNotes}';
			$('#rawAcrfDoc_motherRecvArvLaborPregnancyNotes').val(arvMedications.split(','));
			
			var birthDefectsTypes = '${acrfFormVO.rawAcrfDoc.birthDefectsTypes}';
			$('#rawAcrfDoc_birthDefectsTypes').val(birthDefectsTypes.split(','));
			
			
	</script> 
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	
	
	<div style="float:left; width:99%;" class="container pB5"> 
		<div style="float:left; width:29%; border-right:1px dotted;  background-color:#DBEAF9;"> 
					<span class="hdrFont "> Maternal Information</span>
		</div>
		<div style="float:left; width:30%; border-right:1px dotted;  ">
		
				<span class="mL10"> Maternal DOB</span>
				<span class="mL10"><form:input path="rawAcrfDoc.maternalDob" id="rawAcrfDoc_maternalDob" class="standardInputSmallText  dPicker mask " datevalidations="partialDateForAll" title="Maternal DOB" /> </span>
				
				<div class="v_error rawAcrfDoc_maternalDob"></div>
				
		</div>
		<div style="float:left; width:40%; "> 
				<span class="mL5"> Maternal Last Name Soundex </span>
				<span class="mL10"><form:input path="rawAcrfDoc.maternalLastNameSndx" id="rawAcrfDoc_maternalLastNameSndx" class="standardInputMedText"  title="Maternal Last Name Soundex" /> </span>
		</div>
	</div>
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	
	
	<div style="float:left; width:99%;" class="container pB5"> 
		<div style="float:left; width:49%; border-right:1px dotted;  ">
		
				<span class="mL10"> Maternal State ID Number</span>
				<span class="mL10"><form:input path="rawAcrfDoc.maternalStateIdNumber" id="rawAcrfDoc_maternalStateIdNumber" class="standardInputText" title="Maternal State ID Number" maxlength="20"/> </span>
				
		</div>
		<div style="float:left; width:50%; "> 
				<span class="mL5"> Maternal Country of Birth </span>
				<span class="mL10">
					<form:select path="rawAcrfDoc.maternalCountryOfBirth" id="rawAcrfDoc_maternalCountryOfBirth"  class="standardInputMedText" maxlength="7" title="Maternal Country of Birth">
						<form:option value=""></form:option>
						<form:options items="${codedCountries}"/>
						<form:option value="OTH">Other</form:option>
						<form:option value="UNK">Unknown</form:option>
					</form:select>
					 
				</span>
		</div>
	</div>
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%;" class="container pB5"> 
				<span class="mL10"> *Other Maternal ID (specify type of ID and ID number)</span>
				<span class="mL10"><form:input path="rawAcrfDoc.otherMaternalId" id="rawAcrfDoc_otherMaternalId" class="standardInputText" title="Other Maternal ID" /> </span>
	</div>
	
	
	<div style="width:99.9%;" class="container"></div>
</div>

<!-- Page3 Second block -->

<br/>

<span class="mL10 hdrFont"> Treatment/Services Referrals (record all dates as mm/dd/yyyy) </span>
<div class="roundedBorderFont mL10" style="width:98%; background:#DDDDDD" >
	<div style="width:99%; margin-left:5px;  margin-right:5px; padding-bottom:5px">
		<div id="takenArvs" style="width:60%; border-right:1px dotted;"> 
				<span class="mL5"> This child ever taken any ARVs?  </span>
				<span class="mL20"><form:radiobutton path="rawAcrfDoc.childEverTakenArvs" id="rawTTHDocVO_childEverTakenArvs" class="takenArvs" value="Y" title="child ever taken ARVs" /> Yes </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.childEverTakenArvs" id="rawTTHDocVO_childEverTakenArvs" class="takenArvs" value="N" title="child ever taken ARVs" /> No </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.childEverTakenArvs" id="rawTTHDocVO_childEverTakenArvs" class="takenArvs" value="U" title="child ever taken ARVs" /> Unknown</span>
				<div class="v_error takenArvs"></div>	
			</div>

		</div>

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
					</div><br/>
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

	<div style="width:99%; margin-left:5px;  margin-right:5px; padding-bottom:5px" class="changeSelection">
		<div style="float:left; width:47%;"> 
			<span class="mL5"> Has this child ever taken PCP prophylaxis </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.childEverTakenPcpProphylaxis" id="rawTTHDocVO_childEverTakenPcpProphylaxis" value="Y"  title="child ever taken PCP prophylaxis"/> Yes</span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.childEverTakenPcpProphylaxis" id="rawTTHDocVO_childEverTakenPcpProphylaxis" value="N" title="child ever taken PCP prophylaxis"/> No</span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.childEverTakenPcpProphylaxis" id="rawTTHDocVO_childEverTakenPcpProphylaxis" value="U" title="child ever taken PCP prophylaxis"/> Unknown</span>	
		</div>
		<div class="floatLeft" style="width:26%; border-left:1px dotted;">
			<span class="mL5">Date began: <form:input path="rawAcrfDoc.prophylaxisBeganDate" id="rawAcrfDoc_prophylaxisBeganDate" class="standardInputSmallText dPicker mask datepicker maskDate" datevalidations="partialDateForAll" title="Date began"/> </span>
			<div class="v_error rawAcrfDoc_prophylaxisBeganDate"></div>
		</div>
		<div class="floatLeft" style="width:26%; border-left:1px dotted;">
			<span class="mL5">Date of last use: <form:input path="rawAcrfDoc.prophylaxisLastUseDate" id="rawAcrfDoc_prophylaxisLastUseDate" class="standardInputSmallText dPicker mask datepicker maskDate" datevalidations="partialDateForAll" title="Date of last use"/> </span>
			<div class="v_error rawAcrfDoc_prophylaxisLastUseDate"></div>
		</div>	
	</div>
	<div style="width:99.9%; border:1px dotted;" class="container"></div>

	<div style="width:99%; margin-left:5px;  margin-right:5px; padding-bottom:5px;">
			<span class="mL5"> Was this child breastfed? </span>
			<span class="mL20"><form:radiobutton path="rawAcrfDoc.wasChildBreastfed" id="rawTTHDocVO_wasChildBreastfed" value="Y" title="child breastfed" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.wasChildBreastfed" id="rawTTHDocVO_wasChildBreastfed" value="N" title="child breastfed" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.wasChildBreastfed" id="rawTTHDocVO_wasChildBreastfed" value="U" title="child breastfed" /> Unknown</span>
				
	</div>
	<div style="width:99.9%; border:1px dotted;" class="container"></div>	

	<div style="width:99%; margin-left:5px;  margin-right:5px; padding-bottom:5px;">
			<span class="mL5"> This child's primary caretaker is </span>
			<span class="mL20"><form:radiobutton path="rawAcrfDoc.childPrimaryCaretaker" id="rawTTHDocVO_childPrimaryCaretaker" value="1" title="child breastfed" />  1-Biological parent  </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.childPrimaryCaretaker" id="rawTTHDocVO_childPrimaryCaretaker" value="2" title="child breastfed" /> 2-Other relative </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.childPrimaryCaretaker" id="rawTTHDocVO_childPrimaryCaretaker" value="3" title="child breastfed" /> 3-Foster/Adoptive parent, relative</span>
			<span class="mL20"><form:radiobutton path="rawAcrfDoc.childPrimaryCaretaker" id="rawTTHDocVO_childPrimaryCaretaker" value="4" title="child breastfed" /> 4-Foster/Adoptive parent, unrelated </span>
			<span class="mL20"><form:radiobutton path="rawAcrfDoc.childPrimaryCaretaker" id="rawTTHDocVO_childPrimaryCaretaker" value="7" title="child breastfed" /> 7-Social service agency </span>
			<span class="mL20"><form:radiobutton path="rawAcrfDoc.childPrimaryCaretaker" id="rawTTHDocVO_childPrimaryCaretaker" value="8" title="child breastfed" /> 8-Other (please specify in comments)  </span>
			<span class="mL20"><form:radiobutton path="rawAcrfDoc.childPrimaryCaretaker" id="rawTTHDocVO_childPrimaryCaretaker" value="9" title="child breastfed" />9-Unknown  </span>
				
	</div>

	
	<div style="width:99.9%; border:1px dotted;" class="container"></div>
</div>
<br/>

<span class="mL10 hdrFont"> Comments </span>
<div class="roundedBorder mL10" style="width:98%; height:187px; background:#DDDDDD; padding-left:2px;" >
	<span>  <form:textarea path="rawAcrfDoc.comments" id="rawAcrfDoc_comments" value="${acrfFormVO.rawAcrfDoc.comments}" ROWS="8" COLS="165" maxlength="255" title="Comments"/> </span>
	<div class="v_error rawAcrfDoc_comments"></div>
</div>

<br/>

<span class="mL10 hdrFont"> Local / Optional Fields </span>
<div class="roundedBorder mL10" style="width:98%; height:187px; background:#DDDDDD; padding-left:2px;" >
	<span>  <form:textarea path="rawAcrfDoc.localOptionalFields" id="rawAcrfDoc_localOptionalFields" value="${acrfFormVO.rawAcrfDoc.localOptionalFields}" ROWS="8" COLS="165" maxlength="4000" title="Comments"/> </span>
</div>
<div style="width:98%; background:#DDDDDD" class="roundedBorder mL10 container mT10">
	<p class="mL20 mR10"> 
		This report to CDC is authorized by law (Sections 304 and 306 of the Public Health Service Act, 42 USC 242b and 242k). Response in this case is voluntary for federal government purposes, but may be mandatory under state and local statutes. Your cooperation is necessary for the understanding and control of HIV. Information in CDC's National HIV Surveillance System that would permit identification of any individual on whom a record is maintained, is collected with a guarantee that it will be held in confidence, will be used only for the purposes stated in the assurance on file at the local health department, and will not otherwise be disclosed or released without the consent of the individual in accordance with Section 308(d) of the Public Health Service Act (42 USC 242m).
		
	</p>
</div>
<br/>