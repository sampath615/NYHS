<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>

<link href="${staticPath}/css/docs/clusterForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/docs/commonForm.css" rel="stylesheet" type="text/css" />
    
<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	
<form:form modelAttribute="rasDocVO" id="rasDocVO" cssClass="documentFormVO documentDescription">
<form:errors path="*" cssClass="errorblock" element="div" />

	<div id="" style="background-color: transparent">
                
                <div style="padding-left:43%; " class="headingCls">
                	<c:choose>
                		<c:when test="${SIERRA_FLAG == 'Y'}">
                			SIERRA RESULTS
                		</c:when>
                		<c:otherwise>
                			RAS &nbsp;  DOCUMENT
                		</c:otherwise>
                	
                	</c:choose>
                
                                
                </div>
     </div>
     
			<div class="container pB10 w100 pT10">
<%-- 	            <div style="float:left; width: 40%">
		            <div>
		               <span class="ml10"> <spring:message code="label.stateNo" />: </span>
						<span class="ml10"><form:input path="MATCHED_STATENO" class="standardInputSmallText readonly" maxlength="10" readonly="true"/> </span>
		            </div>    
	            </div>
 --%>	            
				<div  class="floatRight">
		            <span class="mL10 bold">Document ID: </span>
					<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="10" readonly="true"/></span>
	        	 </div>		            
	   		</div>			
		
		       <div class="container" id="patientInformation">  
                        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Patient Information</span>
                        </div>
                        
                        <div style="padding-left:10px;padding-top:10px">
                                <div class="personInfo" style="height:60px">
                                
                                			<div class="fieldHolder25">
	                                            <span class="pL10"><spring:message code="label.firstname" /> </span> 
	                                            <span class="pL5"><form:input path="FIRST_NAME" id="ras_fname" class="standardInputSmallText readonly" title="First Name" maxLength="20"/></span>
	                                        </div>
	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10"><spring:message code="label.middleName" /> </span> 
                                            	<span  class="pL5"><form:input path="MIDDLE_NAME" id="ras_mname" class="standardInputSmallText readonly" title="Middle Name" maxLength="25"/></span>
                                            </div>	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10"><spring:message code="label.lastname" /></span> 
                                            	<span  class="pL5"><form:input path="LAST_NAME" id="ras_lname" class="standardInputSmallText readonly" title="Last Name" maxLength="25"/></span>
                                            </div>
                                             
	                               			<div class="fieldHolder25">
	                                       		<span class="pL10">Date of Birth </span>
	                                       		<span  class="pL5"><form:input path="DOB" id="date_of_birth" class="standardInputSmallText readonly" title="Date Of Birth" maxLength="10"/></span>
	                                       </div>   
                                </div>
                                
                        </div>        
                </div>   
                
		
		       <div class="container" id="patientInformation">  
                        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Sequence Information</span>
                        </div>
                        
                        <c:if test="${SIERRA_FLAG == null}">
                        <div style="padding-left:10px;padding-top:10px">
                                <div class="personInfo" style="height:60px">
                                
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Sequence ID</span> 
                                            	<span  class="pL5"><form:input path="VSEQID" id="vseqid" class="standardInputSmallText readonly" title="Sequence ID" maxLength="25"/></span>
                                            </div>
                                            	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Sequence ID Create Date</span> 
                                            	<span  class="pL5"><form:input path="VSEQCREATIONDATE" id="vseqcreatedate" class="standardInputSmallText readonly" title="Sequence ID Create Date" maxLength="10"/></span>
                                            </div>
                                            	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10"># of Records Grouped</span> 
                                            	<span  class="pL5"><form:input path="RECORDS_GROUPED_COUNT" id="recgroupcount" class="standardInputSmallText readonly" title="# of Records Grouped" maxLength="10"/></span>
                                            </div>
                                </div>
                                
                       </div>        
                        </c:if>
                        <div style="padding-left:10px;padding-top:10px">
                                <div class="personInfo" style="height:60px">
                                			
                                			<c:if test="${SIERRA_FLAG == null}">
                                				<div>
			                                        <div class="fieldHolder25"><span class="pL10">Resistance Document ID(s)</span></div>
			                                        <div class="fieldHolder25"><span class="pL10">Specimen Date</span> </div>                                          
		                                			
		                                			<br><br>
		                                			                                
			                                        <div class="fieldHolder25">
		                                            	<span class="pL5"><form:input path="RESISTANCE_DOC_ID_1" id="RESISTANCE_DOC_ID_1" class="standardInputSmallText readonly" title="Resistance Document ID 1" maxLength="10"/></span> 
		                                            </div>
		                                
			                                        <div class="fieldHolder25">
		                                            	<span class="pL5"><form:input path="RESISTANCE_TEST_DATE_1" id="RESISTANCE_TEST_DATE_1" class="standardInputSmallText readonly" title="Specimen Date 1" maxLength="10"/></span> 
		                                            </div>                                          
		
		                                			<br><br>
			                                        <div class="fieldHolder25">
		                                            	<span class="pL5"><form:input path="RESISTANCE_DOC_ID_2" id="RESISTANCE_DOC_ID_2" class="standardInputSmallText readonly" title="Resistance Document ID 2" maxLength="10"/></span> 
		                                            </div>
		                                
			                                        <div class="fieldHolder25">
		                                            	<span class="pL5"><form:input path="RESISTANCE_TEST_DATE_2" id="RESISTANCE_TEST_DATE_2" class="standardInputSmallText readonly" title="Specimen Date 2" maxLength="10"/></span> 
		                                            </div>                                          
		
		                                			<br><br>
			                                        <div class="fieldHolder25">
		                                            	<span class="pL5"><form:input path="RESISTANCE_DOC_ID_3" id="RESISTANCE_DOC_ID_3" class="standardInputSmallText readonly" title="Resistance Document ID 3" maxLength="10"/></span> 
		                                            </div>
		                                
			                                        <div class="fieldHolder25">
		                                            	<span class="pL5"><form:input path="RESISTANCE_TEST_DATE_3" id="RESISTANCE_TEST_DATE_3" class="standardInputSmallText readonly" title="Specimen Date 3" maxLength="10"/></span> 
		                                            </div>  
		                                           </div>
		                                           <br/><br/>
		                                           <br/><br/>
	                                        </c:if>
	                                        <c:if test="${SIERRA_FLAG == 'Y'}">
<!-- 	                                        	<div class="mT10"> -->
		                                        	<div class="fieldHolder25">
		                                        		<span class="mL1-">Resistance Document ID</span>
		                                        		<span class="mL10"><form:input path="DOCUMENT_ID" id="DOCUMENT_ID" class="standardInputSmallText readonly" title="Resistance Document ID" maxLength="10"/></span>
		                                        	</div>
			                                        <div class="fieldHolder25">
			                                        	<span class="mL10">Specimen Date</span>
			                                        	<span class="mL10"><form:input path="COLLECTION_DATE" id="COLLECTION_DATE" class="standardInputSmallText readonly" title="Specimen Date" maxLength="10"/></span>
			                                         </div>
<!-- 			                                   </div>                                           -->
	                                        </c:if>           
                                            
                                            
                                            
                                                                         
											<div class="">

						                    
		                                        <div class="fieldHolder25">
	                                            	<span class="pL10">Genes Found</span> 
	                                            	<span  class="pL5"><form:input path="GENESFOUNDTEXT" id="GENESFOUNDTEXT" class="standardInputSmallText readonly" title="Genes Found" maxLength="25"/></span>
	                                            </div>
	                                            	                                        
		                                        <div class="fieldHolder25">
	                                            	<span class="pL10">Subtype</span> 
	                                            	<span  class="pL5"><form:input path="PHYLOSUBTYPE" id="PHYLOSUBTYPE" class="standardInputSmallText readonly" title="Subtype" maxLength="10"/></span>
	                                            </div>
	
							                    <br><br><br>
							                    
	 											<div class="fieldHolder25"><span class="pL10">Resistance to The Drug Class</span></div>       
	 											
	 											<br><br>
	 											
	 											<div class="fieldHolder33">
	                                            	<span  class="pL5"><form:input path="RESISTANCE_DRUG_CLASS_PR_STR" id="RESISTANCE_DRUG_CLASS_PR_STR" class="standardInputVerySmall readonly" title="PR" maxLength="10"/></span>                                            	
	                                            	<span class="pL10">Protease (PR)</span> 
	                                            </div>
	                                            
	 											<div class="fieldHolder33">
	                                            	<span  class="pL5"><form:input path="RESISTANCE_DRUG_CLASS_NRTI_STR" id="RESISTANCE_DRUG_CLASS_NRTI_STR" class="standardInputVerySmall readonly" title="NRTI" maxLength="10"/></span>
	                                            	<span class="pL10">Nucleoside Reverse Transcriptase (NRTI)</span> 
	                                            </div>
	                                            
	   											<br><br>
	 											
	 											<div class="fieldHolder33">
	                                            	<span  class="pL5"><form:input path="RESISTANCE_DRUG_CLASS_NNRTI_STR" id="RESISTANCE_DRUG_CLASS_NNRTI_STR" class="standardInputVerySmall readonly" title="NNRTI" maxLength="10"/></span> 											
	                                            	<span class="pL10">Non-Nucleoside Reverse Transcriptase (NNRTI)</span> 
	                                            </div>
	                                            
	 											<div class="fieldHolder33">
	                                            	<span  class="pL5"><form:input path="RESISTANCE_DRUG_CLASS_IN_STR" id="RESISTANCE_DRUG_CLASS_IN_STR" class="standardInputVerySmall readonly" title="IN" maxLength="10"/></span> 											
	                                            	<span class="pL10">Integrase (IN)</span> 
	                                            </div>
	                                          </div>
                                            
                                </div>
                                
                       </div>        

                </div>                   
                
				<br>                 
				
	<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
	
	<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />	
	
 
</form:form>