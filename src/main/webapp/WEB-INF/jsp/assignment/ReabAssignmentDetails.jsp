<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Reabstraction Assignment details Div -->
<div id="assignmentDetailsDiv" style="height: 200px;">

		<div class="divHeadingCls">Reabstraction Details </div>
		
		<div class="personDetailsDiv" id="currentReabAssignmentDetails">
			<div class="assignmentDetailsRow">
				<div class="assignmentDetailsRowItem">
					<div class="floatLeft width45Perc"><font class="standardInputTitle">Worker</font></div>        
					<div class="floatLeft width50Perc"><form:input path="reabWorkerName"  class="readonly" readonly="true" tabindex="-1" title="Reabstraction Worker"/></div>	 
				</div>
				
				<div class="assignmentDetailsRowItem">
					<div class="floatLeft width45Perc"><font class="standardInputTitle">Assigned By</font></div>        
					<div class="floatLeft width50Perc"><form:input path="reabSupervisorName"   class="readonly" readonly="true" tabindex="-1" title="Reabstraction Supervisor"/></div>	 
				</div>
				
				<div class="assignmentDetailsRowItem">
					<div class="floatLeft width45Perc"><font class="standardInputTitle">Assignment Date</font></div>        
					<div class="floatLeft width50Perc"><form:input path="reabAssignedDate"  class="readonly" readonly="true" tabindex="-1" title="Reabstraction Assignment On Date"/></div>	 
				</div>				
			</div>
			
			<div class="assignmentDetailsRow">
				
				<div style="display: inline-block; width: 35%;">
					<div class="floatLeft" style="width:31%"><font class="standardInputTitle">Status</font></div>        
					<div class="floatLeft width50Perc"><form:input path="reabCurrentStatusDesc" class="readonly" style="width:344px" readonly="true" tabindex="-1" title="Reabstraction Assignment Status"/></div>	 
				</div>		

				<div class="assignmentDetailsRowItem">
					<div class="floatLeft width45Perc"><font class="standardInputTitle">Complete Date</font></div>        
					<div class="floatLeft width50Perc"><form:input path="reabCrfCompletedDate"  class="readonly" readonly="true" tabindex="-1" title="Reabstraction Complete Date"/></div>	 
				</div>
				
			</div>
			<div class="assignmentDetailsRow">
			
				<div style="display: inline-block; width: 35%;">
									<div class="floatLeft" style="width:31%"><font class="standardInputTitle">Activity</font></div>        
									<div class="floatLeft width50Perc">
												<form:select path="reabSelectedActivity"  class="readonly" cssClass="required " title="Reabstraction Activity" onchange="displayClosureCatregory(this.value)"> 
													<form:option value=""> -- Select --</form:option>
													<c:forEach items="${reabActivityMap}" var="reabActivity">
														<form:option value="${reabActivity.code}">${reabActivity.desc}</form:option> 											
													</c:forEach>
		
												</form:select>
									</div>	 
				</div>
				
				<div id="reabAssignmentClosureStatusDiv" style="display: inline-block; width: 45%;">
									<div class="floatLeft" style="width:22.5%;"><font class="standardInputTitle" style="padding-left:14px;">Closure Category</font></div>        
									<div class="floatLeft width50Perc" style="padding-left:12px;">
												<form:select path="reabClosureCategory" class="readonly" cssClass="required " title="Closure Category"> 
													<form:option value=""> -- Select --</form:option>
													<c:forEach items="${reabClosureCategoryMap}" var="closureCategoryDetails">
														<form:option value="${closureCategoryDetails.code}" cssClass="${closureCategoryDetails.needAcrf}" >${closureCategoryDetails.desc}</form:option> 											
													</c:forEach>
												</form:select>
												
									</div>	 
				</div>					
				
				<div style="display: inline-block; width: 15%;">
					<div class="floatLeft width75Perc">
						<a class="enabledColor a_continue fadedCancelButton" style="color: #FFFFFF;display:inline-block;" href="#" onclick="javascript:saveReabActivity()" id="saveReabAssignmentActivity" >Save</a>
						</div>	 
				</div>
			</div>
			<br>

		</div>
	
	</div>
