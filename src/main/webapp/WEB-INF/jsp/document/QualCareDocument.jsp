<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));
%>

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
			 	                 <span class="ml10"><form:input path="STATENO" value="${STATENO}" class="standardInputSmallText readonly" tabindex="-1" maxlength="10" readonly="true"/> </span>
			 	          	</div>
		 	          	
						<c:if test="${qualCareDocVO.DOCUMENT_ID != null &&  qualCareDocVO.DOCUMENT_ID != 0}">			
		 					<div  class="floatRight">
					            <span class="mL10 bold">Document ID: </span>
								<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" tabindex="-1" maxlength="6" readonly="true"/></span>
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
                             <span class="pL5"><form:input path="FNAME" class="standardInputMedText required" maxlength="48" title="First Name"/></span>
                        </div>
                        <div class="fieldHolder25">
                            	<span class="pL10">Middle Name </span> 
                            	<span  class="pL5"><form:input path="MNAME" class="standardInputMedText" maxlength="1" title="Middle Name"/></span>
                        </div>
                        <div class="fieldHolder25">
                            	<span class="pL10">Last Name </span> 
                            	<span  class="pL5"><form:input path="LNAME" class="required standardInputMedText" maxlength="48" title="Last Name"/></span>
                        </div>
                        <br><br>
                </div>
               
                <div class="clearBoth personInfo">

                         <div class="fieldHolder25">
                            	<span class="pL10"><spring:message code="label.birthdate" /></span> 
                            	<span  class="pL5"><form:input path="DOB_TEXT" class="standardInputSmallText3" datevalidations="partialDate" maxlength="10" title="Date of Birth"/> </span>
                         </div>
                         <div class="fieldHolder25">
                            	<span class="pL10"><spring:message code="label.age" /> </span> 
                            	<span  class="pL5"><form:input path="AGE" class="nOnly standardInputSmallText2" maxlength="3" title="AGE"/></span>
                         </div>
                        <br><br>
                        
                </div>
		</div>					

				 
                <div class="container" style="border:1px dotted;">
                        <div class="fieldHolder25" style="width: 24%; border: 1px dotted; height: 476px">
                                <div style="padding:10px ">      
                                        <div> Race </div>
                                        <div>   <form:checkbox path="RACE_WHITE"  value="1"/> White </div>
                                        <div>   <form:checkbox path="RACE_BLACK" value="1"/> African American</div>
                                        <div>   <form:checkbox path="RACE_ASIAN" value="1"/> Asian</div>
                                        <div>   <form:checkbox path="RACE_INDIAN" value="1"/> American Indian/Alaskan Native</div>
                                        <div>   <form:checkbox path="RACE_PACIFIC" value="1"/> Pacific Islander</div> 
                                        <div>   <form:checkbox path="RACE_OTHER" value="1"/> Other </div>
                                        <div>   <form:checkbox path="RACE_UNKNOWN" value="1"/> Unknown</div> 
                                        <div class="mT5 container">
											<div style="float:left; width:35%">	Asian Sub Group </div>
											<div style="float:left">
												<form:input path="ASIAN_SUB_GROUP"  class="standardInputMedText mL5" title="Asian Sub Group"/>
											</div>
										</div>  
                                </div>  
                                <div style="padding:15px 0 10px 10px; border-top: dotted 1px; ">   
                                        <div>
                                                Ethnicity (Check one) Hispanic or Latino<br>
                                                <form:radiobutton path="ETHNICITY" value="01" /> Yes
                                                <form:radiobutton path="ETHNICITY" value="02" /> No
                                                <form:radiobutton path="ETHNICITY" value="09" /> Unknown
                                        </div>
                                        <div>	Hispanic Sub Group </div>
										<div>
											<form:textarea path="HISPANIC_SUB_GROUP"  rows="2" cols="50" title="Hispanic Sub Group"/>
										</div>
                                </div>  
                                <div style="padding:15px 0 10px 10px; border-top: dotted 1px; "> 
									<div class=""> Birth Sex </div>
									<div  class="">
										<span> <form:radiobutton path="BIRTH_SEX" value="M" /> Male </span>
										<span> <form:radiobutton path="BIRTH_SEX" value="F" /> Female </span>		
									</div>
								</div>
								 <div style="padding:15px 0 10px 10px; border-top: dotted 1px; "> 
								 	<div class="floatLeft pR10" style="border-right: dotted 1px;">
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
                                                <span class="riskValueField"><form:radiobutton path="IDU" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton path="IDU" value="N" /></span>
                                                <span class="riskValueField"><form:radiobutton path="IDU" value="U" /></span><br>
                                        </div>  
                                        
                                        <div class="pT8">
                                                <span class="riskLabelField">Male Sex with Male </span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_MSM" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_MSM" value="N" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_MSM" value="U" /></span><br>
                                        </div>  
                                        
                                        <div class="pT10"> <u>Heterosexual sex with:</u> </div>
                                        <ul>
                                                <li class="pT8">        
                                                        
                                                                <span class="riskLabelField1">HIV+ person </span>
                                                                <span class="riskValueField1"><form:radiobutton path="SEX_PARTNER_HIV" value="Y" /></span>
                                                                <span class="riskValueField1"><form:radiobutton path="SEX_PARTNER_HIV" value="N" /></span>  
                                                                <span class="riskValueField1"><form:radiobutton path="SEX_PARTNER_HIV" value="U" /></span>           
                                                        
                                                </li>
                                        </ul>                                        
  
                                        <div class="pT8">
                                                <span class="riskLabelField pB5">Received Blood Products </span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RECV_BLOOD_PROD" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RECV_BLOOD_PROD" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton path="RECV_BLOOD_PROD" value="U" /></span><br>                
                                        </div> 
                                        
                                        <div class="pT8">
                                                <span class="riskLabelField pB5">Received blood clotting factor </span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RECV_CLOTTING_FACTOR" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RECV_CLOTTING_FACTOR" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton path="RECV_CLOTTING_FACTOR" value="U" /></span><br>                
                                        </div> 
                                                                                
                                        <div class="pT8">
                                                <span class="riskLabelField pB5">Perinatal Exposure </span>
                                                <span class="riskValueField pB5"><form:radiobutton path="PERINATAL" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton path="PERINATAL" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton path="PERINATAL" value="U" /></span><br>                
                                        </div>  
                                        <div class="pT8">
                                                <span class="riskLabelField">Other Risk </span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_OTHER" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_OTHER" value="N" /></span>    
                                                <span class="riskValueField"><form:radiobutton path="RISK_OTHER" value="U" /></span><br><br>
                                        </div>
                                        <div>
                                                <span class="riskLabelField">Unknown </span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_UNKNOWN" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_UNKNOWN" value="N" /></span>    
                                                <span class="riskValueField"><form:radiobutton path="RISK_UNKNOWN" value="U" /></span>
                                        </div>
                                </div>
                                <div class="reportHW borderField pT10" style="height:456px">   
						 				<div class="mT5 container">
											<div style="float:left; width:35%"> Medical Record Number </div>
											<div style="float:left">
												<form:input path="MED_RECORD_NUMBER" class="standardInputMedText mL5" maxlength="50" title="Medical Record Number"/>
											</div>
										</div>			
									<div class="mT5 container">
											<div style="float:left; width:35%"> Inpatient </div>
											<div style="float:left">
												<form:input path="INPATIENT" class="standardInputMedText mL5" maxlength="20" title="Inpatient"/>
											</div>
									</div>	
									
					 				<div class="mT5 container">
										<div style="float:left; width:35%">	Housing Status </div>
										<div style="float:left">
											<form:input path="HOUSING_STATUS"  class="standardInputMedText mL5" title="Housing Status"/>
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
												<form:input path="PATIENT_STATUS" class="standardInputMedText mL5" maxlength="35" title="Patient Status"/>
											</div>
									</div>
									
					 				<div class="mT5 container">
										<div style="float:left; width:35%">	Diagnosis </div>
										<div style="float:left">
											<form:textarea path="DIAGNOSIS"  rows="2" cols="30" title="Diagnosis"/>
										</div>
									</div>
														
									<div class="mT5 container">
											<div style="float:left; width:35%">Diagnosis Date</div>
											<div style="float:left">
												<form:input path="DIAGNOSIS_DATE" class="standardInputMedText mL5" maxlength="20" title="Diagnosis Date"/>
											</div>
									</div>	
									
									<div class="mT5 container">
											<div style="float:left; width:35%">Discharge Date</div>
											<div style="float:left">
												<form:input path="DISCHARGE_DATE" class="standardInputMedText mL5" maxlength="20" title="Discharge Date"/>
											</div>
									</div>	
									
					 				<div class="mT5 container">
										<div style="float:left; width:35%">	Enrollment </div>
										<div style="float:left">
											<form:textarea path="ENROLLMENT"  rows="3" cols="30" title="Enrollment"/>
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
							<form:input path="LINKED" class="standardInputSmallText1 mL5" maxlength="20" title="linked"/>
						</div>
				</div>	

				<div class="mT5 container">
						<div style="float:left; width:35%">Linked Days</div>
						<div style="float:left">
							<form:input path="LINKAGE_DAYS" class="standardInputSmallText1 mL5" maxlength="20" title="Linked Days"/>
						</div>
				</div>	

			</div>

			<div style="float:left; width:30%; padding-left:10px;">
				<div><b>HIV Care for patient newly Dx during review period</b></div>
				<div class="mT5 container">
						<div style="float:left; width:35%">HIV Care</div>
						<div style="float:left">
							<form:input path="HIV_CARE" class="standardInputSmallText1 mL5" maxlength="10" title="HIV Care"/>
						</div>
				</div>	
				<div class="mT5 container">
						<div style="float:left; width:35%">HIV Care Date</div>
						<div style="float:left">
							<form:input path="HIV_CARE_DATE" class="standardInputSmallText1 mL5" maxlength="10" title="HIV Care Date"/>
						</div>
				</div>	
								
			</div>		
			
			<div style="float:left; width:30%; padding-left:10px; border-left:1px dotted;">
				<div><b>ARV Therapy</b></div>
				<div class="mT5 container">
						<div style="float:left; width:55%">Receive ARV (besides PrEP)</div>
						<div style="float:left">
							<form:input path="ARV" class="standardInputSmallText1 mL5" maxlength="10" title="ARV"/>
						</div>
				</div>	
				<div class="mT5 container">
						<div style="float:left; width:55%">On ARV</div>
						<div style="float:left">
							<form:input path="ON_ARV" class="standardInputSmallText1 mL5" maxlength="10" title="On ARV"/>
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
							<form:input path="FIRST_SUPPRESSED_DATE_NEWLY_DX" class="standardInputSmallText1 mL5" maxlength="20" title="First Suppressed Date Newly Dx"/>
						</div>
				</div>	

				<div class="mT5 container">
						<div style="float:left; width:70%">Suppressed Days</div>
						<div style="float:left">
							<form:input path="SUPPRESSION_DAYS" class="standardInputSmallText1 mL5" maxlength="20" title="Suppressed Days"/>
						</div>
				</div>	

				<div class="mT5 container">
						<div style="float:left; width:70%">Suppressed Days Discharge</div>
						<div style="float:left">
							<form:input path="SUPPRESSION_DAYS_DISCHARGE" class="standardInputSmallText1 mL5" maxlength="20" title="Suppressed Days Discharge"/>
						</div>
				</div>	

				<div class="mT5 container">
						<div style="float:left; width:70%">Suppressed Days HIV Care</div>
						<div style="float:left">
							<form:input path="SUPPRESSION_DAYS_HIV_CARE" class="standardInputSmallText1 mL5" maxlength="20" title="Suppressed Days HIV Care"/>
						</div>
				</div>	
	
			</div>	
		
			<div style="float:left; width:36%; padding-bottom:5px; padding-left:10px; border-left:1px dotted;">
			
				<div><b>Viral Load Testing</b></div>
				
				<div class="mT5 container">
						<div style="float:left; width:46%">Newly Dx First VL Date </div>
						<div style="float:left">
							<form:input path="FIRST_VL_DATE_NEWLY_DX" class="standardInputSmallText1 mL5" maxlength="20" title="First VL Date Newly Dx"/>
						</div>
				</div>	
	
				<div class="mT5 container">
						<div style="float:left; width:46%">Suppressed Final VL</div>
						<div style="float:left">
							<form:input path="SUPPRESSED_FINAL_VL" class="standardInputSmallText1 mL5" maxlength="20" title="Suppressed Final VL"/>
						</div>
				</div>	
	
				<div class="mT5 container">
						<div style="float:left; width:46%">VL Reviewed</div>
						<div style="float:left">
							<form:input path="VL_REVIEWED" class="standardInputSmallText1 mL5" maxlength="20" title="VL Reviewed"/>
						</div>
				</div>	
	
				<div class="mT5 container">
						<div style="float:left; width:46%">VL Suppressed</div>
						<div style="float:left">
							<form:input path="VL_SUPPRESSED" class="standardInputSmallText1 mL5" maxlength="20" title="VL Suppressed"/>
						</div>
				</div>	
	
				<div class="mT5 container">
						<div style="float:left; width:46%">VL in review year </div>
						<div style="float:left">
							<form:input path="VL_TEST_REVIEW_YEAR" class="standardInputSmallText1 mL5" maxlength="20" title="VL Test Review Year"/>
						</div>
				</div>	
				
				<div class="mT5 container">
						<div style="float:left; width:46%">VL Tested</div>
						<div style="float:left">
							<form:input path="VL_TESTED" class="standardInputSmallText1 mL5" maxlength="20" title="VL Tested"/>
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
						<form:input path="ORGANIZATION_NAME" class="standardInputText mL5" maxlength="100" title="Organization Name"/>
					</div>
					<div style="float:left; width:7%">Service Line</div>
					<div style="float:left">
						<form:input path="SERVICE_LINE" class="standardInputXLongText mL5" title="Service Line"/>
					</div>
			</div>	
			<div class="mT5 container">
					<div style="float:left; width:10%">Facility Name</div>
					<div style="float:left; width:30%">
						<form:input path="FACILITY_NAME" class="standardInputText mL5" maxlength="100" title="Facility Name"/>
					</div>
					<div style="float:left; width:7%">Other Service</div>
					<div style="float:left">
						<form:input path="OTHER_SERVICE" class="standardInputXLongText mL5" title="Other Service"/>
					</div>
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
	 	                        <form:input path="FACILITY_ZIP" id="FACILITY_ZIP" class="editable oAdrDlg nOnly  standardInputSmallText1 newZip widgetAddressFieldsReadonly facilityFields" maxlength="5" title="Facility Zip"/>
<%-- 								<form:input path="FACILITY_PLUS4" id="FACILITY_PLUS4" class="editable oAdrDlg nOnly standardInputSmallText1 facilityFields" maxlength="4" title="Facility Plus4"/> --%>
	 	                    </span>
	 	                    <span class="pL20">County </span>
	 		                   <span class="pL10"> 
									<form:select  id="FACILITY_COUNTY" tabindex="8" path="FACILITY_COUNTY" class="editable oAdrDlg standardInputMedText newCounty wC100 widgetAddressFieldsReadonly facilityFields" readonly="" title="Facility County">
								        <form:option value="" label="" />
										<form:options items="${sessionScope.countyMap}"/>
								    </form:select>
		 		               </span>       	                    
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
	 	                </span>
 	            </div>
  	            <div class="personInfo pT10 pTRBL10">
 	            	<span  class="" style="padding-left:59px"> <form:input path="RAWFACILITYADDRESS" class="editable standardInputText readonly" title="Raw Facility Address"/></span>
 	               	                 
 	                <br>               
 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
 	                    
 	            </div> 
 	        </div>						
		</div>
			
  </div>      
  
  	<div class="mT10">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
 	 <div class="button-row">
	 
			<c:choose>
				<c:when test="${qualCareDocVO.STATENO != null && qualCareDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
						<c:if test="${userPermissions['90'] != null}" >
							<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
						</c:if>
				</c:when>
			</c:choose>
			
	 		
	 		
	</div>
	<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
</form:form>

