<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link href="${staticPath}/css/standardization/facility/newpage.css?v=${buildDate}" rel="stylesheet" type="text/css" />


		<div id="newPageTab">
			<div id="facilitySearchFormElements">
				<div id="addFacilityAccordion" style="width: 950px; margin-left: 20px; clear: both;" >
					<h3><span id="searchForFacilityAccordian">Search For Master Facility</span><span id="searchForSubFacilityAccordian">Search For Sub Facility</span></h3>
					<div style="height: 285px;">
						<div>
						
						
						
						
						
						
						
						
						
						
						
							<form:form id="searchForm" name="searchForm" modelAttribute="subFacilityVO">
								<div class="stdDetailsDiv" id="stdInfoDiv">
									<div>&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->
<!-- 									<div style="float:left; width:47%"> -->
<!-- 										<div class="standardInputDiv" grandFacility="allSrch"> -->
<!-- 											<div style="float: left"> -->
<%-- 												<label class="standardInputTitle"><spring:message code="label.masterFacilityName" /></label> --%>
<!-- 											</div> -->
<!-- 											<div style="float: right"> -->
<%-- 												<input id="masterFacilityName" name="masterFacilityName" tabindex="1" class="standardInputMedText mR50 fieldLevelHelp" value="" type="text" maxLength="100" title="<spring:message code='title.masterFacilityName' />"> --%>
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 									</div> -->
									<div style="clear: both;"></div> <!-- dummy div -->
									<div class="leftDiv">		
										<div class="standardInputDiv" >
											<div style="float:left">
												<label class="standardInputTitle"><spring:message code="label.facilityName" /></label>
											</div>
											<div style="float:right">
												<input id="stdFacilityName" name="facilityName"  tabindex="2" class="standardInputMedText mR50 fieldLevelHelp" value="" type="text" maxLength="80" title="<spring:message code='title.facilityName' />">
											</div>
										</div>
										
										<div class="standardInputDiv">
											<div style="float:left">
												<label class="standardInputTitle"><spring:message code="label.streetAddress" /></label>
											</div>
											<div style="float:right">
												<input id="stdFacilityStAddrs" name="address" tabindex="4" class="standardInputMedText mR50 fieldLevelHelp" value="" type="text" maxLength="100" title="<spring:message code='title.streetAddress' />">
											</div>
										</div>
										<div class="standardInputDiv">
											<div style="float:left">
												<label class="standardInputTitle"><spring:message code="label.city" /></label>
											</div>
											<div style="float:right">
												<input id="stdFacilityCity" name="city" tabindex="6" class="standardInputMedText mR50 fieldLevelHelp" type="text" maxLength="30" title="<spring:message code='title.city' />">
											</div>
										</div>
										<div class="standardInputDiv">
											<div style="float:left">
												<label class="standardInputTitle"><spring:message code="label.state" /></label>
											</div>
											<div style="float:right">
												<spring:message code="title.state" var="state_title"/>
												<form:select id="stdFacilityState" tabindex="8" path ="state" class="standardInputMedText mR50"  title="${state_title}">
											        <form:option value="" label="--- Select ---" />
													<form:options items="${sessionScope.stateMap}"/>
										    	</form:select>
											</div>
										</div>
										<div class="standardInputDiv">
											<div style="float:left">
												<label class="standardInputTitle">NPI</label>
											</div>
											<div style="float:right">
												<input id="stdFacilityNpi" name="facilityNpi"  tabindex="10" class="standardInputMedText mR50 fieldLevelHelp" value="" type="text" title="NPI">
											</div>
										</div>
									</div>
									<div class="rightDiv">		
										<div class="standardInputDiv">
											<div style="float:left">
												<label class="standardInputTitle"><spring:message code="label.department" /></label>
											</div>
											<div style="float:right">
												<input id="stdFacilityDept" name="facilityDepartment"  tabindex="3" class="standardInputMedText mR50 fieldLevelHelp" value="" type="text" maxLength="100" title="<spring:message code='title.department' />">
											</div>
										</div>
										<div class="standardInputDiv">
											<div style="float:left">
												<label class="standardInputTitle"><spring:message code="label.phoneNumber" /></label>
											</div>
											<div style="float:right">
												<input id="stdFacilityPhone" name="phone" tabindex="5" class="standardInputMedText mR50 mask phoneMask" type="text"  title="<spring:message code='title.phoneNumber' />">
											</div>
										</div>
										<div class="standardInputDiv">
											<div style="float:left">
												<label class="standardInputTitle"><spring:message code="label.zip" /></label>
											</div>
											<div style="float:right">
												<input id="stdFacilityZip" name="zipcode" tabindex="7" class="standardInputMedText mR50 fieldLevelHelp" type="text" maxLength="5" title="<spring:message code='title.zip' />">
											</div>
										</div>
										<div class="standardInputDiv" rawCrit="allSrch">
											<div style="float:left">
												<label class="standardInputTitle"><spring:message code="label.county" /></label>
											</div>
											<div style="float:right">
												<spring:message code="title.county" var="county_title"/>
												<form:select id="stdFacilityCounty" tabindex="9" path ="countyFips" class="standardInputMedText mR50" title="${county_title}">
											        <form:option value="" label="--- Select ---" />
													<form:options items="${sessionScope.countyMap}"/>
										    	</form:select>
											</div>
										</div>
									</div>
									
									<div style="clear: both;"><br/></div> <!-- dummy div -->
							          <div style="text-align: center;  margin-bottom: 12px;">
							        		  <a class="a_demo_search" style="color:#FFFFFF; margin-left: 5px;" onclick="dispSearchResults();"> Search </a>
											  	<a class="actionButtons a_demo_search a_demo_reset"
																style="color: #FFFFFF; margin-left: 5px;"
																onclick="resetFields();"> Reset </a>
									
									</div> 
									
									<div>
										<label class="redNote">Note:</label><label>Use '*' for wild card search criteria's</label>
									</div>
								</div>			
							</form:form>
							
							
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						</div>
					</div>
				</div>
			</div>
			<div style="clear: both;">
				<br />
			</div>
			
			<div id="searchResultsDiv" style="margin-left: 20px;" >
				<table id="searchResultsTable"></table>
				<div id="searchResultsPagerDiv"></div>
				<br />
				
				<div id="actionButtons" class="button-row" style="margin-bottom: 15px;">
				    <div id="rawFacilityStandardizationButtons">
						<a id="assignRawFacility" class="actionButtons a_disable fadedAddButton" style ="color:#FFFFFF; margin-left: 5px;" onclick="assginRawFacility(this);" title="Assign raw facility"> Assign Raw Facility </a>
						<a id="newRawFacility" class="actionButtons a_demo_search a_demo_add" style ="color:#FFFFFF; margin-left: 5px;" onclick="assignNewRawFacility();" title="Add new raw facility"> New Raw Facility </a>
					</div>
				</div>
			
			</div>
			
		</div>
		<script src="${staticPath}/js/standardization/facility/ChangeRawFacility.js?v=${buildDate}"></script>
