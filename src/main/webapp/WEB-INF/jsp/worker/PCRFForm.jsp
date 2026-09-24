<%@ page import="gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.AcrfFormVO" %>
<%@ page import="gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfLabsVO" %>
<%@ page import="java.util.List" %>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- This jsp belongs to "ACRF Form contains PT Info, PT Hist, Lab, TTH" --%> 

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<script type="text/javascript"> 
	var contextPath="${contextPath}";
	var isOldAcrf = 'N'
	var arvMedications = '';
</script>


<link href="${staticPath}/css/docs/acrfForm.css" rel="stylesheet" type="text/css" />
<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>

<c:choose>
<c:when test="${param.loadInDialog == 'Y'}">
<script src="${staticPath}/js/docs/acrfFormInDialog.js"></script>
</c:when>
<c:otherwise>
<script src="${staticPath}/js/docs/pcrfForm.js"></script>

</c:otherwise>
</c:choose>
<form:form method="post" modelAttribute="acrfFormVO" id="acrfFormVO" class="documentFormVO">


<div id="emailViewTab" class="height325">
	<input type="hidden" id="emailSubject" value="Request to add a new standardized facility" />
	<div id="standardMessageCryptModal" class="displayNone">
		<%@ include file="../email/emailWindow.jsp" %>
	</div>
</div>


<div style="clear: both;"></div>

<form:errors path="*" cssClass="errorblock" element="div"></form:errors>



	<div style="padding-left:43%; " class="headingCls">

						PCRF
		</div>
		
	<div style="clear: both;"></div>
	
	<div id="acrfFormDetailsTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	<div id="sendEmailTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	
	<div id="wrngMsgs"></div>

	<div style="clear: both;"></div>
	<div id="acrfTabs">	
		   <ul>
			   <li><a href="#nav-section1">PT Info</a></li>
			   <li><a href="#nav-section2">PT Hist</a></li>
			   <li><a href="#nav-section3">Lab</a></li>
			   <li><a href="#nav-section4">TTH</a></li>
		   </ul>
		   
		  <div id="nav-section1">
			  <div id="ACRFForm1" style="width:100%; height: 460px; padding:0; overflow-y:scroll;" class="chngFont">
			  		<jsp:include page="PCRFForm1.jsp"></jsp:include>
			  </div>
		  </div>
		  <div id="nav-section2">
			  <div id="ACRFForm2" style="width:100%; height: 460px; padding:0; overflow-y:scroll;" class="chngFont">
					<jsp:include page="PCRFForm2.jsp"></jsp:include>  
			  </div>
		  </div>
		  <div id="nav-section3">
			  <div id="ACRFForm3" style="width:100%; height: 460px; padding:0; overflow-y:scroll;" class="chngFont">
			  
			  		<span class="mL10 hdrFont"> Laboratory Data  (record additional tests and tests not specified below in Comments)(record all dates as mm/dd/yyyy) 
					</span>
					<div class="roundedBorder mL10" style="width:98%; background:#DDDDDD;" >
						<div id="labDataContainer" class="container">
							
							
							<c:choose>
								<c:when test="${acrfRevison < 2020}">
									<jsp:include page="PCRFForm3.jsp"></jsp:include>
								</c:when>
								<c:otherwise>
									<jsp:include page="ACRFImmunossays_23.jsp"></jsp:include>
									<jsp:include page="ACRFDetectionTest_23.jsp"></jsp:include>
									<jsp:include page="ACRFForm3_23.jsp"></jsp:include>
									<jsp:include page="PCRFFormDocTests.jsp"></jsp:include>
								</c:otherwise>
							
							
							</c:choose>
							
							
							
							
								
						</div>
					</div>
			  
			  
					  
			  </div>
		  </div>
		  <div id="nav-section4">
			  <div id="ACRFForm4" style="width:100%; height: 460px; padding:0; overflow-y:scroll;" class="chngFont">
			  		
			  		<c:choose>
						<c:when test="${acrfRevison > 2020}">
							<jsp:include page="PCRFForm4_23.jsp"></jsp:include>
						</c:when>
						<c:otherwise>
							<jsp:include page="PCRFForm4.jsp"></jsp:include>	
						</c:otherwise>
					</c:choose>
					 
			  </div>
		  </div> 
	</div>
	<div id="dialog-confirm" class="displayNone">	
			<div id="dContent"> </div>
		
			<div class="btnDiv">
				<ul id="menu-css">  
					<li><a href="javascript:validationAcrfForm('submitAcrfForm');">Yes</a></li>  
					<li><a href="javascript:closeDialog(this);">No</a></li>  
				</ul>  
			</div>
	</div>	
	
	<div id="PTHistory" class="displayNone">	
			<div id="phContent"> </div>
			
			<div class="btnDiv" style="text-align: right;">
				<ul id="menu-css">  
					<li><a href="#" onclick="javascript:ptClose();">OK</a></li>  
				</ul>  
			</div>
		
	</div>	
	<div class="mT10">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
	 <div class="button-row">
	 	
	 	
	 	
	 	<c:choose>
	 		<c:when test="${acrfFormVO.rawAcrfDoc.MATCHED_STATENO != null && acrfFormVO.rawAcrfDoc.MATCHED_STATENO != '' && (param.documentType == 'PCRF') && acrfFormVO.rawAcrfDoc.EHARS_IMPORT_DATE != null && acrfFormVO.rawAcrfDoc.EHARS_IMPORT_DATE != '' && userPermissions['214'] != null}">
				<input type="button" id="deleteCRFDocReopenAssign" class="deleteBtnclick button rounded blue effect-3"  value="Delete" onclick="confirmDeleteCRFDocAndReopenAssign('PCRF')" />
			</c:when>
	 		<c:otherwise>
	 			 		<a class="actionButtons a_demo_search a_demo_save" style="color: #FFFFFF;display:inline-block;margin-top:9px" href="javascript:saveAfterValidatingTheForm('')" id="sButton">Save</a>
				 		<a class="enabledColor a_continue fadedCancelButton mL10" style="color: #FFFFFF;display:inline-block;margin-top:9px" href="javascript:discardAcrfFormInAcrfTab()" id="dButton">Delete</a>
				 
	 		</c:otherwise>
	 	</c:choose>
	 
	</div>
	<form:hidden path="rawAcrfDoc.documentId" id="rawAcrfDoc_documentId" />
	<form:hidden path="rawAcrfDoc.EHARS_IMPORT_DATE" id="EHARS_IMPORT_DATE" />
	<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
</form:form>
<script src="${staticPath}/js/main.js"></script>