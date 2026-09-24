<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>
<link href="${staticPath}/css/docs/partnerService.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<script src="${staticPath}/js/docs/partnerService/partnerService.js?v=${buildDate}"></script>

<form:form modelAttribute="psPartnerView" id="partnerService" >

<form:errors path="*" cssClass="errorblock" element="div" />

	<div id="PartnerDoc">	
		<!--  Partner Service Details -->
		<div class="headingCls centerDoc">
				Partner Details
		</div>	
		<div class="container paddingBottom5" style="width:100%; float:right">
			<div style="float:right; width:25%">
				<span class="fLW35"> Partner ID:</span>
				<span class="fLW60"> <form:input path="PARTNRID" value="" class="standardInputSmallText" readonly="true"/> </span>
			</div>							
		</div>
		<div class="container paddingBottom5" style="width:100%; float:right">
			<div style="float:left; ">
				<span class="fLW35"> <spring:message code="label.stateNo" />:</span> 
					<span class="fLW60"> <form:input path="STATENO" value="" class="standardInputSmallText" readonly="true"/> </span>
			</div>
			<div style="float:right; width:25%">
				<span class="fLW35"> Assignment ID:</span>
				<span class="fLW60"> <form:input path="ASSIGNMENT_ID" value="" class="standardInputSmallText" readonly="true"/> </span>
			</div>								
		</div>

		<div class="container borderSSDMDoc">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> </span>					
			</div>	 
			<div class="mLRTB">
				<span class="fieldHolder25"> 
					<span class="fLW35">Supervisor </span>	
					<span class="fLW60"> <input path="" value="" class="standardInputSmallText" readonly="true"/> </span>
				</span>	
				<span class="fieldHolder25"> 
					<span class="fLW35">Worker </span>	
					<span class="fLW60"> <form:input path="WORK_TYPE" value="" class="standardInputSmallText" readonly="true"/> </span>
				</span>	
			</div>
	
			<div class="mLRTB">	
				<br><br>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Entry Date</span>
					<span class="fLW60"> <input path="" value="" class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35"> Assignment Date </span> 
					<span class="fLW60"> <input path="" value="" class="standardInputSmallText" /> </span>
				</span>
				<span class="fieldHolder49">
					<span class="fLW35"> Date Initiated/Open </span>
					<fmt:formatDate value="${psPartnerView.DISCL_DT}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDISCL_DT" />
					<span class="fLW60"> <form:input path="DISCL_DT" value="${theFormattedDISCL_DT}" class="standardInputSmallText" /> </span>
				</span>
				<br>
			</div>						  
		</div>

		<!--  Demographics Details -->

		<div class="container borderSSDMDoc">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> Demographics and PRF status:</span>					
			</div>	 
			<div class="mLRTB">
				<span class="fieldHolder25 ">
					<span class="fLW35"> <spring:message code="label.lastname" /></span>
					<span class="fLW60"> <form:input path="LNAME" value="" class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35"> <spring:message code="label.firstname" /></span> 
					<span class="fLW60"> <form:input path="FNAME" value="" class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> <spring:message code="label.middleName" /></span>
					<span class="fLW60"> <form:input path="MNAME" value="" class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"><spring:message code="label.suffix" /></span>
					<span class="fLW60"> <form:input path="SUFFIX" value="" class="standardInputSmallText" readonly="true"/> </span>	
				</span>	
			</div>
			<div class="mLRTB">	
				<br><br>
				<span class="fieldHolder25 ">
					<span class="fLW35"> <spring:message code="label.maiden" /></span>
					<span class="fLW60"> <form:input path="MAIDEN" value="" class="standardInputSmallText" readonly="true"/> </span>
				</span>
			</div>
			<div class="mLRTB">	
				<br>
				<span class="fieldHolder75">
                    <span class="personLabelDoc">ALIAS </span> 
                    <span class="pL10"><form:input path="ALIAS_LNAME" id="airsLname" class="standardInputMedText required" style="width:210px" title="Last Name" maxLength="25"/></span> 
                    <span  class="pL60"><form:input path="ALIAS_FNAME" id="airsFname" class="standardInputMedText required" style="width:210px" title="First Name" maxLength="20"/></span>
                    <span  class="pL60"><form:input path="ALIAS_MNAME" id="airsMname" class="standardInputMedText" style="width:210px" title="Middle Name" maxLength="25"/></span>
                    <br>
                    <span class="pL130"> Last</span>
                    <span class="pL250"> First</span>
                    <span class="pL250"> Middle</span>
          		</span>
			</div>

			<div class="mLRTB pB15">	
				<br><br>
				<span class="fieldHolder25"> 
					<span class="fLW35"><spring:message code="label.dateOfBirth" /></span>	
					<span class="fLW60"> <form:input path="DOB_TEXT" value="" class="standardInputSmallText" readonly="true"/> </span>
				</span>	
				<span class="fieldHolder25">
					<span class="fLW35"><spring:message code="label.age" /></span> 
					<span class="fLW60"> <form:input path="AGE" value="" class="standardInputSmallText" /> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35">Date of Death</span> 
					<span class="fLW60"> <form:input path="" value="" class="standardInputSmallText" /> </span>
				</span>
			</div>
		</div>
		<div id="raceEthnicity" class="container borderSSDMDoc" style="border-top:1px dotted; padding:10px 0 5px 0;">
			<div style="float:left; width:35%; margin-left:20px; border-right:1px dotted; ">
				<div style="border-bottom: 1px dotted;">			
					<div> Race</div> 
					<div style="margin-right:10px; padding-bottom: 10px">
						<form:checkbox  path="RACE_WHITE" value="1" /> White	
						<br>
						<form:checkbox path="RACE_BLACK" value="1" /> African American
						<br>
						<form:checkbox path="RACE_ASIAN" value="1" /> Asian
						<br>
						<form:checkbox path="RACE_INDIAN" value="1" /> American Indian/ Alaskan Native
						<br>
						<form:checkbox path="RACE_PACIFIC" value="1" /> Pacific Islander
						<br>
						<form:checkbox path="RACE_OTHER" value="1" /> Other <form:input path="RACE_OTHER" value="" class="standardinputSmallText2" />
						<br>
					</div>
				</div>
				<div style="  height: 35px; margin-bottom: 10px; margin-top: 10px;">
					<span class="">
						<span class="fLW35"> Sex At Birth </span> 
						<span class="fLW60"> 
							<form:radiobutton path="BIRTH_SEX" value="M" /> Male 
							<form:radiobutton path="BIRTH_SEX" value="F" /> Female
						</span>
					</span>
				</div>
			</div>
			
		
				
			<div class=" " style="float:left; padding-left:10px; width:25% ;border-right: 1px dotted;">
				
				<div >
					<div class="floatLeft pR10">
						<div class=""> Current Gender </div>
						<div  class="">
							<div> <form:radiobutton path="GENDER" value="M" /> Male </div>
							<div> <form:radiobutton path="GENDER" value="F" /> Female </div>
							<div> <form:radiobutton path="GENDER" value="U" />Unknown</div>		
						</div>
					</div>
					<div class="floatLeft pL10">
						<div class=""> Transgender </div>
						<div  class="">
							<div> <form:radiobutton path="GENDER" value="X" /> MTF </div>		
							<div> <form:radiobutton path="GENDER" value="Y" /> FTM </div>		
							<div> <form:radiobutton path="GENDER" value="T" /> Unspecified </div>														
						</div>
					</div> 
				</div>
				<div class="clearBoth"></div>
				<div style="margin-top: 10px;  border-top: 1px dotted;">
					<span><spring:message code="label.ethnicity" />  (Hispanic or Latino)</span>
					<div> 
						<form:radiobutton path="ETHNICCD" value="01" /> <spring:message code="label.yes" /><br>
						<form:radiobutton path="ETHNICCD" value="02" /> <spring:message code="label.no" /><br>
						<form:radiobutton path="ETHNICCD" value="09" /> <spring:message code="label.unknown" />
					</div>
				</div>
			</div>
			
			<!--  Medical details -->
			
			<div style="float:left; width:35% ; padding-left:10px; padding-bottom: 12px; border-left:1px dotted;">
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Marital Status
					</div>
					<div style="float:left">
							<span> <form:radiobutton path="MARITALCD" value="01" /> Single </span>
							<span> <form:radiobutton path="MARITALCD" value="06" /> Married </span>	
							<span> <form:radiobutton path="MARITALCD" value="05" /> Separated </span>
							<span> <form:radiobutton path="MARITALCD" value="04" /> Divorced </span>
							<span> <form:radiobutton path="MARITALCD" value="03" /> Windowed </span>	
							<span> <form:radiobutton path="MARITALCD" value="07" /> Cohabitation </span>
							<span> <form:radiobutton path="MARITALCD" value="09" /> Unknown </span>
							<span> <form:radiobutton path="MARITALCD" value="08" /> Refused to Answer </span>
					</div>
				</div>		
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Spouse
					</div>
					<div style="float:left">
						<span> <form:radiobutton path="SPOUSE" value="Y" /> Yes </span>
						<span> <form:radiobutton path="SPOUSE" value="N" /> No </span>
					</div>
				</div>			
				<div class="mT5 container">
					<div style="float:left; width:45%">
						DV Screen
					</div>
					<div style="float:left">
						<span> <form:radiobutton path="DVSCREEN" value="Y" /> Yes </span>
						<span> <form:radiobutton path="DVSCREEN" value="N" /> No </span>
					</div>
				</div>					
				<div class="mT5 container">
					<div style="float:left; width:45%">
						DV Risk
					</div>
					<div style="float:left">
						<span> <form:radiobutton path="RISK_VIOLENCE" value="Y" /> Yes </span>
						<span> <form:radiobutton path="RISK_VIOLENCE" value="N" /> No </span>
					</div>
				</div>		
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Prov. Status
					</div>
					<div style="float:left">
						 <input path="" value="" class="standardInputSmallText" />
					</div>
				</div>
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Status Date
					</div>
					<div style="float:left">
						<input path="" value="" class="standardInputSmallText" />
					</div>
				</div>			
			</div>
		</div>

		<div class="container borderSSDMDoc" style="padding: 10px 0">	
			<div class="mTB">	
				<span class="" > 
					<span class="pL10">Street </span>	
					<span class="pL10"> <form:input path="ADDRESS" value="" class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="pL25">
					<span class=""> City </span> 
					<span class="pL10"> <form:input path="CITY" value="" class="standardInputSmallText" /> </span>
				</span>
				<span class="pL25">
					<span class=""> State</span>
					<span class="pL10"> <form:input path="STATE" value="" class="standardInputSmallText" /> </span>
				</span>
				<span class="pL25">
					<span class="">  Zip </span> 
					<span class="pL10"> <form:input path="CITY" value="" class="standardInputSmallText" /> </span>
				</span>
				<br>
			</div>
			<div class="mLRTB">	
				<span class="fieldHolder33">
					<span class="fLW35"> Country </span> 
					<span class="fLW60"> 
						<form:select path="COUNTRY_CD" cssClass="standardInputMedText" title="Country" >
							<form:option value=""></form:option>
							<form:options items="${sessionScope.countryMap}"/>
						</form:select> 
					</span>
				</span>
				<span class="fieldHolder33"> 
					<span class="fLW35">Home Phone</span>	
					<span class="fLW60"> <input path="" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35">Cell Phone</span>
					<span class="fLW60"> <form:input path="CELL_PHONE" value="" class="standardInputMedText" /> </span>
				</span>
				<br>
			</div>
			<div class="mLRTB">	
				<span class="fieldHolder33">
					<span class="fLW35">Work Phone </span> 
					<span class="fLW60"> <form:input path="WORK_PHONE" value="" class="standardInputMedText" /> </span>
				</span>
				<span class="fieldHolder33"> 
					<span class="fLW35">Place of Employment</span>	
					<span class="fLW60"> <input path="" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35">Work Hours</span> 
					<span class="fLW60"> <form:input path="WORK_HOURS" value="" class="standardInputMedText" /> </span>
				</span>
				<br>
			</div>
			<div class="mLRTB">	
				<span class="fieldHolder33">
					<span class="fLW35">E-mail Address</span>
					<span class="fLW60"> <form:input path="EMAIL_ADDRESS" value="" class="standardInputMedText" /> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35">WebAddress/Screen Name</span>
					<span class="fLW60"> <input path="" value="" class="standardInputMedText" /> </span>
				</span>		
				<br>		
			</div>
			
		</div>

		<!--  Pregnancy & Physical Information -->

		<div class="container borderSSDMDoc">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> Pregnancy & Physical Info:</span>					
			</div>	 
			<div class="mLRTB">
				<span class="fieldHolder33">
					<span class="fLW35"> Height</span>
					<span class="fLW60"> <form:input path="HEIGHT_PARTNER" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35"> Weight</span>
					<span class="fLW60"> <input path="" value="" class="standardInputMedText" readonly="true"/> </span>	
				</span>	
				<span class="fieldHolder33">
					<span class="fLW35"> Size/Build</span>
					<span class="fLW60"> <form:input path="BUILD_PARTNER" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>	<br>	
			</div>
			<div class="mLRTB">
				<br>
				<span class="fieldHolder33">
					<span class="fLW35"> Hair</span> 
					<span class="fLW60"> <form:input path="HAIR_PARTNER" value="" class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder33 ">
					<span class="fLW35"> Complexion</span>
					<span class="fLW60"> <form:input path="COMPLEXION_PARTNER" value="" class="standardInputSmallText" readonly="true"/> </span>
				</span>	
				<span class="fieldHolder33">
					<span class="fLW35"> #Weeks</span> 
					<span class="fLW60"> <input path="" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
				
				<br>

			</div>
			<div class="mLRTB pB15">
				<br>
				<span class="fieldHolder33">
					<span class="fLW35"> Pregnant</span>
					<span class="fLW60"> 
						<form:checkbox path="" value="Y" /> Yes 
					</span>
				</span>
				<span class="fieldHolder49"> 
					<span class="fLW35">Other Identifying, Locating, or Medical Information </span>	
					<span class="fLW60"> <textarea rows="3" cols="89" path="COMMENTS" value="" readonly="true"/> </textarea>  </span>
				</span>					
				<br>
			</div>
		</div>

		<!--  Partner Services Follow-Up -->

		<div class="container borderSSDMDoc">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> Partner Services Follow-Up</span>					
			</div>	 
			<div class="mLRTB">
				<span class="fieldHolder33">
					<span class="fLW35"> First Exposure Date</span>
					<span class="fLW60"> <form:input path="F_EXP_DT" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>				
				<span class="fieldHolder33">
					<span class="fLW35"> Last Exposure Date</span> 
					<span class="fLW60"> <form:input path="L_EXP_DT" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35">Exposure Frequency</span> 
					<span class="fLW60"> <input path="" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>	
			</div>
			<div class="mLRTB">
				<br><br>
				<span class="fieldHolder33">
					<span class="fLW35"> Date Initiated</span> 
					<span class="fLW60"> <input path="" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35"> Referral Basis</span>
					<span class="fLW60"> <input path="" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
			</div>
			<div class="mLRTB">
				<br><br>				
				<span class="fieldHolder33">
					<span class="fLW35"> Notifiability</span>
					<span class="fLW60"> <form:input path="NOTIFY" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>	
				<span class="fieldHolder33">
					<span class="fLW35"> Notification Plan</span>
					<span class="fLW60"> <form:input path="NOTIFY_PLAN" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35"> Actual Notification Method</span> 
					<span class="fLW60"> <form:input path="NOTIFY_METHOD" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
			</div>
			<div class="mLRTB">
				<br><br>				
				<span class="fieldHolder33">
					<span class="fLW35"> Notification Date</span>
					<span class="fLW60"> <input path="" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>	
				<span class="fieldHolder33">
					<span class="fLW35"> DIS # Conducted Notification</span>
					<span class="fLW60"> <input path="" value="" class="standardInputMedText" readonly="true"/> </span>	
				</span>	
			</div>
			<div class="mLRTB">
				<br><br>				
				<span class="fieldHolder33">
					<span class="fLW35"> Disposition Date</span>
					<span class="fLW60"> <input path="" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35"> Disposition</span>
					<span class="fLW60"> <input path="" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35"> Internet Outcome</span>
					<span class="fLW60"> <input path="" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
			</div>
			<div class="mLRTB">
				<br><br>				
				<span class="fieldHolder49">
					<span class="fLW18"> Locator Notes</span> 
					<span class="fLW75"> <textarea rows="2" cols="78" path="COMMENTS" value="" readonly="true"/> </textarea> </span>
				</span>
			</div>
		</div>
		
		<!--  Provider & Test Details -->

		<div class="container borderSSDMDoc">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> Provider & Test Details:</span>
			</div>
			<div class="mLRTB">	 	
				<span class="fieldHolder33 ">
					<span class="fLW35"> PRF HIV Status</span>
					<span class="fLW60"> <input path="" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35"> PRF Date Tested</span> 
					<span class="fLW60"> <input path="" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
			</div>
			<div class="mLRTB">                                                                                              
				<br><br>
				<span class="fieldHolder33">
					<span class="fLW35"> Previous HIV Test</span> 
					<span class="fLW60"> <form:input path="PREV_HIV_TEST" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35"> Date of Last HIV Test</span>
					<fmt:formatDate value="${psPartnerView.DATE_LAST_HIV_TEST}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDATE_LAST_HIV_TEST" />
					<span class="fLW60"> <form:input path="DATE_LAST_HIV_TEST" value="${theFormattedDATE_LAST_HIV_TEST}" class="standardInputMedText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35"> Self Reported Results</span> 
					<span class="fLW60"> <form:input path="SELF_RPT_RES" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
			</div>	
			
			<div class="mLRTB">                                                                                              
				<br>
				<span class="fieldHolder33">
					<span class="fLW35"> Referred to HIV Testing</span> 
					<span class="fLW60"> <form:input path="REF_TO_TESTING" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>		
				<span class="fieldHolder33">
					<span class="fLW35"> Referral Date</span> 
					<span class="fLW60"> <input path="" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>				
			</div>
			<div class="mLRTB">
				<br>	
				<span class="fieldHolder33">
					<span class="fLW35"> Tested</span> 
					<span class="fLW60"> <form:input path="TESTED" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>			
				<span class="fieldHolder33">
					<span class="fLW35"> Date Tested</span> 
					<span class="fLW60"> <input path="" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35"> Result</span> 
					<span class="fLW60"> <form:input path="TEST_RESULTS" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>				
			</div>
			<div class="mLRTB">
				<br>	
					<span class="mR20 bold"> Test Location</span>
					<span><form:radiobutton path="TESTLOC_CD" value="01" /> Field</span> 
					<span><form:radiobutton path="TESTLOC_CD" value="02" /> Private Provider</span>
					<span><form:radiobutton path="TESTLOC_CD" value="03" /> Anonymous </span>
					<span><form:radiobutton path="TESTLOC_CD" value="04" /> STD Clinic</span>
					<span><form:radiobutton path="TESTLOC_CD" value="05" /> Community Health Center</span>
					<span><form:radiobutton path="TESTLOC_CD" value="06" /> Hospital Clinic</span>
					<span><form:radiobutton path="TESTLOC_CD" value="08" /> Other</span>
					<span><form:radiobutton path="TESTLOC_CD" value="09" /> Unknown</span>

			</div>
			<div class="mLRTB">
					<span class="mR20 bold"> Field Testing</span> 
					<span class=""> 
						<span><form:radiobutton path="FIELD_TESTING" value="01" /> Field testing not offered</span> 
						<span><form:radiobutton path="FIELD_TESTING" value="02" /> Field testing offered, refused by client</span>
						<span><form:radiobutton path="FIELD_TESTING" value="03" /> Field testing completed by PS staff </span>
					</span>
			</div>
			
			<div class="mLRTB">

					<span class="mR20 bold"> First Appt</span> 
					<span class="">
						<span><form:radiobutton path="ATTEND_FIRST_APPT" value="01" /> Pending</span> 
						<span><form:radiobutton path="ATTEND_FIRST_APPT" value="02" /> Confirmed, Accessed Service</span>
						<span><form:radiobutton path="ATTEND_FIRST_APPT" value="03" /> Confirmed, Did not access service </span>
						<span><form:radiobutton path="ATTEND_FIRST_APPT" value="04" /> Lost to follow-up</span>
						<span><form:radiobutton path="ATTEND_FIRST_APPT" value="05" /> No follow-up</span>
						<span><form:radiobutton path="ATTEND_FIRST_APPT" value="99" /> Dont Know</span> 
					</span>
<br/>
			</div>
			<div class="mLRTB">
				<span class="fieldHolder33">
					<span class="fLW35"> Post Test Counseling</span>
					<span class="fLW60"> <form:input path="POSTESTC_NEW" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35"> Referred to Care</span> 
					<span class="fLW60"> <form:input path="REF2" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35"> Provider</span> 
					<span class="fLW60"> <input path="" value="" class="standardInputMedText" readonly="true"/> </span>
				</span>
			</div>
			
		</div>	

		<!--  Partner Risk (Optional unless partner is new 900+) -->

		<div class="container borderSSDMDoc pB10">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> Partner Risk (Optional unless partner is new 900+):</span>
			</div>
			<div class="mLRTB">	 	
				<span class="fieldHolder49 ">
					<span class="fLW100"> <u>Within the past 12 months has the patient</u></span>
				</span>
				<span class="fieldHolder49">
					<span class="fLW100"> <u>Within the past 12 months has the patient</u></span> 
				</span>
			</div>	
			
			<div class="mLRTB">                                                                                              
				<br><br>
				<span class="fieldHolder49 ">
					<span class="fLW35"> Had Sex with a male</span>
					<span class="fLW60"> 
						<form:radiobutton path="SWM" value="Y" /> Yes 
						<form:radiobutton path="SWM" value="N" /> No
						<form:radiobutton path="SWM" value="R" /> Refused
						<form:radiobutton path="SWM" value="N" /> Didn't ask 
					</span>
				</span>
				<span class="fieldHolder49 ">
					<span class="fLW35"> Shared drug paraphenalia</span>
					<span class="fLW60"> 
						<form:radiobutton path="SHARED_DRUG_EQUIP" value="Y" /> Yes 
						<form:radiobutton path="SHARED_DRUG_EQUIP" value="N" /> No 
						<form:radiobutton path="SHARED_DRUG_EQUIP" value="R" /> Refused
						<form:radiobutton path="SHARED_DRUG_EQUIP" value="N" /> Didn't ask
					</span>
				</span>
				<span class="fieldHolder49">
					<span class="fLW35"> Had Sex with a female</span> 
					<span class="fLW60"> 
						<form:radiobutton path="SWF" value="Y" /> Yes 
						<form:radiobutton path="SWF" value="N" /> No 
						<form:radiobutton path="SWF" value="R" /> Refused
						<form:radiobutton path="SWF" value="N" /> Didn't ask
					</span>
				</span>
				<span class="fieldHolder49">
					<span class="fLW35"> Engaged in injection drug use?</span> 
					<span class="fLW60"> 
						<form:radiobutton path="IDU" value="Y" /> Yes 
						<form:radiobutton path="IDU" value="N" /> No  
						<form:radiobutton path="IDU" value="R" /> Refused
						<form:radiobutton path="IDU" value="N" /> Didn't ask
					</span>
				</span>
				<span class="fieldHolder49">
					<span class="fLW35"> Had Sex with a transgender person</span> 
					<span class="fLW60"> 
						<form:radiobutton path="SEX_W_TRANS" value="Y" /> Yes 
						<form:radiobutton path="SEX_W_TRANS" value="N" /> No
						<form:radiobutton path="SEX_W_TRANS" value="R" /> Refused
						<form:radiobutton path="SEX_W_TRANS" value="N" /> Didn't ask 
					</span>
				</span>
				<span class="fieldHolder49">
					<span class="fLW35"> Other risk, specify?</span> 
					<span class="fLW60"> <form:input path="RISK_OTHER" value="" class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder49">
					<span class="fLW35"> &nbsp; </span> 
					<span class="fLW60"> &nbsp; </span>
				</span>
				<span class="fieldHolder49" style="margin-top: 35px">
					<span class="fLW35"> Date Client Risk Collected:</span> 
					<span class="fLW60"> 
						<input path="" value="" class="standardInputSmallText" readonly="true"/> </span>
				</span>
			</div>
			<div class="mLRTB">
				<br><br>	
				<span class="fieldHolder49 ">
					<span style="float:left"> Comments</span>
					<span class="pL10">  
						<textarea rows="2" cols="78" path="COMMENTS" value="" readonly="true"/> </textarea> 
					</span>
				</span>
			</div>
		</div>	

		<!--  Drugs use and other Risk Behaviors -->

		<!-- <div class="container borderSSDMDoc">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> Drugs use and other Risk Behaviors:</span>
			</div>
			<div class="mLRTB">	 	
				<span class="fieldHolder49 ">
					<span class="fLW100"> <u>Within the past 12 months has the patient</u></span>
				</span>
				<span class="fieldHolder49">
					<span class="fLW100"></span> 
				</span>
			</div>	
			
			<div class="mLRTB">                                                                                              
				<br><br>
				<span class="fieldHolder49 ">
					<span class="fLW35"> Been incarcerated</span>
					<span class="fLW60"> <form:input path="SWM" value="" class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder49 ">
					<span class="fLW35"> Shared drug paraphenalia</span>
					<span class="fLW60"> <form:input path="SHARED_DRUG_EQUIP" value="" class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder49">
					<span class="fLW35"> Engaged in injection drug use?</span> 
					<span class="fLW60"> <form:input path="SWF" value="" class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder49">
					<span class="fLW35"> Other risk, specify?</span> 
					<span class="fLW60"> <form:input path="IDU" value="" class="standardInputSmallText" readonly="true"/> </span>
				</span>
			 	
				<span class="fieldHolder49 ">
					<span class="fLW100">During the past 12 months, which of the following injection or non-injection drugs have been used?</span>
					<span class="fLW35"> 
						<div>
							<input type="checkbox" path="RACE_WHITE" value="" /> Crack	
							<br>
							<input type="checkbox" path="RACE_BLACK" value="" /> Cocaine
							<br>
							<input type="checkbox" path="RACE_ASIAN" value="" /> Heroin
							<br>
							<input type="checkbox" path="RACE_INDIAN" value="" /> Methamphetamines
							<br>
						</div>	
					</span>	
					<span class="fLW60"> 	
						<div>
							<input type="checkbox" path="RACE_PACIFIC" value="" /> Nitrates/Poppers
							<br>
							<input type="checkbox" path="RACE_INDIAN" value="" /> Erectile dysfunction medications (e.g. Viagra)
							<br>
							<input type="checkbox" path="RACE_PACIFIC" value="" /> None
							<br>
							<input type="checkbox" path="RACE_OTHER" value="" /> Other <form:input path="RACE_OTHER" value="" class="standardinputSmallText2" />
							<br>
						</div>	
					</span>
				</span>
				<span class="fieldHolder49 ">
					<span class="fLW35"> Comments</span>
					<span class="fLW60">  
						<textarea rows="4" cols="50" path="COMMENTS" value="" readonly="true"/> </textarea> 
					</span>
				</span>
			</div>	
		</div> -->

		<!--  Comments -->

		<div class="container borderSSDMDoc pB15">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> Comments:</span>
			</div>
			<div class="mLRTB">	 	
				<span class="fieldHolder25 ">
					<span class="fLW100"> 
						<input type="checkbox" path="" value="" /> Flagged for Worker	
					</span>
				</span>
				<span class="fieldHolder75 ">
					<span class="fLW18"> Supervisor Comments</span>
					<span class="fLW75">  
						<textarea rows="2" cols="115" path="" value="" readonly="true"/> </textarea> 
					</span>
				</span>
			</div>	
			
			<div class="mLRTB">                                                                                              
				<br><br><br><br><br><br>
				<span class="fieldHolder25 ">
					<span class="fLW100"> 
						<input type="checkbox" path="" value="" /> Flagged for Supervisor	
					</span>
				</span>
				<span class="fieldHolder75 ">
					<span class="fLW18"> Worker Comments</span>
					<span class="fLW75">  
						<textarea rows="2" cols="115" path="COMMENTS" value="" readonly="true"/> </textarea> 
					</span>
				</span>
			</div>
			
		</div>
		<!--
		<div class="mT50">&nbsp;&nbsp;&nbsp;</div> dummy div 

		<hr>

		<div class="container borderSSDMDoc">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> </span>					
			</div>	 
			<div class="mLRTB">
				<span class="fieldHolder25">
					<span class="fLW35"> Nav</span> 
					<span class="fLW60"> <input path="" value="" class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder75"> 
					<button type="button">Save</button>
					<button type="button">Add New Partner</button>
					<button type="button">Field Record - Partner</button>
				</span>
			</div>
			<div class="mLRTB">	
				An asterisk (*) indicates more detail available. Move Mouse to items to display the detail.
			</div>						  
		</div>  -->	

</form:form>