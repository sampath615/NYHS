<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<form:form id="addLabPermit" name="addLabPermit" modelAttribute="labPermitVO">
			<div class="leftDiv50">
				<div class="standardInputDiv">
						<div class="label113mrg">
							<label class="standardInputTitle">Lab Permit</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<form:select path="permitCode" 	title="Permit Code" class="standardSelectMedText required">
									<form:option value="" label="--- Select ---" />
									
									<c:forEach var="permit" items="${permitCodeAndDescriptionMap}" >
										<form:option value="${permit.permitCode}" label="${permit.permitDescription}" />
									</c:forEach>
									
							</form:select>
						</div>
				</div>
			
				<div class="standardInputDiv ">
						<div class="label113mrg">
							<label class="standardInputTitle">Start Date</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<input type="text" id="dateValidFrom"  readonly name="dateValidFrom" title="Start Date" class="dPicker standardInputMedText" />
						</div>
				</div>
								
				<div class="standardInputDiv" >
					<div class="label113mrg">
							<label class="standardInputTitle">Comments</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<textarea id="permitComments" name="permitComments" rows="2" cols="50" title="Comments" maxLength="100" style="width:400px;" class="roundedBorder"></textarea>
						</div>
				</div>
								
			</div>
			
			
			<div class="rightDiv49" >
				
				<div class="standardInputDiv" ></div>

				<div class="standardInputDiv" >
					<div class="label113mrg">
							<label class="standardInputTitle">Approve Date</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<input type="text" id="bhaeCertDate"  readonly name="bhaeCertDate" dateValidations="noFutureDate" class="dPicker standardInputMedText" title="Certification Date"/>
						</div>
				</div>
				
				<div class="standardInputDiv ">
						<div class="label113mrg">
							<label class="standardInputTitle">End Date</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<input type="text" id="dateValidTill" name="dateValidTill" readonly class="dPicker standardInputMedText" title="End Date" />
						</div>
				</div>
				
				
				
			</div>		
		<div class="clearBoth"></div>
		<div class="button-row" style="margin-bottom: 12px; margin-top: 55px;">
			<a class="a_demo_search a_demo_add" style="color: #FFFFFF; margin-left: 5px;"
				onclick="addLabPermitFun();"> Add </a> <a class="a_demo_search a_demo_cancel"
				style="color: #FFFFFF; margin-left: 5px;"
				onclick="closeStdDialog(this);"> Cancel </a>
		</div>
		
</form:form>


<script src="${staticPath}/js/main.js"></script>
