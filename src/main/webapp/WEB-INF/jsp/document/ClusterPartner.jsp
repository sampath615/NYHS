<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>

<!--  Person view data -->
<form:form method="post" modelAttribute="personViewObj"
	id='personViewObj${personViewNumber}'>

	<div id='personViewDiv<c:out value="${personViewNumber}" />'
		style="background-color: transparent;">
    <div>
        <div class="divHeadingCls inlineBlock"
			id='personDetailsHeadingDiv<c:out value="${personViewNumber}" />'>Person
			Details</div>
     
    </div>
		
			<div class ="floatRight">
			  <font class="personInputTitle" style="font-weight:bold">As of :</font>
				<form:input path="bIndicatedDate"
					id='bIndicatedDate${personViewNumber}' value="${bIndicatedDate}"
					class="dPicker standardInputMedText"
					onchange="reloadPersonView(this, '#stateNo${personViewNumber}', '${personViewNumber}');" />
			</div>
		

		<div id='pHdrDiv<c:out value="${personViewNumber}" />'
			class="personDetailsDiv" style="height:30px">
			<div class="leftDivHolder">
				<font class="personInputTitle" style="font-weight:bold"><spring:message code="label.stateNo" />:</font> <font
					class="stateno_person"
					id='stateNo<c:out value="${personViewNumber}" />'>${personViewObj.stateNo}</font>
					
				
				<c:if test="${statenoHistoryObj != null && statenoHistoryObj != '[]' }">
					<span class="historyCharacter">*</span>
					<div
						class='stateNoHistoryData stateNo<c:out value="${personViewNumber}" />'>
						<table
							class='statenoHistory historyTable<c:out value="${personViewNumber}" />'>
							<tr>
								<th class="historyTableHeader">Prev STATENO</th>
							</tr>
							<c:forEach var="item" items="${statenoHistoryObj}">
								<tr>
									<td>${item}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</c:if>
			</div>
			<div class="centerDivHolder">
					<div id="FlaggingSection${personViewNumber}">
						<div class="flW33">
							<c:if  test="${userPermissions['17'] != null && personViewObj.stateNo != ''}" >
								<!-- Make it as Bad merge or un mark as bad merge -->
				                 
				                   <font class="personInputTitle" style="font-weight:bold">IS THIS A BAD MERGE :</font>
				                   <form:radiobutton path="badMergeFlag" id="badlyMergedFlagYes${personViewNumber}" class="bMYes" title="Bad Merge" onchange="markingAsBadMerge(this, 'badlyMergedFlagNo${personViewNumber}', '#stateNo${personViewNumber}')" value="Y" />Yes 
				                   <form:radiobutton path="badMergeFlag" id="badlyMergedFlagNo${personViewNumber}" class="bMYes" title="Not a Bad Merge" onchange="unMarkingAsBadMerge(this, 'badlyMergedFlagYes${personViewNumber}', '#stateNo${personViewNumber}')" value="N"/>No
				                
				            </c:if>   
		     
			              	<c:if  test="${userPermissions['17'] == null}" >
			              		<!-- Showing the Bad merge status, but not giving user to change the status -->
			                    <font class="personInputTitle" style="font-weight:bold">IS THIS A BAD MERGE :</font>
			                    <form:radiobutton path="badMergeFlag" id="badlyMergedFlagYes${personViewNumber}" class="bMYes" title="Bad Merge"  value="Y" disabled="true" />Yes 
			                    <form:radiobutton path="badMergeFlag" id="badlyMergedFlagNo${personViewNumber}" class="bMYes" title="Not a Bad Merge"  value="N" disabled="true" />No
			            	</c:if>
		           			
           				</div>
	              		
	              		<div class="flW40">
	                    
	                    
		                    <c:if  test="${userPermissions['105'] != null && personViewObj.stateNo != ''}" >
									<!-- Make it as Bad merge or un mark as bad merge -->
				                 	<font class="personInputTitle" style="font-weight:bold">FLAGGED AS RECORD SEARCH :</font>
				                     <form:radiobutton path="recordSearchFlag" id="recordSearchFlagYes${personViewNumber}" class="bMYes" title="Flag Record Search" onchange="markingAsRecordSearch(this, 'recordSearchFlagNo${personViewNumber}', '#stateNo${personViewNumber}', 'recordSearchComments${personViewNumber}')" value="Y" />Yes 
		                    
				                    <c:if test="${personViewNumber=='_recordSearch'}">
				                    	<form:radiobutton path="recordSearchFlag" id="recordSearchFlagNo${personViewNumber}" class="bMYes" title="Unflag Record Search" onchange="markingAsRecordSearch(this, 'recordSearchFlagYes${personViewNumber}', '#stateNo${personViewNumber}', 'recordSearchComments${personViewNumber}')" value="N" />No
				                    </c:if>
				                
				            </c:if>  
				            
				             <c:if  test="${userPermissions['105'] == null && personViewObj.stateNo != ''}" >
								<!-- Make it as Bad merge or un mark as bad merge -->
				                 	<font class="personInputTitle" style="font-weight:bold">FLAGGED AS RECORD SEARCH :</font>
				                     <form:radiobutton path="recordSearchFlag" id="recordSearchFlagYes${personViewNumber}" class="bMYes" title="Flag Record Search"  value="Y"  disabled="true"/>Yes 
		                    
				                    <c:if test="${personViewNumber=='_recordSearch'}">
				                    	<form:radiobutton path="recordSearchFlag" id="recordSearchFlagNo${personViewNumber}" class="bMYes"  value="N"  disabled="true"/>No
				                    </c:if>
				                
				            </c:if>  
		                    
		                   <form:hidden path="sentToCDC" id="sentToCDC${personViewNumber}"/>
		                    
		                    <c:if  test="${personViewObj.stateNo != ''}" >
			                    <c:choose>
			                    	<c:when test="${personViewObj.statenoFlaggedEarlier == 'Y' }">
			                    		<span class="infoIcon" id="recordSearchComments${personViewNumber}" onclick="loadRecordSearchHistory('${personViewObj.stateNo}')"></span>	
			                    	</c:when>
			                    	<c:otherwise>
			                    		<span class="infoIcon" id="recordSearchComments${personViewNumber}" style="display:none" onclick="loadRecordSearchHistory('${personViewObj.stateNo}')"></span>
			                    	</c:otherwise>
			                    </c:choose>
		                    </c:if>	
		                    
		                   
						
	                    </div>
	                    <div class="fLW25">
	                    	<c:choose>
	                    		<c:when  test="${userPermissions['117'] != null && personViewObj.stateNo != ''}" >
	                    				<font class="personInputTitle" style="font-weight:bold">Non-Case :</font>
			                    		<form:radiobutton path="nonCaseDocStatus" id="nonCaseYes${personViewNumber}"  title="Non Case"  value="Y" onchange="markingAsNonCase(this, 'nonCaseNo${personViewNumber}', '#stateNo${personViewNumber}', 'nonCaseDocComments${personViewNumber}')" />Yes 
			                    		<form:radiobutton path="nonCaseDocStatus" id="nonCaseNo${personViewNumber}"  title="Case"  value="N" onchange="unmarkingAsNonCase(this, 'nonCaseYes${personViewNumber}', '#stateNo${personViewNumber}', 'nonCaseDocComments${personViewNumber}')"/>No
	                    		</c:when>	
	                    		<c:otherwise>
										<font class="personInputTitle" style="font-weight:bold">Non-Case :</font>
			                    		<form:radiobutton path="nonCaseDocStatus" id="nonCaseYes${personViewNumber}"  title="Non Case"  value="Y"  disabled="true"/>Yes 
			                    		<form:radiobutton path="nonCaseDocStatus" id="nonCaseNo${personViewNumber}"  title="Case"  value="N" disabled="true"/>No	                    		
	                    		
	                    		</c:otherwise>
	                    	</c:choose>
	                    	
	                    	<c:if  test="${personViewObj.stateNo != ''}" >
			                    <c:choose>
			                    	<c:when test="${personViewObj.nonCaseHistory == 'Y' }">
			                    		<span class="infoIcon" id="nonCaseDocComments${personViewNumber}" onclick="loadNonCaseDocistory('${personViewObj.stateNo}')"></span>	
			                    	</c:when>
			                    	<c:otherwise>
			                    		<span class="infoIcon" id="nonCaseDocComments${personViewNumber}" style="display:none" onclick="loadNonCaseDocistory('${personViewObj.stateNo}')"></span>
			                    	</c:otherwise>
			                    </c:choose>
		                    </c:if>	
           				</div>
           				
           				
		           			<script type="text/javascript">
		           			
		           				var isStatenoMarkedAsBadMerge = '${personViewObj.badMergeFlag}';
		           				var isStatenoMarkedAsNonCase = '${personViewObj.nonCaseDocStatus}';
		           			</script>
	                 
					</div>
             </div>

				<div class="rightDivHolder" id= "duplicateStateno${personViewNumber}">
							
				                <font class="personInputTitle" style="font-weight: bold">FLAGGED AS DUPLICATE:</font>
				              
								 <c:choose>
				                   <c:when test ="${personViewObj.DUPLICATE_STATENO_LIST != null && personViewObj.DUPLICATE_STATENO_LIST != '[]' }">
				                   <input type="radio" class="bMYes" title="Duplicate Record Search" value="Y"  checked/>
				                    </c:when>
				                   <c:otherwise>
				                    <input type="radio" class="bMYes" title="Duplicate Record Search" value="Y" disabled/>Yes
				                   </c:otherwise>
				               </c:choose>
								
								<c:if
									test="${personViewObj.DUPLICATE_STATENO_LIST != null && personViewObj.DUPLICATE_STATENO_LIST != '[]' }">
										
									 <font class="showMultiData"
										id='duplicateStateno<c:out value="${personViewNumber}" />'>Yes<span class="historyCharacter">*</span> </font>
										<div class='displayNone stateNoHistoryData duplicateStateno<c:out value="${personViewNumber}" />'>
										<table class='statenoHistory historyTable<c:out value="${personViewNumber}" />'>
												<tr>
													<th class="historyTableHeader">DUPLICATE STATENOS</th>
												</tr>
												<c:forEach var="item"
													items="${personViewObj.DUPLICATE_STATENO_LIST}">
													<tr>
														<td>${item}</td>
													</tr>
												</c:forEach>
											</table>
											</div>
										
									</c:if>
				           </div>

			
		</div>
		
		<div id='' class="personDetailsDiv" style="margin-top:0; border-top: none; height:30px">
			<div class="flW50 mT5">
				<div class="fieldHolder35">
					<font class="personInputTitle" style="font-weight:bold">CITYNO:</font> 
					<font class="showMultiData" id='eHARSNycCityno<c:out value="${personViewNumber}" />'>${personViewObj.NYC_CITYNO}</font>
					
					<c:if test="${personViewObj.NYC_CITYNO_LIST != null && personViewObj.NYC_CITYNO_LIST != '[]' }">
						<span class="historyCharacter">*</span>
						<div
							class='displayNone stateNoHistoryData eHARSNycCityno<c:out value="${personViewNumber}" />'>
							<table
								class='statenoHistory historyTable<c:out value="${personViewNumber}" />'>
								<tr>
									<th class="historyTableHeader">CITYNO</th>
								</tr>
								<c:forEach var="item" items="${personViewObj.NYC_CITYNO_LIST}">
									<tr>
										<td>${item}</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</c:if>
						
					<form:hidden path="NYC_CITYNO" id="personView_cityNo${personViewNumber}"/>			
					<form:hidden path="EHARS_STATENO" id="personView_eharsNysStateno${personViewNumber}"/>
					<form:hidden path="NYC_EHARS_STATENO" id="personView_eharsNycStateno${personViewNumber}"/>
				</div>
				<div class="fieldHolder65">
						<font class="personInputTitle" style="font-weight:bold">eHARS NYC STATENO :</font>
						<font class="showMultiData" id='eHARSNycStateno<c:out value="${personViewNumber}" />'>${personViewObj.NYC_EHARS_STATENO}</font>	
						
						<c:if test="${personViewObj.NYC_EHARS_STATENO_LIST != null && personViewObj.NYC_EHARS_STATENO_LIST != '[]' }">
							<span class="historyCharacter">*</span>
							<div
								class='displayNone stateNoHistoryData eHARSNycStateno<c:out value="${personViewNumber}" />'>
								<table
									class='statenoHistory historyTable<c:out value="${personViewNumber}" />'>
									<tr>
										<th class="historyTableHeader">eHARS NYC STATENO</th>
									</tr>
									<c:forEach var="item" items="${personViewObj.NYC_EHARS_STATENO_LIST}">
										<tr>
											<td>${item}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:if>
				</div>
			</div>
			<div class="flW50 mT5">
				<div class="fieldHolder65">
					<font class="personInputTitle" style="font-weight:bold">eHARS NYS STATENO :</font>
		              <font class="showMultiData" id='eHARSNysStateno<c:out value="${personViewNumber}" />'>${personViewObj.EHARS_STATENO}</font>
		              
						<c:if test="${personViewObj.EHARS_STATENO_LIST != null && personViewObj.EHARS_STATENO_LIST != '[]' }">
							<span class="historyCharacter">*</span>
							<div
								class='displayNone stateNoHistoryData eHARSNysStateno<c:out value="${personViewNumber}" />'>
								<table
									class='statenoHistory historyTable<c:out value="${personViewNumber}" />'>
									<tr>
										<th class="historyTableHeader">eHARS NYS STATENO</th>
									</tr>
									<c:forEach var="item" items="${personViewObj.EHARS_STATENO_LIST}">
										<tr>
											<td>${item}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:if>
				</div>
				<div class="fieldHolder35">
					<font class="personInputTitle" style="font-weight:bold">RAS Results :</font>
				<font id="stateNo_haveRas">${personViewObj.rasDocActive}</font>	
				</div>
			</div>

		</div>
		
		<div id='' class="personDetailsDiv" style="margin-top:0; border-top: none; height:auto">
			<div class="flW50 mT5 marginBottom5">
				<div class="">
					<font class="personInputTitle" style="font-weight:bold">Stage 0 at dx:</font> 
					<font>${personViewObj.NYC_STAGE_ZERO_DX}</font>
				</div>
				<div class="clearBoth mT5">
					<font class="personInputTitle" style="font-weight:bold">Stage at dx based on CD4/OI :</font>
					<font >${personViewObj.NYC_STAGE_CD4_OI_DX}</font>	
				</div>
			</div>
			<div class="flW50 mT5 marginBottom5">
				<div class="">
					<font class="personInputTitle" style="font-weight:bold">Stage 0 at dx:</font>
	              	<font>${personViewObj.NYS_STAGE_ZERO_DX}</font><br/>
				</div>
				<div class="clearBoth mT5">
					<font class="personInputTitle" style="font-weight:bold">Stage at dx based on CD4/OI :</font>
					<font id="stateNo_haveRas">${personViewObj.NYS_STAGE_CD4_OI_DX}</font>	
				</div>
			</div>

           
		</div>

		<div id='personClusterAssociation' class="personDetailsDiv" style="margin-top:0; border-top: none;">
			<div class="persondata">
			
				<div class="fieldHolder100">
					<div class="standardInputDiv" style="height:auto">
							<font class="personInputTitle" style="font-weight:bold;">CLUSTER ID(s): </font><font id="stateNo_haveMolCluster"><c:out value="${personViewObj.clusterIds}" /> </font>
							
							<div id="addToClusterButton" class="displayNone">
								<img src="${staticPath}/images/icons/add.png" class="addPNG mL5 fnone"
									title="Add this STATENO to a Cluster" onclick="getNotAssocClusterIds('${personViewObj.stateNo}', '${personViewNumber}')">
							</div>			
													
					</div>
				</div>			
			</div>
		</div>
		
		<div class="personDetailsDiv"
			id='personNameInfoDiv<c:out value="${personViewNumber}" />'>
			<div class="persondata">
				<div class="placeHolder fieldHolder51">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.firstname" /></font>
						</div>
						<div class="personinput">
							<form:input path="FNAME" id="personView_firstName${personViewNumber}"
								value="${FNAME}" class="standardInputText formInputText left floatLeft width336"
								 readonly="true" />
								 
						
							
						</div>
					</div>
				</div>
				<div class="placeHolder fieldHolder51">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.middleName" /></font>
						</div>
						<div class="personinput">
							<form:input path="MNAME" id="personView_middleName${personViewNumber}"
								value="${MNAME}" class="standardInputText formInputText left floatLeft width336"
								readonly="true" />
								
						
							
						</div>
					</div>
				</div>
				<div class="placeHolder fieldHolder51">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.lastname" /></font>
						</div>
						<div class="personinput">
							
							<form:input path="LNAME" id="personView_lastName${personViewNumber}"
								value="${LNAME}" class="standardInputText formInputText left floatLeft width336"
								readonly="true" />
								
							
						</div>
					</div>
				</div>

				<div class="holder fieldHolder51">
					<div class="standardInputDiv" style="display: none;">
						<div class="fLW35">
							<font class="personInputTitle">Soundex</font>
						</div>
						<div class="personinput">
							<input type="text" name="soundex"
								id='soundex<c:out value="${personViewNumber}" />'
								value="${soundex}" class="standardInputText formInputText"
								readonly="true" />
						</div>
					</div>
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.suffix" /></font>
						</div>
						<div class="personinput">
						
							<form:input path="SUFFIX" id="personView_suffix${personViewNumber}"
								value="${SUFFIX}" class="standardInputText formSmallInputText left floatLeft width336"
								readonly="true" />
								
						
							
						</div>
					</div>
				</div>
			</div>

			<div class="persondata">
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35" >
							<font class="personInputTitle"><spring:message code="label.birthdate" /></font>
						</div>
						<div class="personinput">
							<form:input path="DOB" id="personView_birthDate${personViewNumber}"
								value="${DOB}" class="standardInputText formInputText floatLeft"
								readonly="true" />
								
						
				
						</div>
					</div>
				</div>
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.birthcountry" /></font>
						</div>
						<div class="personinput">
							<form:input path="BIRTH_COUNTRY"
								id="personView_birthCountry${personViewNumber}" value="${BIRTH_COUNTRY}"
								class="standardInputText formInputText floatLeft"
								readonly="true" />
								
						
							
						</div>
					</div>
				</div>
			</div>

			<div class="persondata">
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35" >
							<font class="personInputTitle">Current Gender</font>
						</div>
						<div class="personinput">
							
							<form:input path="GENDER"
								id="personView_currentGender${personViewNumber}" value="${GENDER}"
								class="standardInputText formInputText floatLeft"
								readonly="true" />
								
					
							
						</div>
					</div>
				</div>
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle">Birth
								Sex&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
						</div>
						<div class="personinput">
							<form:input path="birthSex" id="personView_birthSex${personViewNumber}"
								value="${birthSex}" class="standardInputText formInputText floatLeft"
								readonly="true" />
								
						
		
						</div>
					</div>
				</div>
			</div>

			<div class="persondata">
				
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.deathdate" /></font>
						</div>
						<div class="personinput">
							<form:input path="dateOfDeath"
								id="personView_dateOfDeath${personViewNumber}" value="${dateOfDeath}"
								class="standardInputText formInputText floatLeft"
								readonly="true" />
						
						
						</div>
					</div>
				</div>
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.stateofdeath" /></font>
						</div>
						<div class="personinput">
							<form:input path="stateOfDeath"
								id="personView_stateOfDeath${personViewNumber}" value="${stateOfDeath}"
								class="standardInputText formInputText floatLeft" readonly="true" />
						
							
						</div>
					</div>
				</div>

			</div>

			<div class="persondata">
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle">Medical Record</font>
						</div>
						<div class="personinput">
							<form:input path="MED_RECORD_NUMBER" id="personView_medRecord${personViewNumber}"
								value="${MED_RECORD_NUMBER}" class="standardInputText formInputText floatLeft"
								readonly="true" />
						
							
						</div>
					</div>
				</div>
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle">Insurance</font>
						</div>
						<div class="personinput">
							<form:input path="INSURANCE_NUMBER" id="personView_insurance${personViewNumber}"
								value="${INSURANCE_NUMBER}" class="standardInputText formInputText floatLeft"
								readonly="true" />
							

						</div>
					</div>
				</div>
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle">CIN</font>
						</div>
						<div class="personinput">
							
							<form:input path="MEDICAID_NUMBER" id="personView_medicaidNumber${personViewNumber}"
								 class="standardInputText formInputText floatLeft"
								readonly="true" />
						
						</div>
					</div>
				</div>
			</div>

			<div class="persondata">
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"> <spring:message code="label.ssn" /></font>
						</div>
						<div class="personinput">
							<form:input path="SSN" id="personView_ssn${personViewNumber}" value="${SSN}"
								class="standardInputText formInputText floatLeft"
								readonly="true" />
							
						
						</div>
					</div>
				</div>
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.nysid" /></font>
						</div>
						<div class="personinput">
							<form:input path="NYSID" id="personView_nysid${personViewNumber}"
								value="${NYSID}" class="standardInputText formInputText floatLeft"
								readonly="true" />
							

						</div>
					</div>
				</div>
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.din" /></font>
						</div>
						<div class="personinput">
							<form:input path="DIN" id="personView_din${personViewNumber}" value="${DIN}"
								class="standardInputText formInputText floatLeft" readonly="true" />
						
						</div>
					</div>
				</div>
			</div>

			<div class="persondata" style="padding-bottom: 55px">
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.ethnicity" />&nbsp;&nbsp;&nbsp;&nbsp;</font>
						</div>
						<div class="personinput">
							<form:input path="ETHNICITY" id="personView_ethnicity${personViewNumber}"
								value="${ETHNICITY}" class="standardInputText formInputText floatLeft"
								readonly="true" />
							
						</div>
					</div>
				</div>
				<div class="placeHolder" style="width: 70%">
					<div class="standardInputDiv">
						<div class="personLabel ">
							<font class="personInputTitle"><spring:message code="label.race" />&nbsp;&nbsp;&nbsp;&nbsp;</font>
						</div>
						<!-- <div style="float:right">
					<input type="text" class="standardInputText" readonly="readonly"/>
				</div> -->
						<div class="personinput">
							<%-- <form:input  path="race" value="${race}" class="standardInputText" readonly="true"/> --%>
							<table style="margin-right: 55px; margin-left: 87px">
								<tr>
									<td><form:checkbox path="personRaceVO.RACE_WHITE"
											id="raceWhite${personViewNumber}" value="1" label="White"
											cssClass="checkboxMid" onclick="return false" /></td>
									<td width="40%"><form:checkbox
											path="personRaceVO.RACE_BLACK"
											id="raceBlack${personViewNumber}" value="1"
											label="Black or African American" cssClass="checkboxMid" onclick="return false" /></td>
									<td><form:checkbox path="personRaceVO.RACE_ASIAN"
											id="raceAsian${personViewNumber}" value="1" label="Asian"
											cssClass="checkboxMid" onclick="return false" /></td>
								</tr>
								<tr>
									<td><form:checkbox path="personRaceVO.RACE_PACIFIC"
											id="racePacific${personViewNumber}" value="1"
											label="Native Hawaiian or Other Pacific Islander"
											cssClass="checkboxMid" onclick="return false" /></td>
									<td width="40%"><form:checkbox
											path="personRaceVO.RACE_INDIAN"
											id="raceIndian${personViewNumber}" value="1"
											label="American Indian/Alaska Native" cssClass="checkboxMid" onclick="return false" /></td>
									<td><form:checkbox path="personRaceVO.RACE_UNKNOWN"
											id="raceUnknown${personViewNumber}" value="1"
											label="Unknown" cssClass="checkboxMid" onclick="return false" /></td>
								</tr>
								<tr>
									<td><form:checkbox path="personRaceVO.RACE_OTHER"
											id="raceOther${personViewNumber}" value="1" label="Other"
											cssClass="checkboxMid" onclick="return false"/> <form:textarea
											path="personRaceVO.RACE_OTHER_TEXT"
											id="raceOtherText${personViewNumber}"
											cssStyle="vertical-align: top; height: 20px;"
											cssClass="standardInputMedText" readonly="true"/></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="divHeadingCls">Person Address</div>

		<div class="personDetailsDiv"
			id='personAddressInfodiv<c:out value="${personViewNumber}" />'>
			<div class="persondata">
				<div class="placeHolder" style="width: 56%">
					<div class="standardInputDiv">
						<div class="fLW18">
							<font class="personInputTitle"><spring:message code="label.address" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
						</div>
						<div class="personinput">
							<form:input path="PERSON_ADDRESS" value="${PERSON_ADDRESS}"
								id="personView_address${personViewNumber}"
								class="standardInputText formInputTextAddress floatLeft"
								readonly="true" />
							
						</div>
					</div>
				</div>

				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.county" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
						</div>
						<div class="personinput">
							<form:input path="PERSON_COUNTY" id="personView_county${personViewNumber}"
								value="${PERSON_COUNTY}" class="standardInputText formInputText"
								readonly="true" />
						</div>
					</div>
				</div>
			</div>

			<div class="persondata">
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.city" /></font>
						</div>
						<div class="personinput">
							<form:input path="PERSON_CITY" id="personView_city${personViewNumber}"
								value="${PERSON_CITY}" class="standardInputText formInputText"
								readonly="true" />
						</div>
					</div>
				</div>
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.state" /></font>
						</div>
						<div class="personinput">
							<form:input path="PERSON_STATE" id="personView_state${personViewNumber}"
								value="${PERSON_STATE}" class="standardInputText formInputText"
								readonly="true" />
						</div>
					</div>
				</div>
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.country" /></font>
						</div>
						<div class="personinput">
							<form:input path="country" id="country${personViewNumber}"
								value="${country}" class="standardInputText formInputText"
								readonly="true" />
						</div>
					</div>
				</div>
				<div class="holder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.zip" /></font>
						</div>
						<div class="personinput">
							<!--  combining Zip and plus4 and displaying together -->
							<c:set var="completeZip" value='${personViewObj.PERSON_ZIP} ${personViewObj.PERSON_PLUS4}' />
							<form:input path="PERSON_ZIP" id="personView_zip${personViewNumber}" value='${completeZip}'
								class="standardInputText formSmallInputText" readonly="true" />
						</div>
					</div>
				</div>
			</div>

			<div class="persondata">
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.cellPhone" /></font>
						</div>
						<div class="personinput">
							<form:input path="cellPhone" id="cellPhone${personViewNumber}"
								value="${cellPhone}" class="standardInputText formInputText"
								readonly="true" />
						</div>
					</div>
				</div>
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.workPhone" /></font>
						</div>
						<div class="personinput">
							<form:input path="workPhone" id="workPhone${personViewNumber}"
								value="${workPhone}" class="standardInputText formInputText"
								readonly="true" />
						</div>
					</div>
				</div>
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.emergencyPhone" /></font>
						</div>
						<div class="personinput">
							<form:input path="emerPhone" id="emerPhone${personViewNumber}"
								value="${emerPhone}" class="standardInputText formInputText"
								readonly="true" />
						</div>
					</div>
				</div>
			</div>

			<div class="persondata">
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.pager" /></font>
						</div>
						<div class="personinput">
							<form:input path="pager" id="pager${personViewNumber}"
								value="${pager}" class="standardInputText formInputText"
								readonly="true" />
						</div>
					</div>
				</div>
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle"><spring:message code="label.email" /></font>
						</div>
						<div class="personinput">
							<form:input path="email" id="email${personViewNumber}"
								value="${email}" class="standardInputText formInputText"
								readonly="true" />
						</div>
					</div>
				</div>
				<div class="placeHolder">
					<div class="standardInputDiv">
						<div class="fLW35">
							<font class="personInputTitle">Web</font>
						</div>
						<div class="personinput">
							<form:input path="web" id="web${personViewNumber}" value="${web}"
								class="standardInputText formInputText" readonly="true" />
						</div>
					</div>
				</div>
			</div>


		</div>
	</div>


</form:form>