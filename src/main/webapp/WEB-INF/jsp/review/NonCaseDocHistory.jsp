<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

 	
<div id="labDocTab" style="background-color: transparent">
		<div style="padding-left:43%; " class="headingCls">
			Non case History	
		</div>
		    
		<table class="bordered mT20"  align="center" > 
				<thead style="font-size:12px;">
					<tr>
<!-- 						<th class="displayNone floatLeft"></th> -->
						<th >Current STATENO</th>
						<th >Flagged STATENO</th>
						<th>Non Case Reason</th>
						<th>ROS STATENO</th>
						<th>NYC EHARS STATENO</th>
						<th>CITYNO</th>
						<th>Flagged By</th>
						<th>Flagged Date</th>
						<th>Flag Comments</th>
						<th>Unflag Comments</th>
						<th>Unflag By</th>
						<th>Unflag Date</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${nonCaseDocHistory}" var="non_case_stateno">
						<tr>
							<td>${non_case_stateno.CURRENT_STATENO}</td>
							<td>${non_case_stateno.ACTUAL_STATENO}</td>
							<td>${non_case_stateno.REASON_DESC}</td>
							<td>${non_case_stateno.EHARS_STATENO}</td>
							<td>${non_case_stateno.NYC_STATENO}</td>
							<td>${non_case_stateno.NYC_CITYNO}</td>
							<td>${non_case_stateno.FLAGGED_BY}</td>
							<td>${non_case_stateno.FLAGGED_DATE}</td>
							<td>${non_case_stateno.REQUEST_COMMENTS}</td>
							<td>${non_case_stateno.REVIEW_COMMENTS}</td>
							<td>${non_case_stateno.LAST_UPDATED_BY}</td>
							<td>${non_case_stateno.LAST_UPDATED_DATE}</td>
						</tr>	
					</c:forEach>
				</tbody>
		</table>
		    		
</div>																	 										

	
