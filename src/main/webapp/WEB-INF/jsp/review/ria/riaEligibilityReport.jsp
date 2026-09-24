<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

 	
		<table class=" bordered"  > 
				<thead style="font-size:12px;">
					<tr>
						<th >RIA eligible</th>
						<th >RIA not eligible</th>
						<th>RIA eligibility not selected</th>
					</tr>
				</thead>
				<tbody>
						<tr>
							<td><a href="#" onClick="loadRiaData('ALL_ELIG','Y', 'RIA eligible')">${riaEligibilityReport.RIA_ELIGIBLE}</a></td>
							<td><a href="#" onClick="loadRiaData('ALL_INELIG','Y', 'RIA not eligible')">${riaEligibilityReport.RIA_NOT_ELIGIBLE}</a></td>
							<td><a href="#" onClick="loadRiaData('ALL_ELIG','N', 'RIA eligibility not selected')">${riaEligibilityReport.RIA_ELIGIBILITY_NOT_SELECTED}</a></td>
						</tr>	
				</tbody>
		</table>
		    		
