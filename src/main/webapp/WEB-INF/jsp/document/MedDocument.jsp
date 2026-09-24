<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>
<script src="${staticPath}/js/docs/medDoc.js?v=${buildDate}"></script>

<link href="${staticPath}/css/docs/prfForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/docs/nycijrDoc.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/docs/adapForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />

<form:form modelAttribute="stdMedicaidDocVO" id="stdMedicaidDocVO" cssClass="documentDescription">
<form:errors path="*" cssClass="errorblock" element="div" />

<div id="MedOldDoc">
    <div class="headingCls centerDoc">
    Insurance Document
    </div>
    <div style="padding-bottom:10px; text-align: right">
           <span class="mL10">Document ID: </span>
		<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
       </div>
	<div class="container w100 pB10">
        <div style="float:left; width: 40%">
           <div>
               <span class=" ml10"><spring:message code="label.stateNo" />: </span>
                <span class="ml10"><form:input path="STATENO" class="standardInputSmallText" maxlength="10" readonly="true"/> </span>
           </div>    
         </div>
         <div style="float:left; width: 30%">
           <div>
               <span class=" ml10">	eHARS Import Date: </span>
               <fmt:formatDate value="${stdMedicaidDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
	 		<span class="ml10"><form:input path="EHARS_IMPORT_DATE" value="${theFormattedDate}" class="standardInputSmallText ml10" readonly="true"/> </span>
          </div>                  
        </div>
        <div style="float:left; width: 30%; text-align: right">
        	<div>
               <span class="mL10">NYC SHIP Date: </span>
               <fmt:formatDate value="${stdMedicaidDocVO.NYC_SHIP_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedNYC_SHIP_DATE_TEXT" ></fmt:formatDate>
	 		<span class="ml10 "><form:input path="NYC_SHIP_DATE" value="${theFormattedNYC_SHIP_DATE_TEXT}" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
           </div> 
        </div>     
	</div>
	<jsp:include page="statenoDetails.jsp"></jsp:include>
	
	
    <div class="container" id="patientInformation" style="margin-bottom: 10px; border: dotted 1px">  
        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
            <span class=" mL2">Patient Information</span>
        </div>
        <div class="mLRTB">
			<span class="">
				<span class=""> First Name</span> 
				<span class="pL10"><form:input path="FNAME" class="standardInputMedText" maxlength="13" /></span>
			</span>
			<span class=" ">
				<span class="pL30"> Last Name</span>
				<span class="pL10"><form:input path="LNAME" id="lName" title="Last Name" class="standardInputMedText" maxlength="15" /></span>
			</span>					
			<span class=""> 
				<span class="pL30"> Middle Name</span>
				<span class="pL10"> <form:input path="MNAME" id="mname" class=" standardInputMedText" maxlength="10" /> </span>
			</span>
		</div>
        
        <div class="pB10">           
            <span class="pL10">DOB </span>
            <span> 
	 	        <span class="pL10"><form:input path="DOB_TEXT" class="standardInputSmallText" maxLength="9" /> </span>  	 
            </span>
            <span class="pL30">DOD</span>
            <span class="pL10"><form:input path="DOD_TEXT" class="standardInputSmallText" maxLength="9" /> </span>
           
            <span class="pL30">SSN</span>
            <span class="pL10"><form:input path="SSN" class="standardInputSmallText" maxLength="9" /> </span>
        </div> 
        
        <div class="b1" style="margin: 15px 5px 10px 5px">
            <div class="personInfo pTRBL10">
                <span class="personLabelDoc"> Address </span>
	                <span> 	             
	                	<span class="pL10"><form:input path="raw_address" class="standardinputText" maxlength="62" readonly="true"/>  </span>  	 
	                    
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
	                   		 <form:select path="PERSON_COUNTY"  cssClass="oAdrDlg newCounty standardInputSmallText1 roundedBrdr" disabled="true"> 
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
              	 
                <br>               
                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
                    
            </div>
        </div>
                
        <div id="raceEthnicity" class="container" style="border-top:1px dotted; padding:10px 0 5px 0;">
			<div style="float:left; width:35%; margin-left:20px; border-right:1px dotted;">			
				<div> Race </div> 
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
						<form:radiobutton path="ETHNICITY" value="01" /> <spring:message code="label.yes" /><br>
						<form:radiobutton path="ETHNICITY" value="02" /> <spring:message code="label.no" /><br>
						<form:radiobutton path="ETHNICITY" value="09" /> <spring:message code="label.unknown" />
					</div>
				</div>	
				<div style="padding:15px 0 10px 10px; border-top: dotted 1px; ">
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
						<form:input path="MEDICAID_NUMBER" class="standardInputMedText mL5" maxlength="10"/>
					</div>
				</div>	
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Vital Status
					</div>
					<div style="float:left">
						<form:input path="VITAL_STATUS" class="standardInputMedText mL5" maxlength="40"/>
					</div>
				</div>					
			</div>
		</div>
       

        <div class="divHeadingCls bGHeading clearBoth">
            <span class=" mL2"> Event Criteria:</span>    
            <span class="criteriaInfo"> </span>                             
        </div>
        
        
        
        <div class="eventCriteriaFields" style="border-bottom: 1px dotted; height: 65px">
            <div class="" style="width:18%; float:left; border-right:1px dotted; padding:15px 0 10px 20px;">
                <div >           
                        <div><span><form:checkbox path="CRITERIA_1" value="1" /> Criteria 1 </span></div>
                        <div><span><form:checkbox path="CRITERIA_2" value="1" /> Criteria 2 </span></div>
                </div>
            </div>
             <div class="" style="width:18%; float:left; border-right:1px dotted; padding:15px 0 10px 20px;">
                <div >   
                        <div><span><form:checkbox path="CRITERIA_3" value="1" /> Criteria 3 </span></div>
                        <div><span><form:checkbox path="CRITERIA_4" value="1" /> Criteria 4 </span></div>
                </div>
            </div>
             <div class="" style="width:18%; float:left; border-right:1px dotted; padding:15px 0 10px 20px;">
                <div >           
                    <div><span><form:checkbox path="CRITERIA_5" value="1" /> Criteria 5 </span></div>
                    <div><span><form:checkbox path="CRITERIA_6" value="1" /> Criteria 6 </span></div>
                </div>
            </div>
            <div class="" style="width:18%; float:left;border-right:1px dotted;  padding:15px 0 10px 20px">
                <div >                        
                    <div><span><form:checkbox path="CRITERIA_7" value="1" /> Criteria 7 </span></div>
                    <div><span><form:checkbox path="CRITERIA_8" value="1" /> Criteria 8 </span></div>
                </div> 
            </div>
            <div class="" style="width:18%; float:left; padding:15px 0 10px 20px">
                <div >                        
                    <div><span><form:checkbox path="CRITERIA_9" value="1" /> Criteria 9 </span></div>
                    <div><span><form:checkbox path="CRITERIA_10" value="1" /> Criteria 10 </span></div>
                </div> 
            </div>
            
             <div id="criteriaPopUp" style="display:none">
             </div>
            	<div id="criteriaContent" class="displayNone">
	            <div class="pL20" style="padding-top:15px;" >
	            	<div  style="font-weight: bold; margin: 10px 0">
	            		A recepient is considered HIV/AIDS if <b><u>any</u></b> of the following are true any time within the entire FFY.
	            	</div>
	            	
	            	<table>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 1:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText">One or more claims with a CRG HIV/AIDS Dx code(042,043,044,V08,795.8,795.71,079.53) </span>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 2:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText">One or more inpatient claims with DRGs 700-716, plus 890,892,894 (HIV related) </span>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 3:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText">One or more claims with HIV/AIDS inpatient rate codes 2908, 2964, 2965</span>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 4:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText">One or more claims with 044.x or 044.9 Dx (HIV infection) plus one or more claims with an AIDS defining condition</span>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 5:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText">One or more claims with an AIDS HHC, LTC or hospice rate code</span>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 6:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText">One or more instances of outpatient rate codes(2940,2941,2945,2985,1697)</span>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 7:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText">One claim for an ARV plus any of the following: </span>
	            				<ol type="a" class="criteriaText mL30">
            						<li>procedure code for viral load testing</li>
            						<li>procedure code for CD4 testing</li>
            						<li>rate code for a monitoring visit (2987,1699)</li>
            						<li>procedure code for genotype/phenotype testing</li>
            						<li>pharmacy claim for and an AIDS related conditions - CMV treatment, Crypto treatment, HIV wasting</li>
            					</ol>
	            				
	            				
	            			</td>
	            		</tr>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 8:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText">One or more claims with a HIV Dx code (647.6,795.71,795.8,279.10,279.3,279.19,V08.x) and one or more claims with a Dx code for an AIDS defining conditions (OIs)</span>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 9:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText">One or more claims with a HIV Dx code (647.6,795.71,795.8,279.10,279.3,279.19,V08.x) with any of the following:</span>
	            				<ol type="a" class="criteriaText mL30">
	            						<li>one or more pharmacy claims for an ARV</li>
	            						<li>one or more procedure codes for ARV administration</li>
	            						<li>one or more claims with an "outpatient" rate code (with COBRA/HHL recipients)</li>
	            						<li>one or more claims with an HIV/AIDS provider specialty code(249,303,308,355)</li>
	            				</ol>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 10:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText"> HIV-positive individuals enrolled in an HIV SNP for 3 months of the year (rate codes 2272-2279)</span>
	            			</td>
	            		</tr>
	            	</table>  
	            </div> 
	           </div>
          
        </div>     
        
        <div class="arvsAccordion" style="margin: 20px;">
        	<c:forEach items="${stdMedicaidDocVO.medicaidDocArvsVO}" var="medicaidDocArvsVO" varStatus="statusHNT">
		        <h3 class="divHeadingCls bGHeading clearBoth">
		            <span style="left: 2em;top: 50%;margin-top: -11px;position: absolute;"> ARVs:</span>                                 
		        </h3>
		        <div class="arvDetails">
			         <div class="mLRTB pB10">            
			            <span class="pL10">CIN</span>
			            <span class="pL10"> <form:input path="medicaidDocArvsVO[${statusHNT.index}].CIN" class="standardinputMedText ml10" maxlength="20"/>  </span>
			            
			            <span class="pL10">Prescribed ARVs</span>
			            <span class="pL10"> <form:input path="medicaidDocArvsVO[${statusHNT.index}].PRESCRIBED_ARVS" class="standardinputMedText ml10" maxlength="20"/>  </span>
			            <span class="pL10">PLAN TYPE</span>
			            <span class="pL10"> <form:input path="medicaidDocArvsVO[${statusHNT.index}].PLAN_TYPE" class="standardinputMedText ml10" maxlength="20"/>  </span>
			        </div>
			        <div class="mLRTB pB10">            
			            <span class="pL10">ARV MONTH</span>
			            <span class="pL10"> <form:input path="medicaidDocArvsVO[${statusHNT.index}].ARV_MOS" class="standardinputMedText ml10" maxlength="20"/>  </span>
			            
			            <span class="pL10">ARV YEAR</span>
			            <span class="pL10"> <form:input path="medicaidDocArvsVO[${statusHNT.index}].ARV_YOS" class="standardinputMedText ml10" maxlength="20"/>  </span>
			            <span class="pL10">MEDICAID FILE</span>
			            <span class="pL10"> <form:input path="medicaidDocArvsVO[${statusHNT.index}].MEDICAID_FILE" class="standardinputMedText ml10" maxlength="20"/>  </span>
			        </div>
			    </div>
			 </c:forEach>
	    </div> 
        
        <div class="providerAccordion" style="margin: 20px;">
        	<c:forEach items="${stdMedicaidDocVO.medicaidDocProvidersVO}" var="medicaidDocProvidersVO" varStatus="statusHNT">
		        <h3 class="divHeadingCls bGHeading clearBoth">
		            <span style="left: 2em;top: 50%;margin-top: -11px;position: absolute;"> Provider Details:</span>                                 
		        </h3>
		        <div class="medicaidProviderSection providerDetails">
			         <div class="mLRTB pB10 ">            
			            <span class="pL10">Name</span>
			            <span class="pL10">  <form:input path="medicaidDocProvidersVO[${statusHNT.index}].PROV_NAME" id="PROV_NAME" class="standardinputMedText ml10 providerFields" maxlength="80"/>  </span>
			            
			            <span class="pL10">NPI</span>
			            <span class="pL10">  <form:input path="medicaidDocProvidersVO[${statusHNT.index}].NPI" id="NPI" class="standardinputMedText ml10 providerFields" maxlength="20"/>  </span>
			            <span class="pL10">CIN</span>
			            <span class="pL10">  <form:input path="medicaidDocProvidersVO[${statusHNT.index}].MEDICAID_NUMBER" id="MEDICAID_NUMBER" class="standardinputMedText ml10" maxlength="20"/>  </span>
			        </div>
			        <div class="mLRTB pB10">            
			            <span class="pL10">SERVICE DATE</span>
			            <fmt:formatDate value="${stdMedicaidDocVO.medicaidDocProvidersVO[statusHNT.index].SERVICE_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedSERVICE_DATE" ></fmt:formatDate>
			            <span class="pL10">  <form:input path="medicaidDocProvidersVO[${statusHNT.index}].SERVICE_DATE" value="${theFormattedSERVICE_DATE}" class="standardinputMedText ml10" maxlength="20"/>  </span>
			            
			            <span class="pL10">PRIMARY PROVIDER</span>
			            <span class="pL10">  
			            	<span> <form:radiobutton path="medicaidDocProvidersVO[${statusHNT.index}].PRIMARY_IND" value="Y" /> Yes </span>	
			            	<span> <form:radiobutton path="medicaidDocProvidersVO[${statusHNT.index}].PRIMARY_IND" value="N" /> No </span> 
			            </span>
			            
			            
			            <span class="pL10">CATEGORY OF SERVICE</span>
			            <span class="pL10">  
			            	    <form:input path="medicaidDocProvidersVO[${statusHNT.index}].PROV_COS_CODE_DESC" class="ml10 roundedBrdr" maxlength="20" title="Category Service code" />
			              </span>
			           
			        </div>
			         <div class="b1 " style="margin: 15px 5px 10px 5px">
			            <div class="personInfo pTRBL10" id="medDocumentProviderAddressDtls">
			                <span class="personLabelDoc"> Address </span>
				                <span> 	             
				                	<span class="pL10"><form:input path="medicaidDocProvidersVO[${statusHNT.index}].PROV_ADDRESS" id="PROV_ADDRESS" class="standardinputText providerFields newAddress widgetAddressFieldsReadonly" maxlength="62" readonly="true"/>  </span>  	 
				                    
				                    <span class="pL30">City</span>
				                    <span class="pL10"><form:input path="medicaidDocProvidersVO[${statusHNT.index}].PROV_CITY" id="PROV_CITY" class="standardinputSmallText1 providerFields newCity widgetAddressFieldsReadonly" maxlength="17"/></span>
				                    
				                    <span class="pL30">State </span>
				                    <span  class="pL10">
				                    	<form:select path="medicaidDocProvidersVO[${statusHNT.index}].PROV_STATE" id="PROV_STATE" class="standardinputSmallText1 providerFields newState widgetAddressFieldsReadonly" maxlength="2">
				                    				<form:option value=""></form:option>
													<form:options items="${sessionScope.stateMap}"/>
										</form:select>
				                    	
				                    </span>
				                    
				                    <span class="pL30">Zip </span>
				                    <span class="pL10"> 
				                       <form:input path="medicaidDocProvidersVO[${statusHNT.index}].PROV_ZIP" id="PROV_ZIP" class="standardInputSmallText1 providerFields newZip widgetAddressFieldsReadonly" maxlength="5"/>   
				                    </span>    
				                    
				                    <span class="pL30">County </span>
				                    <span class="pL10">
				                    	<form:select path="medicaidDocProvidersVO[${statusHNT.index}].PROV_COUNTY" id="PROV_COUNTY"  cssClass="oAdrDlg roundedBrdr newCounty standardInputSmallText1 providerFields widgetAddressFieldsReadonly" disabled="true"> 
											<form:option value=""></form:option>
											<form:options items="${sessionScope.countyMap}"/>
										</form:select>
									</span> 
									 <span style="float: right;" class="mr5">
										<button value="Edit" onclick="openAddressWidget('medDocumentProviderAddressDtls');return false;" class="displayNone editAddress">Edit</button>
									 </span>	                    
				                    <br>
				                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
				                </span>
			            </div>
			        </div>
			    </div>
		    </c:forEach>
	    </div> 
	</div>
</div>
 <div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
		 <div id="button-row" class="button-row documentButtons center">
		 				
		 				
						<c:if  test="${userPermissions['99'] != null && stdMedicaidDocVO.STATENO != null && stdMedicaidDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}" >	
							<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3 displayNone"   value="Trigger Assignment"  onclick="loadSurvAssignmentPopUp()"/>
						</c:if>
					
						<c:if test="${param.hasEditFunctionality == true}">
							<input type="button" id="eButton"  class="editBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="customDocumentEditViewerStyles()" />
						</c:if>
						<c:choose>
						<c:when  test="${param.hasDeleteFunctionality == true}" >	
								<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
								<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
						</c:when>
						<c:when test="${userPermissions['90'] != null && stdMedicaidDocVO.STATENO != null && stdMedicaidDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
								<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
						</c:when>
					</c:choose>

												
					<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Save" onclick="saveForm()"/>	 
					<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />				
		</div>	
        <input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
</form:form>
