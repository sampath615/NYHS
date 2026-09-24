<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<div id="labInteractionTransactionMessage" class="transactionMessage"><span class="message"></span></div>
<form id="labInteractionHistory" name="labInteractionHistory" >
			<div class="fieldHolder75 ">
				<div id="labInteractionHistory" class="jqGridTableSection">
					<table id="labInteractionHistoryTable"></table>
					<table id="labInteractionHistoryPager"></table>
				</div>
			</div>
			
			<div class="fieldHolder25 " >
				<div id="existingContact">
					<div class="standardInputDiv">
							<div class="label110mrg">
								<label class="standardInputTitle">Type<span class="requiredInd">*</span></label>
							</div>
							<div class="inputFieldInDialogWindow">
								<select id="interactionContactType" name="interactionContactType" class="standardSelectMedText required"
										title="Contact Type">
										<option value="">--- Select ---</option>
										<c:forEach items="${communicationTypes}" var="contactType">
											<option value="${contactType.key}">${contactType.value}</option>
										</c:forEach>
								</select>
							</div>
					</div>
				</div>				
				<div class="standardInputDiv" id="commentsForExistingContact">
						<div class="label110mrg">
							<label class="standardInputTitle">Comments<span class="requiredInd">*</span></label>
						</div>
						<div class="inputFieldInDialogWindow">
							<textarea id="interactionNotes" name="interactionNotes" class="standardInputMedText required" title="Interaction Comments" ></textarea>
						</div>
				</div>
				
				<div class="clearBoth"></div>
				<div class="button-row" style="margin-bottom: 12px; margin-top: 55px;">
					<a class="actionButtons a_demo_search a_demo_save" style="color: #FFFFFF; margin-left: 5px;"
						onclick="addLabInteractionNotesOrAlternativeContact();"> Save </a> <a class="actionButtons a_demo_search a_demo_cancel"
						style="color: #FFFFFF; margin-left: 5px;"
						onclick="closeStdDialog(this);"> Cancel </a>
				</div>
			</div>
			
</form>
<div class="clearBoth"></div>
<div id="currentSelectedInteractionNotes">   </div>

<script type="text/javascript">
	var communicationTypes = ${communicationTypes};
</script>
