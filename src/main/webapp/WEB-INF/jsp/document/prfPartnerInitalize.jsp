<div id="prfPartnerContent">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form modelAttribute="stdPRFDocVO" id="stdPRFDocVO" cssClass="documentDescription">		

			<c:if test="${stdPRFDocVO.partnerList != null && stdPRFDocVO.partnerList != '[]' }">
						<c:forEach items="${stdPRFDocVO.partnerList}" var="ptnrDocumentVo" varStatus="ptnrCounter">
							<c:set var="currentPartnerIndex" value="${partnerIndex}" />
							
							<c:choose>
								<c:when test="${param.isNewPrfForm == 'Y'}">
									<%@include file="PartnerDetails2023.jsp" %>	
								</c:when>
								<c:otherwise>
									<%@include file="PartnerDetails2016.jsp" %>
								</c:otherwise>
							</c:choose>
						</c:forEach>
			</c:if>
</form:form>															



</div>