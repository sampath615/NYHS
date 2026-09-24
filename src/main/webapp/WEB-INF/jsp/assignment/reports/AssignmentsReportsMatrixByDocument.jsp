<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="documentTypeClass" value="displayNone" />
<c:set var="documentTypeCode" value="${param.documentType}" />
<c:set var="documentName" value="${param.documentName}" />

<c:if test="${documentTypeCode == 'HIV_LAB' }">
	<c:set var="documentTypeClass" value="" />
</c:if>

<div id="assignmentReportMatrix">
	<c:choose>
		<c:when test="${assignmentReportsMatrixByRegion != null && assignmentReportsMatrixByRegion != '{}'}" >
						<div class="tableReferenceDetails" id="AssignmentReportByRegion">
							<div  class=" width90Perc pagebreak inlineBlock solidBorder2Black textAlignCenter assignmentReportMatrixByDocumentType marginTop20 marginBottom10">
								<div class="mT20 width90Perc">
									<c:choose>
										<c:when test="${param.fileName != '' && param.fileName != null}">
											<span class="font15pt">${documentName} Match ${param.fileDisplayName} </span><br/>
										</c:when>
										<c:otherwise>
											<span class="font15pt">${documentName} Match ${param.fromDate} - ${param.toDate} </span><br/>
										</c:otherwise>
									</c:choose>
									<span class="font15pt">NYEHMS Assignment Status By Region </span><br/>
									<span class="font15pt">${param.currentDate}</span>
								</div>
								<table class="table table-striped exportTables pB20 width97Perc expandCollpaseContent"  align="center" > 
									<thead style="font-size:12px;">
										<tr>
											<th class="displayNone floatLeft"></th>
											<th >Assignment Status</th>
											<th class="${documentTypeClass}">Document Type</th>
											<th>Capital District</th>
											<th>Syracuse</th>
											<th>New Rochelle</th>
											<th>Long Island</th>
											<th>Rochester</th>
											<th>Buffalo</th>
											<th>New York City</th>
											<th>Central Office</th>
											<th>Totals</th>
										</tr>
									</thead>
									<tbody>
									
												<c:set var="TOTAL_CAPITAL_DISTRICT_COUNT" value="0" />
												<c:set var="TOTAL_SYRACUSE_COUNT" value="0" />
												<c:set var="TOTAL_NEW_ROCHELLE_COUNT" value="0" />
												<c:set var="TOTAL_LONG_ISLAND_COUNT" value="0" />
												<c:set var="TOTAL_ROCHESTER_COUNT" value="0" />
												<c:set var="TOTAL_BUFFALO_COUNT" value="0" />
												<c:set var="TOTAL_NEW_YORK_CITY_COUNT" value="0" />
												<c:set var="TOTAL_CENTRAL_OFFICE_COUNT" value="0" />
												<c:set var="assignmentStatus" value="" />
											
											<c:forEach items="${assignmentReportsMatrixByRegion}" var="statusMap">
												<c:set var="currentStatus" value="${statusMap.STATUS}" />
												<c:set var="currentStatusCode" value="${statusMap.STATUS_CD}" />
												
												
												<c:set var="CAPITAL_DISTRICT_COUNT" value="${statusMap.CAPITAL_DISTRICT}" />
												<c:set var="SYRACUSE_COUNT" value="${statusMap.SYRACUSE}" />
												<c:set var="NEW_ROCHELLE_COUNT" value="${statusMap.NEW_ROCHELLE}" />
												<c:set var="LONG_ISLAND_COUNT" value="${statusMap.LONG_ISLAND}" />
												<c:set var="ROCHESTER_COUNT" value="${statusMap.ROCHESTER}" />
												<c:set var="BUFFALO_COUNT" value="${statusMap.BUFFALO}" />
												<c:set var="NEW_YORK_CITY_COUNT" value="${statusMap.NEW_YORK_CITY}" />
												<c:set var="CENTRAL_OFFICE_COUNT" value="${statusMap.CENTRAL_OFFICE}" />
												
												
												
												<c:set var="TOTAL_CAPITAL_DISTRICT_COUNT" value="${TOTAL_CAPITAL_DISTRICT_COUNT+CAPITAL_DISTRICT_COUNT}" />
												<c:set var="TOTAL_SYRACUSE_COUNT" value="${TOTAL_SYRACUSE_COUNT+SYRACUSE_COUNT}" />
												<c:set var="TOTAL_NEW_ROCHELLE_COUNT" value="${TOTAL_NEW_ROCHELLE_COUNT+NEW_ROCHELLE_COUNT}" />
												<c:set var="TOTAL_LONG_ISLAND_COUNT" value="${TOTAL_LONG_ISLAND_COUNT+LONG_ISLAND_COUNT}" />
												<c:set var="TOTAL_ROCHESTER_COUNT" value="${TOTAL_ROCHESTER_COUNT+ROCHESTER_COUNT}" />
												<c:set var="TOTAL_BUFFALO_COUNT" value="${TOTAL_BUFFALO_COUNT+BUFFALO_COUNT}" />
												<c:set var="TOTAL_NEW_YORK_CITY_COUNT" value="${TOTAL_NEW_YORK_CITY_COUNT+NEW_YORK_CITY_COUNT}" />
												<c:set var="TOTAL_CENTRAL_OFFICE_COUNT" value="${TOTAL_CENTRAL_OFFICE_COUNT+CENTRAL_OFFICE_COUNT}" />
												
												<c:set var="DOCUMENT_TYPE" value="${statusMap.DOCUMENT_TYPE}" />
												
												<c:choose>
													<c:when test="${assignmentStatus ==  currentStatus}">
														<c:set var="currentStatus" value="" />
													</c:when>
													<c:otherwise>
														<c:set var="assignmentStatus" value="${currentStatus}" />
													</c:otherwise>
												</c:choose>
												
												<c:choose>
													<c:when test="${currentStatus == 'TOTALS'}">
														<tr>
															<td class="displayNone"></td>
															<td>TOTALS</td>
															<td class="${documentTypeClass}"></td>
															<td>
																		<c:choose>
																			<c:when test="${TOTAL_CAPITAL_DISTRICT_COUNT != 0 }">
																				<a href="#" onclick="loadAssignmentDocuments('1','','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '', 'Capital District', 'byRegion')">${TOTAL_CAPITAL_DISTRICT_COUNT}</a>
																			</c:when>
																			<c:otherwise>
																				${TOTAL_CAPITAL_DISTRICT_COUNT}
																			</c:otherwise>
																		</c:choose>
															</td>
															<td>
																		<c:choose>
																			<c:when test="${TOTAL_SYRACUSE_COUNT != 0 }">
																				<a href="#" onclick="loadAssignmentDocuments('2','','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '', 'Syracuse', 'byRegion')">${TOTAL_SYRACUSE_COUNT}</a>
																			</c:when>
																			<c:otherwise>
																				${TOTAL_SYRACUSE_COUNT}
																			</c:otherwise>
																		</c:choose>
															</td>
															<td>
																		<c:choose>
																			<c:when test="${TOTAL_NEW_ROCHELLE_COUNT != 0 }">
																				<a href="#" onclick="loadAssignmentDocuments('3','','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '', 'New Rochelle', 'byRegion')">${TOTAL_NEW_ROCHELLE_COUNT}</a>
																			</c:when>
																			<c:otherwise>
																				${TOTAL_NEW_ROCHELLE_COUNT}
																			</c:otherwise>
																		</c:choose>
															</td>
															<td>
																		<c:choose>
																			<c:when test="${TOTAL_LONG_ISLAND_COUNT != 0 }">
																				<a href="#" onclick="loadAssignmentDocuments('4','','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '', 'Long Island', 'byRegion')">${TOTAL_LONG_ISLAND_COUNT}</a>
																			</c:when>
																			<c:otherwise>
																				${TOTAL_LONG_ISLAND_COUNT}
																			</c:otherwise>
																		</c:choose>
															</td>
															<td>
																		<c:choose>
																			<c:when test="${TOTAL_ROCHESTER_COUNT != 0 }">
																				<a href="#" onclick="loadAssignmentDocuments('5','','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '', 'Rochester', 'byRegion')">${TOTAL_ROCHESTER_COUNT}</a>
																			</c:when>
																			<c:otherwise>
																				${TOTAL_ROCHESTER_COUNT}
																			</c:otherwise>
																		</c:choose>
															</td>
															<td>
																		<c:choose>
																			<c:when test="${TOTAL_BUFFALO_COUNT != 0 }">
																				<a href="#" onclick="loadAssignmentDocuments('6','','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '', 'Buffalo', 'byRegion')">${TOTAL_BUFFALO_COUNT}</a>
																			</c:when>
																			<c:otherwise>
																				${TOTAL_BUFFALO_COUNT}
																			</c:otherwise>
																		</c:choose>
															</td>
															<td>
																		<c:choose>
																			<c:when test="${TOTAL_NEW_YORK_CITY_COUNT != 0 }">
																				<a href="#" onclick="loadAssignmentDocuments('7','','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '', 'New York City', 'byRegion')">${TOTAL_NEW_YORK_CITY_COUNT}</a>
																			</c:when>
																			<c:otherwise>
																				${TOTAL_NEW_YORK_CITY_COUNT}
																			</c:otherwise>
																		</c:choose>
															</td>
															<td>
																		<c:choose>
																			<c:when test="${TOTAL_CENTRAL_OFFICE_COUNT != 0 }">
																				<a href="#" onclick="loadAssignmentDocuments('9','','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '', 'Central Office', 'byRegion')">${TOTAL_CENTRAL_OFFICE_COUNT}</a>
																			</c:when>
																			<c:otherwise>
																				${TOTAL_CENTRAL_OFFICE_COUNT}
																			</c:otherwise>
																		</c:choose>
															</td>
															<td>
																<c:set var="totalAssignmentCountForAllRegions" value="${TOTAL_CAPITAL_DISTRICT_COUNT + TOTAL_SYRACUSE_COUNT + TOTAL_NEW_ROCHELLE_COUNT + TOTAL_LONG_ISLAND_COUNT + TOTAL_ROCHESTER_COUNT + TOTAL_BUFFALO_COUNT + TOTAL_NEW_YORK_CITY_COUNT + TOTAL_CENTRAL_OFFICE_COUNT}" />
																<c:choose>
																	<c:when test="${totalAssignmentCountForAllRegions != 0 }">
																		<a href="#" onclick="loadAssignmentDocuments('','','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '', '', 'byRegion')">${totalAssignmentCountForAllRegions}</a>
																	</c:when>
																	<c:otherwise>
																		${totalAssignmentCountForAllRegions}
																	</c:otherwise>
																</c:choose>
																
															</td>
														</tr>	
													</c:when>
													<c:otherwise>
														<tr>
															<td class="displayNone"></td>
															<td>${currentStatus}</td>
															<td class="${documentTypeClass}">${DOCUMENT_TYPE}</td>
															<td>
																<c:choose>
																	<c:when test="${CAPITAL_DISTRICT_COUNT != 0 && currentStatusCode != '' && currentStatusCode != null}">
																		<a href="#" onclick="loadAssignmentDocuments('1','${currentStatusCode}','${DOCUMENT_TYPE}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '${assignmentStatus}', 'Capital District', 'byRegion')">${CAPITAL_DISTRICT_COUNT}</a>
																	</c:when>
																	<c:otherwise>
																		${CAPITAL_DISTRICT_COUNT}
																	</c:otherwise>
																</c:choose>
															</td>
															<td>
																<c:choose>
																	<c:when test="${SYRACUSE_COUNT != 0 && currentStatusCode != '' && currentStatusCode != null}">
																		<a href="#" onclick="loadAssignmentDocuments('2','${currentStatusCode}','${DOCUMENT_TYPE}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '${assignmentStatus}', 'Syracuse', 'byRegion')">${SYRACUSE_COUNT}</a>
																	</c:when>
																	<c:otherwise>
																		${SYRACUSE_COUNT}
																	</c:otherwise>
																</c:choose>
															</td>
															<td>
																<c:choose>
																	<c:when test="${NEW_ROCHELLE_COUNT != 0 && currentStatusCode != '' && currentStatusCode != null}">
																		<a href="#" onclick="loadAssignmentDocuments('3','${currentStatusCode}','${DOCUMENT_TYPE}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '${assignmentStatus}', 'New Rochelle', 'byRegion')">${NEW_ROCHELLE_COUNT}</a>
																	</c:when>
																	<c:otherwise>
																		${NEW_ROCHELLE_COUNT}
																	</c:otherwise>
																</c:choose>
															</td>
															<td>
																<c:choose>
																	<c:when test="${LONG_ISLAND_COUNT != 0 && currentStatusCode != '' && currentStatusCode != null}">
																		<a href="#" onclick="loadAssignmentDocuments('4','${currentStatusCode}','${DOCUMENT_TYPE}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '${assignmentStatus}', 'Long Island', 'byRegion')">${LONG_ISLAND_COUNT}</a>
																	</c:when>
																	<c:otherwise>
																		${LONG_ISLAND_COUNT}
																	</c:otherwise>
																</c:choose>
															</td>
																
															<td>
																<c:choose>
																	<c:when test="${ROCHESTER_COUNT != 0 && currentStatusCode != '' && currentStatusCode != null}">
																		<a href="#" onclick="loadAssignmentDocuments('5','${currentStatusCode}','${DOCUMENT_TYPE}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '${assignmentStatus}', 'Rochester', 'byRegion')">${ROCHESTER_COUNT}</a>
																	</c:when>
																	<c:otherwise>
																		${ROCHESTER_COUNT}
																	</c:otherwise>
																</c:choose>
		
															</td>
															<td>
																<c:choose>
																	<c:when test="${BUFFALO_COUNT != 0 && currentStatusCode != '' && currentStatusCode != null}">
																		<a href="#" onclick="loadAssignmentDocuments('6','${currentStatusCode}','${DOCUMENT_TYPE}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '${assignmentStatus}', 'Buffalo', 'byRegion')">${BUFFALO_COUNT}</a>
																	</c:when>
																	<c:otherwise>
																		${BUFFALO_COUNT}
																	</c:otherwise>
																</c:choose>
		
															</td>
															<td>
																<c:choose>
																	<c:when test="${NEW_YORK_CITY_COUNT != 0 && currentStatusCode != '' && currentStatusCode != null}">
																		<a href="#" onclick="loadAssignmentDocuments('7','${currentStatusCode}','${DOCUMENT_TYPE}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '${assignmentStatus}', 'New York City', 'byRegion')">${NEW_YORK_CITY_COUNT}</a>
																	</c:when>
																	<c:otherwise>
																		${NEW_YORK_CITY_COUNT}
																	</c:otherwise>
																</c:choose>
		
															</td>
															<td>
																<c:choose>
																	<c:when test="${CENTRAL_OFFICE_COUNT != 0 && currentStatusCode != '' && currentStatusCode != null}">
																		<a href="#" onclick="loadAssignmentDocuments('9','${currentStatusCode}','${DOCUMENT_TYPE}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '${assignmentStatus}', 'Central Office', 'byRegion')">${CENTRAL_OFFICE_COUNT}</a>
																	</c:when>
																	<c:otherwise>
																		${CENTRAL_OFFICE_COUNT}
																	</c:otherwise>
																</c:choose>
		
															</td>
															<td>
																<c:choose>
																	<c:when test="${statusMap.TOTAL != 0 && currentStatusCode != '' && currentStatusCode != null}">
																		<a href="#" onclick="loadAssignmentDocuments('','${currentStatusCode}','${DOCUMENT_TYPE}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '${assignmentStatus}', '', 'byRegion')">${statusMap.TOTAL}</a>
																	</c:when>
																	<c:otherwise>
																		${statusMap.TOTAL}
																	</c:otherwise>
																</c:choose>
															</td>
														</tr>	
													</c:otherwise> 
												</c:choose>
												
											</c:forEach>
									</tbody>
								</table>	
							</div>
						</div>
		</c:when>
		<c:otherwise>
			<div class="tableReferenceDetails assignmentReportMatrixByRegion padding20">
					<span class="font20pt">No Assignments found with the criteria.</span>
			</div>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${assignmentReportsMatrixByEharsStatus != null && assignmentReportsMatrixByEharsStatus != '{}'}" >
						<div class="tableReferenceDetails" id="AssignmentReportByEharsStatus">
							<div  class=" width90Perc pagebreak inlineBlock solidBorder2Black textAlignCenter assignmentReportMatrixByDocumentType marginTop20 marginBottom10">
								<div class="mT20 width90Perc">
									<c:choose>
										<c:when test="${param.fileName != '' && param.fileName != null}">
											<span class="font15pt">${param.documentName} Match ${param.fileDisplayName} </span><br/>
										</c:when>
										<c:otherwise>
											<span class="font15pt">${param.documentName} Match ${param.fromDate} - ${param.toDate} </span><br/>
										</c:otherwise>
									</c:choose>
									<span class="font15pt">NYEHMS Assignment Status By eHARS Status </span><br/>
									<span class="font15pt">${param.currentDate}</span>
								</div>
								<table class="table table-striped exportTables pB20 width97Perc expandCollpaseContent"  align="center" > 
									<thead style="font-size:12px;">
										<tr>
											<th colspan="2"></th>
											<th colspan="4">In eHARS</th>
											<th ></th>
										</tr>
										<tr>
											<th class="displayNone floatLeft"></th>
											<th>Assignment Status</th>
											<th>Not In eHARS</th>
											<th>Does Not Meet Case Definition</th>
											<th>Meets Case Definition Does Not Meet Eligibility</th>
											<th>Meets Case Definition Meets Eligibility</th>
											<th>Total</th>
										</tr>
									</thead>
									<tbody>
											<c:set var="TOTAL_NOT_IN_EHARS_CNT" value="0" />
											<c:set var="TOTAL_DOES_NOT_MCD_CNT" value="0" />
											<c:set var="TOTAL_MCD_NOT_ELIG_CNT" value="0" />
											<c:set var="TOTAL_ELIG_CNT" value="0" />
											<c:forEach items="${assignmentReportsMatrixByEharsStatus}" var="statusMap">
												<c:set var="NOT_IN_EHARS_CNT" value="${statusMap.NOT_IN_EHARS_CNT}" />
												<c:set var="DOES_NOT_MCD_CNT" value="${statusMap.DOES_NOT_MCD_CNT}" />
												<c:set var="MCD_NOT_ELIG_CNT" value="${statusMap.MCD_NOT_ELIG_CNT}" />
												<c:set var="ELIG_CNT" value="${statusMap.ELIG_CNT}" />
												
												<c:set var="TOTAL_NOT_IN_EHARS_CNT" value="${TOTAL_NOT_IN_EHARS_CNT + NOT_IN_EHARS_CNT}" />
												<c:set var="TOTAL_DOES_NOT_MCD_CNT" value="${TOTAL_DOES_NOT_MCD_CNT + DOES_NOT_MCD_CNT}" />
												<c:set var="TOTAL_MCD_NOT_ELIG_CNT" value="${TOTAL_MCD_NOT_ELIG_CNT + MCD_NOT_ELIG_CNT}" />
												<c:set var="TOTAL_ELIG_CNT" value="${TOTAL_ELIG_CNT + ELIG_CNT}" />
												<c:set var="currentAssignmentStatus" value="${statusMap.STATUS}" />
												<c:set var="currentStatusCode" value="${statusMap.STATUS_CD}" />
												
												<c:choose>
													<c:when test="${currentAssignmentStatus == 'TOTALS'}">
												
														<tr>
															<td class="displayNone"></td>
															<td>TOTALS</td>
															<td>
																<c:choose>
																	<c:when test="${TOTAL_NOT_IN_EHARS_CNT != 0 }">
																		<a href="#" onclick="loadAssignmentDocuments('', '','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '', 'Not In eHARS', 'eHars')">${TOTAL_NOT_IN_EHARS_CNT}</a>
																	</c:when>
																	<c:otherwise>
																		${TOTAL_NOT_IN_EHARS_CNT}
																	</c:otherwise>
																</c:choose>
															</td>
															<td>
																<c:choose>
																	<c:when test="${TOTAL_DOES_NOT_MCD_CNT != 0 }">
																		<a href="#" onclick="loadAssignmentDocuments('', '','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '', 'Does Not Meet Case Definition', 'eHars')">${TOTAL_DOES_NOT_MCD_CNT}</a>
																	</c:when>
																	<c:otherwise>
																		${TOTAL_DOES_NOT_MCD_CNT}
																	</c:otherwise>
																</c:choose>
															</td>
															<td>
																<c:choose>
																	<c:when test="${TOTAL_MCD_NOT_ELIG_CNT != 0 }">
																		<a href="#" onclick="loadAssignmentDocuments('', '','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '', 'Meets Case Definition Does Not Meet Eligibility', 'eHars')">${TOTAL_MCD_NOT_ELIG_CNT}</a>
																	</c:when>
																	<c:otherwise>
																		${TOTAL_MCD_NOT_ELIG_CNT}
																	</c:otherwise>
																</c:choose>
															</td>
															<td>
																<c:choose>
																	<c:when test="${TOTAL_ELIG_CNT != 0 }">
																		<a href="#" onclick="loadAssignmentDocuments('', '','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '', 'Meets Case Definition Meets Eligibility', 'eHars')">${TOTAL_ELIG_CNT}</a>
																	</c:when>
																	<c:otherwise>
																		${TOTAL_ELIG_CNT}
																	</c:otherwise>
																</c:choose>
															</td>
															<td>
															
																<c:set var="totalAssignmentCountForAllRegions" value="${TOTAL_NOT_IN_EHARS_CNT + TOTAL_DOES_NOT_MCD_CNT + TOTAL_MCD_NOT_ELIG_CNT + TOTAL_ELIG_CNT}" />
																	<c:choose>
																		<c:when test="${totalAssignmentCountForAllRegions != 0 }">
																			<a href="#" onclick="loadAssignmentDocuments('', '','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '', '', 'eHars')">${totalAssignmentCountForAllRegions}</a>
																		</c:when>
																		<c:otherwise>
																			${totalAssignmentCountForAllRegions}
																		</c:otherwise>
																	</c:choose>
															</td>
														</tr>
													</c:when>
													<c:otherwise>
														<tr>
															<td class="displayNone"></td>
															<td>${currentAssignmentStatus}</td>
															<td>
																<c:choose>
																	<c:when test="${NOT_IN_EHARS_CNT != 0 && currentStatusCode != '' && currentStatusCode != null}">
																		<a href="#" onclick="loadAssignmentDocuments('', '${currentStatusCode}','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '${currentAssignmentStatus}', 'Not In eHARS', 'eHars')">${NOT_IN_EHARS_CNT}</a>
																	</c:when>
																	<c:otherwise>
																		${NOT_IN_EHARS_CNT}
																	</c:otherwise>
																</c:choose>
															</td>
															<td>
																<c:choose>
																	<c:when test="${DOES_NOT_MCD_CNT != 0 && currentStatusCode != '' && currentStatusCode != null}">
																		<a href="#" onclick="loadAssignmentDocuments('', '${currentStatusCode}','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '${currentAssignmentStatus}', 'Does Not Meet Case Definition', 'eHars')">${DOES_NOT_MCD_CNT}</a>
																	</c:when>
																	<c:otherwise>
																		${DOES_NOT_MCD_CNT}
																	</c:otherwise>
																</c:choose>
															</td>
															<td>
																<c:choose>
																	<c:when test="${MCD_NOT_ELIG_CNT != 0 && currentStatusCode != '' && currentStatusCode != null}">
																		<a href="#" onclick="loadAssignmentDocuments('', '${currentStatusCode}','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '${currentAssignmentStatus}', 'Meets Case Definition Does Not Meet Eligibility', 'eHars')">${MCD_NOT_ELIG_CNT}</a>
																	</c:when>
																	<c:otherwise>
																		${MCD_NOT_ELIG_CNT}
																	</c:otherwise>
																</c:choose>
															</td>
															<td>
																<c:choose>
																	<c:when test="${ELIG_CNT != 0 && currentStatusCode != '' && currentStatusCode != null}">
																		<a href="#" onclick="loadAssignmentDocuments('', '${currentStatusCode}','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '${currentAssignmentStatus}', 'Meets Case Definition Meets Eligibility', 'eHars')">${ELIG_CNT}</a>
																	</c:when>
																	<c:otherwise>
																		${ELIG_CNT}
																	</c:otherwise>
																</c:choose>
															</td>
															<td>
																<c:choose>
																	<c:when test="${statusMap.TOTAL != 0 && currentStatusCode != '' && currentStatusCode != null}">
																		<a href="#" onclick="loadAssignmentDocuments('', '${currentStatusCode}','${documentTypeCode}','${documentName}','${param.fileName}', '${param.fromDate}', '${param.toDate}', '${currentAssignmentStatus}', '', 'eHars')">${statusMap.TOTAL}</a>
																	</c:when>
																	<c:otherwise>
																		${statusMap.TOTAL}
																	</c:otherwise>
																</c:choose>
															</td>
														</tr>	
													</c:otherwise>
												</c:choose>
												
											</c:forEach>
											
											
												
									</tbody>
								</table>	
							</div>
						</div>
		</c:when>
		<c:otherwise>
			<div class="tableReferenceDetails assignmentReportMatrixByRegion padding20">
					<span class="font20pt">No Assignments found with the criteria.</span>
			</div>
		</c:otherwise>
	</c:choose>
</div>