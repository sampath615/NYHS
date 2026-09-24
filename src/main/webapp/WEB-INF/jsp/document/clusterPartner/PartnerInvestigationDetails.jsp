<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

		<div style="" class="container bold">
		
		
			
			<div class="clearBoth container " style="border:1px dotted">
			
				<div class="mLRTB pB20">
					<div class="fieldHolder35">
						<span class="fLW38"> Date initiated</span> 
						<span class="fLW60"> 
							<form:input path="partnerInvestigationList[${currentPartnerIndex.index}].DATEINITIATED" id="partnerInvestigationList${currentPartnerIndex.index}_DATEINITIATED"  class="standardInputSmallText2 mL5"  title="Date initiated"/>
						</span>
					</div>
					<div class="fieldHolder35">
						<span class="fLW38"> Interview date</span> 
						<span class="fLW60"> 
							<form:input path="partnerInvestigationList[${currentPartnerIndex.index}].INTERVIEWDATE" id="partnerInvestigationList${currentPartnerIndex.index}_INTERVIEWDATE" class="standardInputSmallText2 mL5"  title="Interview date"/>
						</span>
					</div>
					<div class="fieldHolder30">
						<span class="fLW38"> OP Date closed</span> 
						<span class="fLW60"> 
							<form:input path="partnerInvestigationList[${currentPartnerIndex.index}].OP_DATE_CLOSED" id="partnerInvestigationList${currentPartnerIndex.index}_OP_DATE_CLOSED" class="standardInputSmallText2 mL5 " title="OP Date closed" />
						</span>
					</div>
				</div>
				<div class="mLRTB clearBoth pB20">
					<div class="fieldHolder35">
						<span class="fLW38"> OP STATENO</span> 
						<span class="fLW60"> 
							<form:input path="partnerInvestigationList[${currentPartnerIndex.index}].OP_STATENO" id="partnerInvestigationList${currentPartnerIndex.index}_OP_STATENO" class="standardInputSmallText2 mL5" maxlength="10" title="OP STATENO"/>
						</span>
					</div>
					
				
					
					<div class="fieldHolder35">
						<span class="fLW38"> OP CDESSCASEKEY</span> 
						<span class="fLW60"> 
							<form:input path="partnerInvestigationList[${currentPartnerIndex.index}].OP_CDESSCASEKEY" id="partnerInvestigationList${currentPartnerIndex.index}_OP_CDESSCASEKEY" class="standardInputSmallText2 mL5 " title="OP CDESSCASEKEY"/>
						</span>
					</div>
					<div class="fieldHolder30">
						<span class="fLW38"> OP FR Number</span> 
						<span class="fLW60"> 
							<form:input path="partnerInvestigationList[${currentPartnerIndex.index}].OP_FRNUMBER" class="standardInputSmallText2 mL5 " title="OP FR Number"/>
						</span>
					</div>
				</div>
				<div class="mLRTB clearBoth">
					<div class="fieldHolder35">
						<span class="fLW38"> OP Field record key</span> 
						<span class="fLW60"> 
							<form:input path="partnerInvestigationList[${currentPartnerIndex.index}].OP_FIELDRECORDKEY" id="partnerInvestigationList${currentPartnerIndex.index}_OP_FIELDRECORDKEY" class="standardInputSmallText2 mL5 " title="OP Field record key" />
						</span>
					</div>
<!-- 					<div class="fieldHolder25"> -->
<!-- 						<span class="fLW38"> OP Inactive flag</span>  -->
<!-- 						<span class="fLW60">  -->
<%-- 							<form:select path="partnerInvestigationList[${currentPartnerIndex.index}].OP_INACTIVE_FLAG" id="partnerInvestigationList${currentPartnerIndex.index}_OP_INACTIVE_FLAG" class="standardInputSmallText2 mL5 " title="OP Inactive flag" > --%>
<%-- 								<form:option value=""></form:option> --%>
<%-- 								<form:option value="Y">Yes</form:option> --%>
<%-- 								<form:option value="N">No</form:option> --%>
<%-- 							</form:select> --%>
<!-- 						</span> -->
<!-- 					</div> -->
					
					
					
				</div>
				<div class="clearBoth  pT10"></div>
			</div>
			
		
			
			<div class="clearBoth container " style="border:1px dotted">
				<div class="mLRTB clearBoth pB20">
					<div class="fieldHolder35">
						<span class="fLW38"> Currently on PrEP</span> 
						<span class="fLW60"> 
							<form:select path="partnerInvestigationList[${currentPartnerIndex.index}].CURRENTONPREP" id="partnerInvestigationList${currentPartnerIndex.index}_CURRENTONPREP"  class="standardInputSmallText mL5"  title="Currently on PrEP">
									<form:option value=""></form:option>
										<c:forEach items="${documentRefData}" var="columnData">
											<c:if test="${columnData.REF_COLUMN == 'CURRENTONPREP' }">
												<form:option value="${columnData.LOOKUP_CD}"
													label="${columnData.LOOKUP_NAME}"
													name="${columnData.LOOKUP_NAME}"></form:option>
											</c:if>
										</c:forEach>
										
								</form:select>
						</span>
					</div>
					<div class="fieldHolder35">
						<span class="fLW38"> Patient on PrEP</span> 
						<span class="fLW60"> 
							<form:select path="partnerInvestigationList[${currentPartnerIndex.index}].CONFIRMPATIENTPREP" id="partnerInvestigationList${currentPartnerIndex.index}_CONFIRMPATIENTPREP"  class="standardInputSmallText mL5" title="Patient on PrEP">
									<form:option value=""></form:option>
										<form:option value="1">Yes</form:option>
										<c:forEach items="${documentRefData}" var="columnData">
											<c:if test="${columnData.REF_COLUMN == 'CONFIRMPATIENTPREP' }">
												<form:option value="${columnData.LOOKUP_CD}"
													label="${columnData.LOOKUP_NAME}"
													name="${columnData.LOOKUP_NAME}"></form:option>
											</c:if>
										</c:forEach>
										
								</form:select>
						</span>
					</div>
					<div class="fieldHolder30">
						<span class="fLW38"> Last HIV negative date</span> 
						<span class="fLW60"> 
							<form:input path="partnerInvestigationList[${currentPartnerIndex.index}].LAST_HIV_NEG_DATE" id="partnerInvestigationList${currentPartnerIndex.index}_LAST_HIV_NEG_DATE"  class="standardInputSmallText2 mL5"  title="Last HIV negative date"/>
						</span>
					</div>
				</div>
				<div class="mLRTB clearBoth pB20">
					<div class="fieldHolder35">
						<span class="fLW38"> Notification</span> 
						<span class="fLW60"> 
							<form:input path="partnerInvestigationList[${currentPartnerIndex.index}].NOTIFICATIONDATEPIND" id="partnerInvestigationList${currentPartnerIndex.index}_NOTIFICATIONDATEPIND"  class="standardInputSmallText mL5"  title="Notification Inicator"/>
						</span>
					</div>
					
					<div class="fieldHolder35">
						<span class="fLW38"> Notification date</span> 
						<span class="fLW60"> 
							<form:input path="partnerInvestigationList[${currentPartnerIndex.index}].NOTIFICATIONDATE" id="partnerInvestigationList${currentPartnerIndex.index}_NOTIFICATIONDATE"  class="standardInputSmallText2 mL5 " title="Notification date"/>
						</span>
					</div>
					
					<div class="fieldHolder30">
						<span class="fLW38"> DISNUMBER</span> 
						<span class="fLW60"> 
							<form:input path="partnerInvestigationList[${currentPartnerIndex.index}].DISNUMBER" id="partnerInvestigationList${currentPartnerIndex.index}_DISNUMBER"  class="standardInputSmallText mL5"  title="DISNUMBER"/>
						</span>
					</div>
					
				</div>
				<div class="mLRTB clearBoth pB20">
				
					<div class="fieldHolder35">
						<span class="fLW38"> Partner key</span> 
						<span class="fLW60"> 
							<form:input path="partnerInvestigationList[${currentPartnerIndex.index}].PARTNERKEY" id="partnerInvestigationList${currentPartnerIndex.index}_PARTNERKEY" class="standardInputSmallText mL5" title="Partner key"/>
						</span>
					</div>
					<c:if test="${clusterPartnerDocVO.partnerInvestigationList[currentPartnerIndex.index].PARTNER_FRNUMBER != '' && clusterPartnerDocVO.partnerInvestigationList[currentPartnerIndex.index].PARTNER_FRNUMBER != null }">
					
					
					
					<div class="fieldHolder35">
						<span class="fLW38"> Partner FR number</span> 
						<span class="fLW60"> 
							<form:input path="partnerInvestigationList[${currentPartnerIndex.index}].PARTNER_FRNUMBER" id="partnerInvestigationList${currentPartnerIndex.index}_PARTNER_FRNUMBER" class="standardInputSmallText mL5" title="Partner FR number"/>
						</span>
					</div>
					</c:if>
					
					
					<c:if test="${clusterPartnerDocVO.partnerInvestigationList[currentPartnerIndex.index].LEGACY_PARTNER_ID != '' && clusterPartnerDocVO.partnerInvestigationList[currentPartnerIndex.index].LEGACY_PARTNER_ID != null }">
					
					
					
					<div class="fieldHolder35">
						<span class="fLW38"> Legacy partner Id</span> 
						<span class="fLW60"> 
							<form:input path="partnerInvestigationList[${currentPartnerIndex.index}].LEGACY_PARTNER_ID" id="partnerInvestigationList${currentPartnerIndex.index}_LEGACY_PARTNER_ID"  class="standardInputSmallText mL5"  title="Legacy partner Id"/>
						</span>
					</div>
					</c:if>
					
					<div class="fieldHolder30">
						<span class="fLW38"> Partner flag</span> 
						<span class="fLW60"> 
							<form:select path="partnerInvestigationList[${currentPartnerIndex.index}].PARTNER_FLAG" id="partnerInvestigationList${currentPartnerIndex.index}_PARTNER_FLAG" class="standardInputSmallText mL5" title="Partner flag">
								<form:option value=""></form:option>
										<c:forEach items="${documentRefData}" var="columnData">
											<c:if test="${columnData.REF_COLUMN == 'PARTNER_FLAG' }">
												<form:option value="${columnData.LOOKUP_CD}"
													label="${columnData.LOOKUP_NAME}"
													name="${columnData.LOOKUP_NAME}"></form:option>
											</c:if>
										</c:forEach>
							
							</form:select>
						</span>
					</div>
		
			
				
				
				</div>
				<div class="mLRTB clearBoth pB20">
					
					<div class="fieldHolder35">
						<span class="fLW38"> Partner type</span> 
						<span class="fLW60"> 
							<form:select path="partnerInvestigationList[${currentPartnerIndex.index}].PARTNER_TYPE" id="partnerInvestigationList${currentPartnerIndex.index}_PARTNER_TYPE"  class="standardInputSmallText mL5" maxlength="25" title="Partner type">
									<form:option value=""></form:option>
									<c:forEach items="${documentRefData}" var="columnData">
										<c:if test="${columnData.REF_COLUMN == 'PARTNER_TYPE' }">
											<form:option value="${columnData.LOOKUP_CD}"
												label="${columnData.LOOKUP_NAME}"
												name="${columnData.LOOKUP_NAME}"></form:option>
										</c:if>
									</c:forEach>
							
	
							</form:select>
						</span>
					</div>
					
					<div class="fieldHolder35">
						<span class="fLW38"> Partner assistance status</span> 
						<span class="fLW60"> 
								<form:select path="partnerInvestigationList[${currentPartnerIndex.index}].PARTNERASSISTANCESTATUS" id="partnerInvestigationList${currentPartnerIndex.index}_PARTNERASSISTANCESTATUS"  class="standardInputSmallText mL5" title="Partner assistance status">
										<form:option value=""></form:option>
										<c:forEach items="${documentRefData}" var="columnData">
											<c:if test="${columnData.REF_COLUMN == 'PARTNERASSISTANCESTATUS' }">
												<form:option value="${columnData.LOOKUP_CD}"
													label="${columnData.LOOKUP_NAME}"
													name="${columnData.LOOKUP_NAME}"></form:option>
											</c:if>
										</c:forEach>
								</form:select>
						</span>
					</div>
					
				</div>
				
			</div>
									 
		</div>
		