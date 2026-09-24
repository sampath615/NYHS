<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form:form id="addLabInstrument" name="addLabInstrument"
			modelAttribute="labInstrumentDetailsVO">
			<div class="leftDiv50">
				<div class="standardInputDiv">
						<div class="label113mrg">
							<label class="standardInputTitle">Lab Permit</label>
						</div>
						<div style=" float:left">
                            <input id="currentSelectedPermitCodeDescription" style="width:200px;" name="currentSelectedPermitCodeDescription" class="standardInputMedText mR50 readonly" value="${currentSelectedPermitCodeDescription}" readonly="true" type="text" tabindex="-1"/>
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
							<form:select path="hivInstrumentId" class="standardSelectMedText required"
									title="Instrument Code" >
									<form:option value="" label="--- Select ---" />
									<c:forEach var="instrument" items="${instrumentCodeAndDescriptionMap}" >
										<form:option value="${instrument.instrumentId}" label="${instrument.instrumentName}" />
									</c:forEach>
							</form:select>
						</div>
				</div>
				<div class="standardInputDiv ">
						<div class="label113mrg">
							<label class="standardInputTitle">End Date</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<input type="text" id="dateValidTill" name="dateValidTill" readonly class="standardInputMedText dPicker" title="End Date" />
						</div>
				</div>
			</div>
			
			
			<!-- <div class="rightDiv" >
				
				<div class="standardInputDiv ">
						<div class="label110mrg">
							<label class="standardInputTitle">Instrument Description</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<form:select path="hivInstrumentId" class="standardInputMedText required"
									title="Instrument Code" >
									<form:option value="" label="--- Select ---" />
									<form:options items="${instrumentCodeAndDescriptionMap}" />
							</form:select>
						</div>
				</div>
				
				
				
			</div> -->
				
			<input type="hidden" name="permitCode" id="permitCode" value="${currentSelectedPermitCode}" />
			<input type="hidden" name="permitDescription" id="permitDescription" value="${currentSelectedPermitCodeDescription}" />
		<div class="clearBoth"></div>
		<div class="button-row" style="margin-bottom: 12px; margin-top: 55px;">
			<a class="a_demo_search a_demo_add" style="color: #FFFFFF; margin-left: 5px;"
				onclick="addLabInstrumentFun();"> Add </a> <a class="a_demo_search a_demo_cancel"
				style="color: #FFFFFF; margin-left: 5px;"
				onclick="closeStdDialog(this);"> Cancel </a>
		</div>
</form:form>

<script src="${staticPath}/js/main.js"></script>
