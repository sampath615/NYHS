<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link href="${staticPath}/css/docs/dthForm.css?v=${buildDate}"
	rel="stylesheet" type="text/css" />

<style>
.b1 {
	border-style: dotted;
	border-width: 1px;
	margin: 5px;
}

.pL30 {
	padding-left: 30px;
}

.pL10 {
	padding-left: 10px;
}

.pT20 {
	padding-top: 20px;
}

.pTRBL10 {
	padding: 10px 10px 0;
}

.labelunderneath6 {
	padding-left: 205px;
}
</style>

<form:form modelAttribute="stdNycDeathDocVO" id="stdNycDeathDocVO"
	cssClass="documentDescription">
	<form:errors path="*" cssClass="errorblock" element="div" />

	<div id="dthDoc" style="background-color: transparent">

		<div style="padding-left: 43%;" class="headingCls">NYC DEATH
			DOCUMENT</div>
		<div style="padding-bottom: 10px; text-align: right">
			<span class="mL10">Document ID: </span> <span class=" "> <form:input id="DOCUMENT_ID"
					 path="stdNycDeathDoc.documentId" class="standardinputSmallText ml10"
					maxlength="6" readonly="true" />
			</span>
		</div>
		<div class="container pB10 w100 pT20">
			<div style="float: left; width: 40%">
				<div >
					<span class="ml10"> <spring:message code="label.stateNo" />:
					</span> <span class="ml10"> <form:input id="STATENO"  path="stateno"
							class="standardInputSmallText" maxlength="10" readonly="true" />
					</span>
				</div>
			</div>
			<div style="float: left; width: 30%">
				<div >
					<span class="mL10"> eHARS Import Date: </span>
					<fmt:formatDate value="${stdNycDeathDocVO.stdNycDeathDoc.eharsImportDate}"
						type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
					<span class="mL10"><form:input id="EHARS_IMPORT_DATE"  path="stdNycDeathDoc.eharsImportDate" value="${theFormattedDate}"
						class="standardInputSmallText" readonly="true" /></span>
				</div>

			</div>
			<div
				style="float: left; width: 30%; text-align: right">
				<div style="">
					<span class="mL10">NYC SHIP Date: </span>
						<span class=" "> <fmt:formatDate
							value="${stdNycDeathDocVO.stdNycDeathDoc.nycShipDate}" type="date"
							pattern="MM/dd/yyyy" var="theFormattedNYC_SHIP_DATE_TEXT"></fmt:formatDate>
						<span class="mL10 "><form:input id="NYC_SHIP_DATE"  path="stdNycDeathDoc.nycShipDate"
								value="${theFormattedNYC_SHIP_DATE_TEXT}"
								class="standardinputSmallText ml10" maxlength="6"
								readonly="true" /></span>
					</span>
				</div>
			</div>
		</div>

		<jsp:include page="statenoDetails.jsp"></jsp:include>
		<!--  Person details -->

		<div class="container"
			style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
			<div id="psDetailsHeadingDiv" class="divHeadingCls"
				style="background: #DDDDDD;">
				<span class=" mL2">Person Details</span>
			</div>
			<div class="mLRTB pB20">
				<span class="fieldHolder25"> <span class="fLW35">
						First Name</span> <span class="fLW60"> <form:input id="FNAME"  path="stdNycDeathDoc.fname"
							class="standardInputSmallText" maxlength="15" />
				</span>
				</span> <span class="fieldHolder25 "> <span class="fLW35">
						Middle Name</span> <span class="fLW60"> <form:input id="MNAME"  path="stdNycDeathDoc.mname"
							class="standardInputSmallText" maxlength="1" />
				</span>
				</span> <span class="fieldHolder25 "> <span class="fLW35">
						Last Name</span> <span class="fLW60"> <form:input  path="stdNycDeathDoc.lname"
							id="lName" title="Last Name"
							class="standardInputSmallText required" maxlength="20" />
				</span>
				</span> <span class="fieldHolder25 "> <span class="fLW35">
						Soundex</span> <span class="fLW60"><form:input id="SOUNDEX"  path="stdNycDeathDoc.soundex"
							class="standardInputSmallText" maxlength="3" /></span>
				</span>
			</div>
			<div class="mLRTB pB20">
				<span class="fieldHolder25"> <span class="fLW50">
						Father's Last Name</span> <span class="fLW50"> <form:input id="FATHER_LNAME"
							 path="stdNycDeathDoc.fatherLname" class="standardInputSmallTextFname"
							maxlength="20" />
				</span>
				</span> <span class="fieldHolder25 "> <span class="fLW50">
						Mother's Maiden Name</span> <span class="fLW50"> <form:input id="MOTHER_MAIDEN"
							 path="stdNycDeathDoc.motherMaiden" class="standardInputSmallTextFname"
							maxlength="20" />
				</span>
				</span>
			</div>
			<div class="mLRTB pB20">
				<span class="fieldHolder25 "> <span class="fLW35">
						Date of Birth</span> <span class="fLW60"> <form:input
							id="dobValidation"  path="stdNycDeathDoc.dobText"
							class="dPicker standardInputSmallText" maxlength="15" />
				</span>
				</span> <span class="fieldHolder25 "> <span class="fLW35">
						Birth State </span> <span class="fLW60"><form:input id="BIRTH_STATE"
							 path="stdNycDeathDoc.birthState" class="standardInputSmallText" maxlength="2" /></span>
				</span> <span class="fieldHolder25 "> <span class="fLW35">
						Date of Death </span> <span class="fLW60"><form:input
							id="dodValidation"  path="stdNycDeathDoc.dodText" value="${dodText}"
							title="Date of death"
							class="dPicker required standardInputSmallText" maxlength="15" /></span>
				</span> <span class="fieldHolder25 "> <span class="fLW35">
						Age at Death </span> <span class="fLW60"><form:input
							 path="stdNycDeathDoc.ageDeath" id="deathAge" class="standardInputSmallText"
							maxlength="3" /></span>
				</span>
			</div>

			<div class="mLRTB pB20">

				<span class="fieldHolder25 "> <span class="fLW35">
						Delivery Date</span> <span class="fLW60"> <form:input
							id="ddValidation"  path="stdNycDeathDoc.deliveryDateText"
							class="standardInputSmallText" maxlength="15" />
				</span>
				</span>
			</div>

			<!--  Race details -->

			<div id="raceEthnicity" class="container"
				style="border: 1px dotted; padding: 5px 5px 0 0;">
				<div style="float: left; width: 35%;">
					<div style="padding: 0 0 10px 20px;">
						Race
						<div style="">
							<form:checkbox id="RACE_WHITE1"  path="stdNycDeathDoc.raceWhite" value="1" />
							White <br>
							<form:checkbox id="RACE_BLACK1"  path="stdNycDeathDoc.raceBlack" value="1" />
							African American <br>
							<form:checkbox id="RACE_ASIAN1"  path="stdNycDeathDoc.raceAsian" value="1" />
							Asian <br>
							<form:checkbox id="RACE_PACIFIC1"  path="stdNycDeathDoc.racePacific" value="1" />
							Pacific Islander <br>
							<form:checkbox id="RACE_INDIAN1"  path="stdNycDeathDoc.raceIndian" value="1" />
							American Indian/Alaskan Native <br>
							<form:checkbox id="RACE_OTHER1"  path="stdNycDeathDoc.raceOther" value="1" />
							Other <br>
							<form:checkbox id="RACE_UNKNOWN1"  path="stdNycDeathDoc.raceUnknown" value="1" />
							Unknown
						</div>
					</div>
					<div style="padding: 10px 0 10px 20px; border-top: dotted 1px;">
						Ethnicity (Hispanic or Latino)
						<div style="">
							<form:radiobutton id="ETHNICITY1"  path="stdNycDeathDoc.ethnicity" value="01" />
							Yes <br>
							<form:radiobutton id="ETHNICITY2"  path="stdNycDeathDoc.ethnicity" value="02" />
							No <br>
							<form:radiobutton id="ETHNICITY3"  path="stdNycDeathDoc.ethnicity" value="09" />
							Unknown <br>
						</div>
					</div>
					<div style="padding: 10px 0 10px 20px; border-top: dotted 1px;">
						<div class="floatLeft pR10" style="border-right: dotted 1px;">
							<div class="">Current Gender</div>
							<div class="">
								<div>
									<form:radiobutton id="CURRENT_GENDER1"  path="stdNycDeathDoc.currentGender" value="M" />
									Male
								</div>
								<div>
									<form:radiobutton id="CURRENT_GENDER2"  path="stdNycDeathDoc.currentGender" value="F" />
									Female
								</div>
								<div>
									<form:radiobutton id="CURRENT_GENDER3"  path="stdNycDeathDoc.currentGender" value="U" />
									Unknown
								</div>
							</div>
						</div>
						<div class="floatLeft pL10">
							<div class="">Transgender</div>
							<div class="">
								<div>
									<form:radiobutton id="CURRENT_GENDER4"  path="stdNycDeathDoc.currentGender" value="X" />
									MTF
								</div>
								<div>
									<form:radiobutton id="CURRENT_GENDER5"  path="stdNycDeathDoc.currentGender" value="Y" />
									FTM
								</div>
								<div>
									<form:radiobutton id="CURRENT_GENDER6"  path="stdNycDeathDoc.currentGender" value="T" />
									Unspecified
								</div>
							</div>
						</div>
					</div>
				</div>

				<div
					style="float: left; width: 29%; height: 371px; border-right: dotted 1px; border-left: 1px dotted;">
					<div style="padding: 5px 0 10px 20px; border-bottom: dotted 1px">
						<div class="">Marital Status</div>
						<div class="">
							<div>
								<form:radiobutton id="MARITAL_STATUS1"  path="stdNycDeathDoc.maritalStatus" value="1" />
								Never married
							</div>
							<div>
								<form:radiobutton id="MARITAL_STATUS2"  path="stdNycDeathDoc.maritalStatus" value="2" />
								Married
							</div>
							<div>
								<form:radiobutton id="MARITAL_STATUS3"  path="stdNycDeathDoc.maritalStatus" value="3" />
								Windowed
							</div>
							<div>
								<form:radiobutton id="MARITAL_STATUS4"  path="stdNycDeathDoc.maritalStatus" value="4" />
								Divorced
							</div>
							<div>
								<form:radiobutton id="MARITAL_STATUS5"  path="stdNycDeathDoc.maritalStatus" value="5" />
								Separated
							</div>
							<div>
								<form:radiobutton id="MARITAL_STATUS6"  path="stdNycDeathDoc.maritalStatus" value="6" />
								Domestic Partnership (NYC only)
							</div>
							<div>
								<form:radiobutton id="MARITAL_STATUS7"  path="stdNycDeathDoc.maritalStatus" value="7" />
								Other (NYC Only)
							</div>
							<div>
								<form:radiobutton id="MARITAL_STATUS8"  path="stdNycDeathDoc.maritalStatus" value="9" />
								Unknown (blank)
							</div>
						</div>
					</div>

					<div style="padding: 15px 0 10px 20px;">
						<div class="">Pregnant</div>
						<div class="">
							<div>
								<form:radiobutton id="PREGNANT1"  path="stdNycDeathDoc.pregnant" value="0" />
								Not pregnant in last year
							</div>
							<div>
								<form:radiobutton id="PREGNANT2"  path="stdNycDeathDoc.pregnant" value="1" />
								pregnant at time of death
							</div>
							<div>
								<form:radiobutton id="PREGNANT3"  path="stdNycDeathDoc.pregnant" value="2" />
								Not pregnant, but pregnant within 42 days of death
							</div>
							<div>
								<form:radiobutton id="PREGNANT4"  path="stdNycDeathDoc.pregnant" value="3" />
								Not pregnant, but pregnant within 43 days to 1 year before death
							</div>
							<div>
								<form:radiobutton id="PREGNANT5"  path="stdNycDeathDoc.pregnant" value="4" />
								Unknown if pregnant within the past year
							</div>
							<div>
								<form:radiobutton id="PREGNANT6"  path="stdNycDeathDoc.pregnant" value="" />
								Not stated
							</div>
						</div>
					</div>

				</div>
				<div style="float: left; width: 35%;">
					<div style="padding: 0 0 10px 10px; border-bottom: dotted 1px">
						<div style="text-decoration: underline">Death Certificate</div>
						<div class="mT5 container">
							<div style="float: left; width: 36%">Certificate Number:</div>
							<div style="float: right; width: 55%">
								<form:input id="DEATH_CERT"  path="stdNycDeathDoc.deathCert" class="standardInputMedText "
									maxlength="6" />
							</div>
						</div>
						<div class="mT5 container">
							<div style="float: left; width: 36%">Certifier License
								Number:</div>
							<div style="float: right; width: 55%">
								<form:input id="DEATH_CERT_LICENSE"  path="stdNycDeathDoc.deathCertLicense"
									class="standardInputMedText " maxlength="6" />
							</div>
						</div>
						<div class="mT5 container">
							<div style="float: left; width: 36%">County of Death:</div>
							<div style="float: right; width: 55%">
								<form:select id="DEATH_COUNTY"  path="stdNycDeathDoc.deathCounty" class="standardInputMedText "
									maxlength="2">
									<form:option value="" label="--- Select ---" />
									<form:options items="${sessionScope.countyMap}" />
								</form:select>
							</div>
						</div>
					</div>
					<div style="padding: 8px 0 10px 10px; border-bottom: dotted 1px">
						<div class="mT5 container">
							<div style="float: left; width: 36%">Local Register Number:
							</div>
							<div style="float: right; width: 55%">
								<form:input id="LOCAL_REG_NO"  path="stdNycDeathDoc.localRegNo" class="standardInputMedText"
									maxlength="5" />
							</div>
						</div>
						<div class="mT5 container">
							<div style="float: left; width: 36%">Medical Record Number:
							</div>
							<div style="float: right; width: 55%">
								<form:input id="MED_RECORD_NUMBER"  path="stdNycDeathDoc.medRecordNumber"
									class="standardInputMedText" maxlength="10" />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="psDetailsHeadingDiv" class="divHeadingCls"
				style="background: #DDDDDD;">
				<span class=" mL2">Residential Details</span>
			</div>

			<div class="b1" style="margin: 15px 5px 5px 5px">
				<div class="personInfo pTRBL10">
					<span class="personLabelDoc"> Address </span> <span> <c:set
							var="RawAddress"
							value="${stdNycDeathDocVO.resStreetNo}
	                	 ${stdNycDeathDocVO.resStreetDirect} ${stdNycDeathDocVO.resStreet}
	                	  ${stdNycDeathDocVO.resStreetType} ${stdNycDeathDocVO.resAptNo}" />
						<span class="pL10"><input type="text" name="RES_STREET_NO"
							value="${RawAddress}" class="standardInputText" maxlength="39" />
					</span> <span class="pL30">City</span> <span class="pL10"> <form:input id="PERSON_CITY"
								 path="stdNycDeathDoc.personCity" class=" standardInputSmallText "
								maxlength="2" /></span> <span class="pL30">State </span> <span
						class="pL10"><form:input id="PERSON_STATE"  path="stdNycDeathDoc.personState"
								class="standardInputSmallText1" maxlength="2" /></span> <span
						class="pL30">Zip </span> <span class="pL10"> <form:input id="PERSON_ZIP"
								 path="stdNycDeathDoc.personZip"
								class="oAdrDlg nOnly standardInputSmallText1 newZip"
								maxlength="5" />
					</span> <span class="pL30">County </span> <span class="pL10"> <form:select
								id="PERSON_COUNTY"  path="stdNycDeathDoc.personCounty"
								class="standardInputSmallText1">
								<form:option value="" label="--- Select ---" />
								<form:options items="${sessionScope.countyMap}" />
							</form:select>

					</span> <br> <span class="labelunderneathAddress"> Street
							Address</span><span class="dispAddressStdRaw"> (Raw)</span>
					</span>
				</div>
				<div class="personInfo pT10 pTRBL10">
					<span class="" style="padding-left: 59px"><form:input id="PERSON_ADDRESS"
							 path="stdNycDeathDoc.personAddress" class="standardInputText" readonly="true"
							maxlength="62" /></span> <br> <span class="labelunderneathAddress">
						Street Address</span><span class="dispAddressStdRaw"> (Std)</span>

				</div>
				<br>
			</div>


			<div id="psDetailsHeadingDiv" class="divHeadingCls"
				style="background: #DDDDDD; clear: both">
				<span class=" mL2">Facility Details</span>
			</div>
			<div class="facilityDetails">
				<div class="mLRTB pB10">
					<span class="fieldHolder35 "> <span class="fLW35">Facility
							Name</span> <span class="fLW60"> <form:input id="FACILITY_NAME"  path="stdNycDeathDoc.facilityName"
								class="standardInputMedText facilityFields" maxlength="100" /></span>
					</span> <span class="fieldHolder25 "> <span class="fLW35">Facility
							Type</span> <span class="fLW60"> <form:input id="FACILITY_TYPE"  path="stdNycDeathDoc.facilityType"
								class="standardInputSmallText facilityFields" maxlength="10" />
					</span>
					</span> <span class="fieldHolder25 "> <span class="fLW35">
							Institute Code</span> <span class="fLW60"> <form:input id="INST_CODE"
								 path="stdNycDeathDoc.instCode" class="standardInputSmallText" maxlength="6" />
					</span>
					</span> <span class="fieldHolder15"> <span class="fLW35">
							PFI</span> <span class="fLW60"> <form:input id="PFI"  path="stdNycDeathDoc.pfi"
								class="standardInputSmallText1 facilityFields" maxlength="6" />
					</span>
					</span>
				</div>

				<div class="b1 " style="margin: 15px 5px 5px 5px">
					<div class="personInfo pTRBL10"
						id="deathDocumentFacilityAddressDtls">
						<span class="personLabelDoc"> Address </span> <span> <span
							class="pL10"><form:input id="FACILITY_ADDRESS"  path="stdNycDeathDoc.facilityAddress"
									class="standardInputText widgetAddressFieldsReadonly facilityFields newAddress"
									maxlength="80" /></span> <span class="pL30">City</span> <span
							class="pL10"><form:input id="FACILITY_CITY"  path="stdNycDeathDoc.facilityCity"
									class=" standardInputSmallText1 widgetAddressFieldsReadonly facilityFields newCity"
									maxlength="40" /></span> <span class="pL30">State </span> <span
							class="pL10"> <form:select id="FACILITY_STATE"  path="stdNycDeathDoc.facilityState"
									class="standardInputSmallText1 widgetAddressFieldsReadonly facilityFields newState"
									maxlength="2">
									<form:option value=""></form:option>
									<form:options items="${sessionScope.stateMap}" />
								</form:select>
						</span> <span class="pL30">Zip </span> <span class="pL10"> <form:input id="FACILITY_ZIP"
									 path="stdNycDeathDoc.facilityZip"
									class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly facilityFields newZip"
									maxlength="5" /> - <form:input id="FACILITY_PLUS4"  path="stdNycDeathDoc.facilityPlus4"
									class="standardInputSmallText1 facilityFields" maxlength="4" />
						</span> <span class="pL30">County </span> <span class="pL10"> <form:select id="FACILITY_COUNTY"
									 path="stdNycDeathDoc.facilityCounty"
									class="widgetAddressFieldsReadonly facilityFields newCounty standardInputSmallText1">
									<form:option value="" label="--- Select ---" />
									<form:options items="${sessionScope.countyMap}" />
								</form:select>

						</span> <br> <span class="labelunderneathAddress"> Street
								Address</span><span class="dispAddressStdRaw"> (Std)</span>
						</span>
					</div>
					<div class="personInfo pT10 pTRBL10">
						<span class="pL30">Phone</span> <span class="pL10"><form:input id="FACILITY_PHONE"
								 path="stdNycDeathDoc.facilityPhone"
								class="standardInputSmallText facilityFields" maxlength="20" />&nbsp;&nbsp;</span>
						<span style="float: right;" class="mr5">
							<button value="Edit"
								onclick="openAddressWidget('deathDocumentFacilityAddressDtls');return false;"
								class="displayNone editAddress">Edit</button>
						</span>

					</div>
					<br>
				</div>
			</div>
			
			
			<div id="CAUSE_CODE_SECTION">
				<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; clear:both ">
					<span class=" mL2">Causes of Death : <span class="bold" id="causeCodeDescription"><c:out value="${stdNycDeathDocVO.causeOfDeath}"></c:out></span></span>
				</div>
				<div class="mLRTB pB20" >
					<div class="pB10">	
						<span class="mT5 fieldHolder23 deathCauseCodeElement ">
							<span class="fLW35">
								ICD 10
							</span>
							<span class="fLW60">
								<form:input id="ICD10"  path="stdNycDeathDoc.icd10" class="causeCodes standardInputMedText "  maxlength="4"/>
							</span>
						</span>
						<span class="mT5 fieldHolder23 deathCauseCodeElement ">
							<span class="fLW35">
								Underlying COD
							</span>
							<span class="fLW60">
								<form:input id="UNDERLYING"  path="stdNycDeathDoc.underlying"  class="causeCodes standardInputMedText "  maxlength="4"/>
							</span>
						</span>	
					</div>		
					
					<div class="clearBoth pT10"><span style="text-decoration: underline;">Additional CODs Reported</span> :</div>
					
					<div class="clearBoth ">
					
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 1</span> 
							<span class="fLW60"> <form:input id="CAUSE1"  path="stdNycDeathDoc.cause1" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 2</span> 
							<span class="fLW60"> <form:input id="CAUSE2"  path="stdNycDeathDoc.cause2" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 3</span> 
							<span class="fLW60"> <form:input id="CAUSE3"  path="stdNycDeathDoc.cause3" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span> 
						<span class="fieldHolder23  deathCauseCodeElement ">
							<span class="fLW25"> Cause 4</span> 
							<span class="fLW60"> <form:input id="CAUSE4"  path="stdNycDeathDoc.cause4" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
					</div>
					
					<div class="clearBoth ">
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 5</span> 
							<span class="fLW60"> <form:input id="CAUSE5"  path="stdNycDeathDoc.cause5" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
					
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 6</span> 
							<span class="fLW60"> <form:input id="CAUSE6"  path="stdNycDeathDoc.cause6" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 7</span> 
							<span class="fLW60"> <form:input id="CAUSE7"  path="stdNycDeathDoc.cause7" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 8</span> 
							<span class="fLW60"> <form:input id="CAUSE8"  path="stdNycDeathDoc.cause8" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
					</div>
					
					<div class="clearBoth ">
					
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 9</span> 
							<span class="fLW60"> <form:input id="CAUSE9"  path="stdNycDeathDoc.cause9" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 10</span> 
							<span class="fLW60"> <form:input id="CAUSE10"  path="stdNycDeathDoc.cause10" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 11</span> 
							<span class="fLW60"> <form:input id="CAUSE11"  path="stdNycDeathDoc.cause11" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 12</span> 
							<span class="fLW60"> <form:input id="CAUSE12"  path="stdNycDeathDoc.cause12" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
					</div>
					
					<div class="clearBoth ">
					
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 13</span> 
							<span class="fLW60"> <form:input id="CAUSE13"  path="stdNycDeathDoc.cause13" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 14</span> 
							<span class="fLW60"> <form:input id="CAUSE14"  path="stdNycDeathDoc.cause14" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 15</span> 
							<span class="fLW60"> <form:input id="CAUSE15"  path="stdNycDeathDoc.cause15" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
					
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 16</span> 
							<span class="fLW60"> <form:input id="CAUSE16"  path="stdNycDeathDoc.cause16" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
					</div>
					
					<div class="clearBoth ">
					
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 17</span> 
							<span class="fLW60"> <form:input id="CAUSE17"  path="stdNycDeathDoc.cause17" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 18</span> 
							<span class="fLW60"> <form:input id="CAUSE18"  path="stdNycDeathDoc.cause18" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 19</span> 
							<span class="fLW60"> <form:input id="CAUSE19"  path="stdNycDeathDoc.cause19" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 20</span> 
							<span class="fLW60"> <form:input id="CAUSE20"  path="stdNycDeathDoc.cause20" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
					</div>
					
					<div class="clearBoth pT10 textAlignLeft causeOfDeathLabel" >
						<span class="highlightGreenColor bold">* Note: Input Fields(Highlighted in Green Border) are HIV Death cause related codes.</span>
  					
  						<br><br>
						
						<span class="highlightOrangeRedColor bold">* Note: Input Fields(Highlighted in Orange Border) are Oppurtunity Infection cause related codes.</span>
							
					</div>
						
				</div>																			 
			</div>
	
		</div>
		<div class="mT50">&nbsp;&nbsp;&nbsp;</div>
		<!-- dummy div -->
		<div id="button-row" class="button-row documentButtons center">
			<c:if
				test="${userPermissions['99'] != null && stdNycDeathDocVO.stateno != null && stdNycDeathDocVO.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null}">
				<input type="button" id="tButton"
					class="triggetMsgBtnclick button rounded blue effect-3 displayNone"
					value="Trigger Assignment" onclick="loadSurvAssignmentPopUp()" />
			</c:if>

			<c:if test="${param.hasEditFunctionality == true}">
						<input type="button" id="eButton"  class="editBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="customDocumentEditViewerStyles()" />
					</c:if>
					
				<c:choose>
						<c:when  test="${param.hasDeleteFunctionality == true}" >	
								<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
								<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
						</c:when>
						<c:when test="${userPermissions['90'] != null && stdNycDeathDocVO.stateno != null && stdNycDeathDocVO.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null}">
								<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
						</c:when>
					</c:choose>




			<input type="button" id="sButton"
				class="labBtnclick button rounded blue effect-3" value="Save"
				onclick="saveForm()" /> <input type="button" id="cButton"
				class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />
		</div>
		<input type="hidden" id="transactionResult"
			value="${requestScope.TransactionResult}" />
	</div>
</form:form>

<script src="${staticPath}/js/docs/dthDoc.js?v=${buildDate}"></script>

