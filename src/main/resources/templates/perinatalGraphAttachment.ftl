<html>
<head>
<style>
 

.tdStyle{border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000000;border-left-style: solid;border-left-width: 1px;border-left-color: #000000;border-right-style: solid;border-right-width: 1px;border-right-color: #000000;border-top-style: solid;border-top-width: 1px;border-top-color: #000000;}
.headerTdStyle{border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000000;border-left-style: solid;border-left-width: 1px;border-left-color: #000000;border-right-style: solid;border-right-width: 1px;border-right-color: #000000;border-top-style: solid;border-top-width: 1px;border-top-color: #000000;background-color:#C0C0C0;}
.fontStyle{font-size: 14.5px;;font-family: 'Arial';}
.supFontStyle{font-size: 11px;;font-family: 'Arial';}
.boldFontStyle{font-weight: bold;font-size: 14.5px;;font-family: 'Arial';}
.boldUnderLineFontStyle{font-weight: bold;font-size: 14.5px;font-family: 'Arial';text-decoration: underline;;}
.vTop{vertical-align:top}
.italicFont{font-style: italic;font-size: 14.5px;font-family: 'Arial'}
.numberHeader{text-align: justify;font-family: 'Arial';}
.alignCenter{text-align:center}
.arialFont{font-size: 14.5px;;font-family: 'Arial';}
p{margin-top: 0em ;margin-bottom: 0em;line-height: 1}
p.lessMargin{margin-top: 0em ;margin-bottom: 0em;line-height: 0.75 !important}
.mT5{margin-top:5px}
.mT8{margin-top:8px}
.mT10{margin-top:10px}
.mT12{margin-top:15em}
.mT15{margin-top:13px}
.mT20{margin-top:20px}
.mT25{margin-top:25px}
 
 /* CHARACTER STYLES */ span.DefaultParagraphFont {display:inline;}
</style>

</head>
<body>
 
 
 <!-- userBodyTop goes here -->

 
 <div class="document">
 

 <p class="alignCenter"><span class="boldFontStyle" >New York State (NYS) Department of Health (DOH) AIDS Institute</span></p>
 
 <p class="alignCenter"><span class="boldFontStyle" >Perinatal HIV and Hepatitis C Prevention Program</span></p>
 
  <p class="alignCenter"><span class="boldFontStyle" >Maternal-Newborn Expedited HIV Testing Regulatory Compliance Report<sup>1</sup></span></p>
 
	
<table class="mT8">
<tbody>
<tr style="width:100%">
<td style="vertical-align:top;width:50px"><p align="left" class="boldFontStyle" >Facility: </p></td>
<td style="vertical-align:top;width:400px"><p align="left" class="boldFontStyle" >${NAME?html!""}</p> 
</td>
<td style="vertical-align:top;width:450px"> <p align="right" class="boldFontStyle" >Time Frame: ${fromDate?html!""} - ${toDate?html!""}</p></td>
</tr>
</tbody>
</table>	

	 

 	
 	
 
	 
	 
	
	
 <table id="docx4j_tbl_0" style="vertical-align: top;border-collapse: collapse;">
 
<tbody>
 <tr >
 	<td rowspan="2" class="headerTdStyle"><p class="alignCenter"><span class="boldFontStyle">Quarter      </span></p></td>
	<td rowspan="2" class="headerTdStyle"><p class="alignCenter"><span class=" boldFontStyle">Number of <br/>Births</span></p></td>
	<td colspan="2" class="headerTdStyle"><p class="alignCenter"><span class=" boldFontStyle">Birth Parents <br/>Tested <br/>Prenatally or <br/>Known HIV Positive </span></p></td>
	<td colspan="2" class="headerTdStyle"><p class="alignCenter"><span class=" boldFontStyle">Recommended <br/>Second (repeat) <br/>Prenatal HIV Tests<br/>Done </span></p></td>
	<td rowspan="2" class="headerTdStyle"><p class="alignCenter"><span class=" boldFontStyle">Number of <br/>Expedited <br/>Tests <br/>Indicated</span></p></td>
	<td rowspan="2" class="headerTdStyle"><p class="alignCenter"><span class=" boldFontStyle">Number of <br/>Expedited <br/>Tests <br/>Done</span></p></td>
	<td colspan="2" class="headerTdStyle"><p class="alignCenter"><span class=" boldFontStyle">Number of Expedited <br/>Tests Done</span></p></td>
	<td colspan="2" class="headerTdStyle"><p class="alignCenter"><span class=" boldFontStyle">Expedited Tests <br/>Not Done or Not <br/>Documented </span></p></td>
	
 </tr>
 <tr >
		<td class="headerTdStyle"><p class="alignCenter"><span class="boldFontStyle">#</span></p></td>
		<td class="headerTdStyle"><p class="alignCenter"><span class="boldFontStyle">%</span></p></td>
		<td class="headerTdStyle"><p class="alignCenter"><span class="boldFontStyle">#</span></p></td>
		<td class="headerTdStyle"><p class="alignCenter"><span class="boldFontStyle">%</span></p></td>
		<td class="headerTdStyle"><p class="alignCenter"><span class="boldFontStyle">Birth Parents</span></p></td>
		<td class="headerTdStyle"><p class="alignCenter"><span class="boldFontStyle">Newborns</span></p></td>
		<td class="headerTdStyle"><p class="alignCenter"><span class="boldFontStyle">#</span></p></td>
		<td class="headerTdStyle"><p class="alignCenter"><span class="boldFontStyle">%</span></p></td>
		
	</tr>
	<#list FACILITY_DATA_TWO_QUARTERS as facilityData>
	<tr >
	
	
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${facilityData['QUARTER_NAME']!""}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${facilityData['TOTAL_SPECIMEN']!""}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${facilityData['TESTED_PRENATALLY']!""}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${facilityData['TESTED_PRENATALLY_PERCENT']!""}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${facilityData['SEC_PRENATAL_DONE']!""}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${facilityData['SEC_PRENATAL_DONE_PERCENT']!""}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${facilityData['EXPEDITED_TESTS_INDICATED']!""}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${facilityData['EXPEDITED_TESTS_DONE']!""}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${facilityData['EXPEDITED_TESTS_DONE_MOM']!""}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${facilityData['EXPEDITED_TESTS_DONE_NEWBORN']!""}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${facilityData['EXPEDITED_TEST_NOT_DONE']!""}</span></p></td>
		<td class="tdStyle"><p class="alignCenter"><span class="boldFontStyle">${facilityData['EXPEDITED_TEST_NOT_DONE_PERCENT']!""}</span></p></td>
		
	</tr>
	</#list>
</tbody>
</table>
<p></p>


<img alt="chart" src="${chartBarImgAsBase64}" style="width:249px;height:142px"></img> 
 
 

<img alt="chart" src="${imgAsBase64}" style="width:249px;height:142px"></img> 
 
 
<p >
<span class="fontStyle">The graphs include data from the table regarding your facility's recent prenatal HIV testing rates and expedited HIV testing (ET) performance. ET in the labor/delivery/nursery settings is a safety net for the prevention of perinatal HIV transmission. It is imperative that your facility utilize continuing quality improvement strategies to identify barriers and immediately address deficiencies in performing and/or documenting ET. It is standard of care that all pregnant people receive HIV testing as early as possible during pregnancy with repeat testing in the third trimester for each respective pregnancy.
</span>
</p>
<p class="lessMargin"></p>
 <table class="mT12">
 <tr>
 <td>
<p><span class="fontStyle">The New York State Perinatal HIV Prevention Clinical Guidelines may be accessed <a href="https://www.hivguidelines.org/collection/hiv-perinatal/">here</a>.</span></p>
<p><span class="fontStyle">The New York State maternal-pediatric expedited HIV testing regulations may be accessed <a href="https://regs.health.ny.gov/content/section-69-13-responsibilities-ceo-hospital">here</a>.</span></p>
<p><span class="fontStyle">If more information is needed regarding this report, contact (518) 486-6806 or <a href="phpp@health.ny.gov">phpp@health.ny.gov</a>.</span></p>
<p class="lessMargin"></p>
<p><span class="supFontStyle"><sup>1</sup>This report is based on the data submitted by your staff on the <a href="https://www.wadsworth.org/programs/newborn/screening/providers/forms">Newborn Screening Blood Collection Form (DOH-1514)</a>.</span></p>
</td>
<td>
<img alt="chart" src="${nysDohChartBarImgAsBase64}" style="width:145px;height:26px;"  ></img> 
</td>
</tr>
</table>

</div><div>
 </div></body>
 </html>