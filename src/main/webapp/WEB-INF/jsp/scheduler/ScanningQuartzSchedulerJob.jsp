<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<head>
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
<title>Scan Scheduler Manual</title>
</head>
<body>

<c:if test="${TRANSACTION_MESSAGE != null}">

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
			<span class="mR20 bold"><a href="${contextPath}/web/schedulerMetaData">Scheduler Data</a></span>
		</div>
		<div class="clearBoth" />
		<table>
		<tr>
		<td>
		<table >
			<tbody>
				<tr>
					<th class="bg-black" scope="colgroup" colspan="2">
				</th>
				<tr>
					<th scope="colgroup" colspan="2"> </th>
				</tr>
	            <tr>
	            	<th class="bg-black" scope="colgroup" colspan="2">Encrypt Documents</th>
	            </tr>      
	            
	            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Trigger Scheduler
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="startEncryptingDocuments" class="schedulerEvent"  schedulerName="runCleanAndencryptDocumentsLoader" event="trigger scheduler job"/>	
	                                </td>
	                         </tr>    
	        </tbody>
		</table>
	
		<table style="margin-top:20px">
			<tbody>
				<tr>
					<th class="bg-black" scope="colgroup" colspan="2">
				</th>
				<tr>
					<th scope="colgroup" colspan="2"> </th>
				</tr>
	            <tr>
	            	<th class="bg-black" scope="colgroup" colspan="2" align="center">Decrypt Scan Documents</th>
	            </tr>      
	            
	            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Trigger Job
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="startDecryptingDocuments" class="schedulerEvent"  schedulerName="runDecryptBackedUpEncryptedDocumentsLoader" event="trigger scheduler job"/>
	                                </td>
	                         </tr>         
	                         
			</tbody>
		</table>
		
		
		
		
		
		
		</td>
		
		</tr>
		</table>
		
	</div>	
	
	
	
	
	
</c:if>



<%@ include file="../common/commonScriptsIncludes.html"%>
<script src="${staticPath}/js/scheduler/scheduler.js?v=1"></script>
</body>

