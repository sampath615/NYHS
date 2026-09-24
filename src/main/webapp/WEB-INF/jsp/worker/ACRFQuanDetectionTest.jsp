<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id= "${acrfFormVO.rawAcrfLabs[statusQN.index].labSrcId}" class="">
			<div class="hivQuanTypeTestsDiv acrfLabTestData" style="float:left; width:99%; padding-top:5px; margin-left:5px; margin-right:5px; margin-top:2px;border-bottom:1px solid black" >
				<div class="floatLeft width90perc ">
					<div class="floatLeft width100perc marginBottom10">  
						<div style="float:left; width:7%;">
							<span class="hdr1Font"> Test : </span>
						</div>				
						<div style="float:left; width:30%;" class="qnDrpDwn">
							<span class="mL20"> Test Type </span> 
							<form:select path="rawAcrfLabs[${statusQN.index}].acrfTestCode" id="rawAcrfLabs${statusQN.index}_acrfTestCode" class="quntTst acrf_TestCode" onchange="getResultForTestType(this, '04', 'hivQuanTypeTestsDiv');"  title="Test Type">
								<form:option value="">Select One</form:option>
								<form:options items="${acrfFormVO.labTestResultMap[testResultSection]}" itemLabel="acrtTestDesc" itemValue="acrtTestCode"/>
							</form:select>					
						</div>
						
						
						<c:if test="${acrfRevison > 2016}">
							<div style="float:left; width:25%;" class="mL20">
									<span class="mL10">Test Brand Name/Manufacturer </span>
									<span class="mL2"><form:select path="rawAcrfLabs[${statusQN.index}].manufacturer" id="rawAcrfLabs${statusQN.index}_manufacturer"  class="manufacturer standardInputSmallText3 " maxlength="40" title="Manufacturer">
											<form:option value="" >--select--</form:option>
											<c:forEach items="${manufacutrers}" var="manufacutrer">
												<form:option value="${manufacutrer.lookup_cd}" label="${manufacutrer.lookup_name}" name="${manufacutrer.lookup_name}"></form:option>
											</c:forEach>
										</form:select>
								  	</span>
									<div class="v_error rawAcrfLabs${statusQN.index}_manufacturer"></div>	
								</div>
						</c:if>
					</div>
					
					<c:set var="statusIndex" value="${statusQN.index}" scope="request"/>
					<jsp:include page="acrfLabFacility.jsp" ></jsp:include>
							
					<div class="floatLeft width100perc">
					
						<c:if test="${acrfRevison < 2022}">
					
							<div style="float:left; width:7%;">
								<span class="hdr1Font"> Result  </span>
							</div>				
							
							<div style="float:left; width:25%;">
								<span class="mL20"> <spring:message code="label.testResult" /></span> 
								<form:select path="rawAcrfLabs[${statusQN.index}].acrfResultCode" id="rawAcrfLabs${statusQN.index}_acrfResultCode" class="quanResult acrf_ResultCode mandatoryField" cssErrorClass="error"  title="Test Result">
									<form:option value="">Select One</form:option>		
									<form:options items="${acrfFormVO.rawAcrfLabs[statusQN.index].labResultMap}" />
								</form:select>	
								<div class="v_error rawAcrfLabs${statusQN.index}_acrfResultCode"></div>				
							</div>
						</c:if>
						
						

												
					</div>
					
					<c:if test="${testResultSection  == '4_1'}">
					
						<div class="floatLeft width100perc mT10">
										
							<div class=" analyteResultsSection dependentElements">
								<div style="float:left; width:10%; ">
									<span class="hdr1Font">Result </span>
								</div>
								
							
								
								<div class=" floatLeft " style="width:20%">
									<div style="width:80%;" class="floatLeft">
										<span>
													<form:select path="rawAcrfLabs[${statusQN.index}].interpretation" id="rawAcrfLabs${statusQN.index}_interpretation" class="ainterpretation acrf_ResultCode mandatoryField"    title="Result Interpretation" onchange="disableNDFields(this)">
														<form:option value="">Select One</form:option>		
														<form:option value=">" >Detectable above limit</form:option>
														<form:option value="=" >Detectable within limits</form:option>
														<form:option value="&lt;" >Detectable below limit</form:option>
														<form:option value="ND" >Not detected</form:option>
													</form:select>
										</span>
										
									</div>
											
								</div>
								<div style="float: left; width: 20%;">
									<span class="mL5"> Collection Date: <form:input path="rawAcrfLabs[${statusQN.index}].collectionDate" id="rawAcrfLabs${statusQN.index}_collectionDate" datevalidations="completeDate" value="${acrfFormVO.rawAcrfLabs[statusQN.index].collectionDate}" class=" standardInputSmallText dPicker mask quntCollectionDate acrf_collectionDate mandatoryField" cssStyle="width: 95px;" maxlength="10" title="Collection Date" /> </span>
									<div class="v_error rawAcrfLabs${statusQN.index}_collectionDate"></div>		
								</div>	
								
								<c:set var="analyteSectionClass" value=""/>
								<c:if test="${acrfFormVO.rawAcrfLabs[statusQN.index].interpretation == 'ND'}">
									<c:set var="analyteSectionClass" value="readonly"/>
								
								</c:if>
								
								<div style="width:20%;" class="floatLeft ">
										<span class="mL5 "> Copies/mL: <form:input path="rawAcrfLabs[${statusQN.index}].copiesMl"  id="rawAcrfLabs${statusQN.index}_copiesMl" class="${analyteSectionClass} standardInputMedText abtTAnalyteResults CLData" maxlength="100" title="Copies/mL" /> </span>
										<div class="v_error rawAcrfLabs${statusQN.index}_copiesMl"></div>
								</div>
								<div style="width:20%;" class="floatLeft ">
										<span class="mL5 "> Log: <form:input path="rawAcrfLabs[${statusQN.index}].log"  id="rawAcrfLabs${statusQN.index}_log" class="${analyteSectionClass} standardInputMedText abtTAnalyteResults CLData" maxlength="100" title="Log" /> </span>
										<div class="v_error rawAcrfLabs${statusQN.index}_log"></div>
								</div>
								
								
								
							</div>
						</div>
					</c:if>
					<jsp:include page="acrfTestingOptions.jsp" ></jsp:include>
					
				</div>
				<div style="float:left width:10%;">
					<span class="mL2" style="float:right; margin-right:18px; cursor:pointer"><img class="cloningIcons" onclick="deleteTestDiv(this, 'hivQuanTypeTestsDiv');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span>
				</div>
				<form:hidden path="rawAcrfLabs[${statusQN.index}].isDeleted" class="deleteParams"/>
				<form:hidden path="rawAcrfLabs[${statusQN.index}].parentTestType" class="deleteParams"/>	
				<form:hidden path="rawAcrfLabs[${statusQN.index}].labSrcId"  class="deleteParams"/>	
			</div>	
			
			
			</div>