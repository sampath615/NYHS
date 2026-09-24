<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%-- This jsp belongs to "PT Info" First Tab --%> 
<span class="mL10 hdrFont"> I. Patient Identification (record all dates as mm/dd/yyyy)</span>
<span class="hdrFont" style="float: right; margin-right: 20px" id="eharsStatenoDetails">
	<span class="mL10 "> eHARS STATENO: </span>
	<span id="eharsStateno"></span>
</span>
<div class="roundedBorder mL10" style="width:98%; background:#DDDDDD" >

<div style="width:99%; margin-top:2px; margin-left:5px;  margin-right:5px;">
	<div style="float:left; width:76%; padding-bottom:5px;" class="container"> 		
		<span class="hdrFont"> </span> <br/>		
		<div style="float:left">
			<span class="mL10 hdrFont"> <spring:message code="label.firstname" /> </span>  <form:input path="rawAcrfDoc.fname" id="rawAcrfDoc_fname" value="${acrfFormVO.rawAcrfDoc.fname}" class="standardInputMedText  required" cssErrorClass="error" maxlength="20" title="First Name" />
			<div class="v_error rawAcrfDoc_fname mL10 width250"></div>
		</div>
		<div style="float:left">
			<span class="mL10 hdrFont"> <spring:message code="label.middleName" /> </span> <form:input path="rawAcrfDoc.mname" id="rawAcrfDoc_mname" value="${acrfFormVO.rawAcrfDoc.mname}" class="standardInputSmallText " cssErrorClass="error" maxlength="25" title="Middle Name" />
			<div class="v_error rawAcrfDoc_mname mL10 width250"></div>
		</div>
		<div style="float:left">
			<span class="mL10 hdrFont"> <spring:message code="label.lastname" /> </span> <form:input path="rawAcrfDoc.lname" id="rawAcrfDoc_lname" value="${acrfFormVO.rawAcrfDoc.lname}" class="standardInputMedText required " cssErrorClass="error" maxlength="25" title="Last Name"/>
			<div class="v_error rawAcrfDoc_lname mL10 width250"></div>
		</div>		
	</div>	
	<div style="float:right;width:23% ; border-left:1px dotted; padding-bottom:5px;"> 
		<span class="hdrFont mL20"> Last Name Soundex </span><br/>
		<span><form:input path="rawAcrfDoc.lastnameSoundex" id="rawAcrfDoc_lastnameSoundex" value="${acrfFormVO.rawAcrfDoc.lastnameSoundex}" class="standardInputSmallText" cssErrorClass="error" maxlength="20" title="Last Name Soundex" readonly="true"/> </span>
	</div>
</div>

<div style="width:99.9%; border:1px dotted; " class="container"></div> 

<div class="mL5 mR5">
	<div style="float:left; width:34%; border-right:1px dotted; padding-bottom:5px;"> 
		<span class="hdrFont"> Alternate Name Type (ex Alias, Married) </span><br/>
		<span class="mL20">
			<form:select path="rawAcrfDoc.nameTypeCode" id="rawAcrfDoc_nameTypeCode" class="standardInputMedText" title="Alternate Name Type">
				<form:option value="" selected="selected">--Select--</form:option>
				<form:option value="Legal">Legal</form:option>
				<form:option value="Alias">Alias</form:option>
				<form:option value="Birth">Birth</form:option>
				<form:option value="Call Me">Call Me</form:option>
				<form:option value="Display">Display</form:option>
				<form:option value="Indian/Tribal">Indian/Tribal</form:option>
				<form:option value="License">License</form:option>
				<form:option value="Maiden">Maiden</form:option>
				<form:option value="Married">Married</form:option>
				<form:option value="Professional">Professional</form:option>
				<form:option value="Record">Record</form:option>
				<form:option value="Religious">Religious</form:option>
			</form:select>
		</span>
		<div class="v_error rawAcrfDoc_nameTypeCode mL10"></div>
	</div>	
	<div style="float:left; width:65%; padding-bottom:5px;"> 
		<div style="float:left; width: 215px">
			<span class="mL10 hdrFont"> <spring:message code="label.firstname" /> </span> <br/> <span class="mL10"><form:input path="rawAcrfDoc.altFname" id="rawAcrfDoc_altFname" value="${acrfFormVO.rawAcrfDoc.altFname}" class="standardInputMedText" cssErrorClass="error" maxlength="20" title="First Name"/> </span>
			<div class="v_error rawAcrfDoc_altFname mL10"></div>
		</div>
		<div style="float:left; width: 215px">
			<span class="mL25 hdrFont"> <spring:message code="label.middleName" /> </span> <br/> <span class="mL25"> <form:input path="rawAcrfDoc.altMname" id="rawAcrfDoc_altMname" value="${acrfFormVO.rawAcrfDoc.altMname}" class="standardInputMedText" cssErrorClass="error" maxlength="25" title="Middle Name"/> </span>
			<div class="v_error rawAcrfDoc_altMname mL25"></div>
		</div>
		<div style="float:left; width: 215px">
			<span class="mL25 hdrFont"> <spring:message code="label.lastname" /> </span> <br/> <span class="mL25"><form:input path="rawAcrfDoc.altLname" id="rawAcrfDoc_altLname" value="${acrfFormVO.rawAcrfDoc.altLname}" class="standardInputMedText" cssErrorClass="error" maxlength="25" title="Last Name"/> </span>
			<div class="v_error rawAcrfDoc_altLname mL25"></div>
		</div>		
	</div>
</div>

<div style="width:99.9%; border:1px dotted;" class="container"></div> 

<div id="patientAddrsFields">
	<div style="width:99%;"  class="mL5 mR5 container" >
		<div id="addressTypeforPI" class="checkForValue" style="float:left; width:34%; padding-bottom:3px; border-right:1px dotted;"> 
			<span class="hdrFont"> Address Type </span>
			<span class="chkboxLabelfont"> <form:radiobutton path="rawAcrfDoc.addressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_addressTypeCode"  value="RES" title="Address Type" /> Residential </span>
			<span class="chkboxLabelfont"> <form:radiobutton path="rawAcrfDoc.addressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_addressTypeCode" value="BA" title="Address Type" /> Bad Address </span>
			<span class="chkboxLabelfont"> <form:radiobutton path="rawAcrfDoc.addressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_addressTypeCode" value="CF" title="Address Type" /> Correctional Facility </span><br/>
			<span class="chkboxLabelfont"> <form:radiobutton path="rawAcrfDoc.addressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_addressTypeCode" value="FH" title="Address Type" /> Foster Home </span>
			<span class="chkboxLabelfont"> <form:radiobutton path="rawAcrfDoc.addressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_addressTypeCode" value="HLES" title="Address Type" /> Homeless </span>
			<span class="chkboxLabelfont"> <form:radiobutton path="rawAcrfDoc.addressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_addressTypeCode" value="MIL" title="Address Type" /> Military </span>
			<span class="chkboxLabelfont"> <form:radiobutton path="rawAcrfDoc.addressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_addressTypeCode" value="OTH" title="Address Type" /> Other </span>
			<span class="chkboxLabelfont"> <form:radiobutton path="rawAcrfDoc.addressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_addressTypeCode" value="POST" title="Address Type" /> Postal </span>
			<span class="chkboxLabelfont"> <form:radiobutton path="rawAcrfDoc.addressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_addressTypeCode" value="SHEL" title="Address Type" /> Shelter </span>
			<span class="chkboxLabelfont"> <form:radiobutton path="rawAcrfDoc.addressTypeCode"   addrsVar="addressType " id="rawAcrfDoc_addressTypeCode" value="TEMP" title="Address Type" /> Temporary </span>
			<div class="v_error addressTypeforPI"></div>
		</div>
		
		<div style="float:left; width:65%; padding-bottom:5px; ">
			<div style="float:left; width:33%; border-right:1px dotted;" class="checkForValue"> 
				<span class="mL20 hdrFont"> <spring:message code="label.country" /> </span><br/>
				<span class="mL20"> 
					<form:select path="rawAcrfDoc.country" id="rawAcrfDoc_country" value="${acrfFormVO.rawAcrfDoc.country}" addrsVar="countryAddrs" class="standardInputMedText" maxlength="7" title="Country">
						<form:option value=""></form:option>
						<form:options items="${codedCountries}"/>
						<form:option value="OTH">Other</form:option>
						<form:option value="UNK">Unknown</form:option>
					</form:select>
				
				 </span>
				<div class="v_error rawAcrfDoc_country mL20"></div>
			</div>
			<div style="float:left; border-right:1px dotted;width:33%; " > 
				<span class="hdrFont mL20"> <spring:message code="label.phoneNumber" /> </span> <br/>
				<span class=" mL20">
				<form:input path="rawAcrfDoc.telephone" id="rawAcrfDoc_telephone" value="${acrfFormVO.rawAcrfDoc.telephone}" class="standardInputMedText  mask" maxlength="20" title="Phone Number"/>  </span>
				<div class="v_error rawAcrfDoc_telephone"></div>
			</div>	
<%-- 			<c:if test="${acrfFormVO.rawAcrfDoc.isOldAcrf != 'Y'}"> --%>
			<c:if test="${acrfRevison > 2013}">
				<div style="float:left;" class="checkForValue"> 
					<span class="hdrFont mL20"> Address Date </span> <br/>
					<span class=" mL20">
						<form:input path="rawAcrfDoc.patientAddressDate" id="rawAcrfDoc_patientAddressDate" addrsVar="addressDate" value="${acrfFormVO.rawAcrfDoc.patientAddressDate}" class="standardInputSmallText dPicker mask"  datevalidations="" title="Address Date" /> 
					</span>
					<div class="v_error rawAcrfDoc_patientAddressDate"></div>
				</div>
			</c:if>	
		</div>
	</div>
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%; margin-left:5px; margin-right:5px;" class="container checkForValue" id="patientAddresInfromation"> 
		
		<div style="float:left; width:20%; border-right:1px dotted;   padding-bottom:7px;">
			<span class="hdrFont mL5"> Current Address, Street </span><br/>
			<span class="mL5"> <form:input path="rawAcrfDoc.address" id="rawAcrfDoc_address" class="standardInputMedText newAddress widgetAddressFieldsReadonly"  addrsVar="streetAddrs" maxlength="62" title="Current Street Address"/> </span>
			<div class="v_error rawAcrfDoc_address mL5"></div>
		</div>
		
		<div style="float:left; width:18%; border-right:1px dotted;  padding-bottom:5px;"> 
			<span class="mL5 hdrFont"> <spring:message code="label.city" /> </span><br/>
			<span class="mL5"> <form:input path="rawAcrfDoc.city" id="rawAcrfDoc_city" value="${acrfFormVO.rawAcrfDoc.city}" class="standardInputMedText widgetFields newCity widgetAddressFieldsReadonly" addrsVar="cityAddrs" maxlength="30" title="City"/> </span>
			<div class="v_error rawAcrfDoc_city mL5"></div>
		</div>
		<div style="float:left; width:15%; border-right:1px dotted;  padding-bottom:7px;"> 
			<span class="mL5 hdrFont"> <spring:message code="label.county" /> </span><br/>
			<span class="mL5"> 
				<form:select path="rawAcrfDoc.countyFips" id="rawAcrfDoc_countyFips" value="${acrfFormVO.rawAcrfDoc.countyFips}" class="standardInputSmallText widgetFields newCounty widgetAddressFieldsReadonly" addrsVar="countyAddrs" maxlength="3" title="County Fips" >
									<form:option value="" label="--select--"></form:option>
									<form:options items="${sessionScope.countyMap}"/>
								</form:select> 
			</span>
			
			<div class="v_error rawAcrfDoc_countyFips mL5"></div>
			
			
		</div>
		
		<div style="float:left; width:19%; border-right:1px dotted;  padding-bottom:7px;"> 
			<span class="mL5 hdrFont"> <spring:message code="label.state" /> </span><br/>
			<span class="mL5">  
								<form:select path="rawAcrfDoc.state" id="rawAcrfDoc_state" value="${acrfFormVO.rawAcrfDoc.state}" class="standardInputMedText widgetFields newState widgetAddressFieldsReadonly" addrsVar="stateAddrs" maxlength="2" title="State" >
									<form:option value="" label="--select--"></form:option>
									<form:options items="${sessionScope.stateMap}"/>
								</form:select>
								
			</span>
			<div class="v_error rawAcrfDoc_state mL5"></div>
		</div>
		
		<div style="float:left; width:17%; border-right:1px dotted; padding-bottom:7px;"> 
			<span class="mL5 hdrFont"> *<spring:message code="label.zip" /> </span> <br/>
			<span class="mL5"> <form:input path="rawAcrfDoc.zipcode" id="rawAcrfDoc_zipcode" value="${acrfFormVO.rawAcrfDoc.zipcode}" class="standardInputSmallText2 nOnly widgetFields newZip widgetAddressFieldsReadonly"addrsVar="zipAddrs"  maxlength="5" title="Zip Code"/> </span>
			<span class="mL2"> <form:input path="rawAcrfDoc.plus4" id="rawAcrfDoc_plus4" value="${acrfFormVO.rawAcrfDoc.plus4}" class="standardInputSmallText1 nOnly" addrsVar="plus4Addrs" maxlength="4" title="plus4"/> </span>
			<div class="v_error rawAcrfDoc_zipcode mL5"></div>
		</div>
		
		<div style="float:left; width:9%;   padding-bottom:7px;">
		<span class="mL5 hdrFont"> &nbsp;</span> <br/>
			<span class="mL5"> <button value="Edit" onclick="openAddressWidget('patientAddresInfromation');return false;" class="editAddress">Edit Address</button> </span>
		</div>
		
					
		
	</div>
	<div class="v_error patientAddresInfromation"></div>
	
</div>

<div style="width:99.9%; border:1px dotted; " class="container"></div>

<div style="margin-left:5px; margin-right:5px;" class="container">
	<div style="float:left; width:37%; padding-bottom:5px; padding-top:5px;"> 
		<span class="hdrFont mL20"> Medical Record Number </span>
		<span class="mL10"> <form:input path="rawAcrfDoc.medRecordNumber" id="rawAcrfDoc_medRecordNumber" value="${acrfFormVO.rawAcrfDoc.medRecordNumber}" class="standardInputSmallText" maxlength="17" title="Medical Record Number"/> </span>		
	</div>
	<div style="float:left; width:62%; border-left:1px dotted;  padding-bottom:5px;">
		<div style="float:left; width:54%; padding-top:5px;"> 
			<span class="hdrFont mL20"> Other ID Type </span>
			<span class="mL10"> 
				<form:select id="rawAcrfDoc_idTypeCode" path="rawAcrfDoc.idTypeCode" title="Other ID Type" class="standardInputSmallText mR50" value="${acrfFormVO.rawAcrfDoc.idTypeCode}">
					<form:option value="" label="------Select-------"></form:option>
					<c:forEach items="${acrfOtherIdList}" var="idType">
						<form:option value="${idType.OTHER_ID_TYPE}" label="${idType.OTHER_ID_TYPE_DESC}"></form:option>
					</c:forEach>
				 </form:select> 
			<!-- <form:input path="rawAcrfDoc.idTypeCode" id="rawAcrfDoc_idTypeCode" value="${acrfFormVO.rawAcrfDoc.idTypeCode}" class="standardInputMedText" maxlength="7"/> --> 
			</span>
		</div>
		<div style="float:left; padding-top:5px;"> 
			<span class="hdrFont"> Number </span>
			<span class="mL10"><form:input path="rawAcrfDoc.otherIdNumber" id="rawAcrfDoc_otherIdNumber" value="${acrfFormVO.rawAcrfDoc.otherIdNumber}" class="standardInputMedText" maxlength="25" title="Number"/> </span>
			<div class="v_error rawAcrfDoc_otherIdNumber"></div>
		</div>	
	</div>
</div>	

</div>

<div style="width:98%; margin-top:5px; margin-right:5px;" class="mL10 container">
	<div style="float:left; width:17%;"> 
		<span class="mL10 "> U.S. Department of Health</span><br/>
		<span class="mL10 "> & Human Services</span>
	</div>
	<div style="float:left; width:62%; "> 
		<span class="mL200 hdr2Font"> Adult HIV Confidential Case Report Form</span><br/>
		<span class="mL85 "> (Patients >=13 Years of Age at Time of Diagnosis)  * Information NOT transmitted to CDC</span>
	</div>
	
	<div style="float:left; width:20%;"> 
		<span class="mL25 "> Centers for Disease Control</span><br/>
		<span class="mL65 "> and Prevention</span>
	</div>	
</div>

<!-- Second block starts -->

<div style="width:98%; margin:7px;" class="mL10 container">
	<div style="float:left; width:67%; padding-bottom:5px;">
		<span class="mL20 hdrFont"> II. Health Department Use Only (record all dates as mm/dd/yyyy)</span>
	</div>
	<div style="float:right; width:32%; padding-left:5px">
		<c:choose>	
			<c:when test="${acrfRevison < 2022}">
				Form approved OMB no 0920-0573 Exp. 11/30/2022
			</c:when>
			<c:otherwise>
				Form approved OMB no 0920-0573 Exp. 02/28/2026
			</c:otherwise>
		</c:choose>
				
	</div>
</div>	

<div class="roundedBorder mL10 container" style="width:98%; background:#DDDDDD; padding-top:3px;" >

	<div style="width:99%; margin-left:5px;  margin-right:5px;">
		<div style="float:left; width:39%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="hdrFont"> Date Received at Health Department </span>
			<span> <form:input path="rawAcrfDoc.receivedDate" id="rawAcrfDoc_receivedDate" value="${acrfFormVO.rawAcrfDoc.receivedDate}" class="standardInputSmallText dPicker mask readonly"  datevalidations="completeDate" title="Date Received at Health Department" /> </span>
			<div class="v_error rawAcrfDoc_receivedDate"></div>
		</div>
		<div style="float:left; width:34%; padding-bottom:5px; border-right:1px dotted;">
			<span class="hdrFont mL10"> eHARS Document UID </span>
			<span class="mL5"> <form:input path="rawAcrfDoc.eharsDocId" id="rawAcrfDoc_eharsDocId" value="${acrfFormVO.rawAcrfDoc.eharsDocId}" class="standardInputSmallText readonly" maxlength="25"  title="eHARS Document UID" /> </span>
		</div>
		<div style="float:left; width:26%; padding-bottom:5px;">
			<span class="hdrFont mL10"> <spring:message code="label.stateNo" /> </span>
			<c:choose>
				<c:when test="${acrfFormVO.rawAcrfDoc.stateno != null && acrfFormVO.rawAcrfDoc.stateno != ''}">
					<span class="mL5"> <form:input path="rawAcrfDoc.stateno" id="rawAcrfDoc_stateno" value="${acrfFormVO.rawAcrfDoc.stateno}" class="standardInputSmallText readonly" maxlength="10"  title="STATENO" /> </span>
				</c:when>
				<c:otherwise>
					<span class="mL5"> <form:input path="rawAcrfDoc.stateno" id="rawAcrfDoc_stateno" value="${param.stateNo}" class="standardInputSmallText readonly" maxlength="10"  title="STATENO" /> </span>	
				</c:otherwise>
			</c:choose>
			
			
			
		</div>		
	</div>

	<div style="width:99.9%; border:1px dotted; margin-top:45px;"></div>

	<div style="float:left; width:99%; padding-bottom:5px; " class="mL5 mR5 mT5 "> 
		<div style="float:left; width:50%; padding-bottom:5px; border-right:1px dotted;">
			<span class="hdrFont mL5"> Reporting Health Dept - City / County </span>
			<span class="mL5"> <form:input path="rawAcrfDoc.reportingHealthDeptCityCounty" id="rawAcrfDoc_reportingHealthDeptCityCounty" value="${acrfFormVO.rawAcrfDoc.reportingHealthDeptCityCounty}" title="Reporting Health Dept - City / County" class="standardInputSmallText" maxlength="100"/> </span>
		</div>
		<div style="float:left; width:49%; padding-bottom:5px;">
			<span class="hdrFont mL20"> City/County Number </span>
			<span class="mL5"> <form:input path="rawAcrfDoc.cityCountyNumber" id="rawAcrfDoc_cityCountyNumber" value="${acrfFormVO.rawAcrfDoc.cityCountyNumber}" title="City/County Number" class="standardInputSmallText" maxlength="20"/> </span>
		</div>	
	</div>	

	<div style="width:99.9%; border:1px dotted; margin-top:35px;"></div>
	
	<div style="margin-left:5px; margin-right:7px;">
		<div style="float:left; width:33%; padding-bottom:5px; border-right:1px dotted;"> 
			<span class="hdrFont "> Document Source </span>
			<span class="mL5"> 
				<form:select path="rawAcrfDoc.documentSource" id="rawAcrfDoc_documentSource" class="standardInputSmallText mR50 required" title="Document Source">
					<form:option value="" label="-----Select-------"></form:option>
					<c:forEach items="${documentSourceList}" var="metaData">
						<form:option value="${metaData.DOCUMENT_SOURCE_CODE}" label="${metaData.DOCUMENT_SOURCE_DESCRIPTION}"></form:option>
					</c:forEach>
				 </form:select> 
			</span>
			<div class="v_error rawAcrfDoc_documentSource"></div>
		</div>
		
		<div style="float:right; width:66%; "> 
			<span class="hdrFont "> Surveillance Method </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.surveillanceMethodCode" id="rawAcrfDoc_surveillanceMethodCode" class="readonly" value="ACT" title="Surveillance Method" checked="true" /> Active </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.surveillanceMethodCode" id="rawAcrfDoc_surveillanceMethodCode" class="readonly" value="PAS" title="Surveillance Method" /> Passive </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.surveillanceMethodCode" id="rawAcrfDoc_surveillanceMethodCode" class="readonly" value="FUP" title="Surveillance Method" /> Follow up </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.surveillanceMethodCode" id="rawAcrfDoc_surveillanceMethodCode" class="readonly" value="REAB" title="Surveillance Method" /> Reabstraction </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.surveillanceMethodCode" id="rawAcrfDoc_surveillanceMethodCode" class="readonly" value="U" title="Surveillance Method"  /> Unknown </span>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; margin-top:45px;"></div>

	<div style="width:99%;"  class="mL5 mR5" >
		<div style="float:left; width:33%; padding-bottom:5px; border-right:1px dotted;" id="newCaseInvestigation"> 
			<span class="hdrFont"> Did this report initiate a new case investigation?</span><br/>
			<span class="mL20"><form:radiobutton path="rawAcrfDoc.newCaseCode" id="rawAcrfDoc_newCaseCode" class="readonly" value="Y" checked="true" title="New case investigation"/> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.newCaseCode" id="rawAcrfDoc_newCaseCode" class="readonly" value="N"  title="New case investigation"/> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.newCaseCode" id="rawAcrfDoc_newCaseCode" class="readonly" value="U"  title="New case investigation"/> Unknown </span>
			
			<div class="v_error newCaseInvestigation"></div>
			
		</div>	
		<div style="float:left; width:15%;   padding-bottom:7px;"> 
			<span class="hdrFont mL20"> Report Medium</span>
		</div>
		<div style="float:left; width:51%;   padding-bottom:7px;" id="reportMedium">	
			<span><form:radiobutton path="rawAcrfDoc.reportMediumCode" id="rawAcrfDoc_reportMediumCode" value="FV"  checked="true" title="Report Medium"/> 1-Field Visit </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.reportMediumCode"  id="rawAcrfDoc_reportMediumCode"  value="Mailed" title="Report Medium"/> 2-Mailed </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.reportMediumCode" id="rawAcrfDoc_reportMediumCode" value="Faxed" title="Report Medium"/> 3-Faxed </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.reportMediumCode" id="rawAcrfDoc_reportMediumCode" value="Phone" title="Report Medium"/> 4-Phone </span><br/>
			<span ><form:radiobutton path="rawAcrfDoc.reportMediumCode" id="rawAcrfDoc_reportMediumCode" value="ET" title="Report Medium"/> 5-Electronic Transfer </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.reportMediumCode" id="rawAcrfDoc_reportMediumCode" value="CDDisk" title="Report Medium"/> 6-CD/Disk </span>
			
			<div class="v_error reportMedium"></div>		
		</div>
		
		<div class="clearBoth"></div>
		
		
		
			
	</div>
	
</div>

<!-- Third block starts -->
<br/>
<span class="mL10 hdrFont"> III. Facility Providing Information (record all dates as mm/dd/yyyy) </span>
<div id="facilityInfoFields" class="roundedBorder mL10" style="width:98%; background:#DDDDDD" >

<div style="width:99%; margin-top:5px; margin-left:5px;  margin-right:5px;" class="container checkForValue">
	<div style="float:left; width:68%; padding-bottom:5px;"> 
		<span class="hdrFont"> <spring:message code="label.facilityName" /></span>
		<span class="mL30"> 
		<form:select onchange="loadFacilityAddressDetails('#rawAcrfDoc_providingFacility', this)" path="rawAcrfDoc.providingEharsFacilityEharsUid" id="rawAcrfDoc_providingEharsFacilityEharsUid"  addrsVar="facilityUid" class="standardInputText " maxlength="80" title="Facility Name">
			<form:option value="" >--select--</form:option>
			<c:forEach items="${subFacilities}" var="facility">
				<form:option value="${facility.facility_uid}" label="${facility.facility_name}" name="${facility.facility_name}"></form:option>
			</c:forEach>
		</form:select> 
		<form:hidden path="rawAcrfDoc.providingFacilityId" id="rawAcrfDoc_providingFacilityId" addrsVar="facilityId"/>
		<form:hidden path="rawAcrfDoc.providingFacilityName" id="rawAcrfDoc_providingFacilityName" addrsVar="facilityName"/>		
		
		</span>
		<span ><a class="mailFacility" href="#"></a></span>
		<div class="v_error rawAcrfDoc_providingEharsFacilityEharsUid"></div>
	</div>	
	<div style="float:left; border-left:1px dotted; padding-bottom:5px;"> 
		<span class="hdrFont mL20" style="text-align:center; "> <spring:message code="label.phoneNumber" /></span> <form:input path="rawAcrfDoc.providingFacilityPhone" id="rawAcrfDoc_providingFacilityPhone" addrsVar="facilityPhone" value="${acrfFormVO.rawAcrfDoc.providingFacilityPhone}"  class="standardInputMedText pNumber font10pt readonly" maxlength="20" title="Phone Number"/>  
	</div>
</div>

<div style="width:99.9%; border:1px dotted; " class="container"></div>

<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT5 checkForValue"> 
	<span class="hdrFont"> *<spring:message code="label.streetAddress" /></span>
	<span class="mL17"> <form:input path="rawAcrfDoc.providingFacilityAddress" id="rawAcrfDoc_providingFacilityAddress" value="${acrfFormVO.rawAcrfDoc.providingFacilityAddress}" class="standardInputText readonly" addrsVar="streetAddrs" maxlength="100" title="Street Address" /> </span>
	<div class="v_error rawAcrfDoc_providingFacilityAddress"></div>
</div>	

<div style="width:99.9%; border:1px dotted; " class="container"></div>

<div style="float:left; width:99%; margin-left:5px; margin-right:5px; margin-top:2px;" class="container checkForValue"> 
	<div style="float:left; width:19%; border-right:1px dotted;  padding-bottom:5px;"> 
		<span class="mL5 hdrFont"> <spring:message code="label.city" /> </span>
		<span class="mL5"> <form:input path="rawAcrfDoc.providingFacilityCity" id="rawAcrfDoc_providingFacilityCity" value="${acrfFormVO.rawAcrfDoc.providingFacilityCity}" class="standardInputSmallText readonly" addrsVar="cityAddrs" maxlength="30" title="City" /> </span>
		<div class="v_error rawAcrfDoc_providingFacilityCity"></div>
	</div>
	<div style="float:left; width:22%; border-right:1px dotted;  padding-bottom:7px;"> 
		<span class="mL10 hdrFont"> <spring:message code="label.county" /> </span> 
		<span class="mL5"> 
		<form:select path="rawAcrfDoc.providingFacilityCountyFips" id="rawAcrfDoc_providingFacilityCountyFips" value="${acrfFormVO.rawAcrfDoc.providingFacilityCountyFips}" class="standardInputSmallText readonly" addrsVar="countyAddrs" maxlength="3" title="County Fips" >
									<form:option value="" label="--select--"></form:option>
									<form:options items="${sessionScope.countyMap}"/>
								</form:select>
		</span>
	</div>
	
	<div style="float:left; width:15%; border-right:1px dotted;  padding-bottom:7px;"> 
		<span class="mL10 hdrFont"> <spring:message code="label.state" /> </span>
		<span class="mL5"> 
			<form:select path="rawAcrfDoc.providingFacilityState" id="rawAcrfDoc_providingFacilityState" value="${acrfFormVO.rawAcrfDoc.providingFacilityState}" class="standardInputSmallText2 readonly" addrsVar="stateAddrs" maxlength="2" title="State" >
									<form:option value="" label="--select--"></form:option>
									<form:options items="${sessionScope.stateMap}"/>
								</form:select>	
		 </span>
	</div>
	<div style="float:left; width:17%; border-right:1px dotted;  padding-bottom:7px;">
		<span class="mL10 hdrFont"> <spring:message code="label.country" /></span>
		<span class="mL5"> 
			<form:select path="rawAcrfDoc.providingFacilityCountry" id="rawAcrfDoc_providingFacilityCountry" value="${acrfFormVO.rawAcrfDoc.providingFacilityCountry}" addrsVar="countryAddrs"  class="standardInputSmallText2 readonly" maxlength="7" title="Country" >
					<form:option value=""></form:option>
					<form:options items="${codedCountries}"/>
					<form:option value="OTH">Other</form:option>
					<form:option value="UNK">Unknown</form:option>
				</form:select>				
		 </span>		
	</div>
	
	<div style="float:left; padding-bottom:7px;"> 
		<span class="mL10 hdrFont"> <spring:message code="label.zip" /> </span>
		<span class="mL5"> <form:input path="rawAcrfDoc.providingFacilityZipcode" id="rawAcrfDoc_providingFacilityZipcode" value="${acrfFormVO.rawAcrfDoc.providingFacilityZipcode}" class="standardInputSmallText2 readonly" addrsVar="zipAddrs" maxlength="5" title="Zip Code" /> </span>
		<span class="mL2"> <form:input path="rawAcrfDoc.providingFacilityPlus4" id="rawAcrfDoc_providingFacilityPlus4" value="${acrfFormVO.rawAcrfDoc.providingFacilityPlus4}" class="standardInputSmallText1 readonly" addrsVar="plus4Addrs" maxlength="4" title="Plus4" /> </span>		
	</div>	
</div>

<div style="width:99.9%; border:1px dotted;" class="container"></div>

<div style="margin-right:7px; padding-bottom: 3px;">
	<div style="padding-bottom:5px;" class="mL10">
		<span class="hdrFont"> Facility Type</span> <form:input class="standardInputText readonly" path="rawAcrfDoc.providingFacilityTypeCode" id="rawAcrfDoc_providingFacilityTypeCode" value="${acrfFormVO.rawAcrfDoc.providingFacilityTypeCode}" addrsVar="facilityTypeCD"  title="Facility Type"/>
	</div>		

</div>	

<div style="width:99.9%; border:1px dotted; " class="container"></div>

<div style="width:99%; " class="mL5 mR5 container"  >
	<div style="float:left; width:30%;   padding-bottom:5px; padding-top:5px; "> 
		<span class="hdrFont"> Date Form Completed </span>
		<span> <form:input path="rawAcrfDoc.dateCompleted" id="rawAcrfDoc_dateCompleted" datevalidations="completeDate noFutureDate" value="${acrfFormVO.rawAcrfDoc.dateCompleted}" class="standardInputSmallText readonly dPicker mask sfsdf" title="Date Form Completed" readonly="true" /> </span>
		<div class="v_error rawAcrfDoc_dateCompleted"></div>
	</div>
	<div style="float:right; width:69%; padding-top:5px;">
	
	
	
		<div style="float:left; width:59%; border-right:1px dotted; border-left:1px dotted;padding-bottom:10px;">
			<c:choose>
				<c:when test="${acrfRevison > 2022}">
					<span class="hdrFont mL20"> *Person Completing Form </span>
					<c:choose>
						<c:when test="${regionWorkers != null }">
							<span class="mL20"> 
								<form:select path="rawAcrfDoc.personCompleting" id="rawAcrfDoc_personCompleting"  class="standardInputMedText" maxlength="100"  title="Person Completing Form">
									<form:option value=""></form:option>
									<c:forEach var="regionWorker" items="${regionWorkers}">
												<form:option  label="${regionWorker.value}" value="${regionWorker.value}" />	
									</c:forEach>
								</form:select> 
							</span>
						</c:when>
						<c:otherwise>
							<span class="mL20"> <form:input path="rawAcrfDoc.personCompleting" id="rawAcrfDoc_personCompleting" value="${acrfFormVO.rawAcrfDoc.personCompleting}" class="standardInputMedText" maxlength="100"  title="Person Completing Form"/> </span>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<span class="hdrFont mL20"> *Person Completing Form </span>
					<c:choose>
						<c:when test="${acrfFormVO.rawAcrfDoc.documentId != null && acrfFormVO.rawAcrfDoc.documentId != '0'}">
							<span class="mL20"> <form:input path="rawAcrfDoc.personCompleting" id="rawAcrfDoc_personCompleting" value="${acrfFormVO.rawAcrfDoc.personCompleting}" class="standardInputMedText" maxlength="100"  title="Person Completing Form"/> </span>
						</c:when>
						<c:otherwise>
							<span class="mL20"> <form:input path="rawAcrfDoc.personCompleting" id="rawAcrfDoc_personCompleting" value="${sessionScope.UserObj.userId}" class="standardInputMedText" maxlength="100"  title="Person Completing Form"/> </span>	
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			
			</c:choose>
		 
			
		
		
		
		</div>
		<div style="float:right; width:40%; "> 
			<span class="hdrFont" style="text-align:center; "> Phone </span><form:input path="rawAcrfDoc.workerPhone" id="rawAcrfDoc_workerPhone" value="${acrfFormVO.rawAcrfDoc.workerPhone}"  class="standardInputSmallText" maxlength="20"  title="Phone"/> 
		</div>	
	</div>
</div>
	
</div>
.
<!-- fourth block -->

<br/>
<span class="mL10 hdrFont"> IV. Patient Demographics (record all dates as mm/dd/yyyy) </span>
<div class="roundedBorder mL10" style="width:98%; height:auto; background:#DDDDDD" >

	<div style="width:99%; margin-top:5px; margin-left:5px;  margin-right:5px;" >
		<div id="sexAtBirthinPD" style="float:left; width:36%; padding-bottom:5px;"> 
			<span class="hdrFont"> Sex assigned at Birth</span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthSex"  id="rawAcrfDoc_birthSex" value="M" title="Sex assigned at Birth" /> Male </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthSex"  id="rawAcrfDoc_birthSex" value="F" title="Sex assigned at Birth" /> Female </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthSex"  id="rawAcrfDoc_birthSex" value="U" title="Sex assigned at Birth" /> Unknown </span>
			<div class="v_error sexAtBirthinPD"></div>
		</div>	
		<div id="countryOfBirthinPD" style="float:right;width:63%; border-left:1px dotted; padding-bottom:5px;"> 
			<span class="hdrFont mL20"> Country of Birth</span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.birthCountry" id="rawAcrfDoc_birthCountry" class="birthCountry" value="U" title="Country of Birth"/> US </span>
			<span class="mL5">
				<form:radiobutton path="rawAcrfDoc.birthCountry" id="rawAcrfDoc_birthCountry" class="birthCountry" value="O" title="Country of Birth" /> Other/ Us Dependency (please specify) 
				
				
				<form:select path="rawAcrfDoc.birthCountryOther" id="rawAcrfDoc_birthCountryOther" value="${acrfFormVO.rawAcrfDoc.birthCountryOther}"  class="standardInputSmallText" maxlength="30" title="Birth Country Other">
					<form:option value=""></form:option>
					<form:options items="${codedCountries}"/>
					<form:option value="OTH">Other</form:option>
					<form:option value="UNK">Unknown</form:option>
				</form:select>
					
				
			</span>
		<div class="v_error countryOfBirthinPD mL20"></div>
		<div class="v_error rawAcrfDoc_birthCountryOther mL20"></div>
		</div>
	</div>

	<div style="width:99.9%; border:1px dotted; " class="container"></div>

	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT5"> 
		<div style="float:left; width:40%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="hdrFont"> <spring:message code="label.dateOfBirth" /> </span>
			<span> <form:input path="rawAcrfDoc.dateOfBirth" id="rawAcrfDoc_dateOfBirth" datevalidations="partialDate" value="${acrfFormVO.rawAcrfDoc.dateOfBirth}" class="required standardInputSmallText cDateVal dPicker mask " maxlength="10" title="Date of Birth" />   </span>
			<div class="v_error rawAcrfDoc_dateOfBirth"></div>
		</div>
		<div style="float:right; width:59%; padding-bottom:5px;">
			<span class="hdrFont mL20"> Alias Date of Birth </span>
			<span> <form:input path="rawAcrfDoc.aliasDateOfBirth" id="rawAcrfDoc_aliasDateOfBirth" datevalidations="partialDate" value="${acrfFormVO.rawAcrfDoc.aliasDateOfBirth}" class="standardInputSmallText dPicker mask" maxlength="10" title="Alias Date of Birth" />  </span>
			<div class="v_error rawAcrfDoc_aliasDateOfBirth mL20"></div>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>

	<div style="float:left; width:99%; margin-left:5px; margin-right:5px; margin-top:2px"> 
		<div style="float:left; width:33%; border-right:1px dotted;  padding-bottom:5px;"> 
			<span class=" hdrFont"> Vital Status</span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.vitalStatisticsCode" id="rawAcrfDoc_vitalStatisticsCode" value="A" checked="true"/> 1- Alive </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.vitalStatisticsCode" id="rawAcrfDoc_vitalStatisticsCode" value="D" /> 2- Dead </span>
		</div>
		<div style="float:left; width:32%; border-right:1px dotted;  padding-bottom:7px;"> 
			<span class="hdrFont mL20"> <spring:message code="label.dateOfDeath" /> </span>
			<span class="mL20"> <form:input path="rawAcrfDoc.dateOfDeath" id="rawAcrfDoc_dateOfDeath" datevalidations="partialDate" value="${acrfFormVO.rawAcrfDoc.dateOfDeath}" class="standardInputSmallText dPicker mask"  maxlength="10" title="Date of Death" />   </span>
			<div class="v_error rawAcrfDoc_dateOfDeath mL20"></div>
		</div>
		
		<div style="float:left; width:33%;  padding-bottom:7px;"> 
			<span class="mL20 hdrFont"> State of Death</span>
			<span class="mL20"> 
				<form:select path="rawAcrfDoc.stateOfDeath" id="rawAcrfDoc_stateOfDeath" value="${acrfFormVO.rawAcrfDoc.stateOfDeath}" class="standardInputSmallText" addrsVar="stateAddrs" maxlength="2" title="State" >
					<form:option value="" label="--select--"></form:option>
					<form:options items="${sessionScope.stateMap}"/>
				</form:select> 
			</span>
			<div class="v_error rawAcrfDoc_stateOfDeath mL20"></div>
		</div>	
		
	</div>

	<div style="width:99.9%; border:1px dotted; " class="container"></div>

	<div style="margin-left:5px; margin-right:7px; margin-bottom:3px;">
		<div>
			<div style="float:left; width:18%; padding-bottom:5px;"> 
				<span class="hdrFont "> Gender Identity </span>
			</div>
			<div style="float:right; width:81%;" id="rawAcrfDoc_current_gender_identity"> 
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.genderCode" id="rawAcrfDoc_genderCode" class="genderCode" value="M" title="Current Gender Identity" /> Man </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.genderCode" id="rawAcrfDoc_genderCode" class="genderCode" value="F" title="Current Gender Identity" /> Woman </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.genderCode" id="rawAcrfDoc_genderCode" class="genderCode" value="Y" title="Current Gender Identity" /> Transgender man </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.genderCode" id="rawAcrfDoc_genderCode" class="genderCode" value="X" title="Current Gender Identity" /> Transgender woman </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.genderCode" id="rawAcrfDoc_genderCode" class="genderCode" value="D" title="Current Gender Identity" /> Declined to answer </span><br/>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.genderCode" id="rawAcrfDoc_genderCode" class="genderCode" value="U" title="Current Gender Identity" /> Unknown </span><br/>
				<span class="mL5">
					<form:radiobutton path="rawAcrfDoc.genderCode" id="rawAcrfDoc_genderCode" class="genderCode" value="A" /> Additional gender identity (specify) <form:input path="rawAcrfDoc.additionalGender" id="rawAcrfDoc_additionalGender" value="${acrfFormVO.rawAcrfDoc.additionalGender}" class="standardInputSmallText" maxlength="25" title="Additional gender identity"/>
				</span>
				<div class="v_error rawAcrfDoc_current_gender_identity mL5"></div>	
			</div>
		</div>
		<c:if test="${acrfRevison > 2022}">
			<div class="clearBoth mT10">
				<div style="float:left; width:18%; padding-bottom:5px;"> 
					<span class="hdrFont ">Date Identified </span>
				</div>
				<div style="float:right; width:81%;" id="rawAcrfDoc_current_gender_identity_date">
					<form:input path="rawAcrfDoc.genderIdentifiedDate" id="rawAcrfDoc_genderIdentifiedDate" datevalidations="completeDate" value="${acrfFormVO.rawAcrfDoc.genderIdentifiedDate}" class="standardInputSmallText dPicker mask"  maxlength="10" title="Gender Identified Date" />  
				</div>
				<div class="v_error rawAcrfDoc_genderIdentifiedDate mL5"></div>
			</div>
			
		</c:if>
		
	</div>	
	
	
	<c:if test="${acrfRevison > 2022}">
		<div style="width:99.9%; border:1px dotted; " class="container"></div>

		<div style="margin-left:5px; margin-right:7px; margin-bottom:3px;">
			<div>
				<div style="float:left; width:18%; padding-bottom:5px;"> 
					<span class="hdrFont "> Sexual Orientation </span>
				</div>
				<div style="float:right; width:81%;" id="rawAcrfDoc_sexual_orientation"> 
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.sexOrientation" id="rawAcrfDoc_sexOrientation" class="sexOrientation" value="S" title="Sexual Orientation" /> Straight or heterosexual </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.sexOrientation" id="rawAcrfDoc_sexOrientation" class="sexOrientation" value="G" title="Sexual Orientation" /> Lesbian or gay </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.sexOrientation" id="rawAcrfDoc_sexOrientation" class="sexOrientation" value="B" title="Sexual Orientation" /> Bisexual </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.sexOrientation" id="rawAcrfDoc_sexOrientation" class="sexOrientation" value="D" title="Sexual Orientation" /> Declined to answer </span><br/>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.sexOrientation" id="rawAcrfDoc_sexOrientation" class="sexOrientation" value="U" title="Sexual Orientation" /> Unknown </span><br/>
					<span class="mL5">
						<form:radiobutton path="rawAcrfDoc.sexOrientation" id="rawAcrfDoc_sexOrientation" class="sexOrientation" value="A" /> Additional sexual orientation (specify) <form:input path="rawAcrfDoc.additionalsexOrientation" id="rawAcrfDoc_additionalsexOrientation" value="${acrfFormVO.rawAcrfDoc.additionalsexOrientation}" class="standardInputSmallText" maxlength="25" title="Additional sexual orientation"/>
					</span>
					<div class="v_error rawAcrfDoc_sexual_orientation mL5"></div>	
				</div>
			</div>

			<div class="clearBoth mT10">
				<div style="float:left; width:18%; padding-bottom:5px;"> 
					<span class="hdrFont ">Date Identified </span>
				</div>
				<div style="float:right; width:81%;" id="rawAcrfDoc_sex_orientation_identity_date">
					<form:input path="rawAcrfDoc.sexOrientationDate" id="rawAcrfDoc_sexOrientationDate" datevalidations="completeDate" value="${acrfFormVO.rawAcrfDoc.sexOrientationDate}" class="standardInputSmallText dPicker mask"  maxlength="10" title="sexual orientation Identified Date" />  
				</div>
				<div class="v_error rawAcrfDoc_sexOrientationDate mL5"></div>
			</div>
		
		</div>
	</c:if>

	<div style="width:99.9%; border:1px dotted; " class="container"></div>

	<div style="width:99%; padding-bottom:5px; padding-top:3px;"  class="mL5 mR5 container" >
		<div id="EthinicityPD" style="float:left; width:55%; "> 
			<span class="hdrFont"> <spring:message code="label.ethnicity" /></span>
			<span class="mL20"><form:radiobutton path="rawAcrfDoc.ethnicityCode" id="rawAcrfDoc_ethnicityCode" value="01" title="Ethinicity" /> Hispanic/Latino </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.ethnicityCode"  id="rawAcrfDoc_ethnicityCode" value="02" title="Ethinicity" /> Not Hispanic/Latino </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.ethnicityCode"  id="rawAcrfDoc_ethnicityCode" value="09" title="Ethinicity" /> Unknown </span>
		<div class="v_error EthinicityPD"></div>	
		</div>
		<div style="float:right;width:44%; border-left:1px dotted;"> 
			<span class="hdrFont mL20"> Expanded Ethnicity </span><form:select path="rawAcrfDoc.expandedEthnicity" id="rawAcrfDoc_expandedEthnicity"  value="${acrfFormVO.rawAcrfDoc.expandedEthnicity}" class="standardInputMedText font10pt" maxlength="50" title="Expanded Ethinicity">
				<form:option value="" >--select--</form:option>
				<c:forEach items="${ethnicities}" var="ethnicitiy">
					<form:option value="${ethnicitiy.lookup_cd}" label="${ethnicitiy.lookup_name}" name="${ethnicitiy.lookup_name}"></form:option>
				</c:forEach>
			</form:select>
			
		</div>
	</div>
	<div style="width:99.9%; border:1px dotted;" class="container"></div>	

	<div style="width:99%; padding-bottom:5px;"  class="mL5 mR5 container" > 
		<div style="float:left; width:14%; "> 
			<span class=" hdrFont"> <spring:message code="label.race" /> </span><br/>
			<span class="mL5">(Check all that apply)</span>
		</div>
		<div id="racePD" style="float:left; width:51%; border-right:1px dotted;"> 
			<span class="mL5"><form:checkbox path="rawAcrfDoc.raceIndian" id="rawAcrfDoc_raceIndian" value="Y" title="Race" /> American Indian/Alaska Native </span>
			<span class="mL5"><form:checkbox path="rawAcrfDoc.raceAsian" id="rawAcrfDoc_raceAsian" value="Y" title="Race" /> Asian </span>
			<span class="mL5"><form:checkbox path="rawAcrfDoc.raceBlack" id="rawAcrfDoc_raceBlack" value="Y" title="Race" /> Black/African American </span><br/>
			<span class="mL5"><form:checkbox path="rawAcrfDoc.racePacific" id="rawAcrfDoc_racePacific" value="Y" title="Race" /> Native Hawaiian/Other Pacific Islander </span>
			<span class="mL5"><form:checkbox path="rawAcrfDoc.raceWhite" id="rawAcrfDoc_raceWhite" value="Y" title="Race" /> White </span>
			<span class="mL5"><form:checkbox path="rawAcrfDoc.raceUnknown" id="rawAcrfDoc_raceUnknown" value="Y" title="Race" /> Unknown </span>		
			<div class="v_error racePD"></div>		
		</div>
		<div style="float:left; width:33%;  padding-bottom:7px; padding-top:8px"> 
			<span class="mL20 hdrFont"> Expanded Race</span>
			<span class="mL20"> 
				<form:select path="rawAcrfDoc.expandedRace" id="rawAcrfDoc_expandedRace"  value="${acrfFormVO.rawAcrfDoc.expandedRace}" class="standardInputMedText" title="Expanded Race">
					<form:option value="" >--select--</form:option>
					<c:forEach items="${codedExpandedRaceList}" var="race">
						<form:option value="${race.EHARS_RACE_CODE}" label="${race.EHARS_RACE_DESC}" name="${race.EHARS_RACE_DESC}"></form:option>
					</c:forEach>
				</form:select>
			</span>
		</div>	
		
	</div>
</div>
<div style="width:99.9%;" class="container"></div>
<!-- fifth block -->
<div class="v_error HIVTab mL10"> </div>
<div class="v_error AIDSTab mL10"> </div>
<br/>
<span class="mL10 hdrFont"> V. Residence at Diagnosis (add additional addresses in Comments) (record all dates as mm/dd/yyyy)</span>
<div id="addressTabs" style="background-color: transparent; width:98%; margin-left:10px;" 
class="container">
	<ul id="addressTabs1">
		<li><a href="#HIVTab">HIV Diagnosis</a></li>				
		<li><a href="#AIDSTab">AIDS Diagnosis</a>  </li> 
	</ul>					
	<div id="HIVTab" title="Hiv Diagnosis">				
		<%@ include file="../worker/HIVDiagnosis.jsp" %> 	
	</div>	
	<div id="AIDSTab" title="Aids Diagnosis">						
		<%@ include file="../worker/AIDSDiagnosis.jsp" %>
	</div>
</div>

<!-- last section  -->
<br/>
<div class="roundedBorder mL10 container" style="width:98%; background:#DDDDDD" >
			<p class="mL20 mR10"> 
				Public reporting burden of this collection of information is estimated to average 20 minutes per response, including the time for reviewing instructions, searching existing data sources, gathering and maintaining the data needed, and completing and reviewing the collection of information. An agency may not conduct or sponsor, and a person is not required to respond to, a collection of information unless it displays a currently valid OMB control number. Send comments regarding this burden estimate or any other aspect of this collection of information, including suggestions for reducing this burden, to CDC, Project Clearance Officer, 1600 Clifton Road, MS D-74, Atlanta, GA 30333, ATTN: PRA (0920-0573). Do not send the completed form to this address.
			</p>
	

	
	
	
</div>
<br/>
