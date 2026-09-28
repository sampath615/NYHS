<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link href="${staticPath}/css/docs/clusterForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/docs/commonForm.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/upload/upload.css?v=${buildDate}" rel="stylesheet" type="text/css" />
    
<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
<script src="${staticPath}/js/docs/clusterDoc.js?v=${buildDate}"></script>
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 
	
 	<div id="clusterDocumentPage" style="display:none">
	   		<div id="clusterDocumentTransactionMessage" class="transactionMessage"><span class="message"></span></div>
   </div>
   	

<form:form modelAttribute="clusterDocVO" id="clusterDocVO" cssClass="documentFormVO documentDescription">
<form:errors path="*" cssClass="errorblock" element="div" />

	<div id="" style="background-color: transparent">
                
                <div style="padding-left:43%; " class="headingCls">
                                CLUSTER &nbsp;  DOCUMENT
                </div>
     </div>
     
     		 <div class="pB30 pT20">
					
				<c:if test="${clusterDocVO.PARENT_DOCUMENT_ID != null &&  clusterDocVO.PARENT_DOCUMENT_ID != 0}">
	
					<div  class="floatLeft width70Perc" id="parentDocumentIdSection">
			            <span class="parentIdSectionLabel bold mL10">Parent DocumentId: </span>
						<span class="parentIdSectionValue "><a href="#" onclick="loadDocumentDlg(${clusterDocVO.PARENT_DOCUMENT_ID}, '${param.documentType}')">${clusterDocVO.PARENT_DOCUMENT_ID}</a></span>
						
			         </div>	
		         </c:if>

				<c:if test="${clusterDocVO.IS_RES_RECORD != 'Y' }">
	
					<div  class="floatLeft width70Perc" id="parentDocumentIdSection">
			            <span class="parentIdSectionLabel bold mL10">* No Resistance Record</span>
			         </div>	
		         </c:if>
		         
					<div  class="floatRight">
			            <span class="mL10 bold">Document ID: </span>
						<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="10" readonly="true"/></span>
		         </div>	
			</div>
			
		 <div class="clearBoth container pB5 w100">
            <div style="float:left; padding-bottom:10px; width: 40%">
	            <div >
	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
                    <span class="ml10"><form:input path="STATENO" class="standardInputSmallText readonly" style="width:180px" maxlength="10" readonly="true"/> </span>
	            </div>    
	            </div>
	 	          <div style="float:left;  width: 30%">
	 	         	<div >
	 	                <span class="ml10"> eHARS Import Date: </span>
	 	                <fmt:formatDate value="${clusterDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
	 			 		<span class="ml10"><form:input path="EHARS_IMPORT_DATE" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/> </span>
	 	            </div>    
	 	         </div>            
   		</div>			
			
   			<jsp:include page="statenoDetails.jsp"></jsp:include>			 
		
		       <div class="container" id="patientInformation">  
                        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Cluster Information</span>
                        </div>
                        
                        <div style="padding-left:10px;padding-top:10px">
                                <div class="personInfo" style="height:60px">
                                
                                			<div class="fieldHolder25">
	                                            <span class="pL10">Cluster ID</span> 
	                                            <span class="pL5"><form:input path="CLUSTER_ID" id="cluster_id" class="standardInputMedText readonly" title="Cluster ID" maxLength="12"/></span>
	                                        </div>
	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Cluster Type</span> 
                                            	<span  class="pL5"><form:input path="CLUSTER_TYPE_DESC" id="cluster_type" class="standardInputMedText readonly" title="Cluster Type" maxLength="10"/></span>
                                            </div>	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Cluster Create Date</span> 
                                            	<span  class="pL5"><form:input path="CLUSTER_DATE" id="cluster_date" class="standardInputMedText readonly" title="Cluster Date" maxLength="25"/></span>
                                            </div>

                                        <br><br><br>
                                                                                       
	                               		<c:if test="${clusterDocVO.CLUSTER_TYPE != 'BHAE_TS'}">                                                                                             
	                               			<div class="fieldHolder25">
	                                       		<span class="pL10">File Name</span>
	                                   			<span class="pL5"><form:input path="REFERENCE_FILE_NAME" id="cluster_filename" class="standardInputMedText readonly" title="Upload File Name" maxlength="100"/> </span>
	                                       </div>
	                                    
	                               			<div class="fieldHolder25">
	                                       		<span class="pL10">Added to Cluster</span>
	                                   			<span class="pL5"><form:input path="CREATE_DATE" id="dt_added_to_cluster" class="standardInputMedText readonly" title="Date added to Cluster" maxlength="25"/> </span>
	                                       </div>
	                                       
	                                        <div class="fieldHolder25">
	                                           	<span class="pL10">Identification Method</span> 
	                                           	<span  class="pL5"><form:input path="IDENTIFICATION_METHOD" id="IDENTIFICATION_METHOD" class="standardInputMedText readonly" title="IDENTIFICATION METHOD" maxLength="50"/></span>
	                                        </div>	
	                                        
	                                        <div class="fieldHolder25">
	                                           	<span class="pL10">Identification Date</span> 
	                                           	<span  class="pL5"><form:input path="IDENTIFICATION_DATE" id="IDENTIFICATION_DATE" class="standardInputMedText readonly" title="IDENTIFICATION_DATE" maxLength="10"/></span>
	                                        </div>	                                      
                                        
                                        <br><br><br>
                                         	                                       
	                                        <div class="fieldHolder25">
	                                           	<span class="pL10">Relatedness</span> 
	                                           	<span  class="pL5"><form:input path="RELATEDNESS" id="relatedness" class="standardInputMedText readonly" title="Relatedness" maxLength="10"/></span>
	                                        </div>	
	                                        
	                                        <div class="fieldHolder25">
	                                           	<span class="pL10">Timeframe</span> 
	                                           	<span  class="pL5"><form:input path="TIMEFRAME" id="Timeframe" class="standardInputMedText readonly" title="Timeframe" maxLength="20"/></span>
	                                        </div>	                                      

	                                  	</c:if>    
	                                  		                                    
	                                    <c:if test="${clusterDocVO.CLUSTER_TYPE == 'BHAE_TS'}">
	                                    
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Dx Start Date</span> 
                                            	<span  class="pL5"><form:input path="DX_START_DATE" id="dx_start_dt" class="standardInputMedText readonly" title="Dx Start Date" maxLength="10"/></span>
                                            </div>

	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Dx End Date</span> 
                                            	<span  class="pL5"><form:input path="DX_END_DATE" id="dx_end_dt" class="standardInputMedText readonly" title="Dx End Date" maxLength="10"/></span>
                                            </div>
                                            
	                                        <div class="fieldHolder49">
                                            	<span class="pL10">Cluster Counties</span> 
                                            	<span  class="pL5"><form:input path="CLUSTER_COUNTIES" id="cluster_counties" class="standardInputLongText readonly" title="Cluster Counties"/></span>
                                            </div>

                                        <br><br><br>
                                            
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Doc Inactive</span> 
                                            	<span  class="pL5"><form:input path="INACTIVE_FLAG" id="inctive_flag" class="standardInputMedText readonly" title="Inactive Flag" maxLength="10"/></span>
                                            </div>

	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Doc Inactive Date</span> 
                                            	<span  class="pL5"><form:input path="INACTIVE_DATE" id="inctive_date" class="standardInputMedText readonly" title="Inactive Date" maxLength="10"/></span>
                                            </div>
                                            
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Cluster End Date</span> 
                                            	<span  class="pL5"><form:input path="CLUSTER_END_DATE" id="cluster_end_date" class="standardInputMedText readonly" title="Cluster Date" maxLength="25"/></span>
                                            </div>                                            
                                            
                                        <br><br><br>
                                                                                    
										</c:if>  

	                                      <div class="fieldHolder49">
	                                       		<span class="pL10">Cluster Description</span><br>
	                                   			<span class="pL5"><form:textarea path="CLUSTER_DESC" id="cluster_desc" cols="80" rows="2" class="readonly" title="Cluster Desc"/> </span>
	                                      </div>                                  
                                </div>
                                
                        </div>        
                </div>        
                            
				<br>                 

		       <div class="container" id="patientInformation">  
                        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Patient Demographic pulled for Document Creation</span>
                                <a id="clusterHelpIcon" class="tooltip" title="Heirarchy Used for Document Demographic Data" onClick="$('#docCreationDemographicDialog').dialog('open');"></a>					
                        </div>
                        
                        <div style="padding-left:10px;padding-top:10px">
                                <div class="personInfo" style="height:60px">
                                
                                			<div class="fieldHolder25">
	                                            <span class="pL10"><spring:message code="label.firstname" /> </span> 
	                                            <span class="pL5"><form:input path="FIRST_NAME" id="cluster_fname" class="standardInputSmallText readonly" title="First Name" maxLength="20"/></span>
	                                        </div>
	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10"><spring:message code="label.middleName" /> </span> 
                                            	<span  class="pL5"><form:input path="MIDDLE_NAME" id="cluster_mname" class="standardInputSmallText readonly" title="Middle Name" maxLength="25"/></span>
                                            </div>	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10"><spring:message code="label.lastname" /></span> 
                                            	<span  class="pL5"><form:input path="LAST_NAME" id="cluster_lname" class="standardInputSmallText readonly" title="Last Name" maxLength="25"/></span>
                                            </div>
                                             
	                               			<div class="fieldHolder25">
	                                       		<span class="pL10">Date of Birth </span>
	                                       		<span  class="pL5"><form:input path="DOB" id="date_of_birth" class="standardInputSmallText readonly" title="Date Of Birth" maxLength="10"/></span>
<%-- 	                                       		<fmt:formatDate value="${clusterDocVO.DOB}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDOBDate" />
	                                   			<span class="pL5"><form:input path="DOB" value= "${theFormattedDOBDate}" id="DATE_OF_BIRTH" class="standardInputSmallText readonly" title="Date Of Birth" maxlength="10"/> </span>
 --%>	                                    </div>   
                                </div>
                                
                        </div>        
                </div>   

				<br>        

		       <div class="container" id="patientInformation">  
                        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Current eHARS Data</span>
                        </div>
                        
                        <div style="padding-left:10px;padding-top:10px">
                        
                        		<div><b>NYS</b></div><br>
                        		
                                <div class="personInfo" style="height:125px">

                                			<div class="fieldHolder25">
	                                            <span class="pL10">Residence HIV County</span> 
                                            	<span  class="pL10">
	                                            	<form:select path="RSH_COUNTY_FIPS"  cssClass="oAdrDlg roundedBrdr newCounty widgetAddressFieldsReadonly" title="RSH County"> 
															<form:option value=""></form:option>
															<form:options items="${sessionScope.countyMap}"/>
													</form:select>
												</span>	                                            
	                                        </div>
	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Residence HIV State</span> 
                                            	<span  class="pL5"><form:input path="RSH_STATE_CD" id="RSH_STATE_CD" class="standardInputSmallText1 readonly" title="RSH State" maxLength="25"/></span>
                                            </div>
                                            	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Current County</span> 
                                            	<span  class="pL10">
	                                            	<form:select path="CUR_COUNTY_FIPS" cssClass="oAdrDlg roundedBrdr newCounty widgetAddressFieldsReadonly" title="Current County"> 
															<form:option value=""></form:option>
															<form:options items="${sessionScope.countyMap}"/>
													</form:select>
												</span>
                                            </div>
                                             
	                               			<div class="fieldHolder25">
	                                       		<span class="pL10">Current State</span>
	                                   			<span class="pL5"><form:input path="CUR_STATE_CD" id="CUR_STATE_CD" class="standardInputSmallText1 readonly" title="Current State" maxlength="10"/> </span>
	                                       </div>   

                                        <br><br><br>
                                			<div class="fieldHolder25">
	                                            <span class="pL10">HIV DX Date</span> 
												<span  class="pL5"><form:input path="HIV_DX_DT" id="HIV_DX_DT" class="standardInputSmallText1 readonly" title="HIV_DX_DT" maxLength="25"/></span>	                                            
	                                        </div>
	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">AIDS DX Date</span> 
                                            	<span  class="pL5"><form:input path="AIDS_DX_DT" id="AIDS_DX_DT" class="standardInputSmallText1 readonly" title="AIDS_DX_DT" maxLength="25"/></span>
                                            </div>
                                            	                                        
	                               			<div class="fieldHolder25">
	                                       		<span class="pL10">CDC Eligible</span>
	                                   			<span class="pL5"><form:input path="HIV_AIDS_CDC_ELIG" id="HIV_AIDS_CDC_ELIG" class="standardInputSmallText1 readonly" title="HIV_AIDS_CDC_ELIG" maxlength="10"/> </span>
	                                       </div>   
	                                       
	                               			<div class="fieldHolder25">
	                                       		<span class="pL10">Status</span>
	                                   			<span class="pL5"><form:input path="STATUS_FLAG" id="STATUS_FLAG" class="standardInputSmallText readonly" title="STATUS_FLAG" maxlength="20"/> </span>

	                                       </div>   

                                        <br><br><br>
                                
                                			<div class="fieldHolder25">
	                                            <span class="pL10">Ethnicity</span> 
												<span class="pL5"><form:input path="ETHNICITY" id="Ethnicity" class="standardInputSmallText readonly" title="Ethnicity" maxLength="20"/></span>                                          
	                                        </div>
	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Race</span> 
												<span class="pL5"><form:input path="RACE" id="race" class="standardInputSmallText readonly" title="Race" maxLength="20"/></span>
                                            </div>
	                                       
                                </div>

                        
                        		<div><b>NYC</b></div><br>
                        		
                                <div class="personInfo" style="height:125px">

                                			<div class="fieldHolder25">
	                                            <span class="pL10">Residence HIV County</span> 
                                            	<span  class="pL10">
	                                            	<form:select path="NYC_RSH_COUNTY_FIPS"  cssClass="oAdrDlg roundedBrdr newCounty widgetAddressFieldsReadonly" title="RSH County"> 
															<form:option value=""></form:option>
															<form:options items="${sessionScope.countyMap}"/>
													</form:select>
												</span>	                                            
	                                        </div>
	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Residence HIV State</span> 
                                            	<span  class="pL5"><form:input path="NYC_RSH_STATE_CD" id="RSH_STATE_CD" class="standardInputSmallText1 readonly" title="RSH State" maxLength="25"/></span>
                                            </div>
                                             	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Current County</span> 
                                            	<span  class="pL10">
	                                            	<form:select path="NYC_CUR_COUNTY_FIPS" cssClass="oAdrDlg roundedBrdr newCounty widgetAddressFieldsReadonly" title="Current County"> 
															<form:option value=""></form:option>
															<form:options items="${sessionScope.countyMap}"/>
													</form:select>
												</span>
                                            </div>
                                             
	                               			<div class="fieldHolder25">
	                                       		<span class="pL10">Current State</span>
	                                   			<span class="pL5"><form:input path="NYC_CUR_STATE_CD" id="NYC_CUR_STATE_CD" class="standardInputSmallText1 readonly" title="Current State" maxlength="10"/> </span>
	                                       </div>   
 
                                        <br><br><br>
                                			<div class="fieldHolder25">
	                                            <span class="pL10">HIV DX Date</span> 
												<span  class="pL5"><form:input path="NYC_HIV_DX_DT" id="NYC_HIV_DX_DT" class="standardInputSmallText1 readonly" title="HIV_DX_DT" maxLength="25"/></span>	                                            
	                                        </div>
	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">AIDS DX Date</span> 
                                            	<span  class="pL5"><form:input path="NYC_AIDS_DX_DT" id="NYC_AIDS_DX_DT" class="standardInputSmallText1 readonly" title="AIDS_DX_DT" maxLength="25"/></span>
                                            </div>
                                            	                                        
	                               			<div class="fieldHolder25">
	                                       		<span class="pL10">CDC Eligible</span>
	                                   			<span class="pL5"><form:input path="NYC_HIV_AIDS_CDC_ELIG" id="NYC_HIV_AIDS_CDC_ELIG" class="standardInputSmallText1 readonly" title="HIV_AIDS_CDC_ELIG" maxlength="10"/> </span>
	                                       </div>   
	                                       
	                               			<div class="fieldHolder25">
	                                       		<span class="pL10">Status</span>
	                                   			<span class="pL5"><form:input path="NYC_STATUS_FLAG" id="NYC_STATUS_FLAG" class="standardInputSmallText readonly" title="STATUS_FLAG" maxlength="20"/> </span>
	                                       </div>  
	                                        
                                        <br><br><br>
                                
                                			<div class="fieldHolder25">
	                                            <span class="pL10">Ethnicity</span> 
												<span class="pL5"><form:input path="NYC_ETHNICITY" id="NYC_ETHNICITY" class="standardInputSmallText readonly" title="Ethnicity" maxLength="20"/></span>                                          
	                                        </div>
	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">Race</span> 
												<span class="pL5"><form:input path="NYC_RACE" id="race" class="standardInputSmallText readonly" title="Race" maxLength="20"/></span>
                                            </div>
	                                       	                                       
                                </div>                                
                        </div>        
                </div>   
                
				<br>                 
				
		       <div class="container" id="patientInformation">  
                        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Partner Service Message</span>
                        </div>
                        
                        <div style="padding-left:10px">
                        
                                <div class="personInfo">

											 <div  class=" width90Perc pagebreak marginTop20 marginBottom10">
				
												<table class="table table-striped textAlignCenter pB20 width97Perc"> 
													<thead style="font-size:12px;">
														<tr>
															<th>Document Type</th>
															<th>Document Id</th>
															<th>Message Send Date</th>
															<th>Message Return Date</th>
															<th>Return Dispo (Recent)</th>
														</tr>
													</thead>
 													
													<tbody class="textAlignCenter" style="font-size:12px;">
													
									      				<c:forEach var="clusterDocPSMessage" items="${clusterDocVO.clusterDocPSMessageList}">
									      					<tr>
																<td><span class="parentIdSectionValue "><a href="#" onclick="loadDocumentDlgSecond(${clusterDocPSMessage.TRIGGER_DOC_ID}, '${clusterDocPSMessage.DOCUMENT_TYPE}')">${clusterDocPSMessage.DOCUMENT_TYPE}</a></span></td>
																<td><span class="parentIdSectionValue "><a href="#" onclick="loadDocumentDlgSecond(${clusterDocPSMessage.TRIGGER_DOC_ID}, 'PS_MSG_LIST')">${clusterDocPSMessage.DOCUMENT_ID}</a></span></td>
																<td>${clusterDocPSMessage.SEND_DATE}</td>
																<td>${clusterDocPSMessage.RETURN_DATE}</td>
																<td>${clusterDocPSMessage.RECENT_RETURN_DISPO}</td>
															</tr>
																						      				
									      				</c:forEach>
				
													</tbody>
													 
												</table>	
											</div>
							
															            
							                                   
                                </div>
                        </div>        
                </div>                             
                
	<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
	
 	<div id="button-row" class="button-row clear documentButtons"  align="center">

 	
							
<%-- 							<input type="button" id="nonCaseButton" class="createNonCaseDocument button rounded blue effect-3"  value="Create Non Case Doc"  onclick="createNonCaseDocument(${clusterDocVO.DOCUMENT_ID})"/> --%>
						
<%-- 						</c:if> 
		
<%-- 		<c:if test="${clusterDocVO.STATENO != null && clusterDocVO.STATENO != '' &&  clusterDocVO.DISCARD_STATUS == null}"> --%>
							
<%-- 							<input type="button" id="nonCaseButton" class="createNonCaseDocument button rounded blue effect-3"  value="Create Non Case Doc"  onclick="createNonCaseDocument(${clusterDocVO.DOCUMENT_ID})"/> --%>
						
<%-- 						</c:if> --%>
<%--

		 <c:choose>
				<c:when test="${clusterDocVO.STATENO != null && clusterDocVO.STATENO != ''}">
					<c:if test="${userPermissions['100'] != null}" >
							<input type="button" id="editDocument"  class="editDocumentBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="confirmEditDocumentFromStateno()" />
						</c:if>
				</c:when>
			</c:choose>
			
			<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Save" onclick="saveForm()"/>	 
			<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />			
		
	--%>
	</div> 
	<form:hidden path="DEMO_DOCUMENT_ID" />	
	<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />	
	
		<div id="docCreationDemographicDialog">
				<ol>
					<li class="pB5">NYS eHARS person view demographic data</li>
					<li class="pB5">NYC eHARS person view demographic data</li>
					<li class="pB5">If Molecular Cluster, demographic data from first Resistance Lab record</li>
					<li class="pB5">If Molecular Cluster, demographic data from most recent Lab record</li>
				</ol>			
		</div>
	
 
</form:form>

