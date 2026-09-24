<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>

<link href="${staticPath}/css/docs/prfForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />

<style>
		* {box-sizing: border-box}
		body {font-family: "Lato", sans-serif;}
		
		/* Style the tab */
		.tab {
		    float: left;
		    border: 1px solid #ccc;
		    background-color: #f1f1f1;
		    width: 10%;
		    height: auto;
		}
		
		/* Style the buttons inside the tab */
		.tab button {
		    display: block;
		    background-color: inherit;
		    color: black;
		    padding: 22px 16px;
		    width: 100%;
		    border: none;
		    outline: none;
		    text-align: left;
		    cursor: pointer;
		    transition: 0.3s;
		    font-size: 17px;
		}
		
		/* Change background color of buttons on hover */
		.tab button:hover {
		    background-color: #ddd;
		}
		
		/* Create an active/current "tab button" class */
		.tab button.active {
		    background-color: #ccc;
		}
		
		/* Style the tab content */
		.tabcontent {
		    float: left;
		    padding: 0px 12px;
		    border: 1px solid #ccc;
		    width: 90%;
		    border-left: none;
		    height: auto;
		    border-style: solid;
		    border-color: #ccc;
		}
	
	
	.ui-jqgrid{
		margin : 0px 0px 0px 15px;
	}
		
	</style>

<body>
<div id="Hl7DocumentAddressTransactionMessage" class="transactionMessage">
						<span class="message"></span></div>
<div style="text-align: center; " class="headingCls">
			<p>HL7 DOCUMENT SUMMARY</p>
</div>
	<div class="container" style="width: 100%">
		<div
			style="padding-bottom: 5px; padding-top: 5px; padding-right: 44px; padding-left: 35px;">
			<div style="float: left; width: 40%">
				<div>
					<span class=" ml10"><spring:message code="label.stateNo" />:
					</span> <span class="ml10"><form:input path="stdRawHL7DocVO.personStdData.ccdMatchedStateNo" class="standardInputSmallText" maxlength="10" readonly="true" /></span>
				</div>
			</div>

			<div style="float: left; width: 40%">
				<div>
					<span class="mL20">CCD DOCUMENT ID: </span> 
					<span class=" "><form:input id="DocumentID" path="stdRawHL7DocVO.ccdMatchDocId" class="standardinputSmallText mr30" maxlength="6" readonly="true" /></span>
				</div>
			</div>
		</div>
		<div
			style="padding-bottom: 5px; padding-top: 25px; padding-right: auto; padding-left: 45px;">
			<!--  <jsp:include page="statenoDetails.jsp"></jsp:include>-->
			<div class="clearBoth container pB10 w100 nonRawEditFields">
				<div style="float: left; padding-bottom: 10px; width: 40%">
					<div class="positionAbsolute">
						<span class=" ml10">CITYNO: </span> <span
							class="ml10 showMultiData" id="nycCITYNO_docViewer"><c:out
								value="${nyehmsStatenoDetails.NYC_CITYNO}" /></span>


						<c:if
							test="${nyehmsStatenoDetails.NYC_CITYNO_LIST != null && nyehmsStatenoDetails.NYC_CITYNO_LIST != '[]' }">
							<span class="historyCharacter">*</span>
							<div class='displayNone stateNoHistoryData nycCITYNO_docViewer'>
								<table
									class='statenoHistory historyTable<c:out value="${personViewNumber}" />'>
									<tr>
										<th class="historyTableHeader">CITYNO</th>
									</tr>
									<c:forEach var="item"
										items="${nyehmsStatenoDetails.NYC_CITYNO_LIST}">
										<tr>
											<td>${item}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:if>


					</div>
				</div>
				<div style="float: left; padding-bottom: 10px; width: 33%">
					<div class="positionAbsolute">
						<span class="ml">eHARS NYC STATENO: </span> <span
							class="ml10 showMultiData" id="nycEHARS_STATENO_docViewer"><c:out
								value="${nyehmsStatenoDetails.NYC_EHARS_STATENO}" /></span>

						<c:if
							test="${nyehmsStatenoDetails.NYC_EHARS_STATENO_LIST != null && nyehmsStatenoDetails.NYC_EHARS_STATENO_LIST != '[]' }">
							<span class="historyCharacter">*</span>
							<div
								class='displayNone stateNoHistoryData nycEHARS_STATENO_docViewer'>
								<table
									class='statenoHistory historyTable<c:out value="${personViewNumber}" />'>
									<tr>
										<th class="historyTableHeader">eHARS NYC STATENO</th>
									</tr>
									<c:forEach var="item"
										items="${nyehmsStatenoDetails.NYC_EHARS_STATENO_LIST}">
										<tr>
											<td>${item}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:if>

					</div>
				</div>
				<div
					style="float: left; padding-bottom: 10px; width: 27%; text-align: right">

					<div class="positionAbsolute">
						<span class="mL"> eHARS NYS STATENO: </span> <span
							class="ml10 showMultiData" id="nysEharsStateno_docViewer"><c:out
								value="${nyehmsStatenoDetails.EHARS_STATENO}" /></span>

						<c:if
							test="${nyehmsStatenoDetails.EHARS_STATENO_LIST != null && nyehmsStatenoDetails.EHARS_STATENO_LIST != '[]' }">
							<span class="historyCharacter">*</span>
							<div
								class='displayNone stateNoHistoryData nysEharsStateno_docViewer'>
								<table
									class='statenoHistory historyTable<c:out value="${personViewNumber}" />'>
									<tr>
										<th class="historyTableHeader">eHARS NYS STATENO</th>
									</tr>
									<c:forEach var="item"
										items="${nyehmsStatenoDetails.EHARS_STATENO_LIST}">
										<tr>
											<td>${item}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:if>

					</div>
				</div>
			</div>
		</div>
	</div>

<div id ="searchDateFields" style ="width: auto; padding: 15px; border : 1px dotted; ">	
		<span>
			<label class=" ml10">START DATE: </label>&nbsp;
			<input type="text" id="datepickerstart"  name="stdRawHL7DocVO.personStdData.startDate" value="${stdRawHL7DocVO.personStdData.startDate}" class="required noDisabled standardInputSmallText" title ="Start Date" datevalidations="completeDate" style ="width: 150px;"/>
		</span>
		<span>		
			<label class=" ml10">END DATE: </label>&nbsp;
			<input type="text" id="datepickerend" name="stdRawHL7DocVO.personStdData.endDate" value="${stdRawHL7DocVO.personStdData.endDate}" class="noDisabled required standardInputSmallText" title="End Date" datevalidations="completeDate" style ="width: 150px;"/>
		</span>
		<span>	
			<a tabindex="3" id="searchResultsButton" onclick="LoadSearchedDatainTable();" style="color: #FFFFFF; margin-left: 5px;" class="a_demo_search"> Search </a>
		</span>
		<span>	
			<a class="actionButtons a_demo_search a_demo_reset"	style="color: #FFFFFF; margin-left: 5px;" type="reset" onclick="resetForms();"> Reset </a>
		</span>

</div>
<!--  <div id ="searchDateFields">		
</div>-->
						
				
<br/>
		<div class="tab">
		  <button class="tablinks" onclick="openTab(this, 'Demographics')" id="demographicsBtn" style="padding: 22px 5px">Demographics</button>
		  <button class="tablinks" onclick="openTab(this, 'Encounter')" id="encounterBtn" style="padding: 22px 5px">Encounters</button>
		  <button class="tablinks" onclick="openTab(this, 'Diagnosis')" id="diagnosisBtn" style="padding: 22px 5px">Diagnosis</button>
		  <button class="tablinks" onclick="openTab(this, 'Labtest')" id="labTestsBtn" style="padding: 22px 5px">Lab Tests</button>
		  <button class="tablinks" onclick="openTab(this, 'Medication')" id="medicationsBtn" style="padding: 22px 5px">Medications</button>		  
		</div>


		<div id="Demographics" class="tabcontent">
			<div id="hl7DemographicsMultiValContainer">
				<table id="hl7DemographicsMultiValues"></table>
			</div>
			<div id="hl7DOBDemographicsMultiValContainer">
				<table id="hl7DOBDemographicsMultiValues"></table>
			</div>
	  		<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; margin-top: 5px;">
				<span class=" mL">Person Details</span>
			</div>
			<div id="demographicsContent" style="padding: 9px 0px 0px 8px;">
					<jsp:include page="HL7Demographics.jsp" >
						<jsp:param name="stdRawHL7DocVO" value="${stdRawHL7DocVO}"/>
					</jsp:include>
						
			
			</div>
			<div id="addressDetailsSection" class="jqGridTableSection">
				<table id="addressDetails"></table>
				<table id="hl7AddressPager"></table>
			</div>
			
			<div id="insuranceDetailsSection" class="jqGridTableSection" style = "padding: 20px 6px 10px 0px;">
				<table id="insuranceDetails"></table>
				<table id="hl7InsurancePager"></table>
			</div>
	</div>
	
	<div id="Diagnosis" class="tabcontent">	
		<div id="diagnosisGrid" class="jqGridTableSection" style="padding : 10px">
				<table id="hl7DiagnosisTable"></table>
				<table id="hl7DiagnosisPager"></table>
		</div>
	</div>
	<div id="Encounter" class="tabcontent">
	  <div id="encounterGrid" class="jqGridTableSection" style="padding : 10px">
			<table id="hl7EncounterTable"></table>
			<table id="hl7EncounterPager"></table>
	  </div>
	</div>
	<div id="Medication" class="tabcontent">
		<div id="medicationGrid" class="jqGridTableSection" style="padding : 10px">
				<table id="hl7MedicationTable"></table>
				<table id="hl7MedicationPager"></table>
		</div>
	</div>
	<div id="Labtest" class="tabcontent">
	  <div id="LabTestGrid" class="jqGridTableSection" style="padding : 10px">
			<table id="labtestTable"></table>
			<table id="labtestPager"></table>
	  </div>
	</div>
<script src="${staticPath}/js/utils/hl7Document.js?v=${buildDate}"></script>
</body>																