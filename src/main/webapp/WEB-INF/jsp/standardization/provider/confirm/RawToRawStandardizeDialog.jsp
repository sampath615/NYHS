<div id="standardizeDialog" class="dialogWindow" title="Standardize Confirmation">
	<p id="standardiseMsg"><span style="float: left; margin: 0 7px 20px 0;"></span><strong>Are you sure you want to Standardize?</strong></p>
 	<div class="standardizationDetailsDiv" id="standardizationInfoDiv">
		<div class="leftDiv" title="Raw Facility">
		 <div class='dataDivInConfirmationWindow' id='rawProviderAddressDiv'>
		    <p><strong>Raw Provider Address Details</strong></p>
			<div class="standardInputDiv">
				<div class="confirmDialogLeftLabel">
					<font class="standardInputTitle">First Name</font>
				</div>
				<div class="confirmDialogRightLabel">
				    <font  id="rawFirstName" class="standardInputTitle "></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="confirmDialogLeftLabel">
					<font class="standardInputTitle">Middle Name</font>
				</div>
				<div class="confirmDialogRightLabel">
				    <font  id="rawMiddleName" class="standardInputTitle "></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="confirmDialogLeftLabel">
					<font class="standardInputTitle">Last Name</font>
				</div>
				<div class="confirmDialogRightLabel">
				    <font  id="rawLastName" class="standardInputTitle "></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="confirmDialogLeftLabel">
					<font class="standardInputTitle">Street Address</font>
				</div>
				<div class="confirmDialogRightLabel">
				    <font  id="rawStreetAddress" class="standardInputTitle "></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="confirmDialogLeftLabel">
					<font class="standardInputTitle">City</font>
				</div>
				<div class="confirmDialogRightLabel">
				    <font  id="rawCity" class="standardInputTitle "></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="confirmDialogLeftLabel">
					<font class="standardInputTitle">State</font>
				</div>
				<div class="confirmDialogRightLabel"> 
					 <font  id="rawState" class="standardInputTitle "></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="confirmDialogLeftLabel">
					<font class="standardInputTitle">Zip</font>
				</div>
				<div class="confirmDialogRightLabel">
					<font  id="rawZip" class="standardInputTitle "></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="confirmDialogLeftLabel">
					<font class="standardInputTitle">County</font>
				</div>
				<div class="confirmDialogRightLabel">
				    <font  id="rawCounty" class="standardInputTitle "></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="confirmDialogLeftLabel">
					<font class="standardInputTitle">Phone Number</font>
				</div>
				<div class="confirmDialogRightLabel">
				    <font  id="rawPhoneNumber" class="standardInputTitle "></font>
				</div>
			</div>
		 </div>
		</div>
		
		<!--Middle Div-->
		<div style="float: left;">
			<div class="standardInputDiv">&nbsp;</div>
			<div class="standardInputDiv">&nbsp;</div>
			<div class="standardInputDiv">&nbsp;</div>
			<div class="standardInputDiv">&nbsp;</div>
			<div class="standardInputDiv">&nbsp;</div>
			<div class="standardInputDiv">TO</div>
		</div>
		
	    
		<div class="rightDiv" title="Standardization Facility">
		  <div class='dataDivInConfirmationWindow'>
		    <p><strong>Provider Address Details</strong></p>
		    <div class="standardInputDiv">
				<div class="confirmDialogLeftLabel">
					<font class="standardInputTitle">First Name</font>
				</div>
				<div class="confirmDialogRightLabel">
				    <font  id="existingRawFirstName" class="standardInputTitle "></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="confirmDialogLeftLabel">
					<font class="standardInputTitle">Middle Name</font>
				</div>
				<div class="confirmDialogRightLabel">
				    <font  id="existingRawMiddleName" class="standardInputTitle "></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="confirmDialogLeftLabel">
					<font class="standardInputTitle">Last Name</font>
				</div>
				<div class="confirmDialogRightLabel">
				    <font  id="existingRawLastName" class="standardInputTitle "></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="confirmDialogLeftLabel">
					<font class="standardInputTitle">Street Address</font>
				</div>
				<div class="confirmDialogRightLabel">
				    <font  id="existingRawStreetAddress" class="standardInputTitle "></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="confirmDialogLeftLabel">
					<font class="standardInputTitle">City</font>
				</div>
				<div class="confirmDialogRightLabel">
				    <font  id="existingRawCity" class="standardInputTitle "></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="confirmDialogLeftLabel">
					<font class="standardInputTitle">State</font>
				</div>
				<div class="confirmDialogRightLabel"> 
					 <font  id="existingRawState" class="standardInputTitle "></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="confirmDialogLeftLabel">
					<font class="standardInputTitle">Zip</font>
				</div>
				<div class="confirmDialogRightLabel">
					<font  id="existingRawZip" class="standardInputTitle "></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="confirmDialogLeftLabel">
					<font class="standardInputTitle">County</font>
				</div>
				<div class="confirmDialogRightLabel">
				    <font  id="existingRawCounty" class="standardInputTitle "></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div class="confirmDialogLeftLabel">
					<font class="standardInputTitle">Phone Number</font>
				</div>
				<div class="confirmDialogRightLabel">
				    <font  id="existingRawPhoneNumber" class="standardInputTitle "></font>
				</div>
			</div>
		  </div>	
       </div>	
		<div style="clear: both;">
			<br />
		</div>
        <div class="button-row" style="text-align: center;">
			<a id ="linkStdButton" class="actionButtons a_demo_search a_demo_add" style ="color:#FFFFFF; margin-left: 5px;" onclick="matchResults();"> Link </a>
			<a class="actionButtons a_demo_search a_demo_cancel" style ="color:#FFFFFF; margin-left: 5px;" onclick="closeStdDialog(this);"> Cancel </a>
		</div>
		<div>&nbsp;&nbsp;</div>
	</div>
</div>






