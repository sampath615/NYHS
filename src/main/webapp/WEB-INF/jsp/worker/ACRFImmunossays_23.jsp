<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

		<div style="width:100%; padding:2px; background-color:#DBEAF9" class="container">
				<div style="float:left;">
					<span class="hdr1Font mL5"> HIV Immunoassays</span>
				</div>
				<div style="float:right; margin-right:20px;">
					<span><img  class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addLabTestSec('hivLabImmunossaySec1')" /> </span>
				</div>
		</div>
		<div class="hivLabImmunossaySec1" class="container crfLabTest">			
			<c:forEach items="${acrfFormVO.rawAcrfLabs}" var="hivABTstNonTypDiff" varStatus="statusHNT">
				<c:if test="${ acrfFormVO.rawAcrfLabs[statusHNT.index].parentTestType == '01' && acrfFormVO.rawAcrfLabs[statusHNT.index].testSection == '1'}">
					<c:set var="statusHNT" value="${statusHNT}" scope="request"/>
					<c:set var="testResultSection" value="1_1" scope="request"/>
					<jsp:include page="ACRFImmunoassaysLabSection.jsp" ></jsp:include>
				</c:if>			
			</c:forEach>				
		</div>	
		
	
		
		
		<div style="width:99.9%;" class="container"></div>		

		<div style="width:100%; padding:2px; background-color:#DBEAF9; " class="container">			
			<div style="float:left;">
<!-- 				<span class="hdrFont mL5"> HIV Immunoassays (Differentiating)</span> -->
			</div>			
			<div style="float:right; margin-right:20px;">
				<span><img class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addLabTestSec('hivLabDiffSec1')" /> </span>			
			</div>			
		</div>
		
		<div class="hivLabDiffSec1" class="container crfLabTest" style="padding:2px">
			<c:forEach items="${acrfFormVO.rawAcrfLabs}" var="hivABTstTypDiff" varStatus="statusHT">
				
				<c:if test="${ acrfFormVO.rawAcrfLabs[statusHT.index].parentTestType == '02' && acrfFormVO.rawAcrfLabs[statusHT.index].testSection == '1' }">
					
					<c:set var="statusHT" value="${statusHT}" scope="request"/>
					<c:set var="testResultSection" value="2_1" scope="request"/>
					<jsp:include page="ACRFDifferntImmunoassays.jsp" ></jsp:include>
				</c:if>					
		</c:forEach>						
		</div>
		
		
		<div style="width:99.9%;" class="container"></div>		

		<div style="width:100%; padding:2px; background-color:#DBEAF9; " class="container">			
			<div style="float:left;">
<!-- 				<span class="hdrFont mL5"> HIV Immunoassays (Differentiating)</span> -->
			</div>			
			<div style="float:right; margin-right:20px;">
				<span><img class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addLabTestSec('hivLabDiffSec2')" /> </span>			
			</div>			
		</div>
		
		<div class="hivLabDiffSec2" class="container crfLabTest" style="padding:2px">
			<c:forEach items="${acrfFormVO.rawAcrfLabs}" var="hivABTstTypDiff" varStatus="statusHT">	
				<c:if test="${ acrfFormVO.rawAcrfLabs[statusHT.index].parentTestType == '02' && acrfFormVO.rawAcrfLabs[statusHT.index].testSection == '2' }">
				<c:set var="statusHT" value="${statusHT}" scope="request"/>
				<c:set var="testResultSection" value="2_2" scope="request"/>
				<jsp:include page="ACRFDifferntImmunoassays.jsp" ></jsp:include>
			</c:if>					
		</c:forEach>						
		</div>
		
		<div style="width:99.9%;" class="container"></div>		

		<div style="width:100%; padding:2px; background-color:#DBEAF9; " class="container">			
			<div style="float:left;">
<!-- 				<span class="hdrFont mL5"> HIV Immunoassays (Differentiating)</span> -->
			</div>			
			<div style="float:right; margin-right:20px;">
				<span><img class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addLabTestSec('hivLabDiffSec3')" /> </span>			
			</div>			
		</div>
		
		<div class="hivLabDiffSec3" class="container crfLabTest" style="padding:2px">
			<c:forEach items="${acrfFormVO.rawAcrfLabs}" var="hivABTstTypDiff" varStatus="statusHT">	
				<c:if test="${ acrfFormVO.rawAcrfLabs[statusHT.index].parentTestType == '02' && acrfFormVO.rawAcrfLabs[statusHT.index].testSection == '3' }">
				<c:set var="statusHT" value="${statusHT}" scope="request"/>
				<c:set var="testResultSection" value="2_3" scope="request"/>
				<jsp:include page="ACRFDifferntImmunoassays.jsp" ></jsp:include>
			</c:if>					
		</c:forEach>						
		</div>
		
		
		<div style="width:100%; padding:2px; background-color:#DBEAF9" class="container">
				<div style="float:left;">
<!-- 					<span class="hdr1Font mL5"> HIV Immunoassays (Non-differentiating)</span> -->
				</div>
				<div style="float:right; margin-right:20px;">
					<span><img  class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addLabTestSec('hivLabImmunossaySec2')" /> </span>
				</div>
		</div>
		<div class="hivLabImmunossaySec2" class="container crfLabTest">			
			<c:forEach items="${acrfFormVO.rawAcrfLabs}" var="hivABTstNonTypDiff" varStatus="statusHNT">
				<c:if test="${ acrfFormVO.rawAcrfLabs[statusHNT.index].parentTestType == '01' && acrfFormVO.rawAcrfLabs[statusHNT.index].testSection == '2'}">
					<c:set var="statusHNT" value="${statusHNT}" scope="request"/>
					<c:set var="testResultSection" value="1_2" scope="request"/>
					<jsp:include page="ACRFImmunoassaysLabSection.jsp" ></jsp:include>
				</c:if>			
			</c:forEach>				
		</div>	
		
		
		