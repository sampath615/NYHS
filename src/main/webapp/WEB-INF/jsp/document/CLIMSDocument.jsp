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
						<span class=" "><form:input id="DOCUMENT_ID"  path="stdClimsDoc.documentId" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
			         </div>	
		
				</div>

				<div class="container w100 nonRawEditFields pB10">
		 	         <div style="float:left; width: 40%">
		 	            <div >
		 	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
		 	                 <span class="ml10"><form:input id="STATENO"  path="stateno" class="standardInputSmallText readonly" maxlength="10" readonly="true"/> </span>
		 	            </div>    
		 	          </div>
		 	          <div style="float:left; width: 30%">
		 	         	<div >
		 	                <span class="ml10"> eHARS Import Date: </span>
		 	                <fmt:formatDate value="${stdCLIMSDocVO.stdClimsDoc.eharsImportDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
		 			 		<span class="ml10"><form:input id="EHARS_IMPORT_DATE"  path="stdClimsDoc.eharsImportDate" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/> </span>
		 	            </div>    
		 	         </div>
		 	         <div style="float:left; width: 30%; text-align: right">
		 	            <div >
		 	                <span class="mL10">NYC SHIP Date: </span>
		 	                <fmt:formatDate value="${stdCLIMSDocVO.stdClimsDoc.nycShipDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedNYC_SHIP_DATE_TEXT" ></fmt:formatDate>
		 			 		<span class="ml10"><form:input id="NYC_SHIP_DATE"  path="stdClimsDoc.nycShipDate" value="${theFormattedNYC_SHIP_DATE_TEXT}" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
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
	                                            <span class="pL5"><form:input  path="stdClimsDoc.firstname" id="CLIMS_FIRSTNAME" class="standardInputMedText  readonly required" title="First Name" maxLength="20"/></span>
	                                            <div class="pL10 v_error AIRS_FNAME "></div> 
	                                        </div>
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Last Name </span> 
                                            	<span  class="pL5"><form:input  path="stdClimsDoc.lastname" id="CLIMS_LASTNAME" class="standardInputMedText readonly"  title="Last Name" maxLength="25"/></span>
                                            	<div class="pL10 v_error AIRS_MNAME "></div>
                                            </div>
                               			<div class="fieldHolder25">
                                       		<span class="pL10">Date of Birth </span>
                                   			<fmt:formatDate value="${stdCLIMSDocVO.stdClimsDoc.dob}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDOBDate" />
                                   			<span class="ml10"><form:input id="DOB"  path="stdClimsDoc.dob" value= "${theFormattedDOBDate}" class="standardInputSmallText ml10 readonly" readonly="true"/> </span>
                                       		<div class="pL10 v_error DATE_OF_BIRTH "></div>
                                       </div>
                                      	<div class="fieldHolder25">
                                        	<span class="pL10">Age</span>
                                        	<span class="pL10"><form:input id="AGE"  path="stdClimsDoc.age" class="standardInputSmallText nOnly readonly" title="Age" maxLength="3"/>&nbsp;&nbsp;</span>
                                        </div>
                                        <br><br>
                                        
                                        
                                </div>
                                
                                <div class="b1" style="margin: 15px 5px 10px 0px" id="airsDocumentPersonAddressDtls">
					 	            <div class="personInfo pTRBL10">
				 		                <span> 	                	
					 	                    <span class="pL30">Zip </span>
					 	                    <span class="pL10"> 
					 	                       <form:input  path="stdClimsDoc.patzip" id="CLIMSZip" class="standardInputSmallText1 widgetAddressFieldsReadonly newZip readonly" title="Zipcode" maxLength="5"/>
					 	                    </span> 
					 	                    
					 	                    <span class="pL30">County </span>
					 	                    <span class="pL10"> 
					 	                    	<form:select id="COUNTYCODE"  path="stdClimsDoc.countycode"  cssClass="oAdrDlg newCounty roundedBrdr widgetAddressFieldsReadonly newCounty readonly" title="County" maxLength="3"> 
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
                                        <div>   <form:checkbox id="RACE_WHITE1"  path="stdClimsDoc.raceWhite"  value="1"/> White </div>
                                        <div>   <form:checkbox id="RACE_BLACK1"  path="stdClimsDoc.raceBlack" value="1"/> African American</div>
                                        <div>   <form:checkbox id="RACE_ASIAN1"  path="stdClimsDoc.raceAsian" value="1"/> Asian</div>
                                        <div>   <form:checkbox id="RACE_INDIAN1"  path="stdClimsDoc.raceIndian" value="1"/> American Indian/Alaskan Native</div>
                                        <div>   <form:checkbox id="RACE_PACIFIC1"  path="stdClimsDoc.racePacific" value="1"/> Pacific Islander</div> 
                                        <div>   <form:checkbox id="RACE_OTHER1"  path="stdClimsDoc.raceOther" value="1"/> Other 
                                        </div>
                                        <div>   <form:checkbox id="RACE_UNKNOWN1"  path="stdClimsDoc.raceUnknown" value="1"/> Unknown</div> 
                                        <br>           
                                </div>  
                                <div style="padding:15px 0 10px 10px; border-top: dotted 1px; ">   
                                        <div>
                                                Ethnicity (Check one) Hispanic or Latino<br>
                                                <form:radiobutton id="ETHNICITY1"  path="stdClimsDoc.ethnicity" value="01" /> Yes
                                                <form:radiobutton id="ETHNICITY2"  path="stdClimsDoc.ethnicity" value="02" /> No
                                                <form:radiobutton id="ETHNICITY3"  path="stdClimsDoc.ethnicity" value="09" /> Unknown
                                        </div>
                                </div>  
								 <div style="padding:15px 0 10px 10px; border-top: dotted 1px; "> 
								 	<div class="floatLeft pR10" style="border-right: dotted 1px;">
										<div class=""> Current Gender </div>
										<div  class="">
											<div> <form:radiobutton id="CURRENT_GENDER1"  path="stdClimsDoc.currentGender" value="M" /> Male </div>
											<div> <form:radiobutton id="CURRENT_GENDER2"  path="stdClimsDoc.currentGender" value="F" /> Female </div>
											<div> <form:radiobutton id="CURRENT_GENDER3"  path="stdClimsDoc.currentGender" value="U" />Unknown</div>		
										</div>
									</div>
									<div class="floatLeft pL10">
										<div class=""> Transgender </div>
										<div  class="">
											<div> <form:radiobutton id="CURRENT_GENDER4"  path="stdClimsDoc.currentGender" value="X" /> MTF </div>		
											<div> <form:radiobutton id="CURRENT_GENDER5"  path="stdClimsDoc.currentGender" value="Y" /> FTM </div>		
											<div> <form:radiobutton id="CURRENT_GENDER6"  path="stdClimsDoc.currentGender" value="T" /> Unspecified </div>														
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
												<form:input id="ACCID"  path="stdClimsDoc.accid" class="standardInputMedText mL5 readonly" title="ACCID" maxlength="30"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Final Approval Date
											</div>
											<div style="float:left">
											<fmt:formatDate value="${stdCLIMSDocVO.stdClimsDoc.finalapprovaldt}"  type="date" pattern="MM/dd/yyyy" var="theFormattedFINALAPPROVALDT" />
                                   			<form:input id="FINALAPPROVALDT"  path="stdClimsDoc.finalapprovaldt" value= "${theFormattedFINALAPPROVALDT}" class="standardInputMedText mL5 readonly" datevalidations="completeDate" title="Final Approval Date"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Initial PCR
											</div>
											<div style="float:left">
												<form:input id="INITIALPCR"  path="stdClimsDoc.initialpcr" title="Initial PCR" class="standardInputMedText mL5 readonly" maxlength="1"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Follow Up PCR
											</div>
											<div style="float:left">
												<form:input id="FOLLOWUPPCR"  path="stdClimsDoc.followuppcr" title="Follow Up PCR" class="standardInputMedText mL5 readonly" maxlength="1"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Records ID
											</div>
											<div style="float:left">
												<form:input id="RECID"  path="stdClimsDoc.recid" class="standardInputMedText mL5 nOnly readonly" maxlength="10" title="Records ID"/>
											</div>
										</div>
										
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Patient ID
											</div>
											<div style="float:left">
												<form:input id="PATIENTID"  path="stdClimsDoc.patientid" class="standardInputMedText mL5 nOnly readonly" maxlength="10" title="Patient ID"/>
											</div>
										</div>
										
										
										<div class="mT5 container">
											<div style="float:left; width:25%">
												NBS Code
											</div>
											<div style="float:left">
												<form:input id="NBSCODE"  path="stdClimsDoc.nbscode" class="standardInputMedText mL5 nOnly  readonly" maxlength="10" title="NBS Code"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												NBS Lab ID
											</div>
											<div style="float:left">
												<form:input id="NBSLABID"  path="stdClimsDoc.nbslabid" class="standardInputMedText mL5 readonly" maxlength="30" title="NBS Lab ID"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Possible Medical Record #
											</div>
											<div style="float:left">
												<form:input id="PATCODE"  path="stdClimsDoc.patcode" class="standardInputMedText mL5 readonly" maxlength="30" title="Patient Code"/>
											</div>
										</div>	               
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Collection Date
											</div>
											<div style="float:left">
											<fmt:formatDate value="${stdCLIMSDocVO.stdClimsDoc.colldate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedCOLLDATE" />
                                   			<form:input id="COLLDATE"  path="stdClimsDoc.colldate" value= "${theFormattedCOLLDATE}" class="standardInputMedText mL5 readonly" datevalidations="completeDate" title="Collection Date"/>										
											</div>
										</div>	               
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Gestation
											</div>
											<div style="float:left">
												<form:input id="GESTATION"  path="stdClimsDoc.gestation" class="standardInputMedText mL5 nOnly readonly" maxlength="10" title="Gestation"/>
											</div>
										</div>	               
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Birth Facility
											</div>
											<div style="float:left">
												<form:input id="BIRTH_FACILITY"  path="stdClimsDoc.birthFacility" class="standardInputMedText mL5 readonly" maxlength="20" title="Birth Facility"/>
											</div>
										</div>	               
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Foster Care
											</div>
											<div style="float:left">
												<form:input id="FOSTERCARE"  path="stdClimsDoc.fostercare" class="standardInputMedText mL5 readonly" maxlength="1" title="Foster Care"/>
											</div>
										</div>	               
										<div class="mT5 container">
											<div style="float:left; width:25%">
												MAV
											</div>
											<div style="float:left">
												<form:input id="MAV"  path="stdClimsDoc.mav" class="standardInputMedText mL5 readonly" maxlength="1" title="MAV"/>
											</div>
										</div>	               
										<div class="mT5 container">
											<div style="float:left; width:25%">
												MAV When
											</div>
											<div style="float:left">
												<form:input id="MAVWHEN"  path="stdClimsDoc.mavwhen" class="standardInputMedText mL5 readonly" maxlength="30" title="MAV When"/>
											</div>
										</div>	                            
                                </div>
                                <div class="reportHW borderField pT10" style="height:382px">   
										<div class="mT5 container">
											<div style="float:left; width:25%">
												IPAV
											</div>
											<div style="float:left">
												<form:input id="IPAV"  path="stdClimsDoc.ipav" class="standardInputMedText mL5 readonly" maxlength="1" title="IPAV"/>
											</div>
										</div>	               
										<div class="mT5 container">
											<div style="float:left; width:25%">
												ITAV
											</div>
											<div style="float:left">
												<form:input id="ITAV"  path="stdClimsDoc.itav" class="standardInputMedText mL5 readonly" maxlength="1" title="ITAV"/>
											</div>
										</div>	                                  
                                		 <div class="mT5 container">
											<div style="float:left; width:25%">
												Weight
											</div>
											<div style="float:left">
												<form:input id="WEIGHT"  path="stdClimsDoc.weight" class="standardInputMedText mL5 readonly" title="Weight" maxlength="10"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Submitter First Name
											</div>
											<div style="float:left">
												<form:input id="SUBMFNAME"  path="stdClimsDoc.submfname" class="standardInputMedText mL5 readonly" title="Submitter First Name" maxlength="100" />
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Submitter Last Name
											</div>
											<div style="float:left">
												<form:input id="SUBMLNAME"  path="stdClimsDoc.submlname" title="Submitter Last Name" class="standardInputMedText mL5 readonly" maxlength="100"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Submitter ID
											</div>
											<div style="float:left">
												<form:input id="SUBMITTERID"  path="stdClimsDoc.submitterid" title="Submitter ID" class="standardInputMedText mL5 readonly" maxlength="50"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Multispot
											</div>
											<div style="float:left">
												<form:input id="MULTISPOT"  path="stdClimsDoc.multispot" class="standardInputMedText mL5 readonly" maxlength="50" title="Multispot"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												HIV2QUAL
											</div>
											<div style="float:left">
												<form:input id="HIV2QUAL"  path="stdClimsDoc.hiv2qual" class="standardInputMedText mL5 readonly" maxlength="30" title="HIV2QUAL"/>
											</div>
										</div>
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Results
											</div>
											<div style="float:left">
												<form:input id="TMA"  path="tma" class="standardInputMedText mL5 readonly" maxlength="30" title="TMA"/>
											</div>
										</div>	               
										<div class="mT5 container">
											<div style="float:left; width:25%">
												MEMO
											</div>
											<div style="float:left">
												<form:input  path="stdClimsDoc.memo" id="MEMO" class="standardInputMedText mL5 dPicker mask readonly" maxlength="40" title="MEMO"/>
											</div>
										</div>	               
										<div class="mT5 container">
											<div style="float:left; width:25%">
												Geenius
											</div>
											<div style="float:left">
												<form:input id="GEENIUS"  path="stdClimsDoc.geenius" class="standardInputMedText mL5 nOnly readonly" maxlength="30" title="Geenius"/>
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
                                        <span class="pL10"><form:input id="FACILITY_NAME"  path="stdClimsDoc.facilityName" class="standardInputText facilityFields readonly" title="Facility Name" maxlength="80"/></span> 
                                </span>    
                               
                        </div><br>
                        
                        <div class="b1" style="margin: 15px 5px 10px 0px">
			 	            <div class="personInfo pTRBL10" id="airsDocumentFacilityAddressDtls">
			 	                <span class="personLabelDoc"> Address </span>
		 		                <span> 	                	
			 	                	<span class="pL10"> <form:input id="FACILITY_ADDRESS"  path="stdClimsDoc.facilityAddress" class="standardInputText facilityFields newAddress widgetAddressFieldsReadonly" title="Address" maxLength="62"/>  </span>	 	        
			 	                	            	 	                    
			 	                    <span class="pL20">City</span>
			 	                    <span class="pL10"><form:input id="FACILITY_CITY"  path="stdClimsDoc.facilityCity" class="standardInputSmallText facilityFields widgetAddressFieldsReadonly newCity" title="City" maxLength="30"/></span>
			 	                    
			 	                    <span class="pL20">State </span>
			 	                    <span  class="pL10">
										<form:select id="FACILITY_STATE"  path="stdClimsDoc.facilityState"  class="standardInputSmallText1 facilityFields widgetAddressFieldsReadonly newState" title="State" maxLength="2">
													<form:option value=""></form:option>
													<form:options items="${sessionScope.stateMap}"/>
										</form:select>
									</span>
			 	                    
			 	                    <span class="pL20">Zip </span>
			 	                    <span class="pL10"> 
			 	                        <form:input id="FACILITY_ZIP"  path="stdClimsDoc.facilityZip" class="standardInputSmallText1 facilityFields widgetAddressFieldsReadonly newZip" title="Zipcode" maxLength="5"/>
			 	                    </span> 
			 	                    
			 	                    <span class="pL20">County </span>
			 	                    <span class="pL10"> 
			 	                    	<form:select id="FACILITY_COUNTY"  path="stdClimsDoc.facilityCounty"  cssClass="oAdrDlg newCounty roundedBrdr facilityFields widgetAddressFieldsReadonly newCounty" titl="County" > 
											<form:option value=""></form:option>
											<form:options items="${sessionScope.countyMap}"/>
										 </form:select>
			 	                    </span>     	                    
			 	                    <br>
			 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
			 	                </span>
			 	            </div>
			 	            <div class="personInfo pT10 pTRBL10">
			 	            	<span  class="nonRawEditFields" style="padding-left:59px"><form:input id="FACADDR1"  path="facaddr1" class="standardInputText readonly" title="Address" maxLength="100"/> - 
			 	            				<form:input id="FACADDR2"  path="facaddr2" class="standardInputText readonly" title="Address" maxLength="100"/>  </span>
			 	              	
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


