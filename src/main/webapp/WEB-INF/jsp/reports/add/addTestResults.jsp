<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<form id="addTestResults" name="addTestResults">
				<input type="hidden" id="associatedLoincCode"   name="associatedLoincCode" value="${param.associatedLoincCode}" />
				<div class="standardInputDiv">
						<div class="label150mrg">
							<label >Value Type</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<select role="select" class="required" onchange="handleTestResultFieldStyles('#')" id="typeOfResult" name="typeOfResult" title="Value type">
								<option role="option" value="NUMERIC">NUMERIC</option>
								<option role="option" value="STRING">STRING</option>
							</select>
						</div>
				</div>
				<div class="standardInputDiv">
						<div class="label150mrg">
							<label>Accepted Value</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<input type="text" name="acceptedValue" id="acceptedValue" class="standardInputMedText" title="Accepted Value" value="" maxlength="100"/>
						</div>
				</div>
				<div class="standardInputDiv">
						<div class="label150mrg">
							<label >Range Lower Bound</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<input type="text" name="rangeLowerBound" id="rangeLowerBound" class="required standardInputMedText" title="Lower bound range"  value="" maxlength="15"/>
						</div>
				</div>
				<div class="standardInputDiv">
						<div class="label150mrg">
							<label >Range Upper Bound</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<input type="text" name="rangeUpperBound" id="rangeUpperBound" class="required standardInputMedText" title="Upper bound range"  value="" maxlength="15"/>
						</div>
				</div>
				<div class="standardInputDiv">
						<div class="label150mrg">
							<label class="">Standard Results</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<select role="select" class="required"  id="standardResults" name="Standard Results" title="Standard Results">
								<option name="" value="">Select</option>
								<c:forEach items="${sessionScope.eharsTestResults}" var="testResult">
									<option value="${testResult.STANDARDRESULTSDESC}" name="${testResult.STANDARDRESULTSCODE}">${testResult.STANDARDRESULTSDESC}</option> 											
								</c:forEach>
								
							</select>
						</div>
				</div>
			</div>
			<div class="clearBoth"></div>
			<div class="button-row" style="margin-bottom: 12px; margin-top: 35px;">
				<a class="a_demo_search a_demo_save" style="color: #FFFFFF; margin-left: 5px;"
					onclick="addNewTestResults();"> Save </a> <a class="a_demo_search a_demo_cancel"
					style="color: #FFFFFF; margin-left: 5px;"
					onclick="closeStdDialog(this);"> Cancel </a>
			</div>	 			
						
			
<form>