<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form:errors path="*" cssClass="errorblock" element="div" />
    <div id="updateDocumentTransactionMessage" class="transactionMessage"><span class="message"></span></div>
	<div id="" style="background-color: transparent">
                <div style="padding-left:30%; " class="headingCls">
                                NYC Field Surveillance Investigation Disposition
                </div>
                <div style="padding-left:15%; ">
                               <span class="mL10 bold">All NYC field investigations are assigned a disposition based on the information obtained during the investigation. This data is sent from NYC to BHAE quarterly.</span>
                </div>
     </div>
     
			<div class="container pB10 w100 pT10">
				<div  class="floatRight">
		            <span class="mL10 bold">Document ID: </span>
					<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="10" readonly="true"/></span>
	        	 </div>		            
	   		</div>			
		
		       <div class="container" id="patientInformation">  
                        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Document Information</span>
                        </div>
                        
                        <div style="padding-left:10px;padding-top:10px">
                                <div class="personInfo" style="height:60px">
                                
                                			<div class="fieldHolder25">
	                                            <span class="pL10">STATENO</span> 
	                                            <span class="pL5"><form:input path="rejectDocumentVO.STATENO" class="standardInputSmallText readonly" title="STATENO" maxLength="20"/></span>
	                                        </div>
	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">NYC CITYNO</span> 
                                            	<span  class="pL5"><form:input path="rejectDocumentVO.NYCNUMBER" class="standardInputSmallText readonly" title="NYCNUMBER" maxLength="20"/></span>
                                            </div>	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">REJECT DOCUMENTID</span> 
                                            	<span  class="pL5"><form:input path="rejectDocumentVO.REJECT_DOCUMENT_ID" class="standardInputSmallText readonly" title="Reject Document Id" maxLength="20"/></span>
                                            </div>
                                             
                                             <br><br><br>
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">DISPOSITION</span> 
                                            	<span  class="pL5"><form:input path="rejectDocumentVO.DISPOSITION_DESC_SHORT" class="standardInputSmallText readonly" title="Disposition" maxLength="30"/></span>
                                            </div>
                                            
		  									<div class="fieldHolder75">
												<span class="pL10">DISPOSITION DESC</span>
												<span class="pL5">  <form:textarea title="Disposition desc" maxlength="120" cols="60" rows="1" path="rejectDocumentVO.DISPOSITION_DESC" style="width:55%; height:20px; border:1px solid gray;" class="roundedBorder" readonly="true"></form:textarea></span>											
				                                </div>
			                                </div>    
			                                
                                             <br><br>
                                             
		  									<div class="fieldHolder15">
												<span class="pL10">STATUS NOTES</span> 
			                                </div>       	                                        
		  									<div class="fieldHolder75">
												<span class="pL5">  <form:textarea title="Status Notes" maxlength="200" cols="60" rows="4" path="rejectDocumentVO.STATUSNOTES" style="width:80%; height:67px; border:1px solid gray;" class="roundedBorder" readonly="true"></form:textarea></span>											                                   	
			                                </div>        
			                                
                                             <br><br><br><br><br><br>
                                             
		  									<div class="fieldHolder15">
												<span class="pL10">STATUS NON CASE</span> 
			                                </div>       	                                        
		  									<div class="fieldHolder75">
												<span class="pL5">  <form:textarea title="Status Non Case" maxlength="200" cols="60" rows="4" path="rejectDocumentVO.STATUSNONCASE" style="width:80%; height:67px; border:1px solid gray;" class="roundedBorder" readonly="true"></form:textarea></span>											                                   	
			                                </div>   
			                                
			                                <br><br><br><br><br><br>
			                                
                                			<div class="fieldHolder49">
	                                            <span class="pL10">DATE STATUS WAS UPDATED BY NYC</span> 
	                                            <span class="pL5"><form:input path="rejectDocumentVO.STATUS_UPDATE_DATE" class="standardInputSmallText readonly" title="DATE STATUS WAS UPDATED BY NYC" maxLength="20"/></span>
	                                        </div>
	                                        
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">STATENO2</span> 
                                            	<span  class="pL5"><form:input path="rejectDocumentVO.STATENO2" class="standardInputSmallText readonly" title="STATENO2" maxLength="20"/></span>
                                            </div>
			                                
			                                <br><br>
			                                                                            	                                        
	                                        <div class="fieldHolder49">
                                            	<span class="pL10">NAME OF FILE</span> 
                                            	<span  class="pL5"><form:input path="rejectDocumentVO.UPLOAD_FILENAME" class="standardInputLongText readonly" title="Name OF File" maxLength="50"/></span>
                                            </div>		
                                            
	                                        <div class="fieldHolder25">
                                            	<span class="pL10">DATE ADDED TO NYEHMS</span> 
                                            	<span  class="pL5"><form:input path="rejectDocumentVO.UPLOAD_DATE" class="standardInputSmallText readonly" title="Date Added to NYEHMS" maxLength="20"/></span>
                                            </div>	 
                                                                                       	                                     
                                             <br><br>
                                             
		  									<div class="fieldHolder15">
												<span class="pL10">COMMENTS</span> 
			                                </div>       	                                        
		  									<div class="fieldHolder75">
												<span class="pL5">  <form:textarea id="comments${statusHNT.index}" title="BHAE Comments" maxlength="200" cols="60" rows="4" path="rejectDocumentVO.COMMENTS" style="width:80%; height:67px; border:1px solid gray;" class="roundedBorder"></form:textarea></span>
												<div class="v_error comments${statusHNT.index}"></div>											                                   	
			                                </div>                                  
			                 </div>
                                
                        </div>        
				<br>                 
				
	<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
	 <div id="button-row" class="button-row documentButtons center">
	 				
					<c:if test="${param.hasEditFunctionality == true}">
						<input type="button" id="eButton"  class="editBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="customDocumentEditViewerStyles()" />
					</c:if>
						<c:choose>
							<c:when  test="${param.hasDeleteFunctionality == true}" >	
									<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
									<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
							</c:when>
							<c:when test="${nycRejectVO.rejectDocumentVO.STATENO != null && nycRejectVO.rejectDocumentVO.STATENO != ''}">
									<c:if test="${userPermissions['100'] != null}" >
										<input type="button" id="editDocument"  class="editDocumentBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="editDocumentFromStatenoNYCReject('${statusHNT.index}')" />
									</c:if>
							</c:when>
						</c:choose>
						

				<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Save" 
										onclick="saveNYCRejectForm('${nycRejectVO.rejectDocumentVO.DOCUMENT_ID}','${nycRejectVO.rejectDocumentVO.STATENO}', '${statusHNT.index}')"/>
				<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />				
	</div>		
	
        <br>
		<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />	
	
