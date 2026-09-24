<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<form:form id="newMasterFacForm" name="newMasterFacForm" modelAttribute="masterFacilityVO">
	<div id="newMasterFacDiv" title="Add New Master facility And Standardize">
	  <div style="height: 150px;">
		<div class="stdDetailsDiv" id="stdInfoDiv">
			<div class="leftDiv">	
				<div class="standardInputDiv">
					<div class="labelInDialogWindow">
						<font class="standardInputTitle"><spring:message code="label.facilityName" /></font>
					</div>
					<div class="inputFieldInDialogWindow">
						<input id="grdfacilityName" name="grdfacilityName" class="required standardInputMedText" value="" type="text" maxLength="100" title="<spring:message code='title.facilityName' />">
					</div>
				</div>
			</div>
			<div>&nbsp;&nbsp;</div><br/>	
			<div>&nbsp;&nbsp;</div><br/>
			<div class="button-row">
				<a class="a_demo_search" style ="color:#FFFFFF; margin-left: 5px;" onclick="submitNewPageGrandResults();"> Submit </a>
				<a class="a_demo_search" style ="color:#FFFFFF; margin-left: 5px;" onclick="closeStdDialog(this);"> Cancel </a>
			</div>
			<div>&nbsp;&nbsp;</div>
		</div>
	  </div>
	</div>
</form:form>
