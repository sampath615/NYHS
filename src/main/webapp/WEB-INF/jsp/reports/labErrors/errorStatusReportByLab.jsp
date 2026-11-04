<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="errorReportMatrix" >
	


	<c:choose>
		<c:when test="${errorStatusReportByLab != null && errorStatusReportByLab != '[]'}" >
						<div class="tableReferenceDetails" id="errorReportsByDocument">
							<div  class=" width90Perc pagebreak inlineBlock solidBorder2Black textAlignCenter assignmentReportMatrixByDocumentType marginTop20 pB20">
								
								<div  class="alignRight mR30 pT10">
  									
  									<a onclick="doGenerateErrorReportFile();" class="pdfDownload bold font10pt" href = "#">Download</a>
									  
								
								</div>

								<div class="mT20 ">
											<span class="font15pt">LABORATORY REPORTS IN ERROR, REPRISED and DELETED BY LABORATORY</span><br/>
											<span class="font15pt">FROM ${param.fromDate} THROUGH ${param.toDate} </span><br/>
											
											
											
	
   	
   
   
								</div>
								<div style="overflow-x:auto;">
								<table class="table table-striped exportTables pB20 width90Perc expandCollpaseContent"  align="center" > 
									<thead style="font-size:12px;">
										<tr>
											<th class="displayNone floatLeft"></th>
											<th colspan = "4" ></th>
											<th colspan="3">Number Lab Reports</th>
										</tr>
										<tr>
											<th class="displayNone floatLeft"></th>
											<th >CLIA</th>
											<th >PFI</th>
											<th >Laboratory Name</th>
											<th >LAFT Staff Assigned to Lab</th>
											<th >Reprised</th>
											<th >Deleted</th>
											<th >Edited</th>
										</tr>
									</thead>
									<tbody>
											
											<c:set var="totalReprisedCount" value="0" />
											<c:set var="totalDeletedCount" value="0" />
											<c:set var="totalEditedCount" value="0" />
											
											
											<c:forEach items="${errorStatusReportByLab}" var="errorData">
														
														<c:set var="totalReprisedCount" value="${totalReprisedCount+errorData.REPRISE_CNT }" />
														<c:set var="totalDeletedCount" value="${totalDeletedCount+errorData.DELETE_CNT }" />
														<c:set var="totalEditedCount" value="${totalEditedCount+errorData.EDIT_CNT }" />
													
														<tr>
															<td class="displayNone"></td>
															<td>${errorData.CLIA}</td>
															<td>${errorData.PFI}</td>
															<td>${errorData.LAB_NAME}</td>
															<td>${errorData.UPDATEID}</td>
															<td>${errorData.REPRISE_CNT}</td>
															<td>${errorData.DELETE_CNT}</td>
															<td>${errorData.EDIT_CNT}</td>
														</tr>	
													
												
											</c:forEach>
											
											<tr class="bold">
												<td colspan="4">TOTAL LABORATORY REPORTS REPRISED OR DELETED</td>
												<td>${totalReprisedCount}</td>
												<td>${totalDeletedCount}</td>
												<td>${totalEditedCount}</td>
											</tr>
									</tbody>
								</table>
								</div>	
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