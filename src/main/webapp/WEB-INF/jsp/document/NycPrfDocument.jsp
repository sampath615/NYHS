<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>
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
                    <span class="ml10"><form:input path="STATENO"  class="standardInputSmallText" style="width:180px" maxlength="10" readonly="true"/> </span>
	            </div>    
            </div>
            <div style="float:left; width: 30%">
	            <div >
	                <span class=" ml10">	eHARS Import Date: </span>
	                <fmt:formatDate value="${nycPRFDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
					<span class=" ml10"><form:input path="EHARS_IMPORT_DATE" value="${theFormattedDate}"  class="standardInputSmallText ml10" readonly="true"/></span>
	            </div>                  
            </div>
            <div style="float:left; width: 30%; text-align: right">
            	<div>
	                <span class="mL10">Document ID: </span>
					<span class="mL10 "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
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
								<span class="pL150"><form:input path="LNAME" id="lName" class="standardInputMedText" maxlength="25" /></span> 
								<span  class="pL30"><form:input path="FNAME" class="standardInputMedText" maxlength="20" /></span>
								<span  class="pL30"><form:input path="MNAME" class="standardInputMedText" maxlength="25" /></span><br>
								<span class="labelunderneath2" style="display: inline-block"> Last</span>
								<span class="labelunderneath1"> First</span>
								<span class="labelunderneath1"> Middle</span>
							</span>
						</div>
						<div class="personInfo">
							<span class="personLabelDoc bold">Alternate name </span>
							<span> 
								<span style="padding-left:95px"><form:input path="ALT_NAME" class="standardInputMedText" maxlength="25" /></span><br>
						
							</span>
						</div>
												
						<div class="b1" style="margin: 15px 5px 10px 5px">
			 	            <div class="personInfo pTRBL10">
			 	                <span class="personLabelDoc bold"> Address </span>
			 		                <span> 	                	
				 	                	<span class="pL10"><form:input path="rawPersonAddress" class="standardInputText newAddress" style="width:300px" maxlength="62" />  </span>
				 	                    
				 	                    <span class="pL30 bold">Apartment</span>
				 	                    <span class="pL10"><form:input path="APARTMENT" class="oAdrDlg standardInputSmallText newApartment" maxlength="30"/></span>
				 	                    
				 	                    <span class="pL30 bold">City</span>
				 	                    <span class="pL10"><form:input path="PERSON_CITY" class="oAdrDlg standardInputSmallText1 newCity" maxlength="30"/></span>
				 	                    
				 	                    <span class="pL30 bold">State </span>
				 	                    <span  class="pL10">
											<form:input path="PERSON_STATE" cssClass=" standardInputSmallText1 "  maxlength="2"/>
										</span>
				 	                    
				 	                    <span class="pL30 bold">Zip </span>
				 	                    <span class="pL10"> 
				 	                        <form:input path="PERSON_ZIP" class="standardInputSmallText1" maxlength="5"/>
				 	                    </span>
				 	                    <span class="pL30 bold">County </span>
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
			 	              	 	                 
			 	                 <span class="pL30 bold"> Phone</span>
			 					 <span class="pL10"> <form:input path="PERSON_PHONE" class="pNumber standardInputSmallText" maxlength="20"/></span>
			 	                <br>               
			 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
			 	                    
			 	            </div>
			 	        </div>

						<div class="personInfo pB10">
							<span class="fieldHolder25 ">
								<span class="fLW35 bold">Date of Birth </span>
								<span  class="fLW60"><form:input path="DOB" id="dobValidation" class="dPicker standardInputSmallText"  maxlength="10"/></span>
							</span>
							<span class="fieldHolder25 ">
								<span class="fLW35 bold">Date of Death </span>
								<span class="fLW60"> 
									<form:input path="DOD_TEXT" id="dodValidation" class="dPicker standardInputSmallText"  maxlength="10"/>
								</span>
							</span>						
							<span class="fieldHolder50 ">
								<span class="fLW35 bold">SSN </span>
								<span class="fLW60"> 
									<form:input path="SSN" class="standardInputSmallText"  maxlength="9"/>
								</span>
							</span>						
						</div><br>
						<div class="personInfo pT10 clear">
							<span class="fieldHolder25 ">
								<span class="fLW35 bold">Gender </span>
								<span class=""> 
									<span> <form:radiobutton path="CURRENT_GENDER" value="M" /> Male </span>
									<span> <form:radiobutton path="CURRENT_GENDER" value="F" /> Female </span>
								</span>
							</span>
							<span class="fieldHolder25 ">
								<span class="fLW35 bold">Transgender </span>
								<span class=""> 
									<span> <form:radiobutton path="CURRENT_GENDER" value="X" /> MTF </span>
									<span> <form:radiobutton path="CURRENT_GENDER" value="Y" /> FTM </span>
								</span>
							</span>
							<span class="fieldHolder50 ">
								<span class="fLW35 bold">Additional gender identity</span>
								<span  class="fLW60"><form:input path="ADDITIONAL_GENDER_IDENTITY" class="standardInputText"  /></span>
							</span>	<br>					
						</div><br>
						<div class="personInfo clear">
							<span class="fieldHolder25 ">
								<span class="fLW35 bold">Birth Sex </span>
								<span class=""> 
									<span> <form:radiobutton path="BIRTH_SEX" value="M" /> Male </span>
									<span> <form:radiobutton path="BIRTH_SEX" value="F" /> Female </span>
								</span>
							</span>
							<span class="fieldHolder25 ">
								<span class="fLW35 bold">Patient Record No. </span>
								<span  class="fLW60"><form:input path="MED_RECORD_NUMBER" class="standardInputSmallText"  maxlength="17"/></span>
							</span>
							<span class="fieldHolder50 ">
								<span class="fLW35 bold">Expected Date of Delivery</span>
								<span class=""> 
									<span> <form:input path="EDC"  class="dPicker mask standardInputSmallText " datevalidations="partialDateForAllorFuture" maxlength="20" title="EDC"/> </span>
								</span>
							</span>
							
							
							
							
						</div>
						<div class="personInfo pT10 clear">
							<span class="fieldHolder50 ">
								<span class="fLW20 bold">Primary Language </span>
								<span class="fLW75">
									<c:forEach items="${codedPrimaryLanguageValues}" var="primaryLanguage">
										<span><form:radiobutton path="PRIMARY_LANGUAGE" value="${primaryLanguage.LANG_CODE}" /> ${primaryLanguage.LANG_DESC}</span>
										<c:if test="${primaryLanguage.LANG_CODE == 'O'}">
											<span class="mL20"><form:input path="PRIMARY_LANGUAGE_OTHER" class="standardInputSmallText" style="width:220px" maxlength="50" title="Other Primary Language"/></span>
										</c:if>
									</c:forEach>
								</span>
							</span>
							
							<span class="fieldHolder50 ">
								<span class="fLW35 bold">Currently pregnant?</span>
								<c:forEach items="${codedYesNoUnkownValues}" var="codedValue">
										<span  class="mL10"><form:radiobutton path="PREGNANT"  value="${codedValue.ANS_CODE}" title="Currently Pregnant"/> ${codedValue.ANS_DESC}</span>
								</c:forEach>
							</span>
							
						</div>
						
						
						<div class="personInfo pT10 clear">
							<span class="fieldHolder50">
								<span class="fLW35 bold">Date of Admission or Visit </span>
								<fmt:formatDate value="${nycPRFDocVO.ADMISSION_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedadmissionDate" />
								<span  class="fLW60"><form:input path="ADMISSION_DATE" value="${theFormattedadmissionDate}" class="dPicker standardInputText "  maxlength="10"/>&nbsp;&nbsp;</span>
							</span>
							<span class="fieldHolder50 ">
								<span class="fLW35 bold">DIN for inmate </span>
								<span class="fLW60"> 
									<form:input path="DIN" class="standardInputText "  maxlength="7"/>
								</span>
							</span>
						</div>
						<div class="personInfo  pT10 clear">
							<div class="fieldHolder50">
								<span class="fLW35   bold">Other identifier </span>
								<span  class="fLW60"><form:input path="PERSON_OTHER_IDENTIFIER" class="standardInputText " maxlength="50" title="Other identifier" />&nbsp;&nbsp;</span>
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
									<form:input path="RACE_OTHER_TEXT" class="standardInputSmallText2"  maxlength="20"/>
								</div>
								<div>
									<form:checkbox path="RACE_UNKNOWN" value="1" /> Unknown
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
									<form:radiobutton path="ETHNICITY" value="01" /> Yes
									<form:radiobutton path="ETHNICITY" value="02" /> No
									<form:radiobutton path="ETHNICITY" value="09" /> Unknown
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
									<form:select path="BIRTH_COUNTRY" cssClass="standardInputSmallText" title="Birth Country">
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
										
										<c:forEach items="${codedSexualOrientationValues}" var="sexualOrientation">
												<div><span  class=""><form:radiobutton path="SEX_ORN" value="${sexualOrientation.SEX_ORIENT_CD}" title="Sexual Orientation"/> ${sexualOrientation.SEX_ORIENT_DESC}</span></div>
										</c:forEach>
										<div class="mT5">
											<span class="mL10"></span><form:input path="SEX_ORN_ADD_ID" class="standardInputMedText"  maxlength="50" title="Additional Sexual Orientation identity" />
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
									<span class="riskValueField"><form:radiobutton path="SWM" value="Y" /></span>
									<span class="riskValueField"><form:radiobutton path="SWM" value="N" /></span>
									<span class="riskValueField"><form:radiobutton path="SWM" value="U" /></span><br>		
								</div>
								<div class="pT8">
									<span class="riskLabelField bold">Sex With Female</span>
									<span class="riskValueField"><form:radiobutton path="SWF" value="Y" /></span>
									<span class="riskValueField"><form:radiobutton path="SWF" value="N" /></span>
									<span class="riskValueField"><form:radiobutton path="SWF" value="U" /></span>
								</div>
								<div class="pT8 pB5">
									<span class="riskLabelField pT8 pB5 bold">Injection Drug Use </span>
						 			<span class="riskValueField pT8 pB5"><form:radiobutton path="IDU" value="Y" /></span>
									<span class="riskValueField pT8 pB5"><form:radiobutton path="IDU" value="N" /></span>
									<span class="riskValueField pT8 pB5"><form:radiobutton path="IDU" value="U" /></span> 
								</div>	
								<div class="pT10"> <u><span class="bold">Heterosexual sex with:</span></u> </div>
								<ul>
									<li class="pT8">
										
											<span class="riskLabelField1 bold">Injection Drug User </span>
								 			<span class="riskValueField1"><form:radiobutton path="SEX_PARTNER_IDU" value="Y" /></span>
											<span class="riskValueField1"><form:radiobutton path="SEX_PARTNER_IDU" value="N" /></span>
											<span class="riskValueField1"><form:radiobutton path="SEX_PARTNER_IDU" value="U" /></span> 
										
									</li>
									<li class="pT8">		
										
											<span class="riskLabelField1 bold">Bisexual man </span>
								 			<span class="riskValueField1"><form:radiobutton path="SEX_PARTNER_MSM" value="Y" /></span>
											<span class="riskValueField1"> <form:radiobutton path="SEX_PARTNER_MSM" value="N" /></span>	
											<span class="riskValueField1"> <form:radiobutton path="SEX_PARTNER_MSM" value="U" /></span>	 
										
									</li>
									<li class="pT8">	
										
											<span class="riskLabelField1 bold">HIV+ person </span>
								 			<span class="riskValueField1"><form:radiobutton path="SEX_PARTNER_HIV" value="Y" /></span>
											<span class="riskValueField1"> <form:radiobutton path="SEX_PARTNER_HIV" value="N" /></span>	
											<span class="riskValueField1"> <form:radiobutton path="SEX_PARTNER_HIV" value="U" /></span>		 
										
									</li>
									<li class="pT8">	
										
											<span class="riskLabelField1 bold">Person with HIV + Hemophiliac and documented HIV </span>
								 			<span class="riskValueField1"><form:radiobutton path="SEX_PARTNER_HEMOPHILIAC" value="Y" /></span>
											<span class="riskValueField1"> <form:radiobutton path="SEX_PARTNER_HEMOPHILIAC" value="N" /></span>	
											<span class="riskValueField1"> <form:radiobutton path="SEX_PARTNER_HEMOPHILIAC" value="U" /></span>		 
										
									</li>
								</ul>
								<div class="pT8 pB5">
									<span class="riskLabelField pB5 bold">Perinatal Exposure </span>
						 			<span class="riskValueField pB5"><form:radiobutton path="PERINATAL" value="Y" /></span>
									<span class="riskValueField pB5"> <form:radiobutton path="PERINATAL" value="N" /></span>	
									<span class="riskValueField pB5"> <form:radiobutton path="PERINATAL" value="U" /></span>		 
								</div>	
								<div class="pT8">
									<span class="riskLabelField bold">Received Blood Products </span>
						 			<span class="riskValueField"><form:radiobutton path="RECV_BLOOD_PROD" value="Y" /></span>
									<span class="riskValueField"><form:radiobutton path="RECV_BLOOD_PROD" value="N" /></span>	
									<span class="riskValueField"><form:radiobutton path="RECV_BLOOD_PROD" value="U" /></span>	 
								</div>
								<br>
								<ul style="clear:both">
									<li class="pT8">
										<span class="riskLabelField2 bold">Date </span>
										<span class="riskValueField2"><form:input path="RECV_BLOOD_PROD_DATE_TEXT" class="dPicker standardInputSmallText"  maxlength="10"/></span>	<br>
									</li>
									<li class="pT8">
										<span class="riskLabelField2 bold">Country </span>
										<span class="riskValueField2"><form:input path="RECV_BLOOD_PROD_COUNTRY" class="standardInputSmallText"  maxlength="40"/></span>
									</li>	
								</ul>	
								<div class="pT8" style="clear:both">
									<span class="riskLabelField bold">Worked in healthcare</span>
						 			<span class="riskValueField"><form:radiobutton path="HEALTHCARE_WORKER" value="Y" /></span>
									<span class="riskValueField"><form:radiobutton path="HEALTHCARE_WORKER" value="N" /></span>	
									<span class="riskValueField"><form:radiobutton path="HEALTHCARE_WORKER" value="U" /></span>	 
								</div>
								<div class="pT8" style="clear:both">
									<span class="riskLabelField bold">Risk other</span>
						 			<span class="riskValueField"><form:radiobutton path="RISK_OTHER" value="Y" /></span>
									<span class="riskValueField"><form:radiobutton path="RISK_OTHER" value="N" /></span>	
									<span class="riskValueField"><form:radiobutton path="RISK_OTHER" value="U" /></span>	 
								</div>
								<div style="clear:both" class="pT8">
									<span class="riskLabelField3 bold">Other risk </span>
									<span class="riskValueField3"><form:input path="RISK_OTHER_TEXT" class="standardInputSmallText"  maxlength="30"/></span>
								</div>	
								<div class="pT8" style="clear:both">
									<span class="riskLabelField bold">Risk unknown</span>
						 			<span class="riskValueField"><form:radiobutton path="RISK_UNKNOWN" value="Y" /></span>
									<span class="riskValueField"><form:radiobutton path="RISK_UNKNOWN" value="N" /></span>	
									<span class="riskValueField"><form:radiobutton path="RISK_UNKNOWN" value="U" /></span>	 
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
							<form:checkbox path="INITIAL_HIV_DIAG" value="1"  maxlength="1"/> Initial/new HIV diagnosis(Complete Section II)	
						</div>
						<ul class="pL50">
							<li>
								<span class="riskLabelField2 bold">Date of 1st HIV diagnosis</span>
								<span class="riskValueField3"><form:input  path="INITIAL_HIV_DIAG_DATE_TEXT" class="dPicker standardInputMedText"  maxlength="10"/></span>
							</li>
						</ul>	
						
						<div class="pL30 floatLeft pT10 pB10 clearBoth">
								<span class="bold">Acute infection </span>
								<span class="">
									<c:forEach items="${codedYesNoUnkownValues}" var="codedValue">
											<form:radiobutton path="ACUTE_INFECTION" value="${codedValue.ANS_CODE}" /> ${codedValue.ANS_DESC}
									</c:forEach>
								</span>
							
							</div>
						<div class="clearBoth pL30 floatLeft pT10 pB10">
								<span class="riskLabelField2 bold">Has this patient been informed of his/her HIV positive status?</span>
									<span class="riskValueField4">
										<form:radiobutton path="INFORMED_HIV_POS" value="1" /> Yes 							
										<form:radiobutton path="INFORMED_HIV_POS" value="0" /> No	
									</span>
							
							</div>
						<ul class="pL50">
							<li class="floatLeft">
									<span class="riskLabelField2 ">Date</span>
									<span class="riskValueField4"><form:input  path="INFORMED_HIV_DATE_TEXT" class="dPicker standardInputMedText"  maxlength="10"/></span>
							</li>
						</ul>	
					
			
						<div class="clearBoth pL30 pT10">
							<form:checkbox path="PREVIOUS_HIV_DIAG" value="1"/> Previously diagnosed HIV infection (non-AIDS)	
						</div>
						<ul class="pL50">
							<li>
								<span class="riskLabelField2 bold">Date of diagnosis</span>
								<span class="riskValueField3"><form:input  path="PREV_HIV_DIAG_DATE_TEXT" class="dPicker standardInputMedText"  maxlength="10"/></span>
							</li>
						</ul>	
						<div class="pL30">
							<form:checkbox path="INITIAL_AIDS_DIAG" value="1" /> Initial/new AIDS diagnosis	
						</div>
						<ul class="pL50">
							<li>
								<span class="riskLabelField2 bold">Date of diagnosis</span>
								<span class="riskValueField3"><form:input  path="INIT_AIDS_DIAG_DATE_TEXT" class="dPicker standardInputMedText"  maxlength="10"/></span>
							</li>
						</ul>	
						<div class="pL30">
							<form:checkbox path="PREVIOUS_AIDS_DIAG" value="1" /> Previously diagnosis	AIDS 
						</div>
						<ul class="pL50">
							<li>
								<span class="riskLabelField2 bold">Date of diagnosis</span>
								<span class="riskValueField3"><form:input  path="PREV_AIDS_DIAG_DATE_TEXT" class="dPicker standardInputMedText"  maxlength="10"/></span>
							</li><br>
						</ul>
						
						
						
						<div class="pL30 pT10 pB10">
								<span class="bold flw50 bold">Was patient referred to an HIV medical provider? </span>
								<span class="">
									<c:forEach items="${codedYesNoUnkownValues}" var="codedValue">
											<form:radiobutton path="REFERRED_HIV_PROVIDER" class="otherFieldDependentRadio "   dependentField="REFERRED_PROVIDER_NAME,REFERRED_PROVIDER_TELEPHONE" value="${codedValue.ANS_CODE}" /> ${codedValue.ANS_DESC}
									</c:forEach>
								</span>
							
							</div>
							
							<div class="pL30  pT10 pB10">
								<span class="bold ">Referred to:  </span>
								<span class="mL50">
									<form:input path="REFERRED_PROVIDER_NAME" class="standardInputText" requiredField="Y" maxlength="80" title="Provider Name" />  							
								</span>
								<div class="v_error REFERRED_PROVIDER_NAME "></div>
							</div>
							
							<div class="pL30  pT10 pB10">
								<span class="bold">Telephone number:   </span>
								<span class="">
									<form:input path="REFFERED_PROVIDER_TELEPHONE" class="standardInputMedText" maxlength="20" />  							
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
											<span class="valueHIV"> <form:input path="PT_INTERVIEW_DATE"  class="dPicker standardInputMedText"  maxlength="15"/> </span>
										</li>
										<li >
											<div id="everHadPrePosGroup"> 
												<span class="bold">Ever had a previous positive HIV test? </span><br/>
												<c:forEach items="${codedYesNoRefUnkownValues}" var="codedValue">
														<span> <form:radiobutton path="EVER_HAD_PREV_POS_TEST" value="${codedValue.ANS_CODE}" /> ${codedValue.ANS_DESC}  </span><br>
												</c:forEach>
											</div>
											<div class="v_error everHadPrePosGroup floatLeft"></div>
										</li>
										<li>
											<span class="labelHIV bold">Date of first POSITIVE HIV test (enter date of current test if it is first) </span>
											<span class="valueHIV"> <form:input path="FIRST_POS_HIV_DATE" class="dPicker standardInputMedText"  maxlength="15"/> </span>
										</li>
										<li style="padding-top:10px">
											<span class="labelHIV bold">Was the FIRST positive test anonymous? </span>
											<div class="valueHIV"> 
												<span> <form:radiobutton path="FIRST_POS_ANON" value="Y" /> Yes </span>
												<span> <form:radiobutton path="FIRST_POS_ANON" value="N" /> No </span>	
												<span> <form:radiobutton path="FIRST_POS_ANON" value="U" /> Unknown </span> 
											</div>
										</li>
										<li>
											<span>Location of First positive test(Select only one)<br>Type of facility (if known):</span><br>
												
											<div class="pT10 clear">
												<span class="bold"> Name of facility</span>
												<span class=""><form:input path="FIRST_POS_FAC_NAME" class="mL20 standardInputSmallText"  maxlength="100"/></span>
												<span class="mL20 bold"> State</span> 
												<span class=""><form:input path="FIRST_POS_FAC_STATE" class="mL20 standardInputSmallText"  maxlength="10"/></span>
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
													<span class="pLR"><form:radiobutton path="RSON_FIRST_POS_EXP" value="Y" /></span>
													<span> <form:radiobutton path="RSON_FIRST_POS_EXP" value="N" /></span>	
												</span>	<br>
												<span class="width70 left pB10 bold">Patient test routinely and it was time to test again</span>
												<span class="width30 left pB10"> 
													<span class="pLR"><form:radiobutton path="RSON_FIRST_POS_ROUTN" value="Y" /></span>
													<span> <form:radiobutton path="RSON_FIRST_POS_ROUTN" value="N" /></span>	
												</span>	<br>
												<span class="width70 left pB10 bold">Patient is checking to make sure he/she is HIV negative</span>
												<span class="width30 left pB10"> 
													<span class="pLR"><form:radiobutton path="RSON_FIRST_POS_CHKNEG" value="Y" /></span>
													<span> <form:radiobutton path="RSON_FIRST_POS_CHKNEG" value="N" /></span>	
												</span>	<br>
												<span class="width70 left pB10 bold">Required (court ordered, insurance, military, etc.)</span>
												<span class="width30 left pB10"> 
													<span class="pLR"><form:radiobutton path="RSON_FIRST_POS_REQD" value="Y" /></span>
													<span> <form:radiobutton path="RSON_FIRST_POS_REQD" value="N" /></span>	
												</span>	<br>
												<span class="width70 left pB10 bold">Notified that partner was HIV positive</span>
												<span class="width30 left pB10"> 
													<span class="pLR"><form:radiobutton path="RSON_FIRST_POS_PRTNR" value="Y" /></span>
													<span> <form:radiobutton path="RSON_FIRST_POS_PRTNR" value="N" /></span>	
												</span>	<br>
												<span class="width70 left pB10 bold">Some other reason <form:input path="RSON_FIRST_POS_OTHER_TEXT" class="dPicker ml10 standardInputMedText"  maxlength="75"/><br><span class="labelunderneath">(specify)</span></span>
												<span class="width30 left pB10"> 
													<span class="pLR"><form:radiobutton path="RSON_FIRST_POS_OTHER" value="Y" /></span>
													<span> <form:radiobutton path="RSON_FIRST_POS_OTHER" value="N" /></span>	
												</span>	<br>
											</span>
										</li>	
										<li class="clearBoth pT10">
											<span class="bold">Ever taken any antiretroviral (ARVs) medications?  (if no, skip to #9) </span>
											<div id="everTakenAntiretroviralGroup"> 
												<c:forEach items="${codedYesNoRefUnkownValues}" var="codedValue">
													<span> <form:radiobutton path="PT_ARV_EVER_TAKEN" value="${codedValue.ANS_CODE}" title="Ever taken any antiretroviral"/> ${codedValue.ANS_DESC}  </span><br>
												</c:forEach>
											</div>
											<div class="v_error  everTakenAntiretroviralGroup "></div>
											
										</li>
										<li class="clearBoth pT10">
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
													 					var ARV_PREVENPREP_MED_NAMES = '${nycPRFDocVO.ARV_PREVENPREP_MED_NAMES}';
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
													 					var POST_EXP_ARV_PEP_MED_NAMES = '${nycPRFDocVO.POST_EXP_ARV_PEP_MED_NAMES}';
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
														 <form:radiobutton path="ARV_TREATMENT" value="Y" /> Yes
														 <form:radiobutton path="ARV_TREATMENT" value="N" /> No 
													</span>	
													<div class="clear pT10 pB10">	
														<span class="pT10 pB10 bold"> Date first began:</span> 
														<span class="ml10 pB5"><form:input path="PT_ARV_START_DATE_TEXT" title="Antiretrovirals start Date"  class="dPicker mask standardInputSmallText" datevalidations="partialDateForAll"  maxlength="15"/></span>
														<span class="mL20 pB10 bold"> Date last use:</span>
														<span class="ml10 pB5"><form:input path="PT_ARV_END_DATE_TEXT" title="Antiretrovirals end Date" class=" dPicker mask standardInputSmallText" datevalidations="partialDateForAll"  maxlength="15"/></span>
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
											 					var PT_ARV_NAME = '${nycPRFDocVO.PT_ARV_NAME}';
											 					$('#PT_ARV_NAME').val(PT_ARV_NAME.split('|'));
															</script>
														</span>
													</div>
												</div>
										</li>					
										<li>
											<span class="bold">Has patient taken any antiretrovirals in the past six months? </span> <br>
											<div class=" pB20"> 
												<span class="width25 left"> <form:radiobutton path="ARV_6_MO" value="Y" /> Yes </span>
												<span class="width25 left"> <form:radiobutton path="ARV_6_MO" value="N" /> No </span>
												<span class="width25 left"> <form:radiobutton path="ARV_6_MO" value="R" /> Refused</span>
												<span class="width25 left"> <form:radiobutton path="ARV_6_MO" value="U" /> Unknown</span>
											</div>	
											<div class="clear pT10">
												<span class="pB10"> if Yes, which</span>
												<span class="mL20 pB5"><form:input path="ARV_6_MO_NAME" class="standardInputText"  maxlength="500"/></span><br>
											</div>
											<div class="clear pT10">	
												<span class="pT10 pB10"> if Yes, start date</span> 
												<span class="ml10 pB5"><form:input path="ARV_6_MO_START_DATE_TEXT" class="standardInputSmallText"  maxlength="15"/></span>
												<span class="mL20 pB10"> End date</span>
												<span class="ml10 pB5"><form:input path="ARV_6_MO_END_DATE_TEXT" class="standardInputSmallText"  maxlength="15"/></span>
											</div>
											<span class="pB5 pT10 inlineBlock bold"> Was the patient taking any antiretrovirals at the time of the current HIV test?</span> 
											<span class="pB10"> 
												<span> <form:radiobutton path="ARV_CURRENT_TEST" value="Y" /> Yes </span>
												<span> <form:radiobutton path="ARV_CURRENT_TEST" value="N" /> No </span>
											</span>	
										</li>
						
										<li>
											<span class="bold">Date of first HIV test ever (negative, indeterminate, or positive) </span>
											<span> <form:input path="FIRST_HIV_TEST_EVER_DATE_TEXT" class="dPicker standardInputSmallText ml10"  maxlength="15"/> </span>
										</li>	
										<li style="height:45px">
											<span class="bold">How many times did patient test for HIV in the 2 years before the FIRST positive test? (including the test on date under b.)</span>
											<div> 
												<span> <form:input path="NUMBER_TEST_2_YRS" class="dPicker standardInputTinyText"  maxlength="5"/>&nbsp;&nbsp;times</span>
											</div>
										</li> 
										<li>
											<span class="bold">Has patient ever tested negative for HIV before? </span>
											<div> 
												<span> <form:radiobutton path="EVER_TEST_NEG" value="N" /> No (if checked,got to section III) </span><br>
												<span> <form:radiobutton path="EVER_TEST_NEG" value="Y" /> Yes (If checked, answer j)</span><br>
												<span> <form:radiobutton path="EVER_TEST_NEG" value="R" /> Refused </span><br>
												<span> <form:radiobutton path="EVER_TEST_NEG" value="U" /> Unknown </span> 
											</div>
										</li>
										<li>
											<span class="bold">Date of last NEGATIVE HIV test <form:input path="LAST_NEG_DATE_TEXT" value=""  class="dPicker standardInputSmallText ml10"  maxlength="15"/><br>
											 Type of facility (if known):</span> <br>
											 <span> 
												<span class="bold"> <form:radiobutton path="LAST_NEG_FAC_TYPE" value="01" /> HIV Counseling and Testing services </span><br>
												<span style="width:33%; float:left">
													<span class="bold"> <form:radiobutton path="LAST_NEG_FAC_TYPE" value="02" /> TB clinic </span><br>
													<span class="bold"> <form:radiobutton path="LAST_NEG_FAC_TYPE" value="03" /> STD clinic </span><br>
													<span class="bold"> <form:radiobutton path="LAST_NEG_FAC_TYPE" value="04" /> Drug treatment clinic </span><br>
													<span class="bold"> <form:radiobutton path="LAST_NEG_FAC_TYPE" value="05" /> Family planning clinic </span><br>
													<span class="bold"> <form:radiobutton path="LAST_NEG_FAC_TYPE" value="06" /> Prenatal/OB clinic </span>
												</span>
												<span style="width:33%; float:left">
													<span class="bold"> <form:radiobutton path="LAST_NEG_FAC_TYPE" value="07" /> Emergency room </span><br>
													<span class="bold"> <form:radiobutton path="LAST_NEG_FAC_TYPE" value="08" /> Prison/Jail </span><br>
													<span class="bold"> <form:radiobutton path="LAST_NEG_FAC_TYPE" value="09" /> Private doctor </span><br>
													<span class="bold"> <form:radiobutton path="LAST_NEG_FAC_TYPE" value="10" /> Blood Bank </span><br>
													<span class="bold"> <form:radiobutton path="LAST_NEG_FAC_TYPE" value="11" /> Outreach/Mobile </span>
												</span>
												<span  style="width:33%; float:left">
													<span class="bold"> <form:radiobutton path="LAST_NEG_FAC_TYPE" value="12" /> Community health clinic </span><br>
													<span class="bold"> <form:radiobutton path="LAST_NEG_FAC_TYPE" value="13" /> Hospital </span><br>
													<span class="bold"> <form:radiobutton path="LAST_NEG_FAC_TYPE" value="14" /> Other </span><br>
												</span>
											</span>	
											<div class="pT10 clear">
												<span class="bold"> Name of facility</span>
												<span class=""><form:input path="LAST_NEG_FAC_NAME" class="mL20 standardInputSmallText"  maxlength="100"/></span>
												<span class="mL20 bold"> State</span> 
												<span class=""><form:input path="LAST_NEG_FAC_STATE" class="mL20 standardInputSmallText"  maxlength="10"/></span>	
											</div>						
										</li>
										<li>
											<span class="bold">Number of negative HIV tests </span>
											<span class="mL10"> 
												<span> <form:input path="NUM_NEG_HIV_TEST" class=" standardInputTinyText"  maxlength="5"/> </span> 
											</span>
										</li>
										<li>
											<span class="bold">Date assigned for field investigation </span>
											<span class="mL10"> 
												<span> <form:input path="FIELD_PROC_DATE" class="dPicker standardInputSmallText"  maxlength="10"/> </span> 
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
					<form:checkbox path="PS_REQUESTED" value="Y" maxlength="1"/> Provider Requests Health Department Partner Services	
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
								<fmt:formatDate value="${nycPRFDocVO.COMPLETED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedcompletedDate" />
								<span ><form:input path="COMPLETED_DATE" value="${theFormattedcompletedDate}" class="dPicker standardInputMedText"  maxlength="10"/></span> 
							</div>
									
							<div class="pL30 pT10" >
								<span class="personLabelDoc4 bold">Name</span>
								<span>
									<span><form:input path="PROV_LNAME" class="mL20 standardInputSmallText"  maxlength="80"/></span> 
									<span><form:input path="PROV_FNAME" class="standardInputSmallText"  maxlength="80"/></span> 
									<span><form:input path="PROV_MI" class="standardInputSmallText"  maxlength="1"/></span> 
									<span class="labelunderneath4"> Last</span>
									<span class="labelunderneath4"> First</span>
									<span class="labelunderneath4"> Middle</span>
								</span> 
							</div>	
							
							<div class="pL30 pT10">
								<span class="riskLabelField4 bold">Department</span>
								<span ><form:input path="PROV_DEPARTMENT" class="standardInputMedText" maxlength="40" /></span> 
							</div>				
							
							<div id="prfFacilityAddressSection" class="mT10 pB10">
									<div class="pL30 pT10 clear">
										<span class="riskLabelField4 bold">Street Address</span>
										<span ><form:input path="PROV_ADDRESS" class="standardInputText"  maxlength="62"/></span>
										<span class="labelunderneath3 dispAddressStdRaw"> Street (Std)</span> <br>
									</div>
									<div class="pT10">
										<span class="pL30 bold">City</span>
										<span class="pL30"><form:input path="PROV_CITY" class="standardInputSmallText1"  maxlength="30"/></span>
										<span class="pL10 bold">State</span>
										<span class="pL10"><form:input path="PROV_STATE" cssClass=" standardInputSmallText1 "  maxlength="2"/></span>
										<span class="pL10 bold"> Zip </span>
										<span class="pL30"><form:input path="PROV_ZIP" class="standardInputSmallText1" maxlength="5"/></span>										
										<span class="pL30 bold">County</span>
										<span class="pL10"> 
											<form:select path="PROV_COUNTY"  cssClass="standardInputSmallText1" > 
												<form:option value=""></form:option>
												<form:options items="${sessionScope.countyMap}"/>
											</form:select>
						 		         </span>  
									</div>	
									<div class="pT10">
										<span class="pL30 bold">Phone</span>
										<span class="pL10"><form:input path="PROV_PHONE" class="standardInputSmallText2"  maxlength="20"/></span>
										<span class="pL10 bold">Ext</span>
										<span class="pL10"><form:input path="PROV_EXT" cssClass=" standardInputSmallText1 "  maxlength="8"/></span>
										<span class="pL10 bold"> Email </span>
										<span class="pL10"><form:input path="PRO_EMAIL" class="standardInputMedText" maxlength="80"/></span>										
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
								
									<span><form:radiobutton path="FACILITY_TYPE" id="FACILITY_TYPE" class="facilityFields" title="Facility Type"  value="IH"/>Inpatient / Hospital</span><br/>
									<span><form:radiobutton path="FACILITY_TYPE" id="FACILITY_TYPE" class="facilityFields" title="Facility Type"  value="OH"/>Outpatient / HIV Clinic</span><br/>
									<span><form:radiobutton path="FACILITY_TYPE" id="FACILITY_TYPE" class="facilityFields" title="Facility Type"  value="OP"/>Outpatient / private physician's office</span><br/>
									<span><form:radiobutton path="FACILITY_TYPE" id="FACILITY_TYPE" class="facilityFields" title="Facility Type"  value="CT"/>Counseling / Testing Agency</span><br/>
									<span><form:radiobutton path="FACILITY_TYPE" id="FACILITY_TYPE" class="facilityFields" title="Facility Type"  value="SC"/>STD Clinic</span><br/>
									<span><form:radiobutton path="FACILITY_TYPE" id="FACILITY_TYPE" class="facilityFields" title="Facility Type"  value="ER"/>Emergency Room</span><br/>
									<span><form:radiobutton path="FACILITY_TYPE" id="FACILITY_TYPE" class="facilityFields" title="Facility Type"  value="PJ"/>Prison / Jail</span><br/>
									<span><form:radiobutton path="FACILITY_TYPE" id="FACILITY_TYPE" class="facilityFields" title="Facility Type"  value="OT"/>Other <form:input path="FACILITY_TYPE_OTHER_TEXT" class="standardInputSmallText3 facilityFields"  maxlength="100" title="Other Facility type"/></span>
								
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
								<span ><form:input path="CONTACT_LNAME" class="mL20 standardInputSmallText"  maxlength="25"/></span> 
								<span ><form:input path="CONTACT_FNAME" class="standardInputSmallText"  maxlength="20"/></span>
								<span ><form:input path="CONTACT_MNAME" class="standardInputSmallText"  maxlength="1"/></span>
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
								<form:input path="CONTACT_PHONE" class="pNumber standardInputSmallText2"  maxlength="25"/>
							</span>
						</div>
						
						<div class="pL30 pT10">
							<span class="bold">NYS License #:</span>
							<span ><form:input path="PROVIDER_NYS_LIC_NO" class="mL20 standardInputSmallText required"  maxlength="20"/></span> 
						</div>
						<div class="pL30 pT10">
							<span class="bold">National Provider Identifier (NPI) #:</span>
							<span ><form:input path="PROVIDER_NPI_NO" class="mL20 standardInputSmallText "  maxlength="10" title="NPI"/></span>	
						</div>
						<div class="pL30 pT10">
							<span class="bold">Provider Facility:</span>
							<span ><form:input path="PROV_FACILITY" class="mL20 standardInputText "  /></span>	
						</div>
						<div class="pL30 pT10">
							<span class="bold">Provider Title:</span>
							<span ><form:input path="PROV_TITLE" class="mL20 standardInputMedText "  /></span>	
						</div>
						<div class="pL30 pT10">
							<span class="bold">MD Print name:</span>
							<span ><form:input path="MDPRINTNAME" class="mL20 standardInputMedText "  /></span>	
						</div>
						<div class="pL30 pT10">
							<span class="bold">Print Date:</span>
							<span ><form:input path="PRINTED_DATE" class="mL20 standardInputSmallText "  /></span>	
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
			<c:when test="${userPermissions['90'] != null   && nycPRFDocVO.STATENO != null && nycPRFDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
					<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
			</c:when>
		</c:choose>

						
		<c:if test="${nycPRFDocVO.STATENO != null && nycPRFDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null && userPermissions['230'] != null}">						

			<input type="button" id="LaftFollowupButton" class="flagLaftFollowupDocument button rounded blue effect-3 displayNone"  value="Laft follow-up"  onclick="flagLaftFollowupDocument(${nycPRFDocVO.DOCUMENT_ID}, '${nycPRFDocVO.STATENO}')"/>						
		</c:if>				
						
		<input type="button" id="sButton" class="prfBtnclick button rounded blue effect-3" value="Submit" />
		<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />
		<span id="btnTxtMsg" style="display:none;"> Add / Cancel partner to submit </span>
	</div>
	
	<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />	
</form:form>															



