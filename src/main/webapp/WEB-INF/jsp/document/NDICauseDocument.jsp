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
 	                 <span class="ml10"><form:input id="STATENO"  path="stateno" class="standardInputSmallText" maxlength="10" readonly="true"/> </span>
 	            </div>    
 	          </div>
 	          <div style="float:left; width: 30%">
 	            	<div>
 	                <span class=" ml10">	eHARS Import Date: </span>
 			 		<span class="ml10"><form:input id="EHARS_IMPORT_DATE"  path="stdNdiCauseDoc.eharsImportDate" class="standardInputSmallText ml10" readonly="true"/> </span>
 	           	</div>    
 	         </div>
 	         
 	         <div style="float:left; width: 30%; text-align: right">
 	            <div>
 	                <span class=" ml10">	Document ID: </span>
 			 		<span class="ml10"><form:input id="DOCUMENT_ID"  path="stdNdiCauseDoc.documentId" class="standardinputSmallText ml10" maxlength="6" readonly="true"/> </span>
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
                                <span class="pL10"> <form:input id="FNAME"  path="stdNdiCauseDoc.fname" class="standardInputSmallText" /> </span>
                        </span>
                        <span class="">
                                <span class="pL30 bold"> Middle Name</span>
                                <span class="pL10"> <form:input id="MNAME"  path="stdNdiCauseDoc.mname" class="standardInputSmallText" /> </span>
                        </span>
                        <span class="">
                                <span class="pL30 bold"> Last Name</span> 
                                <span class="pL10"> <form:input id="LNAME"  path="stdNdiCauseDoc.lname" class="required standardInputSmallText" /> </span>
                        </span>
                        <span class="">
                                <span class="pL30 bold"> SSN</span>
                                <span class="pL10"> <form:input id="SSN"  path="stdNdiCauseDoc.ssn" class="standardInputSmallText" /> </span>
                        </span>   
                        <br>               
                </div>
                <div class="mLRTB">
                         <span class="">
                                <span class="bold"> Date of Birth</span>
                                <span class="pL10"> <form:input id="DOB_TEXT"  path="stdNdiCauseDoc.dobText" class="standardInputSmallText" /> </span>
                        </span>
                        <span class="">
                                <span class="pL30 bold"> State of Residence</span> 
                                <span class="pL10"> <form:input id="RES_STATE"  path="stdNdiCauseDoc.resState" class="standardInputSmallText" /> </span>
                        </span> 
               			<span class="">
                                <span class="pL30 bold"> Gender</span> 
                                <span class="pL10"> 
                                        <span> <form:radiobutton id="GENDER1"  path="stdNdiCauseDoc.gender" value="M" /> Male </span>
                                        <span> <form:radiobutton id="GENDER2"  path="stdNdiCauseDoc.gender" value="F" /> Female </span>
                                </span>
                        </span>
                        
                        <br>                   
                </div>
                <div class="mLRTB">
                          <div class="bold">
	                        Race
	                    </div>
	                    <div class="mL50 fieldHolder25 ">
	                        <div><form:checkbox id="RACE_WHITE1"  path="stdNdiCauseDoc.raceWhite"  value="1" /> White</div>
	                        
	                        <div><form:checkbox id="RACE_BLACK1"  path="stdNdiCauseDoc.raceBlack"  value="1" /> African American </div>
	                        
	                        <div><form:checkbox id="RACE_ASIAN1"  path="stdNdiCauseDoc.raceAsian"  value="1" /> Asian/Pacific Islander </div>
	                    </div>
	                    
	                    <div class="fieldHolder25 ">
	                        <div><form:checkbox id="RACE_INDIAN1"  path="stdNdiCauseDoc.raceIndian"  value="1" /> American Indian/Alaskan Native</div>
	                        
	                        <div><form:checkbox id="RACE_PACIFIC1"  path="stdNdiCauseDoc.racePacific"  value="1" /> Pacific</div>
	                        
	                        <div><form:checkbox id="RACE_MULTIPLE1"  path="stdNdiCauseDoc.raceMultiple"  value="1" /> Multiple</div>
	                    </div>
	                    <div  class="fieldHolder25 ">
	                        <div><form:checkbox id="RACE_OTHER1"  path="stdNdiCauseDoc.raceOther"  value="1" /> Other</div>
	                        
	                        <div><form:checkbox id="RACE_UNKNOWN1"  path="stdNdiCauseDoc.raceUnknown"  value="1" /> Unknown</div> 
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
                                <span class="fLW60"> <form:input id="UNIQ_NO"  path="stdNdiCauseDoc.uniqNo" class="required standardInputSmallText" /> </span>
                        </span> 
                        <span class="fieldHolder25">
                                <span class="fLW35 bold"> File Category</span> 
                                <span class="fLW60"> 
                                        <span> <form:radiobutton id="NDIFILE_CAT1"  path="stdNdiCauseDoc.ndifileCat" value="U" /> Unknown </span>
                                        <span> <form:radiobutton id="NDIFILE_CAT2"  path="stdNdiCauseDoc.ndifileCat" value="K" /> Known </span>
                                </span>
                        </span> 
                        <br>
                </div>    
                <div class="mLRTB pB10">           
                        <span class="fieldHolder25">
                                <span class="fLW35 bold"> Date of Death</span> 
                                <span class="fLW60"> <form:input id="DOD_TEXT"  path="stdNdiCauseDoc.dodText" class="required standardInputSmallText" /> </span>
                        </span> 
                        <span class="fieldHolder25">
                                <span class="fLW35 bold"> Death Certificate</span>
                                <span class="fLW60"> <form:input id="DEATH_CERT"  path="stdNdiCauseDoc.deathCert" class="standardInputSmallText" /> </span>
                        </span>        
                        <span class="fieldHolder25">
                                <span class="fLW35 bold"> State of Death</span> 
                                <span class="fLW60"> <form:input id="DEATH_STATE"  path="stdNdiCauseDoc.deathState" class="standardInputSmallText" /> </span>
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
                		
                
                		<c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause1 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag1 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 1</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG11"  path="stdNdiCauseDoc.natureInjuryFlag1" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 1</span> 
	                                <span class="fLW60"> <form:input id="CAUSE1"  path="stdNdiCauseDoc.cause1" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause2 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag2 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 2</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG21"  path="stdNdiCauseDoc.natureInjuryFlag2" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 2</span> 
	                                <span class="fLW60"> <form:input id="CAUSE2"  path="stdNdiCauseDoc.cause2" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause3 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag3 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 3</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG31"  path="stdNdiCauseDoc.natureInjuryFlag3" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 3</span> 
	                                <span class="fLW60"> <form:input id="CAUSE3"  path="stdNdiCauseDoc.cause3" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause4 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag4 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 4</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG41"  path="stdNdiCauseDoc.natureInjuryFlag4" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 4</span> 
	                                <span class="fLW60"> <form:input id="CAUSE4"  path="stdNdiCauseDoc.cause4" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause5 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag5 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 5</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG51"  path="stdNdiCauseDoc.natureInjuryFlag5" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 5</span> 
	                                <span class="fLW60"> <form:input id="CAUSE5"  path="stdNdiCauseDoc.cause5" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause6 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag6 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 6</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG61"  path="stdNdiCauseDoc.natureInjuryFlag6" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 6</span> 
	                                <span class="fLW60"> <form:input id="CAUSE6"  path="stdNdiCauseDoc.cause6" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause7 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag7 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 7</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG71"  path="stdNdiCauseDoc.natureInjuryFlag7" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 7</span> 
	                                <span class="fLW60"> <form:input id="CAUSE7"  path="stdNdiCauseDoc.cause7" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause8 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag8 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 8</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG81"  path="stdNdiCauseDoc.natureInjuryFlag8" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 8</span> 
	                                <span class="fLW60"> <form:input id="CAUSE8"  path="stdNdiCauseDoc.cause8" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause9 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag9 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 9</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG91"  path="stdNdiCauseDoc.natureInjuryFlag9" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 9</span> 
	                                <span class="fLW60"> <form:input id="CAUSE9"  path="stdNdiCauseDoc.cause9" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause10 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag10 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 10</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG101"  path="stdNdiCauseDoc.natureInjuryFlag10" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 10</span> 
	                                <span class="fLW60"> <form:input id="CAUSE10"  path="stdNdiCauseDoc.cause10" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause11 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag11 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 11</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG111"  path="stdNdiCauseDoc.natureInjuryFlag11" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 11</span> 
	                                <span class="fLW60"> <form:input id="CAUSE11"  path="stdNdiCauseDoc.cause11" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause12 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag12 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 12</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG121"  path="stdNdiCauseDoc.natureInjuryFlag12" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 12</span> 
	                                <span class="fLW60"> <form:input id="CAUSE12"  path="stdNdiCauseDoc.cause12" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause13 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag13 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 13</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG131"  path="stdNdiCauseDoc.natureInjuryFlag13" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 13</span> 
	                                <span class="fLW60"> <form:input id="CAUSE13"  path="stdNdiCauseDoc.cause13" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause14 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag14 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 14</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG141"  path="stdNdiCauseDoc.natureInjuryFlag14" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 14</span> 
	                                <span class="fLW60"> <form:input id="CAUSE14"  path="stdNdiCauseDoc.cause14" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause15 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag15 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 15</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG151"  path="stdNdiCauseDoc.natureInjuryFlag15" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 15</span> 
	                                <span class="fLW60"> <form:input id="CAUSE15"  path="stdNdiCauseDoc.cause15" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause16 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag16 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 16</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG161"  path="stdNdiCauseDoc.natureInjuryFlag16" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 16</span> 
	                                <span class="fLW60"> <form:input id="CAUSE16"  path="stdNdiCauseDoc.cause16" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause17 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag17 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 17</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG171"  path="stdNdiCauseDoc.natureInjuryFlag17" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 17</span> 
	                                <span class="fLW60"> <form:input id="CAUSE17"  path="stdNdiCauseDoc.cause17" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause18 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag18 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 18</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG181"  path="stdNdiCauseDoc.natureInjuryFlag18" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 18</span> 
	                                <span class="fLW60"> <form:input id="CAUSE18"  path="stdNdiCauseDoc.cause18" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause19 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag19 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 19</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG191"  path="stdNdiCauseDoc.natureInjuryFlag19" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 19</span> 
	                                <span class="fLW60"> <form:input id="CAUSE19"  path="stdNdiCauseDoc.cause19" class="standardInputSmallText" /> </span>
	                        </span> 
	                        <br><br>
	                    </c:if>
	                    
	                    <c:if test="${stdNDICauseDocVO.stdNdiCauseDoc.cause20 != null  || stdNDICauseDocVO.stdNdiCauseDoc.natureInjuryFlag20 != null}">
	                        <span class="fieldHolder30">
	                                <span class="fLW60 bold"> Nature of Injury 20</span> 
	                                <span class="fLW35"> <form:checkbox id="NATURE_INJURY_FLAG201"  path="stdNdiCauseDoc.natureInjuryFlag20" value='1' /> </span>
	                        </span>
	                        <span class="fieldHolder70">
	                                <span class="fLW35 bold"> Cause of Death 20</span> 
	                                <span class="fLW60"> <form:input id="CAUSE20"  path="stdNdiCauseDoc.cause20" class="standardInputSmallText" /> </span>
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
							<c:when test="${userPermissions['90'] != null && stdNDICauseDocVO.stateno != null && stdNDICauseDocVO.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null}">
									<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
							</c:when>
						</c:choose>

                </div>   
        </div><br>
        <input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
</form:form>
