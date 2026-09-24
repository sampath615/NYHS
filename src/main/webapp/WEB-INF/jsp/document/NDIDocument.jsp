<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>

<link href="${staticPath}/css/docs/ndiForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />

<script src="${staticPath}/js/docs/docViewers.js?v=${buildDate}"></script>

<form:form modelAttribute="stdNDIDocVO" id="stdNDIDocVO" cssClass="documentFormVO documentDescription">
<form:errors path="*" cssClass="errorblock" element="div" />

<div id="NDIDoc">
        <div class="headingCls centerDoc">
                NDI Document
        </div>
        
        
          <div class="pB30 pT20">

			
			<c:if test="${stdNDIDocVO.PARENT_DOCUMENT_ID != null &&  stdNDIDocVO.PARENT_DOCUMENT_ID != 0}">

				<div  class="floatLeft width70Perc" id="parentDocumentIdSection">
		            <span class="parentIdSectionLabel bold mL10">Parent DocumentId: </span>
					<span class="parentIdSectionValue"><a href="#" onclick="loadDocumentDlg(${stdNDIDocVO.PARENT_DOCUMENT_ID}, '${param.documentType}')">${stdNDIDocVO.PARENT_DOCUMENT_ID}</a></span>
					
		         </div>	
	         </c:if>
	         
			<div  class="floatRight">
	            <span class="mL10 bold">Document ID: </span>
				<span class=""><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
	         </div>	

		</div>

		<div class="container w100 pB10">
 	         <div style="float:left; width: 40%">
 	            <div>
 	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
 	                 <span class="ml10"><form:input path="STATENO"  class="standardInputSmallText readonly" maxlength="10" readonly="true"/> </span>
 	            </div>    
 	          </div>
 	          <div style="float:left; width: 30%">
 	            <div>
 	                <span class=" ml10">	eHARS Import Date: </span>
 			 		<span class="ml10"><form:input path="EHARS_IMPORT_DATE" class="standardInputSmallText ml10 readonly" readonly="true"/> </span>
 	           </div>                  
 	         </div>
 	         <div style="float:left; width: 30%; text-align: right">
 	         	<div>
 	                <span class="mL10">NYC SHIP Date: </span>
 	                <fmt:formatDate value="${stdNDIDocVO.NYC_SHIP_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedNYC_SHIP_DATE_TEXT" ></fmt:formatDate>
 			 		<span class="mL10 "><form:input path="NYC_SHIP_DATE" value="${theFormattedNYC_SHIP_DATE_TEXT}" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
 	            </div> 
 	         </div>     
 		</div>
 		
 		<jsp:include page="statenoDetails.jsp"></jsp:include>

        <!--  Patient details -->
        <div class="container borderNDIDoc">
                <div class="divHeadingCls bGHeading">
                        <span class=" mL2"> BHAE Data:</span>                                                                      
                </div>   
                <div class="mLRTB">
                        <div class="fieldHolder25">
                                <span class=""> First Name</span> 
                                <span class="pL10"> <form:input path="FNAME" id="NDI_FNAME" title="First Name" class="standardInputSmallText required" maxlength="20"/> </span>
                                <div class=" v_error NDI_FNAME "></div>
                        </div>
                        <div class="fieldHolder25">
                                <span class="pL30"> Middle Name</span>
                                <span class="pL10"> <form:input path="MNAME" class="standardInputSmallText" title="Middle Name" maxlength="25"/> </span>
                        </div>
                        <div class="fieldHolder25">
                                <span class="pL30"> Last Name</span> 
                                <span class="pL10"> <form:input path="LNAME" id="NDI_LNAME" class="required standardInputSmallText" title="Last Name" maxlength="25"/> </span>
                                <div class="pL30 v_error NDI_LNAME "></div>
                        </div>
                        <div class="fieldHolder25">
                                <span class="pL30"> SSN</span>
                                <span class="pL10"> <form:input path="SSN" id="NDI_SSN" class="standardInputSmallText" title="SSN" maxlength="9"/> </span>
                                <div class="pL30 v_error NDI_SSN "></div>
                        </div>   
                </div>
                <div class="clearBoth pB20 pT20 pL10">
                         <div class="fieldHolder25">
                                <span class=""> Date of Birth</span>
                                <span class="pL10"> <form:input path="DOB_TEXT" id="DATE_OF_BIRTH" class="standardInputSmallText mask dPicker" datevalidations="completeDate" title="Date of Birth" maxlength="10"/> </span>
                                <div class=" v_error DATE_OF_BIRTH "></div>
                        </div>
                        <div class="fieldHolder25">
                                <span class="pL30"> Date of Death</span> 
                                <span class="pL10"> 
	                                <form:input path="DOD_TEXT" id="DATE_OF_DEATH" class="standardInputSmallText mask dPicker" datevalidations="completeDate" title="Date of Death" maxlength="10"/>
                                </span>
                                <div class=" v_error DATE_OF_DEATH "></div>
                                
                        </div> 
                        <div class="fieldHolder30">
                                <span class="pL30"> State of Residence</span>
                                <span class="pL10"> <form:input path="STATE_RES" class="standardInputSmallText" maxlength="2" title="State of Residence"/> </span>
                        </div>
                        
                </div>
                <div class="clearBoth pB20 pT20 pL10">
                        <div class="fieldHolder25">
                                <span class=""> Gender</span> 
                                <span class="pL10"> 
                                        <span> <form:radiobutton path="BIRTH_SEX" value="M" /> Male </span>
                                        <span> <form:radiobutton path="BIRTH_SEX" value="F" /> Female </span>
                                </span>
                        </div>
                </div>  
        </div>   


        <!-- NDI details -->
        <div class="container borderNDIDoc">
                <div class="divHeadingCls bGHeading">
                        <span class=" mL2"> National Death Index:</span>                                                                      
                </div>
                <div class="mLRTB pB10">    
                        <div class="fieldHolder25">
                                <span class="fLW35"> Received Date</span>
                                <fmt:formatDate value="${stdNDIDocVO.RECEIVED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedrecvDt" />
                                <span class="fLW60"> <form:input path="RECEIVED_DATE" value="${theFormattedrecvDt}" class="standardInputSmallText readonly" title="Received Date"/> </span>
                        </div>
                        <div class="fieldHolder25">
                                <span class="fLW35"> Unique No</span> 
                                <span class="fLW60"> <form:input path="UNIQ_NO" class=" standardInputSmallText readonly" title="Unique No" maxlength="24"/> </span>
                        </div> 
                        <div class="fieldHolder25">
                                <span class="fLW35"> File Category</span> 
                                <span class="fLW60"> 
                                        <span> <form:radiobutton path="NDIFILE_CAT" value="U"  class="readonly"/> Unknown </span>
                                        <span> <form:radiobutton path="NDIFILE_CAT" value="K" class="readonly"/> Known </span>
                                </span>
                        </div> 
                </div>    
                <div class="clearBoth pB20 pT20 pL10">           
                        <div class="fieldHolder25">
                                <span class="fLW35"> Date of Death</span> 
                                <span class="fLW60"> <form:input path="NDI_DOD" id="NDI_NDI_DOD" class="standardInputSmallText mask dPicker" datevalidations="completeDate" title="Date of Death" maxlength="10"/> </span>
                                
                                <div class=" v_error NDI_NDI_DOD "></div>
                        </div> 
                        <div class="fieldHolder25">
                                <span class="fLW35"> Death Certificate</span>
                                <span class="fLW60"> <form:input path="DEATH_CERT" class="standardInputSmallText" title="Death Certificate" maxlength="6"/> </span>
                        </div>        
                        <div class="fieldHolder25">
                                <span class="fLW35"> State of Death</span> 
                                <span class="fLW60"> <form:input path="STATE_DEATH" class="standardInputSmallText" title="State of Death" maxlength="2"/> </span>
                        </div>                                                              
                        
                </div> 
        </div>
                
        <!--  COD details -->
        <div class="container borderNDIDoc">
                <div class="divHeadingCls bGHeading">
                        <span class=" mL2"> Nature of Injury/ Cause of Death:</span>                                                                      
                </div>
                <div class="mLRTB pB10">
                        <div class="fieldHolder25">
                                <span class="fLW35"> HIV ICD codes</span>
                                <span class="fLW60"> 
                                        <span> <form:radiobutton path="HIVCAUSE" value="Y" /> Yes </span>
                                        <span> <form:radiobutton path="HIVCAUSE" value="N" /> No </span>
                                </span>
                        </div>
                        <div class="fieldHolder25">
                                <span class="fLW35"> ICD Code</span> 
                                <span class="fLW60"> <form:input path="ICD_CODE" class="standardInputSmallText" maxlength="4" title="ICD Code"/> </span>
                        </div>
                </div>        
                <div id="personNDIDetailsLeft">
                        <div class="fieldHolder30">
                                <span class="fLW60"> Nature of Injury 1</span> 
                                <span class="fLW35"> <form:checkbox path="NIF1" value='1' /> </span>
                        </div>
                        <div class="fieldHolder70">
                                <span class="fLW35"> Cause of Death 1</span> 
                                <span class="fLW60"> <form:input path="COD1" class="standardInputSmallText" title="Cause of Death" maxlength="4"/> </span>
                        </div> 
                        <br><br>
                        <div class="fieldHolder30">
                                <span class="fLW60"> Nature of Injury 2</span> 
                                <span class="fLW35"> <form:checkbox path="NIF2" value='1' /> </span>
                        </div>
                        <div class="fieldHolder70">
                                <span class="fLW35"> Cause of Death 2</span> 
                                <span class="fLW60"> <form:input path="COD2" class="standardInputSmallText" title="Cause of Death" maxlength="4"/> </span>
                        </div>
                        <br><br>
                        <div class="fieldHolder30">
                                <span class="fLW60"> Nature of Injury 3</span> 
                                <span class="fLW35"> <form:checkbox path="NIF3" value='1' /> </span>
                        </div>
                        <div class="fieldHolder70">
                                <span class="fLW35"> Cause of Death 3</span> 
                                <span class="fLW60"> <form:input path="COD3" class="standardInputSmallText" title="Cause of Death" maxlength="4"/> </span>
                        </div> 
                        <br><br>
                        <div class="fieldHolder30">
                                <span class="fLW60"> Nature of Injury 4</span> 
                                <span class="fLW35"> <form:checkbox path="NIF4" value='1' /> </span>
                        </div>
                        <div class="fieldHolder70">
                                <span class="fLW35"> Cause of Death 4</span> 
                                <span class="fLW60"> <form:input path="COD4" class="standardInputSmallText" title="Cause of Death" maxlength="4"/> </span>
                        </div> 
                        <br><br>
                        <div class="fieldHolder30">
                                <span class="fLW60"> Nature of Injury 5</span> 
                                <span class="fLW35"> <form:checkbox path="NIF5" value='1' /> </span>
                        </div>
                        <div class="fieldHolder70">
                                <span class="fLW35"> Cause of Death 5</span> 
                                <span class="fLW60"> <form:input path="COD5" class="standardInputSmallText" title="Cause of Death" maxlength="4"/> </span>
                        </div> 
                        <br><br>
                        <span class="fieldHolder30">
                                <span class="fLW60"> Nature of Injury 6</span> 
                                <span class="fLW35"> <form:checkbox path="NIF6" value='1' /> </span>
                        </span>
                        <span class="fieldHolder70">
                                <span class="fLW35"> Cause of Death 6</span> 
                                <span class="fLW60"> <form:input path="COD6" class="standardInputSmallText" title="Cause of Death" maxlength="4"/> </span>
                        </span> 
                        <br><br>
               			 <span class="fieldHolder30">
                                <span class="fLW60"> Nature of Injury 7</span> 
                                <span class="fLW35"> <form:checkbox path="NIF7" value='1' /> </span>
                        </span>
               			 <span class="fieldHolder70">
                                <span class="fLW35"> Cause of Death 7</span> 
                                <span class="fLW60"> <form:input path="COD7" class="standardInputSmallText" title="Cause of Death" maxlength="4"/> </span>
                        </span> 
                        <br><br>
                        <span class="fieldHolder30">
                                <span class="fLW60"> Nature of Injury 8</span> 
                                <span class="fLW35"> <form:checkbox path="NIF8" value='1' /> </span>
                        </span>
                        <span class="fieldHolder70">
                                <span class="fLW35"> Cause of Death 8</span> 
                                <span class="fLW60"> <form:input path="COD8" class="standardInputSmallText" title="Cause of Death" maxlength="4"/> </span>
                        </span> 
                        <br><br>
                        <span class="fieldHolder30">
                                <span class="fLW60"> Nature of Injury 9</span> 
                                <span class="fLW35"> <form:checkbox path="NIF9" value='1' /> </span>
                        </span>
                        <span class="fieldHolder70">
                                <span class="fLW35"> Cause of Death 9</span> 
                                <span class="fLW60"> <form:input path="COD9" class="standardInputSmallText" title="Cause of Death" maxlength="4"/> </span>
                        </span> 
                        <br><br>
                        <span class="fieldHolder30">
                                <span class="fLW60"> Nature of Injury 10</span> 
                                <span class="fLW35"> <form:checkbox path="NIF10" value='1' /> </span>
                        </span>
                        <span class="fieldHolder70">
                                <span class="fLW35"> Cause of Death 10</span> 
                                <span class="fLW60"> <form:input path="COD10" class="standardInputSmallText" title="Cause of Death" maxlength="4"/> </span>
                        </span>
                        <br><br>      
                        <span class="fieldHolder30">
                                <span class="fLW60"> Nature of Injury 11</span> 
                                <span class="fLW35"> <form:checkbox path="NIF11" value='1' /> </span>
                        </span>
                        <span class="fieldHolder70">
                                <span class="fLW35"> Cause of Death 11</span> 
                                <span class="fLW60"> <form:input path="COD11" class="standardInputSmallText" title="Cause of Death" maxlength="4"/> </span>
                        </span> 
                        <br><br>
                        <div class="fieldHolder30">
                                <span class="fLW60"> Nature of Injury 12</span> 
                                <span class="fLW35"> <form:checkbox path="NIF12" value='1' /> </span>
                        </div>
                        <div class="fieldHolder70">
                                <span class="fLW35"> Cause of Death 12</span> 
                                <span class="fLW60"> <form:input path="COD12" class="standardInputSmallText" title="Cause of Death" maxlength="4"/> </span>
                        </div>
                        
                </div>
        </div>

        <div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->        
        <div id="button-row" class="button-row documentButtons">
                <div align="center">
                        <c:choose>
							<c:when  test="${param.hasDeleteFunctionality == true}" >	
									<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
									<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
							</c:when>
							<c:when test="${stdNDIDocVO.STATENO != null && stdNDIDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
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
        </div><br>
        <input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
    </div>
</form:form>