<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<form:form id="addLabCommunication" name="addLabCommunication" modelAttribute="labInteractionHistoryVO">
	<div class="leftDiv35">
		<div class="standardInputDiv">
				<div class="label180mrg">
					<label class="standardInputTitle">Communication Type:</label>
				</div>
				<div class="inputFieldInDialogWindow">
					<form:select path="communicationType" 	title="Communication Type" class="standardSelectMedText required">
						<form:option value="" label="--- Select ---" />
						
						<c:forEach var="typeMap" items="${communicationTypes}" >
								<form:option value="${typeMap.COMMUNICATION_TYPE}" label="${typeMap.COMMUNICATION_TYPE_DESCRIPTION}" />
						</c:forEach>
							
					</form:select>
				</div>
		</div>
		<div class="standardInputDiv" >
			<div class="label180mrg">
					<label class="standardInputTitle">Date of Communication:</label>
				</div>
				<div class="inputFieldInDialogWindow">
					<input type="text" id="interactionDate"  readonly name="interactionDate" dateValidations="noFutureDate" class="dPicker standardInputMedText required" title="Date of Communication"/>
				</div>
		</div>	
<%-- 		<div class="standardInputDiv">
				<div class="label180mrg">
					<label class="standardInputTitle">Issue Resolved:</label>
				</div>
				<div class="inputFieldInDialogWindow">
					<form:select path="issueResolved" 	title="Issue Resolved" class="standardSelectMedText optional">
							<form:option value="" label="--- Select ---" />
							<form:option value="Yes" label="Yes" />
							<form:option value="No" label="No" />									
					</form:select>
				</div>
		</div> --%>
	</div>		
	
	<div class="rightDiv64" >	
		<div class="standardInputDiv" >
			<div class="label180mrg">
				<label class="standardInputTitle">Comments:</label>
			</div>
			<div class="inputFieldInDialogWindowLarge">
				<form:textarea path="interactionNotes" name="interactionNotes" class="standardInputMedTextLarge" rows="20" cols="200" maxLength="5000"></form:textarea>
			</div>
		</div>
	</div>		
<div class="clearBoth"></div>
<div class="button-row" style="margin-bottom: 12px; margin-top: 55px;">
	<a class="a_demo_search a_demo_add" style="color: #FFFFFF; margin-left: 5px;"
		onclick="addLabCommunicationFun();"> Add </a> <a class="a_demo_search a_demo_cancel"
		style="color: #FFFFFF; margin-left: 5px;"
		onclick="closeStdDialog(this);"> Cancel </a>
</div>
		
</form:form>


<script src="${staticPath}/js/main.js"></script>
