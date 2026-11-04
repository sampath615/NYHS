<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="labDetailsWidgetModal"  >
		<div style="padding-top:3px; padding-bottom:10px;">
			<div class="standardInputDiv">
				<div style="float: left; width: 37%;">
					<font class="standardInputTitle">Lab Name</font>
				</div>
				<div style="float:left">
					<select id="acrfLabs_Name" class="standardInputText" title="Lab Name">
						<option value="" >--select--</option>
						<c:forEach items="${labNames}" var="labDetails">
							<option value="${labDetails.CLIA}">${labDetails.LAB_NAME}</option>
						</c:forEach>
						<option value="RAP_PROV" >Rapid test by provider</option>
						<option value="Unknown" >Unknown</option>
					</select>
				</div>
			</div>
		</div>
		
		<div style="padding-top:3px; padding-bottom:10px;">
			<div class="standardInputDiv">
				<div style="float: left; width: 37%;">
					<font class="standardInputTitle">Facility Name</font>
				</div>
				<div style="float:left">
					<select id="acrfLabs_FacilityName" class="standardInputText" title="Facility Name">
						<option value="" >--select--</option>
						<c:forEach items="${subFacilities}" var="facility">
							<option value="${facility.facility_uid}">${facility.facility_name}</option>
						</c:forEach>
						<option value="Unknown" >Unknown</option>
					</select>
				</div>
			</div>
		</div>
		<div class="container" style="width: 100%; border-spacing: 3px; padding: 3px; border: thin solid lightblue; float: left;">
			<div style="padding-top:3px; padding-bottom:10px;">
				<div class="standardInputDiv">
					<div style="float: left; width: 37%;">
						<font class="standardInputTitle">Provider First name</font>
					</div>
					<div style="float:left">
						<input type="text" size="35" maxlength="80" id="acrfLabs_ProvFname" class="standardInputText" title="Provider First Name"/>
					</div>
				</div>
			</div>
			<div style="padding-top:3px; padding-bottom:10px;">
				<div class="standardInputDiv">
					<div style="float: left; width: 37%;">
						<font class="standardInputTitle">Provider Middle name</font>
					</div>
					<div style="float:left">
						<input type="text" size="35" maxlength="80" id="acrfLabs_ProvMname" class="standardInputText" title="Provider Middle Name"/>
					</div>
				</div>
			</div>
			<div style="padding-top:3px; padding-bottom:10px;">
				<div class="standardInputDiv">
					<div style="float: left; width: 37%;">
						<font class="standardInputTitle">Provider Last name</font>
					</div>
					<div style="float:left">
						<input type="text" size="35" maxlength="80" id="acrfLabs_ProvLname" class="standardInputText" title="Provider Last Name"/>
					</div>
				</div>
			</div>
			<div style="padding-top:3px; padding-bottom:10px;">
				<div class="standardInputDiv">
					<div style="float: left; width: 37%;">
						<font class="standardInputTitle">Provider Suffix</font>
					</div>
					<div style="float:left">
						<input type="text" size="35" maxlength="10" id="acrfLabs_ProvSuffix" class="standardInputText" title="Provider Suffix"/>
					</div>
				</div>
			</div>
		</div>
		
		<div style="clear: both;"></div>
		<div style="margin-top: 10px;">
			<ul id="menu-css">
				<li><a id="addressSubmitButton" href="javascript:setAcrfLabFields(this);">Submit</a></li>
				<li><a href="javascript:clearAcrfLabFields(this);">Reset</a></li>
			</ul>
		</div>
		
		
	</div>