<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="staticPath" value="${sessionScope.staticPath}"/>
<c:set var="countyMap" value="${sessionScope.countyMap}"/>
<c:set var="userId" value="${sessionScope.UserObj.userId}"/>
<c:set var="workerId" value="${sessionScope.UserObj.workerId}"/>

<%-- Initializing it to dummy value --%>
<c:if test="${userPermissions == null || userPermissions == ''}">
	<c:set var="userPermissions" value = "1" />
</c:if>

<script type="text/javascript"> 
	var contextPath="${contextPath}";
	var staticPath="${staticPath}";
	var countyMap = "${countyMap}";
	var userId="${userId}";
	var workerId="${workerId}";
	var buildDate = "${buildDate}";
	countyMap = countyMap.replace(/=/g,'":"').replace(/, /g,', "').replace(/,/g,'", ').replace(/{/g,'{"').replace(/}/g,'"}');
	var userPermissionsJSON = ${userPermissions};
</script>



		<div id="triggerPsMessageDlg" class="displayNone">
			<div id="psMessageInputData">
				  	
			</div>
		</div>