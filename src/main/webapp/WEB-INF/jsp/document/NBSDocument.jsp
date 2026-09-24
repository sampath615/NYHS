<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>
<script src="${staticPath}/js/docs/airsDoc.js?v=${buildDate}"></script>
<link href="${staticPath}/css/docs/airsForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/docs/commonForm.css" rel="stylesheet" type="text/css" />

<form:form modelAttribute="stdNBSDocVO" id="stdNBSDocVO" cssClass="documentFormVO documentDescription">
<form:errors path="*" cssClass="errorblock" element="div" />

        <div id="infantbirthDoc" style="background-color: transparent">
                
                <div style="padding-left:40%; " class="headingCls">
                               NBS DOCUMENT
                </div>
                
                <div class="pB30 pT20">
					<div  class="width100">
						<span class="frw25">
				            <span class="mL10 bold">Document ID: </span>
							<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
				        
			       		</span>
			         
			         </div>
				</div>
<!-- 		<div class="container pB10 w100 pT20"> -->
<!-- 			<div style="float: left; width: 40%"> -->
<!-- 				<div > -->
<%-- 					 <span class="ml10">STATE NO: <form:input path="STATENO" --%>
<%-- 							class="standardInputSmallText" maxlength="10" readonly="true" /> --%>
<!-- 					</span><br> -->
					
<!-- 				</div> -->
<!-- 			</div> -->
		

<!-- 			</div> -->
			<div
				style="float: left; width: 30%; text-align: right">
				
			</div>
		</div>
<%-- 			<jsp:include page="statenoDetails.jsp"></jsp:include> --%>
		
    	<div class="container"
			style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
			<div id="psDetailsHeadingDiv" class="divHeadingCls"
				style="background: #DDDDDD;">
				<span class=" mL2">Infant Details</span>
			</div>
			<div class="mLRTB pB20">
				<span class="fieldHolder25"> <span class="fLW35">
						First Name</span> <span class="fLW60"> <form:input path="INFANT_FNAME"
							class="standardInputSmallText" maxlength="15" />
				</span>
				</span> 
				</span> <span class="fieldHolder25 "> <span class="fLW35">
						Last Name</span> <span class="fLW60"> <form:input path="INFANT_LNAME"
							id="lName" title="Last Name"
							class="standardInputSmallText required" maxlength="20" />
				</span>
				</span> 
				
				<span class="fieldHolder25 "> <span class="fLW35">
						AKA</span> <span class="fLW60"> <form:input path="AKA"
							id="lName" title="AKA"
							class="standardInputSmallText required" maxlength="20" />
				</span>
				</span> 
				
			</div>
	
			<div class="mLRTB pB20">
				<span class="fieldHolder25 "> <span class="fLW35">
						Date of Birth</span> <span class="fLW60"> <form:input
							id="dobValidation" path="BIRTH_DATE"
							class="dPicker standardInputSmallText" maxlength="15" />
				</span>
				</span> <span class="fieldHolder25 "> <span class="fLW35">
						Time of Birth </span> <span class="fLW60"><form:input
							path="BIRTH_TIME" class="standardInputSmallText" maxlength="10" /></span>
				</span> <span class="fieldHolder25 "> <span class="fLW35">
						 Birth Weight
						  </span> <span class="fLW60"><form:input
							path="BIRTH_WEIGHT" class="standardInputSmallText" maxlength="10" /></span>
				</span>
			</div>
			
		</div>
		
		
		<div class="container" style="border:1px dotted; padding-bottom:5px;">
			<div style="float:left; width:25%; margin-left:20px; border-right:1px dotted; height: 156px">				
				<div>
					<spring:message code="label.race" />  
				</div>
				<div>
					<form:checkbox path="RACE_WHITE" value="1"  /> <spring:message code="label.raceWhite" />	
				</div>
				<div>
					<form:checkbox path="RACE_BLACK" value="1"/> African American
				</div>
				<div>
					<form:checkbox path="RACE_ASIAN" value="1" /> Asian
				</div>
<!-- 				<div> -->
<%-- 					<form:checkbox path="RACE_PACIFIC" value="1"/> Pacific Islander --%>
<!-- 				</div> -->
				<div>
					<form:checkbox path="RACE_INDIAN" value="1" /> American Indian/Alaskan Native
				</div>
				<div>
					<form:checkbox path="RACE_OTHER" value="1"/> <spring:message code="label.raceOther" /> 
				</div>
			</div>
			<div style="float:left; width:35% ; ">
				<div style="padding:5px 0 5px 10px;">
					<spring:message code="label.ethnicity" /> 
					(Hispanic or Latino) <br/>
										<form:radiobutton path="ETHNICITY" value="01" /> <spring:message code="label.yes" /> <br>
									   <form:radiobutton path="ETHNICITY" value="02" /> <spring:message code="label.no" /><br>
									   <form:radiobutton path="ETHNICITY" value="09" /> <spring:message code="label.unknown" />
				</div>
				<div style="border-top: dotted 1px">
					<div style="padding:10px 0 0 10px;">	
						<div class="floatLeft pR10" style="border-right: dotted 1px;">
							<div class=""> Current Gender </div>
							<div  class="">
								<div> <form:radiobutton path="SEX" value="M" class="editable"/> <spring:message code="label.male" /> </div>
								<div> <form:radiobutton path="SEX" value="F" class="editable"/> <spring:message code="label.female" /> </div>
								<div> <form:radiobutton path="SEX" value="U" class="editable"/>Unknown</div>		
							</div>
						</div>
 					</div>				
				</div>
			</div>
			
			<!--  Medical details -->
			
			<div style="float:left; width:35% ; padding-left:10px; padding-bottom: 12px; border-left:1px dotted;">

								
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Medical Record Number
					</div>
					<div style="float:left">
						<form:input path="MEDRECNO" id="MEDRECNO"  class="standardInputMedText mL5" maxlength="25" title="Medical Record Number"/>
					</div>
				</div>			
				<div class="mT5 container">
					<div style="float:left; width:45%">
						NYSID
					</div>
					<div style="float:left">
						<form:input path="NYS_ID" id="NYS_ID"  class="standardInputMedText mL5" maxlength="20" title="NYSID"/>
					</div>
				</div>	
				<div class="mT5 container">
					<div style="float:left; width:45%">
						LABID
					</div>
					<div style="float:left">
						<form:input path="LABID" id="LABID"  class="standardInputMedText mL5" maxlength="8" title="LABID"/>
					</div>
				</div>
				<div class="mT5 container">
					<div style="float:left; width:45%">
						PATIENTID
					</div>
					<div style="float:left">
						<form:input path="PATIENTID" id="PATIENTID"  class="standardInputMedText mL5" title="PATIENTID"/>
					</div>
				</div>
				<div class="mT5 container">
					<div style="float:left; width:45%">
						LABNO
					</div>
					<div style="float:left">
						<form:input path="LABNO" id="LABNO"  class="standardInputMedText mL5" maxlength="7" title="LABNO"/>
					</div>
				</div>

				<div class="mT5 container">
					<div style="float:left; width:45%">
						Collection Date
					</div>
					<div style="float:left">
						<form:input path="COLLECTION_DATE" id="COLLECTION_DATE"  class="standardInputMedText mL5" maxlength="9" title="Collection Date"/>
					</div>
				</div>
				
				
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Date Received
					</div>
					<div style="float:left">
						<form:input path="DATE_RECEIVED" id="DATE_RECEIVED"  class="standardInputMedText mL5" maxlength="10" title="Date Received"/>
					</div>
				</div>
				
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Date Reported
					</div>
					<div style="float:left">
						<form:input path="DATE_REPORTED" id="DATE_REPORTED"  class="standardInputMedText mL5" maxlength="10" title="Date Reported"/>
					</div>
				</div>		
				
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Twin Status
					</div>
					<div style="float:left">
						<form:input path="TWIN_STATUS" id="TWIN_STATUS"  class="standardInputMedText mL5" maxlength="10" title="Twin status"/>
					</div>
				</div>		
				
						
			</div>
		</div>
		
			
		<div class="container"
			style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
			<div id="psDetailsHeadingDiv" class="divHeadingCls"
				style="background: #DDDDDD;">
				<span class=" mL2">Mother Details</span>
			</div>
			
			<div class="mLRTB pB20">
					
				<span class="fieldHolder25"> <span class="fLW35">
						First Name</span> <span class="fLW60"> <form:input path="MOTHER_FNAME"
							class="standardInputSmallText" maxlength="15" />
				</span>
				</span> 
				<span class="fieldHolder25"> <span class="fLW35">
						Last Name</span> <span class="fLW60"> <form:input
							path="MOTHER_LNAME" class="standardInputSmallText"
							maxlength="20" />
				</span>
				</span> 
				<span class="fieldHolder25 "> <span class="fLW35">
						Date of Birth</span> <span class="fLW60"> <form:input
							id="dobValidation" path="MOTHER_DOB"
							class="dPicker standardInputSmallText" maxlength="15" />
				</span>
				</span> <span class="fieldHolder25 "> <span class="fLW35">
						Age </span> <span class="fLW60"><form:input
							path="MOTHER_AGE" class="standardInputSmallText" maxlength="10" /></span>
				</span> 
			</div>
			
			
			<div style="float:left; width:99%; margin-left:5px; margin-right:5px; margin-bottom:10px" class="mT5">
			
			<div class="b1" style="margin: 15px 5px 5px 5px" id="labDocumentPrsnAddressDtls">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"><form:input path="MOTHER_ADDRESS" id="MOTHER_ADDRESS" class="standardInputText widgetAddressFieldsReadonly newAddress" maxlength="100" title="Address"/>  </span>
	 	                    
	 	                    
	 	                    <span class="pL20">City</span>
	 	                    <span class="pL10"> <form:input path="MOTHER_CITY" id="MOTHER_CITY"  class="oAdrDlg standardInputSmallText newCity widgetAddressFieldsReadonly" maxlength="50" title="City"/></span>
	 	                    
	 	                    <span class="pL20">State </span>
	 	                    <span  class="pL10">
								<form:input path="MOTHER_STATE" id="MOTHER_STATE"   cssClass="oAdrDlg roundedBrdr newState standardInputVerySmall widgetAddressFieldsReadonly" maxlength="2" title="State"/> 
							</span>
	 	                    
	 	                    <span class="pL20">Zip </span>
	 	                    <span class="pL10"> 
	 	                        <form:input path="MOTHER_ZIP" id="MOTHER_ZIP"  class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5" title="Zip code"/> - 
	 	                    </span>
	 	                    <span class="pL20">County </span>
	 		                   <span class="pL10"> 
									<form:select path="MOTHER_COUNTY" id="MOTHER_COUNTY"   cssClass="oAdrDlg roundedBrdr newCounty widgetAddressFieldsReadonly" title="County"> 
										<form:option value=""></form:option>
										<form:options items="${sessionScope.countyMap}"/>
									</form:select>
		 		               </span>       	                    
	 	                    <br>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10">
 	                 <span class=""> Phone</span>
 					 <span class="pL10"> <form:input path="MOTHER_PHONE" id="MOTHER_PHONE"  class="pNumber standardInputSmallText phoneInputText" maxlength="20" title="Phone"/></span>
 					 
 					 <span class="pL20">  Region</span>
 					 <span class="pL10"> <form:input path="MOTHER_REGION" id="MOTHER_REGION"  class="standardInputSmallText newCountry" maxlength="106" title="Mother Region"/></span>
 	                    
 	            </div>
 	        </div>					
			
		</div>
	</div>
	
	
	<div style="padding-bottom:5px; border:1px dotted;" class="container" id="labDocumentFacilityAddressDtls">
			<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class="mL2">Facility Details</span>
			</div>
			<div style="padding-left:10px; padding-top:2px;" class="providerDetails">
				<span>Facility Name</span> <span class="pL10"><form:input path="FACILITY_NAME"  id="FACILITY_NAME" class="editable standardInputText facilityFields" maxlength="90" title="Facility Name"/></span>
				<span class="pL20">PFI</span> <span class="pL10"><form:input path="PFI"  id="PFI" class="standardInputSmallText facilityFields" maxlength="10" title="Facility Name"/></span>
			</div>	
			
			<div class="b1 facilityDetails" style="margin: 15px 5px 5px 5px">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"><form:input path="FACILITY_ADDRESS" id="FACILITY_ADDRESS" class="oAdrDlg standardInputText newAddress widgetAddressFieldsReadonly facilityFields" maxlength="106" title="Facility Address"/></span>	 	                    
	 	                    
	 	                    <span class="pL20">City</span>
	 	                    <span class="pL10"> <form:input path="FACILITY_CITY" id="FACILITY_CITY" class="editable oAdrDlg standardInputSmallText newCity widgetAddressFieldsReadonly facilityFields" maxlength="30" title="Facility City"/> </span>
	 	                    
	 	                    <span class="pL20">State </span>
	 	                    <span  class="pL10">
								 <form:input path="FACILITY_STATE" id="FACILITY_STATE" class="editable oAdrDlg standardInputVerySmall  newState wS40 widgetAddressFieldsReadonly facilityFields" maxlength="2" title="Facility State"/>
							</span>
	 	                    
	 	                    <span class="pL20">Zip </span>
	 	                    <span class="pL10"> 
	 	                        <form:input path="FACILITY_ZIP" id="FACILITY_ZIP" class="editable oAdrDlg nOnly  standardInputSmallText1 newZip widgetAddressFieldsReadonly facilityFields" maxlength="5" title="Facility Zip"/> - 
	 	                    </span>
	 	                    <span class="pL20">County </span>
	 		                   <span class="pL10"> 
									<form:select  id="FACILITY_COUNTY" tabindex="8" path="FACILITY_COUNTY" class="editable oAdrDlg standardInputMedText newCounty wC100 widgetAddressFieldsReadonly facilityFields" readonly="" title="Facility County">
								        <form:option value="" label="" />
										<form:options items="${sessionScope.countyMap}"/>
								    </form:select>
		 		               </span>       	                    
	 	                    <br>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10">
 	               	                 
 	                 <span class=""> Phone</span>
 					 <span class="pL10"> <form:input path="FACILITY_PHONE"  id="FACILITY_PHONE" class="editable pNumber standardInputSmallText3 phoneInputText facilityFields" maxlength="20"  title="Facility Phone"/></span>
 					 
 					 <span class="pL20"> Region</span>
 					 <span class="pL10"> <form:input path="FACILITY_REGION"  id="FACILITY_REGION" class="standardInputSmallText3 readonly" maxlength="20" title="Facility Region"/></span>
 	                    
 	            </div>
 	        </div>						
		</div>
		
		
		

</form:form>           
                
           
                  


