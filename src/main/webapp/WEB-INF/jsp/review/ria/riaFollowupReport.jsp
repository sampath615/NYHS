<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

 	
		<table class=" bordered"  > 
				<thead style="font-size:12px;">
					<tr>
						<th >Program Type</th>
						<th >Completed</th>
						<th>Not Completed</th>
					</tr>
				</thead>
				<tbody>
						<tr>
							<th>BHAE</th>
							<td><a href="#" onClick="loadRiaDataByFollowup('BHAE','Y', 'BHAE Completed')">${riaFollowUpReport.BHAE_COMPLETED}</a></td>
							<td><a href="#" onClick="loadRiaDataByFollowup('BHAE','N', 'BHAE Not Completed')">${riaFollowUpReport.BHAE_NOT_COMPLETED}</a></td>
						</tr>	
						<tr>
							<th>OMD</th>
							<td><a href="#" onClick="loadRiaDataByFollowup('OMD','Y', 'OMD Completed')">${riaFollowUpReport.OMD_COMPLETED}</a></td>
							<td><a href="#" onClick="loadRiaDataByFollowup('OMD','N', 'OMD Not Completed')">${riaFollowUpReport.OMD_NOT_COMPLETED}</a></td>
						</tr>	
						<tr>
							<th>PS</th>
							<td><a href="#" onClick="loadRiaDataByFollowup('PS','Y', 'PS Completed')">${riaFollowUpReport.PS_COMPLETED}</a></td>
							<td><a href="#" onClick="loadRiaDataByFollowup('PS','N', 'PS Not Completed')">${riaFollowUpReport.PS_NOT_COMPLETED}</a></td>
						</tr>	
				</tbody>
		</table>
		    		
