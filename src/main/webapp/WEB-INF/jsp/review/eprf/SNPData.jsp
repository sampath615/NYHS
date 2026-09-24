<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<span class="floatRight bold inlineEditButton"><a href="snpFeedbackFile.xlsx" style="text-decoration:none;color:white">Download</a></span>
	
	  <br/><br/><br/>
</div>
							
<c:choose>
<c:when test="${docList != null && docList != '[]'}">
				<table class=" bordered" border="1" cellspacing="0" id="documentReviewTable">
						
							<tr>
								<th>CIN</th>
								<th>CONFIRMED</th>
							</tr>
							
							<c:forEach items="${docList}" var="document">
								<tr>
									<td>${document.MEDICAID_NUMBER}</td>
									<td>${document.ELIGIBILITY}</td>
								</tr>
							
							</c:forEach>
						
						</table>
</c:when>
<c:otherwise>
		<span class="message">
				No Information available
		</span>
</c:otherwise>

</c:choose>