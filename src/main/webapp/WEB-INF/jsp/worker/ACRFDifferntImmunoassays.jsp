<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id= "${acrfFormVO.rawAcrfLabs[statusHT.index].labSrcId}" class="">
				
				
					<c:set var="B3SectionStyle" value="displayNone" />
					<c:set var="B1SectionStyle" value="displayNone" />
					<c:set var="B2SectionStyle" value="displayNone" />
					<c:set var="analyteSectionStyle" value="displayNone" />
					
					<c:choose>
						<c:when test="${acrfFormVO.rawAcrfLabs[statusHT.index].acrfTestCode  == 'B1'}">
							<c:set var="B1SectionStyle" value="" />
							<c:set var="analyteSectionStyle" value="" />
						</c:when>
						<c:when test="${acrfFormVO.rawAcrfLabs[statusHT.index].acrfTestCode  == 'B3'}">
							<c:set var="B3SectionStyle" value="" />
							<c:set var="analyteSectionStyle" value="" />	
						</c:when>
						<c:when test="${acrfFormVO.rawAcrfLabs[statusHT.index].acrfTestCode  == 'B2'}">
							<c:set var="B2SectionStyle" value="" />
							<c:set var="analyteSectionStyle" value="" />
						</c:when>
					
					</c:choose>
				
				
				
				
				
						
					<div class="hivTypeDiv acrfLabTestData" style="float:left; width:99%; padding-top:5px; margin-left:5px; margin-right:5px; margin-top:2px; border-bottom:1px solid black" >
						<div class="floatLeft width90perc ">
							<div class="floatLeft width100perc marginBottom10"> 
								<div style="float:left; width:7%; ">
									<span class="hdr1Font"> Test :</span>
								</div>				
								<div style="float:left; width:36%;" class="abtTDrpDwn" >
									<span class="mL20"> Test Type  
										<form:select path="rawAcrfLabs[${statusHT.index}].acrfTestCode" id="rawAcrfLabs${statusHT.index}_acrfTestCode" class="abtTTest acrf_TestCode" onchange="getResultForTestType(this, '02', 'hivTypeDiv');"  title="Test Type">
											<form:option value="">Select One</form:option>
											<form:options items="${acrfFormVO.labTestResultMap[testResultSection]}" itemLabel="acrtTestDesc" itemValue="acrtTestCode"/>
										</form:select>		
									</span>
								</div>
								<div style="float:left; width:27%;" class="mL20">
									<span class="mL10">Test Brand Name/Manufacturer </span>
									<span class="mL2"><form:select path="rawAcrfLabs[${statusHT.index}].manufacturer" id="rawAcrfLabs${statusHT.index}_manufacturer"  class="manufacturer standardInputSmallText3 " maxlength="40" title="Manufacturer">
											<form:option value="" >--select--</form:option>
											<c:forEach items="${manufacutrers}" var="manufacutrer">
												<form:option value="${manufacutrer.lookup_cd}" label="${manufacutrer.lookup_name}" name="${manufacutrer.lookup_name}"></form:option>
											</c:forEach>
										</form:select>
								  </span>
									<div class="v_error rawAcrfLabs${statusHT.index}_manufacturer"></div>	
								</div>
							</div>
							
							
							
							<c:set var="statusIndex" value="${statusHT.index}" scope="request"/>
							<jsp:include page="acrfLabFacility.jsp" ></jsp:include>
							
							
							<div class="floatLeft width100perc">
								<div style="float:left; width:7%; ">
									<span class="hdr1Font"> Result<c:choose><c:when test="${testResultSection  == '2_2'}"><span class="dependentElements top"><sup>3</sup></span></c:when><c:when test="${testResultSection  == '2_3'}"><span class="dependentElements top"><sup>4</sup></span></c:when></c:choose></span>
								</div>
								<div style="float:left; width:30%;" class="mL20">
									<span> Overall interpretation</span> 					
									<form:select path="rawAcrfLabs[${statusHT.index}].acrfResultCode" id="rawAcrfLabs${statusHT.index}_acrfResultCode" class="abtTResults acrf_ResultCode mandatoryField" cssErrorClass="error"  title="Test Result">
										<form:option value="">Select One</form:option>		
										<form:options items="${acrfFormVO.rawAcrfLabs[statusHT.index].labResultMap}" />
										
										
										<c:choose>
											<c:when test="${acrfFormVO.rawAcrfLabs[statusHT.index].acrfTestCode  == 'B1' && acrfRevison < 2018 }">
												
												<form:option value="25">HIV-1</form:option>
												<form:option value="26">HIV-2</form:option>
												<form:option value="27">Both (undifferentiated)</form:option>
												<form:option value="28">Neither (negative)</form:option>
												<form:option value="47">INDETERMINATE</form:option>
												
											</c:when>
											<c:when test="${acrfFormVO.rawAcrfLabs[statusHT.index].acrfTestCode  == 'B3' && acrfRevison < 2018 }">
												
												<form:option value="53">Not Reported</form:option>
												<form:option value="54">HIV-1 Reactive</form:option>
												<form:option value="55">HIV-2 Reactive</form:option>
												<form:option value="56">Both Reactive, Undifferentiated</form:option>
												<form:option value="57">Both Nonreactive</form:option>
											</c:when>
										
										</c:choose>
									
										
										
										
										
										
										
										
										
										
										
										
									</form:select>		
									<div class="v_error rawAcrfLabs${statusHT.index}_acrfResultCode"></div>
								</div>
								
								<c:if test="${acrfRevison > 2016}">
									<div style="width:25%;" class="floatLeft B3Section ${B3SectionStyle} dependentElements">
												<span class="mL5 "> Index Value: <form:input path="rawAcrfLabs[${statusHT.index}].indexValue"  id="rawAcrfLabs${statusHT.index}_indexValue" class="standardInputMedText" maxlength="100" title="Index Value" /> </span>
											</div>
										</c:if>	
										
								<div style="float:left;width:22%;">
									<span class="mL5"> Collection Date: <form:input path="rawAcrfLabs[${statusHT.index}].collectionDate"  id="rawAcrfLabs${statusHT.index}_collectionDate" datevalidations="completeDate" value="${acrfFormVO.rawAcrfLabs[statusHT.index].collectionDate}" class="abtTCollectionDate standardInputMedText dPicker mask acrf_collectionDate mandatoryField" maxlength="10" title="Collection Date" /> </span>
									<div class="v_error rawAcrfLabs${statusHT.index}_collectionDate"></div>		
								</div>
								<c:if test="${acrfRevison < 2023}">
								<div style="float:left; width:15%;">
										<span class="mL10"><form:checkbox path="rawAcrfLabs[${statusHT.index}].rapidTest" id="rawAcrfLabs${statusHT.index}_rapidTest" class="abtTRapid" /></span>
										<span class="mL2">RAPID TEST <I>(check if rapid)</I> </span>			
									</div>
								</c:if>
								
								 
							</div>
							
							
							
							<c:if test="${acrfRevison > 2016}">         
								<div class="floatLeft width100perc mT10">
									
									<div class="${analyteSectionStyle} analyteResultsSection dependentElements">
										<div style="float:left; width:10%; ">
											<span class="hdr1Font">Analyte results: </span>
										</div>
										
										
										<c:if test="${testResultSection  == '2_1' || testResultSection  == '2_2'}">
											
											<div class=" floatLeft B3Section B2Section " style="width:40%">
												<div style="width:80%;" class="floatLeft">
													<span>  HIV-1 Ag: </span>
													<span>
																<form:select path="rawAcrfLabs[${statusHT.index}].hiv1AgAnalyteResult" id="rawAcrfLabs${statusHT.index}_hiv1AgAnalyteResult" class="abtTAnalyteResults analyteResultsDropDown"    title="HIV-1 Ag">
																	<form:option value="">Select One</form:option>		
																	<form:option value="A4" cssClass="B3options ${B3SectionStyle}">Reactive</form:option>
																	<form:option value="A5" cssClass="B3options ${B3SectionStyle}">Nonreactive</form:option>
																	<form:option value="A6" cssClass="B3options ${B3SectionStyle}">Not reportable due to high Ab level</form:option>
																	<form:option value="A8" cssClass="B2options ${B2SectionStyle}">Reactive</form:option>
																	<form:option value="A9" cssClass="B2options ${B2SectionStyle}">Nonreactive</form:option>
																</form:select>
													</span>
													
												</div>
														
											</div>
											<div style="width:25%;" class="floatLeft B3Section ${B3SectionStyle}">
													<span class="mL5 B3Section"> Index Value: <form:input path="rawAcrfLabs[${statusHT.index}].hiv1AgAnalyteResultIndex"  id="rawAcrfLabs${statusHT.index}_hiv1AgAnalyteResultIndex" class="standardInputMedText abtTAnalyteResults" maxlength="100" title="Index Value" /> </span>
											</div>
										</c:if>
										
										<c:if test="${testResultSection  == '2_1'}">
											<div style="" class="clearBoth mT10 B2Section">
												<div class="floatLeft" style="width:10%;">
													<span class="hdr1Font"> &nbsp; </span>
												</div>
												<div style="width:40%;" class="floatLeft">
													<span>  HIV-1/2 Ab: </span>
													<span>
																<form:select path="rawAcrfLabs[${statusHT.index}].hiv1AbAnalyteResult" id="rawAcrfLabs${statusHT.index}_hiv12AbAnalyteResult" class="abtTAnalyteResults analyteResultsDropDown"   title="HIV-1 Ab">
																	<form:option value="">Select One</form:option>		
																	<form:option value="A8" cssClass="B2options ${B2SectionStyle}">Reactive</form:option>
																	<form:option value="A9" cssClass="B2options ${B2SectionStyle}">Nonreactive</form:option>
																</form:select>
													</span>
												</div>
												
											</div>
										</c:if>
										
										<c:if test="${testResultSection  == '2_3' || testResultSection  == '2_2'}">
											
											<div style="" class="clearBoth mT10 B1Section B3Section">
												<div class="floatLeft" style="width:10%; ${B3SectionStyle}">
													<span class="hdr1Font"> &nbsp; </span>
												</div>
												<div style="width:40%;" class="floatLeft">
													<span>  HIV-1 Ab: </span>
													<span>
																<form:select path="rawAcrfLabs[${statusHT.index}].hiv1AbAnalyteResult" id="rawAcrfLabs${statusHT.index}_hiv1AbAnalyteResult" class="abtTAnalyteResults analyteResultsDropDown"   title="HIV-1 Ab">
																	<form:option value="">Select One</form:option>		
																	<form:option value="A4" cssClass="B3options ${B3SectionStyle}">Reactive</form:option>
																	<form:option value="A5" cssClass="B3options ${B3SectionStyle}">Nonreactive</form:option>
																	<form:option value="A7" cssClass="B3options ${B3SectionStyle}">Reactive undifferentiated</form:option>
																	<form:option value="A1" cssClass="B1options ${B1SectionStyle}">Positive</form:option>
																	<form:option value="A2" cssClass="B1options ${B1SectionStyle}">Negative</form:option>
																	<form:option value="A3" cssClass="B1options ${B1SectionStyle}">Indeterminate</form:option>
																</form:select>
													</span>
												</div>
												<div style="width:25%;" class="floatLeft B3Section ${B3SectionStyle}">
													<span class="mL5 "> Index Value: <form:input path="rawAcrfLabs[${statusHT.index}].hiv1AbAnalyteResultIndex"  id="rawAcrfLabs${statusHT.index}_hiv1AbAnalyteResultIndex" class="standardInputMedText abtTAnalyteResults" maxlength="100" title="Index Value" /> </span>
												</div>	
											</div>
										
											
											
											
											<div  class="clearBoth B1Section B3Section mT10 ${analyteSectionStyle}">
												<div class="floatLeft" style="width:10%; ">
													<span class="hdr1Font"> &nbsp; </span>
												</div>
												<div style="width:40%;" class="floatLeft">
														<span>  HIV-2 Ab: </span>
														<span>
																	<form:select path="rawAcrfLabs[${statusHT.index}].hiv2AbAnalyteResult" id="rawAcrfLabs${statusHT.index}_hiv2AbAnalyteResult" class="abtTAnalyteResults analyteResultsDropDown"   title="HIV-2 Ab">
																		<form:option value="">Select One</form:option>		
																		<form:option value="A4" cssClass="B3options ${B3SectionStyle}">Reactive</form:option>
																		<form:option value="A5" cssClass="B3options ${B3SectionStyle}">Nonreactive</form:option>
																		<form:option value="A7" cssClass="B3options ${B3SectionStyle}">Reactive undifferentiated</form:option>
																		<form:option value="A1" cssClass="B1options ${B1SectionStyle}">Positive</form:option>
																		<form:option value="A2" cssClass="B1options ${B1SectionStyle}">Negative</form:option>
																		<form:option value="A3" cssClass="B1options ${B1SectionStyle}">Indeterminate</form:option>
																	</form:select>
														</span>
												</div>
												<div style="width:25%;" class="floatLeft B3Section ${B3SectionStyle}">
													<span class="mL5"> Index Value: <form:input path="rawAcrfLabs[${statusHT.index}].hiv2AbAnalyteResultIndex"  id="rawAcrfLabs${statusHT.index}_hiv2AbAnalyteResultIndex" class="standardInputMedText abtTAnalyteResults" maxlength="100" title="Index Value" /> </span>
												</div>
												 					
											</div>
										</c:if>
										
									</div>
									 
								</div>
							</c:if>
							
							<jsp:include page="acrfTestingOptions.jsp" ></jsp:include>
							
							
						</div>
						<div style="float:left width:10%;">
							<span class="mL2" style="float:right; margin-right:18px;"><img class="cloningIcons" onclick="deleteTestDiv(this, 'hivTypeDiv');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span>
						</div>
						<form:hidden path="rawAcrfLabs[${statusHT.index}].isDeleted" class="deleteParams"/>
						<form:hidden path="rawAcrfLabs[${statusHT.index}].parentTestType" class="deleteParams"/>
						<form:hidden path="rawAcrfLabs[${statusHT.index}].labSrcId" class="deleteParams rawAcrfLabSrcId"/>
			
					</div>
							
				
				</div>