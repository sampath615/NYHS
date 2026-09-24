<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page
	import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link href="${staticPath}/css/docs/dthForm.css" rel="stylesheet"
	type="text/css" />

<script src="${staticPath}/js/docs/docViewers.js"></script>

	<style>

	
	#personViewcomparePage .verticalAlign{
		clear:both;
		margin-top:10px;
	}
	
	
	#personViewcomparePage #clusterPartnerDocVO .fLW40{
		width: 60% !important; 
		margin-top: 10px !important;
	}
	#personViewcomparePage #clusterPartnerDocVO .fLW30{
		width: 60% !important; 
/* 		margin-top: 10px !important; */
	}
	
	#personViewcomparePage #clusterPartnerDocVO .clusterMargin10{
		margin-top: 10px !important;
	}
	

#clusterPartnerDocVO .ui-accordion .ui-accordion-content:hover{
		padding: 10px 10px !important;
	}
	
#clusterPartnerDocVO .ui-accordion .ui-accordion-content{
		padding: 10px 10px !important;
	}
	
</style>

<form:form modelAttribute="clusterPartnerDocVO" id="clusterPartnerDocVO"
	cssClass="documentDescription documentFormVO">
	<form:errors path="*" cssClass="errorblock" element="div" />

	<div id="casaDoc" style="background-color: transparent">

		<div class="headingCls mT10" style="text-align: center">CLUSTER	PARTNER DOCUMENT</div>
		<div class="pB30 pT20">

			
			<c:if test="${isStdDocument == false}">
				<div class="floatRight ">
					<span class="mL10 bold">Partner ID: </span>
					<span class=" "><form:input path="PARTNER_ID"
							class="standardinputSmallText ml10 readonly" maxlength="6"
							readonly="true" /></span>
				</div>
			</c:if>
			<c:if test="${clusterPartnerDocVO.DOCUMENT_ID != null}">
				<div class="floatRight clearBoth mT10">
					<span class="mL10 bold">Document ID: </span>
					<span class=" "><form:input path="DOCUMENT_ID"
							class="standardinputSmallText ml10 readonly" maxlength="6"
							readonly="true" /></span>
				</div>
			</c:if>

			
			<div class="floatRight clearBoth mT10">
					<span class="mL10 bold"> Cluster Id: </span>
					<span class="ml10"> <form:input path="CLUSTER_ID" id="CLUSTER_ID"  class="standardinputSmallText readonly"  title="CLUSTER_ID"/> </span>
				</div>

		</div>

		<c:if test="${isStdDocument == true}">

			<div class="container pB10 w100 pT10">
				<div style="float: left; width: 40%">
					<div>
						<span class="ml10"> <spring:message code="label.stateNo" />:
						</span> <span class="ml10"><form:input path="STATENO"
								class="standardInputSmallText readonly" maxlength="10"
								readonly="true" /> </span>
					</div>
				</div>
				<div style="float: left; width: 30%">
					<div></div>
				</div>

			</div>

			<jsp:include page="statenoDetails.jsp"></jsp:include>
		</c:if>

		<!--  Person details -->

		<div class="container bold"
			style="border: 1px dotted; width: 100%; padding-bottom: 5px;">

			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Person Details</span>
			</div>
			<div class="mLRTB pB20">
				<div class="fLW25">
					<span class="fLW35"> First Name</span> 
					<span class="fLW60"> <form:input path="FNAME" id="FNAME" title="First Name" class="standardInputSmallText required" maxlength="15"/> </span>
				</div>
				<div class="fLW25">
					<span class="fLW35"> Middle Name</span>
					<span class="fLW60"> <form:input path="MNAME" title="Middle Name" class="standardInputSmallText" maxlength="1"/>  </span>
				</div>
				<div class="fLW25 ">
					<span class="fLW35"> Last Name</span>
					<span class="fLW60"> <form:input path="LNAME" id="LNAME" title="Last Name" class="standardInputSmallText required" maxlength="30"/>  </span>
				</div>
			</div>
			
			<div class="mLRTB pB20">
				<div class="fLW25">
					<span class="fLW35"> Date of Birth</span>
					<span class="fLW60"> <form:input id="DATE_OF_BIRTH" path="DATE_OF_BIRTH" title="Date of Birth" class="dPicker mask standardInputSmallText2"  datevalidations="completeDate" maxlength="15"/> </span>
				</div>
				<div class="fLW25">
					<span class="fLW35"> Date of Death</span>
					<span class="fLW60"> <form:input id="DATE_OF_DEATH" path="DATE_OF_DEATH" title="Date of Death" class="dPicker mask standardInputSmallText2"  datevalidations="completeDate" maxlength="15"/> </span>
				</div>
				<div class="fLW25">
					<span class="fLW35"> DIN</span>
					<span class="fLW60"> <form:input path="DIN" id="DIN"  class="standardInputSmallText2 " maxlength="25" title="DIN"/> </span>
				</div>
				<div class="fLW25">
					<span class="fLW35"> NYSID</span>
					<span class="fLW60"> <form:input path="NYSID" id="NYSID"  class="standardInputSmallText2 " maxlength="25" title="NYSID"/> </span>
				</div>
						
			</div>
			<div class="mLRTB pB20">
				<div class="fLW25">
					<span class="fLW35"> Birth country</span>
					<span class="fLW60"> 
						<form:select path="BIRTH_COUNTRY" id="BIRTH_COUNTRY" Class="standardInputSmallText  " title="Birth Country">
										<form:option value=""></form:option>
										<form:options items="${sessionScope.countryMap}"/>
										<form:option value="OTH">Other</form:option>
										<form:option value="UNK">Unknown</form:option>
									</form:select> 
					 </span>
				</div>
				
				
				<div class="fieldHolder45">
					<span class="fLW35"> Med Record Number</span>
					<span class="fLW60"> <form:input path="MEDRECNO" id="MEDRECNO"  class="standardInputSmallText2 " maxlength="25" title="Medical Record Number"/> </span>
				</div>
				
						
			</div>
			
			
			
			
			

			<div style="float:left; width:99%; margin-left:5px; margin-right:5px; margin-bottom:10px" class="mT5">
			
			<div class="clearBoth container    pL10 pB10" style="border:1px dotted">
 	            <div class="personInfo ">
 	            		<div class="personData  pB10 mT20">
	 	            		<div class="fLW50">
		 	            		<div class="fLW18">
									<span class="bold"> Address</span>
								</div>
								<div class="personinput">
										<form:input path="PERSON_ADDRESS" id="PERSON_ADDRESS" class="standardInputText  widgetAddressFieldsReadonly newAddress" maxlength="100" title="Address"/> 
									
								</div>
							</div>
							<div class="fLW25">
								<span class="fLW35"> City</span>
								<span class="fLW60"> <form:input path="PERSON_CITY" id="PERSON_CITY"  class="oAdrDlg standardInputSmallText newCity widgetAddressFieldsReadonly" maxlength="50" title="City"/> </span>
							</div>
							<div class="fLW25">
								<span class="fLW35"> State</span>
								<span class="fLW60"> <form:input path="PERSON_STATE" id="PERSON_STATE"   cssClass="oAdrDlg  newState standardInputSmallText1 widgetAddressFieldsReadonly" maxlength="2" title="State"/> </span>
							</div>
						</div>
						<div class="personData  pB10">
							
						
							<div class="fLW25">
								<span class="fLW35"> Zip</span>
								<span class="fLW60"> <form:input path="PERSON_ZIP" id="PERSON_ZIP"  class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5" title="Zip code"/> </span>
							</div>
							<div class="fLW25">
								<span class="fLW35"> County</span>
								<span class="fLW60"> 
										<form:select path="PERSON_COUNTY" id="PERSON_COUNTY"   cssClass="oAdrDlg standardInputSmallText newCounty widgetAddressFieldsReadonly" title="County"> 
											<form:option value=""></form:option>
											<form:options items="${sessionScope.countyMap}"/>
										</form:select>
								 </span>
							</div>
							<div class="fLW25">
								<span class="fLW35"> Home phone</span>
								<span class="fLW60"> <form:input path="HOME_PHONE" id="HOME_PHONE"  class="pNumber standardInputSmallText phoneInputText" maxlength="20" title="Phone"/> </span>
							</div>
							<div class="fLW25">
								<span class="fLW35"> Cell phone</span>
								<span class="fLW60"> <form:input path="CELL_PHONE" id="CELL_PHONE"  class="pNumber standardInputSmallText phoneInputText" maxlength="20" title="Phone"/> </span>
							</div>
						</div>
						
 	            		
 	            		
 	            </div>
 	            
 	        </div>					
			
		</div>

			<div id="raceEthnicity" class="clearBoth container" style="border:1px dotted; padding:5px 5px 0 0;">
				<div style="float:left; width:33%; border-right:1px dotted; ">	
					<div style="padding:0 0 10px 20px;">
							Race					
	
						<div style="">
	
							<form:checkbox path="RACE_WHITE" value="1" />White	
							<br>
							<form:checkbox path="RACE_BLACK" value="1" /> African American
							<br>
							<form:checkbox path="RACE_ASIAN" value="1" /> Asian
							<br>
							<form:checkbox path="RACE_PACIFIC" value="1" /> Pacific Islander
							<br>
							<form:checkbox path="RACE_INDIAN" value="1" /> American Indian/Alaskan Native
							<br>
	
							<form:checkbox path="RACE_OTHER" value="1" /> Other
							<br>
							<form:checkbox path="RACE_UNKNOWN" value="1" /> Unknown
							
						</div>	
					</div>
				
				
				
				</div>
				
			<div style="float:left; width:20%; border-right:1px dotted; ">
				<div style="padding:5px 0 5px 10px;">
					<spring:message code="label.ethnicity" /> 
					(Hispanic or Latino) </br>
							<form:radiobutton path="ETHNICITY" value="01" /> Yes <br>
							<form:radiobutton path="ETHNICITY" value="02" /> No <br>
							<form:radiobutton path="ETHNICITY" value="09" /> Unknown <br>
				</div>
				<div style="border-top: dotted 1px">
					<div style="padding:10px 0 0 10px;">	
						<div class="floatLeft pR10" style="">
							<div class=""> Birth Sex </div>
							<div  class="">
								<div> <form:radiobutton path="BIRTH_SEX" value="M" /> Male </div>
								<div> <form:radiobutton path="BIRTH_SEX" value="F" /> Female </div>
								<div> <form:radiobutton path="BIRTH_SEX" value="U" />Unknown</div>		
							</div>
						</div>
 					</div>				
				</div>
			
			</div>
				
			<div style="float:left; width:45% ;" >
				<div style="padding:5px 0 5px 10px;">
					
						
						<div class="floatLeft pR10" style="border-right: dotted 1px;">
							<div class="bold"> Current Gender </div>
							<div  class="">
								<div> <form:radiobutton path="GENDER" value="M" /> <spring:message code="label.male" /> </div>
								<div> <form:radiobutton path="GENDER" value="F" /> <spring:message code="label.female" /> </div>
								<div> <form:radiobutton path="GENDER" value="U" />Unknown</div>		
							</div>
						</div>
						<div class="floatLeft pL10 marginBottom10">
							<div class="bold"> Transgender </div>
							<div  class="">
								<div> <form:radiobutton path="GENDER" value="X" /> MTF </div>		
								<div> <form:radiobutton path="GENDER" value="Y" /> FTM </div>		
								<div> <form:radiobutton path="GENDER" value="T" /> Unspecified </div>														
							</div>
						</div> 
					
					
					
<!-- 					<div class="mT5 container"> -->
<!-- 						<div style="float:left; width:25%"> -->
<!-- 							Inactive date -->
<!-- 						</div> -->
<!-- 						<div style="float:left"> -->
<%-- 							<form:input path="INACTIVE_DATE" id="INACTIVE_DATE"  class="standardInputMedText mL5"  title="Inactive date"/> --%>
<!-- 						</div> -->
<!-- 					</div> -->
					
				
				 	
				</div>

			
	        
			</div>	
						
		</div>


			<!--  Race details -->




			<div class="partnerInvestigationAccordion">
				<c:forEach items="${clusterPartnerDocVO.partnerInvestigationList}"
					var="InvestigationDetails" varStatus="currentPartnerIndex">
					<h3 class="partnerInvestigationAccordion${currentPartnerIndex.index}">
						<span class="accordionHeader">Partner investigation details</span>
					</h3>
					<div id="partnerInvestigationAccordion${currentPartnerIndex.index}" class="partnerInvestigationContent"
						style="background-color: transparent; border: 1px dotted;"
						class="container">

						<c:set var="currentPartnerIndex" value="${currentPartnerIndex}" />


						<%@include file="clusterPartner/PartnerInvestigationDetails.jsp"%>

						<div class="container borderSSDMDoc">
							<div class="divHeadingCls bGHeading">
								<span class=" mL2"> Dispositions</span>
							</div>
							<div class="clearBoth container pT10  pB10 pL10" style="border:1px dotted">
								
								
								
								
								
								<div class="floatLeft">
									<span class="mL10 width60"> Condition</span> 
									<span class="mL10"> 
											
										<form:select
											path="partnerInvestigationList[${currentPartnerIndex.index}].CONDITION_1"
											class="standardInputMedText">
											<form:option value=""></form:option>

											<c:forEach items="${documentRefData}" var="columnData">
												<c:if test="${columnData.REF_COLUMN == 'CONDITION' }">
													<form:option value="${columnData.LOOKUP_CD}"
														label="${columnData.LOOKUP_NAME}"
														name="${columnData.LOOKUP_NAME}"></form:option>
												</c:if>
											</c:forEach>
										</form:select>
									</span>
								</div>
								<div class="floatLeft verticalAlign">
									<span class="mL10  width60"> Disposition date</span> 
									<span class="mL10"> 
											
										<form:input
											path="partnerInvestigationList[${currentPartnerIndex.index}].DISPOSITIONDATE1"
											class="standardInputSmallText2 " />
									</span>
								</div>
								
								<div class="floatLeft verticalAlign">
									<span class="mL10 width60"> Disposition</span> 
									<span class="mL10"> 
											
										<form:select
											path="partnerInvestigationList[${currentPartnerIndex.index}].DISPOSITION1"
											class="standardInputText">
											<form:option value=""></form:option>
											<c:forEach items="${dispositionCodeValues}"
												var="dispositionCode">
												<form:option value="${dispositionCode.DISPOSITION_CODE}"
													label="${dispositionCode.DISPOSITION_DESC}"></form:option>
											</c:forEach>
										</form:select>
									</span>
								</div>
							</div>
							
							
							<div class="clearBoth container pT10 pB10 pL10" style="border:1px dotted">
								
								<div class="floatLeft">
									<span class="mL10 width60"> Condition</span> 
									<span class="mL10 "> 
											
										<form:select
											path="partnerInvestigationList[${currentPartnerIndex.index}].CONDITION_2"
											class="standardInputMedText">
											<form:option value=""></form:option>

											<c:forEach items="${documentRefData}" var="columnData">
												<c:if test="${columnData.REF_COLUMN == 'CONDITION' }">
													<form:option value="${columnData.LOOKUP_CD}"
														label="${columnData.LOOKUP_NAME}"
														name="${columnData.LOOKUP_NAME}"></form:option>
												</c:if>
											</c:forEach>
										</form:select>
									</span>
								</div>
								<div class="floatLeft verticalAlign">
									<span class="mL10 width60 "> Disposition date</span> 
									<span class="mL10 "> 
											
										<form:input
											path="partnerInvestigationList[${currentPartnerIndex.index}].DISPOSITIONDATE2"
											class="standardInputSmallText2 " />
									</span>
								</div>
								
								<div class="floatLeft verticalAlign">
									<span class="mL10 width60"> Disposition</span> 
									<span class="mL10 "> 
											
										<form:select
											path="partnerInvestigationList[${currentPartnerIndex.index}].DISPOSITION2"
											class="standardInputText">
											<form:option value=""></form:option>
											<c:forEach items="${dispositionCodeValues}"
												var="dispositionCode">
												<form:option value="${dispositionCode.DISPOSITION_CODE}"
													label="${dispositionCode.DISPOSITION_DESC}"></form:option>
											</c:forEach>
										</form:select>
									</span>
								</div>
							</div>
							
							<div class="clearBoth container pT10 pB10 pL10" style="border:1px dotted">
								
								<div class="floatLeft ">
									<span class="mL10 width60"> Condition</span> 
									<span class="mL10 "> 
											
										<form:select
											path="partnerInvestigationList[${currentPartnerIndex.index}].CONDITION_3"
											class="standardInputMedText">
											<form:option value=""></form:option>

											<c:forEach items="${documentRefData}" var="columnData">
												<c:if test="${columnData.REF_COLUMN == 'CONDITION' }">
													<form:option value="${columnData.LOOKUP_CD}"
														label="${columnData.LOOKUP_NAME}"
														name="${columnData.LOOKUP_NAME}"></form:option>
												</c:if>
											</c:forEach>
										</form:select>
									</span>
								</div>
								<div class="floatLeft verticalAlign">
									<span class="mL10 width60 "> Disposition date</span> 
									<span class="mL10 "> 
											
										<form:input
											path="partnerInvestigationList[${currentPartnerIndex.index}].DISPOSITIONDATE3"
											class="standardInputSmallText2 " />
									</span>
								</div>
								
								<div class="floatLeft verticalAlign">
									<span class="mL10 width60"> Disposition</span> 
									<span class="mL10 "> 
											
										<form:select
											path="partnerInvestigationList[${currentPartnerIndex.index}].DISPOSITION3"
											class="standardInputText">
											<form:option value=""></form:option>
											<c:forEach items="${dispositionCodeValues}"
												var="dispositionCode">
												<form:option value="${dispositionCode.DISPOSITION_CODE}"
													label="${dispositionCode.DISPOSITION_DESC}"></form:option>
											</c:forEach>
										</form:select>
									</span>
								</div>
							</div>
							
							<div class="clearBoth container pT10  pB10 pL10" style="border:1px dotted">
								
								<div class="floatLeft ">
									<span class="mL10 width60"> Condition</span> 
									<span class="mL10 "> 
											
										<form:select
											path="partnerInvestigationList[${currentPartnerIndex.index}].CONDITION_4"
											class="standardInputMedText">
											<form:option value=""></form:option>

											<c:forEach items="${documentRefData}" var="columnData">
												<c:if test="${columnData.REF_COLUMN == 'CONDITION' }">
													<form:option value="${columnData.LOOKUP_CD}"
														label="${columnData.LOOKUP_NAME}"
														name="${columnData.LOOKUP_NAME}"></form:option>
												</c:if>
											</c:forEach>
										</form:select>
									</span>
								</div>
								<div class="floatLeft verticalAlign">
									<span class="mL10 width60"> Disposition date</span> 
									<span class="mL10 "> 
											
										<form:input
											path="partnerInvestigationList[${currentPartnerIndex.index}].DISPOSITIONDATE4"
											class="standardInputSmallText2 " />
									</span>
								</div>
								
								<div class="floatLeft verticalAlign">
									<span class="mL10 width60" > Disposition</span> 
									<span class="mL10 "> 
											
										<form:select
											path="partnerInvestigationList[${currentPartnerIndex.index}].DISPOSITION4"
											class="standardInputText">
											<form:option value=""></form:option>
											<c:forEach items="${dispositionCodeValues}"
												var="dispositionCode">
												<form:option value="${dispositionCode.DISPOSITION_CODE}"
													label="${dispositionCode.DISPOSITION_DESC}"></form:option>
											</c:forEach>
										</form:select>
									</span>
								</div>
							</div>
							
							
							
							

						</div>
						<div
							style="padding-top: 5px; padding-bottom: 5px; border: 1px dotted;"
							class="container bold">
							<div style="float: left; width: 70%; padding-left: 10px;">

								<div class="mT5 container">
									<div style="float: left; width: 90%">
										<span for="FIELDNOTE">Field note</span>
									</div>
									<div style="float: left">
										<form:textarea title="Field note" maxlength="4000" cols="200"
											rows="3"
											path="partnerInvestigationList[${currentPartnerIndex.index}].FIELDNOTE"
											style="width:65%; height:67px; border:1px solid gray;"
											class="roundedBorder"></form:textarea>
									</div>
								</div>
							</div>
						</div>

					</div>
				</c:forEach>
			</div>

			
		</div>
	</div> 
	<c:out value="${clusterPartnerDocVO.psMessageExist}" />
	<c:out value="${psMessageReasonDetails.RSN_NO_PS_MSG}" />
	<c:out value="${psMessageReasonDetails.RSN_PS_MSG}" />
	
	<div id="button-row" class="button-row clear documentButtons mT10"  align="center">
	 	<c:if test="${clusterPartnerDocVO.psMessageExist == 'No'   &&  psMessageReasonDetails.RSN_NO_PS_MSG == 'N'   &&  psMessageReasonDetails.RSN_PS_MSG == 'N'}"> 
	 		<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3 displayNone"  value="Trigger Message"  onclick="confirmAddressForPsMessage('Y')"/>
	 	
	 		<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3 displayNone"  value="Reject PS Message"  onclick="rejectPsMessage()"/>
	 	</c:if>
	 </div>
 	
</form:form>

<script>
	$(".partnerInvestigationAccordion").accordion();
	$(".partnerInvestigationAccordion, .partnerInvestigationContent").css("height","auto");
</script>