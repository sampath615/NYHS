<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="mergeReportMatrix" >
	<c:choose>
		<c:when test="${mergeReportsByDailyStaff != null && mergeReportsByDailyStaff != '[]' && userNamesList != '[]'}" >
			<div class="tableReferenceDetails" id="mergeReportsByDocument">
				<div  class=" width90Perc  pagebreak inlineBlock solidBorder2Black textAlignCenter assignmentReportMatrixByDocumentType marginTop20 pB20">								
					<div  class="alignRight mR30 pT10"> 									
						<a onclick="doGenerateMergeReportFile();" class="pdfDownload bold font10pt" href = "#">Download</a>
					</div>

					<div class="mT20 ">
						<span class="font15pt">Daily Count of Merges Reviewed by Staff</span><br/>
						<span class="font15pt">from ${param.fromDate} through ${param.toDate} </span><br/>  
					</div>
					
					<c:set var="totalMergedCountByUser" value="0" />
					<c:set var="totalRejectedCountByUSer" value="0" />
					
					<div class="scrollContainer">		
					<table class="table table-striped scrollTables pB20 width90Perc  expandCollpaseContent"  align="center" > 
						<thead style="font-size:12px;">
							<tr class="firstRowHeader">
								<th class="displayNone floatLeft"></th>
								<th >Staff ID</th>
								
								<c:forEach items="${userNamesList}" var="workerName">
									<th colspan="2" align="center">${workerName}</th>
								</c:forEach>
								
								<th colspan="2" align="center">Total</th>
							</tr>										
							<tr class="secondRowHeader">
								<th class="displayNone floatLeft"></th>
								<th >Date</th>
								
								<c:forEach items="${userNamesList}" var="workerName">
									<th>Merged</th>
									<th>Rejected</th>
								</c:forEach>
								
								<th>Merged</th>
								<th>Rejected</th>
							</tr>
						</thead>
						<tbody>	
							<c:forEach items="${mergeReportsByDailyStaff}" var="mergeDataByWeek">										
								<c:set var="week" value="${mergeDataByWeek.key }" /> 			
											
								<c:forEach items="${mergeDataByWeek.value}" var="mergeDataByDate">								
									<c:set var="createDate" value="${mergeDataByDate.key}" />
									<c:set var="totalMergedCountByDay" value="0" />
									<c:set var="totalRejectedCountByDay" value="0" />
					
									<tr>
										<c:set value="${mergeDataByDate.value}" var="mergeDataByUser" />		
										<c:set var="UPDATEID" value="${mergeDataByUser.key}" />
										<c:set var="mergeStatusData" value="${mergeDataByUser.value}" />
										<td class="displayNone"></td>
										<td>${createDate}</td>
								
										<c:forEach items="${userNamesList}" var="workerName">			
											<c:set var="mergeStatusData" value="${mergeDataByUser[workerName] }" />
											<c:choose>
												<c:when test="${mergeStatusData != null && mergeStatusData != '[]'}">
													<td>${mergeStatusData.ACCEPT}</td>
													<td>${mergeStatusData.REJECT}</td>
													
													<c:set var="totalMergedCountByDay" value="${totalMergedCountByDay + mergeStatusData.ACCEPT }" />
													<c:set var="totalRejectedCountByDay" value="${totalRejectedCountByDay + mergeStatusData.REJECT}" />
													
													<c:set var="totalMergedCount" value="${totalMergedCount + mergeStatusData.ACCEPT }" />
													<c:set var="totalRejectedCount" value="${totalRejectedCount + mergeStatusData.REJECT}" />														
												</c:when>
												<c:otherwise>
													<td></td>
													<td></td>		
												</c:otherwise>
											</c:choose>										
										</c:forEach>

										<td>${totalMergedCountByDay}</td>
										<td>${totalRejectedCountByDay}</td>
									</tr>								
								</c:forEach>
					 			
								<c:set var="totalMergedCountByWeek" value="0" />
								<c:set var="totalRejectedCountByWeek" value="0" />
							
								<tr class="bold">	
									<td>Weekly Total</td>
									<c:set var="weeklyCountByUser" value="${weeklyCountByWeeks[week]}" />
									<c:forEach items="${userNamesList}" var="workerName">

										<c:set var="weeklyCountByStatus" value="${weeklyCountByUser[workerName]}" />
										<td><c:out value="${weeklyCountByStatus.ACCEPT}" /></td>
										<td><c:out value="${weeklyCountByStatus.REJECT}" /></td>

										<c:set var="totalMergedCountByWeek" value="${totalMergedCountByWeek + weeklyCountByStatus.ACCEPT}" />
										<c:set var="totalRejectedCountByWeek" value="${totalRejectedCountByWeek + weeklyCountByStatus.REJECT}" />
									</c:forEach>
									
									<td>
										<c:if test="${totalMergedCountByWeek >= 0 }" >
											${totalMergedCountByWeek }
										</c:if>
									</td>
									<td>
										<c:if test="${totalRejectedCountByWeek >= 0 }" >
											${totalRejectedCountByWeek }
										</c:if>
									</td>									
								</tr>								
							</c:forEach>

							<c:set var="totalMergedCount" value="0" />
							<c:set var="totalRejectedCount" value="0" />

							<tr class="bold">	
								<td>Total For Date Range</td>
								<c:forEach items="${userNamesList}" var="workerName">
									<c:set var="totalCountByStatus" value="${totalCountByUsers[workerName]}" />
									<td>
										<c:if test="${totalCountByStatus.ACCEPT >= 0}">${totalCountByStatus.ACCEPT}</c:if>
										<c:if test="${!(totalCountByStatus.ACCEPT >= 0)}">0</c:if>
										<!--<c:out value="${totalCountByStatus.ACCEPT}" />-->
									</td>
									<td>
										<c:if test="${totalCountByStatus.REJECT >= 0}">${totalCountByStatus.REJECT}</c:if>
										<c:if test="${!(totalCountByStatus.REJECT >= 0)}">0</c:if>
										<!--<c:out value="${totalCountByStatus.REJECT}" />-->
									</td>
									
									<c:set var="totalMergedCount" value="${totalMergedCount + totalCountByStatus.ACCEPT }" />
									<c:set var="totalRejectedCount" value="${totalRejectedCount + totalCountByStatus.REJECT }" />
								</c:forEach>
								<td>
									<c:if test="${totalMergedCount >= 0 }" >
										${totalMergedCount }
									</c:if>
								</td>
								<td>
									<c:if test="${totalRejectedCount >= 0 }" >
										${totalRejectedCount }
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
