<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="mergeReportMatrix" >
	<c:choose>
		<c:when test="${mergeReportsByOrigin != null && mergeReportsByOrigin != '[]'}" >
			<div class="tableReferenceDetails" id="mergeReportsByOrigin">
				<div  class=" width75Perc pagebreak inlineBlock solidBorder2Black textAlignCenter assignmentReportMatrixByDocumentType marginTop20 pB20">					
					<div  class="alignRight mR30 pT10">		
						<a onclick="doGenerateMergeReportFile();" class="pdfDownload bold font10pt" href = "#">Download</a>
					</div>

					<div class="mT20 ">
						<span class="font15pt">Number of Merges Flagged for Review By Origin Category</span><br/>
						<span class="font12pt">*Weekly counts are pulled each Friday at 6 pm </span><br/>
					</div>
					<table class="table table-striped exportTables pB20 width75Perc expandCollpaseContent"  align="center" > 
						<thead style="font-size:12px;">
							<tr>
								<th class="displayNone floatLeft"></th>
								<th>Origin Category</th>								
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
							<c:set var="totalMergesWeek1" value="0"/>
							<c:set var="totalMergesWeek2" value="0"/>
							<c:set var="totalMergesWeek3" value="0"/>
							<c:set var="totalMergesWeek4" value="0"/>
							<c:set var="totalMergesWeek5" value="0"/>
							<c:set var="totalMergesWeek6" value="0"/>
							<c:set var="totalMergesWeek7" value="0"/>
							<c:set var="totalMergesWeek8" value="0"/>
							<c:set var="totalCurrentlyNew" value="0"/>
							<c:forEach items="${mergeReportsByOrigin}" var="mergeMap">		
								<c:set var="totalMergesWeek1" value="${totalMergesWeek1 + mergeMap.MergeReportWeek1}" />
								<c:set var="totalMergesWeek2" value="${totalMergesWeek2 + mergeMap.MergeReportWeek2}" />
								<c:set var="totalMergesWeek3" value="${totalMergesWeek3 + mergeMap.MergeReportWeek3}" />
								<c:set var="totalMergesWeek4" value="${totalMergesWeek4 + mergeMap.MergeReportWeek4}" />
								<c:set var="totalMergesWeek5" value="${totalMergesWeek5 + mergeMap.MergeReportWeek5}" />
								<c:set var="totalMergesWeek6" value="${totalMergesWeek6 + mergeMap.MergeReportWeek6}" />
								<c:set var="totalMergesWeek7" value="${totalMergesWeek7 + mergeMap.MergeReportWeek7}" />
								<c:set var="totalMergesWeek8" value="${totalMergesWeek8 + mergeMap.MergeReportWeek8}" />
								<c:set var="totalCurrentlyNew" value="${totalCurrentlyNew + mergeMap.CURRENTLY_NEW}" />
								<tr>
									<td class="displayNone"></td>
									<td>${mergeMap.ORIGIN}</td>
									<td>${mergeMap.MergeReportWeek1}</td>
									<td>${mergeMap.MergeReportWeek2}</td>
									<td>${mergeMap.MergeReportWeek3}</td>
									<td>${mergeMap.MergeReportWeek4}</td>
									<td>${mergeMap.MergeReportWeek5}</td>
									<td>${mergeMap.MergeReportWeek6}</td>
									<td>${mergeMap.MergeReportWeek7}</td>
									<td>${mergeMap.MergeReportWeek8}</td>
									<td>${mergeMap.CURRENTLY_NEW}</td>
								</tr>	
							</c:forEach>
							<tr class="bold">
								<td class="displayNone"></td>
								<td>TOTAL MERGES</td>
								<td>${totalMergesWeek1}</td>
								<td>${totalMergesWeek2}</td>
								<td>${totalMergesWeek3}</td>
								<td>${totalMergesWeek4}</td>
								<td>${totalMergesWeek5}</td>
								<td>${totalMergesWeek6}</td>
								<td>${totalMergesWeek7}</td>
								<td>${totalMergesWeek8}</td>
								<td>${totalCurrentlyNew}</td>
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