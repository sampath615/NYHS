<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>

<link href="${staticPath}/css/docs/commonForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/assignments/assignments.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<script src="${staticPath}/js/uploads/nycRejectDoc.js"></script>
<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
<%@ include file="../common/common_includes.jsp" %> 
<%@ include file="../common/commonStylesIncludes.html" %> 
<%@ include file="../common/MainMenuIncludes.jsp" %> 


<div id="NycRejectAccordions" >

<c:forEach items="${nycRejectList}" var="nycRejectDoc" varStatus="statusHNT">
	<c:set var="nycRejectVO" value="${nycRejectDoc}" scope="request" />
	<h3 style="padding:1em" class="displayNone nycRejectAccordion${statusHNT.index}"><span class="accordionHeader" style="left: 2em;top: 50%;margin-top: -8px;position: absolute;">Reject Document</span>	</h3>
	<div class="nycRejectAccordion container" style="background-color: transparent; border:1px dotted;height:auto;" >
		<form:form modelAttribute="nycRejectVO">
			<div class="nycRejectDetails pB10" style="width: 100%; border: 1px dotted gray; height: auto;" class="pT20 pB20">
				<c:choose>
					<c:when test="${nycRejectVO.DOCUMENT_ID != null}">
						
						<%@ include file="NYCRejectDocument.jsp"%>
					</c:when>

				</c:choose>
			</div>
		</form:form>
	</div>
</c:forEach>

</div>

<script type="text/javascript">


var accordionLength = $('.nycRejectAccordion').length; 
if(accordionLength > 1){
    $("#NycRejectAccordions").accordion({active : accordionLength - 1 });  
    $('.nycRejectAccordion').css('height','auto');
} 
</script>