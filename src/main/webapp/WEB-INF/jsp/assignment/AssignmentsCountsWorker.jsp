<table style="width: 320px;">
	<tr>
		<td> All Open Assignments </td> 
		<td class="jslink" style="width: 7%;"> <a onclick="openAssignment('OPEN', this);" title="All Open Assignments"> (<span>${tallyCounts.allOpenCount}</span>) </a> </td> 
	</tr>
	<tr>
		<td style="padding-left: 10px;"> &raquo; Assignments with Saved ACRF </td> 
		<td class="jslink"> <a onclick="openAssignment('NSA', this);" title="Assignments with Saved ACRF"> (<span>${tallyCounts.nonSubmittedAcrf}</span>) </a> </td> 
	</tr>
	<tr>
		<td> All ReOpen Assignments </td> 
		<td class="jslink"> <a onclick="openAssignment('REOPEN', this);" title="All ReOpen Assignments"> (<span>${tallyCounts.allReopenCount}</span>) </a> </td> 
	</tr>
	<tr>	
		<td> All Rejected Assignments </td> 
		<td class="jslink"><a onclick="openAssignment('REJECT', this);" title="All Rejected Assignments"> (<span>${tallyCounts.allRejectedCount}</span>)</a>  </td>
	</tr>
	<tr>	
		<td> Pending Re-assignment </td> 
		<td class="jslink"><a onclick="openAssignment('REASSGN', this);" title="Pending Re-assignment">(<span>${tallyCounts.pendingReassignCount}</span>)</a> </td>
	</tr>
	<tr>	
		<td> Pending ACRF Review </td> 
		<td class="jslink"><a onclick="openAssignment('SUB_ACRF', this);" title="Pending ACRF Review">(<span>${tallyCounts.pendingAcrfRvwCount}</span>)</a> </td>
	</tr>
	<!-- 'NEW' is nothing but 'OPEN' with 'from Date' as 'Last Login Date' -->
	<%-- <tr>	
		<td> New Assignments Since Last Login   </td> 
		<td class="jslink"><a onclick="openAssignment('NEW', this);" title="New Assignments Since Last Login"> (<span>${tallyCounts.newCount}</span>)</a>   </td> 		
	</tr> --%>
	<tr>	
		<td> Administratively Closed (last 30 days)  </td> 
		<td class="jslink"><a onclick="openAssignment('CLS_ADM', this);" title="Administratively Closed"> (<span>${tallyCounts.adminClosedCount}</span>)</a>  </td> 		
	</tr>
	
</table>
								