<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>

<script src="${staticPath}/js/docs/docViewers.js?v=${buildDate}"></script>
<link href="${staticPath}/css/docs/ssdmForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />

<form:form modelAttribute="stdSSDMDocVO" id="stdSSDMDocVO" cssClass="documentDescription documentFormVO">
<form:errors path="*" cssClass="errorblock" element="div" />

<div id="SSDMDoc">
        <div class="headingCls centerDoc">
        Social Security Death Match Document
        </div>
        
        
         <div class="pB30 pT20">
		
					
			<c:if test="${stdSSDMDocVO.PARENT_DOCUMENT_ID != null &&  stdSSDMDocVO.PARENT_DOCUMENT_ID != 0}">

				<div  class="floatLeft width70Perc" id="parentDocumentIdSection">
		            <span class="parentIdSectionLabel bold mL10">Parent DocumentId: </span>
					<span class="parentIdSectionValue "><a href="#" onclick="loadDocumentDlg(${stdSSDMDocVO.PARENT_DOCUMENT_ID}, '${param.documentType}')">${stdSSDMDocVO.PARENT_DOCUMENT_ID}</a></span>
					
		         </div>	
	         </c:if>
	         
			<div  class="floatRight">
	            <span class="mL10 bold">Document ID: </span>
				<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
	         </div>	

		</div>

		
		
    
		<div class="container w100 pB10">
 	         <div style="float:left; width: 40%">
 	            <div>
 	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
 	                 <span class="ml10"><form:input path="STATENO" value="${STATENO}" class="standardInputSmallText readonly" maxlength="10" readonly="true"/> </span>
 	            </div>    
 	          </div>
 	          <div style="float:left; width: 30%">
 	            <div>
 	                <span class=" ml10">	eHARS Import Date: </span>
 	                <fmt:formatDate value="${stdSSDMDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
 			 		<span class="ml10"><form:input path="EHARS_IMPORT_DATE" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" /> </span>
 	           </div>                  
 	         </div>
 	         <div style="float:left; width: 30%; text-align: right"> 	           	            
		       <div> 
  	                <span class="mL10">NYC SHIP Date: </span> 
 			 		<span class="mL10 "><input class="standardinputSmallText ml10 readonly" maxlength="6" /></span> 
  	            </div>  
 	         </div>     
 		</div> 		
 		
 		<jsp:include page="statenoDetails.jsp"></jsp:include>
        
        <!--  Person details -->

        <div class="container borderSSDMDoc">
                <div class="divHeadingCls bGHeading">
                        <span class=" mL2"> Patient details:</span>                                                                      
                </div>   
                <div class="mLRTB pB20">
                        <div class="fieldHolder25">
                                <span class="fLW35"> First Name</span> 
                                <span class="fLW60"> <form:input path="FNAME" id="SSDM_FNAME" class="standardInputSmallText required" title="First Name"  maxlength="20"/> </span>
                                <div class=" v_error SSDM_FNAME "></div>
                        </div>
                        <div class="fieldHolder25 ">
                                <span class="fLW35"> Middle Name</span>
                                <span class="fLW60"> <form:input path="MNAME" class="standardInputSmallText" title="Middle Name" maxlength="20" /> </span>
                        </div>
                        <div class="fieldHolder25 ">
                                <span class="fLW35"> Last Name</span>
                                <span class="fLW60"> <form:input path="LNAME" id="SSDM_LNAME" title="Last Name" class="required standardInputSmallText"  maxlength="30"/> </span>
                                <div class=" v_error SSDM_LNAME "></div>
                        </div>
                        <div class="fieldHolder25 ">
                                <span class="fLW35"> Suffix</span>
                                <span class="fLW60"> <form:input path="SUFFIX" class="standardInputSmallText"  title="Suffix" maxlength="3"/> </span>        
                        </div>               
                </div>
                <div class="clearBoth mLRTB pB20">      
                        <div class="fieldHolder25"> 
                                <span class="fLW35">Date of Birth </span>             
                                <span class="fLW60"> <form:input path="DOB_TEXT" id="DATE_OF_BIRTH" title="Date of Birth" class="standardInputSmallText mask dPicker" datevalidations="completeDate" maxlength="10"/> </span>
                                <div class=" v_error DATE_OF_BIRTH "></div>
                        </div>               
                        <div class="fieldHolder25">
                                <span class="fLW35"> <spring:message code="label.birthyear" /> </span> 
                                <span class="fLW60"> <form:input path="BIRTH_YEAR" class="standardInputSmallText" title="Birth Year" id="birthYear" maxlength="4"/> </span>
                        </div>   
                        <div class="fieldHolder25"> 
                                <span class="fLW35"> SSN</span>
                                <span class="fLW60"> <form:input path="SSN" id="SSDM_SSN" title="SSN" class="mask required standardInputSmallText" maxlength="9"/> </span>
                                <div class=" v_error SSDM_SSN "></div>
                        </div>                                                             
                </div>
                <div class="clearBoth mLRTB">           
                        <div class="fieldHolder25">
                                <span class="fLW35"> <spring:message code="label.deathdate" /> </span> 
                                <span class="fLW60"> <form:input path="DOD_TEXT" id="DATE_OF_DEATH" title="Date of death" class="standardInputSmallText mask dPicker" datevalidations="completeDate" maxlength="10"/> </span>
                                <div class=" v_error DATE_OF_DEATH "></div>
                        </div>
                        <div class="fieldHolder25">
                                <span class="fLW35"> Age at Death</span>
                                <span class="fLW60"> <form:input path="AGE_AT_DEATH" title="Age at Death" class="standardInputSmallText nOnly" maxlength="3" /> </span>
                        </div>                                                               
                        <div class="fieldHolder25">
                                <span class="fLW35"> State Of Death</span> 
                                <span class="fLW60"> <form:input path="PERSON_STATE_AT_DEATH" title="State of Death" class="standardInputSmallText" maxlength="2"/> </span>
                        </div>
                        <div class="fieldHolder25">
                                <span class="fLW35"> Zipcode Of Death</span>
                                <span class="fLW60"> <form:input path="PERSON_ZIP_AT_DEATH" title="Zip code of Death" class="standardInputSmallText nOnly" maxlength="5" /> </span>
                        </div>
                </div>                                                                                                                                                                                     
        </div>
        <div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->        
        <div id="button-row" class="button-row documentButtons">
                <div align="center"> 
                	<c:if  test="${userPermissions['99'] != null && stdSSDMDocVO.STATENO != null && stdSSDMDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}" >	
						<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3 displayNone"  value="Trigger Assignment"  onclick="loadSurvAssignmentPopUp()"/>
					</c:if>
                   <c:choose>
						<c:when  test="${param.hasDeleteFunctionality == true}" >	
								<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
								<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
						</c:when>
						<c:when test="${stdSSDMDocVO.STATENO != null && stdSSDMDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
								<c:if test="${userPermissions['90'] != null}" >
										<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
									</c:if>
									<c:if test="${userPermissions['100'] != null}" >
										<input type="button" id="editDocument"  class="editDocumentBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="confirmEditDocumentFromStateno()" />
									</c:if>
						</c:when>
					</c:choose>

						
						
					<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Save" onclick="saveForm()"/>	 
					<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />
                </div>   
	</div>
</div>
        <input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
</form:form>
