<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class=" pB20" id="riaFollowupData" >
				
							
							<c:set var="previousProgramType" value="" />
							
							
							<c:forEach items="${stdRiaDocVO.followUpDocList}" var="followUpData" varStatus="followUpIndex">
							
									<c:set var="currentProgramType" value="${followUpData.PROGRAM_TYPE}" />
									<c:set var="showFollowUpOtherText" value=""/>
									<c:set var="fieldShowProperty" value="displayNone"/>
									<c:set var="readOnlyProperty" value=""/>
									
									<c:if test="${followUpData.FOLLOW_UP_DESC == 'Other'}">
										<c:set var="showFollowUpOtherText" value="Y"/>
									</c:if>
									
									<c:if test="${followUpData.RIA_FOLLOW_UP_ID != null}">
										<c:set var="fieldShowProperty" value=""/>
										<c:set var="readOnlyProperty" value="readOnly"/>
									</c:if>
									
									<c:if test="${currentProgramType != previousProgramType}">
										
										<c:if test="${followUpIndex.index >0 }">
											</div></div></div>
										</c:if>
										<div class="clearBoth" style="border: 1px dotted;">
										<div class="fRW30 mT20" >
											<div class="mL10">
												<c:choose>
												
													<c:when test="${followUpData.PROGRAM_TYPE == 'BHAE'}">
														<div class="bold">BHAE Notes</div>
														<div>
															<form:textarea path="BHAE_NOTES" id="BHAE_NOTES" ROWS="7" COLS="70" maxlength="4000" title="BHAE following up notes"/>
														</div>	
													
													</c:when>
													<c:when test="${followUpData.PROGRAM_TYPE == 'OMD'}">
														<div class="bold">OMD Notes</div>
														<div>
															<form:textarea path="OMD_NOTES" id="OMD_NOTES" ROWS="7" COLS="70" maxlength="4000" title="OMD following up notes"/>
														</div>
													
													</c:when>
													<c:when test="${followUpData.PROGRAM_TYPE == 'PS'}">
														<div class="bold">PS Notes</div>
														<div>
															<form:textarea path="PS_NOTES" id="PS_NOTES" ROWS="7" COLS="70" maxlength="4000" title="Patient following up notes"/>
														</div>
													
													</c:when>
												
												</c:choose>
										
										
										</div>
										
										</div>
										
										
										<div class="flw70" >
										<div class="mL10 mT10" style="border-right: 1px dotted;">
										
										<div>
											<span class="bold">
												${followUpData.PROGRAM_TYPE}
											</span>
										</div>
									</c:if>
							
									
									
									<div class="mLRTB pB20 clearBoth">
										<div class="fLW100 indivualFollowupData">
											<div>
												<div class="fLW20">
													<form:hidden path="followUpDocList[${followUpIndex.index}].RIA_FOLLOW_UP_ID" id="followUpDocList${followUpIndex.index}_RIA_FOLLOW_UP_ID" class="followUpId riaFollowUpFields"/>
													<form:checkbox checked="false" path="followUpDocList[${followUpIndex.index}].FOLLOW_UP_CD" id="followUpDocList${followUpIndex.index}_FOLLOW_UP_CD" class="followUpCode ${readOnlyProperty} intervention riaFollowUpFields" onClick="showDependentFields(this)" title="${followUpData.FOLLOW_UP_DESC}" value="${followUpData.FOLLOW_UP_CD}"/>${followUpData.FOLLOW_UP_DESC}
												</div>
												
												<c:set var="interventionWidth" value="fLW75" />
												<c:set var="followUpCd" value="${stdRiaDocVO.followUpDocList[followUpIndex.index].FOLLOW_UP_CD}"/>
												<c:if test = "${fn:contains(followUpCd, 'OTH')}">
													<c:set var="interventionWidth" value="fLW45" />
												</c:if>
												
												
												<c:if test="${showFollowUpOtherText =='Y' }">
													<div class="fLW30 ${fieldShowProperty} followUpOtherTextField">
														<span class=""> Other Follow up</span> 
														<span class="mL10"> <form:input path="followUpDocList[${followUpIndex.index}].FOLLOWUP_OTHER_TEXT" id="followUpDocList_${followUpIndex.index}.FOLLOWUP_OTHER_TEXT" title="Followup Other Text" class="intervention standardInputSmallText required ${readOnlyProperty} followupOtherText riaFollowUpFields"  />
														</span>
													</div>
												</c:if>
											</div>
											<c:choose>
												<c:when test="${stdRiaDocVO.followUpDocList[followUpIndex.index].interventionDocList != '[]' }">
													<div class="${interventionWidth} interventionList">
														<c:forEach items="${stdRiaDocVO.followUpDocList[followUpIndex.index].interventionDocList}" var="interventionData" varStatus="interventionIndex">
																<c:set var="interventionReadOnlyProperty" value=""/>
																<c:set var="interventionDateReadOnlyProperty" value=""/>
																<c:set var="interventionOtherTextProperty" value=""/>
																<c:if test="${stdRiaDocVO.followUpDocList[followUpIndex.index].interventionDocList[interventionIndex.index].INTERVENTION_CD != null}">
																	<c:set var="interventionReadOnlyProperty" value="readOnly"/>
																</c:if>
																<c:if test="${stdRiaDocVO.followUpDocList[followUpIndex.index].interventionDocList[interventionIndex.index].INTERVENTION_CD != null}">
																	<c:set var="interventionReadOnlyProperty" value="readOnly"/>
																</c:if>
																<c:if test="${stdRiaDocVO.followUpDocList[followUpIndex.index].interventionDocList[interventionIndex.index].INTERVENTION_OTHER_TEXT != null}">
																	<c:set var="interventionOtherTextProperty" value="readOnly"/>
																</c:if>
																<c:set var="otherFieldProperty" value="displayNone" />
																<c:if test="${stdRiaDocVO.followUpDocList[followUpIndex.index].interventionDocList[interventionIndex.index].INTERVENTION_CD == 'OTH'}">
																	<c:set var="otherFieldProperty" value="" />
																</c:if>
																<div class="clearBoth followUpInterventionFields ${fieldShowProperty}">
																	<div class=" floatLeft  marginBottom10  interventionCodeContent" style="height: 5px; ">
																			<span class=""> Intervention</span> 
																			<span class="mL10"> 
																			<c:set var="refColumnData" value="${riaFollowUpDropdownList[followUpData.FOLLOW_UP_CD]}"/>
																				<form:select path="followUpDocList[${followUpIndex.index}].interventionDocList[${interventionIndex.index}].INTERVENTION_CD"
																					id="followUpDocList_${followUpIndex.index}_interventionDocList${interventionIndex.index}_INTERVENTION_CD" class="standardInputMedText interventionCode intervention ${interventionReadOnlyProperty} riaFollowUpFields" 
																					title="Intervention" onChange="showInterventionOtherField(this)">
																					<form:option value=""></form:option>
																					<c:forEach items="${refColumnData}" var="intervention">
																						<form:option value="${intervention.INTERVENTION_CD}">${intervention.INTERVENTION_DESC}</form:option>
																					</c:forEach>
														
																				</form:select>
																		
																			</span>
																		</div>
																		
																			<div class="floatLeft marginBottom10 OtherIntervention  ${otherFieldProperty}">
																				<span class="mL20"> Other Intervention</span> 
																				<span class="mL10"> <form:input path="followUpDocList[${followUpIndex.index}].interventionDocList[${interventionIndex.index}].INTERVENTION_OTHER_TEXT" id="followUpDocList_${followUpIndex.index}_interventionDocList${interventionIndex.index}_INTERVENTION_OTHER_TEXT" title="Intervention Other Text" class="intervention standardInputSmallText  ${interventionOtherTextProperty} InterventionOtherText riaFollowUpFields"  /></span>
																			</div>
																		
																		<div class="floatLeft marginBottom10 ">
																			<span class="mL10"> Date</span> 
																			<span class="mL10"> 
																				<form:input path="followUpDocList[${followUpIndex.index}].interventionDocList[${interventionIndex.index}].INTERVENTION_DATE"
																					id="followUpDocList_${followUpIndex.index}_interventionDocList${interventionIndex.index}_INTERVENTION_DATE" class="standardInputSmallText1  intervention interventionDate ${interventionDateReadOnlyProperty} riaFollowUpFields mask dPicker" 
																					title="Intervention Date" />
																			</span>
																		</div>
																		<div class="v_error mR10 followUpDocList_${followUpIndex.index}_interventionDocList${interventionIndex.index}_INTERVENTION_DATE "></div>
																</div>
													
													
													
														</c:forEach>
														
													</div>
													<div class="floatLeft ${fieldShowProperty} addIntervention" >
															<span><img  class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addIntevention(this)" /> </span>
													</div>
												
												</c:when>
												<c:otherwise>
													<div class="${interventionWidth} interventionList">
														<div class="clearBoth followUpInterventionFields ${fieldShowProperty}">
															<div class="floatLeft  marginBottom10  interventionCodeContent">
																<span class=""> Intervention</span> 
																<span class="mL10"> 
																<c:set var="refColumnData" value="${riaFollowUpDropdownList[followUpData.FOLLOW_UP_CD]}"/>
																	<form:select path="followUpDocList[${followUpIndex.index}].interventionDocList[0].INTERVENTION_CD"
																		id="followUpDocList_${followUpIndex.index}_interventionDocList0_INTERVENTION_CD" class="standardInputMedText interventionCode intervention riaFollowUpFields" 
																		title="Intervention" onChange="showInterventionOtherField(this)">
																		<form:option value=""></form:option>
																		<c:forEach items="${refColumnData}" var="intervention">
																			<form:option value="${intervention.INTERVENTION_CD}">${intervention.INTERVENTION_DESC}</form:option>
																		</c:forEach>
											
																	</form:select>
															
																</span>
															</div>
															<div class="floatLeft marginBottom10 displayNone OtherIntervention" >
																<span class="mL20"> Other Intervention</span> 
																<span class="mL10"> <form:input path="followUpDocList[${followUpIndex.index}].interventionDocList[0].INTERVENTION_OTHER_TEXT" id="followUpDocList_${followUpIndex.index}_interventionDocList0_INTERVENTION_OTHER_TEXT" title="Intervention Other Text" class="intervention standardInputSmallText  ${readOnlyProperty} InterventionOtherText riaFollowUpFields"  /></span>
															</div>
															<div class="floatLeft marginBottom10  ">
																			<span class="mL10"> Date</span> 
																			<span class="mL10"> 
																				<form:input path="followUpDocList[${followUpIndex.index}].interventionDocList[0].INTERVENTION_DATE"
																					id="followUpDocList_${followUpIndex.index}_interventionDocList0_INTERVENTION_DATE" class="standardInputSmallText1 intervention riaFollowUpFields interventionDate mask dPicker" 
																					title="Intervention Date" />
																			</span>
																		</div>
																		<div class="v_error mR10 followUpDocList_${followUpIndex.index}_interventionDocList0_INTERVENTION_DATE "></div>
														</div>
													</div>
													<div class="floatLeft ${fieldShowProperty} addIntervention" >
															<span class="mL5"><img  class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addIntevention(this)" /> </span>
													</div>
												
												
												</c:otherwise>
											
											
											</c:choose>
											
											
											
											
											
											
											
											
											
										</div>
									</div>
									<c:set var="previousProgramType" value="${currentProgramType}" />
							</c:forEach>
							
								</div></div></div>
							
				</div>