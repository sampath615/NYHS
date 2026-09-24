<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:forEach items="${stdMessageListVO}" var="obj">
	<c:set var="messageVO" value="${obj.value}" />
    <div id="${messageVO.MESSAGEID}" class="secureMessages">
        <span> 
            <div id="sender">
                <b> <c:out value="${messageVO.RECEIVER}">  </c:out> </b>
            </div>
            <div class="messageBody" style="width:100%">
                <c:out value="${fn:substring(messageVO.MESSAGEBODY,0,15)}">  </c:out>
            </div>
        </span>
    </div>
</c:forEach>

<script type="text/javascript">
	var messagesJSONObj = ${stdMessageListVO};
</script>