<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="accordion" style="width: 85%; clear: both;">
	<div>
	   
			<form id="searchForm" name="searchForm">
				<div class="personDetailsDiv" id="personNameInfoDiv" style="dispaly:none">
					<div>&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->
					
					<div id="searchByPerson">
						<div class="fLW40" id="leftSideCriteria">
							<div class="standardInputDiv">
								<div class="fLW35">
									<label class="standardInputTitle">LABID</label>
								</div>
								<div class="fRW45">
									<input id="labId_Search" name="labId_Search" title="labId" class="standardInputMedText mR50" value="" type="text" >
								</div>
							</div>
							<div class="standardInputDiv">
								<div class="fLW35">
									<label class="standardInputTitle"><spring:message code="label.firstname" /></label>
								</div>
								<div class="fRW45">
									<input id="firstName" title="First Name" name="firstName"  class="standardInputMedText mR50" value="" type="text">
								</div>
							</div>
							<div class="standardInputDiv">
								<div class="fLW35">
									<label class="standardInputTitle"><spring:message code="label.lastname" /></label>
								</div>
								<div class="fRW45">
									<input id="lastName" name="lastName" title="Last Name" class="standardInputMedText mR50" value="" type="text">
								</div>							
							</div>
							<div class="standardInputDiv">
								<div class="fLW35">
									<label class="standardInputTitle"><spring:message code="label.birthdate" /></label>
								</div>
								<div class="fRW45" id="masterDobDate">
									
									<input id="dobMonth" class="floatLeft mR5 width30"  placeholder="MM"/>
									<span class="floatLeft  mR5">/</span>
									<input id="dobDay" class="floatLeft mR5 width30" placeholder="DD"/>
									<span class="floatLeft  mR5">/</span>
									<input id="dateOfBirth" name="dateOfBirth" placeholder="YYYY" class="standardInputmallText width40" type="text" maxlength="4">
								</div>
							</div>
							
							
							
							
						</div>
						<div class="fLW10">
							<div class="standardInputDiv">
							  </div>
							  <div class="standardInputDiv">
									<div class="swapArrow" title="Swap Names" onclick="swapNames();"></div>
								</div>
						</div>
						<div class="fLW40" id="rightSideCriteria">
							<div class="standardInputDiv">
								<div class="fLW35">
									<label class="standardInputTitle">LABNO</label>
								</div>
								<div class="fRW45">
									<input id="labNo" name="labNo" class="standardInputMedText mR50" value="" type="text">
								</div>
							</div>
							<div class="standardInputDiv">
								<div class="fLW35">
									<label class="standardInputTitle">Med Record Number</label>
								</div>
								<div class="fRW45">
									<input id="medRecordNumber" name="medRecordNumber" class="standardInputMedText mR50" title="Med Record Number" value="" type="text">
								</div>
							</div>
							
						
							
						</div>
						
						<div class="fLW40" id="rightSideCriteria">
							<div class="standardInputDiv">
								<div class="fLW35">
									<label class="standardInputTitle">PFI</label>
								</div>
								<div class="fRW45">
									<input id="pfi_search" name="pfi_Search" class="standardInputMedText mR50" value="" type="text">
								</div>
							</div>
							<div class="standardInputDiv">
								<div class="fLW35">
									<label class="standardInputTitle">Gender</label>
								</div>
								<div class="fRW45">
									<input id="gender_search" name="gender_search"  title="Current Gender" value="1" type="radio">  Male 
									<input id="gender_search" name="gender_search"  title="Current Gender" class="mL10" value="2" type="radio">  Female <br/>
									<input id="gender_search" name="gender_search"  title="Current Gender" value="3" type="radio">  Intersex
									<input id="gender_search" name="gender_search"  title="Current Gender" value="4" type="radio">  Unknown
								</div>
							</div>
							
						
							
						</div>
						<div class="fLW40 clearBoth mT10" id="rightSideCriteria">
							<div class="standardInputDiv">
								<div class="fLW35">
									<label class="standardInputTitle">Weight</label>
								</div>
								<div class="fRW45">
									<input id="weight_search" name="weight_Search" class="standardInputMedText mR50" value="" type="text">
								</div>
							</div>
							
							
						
							
						</div>
					</div>
					
					<div class="clearBoth" ></div>
					<div style="margin-top:20px">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->
					
					<div class="button-row">
						<span>
							<a class="a_demo_search" style="color: #FFFFFF; margin-left: 5px;" onclick="javascript:dispSearchResults();" id="searchAssignmentFormResultsButton"> Search </a>
						</span>
						<span style="margin-left: 25px">
							<a class="actionButtons a_demo_search a_demo_reset" style="color: #FFFFFF; margin-left: 5px;" onclick="javascript:clearAllSearchFields();" id="clearAllSearchFields"> Clear All </a>
						</span>
						
					</div>
					<br />
					
				
				</div>			
				
			</form>
	</div>
</div>