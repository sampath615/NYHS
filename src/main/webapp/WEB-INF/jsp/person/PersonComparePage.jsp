<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:forEach var="personObj" varStatus="varStatus"
	items="${personObjects}" >
	<c:set var="personViewNumber" value="${varStatus.index+1}" />
	<c:set var="personViewObj" value="${personObj.personViewVO}"
		scope="request" />
	<c:set var="personRisksObj" value="${personObj.personRisksVO}"
		scope="request" />
	<c:set var="statenoHistoryObj" value="${personObj.statenoHistory}"
		scope="request" />
	
	<c:if test="${personViewObj != null}">
		<div id="personView<c:out value="${personViewNumber}" />"
			class="personViewData">
			<div id='floatContent<c:out value="${personViewNumber}" />'
				style="background-color: transparent; width: 1210px">
				<%@ include file="PersonPersonelInfoView.jsp"%>
				
				<div class="divHeadingCls clearBoth"><span class="ui-icon ui-icon-plus personRiskNavigationIcon${personViewNumber}" onclick='loadPersonRisk("${personViewObj.stateNo}", "${personViewNumber}", ".personRiskNavigationIcon${personViewNumber}"  )'></span>Person Risks</div>
				<div  class="personDetailsDiv" id="personRisksDiv${personViewNumber}">
		
				</div>
				
			</div>
		</div>
	</c:if>
</c:forEach>