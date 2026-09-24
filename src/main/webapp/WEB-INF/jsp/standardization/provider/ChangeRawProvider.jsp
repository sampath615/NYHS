<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>
<link
	href="${staticPath}/css/standardization/provider/providerStdznHome.css?v=${buildDate}"
	rel="stylesheet" type="text/css" />
		<div id="providerStandardizationTab">
				<div id="providerStandardizationElements">
					<div id="providerSearchFormElements">
						<helpContext class="referenceTarget" context="providerSearchFormManual" ref="help/standardization/provider/unStandardized/providerSearchForm" />
						<div id="provAccordion" style="width: 950px; clear: both;">
							<h3><span id="searchForProviderAccordian">Search For Provider</span><span id="searchForStandardProviderAccordian">Search For Provider Address</span></h3>
							
							<div style="height: 250px;">
								<div id="errorDiv" class="errorblock" style="display: none;">
									All search fields are blank.</div>
								<div>
								
								
								
								
								
								
								
								
								
								
								
									<form:form id="providerSearchForm" name="providerSearchForm" modelAttribute="addressFieldVO">
										  <div class="stdDetailsDiv"> 
											<helpContext class="referenceTarget" context="providerSearchManual" ref="help/standardization/provider/unStandardized/providerSearchForm" />
											<div>&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->
											<div class="leftDiv">
												<div class="standardInputDiv" subProvCrit="provSrch" >
													<helpContext context="common_firstName_search"  />
													<div style="float:left">
														<label for="subProvFName"><spring:message code="label.firstname" /></label>
													</div>
													<div style="float:right">
														<input id="subProvFName" name="providerFname"  tabindex="1" class="standardInputMedText mR50 fieldLevelHelp" value="" maxlength="100" type="text" title="<spring:message code='title.firstName' />">
													</div>
												</div>
												<div class="standardInputDiv" subProvCrit="provSrch">
													<helpContext context="common_lastName_search"  />
													<div style="float:left">
														<label for="subProvLName"><spring:message code="label.lastname" /></label>
													</div>
													<div style="float:right">
														<input id="subProvLName" name="providerLname" tabindex="3"  class="standardInputMedText mR50 fieldLevelHelp" value="" type="text" maxlength="100" title="<spring:message code='title.lastName' />">
													</div>
												</div>
												<div class="standardInputDiv">
													<helpContext context="common_streetAddress_search"  />
													<div style="float:left">
														<label for="subProvStAddrs"><spring:message code="label.streetAddress" /></label>
													</div>
													<div style="float:right">
														<input id="subProvStAddrs" name="address"  tabindex="5" class="standardInputMedText mR50 fieldLevelHelp" value="" type="text" maxlength="100" title="<spring:message code='title.streetAddress' />">
													</div>
												</div>
												<div class="standardInputDiv">
													<helpContext context="common_city_search" />
													<div style="float:left">
														<label for="subProvCity"><spring:message code="label.city"  /></label>
													</div>
													<div style="float:right">
														<input id="subProvCity" name="city"  tabindex="6" maxlength="30"  class="standardInputMedText mR50 fieldLevelHelp" type="text" title="<spring:message code='title.city' />">
													</div>
												</div>
												<div class="standardInputDiv">
													<helpContext context="common_state_search"  />
													<div style="float:left">
														<label for="state"><spring:message code="label.state" /></label>
													</div>
													<div style="float:right">
														<spring:message code="title.state" var="state_title"/>
													    <form:select id="subProvState"  tabindex="7" path ="state" class="standardInputMedText mR50"  title="${state_title}">
														        <form:option value="" label="--- Select ---" />
																<form:options items="${sessionScope.stateMap}"/>
													    </form:select>
													</div>
												</div>
											</div>
											<div class="rightDiv">
											    <div class="standardInputDiv" subProvCrit="provSrch">
													<helpContext context="common_middleName_search" />
													<div style="float:left">
														<label for="subProvMName"><spring:message code="label.middleName" /></label>
													</div>
													<div style="float:right">
														<input id="subProvMName" name="providerMname" tabindex="2"  class="standardInputMedText mR50 fieldLevelHelp" value="" maxlength="100" type="text" title="<spring:message code='title.middleName' />">
													</div>
												</div>
												<div class="standardInputDiv">
													<helpContext context="common_phoneNumber_search" />
													<div style="float:left">
														<label for="subProvPhone"><spring:message code="label.phoneNumber" /></label>
													</div>
													<div style="float:right">
														<input id="subProvPhone" name="phone"  tabindex="4" class="standardInputMedText mR50 mask phoneMask fieldLevelHelp" type="text" value="" title="<spring:message code='title.phoneNumber' />">
													</div>
												</div>
												<div class="standardInputDiv">
													<helpContext context="common_county_search" />
													<div style="float:left">
														<label for="countyFipsCd"><spring:message code="label.county" /></label>
													</div>
													<div style="float:right">
														<spring:message code="title.county" var="county_title"/>
														<form:select id="subProvCounty"  tabindex="8" path="countyFipsCd" class="standardInputMedText mR50" title="${county_title}">
														        <form:option value="" label="--- Select ---" />
																<form:options items="${sessionScope.countyMap}"/>
													    </form:select>
														<!-- <input id="subProvCounty" name="subProvCounty" class="standardInputMedText mR50" type="text"> -->
													</div>
												</div>
												<div class="standardInputDiv">
													<helpContext context="common_zip_search"  />
													<div style="float:left">
														<label for="subProvZip"><spring:message code="label.zip"  /></label>
													</div>
													<div style="float:right">
														<input id="subProvZip" name="zipcode"  tabindex="9" class="standardInputMedText mR50 fieldLevelHelp" type="text" title="<spring:message code='title.zip' />">
													</div>
												</div>
												
												<div class="standardInputDiv" subProvCrit="provSrch">
													<helpContext context="common_npi_search"  />
													<div style="float:left">
														<label for="subProvNPI">NPI</label>
													</div>
													<div style="float:right">
														<input id="subProvNPI" name="npi"  tabindex="10" class="standardInputMedText mR50 fieldLevelHelp" type="text" title="npi">
													</div>
												</div>
												
											</div>
											
											<div style="clear: both;"><br/></div> <!-- dummy div -->
										</div>
										</form:form>
								
								
								
								
								
								
								
								
								
								
								
								
								
								
								
								
								
								</div>
								<div
									style="text-align: center; margin-bottom: 12px; clear: both;">
									<br /> <a class="a_demo_search"
										style="color: #FFFFFF; margin-left: 5px;"
										onclick="dispSearchResults();"> Search </a>
										<a class="actionButtons a_demo_search a_demo_reset"
										style="color: #FFFFFF; margin-left: 5px;"
										onclick="resetFields();"> Reset </a>
								</div>
									<div>
										<label class="redNote">Note:</label><label>Use '*' for wild card search criteria's</label>
									</div>
							</div>
						</div>
					</div>
					<div style="clear: both;">
						<br />
					</div>
					<div id="subProvSearchResults" style="display: none;">
						<helpContext class="referenceTarget" context="providerSearchResultsDataGridManual" ref="help/standardization/provider/unStandardized/providerSearchResultsDataGrid" />
						<table id="subProvAddrsTable"></table>
						<div id="subProvAddrsPager" style="color: #FFFFFF"></div>
						
						
							<div id="actionButtons">
								<div id="rawProvAddrStandardization">
									<a class="actionButtons a_disable fadedAddButton" id="assignRawProvider" style="color: #FFFFFF; margin-left: 5px;" onclick="assginRawProvider(this)" title="Assign raw provider"> Assign Raw Provider </a> 
									<a class="actionButtons a_demo_search a_demo_add" id="assignNewRawProvider" style="color: #FFFFFF; margin-left: 5px;" onclick="assignNewRawProvider();" title="Add new raw provider"> New Raw Provider</a>
								</div>
							</div>
						
					</div>
				</div>

			</div>

		
		<script src="${staticPath}/js/standardization/provider/ChangeRawProvider.js?v=${buildDate}"></script>
