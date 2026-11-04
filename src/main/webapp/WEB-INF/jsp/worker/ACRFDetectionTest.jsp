<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div style="width:99.9%;" class="container"></div>
		
		<div style="width:100%; padding:2px; background-color:#DBEAF9;" class="container">			
				<div style="float:left;">
					<span class="hdrFont mL5"> HIV Detection Tests (Qualitative)</span>
				</div>			
				<div style="float:right; margin-right:20px;">
					<span><img class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addQualTypeHIVDiv()" /> </span>
				</div>	
		</div>
		
		<div id="hivQualTestsDivContainer" class="container crfLabTest">
			<c:forEach items="${acrfFormVO.rawAcrfLabs}" var="hivABTstNonTypDiff" varStatus="statusQL">
			<c:if test="${ acrfFormVO.rawAcrfLabs[statusQL.index].parentTestType == '03' }">
			<div id= "${acrfFormVO.rawAcrfLabs[statusQL.index].labSrcId}" class="">
					
			<div class="hivQualTypeTestsDiv" style="float:left; width:99%; padding-bottom:5px; margin-left:5px; margin-right:5px; margin-top:2px; padding-top:2px;border-bottom:1px solid black" >
				<div class="floatLeft width90perc ">
					<div class="floatLeft width100perc marginBottom10"> 
						<div style="float:left; width:7%; ">
							<span class="hdr1Font"> Test : </span>
						</div>
						<div style="float:left; width:34%;" class="qlDrpDwn">
							<span class="mL20"> Test Type</span> 
							<form:select path="rawAcrfLabs[${statusQL.index}].acrfTestCode" id="rawAcrfLabs${statusQL.index}_acrfTestCode" class="qualTest acrf_TestCode" onchange="getResultForTestType(this, '03', 'hivQualTypeTestsDiv');"  title="Test Type">
								<form:option value="">Select One</form:option>
								<form:options items="${acrfFormVO.hivQualitativeTypTstMap}" itemLabel="acrtTestDesc" itemValue="acrtTestCode"/>
<%-- 								<c:if test="${isOldAcrfValue == 'Y'}"> --%>
								<c:if test="${acrfRevison == 2013}">
									<form:option  value="C2">HIV-1 P24 ANTIGEN</form:option>
								</c:if>
							</form:select>						
						</div>
						<c:if test="${acrfRevison > 2016}">
							<div style="float:left; width:25%;" class="mL20">
									<span class="mL10">Test Brand Name/Manufacturer </span>
									<span class="mL2"><form:select path="rawAcrfLabs[${statusQL.index}].manufacturer" id="rawAcrfLabs${statusQL.index}_manufacturer"  class="manufacturer standardInputSmallText3 " maxlength="40" title="Manufacturer">
											<form:option value="" >--select--</form:option>
											<c:forEach items="${manufacutrers}" var="manufacutrer">
												<form:option value="${manufacutrer.lookup_cd}" label="${manufacutrer.lookup_name}" name="${manufacutrer.lookup_name}"></form:option>
											</c:forEach>
										</form:select>
								  </span>
									<div class="v_error rawAcrfLabs${statusQL.index}_manufacturer"></div>	
								</div>
						</c:if>
					</div>
					
					<c:if test="${acrfRevison > 2016}">
						<div class="floatLeft width100perc marginBottom10 dropdownSection"> 
							<div>
							<div style="float:left; width:30%;" class="qlDrpDwn mL20">
								
								<span class="">Lab Name  </span>
								<span>
									<form:input path="rawAcrfLabs[${statusQL.index}].labName" id="rawAcrfLabs${statusQL.index}_labName" class="qualTest acrfLabs_Name standardInputLongText mL5 widgetFieldsReadonly"  title="Lab Name" tabindex="-1"></form:input>
									<form:hidden path="rawAcrfLabs[${statusQL.index}].clia" id="rawAcrfLabs${statusQL.index}_clia" class="acrfLabs_clia qualTest standardInputSmallText "   ></form:hidden>
								</span>		
								<div class="v_error rawAcrfLabs${statusQL.index}_labName"></div>
							</div>
								
							<div style="float:left; width:30%;">
								<span class="mL10">Facility Name  </span>
								<span>
									<form:input path="rawAcrfLabs[${statusQL.index}].facilityName" id="rawAcrfLabs${statusQL.index}_facilityName" class="qualTest acrfLabs_FacilityName standardInputLongText mL5 widgetFieldsReadonly"  title="Facility Name"  tabindex="-1"></form:input>
									<form:hidden path="rawAcrfLabs[${statusQL.index}].facilityEharsUid" id="rawAcrfLabs${statusQL.index}_facilityEharsUid" class="acrfLabs_FacilityEharsUid qualTest " ></form:hidden>
								</span>
								<div class="v_error rawAcrfLabs${statusQL.index}_facilityName"></div>
							</div>
							
							<div style="float:left; width:8%; "> 
									<span class="mL5"> <button class="editLabData" onclick="openLabWidget(this);return false;" value="Edit">Edit</button> </span>
								</div>
							</div>
							<div class="clearBoth mT10">
								
									
									
									<span class="hdrFont mL30"> Provider </span>
								
									<span class="mL10">First Name  </span>
									<span>
										<form:input path="rawAcrfLabs[${statusQL.index}].providerFname" id="rawAcrfLabs${statusQL.index}_providerFname" class="qualTest acrfLabs_ProvFname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider First Name"  tabindex="-1"></form:input>
									</span>
<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerFname"></div> --%>
								
									<span class="mL10">Middle Name  </span>
									<span>
										<form:input path="rawAcrfLabs[${statusQL.index}].providerMname" id="rawAcrfLabs${statusQL.index}_providerMname" class="qualTest acrfLabs_ProvMname standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Middle Name"  tabindex="-1"></form:input>
									</span>
<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerMname"></div> --%>
								
									<span class="mL10">Last Name  </span>
									<span>
										<form:input path="rawAcrfLabs[${statusQL.index}].providerLname" id="rawAcrfLabs${statusQL.index}_providerLname" class="qualTest acrfLabs_ProvLname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider Last Name"  tabindex="-1"></form:input>
									</span>
<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerLname"></div> --%>
								
									<span class="mL10">Suffix </span>
									<span>
										<form:input path="rawAcrfLabs[${statusQL.index}].providerSuffix" id="rawAcrfLabs${statusQL.index}_providerSuffix" class="qualTest acrfLabs_ProvSuffix standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Suffix"  tabindex="-1"></form:input>
									</span>
<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerSuffix"></div> --%>
								
							
							</div>
					</div>
					</c:if>
						
					
					
					
					
					
					
					<div class="floatLeft width100perc ">
						<div style="float:left; width:7%; ">
							<span class="hdr1Font"> Result : </span>
						</div>
						<div style="float:left; width:25%;" class="mL20">
							<span> <spring:message code="label.testResult" /></span> 
							<form:select path="rawAcrfLabs[${statusQL.index}].acrfResultCode" id="rawAcrfLabs${statusQL.index}_acrfResultCode" class="qualResult acrf_ResultCode mandatoryField" cssErrorClass="error"  title="Test Result">
								<form:option value="">Select One</form:option>		
								<form:options items="${acrfFormVO.rawAcrfLabs[statusQL.index].labResultMap}" />
							</form:select>
							<div class="v_error rawAcrfLabs${statusQL.index}_acrfResultCode"></div>					
						</div>	
						<div style="float:left;width:40%;">
							<span class="mL5">Collection Date: <form:input path="rawAcrfLabs[${statusQL.index}].collectionDate" id="rawAcrfLabs${statusQL.index}_collectionDate" datevalidations="completeDate"  value="${acrfFormVO.rawAcrfLabs[statusQL.index].collectionDate}" class="qualCollectionDate standardInputMedText dPicker mask acrf_collectionDate mandatoryField" maxlength="10" title="Collection Date" /></span>
							<div class="v_error rawAcrfLabs${statusQL.index}_collectionDate"></div>				
						</div>
					</div>
				</div>
				<div style="float:left width:10%;">
					<span class="mL2" style="float:right; margin-right:18px;"><img class="cloningIcons" onclick="deleteTestDiv(this, 'hivQualTypeTestsDiv');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span>
				</div>
			</div>
			<c:if test="${param.loadInDialog != 'Y'}">
				<script>  
					acrfRowCnt = acrfRowCnt+1;
				</script>
			</c:if>
			<form:hidden path="rawAcrfLabs[${statusQL.index}].isDeleted" class="deleteParams"/>
			<form:hidden path="rawAcrfLabs[${statusQL.index}].parentTestType" class="deleteParams"/>
			<form:hidden path="rawAcrfLabs[${statusQL.index}].labSrcId"  class="deleteParams"/>			
			</div>
			</c:if>
			</c:forEach>			
			
		</div>	