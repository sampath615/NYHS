<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Lab Process</title>
        <%@ include file="../common/common_includes.jsp" %> 
        <%@ include file="../common/commonStylesIncludes.html" %> 
        <%@ include file="../common/MainMenuIncludes.jsp" %> 
        <link href="${staticPath}/css/reports/labReports.css?v=${buildDate}" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="${staticPath}/css/reports/toggles-light.css" type="text/css"/>
    </head>

    <body>
        <%@ include file="../common/Menu.jsp"%>
        <div id="proficiencyPage">
            <ul id="ProficiencyMaintenanceTabs">
                <li><a href="#ProficiencyTestTab" id="TestResultsDomain">Process Tests</a></li>

            </ul>

            <div id="ProficiencyTestTab" >


                <div id="labProficiencyTestTransactionMessage" class="transactionMessage"><span class="message"></span></div>			
                <div id="labProficiencyTestResult" class="jqGridTableSection">
                    <table id="labProficiencyTestTable"></table>
                    <table id="labProficiencyTestPager"></table>
                </div>





            </div>
        </div>
    </div>

    <div id="addLabConditionDialog" >
        <div class="stdDetailsDiv" id="stdInfoDiv">
            <div id="addingLabDetails" >

            </div>
        </div>			
    </div>
    
    <div id="createFilter" style="display:none">
        <div id="filterDetails" style="margin-top:20px">
           <table id="filterDataTable"></table>
           <div id="filterDataPager"></div>
           
         </div>
    </div>

   	<div id="addLabProficiencyDiv" class="displayNone">
   	<div style="float:left; width:99%; padding-bottom:5px; margin-left:5px; margin-right:5px; margin-top:2px;" class="hivNonTypeTestsDiv" id="hivNonTypeTestsDiv"> 
				<div class="abtNTDrpDwn mL20" style="float:left; width:30%;">
					<select onchange="getResultForTestType('testFilter20000_FIELD_NAME', '20000', 'hivNonTypeTestsDiv', 'FIELD_NAME');" class="abtNTTest required" name="testFilterFieldName" count="20000" id="testFilter20000_FIELD_NAME" title="Field Name">
						<option value="" selected="selected">Select</option>
		                <option value="FIRST NAME" >FIRST NAME</option>
						<option value="MIDDLE NAME">MIDDLE NAME</option>
		                <option value="LAST NAME">LAST NAME</option>
		                <option value="DOB">DOB</option>	
		                <option value="ACCESSION NUM">ACCESSION NUM</option>
                        <option value="OBSERVATION RESULT TEXT">OBSERVATION RESULT TEXT</option>
                        <option value="INTERNAL ID">INTERNAL ID</option>
    					<option value="PATIENT ADDRESS LINE1">PATIENT ADDRESS LINE1</option>
                        <option value="PATIENT ADDRESS LINE2">PATIENT ADDRESS LINE2</option>
                        <option value="PATIENT CITY">PATIENT CITY</option>
                        <option value="PATIENT STATE">PATIENT STATE</option>
                        <option value="FACILITY NAME">FACILITY NAME</option>
                        <option value="FACILITY ADDRESS LINE1">FACILITY ADDRESS LINE1</option>
                        <option value="FACILITY ADDRESS LINE2">FACILITY ADDRESS LINE2</option>
                        <option value="FACILITY CITY">FACILITY CITY</option>
                        <option value="PROVIDER FIRST NAME">PROVIDER FIRST NAME</option>
                        <option value="PROVIDER LAST NAME">PROVIDER LAST NAME</option>
                        <option value="PROVIDER ADDRESS LINE1">PROVIDER ADDRESS LINE1</option>
                        <option value="PROVIDER ADDRESS LINE2">PROVIDER ADDRESS LINE2</option>
                        <option value="PROVIDER CITY">PROVIDER CITY</option>
                        <option value="LOINC">LOINC</option>
					</select>								
				</div>
		   
		        <div style="float:left; width:20%;">
					<select onchange="getResultForTestType('testFilter20000_operator', '20000', 'hivNonTypeTestsDiv', 'operator');" class="abtNTTest" name="testFilterOperator" count="20000" id="testFilter20000_operator" title="Operator">
		                <option value="LIKE" selected="selected">LIKE</option>
		                <option value="STARTWITH">STARTWITH</option>
		                <option value="EQUAL">EQUAL</option>
		                <option value="NOTEQUAL">NOTEQUAL</option>
		                <option value="NOTSTARTWITH">NOTSTARTWITH</option>	
		                <option value="NOTLIKE">NOTLIKE</option>
					</select>							
				</div>	
								
		        <div style="float:left; width:20%;">
					<input cssstyle="width: 75px;" placeholder="Value" onchange="getResultForTestType('testFilter20000_value', '20000', 'hivNonTypeTestsDiv', 'value');" onkeyup="capitalize(this.id,this.value)" maxlength="100" class="standardInputSmallText2 quanCP" value="" name="testFilterValue" count="20000" id="testFilter20000_value" title="Field Value">						
				</div>	
		                                                
		        <div style="float:left; width:20%;">
					<select onchange="getResultForTestType('testFilter20000_condition', '20000', 'hivNonTypeTestsDiv', 'condition');" class="required abtNTTest" name="testFilterCondition" count="20000" id="testFilter20000_condition">
						<option value=""  selected="selected"> Select </option>
						<option value="AND">AND</option>
		                <option value="OR">OR</option>
		            </select>							
				</div>	        
							
				<div style="float:left;width:10%;">
					<span style="float:right; margin-right:10px;" class="mL2"><img style="vertical-align: middle; width: 14px;" title="Click to Delete" src="${staticPath}/images/icons/Delete-icon.png" onclick="deleteTestDiv(this, 'testFilter20000_condition', 'hivNonTypeTestsDiv');"></span>
				</div>
			</div>
   	
   	
   	</div>
	
    <%@ include file="../common/commonScriptsIncludes.html"%>
    <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/reports/labCommonUtil.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/reports/LabProficiency.js?v=${buildDate}"></script>
    <script src="${staticPath}/js/common/jquery.toggles.js"></script>
    <script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
	
</body> 