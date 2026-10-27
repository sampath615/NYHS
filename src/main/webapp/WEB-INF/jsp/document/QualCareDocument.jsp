<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link href="${staticPath}/css/docs/ahiForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/docs/commonForm.css" rel="stylesheet" type="text/css" />

<script src="${staticPath}/js/docs/ahiDoc.js?v=${buildDate}"></script>
<c:if test="${param.origin != 'Print'}">
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
</c:if>
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	
<form:form modelAttribute="qualCareDocVO" id="qualCareDocVO" cssClass="documentFormVO documentDescription">
<form:errors path="*" cssClass="errorblock" element="div" />

        <div id="" style="background-color: transparent">
                
                <div style="padding-left:43%; " class="headingCls">
                                QUALITY CARE DOCUMENT
                </div>
                
           	<div id="ahiDocumentTransactionMessage" class="transactionMessage"><span class="message"></span></div>
			<div id="sendEmailTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				
				
					 <div class="pB30 pT20">
	
			 	      		<div class="floatLeft">
			 	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
			 	                 <span class="ml10"><form:input id="STATENO"  path="stateno" value="${STATENO}" class="standardInputSmallText readonly" tabindex="-1" maxlength="10" readonly="true"/> </span>
			 	          	</div>
		 	          	
						<c:if test="${qualCareDocVO.stdQualityCareDoc.documentId != null &&  qualCareDocVO.stdQualityCareDoc.documentId != 0}">			
		 					<div  class="floatRight">
					            <span class="mL10 bold">Document ID: </span>
								<span class=" "><form:input id="DOCUMENT_ID"  path="stdQualityCareDoc.documentId" class="standardinputSmallText ml10 readonly" tabindex="-1" maxlength="6" readonly="true"/></span>
					       	</div>
		 	      		</c:if>   	
					</div>
					
				
		<!--  Person details -->
		
		<div class="container" style="border:1px dotted; width:100%">	 
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Person Details</span></div>
				
				<br>
				<div class="personInfo">
                		<div class="fieldHolder25">
                             <span class="pL10">First Name </span> 
                             <span class="pL5"><form:input id="FNAME"  path="stdQualityCareDoc.fname" class="standardInputMedText required" maxlength="48" title="First Name"/></span>
                        </div>
                        <div class="fieldHolder25">
                            	<span class="pL10">Middle Name </span> 
                            	<span  class="pL5"><form:input id="MNAME"  path="stdQualityCareDoc.mname" class="standardInputMedText" maxlength="1" title="Middle Name"/></span>
                        </div>
                        <div class="fieldHolder25">
                            	<span class="pL10">Last Name </span> 
                            	<span  class="pL5"><form:input id="LNAME"  path="stdQualityCareDoc.lname" class="required standardInputMedText" maxlength="48" title="Last Name"/></span>
                        </div>
                        <br><br>
                </div>
               
                <div class="clearBoth personInfo">

                         <div class="fieldHolder25">
                            	<span class="pL10"><spring:message code="label.birthdate" /></span> 
                            	<span  class="pL5"><form:input id="DOB_TEXT"  path="stdQualityCareDoc.dobText" class="standardInputSmallText3" datevalidations="partialDate" maxlength="10" title="Date of Birth"/> </span>
                         </div>
                         <div class="fieldHolder25">
                            	<span class="pL10"><spring:message code="label.age" /> </span> 
                            	<span  class="pL5"><form:input id="AGE"  path="stdQualityCareDoc.age" class="nOnly standardInputSmallText2" maxlength="3" title="AGE"/></span>
                         </div>
                        <br><br>
                        
                </div>
		</div>					

				 
                <div class="container" style="border:1px dotted;">
                        <div class="fieldHolder25" style="width: 24%; border: 1px dotted; height: 476px">
                                <div style="padding:10px ">      
                                        <div> Race </div>
                                        <div>   <form:checkbox id="RACE_WHITE1"  path="stdQualityCareDoc.raceWhite"  value="1"/> White </div>
                                        <div>   <form:checkbox id="RACE_BLACK1"  path="stdQualityCareDoc.raceBlack" value="1"/> African American</div>
                                        <div>   <form:checkbox id="RACE_ASIAN1"  path="stdQualityCareDoc.raceAsian" value="1"/> Asian</div>
                                        <div>   <form:checkbox id="RACE_INDIAN1"  path="stdQualityCareDoc.raceIndian" value="1"/> American Indian/Alaskan Native</div>
                                        <div>   <form:checkbox id="RACE_PACIFIC1"  path="stdQualityCareDoc.racePacific" value="1"/> Pacific Islander</div> 
                                        <div>   <form:checkbox id="RACE_OTHER1"  path="stdQualityCareDoc.raceOther" value="1"/> Other </div>
                                        <div>   <form:checkbox id="RACE_UNKNOWN1"  path="stdQualityCareDoc.raceUnknown" value="1"/> Unknown</div> 
                                        <div class="mT5 container">
											<div style="float:left; width:35%">	Asian Sub Group </div>
											<div style="float:left">
												<form:input id="ASIAN_SUB_GROUP"  path="asianSubGroup"  class="standardInputMedText mL5" title="Asian Sub Group"/>
											</div>
										</div>  
                                </div>  
                                <div style="padding:15px 0 10px 10px; border-top: dotted 1px; ">   
                                        <div>
                                                Ethnicity (Check one) Hispanic or Latino<br>
                                                <form:radiobutton id="ETHNICITY1"  path="stdQualityCareDoc.ethnicity" value="01" /> Yes
                                                <form:radiobutton id="ETHNICITY2"  path="stdQualityCareDoc.ethnicity" value="02" /> No
                                                <form:radiobutton id="ETHNICITY3"  path="stdQualityCareDoc.ethnicity" value="09" /> Unknown
                                        </div>
                                        <div>	Hispanic Sub Group </div>
										<div>
											<form:textarea id="HISPANIC_SUB_GROUP"  path="hispanicSubGroup"  rows="2" cols="50" title="Hispanic Sub Group"/>
										</div>
                                </div>  
                                <div style="padding:15px 0 10px 10px; border-top: dotted 1px; "> 
									<div class=""> Birth Sex </div>
									<div  class="">
										<span> <form:radiobutton id="BIRTH_SEX1"  path="stdQualityCareDoc.birthSex" value="M" /> Male </span>
										<span> <form:radiobutton id="BIRTH_SEX2"  path="stdQualityCareDoc.birthSex" value="F" /> Female </span>		
									</div>
								</div>
								 <div style="padding:15px 0 10px 10px; border-top: dotted 1px; "> 
								 	<div class="floatLeft pR10" style="border-right: dotted 1px;">
										<div class=""> Current Gender </div>
										<div  class="">
											<div> <form:radiobutton id="GENDER1"  path="stdQualityCareDoc.gender" value="M" /> Male </div>
											<div> <form:radiobutton id="GENDER2"  path="stdQualityCareDoc.gender" value="F" /> Female </div>
											<div> <form:radiobutton id="GENDER3"  path="stdQualityCareDoc.gender" value="U" />Unknown</div>		
										</div>
									</div>
									<div class="floatLeft pL10">
										<div class=""> Transgender </div>
										<div  class="">
											<div> <form:radiobutton id="GENDER4"  path="stdQualityCareDoc.gender" value="X" /> MTF </div>		
											<div> <form:radiobutton id="GENDER5"  path="stdQualityCareDoc.gender" value="Y" /> FTM </div>		
											<div> <form:radiobutton id="GENDER6"  path="stdQualityCareDoc.gender" value="T" /> Unspecified </div>														
										</div>
									</div>
								</div>
                                <br> 
                        </div>
                        <div class="fieldHolder75">
                        <!--  Risk details -->
                                <div class="riskHW borderField pT10" style="height:456px">
                                       <div class="pT8">
                                                <span class="riskLabelField">Risk Details</span>
                                                <span class="riskValueField">Yes</span>
                                                <span class="riskValueField">No</span>
                                                <span class="riskValueField">Unknown</span><br>
                                        </div>  
                                        <div class="pT8">
                                                <span class="riskLabelField">Injection Drug Use </span>
                                                <span class="riskValueField"><form:radiobutton id="IDU1"  path="stdQualityCareDoc.idu" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton id="IDU2"  path="stdQualityCareDoc.idu" value="N" /></span>
                                                <span class="riskValueField"><form:radiobutton id="IDU3"  path="stdQualityCareDoc.idu" value="U" /></span><br>
                                        </div>  
                                        
                                        <div class="pT8">
                                                <span class="riskLabelField">Male Sex with Male </span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_MSM1"  path="stdQualityCareDoc.riskMsm" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_MSM2"  path="stdQualityCareDoc.riskMsm" value="N" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_MSM3"  path="stdQualityCareDoc.riskMsm" value="U" /></span><br>
                                        </div>  
                                        
                                        <div class="pT10"> <u>Heterosexual sex with:</u> </div>
                                        <ul>
                                                <li class="pT8">        
                                                        
                                                                <span class="riskLabelField1">HIV+ person </span>
                                                                <span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_HIV1"  path="stdQualityCareDoc.sexPartnerHiv" value="Y" /></span>
                                                                <span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_HIV2"  path="stdQualityCareDoc.sexPartnerHiv" value="N" /></span>  
                                                                <span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_HIV3"  path="stdQualityCareDoc.sexPartnerHiv" value="U" /></span>           
                                                        
                                                </li>
                                        </ul>                                        
  
                                        <div class="pT8">
                                                <span class="riskLabelField pB5">Received Blood Products </span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RECV_BLOOD_PROD1"  path="stdQualityCareDoc.recvBloodProd" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RECV_BLOOD_PROD2"  path="stdQualityCareDoc.recvBloodProd" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton id="RECV_BLOOD_PROD3"  path="stdQualityCareDoc.recvBloodProd" value="U" /></span><br>                
                                        </div> 
                                        
                                        <div class="pT8">
                                                <span class="riskLabelField pB5">Received blood clotting factor </span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RECV_CLOTTING_FACTOR1"  path="stdQualityCareDoc.recvClottingFactor" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RECV_CLOTTING_FACTOR2"  path="stdQualityCareDoc.recvClottingFactor" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton id="RECV_CLOTTING_FACTOR3"  path="stdQualityCareDoc.recvClottingFactor" value="U" /></span><br>                
                                        </div> 
                                                                                
                                        <div class="pT8">
                                                <span class="riskLabelField pB5">Perinatal Exposure </span>
                                                <span class="riskValueField pB5"><form:radiobutton id="PERINATAL1"  path="stdQualityCareDoc.perinatal" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="PERINATAL2"  path="stdQualityCareDoc.perinatal" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton id="PERINATAL3"  path="stdQualityCareDoc.perinatal" value="U" /></span><br>                
                                        </div>  
                                        <div class="pT8">
                                                <span class="riskLabelField">Other Risk </span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_OTHER1"  path="stdQualityCareDoc.riskOther" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_OTHER2"  path="stdQualityCareDoc.riskOther" value="N" /></span>    
                                                <span class="riskValueField"><form:radiobutton id="RISK_OTHER3"  path="stdQualityCareDoc.riskOther" value="U" /></span><br><br>
                                        </div>
                                        <div>
                                                <span class="riskLabelField">Unknown </span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_UNKNOWN1"  path="stdQualityCareDoc.riskUnknown" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_UNKNOWN2"  path="stdQualityCareDoc.riskUnknown" value="N" /></span>    
                                                <span class="riskValueField"><form:radiobutton id="RISK_UNKNOWN3"  path="stdQualityCareDoc.riskUnknown" value="U" /></span>
                                        </div>
                                </div>
                                <div class="reportHW borderField pT10" style="height:456px">   
						 				<div class="mT5 container">
											<div style="float:left; width:35%"> Medical Record Number </div>
											<div style="float:left">
												<form:input id="MED_RECORD_NUMBER"  path="stdQualityCareDoc.medRecordNumber" class="standardInputMedText mL5" maxlength="50" title="Medical Record Number"/>
											</div>
										</div>			
									<div class="mT5 container">
											<div style="float:left; width:35%"> Inpatient </div>
											<div style="float:left">
												<form:input id="INPATIENT"  path="stdQualityCareDoc.inpatient" class="standardInputMedText mL5" maxlength="20" title="Inpatient"/>
											</div>
									</div>	
									
					 				<div class="mT5 container">
										<div style="float:left; width:35%">	Housing Status </div>
										<div style="float:left">
											<form:input id="HOUSING_STATUS"  path="housingStatus"  class="standardInputMedText mL5" title="Housing Status"/>
										</div>
									</div>	
<%-- 									
									<div class="mT5 container">
										<div style="float:left; width:35%">	Insurance </div>
										<div style="float:left">
											<form:input path="INSURANCE"  class="standardInputMedText mL5" title="Insurance"/>
										</div>
									</div>
 --%>									
									<div class="mT5 container">
											<div style="float:left; width:35%">Patient Status </div>
											<div style="float:left">
												<form:input id="PATIENT_STATUS"  path="stdQualityCareDoc.patientStatus" class="standardInputMedText mL5" maxlength="35" title="Patient Status"/>
											</div>
									</div>
									
					 				<div class="mT5 container">
										<div style="float:left; width:35%">	Diagnosis </div>
										<div style="float:left">
											<form:textarea id="DIAGNOSIS"  path="diagnosis"  rows="2" cols="30" title="Diagnosis"/>
										</div>
									</div>
														
									<div class="mT5 container">
											<div style="float:left; width:35%">Diagnosis Date</div>
											<div style="float:left">
												<form:input id="DIAGNOSIS_DATE"  path="diagnosisDate" class="standardInputMedText mL5" maxlength="20" title="Diagnosis Date"/>
											</div>
									</div>	
									
									<div class="mT5 container">
											<div style="float:left; width:35%">Discharge Date</div>
											<div style="float:left">
												<form:input id="DISCHARGE_DATE"  path="dischargeDate" class="standardInputMedText mL5" maxlength="20" title="Discharge Date"/>
											</div>
									</div>	
									
					 				<div class="mT5 container">
										<div style="float:left; width:35%">	Enrollment </div>
										<div style="float:left">
											<form:textarea id="ENROLLMENT"  path="enrollment"  rows="3" cols="30" title="Enrollment"/>
										</div>
									</div>			
															
                                </div>
                        </div>  
                </div>

		<!--  Lab details -->
<!-- 		<div id="labDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
			<span class=" mL2">Lab Details</span>
		</div>			
 -->		
		<div style="padding-top:5px; padding-bottom:5px; border:1px dotted;" class="container">	 
		
			<div style="float:left; width:23.5%; padding-left:10px; border-right:1px dotted;">
				<div><b>Linked To Care</b></div>
				
				<div class="mT5 container">
						<div style="float:left; width:35%">Linked</div>
						<div style="float:left">
							<form:input id="LINKED"  path="stdQualityCareDoc.linked" class="standardInputSmallText1 mL5" maxlength="20" title="linked"/>
						</div>
				</div>	

				<div class="mT5 container">
						<div style="float:left; width:35%">Linked Days</div>
						<div style="float:left">
							<form:input id="LINKAGE_DAYS"  path="stdQualityCareDoc.linkageDays" class="standardInputSmallText1 mL5" maxlength="20" title="Linked Days"/>
						</div>
				</div>	

			</div>

			<div style="float:left; width:30%; padding-left:10px;">
				<div><b>HIV Care for patient newly Dx during review period</b></div>
				<div class="mT5 container">
						<div style="float:left; width:35%">HIV Care</div>
						<div style="float:left">
							<form:input id="HIV_CARE"  path="stdQualityCareDoc.hivCare" class="standardInputSmallText1 mL5" maxlength="10" title="HIV Care"/>
						</div>
				</div>	
				<div class="mT5 container">
						<div style="float:left; width:35%">HIV Care Date</div>
						<div style="float:left">
							<form:input id="HIV_CARE_DATE"  path="stdQualityCareDoc.hivCareDate" class="standardInputSmallText1 mL5" maxlength="10" title="HIV Care Date"/>
						</div>
				</div>	
								
			</div>		
			
			<div style="float:left; width:30%; padding-left:10px; border-left:1px dotted;">
				<div><b>ARV Therapy</b></div>
				<div class="mT5 container">
						<div style="float:left; width:55%">Receive ARV (besides PrEP)</div>
						<div style="float:left">
							<form:input id="ARV"  path="stdQualityCareDoc.arv" class="standardInputSmallText1 mL5" maxlength="10" title="ARV"/>
						</div>
				</div>	
				<div class="mT5 container">
						<div style="float:left; width:55%">On ARV</div>
						<div style="float:left">
							<form:input id="ON_ARV"  path="stdQualityCareDoc.onArv" class="standardInputSmallText1 mL5" maxlength="10" title="On ARV"/>
						</div>
				</div>	
				
			</div>				
		</div>


		<div style="padding-top:5px; padding-bottom:5px; border:1px dotted;" class="container">	 

			<div style="float:left; width:23.5%; padding-bottom:5px; padding-left:10px; border-right:1px dotted;">
				<div><b>Viral Load Suppression </b></div>
 				<div class="mT5 container">
						<div style="float:left; width:70%">Newly Dx First Suppressed Date </div>
						<div style="float:left">
							<form:input id="FIRST_SUPPRESSED_DATE_NEWLY_DX"  path="stdQualityCareDoc.firstSuppressedDateNewlyDx" class="standardInputSmallText1 mL5" maxlength="20" title="First Suppressed Date Newly Dx"/>
						</div>
				</div>	

				<div class="mT5 container">
						<div style="float:left; width:70%">Suppressed Days</div>
						<div style="float:left">
							<form:input id="SUPPRESSION_DAYS"  path="stdQualityCareDoc.suppressionDays" class="standardInputSmallText1 mL5" maxlength="20" title="Suppressed Days"/>
						</div>
				</div>	

				<div class="mT5 container">
						<div style="float:left; width:70%">Suppressed Days Discharge</div>
						<div style="float:left">
							<form:input id="SUPPRESSION_DAYS_DISCHARGE"  path="stdQualityCareDoc.suppressionDaysDischarge" class="standardInputSmallText1 mL5" maxlength="20" title="Suppressed Days Discharge"/>
						</div>
				</div>	

				<div class="mT5 container">
						<div style="float:left; width:70%">Suppressed Days HIV Care</div>
						<div style="float:left">
							<form:input id="SUPPRESSION_DAYS_HIV_CARE"  path="stdQualityCareDoc.suppressionDaysHivCare" class="standardInputSmallText1 mL5" maxlength="20" title="Suppressed Days HIV Care"/>
						</div>
				</div>	
	
			</div>	
		
			<div style="float:left; width:36%; padding-bottom:5px; padding-left:10px; border-left:1px dotted;">
			
				<div><b>Viral Load Testing</b></div>
				
				<div class="mT5 container">
						<div style="float:left; width:46%">Newly Dx First VL Date </div>
						<div style="float:left">
							<form:input id="FIRST_VL_DATE_NEWLY_DX"  path="stdQualityCareDoc.firstVlDateNewlyDx" class="standardInputSmallText1 mL5" maxlength="20" title="First VL Date Newly Dx"/>
						</div>
				</div>	
	
				<div class="mT5 container">
						<div style="float:left; width:46%">Suppressed Final VL</div>
						<div style="float:left">
							<form:input id="SUPPRESSED_FINAL_VL"  path="stdQualityCareDoc.suppressedFinalVl" class="standardInputSmallText1 mL5" maxlength="20" title="Suppressed Final VL"/>
						</div>
				</div>	
	
				<div class="mT5 container">
						<div style="float:left; width:46%">VL Reviewed</div>
						<div style="float:left">
							<form:input id="VL_REVIEWED"  path="stdQualityCareDoc.vlReviewed" class="standardInputSmallText1 mL5" maxlength="20" title="VL Reviewed"/>
						</div>
				</div>	
	
				<div class="mT5 container">
						<div style="float:left; width:46%">VL Suppressed</div>
						<div style="float:left">
							<form:input id="VL_SUPPRESSED"  path="stdQualityCareDoc.vlSuppressed" class="standardInputSmallText1 mL5" maxlength="20" title="VL Suppressed"/>
						</div>
				</div>	
	
				<div class="mT5 container">
						<div style="float:left; width:46%">VL in review year </div>
						<div style="float:left">
							<form:input id="VL_TEST_REVIEW_YEAR"  path="stdQualityCareDoc.vlTestReviewYear" class="standardInputSmallText1 mL5" maxlength="20" title="VL Test Review Year"/>
						</div>
				</div>	
				
				<div class="mT5 container">
						<div style="float:left; width:46%">VL Tested</div>
						<div style="float:left">
							<form:input id="VL_TESTED"  path="stdQualityCareDoc.vlTested" class="standardInputSmallText1 mL5" maxlength="20" title="VL Tested"/>
						</div>
				</div>	
	
			</div>						 
		</div>		
		<!--  Facility details -->
			
		<div style="padding-bottom:5px; border:1px dotted;" class="container" id="labDocumentFacilityAddressDtls">
			<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class="mL2">Facility Details</span>
			</div>

			<div class="mT5 container">
					<div style="float:left; width:10%">Organization Name</div>
					<div style="float:left; width:30%">
						<form:input id="ORGANIZATION_NAME"  path="stdQualityCareDoc.organizationName" class="standardInputText mL5" maxlength="100" title="Organization Name"/>
					</div>
					<div style="float:left; width:7%">Service Line</div>
					<div style="float:left">
						<form:input id="SERVICE_LINE"  path="stdQualityCareDoc.serviceLine" class="standardInputXLongText mL5" title="Service Line"/>
					</div>
			</div>	
			<div class="mT5 container">
					<div style="float:left; width:10%">Facility Name</div>
					<div style="float:left; width:30%">
						<form:input id="FACILITY_NAME"  path="stdQualityCareDoc.facilityName" class="standardInputText mL5" maxlength="100" title="Facility Name"/>
					</div>
					<div style="float:left; width:7%">Other Service</div>
					<div style="float:left">
						<form:input id="OTHER_SERVICE"  path="stdQualityCareDoc.otherService" class="standardInputXLongText mL5" title="Other Service"/>
					</div>
			</div>	
			
			<div class="b1 facilityDetails" style="margin: 15px 5px 5px 5px">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"><form:input  path="stdQualityCareDoc.facilityAddress" id="FACILITY_ADDRESS" class="oAdrDlg standardInputText newAddress widgetAddressFieldsReadonly facilityFields" maxlength="106" title="Facility Address"/></span>	 	                    
	 	                    
	 	                    <span class="pL20">City</span>
	 	                    <span class="pL10"> <form:input  path="stdQualityCareDoc.facilityCity" id="FACILITY_CITY" class="editable oAdrDlg standardInputSmallText newCity widgetAddressFieldsReadonly facilityFields" maxlength="30" title="Facility City"/> </span>
	 	                    
	 	                    <span class="pL20">State </span>
	 	                    <span  class="pL10">
								 <form:input  path="facilityState" id="FACILITY_STATE" class="editable oAdrDlg standardInputVerySmall  newState wS40 widgetAddressFieldsReadonly facilityFields" maxlength="2" title="Facility State"/>
							</span>
	 	                    
	 	                    <span class="pL20">Zip </span>
	 	                    <span class="pL10"> 
	 	                        <form:input  path="stdQualityCareDoc.facilityZip" id="FACILITY_ZIP" class="editable oAdrDlg nOnly  standardInputSmallText1 newZip widgetAddressFieldsReadonly facilityFields" maxlength="5" title="Facility Zip"/>
<%-- 								<form:input path="FACILITY_PLUS4" id="FACILITY_PLUS4" class="editable oAdrDlg nOnly standardInputSmallText1 facilityFields" maxlength="4" title="Facility Plus4"/> --%>
	 	                    </span>
	 	                    <span class="pL20">County </span>
	 		                   <span class="pL10"> 
									<form:select  id="FACILITY_COUNTY" tabindex="8"  path="stdQualityCareDoc.facilityCounty" class="editable oAdrDlg standardInputMedText newCounty wC100 widgetAddressFieldsReadonly facilityFields" readonly="" title="Facility County">
								        <form:option value="" label="" />
										<form:options items="${sessionScope.countyMap}"/>
								    </form:select>
		 		               </span>       	                    
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
	 	                </span>
 	            </div>
  	            <div class="personInfo pT10 pTRBL10">
 	            	<span  class="" style="padding-left:59px"> <form:input id="RAWFACILITYADDRESS"  path="rawfacilityaddress" class="editable standardInputText readonly" title="Raw Facility Address"/></span>
 	               	                 
 	                <br>               
 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
 	                    
 	            </div> 
 	        </div>						
		</div>
			
  </div>      
  
  	<div class="mT10">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
 	 <div class="button-row">
	 
			<c:choose>
				<c:when test="${qualCareDocVO.stateno != null && qualCareDocVO.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null}">
						<c:if test="${userPermissions['90'] != null}" >
							<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
						</c:if>
				</c:when>
			</c:choose>
			
	 		
	 		
	</div>
	<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
</form:form>

