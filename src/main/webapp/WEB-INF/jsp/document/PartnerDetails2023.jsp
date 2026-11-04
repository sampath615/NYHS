<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set  var="partnerIndex" value="${param.partnerIndex}" />
<h3 style="padding:1em" class="partnerAccordion${currentPartnerIndex} partnerList_${currentPartnerIndex}_accordion"><span class="accordionHeader" style="left: 2em;top: 50%;margin-top: -8px;position: absolute;">Partner Document(s)</span>	</h3>
<div id="partnerList_${currentPartnerIndex}_accordion" class="partnerAccordion${currentPartnerIndex} partnerDetailsContent" style="background-color: transparent; border:1px dotted;" class="container">

	
	<form:hidden path="partnerList[${currentPartnerIndex}].partnerId" />
	<form:hidden path="partnerList[${currentPartnerIndex}].documentId" />
	<form:hidden path="partnerList[${currentPartnerIndex}].createId"/>
	<form:hidden path="partnerList[${currentPartnerIndex}].createDate"/>
	<button value="Submit" type="submit" onclick="confirmDeleteAccordion('partnerAccordion<c:out value='${currentPartnerIndex}' />');return false;" class="editableFields styled-button floatRight actionIcon mR10">Delete</button>
							
	<div class="clearBoth" ></div>			
	<!--  Person details -->
	<p class="mL10 mR10">New York State Public Health Law (63.8) requires providers to talk to individuals diagnosed with HIV about partner notification and report such patients sex and/or needle sharing
							partner(s) to the health department. Ask all individuals diagnosed with HIV about their partner(s) and inform them that you will provide the information to the health department. The health
							department will NOT reveal any of the patient's information to the partner(s). Department of Health (DOH) will contact the reporting provider before providing partner services. 
							<b>The following information will help DOH staff identify and contact the partner where follow-up is needed.</b></p>
	<div id="partnerFormNew<c:out value='${currentPartnerIndex}' />">
		<div class="partnerFormAMain"  style="height:390px">
			<div id="personHIVDetailsLeft<c:out value='${currentPartnerIndex}' />" class="personHIVDetailsLeft">
				<div class="pL30 pT10">
					<span class="personLabelDoc4 bold">1.Name </span>
					<span> 
						<span ><form:input path="partnerList[${currentPartnerIndex}].lname" id="partnerList_${currentPartnerIndex}_lname" title="Last name"  class="standardInputSmallText mL10"  maxlength="25"/></span> 
						<span ><form:input path="partnerList[${currentPartnerIndex}].fname" title="First name" class="standardInputSmallText"  maxlength="20"/></span>
						<span ><form:input path="partnerList[${currentPartnerIndex}].mname" title="Middle name" class="standardInputSmallText"  maxlength="25"/></span>
						<br>
						<span class="labelunderneath4"> Last</span> 
						<span class="labelunderneath4"> First</span>
						<span class="labelunderneath4"> Middle</span>
					</span>
					<div class="v_error partnerList_${currentPartnerIndex}_lname "></div>
				</div>
				<div class="pL30 pT10">
					<span class="personLabelDoc4 bold">2.Alias</span>
					<span> 
						<span ><form:input path="partnerList[${currentPartnerIndex}].aliasLname" id="partnerList_${currentPartnerIndex}_aliasLname" title="Alias Last name" class="standardInputSmallText mL10"  maxlength="25"/></span> 
						<span ><form:input path="partnerList[${currentPartnerIndex}].aliasFname" title="Alias First name" class="standardInputSmallText"  maxlength="20"/></span>
						<span ><form:input path="partnerList[${currentPartnerIndex}].aliasMname" title="Alias Middle name" class="standardInputSmallText"  maxlength="25"/></span>
						<br>
						<span class="labelunderneath4"> Last</span> 
						<span class="labelunderneath4"> First</span>
						<span class="labelunderneath4"> Middle</span>
					</span>
					<div class="v_error partnerList_${currentPartnerIndex}_aliasLname "></div>
				</div>
				<div class="b1" id="partnerAddress<c:out value='${currentPartnerIndex}' />">
					<div class="pL30 pT10">
						<span class="bold">3.Address</span>
						<span ><form:input path="partnerList[${currentPartnerIndex}].address" title="Address" class="standardInputText newAddress widgetAddressFieldsReadonly "  maxlength="62"/></span>
						<span style="float: right;" class="mr5">
							<button value="Edit" onclick="openAddressWidgetFromPRFDocument('partnerAddress<c:out value='${currentPartnerIndex}' />');return false;" class="displayNone editAddress">Edit</button>
						 </span>
					</div>
					<div class="pL30 pT10">
						<span class="bold">City</span>
						<span class="pL10"><form:input path="partnerList[${currentPartnerIndex}].city" title="City" class="oAdrDlg standardInputSmallText1 newCity widgetAddressFieldsReadonly"  maxlength="30"/></span>
						<span class="pL10 bold">State</span>
						<span class="pL10">
						
							<form:select path="partnerList[${currentPartnerIndex}].state" title="State" class="oAdrDlg standardInputSmallText1 newState widgetAddressFieldsReadonly"  >
									<form:option value=""></form:option>
									<form:options items="${sessionScope.stateMap}"/>
							</form:select>
						
						 </span>
						<span class="pL10 bold"> Zip </span>
						<span class="pL10"><form:input path="partnerList[${currentPartnerIndex}].zip" id="partnerList_${currentPartnerIndex}_zip"  title="Zip" class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5"/></span>
						
						<span class="pL10 bold"> County </span>
						<span class="pL10">
							<form:select path="partnerList[${currentPartnerIndex}].county" title="County" class="oAdrDlg nOnly standardInputSmallText1 newCounty widgetAddressFieldsReadonly">
								<form:option value=""></form:option>
								<form:options items="${sessionScope.countyMap}"/>
							</form:select>
						</span>
						
					</div>
					<div class="v_error partnerList_${currentPartnerIndex}_zip"></div>
				</div>		
				<div class="pL30 pT10">
					<span class="bold">4.E-mail Address</span>
					<span class="pL30"><form:input path="partnerList[${currentPartnerIndex}].email" id="partnerList_${currentPartnerIndex}_email" title="Email Address"  class="standardInputText"  maxlength="50"/></span>
					<div class="v_error partnerList_${currentPartnerIndex}_email"></div>	
				</div>			
<!-- 				<div class="pL30 pT10"> -->
<!-- 					<span class="">Home Phone</span> -->
<%-- 					<span class="pL10"><form:input path="partnerList[${currentPartnerIndex}].homePhone" title="Home Phone" class="pNumber standardInputSmallText"  maxlength="20"/></span> --%>
<!-- 					<span class="pL30">Cell Phone</span> -->
<%-- 					<span class="pL10"> <form:input path="partnerList[${currentPartnerIndex}].cellPhone"  title="Cell Phone" style="width:136px" class="pNumber standardInputSmallText"  maxlength="20"/></span>												 --%>
<!-- 					<br> -->
<!-- 				</div> -->

				<div class="pL30 pT10">
					<span class="bold">5.Phone #</span>
					<span class="pL10"><form:input id="partnerList${currentPartnerIndex}_telephone1" path="partnerList[${currentPartnerIndex}].telephone1" title="Phone" class="pNumber standardInputSmallText"  maxlength="20"/></span>
					<span class="pL20">
						<c:forEach items="${codedTelephoneTypeValues}" var="telephoneType">
								<form:radiobutton path="partnerList[${currentPartnerIndex}].telephoneNo1Type1"  title="${telephoneType.ANS_DESC}" value="${telephoneType.ANS_CODE}"  />${telephoneType.ANS_DESC}
						</c:forEach>
						
					</span>
					<div class="v_error partnerList${currentPartnerIndex}_telephone1"></div>											
					<br>
				</div>
				<div class="pL30 pT10">
					<span class="bold">Phone #</span>
					<span class="pL10"><form:input id="partnerList${currentPartnerIndex}_telephone2" path="partnerList[${currentPartnerIndex}].telephone2" title="Phone" class="pNumber standardInputSmallText"  maxlength="20"/></span>
					<span class="pL20">
						<c:forEach items="${codedTelephoneTypeValues}" var="telephoneType">
								<form:radiobutton path="partnerList[${currentPartnerIndex}].telephoneNo2Type2"  title="${telephoneType.ANS_DESC}" value="${telephoneType.ANS_CODE}" />${telephoneType.ANS_DESC}
						</c:forEach>
					</span>
					<div class="v_error partnerList${currentPartnerIndex}_telephone2"></div>												
					<br>
				</div>
				
				<div class="pL30 pT10">
					<div><span class="bold">6.Electronic social network contact information:</span></div>
					<div class="pT8 ">
						<span>Website/mobile application:</span>
						<span class="pL10"><form:input path="partnerList[${currentPartnerIndex}].websiteMobileApp1" title="Website/mobile application" class="standardInputSmallText"  maxlength="100"/></span>
					</div>
					<div class="pT8">
						<span>Screen/User Name:</span>
						<span class="pL10"><form:input path="partnerList[${currentPartnerIndex}].websiteMobileApp1Username" title="Screen/User Name" class="standardInputSmallText"  maxlength="20"/></span>
					</div>
					
					<div class="pT8">
						<span>Website/mobile application:</span>
						<span class="pL10"><form:input path="partnerList[${currentPartnerIndex}].websiteMobileApp2" title="Website/mobile application" class="standardInputSmallText"  maxlength="100"/></span>
					</div>
					<div class="pT8">
						<span>Screen/User Name:</span>
						<span class="pL10"><form:input path="partnerList[${currentPartnerIndex}].websiteMobileApp2Username" title="Screen/User Name" class="standardInputSmallText"  maxlength="20"/></span>
					</div>
				</div>
				
				<div class="pL30 pT10">
					<span class="bold">7.DOB</span>
					<span class="pL10"> <form:input path="partnerList[${currentPartnerIndex}].dobText" title="Date of Birth" id="partnerList_${currentPartnerIndex}_dobText" style="width:136px" class="dPicker mask dobText standardInputSmallText" datevalidations="completeDate"  maxlength="10"/></span>
					<span class="pL30 bold">or Estimated Age</span>
					<span class="pL30"><form:input path="partnerList[${currentPartnerIndex}].age" id="partnerList_${currentPartnerIndex}_age" title="Age"  class="pNumber standardInputSmallText1 estimatedAge"  maxlength="3"/></span>
					<div class="v_error partnerList_${currentPartnerIndex}_dobText"></div>
					<div class="v_error partnerList_${currentPartnerIndex}_age"></div>	
					<br>
				</div>
				
				<div class="pL30 pT10">
					<span class="bold">8.Sex assigned at birth:</span>
					<span class="pL20">
						<form:radiobutton path="partnerList[${currentPartnerIndex}].sexAtBirth"  title="Male" value="M"/>Male
						<form:radiobutton path="partnerList[${currentPartnerIndex}].sexAtBirth"  title="Female" value="F"/>Female
						<form:radiobutton path="partnerList[${currentPartnerIndex}].sexAtBirth"  title="Unknown" value="U"/>Unknown
					</span>												
					<br>
				</div>
				
				<div class="pL30 pT10" id="partnerList_${currentPartnerIndex}_gender">	
					<div class="floatLeft pR10" style="border-right: dotted 1px;">
						<div class="bold">9.Current Gender identity: </div>
						<div  class="">
							<span> <form:radiobutton path="partnerList[${currentPartnerIndex}].gender" value="M" /> Man </span>
							<span> <form:radiobutton path="partnerList[${currentPartnerIndex}].gender" value="F" /> Woman </span>
							<span> <form:radiobutton path="partnerList[${currentPartnerIndex}].gender" value="Y" /> Transgender Man </span>
							<span> <form:radiobutton path="partnerList[${currentPartnerIndex}].gender" value="X" /> Transgender Woman </span>
							<span> <form:radiobutton path="partnerList[${currentPartnerIndex}].gender" value="G" /> X </span>
							<span> <form:radiobutton path="partnerList[${currentPartnerIndex}].gender" value="U" /> Unknown </span><br/>
							<span> <form:radiobutton path="partnerList[${currentPartnerIndex}].gender" value="A" /> Additional gender identity </span>
							<span class="mL10"></span><form:input path="partnerList[${currentPartnerIndex}].additionalPartGenderIdentity" class="standardInputMedText"  maxlength="50" title="Additional Gender identity" style="width:380px"/>
						</div>
					</div>
					<div class="v_error  partnerList_${currentPartnerIndex}_gender "></div>
					<br>
				</div>	
				
				 <br>
				 <br>
				 <br>						
			</div>
			<div id="personHIVDetailsRight<c:out value='${currentPartnerIndex}' />" class="personHIVDetailsRight">					
				
				
				
				
				<div class="pT10">
					<span class="fieldHolder50 bold">
						10.Is partner the patient's spouse?
					</span>
					<span class="fieldHolder50">
						<span> <form:radiobutton path="partnerList[${currentPartnerIndex}].spouse" value="Y" /> Yes </span>
						<span> <form:radiobutton path="partnerList[${currentPartnerIndex}].spouse" value="N" /> No </span>
					</span> <br>
				</div>
				 <br>
				<div class="clear ">
					<span class="fieldHolder50 bold">
						11.Domestic violence screen done?
					</span>
					<span class="fieldHolder50">
						<span> <form:radiobutton path="partnerList[${currentPartnerIndex}].dvScreen" class="dvScreen" value="Y" onclick="checkdvScreenSection(this)" dependentField="partnerList_${currentPartnerIndex}_dvRisk"/> Yes </span>
						<span> <form:radiobutton path="partnerList[${currentPartnerIndex}].dvScreen" class="dvScreen" value="N" onclick="checkdvScreenSection(this)" dependentField="partnerList_${currentPartnerIndex}_dvRisk"/> No </span>
					</span>
				</div>
				 <div  class="clear ">
					<span class="fieldHolder50">
						<span class="mL20">If YES, risk of domestic violence identified?</span>
					</span>
					<span class="fieldHolder50">
						<c:choose>
							<c:when test="${stdPRFDocVO.partnerList[currentPartnerIndex].dvScreen == 'Y'}">
								<span> <form:radiobutton path="partnerList[${currentPartnerIndex}].dvRisk" value="Y" /> Yes </span>
								<span> <form:radiobutton path="partnerList[${currentPartnerIndex}].dvRisk" value="N" /> No </span>	
							</c:when>
							<c:otherwise>
								<span> <form:radiobutton path="partnerList[${currentPartnerIndex}].dvRisk" class="partnerList_${currentPartnerIndex}_dvRisk readOnlyDisabled" value="Y" /> Yes </span>
								<span> <form:radiobutton path="partnerList[${currentPartnerIndex}].dvRisk" class="partnerList_${currentPartnerIndex}_dvRisk readOnlyDisabled" value="N" /> No </span>
							</c:otherwise>
						</c:choose>
						
					</span> 
					<br>
					<div class="v_error  partnerList_${currentPartnerIndex}_dvRisk "></div>
				</div>
				 <br>
				 <div class="clear " style="position:relative;">
					<span class="bold">
						12.Partner Assistance Status
					</span>
					<span class="pL10">
						<span> 
						
						<form:select path="partnerList[${currentPartnerIndex}].partnerAssistanceStatus" title="Partner Assistance status" id="partnerAssistanceStatus" class="standardInputSmallText">
							<form:option value=""></form:option>
							<c:forEach items="${partnerAssistanceStatusList}" var="partnerAssistanceStatus">
								<form:option value="${partnerAssistanceStatus.PROV_STATUS_CODE}">${partnerAssistanceStatus.PROV_STATUS_DESC}</form:option>
							</c:forEach>
						</form:select> 
						</span>
					</span> 
					<span style="position:absolute;" class="informationMarkTarget">
						<img src="${staticPath}/images/icons/information.png" class="informationMark" alt="Question Mark" style="width: 16px; height: 16px; vertical-align: top;">
						
					</span>
					
					<span class="pL30 bold">
						13.Date Notified
					</span>
					<span class="pL30">
						<span> <form:input path="partnerList[${currentPartnerIndex}].partnerNotifiedDate" id="partnerList_${currentPartnerIndex}_partnerNotifiedDate" title="Date notified" class="dPicker mask dateNotified standardInputSmallText1" datevalidations="partialDateForAll"  maxlength="10"/> </span>
						
					</span>
					<div class=" v_error partnerList_${currentPartnerIndex}_partnerNotifiedDate"></div> 
				</div>
				
				
				
				<div>
					<span class="floatLeft bold" style="margin-top:3px">
						14.HIV Status of Partner:
					</span>
					<span class="pL10 fieldHolder50">
						<span> <form:radiobutton path="partnerList[${currentPartnerIndex}].partnerStatus" value="P" /> Positive </span>
						<span> <form:radiobutton path="partnerList[${currentPartnerIndex}].partnerStatus" value="N" /> Negative </span>
						<span> <form:radiobutton path="partnerList[${currentPartnerIndex}].partnerStatus" value="U" /> Unknown </span>
					</span> <br>
				</div>
				 <br>
				<div>
					<div class="fieldHolder50">
						<span class="bold">(Patient/partner's self-report OK) </span>
						<span class="pL30"> Date tested </span>
					</div>
					<div class="fieldHolder50">
						<span> <form:input path="partnerList[${currentPartnerIndex}].partnerTestDate" id="partnerList_${currentPartnerIndex}_partnerTestDate" title="Date tested" class="dPicker mask dateTested standardInputSmallText" datevalidations="partialDateForAll" maxlength="10"/> </span>
						
					</div> 
					<div class="v_error partnerList_${currentPartnerIndex}_partnerTestDate"></div>
				</div>
				<br>
				<div id="raceEthnicity">
					<span class="container fieldHolder50 " >
						<ul>
							<li>
								<div class="bold">
									15.Race 
								</div>
								<div>
									<form:checkbox path="partnerList[${currentPartnerIndex}].raceIndian" value="true" /> American Indian/Alaskan Native
								</div>		
								<div>
									<form:checkbox path="partnerList[${currentPartnerIndex}].raceAsian" value="true" /> Asian
								</div>
								<div>
									<form:checkbox path="partnerList[${currentPartnerIndex}].raceBlack" value="true" /> Black/African American
								</div>
								<div>
									<form:checkbox path="partnerList[${currentPartnerIndex}].racePacific" value="true" /> Native Hawaiian/Other Pacific Islander
								</div> 
								<div>
									<form:checkbox path="partnerList[${currentPartnerIndex}].raceWhite" value="true" /> White	
								</div>
								<div>
									<form:checkbox path="partnerList[${currentPartnerIndex}].raceOther" value="true" class="otherFieldDependentCBox" dependentField="partnerList_${currentPartnerIndex}_raceOtherText" /> Other 
									<form:input path="partnerList[${currentPartnerIndex}].raceOtherText" id="partnerList_${currentPartnerIndex}_raceOtherText" title="Race other text" class="standardInputSmallText2"  maxlength="20"/>
								</div>
								<div id="partnerList_${currentPartnerIndex}_raceUnknown">
									<form:checkbox path="partnerList[${currentPartnerIndex}].raceUnknown" value="true" /> Unknown
								</div>
								<div class="v_error  partnerList_${currentPartnerIndex}_raceOtherText "></div>
								<div class="v_error  partnerList_${currentPartnerIndex}_raceUnknown "></div>	
							</li>
						</ul>		
					</span>	
					<span class="fieldHolder50 borderLD" style="width:49%">
						<ul>
							<li>
								<div>
									<div class="bold">16. Ethnicity (Check one) </br> Hispanic or Latino<br></div>
									<div>
										<form:radiobutton path="partnerList[${currentPartnerIndex}].ethnicity" value="01" /> Yes <br>
										<form:radiobutton path="partnerList[${currentPartnerIndex}].ethnicity" value="02" /> No <br>
										<form:radiobutton path="partnerList[${currentPartnerIndex}].ethnicity" value="09" /> Unknown <br>
									</div>
								</div>
							</li>
						</ul>
					</span>
				</div>
				<br>
				<div class="clear">						
					<span class="bold">17.Physical Description</span>
					<span class="pL10"><form:input path="partnerList[${currentPartnerIndex}].physicalDescription" title="Physical description" class="standardInputSmallText"  maxlength="500"/></span> 
				</div>
				<div class="clear pT10">						
					<span class="bold">18.Height</span>
					<span class="pL10"><form:input path="partnerList[${currentPartnerIndex}].height" title="Height" class="standardInputSmallText1"  maxlength="15"/></span> 
					<span class="pL10 bold">Weight</span>
					<span class="pL10"><form:input path="partnerList[${currentPartnerIndex}].weight" title="Weight" class="standardInputSmallText1"  maxlength="20"/></span> 
				</div>
				<br>	
				<div>
					<span class="riskLabelField4 bold">
						19.Place of employment
					</span>
					<span class="">
						<span> <form:input path="partnerList[${currentPartnerIndex}].placeOfEmployment" title="Place of employment" class="standardInputText"  maxlength="30" /> </span>
					</span> <br>
				</div>
				<br>
				<div class="">
					<span class="bold">Work Hours</span>
					<span class="pL30" ><form:input path="partnerList[${currentPartnerIndex}].workHours" title="Work hours" class="standardInputSmallText2"  maxlength="25"/></span> 
					<span class="pL30 bold">Work Phone</span>
					<span class="pL30">
						<form:input path="partnerList[${currentPartnerIndex}].workPhone" title="Work phone" class="standardInputSmallText2"  maxlength="30"/> - <form:input path="partnerList[${currentPartnerIndex}].workPhoneExt" class="mL10 standardInputSmallText2"  maxlength="5"/>
					</span> 
				</div>
				<br>
				<div class="">
					<div class="floatLeft">
						<span class="bold">20.Marital Status</span>
						<span class="pL30" >
							
							<form:select path="partnerList[${currentPartnerIndex}].maritalStatus" title="Marital Status"  class="standardInputSmallText">
								<form:option value=""></form:option>
								<c:forEach items="${codedMaritalStatusList}" var="maritalStatus">
										<form:option value="${maritalStatus.MARITAL_STATUS_CODE}">${maritalStatus.MARITAL_STATUS_DESC}</form:option>
									</c:forEach>
							</form:select>
													
						</span>
					</div> 
					<div class="floatLeft">
						<span class="pL30 bold">Pregnant?</span>
						<span class="pL30">
							<span> 
								<c:forEach items="${codedYesNoUnkownValues}" var="codedValue">
										<form:radiobutton path="partnerList[${currentPartnerIndex}].pregnant"  value="${codedValue.ANS_CODE}" title="Pregnant"/> ${codedValue.ANS_DESC}
								</c:forEach>
							</span>
						</span>
						<div class="v_error  partnerList_${currentPartnerIndex}_pregnant "></div>
					</div>
				</div>
				
				<br>
			</div>
		</div>
	</div>			
	<input type="hidden" id="tempDelete${pCount.index}" name="stdPRFPartnerVOList[${pCount.index}].tempDelete" value="false" />
		
</div>	

