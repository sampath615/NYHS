<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<form:form method="post" modelAttribute="ahiAlertTrackingVO" id="ahiAlertTrackingVO">

<div id="ahiAlertTrackingFormDiv" style="height: 530px;">
 
		<div class="personDetailsDiv" id="ahiAlertTrackingDetails">
			<div class="assignmentDetailsRow">
				<div class="fieldHolder20">
					<div class="floatLeft width30Perc"><font class="standardInputTitle" style="font-weight:bold">STATENO</font></div>        
					<div class="floatLeft width50Perc"><form:input path="stateno"  class="readonly" readonly="true" tabindex="-1" title="Stateno"/></div>	 
				</div>
				
				<div class="fieldHolder20">
					<div class="floatLeft width30Perc"><font class="standardInputTitle" style="font-weight:bold">Alert Date</font></div>        
					<div class="floatLeft width50Perc"><form:input path="ahiFlagSetDate"  class="readonly" readonly="true" tabindex="-1" title="Alert Date"/></div>	 
				</div>

				<div class="fieldHolder20">
					<div class="floatLeft width35Perc"><font class="standardInputTitle" style="font-weight:bold">Alert Source</font></div>  
					<div class="floatLeft width50Perc">
												<form:select path="alertSource" class="readonly" readonly="true" title="Alert Source"> 
													<form:option value=""> -- Select --</form:option>
													<c:forEach items="${alertSrcMap}" var="alertSrc">
														<form:option value="${alertSrc.AHI_ALERT_CODE}">${alertSrc.AHI_ALERT_DESC}</form:option> 											
													</c:forEach>
												</form:select>
					</div>	
				</div>	

				<div class="fieldHolder20">
					<div class="floatLeft width30Perc"><font class="standardInputTitle" style="font-weight:bold">Alert By</font></div>        
					<div class="floatLeft width50Perc"><form:input path="ahiFlagSetBy"  class="readonly" readonly="true" tabindex="-1" title="Alert By"/></div>	 
				</div>
			</div>

			<div class="assignmentDetailsRow">
							
				<div class="fieldHolder20">
					<div class="floatLeft" style="width: 42%;"><font class="standardInputTitle" style="font-weight:bold">PRF Collection</font></div>  
					<div class="floatLeft width30Perc">
												<form:select path="prfCollection" title="PRF Collection"> 
													<form:option value=""> -- Select --</form:option>
													<c:forEach items="${activePassiveMap}" var="activePassive">
														<form:option value="${activePassive.ANS_CODE}">${activePassive.ANS_DESC}</form:option> 											
													</c:forEach>
												</form:select>
					</div>	 
				</div>			
				
				<div class="fieldHolder20">
					<div class="floatLeft width50Perc"><font class="standardInputTitle" style="font-weight:bold">PRF Collection Date</font></div>        
					<div class="floatLeft width30Perc"><form:input path="prfCollectionDate"  class="readonly" readonly="true" tabindex="-1" title="PRF Collection Date"/></div>	 
				</div>

				<div class="fieldHolder20">
					<div class="floatLeft width30Perc"><font class="standardInputTitle" style="font-weight:bold">PRF AHI</font></div>        
					<div class="floatLeft width30Perc"><form:input path="prfFlaggedAhi"  class="readonly" readonly="true" tabindex="-1" title="PRF AHI"/></div>	 
				</div>
				
			</div>
			
 
       <div class="personDetailsDiv" id="patientInformation" style="height: 100px;width:50%; overflow-y: auto;">  
			 <div  class=" width90Perc pagebreak marginBottom10">
				<table class="table table-striped textAlignCenter pB20 width97Perc"> 
					<thead style="font-size:11px;">
						<tr>
							<th>PS Message Type</th>
							<th>Send Date</th>
							<th>Return Date</th>
							<th>Disposition (Recent)</th>
						</tr>
					</thead>
					<tbody class="textAlignCenter" style="font-size:12px;">
					
	      				<c:forEach var="psMessage" items="${ahiAlertTrackingVO.psMessageList}">
	      					<tr>
	      						<td>${psMessage.TRIGGER_MSG_TYPE}</td>
								<td>${psMessage.SEND_DATE}</td>
								<td>${psMessage.RETURN_DATE}</td>
								<td>${psMessage.LATEST_DISPOSITION_DESC}</td>
							</tr>
														      				
	      				</c:forEach>

					</tbody>							
						
				</table>	
			</div>
        </div>
 			
		<div class="assignmentDetailsRow">
			
				<div class="fieldHolder20">
					<div class="floatLeft width45Perc"><font class="standardInputTitle" style="font-weight:bold">Last Updated By</font></div>        
					<div class="floatLeft width50Perc"><form:input path="lastUpdatedBy"  class="readonly" readonly="true" tabindex="-1" title="Last Updated By"/></div>	 
				</div>
				
				<div class="fieldHolder20">
					<div class="floatLeft width45Perc"><font class="standardInputTitle" style="font-weight:bold">Last Updated Date</font></div>        
					<div class="floatLeft width50Perc"><form:input path="lastUpdatedDate"  class="readonly" readonly="true" tabindex="-1" title="Last Updated Date"/></div>	 
				</div>
				
				<div id ="nextFollowupStep" class="fieldHolder35">
					<div class="floatLeft width30Perc"><font class="standardInputTitle" style="font-weight:bold">Next Follow-up step</font></div>  
					<div class="floatLeft width50Perc">
												<form:select path="followupStepTakenCode"  cssClass="required" title="Next Follow-up step" onchange="changePRFCollection(this.value);"> 
													<form:option value=""> -- Select --</form:option>
													<c:forEach items="${ahiPrfFollowupMap}" var="ahiPrfFollowup">
														<form:option value="${ahiPrfFollowup.FOLLOW_UP_CODE}">${ahiPrfFollowup.FOLLOW_UP_DESC}</form:option> 											
													</c:forEach>
												</form:select>
						<div class="v_error followupStepTakenCode width250"></div>													
					</div>	 
				</div>			

				<div id ="alertCompletedDiv" class="fieldHolder20 displayNone">
					<div class="floatLeft width45Perc"><font class="standardInputTitle" style="font-weight:bold">Alert Completed Date</font></div>        
					<div class="floatLeft width50Perc"><form:input path="alertCompletedDate"  class="readonly" readonly="true" tabindex="-1" title="Alert Completed Date"/></div>	 
				</div>
				
		</div>

			<div class="assignmentDetailsRow" style="display: inline-flex">

				<div  id = "userNotesEntry" style="display: inline-block;width: 34.5%;">
					<div class="floatLeft" style="width:22.5%"><font class="standardInputTitle" style="font-weight:bold">User Notes</font></div>  
					<div class="floatLeft width75Perc">
								<form:textarea path="userNotes" cols="60" rows="6" maxlength="4000" title="User Notes"/>
								<div class="v_error userNotes width250"></div>
					</div>	 
				</div>	
				
<!-- 				<div style="width: 65%; float: right;"> -->
<!-- 					<div style="float:left"><span class="assignmentHeaders">Follow-up and User Notes History</span></div> -->
<!-- 					<div class="clearBoth" ></div> -->
<!-- 					<div id="assignmentCommentsHistory" class="assignmentComments" style="height:200px;width:90%"> -->
<%-- 						<%@ include file="AHIAlertUserNotesHistory.jsp"%> --%>
<!-- 					</div>					 -->
<!-- 				</div>			 -->
			</div>								

		</div>
		
		<form:hidden path="ahiAlertSeqId" />
		<form:hidden path="ahiDocId" />
		<form:hidden path="ahiAlertStatus" />
		<form:hidden path="assignmentId" />
		<form:hidden path="triggeringDocType" />
			
		<div class="mT10">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->				
		<div id="button-row" class="button-row center">
			<c:if test="${ahiAlertTrackingVO.ahiAlertStatus == 'OPEN' }">			
				<input type="button" id="approvButton"  class="labBtnclick button rounded blue effect-3" value="AHI Alert Approve" onclick="approveAHIAlert()"/>	 
				<input type="button" id="disapproveButton"  class="labBtnclick button rounded blue effect-3" value="AHI Alert Disapprove" onclick="disApproveAHIAlert()"/>	 
			</c:if>
						
			<c:if test="${ahiAlertTrackingVO.ahiAlertStatus == 'REVIEWED' && ahiAlertTrackingVO.ahiFlag == 'Y'}">									
				<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Save" onclick="submitAhiAlertTrackingForm()"/>	 
				<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Clear" onclick="clearAhiAlertTrackingForm()" />
			</c:if>			
		</div>
</div>

</form:form>