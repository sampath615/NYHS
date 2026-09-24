<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
		
		<div style="width:99.9%;" class="container"></div>
		
		<div style="width:100%; padding:2px; background-color:#DBEAF9;" class="container">			
				<div style="float:left;">
					<span class="hdrFont mL5"> HIV Detection Tests</span>
				</div>			
				<div style="float:right; margin-right:20px;">
					<span><img class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addLabTestSec('hivLabDetectionSec1')" /> </span>
				</div>	
		</div>
		
		<div id="hivLabDetectionSec1" class="container crfLabTest">
			<c:forEach items="${acrfFormVO.rawAcrfLabs}" var="hivABTstNonTypDiff" varStatus="statusQL">
			<c:if test="${ acrfFormVO.rawAcrfLabs[statusQL.index].parentTestType == '03' && acrfFormVO.rawAcrfLabs[statusQL.index].testSection == '1'}">
				<c:set var="statusQL" value="${statusQL}" scope="request"/>
				<c:set var="testResultSection" value="3_1" scope="request"/>
				<jsp:include page="ACRFDetectionTestSection.jsp" ></jsp:include>
			</c:if>
			</c:forEach>			
			
		</div>	
		
		<div style="width:99.9%;" class="container"></div>
		
		<div style="width:100%; padding:2px; background-color:#DBEAF9;" class="container">			
				<div style="float:left;">
<!-- 					<span class="hdrFont mL5"> HIV Detection Tests (Qualitative)</span> -->
				</div>			
				<div style="float:right; margin-right:20px;">
					<span><img class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addLabTestSec('hivLabDetectionSec2')" /> </span>
				</div>	
		</div>
		
		<div id="hivLabDetectionSec2" class="container crfLabTest">
			<c:forEach items="${acrfFormVO.rawAcrfLabs}" var="hivABTstNonTypDiff" varStatus="statusQL">
			<c:if test="${ acrfFormVO.rawAcrfLabs[statusQL.index].parentTestType == '03' && acrfFormVO.rawAcrfLabs[statusQL.index].testSection == '2'}">
				<c:set var="statusQL" value="${statusQL}" scope="request"/>
				<c:set var="testResultSection" value="3_2" scope="request"/>
				<jsp:include page="ACRFDetectionTestSection.jsp" ></jsp:include>
			</c:if>
			</c:forEach>			
			
		</div>	
		
		
		<div style="width:99.9%;" class="container"></div>
		
		<div style="width:100%; padding:2px; background-color:#DBEAF9;" class="container">			
				<div style="float:left;">
<!-- 					<span class="hdrFont mL5"> HIV Detection Tests (Qualitative)</span> -->
				</div>			
				<div style="float:right; margin-right:20px;">
					<span><img class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addLabTestSec('hivLabDetectionSec3')" /> </span>
				</div>	
		</div>
		
		<div id="hivLabDetectionSec3" class="container crfLabTest">
			<c:forEach items="${acrfFormVO.rawAcrfLabs}" var="hivABTstNonTypDiff" varStatus="statusQL">
			<c:if test="${ acrfFormVO.rawAcrfLabs[statusQL.index].parentTestType == '03' && acrfFormVO.rawAcrfLabs[statusQL.index].testSection == '3'}">
				<c:set var="statusQL" value="${statusQL}" scope="request"/>
				<c:set var="testResultSection" value="3_3" scope="request"/>
				<jsp:include page="ACRFDetectionTestSection.jsp" ></jsp:include>
			</c:if>
			</c:forEach>			
			
		</div>	