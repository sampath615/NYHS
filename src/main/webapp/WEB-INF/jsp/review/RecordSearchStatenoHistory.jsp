<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

 	
<div id="labDocTab" style="background-color: transparent">
		<div style="padding-left:43%; " class="headingCls">
			Record Search History	
		</div>
		
		
		    
		<table class="mT20  bordered"  align="center" > 
				<thead style="font-size:12px;">
					<tr>
<!-- 						<th class="displayNone floatLeft"></th> -->
						<th >Flag Date</th>
						<th >Comments</th>
						<th>CDC File Generated Date</th>
						<th>Unflag Date</th>
						<th>Unflag Comments</th>
						<th class="width30Perc" ># of rows created for this stateno and sent to CDC</th>
						<th>Matches to which states</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${recordSearchStatenoHistory}" var="rec_stateno">
						<tr>
							<td>${rec_stateno.CREATE_DATE}</td>
							<td>${rec_stateno.COMMENTS}</td>
							<td>${rec_stateno.GENERATE_DATE}</td>
							<td>${rec_stateno.UNFLAG_DATE}</td>
							<td>${rec_stateno.UNFLAG_COMMENTS}</td>
<%-- 							<td>${rec_stateno.SENT_FILE_NAME}</td> --%>
							<td>${rec_stateno.SENT_COUNT}</td>
							<td>${rec_stateno.STATE_CD}</td>
						</tr>	
					</c:forEach>
				</tbody>
		</table>
		    		
</div>																	 										

	
