<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="isOldAcrfValue" value="${acrfFormVO.rawAcrfDoc.isOldAcrf}" />
<%-- This jsp belongs to "Lab" Third Tab --%> 
<!-- Page3 First block -->

		
			
		<%@ include file="../worker/ACRFDetectionTest.jsp" %>
				
		

		<div style="width:99.9%;" class="container"></div>  

		<div style="width:100%; padding:2px; background-color:#DBEAF9;" class="container">			
				<div style="float:left;">
					<span class="hdrFont mL5"> HIV Detection Tests (Quantitative viral load) Note: Include earliest test at or after diagnosis</span>
				</div>			
				<div style="float:right; margin-right:20px;">
					<span><img class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addQuanTypeHIVDiv()" style="cursor:pointer"/> </span>
				</div>
		</div>

		<div id="hivQuanTestsDivContainer" class="container crfLabTest" style="padding-bottom:5px">
			<c:forEach items="${acrfFormVO.rawAcrfLabs}" var="hivABTstNonTypDiff" varStatus="statusQN">
			<c:if test="${ acrfFormVO.rawAcrfLabs[statusQN.index].parentTestType == '04' }">
			<div id= "${acrfFormVO.rawAcrfLabs[statusQN.index].labSrcId}" class="">
			<div class="hivQuanTypeTestsDiv" style="float:left; width:99%; padding-top:5px; margin-left:5px; margin-right:5px; margin-top:2px;border-bottom:1px solid black" >
				<div class="floatLeft width90perc ">
					<div class="floatLeft width100perc marginBottom10">  
						<div style="float:left; width:7%;">
							<span class="hdr1Font"> Test : </span>
						</div>				
						<div style="float:left; width:30%;" class="qnDrpDwn">
							<span class="mL20"> Test Type </span> 
							<form:select path="rawAcrfLabs[${statusQN.index}].acrfTestCode" id="rawAcrfLabs${statusQN.index}_acrfTestCode" class="quntTst acrf_TestCode" onchange="getResultForTestType(this, '04', 'hivQuanTypeTestsDiv');"  title="Test Type">
								<form:option value="">Select One</form:option>
								<form:options items="${acrfFormVO.hivQuantitativeTstMap}" itemLabel="acrtTestDesc" itemValue="acrtTestCode"/>
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
					
					<c:if test="${acrfRevison > 2016}">
						<div class="floatLeft width100perc marginBottom10 dropdownSection"> 
							
							<div>
								<div style="float:left; width:30%;" class="qnDrpDwn mL20">
									
									<span class="">Lab Name  </span>
									<span>
										<form:input path="rawAcrfLabs[${statusQN.index}].labName" id="rawAcrfLabs${statusQN.index}_labName" class="quntTst acrfLabs_Name standardInputLongText mL5 widgetFieldsReadonly "  title="Lab Name" tabindex="-1"></form:input>
										<form:hidden path="rawAcrfLabs[${statusQN.index}].clia" id="rawAcrfLabs${statusQN.index}_clia" class="acrfLabs_clia quntTst standardInputSmallText "   ></form:hidden>
									</span>		
									<div class="v_error rawAcrfLabs${statusQN.index}_labName"></div>
								</div>
									
								<div style="float:left; width:30%;">
									<span class="mL10">Facility Name  </span>
									<span>
										<form:input path="rawAcrfLabs[${statusQN.index}].facilityName" id="rawAcrfLabs${statusQN.index}_facilityName" class="quntTst acrfLabs_FacilityName standardInputLongText mL5 widgetFieldsReadonly"  title="Facility Name"  tabindex="-1"></form:input>
										<form:hidden path="rawAcrfLabs[${statusQN.index}].facilityEharsUid" id="rawAcrfLabs${statusQN.index}_facilityEharsUid" class="acrfLabs_FacilityEharsUid quntTst " ></form:hidden>
									</span>
									<div class="v_error rawAcrfLabs${statusQN.index}_facilityName"></div>
								</div>
								
								<div style="float:left; width:8%; "> 
									<span class="mL5"> <button class="editLabData" onclick="openLabWidget(this);return false;" value="Edit">Edit</button> </span>
								</div>
							</div>
							<div class="clearBoth pT10">
								
									
									
									<span class="hdrFont mL20"> Provider </span>
								
									<span class="mL10">First Name  </span>
									<span>
										<form:input path="rawAcrfLabs[${statusQN.index}].providerFname" id="rawAcrfLabs${statusQN.index}_providerFname" class="quntTst acrfLabs_ProvFname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider First Name"  tabindex="-1"></form:input>
									</span>
<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerFname"></div> --%>
								
									<span class="mL10">Middle Name  </span>
									<span>
										<form:input path="rawAcrfLabs[${statusQN.index}].providerMname" id="rawAcrfLabs${statusQN.index}_providerMname" class="quntTst acrfLabs_ProvMname standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Middle Name"  tabindex="-1"></form:input>
									</span>
<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerMname"></div> --%>
								
									<span class="mL10">Last Name  </span>
									<span>
										<form:input path="rawAcrfLabs[${statusQN.index}].providerLname" id="rawAcrfLabs${statusQN.index}_providerLname" class="quntTst acrfLabs_ProvLname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider Last Name"  tabindex="-1"></form:input>
									</span>
<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerLname"></div> --%>
								
									<span class="mL10">Suffix </span>
									<span>
										<form:input path="rawAcrfLabs[${statusQN.index}].providerSuffix" id="rawAcrfLabs${statusQN.index}_providerSuffix" class="quntTst acrfLabs_ProvSuffix standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Suffix"  tabindex="-1"></form:input>
									</span>
<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerSuffix"></div> --%>
								
							
							</div>
						</div>
					</c:if>
							
					<div class="floatLeft width100perc">
						<div style="float:left; width:7%;">
							<span class="hdr1Font"> Result : </span>
						</div>				
						
						<div style="float:left; width:25%;">
							<span class="mL20"> <spring:message code="label.testResult" /></span> 
							<form:select path="rawAcrfLabs[${statusQN.index}].acrfResultCode" id="rawAcrfLabs${statusQN.index}_acrfResultCode" class="quanResult acrf_ResultCode mandatoryField" cssErrorClass="error"  title="Test Result">
								<form:option value="">Select One</form:option>		
								<form:options items="${acrfFormVO.rawAcrfLabs[statusQN.index].labResultMap}" />
							</form:select>	
							<div class="v_error rawAcrfLabs${statusQN.index}_acrfResultCode"></div>				
						</div>
						
						
						<div style="width:46%; float:left" id="rawAcrfLabs${statusQN.index}_copiesMlLogField">
							<div style="float:left; width:50%;">
								<span class="mL20"> Copies/mL:<form:input path="rawAcrfLabs[${statusQN.index}].copiesMl" id="rawAcrfLabs${statusQN.index}_copiesMl" value="${acrfFormVO.rawAcrfLabs[statusQN.index].copiesMl}" class="standardInputSmallText2 quanCP"  cssStyle="width: 75px;" maxlength="10"  title="Copies/mL"/></span>
								<div class="v_error rawAcrfLabs${statusQN.index}_copiesMl"></div>
							</div>
							<div style="float:left; width:50%;">
								<span class="mL20"> Log:<form:input path="rawAcrfLabs[${statusQN.index}].log" id="rawAcrfLabs${statusQN.index}_log" value="${acrfFormVO.rawAcrfLabs[statusQN.index].log}" class="standardInputSmallText2 quanLog"  cssStyle="width: 75px;" maxlength="10"  title="Log"/></span>
								<div class="v_error rawAcrfLabs${statusQN.index}_log"></div>
							</div>
							<div class="v_error rawAcrfLabs${statusQN.index}_copiesMlLogField"></div>
						</div>			
						<div style="float: left; width: 20%;">
							<span class="mL5"> Collection Date: <form:input path="rawAcrfLabs[${statusQN.index}].collectionDate" id="rawAcrfLabs${statusQN.index}_collectionDate" datevalidations="completeDate" value="${acrfFormVO.rawAcrfLabs[statusQN.index].collectionDate}" class=" standardInputSmallText dPicker mask quntCollectionDate acrf_collectionDate mandatoryField" cssStyle="width: 95px;" maxlength="10" title="Collection Date" /> </span>
							<div class="v_error rawAcrfLabs${statusQN.index}_collectionDate"></div>		
						</div>							
					</div>
				</div>
				<div style="float:left width:10%;">
					<span class="mL2" style="float:right; margin-right:18px; cursor:pointer"><img class="cloningIcons" onclick="deleteTestDiv(this, 'hivQuanTypeTestsDiv');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span>
				</div>	
			</div>	
			<c:if test="${param.loadInDialog != 'Y'}">
				<script>  
					 acrfRowCnt = acrfRowCnt+1;
				</script>
			</c:if>
			<form:hidden path="rawAcrfLabs[${statusQN.index}].isDeleted" class="deleteParams"/>
			<form:hidden path="rawAcrfLabs[${statusQN.index}].parentTestType" class="deleteParams"/>	
			<form:hidden path="rawAcrfLabs[${statusQN.index}].labSrcId"  class="deleteParams"/>
			</div>
			</c:if>				
			</c:forEach>		
		</div>
		
		<c:if test="${acrfRevison > 2016}">
		
			<div style="width:99.9%;" class="container"></div>		
	
			<div style="width:100%; padding:2px; background-color:#DBEAF9; " class="container">			
				<div style="float:left;">
					<span class="hdrFont mL5"> Drug Resistance Tests (Genotypic)</span>
				</div>			
				<div style="float:right; margin-right:20px;">
					<span><img class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addDrugResistanceDiv()" /> </span>			
				</div>			
			</div>
			
			<div id="hivResistanceTestsDivContainer" class="container crfLabTest" style="padding:2px">
				<c:forEach items="${acrfFormVO.rawAcrfLabs}" var="hivResTstTypDiff" varStatus="statusHT">	
					<c:if test="${ acrfFormVO.rawAcrfLabs[statusHT.index].parentTestType == '05' }">
					<div id= "${acrfFormVO.rawAcrfLabs[statusHT.index].labSrcId}" class="">			
						<div class="resistanceTypeDiv" style="float:left; width:99%; padding-top:5px; margin-left:5px; margin-right:5px; margin-top:2px; border-bottom:1px solid black" >
							<div class="floatLeft width90perc ">
								<div class="floatLeft width100perc marginBottom10"> 
									<div style="float:left; width:7%; ">
										<span class="hdr1Font"> Test :</span>
									</div>				
									<div style="float:left; width:33%;" class="restTestDrpDwn" >
										<span class="mL20"> Test Type  
											<form:select path="rawAcrfLabs[${statusHT.index}].acrfTestCode" id="rawAcrfLabs${statusHT.index}_acrfTestCode" class="resTest acrf_TestCode"  title="Test Type" onchange="getResultForTestType(this, '05', 'resistanceTypeDiv');">
												<form:option value="">Select One</form:option>
												<form:options items="${acrfFormVO.drugResistanceTstMap}" itemLabel="acrtTestDesc" itemValue="acrtTestCode"/>
											</form:select>		
										</span>
									</div>
									<div style="float:left; width:33%;">
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
									<div style="float: left; width: 20%;">
										<span class="mL5"> Collection Date: <form:input path="rawAcrfLabs[${statusHT.index}].collectionDate" id="rawAcrfLabs${statusHT.index}_collectionDate" datevalidations="completeDate" value="${acrfFormVO.rawAcrfLabs[statusHT.index].collectionDate}" class=" standardInputSmallText dPicker mask quntCollectionDate acrf_collectionDate mandatoryField" cssStyle="width: 95px;" maxlength="10" title="Collection Date" /> </span>
										<div class="v_error rawAcrfLabs${statusHT.index}_collectionDate"></div>		
									</div>
									
								</div>
								
								
								<c:if test="${acrfRevison > 2016}">
									<div class="floatLeft width100perc marginBottom10 dropdownSection"> 
										<div>
											<div style="float:left; width:30%;" class="restTestDrpDwn mL20">
												
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
							</div>
							<div style="float:left width:10%;">
								<span class="mL2" style="float:right; margin-right:18px;"><img class="cloningIcons" onclick="deleteTestDiv(this, 'resistanceTypeDiv');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span>
							</div>
						</div>
								
					<c:if test="${param.loadInDialog != 'Y'}">
						<script>  
							acrfRowCnt = acrfRowCnt+1;
						</script>
					</c:if>
					<form:hidden path="rawAcrfLabs[${statusHT.index}].isDeleted" class="deleteParams"/>
					<form:hidden path="rawAcrfLabs[${statusHT.index}].parentTestType" class="deleteParams"/>
					<form:hidden path="rawAcrfLabs[${statusHT.index}].labSrcId"  class="deleteParams"/>
					</div>
				</c:if>				
			</c:forEach>						
			</div>
		
		</c:if>
		
		<div style="width:99.9%;" class="container"></div>		

		<div style="width:100%; padding:2px; background-color:#DBEAF9;" class="container ">
				<span class="hdrFont mL5"> Immunologic Tests (CD4 count and percentage)</span>
				<div style="float:right; margin-right:20px;">
					<span><img class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addCD4()" /> </span>
				</div>				
		</div>
		
		<div id="cd4DivContainer" class="container crfLabTest" style="float:left; width:99%; padding:2px; padding-left:10px;"> 
			<div id= "${acrfFormVO.rawAcrfCd4Tst[0].cd4SrcId}" class="">
			<div class="cd4Div pT5 mL5 pB10" style="float:left; width:99%;  " >
				<div> 			
					<div style="float:left; width:25%;">
						<span class="CD4Desc bold"> CD4 at or closest to current diagnostic status:</span>
					</div>
					<div style="float:left; width:25%;">
					 <span class="">CD4 count:</span> <form:input  path="rawAcrfCd4Tst[0].cd4Count"  id="rawAcrfCd4Tst0_cd4Count" value="${acrfFormVO.rawAcrfCd4Tst[0].cd4Count}" class="nOnly standardInputSmallText1" maxlength="10" title="cells/&mu;L"/>cells/&mu;L
						<div class="v_error rawAcrfCd4Tst0_cd4Count mL10"></div>
					</div>
					<div style="float:left; width:25%;">
						<span class=" ">CD4 percentage <form:input  path="rawAcrfCd4Tst[0].cd4Percent" id="rawAcrfCd4Tst0_cd4Percent"  value="${acrfFormVO.rawAcrfCd4Tst[0].cd4Percent}" class="standardInputSmallText1 floatOnly" maxlength="10" title="CD4 percentage"/>%</span>
						<div class="v_error rawAcrfCd4Tst0_cd4Percent mL10"></div>
					</div>
					<div style="float:left;width:25%;">
						<span class=" "> Collection Date: <form:input  path="rawAcrfCd4Tst[0].collectionDate"  id="rawAcrfCd4Tst0_collectionDate" datevalidations="completeDate" value="${acrfFormVO.rawAcrfCd4Tst[0].collectionDate}" class="acrf_collectionDate mandatoryField standardInputMedText dPicker mask" maxlength="10" title="Collection Date" /></span>
						<div class="v_error rawAcrfCd4Tst0_collectionDate mL20"></div>
					</div>
					<form:hidden path="rawAcrfCd4Tst[0].closestToCurrentDx" class="closestToCurrentDx" value="1"/>
					<form:hidden path="rawAcrfCd4Tst[0].defaultCd4" class="defaultCd4Elements deleteParams" value="true"/>
					<form:hidden id="rawAcrfCd4Tst0_cd4SrcId" path="rawAcrfCd4Tst[0].cd4SrcId" class="deleteParams"/>
				</div>
				<c:if test="${acrfRevison > 2016}">
					<div class="clearBoth pT10">
						
							 <div style="float:left; width:25%; ">
								<span class="">Test Brand Name/Manufacturer </span>
								<span class="mL2"><form:select path="rawAcrfCd4Tst[0].manufacturer" id="rawAcrfCd4Tst[0]_manufacturer"  class="manufacturer standardInputSmallText3 " maxlength="40" title="Manufacturer">
										<form:option value="" >--select--</form:option>
										<c:forEach items="${manufacutrers}" var="manufacutrer">
											<form:option value="${manufacutrer.lookup_cd}" label="${manufacutrer.lookup_name}" name="${manufacutrer.lookup_name}"></form:option>
										</c:forEach>
									</form:select>
							    </span>
								<div class="v_error rawAcrfCd4Tst[0]_manufacturer"></div>	
							</div>
					</div>
					<div class="clearBoth pT10 width90perc ">
							<div class="floatLeft dropdownSection width100perc ">
								<div>
									<div style="float:left; width:30%;" class="qnDrpDwn ">
										
										<span class="">Lab Name  </span>
										<span>
											<form:input path="rawAcrfCd4Tst[0].labName" id="rawAcrfCd4Tst0_labName" class=" acrfLabs_Name standardInputLongText mL5 widgetFieldsReadonly"  title="Lab Name" tabindex="-1"></form:input>
											<form:hidden path="rawAcrfCd4Tst[0].clia" id="rawAcrfCd4Tst0_clia" class="acrfLabs_clia  standardInputSmallText "   ></form:hidden>
										</span>		
										<div class="v_error rawAcrfCd4Tst0_labName"></div>
									</div>
										
									<div style="float:left; width:30%;">
										<span class="">Facility Name  </span>
										<span>
											<form:input path="rawAcrfCd4Tst[0].facilityName" id="rawAcrfCd4Tst0_facilityName" class=" acrfLabs_FacilityName standardInputLongText mL5 widgetFieldsReadonly"  title="Facility Name"  tabindex="-1"></form:input>
											<form:hidden path="rawAcrfCd4Tst[0].facilityEharsUid" id="rawAcrfCd4Tst0_facilityEharsUid" class="acrfLabs_FacilityEharsUid  " ></form:hidden>
										</span>
										<div class="v_error rawAcrfCd4Tst0_facilityName"></div>
									</div>
									
								
									<div style="float:left; width:8%; "> 
											<span class="mL5"> <button class="editLabData" onclick="openLabWidget(this);return false;" value="Edit">Edit</button> </span>
										</div>
									
									</div>
									<div class="clearBoth pT10">
									
											<span class="hdrFont "> Provider </span>
										
											<span class="mL10">First Name  </span>
											<span>
												<form:input path="rawAcrfCd4Tst[0].providerFname" id="rawAcrfCd4Tst0_providerFname" class=" acrfLabs_ProvFname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider First Name"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerFname"></div> --%>
										
											<span class="mL10">Middle Name  </span>
											<span>
												<form:input path="rawAcrfCd4Tst[0].providerMname" id="rawAcrfCd4Tst0_providerMname" class=" acrfLabs_ProvMname standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Middle Name"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerMname"></div> --%>
										
											<span class="mL10">Last Name  </span>
											<span>
												<form:input path="rawAcrfCd4Tst[0].providerLname" id="rawAcrfCd4Tst0_providerLname" class=" acrfLabs_ProvLname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider Last Name"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerLname"></div> --%>
										
											<span class="mL10">Suffix </span>
											<span>
												<form:input path="rawAcrfCd4Tst[0].providerSuffix" id="rawAcrfCd4Tst0_providerSuffix" class=" acrfLabs_ProvSuffix standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Suffix"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerSuffix"></div> --%>
										
									
									</div>
								</div>
							
						
						
					</div>
			</c:if>
			</div>
			</div>
			<div class="container" style="width:100%; border:1px dotted; "></div>
			<div id= "${acrfFormVO.rawAcrfCd4Tst[1].cd4SrcId}" class="">
			<div class="cd4Div pT5 mL5 pB10" style="float:left; width:99%;  " >
				<div> 			
					<div style="float:left; width:25%;">
						<span class="CD4Desc bold"> First CD4 result <200 cells/&mu;L or <14%:</span>
					</div>
					<div style="float:left; width:25%;"> 			
						<span class="CD4Desc"> CD4 count:</span> <form:input  path="rawAcrfCd4Tst[1].cd4Count"  id="rawAcrfCd4Tst1_cd4Count" value="${acrfFormVO.rawAcrfCd4Tst[1].cd4Count}" class="nOnly standardInputSmallText1" maxlength="10" title="cells/&mu;L"/>cells/&mu;L
					</div>
					<div style="float:left; width:25%;">
						<span class=" ">CD4 percentage <form:input  path="rawAcrfCd4Tst[1].cd4Percent" id="rawAcrfCd4Tst1_cd4Percent"  value="${acrfFormVO.rawAcrfCd4Tst[1].cd4Percent}" class="standardInputSmallText1 floatOnly" maxlength="10" title="CD4 percentage"/>%</span>
					</div>
					<div style="float:left;; width:25%;">
						<span class=" "> Collection Date: <form:input  path="rawAcrfCd4Tst[1].collectionDate"  id="rawAcrfCd4Tst1_collectionDate" datevalidations="completeDate" value="${acrfFormVO.rawAcrfCd4Tst[1].collectionDate}" class="acrf_collectionDate mandatoryField standardInputMedText dPicker mask" maxlength="10" title="Collection Date" /></span>
						<div class="v_error rawAcrfCd4Tst1_collectionDate mL20"></div>
					</div>
					<form:hidden path="rawAcrfCd4Tst[1].closestToCurrentDx" class="closestToCurrentDx" value="2"/>
					<form:hidden path="rawAcrfCd4Tst[1].defaultCd4" class="defaultCd4Elements deleteParams" value="true"/>
					<form:hidden id="rawAcrfCd4Tst1_cd4SrcId" path="rawAcrfCd4Tst[1].cd4SrcId" class="deleteParams"/>
				</div>
				<c:if test="${acrfRevison > 2016}">
					<div class="clearBoth pT10">
						
							 <div style="float:left; width:25%; ">
								<span class="">Test Brand Name/Manufacturer </span>
								<span class="mL2"><form:select path="rawAcrfCd4Tst[1].manufacturer" id="rawAcrfCd4Tst[1]_manufacturer"  class="manufacturer standardInputSmallText3 " maxlength="40" title="Manufacturer">
										<form:option value="" >--select--</form:option>
										<c:forEach items="${manufacutrers}" var="manufacutrer">
											<form:option value="${manufacutrer.lookup_cd}" label="${manufacutrer.lookup_name}" name="${manufacutrer.lookup_name}"></form:option>
										</c:forEach>
									</form:select>
							    </span>
								<div class="v_error rawAcrfCd4Tst[1]_manufacturer"></div>	
							</div>
					</div>
					<div class="clearBoth pT10 width90perc ">
							<div class="floatLeft dropdownSection width100perc ">
								<div>
									<div style="float:left; width:30%;" class="qnDrpDwn ">
										
										<span class="">Lab Name  </span>
										<span>
											<form:input path="rawAcrfCd4Tst[1].labName" id="rawAcrfCd4Tst1_labName" class=" acrfLabs_Name standardInputLongText mL5 widgetFieldsReadonly"  title="Lab Name" tabindex="-1"></form:input>
											<form:hidden path="rawAcrfCd4Tst[1].clia" id="rawAcrfCd4Tst1_clia" class="acrfLabs_clia  standardInputSmallText "   ></form:hidden>
										</span>		
										<div class="v_error rawAcrfCd4Tst1_labName"></div>
									</div>
										
									<div style="float:left; width:30%;">
										<span class="">Facility Name  </span>
										<span>
											<form:input path="rawAcrfCd4Tst[1].facilityName" id="rawAcrfCd4Tst1_facilityName" class=" acrfLabs_FacilityName standardInputLongText mL5 widgetFieldsReadonly"  title="Facility Name"  tabindex="-1"></form:input>
											<form:hidden path="rawAcrfCd4Tst[1].facilityEharsUid" id="rawAcrfCd4Tst1_facilityEharsUid" class="acrfLabs_FacilityEharsUid  " ></form:hidden>
										</span>
										<div class="v_error rawAcrfCd4Tst1_facilityName"></div>
									</div>
									
									<div style="float:left; width:8%; "> 
											<span class="mL5"> <button class="editLabData" onclick="openLabWidget(this);return false;" value="Edit">Edit</button> </span>
										</div>
								</div>
								<div class="clearBoth pT10">
									
											<span class="hdrFont "> Provider </span>
										
											<span class="mL10">First Name  </span>
											<span>
												<form:input path="rawAcrfCd4Tst[1].providerFname" id="rawAcrfCd4Tst1_providerFname" class=" acrfLabs_ProvFname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider First Name"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerFname"></div> --%>
										
											<span class="mL10">Middle Name  </span>
											<span>
												<form:input path="rawAcrfCd4Tst[1].providerMname" id="rawAcrfCd4Tst1_providerMname" class=" acrfLabs_ProvMname standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Middle Name"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerMname"></div> --%>
										
											<span class="mL10">Last Name  </span>
											<span>
												<form:input path="rawAcrfCd4Tst[1].providerLname" id="rawAcrfCd4Tst1_providerLname" class=" acrfLabs_ProvLname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider Last Name"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerLname"></div> --%>
										
											<span class="mL10">Suffix </span>
											<span>
												<form:input path="rawAcrfCd4Tst[1].providerSuffix" id="rawAcrfCd4Tst1_providerSuffix" class=" acrfLabs_ProvSuffix standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Suffix"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerSuffix"></div> --%>
										
									
								</div>
							</div>
					
						
				</div>
			</c:if>
			</div>
			</div>
			<div class="container" style="width:100%; border:1px dotted; "></div>
			<div id= "${acrfFormVO.rawAcrfCd4Tst[2].cd4SrcId}" class="">
			<div id="otherCd4Div" class="cd4Div pT5 mL5 pB10" style="float:left; width:99%;  " > 			
				<div> 			
					<div style="float:left; width:25%;">
						<span class="CD4Desc bold"> Other CD4 result:</span>
					</div>
					<div style="float:left; width:25%;"> 
						<span class="CD4Desc"> CD4 count:</span>  <form:input  path="rawAcrfCd4Tst[2].cd4Count"  id="rawAcrfCd4Tst2_cd4Count" value="${acrfFormVO.rawAcrfCd4Tst[2].cd4Count}" class="nOnly standardInputSmallText1" maxlength="10" title="cells/&mu;L"/>cells/&mu;L
					</div>
					<div style="float:left; width:20%;">
						<span class=" ">CD4 percentage <form:input  path="rawAcrfCd4Tst[2].cd4Percent" id="rawAcrfCd4Tst2_cd4Percent"  value="${acrfFormVO.rawAcrfCd4Tst[2].cd4Percent}" class="standardInputSmallText1 floatOnly" maxlength="10" title="CD4 percentage"/>%</span>
					</div>
					<div style="float:left; width:25%;">
						<span class=" "> Collection Date: <form:input  path="rawAcrfCd4Tst[2].collectionDate"  id="rawAcrfCd4Tst2_collectionDate" datevalidations="completeDate" value="${acrfFormVO.rawAcrfCd4Tst[2].collectionDate}" class="acrf_collectionDate mandatoryField standardInputMedText dPicker mask" maxlength="10" title="Collection Date" /></span>
						<div class="v_error rawAcrfCd4Tst2_collectionDate mL20"></div>
					</div>
					<div style="float:left; width:4%" id="rawAcrfCd4Tst2_closeCd4" class="displayNone">
							<span class="mL2" style="float:right; margin-top:4px;"><img class="cloningIcons" onclick="deleteCd4TestDiv(this, 'cd4Div');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span>
					</div>
					<form:hidden path="rawAcrfCd4Tst[2].closestToCurrentDx" class="closestToCurrentDx" value="3"/>
					<form:hidden id="rawAcrfCd4Tst2_cd4SrcId" path="rawAcrfCd4Tst[2].cd4SrcId" class="deleteParams"/>
				</div>
				<c:if test="${acrfRevison > 2016}">
					<div class="clearBoth pT10">
						
							 <div style="float:left; width:25%; ">
								<span class="">Test Brand Name/Manufacturer </span>
								<span class="mL2"><form:select path="rawAcrfCd4Tst[2].manufacturer" id="rawAcrfCd4Tst[2]_manufacturer"  class="manufacturer standardInputSmallText3 " maxlength="40" title="Manufacturer">
										<form:option value="" >--select--</form:option>
										<c:forEach items="${manufacutrers}" var="manufacutrer">
											<form:option value="${manufacutrer.lookup_cd}" label="${manufacutrer.lookup_name}" name="${manufacutrer.lookup_name}"></form:option>
										</c:forEach>
									</form:select>
							    </span>
								<div class="v_error rawAcrfCd4Tst[2]_manufacturer"></div>	
							</div>
					</div>
					<div class="clearBoth pT10 width90perc ">
							<div class="floatLeft dropdownSection width100perc ">
								<div>
									<div style="float:left; width:30%;" class="qnDrpDwn ">
										
										<span class="">Lab Name  </span>
										<span>
											<form:input path="rawAcrfCd4Tst[2].labName" id="rawAcrfCd4Tst2_labName" class=" acrfLabs_Name standardInputLongText mL5 widgetFieldsReadonly"  title="Lab Name" tabindex="-1"></form:input>
											<form:hidden path="rawAcrfCd4Tst[2].clia" id="rawAcrfCd4Tst2_clia" class="acrfLabs_clia  standardInputSmallText "   ></form:hidden>
										</span>		
										<div class="v_error rawAcrfCd4Tst2_labName"></div>
									</div>
										
									<div style="float:left; width:30%;">
										<span class="">Facility Name  </span>
										<span>
											<form:input path="rawAcrfCd4Tst[2].facilityName" id="rawAcrfCd4Tst2_facilityName" class=" acrfLabs_FacilityName standardInputLongText mL5 widgetFieldsReadonly"  title="Facility Name"  tabindex="-1"></form:input>
											<form:hidden path="rawAcrfCd4Tst[2].facilityEharsUid" id="rawAcrfCd4Tst2_facilityEharsUid" class="acrfLabs_FacilityEharsUid  " ></form:hidden>
										</span>
										<div class="v_error rawAcrfCd4Tst2_facilityName"></div>
									</div>
									
									
									<div style="float:left; width:8%; "> 
											<span class="mL5"> <button class="editLabData" onclick="openLabWidget(this);return false;" value="Edit">Edit</button> </span>
										</div>
								</div>
								<div class="clearBoth pT10">
									
											<span class="hdrFont "> Provider </span>
										
											<span class="mL10">First Name  </span>
											<span>
												<form:input path="rawAcrfCd4Tst[2].providerFname" id="rawAcrfCd4Tst2_providerFname" class=" acrfLabs_ProvFname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider First Name"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerFname"></div> --%>
										
											<span class="mL10">Middle Name  </span>
											<span>
												<form:input path="rawAcrfCd4Tst[2].providerMname" id="rawAcrfCd4Tst2_providerMname" class=" acrfLabs_ProvMname standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Middle Name"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerMname"></div> --%>
										
											<span class="mL10">Last Name  </span>
											<span>
												<form:input path="rawAcrfCd4Tst[2].providerLname" id="rawAcrfCd4Tst2_providerLname" class=" acrfLabs_ProvLname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider Last Name"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerLname"></div> --%>
										
											<span class="mL10">Suffix </span>
											<span>
												<form:input path="rawAcrfCd4Tst[2].providerSuffix" id="rawAcrfCd4Tst2_providerSuffix" class=" acrfLabs_ProvSuffix standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Suffix"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerSuffix"></div> --%>
										
									
									</div>
								</div>
					<div class="container" style="width:100%; border:1px dotted; "></div>
				</div>
			</c:if>
			</div>
			</div>
			<div class="container" style="width:100%; border:1px dotted; "></div>
			<c:forEach items="${acrfFormVO.rawAcrfCd4Tst}" var="rawAcrfCd4Tst" varStatus="statusCD">
			<c:choose>
				<c:when test="${statusCD.index ==0 || statusCD.index ==1 || statusCD.index ==2}">
					
				</c:when>
				<c:otherwise>
			<div id= "${acrfFormVO.rawAcrfCd4Tst[statusCD.index].cd4SrcId}" class="">				
			<div id="otherCd4Div" class="cd4Div pT5 mL5 pB10" style="float:left; width:99%;  " > 			
				<div> 			
					<div style="float:left; width:25%;">
						<span class="CD4Desc bold"> Other CD4 result:</span>
					</div>
					<div style="float:left; width:25%;"> 
						<span class="CD4Desc"> CD4 count: <form:input  path="rawAcrfCd4Tst[${statusCD.index}].cd4Count"  id="rawAcrfCd4Tst${statusCD.index}_cd4Count" value="${acrfFormVO.rawAcrfCd4Tst[statusCD.index].cd4Count}" class="nOnly standardInputSmallText1" maxlength="10" title="cells/&mu;L"/>cells/&mu;L</span>
					</div>
					<div style="float:left; width:20%;">
						<span class=" ">CD4 percentage <form:input  path="rawAcrfCd4Tst[${statusCD.index}].cd4Percent" id="rawAcrfCd4Tst${statusCD.index}_cd4Percent"  value="${acrfFormVO.rawAcrfCd4Tst[statusCD.index].cd4Percent}" class="standardInputSmallText1 floatOnly" maxlength="10" title="CD4 percentage"/>%</span>
					</div>
					<div style="float:left; width:25%;">
						<span class=" "> Collection Date: <form:input  path="rawAcrfCd4Tst[${statusCD.index}].collectionDate"  id="rawAcrfCd4Tst${statusCD.index}_collectionDate" datevalidations="completeDate" value="${acrfFormVO.rawAcrfCd4Tst[statusCD.index].collectionDate}" class="acrf_collectionDate mandatoryField standardInputMedText dPicker mask" maxlength="10" title="Collection Date" /></span>
						<div class="v_error rawAcrfCd4Tst${statusCD.index}_collectionDate mL20"></div>
					</div>
					<div style="float:left; width:4%">
						<span class="mL2" style="float:right; margin-top:4px;"><img class="cloningIcons" onclick="deleteCd4TestDiv(this, 'cd4Div');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span>
					</div>
					<form:hidden path="rawAcrfCd4Tst[${statusCD.index}].closestToCurrentDx" class="closestToCurrentDx" value="3"/>
					<form:hidden id="rawAcrfCd4Tst${statusCD.index}_cd4SrcId" path="rawAcrfCd4Tst[${statusCD.index}].cd4SrcId" class="deleteParams"/>
				</div>
				
				<c:if test="${acrfRevison > 2016}">
					<div class="clearBoth pT10">
						
							 <div style="float:left; width:25%; ">
								<span class="">Test Brand Name/Manufacturer </span>
								<span class="mL2"><form:select path="rawAcrfCd4Tst[${statusCD.index}].manufacturer"  id="rawAcrfCd4Tst${statusCD.index}_manufacturer"  class="manufacturer standardInputSmallText3 " maxlength="40" title="Manufacturer">
										<form:option value="" >--select--</form:option>
										<c:forEach items="${manufacutrers}" var="manufacutrer">
											<form:option value="${manufacutrer.lookup_cd}" label="${manufacutrer.lookup_name}" name="${manufacutrer.lookup_name}"></form:option>
										</c:forEach>
									</form:select>
							    </span>
								<div class="v_error rawAcrfCd4Tst[2]_manufacturer"></div>	
							</div>
					</div>
					<div class="clearBoth pT10 width90perc ">
							<div class="floatLeft dropdownSection width100perc ">
								<div>
									<div style="float:left; width:30%;" class="qnDrpDwn ">
										
										<span class="">Lab Name  </span>
										<span>
											<form:input path="rawAcrfCd4Tst[${statusCD.index}].labName"  id="rawAcrfCd4Tst${statusCD.index}_labName" class=" acrfLabs_Name standardInputLongText mL5 widgetFieldsReadonly"  title="Lab Name" tabindex="-1"></form:input>
											<form:hidden path="rawAcrfCd4Tst[${statusCD.index}].clia" id="rawAcrfCd4Tst${statusCD.index}_clia" class="acrfLabs_clia  standardInputSmallText "   ></form:hidden>
										</span>		
										<div class="v_error rawAcrfCd4Tst${statusCD.index}_labName"></div>
									</div>
										
									<div style="float:left; width:30%;">
										<span class="">Facility Name  </span>
										<span>
											<form:input path="rawAcrfCd4Tst[${statusCD.index}].facilityName"  id="rawAcrfCd4Tst${statusCD.index}_facilityName" class=" acrfLabs_FacilityName standardInputLongText mL5 widgetFieldsReadonly"  title="Facility Name"  tabindex="-1"></form:input>
											<form:hidden path="rawAcrfCd4Tst[${statusCD.index}].facilityEharsUid" id="rawAcrfCd4Tst${statusCD.index}_facilityEharsUid" class="acrfLabs_FacilityEharsUid  " ></form:hidden>
										</span>
										<div class="v_error rawAcrfCd4Tst${statusCD.index}_facilityName"></div>
									</div>
									
									
									<div style="float:left; width:8%; "> 
											<span class="mL5"> <button class="editLabData" onclick="openLabWidget(this);return false;" value="Edit">Edit</button> </span>
										</div>
								</div>
								<div class="clearBoth pT10">
									
											<span class="hdrFont "> Provider </span>
										
											<span class="mL10">First Name  </span>
											<span>
												<form:input path="rawAcrfCd4Tst[${statusCD.index}].providerFname" id="rawAcrfCd4Tst${statusCD.index}_providerFname" class=" acrfLabs_ProvFname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider First Name"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerFname"></div> --%>
										
											<span class="mL10">Middle Name  </span>
											<span>
												<form:input path="rawAcrfCd4Tst[${statusCD.index}].providerMname" id="rawAcrfCd4Tst${statusCD.index}_providerMname" class=" acrfLabs_ProvMname standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Middle Name"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerMname"></div> --%>
										
											<span class="mL10">Last Name  </span>
											<span>
												<form:input path="rawAcrfCd4Tst[${statusCD.index}].providerLname" id="rawAcrfCd4Tst${statusCD.index}_providerLname" class=" acrfLabs_ProvLname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider Last Name"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerLname"></div> --%>
										
											<span class="mL10">Suffix </span>
											<span>
												<form:input path="rawAcrfCd4Tst[${statusCD.index}].providerSuffix" id="rawAcrfCd4Tst${statusCD.index}_providerSuffix" class=" acrfLabs_ProvSuffix standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Suffix"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerSuffix"></div> --%>
										
									
									</div>
							</div>
							
						
						
					</div>
				</c:if>
				<div class="container" style="width:100%; border:1px dotted; "></div>
			</div>
			</div>	
			<c:if test="${param.loadInDialog != 'Y'}">
				<script>  
					 cd4Cnt = cd4Cnt+1;
				</script>			
			</c:if>
				</c:otherwise>
			</c:choose>
			</c:forEach>		
		</div>		
		
		<div style="width:99.9%;" class="container"></div>		


		<div style="width:100%; background-color:#DBEAF9; padding:2px; " class="container">
				<span class="hdrFont mL5"> Documentation of Tests</span>
		</div>	
			
		<div style="float:left; width:99%; padding:2px; margin-left:5px; margin-right:5px;" class="container documentationTests"> 
			<div style="float:left; padding-bottom:5px; border-bottom:1px dotted; width:99%">
			 	<div>
					<span class="mL5"> Did documented laboratory test results meet approved HIV diagnostic algorithm criteria?</span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.dxAlgorithm" id="rawAcrfDoc_dxAlgorithm" value="Y" /> Yes </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.dxAlgorithm" id="rawAcrfDoc_dxAlgorithm" value="N" /> No </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.dxAlgorithm" id="rawAcrfDoc_dxAlgorithm" value="U" /> Unknown </span>
				</div>
				<div style="padding-top:5px;">
					<span class=" mL5"> If YES, provide specimen collection date of earliest positive test for this algorithm: <form:input path="rawAcrfDoc.dxAlgorithmDate" id="rawAcrfDoc_dxAlgorithmDate" datevalidations="partialDate" value="" class="standardInputMedText dPicker mask datepicker maskDate " maxlength="10" title="Date"  /></span>
					<div class="v_error rawAcrfDoc_dxAlgorithmDate mL5"></div>
				</div>
				<div style="padding-top:5px;">
					<span class="mL5" style="font-style: italic">Complete the above only if none of the following were positive for <span class="bold">HIV-1</span>: Western blot, IFA, culture, viral load, qualitative NAAT (RNA or DNA), HIV-1/2 type-differentiating immunoassay (supplemental test), stand-alone p24 antigen, or nucleotide sequence.</span>
				</div>
			</div>
			<div style="float:left; width:44%; padding-bottom:5px;"> 
				<div style="padding-top:5px; padding-bottom: 10px; border-bottom:1px dotted;">
					<span class="mL5"> Date of last documented negative HIV test:<form:input path="rawAcrfDoc.lastNegHivDate" id="rawAcrfDoc_lastNegHivDate" datevalidations="completeDate" value="${acrfFormVO.rawAcrfDoc.lastNegHivDate}" class="standardInputMedText dPicker mask datepicker maskDate " maxlength="10" title="Date" /></span>
					 <div class="v_error rawAcrfDoc_lastNegHivDate mL5"></div>
				</div>
				<div style="float:left; width:100%; padding-top:10px;">
					<span class="mL5"> Specify type of test:<form:input path="rawAcrfDoc.lastNegHivTest" id="rawAcrfDoc_lastNegHivTest" value="${acrfFormVO.rawAcrfDoc.lastNegHivTest}" class="standardInputText" maxlength="60" title="Specify type of test"/></span>
				</div>
			</div>
			<div style="float:left; width:55%; border-left:1px dotted;  padding-bottom:7px;"> 
				<div style="border-bottom:1px dotted; width=100%">
					<span class="mL5"> If HIV laboratory tests were not documented, is HIV diagnosis documented by a physician?</span><br/>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.hivDiagnosisByPhysician" id="rawAcrfDoc_hivDiagnosisByPhysician" value="Y" /> Yes </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.hivDiagnosisByPhysician" id="rawAcrfDoc_hivDiagnosisByPhysician" value="N" /> No </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.hivDiagnosisByPhysician" id="rawAcrfDoc_hivDiagnosisByPhysician" value="U" /> Unknown </span>
				</div>
				<div style="float:left; width:100%; padding-top:5px;">
					<span class=" mL5"> If YES, provide date of diagnosis:<form:input path="rawAcrfDoc.phyisicianDiagnosisDate" id="rawAcrfDoc_phyisicianDiagnosisDate" datevalidations="partialDate" value="${acrfFormVO.rawAcrfDoc.phyisicianDiagnosisDate}" class="standardInputMedText dPicker mask datepicker maskDate " maxlength="10" title="Date" /></span>
					<div class="v_error rawAcrfDoc_phyisicianDiagnosisDate mL5"></div>
				</div>
			</div>		
		</div>


		
<!-- Page3 second section  -->
<br/>

