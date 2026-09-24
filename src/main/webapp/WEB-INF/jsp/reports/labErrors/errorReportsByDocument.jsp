<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="errorReportMatrix" >

	<c:choose>
		<c:when test="${errorReportsByCategory != null && errorReportsByCategory != '[]'}" >
						<div class="tableReferenceDetails" id="errorReportsByCategory">
							<div  class=" width75Perc pagebreak inlineBlock solidBorder2Black textAlignCenter assignmentReportMatrixByDocumentType marginTop20 pB20">
								
								<div  class="alignRight mR30 pT10">
  									
  									<a onclick="doGenerateErrorReportFile();" class="pdfDownload bold font10pt" href = "#">Download</a>
									  
								
								</div>

								<div class="mT20 ">
											<span class="font15pt">LABORATORY REPORTS IN ERROR BY ERROR TYPE </span><br/>
   
								</div>
								
								
								
								<table class="table table-striped exportTables pB20 width75Perc expandCollpaseContent"  align="center" > 
									<thead style="font-size:12px;">
										<tr>
											<th class="displayNone floatLeft"></th>
											<th >ERROR</th>
											<th >Number of Lab Reports in ERROR</th>
										</tr>
									</thead>
									<tbody>
											<c:set var="totalErrors" value="0" />
											
											<c:forEach items="${errorReportsByCategory}" var="errorMap">
														
														
														<tr>
															<td class="displayNone"></td>
															<td class="bold errorCheck">${errorMap.key}:</td>
															<td></td>
														</tr>
														
													
														<c:forEach items="${errorMap.value}" var="errorCategory">
														
															<c:if test="${errorCategory.key != 'Soft Check' }">
															
																<tr>
																	<td class="displayNone"></td>
																	<td class="bold errorCategory">${errorCategory.key}:</td>
																	<td></td>
																	
																</tr>
															</c:if>
															
															<c:forEach items="${errorCategory.value}" var="error">
																<tr >
																	<td class="displayNone"></td>
																	<td class="pL50">${error.key}</td>
																	<td>${error.value}</td>
																</tr>
																<c:set var="totalErrors" value="${totalErrors+error.value }" />
															
															</c:forEach>
															
															
														
														
														</c:forEach>
														
															
														
													
											
														
												
											</c:forEach>
											
											<tr class="bold">
																	<td class="displayNone"></td>
																	<td class="pL20">TOTAL ERRORS</td>
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