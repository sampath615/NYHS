<html>
<head>
<style>
 

.tdStyle{border-bottom-style: solid;border-bottom-width: 1px;border-bottom-color: #000000;border-left-style: solid;border-left-width: 1px;border-left-color: #000000;border-right-style: solid;border-right-width: 1px;border-right-color: #000000;border-top-style: solid;border-top-width: 1px;border-top-color: #000000;}
.fontStyle{font-size: 13.75px;;font-family: 'Arial';}
.supFontStyle{font-size: 11px;;font-family: 'Arial';}
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
 

 <p style="text-align:left"><span class="boldFontStyle">Attachment ${BIRTH_QTR}B</span></p>
 
 <p class="alignCenter"><span class="boldFontStyle" >New York State Department of Health AIDS Institute</span></p>
 
 <p class="alignCenter"><span class="boldFontStyle" >Maternal-Pediatric HIV Prevention and Care Program</span></p>
 
 <p class="alignCenter"><span class="boldFontStyle" >Maternal-Newborn Expedited HIV Testing Regulatory Compliance Report<sup>1</sup></span></p>
 
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

	 

 	
 
	 
	 
	
	
 <table id="docx4j_tbl_0" style="vertical-align: top;border-collapse: collapse;">
 
<tbody>
<tr style="width:100%">
	<td class="tdStyle" style="width:10%"><p class="alignCenter"><span class="boldFontStyle">Permanent Facility Identifier (PFI)</span></p></td>
	<td class="tdStyle" style="width:35%"><p class="alignCenter"><span class="boldFontStyle">Facility Name</span></p></td>
	<td class="tdStyle" style="width:10%"><p class="alignCenter"><span class="boldFontStyle">SEX</span></p></td>
	<td class="tdStyle" style="width:15%"><p class="alignCenter"><span class="boldFontStyle">LABID</span></p></td>
	<td class="tdStyle" style="width:15%"><p class="alignCenter"><span class="boldFontStyle">Medical Record Number</span></p></td>
 </tr>
 
 <#list FACILITY_DEMOGRAPHICS_DATA as facilityData>
		<tr >
			<td class="tdStyle" style="width:10%"><p class="alignCenter"><span class="arialFont">${facilityData['PFI']!""}</span></p></td>
			<td class="tdStyle" style="width:35%"><p class="alignCenter"><span class="arialFont">${facilityData['FACILITY_NAME']?html!""}</span></p></td>
			<td class="tdStyle" style="width:10%"><p class="alignCenter"><span class="arialFont">${facilityData['SEX']!""}</span></p></td>
			<td class="tdStyle" style="width:15%"><p class="alignCenter"><span class="arialFont">${facilityData['LABID']!""}</span></p></td>
			<td class="tdStyle" style="width:15%"><p class="alignCenter"><span class="arialFont">${facilityData['MEDRECNO']!""}</span></p></td>
		</tr>
 </#list>  
 
 
 
	
</tbody>
</table>
<p><span class="supFontStyle"><sup>1</sup>This report is based on the data submitted by your staff on the <a href="https://www.wadsworth.org/programs/newborn/screening/providers/forms">Newborn Screening Blood Collection Form (DOH-1514)</a>.</span></p>
<p></p>
<p style="text-align:right">
<img alt="chart" src="${nysDohChartBarImgAsBase64}" style="width:145px;height:26px;"  ></img>
</p>


 
</div><div>
 </div></body>
 </html>