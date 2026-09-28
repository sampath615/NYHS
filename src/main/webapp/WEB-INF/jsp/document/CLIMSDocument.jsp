<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${staticPath}/js/docs/airsDoc.js?v=${buildDate}"></script>
<link href="${staticPath}/css/docs/airsForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/docs/commonForm.css" rel="stylesheet" type="text/css" />

<form:form modelAttribute="stdCLIMSDocVO" id="stdCLIMSDocVO" cssClass="documentFormVO documentDescription">
<form:errors path="*" cssClass="errorblock" element="div" />

        <div id="labDocTab" style="background-color: transparent">
                
                <div style="padding-left:43%; " class="headingCls">
                                CLIMS &nbsp;  DOCUMENT
                </div>
                
                
                <div class="pB30 pT20">
		
					<div  class="floatRight">
			            <span class="mL10 bold">Document ID: </span>
						<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
			         </div>	
		
				</div>

				<div class="container w100 nonRawEditFields pB10">
		 	         <div style="float:left; width: 40%">
		 	            <div >
		 	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
		 	                 <span class="ml10"><form:input path="STATENO" class="standardInputSmallText readonly" maxlength="10" readonly="true"/> </span>
		 	            </div>    
		 	          </div>
		 	          <div style="float:left; width: 30%">
		 	         	<div >
		 	                <span class="ml10"> eHARS Import Date: </span>
		 	                <fmt:formatDate value="${stdCLIMSDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
		 			 		<span class="ml10"><form:input path="EHARS_IMPORT_DATE" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/> </span>
		 	            </div>    
		 	         </div>
		 	         <div style="float:left; width: 30%; text-align: right">
		 	            <div >
		 	                <span class="mL10">NYC SHIP Date: </span>
		 	                <fmt:formatDate value="${stdCLIMSDocVO.NYC_SHIP_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedNYC_SHIP_DATE_TEXT" ></fmt:formatDate>
		 			 		<span class="ml10"><form:input path="NYC_SHIP_DATE" value="${theFormattedNYC_SHIP_DATE_TEXT}" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
		 	            </div> 
		 	         </div>     
		 		</div>	 
		 		
		 		
                <!--  Person details -->

                <div class="container" id="patientInformation">  
                        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Patient Information</span>
                        </div>
                        <div style="padding-left:10px;padding-top:10px">
                                <div class="personInfo">
                                			<div class="fieldHolder25">
	                                            <span class="pL10">First Name </span> 
	                                            <span class="pL5"><form:input path="FIRSTNAME" id="CLIMS_FIRSTNAME" class="standardInputMedText  readonly required" title="First Name" maxLength="20"/></span>
	                                            <div class="pL10 v_error AIRS_FNAME "></div> 
	                                        </div>
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Last Name </span> 
                                            	<span  class="pL5"><form:input path="LASTNAME" id="CLIMS_LASTNAME" class="standardInputMedText readonly"  title="Last Name" maxLength="25"/></span>
                                            	<div class="pL10 v_error AIRS_MNAME "></div>
                                            </div>
                               			<div class="fieldHolder25">
                                       		<span class="pL10">Date of Birth </span>
                                   			<fmt:formatDate value="${stdCLIMSDocVO.DOB}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDOBDate" />
                                   			<span class="ml10"><form:input path="DOB" value= "${theFormattedDOBDate}" class="standardInputSmallText ml10 readonly" readonly="true"/> </span>
                                       		<div class="pL10 v_error DATE_OF_BIRTH "></div>
                                       </div>
                                      	<div class="fieldHolder25">
                                        	<span class="pL10">Age</span>
                                        	<span class="pL10"><form:input path="AGE" class="standardInputSmallText nOnly readonly" title="Age" maxLength="3"/>&nbsp;&nbsp;</span>
                                        </div>
                                        <br><br>
                                        
                                        
                                </div>
                                
                                <div class="b1" style="margin: 15px 5px 10px 0px" id="airsDocumentPersonAddressDtls">
					 	            <div class="personInfo pTRBL10">
				 		                <span> 	                	
					 	                    <span class="pL30">Zip </span>
					 	                    <span class="pL10"> 
					 	                       <form:input path="PATZIP" id="CLIMSZip" class="standardInputSmallText1 widgetAddressFieldsReadonly newZip readonly" title="Zipcode" maxLength="5"/>
					 	                    </span> 
					 	                    
					 	                    <span class="pL30">County </span>
					 	                    <span class="pL10"> 
					 	                    	<form:select path="COUNTYCODE"  cssClass="oAdrDlg newCounty roundedBrdr widgetAddressFieldsReadonly newCounty readonly" title="County" maxLength="3"> 
													<form:option value=""></form:option>
													<form:options items="${sessionScope.countyMap}"/>
												 </form:select>
					 	                    </span>     	                    
					 	                    <br><br>
					 	                </span>
					 	            </div>
					 	        </div>                               
                        </div>        
                </div>

                <div class="raceRiskReprt">
                        <div class="fieldHolder25" style="width: 24%; border: 1px dotted; height: 402px">
                                <div style="padding:10px ">      
                                        <div> Race </div>
                                        <div>   <form:checkbox path="RACE_WHITE"  value="1"/> White </div>
                                        <div>   <form:checkbox path="RACE_BLACK" value="1"/> African American</div>
                                        <div>   <form:checkbox path="RACE_ASIAN" value="1"/> Asian</div>
                                        <div>   <form:checkbox path="RACE_INDIAN" value="1"/> American Indian/Alaskan Native</div>
                                        <div>   <form:checkbox path="RACE_PACIFIC" value="1"/> Pacific Islander</div> 
                                        <div>   <form:checkbox path="RACE_OTHER" value="1"/> Other 
                                        </div>
                                        <div>   <form:checkbox path="RACE_UNKNOWN" value="1"/> Unknown</div> 
                                        <br>           
                                </div>  
                                <div style="padding:15px 0 10px 10px; border-top: dotted 1px; ">   
                                        <div>
                                                Ethnicity (Check one) Hispanic or Latino<br>
                                                <form:radiobutton path="ETHNICITY" value="01" /> Yes
                                                <form:radiobutton path="ETHNICITY" value="02" /> No
                                                <form:radiobutton path="ETHNICITY" value="09" /> Unknown
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
                                <br> 
                        </div>
                        <div class="fieldHolder75">
                                                <!--  Risk details -->
                                <div class="riskHW borderField pT10" style="height:382px">
                                        <div class="mT5 container">
											<div style="float:left; width:25%">
												ACCID
											</div>
											<div style="float:left">
												<form:input path="ACCID" class="standardInputMedText mL5 readonly" title="ACCID" maxlength="30"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Final Approval Date
											</div>
											<div style="float:left">
											<fmt:formatDate value="${stdCLIMSDocVO.FINALAPPROVALDT}"  type="date" pattern="MM/dd/yyyy" var="theFormattedFINALAPPROVALDT" />
                                   			<form:input path="FINALAPPROVALDT" value= "${theFormattedFINALAPPROVALDT}" class="standardInputMedText mL5 readonly" datevalidations="completeDate" title="Final Approval Date"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Initial PCR
											</div>
											<div style="float:left">
												<form:input path="INITIALPCR" title="Initial PCR" class="standardInputMedText mL5 readonly" maxlength="1"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Follow Up PCR
											</div>
											<div style="float:left">
												<form:input path="FOLLOWUPPCR" title="Follow Up PCR" class="standardInputMedText mL5 readonly" maxlength="1"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Records ID
											</div>
											<div style="float:left">
												<form:input path="RECID" class="standardInputMedText mL5 nOnly readonly" maxlength="10" title="Records ID"/>
											</div>
										</div>
										
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Patient ID
											</div>
											<div style="float:left">
												<form:input path="PATIENTID" class="standardInputMedText mL5 nOnly readonly" maxlength="10" title="Patient ID"/>
											</div>
										</div>
										
										
										<div class="mT5 container">
											<div style="float:left; width:25%">
												NBS Code
											</div>
											<div style="float:left">
												<form:input path="NBSCODE" class="standardInputMedText mL5 nOnly  readonly" maxlength="10" title="NBS Code"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												NBS Lab ID
											</div>
											<div style="float:left">
												<form:input path="NBSLABID" class="standardInputMedText mL5 readonly" maxlength="30" title="NBS Lab ID"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Possible Medical Record #
											</div>
											<div style="float:left">
												<form:input path="PATCODE" class="standardInputMedText mL5 readonly" maxlength="30" title="Patient Code"/>
											</div>
										</div>	               
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Collection Date
											</div>
											<div style="float:left">
											<fmt:formatDate value="${stdCLIMSDocVO.COLLDATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedCOLLDATE" />
                                   			<form:input path="COLLDATE" value= "${theFormattedCOLLDATE}" class="standardInputMedText mL5 readonly" datevalidations="completeDate" title="Collection Date"/>										
											</div>
										</div>	               
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Gestation
											</div>
											<div style="float:left">
												<form:input path="GESTATION" class="standardInputMedText mL5 nOnly readonly" maxlength="10" title="Gestation"/>
											</div>
										</div>	               
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Birth Facility
											</div>
											<div style="float:left">
												<form:input path="BIRTH_FACILITY" class="standardInputMedText mL5 readonly" maxlength="20" title="Birth Facility"/>
											</div>
										</div>	               
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Foster Care
											</div>
											<div style="float:left">
												<form:input path="FOSTERCARE" class="standardInputMedText mL5 readonly" maxlength="1" title="Foster Care"/>
											</div>
										</div>	               
										<div class="mT5 container">
											<div style="float:left; width:25%">
												MAV
											</div>
											<div style="float:left">
												<form:input path="MAV" class="standardInputMedText mL5 readonly" maxlength="1" title="MAV"/>
											</div>
										</div>	               
										<div class="mT5 container">
											<div style="float:left; width:25%">
												MAV When
											</div>
											<div style="float:left">
												<form:input path="MAVWHEN" class="standardInputMedText mL5 readonly" maxlength="30" title="MAV When"/>
											</div>
										</div>	                            
                                </div>
                                <div class="reportHW borderField pT10" style="height:382px">   
										<div class="mT5 container">
											<div style="float:left; width:25%">
												IPAV
											</div>
											<div style="float:left">
												<form:input path="IPAV" class="standardInputMedText mL5 readonly" maxlength="1" title="IPAV"/>
											</div>
										</div>	               
										<div class="mT5 container">
											<div style="float:left; width:25%">
												ITAV
											</div>
											<div style="float:left">
												<form:input path="ITAV" class="standardInputMedText mL5 readonly" maxlength="1" title="ITAV"/>
											</div>
										</div>	                                  
                                		 <div class="mT5 container">
											<div style="float:left; width:25%">
												Weight
											</div>
											<div style="float:left">
												<form:input path="WEIGHT" class="standardInputMedText mL5 readonly" title="Weight" maxlength="10"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Submitter First Name
											</div>
											<div style="float:left">
												<form:input path="SUBMFNAME" class="standardInputMedText mL5 readonly" title="Submitter First Name" maxlength="100" />
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Submitter Last Name
											</div>
											<div style="float:left">
												<form:input path="SUBMLNAME" title="Submitter Last Name" class="standardInputMedText mL5 readonly" maxlength="100"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Submitter ID
											</div>
											<div style="float:left">
												<form:input path="SUBMITTERID" title="Submitter ID" class="standardInputMedText mL5 readonly" maxlength="50"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Multispot
											</div>
											<div style="float:left">
												<form:input path="MULTISPOT" class="standardInputMedText mL5 readonly" maxlength="50" title="Multispot"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												HIV2QUAL
											</div>
											<div style="float:left">
												<form:input path="HIV2QUAL" class="standardInputMedText mL5 readonly" maxlength="30" title="HIV2QUAL"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Results
											</div>
											<div style="float:left">
												<form:input path="TMA" class="standardInputMedText mL5 readonly" maxlength="30" title="TMA"/>
											</div>
										</div>	               
										<div class="mT5 container">
											<div style="float:left; width:25%">
												MEMO
											</div>
											<div style="float:left">
												<form:input path="MEMO" id="MEMO" class="standardInputMedText mL5 dPicker mask readonly" maxlength="40" title="MEMO"/>
											</div>
										</div>	               
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Geenius
											</div>
											<div style="float:left">
												<form:input path="GEENIUS" class="standardInputMedText mL5 nOnly readonly" maxlength="30" title="Geenius"/>
											</div>
										</div>	               

      
                                </div>
                        </div>
                          
                </div>

                <!--  Provider details  -->     
                <div id="personHIVDetails" class="container facilityDetails">   
                        <div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Facility Information</span>                                  
                        </div>
                        <!--  Provider details  -->
                        <div class="pL10 pT10">
                                <span class="">
                                        <span class="pL10">Name</span>
                                        <span class="pL10"><form:input path="FACILITY_NAME" class="standardInputText facilityFields readonly" title="Facility Name" maxlength="80"/></span> 
                                </span>    
                               
                        </div><br>
                        
                        <div class="b1" style="margin: 15px 5px 10px 0px">
			 	            <div class="personInfo pTRBL10" id="airsDocumentFacilityAddressDtls">
			 	                <span class="personLabelDoc"> Address </span>
		 		                <span> 	                	
			 	                	<span class="pL10"> <form:input path="FACILITY_ADDRESS" class="standardInputText facilityFields newAddress widgetAddressFieldsReadonly" title="Address" maxLength="62"/>  </span>	 	        
			 	                	            	 	                    
			 	                    <span class="pL20">City</span>
			 	                    <span class="pL10"><form:input path="FACILITY_CITY" class="standardInputSmallText facilityFields widgetAddressFieldsReadonly newCity" title="City" maxLength="30"/></span>
			 	                    
			 	                    <span class="pL20">State </span>
			 	                    <span  class="pL10">
										<form:select path="FACILITY_STATE"  class="standardInputSmallText1 facilityFields widgetAddressFieldsReadonly newState" title="State" maxLength="2">
													<form:option value=""></form:option>
													<form:options items="${sessionScope.stateMap}"/>
										</form:select>
									</span>
			 	                    
			 	                    <span class="pL20">Zip </span>
			 	                    <span class="pL10"> 
			 	                        <form:input path="FACILITY_ZIP" class="standardInputSmallText1 facilityFields widgetAddressFieldsReadonly newZip" title="Zipcode" maxLength="5"/>
			 	                    </span> 
			 	                    
			 	                    <span class="pL20">County </span>
			 	                    <span class="pL10"> 
			 	                    	<form:select path="FACILITY_COUNTY"  cssClass="oAdrDlg newCounty roundedBrdr facilityFields widgetAddressFieldsReadonly newCounty" titl="County" > 
											<form:option value=""></form:option>
											<form:options items="${sessionScope.countyMap}"/>
										 </form:select>
			 	                    </span>     	                    
			 	                    <br>
			 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
			 	                </span>
			 	            </div>
			 	            <div class="personInfo pT10 pTRBL10">
			 	            	<span  class="nonRawEditFields" style="padding-left:59px"><form:input path="FACADDR1" class="standardInputText readonly" title="Address" maxLength="100"/> - 
			 	            				<form:input path="FACADDR2" class="standardInputText readonly" title="Address" maxLength="100"/>  </span>
			 	              	
			 	                <span style="float: right;" class="mr5">
			 	                	<a href="javascript:openAddressWidget('airsDocumentFacilityAddressDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
								 </span>
			 	                <br>               
			 	                <span class="nonRawEditFields"><span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span></span>
			 	                    
			 	            </div>
			 	        </div>
                </div>           
        </div>
        <div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
 		 <div id="button-row" class="button-row documentButtons center">

<%-- 						<c:if test="${stdCLIMSDocVO.STATENO != null && stdCLIMSDocVO.STATENO != '' &&  stdCLIMSDocVO.DISCARD_STATUS == null}"> --%>
							
<%-- 							<input type="button" id="nonCaseButton" class="createNonCaseDocument button rounded blue effect-3"  value="Create Non Case Doc"  onclick="createNonCaseDocument(${stdCLIMSDocVO.DOCUMENT_ID})"/> --%>
						
<%-- 						</c:if> --%>
<%--		 				
		 					<c:if  test="${userPermissions['99'] != null && stdCLIMSDocVO.STATENO != null && stdCLIMSDocVO.STATENO != ''}" >	
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
								<c:when test="${stdCLIMSDocVO.STATENO != null && stdCLIMSDocVO.STATENO != ''}">
										<c:if test="${userPermissions['90'] != null}" >
											<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
										</c:if>
										<c:if test="${userPermissions['100'] != null}" >
											<input type="button" id="editDocument"  class="editDocumentBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="confirmEditDocumentFromStateno()" />
										</c:if>
								</c:when>
							</c:choose>
					<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Save" onclick="saveForm()"/>	 
					<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />		 --%>		
		</div>		
		
        <br>
        <input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />          
</form:form>


