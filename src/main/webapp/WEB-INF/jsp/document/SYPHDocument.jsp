<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${staticPath}/js/docs/airsDoc.js?v=${buildDate}"></script>
<link href="${staticPath}/css/docs/airsForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/docs/commonForm.css" rel="stylesheet" type="text/css" />

<form:form modelAttribute="stdSYPHDocVO" id="stdSYPHDocVO" cssClass="documentFormVO documentDescription">
<form:errors path="*" cssClass="errorblock" element="div" />

        <div id="labDocTab" style="background-color: transparent">
                
                <div style="padding-left:43%; " class="headingCls">
                                SYPHILIS &nbsp;  DOCUMENT
                </div>                
                
                <div class="pB30 pT20">		
					
					<c:if test="${stdSYPHDocVO.PARENT_DOCUMENT_ID != null &&  stdSYPHDocVO.PARENT_DOCUMENT_ID != 0}">
		
				         <div  class="floatLeft width70Perc" id="parentDocumentIdSection">
				            <span class="parentIdSectionLabel bold mL10">Parent DocumentId: </span>
							<span class="parentIdSectionValue "><a href="#" onclick="loadDocumentDlg(${stdSYPHDocVO.PARENT_DOCUMENT_ID}, '${param.documentType}')">${stdSYPHDocVO.PARENT_DOCUMENT_ID}</a></span>
				         </div>	
			        </c:if>
			         
					<div  class="floatRight">
			            <span class="mL10 bold">Document ID: </span>
						<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
			        </div>	
		
				</div>

				<div class="container w100 nonRawEditFields pB10">
		 	         <div style="float:left; width: 40%">
		 	            <div >
		 	                 <span class="ml10"><spring:message code="label.stateNo" />: </span>
		 	                 <span class="ml10"><form:input path="STATENO" class="standardInputSmallText readonly" maxlength="10" readonly="true"/> </span>
		 	            </div>    
		 	          </div>
		 	          <div style="float:left; width: 30%">
		 	         	<div >
		 	                <span class="ml10"> eHARS Import Date: </span>
		 	                <fmt:formatDate value="${stdSYPHDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
		 			 		<span class="ml10"><form:input path="EHARS_IMPORT_DATE" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/> </span>
		 	            </div>    
		 	         </div>
		 	         <div style="float:left; width: 30%; text-align: right">
		 	            <div >
		 	                <span class="ml10">Received Date</span>
		 	                <span class="ml10"><form:input path="RECEIVED_DATE" class="standardInputSmallText readonly" maxlength="10" readonly="true"/> </span>
		 	            </div>    
		 	          </div>
		 	 	</div>	 
		 		
		 		
   				<jsp:include page="statenoDetails.jsp"></jsp:include>               
                
                <!--  Person details -->

                <div class="container" id="patientInformation">  
                        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Person Details</span>
                        </div>
                        <div style="padding-left:10px;padding-top:10px">
                                <div class="personInfo">
                                			<div class="fieldHolder25">
	                                            <span class="pL10">First Name </span> 
	                                            <span class="pL5"><form:input path="FNAME" id="SYPH_FNAME" class="standardInputMedText required" title="First Name" maxLength="20"/></span>
	                                            <div class="pL10 v_error SYPH_FNAME "></div> 
	                                        </div>
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Middle Name </span> 
                                            	<span  class="pL5"><form:input path="MNAME" id="SYPH_MNAME" class="standardInputMedText"  title="Middle Name" maxLength="25"/></span>
                                            	<div class="pL10 v_error SYPH_MNAME "></div>
                                            </div>
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Last Name </span> 
                                            	<span  class="pL5"><form:input path="LNAME" id="SYPH_LNAME" class="standardInputMedText required" title="Last Name" maxLength="25"/></span>
                                            	<div class="pL10 v_error SYPH_LNAME "></div>
                                            </div>
	                            <br><br>
                                </div>
                                
                               	<div class="clearBoth personInfo">
                               			<div class="fieldHolder25">
                                       		<span class="pL10">Date of Birth </span>
                                       		<span  class="pL10"><form:input path="DOB" id="DATE_OF_BIRTH" class="dPicker mask standardInputSmallText" title="Date of Birth" maxLength="10" datevalidations="completeDate"/></span>
                                       		<div class="pL10 v_error DATE_OF_BIRTH "></div>
                                       </div>
                                       <div class="fieldHolder25">
                                       		<span class="pL10">Date of Death</span>
                                       		<span class="pL10"><form:input path="DOD" id="DATE_OF_DEATH" class="dPicker mask standardInputSmallText" title="Date of Death" maxLength="10" datevalidations="completeDate"/></span>
                                       		<div class="pL10 v_error DATE_OF_DEATH "></div>
                                       </div>
                                </div><br>
                                 
                                
                                <div class="b1" style="margin: 15px 5px 10px 0px" id="airsDocumentPersonAddressDtls">
					 	            <div class="personInfo pTRBL10">
					 	                <span class="personLabelDoc"> Address </span>
				 		                <span> 	                	
					 	                	<span class="pL10"> <form:input path="PERSON_ADDRESS" id="syphAdd" class="standardInputText widgetAddressFieldsReadonly newAddress" title="Address" maxLength="62"/>  </span>	 	        
					 	                	            	 	                    
					 	                    <span class="pL30">City</span>
					 	                    <span class="pL10"><form:input path="PERSON_CITY" id="syphCity" class="standardInputSmallText widgetAddressFieldsReadonly newCity" title="City" maxLength="30"/></span>
					 	                    
					 	                    <span class="pL30">State </span>
					 	                    <span  class="pL10">
												<form:input path="PERSON_STATE" id="syphState" class="standardInputSmallText1 widgetAddressFieldsReadonly newState" title="State" maxLength="2"/>
											</span>
					 	                    
					 	                    <span class="pL30">Zip </span>
					 	                    <span class="pL10"> 
					 	                       <form:input path="PERSON_ZIP" id="syphZip" class="standardInputSmallText1 widgetAddressFieldsReadonly newZip" title="Zipcode" maxLength="5"/>
					 	                    </span> 
					 	                    
					 	                    <span class="pL30">County </span>
					 	                    <span class="pL10"> 
					 	                    	<form:select path="PERSON_COUNTY"  cssClass="oAdrDlg newCounty standardInputSmallText1 roundedBrdr widgetAddressFieldsReadonly newCounty" title="County" maxLength="3"> 
													<form:option value=""></form:option>
													<form:options items="${sessionScope.countyMap}"/>
												 </form:select>
					 	                    </span>     	                    
					 	                    <br>
					 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
					 	                </span>
					 	            </div>
					 	            <div class="personInfo pT10 pTRBL10">
					 	            	<span  class="nonRawEditFields" style="padding-left:59px"><form:input path="STREET" id="syphRawAdd" class="standardInputText readonly" title="Address" maxLength="62"/></span>
					 	              	
					 	              	<span class="pL30">Phone </span>
					 	                    <span class="pL10"> 
					 	                       <form:input path="PERSON_PHONE" id="syphPhone" class="standardInputSmallText" title="Telephone" maxLength="20"/>
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
                
                
                <!--  Race/Ethnicity details -->
		
		        <div id="raceEthnicity" class="container" style="border:1px dotted; padding:10px;">
			       <div style="float:left; width:25%; margin-left:20px; border-right:1px dotted; padding-bottom:10px;">				
				        <div>
					        Race 
				        </div>
				        <div>
				        	<form:checkbox path="RACE_WHITE" value="1" /> White	
				        </div>
				        <div>
					        <form:checkbox path="RACE_BLACK" value="1" /> African American
				        </div>
				        <div>
					        <form:checkbox path="RACE_ASIAN" value="1" /> Asian
				        </div>
				        <div>
					        <form:checkbox path="RACE_PACIFIC" value="1" /> Pacific
				        </div>				
				        <div>
					        <form:checkbox path="RACE_INDIAN" value="1" /> American Indian/ Alaskan Native
				        </div>				
				        <div>
					        <form:checkbox path="RACE_OTHER" value="1" /> Other 
			        	</div>
				        <div>
					        <form:checkbox path="RACE_UNKNOWN" value="1" /> Unknown
				        </div>
			       </div>
			
			
			       <div style="float:left;  width:35% ; ">
				        <div style="padding-left:10px;">	
					        Ethnicity (Hispanic or Latino)
				    	   <br>
					       <form:radiobutton path="ETHNICITY" value="01" /> Yes <br>
					       <form:radiobutton path="ETHNICITY" value="02" /> No <br>
					       <form:radiobutton path="ETHNICITY" value="09" /> Unknown	
				        </div>
								
				        <div style="padding:15px 0 10px 10px; border-top: dotted 1px; border-bottom: dotted 1px"> 
					        <div class=""> Gender </div>
					        <div  class="">
				    	       <div> <form:radiobutton path="BIRTH_SEX" value="M" /> Male </div>
						       <div> <form:radiobutton path="BIRTH_SEX" value="F" /> Female </div>		
					        </div>
				        </div>
			       </div>
			
			
			<div style="float:left; width:35% ; padding-left:10px; border-left:1px dotted; height: 163px" >
			    <div class="mT5 container">
					     <div style="float:left; width:35%">
						     Case
					     </div>
					     <div style="float:left">
						     <form:input path="CASEKEY" class="standardInputMedText " title="Case"   maxlength="50"/>
					     </div>
				</div>				
				<div class="mT5 container">
					<div style="float:left; width:35%">
						Diagnosis Date
					</div>
					<div style="float:left">
						<form:input path="SYPHILIS_DIAGNOSIS_DATE" id="SYPH_DIAG_DATE" class="dPicker mask standardInputMedText " datevalidations="completeDate" title="Diagnosis Date" maxlength="10" />
					</div>
				</div>
			</div>			
		</div>	
		
		
		<!--  Provider/Facility Details -->	
		
		<div style=" width:100%; border:1px dotted; padding-bottom:5px;" class="container" id="syphDocumentProviderDtls">
					
			<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Provider/Facility Details</span>					
			</div>
			<div class="mLRTB pB20">
				<div style="float:left;"> 
					<span class="mL25">First Name</span><span> <form:input path="PROV_FNAME" class="standardInputMedText providerFields" title="Provider First Name" maxlength="48"/> </span> 
					<span style="margin-left:10px">Last Name</span><span> <form:input path="PROV_LNAME" class="standardInputMedText providerFields" title="Provider Last Name"  maxlength="48" /> </span>
				</div>
				<div style="clear:both"></div>
				<div class="b1" style="margin: 15px 5px 10px 0px">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"> <form:input path="PROV_ADDRESS" class="standardinputText newAddress widgetAddressFieldsReadonly" maxlength="100" title="Provider Address" /></span>	 	        
	 	                    <span class="pL30">City</span>
	 	                    <span class="pL10"><form:input path="PROV_CITY" class="oAdrDlg standardInputSmallText1 newCity widgetAddressFieldsReadonly" title="Provider City" maxlength="60"/></span>
	 	                    <span class="pL30">State </span>
	 	                    <span  class="pL10">
								<form:select id="PROV_STATE"  path ="PROV_STATE" class="oAdrDlg standardInputSmallText1 newState widgetAddressFieldsReadonly" title="Provider State" >
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.stateMap}"/>
							    </form:select>
							</span>
	 	                    <span class="pL30">Zip </span>
	 	                    <span class="pL10"> 
	 	                        <form:input path="PROV_ZIP" class="oAdrDlg nOnly  standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5" title="Provider Zipcode" />  
	 	                    </span> 
	 	                    <span class="pL30">County </span>
	 	                    <span class="pL10"> 
	 	                        <form:select id="PROV_COUNTY"   path="PROV_COUNTY" class="oAdrDlg standardInputSmallText1 newCounty widgetAddressFieldsReadonly" title="Provider County" >
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.countyMap}"/>
							    </form:select>
	 	                    </span>     
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
	 	                </span>
	 	            </div>
	 	            <div class="personInfo pT10 pTRBL10">
	 	            	<span class="nonRawEditFields" style="padding-left:59px"><form:input path="PROVIDERADDRESS" class="standardinputText readonly" maxlength="100" readonly="true" title="Raw Address" /></span>
	 	                <span style="float: right;" class="mr50">
							<a href="javascript:openAddressWidget('syphDocumentProviderDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
					    </span> 
						<span class="pL30">Phone </span>
 	                    <span class="pL10"> 
 	                            <form:input path="PROV_PHONE" class="standardInputMedText mR20" maxlength="16" title="Provider Phone" />
 	                    </span>  
			            <br>               
	 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
	 	            </div>
	 	        </div>
			 </div>																	 
		  </div>	
		</div>
		        
        <!--  Buttons Removed -->   
        <div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
        <br>
        <form:hidden path="DOCUMENT_SOURCE_TABLE" id="DOCUMENT_SOURCE_TABLE"/>
        <input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />          
</form:form>


