<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="updatedMessageAccordion" >
					<c:forEach items="${psMessageVO.messageReturnVO.updatedDispositionsList}" var="updatedDispositions" varStatus="currentPartnerIndex">
						<h3 class="updatedMessages"><span class="accordionHeader">Updated PS Investigation Details</span>	</h3>
						<div class="updatedPsMessageContent" style="background-color: transparent; border:1px dotted;" class="container">
								 <div class="pB10" style="width: 100%">
						            	<div  class="floatLeft" style="width: 50%">
							                <span class=" ml10 label33Perc bold">Disposition date: </span>
							                <span class="ml10"><form:input path="messageReturnVO.updatedDispositionsList[${currentPartnerIndex.index}].DISPOSITION_DATE" class="standardInputSmallText" /> </span>
							            </div>    
						                <div class="floatRight" style="width: 50%">
							                <span class=" ml10 label33Perc bold">Disposition Description: </span>
							                <span class="ml10 "><form:input path="messageReturnVO.updatedDispositionsList[${currentPartnerIndex.index}].DISPOSITION_DESC" class="standardInputMedText" style="width:300px" maxlength="10"/> </span>
							            </div>
							            
						            </div>
						            
						            <div class="clearBoth pT10" style="width: 100%">
						            	<div  class="floatLeft" style="width: 50%">
							                <span class=" ml10 label33Perc bold">Updated by: </span>
							                <span class="ml10 "><form:input path="messageReturnVO.updatedDispositionsList[${currentPartnerIndex.index}].UPDATED_BY" class="standardInputSmallText"  maxlength="10" /> </span>
							                
							                
							            </div>    
						                <div class="floatRight" style="width: 50%">
							                <span class=" ml10 label33Perc bold">Updated Date: </span>
							                <fmt:formatDate value="${psMessageVO.messageReturnVO.updatedDispositionsList[currentPartnerIndex.index].UPDATED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedUploadDate" />
						                    <span class="ml10"><form:input path="messageReturnVO.updatedDispositionsList[${currentPartnerIndex.index}].UPDATED_DATE" value="${theFormattedUploadDate}" class="standardInputSmallText" /> </span>
							            </div>
							            
						            </div>
						            
						            
						            <div class="clearBoth pT10  pB10" style="width: 100%">
						            	<div  class="floatLeft" style="width: 100%">
						            		<span class="mL30 ">Comments (Max length is 255 characters) </span><br/>
						  					<div class="mL30">
												<span>  <form:textarea title="Field Notes" maxlength="4000" cols="165" rows="8" path="messageReturnVO.updatedDispositionsList[${currentPartnerIndex.index}].FIELDNOTES" style="width:65%; height:67px; border:1px solid gray;" class="roundedBorder"></form:textarea></span>
							
											</div>
										</div>
						            </div>			
						
						</div>
					
					</c:forEach>
				
				</div>