<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${staticPath}/js/docs/prfDoc.js?v=${buildDate}"></script>
<link href="${staticPath}/css/docs/prfForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<form:form modelAttribute="nycPRFDocVO" id="nycPRFDocVO" cssClass="documentDescription">		

<form:errors path="*" cssClass="errorblock" element="div" />

	<div id="prfDocTab" style="background-color: transparent">		
		<div style="padding-left:43%; " class="headingCls">
				NYC PRF DOCUMENT
		</div>
		
		
		<!-- adding fields ssn  -->
		<div class="container w100 pT20 pB10">
            <div style="float:left; width: 40%">
	            <div>
	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
                    <span class="ml10"><form:input id="STATENO"  path="stateno"  class="standardInputSmallText" style="width:180px" maxlength="10" readonly="true"/> </span>
	            </div>    
            </div>
            <div style="float:left; width: 30%">
	            <div >
	                <span class=" ml10">	eHARS Import Date: </span>
	                <fmt:formatDate value="${nycPRFDocVO.stdNycprfDoc.eharsImportDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
					<span class=" ml10"><form:input id="EHARS_IMPORT_DATE"  path="stdNycprfDoc.eharsImportDate" value="${theFormattedDate}"  class="standardInputSmallText ml10" readonly="true"/></span>
	            </div>                  
            </div>
            <div style="float:left; width: 30%; text-align: right">
            	<div>
	                <span class="mL10">Document ID: </span>
					<span class="mL10 "><form:input id="DOCUMENT_ID"  path="stdNycprfDoc.documentId" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
	            </div> 
            </div>     
   		</div>
   		
   		<jsp:include page="statenoDetails.jsp"></jsp:include>
   		<!-- 
		<div>
			<input type="radio" name="PRFDoc" class="PrfTTH" value="PrfTTH" checked>Edit PRF and TTH
			<input type="radio" name="PRFDoc" class="mL30 TTHonly" value="TTHonly">Edit TTH only
		</div>   -->

		<!--  Person details -->
		<div id="prfContent">
			<div class="container mT10" id="patientInformation">	 
				<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
					<span class=" mL2">Patient Information</span>
				</div>
				<ol type="a">
					<li>
						<div class="personInfo">
							<span class="personLabelDoc bold">Name </span>
							<span> 
								<span class="pL150"><form:input  path="stdNycprfDoc.lname" id="lName" class="standardInputMedText" maxlength="25" /></span> 
								<span  class="pL30"><form:input id="FNAME"  path="stdNycprfDoc.fname" class="standardInputMedText" maxlength="20" /></span>
								<span  class="pL30"><form:input id="MNAME"  path="stdNycprfDoc.mname" class="standardInputMedText" maxlength="25" /></span><br>
								<span class="labelunderneath2" style="display: inline-block"> Last</span>
								<span class="labelunderneath1"> First</span>
								<span class="labelunderneath1"> Middle</span>
							</span>
						</div>
						<div class="personInfo">
							<span class="personLabelDoc bold">Alternate name </span>
							<span> 
								<span style="padding-left:95px"><form:input id="ALT_NAME"  path="stdNycprfDoc.altName" class="standardInputMedText" maxlength="25" /></span><br>
						
							</span>
						</div>
												
						<div class="b1" style="margin: 15px 5px 10px 5px">
			 	            <div class="personInfo pTRBL10">
			 	                <span class="personLabelDoc bold"> Address </span>
			 		                <span> 	                	
				 	                	<span class="pL10"><form:input id="rawPersonAddress"  path="rawPersonAddress" class="standardInputText newAddress" style="width:300px" maxlength="62" />  </span>
				 	                    
				 	                    <span class="pL30 bold">Apartment</span>
				 	                    <span class="pL10"><form:input id="APARTMENT"  path="stdNycprfDoc.apartment" class="oAdrDlg standardInputSmallText newApartment" maxlength="30"/></span>
				 	                    
				 	                    <span class="pL30 bold">City</span>
				 	                    <span class="pL10"><form:input id="PERSON_CITY"  path="stdNycprfDoc.personCity" class="oAdrDlg standardInputSmallText1 newCity" maxlength="30"/></span>
				 	                    
				 	                    <span class="pL30 bold">State </span>
				 	                    <span  class="pL10">
											<form:input id="PERSON_STATE"  path="stdNycprfDoc.personState" cssClass=" standardInputSmallText1 "  maxlength="2"/>
										</span>
				 	                    
				 	                    <span class="pL30 bold">Zip </span>
				 	                    <span class="pL10"> 
				 	                        <form:input id="PERSON_ZIP"  path="stdNycprfDoc.personZip" class="standardInputSmallText1" maxlength="5"/>
				 	                    </span>
				 	                    <span class="pL30 bold">County </span>
				 		                   <span class="pL10"> 
												<form:select id="PERSON_COUNTY"  path="stdNycprfDoc.personCounty"  cssClass="standardInputSmallText1" > 
													<form:option value=""></form:option>
													<form:options items="${sessionScope.countyMap}"/>
												</form:select>
					 		               </span>       	                    
				 	                    <br>
				 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
				 	                </span>
			 	            </div>
			 	            <div class="personInfo pT10 pTRBL10">
			 	            	<span  class="" style="padding-left:59px"><form:input id="PERSON_ADDRESS"  path="stdNycprfDoc.personAddress" class="standardInputText dispAddressStdRaw" style="width:300px" maxlength="62" /></span>
			 	              	 	                 
			 	                 <span class="pL30 bold"> Phone</span>
			 					 <span class="pL10"> <form:input id="PERSON_PHONE"  path="stdNycprfDoc.personPhone" class="pNumber standardInputSmallText" maxlength="20"/></span>
			 	                <br>               
			 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
			 	                    
			 	            </div>
			 	        </div>

						<div class="personInfo pB10">
							<span class="fieldHolder25 ">
								<span class="fLW35 bold">Date of Birth </span>
								<fmt:formatDate value="${nycPRFDocVO.stdNycprfDoc.dob}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDob" />
								<span  class="fLW60"><form:input  path="stdNycprfDoc.dob" id="dobValidation" class="dPicker standardInputSmallText" value="${theFormattedDob}"  maxlength="10"/></span>
							</span>
							<span class="fieldHolder25 ">
								<span class="fLW35 bold">Date of Death </span>
								<span class="fLW60"> 
									<form:input  path="stdNycprfDoc.dodText" id="dodValidation" class="dPicker standardInputSmallText"  maxlength="10"/>
								</span>
							</span>						
							<span class="fieldHolder50 ">
								<span class="fLW35 bold">SSN </span>
								<span class="fLW60"> 
									<form:input id="SSN"  path="stdNycprfDoc.ssn" class="standardInputSmallText"  maxlength="9"/>
								</span>
							</span>						
						</div><br>
						<div class="personInfo pT10 clear">
							<span class="fieldHolder25 ">
								<span class="fLW35 bold">Gender </span>
								<span class=""> 
									<span> <form:radiobutton id="CURRENT_GENDER1"  path="stdNycprfDoc.currentGender" value="M" /> Male </span>
									<span> <form:radiobutton id="CURRENT_GENDER2"  path="stdNycprfDoc.currentGender" value="F" /> Female </span>
								</span>
							</span>
							<span class="fieldHolder25 ">
								<span class="fLW35 bold">Transgender </span>
								<span class=""> 
									<span> <form:radiobutton id="CURRENT_GENDER3"  path="stdNycprfDoc.currentGender" value="X" /> MTF </span>
									<span> <form:radiobutton id="CURRENT_GENDER4"  path="stdNycprfDoc.currentGender" value="Y" /> FTM </span>
								</span>
							</span>
							<span class="fieldHolder50 ">
								<span class="fLW35 bold">Additional gender identity</span>
								<span  class="fLW60"><form:input id="ADDITIONAL_GENDER_IDENTITY"  path="stdNycprfDoc.additionalGenderIdentity" class="standardInputText"  /></span>
							</span>	<br>					
						</div><br>
						<div class="personInfo clear">
							<span class="fieldHolder25 ">
								<span class="fLW35 bold">Birth Sex </span>
								<span class=""> 
									<span> <form:radiobutton id="BIRTH_SEX1"  path="stdNycprfDoc.birthSex" value="M" /> Male </span>
									<span> <form:radiobutton id="BIRTH_SEX2"  path="stdNycprfDoc.birthSex" value="F" /> Female </span>
								</span>
							</span>
							<span class="fieldHolder25 ">
								<span class="fLW35 bold">Patient Record No. </span>
								<span  class="fLW60"><form:input id="MED_RECORD_NUMBER"  path="stdNycprfDoc.medRecordNumber" class="standardInputSmallText"  maxlength="17"/></span>
							</span>
							<span class="fieldHolder50 ">
								<span class="fLW35 bold">Expected Date of Delivery</span>
								<span class=""> 
									<span> <form:input id="EDC"  path="stdNycprfDoc.edc"  class="dPicker mask standardInputSmallText " datevalidations="partialDateForAllorFuture" maxlength="20" title="EDC"/> </span>
								</span>
							</span>
							
							
							
							
						</div>
						<div class="personInfo pT10 clear">
							<span class="fieldHolder50 ">
								<span class="fLW20 bold">Primary Language </span>
								<span class="fLW75">
									<c:forEach items="${codedPrimaryLanguageValues}" var="primaryLanguage" varStatus="primaryLanguageStatus">
										<span><form:radiobutton id="PRIMARY_LANGUAGE${primaryLanguageStatus.count}"  path="stdNycprfDoc.primaryLanguage" value="${primaryLanguage.LANG_CODE}" /> ${primaryLanguage.LANG_DESC}</span>
										<c:if test="${primaryLanguage.LANG_CODE == 'O'}">
											<span class="mL20"><form:input id="PRIMARY_LANGUAGE_OTHER"  path="stdNycprfDoc.primaryLanguageOther" class="standardInputSmallText" style="width:220px" maxlength="50" title="Other Primary Language"/></span>
										</c:if>
									</c:forEach>
								</span>
							</span>
							
							<span class="fieldHolder50 ">
								<span class="fLW35 bold">Currently pregnant?</span>
								<c:forEach items="${codedYesNoUnkownValues}" var="codedValue" varStatus="pregnantStatus">
										<span  class="mL10"><form:radiobutton id="PREGNANT${pregnantStatus.count}"  path="stdNycprfDoc.pregnant"  value="${codedValue.ANS_CODE}" title="Currently Pregnant"/> ${codedValue.ANS_DESC}</span>
								</c:forEach>
							</span>
							
						</div>
						
						
						<div class="personInfo pT10 clear">
							<span class="fieldHolder50">
								<span class="fLW35 bold">Date of Admission or Visit </span>
								<fmt:formatDate value="${nycPRFDocVO.stdNycprfDoc.admissionDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedadmissionDate" />
								<span  class="fLW60"><form:input id="ADMISSION_DATE"  path="stdNycprfDoc.admissionDate" value="${theFormattedadmissionDate}" class="dPicker standardInputText "  maxlength="10"/>&nbsp;&nbsp;</span>
							</span>
							<span class="fieldHolder50 ">
								<span class="fLW35 bold">DIN for inmate </span>
								<span class="fLW60"> 
									<form:input id="DIN"  path="stdNycprfDoc.din" class="standardInputText "  maxlength="7"/>
								</span>
							</span>
						</div>
						<div class="personInfo  pT10 clear">
							<div class="fieldHolder50">
								<span class="fLW35   bold">Other identifier </span>
								<span  class="fLW60"><form:input id="PERSON_OTHER_IDENTIFIER"  path="stdNycprfDoc.personOtherIdentifier" class="standardInputText " maxlength="50" title="Other identifier" />&nbsp;&nbsp;</span>
								<div class="v_error  PERSON_OTHER_IDENTIFIER "></div>
							</div>
						</div>
						<br>
					</li>	
				</ol>
			</div>	
			<!--  Race details -->
			<div class="raceRiskReprt borderField">
				<div class="fieldHolder25" id="raceEthnicity">
					<div class="container raceHW">
						<ol start="2" type='a'>
							<li>
								<div>
									<span class="bold">Race</span>
								</div>
								<div>
									<form:checkbox id="RACE_WHITE1"  path="stdNycprfDoc.raceWhite" value="1" /> White	
								</div>
								<div>
									<form:checkbox id="RACE_BLACK1"  path="stdNycprfDoc.raceBlack" value="1" /> African American
								</div>
								<div>
									<form:checkbox id="RACE_ASIAN1"  path="stdNycprfDoc.raceAsian" value="1" /> Asian
								</div>
								<div>
									<form:checkbox id="RACE_INDIAN1"  path="stdNycprfDoc.raceIndian" value="1" /> American Indian/Alaskan Native
								</div>
								<div>
									<form:checkbox id="RACE_PACIFIC1"  path="stdNycprfDoc.racePacific" value="1" /> Pacific
								</div> 
								<div>
									<form:checkbox id="RACE_OTHER1"  path="stdNycprfDoc.raceOther" value="1" /> Other 
									<form:input id="RACE_OTHER_TEXT"  path="stdNycprfDoc.raceOtherText" class="standardInputSmallText2"  maxlength="20"/>
								</div>
								<div>
									<form:checkbox id="RACE_UNKNOWN1"  path="stdNycprfDoc.raceUnknown" value="1" /> Unknown
								</div>	
							</li>
						</ol>		
					</div>	
					<br>
					<div class="borderTD ethnicityHW">	
						<ol start="3" type='a'>
							<li>
								<div>
									<span class="bold">Ethnicity</span> (Check one) </br> Hispanic or Latino<br>
									<form:radiobutton id="ETHNICITY1"  path="stdNycprfDoc.ethnicity" value="01" /> Yes
									<form:radiobutton id="ETHNICITY2"  path="stdNycprfDoc.ethnicity" value="02" /> No
									<form:radiobutton id="ETHNICITY3"  path="stdNycprfDoc.ethnicity" value="09" /> Unknown
								</div>
							</li>
						</ol>
					</div>	
					<br>
					<div class="borderTD birthCountyHW">	
						<ol start="4" type='a'>
							<li>
								<div>
									<span class="bold">Country of birth</span><br>
									<form:select id="BIRTH_COUNTRY"  path="stdNycprfDoc.birthCountry" cssClass="standardInputSmallText" title="Birth Country">
										<form:option value=""></form:option>
										<form:options items="${sessionScope.countryMap}"/>
										<form:option value="OTH">Other</form:option>
										<form:option value="UNK">Unknown</form:option>
									</form:select>
								</div>
							</li>
						</ol>
					</div>	
					<br>
					<div class="borderTD birthSexOrnHW">	
						<ol start="4" type='a'>
							<li>
								<div>
									<span class="bold">Sexual Orientation:</span><br>
									
									
									<div id="sexualOrientationGroup" class="">
										
										<c:forEach items="${codedSexualOrientationValues}" var="sexualOrientation" varStatus="sexOrnStatus">
												<div><span  class=""><form:radiobutton id="SEX_ORN${sexOrnStatus.count}"  path="stdNycprfDoc.sexOrn" value="${sexualOrientation.SEX_ORIENT_CD}" title="Sexual Orientation"/> ${sexualOrientation.SEX_ORIENT_DESC}</span></div>
										</c:forEach>
										<div class="mT5">
											<span class="mL10"></span><form:input id="SEX_ORN_ADD_ID"  path="stdNycprfDoc.sexOrnAddId" class="standardInputMedText"  maxlength="50" title="Additional Sexual Orientation identity" />
										</div>
									</div>
																		
								</div>
							</li>
						</ol>
					</div>
				</div>
				<div class="fieldHolder75">
				<!--  Risk details -->
					<div class="riskHW borderLD borderRD " style="height: 440px">
						<ol start="5" type='a'>
							<li>
								<div class="pT8">
									<span class="riskLabelField bold">Risk Details</span>
									<span class="riskValueField">Yes</span>
									<span class="riskValueField">No</span>
									<span class="riskValueField">Unknown</span><br>
								</div>	
								<div class="pT8">
									<span class="riskLabelField bold">Sex With Male</span>
									<span class="riskValueField"><form:radiobutton id="SWM1"  path="stdNycprfDoc.swm" value="Y" /></span>
									<span class="riskValueField"><form:radiobutton id="SWM2"  path="stdNycprfDoc.swm" value="N" /></span>
									<span class="riskValueField"><form:radiobutton id="SWM3"  path="stdNycprfDoc.swm" value="U" /></span><br>		
								</div>
								<div class="pT8">
									<span class="riskLabelField bold">Sex With Female</span>
									<span class="riskValueField"><form:radiobutton id="SWF1"  path="stdNycprfDoc.swf" value="Y" /></span>
									<span class="riskValueField"><form:radiobutton id="SWF2"  path="stdNycprfDoc.swf" value="N" /></span>
									<span class="riskValueField"><form:radiobutton id="SWF3"  path="stdNycprfDoc.swf" value="U" /></span>
								</div>
								<div class="pT8 pB5">
									<span class="riskLabelField pT8 pB5 bold">Injection Drug Use </span>
						 			<span class="riskValueField pT8 pB5"><form:radiobutton id="IDU1"  path="stdNycprfDoc.idu" value="Y" /></span>
									<span class="riskValueField pT8 pB5"><form:radiobutton id="IDU2"  path="stdNycprfDoc.idu" value="N" /></span>
									<span class="riskValueField pT8 pB5"><form:radiobutton id="IDU3"  path="stdNycprfDoc.idu" value="U" /></span> 
								</div>	
								<div class="pT10"> <u><span class="bold">Heterosexual sex with:</span></u> </div>
								<ul>
									<li class="pT8">
										
											<span class="riskLabelField1 bold">Injection Drug User </span>
								 			<span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_IDU1"  path="stdNycprfDoc.sexPartnerIdu" value="Y" /></span>
											<span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_IDU2"  path="stdNycprfDoc.sexPartnerIdu" value="N" /></span>
											<span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_IDU3"  path="stdNycprfDoc.sexPartnerIdu" value="U" /></span> 
										
									</li>
									<li class="pT8">		
										
											<span class="riskLabelField1 bold">Bisexual man </span>
								 			<span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_MSM1"  path="stdNycprfDoc.sexPartnerMsm" value="Y" /></span>
											<span class="riskValueField1"> <form:radiobutton id="SEX_PARTNER_MSM2"  path="stdNycprfDoc.sexPartnerMsm" value="N" /></span>	
											<span class="riskValueField1"> <form:radiobutton id="SEX_PARTNER_MSM3"  path="stdNycprfDoc.sexPartnerMsm" value="U" /></span>	 
										
									</li>
									<li class="pT8">	
										
											<span class="riskLabelField1 bold">HIV+ person </span>
								 			<span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_HIV1"  path="stdNycprfDoc.sexPartnerHiv" value="Y" /></span>
											<span class="riskValueField1"> <form:radiobutton id="SEX_PARTNER_HIV2"  path="stdNycprfDoc.sexPartnerHiv" value="N" /></span>	
											<span class="riskValueField1"> <form:radiobutton id="SEX_PARTNER_HIV3"  path="stdNycprfDoc.sexPartnerHiv" value="U" /></span>		 
										
									</li>
									<li class="pT8">	
										
											<span class="riskLabelField1 bold">Person with HIV + Hemophiliac and documented HIV </span>
								 			<span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_HEMOPHILIAC1"  path="stdNycprfDoc.sexPartnerHemophiliac" value="Y" /></span>
											<span class="riskValueField1"> <form:radiobutton id="SEX_PARTNER_HEMOPHILIAC2"  path="stdNycprfDoc.sexPartnerHemophiliac" value="N" /></span>	
											<span class="riskValueField1"> <form:radiobutton id="SEX_PARTNER_HEMOPHILIAC3"  path="stdNycprfDoc.sexPartnerHemophiliac" value="U" /></span>		 
										
									</li>
								</ul>
								<div class="pT8 pB5">
									<span class="riskLabelField pB5 bold">Perinatal Exposure </span>
						 			<span class="riskValueField pB5"><form:radiobutton id="PERINATAL1"  path="stdNycprfDoc.perinatal" value="Y" /></span>
									<span class="riskValueField pB5"> <form:radiobutton id="PERINATAL2"  path="stdNycprfDoc.perinatal" value="N" /></span>	
									<span class="riskValueField pB5"> <form:radiobutton id="PERINATAL3"  path="stdNycprfDoc.perinatal" value="U" /></span>		 
								</div>	
								<div class="pT8">
									<span class="riskLabelField bold">Received Blood Products </span>
						 			<span class="riskValueField"><form:radiobutton id="RECV_BLOOD_PROD1"  path="stdNycprfDoc.recvBloodProd" value="Y" /></span>
									<span class="riskValueField"><form:radiobutton id="RECV_BLOOD_PROD2"  path="stdNycprfDoc.recvBloodProd" value="N" /></span>	
									<span class="riskValueField"><form:radiobutton id="RECV_BLOOD_PROD3"  path="stdNycprfDoc.recvBloodProd" value="U" /></span>	 
								</div>
								<br>
								<ul style="clear:both">
									<li class="pT8">
										<span class="riskLabelField2 bold">Date </span>
										<span class="riskValueField2"><form:input id="RECV_BLOOD_PROD_DATE_TEXT"  path="stdNycprfDoc.recvBloodProdDateText" class="dPicker standardInputSmallText"  maxlength="10"/></span>	<br>
									</li>
									<li class="pT8">
										<span class="riskLabelField2 bold">Country </span>
										<span class="riskValueField2"><form:input id="RECV_BLOOD_PROD_COUNTRY"  path="stdNycprfDoc.recvBloodProdCountry" class="standardInputSmallText"  maxlength="40"/></span>
									</li>	
								</ul>	
								<div class="pT8" style="clear:both">
									<span class="riskLabelField bold">Worked in healthcare</span>
						 			<span class="riskValueField"><form:radiobutton id="HEALTHCARE_WORKER1"  path="stdNycprfDoc.healthcareWorker" value="Y" /></span>
									<span class="riskValueField"><form:radiobutton id="HEALTHCARE_WORKER2"  path="stdNycprfDoc.healthcareWorker" value="N" /></span>	
									<span class="riskValueField"><form:radiobutton id="HEALTHCARE_WORKER3"  path="stdNycprfDoc.healthcareWorker" value="U" /></span>	 
								</div>
								<div class="pT8" style="clear:both">
									<span class="riskLabelField bold">Risk other</span>
						 			<span class="riskValueField"><form:radiobutton id="RISK_OTHER1"  path="stdNycprfDoc.riskOther" value="Y" /></span>
									<span class="riskValueField"><form:radiobutton id="RISK_OTHER2"  path="stdNycprfDoc.riskOther" value="N" /></span>	
									<span class="riskValueField"><form:radiobutton id="RISK_OTHER3"  path="stdNycprfDoc.riskOther" value="U" /></span>	 
								</div>
								<div style="clear:both" class="pT8">
									<span class="riskLabelField3 bold">Other risk </span>
									<span class="riskValueField3"><form:input id="RISK_OTHER_TEXT"  path="stdNycprfDoc.riskOtherText" class="standardInputSmallText"  maxlength="30"/></span>
								</div>	
								<div class="pT8" style="clear:both">
									<span class="riskLabelField bold">Risk unknown</span>
						 			<span class="riskValueField"><form:radiobutton id="RISK_UNKNOWN1"  path="stdNycprfDoc.riskUnknown" value="Y" /></span>
									<span class="riskValueField"><form:radiobutton id="RISK_UNKNOWN2"  path="stdNycprfDoc.riskUnknown" value="N" /></span>	
									<span class="riskValueField"><form:radiobutton id="RISK_UNKNOWN3"  path="stdNycprfDoc.riskUnknown" value="U" /></span>	 
								</div>
								
								
							</li>
						</ol>	
					</div>
					<div class="reportHW ">		
						<!--  Report details -->
						<ol start="6" type='a'>
							<li>
								<div class="pT8">
									<span class="riskLabelField bold">Type of report</span>
								</div><br>
							</li>
						</ol>
						<div class="pL30">
							<form:checkbox id="INITIAL_HIV_DIAG1"  path="stdNycprfDoc.initialHivDiag" value="1"  maxlength="1"/> Initial/new HIV diagnosis(Complete Section II)	
						</div>
						<ul class="pL50">
							<li>
								<span class="riskLabelField2 bold">Date of 1st HIV diagnosis</span>
								<span class="riskValueField3"><form:input id="INITIAL_HIV_DIAG_DATE_TEXT"   path="stdNycprfDoc.initialHivDiagDateText" class="dPicker standardInputMedText"  maxlength="10"/></span>
							</li>
						</ul>	
						
						<div class="pL30 floatLeft pT10 pB10 clearBoth">
								<span class="bold">Acute infection </span>
								<span class="">
									<c:forEach items="${codedYesNoUnkownValues}" var="codedValue" varStatus="acuteInfectionStatus">
											<form:radiobutton id="ACUTE_INFECTION${acuteInfectionStatus.count}"  path="stdNycprfDoc.acuteInfection" value="${codedValue.ANS_CODE}" /> ${codedValue.ANS_DESC}
									</c:forEach>
								</span>
							
							</div>
						<div class="clearBoth pL30 floatLeft pT10 pB10">
								<span class="riskLabelField2 bold">Has this patient been informed of his/her HIV positive status?</span>
									<span class="riskValueField4">
										<form:radiobutton id="INFORMED_HIV_POS1"  path="stdNycprfDoc.informedHivPos" value="1" /> Yes 							
										<form:radiobutton id="INFORMED_HIV_POS2"  path="stdNycprfDoc.informedHivPos" value="0" /> No	
									</span>
							
							</div>
						<ul class="pL50">
							<li class="floatLeft">
									<span class="riskLabelField2 ">Date</span>
									<span class="riskValueField4"><form:input id="INFORMED_HIV_DATE_TEXT"   path="stdNycprfDoc.informedHivDateText" class="dPicker standardInputMedText"  maxlength="10"/></span>
							</li>
						</ul>	
					
			
						<div class="clearBoth pL30 pT10">
							<form:checkbox id="PREVIOUS_HIV_DIAG1"  path="stdNycprfDoc.previousHivDiag" value="1"/> Previously diagnosed HIV infection (non-AIDS)	
						</div>
						<ul class="pL50">
							<li>
								<span class="riskLabelField2 bold">Date of diagnosis</span>
								<span class="riskValueField3"><form:input id="PREV_HIV_DIAG_DATE_TEXT"   path="stdNycprfDoc.prevHivDiagDateText" class="dPicker standardInputMedText"  maxlength="10"/></span>
							</li>
						</ul>	
						<div class="pL30">
							<form:checkbox id="INITIAL_AIDS_DIAG1"  path="stdNycprfDoc.initialAidsDiag" value="1" /> Initial/new AIDS diagnosis	
						</div>
						<ul class="pL50">
							<li>
								<span class="riskLabelField2 bold">Date of diagnosis</span>
								<span class="riskValueField3"><form:input id="INIT_AIDS_DIAG_DATE_TEXT"   path="stdNycprfDoc.initAidsDiagDateText" class="dPicker standardInputMedText"  maxlength="10"/></span>
							</li>
						</ul>	
						<div class="pL30">
							<form:checkbox id="PREVIOUS_AIDS_DIAG1"  path="stdNycprfDoc.previousAidsDiag" value="1" /> Previously diagnosis	AIDS 
						</div>
						<ul class="pL50">
							<li>
								<span class="riskLabelField2 bold">Date of diagnosis</span>
								<span class="riskValueField3"><form:input id="PREV_AIDS_DIAG_DATE_TEXT"   path="stdNycprfDoc.prevAidsDiagDateText" class="dPicker standardInputMedText"  maxlength="10"/></span>
							</li><br>
						</ul>
						
						
						
						<div class="pL30 pT10 pB10">
								<span class="bold flw50 bold">Was patient referred to an HIV medical provider? </span>
								<span class="">
									<c:forEach items="${codedYesNoUnkownValues}" var="codedValue" varStatus="referredHivProviderStatus">
											<form:radiobutton id="REFERRED_HIV_PROVIDER${referredHivProviderStatus.count}"  path="stdNycprfDoc.referredHivProvider" class="otherFieldDependentRadio "   dependentField="REFERRED_PROVIDER_NAME,REFERRED_PROVIDER_TELEPHONE" value="${codedValue.ANS_CODE}" /> ${codedValue.ANS_DESC}
									</c:forEach>
								</span>
							
							</div>
							
							<div class="pL30  pT10 pB10">
								<span class="bold ">Referred to:  </span>
								<span class="mL50">
									<form:input id="REFERRED_PROVIDER_NAME"  path="stdNycprfDoc.referredProviderName" class="standardInputText" requiredField="Y" maxlength="80" title="Provider Name" />  							
								</span>
								<div class="v_error REFERRED_PROVIDER_NAME "></div>
							</div>
							
							<div class="pL30  pT10 pB10">
								<span class="bold">Telephone number:   </span>
								<span class="">
									<form:input id="REFFERED_PROVIDER_TELEPHONE"  path="stdNycprfDoc.refferedProviderTelephone" class="standardInputMedText" maxlength="20" />  							
								</span>
							</div>
							
										
					</div>
				</div>	
			</div>
			
		<!--  HIV Testing History Information -->
	 		<div class="mT10">
	 			<div id="" class="divHeadingCls clear" style="background:#DDDDDD; ">
					<span class=" mL2">HIV Testing History Information</span>					
				</div>
				<div class="accordion" id="TTHaccordion" style="margin-bottom: 20px;">		
<%-- 					<c:forEach items="${stdPRFDocVO.tTHDocumentVOList}" var="tthDocumentVo" varStatus="tthCounter">	 --%>
<%-- 						<%@include file="TTHDocument.jsp" %> --%>
<%-- 					</c:forEach>				 --%>


						 	<h3 style="padding:1em"><span class="accordionHeader" style="left: 2em;top: 50%;margin-top: -8px;position: absolute;">TTH Document</span></h3>
	
						 	<div id="personHIVDetails" class="container personHIVDetailsForm" >
						 		
								<div id="personHIVDetailsLeft" class="clear personHIVDetailsLeft">
									<ul>
										<li>
											<span class="labelHIV bold">Date of patient interview </span>
											<span class="valueHIV"> <form:input id="PT_INTERVIEW_DATE"  path="stdNycprfDoc.ptInterviewDate"  class="dPicker standardInputMedText"  maxlength="15"/> </span>
										</li>
										<li >
											<div id="everHadPrePosGroup"> 
												<span class="bold">Ever had a previous positive HIV test? </span><br/>
												<c:forEach items="${codedYesNoRefUnkownValues}" var="codedValue" varStatus="everHadPrevPosTestStatus">
														<span> <form:radiobutton id="EVER_HAD_PREV_POS_TEST${everHadPrevPosTestStatus.count}"  path="stdNycprfDoc.everHadPrevPosTest" value="${codedValue.ANS_CODE}" /> ${codedValue.ANS_DESC}  </span><br>
												</c:forEach>
											</div>
											<div class="v_error everHadPrePosGroup floatLeft"></div>
										</li>
										<li>
											<span class="labelHIV bold">Date of first POSITIVE HIV test (enter date of current test if it is first) </span>
											<span class="valueHIV"> <form:input id="FIRST_POS_HIV_DATE"  path="stdNycprfDoc.firstPosHivDate" class="dPicker standardInputMedText"  maxlength="15"/> </span>
										</li>
										<li style="padding-top:10px">
											<span class="labelHIV bold">Was the FIRST positive test anonymous? </span>
											<div class="valueHIV"> 
												<span> <form:radiobutton id="FIRST_POS_ANON1"  path="stdNycprfDoc.firstPosAnon" value="Y" /> Yes </span>
												<span> <form:radiobutton id="FIRST_POS_ANON2"  path="stdNycprfDoc.firstPosAnon" value="N" /> No </span>	
												<span> <form:radiobutton id="FIRST_POS_ANON3"  path="stdNycprfDoc.firstPosAnon" value="U" /> Unknown </span> 
											</div>
										</li>
										<li>
											<span>Location of First positive test(Select only one)<br>Type of facility (if known):</span><br>
												
											<div class="pT10 clear">
												<span class="bold"> Name of facility</span>
												<span class=""><form:input id="FIRST_POS_FAC_NAME"  path="stdNycprfDoc.firstPosFacName" class="mL20 standardInputSmallText"  maxlength="100"/></span>
												<span class="mL20 bold"> State</span> 
												<span class=""><form:input id="FIRST_POS_FAC_STATE"  path="stdNycprfDoc.firstPosFacState" class="mL20 standardInputSmallText"  maxlength="10"/></span>
											</div>
										</li>
										<li class="pT10">
											<span >
												<span class="width70 left pB10 bold">Reason for FIRST positive test (Respond to each):</span> 
												<span class="width30 left pB10">
													<span class="pLR">Yes</span>
													<span>No</span>
												</span><br>
												<span class="width70 left pB10 bold">Patient thinks he/she was exposed in 6 months prior to test</span>
												<span class="width30 left pB10"> 
													<span class="pLR"><form:radiobutton id="RSON_FIRST_POS_EXP1"  path="stdNycprfDoc.rsonFirstPosExp" value="Y" /></span>
													<span> <form:radiobutton id="RSON_FIRST_POS_EXP2"  path="stdNycprfDoc.rsonFirstPosExp" value="N" /></span>	
												</span>	<br>
												<span class="width70 left pB10 bold">Patient test routinely and it was time to test again</span>
												<span class="width30 left pB10"> 
													<span class="pLR"><form:radiobutton id="RSON_FIRST_POS_ROUTN1"  path="stdNycprfDoc.rsonFirstPosRoutn" value="Y" /></span>
													<span> <form:radiobutton id="RSON_FIRST_POS_ROUTN2"  path="stdNycprfDoc.rsonFirstPosRoutn" value="N" /></span>	
												</span>	<br>
												<span class="width70 left pB10 bold">Patient is checking to make sure he/she is HIV negative</span>
												<span class="width30 left pB10"> 
													<span class="pLR"><form:radiobutton id="RSON_FIRST_POS_CHKNEG1"  path="stdNycprfDoc.rsonFirstPosChkneg" value="Y" /></span>
													<span> <form:radiobutton id="RSON_FIRST_POS_CHKNEG2"  path="stdNycprfDoc.rsonFirstPosChkneg" value="N" /></span>	
												</span>	<br>
												<span class="width70 left pB10 bold">Required (court ordered, insurance, military, etc.)</span>
												<span class="width30 left pB10"> 
													<span class="pLR"><form:radiobutton id="RSON_FIRST_POS_REQD1"  path="stdNycprfDoc.rsonFirstPosReqd" value="Y" /></span>
													<span> <form:radiobutton id="RSON_FIRST_POS_REQD2"  path="stdNycprfDoc.rsonFirstPosReqd" value="N" /></span>	
												</span>	<br>
												<span class="width70 left pB10 bold">Notified that partner was HIV positive</span>
												<span class="width30 left pB10"> 
													<span class="pLR"><form:radiobutton id="RSON_FIRST_POS_PRTNR1"  path="stdNycprfDoc.rsonFirstPosPrtnr" value="Y" /></span>
													<span> <form:radiobutton id="RSON_FIRST_POS_PRTNR2"  path="stdNycprfDoc.rsonFirstPosPrtnr" value="N" /></span>	
												</span>	<br>
												<span class="width70 left pB10 bold">Some other reason <form:input id="RSON_FIRST_POS_OTHER_TEXT"  path="stdNycprfDoc.rsonFirstPosOtherText" class="dPicker ml10 standardInputMedText"  maxlength="75"/><br><span class="labelunderneath">(specify)</span></span>
												<span class="width30 left pB10"> 
													<span class="pLR"><form:radiobutton id="RSON_FIRST_POS_OTHER1"  path="stdNycprfDoc.rsonFirstPosOther" value="Y" /></span>
													<span> <form:radiobutton id="RSON_FIRST_POS_OTHER2"  path="stdNycprfDoc.rsonFirstPosOther" value="N" /></span>	
												</span>	<br>
											</span>
										</li>	
										<li class="clearBoth pT10">
											<span class="bold">Ever taken any antiretroviral (ARVs) medications?  (if no, skip to #9) </span>
											<div id="everTakenAntiretroviralGroup"> 
												<c:forEach items="${codedYesNoRefUnkownValues}" var="codedValue" varStatus="ptArvEverTakenStatus">
													<span> <form:radiobutton id="PT_ARV_EVER_TAKEN${ptArvEverTakenStatus.count}"  path="stdNycprfDoc.ptArvEverTaken" value="${codedValue.ANS_CODE}" title="Ever taken any antiretroviral"/> ${codedValue.ANS_DESC}  </span><br>
												</c:forEach>
											</div>
											<div class="v_error  everTakenAntiretroviralGroup "></div>
											
										</li>
										<li class="clearBoth pT10">
											<div class="pB20">
													<span class="bold">ARVs for prevention (PrEP)?</span>
													<span>
														 <form:radiobutton id="ARV_PREVEN_PREP1"  path="stdNycprfDoc.arvPrevenPrep" value="Y" /> Yes
														 <form:radiobutton id="ARV_PREVEN_PREP2"  path="stdNycprfDoc.arvPrevenPrep" value="N" /> No 
													</span>	
													<div class="clear pT10 pB10">	
														<span class="pT10 pB10 bold"> Date first began:</span> 
														<span class="ml10 pB5"><form:input id="ARV_PREVEN_PREP_START_DT"  path="stdNycprfDoc.arvPrevenPrepStartDt" title="ARVs for prevention first began Date"  class="dPicker mask standardInputSmallText" datevalidations="partialDateForAll"  maxlength="15"/></span>
														<span class="mL20 pB10 bold"> Date last use:</span>
														<span class="ml10 pB5"><form:input id="ARV_PREVEN_PREP_END_DT"  path="stdNycprfDoc.arvPrevenPrepEndDt" title="ARVs for prevention last used Date" class=" dPicker mask standardInputSmallText" datevalidations="partialDateForAll"  maxlength="15"/></span>
														<div class="v_error ARV_PREVEN_PREP_START_DT floatLeft"></div>
														<div class="v_error ARV_PREVEN_PREP_END_DT floatLeft"></div>
													</div>
													<div class="clearBoth">
														<span class="bold">ARV medications:</span>
														<span> 
													
														
															<form:select id="ARV_PREVENPREP_MED_NAMES"  path="stdNycprfDoc.arvPrevenprepMedNames" class="standardInputText"  multiple="true" title="Antiretrovirals name" >
																	<c:forEach items="${arvList}" var="arvCategory">
																		<form:option value="${arvCategory.LOOKUP_CD}">${arvCategory.ARV_DESC}</form:option>
																	</c:forEach>
																</form:select>
															
																	<script type="text/javascript"> 
													 					var ARV_PREVENPREP_MED_NAMES = '${nycPRFDocVO.stdNycprfDoc.arvPrevenprepMedNames}';
													 					$('#ARV_PREVENPREP_MED_NAMES').val(ARV_PREVENPREP_MED_NAMES.split('|'));
																	</script>
																
														</span>
													</div>
												</div>
										</li>
										<li class="clearBoth pT10">
											<div class="pB20">
													<span class="bold">Post-exposure ARVs (PEP)?</span>
													<span>
														 <form:radiobutton id="POST_EXP_ARV_PEP1"  path="stdNycprfDoc.postExpArvPep" value="Y" /> Yes
														 <form:radiobutton id="POST_EXP_ARV_PEP2"  path="stdNycprfDoc.postExpArvPep" value="N" /> No 
													</span>	
													<div class="clear pT10 pB10">	
														<span class="pT10 pB10 bold"> Date first began:</span> 
														<span class="ml10 pB5"><form:input id="POST_EXP_ARV_PEP_START_DT"  path="stdNycprfDoc.postExpArvPepStartDt" title="ARVs for prevention first began Date"  class="dPicker mask standardInputSmallText" datevalidations="partialDateForAll"  maxlength="15"/></span>
														<span class="mL20 pB10 bold"> Date last use:</span>
														<span class="ml10 pB5"><form:input id="POST_EXP_ARV_PEP_END_DT"  path="stdNycprfDoc.postExpArvPepEndDt" title="ARVs for prevention last used Date" class=" dPicker mask standardInputSmallText" datevalidations="partialDateForAll"  maxlength="15"/></span>
														<div class="v_error POST_EXP_ARV_PEP_START_DT floatLeft"></div>
														<div class="v_error POST_EXP_ARV_PEP_END_DT floatLeft"></div>
													</div>
													<div class="clearBoth">
														<span class="bold">ARV medications:</span>
														<span> 
															<form:select id="POST_EXP_ARV_PEP_MED_NAMES"  path="stdNycprfDoc.postExpArvPepMedNames" class="standardInputText"  multiple="true" title="Antiretrovirals name" >
																	<c:forEach items="${arvList}" var="arvCategory">
																		<form:option value="${arvCategory.LOOKUP_CD}">${arvCategory.ARV_DESC}</form:option>
																	</c:forEach>
																</form:select>
																
																	<script type="text/javascript"> 
													 					var POST_EXP_ARV_PEP_MED_NAMES = '${nycPRFDocVO.stdNycprfDoc.postExpArvPepMedNames}';
													 					$('#POST_EXP_ARV_PEP_MED_NAMES').val(POST_EXP_ARV_PEP_MED_NAMES.split('|'));
																	</script>
														</span>
													</div>
												</div>
										</li>
										
										
										
										
										
										
										
										
										
										
									</ul>	
								</div>			
						
								<div id="personHIVDetailsRight" class="personHIVDetailsRight">	
									<ol type="a" start="10">	
										<li>
											<div class="pB20">
													<span class="bold">ARVs for treatment?</span>
													<span>
														 <form:radiobutton id="ARV_TREATMENT1"  path="stdNycprfDoc.arvTreatment" value="Y" /> Yes
														 <form:radiobutton id="ARV_TREATMENT2"  path="stdNycprfDoc.arvTreatment" value="N" /> No 
													</span>	
													<div class="clear pT10 pB10">	
														<span class="pT10 pB10 bold"> Date first began:</span> 
														<span class="ml10 pB5"><form:input id="PT_ARV_START_DATE_TEXT"  path="stdNycprfDoc.ptArvStartDateText" title="Antiretrovirals start Date"  class="dPicker mask standardInputSmallText" datevalidations="partialDateForAll"  maxlength="15"/></span>
														<span class="mL20 pB10 bold"> Date last use:</span>
														<span class="ml10 pB5"><form:input id="PT_ARV_END_DATE_TEXT"  path="stdNycprfDoc.ptArvEndDateText" title="Antiretrovirals end Date" class=" dPicker mask standardInputSmallText" datevalidations="partialDateForAll"  maxlength="15"/></span>
														<div class="v_error PT_ARV_START_DATE floatLeft"></div>
														<div class="v_error PT_ARV_END_DATE floatLeft"></div>
													</div>
													<div class="clearBoth">
														<span class="bold">ARV medications:</span>
														<span> 
														
															<form:select id="PT_ARV_NAME"  path="stdNycprfDoc.ptArvName" class="standardInputText"  multiple="true" title="Antiretrovirals name" >
																	<c:forEach items="${arvList}" var="arvCategory">
																		<form:option value="${arvCategory.LOOKUP_CD}">${arvCategory.ARV_DESC}</form:option>
																	</c:forEach>
															</form:select>
															
															<script type="text/javascript"> 
											 					var PT_ARV_NAME = '${nycPRFDocVO.stdNycprfDoc.ptArvName}';
											 					$('#PT_ARV_NAME').val(PT_ARV_NAME.split('|'));
															</script>
														</span>
													</div>
												</div>
										</li>					
										<li>
											<span class="bold">Has patient taken any antiretrovirals in the past six months? </span> <br>
											<div class=" pB20"> 
												<span class="width25 left"> <form:radiobutton id="ARV_6_MO1"  path="stdNycprfDoc.arv6Mo" value="Y" /> Yes </span>
												<span class="width25 left"> <form:radiobutton id="ARV_6_MO2"  path="stdNycprfDoc.arv6Mo" value="N" /> No </span>
												<span class="width25 left"> <form:radiobutton id="ARV_6_MO3"  path="stdNycprfDoc.arv6Mo" value="R" /> Refused</span>
												<span class="width25 left"> <form:radiobutton id="ARV_6_MO4"  path="stdNycprfDoc.arv6Mo" value="U" /> Unknown</span>
											</div>	
											<div class="clear pT10">
												<span class="pB10"> if Yes, which</span>
												<span class="mL20 pB5"><form:input id="ARV_6_MO_NAME"  path="stdNycprfDoc.arv6MoName" class="standardInputText"  maxlength="500"/></span><br>
											</div>
											<div class="clear pT10">	
												<span class="pT10 pB10"> if Yes, start date</span> 
												<span class="ml10 pB5"><form:input id="ARV_6_MO_START_DATE_TEXT"  path="stdNycprfDoc.arv6MoStartDateText" class="standardInputSmallText"  maxlength="15"/></span>
												<span class="mL20 pB10"> End date</span>
												<span class="ml10 pB5"><form:input id="ARV_6_MO_END_DATE_TEXT"  path="stdNycprfDoc.arv6MoEndDateText" class="standardInputSmallText"  maxlength="15"/></span>
											</div>
											<span class="pB5 pT10 inlineBlock bold"> Was the patient taking any antiretrovirals at the time of the current HIV test?</span> 
											<span class="pB10"> 
												<span> <form:radiobutton id="ARV_CURRENT_TEST1"  path="stdNycprfDoc.arvCurrentTest" value="Y" /> Yes </span>
												<span> <form:radiobutton id="ARV_CURRENT_TEST2"  path="stdNycprfDoc.arvCurrentTest" value="N" /> No </span>
											</span>	
										</li>
						
										<li>
											<span class="bold">Date of first HIV test ever (negative, indeterminate, or positive) </span>
											<span> <form:input id="FIRST_HIV_TEST_EVER_DATE_TEXT"  path="stdNycprfDoc.firstHivTestEverDateText" class="dPicker standardInputSmallText ml10"  maxlength="15"/> </span>
										</li>	
										<li style="height:45px">
											<span class="bold">How many times did patient test for HIV in the 2 years before the FIRST positive test? (including the test on date under b.)</span>
											<div> 
												<span> <form:input id="NUMBER_TEST_2_YRS"  path="stdNycprfDoc.numberTest2Yrs" class="dPicker standardInputTinyText"  maxlength="5"/>&nbsp;&nbsp;times</span>
											</div>
										</li> 
										<li>
											<span class="bold">Has patient ever tested negative for HIV before? </span>
											<div> 
												<span> <form:radiobutton id="EVER_TEST_NEG1"  path="stdNycprfDoc.everTestNeg" value="N" /> No (if checked,got to section III) </span><br>
												<span> <form:radiobutton id="EVER_TEST_NEG2"  path="stdNycprfDoc.everTestNeg" value="Y" /> Yes (If checked, answer j)</span><br>
												<span> <form:radiobutton id="EVER_TEST_NEG3"  path="stdNycprfDoc.everTestNeg" value="R" /> Refused </span><br>
												<span> <form:radiobutton id="EVER_TEST_NEG4"  path="stdNycprfDoc.everTestNeg" value="U" /> Unknown </span> 
											</div>
										</li>
										<li>
											<span class="bold">Date of last NEGATIVE HIV test <form:input id="LAST_NEG_DATE_TEXT"  path="stdNycprfDoc.lastNegDateText" value=""  class="dPicker standardInputSmallText ml10"  maxlength="15"/><br>
											 Type of facility (if known):</span> <br>
											 <span> 
												<span class="bold"> <form:radiobutton id="LAST_NEG_FAC_TYPE1"  path="stdNycprfDoc.lastNegFacType" value="01" /> HIV Counseling and Testing services </span><br>
												<span style="width:33%; float:left">
													<span class="bold"> <form:radiobutton id="LAST_NEG_FAC_TYPE2"  path="stdNycprfDoc.lastNegFacType" value="02" /> TB clinic </span><br>
													<span class="bold"> <form:radiobutton id="LAST_NEG_FAC_TYPE3"  path="stdNycprfDoc.lastNegFacType" value="03" /> STD clinic </span><br>
													<span class="bold"> <form:radiobutton id="LAST_NEG_FAC_TYPE4"  path="stdNycprfDoc.lastNegFacType" value="04" /> Drug treatment clinic </span><br>
													<span class="bold"> <form:radiobutton id="LAST_NEG_FAC_TYPE5"  path="stdNycprfDoc.lastNegFacType" value="05" /> Family planning clinic </span><br>
													<span class="bold"> <form:radiobutton id="LAST_NEG_FAC_TYPE6"  path="stdNycprfDoc.lastNegFacType" value="06" /> Prenatal/OB clinic </span>
												</span>
												<span style="width:33%; float:left">
													<span class="bold"> <form:radiobutton id="LAST_NEG_FAC_TYPE7"  path="stdNycprfDoc.lastNegFacType" value="07" /> Emergency room </span><br>
													<span class="bold"> <form:radiobutton id="LAST_NEG_FAC_TYPE8"  path="stdNycprfDoc.lastNegFacType" value="08" /> Prison/Jail </span><br>
													<span class="bold"> <form:radiobutton id="LAST_NEG_FAC_TYPE9"  path="stdNycprfDoc.lastNegFacType" value="09" /> Private doctor </span><br>
													<span class="bold"> <form:radiobutton id="LAST_NEG_FAC_TYPE10"  path="stdNycprfDoc.lastNegFacType" value="10" /> Blood Bank </span><br>
													<span class="bold"> <form:radiobutton id="LAST_NEG_FAC_TYPE11"  path="stdNycprfDoc.lastNegFacType" value="11" /> Outreach/Mobile </span>
												</span>
												<span  style="width:33%; float:left">
													<span class="bold"> <form:radiobutton id="LAST_NEG_FAC_TYPE12"  path="stdNycprfDoc.lastNegFacType" value="12" /> Community health clinic </span><br>
													<span class="bold"> <form:radiobutton id="LAST_NEG_FAC_TYPE13"  path="stdNycprfDoc.lastNegFacType" value="13" /> Hospital </span><br>
													<span class="bold"> <form:radiobutton id="LAST_NEG_FAC_TYPE14"  path="stdNycprfDoc.lastNegFacType" value="14" /> Other </span><br>
												</span>
											</span>	
											<div class="pT10 clear">
												<span class="bold"> Name of facility</span>
												<span class=""><form:input id="LAST_NEG_FAC_NAME"  path="stdNycprfDoc.lastNegFacName" class="mL20 standardInputSmallText"  maxlength="100"/></span>
												<span class="mL20 bold"> State</span> 
												<span class=""><form:input id="LAST_NEG_FAC_STATE"  path="stdNycprfDoc.lastNegFacState" class="mL20 standardInputSmallText"  maxlength="10"/></span>	
											</div>						
										</li>
										<li>
											<span class="bold">Number of negative HIV tests </span>
											<span class="mL10"> 
												<span> <form:input id="NUM_NEG_HIV_TEST"  path="stdNycprfDoc.numNegHivTest" class=" standardInputTinyText"  maxlength="5"/> </span> 
											</span>
										</li>
										<li>
											<span class="bold">Date assigned for field investigation </span>
											<span class="mL10"> 
												<fmt:formatDate value="${nycPRFDocVO.stdNycprfDoc.fieldProcDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedFieldProcDate" />
												<span> <form:input id="FIELD_PROC_DATE"  path="stdNycprfDoc.fieldProcDate" class="dPicker standardInputSmallText" value="${theFormattedFieldProcDate}"  maxlength="10"/> </span> 
											</span>
										</li>	
									
									</ul>		
								</div>	
							</div>
	 			</div>
	 		</div>
	 	<!--  End of HIV Testing History Information -->	

		<!-- Partner Details  -->
			<div>
				<div id="" class="divHeadingCls clear" style="background:#DDDDDD; ">
					<span class=" mL2">Partner Details</span>					
				</div>
				<div class="pT20">
					<form:checkbox id="PS_REQUESTED1"  path="stdNycprfDoc.psRequested" value="Y" maxlength="1"/> Provider Requests Health Department Partner Services	
				</div>			
				
				<c:forEach items="${nycPRFDocVO.partnerList}" var="ptnrDocumentVo" varStatus="ptnrCounter">
					<%@include file="NycPrfPartnerDetails.jsp" %>
				</c:forEach>				
			</div>	
		<!--  End of HIV Testing History Information -->	

		<!--  Provider details  -->	
			<div class="personHIVDetailsForm" class="container clear" style="clear:both">	
				<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
					<span class=" mL2">Provider Information</span>					
				</div>
				<div style="border: 1px dotted; height:420px">
					<div class="personHIVDetailsLeft">
						<div>
							<div class="pL30 pT10">
								<span class="riskLabelField4 bold">Date form Completed</span>
								<fmt:formatDate value="${nycPRFDocVO.stdNycprfDoc.completedDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedcompletedDate" />
								<span ><form:input id="COMPLETED_DATE"  path="stdNycprfDoc.completedDate" value="${theFormattedcompletedDate}" class="dPicker standardInputMedText"  maxlength="10"/></span> 
							</div>
									
							<div class="pL30 pT10" >
								<span class="personLabelDoc4 bold">Name</span>
								<span>
									<span><form:input id="PROV_LNAME"  path="stdNycprfDoc.provLname" class="mL20 standardInputSmallText"  maxlength="80"/></span> 
									<span><form:input id="PROV_FNAME"  path="stdNycprfDoc.provFname" class="standardInputSmallText"  maxlength="80"/></span> 
									<span><form:input id="PROV_MI"  path="stdNycprfDoc.provMi" class="standardInputSmallText"  maxlength="1"/></span> 
									<span class="labelunderneath4"> Last</span>
									<span class="labelunderneath4"> First</span>
									<span class="labelunderneath4"> Middle</span>
								</span> 
							</div>	
							
							<div class="pL30 pT10">
								<span class="riskLabelField4 bold">Department</span>
								<span ><form:input id="PROV_DEPARTMENT"  path="stdNycprfDoc.provDepartment" class="standardInputMedText" maxlength="40" /></span> 
							</div>				
							
							<div id="prfFacilityAddressSection" class="mT10 pB10">
									<div class="pL30 pT10 clear">
										<span class="riskLabelField4 bold">Street Address</span>
										<span ><form:input id="PROV_ADDRESS"  path="stdNycprfDoc.provAddress" class="standardInputText"  maxlength="62"/></span>
										<span class="labelunderneath3 dispAddressStdRaw"> Street (Std)</span> <br>
									</div>
									<div class="pT10">
										<span class="pL30 bold">City</span>
										<span class="pL30"><form:input id="PROV_CITY"  path="stdNycprfDoc.provCity" class="standardInputSmallText1"  maxlength="30"/></span>
										<span class="pL10 bold">State</span>
										<span class="pL10"><form:input id="PROV_STATE"  path="stdNycprfDoc.provState" cssClass=" standardInputSmallText1 "  maxlength="2"/></span>
										<span class="pL10 bold"> Zip </span>
										<span class="pL30"><form:input id="PROV_ZIP"  path="stdNycprfDoc.provZip" class="standardInputSmallText1" maxlength="5"/></span>										
										<span class="pL30 bold">County</span>
										<span class="pL10"> 
											<form:select id="PROV_COUNTY"  path="stdNycprfDoc.provCounty"  cssClass="standardInputSmallText1" > 
												<form:option value=""></form:option>
												<form:options items="${sessionScope.countyMap}"/>
											</form:select>
						 		         </span>  
									</div>	
									<div class="pT10">
										<span class="pL30 bold">Phone</span>
										<span class="pL10"><form:input id="PROV_PHONE"  path="stdNycprfDoc.provPhone" class="standardInputSmallText2"  maxlength="20"/></span>
										<span class="pL10 bold">Ext</span>
										<span class="pL10"><form:input id="PROV_EXT"  path="stdNycprfDoc.provExt" cssClass=" standardInputSmallText1 "  maxlength="8"/></span>
										<span class="pL10 bold"> Email </span>
										<span class="pL10"><form:input id="PRO_EMAIL"  path="stdNycprfDoc.proEmail" class="standardInputMedText" maxlength="80"/></span>										
									</div>	
							</div>
						</div>

						<div id="prfDocumentFacilityAddressDtls" class="clearBoth facilityDetails">
							<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD;">
								<span class="mL2 bold">Facility Details</span>
							</div>
							<div class="pL30 pT10" id="FACILITY_TYPE_GROUP">
								<div class="floatLeft fieldHolder15"><span class=" bold ">Facility Type</span></div>
								<div class="floatLeft">
								
									<span><form:radiobutton  path="stdNycprfDoc.facilityType" id="FACILITY_TYPE" class="facilityFields" title="Facility Type"  value="IH"/>Inpatient / Hospital</span><br/>
									<span><form:radiobutton  path="stdNycprfDoc.facilityType" id="FACILITY_TYPE" class="facilityFields" title="Facility Type"  value="OH"/>Outpatient / HIV Clinic</span><br/>
									<span><form:radiobutton  path="stdNycprfDoc.facilityType" id="FACILITY_TYPE" class="facilityFields" title="Facility Type"  value="OP"/>Outpatient / private physician's office</span><br/>
									<span><form:radiobutton  path="stdNycprfDoc.facilityType" id="FACILITY_TYPE" class="facilityFields" title="Facility Type"  value="CT"/>Counseling / Testing Agency</span><br/>
									<span><form:radiobutton  path="stdNycprfDoc.facilityType" id="FACILITY_TYPE" class="facilityFields" title="Facility Type"  value="SC"/>STD Clinic</span><br/>
									<span><form:radiobutton  path="stdNycprfDoc.facilityType" id="FACILITY_TYPE" class="facilityFields" title="Facility Type"  value="ER"/>Emergency Room</span><br/>
									<span><form:radiobutton  path="stdNycprfDoc.facilityType" id="FACILITY_TYPE" class="facilityFields" title="Facility Type"  value="PJ"/>Prison / Jail</span><br/>
									<span><form:radiobutton  path="stdNycprfDoc.facilityType" id="FACILITY_TYPE" class="facilityFields" title="Facility Type"  value="OT"/>Other <form:input id="FACILITY_TYPE_OTHER_TEXT"  path="stdNycprfDoc.facilityTypeOtherText" class="standardInputSmallText3 facilityFields"  maxlength="100" title="Other Facility type"/></span>
								
								</div>
							</div>
						
						</div>
					</div>
					
					<!-- Other Contact Details -->
					<div class="personHIVDetailsRight">
						<div class="personTitleFieldRight">Office contact person for information related to partner notification<br><center>(if other than the person completing the form):<center></div>	
						<div class="pL30 pT10">
							<span class="personLabelDoc4 bold">Name </span>
							<span> 
								<span ><form:input id="CONTACT_LNAME"  path="stdNycprfDoc.contactLname" class="mL20 standardInputSmallText"  maxlength="25"/></span> 
								<span ><form:input id="CONTACT_FNAME"  path="stdNycprfDoc.contactFname" class="standardInputSmallText"  maxlength="20"/></span>
								<span ><form:input id="CONTACT_MNAME"  path="stdNycprfDoc.contactMname" class="standardInputSmallText"  maxlength="1"/></span>
								<span class="labelunderneath4"> Last</span>
								<span class="labelunderneath4"> First</span>
								<span class="labelunderneath4"> Middle</span>
							</span>
						</div>
						<div class="pL30 pT10">
							<span class="riskLabelField4 bold">Title</span>
							<span ><form:input id="CONTACT_TITLE"  path="stdNycprfDoc.contactTitle" class="standardInputSmallText"  maxlength="30"/></span> 
						</div>
						<div class="pL30 pT10">
							<span class="riskLabelField4 bold">Telephone number</span>
							<span> 
								<form:input id="CONTACT_PHONE"  path="stdNycprfDoc.contactPhone" class="pNumber standardInputSmallText2"  maxlength="25"/>
							</span>
						</div>
						
						<div class="pL30 pT10">
							<span class="bold">NYS License #:</span>
							<span ><form:input id="PROVIDER_NYS_LIC_NO"  path="stdNycprfDoc.providerNysLicNo" class="mL20 standardInputSmallText required"  maxlength="20"/></span> 
						</div>
						<div class="pL30 pT10">
							<span class="bold">National Provider Identifier (NPI) #:</span>
							<span ><form:input id="PROVIDER_NPI_NO"  path="stdNycprfDoc.providerNpiNo" class="mL20 standardInputSmallText "  maxlength="10" title="NPI"/></span>	
						</div>
						<div class="pL30 pT10">
							<span class="bold">Provider Facility:</span>
							<span ><form:input id="PROV_FACILITY"  path="stdNycprfDoc.provFacility" class="mL20 standardInputText "  /></span>	
						</div>
						<div class="pL30 pT10">
							<span class="bold">Provider Title:</span>
							<span ><form:input id="PROV_TITLE"  path="stdNycprfDoc.provTitle" class="mL20 standardInputMedText "  /></span>	
						</div>
						<div class="pL30 pT10">
							<span class="bold">MD Print name:</span>
							<span ><form:input id="MDPRINTNAME"  path="stdNycprfDoc.mdprintname" class="mL20 standardInputMedText "  /></span>	
						</div>
						<div class="pL30 pT10">
							<span class="bold">Print Date:</span>
							<span ><form:input id="PRINTED_DATE"  path="stdNycprfDoc.printedDate" class="mL20 standardInputSmallText "  /></span>	
						</div>
						
						
						<br><br>	
						
					</div>
				</div>	
			</div>	
		</div>
	</div>

	<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
	
	<div id="button-row" class="button-row clear documentButtons"  align="center">
	<!--	<c:if  test="${userPermissions['74'] != null}" >
			<input type="button" id="eButton" style="display:none;" class="editBtnclick button rounded blue effect-3"  value="Edit" />
		</c:if>    -->
		<c:choose>
			<c:when  test="${param.hasDeleteFunctionality == true}" >	
					<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
					<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
			</c:when>
			<c:when test="${userPermissions['90'] != null   && nycPRFDocVO.stateno != null && nycPRFDocVO.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null}">
					<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
			</c:when>
		</c:choose>

						
		<c:if test="${nycPRFDocVO.stateno != null && nycPRFDocVO.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null && userPermissions['230'] != null}">						

			<input type="button" id="LaftFollowupButton" class="flagLaftFollowupDocument button rounded blue effect-3 displayNone"  value="Laft follow-up"  onclick="flagLaftFollowupDocument(${nycPRFDocVO.stdNycprfDoc.documentId}, '${nycPRFDocVO.stateno}')"/>						
		</c:if>				
						
		<input type="button" id="sButton" class="prfBtnclick button rounded blue effect-3" value="Submit" />
		<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />
		<span id="btnTxtMsg" style="display:none;"> Add / Cancel partner to submit </span>
	</div>
	
	<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />	
</form:form>															



