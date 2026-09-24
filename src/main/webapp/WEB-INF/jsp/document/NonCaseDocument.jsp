<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>

<link href="${staticPath}/css/docs/commonForm.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/docs/nycijrDoc.css?v=${buildDate}" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${staticPath}/css/reports/toggles-light.css" type="text/css"/>


<style>
		/*
	 * @author: gxl13
	 * This css file is used for NYC_IJR Document Look and Feel.
	 */
	.pTRBL10{
	    padding: 10px 10px 0px 10px;
	}
	
	.b1{
	    border-style: dotted;
	    border-width: 1px;
	    margin: 5px;
	}
	
	.fieldHolder20{
	    float: left;
	    width: 20%;
	}
	
	.birthCountyHWIJR{
		height:63px;
	    width:266px;
	}
	
	.ethnicityHWIJR{
		height:90px;
	    width:266px;
	}
	
	.riskHWIJR{
	    height:372px;
	    width:412px;
	    float:left;
	}
	
	.reportHWIJR{
	    height:362px;
	    width:429px;
	    float:right;
	}
	
	.pL10{
		padding-left:10px;
	}
	
	#StdNYCEHARSDocVO .labelunderneath2{
		padding-left:120px;
	}
	
	#StdNYCEHARSDocVO .labelunderneath1{
		padding-left:175px;
	}
	
	#StdNYCEHARSDocVO .labelunderneath3{
		padding-left:160px;
	}
	#StdNYCEHARSDocVO .labelunderneath5{
		padding-left:245px;
	}
	 
	 #StdNYCEHARSDocVO .labelunderneath6{
	 	padding-left:205px;
	 }
	 
	.mR50{
		margin-right:50px;
	}
	
	.pL20{
		padding-left: 20px;
	}
	
	.pT20{
		padding-top: 20px;
	}
	
	.mT10{
		margin-top: 10px;
	}
	
	.riskLabelFieldLeft{
		float: left;
	    width: 20%;
	}
	
	.riskLabelFieldRight{
		float: left;
	    width: 13%;
	}
	.raceHW {
	    height: 165px;
	    width: 266px;
	}
	#patientInformation{ padding: 0}

</style>


<form:form modelAttribute="nonCaseDocVO" id="nonCaseDocVO" cssClass="documentFormVO documentDescription" >

<div id="labDocTab" style="height:auto">
   <div style="padding-left:43%; " class="headingCls">
                NON CASE DOCUMENT
        </div>
    
<!--     <div class="container pB10 w100 mT10"> -->
<!--             <div style="float:left; width: 40%"> -->
<!-- 	            <div> -->
<%-- 	                <span class="ml10"> <spring:message code="label.stateNo" />: </span> --%>
<%-- 	                <span class="ml10" style=""><form:input path="STATENO" class="standardInputSmallText readonly" /> </span> --%>
<!-- 	            </div>     -->
<!--             </div> -->
<!--             <div style="float:left; width: 30%"> -->
<!--  	         	<div> -->
<!--  	                <span class="mL10 bold">eHARS Import Date: </span> -->
<!-- 					<span class="ml10 "> -->
<%-- 						<fmt:formatDate value="${StdNYCEHARSDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" /> --%>
<%-- 						<form:input path="EHARS_IMPORT_DATE" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/> --%>
<!-- 					</span> -->
<!--  	            </div>  -->
  
<!--             </div> -->
<!--             <div style="float:left; width: 30%; text-align:right"> -->
<!--  	            <div> -->
<!--  	                <span class=" ml10 bold">	Document ID: </span> -->
<%--  			 		<span class="ml10"><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/> --%>
 			 			
<!--  			 		</span> -->
<!--  	           </div>                   -->
<!--  	         </div>      -->
<!--     </div> -->
<%--     <jsp:include page="statenoDetails.jsp"></jsp:include> --%>
				
						<div style="width:90%; height:auto; margin:0px auto" >

								
								<div style="border:1px dotted; padding : 0 30 30 30; height:auto">
									<div class=" clearBoth pT10 ">
											<div class="floatLeft width25Perc">
											<span class="font10pt bold">Flag Active</span>
											</div>
											<div class="floatLeft  bold"  style="color:black">
											
<%-- 												<form:select path="FLAG_ACTIVE" id="FLAG_ACTIVE" class="required" title="Confirmatory Result Reported"> --%>
<%-- 													<form:option value="">Select</form:option> --%>
<%-- 													<form:option value="Y">Yes</form:option> --%>
<%-- 													<form:option value="N">No</form:option> --%>
												
<%-- 												</form:select> --%>
												
												<div class="toggle-light">
												<div class="nonCaseToggle"  data-checkbox=nonCaseFlagActiveStatus tabindex="15" ></div>
												</div>
												
												<input type=checkbox disabled=disabled class="nonCaseFlagActiveStatus displayNone">
											
											
											</div>
									</div>
									<div class=" clearBoth pT10 ">
											<div class="floatLeft width25Perc">
											<span class="font10pt bold">Reason</span>
											</div>
											<div class="floatLeft width75Perc bold"  style="color:black">
											<div>
													<div class="floatLeft width45Perc bold"  style="color:black">
														<span><c:out value="${nonCaseDocVO.REASON_DESC}" /></span>
													</div>
											</div>
											</div>
									</div>
									<div class=" clearBoth pT10 pB10">
											<div class="floatLeft width25Perc">
											<span class="font10pt bold">Comments</span>
											</div>
											<div class="floatLeft width75Perc bold"  style="color:black">
											<span>
											
												
<%-- 												(<span class=""><c:out value="${nonCaseDocVO.CREATE_ID}" /></span>            <span class="pL30"><c:out value="${nonCaseDocVO.CREATE_DATE}" /></span>)<span class="pL50"> <c:out value="${nonCaseDocVO.COMMENTS}" /></span> --%>

													<c:out value="${nonCaseDocVO.COMMENTS}" escapeXml="false"/>
											
											</span>
											</div>
									</div>
									
									<div class=" clearBoth  ">
									</div>
							
							
								</div>
								
							
							</div>
							
							
								<div style="width:90%; height:auto; margin:0px auto" >
										<div style="border:1px dotted; padding : 0 30 30 30" class="mT20">
											<div class=" clearBoth pT10 ">
													<div class="floatLeft width15Perc">
													<span class="font10pt bold">Add Comments</span>
													</div>
													<div class="floatLeft width40Perc  "  >
														<span><textarea rows="3" cols="60" id="addNonCaseComments" name="addNonCaseComments" maxlength="4000" class="noDisabled"></textarea></span>
													</div>
													<div class="floatLeft width25Perc "  >
														<span>
															<a class="actionButtons a_demo_search a_demo_continue"  id="addNonCaseCommentsBtn" onClick="addCommentsToNonCaseDoc(${nonCaseDocVO.TRIGGERING_DOC_ID})" style="color: #FFFFFF; margin-left: 5px; top:20px"> Add Comments</a>
														</span>
													</div>
													
											</div>
											
														
											<div class=" clearBoth  ">
											</div>
									</div>
							</div>  
    						
    						
    						

    </div>     
	
	
	<input type="hidden" id="nonCaseStateno" name="nonCaseStateno" value="${nonCaseDocVO.STATENO}" />
      
</form:form>

<script src="${staticPath}/js/docs/nonCaseDocViewers.js?v=${buildDate}"></script>
<script type="text/javascript">
	createToggleForNonCaseFlag('${nonCaseDocVO.FLAG_ACTIVE}', '${nonCaseDocVO.STATENO}');
</script>