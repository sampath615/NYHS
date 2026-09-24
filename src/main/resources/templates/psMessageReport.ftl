<html>
<head>

	<style>
	
	.table-bordered td, .table-bordered th {
    border: 1px solid #dee2e6;
}
.table td, .table th {
    padding: .35rem;
    vertical-align: top;
    border-top: 1px solid #dee2e6;
}
th {
    text-align: inherit;
}
*, ::after, ::before {
    box-sizing: border-box;
}
table {
    border-collapse: collapse;
}

.w-auto{
	width:auto !important;
	margin:0px auto;
	margin-top: 10px;
}


.table .thead-dark th {
    color: #fff;
    background-color: #212529;
    border-color: #32383e;
}


.table thead th {
    vertical-align: bottom;
    border-bottom: 2px solid #dee2e6;
        border-bottom-color: rgb(222, 226, 230);
        border-bottom-width: 2px;
}

th {
    border: 1px solid #dee2e6;
        border-top-color: rgb(222, 226, 230);
        border-right-color: rgb(222, 226, 230);
        border-bottom-color: rgb(222, 226, 230);
        border-bottom-style: solid;
        border-bottom-width: 1px;
        border-left-color: rgb(222, 226, 230);
}




:root {
    --blue: #007bff;
    --indigo: #6610f2;
    --purple: #6f42c1;
    --pink: #e83e8c;
    --red: #dc3545;
    --orange: #fd7e14;
    --yellow: #ffc107;
    --green: #28a745;
    --teal: #20c997;
    --cyan: #17a2b8;
    --white: #fff;
    --gray: #6c757d;
    --gray-dark: #343a40;
    --primary: #007bff;
    --secondary: #6c757d;
    --success: #28a745;
    --info: #17a2b8;
    --warning: #ffc107;
    --danger: #dc3545;
    --light: #f8f9fa;
    --dark: #343a40;
    --breakpoint-xs: 0;
    --breakpoint-sm: 576px;
    --breakpoint-md: 768px;
    --breakpoint-lg: 992px;
    --breakpoint-xl: 1200px;
    --font-family-sans-serif: -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol";
    --font-family-monospace: SFMono-Regular,Menlo,Monaco,Consolas,"Liberation Mono","Courier New",monospace;
}
html {
    font-family: sans-serif;
    line-height: 1;
    -webkit-text-size-adjust: 100%;
}



.table-bordered {
    border: 1px solid #dee2e6;
}
.table {
    width: 100%;
    max-width: 100%;
    margin-bottom: 1rem;
    background-color: transparent;
}
    
    </style>
</head>
<body>

  <div class="document">
  
  
  
  		<table id="REPORT_DATA" class="table table-bordered w-auto">
  				<thead class="thead-dark">
  				
  				    <tr>
  				    		<th scope="col" >
  				    			County Name
  				    		</th>
  				    		
  				    		<#list metricTotalMap as metricName, metricCount>
  				    			<th scope="col" >
					  				${metricName}
					  			</th>
  				    		</#list>
  				    		
  				    	
  				    		
  				    		<th scope="col" >
  				    			Total
  				    		</th>
  				    		
  				    </tr>
  			</thead>
  			<tbody>
  				    		
							<#list countyList as countyName>
								<tr>
						  			<th scope="row">
						  				${countyName} 
						  			</th>
						  			
						  			
						  			<#if weekTotalByCountyMap[countyName]??>
						  				
						  				<#assign countyMetricMap = weekTotalByCountyMap[countyName]>
						  				
			  							<#list metricTotalMap as metricName, metricCount>
	  				    				
	  				    				
	  				    					<td >
	  				    						
	  				    						${(countyMetricMap[metricName])!"0"}
	  				    						
	  				    					
	  				    					 </td>
	  				    				
	  				    				</#list>
	  				    				
	  				    			
	  				    				
	  				    				
	  				    				<td >
	  				    					${(countyTotalMap[countyName])!"0"}
	  				    				</td>
			  							
			  								 			  	
									<#else>
										<#list metricTotalMap as metricName, metricCount>
	  				    					<td>0</td>
	  				    				</#list>	
	  				    				
	  				    		
	  				    								
	  				    				<td>0</td>
									</#if>
								
					  			</tr>
					  			 
					  			
					  				
					  			
					  			
					  		</#list>
				  	</tr>
				</tbody>
		</table>	
  </div>
</body>
</html>


