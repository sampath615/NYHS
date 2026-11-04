<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="mergeReportMatrix" >
	<c:choose>
		<c:when test="${mergeReportsByBadMerges != null && mergeReportsByBadMerges != '[]'}" >
			<div class="tableReferenceDetails" id="mergeReportsByBadMerges">
				<div  class=" width75Perc pagebreak inlineBlock solidBorder2Black textAlignCenter assignmentReportMatrixByDocumentType marginTop20 pB20">					
					<div  class="alignRight mR30 pT10">		
						<a onclick="doGenerateMergeReportFile();" class="pdfDownload bold font10pt" href = "#">Download</a>
					</div>

					<div class="mT20 ">
						<span class="font15pt">Number of Bad Merges Flagged for Review</span><br/>
						<span class="font12pt">*Weekly counts are pulled each Friday at 6 pm </span><br/>
					</div>
					<table class="table table-striped exportTables pB20 width75Perc expandCollpaseContent"  align="center" > 
						<thead style="font-size:12px;">
							<tr>
								<th class="displayNone floatLeft"></th>
								<th>Bad Merges</th>								
								<th>${previousWeeksFriday1}</th>
								<th>${previousWeeksFriday2}</th>
								<th>${previousWeeksFriday3}</th>
								<th>${previousWeeksFriday4}</th>
								<th>${previousWeeksFriday5}</th>
								<th>${previousWeeksFriday6}</th>
								<th>${previousWeeksFriday7}</th>
								<th>${previousWeeksFriday8}</th>
								<th>Currently NEW</th>
							</tr>
						</thead>
						<tbody>
							<tr class="bold">
								<td class="displayNone"></td>
								<td>TOTAL BAD MERGES</td>
								<td>${mergeReportsByBadMerges[0].BadMergesWeek1}</td>
								<td>${mergeReportsByBadMerges[0].BadMergesWeek2}</td>
								<td>${mergeReportsByBadMerges[0].BadMergesWeek3}</td>
								<td>${mergeReportsByBadMerges[0].BadMergesWeek4}</td>
								<td>${mergeReportsByBadMerges[0].BadMergesWeek5}</td>
								<td>${mergeReportsByBadMerges[0].BadMergesWeek6}</td>
								<td>${mergeReportsByBadMerges[0].BadMergesWeek7}</td>
								<td>${mergeReportsByBadMerges[0].BadMergesWeek8}</td>
								<td>${mergeReportsByBadMerges[0].CURRENTLY_NEW}</td>
							</tr>	
						</tbody>
					</table>	
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="tableReferenceDetails assignmentReportMatrixByRegion padding20">
					<span class="font20pt">No Data found with the criteria.</span>
			</div>
		</c:otherwise>
	</c:choose>
	
</div>