<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

 	
<div id="labDocTab" style="background-color: transparent">
		<div style="padding-left:43%; " class="headingCls">
			RIA Doc Activity	
		</div>
		
		
		    
		<table class="mT20  bordered"  align="center" > 
				<thead style="font-size:12px;">
					<tr>
<!-- 						<th class="displayNone floatLeft"></th> -->
						<th >Activity Desc</th>
						<th >Worked By</th>
						<th>Updated Date</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${riaActivity}" var="activity">
						<tr>
							<td>${activity.ACTIVITY_DESC}</td>
							<td>${activity.WORKER_NAME}</td>
							<td>${activity.ACTIVITY_DATE}</td>
						</tr>	
					</c:forEach>
				</tbody>
		</table>
		    		
</div>																	 										

	
