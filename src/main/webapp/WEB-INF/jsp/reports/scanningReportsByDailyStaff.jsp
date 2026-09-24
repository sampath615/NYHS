<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="scanningReportMatrix">

<c:choose>
	<c:when test="${scanningReportsByDailyStaff != null && totalCountByUser != null}">
	<div  class=" width75Perc pagebreak inlineBlock solidBorder2Black textAlignCenter assignmentReportMatrixByDocumentType marginTop20 pB20">	
			<div  class="alignRight mR30 pT10">		
						<a onclick="doGenerateScanningReportFile();" class="pdfDownload bold font10pt" href = "#">Download</a>
					</div>	
					
		<div class="tableRefernceDetails" id="">
				<table class="table table-striped scrollTables pB20 width90Perc  expandCollpaseContent" style="margin-top:30px;" align="center" > 
					<thead style="font-size:12px;">
					<tr class="firstRowHeader">
						<th class="displayNone floatLeft"></th>
						<th>Staff ID</th>
						<c:forEach items="${totalCountByUser}" var="workerNameAndCount">
							<c:set var="workerName" value="${workerNameAndCount.key}"></c:set>
							<th colspan="1" align="center">${workerName}</th>
						</c:forEach>
						<th colspan="2" align="center">Total</th>
					
						<tr class="secondRowHeader">
							<th class="displayNone floatLeft"></th>
							<th>Date</th>
						</tr>
					</thead>
					<tbody>
					  <c:set var="totalCountByDayRange" value="0" />
					
						<c:forEach items="${scanningReportsByDailyStaff}" var="userData">
								<c:set var="userDate" value="${userData.key}"></c:set>
								<c:set var="userDataSummary" value="${userData.value}"></c:set>
							 <tr>
							        <c:set var="totalCountByDay" value="0" />
							      									
							 
							 <td>${userDate}</td>
							 
							
						 <c:forEach items="${totalCountByUser}" var="workerNameAndCount">
										<c:set var="workerName" value="${workerNameAndCount.key}" />
										<c:set var="workerCount" value="${workerNameAndCount.value}" />
										
									<c:set var="summaryByWorker"
											value="${userDataSummary[workerName]}" />
									
											
									<td><c:out value="${summaryByWorker['count']}" default="0"/></td>
									
									<c:set var="totalCountByDay" value="${totalCountByDay+summaryByWorker['count']}"></c:set>
									<c:set var="totalCountByDayRange" value="${totalCountByDayRange+summaryByWorker['count']}"></c:set>
							</c:forEach>
							 <c:choose>
							 	<c:when test="${totalCountByDay != null}">
							 	<td>${totalCountByDay}</td>
							 	</c:when>
							 	<c:otherwise>
							 	<td>0</td>
							 	</c:otherwise>
							 </c:choose>
							
							 
							 </tr>
							
						</c:forEach>
					
						<tr class="bold">
						<td>Total for Date Range</td>
						<c:forEach var="workerNameAndCount" items="${totalCountByUser}">
								<c:set var="workerCount" value="${workerNameAndCount.value}"></c:set>
								<td>${workerCount.totalCount}</td>
						</c:forEach>
						<td><c:if test="${totalCountByDayRange >= 0}">${totalCountByDayRange}</c:if></td>
						
						</tr>
					</tbody>
				</table>
		
		</div>
	</div>
	</c:when>
	
</c:choose>

</div>