<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:if test="${assignmentDocuments != null && assignmentDocuments != '[]'}" >
	
	<form method="post" id="assignmentDocumentMatrix" action="/doh7/applinks/nyehms/hims/web/getAssignmentDocumentsByStatusByDocumentByCategory.pdf" target="blank" name="Assignments.pdf">
		<input type="hidden" id="assignmentStatus" name="assignmentStatus" value=""/>
		<input type="hidden" id="assignmentStatusDisplayName" name="assignmentStatusDisplayName"  value=""/>
		<input type="hidden" id="documentType" name="documentType"  value=""/>
		<input type="hidden" id="regionId" name="regionId"  value=""/>
		<input type="hidden" id="regionName" name="regionName"  value=""/>
		
	
					<div id="assignmentDocuments" style="background-color:white">
						<div  class=" inlineBlock solidBorder2Black textAlignCenter assignmentReportMatrixByRegion padding25 margin25">
							<div class="mT20" >
								<span class="font15pt"> 
								<span style="color:blue">${documentDisplayName}</span> 
								<span style="color:red">${param.regionName}</span> <span style="color:green">${param.assignmentStatusDisplayName}</span> <span>Assignments</span>   </span>
							</div>
							<table class="table table-striped exportTables pB20 width97Perc expandCollpaseContent"  align="center" > 
								<thead style="font-size:12px;">
									<tr>
										<th class="displayNone floatLeft"></th>
										<th >STATENO</th>
										<th>Worker Name</th>
										<th>Date</th>
									</tr>
								</thead>
								<tbody>
										<c:forEach items="${assignmentDocuments}" var="assigment">
											<tr>
												<td class="displayNone"></td>
												<td style="width:150px;"><a href="javascript:loadStatenoInPersonTabs('${assigment.STATENO}')">${assigment.STATENO}</a></td>
												<td style="width:200px;" >${assigment.AHI_UPDATE_UID}</td>
												<td style="width:250px">${assigment.AHI_UPDATE_DATE}</td>
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
