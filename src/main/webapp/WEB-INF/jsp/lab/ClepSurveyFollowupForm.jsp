<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

            <div id="submitCLEPResultTransactionMessage" class="transactionMessage"><span class="message"></span></div>
            
			<form:form modelAttribute="clepUploadSurveyVO" id ="submitClepSurveyFollowupData" action="submitSurveyFollowup" method="post">
			
			<div>
				
					<div style="float:left; width:99%; margin: 10px 10px 10px 10px;">
						<form:hidden path="uploadNumber"/>
						<form:hidden path="surveyNumber"/>
						<div style="float:left; width:15%">
							<label class="standardInputTitle">PFI</label>
							<form:input id="labPfi" path="labPfi"  class="mL25 formSmallInputText required readonly" maxLength="6" title="PFI"/>
						</div>
						<div style="float:left; width:20%">
							<label class="standardInputTitle"><spring:message code="label.CLIA" /></label>
							<form:input id="labClia" path="labClia"  class="mL25 standardInputSmallText readonly" maxLength="50"/>
						</div>
						<div style="float:left; width:30%">
							<label class="standardInputTitle"><spring:message code="label.labName" /></label>
							<form:input id="labName" path="labName" style="margin-left:25px;" class="standardInputlabText1 readonly" maxLength="100"/>
						</div>
						<div style="float:left; width:15%">
							<label class="standardInputTitle">Survey Begin Date</label>
							<form:input id="surveyBeginDate" path="surveyBeginDate" class="mL25 formSmallInputText readonly" title="Survey Begin Date"/>
						</div>
						<div style="float:left; width:15%">
							<label class="standardInputTitle">Survey End Date</label>
							<form:input id="surveyEndDate" path="surveyEndDate"  class="mL25 formSmallInputText readonly" title="Survey End Date"/>
						</div>						
					</div>
			</div>
			
			<div>
					<div style="float:left; width:99%; margin: 10px 10px 10px 10px;">
						<div style="float:left; width:15%">
							<label class="standardInputTitle">Assigned Staff</label>
							<form:input id="assignedStaffId" path="assignedStaffId"  class="mL25 formSmallInputText required" type="text" maxLength="6" title="Assigned Staff"/>
						</div>
						<div style="float:left; width:20%">
							<label class="standardInputTitle">Assigned Date</label>
							<form:input id="assignedDate" path="assignedDate"  readonly="true" class="mL25 date-picker" title="Assigned Date"/>
						</div>
						<div id="assignedByDiv" style="float:left; width:20%">
							<label class="standardInputTitle">Assigned By</label>
							<form:input id="assignedBy" path="assignedBy"  class="mL25 formSmallInputText required readonly" title="Assigned By"/>
						</div>				
					</div>
			</div>	
					
			<div>
					<div style="float:left; width:99%; margin: 10px 10px 10px 10px;">
						<div style="float:left; width:15%">
							<label class="standardInputTitle">Close Date</label>
							<form:input id="surveyCloseDate" path="surveyCloseDate"  readonly="true" class="mL25 date-picker" title="Survey Close Date"/>
						</div>
						<div id="surveyCloseByDiv" style="float:left; width:20%">
							<label class="standardInputTitle">Close By</label>
							<form:input id="surveyCloseBy" path="surveyCloseBy"  class="mL25 formSmallInputText readonly" title="Survey Close By"/>
						</div>	
						<div style="float:left; width:30%">
							<label class="standardInputTitle" style="vertical-align: top;">Comments:</label>
							<form:textarea path="comments" id="comments"
								title="Comments" maxlength="4000" cols="200" rows="3" style="width:74%; height:50px; border:1px solid gray;" class="mL25 roundedBorder"></form:textarea>									 
						</div>														
					</div>
			</div>	
					
			 <div class="button-row">
		 				<input type="button" id="sButton" class="button rounded effect-3 blue"  style="background-color: #62BBE8; margin-top: 25px;" value="Save" onclick="saveFollowupHIVSurvey(${clepUploadSurveyVO.uploadNumber})"/>	
		 				<input type="button" id="cButton"  class="cancelBtnclick button rounded effect-3 blue" style="background-color: #62BBE8;" value="Cancel" onclick="cancelFollowupHIVSurvey()"/>
			</div>					
											
			</form:form>