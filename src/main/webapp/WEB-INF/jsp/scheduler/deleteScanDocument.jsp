<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<head>
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
<title>Delete Scan Document</title>
</head>
<body>



	<div id="schedulerTransactionMessage" class="transactionMessage successTransactionMessage"><span class="message"><c:out value="${TRANSACTION_MESSAGE}" /></span></div>
	
	<Style>
		table{
			border: 1px solid black;
			margin:0 auto;
		}
		td,th{
			border: 1px solid black;
		}
		.bg-black{
			background-color:black;
			color:white;
		}
		#manualQuartzJobControl{
			margin-top:50px;
		}
	</Style>
	
	
	
	<div id="manualQuartzJobControl">
	
		<div class="floatRight">
			<span class="mR20 bold"><a href="${contextPath}/web/getAssignmentsTally">Application Home</a></span>
		</div>
		<form action="deleteScanDoc" >
		<table >
			<tbody>
				<tr>
					<th class="bg-black" scope="colgroup" colspan="2">
				</th>
				<tr>
					<th scope="colgroup" colspan="2"> </th>
				</tr>
	            <tr>
	            	<th class="bg-black" scope="colgroup" colspan="2">Delete Scan Document</th>
	            </tr>      
	            
	            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Document Id
		                                </strong>
	                                </td>
	                                <td>
	                                	<input type="text" name="DOCUMENT_ID" id="DOCUMENT_ID" value="">	
	                                </td>
	                         </tr>    
	                         
	                          <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												STATENO
		                                </strong>
	                                </td>
	                                <td>
	                                	<input type="text" name="STATENO" id="STATENO" value="">	
	                                </td>
	                         </tr>
	                         
	                          <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												REPORTED_BY
		                                </strong>
	                                </td>
	                                <td>
	                                	<select id="DELETED_BY" name="DELETED_BY" class="required">
	                                		<option value="" >Select</option>
	                                		<c:forEach items="${USER_IDS}" var="user">
												<option value="${user.WORKER_ID}" label="${user.FULL_NAME}"></option>
											</c:forEach>
	                                	
	                                	</select>	
	                                </td>
	                         </tr>
	                         
	                         
	               <tr >
	                                <td width="350px" scope="row" colspan = "2" align="center">
		                            	<input type="submit"
		                            	 value="submit" onclick="return validateDate();">
	                                </td>
	                                
	                            </tr>
	                                    
	        </tbody>
		</table>
	</form>
		
		
	</div>	
	
	
	
	



<%@ include file="../common/commonScriptsIncludes.html"%>
<script src="${staticPath}/js/scheduler/scheduler.js?v=1"></script>
<script type="text/javascript">

function validateDate(){
	var document_id = document.getElementById('DOCUMENT_ID').value;
	if(!document_id){
		alert("DOCUMENT_ID is required");
		
		return false;
	}
	
	var stateno = document.getElementById('STATENO').value;
	if(!stateno){
		alert("STATENO is required");
		
		return false;
	}
	
	var deletedBy = document.getElementById('DELETED_BY').value;
	if(!deletedBy){
		alert("Reported by is required");
		
		return false;
	}
	
	return true;
}
</script>
</body>

