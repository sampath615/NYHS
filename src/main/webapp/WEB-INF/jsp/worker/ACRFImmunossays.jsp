<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div style="width:100%; padding:2px; background-color:#DBEAF9" class="container">
				<div style="float:left;">
					<span class="hdr1Font mL5"> HIV Immunoassays (Non-differentiating)</span>
				</div>
				<div style="float:right; margin-right:20px;">
					<span><img  class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addNonTypeHIVDiv()" /> </span>
				</div>
		</div>
<div id="hivNonTypeTestsDivContainer" class="container crfLabTest">			
			<c:forEach items="${acrfFormVO.rawAcrfLabs}" var="hivABTstNonTypDiff" varStatus="statusHNT">
				<c:if test="${ acrfFormVO.rawAcrfLabs[statusHNT.index].parentTestType == '01' }">
				<div id= "${acrfFormVO.rawAcrfLabs[statusHNT.index].labSrcId}" class="">
				<div class="hivNonTypeTestsDiv" style="float:left; width:99%; padding:2px; margin-left:5px; margin-right:5px; margin-top:2px; border-bottom:1px solid black" >
					<div class="floatLeft width90perc">
						<div class="floatLeft width100perc marginBottom10"> 
							<div style="float:left; width:7%; ">
								<span class="hdr1Font"> Test : </span>
							</div>
							<div style="float:left; width:25%;" class="abtNTDrpDwn mL20">
								<span> Test Type 
								<form:select path="rawAcrfLabs[${statusHNT.index}].acrfTestCode" id="rawAcrfLabs${statusHNT.index}_acrfTestCode" class="abtNTTest acrf_TestCode" onchange="getResultForTestType(this, '01', 'hivNonTypeTestsDiv');" title="Test Type">
									<form:option value="">Select One</form:option>
									<form:options items="${acrfFormVO.hivABNonTypTstMap}" itemLabel="acrtTestDesc" itemValue="acrtTestCode"/>
<%-- 								<c:if test="${isOldAcrfValue == 'Y'}"> --%>
									<c:if test="${acrfRevison == 2013}">
										<form:option  value="A9">OTHER</form:option>
									</c:if>
									
									
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
						<c:if test="${acrfRevison > 2016}">
							<div class="floatLeft width100perc marginBottom10 dropdownSection">
								<div> 
									<div style="float:left; width:30%;" class="abtNTDrpDwn mL20">
										
										<span class="">Lab Name  </span>
										<span>
											<form:input path="rawAcrfLabs[${statusHNT.index}].labName" id="rawAcrfLabs${statusHNT.index}_labName" class="abtNTTest acrfLabs_Name standardInputLongText mL5 widgetFieldsReadonly"   title="Lab Name" tabindex="-1"></form:input>
											<form:hidden path="rawAcrfLabs[${statusHNT.index}].clia" id="rawAcrfLabs${statusHNT.index}_clia" class="acrfLabs_clia abtNTTest standardInputSmallText "   ></form:hidden>
										</span>		
										<div class="v_error rawAcrfLabs${statusHNT.index}_labName"></div>
									</div>
	
	
										
									<div style="float:left; width:30%;">
										<span class="mL10">Facility Name  </span>
										<span>
											<form:input path="rawAcrfLabs[${statusHNT.index}].facilityName" id="rawAcrfLabs${statusHNT.index}_facilityName" class=" abtNTTest acrfLabs_FacilityName standardInputLongText mL5 widgetFieldsReadonly"  title="Facility Name"  tabindex="-1"></form:input>
											<form:hidden path="rawAcrfLabs[${statusHNT.index}].facilityEharsUid" id="rawAcrfLabs${statusHNT.index}_facilityEharsUid" class="acrfLabs_FacilityEharsUid abtNTTest " ></form:hidden>
										</span>
										<div class="v_error rawAcrfLabs${statusHNT.index}_facilityName"></div>
									</div>
									
									
									<div style="float:left; width:8%; "> 
										<span class="mL5"> <button class="editLabData" onclick="openLabWidget(this);return false;" value="Edit">Edit</button> </span>
									</div>
								</div>
								<div class="clearBoth pT10">
								
									
									
									<span class="hdrFont mL20"> Provider </span>
								
									<span class="mL10">First Name  </span>
									<span>
										<form:input path="rawAcrfLabs[${statusHNT.index}].providerFname" id="rawAcrfLabs${statusHNT.index}_providerFname" class="abtNTTest acrfLabs_ProvFname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider First Name"  tabindex="-1"></form:input>
									</span>
<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerFname"></div> --%>
								
									<span class="mL10">Middle Name  </span>
									<span>
										<form:input path="rawAcrfLabs[${statusHNT.index}].providerMname" id="rawAcrfLabs${statusHNT.index}_providerMname" class="abtNTTest acrfLabs_ProvMname standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Middle Name"  tabindex="-1"></form:input>
									</span>
<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerMname"></div> --%>
								
									<span class="mL10">Last Name  </span>
									<span>
										<form:input path="rawAcrfLabs[${statusHNT.index}].providerLname" id="rawAcrfLabs${statusHNT.index}_providerLname" class="abtNTTest acrfLabs_ProvLname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider Last Name"  tabindex="-1"></form:input>
									</span>
<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerLname"></div> --%>
								
									<span class="mL10">Suffix </span>
									<span>
										<form:input path="rawAcrfLabs[${statusHNT.index}].providerSuffix" id="rawAcrfLabs${statusHNT.index}_providerSuffix" class="abtNTTest acrfLabs_ProvSuffix standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Suffix"  tabindex="-1"></form:input>
									</span>
<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerSuffix"></div> --%>
								
							
								</div>
									
							</div>
						</c:if>
						
						<div class="floatLeft width100perc">
								<div style="float:left; width:7%; ">
									<span class="hdr1Font"> Result : </span>
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
								<div style="float:left; width:25%;">
									<span class="mL10"><form:checkbox path="rawAcrfLabs[${statusHNT.index}].rapidTest" id="rawAcrfLabs${statusHNT.index}_rapidTest" class="abtNTRapid" value="${hivABTstNonTypDiff.rapidTest}"/></span>
									<span class="mL2">RAPID TEST <I>(check if rapid)</I> </span>			
								</div>
								
								
							
							</div>
						</div>		
					<div style="float:left width:2%;">
						<span class="mL2" style="float:right; margin-top:4px;"><img class="cloningIcons" onclick="deleteTestDiv(this, 'hivNonTypeTestsDiv');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span>
					</div>
				</div>
				
				<c:if test="${param.loadInDialog != 'Y'}">
					<script>  
						acrfRowCnt = acrfRowCnt+1;
					</script>
				</c:if>
				
					
				<form:hidden path="rawAcrfLabs[${statusHNT.index}].isDeleted" class="deleteParams"/>
				<form:hidden path="rawAcrfLabs[${statusHNT.index}].parentTestType" class="deleteParams"/>
				<form:hidden path="rawAcrfLabs[${statusHNT.index}].labSrcId" class="deleteParams" />
				</div>
			</c:if>			
			</c:forEach>				
		</div>	
		
		
		
		
		
		
		
		<div style="width:99.9%;" class="container"></div>		

		<div style="width:100%; padding:2px; background-color:#DBEAF9; " class="container">			
			<div style="float:left;">
				<span class="hdrFont mL5"> HIV Immunoassays (Differentiating)</span>
			</div>			
			<div style="float:right; margin-right:20px;">
				<span><img class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addTypeHIVDiv()" /> </span>			
			</div>			
		</div>
		
		<div id="hivTypeTestsDivContainer" class="container crfLabTest" style="padding:2px">
			<c:forEach items="${acrfFormVO.rawAcrfLabs}" var="hivABTstTypDiff" varStatus="statusHT">	
				<c:if test="${ acrfFormVO.rawAcrfLabs[statusHT.index].parentTestType == '02' }">
				<div id= "${acrfFormVO.rawAcrfLabs[statusHT.index].labSrcId}" class="">
				
				
					<c:set var="B3SectionStyle" value="displayNone" />
					<c:set var="B1SectionStyle" value="displayNone" />
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
					
					</c:choose>
				
				
				
				
				
						
					<div class="hivTypeDiv" style="float:left; width:99%; padding-top:5px; margin-left:5px; margin-right:5px; margin-top:2px; border-bottom:1px solid black" >
						<div class="floatLeft width90perc ">
							<div class="floatLeft width100perc marginBottom10"> 
								<div style="float:left; width:7%; ">
									<span class="hdr1Font"> Test :</span>
								</div>				
								<div style="float:left; width:36%;" class="abtTDrpDwn" >
									<span class="mL20"> Test Type  
										<form:select path="rawAcrfLabs[${statusHT.index}].acrfTestCode" id="rawAcrfLabs${statusHT.index}_acrfTestCode" class="abtTTest acrf_TestCode" onchange="getResultForTestType(this, '02', 'hivTypeDiv');"  title="Test Type">
											<form:option value="">Select One</form:option>
											<form:options items="${acrfFormVO.hivABTypTstMap}" itemLabel="acrtTestDesc" itemValue="acrtTestCode"/>
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
								<c:if test="${acrfRevison > 2016}">
									<div style="float:left; width:27%;" id="DIAGNOSTIC_ALGORITHM_B1" class="${B1SectionStyle} dignosticAlgorithmSection B1Section dependentElements">
										<span class="mL10">Role of test in diagnostic algorithm </span>
										<span class="mL2"><form:select path="rawAcrfLabs[${statusHT.index}].dignosticAlgorithm" id="rawAcrfLabs${statusHT.index}_dignosticAlgorithm"  class="diagnosticAlgorithmRole standardInputSmallText3 " maxlength="40" title="Diagnostic Algorithm">
												<form:option value="" >--select--</form:option>
<%-- 												<c:forEach items="${diagnosticAlgorithmRoles}" var="role"> --%>
<%-- 													<form:option value="${role.LOOKUP_CD}" label="${role.LOOKUP_NAME}" name="${role.LOOKUP_NAME}"></form:option> --%>
<%-- 												</c:forEach> --%>
												<form:option value="DA1">Screening/initial test</form:option>
												<form:option value="DA2">Confirmatory/supplemental test</form:option>

											</form:select>
									  </span>
										<div class="v_error rawAcrfLabs${statusHT.index}_dignosticAlgorithm"></div>	
									</div>
								</c:if>
								
							</div>
							
							
							
							<c:if test="${acrfRevison > 2016}">
							
								<div class="floatLeft width100perc marginBottom10 dropdownSection">
									<div> 
	
										<div style="float:left; width:30%;" class="abtTDrpDwn mL20">
											
											<span class="">Lab Name  </span>
											<span>
												<form:input path="rawAcrfLabs[${statusHT.index}].labName" id="rawAcrfLabs${statusHT.index}_labName" class="abtTTest acrfLabs_Name standardInputLongText mL5 widgetFieldsReadonly"  title="Lab Name" tabindex="-1"></form:input>
												<form:hidden path="rawAcrfLabs[${statusHT.index}].clia" id="rawAcrfLabs${statusHT.index}_clia" class="acrfLabs_clia abtTTest standardInputSmallText "   ></form:hidden>
											</span>		
											<div class="v_error rawAcrfLabs${statusHT.index}_labName"></div>
										</div>
											
										<div style="float:left; width:30%;">
											<span class="mL10">Facility Name  </span>
											<span>
												<form:input path="rawAcrfLabs[${statusHT.index}].facilityName" id="rawAcrfLabs${statusHT.index}_facilityName" class="abtTTest acrfLabs_FacilityName standardInputLongText mL5 widgetFieldsReadonly"  title="Facility Name"  tabindex="-1"></form:input>
												<form:hidden path="rawAcrfLabs[${statusHT.index}].facilityEharsUid" id="rawAcrfLabs${statusHT.index}_facilityEharsUid" class="acrfLabs_FacilityEharsUid abtTTest " ></form:hidden>
											</span>
											<div class="v_error rawAcrfLabs${statusHT.index}_facilityName"></div>
										</div>
										
										<div style="float:left; width:8%; "> 
											<span class="mL5"> <button class="editLabData" onclick="openLabWidget(this);return false;" value="Edit">Edit</button> </span>
										</div>
									</div>
									<div class="clearBoth pT10">
								
											
											
											<span class="hdrFont mL20"> Provider </span>
										
											<span class="mL10">First Name  </span>
											<span>
												<form:input path="rawAcrfLabs[${statusHT.index}].providerFname" id="rawAcrfLabs${statusHT.index}_providerFname" class="abtTTest acrfLabs_ProvFname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider First Name"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerFname"></div> --%>
										
											<span class="mL10">Middle Name  </span>
											<span>
												<form:input path="rawAcrfLabs[${statusHT.index}].providerMname" id="rawAcrfLabs${statusHT.index}_providerMname" class="abtTTest acrfLabs_ProvMname standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Middle Name"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerMname"></div> --%>
										
											<span class="mL10">Last Name  </span>
											<span>
												<form:input path="rawAcrfLabs[${statusHT.index}].providerLname" id="rawAcrfLabs${statusHT.index}_providerLname" class="abtTTest acrfLabs_ProvLname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider Last Name"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerLname"></div> --%>
										
											<span class="mL10">Suffix </span>
											<span>
												<form:input path="rawAcrfLabs[${statusHT.index}].providerSuffix" id="rawAcrfLabs${statusHT.index}_providerSuffix" class="abtTTest acrfLabs_ProvSuffix standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Suffix"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerSuffix"></div> --%>
										
									
									</div>
									
										
								</div>
							</c:if>
							
							
							
							
							
							<div class="floatLeft width100perc">
								<div style="float:left; width:7%; ">
									<span class="hdr1Font"> Result</span><span class="${B1SectionStyle} B1Section dependentElements top"><sup>1</sup></span><span class="${B3SectionStyle} B3Section dependentElements top"><sup>2</sup></span>
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
								
								<div style="float:left; width:15%;">
										<span class="mL10"><form:checkbox path="rawAcrfLabs[${statusHT.index}].rapidTest" id="rawAcrfLabs${statusHT.index}_rapidTest" class="abtTRapid" /></span>
										<span class="mL2">RAPID TEST <I>(check if rapid)</I> </span>			
									</div>
								 
							</div>
							
							<c:if test="${acrfRevison > 2016}">         
								<div class="floatLeft width100perc mT10">
									
									<div class="${analyteSectionStyle} analyteResultsSection dependentElements">
										<div style="float:left; width:10%; ">
											<span class="hdr1Font">Analyte results: </span>
										</div>
										
										<div class=" floatLeft B3Section   ${B3SectionStyle}" style="width:40%">
											<div style="width:80%;" class="floatLeft">
												<span>  HIV-1 Ag: </span>
												<span>
															<form:select path="rawAcrfLabs[${statusHT.index}].hiv1AgAnalyteResult" id="rawAcrfLabs${statusHT.index}_hiv1AgAnalyteResult" class="abtTAnalyteResults analyteResultsDropDown"    title="HIV-1 Ag">
																<form:option value="">Select One</form:option>		
																<form:option value="A4" cssClass="B3options ${B3SectionStyle}">Reactive</form:option>
																<form:option value="A5" cssClass="B3options ${B3SectionStyle}">Nonreactive</form:option>
																<form:option value="A6" cssClass="B3options ${B3SectionStyle}">Not Reportable due to high Ab level</form:option>
															</form:select>
												</span>
												
											</div>
													
										</div>
										<div style="width:25%;" class="floatLeft B3Section ${B3SectionStyle}">
												<span class="mL5 B3Section"> Index Value: <form:input path="rawAcrfLabs[${statusHT.index}].hiv1AgAnalyteResultIndex"  id="rawAcrfLabs${statusHT.index}_hiv1AgAnalyteResultIndex" class="standardInputMedText abtTAnalyteResults" maxlength="100" title="Index Value" /> </span>
										</div>
										
											
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
										
										
									</div>
									
									<div class="${B1SectionStyle}  B1Section dependentElements floatRight">
										<span><sup>1</sup>Always complete the overall interpretation. Complete the analyte results when available.</span>
									</div>
									<div class="${B3SectionStyle}  B3Section dependentElements floatRight">
										<span><sup>2</sup>Complete the overall interpretation and the analyte results.</span>
									</div>
									
									
									
									 
								</div>
							</c:if>
							
						</div>
						<div style="float:left width:10%;">
							<span class="mL2" style="float:right; margin-right:18px;"><img class="cloningIcons" onclick="deleteTestDiv(this, 'hivTypeDiv');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span>
						</div>
					</div>
							
				<c:if test="${param.loadInDialog != 'Y'}">
					<script>  
						acrfRowCnt = acrfRowCnt+1;
					</script>
				</c:if>
				<form:hidden path="rawAcrfLabs[${statusHT.index}].isDeleted" class="deleteParams"/>
				<form:hidden path="rawAcrfLabs[${statusHT.index}].parentTestType" class="deleteParams"/>
				<form:hidden path="rawAcrfLabs[${statusHT.index}].labSrcId" class="deleteParams"/>
				</div>
			</c:if>					
		</c:forEach>						
		</div>
		
		
		
		