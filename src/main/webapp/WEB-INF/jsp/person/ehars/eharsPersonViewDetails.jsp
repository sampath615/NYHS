<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table id="eharsVariables">
	<tr>
		<th id="variable">Variable</th>
		<th id="value">Value</th>
	</tr>
	<c:forEach var="element" items="${eharPersonDataObj}">
		<c:if
			test="${element.key !='RIDR Values'}">
				  <tr>
					<td> <c:out value="${element.key}"/></td>
					<td><c:out value="${element.value}"/></td>
				 </tr>
		</c:if>
	</c:forEach>
</table>
