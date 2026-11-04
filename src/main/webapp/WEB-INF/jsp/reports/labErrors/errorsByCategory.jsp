<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form id="errorReportCategory" name="errorReportCategory">

			<c:forEach items="${labProcessErrors}" var="labProcessError">
				
				<div class="standardInputDiv pT10 clearBoth">
						<div class="width10Perc floatLeft">
							<input type="checkbox" name="errorCode" value="${labProcessError.ERROR_CODE}">
						</div>
						<div class="width85Perc floatLeft">
							${labProcessError.ERROR_DESC}
						</div>
				</div>
			
			</c:forEach>
</form>