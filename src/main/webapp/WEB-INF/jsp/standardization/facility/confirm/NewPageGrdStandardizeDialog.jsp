<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="stdGrdstandardizeDialog" title="Grand Confirmation">
	<p id="standardiseGrdMsg"><span style="float: left; margin: 0 7px 20px 0;"></span><strong>Are you sure you want to Standardize?</strong></p>
 	<div class="standardizationDetailsDiv" id="standardizationInfoDiv">
		<div class="leftDiv" title="Standardization Facility">
		  <div class='dataDivInConfirmationWindow'>
		    <div class="standardInputDiv">
				<div style="float: left">
					<font class="standardInputTitle"><spring:message code="label.facilityName" /></font>
				</div>
				<div style="float: right">
				    <font  id="grdstdfacilityName" class="standardInputTitle"></font>
				</div>
			</div>
		    <div class="standardInputDiv">
				<div style="float: left">
					<font class="standardInputTitle"><spring:message code="label.department" /></font>
				</div>
				<div style="float: right">
					<font  id="grdstddepartment" class="standardInputTitle"></font>
				</div>
			</div>
		    <div class="standardInputDiv">
				<div style="float: left">
					<font class="standardInputTitle"><spring:message code="label.facilityType" /></font>
				</div>
				<div style="float: right">
				   <font  id="grdstdfacilityCode" class="standardInputTitle"></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div style="float: left">
					<font class="standardInputTitle"><spring:message code="label.streetAddress" /></font>
				</div>
				<div style="float: right">
				    <font  id="grdstdstreetAddress" class="standardInputTitle"></font>
				</div>
			</div>
		    <div class="standardInputDiv">
				<div style="float: left">
					<font class="standardInputTitle"><spring:message code="label.city" /></font>
				</div>
				<div style="float: right">
				    <font  id="grdstdcity" class="standardInputTitle"></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div style="float: left">
					<font class="standardInputTitle"><spring:message code="label.state" /></font>
				</div>
				<div style="float: right"> 
					 <font  id="grdstdstate" class="standardInputTitle"></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div style="float: left">
					<font class="standardInputTitle"><spring:message code="label.zip" /></font>
				</div>
				<div style="float: right">
					<font  id="grdstdzip" class="standardInputTitle"></font>
				</div>
			</div>
			<div class="standardInputDiv">
				<div style="float: left">
					<font class="standardInputTitle"><spring:message code="label.phoneNumber" /></font>
				</div>
				<div style="float: right">
				    <font  id="grdstdphoneNumber" class="standardInputTitle"></font>
				</div>
			</div>
		  </div>	
       </div>
		
		<!--Middle Div-->
		<div style="float: left;">
			<div class="standardInputDiv">&nbsp;</div>
			<div class="standardInputDiv">&nbsp;</div>
			<div class="standardInputDiv">&nbsp;</div>
			<div class="standardInputDiv">TO</div>
		</div>

		<div class="rightDiv" title="Standardization Facility">
			<div class='dataDivInConfirmationWindow'>
				<div class="standardInputDiv">
					<div style="float: left">
						<font class="standardInputTitle"><spring:message code="label.facilityName" /></font>
					</div>
					<div style="float: right">
						<font id="grandfacilityName" class="standardInputTitle"></font>
					</div>
				</div>
				<div class="standardInputDiv">
					<div style="float: left">
						<font class="standardInputTitle"><spring:message code="label.facilityType" /></font>
					</div>
					<div style="float: right">
						<font id="grandfacilityTypeCode" class="standardInputTitle"></font>
					</div>
				</div>
				<div class="standardInputDiv">&nbsp;</div>
			    <div class="standardInputDiv">&nbsp;</div>
			    <div class="standardInputDiv">&nbsp;</div>
			    <div class="standardInputDiv">&nbsp;</div>
			    <div class="standardInputDiv">&nbsp;</div>
			    <div class="standardInputDiv">&nbsp;</div>
			    <div class="standardInputDiv">&nbsp;</div>
			</div>
		</div>
		<div style="clear: both;">
			<br />
		</div>
		<!-- dummy div -->
		<div class="button-row">
			<a id ="linkGrdButton" class="a_demo_search" style ="color:#FFFFFF; margin-left: 5px;" onclick="SubmitGrdStandardizeResults();"> Link </a>
			<a id ="addLinkGrdButton" class="a_demo_search" style ="color:#FFFFFF; margin-left: 5px;" onclick="SubmitNewStandardizeResults();"> Add and Link </a>
			<a class="a_demo_search" style ="color:#FFFFFF; margin-left: 5px;" onclick="closeStdDialog(this);"> Cancel </a>
			
			
		</div>
		<div>&nbsp;&nbsp;</div>
	</div>
</div>
