<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta http-equiv="cache-control" content="no-cache" />
        <title>Assignments Home</title>
        

        <%@ include file="../common/common_includes.jsp" %> 
        <%@ include file="../common/commonStylesIncludes.html" %> 
        <%@ include file="../common/MainMenuIncludes.jsp" %>  
        
        <link href="${staticPath}/css/assignments/assignmentshome.css?v=${buildDate}" rel="stylesheet" type="text/css" />
        <link href="${staticPath}/css/dashboard/graphData.css?v=${buildDate}" rel="stylesheet" type="text/css" />
    </head>

    <body>
        <jsp:include page="../common/Menu.jsp"></jsp:include> 
        <jsp:include page="../dashboard/GraphData.jsp"></jsp:include>
        
        
        <c:if test="${sessionScope.confidentialityAndSecurityTrainingDateStatusMessage != ''}" >
			<div id="warningHolder">
				<div class="warning">
					<a title="Close" class="warningCloseImg" onclick="warningClose()"> </a>
					<c:out value="${sessionScope.confidentialityAndSecurityTrainingDateStatusMessage}" />
				</div>
			</div>
		</c:if>

    </body>   
        <%@ include file="../common/commonScriptsIncludes.html" %>
        <script type="text/javascript" src="${staticPath}/js/highcharts/highcharts.js"></script>
        <script type="text/javascript" src="${staticPath}/js/highcharts/exporting.js"></script>
        <script type="text/javascript" src="${staticPath}/js/dashboard/graphData.js?v=${buildDate}"></script>
        <script type="text/javascript" src="${staticPath}/js/assignments/assignmentshome.js?v=${buildDate}"></script>
<!--         <input type="hidden" id="graphsHierarchy" name="graphsHierarchy" value="hiddenValue"> -->
</html>
