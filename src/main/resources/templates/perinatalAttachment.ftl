<html>
<head>
<style>
 

.tdStyle{border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000000;border-left-style: solid;border-left-width: 1px;border-left-color: #000000;border-right-style: solid;border-right-width: 1px;border-right-color: #000000;border-top-style: solid;border-top-width: 1px;border-top-color: #000000;}
.fontStyle{font-size: 13.75px;;font-family: 'Arial';}
.boldFontStyle{font-weight: bold;font-size: 13.75px;;font-family: 'Arial';}
.boldUnderLineFontStyle{font-weight: bold;font-size: 13.75px;font-family: 'Arial';text-decoration: underline;;}
.vTop{vertical-align:top}
.italicFont{font-style: italic;font-size: 13.75px;;font-family: 'Arial'}
.numberHeader{text-align: justify;font-family: 'Arial';}
.alignCenter{text-align:center}
.arialFont{font-size: 13.75px;;font-family: 'Arial';}
p{margin-top: 0em ;margin-bottom: 0em;line-height: 1.09;}
.mT5{margin-top:5px}
.mT10{margin-top:10px}
.mT15{margin-top:15px}
.mT20{margin-top:20px}
.mT25{margin-top:25px}
 
 /* CHARACTER STYLES */ span.DefaultParagraphFont {display:inline;}
</style>

<script type="text/javascript"><!--function toggleDiv(divid){if(document.getElementById(divid).style.display == 'none'){document.getElementById(divid).style.display = 'block';}else{document.getElementById(divid).style.display = 'none';}}
--></script></head><body>
 
 <!-- userBodyTop goes here -->

 
 <div class="document">
 

 <p style="text-align:left"><span class="boldFontStyle">Attachment ${BIRTH_QTR}A</span></p>
 
 <p class="alignCenter"><span class="boldFontStyle" >New York State Department of Health AIDS Institute</span></p>
 
 <p class="alignCenter"><span class="boldFontStyle" >Maternal-Pediatric HIV Prevention and Care Program</span></p>
 
 <p></p>
	

	
<table >
<tbody>
<tr style="width:100%">
<td style="vertical-align:top;width:50px"><p align="left" class="boldFontStyle" >Facility: </p></td>
<td style="vertical-align:top;width:400px"><p align="left" class="boldFontStyle" >${NAME?html!""}</p> 
</td>
<td style="vertical-align:top;width:450px"> <p align="right" class="boldFontStyle" >Time Frame: ${REPORT_FROM_DATE} - ${REPORT_TO_DATE}</p></td>
</tr>
</tbody>
</table>	

	 

<p></p>
 	
 	
 
	 
	 
	
	
 <table id="docx4j_tbl_0" style="vertical-align: top;border-collapse: collapse;">
 
<tbody>
<tr style="width:100%">
	<td class="tdStyle" style="width:25%"><p class="alignCenter"><span class="arialFont">1</span></p></td>
	<td colspan="2" class="tdStyle"><p class="alignCenter"><span class="arialFont">2</span></p></td>
	<td class="tdStyle"><p class="alignCenter"><span class="arialFont">3</span></p></td>
	<td class="tdStyle"><p class="alignCenter"><span class="arialFont">4</span></p></td>
	<td colspan="2" class="tdStyle"><p class="alignCenter"><span class="arialFont">5</span></p></td>
	<td colspan="2" class="tdStyle"><p class="alignCenter"><span class="arialFont">6</span></p></td>
	<td colspan="2" class="tdStyle"><p class="alignCenter"><span class="arialFont">7</span></p></td>
 </tr>
 <tr >
	<td rowspan="2" class="tdStyle"><p class="alignCenter"><span class="fontStyle"># Births</span></p></td>
	<td colspan="2" class="tdStyle"><p class="alignCenter"><span class="fontStyle">Mothers Tested Prenatally or Known Positive*</span></p></td>
	<td rowspan="2" class="tdStyle"><p class="alignCenter"><span class="fontStyle"># Expedited Tests Indicated</span></p></td>
	<td rowspan="2" class="tdStyle"><p class="alignCenter"><span class="fontStyle"># Total Expedited Tests Done</span></p></td>
	<td colspan="2" class="tdStyle"><p class="alignCenter"><span class="fontStyle"># Expedited Tests Done</span></p></td>
	<td colspan="2" class="tdStyle"><p class="alignCenter"><span class="fontStyle"># Expedited Tests Not Done or Not Documented</span></p></td>
	<td colspan="2" class="tdStyle"><p class="alignCenter"><span class="fontStyle">Second (repeat) Prenatal HIV Tests Done</span></p></td>
 </tr>
 <tr >
		<td class="tdStyle"><p class="alignCenter"><span class="fontStyle">#</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="fontStyle">%</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="fontStyle">On Mothers</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="fontStyle">On Newborns</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="fontStyle">#</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="fontStyle">%</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="fontStyle">#</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="fontStyle">%</span></p></td>
	</tr>
	<tr >
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${TOTAL_SPECIMEN}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${TESTED_PRENATALLY}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${TESTED_PRENATALLY_PERCENT}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${EXPEDITED_TESTS_INDICATED}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${EXPEDITED_TESTS_DONE}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${EXPEDITED_TESTS_DONE_MOM}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${EXPEDITED_TESTS_DONE_NEWBORN}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${EXPEDITED_TEST_NOT_DONE}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${EXPEDITED_TEST_NOT_DONE_PERCENT}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${SEC_PRENATAL_DONE}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${SEC_PRENATAL_DONE_PERCENT}</span></p></td>
	</tr>
</tbody>
</table>


 
 
 
 
 
 
 
 
 
 
 
<p></p>
 
 
 
 
 
 
 <table id="docx4j_tbl_1" style="vertical-align: top;border-collapse: collapse;">
 
 <tbody>
	<tr>
		<td colspan="2" class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">Column / Title</span></p></td>
		<td class="tdStyle">		 <p class="alignCenter"><span class="boldFontStyle">Source / Explanation / Correlating DOH-4068 &amp; DOH-1514 HIV Box Information</span></p></td>
	</tr>
	<tr>
		<td class="tdStyle alignCenter"><span class="arialFont">1</span></td>
		<td class="tdStyle"><p ><span class="fontStyle"># Births</span></p></td>
		<td class="tdStyle"><p ><span class="fontStyle">This number is the total number of newborn specimens accessioned by the NYSDOH this quarter. This may not include all births based on (1) when the Newborn Screening specimen was submitted, and (2) only one infant specimen is counted from multiple births. Repeat specimens are not included.</span></p></td>
	</tr>
	<tr>
		<td class="tdStyle alignCenter"><span class="arialFont">2</span></td>
		<td class="tdStyle"><p ><span class="fontStyle"># Mothers Tested Prenatally or Known Positive</span></p></td>
		<td class="tdStyle"><p ><span class="fontStyle">The total number of mothers that received HIV testing during the current pregnancy or known positive. Documentation must be included in the prenatal record and verified by the birth facility (HIV box "A" or "B" checked on the DOH-4068). Rate is the total number women tested prenatally and known positive (both "A" and "B" Box) divided by the total number of "Births" (column 1) multiplied by 100%.</span></p></td>
	</tr>
	<tr >
		<td class="tdStyle alignCenter"><span class="arialFont">3</span></td>
		<td class="tdStyle"><p ><span class="fontStyle"># Expedited Tests Indicated</span></p></td>
		<td class="tdStyle"><p ><span class="fontStyle">This number is obtained by subtracting the number of women who had documented HIV testing during the current pregnancy (Box "A" on the DOH-4068) or tested HIV positive at any time (Box "B" on the DOH-4068) (column 2) from the total number of "Births" (column 1). </span></p></td>
	</tr>
	<tr >
		<td class="tdStyle alignCenter"><span class="arialFont">4</span></td>
		<td class="tdStyle"><p ><span class="fontStyle"># Total Expedited Tests Done</span></p></td>
		<td class="tdStyle"><p ><span class="fontStyle">This number is obtained by adding the number of expedited HIV tests done on mothers (HIV Box "E" on the DOH-4068) and the number of expedited HIV tests done on newborns (HIV Box "F" on the DOH-4068) (column 5).</span></p></td>
	</tr>
	<tr>
		<td class="tdStyle alignCenter"><span class="arialFont">5</span></td>
		<td class="tdStyle"><p ><span class="fontStyle"># Expedited Tests Done on Moms/Newborns</span></p></td>
		<td class="tdStyle"><p ><span class="fontStyle">The total number of expedited HIV tests done on mothers (HIV Box "E" on the DOH-4068).
		</span></p>
 
			<p ><span class="fontStyle">The total number of expedited HIV tests done on newborns (HIV Box "F" on the DOH-4068).</span></p></td>
	</tr>
	<tr >
		<td class="tdStyle alignCenter"><span class="arialFont">6</span></td>
		<td class="tdStyle"><p ><span class="fontStyle"># Expedited Tests Not Done or Not Documented</span></p></td>
		<td class="tdStyle"><p ><span class="fontStyle">This number is obtained by subtracting the total number of expedited tests done (column 4) from the number of expedited tests indicated (column 3). Rate of missed opportunities is the total number of expedited tests not done/not documented divided by the number of expedited tests indicated multiplied by 100%.</span></p></td>
	</tr>
	<tr>
		<td class="tdStyle alignCenter"><span class="arialFont">7</span></td>
		<td class="tdStyle"><p ><span class="fontStyle">Second Prenatal HIV Tests Done</span></p></td>
		<td class="tdStyle"><p ><span class="fontStyle">This number is the total number of mothers with documentation of a second HIV test during the current pregnancy. Rate is the total number of mothers delivering (column 1) who had a second prenatal HIV test documented at any time during pregnancy.</span></p></td>
	</tr>
</tbody>
</table>
 
 
 <p></p>
 


 	<#if ATTACHMENT_NAME == "Attachment A">
<p><span class="fontStyle">If any cases are listed in column #6 (# Expedited Tests Not Done or Not Documented) in the above chart, please continue to strive for optimal performance by reducing/eliminating missed opportunities. Documentation of prenatal HIV testing should be forwarded to the delivery setting, and if not available, timely expedited HIV testing should be offered to the mother (with her consent) or done on the infant immediately after birth (without mother's consent) so that Antiretroviral (ARV) Prophylaxis, if indicated, may be started as early as possible to prevent perinatal transmission of HIV. </span></p>	
 	<#elseif ATTACHMENT_NAME == "Attachment B">
<p><span class="boldFontStyle">ISSUE: Low prenatal HIV test rate. Monitoring threshold is less than 90%.</span></p>
<p></p>
<p><span class="boldFontStyle">RECOMMENDATION: </span>
	
	<span class="fontStyle" >The percent of prenatal testing among women delivering at your facility (percent includes clinic and private patients) is below 90%. We urge you to take steps to identify barriers and develop an action plan to improve the performance and/or documentation of prenatal HIV testing. The Department recognizes that some hospitals have lower-than-average prenatal HIV testing rates because high numbers of women with little or no prenatal care deliver at their facilities. To confirm that this is a barrier at your hospital, we encourage you to keep a log of women requiring expedited HIV counseling and testing. An analysis of the data therein may reveal strategies to improve your facility's statistics. Besides determining the number of women with late or no prenatal care, the log may also identify prenatal providers who, for whatever reason, do not make HIV results available to your facility. Other barriers may include: lack of prenatal care, lack of prenatal counseling, patient declination of testing and/or failure of the prenatal provider to transfer HIV information with the prenatal record. Counseling messages that highlight the benefits of prenatal HIV testing may increase acceptance of a prenatal HIV test. </span>
	<span class="italicFont" >In addition, medical adherence studies show that patients are more likely to test for HIV when their clinician encourages them to do so.</span>
</p>
 	<#elseif ATTACHMENT_NAME == "Attachment C">
<p><span class="boldFontStyle">ISSUE: When it was indicated, your facility failed to conduct or document prenatal or expedited HIV testing</span></p>
<p></p>
<p><span class="boldFontStyle">RECOMMENDATION: </span>
	
	<span class="fontStyle" >Please reinforce to staff the need to accurately document both prior maternal HIV test status and/or the need for expedited testing. Because expedited HIV testing is a safety net for the prevention of perinatal HIV transmission, the Department expects that your facility will utilize continuing quality improvement strategies to identify barriers and to immediately address deficiencies in conducting and/or documenting the performance of expedited HIV testing in the obstetrical and nursery settings.</span>
</p>
 	<#elseif ATTACHMENT_NAME == "Attachment D">
<p><span class="boldFontStyle">ISSUES: (1) Low prenatal test rate. Monitoring threshold is less than 90%; (2) When it was indicated, your facility failed to conduct or document prenatal or expedited HIV testing.</span></p>
<p></p>
<p><span class="boldFontStyle">(1) Low Prenatal Test Rate</span></p>
<p><span class="boldFontStyle">RECOMMENDATION: </span>
				
				<span class="fontStyle" >The percent of prenatal HIV testing among women delivering at your facility (percent includes clinic and private patients) is below 90%. We urge you to take steps to identify barriers and develop an action plan to improve the performance and/or documentation of prenatal HIV testing. The Department recognizes that some hospitals have lower-than-average prenatal HIV testing rates because high numbers of women with little or no prenatal care deliver at their facilities. To confirm that this is a barrier at your hospital, we encourage you to keep a log of women requiring expedited HIV counseling and testing. An analysis of the data therein may reveal strategies to improve your facility's statistics. Besides determining the number of women with late or no prenatal care, the log may also identify prenatal providers who, for whatever reason, do not make HIV results available to your facility. Other barriers may include: lack of prenatal care, lack of prenatal counseling, patient declination of testing and/or failure of the prenatal provider to transfer HIV information with the prenatal record. Counseling messages that highlight the benefits of prenatal HIV testing may increase acceptance of a prenatal HIV test.</span><span class="italicFont" > In addition, studies show that patients are more likely to test for HIV when their clinician encourages them to do so. </span>
</p>
<p><span class="boldFontStyle">(2) When it was indicated, your facility failed to conduct or document prenatal or expedited HIV testing</span></p>
<p><span class="boldFontStyle">RECOMMENDATION: </span>
				<span class="fontStyle" >Please reinforce to staff the need to accurately document both prior maternal HIV test status and the need for expedited testing. Because expedited HIV testing is a safety net for the prevention of perinatal HIV transmission, the Department expects that your facility will utilize continuing quality improvement strategies to identify barriers and to immediately address deficiencies in performing and/or documenting the performance of expedited HIV testing in the obstetrical setting.</span>
			 </p>
 	<#elseif ATTACHMENT_NAME == "Attachment E">
<p><span class="boldFontStyle">ISSUE: 100% of expedited HIV tests that were indicated were not conducted or documented.</span></p>
<p></p>
<p><span class="boldFontStyle">RECOMMENDATION: </span>
	
	<span class="fontStyle" >
		Please reinforce to staff to accurately document both prior maternal HIV test status and the need for expedited testing. Because expedited HIV testing is a safety net for the prevention of perinatal HIV transmission, the Department expects that your facility will utilize continuing quality improvement strategies to identify barriers and to immediately address deficiencies in conducting and/or documenting the performance of expedited HIV testing in the obstetrical and nursery setting.
 	</span>
</p>
 	<#elseif ATTACHMENT_NAME == "Attachment F">
<p><span class="boldFontStyle">ISSUE: 100% of expedited HIV tests that were indicated were not conducted or documented, and your facility failed to conduct or document either prenatal or expedited HIV testing for a high number of women or infants.
 </span></p>
 
<p></p>
<p><span class="boldFontStyle">RECOMMENDATION: </span>
	
	<span class="fontStyle" >Please reinforce to staff the need to accurately document both prior maternal HIV test status and the need for expedited testing. Because expedited HIV testing is a safety net for the prevention of perinatal HIV transmission, the Department expects that your facility will utilize continuing quality improvement strategies to identify barriers and to immediately address deficiencies in conducting and/or documenting the performance of expedited HIV testing in the obstetrical setting.
 	</span>
</p>
 </#if>
 
<p></p>
<p><span class="boldFontStyle">Note:</span><span class="fontStyle"> If additional information or clarification is needed, contact Lisa Haskin at (518)486-6048 or <a href="phpp@health.ny.gov">phpp@health.ny.gov</a></span></p>
</div><div>
 </div></body>
 </html>