<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	<div class="container w100 nonRawEditFields">
            <div class="pB10" style="width: 100%">
            	<div  class="floatLeft" style="width: 50%">
	                <span class=" ml10 label33Perc bold">STATENO: </span>
                    <span class="ml10"><form:input path="messageReturnVO.STATENO" class="standardInputSmallText" style="width:180px"/> </span>
	            </div>    
                <div class="floatRight" style="width: 50%">
	                <span class=" ml10 label33Perc bold">CITYNO: </span>
	                <span class="ml10 "><form:input path="messageReturnVO.CITY_NO" class="standardInputSmallText" style="width:180px" maxlength="10"/> </span>
	            </div>
	            
            </div>
            
            <div class="pT20  pB10" style="width: 100%">
            	<div  class="floatLeft" style="width: 50%">
	                <span class=" ml10 label33Perc bold">Message Enter Date </span>
	                <fmt:formatDate value="${psMessageVO.messageReturnVO.MESSAGE_ENTERED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedMessageEnteredDate" />
                    <span class="ml10"><form:input path="messageReturnVO.MESSAGE_ENTERED_DATE" class="standardInputSmallText" style="width:180px" value="${theFormattedMessageEnteredDate}" /> </span>
	            </div>    
                <div class="floatRight" style="width: 50%">
	                <span class=" ml10 label33Perc bold">Message Received Date: </span>
	                <fmt:formatDate value="${psMessageVO.messageReturnVO.MESSAGE_RECEIVED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedMessageReceivedDate" />
	                <span class="ml10 "><form:input path="messageReturnVO.MESSAGE_RECEIVED_DATE" class="standardInputSmallText" style="width:180px" maxlength="10" value="${theFormattedMessageReceivedDate}"/> </span>
	            </div>
            </div>
            
            <div class="pT20  pB10" style="width: 100%">
            
              <div class="floatLeft" style="width: 100%">
	                <span class=" ml10 label16Perc bold">Disposition: </span>
	                <span class="ml10 "><form:select path="messageReturnVO.DISPOSITION_CODE" cssClass="roundedBorder"  title="Disposition">
													<form:option value=""></form:option>
													<c:forEach items="${dispositionCodeValues}" var="dispositionCode">
														<form:option value="${dispositionCode.DISPOSITION_CODE}" label="${dispositionCode.DISPOSITION_DESC}"></form:option>
													</c:forEach>
										</form:select> </span>
	            </div>
	       </div>
            
   		</div>
   		
   		<div class="container mT10">	 
				<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
					<span class=" mL2 bold">Person Information</span>
				</div>
				<div class="pL20 pT20 pB20 mR20">
						<div class="personInfo">
							<span class="personLabelDoc bold">Name </span>
							<span> 
								<span class="pL150"><form:input path="messageReturnVO.LAST_NAME" class="standardInputMedText required" maxlength="25" title="Last name"/></span> 
								<span  class="pL30"><form:input path="messageReturnVO.FIRST_NAME" class="standardInputMedText" maxlength="20" title="First name"/></span>
								<span  class="pL30"><form:input path="messageReturnVO.MIDDLE_NAME" class="standardInputMedText " maxlength="25" title=" Middle name"/></span>
								<br/>
								<span class="labelunderneath2 bold"> Last<span class="requiredInd editableFields" title="Required Field">*</span></span>
								<span class="labelunderneath1 bold"> First</span>
								<span class="labelunderneath1 bold"> Middle</span>
							</span>
						</div>
						<div class="b1" style="margin: 15px 5px 10px 5px">
			 	            <div class="personInfo pTRBL10 pB10" >
			 	                <span class="personLabelDoc bold"> Address </span>
			 		                <span> 	                	
				 	                	<span class="pL10"><form:input path="messageReturnVO.PERSON_ADDRESS" class="standardInputText newAddress widgetFields widgetAddressFieldsReadonly" style="width:250px" maxlength="62" title="Patient Address"/>  </span>
				 	                    
				 	                    
				 	                    <span class="pL10 bold">City</span>
				 	                    <span class="pL10"><form:input path="messageReturnVO.PERSON_CITY" class="oAdrDlg standardInputSmallText newCity widgetFields widgetAddressFieldsReadonly" maxlength="30"  title="City"/></span>
				 	                    
				 	                    <span class="pL10 bold">State </span>
				 	                    <span  class="pL10">
											<form:select path="messageReturnVO.PERSON_STATE" cssClass=" standardInputSmallText4  widgetFields newState widgetAddressFieldsReadonly"  title="State">
													<form:option value=""></form:option>
													<form:options items="${sessionScope.stateMap}"/>
											</form:select>
										</span>
				 	                    
				 	                    <span class="pL10 bold">Zip </span>
				 	                    <span class="pL10"> 
				 	                        <form:input path="messageReturnVO.PERSON_ZIP" class="standardInputSmallText1 widgetFields newZip widgetAddressFieldsReadonly"  maxlength="5" title="Zip"/>
				 	                    </span>
				 	                </span>
			 	            </div>
			 	        </div>	

						<div class="personInfo pB10">
							<div class="fieldHolder25 ">
								<span class="fLW35 bold">Date of Birth </span>
								<span  class="fLW60"><form:input path="messageReturnVO.DOB" datevalidations="completeDate" class="dPicker standardInputSmallText"   maxlength="10" title="Date of Birth"/></span>
							</div>
							<div class="fieldHolder25 ">
								<span class="fLW35 bold">Date of Death </span>
								<span class="fLW60"> 
									<form:input path="messageReturnVO.DOD" datevalidations="completeDate" class="dPicker standardInputSmallText"   maxlength="10" title="Date of Death"/>
								</span>
							</div>						
						</div>
						<br>
						<div class="personInfo pT10 clear" >
							 <div class="floatLeft mR10"> 
								<span class="bold"> Birth Sex </span>
								<span  class="">
									<span> <form:radiobutton path="messageReturnVO.SEX_AT_BIRTH" value="1" /> Male </span>
									<span> <form:radiobutton path="messageReturnVO.SEX_AT_BIRTH" value="2" /> Female </span>		
								</span>
							</div>
							<div class="floatLeft pL10" style="border-left: 1px dotted">
								<span class=" ">
									<span class="bold">Current Gender </span>
									<span  class="">
										<span> <form:radiobutton path="messageReturnVO.GENDER" value="1" /> Male </span>
										<span> <form:radiobutton path="messageReturnVO.GENDER" value="2" /> Female </span>
										<span> <form:radiobutton path="messageReturnVO.GENDER" value="4" /> Transgender, MTF </span>		
										<span> <form:radiobutton path="messageReturnVO.GENDER" value="3" /> Transgender, FTM </span>		
										<span> <form:radiobutton path="messageReturnVO.GENDER" value="5" /> Transgender, unspecified </span>
										<span> <form:radiobutton path="messageReturnVO.GENDER" value="6" /> Refused</span>
										<span> <form:radiobutton path="messageReturnVO.GENDER" value="7" /> Unknown</span>
									</span>
								</span> 
							</div>
						</div><br>
						
					</div>
				
				<div class="raceRiskReprt borderField">
				
				
					<div class="fieldHolder25" id="raceEthnicity">
						<div class="container">
							<div class="pL20 pT20 pB20 mR20">
									<div class="pB20 bold">
										Race  
									</div>
									<div>
										<form:checkbox path="messageReturnVO.RACE_WHITE" value="true" /> White	
									</div>
									<div>
										<form:checkbox path="messageReturnVO.RACE_BLACK" value="true" /> African American
									</div>
									<div>
										<form:checkbox path="messageReturnVO.RACE_ASIAN" value="true" /> Asian
									</div>
									<div>
										<form:checkbox path="messageReturnVO.RACE_INDIAN" value="true" /> American Indian/Alaskan Native
									</div>
									<div>
										<form:checkbox path="messageReturnVO.RACE_PACIFIC" value="true" /> Pacific
									</div> 
									<div>
										<form:checkbox path="messageReturnVO.RACE_OTHER" value="true"  /> Other 
									</div>
								</div>		
						</div>	
						<br>
						<div class="borderTD">	
							<div class="pL20 pT20 pB20 mR20">
									<div>
										<span class="bold">Country of birth</span>
										<form:input path="messageReturnVO.BIRTH_COUNTRY" cssClass="standardInputSmallText" title="Country of birth" />
									</div>
								</div>
						</div>	
					</div>
					<div class="floatLeft borderLD borderRD fieldHolder33" id="PATIENT_RISK">
							<div class="pL20 pT20 pB20 mR20">
									<div class="pB10 bold">
										Individual drugs
									</div>
								 
								<div class="pT8">
									<span class="riskLabelField bold">Crack</span>
									<span class="riskValueField"><form:radiobutton path="messageReturnVO.CRACK" value="Y" title="Yes" />Yes</span>
									<span class="riskValueField"><form:radiobutton path="messageReturnVO.CRACK" value="N" title="No" />No</span><br>
								</div>
								<div class="pT8">
									<span class="riskLabelField bold">Cocaine</span>
									<span class="riskValueField"><form:radiobutton path="messageReturnVO.COCAINE" value="Y" title="Yes" />Yes</span>
									<span class="riskValueField"><form:radiobutton path="messageReturnVO.COCAINE" value="N" title="No" />No</span><br>
								</div>
								<div class="pT8">
									<span class="riskLabelField bold">Heroin</span>
									<span class="riskValueField"><form:radiobutton path="messageReturnVO.HEROIN" value="Y" title="Yes" />Yes</span>
									<span class="riskValueField"><form:radiobutton path="messageReturnVO.HEROIN" value="N" title="No" />No</span><br>
								</div>
								<div class="pT8">
									<span class="riskLabelField bold">Methamphetamines</span>
									<span class="riskValueField"><form:radiobutton path="messageReturnVO.METH" value="Y" title="Yes" />Yes</span>
									<span class="riskValueField"><form:radiobutton path="messageReturnVO.METH" value="N" title="No" />No</span><br>
								</div>
								<div class="pT8">
									<span class="riskLabelField bold">Nitrates/Poppers</span>
									<span class="riskValueField"><form:radiobutton path="messageReturnVO.NITRATES" value="Y" title="Yes" />Yes</span>
									<span class="riskValueField"><form:radiobutton path="messageReturnVO.NITRATES" value="N" title="No" />No</span><br>
								</div>
								<div class="pT8">
									<span class="riskLabelField bold">ED Medications</span>
									<span class="riskValueField"><form:radiobutton path="messageReturnVO.ED_MEDS" value="Y" title="Yes" />Yes</span>
									<span class="riskValueField"><form:radiobutton path="messageReturnVO.ED_MEDS" value="N" title="No" />No</span><br>
								</div>
								
								<div class="pT8">
									<span class="riskLabelField bold">Other Drugs</span>
									<span  class="">
										<form:input path="messageReturnVO.OTHER_DRUGS" cssClass="standardInputSmallText" title="Other Drugs" />
									</span>
								</div>					
							</div>
		
					</div>
					<div class="floatLeft borderLD borderRD fLW40" id="PATIENT_RISK">
						<div class="pL20 pT20 pB20 mR20">
							<div class="pT8">
									<span class="fieldHolder20 bold">DIN</span>
									<span  class="">
										<form:input path="messageReturnVO.DIN" cssClass="standardInputSmallText" title="DIN" />
									</span>
								</div>
								<div class="pT8">
									<span class="fieldHolder20 bold">NYSID</span>
									<span  class="">
										<form:input path="messageReturnVO.NYSID" cssClass="standardInputSmallText" title="DIN" />
									</span>
								</div>
								<div class="pT8">
									<span class="fieldHolder20 bold">Pregnant</span>
									<span class="riskValueField"><form:radiobutton path="messageReturnVO.PREGNANT" value="1" title="Yes" />Yes</span>
									<span class="riskValueField"><form:radiobutton path="messageReturnVO.PREGNANT" value="2" title="No" />No</span>
									<span class="riskValueField"><form:radiobutton path="messageReturnVO.PREGNANT" value="3" title="Unknown" />Unknown</span>
									<span class="riskValueField"><form:radiobutton path="messageReturnVO.PREGNANT" value="4" title="Refused" />Refused</span><br>
								</div>
							
						</div>
					</div>
					
				</div>
				<div class="clearBoth"></div>
				<div class="fLW100 borderDotted pB20">
					<div class="divHeadingCls bGHeading">
						<span class=" mL2 bold"> Sexual Risks:</span>
					</div>
					<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold"> Had sex with a male?</span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SWM" value="1" /> Yes</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SWM" value="2" /> No</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.SWM" value="5" /> Oral sex only</span>
										<span class="fieldHolder20"><form:radiobutton path="messageReturnVO.SWM" value="6" /> Unspecified type of sex</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.SWM" value="3" /> Did not ask</span>		
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SWM" value="4" /> Refused</span>
										
								 </span>
							</span>
						</div>
					<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold"> Had sex with a female?</span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SWF" value="1" /> Yes</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SWF" value="2" /> No</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.SWF" value="5" /> Oral sex only</span>
										<span class="fieldHolder20"><form:radiobutton path="messageReturnVO.SWF" value="6" /> Unspecified type of sex</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.SWF" value="3" /> Did not ask</span>		
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SWF" value="4" /> Refused</span>
										
								 </span>
							</span>
						</div>
					<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold"> Had sex with a transgender person?</span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SWT" value="1" /> Yes</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SWT" value="2" /> No</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.SWT" value="5" /> Oral sex only</span>
										<span class="fieldHolder20"><form:radiobutton path="messageReturnVO.SWT" value="6" /> Unspecified type of sex</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.SWT" value="3" /> Did not ask</span>		
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SWT" value="4" /> Refused</span>
										
								 </span>
							</span>
						</div>
					<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold"> Had sex with an anonymous person?</span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SWA" value="1" /> Yes</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SWA" value="2" /> No</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.SWA" value="5" /> Oral sex only</span>
										<span class="fieldHolder20"><form:radiobutton path="messageReturnVO.SWA" value="6" /> Unspecified type of sex</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.SWA" value="3" /> Did not ask</span>		
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SWA" value="4" /> Refused</span>
										
								 </span>
							</span>
						</div>
					<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold"> Had sex without a condom?</span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SEX_WOUT_CONDOM" value="1" /> Yes</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SEX_WOUT_CONDOM" value="2" /> No</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.SEX_WOUT_CONDOM" value="5" /> Oral sex only</span>
										<span class="fieldHolder20"><form:radiobutton path="messageReturnVO.SEX_WOUT_CONDOM" value="6" /> Unspecified type of sex</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.SEX_WOUT_CONDOM" value="3" /> Did not ask</span>		
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SEX_WOUT_CONDOM" value="4" /> Refused</span>
										
								 </span>
							</span>
						</div>
					<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold"> Had sex while intoxicated?</span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SEX_WHILE_INTOX" value="1" /> Yes</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SEX_WHILE_INTOX" value="2" /> No</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.SEX_WHILE_INTOX" value="5" /> Oral sex only</span>
										<span class="fieldHolder20"><form:radiobutton path="messageReturnVO.SEX_WHILE_INTOX" value="6" /> Unspecified type of sex</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.SEX_WHILE_INTOX" value="3" /> Did not ask</span>		
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SEX_WHILE_INTOX" value="4" /> Refused</span>
										
								 </span>
							</span>
						</div>
					<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold"> Exchanged drugs/money for Sex?</span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.EXCHANGE_SEX" value="1" /> Yes</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.EXCHANGE_SEX" value="2" /> No</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.EXCHANGE_SEX" value="5" /> Oral sex only</span>
										<span class="fieldHolder20"><form:radiobutton path="messageReturnVO.EXCHANGE_SEX" value="6" /> Unspecified type of sex</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.EXCHANGE_SEX" value="3" /> Did not ask</span>		
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.EXCHANGE_SEX" value="4" /> Refused</span>
										
								 </span>
							</span>
						</div>
					<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold"> [Females only] Had sex with a person who is known to her to be an MSM?</span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.FEMALE_SEX_MSM" value="1" /> Yes</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.FEMALE_SEX_MSM" value="2" /> No</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.FEMALE_SEX_MSM" value="5" /> Oral sex only</span>
										<span class="fieldHolder20"><form:radiobutton path="messageReturnVO.FEMALE_SEX_MSM" value="6" /> Unspecified type of sex</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.FEMALE_SEX_MSM" value="3" /> Did not ask</span>		
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.FEMALE_SEX_MSM" value="4" /> Refused</span>
								 </span>
							</span>
						</div>
					<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold">  Had sex with a person who is known to him/her to be an IDU?</span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SEX_IDU" value="1" /> Yes</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SEX_IDU" value="2" /> No</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.SEX_IDU" value="5" /> Oral sex only</span>
										<span class="fieldHolder20"><form:radiobutton path="messageReturnVO.SEX_IDU" value="6" /> Unspecified type of sex</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.SEX_IDU" value="3" /> Did not ask</span>		
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SEX_IDU" value="4" /> Refused</span>
								 </span>
							</span>
						</div>
				</div>
				<div class="clearBoth"></div>
				<div class="width100perc borderDotted pB20">
					<div class="divHeadingCls bGHeading">
						<span class=" mL2 bold"> Drug risks:</span>
					</div>
					<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold"> Been incarcerated</span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.INCARCERATED" value="1" /> Yes</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.INCARCERATED" value="2" /> No</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.INCARCERATED" value="4" /> Refused</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.INCARCERATED" value="3" /> Did not ask</span>
								 </span>
							</span>
						</div>
					<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold">  Shared drug paraphenalia </span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SHARED_DRUG_EQUIP" value="1" /> Yes</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SHARED_DRUG_EQUIP" value="2" /> No</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.SHARED_DRUG_EQUIP" value="4" /> Refused</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.SHARED_DRUG_EQUIP" value="3" /> Did not ask</span>
								 </span>
							</span>
						</div>
					<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold">   Engaged in injection drug use? </span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.IDU" value="1" /> Yes</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.IDU" value="2" /> No</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.IDU" value="4" /> Refused</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.IDU" value="3" /> Did not ask</span>
								 </span>
							</span>
						</div>
						
						<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold">   Other Risk </span>
								<span class="fieldHolder75"> 
										<span class=""><form:input path="messageReturnVO.OTHER_RISK" cssClass="standardInputSmallText" title="Other Risk" /> </span>
										
								 </span>
							</span>
						</div>
				
				
				</div>	
				
				
				<div class="clearBoth"></div>
				<div class="width100perc">
					<div class="divHeadingCls bGHeading">
						<span class=" mL2 bold"> Diagnosis Information:</span>
					</div>
					<div class="borderDotted">
						<div class="pLRTB15">
								<span class="fLW100 ">
									<span class="fieldHolder25 bold"> HIV Positive</span>
									<span class="fieldHolder75"> 
											<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.X900POSITIVE" value="1" /> Yes</span>
											<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.X900POSITIVE" value="2" /> No</span>
											<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.X900POSITIVE" value="9" /> Unknown</span>
									 </span>
								</span>
							</div>
							
						<div class="pLRTB15">
								<span class="fLW100 ">
									<span class="fieldHolder25 bold"> Diagnosis Date</span>
									<span class="fieldHolder75"> 
											<span class=""><form:input path="messageReturnVO.X900DXDATE" cssClass="standardInputSmallText" title="X900DXDATE" /> </span>
									 </span>
								</span>
							</div>
							
							
							
						<div class="pLRTB15">
								<span class="fLW100 ">
									<span class="fieldHolder25 bold">  Diagnosis Location </span>
									<span class="fieldHolder75"> 
											<span class="">
											<form:select path="messageReturnVO.DIAGNOSISLOCATION" cssClass="standardInputMedText" >
												<form:option value="" ></form:option> 
												<c:forEach items="${codedSexAtBirthValues}" var="columnData">
														<form:option value="${columnData.LOOKUP_CD}" label="${columnData.LOOKUP_NAME}" name="${columnData.LOOKUP_NAME}"></form:option>
												</c:forEach>
											</form:select></span>
									 </span>
								</span>
							</div>
						<div class="pLRTB15 marginBottom20" >
								<span class="fLW100 ">
									<span class="fieldHolder25 bold">   Diagnosis Loc Outside US </span>
									<span class="fieldHolder75"> 
											<form:input path="messageReturnVO.DIAGNOSISLOCOUTSIDEUS" cssClass="standardInputText" title="DIAGNOSISLOCOUTSIDEUS" />
									 </span>
								</span>
							</div>
					</div>
					<div class="borderDotted">
						
						<div class="pLRTB15 ">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold">   Initial Med Appointment </span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.INITMEDAPPT" value="1" /> Yes</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.INITMEDAPPT" value="2" /> No</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.INITMEDAPPT" value="3" /> Refused</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.INITMEDAPPT" value="4" /> Did not ask</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.INITMEDAPPT" value="9" /> Unknown</span>
								 </span>
							</span>
						</div>
						
						<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold">   Appointment Date  </span>
								<span class="fieldHolder75"> 
										<form:input path="messageReturnVO.DATEMEDAPPT" cssClass="standardInputSmallText" title="DATEMEDAPPT " />
								 </span>
							</span>
						</div>
						
						<div class="pLRTB15 marginBottom20">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold">   Attended Appointment  </span>
								<span class="fieldHolder75"> 
										<form:input path="messageReturnVO.ATTENDEDMEDAPPT" cssClass="standardInputSmallText" title="ATTENDEDMEDAPPT" />
								 </span>
							</span>
						</div>
					</div>
					<div class="borderDotted">
						<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold">   Test Technology </span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.TESTTECHNOLOGY" value="1" /> Not Offered</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.TESTTECHNOLOGY" value="2" /> Refused</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.TESTTECHNOLOGY" value="3" /> Completed</span>
								 </span>
							</span>
						</div>
						<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold">   900 Test </span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.NINEHUNDREDTEST" value="1" /> Yes</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.NINEHUNDREDTEST" value="2" /> No</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.NINEHUNDREDTEST" value="3" /> Refused</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.NINEHUNDREDTEST" value="4" /> Did not ask</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.NINEHUNDREDTEST" value="9" /> Unknown</span>
								 </span>
							</span>
						</div>
						<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold">   Current 900 Test Date  </span>
								<span class="fieldHolder75"> 
										<form:input path="messageReturnVO.CURRENT900TESTDATE" cssClass="standardInputSmallText" title="CURRENT900TESTDATE" />
								 </span>
							</span>
						</div>
						<div class="pLRTB15 marginBottom20">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold">   Current 900 Test Result </span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.CURRENT900TESTRESULT" value="1" /> Positive</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.CURRENT900TESTRESULT" value="2" /> Negative</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.CURRENT900TESTRESULT" value="3" /> Indeterminate</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.CURRENT900TESTRESULT" value="4" /> Declined</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.CURRENT900TESTRESULT" value="5" /> Do not know</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.CURRENT900TESTRESULT" value="6" /> Not Asked</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.CURRENT900TESTRESULT" value="7" /> Rapid reactive only</span>
								 </span>
							</span>
						</div>
					</div>
					<div class="borderDotted">
						<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold">   PREP Referral </span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.PREPREFERRAL" value="1" /> Yes</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.PREPREFERRAL" value="2" /> No</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.PREPREFERRAL" value="3" /> Unknown</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.PREPREFERRAL" value="4" /> Refused</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.PREPREFERRAL" value="5" /> Did not ask</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.PREPREFERRAL" value="9" /> Unknown</span>
								 </span>
							</span>
						</div>
						<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold">   Ever On PREP  </span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.EVERONPREP" value="1" /> Yes</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.EVERONPREP" value="2" /> No</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.EVERONPREP" value="3" /> Unknown</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.EVERONPREP" value="4" /> Refused</span>
										<span class="fieldHolder10"><form:radiobutton path="messageReturnVO.EVERONPREP" value="5" /> Did not ask</span>
										<span class="fieldHolder15"><form:radiobutton path="messageReturnVO.EVERONPREP" value="9" /> Unknown</span>
								 </span>
							</span>
						</div>
						<div class="pLRTB15">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold">  Current On PREP </span>
								<span class="fieldHolder75"> 
										<span class="">
											<form:select path="messageReturnVO.CURRENTONPREP" cssClass="standardInputText" >
												<form:option value="" ></form:option> 
												<c:forEach items="${currentPrepList}" var="columnData">
														<form:option value="${columnData.LOOKUP_CD}" label="${columnData.LOOKUP_NAME}" name="${columnData.LOOKUP_NAME}"></form:option>
												</c:forEach>
											</form:select>
										</span>
								 </span>
							</span>
						</div>
						<div class="pLRTB15 marginBottom20">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold">   Referral PREP Info  </span>
								<span class="fieldHolder75"> 
										<span class="fieldHolder33"><form:radiobutton path="messageReturnVO.REFERRALPREPINFO" value="1" /> Active, Client info given to PrEP provider/navigator</span>
										<span class="fieldHolder33"><form:radiobutton path="messageReturnVO.REFERRALPREPINFO" value="2" /> Passive, PrEP provider/navigator info given to client</span>
										<span class="fieldHolder33"><form:radiobutton path="messageReturnVO.REFERRALPREPINFO" value="4" /> Refused</span>
										
								 </span>
							</span>
						</div>
						
				</div>
				
				<div class="">
						<div class="pT10  pB10">
							<span class="fLW100 ">
								<span class="fieldHolder25 bold mL15">   Field Note </span>
								<span class="fieldHolder75 mL15"> 
										<form:textarea title="Field Notes" maxlength="4000" cols="165" rows="8" path="messageReturnVO.FIELDNOTE" style="width:65%; height:67px; border:1px solid gray;" class="roundedBorder"></form:textarea>
								 </span>
							</span>
						</div>
				</div>
				
				</div>
				
				
				
				
				
				
				
				
			</div>
			
			
	