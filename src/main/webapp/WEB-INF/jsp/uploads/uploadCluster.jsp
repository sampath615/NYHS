<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload CDC Cluster IDs</title>
<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<%@ include file="../common/MainMenuIncludes.jsp"%>

<link href="${staticPath}/css/docs/clusterForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/common.css" rel="stylesheet"
	type="text/css" />


</head>

<body class="clusterFileUpload">
	<%@ include file="../common/Menu.jsp"%>
	<div id="fileUploadsPage" class="pageView">
		<ul id="fileUploadTab">
			<li><a href="#uploadUpdatedDispositionsContent"
				id="uploadUpdatedDispositionsTab">Upload Cluster File</a></li>

		</ul>
		<div id="uploadUpdatedDispositionsContent" style="padding: 3em;" >

			<c:choose>
				<c:when test="${TRANSACTION_RESULT == 'TRANSACTION_SUCCESS'}">
					<div id="fileUploadTransactionMessage" class="transactionMessage successTransactionMessage">
						<span class="message"><c:out value="${TRANSACTION_MESSAGE}" /></span>
					</div>
				</c:when>
				<c:when test="${TRANSACTION_RESULT == 'TRANSACTION_FAILURE'}">
					<div id="fileUploadTransactionMessage" class="transactionMessage errorTransactionMessage">
						<span class="message"><c:out value="${TRANSACTION_MESSAGE}" /></span>
					</div>
				</c:when>
			</c:choose>

			<div id="assignmentsReviewSearchCriteria" class="pT20 pB20" style="height:360px" >
			
				<form:form modelAttribute="clusterUploadVO" id ="uploadClusterData" action="uploadCluster" method="post" enctype="multipart/form-data">
				
                         <div class="fieldHolder20" id="uploadTypeSelection">   
                                <span class="pL10">Upload Type</span>
                                <span class="mL10"><form:radiobutton id="uploadType1" path="uploadType" requiredgroup="requiredgroup" value="N" title="Upload Type" ></form:radiobutton>New</span>
                                <span class="mL10"><form:radiobutton id="uploadType2" path="uploadType" requiredgroup="requiredgroup" value="E" title="Upload Type" ></form:radiobutton>Existing</span>
                         </div><br><br>
						
                         <div class="fieldHolder15" id="clusterTypeGroup"  style="display:none; width: 12%;">   
                                <br><span class="pL10">Cluster Type</span><br>
                                
						        <span class="mL10"><form:radiobutton id="clusterType1" path="clusterType" requiredgroup="requiredgroup" value="CDC_M" title="Cluster Type" ></form:radiobutton>CDC Molecular</span>
						        	 <a id="clusterHelpIcon" class="tooltip" title="CDC Molecular Cluster Definition" onClick="$('#CDCMolecularDef').dialog('open');"></a><br>
						       	<span class="mL10"><form:radiobutton id="clusterType2" path="clusterType" requiredgroup="requiredgroup" value="BHAE_M" title="Cluster Type" ></form:radiobutton>BHAE Molecular</span>
									 <a id="clusterHelpIcon" class="tooltip" title="BHAE Molecular Cluster Definition" onClick="$('#BHAEMolecularDef').dialog('open');"></a><br>
								<div id="timeSpaceTemp1">
						        <span class="mL10"><form:radiobutton  id="clusterType3" path="clusterType" requiredgroup="requiredgroup" value="BHAE_TS" title="Cluster Type" ></form:radiobutton>BHAE Space - Time</span> 
									<a id="clusterHelpIcon" class="tooltip" title="BHAE Space - Time Cluster Definition" onClick="$('#BHAETimeSpaceDef').dialog('open');"></a><br>
								</div>									
								
						        <div class="v_error clusterTypeGroup"></div>
                         </div>

						<div class="fieldHolder15"  id="newClusterIdDiv" style="display:none">							
	                   		<br><span class="pL10">Cluster ID</span><br>
	                   		<span class="pL10"><form:input id="clusterId" path= "clusterId" class="standardInputMedText required" title="Cluster ID" maxLength="35"/></span>
	                  		<div class="pL10 v_error clusterId"></div>	
	                  	</div>
	                  	
						<div class="fieldHolder15" id="existingClusterIdDiv" style="display:none">
							<br><span>Cluster ID</span> 					
							<span class="pL10">
								<form:select path="existingClusterId" name="existingClusterId" id="existingClusterId" class="standardInputMedText" title="Cluster ID" onchange="getClusterData(this.value)">
									<form:option value="">Select</form:option>
									<c:if test="${clusterUploadVO.clusterType != null && clusterUploadVO.clusterType != ''}">
										<c:forEach items="${clusterIdsObj}" var="clusterIdElement">
											<form:option value="${clusterIdElement.CLUSTER_ID}">${clusterIdElement.CLUSTER_ID}</form:option>
										</c:forEach>
									</c:if>
								</form:select>
							</span>
                			<div class="pL10 v_error existingClusterId"></div>														
						</div>											
	                  	
						<div class="fieldHolder20"  id="descriptionDiv" style="display:none; width: 16%;">							
	                   		<br><span class="valignTop">Description</span><br>
	                   		<span><form:textarea id="description" path= "description" title="Description" maxLength="400" cols="20" rows="3" style="width:90%; height:100px; border:1px solid gray;" class="roundedBorder required"/></span>
	                  		<div class="v_error description"></div>	
	                  		
	                  	</div>	                  	

						<div class="fieldHolder15"  id="relatednessDiv" style="display:none">							
	                   		<br><span>Relatedness</span><br>
	                   		<form:select id="relatedness" path= "relatedness" class="standardInputSmallText3 required readonly" title="Relatedness" maxLength="6">
	                   				<form:option value="">Select</form:option>
									<c:forEach items="${relatednessData}" var="relatedness">
										<form:option value="${relatedness}">${relatedness}</form:option>
									</c:forEach>	                   				
	                   		</form:select>
	                  		<div class="v_error relatedness"></div>
	                  			                  		
	                   		<br><span>Time-Frame</span><br>
	                   		<form:select id="molTimeframe" path= "moleTimeFrame" class="standardInputSmallText3 required readonly" title="Time-Frame" maxLength="20">
	                   				<form:option value="">Select</form:option>
									<form:options items="${moleTimeFrameData}"></form:options>         				
	                   		</form:select>
	                  		<div class="v_error molTimeframe"></div>	 
	                  		
							<br><span>Cluster Date</span><br>
	                   		<span><form:input id="clusterIdentifyDate" path= "clusterIdentifyDate" datevalidations="partialDateMMYYYY" class="standardInputSmallText3 mask required readonly" title="Cluster Date" maxLength="10"/></span>
 	                   		<div><font>(mm/yyyy or mm/dd/yyyy)</font></div>
	                  		<div class="v_error clusterIdentifyDate"></div>
	                  	</div>

						<div class="fieldHolder10" id="clusterCountiesDiv" style="display:none; width: 8%;" >				
							<br><span class="pL10 valignTop">Cluster Counties</span><br>
							<span class="pL10">
	 	                       <form:select path="clusterCountiesList"  cssClass="oAdrDlg roundedBrdr newCounty readonly" id="clusterCounties" multiple="true" size="6" title="Cluster Counties" style="width:100px;"> 
									<form:option value=""></form:option>
									<form:options items="${sessionScope.countyMap}"/>
								</form:select>
							</span>
	                   		<div class="pL10 v_error clusterCounties"></div>
						</div>
						<div class="fieldHolder15" id="spaceTimeDateDiv" style="display:none; width: 12%;" >	                   		
	                   		<br><span class="pL10">Dx Start Date</span>
	                   		<span class="pL10"><form:input id="spaceTimeStartDate" path= "spaceTimeDxStartDate" readonly="true" class="standardInputSmallText required date-picker" title="Start Date" maxLength="7"/></span>
	                  		<div class="pL10 v_error spaceTimeStartDate"></div>	
	                  		
	                  		<br><span class="pL10">Dx End Date</span>
	                   		<span class="pL10"><form:input id="spaceTimeEndDate" path= "spaceTimeDxEndDate" readonly="true" class="standardInputSmallText date-picker" title="End Date" maxLength="7"/></span>
							<div class="pL10 v_error spaceTimeEndDate"></div>		                   		
	                   		
	                   		<br><span class="pL10"><input type="button" value="Clear Dates" class="btn btn-primary inlineEditButton" id="clear-dates"></span>
						</div>
						
						<div class="fieldHolder25"  id="tsClusterdDemography" style="display:none">							
	                   		<br><span class="pL10">Transmission Risk</span><br>
	                   		<span class="pL10">
	                   			<form:select id="transmissionRisk" path= "transmissionRiskList" cssClass="oAdrDlg roundedBrdr" multiple="true" size="4" title="Transmission Risk">
	                   				<form:option value=""></form:option>
									<form:options items="${transCategData}"></form:options>                    				
	                   			</form:select>
	                   		</span>
	                   		<div class="pL10"></div>	
	                  			                  		
	                   		<br><span class="pL10">Birth Sex</span>
	                   		<span class="pL10"><form:select id="birthSex" path= "birthSex" class="standardInputSmallText3" title="Birth Sex">
	                   			<form:option value=""></form:option>
								<c:forEach var="sexAtBirthMap" items="${codedSexAtBirthValues}" >
									<form:option value="${sexAtBirthMap.SEX_CODE}" label="${sexAtBirthMap.SEX_DESCRIPTION}" />
								</c:forEach>								
	                   		</form:select></span>
	                   		<div class="pL10"></div>
	                  		
	                   		<br><span class="pL10" style="vertical-align: top">Race</span>
	                   		<span class="pL10">
	                   			<form:select id="race" path= "raceList" cssClass="oAdrDlg roundedBrdr" multiple="true" size="4" title="race">
		                   			<form:option value=""></form:option>
		                   			<form:options items="${eharsRaceValues}"></form:options>            
	                   			</form:select>
	                   		</span>
	                   		<div class="pL10"></div>
	                   		
	                   		<br><span class="pL10">Ethinicity</span>
	                   		<span class="pL10"><form:select id="ethnicity" path= "ethnicity" class="standardInputMedText" title="ethnicity">
	                   			<form:option value=""></form:option>
	                   			<form:options items="${eharsEthnicityValues}"></form:options>            
	                   		</form:select></span>
	                   		<div class="pL10"></div>
	                   		
	                   		<br><span class="pL10">Age Range: From </span>
	                   		<span class="pL10"><form:input id="ageRangeFrom" path= "ageRangeFrom" class="standardInputVerySmall" title="Age Range: From" maxLength="3"/></span>
	                  		<div class="pL10 v_error ageRangeFrom"></div>
	                  		
	                   		<br><span class="pL10">Age Range: To </span>
	                   		<span class="pL10"><form:input id="ageRangeTo" path= "ageRangeTo" class="standardInputVerySmall" title="Age Range: To" maxLength="3"/></span>
	                  		<div class="pL10 v_error ageRangeTo"></div>	
	                  		
	                  	</div>						
						
						<div class="fieldHolder75" id="clusterFileSubmitDiv" style="display:none">		                  				
							<br><span class="pL10 bold"> Upload File :</span>
							<span class="mL10"> <input type="file"	id="uploadClusterFile" name="fileData" title="File"  value="${param.fileData}"></span>
							<span> <input type="submit" class="inlineEditButton" value="Upload File" onclick="return validateClusterFile();"/></span>
							<span>&nbsp;&nbsp;&nbsp;&nbsp;<a class="pdfDownload bold" href="${staticPath}/docs/ClusterTemplate.xlsx">Template</a></span>
							<div class="v_error uploadClusterFile"></div>
							
							<div class="pL10"><font>***The filename must be less than 255 characters, only contain alphanumeric characters (Aa-Zz and 0-9) and a maximum of one period(.)</font></div>	
						</div>	
						
						<input type="hidden" name="tsUpdateRetire" id="tsUpdateRetire">

						<div class="fieldHolder10" id="stSubmitCriteriaDiv" style="display:none; " >
	                   		<br><span class="pL10"><input type="submit" value="Submit Criteria" class="btn btn-primary inlineEditButton" id="submit_st_criteria" onclick="return validateClusterFile();"></span>
						</div>
						<div class="fieldHolder10" id="stEndClusterDiv" style="display:none; " >
							<form:input id="spaceTimeClusterEndDate" path= "spaceTimeClusterEndDate" type="hidden"/>
	                   		<br><span class="pL10"><input type="submit" value="Update Cluster" class="btn btn-primary inlineEditButton" id="update_ts_cluster" onclick="return validateClusterFile('U');"></span>
	                   		<div class="pL10 v_error update_ts_cluster"></div>	  
	                   		<br><br><span class="pL10"><input type="submit" value="Retire Cluster" class="btn btn-primary inlineEditButton" id="retire_ts_cluster" onclick="return validateClusterFile('R');"></span>
	                   		<c:if  test="${userPermissions['250'] != null}" >
	                   			<div class="clearBoth mT10 pT10 displayNone" id="associate_stateno_cluster">
	                   				<span class="pL10 "><input type="button" value="Associate Stateno" class="btn btn-primary  inlineEditButton"  onclick="validateAssoStateno('AS');"></span>
	                   			</div>
	                   		</c:if>
	                   		<br><br><span class="pL10"><input type="submit" value="Activate Cluster" class="btn btn-primary inlineEditButton" id="activate_ts_cluster" onclick="return validateClusterFile('A');"></span>
	                   		
                 		
						</div>
						
												
				</form:form>

			</div>		
			
			<c:if test="${resultMap != null}">

				<div id="clusterDataError" class="width97Perc">
				
						<div>
							<span class="floatLeft bold hdr1Font">Error Messages</span>
						</div>

						<table class=" bordered" border="1" cellspacing="0" id="uploadNYCSurvRejectDataErrors">
						
							<tr><th class="width15Perc">STATENO</th><th>Error Message</th></tr>
							
							<c:forEach items="${resultMap}" var="rowError">
								<tr>
									<td class="width15Perc">${rowError.key}</td>
									
									<td>${rowError.value}</td>
								</tr>
							
							</c:forEach>
						
						</table>
						
				</div>
			
			</c:if>
			
		</div>

	</div>
   
   <div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
   </div>
   
   
   <div id="associateStatenoDialog" >
		<h4 id="associateStatenoContent"></h4>
		  <div id="clusterStatenoTransactionMessage" class="transactionMessage successTransactionMessage">
				<span class="message"><c:out value="${TRANSACTION_MESSAGE}" /></span>
			</div>
		  <div class="mT10 pB30">
	   			<div class="floatLeft">
	   				<span class="boldHeaders" >Stateno</span>
	   			</div>
	   			<div class="floatLeft mL50">
	          		<input id="clusterAssStateno" class="required standardInputMedText" title="stateno" />
	          		<div class="v_error clusterAssStateno "></div>
	          	</div>
		  </div>
		
		<div id="requestCommentsText" class="clearBoth mT20">
	   			<div class="floatLeft">
	   				<span class="boldHeaders valignTop" ><spring:message code="label.comments" /></span>
	   			</div>
	   			<div class="floatLeft mL35">
          			<textarea id="clstrStatenoRequestComments" class="required" cols="40" class="" rows="4" style="border: thin solid #003366;" title="<spring:message code='label.requestComments' />"></textarea>
          			<div class="v_error clstrStatenoRequestComments "></div>
          		</div>
		</div>  
		  
		
   </div>
	
	<%@ include file="../common/commonScriptsIncludes.html"%>
	<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/uploads/fileUpload.js?v=${buildDate}"></script>
	<script src="${staticPath}/js/person/commonPersonviewUtils.js?v=${buildDate}"></script>	
	<script src="${staticPath}/js/common/html2canvas.js"></script>
	<script src="${staticPath}/js/common/jspdf.min.js"></script>
	<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>	
</body>

		<div id="CDCMolecularDef">
				CDC defined molecular clusters sent to NYS. A molecular cluster is defined as a group of individuals whose HIV sequences are closely-related based on standard sequence similarity measures.	
		</div>
		<div id="BHAEMolecularDef">
				Molecular clusters are identified using the Secure HIV Trace web site to process genotype results of antiretroviral drug resistance tests submitted to NYS. A molecular cluster is defined as a group of individuals whose HIV sequences are closely-related based on standard sequence similarity measures.	
		</div>
		<div id="BHAETimeSpaceDef">
				Space-Time clusters are identified using eHARS location and date of diagnosis data. A space-time cluster is defined as a geographic region where the quantity of new HIV diagnosis greatly exceeds the average of new HIV diagnoses over the last five years.	
		</div>
		