<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="assignmentReportMatrix">
	<c:choose>
		<c:when test="${errorMessage != null && errorMessage != ''}" >
			<div class="tableReferenceDetails assignmentReportMatrixByRegion padding20">
					<span class="font20pt highlightRowRedColor">${errorMessage}</span>
			</div>
		</c:when>
		<c:otherwise>
			<div>
			  <br/>
			  <span class="floatLeft bold mL10" style="font-size:15px"><input type="checkbox" id="selectAllReports"/> Select All</span>
			</div>
			<br/><br/>
			<div class="clearBoth"></div>

			<c:forEach items="${regionNames}" var="regionName">
			
			
				 
			
				<c:set var="assignmentsByWorker" value="${assignmentReportsMatrixObj[regionName]}" />
					<c:if test="${assignmentsByWorker != null || openAssignmentReportsByWorker[regionName] != null}">
						<div class="tableReferenceDetails" id="${regionName}_region_content">
<%-- 							<div style="width:8%" class="inlineBlock"><input type="checkbox" id="${regionName}_regionData" class="regions"></div> --%>
							
							<div class="floatLeft width10Perc">
							  <span class="collapseIcon expandButtons" reference="${regionName}_region_content"></span>
							</div>
							<div  class=" width90Perc pagebreak inlineBlock solidBorder2Black textAlignCenter assignmentReportMatrixByRegion <c:out value="${regionName}_regionData" /> marginTop20 marginBottom10">
								<div class="font20pt  floatLeft mL10 pB10 width90Perc textAlignLeft"><input type="checkbox" class="regions" id="${regionName}_regionData" title="Select to Print"></div>
								
								<div class="mT20 width90Perc">
									<span class="font20pt"><c:out value="${regionsList[regionName]}" /></span>
									<br/><br/>
									<span class="font15pt  expandCollpaseContent">Closed Assignments</span>
								</div>
								<c:choose>
									<c:when test="${assignmentsByWorker == null || assignmentsByWorker == '{}' }">
										<br/><br/>
										<span class="font15pt" style="padding:10px; border:2px solid black">No Closed Assignments with the selected criteria</span>
										
										<br/><br/>
										
									</c:when>
									<c:otherwise>
										<table class="table table-striped exportTables pB20 width97Perc expandCollpaseContent"  align="center" > 
											<thead style="font-size:12px;">
												<tr>
													<th class="floatLeft displayNone"></th>
													<th>Worker&nbsp;Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
																
													<c:forEach items="${monthNames}" var="month">
														<th><c:out value="${month}" /></th>
													</c:forEach>
													
												</tr>
											</thead>
											<tbody>
													<c:forEach items="${assignmentsByWorker}" var="workerMap">
													
														<tr>
															
															<td class="floatLeft displayNone"></td>
															<td>${workerMap.key}</td>
															
															<c:set value="${workerMap.value}" var="workerAssignmentsByMonth" />
															
															<c:forEach items="${monthNames}" var="month">
																
																<c:set value="${workerAssignmentsByMonth[month]}" var="assignmentsByClosedStatus" />
																
																
																
																<c:set var="adminClosedCount" value="${assignmentsByClosedStatus['CLS_ADM_CNT']}" />
																
																<c:set var="closedUnAssignedCount" value="${assignmentsByClosedStatus['CLOSED_UNASSIGNED_CNT']}" />
																
														
																<td class="padding3 reportCount">
																
																
																${assignmentsByClosedStatus['CLOSED_CNT']} 
																
																<c:if test="${adminClosedCount != null && adminClosedCount != 0}" >
																	<span class="highlightOrangeRedColor">(${adminClosedCount})</span>
																</c:if>
																
																
																<c:if test="${closedUnAssignedCount != null && closedUnAssignedCount != 0}" >
																	<span class="highlightGreenColor">(${closedUnAssignedCount})</span>
																</c:if>
																
																
																
																
																
																
																
																
																</td>	
															</c:forEach>
														</tr>	
													</c:forEach>
											</tbody>
										</table>	
									</c:otherwise>
								</c:choose>
								<div class="width75Perc margin20Auto expandCollpaseContent">
									<c:if test="${openAssignmentReportsByWorker[regionName] != null}">
										<c:set value="${openAssignmentReportsByWorker[regionName]}" var="AllRegionsAssignments" />
										<div class="width97Perc">
											<c:choose>
												<c:when test="${regionName == 'ALL' }">
														<div class="width75Perc  marginAuto ">
															<span class="font15pt">Current Open Assignments</span>
															<table class="table table-striped exportTables pB20 width100Perc  openAssignmentTable"  align="center">
																<thead style="font-size:12px;">
																	<tr>
																		<th class="floatLeft displayNone"></th>
																		<th>Worker</th>
																		<th>Open Assignments</th>
																		<th>Open 60 Days</th>
																	</tr>
																</thead>
																<tbody>
																		<c:set var="totalOpenAssignmentsCount" value="0" />
																		<c:set var="totalOpenAssignments60DaysCount" value="0" />
																		
																		<c:forEach items="${AllRegionsAssignments[regionName]}" var="workerMap">
																			<c:set var="worker" value="${workerMap.key}" />
																			<c:set var="allAssignmentsByCategory" value="${workerMap.value}" />
																			
																				<c:set var="currentWorkerOpenAssignments" value="${allAssignmentsByCategory['COUNT']}" />
																				<c:set var="currentWorkerOpen60DaysAssignments" value="${allAssignmentsByCategory['60DAYS']}" />	
																			
																				<c:set var="totalOpenAssignmentsCount" value="${totalOpenAssignmentsCount+currentWorkerOpenAssignments}" />
																				<c:set var="totalOpenAssignments60DaysCount" value="${totalOpenAssignments60DaysCount+currentWorkerOpen60DaysAssignments}" />
																			
																				<tr class="${worker}">
																						<td class="floatLeft displayNone"></td>
																						<td class="">${worker}</td>
																						<td class="padding3 reportCount">${currentWorkerOpenAssignments}</td>
																						<td class="padding3 reportCount">${currentWorkerOpen60DaysAssignments}</td>
																				</tr>
																			
																		</c:forEach>	
																		<tr class="${worker} total">
																				<td class="floatLeft displayNone"></td>
																				<td class="bold ">Total</td>
																				<td class="padding3 bold reportCount">${totalOpenAssignmentsCount}</td>
																				<td class="padding3 bold reportCount">${totalOpenAssignments60DaysCount}</td>
																		</tr>						
																</tbody>
															</table>
														</div>
															
												</c:when>
												<c:otherwise>
															<span class="font15pt displayBlock">Total number of Open Assignments (<span id="${regionName}_openAssignmentsCount" class="bold"></span>)</span>
															<c:set var="totalOpenAssignmentsInTheRegion" value="0" />
															<c:forEach items="${openAssignmentReportsByWorker[regionName]}" var="workerMap">
																<c:set var="totalOpenAssignmentsCountForTheUser" value="0" />
																<c:set var="totalOpenAssignments60DaysCountForTheUser" value="0" />
																<div id="${regionName}_${workerMap.key}" class="childTable floatLeft width50Perc marginTop20">
																	<span id="${regionName}_${workerMap.key}LabelName" class="bold"><c:out value="${workerMap.key}" escapeXml="false"/></span>
																	<table class="table table-striped exportTables pB20 width75Perc"   align="center">
																		<thead style="font-size:12px;">
																			<tr>
																				<th class="floatLeft displayNone"></th>
																				<th class="label100mrg">Document Type</th>			
																				<th>Open</th>
																				<th>60Days</th>
																			</tr>
																		</thead>
																		<tbody>
																			<c:forEach items="${workerMap.value}" var="assignmentsByDocumentType">
																				<c:set var="assignmentsByCategory" value="${assignmentsByDocumentType.value}" />
																				
																				<c:set var="totalOpenAssignmentsCountForTheUser" value="${totalOpenAssignmentsCountForTheUser+assignmentsByCategory['OPEN']}" />
																				<c:set var="totalOpenAssignments60DaysCountForTheUser" value="${totalOpenAssignments60DaysCountForTheUser+assignmentsByCategory['60DAYS']}" />
																				
																				<tr >
																					<td class="floatLeft displayNone"></td>
																					<td class="padding3 reportCount">${assignmentsByDocumentType.key}</td>
																					<td class="padding3 reportCount">${assignmentsByCategory['OPEN']}</td>
																					<td class="padding3 reportCount">${assignmentsByCategory['60DAYS']}</td>
																				</tr>
																				</c:forEach>
																				<c:set var="totalOpenAssignmentsInTheRegion" value="${totalOpenAssignmentsInTheRegion+totalOpenAssignmentsCountForTheUser}" />
																				<tr class="total">
																					<td class="floatLeft displayNone"></td>
																					<td class="padding3 bold reportCount">Total</td>
																					<td class="padding3 bold reportCount">${totalOpenAssignmentsCountForTheUser}</td>
																					<td class="padding3 bold reportCount">${totalOpenAssignments60DaysCountForTheUser}</td>
																				</tr>				
																		</tbody>
																	</table>
																</div>
															</c:forEach>	
															<input type="hidden" class="${regionName}_openAssignmentsCount" value="${totalOpenAssignmentsInTheRegion}" />											
												
												</c:otherwise>
											</c:choose>
										</div>
									</c:if>
								</div>
				
							</div>
						</div>
					</c:if>
					
				</c:forEach>
			
		</c:otherwise>
	</c:choose>
	<%-- 		<c:otherwise> --%>
<!-- 			<div class="tableReferenceDetails assignmentReportMatrixByRegion padding20"> -->
<!-- 					<span class="font20pt">No Assignments found with the criteria.</span> -->
<!-- 			</div> -->
<%-- 		</c:otherwise> --%>
	
</div>


<div class="floatLeft pT10 textAlignLeft" >
						<span class="highlightOrangeRedColor bold">* Note: Assignments Count(Highlighted in orange font) was Administratively Closed.</span>
  <br><br>
						
						<label class="highlightGreenColor BOLD">* Note: Assignments Count(Highlighted in Green font) was Closed from Needs Assignment bucket.</label>	</div>

