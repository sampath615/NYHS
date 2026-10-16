<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link href="${staticPath}/css/docs/prfForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<form:form modelAttribute="stdOldDocVO" id="stdOldDocVO" cssClass="documentDescription">		

<form:errors path="*" cssClass="errorblock" element="div" />

	<div id="stdOldTab" style="background-color: transparent">		
		<div style="padding-left:43%; " class="headingCls">
				STD OLD DOCUMENT
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
	            <div>
	                <span class=" ml10">	eHARS Import Date: </span>
	                <fmt:formatDate value="${stdOldDocVO.stdStdOldDoc.eharsImportDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
					<span class=" ml10"><form:input id="EHARS_IMPORT_DATE"  path="stdStdOldDoc.eharsImportDate" value="${theFormattedDate}"  class="standardInputSmallText ml10" readonly="true"/></span>
	            </div>                  
            </div>
            <div style="float:left; width: 30%; text-align: right">
            	<div>
	                <span class="mL10">Document ID: </span>
					<span class=" mL10"><form:input id="DOCUMENT_ID"  path="stdStdOldDoc.documentId" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
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
			<div class="container mT10 pL10" id="patientInformation">	 
				<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
					<span class=" mL2">Patient Information</span>
				</div>
				
						<div class="personInfo">
							<span class="personLabelDoc">Name </span>
							<span> 
								<span class="pL150"><form:input  path="stdStdOldDoc.lname" id="lName" class="standardInputMedText" maxlength="25" /></span> 
								<span  class="pL30"><form:input id="FNAME"  path="stdStdOldDoc.fname" class="standardInputMedText" maxlength="20" /></span>
								<span  class="pL30"><form:input id="MNAME"  path="stdStdOldDoc.mname" class="standardInputMedText" maxlength="25" /></span>
								<span  class="pL30"><form:input id="SUFFIX"  path="stdStdOldDoc.suffix" class="standardInputMedText" maxlength="25" /></span>
								<span class="labelunderneath2" style="display: inline-block"> Last</span>
								<span class="labelunderneath1"> First</span>
								<span class="labelunderneath1"> Middle</span>
								<span class="labelunderneath1"> Suffix</span>
							</span>
						</div>
						
						
						<div class="b1" style="margin: 15px 5px 10px 5px">
			 	            <div class="personInfo pTRBL10">
			 	                <span class="personLabelDoc"> Address </span>
			 		                <span> 	                	
				 	                	<span class="pL10"><form:input id="rawPersonAddress"  path="rawpersonaddress" class="standardInputText newAddress" style="width:300px" maxlength="62" />  </span>
				 	                    
				 	                    
				 	                    <span class="pL30">City</span>
				 	                    <span class="pL10"><form:input id="PERSON_CITY"  path="stdStdOldDoc.personCity" class="oAdrDlg standardInputSmallText1 newCity" maxlength="30"/></span>
				 	                    
				 	                    <span class="pL30">State </span>
				 	                    <span  class="pL10">
											<form:input id="PERSON_STATE"  path="stdStdOldDoc.personState" cssClass=" standardInputSmallText1 "  maxlength="2"/>
										</span>
				 	                    
				 	                    <span class="pL30">Zip </span>
				 	                    <span class="pL10"> 
				 	                        <form:input id="PERSON_ZIP"  path="stdStdOldDoc.personZip" class="standardInputSmallText1" maxlength="5"/> -<form:input id="PERSON_PLUS4"  path="stdStdOldDoc.personPlus4" class="standardInputSmallText1" maxlength="5"/> 
				 	                    </span>
				 	                    <span class="pL30">County </span>
				 		                   <span class="pL10"> 
												<form:select id="PERSON_COUNTY"  path="stdStdOldDoc.personCounty"  cssClass="standardInputSmallText1" > 
													<form:option value=""></form:option>
													<form:options items="${sessionScope.countyMap}"/>
												</form:select>
					 		               </span>       	                    
				 	                    <br>
				 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
				 	                </span>
			 	            </div>
			 	            <div class="personInfo pT10 pTRBL10">
			 	            	<span  class="" style="padding-left:59px"><form:input id="PERSON_ADDRESS"  path="stdStdOldDoc.personAddress" class="standardInputText dispAddressStdRaw" style="width:300px" maxlength="62" /></span>
			 	              	 	                 
			 	                 <span class="pL30"> Phone</span>
			 					 <span class="pL10"> <form:input id="PERSON_PHONE"  path="stdStdOldDoc.personPhone" class="pNumber standardInputSmallText" maxlength="20"/></span>
			 	                <br>               
			 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
			 	                    
			 	            </div>
			 	        </div>

						<div class="personInfo pB10">
							<span class="fieldHolder25 ">
								<span class="fLW35">Date of Birth </span>
								<span  class="fLW60"><form:input  path="stdStdOldDoc.dobText" id="dobValidation" class="dPicker standardInputSmallText"  maxlength="10"/></span>
							</span>
							<span class="fieldHolder25 ">
								<span class="fLW35">Date of Death </span>
								<span class="fLW60"> 
									<form:input  path="stdStdOldDoc.dodText" id="dodValidation" class="dPicker standardInputSmallText"  maxlength="10"/>
								</span>
							</span>
							<span class="fieldHolder25 ">
								<span class="fLW35">Vital Status</span>
				                <span class="fLW60">
				                	<form:radiobutton id="VITAL_STATUS1"  path="stdStdOldDoc.vitalStatus" value="1" /> Alive
				                	<form:radiobutton id="VITAL_STATUS2"  path="stdStdOldDoc.vitalStatus" value="2" /> Dead
				                </span>
				               </span>
				               
				              
							<span class="fieldHolder25 ">
								<span class="fLW35">SSN</span>
				                <span class="fLW60">
				                	<form:input id="SSN"  path="stdStdOldDoc.ssn" class="standardInputSmallText"  maxlength="10"/>
				                </span>
				               </span>	
				               
				               					
						</div><br>
						<div class="personInfo pT10 clear">
							<span class="fieldHolder25 ">
								<span class="fLW35">Gender </span>
								<span class=""> 
									<span> <form:radiobutton id="CURRENT_GENDER1"  path="stdStdOldDoc.currentGender" value="M" /> Male </span>
									<span> <form:radiobutton id="CURRENT_GENDER2"  path="stdStdOldDoc.currentGender" value="F" /> Female </span>
								</span>
							</span>
							<span class="fieldHolder25 ">
								<span class="fLW35">Transgender </span>
								<span class=""> 
									<span> <form:radiobutton id="CURRENT_GENDER3"  path="stdStdOldDoc.currentGender" value="X" /> MTF </span>
									<span> <form:radiobutton id="CURRENT_GENDER4"  path="stdStdOldDoc.currentGender" value="Y" /> FTM </span>
								</span>
							</span>
							<span class="fieldHolder50 ">
								<span class="fLW35">Patient Record No. </span>
								<span  class="fLW60"><form:input id="MED_RECORD_NUMBER"  path="stdStdOldDoc.medRecordNumber" class="standardInputText"  maxlength="17"/></span>
							</span>	<br>					
						</div><br>
						<div class="personInfo pB10">
							<span class="fieldHolder25 ">
								<span class="fLW35">DIN for inmate </span>
								<span  class="fLW60"><form:input id="DIN"  path="stdStdOldDoc.din" class=" standardInputSmallText"  maxlength="10"/></span>
							</span>
							<span class="fieldHolder25 ">
								<span class="fLW35">Insurance Number </span>
								<span class="fLW60"> 
									<form:input id="INSURANCE_NUMBER"  path="stdStdOldDoc.insuranceNumber"  class=" standardInputSmallText"  maxlength="10"/>
								</span>
							</span>
							<span class="fieldHolder25 ">
								<span class="fLW35">Received Date</span>
				                <span class="fLW60">
				                	<fmt:formatDate value="${stdOldDocVO.stdStdOldDoc.receivedDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedReceivedDate" />
				                	<form:input id="RECEIVED_DATE"  path="stdStdOldDoc.receivedDate" value="${theFormattedReceivedDate}"  class="standardInputSmallText mL5" />
				                </span>
				               </span>
				               
				              
							<span class="fieldHolder25 ">
								<span class="fLW35">Facility Name</span>
				                <span class="fLW60">
				                	<form:input path="" class="standardInputSmallText"  maxlength="10"/>
				                </span>
				               </span>	
				               
				               					
						</div>
					<br>
					
			</div>	
			<!--  Race details -->
			<div class="raceRiskReprt borderField">
				<div class="fieldHolder25" id="raceEthnicity">
					<div class="container raceHW pL10">
						
								<div>
									Race
								</div>
								<div>
									<form:checkbox id="RACE_WHITE1"  path="raceWhite" value="1" /> White	
								</div>
								<div>
									<form:checkbox id="RACE_BLACK1"  path="raceBlack" value="1" /> African American
								</div>
								<div>
									<form:checkbox id="RACE_ASIAN1"  path="raceAsian" value="1" /> Asian
								</div>
								<div>
									<form:checkbox id="RACE_INDIAN1"  path="raceIndian" value="1" /> American Indian/Alaskan Native
								</div>
								<div>
									<form:checkbox id="RACE_PACIFIC1"  path="racePacific" value="1" /> Pacific
								</div> 
								<div>
									<form:checkbox id="RACE_OTHER1"  path="raceOther" value="1" /> Other 
								</div>
								<div>
									<form:checkbox id="RACE_UNKNOWN1"  path="raceUnknown" value="1" /> Unknown
								</div>	
							
					</div>	
					<br>
					<div class="borderTD ethnicityHW pL10">	
						
								<div>
									Ethnicity (Check one) </br> Hispanic or Latino<br>
									<form:radiobutton id="ETHNICITY1"  path="stdStdOldDoc.ethnicity" value="01" /> Yes
									<form:radiobutton id="ETHNICITY2"  path="stdStdOldDoc.ethnicity" value="02" /> No
									<form:radiobutton id="ETHNICITY3"  path="stdStdOldDoc.ethnicity" value="09" /> Unknown
								</div>
							
					</div>	
					<br>
					<div class="borderTD birthCountyHW pL10">	
						
								<div>
								
								
								
								
									
								
								
									Country of birth<br>
									<form:select id="BIRTH_COUNTRY"  path="stdStdOldDoc.birthCountry" cssClass="standardInputSmallText" title="Birth Country">
										<form:option value=""></form:option>
										<form:options items="${sessionScope.countryMap}"/>
										<form:option value="OTH">Other</form:option>
										<form:option value="UNK">Unknown</form:option>
									</form:select>
									
									
									
									
									
									
									
								</div>
						
					</div>	
				</div>
				<div class="fieldHolder75">
				<!--  Risk details -->
					<div class=" borderLD borderRD pL10" style="height: 396px">
						
<!-- 								<div class="pT8"> -->
<!-- 									<span class="riskLabelField">Risk Details</span> -->
<!-- 									<span class="riskValueField">Yes</span> -->
<!-- 									<span class="riskValueField">No</span> -->
<!-- 									<span class="riskValueField">Unknown</span><br> -->
<!-- 								</div>	 -->
								<div class="pT8">
									<span class="fieldHolder25 bold"> Sex With Male</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton id="SWM1"  path="stdStdOldDoc.swm" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton id="SWM2"  path="stdStdOldDoc.swm" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton id="SWM3"  path="stdStdOldDoc.swm" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton id="SWM4"  path="stdStdOldDoc.swm" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton id="SWM5"  path="stdStdOldDoc.swm" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton id="SWM6"  path="stdStdOldDoc.swm" value="O" /> Yes - Oral Sex Only</span>
											
									</span>
								</div>
								<div class="pT8">
								
									<span class="fieldHolder25 bold"> Sex With Female</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton id="SWF1"  path="stdStdOldDoc.swf" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton id="SWF2"  path="stdStdOldDoc.swf" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton id="SWF3"  path="stdStdOldDoc.swf" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton id="SWM7"  path="stdStdOldDoc.swm" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton id="SWF4"  path="stdStdOldDoc.swf" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton id="SWF5"  path="stdStdOldDoc.swf" value="O" /> Yes - Oral Sex Only</span>
									</span>
									
								</div>
								<div class="pT8 pB5">
									<span class="fieldHolder25 bold"> Injection Drug Use</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton id="IDU1"  path="stdStdOldDoc.idu" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton id="IDU2"  path="stdStdOldDoc.idu" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton id="IDU3"  path="stdStdOldDoc.idu" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton id="IDU4"  path="stdStdOldDoc.idu" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton id="IDU5"  path="stdStdOldDoc.idu" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton id="IDU6"  path="stdStdOldDoc.idu" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
								
								
								<div class="pT8 pB5">
									<span class="fieldHolder25 bold"> HETEROSEXUAL contact with intravenous/injection drug user</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton id="SEX_PARTNER_IDU1"  path="stdStdOldDoc.sexPartnerIdu" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton id="SEX_PARTNER_IDU2"  path="stdStdOldDoc.sexPartnerIdu" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton id="SEX_PARTNER_IDU3"  path="stdStdOldDoc.sexPartnerIdu" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton id="SEX_PARTNER_IDU4"  path="stdStdOldDoc.sexPartnerIdu" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton id="SEX_PARTNER_IDU5"  path="stdStdOldDoc.sexPartnerIdu" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton id="SEX_PARTNER_IDU6"  path="stdStdOldDoc.sexPartnerIdu" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
								
								<div class="clearBoth pT8 pB5">
									<span class="fieldHolder25 bold"> HETEROSEXUAL contact with bisexual male</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton id="SEX_PARTNER_MSM1"  path="stdStdOldDoc.sexPartnerMsm" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton id="SEX_PARTNER_MSM2"  path="stdStdOldDoc.sexPartnerMsm" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton id="SEX_PARTNER_MSM3"  path="stdStdOldDoc.sexPartnerMsm" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton id="SEX_PARTNER_MSM4"  path="stdStdOldDoc.sexPartnerMsm" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton id="SEX_PARTNER_MSM5"  path="stdStdOldDoc.sexPartnerMsm" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton id="SEX_PARTNER_MSM6"  path="stdStdOldDoc.sexPartnerMsm" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
								<div class="clearBoth pT8 pB5">
									<span class="fieldHolder25 bold">  HETEROSEXUAL contact with intravenous/injection drug user</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton id="DRUG_USE1"  path="stdStdOldDoc.drugUse" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton id="DRUG_USE2"  path="stdStdOldDoc.drugUse" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton id="DRUG_USE3"  path="stdStdOldDoc.drugUse" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton id="DRUG_USE4"  path="stdStdOldDoc.drugUse" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton id="DRUG_USE5"  path="stdStdOldDoc.drugUse" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton id="DRUG_USE6"  path="stdStdOldDoc.drugUse" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
								<div class="clearBoth pT8 pB5">
									<span class="fieldHolder25 bold"> Had sex with an anonymous person?</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton id="SWA1"  path="stdStdOldDoc.swa" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton id="SWA2"  path="stdStdOldDoc.swa" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton id="SWA3"  path="stdStdOldDoc.swa" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton id="SWA4"  path="stdStdOldDoc.swa" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton id="SWA5"  path="stdStdOldDoc.swa" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton id="SWA6"  path="stdStdOldDoc.swa" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
								
								
								<div class="clearBoth pT8 pB5">
									<span class="fieldHolder25 bold"> Had sex with a transgender person?</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton id="SWT1"  path="stdStdOldDoc.swt" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton id="SWT2"  path="stdStdOldDoc.swt" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton id="SWT3"  path="stdStdOldDoc.swt" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton id="SWT4"  path="stdStdOldDoc.swt" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton id="SWT5"  path="stdStdOldDoc.swt" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton id="SWT6"  path="stdStdOldDoc.swt" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
								
								<div class="clearBoth pT8 pB5">
									<span class="fieldHolder25 bold"> Had sex while intoxicated?</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton id="SEX_WHILE_INTOX1"  path="stdStdOldDoc.sexWhileIntox" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton id="SEX_WHILE_INTOX2"  path="stdStdOldDoc.sexWhileIntox" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton id="SEX_WHILE_INTOX3"  path="stdStdOldDoc.sexWhileIntox" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton id="SEX_WHILE_INTOX4"  path="stdStdOldDoc.sexWhileIntox" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton id="SEX_WHILE_INTOX5"  path="stdStdOldDoc.sexWhileIntox" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton id="SEX_WHILE_INTOX6"  path="stdStdOldDoc.sexWhileIntox" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
								
								
								<div class="clearBoth pT8 pB5">
									<span class="fieldHolder25 bold"> Exchanged drugs/money for Sex?</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton id="EXCHANGE_SEX1"  path="stdStdOldDoc.exchangeSex" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton id="EXCHANGE_SEX2"  path="stdStdOldDoc.exchangeSex" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton id="EXCHANGE_SEX3"  path="stdStdOldDoc.exchangeSex" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton id="EXCHANGE_SEX4"  path="stdStdOldDoc.exchangeSex" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton id="EXCHANGE_SEX5"  path="stdStdOldDoc.exchangeSex" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton id="EXCHANGE_SEX6"  path="stdStdOldDoc.exchangeSex" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
								
								<div class="clearBoth pT8 pB5">
									<span class="fieldHolder25 bold"> Had sex without a condom?</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton id="SEX_WOUT_CONDOM1"  path="stdStdOldDoc.sexWoutCondom" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton id="SEX_WOUT_CONDOM2"  path="stdStdOldDoc.sexWoutCondom" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton id="SEX_WOUT_CONDOM3"  path="stdStdOldDoc.sexWoutCondom" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton id="SEX_WOUT_CONDOM4"  path="stdStdOldDoc.sexWoutCondom" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton id="SEX_WOUT_CONDOM5"  path="stdStdOldDoc.sexWoutCondom" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton id="SEX_WOUT_CONDOM6"  path="stdStdOldDoc.sexWoutCondom" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>

								<div class="clearBoth pT8 pB5">
									<span class="fieldHolder25 bold"> Been incarcerated</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton id="INCARCERATED1"  path="stdStdOldDoc.incarcerated" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton id="INCARCERATED2"  path="stdStdOldDoc.incarcerated" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton id="INCARCERATED3"  path="stdStdOldDoc.incarcerated" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton id="INCARCERATED4"  path="stdStdOldDoc.incarcerated" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton id="INCARCERATED5"  path="stdStdOldDoc.incarcerated" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton id="INCARCERATED6"  path="stdStdOldDoc.incarcerated" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
								<div class="clearBoth pT8 pB5">
									<span class="fieldHolder25 bold"> Shared drug paraphenalia</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton id="SHARED_DRUG_EQUIP1"  path="stdStdOldDoc.sharedDrugEquip" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton id="SHARED_DRUG_EQUIP2"  path="stdStdOldDoc.sharedDrugEquip" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton id="SHARED_DRUG_EQUIP3"  path="stdStdOldDoc.sharedDrugEquip" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton id="SHARED_DRUG_EQUIP4"  path="stdStdOldDoc.sharedDrugEquip" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton id="SHARED_DRUG_EQUIP5"  path="stdStdOldDoc.sharedDrugEquip" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton id="SHARED_DRUG_EQUIP6"  path="stdStdOldDoc.sharedDrugEquip" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
							
								
							
					
							
										
					</div>
				</div>	
			</div>
			
	
	
	
	
		<div class="clearBoth  divHeadingCls bGHeading"  style="background:#DDDDDD; ">
				<span class=" mL2"> Provider Details:</span>					
			</div>
			<div class="mLRTB pB10">	
				<span class="fieldHolder25">
					<span class="">Provider Name</span>
					<span class="">  <form:input id="PROV_NAME"  path="stdStdOldDoc.provName" class="standardInputMedText ml10" maxlength="20"/>  </span>
				</span>
				
			</div>
			
			<div class="b1" style="margin: 15px 5px 10px 5px">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	  
 		                	<span class="pL10"><form:input id="rawProviderAddress"  path="rawprovideraddress" class="standardinputText" maxlength="62" readonly="true"/> </span>              	 	        
	 	                	            	 	                    
	 	                    <span class="pL30">City</span>
	 	                    <span class="pL10"><form:input id="PROV_CITY"  path="stdStdOldDoc.provCity" class="standardinputSmallText1" maxlength="30"/></span>
	 	                    
	 	                    <span class="pL30">State </span>
	 	                    <span  class="pL10">
								<form:input id="PROV_STATE"  path="stdStdOldDoc.provState" class="phoneNumberText" maxlength="2"/>
							</span>
	 	                    
	 	                    <span class="pL30">Zip </span>
	 	                    <span class="pL10"> 
	 	                       <form:input id="PROV_ZIP"  path="stdStdOldDoc.provZip" class="standardinputSmallText1" maxlength="5"/> - <form:input id="PROV_PLUS4"  path="stdStdOldDoc.provPlus4" class="standardinputSmallText1" maxlength="4"/> 
	 	                    </span> 
	 	                    
	 	                    <span class="pL30">County </span>
	 	                    <span class="pL10"> 
	 	                       <form:select id="PROV_COUNTY"  path="stdStdOldDoc.provCounty"  cssClass="oAdrDlg roundedBrdr newCounty"> 
									<form:option value=""></form:option>
									<form:options items="${sessionScope.countyMap}"/>
								</form:select>
	 	                    </span>    	                    
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (raw)</span>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10">
 	            
 	            	<span  class="" style="padding-left:59px"><form:input id="PROV_ADDRESS"  path="stdStdOldDoc.provAddress" class="standardInputText dispAddressStdRaw" style="width:300px" maxlength="62" /></span>
			 	              	 	                 
 	                 <span class="pL30"> Phone</span>
 					 <span class="pL10"> <form:input id="PROV_PHONE"  path="stdStdOldDoc.provPhone" class="pNumber standardInputSmallText" maxlength="20"/></span>
 	                <br>               
 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
 	                 
 	            	
 	            </div>
 	        </div>
	
	
	
			<div class="clearBoth  divHeadingCls bGHeading"  style="background:#DDDDDD; ">
				<span class=" mL2"> Facility Details:</span>					
			</div>
			<div class="mLRTB pB10">	
				<span class="fieldHolder25">
					<span class="">Facility Name</span>
					<span class="">  <form:input id="FACILITY_NAME"  path="stdStdOldDoc.facilityName" class="standardInputMedText ml10" maxlength="20"/>  </span>
				</span>
				
			</div>
	
		
		</div>
	</div>

	<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
	
	<div id="button-row" class="button-row clear documentButtons"  align="center">


		<c:if  test="${stdOldDocVO.stateno != null && stdOldDocVO.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null}" >
				<c:if  test="${userPermissions['99'] != null}">
					<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3 displayNone"  value="Trigger Assignment"  onclick="loadSurvAssignmentPopUp()"/>
				</c:if>
		</c:if>
		<c:choose>
			<c:when  test="${param.hasDeleteFunctionality == true}" >	
					<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
					<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
			</c:when>
			<c:when test="${userPermissions['90'] != null && stdOldDocVO.stateno != null && stdOldDocVO.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null}">
								<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
						</c:when>
		</c:choose>

						
		<input type="button" id="sButton" class="prfBtnclick button rounded blue effect-3" value="Submit" />
		<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />
	</div>
	
	
</form:form>															



