<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form id="addSubmissionTypeToPFI"  >
			<div class="leftDiv50">
				<div class="standardInputDiv">
						<div class="label110mrg" style="width:20%">
							<label class="standardInputTitle" style="margin:0">Submission Type</label>
						</div>
						<div class="inputFieldInDialogWindow" >
							<select id="addSubmissionType" name="addSubmissionType" title="Submission Type" class="standardSelectMedText required">
									<option value="" >--- Select ---</option>
									<c:forEach items="${submissionTypes}" var="submissionType">  
									   <option value="${submissionType.key}">${submissionType.value}</option>  
								   </c:forEach> 
							</select>
						</div>
				</div>
				<div class="standardInputDiv ">
						<div class="label113mrg">
							<label class="standardInputTitle" style="margin:0">Start Date</label>
						</div>
						<div class="inputFieldInDialogWindow" >
							<input type="text" id="dateValidFrom"  readonly name="dateValidFrom" title="Start Date" class="standardInputMedText dPicker" />
						</div>
				</div>
				<div class="standardInputDiv">
						<div class="label110mrg" style="width:20%">
							<label class="standardInputTitle" style="margin:0">LIS/Vendor</label>
						</div>
						<div class="inputFieldInDialogWindow" >
							<select id="addLISVendor" name="addLISVendor" title="LIS Vendor" class="standardSelectMedText">
									<option value="" >--- Select ---</option>
									<c:forEach items="${labLisVendors}" var="labLisVendor">  
									   <option value="${labLisVendor.key}">${labLisVendor.value}</option>  
								   </c:forEach> 
							</select>
						</div>
				</div>
			</div>
			<div class="rightDiv49">
				<div class="standardInputDiv " >
						<div class="label110mrg" style="width:30%">
							<label class="standardInputTitle" style="margin:0">Is this a Primary Submission Type?</label>
						</div>
						<div class="inputFieldInDialogWindow" >
							<select id="primarySubmission" name="primarySubmission" title="Primary Submission" class="standardSelectMedText required">
								<option value="">--- Select ---</option>
								<option value="Y">Yes</option>
								<option value="N">No</option>									
							</select>
						</div>
				</div>
				<div class="standardInputDiv ">
						<div class="label113mrg">
							<label class="standardInputTitle">End Date</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<input type="text" id="dateValidTill" name="dateValidTill" readonly class="standardInputMedText dPicker" dateValidations="noPastDate" title="End Date" />
						</div>
				</div>
			</div>
			<input type="hidden" name="transactionResult" id="transactionResult_addSubmission" value="<c:out value='${transactionResult}' />" />
			<input type="hidden" name="transactionMessage" id="transactionMessage_addSubmission" value="<c:out value='${transactionMessage}' />" />
		
		<div class="clearBoth"></div>
		<div class="button-row" style="margin-bottom: 12px; margin-top: 55px;">
			<a class="a_demo_search a_demo_add" style="color: #FFFFFF; margin-left: 5px;"
				onclick="addSubmissionTypeToFacility();"> Add </a> <a class="a_demo_search a_demo_cancel"
				style="color: #FFFFFF; margin-left: 5px;"
				onclick="$('#addLabDetailsDialog').dialog('close');closeLabDetailsDialog();"> Cancel </a>
		</div>
		
</form>
<script src="${staticPath}/js/main.js"></script>


