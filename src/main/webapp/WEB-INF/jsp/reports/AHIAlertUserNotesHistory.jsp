<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		<table class="table pB20 width97Perc">
			<tbody style="font-size:12px;">
			<c:forEach items="${ahiAlertUserNotesHistory}" var="ahiAlertTrackingVO" varStatus="docCount">
			  <tr>
			    <td width="100px">${ahiAlertTrackingVO.lastUpdatedBy}</td>
			    <td width="80px">${ahiAlertTrackingVO.lastUpdatedDate}</td>
			    <td width="350px">${ahiAlertTrackingVO.followupStepTaken}</td>
			    <td>${ahiAlertTrackingVO.userNotes}</td>
			  </tr>
			</c:forEach>	
			</tbody>		  
		</table>
