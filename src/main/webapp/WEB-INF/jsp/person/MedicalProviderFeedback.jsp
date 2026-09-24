<!DOCTYPE html>
<html>
<head>
	<title>Medical Provider Feedback</title>
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/MedicalProviderFeedback.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${staticPath}/js/common/jqBarGraph.1.1.js"></script>
	<script>
		$(document).ready(function(){
			arrayOfData = new Array(
			   [[24,54],'0-14*'],
			   [[18,48],'15-29'],
			   [[14,36],'30-44'],
			   [[28,50],'45-59'],
			   [[31,59],'Unknown']
			);
			 

			arrayOfDataHIV = new Array(
			   [[10,20],'0-2 Days'],
			   [[40,30],'3-6 Days'],
			   [[15,25],'1-2 Weeks'],
			   [[45,35],'2+ Weeks'],
			   [[25,50],'Unknown']
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
</head>

<body>
	<div id="medicalProvider">
		<a href="#" onclick="window.print();return false;">Print this page</a>
		<div id="headerSection">
			<div class="titleFont">	
				<center>
					<span class="center">
						<img id="nydohimg" src="${staticPath}/images/MedicalProvider/nysdoh.jpg">
						Medical Provider HIV/AIDS Report Form Feedback<br>
					    Reporting Period: 
					    <input type="text" name="reportingStartPeriod" class="standardInputTinyText"> - <input type="text" name="reportingEndPeriod" class="standardInputTinyText">	
					</span> 
			    </center>		
			</div>	
			<div class="rightAlign">
				<div class="p10">
					<span class="w15">Provider Name: </span> 
					<span class="w15"><input type="text" name="providerName" class="standardInputSmallText"></span>
				</div>	
				<div class="p10">
					<span class="w15">Facility Name:  </span>
				    <span class="w15">&nbsp;&nbsp;<input type="text" name="facilityName" class="standardInputSmallText"></span>
				</div>   
				<div class="p10">
					<span class="w15">Address:  </span>
				    <span class="w15">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="Address" class="standardInputSmallText"></span>
				</div>   
				<div class="p10">
					<span class="w15">Phone Number:  </span>
				    <span class="w15"><input type="text" name="phoneNumber" class="standardInputSmallText"></span>
				</div>  
			</div>
		</div>
		<div id="overviewHolder">
			<p class="subTitle">OVERVIEW</p> 
			<div class="mLRTB">
				<span class="fieldHolder33">
					<span class="fLW45"> Total PRFs Received:</span> 
					<span class="fLW40"> <input class="standardInputSmallText"> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW45"> Top Reported Risk Category:</span>
					<span class="fLW40"> <input class="standardInputSmallText"> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW45"></span>
					<span class="fLW40"></span>
				</span>
			</div>
			<div class="mLRTB"><br><br>
				<span class="fieldHolder33">
					<span class="fLW45"> Number of Initial Diagnoses:</span> 
					<span class="fLW40"> <input class="standardInputSmallText"> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW45"> HIV</span>
					<span class="fLW40"> <input class="standardInputSmallText"> </span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW45"> AIDS</span>
					<span class="fLW40"> <input class="standardInputSmallText"> </span>
				</span>
			</div>
			<div class="mLRTB"><br><br>
				<span class="fLW48"> Percentage of newly diagnosed HIV patients that were informed of his/her positive status?</span> 
				<span class="fLW15"> <input class="standardInputSmallText">% </span>
			</div>	
		</div>	
		<div id="completenessHolder">
			<p class="subTitle">Completeness of Important Information on PRFs</p>
			<table style="width:100%" class="reference">
				<tbody>
					<tr>
						<th>Variables</th>
						<th>Total # Forms with Complete Data</th>		
						<th>% of Total PRFs Received</th>
						<th>% For All Other Providers in NYS</th>
					</tr>
					<tr>
						<td colspan="4">Required Variables</td>
					</tr>	
					<tr>
						<td>Gender</td>
						<td></td>		
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>Date of Birth</td>
						<td></td>		
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>Race</td>
						<td></td>		
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>Ethnicity</td>
						<td></td>		
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>Vital Status</td>
						<td></td>		
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>Risk (Respond to each)</td>
						<td></td>		
						<td></td>
						<td></td>
					</tr>
				</tbody>
		    </table>
		</div>
		
		<div id="providerTimeliness">
			<p class="subTitle">Provider Timeliness</p>
			<center><div id="graphContainer"><center><p class="h20">Timeliness in Case Reporting</p><div class="box2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You </div><br> <div class="box1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Other NYS Providers</div><div id='divForGraph'></div><p>NUMBER OF DAYS FROM DATE PRF IS COMPLETED TO DATE PRF IS CREATED IN TRACKING</p></center></div></center>
			<p><b>*New York State law,</b> Section 63.4 of 2012 requires that medical providers shall submit PRFs as soon as possible after post-test counseling, but no later than 14 days after the provider's receipt of a positive laboratory result or after diagnosis, whichever is sooner.</p>
			<center><div id="graphContainer"><center><p class="h20">Timeliness of Reporting HIV+ Diagnosis to Patients</p><div class="box1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You </div> <br><div class="box2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Other NYS Providers</div><div id='divForGraphHIV'></div><p>LENGTH OF TIME FROM HIV DIAGNOSIS DATE TO DATE PATIENT WAS INFORMED OF POSITIVE STATUS</p> </center></div></center>
		</div>
		<div id="treatmentHistory">
			<p class="subTitle">Completeness of Testing and Treatment History</p>
			<p>Total number of HIV Incidence Cases Reported:<input type="text" name="reportedHIV" class="standardInputTinyText"></p>
			<table style="width:100%" class="reference">
				<tbody>
					<tr>
						<th></th>
						<th>Total # PRFs with Complete Data</th>		
						<th>% of Total PRFs Received</th>
						<th>% For All Other Providers in NYS</th>
					</tr>	
					<tr>
						<td>Complete data for all TTH Variables</td>
						<td></td>		
						<td></td>
						<td></td>
					</tr>
				</tbody>
		    </table>
		    <p>*In order to be considered complete...</p>	
		</div>	
		<div id="treatmentHistory">
			<p class="subTitle">Partner/Contact Information</p>
			<p>Total number of HIV Incidence Cases Reported:<input type="text" name="reportedHIV" class="standardInputTinyText"></p>
			<table style="width:100%" class="reference">
				<tbody>
					<tr>
						<th></th>
						<th>Total # PRFs</th>		
						<th>% of Total PRFs Received</th>
						<th>% For All Other Providers in NYS</th>
					</tr>	
					<tr>
						<td>DOH Assistance was Requested</td>
						<td></td>		
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>Partners/Contacts Could <b>Not</b> be Named</td>
						<td></td>		
						<td></td>
						<td></td>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<th></th>		
						<th></th>
						<th></th>
					</tr>	
					<tr>
						<td colspan="4" class="bgcolortd"><b>Total Number of Partners Reported:</b></td>
					</tr>
					<tr>
						<td></td>
						<td># of partners with PAS provided</td>		
						<td>% of Total Partners Reported</td>
						<td>% For All Other Providers in NYS</td>
					</tr>
					<tr>
						<td>Partner Assistance Status Provided</td>
						<td></td>		
						<td></td>
						<td></td>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<th></th>		
						<th></th>
						<th></th>
					</tr>
					<tr>
						<td>Partner notified at time of PRF completion</td>
						<td></td>		
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>Partner <b>not</b> notified at time of PRF completion</td>
						<td></td>		
						<td></td>
						<td></td>
					</tr>
				</tbody>
		    </table>
		</div>	
	</div>	
	
</body>