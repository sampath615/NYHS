<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="cache-control" content="no-cache" />

<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<%@ include file="../common/MainMenuIncludes.jsp"%>

<link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/common.css" rel="stylesheet"
	type="text/css" />


<title>Scanning Report</title>

</head>
<body bgcolor="#e4e4e4"
	background="${staticPath}/images/NYEHMSVirusBackground.png">
	<%@ include file="../common/Menu.jsp"%>
	<div id="floatContent" style="background-color: transparent">
		<div id="mainContentDiv">
			<div id="tabs">
				<ul id="Tabs">
					<li id="scanningReportTab"><a
						href="#scanningReportRevieTab">Scanning Report By Staff</a></li>
				</ul>

				<div id="scanningReportRevieTab" style="clear: both;">

					<div id="scanningReportsTransactionMessage"
						class="transactionMessage">
						<span class="message"></span>
					</div>

					<div id="scanningReportByStaff" class="pT20 pB20">
						<form action="getscanningReportByStaff"
							id="loadScanningReportByStaffData">
							<div id="dateRange" class="floatLeft" style="height: 25px">
								<font class="standardInputTitle" id="textToDate">Year: </font>
								<font class="standardInputTitle" id="textToDate">From: </font>
								<input id="fromDate" class="required  date-picker" readonly /> <font
									class="standardInputTitle" id="workersFont">To: </font> <input
									id="toDate" class="required date-picker" readonly />
							</div>

							<div class="mL50 floatLeft">
								<input type="button"
									onclick="loadScaningReportByStaff();"
									title="Generate Report" value="Generate Report"
									id="loadScanningReportByStaff">
							</div>
						</form>
						</div>
					<div class="clear" style="clear: both;"></div>


					<div id="tableContents"
						class="clearBoth panel-body panel-body-table displayNone"
						style="text-align: center;"></div>
				</div>

			</div>
		</div>

	</div>


	<%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/main.js"></script>
	<script src="${staticPath}/js/common/jquery.fileDownload.js"></script>
	<script src="${staticPath}/js/reports/scanningReport.js?v=${buildDate}"></script>
</body>
</html>