

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${scannedFileNames != '[]' && scannedFileNames != ''}">
	<div id="scannedDocumentFileNames">
		<c:forEach items="${scannedFileNames}" var="scannedDocument">
			
			<span class="scanFileName clearBoth">
				<a href="javascript:loadScannedDocumentInDoc('<c:out value="${scannedDocument}" />')" 
				
				style="color:#006890; font-weight:bold; margin:10px; float:none; text-decoration:none; white-space:nowrap;font-size:17px;display:block"
				
				><c:out value="${scannedDocument}" /></a>
			</span>
		</c:forEach>
	</div>
</c:if>