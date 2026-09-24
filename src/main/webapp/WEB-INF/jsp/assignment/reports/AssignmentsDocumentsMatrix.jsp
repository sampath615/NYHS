<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:if test="${assignmentDocuments != null && assignmentDocuments != '[]'}" >
	
	<c:set var="regionColumnClass" value="" />
	<c:choose>
		<c:when test="${param.category == 'eHars' }">
			<c:set var="columnName" value="eHARS Status" />
			<c:if test="${(param.eharsStatus != '' && param.eharsStatus != null) || param.assignmentStatus == 'WAITING IN THE WINGS'}">
				<c:set var="regionColumnClass" value="displayNone" />
			</c:if>
		</c:when>
		<c:otherwise>
			<c:set var="columnName" value="Region" />
			<c:if test="${(param.regionId != '' && param.regionId != null) || param.assignmentStatus == 'WAITING IN THE WINGS'}">
				<c:set var="regionColumnClass" value="displayNone" />
			</c:if>
		</c:otherwise>
	</c:choose>

	<form method="post" id="assignmentDocumentMatrix" action="/doh2/applinks/nyehms/web/getAssignmentDocumentsByStatusByDocumentByCategory.pdf" target="blank" name="Assignments.pdf">
		<input type="hidden" id="assignmentStatus" name="assignmentStatus" value=""/>
		<input type="hidden" id="assignmentStatusDisplayName" name="assignmentStatusDisplayName"  value=""/>
		<input type="hidden" id="documentType" name="documentType"  value=""/>
		<input type="hidden" id="fileName" name="fileName"  value=""/>
		<input type="hidden" id="fileDisplayName" name="fileDisplayName"  value=""/>
		<input type="hidden" id="fromDate" name="fromDate"  value=""/>
		<input type="hidden" id="regionId" name="regionId"  value=""/>
		<input type="hidden" id="regionName" name="regionName"  value=""/>
		<input type="hidden" id="toDate" name="toDate"  value=""/>
		<input type="hidden" id="eharsStatus" name="eharsStatus"  value=""/>
		<input type="hidden" id="category" name="category"  value=""/>
		<input type="hidden" id="documentName" name="documentName"  value=""/>
		
		<c:set var="assignmentStatusClass" value="" />
		<c:if test="${param.assignmentStatus != '' && param.assignmentStatus != null}">
			<c:set var="assignmentStatusClass" value="displayNone" />
		</c:if>
					<div id="assignmentDocuments" style="background-color:white">
						<div  class=" inlineBlock solidBorder2Black textAlignCenter assignmentReportMatrixByRegion padding25 margin25">
							<div class="mT20" >
								<span class="font15pt"> 
								<span style="color:blue">${documentDisplayName}</span> 
								<c:choose>
									<c:when test="${param.fileName != '' && param.fileName != null}">
										<span style="color:orange">${param.fileDisplayName} </span>
									</c:when>
									<c:otherwise>
										<span style="color:orange">${param.fromDate} - ${param.toDate} </span>
									</c:otherwise>
								</c:choose>
								<span style="color:red">${param.regionName}</span> <span style="color:green">${param.assignmentStatusDisplayName}</span> <span>Assignments</span>   </span>
							</div>
							<table class="table table-striped exportTables pB20 width97Perc expandCollpaseContent"  align="center" > 
								<thead style="font-size:12px;">
									<tr>
										<th class="displayNone floatLeft"></th>
										<th >STATENO</th>
										<th class="${regionColumnClass}">${columnName}</th>
										<th class="${assignmentStatusClass}">Assignment Status</th>
										<th>Worker Name</th>
									</tr>
								</thead>
								<tbody>
										<c:forEach items="${assignmentDocuments}" var="assigment">
											<c:set var="assignmentStatusDesc" value="${fn:replace(assigment.ASSIGNMENT_STATUS,'HIV', '900')}" />
										
											<tr>
												<td class="displayNone"></td>
												<td style="width:150px;"><a href="javascript:loadStatenoInPersonTabs('${assigment.STATENO}')">${assigment.STATENO}</a></td>
												<td style="width:200px;" class="${regionColumnClass}">${assigment.CATEGORY_DESCRIPTION}</td>
												<td style="width:505px;" class="${assignmentStatusClass}">${assignmentStatusDesc}</td>
												<td style="width:250px">${assigment.WORKER_NAME}</td>
											</tr>	
										</c:forEach>
								</tbody>
							</table>
							
							
							
														
						</div>
					</div>
					<div id="assignmentDocumentReportActionButtons" class="mT20 ">
							
						<div align="center">
							
							<input type="button" id="sButton" class="savePdfButton button rounded blue effect-3"  value="Save" style="pointer-events:auto"/>
							<input type="button" id="pButton" class="printButton button rounded blue effect-3"  value="Print" style="pointer-events:auto" onclick="printPage('#printDialogContent','Assignments Report')"/>
						</div>
					
					</div>
	</form>					
</c:if>
