<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<div class="stdDetailsDiv" id="stdInfoDiv">
	<form id="addingLabPermitAndInstrument" >
		<div class="leftDiv">
			<div class="standardInputDiv">
					<div class="label110mrg">
						<label class="standardInputTitle"><spring:message
								code="label.labPermit" /></label>
					</div>
					<div class="inputFieldInDialogWindow">
						<select name="addPermitCode" id="addPermitCode"	class="standardInputMedText" >
							<option value="">Select Lab Permit</option>
								 <c:forEach items="${permitCodeAndDescriptionMap}" var="labPermit">                  
							        <option value="${labPermit.key}">${labPermit.value}</option>                    
							    </c:forEach>		
						</select>
					</div>
			</div>
			
			<div class="standardInputDiv permitDates">
					<div class="label110mrg">
						<label class="standardInputTitle">Date From</label>
					</div>
					<div class="inputFieldInDialogWindow">
						<input type="text" id="addDateValidFrom" name="addDateValidFrom" class="standardInputMedText dPicker" />
					</div>
			</div>
			
		</div>
		
		
		<div class="rightDiv" >
			
			<div class="standardInputDiv" >
				<div id="addInstrumentComboBox">
					<div class="label110mrg" >
						<label class="standardInputTitle"><spring:message
								code="label.labInstrument" /></label>
					</div>
					<div class="inputFieldInDialogWindow">
				
						<select name="addInstrumentCode" id="addInstrumentCode"	class="standardSelectMedText" >
							<option value="">Select Lab Instrument</option>
								<c:forEach var="instrument" items="${instrumentCodeAndDescriptionMap}" >
										<option value="${instrument.instrumentId}">${instrument.instrumentName}</option>
									</c:forEach>
						</select>
					</div>
				</div>
			</div>
			
			<div class="standardInputDiv permitDates">
					<div class="label110mrg">
						<label class="standardInputTitle">Date Till</label>
					</div>
					<div class="inputFieldInDialogWindow">
						<input type="text" id="addDateValidTill" name="addDateValidTill" class="standardInputMedText dPicker" />
					</div>
			</div>
			
			
			
		</div>		
	</form>
	<div class="clearBoth"></div>
	<div class="button-row" style="margin-bottom: 12px; margin-top: 55px;">
		<a class="a_demo_search a_demo_add" style="color: #FFFFFF; margin-left: 5px;"
			onclick="addLabPermitAndIstrumentDetails();"> Add </a> <a class="a_demo_search a_demo_cancel"
			style="color: #FFFFFF; margin-left: 5px;"
			onclick="closeStdDialog(this);"> Cancel </a>
	</div>
	
</div>			
