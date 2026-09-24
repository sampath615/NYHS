<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>
 <script src="${staticPath}/js/docs/labDoc.js?v=${buildDate}"></script>
 	
<form:form modelAttribute="stdLabDocVO" id="stdLabDocVO" cssClass="documentFormVO documentDescription">

<form:errors path="*" cssClass="errorblock" element="div" />
	<form:hidden path="DOCUMENT_ID"/>
	<form:hidden path="INTERPRET_CD" id="HIV_INTERPRET_CD"/>
	<div id="labDocTab" style="background-color: transparent">
		<div style="padding-left:43%; " class="headingCls">
				<c:choose>
					<c:when test="${param.documentType == 'STD_LAB'}">
						STD &nbsp;  DOCUMENT
					</c:when>
					<c:otherwise>
						LAB &nbsp;  DOCUMENT
					</c:otherwise>
				</c:choose>
		</div>
		
		<div id="labEditTransactionMessage" class="transactionMessage"><span class="message"></span></div>
		
		<div class="pB30 pT20 bold">
		
					
					<c:if test="${stdLabDocVO.PARENT_DOCUMENT_ID != null &&  stdLabDocVO.PARENT_DOCUMENT_ID != 0}">
		
				<div  class="floatLeft width70Perc" id="parentDocumentIdSection">
				            <span class="parentIdSectionLabel bold mL10">Parent DocumentId: </span>
							<span class="parentIdSectionValue "><a href="#" onclick="loadDocumentDlg(${stdLabDocVO.PARENT_DOCUMENT_ID}, '${param.documentType}')">${stdLabDocVO.PARENT_DOCUMENT_ID}</a></span>
							
				         </div>	
			         </c:if>
			         
					<div  class="floatRight">
			            <span class="mL10 bold">Document ID: </span>
						<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
						<form:hidden path="PFI" id="mshLabPfi"/>
			         </div>	
		
		</div>

		
		
		    
		
		 <div class="clearBoth container pB5 w100 bold">
            <div style="float:left; padding-bottom:10px; width: 40%">
	            <div >
	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
                    <span class="ml10"><form:input path="STATENO" class="standardInputSmallText readonly" style="width:180px" maxlength="10" readonly="true"/> </span>
	            </div>    
            </div>
            <div style="float:left; padding-bottom:10px; width: 30%">
            	<div>
	                <span class="ml10"> eHARS Import Date: </span>
                    <span class="ml10" >
                    	<fmt:formatDate value="${stdLabDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
	                	<form:input path="EHARS_IMPORT_DATE" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/>
                    </span>
	            </div>    
            </div>
            <div style="float:left; padding-bottom:10px; width: 30%; text-align: right">
            	        	
	            <div >
	                <span  class="ml10">NYC Ship Date:</span>
	                
	                <fmt:formatDate value="${stdLabDocVO.NYC_SHIP_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedNYC_SHIP_DATE_TEXT" ></fmt:formatDate>
					<span class="ml10"><form:input path="NYC_SHIP_DATE" value="${theFormattedNYC_SHIP_DATE_TEXT}" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
	            </div>
            </div>     
   		</div>
   		
		<div class="bold"><jsp:include page="statenoDetails.jsp"></jsp:include></div>                  		
   		
				
		<!--  Person details -->
		
		<div class="container bold" style="border:1px dotted; width:100%">	 
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Person Details</span></div>
				
				
				<div class="personInfo">
                			<div class="fieldHolder25">
                             <span class="pL10">First Name </span> 
                             <span class="pL5"><form:input path="FNAME" id="HIV_LAB_FNAME" class="standardInputMedText required" maxlength="48" title="First Name"/></span>
                             <div class="pL10 v_error HIV_LAB_FNAME "></div> 
                         </div>
                         <div class="fieldHolder25">
                            	<span class="pL10">Middle Name </span> 
                            	<span  class="pL5"><form:input path="MNAME" id="HIV_LAB_MNAME" class="standardInputMedText" maxlength="48" title="Middle Name"/></span>
                            	<div class="pL10 v_error HIV_LAB_MNAME "></div>
                            </div>
                         <div class="fieldHolder20">
                            	<span class="pL10">Last Name </span> 
                            	<span  class="pL5"><form:input path="LNAME" id="HIV_LAB_LNAME" class="required standardInputMedText" maxlength="48" title="Last Name"/></span>
                            	<div class="pL10 v_error HIV_LAB_LNAME "></div>
                            </div>
                         <div class="fieldHolder25">
                      			<span class="pL10">Alt Name </span> 
                  				<span class="pL5"><form:input path="SUFFIX" id="HIV_LAB_SUFFIX" class="standardInputMedText" maxlength="48" title="Suffix"/></span>
                  				<div class="pL10 v_error HIV_LAB_SUFFIX "></div>
                  			</div>  
                        <br><br>
                        
                        
                </div>
                
                
                <div class="clearBoth personInfo">
                			<div class="fieldHolder25">
                             <span class="pL10"><spring:message code="label.ssn" /> </span> 
                             <span class="pL5"><form:input id="HIV_LAB_SSN"  path="SSN" class=" standardInputSmallText" maxlength="9" title="SSN"/></span>
                             <div class="pL10 v_error HIV_LAB_SSN "></div> 
                         </div>
                         <div class="fieldHolder25">
                            	<span class="pL10"><spring:message code="label.birthdate" /></span> 
                            	<span  class="pL5"><form:input path="DOB_TEXT" id="DATE_OF_BIRTH"  class="editable dPicker standardInputSmallText3" datevalidations="partialDate" maxlength="10" title="Date of Birth"/> </span>
                            	<div class="pL10 v_error HIV_LAB_DOB_TEXT "></div>
                            </div>
                         <div class="fieldHolder20">
                            	<span class="pL10"><spring:message code="label.age" /> </span> 
                            	<span  class="pL5"><form:input path="AGE" id="HIV_LAB_AGE" class="nOnly standardInputSmallText2" maxlength="3" title="AGE"/></span>
                            	<div class="pL10 v_error HIV_LAB_AGE "></div>
                            </div>
                         <div class="fieldHolder30">
                      			<span class="pL10">Birth Country </span> 
                  				<span class="pL5">
                  					<form:select path="BIRTH_COUNTRY" id="HIV_LAB_BIRTH_COUNTRY" Class="roundedBrdr" title="Birth Country">
										<form:option value=""></form:option>
										<form:option value="${stdLabDocVO.BIRTH_COUNTRY}">${stdLabDocVO.BIRTH_COUNTRY}</form:option>
										<form:options items="${sessionScope.countryMap}"/>
										<form:option value="OTH">Other</form:option>
										<form:option value="UNK">Unknown</form:option>
									</form:select>
                  				</span>
                  				<div class="pL10 v_error HIV_LAB_BIRTH_COUNTRY "></div>
                  			</div>  
                        <br><br>
                        
                        
                </div>
				
			
		    <div style="float:left; width:99%; margin-left:5px; margin-right:5px; margin-bottom:10px" class="mT5">
			
			<div class="b1" style="margin: 15px 5px 5px 5px" id="labDocumentPrsnAddressDtls">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"><form:input path="PERSON_ADDRESS" id="HIV_LAB_PERSON_ADDRESS" class="standardInputText widgetAddressFieldsReadonly newAddress" maxlength="100" title="Address"/>  </span>
	 	                    
	 	                    
	 	                    <span class="pL20">City</span>
	 	                    <span class="pL10"> <form:input path="PERSON_CITY" id="HIV_LAB_PERSON_CITY"  class="oAdrDlg standardInputSmallText newCity widgetAddressFieldsReadonly" maxlength="50" title="City"/></span>
	 	                    
	 	                    <span class="pL20">State </span>
	 	                    <span  class="pL10">
								<form:input path="PERSON_STATE" id="HIV_LAB_PERSON_STATE"   cssClass="oAdrDlg roundedBrdr newState standardInputVerySmall widgetAddressFieldsReadonly" maxlength="2" title="State"/> 
							</span>
	 	                    
	 	                    <span class="pL20">Zip </span>
	 	                    <span class="pL10"> 
	 	                        <form:input path="PERSON_ZIP" id="HIV_LAB_PERSON_ZIP"  class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5" title="Zip code"/> - 
								<form:input path="PERSON_PLUS4" id="HIV_LAB_PERSON_PLUS4"  class="nOnly standardInputSmallText1" maxlength="4" title="Plus4"/>
	 	                    </span>
	 	                    <span class="pL20">County </span>
	 		                   <span class="pL10"> 
									<form:select path="PERSON_COUNTY" id="HIV_LAB_PERSON_COUNTY"   cssClass="oAdrDlg roundedBrdr newCounty widgetAddressFieldsReadonly" title="County"> 
										<form:option value=""></form:option>
										<form:options items="${sessionScope.countyMap}"/>
									</form:select>
		 		               </span>       	                    
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10">
 	            	<span  class="" style="padding-left:59px"><form:input path="RAWPERSONADDRESS" class="standardInputText newAddress readonly" title="Raw Person Address"/></span>
 	               	                	                 
 	                 <span class="pL20"> Phone</span>
 					 <span class="pL10"> <form:input path="PERSON_PHONE" id="HIV_LAB_PERSON_PHONE"  class="pNumber standardInputSmallText phoneInputText" maxlength="20" title="Phone"/></span>
 					 
 					 <span class="pL20">  <spring:message code="label.country" /></span>
 					 <span class="pL10"> <form:input path="PERSON_COUNTRY" id="HIV_LAB_PERSON_COUNTRY"  class="standardInputSmallText newCountry" maxlength="106" title="Country"/></span>
 					 
 					   <span style="float: right;" class="mr50">
							<a href="javascript:openAddressWidget('labDocumentPrsnAddressDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
						 </span>  
 					<br>               
 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
 	                    
 	            </div>
 	        </div>					
			
		</div>		  
		</div>
		
			<!--  Race details -->
			
		<div class="container" style="border:1px dotted; padding-bottom:5px;">
			<div style="float:left; width:25%; margin-left:20px; border-right:1px dotted; height: 156px">				
				<div class= "bold">
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
				<div>
					<form:checkbox path="RACE_PACIFIC" value="1"/> Pacific Islander
				</div>
				<div>
					<form:checkbox path="RACE_INDIAN" value="1" /> American Indian/Alaskan Native
				</div>
				<div>
					<form:checkbox path="RACE_OTHER" value="1"/> <spring:message code="label.raceOther" /> 
				</div>
				<div>
					<form:checkbox path="RACE_UNKNOWN" value="1"/> <spring:message code="label.raceUnknown" />
				</div>
			</div>
			<div style="float:left; width:35% ; ">
				<div style="padding:5px 0 5px 10px;">
					<div class="bold"><spring:message code="label.ethnicity" /> 
					(Hispanic or Latino) </div>
										<form:radiobutton path="ETHNICITY" value="01" /> <spring:message code="label.yes" /> <br>
									   <form:radiobutton path="ETHNICITY" value="02" /> <spring:message code="label.no" /><br>
									   <form:radiobutton path="ETHNICITY" value="09" /> <spring:message code="label.unknown" />
				</div>
				<div style="border-top: dotted 1px">
					<div style="padding:10px 0 0 10px;">	
						<div class="floatLeft pR10" style="border-right: dotted 1px;">
							<div class="bold"> Current Gender </div>
							<div  class="">
								<div> <form:radiobutton path="GENDER" value="M" class="editable"/> <spring:message code="label.male" /> </div>
								<div> <form:radiobutton path="GENDER" value="F" class="editable"/> <spring:message code="label.female" /> </div>
								<div> <form:radiobutton path="GENDER" value="U" class="editable"/>Unknown</div>		
							</div>
						</div>
						<div class="floatLeft pL10">
							<div class="bold"> Transgender </div>
							<div  class="">
								<div> <form:radiobutton path="GENDER" value="X" class="editable"/> MTF </div>		
								<div> <form:radiobutton path="GENDER" value="Y" class="editable"/> FTM </div>		
								<div> <form:radiobutton path="GENDER" value="T" class="editable"/> Unspecified </div>														
							</div>
						</div> 
 					</div>				
				</div>
			</div>
			
			<!--  Medical details -->
			
			<div style="float:left; width:35% ; padding-left:10px; padding-bottom: 12px; border-left:1px dotted;" class="bold">

								
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Medical Record Number
					</div>
					<div style="float:left">
						<form:input path="MED_RECORD_NUMBER" id="HIV_LAB_MED_RECORD_NUMBER"  class="standardInputMedText mL5" maxlength="25" title="Medical Record Number"/>
					</div>
					
					<div class="pL10 v_error HIV_LAB_MED_RECORD_NUMBER "></div>
				</div>			
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Insurance Number
					</div>
					<div style="float:left">
						<form:input path="INSURANCE_NUMBER" id="HIV_LAB_INSURANCE_NUMBER"  class="standardInputMedText mL5" maxlength="20" title="Insurance Number"/>
					</div>
				</div>	
				<div class="mT5 container">
					<div style="float:left; width:45%">
						CIN
					</div>
					<div style="float:left">
						<form:input path="MEDICAID_NUMBER" id="HIV_LAB_MEDICAID_NUMBER"  class="standardInputMedText mL5" maxlength="8" title="CIN"/>
					</div>
					
					<div class="pL10 v_error HIV_LAB_MEDICAID_NUMBER "></div>
				</div>
				<div class="mT5 container">
					<div style="float:left; width:45%">
						<spring:message code="label.din" />
					</div>
					<div style="float:left">
						<form:input path="DIN" id="HIV_LAB_DIN"  class="standardInputMedText mL5" maxlength="7" title="DIN"/>
					</div>
					
					<div class="pL10 v_error HIV_LAB_DIN "></div>
				</div>

				<div class="mT5 container">
					<div style="float:left; width:45%">
						<spring:message code="label.nysid" />
					</div>
					<div style="float:left">
						<form:input path="NYSID" id="HIV_LAB_NYSID"  class="standardInputMedText mL5" maxlength="9" title="NYSID"/>
					</div>
					
					<div class="pL10 v_error HIV_LAB_NYSID "></div>
				</div>				
			</div>
		</div>
		<div id="labDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
			<span class=" mL2">Lab Details</span>
		</div>
		
		<!--  Lab details -->
		
		<div style="padding-top:5px; padding-bottom:5px; border:1px dotted;" class="container bold">	 
			<div style="float:left; width:35%; padding-left:10px; ">
					
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Observation Result Key
					</div>
					<div style="float:left">
						<form:input path="LAB_SRC_ID" id="HIV_LAB_LAB_SRC_ID"  class="standardInputMedText mL5 readonly" title="Observation Result Key"/>
					</div>
				</div>	
				
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Collection Date
					</div>
					<div style="float:left">
						<fmt:formatDate value="${stdLabDocVO.COLLECTION_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedCOLLECTION_DATE_TEXT" ></fmt:formatDate>
						<form:input path="COLLECTION_DATE" id="HIV_LAB_COLLECTION_DATE"  value="${theFormattedCOLLECTION_DATE_TEXT}" class="editable dPicker standardInputMedText mL5" datevalidations="completeDate" maxlength="10" title="Collection Date"/>
					</div>
					
					<div class="pL10 v_error HIV_LAB_COLLECTION_DATE "></div>
				</div>

				<div class="mT5 container">
					<div style="float:left; width:45%">
						Observation Date
					</div>
					<div style="float:left">
						<fmt:formatDate value="${stdLabDocVO.OBSERVATION_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedOBSERVATION_DATE_TEXT" ></fmt:formatDate>
						<form:input path="OBSERVATION_DATE" id="HIV_LAB_OBSERVATION_DATE" value="${theFormattedOBSERVATION_DATE_TEXT}"  class="editable dPicker standardInputMedText mL5" datevalidations="completeDate" maxlength="10" title="Observation Date"/>
					</div>
					
					<div class="pL10 v_error HIV_LAB_OBSERVATION_DATE "></div>
				</div>

				<div class="mT5 container">
					<div style="float:left; width:45%">
						Result Reported Date
					</div>
					<div style="float:left">
						<fmt:formatDate value="${stdLabDocVO.RESULT_REPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedRESULT_REPORT_DATE_TEXT" ></fmt:formatDate>
						<form:input path="RESULT_REPORT_DATE" id="HIV_LAB_RESULT_REPORT_DATE" value="${theFormattedRESULT_REPORT_DATE_TEXT}"  class="editable dPicker standardInputMedText mL5" datevalidations="completeDate" maxlength="10" title="Result Reported Date"/>
					</div>
					
					<div class="pL10 v_error HIV_LAB_RESULT_REPORT_DATE "></div>
				</div>

				<div class="mT5 container">
					<div style="float:left; width:45%">
						Accession Number
					</div>
					<div style="float:left">
						<form:input path="ACCESSION_NUMBER" id="HIV_LAB_ACCESSION_NUMBER" class="standardInputMedText mL5" maxlength="23" title="Accession Number"/>
					</div>
				</div>	

				<div class="mT5 container">
					<div style="float:left; width:45%">
						HIV Vaccine Recipient
					</div>
					<div style="float:left">
						<form:input path="HIV_VACCINE_RECIP" id="HIV_LAB_HIV_VACCINE_RECIP" class="standardInputMedText mL5" maxlength="1" title="HIV Vaccine Recipient"/>
					</div>
				</div>	
			</div>
		
			<div style="float:right; width:63%; padding-bottom:5px; padding-left:10px; border-left:1px dotted;">
				<div style="float:left; width:49%;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							LOINC
						</div>
						<div style="float:left">
							<form:input path="LOINC" id="HIV_LAB_LOINC" class="editable standardInputMedText mL5" maxlength="10" title="LOINC"/>
						</div>
					</div>

					<div class="mT5 container">
						<div style="float:left; width:40%">
							Test Result (STD)
						</div>
						<div style="float:left">
							<form:input path="TEST_RESULT" id="HIV_LAB_TEST_RESULT" class="standardInputMedText mL5 " maxlength="4000" title="Test Result"/>
						</div>
					</div>
					<c:if test="${param.documentType != 'RESISTANCE' }">
						<div class="mT5 container">
							<div style="float:left; width:40%">
								Test Result (RAW)
							</div>
							<div style="float:left">
								<form:input path="RAW_OBXOBSERVATIONRESULTTEXT" class="standardInputMedText mL5 readonly" title="Test Result (RAW)"/>
							</div>
						</div>
					</c:if>
					
				</div>
				<div style="float:right; width:49%; margin-bottom: 5px;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							DESCRIPTION
						</div>
						<div style="float:left">
							<form:input path="LOINC_DESC" id="HIV_LAB_LOINC_DESC" class="standardInputMedText mL5 widgetAddressFieldsReadonly" maxlength="500" title="DESCRIPTION" />
						</div>
					</div>
					
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Unit Measure Code
						</div>
						<div style="float:left">
							<form:input path="UNIT_MEASURE_CODE" id="HIV_LAB_UNIT_MEASURE_CODE" class="standardInputSmallText2 mL5 widgetAddressFieldsReadonly" maxlength="50" title="Unit Measure Code" />
						</div>
					</div>					
				</div>

				<div style="width:99.9%; border:1px dotted;" class="container" ></div>

				<div style="float:left; width:49%;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Internal ID
						</div>
						<div style="float:left">
							<form:input path="PERSON_INTERNAL_ID" id="HIV_LAB_PERSON_INTERNAL_ID"  class="standardInputMedText mL5" maxlength="20" title="Internal ID"/>
						</div>
					</div>					
				</div>
				<div style="float:right; width:49%; margin-bottom: 5px;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							External ID
						</div>
						<div style="float:left">
							<form:input path="PERSON_EXTERNAL_ID" id="HIV_LAB_PERSON_EXTERNAL_ID" class="standardInputMedText mL5" maxlength="20" title="External ID"/>
						</div>
					</div>				
				</div>		
				
				<div style="width:99.9%; border:1px dotted;" class="container"></div>

				<div style="float:left; width:49%;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Sending Facility
						</div>
						<div style="float:left">
							<form:input path="SENDING_FACILITY_NAME" id="HIV_LAB_SENDING_FACILITY_NAME"   class="standardInputMedText mL5" maxlength="180" title="Sending Facility"/>
						</div>
					</div>	
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Sending Facility CLIA
						</div>
						<div style="float:left">
							<form:input path="SENDING_FACILITY_CLIA" id="HIV_LAB_SENDING_FACILITY_CLIA"   class="standardInputMedText mL5" maxlength="10" title="Sending Facility CLIA"/>
						</div>
					</div>					
				</div>
				<div style="float:right; width:49%; margin-bottom: 5px;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Referring Facility
						</div>
						<div style="float:left">
							<form:input path="REFERRING_FACILITY"  id="HIV_LAB_REFERRING_FACILITY" class="standardInputMedText mL5 " maxlength="30" title="Referring Facility"/>
						</div>
					</div>
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Testing Facility
						</div>
						<div style="float:left">
							<form:input path="TESTING_FACILITY" id="HIV_LAB_TESTING_FACILITY"  class="standardInputMedText mL5" maxlength="30" title="Testing Facility"/>
						</div>
					</div>					
				</div>				

				<div style="width:99.9%; border:1px dotted;" class="container"></div>

				<div style="float:left; width:49%;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Specimen Source Code
						</div>
						<div style="float:left">
							<form:input path="SPECIMEN_SOURCE_CD" id="HIV_LAB_SPECIMEN_SOURCE_CD"  class="standardInputMedText mL5" maxlength="10" title="Specimen Source Code"/>
						</div>
					</div>					
				</div>
				<div style="float:right; width:49%; margin-bottom: 5px;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Specimen Source Name
						</div>
						<div style="float:left">
							<form:input path="SPECIMEN_SOURCE_NAME" id="HIV_LAB_SPECIMEN_SOURCE_NAME"  class="standardInputMedText mL5 " maxlength="255" title="Specimen Source Name"/>
						</div>
					</div>				
				</div>			
										
			</div>						 
		</div>
		
		<!--  Provider details -->
			
		<div class="container bold" style="border:1px dotted; width:100%; padding-bottom:5px;" id="labDocumentProviderAddressDtls">	 
			<div id="prvDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Provider Details</span></div>
			<div style="margin-top:2px; margin-left:5px;  margin-right:5px;" class="providerDetails">
					<span class="mL25"> <spring:message code="label.firstname" /></span><span class="pL10"> <form:input path="PROV_FNAME"  id="PROV_FNAME"  class="standardInputMedText providerFields" maxlength="80" title="Provider First Name"/> </span> 
					<span style="margin-left:10px"> <spring:message code="label.middleName" /></span><span class="pL10"> <form:input path="PROV_MNAME" id="PROV_MNAME" class="standardInputMedText providerFields" maxlength="80" title="Provider Middle Name"/> </span>
					<span style="margin-left:10px"> <spring:message code="label.lastname" /></span><span class="pL10"> <form:input path="PROV_LNAME" id="PROV_LNAME" class="standardInputMedText providerFields" maxlength="80" title="Provider Last Name"/> </span>
					<span style="margin-left:10px"> <spring:message code="label.suffix" /></span><span class="pL10"><form:input path="PROV_SUFFIX" id="PROV_SUFFIX" class="standardInputMedText providerFields" maxlength="80" title="Provider Suffix"/> </span>
					<br/>
				
			</div>
			
			<div class="b1 providerDetails" style="margin: 15px 5px 5px 5px">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"><form:input path="PROV_ADDRESS" id="PROV_ADDRESS" class="oAdrDlg standardInputText newAddress widgetAddressFieldsReadonly providerFields" maxlength="106" title="Provider Address"/></span>	 	                    
	 	                    
	 	                    <span class="pL20">City</span>
	 	                    <span class="pL10"> <form:input path="PROV_CITY" id="PROV_CITY" class="editable oAdrDlg standardInputSmallText  newCity widgetAddressFieldsReadonly providerFields" maxlength="30" title="Provider City"/> </span>
	 	                    
	 	                    <span class="pL20">State </span>
	 	                    <span  class="pL10">
								<form:input path="PROV_STATE" id="PROV_STATE" class="editable oAdrDlg standardInputVerySmall  newState wS40 widgetAddressFieldsReadonly providerFields"  maxlength="2" title="Provider State"/>
							</span>
	 	                    
	 	                    <span class="pL20">Zip </span>
	 	                    <span class="pL10"> 
	 	                        <form:input path="PROV_ZIP" id="PROV_ZIP" class="editable oAdrDlg nOnly  standardInputSmallText1 newZip widgetAddressFieldsReadonly providerFields"  maxlength="5" title="Provider Zip"/> - 
								<form:input path="PROV_PLUS4" id="PROV_PLUS4" class="editable oAdrDlg nOnly standardInputSmallText1 providerFields" maxlength="4" title="Provider Plus4"/>
	 	                    </span>
	 	                    <span class="pL20">County </span>
	 		                   <span class="pL10"> 
									<form:select  id="PROV_COUNTY"  tabindex="8" path="PROV_COUNTY" class="editable oAdrDlg standardInputMedText newCounty wC100 widgetAddressFieldsReadonly providerFields" readonly="" title="Provider County">
								        <form:option value="" label="" />
										<form:options items="${sessionScope.countyMap}"/>
							  		 </form:select>
		 		               </span>       	                    
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10">
 	            	<span  class="" style="padding-left:59px"> <form:input path="RAWPROVADDRESS" class="editable standardInputText readonly" title="Raw Provider Address"/></span>
 	               	                 
 	                 <span class="pL20"> Phone</span>
 					 <span class="pL10"> <form:input path="PROV_PHONE"  id="PROV_PHONE" class="editable pNumber standardInputSmallText3 phoneInputText providerFields" maxlength="20" title="Provider Phone"/></span>
 					 
 					 <span class="pL20"> Provider NPI</span>
 					 <span class="pL10"> <form:input path="PROV_NPI"  id="PROV_NPI" class="standardInputSmallText3 readonly" maxlength="20" title="Provider NPI"/></span>
 					 
 					 <div style="float: right;" class="mr5">
						<a href="javascript:openAddressWidget('labDocumentProviderAddressDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
					 </div>
 	                <br>               
 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
 	                    
 	            </div>
 	        </div>						
		</div>
			
		<!--  Facility details -->
			
		<div style="padding-bottom:5px; border:1px dotted;" class="container bold" id="labDocumentFacilityAddressDtls">
			<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class="mL2">Facility Details</span>
			</div>
			<div style="padding-left:10px; padding-top:2px;" class="providerDetails">
				Facility Name <form:input path="FACILITY_NAME"  id="FACILITY_NAME" class="editable standardInputText facilityFields" maxlength="90" title="Facility Name"/>					
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
								<form:input path="FACILITY_PLUS4" id="FACILITY_PLUS4" class="editable oAdrDlg nOnly standardInputSmallText1 facilityFields" maxlength="4" title="Facility Plus4"/>
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
 	               	                 
 	                 <span class="pL20"> Phone</span>
 					 <span class="pL10"> <form:input path="FACILITY_PHONE"  id="FACILITY_PHONE" class="editable pNumber standardInputSmallText3 phoneInputText facilityFields" maxlength="20"  title="Facility Phone"/></span>
 					 
 					 <span class="pL20"> Facility NPI</span>
 					 <span class="pL10"> <form:input path="FACILITY_NPI"  id="FACILITY_NPI" class="standardInputSmallText3 readonly" maxlength="20" title="Facility NPI"/></span>
 					 
 					 
 					 <div style="float: right;" class="mr5">
 					 	<a href="javascript:openAddressWidget('labDocumentFacilityAddressDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
					 </div>
 	                <br>               
 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
 	                    
 	            </div>
 	        </div>						
		</div>
		
	</div>																	 										
	<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
	 <div id="button-row" class="button-row documentButtons center">
					
					
					<c:choose>
						<c:when test="${psMessageReasonDetails != null   &&  psMessageReasonDetails.RSN_NO_PS_MSG == 'N'   &&  psMessageReasonDetails.RSN_PS_MSG == 'N'}">
						
								<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3 displayNone"  value="Trigger Message"  onclick="confirmAddressForPsMessage('Y')"/>
								
							
				 				<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3 displayNone"  value="Reject PS Message"  onclick="rejectPsMessage()"/>
				 		
						
						</c:when>
						
						<c:when test="${psMessageReasonDetails == null && stdLabDocVO.STATENO != null && stdLabDocVO.STATENO != ''  && nyehmsStatenoDetails.REASON_DESC == null && userPermissions['98'] != null}">
							
										<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3 displayNone"  value="Trigger Message"  onclick="confirmAddressForPsMessage()"/>
						
						</c:when>
					</c:choose>
					
					
					
					
					<c:if  test="${stdLabDocVO.STATENO != null && stdLabDocVO.STATENO != ''  && nyehmsStatenoDetails.REASON_DESC == null}" >
							<c:if  test="${userPermissions['99'] != null && param.documentType != 'STD_LAB'}">
								<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3 displayNone"  value="Trigger Assignment"  onclick="loadSurvAssignmentPopUp()"/>
							</c:if>
					</c:if>	
			
					<c:if test="${param.hasEditFunctionality == true}">
						<input type="button" id="eButton"  class="editBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="customDocumentEditViewerStyles()" />
					</c:if>
					<c:choose>
						<c:when  test="${param.hasDeleteFunctionality == true}" >	
								<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
								<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
						</c:when>
						<c:when test="${stdLabDocVO.STATENO != null && stdLabDocVO.STATENO != ''  && nyehmsStatenoDetails.REASON_DESC == null}">
								
								<c:if test="${userPermissions['90'] != null}">
									<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
								</c:if>
								
								<c:if test="${userPermissions['100'] != null}" >
									<input type="button" id="editDocument"  class="editDocumentBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="confirmEditDocumentFromStateno()" />
								</c:if>
								
						</c:when>
					</c:choose>
					
						<c:if test="${stdLabDocVO.STATENO != null && stdLabDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null && param.documentType != 'STD_LAB'
													&& userPermissions['230'] != null}">
							<input type="button" id="LaftFollowupButton" class="flagLaftFollowupDocument button rounded blue effect-3 displayNone"  value="Laft follow-up"  onclick="flagLaftFollowupDocument(${stdLabDocVO.DOCUMENT_ID}, '${stdLabDocVO.STATENO}')"/>						
						</c:if>
						
				<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Save" onclick="saveForm()"/>	 
				<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />				
			
		
	</div>
	
	<div id="editLabConfirm">
		<h1 class="editLabContentMessage"></h1>
	
	</div>
	
	<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />	
</form:form>																



