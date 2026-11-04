<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<form:form modelAttribute="stdADAPDocVO" id="stdADAPDocVO" cssClass="documentDescription mT40">
	<div id="triggeringAdapDocTab" style="background-color: transparent">
		<div class="headingCls marginBottom10 alignCenter">
				Assignment Details - UCP Match
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
							<form:input path="DOB_TEXT" class="standardInputMedText" />
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
					
					<div class="mT10 container">
						<span style="float:left; width:45%">
							Diagnosis date
						</span>
						<span style="float:left">
							<form:input path="FIRST_HIV_TEST_DATE_TEXT" class="standardInputMedText" />
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
							<form:input path="" class="standardInputMedText" />
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
			
		
		
				
				
			<!--  Race details -->
		<div id="matchDetailsHeadingDiv" class="divHeadingCls mT20" style="background:#DDDDDD; ">
			<span class=" mL2">Match Details</span>
		</div>
		<div style="padding-top:5px; padding-bottom:5px; border:1px dotted;" class="container">	 	 
			<div style="float:left; width:49%; padding-left:10px; ">
					
				<div class="mT10 container">
					<span style="float:left; width:45%">
						File Received
					</span>
					<span style="float:left">
						<fmt:formatDate value="${stdADAPDocVO.RECEIVED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedRECEIVED_DATE" ></fmt:formatDate>
						<form:input path="RECEIVED_DATE"  value="${theFormattedRECEIVED_DATE}" class="  standardInputMedText mL5" />
					</span>
				</div>

			</div>
			<div style="float:left; width:49%; padding-bottom:5px; padding-left:10px; border-left:1px dotted;">
					
					<div class="mT10 container">
						<span style="float:left; width:40%">
							Record Processed
						</span>
						<span style="float:left">
							<fmt:formatDate value="${stdADAPDocVO.CREATE_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedCREATE_DATE" ></fmt:formatDate>
							<form:input path="CREATE_DATE" value="${theFormattedCREATE_DATE}"  class="  standardInputMedText mL5" />
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
 	            		<span class="">Name of Facility</span> 
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
				<span class=" mL2">Prescribing Provider on Triggering Document</span></div>
		<div class="container " style="border:1px dotted; width:100%; padding-bottom:5px;" id="labDocumentProviderAddressDtls">	 
			<div style="margin-top:2px; margin-left:10px;  margin-right:5px;">
					<div class="mT10 marginBottom10">
					<div style="width:50%;float:left">
						<span style="float:left; width:40%"> <spring:message code="label.firstname" /></span><span style="float:left"> <form:input path="RX_PROV_FNAME" class="standardInputMedText" /> </span>
					</div>
					
					<div style="width:50%;float:left">
						<span style="float:left; width:40%"> <spring:message code="label.middleName" /></span><span style="float:left"> <form:input path="RX_PROV_MNAME" class="standardInputMedText" /> </span>
					</div>
					</div>
					<div class="clearBoth" ></div>
					<div class="mT10 marginBottom10">
						<div style="width:50%;float:left">
							<span style="float:left; width:40%"> <spring:message code="label.lastname" /></span><span style="float:left"> <form:input path="RX_PROV_LNAME" class="standardInputMedText" /> </span>
						</div>
					</div>
			</div>
			<div class="clearBoth" ></div>
			<div class="b1" style="margin: 15px 5px 5px 5px">
 	            <div class="personInfo pTRBL10 pB10">
 	                <span class="personLabelDoc"> Address </span>
 	                	<span> 	                	
	 	                	<span class="pL10"><form:input path="RX_PROV_ADDRESS" class="oAdrDlg standardInputText newAddress " /></span>	 	                    
	 	                    
	 	                          	                    
	 	                    <br>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10 pB10">
 	                 
 					 <span class="">City</span>
                    <span class="pL10"> <form:input path="RX_PROV_CITY" class=" oAdrDlg standardInputSmallText  newCity " readonly="" /> </span>
                    
                    <span class="pL30">State </span>
                    <span  class="pL10">
						<form:input path="RX_PROV_STATE" class=" oAdrDlg standardInputMedText newState wS40 " readonly="" />
					</span>
                    
                    <span class="pL30">Zip </span>
                    <span class="pL10"> 
                        <form:input path="RX_PROV_ZIP" class=" oAdrDlg nOnly  standardInputSmallText1 newZip " readonly="" maxlength="5"/> - 
					<form:input path="RX_PROV_PLUS4" class=" oAdrDlg nOnly standardInputSmallText1 " maxlength="4"/>
                    </span>
                    <span class="pL30">County </span>
	                   <span class="pL10"> 
						<form:input id="RX_PROV_COUNTY"  tabindex="8" path="RX_PROV_COUNTY" class=" oAdrDlg standardInputMedText newCounty wC100 " readonly="" title="${county_title}" value="${sessionScope.countyMap[stdADAPDocVO.RX_PROV_COUNTY]}"/>
					        
		               </span> 
		               
		            <span class="pL30"> Phone</span>
 					 <span class="pL10"> <form:input path="RX_PROV_PHONE" class=" pNumber standardInputSmallText3 phoneInputText" maxlength="14" /></span>
 	                <br>               
 	            </div>
 	        </div>						
		</div>
		
		
		<div id="prvDetailsHeadingDiv" class="divHeadingCls mT20" style="background:#DDDDDD; ">
				<span class=" mL2">Primary Care Provider on Triggering Document</span></div>
		<div class="container " style="border:1px dotted; width:100%; padding-bottom:5px;" id="labDocumentProviderAddressDtls">	 
			<div style="margin-top:2px; margin-left:10px;  margin-right:5px;">
					<div class="mT10 marginBottom10">
					<div style="width:50%;float:left">
						<span style="float:left; width:40%"> <spring:message code="label.firstname" /></span><span style="float:left"> <form:input path="PCP_PROV_FNAME" class="standardInputMedText" /> </span>
					</div>
					
					<div style="width:50%;float:left">
						<span style="float:left; width:40%"> <spring:message code="label.middleName" /></span><span style="float:left"> <form:input path="PCP_PROV_MNAME" class="standardInputMedText" /> </span>
					</div>
					</div>
					<div class="clearBoth" ></div>
					<div class="mT10 marginBottom10">
						<div style="width:50%;float:left">
							<span style="float:left; width:40%"> <spring:message code="label.lastname" /></span><span style="float:left"> <form:input path="PCP_PROV_LNAME" class="standardInputMedText" /> </span>
						</div>
					</div>
			</div>
			<div class="clearBoth" ></div>
			<div class="b1" style="margin: 15px 5px 5px 5px">
 	            <div class="personInfo pTRBL10 pB10">
 	                <span class="personLabelDoc"> Address </span>
 	                	<span> 	                	
	 	                	<span class="pL10"><form:input path="PCP_PROV_ADDRESS" class="oAdrDlg standardInputText newAddress " /></span>	 	                    
	 	                    
	 	                          	                    
	 	                    <br>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10 pB10">
 	                 
 					 <span class="">City</span>
                    <span class="pL10"> <form:input path="PCP_PROV_CITY" class=" oAdrDlg standardInputSmallText  newCity " readonly="" /> </span>
                    
                    <span class="pL30">State </span>
                    <span  class="pL10">
						<form:input path="PCP_PROV_STATE" class=" oAdrDlg standardInputMedText newState wS40 " readonly="" />
					</span>
                    
                    <span class="pL30">Zip </span>
                    <span class="pL10"> 
                        <form:input path="PCP_PROV_ZIP" class=" oAdrDlg nOnly  standardInputSmallText1 newZip " readonly="" maxlength="5"/> - 
					<form:input path="PCP_PROV_PLUS4" class=" oAdrDlg nOnly standardInputSmallText1 " maxlength="4"/>
                    </span>
                    <span class="pL30">County </span>
	                   <span class="pL10"> 
						<form:input id="PCP_PROV_COUNTY"  tabindex="8" path="PCP_PROV_COUNTY" class=" oAdrDlg standardInputMedText newCounty wC100 " readonly="" title="${county_title}" value="${sessionScope.countyMap[stdADAPDocVO.PCP_PROV_COUNTY]}"/>
					        
		               </span> 
		               
		            <span class="pL30"> Phone</span>
 					 <span class="pL10"> <form:input path="PCP_PROV_PHONE" class=" pNumber standardInputSmallText3 phoneInputText" maxlength="14" /></span>
 	                <br>               
 	            </div>
 	        </div>						
		</div>
		
		
		
		<div id="prvDetailsHeadingDiv" class="divHeadingCls mT20" style="background:#DDDDDD; ">
				<span class=" mL2">Certifying Provider on Triggering Document</span></div>
		<div class="container " style="border:1px dotted; width:100%; padding-bottom:5px;" id="labDocumentProviderAddressDtls">	 
			<div style="margin-top:2px; margin-left:10px;  margin-right:5px;">
					<div class="mT10 marginBottom10">
					<div style="width:50%;float:left">
						<span style="float:left; width:40%"> <spring:message code="label.firstname" /></span><span style="float:left"> <form:input path="CERT_PROV_FNAME" class="standardInputMedText" /> </span>
					</div>
					
					<div style="width:50%;float:left">
						<span style="float:left; width:40%"> <spring:message code="label.middleName" /></span><span style="float:left"> <form:input path="CERT_PROV_MNAME" class="standardInputMedText" /> </span>
					</div>
					</div>
					<div class="clearBoth" ></div>
					<div class="mT10 marginBottom10">
						<div style="width:50%;float:left">
							<span style="float:left; width:40%"> <spring:message code="label.lastname" /></span><span style="float:left"> <form:input path="CERT_PROV_LNAME" class="standardInputMedText" /> </span>
						</div>
					</div>
			</div>
			<div class="clearBoth" ></div>
			<div class="b1" style="margin: 15px 5px 5px 5px">
 	            <div class="personInfo pTRBL10 pB10">
 	                <span class="personLabelDoc"> Address </span>
 	                	<span> 	                	
	 	                	<span class="pL10"><form:input path="CERT_PROV_ADDRESS" class="oAdrDlg standardInputText newAddress " /></span>	 	                    
	 	                    
	 	                          	                    
	 	                    <br>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10 pB10">
 	                 
 					 <span class="">City</span>
                    <span class="pL10"> <form:input path="CERT_PROV_CITY" class=" oAdrDlg standardInputSmallText  newCity " readonly="" /> </span>
                    
                    <span class="pL30">State </span>
                    <span  class="pL10">
						<form:input path="CERT_PROV_STATE" class=" oAdrDlg standardInputMedText newState wS40 " readonly="" />
					</span>
                    
                    <span class="pL30">Zip </span>
                    <span class="pL10"> 
                        <form:input path="CERT_PROV_ZIP" class=" oAdrDlg nOnly  standardInputSmallText1 newZip " readonly="" maxlength="5"/> - 
					<form:input path="CERT_PROV_PLUS4" class=" oAdrDlg nOnly standardInputSmallText1 " maxlength="4"/>
                    </span>
                    <span class="pL30">County </span>
	                   <span class="pL10"> 
						<form:input id="CERT_PROV_COUNTY"  tabindex="8" path="CERT_PROV_COUNTY" class=" oAdrDlg standardInputMedText newCounty wC100 " readonly="" title="${county_title}" value="${sessionScope.countyMap[stdADAPDocVO.CERT_PROV_COUNTY]}"/>
					        
		               </span> 
		               
		            <span class="pL30"> Phone</span>
 					 <span class="pL10"> <form:input path="CERT_PROV_PHONE" class=" pNumber standardInputSmallText3 phoneInputText" maxlength="14" /></span>
 	                <br>               
 	            </div>
 	        </div>						
		</div>
			
		
		
	</div>																	 										
</form:form>																



