<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>
<link href="${staticPath}/css/docs/partnerService.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<script src="${staticPath}/js/docs/partnerService/partnerService.js?v=${buildDate}"></script>

<form:form modelAttribute="psAssignmentView" id="partnerService" >



<form:errors path="*" cssClass="errorblock" element="div" />

	<div id="PartnerDoc">
		<div class="headingCls centerDoc">
				Partner Service Index Case Details
		</div>	
		<div class="container paddingBottom5" style="width:100%; float:right">
			<div style="float:right; width:25%">
				<span class="fLW40"> Report ID:</span>
				<span class="fLW60"> <form:input path="LEGACY_REPORT_ID"  class="standardInputSmallText" readonly="true"/> </span>
			</div>							
		</div>
		<div class="container paddingBottom5" style="width:100%; float:right">
			<div style="float:left; ">
				<span class="fLW35"> <spring:message code="label.stateNo" />:</span> 
				<span class="fLW60"> <form:input path="STATENO"  class="standardInputSmallText" readonly="true"/> </span>
			</div>
			<div style="float:right; width:25%">
				<span class="fLW40"> Assign ID:</span>
				<span class="fLW60"> <form:input id="assignment_id" path="LEGACY_ASSIGNMENT_ID"  class="standardInputSmallText" readonly="true"/> </span>
			</div>	
							
		</div>
		<!--  Partner Service Details -->
		
		<div class="container borderSSDMDoc">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> </span>					
			</div>	 
			<div class=" mT20 mL10">
				<span class="fieldHolder33">
					<span class="fLW35"> Event Description</span>
					<span class="fLW60"> <form:input path="EVENT_DESC"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35"> Entry Date</span>
					<fmt:formatDate value="${psAssignmentView.CREATE_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedCREATE_DATE" />
					<span class="fLW60"> <form:input path="CREATE_DATE" value="${theFormattedCREATE_DATE}" class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder33"> 
					<span class="fLW35">Supervisor </span>	
					<span class="fLW60"> <form:input path="LAST_SUPERV_ASSIGNED_NAME"  class="standardInputSmallText" readonly="true"/> </span>
				</span>	
				
			
			</div>
			<br>
			<div class="mLRTB">	
				<span class="fieldHolder33">
					<span class="fLW35"> Assigned Date </span> 
					<fmt:formatDate value="${psAssignmentView.LAST_WORKER_ASSIGNED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedLAST_WORKER_ASSIGNED_DATE" />
					<span class="fLW60"> <form:input path="LAST_WORKER_ASSIGNED_DATE" value="${theFormattedLAST_WORKER_ASSIGNED_DATE}" class="standardInputSmallText" /> </span>
				</span>
				<span class="fieldHolder33"> 
					<span class="fLW35">Worker </span>	
					<span class="fLW60"> <form:input path="LAST_WORKER_ASSIGNED_NAME"  class="standardInputSmallText" readonly="true"/> </span>
				</span>	
				
					
				
	<%-- tempararoy			<span class="fieldHolder33"> 
					<span class="fLW35"> Partners Status</span>
					<span class="fLW60"> <form:input path=""  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35"> Assignment ID</span> 
					<span class="fLW60"> <form:input path="assignment_id"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				
				
				<span class="fieldHolder25"> 
					<span class="fLW35"> Assignment History</span>
					<span class="fLW60"> <form:input path=""  class="standardInputSmallText" readonly="true"/> </span>
				</span>	 --%>
			</div>
			<br>
			<div class="mLRTB">
				<span class="fieldHolder33">
					<span class="fLW35"> Closed Date </span> 
					<fmt:formatDate value="${psAssignmentView.SYSTEM_CLOSE_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedSYSTEM_CLOSE_DATE" />
					<span class="fLW60"> <form:input path="SYSTEM_CLOSE_DATE" value="${theFormattedSYSTEM_CLOSE_DATE}" class="standardInputSmallText" /> </span>
				</span>
				<span class="fieldHolder33 ">
					<span class="fLW35">Worker Closed Date</span>
					<fmt:formatDate value="${psAssignmentView.WORKER_CLOSE_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedWORKER_CLOSE_DATE" />
					<span class="fLW60"> <form:input path="WORKER_CLOSE_DATE" value="${theFormattedWORKER_CLOSE_DATE}" class="standardInputSmallText" readonly="true"/> </span>	
				</span>
				<span class="fieldHolder33 ">
					<span class="fLW35">Duplicate Stateno</span>
					<span class="fLW60"> <form:input path="INV_DUP_STATENO"  class="standardInputSmallText" readonly="true"/> </span>	
				</span>
				<br>
			</div>	
			
			<div class="mLRTB">
			<span class="fieldHolder33">
					<span class="fLW35"> Investigation Status</span>
					
					<span class="fLW60"> 
						<form:select path="INVESTIGATION_STATUS_CD"  class="" readonly="true">
							<form:option value="" >--select--</form:option>
								<c:forEach items="${investigationStatusList}" var="investigationStatu">
									<form:option value="${investigationStatu.INVESTIGATION_STATUS_CD}" label="${investigationStatu.INVESTIGATION_STATUS_DESC}" name="${investigationStatu.INVESTIGATION_STATUS_DESC}"></form:option>
								</c:forEach>
						</form:select>
					 </span>
				</span>	
			</div>
			<br/><br/>					  
		</div>

		<!--  Demographics Details -->

		<div class="container borderSSDMDoc">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> Demographics:</span>					
			</div>	 
			<div class="mLRTB">
				<span class="fieldHolder25 ">
					<span class="fLW35"> Last Name</span>
					<span class="fLW60"> <form:input path="LNAME"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35"> First Name</span> 
					<span class="fLW60"> <form:input path="FNAME"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Middle Name</span>
					<span class="fLW60"> <form:input path="MNAME"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
<!-- 				<span class="fieldHolder25 "> -->
<!-- 					<span class="fLW35"> Suffix</span> -->
<!-- 					<span class="fLW60"> <form:input path=""  class="standardInputSmallText" readonly="true"/> </span>	 -->
<!-- 				</span>	 -->
			</div>

			<div class="mLRTB">	
				<br><br>				
				<span class="fieldHolder25"> 
					<span class="fLW35"> SSN</span>
					<span class="fLW60"> <form:input path="SSN"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35"> Age </span> 
					<span class="fLW60"> <form:input path="AGE"  class="standardInputSmallText" /> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35"> Marital Status </span> 
					<span class="fLW60"> <form:input path="MARITAL_STATUS"  class="standardInputSmallText" /> </span>
				</span>	
<!-- 				<span class="fieldHolder25 "> -->
<!-- 					<span class="fLW35"> Maiden Name</span> -->
<!-- 					<span class="fLW60"> <form:input path=""  class="standardInputSmallText" readonly="true"/> </span> -->
<!-- 				</span>			 -->
			</div>

			<div class="mLRTB" style="border-bottom: 1px dotted; height: 45px;">
				<br>
				<span class="fieldHolder25"> 
					<span class="fLW35">Date of Birth </span>	
					<span class="fLW60"> <form:input path="DOB_TEXT"  class="standardInputSmallText" readonly="true"/> </span>
				</span>	
<!-- 				<span class="fieldHolder25"> -->
<!-- 					<span class="fLW35"> Birth Country </span>  -->
<!-- 					<span class="fLW60"> <form:input path=""  class="standardInputSmallText" /> </span> -->
<!-- 				</span>	 -->
			</div>

			<div class="mLRTB">	
				<span class="fieldHolder49" style="width:50%"> 
					<span class="fLW18">Address </span>	
					<span class="fLW60"> <form:input path="PERSON_ADDRESS"  class="standardInputSmallText" style="width: 425px" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35"> Telephone</span>
					<span class="fLW60"> <form:input path=""  class="standardInputSmallText" /> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35"> Work Phone </span>
					<span class="fLW60"> <form:input path="WORK_PHONE"  class="standardInputSmallText" /> </span>
				</span>
				<br>
			</div>

			<div class="mLRTB" style="height: 30px;">
				<br>
				<span class="fieldHolder25"> 
					<span class="fLW35">City</span>	
					<span class="fLW60"> <form:input path="PERSON_CITY"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35">State </span> 
					<span class="fLW60"> <form:input path="PERSON_STATE"  class="standardInputSmallText" /> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35">Zip</span>
					<span class="fLW60"> <form:input path="PERSON_ZIP"  class="standardInputSmallText" /> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35">County</span>
					<span class="fLW60"> 
						<form:select path="PERSON_COUNTY"  cssClass="oAdrDlg roundedBrdr newCounty" disabled="true"> 
										<form:option value=""></form:option>
										<form:options items="${sessionScope.countyMap}"/>
									</form:select>
						</span>
				</span>
			</div>
			
			
			<div class="mLRTB" style="border-bottom: 1px dotted; height: 45px;">
				<br>
				<span class="fieldHolder25"> 
					<span class="fLW35">Cell Phone</span>	
					<span class="fLW60"> <form:input path="CELL_PHONE"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35">Pager </span> 
					<span class="fLW60"> <form:input path="PAGER"  class="standardInputSmallText" /> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35">Web Address/ Screen Name</span>
					<span class="fLW60"> <form:input path="WEB_ADDRESS_SCREEN"  class="standardInputSmallText" /> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35">Email Address</span>
					<span class="fLW60"> <form:input path="EMAIL"  class="standardInputSmallText" /> </span>
				</span>
			</div>
			<div class="clearBoth"></div>
			<div class="mLRTB">			
				<span class="fieldHolder25">
					<span class="fLW35"> Time in Address</span>
					<c:set var="timeInAddress" value="${TIME_IN_ADDRESS} ${ADDRESS_TIME_UNIT}" />
					<span class="fLW60"> <input  value="${timeInAddress}" class="standardInputSmallText" /> </span>
				</span>
				<span class="fieldHolder25"> 
					<span class="fLW35">Time in State </span>	
					<c:set var="timeInState" value="${TIME_IN_STATE} ${STATE_TIME_UNIT}" />
					<span class="fLW60"> <input  value="${timeInState}" class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35"> Time in Country </span> 
					<c:set var="timeInCountry" value="${TIME_IN_COUNTRY} ${COUNTRY_TIME_UNIT}" />
					<span class="fLW60"> <input value="${timeInCountry}" class="standardInputSmallText" /> </span>
				</span>
			</div>	
			<br>
			<div class="mLRTB">
				<span class="fieldHolder25">
					<span class="fLW35"> Living With </span> 
					<span class="fLW60"> <form:input path="LIVING_WITH"  class="standardInputSmallText" /> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35"> Residence Type</span>
					<span class="fLW60"> <form:input path="RESIDENCE_TYPE"  class="standardInputSmallText" /> </span>
				</span>
			</div>
			<br>
			<div class="mLRTB">					
				<span class="fieldHolder25">
					<span class="fLW35"> Currently Institutionalized </span> 
					<span class="fLW60"> 
						<form:radiobutton path="CURRENTLY_INSTITUTE_ANS" value="Y" /> <spring:message code="label.yes" />
						<form:radiobutton path="CURRENTLY_INSTITUTE_ANS" value="N" /> <spring:message code="label.no" />
						<form:radiobutton path="CURRENTLY_INSTITUTE_ANS" value="U" /> <spring:message code="label.unknown" />
					 </span>
				</span>
				<span class="fieldHolder25"> 
					<span class="fLW35">Name of Institution </span>	
					<span class="fLW60"> <form:input path="INSTITUTE_NAME"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35"> Institution Type </span> 
					<span class="fLW60"> <form:input path="INSTITUTE_CD"  class="standardInputSmallText" /> </span>
				</span>	<br>		
			</div>								  
		</div>

		<div id="raceEthnicity" class="container borderSSDMDoc" style="border-top:1px dotted; padding:10px 0 5px 0;">

<!-- 			<div style="float:left; width:35%; margin-left:20px; border-right:1px dotted;">			 -->
<!-- 				<div> Race (Select all that apply)</div>  -->
<!-- 				<div style="border-bottom: dotted 1px; margin-right:10px; padding-bottom: 10px"> -->
<%-- 					<form:checkbox path="" value="${raceWhite}" /> White	 --%>
<!-- 					<br> -->
<%-- 					<form:checkbox path="" value="${raceBlack}" /> African American --%>
<!-- 					<br> -->
<%-- 					<form:checkbox path="" value="${raceAsian}" /> Asian --%>
<!-- 					<br> -->
<%-- 					<form:checkbox path="" value="${raceIndian}" /> American Indian/ Alaskan Native --%>
<!-- 					<br> -->
<%-- 					<form:checkbox path="" value="${racePacific}" /> Pacific Islander --%>
<!-- 					<br> -->
<%-- 					<form:checkbox path="" value="${raceOther}" /> Other <form:input path=""  class="standardinputSmallText2" /> --%>
<!-- 					<br> -->
<!-- 					<br> -->
<%-- 					<form:checkbox path="" value="${raceOther}" /> Unknown --%>
<!-- 					<br> -->
<!-- 				</div>				 -->
<!-- 			</div> -->
<!-- 			<div class=" " style="float:left; padding-left:10px; width:25% ;"> -->
<!-- 				<div style=" border-bottom: 1px dotted; height: 35px; margin-bottom: 10px; width:92%"> -->
<!-- 					<span class=""> -->
<!-- 						<span class="fLW35"> Sex At Birth </span>  -->
<!-- 						<span class="fLW60">  -->
<%-- 							<form:radiobutton path="" value="M" /> Male  --%>
<%-- 							<form:radiobutton path="" value="F" /> Female --%>
<!-- 						</span> -->
<!-- 					</span> -->
<!-- 				</div> -->
<!-- 				<div style=" border-bottom: 1px dotted; height: 35px; margin-bottom: 10px; width:92%"> -->
<!-- 					<span class=""> -->
<!-- 						<span class="fLW35"> Current Gender </span>  -->
<!-- 						<span class="fLW60">  -->
<%-- 							<form:radiobutton path="" value="M" /> Male  --%>
<%-- 							<form:radiobutton path="" value="F" /> Female --%>
<!-- 						</span> -->
<!-- 					</span> -->
<!-- 				</div> -->
<!-- 				<div > -->
<%-- 					<span><spring:message code="label.ethnicity" />  (Hispanic or Latino)</span> --%>
<!-- 					<div>  -->
<%-- 						<form:radiobutton path="" value="01" /> <spring:message code="label.yes" /><br> --%>
<%-- 						<form:radiobutton path="" value="02" /> <spring:message code="label.no" /><br> --%>
<%-- 						<form:radiobutton path="" value="09" /> <spring:message code="label.unknown" /> --%>
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->

			
			
			<!--  Medical details -->
			
			<div style="float:left; width:35% ; padding-left:10px; padding-bottom: 12px; border-left:1px dotted;">
						
<!-- 				<div class="mT5 container"> -->
<!-- 					<div style="float:left; width:45%"> -->
<!-- 						Medical Record No -->
<!-- 					</div> -->
<!-- 					<div style="float:left"> -->
<!-- 						<form:input path=""  class="standardInputSmallText" readonly="true"/> -->
<!-- 					</div> -->
<!-- 				</div>					 -->
				<div class="mT5 container">
					<div style="float:left; width:45%">
						English Speaking
					</div>
					<div style="float:left">
						<form:radiobutton path="ENGLISH_SPEAKING" value="Y" /> <spring:message code="label.yes" />
						<form:radiobutton path="ENGLISH_SPEAKING" value="N" /> <spring:message code="label.no" />
						<form:radiobutton path="ENGLISH_SPEAKING" value="U" /> <spring:message code="label.unknown" />
					</div>
				</div>
				<div class="mT5 container" style="paddinf-bottom: 5px">
					<div style="float:left; width:45%">
						Primary Language
					</div>
					<div style="float:left">
						<form:input path="PRIMARY_LANGUAGE"  class="standardInputSmallText" />
					</div>
				</div>	
				<div class="mT5 container" style="border-top: dotted 1px; padding-top: 10px">
					<div style="float:left; width:45%">
						CTR Code
					</div>
					<div style="float:left">
						<form:input path="CTR_AIRS_ID"  class="standardInputSmallText" readonly="true"/>
					</div>
				</div>
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Date Demo Collected
					</div>
					<div style="float:left">
						<fmt:formatDate value="${psAssignmentView.DEMO_COLLECTION_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDEMO_COLLECTION_DATE" />
						<form:input path="DEMO_COLLECTION_DATE" value="${theFormattedDEMO_COLLECTION_DATE}"  class="standardInputSmallText" readonly="true"/>
					</div>
				</div>			
			</div>
		</div>
		<div class="container borderSSDMDoc" style="padding: 10px 0">	
			<span class="fieldHolder25" > 
				<span class="fLW100" style="padding-left: 10px"><b>Emergency Contact</b></span>
			</span>
			<span class="fieldHolder25">
				<span class="fLW35">Name </span> 
				<span class="fLW60"> <form:input path="EMERGENCY_NAME"  class="standardInputSmallText" /> </span>
			</span>
			<span class="fieldHolder25">
				<span class="fLW35">Phone</span>
				<span class="fLW60"> <form:input path="EMERGENCY_PHONE"  class="standardInputSmallText" /> </span>
			</span>
			<span class="fieldHolder25">
				<span class="fLW35">Relationship</span>
				<span class="fLW60"> <form:input path="EMERGENCY_RELATIONSHIP"  class="standardInputSmallText" /> </span>
			</span>
			<br>
		</div>

		<!--  Pregnancy Information -->

		<div class="container borderSSDMDoc">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> Pregnant Information:</span>					
			</div>	 
			<div class="mLRTB">
				<span class="fieldHolder25 ">
					<span class="fLW35"> Pregnant at Exam</span>
					<span class="fLW60"> <form:input path="PREGNANT_AT_EXAM"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35"> Number of Weeks (Exam)</span> 
					<span class="fLW60"> <form:input path="NUM_WKS_PREG_AT_EXAM"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Pregnant at Interview</span>
					<span class="fLW60"> <form:input path="PREG_AT_INTERVIEW"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Number of Weeks (Interview)</span>
					<span class="fLW60"> <form:input path="NUM_WKS_PREG_AT_NUMBER"  class="standardInputSmallText" readonly="true"/> </span>	
				</span>	
			</div>
			<div class="mLRTB">
				<br><br>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Pregnant last 12 months</span>
					<span class="fLW60"> <form:input path="PREG_IN_LAST_YR"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35"> Pregnancy outcome</span> 
					<span class="fLW60"> <form:input path="PREGNANCY_OUTCOME"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Currently in Prenatal Care</span>
					<span class="fLW60"> <form:input path="CURR_PRENATAL_CARE"  class="standardInputSmallText" readonly="true"/> </span>
				</span>	
			</div>
		</div>

		<!--  Investigation Information -->

		<div class="container borderSSDMDoc">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> Investigation Information:</span>					
			</div>	 
			<div class="mLRTB">
				<span class="fieldHolder25 ">
					<span class="fLW35"> Method of Case Detection</span>
					<span class="fLW60"> <form:input path="METHOD_CASE_DETECTION"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35"> Defined Other</span> 
					<span class="fLW60"> <form:input path="METHOD_CASE_DETECTION_OTHER"  class="standardInputSmallText" readonly="true"/> </span>
				</span>	
				<span class="fieldHolder25">
					<span class="fLW35"> Enrollment</span> 
					<span class="fLW60"> <form:input path="ENROLLMENT"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
			</div>
			<div class="mLRTB">
				<br><br><br>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Interviewed (IX)</span>
					<span class="fLW60"> <form:input path="INTERVIEWED_IX"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> If no, why not</span>
					<span class="fLW60"> <form:input path="WHY_NOT_INTERVIEWED"  class="standardInputSmallText" readonly="true"/> </span>	
				</span>	
				<span class="fieldHolder25 ">
					<span class="fLW35"> Other</span>
					<span class="fLW60"> <form:input path="INTERVIEWED_OTHER"  class="standardInputSmallText" readonly="true"/> </span>
				</span>	
			</div>
			<div class="mLRTB">
				<br><br>
				<span class="fieldHolder25 ">
					<span class="fLW35"> IX Period (months)</span>
					<span class="fLW60"> <form:input path="INTERVIEWED_PERIOD_MO"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35"> Place of IX</span> 
					<span class="fLW60"> <form:input path="INTERVIEWED_LOCATION"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Other</span>
					<span class="fLW60"> <form:input path="INTERVIEWED_LOCATION_OTHER"  class="standardInputSmallText" readonly="true"/> </span>
				</span>	
			</div>
			<div class="mLRTB">
				<br><br>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Date IX Assigned</span>
					<fmt:formatDate value="${psAssignmentView.INTERVIEWED_ASSIGNED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedINTERVIEWED_ASSIGNED_DATE" />
					<span class="fLW60"> <form:input path="INTERVIEWED_ASSIGNED_DATE" value="${theFormattedINTERVIEWED_ASSIGNED_DATE}" class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> DIS # Assigned IX</span>
					<span class="fLW60"> <form:input path="DIS_ASSIGNED"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35"> Date IXed</span> 
					<fmt:formatDate value="${psAssignmentView.INTERVIEWED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedINTERVIEWED_DATE" />
					<span class="fLW60"> <form:input path="INTERVIEWED_DATE" value="${theFormattedINTERVIEWED_DATE}" class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> DIS # Conducted IX</span>
					<span class="fLW60"> <form:input path="DIS_CONDUCTED"  class="standardInputSmallText" readonly="true"/> </span>
				</span>					
			</div>
			
			<div class="mLRTB">
				<br><br>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Date IX ReAssigned</span>
					<fmt:formatDate value="${psAssignmentView.INTERVIEWED_REASSIGN_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedINTERVIEWED_REASSIGN_DATE" />
					<span class="fLW60"> <form:input path="INTERVIEWED_REASSIGN_DATE" value="${theFormattedINTERVIEWED_REASSIGN_DATE}" class="standardInputSmallText" readonly="true"/> </span>
				</span>	
				<span class="fieldHolder25">
					<span class="fLW35"> DIS # Reassigned IX</span> 
					<span class="fLW60"> <form:input path="DIS_REASSIGNED"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Date ReIXed</span>
					<fmt:formatDate value="${psAssignmentView.INTERVIEW_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedINTERVIEW_DATE" />
					<span class="fLW60"> <form:input path="INTERVIEW_DATE" value="${theFormattedINTERVIEW_DATE}"  class="standardInputSmallText" readonly="true"/> </span>
				</span>				<!-- TODO check this field -->
				<span class="fieldHolder25 ">
					<span class="fLW35"> DIS # Conducted ReIX</span>
					<span class="fLW60"> <form:input path="DIS_CONDUCT_INTERVIEW"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
			</div>
		</div>
		
		<!--  Patient Risk Information -->

		<div class="container borderSSDMDoc">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> Patient Risk:</span>
			</div>	 
			<div class="mLRTB">
				<span class="fieldHolder100 ">
					<span class="fLW100 "> <b> Sexual Behaviors: </b> </span>
				</span>
			</div>		
			<div class="mLRTB">
				<span class="fieldHolder25 ">
					<span class="fLW100"> <u>Within the past 12 months has the patient</u></span>
				</span>
			</div>
			<br><br>
			<div class="mLRTB">
				<span class="fieldHolder49 ">
					<span class="fLW35"> Had sex with a male?</span>
					<span class="fLW60"> 
							<form:select path="SWM"  class="standardInputMedText">
								<form:option value=""></form:option>
								<form:options items="${riskList}"/>
							</form:select> 
							 
					</span>
				</span>
				<span class="fieldHolder49">
					<span class="fLW35"> Had sex with a female?</span> 
					<span class="fLW60"> 
						<form:select path="SWF"   class="standardInputMedText">
								<form:option value=""></form:option>
								<form:options items="${riskList}"/>
							</form:select> 
					</span>
				</span>
			</div>
			<div class="mLRTB">
				<br><br>
				<span class="fieldHolder49">
					<span class="fLW35"> Had sex with a transgender person?</span> 
					<span class="fLW60"> 
						<form:select path="SEX_W_TRANS"   class="standardInputMedText">
								<form:option value=""></form:option>
								<form:options items="${riskList}"/>
							</form:select> 
						   
					</span>
				</span>
				<span class="fieldHolder49">
					<span class="fLW35"> Exchanged drugs/money for Sex</span> 
					<span class="fLW60"> 
						<form:select path="EXCHG_DRUGS_FOR_SEX"   class="standardInputMedText">
								<form:option value=""></form:option>
								<form:options items="${riskList}"/>
							</form:select> 
					</span>
				</span>	
			</div>
			<div class="mLRTB">
				<br><br>	
				<span class="fieldHolder49">
					<span class="fLW35"> [Females only] Had sex with a person who is known to her to be an MSM?</span> 
					<span class="fLW60">
						 <form:select path="SEX_PARTNER_MSM"   class="standardInputMedText">
								<form:option value=""></form:option>
								<form:options items="${riskList}"/>
							</form:select> 
						
					</span>
				</span>
				<span class="fieldHolder49">
					<span class="fLW35"> Had sex with a person who is known to him/her to be an IDU?</span> 
					<span class="fLW60"> 
						 <form:select path="SEX_PARTNER_IDU"   class="standardInputMedText">
								<form:option value=""></form:option>
								<form:options items="${riskList}"/>
							</form:select> 
						  
					</span>
				</span>
			</div>
		</div>	

		

		<!--  Drugs use and other Risk Behaviors -->

		<div class="container borderSSDMDoc">
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
				<br> <br>
				<span class="fieldHolder49">
					<span class="fLW35"> Engaged in injection drug use?</span> 
					<span class="fLW60"> 
						 <form:select path="IDU"   class="standardInputMedText">
								<form:option value=""></form:option>
								<form:options items="${riskList}"/>
							</form:select> 
						
					</span>
				
				<br><br>
			
					<span class="fLW35"> Shared drug paraphenalia</span>
					<span class="fLW60"> <form:input path="SHARED_DRUG_EQUIP"  class="standardInputSmallText" readonly="true"/> </span>
				
				<br><br>
			
					<span class="fLW35"> Been incarcerated</span>
					<span class="fLW60"> <form:input path="INCARCERATED"  class="standardInputSmallText" readonly="true"/> </span>
				
				<br><br>
				
					<span class="fLW35"> Other risk, specify?</span> 
					<span class="fLW60"> <form:input path="SEX_OTHER"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder49 "  style="padding-bottom:10px">
					<span class="fLW100">During the past 12 months, which of the following injection or non-injection drugs have been used?</span>
					<span class="fLW35"> 
						<div>
							<form:checkbox path="CRACK" value="1" /> Crack	
							<br>
							<form:checkbox path="COCAINE" value="1" /> Cocaine
							<br>
							<form:checkbox path="HEROIN" value="1" /> Heroin
							<br>
							<form:checkbox path="METH" value="1" /> Methamphetamines
							<br>
						</div>	
					</span>	
					<span class="fLW60"> 	
						<div>
							<form:checkbox path="NITRATES" value="1" /> Nitrates/Poppers
							<br>
							<form:checkbox path="ED_MEDS" value="1" /> Erectile dysfunction medications (e.g. Viagra)
							<br>
							<form:checkbox path="NONE" value="1" /> None
							<br>
							<%--	<form:checkbox path="DRUGS_OTHER"  /> --%>
							Other <form:input path="DRUGS_OTHER"  class="standardinputSmallText2" /> 
							<br>
						</div>	
					</span>
				</span>
				<br><br>
				<div style="clear:both; padding-top: 10px">
					<span style="float: left; width: 18%"> Comments</span>
					<span class="fLW100">  
						<form:textarea rows="2" cols="175" path="RISK_OTHER_TEXT"  readonly="true"/> 
					</span>
				</div>
			</div>	
		</div>

		<!--  Social History Information -->

		<div class="container borderSSDMDoc">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> Social History:</span>					
			</div>	 
			<div>
				<span class="fieldHolder49 " style="border-right: 1px dotted; margin-right: 20px;">
					<div class="mLRTB">
						<span class="fieldHolder95 " style="margin: 0 0 10px 0">
							<span class="fLW35 " style="font-weight:bold; width: 40%">  Were places to meet partner given </span>
							<span class="fLW60"> 
								<form:radiobutton path="PLACES_MET_GIVEN" value="Y" /> <spring:message code="label.yes" />
								<form:radiobutton path="PLACES_MET_GIVEN" value="N" /> <spring:message code="label.no" />
								<form:radiobutton path="PLACES_MET_GIVEN" value="R" /> <spring:message code="label.refused" />
								<form:radiobutton path="PLACES_MET_GIVEN" value="D" /> <spring:message code="label.didntask" />
								
							</span>
						</span>
					</div>
					<div>
						<span class="fieldHolder49 " style="margin: 0 0 5px 10px">
							<span class="fLW18"> 1.) Type </span>
							<span class="fLW60"> <form:input path="PLACE_MET1"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
						<span class="fieldHolder49">
							<span class="fLW18">  Name </span> 
							<span class="fLW60"> <form:input path="PLACE_MET_NAME1"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
					</div>
					<div>
						<span class="fieldHolder49 " style="margin: 0 0 5px 10px">
							<span class="fLW18"> 2.) Type </span>
							<span class="fLW60"> <form:input path="PLACE_MET2"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
						<span class="fieldHolder49">
							<span class="fLW18">  Name </span> 
							<span class="fLW60"> <form:input path="PLACE_MET_NAME2"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
					</div>
					<div>
						<span class="fieldHolder49 " style="margin: 0 0 5px 10px">
							<span class="fLW18"> 3.) Type </span>
							<span class="fLW60"> <form:input path="PLACE_MET3"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
						<span class="fieldHolder49">
							<span class="fLW18">  Name </span> 
							<span class="fLW60"> <form:input path="PLACE_MET_NAME3"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
					</div>
					<div>
						<span class="fieldHolder49 " style="margin: 0 0 5px 10px">
							<span class="fLW18"> 4.) Type</span>
							<span class="fLW60"> <form:input path="PLACE_MET4"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
						<span class="fieldHolder49">
							<span class="fLW18">  Name</span> 
							<span class="fLW60"> <form:input path="PLACE_MET_NAME4"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
					</div>
					<div>
						<span class="fieldHolder49 " style="margin: 0 0 5px 10px">
							<span class="fLW18"> 5.) Type </span>
							<span class="fLW60"> <form:input path="PLACE_MET5"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
						<span class="fieldHolder49">
							<span class="fLW18"> Name </span> 
							<span class="fLW60"> <form:input path="PLACE_MET_NAME5"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
					</div>
				</span>

				<span class="fieldHolder49 ">
					<div class="mLRTB" style="margin-left:0">				
						<span class="fieldHolder95 "  style="margin: 0 0 10px 0">
							<span class="fLW35 " style="font-weight:bold; width: 40%; ">  Were places to have Sex given?</span>
						
							<span class="fLW60"> 
									<form:radiobutton path="SEX_PLACES_GIVEN" value="Y" /> <spring:message code="label.yes" />
									<form:radiobutton path="SEX_PLACES_GIVEN" value="N" /> <spring:message code="label.no" />
									<form:radiobutton path="SEX_PLACES_GIVEN" value="R" /> <spring:message code="label.refused" />
									<form:radiobutton path="SEX_PLACES_GIVEN" value="D" /> <spring:message code="label.didntask" />
									
							</span>
						</span>
					</div>
					<div>
						<span class="fieldHolder49 " style="margin: 0 0 5px 0">
							<span class="fLW18"> 1.) Type </span>
							<span class="fLW60"> <form:input path="PLACE_SEX1"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
						<span class="fieldHolder49" style="margin: 0 0 5px 0">
							<span class="fLW18">  Name </span> 
							<span class="fLW60"> <form:input path="PLACE_SEX_NAME1"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
					</div>
					<div>
						<span class="fieldHolder49 " style="margin: 0 0 5px 0">
							<span class="fLW18"> 2.) Type </span>
							<span class="fLW60"> <form:input path="PLACE_SEX2"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
						<span class="fieldHolder49" style="margin: 0 0 5px 0">
							<span class="fLW18">  Name </span> 
							<span class="fLW60"> <form:input path="PLACE_SEX_NAME2"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
					</div>
					<div>
						<span class="fieldHolder49 " style="margin: 0 0 5px 0">
							<span class="fLW18"> 3.) Type </span>
							<span class="fLW60"> <form:input path="PLACE_SEX3"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
						<span class="fieldHolder49" style="margin: 0 0 5px 0">
							<span class="fLW18">  Name </span> 
							<span class="fLW60"> <form:input path="PLACE_SEX_NAME3"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
					</div>
					<div>
						<span class="fieldHolder49 " style="margin: 0 0 5px 0">
							<span class="fLW18"> 4.)  Type </span>
							<span class="fLW60"> <form:input path="PLACE_SEX4"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
						<span class="fieldHolder49" style="margin: 0 0 5px 0">
							<span class="fLW18">  Name </span> 
							<span class="fLW60"> <form:input path="PLACE_SEX_NAME4"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
					</div>
					<div>
						<span class="fieldHolder49 " style="margin: 0 0 5px 0">
							<span class="fLW18"> 5.) Type </span>
							<span class="fLW60"> <form:input path="PLACE_SEX5"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
						<span class="fieldHolder49" style="margin: 0 0 5px 0">
							<span class="fLW18">  Name </span> 
							<span class="fLW60"> <form:input path="PLACE_SEX_NAME5"  class="standardInputSmallText2" readonly="true"/> </span>
						</span>
					</div>
				</span>
				
				<div class="pT10 mL10">
					<span style="float: left; width: 18%"> Comments</span>
					<span class="fLW100">  
						<form:textarea rows="2" cols="175" path="SOCIAL_HIST_COMMENTS"  readonly="true"/> 
					</span>
				</div>
			</div>
		</div>	

		<!--  Partner Type Summary -->

		<div class="container borderSSDMDoc">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> Partner Type Summary:</span>					
			</div>	 
			<div class="mLRTB">
				<span class="fieldHolder95 ">
					<span class="fLW100 "> <b>Partners in last 12 months</b></span>
				</span>
			</div>	 
			<div class="mLRTB">	
				<br><br>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Female partners</span>
					<span class="fLW60"> <form:input path="CLAIMED_FEM_PART"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35"> Number Female Partners</span> 
					<span class="fLW60"> <form:input path="CLAIMED_NO_FEM_PART"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Male partners</span>
					<span class="fLW60"> <form:input path="CLAIMED_MALE_PART"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Number Male Partners</span>
					<span class="fLW60"> <form:input path="CLAIMED_NO_MALE_PART"  class="standardInputSmallText" readonly="true"/> </span>	
				</span>	
				<span class="fieldHolder25 ">
					<span class="fLW35"> Transgender partners</span>
					<span class="fLW60"> <form:input path="CLAIMED_TRANS_PART"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Number Transgender Partners</span>
					<span class="fLW60"> <form:input path="CLAIMED_NO_TRANS_PART"  class="standardInputSmallText" readonly="true"/> </span>	
				</span>	
			</div>
			<div class="mLRTB">
				<span class="fieldHolder95 ">
					<span class="fLW100 "> <b> Partners in interview period </b></span>
				</span>
			</div>	 
			<div class="mLRTB">
				<br><br>
				<span class="fieldHolder25">
					<span class="fLW35"> Number Sex</span> 
					<span class="fLW60"> <form:input path="NUMBER_SEX_PARTNERS"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Number Needle Sharing</span>
					<span class="fLW60"> <form:input path="NUMBER_NS_PARTNERS"  class="standardInputSmallText" readonly="true"/> </span>
				</span>	
				<span class="fieldHolder25">
					<span class="fLW35"> Number Both</span> 
					<span class="fLW60"> <form:input path="NUMBER_BOTH"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<br>
			</div>	 
			<br>
			<div class="mLRTB">	
				<br><br>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Female partners</span>
					<span class="fLW60"> <form:input path="NAMED_FEM_PART"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25">
					<span class="fLW35"> Number Female Partners</span> 
					<span class="fLW60"> <form:input path="NAMED_NO_FEM_PART"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Male partners</span>
					<span class="fLW60"> <form:input path="NAMED_MALE_PART"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Number Male Partners</span>
					<span class="fLW60"> <form:input path="NAMED_NO_MALE_PART"  class="standardInputSmallText" readonly="true"/> </span>	
				</span>	
				<span class="fieldHolder25 ">
					<span class="fLW35"> Transgender partners</span>
					<span class="fLW60"> <form:input path="NAMED_TRANS_PART"  class="standardInputSmallText" readonly="true"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35"> Number Transgender Partners</span>
					<span class="fLW60"> <form:input path="NAMED_NO_TRANS_PART"  class="standardInputSmallText" readonly="true"/> </span>	
				</span>	
				<br><br>
			</div>
<!-- 			<div class="container borderSSDMDoc"> -->
<!-- 				<div class="divHeadingCls bGHeading"> -->
<!-- 					<span class=" mL2"> Events:</span>				 -->
<!-- 				</div>	   -->
<!-- 				<div class="mLRTB">	 -->
<!-- 					<span class="fieldHolder25 "  style="padding-bottom:10px"> -->
<!-- 						<span class="fLW35"> Raw Test Result</span> -->
<!-- 						<span class="fLW60"> <form:input path=""  class="standardInputSmallText" readonly="true"/> </span>	 -->
<!-- 					</span> -->
<!-- 				</div> -->
<!-- 			</div>	 -->
			<div class="container borderSSDMDoc">
				<div class="divHeadingCls bGHeading">
					<span class=" mL2"> Providers:</span>				
				</div>
				<div class="mLRTB">
					<span class="fieldHolder95 ">
						<span class="fLW100 "> <b> Provider Information </b></span>
					</span>
				</div>	  
				<div class="mLRTB">	
					<br>
					<span class="fieldHolder25 ">
						<span class="fLW35">Provider</span>
						<span class="fLW60"> <form:input path="PROV_NAME"  class="standardInputSmallText" readonly="true"/> </span>	
					</span>
<!-- 					<span class="fieldHolder49 " style="width: 50%"> -->
<!-- 						<span class="fLW18">Alt</span> -->
<!-- 						<span class="fLW60"> <form:input path=""  class="standardInputText" style="width: 440px" readonly="true"/> </span>	 -->
<!-- 					</span> -->
				</div>	
				<div class="mLRTB" style="border-bottom: 1px dotted; height: 60px;">
					<br><br>
					<span class="fieldHolder25 ">
						<span class="fLW35">Provider Contact By</span>
						<span class="fLW60"> <form:input path="PROVIDER_CONTACT_UID"  class="standardInputSmallText" readonly="true"/> </span>	
					</span>
					<span class="fieldHolder25 ">
						<span class="fLW35">Provider Contact Date</span>
						<fmt:formatDate value="${psAssignmentView.PROVIDER_CONTACT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedPROVIDER_CONTACT_DATE" />
						<span class="fLW60"> <form:input path="PROVIDER_CONTACT_DATE" value="${theFormattedPROVIDER_CONTACT_DATE}" class="standardInputSmallText" readonly="true"/> </span>	
					</span>
				</div>
				<div class="mLRTB">	
					<span class="fieldHolder49 "  style="width: 50%">
						<span class="fLW18">Address</span>
						<span class="fLW60"> <form:input path="PROV_ADDRESS"  class="standardInputText" style="width: 425px" readonly="true"/> </span>	
					</span>
					<span class="fieldHolder25 ">
						<span class="fLW35">Phone</span>
						<span class="fLW60"> <form:input path=""  class="standardInputSmallText" readonly="true"/> </span>	
					</span>					
				</div>
				<div class="mLRTB" style="border-bottom: 1px dotted; height: 60px;">	
					<br><br>
					<span class="fieldHolder25 ">
						<span class="fLW35">City</span>
						<span class="fLW60"> <form:input path="PROV_CITY"  class="standardInputSmallText" readonly="true"/> </span>	
					</span>
					<span class="fieldHolder25 ">
						<span class="fLW35">State</span>
						<span class="fLW60"> <form:input path=""  class="standardInputSmallText" readonly="true"/> </span>	
					</span>
					<span class="fieldHolder25 ">
						<span class="fLW35">Zip</span>
						<span class="fLW60"> <form:input path="PROV_ZIP"  class="standardInputSmallText" readonly="true"/> </span>	
					</span>
					<span class="fieldHolder25 ">
						<span class="fLW35">County</span>
						<span class="fLW60">  
						
						<form:select path="PROV_COUNTY"  cssClass="standardInputSmallText" readonly="true"> 
										<form:option value=""></form:option>
										<form:options items="${sessionScope.countyMap}"/>
									</form:select>
						</span>
							
					</span>					
				</div>
				<div class="mLRTB">	
					<span class="fieldHolder25 ">
						<span class="fLW35">Status</span>
						<span class="fLW60"> <form:input path="PROVIDER_CONTACT"  class="standardInputSmallText" readonly="true"/> </span>	
					</span>
					<span class="fieldHolder25 ">
						<span class="fLW35">Date of Previous HIV Test</span>
						<span class="fLW60"> <form:input path="PREVIOUS_TEST_DATE_TEXT"  class="standardInputSmallText" readonly="true"/> </span>	
					</span>
					<span class="fieldHolder25 ">
						<span class="fLW35">Previous Test Results</span>
						<span class="fLW60"> <form:input path="PREVIOUS_TEST_RESULTS"  class="standardInputSmallText" readonly="true"/> </span>	
					</span>
					<span class="fieldHolder25 ">
						<span class="fLW35">Current HIV Test</span>
						<span class="fLW60"> <form:input path="CURRENT_TEST_DATE_TEXT"  class="standardInputSmallText" readonly="true"/> </span>	
					</span>
				</div>
				<div class="mLRTB" style="border-bottom: 1px dotted; height: 60px;">	
					<br><br>					
<!-- 					<span class="fieldHolder25 "> -->
<!-- 						<span class="fLW35">Init HIV Dx Date</span> -->
<!-- 						<span class="fLW60"> <form:input path=""  class="standardInputSmallText" readonly="true"/> </span>	 -->
<!-- 					</span> -->
					<span class="fieldHolder25 ">
						<span class="fLW35">Informed</span>
						<span class="fLW60"> <form:input path="INFORMED_DIAG"  class="standardInputSmallText" readonly="true"/> </span>	
					</span>
					<span class="fieldHolder25 ">
						<span class="fLW35">Informed Dt</span>
						<span class="fLW60"> <form:input path="INFORMED_DIAG_DATE_TEXT"  class="standardInputSmallText" readonly="true"/> </span>	
					</span>
				</div>
				<div class="mLRTB" >	
					<span class="fieldHolder25 ">
						<span class="fLW100"><b>Test Site Agency Name</b></span>	
					</span>
					<br><br>
					<span class="fieldHolder49 " style="width: 50%">
						<span class="fLW35">Expanded Test Site</span>
						<span class="fLW60"> 
							<form:radiobutton path="EXPANDED_TEST_SITE" value="Y" /> <spring:message code="label.yes" />
							<form:radiobutton path="EXPANDED_TEST_SITE" value="N" /> <spring:message code="label.no" />
							<form:radiobutton path="EXPANDED_TEST_SITE" value="U" /> <spring:message code="label.unknown" />
						</span>	
					</span>
					<span class="fieldHolder49 ">
						<span class="fLW45">Initial Medical Appointment Scheduled</span>
						<span class="fLW55"> 
							<form:radiobutton path="APPT_SCHEDULED" value="Y" /> <spring:message code="label.yes" />
							<form:radiobutton path="APPT_SCHEDULED" value="N" /> <spring:message code="label.no" />
							<form:radiobutton path="APPT_SCHEDULED" value="U" /> <spring:message code="label.unknown" />
							<form:radiobutton path="APPT_SCHEDULED" value="R" /> <spring:message code="label.refused" />
							
						</span>	
					</span>
				</div>
				<div class="mLRTB" style="border-bottom: 1px dotted; height: 80px;">
					<br>
					<span class="fieldHolder25 ">
						<span class="fLW35">Test Site Agency Name</span>
						<span class="fLW60"> <form:input path="EXPANDED_TEST_SITE_AGENCY"  class="standardInputSmallText" readonly="true"/> </span>	
					</span>
					<span class="fieldHolder25 ">
						<span class="fLW35">Scheduled Appointment Attended</span>
						<span class="fLW60"> <form:input path="APPT_ATTENDED"  class="standardInputSmallText" readonly="true"/> </span>	
					</span>
					<span class="fieldHolder25 ">
						<span class="fLW35">Appointment Date</span>
						<fmt:formatDate value="${psAssignmentView.APPT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedAPPT_DATE" />
						<span class="fLW60"> <form:input path="APPT_DATE" value="${theFormattedAPPT_DATE}" class="standardInputSmallText" readonly="true"/> </span>	
					</span>
				</div>

				<div class="mLRTB" >
					<br>
					<span class="" >
						<span class="fLW18">Worker Comments</span>
						<span class="fLW60"> 
							<form:textarea readonly="true"  path="WORKER_COMMENTS" cols="150" rows="7" disabled="disabled"/>
						</span>	
					</span>
				</div>
				<div  class="clearBoth"></div>
				<div class="mLRTB" >
					<span class=" " >
						<span class="fLW18">Supervisor Comments</span>
						<span class="fLW60"> 
							<form:textarea readonly="true"  path="SUPERVISOR_COMMENTS" cols="150" rows="7" disabled="disabled"/>
						</span>	
					</span>
				</div>
<!-- 				<div  class="clearBoth" ></div> -->
<!-- 				<div class="mLRTB" > -->
<!-- 					<span class=" "> -->
<!-- 						<span class="fLW18"> Central Office Comments</span> -->
<!-- 						<span class="fLW60" style="padding-bottom:10px">   -->
<!-- 							<textarea readonly="true"  path="" cols="150" rows="7" disabled="disabled"></textarea> -->
<!-- 						</span> -->
<!-- 					</span> -->
<!-- 				</div> -->
				<div  class="clearBoth"></div>
		</div>	
		
		<c:if test='${psAssignmentView.EVENT_DESC == "ExPS - OOC" || psAssignmentView.EVENT_DESC == "HC_OOC" || psAssignmentView.EVENT_DESC == "S_DX_NLTC"}' >
		<div class="container borderSSDMDoc">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> Supplemental Expanded Partner Services Out of Care Case Investigation Form<br></span>	
			</div>
			<div class="mLRTB pLRTB" style="border: dotted 1px">
				<div style="font-weight:bold"> Tracing Disposition </div>
				
				<div>
					<form:radiobutton path="TRACING_DISPO" value="01" style=""/> <b>Traced - Out of Care</b>
					<form:radiobutton path="TRACING_DISPO" value="02" style="margin-left:100px"/> <b>Traced - Current to Care</b>
					<form:radiobutton path="TRACING_DISPO" value="03" style="margin-left:100px"/><b> Not Traced - Unable to Lacate	</b>
				</div>
				<div>
					<b> Traced - Other</b>
				</div>
				<div style="margin-left: 50px">
					<form:radiobutton path="TRACING_DISPO" value="10" /> Medically Unable to Respond <br>
					<form:radiobutton path="TRACING_DISPO" value="11" /> Refused: Located and Refused to Meet in Person/ Phone <br>
					<form:radiobutton path="TRACING_DISPO" value="12" /> Moved to Another Jurisdiction <br>
					<form:radiobutton path="TRACING_DISPO" value="13" /> Deceased <br>
					<form:radiobutton path="TRACING_DISPO" value="09" /> Other, Specify <form:textarea rows="4" cols="50" path="TRACING_DISPO_OTHER"  style="margin-left:20px" readonly="true"/> 
				</div>
			</div>

			<div class="mLRTB pLRTB" style="border: dotted 1px">
				<div style="font-weight:bold"> Medical Provider Interview/ Chart Review Notes </div>
				<br>
				<div>
					<form:textarea rows="7" cols="150" path="MED_CHART_REVIEW"  readonly="true"/> 
				</div>
			</div>

			<div class="mLRTB pLRTB" style="border: dotted 1px">
				<div style="font-weight:bold"> Interviewed For Reason Out of Care (Check all that apply) </div>
				<br>
				<div>
					<span class="fieldHolder49 ">
						<form:checkbox path="FELT_HEALTHY" value="1" /> Felt Healthy	
						<br>
						<form:checkbox path="FELT_DEPRESSED" value="1" /> Felt Depressed	
						<br>
						<form:checkbox path="RESPONSIBILITIES" value="1" /> Day-to-Day Responsibilities	
						<br>
						<form:checkbox path="THINKS_NOT_HIV_POS" value="1" /> Do Not Think HIV Positive	
						<br>
						<form:checkbox path="FAMILY_COMMITMENTS" value="1" /> Family Commitments	
						<br>
						<form:checkbox path="SUBSTANCE_USE" value="1" /> Substance Use	
						<br>
						<form:checkbox path="NO_CHILD_CARE" value="1" /> No Child Care	
						<br>
						<form:checkbox path="LACK_STABLE_HOUSING" value="1" /> Lack of Stable Housing	
						<br>
						<form:checkbox path="LANGUAGE_BARRIER" value="1" /> Language Barrier	
						<br>
						<form:checkbox path="WORK_OBLIGATION" value="1" /> Work Obligation	
						<br>
					</span>
					<span class="fieldHolder49 ">
						<form:checkbox path="NO_MED_INSURANCE" value="1" /> Do not Have Medical Insurance	
						<br>	
						<form:checkbox path="DONT_TRUST_HEALTH_WRKR" value="1" /> Did Not Trust Health Care Workers
						<br>
						<form:checkbox path="ADVERSE_EFFECT_HIV_MEDS" value="1" /> Adverse Effect of HIV Medication	
						<br>
						<form:checkbox path="INCONVENIENT_APPT_TIMES" value="1" /> Inconvenient Time of Appointment/s
						<br>
						<form:checkbox path="FORGOT_APPT" value="1" /> Forgot About Appointments
						<br>
						<form:checkbox path="DIDNT_FEEL_LIKE_GOING_APPT" value="1" /> Did Not Feel Like Going to Appointment
						<br>
						<form:checkbox path="TOO_ILL" value="1" /> Too Ill to Attend Appointment
						<br>
						<form:checkbox path="NOT_WITH_PREFERRED_PROV" value="1" /> Appointment Wasn't with Preferred Provider
						<br>
						<form:checkbox path="LACK_TRANSPORTATION" value="1" /> Lack of Transportation
						<br>
						<form:checkbox path="OTHER_REASON_OOC" value="1" /> Other, Specify  <form:input path="SPECIFY_REASON_OOC"  class="standardInputSmallText" readonly="true"/>
						<br>
					</span>
				</div>
				<div class="clearBoth"></div>
			</div>

			<div class="mLRTB pLRTB" style="border: dotted 1px; font-weight: bold">
				<div class="">Patient Interviewed
					<form:radiobutton path="INTERVIEWED" value="Y" style="margin-left:25px"/> Yes
					<form:radiobutton path="INTERVIEWED" value="N" style="margin-left:25px"/> No
				</div>
			</div>

			<div class="mLRTB pLRTB" style="border: dotted 1px">
				<div style="font-weight:bold"> Interview Activities/ Outcomes (Check all that apply) </div>
				<br>
				<div>
					<form:checkbox path="PREVENTION_COUNSELING" value="1" /> Prevention Counseling	
					<br>	
					<form:checkbox path="RISK_ASSESSMENT" value="1" /> Risk Assessment Completed
					<br>
					<form:checkbox path="MALE_CONDOMS" value="1" /> Male Condoms Provided	
					<br>
					<form:checkbox path="FEMALE_CONDOMS" value="1" /> Female Condoms Provided
					<br>
					<form:checkbox path="" value="1" /> Discussion of Partners
					<br>
					<form:checkbox path="NONMED_APPTS_MADE" value="1" /> Appointment Made on Behalf of Patient or Refferal for Non-Medical Services Made
					<br>
				</div>
			</div>

			<div class="mLRTB pLRTB" style="border: dotted 1px">
				<div style="font-weight:bold"> Medical Referral Information </div>
				<br>
				<div>
					<div>
						<span style="font-weight:bold"> Referred To </span> <span> (Name, Address, Phone)</span>
					</div>
					<div class="mLRTB">	
						<span class="fieldHolder49 "  style="width: 50%">
							<span class="fLW18">Name</span>
							<span class="fLW60"> <form:input path="REFERRAL_NAME"  class="standardInputText" style="width: 413px" readonly="true"/> </span>	
						</span>
						<span class="fieldHolder25 ">
							<span class="fLW35">Phone</span>
							<span class="fLW60"> <form:input path="REFERRAL_PHONE"  class="standardInputSmallText" readonly="true"/> </span>	
						</span>					
					</div>
					<div class="clearBoth"></div>
					<div class="mLRTB">	
						<span class="fieldHolder49 "  style="width: 50%">
							<span class="fLW18">Address</span>
							<span class="fLW60"> <form:input path="REFERRAL_ADDRESS"  class="standardInputText" style="width: 413px" readonly="true"/> </span>	
						</span>				
					</div>
					<div class="clearBoth"></div>
					<div class="mLRTB" >
						<span class="fieldHolder25 ">
							<span class="fLW35">City</span>
							<span class="fLW60"> <form:input path="REFERRAL_CITY"  class="standardInputSmallText" readonly="true"/> </span>	
						</span>
						<span class="fieldHolder25 ">
							<span class="fLW35">State</span>
							<span class="fLW60"> <form:input path="REFERRAL_STATE"  class="standardInputSmallText" readonly="true"/> </span>	
						</span>
						<span class="fieldHolder25 ">
							<span class="fLW35">Zip</span>
							<span class="fLW60"> <form:input path="REFERRAL_ZIP"  class="standardInputSmallText" readonly="true"/> </span>	
						</span>
<!-- 						<span class="fieldHolder25 "> -->
<!-- 							<span class="fLW35">County</span> -->
<!-- 							<span class="fLW60"> <form:input path=""  class="standardInputSmallText" readonly="true"/> </span>	 -->
<!-- 						</span>						 -->
					</div>
					<div class="clearBoth"></div>
					<div class="mLRTB">	
						<span class="fieldHolder49 "  style="width: 50%">
							<span class="fLW55">Date of First Medical Appointment Scheduled</span>
							<span class="fLW45"> <form:input path="MED_APPT_SCHEDULED_DATE"  class="standardInputSmallText" readonly="true"/> </span>	
						</span>				
					</div>
					<div class="clearBoth"></div>
					<div class="mLRTB">	
						<span class="fieldHolder49 "  style="width: 50%">
							<span class="fLW55">Date of First Medical Appointment Attended</span>
							<span class="fLW45"> <form:input path="MED_APPT_ATTENDED_DATE"  class="standardInputSmallText" readonly="true"/> </span>	
						</span>	
						<span class="fieldHolder49 "  style="width: 50%">
							<span class="fLW55">Date Medical Appointment Verified</span>
							<span class="fLW45"> <form:input path="MED_APPT_VERIFIED_DATE"  class="standardInputSmallText" readonly="true"/> </span>	
						</span>				
					</div>
					<div class="clearBoth"></div>
					<div class="mLRTB">	
						<span>
							If patient did not attend a medical appointment after at least two linkage attempts, leave 'date' field blank and complete the following:
						</span>				
					</div>
					<div class="clearBoth"></div>
					<div class="mLRTB">	
						<span>
							<form:radiobutton path="REASON_MED_APPT_NOT_ATTENDED" value="01" style="margin-left:25px"/> No show for appointments
							<form:radiobutton path="REASON_MED_APPT_NOT_ATTENDED" value="02" style="margin-left:25px"/> Lost To Follow Up	
							<form:radiobutton path="REASON_MED_APPT_NOT_ATTENDED" value="08" style="margin-left:25px"/> Other, Specify <form:input path="SPECIFIED_REASON"  class="standardInputSmallText" readonly="true"/>
							
						</span>				
					</div>
					<div class="clearBoth"></div>
				</div>
			</div>

			<div class="mLRTB pLRTB" style="border: dotted 1px">
				<div> 
					<span style="font-weight:bold"> Primary Linkage Disposition </span> 
					<span> (only completed for patients who were traced and found to be out of care)
					</span> 
				</div>
				<div>
					<form:radiobutton path="LINKAGE_DISPO" value="01" style="margin-left:25px"/> <b>Linked to Care: </b> Patient attends at least one medical appointment<br>
					<form:radiobutton path="LINKAGE_DISPO" value="02" style="margin-left:25px"/> <b>Not Linked to Care: </b> No evidence of medical appointment
					
				</div>
			</div>

		</div>
		</c:if>
		<div id='partnerViewDlg'>  </div>
		<!-- <div>
			<a onClick="personPartnerClick()" class="personPartner"> Person Partner Screen </a>
		</div> -->

		<div class="container borderSSDMDoc">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> Person Partner Screen:<br></span>	
			</div>	
			<div>
				<c:if test="${fn:length(psAssignmentView.partnerList) <= 0}">
				  <c:out value="Partner Service Record are Unavailable for this record"/>
				</c:if>

				<c:set var="count" value="1" scope="page" />
				<c:forEach var="partner" items="${psAssignmentView.partnerList}">
					<p class="mLRTB"><a class="partnerList" id="partnerList<c:out value="${count}"/>" onClick="personPartnerClick(this);"><c:out value="${partner}" /></a><br></p>
					<c:set var="count" value="${count+1}" />
				</c:forEach>	
			</div>		   		
	<!--  <c:out value="${fn:length(psAssignmentView.partnerList)}" />  -->
		</div>
</form:form>