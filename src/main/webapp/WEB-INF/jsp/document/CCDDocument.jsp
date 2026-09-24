<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<!--<c:out value="${stdRawCCDDocVO}" />-->
<div style="text-align: center; " class="headingCls">
			CONTINUITY OF CARE DOCUMENT
</div>
<div class="container" style=" width:100%">
		<div style="padding-bottom:10px; text-align: right; padding-right: 30px;">
				
		            <span class="mL10 bold">DOCUMENT ID: </span>
					<span class=" "><form:input id="DocumentID" path="stdRawCCDDocVO.personStdData.documentId" class="standardinputSmallText mr30" maxlength="6" readonly="true"/></span>
		 </div>
		 <div style="padding-bottom:5px;padding-top: 5px;padding-right: 44px; padding-left: 45px;">
		 		<div style="float:left; width: 40%">
					 <div>
					 	<span class=" ml10"><spring:message code="label.stateNo" />: </span>
				        <span class="ml10"><form:input path="stdRawCCDDocVO.personStdData.stateno" class="standardInputSmallText" maxlength="10" readonly="true"/> </span>
					 </div>    
				 </div>
				 
				 <div style="float:left; width: 29%">
					  <div>
			              <span class="ml25"> eHARS Import Date: </span>
				 	      <fmt:formatDate value="${stdRawCCDDocVO.personStdData.eharsImportDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
				 		  <span class="ml10"><form:input path="stdRawCCDDocVO.personStdData.eharsImportDate" id ="EHARS_IMPORT_DATE" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/> </span>
		               </div>            
		             </div>
		             
		              <div style="float:left; width: 30%; text-align: right;">
		                  <div>
			                <span >NYC SHIP Date:</span>
			                <span ><input path="" class="standardInputSmallText"/></span>
			              </div>
		            	</div> 
		</div>
		<div style="padding-bottom:5px;padding-top: 25px;padding-right: auto; padding-left: 45px;">
		    <!--  <jsp:include page="statenoDetails.jsp"></jsp:include>-->
		    <div class="clearBoth container pB10 w100 nonRawEditFields">
            <div style="float:left; padding-bottom:10px; width: 40%">
	            <div class="positionAbsolute">
	                <span class=" ml10">CITYNO: </span>
	                <span class="ml10 showMultiData" id="nycCITYNO_docViewer"><c:out value="${nyehmsStatenoDetails.NYC_CITYNO}" /></span>
	                
	                
	                <c:if test="${nyehmsStatenoDetails.NYC_CITYNO_LIST != null && nyehmsStatenoDetails.NYC_CITYNO_LIST != '[]' }">
						<span class="historyCharacter">*</span>
						<div
							class='displayNone stateNoHistoryData nycCITYNO_docViewer'>
							<table
								class='statenoHistory historyTable<c:out value="${personViewNumber}" />'>
								<tr>
									<th class="historyTableHeader">CITYNO</th>
								</tr>
								<c:forEach var="item" items="${nyehmsStatenoDetails.NYC_CITYNO_LIST}">
									<tr>
										<td>${item}</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</c:if>
	                
	                
	            </div>
            </div>
            <div style="float:left; padding-bottom:10px; width: 33%">
            	<div class="positionAbsolute">
	                <span class="ml10">eHARS NYC STATENO: </span>
	                <span class="ml10 showMultiData" id="nycEHARS_STATENO_docViewer"><c:out value="${nyehmsStatenoDetails.NYC_EHARS_STATENO}" /></span>
	                
	                <c:if test="${nyehmsStatenoDetails.NYC_EHARS_STATENO_LIST != null && nyehmsStatenoDetails.NYC_EHARS_STATENO_LIST != '[]' }">
						<span class="historyCharacter">*</span>
						<div
							class='displayNone stateNoHistoryData nycEHARS_STATENO_docViewer'>
							<table
								class='statenoHistory historyTable<c:out value="${personViewNumber}" />'>
								<tr>
									<th class="historyTableHeader">eHARS NYC STATENO</th>
								</tr>
								<c:forEach var="item" items="${nyehmsStatenoDetails.NYC_EHARS_STATENO_LIST}">
									<tr>
										<td>${item}</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</c:if>
	                
	            </div>                    
            </div>
            <div style="float:left; padding-bottom:10px; width: 27%; text-align: right">
            	        	
	            <div class="positionAbsolute">
	                <span class="mL"> eHARS NYS STATENO:	 </span>
	                <span class="ml10 showMultiData" id="nysEharsStateno_docViewer"><c:out value="${nyehmsStatenoDetails.EHARS_STATENO}" /></span>

						<c:if test="${nyehmsStatenoDetails.EHARS_STATENO_LIST != null && nyehmsStatenoDetails.EHARS_STATENO_LIST != '[]' }">
							<span class="historyCharacter">*</span>
							<div
								class='displayNone stateNoHistoryData nysEharsStateno_docViewer'>
								<table
									class='statenoHistory historyTable<c:out value="${personViewNumber}" />'>
									<tr>
										<th class="historyTableHeader">eHARS NYS STATENO</th>
									</tr>
									<c:forEach var="item" items="${nyehmsStatenoDetails.EHARS_STATENO_LIST}">
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
<br/>
		<div class="tab">
		  <button class="tablinks" onclick="openTab(this, 'Demographics')" id="demographicsBtn" style="padding: 22px 5px">Demographics</button>
		  <button class="tablinks" onclick="openTab(this, 'Providers')" id="providerBtn" style="padding: 22px 5px">Providers</button>
		  <button class="tablinks" onclick="openTab(this, 'Problems')" id="problemBtn" style="padding: 22px 5px">Problems</button>
		  <button class="tablinks" onclick="openTab(this, 'Encounters')" id="encounterBtn" style="padding: 22px 5px">Encounters</button>
		  <button class="tablinks" onclick="openTab(this, 'LabTest')" id="labtestBtn" style="padding: 22px 5px">Lab Tests</button>
		  <button class="tablinks" onclick="openTab(this, 'Medications')" id="medicationsBtn" style="padding: 22px 5px">Medications</button>
		  <button class="tablinks" onclick="openTab(this, 'Payers')" id="payersBtn" style="padding: 22px 5px">Payers</button>
		</div>


		<div id="Demographics" class="tabcontent">
			<div id="demographicsContent" style="padding: 9px 0px 0px 8px;">
					<jsp:include page="CCDDemographics.jsp" >
						<jsp:param name="stdRawCCDDocVO" value="${stdRawCCDDocVO}"/>
					</jsp:include>		
			</div>
			 <div id="addressDetailsSection" class="jqGridTableSection" style = "padding: 2px 9px 21px 28px;">
				<table id="addressDetails"></table>
				<table id="ccdAddressPager" ></table>
			</div>
			
			<div id="button-row" class="button-row documentButtons center" style = "align-content: center; padding:5px;">
		 		<c:if test="${userPermissions['90'] != null && stdRawCCDDocVO.personStdData.stateno != null && stdRawCCDDocVO.personStdData.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null}">
						<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
				</c:if>									
			</div>
			
			<!-- <div id="insuranceDetailsSection" class="jqGridTableSection" style = "padding: 20px 6px 10px 0px;">
				<table id="insuranceDetails" class ="ui-jqgrid"></table>
				<table id="hl7InsurancePager" class ="ui-jqgrid"></table>
			</div>-->
	</div>
	
	  <div id="Providers" class="tabcontent">	
		<div id="providersGrid" class="jqGridTableSection" style="padding : 10px">
				<table id="ccdProvidersTable"></table>
				<table id="ccdProvidersPager"></table>
		</div>
	</div>
	<div id="Encounters" class="tabcontent">
	  <div id="encountersGrid" class="jqGridTableSection" style="padding : 10px">
			<table id="ccdEncountersTable"></table>
			<table id="ccdEncountersPager"></table>
	  </div>
	</div>
	<div id="Problems" class="tabcontent">
	  <div id="problemsGrid" class="jqGridTableSection" style="padding : 10px">
			<table id="ccdProblemTable"></table>
			<table id="ccdProblemPager"></table>
	  </div>
	</div>
	<div id="LabTest" class="tabcontent">
		<div id="labtestGrid" class="jqGridTableSection" style="padding : 10px">
				<table id="ccdLabtestTable"></table>
				<table id="ccdLabtestPager"></table>
		</div>
	</div>
	<div id="Medications" class="tabcontent">
	  <div id="medicationsGrid" class="jqGridTableSection" style="padding : 10px">
			<table id="ccdMedicationTable"></table>
			<table id="ccdMedicationPager"></table>
	  </div>
	</div>
	<div id="Payers" class="tabcontent">
	  <div id="payersGrid" class="jqGridTableSection" style="padding : 10px">
			<table id="ccdPayersTable"></table>
			<table id="ccdPayersPager"></table>
	  </div>
	</div>
<script src="${staticPath}/js/utils/ccdDocument.js?v=${buildDate}"></script>


																