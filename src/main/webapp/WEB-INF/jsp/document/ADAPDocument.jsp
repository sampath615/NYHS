<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="${staticPath}/css/docs/adapForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<script src="${staticPath}/js/docs/adapDoc.js?v=${buildDate}"></script>

<form:form modelAttribute="stdADAPDocVO" id="stdADAPDocVO" cssClass="documentDescription">
<form:errors path="*" cssClass="errorblock" element="div" />
	
	<div id="ADAPDoc">
		<div class="headingCls centerDoc">
				<c:choose >
					<c:when test="${param.documentType == 'ADAP_NYC'}">
						UCP NYC Document
					</c:when>
					<c:otherwise>
						UCP Document
					</c:otherwise>
				</c:choose>				
		
				
		</div>
		
		
		<div class="pB30 pT20">
		
					
					<c:if test="${stdADAPDocVO.PARENT_DOCUMENT_ID != null &&  stdADAPDocVO.PARENT_DOCUMENT_ID != 0}">
		
				<div  class="floatLeft width70Perc" id="parentDocumentIdSection">
				            <span class="parentIdSectionLabel bold mL10">Parent DocumentId: </span>
							<span class="parentIdSectionValue "><a href="#" onclick="loadDocumentDlg(${stdADAPDocVO.PARENT_DOCUMENT_ID}, '${param.documentType}')">${stdADAPDocVO.PARENT_DOCUMENT_ID}</a></span>
							
				         </div>	
			         </c:if>
			         
					<div  class="floatRight">
			            <span class="mL10 bold">Document ID: </span>
						<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
			         </div>	
		
		</div>
		
			
	
		<div class="container w100 pB10">
 	         <div style="float:left; width: 40%">
 	            <div>
 	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
 	                 <span class="ml10"><form:input path="MATCHED_STATENO" class="standardInputSmallText" maxlength="10" readonly="true"/> </span>
 	            </div>    
 	          </div>
 	          <div style="float:left; width: 30%">
 	            <div>
 	                <span class=" ml10">	eHARS Import Date: </span>
 			 		<span class="ml10">
 			 			<fmt:formatDate value="${stdADAPDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
						<form:input path="EHARS_IMPORT_DATE" value= "${theFormattedDate}" class="standardInputSmallText ml10" readonly="true"/>
 			 		</span>
 	           </div>                  
 	         </div>
 	         <div style="float:left; width: 30%; text-align: right">
 	         	<div >
 	                <span class="mL10">NYC SHIP Date: </span>
 	                <fmt:formatDate value="${stdADAPDocVO.NYC_SHIP_DT}"  type="date" pattern="MM/dd/yyyy" var="theFormattedNYC_SHIP_DATE_TEXT" ></fmt:formatDate>
					<span class="ml10 "><form:input path="NYC_SHIP_DT" value="${theFormattedNYC_SHIP_DATE_TEXT}" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
 	            </div> 
 	         </div>     
 		</div>
		<jsp:include page="statenoDetails.jsp"></jsp:include>

		<!--  Person details -->
		<div class="container borderADAPDoc">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> Patient details:</span>					
			</div>	 
			<div class="mLRTB pB10">
				<span class="fieldHolder20">
					<span class="fLW35"> First Name</span> 
					<span class="fLW60"><form:input path="FNAME" class="standardinputSmallTextAdap" maxlength="13" /></span>
				</span>
				<span class="fieldHolder20 ">
					<span class="fLW35"> Last Name</span>
					<span class="fLW60"><form:input path="LNAME" id="lName" title="Last Name" class="required standardinputSmallTextAdap" maxlength="15" /></span>
				</span>					
				<span class="fieldHolder20"> 
					<span class="fLW35"> Date of Birth</span>
					<span class="fLW60"> <form:input path="DOB_TEXT" id="dobValidation" class=" standardinputSmallTextAdap" maxlength="10" /> </span>
				</span>
				<span class="fieldHolder20"> 
					<span class="fLW35">SSN </span>	
					<span class="fLW60"> <form:input path="SSN" id="ssnValidation" class="required mask standardinputSmallTextAdap" maxLength="9" /></span>
				</span>	 
			</div>
			
			<div class="b1" style="margin: 15px 5px 10px 5px">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	             
 		                	<span class="pL10"><form:input type="text" path="street_raw"  class="standardInputText" maxlength="39"/>  </span>  	 
	 	                    
	 	                    <span class="pL30">City</span>
	 	                    <span class="pL10"><form:input path="PERSON_CITY" class="standardinputSmallText1 ml10" maxlength="17"/></span>
	 	                    
	 	                    <span class="pL30">State </span>
	 	                    <span  class="pL10">
								<form:input path="PERSON_STATE" class="standardinputSmallText1 ml10" maxlength="2"/>
							</span>
	 	                    
	 	                    <span class="pL30">Zip </span>
	 	                    <span class="pL10"> 
	 	                       <form:input path="PERSON_ZIP" class="standardInputSmallText1 ml10" maxlength="5"/> 
	 	                    </span>    
	 	                    
	 	                    <span class="pL30">County </span>
	 	                    <span class="pL10"> 
	 	                       <form:select path="PERSON_COUNTY"  cssClass="oAdrDlg roundedBrdr newCounty"> 
									<form:option value=""></form:option>
									<form:options items="${sessionScope.countyMap}"/>
								</form:select> 
	 	                    </span> 	                    
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10">
 	            	<span  class="" style="padding-left:59px"><form:input path="PERSON_ADDRESS" class="standardinputText" maxlength="62" readonly="true"/></span>
 	              	 <span class="pL30">Phone </span>
                    <span class="pL10"> 
                       <form:input path="PERSON_PHONE" class="standardinputSmallText" maxlength="15"/>
                    </span>
 	                <br>               
 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
 	                    
 	            </div>
 	        </div>

			<div id="raceEthnicity" class="container" style="border-top:1px dotted; padding:10px 0 5px 0;">
			<div style="float:left; width:35%; margin-left:20px; border-right:1px dotted;">			
				<div> Race</div> 
				<div>					
					<form:checkbox path="RACE_WHITE" value="1" />White	
					<br>
					<form:checkbox path="RACE_BLACK" value="1" /> African American
					<br>
					<form:checkbox path="RACE_ASIAN" value="1" /> Asian
					<br>
					<form:checkbox path="RACE_INDIAN" value="1" /> American Indian/ Alaskan Native		
					<br>			
					<form:checkbox path="RACE_PACIFIC" value="1" /> Pacific Islander
					<br>
					<form:checkbox path="RACE_OTHER" value="1" /> Other 
					<br>
					<form:checkbox path="RACE_UNKNOWN" value="1" /> Unknown						
				</div>	
			</div>
			<div class=" " style="float:left; width:25% ;">
				<div style=" padding-left:10px;">
					<span><spring:message code="label.ethnicity" />  (Hispanic or Latino)</span>
					<div> 
						<form:radiobutton path="ETHNICCD" value="01" /> <spring:message code="label.yes" /><br>
						<form:radiobutton path="ETHNICCD" value="02" /> <spring:message code="label.no" /><br>
						<form:radiobutton path="ETHNICCD" value="09" /> <spring:message code="label.unknown" />
					</div>
				</div>	
				<div style="padding:15px 0 10px 10px; border-top: dotted 1px; "> 
					<div class=""> Birth Sex </div>
					<div  class="">
						<span> <form:radiobutton path="BIRTH_SEX" value="M" /> Male </span>
						<span> <form:radiobutton path="BIRTH_SEX" value="F" /> Female </span>		
					</div>
				</div>
				<div style="padding:15px 0 10px 10px; border-top: dotted 1px;">
					<div class="floatLeft pR10" style="border-right: dotted 1px;">
						<div class=""> Current Gender </div>
						<div  class="">
							<div> <form:radiobutton path="CURRENT_GENDER" value="M" /> Male </div>
							<div> <form:radiobutton path="CURRENT_GENDER" value="F" /> Female </div>
							<div> <form:radiobutton path="CURRENT_GENDER" value="U" />Unknown</div>		
						</div>
					</div>
					<div class="floatLeft pL10">
						<div class=""> Transgender </div>
						<div  class="">
							<div> <form:radiobutton path="CURRENT_GENDER" value="X" /> MTF </div>		
							<div> <form:radiobutton path="CURRENT_GENDER" value="Y" /> FTM </div>		
							<div> <form:radiobutton path="CURRENT_GENDER" value="T" /> Unspecified </div>														
						</div>
					</div> 
				</div>
			</div>
			
			<!--  Medical details -->
			
			<div style="float:left; width:35%; height: 142px; padding-left:10px; padding-bottom: 12px; border-left:1px dotted;">
				<div class="mT5 container">
					<div style="float:left; width:45%">
						CIN
					</div>
					<div style="float:left">
						 <form:input path="MEDICAID_NUMBER" class="standardInputMedText mL5" maxlength="12"/> 
					</div>
				</div>		
				<div class="mT5 container">
					<div style="float:left; width:45%">
						ADAP ID
					</div>
					<div style="float:left">
						<form:input path="ADAP_ID" class="standardInputMedText mL5" maxlength="10"/>
					</div>
				</div>			
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Date of First HIV Test
					</div>
					<div style="float:left">
						<form:input id="dateOfFirstHIV" path="FIRST_HIV_TEST_DATE_TEXT" class=" standardInputMedText mL5" maxlength="10"/>
					</div>
				</div>					
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Care Of
					</div>
					<div style="float:left">
						<form:input path="CARE_OF" class="standardInputMedText mL5" maxlength="40"/>
					</div>
				</div>					
			</div>
		</div>
		
		
		
		<c:choose >
			<c:when test="${param.documentType == 'ADAP_NYC'}">
				<div class="providerDetails">
					<div class="divHeadingCls bGHeading " style="clear:both">
						<span class=" mL2"> Provider Details:</span>					
					</div>
					<div class="mLRTB pB10">	
						<span class="fieldHolder25">
							<span class="fLW25">First Name</span>
							<span class="fLW60">  <form:input path="RX_PROV_FNAME" class="standardinputSmallText ml10 providerFields" maxlength="20"/>  </span>
						</span>
						<span class="fieldHolder25">
							<span class="fLW35">Middle Name</span> 
							<span class="fLW60"> <form:input path="RX_PROV_MNAME" class="standardinputSmallText providerFields" maxlength="20"/> </span>
						</span>
						<span class="fieldHolder25">
							<span class="fLW35">Last Name</span>
							<span class="fLW60"><form:input path="RX_PROV_LNAME" class="standardinputSmallText providerFields" maxlength="25"/></span>
						</span>
					
					</div>
					
					<div class="b1" style="margin: 15px 5px 10px 5px" id="adapDocumentProviderAddressDtls">
		 	            <div class="personInfo pTRBL10">
		 	                <span class="personLabelDoc"> Address </span>
		 		                <span> 
			 		                <span class="pL10"><form:input path="RX_PROV_ADDRESS" class="standardinputText providerFields newAddress widgetAddressFieldsReadonly" maxlength="62" readonly="true"/>  </span> 	                    	                	      
			 	                	            	 	                    
			 	                    <span class="pL30">City</span>
			 	                    <span class="pL10"><form:input path="RX_PROV_CITY" class="standardinputSmallText1 newCity widgetAddressFieldsReadonly providerFields" maxlength="30"/></span>
			 	                    
			 	                    <span class="pL30">State </span>
			 	                    <span  class="pL10">
										<form:select path="RX_PROV_STATE" class="phoneNumberText providerFields newState widgetAddressFieldsReadonly" maxlength="2">
											<form:option value=""></form:option>
											<form:options items="${sessionScope.stateMap}"/>
										</form:select>
									</span>
			 	                    
			 	                    <span class="pL30">Zip </span>
			 	                    <span class="pL10"> 
			 	                        <form:input path="RX_PROV_ZIP" class="standardinputSmallText1 providerFields newZip widgetAddressFieldsReadonly" maxlength="5"/> - <form:input path="RX_PROV_PLUS4" class="standardinputSmallText1 providerFields" maxlength="4"/>
			 	                    </span> 
			 	                    
			 	                    <span class="pL30">County </span>
			 	                    <span class="pL10"> 
			 	                      <form:select path="RX_PROV_COUNTY"  cssClass="oAdrDlg roundedBrdr newCounty providerFields widgetAddressFieldsReadonly"> 
											<form:option value=""></form:option>
											<form:options items="${sessionScope.countyMap}"/>
										</form:select> 
			 	                    </span>    	                    
			 	                    <br>
			 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
			 	                </span>
		 	            </div>
		 	            <div class="personInfo pT10 pB10">
		 	            	<span class="pL30">Phone </span>
		                    <span class="pL10"> 
		                       <form:input path="RX_PROV_PHONE" class="standardinputSmallText providerFields" maxlength="15"/>
		                    </span>
		                     <span style="float: right;" class="mr50">
								<button value="Edit" onclick="openAddressWidget('adapDocumentProviderAddressDtls');return false;" class="displayNone editAddress">Edit</button>
							 </span>  
		 	            </div>
		 	        </div>
	 	        </div>
			</c:when>
			<c:otherwise>
				<div class="divHeadingCls bGHeading">
					<span class=" mL2"> Certifying Provider Details:</span>					
				</div>
				<div class="mLRTB pB10">	
					<span class="fieldHolder25">
						<span class="fLW25">First Name</span>
						<span class="fLW60">  <form:input path="CERT_PROV_FNAME" class="standardinputSmallText ml10" maxlength="20"/>  </span>
					</span>
					<span class="fieldHolder25">
						<span class="fLW35">Middle Name</span> 
						<span class="fLW60">  <form:input path="CERT_PROV_MNAME" class="standardinputSmallText" maxlength="20"/>  </span>
					</span>
					<span class="fieldHolder25">
						<span class="fLW35">Last Name</span>
						<span class="fLW60"> <form:input path="CERT_PROV_LNAME" class="standardinputSmallText" maxlength="25"/></span>
					</span>
					<span class="fieldHolder25">
						<span class="fLW17"> NPI</span> 
						<span class="fLW60">  <form:input path="CERT_PROV_NPI" class="standardinputSmallText ml10" maxlength="12"/> </span>
					</span>
				</div>
				
				<div class="b1" style="margin: 15px 5px 10px 5px">
	 	            <div class="personInfo pTRBL10">
	 	                <span class="personLabelDoc"> Address </span>
	 		                <span> 	  
	 		                	<span class="pL10"><form:input path="CERT_PROV_ADDRESS" class="standardinputText" maxlength="62" readonly="true"/> </span>              	 	        
		 	                	            	 	                    
		 	                    <span class="pL30">City</span>
		 	                    <span class="pL10"><form:input path="CERT_PROV_CITY" class="standardinputSmallText1" maxlength="30"/></span>
		 	                    
		 	                    <span class="pL30">State </span>
		 	                    <span  class="pL10">
									<form:input path="CERT_PROV_STATE" class="phoneNumberText" maxlength="2"/>
								</span>
		 	                    
		 	                    <span class="pL30">Zip </span>
		 	                    <span class="pL10"> 
		 	                       <form:input path="CERT_PROV_ZIP" class="standardinputSmallText1" maxlength="5"/> - <form:input path="CERT_PROV_PLUS4" class="standardinputSmallText1" maxlength="4"/> 
		 	                    </span> 
		 	                    
		 	                    <span class="pL30">County </span>
		 	                    <span class="pL10"> 
		 	                       <form:select path="CERT_PROV_COUNTY"  cssClass="oAdrDlg roundedBrdr newCounty"> 
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
	                       <form:input path="CERT_PROV_PHONE" class="standardinputSmallText" maxlength="15"/>
	                    </span>
	                        
	 	            </div>
	 	        </div>
				<div class="providerDetails">
					<div class="divHeadingCls bGHeading " style="clear:both">
						<span class=" mL2"> Prescribing Provider Details:</span>					
					</div>
					<div class="mLRTB pB10">	
						<span class="fieldHolder25">
							<span class="fLW25">First Name</span>
							<span class="fLW60">  <form:input path="RX_PROV_FNAME" class="standardinputSmallText ml10 providerFields" maxlength="20"/>  </span>
						</span>
						<span class="fieldHolder25">
							<span class="fLW35">Middle Name</span> 
							<span class="fLW60"> <form:input path="RX_PROV_MNAME" class="standardinputSmallText providerFields" maxlength="20"/> </span>
						</span>
						<span class="fieldHolder25">
							<span class="fLW35">Last Name</span>
							<span class="fLW60"><form:input path="RX_PROV_LNAME" class="standardinputSmallText providerFields" maxlength="25"/></span>
						</span>
						<span class="fieldHolder25">
							<span class="fLW17"> NPI</span> 
							<span class="fLW60">  <form:input path="RX_PROV_NPI" class="standardinputSmallText ml10 providerFields" maxlength="12"/></span>
						</span>
					</div>
					
					<div class="b1" style="margin: 15px 5px 10px 5px" id="adapDocumentProviderAddressDtls">
		 	            <div class="personInfo pTRBL10">
		 	                <span class="personLabelDoc"> Address </span>
		 		                <span> 
			 		                <span class="pL10"><form:input path="RX_PROV_ADDRESS" class="standardinputText providerFields newAddress widgetAddressFieldsReadonly" maxlength="62" readonly="true"/>  </span> 	                    	                	      
			 	                	            	 	                    
			 	                    <span class="pL30">City</span>
			 	                    <span class="pL10"><form:input path="RX_PROV_CITY" class="standardinputSmallText1 newCity widgetAddressFieldsReadonly providerFields" maxlength="30"/></span>
			 	                    
			 	                    <span class="pL30">State </span>
			 	                    <span  class="pL10">
										<form:select path="RX_PROV_STATE" class="phoneNumberText providerFields newState widgetAddressFieldsReadonly" maxlength="2">
											<form:option value=""></form:option>
											<form:options items="${sessionScope.stateMap}"/>
										</form:select>
									</span>
			 	                    
			 	                    <span class="pL30">Zip </span>
			 	                    <span class="pL10"> 
			 	                        <form:input path="RX_PROV_ZIP" class="standardinputSmallText1 providerFields newZip widgetAddressFieldsReadonly" maxlength="5"/> - <form:input path="RX_PROV_PLUS4" class="standardinputSmallText1 providerFields" maxlength="4"/>
			 	                    </span> 
			 	                    
			 	                    <span class="pL30">County </span>
			 	                    <span class="pL10"> 
			 	                      <form:select path="RX_PROV_COUNTY"  cssClass="oAdrDlg roundedBrdr newCounty providerFields widgetAddressFieldsReadonly"> 
											<form:option value=""></form:option>
											<form:options items="${sessionScope.countyMap}"/>
										</form:select> 
			 	                    </span>    	                    
			 	                    <br>
			 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
			 	                </span>
		 	            </div>
		 	            <div class="personInfo pT10 pB10">
		 	            	<span class="pL30">Phone </span>
		                    <span class="pL10"> 
		                       <form:input path="RX_PROV_PHONE" class="standardinputSmallText providerFields" maxlength="15"/>
		                    </span>
		                     <span style="float: right;" class="mr50">
								<button value="Edit" onclick="openAddressWidget('adapDocumentProviderAddressDtls');return false;" class="displayNone editAddress">Edit</button>
							 </span>  
		 	            </div>
		 	        </div>
	 	        </div>
				<div class="divHeadingCls bGHeading" style="clear:both">
					<span class=" mL2">Primary Care Provider Details:</span>					
				</div>		
			  	<div class="mLRTB pB10">	
					<span class="fieldHolder25">
						<span class="fLW25">First Name</span>
						<span class="fLW60">  <form:input path="PCP_PROV_FNAME" class="standardinputSmallText ml10" maxlength="20"/>  </span>
					</span>
					<span class="fieldHolder25">
						<span class="fLW35">Middle Name</span> 
						<span class="fLW60"> <form:input path="PCP_PROV_MNAME" class="standardinputSmallText" maxlength="20"/> </span>
					</span>
					<span class="fieldHolder25">
						<span class="fLW35">Last Name</span>
						<span class="fLW60"><form:input path="PCP_PROV_LNAME" class="standardinputSmallText" maxlength="25"/></span>
					</span>
					<span class="fieldHolder25">
						<span class="fLW17"> NPI</span> 
						<span class="fLW60">  <form:input path="PCP_PROV_NPI" class="standardinputSmallText ml10" maxlength="12"/></span>
					</span>
				</div>
				
				<div class="b1" style="margin: 15px 5px 10px 5px">
	 	            <div class="personInfo pTRBL10">
	 	                <span class="personLabelDoc"> Address </span>
	 		                <span> 
		 		                <span class="pL10"><form:input path="PCP_PROV_ADDRESS" class="standardinputText" maxlength="62" readonly="true"/>  </span> 	                    	                	      
		 	                	            	 	                    
		 	                    <span class="pL30">City</span>
		 	                    <span class="pL10"><form:input path="PCP_PROV_CITY" class="standardinputSmallText1" maxlength="30"/></span>
		 	                    
		 	                    <span class="pL30">State </span>
		 	                    <span  class="pL10">
									<form:input path="PCP_PROV_STATE" class="phoneNumberText " maxlength="2"/>
								</span>
		 	                    
		 	                    <span class="pL30">Zip </span>
		 	                    <span class="pL10"> 
		 	                        <form:input path="PCP_PROV_ZIP" class="standardinputSmallText1" maxlength="5"/> - <form:input path="PCP_PROV_PLUS4" class="standardinputSmallText1" maxlength="4"/>
		 	                    </span> 
		 	                    
		 	                    <span class="pL30">County </span>
		 	                    <span class="pL10"> 
		 	                      <form:select path="PCP_PROV_COUNTY"  cssClass="oAdrDlg roundedBrdr newCounty"> 
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
	                       <form:input path="PCP_PROV_PHONE" class="standardinputSmallText" maxlength="15"/>
	                    </span>
	 	            </div>
	 	        </div>
			</c:otherwise>
		</c:choose>		
			
			
				
			<div class="divHeadingCls bGHeading" style="clear:both">
				<span class=" mL2">Risk Details:</span>					
			</div>
			<div style="width:99.9%; border:1px dotted;" class="container">	
				<div style="float:left; width:49% ; padding-bottom:5px; border-right:1px dotted;"> 
					<div style="float:left; width:60%">
						<span class="mL20  "> Sex With Male </span>
					</div>
					<div style="float:left; width:39%">
						<span class="mL5"> <form:radiobutton path="SWM" value="Y" /> <spring:message code="label.yes" /> </span>
						<span> <form:radiobutton path="SWM" value="N" /> <spring:message code="label.no" /> </span>	
						<span> <form:radiobutton path="SWM" value="U" /> <spring:message code="label.unknown" /> </span>	
					</div>	
				</div>	
				<div style="float:left; width:49% ; padding-bottom:5px;"> 
					<div style="float:left; width:60%">
						<span class="mL20  "> IDU </span>
					</div>
					<div style="float:left; width:39%">
						<span class="mL5"> <form:radiobutton path="IDU" value="Y" /> <spring:message code="label.yes" /> </span>
						<span> <form:radiobutton path="IDU" value="N" /> <spring:message code="label.no" /> </span>	
						<span> <form:radiobutton path="IDU" value="U" /> <spring:message code="label.unknown" /> </span>	
					</div>	
				</div>	
						
			</div>
			<div style="width:99.9%; border:1px dotted;" class="container">
				<div style="float:left; width:49% ; padding-bottom:5px; border-right:1px dotted;"> 
					<div style="float:left; width:60%">
						<span class="mL20  "> Sex With Female </span>
					</div>	
					<div style="float:left; width:39%">	
						<span class="mL5"> <form:radiobutton path="SWF" value="Y" /> <spring:message code="label.yes" /> </span>
						<span> <form:radiobutton path="SWF" value="N" /> <spring:message code="label.no" /> </span>	
						<span> <form:radiobutton path="SWF" value="U" /> <spring:message code="label.unknown" /> </span>	
					</div>	
				</div>
				<div style="float:left; width:49% ; padding-bottom:5px; "> 
					<div style="float:left; width:60%">
						<span class="mL20  "> Health Care Worker </span>
					</div>
					<div style="float:left; width:39%">
						<span class="mL5"> <form:radiobutton path="HEALTHCARE_WORKER" value="Y" /> <spring:message code="label.yes" /> </span>
						<span> <form:radiobutton path="HEALTHCARE_WORKER" value="N" /> <spring:message code="label.no" /> </span>	
						<span> <form:radiobutton path="HEALTHCARE_WORKER" value="U" /> <spring:message code="label.unknown" /> </span>	
					</div>	
				</div>				
			</div>
			<div style="width:99.9%; border:1px dotted;" class="container">
				<div style="float:left; width:49% ; padding-bottom:5px;border-right:1px dotted;"> 
					<div style="float:left; width:60%">
						<span class="mL20  "> Sex Partner - HIV </span>
					</div>	
					<div style="float:left; width:39%">	
						<span class="mL5"> <form:radiobutton path="SEX_PARTNER_HIV" value="Y" /> <spring:message code="label.yes" /> </span>
						<span> <form:radiobutton path="SEX_PARTNER_HIV" value="N" /> <spring:message code="label.no" /> </span>	
						<span> <form:radiobutton path="SEX_PARTNER_HIV" value="U" /> <spring:message code="label.unknown" /> </span>	
					</div>	
				</div>
				<div style="float:left; width:49% ; padding-bottom:5px;"> 
					<div style="float:left; width:60%">
						<span class="mL20  "> Perinatal Exposure </span>
					</div>	
					<div style="float:left; width:39%">	
						<span class="mL5"> <form:radiobutton path="PERINATAL" value="Y" /> <spring:message code="label.yes" /> </span>
						<span> <form:radiobutton path="PERINATAL" value="N" /> <spring:message code="label.no" /> </span>	
						<span> <form:radiobutton path="PERINATAL" value="U" /> <spring:message code="label.unknown" /> </span>	
					</div>	
				</div>					
			</div>
			<div style="width:99.9%; border:1px dotted;" class="container">
				<div style="float:left; width:49% ; padding-bottom:5px; border-right:1px dotted;"> 
					<div style="float:left; width:60%">
						<span class="mL20  "> Sex Partner - IDU</span>
					</div>	
					<div style="float:left; width:39%">	
						<span class="mL5"> <form:radiobutton path="SEX_PARTNER_IDU" value="Y" /> <spring:message code="label.yes" /> </span>
						<span> <form:radiobutton path="SEX_PARTNER_IDU" value="N" /> <spring:message code="label.no" /> </span>	
						<span> <form:radiobutton path="SEX_PARTNER_IDU" value="U" /> <spring:message code="label.unknown" /> </span>	
					</div>	
				</div>	
				<div style="float:left; width:49% ; padding-bottom:5px;"> 
					<div style="float:left; width:60%">
						<span class="mL20  "> Other Risk</span>
					</div>
					<div style="float:left; width:39%">
						<span class="mL5"> <form:radiobutton path="RISK_OTHER" value="Y" /> <spring:message code="label.yes" /> </span>
						<span> <form:radiobutton path="RISK_OTHER" value="N" /> <spring:message code="label.no" /> </span>	
						<span> <form:radiobutton path="RISK_OTHER" value="U" /> <spring:message code="label.unknown" /> </span>	
					</div>	
				</div>									
			</div>
			<div style="width:99.9%; border:1px dotted;" class="container">
				<div style="float:left; width:49% ; padding-bottom:5px; border-right:1px dotted;"> 
					<div style="float:left; width:60%">
						<span class="mL20  "> Sex Partner - Transfusion Recipient with HIV</span>
					</div>
					<div style="float:left; width:39%">
						<span class="mL5"> <form:radiobutton path="RECV_BLOOD_PROD" value="Y" /> <spring:message code="label.yes" /> </span>
						<span> <form:radiobutton path="RECV_BLOOD_PROD" value="N" /> <spring:message code="label.no" /> </span>	
						<span> <form:radiobutton path="RECV_BLOOD_PROD" value="U" /> <spring:message code="label.unknown" /> </span>	
					</div>	
				</div>	
			</div>		
		</div>	
	</div>
	<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
	 <div id="button-row" class="button-row documentButtons center">
	 				<c:if  test="${userPermissions['99'] != null && stdADAPDocVO.MATCHED_STATENO != null && stdADAPDocVO.MATCHED_STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}" >	
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
						<c:when test="${userPermissions['90'] != null && stdADAPDocVO.MATCHED_STATENO != null && stdADAPDocVO.MATCHED_STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
								<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
						</c:when>
					</c:choose>
					
				<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Save" onclick="saveForm()"/>	 
				<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />				
	</div>		
	
	<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />															
</form:form>


