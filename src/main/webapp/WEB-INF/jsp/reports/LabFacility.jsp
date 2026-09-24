<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<form id="labFacilityDetails" name="labFacilityDetails" >
	<div class="labFacilityInfo" style="margin: 10px 10px 10px 10px;">
		<div style="float:left; width:30%">&nbsp;&nbsp;&nbsp;</div>
		<div style="float:left; width:24%">
			<div style="float:left; width:20%">
				<font style="font-size:16px"><b>CLIA:</b></font>
			</div>
			<div id="mngLabDetailsClia" style="float:left; font-size:16px; color:rgb(20, 116, 191)">
				<b><c:out value="${physicalFacilityDetails.CLIA}" /></b>
			</div>
		</div>
		<div style="float:left; width:24%">
			<div style="float:left; width:20%">
				<font style="font-size:16px"><b>PFI:</b></font>
			</div>
			<div  id="mngLabDetailsPfi" style="float:left; font-size:16px; color:rgb(20, 116, 191)">
				<b><c:out value="${physicalFacilityDetails.PFI}" /></b>
			</div>
		</div>
		<div style="float:left; width:20%">&nbsp;&nbsp;&nbsp;</div>
	</div>	
	
	<div class="LabData">
	    <div class="labReportTitle"><b>Lab Information:</b></div>
		<div class="labFacilityName" style="margin: 0px 10px 10px 10px;">
			<div class="labInfoDiv3">
				<div class="standardInputDiv">
					<div class="labReportLabel1">
						<font class="labReportTitle">Lab Name:</font>
					</div>
					<div class="labReportInput">
						<input id="mngLabDetailsLabName" maxlength="100" class="standardInputlabText1 required" type="text" name="labName" title="Lab name" value="<c:out value='${physicalFacilityDetails.LAB_NAME}' />" />
					</div>
				</div>
			</div>	
			<div style="float:left; width:50%">
				<div class="placeHolder" style="float:left; width:40%">
					<div class="standardInputDiv">
						<div class="labReportLabel2">
							<font class="labReportTitle">Flag Active:</font>
						</div>
						<div class="labReportInput2">
								
						    	<span>
								<select id="searchFlag" class="searchOptions standardInputMedText1" style="width:55px" name="flagStatus">
									<c:choose>
						        		<c:when test="${physicalFacilityDetails.FLAG_ACTIVE == 'Y'}" >
						    				<option value="Y" selected>Yes</option>
											<option value="N">No</option>	
						    			</c:when>
						    			<c:otherwise>
						    				<option value="Y">Yes</option>
											<option value="N" selected>No</option>
						    			</c:otherwise>    
					    			</c:choose>
									
								</select>
							</span>
						</div>
					</div>
				</div>
				<div class="placeHolder" style="float:left; width:60%">
					<div class="standardInputDiv">
						<div class="labReportLabel2">
							<font class="labReportTitle">Approval Status:</font>
						</div>
						<div class="labReportInput2">
						    <span>
								<select id="searchApproval" class="searchOptions standardInputMedText1" style="width:260px" name="approvalStatus">
									<option value="">-- Select --</option>
							        <c:forEach items="${labApprovalStatusMap}" var="status">
							        	<c:choose>
							        	<c:when test="${physicalFacilityDetails.APPROVAL_STATUS_CODE != status.key}" >
							    			<option value="${status.key}">${status.value}</option>
							    		</c:when>
							    		<c:otherwise>
							    			<option value="${status.key}" selected>${status.value}</option>
							    		</c:otherwise>    
							    		</c:choose>
							        </c:forEach>								    
								</select>
							</span>
						</div>
					</div>
				</div>
			</div>
			<br>
			<div class="labInfoDiv3">
				<div class="standardInputDiv">
					<div class="labReportLabel1">
						<span class="labReportTitle">LAFT Staff Assigned:</span>
					</div>
					<div class="labReportInput">
						<input id="mngLaftStaffAssigned" maxlength="100" class="standardInputlabText1" type="text" name="mngLaftStaffAssigned" value="<c:out value='${physicalFacilityDetails.LAFT_STAFF}' />" title="Laft Staff" />
					</div>
				</div>
			</div>
			<div class="labInfoDiv3">
				<div class="placeHolder" style="float:left; width:40%">
					<div class="standardInputDiv">
						<div class="labReportLabel2">
							<font class="labReportTitle">NYCLab: </font>
						</div>
						<div class="labReportInput2">
								
						    	<span>
								<select id="flagServersNYC" class="standardInputMedText1" style="width:55px" name="nycLab">
									<c:choose>
										<c:when test="${physicalFacilityDetails.FLAG_SERVES_NYC == 'Y'}" >
						    				<option value="Y" selected>Yes</option>
											<option value="N">No</option>	
						    			</c:when>
						    			<c:otherwise>
						    				<option value="Y">Yes</option>
											<option value="N" selected>No</option>
						    			</c:otherwise>    
					    			</c:choose>
									
								</select>
							</span>
						</div>
					</div>
				</div>
				<div class="placeHolder" style="float:left; width:60%">
					<div class="standardInputDiv">
						<div class="labReportLabel2">
							<font class="labReportTitle">Blood and Tissue Bank: </font>
						</div>
						<div class="labReportInput2">							
						    <span>
								<select id="flagBloodBank" class="standardInputMedText1 required" style="width:55px" name="bloodBank">
									<c:choose>
										<c:when test="${physicalFacilityDetails.FLAG_BLOOD_BANK == 'Y'}" >
						    				<option value="Y" selected>Yes</option>
											<option value="N">No</option>	
						    			</c:when>
						    			<c:otherwise>
						    				<option value="Y">Yes</option>
											<option value="N" selected>No</option>
						    			</c:otherwise>    
					    			</c:choose>
									
								</select>
							</span>
						</div>
					</div>
				</div>
			</div>
			<div class="labInfoDiv3">
				<div class="standardInputDiv">
					<div class="labReportLabel1">
						<span class="labReportTitle">Refers DTA Testing to:</span>
					</div>
					<div class="labReportInput">
							<div>
										<span>
											<select id="refersDTATestingTo" class="searchOptions standardInputMedText1" style="width:525px" name="refersDTATestingTo">
												<option value="">-- Select --</option>
												<c:forEach items="${refersDTATestingToList}" var="curData">
													<c:choose>
														<c:when test="${physicalFacilityDetails.REFERS_DTA_TESTING_TO ne curData.labName}">
															<option value="${curData.labName}">${curData.labDesc}</option>
														</c:when>
														<c:otherwise>
															<option value="${curData.labName}" selected>${curData.labDesc}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</span>
							</div>						
					</div>
				</div>

			</div>
			<div class="labInfoDiv3">
				<div class="placeHolder" style="float:left; width:40%">
					<div class="standardInputDiv">
						<div class="labReportLabel2">
							<font class="labReportTitle">Low Volume: </font>
						</div>
						<div class="labReportInput2">
								
						    	<span>
								<select id="lowVolume" class="standardInputMedText1" style="width:55px" name="lowVolume">
									<c:choose>
										<c:when test="${physicalFacilityDetails.LOW_VOLUME == 'Y'}" >
						    				<option value="Y" selected>Yes</option>
											<option value="N">No</option>	
						    			</c:when>
						    			<c:otherwise>
						    				<option value="Y">Yes</option>
											<option value="N" selected>No</option>
						    			</c:otherwise>    
					    			</c:choose>
									
								</select>
							</span>
						</div>
					</div>
				</div>
				<div class="placeHolder" style="float:left; width:35%">
					<div class="standardInputDiv">
						<div class="labReportLabel2">
							<font class="labReportTitle">Physician Office Lab: </font>
						</div>
						<div class="labReportInput2">							
						    <span>
								<select id="physicianOfficeLab" class="standardInputMedText1 required" style="width:55px" name="physicianOfficeLab">
									<c:choose>
										<c:when test="${physicalFacilityDetails.PHY_OFF_LAB == 'Y'}" >
						    				<option value="Y" selected>Yes</option>
											<option value="N">No</option>	
						    			</c:when>
						    			<c:otherwise>
						    				<option value="Y">Yes</option>
											<option value="N" selected>No</option>
						    			</c:otherwise>    
					    			</c:choose>
									
								</select>
							</span>
						</div>
					</div>
				</div>			
				<div class="placeHolder" style="float:left; width:25%">
					<div class="standardInputDiv">
						<div class="labReportLabel2">
							<font class="labReportTitle">Perform DTA: </font>
						</div>
						<div class="labReportInput2">							
						    <span>
								<select id="performsDTA" class="standardInputMedText1 required" style="width:55px" name=performsDTA>
									<c:choose>
										<c:when test="${physicalFacilityDetails.PERFORMS_DTA == 'Y'}" >
						    				<option value="Y" selected>Yes</option>
											<option value="N">No</option>	
						    			</c:when>
						    			<c:otherwise>
						    				<option value="Y">Yes</option>
											<option value="N" selected>No</option>
						    			</c:otherwise>    
					    			</c:choose>											
								</select>
							</span>
						</div>
					</div>
				</div>			
		</div>
		<div class="labReportTitle"><b>Address Information:</b></div>
		<div style="float:left; width:99%; margin: 10px 10px 10px 10px;">
			<div class="labReportLabel1" style="float:left; width:5%">
				<font class="labReportTitle">Address:</font>
			</div>
			<div class="labReportInput" style="float:left; width:35%">
				<input id="mngLabDetailsAddress" maxLength="200" class=" standardInputlabText2 required"type="text" name="address" value="<c:out value='${physicalFacilityDetails.ADDRESS}' />" />
			</div>

			<div class="labReportLabel" style="float:left; width:3%">
				<font class="labReportTitle">City:</font>
			</div>
			<div class="labReportInput" style="float:left; width:14%">
				<input id="mngLabDetailsCity" maxlength="120" class="formInputText2 required" type="text" name="city" value="<c:out value='${physicalFacilityDetails.CITY}' />" />
			</div>
			
			<div class="labReportLabel1" style="float:left; width:3%">
				<font class="labReportTitle">State:</font>
			</div>
			<div class="labReportInput" style="float:left; width:7%">
				<span>
					<spring:message code="title.state" var="state_title"/>
					<select id="mngLabDetailsState"  name="state" class=" searchOptions standardInputMedText1 required"  title="${state_title}" style="width:60px" >
					        <option value="">--- Select ---</option>
					        <c:forEach items="${sessionScope.stateMap}" var="state">
					        	<c:choose>
					        	<c:when test="${physicalFacilityDetails.STATE != state.value}" >
					    			<option value="${state.value}">${state.key}</option>
					    		</c:when>
					    		<c:otherwise>
					    			<option value="${state.value}" selected>${state.key}</option>
					    		</c:otherwise>    
					    		</c:choose>
					        </c:forEach>
						
				    	</select>
				</span>
			</div>

			<div class="labReportLabel1" style="float:left; width:5%">
				<font class="labReportTitle">Zip Code:</font>
			</div>
			<div class="labReportInput" style="float:left; width:8%">
				<input id="mngLabDetailsZip" maxlength="50" class="formInputText1 required" type="text" name="zipCode" value="<c:out value='${physicalFacilityDetails.ZIP}' />" />
			</div>

			<div class="labReportLabel1" style="float:left; width:4%">
				<font class="labReportTitle">Country:</font>
			</div>
			<div class="labReportInput" style="float:left; width:8%">
				<input id="mngLabDetailsCountry" maxlength="100" class="formInputText2 required" type="text" name="country " value="<c:out value='${physicalFacilityDetails.COUNTRY}' />" />
			</div>
		</div>	
	</div>
   <div class="contactNote">
   		<label class="requiredInd">Note:</label> 
   		<label> There are some unsaved changes on this page, Please click on below Save button to Save them.</label>
   </div>
</form>
<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
	 <div class="button-row">
         <a class="actionButtons a_demo_search a_demo_save" onclick="saveLabDetailsInfo()" style="color: #FFFFFF;"> Save </a>
		 <a class="actionButtons a_demo_search a_demo_cancel" onclick="$('#manageLabDetails').hide();$('#searchManage').click();" style="color: #FFFFFF; margin-left:25px;"> Cancel </a>
		 <a class="actionButtons a_demo_search a_demo_save" onclick="addAndEditCliaFacilities('edit')" style="color: #FFFFFF; margin-left:25px;"> Change CLIA </a>
     </div>

     <script type="text/javascript">
      	var pfiContactDetails = ${physicalFacilityDetails.pfiContact};
     	var submissionDataTypeList = ${physicalFacilityDetails.submissionDataTypeList};
     	var reportingStatusDetails = ${reportingStatusDetails};
     	var currentWorkerId = ${currentWorkerId};
     	var pfiContactTypeDetails = ${pfiContactTypeDetails};
     </script>