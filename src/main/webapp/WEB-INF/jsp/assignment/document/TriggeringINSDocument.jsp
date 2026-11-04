<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<form:form modelAttribute="medicaidDocVO" id="medicaidDocVO" cssClass="documentDescription mT40">
	<div id="triggeringLabDocTab" style="background-color: transparent">
		<div style="padding-left:43%; " class="headingCls marginBottom10">
				Assignment Details - INS Match
		</div>
		
		<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Person Details</span>
			</div>
			
			<div style="padding-top:5px; padding-bottom:5px; border:1px dotted;" class="container">	 
				<div style="float:left; padding-left:10px;width:49%; border-right:1px dotted;">
					<div class="mT10 container">
						<div class="fLW40">
							<spring:message code="label.firstname" />
						</div>
						<div style="float:left">
							<form:input path="FNAME" class="standardInputMedText" />
						</div>
					</div>
					<div class="mT10 container">
						<div class="fLW40">
							<spring:message code="label.middleName" />
						</div>
						<div style="float:left">
							<form:input path="MNAME" class="standardInputMedText" />
						</div>
					</div>
					<div class="mT10 container">
						<div class="fLW40">
							<spring:message code="label.birthdate" />
						</div>
						<div style="float:left">
							<form:input path="DOB_TEXT" class="standardInputMedText" />
						</div>
					</div>
					<div class="mT10 container">
						<div class="fLW40">
							<spring:message code="label.stateNo" />
						</div>
						<div style="float:left">
							<form:input path="" value="${param.stateno}" class="standardInputMedText" />
						</div>
					</div>
				</div>
			
			
			<div style="float:left; width:49%; padding-bottom:5px; padding-left:10px; ">
			
				
					<div class="mT10 container">
						<div class="fLW40">
							<spring:message code="label.lastname" />
						</div>
						<div style="float:left">
							<form:input path="LNAME" class="standardInputMedText" /> 
						</div>
					</div>
					<div class="mT10 container">
						<div class="fLW40">
							<spring:message code="label.suffix" />
						</div>
						<div style="float:left">
							<form:input path="" class="standardInputMedText" /> 
						</div>
					</div>
					<div class="mT10 container">
						<div class="fLW40">
							<spring:message code="label.deathdate" />
						</div>
						<div style="float:left">
							<form:input path="DOD_TEXT" class="standardInputMedText" />
						</div>
					</div>
					
					<div class="mT10 container positionAbsolute" style="width: 565px;">
						<div class="fLW40">
							CITYNO
						</div>
						<div style="float:left" class="showMultiData" id="nycCITYNO_docViewer">
							<input value="${nyehmsStatenoDetails.NYC_CITYNO}"  class="standardInputSmallText"/> 
						</div>
						
						
						<c:if test="${nyehmsStatenoDetails.NYC_CITYNO_LIST != null && nyehmsStatenoDetails.NYC_CITYNO_LIST != '[]' }">
							<span class="historyCharacter">*</span>
							<div
								class='displayNone stateNoHistoryData nycCITYNO_docViewer'>
								<table
									class='statenoHistory historyTable<c:out value="${personViewNumber}" />'>
									<tr>
										<th class="historyTableHeader">CITYNO</th>
									</tr>
									<c:forEach var="item" items="${nyehmsStatenoDetails.NYC_CITYNO_LIST}">
										<tr>
											<td>${item}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:if>
		                
					</div>
					
				
			</div>
			</div>
			
		
		
			<!--  Race details -->
		<div id="labDetailsHeadingDiv" class="divHeadingCls mT20" style="background:#DDDDDD; ">
			<span class=" mL2">Match Details</span>
		</div>
		<div style="padding-top:5px; padding-bottom:5px; border:1px dotted;" class="container">	 	 
			<div style="float:left; width:49%; padding-left:10px; ">
					
				<div class="mT10 container">
					<span style="float:left; width:45%">
						File Received 
					</span>
					<span style="float:left">
						<fmt:formatDate value="${medicaidDocVO.CREATE_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedCREATE_DATE" ></fmt:formatDate>
						<form:input path="CREATE_DATE"  value="${theFormattedCREATE_DATE}" class="  standardInputMedText mL5" />
					</span>
				</div>

				<div class="mT10 container">
					<span style="float:left; width:45%">
						File Processed
					</span>
					<span style="float:left">
						<fmt:formatDate value="${medicaidDocVO.MATCHED_PROCESS_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedMATCHED_PROCESS_DATE" ></fmt:formatDate>
						<form:input path="MATCHED_PROCESS_DATE" value="${theFormattedMATCHED_PROCESS_DATE}"  class="  standardInputMedText mL5" />
					</span>
				</div>

<!-- 				<div class="mT10 container"> -->
<!-- 					<span style="float:left; width:45%"> -->
<!-- 						Identify if this match is ETA or CASA* -->
<!-- 					</span> -->
<!-- 					<span style="float:left"> -->
<!-- 					</span> -->
<!-- 				</div> -->

			</div>
			<div style="float:left; width:49%; padding-bottom:5px; padding-left:10px; border-left:1px dotted;">
			
					<div class="mT10 container">
						<div class="fLW40">
							CIN
						</div>
						<div >
							<form:input path="CIN" class="standardInputMedText mL5" />
						</div>
					</div>	
					
					<div class="mT10 container">
						<div class="fLW40">
							Event Code /Criteria 
<!-- 							(the reason why made onto this match)  -->
						</div>
						<div >
							
							<c:if test="${medicaidDocVO.CRITERIA_1 == '1'}">
										Criteria 1					
							</c:if>
							<c:if test="${medicaidDocVO.CRITERIA_2 == '1'}">
										/ Criteria 2					
							</c:if>
							<c:if test="${medicaidDocVO.CRITERIA_3 == '1'}">
										/ Criteria 3					
							</c:if>
							<c:if test="${medicaidDocVO.CRITERIA_4 == 1}">
										/ Criteria 4					
							</c:if>
							<c:if test="${medicaidDocVO.CRITERIA_5 == 1}">
										/ Criteria 5					
							</c:if>
							<c:if test="${medicaidDocVO.CRITERIA_6 == 1}">
										/ Criteria 6					
							</c:if>
							<c:if test="${medicaidDocVO.CRITERIA_7 == 1}">
										/ Criteria 7					
							</c:if>
							<c:if test="${medicaidDocVO.CRITERIA_8 == 1}">
										/ Criteria 8					
							</c:if>
							<c:if test="${medicaidDocVO.CRITERIA_9 == 1}">
										/ Criteria 9					
							</c:if>
							<c:if test="${medicaidDocVO.CRITERIA_10 == 1}">
										/ Criteria 10					
							</c:if>
							
						</div>
					</div>
		
						
				</div>
													
								 
		</div>
		
		<!--  Provider details -->
		<div id="prvDetailsHeadingDiv" class="divHeadingCls mT20" style="background:#DDDDDD; ">
				<span class=" mL2">Current Assignment Address</span></div>
		<div class="container " style="border:1px dotted; width:100%; padding-bottom:5px;" id="assignmentAddressDtls">
			<div class="b1" style="margin: 15px 5px 5px 5px" id="labDocumentPrsnAddressDtls">
 	            <div class="personInfo pTRBL10 pB10">
 	            		<div class="fLW40">
 	            				<div class="fLW20">Name</div> 
	 	 	                    <div class="pL10"> <form:input path="" value="${assignmentAndAddressDetails.PROVIDER_FACILITY_NAME}" class="standardInputText " /></div>
	 	 	            </div>
	 	 	            
 	                	
 	            </div>
 	            <div class="clearBoth" ></div>
 	            
 	            <div class="personInfo pTRBL10 pB20">
 	            		<div class="fLW40">
 	            			<div class="fLW20">Address </div>
 		                               	
	 	                	<div class=""><form:input path="" value="${assignmentAndAddressDetails.PROVIDER_FACILITY_ADDR}" class="standardInputText newAddress" />   </div>
	 	                </div>
 	            		<div class="fLW30">
	 	            		<div class="fLW20">County</div> 
	           				<div class=""> 
	 	                    	<form:input path=""  cssClass="oAdrDlg newCounty standardInputSmallText  mL5"  value="${sessionScope.countyMap[assignmentAndAddressDetails.PROVIDER_FACILITY_COUNTY]}" /> 
	 	                    </div>
	 	 	            </div>
	 	 	           <div class="fLW30">
 	                		<div class="fLW20">Phone </div>
 		                               	
	 	                	<div class=""><form:input path="" value="${assignmentAndAddressDetails.PROVIDER_FACILITY_PHONE}" class="standardInputSmallText newAddress" />   </div>
	 	                </div>
	 	                
	 	                
 	            </div>
 	            
 	            <div class="clearBoth" ></div>
 	            <div class="personInfo pTRBL10 pB30 marginBottom10">
 	            	<div class="fLW30">
	 	            	<div class="fLW50"> Date Assignment Created</div>
						<div> <form:input path="" value="${assignmentAndAddressDetails.ASSIGN_CREATE_DATE}" class=" standardInputSmallText2" /></div>
					</div>
					<div class="fLW33">
						<div class="fLW50"> Date Assigned to Current Worker </div>
						<div> <form:input path="" value="${assignmentAndAddressDetails.WORKER_ASSIGN_DATE}"  class="  standardInputSmallText2" /> </div>
					</div>
					<div class="fLW30">
						<div class="fLW40"> Current Worker </div>
						<div> <form:input path="" value="${assignmentAndAddressDetails.WORKER_NAME}" class="nOnly standardInputMedText" /> </div>
					</div>
				</div>
 	        </div>					
			
		</div>	
		
		<c:forEach items="${medicaidDocVO.medicaidDocProvidersVO}" var="medicaidDocProvidersVO" varStatus="statusHNT">
		
			<div id="prvDetailsHeadingDiv" class="divHeadingCls mT20" style="background:#DDDDDD; ">
					<span class=" mL2">Provider Reported on Triggering Document</span>
			</div>
			
			
			<div style="padding-top:5px; padding-bottom:5px; border:1px dotted;" class="container">	 	 
				<div style="float:left; width:49%; padding-left:10px; border-right: 1px dotted;">
						
					<div class="mT10 container">
						<div class="fLW40">
							Name of Provider 
						</div>
						<div >
							<form:input path="medicaidDocProvidersVO[${statusHNT.index}].PROV_NAME"  class="  standardInputText  " />
						</div>
					</div>
	
					<div class="mT10 container">
						<div class="fLW40">
							Address
						</div>
						<div >
							<form:input path="medicaidDocProvidersVO[${statusHNT.index}].PROV_ADDRESS" class="  standardInputMedText  " />
						</div>
					</div>
	
					<div class="mT10 container">
						<div class="fLW40">
							City  
						</div>
						<div >
							<form:input path="medicaidDocProvidersVO[${statusHNT.index}].PROV_CITY" class="  standardInputMedText " />
						</div>
					</div>
					
					<div class="mT10 container">
						<div class="fLW40">
							State    
						</div>
						<div >
							<form:input path="medicaidDocProvidersVO[${statusHNT.index}].PROV_STATE" class="  standardInputMedText " />
						</div>
					</div>
					
					<div class="mT10 container">
						<div class="fLW40">
							Zip  
						</div>
						<div >
							<form:input path="medicaidDocProvidersVO[${statusHNT.index}].PROV_ZIP" class="  standardInputMedText " />
						</div>
					</div>
					
					<div class="mT10 container">
						<div class="fLW40">
							County  
						</div>
						<div >
							<form:select path="medicaidDocProvidersVO[${statusHNT.index}].PROV_COUNTY"  cssClass="oAdrDlg newCounty standardInputSmallText roundedBrdr"  > 
										<form:option value=""></form:option>
										<form:options items="${sessionScope.countyMap}"/>
									 </form:select>
						</div>
					</div>
					
<!-- 					<div class="mT10 container"> -->
<!-- 						<span style="float:left; width:45%"> -->
<!-- 							Phone   -->
<!-- 						</span> -->
<!-- 						<span > -->
<%-- 							<form:input path="medicaidDocProvidersVO[${statusHNT.index}].PROV_CITY" class="  standardInputMedText mL5" /> --%>
<!-- 						</span> -->
<!-- 					</div> -->
	
				</div>
				<div style="float:left; width:49%; padding-bottom:5px; padding-left:10px; ">
				
						<div class="mT10 container">
							<div class="fLW40">
								Category Of Service
							</div>
							<div >
								<form:input path="medicaidDocProvidersVO[${statusHNT.index}].PROV_COS_CODE_DESC" class="standardInputText  " />
							</div>
						</div>
						<div class="mT10 container">
							<div class="fLW40">
								Service Date
							</div>
							<div >
								<fmt:formatDate value="${medicaidDocProvidersVO.SERVICE_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedSERVICE_DATE_TEXT" ></fmt:formatDate>
								<form:input path="medicaidDocProvidersVO[${statusHNT.index}].SERVICE_DATE" value="${theFormattedSERVICE_DATE_TEXT}" class="standardInputMedText " />
							</div>
						</div>	
						
						<div class="mT10 container">
							<div class="fLW40">
								Primary Provider 
							</div>
							<div>
								<span> <form:radiobutton path="medicaidDocProvidersVO[${statusHNT.index}].PRIMARY_IND" value="Y" /> Yes </span>	
			            		<span> <form:radiobutton path="medicaidDocProvidersVO[${statusHNT.index}].PRIMARY_IND" value="N" /> No </span> 
							</div>
						</div>
			
							
					</div>
														
									 
			</div>
			
			
			
		</c:forEach>		
	
		
	</div>																	 										
</form:form>																



