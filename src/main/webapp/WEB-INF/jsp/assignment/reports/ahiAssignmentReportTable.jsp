<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="tableContents" class="clearBoth panel-body panel-body-table" style="text-align:center; border: 1px solid slategray">
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
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${ahiReportMap}" var="data">
										<tr>
															<td class="displayNone"></td>
															<td>${data.CURRENT_STATUS}</td>
															<td class="${documentTypeClass}">${data.DOC_TYPE}</td>
															<td>
																		<c:choose>
																			<c:when test="${data.R1 != 0 }">
																				<a href="#" onclick="loadAssignmentAHIDocuments('1','${data.CURRENT_STATUS}','${data.DOC_TYPE}','${data.CURRENT_STATUS_CODE}', 'Capital District')">${data.R1}</a>
																			</c:when>
																			<c:otherwise>
																				${data.R1}
																			</c:otherwise>
																		</c:choose>
															</td>
															<td>
																		<c:choose>
																			<c:when test="${data.R2 != 0 }">
																				<a href="#" onclick="loadAssignmentAHIDocuments('2','${data.CURRENT_STATUS}','${data.DOC_TYPE}','${data.CURRENT_STATUS_CODE}', 'Syracuse')">${data.R2}</a>
																			</c:when>
																			<c:otherwise>
																				${data.R2}
																			</c:otherwise>
																		</c:choose>
															</td>
															<td>
																		<c:choose>
																			<c:when test="${data.R3 != 0 }">
																				<a href="#" onclick="loadAssignmentAHIDocuments('3','${data.CURRENT_STATUS}','${data.DOC_TYPE}','${data.CURRENT_STATUS_CODE}', 'New Rochelle')">${data.R3}</a>
																			</c:when>
																			<c:otherwise>
																				${data.R3}
																			</c:otherwise>
																		</c:choose>
															</td>
															<td>
																		<c:choose>
																			<c:when test="${data.R4 != 0 }">
																				<a href="#" onclick="loadAssignmentAHIDocuments('4','${data.CURRENT_STATUS}','${data.DOC_TYPE}','${data.CURRENT_STATUS_CODE}', 'Long Island')">${data.R4}</a>
																			</c:when>
																			<c:otherwise>
																				${data.R4}
																			</c:otherwise>
																		</c:choose>
															</td>
															<td>
																		<c:choose>
																			<c:when test="${data.R5 != 0 }">
																				<a href="#" onclick="loadAssignmentAHIDocuments('5','${data.CURRENT_STATUS}','${data.DOC_TYPE}','${data.CURRENT_STATUS_CODE}', 'Rochester')">${data.R5}</a>
																			</c:when>
																			<c:otherwise>
																				${data.R5}
																			</c:otherwise>
																		</c:choose>
															</td>
															<td>
																		<c:choose>
																			<c:when test="${data.R6 != 0 }">
																				<a href="#" onclick="loadAssignmentAHIDocuments('6','${data.CURRENT_STATUS}','${data.DOC_TYPE}','${data.CURRENT_STATUS_CODE}', 'Buffalo')">${data.R6}</a>
																			</c:when>
																			<c:otherwise>
																				${data.R6}
																			</c:otherwise>
																		</c:choose>
															</td>
															<td>
																		<c:choose>
																			<c:when test="${data.R7 != 0 }">
																				<a href="#" onclick="loadAssignmentAHIDocuments('7','${data.CURRENT_STATUS}','${data.DOC_TYPE}','${data.CURRENT_STATUS_CODE}', 'New York City')">${data.R7}</a>
																			</c:when>
																			<c:otherwise>
																				${data.R7}
																			</c:otherwise>
																		</c:choose>
															</td>
															<td>
																		<c:choose>
																			<c:when test="${data.R9 != 0 }">
																				<a href="#" onclick="loadAssignmentAHIDocuments('9','${data.CURRENT_STATUS}','${data.DOC_TYPE}','${data.CURRENT_STATUS_CODE}', 'Central Office')">${data.R9}</a>
																			</c:when>
																			<c:otherwise>
																				${data.R9}
																			</c:otherwise>
																		</c:choose>
															</td>
														</tr>	
													</c:forEach>
												</tbody>
										</table>
	                                                                      
                                    
</div>