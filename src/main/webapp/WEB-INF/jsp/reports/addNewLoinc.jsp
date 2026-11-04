<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form id="addingLoincDetails" >
				<div class="standardInputDiv">
						<div class="label150mrg">
							<label >LOINC Code</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<input id="newLoincCode" name="newLoincCode" class="standardInputMedText mR50 required"  type="text" maxlength="25" title="LOINC Code"/>
						</div>
				</div>
				
				<div class="standardInputDiv">
						<div class="label150mrg">
							<label >Test type</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<select id="newTestType" name="newTestType" class="standardInputMedText mR50 required" onchange="changeLoincDescriptions(this)" title="Test type">
								<option value="">select</option>
								<c:forEach items="${loincTestTypes}" var="testType">
									<option value="${testType}">${testType}</option>
								</c:forEach>
							</select>
						</div>
				</div>
				
				<div class="standardInputDiv">
						<div class="label150mrg">
							<label >LOINC Description</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<select id="newLoincDescription" name="newLoincDescription" class="standardInputMedText mR50" onchange="changeLoincUnitsOfMeasurements(this)" title="LOINC Description" disabled>
								<option value="">select</option>
							</select>
						</div>
				</div>
				
				<div class="standardInputDiv">
						<div class="label150mrg">
							<label >Units of Measurement</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<select id="newUnitsOfMeasurements" name="newUnitsOfMeasurements" class="standardInputMedText mR50" title="Units of Measurement" disabled>
								<option value="">select</option>
							</select>
						</div>
				</div>
			
		<div class="clearBoth"></div>
		<div class="button-row" style="margin-bottom: 12px; margin-top: 35px;">
			<a class="a_demo_search a_demo_add" style="color: #FFFFFF; margin-left: 5px;"
				onclick="addNewLabLoinc();"> Add </a> <a class="a_demo_search a_demo_cancel"
				style="color: #FFFFFF; margin-left: 5px;"
				onclick="closeLabDetailsDialog();"> Cancel </a>
		</div>
		
</form>



