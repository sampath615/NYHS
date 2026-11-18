<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="${staticPath}/css/docs/dthForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />

<style>
	.b1{
	    border-style: dotted;
	    border-width: 1px;
	    margin: 5px;
	}
	.pL30{
	    padding-left: 30px;
	}
	
	.pL10{
	    padding-left: 10px;
	}
	.pT20{
		padding-top: 20px;
	}
	.pTRBL10 {
	    padding: 10px 10px 0;
	}
	.labelunderneath6{
	 	padding-left:205px;
	 }
</style>

<form:form modelAttribute="stdDeathDocVO" id="stdDeathDocVO" cssClass="documentDescription">		
<form:errors path="*" cssClass="errorblock" element="div"/>

	<div id="dthDoc" style="background-color: transparent">
		
		<div style="padding-left:43%; " class="headingCls">
				DEATH DOCUMENT
		</div>
		<div style="padding-bottom:10px; text-align: right">
            <span class="mL10">Document ID: </span>
			<span class=" "><form:input id="DOCUMENT_ID"  path="stdDeathDoc.documentId" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
        </div>
		<div class="container pB10 w100 pT20">
            <div style="float:left; width: 40%">
	            <div >
	               <span class="ml10"> <spring:message code="label.stateNo" />: </span>
				<span class="ml10"><form:input id="STATENO"  path="stateno" class="standardInputSmallText" maxlength="10" readonly="true"/> </span>
	            </div>    
            </div>
            <div style="float:left;  width: 30%">
	            <div style="">
	                <span class="mL10">	eHARS Import Date: </span>
	                <fmt:formatDate value="${stdDeathDocVO.stdDeathDoc.eharsImportDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
					<span class="mL10"><form:input id="EHARS_IMPORT_DATE"  path="stdDeathDoc.eharsImportDate" value= "${theFormattedDate}" class="standardInputSmallText" readonly="true"/></span>
	            </div>
            </div>
            <div style="float:left; width: 30%; text-align: right">
	            <div style="">
 	                <span class="mL10">NYC SHIP Date: </span>
 	                <fmt:formatDate value="${stdDeathDocVO.stdDeathDoc.nycShipDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedNYC_SHIP_DATE_TEXT" ></fmt:formatDate>
 			 		<span class="mL10 "><form:input id="NYC_SHIP_DATE"  path="stdDeathDoc.nycShipDate" value="${theFormattedNYC_SHIP_DATE_TEXT}" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
 	            </div>                   
            </div>     
   		</div>
		
		<jsp:include page="statenoDetails.jsp"></jsp:include>
		
		<!--  Person details -->
		
		<div class="container" style="border:1px dotted; width:100%; padding-bottom:5px;">	 
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Person Details</span>
			</div>
			<div class="mLRTB pB20">
				<span class="fieldHolder25">
					<span class="fLW35"> First Name</span> 
					<span class="fLW60"> <form:input id="FNAME"  path="stdDeathDoc.fname" class="standardInputSmallText" maxlength="15"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Middle Name</span>
					<span class="fLW60"> <form:input id="MNAME"  path="stdDeathDoc.mname" class="standardInputSmallText" maxlength="1"/>  </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Last Name</span>
					<span class="fLW60"> <form:input  path="stdDeathDoc.lname" id="lName" title="Last Name" class="standardInputSmallText required" maxlength="20"/>  </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Soundex</span>
					<span class="fLW60"><form:input id="SOUNDEX"  path="stdDeathDoc.soundex" class="standardInputSmallText" maxlength="3"/></span>	
				</span>	
			</div>
			<div class="mLRTB pB20">				
				<span class="fieldHolder25">
					<span class="fLW50"> Father's Last Name</span> 
					<span class="fLW50"> <form:input id="FATHER_LNAME"  path="stdDeathDoc.fatherLname" class="standardInputSmallTextFname" maxlength="20"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW50"> Mother's Maiden Name</span>
					<span class="fLW50"> <form:input id="MOTHER_MAIDEN"  path="stdDeathDoc.motherMaiden" class="standardInputSmallTextFname" maxlength="20"/>  </span>
				</span>										
			</div>
			<div class="mLRTB pB20">
				<span class="fieldHolder25 ">
					<span class="fLW35"> Date of Birth</span>
					<span class="fLW60"> <form:input id="dobValidation"  path="stdDeathDoc.dobText" class="dPicker standardInputSmallText" maxlength="15"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Birth State </span>
					<span class="fLW60"><form:input id="BIRTH_PLACE"  path="stdDeathDoc.birthState"  class="standardInputSmallText" maxlength="2"/></span>	
				</span>	
				<span class="fieldHolder25 ">
					<span class="fLW35"> Date of Death </span>
					<span class="fLW60"><form:input id="dodValidation"  path="stdDeathDoc.dodText" value="${dodText}" title="Date of death" class="dPicker required standardInputSmallText" maxlength="15"/></span>	
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Age at Death </span>
					<span class="fLW60"><form:input  path="stdDeathDoc.ageDeath" id="deathAge" class="standardInputSmallText" maxlength="3"/></span>	
				</span>	
			</div>

			<div class="mLRTB pB20">		
				
				<span class="fieldHolder25 ">
					<span class="fLW35"> Delivery Date</span>
					<span class="fLW60"> <form:input id="ddValidation" path="stdDeathDoc.deliveryDateText" class="standardInputSmallText" maxlength="15"/>  </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> SSN</span>
					<span class="fLW60"> <form:input id="SSN" path="stdDeathDoc.ssn" class="standardInputSmallText" maxlength="15"/>  </span>
				</span>					
			</div>

			<!--  Race details -->
		
			<div id="raceEthnicity" class="container" style="border:1px dotted; padding:5px 5px 0 0;">
				<div style="float:left; width:35%;">	
					<div style="padding:0 0 10px 20px;">
							Race 				
	
						<div style="">
	
							<form:checkbox id="RACE_WHITE1"  path="stdDeathDoc.raceWhite" value="1" />White	
							<br>
							<form:checkbox id="RACE_BLACK1"  path="stdDeathDoc.raceBlack" value="1" /> African American
							<br>
							<form:checkbox id="RACE_ASIAN1"  path="stdDeathDoc.raceAsian" value="1" /> Asian
							<br>
							<form:checkbox id="RACE_PACIFIC1"  path="stdDeathDoc.racePacific" value="1" /> Pacific Islander
							<br>
							<form:checkbox id="RACE_INDIAN1"  path="stdDeathDoc.raceIndian" value="1" /> American Indian/Alaskan Native
							<br>
	
							<form:checkbox id="RACE_OTHER1"  path="stdDeathDoc.raceOther" value="1" /> Other
							<br>
							<form:checkbox id="RACE_UNKNOWN1"  path="stdDeathDoc.raceUnknown" value="1" /> Unknown
						</div>	
					</div>
					<div style="padding:10px 0 10px 20px; border-top: dotted 1px;">
						Ethnicity  (Hispanic or Latino)
						<div style="">
							<form:radiobutton id="ETHNICITY1"  path="stdDeathDoc.ethnicity" value="01" /> Yes <br>
							<form:radiobutton id="ETHNICITY2"  path="stdDeathDoc.ethnicity" value="02" /> No <br>
							<form:radiobutton id="ETHNICITY3"  path="stdDeathDoc.ethnicity" value="09" /> Unknown <br>
						</div>	
					</div>
					<div style="padding:10px 0 10px 20px; border-top: dotted 1px;">						
						<div class="floatLeft pR10" style="border-right: dotted 1px;">
							<div class=""> Current Gender </div>
							<div  class="">
								<div> <form:radiobutton id="SEX1"  path="stdDeathDoc.currentGender" value="M" /> Male </div>
								<div> <form:radiobutton id="SEX2"  path="stdDeathDoc.currentGender" value="F" /> Female </div>
								<div> <form:radiobutton id="SEX3"  path="stdDeathDoc.currentGender" value="U" />Unknown</div>		
							</div>
						</div>
						<div class="floatLeft pL10">
							<div class=""> Transgender </div>
							<div  class="">
								<div> <form:radiobutton id="SEX4"  path="stdDeathDoc.currentGender" value="X" /> MTF </div>		
								<div> <form:radiobutton id="SEX5"  path="stdDeathDoc.currentGender" value="Y" /> FTM </div>		
								<div> <form:radiobutton id="SEX6"  path="stdDeathDoc.currentGender" value="T" /> Unspecified </div>														
							</div>
						</div>
					</div>
				
				</div>
				
				
				<div style="float:left;  width:29% ; height: 371px; border-right: dotted 1px;  border-left:1px dotted;">
					
					<div style="padding:5px 0 10px 20px; border-bottom: dotted 1px">
						<div class=""> Marital Status </div>
						<div class="">						
							<div> <form:radiobutton id="MARITAL_STATUS1"  path="stdDeathDoc.maritalStatus" value="1" /> Never married </div>
							<div> <form:radiobutton id="MARITAL_STATUS2"  path="stdDeathDoc.maritalStatus" value="2" /> Married </div>	
							<div> <form:radiobutton id="MARITAL_STATUS3"  path="stdDeathDoc.maritalStatus" value="3" /> Widowed </div>	
							<div> <form:radiobutton id="MARITAL_STATUS4"  path="stdDeathDoc.maritalStatus" value="4" /> Divorced </div>
							<div> <form:radiobutton id="MARITAL_STATUS5"  path="stdDeathDoc.maritalStatus" value="5" /> Separated </div>
							<div> <form:radiobutton id="MARITAL_STATUS6"  path="stdDeathDoc.maritalStatus" value="6" /> Domestic Partnership (NYC only) </div>
							<div> <form:radiobutton id="MARITAL_STATUS7"  path="stdDeathDoc.maritalStatus" value="7" /> Other (NYC Only) </div>
							<div> <form:radiobutton id="MARITAL_STATUS8"  path="stdDeathDoc.maritalStatus" value="9" /> Unknown (blank) </div>
						</div>	
					</div>
					
					<div style="padding:15px 0 10px 20px; ">
						<div class=""> Pregnant </div>
						<div class="">						
							<div> <form:radiobutton id="PREGNANT1"  path="stdDeathDoc.pregnant" value="0" /> Not pregnant in last year </div>
						<div> <form:radiobutton id="PREGNANT2"  path="stdDeathDoc.pregnant" value="1" /> pregnant at time of death </div>
						<div> <form:radiobutton id="PREGNANT3"  path="stdDeathDoc.pregnant" value="2" /> Not pregnant, but pregnant within 42 days of death </div>	
						<div> <form:radiobutton id="PREGNANT4"  path="stdDeathDoc.pregnant" value="3" /> Not pregnant, but pregnant within 43 days to 1 year before death </div>	
						<div> <form:radiobutton id="PREGNANT5"  path="stdDeathDoc.pregnant" value="4" /> Unknown if pregnant within the past year </div>	
						<div> <form:radiobutton id="PREGNANT6"  path="stdDeathDoc.pregnant" value="" /> Not stated </div>	
						</div>	
					</div>				
					
				</div>
				<div style="float:left; width:35% ;" >
					<div style="padding:0 0 10px 10px; border-bottom: dotted 1px">
						<div style="text-decoration: underline">Death Certificate</div>
						<div class="mT5 container">
							<div style="float:left; width:36%">
								Certificate Number:
							</div>
							<div style="float:right; width:55%">
								<form:input id="CERT"  path="stdDeathDoc.deathCert" class="standardInputMedText "  maxlength="6"/>
							</div>
						</div>
						<div class="mT5 container">
							<div style="float:left; width:36%">
								Certifier License Number:
							</div>
							<div style="float:right; width:55%">
								<form:input id="DEATH_CERT_LICENSE"  path="stdDeathDoc.deathCertLicense" class="standardInputMedText "  maxlength="6"/>
							</div>
						</div>
						<div class="mT5 container">
							<div style="float:left; width:36%">
								County of Death:
							</div>
							<div style="float:right; width:55%">
								<form:input id="DEATH_CNTY"  path="stdDeathDoc.deathCounty" class="standardInputMedText "  maxlength="2"/>
							</div>
						</div>
					</div>
					<div style="padding:8px 0 10px 10px; border-bottom: dotted 1px">
						<div class="mT5 container">
							<div style="float:left; width:36%">
								Local Register Number:
							</div>
							<div style="float:right; width:55%">
								<form:input id="LOCAL_REG_NO"  path="stdDeathDoc.localRegNo"  class="standardInputMedText"  maxlength="5"/>
							</div>
						</div>
						<div class="mT5 container">
							<div style="float:left; width:36%">
								Medical Record Number:
							</div>
							<div style="float:right; width:55%">
								<form:input id="MED_RECORD_NUMBER"  path="stdDeathDoc.medRecordNumber" class="standardInputMedText"  maxlength="10"/>
							</div>
						</div>
					</div>									
				</div>			
			</div>	
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Residential Details</span>
			</div>
			
			<div class="b1" style="margin: 15px 5px 5px 5px">
	            <div class="personInfo pTRBL10">
	                <span class="personLabelDoc"> Address </span>
	                <span> 
	                	<c:set var="RawAddress" value="${stdDeathDocVO.resStreetNo} ${stdDeathDocVO.resStreetDirect} ${stdDeathDocVO.resStreet} ${stdDeathDocVO.resStreetType} ${stdDeathDocVO.resAptNo}" />
	                	<span class="pL10"><input type="text" name="RES_STREET_NO" value="${RawAddress}" class="standardInputText" maxlength="39"/>  </span>
	                    
	                    
	                    <span class="pL30">City</span>
	                    <span class="pL10"> <form:input id="PERSON_CITY"  path="stdDeathDoc.personCity"  class=" standardInputSmallText "  maxlength="2"/></span>
	                    
	                    <span class="pL30">State </span>
	                    <span  class="pL10"><form:input id="PERSON_STATE"  path="stdDeathDoc.personState" class="standardInputSmallText1" maxlength="2"/></span>
	                    
	                    <span class="pL30">Zip </span>
	                    <span class="pL10"> 
	                         <form:input id="PERSON_ZIP"  path="stdDeathDoc.personZip" class="oAdrDlg nOnly standardInputSmallText1 newZip" maxlength="5" /> 
	                    </span>
	                    <span class="pL30">County </span>
		                   <span class="pL10"> 
		                   
		                   <form:select id="PERSON_COUNTY"    path="stdDeathDoc.personCounty" class="standardInputSmallText1">
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.countyMap}"/>
							    </form:select>
							    
		                </span>       	                    
	                    <br>
	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
	                </span>
	            </div>
	            <div class="personInfo pT10 pTRBL10">
	            	<span  class="" style="padding-left:59px"><form:input id="PERSON_ADDRESS"  path="stdDeathDoc.personAddress" class="standardInputText" readonly="true" maxlength="62"/></span>
	                <br>               
	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
	                    
	            </div><br>
	        </div>
			
						
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; clear:both">
				<span class=" mL2">Facility Details</span>
			</div>
			<div class="facilityDetails">
				<div class="mLRTB pB10">
					<span class="fieldHolder35 ">
						<span class="fLW35">Facility Name</span>
						<span class="fLW60"> <form:input id="FACILITY_NAME"  path="stdDeathDoc.facilityName" class="standardInputMedText facilityFields" maxlength="100"/></span>
					</span>
					<span class="fieldHolder25 ">
						<span class="fLW35">Facility Type</span>
						<span class="fLW60"> <form:input id="FACILITY_TYPE"  path="stdDeathDoc.facilityType" class="standardInputSmallText facilityFields"  maxlength="10"/>  </span>
					</span>
					<span class="fieldHolder25 ">
						<span class="fLW35"> Institute Code</span>
						<span class="fLW60"> <form:input id="INST_CODE"  path="stdDeathDoc.instCode" class="standardInputSmallText" maxlength="6"/>  </span>
					</span>
					<span class="fieldHolder15">
						<span class="fLW35"> PFI</span> 
						<span class="fLW60"> <form:input id="PFI"  path="stdDeathDoc.pfi" class="standardInputSmallText1 facilityFields" maxlength="6"/> </span>
					</span>
				</div>	
	
				<div class="b1 " style="margin: 15px 5px 5px 5px">
		            <div class="personInfo pTRBL10" id="deathDocumentFacilityAddressDtls">
		                <span class="personLabelDoc"> Address </span>
		                <span> 
		                    <span class="pL10"><form:input id="FACILITY_ADDRESS"  path="stdDeathDoc.facilityAddress" class="standardInputText widgetAddressFieldsReadonly facilityFields newAddress" maxlength="80"/></span>
		                    
		                    
		                    <span class="pL30">City</span>
		                    <span class="pL10"><form:input id="FACILITY_CITY"  path="stdDeathDoc.facilityCity"  class=" standardInputSmallText1 widgetAddressFieldsReadonly facilityFields newCity"  maxlength="40"/></span>
		                    
		                    <span class="pL30">State </span>
		                    <span  class="pL10">
		                    	<form:select id="FACILITY_STATE"  path="stdDeathDoc.facilityState" class="standardInputSmallText1 widgetAddressFieldsReadonly facilityFields newState" maxlength="2">
		                    			<form:option value=""></form:option>
										<form:options items="${sessionScope.stateMap}"/>
								</form:select>
		                    </span>
		                    
		                    <span class="pL30">Zip </span>
		                    <span class="pL10"> 
		                         <form:input id="FACILITY_ZIP"  path="stdDeathDoc.facilityZip" class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly facilityFields newZip" maxlength="5" /> - <form:input id="FACILITY_PLUS4"  path="stdDeathDoc.facilityPlus4" class="standardInputSmallText1 facilityFields" maxlength="4"/>
		                    </span>
		                    
		                    <span class="pL30">County </span>
		                    <span class="pL10"> 
		                    	
			                   <form:select id="FACILITY_COUNTY"  path="stdDeathDoc.facilityCounty"   class="widgetAddressFieldsReadonly facilityFields newCounty standardInputSmallText1" >
									        <form:option value="" label="--- Select ---" />
											<form:options items="${sessionScope.countyMap}"/>
								    </form:select>
		                    	
		                    </span>
		                    
		                    <br>
		                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
		                </span>
		            </div>
		            <div class="personInfo pT10 pTRBL10">
		            	<span class="pL30">Phone</span>
		                <span class="pL10"><form:input id="FACILITY_PHONE"  path="stdDeathDoc.facilityPhone" class="standardInputSmallText facilityFields" maxlength="20"/>&nbsp;&nbsp;</span>
		                <span style="float: right;" class="mr5">
							<button value="Edit" onclick="openAddressWidget('deathDocumentFacilityAddressDtls');return false;" class="displayNone editAddress">Edit</button>
						 </span>
		            </div><br>
		         </div>
	        </div>
			
			<div id="CAUSE_CODE_SECTION">
				<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; clear:both ">
					<span class=" mL2">Causes of Death : <span class="bold" id="causeCodeDescription"><c:out value="${stdDeathDocVO.causeOfDeath}"></c:out></span></span>
				</div>
				<div class="mLRTB pB20" >
					<div class="pB10">	
						<span class="mT5 fieldHolder23 deathCauseCodeElement ">
							<span class="fLW35">
								ICD 10
							</span>
							<span class="fLW60">
								<form:input id="ICD10"  path="stdDeathDoc.icd10" class="causeCodes standardInputMedText "  maxlength="4"/>
							</span>
						</span>
						<span class="mT5 fieldHolder23 deathCauseCodeElement ">
							<span class="fLW35">
								Underlying COD
							</span>
							<span class="fLW60">
								<form:input id="UNDERLYING"  path="stdDeathDoc.underlying"  class="causeCodes standardInputMedText "  maxlength="4"/>
							</span>
						</span>	
					</div>		
					
					<div class="clearBoth pT10"><span style="text-decoration: underline;">Additional CODs Reported</span> :</div>
					
					<div class="clearBoth ">
					
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 1</span> 
							<span class="fLW60"> <form:input id="CAUSE1"  path="stdDeathDoc.cause1" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 2</span> 
							<span class="fLW60"> <form:input id="CAUSE2"  path="stdDeathDoc.cause2" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 3</span> 
							<span class="fLW60"> <form:input id="CAUSE3"  path="stdDeathDoc.cause3" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span> 
						<span class="fieldHolder23  deathCauseCodeElement ">
							<span class="fLW25"> Cause 4</span> 
							<span class="fLW60"> <form:input id="CAUSE4"  path="stdDeathDoc.cause4" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
					</div>
					
					<div class="clearBoth ">
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 5</span> 
							<span class="fLW60"> <form:input id="CAUSE5"  path="stdDeathDoc.cause5" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
					
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 6</span> 
							<span class="fLW60"> <form:input id="CAUSE6"  path="stdDeathDoc.cause6" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 7</span> 
							<span class="fLW60"> <form:input id="CAUSE7"  path="stdDeathDoc.cause7" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 8</span> 
							<span class="fLW60"> <form:input id="CAUSE8"  path="stdDeathDoc.cause8" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
					</div>
					
					<div class="clearBoth ">
					
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 9</span> 
							<span class="fLW60"> <form:input id="CAUSE9"  path="stdDeathDoc.cause9" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 10</span> 
							<span class="fLW60"> <form:input id="CAUSE10"  path="stdDeathDoc.cause10" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 11</span> 
							<span class="fLW60"> <form:input id="CAUSE11"  path="stdDeathDoc.cause11" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 12</span> 
							<span class="fLW60"> <form:input id="CAUSE12"  path="stdDeathDoc.cause12" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
					</div>
					
					<div class="clearBoth ">
					
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 13</span> 
							<span class="fLW60"> <form:input id="CAUSE13"  path="stdDeathDoc.cause13" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 14</span> 
							<span class="fLW60"> <form:input id="CAUSE14"  path="stdDeathDoc.cause14" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 15</span> 
							<span class="fLW60"> <form:input id="CAUSE15"  path="stdDeathDoc.cause15" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
					
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 16</span> 
							<span class="fLW60"> <form:input id="CAUSE16"  path="stdDeathDoc.cause16" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
					</div>
					
					<div class="clearBoth ">
					
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 17</span> 
							<span class="fLW60"> <form:input id="CAUSE17"  path="stdDeathDoc.cause17" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 18</span> 
							<span class="fLW60"> <form:input id="CAUSE18"  path="stdDeathDoc.cause18" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 19</span> 
							<span class="fLW60"> <form:input id="CAUSE19"  path="stdDeathDoc.cause19" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
						<span class="fieldHolder23 deathCauseCodeElement ">
							<span class="fLW25"> Cause 20</span> 
							<span class="fLW60"> <form:input id="CAUSE20"  path="stdDeathDoc.cause20" style="width:135px" class="causeCodes standardInputSmallText" maxlength="4"/> </span>
						</span>
					</div>
					
					<div class="clearBoth pT10 textAlignLeft causeOfDeathLabel" >
						<span class="highlightGreenColor bold">* Note: Input Fields(Highlighted in Green Border) are HIV Death cause related codes.</span>
  					
  						<br><br>
						
						<span class="highlightOrangeRedColor bold">* Note: Input Fields(Highlighted in Orange Border) are Oppurtunity Infection cause related codes.</span>
							
					</div>
						
			</div>																			 
	</div>
	 <div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
		 <div id="button-row" class="button-row documentButtons center">
		 				<c:if  test="${userPermissions['99'] != null && stdDeathDocVO.stateno != null && stdDeathDocVO.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null}" >	
								<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3 displayNone"  value="Trigger Assignment"  onclick="loadSurvAssignmentPopUp()"/>
						</c:if>
						<c:if test="${param.hasEditFunctionality == true}">
							<input type="button" id="eButton"  class="editBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="customDocumentEditViewerStyles()" />
						</c:if>
							<c:choose>
								<c:when  test="${param.hasDeleteFunctionality == true}" >	
										<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
										<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
								</c:when>
								<c:when test="${userPermissions['90'] != null && stdDeathDocVO.stateno != null && stdDeathDocVO.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null}">
										<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
								</c:when>
							</c:choose>

					<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Save" onclick="saveForm()"/>	 
					<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />				
		</div>		
	<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
	</div>
</form:form>															

<script src="${staticPath}/js/docs/dthDoc.js?v=${buildDate}"></script>

