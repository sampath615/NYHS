<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="accordion" style="width: 950px; clear: both;">
	<h3>Reports</h3>
	<div>
			<form id="searchForm" name="searchForm">
				
					<div>&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->
					<div class="centerDiv">
						<div class="standardInputDiv">
							<div class="floatLeft">
								<label class="standardInputTitle">Provider Name</label>
							</div>
							<div class="floatRight" style="padding-right:18px">
								<!-- <input id="stateNo_Search" name="stateNo_Search"  class="standardInputMedText mR50" value="" type="text"> -->
								<span class="w15">	
									 <input name="providerName" id="providerName" class="standardInputMedText" ></input>
									 <input name="providerId" id="providerId" type="hidden" class="standardInputMedText" ></input>
								</span>
							</div>
						</div>
						<div class="standardInputDiv">
							<div class="floatLeft">
								<label class="standardInputTitle">From Date</label>
							</div>
							<div class="floatRight inputFieldInDialogWindow effectiveDateTimePicker">
								<input id="mngProCNSTrainingDate"
									   class="dPicker standardInputMedText required"
									   data-format="MM/dd/yyyy" type="text"
						               title="" />
								<span class="add-on">
									<span class="icon-calendar" data-date-icon="icon-calendar" data-time-icon="icon-time">
	      							</span>	
  							 	</span>
							</div>
						</div>
						<div class="standardInputDiv">
							<div class="floatLeft">
								<label class="standardInputTitle">To Date</label>
							</div>
							<div class="floatRight inputFieldInDialogWindow effectiveDateTimePicker">
								<input id="mngProCNSTrainingDate"
									   class="dPicker standardInputMedText required"
									   data-format="MM/dd/yyyy" type="text"
						               title="" />
								<span class="add-on">
									<span class="icon-calendar" data-date-icon="icon-calendar" data-time-icon="icon-time">
	      							</span>	
  							 	</span>
							</div>
						</div>	
					<br>
					<div class="mT150">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->
					<center>
						<div class="button-row">
							<a class="a_demo_search reportGenerateBtnClick" style="color: #FFFFFF; margin-left: 5px;"
								onClick="reportClick();" id="searchReportAssignmentFormResultsButton"> Generate Report </a>
						</div>
					</center>	
					<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->
					</div>						  
						
				
			</form>
	</div>
</div>