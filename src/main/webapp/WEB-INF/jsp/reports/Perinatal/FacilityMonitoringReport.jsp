<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Facility Monitoring Report</title>
<%@ include file="../../common/common_includes.jsp"%>
<%@ include file="../../common/commonStylesIncludes.html"%>
<%@ include file="../../common/MainMenuIncludes.jsp"%>

<link href="${staticPath}/css/common.css" rel="stylesheet"
	type="text/css" />

<link href="${staticPath}/js/bootstrap-3.3.4/css/bootstrap.css" rel="stylesheet">


</head>

<body class="assignmentReview">
	<%@ include file="../../common/Menu.jsp"%>
	<div id="commonTemplate" class="pageView">
		<ul id="viewTabs">
			<li><a href="#facilityReportCriteria"
				id="facilityReportCriteriaTab">Facility Monitoring Report</a></li>

		</ul>
		<div id="facilityReportCriteria" style="min-height:150px; max-height:700px">
				
				<div id="transactionMessage" class="transactionMessage"><span class="message"></span></div>
				
				<div id="facilityReportCriteriaForm">

			        <div class="form-row">
			            <div class="form-group col-md-1">
			                <label for="criteriaForQuarters">First Quarter</label>
			                <select class="form-control required"  name="birthFirstQuarter" id="birthFirstQuarter" onchange="updateYear(this, true)">
			                    <option value="">Select</option>
			                    <option value="1">Q1</option>
			                    <option value="2">Q2</option>
			                    <option value="3">Q3</option>
			                    <option value="4">Q4</option>
			                </select>
			            </div>
			            <div class="form-group col-md-1 displayNone birthQuarter" id="elementYear">
			                <label for="criteriaForYear">Year</label>
			                <select class="form-control required" name="birthFirstYear" id="birthFirstYear" onchange="updateFacilityDetails(this, true)">
			                    <option value="">Select</option>
			                </select>
			            </div>
			      </div>
			      <div class="form-row displayNone" id="secondQuarterElements">
			      		 <div class="form-group col-md-1">
			                <label for="criteriaForQuarters">Second Quarter</label>
			                <select class="form-control required readonly"  name="birthSecondQuarter" id="birthSecondQuarter"">
			                    <option value="">Select</option>
			                    <option value="1">Q1</option>
			                    <option value="2">Q2</option>
			                    <option value="3">Q3</option>
			                    <option value="4">Q4</option>
			                </select>
			            </div>
			            <div class="form-group col-md-1">
			                <label for="criteriaForYear">Year</label>
			                <select class="form-control required readonly" name="birthSecondYear" id="birthSecondYear">
			                    <option value="">Select</option>
			                </select>
			            </div>
			            <div class="form-group col-md-4">
			                <label for="criteriaForYear">Facility Name</label>
			                <select class="form-control required" name="facilityName" id="facilityName">
			                    <option value="ALL">All</option>
			                    
			                </select>
			            </div>
			            <div class="form-group col-md-1">
			                <label for="criteriaForYear">Letter Date</label>
			                <input class="form-control required date-picker" name="letterDate" id="letterDate"/>
			            </div>			            
			            <div class="form-group col-md-1 displayNone birthYear birthQuarter elementFacilityName" id="elementSubmit">
			               <button class="btn btn-info mT20" onclick="loadFacilityReport()">Submit</button>
			            </div>
			        </div>
			    </div>
			    
			    
			    <table class=" bordered displayNone" border="1" cellspacing="0" id="pfiAttachmentsResult" >
						
					<thead>
						<tr><th class="width25Perc">PFI</th><th class="width75Perc">Message</th></tr>
					</thead>
					<tbody id="failedPFIData">
						
					
					</tbody>
				</table>
				
				
		</div>
	</div>





	
	<%@ include file="../../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/review/nbs/nbsReport.js?v=${buildDate}"></script>
</body>