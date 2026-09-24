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
	
<!-- 	<div id="ahiDocumentPage" style="display:none">
	   		<div id="ahiDocumentTransactionMessage" class="transactionMessage"><span class="message"></span></div>
   </div> -->
   	

<form:form modelAttribute="aHIDocVO" id="aHIDocVO" cssClass="documentFormVO documentDescription">
<form:errors path="*" cssClass="errorblock" element="div" />

        <div id="" style="background-color: transparent">
                
                <div style="padding-left:43%; " class="headingCls">
                                AHI &nbsp;  DOCUMENT
                </div>
                
           	<div id="ahiDocumentTransactionMessage" class="transactionMessage"><span class="message"></span></div>
			<div id="sendEmailTransactionMessage" class="transactionMessage"><span class="message"></span></div>
				
				
			<div class="pB30 pT20">
		
					
					<c:if test="${aHIDocVO.PARENT_DOCUMENT_ID != null &&  aHIDocVO.PARENT_DOCUMENT_ID != 0 && param.origin != 'Assignment'}">
		
				<div  class="floatLeft width70Perc" id="parentDocumentIdSection">
				            <span class="parentIdSectionLabel bold mL10">Parent DocumentId: </span>
							<span class="parentIdSectionValue "><a href="#" onclick="loadDocumentDlg(${aHIDocVO.PARENT_DOCUMENT_ID}, '${param.documentType}')">${aHIDocVO.PARENT_DOCUMENT_ID}</a></span>
							
				         </div>	
			         </c:if>
			         
			
			</div>
				
	                <div class="pB30 pT20">
	
			 	      		<div class="floatLeft">
			 	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
			 	                 <span class="ml10"><form:input path="STATENO" value="${STATENO}" class="standardInputSmallText readonly" tabindex="-1" maxlength="10" readonly="true"/> </span>
			 	          	</div>
		 	          	
						<c:if test="${aHIDocVO.DOCUMENT_ID != null &&  aHIDocVO.DOCUMENT_ID != 0}">			
		 					<div  class="floatRight">
					            <span class="mL10 bold">Document ID: </span>
								<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" tabindex="-1" maxlength="6" readonly="true"/></span>
					       	</div>
		 	      		</c:if>   	
					</div>
				
	
   					<jsp:include page="statenoDetails.jsp"></jsp:include>
   					
   							 	    
                <!--  Person details -->

                <div class="container" id="patientInformation">  
                        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Patient Information</span>
                        </div>
                        <div style="padding-left:10px;padding-top:10px">
                                <div class="personInfo" style="height:60px">
                                			<div class="fieldHolder25">
	                                            <span class="pL10"><spring:message code="label.firstname" /> </span> 
	                                            <span class="pL5"><form:input path="FNAME" id="ahi_fname" class="standardInputMedText required" cssErrorClass="error" title="First Name" maxLength="20"/></span>
	                                            <div class="pL10 v_error ahi_fname mL10 width250"></div> 
	                                        </div>
	                                        <div class="fieldHolder30">
                                            	<span class="pL10"><spring:message code="label.middleName" /> </span> 
                                            	<span  class="pL5"><form:input path="MNAME" id="ahi_mname" class="standardInputMedText"  cssErrorClass="error" title="Middle Name" maxLength="25"/></span>
                                            	<div class="pL10 v_error ahi_mname mL10 width250"></div>
                                            </div>	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10"><spring:message code="label.lastname" /></span> 
                                            	<span  class="pL5"><form:input path="LNAME" id="ahi_lname" class="standardInputMedText required"  cssErrorClass="error" title="Last Name" maxLength="25"/></span>
                                            	<div class="pL10 v_error ahi_lname mL10 width250"></div>
                                            </div>
                                             <br><br>
                                             
	                               			<div class="fieldHolder30">
	                                       		<span class="pL10">Date of Birth </span>
	                                   			<span class="pL5"><form:input path="DOB" id="DATE_OF_BIRTH" datevalidations="completeDate" class="standardInputSmallText dPicker mask required" title="Date Of Birth" maxlength="10"/> </span>
	                                       		<div class="pL10 v_error DATE_OF_BIRTH "></div>
	                                       </div>
                                       
	                                       <div class="fieldHolder25">
	                                       		<span class="pL10">SSN</span>
	                                       		<span class="pL10"><form:input path="SSN" id="ahi_ssn" class="standardInputSmallText mask" title="SSN" maxLength="9"/></span>
                                       			<div class="pL10 v_error ahi_ssn "></div>	
	                                       	</div>                                        
                                        <br>
                                        
                                </div>
                                
                                <div class="b1" style="margin: 15px 5px 10px 0px" id="ahiDocumentPersonAddressDtls">
                                	<div class="personInfo pTRBL10">
					 	                <span class="personLabelDoc"> Residence Dx Address </span>
				 		                <span> 	                	
					 	                	<span class="pL10"> <form:input path="PERSON_ADDRESS" id="ahiPersonAddress" class="standardInputMedText newAddress widgetAddressFieldsReadonly" tabindex="-1" title="Address" maxLength="62"/>  </span>	 	        
					 	                	            	 	                    
					 	                    <span class="pL20"><spring:message code="label.city" /></span>
					 	                    <span class="pL10"><form:input path="PERSON_CITY" id="ahiPersonCity" class="standardInputSmallText newCity widgetAddressFieldsReadonly" tabindex="-1" title="City" maxLength="30"/></span>
					 	                    
					 	                    <span class="pL20"><spring:message code="label.state" /> </span>
					 	                    <span  class="pL10">
												<form:input path="PERSON_STATE" id="ahiStateState" class="standardInputSmallText1 newState widgetAddressFieldsReadonly" tabindex="-1" title="State" maxLength="2"/>
											</span>
					 	                    
					 	                    <span class="pL20"><spring:message code="label.zip" /> </span>
					 	                    <span class="pL10"> 
					 	                       <form:input path="PERSON_ZIP" id="ahiZipZip" class="standardInputSmallText1 newZip widgetAddressFieldsReadonly" tabindex="-1" title="Zipcode" maxLength="5"/>
					 	                    </span> 
					 	                    
					 	                    <span class="pL20"><spring:message code="label.county" /> </span>
					 	                    <span class="pL10">
					 	                    	<c:choose>
					 	                    		<c:when test="${param.origin == 'Print'}">
					 	                    			<form:input path="PERSON_COUNTY"  id="ahiPersonCounty" cssClass="oAdrDlg newCounty standardInputSmallText1  newCounty widgetAddressFieldsReadonly" title="County" maxLength="3"/>
					 	                    		</c:when>
					 	                    		<c:otherwise>
					 	                    			<form:select path="PERSON_COUNTY"  id="ahiPersonCounty" cssClass="oAdrDlg newCounty standardInputSmallText1  newCounty widgetAddressFieldsReadonly" tabindex="-1" title="County" maxLength="3"> 
															<form:option value=""></form:option>
															<form:options items="${sessionScope.countyMap}"/>
														 </form:select>
													
					 	                    		</c:otherwise>
					 	                    	</c:choose>
					 	                     
					 	                    	 
												 
					 	                    </span> 
					 	                    <span class="pL20"> <button value="Edit" onclick="openAddressWidget('ahiDocumentPersonAddressDtls');return false;" class="editAddress displayNone inlineEditButton">Add Address</button> </span>    	 
					 	                    
					 	                </span>
					 	            </div>
					 	        </div>                               
                        </div>        
                </div>

                
                <div class="container" id="patientInformation">  
                        <div id="psCaseInfoHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Facility of Diagnosis</span>
                        </div>
 						<div style="width:100%; margin-top:5px; margin-right:5px;" class="checkForValue facilityDiagnosisHivElements">
							<div style="float:left; width:70%; padding-bottom:5px;"> 
								<span class="pL10"> <spring:message code="label.facilityName" /></span>
								<span class="mL30"> 
									<c:choose>
										<c:when test="${param.origin == 'Print'}">
											<form:input path="FACILITY_NAME" id="AHI_FACILITY_NAME" class="standardInputText " maxlength="80" title="Facility Name" />
										</c:when>
										
										<c:otherwise>
												<form:select onchange="loadFacilityAddressDetails('#facilityOfDx', this)" path="FACILITY_EHARS_UID" id="facilityOfDxEharsUid" addrsVar="facilityUid" class="standardInputText " maxlength="80" title="Facility Name">
													<form:option value="" >--select--</form:option>
													<c:forEach items="${subFacilities}" var="facility">
														<form:option value="${facility.facility_uid}" label="${facility.facility_name}" name="${facility.facility_name}"></form:option>
													</c:forEach>
												</form:select>
												<c:if test="${param.origin != 'Assignment' && param.origin != 'Print' }">
													<span ><a class="mailFacility cloningIcons" href="#"></a></span>
												</c:if>
												<form:hidden path="FACILITY_ID" id="facilityOfDxId" addrsVar="facilityId"/>
												<form:hidden path="FACILITY_NAME" id="facilityOfDxName" addrsVar="facilityName"/>													
										</c:otherwise>
									</c:choose>
								 </span>
							</div>
							<div style="float:right;width:29%; border-left:1px dotted; padding-bottom:5px;"> 
								<span class="mL20" > <spring:message code="label.phoneNumber" /> <form:input path="FACILITY_PHONE" id="facilityOfDxPhone"  value="${FACILITY_PHONE}" class="standardInputMedText mL5 readonly" tabindex="-1" addrsVar="facilityPhone" maxlength="20" title="Phone Number"/> </span>
								<div class="v_error facilityOfDxPhone mL20"></div>
							</div>
						</div> 
						
						<div style="width:100%; border:1px dotted; " class="container"></div>
						
							<div style="float:left; width:99%; padding-bottom:5px; border-bottom:1px dotted;" class="mR5 mT5 checkForValue facilityDiagnosisHivElements"> 
								<span  class="pL10"><spring:message code="label.streetAddress" /> </span>
								<span class="mL17"> <form:input path="FACILITY_ADDRESS" id="facilityOfDxAddress" value="${FACILITY_ADDRESS}" class="standardInputText readonly" addrsVar="streetAddrs" tabindex="-1" maxlength="100" title="Street Address"/> </span>
								<div class="v_error facilityOfDxAddress"></div>
							</div>	
						
							<div style="float:left; width:99%; margin-right:5px;" class="checkForValue facilityDiagnosisHivElements"> 
								<div style="float:left; width:19%; border-right:1px dotted;  padding-bottom:5px;"> 
									<span class="mL5"> <spring:message code="label.city" /></span><br/>
									<span class="mL5"> <form:input path="FACILITY_CITY" id="facilityOfDxCity" value="${FACILITY_CITY}" class="standardInputMedText readonly" tabindex="-1" addrsVar="cityAddrs" maxlength="30" title="City" /> </span>
								</div>
								<div style="float:left; width:20%; border-right:1px dotted;  padding-bottom:7px;"> 
									<span class="mL20"> <spring:message code="label.county" /></span><br/>
									<span class="mL20"> 
										<c:choose>
												<c:when test="${param.origin == 'Print'}">
				 	                    			<form:input path="FACILITY_COUNTY"  cssClass="oAdrDlg newCounty standardInputSmallText1  newCounty widgetAddressFieldsReadonly" title="County" maxLength="3"/>
				 	                    		</c:when>
				 	                    		<c:otherwise>
				 	                    			<form:select path="FACILITY_COUNTY" id="facilityOfDxCountyFips" value="${FACILITY_COUNTY}" class="standardInputSmallText readonly" tabindex="-1" addrsVar="countyAddrs" maxlength="3" title="County Fips" >
														<form:option value="" label="--select--"></form:option>
														<form:options items="${sessionScope.countyMap}"/>
													</form:select>
				 	                    		</c:otherwise>
				 	                    	</c:choose>
									
											  
									</span>
								</div>
								
								<div style="float:left; width:20%; border-right:1px dotted;  padding-bottom:7px;"> 
									<span class="mL20"> <spring:message code="label.state" /></span><br/>
									<span class="mL20"> 
										<c:choose>
												<c:when test="${param.origin == 'Print'}">
				 	                    			<form:input path="FACILITY_STATE"  cssClass="oAdrDlg newCounty standardInputSmallText1  newCounty widgetAddressFieldsReadonly" title="State" maxLength="2"/>
				 	                    		</c:when>
				 	                    		<c:otherwise>
				 	                    			<form:select path="FACILITY_STATE" id="facilityOfDxState" value="${FACILITY_STATE}" class="standardInputSmallText readonly" tabindex="-1" addrsVar="stateAddrs" maxlength="2" title="State" >
														<form:option value="" label="--select--"></form:option>
														<form:options items="${sessionScope.stateMap}"/>
													</form:select>	
				 	                    		</c:otherwise>
				 	                    	</c:choose>
										
										 
									</span>
								</div>
								<div style="float:left; width:19%; border-right:1px dotted;  padding-bottom:7px;">
									<span class="mL20"> <spring:message code="label.country" /></span><br/>
									<span class="mL20"> <form:select path="FACILITY_COUNTRY" id="facilityOfDxCountry" value="${FACILITY_COUNTRY}" tabindex="-1" class="standardInputSmallText readonly" addrsVar="countryAddrs" maxlength="7" title="Country" >
									<form:option value=""></form:option>
										<form:options items="${codedCountries}"/>
										<form:option value="OTH">Other</form:option>
										<form:option value="UNK">Unknown</form:option>
									</form:select>  </span>
									
								</div>
								
								<div style="float:left; width:21%; padding-bottom:7px;"> 
									<span class="mL20"> <spring:message code="label.zip" /></span><br/>
									<span class="mL20"> <form:input path="FACILITY_ZIP" id="facilityOfDxZipcode" value="${FACILITY_ZIP}" tabindex="-1" class="standardInputSmallText2 readonly" addrsVar="zipAddrs" maxlength="5" title="Zip Code" /> </span>
									<span class="mL2"> <form:input path="FACILITY_PLUS4" id="facilityOfDxPlus4" value="${FACILITY_PLUS4}" tabindex="-1" class="standardInputSmallText1 readonly" addrsVar="plus4Addrs" maxlength="4" title="plus4" /> </span>			
								</div>	
							</div>						
                     
                </div>                                    
                
                <div class="container" id="patientInformation">  
                        <div id="psCaseInfoHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Provider of Diagnosis</span>
                        </div>

                        <div style="padding-left:10px;padding-top:10px">
                                <div class="personInfo">

									<div class="fLW10">
										<span class="pL10">Provider Name </span>
									</div>
									<div class="fLW20">
											 <span class="pL10">First Name </span>
											<span  class="pL5"><form:input path="DIAGNOSIS_PROVIDER_FNAME" id="dxProviderFName" class="standardInputSmallText "  title="Provider Name" maxLength="100"/></span>
											
									</div>
									<div class="fLW25">
											<span class="pL10">Middle Name </span> 
											<span  class="pL5"><form:input path="DIAGNOSIS_PROVIDER_MNAME" id="dxProviderMName" class="standardInputSmallText "  title="Provider Name" maxLength="100"/></span>
											
									</div>
									<div class="fLW20">
											<span class="pL10">Last Name </span> 
											<span  class="pL5"><form:input path="DIAGNOSIS_PROVIDER_LNAME" id="dxProviderLName" class="standardInputSmallText "  title="Provider Name" maxLength="100"/></span>
											
									</div>
									<div class="fLW20">
											<span class="pL10">Suffix </span> 
											<span  class="pL5"><form:input path="DIAGNOSIS_PROVIDER_SUFFIX" id="dxProviderSuffix" class="standardInputSmallText "  title="Provider Name" maxLength="100"/></span>
											
									</div>
									
									<div class="clearBoth ">
										<div class="fieldHolder10 mT10"><span class="pL10">Provider Notes: </span> </div>
										<div class="fieldHolder49 mT10">
											<span>  <form:textarea title="Provider Notes" maxlength="4000" cols="200" rows="3" path="DIAGNOSIS_PROVIDER_NOTES" style="width:65%; height:67px; border:1px solid gray;" class="roundedBorder"></form:textarea></span>											                                   	
		                                </div>
	                                </div>
                                </div>
                        </div>                            
                </div>                    
                
                

                
                
                <div class="container" id="patientInformation">  
                        <div id="psCaseInfoHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Negative/Indeterminate 900 test history</span>
                        </div>
                       
                        <div style="padding-left:10px;padding-top:10px">
                                <div class="personInfo">
                                	<div class="fieldHolder49">		
									<span class="pL30"> Reason patient tested</span>
									<span class="pL10">
									<c:choose>
		 	                    		<c:when test="${param.origin == 'Print'}">
		 	                    			<form:input path="CI_REASON_PATIENT_TESTED" id="ciReasonPatientTested" class="standardInputSmallText mR50" title="Reason Patient Tested" />
		 	                    		</c:when>
		 	                    		<c:otherwise>
											<form:select path="CI_REASON_PATIENT_TESTED" id="ciReasonPatientTested" class="standardInputSmallText mR50" title="Reason Patient Tested" onChange="changeOtherCondition(this, '#ciResonPatientTestedOther')">
												<form:option value="" label="-----Select-------"></form:option>
												<c:forEach items="${ahiCIReasonPatientTestedList}" var="testReason">
													<form:option value="${testReason.TESTED_CODE}" label="${testReason.TESTED_DESC}"></form:option>
												</c:forEach>
											 </form:select> 
										
		 	                    		</c:otherwise>
		 	                    	</c:choose>
									 </span>
									 </div>	
		 	                      	       
									<div class="fieldHolder30">		 	                      	             
	                                   	<span class="pL10">Other </span> 
	                                   	<span  class="pL5"><form:input path="CI_REASON_PATIENT_TESTED_OTHR" id="ciResonPatientTestedOther" class="standardInputMedText"  title="Other" maxLength="100"/></span>
	                                   	<div class="pL10 v_error ciResonPatientTestedOther mL10 width250"></div>
                                   	</div>
			                   </div>
                        </div><br>
		                                                       
				<div style="width:100%; padding:2px; background-color:#DBEAF9;" class="container ">
						<span class="hdrFont mL5"> Negative/Indeterminate 900 test history</span>
<!-- 						<div style="float:right; margin-right:20px;"> -->
<%-- 							<span><img class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addNegInd900TH()" /> </span> --%>
<!-- 						</div>				 -->
				</div>		
				
				<div id="negIndDivContainer" class="container" style="float:left; width:99%; padding:2px; padding-left:10px;border-bottom:1px dotted"> 
				
					<c:forEach items="${aHIDocVO.negInd900TestHisList}" var="tempObj" varStatus="statusCD">
	<%-- 				<c:choose> --%>
	<%-- 					<c:when test="${statusCD.index ==0}"> --%>
						
	<%-- 					</c:when> --%>
	<%-- 					<c:otherwise> --%>
						
								<div class="negIndDiv" id="negIndDiv" style="float:left; width:99%; padding-top:5px; margin-left:5px; margin-right:5px; margin-top:2px;" > 	
										
									<div>			
										<div class="fieldHolder33">
											<span> Last Neg./IND date <form:input  path="negInd900TestHisList[${statusCD.index}].LAST_NEG_IND_DATE"  id="negInd900TestHis_${statusCD.index}_lastNegIndDate" datevalidations="partialDateForAll" value="${negInd900TestHisList[statusCD.index].LAST_NEG_IND_DATE}" class="lastNegIndDateField standardInputMedText dPicker mask" maxlength="10" title="Last Neg./IND date"  onChange="changeNegIndResult(this, 'negInd900TestHisList[${statusCD.index}].NEG_IND_RESULT')"/></span>
										 	<div class="v_error negInd900TestHis_${statusCD.index}_lastNegIndDate mL20"></div>
										</div>
										<div id="negInd_${statusCD.index}_ResultForDt" class="fieldHolder15"> 
											<span> Result</span>
											<span class="mL5"><form:radiobutton path="negInd900TestHisList[${statusCD.index}].NEG_IND_RESULT" id="negInd_${statusCD.index}_Result" value="N"/> Neg. </span>
											<span class="mL5"><form:radiobutton path="negInd900TestHisList[${statusCD.index}].NEG_IND_RESULT" id="negInd_${statusCD.index}_Result" value="I" /> IND. </span>
											<div class="v_error negInd_${statusCD.index}_ResultForDt mL20"></div>										
	
										</div>
					
										<div class="fieldHolder25">
											
											<span> Test Type</span>
											<span class="pL10">
											<c:choose>
				 	                    		<c:when test="${param.origin == 'Print'}">
													<form:input path="negInd900TestHisList[${statusCD.index}].NEG_IND_TEST_TYPE_CODE" id="negInd900TestHis_${statusCD.index}_negIndTestType" class="standardInputSmallText mR50" title="Test Type"/>												
				 	                    		</c:when>
				 	                    		<c:otherwise>
													<form:select path="negInd900TestHisList[${statusCD.index}].NEG_IND_TEST_TYPE_CODE" id="negInd900TestHis_${statusCD.index}_negIndTestType" class="standardInputSmallText mR50" title="Test Type">
														<form:option value="" label="-----Select-------"></form:option>
														<c:forEach items="${ahiNegInd900TestList}" var="negIndTest">
															<form:option value="${negIndTest.TEST_TYPE_CODE}" label="${negIndTest.TEST_TYPE_DESC}"></form:option>
														</c:forEach>
													 </form:select> 
												
				 	                    		</c:otherwise>
				 	                    	</c:choose>					 	                     
											 						 	                    
											 </span>	
										</div>
										<div class="fieldHolder25">
											<span >if other, Specify </span> 
											<span  class="pL5"><form:input path="negInd900TestHisList[${statusCD.index}].NEG_IND_TEST_TYPE_OTHER" id="negInd900TestHis_${statusCD.index}_negIndTestTypeOther" class="standardInputMedText"  title="Other" maxLength="100"/></span>
										</div>
									</div>					
									<div class="clearBoth pT10">
									
										<div class="fieldHolder33"> 
											<span > Report</span>
											<span class="mL5"><form:radiobutton path="negInd900TestHisList[${statusCD.index}].NEG_IND_LAB_REPORT" id="negInd900TestHis_${statusCD.index}_negIndLabReport" value="L"/> Lab </span>
											<span class="mL5"><form:radiobutton path="negInd900TestHisList[${statusCD.index}].NEG_IND_LAB_REPORT" id="negInd900TestHis_${statusCD.index}_negIndLabReport" value="S" /> PT Self </span>
											<span class="mL5"><form:radiobutton path="negInd900TestHisList[${statusCD.index}].NEG_IND_LAB_REPORT" id="negInd900TestHis_${statusCD.index}_negIndLabReport" value="D" /> DR Notes </span>
										</div>
									
										<div class="fieldHolder49">
										  <div style="float:left; width:80%;">
											<span >Provider at last Neg./IND test </span> 
											<span  class="pL5"><form:input path="negInd900TestHisList[${statusCD.index}].NEG_IND_LAST_TEST_PROV_NAME" id="negInd900TestHis_${statusCD.index}_negIndLastTestProvName" class="standardInputMedText"  title="Provider Name" maxLength="100"/></span>
											<div class="pL10 v_error negInd900TestHis_${statusCD.index}_negIndLastTestProvName mL10 width250"></div>
										  </div>
										  <div style="float:left; width:20%;">	
											<span class="mL30"><form:checkbox path="negInd900TestHisList[${statusCD.index}].NEG_IND_PROV_NAME_UNKNOWN" id="negInd900TestHis_${statusCD.index}_negIndProvNameUnknown" value="Y" title="Unk" /> Unk </span>
										  </div>
										</div>
<%-- 										<div class="floatLeft " style=" width:4%" id="negInd900TestHis_${statusCD.index}_closeNegInd" > --%>
<%-- 											<span class="mL2" style="float:right; margin-top:4px;"><img class="cloningIcons" onclick="deleteNegInd900THDiv(this, 'negIndDiv');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span> --%>
<!-- 										</div> -->
								 		<form:hidden id="negInd900TestHis_${statusCD.index}_negIndDocId" path="negInd900TestHisList[${statusCD.index}].AHI_DOCUMENT_ID" />
								 	</div>
							 		
						 		</div>
					 		
<!-- 					 		<script>   -->
<!--  					 			negIndCnt = negIndCnt+1; -->
<!-- 							</script>	 -->

					</c:forEach>
				</div>
        		</div>
        		
        		
                <div class="container" id="patientInformation">  
                        <div id="psCaseInfoHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Evidence for AHI</span>
                        </div>
                        
                        <div style="float:left; width:90%; padding-bottom:5px;"> 
							<span class=" pL30"> AHI reported symptoms</span>
							<span class="mL5"><form:radiobutton path="EVIDENCE_AHI_REPTD_SYMPTOMS" id="evidenceAhiRptSymptoms" value="Y"/> Yes, specify below (check all that apply) </span>
							<span class="mL5"><form:radiobutton path="EVIDENCE_AHI_REPTD_SYMPTOMS" id="evidenceAhiRptSymptoms" value="N"/> No, asymptomatic </span>
							<span class="mL5"><form:radiobutton path="EVIDENCE_AHI_REPTD_SYMPTOMS" id="evidenceAhiRptSymptoms" value="U"/> Unk/not documented </span>
						</div>
					<br><br>
	  
					<div style="width:99%; padding-bottom:5px;"  class="mL5 mR5 container" > 
						<div style="float:left; width:28%; ">
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_REPTD_NOT_SPECFD" id="ahiSymptomsReptdNotSpecfy" value="1" class="readonly"/> Symptoms reported, but not specified </span><br>   
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_ABDOMINAL_PAIN" id="ahiSymptomsAbdominalPain" value="1" class="readonly" /> Abdominal pain </span><br>   
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_ARTHRALGIAS" id="ahiSymptomsArthralgias" value="1" class="readonly" /> Arthralgias (pain in joint)  </span><br>   
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_DIARRHEA" id="ahiSymptomsDiarrhea" value="1" class="readonly" /> Diarrhea </span><br>   
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_EXDA_PHRYNGTIS" id="ahiSymptomsPhryngtis" value="1" class="readonly" /> Exudative pharyngitis (inflammation of throat w/pus) </span><br>
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_FATIGUE" id="ahiSymptomsFatigue" value="1" class="readonly" /> Fatigue </span>  
						</div>						
						<div style="float:left; width:28%; ">
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_FEVER" id="ahiSymptomsFewer" value="1" class="readonly" /> Fever </span><br>   
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_FLU_LIKE_SYMPTOM" id="ahiSymptomsFluLike" value="1" class="readonly" /> Flu-like symptoms </span><br>   
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_GENITAL_ULCERS" id="ahiSymptomsGenitalUlcers" value="1" class="readonly" /> Genital ulcers </span><br>   
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_HEADACHE" id="ahiSymptomsHeadache" value="1" class="readonly" /> Headache </span><br>   
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_LYMPHADENCPATHY" id="ahiSymptomsLymphadenopathy" value="1" class="readonly" /> Lymphadenopathy (enlargment of lymph nodes) </span><br>   
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_MYALGIAS" id="ahiSymptomsMyalgias" value="1" class="readonly" /> Myalgias (pain in muscle) </span>   
						</div>						
						<div style="float:left; width:20%; ">
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_NAUSEA" id="ahiSymptomsNausea" value="1" class="readonly" /> Nausea </span><br>   
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_NIGHT_SWEATS" id="ahiSymptomsNightSweats" value="1" class="readonly" /> Night sweats </span><br>   
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_ORAL_ULCERS" id="ahiSymptomsOralUlcers" value="1" class="readonly" /> Oral ulcers </span><br>   
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_PHOTOPHOBIA" id="ahiSymptomsPhotophobia" value="1" class="readonly" /> Photophobia </span><br>   
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_RASH" id="ahiSymptomsRash" value="1" class="readonly" /> Rash </span><br>   
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_RECTAL_ULCERS" id="ahiSymptomsRectalUlcers" value="1" class="readonly" /> Rectal ulcers </span>   
						</div>						
						<div style="float:left; width:22%; ">
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_SORE_THROAT" id="ahiSymptomsSoreThroat" value="1" class="readonly" /> Sore throat </span><br>   
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_STIFF_NECK" id="ahiSymptomsStiffNeck" value="1" class="readonly" /> Stiff neck </span><br>   
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_VOMITING" id="ahiSymptomsVommiting" value="1" class="readonly" /> Vomiting </span><br>   
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_WEIGHT_LOSS" id="ahiSymptomsWeightLoss" value="1" class="readonly" />Weight loss </span><br>
		                  	<span class="mL5"><form:checkbox path="AHI_SYMPTOMS_OTHER" id="ahiSymptomsOther" value="1" class="readonly" />Other, specify:</span><br>
							<span  class="pL5"><form:input path="AHI_SYMPTOMS_OTHER_TEXT" id="ahiSymptomsOtherText" class="standardInputMedText readonly" maxLength="100" title="Other"/></span>
							<div class="pL10 v_error ahiSymptomsOtherText"></div>	
													
						</div>						
					</div> 
					
					<div style="float:left; width:50%; padding-bottom:5px;"> 
							<span class=" pL30"> Provider Diagnosis of AHI</span>
							<span class="mL5"><form:radiobutton path="EVIDENCE_AHI_PROV_DX_OF_AHI" id="provDxOfAhi" value="Y"/> Yes </span>
							<span class="mL5"><form:radiobutton path="EVIDENCE_AHI_PROV_DX_OF_AHI" id="provDxOfAhi" value="N" /> No </span>
					</div> 
  					<br>
					<div style="float:left; width:40%;">
						<span class=" mL20"> Provider diagnosis date<form:input  path="EVIDENCE_AHI_PROV_DX_DATE"  id="provDxDate" datevalidations="completeDate" value="${EVIDENCE_AHI_PROV_DX_DATE}" class=" standardInputMedText dPicker mask" maxlength="10" title="Diagnosis date" /></span>
					 	<div class="v_error provDxDate mL20"></div>
					</div>                       
                </div>        		
        		
                <div class="container" id="patientInformation">  
                        <div id="psCaseInfoHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Medical Follow-up appointment</span>
                        </div>

                        <div style="padding-left:10px;padding-top:10px">
                                <div class="personInfo">
	                               	<div class="fieldHolder49">
		                                	<span class="mL5"><form:checkbox path="MFA_REFSD_LNK_TO_900_OMC" id="mfaRefusedLinkTo900Omc" value="Y" title="Refused linkage to 900 outpatient medical care." /> Refused linkage to 900 outpatient medical care. </span>
	                                </div> 
	                               	<div class="fieldHolder49">
			                                	<span class="pL10">Reason: </span> 
			                                   	<span  class="pL5"><form:input path="MFA_REFSD_LNK_TO_900_OMC_RESN" id="mfaRefusedLinkTo900OmcReason" class="standardInputMedText"  title="Reason" maxLength="100"/></span>
												<div class="pL10 v_error mfaRefusedLinkTo900OmcReason mL20"></div>	  
	                                </div> 	                                
	                                <br><br>
									
									<div class="fieldHolder49">
											 <span class="inputFieldInDialogWindow appointmentDateTimePicker">Next 900-related medical appointment date and time
		                                       <form:input class="formInputText readonly" data-format="MM/dd/yyyy HH:mm:ss PP" dateValidations="noPastDate" path="MFA_NEXT_900_RELATD_APPT_DATE" tabindex="-1"  id="mfaNext900RelatedAptDate" name="mfaNext900RelatedAptDate" value="" title="Appointment Date" />
												<span id="appointmentDateTimePickerIcon" class="add-on">
												 <span class="icon-calendar cloningIcons" data-date-icon="icon-calendar" data-time-icon="icon-time">
				      							</span>
				      							</span>                                       
											</span>
									</div>
									<div class="fieldHolder49">
											<span class="pL10">Name of provider </span> 
											<span  class="pL5"><form:input path="MFA_PROVIDER_NAME" id="mfaProviderName" class="standardInputMedText"  title="Provider Name" maxLength="50"/></span>
											<div class="pL20 v_error mfaProviderName mL10 width250"></div>
									</div>
																	
                                </div><br><br><br>
                                
                                <div class="b1" style="margin: 15px 5px 10px 0px" id="ahiMFAClinicAddress">
                                	<div class="personInfo pTRBL10">
					 	                <span class="personLabelDoc"> Clinic Address </span>
				 		                <span> 	                	
					 	                	<span class="pL10"> <form:input path="MFA_CLINIC_ADDRESS" id="mfaClinicAddress" class="standardInputMedText newAddress widgetAddressFieldsReadonly" tabindex="-1" title="Address" maxLength="62"/>  </span>	 	        
					 	                	            	 	                    
					 	                    <span class="pL30"><spring:message code="label.city" /></span>
					 	                    <span class="pL10"><form:input path="MFA_CLINIC_CITY" id="mfaClinicCity" class="standardInputSmallText newCity widgetAddressFieldsReadonly" tabindex="-1" title="City" maxLength="30"/></span>
					 	                    
					 	                    <span class="pL30"><spring:message code="label.state" /> </span>
					 	                    <span  class="pL10">
												<form:input path="MFA_CLINIC_STATE" id="mfaClinicState" class="standardInputSmallText1 newState widgetAddressFieldsReadonly" tabindex="-1" title="State" maxLength="2"/>
											</span>
					 	                    
					 	                    <span class="pL30"><spring:message code="label.zip" /> </span>
					 	                    <span class="pL10"> 
					 	                       <form:input path="MFA_CLINIC_ZIP" id="mfaClinicZip" class="standardInputSmallText1 newZip widgetAddressFieldsReadonly" tabindex="-1" title="Zipcode" maxLength="5"/>
					 	                    </span> 
					 	                    
					 	                    <span class="pL30"><spring:message code="label.county" /> </span>
					 	                    <span class="pL10">
											<c:choose>
				 	                    		<c:when test="${param.origin == 'Print'}">
				 	                    			<form:input path="MFA_CLINIC_COUNTY_FIPS"  cssClass="oAdrDlg newCounty standardInputSmallText1 roundedBrdr newCounty widgetAddressFieldsReadonly" title="County" maxLength="3"/>
				 	                    		</c:when>
				 	                    		<c:otherwise>
											      	<form:select path="MFA_CLINIC_COUNTY_FIPS"  cssClass="oAdrDlg newCounty standardInputSmallText1 roundedBrdr newCounty widgetAddressFieldsReadonly" tabindex="-1" title="County" maxLength="3"> 
														<form:option value=""></form:option>
														<form:options items="${sessionScope.countyMap}"/>
													 </form:select>
												
				 	                    		</c:otherwise>
				 	                    	</c:choose>					 	                     
					 	              
					 	                    </span>     	                    
					 	                    <span class="pL20"> <button value="Edit" onclick="openAddressWidget('ahiMFAClinicAddress');return false;" class="editAddress displayNone inlineEditButton">Clinic Address</button> </span> 					 	                    
					 	                </span>
					 	            </div>
					 	        </div>
					 	                                       
                        </div>                            
                </div>            		

                
                <div class="container" id="patientInformation">  
                        <div id="psCaseInfoHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Case Information</span>
                        </div>
                        
						<div style="width:100%; padding:2px; background-color:#DBEAF9;" class="container ">
								<span class="hdrFont mL5"> Disease/Diagnosis Condition (Please list any STI's occurring within 6 months of the AHI diagnosis)</span>
								<div style="float:right; margin-right:20px;">
									<span><img class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addSTICondition()" /> </span>
								</div>				
						</div>		
						
						<div id="stiDivContainer" class="container" style="float:left; width:99%; padding:2px; padding-left:10px;"> 
<!-- 							<div class="stiDiv" id="stiDiv" style="float:left; width:99%; padding-top:5px; margin-left:5px; margin-right:5px; margin-top:2px;" > 		 -->
								
<!-- 								<div style="float:left; width:45%;"> -->
									
<!-- 								<span class="pL30"> STI Condition</span> -->
<!-- 								<span class="pL10"> -->
<%-- 									<c:choose> --%>
<%-- 		 	                    		<c:when test="${param.origin == 'Print'}"> --%>
<%-- 		 	                    			<form:input path="ciStiConditionList[0].CI_STI_CONDITION_CODE" id="ciStiCondition_0_stiConditionCode" class="standardInputSmallText mR50" title="STI Condition"/> --%>
<%-- 		 	                    		</c:when> --%>
<%-- 		 	                    		<c:otherwise> --%>
<%-- 		 	                    			<form:select path="ciStiConditionList[0].CI_STI_CONDITION_CODE" id="ciStiCondition_0_stiConditionCode" class="standardInputSmallText mR50 sitiCondition" title="STI Condition" onChange="changeDxDateCondition(this, '#ciStiCondition_0_stiConditionDxDate')"> --%>
<%-- 												<form:option value="" label="-----Select-------"></form:option> --%>
<%-- 												<c:forEach items="${ahiCiStiConditionList}" var="stiCondition"> --%>
<%-- 													<form:option value="${stiCondition.DIAGNOSIS_CODE}" label="${stiCondition.DIAGNOSIS_DESC}"></form:option> --%>
<%-- 												</c:forEach> --%>
<%-- 											 </form:select>  --%>
										
<%-- 		 	                    		</c:otherwise> --%>
<%-- 		 	                    	</c:choose> --%>
									 						 	                    
									
<!-- 								 </span>	 -->
<!-- 								</div> -->
											 						
<!-- 								<div style="float:left;"> -->
<%-- 									<span class=" mL20"> Dx Date: <form:input  path="ciStiConditionList[0].CI_STI_CONDITION_DX_DATE"  id="ciStiCondition_0_stiConditionDxDate" datevalidations="completeDate" value="${ciStiConditionList[0].CI_STI_CONDITION_DX_DATE}" class=" standardInputMedText dPicker mask" maxlength="10" title="Diagnosis Date" /></span> --%>
<!-- 								 	<div class="v_error ciStiCondition_0_stiConditionDxDate mL20"></div> -->
<!-- 								</div> -->
<!-- 		 						<div style="float:left; width:4%" id="ciStiCondition_0_closeSti" class="displayNone"> -->
<%-- 										<span class="mL2" style="float:right; margin-top:4px;"><img class="cloningIcons" onclick="deleteSITConditionDiv(this, 'stiDiv');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span> --%>
<!-- 									</div> -->
<%-- 						 			<form:hidden id="ciStiCondition_0_stiDocId" path="ciStiConditionList[0].AHI_DOCUMENT_ID" /> --%>
<!-- 							</div> -->
			
		
						
						<c:forEach items="${aHIDocVO.ciStiConditionList}" var="tempObj" varStatus="statusCD">
<%-- 						<c:choose> --%>
<%-- 							<c:when test="${statusCD.index ==0}"> --%>
							
<%-- 							</c:when> --%>
<%-- 							<c:otherwise> --%>
							
									<div class="stiDiv" id="stiDiv" style="float:left; width:99%; padding-top:5px; margin-left:5px; margin-right:5px; margin-top:2px;" > 		
										
										<div style="float:left; width:45%;">
											
										<span class="pL30"> STI Condition</span>
										<span class="pL10">
											<c:choose>
				 	                    		<c:when test="${param.origin == 'Print'}">
				 	                    			<form:input path="ciStiConditionList[${statusCD.index}].CI_STI_CONDITION_CODE" id="ciStiCondition_${statusCD.index}_stiConditionCode" class="standardInputSmallText mR50" title="STI Condition"/>
				 	                    		</c:when>
				 	                    		<c:otherwise>
				 	                    			<form:select path="ciStiConditionList[${statusCD.index}].CI_STI_CONDITION_CODE" id="ciStiCondition_${statusCD.index}_stiConditionCode" class="sitiCondition standardInputSmallText mR50" title="STI Condition"  onChange="changeDxDateCondition(this, '#ciStiCondition_${statusCD.index}_stiConditionDxDate')">
														<form:option value="" label="-----Select-------"></form:option>
														<c:forEach items="${ahiCiStiConditionList}" var="stiCondition">
															<form:option value="${stiCondition.DIAGNOSIS_CODE}" label="${stiCondition.DIAGNOSIS_DESC}"></form:option>
														</c:forEach>
													 </form:select> 
												
				 	                    		</c:otherwise>
				 	                    	</c:choose>
										
										
										
										
										 						 	                    
											
										 </span>	
										</div>
													 						
		 								<div style="float:left;">
											<span class=" mL20"> Dx Date: <form:input  path="ciStiConditionList[${statusCD.index}].CI_STI_CONDITION_DX_DATE"  id="ciStiCondition_${statusCD.index}_stiConditionDxDate" datevalidations="completeDate" value="${ciStiConditionList[statusCD.index].CI_STI_CONDITION_DX_DATE}" class=" standardInputMedText dPicker mask" maxlength="10" title="Diagnosis Date" /></span>
										 	<div class="v_error ciStiCondition_${statusCD.index}_stiConditionDxDate mL20"></div>
										</div>
				 						<div style="float:left; width:4%" id="ciStiCondition_${statusCD.index}_closeSti" >
												<span class="mL2" style="float:right; margin-top:4px;"><img class="cloningIcons" onclick="deleteSITConditionDiv(this, 'stiDiv');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span>
											</div>
								 			<form:hidden id="ciStiCondition_${statusCD.index}_stiDocId" path="ciStiConditionList[${statusCD.index}].AHI_DOCUMENT_ID" />
									</div>
									
								<script>  
									 stiCnt = stiCnt+1;
								</script>	
					

						</c:forEach>
					</div>				

                </div>                           		
        
  </div>      
  
  	<div class="mT10">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
	 <div class="button-row">
	 
 			<c:if test="${param.origin == 'Assignment' }">
 				<input type="button" id="saveAndCloseButton" class="button rounded blue effect-3" value="Save and Close Assignment" onclick="saveAndCloseAssignment()"/>
 			</c:if>
 			<c:if test="${param.origin != 'Print' }">
 				<input type="button" id="sButton" class="button rounded blue effect-3" value="Save" onclick="saveAhiForm()"/>	
 				<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" onclick="cancelAHIForm()"/>
 			</c:if>
 		
			<c:choose>
				<c:when test="${aHIDocVO.STATENO != null && aHIDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
						<c:if test="${userPermissions['90'] != null}" >
							<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
						</c:if>
						<c:if test="${userPermissions['100'] != null}" >
							<input type="button" id="editDocument"  class="editDocumentBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="confirmEditDocumentFromStateno()" />
						</c:if>
				</c:when>
			</c:choose>
			
	 		
	 		
	</div>
	<form:hidden path="AHI_FLAG" id="ahiFlag" />
	<form:hidden path="DOCUMENT_ID" id="ahiDocumentId" />
	<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
</form:form>

