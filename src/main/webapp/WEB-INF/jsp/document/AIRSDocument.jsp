<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${staticPath}/js/docs/airsDoc.js?v=${buildDate}"></script>
<link href="${staticPath}/css/docs/airsForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/docs/commonForm.css" rel="stylesheet" type="text/css" />

<form:form modelAttribute="stdAIRSDocVO" id="stdAIRSDocVO" cssClass="documentFormVO documentDescription">
<form:errors path="*" cssClass="errorblock" element="div" />

        <div id="AIRSDocTab" style="background-color: transparent">
                
                <div style="padding-left:43%; " class="headingCls">
                                AIRS &nbsp;  DOCUMENT
                </div>
                
                
                <div class="pB30 pT20 bold">
		
					
					<c:if test="${stdAIRSDocVO.parentDocumentId != null &&  stdAIRSDocVO.parentDocumentId != 0}">
		
				<div  class="floatLeft width70Perc" id="parentDocumentIdSection">
				            <span class="parentIdSectionLabel mL10">Parent DocumentId: </span>
							<span class="parentIdSectionValue "><a href="#" onclick="loadDocumentDlg(${stdAIRSDocVO.parentDocumentId}, '${param.documentType}')">${stdAIRSDocVO.parentDocumentId}</a></span>
							
				         </div>	
			         </c:if>
			         
					<div  class="floatRight">
			            <span class="mL10">Document ID: </span>
						<span class=" "><form:input id="DOCUMENT_ID"  path="stdAirsDoc.documentId" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
			         </div>	
		
				</div>

                
                
                
                
           
				<div class="container w100 nonRawEditFields pB10 bold">
		 	         <div style="float:left; width: 40%">
		 	            <div >
		 	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
		 	                 <span class="ml10"><form:input id="STATENO"  path="stateno" class="standardInputSmallText readonly" maxlength="10" readonly="true"/> </span>
		 	            </div>    
		 	          </div>
		 	          <div style="float:left; width: 30%">
		 	         	<div >
		 	                <span class="ml10"> eHARS Import Date: </span>
		 	                <fmt:formatDate value="${stdAIRSDocVO.stdAirsDoc.eharsImportDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
		 			 		<span class="ml10"><form:input id="EHARS_IMPORT_DATE"  path="stdAirsDoc.eharsImportDate" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/> </span>
		 	            </div>    
		 	         </div>
		 	         <div style="float:left; width: 30%; text-align: right">
		 	            <div >
		 	                <span class="mL10">NYC SHIP Date: </span>
		 	                <fmt:formatDate value="${stdAIRSDocVO.stdAirsDoc.nycShipDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedNYC_SHIP_DATE_TEXT" ></fmt:formatDate>
		 			 		<span class="ml10"><form:input id="NYC_SHIP_DATE"  path="stdAirsDoc.nycShipDate" value="${theFormattedNYC_SHIP_DATE_TEXT}" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
		 	            </div> 
		 	         </div>     
		 		</div>	 
		 		
		 		
   				<div class="bold"><jsp:include page="statenoDetails.jsp"></jsp:include></div>               
                
                <!--  Person details -->

                <div class="container bold" id="patientInformation">  
                        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Patient Information</span>
                        </div>
                        <div style="padding-left:10px;padding-top:10px">
                                <div class="personInfo">
                                			<div class="fieldHolder20">
	                                            <span class="pL10">First Name </span> 
	                                            <span class="pL5"><form:input  path="stdAirsDoc.fname" id="AIRS_FNAME" class="standardInputMedText required" title="First Name" maxLength="20"/></span>
	                                            <div class="pL10 v_error AIRS_FNAME "></div> 
	                                        </div>
	                                        <div class="fieldHolder20">
                                            	<span class="pL10">Middle Name </span> 
                                            	<span  class="pL5"><form:input  path="stdAirsDoc.mname" id="AIRS_MNAME" class="standardInputSmallText"  title="Middle Name" maxLength="25"/></span>
                                            	<div class="pL10 v_error AIRS_MNAME "></div>
                                            </div>
	                                        <div class="fieldHolder20">
                                            	<span class="pL10">Last Name </span> 
                                            	<span  class="pL5"><form:input  path="stdAirsDoc.lname" id="AIRS_LNAME" class="standardInputMedText required" title="Last Name" maxLength="25"/></span>
                                            	<div class="pL10 v_error AIRS_LNAME "></div>
                                            </div>
                                            <c:if test="${stdAIRSDocVO.airsVerType == 'OLD'}">
	                                        	<div class="fieldHolder20">
	                                      			<span class="pL10">Alt Name </span> 
	                                  				<span class="pL5"><form:input  path="stdAirsDoc.altName" id="AIRS_ALT_NAME" class="standardInputMedText" title="Alt Name" maxLength="25"/></span>
	                                  				<div class="pL10 v_error AIRS_ALT_NAME "></div>
                                  				</div>  
                                  			</c:if>
                                            <div class="fieldHolder20">
	                                       		<span class="pL10">Date of Birth </span>
	                                       		<span  class="pL10"><form:input  path="dob" id="DATE_OF_BIRTH" class="dPicker mask standardInputSmallText" title="Date of Birth" maxLength="10" datevalidations="completeDate"/></span>
	                                       		<div class="pL10 v_error DATE_OF_BIRTH "></div>
                                       		</div>
                                        <br><br>
                                        
                                        
                                </div>
                               	<div class="clearBoth personInfo">
                                 		<div class="fieldHolder20">
	                                        <span class="pL10">Date of Death </span>
	                                        <span class="pL10"> 
	                                                <form:input  path="stdAirsDoc.dodText" id="DATE_OF_DEATH" class="dPicker mask standardInputSmallText" title="Date of Death" maxLength="10" datevalidations="completeDate"/>
	                                        </span>
	                                        <div class="pL10 v_error DATE_OF_DEATH "></div>
	                                    </div>                                       
                                       <div class="fieldHolder10">
                                       		<span class="pL10">SSN</span>
                                       		<span class="pL10"><form:input  path="stdAirsDoc.ssn" id="AIRS_SSN" class="standardInputSmallText1" title="SSN" maxLength="9"/></span>
                                       		<div class="pL10 v_error AIRS_SSN "></div>
                                       	</div>
                                      	<div class="fieldHolder10">
                                        	<span class="pL10">Age</span>
                                        	<span class="pL10"><form:input id="AGE"  path="stdAirsDoc.age" class="standardInputSmallText1 nOnly" title="Age" maxLength="3"/>&nbsp;&nbsp;</span>
                                        </div>
	                                    <div class="fieldHolder30">
                                        	<span class="pL10">Birth Country </span>
                                        	<span  class="pL10">
                                        	
                                        		<form:select id="BIRTH_COUNTRY" class="roundedBrdr"  path="stdAirsDoc.birthCountry" title="Birth Country">
													<form:option value=""></form:option>
													<form:options items="${sessionScope.countryMap}"/>
													<form:option value="OTH">Other</form:option>
													<form:option value="UNK">Unknown</form:option>
												</form:select>
                                        	</span>
                                        </div>                                       	
                                       	<div class="fieldHolder25" id="BIRTH_COUNTRY_DEPENDENT_FIELD">
                                        	<span class="pL10">Birth Country Other </span>
                                        	<span  class="pL10"><form:input id="BIRTH_COUNTRY_OTHER"  path="stdAirsDoc.birthCountryOther" class="standardInputSmallText " title="Birth Country Other" maxlength="36"/>&nbsp;&nbsp;</span>
                                        </div>                                        
                                                                                        
                                </div><br>
                                
                                <div class="b1" style="margin: 15px 5px 10px 0px" id="airsDocumentPersonAddressDtls">
					 	            <div class="personInfo pTRBL10">
					 	                <span class="personLabelDoc"> Address </span>
				 		                <span> 	                	
					 	                	<span class="pL10"> <form:input  path="stdAirsDoc.personAddress" id="airsAdd" class="standardInputText widgetAddressFieldsReadonly newAddress" title="Address" maxLength="62"/>  </span>	 	        
					 	                	            	 	                    
					 	                    <span class="pL30">City</span>
					 	                    <span class="pL10"><form:input  path="stdAirsDoc.personCity" id="airsCity" class="standardInputSmallText widgetAddressFieldsReadonly newCity" title="City" maxLength="30"/></span>
					 	                    
					 	                    <span class="pL30">State </span>
					 	                    <span  class="pL10">
												<form:input  path="stdAirsDoc.personState" id="airsState" class="standardInputSmallText1 widgetAddressFieldsReadonly newState" title="State" maxLength="2"/>
											</span>
					 	                    
					 	                    <span class="pL30">Zip </span>
					 	                    <span class="pL10"> 
					 	                       <form:input  path="stdAirsDoc.personZip" id="airsZip" class="standardInputSmallText1 widgetAddressFieldsReadonly newZip" title="Zipcode" maxLength="5"/>
					 	                    </span> 
					 	                    
					 	                    <span class="pL30">County </span>
					 	                    <span class="pL10"> 
					 	                    	<form:select id="PERSON_COUNTY"  path="stdAirsDoc.personCounty"  cssClass="oAdrDlg newCounty roundedBrdr widgetAddressFieldsReadonly newCounty" title="County" maxLength="3"> 
													<form:option value=""></form:option>
													<form:options items="${sessionScope.countyMap}"/>
												 </form:select>
					 	                    </span>     	                    
					 	                    <br>
					 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
					 	                </span>
					 	            </div>
					 	            <div class="personInfo pT10 pTRBL10">
					 	            	<span  class="nonRawEditFields" style="padding-left:59px"><form:input  path="address" id="airsRawAdd" class="standardInputText readonly" title="Address" maxLength="62"/></span>
					 	              	
					 	              	<span class="pL30">Phone </span>
					 	                    <span class="pL10"> 
					 	                       <form:input  path="stdAirsDoc.personPhone" id="airsPhone" class="standardInputSmallText" title="Telephone" maxLength="20"/>
					 	                    </span>
					 	                    
					 	                     <span style="float: right;" class="mr50">
												<a href="javascript:openAddressWidget('airsDocumentPersonAddressDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
											 </span>    	              	
					 	                <br>               
					 	                <span class="nonRawEditFields" ><span class="labelunderneathAddress "> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span></span>
					 	                    
					 	            </div>
					 	        </div>                               
                        </div>        
                </div>
					
					
                <div class="raceRiskReprt">           
                <c:if test="${stdAIRSDocVO.airsVerType == 'OLD'}">

                        <div class="fieldHolder25" style="width: 40%; border: 1px dotted; height: auto">
                                <div style="padding:10px ">   
	                                        <div class="boldFont"> Race </div>
	                                        <div  class="">
												<span> <form:checkbox id="RACE_WHITE1"  path="stdAirsDoc.raceWhite"  value="1"/> White </span>
												<span> <form:checkbox id="RACE_BLACK1"  path="stdAirsDoc.raceBlack" value="1"/> African American </span>		
												<span> <form:checkbox id="RACE_ASIAN1"  path="stdAirsDoc.raceAsian" value="1"/> Asian </span>		
												<span> <form:checkbox id="RACE_INDIAN1"  path="stdAirsDoc.raceIndian" value="1"/> American Indian/Alaskan Native </span><br>
												<span> <form:checkbox id="RACE_PACIFIC1"  path="stdAirsDoc.racePacific" value="1"/> Pacific Islander </span>	
												<c:if test="${stdAIRSDocVO.airsVerType == 'OLD'}">	
													<span> <form:checkbox id="RACE_OTHER1"  path="stdAirsDoc.raceOther" value="1"/> Other </span>		
												</c:if>	
												<span> <form:checkbox id="RACE_UNKNOWN1"  path="stdAirsDoc.raceUnknown" value="1"/> Unknown </span>														
											</div>
                                </div>  
                                <div style="padding:10px 0 10px 10px; border-top: dotted 1px; ">
                                        <div class="">
                                                <span class="bold">Ethnicity (Check one) Hispanic or Latino	</span>
                                                <form:radiobutton id="ETHNICITY1"  path="stdAirsDoc.ethnicity" value="01" /> Yes
                                                <form:radiobutton id="ETHNICITY2"  path="stdAirsDoc.ethnicity" value="02" /> No
                                                <form:radiobutton id="ETHNICITY3"  path="stdAirsDoc.ethnicity" value="09" /> Unknown
                                        </div>
                                </div>  
                                <div style="padding:10px 0 10px 10px; border-top: dotted 1px; "> 
									<div class=""> <span class="bold">Birth Sex </span>
										<span> <form:radiobutton id="BIRTH_SEX1"  path="stdAirsDoc.birthSex" value="M" /> Male </span>
										<span> <form:radiobutton id="BIRTH_SEX2"  path="stdAirsDoc.birthSex" value="F" /> Female </span>		
										<span> <form:radiobutton id="BIRTH_SEX3"  path="stdAirsDoc.birthSex" value="I" /> Intersex </span>		
										<span> <form:radiobutton id="BIRTH_SEX4"  path="stdAirsDoc.birthSex" value="U" /> Chose not to respond </span>		
									</div>
								</div>
								 <div style="padding:10px 0 10px 10px; border-top: dotted 1px; "> 
								 	<div class="floatLeft pR10">
										<div class="boldFont"> Current Gender </div>
										<div  class="">
											<div> <form:radiobutton id="CURRENT_GENDER1"  path="stdAirsDoc.currentGender" value="M" /> Male </div>
											<div> <form:radiobutton id="CURRENT_GENDER2"  path="stdAirsDoc.currentGender" value="F" /> Female </div>
											<div> <form:radiobutton id="CURRENT_GENDER3"  path="stdAirsDoc.currentGender" value="B" /> Non-binary </div>
											<div> <form:radiobutton id="CURRENT_GENDER4"  path="stdAirsDoc.currentGender" value="C" /> Gender non-conforming</div>	
										</div>
									</div>
								 	<div class="floatLeft pR10" style="border-right: dotted 1px; padding-top:15px">
										<div class="boldFont"></div>
										<div  class="">
											<div> </div>
											<div> <form:radiobutton id="CURRENT_GENDER5"  path="stdAirsDoc.currentGender" value="Q" /> Questioning</div>
											<div> <form:radiobutton id="CURRENT_GENDER6"  path="stdAirsDoc.currentGender" value="R" /> Chose not to respond </div>		
											<div> <form:radiobutton id="CURRENT_GENDER7"  path="stdAirsDoc.currentGender" value="U" /> Unknown</div>		
										</div>
									</div>
									<div class="floatLeft pL10">
										<div class="boldFont"> Transgender </div>
										<div  class="">
											<div> <form:radiobutton id="CURRENT_GENDER8"  path="stdAirsDoc.currentGender" value="X" /> MTF </div>		
											<div> <form:radiobutton id="CURRENT_GENDER9"  path="stdAirsDoc.currentGender" value="Y" /> FTM </div>		
											<div> <form:radiobutton id="CURRENT_GENDER10"  path="stdAirsDoc.currentGender" value="T" /> Unspecified </div>														
										</div>
									</div>
								</div>
                                <br> 
                        </div>
                        <div style="width: 58%; float:left">
                        <!--  Risk details -->
                                <div class=" riskHW borderField pT10" style="height:auto">

                                        <div class="pT8">
                                                <span class="riskLabelField bold">Risk Details</span>
                                                <span class="riskValueField">Yes</span>
                                                <span class="riskValueField">No</span>
                                                <span class="riskValueField">Unknown</span><br>
                                        </div>  
                                        <div class="pT8">
                                                <span class="riskLabelField">Sex With Male</span>
                                                <span class="riskValueField"><form:radiobutton id="SWM1"  path="stdAirsDoc.swm" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton id="SWM2"  path="stdAirsDoc.swm" value="N" /></span>
                                                <span class="riskValueField"><form:radiobutton id="SWM3"  path="stdAirsDoc.swm" value="U" /></span><br>                
                                        </div>
                                        <div class="pT8">
                                                <span class="riskLabelField">Sex With Female</span>
                                                <span class="riskValueField"><form:radiobutton id="SWF1"  path="stdAirsDoc.swf" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton id="SWF2"  path="stdAirsDoc.swf" value="N" /></span>
                                                <span class="riskValueField"><form:radiobutton id="SWF3"  path="stdAirsDoc.swf" value="U" /></span>
                                        </div>
                                        <div class="pT8 pB5">
                                                <span class="riskLabelField pT8 pB5">Injection Drug Use </span>
                                                <span class="riskValueField pT8 pB5"><form:radiobutton id="IDU1"  path="stdAirsDoc.idu" value="Y" /></span>
                                                <span class="riskValueField pT8 pB5"><form:radiobutton id="IDU2"  path="stdAirsDoc.idu" value="N" /></span>
                                                <span class="riskValueField pT8 pB5"><form:radiobutton id="IDU3"  path="stdAirsDoc.idu" value="U" /></span> 
                                        </div>  
                                        <div class="pT10 bold"> <u>Heterosexual sex with:</u> </div>
                                        <ul>
                                                <li class="pT8">
                                                        
                                                                <span class="riskLabelField1">Injection Drug User </span>
                                                                <span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_IDU1"  path="stdAirsDoc.sexPartnerIdu" value="Y" /></span>
                                                                <span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_IDU2"  path="stdAirsDoc.sexPartnerIdu" value="N" /></span>
                                                                <span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_IDU3"  path="stdAirsDoc.sexPartnerIdu" value="U" /></span> 
                                                        
                                                </li>
                                                <li class="pT8">                
                                                        
                                                                <span class="riskLabelField1">Received Blood Products </span>
                                                                <span class="riskValueField1"><form:radiobutton id="RECV_BLOOD_PROD1"  path="stdAirsDoc.recvBloodProd" value="Y" /></span>
                                                                <span class="riskValueField1"><form:radiobutton id="RECV_BLOOD_PROD2"  path="stdAirsDoc.recvBloodProd" value="N" /></span>  
                                                                <span class="riskValueField1"><form:radiobutton id="RECV_BLOOD_PROD3"  path="stdAirsDoc.recvBloodProd" value="U" /></span>   
                                                        
                                                </li>
                                                <li class="pT8">        
                                                        
                                                                <span class="riskLabelField1">HIV+ person </span>
                                                                <span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_HIV1"  path="stdAirsDoc.sexPartnerHiv" value="Y" /></span>
                                                                <span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_HIV2"  path="stdAirsDoc.sexPartnerHiv" value="N" /></span>  
                                                                <span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_HIV3"  path="stdAirsDoc.sexPartnerHiv" value="U" /></span>           
                                                        
                                                </li>
                                        </ul>
                                        <div class="pT8 pB5">
                                                <span class="riskLabelField pB5">Perinatal Exposure </span>
                                                <span class="riskValueField pB5"><form:radiobutton id="PERINATAL1"  path="stdAirsDoc.perinatal" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="PERINATAL2"  path="stdAirsDoc.perinatal" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton id="PERINATAL3"  path="stdAirsDoc.perinatal" value="U" /></span>                
                                        </div>  
                                        <div class="pT8">
                                                <span class="riskLabelField">Other Risk </span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_OTHER1"  path="stdAirsDoc.riskOther" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_OTHER2"  path="stdAirsDoc.riskOther" value="N" /></span>    
                                                <span class="riskValueField"><form:radiobutton id="RISK_OTHER3"  path="stdAirsDoc.riskOther" value="U" /></span>     
                                        </div><br>  
                                        <div><br>
                                                <span class="riskLabelField">Unknown </span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_UNKNOWN1"  path="stdAirsDoc.riskUnknown" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_UNKNOWN2"  path="stdAirsDoc.riskUnknown" value="N" /></span>    
                                                <span class="riskValueField"><form:radiobutton id="RISK_UNKNOWN3"  path="stdAirsDoc.riskUnknown" value="U" /></span>
                                        </div>  
                                  
                                </div>

                                <div class="borderField pT10 bold" style="height:auto; width:265px;float:right;">   
                                		 <div class="mT5 container">
											<div class="fieldHolder50">TC ID</div>
											<div style="float:left">
												<form:input id="TC_ID"  path="stdAirsDoc.tcId" class="standardInputsmallerText mL5 readonly" title="TC ID" maxlength="10"/>
											</div>
										</div>
										<div class="mT5 container">
											<div class="fieldHolder50">URN</div>
											<div style="float:left">
												<form:input id="URN"  path="stdAirsDoc.urn" class="standardInputsmallerText mL5 readonly" title="URN" maxlength="9" />
											</div>
										</div>
										<div class="mT5 container">
											<div class="fieldHolder50">Is Medicaid</div>
											<div style="float:left">
												<form:checkbox id="IS_MEDICAID1"  path="stdAirsDoc.isMedicaid" value="1"/>
											</div>
										</div>
										<div class="mT5 container">
											<div class="fieldHolder50">CIN
											</div>
											<div style="float:left">
												<form:input  path="stdAirsDoc.medicaidNumber" id="AIRS_MEDICAID_NUMBER" title="CIN" class="standardInputsmallerText mL5" maxlength="8"/>
											</div>
											<div class="v_error AIRS_MEDICAID_NUMBER "></div>
										</div>
										<div class="mT5 container">
											<div class="fieldHolder50">Case No</div>
											<div style="float:left">
												<form:input id="CASE_NO"  path="stdAirsDoc.caseNo" class="standardInputsmallerText mL5" maxlength="10" title="Case No"/>
											</div>
										</div>
										<div class="mT5 container">
											<div class="fieldHolder50">Patient Record No.</div>
											<div style="float:left">
												<form:input id="MED_RECORD_NUMBER"  path="stdAirsDoc.medRecordNumber" class="standardInputsmallerText mL5" maxlength="20" title="Patient Record No."/>
											</div>
										</div>
										<div class="mT5 container">
											<div class="fieldHolder50">Date Results Provided</div>
											<div style="float:left">
												<form:input  path="stdAirsDoc.sigDateText" id="AIRS_SIG_DATE_TEXT" class="standardInputsmallerText mL5 dPicker mask" datevalidations="partialDate"  maxlength="10" title="Sign Date"/>
											</div>
											
											<div class="v_error AIRS_SIG_DATE_TEXT "></div>
										</div>	               
										<div class="mT5 container">
											<div class="fieldHolder50">Record Type</div>
											<div style="float:left">
												<form:input id="RECORD_TYPE"  path="stdAirsDoc.recordType" class="standardInputsmallerText mL5" maxlength="1" title="Record Type"/>
											</div>
										</div>	               
										<div class="mT5 container">
											<div class="fieldHolder50">CTR Flag</div>
											<div style="float:left">
												<form:checkbox id="CTR_FLAG1"  path="stdAirsDoc.ctrFlag" value="1"/>
											</div>
										</div>
                                </div>
                        </div>  
                      
                </c:if>
                <c:if test="${stdAIRSDocVO.airsVerType == 'NEW'}">
                
                        <div class="fieldHolder25" style="width: 30%; border: 1px dotted; height: auto">
                                <div style="padding:10px ">   
	                                        <div class="boldFont"> Race </div>
	                                        <div  class="">
												<span> <form:checkbox id="RACE_WHITE2"  path="stdAirsDoc.raceWhite"  value="1"/> White </span>
												<span> <form:checkbox id="RACE_BLACK2"  path="stdAirsDoc.raceBlack" value="1"/> African American </span>		
												<span> <form:checkbox id="RACE_ASIAN2"  path="stdAirsDoc.raceAsian" value="1"/> Asian </span>		<br>
												<span> <form:checkbox id="RACE_INDIAN2"  path="stdAirsDoc.raceIndian" value="1"/> American Indian/Alaskan Native </span>
												<span> <form:checkbox id="RACE_PACIFIC2"  path="stdAirsDoc.racePacific" value="1"/> Pacific Islander </span>	
												<c:if test="${stdAIRSDocVO.airsVerType == 'OLD'}">	
													<span> <form:checkbox id="RACE_OTHER2"  path="stdAirsDoc.raceOther" value="1"/> Other </span>		
												</c:if>	
												<span> <form:checkbox id="RACE_UNKNOWN2"  path="stdAirsDoc.raceUnknown" value="1"/> Unknown </span>														
											</div>
                                </div>  
                                <div style="padding:10px 0 10px 10px; border-top: dotted 1px; ">
                                        <div class="">
                                                <span class="bold">Ethnicity (Check one) Hispanic or Latino	</span>
                                                <form:radiobutton id="ETHNICITY4"  path="stdAirsDoc.ethnicity" value="01" /> Yes
                                                <form:radiobutton id="ETHNICITY5"  path="stdAirsDoc.ethnicity" value="02" /> No
                                                <form:radiobutton id="ETHNICITY6"  path="stdAirsDoc.ethnicity" value="09" /> Unknown
                                        </div>
                                </div>  
                                <br> 
                        </div>
                        <div class="fieldHolder25" style="width: 33%; border: 1px dotted; height: auto">
                                <div style="padding:10px 0 10px 10px; border-top: dotted 1px; "> 
									<div class=""> <span class="bold">Birth Sex </span>
										<span> <form:radiobutton id="BIRTH_SEX5"  path="stdAirsDoc.birthSex" value="M" /> Male </span>
										<span> <form:radiobutton id="BIRTH_SEX6"  path="stdAirsDoc.birthSex" value="F" /> Female </span>		
										<span> <form:radiobutton id="BIRTH_SEX7"  path="stdAirsDoc.birthSex" value="I" /> Intersex </span>		
										<span> <form:radiobutton id="BIRTH_SEX8"  path="stdAirsDoc.birthSex" value="U" /> Chose not to respond </span>		
									</div>
								</div>
								 <div style="padding:10px 0 10px 10px; border-top: dotted 1px; "> 
								 	<div class="floatLeft pR10 pB10">
										<div class="boldFont"> Current Gender </div>
										<div  class="">
											<div> <form:radiobutton id="CURRENT_GENDER11"  path="stdAirsDoc.currentGender" value="M" /> Male </div>
											<div> <form:radiobutton id="CURRENT_GENDER12"  path="stdAirsDoc.currentGender" value="F" /> Female </div>
											<div> <form:radiobutton id="CURRENT_GENDER13"  path="stdAirsDoc.currentGender" value="B" /> Non-binary </div>
											<div> <form:radiobutton id="CURRENT_GENDER14"  path="stdAirsDoc.currentGender" value="C" /> Gender non-conforming</div>	
										</div>
									</div>
								 	<div class="floatLeft pR10" style="border-right: dotted 1px; padding-top:15px">
										<div class="boldFont"></div>
										<div  class="">
											<div> </div>
											<div> <form:radiobutton id="CURRENT_GENDER15"  path="stdAirsDoc.currentGender" value="Q" /> Questioning</div>
											<div> <form:radiobutton id="CURRENT_GENDER16"  path="stdAirsDoc.currentGender" value="R" /> Chose not to respond </div>		
											<div> <form:radiobutton id="CURRENT_GENDER17"  path="stdAirsDoc.currentGender" value="U" /> Unknown</div>		
										</div>
									</div>
									<div class="floatLeft pL10">
										<div class="boldFont"> Transgender </div>
										<div  class="">
											<div> <form:radiobutton id="CURRENT_GENDER18"  path="stdAirsDoc.currentGender" value="X" /> MTF </div>		
											<div> <form:radiobutton id="CURRENT_GENDER19"  path="stdAirsDoc.currentGender" value="Y" /> FTM </div>		
											<div> <form:radiobutton id="CURRENT_GENDER20"  path="stdAirsDoc.currentGender" value="T" /> Unspecified </div>														
										</div>
									</div>
								</div>
                                <br> 
                        </div>
                        <div style="float:left">
                        <!--  Risk details -->
                                <div class="pT10 pL10 bold" style="height:auto; width:230px;float:right;">   
                                		 <div class="mT5 container">
											<div class="fieldHolder40">TC ID</div>
											<div style="float:left">
												<form:input id="TC_ID"  path="stdAirsDoc.tcId" class="standardInputsmallerText mL5 readonly" title="TC ID" maxlength="10"/>
											</div>
										</div>
										<div class="mT5 container">
											<div class="fieldHolder40">URN</div>
											<div style="float:left">
												<form:input id="URN"  path="stdAirsDoc.urn" class="standardInputsmallerText mL5 readonly" title="URN" maxlength="9" />
											</div>
										</div>
										<div class="mT5 container">
											<div class="fieldHolder40">Is Medicaid</div>
											<div style="float:left">
												<form:checkbox id="IS_MEDICAID2"  path="stdAirsDoc.isMedicaid" value="1"/>
											</div>
										</div>
										<div class="mT5 container">
											<div class="fieldHolder40">CIN
											</div>
											<div style="float:left">
												<form:input  path="stdAirsDoc.medicaidNumber" id="AIRS_MEDICAID_NUMBER" title="CIN" class="standardInputsmallerText mL5" maxlength="8"/>
											</div>
											<div class="v_error AIRS_MEDICAID_NUMBER "></div>
										</div>
										<div class="mT5 container">
											<div class="fieldHolder40">Case No</div>
											<div style="float:left">
												<form:input id="CASE_NO"  path="stdAirsDoc.caseNo" class="standardInputsmallerText mL5" maxlength="10" title="Case No"/>
											</div>
										</div>
                                </div>
                        </div>  
                        <div style="width: 19%; float:left;">
                        <!--  Risk details -->
                                <div class="pT10 bold" style="height:auto; width:265px;float:right;">   

										<div class="mT5 container">
											<div class="fieldHolder50">Patient Record No.</div>
											<div style="float:left">
												<form:input id="MED_RECORD_NUMBER"  path="stdAirsDoc.medRecordNumber" class="standardInputsmallerText mL5" maxlength="20" title="Patient Record No."/>
											</div>
										</div>
										<div class="mT5 container">
											<div class="fieldHolder50">Date Results Provided</div>
											<div style="float:left">
												<form:input  path="stdAirsDoc.sigDateText" id="AIRS_SIG_DATE_TEXT" class="standardInputsmallerText mL5 dPicker mask" datevalidations="partialDate"  maxlength="10" title="Sign Date"/>
											</div>
											
											<div class="v_error AIRS_SIG_DATE_TEXT "></div>
										</div>	               
										<div class="mT5 container">
											<div class="fieldHolder50">Record Type</div>
											<div style="float:left">
												<form:input id="RECORD_TYPE"  path="stdAirsDoc.recordType" class="standardInputsmallerText mL5" maxlength="1" title="Record Type"/>
											</div>
										</div>	               
										<div class="mT5 container">
											<div class="fieldHolder50">CTR Flag</div>
											<div style="float:left">
												<form:checkbox id="CTR_FLAG2"  path="stdAirsDoc.ctrFlag" value="1"/>
											</div>
										</div>
                                </div>
                        </div>  
                
                </c:if>
                </div>  

                <!--  HIV Testing History Information -->
                <div id="personHIVDetails" class="container">   
                        <div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">HIV Testing History Information</span>     
                        </div>
                        <c:if test="${stdAIRSDocVO.airsVerType == 'OLD'}">
                        <div id="personHIVDetailsLeft2" class="">
                        	   <!--  Report details -->
                             
                                <div class="pL30 pT10 bold">
                                        <span class="riskLabelField2">Date of Admission</span>
                                        <span class="riskValueField3"><form:input   path="stdAirsDoc.admissionDateText" id="AIRS_ADMISSION_DATE_TEXT" class="dPicker mask standardInputsmallerText"  title="Admission Date" datevalidations="partialDate" maxlength="10"/></span>
                                        <div class="v_error AIRS_ADMISSION_DATE_TEXT "></div> 
                                </div>
                                <div class="pL30 pT10 bold">
                                        <span class="riskLabelField2">Date of Blood Draw</span>
                                        <span class="riskValueField3"><form:input   path="stdAirsDoc.recvBloodProdDateText" id="AIRS_RECV_BLOOD_PROD_DATE_TEXT" class="dPicker mask standardInputsmallerText" title="Blood Draw Date" datevalidations="partialDate" maxlength="10"/></span>
                                        <div class="v_error AIRS_RECV_BLOOD_PROD_DATE_TEXT "></div> 
                                </div>
                                
                                <div class="pL30 pT10">
                                        <form:checkbox id="INIT_HIV_DIAG1"  path="stdAirsDoc.initHivDiag"  class="otherFieldDependentCBox"  dependentField="AIRS_INIT_HIV_DIAG_DATE_TEXT" value="1"/> Initial/new HIV diagnosis
                                </div>
                                
                                <div class="pL50 pT10 bold">
                                        <span class="riskLabelField2">Date of 1st HIV diagnosis</span>
                                        <span class="riskValueField3"><form:input   path="stdAirsDoc.initHivDiagDateText" requiredField="Y" id="AIRS_INIT_HIV_DIAG_DATE_TEXT" class="dPicker mask standardInputsmallerText" title="HIV diagnosis Date" datevalidations="partialDate"  maxlength="10"/></span>
                                        <div class="v_error AIRS_INIT_HIV_DIAG_DATE_TEXT "></div>
                                </div>
                                
                                 <div class="pL30">
                                 		<form:checkbox id="INFORMED_HIV_POS1"  path="stdAirsDoc.informedHivPos"  class="otherFieldDependentCBox"  dependentField="AIRS_INFORMED_HIV_DATE_TEXT" value="1"/> Has this patient been informed of his/her HIV positive status?
                                </div>
                                
                                <div class="clearBoth pL50 pT10 bold">
                                        <span class="riskLabelField2">Date</span>
                                        <span class="riskValueField4"><form:input   path="stdAirsDoc.informedHivDateText" requiredField="Y" id="AIRS_INFORMED_HIV_DATE_TEXT" class="dPicker mask standardInputsmallerText" title="Informed Date" datevalidations="partialDate" maxlength="10"/></span>
                                        <div class="v_error AIRS_INFORMED_HIV_DATE_TEXT "></div>
                                </div>
                                
                                <div class="pL30 pT10">
                                        <form:checkbox id="PREV_HIV_DIAG1"  path="stdAirsDoc.prevHivDiag" value="1"   class="otherFieldDependentCBox"  dependentField="AIRS_PREV_HIV_DIAG_DATE_TEXT"/> Previously diagnosis HIV infection (non-AIDS)  
                                </div> 
                                                               
                                <div class="pL50 pT10 bold">
                                        
                                            <span class="riskLabelField2">Date of diagnosis</span>
                                            <span class="riskValueField3"><form:input   path="stdAirsDoc.prevHivDiagDateText" requiredField="Y" id="AIRS_PREV_HIV_DIAG_DATE_TEXT" class="dPicker mask standardInputsmallerText" title="Previous HIV Diagnosis Date" datevalidations="partialDate" maxlength="10"/></span>
                                            <div class="v_error AIRS_PREV_HIV_DIAG_DATE_TEXT "></div>
                                </div>   
                                <div class="pL30">
                                        <form:checkbox id="INIT_AIDS_DIAG1"  path="stdAirsDoc.initAidsDiag" value="1"   class="otherFieldDependentCBox"  dependentField="AIRS_INIT_AIDS_DIAG_DATE_TEXT"/> Initial/new AIDS diagnosis        
                                </div>
                                <div class="pL50 pT10 bold">
                                       
                                                <span class="riskLabelField2">Date of diagnosis</span>
                                                <span class="riskValueField3"><form:input   path="stdAirsDoc.initAidsDiagDateText" requiredField="Y"  id="AIRS_INIT_AIDS_DIAG_DATE_TEXT" class="dPicker mask standardInputsmallerText" title="AIDS Diagnosis Date" datevalidations="partialDate" maxlength="10"/></span>
                                                <div class="v_error AIRS_INIT_AIDS_DIAG_DATE_TEXT "></div>
                                </div>   
                        </div>

                        <div id="personHIVDetailsRight2">        
                        		<div class="pL50">
                               			<div class="mT10 container bold">
                                                <span style="width:18%;float:left;">Date of patient interview</span>
                                                <span class="riskValueField3"><fmt:formatDate value="${stdAIRSDocVO.stdAirsDoc.ptInterviewDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedptInterviewDate" ></fmt:formatDate>
                                                <form:input  path="stdAirsDoc.ptInterviewDate" id="AIRS_PT_INTERVIEW_DATE" value="${theFormattedptInterviewDate}" title="patient Interview Date" class="dPicker mask standardInputsmallerText" datevalidations="completeDate" maxlength="10"/> </span>
                                                <div class="v_error AIRS_PT_INTERVIEW_DATE "></div>
                                        </div>
                                        <div class="mT10 container bold">
                                                <span class="riskLabelField1">Date of first POSITIVE HIV test (enter date of current test if it is first) </span>
                                                <span class="riskValueField3"><fmt:formatDate value="${stdAIRSDocVO.stdAirsDoc.firstPosHivDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedfirstPosHivDate" ></fmt:formatDate>
                                                <form:input  path="stdAirsDoc.firstPosHivDate" id="AIRS_FIRST_POS_HIV_DATE" value="${theFormattedfirstPosHivDate}" class="dPicker mask standardInputsmallerText" title="First Positive HIV Date" datevalidations="completeDate" maxlength="10"/> </span>
                                                <div class="v_error AIRS_FIRST_POS_HIV_DATE "></div>
                                        </div>
                                        
                                        <div class="mT10" style="clear: both">
                                                <span class="riskLabelField1 bold">Has patient taken any antiretrovirals in the past six months? </span>
                                                <div class="riskValueField3 pB5"> 
                                                        <span> <form:radiobutton id="ARV_6_MO1"  path="stdAirsDoc.arv6Mo" value="Y" class="otherFieldDependentRadio" dependentField="AIRS_ARV_6_MO_NAME,AIRS_ARV_6_MO_NAME2,AIRS_ARV_6_MO_NAME3,AIRS_ARV_6_MO_NAME4,AIRS_ARV_6_MO_START_DATE_TEXT,AIRS_ARV_6_MO_END_DATE_TEXT"/> Yes </span>
                                                        <span> <form:radiobutton id="ARV_6_MO2"  path="stdAirsDoc.arv6Mo" value="N" class="otherFieldDependentRadio" dependentField="AIRS_ARV_6_MO_NAME,AIRS_ARV_6_MO_NAME2,AIRS_ARV_6_MO_NAME3,AIRS_ARV_6_MO_NAME4,AIRS_ARV_6_MO_START_DATE_TEXT,AIRS_ARV_6_MO_END_DATE_TEXT"/> No </span>
                                                        <span> <form:radiobutton id="ARV_6_MO3"  path="stdAirsDoc.arv6Mo" value="R" class="otherFieldDependentRadio" dependentField="AIRS_ARV_6_MO_NAME,AIRS_ARV_6_MO_NAME2,AIRS_ARV_6_MO_NAME3,AIRS_ARV_6_MO_NAME4,AIRS_ARV_6_MO_START_DATE_TEXT,AIRS_ARV_6_MO_END_DATE_TEXT"/> Refused</span>
                                                        <span> <form:radiobutton id="ARV_6_MO4"  path="stdAirsDoc.arv6Mo" value="U" class="otherFieldDependentRadio" dependentField="AIRS_ARV_6_MO_NAME,AIRS_ARV_6_MO_NAME2,AIRS_ARV_6_MO_NAME3,AIRS_ARV_6_MO_NAME4,AIRS_ARV_6_MO_START_DATE_TEXT,AIRS_ARV_6_MO_END_DATE_TEXT"/> Unknown</span>
                                                </div>  
                                                <div style="clear:both"></div>
                                                <span class="fieldHolder15  pB10 pL50 bold"> if Yes, which</span>
                                                 <div class="fieldHolder75 pB5"> 
	                                                <span class="fieldHolder25 pB5"><form:input  path="stdAirsDoc.arv6MoName" id="AIRS_ARV_6_MO_NAME" class="standardInputSmallText2" maxlength="40"/></span>
	                                                <span class="fieldHolder25 pB5"><form:input  path="stdAirsDoc.arv6MoName2" id="AIRS_ARV_6_MO_NAME2" class="standardInputSmallText2" maxlength="40" /></span>
	                                                <span class="fieldHolder25 pB5"><form:input  path="stdAirsDoc.arv6MoName3" id="AIRS_ARV_6_MO_NAME3" class="standardInputSmallText2" maxlength="40" /></span>
	                                                <span class="fieldHolder25 pB5"><form:input  path="stdAirsDoc.arv6MoName4" id="AIRS_ARV_6_MO_NAME4" class="standardInputSmallText2" maxlength="40" /></span>
	                                             </div>
                                                 <div style="clear:both"></div>
                                                 
                                                 <div class="bold">
	                                                 <div>
	                                                	<span class="fieldHolder15  pB10 pL50"> if Yes, start date</span> 
	                                                	<span class="fieldHolder15 pB5"><form:input  path="stdAirsDoc.arv6MoStartDateText" id="AIRS_ARV_6_MO_START_DATE_TEXT" class="standardInputsmallerText dPicker mask" title="Antiretrovirals Start Date" datevalidations="partialDate" maxlength="10" /></span>
	                                                	<span class="fieldHolder10  pB10 pL50"> End date</span>
	                                                	<span class="fieldHolder15 pB5"><form:input  path="stdAirsDoc.arv6MoEndDateText" id="AIRS_ARV_6_MO_END_DATE_TEXT" class="standardInputsmallerText dPicker mask" title="Antiretrovirals End Date" datevalidations="partialDate" maxlength="10"/></span>
	                                                	
	                                                </div>
	                                                
	                                                 <div style="clear:both"></div>
	                                                 <div class="fieldHolder75 pB5">
	                                                 	<span class="pL50 fieldHolder45 v_error AIRS_ARV_6_MO_START_DATE_TEXT "></span>	
			                                            <span class="pL15 v_error AIRS_ARV_6_MO_END_DATE_TEXT "></span>
	                                                </div>
                                                </div>
                                                
                                                 <div style="clear:both"></div>
                                                 
                                                 <div>
                                                	<span class="riskLabelField1 pB5 bold"> Was the patient taking any antiretrovirals at the time of the current HIV test?</span> 
	                                                <div class="riskValueField3 pB10"> 
	                                                        <span> <form:radiobutton id="ARV_CURRENT_TEST1"  path="stdAirsDoc.arvCurrentTest" value="Y" /> Yes </span>
	                                                        <span> <form:radiobutton id="ARV_CURRENT_TEST2"  path="stdAirsDoc.arvCurrentTest" value="N" /> No </span>
	                                                        <span> <form:radiobutton id="ARV_CURRENT_TEST3"  path="stdAirsDoc.arvCurrentTest" value="R" /> Refused </span>
	                                                        <span> <form:radiobutton id="ARV_CURRENT_TEST4"  path="stdAirsDoc.arvCurrentTest" value="U" /> Unknown </span>
	                                                </div>
	                                              </div>  
                                        </div>
										 <div style="clear:both"></div>
                                        <div class="bold">
                                                <span class="riskLabelField1">Date of first HIV test ever (negative, indeterminate, or positive) </span>
                                                <span class="riskValueField3"> <form:input  path="stdAirsDoc.firstHivTestEverDateText" id="AIRS_FIRST_HIV_TEST_EVER_DATE_TEXT" class="dPicker mask standardInputsmallerText" title="First HIV test Date"  datevalidations="partialDate" maxlength="10"/> </span>
                                                <div class="v_error AIRS_FIRST_HIV_TEST_EVER_DATE_TEXT "></div>
                                        </div>   
                                         <div style="clear:both"></div>
                                        <div class="mT20">
                                                <span class="labelHIV bold">How many times did patient test for HIV in the 2 years before the FIRST positive test? (including the test on date under b.)</span>
                                                <div class="valueHIV"> 
                                                        <span> <form:input id="NUMBER_TEST_2_YRS"  path="stdAirsDoc.numberTest2Yrs" class="standardInputTinyText" maxlength="3"/>&nbsp;&nbsp;times</span> 
                                                </div>
                                        </div> 
                                         <div style="clear:both"></div>
                                        <div class="mT10">
                                                <span class="riskLabelField4 bold">Has patient ever tested negative for HIV before? </span>
                                                <div class="riskValueField3"> 
                                                        <span> <form:radiobutton id="EVER_TEST_NEG1"  path="stdAirsDoc.everTestNeg" value="Y" class="otherFieldDependentRadio" dependentField="AIRS_LAST_NEG_DATE_TEXT"  /> Yes </span>
                                                        <span> <form:radiobutton id="EVER_TEST_NEG2"  path="stdAirsDoc.everTestNeg" value="N" class="otherFieldDependentRadio" dependentField="AIRS_LAST_NEG_DATE_TEXT" /> No  </span>  
                                                        <span> <form:radiobutton id="EVER_TEST_NEG3"  path="stdAirsDoc.everTestNeg" value="R" class="otherFieldDependentRadio" dependentField="AIRS_LAST_NEG_DATE_TEXT"  /> Refused </span>
                                                        <span> <form:radiobutton id="EVER_TEST_NEG4"  path="stdAirsDoc.everTestNeg" value="U" class="otherFieldDependentRadio" dependentField="AIRS_LAST_NEG_DATE_TEXT"  /> Unknown </span> 
                                                </div>
                                        </div>
                                         <div style="clear:both"></div>
                                        <div class="mT10 bold" >
                                                <span style="width:20%;float:left;"> Date of last NEGATIVE HIV test</span>
                                                <span class="riskValueField3"><form:input  path="stdAirsDoc.lastNegDateText" id="AIRS_LAST_NEG_DATE_TEXT" requiredField="Y" class="standardInputsmallerText dPicker mask" title="Last Negative HIV Test Date" datevalidations="partialDate" maxlength="10"/></span><br>
                                                <div class="v_error AIRS_LAST_NEG_DATE_TEXT "></div>                                       
                                               
                                        </div>   
                              </div>  
                                         
                        </div>  
                     </c:if>
                     <c:if test="${stdAIRSDocVO.airsVerType == 'NEW'}">
                         <div id="personHIVDetailsLeft2" class="">
                        	   <!--  Report details -->
                                 
                                <div class="pL30 pT10">
                                        <form:checkbox id="INIT_HIV_DIAG2"  path="stdAirsDoc.initHivDiag"  class="otherFieldDependentCBox"  dependentField="AIRS_INIT_HIV_DIAG_DATE_TEXT" value="1"/> Initial/new HIV diagnosis
                                </div>
                                
                                <div class="pL50 pT10 bold">
                                        <span class="riskLabelField2">Date of 1st HIV diagnosis</span>
                                        <span class="riskValueField3"><form:input   path="stdAirsDoc.initHivDiagDateText" requiredField="Y" id="AIRS_INIT_HIV_DIAG_DATE_TEXT" class="dPicker mask standardInputsmallerText" title="HIV diagnosis Date" datevalidations="partialDate"  maxlength="10"/></span>
                                        <div class="v_error AIRS_INIT_HIV_DIAG_DATE_TEXT "></div>
                                </div>
                                
                                <div class="pL30 pT10">
                                        <form:checkbox id="PREV_HIV_DIAG2"  path="stdAirsDoc.prevHivDiag" value="1"   class="otherFieldDependentCBox"  dependentField="AIRS_PREV_HIV_DIAG_DATE_TEXT"/> Previously diagnosis HIV infection (non-AIDS)  
                                </div> 
                                                               
                                <div class="pL50 pT10 bold">
                                        
                                            <span class="riskLabelField2">Date of diagnosis</span>
                                            <span class="riskValueField3"><form:input   path="stdAirsDoc.prevHivDiagDateText" requiredField="Y" id="AIRS_PREV_HIV_DIAG_DATE_TEXT" class="dPicker mask standardInputsmallerText" title="Previous HIV Diagnosis Date" datevalidations="partialDate" maxlength="10"/></span>
                                            <div class="v_error AIRS_PREV_HIV_DIAG_DATE_TEXT "></div>
                                </div>   
                                <div class="pL30">
                                        <form:checkbox id="INIT_AIDS_DIAG2"  path="stdAirsDoc.initAidsDiag" value="1"   class="otherFieldDependentCBox"  dependentField="AIRS_INIT_AIDS_DIAG_DATE_TEXT"/> Initial/new AIDS diagnosis        
                                </div>
                                <div class="pL50 pT10 bold">
                                       
                                                <span class="riskLabelField2">Date of diagnosis</span>
                                                <span class="riskValueField3"><form:input   path="stdAirsDoc.initAidsDiagDateText" requiredField="Y"  id="AIRS_INIT_AIDS_DIAG_DATE_TEXT" class="dPicker mask standardInputsmallerText" title="AIDS Diagnosis Date" datevalidations="partialDate" maxlength="10"/></span>
                                                <div class="v_error AIRS_INIT_AIDS_DIAG_DATE_TEXT "></div>
                                </div>   
                        </div>

                        <div id="personHIVDetailsRight2">        
		                        <div class="pL50 pT10">
		                        
		                        <div class="fieldHolder40 bold">
	                                            <span class="fieldHolder40">Date of Admission</span> 
                                        		<span class=""><form:input   path="stdAirsDoc.admissionDateText" id="AIRS_ADMISSION_DATE_TEXT" class="dPicker mask standardInputsmallerText"  title="Admission Date" datevalidations="partialDate" maxlength="10"/></span>
                                        		<div class="v_error AIRS_ADMISSION_DATE_TEXT "></div> 	                                            
	                            </div>
	                            
		                        <div class="fieldHolder40 bold">
	                                            <span class="fieldHolder40">Date of Blood Draw</span> 
		                                        <span class="riskValueField3"><form:input   path="stdAirsDoc.recvBloodProdDateText" id="AIRS_RECV_BLOOD_PROD_DATE_TEXT" class="dPicker mask standardInputsmallerText" title="Blood Draw Date" datevalidations="partialDate" maxlength="10"/></span>
		                                        <div class="v_error AIRS_RECV_BLOOD_PROD_DATE_TEXT "></div>                                            
	                            </div>
                             	<br>
		                        
		                                <div class="mT10">
		                                 		<form:checkbox id="INFORMED_HIV_POS2"  path="stdAirsDoc.informedHivPos"  class="otherFieldDependentCBox"  dependentField="AIRS_INFORMED_HIV_DATE_TEXT" value="1"/> Has this patient been informed of his/her HIV positive status?
		                                </div>
		                                
		                                <div class="clearBoth pT10 bold">
		                                        <span class="fieldHolder10">Date</span>
		                                        <span class="riskValueField4"><form:input   path="stdAirsDoc.informedHivDateText" requiredField="Y" id="AIRS_INFORMED_HIV_DATE_TEXT" class="dPicker mask standardInputsmallerText" title="Informed Date" datevalidations="partialDate" maxlength="10"/></span>
		                                        <div class="v_error AIRS_INFORMED_HIV_DATE_TEXT "></div>
		                                </div>
		                                
                        		
                                        <div class="mT10 container bold">
                                                <span class="riskLabelField1">Date of first POSITIVE HIV test (enter date of current test if it is first) </span>
                                                <span class="riskValueField3"><fmt:formatDate value="${stdAIRSDocVO.stdAirsDoc.firstPosHivDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedfirstPosHivDate" ></fmt:formatDate>
                                                <form:input  path="stdAirsDoc.firstPosHivDate" id="AIRS_FIRST_POS_HIV_DATE" value="${theFormattedfirstPosHivDate}" class="dPicker mask standardInputsmallerText" title="First Positive HIV Date" datevalidations="completeDate" maxlength="10"/> </span>
                                                <div class="v_error AIRS_FIRST_POS_HIV_DATE "></div>
                                        </div>
                                        
                                        <div class="mT10" style="clear: both">
                                                <span class="riskLabelField1 bold">Has patient taken any antiretrovirals in the past six months? </span>
                                                <div class="riskValueField3 pB5"> 
                                                        <span> <form:radiobutton id="ARV_6_MO5"  path="stdAirsDoc.arv6Mo" value="Y" class="otherFieldDependentRadio" dependentField="AIRS_ARV_6_MO_NAME,AIRS_ARV_6_MO_NAME2,AIRS_ARV_6_MO_NAME3,AIRS_ARV_6_MO_NAME4,AIRS_ARV_6_MO_START_DATE_TEXT,AIRS_ARV_6_MO_END_DATE_TEXT"/> Yes </span>
                                                        <span> <form:radiobutton id="ARV_6_MO6"  path="stdAirsDoc.arv6Mo" value="N" class="otherFieldDependentRadio" dependentField="AIRS_ARV_6_MO_NAME,AIRS_ARV_6_MO_NAME2,AIRS_ARV_6_MO_NAME3,AIRS_ARV_6_MO_NAME4,AIRS_ARV_6_MO_START_DATE_TEXT,AIRS_ARV_6_MO_END_DATE_TEXT"/> No </span>
                                                        <span> <form:radiobutton id="ARV_6_MO7"  path="stdAirsDoc.arv6Mo" value="R" class="otherFieldDependentRadio" dependentField="AIRS_ARV_6_MO_NAME,AIRS_ARV_6_MO_NAME2,AIRS_ARV_6_MO_NAME3,AIRS_ARV_6_MO_NAME4,AIRS_ARV_6_MO_START_DATE_TEXT,AIRS_ARV_6_MO_END_DATE_TEXT"/> Refused</span>
                                                        <span> <form:radiobutton id="ARV_6_MO8"  path="stdAirsDoc.arv6Mo" value="U" class="otherFieldDependentRadio" dependentField="AIRS_ARV_6_MO_NAME,AIRS_ARV_6_MO_NAME2,AIRS_ARV_6_MO_NAME3,AIRS_ARV_6_MO_NAME4,AIRS_ARV_6_MO_START_DATE_TEXT,AIRS_ARV_6_MO_END_DATE_TEXT"/> Unknown</span>
                                                </div>  
                                        </div>
                              </div>  
                                         
                        </div>  
                     </c:if>                     
                </div>

                <!--  End of HIV Testing History Information -->        
                <!--  Provider details  -->     
                <div id="personHIVDetails" class="container facilityDetails bold">   
                        <div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Facility Information</span>                                  
                        </div>
                        <!--  Provider details  -->
                        <div class="pL10 pT10">
                                <span class="">
                                        <span class="pL10">Name</span>
                                        <span class="pL10"><form:input id="FACILITY_NAME"  path="stdAirsDoc.facilityName" class="standardInputText facilityFields" title="Facility Name" maxlength="80"/></span> 
                                </span>    
                               
                                <span class="pL30">Type</span>
                                <span class="pL10"><form:input id="PROVIDER_DESCRIPTION"  path="stdAirsDoc.providerDescription" class="standardInputText" title="Facility Type" maxLength="60"/>&nbsp;&nbsp;</span>
                       
                                <span class="pL30">Agency ID </span>
                                <span class="pL10"> 
                                        <form:input id="AGENCY_ID"  path="stdAirsDoc.agencyId" class="standardInputSmallText readonly" title="Agency Id" maxLength="9"/>
                                </span>
                                     
                        </div><br>
                        
                        <div class="b1" style="margin: 15px 5px 10px 0px">
			 	            <div class="personInfo pTRBL10" id="airsDocumentFacilityAddressDtls">
			 	                <span class="personLabelDoc"> Address </span>
		 		                <span> 	                	
			 	                	<span class="pL10"> <form:input id="FACILITY_ADDRESS"  path="stdAirsDoc.facilityAddress" class="standardInputText facilityFields newAddress widgetAddressFieldsReadonly" title="Address" maxLength="62"/>  </span>	 	        
			 	                	            	 	                    
			 	                    <span class="pL20">City</span>
			 	                    <span class="pL10"><form:input id="FACILITY_CITY"  path="stdAirsDoc.facilityCity" class="standardInputSmallText facilityFields widgetAddressFieldsReadonly newCity" title="City" maxLength="30"/></span>
			 	                    
			 	                    <span class="pL20">State </span>
			 	                    <span  class="pL10">
										<form:select id="FACILITY_STATE"  path="stdAirsDoc.facilityState"  class="standardInputSmallText1 facilityFields widgetAddressFieldsReadonly newState" title="State" maxLength="2">
													<form:option value=""></form:option>
													<form:options items="${sessionScope.stateMap}"/>
										</form:select>
									</span>
			 	                    
			 	                    <span class="pL20">Zip </span>
			 	                    <span class="pL10"> 
			 	                        <form:input id="FACILITY_ZIP"  path="stdAirsDoc.facilityZip" class="standardInputSmallText1 facilityFields widgetAddressFieldsReadonly newZip" title="Zipcode" maxLength="5"/> - <form:input id="FACILITY_PLUS4"  path="stdAirsDoc.facilityPlus4" class="nOnly standardInputSmallText1 facilityFields" />
			 	                    </span> 
			 	                    
			 	                    <span class="pL20">County </span>
			 	                    <span class="pL10"> 
			 	                    	<form:select id="FACILITY_COUNTY"  path="stdAirsDoc.facilityCounty"  cssClass="oAdrDlg newCounty roundedBrdr facilityFields widgetAddressFieldsReadonly newCounty" titl="County" > 
											<form:option value=""></form:option>
											<form:options items="${sessionScope.countyMap}"/>
										 </form:select>
			 	                    </span>     	                    
			 	                    <br>
			 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
			 	                </span>
			 	            </div>
			 	            <div class="personInfo pT10 pTRBL10">
			 	            	<span  class="nonRawEditFields" style="padding-left:59px"><form:input id="PROV_ADDR"  path="provAddr" class="standardInputText readonly" title="Address" maxLength="62"/></span>
			 	              	
			 	              	<span class="pL30">Phone </span>
			 	                    <span class="pL10"> 
			 	                        <form:input id="FACILITY_PHONE"  path="stdAirsDoc.facilityPhone" class="pNumber standardInputSmallText2 facilityFields" maxlength="20" title="Phone"/>
			 	                    </span>  	              	
			 	                <span style="float: right;" class="mr5">
			 	                	<a href="javascript:openAddressWidget('airsDocumentFacilityAddressDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
								 </span>
			 	                <br>               
			 	                <span class="nonRawEditFields"><span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span></span>
			 	                    
			 	            </div>
			 	        </div>
                </div>           
        </div>
        <form:hidden id="FED_ID"  path="stdAirsDoc.fedId"/>
        <form:hidden id="DUNS_ID"  path="stdAirsDoc.dunsId"/>
		<form:hidden id="AIRS_CALC_FILE_DATE"  path="stdAirsDoc.airsCalcFileDate"/>
		<form:hidden id="EXTRALOGID"  path="stdAirsDoc.extralogid"/>										
		<form:hidden id="AIRS_VERSION"  path="stdAirsDoc.airsVersion"/>
        
        <div class="clearBoth divHeadingCls" id="loadAIDADataDiv" style="background:#DDDDDD; "><span class="ui-icon ui-icon-plus"  onclick='loadAIDAData("${stdAIRSDocVO.stdAirsDoc.tcId}", this)'></span>Load AIDA Data</div>
        
        
        
        <div id="aidaDataContent" class="personDetailsDiv" style="display: none;">
        
        </div>
        
        
        
        
        
        
        <div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
		 <div id="button-row" class="button-row documentButtons center">
		 				
		 					<c:if  test="${userPermissions['99'] != null && stdAIRSDocVO.stateno != null && stdAIRSDocVO.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null}" >	
								<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3 displayNone"  value="Trigger Assignment"  onclick="loadSurvAssignmentPopUp()"/>
							</c:if>
						<c:if test="${param.hasEditFunctionality == true}">
							<input type="button" id="eButton"  class="editBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="customDocumentEditViewerStyles()" />
						</c:if>
							<c:choose>
								<c:when  test="${param.hasDeleteFunctionality == true}" >	
										<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
										<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
								</c:when>
								<c:when test="${stdAIRSDocVO.stateno != null && stdAIRSDocVO.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null}">
										<c:if test="${userPermissions['90'] != null}" >
											<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
										</c:if>
										<c:if test="${userPermissions['100'] != null}" >
											<input type="button" id="editDocument"  class="editDocumentBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="confirmEditDocumentFromStateno()" />
										</c:if>
								</c:when>
							</c:choose>
						
					<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Save" onclick="saveForm()"/>	 
					<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />				
		</div>		
		
        <br>
        <form:hidden  path="documentSourceTable" id="DOCUMENT_SOURCE_TABLE"/>
        <input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />          
</form:form>


