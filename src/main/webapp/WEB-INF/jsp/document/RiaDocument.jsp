<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page
	import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>
<link href="${staticPath}/css/docs/dthForm.css?v=${buildDate}"
	rel="stylesheet" type="text/css" />
<script src="${staticPath}/js/docs/docViewers.js?v=${buildDate}"></script>
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

<form:form modelAttribute="stdRiaDocVO" id="stdRiaDocVO"
	cssClass="documentDescription documentFormVO">
	<form:errors path="*" cssClass="errorblock" element="div" />

	<div id="riaDocContent" style="background-color: transparent">

		<div style="padding-left: 43%;" class="headingCls">RIA DOCUMENT
		</div>

		<div class="container pB10 w100 pT10">
			<div style="float: left; width: 40%">
				<div>
					<span class="ml10 bold"> RIA DocId
					</span> <span class="ml10"><form:input path="DOCUMENT_ID"
							class="standardInputSmallText readonly" maxlength="10"
							readonly="true" /> </span>
				</div>
			</div>
				
				
            <div style="float:left; width: 33%">
            	    <div>
	                <span class="mL10 bold">	STATENO </span>
	                <span class="mL10">${stdRiaDocVO.CURR_NYEHMS_STATENO}</span>
					 <form:hidden path="CURR_NYEHMS_STATENO"/>
	            </div>
            </div>
            
            <c:if test="${stdRiaDocVO.CASE_CLOSURE_CD != null && stdRiaDocVO.CASE_CLOSURE_CD != ''}">
            <div style="float:left; width: 23%">
            	    <div>
	                <span class="mL10 bold">	Case Closure Code  </span>
	                <span class="mL10">
	                	<c:set var="refColumnData" value="${riaDropDownList.CASE_CLOSURE_CD}"/>
						<form:select path="CASE_CLOSURE_CD"
								title=" Case Closure Code"
								class="standardInputMedText ">
							<form:option value="">Select</form:option>
							<c:forEach items="${refColumnData}" var="diagnosisSettingData">
									<form:option value="${diagnosisSettingData.LOOKUP_CD}">${diagnosisSettingData.LOOKUP_NAME}</form:option>
								</c:forEach>
						</form:select>
	                
	                </span>
	            </div>
            </div>
            </c:if>
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
				<div class="fieldHolder25">
					<span class="fLW35 bold"> First Name</span> <span class="fLW60">
						<form:input path="FIRST_NAME" 
							title="First Name" class="standardInputSmallText required" value="${riaDocNyehmsData.FIRST_NAME}" maxlength="50" />
					</span>
					<div class="v_error mR10 RIA_FNAME "></div>
				</div>
				<div class="fieldHolder25 ">
					<span class="fLW35 bold"> Last Name</span> <span class="fLW60"> <form:input
							path="LAST_NAME" title="Last Name"
							class="standardInputSmallText required" maxlength="50" />
					</span>
					<div class="v_error mR10 RIA_LNAME "></div>
				</div>
				
				<div class="fieldHolder25">

						<span class=" fLW35 bold"> SSN </span> 
						
						<span
							class="fLW60"> 
						<form:input path="SSN" id="SSN" class="standardInputMedText" title="SSN "/>
						</span>
	
					</div>
					
					<div class="fieldHolder25">
						<span class="fLW35 bold"> Date of birth</span> <span class="fLW50">
							<form:input path="DOB_TEXT" id="DOB_TEXT" title="Date of birth"
								class="standardInputSmallText mask dPicker " maxlength="10" />
						</span>
						<div class="v_error mR10 DOB_TEXT "></div>
					</div>
					

			</div>
			
			
			
			<div  class="mLRTB pB20 clearBoth">
					
					
					
					<div class="fieldHolder25">
	
							<span class=" fLW35 bold"> Country of birth </span> 
							
							<span
								class="fLW50"> 
							<form:select path="BIRTH_COUNTRY" id="BIRTH_COUNTRY" class="standardInputSmallText" title=" Country of birth ">
								<form:option value=""></form:option>
								<form:options items="${codedCountries}"/>
								<form:option value="OTH">Other</form:option>
								<form:option value="UNK">Unknown</form:option>
							</form:select>
							</span>
		
						</div>
					<div class="fieldHolder25">
						<span class=" fLW35 bold"> Date of Death </span> 
						
						<span
							class="fLW50"> <form:input path="DOD_TEXT"
								id="DOD_TEXT" title="Date of Death"
								class="standardInputSmallText mask dPicker " />
						</span>
						<div class="v_error mR10 DOD_TEXT "></div>
					</div>
					<div class="fieldHolder25">

						<span class=" fLW35 bold"> Source of death </span> 
						
						<span
							class="fLW50"> 
							<c:set var="refColumnData" value="${riaDropDownList.DEATH_SOURCE}"/>
							
						<form:select path="DEATH_SOURCE" id="DEATH_SOURCE" class="standardInputMedText" title=" Source of death " dependentField="DEATH_SOURCE_FIELDS"  onchange="showOtherFields(this)">
							<form:option value=""></form:option>
							<c:forEach items="${refColumnData}" var="codedData">
								<form:option value="${codedData.LOOKUP_CD}">${codedData.LOOKUP_NAME}</form:option>
							</c:forEach>
						</form:select>
						</span>
	
					</div>
					
					<div class="fieldHolder25" id="DEATH_SOURCE_FIELDS">
						<span class="fLW35 bold"> Other Source</span> <span class="fLW50">
							<form:input path="DEATH_SOURCE_OTHER_TEXT" title="Souce of Death other"
								class="standardInputSmallText" />
						</span>
					</div>
			</div>


		</div>
				
		<div class="container" style="border:1px dotted; padding-bottom:5px;">
			<div style="float:left; width:25%; margin-left:20px; border-right:1px dotted; height: 215px">	
			
				<div>			
					<div>
						<span class="bold">Race</span>  
					</div>
					<div>
						<form:checkbox path="RACE_WHITE" id="RACE_WHITE" value="1" title="Race White" /> White	
					</div>
					<div>
						<form:checkbox path="RACE_BLACK" id="RACE_BLACK" value="1" title="Race Black" /> Black
					</div>
					<div>
						<form:checkbox path="RACE_ASIAN" id="RACE_ASIAN" value="1" title="Race Asian" /> Asian
					</div>
					<div>
						<form:checkbox path="RACE_PACIFIC" id="RACE_PACIFIC" value="1" title="Race Pacific Islander" /> Pacific Islander
					</div>
					<div>
						<form:checkbox path="RACE_INDIAN" id="RACE_INDIAN" value="1" title="Race American Indian/Alaskan Native" /> American Indian/Alaskan Native
					</div>
				</div>
				<div style="border-top: dotted 1px" class="mT10 pT10">
					<span class="bold">Ethnicity 
					(Hispanic or Latino) </span><br>
										<form:radiobutton  path="ETHNICITY" id="ETHNICITY" value="01" title="Ethinicity Yes" />  Yes <br>
									   <form:radiobutton  path="ETHNICITY"  id="ETHNICITY" value="02" title="Ethinicity No" />No<br>
									   <form:radiobutton path="ETHNICITY"  id="ETHNICITY" value="09" title="Ethinicity Unknown" /> Unknown
				</div>
				
			</div>
			<div style="float:left; width:35% ; ">
				<div style="padding:5px 0 5px 10px;">
					<span class="bold">Birth Sex </span><br>
									<span class="mL5"><form:radiobutton   path="BIRTH_SEX"  id="BIRTH_SEX" value="M" title="Birth Sex : Male" /> Male </span><br/>
									<span class="mL5"><form:radiobutton   path="BIRTH_SEX"  id="BIRTH_SEX" value="F" title="Birth Sex : Female" /> Female </span><br/>
									<span class="mL5"><form:radiobutton   path="BIRTH_SEX"  id="BIRTH_SEX" value="U" title="Birth Sex : Unknown" /> Unknown </span>
				</div>
				<div style="border-top: dotted 1px">
					<div style="padding:10px 0 0 10px;">	
						<div class="floatLeft pR10" style="border-right: dotted 1px;">
							<div class=""><span class="bold"> Current Gender </span></div>
							<div class="">
								<div> <form:radiobutton   path="CURRENT_GENDER" id="CURRENT_GENDER" class="genderCode" value="M" title="Current Gender : Male" /> Male </div>
								<div> <form:radiobutton   path="CURRENT_GENDER" id="CURRENT_GENDER" class="genderCode" value="F" title="Current Gender : Female" /> Female </div>
								<div> <form:radiobutton   path="CURRENT_GENDER" id="CURRENT_GENDER" class="genderCode" value="U" title="Current Gender : Unknown" />Unknown</div>		
							</div>
						</div>
						<div class="floatLeft pL10">
							<div class=""> <span class="bold">Transgender </span></div>
							<div class="">
								<div> <form:radiobutton   path="CURRENT_GENDER" id="CURRENT_GENDER" class="genderCode" value="X" title="Current Gender : MTF" /> MTF </div>		
								<div> <form:radiobutton   path="CURRENT_GENDER" id="CURRENT_GENDER" class="genderCode" value="Y" title="Current Gender : FTM" /> FTM </div>		
							</div>
						</div> 
 					</div>				
				</div>
			</div>
			

						
						
			<!--  Medical details -->
			
			<div style="float:left; width:35% ; padding-left:10px; padding-bottom: 12px; border-left:1px dotted; min-height:210px">

								
				<div class="mT5 container">
					<div class="flw30">
						<span class="bold">First PRF Date</span>
					</div>
					<div class="flw70">
						<form:input path="FIRST_PRF_DATE"
								id="FIRST_PRF_DATE" title=" First PRF Date"
								class="standardInputSmallText2  mask dPicker " />
					</div>
					<div class="v_error mR10 FIRST_PRF_DATE "></div>
					
				</div>
				<div class="mT5 container">
					<div class="flw30">
					<span class="bold">	AIRS Doc</span> 
					</div>
					<div class="flw70">
						<form:select path="AIRS_DOC"
							class="standardInputSmallText" 
							 >
							 <form:option value=""></form:option>
							 <form:option value="Y">Yes</form:option>
							 <form:option value="N">No</form:option>
							 
							 </form:select>
					</div>
				</div>			
				<div class="mT5 container">
					<div class="flw30">
						<span class="bold">AIRS Facility</span>
					</div>
					<div class="flw70">
						<form:input path="AIRS_FACILITY"
							class="standardInputText" 
							 />
					</div>
				</div>
				<div class="mT5 container">
					<div class="flw30">
						<span class="bold">AIRS TC_ID</span>
					</div>
					<div class="flw70">
						<form:input path="AIRS_TC_ID"
							class="standardInputMedText" 
							 />
					</div>
				</div>
				
				<div class="mT5 container">
					<div class="flw30">
						<span class="bold">Record Search</span>
					</div>
					<div class="flw70">
						<form:select path="RECORD_SEARCH"
							class="standardInputSmallText" 
							 >
							 <form:option value=""></form:option>
							 <form:option value="Y">Yes</form:option>
							 <form:option value="N">No</form:option>
							 
							 </form:select>
					</div>
				</div>
				<div class="mT5 container">
					<div class="flw30">
					<span class="bold">	AHI Flag</span>
					</div>
					<div class="flw70">
						<form:select path="AHI_FLAG"
							class="standardInputSmallText"  title="Indicator if patient is flagged as AHI"
							 >
							<form:option value=""></form:option>
							<form:option value="Y">Yes</form:option>
							<form:option value="N">No</form:option>
							
							</form:select>
					</div>
				</div>
					
			</div>
		</div>
		
		
		<div class="container"
			style="border: 1px dotted; width: 100%; padding-bottom: 5px;"
			id="labDocumentProviderAddressDtls">
			<div id="prvDetailsHeadingDiv" class="divHeadingCls"
				style="background: #DDDDDD;">
				<span class=" mL2">Patient address at diagnosis</span>
			</div>
			
			<div class="mLRTB pB20 clearBoth">
			
				<div class="b1 personAddressDetails" style="margin: 15px 5px 5px 5px" id="personAddressDetails">
				<div class="personInfo pTRBL10">
					<span class="personLabelDoc bold"> Address </span> <span> <span
						class="pL10"><form:input path="PERSON_ADDRESS"
								id="PERSON_ADDRESS"
								class="oAdrDlg standardInputText newAddress widgetAddressFieldsReadonly "
								maxlength="106" title="Patient street address at diagnosis" /></span> <span class="pL20 bold">City</span>
						<span class="pL10"> <form:input path="PERSON_CITY"
								id="PERSON_CITY"
								class="editable oAdrDlg standardInputSmallText  newCity widgetAddressFieldsReadonly "
								maxlength="30" title="Patient city address at diagnosi" />
					</span> <span class="pL20 bold">State </span> <span class="pL10"> <form:input
								path="PERSON_STATE" id="PERSON_STATE"
								class="editable oAdrDlg standardInputVerySmall  newState wS40 widgetAddressFieldsReadonly "
								maxlength="2" title="Patient state at diagnosis" />
					</span> <span class="pL20 bold">Zip </span> <span class="pL10"> <form:input
								path="PERSON_ZIP" id="PERSON_ZIP"
								class="editable oAdrDlg nOnly  standardInputSmallText1 newZip widgetAddressFieldsReadonly "
								maxlength="5" title="Patient ZIP code at diagnosis" /> -
					</span>
					<span class="pL20 bold">County </span> <span class="pL10"> <form:select
								path="PERSON_COUNTY" id="PERSON_COUNTY"
								class="editable oAdrDlg nOnly  standardInputSmallText newCounty widgetAddressFieldsReadonly "
								maxlength="5" title="Patient County code at diagnosis" >
								<form:option value="" label="" />
								<form:options items="${sessionScope.countyMap}" />
							</form:select> 
							
					</span>
					<span class="pL20 bold">
					Address Date</span>
					<span class="pL10">
						<form:input path="ADDRESS_DATE" id="ADDRESS_DATE" title="Address Date" class="standardInputSmallText1 mask dPicker "/>
					</span>

					</span>
					<span style="float: right;" class="mr50">
							<a href="javascript:openAddressWidget('personAddressDetails');" class="inlineEditButton displayNone editAddress">Edit</a>
						 </span>  
						 <div class="v_error mR10 ADDRESS_DATE "></div>
				</div>

			</div>
			
			
			</div>

			
		</div>
		
		
		<jsp:include page="ria/RiaProviderFollowupData.jsp"></jsp:include>		


		<div class="container"
			style="border: 1px dotted; width: 100%; padding-bottom: 5px;"
			id="labDocumentProviderAddressDtls">
			<div id="prvDetailsHeadingDiv" class="divHeadingCls"
				style="background: #DDDDDD;">
				<span class=" mL2">Diagnosing Provider</span>
			</div>
			<div style="margin-top: 2px; margin-left: 5px; margin-right: 5px;"
				class="providerDetails">
				<span class="bold"> Provider Name</span><span>
					<form:input path="INITIAL_PROVNAME" id="INITIAL_PROVNAME"
						class="standardInputText providerFields" maxlength="80"
						title="Provider Name" />
				</span>

			</div>

			<div class="b1 providerDetails" style="margin: 15px 5px 5px 5px" id="initProvDetails">
				<div class="personInfo pTRBL10">
					<span class="personLabelDoc bold"> Address </span> <span> <span
						class="pL10"><form:input path="INITIAL_PROV_ADDRESS"
								id="INITIAL_PROV_ADDRESS"
								class="oAdrDlg standardInputText newAddress widgetAddressFieldsReadonly providerFields"
								maxlength="106" title="Provider Address" /></span> <span class="pL20 bold">City</span>
						<span class="pL10"> <form:input path="INITIAL_PROV_CITY"
								id="INITIAL_PROV_CITY"
								class="editable oAdrDlg standardInputSmallText  newCity widgetAddressFieldsReadonly providerFields"
								maxlength="30" title="Provider City" />
					</span> <span class="pL20 bold">State </span> <span class="pL10"> <form:input
								path="INITIAL_PROV_STATE" id="INITIAL_PROV_STATE"
								class="editable oAdrDlg standardInputVerySmall  newState wS40 widgetAddressFieldsReadonly providerFields"
								maxlength="2" title="Provider State" />
					</span> <span class="pL20 bold">Zip </span> <span class="pL10"> <form:input
								path="INITIAL_PROV_ZIP" id="INITIAL_PROV_ZIP"
								class="editable oAdrDlg nOnly  standardInputSmallText1 newZip widgetAddressFieldsReadonly providerFields"
								maxlength="5" title="Provider Zip" /> -
					</span> <span class="pL20 bold">County </span> <span class="pL10"> <form:select
								tabindex="8" path="INITIAL_PROV_COUNTY"
								class="editable oAdrDlg standardInputMedText newCounty wC100 widgetAddressFieldsReadonly providerFields"
								readonly="" title="Provider County">
								<form:option value="" label="" />
								<form:options items="${sessionScope.countyMap}" />
							</form:select>
					</span>
					</span>
					<span style="float: right;" class="mr50">
							<a href="javascript:openAddressWidget('initProvDetails');" class="inlineEditButton displayNone editAddress">Edit</a>
						 </span>  
				</div>

			</div>
						
		</div>
				
		<div class="container"
			style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
			<div id="psDetailsHeadingDiv" class="divHeadingCls"
				style="background: #DDDDDD;">
				<span class=" mL2">Partner Services Details</span>
			</div>
			<div class="mLRTB pB20">
			
				<div class="flw20">
					<span class="fLW35 bold"> CDESSCASEKEY</span> <span class="fLW60">
						<form:input id="CDESSCASEKEY" path="CDESSCASEKEY"
 							title="CDESSCASEKEY" class="standardInputSmallText" /> 
					</span>
					<div class="v_error mR10 CDESSCASEKEY "></div>
				</div>
				
				<div class="fieldHolder25">
					<span class="fLW45 bold"> CDESS sent Date  </span> 
						<span
							class="fLW50">  <form:input path="PS_MESSAGE_SENT_DATE"
								id="PS_MESSAGE_SENT_DATE" title=" CDESS sent Date "
								class="standardInputMedText mask dPicker " />
						</span>
						<div class="v_error mR10 PS_MESSAGE_SENT_DATE "></div>
					</div>
					<div class="fieldHolder25">
					<span class="fLW45 bold"> DIS Number  </span> 
						<span
							class="fLW50">  <form:input path="DISNUMBER"
								id="DISNUMBER" title=" DISNUMBER "
								class="standardInputMedText  " />
						</span>
					</div>
			</div>
			<div class="mLRTB pB20 clearBoth">
				<div class="flw20">
					<span class="fLW35 bold"> Worker assigned</span>
					<span class="fLW60"> <form:input path="PS_WORKER_ASSIGNED"
							id="PS_WORKER_ASSIGNED"
							title="Worker assigned"
							class="standardInputSmallText " maxlength="10" />
					</span>
				</div>
				<div class="fLW40">
					<span class="flw25 bold"> Closure disposition</span> <span
						class="fLW75"> <form:input path="PS_CLOSURE_DISPO"
							id="PS_CLOSURE_DISPO" title="PS closure disposition"
							class="standardInputText " maxlength="100" />
					</span>
				</div>
				<div class="flw15">
					<span class="fLW35 bold"> Date Closed </span> <span
						class="fLW60"> <form:input path="PS_CLOSURE_DATE"
							id="PS_CLOSURE_DATE" title="Date PS closed this case"
							class="standardInputSmallText1 mask dPicker " maxlength="10" />
					</span>
					<div class="v_error mR10 PS_CLOSURE_DATE "></div>
				</div>
				
				<div class="flw25">
					<span class="fLW35 bold">Last updated Date </span> 
						<span
							class="fLW60">  <form:input path="PS_LAST_UPDATE_DATE"
								 title=" Last updated Date"
								class="standardInputSmallText mask dPicker " />
						</span>
						<div class="v_error mR10 PS_LAST_UPDATE_DATE "></div>
					</div>
			</div>
			
		</div>

		<div class="container"
			style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
			<div id="psDetailsHeadingDiv" class="divHeadingCls"
				style="background: #DDDDDD;">
				<span class=" mL2">RIA Details</span>
			</div>
			<div class="mLRTB pB20">
			<div class="fieldHolder30">
					<span class="fLW35 bold"> Identified Date</span> 
					<span
						class="fLW65">  <form:input path="RIA_FLAGGED_DATE"
							id="RIA_FLAGGED_DATE" title="RIA Identified Date"
							class="standardInputMedText mask dPicker " />
					</span>
					<div class="v_error mR10 RIA_FLAGGED_DATE "></div>
				</div>
				<div  class="fieldHolder35">
					<span class="fLW35 bold"> Lab DocId
					</span> <span class="fLW65"><form:input path="LAB_DOC_ID"
							class="standardInputSmallText2 readonly" maxlength="10"
							readonly="true" />  <a class="mL10 loadDocumentBtn nonEditedDocument" title="${param.labDocType}" alt=" " documentid="${param.labDocId }" onclick="loadDocumentDlg(${param.labDocId }, '${param.labDocType}')">${param.labDocType}</a></span>
									
				</div>
				<div class="fieldHolder30">
				
					<span class="fLW35 bold">  CDC Soundex Date</span> <span
						class="fLW65"> <form:input path="SOUNDEX_CHECK_DATE"
							id="SOUNDEX_CHECK_DATE" title="CDC Soundex Date"
							class="standardInputSmallText2 mask dPicker " maxlength="10" />
					</span>
					<div class="v_error mR10 SOUNDEX_CHECK_DATE "></div>
				</div>
				
				
				
			</div>
			<div class="mLRTB pB20 clearBoth">
			<div class="fieldHolder30">

					<span class=" fLW35 bold"> Initial Collection Date </span> 
					
					<span
						class="fLW65"> <form:input path="INITIAL_COLLECTION_DATE"
							id="INITIAL_COLLECTION_DATE" title="Initial Collection Date"
							class="standardInputMedText mask dPicker " />
					</span>
					<div class="v_error mR10 INITIAL_COLLECTION_DATE "></div>

				</div>
				<div class="fieldHolder35">
					<span class="fLW35 bold">Date of move to NYS </span> 
						<span
							class="fLW65">  <form:input path="MOVED_TO_NY_DATE"
								id="MOVED_TO_NY_DATE" title=" Date of move to NYS"
								class="standardInputSmallText2  mask dPicker " />
						</span>
						<div class="v_error mR10 MOVED_TO_NY_DATE "></div>
					</div>
				<div class="fieldHolder30">
				<span class="fLW35 bold">Date of move from NYS </span> 
					<span
						class="fLW65">  <form:input path="MOVED_FROM_NY_DATE"
							id="MOVED_FROM_NY_DATE" title=" Date of move from NYS"
							class="standardInputSmallText2  mask dPicker " />
							
					</span>
					<div class="v_error mR10 MOVED_FROM_NY_DATE "></div>
				</div>
				</div>
				
				
				<div class="mLRTB pB20 clearBoth">
					<div class="fLW30">
				<span class="fLW35 bold">RIA Eligible </span> 
					<span
						class="fLW65"><form:select path="RIA_ELIGIBLE" class="standardInputSmallText reqToClose" onchange="showRiaEligibleFields(this)" title="RIA Eligiblity ">
						
							<form:option value=""></form:option>
							<form:option value="Y">Yes</form:option>
							<form:option value="N">No</form:option>
						</form:select>
						</span>
						<div class="v_error mR10 RIA_ELIGIBLE "></div>
				</div>
				<div id="RIA_ELIGIBLITY_FIELDS">
					<div class="fieldHolder35">
					
						<span class="fLW35 bold">  Eligibility Date</span> <span
							class="fLW65"> <form:input path="RIA_ELIGIBILITY_DATE"
								id="RIA_ELIGIBILITY_DATE" title="Eligibility Date"
								class="standardInputSmallText mask dPicker" maxlength="10" />
						</span>
						<div class="v_error mR10 RIA_ELIGIBILITY_DATE "></div>
					</div>
					<div class="fieldHolder30">
						<span  class="fLW35 bold"> Eligibility flagged by</span> <span
							class="fLW65"> <form:input path="RIA_ELIGIBILITY_UID"
								id="RIA_ELIGIBILITY_UID"
								title="Eligibility flagged by"
								class="standardInputSmallText " maxlength="10" />
						</span>
						<div class="v_error mR10 RIA_ELIGIBILITY_UID "></div>
					</div>
				</div>
				</div>
				<div class="mLRTB pB20 clearBoth" id="RIA_INELIGIBLE_FIELDS">
					<div class="flw100" id="RIA_INELIGIBLE_REASONS">
					<span class=" bold">Reason for ineligibility </span> 
						<span
							class="mL10"> 
							
							<form:checkbox path="NOT_HIV_FLAG" title="Not HIV" value="Y"/>Not HIV</span>
								<span class="mL10"><form:checkbox path="OOJ_CASE_FLAG" title="OOJ Case" value="Y"/>OOJ Case</span>
								<span class="mL10"><form:checkbox path="OOJ_PROVIDER_FLAG" title="OOJ Provider" value="Y"/>OOJ Provider</span>
								<span class="mL10"><form:checkbox path="NON_PROMPT_FLAG" title="Non Promot" value="Y" />Non Prompt</span>
								<span class="mL10"><form:checkbox path="NDVL_AT_DX" title="Non Promot" value="Y" />NDVL at time of dx</span>
								<span class="mL10"><form:checkbox path="FEDERAL_FACILITY" title="Non Promot" value="Y" />Federal Facility</span>
								<span class="mL10"><form:checkbox path="PT_ON_PREP_FLAG" title="PREP" value="Y" />PrEP</span>
						 		<span class="mL10"><form:checkbox path="DECEASED" title="Non Promot" value="Y" />Deceased</span>
						 		<span class="mL10"><form:checkbox path="PERINATAL" title="Perinatal" value="Y" />Perinatal</span>
								<span class="mL10"><form:checkbox path="PKP_FLAG" title="PKP" value="Y" onchange="showPKPFields(this)"/>PKP</span>
						 
					<div class="v_error RIA_INELIGIBLE_REASONS"></div>		
					</div>
					
					
					
				</div>
				<div id="PKP_FIELDS" class="mLRTB pB20 clearBoth">
						<div id="PKP_OTHER_INFO">
							<div class="fLW30">
		
								<span class="fLW35 bold"> PKP Country </span> 
								
								<span
									class="fLW65"> <form:select path="PKP_DX_COUNTRY" id="PKP_DX_COUNTRY" class="standardInputSmallText" title=" PKP Country " onchange="updatePKPStateField(this)">
										<form:option value=""></form:option>
										<form:options items="${codedCountries}"/>
										<form:option value="USA">USA</form:option>
										<form:option value="OTH">Other</form:option>
										<form:option value="UNK">Unknown</form:option>
									</form:select>
								</span>
			
							</div>
							<div class="fLW35">
		
								<span class="fLW35  bold"> PKP Date </span> 
								
								<span
									class="fLW65"> 
									
									
									<form:input path="PKP_DX_DATE" id="PKP_DX_DATE"
		 							title="PKP Date" class="standardInputSmallText2 mask dPicker " /> 
								</span>
								<div class="v_error mR10 PKP_DX_DATE "></div>
							</div>
							<div class="fLW30">
		
								<span class="fLW35  bold"> PKP State </span> 
								
								<span
									class="fLW65"> 
									
									
									<form:select path="PKP_DX_STATE" id="PKP_DX_STATE"
		 							title="PKP State Code" class="standardInputSmallText "> 
		 							<form:option value="">Select</form:option> 
		 							<c:forEach items="${stateCodes}" var="stateCode"> 
		 								<form:option value="${stateCode.lookup_cd}" label="${stateCode.lookup_name}" name="${stateCode.lookup_name}"></form:option> 
		 							</c:forEach> 
		 						</form:select> 
								</span>
			
							</div>
							
							
							
							
						</div>
					</div>
				
				<div class="mLRTB pB20 clearBoth">
							<div class="fLW30" >
		
								<span class="fLW35 bold"> Received HIV Care OOS </span> 
								
								<span
									class="fLW65"> 
									
									
									<form:select path="RECV_CARE_OOS" id="RECV_CARE_OOS"
		 							title="Received HIV Care OOS" class="standardInputSmallText1 " dependentField="OOS_DEPENDENT_FIELDS"  onchange="showDependentYesFields(this)"> 
		 								<form:option value="">Select</form:option> 
		 								<form:option value="Y">Yes</form:option> 
		 								<form:option value="N">No</form:option>
		 						</form:select> 
								</span>
			
							</div>
							<div id="OOS_DEPENDENT_FIELDS">
							<div class="fLW30" >
		
								<span class="fLW35  bold"> State Received Care </span> 
								
								<span
									class="fLW65"> 
									
									
									<form:select path="RECV_CARE_STATE_CD" id="RECV_CARE_STATE_CD"
		 							title="State Received Care" class="standardInputSmallText1 "> 
		 								<form:option value="">Select</form:option> 
			 							<c:forEach items="${stateCodes}" var="stateCode"> 
			 								<form:option value="${stateCode.lookup_cd}" label="${stateCode.lookup_name}" name="${stateCode.lookup_name}"></form:option> 
			 							</c:forEach>
		 							</form:select> 
								</span>
			
							</div>
							
							<div class="flw40" > 
								<span class="fLW35  bold">Facility Name  </span>
								<span
									class="fLW65 ">
								
									<form:input  path="OOS_FACILITY_NAME"   class="standardInputText "  title="OOS Facility Name"/>
								</span>
							</div>
							</div>
							
				</div>
				<div class="mLRTB pB20 clearBoth">
						<div class="flw40" > 
							<span class="flw25  bold">Diagnosis Facility  </span>
							<span
						class="flw60 ">
							
							<form:select  path="DIAGNOSIS_FACILITY"   class="standardInputText "  title="Diagnosis Facility ">
								<form:option value="" >--select--</form:option>
								<c:forEach items="${subFacilities}" var="facility">
									<form:option value="${facility.facility_uid}" label="${facility.facility_name}" name="${facility.facility_name}"></form:option>
								</c:forEach>
							</form:select> 
							</span>
							<span class="flw15"><a class="mailFacility" href="#" style="top:0px !important;"></a></span>
						</div>	
 
					<div class="flw60">
						<span class="bold">Facility type </span> 
							<span
								class="mL10"> 
							<c:set var="refColumnData" value="${riaDropDownList.DIAGNOSIS_SETTING_TYPE}"/>
							<form:select path="DIAGNOSIS_SETTING_TYPE"
									title=" Diagnosis Setting"
									class="standardInputMedText " dependentField="DIAGNOSIS_SETTING_TYPE_FIELDS" onchange="showOtherFields(this)">
								<form:option value="">Select</form:option>
								<c:forEach items="${refColumnData}" var="diagnosisSettingData">
										<form:option value="${diagnosisSettingData.LOOKUP_CD}">${diagnosisSettingData.LOOKUP_NAME}</form:option>
									</c:forEach>
							</form:select>
							</span>
							
							<span class="" id="DIAGNOSIS_SETTING_TYPE_FIELDS">
								<span class="mL10 bold">Other </span> 
									<span
										class="mL10"> 
									<form:input path="DIAGNOSIS_SETTING_OTHER_TEXT"
											title=" Diagnosis Setting other text"
											class="standardInputSmallText" />
										
									</span>
							</span>
							
							<span class="" >
								<span class="mL10 bold">Diagnosis Facility County </span> 
									<span
										class="mL10"> 
									<form:select
										path="INITIAL_FACILITY_COUNTY"
										class="standardInputMedText wC100"
										title="Diagnosis Facility County">
										<form:option value="" label="" />
										<form:options items="${sessionScope.countyMap}" />
									</form:select>	
									</span>
							</span>
							
					</div>
					
<!-- 					<div class="flw30" id="DIAGNOSIS_SETTING_TYPE_FIELDS"> -->
<!-- 						<span class="mL10 bold">Other </span>  -->
<!-- 							<span -->
<!-- 								class="mL10">  -->
<%-- 							<form:input path="DIAGNOSIS_SETTING_OTHER_TEXT" --%>
<%-- 									title=" Diagnosis Setting other text" --%>
<%-- 									class="standardInputMedText " /> --%>
								
<!-- 							</span> -->
<!-- 					</div> -->
				</div>
				
				
				
				
				<div class="mLRTB pB20 clearBoth">
				<div class="fieldHolder30">
				<span class="fLW35 bold">RAP Provider </span> 
					<span
						class="fLW65"><form:select path="RAP_PROVIDER" class="standardInputMedText "  dependentField="RAP_PROVIDER_FIELDS"  onchange="showRiaDependentFields(this)">
						
							<form:option value=""></form:option>
							<form:option value="Y">Yes</form:option>
							<form:option value="N">No</form:option>
						</form:select>
						</span>
						</div>

				<div id="RAP_PROVIDER_FIELDS">
					<div class="fieldHolder70">
							<span class="bold">Provider First name </span> <span
							class="mL10"><form:input path="RAP_PROVIDER_FNAME"
								class="standardInputMedText "  title="RAP Provider First name" /> </span>
					
							<span class="mL20 bold">Provider Middle name </span> <span
							class="mL10"><form:input path="RAP_PROVIDER_MNAME"
								class="standardInputMedText " title="RAP Provider Middle name"/> </span>
							
							<span class="mL20 bold">Provider Last name </span> <span
							class="mL10"><form:input path="RAP_PROVIDER_LNAME"
								class="standardInputMedText rapLname" title="RAP Provider Last name"/> </span>
					</div>
				</div>
			</div>
				
				
			<div class="container"
			style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
				<div id="psDetailsHeadingDiv" class="divHeadingCls"
					style="background: #DDDDDD;">
					<span class=" mL2">RIA Follow-Up</span>
				</div>
				<jsp:include page="ria/RiaFollowupData.jsp"></jsp:include>
				
			</div>	
			
	
			
			<div class="container"
			style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
				<div id="psDetailsHeadingDiv" class="divHeadingCls"
					style="background: #DDDDDD;">
					<span class=" mL2">Barriers to care</span>
				</div>
				<div class="clearBoth mL10 pT10 pB20">
					<div class="fLW100">
							<div class="floatLeft">
								
								
										<div class="fLW100">
									
											<span class="flw15"><form:checkbox path="HOUSING_INSTABILITY" title="Housing Instability" value="Y"/>Housing Instability</span>
											<span class="flw15"><form:checkbox path="FOOD_INSECURITY" title="Food Insecurity" value="Y"/>Food Insecurity</span>
											<span class="flw15"><form:checkbox path="HEALTH_LITERACY" title="Health Literacy" value="Y"/>Health Literacy</span>
											<span class="flw15"><form:checkbox path="INSURANCE_BARRIER" title="Insurance Barrier" value="Y"/>Insurance</span>
											<span class="flw15"><form:checkbox path="TRANSPORTATION_BARRIERS" title="Transportation Barriers" value="Y"/>Transportation Barriers</span>
											<span class="flw15"><form:checkbox path="MENTAL_HEALTH_BARRIER" title="Mental Health Barrier" value="Y"/>Mental Health</span>
											
										</div>
										<div class="mT5 fLW100">
											<span class="flw15"><form:checkbox path="SUBSTANCE_USE" title="Substance Use" value="Y"/>Substance Use</span>
											<span class="flw15"><form:checkbox path="INCARCERATION" title="Incarceration" value="Y"/>Incarceration</span>
											<span class="flw15"><form:checkbox path="LANGUAGE_BARRIER" title="Language Barrier" value="Y"/>Language Barrier</span>
											<span class="flw15"><form:checkbox path="ADVERSE_EFFECTS_TO_MEDS" title="Adverse effects to Medication" value="Y"/>Adverse effects to Medication</span>
											<span class="flw25">Other<span class="mL5">
											 <form:input path="BARRIERS_TO_CARE_OTHER_TEXT"
												id="BARRIERS_TO_CARE_OTHER_TEXT" title="Barriers Other Text"
											class="standardInputSmallText " />
												</span>
												</span>
										</div> 
							
							
						</div>
					</div>
				</div>
			</div>
			
			
			
		</div>


		<div class="container"
			style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
			<jsp:include page="ria/RiaTTHSection.jsp"></jsp:include>
		</div>
		
		

		<div class="container"
			style="border: 1px dotted; width: 100%; padding-bottom: 5px;"
			id="labDocumentProviderAddressDtls">
			<div id="prvDetailsHeadingDiv" class="divHeadingCls"
				style="background: #DDDDDD;">
				<span class=" mL2">eHars Information</span>
			</div>
			
			<div class="mLRTB pB20">
				<div class="fieldHolder35">

					<span class=" fLW45 bold"> ROS Dx date </span> 
					
					<span
						class="fLW50"> <form:input path="NYS_EHARS_HIV_DX_DT"
							id="NYS_EHARS_HIV_DX_DT" title="Rest of State diagnosis date"
							class="standardInputMedText mask dPicker " />
					</span>
					<div class="v_error mR10 NYS_EHARS_HIV_DX_DT "></div>
				</div>
				<div class="fieldHolder35">
					<span class="fLW45 bold"> NYC Dx date</span> 
					<span
						class="fLW50">  <form:input path="NYC_EHARS_HIV_DX_DT"
							id="NYC_EHARS_HIV_DX_DT" title="NYC diagnosis date"
							class="standardInputMedText mask dPicker " />
					</span>
					<div class="v_error mR10 NYC_EHARS_HIV_DX_DT "></div>
				</div>
				<div class="fieldHolder30">

					<span class=" fLW45 bold"> Calculated Dx date </span> 
					
					<span
						class="fLW50"> <form:input path="CALC_EHARS_HIV_DX_DT"
							id="CALC_EHARS_HIV_DX_DT" title="Calculated Diagnosis date"
							class="standardInputMedText mask dPicker " />
					</span>
					<div class="v_error mR10 CALC_EHARS_HIV_DX_DT "></div>
				</div>
			</div>
			
			<div class="mLRTB pB20 clearBoth">
				
				<div class="fieldHolder35">
					<span class="fLW45 bold"> ROS disease Dx category</span> 
					<span
						class="fLW50">  <form:input path="NYS_DISEASE_CATEG_DX"
							id="NYS_DISEASE_CATEG_DX" title="Rest of State Disease diagnosis category"
							class="standardInputMedText " />
					</span>
				</div>
		
				<div class="fieldHolder35">

					<span class=" fLW45 bold"> NYC disease Dx category </span> 
					
					<span
						class="fLW50"> <form:input path="NYC_DISEASE_CATEG_DX"
							id="NYC_DISEASE_CATEG_DX" title="NYC disease Diagnosis category"
							class="standardInputMedText " />
					</span>

				</div>
				
				<div class="fieldHolder30">
					<span class="fLW45 bold"> eHars Eligibility</span> 
					<span
						class="fLW50">  <form:input path="EHARS_ELIG"
							id="EHARS_ELIG" title="eHars Eligibility"
							/>
					</span>
            </div>
				
			</div>
			
		</div>
		
		


		<div class="container"
			style="border: 1px dotted; width: 100%; padding-bottom: 5px;"
			id="labDocumentProviderAddressDtls">
			<div id="prvDetailsHeadingDiv" class="divHeadingCls"
				style="background: #DDDDDD;">
				<span class=" mL2">Laboratory Information </span>
			</div>
			
			<div class="mLRTB pB20">
			<div class="fieldHolder25">
					<span class="fLW35 bold"> First Genotype date </span> 
					<span
						class="fLW60">  <form:input path="FIRST_GENOTYPE_DATE"
							id="FIRST_GENOTYPE_DATE" title="First Genotype date "
							class="standardInputSmallText2  mask dPicker " />
					</span>
					<div class="v_error mR10 FIRST_GENOTYPE_DATE "></div>
				</div>
			</div>
			
			
			<div class="mLRTB pB20 clearBoth">
				<div class="flw10"> <span class="bold" style="font-size:12px">Initial VL : </span></div>
				<div class="flw18">
					<span class="bold"> Date: </span> 
					<span
						class="mL5" id="FIRST_VL_DATE"> 
						
						<c:out value="${stdRiaDocVO.FIRST_VL_DATE}" />
					</span>
					<span>
						<form:hidden path="FIRST_VL_DATE" id="RIA_FIRST_VL_DATE"/> 
					</span>
				</div>
				
				<div class="flw18">

					<span class=" bold"> Value: </span> 
					<span
						class="mL5" id="FIRST_VL_VALUE"> 
						
						<c:out value="${stdRiaDocVO.FIRST_VL_VALUE}" />
					</span>
					<span>
						<form:hidden path="FIRST_VL_VALUE" id="RIA_FIRST_VL_VALUE"/> 
					</span>

				</div>
				<div class="flw18">
					<span class="bold"> Units: </span> 
					<span
						class="mL5" id="FIRST_VL_UNITS"> 
						
						<c:out value="${stdRiaDocVO.FIRST_VL_UNITS}" />
					</span>
					<span>
						<form:hidden path="FIRST_VL_UNITS" id="RIA_FIRST_VL_UNITS"/> 
					</span>
				</div>
			</div>
				
			<div class="mLRTB pB20 clearBoth">
				<div class="flw10"> <span class="bold" style="font-size:12px">Most Recent VL: </span></div>
				
				
				<div class="flw18">
					<span class="bold"> Date: </span> 
					<span
						class="mL5" id="RECENT_VL_DATE"> 
						
						<c:out value="${stdRiaDocVO.RECENT_VL_DATE}" />
					</span>
					<span>
						<form:hidden path="RECENT_VL_DATE" id="RIA_RECENT_VL_DATE"/> 
					</span>
				</div>
				<div class="flw18">

					<span class=" bold"> Value: </span> 
					<span
						class="mL5" id="RECENT_VL_VALUE"> 
						
						<c:out value="${stdRiaDocVO.RECENT_VL_VALUE}" />
					</span>
					<span>
						<form:hidden path="RECENT_VL_VALUE" id="RIA_RECENT_VL_VALUE"/> 
					</span>

				</div>
				<div class="flw18">
					<span class="bold"> Units: </span> 
					<span
						class="mL5" id="RECENT_VL_UNITS"> 
						
						<c:out value="${stdRiaDocVO.RECENT_VL_UNITS}" />
					</span>
					<span>
						<form:hidden path="RECENT_VL_UNITS" id="RIA_RECENT_VL_UNITS"/> 
					</span>
				</div>
				
			</div>
	
			<div class="mLRTB pB20 clearBoth">
				<div class="flw10"> <span class="bold" style="font-size:12px">Most Recent: </span></div>
				
				<div class="flw18">

					<span class=" bold"> Provider Name :</span> 
					<span class="mL5" id="RECENT_VL_PROVNAME">
					<c:out value="${stdRiaDocVO.RECENT_VL_PROVNAME}" />
					</span>
					<span>
						<form:hidden path="RECENT_VL_PROVNAME" id="RIA_RECENT_VL_PROVNAME"/> 
					</span>
				</div>
				<div class="flw18">
				<span class="bold"> Provider Address:</span>
<span class="mL5" id="RECENT_VL_PROV_ADDRESS">
					<c:out value="${stdRiaDocVO.RECENT_VL_PROV_ADDRESS}" />
					</span>
					<span>
						<form:hidden path="RECENT_VL_PROV_ADDRESS" id="RIA_RECENT_VL_PROV_ADDRESS"/> 
					</span>
				</div>
				<div class="flw18">

					<span class=" bold"> City: </span> 
<span class="mL5" id="RECENT_VL_PROV_CITY">
						<c:out value="${stdRiaDocVO.RECENT_VL_PROV_CITY}" />
					</span>
					<span>
						<form:hidden path="RECENT_VL_PROV_CITY" id="RIA_RECENT_VL_PROV_CITY"/> 
					</span>
				</div>
				<div class="flw18">
					<span class="bold"> State:</span> 
<span class="mL5" id="RECENT_VL_PROV_STATE">
						<c:out value="${stdRiaDocVO.RECENT_VL_PROV_STATE}" />
					</span>
					<span>
						<form:hidden path="RECENT_VL_PROV_STATE" id="RIA_RECENT_VL_PROV_STATE"/> 
					</span>
				</div>
				<div class="flw18">
						<span class=" bold"> Zip: </span> 
						<span class="mL5" id="RECENT_VL_PROV_ZIP">
						<c:out value="${stdRiaDocVO.RECENT_VL_PROV_ZIP}" />
						</span>
						<span>
							<form:hidden path="RECENT_VL_PROV_ZIP" id="RIA_RECENT_VL_PROV_ZIP"/> 
						</span>
					</div>
				
			</div>
			
			<div class="mLRTB pB20 clearBoth">
				<div class="flw10"> <span class="bold" style="font-size:12px">Most Recent CD4: </span></div>
				<div class="flw18">

					<span class=" bold"> CD4 Date: </span> 
					
					<span
						class="mL5" id="RECENT_CD4_DATE"> 
						
						<c:out value="${stdRiaDocVO.RECENT_CD4_DATE}" />
						
					</span>
					<span>
						<form:hidden path="RECENT_CD4_DATE" id="RIA_RECENT_CD4_DATE"/> 
					</span>
				</div>
				<div class="flw18">
					<span class="bold"> Value:</span> 
					<span
						class="mL5" id="RECENT_CD4_VALUE">  
						<c:out value="${stdRiaDocVO.RECENT_CD4_VALUE}" />
						
					</span>
					<span>
						<form:hidden path="RECENT_CD4_VALUE" id="RIA_RECENT_CD4_VALUE"/> 
					</span>
				</div>
			
				<div class="flw18">

					<span class=" bold"> Units: </span> 
					
					<span
						class="mL5" id="RECENT_CD4_UNITS"> 
						<c:out value="${stdRiaDocVO.RECENT_CD4_UNITS}" />
						
					</span>
					<span>
						<form:hidden path="RECENT_CD4_UNITS" id="RIA_RECENT_CD4_UNITS"/> 
					</span>
				</div>
				
			</div>
			
			
			
		</div>

		
		<div class="container"
			style="border: 1px dotted; width: 100%; padding-bottom: 5px;"
			id="labDocumentProviderAddressDtls">
			<div id="prvDetailsHeadingDiv" class="divHeadingCls"
				style="background: #DDDDDD;">
				<span class=" mL2">Opportunistic Infections</span>
			</div>
			
			<div class="mLRTB pB20">
				<div class="fLW35">
					<span class="fLW55 bold">Candidiasis, bronchi, trachea or lungs </span> 
						<span
							class="fLW45">  <form:input path="CANDLUNG_DATE_TEXT"
								id="CANDLUNG_DATE_TEXT" title="Candidiasis, bronchi, trachea or lungs Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 CANDLUNG_DATE_TEXT "></div>
					</div>
					<div class="fLW35">
					<span class="fLW55 bold">Candidiasis, esophageal </span> 
						<span
							class="fLW45">  <form:input path="CANDE_DATE_TEXT"
								id="CANDE_DATE_TEXT" title=" Candidiasis, esophageal Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 CANDE_DATE_TEXT "></div>
					</div>
					<div class="fLW30">
					<span class="fLW55 bold">Carcinoma, invasive cervical </span> 
						<span
							class="fLW45">  <form:input path="CERVCANC_DATE_TEXT"
								id="CERVCANC_DATE_TEXT" title=" Carcinoma, invasive cervical Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 CERVCANC_DATE_TEXT "></div>
					</div>
			</div>
			<div class="mLRTB pB20 clearBoth">
				<div class="fLW35">
					<span class="fLW55 bold">Coccidioidomycosis, disseminated 
<!-- 					or extrapulmonary  -->
					</span> 
						<span
							class="fLW45">  <form:input path="COCCI_DATE_TEXT"
								id="COCCI_DATE_TEXT" title="Coccidioidomycosis, disseminated or extrapulmonary Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 COCCI_DATE_TEXT "></div>
					</div>
					<div class="fLW35">
					<span class="fLW55 bold">Cryptococcosis, extrapulmonary </span> 
						<span
							class="fLW45">  <form:input path="CRYPTOCO_DATE_TEXT"
								id="CRYPTOCO_DATE_TEXT" title=" Cryptococcosis, extrapulmonary Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 CRYPTOCO_DATE_TEXT "></div>
					</div>
					<div class="fLW30">
					<span class="fLW55 bold">Cryptosporidiosis, chronic intestinal </span> 
						<span
							class="fLW45">  <form:input path="CRYPTOSP_DATE_TEXT"
								id="CRYPTOSP_DATE_TEXT" title=" Cryptosporidiosis, chronic intestinal Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 CRYPTOSP_DATE_TEXT "></div>
					</div>
			</div>
			<div class="mLRTB pB20 clearBoth">
				<div class="fLW35">
					<span class="fLW55 bold">Cytomegalovirus disease </span> 
						<span
							class="fLW45">  <form:input path="CMV_DATE_TEXT"
								id="CMV_DATE_TEXT" title=" Cytomegalovirus disease Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 CMV_DATE_TEXT "></div>
					</div>
					<div class="fLW35">
					<span class="fLW55 bold">Cytomegalovirus retinitis </span> 
						<span
							class="fLW45">  <form:input path="CMVRET_DATE_TEXT"
								id="CMVRET_DATE_TEXT" title=" Cytomegalovirus retinitis Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 CMVRET_DATE_TEXT "></div>
					</div>
					<div class="fLW30">
					<span class="fLW55 bold">HIV encephalopathy </span> 
						<span
							class="fLW45">  <form:input path="HIVNCEPH_DATE_TEXT"
								id="HIVNCEPH_DATE_TEXT" title=" HIV encephalopathy Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 HIVNCEPH_DATE_TEXT "></div>
					</div>
			</div>
			<div class="mLRTB pB20 clearBoth">
				<div class="fLW35">
					<span class="fLW55 bold">Herpes simplex </span> 
						<span
							class="fLW45">  <form:input path="HERPES_DATE_TEXT"
								id="HERPES_DATE_TEXT" title=" Herpes simplex Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 HERPES_DATE_TEXT "></div>
					</div>
					<div class="fLW35">
					<span class="fLW55 bold">Histoplasmosis, disseminated 
<!-- 					or extrapulmonary  -->
					</span> 
						<span
							class="fLW45">  <form:input path="HISTO_DATE_TEXT"
								id="HISTO_DATE_TEXT" title="Histoplasmosis, disseminated or extrapulmonary Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 HISTO_DATE_TEXT "></div>
					</div>
					<div class="fLW30">
					<span class="fLW55 bold">Isosporiasis, chronic intestinal </span> 
						<span
							class="fLW45">  <form:input path="ISO_DATE_TEXT"
								id="ISO_DATE_TEXT" title=" Isosporiasis, chronic intestinal Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 ISO_DATE_TEXT "></div>
					</div>
			</div>
			<div class="mLRTB pB20 clearBoth">
				<div class="fLW35">
					<span class="fLW55 bold">Kaposi's sarcoma </span> 
						<span
							class="fLW45">  <form:input path="KS_DATE_TEXT"
								id="KS_DATE_TEXT" title=" Kaposi's sarcoma Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 KS_DATE_TEXT "></div>
					</div>
					<div class="fLW35">
					<span class="fLW55 bold">Lymphoma, Burkitts </span> 
						<span
							class="fLW45">  <form:input path="BURKL_DATE_TEXT"
								id="BURKL_DATE_TEXT" title=" Lymphoma, Burkitts Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 BURKL_DATE_TEXT "></div>
					</div>
					<div class="fLW30">
					<span class="fLW55 bold">Lymphoma, immunoblastic </span> 
						<span
							class="fLW45">  <form:input path="LYMPHIM_DATE_TEXT"
								id="LYMPHIM_DATE_TEXT" title=" Lymphoma, immunoblastic Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 LYMPHIM_DATE_TEXT "></div>
					</div>
			</div>
			<div class="mLRTB pB20 clearBoth">
				<div class="fLW35">
					<span class="fLW55 bold">Lymphoma, primary in brain </span> 
						<span
							class="fLW45">  <form:input path="LYMPHB_DATE_TEXT"
								id="LYMPHB_DATE_TEXT" title=" Lymphoma, primary in brain Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 LYMPHB_DATE_TEXT "></div>
					</div>
					<div class="fLW35">
					<span class="fLW55 bold">Mycobacterium avium complex </span> 
						<span
							class="fLW45">  <form:input path="MYCOAV_DATE_TEXT"
								id="MYCOAV_DATE_TEXT" title=" Mycobacterium avium complex Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 MYCOAV_DATE_TEXT "></div>
					</div>
					<div class="fLW30">
					<span class="fLW55 bold">M. Tuberculosis, pulmonary </span> 
						<span
							class="fLW45">  <form:input path="MYCOPULM_DATE_TEXT"
								id="MYCOPULM_DATE_TEXT" title=" M. Tuberculosis, pulmonary Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 MYCOPULM_DATE_TEXT "></div>
					</div>
			</div>
			<div class="mLRTB pB20 clearBoth">
				<div class="fLW35">
					<span class="fLW55 bold">M. Tuberculosis, disseminated
<!-- 					 or extrapulmonary  -->
					 </span> 
						<span
							class="fLW45">  <form:input path="MYCODISM_DATE_TEXT"
								id="MYCODISM_DATE_TEXT" title=" M. Tuberculosis, disseminated or extrapulmonary Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 MYCODISM_DATE_TEXT "></div>
					</div>
					<div class="fLW35">
					<span class="fLW55 bold">Mycobacterium of other species </span> 
						<span
							class="fLW45">  <form:input path="MYCOTH_DATE_TEXT"
								id="MYCOTH_DATE_TEXT" title=" Mycobacterium of other species Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 MYCOTH_DATE_TEXT "></div>
					</div>
					<div class="fLW30">
					<span class="fLW55 bold">Pneumocystis carinii </span> 
						<span
							class="fLW45">  <form:input path="PCP_DATE_TEXT"
								id="PCP_DATE_TEXT" title=" Pneumocystis carinii Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 PCP_DATE_TEXT "></div>
					</div>
			</div>
			<div class="mLRTB pB20 clearBoth">
					<div class="fLW35">
					<span class="fLW55 bold">Pneumonia recurrent </span> 
						<span
							class="fLW45">  <form:input path="PNEUM_DATE_TEXT"
								id="PNEUM_DATE_TEXT" title=" Pneumonia recurrent Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 PNEUM_DATE_TEXT "></div>
					</div>
					<div class="fLW35">
					<span class="fLW55 bold">Progressive multifocal leukoencephalopathy </span> 
						<span
							class="fLW45">  <form:input path="PML_DATE_TEXT"
								id="PML_DATE_TEXT" title=" Progressive multifocal leukoencephalopathy Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 PML_DATE_TEXT "></div>
					</div>
					<div class="fLW30">
					<span class="fLW55 bold">Salmonella septicemia, recurrent </span> 
						<span
							class="fLW45">  <form:input path="SALMNELA_DATE_TEXT"
 								id="SALMNELA_DATE_TEXT" title=" Salmonella septicemia, recurrent Date" 
 								class="standardInputSmallText2 mask dPicker" /> 
						</span>
						<div class="v_error mR10 SALMNELA_DATE_TEXT "></div>
					</div>
			</div>
			<div class="mLRTB pB20 clearBoth">
				<div class="fLW35">
					<span class="fLW55 bold">Toxoplasmosis of brain</span> 
						<span
							class="fLW45">  <form:input path="TOXO_DATE_TEXT"
								id="TOXO_DATE_TEXT" title=" Toxoplasmosis of brain Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 TOXO_DATE_TEXT "></div>
					</div>
					<div class="fLW35">
					<span class="fLW55 bold">Wasting Syndrome due to HIV </span> 
						<span
							class="fLW45">  <form:input path="WASTING_DATE_TEXT"
								id="WASTING_DATE_TEXT" title=" Wasting Syndrome due to HIV Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 WASTING_DATE_TEXT "></div>
					</div>
					<div class="fLW30">
					<span class="fLW55 bold">Lymphoid interstitial pneumonia </span> 
						<span
							class="fLW45">  <form:input path="LIP_DATE_TEXT"
								id="LIP_DATE_TEXT" title=" Lymphoid interstitial pneumonia Date"
								class="standardInputSmallText2 mask dPicker " />
						</span>
						<div class="v_error mR10 LIP_DATE_TEXT "></div>
					</div>
					
			</div>

		</div>
		
		<jsp:include page="ria/RiaRidrData.jsp"></jsp:include>		
		
	</div>
	<form:hidden path="COMPLETE_DATE"/>
	
</form:form>

<c:if test="${(stdRiaDocVO.COMPLETE_DATE == null || stdRiaDocVO.COMPLETE_DATE == '' ) && userPermissions['144'] != null}">
<div id="button-row" class="button-row  center mT20">
					
					
			<input type="button" id="tButton" class="button rounded blue effect-3"  value="SAVE"  onclick="saveRiaDocument()"/>
			<input type="button" id="tButton" class="button rounded blue effect-3"  value="COMPLETE"  onclick="completeRiaDocument()"/>
			<input type="button" id="LaftFollowupButton" class="flagLaftFollowupDocument button rounded blue effect-3"  value="Laft follow-up"  onclick="RiaDocflagLaftFollowupDocument(${stdRiaDocVO.LAB_DOC_ID}, '${stdRiaDocVO.NYEHMS_STATENO}')"/>						
			
</div>
</c:if>


