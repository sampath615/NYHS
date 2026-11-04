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
	var isOldAcrf = '${acrfFormVO.rawAcrfDoc.isOldAcrf}';
	var acrfRevison = '${acrfFormVO.rawAcrfDoc.acrfRevison}';
	
	var arvMedications = '';
</script>


<link href="${staticPath}/css/docs/acrfForm.css" rel="stylesheet" type="text/css" />
<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>

<c:choose>
	<c:when test="${param.loadInDialog == 'Y'}">
		<script src="${staticPath}/js/docs/acrfFormInDialog.js"></script>
	</c:when>
	<c:when test="${acrfRevison > 2020}">
		<script src="${staticPath}/js/docs/acrfForm2023.js"></script>
	</c:when>
	<c:otherwise>
		<script src="${staticPath}/js/docs/acrfForm.js"></script>
	</c:otherwise>
</c:choose>
<form:form method="post" modelAttribute="acrfFormVO" id="acrfFormVO" class="documentFormVO">

<div id="savedACRF" class="savedACRF" style="float:right; display: none;">
	<span class="savedACRFTxt"> Saved ACRF(s)</span>
	<span>
		<select id="sAcrf" class="sAcrf">
			<option value="0">Select One</option>
			<option value="1347">1347</option>
			<option value="1358">1358</option>	
			<option value="1359">1359</option>
			<option value="1929">1929</option>
			<option value="1939">1939</option>	
		</select> 
		
	</span>
</div>
<div id="emailViewTab" class="height325">
	<input type="hidden" id="emailSubject" value="Request to add a new standardized facility" />
	<div id="standardMessageCryptModal" class="displayNone">
		<%@ include file="../email/emailWindow.jsp" %>
	</div>
</div>


<div style="clear: both;"></div>

<form:errors path="*" cssClass="errorblock" element="div"></form:errors>



	<div style="padding-left:43%; " class="headingCls">
				<c:choose>
					<c:when test="${param.documentType == 'MMP' || param.documentType == 'A06.34'}">
						MMP
					</c:when>
					<c:when test="${param.documentType == 'ACCURINT_INDIVIDUAL'}">
						Accurint Individual
					</c:when>
					<c:when test="${param.documentType == 'MISSING_DATA' || param.documentType == 'A06.50'}">
						Other database/Other
					</c:when>
					<c:when test="${param.documentType == 'OOS_TESTING' || param.documentType == 'A06'}">
						OOS TESTING
					</c:when>
					<c:when test="${param.documentType == 'RECORD_SEARCH' || param.documentType == 'A06.18'}">
						RECORD SEARCH
					</c:when>
					<c:when test="${param.documentType == 'RIDR_ACRF' || param.documentType == 'A06.29'}">
						RIDR ACRF
					</c:when>
					<c:otherwise>
						ACRF
					</c:otherwise>
				</c:choose>
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
			   <c:if test="${param.documentType != 'ACRF'}">
			   		<li><a href="#nav-section5">Duplicate Review</a></li>
			   </c:if>
		   </ul>
		   
          <jsp:include page="ACRFFormTabs.jsp"></jsp:include>

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
	 		<c:when test="${param.pageName == 'qualAcrf'}">
				<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3 " value="Save" onclick="editClosedACRFForm()"/>
			</c:when>
	 		<c:when test="${acrfFormVO.rawAcrfDoc.MATCHED_STATENO != null && acrfFormVO.rawAcrfDoc.MATCHED_STATENO != '' &&  param.documentType != 'ACRF' && nyehmsStatenoDetails.REASON_DESC == null}">
	 			<c:if test="${userPermissions['100'] != null}" >
					<input type="button" id="editDocument"  class="editDocumentBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="confirmEditDocumentFromStateno()" />
				</c:if>
				
				<c:if test="${userPermissions['90'] != null}">
							<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
				</c:if>
				
				
				
				<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3 " value="Save" onclick="editACRFForm()"/>	 
				<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />	
				
	 		
	 		</c:when>
			<c:when test="${acrfFormVO.rawAcrfDoc.MATCHED_STATENO != null && acrfFormVO.rawAcrfDoc.MATCHED_STATENO != '' &&  param.documentType == 'ACRF' && nyehmsStatenoDetails.REASON_DESC == null && acrfFormVO.rawAcrfDoc.EHARS_IMPORT_DATE != null  && acrfFormVO.rawAcrfDoc.EHARS_IMPORT_DATE != '' 
					&& userPermissions['214'] != null }">
				<input type="button" id="deleteCRFDocReopenAssign" class="deleteBtnclick button rounded blue effect-3"  value="Delete" onclick="confirmDeleteCRFDocAndReopenAssign('ACRF')" />
			</c:when>
			<c:otherwise>
	 			 <c:choose>
				 	<c:when test="${param.actionName == 'ASSOCIATE' }">
				 		<a class="actionButtons a_demo_search a_demo_save" style="color: #FFFFFF;display:inline-block;margin-top:9px" href="javascript:saveAndAssociateACRFForm('')" id="sButton">Save</a>
				 		<a class="enabledColor a_continue fadedCancelButton mL10" style="color: #FFFFFF;display:inline-block;margin-top:9px" href="javascript:cancelAcrfForm()" id="dButton">Cancel</a>
				 	</c:when>
				 	<c:when test="${nyehmsStatenoDetails.REASON_DESC == null }">
				 		<a class="actionButtons a_demo_search a_demo_save" style="color: #FFFFFF;display:inline-block;margin-top:9px" href="javascript:saveAfterValidatingTheForm('')" id="sButton">Save</a>
				 		<a class="enabledColor a_continue fadedCancelButton mL10" style="color: #FFFFFF;display:inline-block;margin-top:9px" href="javascript:discardAcrfFormInAcrfTab()" id="dButton">Delete</a>
				 	</c:when>
				 </c:choose>
	 		
	 		</c:otherwise>
	 	</c:choose>
	 
	</div>
	<form:hidden path="rawAcrfDoc.documentId" id="rawAcrfDoc_documentId" />
	<form:hidden path="rawAcrfDoc.EHARS_IMPORT_DATE" id="EHARS_IMPORT_DATE" />
	<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
</form:form>
<script src="${staticPath}/js/main.js"></script>
