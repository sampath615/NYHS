<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id= "${acrfFormVO.rawAcrfLabs[statusHNT.index].labSrcId}" class="">
				<div class="hivNonTypeTestsDiv acrfLabTestData" style="float:left; width:99%; padding:2px; margin-left:5px; margin-right:5px; margin-top:2px; border-bottom:1px solid black" >
					<div class="floatLeft width90perc">
						<div class="floatLeft width100perc marginBottom10"> 
							<div style="float:left; width:7%; ">
								<span class="hdr1Font"> Test : </span>
							</div>
							<div style="float:left; width:25%;" class="abtNTDrpDwn mL20">
								<span> Test Type 
								<form:select path="rawAcrfLabs[${statusHNT.index}].acrfTestCode" id="rawAcrfLabs${statusHNT.index}_acrfTestCode" class="abtNTTest acrf_TestCode" onchange="getResultForTestType(this, '01', 'hivNonTypeTestsDiv');" title="Test Type">
									<form:option value="">Select One</form:option>
									<form:options items="${acrfFormVO.labTestResultMap[testResultSection]}" itemLabel="acrtTestDesc" itemValue="acrtTestCode"/>
								</form:select>	
								</span>		
								
							</div>
							<div id="rawAcrfLabs${statusHNT.index}_acrfTestCodeOther" style="float:left; width:22%;<c:if test='${acrfRevison > 2013 || hivABTstNonTypDiff.otherTest == null || hivABTstNonTypDiff.otherTest == \"\"}'>display:none</c:if>">
								 <span class="abtNTOtherDes" >
									<span> Other </span> <form:input path="rawAcrfLabs[${statusHNT.index}].otherTest" id="rawAcrfLabs${statusHNT.index}_otherTest" value="${hivABTstNonTypDiff.otherTest}" class="standardInputSmallText3 abtNTOtherTestType" maxlength="40" title="Other test Type"/> 
								</span>
								<div class="v_error rawAcrfLabs${statusHNT.index}_otherTest"></div>
								
							</div>	
							<div style="float:left; width:40%;">
								<span class="mL10">Test Brand Name/Manufacturer </span>
								<span class="mL2"><form:select path="rawAcrfLabs[${statusHNT.index}].manufacturer" id="rawAcrfLabs${statusHNT.index}_manufacturer" value="${hivABTstNonTypDiff.manufacturer}" class="manufacturer standardInputSmallText3 " maxlength="40" title="Manufacturer">
								
										<form:option value="" >--select--</form:option>
										<c:forEach items="${manufacutrers}" var="manufacutrer">
											<form:option value="${manufacutrer.lookup_cd}" label="${manufacutrer.lookup_name}" name="${manufacutrer.lookup_name}"></form:option>
										</c:forEach>
									</form:select>
							  </span>
								<div class="v_error rawAcrfLabs${statusHNT.index}_manufacturer"></div>	
							</div>
								
						</div>
						<c:set var="statusIndex" value="${statusHNT.index}" scope="request"/>
						<jsp:include page="acrfLabFacility.jsp" ></jsp:include>
						
						<div class="floatLeft width100perc">
								<div style="float:left; width:7%; ">
									<span class="hdr1Font"> Result  </span>
								</div>
								
								<div style="float:left; width:25%;" class="abtNTDrpDwn mL20">
									<span> Test Result</span>
									<form:select path="rawAcrfLabs[${statusHNT.index}].acrfResultCode" id="rawAcrfLabs${statusHNT.index}_acrfResultCode" class="abtNTResults acrf_ResultCode mandatoryField" cssErrorClass="error" title="Test Result">
										<form:option value="">Select One</form:option>		
										<form:options items="${acrfFormVO.rawAcrfLabs[statusHNT.index].labResultMap}" />
									</form:select>
									<div class="v_error rawAcrfLabs${statusHNT.index}_acrfResultCode">
										
									</div>	
								</div>
								<div style="float:left; width:25%;">
									<span class="mL5">Collection Date: <form:input path="rawAcrfLabs[${statusHNT.index}].collectionDate" id="rawAcrfLabs${statusHNT.index}_collectionDate"  datevalidations="completeDate" class="abtNTCollectionDate cDateVal standardInputSmallText  dPicker mask acrf_collectionDate mandatoryField" value="${hivABTstNonTypDiff.collectionDate}" title="Collection Date" /> </span>
									<div class="v_error rawAcrfLabs${statusHNT.index}_collectionDate">
										
									</div>				
								</div>
								<c:if test="${acrfRevison < 2023}">
									<div style="float:left; width:25%;">
										<span class="mL10"><form:checkbox path="rawAcrfLabs[${statusHNT.index}].rapidTest" id="rawAcrfLabs${statusHNT.index}_rapidTest" class="abtNTRapid" value="${hivABTstNonTypDiff.rapidTest}"/></span>
										<span class="mL2">RAPID TEST <I>(check if rapid)</I> </span>			
									</div>
								</c:if>
								
								
							
							</div>
							<jsp:include page="acrfTestingOptions.jsp" ></jsp:include>
						</div>		
					<div style="float:left width:2%;">
						<span class="mL2" style="float:right; margin-top:4px;"><img class="cloningIcons" onclick="deleteTestDiv(this, 'hivNonTypeTestsDiv');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span>
					</div>
					
					<form:hidden path="rawAcrfLabs[${statusHNT.index}].isDeleted" class="deleteParams"/>
					<form:hidden path="rawAcrfLabs[${statusHNT.index}].parentTestType" class="deleteParams"/>
					<form:hidden path="rawAcrfLabs[${statusHNT.index}].labSrcId" class="deleteParams rawAcrfLabSrcId" />
				</div>
				
					
				
				</div>