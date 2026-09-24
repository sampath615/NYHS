<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page
	import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties"%>
<%
	pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));
%>

<link href="${staticPath}/css/docs/commonForm.css?v=${buildDate}"
	rel="stylesheet" type="text/css" />
<link
	href="${staticPath}/css/assignments/assignments.css?v=${buildDate}"
	rel="stylesheet" type="text/css" />
<div id="PsMessageAccordions">
	<c:forEach items="${psMessageList}" var="psMessageVOVar"
		varStatus="statusHNT">
		<c:set var="psMessageVO" value="${psMessageVOVar}" scope="request" />
		<h3 style="padding: 1em"
			class="displayNone psMessageAccordion${statusHNT.index}">
			<span class="accordionHeader"
				style="left: 2em; top: 50%; margin-top: -8px; position: absolute;">Message</span>
		</h3>
		<div class="psMessageAccordion container"
			style="background-color: transparent; border: 1px dotted; height: auto">
			<form:form modelAttribute="psMessageVO">
				<div class="sentMessageDetails pB10"
					style="width: 100%; border: 1px dotted gray; min-height: 130px; overflow-y: auto"
					class="pT20 pB20">
					<c:choose>
						<c:when test="${psMessageVO.messageSentVO != null}">

							<div style="padding-left: 43%;" class="headingCls pB10">
								Sent Message</div>

							<%@ include file="returnMessages/sentMessage.jsp"%>
						</c:when>
						<c:otherwise>
							<div
								style="width: 100%; text-align: center; font-weight: bold; margin-top: 80px; margin-bottom: 80px; font-size: 36px;"
								class="noMessageDetails">
								<span>No Sent Message</span>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="returnMessageDetails clearBoth pT10 pB10"
					style="width: 100%; height: auto; border: 1px dotted gray;">

					<c:choose>
						<c:when
							test="${psMessageVO.messageReturnVO != null && psMessageVO.messageReturnVO.CDESSCASEKEY != null}">
							<div style="padding-left: 43%;" class="headingCls pB10">
								Return Message</div>
							<%@ include file="returnMessages/messageReceived.jsp"%>
							<%@ include file="returnMessages/updatePsMessage.jsp"%>
						</c:when>
						<c:when
							test="${psMessageVO.messageReturnVO != null && psMessageVO.messageReturnVO.CDESSCASEKEY == null && psMessageVO.messageReturnVO.updatedDispositionsList != null}">
							<div
								style="width: 100%; text-align: center; font-weight: bold; margin-top: 80px; margin-bottom: 80px; font-size: 36px;">
								<span>No Return Message</span>
							</div>
							<%@ include file="returnMessages/updatePsMessage.jsp"%>
						</c:when>
						<c:otherwise>
							<div
								style="width: 100%; text-align: center; font-weight: bold; margin-top: 80px; margin-bottom: 80px; font-size: 36px;">
								<span>No Return Message</span>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
				<c:if
					test="${psMessageVO.messageSentVO.TRIGGER_MSG_TYPE=='900  MS Incomp' || 
							psMessageVO.messageSentVO.TRIGGER_MSG_TYPE=='Possible AHI' ||
							psMessageVO.messageSentVO.TRIGGER_MSG_TYPE=='PRF PS Request' }">
					<div class="returnMessageDetails clearBoth pT10 pB10"
						style="width: 100%; height: auto; border: 1px dotted gray;">

						<div class="personDetailsDiv" style="height: 165px;">

							<div class="floatLeft"
								id="psMessageCommentsSec_<c:out value='${psMessageVO.DOCUMENT_ID}' />">
								<div style="float: left">
									<span class="assignmentHeaders">Comments History</span>
								</div>
								<div class="clearBoth"></div>

								<div>
									<div id="psMessageCommentsHistory"
										class="floatLeft psMessageComments">

										<c:forEach items="${psMessageVO.messageSentVO.reviewComments}"
											var="reviewCommentsVO" varStatus="commentsCount">
											<p>${reviewCommentsVO.CREATE_DATE}&nbsp;&nbsp;&nbsp; <b>${reviewCommentsVO.CREATE_ID}</b> 
											<span class="psMessageCommentsHistoryText">${reviewCommentsVO.COMMENTS}</span>
											</p>

										</c:forEach>


									</div>

									<c:if test="${psMessageVO.messageSentVO.PROCESS_IND == 'R'}">
										<div id="checkCommentSection" title="commnets" class="assignmentComments floatLeft mL20 mT20">

											<div style="float: left">
												<span class="assignmentHeaders">Comments</span>
											</div>
											<div class="clearBoth"></div>

											<div class="floatLeft">
												<textarea rows="2" cols="71" id="psMessageDetailsComments" maxLength="1000" name= "Comments" title = "Comments"
													class="inputPsMessageDetailsComments noDisabled required"></textarea>
												<div class="v_error psMessageDetailsComments"></div>
											</div>
											<div class="button-row floatLeft">
												<a class="actionButtons a_demo_search a_demo_save"
													style="color: #FFFFFF; display: inline-block; margin-top: 9px"
													onclick="savePSMessageComments(<c:out value='${psMessageVO.DOCUMENT_ID}' />, 'psMessageCommentsSec_<c:out value='${psMessageVO.DOCUMENT_ID}' />')">Save</a>
													
												
											</div>
										</div>

									</c:if>
								</div>

							</div>

						</div>
				
				<c:if
					test="${psMessageVO.messageSentVO.TRIGGER_MSG_TYPE=='900  MS Incomp'}">
						<div class="personDetailsDiv" style="height: 130px;">
						
							<div id = "psLaftMessageCommentsReason_<c:out value='${psMessageVO.DOCUMENT_ID}' />" class="floatLeft">
								
								<c:choose>
								<c:when test="${psMessageVO.messageSentVO.LAFT_COMMENTS == null && psMessageVO.messageSentVO.PROCESS_IND == 'R'}">
										<div class="assignmentComments floatLeft mL20 mT20">

											<div style="float: left">
												<span class="assignmentHeaders">LAFT Comments</span>
											</div>
											<div class="clearBoth"></div>

											<div class="floatLeft">
												<textarea rows="2" cols="71" id="laftMessageDetailsComments"
													maxLength="1000" name="Laft Comments"  title = "Laft Comments"
													class="inputLaftMessageDetailsComments noDisabled required" ></textarea>
											</div>

											<div class="floatLeft mL40 mT10">
												<span class="assignmentHeaders">Reason</span>
											</div>
											<div class="floatLeft mL20 mT10">
												<select id="pslaftFollowupReasons"
													name="pslaftFollowupReasons"
													class="standardinputMedText noDisabled">
													<option value="">-- Select --</option>
													<c:forEach items="${laftReasonsList}" var="reason">
														<option value="${reason.REASON_DESC}">${reason.REASON_DESC}</option>
													</c:forEach>
												</select>
											</div>

											<div class="button-row floatLeft mL50">
												<a class="actionButtons a_demo_search a_demo_save" style="color: #FFFFFF; display: inline-block; margin-top: 9px"
													onClick="saveLAFTMessageComments(<c:out value='${psMessageVO.DOCUMENT_ID}' />)">Save</a>
											</div>
											<!--  need to create own save button  -->
										</div>
									</c:when>
								<c:otherwise>
								<div class="assignmentComments floatLeft mL20 mT20">
	
										<div style="float: left">
											<span class="assignmentHeaders">LAFT Comments</span>
										</div>
										<div class="clearBoth"></div>
	
										<div class="floatLeft">
											<textarea rows="2" cols="71" id="laftMessageDetailsComments" maxLength="1000"
												class="inputLaftMessageDetailsComments" ><c:out value='${psMessageVO.messageSentVO.LAFT_COMMENTS}'/></textarea>
										</div>
	
										<div class="floatLeft mL40 mT10">
											<span class="assignmentHeaders">Reason</span>
										</div>
										<div class="floatLeft mL20 mT10">
											<select id="pslaftFollowupReasons" name="pslaftFollowupReasons" class="standardinputMedText" required>
										     	<option value="${psMessageVO.messageSentVO.LAFT_REASON}" selected><c:out value='${psMessageVO.messageSentVO.LAFT_REASON}' /></option>
											</select>
										</div>
	                                </div>
								  
								
								</c:otherwise>
								</c:choose>
								
								
								
								
								
							</div>
						</div>
					</c:if>

					</div>
					

				</c:if>


			</form:form>
		</div>
	</c:forEach>


</div>
<div id="psReviewActionButtons" class="displayNone">
	<div id="manageDocuments" class="button-row pT20"
		style="text-align: center">
		<a id="processMessages" class="a_disable fadedResetButton"
			tabindex="9" style="color: #FFFFFF;" onclick="processMessages(this, '<c:out value='${param.docId}' />');">Send
			Message to PS</a> <a id="deleteMessages"
			class="a_disable fadedCancelButton"
			style="color: #FFFFFF; margin-left: 5px" tabindex="10"
			onclick="deleteMessages(this, '<c:out value='${param.docId}' />');">
			Reviewed, No PS Message Needed </a>
	</div>
</div>


<script src="${staticPath}/js/docs/PsMessageDoc.js?v=${buildDate}"></script>
<script type="text/javascript">
	
	
	
	if($('body').hasClass('reviewPsMessagesPage') && $('.inputPsMessageDetailsComments').length > 0){
		$('#psReviewActionButtons').show();
		
		$('#psReviewActionButtons').find('#processMessages').addClass('actionButtons a_demo_search a_demo_reset').removeClass('a_disable fadedManageButton');
		$('#psReviewActionButtons').find('#deleteMessages').addClass('actionButtons a_demo_search a_demo_cancel').removeClass('a_disable fadedCancelButton');
	
	}


	
	
	var accordionLength = $('.psMessageAccordion').length; 
	if(accordionLength > 1){
	    $("#PsMessageAccordions").accordion({active : accordionLength - 1});  
	    
	    $('.psMessageAccordion').css('height','auto');
 
 }
</script>