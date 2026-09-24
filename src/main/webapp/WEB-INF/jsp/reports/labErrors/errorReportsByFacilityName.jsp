<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="errorReportMatrix" >
	


	<c:choose>
		<c:when test="${errorReportsByFacilityName != null && errorReportsByFacilityName != '[]'}" >
						<div class="tableReferenceDetails" id="errorReportsByDocument">
							<div  class=" width75Perc pagebreak inlineBlock solidBorder2Black textAlignCenter assignmentReportMatrixByDocumentType marginTop20 pB20">
								
								<div  class="alignRight mR30 pT10">
  									
  									<a onclick="doGenerateErrorReportFile();" class="pdfDownload bold font10pt" href = "#">Download</a>
									  
								
								</div>

								<div class="mT20 ">
											<span class="font15pt">LABORATORY REPORTS IN ERROR BY LABORATORY</span><br/>
   	
   
   
								</div>
								<table class="table table-striped exportTables pB20 width75Perc expandCollpaseContent"  align="center" > 
									<thead style="font-size:12px;">
										<tr>
											<th class="displayNone floatLeft"></th>
											<th >LAFT Staff</th>
											<th >CLIA</th>
											<th >PFI</th>
											<th >Laboratory Name</th>
											<th >Number of Lab Reports in ERROR</th>
										</tr>
									</thead>
									<tbody>
											<c:set var="totalErrors" value="0"/>
											<c:forEach items="${errorReportsByFacilityName}" var="errorMap">
												
														<c:set var="totalErrors" value="${totalErrors+errorMap.COUNT }" />
														<tr>
															<td class="displayNone"></td>
															<td>${errorMap.LAFT_STAFF}</td>
															<td>${errorMap.CLIA}</td>
															<td>${errorMap.PFI}</td>
															<td>${errorMap.LAB_NAME}</td>
															<td>${errorMap.COUNT}</td>
														</tr>	
													
												
											</c:forEach>
											
											
											<tr class="bold">
															<td class="displayNone"></td>
															<td colspan="4">TOTAL NUMBER OF LABORATORY REPORTS in ERROR</td>
															<td>${totalErrors}</td>
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