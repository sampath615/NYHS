<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:forEach items="${stdMessageListVO.listOfMessages}" var="messageId">
	
	<c:set var="messageVO" value="${stdMessageListVO[messageId]}" />
	
    <div id="${messageVO.MESSAGEID}" class="secureMessages">
        <span>
            <div class="message">
            
            
                <span class="boldFont senderName">
                	<c:choose>
            		<c:when test="${messageType =='RECIEVER'}">    
                		<c:out value="${messageVO.SENDER}"></c:out>
                	</c:when>
                	<c:otherwise>
                		<c:out value="${messageVO.RECEIVER}"></c:out>
                	</c:otherwise>
                	</c:choose>
                
                </span>
               	<c:if test="${messageType =='RECIEVER'}">    
               		<span class="readStatus"><c:if test="${messageVO.ISREAD =='N'}"><img class="unreadImg" src="${staticPath}/images/icons/dot_message.gif"/></c:if> </span>
               	</c:if>
                
            </div>
            <div class="messageBody">
                <c:out value="${fn:substring(messageVO.MESSAGEBODY,0,15)}">  </c:out>
            </div>
        </span>
    </div>
    
    
</c:forEach>

<script type="text/javascript"> 
 	var messagesJSONObj = ${stdMessageListVO};
</script>