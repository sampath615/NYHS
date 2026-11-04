<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${staticPath}/js/docs/trkAliasDoc.js?v=${buildDate}"></script>

<form:form modelAttribute="stdtrkAliasDocVO" id="stdtrkAliasDocVO" cssClass="documentDescription documentFormVO" >		

	<div id="trkAliasDocVO" style="background-color: transparent">
		
		<div class="headingCls alignCenter marginBottom30">
				TRACKING ALIAS DOCUMENT
		</div>
		
		<div style="padding-bottom:10px; text-align: right">
            <span class="mL10">Document ID: </span>
			<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
        </div>
		<div class="container w100 pB10">
 	         <div style="float:left; width: 40%">
 	            <div>
 	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
 	                 <span class="ml10"><form:input path="STATENO" class="standardInputSmallText" maxlength="10" readonly="true"/> </span>
 	            </div>    
 	          </div>
 	          <div style="float:left; width: 30%">
 	            <div>
 	                <span class=" ml10">	Created By: </span>
 	                <span class="ml10"><form:input path="CREATEID" class="standardInputSmallText ml10" readonly="true"/> </span>
 	           </div>                  
 	         </div>
 	         <div style="float:left; width: 30%; text-align: right">
 	         
 	         	<div>
 	                <span class="mL10">Created Date: </span>
 	                <fmt:formatDate value="${stdtrkAliasDocVO.CREATEDT}"  type="date" pattern="MM/dd/yyyy" var="theFormattedCREATEDT" />
 	                <span class="ml10 "><form:input path="CREATEDT" class="standardInputSmallText" maxlength="10" value="${theFormattedCREATEDT}" readonly="true"/> </span>
 	            </div> 
 	         </div>     
 		</div>	
 		
 		<jsp:include page="statenoDetails.jsp"></jsp:include>
 		

		<!--  Person details -->
		
		<div class="container" style="border:1px dotted; width:100%; padding-bottom:5px;">	 
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Person Details</span>
			</div>
			<div class="pL10 mLRTB pB20 mT10">
				<div style="float:left">
					<span class=""> First Name</span> 
					<span class="mL20"> <form:input path="FNAME" class="standardInputSmallText readonly" maxlength="20"/> </span>
				</div>
				<div style="float:left">
					<span class="mL20"> Middle Name</span>
					<span class="mL20"> <form:input path="MNAME" class="standardInputSmallText readonly" maxlength="25"/>  </span>
				</div>
				<div style="float:left">
					<span class="mL20"> Last Name</span>
					<span class="mL20"> <form:input path="LNAME" id="lName" title="Last Name" class="required standardInputSmallText readonly" maxlength="25"/>  </span>
				</div>
				<div style="float:left">
					<span class="mL20"> Suffix</span>
					<span class="mL20"><form:input path="SUFFIX" class="standardInputSmallText readonly" maxlength="5"/></span>	
				</div>	
			</div>
			<div class="pL10 mR5 mT10" >
								
				<div style="float:left;"> 
					<span class=""> Date of Birth </span>
					<fmt:formatDate value="${stdtrkAliasDocVO.DOB}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
					<span class="ml10"> <form:input path="DOB" value="${theFormattedDate}" class="standardInputMedText readonly"  maxlength="10"/> </span>
				</div>		
				<div style="float:left;"> 
					<span class="mL20"> SSN </span>
					<span class="ml10"> <form:input  path="SSN" id="TRK_ALIAS_SSN" class=" standardInputMedText"  maxlength="9" title="SSN"/> </span>
				</div>			
						
			</div>
		</div>

		 <div class="ClearBoth"></div>	
			<div  class="b1 pB10">
 					<span class="mL30 ">Comments </span><br/>
 					<div class="mL30">
					<span>  <form:textarea title="COMMENTS" maxlength="1000" cols="165" rows="8" path="COMMENTS" style="width:65%; height:67px; border:1px solid gray;" class="roundedBorder"></form:textarea></span>

				</div>
		
		</div>				
			
		<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->
		<div id="button-row" class="button-row documentButtons" align="center">
			<c:choose>
				<c:when test="${stdtrkAliasDocVO.STATENO != null && stdtrkAliasDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
						<c:if test="${userPermissions['134'] != null}" >
							<input type="button" id="editDocument"  class="editDocumentBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="confirmEditDocumentFromStateno()" />
						</c:if>
				</c:when>
			</c:choose>
			<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Save" onclick="saveForm()"/>	 
			<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />
		</div>
				
		</div>	
</form:form>															
