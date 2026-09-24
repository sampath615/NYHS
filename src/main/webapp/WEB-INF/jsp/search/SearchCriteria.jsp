<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="accordion" style="width: 85%; clear: both;">
	<h3>Search</h3>
	<div>
	        <div style="height: 35px;">
						<div id="searchByPersonOrDocument" class="searchPersonTxt"> 
						    Search by: &nbsp;
                                <input  id="personSelect" type="radio" name="searchby" value="person" title="Search by Person" checked><span class="searchSelect">By PERSON</span>
                                <input  id="documentSelect" type="radio" name="searchby" value="cityNo" title="search by Cityno"><span class="searchSelect">By CITYNO</span> 
<!--                                 <input  id="documentSelect" type="radio" name="searchby" value="document" disabled><span class="searchSelect">By Document</span> -->
                        </div>	
			</div>
			<form id="searchForm" name="searchForm">
				<div class="personDetailsDiv" id="personNameInfoDiv" style="dispaly:none">
					<div>&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->
					
					<div id="searchByPerson">
						<div class="fLW40" id="leftSideCriteria">
							<div class="standardInputDiv">
								<div class="fLW35">
									<label class="standardInputTitle"><spring:message code="label.stateNo" /></label>
								</div>
								<div class="fRW45">
									<input id="stateNo_Search" name="stateNo_Search" title="STATENO" class="standardInputMedText mR50" value="" type="text" >
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
									<label class="standardInputTitle">DIN</label>
								</div>
								<div class="fRW45">
									<input id="din" name="din" class="standardInputMedText mR50" value="" type="text">
								</div>
							</div>
							<div class="standardInputDiv">
								<div class="fLW35">
									<label class="standardInputTitle"><spring:message code="label.ssn" /></label>
								</div>
								<div class="fRW45">
									<input id="ssn" name="ssn" class="standardInputMedText mR50" title="SSN" value="" type="text" maxlength="9">
								</div>
							</div>
							<div class="standardInputDiv">							
								<div class="fLW35">
									<label class="standardInputTitle">NYSID</label>
								</div>
								<div class="fRW45">
									<input id="nysid" name="nysid" class="standardInputMedText mR50" value="" type="text">
								</div>
								
							</div>
						
							<div class="standardInputDiv">
								<div class="fLW35">
									<label class="standardInputTitle"><spring:message code="label.deathdate" /></label>
								</div>
								<div class="fRW45" id="masterDodDate">
									
									<input id="dodMonth" class="floatLeft mR5 width30"  placeholder="MM"/>
									<span class="floatLeft  mR5">/</span>
									<input id="dodDay" class="floatLeft mR5 width30" placeholder="DD"/>
									<span class="floatLeft  mR5">/</span>
									
									<input id="dateOfDeath" name="dateOfDeath" placeholder="YYYY"  class="width40 standardInputmallText" type="text" maxlength="4">
								</div>
							</div>
						</div>
					</div>
					<div id="searchByCityno" class="displayNone">
						<div class="leftDiv">
							<div class="standardInputDiv">
								<div class="floatLeft">
									<label class="standardInputTitle">CITYNO</label>
								</div>
								<div class="floatRight">
									<input id="cityNo_Search" name="cityNo_Search" title="Cityno" class="standardInputMedText mR50 required" value="" type="text" maxlength="10">
								</div>
							</div>
						</div>
					
					</div>
					<div class="clearBoth" ></div>
					<div style="margin-top:20px">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->
					<!-- 
					<div>
						<input type="button"  class="searchButton" value="     Search     " align="center"/>
					</div> 
					-->
					<div class="button-row">
						<span>
							<a class="a_demo_search" style="color: #FFFFFF; margin-left: 5px;" onclick="javascript:dispSearchResults();" id="searchAssignmentFormResultsButton"> Search </a>
						</span>
						<span style="margin-left: 25px">
							<a class="actionButtons a_demo_search a_demo_reset" style="color: #FFFFFF; margin-left: 5px;" onclick="javascript:clearAllSearchFields();" id="clearAllSearchFields"> Clear All </a>
						</span>
						<span style="margin-left: 25px">
							<a class="a_demo_search" style="color: #FFFFFF; margin-left: 5px;" onclick="javascript:quickSearch();" id="quickSearch"> Recent Demo View Search</a>
						</span>
					</div>
					<br />
					<br />					
					<div>
						<div class="floatLeft">
							<label class="redNote">Note:</label>
						</div>
						<div class="floatLeft ">
							<div><span class="bold">Recent Demo View Search is for the person's exact information search, no wild card search allowed.</span></div>
							
							<div class="mT5"><span class="bold ">Documents will be shown based on the permissions.</span></div>
						</div>
					</div>
					
				
				</div>			
				
			</form>
	</div>
</div>