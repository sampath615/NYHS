<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

            <div id="submitCLEPResultTransactionMessage" class="transactionMessage"><span class="message"></span></div>
            
			<form:form modelAttribute="clepLabResultVO" id ="submitClepLabResultData" action="submitLabResult" method="post">
			
			<div>
				<hr>
				
					<div style="float:left; width:99%; margin: 10px 10px 10px 10px;">
						<div style="float:left; width:18%">
							<label class="standardInputTitle">PFI</label>
							<form:input id="userEnteredPfi" path="userEnteredPfi"  class="mL35 formSmallInputText required" type="text" maxLength="6" title="PFI"/>
							<input id="searchPfi" name="searchPfi"  class="mL25 btn btn-primary" value="Search PFI" type="button" onclick="getPFILabData();" >
							<div class="v_error userEnteredPfi mL20"></div>
						</div>
						<div style="float:left; width:23%">
							<label class="standardInputTitle">Date of Survey (earliest date)</label>
							<form:input id="labDateOfSurvey" path="labDateOfSurvey"  readonly="true" class="mL25 date-picker" title="Date of Survey -Enter earliest survey date"/>
						</div>
						<div style="float:left; width:20%">
							<label class="standardInputTitle">Surveyor</label>
							<form:input id="labSurveyorName" path="labSurveyorName"  class="mL25 standardInputSmallText" type="text" maxLength="50"  title="Surveyor"/>
						</div>
						<div style="float:left; width:20%">
							<label class="standardInputTitle">Fax Recieved Date</label>
							<form:input id="faxReceivedDate" path="faxReceivedDate"  readonly="true" class="mL25 date-picker" title="Fax Received Date"/>
						</div>
						<div id="surveyNumberDiv" style="float:left; width:15%" class="displayNone">
							<label class="standardInputTitle">Survey No</label>
							<form:input id="surveyNumber" path="surveyNumber"  class="mL35 formSmallInputText readonly" type="text" maxLength="8" title="Survey No"/>
						</div>						
					</div>

				<div>
					<div style="float:left; width:99%; margin: 10px 10px 10px 10px;">
						<div style="float:left; width:20%">
							<label class="standardInputTitle"><spring:message code="label.CLIA" /></label>
							<form:input id="pfiLabClia" path="pfiLabClia"  class="mL25 standardInputSmallText readonly" type="text" maxLength="50"/>
						</div>
						<div style="float:left; width:33%">
							<label class="standardInputTitle"><spring:message code="label.labName" /></label>
							<form:input id="pfiLabName" path="pfiLabName" style="margin-left:25px;" class="standardInputlabText1 readonly" type="text" maxLength="100"/>
						</div>
						<div style="float:left; width:17%">
							<label class="standardInputTitle">LAFT Staff Assigned</label>
							<form:input id="pfiLabLaftStaff" path="pfiLabLaftStaff"  class="mL25 standardInputSmallText1 readonly" value="" type="text" maxLength="50"/>
						</div>
						<div style="float:left; width:30%">
							<label class="standardInputTitle">Approval Status</label>
							<form:input id="pfiLabApprovalStatus" path="pfiLabApprovalStatus"  class="mL25 standardInputlabText1 readonly" value="" type="text" 
										style="width:260px; margin-left:25px;"/>
						</div>
						
					</div>
						
					<div class="labReportTitle mL30"><b>Address Information:</b></div>
					<div style="float:left; width:99%; margin: 10px 10px 10px 10px;">
						<div style="float:left; width:20%">
							<label class="standardInputTitle"><spring:message code="label.address" /></label>
							<form:input id="pfiLabAddress" path="pfiLabAddress"  class="mL15 standardInputSmallText readonly" type="text" maxLength="200"/>
						</div>

						<div style="float:left; width:20%">
							<label class="standardInputTitle"><spring:message code="label.city" /></label>
							<form:input id="pfiLabCity" path="pfiLabCity"  class="mL25 standardInputSmallText readonly" type="text" maxLength="80"/>
						</div>
						
						<div style="float:left; width:15%">
							<label class="standardInputTitle"><spring:message code="label.state" /></label>
							<form:input id="pfiLabState" path="pfiLabState"  class="mL25 standardInputSmallText1  readOnly" type="text" maxLength="5"/>
						</div>
						
						<div style="float:left; width:10%">
							<label class="standardInputTitle"><spring:message code="label.zip" /></label>
							<form:input id="pfiLabZip" path="pfiLabZip"  class="mL25 standardInputSmallText1  readOnly" type="text" maxLength="10"/>
						</div>
						
						<div style="float:left; width:20%">
							<label class="standardInputTitle"><spring:message code="label.country" /></label>
							<form:input id="pfiLabCountry" path="pfiLabCountry"  class="mL25 standardInputSmallText1  readOnly" type="text" maxLength="40"/>
						</div>
												
					</div>
				</div>
            	
            	<hr>
				<div class="p10">
					<div style="float:left; width:49%; margin: 10px 10px 10px 10px;">

						<div>
                            <div class="width70Perc floatLeft">1.  Does this laboratory perform instrument based/reportable HIV related testing on-site?</div>
                           	<div class="width30Perc floatLeft"> 
                           			<form:radiobutton id="insBasedHivTest1"  path= "insBasedHivTest" value="Y" /> Yes
                            		<form:radiobutton id="insBasedHivTest2" path= "insBasedHivTest" value="N" /> No
                            </div>
						</div>
						
						<div class="pT20">
							<div class="width70Perc floatLeft">2. Does this laboratory report to NYSDOH HIV-related results using the ECLRS?</div>
							<div class="width30Perc floatLeft"> 
            		                <form:radiobutton id="rptNysDohByEclrs1"  path= "rptNysDohByEclrs" value="Y" /> Yes
                    		        <form:radiobutton id="rptNysDohByEclrs2" path= "rptNysDohByEclrs" value="N" /> No
							</div>
						</div>
						
						<div class="pT20">
							<div class="width70Perc floatLeft">3. Does this laboratory use references laboratories to perform testing?</div>
							<div class="width30Perc floatLeft"> 
            		                <form:radiobutton id="labRefLabForTest1"  path= "labRefLabForTest" requiredgroup="requiredgroup" value="Y" /> Yes
                    		        <form:radiobutton id="labRefLabForTest2" path= "labRefLabForTest" requiredgroup="requiredgroup" value="N" /> No
							</div>
						</div>
					</div>
					
					<div style="float:right; width:40%; margin: 10px 10px 10px 10px;">
						<div style ="height:5%"></div>
						<form:hidden path="reviewCompleted"/>
						<form:hidden path="reviewCompletedBy"/>
						<form:hidden path="reviewCompletedDate"/>
						<form:hidden path="reviewCompletedClicked"/>
					</div>
					
				</div>

				<div class="p10">
				
					<div style="float:left; width:99%;">
						<div style="float:left; width:20%">
							<label class="standardInputTitle">Ref PFI</label>
							<form:input id="labRefPFI" path="labRefPFI"  class="mL25 standardInputSmallText1" value="" type="text" disabled="true" maxLength="6" title="Reference PFI"/>
							<input id="searchRefPfi" name="searchRefPfi"  class="mL25 btn btn-primary" value="Search Ref PFI" type="button" disabled="true" onclick="getRefPFILabData();" >
						</div>
						
						<div style="float:left; width:35%">
							<label class="standardInputTitle">Ref Lab Name</label>
							<form:input id="refLabName" path="refLabName" style="margin-left:25px;" class="standardInputlabText1 readonly" type="text" maxLength="100"/>
						</div>

						<div style="float:left; width:25%">
							<label class="standardInputTitle">Ref Lab City</label>
							<form:input id="refLabCity" path="refLabCity"  class="mL25 standardInputSmallText readonly" type="text" maxLength="80"/>
						</div>
						
						<div style="float:left; width:15%">
							<label class="standardInputTitle">Ref Lab State</label>
							<form:input id="refLabState" path="refLabState"  class="mL25 standardInputSmallText1  readOnly" type="text" maxLength="5"/>
						</div>						
					</div>
				</div>
								
				<div class="p10">
					<div style="float:left; width:99%; margin: 10px 10px 10px 10px;">
						<div style="width:100%; padding:2px;" class="container ">
								<span class="hdrFont mL5">4. Complete this section only if <b>HIV or Cellular Immunology testing is performed on-site</b></span>
						</div>	
						
						<div style="padding-top:5px; padding-bottom:5px; border:2px solid;" class="container">						
							<div style="border-bottom:1px solid" class="container">
									<div class="hdrFont mL5" style="float:left; width:12%"> Accession Number</div>
									<div class="hdrFont mL5" style="float:left; width:10%"> Collection Date</div>
									<div class="hdrFont mL5" style="float:left; width:60%"> Test Type (Check all that apply)</div>
							</div>								
			
						<c:forEach items="${clepLabResultVO.clepLabResultAccessionList}" var="tempObj" varStatus="statusCD">
							
							<div class="accessionDiv" id="accessionDiv" style="float:left; width:99%; padding:5px; border-bottom:1px solid;" > 		
							 <div class="container">
							 	<form:hidden path="clepLabResultAccessionList[${statusCD.index}].accessionSerialNo"/>
 								<div style="float:left; width:12%">
									<form:input  path="clepLabResultAccessionList[${statusCD.index}].accessionNo" 
												   id="clepLabResultAccession_${statusCD.index}_accessionNo" class="formInputText2" type="text" maxLength="23"/>
								 	<div class="v_error clepLabResultAccession_${statusCD.index}_accessionNo mL20"></div>
								</div>
 								<div style="float:left; width:10%">
									<form:input  path="clepLabResultAccessionList[${statusCD.index}].collectionDate"
												   id="clepLabResultAccession_${statusCD.index}_collectionDate"  class="mL5 formInputText2 date-picker" maxlength="10" readonly="true"/>
								 	<div class="v_error clepLabResultAccession_${statusCD.index}_collectionDate mL20"></div>
								</div>
								<div style="float:left; width:75%">
									<form:checkbox path="clepLabResultAccessionList[${statusCD.index}].hivImmunoassay" id="clepLabResultAccession_${statusCD.index}_hivImmunoassay" class="mL25"/><span>HIV Immunoassay</span>
									<form:checkbox path="clepLabResultAccessionList[${statusCD.index}].hivNAT" id="clepLabResultAccession_${statusCD.index}_hivNAT" class="mL25"/><span>HIV NAT</span>
									<form:checkbox path="clepLabResultAccessionList[${statusCD.index}].cd4" id="clepLabResultAccessionList_${statusCD.index}_cd4" class="mL25"/><span>CD4</span>
									<form:checkbox path="clepLabResultAccessionList[${statusCD.index}].cluture" id="clepLabResultAccessionList_${statusCD.index}_cluture" class="mL25"/><span>Cluture</span>
									<form:checkbox path="clepLabResultAccessionList[${statusCD.index}].supImmunoassay" id="clepLabResultAccessionList_${statusCD.index}_supImmunoassay" class="mL25"/><span> Supplemental Immunoassay</span>
									<form:checkbox path="clepLabResultAccessionList[${statusCD.index}].genotypicResistance" id="clepLabResultAccessionList_${statusCD.index}_genotypicResistance" class="mL25"/><span>Genotypic Resistance</span>
									<form:checkbox path="clepLabResultAccessionList[${statusCD.index}].other" id="clepLabResultAccessionList_${statusCD.index}_other" class="mL25"/><span>Other</span>
								</div>
							</div>
							<div class="container" style="padding:7px;">
 								<div style="float:left; width:22%">
									<input id="searchAccessionNo_${statusCD.index}" name="searchAccessionNo_${statusCD.index}"  class="mL15 btn btn-primary" value="Search Accession No" type="button" onclick="seachAccessionNo('${statusCD.index}');" >												   
								</div>
								
								<div style="float:left; width:18%">
									<label class="standardInputTitle">Result Reported in NYEHMS</label>
							       <form:select path="clepLabResultAccessionList[${statusCD.index}].resultRptNyehms" id="clepLabResultAccessionList_${statusCD.index}_resultRptNyehms" 
							       	class="formInputText1" style="width:60px;" title="Result Reported in NYEHMS">
									  <form:option value="">Select</form:option>
									  <form:option value="Y">Yes</form:option>
									  <form:option value="N">No</form:option>
									  <form:option value="NA">NA</form:option>
								   </form:select>
							   </div> 
							   
								<div style="float:left; width:10%">
									<label class="standardInputTitle">Resolved</label>
							       <form:select path="clepLabResultAccessionList[${statusCD.index}].resolved" id="clepLabResultAccessionList_${statusCD.index}_resolved" 
							       	class="formInputText1" style="width:60px;" onchange="clearResolvedDate(this, ${statusCD.index})" ontitle="Resolved">
									  <form:option value="">Select</form:option>
									  <form:option value="Y">Yes</form:option>
									  <form:option value="N">No</form:option>
									  <form:option value="NA">NA</form:option>
								   </form:select>
								    <div class="v_error clepLabResultAccessionList_${statusCD.index}_resolved mL20"></div>
							   </div> 

								<div style="float:left; width:25%">
									<label class="standardInputTitle" style="vertical-align: top;">Comments:</label>
									<form:textarea path="clepLabResultAccessionList[${statusCD.index}].comments" id="clepLabResultAccessionList_${statusCD.index}_comments"
										title="Comments" maxlength="4000" cols="200" rows="3" style="width:65%; height:50px; border:1px solid gray;" class="roundedBorder"></form:textarea>									 
								</div>
															   
								<div style="float:left; width:10%">
									<label class="standardInputTitle">Staff:</label>
									<form:input path="clepLabResultAccessionList[${statusCD.index}].laftStaff" id="clepLabResultAccessionList_${statusCD.index}_laftStaff"
									 class="mL25 formInputText1 readonly" type="text" maxLength="10"/>
								</div>
															   
								<div style="float:left; width:15%">
									<label class="standardInputTitle">Date Resolved:</label>
									<form:input path="clepLabResultAccessionList[${statusCD.index}].dateResolved" id="clepLabResultAccessionList_${statusCD.index}_dateResolved"
										 style="width:70px" readonly="true" class="mL15 date-picker"/>
										 <div class="v_error clepLabResultAccessionList_${statusCD.index}_dateResolved mL20"></div>
								</div>

													   
							</div>	
							
							</div>

						</c:forEach>  						
  						
						</div>	

					</div>
					
				</div>												
			</div>
			
			 <div class="button-row">
		 				<input type="button" id="sButton" class="button rounded effect-3 blue"  style="background-color: #62BBE8;" value="Save" onclick="saveCLEPLabResultForm()"/>	
		 				<input type="button" id="cButton"  class="cancelBtnclick button rounded effect-3 blue" style="background-color: #62BBE8;" value="Clear" onclick="clearCLEPLabResultForm()"/>
		 				<input type="button" id="canButton"  class="cancelBtnclick button rounded blue effect-3 displayNone" style="background-color: #62BBE8;" value="Cancel" onclick="cancelClepReviewForm()"/>
		 				<input type="button" id="reviewComplete" class="button rounded effect-3 blue displayNone"  style="background-color: #62BBE8;margin-top: 20px;" 
								value="Close - Review Complete" onclick="setSurveyAsComplete()"/>
			</div>
			<div id="reviewCompleteMsg"  style="margin-right: 440px;" class= "alignRight displayNone">
				<span class= "bold" >** All accessions must be resolved before able to close form *** </span>
			</div>				
											
			</form:form>