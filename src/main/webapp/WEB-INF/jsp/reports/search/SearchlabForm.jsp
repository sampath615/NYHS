<div id="accordion" style="clear: both; width:90%; margin:0px auto">
	<h3>Search </h3>
	<form:form id="searchForm" name="searchForm" modelAttribute="labFacilitiesVO">
		<div>&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->
    	<div class="personDetailsDiv" id="personNameInfoDiv" style="dispaly:none">
			<div class="leftDiv">
				<div class="standardInputDiv">
					<div class="fLW33">
						<label class="standardInputTitle"><spring:message code="label.CLIA" /></label>
					</div>
					<div class="personInput">
						<input id="searchClia" name="searchClia" tabindex="1" class="standardInputMedText mR50" value="" type="text" maxLength="50" title="<spring:message code='title.CLIA' />"> 
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="fLW33">
						<label class="standardInputTitle"><spring:message code="label.labName" /></label>
					</div>
					<div class="personInput">
						<input id="searchLabName" name="searchLabName" class="standardInputMedText mR50" value="" type="text" maxLength="100" tabindex="3" title="<spring:message code='title.labName' />">
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="fLW33">
						<label class="standardInputTitle"><spring:message code="label.city" /></label>
					</div>
					<div class="personInput">
						<input id="searchCity" name="searchCity"  class="standardInputMedText mR50" type="text" maxLength="80" tabindex="5" title="<spring:message code='title.city' />">
					</div>
				</div>
				
				<div class="standardInputDiv">
					<div class="fLW33">
						<label class="standardInputTitle"><spring:message code="label.zip" /></label>
					</div>
					<div class="personInput">
						<input id="searchZip" name="searchZip" class="standardInputMedText mR50" type="text" maxLength="10" tabindex="7" title="<spring:message code='title.zip' />">
					</div>
				</div>
				
			</div>
			<div class="rightDiv">
				<div class="standardInputDiv">
					<div class="fLW33">
						<label class="standardInputTitle"><spring:message code="label.PFI" /></label>
					</div>
					<div class="personInput">
						<input id="searchPfi" name="searchPfi"  class="standardInputMedText mR50" value="" type="text" maxLength="6" tabindex="2" title="<spring:message code='title.PFI' />">
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="fLW33">
						<label class="standardInputTitle"><spring:message code="label.streetAddress" /></label>
					</div>
					<div class="personInput">
						<input id="searchAddress" name="searchAddress"  class="standardInputMedText mR50" value="" type="text" maxLength="200" tabindex="4"  title="<spring:message code='title.streetAddress' />">
					</div>
				</div>
				
				<div class="standardInputDiv">
					<div class="fLW33">
						<label class="standardInputTitle"><spring:message code="label.state" /></label>
					</div>
					<div class="personInput">
						<spring:message code="title.state" var="state_title"/>
						<form:select id="searchState"  path ="state" class="standardInputMedText mR50"  title="${state_title}" tabindex="6">
					        <form:option value="" label="--- Select ---" />
							<form:options items="${sessionScope.stateMap}"/>
				    	</form:select>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="fLW33">
						<label class="standardInputTitle"><spring:message code="label.country" /></label>
					</div>
					<div class="personInput">
						<input id="searchCountry" name="searchCountry"  class="standardInputMedText mR50" type="text" maxLength="100" tabindex="8" title="<spring:message code='title.country' />">
					</div>
				</div>
			
			
			</div>

			<div class="clearBoth"></div>
			<div id="searchButton" class="button-row alignCenter">
				<a class="a_demo_search" style="color: #FFFFFF; margin-left: 5px;"
					onclick="dispSearchResults('manual');"
					id="searchAssignmentFormResultsButton" tabindex="9" > Search </a>
			</div>
			<div>
				<label class="redNote">Note:</label><label>A blank search will result in all possible search results.</label>
			</div>
		</div>			
		
	</form:form>
</div>