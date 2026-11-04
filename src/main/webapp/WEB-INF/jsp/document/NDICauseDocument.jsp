<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link href="${staticPath}/css/docs/ndiForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />

<script src="${staticPath}/js/docs/docViewers.js?v=${buildDate}"></script>

<form:form modelAttribute="stdNDICauseDocVO" id="stdNDICauseDocVO" cssClass="documentDescription">
<form:errors path="*" cssClass="errorblock" element="div" />

        <div class="headingCls centerDoc">
                NDI Cause Document
        </div>
        
        
		<div class="container w100 pB10 pT20" >
 	         <div style="float:left; width: 40%">
 	            <div>
 	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
 	                 <span class="ml10"><form:input path="STATENO" class="standardInputSmallText" maxlength="10" readonly="true"/> </span>
 	            </div>    
 	          </div>
 	          <div style="float:left; width: 30%">
 	            	<div>
 	                <span class=" ml10">	eHARS Import Date: </span>
 			 		<span class="ml10"><form:input path="EHARS_IMPORT_DATE" class="standardInputSmallText ml10" readonly="true"/> </span>
 	           	</div>    
 	         </div>
 	         
 	         <div style="float:left; width: 30%; text-align: right">
 	            <div>
 	                <span class=" ml10">	Document ID: </span>
 			 		<span class="ml10"><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10" maxlength="6" readonly="true"/> </span>
 	           	</div>    
 	         </div>
     
 		</div>
 		
 		
 		<jsp:include page="statenoDetails.jsp"></jsp:include>
 		

        <!--  Patient details -->
        <div class="container borderNDIDoc pB10">
                <div class="divHeadingCls bGHeading">
                        <span class=" mL2"> BHAE Data:</span>                                                                      
                </div>   
                <div class="mLRTB">
                        <span class="">
                                <span class="bold"> First Name</span> 
                                <span class="pL10"> <form:input path="FNAME" class="standardInputSmallText" /> </span>
                        </span>
                        <span class="">
                                <span class="pL30 bold"> Middle Name</span>
                                <span class="pL10"> <form:input path="MNAME" class="standardInputSmallText" /> </span>
                        </span>
                        <span class="">
                                <span class="pL30 bold"> Last Name</span> 
                                <span class="pL10"> <form:input path="LNAME" class="required standardInputSmallText" /> </span>
                        </span>
                        <span class="">
                                <span class="pL30 bold"> SSN</span>
                                <span class="pL10"> <form:input path="SSN" class="standardInputSmallText" /> </span>
                        </span>   
                        <br>               
                </div>
                <div class="mLRTB">
                         <span class="">
                                <span class="bold"> Date of Birth</span>
                                <span class="pL10"> <form:input path="DOB_TEXT" class="standardInputSmallText" /> </span>
                        </span>
                        <span class="">
                                <span class="pL30 bold"> State of Residence</span> 
                                <span class="pL10"> <form:input path="RES_STATE" class="standardInputSmallText" /> </span>
                        </span> 
               			<span class="">
                                <span class="pL30 bold"> Gender</span> 
                                <span class="pL10"> 
                                        <span> <form:radiobutton path="GENDER" value="M" /> Male </span>
                                        <span> <form:radiobutton path="GENDER" value="F" /> Female </span>
                                </span>
                        </span>
                        
                        <br>                   
                </div>
                <div class="mLRTB">
                          <div class="bold">
	                        Race
	                    </div>
	                    <div class="mL50 fieldHolder25 ">
	                        <div><form:checkbox path="RACE_WHITE"  value="1" /> White</div>
	                        
	                        <div><form:checkbox path="RACE_BLACK"  value="1" /> African American </div>
	                        
	                        <div><form:checkbox path="RACE_ASIAN"  value="1" /> Asian/Pacific Islander </div>
	                    </div>
	                    
	                    <div class="fieldHolder25 ">
	                        <div><form:checkbox path="RACE_INDIAN"  value="1" /> American Indian/Alaskan Native</div>
	                        
	                        <div><form:checkbox path="RACE_PACIFIC"  value="1" /> Pacific</div>
	                        
	                        <div><form:checkbox path="RACE_MULTIPLE"  value="1" /> Multiple</div>
	                    </div>
	                    <div  class="fieldHolder25 ">
	                        <div><form:checkbox path="RACE_OTHER"  value="1" /> Other</div>
	                        
	                        <div><form:checkbox path="RACE_UNKNOWN"  value="1" /> Unknown</div> 
	                    </div>
	                     
	           </div>  
        </div>   


        <!-- NDI details -->
        <div class="container borderNDIDoc">
                <div class="divHeadingCls bGHeading">
                        <span class=" mL2"> National Death Index:</span>                                                                      
                </div>
                <div class="mLRTB pB10">    
                     
                        <span class="fieldHolder25">
                                <span class="fLW35 bold"> Unique No</span> 
                                <span class="fLW60"> <form:input path="UNIQ_NO" class="required standardInputSmallText" /> </span>
                        </span> 
                        <span class="fieldHolder25">
                                <span class="fLW35 bold"> File Category</span> 
                                <span class="fLW60"> 
                                        <span> <form:radiobutton path="NDIFILE_CAT" value="U" /> Unknown </span>
                                        <span> <form:radiobutton path="NDIFILE_CAT" value="K" /> Known </span>
                                </span>
                        </span> 
                        <br>
                </div>    
                <div class="mLRTB pB10">           
                        <span class="fieldHolder25">
                                <span class="fLW35 bold"> Date of Death</span> 
                                <span class="fLW60"> <form:input path="DOD_TEXT" class="required standardInputSmallText" /> </span>
                        </span> 
                        <span class="fieldHolder25">
                                <span class="fLW35 bold"> Death Certificate</span>
                                <span class="fLW60"> <form:input path="DEATH_CERT" class="standardInputSmallText" /> </span>
                        </span>        
                        <span class="fieldHolder25">
                                <span class="fLW35 bold"> State of Death</span> 
                                <span class="fLW60"> <form:input path="DEATH_STATE" class="standardInputSmallText" /> </span>
                        </span>                                                              
                        
                        <br>
                </div> 
        </div>
                
        <!--  COD details -->
        <div class="container borderNDIDoc">
                <div class="divHeadingCls bGHeading">
                        <span class=" mL2"> Nature of Injury/ Cause of Death:</span>                                                                      
                </div>
                <div id="personNDIDetailsLeft">
                		
                
                		<c:if test="${stdNDICauseDocVO.CAUSE1 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG1 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 1</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG1" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 1</span> 
	                                <span class="fLW60"> <form:input path="CAUSE1" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE2 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG2 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 2</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG2" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 2</span> 
	                                <span class="fLW60"> <form:input path="CAUSE2" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE3 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG3 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 3</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG3" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 3</span> 
	                                <span class="fLW60"> <form:input path="CAUSE3" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE4 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG4 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 4</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG4" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 4</span> 
	                                <span class="fLW60"> <form:input path="CAUSE4" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE5 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG5 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 5</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG5" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 5</span> 
	                                <span class="fLW60"> <form:input path="CAUSE5" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE6 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG6 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 6</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG6" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 6</span> 
	                                <span class="fLW60"> <form:input path="CAUSE6" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE7 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG7 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 7</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG7" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 7</span> 
	                                <span class="fLW60"> <form:input path="CAUSE7" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE8 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG8 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 8</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG8" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 8</span> 
	                                <span class="fLW60"> <form:input path="CAUSE8" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE9 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG9 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 9</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG9" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 9</span> 
	                                <span class="fLW60"> <form:input path="CAUSE9" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE10 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG10 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 10</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG10" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 10</span> 
	                                <span class="fLW60"> <form:input path="CAUSE10" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE11 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG11 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 11</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG11" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 11</span> 
	                                <span class="fLW60"> <form:input path="CAUSE11" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE12 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG12 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 12</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG12" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 12</span> 
	                                <span class="fLW60"> <form:input path="CAUSE12" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE13 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG13 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 13</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG13" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 13</span> 
	                                <span class="fLW60"> <form:input path="CAUSE13" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE14 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG14 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 14</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG14" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 14</span> 
	                                <span class="fLW60"> <form:input path="CAUSE14" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE15 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG15 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 15</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG15" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 15</span> 
	                                <span class="fLW60"> <form:input path="CAUSE15" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE16 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG16 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 16</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG16" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 16</span> 
	                                <span class="fLW60"> <form:input path="CAUSE16" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE17 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG17 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 17</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG17" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 17</span> 
	                                <span class="fLW60"> <form:input path="CAUSE17" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE18 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG18 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 18</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG18" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 18</span> 
	                                <span class="fLW60"> <form:input path="CAUSE18" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE19 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG19 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 19</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG19" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 19</span> 
	                                <span class="fLW60"> <form:input path="CAUSE19" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.CAUSE20 != null  || stdNDICauseDocVO.NATURE_INJURY_FLAG20 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 20</span> 
	                                <span class="fLW35"> <form:checkbox path="NATURE_INJURY_FLAG20" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 20</span> 
	                                <span class="fLW60"> <form:input path="CAUSE20" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    
	                    
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
							<c:when test="${userPermissions['90'] != null && stdNDICauseDocVO.STATENO != null && stdNDICauseDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
									<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
							</c:when>
						</c:choose>

                </div>   
        </div><br>
        <input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
</form:form>
