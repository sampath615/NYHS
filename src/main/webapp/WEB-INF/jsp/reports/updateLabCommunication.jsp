<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<form:form id="updateLabCommunication" name="updateLabCommunication" modelAttribute="labInteractionHistoryVO">
	<div id="divId" style="display:none;">
		<input type="text" id="interactionId" name="interactionId" class="standardInputMedText"/>
	</div>
	<div class="leftDiv35">
		<div class="standardInputDiv">
				<div class="label180mrg">
					<label class="standardInputTitle">Communication Type:</label>
				</div>
				<div class="inputFieldInDialogWindow">
					<form:select disabled="true" path="communicationType" 	title="Communication Type" class="standardSelectMedText required">
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
				<!-- <form:input type="text" id="interactionDate" path="interactionDate" dateValidations="noFutureDate" class="dPicker standardInputMedText required" title="Date of Communication"/> -->
				<form:input readonly="true" type="text" id="interactionDate" path="interactionDate" dateValidations="noFutureDate" class="standardInputMedText" title="Date of Communication"/>
			</div>
		</div>		
		<div class="standardInputDiv">
				<div class="label180mrg">
					<label class="standardInputTitle">Issue Resolved:</label>
				</div>
				<div class="inputFieldInDialogWindow">
					<c:choose>
						<c:when test="${labInteractionHistoryVO.interactionBy == userObject.userId}">
							<form:select path="issueResolved" 	title="Issue Resolved" class="standardSelectMedText optional">
								<form:option value="" label="--- Select ---" />
								<form:option value="Yes" label="Yes" />
								<form:option value="No" label="No" />									
							</form:select>
						</c:when>
						<c:otherwise>
							<form:select disabled="true" path="issueResolved"	title="Issue Resolved" class="standardSelectMedText optional">
								<form:option value="" label="--- Select ---" />
								<form:option value="Yes" label="Yes" />
								<form:option value="No" label="No" />									
							</form:select>
						</c:otherwise>
					</c:choose>
				</div>
		</div>
	</div>		
	
	<div class="rightDiv64" >		
		<div class="standardInputDiv" >
			<div class="label180mrg">
				<label class="standardInputTitle">Comments:</label>
			</div>
			<div class="inputFieldInDialogWindowLarge">
				<form:textarea readonly="true" path="interactionNotes" name="interactionNotes" class="standardInputMedTextLarge" rows="20" cols="200" maxLength="5000"></form:textarea>
			</div>
		</div>
	</div>		
	<div class="clearBoth"></div>
	<div class="button-row" style="margin-bottom: 12px; margin-top: 55px;">
		<a class="a_demo_search a_demo_add" style="color: #FFFFFF; margin-left: 5px;"
			onclick="updateLabCommunicationFun();"> Save </a> 
		<a class="a_demo_search a_demo_cancel" style="color: #FFFFFF; margin-left: 5px;"
			onclick="closeStdDialog(this);"> Cancel </a>
	</div>
		
</form:form>


<script src="${staticPath}/js/main.js"></script>
