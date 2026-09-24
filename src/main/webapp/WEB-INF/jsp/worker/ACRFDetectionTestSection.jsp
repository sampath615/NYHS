<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<div id= "${acrfFormVO.rawAcrfLabs[statusQL.index].labSrcId}" class="">
					
			<div class="hivQualTypeTestsDiv acrfLabTestData" style="float:left; width:99%; padding-bottom:5px; margin-left:5px; margin-right:5px; margin-top:2px; padding-top:2px;border-bottom:1px solid black" >
				<div class="floatLeft width90perc ">
					<div class="floatLeft width100perc marginBottom10"> 
						<div style="float:left; width:7%; ">
							<span class="hdr1Font"> Test : </span>
						</div>
						<div style="float:left; width:34%;" class="qlDrpDwn">
							<span class="mL20"> Test Type</span> 
							<form:select path="rawAcrfLabs[${statusQL.index}].acrfTestCode" id="rawAcrfLabs${statusQL.index}_acrfTestCode" class="qualTest acrf_TestCode" onchange="getResultForTestType(this, '03', 'hivQualTypeTestsDiv');"  title="Test Type">
								<form:option value="">Select One</form:option>
								<form:options items="${acrfFormVO.labTestResultMap[testResultSection]}" itemLabel="acrtTestDesc" itemValue="acrtTestCode"/>
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
					
					<c:set var="statusIndex" value="${statusQL.index}" scope="request"/>
					<jsp:include page="acrfLabFacility.jsp" ></jsp:include>
					
					
					
					
					
					<div class="floatLeft width100perc ">
						<div style="float:left; width:7%; ">
							<span class="hdr1Font"> 
									Result
							</span>
						</div>
						<div style="float:left; width:25%;" class="mL20">
							<span> 
								<c:choose>
								<c:when test="${testResultSection  == '3_2'}">
									Qualitative
								</c:when>
								<c:otherwise>
									<spring:message code="label.testResult" />
								</c:otherwise>
							</c:choose>
							</span> 
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
					
					<c:if test="${testResultSection  == '3_2'}">
					
						<div class="floatLeft width100perc mT10">
										
							<div class=" analyteResultsSection dependentElements">
								<div style="float:left; width:10%; ">
									<span class="hdr1Font">Analyte results: </span>
								</div>
								
							
								
								<div class=" floatLeft " style="width:40%">
									<div style="width:80%;" class="floatLeft">
										<span>  HIV-1 Quantitative: </span>
										<span>
													<form:select path="rawAcrfLabs[${statusQL.index}].analyteInterpretation" id="rawAcrfLabs${statusQL.index}_analyteInterpretation" class="abtTAnalyteResults analyteResultsDropDown"    title="HIV-1 C/ML Quantitative">
														<form:option value="">Select One</form:option>		
														<form:option value=">" >Detectable above limit</form:option>
														<form:option value="=" >Detectable within limits</form:option>
														<form:option value="&lt;" >Detectable below limit</form:option>
													</form:select>
										</span>
										
									</div>
											
								</div>
								<div style="width:25%;" class="floatLeft ">
										<span class="mL5 "> Copies/mL: <form:input path="rawAcrfLabs[${statusQL.index}].hiv1CmlAnalyteResult"  id="rawAcrfLabs${statusQL.index}_hiv1CmlAnalyteResult" class="standardInputMedText abtTAnalyteResults" maxlength="100" title="Copies/mL" /> </span>
								</div>
								
								<div style="width:25%;" class="floatLeft ">
										<span class="mL5 "> Log: <form:input path="rawAcrfLabs[${statusQL.index}].hiv1LogAnalyteResult"  id="rawAcrfLabs${statusQL.index}_hiv1LogAnalyteResult" class="standardInputMedText abtTAnalyteResults" maxlength="100" title="Log" /> </span>
								</div>
							
						
							</div>
						</div>
					</c:if>
					<jsp:include page="acrfTestingOptions.jsp" ></jsp:include>
				</div>
				<div style="float:left width:10%;">
					<span class="mL2" style="float:right; margin-right:18px;"><img class="cloningIcons" onclick="deleteTestDiv(this, 'hivQualTypeTestsDiv');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span>
				</div>
				<form:hidden path="rawAcrfLabs[${statusQL.index}].isDeleted" class="deleteParams"/>
				<form:hidden path="rawAcrfLabs[${statusQL.index}].parentTestType" class="deleteParams"/>
				<form:hidden path="rawAcrfLabs[${statusQL.index}].labSrcId"  class="deleteParams"/>			
			</div>

			</div>		
		