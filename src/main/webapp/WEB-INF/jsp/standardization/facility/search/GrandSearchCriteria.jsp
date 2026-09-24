<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<form:form id="grandSearchForm" name="grandSearchForm" modelAttribute="masterFacilityVO">
	<div class="grandDetailsDiv" id="grandInfoDiv">
		<div class="leftdiv">	
			<div class="standardInputDiv">
				<div style="float: left">
					<label class="standardInputTitle"><spring:message code="label.facilityName" /></label>
				</div>
				<div style="float: right">
					<input id="srchGrandName" name="srchGrandName" class="standardInputMedText mR50" value="" type="text"  title="<spring:message code='title.facilityName' />">
				</div>
			</div>
		</div>
		<div style="text-align: center; margin-bottom: 15px; margin-top: 36px;">
			<a class="a_demo_search" style ="color:#FFFFFF; margin-left: 5px;" onclick="dispSearchResults();"> Search </a>
		</div>
	</div>
</form:form>