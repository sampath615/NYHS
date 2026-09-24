<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<title>Medical Provider Feedback</title>
	<link href="${staticPath}/css/common.css"  rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/reports/MedicalProviderFeedback.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	
	
	<c:set var="feedbackOverallSummary" value="${feedbackReportVO.feedbackReportOverallSummaryVO}" />
	<c:set var="feedbackStateSummary" value="${feedbackReportVO.feedbackReportStateSummaryVO}" />
</head>

<body>
	<p><a onclick="PrintElem('#medicalProvider')" style="float:right">Print this page</a></p>
	<div id="medicalProvider">
		<div id="headerSection" style="border-bottom: 2px solid #000 ;height: 19%">
			<div class="titleFont" style="font-size:1em; font-family:Lucida Grande">	
				<center>
					<span class="center" style="margin: auto; float:right; padding-right:20%">
						<img id="nydohimg"  style="float: left; margin: 0 0 0 10px; padding-right:50px"src="${staticPath}/images/MedicalProvider/nysdoh.jpg">
						Medical Provider HIV/AIDS Report Form Feedback<br>
					    Reporting Period: 
					    <input type="text" value="01/01/2012" name="reportingStartPeriod" class="standardInputTinyText" style=" background: none repeat scroll 0 0 #fff ;border-color: #cccccc #999999 #999999 #cccccc ;border-radius: 8px ;border-style: solid ;border-width: 1px ;font-family: Arial,Helvetica,sans-serif ;font-size: 10pt ;margin-left: auto ; margin-right: auto ;width: 90px ;"> - <input value="8/20/2014" type="text" name="reportingEndPeriod" class="standardInputTinyText" style=" background: none repeat scroll 0 0 #fff ;border-color: #cccccc #999999 #999999 #cccccc ;border-radius: 8px ;border-style: solid ;border-width: 1px ;font-family: Arial,Helvetica,sans-serif ;font-size: 10pt ;margin-left: auto ; margin-right: auto ;width: 90px ;">	
					</span> 
			    </center>		
			</div>	
			<div class="rightAlign" style="float: right ;width:40% ;">
				<div class="p10" style="padding-bottom: 10px ;padding-left: 35px;">
					<span class="w15" style="width:20% ;font-size:0.8em; padding-right:5px">Provider Name: </span> 
					<span class="w15" style="width:20% ;"><input value='<c:out value="${feedbackOverallSummary.providerName}"/>' readonly type="text" name="providerName" class="standardInputSmallText" style="width: 156px;background:#fff;font-family:Arial, Helvetica, sans-serif;font-size:8pt;border-top:1px solid #CCCCCC;border-left:1px solid #CCCCCC;border-right:1px solid #999999;border-bottom:1px solid #999999;border-radius: 8px;"></span>
				</div>	
				<!-- <div class="p10" style="padding-bottom: 10px ;">
					<span class="w15" style="width:20% ; font-size:0.8em;">Facility Name:  </span>
				    <span class="w15" style="width:20% ;">&nbsp;&nbsp;<input type="text" name="facilityName" class="standardInputSmallText" style="width: 156px;background:#fff;font-family:Arial, Helvetica, sans-serif;font-size:8pt;border-top:1px solid #CCCCCC;border-left:1px solid #CCCCCC;border-right:1px solid #999999;border-bottom:1px solid #999999;border-radius: 8px;"></span>
				</div>    -->
				<div class="p10" style="padding-bottom: 10px ;padding-left: 35px;">
					<span class="w15" style="width:20% ; font-size:0.8em; padding-right:40px">Address:  </span>
				    <span class="w15" style="width:20% ;"><input value='<c:out value="${feedbackOverallSummary.address1}"/>' readonly type="text" name="Address" class="standardInputSmallText" style="width: 156px;background:#fff;font-family:Arial, Helvetica, sans-serif;font-size:8pt;border-top:1px solid #CCCCCC;border-left:1px solid #CCCCCC;border-right:1px solid #999999;border-bottom:1px solid #999999;border-radius: 8px;"></span>
				</div>   
				<div class="p10" style="padding-bottom: 10px ;padding-left: 35px;">
					<span class="w15" style="width:20% ; font-size:0.8em; padding-right:40px">Address:  </span>
				    <span class="w15" style="width:20% ;"><input value='<c:out value="${feedbackOverallSummary.address2}"/>' readonly type="text" name="Address" class="standardInputSmallText" style="width: 156px;background:#fff;font-family:Arial, Helvetica, sans-serif;font-size:8pt;border-top:1px solid #CCCCCC;border-left:1px solid #CCCCCC;border-right:1px solid #999999;border-bottom:1px solid #999999;border-radius: 8px;"></span>
				</div> 
				<div class="p10" style="padding-bottom: 10px ;padding-left: 35px;">
					<span class="w15" style="width:20% ;font-size:0.8em; padding-right:5px">Phone Number:  </span>
				    <span class="w15" style="width:20% ;"><input value='<c:out value="${feedbackOverallSummary.phoneNumber}"/>' readonly type="text" name="phoneNumber" class="standardInputSmallText" style="width: 156px;background:#fff;font-family:Arial, Helvetica, sans-serif;font-size:8pt;border-top:1px solid #CCCCCC;border-left:1px solid #CCCCCC;border-right:1px solid #999999;border-bottom:1px solid #999999;border-radius: 8px;"></span>
				</div>  
			</div>
		</div>
		<div id="overviewHolder" style="border-bottom: 2px solid #000 ;height: 16% ;">
			<p class="subTitle" style="font-size: 1em ; font-weight:bold ;">OVERVIEW</p> 
			<div class="mLRTB" style="margin:10px;">
				<span class="fieldHolder33" style="float: left ;width: 28% ;">
					<span class="fLW45" style="float:left ; width:45% ;font-size:0.8em;"> Total PRFs Received:</span> 
					<span class="fLW40" style="float:left ;width:40% ;"> <input value='<c:out value="${feedbackOverallSummary.generateTotalPRFS}"/>' readonly class="standardInputSmallText" style="width: 100px;background:#fff;font-family:Arial, Helvetica, sans-serif;font-size:10pt;border-top:1px solid #CCCCCC;border-left:1px solid #CCCCCC;border-right:1px solid #999999;border-bottom:1px solid #999999;border-radius: 8px;"> </span>
				</span>
				<span class="fieldHolder33" style="float: left ;width: 28% ;">
					<span class="fLW45" style="float:left ; width:45% ;font-size:0.8em;"> Top Reported Risk Category:</span>
					<span class="fLW40" style="float:left ;width:40% ;"> <input value='<c:out value="${feedbackOverallSummary.topRiskReportedCategory}"/>' readonly class="standardInputSmallText" style=" background: none repeat scroll 0 0 #fff ;border-color: #cccccc #999999 #999999 #cccccc ;border-radius: 8px ;border-style: solid ;border-width: 1px ;font-family: Arial,Helvetica,sans-serif ;font-size: 10pt ;margin-left: auto ; margin-right: auto ;width: 100px ;"> </span>
				</span>
				<span class="fieldHolder33" style="float: left ;width: 28% ;">
					<span class="fLW45" style="float:left ; width:45% ;"></span>
					<span class="fLW40" style="float:left ;width:40% ;"></span>
				</span>
			</div>
			<div class="mLRTB" style="margin:10px;"><br><br>
				<span class="fieldHolder33" style="float: left ;width: 28% ;">
					<span class="fLW45" style="float:left ; width:45% ;font-size:0.8em;"> Number of Initial Diagnoses:</span> 
					<span class="fLW40" style="float:left ;width:40% ;"> <input value='<c:out value="${feedbackOverallSummary.initialDiagnoseCount}"/>' readonly class="standardInputSmallText" style="width: 100px;background:#fff;font-family:Arial, Helvetica, sans-serif;font-size:10pt;border-top:1px solid #CCCCCC;border-left:1px solid #CCCCCC;border-right:1px solid #999999;border-bottom:1px solid #999999;border-radius: 8px;"> </span>
				</span>
				<span class="fieldHolder33" style="float: left ;width: 28% ;">
					<span class="fLW45" style="float:left ; width:45% ;font-size:0.8em;"> HIV</span>
					<span class="fLW40" style="float:left ;width:40% ;"> <input value='<c:out value="${feedbackOverallSummary.hivDiagnoseCount}"/>' readonly class="standardInputSmallText" style="width: 100px;background:#fff;font-family:Arial, Helvetica, sans-serif;font-size:10pt;border-top:1px solid #CCCCCC;border-left:1px solid #CCCCCC;border-right:1px solid #999999;border-bottom:1px solid #999999;border-radius: 8px;"> </span>
				</span>
				<span class="fieldHolder33" style="float: left ;width: 28% ;">
					<span class="fLW45" style="float:left ; width:45% ;font-size:0.8em;"> AIDS</span>
					<span class="fLW40" style="float:left ;width:40% ;"> <input value='<c:out value="${feedbackOverallSummary.aidsDiagnoseCount}"/>' readonly class="standardInputSmallText" style="width: 100px;background:#fff;font-family:Arial, Helvetica, sans-serif;font-size:10pt;border-top:1px solid #CCCCCC;border-left:1px solid #CCCCCC;border-right:1px solid #999999;border-bottom:1px solid #999999;border-radius: 8px;"> </span>
				</span>
			</div>
			<div class="mLRTB" style="margin:10px;"><br><br>
				<span class="fLW48" style="font-size:0.8em;"> Percentage of newly diagnosed HIV patients that were informed of his/her positive status?</span> 
				<span class="fLW15"> <input value='<c:out value="${feedbackOverallSummary.newlyDiagnosedHIVPercentage}"/>' readonly class="standardInputSmallText" style="width: 156px;background:#fff;font-family:Arial, Helvetica, sans-serif;font-size:10pt;border-top:1px solid #CCCCCC;border-left:1px solid #CCCCCC;border-right:1px solid #999999;border-bottom:1px solid #999999;border-radius: 8px;"></span>
			</div>	
		</div>	
		<div id="completenessHolder" style="border-bottom: 2px solid #000 ;height: 59% ;">
			<p class="subTitle" style="font-size: 1em ; font-weight:bold ;">Completeness of Important Information on PRFs</p>
			<table style="width:100%" class="reference" style="border-collapse: collapse ;width: 100% ;font-size: 1.2em ;">
				<tbody>
					<tr>
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;">Variables</th>
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;">Total # Forms with Complete Data</th>		
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;">% of Total PRFs Received</th>
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;">% For All Other Providers in NYS</th>
					</tr>
					<tr>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;" colspan="4">Required Variables</td>
					</tr>	
					<tr>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ; background-color: #f1f1f1 ;">Gender</td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ; background-color: #f1f1f1 ;text-align:center"><c:out value="${feedbackOverallSummary.genderFilledCount}"/></td>		
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ; background-color: #f1f1f1 ;text-align:center"><c:out value="${feedbackOverallSummary.genderFilledPercenage}"/></td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ; background-color: #f1f1f1 ;text-align:center"><c:out value="${feedbackStateSummary.stateGenderFilledPercenage}"/></td>
					</tr>
					<tr>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;">Date of Birth</td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;text-align:center"><c:out value="${feedbackOverallSummary.dobFilledCount}"/></td>		
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;text-align:center"><c:out value="${feedbackOverallSummary.dobFilledPercentage}"/></td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;text-align:center"><c:out value="${feedbackStateSummary.stateDobFilledPercentage}"/></td>
					</tr>
					<tr>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;">Race</td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;text-align:center"><c:out value="${feedbackOverallSummary.raceFilledCount}"/> </td>		
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;text-align:center"><c:out value="${feedbackOverallSummary.raceFilledPercentage}"/></td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;text-align:center"><c:out value="${feedbackStateSummary.stateRaceFilledPercentage}"/></td>
					</tr>
					<tr>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;">Ethnicity</td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;text-align:center"><c:out value="${feedbackOverallSummary.ethnicityFilledCount}"/></td>		
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;text-align:center"><c:out value="${feedbackOverallSummary.ethnicityFilledPercentage}"/></td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;text-align:center"><c:out value="${feedbackStateSummary.stateEthnicityFilledPercentage}"/></td>
					</tr>
					<tr>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;">Vital Status (Date of Death)</td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;text-align:center"><c:out value="${feedbackOverallSummary.dodFilledCount}"/></td>		
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;text-align:center"><c:out value="${feedbackOverallSummary.dodFilledPercentage}"/></td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;text-align:center"><c:out value="${feedbackStateSummary.stateDodFilledPercentage}"/></td>
					</tr>
					<tr>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;">Risk *</td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;text-align:center"><c:out value="${feedbackOverallSummary.allRiskFilledCount}"/></td>		
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;text-align:center"><c:out value="${feedbackOverallSummary.allRiskFilledPercentage}"/></td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;text-align:center"><c:out value="${feedbackStateSummary.stateAllRiskFilledPercentage}"/></td>
					</tr>
				</tbody>
		    </table>
		    <br>
		    <span>*Indicates response to each risk question </span>
		</div>
		
		<div id="providerTimeliness" style="border-bottom: 2px solid #000 ;height: 100% ;">
			<p class="subTitle" style="font-size: 1em ; font-weight:bold ;">Provider Timeliness</p>
			<center>
				 <div class="box" style="float:right; padding-right:100px; padding-top: 38px;">
					<div class="box2" style="border:1px solid #000 ;background-color:#437346 ;height:8px ;width:8px ;float:left ;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You </div><br> 
					<div class="box1" style="border:1px solid #000 ;background-color:#97D95C ;height:8px ;width:8px ;float:left ;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Other NYS Providers</div>
			    </div> 
			   <div class="vertical-text" style="transform: rotate(270deg); width:100px; float:left; margin-top:150px; margin-left:50px;">PERCENT OF PROVIDER REPORT FORMS</div>
				<div id="graphContainer" style="width: 700px ;height: 400px ;padding: 10px ;border: 5px solid gray ;margin: 0px ;">
					<center>
						<p class="h20" style="font-size: 1.4em ;font-color:#000 ;height:40px ;">Timeliness in Case Reporting</p>
				
						<div id='divForGraph'></div>
						<p>NUMBER OF DAYS FROM DATE PRF IS COMPLETED TO DATE PRF IS CREATED IN TRACKING</p>
						<!-- <div class="box4" style="float:right; padding-right:100px; padding-top: 38px;">
							<span class="box1" style="border:1px solid #000 ;background-color:#97D95C ;height:8px ;width:8px ;float:left ;"></span>
							<span>You&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span class="box2" style="border:1px solid #000 ;background-color:#437346 ;height:8px ;width:8px ;float:left ;"></span>
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Other&nbsp;NYS&nbsp;Providers</span>
						</div>	 -->
					</center>
				</div>
			</center>
			<p><b>*New York State law,</b> Section 63.4 of 2012 requires that medical providers shall submit PRFs as soon as possible after post-test counseling, but no later than 14 days after the provider's receipt of a positive laboratory result or after diagnosis, whichever is sooner.</p>
			<center>
				<div class="box" style="float:right; padding-right:100px; padding-top: 38px;">
					<div class="box2" style="border:1px solid #000 ;background-color:#437346 ;height:8px ;width:8px ;float:left ;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You </div> <br>
					<div class="box1" style="border:1px solid #000 ;background-color:#97D95C ;height:8px ;width:8px ;float:left ;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Other NYS Providers</div>
				</div>	
				<div class="vertical-text" style="transform: rotate(270deg); width:100px; float:left; margin-top:150px; margin-left:50px;">PERCENT OF INFORMED PATIENTS</div>
				<div id="graphContainer" style="width: 700px ;height: 350px ;padding: 10px ;border: 5px solid gray ;margin: 0px ;">
					<center>
						<p class="h20" style="font-size: 1.4em ;font-color:#000 ;height:40px ;">Timeliness of Reporting HIV+ Diagnosis to Patients</p>

					<div id='divForGraphHIV'></div>
					<p>LENGTH OF TIME FROM HIV DIAGNOSIS DATE TO DATE PATIENT WAS INFORMED OF POSITIVE STATUS</p> 
					</center>
				</div>
			</center>
		</div>
		<div id="treatmentHistory" style="border-bottom: 2px solid #000 ;height: 29% ;">
			<p class="subTitle" style="font-size: 1em ; font-weight:bold ;">Completeness of Testing and Treatment History</p>
			<p>Total number of HIV Incidence Cases Reported: <input value="<c:out value='${feedbackOverallSummary.totalHIVIncidenceReported}'/>" readonly type="text" name="reportedHIV" class="standardInputTinyText" style=" background: none repeat scroll 0 0 #fff ;border-color: #cccccc #999999 #999999 #cccccc ;border-radius: 8px ;border-style: solid ;border-width: 1px ;font-family: Arial,Helvetica,sans-serif ;font-size: 10pt ;margin-left: auto ; margin-right: auto ;width: 90px ;/></p>
			<table style="width:100%" class="reference" style="border-collapse: collapse ;width: 100% ;font-size: 1.2em ;">
				<tbody>
					<tr>
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;"></th>
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;">Total # PRFs with Complete Data</th>		
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;">% of Total PRFs Received</th>
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;">% For All Other Providers in NYS</th>
					</tr>	
					<tr>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;">Completeness of TTH variables based on CDC definition</td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;text-align:center"><c:out value="${feedbackOverallSummary.testingTreatmentHistoryCount}"/></td>		
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;text-align:center"><c:out value="${feedbackOverallSummary.testingTreatmentHistoryPercentage}"/></td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;text-align:center"><c:out value="${feedbackStateSummary.stateTestingTreatmentHistoryPercentage}"/></td>
					</tr>
				</tbody>
		    </table>
		    <p>*Completeness of TTH is defined as having at least one of the seven data elements, where the value is not blank: (1) TTH date of first positive; (2) Ever tested negative; (3) Number of negative HIV tests within 24 months before first positive HIV test; (4) TTH date of last negative; (5) Ever had a previous positive test; (6) Ever taken ARVs; (7) ARV use dates.</p>	
		</div>	
		<div id="treatmentHistory1" style="border-bottom: 2px solid #000 ;height: 68% ;">
			<p class="subTitle" style="font-size: 1em ; font-weight:bold ;">Partner/Contact Information</p>
			<table style="width:100%" class="reference" style="border-collapse: collapse ;width: 100% ;font-size: 1.2em ;">
				<tbody>
					<tr>
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;"></th>
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;">Total # PRFs</th>		
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;">% of Total PRFs Received</th>
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;">% For All Other Providers in NYS</th>
					</tr>	
					<tr>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;">DOH Assistance was Requested</td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;text-align:center"><c:out value="${feedbackOverallSummary.dohAssistanceRequiredCount}"/></td>		
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;text-align:center"><c:out value="${feedbackOverallSummary.dohAssistanceRequiredPercent}"/></td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;text-align:center"><c:out value="${feedbackStateSummary.stateDohAssistanceRequiredPercent}"/></td>
					</tr>
					<tr>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;">Partners/Contacts Could <b>Not</b> be Named</td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;text-align:center"><c:out value="${feedbackOverallSummary.partnerContactNamedCount}"/></td>		
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;text-align:center"><c:out value="${feedbackOverallSummary.partnerContactNamedPercentage}"/></td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;text-align:center"><c:out value="${feedbackStateSummary.statePartnerContactNamedPercentage}"/></td>
					</tr>
					<tr>
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;">&nbsp;</th>
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;"></th>		
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;"></th>
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;"></th>
					</tr>	
					<tr>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;" colspan="4" class="bgcolortd"><b>Total Number of Partners Reported: <c:out value="${feedbackOverallSummary.totalPartnersReported}"/></b></td>
					</tr>
					<tr>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;">&nbsp;</td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;"># of partners with PAS provided</td>		
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;">% of Total Partners Reported</td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;">% For All Other Providers in NYS</td>
					</tr>
					<tr>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;">Partner Assistance Status Provided</td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;text-align:center"><c:out value="${feedbackOverallSummary.numberAssistanceStatusProvided}"/></td>		
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;text-align:center"><c:out value="${feedbackOverallSummary.percentAssistanceStatusProvided}"/></td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;text-align:center"><c:out value="${feedbackStateSummary.statePercentageAssistanceStatusProvided}"/></td>
					</tr>
					<tr>
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;">&nbsp;</th>
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;"></th>		
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;"></th>
						<th style="background-color: #555555 ;border: 1px solid #555555 ;color: #ffffff ;padding: 3px ;text-align: left ;vertical-align: top ;"></th>
					</tr>
					<tr>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;">Partner notified at time of PRF completion</td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;text-align:center"><c:out value="${feedbackOverallSummary.numberPartnerNotified}"/></td>		
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;text-align:center"><c:out value="${feedbackOverallSummary.percentPartnerNotified}"/></td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;background-color: #f1f1f1 ;text-align:center"><c:out value="${feedbackStateSummary.statePercentagePartnerNotified}"/></td>
					</tr>
					<tr>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;">Partner <b>not</b> notified at time of PRF completion</td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;text-align:center"><c:out value="${feedbackOverallSummary.numberPartnerNotNotified}"/></td>		
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;text-align:center"><c:out value="${feedbackOverallSummary.percentPartnerNotNotified}"/></td>
						<td style="border: 1px solid #696969 ;padding: 7px 5px ;vertical-align: top ;text-align:center"><c:out value="${feedbackStateSummary.statePercentagePartnerNotNotified}"/></td>
					</tr>
				</tbody>
		    </table>
		    <br>
		</div>
		<div id="coverLetter"  class="pT">
			<div id="coverLetterTitle" style="padding-top:10px; font-size: 1.35em ; font-weight:bold ">
				<span class="ui-icon ui-icon-plus navigatorCover" style="display: inline-block;"></span>NYS Feedback Cover Letter
			</div>	
			<div id="coverLetterContent" style="font-size:12px; border-bottom:2px solid #000; height: 95% ">
				<p>June 27, 2014</p>
				<p>Dear Healthcare Provider:</p>
				<p>As required by New York State law, Section 63.4 of 2012, "all determinations or diagnoses of Human Immunodeficiency Virus (HIV) infection, HIV-related illness and Acquired Immune Deficiency Syndrome (AIDS) shall be reported to the commissioner by physicians and other persons authorized to order diagnostic tests or make medical diagnoses or their agents as soon as possible after post-test counseling but no later than 14 days after the provider's receipt of a positive laboratory result or after diagnosis, whichever is sooner".  
				<p>New York State Department of Health (NYSDOH), Bureau of HIV/AIDS Epidemiology (BHAE) works closely with HIV field staff personnel to continuously improve the quality and completeness of surveillance data.  In efforts to increase  New York State's adherence  to guidelines set by CDC regarding provider reporting, a Medical Provider Report  Card has been implemented that will be distributed to provide  direct feedback on data submitted to NYSDOH using the state mandated DOH-4189 form. This report card will provide basic overall statistics on the DOH-4189 forms submitted by specific providers and more detailed analysis of the individual data components submitted on the form. </p> 
				<p>Please see the accompanying technical guidance document for a more detailed description of the Report Card components. If you have any questions or concerns, please feel free to contact me.  Feedback regarding the report card may be directed to BHAE Surveillance Coordinator, Deepa Rajulu, <a href="mailto:Deepa.Rajulu@health.ny.gov" target="_top">Deepa.Rajulu@health.ny.gov</a>, <b>518-474-4284</b>. Suggestions are for further enhancements of the report card. Our goal in offering NYS Medical Providers individual feedback is to improve surveillance data quality, completeness and timeliness of reporting. This data is successfully disseminated among various audiences because of your continued willingness to work with NYSDOH to improve the data. We thank you in advance for your continued support and hope that you find the report card to be a benefit to improve reporting.</p>

				<p>Sincerely,</p> 

				<p>Bridget J. Anderson, PhD<br>
				Office of Public Health<br>
				New York State Department of Health<br>
				Albany NY 12237-0627<br>
				Voice: 518-474-4284<br>
				Fax: 518-474-1947<br>
				Email: <a href="mailto:Bridget.Anderson@health.ny.gov" target="_top">Bridget.Anderson@health.ny.gov</a></p>

			</div>	
		</div>	

		<div id="technicalNotes">
			<div id="technicalNotesTitle" style="padding-top:10px; font-size: 1.35em ; font-weight:bold ">
				<span class="ui-icon ui-icon-plus navigatorTech" style="display: inline-block;"></span>NYS Provider Feedback Report Technical Notes
			</div>
			<div id="technicalNotesContent" style="font-size:12px; height:71%;">
				<p><center class="subTitleCaption" style="font-size: 1.35em ; font-weight:bold">Provider Report Card Technical Notes</center></p>
				<p>Your provider report card is based on data from all Provider Report Forms (PRFs) submitted either by said provider or on behalf of said provider, during the designated time frame.</p>

				<p><span class="subTitle" style="font-size: 1.2em ; font-weight:bold">Overview:</span><br> 
				This section briefly highlights a few important statistics that deserve special attention. This will give the provider a generalized idea on how he/she is doing as a medical provider to patients with HIV/AIDS and the types of cases being seen most often.</p>

				<span class="subTitle" style="font-size: 1.2em ; font-weight:bold">Completeness Feedback</span>
				<ul>
					<li><u>Total # Forms with Complete Data</u> The raw number of forms received from medical provider during the specified reporting period that had variable of interest completed.</li>
					<li><u>% of Total PRFs Received:</u> Percentage of submitted PRFs with completed information on the variable.</li> 
					<li><u>% for All Other Providers in NYS:</u> The percentage of PRFs submitted with completed variable information (excluding your facility). This category is used to compare your completeness to the completeness of other providers in New York excluding NYC.</li> 
					<li><u>Required Variables:</u> These are variables that must be completed for CDC to count as a case.</li>
					<li><u>Testing & Treatment History (TTH):</u> The TTH variables listed on the report card are required only for HIV incidence cases. The total number of new HIV+ cases reported by you is given above the chart on page two. Of the total number of incidence cases, the number and percentage of PRFs with a completed TTH section are listed in the chart. This chart also compares your completeness to that of all other NYS providers.</li>
					<li><u>Partner/Contact Information:</u> This section reflects part three of the PRF. The first section reflects the number of times you were able to obtain patient contact information and the number of times you were unable to. 
						<ul>
							<li><u>Partner Assistance Status Provided:</u> This reflects the total number of times this variable was completed for each partner listed. The total number of reported partners is listed directly above.</li> 
							<ul>
								<li><u>Partners Notified/Partners Not Notified:</u> The final section uses the number of partners with partner assistance status provided. Of those; partners were considered 'notified' if you selected category 2, 4 or 9 and partners were considered 'not notified' if you selected category 1, 3, 5, 6, 7, 8, 10 or 11.</li> 
							</ul>
						</ul>
					</li>			
				</ul>
				<span class="subTitle" style="font-size: 1.2em ; font-weight:bold">Provider Timeliness</span>
				<ul>
					<li><u>Timeliness in Case Reporting:</u> This section presents a bar graph showing time between the date the PRF was created in the NYSDOH Surveillance System and date the PRF was completed. The graph was created to show how often the provider submits his/her PRFs to the NYSDOH on time; that is, in 14 days or less. The second bar at each interval should be used as a point of comparison of yourself to all other NYS providers.</li> 
					<li><u>Timeliness of Reporting HIV+ Diagnosis to Patient:</u> This bar graph is created using those who were informed of his/her new HIV+ status to show how long it took for the patient to be informed of their status from the time of the initial diagnosis. Again, the second bar at each interval should be used as a point of comparison of yourself to all other NYS providers.</li>
				</ul>

			</div>	
		</div>
	</div>	

 
			   


			 

	 <script>
		$(document).ready(function(){
			arrayOfData = new Array(
				[[<c:out value="${feedbackOverallSummary.percentageCompleted14Days}"/>,<c:out value="${feedbackStateSummary.statePercentageCompleted14Days}"/>],'0-14*'],
				[[<c:out value="${feedbackOverallSummary.percentageCompleted29Days}"/>,<c:out value="${feedbackStateSummary.statePercentageCompleted29Days}"/>],'15-29'],
			   [[<c:out value="${feedbackOverallSummary.percentageCompleted44Days}"/>,<c:out value="${feedbackStateSummary.statePercentageCompleted44Days}"/>],'30-44'],
			   [[<c:out value="${feedbackOverallSummary.percentageCompleted59Days}"/>,<c:out value="${feedbackStateSummary.statePercentageCompleted59Days}"/>],'45-59'],
			   [[<c:out value="${feedbackOverallSummary.percentageCompletedDaysUnknown}"/>,
			   <c:out value="${feedbackStateSummary.statePercentageCompletedDaysUnknown}"/>],'Unknown']
			);
			 

			arrayOfDataHIV = new Array(
			   [[<c:out value="${feedbackOverallSummary.percentageInFormed2Days}"/>,<c:out value="${feedbackStateSummary.statePercentageInFormed2Days}"/>],'0-2 Days'],
			   [[<c:out value="${feedbackOverallSummary.percentageInFormed6Days}"/>,<c:out value="${feedbackStateSummary.statePercentageInFormed6Days}"/>],'3-6 Days'],
			   [[<c:out value="${feedbackOverallSummary.percentageInFormed2Weeks}"/>,<c:out value="${feedbackStateSummary.statePercentageInFormed2Weeks}"/>],'1-2 Weeks'],
			   [[<c:out value="${feedbackOverallSummary.percentageInFormed2PlusWeeks}"/>,
			   <c:out value="${feedbackStateSummary.statePercentageInFormed2PlusWeeks}"/>],'2+ Weeks'],
			   [[<c:out value="${feedbackOverallSummary.percentageInFormedDausUnkonwn}"/>,
			   <c:out value="${feedbackStateSummary.statePercentageInFormedDausUnkonwn}"/>],'Unknown']
			); 
			$('#divForGraph').jqBarGraph({  data: arrayOfData, // array of data for your graph
											title: false, // title of your graph, accept HTML
											barSpace: 10, // this is default space between bars in pixels
											width: 400, // default width of your graph
											height: 200, //default height of your graph
											color: '#66CCCC', // if you don't send colors for your data this will be default bars color
											colors: true, // array of colors that will be used for your bars and legends
											colors: ['#437346','#97D95C'],
											lbl: '', // if there is no label in your array
											sort: false, // sort your data before displaying graph, you can sort as 'asc' or 'desc'
											position: 'bottom', // position of your bars, can be 'bottom' or 'top'. 'top' doesn't work for multi type
											prefix: '', // text that will be shown before every label
											postfix: '', // text that will be shown after every label
											animate: true, // if you don't need animated appearance change to false
											speed: 2, // speed of animation in seconds
											legendWidth: 100, // width of your legend box
											legend: false, // if you want legend change to true
											legends: false, // array for legend. for simple graph type legend will be extracted from labels if you don't set this
											type: true, // for multi array data default graph type is stacked, you can change to 'multi' for multi bar type
											type: "multi",
											showValues: true, // you can use this for multi and stacked type and it will show values of every bar part
											showValuesColor: '#fff' // color of font for values  
										});
			
			$('#divForGraphHIV').jqBarGraph({  data: arrayOfDataHIV, // array of data for your graph
											title: false, // title of your graph, accept HTML
											barSpace: 10, // this is default space between bars in pixels
											width: 400, // default width of your graph
											height: 200, //default height of your graph
											color: '#66CCCC', // if you don't send colors for your data this will be default bars color
											colors: true, // array of colors that will be used for your bars and legends
											colors: ['#437346','#97D95C'],
											lbl: '', // if there is no label in your array
											sort: false, // sort your data before displaying graph, you can sort as 'asc' or 'desc'
											position: 'bottom', // position of your bars, can be 'bottom' or 'top'. 'top' doesn't work for multi type
											prefix: '', // text that will be shown before every label
											postfix: '', // text that will be shown after every label
											animate: true, // if you don't need animated appearance change to false
											speed: 2, // speed of animation in seconds
											legendWidth: 100, // width of your legend box
											legend: false, // if you want legend change to true
											legends: false, // array for legend. for simple graph type legend will be extracted from labels if you don't set this
											type: true, // for multi array data default graph type is stacked, you can change to 'multi' for multi bar type
											type: "multi",
											showValues: true, // you can use this for multi and stacked type and it will show values of every bar part
											showValuesColor: '#fff' // color of font for values  
										});
			});
	</script>
	<script type="text/javascript">
		  $(document).ready(function() {
		  	$('div[id*="coverLetterContent"]').hide();
		  	$('div[id*="technicalNotesContent"]').hide();
		  	$("#coverLetter").on("click", '.navigatorCover', function(q) {
				if($(this).hasClass("ui-icon-minus")){
					$('.navigatorCover').removeClass("ui-icon-minus").addClass("ui-icon-plus");
					$('div[id*="coverLetterContent"]').hide();
				}else{
					$('.navigatorCover').removeClass("ui-icon-plus").addClass("ui-icon-minus");
					$('div[id*="coverLetterContent"]').show();
				}
			});	
			$("#technicalNotes").on("click", '.navigatorTech', function(q) {
				if($(this).hasClass("ui-icon-minus")){
					$('.navigatorTech').removeClass("ui-icon-minus").addClass("ui-icon-plus");
					$('div[id*="technicalNotesContent"]').hide();
				}else{
					$('.navigatorTech').removeClass("ui-icon-plus").addClass("ui-icon-minus");
					$('div[id*="technicalNotesContent"]').show();
				}
			});				
		  });
		</script>
</body>