<div id="orphanAddressToProvDialog" class="dialogWindow" title="Standardize Confirmation">
	<p id="standardiseMsg"><span style="float: left; margin: 0 7px 20px 0;"></span><strong>Are you sure you want to Standardize?</strong></p>
 	<div class="standardizationDetailsDiv" id="standardizationInfoDiv">
		<div class="leftDiv" title="Raw Facility">
			 <div class='dataDivInConfirmationWindow'>
				<p><strong>Provider Address Details</strong></p>
				<div class="standardInputDiv">
					<div class="confirmDialogLeftLabel">
						<span class="standardInputTitle">Address</span>
					</div>
					<div class="confirmDialogRightLabel">
						<span  id="providerAddress" class="standardInputTitle "></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="confirmDialogLeftLabel">
						<span class="standardInputTitle">City</span>
					</div>
					<div class="confirmDialogRightLabel">
						<span  id="providerCity" class="standardInputTitle "></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="confirmDialogLeftLabel">
						<span class="standardInputTitle">State</span>
					</div>
					<div class="confirmDialogRightLabel">
						<span  id="providerState" class="standardInputTitle "></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="confirmDialogLeftLabel">
						<span class="standardInputTitle">Zip</span>
					</div>
					<div class="confirmDialogRightLabel">
						<span  id="providerZip" class="standardInputTitle "></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="confirmDialogLeftLabel">
						<span class="standardInputTitle">Phone Number</span>
					</div>
					<div class="confirmDialogRightLabel">
						<span  id="providerPhoneNumber" class="standardInputTitle "></span>
					</div>
				</div>
			</div>
		</div>
		
		<!--Middle Div-->
		<div style="float: left;">
			<div class="standardInputDiv">&nbsp;</div>
			<div class="standardInputDiv">&nbsp;</div>
			<div class="standardInputDiv">TO</div>
		</div>
		
	    
		<div class="rightDiv" title="Standardization Facility">
			<div class='dataDivInConfirmationWindow'>
				<p><strong>Provider Details</strong></p>
				<div class="standardInputDiv">
					<div class="confirmDialogLeftLabel">
						<span class="standardInputTitle">First Name</span>
					</div>
					<div class="confirmDialogRightLabel">
						<span  id="providerFirstName" class="standardInputTitle "></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="confirmDialogLeftLabel">
						<span class="standardInputTitle">Middle Name</span>
					</div>
					<div class="confirmDialogRightLabel">
						<span  id="providerMiddleName" class="standardInputTitle "></span>
					</div>
				</div>
				<div class="standardInputDiv">
					<div class="confirmDialogLeftLabel">
						<span class="standardInputTitle">Last Name</span>
					</div>
					<div class="confirmDialogRightLabel">
						<span  id="providerLastName" class="standardInputTitle "></span>
					</div>
				</div>
			</div>	
		</div>	
		<div style="clear: both;">
			<br />
		</div>
        <div class="button-row" style="text-align: center;">
			<a id ="orphanAddressStdLinkButton" class="actionButtons a_demo_search a_demo_add" style ="color:#FFFFFF; margin-left: 5px;" onclick="SubmitProviderAddrStandardizeResults();"> Link </a>
			<a id ="addProviderAndLinkOrphanAddrStdButton" class="actionButtons a_demo_search a_demo_add" style ="color:#FFFFFF; margin-left: 5px;" onclick="SubmitProviderAndOrphanAddressResults();"> Add and Link </a>
			<a class="actionButtons a_demo_search a_demo_cancel" style ="color:#FFFFFF; margin-left: 5px;" onclick="closeStdDialog(this);"> Cancel </a>
		</div>
		<div>&nbsp;&nbsp;</div>

	</div>
</div>
