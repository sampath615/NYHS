<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Facility Search Page</title>
<%@ include file="../../common/common_includes.jsp" %> 
<%@ include file="../../common/commonStylesIncludes.html" %> 
<%@ include file="../../common/MainMenuIncludes.jsp" %>
 
<link href="${staticPath}/css/standardization/facility/mainsearch.css?v=${buildDate}" rel="stylesheet" type="text/css" />

</head>
<body>
	<%@ include file="../../common/Menu.jsp" %>
	
	<div id="mainSearch">
	
		<ul>
			<li><a href="#searchPageTab">Facility Search</a></li>
		</ul>
		
		<div id="searchPageTab" style=""> 
			
			<div id="accordion" style="width: 950px;">
				<h3>Search Criteria</h3>
                <div style="height: 210px;">
                	<div id="errorDiv" class="errorblock" style="display: none;">
                		All search fields are blank.
                	</div>
	               	<span class="searchFacilityTxt"> Search Facility</span> 
	               	<span>
						<select id="searchOptions" class="searchOptions" onchange="changeSearchCrit(this.value)">
							<!-- <option value="0">Select</option> -->
							<option value="1">Master Facility</option>
							<option value="2">Sub Facility</option>
							<option value="3">Raw Facility</option>
						</select>
					</span>
					<div style="margin-top: 10px;">
						<jsp:include page="search/StdznSearchCriteria.jsp"></jsp:include>
					</div>
				</div>
			</div>
			
        	<div style="clear: both;"><br/></div>
        	
			<div id="srchResultsDiv">
				<div id="workTypeRadioDiv">
					<c:if  test="${userPermissions['43'] != null}" >
						Choose Work Area Type : &nbsp;
						<input type="radio" name="selWorkTypeRadio" value="Grand" onclick="setFacWorkType(this.value)" id="grandOption"><font id="grandText">Master Facility</font>
						<input type="radio" name="selWorkTypeRadio" value="Sub"   onclick="setFacWorkType(this.value)" id="subOption"  ><font id="subText">Sub Facility</font>
						<input type="radio" name="selWorkTypeRadio" value="Raw"   onclick="setFacWorkType(this.value)" id="rawOption" ><font id="rawText">Raw Facility</font>
					</c:if>
				</div>
				
				<table id="searchResultsTable"></table>
				<div id="searchResultsPagerDiv"></div>
			</div>			
			<br/>
			
			<c:if  test="${userPermissions['43'] != null}" >
				<!-- Work Area -->
				<div id="workArea">
					<div style="margin-bottom: 20px;"></div>
					<!-- Buttons  -->
					<div style="text-align: center; margin-bottom: 30px;">
						<a onclick="confirmForUnlink();" class="actionButtons a_continue enabledArrowButton" style="color: #FFFFFF;" id="unLink">Un-Link</a>
						<a onclick="checkForMerge();" class="actionButtons a_continue enabledArrowButton" style="color: #FFFFFF;" id="merge">Merge</a>
					</div>
				</div>
			</c:if>
			
			<!-- Confirm Dialog -->
			<div id="confirmDialog">
				<div id="confirmGrandFacilityDiv">
					<table id="confirmGrandFacilityTable"></table>
					<div id="confirmGrandFacilityPager"></div>
					<div class="alignCenter marginBottom30 marginTop20" >
						<a class="a_disable fadedSubmitButton mergingButton" href="javascript:confirmMerge();">OK</a>
						<a class="actionButtons a_demo_search a_demo_cancel" href="#" onclick="closeStdDialog(this)">Cancel</a>
					</div>
				</div>
				
				<div id="confirmSubFacilityDiv">
					<table id="confirmSubFacilityTable"></table>
					<div id="confirmSubFacilityPager"></div>
					<div class="alignCenter marginBottom30 marginTop20" >
						<a class="a_disable fadedSubmitButton mergingButton" href="javascript:loadConfirmGrand();">OK</a>
						<a class="actionButtons a_demo_search a_demo_cancel" href="#" onclick="closeStdDialog(this)">Cancel</a>
					</div>
				</div>
				
				<div id="confirmRawFacilityDiv">
					<table id="confirmRawFacilityTable"></table>
					<div id="confirmRawFacilityPager"></div>
				</div>
			</div>
			
		</div>
	
		
		
	</div>
	<div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
	</div>
	<div id="multiValueStringDialog">
		<table id="multiValueStringTable"></table>
	</div>
	
	<%@ include file="../../common/commonScriptsIncludes.html" %> 
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
	<script src="${staticPath}/js/standardization/facility/mainSearch.js?v=${buildDate}"></script>
</body>
</html>