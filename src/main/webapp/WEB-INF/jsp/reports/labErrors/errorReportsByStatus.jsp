<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="errorReportMatrix" >
	


	<c:choose>
		<c:when test="${errorReportsByStatus != null && errorReportsByStatus != '[]' && workerNames != '[]'}" >
						<div class="tableReferenceDetails" id="errorReportsByDocument">
							<div  class=" width90Perc  pagebreak inlineBlock solidBorder2Black textAlignCenter assignmentReportMatrixByDocumentType marginTop20 pB20">
								
								<div  class="alignRight mR30 pT10">
  									
  									<a onclick="doGenerateErrorReportFile();" class="pdfDownload bold font10pt" href = "#">Download</a>
									  
								
								</div>

								<div class="mT20 ">
											<span class="font15pt">LAFT ERROR RESOLUTION</span><br/>
											<span class="font15pt">Daily Count of Laboratory Reports in ERROR Deleted, Reprised or Edited</span><br/>
											<span class="font15pt">FROM ${param.fromDate} THROUGH ${param.toDate} </span><br/>
   
								</div>
								
								<c:set var="totalReprisedCount" value="0" />
								<c:set var="totalDeleteCountBy" value="0" />
								<c:set var="totalEditCountBy" value="0" />
								
								<div style="overflow-x:auto; height:60%; overflow-y:auto;">							
								<table class="table table-striped exportTables pB20 width90Perc  expandCollpaseContent"  align="center" > 
									<thead style="font-size:12px;">
										<tr>
											<th class="displayNone floatLeft"></th>
											<th >LAFT Staff</th>
											
											
											<c:forEach items="${workerNames}" var="workerName">
												<th colspan="3" align="center">${workerName}</th>
											</c:forEach>
											
											<th colspan="3" align="center">Total</th>
										</tr>
										
										<tr>
											<th class="displayNone floatLeft"></th>
											<th >Date</th>
											
											
											<c:forEach items="${workerNames}" var="workerName">
												<th>Deleted</th>
												<th>Reprised</th>
												<th>Edited</th>
											</c:forEach>
											
											<th >Deleted</th>
											<th >Reprised</th>
											<th >Edited</th>
										</tr>
									</thead>
									<tbody>
											
											
											<c:forEach items="${errorReportsByStatus}" var="errorDataByWeek">
														
														<c:set var="week" value="${errorDataByWeek.key }" /> 
														
														<c:forEach items="${errorDataByWeek.value}" var="errorDataByDate">
															
															<c:set var="createDate" value="${errorDataByDate.key}" />
															
															
															<c:set var="totalReprisedCountByDay" value="0" />
															<c:set var="totalDeleteCountByDay" value="0" />
															<c:set var="totalEditCountByDay" value="0" />
															
															
															<tr>
															
																<c:set value="${errorDataByDate.value}" var="errorDataByUser" />
																	
																	<c:set var="UPDATEID" value="${errorDataByUser.key}" />
																	<c:set var="errorStatusData" value="${errorDataByUser.value}" />
																
																
																	
																		<td class="displayNone"></td>
																		<td>${createDate}</td>
																
																		<c:forEach items="${workerNames}" var="workerName">
																		
																			<c:set var="errorStatusData" value="${errorDataByUser[workerName] }" />
																		
																			<c:choose>
																				<c:when test="${errorStatusData != null && errorStatusData != '{}'}">
																						<td>${errorStatusData.DELETE}</td>
																						<td>${errorStatusData.REPRISE}</td>
																						<td>${errorStatusData.EDIT}</td>
																						
																						
																						
																						<c:set var="totalReprisedCountByDay" value="${totalReprisedCountByDay+errorStatusData.REPRISE }" />
																						<c:set var="totalDeleteCountByDay" value="${totalDeleteCountByDay+errorStatusData.DELETE}" />
																						<c:set var="totalEditCountByDay" value="${totalEditCountByDay+errorStatusData.EDIT}" />
																						
																						<c:set var="totalReprisedCount" value="${totalReprisedCount+errorStatusData.REPRISE }" />
																						<c:set var="totalDeleteCount" value="${totalDeleteCount+errorStatusData.DELETE}" />
																						<c:set var="totalEditCount" value="${totalEditCount+errorStatusData.EDIT}" />
																						
																						
					
																						
																						
																				</c:when>
																				<c:otherwise>
																					<td></td>
																					<td></td>
																					<td></td>		
																				</c:otherwise>
																			</c:choose>
																		
																		</c:forEach>
																	
																
																
																<td>
																	<c:if test="${totalDeleteCountByDay > 0 }" >
																		${totalDeleteCountByDay }
																	</c:if>
																</td>
																<td>
																	<c:if test="${totalReprisedCountByDay > 0 }" >
																		${totalReprisedCountByDay }
																	</c:if>
																</td>
																<td>
																	<c:if test="${totalEditCountByDay > 0 }" >
																		${totalEditCountByDay }
																	</c:if>
																</td>
															</tr>
															
														</c:forEach>
														
														
															
															<c:set var="totalReprisedCountByWeek" value="0" />
															<c:set var="totalDeleteCountByWeek" value="0" />
															<c:set var="totalEditCountByWeek" value="0" />
														
															<tr class="bold">	
																<td>Weekly Total</td>
																
																
																<c:set var="statusCountsByUser" value="${weeklyTotalByUser[week]}" />
																
																
																<c:forEach items="${workerNames}" var="workerName">
																
																	
																	<c:set var="statusCounts" value="${statusCountsByUser[workerName]}" />
																
																	
																
																
																	<td><c:out value="${statusCounts.DELETE}" /></td>
																	<td><c:out value="${statusCounts.REPRISE}" /></td>
																	<td><c:out value="${statusCounts.EDIT}" /></td>
																	
																	
																	<c:set var="totalReprisedCountByWeek" value="${totalReprisedCountByWeek+statusCounts.REPRISE}" />
																	<c:set var="totalDeleteCountByWeek" value="${totalDeleteCountByWeek+statusCounts.DELETE}" />
																	<c:set var="totalEditCountByWeek" value="${totalEditCountByWeek+statusCounts.EDIT}" />
																			
																
																
																</c:forEach>
																
																<td>
																	<c:if test="${totalDeleteCountByWeek > 0 }" >
																		${totalDeleteCountByWeek }
																	</c:if>
																</td>
																<td>
																	<c:if test="${totalReprisedCountByWeek > 0 }" >
																		${totalReprisedCountByWeek }
																	</c:if>
																</td>
																<td>
																	<c:if test="${totalEditCountByWeek > 0 }" >
																		${totalEditCountByWeek }
																	</c:if>
																</td>
																
																
															</tr>
														
														
														
												
											</c:forEach>
											
											<tr class="bold">	
																<td>Total For Date Range</td>
																
																
																
																<c:forEach items="${workerNames}" var="workerName">
																
																	
																	<c:set var="statusCounts" value="${totalStatusCountByUser[workerName]}" />
																
																	
																
																
																	<td><c:out value="${statusCounts.DELETE}" /></td>
																	<td><c:out value="${statusCounts.REPRISE}" /></td>
																	<td><c:out value="${statusCounts.EDIT}" /></td>
																	
																
																
																</c:forEach>
																
																
																<td>
																	<c:if test="${totalDeleteCount > 0 }" >
																		${totalDeleteCount }
																	</c:if>
																</td>
																<td>
																	<c:if test="${totalReprisedCount > 0 }" >
																		${totalReprisedCount }
																	</c:if>
																</td>
																<td>
																	<c:if test="${totalEditCount > 0 }" >
																		${totalEditCount }
																	</c:if>
																</td>
																
																
																
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
