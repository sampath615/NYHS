<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

            <div id="submitCLEPResultTransactionMessage" class="transactionMessage"><span class="message"></span></div>
            
			<form:form modelAttribute="clepLabResultVO" id ="submitLabRecruitmentAssessmentFollowupData" action="submitLabRecruitmentAssessmentFollowup" method="post">
			
			<div>
				
					<div style="float:left; width:99%; margin: 10px 10px 10px 10px;">
						<form:hidden path="surveyNumber"/>
						<div style="float:left; width:10%">
							<label class="standardInputTitle">PFI</label>
							<form:input id="userEnteredPfi" path="userEnteredPfi"  class="mL5 formSmallInputText readonly" title="PFI"/>
						</div>
						<div style="float:left; width:10%">
							<label class="standardInputTitle"><spring:message code="label.CLIA" /></label>
							<form:input id="pfiLabClia" path="pfiLabClia"  class="mL5 phoneField readonly"/>
						</div>
						<div style="float:left; width:28%">
							<label class="standardInputTitle"><spring:message code="label.labName" /></label>
							<form:input id="pfiLabName" path="pfiLabName" style="margin-left:5px;" class="standardInputlabText1 readonly"/>
						</div>
						<div style="float:left; width:22%">
							<label class="standardInputTitle">Approval Status</label>
							<form:input id="pfiLabApprovalStatus" path="pfiLabApprovalStatus"  class="standardInputlabText1 readonly" style="width:260px; margin-left:5px;"/>
						</div>	
						<div style="float:left; width:14%">
							<label class="standardInputTitle">Performs HIV Testing On-Site</label>
							<form:input id="insBasedHivTest" path="insBasedHivTest"  class="mL5 formSmallInputText readonly" style="width:30px;"/>
						</div>
						<div style="float:left; width:12%">
							<label class="standardInputTitle">Reports HIV to ECLRS</label>
							<form:input id="rptNysDohByEclrs" path="rptNysDohByEclrs"  class="mL5 formSmallInputText readonly" style="width:30px;"/>
						</div>						
					</div>
			</div>
			
 			<div>
					<div style="float:left; width:99%; margin: 10px 10px 10px 10px;">
						<div style="float:left; width:20%">
							<label class="standardInputTitle">Assigned Staff</label>
							<form:input id="recruitAssessStaffAssign" path="recruitAssessStaffAssign"  class="mL5 formSmallInputText required" type="text" title="Staff Assign"/>
						</div>
						<div style="float:left; width:15%">
							<label class="standardInputTitle">Assigned Date</label>
							<form:input id="recruitAssessDateAssign" path="recruitAssessDateAssign"  readonly="true" class="mL5 date-picker" title="Date Assign"/>
						</div>	
						<div id="recruitAssessStaffAssignByDiv" style="float:left; width:13%">
							<label class="standardInputTitle">Assigned Staff By</label>
							<form:input id="recruitAssessStaffAssignBy" path="recruitAssessStaffAssignBy"  class="mL5 formSmallInputText readonly" type="text" title="Staff Assign By"/>
						</div>						
					</div>
			</div>	
			
 			<div>
					<div style="float:left; width:99%; margin: 10px 10px 10px 10px;">
						<div style="float:left; width:20%">
							<label class="standardInputTitle">Outcome</label>
							<form:select id="recruitAssessOutcome" path="recruitAssessOutcome"  class="mL5 standardInputlabText1" style="width:250px;" title="Outcome">
								<form:option value="" label="-----Select-------"></form:option>
								<c:forEach items="${recruitAssessmentOutcomeList}" var="outcomeList">
									<form:option value="${outcomeList.RECRUIT_ASSESS_OUTCOME}" label="${outcomeList.RECRUIT_ASSESS_OUTCOME_DESC}"></form:option>
								</c:forEach>
							</form:select>
						</div>
						<div style="float:left; width:15%">
							<label class="standardInputTitle">Complete Date</label>
							<form:input id="recruitAssessDateComplete" path="recruitAssessDateComplete"  readonly="true" class="mL5 date-picker" title="Complete Date"/>
						</div>							
						<div id="recruitAssessStaffCompleteDiv" style="float:left; width:13%">
							<label class="standardInputTitle">Complete By Staff</label>
							<form:input id="recruitAssessStaffComplete" path="recruitAssessStaffComplete"  class="mL5 formSmallInputText readonly" title="Complete By Staff"/>
						</div>	
						<div style="float:left; width:30%">
							<label class="standardInputTitle" style="vertical-align: top;">Comments</label>
							<form:textarea path="recruitAssessComments" id="recruitAssessComments"
								title="Comments" maxlength="4000" cols="200" rows="3" style="width:74%; height:50px; border:1px solid gray;" class="mL2 roundedBorder"></form:textarea>									 
						</div>				
					</div>
			</div>	

					
			 <div class="button-row">
		 				<input type="button" id="sButton" class="button rounded effect-3 blue"  style="background-color: #62BBE8; margin-top: 25px;" value="Save" onclick="saveRecruitAssessFollowup(${clepUploadSurveyVO.uploadNumber})"/>	
		 				<input type="button" id="cButton"  class="cancelBtnclick button rounded effect-3 blue" style="background-color: #62BBE8;" value="Cancel" onclick="cancelRecruitAssessFollowup()"/>
			</div>					
											
			</form:form>