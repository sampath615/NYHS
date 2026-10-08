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
                    <span class="ml10"><form:input path="STATENO"  class="standardInputSmallText" style="width:180px" maxlength="10" readonly="true"/> </span>
	            </div>    
            </div>
            <div style="float:left; width: 30%">
	            <div>
	                <span class=" ml10">	eHARS Import Date: </span>
	                <fmt:formatDate value="${stdOldDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
					<span class=" ml10"><form:input path="EHARS_IMPORT_DATE" value="${theFormattedDate}"  class="standardInputSmallText ml10" readonly="true"/></span>
	            </div>                  
            </div>
            <div style="float:left; width: 30%; text-align: right">
            	<div>
	                <span class="mL10">Document ID: </span>
					<span class=" mL10"><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
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
								<span class="pL150"><form:input path="LNAME" id="lName" class="standardInputMedText" maxlength="25" /></span> 
								<span  class="pL30"><form:input path="FNAME" class="standardInputMedText" maxlength="20" /></span>
								<span  class="pL30"><form:input path="MNAME" class="standardInputMedText" maxlength="25" /></span>
								<span  class="pL30"><form:input path="SUFFIX" class="standardInputMedText" maxlength="25" /></span>
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
				 	                	<span class="pL10"><form:input path="rawPersonAddress" class="standardInputText newAddress" style="width:300px" maxlength="62" />  </span>
				 	                    
				 	                    
				 	                    <span class="pL30">City</span>
				 	                    <span class="pL10"><form:input path="PERSON_CITY" class="oAdrDlg standardInputSmallText1 newCity" maxlength="30"/></span>
				 	                    
				 	                    <span class="pL30">State </span>
				 	                    <span  class="pL10">
											<form:input path="PERSON_STATE" cssClass=" standardInputSmallText1 "  maxlength="2"/>
										</span>
				 	                    
				 	                    <span class="pL30">Zip </span>
				 	                    <span class="pL10"> 
				 	                        <form:input path="PERSON_ZIP" class="standardInputSmallText1" maxlength="5"/> -<form:input path="PERSON_PLUS4" class="standardInputSmallText1" maxlength="5"/> 
				 	                    </span>
				 	                    <span class="pL30">County </span>
				 		                   <span class="pL10"> 
												<form:select path="PERSON_COUNTY"  cssClass="standardInputSmallText1" > 
													<form:option value=""></form:option>
													<form:options items="${sessionScope.countyMap}"/>
												</form:select>
					 		               </span>       	                    
				 	                    <br>
				 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
				 	                </span>
			 	            </div>
			 	            <div class="personInfo pT10 pTRBL10">
			 	            	<span  class="" style="padding-left:59px"><form:input path="PERSON_ADDRESS" class="standardInputText dispAddressStdRaw" style="width:300px" maxlength="62" /></span>
			 	              	 	                 
			 	                 <span class="pL30"> Phone</span>
			 					 <span class="pL10"> <form:input path="PERSON_PHONE" class="pNumber standardInputSmallText" maxlength="20"/></span>
			 	                <br>               
			 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
			 	                    
			 	            </div>
			 	        </div>

						<div class="personInfo pB10">
							<span class="fieldHolder25 ">
								<span class="fLW35">Date of Birth </span>
								<span  class="fLW60"><form:input path="DOB_TEXT" id="dobValidation" class="dPicker standardInputSmallText"  maxlength="10"/></span>
							</span>
							<span class="fieldHolder25 ">
								<span class="fLW35">Date of Death </span>
								<span class="fLW60"> 
									<form:input path="DOD_TEXT" id="dodValidation" class="dPicker standardInputSmallText"  maxlength="10"/>
								</span>
							</span>
							<span class="fieldHolder25 ">
								<span class="fLW35">Vital Status</span>
				                <span class="fLW60">
				                	<form:radiobutton path="VITAL_STATUS" value="1" /> Alive
				                	<form:radiobutton path="VITAL_STATUS" value="2" /> Dead
				                </span>
				               </span>
				               
				              
							<span class="fieldHolder25 ">
								<span class="fLW35">SSN</span>
				                <span class="fLW60">
				                	<form:input path="SSN" class="standardInputSmallText"  maxlength="10"/>
				                </span>
				               </span>	
				               
				               					
						</div><br>
						<div class="personInfo pT10 clear">
							<span class="fieldHolder25 ">
								<span class="fLW35">Gender </span>
								<span class=""> 
									<span> <form:radiobutton path="CURRENT_GENDER" value="M" /> Male </span>
									<span> <form:radiobutton path="CURRENT_GENDER" value="F" /> Female </span>
								</span>
							</span>
							<span class="fieldHolder25 ">
								<span class="fLW35">Transgender </span>
								<span class=""> 
									<span> <form:radiobutton path="CURRENT_GENDER" value="X" /> MTF </span>
									<span> <form:radiobutton path="CURRENT_GENDER" value="Y" /> FTM </span>
								</span>
							</span>
							<span class="fieldHolder50 ">
								<span class="fLW35">Patient Record No. </span>
								<span  class="fLW60"><form:input path="MED_RECORD_NUMBER" class="standardInputText"  maxlength="17"/></span>
							</span>	<br>					
						</div><br>
						<div class="personInfo pB10">
							<span class="fieldHolder25 ">
								<span class="fLW35">DIN for inmate </span>
								<span  class="fLW60"><form:input path="DIN" class=" standardInputSmallText"  maxlength="10"/></span>
							</span>
							<span class="fieldHolder25 ">
								<span class="fLW35">Insurance Number </span>
								<span class="fLW60"> 
									<form:input path="INSURANCE_NUMBER"  class=" standardInputSmallText"  maxlength="10"/>
								</span>
							</span>
							<span class="fieldHolder25 ">
								<span class="fLW35">Received Date</span>
				                <span class="fLW60">
				                	<fmt:formatDate value="${stdOldDocVO.RECEIVED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedReceivedDate" />
				                	<form:input path="RECEIVED_DATE" value="${theFormattedReceivedDate}"  class="standardInputSmallText mL5" />
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
									<form:checkbox path="RACE_WHITE" value="1" /> White	
								</div>
								<div>
									<form:checkbox path="RACE_BLACK" value="1" /> African American
								</div>
								<div>
									<form:checkbox path="RACE_ASIAN" value="1" /> Asian
								</div>
								<div>
									<form:checkbox path="RACE_INDIAN" value="1" /> American Indian/Alaskan Native
								</div>
								<div>
									<form:checkbox path="RACE_PACIFIC" value="1" /> Pacific
								</div> 
								<div>
									<form:checkbox path="RACE_OTHER" value="1" /> Other 
								</div>
								<div>
									<form:checkbox path="RACE_UNKNOWN" value="1" /> Unknown
								</div>	
							
					</div>	
					<br>
					<div class="borderTD ethnicityHW pL10">	
						
								<div>
									Ethnicity (Check one) </br> Hispanic or Latino<br>
									<form:radiobutton path="ETHNICITY" value="01" /> Yes
									<form:radiobutton path="ETHNICITY" value="02" /> No
									<form:radiobutton path="ETHNICITY" value="09" /> Unknown
								</div>
							
					</div>	
					<br>
					<div class="borderTD birthCountyHW pL10">	
						
								<div>
								
								
								
								
									
								
								
									Country of birth<br>
									<form:select path="BIRTH_COUNTRY" cssClass="standardInputSmallText" title="Birth Country">
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
											<span class="fieldHolder10"><form:radiobutton path="SWM" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton path="SWM" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton path="SWM" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton path="SWM" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton path="SWM" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton path="SWM" value="O" /> Yes - Oral Sex Only</span>
											
									</span>
								</div>
								<div class="pT8">
								
									<span class="fieldHolder25 bold"> Sex With Female</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton path="SWF" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton path="SWF" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton path="SWF" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton path="SWM" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton path="SWF" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton path="SWF" value="O" /> Yes - Oral Sex Only</span>
									</span>
									
								</div>
								<div class="pT8 pB5">
									<span class="fieldHolder25 bold"> Injection Drug Use</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton path="IDU" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton path="IDU" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton path="IDU" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton path="IDU" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton path="IDU" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton path="IDU" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
								
								
								<div class="pT8 pB5">
									<span class="fieldHolder25 bold"> HETEROSEXUAL contact with intravenous/injection drug user</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton path="SEX_PARTNER_IDU" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton path="SEX_PARTNER_IDU" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton path="SEX_PARTNER_IDU" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton path="SEX_PARTNER_IDU" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton path="SEX_PARTNER_IDU" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton path="SEX_PARTNER_IDU" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
								
								<div class="clearBoth pT8 pB5">
									<span class="fieldHolder25 bold"> HETEROSEXUAL contact with bisexual male</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton path="SEX_PARTNER_MSM" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton path="SEX_PARTNER_MSM" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton path="SEX_PARTNER_MSM" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton path="SEX_PARTNER_MSM" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton path="SEX_PARTNER_MSM" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton path="SEX_PARTNER_MSM" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
								<div class="clearBoth pT8 pB5">
									<span class="fieldHolder25 bold">  HETEROSEXUAL contact with intravenous/injection drug user</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton path="DRUG_USE" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton path="DRUG_USE" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton path="DRUG_USE" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton path="DRUG_USE" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton path="DRUG_USE" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton path="DRUG_USE" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
								<div class="clearBoth pT8 pB5">
									<span class="fieldHolder25 bold"> Had sex with an anonymous person?</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton path="SWA" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton path="SWA" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton path="SWA" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton path="SWA" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton path="SWA" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton path="SWA" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
								
								
								<div class="clearBoth pT8 pB5">
									<span class="fieldHolder25 bold"> Had sex with a transgender person?</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton path="SWT" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton path="SWT" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton path="SWT" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton path="SWT" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton path="SWT" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton path="SWT" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
								
								<div class="clearBoth pT8 pB5">
									<span class="fieldHolder25 bold"> Had sex while intoxicated?</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton path="SEX_WHILE_INTOX" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton path="SEX_WHILE_INTOX" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton path="SEX_WHILE_INTOX" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton path="SEX_WHILE_INTOX" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton path="SEX_WHILE_INTOX" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton path="SEX_WHILE_INTOX" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
								
								
								<div class="clearBoth pT8 pB5">
									<span class="fieldHolder25 bold"> Exchanged drugs/money for Sex?</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton path="EXCHANGE_SEX" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton path="EXCHANGE_SEX" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton path="EXCHANGE_SEX" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton path="EXCHANGE_SEX" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton path="EXCHANGE_SEX" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton path="EXCHANGE_SEX" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
								
								<div class="clearBoth pT8 pB5">
									<span class="fieldHolder25 bold"> Had sex without a condom?</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton path="SEX_WOUT_CONDOM" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton path="SEX_WOUT_CONDOM" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton path="SEX_WOUT_CONDOM" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton path="SEX_WOUT_CONDOM" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton path="SEX_WOUT_CONDOM" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton path="SEX_WOUT_CONDOM" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>

								<div class="clearBoth pT8 pB5">
									<span class="fieldHolder25 bold"> Been incarcerated</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton path="INCARCERATED" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton path="INCARCERATED" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton path="INCARCERATED" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton path="INCARCERATED" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton path="INCARCERATED" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton path="INCARCERATED" value="O" /> Yes - Oral Sex Only</span>
									</span>
								
								</div>
								<div class="clearBoth pT8 pB5">
									<span class="fieldHolder25 bold"> Shared drug paraphenalia</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton path="SHARED_DRUG_EQUIP" value="Y" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton path="SHARED_DRUG_EQUIP" value="N" /> No</span>
											<span class="fieldHolder15"><form:radiobutton path="SHARED_DRUG_EQUIP" value="U" /> Unknown</span>
											<span class="fieldHolder15"><form:radiobutton path="SHARED_DRUG_EQUIP" value="R" /> Refused</span>
											<span class="fieldHolder15"><form:radiobutton path="SHARED_DRUG_EQUIP" value="D" /> Did not ask</span>		
											<span class="fieldHolder20"><form:radiobutton path="SHARED_DRUG_EQUIP" value="O" /> Yes - Oral Sex Only</span>
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
					<span class="">  <form:input path="PROV_NAME" class="standardInputMedText ml10" maxlength="20"/>  </span>
				</span>
				
			</div>
			
			<div class="b1" style="margin: 15px 5px 10px 5px">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	  
 		                	<span class="pL10"><form:input path="rawProviderAddress" class="standardinputText" maxlength="62" readonly="true"/> </span>              	 	        
	 	                	            	 	                    
	 	                    <span class="pL30">City</span>
	 	                    <span class="pL10"><form:input path="PROV_CITY" class="standardinputSmallText1" maxlength="30"/></span>
	 	                    
	 	                    <span class="pL30">State </span>
	 	                    <span  class="pL10">
								<form:input path="PROV_STATE" class="phoneNumberText" maxlength="2"/>
							</span>
	 	                    
	 	                    <span class="pL30">Zip </span>
	 	                    <span class="pL10"> 
	 	                       <form:input path="PROV_ZIP" class="standardinputSmallText1" maxlength="5"/> - <form:input path="PROV_PLUS4" class="standardinputSmallText1" maxlength="4"/> 
	 	                    </span> 
	 	                    
	 	                    <span class="pL30">County </span>
	 	                    <span class="pL10"> 
	 	                       <form:select path="PROV_COUNTY"  cssClass="oAdrDlg roundedBrdr newCounty"> 
									<form:option value=""></form:option>
									<form:options items="${sessionScope.countyMap}"/>
								</form:select>
	 	                    </span>    	                    
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (raw)</span>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10">
 	            
 	            	<span  class="" style="padding-left:59px"><form:input path="PROV_ADDRESS" class="standardInputText dispAddressStdRaw" style="width:300px" maxlength="62" /></span>
			 	              	 	                 
 	                 <span class="pL30"> Phone</span>
 					 <span class="pL10"> <form:input path="PROV_PHONE" class="pNumber standardInputSmallText" maxlength="20"/></span>
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
					<span class="">  <form:input path="FACILITY_NAME" class="standardInputMedText ml10" maxlength="20"/>  </span>
				</span>
				
			</div>
	
		
		</div>
	</div>

	<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
	
	<div id="button-row" class="button-row clear documentButtons"  align="center">


		<c:if  test="${stdOldDocVO.STATENO != null && stdOldDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}" >
				<c:if  test="${userPermissions['99'] != null}">
					<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3 displayNone"  value="Trigger Assignment"  onclick="loadSurvAssignmentPopUp()"/>
				</c:if>
		</c:if>
		<c:choose>
			<c:when  test="${param.hasDeleteFunctionality == true}" >	
					<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
					<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
			</c:when>
			<c:when test="${userPermissions['90'] != null && stdOldDocVO.STATENO != null && stdOldDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
								<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
						</c:when>
		</c:choose>

						
		<input type="button" id="sButton" class="prfBtnclick button rounded blue effect-3" value="Submit" />
		<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />
	</div>
	
	
</form:form>															



