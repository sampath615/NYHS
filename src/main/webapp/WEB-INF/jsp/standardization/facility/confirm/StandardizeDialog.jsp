<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div id="standardizeDialog" title="Standardize Confirmation">
	<p id="standardiseMsg"><span style="float: left; margin: 0 7px 20px 0;"></span><strong>Are you sure you want to Standardize?</strong></p>
 	<div class="standardizationDetailsDiv" id="standardizationInfoDiv">
		<div class="leftDiv" title="Raw Facility">
		 <div class='dataDivInConfirmationWindow' id="rawFacilityDiv">
	 	    <p><strong>Raw Facility Address Details</strong></p>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.facilityName" /></span>
				</div>
				<div class="floatLeft width170">
				    <span  id="rawfacilityName" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.department" /></span>
				</div>
				<div class="floatLeft width170">
				    <span  id="rawfacilityDepartment" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.streetAddress" /></span>
				</div>
				<div class="floatLeft width170">
				    <span  id="rawstreetAddress" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.city" /></span>
				</div>
				<div class="floatLeft width170">
				    <span  id="rawcity" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.state" /></span>
				</div>
				<div class="floatLeft width170"> 
					 <span  id="rawstate" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.zip" /></span>
				</div>
				<div class="floatLeft width170">
					<span  id="rawzip" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.phoneNumber" /></span>
				</div>
				<div class="floatLeft width170">
				    <span  id="rawphoneNumber" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle">NPI</span>
				</div>
				<div class="floatLeft width170">
					<span  id="rawNpi" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">&nbsp;</div>
			<div class="standardInputDiv">&nbsp;</div>
		 </div>
		 
		 
		 <div class='dataDivInConfirmationWindow' id="orphanFacilityDiv">
		    <p> <strong> Sub Facility Details </strong> </p>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.facilityName" /></span>
				</div>
				<div class="floatLeft width170">
				    <span  id="orphanFacilityNameInConfirmWindow" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.department" /></span>
				</div>
				<div class="floatLeft width170">
				    <span  id="orphanFacilityDepartmentInConfirmWindow" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.streetAddress" /></span>
				</div>
				<div class="floatLeft width170">
				    <span  id="orphanFacilityAddressInConfirmWindow" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.city" /></span>
				</div>
				<div class="floatLeft width170">
				    <span  id="orphanFacilityCityInConfirmWindow" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.state" /></span>
				</div>
				<div class="floatLeft width170">
				    <span  id="orphanFacilityStateInConfirmWindow" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.zip" /></span>
				</div>
				<div class="floatLeft width170">
				    <span  id="orphanFacilityZipcodeInConfirmWindow" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle"><spring:message code="label.phoneNumber" /></span>
				</div>
				<div class="floatLeft width170">
				    <span  id="orphanFacilityPhoneInConfirmWindow" class="standardInputTitle"></span>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="floatLeft width150">
					<span class="standardInputTitle">NPI</span>
				</div>
				<div class="floatLeft width170">
					<span  id="orphanFacilityNpi" class="standardInputTitle"></span>
				</div>
			</div>
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
		     <p><strong>Facility Details</strong></p>
			<div id="masterFacilityConfirmDetailsDiv">
				<div class="standardInputDiv">
					<div class="floatLeft width150">
						<span class="standardInputTitle"><spring:message code="label.masterFacilityName" /></span>
					</div>
					<div class="floatLeft width170">
						<span  id="masterFacilityNameInConfirmWindow" class="standardInputTitle"></span>
					</div>
				</div>
	
			</div>
			<div id="standardFacilityConfirmDetailsDiv">
			
				<div class="standardInputDiv">
					<div class="floatLeft width150">
						<span class="standardInputTitle"><spring:message code="label.facilityName" /></span>
					</div>
					<div class="floatLeft width170">
						<span  id="stdfacilityName" class="standardInputTitle"></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="floatLeft width150">
						<span class="standardInputTitle"><spring:message code="label.department" /></span>
					</div>
					<div class="floatLeft width170">
						<span  id="stddepartment" class="standardInputTitle"></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="floatLeft width150">
						<span class="standardInputTitle"><spring:message code="label.streetAddress" /></span>
					</div>
					<div class="floatLeft width170">
						<span  id="stdstreetAddress" class="standardInputTitle"></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="floatLeft width150">
						<span class="standardInputTitle"><spring:message code="label.city" /></span>
					</div>
					<div class="floatLeft width170">
						<span  id="stdcity" class="standardInputTitle"></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="floatLeft width150">
						<span class="standardInputTitle"><spring:message code="label.state" /></span>
					</div>
					<div class="floatLeft width170"> 
						 <span  id="stdstate" class="standardInputTitle"></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="floatLeft width150">
						<span class="standardInputTitle"><spring:message code="label.zip" /></span>
					</div>
					<div class="floatLeft width170">
						<span  id="stdzip" class="standardInputTitle"></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="floatLeft width150">
						<span class="standardInputTitle"><spring:message code="label.county" /></span>
					</div>
					<div class="floatLeft width170">
						<span  id="stdcounty" class="standardInputTitle"></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="floatLeft width150">
						<span class="standardInputTitle"><spring:message code="label.phoneNumber" /></span>
					</div>
					<div class="floatLeft width170">
						<span  id="stdphoneNumber" class="standardInputTitle"></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="floatLeft width150">
						<span class="standardInputTitle">NPI</span>
					</div>
					<div class="floatLeft width170">
						<span  id="stdNpi" class="standardInputTitle"></span>
					</div>
				</div>
			</div>
		  </div>	
       </div>	
		<div style="clear: both;">
			<br />
		</div>
        <div class="button-row">
			<a id ="linkStdButton" class="actionButtons a_demo_search a_demo_add" style ="color:#FFFFFF; margin-left: 5px;" onclick="SubmitStandardizeResults();"> Link </a>
			<a id ="addLinkStdButton" class="actionButtons a_demo_search a_demo_add" style ="color:#FFFFFF; margin-left: 5px;" onclick="SubmitNewStandardizeResults();"> Add and Link </a>
			<a onclick="showMasterFacilitySelectionPage();" style="color: rgb(255, 255, 255); margin-left: 5px;" class="a_demo_search" id="selectMasterFacilityButton"> Select Facility </a>
			<a class="actionButtons a_demo_search a_demo_cancel" style ="color:#FFFFFF; margin-left: 5px;" onclick="closeStdDialog(this);"> Cancel </a>
		</div>
		<div>&nbsp;&nbsp;</div>

	</div>
</div>
