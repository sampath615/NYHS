<table style="width: 340px;">
	<tr>	
		<td> Needs Assignment  </td> 
		<td class="jslink"><a onclick="openAssignment('OPEN_ND', this);" title="Needs Assignment"> (<span>${tallyCounts.needsAssignTotal}</span>)</a>   </td> 		
	</tr>
	<tr>
		<td> All Open Assignments </td> 
		<td class="jslink" style="width: 7%;"> <a onclick="openAssignment('ALLOPEN', this);" title="All Open Assignments"> (<span>${tallyCounts.allOpenTotal}</span>) </a> </td> 
	</tr>
	<tr>
		<td style="padding-left: 10px;"> &raquo; My Open Assignments </td> 
		<td class="jslink"> <a onclick="openAssignment('OPEN', this);" title="My Open Assignments"> (<span>${tallyCounts.myOpenTotal}</span>) </a> </td> 
	</tr>
	<tr>
		<td style="padding-left: 15px;"> &raquo; My Assignments with Saved ACRF </td> 
		<td class="jslink"> <a onclick="openAssignment('MY_NSA', this);" title="My Assignments with Saved ACRF"> (<span>${tallyCounts.myNonSubmittedAcrf}</span>) </a> </td> 
	</tr>
	<tr>	
		<td> All Rejected Assignments </td> 
		<td class="jslink"><a onclick="openAssignment('REJECT', this);" title="All Rejected Assignments"> (<span>${tallyCounts.allRejectedTotal}</span>)</a>  </td>
	</tr>
	<tr>	
		<td> Pending Re-assignment</td> 
		<td class="jslink"><a onclick="openAssignment('REASSGN', this);" title="Pending Re-assignment">(<span>${tallyCounts.reassignRequestedTotal}</span>)</a> </td>
	</tr>
	<tr>	
		<td> All Pending ACRF Review </td> 
		<td class="jslink"><a onclick="openAssignment('ALLSUB_ACRF', this);" title="All Pending ACRF Review">(<span>${tallyCounts.pendingAcrfRvwTotal}</span>)</a> </td>
	</tr>
	<tr>
		<td style="padding-left: 10px;"> &raquo; My Pending ACRF Review </td> 
		<td class="jslink"> <a onclick="openAssignment('SUB_ACRF', this);" title="My Pending ACRF Review"> (<span>${tallyCounts.myPendingAcrfRvw}</span>) </a> </td> 
	</tr>
	<tr>	
		<td> Administratively Closed (last 30 days)  </td> 
		<td class="jslink"><a onclick="openAssignment('ALLCLS_ADM', this);" title="Administratively Closed (last 30 days)"> (<span>${tallyCounts.adminClosedTotal}</span>)</a>  </td> 		
	</tr>
	<tr>
		<td style="padding-left: 10px;"> &raquo; My Administratively Closed (last 30 days)</td> 
		<td class="jslink"> <a onclick="openAssignment('CLS_ADM', this);" title="My Administratively Closed (last 30 days)"> (<span>${tallyCounts.myAdminClsd}</span>) </a> </td> 
	</tr>
	
</table>