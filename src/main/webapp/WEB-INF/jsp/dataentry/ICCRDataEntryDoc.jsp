<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ICCR Document Data Entry</title>
<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<%@ include file="../common/MainMenuIncludes.jsp"%>

<link href="${staticPath}/css/docs/iccrDocForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/jquerycombobox.css" rel="stylesheet" type="text/css" />

</head>

<body class="ICCRDataEntryScreen">
	<%@ include file="../common/Menu.jsp"%>
	<div id="fileUploadsPage" class="pageView">
		<ul id="fileUploadTab">
			<li><a href="#uploadUpdatedDispositionsContent" id="uploadUpdatedDispositionsTab">ICCR Document Data Entry</a></li>

		</ul>
		<div id="uploadUpdatedDispositionsContent" style="padding: 3em; height:670px" >

			<c:choose>
				<c:when test="${TRANSACTION_RESULT == 'TRANSACTION_SUCCESS'}">
					<div id="fileUploadTransactionMessage" class="transactionMessage successTransactionMessage">
						<span class="message"><c:out value="${TRANSACTION_MESSAGE}" /></span>
					</div>
				</c:when>
				<c:when test="${TRANSACTION_RESULT == 'TRANSACTION_FAILURE'}">
					<div id="fileUploadTransactionMessage" class="transactionMessage errorTransactionMessage">
						<span class="message"><c:out value="${TRANSACTION_MESSAGE}" /></span>
					</div>
				</c:when>
			</c:choose>

			<div id="assignmentsReviewSearchCriteria" class="pT20 pB20">
			
				<form:form modelAttribute="iccrDataEntryVO" id ="ICCRDataEntry" action="ICCRDataEntryInsert" method="post" cssClass="documentFormVO documentDescription">
				<form:errors path="*" cssClass="errorblock" element="div" />
			
			        <div id="" style="background-color: transparent">
			                <div style="padding-left:43%; " class="headingCls">
			                                ICCR DOCUMENT
			                </div>
			                
			           	<div id="iccrDocumentTransactionMessage" class="transactionMessage"><span class="message"></span></div>
							
			                <!--  Person details -->
			
			                <div class="container" id="patientInformation">  
			                        <div style="padding-left:10px;padding-top:10px">
			                                <div class="personInfo" style="height:35px">
			                                			<div class="fieldHolder25">
				                                            <span class="pL10"><spring:message code="label.firstname" /> </span> 
				                                            <span class="pL5"><form:input path="firstName" id="iccrFirstName" class="standardInputMedText required" cssErrorClass="error" title="First Name" maxLength="30"/></span>
				                                            <div class="v_error iccrFirstName mL10 width250"></div> 
				                                        </div>
				                                        <div class="fieldHolder25">
			                                            	<span class="pL10"><spring:message code="label.lastname" /></span> 
			                                            	<span  class="pL5"><form:input path="lastName" id="iccrLastName" class="standardInputMedText required"  cssErrorClass="error" title="Last Name" maxLength="30"/></span>
			                                            	<div class="v_error iccrLastName mL10 width250"></div>
			                                            </div>	                                        
				                                        <div class="fieldHolder25">
			                                            	<span class="pL10"><spring:message code="label.middleName" /> </span> 
			                                            	<span  class="pL5"><form:input path="middleName" id="iccrMiddleName" class="standardInputMedText" cssErrorClass="error" title="Middle Name" maxLength="30"/></span>
			                                            	<div class="v_error iccrMiddleName mL10 width250"></div>
			                                            </div>	                                        
			                                </div>
			                                <br>
			                                <div class="personInfo" style="height:40px">
				                               			<div class="fieldHolder25">
				                                       		<span class="pL10">Date of Birth </span>
				                                   			<span class="pL5"><form:input path="dob" id="iccrDOB" class="dPicker standardInputSmallText required" datevalidations="completeDate" data-format="MM/dd/yyyy" type="text" title="Date Of Birth" maxlength="10"/> </span>
				                                   			<br><span class="pL10">(mm/dd/yyyy)</span>
				                                       		<div class="v_error iccrDOB mL10 width250"></div>
				                                       </div>
			                                			<div class="fieldHolder25">
				                                            <span class="pL10">Phone</span> 
				                                            <span class="pL5"><form:input path="personPhone" id="iccrPersonPhone" class="pNumber standardInputMedText phoneInputText" cssErrorClass="error" title="Person Phone" maxLength="14"/></span>
				                                            <div class="v_error iccrPersonPhone mL10 width250"></div> 
				                                        </div>
	                                		</div>
			                                
			                                <div class="b1" style="margin: 15px 5px 10px 0px" id="iccrDocumentPersonAddressDtls">
			                                	<div class="personInfo pTRBL10">
								 	                <span class="personLabelDoc"> Person Address </span>
							 		                <span> 	                	
								 	                	<span class="pL10"> <form:input path="personAddress" id="iccrPersonAddress" class="standardInputMedText newAddress widgetAddressFieldsReadonly" tabindex="-1" title="Address" maxLength="62"/>  </span>	 	        
								 	                	            	 	                    
								 	                    <span class="pL20"><spring:message code="label.city" /></span>
								 	                    <span class="pL10"><form:input path="personCity" id="iccrPersonCity" class="standardInputSmallText newCity widgetAddressFieldsReadonly" tabindex="-1" title="City" maxLength="30"/></span>
								 	                    
								 	                    <span class="pL20"><spring:message code="label.state" /> </span>
								 	                    <span  class="pL10">
															<form:input path="personState" id="iccrStateState" class="standardInputSmallText1 newState widgetAddressFieldsReadonly" tabindex="-1" title="State" maxLength="2"/>
														</span>
								 	                    
								 	                    <span class="pL20"><spring:message code="label.zip" /> </span>
								 	                    <span class="pL10"> 
								 	                       <form:input path="personZip" id="iccrZipZip" class="standardInputSmallText1 newZip widgetAddressFieldsReadonly" tabindex="-1" title="Zipcode" maxLength="5"/>
								 	                    </span> 
								 	                    
								 	                    <span class="pL20"><spring:message code="label.county" /> </span>
								 	                    <span class="pL10">
								 	                    			<form:select path="personCounty"  id="iccrPersonCounty" cssClass="oAdrDlg newCounty standardInputSmallText1  newCounty widgetAddressFieldsReadonly" tabindex="-1" title="County" maxLength="3"> 
																		<form:option value=""></form:option>
																		<form:options items="${sessionScope.countyMap}"/>
																	 </form:select>
																
								 	                    </span> 
								 	                    <span class="pL20"> <button value="Edit" onclick="openAddressWidget('iccrDocumentPersonAddressDtls');return false;" class="editAddress inlineEditButton">Add Address</button> </span>    	 
								 	                    
								 	                </span>
								 	            </div>
								 	        </div>    
								 	        
			                                <div class="personInfo" style="padding-top:10px;height:35px">
				                                        <div class="fieldHolder25">
			                                            	<span class="pL10">Provider Name</span> 
			                                            	<span  class="pL5"><form:input path="provName" id="iccrProvName" class="standardInputMedText"  cssErrorClass="error" title="Provider Name" maxLength="100"/></span>
			                                            	<div class="v_error iccrProvName mL10 width250"></div>
			                                            </div>	                                        
				                                        <div class="fieldHolder25">
			                                            	<span class="pL10">Provider Phone</span> 
			                                            	<span  class="pL5"><form:input path="provPhone" id="iccrProvPhone" class="pNumber standardInputMedText phoneInputText"  cssErrorClass="error" title="Provider Phone" maxLength="14"/></span>
			                                            	<div class="v_error iccrProvPhone mL10 width250"></div>
			                                            </div>	                                        
			                                			<div class="fieldHolder49">
				                                            <span class="pL10">Jurisdiction providing information </span> 
				                                            <span class="mL5"> 
																<form:select path="jurisdictionProvidingInfo" id="iccrJurisdiction" class="standardInputMedText required" cssErrorClass="error" title="Jurisdiction providing information" maxLength="2" >
																	<form:option value="" label="--select--"></form:option>
																	<form:options items="${sessionScope.stateMap}"/>
																</form:select>	
															 </span>
				                                            <div class="v_error iccrJurisdiction mL10" style="width:350px;"></div> 
				                                        </div>
			                                </div>		
			                                
			                                
			                                <div class="b1" style="margin: 15px 5px 10px 0px" id="iccrDocumentProviderAddressDtls">
			                                	<div class="personInfo pTRBL10">
								 	                <span class="personLabelDoc"> Provider Address </span>
							 		                <span> 	                	
								 	                	<span class="pL10"> <form:input path="provAddress" id="iccrProvAddress" class="standardInputMedText newAddress widgetAddressFieldsReadonly" tabindex="-1" title="Address" maxLength="62"/>  </span>	 	        
								 	                	            	 	                    
								 	                    <span class="pL20"><spring:message code="label.city" /></span>
								 	                    <span class="pL10"><form:input path="provCity" id="iccrProvCity" class="standardInputSmallText newCity widgetAddressFieldsReadonly" tabindex="-1" title="City" maxLength="30"/></span>
								 	                    
								 	                    <span class="pL20"><spring:message code="label.state" /> </span>
								 	                    <span  class="pL10">
															<form:input path="provState" id="iccrStateState" class="standardInputSmallText1 newState widgetAddressFieldsReadonly" tabindex="-1" title="State" maxLength="2"/>
														</span>
								 	                    
								 	                    <span class="pL20"><spring:message code="label.zip" /> </span>
								 	                    <span class="pL10"> 
								 	                       <form:input path="provZip" id="iccrZipZip" class="standardInputSmallText1 newZip widgetAddressFieldsReadonly" tabindex="-1" title="Zipcode" maxLength="5"/>
								 	                    </span> 
								 	                    
								 	                    <span class="pL20"><spring:message code="label.county" /> </span>
								 	                    <span class="pL10">
								 	                    			<form:select path="provCounty"  id="iccrProvCounty" cssClass="oAdrDlg newCounty standardInputSmallText1  newCounty widgetAddressFieldsReadonly" tabindex="-1" title="County" maxLength="3"> 
																		<form:option value=""></form:option>
																		<form:options items="${sessionScope.countyMap}"/>
																	 </form:select>
																
								 	                    </span> 
								 	                    <span class="pL20"> <button value="Edit" onclick="openAddressWidget('iccrDocumentProviderAddressDtls');return false;" class="editAddress inlineEditButton">Add Address</button> </span>    	 
								 	                    
								 	                </span>
								 	            </div>
								 	        </div>    
			                                
						                <div id="patientInformation" class="divHeadingCls" style="padding-top:10px;height:150px;overflow-y:auto;overflow-x:hidden;border:1px dotted;">  
												<div style="width:100%; padding:2px; background-color:#DBEAF9;">
														<span class="hdrFont mL5"> Test Information</span>
														<div style="float:right; margin-right:20px;">
															<span><img class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addTestRow()" /> </span>
														</div>				
												</div>		
												
												<div id="testDivContainer" style="float:left; width:95%; padding:2px; padding-left:10px;"> 
												
													<c:forEach items="${iccrDataEntryVO.iccrTestList}" var="tempObj" varStatus="statusCD">
														
																<div class="testDiv" id="testDiv" style="float:left; width:99%; padding-top:5px; margin-left:5px; margin-right:5px; margin-top:2px;" >	
																	
																	<div class="fieldHolder15">
																		<span class="pL10">Date of Test</span>
																		<span class="pL5"><form:input  path="iccrTestList[${statusCD.index}].dateOfTest"  id="iccrTest_${statusCD.index}_DateOfTest" class=" standardInputSmallText1 dPicker required" datevalidations="completeDate" data-format="MM/dd/yyyy" type="text" maxlength="10" title="Date Of Test" /></span>
																		<br><span class="pL10">(mm/dd/yyyy)</span>
																	 	<div class="v_error iccrTest_${statusCD.index}_DateOfTest mL10"></div>
																	</div>
																	
																	<div class="fieldHolder25">
																		<span class="pL10">Test Type</span>
<%-- 																		<span class="pL10"><form:input  path="iccrTestList[${statusCD.index}].testType"  id="iccrTest_${statusCD.index}_TestType" class="standardInputMedText required" title="Test Type" maxlength="20"/></span> --%>
																		<span class="pL10">
																			<form:select path="iccrTestList[${statusCD.index}].testType" id="iccrTest_${statusCD.index}_TestType" class="standardInputMedText required mR50" title="Test Type" onchange="changeICCRTestType(this,[${statusCD.index}])">
																				<form:option value="" label="--Select--"></form:option>
 																				<c:forEach items="${codedAcrfTestList}" var="codedValue">
																					<form:option value="${codedValue.acrtTestCode}">${codedValue.acrtTestDesc}</form:option>
																				</c:forEach>
																			</form:select> 																		
																		</span>
																																				
																	 	<div class="v_error iccrTest_${statusCD.index}_TestType mL10"></div>
																	</div>
																	
																	<div class="fieldHolder20">
																		<span class="pL10">Test Result</span> 
																		<span class="pL10">
																			<form:select path="iccrTestList[${statusCD.index}].testResults" id="iccrTest_${statusCD.index}_TestResult" class="standardInputSmallText required" title="Test Results">
																				<form:option value="" label="--Select--"></form:option>
  																				<c:forEach items="${codedAcrfTestResults}" var="codedValue">
																					<form:option value="${codedValue.ACRF_RESULT_CODE}">${codedValue.ACRF_RESULT_DESC}</form:option>
																				</c:forEach>
																				
																			</form:select> 																		
																		</span>
																	 	<div class="v_error iccrTest_${statusCD.index}_TestResult mL10"></div>
																	</div>

																	<div class="fieldHolder20">
																		<span class="pL10">Copies/mL</span> 
 																		<span class="pL10"><form:input  path="iccrTestList[${statusCD.index}].testResultCopies"  id="iccrTest_${statusCD.index}_TestResultCopies" class=" standardInputSmallText2 quanCP readonly" maxlength="10" title="Copies/mL" /></span>
																	 	<div class="v_error iccrTest_${statusCD.index}_TestResultCopies mL10"></div>
																	</div>
																	
																	<div class="fieldHolder15">
																		<span class="pL10">Log</span> 
 																		<span class="pL10"><form:input  path="iccrTestList[${statusCD.index}].testResultLogs"  id="iccrTest_${statusCD.index}_TestResultLogs" class=" standardInputSmallText2 quanLog readonly" maxlength="10" title="Log" /></span>
																	 	<div class="v_error iccrTest_${statusCD.index}_TestResultLogs mL10"></div>
																	</div>
																	
											 						<div style="float:left; width:4%" id="iccrTest_${statusCD.index}_closeTest" >
																			<span class="mL2" style="float:right; margin-top:4px;"><img class="cloningIcons" onclick="deleteTestDiv(this, 'testDiv');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span>
																	</div>
																</div>
																
															<script>  
																 testCnt = testCnt+1;
															</script>	
												
											
													</c:forEach>												
				
												</div>				
						
						                </div>         			                                
								 	        
		                                <div class="personInfo" style="padding-top:10px;height:70px">
		                                            <div class="fieldHolder33">
		                                            	<span class="pL10">Patient received results? </span> 
														<span class="pL30">
															<form:select path="resultsReceived" id="iccrResultReceived" class="standardInputSmallText mR50" title="Has Patient received results?">
																<form:option value="" label="--Select--"></form:option>
																<c:forEach items="${codedYesNoUnkownValues}" var="codedValue">
																	<form:option value="${codedValue.ANS_CODE}">${codedValue.ANS_DESC}</form:option>
																</c:forEach>
															</form:select> 																		
														</span>
					                                 	<div class="v_error iccrResultReceived mL10 width250"></div>
		                                            </div>
														<div class="fieldHolder10 mT10"><span class="pL50">Comments </span> </div>
														<div class="fieldHolder49 mT10">
															<span>  <form:textarea id="COMMENTS" title="Comments" maxlength="4000" cols="200" rows="3" path="comments" style="width:65%; height:67px; border:1px solid gray;" class="roundedBorder"></form:textarea></span>											                                   	
						                                </div>
		                                </div>	
			                                
		                                	                                						 	                                   
			                        </div>        
			                </div>
			
			                
			  		</div>      
			  
  					 <div class="mT10">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->				  
					 <div class="button-row"style="padding-top:20px;text-align: center;">
				 				<input type="button" id="sButton" class="button rounded blue effect-3" value="Save" onclick="submitICCRDataForm()"/>	
				 				<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Clear" onclick="clearIccrDataForm()"/>
					 		
					</div>
				</form:form>
	
			</div>
		</div>
	</div>		
	   <div id="confirmMessageDialog" >
			<h4 id="confirmMessageContent"></h4>
	   </div>	
	   
    <%@ include file="../common/addressComboDialog.jsp" %>
	<%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/docs/iccrDeDoc.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>	
	<script src="${staticPath}/js/common/html2canvas.js"></script>
	<script src="${staticPath}/js/common/jspdf.min.js"></script>
	<script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/jquery/jquerycombobox.js"></script>	
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>	
</body>		   		
