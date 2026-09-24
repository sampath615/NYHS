<html>
<head>
  <style>
  <!--
/*@media print { */@page { size: A4; margin-left: 20px; margin-right:20px;margin-top:60px;margin-bottom:40px; @top-center {content: element(header) } @bottom-center {content: element(footer) } }

td{border-bottom-style: solid;border-bottom-width: 0.8px;border-bottom-color: #000000;border-left-style: solid;border-left-width: 0.8px;border-left-color: #000000;border-right-style: solid;border-right-width: 0.8px;border-right-color: #000000;border-top-style: solid;border-top-width: 0.8px;border-top-color: #000000;}
.fontStyle{font-size: 9.0pt;;font-family: 'Times New Roman';}
.boldFontStyle{font-weight: bold;font-size: 9.0pt;;font-family: 'Times New Roman';}
.boldUnderLineFontStyle{font-weight: bold;font-size: 9.0pt;;font-family: 'Times New Roman';text-decoration: underline;;}

.italicFont{font-style: italic;font-size: 9.0pt;;font-family: 'Times New Roman'}
.numberHeader{text-align: justify;font-family: 'Arial Unicode MS';}
.alignCenter{text-align:center}
.valignCenter{vertical-align:middle}
.alignLeft{text-align:left}
.alignRight{text-align:right}
.arialFont{font-size: 9.0pt;;font-family: 'Arial Unicode MS';}
.testTypeColumnsize{width:180px}
.dataColumnsize{width:100px}
p{margin-top: 0.5em ;margin-bottom: 0.5em ;
 }
.mT5{margin-top:5px}
.mT10{margin-top:10px}
 
 /* CHARACTER STYLES */ span.DefaultParagraphFont {display:inline;}
--></style>
</head><body>

  <div class="document">
  		
  		
  		<#list dataSetMap as dataSetIndex, testTypeMap>
  				
  				 <#if dataSetIndex == "4">
				    <#break>
				  </#if>
  				
  				<#if dataSetweeklyCount[dataSetIndex]??>
  			
  				<#assign weeklyCountMap = dataSetweeklyCount[dataSetIndex]>
  			
  				<table id="docx4j_tbl_0" style="vertical-align: top;border-collapse: collapse;">
  				<thead>
  				
  				    <tr>
  				    	<td class="boldFontStyle alignCenter valignCenter" colspan="${(weeklyCountMap?size)+2}" style="width:${((weeklyCountMap?size)*100)+280}px">
  				    		${headerNotes[dataSetIndex]}
  				    	</td>
  				    
  				    </tr>	
  				    <tr style="width:100%">
  						<td class=" boldFontStyle alignCenter valignCenter testTypeColumnsize" >Test type</td>
  						
  						<#list dataSetweeklyCount[dataSetIndex] as weekName, weekData>
						    <td  class="boldFontStyle alignCenter valignCenter dataColumnsize" >${weekName}</td>
						</#list>  
  						
						<td class=" boldFontStyle alignCenter valignCenter dataColumnsize"  >Total</td>
  					</tr>
  				</thead>
  				
  				
  				<tbody>
  					<#list testTypeMap as testType, testTypeData>

  						<tr>
  							<#assign countByTestType = 0>
  							<td class=" alignLeft boldFontStyle" >${testType}</td>
  							<#list dataSetweeklyCount[dataSetIndex] as weekName, weekData>
  								<#assign testTypeCountMapByDate = testTypeData[weekName]>
  							
  								<#assign countByTestType = countByTestType+testTypeCountMapByDate['COUNT']>
							    <td class=" alignRight fontStyle" >
		  							${testTypeCountMapByDate['COUNT']}
		  						</td>
							</#list>  
  							
							<td class=" alignRight fontStyle" >${countByTestType}</td> 
  						</tr>
  					</#list>
  							
  							
		  			<tr>
  						<td class=" alignLeft boldFontStyle">Total</td>
  						<#assign totalCount = 0>
  						<#list dataSetweeklyCount[dataSetIndex] as weekName, weekData>
  							<#assign totalCount = totalCount+weekData>
						    <td class=" alignRight fontStyle" >${weekData}</td>
						</#list>  
  					
						<td class=" alignRight fontStyle" >${totalCount}</td>
										
  					</tr>
  				</tbody>
  				</table>
	  			<p class="fontStyle">${footerNote}</p>
	  			<br/><br/>			
  				</#if>
  		</#list>
  		
  		<#if dataSetweeklyCount["4"]??>
  		<table id="docx4j_tbl_0" style="vertical-align: top;border-collapse: collapse;">
  				<thead>
  				
  				    <tr>
  				    	<td class="boldFontStyle alignCenter valignCenter" colspan="7" style="width:780px">
  				    		${headerNotes["4"]}
  				    	</td>
  				    
  				    </tr>	
  				    <tr style="width:100%">
  				    	<td rowspan="2" class=" boldFontStyle alignCenter valignCenter testTypeColumnsize" >Test type</td>
  						
  						<#list dataSetweeklyCount["4"] as weekName, weekData>
  							<#assign dataSetWeekName4 = weekName>
						    <td  colspan="2" class="boldFontStyle alignCenter valignCenter dataColumnsize" >${weekName}</td>
						</#list> 
						<#list dataSetweeklyCount["5"] as weekName, weekData>
							<#assign dataSetWeekName5 = weekName>
						    <td  colspan="2" class="boldFontStyle alignCenter valignCenter dataColumnsize" >${weekName}</td>
						</#list>
						<#list dataSetweeklyCount["6"] as weekName, weekData>
						<#assign dataSetWeekName6 = weekName>
						    <td  colspan="2" class="boldFontStyle alignCenter valignCenter dataColumnsize" >${weekName}</td>
						</#list> 
						
  				    </tr>
  				    <tr style="width:100%">
  				    	<td  class="boldFontStyle alignCenter valignCenter dataColumnsize" >Frequency</td>
  				    	<td  class="boldFontStyle alignCenter valignCenter dataColumnsize" >Percentage</td>
  				    	<td  class="boldFontStyle alignCenter valignCenter dataColumnsize" >Frequency</td>
  				    	<td  class="boldFontStyle alignCenter valignCenter dataColumnsize" >Percentage</td>
  				    	<td  class="boldFontStyle alignCenter valignCenter dataColumnsize" >Frequency</td>
  				    	<td  class="boldFontStyle alignCenter valignCenter dataColumnsize" >Percentage</td>
  				    </tr>
  				    
  				</thead>
  				<tbody>
  						<#assign testTypeMap4 = dataSetMap["4"]>
  						<#assign testTypeMap5 = dataSetMap["5"]>
  						<#assign testTypeMap6 = dataSetMap["6"]>
  						<#assign totalCountDataSet4 = 0 >
  						<#assign totalCountDataSet5 = 0 >
  						<#assign totalCountDataSet6 = 0 >
  						<#list testTypeMap4 as testType, testTypeData>
  						<tr>
  							<td class=" alignLeft boldFontStyle" >${testType}</td>
  							<#assign totalCountDataSet4 = totalCountDataSet4+testTypeMap4[testType][dataSetWeekName4]['COUNT']>
  							<#assign totalCountDataSet5 = totalCountDataSet5+testTypeMap5[testType][dataSetWeekName5]['COUNT']>
  							<#assign totalCountDataSet6 = totalCountDataSet6+testTypeMap6[testType][dataSetWeekName6]['COUNT']>
  							
  							
  							
  							<td class=" alignRight fontStyle">${testTypeMap4[testType][dataSetWeekName4]['COUNT']}</td>
  							<td class=" alignRight fontStyle">${testTypeMap4[testType][dataSetWeekName4]['PERCENTAGE']}</td>
  							<td class=" alignRight fontStyle">${testTypeMap5[testType][dataSetWeekName5]['COUNT']}</td>
  							<td class=" alignRight fontStyle">${testTypeMap5[testType][dataSetWeekName5]['PERCENTAGE']}</td>
  							<td class=" alignRight fontStyle">${testTypeMap6[testType][dataSetWeekName6]['COUNT']}</td>
  							<td class=" alignRight fontStyle">${testTypeMap6[testType][dataSetWeekName6]['PERCENTAGE']}</td>
  						</tr>
  						</#list> 
  						<tr>
  							<td class=" alignLeft boldFontStyle" >Total</td>
  							<td class=" alignRight fontStyle">${totalCountDataSet4}</td>
  							<td class=" alignRight fontStyle">100</td>
  							<td class=" alignRight fontStyle">${totalCountDataSet5}</td>
  							<td class=" alignRight fontStyle">100</td>
  							<td class=" alignRight fontStyle">${totalCountDataSet6}</td>
  							<td class=" alignRight fontStyle">100</td>
  						</tr>
  				</tbody>
  		</table>
  			
  				
  		</#if>
	  			<p class="fontStyle">${footerNote}</p>
	  			<br/>
  						
  			
		<span class="fontStyle">${formattedDate} - BHAE</span>  		
  		  		
  </div>
  <div>
  </div>
</body>
</html>


