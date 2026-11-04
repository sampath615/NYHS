<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<title>Raw Provider Standardization </title>
	
	<%@ include file="../../common/common_includes.jsp" %> 
    <%@ include file="../../common/commonStylesIncludes.html" %> 
    <%@ include file="../../common/MainMenuIncludes.jsp" %>
        
	<link href="${staticPath}/css/jquerycombobox.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/standardization/provider/providerStdznHome.css?v=${buildDate}" rel="stylesheet" type="text/css" />	
	
</head>

<body>

	<div id="floatContent"  style="background-color: transparent" >
	<%@ include file="../../common/Menu.jsp" %>
		<div id="mainContentDiv">		
				<div id="tabs">
					<ul id="Tabs">
						<li id="listRawProviderAddressesTab"><a href="#rawProviderAddressesTab">Raw Provider Addresses</a></li>
				 		<li id="listRawProviderAddressesStdTab" class="displayNone"><a href="#rawProviderAddressesStdTab" >Standardize Raw Addresses</a></li>
					</ul>
					
 					<div id="rawProviderAddressesTab" style="clear: both;">
						<div id="rawProviderAddtressTransactionMessage" class="transactionMessage"><span class="message"></span></div>
						
						<div id="rawProviderAddressesRecords" class="jqGridTableSection">
							<table id="rawProviderAddressesTable"></table>
							<table id="rawProviderAddressesPager"></table>				
						</div>	
						
						<c:if test="${userPermissions['38'] != null}" >
							<div id="actionButtons" class="button-row pT20" style=" text-align:center"> 
									<a id="standardizeNewBtn" class="actionButtons a_disable fadedStandardButton" style="color: #FFFFFF; margin-left: 5px;">Standardize </a>		
									<a class="actionButtons a_disable fadedStandardFacilityButton" id="flagAsFacilityBtn" style="color: #FFFFFF; margin-left: 5px;">Flag As Facility </a>
							</div>		
						</c:if>
					</div>
		            
		            <div id="rawProviderAddressesStdTab" style="clear: both;">
		                    <div id="rawProviderAddressesStd1">
		                    	<div id="rawProvAddressSelectedDiv">
		                        	<table id="selectedAddressesTable"></table>
									<table id="selectedAddressesPager"></table>		 
<%-- 									<c:if  test="${userPermissions['38'] != null}" >
										<div id="actionButtons">
											<a class="actionButtons a_disable fadedStandardButton"
												id="flagAsFacilityBtn" style="color: #FFFFFF; margin-left: 5px;">Flag As Facility </a>
										</div>
									</c:if>			 --%>
		                        </div>
		                    </div>
		                    <div id="rawProvAddressesBorder" ></div>
		                    <div id="rawProviderAddressesStd2" >
		                           <div id="subProvNewSearchDiv">
										<div id="provNewAccordion" style="width: 900px; clear: both;">
											<h3><span id="searchForProviderAccordian">Search For Provider</span>
												<span id="searchForStandardProviderAccordian">Search For Provider Address</span></h3>
											<div style="height: 250px;">
												<div id="errorDiv" class="errorblock" style="display: none;">
													All search fields are blank.</div>
												<div><jsp:include page="search/SubProvNewSearchCriteria.jsp"></jsp:include></div>
												<div style="text-align: center; margin-bottom: 12px; clear: both;"><br/> 
														<a class="a_demo_search" style="color: #FFFFFF; margin-left: 5px;" onclick="dispSearchResults();"> Search </a>
														<a class="actionButtons a_demo_search a_demo_reset" style="color: #FFFFFF; margin-left: 5px;" onclick="resetFields();"> Reset </a>
												</div>
												<div id="nyehmsSearch"><label class="redNote" style="margin: 0">Note:</label><label>Use '*' for wild card search criteria's</label></div>
												<div id="npiRegistrySearch"><label class="redNote" style="margin: 0">Note:</label><label>If NPI is provided with other data, only NPI is used for search.
																								Registry allow search only on First Name, Last Name, City, State, Zip and NPI.</label>
												</div>
											</div>
										</div>
		                           </div>
							<div style="clear: both;"><br/></div>
							<div id="subProvSearchNewResults" style="display: none;">
								<table id="subProvAddrsNewTable"></table>
								<div id="subProvAddrsNewPager" style="color: #FFFFFF"></div>
								
								<c:if  test="${userPermissions['38'] != null}" >
									<div id="actionButtons">
										<a class="actionButtons a_disable fadedStandardButton"
											id="standardizeFinalBtn" style="color: #FFFFFF; margin-left: 5px;">Standardize </a>
										<div id="rawProvAddrStandardization">
											<a class="actionButtons a_disable fadedAddButton"
												id="newAddressBtn" style="color: #FFFFFF; margin-left: 5px;"onclick="addAddress();"> New Address </a>
											<a class="actionButtons a_demo_search a_demo_add" id="newProviderAddressBtn"
												style="color: #FFFFFF; margin-left: 5px;" onclick="addProviderAndAddress();"> New Provider & Address</a>
<!-- 											<a class="actionButtons a_demo_search a_demo_add" id="newNPIProviderAddressBtn"
												style="color: #FFFFFF; margin-left: 5px;" onclick="addNPIProviderAndAddress();"> NPI Provider & Address</a> -->
										</div>
										<div id="orphanProvAddrStandardization">
											<a class="actionButtons a_disable fadedAddButton"
												id="newProviderBtn" style="color: #FFFFFF; margin-left: 5px;"
												onclick="addProvider();"> New Provider</a>
										</div>
									</div>
								</c:if>
							</div>
		                 </div>
		            </div>
		            

				</div>		
		</div> 
	</div>	

	<div id="newPageStandardizeDialog">
<%--		<jsp:include page="confirm/rawToSubConfirmDialog.jsp"></jsp:include>
 		<jsp:include page="confirm/orphanProviderConfirmDialog.jsp"></jsp:include> --%>
	</div>
		
	<div id="confirmDialog">
		<div id="confirmRawProviderAddressDiv">
			<table id="confirmRawProviderAddressTable"></table>
			<div id="confirmRawProviderAddressPager"></div>
		</div>
	</div>

	<div id="confirmMessageDialog">
		<h4 id="confirmMessageContent"></h4>
		<div id ="npiSearchStatediv" style="font-weight:bold;margin:5px;">
		  	<span>Search NPI Registry for providers in State</span>
			<span>
				<select id="npiSearchState" name="npiSearchState" class="standardInputMedText mR50 required" title="NPI provider Search in State">
					<option value="">ALL</option>
		 			<c:forEach items="${sessionScope.stateMap}" var="state">
		 				<option value="${state.key}">${state.value}</option>
					</c:forEach> 
				</select>
			</span>
		</div>
	</div>
	
	<div id="multiValueStringDialog">
		<table id="multiValueStringTable"></table>
	</div>

	<div id="addProvAddrsModal" class="userAddressDtls" title="Adding New Provider and Address">
			<p><font class="required" title="Required Field">*</font> Indicates Required Field.</p>
			<jsp:include page="add/AddProviderAndAddress.jsp"></jsp:include>
	</div>
	<form id="provNewReload" action="" method="post"></form>
	<input type="hidden" id="newPageType" value="${param.id}">
	
	<%@ include file="../../common/addressComboDialog.jsp" %>		
    <%@ include file="../../common/commonScriptsIncludes.html" %>

	  
	<script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
	<script src="${staticPath}/js/common/addressComboDialog.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>	
    <script	src="${staticPath}/js/standardization/provider/providerStdznHomeNew.js?v=${buildDate}"></script>	
	<%-- <script src="${staticPath}/js/standardization/provider/providerStdznHome.js?v=${buildDate}"></script> --%>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>	  
</body>
</html>


