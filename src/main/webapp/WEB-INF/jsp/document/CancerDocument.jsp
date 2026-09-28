<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${staticPath}/js/docs/airsDoc.js?v=${buildDate}"></script>
<link href="${staticPath}/css/docs/airsForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/docs/commonForm.css" rel="stylesheet" type="text/css" />

<form:form modelAttribute="stdCancerDocVO" id="stdCancerDocVO" cssClass="documentFormVO documentDescription">
<form:errors path="*" cssClass="errorblock" element="div" />

        <div id="CancerDocTab" style="background-color: transparent">
                
                <div style="padding-left:43%; " class="headingCls">
                                CANCER &nbsp;  DOCUMENT
                </div>
                
                
                <div class="pB30 pT20 bold">
		
					<div  class="floatRight">
			            <span class="mL10">Patient ID: </span>
						<span class=" "><form:input path="PATIENT_ID_NUMBER" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
			         </div>	
		
				</div>
                
                <!--  Person details -->

                <div class="container bold" id="patientInformation">  
                        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Patient Information</span>
                        </div>
                        <div style="padding-left:10px;padding-top:10px">
                                <div class="personInfo">
                                			<div class="fieldHolder25">
	                                            <span class="pL10">First Name </span> 
	                                            <span class="pL5"><form:input path="FNAME" id="CANCER_FNAME" class="standardInputMedText required" title="First Name" maxLength="20"/></span>
	                                        </div>
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Middle Name </span> 
                                            	<span  class="pL5"><form:input path="MNAME" id="CANCER_MNAME" class="standardInputSmallText"  title="Middle Name" maxLength="25"/></span>
                                            </div>
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Last Name </span> 
                                            	<span  class="pL5"><form:input path="LNAME" id="CANCER_LNAME" class="standardInputMedText required" title="Last Name" maxLength="25"/></span>
                                            </div>
                                            
                                            
                                        <br><br>
                                        
                                        
                                </div>
                               	<div class="clearBoth personInfo">
                               			<div class="fieldHolder25">
                                       		<span class="pL10">Date of Birth </span>
                                       		<span  class="pL10"><form:input path="DATE_OF_BIRTH" id="DATE_OF_BIRTH" class="dPicker mask standardInputSmallText" title="Date of Birth" maxLength="10" datevalidations="completeDate"/></span>
                                      	</div>
                                 		<div class="fieldHolder25">
	                                        <span class="pL10">Date of Death </span>
	                                        <span class="pL10"> 
	                                                <form:input path="DATE_OF_DEATH" id="DATE_OF_DEATH" class="dPicker mask standardInputSmallText" title="Date of Death" maxLength="10" datevalidations="completeDate"/>
	                                        </span>
	                                    </div>                                       
                                       <div class="fieldHolder25">
                                       		<span class="pL10">SSN</span>
                                       		<span class="pL10"><form:input path="SSN" id="CANCER_SSN" class="standardInputSmallText1" title="SSN" maxLength="9"/></span>
                                       	</div>                                        
                                </div>
                                
                                <div class="clearBoth personInfo pT10">
                               			<div class="fieldHolder25">
                                       		<span class="pL10">Birth Sex </span>
                                       		<span  class="pL10"><form:input path="SEX" id="SEX" class="dPicker mask standardInputSmallText" title="Birth sex" /></span>
                                      	</div>
                                 		<div class="fieldHolder25">
	                                        <span class="pL10">Ethnicity </span>
	                                        <span class="pL10"> 
	                                                <form:input path="ETHNICITY" id="ETHNICITY" class="dPicker mask standardInputMedText" title="Ethnicity" />
	                                        </span>
	                                    </div>                                       
                                       <div class="fieldHolder40">
                                       		<span class="pL10">Race</span>
                                       		<span class="pL10"><form:input path="RACE" id="CANCER_RACE" class="standardInputText" title="RACE"/></span>
                                       	</div>                                        
                                </div>
                                
                                <div class="clearBoth pT10">
                                
                                <div class="b1" style="margin: 15px 5px 10px 0px" id="airsDocumentPersonAddressDtls">
					 	            <div class="personInfo pTRBL10">
					 	                <span class="personLabelDoc"> Address </span>
				 		                <span> 	                	
					 	                	<span class="pL10"> <form:input path="PERSON_ADDRESS" id="cancerAdd" class="standardInputText widgetAddressFieldsReadonly newAddress" title="Address" maxLength="62"/>  </span>	 	        
					 	                	            	 	                    
					 	                    <span class="pL30">City</span>
					 	                    <span class="pL10"><form:input path="PERSON_CITY" id="cancerCity" class="standardInputSmallText widgetAddressFieldsReadonly newCity" title="City" maxLength="30"/></span>
					 	                    
					 	                    <span class="pL30">State </span>
					 	                    <span  class="pL10">
												<form:input path="PERSON_STATE" id="cancerState" class="standardInputSmallText1 widgetAddressFieldsReadonly newState" title="State" maxLength="2"/>
											</span>
					 	                    
					 	                    <span class="pL30">Zip </span>
					 	                    <span class="pL10"> 
					 	                       <form:input path="PERSON_ZIP" id="cancerZip" class="standardInputSmallText1 widgetAddressFieldsReadonly newZip" title="Zipcode" maxLength="5"/>
					 	                    </span> 
					 	                    
					 	                    <span class="pL30">County </span>
					 	                    <span class="pL10"> 
					 	                    	<form:select path="PERSON_COUNTY"  cssClass="oAdrDlg newCounty roundedBrdr widgetAddressFieldsReadonly newCounty" title="County" maxLength="3"> 
													<form:option value=""></form:option>
													<form:options items="${sessionScope.countyMap}"/>
												 </form:select>
					 	                    </span>     	                    
					 	                    <br>
					 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
					 	                </span>
					 	            </div>
					 	            <div class="personInfo pT10 pTRBL10">
					 	              	
					 	              	<span class="pL30">Phone </span>
					 	                    <span class="pL10"> 
					 	                       <form:input path="PERSON_PHONE" id="airsPhone" class="standardInputSmallText" title="Telephone" maxLength="20"/>
					 	                    </span>    	              	
					 	            </div>
					 	        </div> 
                                </div>
                                                              
                        </div>        
                </div>
					
					

          
        </div>
       
        
               
</form:form>


