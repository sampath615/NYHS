<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="roundedBorder mL10" style="width: 98%; background: #DDDDDD;">
	<div id="labDataContainer" class="container">
		<div id="confirmMessageDialog">
			<h4 id="confirmMessageContent"></h4>
		</div>

		<div id="hivNonTypeTestsDivContainer" class="container">
			<div
				style="float: left; width: 99%; padding-bottom: 5px; margin-left: 5px; margin-right: 5px; margin-top: 2px;">
				<div style="float: left; width: 20%;" class="abtNTDrpDwn mL20">
					Filter Active</div>

				<div style="float: left; width: 20%;">
					<div class="toggle-light">
						<div class=toggle data-checkbox=filterActive tabindex="15"></div>
					</div>
					<input type=checkbox disabled=disabled class=filterActive>
				</div>
			</div>

			<div
				style="float: left; width: 99%; padding-bottom: 5px; margin-left: 5px; margin-right: 5px; margin-top: 2px;">
				<div
					style="float: left; width: 99%; padding-bottom: 5px; margin-left: 5px; margin-right: 5px; margin-top: 2px;">
					<div style="float: left; width: 55%; margin-top: 5px"
						class="abtNTDrpDwn mL20">
						<span class="bold">Filter Type</span> <select id="filterType"
							name="filterType" title="Filter Type" class="required"
							style="margin-left: 10px; width: 70%">
							<option value="">Select</option>
							
							<c:forEach items="${filterTypeConditions}" var="filterType">
								<option value="${filterType.FILTER_TYPE_CODE}">${filterType.FILTER_TYPE_DESC} </option>
							</c:forEach>
						</select>
					</div>
					<div style="float: left; margin-right: 25px">
						<a href='#' class='testFiler' title='Filter Result'
							onClick='showFilterModalonPopup()'> Test </a>
					</div>
					<div>
						<span><a href="#" class="addProficiencyCondition"
							onclick="javascript:addNonTypeHIVDiv()"
							title="Add Process Condition">Add PC</a> </span>
					</div>
				</div>
			</div>

			<div
				style="float: left; width: 99%; padding-bottom: 5px; margin-left: 5px; margin-right: 5px; margin-top: 2px;">
				<div  class="fLW25 abtNTDrpDwn mL20">
					FIELD</div>

				<div class="fLW20">Operator</div>

				<div 
					class="fLW25 required standardInputSmallText2 quanCP">Value</div>

				<div class="fLW20">Condition</div>

			</div>
			<div id="hivNonTypeTestsDiv" class="hivNonTypeTestsDiv"
				style="float: left; width: 99%; padding-bottom: 5px; margin-left: 5px; margin-right: 5px; margin-top: 2px;">
				<div class="fLW25 abtNTDrpDwn mL20">
					<select id="testFilter0_FIELD_NAME" count="0"
						name="testFilterFieldName" class="abtNTTest"
						onchange="getResultForTestType('testFilter0_FIELD_NAME', '1', 'hivNonTypeTestsDiv', 'FIELD_NAME');"
						title="Field Name">
						<option value="FIRST NAME">FIRST NAME</option>
                        <option value="LAST NAME">LAST NAME</option>
                        <option value="MIDDLE NAME">MIDDLE NAME</option>
                        <option value="DOB">DOB</option>
                        <option value="ACCESSION NUM">ACCESSION NUM</option>
                        <option value="OBSERVATION RESULT TEXT">OBSERVATION RESULT TEXT</option>
                        <option value="INTERNAL ID">INTERNAL ID</option>
                        <option value="PATIENT ADDRESS LINE1">PATIENT ADDRESS LINE1</option>
                        <option value="PATIENT ADDRESS LINE2">PATIENT ADDRESS LINE2</option>
                        <option value="PATIENT CITY">PATIENT CITY</option>
                        <option value="PATIENT STATE">PATIENT STATE</option>
                        <option value="FACILITY NAME">FACILITY NAME</option>
                        <option value="FACILITY ADDRESS LINE1">FACILITY ADDRESS LINE1</option>
                        <option value="FACILITY ADDRESS LINE2">FACILITY ADDRESS LINE2</option>
                        <option value="FACILITY CITY">FACILITY CITY</option>
                        <option value="PROVIDER FIRST NAME">PROVIDER FIRST NAME</option>
                        <option value="PROVIDER LAST NAME">PROVIDER LAST NAME</option>
                        <option value="PROVIDER ADDRESS LINE1">PROVIDER ADDRESS LINE1</option>
                        <option value="PROVIDER ADDRESS LINE2">PROVIDER ADDRESS LINE2</option>
                        <option value="PROVIDER CITY">PROVIDER CITY</option>
                        <option value="LOINC">LOINC</option>
						</select>
				</div>

				<div class="fLW20">
					<select id="testFilter0_operator" count="0"
						name="testFilterOperator" class="abtNTTest"
						onchange="getResultForTestType('testFilter0_operator', '1', 'hivNonTypeTestsDiv', 'operator');"
						title="Operator">
						<option value="LIKE">LIKE</option>
						<option value="STARTWITH">STARTWITH</option>
						<option value="EQUAL">EQUAL</option>
						<option value="NOTEQUAL">NOTEQUAL</option>
						<option value="NOTSTARTWITH">NOTSTARTWITH</option>
						<option value="NOTLIKE">NOTLIKE</option>
						</select>
				</div>

				<div class="fLW25">
					<input id="testFilter0_value" count="0" name="testFilterValue"
						value="" class="standardInputSmallText2 quanCP"
						maxlength="100"
						onchange="getResultForTestType('testFilter0_value', '1', 'hivNonTypeTestsDiv', 'value');"
						onkeyup="capitalize(this.id,this.value)" placeholder="Value"
						style="width: 90%;" title="Value Field" />
				</div>

				<div class="fLW20">
					<select id="testFilter0_condition" count="0"
						name="testFilterCondition" class="required abtNTTest"
						onchange="getResultForTestType('testFilter0_condition', '1', 'hivNonTypeTestsDiv', 'condition');"
						title="Condition">
						<option value="">Select</option>
						<option value="AND">AND</option>
						<option value="OR">OR</option>
						</select>
				</div>

				<div class="fLW10">
					<span class="mL2" style="float: right; margin-right: 10px;"><img
						onclick="deleteTestDiv(this, 'testFilter0_condition', 'hivNonTypeTestsDiv');"
						src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete"
						style="vertical-align: middle; width: 14px;" /></span>
				</div>
			</div>
			<script>
		acrfRowCnt = acrfRowCnt + 1;
	    </script>
		</div>
	</div>
</div>

<div style="clear: both;">
	<br />
</div>

<div style="clear: both;">
	<br />
</div>
<input type="hidden" name="pfiName" id="pfiName" value="${param.pfi}" />
<div id="button-row" class="button-row" style="margin-bottom: 5%;">
	<a class="a_demo_search a_demo_save"
		style="color: #FFFFFF; margin-left: 5px;"
		onclick="updateLabProfFilter();"> Save </a> <a
		class="a_demo_search a_demo_cancel"
		style="color: #FFFFFF; margin-left: 5px;"
		onclick="closeAddProficiencyDialog();"> Cancel </a>
</div>