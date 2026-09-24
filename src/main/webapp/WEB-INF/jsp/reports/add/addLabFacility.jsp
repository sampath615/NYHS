<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div id="addLabDetails">
	<div class="leftDiv">
		<div class="standardInputDiv">
				<div class="label110mrg">
					<label class="standardInputTitle"><spring:message code="label.CLIA" /></label>
				</div>
				<div class="inputFieldInDialogWindow">
					<input type="text" name="addClia" id="addClia" class="required standardInputMedText" maxlength="50" title="Clinical laboratory"/>
				</div>
		</div>
	</div>
</div>
<div class="clearBoth"></div>

<div id="pfiDetails">
	<table id="addNewLabDetailsToClia"></table>
</div>

<a href="#" onclick="addPFIDetails()">Add PFI details</a> 			

<div class="button-row" style="margin-bottom: 12px; margin-top: 35px;">
	<a class="a_demo_search a_demo_save" style="color: #FFFFFF; margin-left: 5px;"
		onclick="addCliaWithFacilities();"> Save </a> <a class="a_demo_search a_demo_cancel"
		style="color: #FFFFFF; margin-left: 5px;"
		onclick="closeStdDialog(this);"> Cancel </a>
</div>
