<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

 	
<div id="labDocTab" style="background-color: transparent">
		<div style="padding-left:43%; " class="headingCls">
			Document ${docDelDiscardHistory.DEL_DISCARD} History	
		</div>
		
		<table class="mT20  bordered"  align="center" > 
				<thead style="font-size:12px;">
					<tr>
						<th width='14%'>${docDelDiscardHistory.DEL_DISCARD} By</th>
						<th width='10%'>${docDelDiscardHistory.DEL_DISCARD} Date</th>
						<th width='28%'>Reason</th>
						<th>Comments</th>
					</tr>
				</thead>
				<tbody>
						<tr>
							<td>${docDelDiscardHistory.CREATE_ID}</td>
							<td>${docDelDiscardHistory.CREATE_DATE}</td>
							<td>${docDelDiscardHistory.REASON_DESC}</td>
							<td>${docDelDiscardHistory.COMMENTS}</td>
						</tr>	
				</tbody>
		</table>
		    		
</div>																	 										

	
