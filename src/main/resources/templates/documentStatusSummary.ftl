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

.table-primary {
    background-color: #d0e0fc !important;
    text-align:center !important;
}
.table-primary {
    --bs-table-bg: #d0e0fc;
    --bs-table-striped-bg: #c6d5ef;
    --bs-table-striped-color: #000;
    --bs-table-active-bg: #bbcae3;
    --bs-table-active-color: #000;
    --bs-table-hover-bg: #c0cfe9;
    --bs-table-hover-color: #000;
    color: #000  !important;
    border-color: #bbcae3 !important;
}

.w-auto{
	width:auto !important;
	margin-top: 20px;
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
.mT20{
	margin-top:20px;
}
    
    </style>
</head>
<body>

  <div class="document">
  
  <h1 style="margin:0px auto">Document Summary Report (${formattedDate})</span></h1>
  
  <p></p>
  <p></p>
 
  	<#list statusSummary as tableName, documentStatusSummary>
  		<#if documentStatusSummary??>
  		<div class="document mT20">
	  		<h1 style="margin:0px auto">${tableName}</h1> <br/>
	  	
	  		<#list documentStatusSummary as createDate, documentSummaryByCreateDateList>
				
				<table id="REPORT_DATA" class="table table-bordered w-auto">
	  				<thead class="thead-dark">
	  					<tr class="table-primary">
	  						<#if tableName?starts_with("STD")>
	  						<th colspan="4" style="background-color: #d0e0fc">
	  						<#else>
	  						<th colspan="3" style="background-color: #d0e0fc">
	  						</#if>
	  							<span>${createDate}</span>
	  						</th>
	  					</tr>
	  				    <tr>
	  				    		<th scope="col" >
	  				    			STATUS
	  				    		</th>
	  				    		<th scope="col" >
	  				    			Description
	  				    		</th>
	  				    		<#if tableName?starts_with("STD")>
	  				    		<th scope="col" >
	  				    			FLUX_MATCH_CD
	  				    		</th>
	  				    		</#if>
	  				    		<th scope="col" >
	  				    			Count
	  				    		</th>
	  				    </tr>
		  			</thead>
		  			<tbody>
		  				<#list documentSummaryByCreateDateList as documentSummaryByCreateDate>
		  					<tr>
		  						<td>${documentSummaryByCreateDate['CURRENT_PROCESS_STATUS']}</td>
		  						<td>${documentSummaryByCreateDate['PROCESSES_STATUS_DESC']}</td>
		  						<#if tableName?starts_with("STD")>
		  							<td>${documentSummaryByCreateDate['FLUX_MATCH_CD']}</td>
		  						</#if>
		  						<td>${documentSummaryByCreateDate['COUNT']}</td>
		  						
		  					</tr>
		  				</#list>
		  			</tbody>
	  			
				</table>
				
				
			</#list>
		</div>
		<div class="mT20">
			<span>----------------------------------------------------------------------------------------------------------------------------------</span><br/>
			<span>----------------------------------------------------------------------------------------------------------------------------------</span>
  		</div>
  		</#if>
  	</#list>
	
  </div>
</body>
</html>


