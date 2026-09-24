<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="isOldAcrfValue" value="${acrfFormVO.rawAcrfDoc.isOldAcrf}" />
<%-- This jsp belongs to "Lab" Third Tab --%> 
<!-- Page3 First block -->

		
				
		

		<div style="width:99.9%;" class="container"></div>  

		<div style="width:100%; padding:2px; background-color:#DBEAF9;" class="container">			
				<div style="float:left;">
<!-- 					<span class="hdrFont mL5"> HIV Detection Tests (Quantitative viral load) Note: Include earliest test at or after diagnosis</span> -->
				</div>			
				<div style="float:right; margin-right:20px;">
					<span><img class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addLabTestSec('hivLabQualDetectionSec1')" style="cursor:pointer"/> </span>
				</div>
		</div>

		<div class="hivLabQualDetectionSec1" class="container crfLabTest" style="padding-bottom:5px">
			<c:forEach items="${acrfFormVO.rawAcrfLabs}" var="hivABTstNonTypDiff" varStatus="statusQN">
			<c:if test="${ acrfFormVO.rawAcrfLabs[statusQN.index].parentTestType == '04' && acrfFormVO.rawAcrfLabs[statusQN.index].testSection == '1'}">
				<c:set var="statusQN" value="${statusQN}" scope="request"/>
				<c:set var="testResultSection" value="4_1" scope="request"/>
				<jsp:include page="ACRFQuanDetectionTest.jsp" ></jsp:include>
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
					<span><img class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addLabTestSec('hivLabDrugResistanceSec1')" /> </span>			
				</div>			
			</div>
			
			<div class="hivLabDrugResistanceSec1" class="container crfLabTest" style="padding:2px">
				<c:forEach items="${acrfFormVO.rawAcrfLabs}" var="hivResTstTypDiff" varStatus="statusHT">	
					<c:if test="${ acrfFormVO.rawAcrfLabs[statusHT.index].parentTestType == '05' && acrfFormVO.rawAcrfLabs[statusHT.index].testSection == '1' }">
						<c:set var="statusHT" value="${statusHT}" scope="request"/>
						<c:set var="testResultSection" value="5_1" scope="request"/>
						<jsp:include page="ACRFDrugResistanceTest.jsp" ></jsp:include>
					</c:if>				
				</c:forEach>						
			</div>
		
		</c:if>
		
		<div style="width:99.9%;" class="container"></div>		
	
		<div style="width:100%; padding:2px; background-color:#DBEAF9;" class="container ">
				<span class="hdrFont mL5"> Immunologic Tests (CD4 count and percentage)</span>
					<div style="float:right; margin-right:20px;">
						<span><img class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addCD4('cd4DivContainer')" /> </span>
					</div>
		</div>
		
		<div class="cd4Section"  class="container crfLabTest" style="padding:2px">
		
		<div id="cd4DivContainer" class="container crfLabTest" style="float:left; width:99%; padding:2px; padding-left:10px;"> 
			<c:forEach items="${acrfFormVO.rawAcrfCd4Tst}" var="cd4TstTypDiff" varStatus="statusHT">	
			<div id= "${acrfFormVO.rawAcrfCd4Tst[statusHT.index].cd4SrcId}" class="">
			<div class="cd4Div pT5 mL5 pB10" style="float:left; width:99%;  " >
				<div> 			
					<div style="float:left; width:25%;">
						<span class="CD4Desc bold"> CD4 at or closest to current diagnostic status:</span>
					</div>
					<div style="float:left; width:25%;">
					 <span class="">CD4 count:</span> <form:input  path="rawAcrfCd4Tst[${statusHT.index}].cd4Count"  id="rawAcrfCd4Tst${statusHT.index}_cd4Count" value="${acrfFormVO.rawAcrfCd4Tst[statusHT.index].cd4Count}" class="nOnly standardInputSmallText1" maxlength="10" title="cells/&mu;L"/>cells/&mu;L
						<div class="v_error rawAcrfCd4Tst${statusHT.index}_cd4Count mL10"></div>
					</div>
					<div style="float:left; width:20%;">
						<span class=" ">CD4 percentage <form:input  path="rawAcrfCd4Tst[${statusHT.index}].cd4Percent" id="rawAcrfCd4Tst${statusHT.index}_cd4Percent"  value="${acrfFormVO.rawAcrfCd4Tst[statusHT.index].cd4Percent}" class="standardInputSmallText1 floatOnly" maxlength="10" title="CD4 percentage"/>%</span>
						<div class="v_error rawAcrfCd4Tst${statusHT.index}_cd4Percent mL10"></div>
					</div>
					<div style="float:left;width:25%;">
						<span class=" "> Collection Date: <form:input  path="rawAcrfCd4Tst[${statusHT.index}].collectionDate"  id="rawAcrfCd4Tst${statusHT.index}_collectionDate" datevalidations="completeDate" value="${acrfFormVO.rawAcrfCd4Tst[statusHT.index].collectionDate}" class="acrf_collectionDate mandatoryField standardInputMedText dPicker mask" maxlength="10" title="Collection Date" /></span>
						<div class="v_error rawAcrfCd4Tst${statusHT.index}_collectionDate mL20"></div>
					</div>
					<div style="float:left; width:4%">
						<span class="mL2" style="float:right; margin-top:4px;"><img class="cloningIcons" onclick="deleteCd4TestDiv(this, 'cd4Div');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span>
					</div>
					<form:hidden path="rawAcrfCd4Tst[${statusHT.index}].closestToCurrentDx" class="closestToCurrentDx" value="1"/>
					<form:hidden path="rawAcrfCd4Tst[${statusHT.index}].defaultCd4" class="defaultCd4Elements deleteParams" value="true"/>
					<form:hidden id="rawAcrfCd4Tst${statusHT.index}_cd4SrcId" path="rawAcrfCd4Tst[${statusHT.index}].cd4SrcId" class="deleteParams"/>
				</div>
					<div class="clearBoth pT10">
						
							 <div style="float:left; width:25%; ">
								<span class="">Test Brand Name/Manufacturer </span>
								<span class="mL2"><form:select path="rawAcrfCd4Tst[${statusHT.index}].manufacturer" id="rawAcrfCd4Tst[${statusHT.index}]_manufacturer"  class="manufacturer standardInputSmallText3 " maxlength="40" title="Manufacturer">
										<form:option value="" >--select--</form:option>
										<c:forEach items="${manufacutrers}" var="manufacutrer">
											<form:option value="${manufacutrer.lookup_cd}" label="${manufacutrer.lookup_name}" name="${manufacutrer.lookup_name}"></form:option>
										</c:forEach>
									</form:select>
							    </span>
								<div class="v_error rawAcrfCd4Tst[${statusHT.index}]_manufacturer"></div>	
							</div>
					</div>
					<div class="clearBoth pT10 width90perc ">
							<div class="floatLeft dropdownSection width100perc ">
								<div>
									<div style="float:left; width:30%;" class="qnDrpDwn ">
										
										<span class="">Lab Name  </span>
										<span>
											<form:input path="rawAcrfCd4Tst[${statusHT.index}].labName" id="rawAcrfCd4Tst${statusHT.index}_labName" class=" acrfLabs_Name standardInputLongText mL5 widgetFieldsReadonly"  title="Lab Name" tabindex="-1"></form:input>
											<form:hidden path="rawAcrfCd4Tst[${statusHT.index}].clia" id="rawAcrfCd4Tst${statusHT.index}_clia" class="acrfLabs_clia  standardInputSmallText "   ></form:hidden>
										</span>		
										<div class="v_error rawAcrfCd4Tst${statusHT.index}_labName"></div>
									</div>
										
									<div style="float:left; width:30%;">
										<span class="">Facility Name  </span>
										<span>
											<form:input path="rawAcrfCd4Tst[${statusHT.index}].facilityName" id="rawAcrfCd4Tst${statusHT.index}_facilityName" class=" acrfLabs_FacilityName standardInputLongText mL5 widgetFieldsReadonly"  title="Facility Name"  tabindex="-1"></form:input>
											<form:hidden path="rawAcrfCd4Tst[${statusHT.index}].facilityEharsUid" id="rawAcrfCd4Tst${statusHT.index}_facilityEharsUid" class="acrfLabs_FacilityEharsUid  " ></form:hidden>
										</span>
										<div class="v_error rawAcrfCd4Tst${statusHT.index}_facilityName"></div>
									</div>
									
								
									<div style="float:left; width:8%; "> 
											<span class="mL5"> <button class="editLabData" onclick="openLabWidget(this);return false;" value="Edit">Edit</button> </span>
										</div>
									
									</div>
									<div class="clearBoth pT10">
									
											<span class="hdrFont "> Provider </span>
										
											<span class="mL10">First Name  </span>
											<span>
												<form:input path="rawAcrfCd4Tst[${statusHT.index}].providerFname" id="rawAcrfCd4Tst${statusHT.index}_providerFname" class=" acrfLabs_ProvFname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider First Name"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerFname"></div> --%>
										
											<span class="mL10">Middle Name  </span>
											<span>
												<form:input path="rawAcrfCd4Tst[${statusHT.index}].providerMname" id="rawAcrfCd4Tst${statusHT.index}_providerMname" class=" acrfLabs_ProvMname standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Middle Name"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerMname"></div> --%>
										
											<span class="mL10">Last Name  </span>
											<span>
												<form:input path="rawAcrfCd4Tst[${statusHT.index}].providerLname" id="rawAcrfCd4Tst${statusHT.index}_providerLname" class=" acrfLabs_ProvLname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider Last Name"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerLname"></div> --%>
										
											<span class="mL10">Suffix </span>
											<span>
												<form:input path="rawAcrfCd4Tst[${statusHT.index}].providerSuffix" id="rawAcrfCd4Tst${statusHT.index}_providerSuffix" class=" acrfLabs_ProvSuffix standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Suffix"  tabindex="-1"></form:input>
											</span>
		<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerSuffix"></div> --%>
										
									
									</div>
								</div>
							
						
						
					</div>
			</div>
			</div>
			</c:forEach>
			
			<div class="container" style="width:100%; border:1px dotted; "></div>
					
		</div>	
		
		</div>	
