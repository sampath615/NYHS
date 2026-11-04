<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Address Same as</title>
        <%@ include file="../common/common_includes.jsp" %> 
        <%@ include file="../common/commonStylesIncludes.html" %> 
        <%@ include file="../common/MainMenuIncludes.jsp" %>
        <link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" /> 
        <link href="${staticPath}/css/reports/labReports.css?v=${buildDate}" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="${staticPath}/css/reports/toggles-light.css" type="text/css"/>
    </head>

    <body>
        <%@ include file="../common/Menu.jsp"%>
        <div id="addressSameAsPage">
            <ul id="addressSameAsTabs">
                <li><a href="#manageAddressTab" id="manageAddressLink">Address Same as</a></li>

            </ul>

            <div id="manageAddressTab" >


                <div id="manageAddressTransactionMessage" class="transactionMessage"><span class="message"></span></div>			
                <div id="addressSameAsContent" class="jqGridTableSection">
                    <table id="addressSameAsTable"></table>
                    <table id="addressSameAsPager"></table>
                </div>





            </div>
        </div>

    <div id="addAddressInDialog" >
        <div class="width75Perc" style="margin:20px auto">
		
			<div>
				<div class="width25Perc floatLeft">
					<label class="bold">Filter Type:</label>
				</div>
				<div class="">
					<select name="addressFilterType" id="addressFilterType" class="required standardInputSmallText" title="Address Filter Type">
						<option value="">Select</option>
						<option value="Provider">Provider Same As</option>
						<option value="Facility">Facility Same As</option>
					</select>
				</div>
			</div>
			<div class="clearBoth mT20">
				<div class="width25Perc floatLeft">
					<label class="bold">Address:</label>
				</div>
				<div class="">
					<input name="rawAddress" id="rawAddress" class="required standardInputSmallText" title="Raw Address" maxlength="500"/>
				</div>
			</div>
		</div>			
    </div>
   
	
    <%@ include file="../common/commonScriptsIncludes.html"%>
    <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/lab/addressSameAs.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/common/jquery.toggles.js"></script>
	
</body> 