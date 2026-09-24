<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form:form modelAttribute="ePRFFollowUpDocVO" id="ePRFFollowUpDocVO">

<div style="width:640px; margin:0px auto">


	<c:choose>
			<c:when test="${ePRFFollowUpDocVO.FILE_TYPE_CD =='O' }">
					<div class="bold alignCenter mT20 font15pt">
							OOC Follow up
						</div>
						
			</c:when>
			
			<c:when test="${ePRFFollowUpDocVO.FILE_TYPE_CD == 'D'}">
				<div class="bold alignCenter mT20 font15pt">DNLTC Follow up</div>
			</c:when>
			
			
	</c:choose>

	<c:if test="${reviewStatus != null && reviewStatus != '{}' }">
			<div class="alignCenter mT10 bold">
				
			<span>Total Number of Rows on File: </span><span class="mL5 fieldValue"><c:out value="${reviewStatus.TOTAL_RECORDS }" /></span> 
			<span class="mL10">Number of Rows Saved, Not Complete: </span><span class="mL5 fieldValue"><c:out value="${reviewStatus.TOTAL_SAVED_RECORDS }" /></span>
			<span class="mL10">Number of Rows Complete: </span><span class="mL5 fieldValue"><c:out value="${reviewStatus.TOTAL_COMPLETED_RECORDS }" /></span>
			</div>
	</c:if>
	<div style="border:1px dotted; padding : 0 30 30 30" class="mT10">
	
		
	
		<div class=" clearBoth pT10 ">
		
				<div class="floatLeft width45Perc"><span class="font10pt bold">Medical Record Number</span></div>        
				<div class="floatLeft width45Perc bold"  style="color:black"><span><c:out value="${ePRFFollowUpDocVO.MED_RECORD_NUMBER}" /></span></div>	 
							
		
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
					<span class="font10pt bold">First Name</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
					<span><c:out value="${ePRFFollowUpDocVO.FNAME}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Last Name</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span><c:out value="${ePRFFollowUpDocVO.LNAME}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">DOB</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span><c:out value="${ePRFFollowUpDocVO.DOB}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Last Provider Visit Date</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span><c:out value="${ePRFFollowUpDocVO.PROV_LAST_VISIT_DATE}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Last VL Date</span><span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<c:out value="${ePRFFollowUpDocVO.LAST_VIRAL_LOAD_DATE}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Last VL Result</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span><c:out value="${ePRFFollowUpDocVO.LAST_VIRAL_LOAD_RESULT}" escapeXml="false"/></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Last CD4 Date</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span><c:out value="${ePRFFollowUpDocVO.LAST_CD4_DATE}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
		
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Last CD4 Count</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span><c:out value="${ePRFFollowUpDocVO.LAST_CD4_RESULT_COUNT}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Last CD4 Percent</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span><c:out value="${ePRFFollowUpDocVO.LAST_CD4_RESULT_PERCENT}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Provider County</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span><c:out value="${ePRFFollowUpDocVO.PROV_COUNTY}" /></span>
				</div>
		</div>
	</div>
	<div style="border:1px dotted; padding : 0 30 30 30">
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">NYEHMS  Match Result</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span><c:out value="${ePRFFollowUpDocVO.QS_MATCH_CD}" />	
				
				</span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Final Match Review Status</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span><c:out value="${ePRFFollowUpDocVO.MATCH_CD}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">STATENO</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span id="NYEHMS_STATENO"><c:out value="${ePRFFollowUpDocVO.NYEHMS_STATENO}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">NYS eHARS STATENO</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span id="NYS_EHARS_STATENO"><c:out value="${ePRFFollowUpDocVO.NYS_EHARS_STATENO}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">NYC eHARS STATENO</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span id="NYC_EHARS_STATENO"><c:out value="${ePRFFollowUpDocVO.NYC_EHARS_STATENO}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">NYC CITYNO</span><span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
					<span id="NYC_CITY_NO"><c:out value="${ePRFFollowUpDocVO.NYC_CITY_NO}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Last HIV Lab Type</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span id="LAST_HIV_LAB_TYPE"><c:out value="${ePRFFollowUpDocVO.LAST_HIV_LAB_TYPE}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Last HIV Lab Date</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span id="LAST_HIV_LAB_DATE"><c:out value="${ePRFFollowUpDocVO.LAST_HIV_LAB_DATE}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Last HIV Lab Result</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span id="LAST_HIV_LAB_RESULT"><c:out value="${ePRFFollowUpDocVO.LAST_HIV_LAB_RESULT}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Months Since Last HIV Lab</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span id="MONTHS_SINCE_LAST_HIV_LAB"><c:out value="${ePRFFollowUpDocVO.MONTHS_SINCE_LAST_HIV_LAB}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">NYS Vital Status</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span id="NYS_VITAL_STATUS"><c:out value="${ePRFFollowUpDocVO.NYS_VITAL_STATUS}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">NYC Vital Status</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span id="NYC_VITAL_STATUS"><c:out value="${ePRFFollowUpDocVO.NYC_VITAL_STATUS}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Meets CDC Case Definition in UPS or NYC</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span id="MEETS_CASE_DEFINITION_IN"><c:out value="${ePRFFollowUpDocVO.MEETS_CASE_DEFINITION_IN}" /></span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Meets CDC Eligibility Definition in &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; UPS or NYC</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span id="MEETS_CDC_ELIG_ID"><c:out value="${ePRFFollowUpDocVO.MEETS_CDC_ELIG_ID}" /></span>
				</div>
		</div>
		
		<br/><br/>
	</div>
	<div style="border:1px dotted; padding : 0 30 30 30">
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Confirmatory Result Reported</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span>
					<form:select path="CONFIRMATORY_RESULT_REPORTED" id="CONFIRMATORY_RESULT_REPORTED" class="required" title="Confirmatory Result Reported">
						<form:option value="">Select</form:option>
						<form:option value="Y">Yes</form:option>
						<form:option value="N">No</form:option>
					
					</form:select>
				
				</span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Submitting Provider Present</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span>
					
					<form:select path="SUBMITTING_PROVIDER_PRESENT" id="SUBMITTING_PROVIDER_PRESENT"  class="required" title="Submitting Provider Present">
						<form:option value="">Select</form:option>
						<form:option value="Y">Yes</form:option>
						<form:option value="N">No</form:option>
					
					</form:select>	
				</span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Review Comment</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
				<span>
				
					<form:textarea rows="3" cols="60" id="REVIEW_COMMENT" path="REVIEW_COMMENT" maxlength="4000"></form:textarea>
				
				
				</span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Internal Conclusion</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
					<span>
				
					<form:select path="INTERNAL_CONCLUSION" id="INTERNAL_CONCLUSION" class="required" title="Internal Conclusion">
						<form:option value="">Select</form:option>
						<c:forEach items="${sessionScope.internalConclusionList}" var="internalConclusion">
							<form:option value="${internalConclusion.INTERNAL_CONCLUSION_ID}">${internalConclusion.INTERNAL_CONCLUSION_DESC} </form:option>
						
						</c:forEach>
						
						
					</form:select>
		
		
				
				</span>
				</div>
		</div>
		<div class=" clearBoth pT10 ">
				<div class="floatLeft width45Perc">
				<span class="font10pt bold">Care Status</span>
				</div>
				<div class="floatLeft width45Perc bold"  style="color:black">
					<span>
						<form:select path="CARE_STATUS" id="CARE_STATUS"  class="required" title="Care Status">
							<form:option value="">Select</form:option>
							<c:forEach items="${sessionScope.careStatusList}" var="careStatus">
								<form:option value="${careStatus.CONCLUSION_ID}">${careStatus.CONCLUSION_DESC} </form:option>
							
							</c:forEach>
							
							
						</form:select>
					
				
				</span>
				</div>
		</div>
		
		
		<form:input type="hidden" path="DOCUMENT_ID" id="EPRF_DOCUMENT_ID" />
	</div>
	
	
	
	<div id="actionButtons" class="mergeButtons mT40" align="center">                  
                <span><a class="actionButtons a_demo_search a_demo_continue"  id="mergeStatenoPairs" onClick="saveFollowUpData()" style="color: #FFFFFF; margin-left: 5px;"> Save</a></span>  
                <span class="pL20"><a class="actionButtons a_demo_search a_demo_cancel" id="skipStatenoPairs" onClick="loadOOCDNLTCFollowupData('skip')" style="color: #FFFFFF; margin-left: 5px;"> Skip</a></span>
				<span class="pL20"><a class="actionButtons a_demo_search a_demo_continue" id="rejectStatenoPairs" onClick="completeFollowUpData()" style="color: #FFFFFF; margin-left: 5px;"> Complete </a></span>
	</div>
</div>
	<script type="text/javascript">
		loadPersonViewDataOOCFollowup('${ePRFFollowUpDocVO.NYEHMS_STATENO}');
	</script>
</form:form>
