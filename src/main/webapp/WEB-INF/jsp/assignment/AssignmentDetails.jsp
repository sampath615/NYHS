<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<link rel="stylesheet" href="${staticPath}/css/reports/toggles-light.css" type="text/css"/>


<form:form method="post" modelAttribute="assignmentDetailsVO" id="assignmentDetailsVO">
<div class="floatRight">

		<div class="standardInputDivMedium mR10">
			<div class="labelInDialogWindowProf  ">
				<label class="standardInputTitle">AHI Flag</label>
			</div>
			<div class="inputFieldInDialogWindow">
				<div class="toggle-light">
				<div class=toggle data-checkbox=ahiFlagStatus tabindex="15" ></div>
				</div>
				<input type=checkbox disabled=disabled class="ahiFlagStatus displayNone">
			</div>
		</div>
		<form:hidden path="ahiDocId" id="ahiDocIdForAssignment"/>
		<form:hidden path="ahiReviewed" id="ahiAlertReviewed"/>
		
		<script type="text/javascript">
			
			createToggleWithAhiStatus('${assignmentDetailsVO.ahiFlag}','${assignmentDetailsVO.closedDate}', '${param.loadInDialog}');    
			
		</script>


		<c:if
			test="${userPermissions_workerRegion['52'] != null && (assignmentDetailsVO.currentAssignmentStatus == 'OPEN' || assignmentDetailsVO.currentAssignmentStatus == 'REOPEN')}">

			<div class="mL20 mT10 floatRight">
				<a onclick="loadAssignRawFacProvDialog()" href="#"
					class="changeAddressLinks">Change Address Details </a>
			</div>

		</c:if>
	</div>
<!-- Assignment details Div -->
<div id="assignmentDetailsDiv" style="height: 476px;">

	
		
		
		<div class="divHeadingCls">Surveillance Assignment for STATENO  <span id="assignmentDetailsStateno"><c:out value="${assignmentDetailsVO.stateno}" /></span></div>
		
		<div class="personDetailsDiv" id="currentAssignmentDetails">
			<div class="assignmentDetailsRow">
				<div class="assignmentDetailsRowItem">
					<div class="floatLeft width45Perc"><font class="standardInputTitle">Worker</font></div>        
					<div class="floatLeft width50Perc"><form:input path="workerName"  class="readonly" readonly="true" tabindex="-1" title="Worker"/></div>	 
				</div>
				
				<div class="assignmentDetailsRowItem">
					<div class="floatLeft width45Perc"><font class="standardInputTitle">Assigned By</font></div>        
					<div class="floatLeft width50Perc"><form:input path="supervisorName"   class="readonly" readonly="true" tabindex="-1" title="Supervisor"/></div>	 
				</div>
				
				<div class="assignmentDetailsRowItem">
					<div class="floatLeft width45Perc"><font class="standardInputTitle"><spring:message code="label.region" /></font></div>        
					<div class="floatLeft width50Perc"><form:input path="regionDescription"   class="readonly" readonly="true" tabindex="-1" title="Assignment Region"/></div>	 
				</div>
				
			</div>
			
			<div class="assignmentDetailsRow">
				<div class="assignmentDetailsRowItem">
					<div class="floatLeft width45Perc"><font class="standardInputTitle">Assignment Date</font></div>        
					<div class="floatLeft width50Perc"><form:input path="assignedDate"  class="readonly" readonly="true" tabindex="-1" title="Assignment On Date"/></div>	 
				</div>
				
				
				<div class="assignmentDetailsRowItem">
					<div class="floatLeft width45Perc"><font class="standardInputTitle">Status</font></div>        
					<div class="floatLeft width50Perc"><form:input path="assignmentStatusDesc" class="readonly" readonly="true" tabindex="-1" title="Assignment Status"/></div>	 
				</div>
				
				<div class="assignmentDetailsRowItem">
					<div class="floatLeft width45Perc"><font class="standardInputTitle">CRF Completed Date</font></div>        
					<div class="floatLeft width50Perc">
								<form:input path="crfDateCompleted"  class="readonly" readonly="true" tabindex="-1" title="CRF Completed Date"/>
					</div>
				</div>
				
				<div class="assignmentDetailsRowItem">
					<div class="floatLeft width45Perc"><font class="standardInputTitle">eHARS Import Date</font></div>        
					<div class="floatLeft width50Perc">
								<form:input path="eharsImportDate"  class="readonly" readonly="true" tabindex="-1" title="eHARS Import Date"/>
					</div>
				</div>
				
			</div>
			<div class="assignmentDetailsRow">
				
				<div class="assignmentDetailsRowItem">
					<div class="floatLeft width45Perc"><font class="standardInputTitle">Close Date</font></div>        
					<div class="floatLeft width50Perc"><form:input path="closedDate"  class="readonly" readonly="true" tabindex="-1" title="Closed Date"/></div>	 
				</div>
				<div style="display: inline-block; width: 50%;">
									<div class="floatLeft" style="width:22.5%"><font class="standardInputTitle">Closure Category</font></div>        
									<div class="floatLeft width75Perc">
										<c:choose>
											<c:when test="${assignmentDetailsVO.assignmentStatusDesc != 'Closed' && assignmentDetailsVO.assignmentStatusDesc != 'Admin closure' }">
												<form:select path="closureStatus"   cssClass="required " title="Closure Category"> 
													<form:option value=""> -- Select --</form:option>
													<c:forEach items="${closureCategoryMap}" var="closureCategoryDetails">
														<form:option value="${closureCategoryDetails.code}" cssClass="${closureCategoryDetails.needAcrf}" >${closureCategoryDetails.desc}</form:option> 											
													</c:forEach>
		
												</form:select>
											</c:when>
											<c:otherwise>
													<c:set var="closureCategoryDesc" value="" />
													<c:set var="closureCategoryActivityCode" value="" />
													<c:set var="crfRequired" value="N" />
													<c:forEach items="${closureCategoryMap}" var="closureCategoryDetails">
														<c:if test="${closureCategoryDetails.code == assignmentDetailsVO.closureCategory}" >
															<c:set var="closureCategoryActivityCode" value="${closureCategoryDetails.code}" />
															<c:set var="closureCategoryDesc" value="${closureCategoryDetails.desc}" />
														</c:if>
 														<c:if test="${closureCategoryActivityCode != '' && crfNeededActivities[closureCategoryActivityCode] != null}">
															<c:set var="crfRequired" value="Y" />
														</c:if> 	 
														
													</c:forEach>
													<form:input path="closureCategory" value="${closureCategoryDesc}"  class="readonly" readonly="true" tabindex="-1" title="${closureCategoryDesc}" style="width:75%"/>
													<form:hidden path = "closureCategoryActivityCode" value="${closureCategoryActivityCode}"/>	
													<form:hidden path = "crfRequired" value="${crfRequired}"/>											
											</c:otherwise>
										</c:choose>
									</div>	 

				</div>
				
				
			</div>
			<br>

		</div>
	
		<c:if test="${assignmentDetailsVO.reabstract == 'Y' }">
			<%@ include file="ReabAssignmentDetails.jsp"%>
		</c:if>	
		
		<form:hidden path="supervisorRegionId"/>
		<div class= "mT20" style="width: 47%; float: left">
		
			<div class="personDetailsDiv" style="height:247px;">
				<c:if  test="${userPermissions['52'] != null}" >
					<div class="assignmentComments">
	<%-- 					<label forName="assignDetailsComments"><spring:message code="label.comments" /></label> --%>
						<div class="floatLeft">
							<textarea rows="2" cols="71" id="assignDetailsComments" ></textarea>
						</div>
						<div class="button-row floatLeft">
							<a class="actionButtons a_demo_search a_demo_save" style="color: #FFFFFF;display:inline-block;margin-top:9px" href="javascript:saveComments('${assignmentDetailsVO.reabstract}')">Save</a>
						</div>
					</div>
				</c:if>
				<div class="clearBoth" ></div>
				<div style="float:left"><span class="assignmentHeaders">Comments History</span></div>
				<div class="clearBoth" ></div>
				<div id="assignmentCommentsHistory" class="assignmentComments">
					<%@ include file="AssignmentCommentsHistory.jsp"%>
				</div>
			
			</div>
		</div>
		
		
		<div class="personDetailsDiv" style="width: 47%; float: right; height:85%;">
			
			<div style="float:left"><span class="assignmentHeaders">Actions</span></div>
			<div style="clear: both;/* margin-left: 15px; */"><br/>
				<div id="assignmentActivityCommentsArea">
					<!-- Assignment Activities  -->
					<%@ include file="assignmentActivityComments.jsp"%>
				</div>
				<br/>
			</div>
		</div>
			
		<div class="personDetailsDiv" align="center">
			<div class="button-row" style="margin-bottom:10px;">
				<c:choose>
					<c:when test="${param.loadInDialog == 'Y' && assignmentDetailsVO.crfDocId != '0' && assignmentDetailsVO.crfDocType == 'ACRF' }">
							<a  class="enabledColor a_continue enabledReadonlyButton" style="color: #FFFFFF;display:inline-block;"  href="#" onclick="javascript:openACRFInDialog('${assignmentDetailsVO.crfDocId}','view', 'ACRF')" id="openAcrfForm">View ACRF</a>
					</c:when>
					<c:when test="${param.loadInDialog == 'Y' && assignmentDetailsVO.crfDocId != '0' && assignmentDetailsVO.crfDocType == 'PCRF' }">
							<a  class="enabledColor a_continue enabledReadonlyButton" style="color: #FFFFFF;display:inline-block;"  href="#" onclick="javascript:openACRFInDialog('${assignmentDetailsVO.crfDocId}','view', 'PCRF')" id="openAcrfForm">View PCRF</a>
					</c:when>
					<c:when test="${param.loadInDialog != 'Y' || param.loadInDialog == null}">

	
						<c:choose>
							<c:when test="${!(assignmentDetailsVO.currentAssignmentStatus == 'CLOSED' 
										|| assignmentDetailsVO.currentAssignmentStatus == 'CLS_ADM') && userPermissions_workerRegion['52'] != null}">
								
									<c:choose>
										<c:when test="${assignmentDetailsVO.crfDocId == '0'}">
										
											<a  class="enabledColor a_continue enabledEditButton" style="color: #FFFFFF;display:inline-block;"  href="#" onclick="javascript:openCRF('')" id="openAcrfForm">Open New CRF</a>
										</c:when>
										<c:when test="${assignmentDetailsVO.crfDocType == 'ACRF'}">
											<a href="#" onclick="javascript:openACRF('${assignmentDetailsVO.crfDocId}', '','ACRF')" class="enabledColor a_continue enabledEditButton" id="openAcrfForm">Open Saved ACRF</a>
										</c:when>
										<c:when test="${assignmentDetailsVO.crfDocType == 'PCRF'}">
											<a href="#" onclick="javascript:openACRF('${assignmentDetailsVO.crfDocId}', '', 'PCRF')" class="enabledColor a_continue enabledEditButton" id="openAcrfForm">Open Saved PCRF</a>
										</c:when>
									</c:choose>
							
									<a class="enabledColor a_continue fadedCancelButton" style="color: #FFFFFF;display:inline-block;" href="#" onclick="javascript:validateBeforeCloseAssignment()" id="closeAssignment" >Close Assignment</a>
									
							</c:when>
<%-- 							<c:when test="${assignmentDetailsVO.reabstract == 'Y' && assignmentDetailsVO.crfDocId != '0' && assignmentDetailsVO.crfDocType == 'ACRF'}">
								<a  class="enabledColor a_continue enabledReadonlyButton" style="color: #FFFFFF;display:inline-block;"  href="#" onclick="javascript:loadDocumentDlg('${assignmentDetailsVO.crfDocId}','ACRF', 'documentDetails')" id="openFSAcrfForm">View FS ACRF</a>
							</c:when>		 --%>					
							<c:when test="${assignmentDetailsVO.reabstract == 'N' && assignmentDetailsVO.crfDocId != '0' && assignmentDetailsVO.crfDocType == 'ACRF'}">
								<a  class="enabledColor a_continue enabledReadonlyButton" style="color: #FFFFFF;display:inline-block;"  href="#" onclick="javascript:openACRF('${assignmentDetailsVO.crfDocId}','view', 'ACRF')" id="openAcrfForm">View ACRF</a>
							</c:when>
<%-- 							<c:when test="${assignmentDetailsVO.reabstract == 'Y' && assignmentDetailsVO.crfDocId != '0' && assignmentDetailsVO.crfDocType == 'PCRF'}">
								<a  class="enabledColor a_continue enabledReadonlyButton" style="color: #FFFFFF;display:inline-block;"  href="#" onclick="javascript:loadDocumentDlg('${assignmentDetailsVO.crfDocId}','PCRF', 'documentDetails')" id="openFSAcrfForm">View FS PCRF</a>
							</c:when>		 --%>					
							<c:when test="${assignmentDetailsVO.reabstract == 'N' && assignmentDetailsVO.crfDocId != '0' && assignmentDetailsVO.crfDocType == 'PCRF'}">
								<a  class="enabledColor a_continue enabledReadonlyButton" style="color: #FFFFFF;display:inline-block;"  href="#" onclick="javascript:openACRF('${assignmentDetailsVO.crfDocId}','view', 'PCRF')" id="openAcrfForm">View PCRF</a>
							</c:when>
						</c:choose>
					</c:when>
			</c:choose>
								
			<c:if test="${assignmentDetailsVO.reabstract == 'Y' }">

				<c:choose>
					<c:when test="${param.loadInDialog == 'Y' && assignmentDetailsVO.reabCrfDocId != '0' && assignmentDetailsVO.crfDocType == 'ACRF' }">
							<a  class="enabledColor a_continue enabledReadonlyButton" style="color: #FFFFFF;display:inline-block;"  href="#" onclick="javascript:openACRFInDialog('${assignmentDetailsVO.reabCrfDocId}','view', 'ACRF')" id="openAcrfForm">View Reabstraction ACRF</a>
					</c:when>
					<c:when test="${param.loadInDialog == 'Y' && assignmentDetailsVO.reabCrfDocId != '0' && assignmentDetailsVO.crfDocType == 'PCRF' }">
							<a  class="enabledColor a_continue enabledReadonlyButton" style="color: #FFFFFF;display:inline-block;"  href="#" onclick="javascript:openACRFInDialog('${assignmentDetailsVO.reabCrfDocId}','view', 'PCRF')" id="openAcrfForm">View Reabstraction PCRF</a>
					</c:when>	
						
					<c:when test="${param.loadInDialog != 'Y' || param.loadInDialog == null}">

					<c:choose>
						<c:when test="${!(assignmentDetailsVO.reabCurrentStatus == 'REAB_R' || assignmentDetailsVO.reabCurrentStatus == 'REAB_C' || assignmentDetailsVO.reabCurrentStatus == 'REAB_CR') && userPermissions_workerRegion['52'] != null}">
							
								<c:choose>
									<c:when test="${assignmentDetailsVO.reabCrfDocId == '0' && assignmentDetailsVO.crfDocType == 'ACRF'}">
										<a  class="enabledColor a_continue enabledEditButton" style="color: #FFFFFF;display:inline-block;"  href="#" onclick="javascript:openCRFDocument('ACRF')" id="openAcrfForm">Open New Reabstraction ACRF</a>
									</c:when>
									<c:when test="${assignmentDetailsVO.reabCrfDocId == '0' && assignmentDetailsVO.crfDocType == 'PCRF'}">
										<a  class="enabledColor a_continue enabledEditButton" style="color: #FFFFFF;display:inline-block;"  href="#" onclick="javascript:openCRFDocument('PCRF')" id="openAcrfForm">Open New Reabstraction PCRF</a>
									</c:when>
									<c:when test="${assignmentDetailsVO.reabCrfDocId != '0' && assignmentDetailsVO.crfDocType == 'ACRF'}">
										<a href="#" onclick="javascript:openACRF('${assignmentDetailsVO.reabCrfDocId}', '','ACRF')" class="enabledColor a_continue enabledEditButton" id="openAcrfForm">Open Saved Reabstraction ACRF</a>
									</c:when>
									<c:when test="${assignmentDetailsVO.reabCrfDocId != '0' && assignmentDetailsVO.crfDocType == 'PCRF'}">
										<a href="#" onclick="javascript:openACRF('${assignmentDetailsVO.reabCrfDocId}', '', 'PCRF')" class="enabledColor a_continue enabledEditButton" id="openAcrfForm">Open Saved Reabstraction PCRF</a>
									</c:when>
								</c:choose>
						
						</c:when>
						<c:when test="${assignmentDetailsVO.reabCrfDocId != '0' && assignmentDetailsVO.crfDocType == 'ACRF'}">
							<a  class="enabledColor a_continue enabledReadonlyButton" style="color: #FFFFFF;display:inline-block;"  href="#" onclick="javascript:openACRF('${assignmentDetailsVO.reabCrfDocId}','view', 'ACRF')" id="openAcrfForm">View Reabstraction ACRF</a>
							<a class="enabledColor a_continue enabledReadonlyButton" id="assignmentViewCRFDiffBtn" href="#" onclick="javascript:displayCRFDiffInDialog('ACRF')" style="color: white;display:inline-block;">View ACRF Diff</a>
							<a class="enabledColor a_continue enabledReadonlyButton" id="assignmentViewCRFDiffSumBtn" href="#" onclick="javascript:crfDiffSummaryInDialog('ACRF')" style="color: white;display:inline-block;">View ACRF Summary</a>
						</c:when>
						<c:when test="${assignmentDetailsVO.reabCrfDocId != '0' && assignmentDetailsVO.crfDocType == 'PCRF'}">
							<a  class="enabledColor a_continue enabledReadonlyButton" style="color: #FFFFFF;display:inline-block;"  href="#" onclick="javascript:openACRF('${assignmentDetailsVO.reabCrfDocId}','view', 'PCRF')" id="openAcrfForm">View Reabstraction PCRF</a>
							<a class="enabledColor a_continue enabledReadonlyButton" id="assignmentViewCRFDiffBtn" href="#" onclick="javascript:displayCRFDiffInDialog('PCRF')" style="color: white;display:inline-block;">View PCRF Diff</a>							
							<a class="enabledColor a_continue enabledReadonlyButton" id="assignmentViewCRFDiffSumBtn" href="#" onclick="javascript:crfDiffSummaryInDialog('PCRF')" style="color: white;display:inline-block;">View PCRF Summary</a>							
						</c:when>
					</c:choose>
					</c:when>
				</c:choose>
				
			</c:if>	
						
						
				
			
			<c:if test="${param.documentType=='HIV_LAB'  || param.documentType=='STD_LAB' || param.documentType=='ADAP' || param.documentType=='PRF' || param.documentType=='INS'}">
				<a class="a_demo_search a_demo_print " id="assignmentListPrintBtn" onclick="printAssignmentDetails();" style="color: white;;display:inline-block;">Print Assign Det.</a>
			</c:if>		
			
			
<%-- 			<c:set var="printAHIClassName" value="displayNone" /> --%>
			
<%-- 			<c:if test="${assignmentDetailsVO.ahiFlag == 'Y'}"> --%>
<%-- 				<c:set var="printAHIClassName" value="" /> --%>
<%-- 			</c:if> --%>
			
<%-- 			<c:if test="${!(assignmentDetailsVO.currentAssignmentStatus == 'CLOSED'  --%>
<%-- 									|| assignmentDetailsVO.currentAssignmentStatus == 'CLS_ADM')}"> --%>
<%-- 				<a class='a_demo_search a_demo_print <c:out value="${printAHIClassName}" />' id="assignmentListPrintAhiBtn" onclick="printAHIDocument();" style="color: white;">Print AHI</a> --%>
<%-- 			</c:if> --%>
		
		</div>
		<div id="DummyText" class="displayNone">
		
		</div>
		<form:hidden path="assignmentId"/>
		<form:hidden path="crfDocType"/>
		<form:hidden path="currentAssignmentStatus"/>
		<form:hidden path="crfDocId"/>
		<form:hidden path="reabCrfDocId"/>		
		<form:hidden path="reabCrfDocType"/>		
		<form:hidden path="reabstract"/>
		<form:hidden path="reabCurrentStatus"/>	
		<form:hidden path="reabCurrentActivity"/>
		<form:hidden path="crfDataEntry"/>

	
	<div id="duplicateStatenoDetails" class="displayNone">	
		<div id="dcContent"> 
			<span>Please enter the STATENO flagged same:</span>	
			<input type="text" class="required" name="duplicateStateno" id ="duplicateStateno" value="" maxlength="10" title="Stateno" />
		</div>
	</div>
	
	<div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
	</div>
	
	<div id="assginRawFacProv" class="displayNone">
		<div id="assignmentAddressChangeTransactionMessage" class="transactionMessage"><span class="message"></span></div>
			<div style="text-align:center; font-weight:bold;margin:5px">
  <span class="searchFacilityTxt"> Search By :</span>
<span>
						<select id="searchOptions" class="searchOptions" onchange="changeSearchCrit(this.value)">
							<option value="">Select</option> 
							<option value="1">Provider</option>
							<option value="2">Facility</option>
						</select>
					</span>
</div>
					
					<div id="changeAddressContent">
					
					</div>
	</div>
	
	
	
	<div id="crfDocTypeDialog" class="displayNone">
			<div style="text-align:center; font-weight:bold;margin:5px">
				  <span class="searchFacilityTxt"> CRF Document Type :</span>
				  <span>
						<select id="searchOptions" class="searchOptions" onchange="openCRFDocument(this.value)">
							<option value="">Select</option> 
							<option value="ACRF">ACRF</option>
							<option value="PCRF">PCRF</option>
						</select>
					</span>
			</div>
	</div>
	
	
</div>
</div>
	</form:form>
	