<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form:form modelAttribute="ePRFFollowUpDocVO" id="ePRFFollowUpDocVO">

<div style="width:640px; margin:0px auto">

	
		
		<c:choose>
		
			<c:when test="${ePRFFollowUpDocVO.FILE_EMAIL_DIRECTOR_DATE == null || ePRFFollowUpDocVO.FILE_EMAIL_DIRECTOR_DATE == ''}">
			
				<div style="border:1px dotted; padding : 0 30 30 30">
					<div class=" clearBoth pT10 ">
			
							<div class="floatLeft width45Perc"><span class="font10pt bold">Email Data to Care Unit</span></div>        
							<div class="floatLeft width45Perc bold"  style="color:black"><span><a href="#" class="mailFacility" style="top:0px;"></a></span></div>	 
			
					</div>
				</div>
				
				
				<div id="emailViewTab" class="height325">
					<input type="hidden" id="emailSubject" value="Request to add a new standardized facility" />
					<div id="standardMessageEmailModal" class="displayNone">
							
							<div id="helpLineEmailWindow">
				                        <div id="emailAccordion">
				                        
				                            <h3 class="emailWindowHeaders"><c:out value="${EMAIL_BUREAU_DIRECTOR_HEADER}" /></h3>
				                            <div> 
				                                <div class="alert alert-info alert-dismissible" role="alert"><br>                   
				                                    <c:out value="${EMAIL_BUREAU_DIRECTOR_WARNING_MSG}" escapeXml="false"/>
				                                </div>
				           
				                                <div class="form-group" id="formGroup">
				                                
				                                		<div id="emailMessageContent" style="width:668px;height:200px;border:1px solid black;margin-top:20px;font-size:11pt;overflow:auto;">
				                                			
				                                			<c:out value="${emailMessageContent}" escapeXml="false"/>
				                                			
				                                		</div>
				                                
				                                
				                                    
				<!--                                         <textarea class="required" style="width:100%" id="emailMessageTxt" title="Email Message Content" name="emailMessageTxt" rows="8" placeholder="Please write the message here"></textarea> -->
				                                        <div>&nbsp;&nbsp;</div>
				                                        <div class="button-row">
				                                            <center>
				                                                <a id="sendHelpline" class="a_demo_save" style="color: #FFFFFF; margin-left: 5px;" tabindex="10" onclick="confirmSendEmailToDirector()"> Send </a>
				                                            </center>    
				                                        </div>        
				                                        <div>&nbsp;&nbsp;</div>
				                                                      
				                                </div> 
				                            </div>
				                        </div> 
				                        
				                            
				                    </div>
					</div>
				</div>
			
			</c:when>
			<c:otherwise>
				<div style="border:1px dotted; padding : 0 30 30 30">
						<div class=" clearBoth pT10 ">
			
							<div class="floatLeft width45Perc"><span class="font10pt bold">Email Data to Care Unit</span></div>        
							<div class="floatLeft width45Perc bold"  style="color:black"><span><c:out value="${ePRFFollowUpDocVO.FILE_EMAIL_DIRECTOR_DATE}" /></span></div>	 
			
						</div>
						
						
						
					
						
							
<!-- 						<div class=" clearBoth pT10 "> -->
<!-- 								<div class="floatLeft width45Perc"> -->
<!-- 								<span class="font10pt bold">Date Approved by AI Director</span> -->
<!-- 								</div> -->
								
								
<%-- 								<c:choose> --%>
<%-- 									<c:when test="${ePRFFollowUpDocVO.FILE_AI_DIRECTOR_SIGN_OFF_DATE == null}"> --%>
<!-- 										<div class="floatLeft width45Perc inputFieldInDialogWindow effectiveDateTimePicker" > -->
<%-- 											<span><form:input class="readonly required standardInputMedText" data-format="MM/dd/yyyy HH:mm:ss PP" tabindex="-1"   path="FILE_AI_DIRECTOR_SIGN_OFF_DATE" dateValidations="noFutureDate" title="Date Approved by AI Director"></form:input></span> --%>
											
<!-- 											<span id="effectiveStartDatePickerIcon" class="add-on"> -->
<!-- 											 <span class="icon-calendar" data-date-icon="icon-calendar" data-time-icon="icon-time" tabindex="4"> -->
<!-- 			      							</span> -->
<!-- 			      							</span> -->
<!-- 										</div> -->
<%-- 									</c:when> --%>
<%-- 									<c:otherwise> --%>
<!-- 										<div class="floatLeft width45Perc bold"  style="color:black"> -->
<%-- 											<span><c:out value="${ePRFFollowUpDocVO.FILE_AI_DIRECTOR_SIGN_OFF_DATE}" /></span> --%>
<!-- 										</div> -->
<%-- 									</c:otherwise> --%>
<%-- 								</c:choose> --%>
								
								
								
								
								
<!-- 								</div> -->
					
						<div class=" clearBoth pT10 ">
								<div class="floatLeft width45Perc">
								<span class="font10pt bold">Date Feedback to Provider</span>
								</div>
								
								
								<c:choose>
									<c:when test="${ePRFFollowUpDocVO.FILE_CLIENT_FEEDBACK_DATE == null}">
										<div class="floatLeft width45Perc inputFieldInDialogWindow effectiveDateTimePicker" >
											<span><form:input class="standardInputMedText dPicker" data-format="MM/dd/yyyy" tabindex="-1"   path="FILE_CLIENT_FEEDBACK_DATE" dateValidations="noFutureDate" title="Date Feedback to Provider"></form:input></span>
											
<!-- 											<span id="effectiveStartDatePickerIcon" class="add-on"> -->
<!-- 											 <span class="icon-calendar" data-date-icon="icon-calendar" data-time-icon="icon-time" tabindex="4"> -->
<!-- 			      							</span> -->
<!-- 			      							</span> -->
			      							
			      							 
										</div>
										
									</c:when>
									<c:otherwise>
										<div class="floatLeft width45Perc bold"  style="color:black">
											<span><c:out value="${ePRFFollowUpDocVO.FILE_CLIENT_FEEDBACK_DATE}" /></span>
										</div>
									</c:otherwise>
								</c:choose>
						</div>
					
					
					</div>
					
					<div id="actionButtons" class="mergeButtons mT40" align="center">                  
				                <span><a class="actionButtons a_demo_search a_demo_continue"  onClick="saveFileFollowUpDates()" style="color: #FFFFFF; margin-left: 5px;"> Save</a></span>  
				                <span><a class="actionButtons a_demo_search a_demo_save"  onClick="generateSummarySpreadsheet()" style="color: #FFFFFF; margin-left: 5px;"> Generate Summary Spreadsheet</a></span>
				                
				                
					</div>
			
			</c:otherwise>
		
		
		
		</c:choose>
	
	
	
	
	
	
	
		

		
	
	</div>
	
	
	
<div id="confirmMessageDialog" >
		<h4 id="confirmMessageContent"></h4>
</div>


</form:form>

 
	
	<script type="text/javascript">
	  $(function() {
	      
	      
	      $('.dPicker').datepicker({
	            changeMonth: true,
	            changeYear: true,
	            //yearRange: '1900:'+currentYear,
	            maxDate: new Date()
	        }).datepicker();
	      
	      
	      
// 	    $('.effectiveDateTimePicker').datepicker({
// 	      language: 'en',
// 	      format: 'MM/dd/yyyy'
// 	      //pick12HourFormat: true
// 	    });
	  });
	</script>