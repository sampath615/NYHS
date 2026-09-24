<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<head>
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
<title>Scheduler Manual</title>
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
			<span class="mR20 bold"><a href="${contextPath}/web/getAssignmentsTally">Application Home</a></span>
			<span class="mR20 bold"><a href="${contextPath}/web/schedulerMetaData">Scheduler Data</a></span>
			<span class="mR20 bold"><a href="${contextPath}/web/experimentHome">Experiment Home</a></span>
		</div>
		<table class="clearBoth">
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
	            	<th class="bg-black" scope="colgroup" colspan="2">Create AllJaro</th>
	            </tr>      
	            
	            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Trigger AllJaro Scheduler
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="startCreatingAllJaro" class="schedulerEvent"  schedulerName="runAllJaroLoaderJob" event="trigger scheduler job"/>	
	                                </td>
	                         </tr>    
	                         
	                          <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Pause AllJaro Scheduler
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="pauseCreatingAllJaro" class="schedulerEvent"  schedulerName="runAllJaroLoaderJob" event="pause scheduler job"/>	
	                                </td>
	                         </tr>
	               <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Resume AllJaro Scheduler
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="resumeCreatingAllJaro" class="schedulerEvent"  schedulerName="runAllJaroLoaderJob" event="resume scheduler job"/>	
	                                </td>
	                                
	                            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Stop AllJaro Scheduler
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="stopCreatingAllJaro" class="schedulerEvent"  schedulerName="runAllJaroLoaderJob" event="stop scheduler job"/>	
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
	            	<th class="bg-black" scope="colgroup" colspan="2" align="center">Standardize Documents<br>and Prep for QS</th>
	            </tr>      
	            
	            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Trigger Standardization<br>and QS Prep
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="startCleanRawRecords" class="schedulerEvent"  schedulerName="runRawRecordsCleanerJob"  event="trigger scheduler job"/>	
	                                </td>
	                         </tr>         
	                         
	                         
	              <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Pause Standardization<br>and QS Prep
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="pauseCleanRawRecords" class="schedulerEvent"  schedulerName="runRawRecordsCleanerJob" event="pause scheduler job"/>	
	                                </td>
	                         </tr>
	               <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Resume Standardization<br>and QS Prep
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="resumeCleanRawRecords" class="schedulerEvent"  schedulerName="runRawRecordsCleanerJob" event="resume scheduler job"/>	
	                                </td>
	                         </tr> 
	                         
	                          <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Stop Standardization<br>and QS Prep
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="stopCleanRawRecords" class="schedulerEvent"  schedulerName="runRawRecordsCleanerJob" event="stop scheduler job"/>	
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
	            	<th class="bg-black" scope="colgroup" colspan="2">Retrieve QS Results and<br>Incorporate into Database</th>
	            </tr>      
	            
	            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Trigger Retrieve<br>and Incorporate
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="startQualityStage" class="schedulerEvent"  schedulerName="runQualityStageLoaderJob"  event="trigger scheduler job"/>	
	                                </td>
	                         </tr>    
	                         
	                          <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Pause Retrieve<br>and Incorporate
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="pauseQualityStage" class="schedulerEvent"  schedulerName="runQualityStageLoaderJob" event="pause scheduler job"/>	
	                                </td>
	                         </tr>
	               <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Resume Retrieve<br>and Incorporate
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="resumeQualityStage" class="schedulerEvent"  schedulerName="runQualityStageLoaderJob" event="resume scheduler job"/>	
	                                </td>
	                         </tr> 
	                         
	                         <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Stop Retrieve<br>and Incorporate
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="stopQualityStage" class="schedulerEvent"  schedulerName="runQualityStageLoaderJob" event="stop scheduler job"/>	
	                                </td>
	                         </tr>        
	        </tbody>
		</table>
		
		</td>
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
	            	<th class="bg-black" scope="colgroup" colspan="2">Retrieve QS Results for Both the Jobs and<br>Incorporate into Database</th>
	            </tr>      
	            
	            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Trigger Retrieve<br>and Incorporate
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="startQualityStageForBothThe" class="schedulerEvent"  schedulerName="runQualityStageMatchResultsLoaderForBothJobs"  event="trigger scheduler job"/>	
	                                </td>
	                         </tr>    
	                         
	                          <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Pause Retrieve<br>and Incorporate
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="pauseQualityStage" class="schedulerEvent"  schedulerName="runQualityStageMatchResultsLoaderForBothJobs" event="pause scheduler job"/>	
	                                </td>
	                         </tr>
	               <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Resume Retrieve<br>and Incorporate
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="resumeQualityStage" class="schedulerEvent"  schedulerName="runQualityStageMatchResultsLoaderForBothJobs" event="resume scheduler job"/>	
	                                </td>
	                                
	                            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Stop Retrieve<br>and Incorporate
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="stopQualityStage" class="schedulerEvent"  schedulerName="runQualityStageMatchResultsLoaderForBothJobs" event="stop scheduler job"/>	
	                                </td>
	                         </tr>        
	        </tbody>
		</table>
		
		
		
		
					<table>
						<tbody>
							<tr>
								<th class="bg-black" scope="colgroup" colspan="2"></th>
							<tr>
								<th scope="colgroup" colspan="2"></th>
								</tr>
								<tr>
									<th class="bg-black" scope="colgroup" colspan="2">
									CCD Loader
									</th>
								</tr>

								<tr>
									<td width="175px" scope="row"><strong> Trigger
											CCD Loader </strong></td>
									<td><img
									src="${staticPath}/images/buttons/submit_button.png"
									id="startCcdLoad"  class="schedulerEvent"  schedulerName="runCCDLoaderJob" event="trigger scheduler job"/></td>
								</tr>
							

								
							<tr>
									<td width="175px" scope="row"><strong> Pause CCD
											Loader </strong></td>
									<td><img
									src="${staticPath}/images/buttons/submit_button.png"
									id="pauseCcdLoad"  class="schedulerEvent"  schedulerName="runCCDLoaderJob" event="pause scheduler job" /></td>
								</tr>
								<tr>
									<td width="175px" scope="row"><strong> Resume CCD
											Loader </strong></td>
									<td><img
									src="${staticPath}/images/buttons/submit_button.png"
									id="resumeCcdLoad"  class="schedulerEvent"  schedulerName="runCCDLoaderJob" event="resume scheduler job"/></td>
								
							<tr>
									<td width="175px" scope="row"><strong> Stop CCD
											Loader </strong></td>
									<td><img
									src="${staticPath}/images/buttons/submit_button.png"
									id="stopCcdLoad"  class="schedulerEvent"  schedulerName="runCCDLoaderJob" event="stop scheduler job"/></td>
								</tr>
							</tbody>
						</table> <!-- PRF Review Table -->
				
		<table >
			<tbody>
				<tr>
					<th class="bg-black" scope="colgroup" colspan="2">
				</th>
				<tr>
					<th scope="colgroup" colspan="2"> </th>
				</tr>
	            <tr>
	            	<th class="bg-black" scope="colgroup" colspan="2">PRF Review</th>
	            </tr>      
	            
	            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Trigger PRF Review Scheduler
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="startPrfReviewStatus" class="schedulerEvent"  schedulerName="runPrfReviewStatusLoaderJob"  event="trigger scheduler job"/>	
	                                </td>
	                         </tr>    
	                         
	                          <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Pause PRF Review Scheduler
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="pausePrfReviewStatus" class="schedulerEvent"  schedulerName="runPrfReviewStatusLoaderJob" event="pause scheduler job"/>	
	                                </td>
	                         </tr>
	               <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Resume PRF Review Scheduler
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="resumePrfReviewStatus" class="schedulerEvent"  schedulerName="runPrfReviewStatusLoaderJob" event="resume scheduler job"/>	
	                                </td>
	                                
	                            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Stop PRF Review Scheduler
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="stopPrfReviewStatus" class="schedulerEvent"  schedulerName="runPrfReviewStatusLoaderJob" event="stop scheduler job"/>	
	                                </td>
	                         </tr>        
	        </tbody>
		</table>
		
		
		<table >
			<tbody>
				<tr>
					<th class="bg-black" scope="colgroup" colspan="2">
				</th>
				<tr>
					<th scope="colgroup" colspan="2"> </th>
				</tr>
	            <tr>
	            	<th class="bg-black" scope="colgroup" colspan="2">Data files reminder</th>
	            </tr>      
	            
	            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Trigger data files reminder
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="startDocumentReminderReviewStatus" class="schedulerEvent"  schedulerName="runDocumentFileReminderLoaderJob"  event="trigger scheduler job"/>	
	                                </td>
	                         </tr>    
	                         
	        </tbody>
		</table>
		
		<table >
			<tbody>
				<tr>
					<th class="bg-black" scope="colgroup" colspan="2">
				</th>
				<tr>
					<th scope="colgroup" colspan="2"> </th>
				</tr>
	            <tr>
	            	<th class="bg-black" scope="colgroup" colspan="2">Inactive users reminder</th>
	            </tr>      
	            
	            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Trigger Inactive users reminder
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="startSendingInactiveReminder" class="schedulerEvent"  schedulerName="runNyehmsLoginReminderLoaderJob"  event="trigger scheduler job"/>	
	                                </td>
	                         </tr>    
	                         
	        </tbody>
		</table>
		
		
		<table >
			<tbody>
				<tr>
					<th class="bg-black" scope="colgroup" colspan="2">
				</th>
				<tr>
					<th scope="colgroup" colspan="2"> </th>
				</tr>
	            <tr>
	            	<th class="bg-black" scope="colgroup" colspan="2">User Training reminder</th>
	            </tr>      
	            
	            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Trigger users training reminder
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="startSendingInactiveReminder" class="schedulerEvent"  schedulerName="runNyehmsTrainingReminderLoaderJob"  event="trigger scheduler job"/>	
	                                </td>
	                         </tr>    
	                         
	        </tbody>
		</table>
		
		
		<table >
			<tbody>
				<tr>
					<th class="bg-black" scope="colgroup" colspan="2">
				</th>
				<tr>
					<th scope="colgroup" colspan="2"> </th>
				</tr>
	            <tr>
	            	<th class="bg-black" scope="colgroup" colspan="2">EHARS Data Loader</th>
	            </tr>      
	            
	            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Trigger EHARS Data Loaderr
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="startEharsDataLoader" class="schedulerEvent"  schedulerName="runEharsDataLoader"  event="trigger scheduler job"/>	
	                                </td>
	                         </tr>    
	                         
	                          
	               
	        </tbody>
		</table>
		
		<table >
			<tbody>
				<tr>
					<th class="bg-black" scope="colgroup" colspan="2">
				</th>
				<tr>
					<th scope="colgroup" colspan="2"> </th>
				</tr>
	            <tr>
	            	<th class="bg-black" scope="colgroup" colspan="2">Weekly Status Loader</th>
	            </tr>      
	            
	            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Weekly Status Loader
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="startWeeklyStatusDataLoader" class="schedulerEvent"  schedulerName="runWeeklyDocumentStatusLoader"  event="trigger scheduler job"/>	
	                                </td>
	                         </tr>    
	                         
	                          
	               
	        </tbody>
		</table>
		
		
		<table >
			<tbody>
				<tr>
					<th class="bg-black" scope="colgroup" colspan="2">
				</th>
				<tr>
					<th scope="colgroup" colspan="2"> </th>
				</tr>
	            <tr>
	            	<th class="bg-black" scope="colgroup" colspan="2">Monthly Status Loader</th>
	            </tr>      
	            
	            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Monthly Status Loader
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="startMonthlyStatusDataLoader" class="schedulerEvent"  schedulerName="runMonthlyDocumentStatusLoader"  event="trigger scheduler job"/>	
	                                </td>
	                         </tr>    
	                         
	                          
	               
	        </tbody>
		</table>
		
		<table >
			<tbody>
				<tr>
					<th class="bg-black" scope="colgroup" colspan="2">
				</th>
				<tr>
					<th scope="colgroup" colspan="2"> </th>
				</tr>
	            <tr>
	            	<th class="bg-black" scope="colgroup" colspan="2">HL7 nightly job</th>
	            </tr>      
	            
	            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												HL7 nightly job
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="startHL7RecordsProcessLoaderJob" class="schedulerEvent"  schedulerName="runHL7RecordsProcessLoaderJob"  event="trigger scheduler job"/>	
	                                </td>
	                         </tr>    
	                         
	             <tr>
	            	<th class="bg-black" scope="colgroup" colspan="2">CCD Documents Job Processor</th>
	            </tr>      
	            
	            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												CCD Documents job
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="startCCDRecordsProcessLoaderJob" class="schedulerEvent"  schedulerName="runCCDLoaderJob"  event="trigger scheduler job"/>	
	                                </td>
	                         </tr>    
	                                       
	               
	        </tbody>
		</table>
		
		<table >
			<tbody>
				<tr>
					<th class="bg-black" scope="colgroup" colspan="2">
				</th>
				<tr>
					<th scope="colgroup" colspan="2"> </th>
				</tr>
	            <tr>
	            	<th class="bg-black" scope="colgroup" colspan="2">HL7 Cursor Scripts To <br> Rematch Against CCD</th>
	            </tr>      
	            
	            <tr >
	                                <td width="175px" scope="row">
		                                <strong>
												Execute HL7 Cursor Scripts
		                                </strong>
	                                </td>
	                                <td>
	                                	<img src="${staticPath}/images/buttons/submit_button.png" id="startHl7CursorManually" onClick='callExecuteHl7Cursors()'/>	
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

