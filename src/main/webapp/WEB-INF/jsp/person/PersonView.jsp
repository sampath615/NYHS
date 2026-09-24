<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="personViewNumber" value="${param.personViewNumber}" />
<script type="text/javascript" src="${staticPath}/js/common/datePicker/bootstrap-datetimepicker.js"></script>
<c:choose>
	<c:when test="${personViewNumber =='_badlyMerged' }">
		<script type="text/javascript">
			var documentTimelineData_badlyMerged = '${documentTimeline}';
			documentTimelineData_badlyMerged = documentTimelineData_badlyMerged.replace(/=/g,':').replace('p_doc_timeline','"p_doc_timeline"');
		</script>	
	</c:when>
</c:choose>
<div id='floatContent<c:out value="${personViewNumber}" />'  style="background-color: transparent;" class="personDetailsAndRiskContent">
	<%@ include file="PersonPersonelInfoView.jsp" %>
	
	<div class="clearBoth divHeadingCls"><span class="ui-icon ui-icon-plus personRiskNavigationIcon${personViewNumber}" onclick='loadPersonRisk("${personViewObj.stateNo}", "${personViewNumber}"  )'></span>Person Risks</div>

	<div  class="personDetailsDiv" id="personRisksDiv${personViewNumber}">
		
	</div>					
</div>
	
<div id="multiValueStringDialog">
	<table id="multiValueStringTable"></table>
</div>