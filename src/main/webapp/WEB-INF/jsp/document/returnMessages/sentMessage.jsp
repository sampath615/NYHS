<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	  <div class="pB10" style="width: 100%">
      	
      </div>

<div class="container w100 nonRawEditFields">
      <div class="pB10" style="width: 100%">
      		<div  class="floatLeft" style="width: 50%">
           		<span class=" ml10 label33Perc bold">Document Id </span>
           
              	<span class="ml10"><form:input path="DOCUMENT_ID" class="standardInputSmallText" style="width:180px" /> </span>
       		</div>    
          	<div class="floatRight" style="width: 50%">
           		<span class=" ml10 label180mrg bold">Message Type </span>
				<span class="ml10"><form:input path="messageSentVO.TRIGGER_MSG_DESC" class="standardInputText" style="width:280px" /> </span>		
      	  </div>
      </div>
      
       <div class="clearBoth pT10  pB10" style="width: 100%">
	      <div  class="floatLeft" style="width: 50%">
	           <span class=" ml10 label33Perc bold">Message Entered Date </span>
	           <fmt:formatDate value="${psMessageVO.messageSentVO.MESSAGE_ENTERED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedMessageEnteredDate" />
	              <span class="ml10"><form:input path="messageSentVO.MESSAGE_ENTERED_DATE" class="standardInputSmallText" style="width:180px" value="${theFormattedMessageEnteredDate}"/> </span>
	       </div>       
          <div class="floatRight" style="width: 50%">
           	<span class=" ml10 label33Perc bold">CDESS Case Key </span>
         	  <span class="ml10 "><form:input path="messageSentVO.CDESSCASEKEY" class="standardInputSmallText" style="width:180px" maxlength="10"/> </span>
      	  </div>
       </div>
      
      <div class="clearBoth pT10  pB10" style="width: 100%">
      	<div  class="floatLeft" style="width: 50%">
           <span class=" ml10 label33Perc bold">Message Received Date </span>
           <fmt:formatDate value="${psMessageVO.messageSentVO.MESSAGE_RECEIVED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedMessageReceivedDate" />
           <span class="ml10"><form:input path="messageSentVO.MESSAGE_RECEIVED_DATE" class="standardInputSmallText" style="width:180px" value="${theFormattedMessageReceivedDate}"/> </span>
       </div>    
          <div class="floatRight" style="width: 50%">
           <span class=" ml10 label33Perc bold">CDESS Dismissed </span>
           <span class="ml10 "><form:input path="messageSentVO.CDESSDISMISSED" class="standardInputSmallText" style="width:180px" maxlength="10"/> </span>
        </div>
       </div>
       
       <div class="clearBoth pT10  pB10" style="width: 100%">
      		<div  class="floatLeft" style="width: 50%">
           		<span class=" ml10 label33Perc bold">Triggered By </span>
           		<c:choose>
           			<c:when test="${psMessageVO.messageSentVO.TRIGGER_BY == '900'}">
           				<span class="ml10"><form:input path="messageSentVO.TRIGGER_BY" class="standardInputSmallText" style="width:180px" value="NYEHMS"/> </span>
           			</c:when>
           			<c:otherwise>
           				<span class="ml10"><form:input path="messageSentVO.TRIGGER_BY" class="standardInputSmallText" style="width:180px"/> </span>	
           			</c:otherwise>
           		</c:choose>
           		
       		</div>    
          	<div class="floatRight" style="width: 50%">
          
	           <c:choose>
					<c:when test="${psMessageVO.messageSentVO.PROCESS_IND == 'D'}">
						       <span class=" ml10 label33Perc bold">Message Status </span>
					           <span class="ml10">Deleted </span>
					</c:when>
					<c:when test="${psMessageVO.messageSentVO.PROCESS_IND == 'R'}">
						       <span class=" ml10 label33Perc bold">Message Status </span>
					           <span class="ml10">In Review </span>
					</c:when>
				</c:choose>
        	</div>
       </div>
       
       
		
	     
       <c:if test="${psMessageVO.messageSentVO.REVIEW_DATE != null}">
       		 <div class="clearBoth pT10  pB10" style="width: 100%">
		      	<div  class="floatLeft" style="width: 50%">
		           <span class=" ml10 label33Perc bold">Review UID </span>
		           <span class="ml10"><form:input path="messageSentVO.REVIEW_UID" class="standardInputSmallText" style="width:180px" /> </span>
		       </div>    
		       <div class="floatRight" style="width: 50%">
		           <span class=" ml10 label33Perc bold">Review Date </span>
		           <span class="ml10 "><form:input path="messageSentVO.REVIEW_DATE" class="standardInputSmallText" style="width:180px" maxlength="10"/> </span>
		        </div>
		       </div>
		       
		       
		       <div class="clearBoth pT10  pB10" style="width: 100%">
		      	 
		       
		       	<c:choose>
						<c:when test="${psMessageVO.messageSentVO.PROCESS_IND == 'Y'}">
								<div  class="floatLeft" style="width: 50%">
						           <span class=" ml10 label33Perc bold">Message Status </span>
						           <span class="ml10">Reviewed, Message Sent </span>
						       </div>
						</c:when>
						<c:when test="${psMessageVO.messageSentVO.PROCESS_IND == 'R_X'}">
								<div  class="floatLeft" style="width: 50%">
						           <span class=" ml10 label33Perc bold">Message Status </span>
						           <span class="ml10">Reviewed, No Message Sent </span>
						       </div>
						</c:when>
					</c:choose>
		       
		       
		         
<!-- 		       <div class="floatRight" style="width: 50%"> -->
<!-- 		           <span class=" ml10 label33Perc bold">CDESS Dismissed </span> -->
<%-- 		           <span class="ml10 "><form:input path="messageSentVO.CDESSDISMISSED" class="standardInputSmallText" style="width:180px" maxlength="10"/> </span> --%>
<!-- 		        </div> -->
		       </div>
		       
		       
       </c:if>
</div>

