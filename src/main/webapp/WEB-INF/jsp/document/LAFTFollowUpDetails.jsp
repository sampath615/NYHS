<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link href="${staticPath}/css/docs/commonForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/assignments/assignments.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<div id="LaftFollowupAccordions" >
<c:forEach items="${laftFollowupList}" var="laftFollowupVOVar" varStatus="statusHNT">
<c:set var="laftFollowupVO" value="${laftFollowupVOVar}" scope="request" />
<h3 style="padding:1em" class="displayNone laftFollowupAccordion${statusHNT.index}"><span class="accordionHeader" style="left: 2em;top: 50%;margin-top: -8px;position: absolute;">LAFT Followup Details</span>	</h3>
<div class="laftFollowupAccordion container" style="background-color: transparent; border:1px dotted;height:auto" >
	<form:form modelAttribute="laftFollowupVO" id="laftFollowupVO" cssClass="documentFormVO documentDescription">
	
		<div style="width:100%; height:auto; margin:0px auto" >
		
			<div style="padding : 0 30 30 30; height:auto; width:100%;">
			
				<div class=" clearBoth pT10 ">
					<div class="width70Perc floatLeft">
						<span class="width25Perc font10pt bold">
							Reason Flagged for Followup :
						</span>
						<span class=" width75Perc bold"  style="color:black">
							<c:out value="${laftFollowupVO.reasonDesc}" />
						</span>
					</div>
					<div class="width30Perc floatLeft">
						<span class="width25Perc">
							<span class="font10pt bold">Flagged By :</span>
						</span>
						<span class="width75Perc bold"  style="color:black">
							<c:out value="${laftFollowupVO.createName}" />
						</span>
					</div>
				</div>
						
				<div class=" clearBoth pT10 ">
					<div class="width70Perc floatLeft">
						<div class="width10Perc floatLeft">
							<span class="font10pt bold">Comments :</span>
						</div>							
						<div class="width90Perc floatLeft">
							<span class=" bold"  style="color:black">
								<c:out value="${laftFollowupVO.initialComments}" escapeXml="false"/>
							</span>
						</div>	
						<c:if test="${laftFollowupVO.loginUserId == laftFollowupVO.createUid && laftFollowupVO.resolverName == null && laftFollowupVO.resolutionDate == null}">
								<input type="button" id="iniReviewCommentsButton" class="button rounded blue effect-3"  value="Review Comments"  onclick="addInitialReviewComments(${laftFollowupVO.followupIdPk})"/>
						</c:if>			
					</div>
					<div class="width30Perc floatLeft">
						<span class="width25Perc">
							<span class="font10pt bold">Flagged Date :</span>
						</span>
						<span class="width75Perc bold"  style="color:black">
							<c:out value="${laftFollowupVO.createDate}" />
						</span>
					</div>
				</div>		
						
				<div class=" clearBoth pT10 ">
					<div class="width70Perc floatLeft">
						<span class="width25Perc font10pt bold">
							LAFT Resolution :
						</span>
						<span class=" width75Perc bold"  style="color:black">
							<c:out value="${laftFollowupVO.resolutionDesc}" />
						</span>
					</div>
					<div class="width30Perc floatLeft">
						<span class="width25Perc">
							<span class="font10pt bold">Resolved By :</span>
						</span>
						<span class="width75Perc bold"  style="color:black">
							<c:out value="${laftFollowupVO.resolverName}" />
						</span>
					</div>
				</div>
						
				<div class=" clearBoth pT10 ">
					<div class="width70Perc floatLeft">
						<div class="width15Perc floatLeft">
							<span class="font10pt bold">LAFT Comments :</span>
						</div>							
						<div class="width75Perc floatLeft">
 							<span class=" bold"  style="color:black">
								<c:out value="${laftFollowupVO.reviewComments}" escapeXml="false"/>
							</span> 
							<%-- <span class="ml10"><form:textarea rows="8" cols="100" path="reviewComments" class="standardInputSmallText" style="width:400px" /> </span>		 --%>					
						</div>						
					</div>					
					<div class="width30Perc floatLeft">
						<span class="width25Perc">
							<span class="font10pt bold">Resolved Date :</span>
						</span>
						<span class="width75Perc bold"  style="color:black">
							<c:out value="${laftFollowupVO.resolutionDate}" />
						</span>
					</div>
				</div>
			
			</div>
		
		</div>
			
	</form:form>
</div>
</c:forEach>
	

</div>

<script type="text/javascript">
	var accordionLength = $('.laftFollowupAccordion').length; 
	if(accordionLength > 1){
	    $("#LaftFollowupAccordions").accordion({active : accordionLength - 1});  
	    $('.laftFollowupAccordion').css('height','auto');
	}
	
	if ($("#flaggedLaftFollowupTab").length) {
		$('#iniReviewCommentsButton').addClass('displayNone');
	} 
</script>