<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>




						
						
						

<c:choose>
	<c:when test="${aidaData == null || aidaData == '[]'}">
			<div class="transactionMessage mT50">
				<span class="message">
					No data available
				</span>
			</div>
	</c:when>
	<c:otherwise>
			<table cellspacing="0" border="1" id="aidaDataInTable" class=" bordered">
<!-- 			<table class="table table-striped exportTables pB20 width97Perc expandCollpaseContent"  align="center" >  -->
				<thead style="font-size:12px;">
					<tr>
						<c:forEach items="${headers}" var="columnName">
							<th><c:out value="${columnName}" /></th>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
						<c:forEach items="${aidaData}" var="data">
							<tr>
								<c:forEach items="${headers}" var="columnName">
									<td><c:out value="${data[columnName]}" /></td>
								</c:forEach>
							</tr>
						</c:forEach>
				</tbody>
			</table>
				
	
	</c:otherwise>
</c:choose>
		
		