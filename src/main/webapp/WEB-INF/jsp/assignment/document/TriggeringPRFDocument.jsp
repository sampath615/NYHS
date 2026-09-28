<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<form:form modelAttribute="pRFDocumentVO" id="pRFDocumentVO" cssClass="documentDescription mT40">
	<div id="triggeringAdapDocTab" style="background-color: transparent">
		<div class="headingCls marginBottom10 alignCenter">
				Assignment Details - Provider Report Form
		</div>
		
		<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Person Details</span>
			</div>
			
			<div style="padding-top:5px; padding-bottom:5px; border:1px dotted;" class="container">	 
				<div style="float:left; padding-left:10px;width:49%; ">
					<div class="mT10 container">
						<span style="float:left; width:45%">
							<spring:message code="label.firstname" />
						</span>
						<span style="float:left">
							<form:input path="FNAME" class="standardInputMedText" />
						</span>
					</div>
					<div class="mT10 container">
						<span style="float:left; width:45%">
							<spring:message code="label.middleName" />
						</span>
						<span style="float:left">
							<form:input path="MNAME" class="standardInputMedText" />
						</span>
					</div>
					<div class="mT10 container">
						<span style="float:left; width:45%">
							<spring:message code="label.birthdate" />
						</span>
						<span style="float:left">
							<form:input path="DOB" class="standardInputMedText" />
						</span>
					</div>
					<div class="mT10 container">
						<span style="float:left; width:45%">
							<spring:message code="label.stateNo" />
						</span>
						<span style="float:left">
							<form:input path="" value="${param.stateno}" class="standardInputMedText" />
						</span>
					</div>
					
				</div>
			
			
			<div style="float:left; width:49%; padding-bottom:5px; padding-left:10px; border-left:1px dotted;">
			
				
					<div class="mT10 container">
						<span style="float:left; width:40%">
							<spring:message code="label.lastname" />
						</span>
						<span style="float:left">
							<form:input path="LNAME" class="standardInputMedText" /> 
						</span>
					</div>
					<div class="mT10 container">
						<span style="float:left; width:40%">
							<spring:message code="label.suffix" />
						</span>
						<span style="float:left">
							<form:input path="SUFFIX" class="standardInputMedText" /> 
						</span>
					</div>
					<div class="mT10 container">
						<span style="float:left; width:40%">
							<spring:message code="label.deathdate" />
						</span>
						<span style="float:left">
							<form:input path="DOD_TEXT" class="standardInputMedText" />
						</span>
					</div>
					
					<div class="mT10 container positionAbsolute" style="width: 565px;">
						<span style="float:left; width:40%">
							CITYNO
						</span>
						<span style="float:left" class="showMultiData" id="nycCITYNO_docViewer">
							<input value="${nyehmsStatenoDetails.NYC_CITYNO}"  class="standardInputSmallText"/> 
						</span>
						
						
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
			
		
		<div id="matchDetailsHeadingDiv" class="divHeadingCls mT20" style="background:#DDDDDD; ">
			<span class=" mL2">Provider Report Form Details</span>
		</div>
		<div style="padding-top:5px; padding-bottom:5px; border:1px dotted;" class="container">	 	 
			<div style="float:left; width:49%; padding-left:10px; ">
					
				<div class="mT10 container">
					<span style="float:left; width:45%">
						File Received
					</span>
					<span style="float:left">
						<fmt:formatDate value="${pRFDocumentVO.FILE_RECEVIED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedFILE_RECEVIED_DATE" ></fmt:formatDate>
						<form:input path="FILE_RECEVIED_DATE" value="${theFormattedFILE_RECEVIED_DATE}"  class="  standardInputMedText mL5" />
					</span>
				</div>
				<div class="mT10 container">
					<span style="float:left; width:45%">
						Date Provider Completed Form 
					</span>
					<span style="float:left">
						<fmt:formatDate value="${pRFDocumentVO.COMPLETED_DATE }"  type="date" pattern="MM/dd/yyyy" var="theFormattedCOMPLETED_DATE" ></fmt:formatDate>
						<form:input path="COMPLETED_DATE"  value="${theFormattedCOMPLETED_DATE}" class="  standardInputMedText mL5" />
					</span>
				</div>
				
				<div class="mT10 container">
					<span style="float:left; width:45%">
						Initial HIV Diagnosis
					</span>
					<span style="float:left">
						<form:input path="INIT_HIV_DIAG_DATE_TEXT"   class="  standardInputMedText mL5" />
					</span>
				</div>
				<div class="mT10 container">
					<span style="float:left; width:45%">
						Initial AIDS Diagnosis
					</span>
					<span style="float:left">
						<form:input path="INIT_AIDS_DIAG_DATE_TEXT"   class="  standardInputMedText mL5" />
					</span>
				</div>

			</div>
			<div style="float:left; width:49%; padding-bottom:5px; padding-left:10px; border-left:1px dotted;">
					
					<div class="mT10 container">
						<span style="float:left; width:40%">
							File Processed 
						</span>
						<span style="float:left">
							<fmt:formatDate value="${pRFDocumentVO.FILE_PROCESSED_DATE }"  type="date" pattern="MM/dd/yyyy" var="theFormattedFILE_PROCESSED_DATE" ></fmt:formatDate>
							<form:input path="FILE_PROCESSED_DATE"  value="${theFormattedFILE_PROCESSED_DATE}"   class="  standardInputMedText mL5" />
						</span>
					</div>
					
					<div class="mT10 container">
						<span style="float:left; width:40%">
							Date of Admission/Visit 
						</span>
						<span style="float:left">
							<form:input path="ADMISSION_DATE" class="  standardInputMedText mL5" />
						</span>
					</div>
					
					<div class="mT10 container">
						<span style="float:left; width:40%">
							Previous HIV Diagnosis 
						</span>
						<span style="float:left">
							<form:input path="PREV_HIV_DIAG_DATE_TEXT"   class="  standardInputMedText mL5" />
						</span>
					</div>
					
					<div class="mT10 container">
						<span style="float:left; width:40%">
							Previous AIDS Diagnosis
						</span>
						<span style="float:left">
							<form:input path="PREV_AIDS_DIAG_DATE_TEXT"  class="  standardInputMedText mL5" />
						</span>
					</div>
				</div>
													
								 
		</div>
		
		<!--  Provider details -->
		<div id="prvDetailsHeadingDiv" class="divHeadingCls mT20" style="background:#DDDDDD; ">
				<span class=" mL2">Current Assignment</span></div>
		<div class="container " style="border:1px dotted; width:100%; padding-bottom:5px;" id="assignmentAddressDtls">
			<div class="b1" style="margin: 15px 5px 5px 5px" id="labDocumentPrsnAddressDtls">
 	            <div class="personInfo pTRBL10 pB10">
 	            		<div style="width:50%;float:left">
 	            		<span class="">Name of Facility/Provider</span> 
	 	 	                    <span class="pL10"> <form:input path="" value="${assignmentAndAddressDetails.PROVIDER_FACILITY_NAME}" class="standardInputText " /></span>
	 	 	                   </div>
	 	 	                   <div style="width:50% ;float:left">
 	                	<span class="personLabelDoc">Address </span>
 		                               	
	 	                	<span class="pL10"><form:input path="" value="${assignmentAndAddressDetails.PROVIDER_FACILITY_ADDR}" class="standardInputText newAddress" />   </span>
	 	                	</div>
 	            </div>
 	            <div class="clearBoth" ></div>
 	            <div class="personInfo pTRBL10 pB10">
 	            		<div style="width:50%;float:left">
 	            		<span class="">County</span> 
 	            				
	 	 	                    <span class="pL10"> 
	 	 	                    	<form:input path="" value="${sessionScope.countyMap[assignmentAndAddressDetails.PROVIDER_FACILITY_COUNTY]}" class="standardInputText " />
	 	 	                    </span>
	 	 	                   </div>
	 	 	                   <div style="width:50% ;float:left">
 	                	<span class="personLabelDoc">Phone </span>
 		                               	
	 	                	<span class="pL10"><form:input path="" value="${assignmentAndAddressDetails.PROVIDER_FACILITY_PHONE}" class="standardInputText newAddress" />   </span>
	 	                	</div>
 	            </div>
 	            <div class="clearBoth" ></div>
 	            <div class="personInfo pTRBL10 pB10 marginBottom10">
	 	            <span style="margin-left:3px"> Date Assignment Created</span>
						<span> <form:input path="" value="${assignmentAndAddressDetails.ASSIGN_CREATE_DATE}" class=" standardInputSmallText2" /></span>
					<span class="mL20"> Date Assigned to Current Worker </span>
						<span> <form:input path="" value="${assignmentAndAddressDetails.WORKER_ASSIGN_DATE}"  class="  standardInputSmallText2" /> </span>
					<span class="mL20"> Current Worker </span>
						<span> <form:input path="" value="${assignmentAndAddressDetails.WORKER_NAME}" class="nOnly standardInputMedText" /> </span>
				</div>
 	        </div>					
			
		</div>	
		
		
		<div id="prvDetailsHeadingDiv" class="divHeadingCls mT20" style="background:#DDDDDD; ">
				<span class=" mL2">Person Completed Form</span></div>
		<div class="container " style="border:1px dotted; width:100%; padding-bottom:5px;" id="labDocumentProviderAddressDtls">	 
			<div style="margin-top:2px; margin-left:10px;  margin-right:5px;">
					<div class="mT10 marginBottom10">
					<div style="width:50%;float:left">
						<span style="float:left; width:40%"> Name</span><span style="float:left"> <form:input path="COMPLETER_NAME" class="standardInputMedText" /> </span>
					</div>
					
					<div style="width:50%;float:left">
						<span style="float:left; width:40%"> Title</span><span style="float:left"> <form:input path="COMPLETER_TITLE" class="standardInputMedText" /> </span>
					</div>
					</div>
					<div class="clearBoth" ></div>
					<div class="mT10 marginBottom10">
						<div style="width:50%;float:left">
							<span style="float:left; width:40%"> Facility Name</span><span style="float:left"> <form:input path="FACILITY_NAME" class="standardInputMedText" /> </span>
						</div>
					</div>
			</div>
			<div class="clearBoth" ></div>
			<div class="b1" style="margin: 15px 5px 5px 5px">
 	            <div class="personInfo pTRBL10 pB10">
 	                <span class="personLabelDoc"> Address </span>
 	                	<span> 	                	
	 	                	<span class="pL10"><form:input path="FACILITY_ADDRESS" class="oAdrDlg standardInputText newAddress " /></span>	 	                    
	 	                    
	 	                          	                    
	 	                    <br>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10 pB10">
 	                 
 					 <span class="">City</span>
                    <span class="pL10"> <form:input path="FACILITY_CITY" class=" oAdrDlg standardInputSmallText  newCity " readonly="" /> </span>
                    
                    <span class="pL30">State </span>
                    <span  class="pL10">
						<form:input path="FACILITY_STATE" class=" oAdrDlg standardInputMedText newState wS40 " readonly="" />
					</span>
                    
                    <span class="pL30">Zip </span>
                    <span class="pL10"> 
                        <form:input path="FACILITY_ZIP" class=" oAdrDlg nOnly  standardInputSmallText1 newZip " readonly="" maxlength="5"/> - 
					<form:input path="FACILITY_PLUS4" class=" oAdrDlg nOnly standardInputSmallText1 " maxlength="4"/>
                    </span>
                    <span class="pL30">County </span>
	                   <span class="pL10"> 
						<form:input id="FACILITY_COUNTY"  tabindex="8" path="FACILITY_COUNTY" class=" oAdrDlg standardInputMedText newCounty wC100 " readonly="" title="${county_title}" value="${sessionScope.countyMap[pRFDocumentVO.FACILITY_COUNTY]}"/>
					        
		               </span> 
		               
		            <span class="pL30"> Phone</span>
 					 <span class="pL10"> <form:input path="COMPLETER_PHONE" class=" pNumber standardInputSmallText3 phoneInputText" maxlength="14" /></span>
 	                <br>               
 	            </div>
 	        </div>						
		</div>
		
		
		<div id="prvDetailsHeadingDiv" class="divHeadingCls mT20" style="background:#DDDDDD; ">
				<span class=" mL2">Office Contact (If Other Than Person Completing Form)</span></div>
		<div class="container " style="border:1px dotted; width:100%; padding-bottom:5px;" id="labDocumentProviderAddressDtls">	 
			<div style="margin-top:2px; margin-left:10px;  margin-right:5px;">
					<div class="mT10 marginBottom10">
					<div style="width:50%;float:left">
						<span style="float:left; width:40%"> Name</span><span style="float:left"> <form:input path="CONTACT_NAME" class="standardInputMedText" /> </span>
					</div>
					
					<div style="width:50%;float:left">
						<span style="float:left; width:40%"> Title</span><span style="float:left"> <form:input path="CONTACT_TITLE" class="standardInputMedText" /> </span>
					</div>
					</div>
					<div class="clearBoth" ></div>
					<div class="mT10 marginBottom10">
						<div style="width:50%;float:left">
							<span style="float:left; width:40%"> Phone</span><span style="float:left"> <form:input path="CONTACT_PHONE" class="standardInputMedText" /> </span>
						</div>
					</div>
			</div>
		</div>
		
			
		
		
	</div>																	 										
</form:form>																



