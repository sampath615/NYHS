<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

 	
		<table class=" bordered"  > 
				<thead style="font-size:12px;">
					<tr>
						<th >No Treatment Start Date</th>
						<th >Treatment Start Date Before Dx Date</th>
					</tr>
				</thead>
				<tbody>
						<tr>
							<td><a href="#" onClick="loadRiaDataByTreatment('NO_TREATMENT', 'NO_TREATMENT', 'No Treatment Start Date')">${riaTreatmentReport.NO_TREATMENT}</a></td>
							<td><a href="#" onClick="loadRiaDataByTreatment('TREATMENT', 'PRIOR_TREATMENT', 'Treatment Start Date Before Dx Date')">${riaTreatmentReport.PRIOR_TREATMENT}</a></td>
						</tr>	
							
				</tbody>
		</table>
		    		
