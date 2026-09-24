<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- This jsp belongs to "ACRF Form contains PT Info, PT Hist, Lab, TTH" --%> 



<div id="nav-section1">
			  <div id="ACRFForm1" style="width:100%; height: 580px; padding:0; overflow-y:scroll;" class="chngFont">
			  		<jsp:include page="ACRFForm1.jsp"></jsp:include>
			  </div>
		  </div>
		  <div id="nav-section2">
			  <div id="ACRFForm2" style="width:100%; height: 580px; padding:0; overflow-y:scroll;" class="chngFont">
					<jsp:include page="ACRFForm2.jsp"></jsp:include> 
					 
			  </div>
		  </div>
		  <div id="nav-section3">
			  <div id="ACRFForm3" style="width:100%; height: 580px; padding:0; overflow-y:scroll;" class="chngFont">
			  		<span class="mL10 hdrFont"> IX. Laboratory Data  (record additional tests and tests not specified below in Comments)(record all dates as mm/dd/yyyy)  
					</span>
					<div class="roundedBorder mL10" style="width:98%; background:#DDDDDD;" >
						<div id="labDataContainer" class="container">
						<c:choose>
							<c:when test="${acrfRevison < 2020}">
								<%@ include file="../worker/ACRFImmunossays.jsp" %>
								<jsp:include page="ACRFForm3.jsp"></jsp:include>
							</c:when>
							<c:otherwise>
								<jsp:include page="../worker/ACRFImmunossays_23.jsp"></jsp:include>
								<jsp:include page="../worker/ACRFDetectionTest_23.jsp"></jsp:include>
								<jsp:include page="ACRFForm3_23.jsp"></jsp:include>
								<jsp:include page="ACRFFormDocTest3_23.jsp"></jsp:include>
								
							</c:otherwise>
						
						
						</c:choose>
						</div>
						
					</div>
					
					
					<c:if test="${acrfRevison < 2018}"> 
	
						<span class="mL10 hdrFont"> Clinical (record all dates as mm/dd/yyyy) 
						</span>
						<div class="roundedBorder medfont mL10" style="width:98%; background:#DDDDDD" >
							
							<%@ include file="../worker/ACRFOpportunisticIllnesses.jsp" %>
								
							<div style="width:99.9%; border:1px dotted;" class="container"></div>
							
							<div style="float:left; width:99%; padding:5px; margin-left:5px; margin-right:5px;"> 
								<span> &dagger; If TB selected above, indicate RVCT Case Number: <form:input path="rawAcrfDoc.tbRvctCaseNumber" id="rawAcrfDoc_tbRvctCaseNumber" class="standardInputText" maxlength="30" title="RVCT Case Number"/></span>
							</div>
							
							<div style="width:99.9%;" class="container"></div>
							
						</div>
					</c:if>  
			  </div>
			  
		  </div>
		  <div id="nav-section4">
			  <div id="ACRFForm4" style="width:100%; height: 580px; padding:0; overflow-y:scroll;" class="chngFont">
					<jsp:include page="ACRFForm4.jsp"></jsp:include> 
			  </div>
		  </div> 
		  <c:if test="${param.documentType != 'ACRF'}">
			  <div id="nav-section5">
				  <div id="ACRFForm5" style="width:100%; height: 580px; padding:0; overflow-y:scroll;" class="chngFont">
						<jsp:include page="ACRFForm5.jsp"></jsp:include> 
				  </div>
			  </div>
		  </c:if>