<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<script src="${staticPath}/js/docs/prfDoc.js?v=${buildDate}"></script>
<link href="${staticPath}/css/docs/prfForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<form:form modelAttribute="stdPRFDocVO" id="stdPRFDocVO" cssClass="documentFormVO documentDescription">		

<form:errors path="*" cssClass="errorblock" element="div" />

	<div id="prfDocTab" style="background-color: transparent">		
		<div style="padding-left:43%; " class="headingCls">
				PRF DOCUMENT
		</div>
		<div style="padding-bottom:10px; text-align: right" class="docViewerFields">
            <span class="mL10 bold">Document ID: </span>
			<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
        </div>
        <form:hidden path="PRF_SRC_ID"/>
        <form:hidden path="ADDRESS_MAPPING_ID"/>
        <form:hidden path="PROV_FAC_TYPE_CODE"/>
        <form:hidden path="EHARS_DOCUMENT_SOURCE_CD"/>
        <form:hidden path="CREATE_UID"/>
        <fmt:formatDate value="${stdPRFDocVO.CREATE_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedCreateDate" />
        <form:hidden path="CREATE_DATE_TEXT" value="${theFormattedCreateDate}"/>
        
		<div class="container w100 nonRawEditFields pB10">
            <div style="float:left; width: 40%">
	            <div>
	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
                    <span class="ml10"><form:input path="STATENO" value="${STATENO}" class="standardInputSmallText" style="width:180px" maxlength="10" readonly="true"/> </span>
	            </div>    
            </div>
            <div style="float:left; width: 30%">
	         	<div>
	                <span class=" ml10">	eHARS Import Date: </span>
	                <fmt:formatDate value="${stdPRFDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
					 <span class=" ml10"><form:input path="EHARS_IMPORT_DATE_TEXT" value="${theFormattedDate}" class="standardInputSmallText ml10" readonly="true"/></span>
	            </div>   
    
            </div>
            <div style="float:left; width: 30%; text-align: right">
	            <div>
 	                <span class="mL10">NYC SHIP Date: </span>
 	                <fmt:formatDate value="${stdPRFDocVO.NYC_SHIP_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedNYC_SHIP_DATE_TEXT" ></fmt:formatDate>
 			 		<span class="mL10 "><form:input path="NYC_SHIP_DATE_TEXT" value="${theFormattedNYC_SHIP_DATE_TEXT}" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
 	            </div> 
	                           
            </div>     
   		</div>
   		
   		<jsp:include page="statenoDetails.jsp"></jsp:include>
   		 
	<!--	<div>
			<input type="radio" name="PRFDoc" class="PrfTTH" value="PrfTTH" checked>Edit PRF and TTH
			<input type="radio" name="PRFDoc" class="mL30 TTHonly" value="TTHonly">Edit TTH only
		</div>    -->
			
		<!--  Person details -->
		<div id="prfContent">
			<div class="container mT10" id="patientInformation">	 
				<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
					<span class=" mL2">I. Patient Information</span>
					<div style="float:right; width: 30%; text-align: right">
		            	<c:choose>
		            		<c:when test="${stdPRFDocVO.ENTRY_REVIEW_UID != null}">
		            			<span class="mL10 bold">Central Office Data Entry </span>
		            		 </c:when>
		            		 <c:otherwise>
		            		 	<span class="mL10 bold">Provider Data Entry </span>
		            		 </c:otherwise>
		            	</c:choose>
	 	            </div> 
				</div>
				<ol type="1">
					<li>
						<div class="personInfo">
							<span class="personLabelDoc">Name </span>
							<span> 
								<span class="pL150"><form:input path="LNAME" class="standardInputMedText required" maxlength="25" title="Patient Last name"/></span> 
								<span  class="pL30"><form:input path="FNAME" class="standardInputMedText required" maxlength="20" title="Patient First name"/></span>
								<span  class="pL30"><form:input path="MNAME" class="standardInputMedText " maxlength="25" title="Patient Middle name"/></span>
								<br>
								<span class="labelunderneath2 requiredInd" title="Required Field"> Last*</span>
								<span class="labelunderneath1 requiredInd" title="Required Field"> First*</span>
								<span class="labelunderneath1"> Middle</span>
								
							</span>
							<div class="v_error LNAME "></div>
							<div class="v_error FNAME "></div>
							<div class="v_error MNAME "></div>
						</div>
						<div class="personInfo">
							<span class="personLabelDoc">Alias or Maiden Name </span>
							<span> 
								<span class="pL45"><form:input path="ALT_LNAME" class="standardInputMedText" maxlength="25" title="Patient Alternate Last name"/></span>
								<span class="pL30"><form:input path="ALT_FNAME" class="standardInputMedText" maxlength="20" title="Patient Alternate First name"/></span>
								<span class="pL30"><form:input path="ALT_MNAME" class="standardInputMedText" maxlength="25" title="Patient Alternate Middle name" /></span> <br>
								<span class="labelunderneath2"> Last</span>
								<span class="labelunderneath1"> First</span>
								<span class="labelunderneath1"> Middle</span>
							</span>
							<div class="v_error ALT_LNAME "></div>
							<div class="v_error ALT_FNAME "></div>
							<div class="v_error ALT_MNAME "></div>
						</div>		
						<div class="b1" style="margin: 15px 5px 10px 5px">
			 	            <div class="personInfo pTRBL10" id="prfPersonAddressDetails">
			 	                <span class="personLabelDoc bold"> Address </span>
			 		                <span> 	                	
				 	                	<span class="pL10"><form:input path="PERSON_ADDRESS" class="standardInputText newAddress widgetFields widgetAddressFieldsReadonly" style="width:250px" maxlength="100" title="Patient Address"/>  </span>
				 	                    
				 	                    
				 	                    <span class="pL10 bold">City</span>
				 	                    <span class="pL10"><form:input path="PERSON_CITY" class="oAdrDlg standardInputSmallText newCity widgetFields widgetAddressFieldsReadonly" maxlength="30"  title="City"/></span>
				 	                    
				 	                    <span class="pL10 bold">State </span>
				 	                    <span  class="pL10">
											<form:select path="PERSON_STATE" cssClass=" standardInputSmallText4  widgetFields newState widgetAddressFieldsReadonly"  title="State">
													<form:option value=""></form:option>
													<form:options items="${sessionScope.stateMap}"/>
											</form:select>
										</span>
				 	                    
				 	                    <span class="pL10 bold">Zip </span>
				 	                    <span class="pL10"> 
				 	                        <form:input path="PERSON_ZIP" class="standardInputSmallText1 widgetFields newZip widgetAddressFieldsReadonly" maxlength="5" title="Zip"/> - <form:input path="PERSON_PLUS4" class="standardInputSmallText1" maxlength="4" title="Plus4"/>
				 	                    </span>
				 	                    <span class="pL10 bold">County </span>
				 		                   <span class="pL10"> 
												<form:select path="PERSON_COUNTY"  cssClass="standardInputSmallText widgetFields newCounty widgetAddressFieldsReadonly" title="County"> 
													<form:option value=""></form:option>
													<form:options items="${sessionScope.countyMap}"/>
												</form:select>
					 		               </span>
					 		               <span style="float: right;" class="mr5">
											<button value="Edit" onclick="openAddressWidgetFromPRFDocument('prfPersonAddressDetails');return false;" class="displayNone editAddress">Edit</button>
										 </span>       	                    
				 	                    <br>
				 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw nonRawEditFields"> (Std)</span>
				 	                </span>
			 	            </div>
			 	            <div class="v_error PERSON_ADDRESS pL10"></div>
			 	            <div class="v_error PERSON_CITY pL10"></div>
			 	            <div class="v_error PERSON_STATE pL10"></div>
			 	            <div class="v_error PERSON_ZIP pL10"></div>
			 	            <div class="v_error PERSON_COUNTY pL10"></div>
			 	            <div class="personInfo pT10 pTRBL10" >
			 	            	<span  class="nonRawEditFields" style="padding-left:59px"><form:input path="rawPersonAddress" class="standardInputText dispAddressStdRaw" style="width:250px" maxlength="100" title="Raw Address"/></span>
			 	              	 	                 
			 	                 <span class="pL10 bold"> Phone</span>
			 					 <span class="pL10"> <form:input path="PERSON_PHONE" class="pNumber standardInputSmallText" maxlength="20" title="Phone"/></span>
			 	                <br>               
			 	                <span class="labelunderneathAddress nonRawEditFields"> Street Address</span><span class="dispAddressStdRaw nonRawEditFields"> (Raw)</span>
			 	                    
			 	            </div>
			 	             <div class="v_error PERSON_PHONE pL10"></div>
			 	        </div>	
			 	        
			 	        <div class="personInfo pT10 clear ">
								<div class=" ">
									<span class=" bold">Address Type: </span>
									
									<c:forEach items="${codedAddressTypes}" var="addressType">
										<span  class=""><form:radiobutton path="PERSON_ADDRESS_TYPE" value="${addressType.ADDRESS_TYPE_CODE}" /> ${addressType.ADDRESS_TYPE_DESCRIPTION}</span>
									</c:forEach>
								</div>
						</div>
						
						<div class="personInfo pT10  clear ">
							<div class="" id="genderGroup">
								<span class=" bold">Gender </span>
								<span  class=""><form:radiobutton path="GENDER" value="M" /> Male</span> 
								<span><form:radiobutton path="GENDER" value="F" /> Female</span>
								<span  class=""><form:radiobutton path="GENDER" value="X" /> Transgender: male-to-female</span>		
								<span><form:radiobutton path="GENDER" value="Y" /> Transgender: female-to-male</span>
								<span><form:radiobutton path="GENDER" value="A" /> Additional gender identity</span>
								<span class="mL10"></span><form:input path="ADDITIONAL_GENDER_IDENTITY" class="standardInputMedText"  maxlength="50" title="Additional Gender identity" style="width:380px"/>
							</div> 
							<div class="v_error genderGroup "></div>							
							<div class="v_error ADDITIONAL_GENDER_IDENTITY "></div>
						</div>
						
						<div class="personInfo  clear pT10">
							<div class="fieldHolder33 " id="sexAtBirthGroup">
								<span class=" bold requiredInd" title="Required Field" >Sex assigned at birth:* </span>
								<c:forEach items="${codedSexAtBirthValues}" var="sexAtBirth">
										<span  class=""><form:radiobutton path="SEX_AT_BIRTH" value="${sexAtBirth.SEX_CODE}" requiredgroup="requiredgroup" title="Sex assigned at birth"/> ${sexAtBirth.SEX_DESCRIPTION}</span>
								</c:forEach>
								<div class="v_error sexAtBirthGroup "></div>
							</div>
							
							<div class="fieldHolder33 ">
								<span class="fLW35 bold requiredInd" title="Required Field" >Date of Birth* </span>
								<span  class="fLW60"><form:input path="DOB" datevalidations="completeDate" class="mask required dPicker standardInputSmallText"  maxlength="10" title="Date of Birth"/></span>
								<div class="v_error  DOB "></div>
							</div>
							<div class="fieldHolder33 ">
								<span class="fLW35 bold">Date of Death </span>
								<span class="fLW60"> 
									<form:input path="DOD_TEXT" datevalidations="completeDate" class="dPicker mask standardInputSmallText"  maxlength="10" title="Date of Death"/>
								</span>
								<div class="v_error  DOD_TEXT"></div>	
							</div>
							
							
							
							
							
						</div>
						
						
						
						<div class="personInfo pT10 clear">
							
							<div class="fieldHolder33 " id="currentlyPregnant">
								<span class=" bold">Currently pregnant? </span>
							
								<c:forEach items="${codedYesNoUnkownValues}" var="codedValue">
										<span  class=""><form:radiobutton path="PREGNANT"  value="${codedValue.ANS_CODE}" title="Currently Pregnant"/> ${codedValue.ANS_DESC}</span>
								</c:forEach>
								
								<div class="v_error currentlyPregnant"></div>								
							</div>	
							<div class="fieldHolder33">
								<span class="fLW35   bold">EDC </span>
								<span  class="fLW40"><form:input path="EDC"  class="dPicker mask standardInputSmallText " datevalidations="partialDateForAllorFuture" maxlength="20" title="EDC"/></span>
								<div class="v_error  EDC "></div>
							</div>	
							
							<div class="fieldHolder33">
								<span class="fLW35   bold">Date of this admission or visit </span>
								<span  class="fLW40"><form:input path="ADMISSION_DATE" class="dPicker mask standardInputSmallText " datevalidations="partialDateForAll" maxlength="10" title="Admission Date"/>&nbsp;&nbsp;</span>
								<div class="v_error  ADMISSION_DATE"></div>
							</div>	
							
			
						</div>
						
						
						<div class="personInfo  pT10 clear">
							<div class="fieldHolder33 ">
								<span class="fLW35  bold">Patient medical record number </span>
								<span class="fLW60"> 
									<form:input path="MED_RECORD_NUMBER" class="standardInputMedText"  maxlength="17" title="Patient Record number"/>
								</span>
							</div>
							<div class="fieldHolder33 ">
								<span class="fLW35 bold">NYSID (if inmate) </span>
								<span class="fLW60"> 
									<form:input path="NYSID" class="standardInputSmallText "  maxlength="9" title="NYSID"/>
								</span>
								<div style="color:gray">(Must be in ########A format)</div>
								<div class="v_error  NYSID "></div>
							</div>
							<div class="fieldHolder33 ">
								<span class="fLW35 bold">DIN (if inmate) </span>
								<span class="fLW60"> 
									<form:input path="DIN" class="standardInputSmallText "  maxlength="7" title="DIN"/>
								</span>
								<div style="color:gray">(Must be in ##A#### format)</div>
								<div class="v_error  DIN "></div>
							</div>
						</div>
						
						<div class="personInfo  pT10 clear">
							<div class="fieldHolder40">
								<span class="fLW20   bold">Other identifier </span>
								<span  class="fLW40"><form:input path="PERSON_OTHER_IDENTIFIER" class="standardInputSmallText " maxlength="50" title="Other identifier" style="width:380px"/>&nbsp;&nbsp;</span>
								<div class="v_error  PERSON_OTHER_IDENTIFIER "></div>
							</div>
						</div>
					</li>	
				</ol>
			</div>	
			<!--  Race details -->
			<div class="raceRiskReprt borderField">
				<div class="fieldHolder30" id="raceEthnicity">
					<div class="container raceHW" style="height:180px;width: 320px;" id="raceGroup">
						<ol start="2" type='1'>
							<li>
								<div class="bold">
									<span title="Required Field" class="requiredInd">Race*</span>
								</div>
								<div>
									<form:checkbox path="RACE_WHITE" value="true" title="Race"/> White	
								</div>
								<div>
									<form:checkbox path="RACE_BLACK" value="true" title="Race" /> Black/African American
								</div>
								<div>
									<form:checkbox path="RACE_ASIAN" value="true" title="Race" /> Asian
								</div>
								<div>
									<form:checkbox path="RACE_INDIAN" value="true" title="Race" /> American Indian/Alaskan Native
								</div>
								<div>
									<form:checkbox path="RACE_PACIFIC" value="true" title="Race"  /> Native Hawaiian/Other Pacific Islander
								</div> 
								<div>
									<form:checkbox path="RACE_OTHER" value="true" class="otherFieldDependentCBox" dependentField="RACE_OTHER_TEXT"   title="Race" /> Other 
									<form:input path="RACE_OTHER_TEXT" class="standardInputSmallText" style="width:220px" maxlength="30" title="Race other text" requiredField="Y"/>
								</div>
								<div id="RACE_UNKNOWN_SECTION">
									<form:checkbox path="RACE_UNKNOWN" value="true"  title="Race" /> Unknown
								</div>	
								<div class="v_error  raceGroup "></div>
								<div class="v_error  RACE_OTHER_TEXT "></div>
								<div class="v_error  RACE_UNKNOWN_SECTION "></div>
							</li>
						</ol>		
					</div>	
					<br>
					<div class="borderTD ethnicityHW">	
						<ol start="3" type='1'>
							<li>
								<div id="ethnicityGroup">
									<span class="bold requiredInd" title="Required Field" >Ethnicity*</span> (Check one) <br/> Hispanic or Latino<br>
									<form:radiobutton path="ETHNICITY" value="01"  requiredgroup="requiredgroup" title="Ethnicity"/> Yes
									<form:radiobutton path="ETHNICITY" value="02" requiredgroup="requiredgroup"  title="Ethnicity"/> No
									<form:radiobutton path="ETHNICITY" value="09"  requiredgroup="requiredgroup"  title="Ethnicity"/> Unknown
								</div>
								<div class="v_error  ethnicityGroup "></div>
							</li>
						</ol>
					</div>	
					<br>
					<div class="borderTD birthCountyHW">	
						<ol start="4" type='1'>
							<li>
								<div>
									<span title="Required Field" class="requiredInd bold">Country of birth*</span>
									<form:select path="BIRTH_COUNTRY" cssClass="standardInputSmallText required" title="Birth Country">
										<form:option value=""></form:option>
										<form:options items="${sessionScope.countryMap}"/>
										<form:option value="OTH">Other</form:option>
										<form:option value="UNK">Unknown</form:option>
									</form:select>
								</div>
								<div class="v_error  BIRTH_COUNTRY "></div>
								
							</li>
						</ol>
					</div>
					
					<div class="borderTD primaryLanguageHW">
						<ol start="5" type='1'>
							<li>
								<div class="bold">
									Primary Language
								</div>
								
								<c:forEach items="${codedPrimaryLanguageValues}" var="primaryLanguage">
									<div  class="">
										
										<form:radiobutton path="PRIMARY_LANGUAGE" value="${primaryLanguage.LANG_CODE}" /> ${primaryLanguage.LANG_DESC}
										<c:if test="${primaryLanguage.LANG_CODE == 'O'}">
											<form:input path="PRIMARY_LANGUAGE_OTHER" class="standardInputSmallText" style="width:220px" maxlength="50" title="Other Primary Language"/>
										</c:if>
									</div>
								</c:forEach>
								<div class="v_error  PRIMARY_LANGUAGE_OTHER "></div>
							</li>
						</ol>		
					</div>		
				</div>
				<div class="fieldHolder70">
				<!--  Risk details -->
					<div class="riskHW borderLD borderRD " id="PATIENT_RISK">
						<ol start="6" type='1'>
							<li>
								<div class="pT8">
									<span class="riskLabelField bold requiredInd" title="Required Field">Risk*</span> (Respond to each)<br/>
									<span>After 1977 and before the earliest known diagnosis of HIV infection, this patient had:</span>
								</div>
								
								<div class="pT8">
									<span class="riskLabelField ">&nbsp;</span>
									<span class="riskValueField">Yes</span>
									<span class="riskValueField">No</span>
									<span class="riskValueField">Unknown</span><br>
								</div>	
								<div class="pT8 clear">
									<span class="riskLabelField bold">Sex With Male</span>
									<span class="riskValueField"><form:radiobutton path="swm" value="Y" /></span>
									<span class="riskValueField"><form:radiobutton path="swm" value="N" /></span>
									<span class="riskValueField"><form:radiobutton path="swm" value="U" /></span><br>		
								</div>
								<div class="pT8 clear">
									<span class="riskLabelField bold">Sex With Female</span>
									<span class="riskValueField"><form:radiobutton path="swf" value="Y" /></span>
									<span class="riskValueField"><form:radiobutton path="swf" value="N" /></span>
									<span class="riskValueField"><form:radiobutton path="swf" value="U" /></span>
								</div>
								<div class="pT8 clear">
									<span class="riskLabelField bold">Injected non-prescription drugs </span>
						 			<span class="riskValueField "><form:radiobutton path="idu" value="Y" /></span>
									<span class="riskValueField "><form:radiobutton path="idu" value="N" /></span>
									<span class="riskValueField "><form:radiobutton path="idu" value="U" /></span> 
								</div>
								<div class="pT8 clear">
									<span class="riskLabelField  bold">Perinatal Exposure </span>
						 			<span class="riskValueField "><form:radiobutton path="perinatalExposure" value="Y" /></span>
									<span class="riskValueField "> <form:radiobutton path="perinatalExposure" value="N" /></span>	
									<span class="riskValueField "> <form:radiobutton path="perinatalExposure" value="U" /></span>		 
								</div>	
								<div class="pT8 clear">
									<span class="riskLabelField bold">Received blood products/transplant/ artificial insemination </span>
						 			<span class="riskValueField"><form:radiobutton path="rcvdTransfusion" value="Y" class="otherFieldDependentRadio" dependentField="RECV_BLOOD_PROD_DATE"/></span>
									<span class="riskValueField"><form:radiobutton path="rcvdTransfusion" value="N" class="otherFieldDependentRadio" dependentField="RECV_BLOOD_PROD_DATE"/></span>	
									<span class="riskValueField"><form:radiobutton path="rcvdTransfusion" value="U" class="otherFieldDependentRadio" dependentField="RECV_BLOOD_PROD_DATE"/></span>	 
								</div>	
								<br>
														
								<ul STYLE="CLEAR:BOTH">
									<li class="pT8 floatLeft">
										<span class="riskLabelField1 bold">Date </span>
										<span class="riskValueField1"><form:input path="RECV_BLOOD_PROD_DATE" requiredField="Y" class="dPicker mask standardInputSmallText" datevalidations="partialDateForAll" maxlength="10" title="Date"/></span>	<br>
										<span class="v_error RECV_BLOOD_PROD_DATE floatLeft"></span>
									</li>
								</ul><br>
								<div class="pT10 bold clear"> <u>Heterosexual sex with:</u> </div>
								<ul class="clear">
									<li class="pT8 pB15" id="HETERO_SEX_IDU">
										
											<span class="riskLabelField1">Intravenous/Injection Drug User </span>
								 			<span class="riskValueField1"><form:radiobutton path="sexIDU" class="HeteroYes" value="Y" /></span>
											<span class="riskValueField1"><form:radiobutton path="sexIDU" value="N" /></span>
											<span class="riskValueField1"><form:radiobutton path="sexIDU" value="U" /></span>
											<span class="v_error HETERO_SEX_IDU floatLeft"></span>										
									</li>
									<li class="pT8 clear" id="HETERO_SEX_BI">		
										
											<span class="riskLabelField1">Bisexual man </span>
								 			<span class="riskValueField1"><form:radiobutton path="sexBI" class="HeteroYes" value="Y" /></span>
											<span class="riskValueField1"> <form:radiobutton path="sexBI" value="N" /></span>	
											<span class="riskValueField1"> <form:radiobutton path="sexBI" value="U" /></span>
											<span class="v_error HETERO_SEX_BI floatLeft"></span>	 
										
									</li>
									<li class="pT8 clear" id="HETERO_SEX_AIDSHIV">	
										
											<span class="riskLabelField1">HIV+ person </span>
								 			<span class="riskValueField1"><form:radiobutton path="sexAIDSHIV" class="HeteroYes" value="Y" /></span>
											<span class="riskValueField1"> <form:radiobutton path="sexAIDSHIV" value="N" /></span>	
											<span class="riskValueField1"> <form:radiobutton path="sexAIDSHIV" value="U" /></span>		 
											<span class="v_error HETERO_SEX_AIDSHIV floatLeft"></span>
									</li>
								</ul>
								
								<div class="pT8">
									<span class="riskLabelField1 bold" style="width:25%">Other risk </span>
									<span class="riskValueField1"><form:input path="RISK_OTHER_TEXT"  class="standardInputSmallText" style="width:220px" maxlength="30" title="Risk other text"/></span>
								</div>		
							</li>
						</ol>
						<div class="v_error PATIENT_RISK"></div>
						
					</div>
					<div class="reportHW " id="TYPE_OF_REPORT_SECTION" style="width:450px">		
						<!--  Report details -->
						<ol start="7" type='1'>
							<li>
								<div class="pT8">
									<span class="riskLabelField bold requiredInd"  title="Required Field">Type of report*</span>
									<br/>
								</div>
							</li>
						</ol>
						<div class="pL30 v_error TYPE_OF_REPORT_SECTION mB10"></div>	
						<div class="pL30" id="typeOfReport">
							<form:checkbox path="INIT_HIV_DIAG" id="INIT_HIV_DIAG" value="true" maxlength="1" title="Type of report" class="typeOfReportCheckBox otherFieldDependentCBox"  dependentField="INIT_HIV_DIAG_DATE_TEXT"/> Initial HIV diagnosis (Complete Section II)
						</div>
						<ul class="pL50">
							<li class="floatLeft">
								<span class="riskLabelField bold">Date of 1st HIV diagnosis</span>
								<span class="riskValueField3"><form:input  path="INIT_HIV_DIAG_DATE_TEXT" requiredField="Y" title="HIV Initial diagnosis Date" class="dPicker mask standardInputMedText" datevalidations="completeDate"  maxlength="10"/></span>
								<div class="v_error INIT_HIV_DIAG_DATE_TEXT floatLeft"></div>
							</li>	
						</ul>
						
						<div class="pL30 floatLeft pT10 pB10">
								<span class="bold">Acute infection </span>
								<span class="">
									<c:forEach items="${codedYesNoUnkownValues}" var="codedValue">
											<form:radiobutton path="ACUTE_INFECTION" value="${codedValue.ANS_CODE}" /> ${codedValue.ANS_DESC}
									</c:forEach>
								</span>
							
							</div>
							
						<div class="pL30 floatLeft pT10 pB10">
								<span class="flw50 bold">Has this patient been informed of this diagnosis?</span>
								<span class="riskValueField4">
									<form:radiobutton path="INFORMED_HIV_POS" value="Y" class="otherFieldDependentRadio" dependentField="INFORMED_HIV_DATE_TEXT"/> Yes 							
									<form:radiobutton path="INFORMED_HIV_POS" value="N" class="otherFieldDependentRadio" dependentField="INFORMED_HIV_DATE_TEXT"/> No	
								</span>
							
							</div>
						<ul class="pL50">
							<li class="floatLeft">
									<span class="riskLabelField2 bold">Date informed</span>
									<span class="riskValueField4"><form:input  path="INFORMED_HIV_DATE_TEXT" title="HIV informed Date" class="dPicker mask standardInputMedText" datevalidations="partialDateForAll"  maxlength="10"/></span>
									<div class="v_error INFORMED_HIV_DATE_TEXT floatLeft"></div>
							</li>
						</ul>	
						
						
							<div class="pL30 floatLeft pT10 pB10">
								<span class="bold flw50 ">Was patient referred to an HIV medical provider? </span>
								<span class="">
									<c:forEach items="${codedYesNoUnkownValues}" var="codedValue">
											<form:radiobutton path="REFERRED_HIV_PROVIDER" class="otherFieldDependentRadio "   dependentField="REFERRED_PROVIDER_NAME,REFERRED_PROVIDER_TELEPHONE" value="${codedValue.ANS_CODE}" /> ${codedValue.ANS_DESC}
									</c:forEach>
								</span>
							
							</div>
							
							<div class="pL30 floatLeft pT10 pB10">
								<span class="bold">Referred to:  </span>
								<span class="">
									<form:input path="REFERRED_PROVIDER_NAME" class="standardInputText" requiredField="Y" maxlength="80" title="Provider Name" style="width:525px"/>  							
								</span>
								<div class="v_error REFERRED_PROVIDER_NAME "></div>
							</div>
							
							<div class="pL30 floatLeft pT10 pB10">
								<span class="bold">Telephone number:   </span>
								<span class="">
									<form:input path="REFERRED_PROVIDER_TELEPHONE" class="standardInputMedText" maxlength="20" />  							
								</span>
							</div>
						
						
						
						<div class="clearBoth" ></div>	
						<div class="pL30 ">
							<form:checkbox path="PREV_HIV_DIAG" id="PREV_HIV_DIAG" value="true" class="typeOfReportCheckBox previous otherFieldDependentCBox"  dependentField="PREV_HIV_DIAG_DATE_TEXT"/> Previously diagnosed HIV infection (non-AIDS)	
						</div>
						<ul class="pL50">
							<li class="floatLeft">
								<span class="riskLabelField2 bold">Date of diagnosis</span>
								<span class="riskValueField3"><form:input  path="PREV_HIV_DIAG_DATE_TEXT"  title="Previous HIV diagnosis Date" class="dPicker mask standardInputMedText" datevalidations="partialDateForAll"  maxlength="10"/></span>
								<div class="v_error PREV_HIV_DIAG_DATE_TEXT floatLeft"></div>
							</li>
						</ul>	
						<div class="clearBoth" ></div>
						<div class="pL30">
							<form:checkbox path="INIT_AIDS_DIAG" id="INIT_AIDS_DIAG" value="true" class="typeOfReportCheckBox otherFieldDependentCBox"  dependentField="INIT_AIDS_DIAG_DATE_TEXT" /> Initial/new AIDS diagnosis	
						</div>
						<ul class="pL50">
							<li class="floatLeft">
								<span class="riskLabelField2 bold">Date of diagnosis</span>
								<span class="riskValueField3"><form:input  path="INIT_AIDS_DIAG_DATE_TEXT" requiredField="Y" title="Initial AIDS diagnosis Date" class="dPicker mask standardInputMedText" datevalidations="completeDate" maxlength="10"/></span>
								<div class="v_error INIT_AIDS_DIAG_DATE_TEXT floatLeft"></div>
							</li>
						</ul>	
						<div class="clearBoth" ></div>
						<div class="pL30">
							<form:checkbox path="PREV_AIDS_DIAG" id="PREV_AIDS_DIAG" value="true" class="typeOfReportCheckBox previous otherFieldDependentCBox"  dependentField="PREV_AIDS_DIAG_DATE_TEXT" /> Previously diagnosis	AIDS 
						</div>
						<ul class="pL50">
							<li class="floatLeft">
								<span class="riskLabelField2 bold">Date of diagnosis</span>
								<span class="riskValueField3"><form:input  path="PREV_AIDS_DIAG_DATE_TEXT" title="Previous AIDS diagnosis Date" class="dPicker mask standardInputMedText" datevalidations="partialDateForAll"  maxlength="10"/></span>
								<div class="v_error PREV_AIDS_DIAG_DATE_TEXT floatLeft"></div>
							</li><br>
						</ul>	
										
					</div>
				</div>
			</div>
			
		<!--  HIV Testing History Information -->
	 		<div class="mT10">
	 			<div id="" class="divHeadingCls clear" style="background:#DDDDDD; ">
					<span class=" mL2">II. HIV TESTING HISTORY INFORMATION. Complete this section for a patient receiving an initial/new HIV diagnosis.</span>					
				</div>
				<div class="accordion" id="TTHaccordion" style="margin-bottom: 20px;">		
<%-- 					<c:forEach items="${stdPRFDocVO.tTHDocumentVOList}" var="tthDocumentVo" varStatus="tthCounter">	 --%>
<%-- 						<%@include file="TTHDocument.jsp" %> --%>
<%-- 					</c:forEach>				 --%>


						 	<h3 style="padding:1em"><span class="accordionHeader" style="left: 2em;top: 50%;margin-top: -8px;position: absolute;">TTH Document</span></h3>
	
						 	<div id="personHIVDetails" class="container personHIVDetailsForm" >
						 		
								<div id="personHIVDetailsLeft" class="clear personHIVDetailsLeft">
									<ol start="1" type='1'>
										<li class="floatLeft width100perc">
											<span class="labelHIV bold">Date patient reported information </span>
											<span class="valueHIV"> <form:input path="PT_INTERVIEW_DATE" title="Patient interview Date"  class="dPicker mask standardInputMedText" datevalidations="partialDateForAll" maxlength="15"/> </span>
											<div class="v_error PT_INTERVIEW_DATE floatLeft"></div>
										</li>
										<li class="floatLeft width100perc">
											<div id="everHadPrePosGroup"> 
												<span class="bold">Ever had a previous positive HIV test? </span><br/>
												<c:forEach items="${codedYesNoRefUnkownValues}" var="codedValue">
														<span> <form:radiobutton path="EVER_HAD_PREV_POS_TEST" value="${codedValue.ANS_CODE}" /> ${codedValue.ANS_DESC}  </span><br>
												</c:forEach>
											</div>
											<div class="v_error everHadPrePosGroup floatLeft"></div>											
										</li>
										
										<li class="floatLeft width100perc">
											<span class="labelHIV bold">Date of first positive HIV test </span>
											<span class="valueHIV"> <form:input path="FIRST_POS_HIV_DATE" title="First positive HIV Date" class="dPicker mask standardInputMedText" datevalidations="partialDateForAll"  maxlength="15"/> </span>
											<div class="v_error  FIRST_POS_HIV_DATE floatLeft"></div>
										</li>
										
									
										<li class="floatLeft width100perc">
											<div id="everHadNegGroup">
												<span class="bold">Ever had a negative HIV test? </span><br/>
												<span> <form:radiobutton path="EVER_HAD_NEG" value="Y" class="otherFieldDependentRadio" dependentField="LAST_NEG_DATE" title="Ever had a negative HIV test"/> Yes </span><br>
												<span> <form:radiobutton path="EVER_HAD_NEG" value="N" class="otherFieldDependentRadio" dependentField="LAST_NEG_DATE" title="Ever had a negative HIV test"/> No  </span><br>
												<span> <form:radiobutton path="EVER_HAD_NEG" value="R" class="otherFieldDependentRadio" dependentField="LAST_NEG_DATE" title="Ever had a negative HIV test"/> Refused </span><br>
												<span> <form:radiobutton path="EVER_HAD_NEG" value="U" class="otherFieldDependentRadio" dependentField="LAST_NEG_DATE" title="Ever had a negative HIV test"/> Unknown </span>
											</div> 
											<div class="v_error  everHadNegGroup floatLeft"></div>
										</li>
										<li class="floatLeft width100perc">
											<span class="bold">Date of last negative HIV test</span> <form:input path="LAST_NEG_DATE" value="" title="Last NEGATIVE HIV test date"  class="dPicker mask standardInputSmallText ml10" datevalidations="partialDateForAll"  maxlength="15"/>
											<div class="v_error LAST_NEG_DATE floatLeft"></div>
											 
										</li>
										<li class="floatLeft width100perc" style="height:45px">
											<span class="bold">Number of negative HIV tests within 24 months before first positive test #</span>
											<div> 
												<span> <form:input path="COUNT_HIV_TEST_2YRS" title="First HIV ever test Date" class="standardInputTinyText"  maxlength="5"/>&nbsp;&nbsp;times</span>
												<span> (<form:radiobutton path="RESP_HIV_TEST_2YRS" value="R"  class="RESP_HIV_TEST_2YRS_FIELD" /> Refused</span>
												<span> <form:radiobutton path="RESP_HIV_TEST_2YRS" value="U"  class="RESP_HIV_TEST_2YRS_FIELD"  /> Unknown)</span>
											</div>
										</li> 
										<li class="floatLeft width100perc">
											<span class="bold">Ever taken any antiretroviral (ARVs) medications?  (if no, skip to #9) </span>
											<div id="everTakenAntiretroviralGroup"> 
												<c:forEach items="${codedYesNoRefUnkownValues}" var="codedValue">
													<span> <form:radiobutton path="PT_ARV_EVER_TAKEN" value="${codedValue.ANS_CODE}" title="Ever taken any antiretroviral"/> ${codedValue.ANS_DESC}  </span><br>
												</c:forEach>
											</div>
											<div class="v_error  everTakenAntiretroviralGroup "></div>
											
										</li>
											
										
											
									</ol>	
								</div>			
						
								<div id="personHIVDetailsRight" class="personHIVDetailsRight">	
									<ol type="8" start="8">		
										<li class="floatLeft">
											<div id="arvGroup">
												<div class="pB20">
													<span class="bold">ARVs for prevention (PrEP)?</span>
													<span>
														 <form:radiobutton path="ARV_PREVEN_PREP" value="Y" /> Yes
														 <form:radiobutton path="ARV_PREVEN_PREP" value="N" /> No 
													</span>	
													<div class="clear pT10 pB10">	
														<span class="pT10 pB10 bold"> Date first began:</span> 
														<span class="ml10 pB5"><form:input path="ARV_PREVEN_PREP_START_DT" title="ARVs for prevention first began Date"  class="dPicker mask standardInputSmallText" datevalidations="partialDateForAll"  maxlength="15"/></span>
														<span class="mL20 pB10 bold"> Date last use:</span>
														<span class="ml10 pB5"><form:input path="ARV_PREVEN_PREP_END_DT" title="ARVs for prevention last used Date" class=" dPicker mask standardInputSmallText" datevalidations="partialDateForAll"  maxlength="15"/></span>
														<div class="v_error ARV_PREVEN_PREP_START_DT floatLeft"></div>
														<div class="v_error ARV_PREVEN_PREP_END_DT floatLeft"></div>
													</div>
													<div class="clearBoth">
														<span class="bold">ARV medications:</span>
														<span> 
													
														
															<form:select path="ARV_PREVENPREP_MED_NAMES" class="standardInputText"  multiple="true" title="Antiretrovirals name" >
																	<c:forEach items="${arvList}" var="arvCategory">
																		<form:option value="${arvCategory.LOOKUP_CD}">${arvCategory.ARV_DESC}</form:option>
																	</c:forEach>
																</form:select>
															
																	<script type="text/javascript"> 
													 					var ARV_PREVENPREP_MED_NAMES = '${stdPRFDocVO.ARV_PREVENPREP_MED_NAMES}';
													 					$('#ARV_PREVENPREP_MED_NAMES').val(ARV_PREVENPREP_MED_NAMES.split('|'));
																	</script>
																
														</span>
													</div>
												</div>
												
												<div class="pB20">
													<span class="bold">Post-exposure ARVs (PEP)?</span>
													<span>
														 <form:radiobutton path="POST_EXP_ARV_PEP" value="Y" /> Yes
														 <form:radiobutton path="POST_EXP_ARV_PEP" value="N" /> No 
													</span>	
													<div class="clear pT10 pB10">	
														<span class="pT10 pB10 bold"> Date first began:</span> 
														<span class="ml10 pB5"><form:input path="POST_EXP_ARV_PEP_START_DT" title="ARVs for prevention first began Date"  class="dPicker mask standardInputSmallText" datevalidations="partialDateForAll"  maxlength="15"/></span>
														<span class="mL20 pB10 bold"> Date last use:</span>
														<span class="ml10 pB5"><form:input path="POST_EXP_ARV_PEP_END_DT" title="ARVs for prevention last used Date" class=" dPicker mask standardInputSmallText" datevalidations="partialDateForAll"  maxlength="15"/></span>
														<div class="v_error POST_EXP_ARV_PEP_START_DT floatLeft"></div>
														<div class="v_error POST_EXP_ARV_PEP_END_DT floatLeft"></div>
													</div>
													<div class="clearBoth">
														<span class="bold">ARV medications:</span>
														<span> 
															<form:select path="POST_EXP_ARV_PEP_MED_NAMES" class="standardInputText"  multiple="true" title="Antiretrovirals name" >
																	<c:forEach items="${arvList}" var="arvCategory">
																		<form:option value="${arvCategory.LOOKUP_CD}">${arvCategory.ARV_DESC}</form:option>
																	</c:forEach>
																</form:select>
																
																	<script type="text/javascript"> 
													 					var POST_EXP_ARV_PEP_MED_NAMES = '${stdPRFDocVO.POST_EXP_ARV_PEP_MED_NAMES}';
													 					$('#POST_EXP_ARV_PEP_MED_NAMES').val(POST_EXP_ARV_PEP_MED_NAMES.split('|'));
																	</script>
														</span>
													</div>
												</div>
												
												<div class="pB20">
													<span class="bold">ARVs for treatment?</span>
													<span>
														 <form:radiobutton path="ARV_TREATMENT" value="Y" /> Yes
														 <form:radiobutton path="ARV_TREATMENT" value="N" /> No 
													</span>	
													<div class="clear pT10 pB10">	
														<span class="pT10 pB10 bold"> Date first began:</span> 
														<span class="ml10 pB5"><form:input path="PT_ARV_START_DATE" title="Antiretrovirals start Date"  class="dPicker mask standardInputSmallText" datevalidations="partialDateForAll"  maxlength="15"/></span>
														<span class="mL20 pB10 bold"> Date last use:</span>
														<span class="ml10 pB5"><form:input path="PT_ARV_END_DATE" title="Antiretrovirals end Date" class=" dPicker mask standardInputSmallText" datevalidations="partialDateForAll"  maxlength="15"/></span>
														<div class="v_error PT_ARV_START_DATE floatLeft"></div>
														<div class="v_error PT_ARV_END_DATE floatLeft"></div>
													</div>
													<div class="clearBoth">
														<span class="bold">ARV medications:</span>
														<span> 
														
															<form:select path="PT_ARV_NAME" class="standardInputText"  multiple="true" title="Antiretrovirals name" >
																	<c:forEach items="${arvList}" var="arvCategory">
																		<form:option value="${arvCategory.LOOKUP_CD}">${arvCategory.ARV_DESC}</form:option>
																	</c:forEach>
															</form:select>
															
															<script type="text/javascript"> 
											 					var PT_ARV_NAME = '${stdPRFDocVO.PT_ARV_NAME}';
											 					$('#PT_ARV_NAME').val(PT_ARV_NAME.split('|'));
															</script>
														</span>
													</div>
												</div>
											</div>
											<div class="v_error  arvGroup"></div>
											
										</li>				
										<li class="floatLeft pT10">
											<span >
												<span class="width70 left pB10 bold">Reasons patient reported for getting this test(i.e., the FIRST positive HIV test)? (Respond to each):</span> 
												<span class="width30 left pB10">
													<span class="pLR">Yes</span>
													<span>No</span>
												</span><br>
												<span class="width70 left pB10 bold">Patient thought might have been exposed to HIV in the 6 months before the test</span>
												<span class="width30 left pB10"> 
													<span class="pLR"><form:radiobutton path="RSON_FIRST_POS_EXP" value="Y" /></span>
													<span> <form:radiobutton path="RSON_FIRST_POS_EXP" value="N" /></span>	
												</span>	<br>
												<span class="width70 left pB10 bold">Patient tests regularly and it was time to get tested again</span>
												<span class="width30 left pB10"> 
													<span class="pLR"><form:radiobutton path="RSON_FIRST_POS_ROUTN" value="Y" /></span>
													<span> <form:radiobutton path="RSON_FIRST_POS_ROUTN" value="N" /></span>	
												</span>	<br>
												<span class="width70 left pB10 bold">Patient checking to make sure HIV negative</span>
												<span class="width30 left pB10"> 
													<span class="pLR"><form:radiobutton path="RSON_FIRST_POS_CHKNEG" value="Y" /></span>
													<span> <form:radiobutton path="RSON_FIRST_POS_CHKNEG" value="N" /></span>	
												</span>	<br>
												<span class="width70 left pB10 bold">Patient required to be tested (e.g., insurance, military, court, or other agency)</span>
												<span class="width30 left pB10"> 
													<span class="pLR"><form:radiobutton path="RSON_FIRST_POS_REQD" value="Y" /></span>
													<span> <form:radiobutton path="RSON_FIRST_POS_REQD" value="N" /></span>	
												</span>	<br>
												<span class="width70 left pB10 bold">Clinician offered or recommended HIV testing</span>
												<span class="width30 left pB10"> 
													<span class="pLR"><form:radiobutton path="RSON_CLINICIAN_OFFERED_REC" value="Y" /></span>
													<span> <form:radiobutton path="RSON_CLINICIAN_OFFERED_REC" value="N" /></span>	
												</span>	<br>
												<span class="width70 left pB10 bold">Other reason <form:input path="RSON_FIRST_POS_OTHER_TEXT" requiredField="Y" class=" ml10 standardInputText" style="width:435px;"  maxlength="100" title="Other Reason"/><br><span class="labelunderneath">(Please describe)</span></span>
												<span class="width30 left pB10"> 
													<span class="pLR"><form:radiobutton path="RSON_FIRST_POS_OTHER"  value="Y" class="otherFieldDependentRadio" dependentField="RSON_FIRST_POS_OTHER_TEXT"/></span>
													<span> <form:radiobutton path="RSON_FIRST_POS_OTHER" value="N"  class="otherFieldDependentRadio" dependentField="RSON_FIRST_POS_OTHER_TEXT"/></span>	
												</span>	<br>
											</span>
											<div class="v_error  RSON_FIRST_POS_OTHER_TEXT"></div>
											
										</li>
										
									</ol>		
								</div>	
							</div>
	 			</div>
	 		</div>
	 	<!--  End of HIV Testing History Information -->	
		<c:set var="currentPartnerIndex" value="0" />

<%-- 		<div class="pT10">
			<form:checkbox path="PS_REQUESTED" value="true" maxlength="1"/> Provider Requests Health Department Partner Services	
		</div>			 --%>
		<!-- Partner Details  -->
		<div class="pB20">
			<c:choose>
			<c:when test="${stdPRFDocVO.partnerList != null && stdPRFDocVO.partnerList != '[]' }">
					<div id="" class="divHeadingCls clear" style="background:#DDDDDD; ">
						<span class=" mL2"  id="prfPartnerTitle">III. PARTNER/CONTACT INFORMATION.</span>
						<span class="floatRight editableFields"><img onclick="javascript:addPrfPartner('Y')" src="${staticPath}/images/plus.png" class="cloningIcons"> </span>					
					</div>
					<div id="prfPartnersAccordion" >
						<c:forEach items="${stdPRFDocVO.partnerList}" var="ptnrDocumentVo" varStatus="ptnrCounter">
							<c:set var="currentPartnerIndex" value="${ptnrCounter.index}" />
							<%@include file="PartnerDetails2016.jsp" %>
						</c:forEach>
					</div>	
					<form:hidden path="" id="partnersCount" value="${currentPartnerIndex}" />			
			</c:when>
			<c:otherwise>
				<div id="" class="divHeadingCls clear" style="background:#DDDDDD; ">
					<span class=" mL2" id="prfPartnerTitle">III. No Partners </span>	
					<span class="floatRight editableFields"><img onclick="javascript:addPrfPartner('Y')" src="${staticPath}/images/plus.png" class="cloningIcons"> </span>				
				</div>
				<div id="prfPartnersAccordion">
				</div>
				<form:hidden path="" id="partnersCount" value="-1" />
			</c:otherwise>	
			</c:choose>
		</div>
		
		<!--  End of HIV Testing History Information -->	

		<!--  Provider details  -->	
			<div class="personHIVDetailsForm" class="container clear" style="clear:both" id="prfProviderSection">	
				<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
					<span class=" mL2">IV. Provider Information</span>					
				</div>
				<div style="border: 1px dotted; height:620px">
					<div class="personHIVDetailsLeft">
						<div class="personTitleField italic bold">Person completing the form:</div>
						<div class="pL30 pT10">
							<span class="personLabelDoc4 bold">Name </span>
							<span> 
								<span ><form:input path="COMPLETER_LNAME" class="standardInputSmallText mL20 required"  maxlength="25"/></span> 
								<span ><form:input path="COMPLETER_FNAME" class="standardInputSmallText required"  maxlength="20"/></span>
								<span ><form:input path="COMPLETER_MNAME" class="standardInputSmallText"  maxlength="1"/></span>
								<br>
								<span class="labelunderneath4 requiredInd"> Last*</span>
								<span class="labelunderneath4 requiredInd"> First*</span>
								<span class="labelunderneath4"> Middle</span>
							</span>
						</div>
						<div class="pL30 pT10">
							<span class="riskLabelField4 bold">Title</span>
							<span ><form:input path="COMPLETER_TITLE" class="standardInputMedText"  maxlength="30"/></span> 
						</div>
						<div class="pL30 pT10">
							<span class="riskLabelField4 bold requiredInd">Telephone number*</span>
							<span> 
								<form:input path="COMPLETER_PHONE" class="pNumber standardInputMedText required"  maxlength="20"/>
								Extn <form:input path="COMPLETER_PHONE_EXT" class="nOnly standardInputSmallText1"  maxlength="5"/>
							</span>
							<div class="v_error  COMPLETER_PHONE"></div>							
						</div>

						<div class="pL30 pT10">
							<span class="riskLabelField4 bold">E-mail address</span>
							<span ><form:input path="COMPLETER_EMAIL" class="standardInputMedText"  maxlength="50"/></span>
							<div class="v_error COMPLETER_EMAIL"></div> 
						</div><br>
						<!--  Facility details  -->
						<div id="prfDocumentFacilityAddressDtls" class="facilityDetails">
							<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD;">
								<span class="mL2">Facility Details</span>
							</div><br>
							<div class="pL30 pT10">
								<span class="riskLabelField4 bold">Facility Name</span>
								<span ><form:input path="FACILITY_NAME" class="standardInputText facilityFields" maxlength="80" /></span> 
							</div>
							<div class="pL30 pT10">
								<span class="riskLabelField4 bold">Department/clinic</span>
								<span ><form:input path="FACILITY_DEPT" class="standardInputSmallText2 facilityFields" maxlength="40" /></span> 
							</div>
							<div class="pL30 pT10" id="FACILITY_TYPE_GROUP">
								<span class="riskLabelField4 bold requiredInd">Facility Type*</span>
								<span class="floatLeft" style="width:70%">
								
									<c:forEach items="${codedFacilityTypeValues}" var="facilityType">
										<label>
										
										<c:choose>
											<c:when test="${facilityType.FACILITY_TYPE_CODE == 'F.OTH'}">
												<form:radiobutton path="FACILITY_TYPE" id="FACILITY_TYPE" value="${facilityType.FACILITY_TYPE_CODE}" class="facilityFields" requiredgroup="requiredgroup" title="Facility Type"  />${facilityType.EPRF_FACILITY_TYPE_DESCRIPTION}
												<form:input path="FACILITY_TYPE_OTHER_TEXT" class="standardInputSmallText3 facilityFields"  maxlength="100" title="Other Facility type"/>
											</c:when>
										
											<c:otherwise>
												<form:radiobutton path="FACILITY_TYPE" id="FACILITY_TYPE" value="${facilityType.FACILITY_TYPE_CODE}" class="facilityFields" requiredgroup="requiredgroup" title="Facility Type"/>${facilityType.EPRF_FACILITY_TYPE_DESCRIPTION}	
											</c:otherwise>
										</c:choose>
										
										</label>										
										<br/>
									</c:forEach>
								</span>
								<div class="v_error FACILITY_TYPE_GROUP "></div>
								 
							</div>
							<div id="prfFacilityAddressSection" style="border: 1px dotted;" class="mT10 pB10">
								<div class="pL30 pT10 clear">
									<span class="riskLabelField4 bold">Facility address</span>
									<span ><form:input path="FACILITY_ADDRESS" class="standardInputText newAddress widgetAddressFieldsReadonly  facilityFields"  maxlength="62"/></span>
									<span class="labelunderneath3 dispAddressStdRaw"> Street (Std)</span> <br>
								</div>
								<div class="pL30 pT10">
									<span class="riskLabelField4 bold">City</span>
									<span><form:input path="FACILITY_CITY" class="oAdrDlg standardInputSmallText1 newCity widgetAddressFieldsReadonly  facilityFields"  maxlength="30"/></span>
									<span class="pL30 bold">County</span>
									<span class="pL30">
										<form:select path="FACILITY_COUNTY"  cssClass="oAdrDlg standardInputSmallText1 newCounty widgetAddressFieldsReadonly  facilityFields" > 
															<form:option value=""></form:option>
															<form:options items="${sessionScope.countyMap}"/>
														</form:select>
									</span>
									
									 <div style="float: right;" class="mr5">
							 					 	<a href="javascript:openAddressWidget('prfFacilityAddressSection');" class="inlineEditButton displayNone editAddress">Edit</a>
												 </div>
									<br>
								</div>	
								<div class="pL30 pT10">
									<span class="riskLabelField4 bold">State</span>
									<span>
										<form:select path="FACILITY_STATE" class="oAdrDlg standardInputSmallText1 newState widgetAddressFieldsReadonly  facilityFields">
											<form:option value=""></form:option>
											<form:options items="${sessionScope.stateMap}"/>
										</form:select> 
									</span>
									<span class="pL30 bold"> Zip </span>
									<span class="pL30"><form:input path="FACILITY_ZIP" class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly  facilityFields" maxlength="5"/>
										<form:input path="FACILITY_PLUS4" class="standardInputSmallText1 facilityFields" maxlength="4"/></span>	
									<br>
								</div>
							</div>
							<div class="pL30 pT10">
								<span class="riskLabelField4 bold">Date form Completed</span>
								<fmt:formatDate value="${stdPRFDocVO.COMPLETED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedcompletedDate" ></fmt:formatDate>
								<span ><form:input path="COMPLETED_DATE_TEXT" value="${theFormattedcompletedDate}" class="dPicker mask standardInputMedText" datevalidations="completeDate" maxlength="10" title="Date form completed"/></span>
								<div class="v_error COMPLETED_DATE_TEXT"></div> 
							</div>	
						</div>
					</div>
					<!-- Other Contact Details -->
					<div class="personHIVDetailsRight">
						<div class="personTitleFieldRight">Office contact person for information related to partner notification<br><center>(if other than the person completing the form):<center></div>	
						<div class="pL30 pT10">
							<span class="personLabelDoc4 bold">Name </span>
							<span> 
								<span ><form:input path="CONTACT_LNAME" class="mL20 standardInputSmallText"  maxlength="25"/></span> 
								<span ><form:input path="CONTACT_FNAME" class="standardInputSmallText"  maxlength="20"/></span>
								<span ><form:input path="CONTACT_MNAME" class="standardInputSmallText"  maxlength="1"/></span>
								<br>
								<span class="labelunderneath4"> Last</span>
								<span class="labelunderneath4"> First</span>
								<span class="labelunderneath4"> Middle</span>
							</span>
						</div>
						<div class="pL30 pT10">
							<span class="riskLabelField4 bold">Title</span>
							<span ><form:input path="CONTACT_TITLE" class="standardInputSmallText"  maxlength="30"/></span> 
						</div>
						<div class="pL30 pT10">
							<span class="riskLabelField4 bold">Telephone number</span>
							<span> 
								<form:input path="CONTACT_PHONE" class="pNumber standardInputMedText"  maxlength="20"/>
								Extn <form:input path="CONTACT_PHONE_EXT" class="nOnly standardInputSmallText1"  maxlength="5"/>
							</span>
						</div>
						<br><br>	
						<div class="personTitleFieldRight"><center>Medical Provider:<br>To the best of my knowledge,<br>I certify that the above information is correct.<center></div>
			
						<div class="pL30 pT10">
							<span class="bold">Print Name</span>
							<span ><form:input path="PROV_LNAME" class="mL20 standardInputSmallText3 required"  maxlength="80"/>
								<form:input path="PROV_FNAME" class="ml10 standardInputSmallText3 required"  maxlength="80"/>
								<form:input path="PROV_MI" class="ml10 standardInputSmallText3"  maxlength="1"/></span> 
								<br>
								<span class="labelunderneath4 requiredInd"> Last*</span>
								<span class="labelunderneath4 requiredInd"> First*</span>
								<span class="labelunderneath4"> Middle</span>
						</div>
						
						<div class="pL30 pT10">
							<span class="bold">NYS License #:</span>
							<span ><form:input path="PROVIDER_NYS_LIC_NO" class="mL20 standardInputSmallText required"  maxlength="20"/></span> 
						</div>
						
						
						<div class="pL30 pT10">
							<span class="bold">Prof. Code:</span>
							<span >
									
									<form:select path="PROV_LICENSE_PROF_CD" class="mL20 standardInputSmallText required"  >
										<form:option value="" label="Select"></form:option>
										<c:forEach items="${codedProfessionValues}" var="professionCode">
											<form:option value="${professionCode.PROF_CODE}" label="${professionCode.PROF_DESC}"></form:option>
										</c:forEach>
									</form:select>
							</span> 
						</div>
						
							
						<div class="pL30 pT10">
							<span class="bold">National Provider Identifier (NPI):</span>
							<c:choose>
								<c:when test="${stdPRFDocVO.PROVIDER_NPI_NO == null || stdPRFDocVO.PROVIDER_NPI_NO == ''}">
									<span ><form:input path="PROVIDER_NPI_NO" class="mL20 standardInputSmallText "  maxlength="10" title="NPI"/></span>	
								</c:when>
								<c:otherwise>
									<span ><form:input path="PROVIDER_NPI_NO" class="mL20 standardInputSmallText readonly"  maxlength="10" title="NPI"/></span>
								</c:otherwise>
							</c:choose>
							 <div class="v_error PROVIDER_NPI_NO"></div> 
						</div>
						
						<div class="pL30 pT10">	
							<span class="riskLabelField4 bold"> Has Provider Signed Form</span> 
								<div> 
									<span> <form:checkbox path="PROV_SIGNED" value="true" class="otherFieldDependentCBox" dependentField="PROV_SIGNED_DATE_TEXT"/> Yes </span>
								</div>
						</div>
						
						<div class="pL30 pT10">
							<span class="riskLabelField4 bold">Date Signed</span>
							<fmt:formatDate value="${stdPRFDocVO.PROV_SIGNED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedprovSignedDate" ></fmt:formatDate>
							<span ><form:input path="PROV_SIGNED_DATE_TEXT" value="${theFormattedprovSignedDate}" class="dPicker mask standardInputMedText" datevalidations="completeDate"  maxlength="10" title="Provider signed date"/></span>
							<div class="v_error PROV_SIGNED_DATE_TEXT"></div> 
						</div>			
					</div>
				</div>	
			</div>	
		</div>
	</div>

	<div class="mT40">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
	
	<div id="button-row" class="button-row clear documentButtons"  align="center">
		
				
					<c:if  test="${stdPRFDocVO.STATENO != null && stdPRFDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}" >
							<c:if  test="${userPermissions['98'] != null}">	
								<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3 displayNone"  value="Trigger Message"  onclick="confirmAddressForPsMessage()"/>
							</c:if>
							<c:if  test="${userPermissions['99'] != null}">
								<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3 displayNone"  value="Trigger Assignment"  onclick="loadSurvAssignmentPopUp()"/>
							</c:if>
							<c:if  test="${param.hasEditFunctionality == true}">
								<input type="button" id="eButton"  class="editBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="customDocumentEditViewerStyles()" />
								<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Save" onclick="saveForm()"/>
							</c:if>
							
					</c:if>			
		
					<c:if test="${stdPRFDocVO.CURRENT_PROCESS_STATUS == 16 && hasEditFunctionality == true}">
						<input type="button" id="eButton" style="display:none;" class="editBtnclick button rounded blue effect-3"  value="Edit" onclick="customDocumentEditViewerStyles('Y')" />
						<input type="button" id="pButton" style="display:none" class="button rounded blue effect-3"  value="Process" onclick="confirmProcessRawPrfDocument(${stdPRFDocVO.DOCUMENT_ID})"/>
						<input type="button" id="dButton" style="display:none" class="deleteBtnclick button rounded blue effect-3"  value="Delete" onclick="confirmDeleteRawPrfDocument(${stdPRFDocVO.DOCUMENT_ID})"/>
					</c:if>
					
				<c:choose>
						<c:when  test="${stdPRFDocVO.CURRENT_PROCESS_STATUS == 33}" >
							<input type="button" id="uButton"  class="undeleteErrorDoc button rounded blue effect-3"  value="Undelete" onclick="unDeleteErrorDocument()" />
						</c:when>
						<c:when  test="${stdPRFDocVO.CURRENT_PROCESS_STATUS == 4}" >
							<input type="button" id="eButton" style="display:none;" class="editBtnclick button rounded blue effect-3"  value="Edit" onclick="customDocumentEditViewerStyles('Y')" />
							<input type="button" id="dButton" style="display:none" class="deleteErrorDoc button rounded blue effect-3"  value="Delete" onclick="deleteErrorDocument()"/>
							<input type="button" id="rButton"  class="repriseBtnclick button rounded blue effect-3 " value="Reprise" onclick="repriseErrorDocument()"/>
						</c:when>
				
						<c:when  test="${param.hasDeleteFunctionality == true}" >	
								<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
								<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
						</c:when>
						<c:when test="${userPermissions['90'] != null && stdPRFDocVO.STATENO != null && stdPRFDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
								<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
						</c:when>
					</c:choose>
					
					<c:if  test="${param.hasEditFunctionality == null}">
						<input type="button" id="sButton" class="prfBtnclick button rounded blue effect-3" value="Save" onclick="savePrfForm('N')"/>
					</c:if>

						
					<c:if test="${stdPRFDocVO.STATENO != null && stdPRFDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null && userPermissions['230'] != null}">
					
							<input type="button" id="LaftFollowupButton" class="flagLaftFollowupDocument button rounded blue effect-3 displayNone"  value="Laft follow-up"  onclick="flagLaftFollowupDocument(${stdPRFDocVO.DOCUMENT_ID}, '${stdPRFDocVO.STATENO}')"/>
					
					</c:if>						
						
					<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel"  onclick="cancelEditPage()"/>
		
		<span id="btnTxtMsg" style="display:none;"> Add / Cancel partner to submit </span>
	</div>
	<form:hidden path="CURRENT_PROCESS_STATUS" />
	<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />	
</form:form>															