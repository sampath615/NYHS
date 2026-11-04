<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>




<c:set var="labLoincDetails" value="${sessionScope.currentLabLoincCodeDetails}" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<form:form id="addLabLoinc" name="addLabLoinc" modelAttribute="labLoincMappingVO">
			<div class="leftDiv">
				<div class="standardInputDiv">
						<div class="label110mrg">
							<label class="standardInputTitle">Lab Local Code</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<input type="text" name="localCode" id="localCode" class="required standardInputMedText width180"/>
						</div>
				</div>
				<div class="standardInputDiv " id="localCodeStartDate">
						<div class="label110mrg">
							<label class="standardInputTitle">Start Date</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<input type="text" id="dateValidFrom"  readonly name="dateValidFrom" title="Start Date" class="standardInputMedText dPicker required width180" />
						</div>
				</div>
				<div class="standardInputDiv " id="localCodeEndDate">
						<div class="label110mrg">
							<label class="standardInputTitle">End Date</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<input type="text" id="dateValidTill" name="dateValidTill" readonly class="standardInputMedText dPicker width180" title="End Date" />
						</div>
				</div>
			</div>
			<div class="rightDiv">
				<div class="standardInputDiv">
					<div class="label110mrg">
						<label class="standardInputTitle">Lab LOINC</label>
					</div>
					<div class="inputFieldInDialogWindow">                                                    
                        <form:select path="loincCode" title="LOINC Code" class="required standardSelectMedText" multiple="true">
                        	<form:options items="${loincCodeAndDescriptionMap}" />
                        </form:select>     
					</div>
				</div>
			</div>
			
</form:form>
		<div class="clearBoth"></div>
		<div style="margin-top:20px">
			<a href="#" onclick="loadLoincDetails(${param.localCodeMapId})">Add new LOINC Details</a> 			
		</div>	
		
		<div class="button-row" style="margin-bottom: 12px; margin-top: 35px;">
			<a class="a_demo_search a_demo_save" style="color: #FFFFFF; margin-left: 5px;"
				onclick="addLabLocalCodeFun();"> Save </a> <a class="a_demo_search a_demo_cancel"
				style="color: #FFFFFF; margin-left: 5px;"
				onclick="closeLabDetailsDialog();"> Cancel </a>
		</div>

<c:if test="${selectedLoincCodesOfLocalCodes != null}">
		
<script>
var selectedLoincCodes = ${selectedLoincCodesOfLocalCodes};
</script>
</c:if>
<script src="${staticPath}/js/main.js"></script>