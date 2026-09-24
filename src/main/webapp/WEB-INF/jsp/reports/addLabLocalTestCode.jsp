<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<form:form id="addLabTest" name="addLabTest" modelAttribute="labTestMappingVO">
	<div class="">
		<div class="standardInputDiv">
				<div class="label200mrg">
					<label class="standardInputTitle">Lab Local Test Code Description</label>
				</div>
				<div class="inputFieldInDialogWindow">
					<input type="text" name="testDescription" id="testDescription" class="required standardInputMedText" maxLength="500"/>
				</div>
		</div>
		<div class="standardInputDiv ">
			<div class="label200mrg">
				<label class="standardInputTitle">Start Date</label>
			</div>
			<div class="inputFieldInDialogWindow">
				<input type="text" id="dateValidFrom"  readonly name="dateValidFrom" title="Start Date" class="standardInputMedText dPicker required" />
			</div>
		</div>
		<div class="standardInputDiv ">
				<div class="label200mrg">
					<label class="standardInputTitle">End Date</label>
				</div>
				<div class="inputFieldInDialogWindow">
					<input type="text" id="dateValidTill" name="dateValidTill" readonly class="standardInputMedText dPicker" title="End Date" />
				</div>
		</div>
	</div>	
	<div class="clearBoth"></div>
	<div class="button-row" style="margin-bottom: 12px; margin-top: 25px;">
		<a class="a_demo_search a_demo_add" style="color: #FFFFFF; margin-left: 5px;"
			onclick="addLabTestFun();"> Add </a> <a class="a_demo_search a_demo_cancel"
			style="color: #FFFFFF; margin-left: 5px;"
			onclick="$('#addLabDetailsDialog').dialog('close');closeLabDetailsDialog();"> Cancel </a>
	</div>	
</form:form>
<script src="${staticPath}/js/main.js"></script>

