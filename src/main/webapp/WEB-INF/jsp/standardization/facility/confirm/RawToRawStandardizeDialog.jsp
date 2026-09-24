<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div id="standardizeDialog" title="Standardize Confirmation">
	<p id="standardiseMsg"><span style="float: left; margin: 0 7px 20px 0;"></span><strong>Are you sure you want to Match?</strong></p>
 	<div class="standardizationDetailsDiv" id="standardizationInfoDiv">
		<div class="leftDiv" title="Raw Facility">
		 <div class='dataDivInConfirmationWindow'>
		 <p> <strong> Raw Facility Details </strong> </p>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.facilityName" /></span>
				</div>
				<div class="floatLeft width170">
				    <span  id="newRawFacilityName" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.department" /></span>
				</div>
				<div class="floatLeft width170">
				    <span  id="newRawfacilityDepartment" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.streetAddress" /></span>
				</div>
				<div class="floatLeft width170">
				    <span  id="newRawStreetAddress" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.city" /></span>
				</div>
				<div class="floatLeft width170">
				    <span  id="newRawCity" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.state" /></span>
				</div>
				<div class="floatLeft width170"> 
					 <span  id="newRawState" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.zip" /></span>
				</div>
				<div class="floatLeft width170">
					<span  id="newRawZip" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.phoneNumber" /></span>
				</div>
				<div class="floatLeft width170">
				    <span  id="newRawPhoneNumber" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">&nbsp;</div>
			<div class="standardInputDiv">&nbsp;</div>
		 </div>
	
		</div>
		
		<!--Middle Div-->
		<div style="float: left;">
			<div class="standardInputDiv">&nbsp;</div>
			<div class="standardInputDiv">&nbsp;</div>
			<div class="standardInputDiv">&nbsp;</div>
			<div class="standardInputDiv">TO</div>
		</div>
		
	    
		<div class="rightDiv" title="Standardization Facility">
		  <div class='dataDivInConfirmationWindow'>
		   <p> <strong> Standard Facility Details </strong> </p>
			<div id="standardFacilityConfirmDetailsDiv">
				<div class="standardInputDiv">
					<div class="floatLeft width150">
						<span class="standardInputTitle"><spring:message code="label.facilityName" /></span>
					</div>
					<div class="floatLeft width170">
						<span  id="existingRawFacilityName" class="standardInputTitle"></span>
					</div>
				</div>
				<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.department" /></span>
				</div>
				<div class="floatLeft width170">
				    <span  id="existingRawfacilityDepartment" class="standardInputTitle"></span>
				</div>
			</div>
			
				<div class="standardInputDiv">
					<div class="floatLeft width150">
						<span class="standardInputTitle"><spring:message code="label.streetAddress" /></span>
					</div>
					<div class="floatLeft width170">
						<span  id="existingRawStreetAddress" class="standardInputTitle"></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="floatLeft width150">
						<span class="standardInputTitle"><spring:message code="label.city" /></span>
					</div>
					<div class="floatLeft width170">
						<span  id="existingRawCity" class="standardInputTitle"></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="floatLeft width150">
						<span class="standardInputTitle"><spring:message code="label.state" /></span>
					</div>
					<div class="floatLeft width170"> 
						 <span  id="existingRawState" class="standardInputTitle"></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="floatLeft width150">
						<span class="standardInputTitle"><spring:message code="label.zip" /></span>
					</div>
					<div class="floatLeft width170">
						<span  id="existingRawZip" class="standardInputTitle"></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="floatLeft width150">
						<span class="standardInputTitle"><spring:message code="label.phoneNumber" /></span>
					</div>
					<div class="floatLeft width170">
						<span  id="existingRawPhoneNumber" class="standardInputTitle"></span>
					</div>
				</div>
			</div>
		  </div>	
       </div>	
		<div style="clear: both;">
			<br />
		</div>
        <div class="button-row">
			<a id ="linkStdButton" class="actionButtons a_demo_search a_demo_continue" style ="color:#FFFFFF; margin-left: 5px;" onclick="matchResults();"> Match </a>
			<a class="actionButtons a_demo_search a_demo_cancel" style ="color:#FFFFFF; margin-left: 5px;" onclick="closeStdDialog(this);"> Cancel </a>
		</div>
		<div>&nbsp;&nbsp;</div>

	</div>
</div>
