<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="clericalReportMatrix">

	<c:choose>
		<c:when
			test="${clericalReportsByDailyStaff != null && userTotalCountByName != null}">
			<div class="tableRefernceDetails" id="clericalReportsByStaff">
				<div
					class=" width75Perc pagebreak inlineBlock solidBorder2Black textAlignCenter assignmentReportMatrixByDocumentType marginTop20 pB20">
					<div class="alignRight mR30 pT10">
						<a onclick="doGenerateClericalReportFile();"
							class="pdfDownload bold font10pt" href="#">Download</a>
					</div>
					<!-- <div class="scrollContainer"> -->
					<table
						class="table table-striped scrollTables pB20 width90Perc  expandCollpaseContent"
						style="margin-top: 30px;" align="center">
						<thead style="font-size: 12px;">
							<tr class="firstRowHeader">
								<th class="displayNone floatLeft"></th>
								<th>Staff ID</th>
								<c:forEach items="${userTotalCountByName}"
									var="workerNameAndCount">
									<c:set var="workerName" value="${workerNameAndCount.key}" />

									<th colspan="2" align="center">${workerName}</th>
								</c:forEach>
								<th colspan="2" align="center">Total</th>
							</tr>
							<tr class="secondRowHeader">
								<th class="displayNone floatLeft"></th>
								<th>Date</th>

								<c:forEach items="${userTotalCountByName}"
									var="workerNameAndCount">
									<c:set var="workerName" value="${workerNameAndCount.key}" />
									<th>Match</th>
									<th>Non-Match</th>
								</c:forEach>

								<th>Match</th>
								<th>Non-Match</th>
							</tr>

						</thead>
						<tbody>
							<c:set var="totalMatchCount" value="0" />
							<c:set var="totalNonMatchCount" value="0" />

							<c:forEach items="${clericalReportsByDailyStaff}" var="userData">
								<tr>
									<c:set var="createDate" value="${userData.key}" />
									<c:set var="userMatchSummary" value="${userData.value}" />

									<c:set var="totalMatchByDay" value="0" />
									<c:set var="totalNonMatchByDay" value="0" />

									<td>${createDate}</td>

									<c:forEach items="${userTotalCountByName}"
										var="workerNameAndCount">
										<c:set var="workerName" value="${workerNameAndCount.key}" />
										<%-- <c:forEach items="${userNamesList}" var="workerName"> --%>


										<c:set var="summaryByWorker"
											value="${userMatchSummary[workerName]}" />

										<td><c:out value="${summaryByWorker['Match']}"
												default="0" /></td>
										<td><c:out value="${summaryByWorker['NonMatch']}"
												default="0" /></td>

										<c:set var="totalMatchByDay"
											value="${totalMatchByDay+summaryByWorker['Match']}" />
										<c:set var="totalNonMatchByDay"
											value="${totalNonMatchByDay+summaryByWorker['NonMatch']}" />

										<c:set var="totalMatchCount"
											value="${totalMatchCount + summaryByWorker['Match'] }" />
										<c:set var="totalNonMatchCount"
											value="${totalNonMatchCount + summaryByWorker['NonMatch']}" />

									</c:forEach>
									<c:choose>
										<c:when test="${totalMatchByDay != null}">
											<td>${totalMatchByDay}</td>
										</c:when>
										<c:otherwise>
											<td>0</td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${totalNonMatchByDay != null}">
											<td>${totalNonMatchByDay}</td>
										</c:when>
										<c:otherwise>
											<td>0</td>
										</c:otherwise>
									</c:choose>

								</tr>
							</c:forEach>


							<tr class="bold">
								<td>Total for Date Range</td>
								<c:forEach items="${userTotalCountByName}"
									var="workerNameAndCount">
									<c:set var="workerName" value="${workerNameAndCount.key}" />
									<c:set var="totalCountSummary"
										value="${workerNameAndCount.value}" />
									<td>${totalCountSummary.Match}</td>
									<td>${totalCountSummary.NonMatch}</td>
								</c:forEach>
								<td><c:if test="${totalMatchCount >= 0}">${totalMatchCount}</c:if></td>
								<td><c:if test="${totalNonMatchCount >=0}">${totalNonMatchCount}</c:if></td>

							</tr>
						</tbody>
					</table>

				</div>

			</div>

		</c:when>
	</c:choose>
</div>