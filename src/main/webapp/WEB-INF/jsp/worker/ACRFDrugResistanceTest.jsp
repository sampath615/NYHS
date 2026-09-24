<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id= "${acrfFormVO.rawAcrfLabs[statusHT.index].labSrcId}" class="">			
						<div class="resistanceTypeDiv acrfLabTestData" style="float:left; width:99%; padding-top:5px; margin-left:5px; margin-right:5px; margin-top:2px; border-bottom:1px solid black" >
							<div class="floatLeft width90perc ">
								<div class="floatLeft width100perc marginBottom10"> 
									<div style="float:left; width:7%; ">
										<span class="hdr1Font"> Test :</span>
									</div>				
									<div style="float:left; width:33%;" class="restTestDrpDwn" >
										<span class="mL20"> Test Type  
											<form:select path="rawAcrfLabs[${statusHT.index}].acrfTestCode" id="rawAcrfLabs${statusHT.index}_acrfTestCode" class="resTest acrf_TestCode"  title="Test Type" onchange="getResultForTestType(this, '05', 'resistanceTypeDiv');">
												<form:option value="">Select One</form:option>
												<form:options items="${acrfFormVO.labTestResultMap[testResultSection]}" itemLabel="acrtTestDesc" itemValue="acrtTestCode"/>
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
								
								
								<c:set var="statusIndex" value="${statusHT.index}" scope="request"/>
								<jsp:include page="acrfLabFacility.jsp" ></jsp:include>
								
								
								
							</div>
							<div style="float:left width:10%;">
								<span class="mL2" style="float:right; margin-right:18px;"><img class="cloningIcons" onclick="deleteTestDiv(this, 'resistanceTypeDiv');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span>
							</div>
							<form:hidden path="rawAcrfLabs[${statusHT.index}].isDeleted" class="deleteParams"/>
							<form:hidden path="rawAcrfLabs[${statusHT.index}].parentTestType" class="deleteParams"/>
							<form:hidden path="rawAcrfLabs[${statusHT.index}].labSrcId"  class="deleteParams"/>
						</div>
								
					
					
					</div>