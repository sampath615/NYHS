<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

 	
<div id="duplicateSummary" style="background-color: transparent">
		<div style="padding-left:25%; " class="headingCls">
			RIDR REVIEW SUMMARY	
		</div>
		
		
		    
		<table class="mT20  bordered"  align="center" > 
				<thead style="font-size:12px;">
					<tr>
						<th >DUPLICATE STATUS</th>
						<th >STATENO</th>
						<th>STATE CODE</th>
				   </tr>
				</thead>
				<tbody>
					<c:forEach items="${duplicateReviewSummary}" var="summary_Review">
						<tr>
							<td>${summary_Review.DUPLICATE_STATUS}</td>
							<td>${summary_Review.STATENO}</td>
							<td>${summary_Review.STATE_CD}</td>
						</tr>	
					</c:forEach>
				</tbody>
		</table>
		    		
</div>																	 										

	
