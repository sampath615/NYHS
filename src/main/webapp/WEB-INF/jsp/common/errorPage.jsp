<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${TRANSACTION_MESSAGE != null}">
		<link href="${staticPath}/css/common/commoncss.css?v=1"  rel="stylesheet" type="text/css" />
		<div class="transactionMessage errorTransactionMessage" ><c:out value="${TRANSACTION_MESSAGE}" /></div>
	</c:when>
	<c:otherwise>
		Error page		
	</c:otherwise>
</c:choose>


