<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form id="addLabLoinc" name="addLabLoinc" modelAttribute="labLoincMappingVO">
			<div class="leftDiv50">
				<div class="standardInputDiv">
						<div class="label110mrg">
							<label class="standardInputTitle">Lab LOINC</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<form:select path="loincCode" 	title="LOINC Code" class="standardSelectMedText required" style="width:500px;">
									<form:option value="" label="--- Select ---" />
									<form:options items="${loincCodeAndDescriptionMap}" />
							</form:select>
						</div>
				</div>
				<div class="standardInputDiv" >
					<div class=label113mrg>
							<label class="standardInputTitle">Test Kit</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<form:select path="testKit" title="Test Kit" class="standardSelectMedText" style="width:400px;">
									<form:option value="" label="--- Select ---" />
									<c:forEach var="testKit" items="${testKitCodesAndDescriptionsMap}" >
										<form:option value="${testKit.testKitCd}" label="${testKit.testKitDesc}" />
									</c:forEach>									
									
							</form:select>
							
						</div>
				</div>					
				<div class="standardInputDiv ">
						<div class="label113mrg">
							<label class="standardInputTitle">Start Date</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<input type="text" id="dateValidFrom"  readonly name="dateValidFrom" title="Start Date" class="standardInputMedText dPicker required" />
						</div>
				</div>
			</div>
			<div class="rightDiv49">
				<div class="standardInputDiv " >
						<div class="label113mrg">
							<label class="standardInputTitle">Instrument</label>
						</div>
						<div class="inputFieldInDialogWindow" style="width:200px; ">
							<form:select path="instrument" title="Instrument" class="standardSelectMedText">
									<form:option value="" label="--- Select ---" />
									<c:forEach var="instrument" items="${instrumentsCodesAndDescriptionsMap}" >
										<form:option value="${instrument.instrumentCd}" label="${instrument.instrumentDesc}" />
									</c:forEach>									
							</form:select>
						</div>
				</div>
				<div class="standardInputDiv" >
					<div class=label113mrg>
							<label class="standardInputTitle">VL Lowest Level of Detection</label>
					</div>
					<div class="inputFieldInDialogWindow">
							<form:select path="vlDetectionLevel" title="VL Lowest Level of Detection" class="standardSelectMedText">
									<form:option value="" label="--- Select ---" />
									<c:forEach var="vlDetectionLevel" items="${vlDetectionLevelsCodesAndDescriptionsMap}" >
										<form:option value="${vlDetectionLevel.detectionCd}" label="${vlDetectionLevel.detectionDesc}" />
									</c:forEach>									
							</form:select>
					</div>
				</div>				
				<div class="standardInputDiv ">
						<div class="label113mrg">
							<label class="standardInputTitle">End Date</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<input type="text" id="dateValidTill" name="dateValidTill" readonly class="standardInputMedText dPicker" title="End Date" dateValidations="noPastDate" />
						</div>
				</div>
			</div>
		<div class="clearBoth"></div>
		<div class="button-row" style="margin-bottom: 12px; margin-top: 55px;">
			<a class="a_demo_search a_demo_add" style="color: #FFFFFF; margin-left: 5px;"
				onclick="addLabLoincFun();"> Add </a> <a class="a_demo_search a_demo_cancel"
				style="color: #FFFFFF; margin-left: 5px;"
				onclick="$('#addLabDetailsDialog').dialog('close');closeLabDetailsDialog();"> Cancel </a>
		</div>
		
</form:form>
<script src="${staticPath}/js/main.js"></script>


