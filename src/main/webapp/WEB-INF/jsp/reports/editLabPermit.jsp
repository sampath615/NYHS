<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<form:form id="editLabPermit" name="editLabPermit" modelAttribute="labPermitVO">
			<div class="leftDiv50">
				<div class="standardInputDiv">
						<div class="label113mrg">
							<label class="standardInputTitle">Lab Permit</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<form:select path="permitCode" 	title="Permit Code" class="standardSelectMedText required readOnly">
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
							<input  type="text" id="dateValidFrom" readonly="true" name="dateValidFrom" title="Start Date" class="dPicker standardInputMedText" value="${labPermitVO.dateValidFrom}" />
						</div>
				</div>
								
				<div class="standardInputDiv" >
					<div class="label113mrg">
							<label class="standardInputTitle">Comments</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<form:textarea path="comments" id="permitComments" name="permitComments" rows="2" cols="50" title="Comments" maxLength="100" style="width:400px;" class="roundedBorder" />
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
							<input type="text" id="bhaeCertDate"  readonly="true" name="bhaeCertDate" dateValidations="noFutureDate" class="dPicker standardInputMedText" value="${labPermitVO.bhaeCertDate}" title="Certification Date"/>
						</div>
				</div>
				
				<div class="standardInputDiv ">
						<div class="label113mrg">
							<label class="standardInputTitle">End Date</label>
						</div>
						<div class="inputFieldInDialogWindow">
							<input type="text" id="dateValidTill" name="dateValidTill" readonly="true" class="dPicker standardInputMedText" value="${labPermitVO.dateValidTill}" title="End Date" />
						</div>
				</div>
				
				<form:hidden path="labPermitId"/>
			</div>		
		<div class="clearBoth"></div>
		<div class="button-row" style="margin-bottom: 12px; margin-top: 55px;">
			<a class="a_demo_search a_demo_add" style="color: #FFFFFF; margin-left: 5px;"
				onclick="editLabPermitFun();"> Save </a> <a class="a_demo_search a_demo_cancel"
				style="color: #FFFFFF; margin-left: 5px;"
				onclick="closeStdDialog(this);"> Cancel </a>
		</div>
		
</form:form>


<script src="${staticPath}/js/main.js"></script>
