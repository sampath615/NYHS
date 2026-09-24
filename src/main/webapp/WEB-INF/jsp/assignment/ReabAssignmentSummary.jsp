<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));
%>

<form:form modelAttribute="stdLabDocVO" id="stdLabDocVO" style="margin: 0 auto; width:70%; min-width: 970px;">
	<div id="reabAsmentSummary" style="background-color: transparent">
		<div class="headingCls marginBottom10 alignCenter">
				Field Staff and Reabstraction CRF comparision for STATENO : ${stateNo}
		</div>

		<c:if test="${assignmentDetailsVO.reabCurrentStatus == 'REAB_CR'}">
<!-- 			<div class="reabSecHeader" style="color:blue">Reabstraction Review Summary</div> -->
			<table class="table table-striped exportTables width97Perc expandCollpaseContent"> 
				<thead style="font-size:14px;">
					<tr>
						<th>Review By</th>
						<th>Review Date</th>
<!-- 						<th>Review Comments</th> -->
					</tr>
				</thead>
				<tr>
					<td>${assignmentDetailsVO.reabReviewBy}</td>
					<td>${assignmentDetailsVO.reabReviewDate}</td>
					<%-- <td>${assignmentDetailsVO.reabReviewComments}</td> --%>
				</tr>
			</table>
		</c:if>
			
		<table class="table table-striped exportTables width97Perc expandCollpaseContent"> 
				<thead style="font-size:14px;">
					<tr>
						<th>Field Staff Closure Category</th>
						<th>Reabstraction Closure Category</th>
					</tr>
				</thead>
				<c:if test="${closureCategory.FS_CLOSURE_CATEGORY == closureCategory.REAB_CLOSURE_CATEGORY}">
				   <tr>
				</c:if>
				<c:if test="${closureCategory.FS_CLOSURE_CATEGORY != closureCategory.REAB_CLOSURE_CATEGORY}">
				   <tr class='crfDiffElement'>
				</c:if>						
						<td>${closureCategory.FS_CLOSURE_CATEGORY}</td>
						<td>${closureCategory.REAB_CLOSURE_CATEGORY}</td>
					</tr>
		</table>	
		
		<div class="reabSecHeader">Patient Identification</div>
		<table class="table table-striped exportTables width97Perc expandCollpaseContent" > 
			<thead style="font-size:14px;">
				<tr>
					<th>Element Name</th>
					<th>Field Staff Value</th>
					<th>Reabstraction Value</th>
				</tr>
			</thead>
			<tr id="FNAME">
				<td>First Name</td>
				<td>${fsRawCrfDoc.fname}</td>		
				<td>${reabRawCrfDoc.fname}</td>
			</tr>
			<tr id="LNAME">
				<td>Last Name</td>		
				<td>${fsRawCrfDoc.lname}</td>		
				<td>${reabRawCrfDoc.lname}</td>
			</tr>
			<tr id="ADDRESS_TYPE_CODE">
				<td>Address Type</td>		
				<td>${fsRawCrfDoc.addressTypeCode}</td>		
				<td>${reabRawCrfDoc.addressTypeCode}</td>
			</tr>
			<tr id="ADDRESS">
				<td>Current Address, Street</td>		
				<td>${fsRawCrfDoc.address}</td>		
				<td>${reabRawCrfDoc.address}</td>
			</tr>
			<tr id=ADDRESS_DATE>
				<td>Address Date</td>		
				<td>${fsRawCrfDoc.patientAddressDate}</td>		
				<td>${reabRawCrfDoc.patientAddressDate}</td>
			</tr>
			<tr id="TELEPHONE">
				<td>Phone Number</td>		
				<td>${fsRawCrfDoc.telephone}</td>		
				<td>${reabRawCrfDoc.telephone}</td>
			</tr>
			<tr id="CITY">
				<td>City</td>		
				<td>${fsRawCrfDoc.city}</td>		
				<td>${reabRawCrfDoc.city}</td>
			</tr>
			<tr id="COUNTY_FIPS">
				<td>County</td>		
				<td>${fsRawCrfDoc.countyFips}</td>		
				<td>${reabRawCrfDoc.countyFips}</td>
			</tr>
			<tr id="STATE">
				<td>State</td>		
				<td>${fsRawCrfDoc.state}</td>		
				<td>${reabRawCrfDoc.state}</td>
			</tr>
			<tr id="COUNTRY">
				<td>Country</td>		
				<td>${fsRawCrfDoc.country}</td>		
				<td>${reabRawCrfDoc.country}</td>
			</tr>
			<tr id="ZIPCODE">
				<td>Zip</td>		
				<td>${fsRawCrfDoc.zipcode}</td>		
				<td>${reabRawCrfDoc.zipcode}</td>
			</tr>
		</table>			

		<div class="reabSecHeader">Health Department Use Only</div>
		<table class="table table-striped exportTables width97Perc expandCollpaseContent"> 
			<thead style="font-size:14px;">
				<tr>
					<th>Element Name</th>
					<th>Field Staff Value</th>
					<th>Reabstraction Value</th>
				</tr>
			</thead>
			<tr id="STATENO">
				<td>Stateno</td>
				<td>${fsRawCrfDoc.stateno}</td>		
				<td>${reabRawCrfDoc.stateno}</td>
			</tr>
			<tr id="REPT_HEALTH_DEPT_CITY_COUNTY">
				<td>Reporting Health Department - City/County</td>		
				<td>${fsRawCrfDoc.reportingHealthDeptCityCounty}</td>		
				<td>${reabRawCrfDoc.reportingHealthDeptCityCounty}</td>
			</tr>
			<tr id="DOCUMENT_SOURCE">
				<td>Document Source</td>		
				<td>${fsRawCrfDoc.documentSource}</td>		
				<td>${reabRawCrfDoc.documentSource}</td>
			</tr>
			<tr id="SURVEILLANCE_METHOD_CODE">
				<td>Surveillance Method</td>		
				<td>${fsRawCrfDoc.surveillanceMethodCode}</td>		
				<td>${reabRawCrfDoc.surveillanceMethodCode}</td>
			</tr>
			<tr id="REPORT_MEDIUM_CODE">
				<td>Report Medium</td>		
				<td>${fsRawCrfDoc.reportMediumCode}</td>		
				<td>${reabRawCrfDoc.reportMediumCode}</td>
			</tr>
		</table>			

		<div class="reabSecHeader">Facility Providing Information</div>
		<table class="table table-striped exportTables width97Perc expandCollpaseContent"> 
			<thead style="font-size:14px;">
				<tr>
					<th>Element Name</th>
					<th>Field Staff Value</th>
					<th>Reabstraction Value</th>
				</tr>
			</thead>
			<tr id="PROVIDING_FACILITY_NAME">
				<td>Facility Name</td>
				<td>${fsRawCrfDoc.providingFacilityName}</td>		
				<td>${reabRawCrfDoc.providingFacilityName}</td>
			</tr>
		</table>			

		<div class="reabSecHeader">Patient Demographics</div>
		<table class="table table-striped exportTables width97Perc expandCollpaseContent"> 
			<thead style="font-size:14px;">
				<tr>
					<th>Element Name</th>
					<th>Field Staff Value</th>
					<th>Reabstraction Value</th>
				</tr>
			</thead>
			<tr id="BIRTH_SEX">
				<td>Sex Assigned At Birth</td>
				<td>${fsRawCrfDoc.birthSex}</td>		
				<td>${reabRawCrfDoc.birthSex}</td>
			</tr>
			<tr id="BIRTH_COUNTRY">
				<td>Country of Birth</td>		
				<td>${fsRawCrfDoc.birthCountry}</td>		
				<td>${reabRawCrfDoc.birthCountry}</td>
			</tr>
			<tr id="BIRTH_COUNTRY_OTHER">
				<td>Birth Country (Other)</td>		
				<td>${fsRawCrfDoc.birthCountry}</td>		
				<td>${reabRawCrfDoc.birthCountry}</td>
			</tr>
			<tr id="DATE_OF_BIRTH">
				<td>Date of Birth</td>		
				<td>${fsRawCrfDoc.dateOfBirth}</td>		
				<td>${reabRawCrfDoc.dateOfBirth}</td>
			</tr>
			<tr id="DATE_OF_DEATH">
				<td>Date of Death</td>		
				<td>${fsRawCrfDoc.dateOfDeath}</td>		
				<td>${reabRawCrfDoc.dateOfDeath}</td>
			</tr>
			<tr id="STATE_OF_DEATH">
				<td>State of Death</td>		
				<td>${fsRawCrfDoc.stateOfDeath}</td>		
				<td>${reabRawCrfDoc.stateOfDeath}</td>
			</tr>
		<c:if test="${docType == 'ACRF' }">
			<tr id="GENDER_CODE">
				<td>Current Gender Identity</td>		
				<td>${fsRawCrfDoc.genderCode}</td>		
				<td>${reabRawCrfDoc.genderCode}</td>
			</tr>
			<tr id="ADDITIONAL_GENDER">
				<td>Additional Gender</td>		
				<td>${fsRawCrfDoc.additionalGender}</td>		
				<td>${reabRawCrfDoc.additionalGender}</td>
			</tr>
		</c:if>			
			<tr id="ETHNICITY_CODE">
				<td>Ethnicity</td>		
				<td>${fsRawCrfDoc.ethnicityCode}</td>		
				<td>${reabRawCrfDoc.ethnicityCode}</td>
			</tr>
			<tr id="EXPANDED_ETHNICITY">
				<td>Expanded Ethnicity</td>		
				<td>${fsRawCrfDoc.expandedEthnicity}</td>		
				<td>${reabRawCrfDoc.expandedEthnicity}</td>
			</tr>
			<tr id="RACE_INDIAN">
				<td>Race: American Indian/Alaska Native</td>		
				<td><input type="checkbox" <c:if test="${fsRawCrfDoc.raceIndian == true}"> checked="checked"</c:if> /></td>		
				<td><input type="checkbox"  <c:if test="${reabRawCrfDoc.raceIndian == true}"> checked="checked"</c:if> /></td>		
			</tr>			
			<tr id="RACE_ASIAN">
				<td>Race: Asian</td>		
				<td><input type="checkbox" <c:if test="${fsRawCrfDoc.raceAsian == true}"> checked="checked"</c:if> /></td>		
				<td><input type="checkbox"  <c:if test="${reabRawCrfDoc.raceAsian == true}"> checked="checked"</c:if> /></td>		
			</tr>			
			<tr id="RACE_BLACK">
				<td>Race: Black/African American</td>		
				<td><input type="checkbox" <c:if test="${fsRawCrfDoc.raceBlack == true}"> checked="checked"</c:if> /></td>		
				<td><input type="checkbox"  <c:if test="${reabRawCrfDoc.raceBlack == true}"> checked="checked"</c:if> /></td>		
			</tr>			
			<tr id="RACE_PACIFIC">
				<td>Race: Native Hawaiian/Other Pacific Islander</td>
				<td><input type="checkbox" <c:if test="${fsRawCrfDoc.racePacific == true}"> checked="checked"</c:if> /></td>		
				<td><input type="checkbox"  <c:if test="${reabRawCrfDoc.racePacific == true}"> checked="checked"</c:if> /></td>		
			</tr>			
			<tr id="RACE_WHITE">
				<td>Race: White </td>		
				<td><input type="checkbox"  <c:if test="${fsRawCrfDoc.raceWhite == true}"> checked="checked"</c:if> /></td>		
				<td><input type="checkbox"  <c:if test="${reabRawCrfDoc.raceWhite == true}"> checked="checked"</c:if> /></td>
			</tr>			
			<tr id="RACE_UNKNOWN">
				<td>Race: Unknown</td>		
				<td><input type="checkbox"  <c:if test="${fsRawCrfDoc.raceUnknown == true}"> checked="checked"</c:if> /></td>		
				<td><input type="checkbox"  <c:if test="${reabRawCrfDoc.raceUnknown == true}"> checked="checked"</c:if> /></td>
			</tr>			
			<tr id="EXPANDED_RACE">
				<td>Expanded Race</td>		
				<td>${fsRawCrfDoc.expandedRace}</td>		
				<td>${reabRawCrfDoc.expandedRace}</td>
			</tr>			
		</table>			


		<div class="reabSecHeader">Residence at Diagnosis</div>
		<table class="table table-striped exportTables width97Perc expandCollpaseContent"> 
			<thead style="font-size:14px;">
				<tr>
					<th>Element Name</th>
					<th>Field Staff Value</th>
					<th>Reabstraction Value</th>
				</tr>
			</thead>
			<tr id="RES_HIV_DX">
				<td>Address Event Type (HIV Dx)</td>
				<td><input type="checkbox"  <c:if test="${fsRawCrfDoc.resHivDx == true}"> checked="checked"</c:if> /></td>		
				<td><input type="checkbox"  <c:if test="${reabRawCrfDoc.resHivDx == true}"> checked="checked"</c:if> /></td>
			</tr>
			<tr id="HIV_ADDRESS_TYPE_CODE">
				<td>Address Type</td>
				<td>${fsRawCrfDoc.hivAddressTypeCode}</td>		
				<td>${reabRawCrfDoc.hivAddressTypeCode}</td>
			</tr>
			<tr id="RES_HIV_ADDRESS">
				<td>Street Address</td>
				<td>${fsRawCrfDoc.resHivAddress}</td>		
				<td>${reabRawCrfDoc.resHivAddress}</td>
			</tr>
			<tr id="RES_HIV_CITY">
				<td>City</td>
				<td>${fsRawCrfDoc.resHivCity}</td>		
				<td>${reabRawCrfDoc.resHivCity}</td>
			</tr>
			<tr id="RES_HIV_COUNTY_FIPS">
				<td>County</td>
				<td>${fsRawCrfDoc.resHivCountyFips}</td>		
				<td>${reabRawCrfDoc.resHivCountyFips}</td>
			</tr>
			<tr id="RES_HIV_STATE">
				<td>State</td>
				<td>${fsRawCrfDoc.resHivState}</td>		
				<td>${reabRawCrfDoc.resHivState}</td>
			</tr>
			<tr id="RES_HIV_COUNTRY">
				<td>Country</td>
				<td>${fsRawCrfDoc.resHivCountry}</td>		
				<td>${reabRawCrfDoc.resHivCountry}</td>
			</tr>
			<tr id="RES_HIV_ZIPCODE">
				<td>Zip Code</td>
				<td>${fsRawCrfDoc.resHivZipcode}</td>		
				<td>${reabRawCrfDoc.resHivZipcode}</td>
			</tr>
			<tr id="RES_AIDS_DX">
				<td>Address Event Type (AIDS Dx)</td>
				<td><input type="checkbox"  <c:if test="${fsRawCrfDoc.resAidsDx == true}"> checked="checked"</c:if> /></td>		
				<td><input type="checkbox"  <c:if test="${reabRawCrfDoc.resAidsDx == true}"> checked="checked"</c:if> /></td>
			</tr>
			<tr id="AIDS_ADDRESS_TYPE_CODE">
				<td>Address Type</td>
				<td>${fsRawCrfDoc.aidsAddressTypeCode}</td>		
				<td>${reabRawCrfDoc.aidsAddressTypeCode}</td>
			</tr>
			<tr id="RES_AIDS_ADDRESS">
				<td>Street Address</td>
				<td>${fsRawCrfDoc.resAidsAddress}</td>		
				<td>${reabRawCrfDoc.resAidsAddress}</td>
			</tr>
			<tr id="RES_AIDS_CITY">
				<td>City</td>
				<td>${fsRawCrfDoc.resAidsCity}</td>		
				<td>${reabRawCrfDoc.resAidsCity}</td>
			</tr>
			<tr id="RES_AIDS_COUNTY_FIPS">
				<td>County</td>
				<td>${fsRawCrfDoc.resAidsCountyFips}</td>		
				<td>${reabRawCrfDoc.resAidsCountyFips}</td>
			</tr>
			<tr id="RES_AIDS_STATE">
				<td>State</td>
				<td>${fsRawCrfDoc.resAidsState}</td>		
				<td>${reabRawCrfDoc.resAidsState}</td>
			</tr>
			<tr id="RES_AIDS_COUNTRY">
				<td>Country</td>
				<td>${fsRawCrfDoc.resAidsCountry}</td>		
				<td>${reabRawCrfDoc.resAidsCountry}</td>
			</tr>
			<tr id="RES_AIDS_ZIPCODE">
				<td>Zip Code</td>
				<td>${fsRawCrfDoc.resAidsZipcode}</td>		
				<td>${reabRawCrfDoc.resAidsZipcode}</td>
			</tr>
			
			<c:if test="${docType == 'PCRF' }">
<%-- 			<tr id="PERINATAL_ADDRESS_TYPE_CODE">
				<td>Address Type</td>
				<td>${fsRawCrfDoc.hivAddressTypeCode}</td>		
				<td>${reabRawCrfDoc.hivAddressTypeCode}</td>
			</tr>
 --%>		<tr id=RES_PERINATAL_ADDRESS>
				<td>Street Address</td>
				<td>${fsRawCrfDoc.resPerinatalAddress}</td>		
				<td>${reabRawCrfDoc.resPerinatalAddress}</td>
			</tr>
			<tr id="RES_PERINATAL_CITY">
				<td>City</td>
				<td>${fsRawCrfDoc.resPerinatalCity}</td>		
				<td>${reabRawCrfDoc.resPerinatalCity}</td>
			</tr>
			<tr id="RES_PERINATAL_COUNTY_FIPS">
				<td>County</td>
				<td>${fsRawCrfDoc.resPerinatalCountyFips}</td>		
				<td>${reabRawCrfDoc.resPerinatalCountyFips}</td>
			</tr>
			<tr id="RES_PERINATAL_STATE">
				<td>State</td>
				<td>${fsRawCrfDoc.resPerinatalState}</td>		
				<td>${reabRawCrfDoc.resPerinatalState}</td>
			</tr>
			<tr id="RES_PERINATAL_COUNTRY">
				<td>Country</td>
				<td>${fsRawCrfDoc.resPerinatalCountry}</td>		
				<td>${reabRawCrfDoc.resPerinatalCountry}</td>
			</tr>
			<tr id="RES_PERINATAL_COUNTRY">
				<td>Zip Code</td>
				<td>${fsRawCrfDoc.resPerinatalZipcode}</td>		
				<td>${reabRawCrfDoc.resPerinatalZipcode}</td>
			</tr>
<%-- 			<tr id="PEDIATRIC_ADDRESS_TYPE_CODE">
				<td>Address Event Type (Pediatric)</td>
				<td>${fsRawCrfDoc.resAidsDx}</td>		
				<td>${reabRawCrfDoc.resAidsDx}</td>
			</tr>
 --%>		
			<tr id="RES_PEDIATRIC_SERO_ADDRESS">
				<td>Street Address</td>
				<td>${fsRawCrfDoc.resPediatricSeroAddress}</td>		
				<td>${reabRawCrfDoc.resPediatricSeroAddress}</td>
			</tr>
			<tr id="RES_PEDIATRIC_SERO_CITY">
				<td>City</td>
				<td>${fsRawCrfDoc.resPediatricSeroCity}</td>		
				<td>${reabRawCrfDoc.resPediatricSeroCity}</td>
			</tr>
			<tr id="RES_PEDIATRIC_SERO_COUNTY_FIPS">
				<td>County</td>
				<td>${fsRawCrfDoc.resPediatricSeroCountyFips}</td>		
				<td>${reabRawCrfDoc.resPediatricSeroCountyFips}</td>
			</tr>
			<tr id="RES_PEDIATRIC_SERO_STATE">
				<td>State</td>
				<td>${fsRawCrfDoc.resPediatricSeroState}</td>		
				<td>${reabRawCrfDoc.resPediatricSeroState}</td>
			</tr>
			<tr id="RES_PEDIATRIC_SERO_COUNTRY">
				<td>Country</td>
				<td>${fsRawCrfDoc.resPediatricSeroCountry}</td>		
				<td>${reabRawCrfDoc.resPediatricSeroCountry}</td>
			</tr>
			<tr id="RES_PEDIATRIC_SERO_ZIPCODE">
				<td>Zip Code</td>
				<td>${fsRawCrfDoc.resPediatricSeroZipcode}</td>		
				<td>${reabRawCrfDoc.resPediatricSeroZipcode}</td>
			</tr>
			
			</c:if>
			
		</table>		

		<div class="reabSecHeader">Facility of Diagnosis</div>
		<table class="table table-striped exportTables width97Perc expandCollpaseContent"> 
			<thead style="font-size:14px;">
				<tr>
					<th>Element Name</th>
					<th>Field Staff Value</th>
					<th>Reabstraction Value</th>
				</tr>
			</thead>
			<tr id="FACILITY_DX_HIV">
				<td>Diagnosis Type (HIV Dx)</td>
				<td><input type="checkbox"  <c:if test="${fsRawCrfDoc.facilityDxHiv == true}"> checked="checked"</c:if> /></td>		
				<td><input type="checkbox"  <c:if test="${reabRawCrfDoc.facilityDxHiv == true}"> checked="checked"</c:if> /></td>				
			</tr>
			<tr id="FACILITY_NAME_HIV_DX">
				<td>Facility Name</td>
				<td>${fsRawCrfDoc.facilityHivDxName}</td>		
				<td>${reabRawCrfDoc.facilityHivDxName}</td>
			</tr>
			<tr id="FACILITY_DX_AIDS">
				<td>Diagnosis Type (AIDS Dx)</td>
				<td><input type="checkbox"  <c:if test="${fsRawCrfDoc.facilityDxAids == true}"> checked="checked"</c:if> /></td>		
				<td><input type="checkbox"  <c:if test="${reabRawCrfDoc.facilityDxAids == true}"> checked="checked"</c:if> /></td>					
			</tr>
			<tr id="FACILITY_NAME_AIDS_DX">
				<td>Facility Name</td>
				<td>${fsRawCrfDoc.facilityAidsDxName}</td>		
				<td>${reabRawCrfDoc.facilityAidsDxName}</td>
			</tr>
			<c:if test="${docType == 'PCRF' }">
			<tr id="FACILITY_NAME_PERINATAL_DX">
				<td>Facility Name</td>
				<td>${fsRawCrfDoc.facilityPerinatalDxName}</td>		
				<td>${reabRawCrfDoc.facilityPerinatalDxName}</td>
			</tr>
			</c:if>			
		</table>	
		
		<div class="reabSecHeader">Patient History</div>
		<table class="table table-striped exportTables width97Perc expandCollpaseContent"> 
			<thead style="font-size:14px;">
				<tr>
					<th>Element Name</th>
					<th>Field Staff Value</th>
					<th>Reabstraction Value</th>
				</tr>
			</thead>
			<c:if test="${docType == 'ACRF' }">
				<tr id="SEX_WITH_MALE">
					<td>Sex with male</td>
					<td>${fsRawCrfDoc.sexWithMale}</td>		
					<td>${reabRawCrfDoc.sexWithMale}</td>
				</tr>
				<tr id="SEX_WITH_FEMALE">
					<td>Sex with female</td>
					<td>${fsRawCrfDoc.sexWithFemale}</td>		
					<td>${reabRawCrfDoc.sexWithFemale}</td>
				</tr>
				<tr id="IDU">
					<td>Injected non-prescription drugs</td>
					<td>${fsRawCrfDoc.idu}</td>		
					<td>${reabRawCrfDoc.idu}</td>
				</tr>
				<tr id="RECV_CLOTTING_FACTOR">
					<td>Received clotting factor for hemophilia/coagulation disorder </td>
					<td>${fsRawCrfDoc.recvClottingFactor}</td>		
					<td>${reabRawCrfDoc.recvClottingFactor}</td>
				</tr>
				<tr id="CLOTTING_FACTOR_RECV">
					<td>Specify clotting factor</td>
					<td>${fsRawCrfDoc.clottingFactorRecv}</td>		
					<td>${reabRawCrfDoc.clottingFactorRecv}</td>
				</tr>
				<tr id="DATE_RECV_CLOTTING_FACTOR">
					<td>Date received</td>
					<td>${fsRawCrfDoc.dateRecvClottingFactor}</td>		
					<td>${reabRawCrfDoc.dateRecvClottingFactor}</td>
				</tr>
				<tr id="HETEROSEX_WITH_IDU">
					<td>HETEROSEXUAL contact with intravenous/injection drug user</td>
					<td>${fsRawCrfDoc.heterosexWithIdu}</td>		
					<td>${reabRawCrfDoc.heterosexWithIdu}</td>
				</tr>
				<tr id="HETEROSEX_WITH_MSM">
					<td>HETEROSEXUAL contact with bisexual male</td>
					<td>${fsRawCrfDoc.heterosexWithMsm}</td>		
					<td>${reabRawCrfDoc.heterosexWithMsm}</td>
				</tr>
				<tr id="HETEROSEX_WITH_HEMOPHILIAC">
					<td>HETEROSEXUAL contact with person with hemophilia / coagulation disorder with documented HIV infection</td>
					<td>${fsRawCrfDoc.heterosexWithHemophiliac}</td>		
					<td>${reabRawCrfDoc.heterosexWithHemophiliac}</td>
				</tr>
				<tr id="HETEROSEX_WITH_TRANSFUSION">
					<td>HETEROSEXUAL contact with transfusion recipient with documented HIV infection</td>
					<td>${fsRawCrfDoc.heterosexWithTransfusion}</td>		
					<td>${reabRawCrfDoc.heterosexWithTransfusion}</td>
				</tr>
				<tr id="HETEROSEX_WITH_TRANSPLANT">
					<td>HETEROSEXUAL contact with transplant recipient with documented HIV infection</td>
					<td>${fsRawCrfDoc.heterosexWithTransplant}</td>		
					<td>${reabRawCrfDoc.heterosexWithTransplant}</td>
				</tr>
				<tr id="HETEROSEX_WITH_HIV">
					<td>HETEROSEXUAL contact with person with documented HIV infection, risk not specified</td>
					<td>${fsRawCrfDoc.heterosexWithHiv}</td>		
					<td>${reabRawCrfDoc.heterosexWithHiv}</td>
				</tr>
				
				<tr id="RECV_BLOOD_TRANSFUSION">
					<td>Received transfusion of blood/blood components (other than clotting factor)</td>
					<td>${fsRawCrfDoc.recvBloodTransfusion}</td>		
					<td>${reabRawCrfDoc.recvBloodTransfusion}</td>
				</tr>
				<tr id="FIRST_DATE_RECV_BLOOD">
					<td>First date received</td>
					<td>${fsRawCrfDoc.firstDateRecvBlood}</td>		
					<td>${reabRawCrfDoc.firstDateRecvBlood}</td>
				</tr>
				<tr id="LAST_DATE_RECV_BLOOD">
					<td>Last date received</td>
					<td>${fsRawCrfDoc.lastDateRecvBlood}</td>		
					<td>${reabRawCrfDoc.lastDateRecvBlood}</td>
				</tr>
				<tr id="RECV_TRANSPLANT">
					<td>Received transplant of tissue/organs or artificial insemination</td>
					<td>${fsRawCrfDoc.recvTransplant}</td>		
					<td>${reabRawCrfDoc.recvTransplant}</td>
				</tr>
				<tr id="HEALTHCARE_WORKER">
					<td>Worked in a healthcare or clinical laboratory setting</td>
					<td>${fsRawCrfDoc.healthcareWorker}</td>		
					<td>${reabRawCrfDoc.healthcareWorker}</td>
				</tr>
				<tr id="OCCUPATION">
					<td>Occupation and setting</td>
					<td>${fsRawCrfDoc.occupation}</td>		
					<td>${reabRawCrfDoc.occupation}</td>
				</tr>
				<tr id="OTHER_RISK">
					<td>Other documented risk</td>
					<td>${fsRawCrfDoc.otherRisk}</td>		
					<td>${reabRawCrfDoc.otherRisk}</td>
				</tr>
				<tr id="OTHER_RISK_DETAILS">
					<td>Other documented risk details</td>
					<td>${fsRawCrfDoc.otherRiskDetails}</td>		
					<td>${reabRawCrfDoc.otherRiskDetails}</td>
				</tr>
			</c:if>
			<c:if test="${docType == 'PCRF' }">
				<tr id="CHILD_MOM_HIV_INFECTION_STATUS">
					<td>Child's biological mother's HIV infection status</td>
					<td>${fsRawCrfDoc.childMomHivInfectionStatus}</td>		
					<td>${reabRawCrfDoc.childMomHivInfectionStatus}</td>
				</tr>
				<tr id="MOTHER_FIRST_POS_TEST_DATE">
					<td>Date of mother's first positive test to confirm infection</td>
					<td>${fsRawCrfDoc.motherFirstPosTestDate}</td>		
					<td>${reabRawCrfDoc.motherFirstPosTestDate}</td>
				</tr>
				<tr id="MOTHER_COUNSELED_HIV_PREGNANCY">
					<td>Was the biological mother counseled about HIV testing during this pregnancy, labor, or delivery?</td>
					<td>${fsRawCrfDoc.motherCounseledHivPregnancy}</td>		
					<td>${reabRawCrfDoc.motherCounseledHivPregnancy}</td>
				</tr>
				<tr id="MOM_PERINATAL_HIV_INFECTION">
					<td>Perinatally acquired HIV infection</td>
					<td>${fsRawCrfDoc.perinatalHivInfection}</td>		
					<td>${reabRawCrfDoc.perinatalHivInfection}</td>
				</tr>
				<tr id="MOM_IDU">
					<td>Injected nonprescription drugs </td>
					<td>${fsRawCrfDoc.idu}</td>		
					<td>${reabRawCrfDoc.idu}</td>
				</tr>
				<tr id="MOM_HETEROSEX_WITH_IDU">
					<td>HETEROSEXUAL contact with intravenous/injection drug user </td>
					<td>${fsRawCrfDoc.heterosexWithIdu}</td>		
					<td>${reabRawCrfDoc.heterosexWithIdu}</td>
				</tr>
				<tr id="MOM_HETEROSEX_WITH_BISEX">
					<td>HETEROSEXUAL contact with bisexual male</td>
					<td>${fsRawCrfDoc.heterosexWithMsm}</td>		
					<td>${reabRawCrfDoc.heterosexWithMsm}</td>
				</tr>
				<tr id="MOM_HETEROSEX_WITH_HEMOPHILIAC">
					<td>HETEROSEXUAL contact with person with hemophilia / coagulation disorder with documented HIV infection</td>
					<td>${fsRawCrfDoc.heterosexWithHemophiliac}</td>		
					<td>${reabRawCrfDoc.heterosexWithHemophiliac}</td>
				</tr>
				<tr id="MOM_HETEROSEX_WITH_TRANSFUSION">
					<td>HETEROSEXUAL contact with transfusion recipient with documented HIV infection</td>
					<td>${fsRawCrfDoc.heterosexWithTransfusion}</td>		
					<td>${reabRawCrfDoc.heterosexWithTransfusion}</td>
				</tr>
				<tr id="MOM_HETEROSEX_WITH_TRANSPLANT">
					<td>HETEROSEXUAL contact with transplant recipient with documented HIV infection</td>
					<td>${fsRawCrfDoc.heterosexWithTransplant}</td>		
					<td>${reabRawCrfDoc.heterosexWithTransplant}</td>
				</tr>
				<tr id="MOM_HETEROSEX_WITH_HIV">
					<td>HETEROSEXUAL contact with person with documented HIV infection, risk not specified</td>
					<td>${fsRawCrfDoc.heterosexWithHiv}</td>		
					<td>${reabRawCrfDoc.heterosexWithHiv}</td>
				</tr>
				<tr id="MOM_RECV_BLOOD_TRANSFUSION">
					<td>Received transfusion of blood/blood components (other than clotting factor)</td>
					<td>${fsRawCrfDoc.motherRecvBloodTransfusion}</td>		
					<td>${reabRawCrfDoc.motherRecvBloodTransfusion}</td>
				</tr>
				
				<tr id="MOM_FIRST_RECV_BLOOD_DATE">
					<td>First date received</td>
					<td>${fsRawCrfDoc.motherFirstRecvBloodDate}</td>		
					<td>${reabRawCrfDoc.motherFirstRecvBloodDate}</td>
				</tr>
				<tr id="MOM_LAST_RECV_BLOOD_DATE">
					<td>Last date received</td>
					<td>${fsRawCrfDoc.motherLastRecvBloodDate}</td>		
					<td>${reabRawCrfDoc.motherLastRecvBloodDate}</td>
				</tr>
				<tr id="MOM_RECV_TRANSPLANT">
					<td>Received transplant of tissue/organs or artificial insemination</td>
					<td>${fsRawCrfDoc.motherRecvTransplant}</td>		
					<td>${reabRawCrfDoc.motherRecvTransplant}</td>
				</tr>
				<tr id="IDU_BEFORE_DIAG">
					<td>Injected nonprescription drugs </td>
					<td>${fsRawCrfDoc.iduBeforeDiag}</td>		
					<td>${reabRawCrfDoc.iduBeforeDiag}</td>
				</tr>
				<tr id="RECV_CLOTTING_FACTOR_BEFORE_DIAG">
					<td>Received clotting factor for hemophilia/coagulation disorder</td>
					<td>${fsRawCrfDoc.recvClottingFactorBeforeDiag}</td>		
					<td>${reabRawCrfDoc.recvClottingFactorBeforeDiag}</td>
				</tr>
				<tr id="CLOTTING_FACTOR_RECV_BEFORE_DIAG">
					<td>Specify clotting factor</td>
					<td>${fsRawCrfDoc.clottingFactorRecvBeforeDiag}</td>		
					<td>${reabRawCrfDoc.clottingFactorRecvBeforeDiag}</td>
				</tr>
				<tr id="CLOTTING_FACTOR_BEFORE_DIAG_RECV_DATE">
					<td>Date received</td>
					<td>${fsRawCrfDoc.clottingFactorBeforeDiagRecvDate}</td>		
					<td>${reabRawCrfDoc.clottingFactorBeforeDiagRecvDate}</td>
				</tr>
				<tr id="RECV_BLOOD_TRANSFUSION_BEFORE_DIAG">
					<td>Received transfusion of blood/blood components (other than clotting factor) </td>
					<td>${fsRawCrfDoc.recvBloodTransfusionBeforeDiag}</td>		
					<td>${reabRawCrfDoc.recvBloodTransfusionBeforeDiag}</td>
				</tr>
				<tr id="FIRST_RECV_BLOOD_BEFORE_DIAG_DATE">
					<td>First date received</td>
					<td>${fsRawCrfDoc.firstRecvBloodBeforeDiagDate}</td>		
					<td>${reabRawCrfDoc.firstRecvBloodBeforeDiagDate}</td>
				</tr>
				<tr id="LAST_RECV_BLOOD_BEFORE_DIAG_DATE">
					<td>Last date received </td>
					<td>${fsRawCrfDoc.lastRecvBloodBeforeDiagDate}</td>		
					<td>${reabRawCrfDoc.lastRecvBloodBeforeDiagDate}</td>
				</tr>
				<tr id="RECV_TRANSPLANT_BEFORE_DIAG">
					<td>Received transplant of tissue/organs</td>
					<td>${fsRawCrfDoc.recvTransplantBeforeDiag}</td>		
					<td>${reabRawCrfDoc.recvTransplantBeforeDiag}</td>
				</tr>
				<tr id="SEX_WITH_MALE_BEFORE_DIAG">
					<td>Sexual contact with male </td>
					<td>${fsRawCrfDoc.sexWithMaleBeforeDiag}</td>		
					<td>${reabRawCrfDoc.sexWithMaleBeforeDiag}</td>
				</tr>
				<tr id="SEX_WITH_FEMALE_BEFORE_DIAG">
					<td>Sexual contact with female</td>
					<td>${fsRawCrfDoc.sexWithFemaleBeforeDiag}</td>		
					<td>${reabRawCrfDoc.sexWithFemaleBeforeDiag}</td>
				</tr>
				<tr id="OTHER_RISK_BEFORE_DIAG">
					<td>Other documented risk</td>
					<td>${fsRawCrfDoc.otherRiskBeforeDiag}</td>		
					<td>${reabRawCrfDoc.otherRiskBeforeDiag}</td>
				</tr>
			</c:if>
		</table>	
		
		<div class="reabSecHeader">Laboratory Data</div>
		<table class="table table-striped exportTables width97Perc expandCollpaseContent"> 
			<c:if test="${hivImmunoNonDiffInfo.size() > 0}">
				<tr>
					<td colspan="3"><b>HIV Immunoassays (Nondifferentiating)</b>
						<table class="table table-striped exportTables width90Perc expandCollpaseContent"  align="center">
						<thead style="font-size:14px;">
							<tr>
								<th class="displayNone">Src_id</th>
								<th>Element Name</th>
								<th>Field Staff Value</th>
								<th>Reabstraction Value</th>
							</tr>
						</thead>
						<c:forEach items="${hivImmunoNonDiffInfo}" var="perLabInfo">
						<c:if test="${perLabInfo[0] == '01' and perLabInfo[6] != 'COPIES_ML' and perLabInfo[6] != 'LOG' 
									and perLabInfo[6] != 'HIV1_AG_ANALYTE_RESULT' and perLabInfo[6] != 'HIV1_AB_ANALYTE_RESULT' and perLabInfo[6] != 'HIV2_AB_ANALYTE_RESULT'}">
							<tr class="${perLabInfo[5]} ${perLabInfo[6]}">
								<td class="displayNone">${perLabInfo[1]}</td>
						 		<td>${perLabInfo[2]}</td>
								<td>${perLabInfo[3]}</td>
								<td>${perLabInfo[4]}</td>
							</tr>
							<c:if test="${perLabInfo[6] == 'RAPID_TEST'}">
								<tr class="blank_row">
									<td colspan="4"></td>
								</tr>
							</c:if>
						</c:if>
						</c:forEach>
						</table>
					</td>
				</tr>		
			</c:if>
			<c:if test="${hivImmunoDiffInfo.size() > 0}">
				<tr>
					<td colspan="3"><b>HIV Immunoassays (Differentiating)</b>
						<table class="table table-striped exportTables width90Perc expandCollpaseContent"  align="center">
						<thead style="font-size:14px;">
							<tr>
								<th class="displayNone">Src_id</th>
								<th>Element Name</th>
								<th>Field Staff Value</th>
								<th>Reabstraction Value</th>
							</tr>
						</thead>
						<c:forEach items="${hivImmunoDiffInfo}" var="perLabInfo">
						<c:if test="${perLabInfo[0] == '02' and perLabInfo[6] != 'COPIES_ML' and perLabInfo[6] != 'LOG' }">
							<tr class="${perLabInfo[5]} ${perLabInfo[6]}">
								<td class="displayNone">${perLabInfo[1]}</td>
						 		<td>${perLabInfo[2]}</td>
								<td>${perLabInfo[3]}</td>
								<td>${perLabInfo[4]}</td>
							</tr>
							<c:if test="${perLabInfo[6] == 'HIV2_AB_ANALYTE_RESULT'}">
								<tr class="blank_row">
									<td colspan="4"></td>
								</tr>
							</c:if>
						</c:if>
						</c:forEach>
						</table>
					</td>
				</tr>		
				</c:if>
				<c:if test="${hivDetTestQualInfo.size() > 0}">
				<tr>
					<td colspan="3"><b>HIV Detection Tests (Qualitative)</b>
						<table class="table table-striped exportTables width90Perc expandCollpaseContent"  align="center">
						<thead style="font-size:14px;">
							<tr>
								<th class="displayNone">Src_id</th>
								<th>Element Name</th>
								<th>Field Staff Value</th>
								<th>Reabstraction Value</th>
							</tr>
						</thead>
						<c:forEach items="${hivDetTestQualInfo}" var="perLabInfo">
						<c:if test="${perLabInfo[0] == '03' and perLabInfo[6] != 'COPIES_ML' and perLabInfo[6] != 'LOG'  and perLabInfo[6] != 'RAPID_TEST' 
									and perLabInfo[6] != 'HIV1_AG_ANALYTE_RESULT' and perLabInfo[6] != 'HIV1_AB_ANALYTE_RESULT' and perLabInfo[6] != 'HIV2_AB_ANALYTE_RESULT'}">
							<tr class="${perLabInfo[5]} ${perLabInfo[6]}">
								<td class="displayNone">${perLabInfo[1]}</td>
						 		<td>${perLabInfo[2]}</td>
								<td>${perLabInfo[3]}</td>
								<td>${perLabInfo[4]}</td>
							</tr>
							<c:if test="${perLabInfo[6] == 'COLLECTION_DATE'}">
								<tr class="blank_row">
									<td colspan="4"></td>
								</tr>
							</c:if>
						</c:if>
						</c:forEach>
						</table>
					</td>
				</tr>		
				</c:if>
				<c:if test="${hivDetTestQualVLInfo.size() > 0}">
				<tr>
					<td colspan="3"><b>HIV Detection Tests (Quantitative viral load)</b>
						<table class="table table-striped exportTables width90Perc expandCollpaseContent"  align="center">
						<thead style="font-size:14px;">
							<tr>
								<th class="displayNone">Src_id</th>
								<th>Element Name</th>
								<th>Field Staff Value</th>
								<th>Reabstraction Value</th>
							</tr>
						</thead>
						<c:forEach items="${hivDetTestQualVLInfo}" var="perLabInfo">
						<c:if test="${perLabInfo[0] == '04' and perLabInfo[6] != 'RAPID_TEST' and perLabInfo[6] != 'HIV1_AG_ANALYTE_RESULT' 
											and perLabInfo[6] != 'HIV1_AB_ANALYTE_RESULT' and perLabInfo[6] != 'HIV2_AB_ANALYTE_RESULT'}">
							<tr class="${perLabInfo[5]} ${perLabInfo[6]}">
								<td class="displayNone">${perLabInfo[1]}</td>
						 		<td>${perLabInfo[2]}</td>
								<td>${perLabInfo[3]}</td>
								<td>${perLabInfo[4]}</td>
							</tr>
							<c:if test="${perLabInfo[6] == 'LOG'}">
								<tr class="blank_row">
									<td colspan="4"></td>
								</tr>
							</c:if>
						</c:if>
						</c:forEach>
						</table>
					</td>
				</tr>		
				</c:if>
				<c:if test="${drugResTestGenoInfo.size() > 0}">
				<tr>
					<td colspan="3"><b>Drug Resistance Tests (Genotypic)</b>
						<table class="table table-striped exportTables width90Perc expandCollpaseContent"  align="center">
						<thead style="font-size:14px;">
							<tr>
								<th class="displayNone">Src_id</th>
								<th>Element Name</th>
								<th>Field Staff Value</th>
								<th>Reabstraction Value</th>
							</tr>
						</thead>
						<c:forEach items="${drugResTestGenoInfo}" var="perLabInfo">
						<c:if test="${perLabInfo[0] == '05' and perLabInfo[6] != 'COPIES_ML' and perLabInfo[6] != 'LOG'  and perLabInfo[6] != 'RAPID_TEST' 
							and perLabInfo[6] != 'ACRF_RESULT_CODE' and perLabInfo[6] != 'HIV1_AG_ANALYTE_RESULT' and perLabInfo[6] != 'HIV1_AB_ANALYTE_RESULT' and perLabInfo[6] != 'HIV2_AB_ANALYTE_RESULT'}">
							<tr class="${perLabInfo[5]} ${perLabInfo[6]}">
								<td class="displayNone">${perLabInfo[1]}</td>
						 		<td>${perLabInfo[2]}</td>
								<td>${perLabInfo[3]}</td>
								<td>${perLabInfo[4]}</td>
							</tr>
							<c:if test="${perLabInfo[6] == 'COLLECTION_DATE'}">
								<tr class="blank_row">
									<td colspan="4"></td>
								</tr>
							</c:if>						
						</c:if>
						</c:forEach>
						</table>
					</td>
				</tr>		
				</c:if>
				
				<c:if test="${closestToCurDx.size() > 0 or firstLowCd4.size() > 0 or otherCd4.size() > 0}">
				<tr>
					<td colspan="3"><b>Immunologic Tests (CD4 Count and Percentage)</b> <br><br>
					<c:if test="${closestToCurDx.size() > 0}">
					<b>CD4 at or closest to current diagnostic status:</b>
						<table class="table table-striped exportTables width90Perc expandCollpaseContent"  align="center">
						<thead style="font-size:14px;">
							<tr>
								<th class="displayNone">Src_id</th>
								<th>Element Name</th>
								<th>Field Staff Value</th>
								<th>Reabstraction Value</th>
							</tr>
						</thead>
						<c:forEach items="${closestToCurDx}" var="closestToCurDxRec">
							<tr class="${closestToCurDxRec[4]} ${closestToCurDxRec[5]}">
								<td class="displayNone">${closestToCurDxRec[0]}</td>
						 		<td>${closestToCurDxRec[1]}</td>
								<td>${closestToCurDxRec[2]}</td>
								<td>${closestToCurDxRec[3]}</td>
								<c:if test="${closestToCurDxRec[5] == 'COLLECTION_DATE'}">
									<tr class="blank_row">
										<td colspan="4"></td>
									</tr>
								</c:if>
							</tr>
						</c:forEach>
						</table>
					</c:if>						

					<c:if test="${firstLowCd4.size() > 0}">
					<br><b>First CD4 result <200 cells/uL or <14%:</b>
						<table class="table table-striped exportTables width90Perc expandCollpaseContent"  align="center">
						<thead style="font-size:14px;">
							<tr>
								<th class="displayNone">Src_id</th>
								<th>Element Name</th>
								<th>Field Staff Value</th>
								<th>Reabstraction Value</th>
							</tr>
						</thead>
						<c:forEach items="${firstLowCd4}" var="firstLowCd4Rec">
							<tr class="${firstLowCd4Rec[4]} ${firstLowCd4Rec[5]}">
								<td class="displayNone">${firstLowCd4Rec[0]}</td>
						 		<td>${firstLowCd4Rec[1]}</td>
								<td>${firstLowCd4Rec[2]}</td>
								<td>${firstLowCd4Rec[3]}</td>
								<c:if test="${firstLowCd4Rec[5] == 'COLLECTION_DATE'}">
									<tr class="blank_row">
										<td colspan="4"></td>
									</tr>
								</c:if>
							</tr>
						</c:forEach>
						</table>
					</c:if>						

					<c:if test="${otherCd4.size() > 0}">
						<br><b>Other CD4 result:</b>
						<table class="table table-striped exportTables width90Perc expandCollpaseContent"  align="center">
						<thead style="font-size:14px;">
							<tr>
								<th class="displayNone">Src_id</th>
								<th>Element Name</th>
								<th>Field Staff Value</th>
								<th>Reabstraction Value</th>
							</tr>
						</thead>
						<c:forEach items="${otherCd4}" var="otherCd4Rec">
							<tr class="${otherCd4Rec[4]} ${otherCd4Rec[5]}">
								<td class="displayNone">${otherCd4Rec[0]}</td>
						 		<td>${otherCd4Rec[1]}</td>
								<td>${otherCd4Rec[2]}</td>
								<td>${otherCd4Rec[3]}</td>
								<c:if test="${otherCd4Rec[5] == 'COLLECTION_DATE'}">
									<tr class="blank_row">
										<td colspan="4"></td>
									</tr>
								</c:if>
							</tr>
						</c:forEach>
						</table>
					</c:if>						
					</td>
				</tr> 
				</c:if>				
		</table>
		
		
		<div class="reabSecHeader">Treatment/Services Referrals</div>
		<table class="table table-striped exportTables width97Perc expandCollpaseContent"> 
			<thead style="font-size:14px;">
				<tr>
					<th>Element Name</th>
					<th>Field Staff Value</th>
					<th>Reabstraction Value</th>
				</tr>
			</thead>
			<tr id="CURRENTLY_PREGNANT">
				<td>For Female Patient - Is this Patient Currently Pregnant</td>
				<td>${fsRawCrfDoc.currentlyPregnant}</td>		
				<td>${reabRawCrfDoc.currentlyPregnant}</td>
			</tr>
		</table>	
		
		
		<div class="reabSecHeader">Antiretroviral Use History</div>
		<table class="table table-striped exportTables width97Perc expandCollpaseContent"> 
			<thead style="font-size:14px;">
				<tr>
					<th>Element Name</th>
					<th>Field Staff Value</th>
					<th>Reabstraction Value</th>
				</tr>
			</thead>
			<c:if test="${docType == 'ACRF' }">			
						<tr id="MAIN_ARV_SOURCE">
							<td>Main Source of ARV Use Information</td>
							<td>${fsRawCrfDoc.mainARVSource}</td>		
							<td>${reabRawCrfDoc.mainARVSource}</td>
						</tr>
						<tr id="PT_ARV_DATE_REPORTED">
							<td>Date Patient Reported Information</td>
							<td>${fsRawCrfDoc.arvPatientDateReported}</td>		
							<td>${reabRawCrfDoc.arvPatientDateReported}</td>
						</tr>
						<tr id="PT_ARV_6_MO">
							<td>Ever Taken Any ARVs</td>
							<td>${fsRawCrfDoc.takenArvs}</td>		
							<td>${reabRawCrfDoc.takenArvs}</td>
						</tr>
			</c:if>
			<c:if test="${docType == 'PCRF' }">			
				<tr id="CHILD_EVER_TAKEN_ARVS">
					<td>This child ever taken any ARVs?</td>
					<td>${fsRawCrfDoc.childEverTakenArvs}</td>		
					<td>${reabRawCrfDoc.childEverTakenArvs}</td>
				</tr>

				<tr id=MOTHER_RECV_ARV_PRIOR_PREGNANCY>
					<td>Did mother receive any antiretrovirals (ARVs) prior to this pregnancy?</td>
					<td>${fsRawCrfDoc.motherRecvArvPriorPregnancy}</td>		
					<td>${reabRawCrfDoc.motherRecvArvPriorPregnancy}</td>
				</tr>
				<tr id="ARV_PRIOR_PREGNANCY_BEGAN_DATE">
					<td>Date began</td>
					<td>${fsRawCrfDoc.arvPriorPregnancyBeganDate}</td>		
					<td>${reabRawCrfDoc.arvPriorPregnancyBeganDate}</td>
				</tr>
				<tr id="ARV_PRIOR_PREGNANCY_LAST_USE_DATE">
					<td>Date of last use</td>
					<td>${fsRawCrfDoc.arvPriorPregnancyLastUseDate}</td>		
					<td>${reabRawCrfDoc.arvPriorPregnancyLastUseDate}</td>
				</tr>
				
				<tr id=MOTHER_RECV_ARV_DURING_PREGNANCY>
					<td>Did mother receive any ARVs during pregnancy?</td>
					<td>${fsRawCrfDoc.motherRecvArvDuringPregnancy}</td>		
					<td>${reabRawCrfDoc.motherRecvArvDuringPregnancy}</td>
				</tr>
				<tr id="ARV_DURING_PREGNANCY_BEGAN_DATE">
					<td>Date began</td>
					<td>${fsRawCrfDoc.arvDuringPregnancyBeganDate}</td>		
					<td>${reabRawCrfDoc.arvDuringPregnancyBeganDate}</td>
				</tr>
				<tr id="ARV_DURING_PREGNANCY_LAST_USE_DATE">
					<td>Date of last use</td>
					<td>${fsRawCrfDoc.arvDuringPregnancyLastUseDate}</td>		
					<td>${reabRawCrfDoc.arvDuringPregnancyLastUseDate}</td>
				</tr>
				
				<tr id=MOTHER_RECV_ARV_LABOR_PREGNANCY>
					<td>Did mother receive any ARVs during labor/delivery?</td>
					<td>${fsRawCrfDoc.motherRecvArvLaborPregnancy}</td>		
					<td>${reabRawCrfDoc.motherRecvArvLaborPregnancy}</td>
				</tr>
				<tr id="ARV_LABOR_PREGNANCY_BEGAN_DATE">
					<td>Date began</td>
					<td>${fsRawCrfDoc.arvLaborPregnancyBeganDate}</td>		
					<td>${reabRawCrfDoc.arvLaborPregnancyBeganDate}</td>
				</tr>
				<tr id="ARV_LABOR_PREGNANCY_LAST_USE_DATE">
					<td>Date of last use</td>
					<td>${fsRawCrfDoc.arvLaborPregnancyLastUseDate}</td>		
					<td>${reabRawCrfDoc.arvLaborPregnancyLastUseDate}</td>
				</tr>
				

				<tr id="CHILD_EVER_TAKEN_PCP_PROPHYLAXIS">
					<td>Has this child ever taken PCP prophylaxis</td>
					<td>${fsRawCrfDoc.childEverTakenPcpProphylaxis}</td>		
					<td>${reabRawCrfDoc.childEverTakenPcpProphylaxis}</td>
				</tr>
			</c:if>			
		<!-- ARV Use TYpe data will go here -->
			<c:if test="${fsRawCrfDoc.takenArvs == 'Y' or reabRawCrfDoc.takenArvs == 'Y'}">
				<tr>
					<td colspan="3">If Yes, Reason For ARV Use
						<table class="table table-striped exportTables width90Perc expandCollpaseContent"  align="center">
						<thead style="font-size:14px;">
							<tr>
								<th>Arv Use Type</th>
								<th>Element Name</th>
								<th>Field Staff Value</th>
								<th>Reabstraction Value</th>
							</tr>
						</thead>
						<c:forEach items="${arvInfo}" var="perArvInfo">
						<tr class="${perArvInfo[4]} ${perArvInfo[5]}">
							<td>${perArvInfo[0]}</td>
					 		<td>${perArvInfo[1]}</td>
							<td>${perArvInfo[2]}</td>
							<td>${perArvInfo[3]}</td>
							<c:if test="${perArvInfo[5] == 'ARV_END_DATE'}">
								<tr class="blank_row">
									<td colspan="4"></td>
								</tr>
							</c:if>							
						</tr>
						</c:forEach>
						</table>
					</td>
				</tr>
			</c:if>
			<c:if test="${docType == 'ACRF' }">					
				<tr id="PT_ARV_START_DATE">
					<td>Date Began</td>
					<td>${fsRawCrfDoc.dateArvsBegan}</td>		
					<td>${reabRawCrfDoc.dateArvsBegan}</td>
				</tr>
				<tr id="PT_ARV_END_DATE">
					<td>Date of Last Use</td>
					<td>${fsRawCrfDoc.dateLastUsedArvs}</td>		
					<td>${reabRawCrfDoc.dateLastUsedArvs}</td>
				</tr>
			</c:if>
		</table>	
		
	<c:if test="${docType == 'ACRF' }">					
		<div class="reabSecHeader">HIV Testing History</div>
		<table class="table table-striped exportTables width97Perc expandCollpaseContent"> 
			<thead style="font-size:14px;">
				<tr>
					<th>Element Name</th>
					<th>Field Staff Value</th>
					<th>Reabstraction Value</th>
				</tr>
			</thead>
			<tr id="MAIN_TTH_SOURCE">
				<td>Main Source of Testing History Information</td>
				<td>${fsRawCrfDoc.testTreatmentHistorySource}</td>		
				<td>${reabRawCrfDoc.testTreatmentHistorySource}</td>
			</tr>
			<tr id="PT_INTERVIEW_DATE">
				<td>Date Patient Reported Information</td>
				<td>${fsRawCrfDoc.dateReported}</td>		
				<td>${reabRawCrfDoc.dateReported}</td>
			</tr>
			<tr id="EVER_HAD_POS">
				<td>Ever Had Previous Positive HIV Test</td>
				<td>${fsRawCrfDoc.prevPosHivTest}</td>		
				<td>${reabRawCrfDoc.prevPosHivTest}</td>
			</tr>
			<tr id="FIRST_HIV_TEST_EVER">
				<td>Date of First Positive HIV Test</td>
				<td>${fsRawCrfDoc.dateFirstPosHivTest}</td>		
				<td>${reabRawCrfDoc.dateFirstPosHivTest}</td>
			</tr>
			<tr id="EVER_HAD_NEG">
				<td>Ever Had a Negative HIV Test</td>
				<td>${fsRawCrfDoc.negHivTest}</td>		
				<td>${reabRawCrfDoc.negHivTest}</td>
			</tr>
			<tr id="LAST_NEG_DATE">
				<td>Date of Last Negative HIV Test</td>
				<td>${fsRawCrfDoc.dateLastNegHivTest}</td>		
				<td>${reabRawCrfDoc.dateLastNegHivTest}</td>
			</tr>
			<tr id="COUNT_HIV_TEST_2YRS">
				<td>Number of Negative HIV Tests within 24 Months before First Positive Test</td>
				<td>${fsRawCrfDoc.numNegTestBeforePos}</td>		
				<td>${reabRawCrfDoc.numNegTestBeforePos}</td>
			</tr>
			<tr id="RESP_HIV_TEST_2YRS">
				<td>Number of Negative HIV Tests within 24 Months before First Positive Test (Unknown)</td>
				<td>${fsRawCrfDoc.numNegTestUnkRef}</td>		
				<td>${reabRawCrfDoc.numNegTestUnkRef}</td>
			</tr>
		</table>	
	</c:if>
				
		
	</div>			

	<c:if test="${workerRole == 4 && assignmentDetailsVO.reabCurrentStatus == 'REAB_C'}">
		<div class="button-row marginTop20 marginBottom20">
			<span>
				<a class="a_demo_search" style="color: #FFFFFF; margin-left: 5px;" onclick="$('#reabReviewDialog').dialog('open');" id="reabCompareReview"> Review</a>
			</span>
		</div>
	</c:if>

	   		
</form:form>																



