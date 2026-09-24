<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- This jsp belongs to "TTH" Fourth Tab --%> 
<!-- page4 first block starts -->

<span class="mL10 hdrFont"> XII. Treatment/Services Referrals (record all dates as mm/dd/yyyy) </span>
<div class="roundedBorderFont mL10 toggleSection" dependentElement="tthMedArvDepElement" style="width:98%; background:#DDDDDD" >
	<div style="width:99%; margin-left:5px;  margin-right:5px; padding-bottom:5px" >
		<div id="takenArvs" style="width:60%; border-right:1px dotted;"> 
				<span class="mL5"> Has this child ever taken any ARVs?  </span>
				<span class="mL20"><form:radiobutton path="rawAcrfDoc.childEverTakenArvs" id="rawTTHDocVO_childEverTakenArvs" class="takenArvs" value="Y" title="child ever taken ARVs" /> Yes </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.childEverTakenArvs" id="rawTTHDocVO_childEverTakenArvs" class="takenArvs" value="N" title="child ever taken ARVs" /> No </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.childEverTakenArvs" id="rawTTHDocVO_childEverTakenArvs" class="takenArvs" value="U" title="child ever taken ARVs" /> Unknown</span>
				<div class="v_error takenArvs"></div>	
			</div>

		</div>

		<div style="width:99.9%; border:1px dotted;" class="container"></div>
		
		<div style="width:99%; margin-left:5px;  margin-right:5px; padding-bottom:5px" class="tthMedArvDepElement">
			<div style=" width:99%; margin-right:5px; padding-bottom:5px"> 
				<div class="alignCenter mT10">
				<span class="arvReason">Reason for use  </span></div></br>
				<c:set var="arvMedicationsIndex" value="0" />
				
				
				<div>
<!-- 					<div class="fLW5"> -->
<!-- 						<span class="bold "> &nbsp; </span> -->
<!-- 					</div> -->
					<div class="fLW18">
						<span class="bold mL5"> ARV medication </span>
					</div>
					<div class="fLW5">
						<span class="bold "> HIV Tx </span>					
					</div>
					<div class="fLW5">
						<span class="bold "> PrEP </span>					
					</div>
					<div class="fLW5">
						<span class="bold "> PEP </span>					
					</div>
					<div class="fLW5">
						<span class="bold "> PMTCT </span>					
					</div>
					<div class="fLW5">
						<span class="bold "> HBV Tx </span>					
					</div>
					
					<div class="fLW5">
						<span class="bold "> Other </span>					
					</div>
					
					<div class="fLW18">
						<span class="bold "> (specify reason) </span>					
					</div>
					
					<div class="fLW12">
						<span class="bold "> Date began </span>					
					</div>
					
					<div class="fLW12">
						<span class="bold "> Date of last use </span>					
					</div>
				</div>
				
				
				<c:forEach var="index" begin="0" end="4" step="1" varStatus="arvUseTypeIndex">
					<div class="clearBoth arvMedicationSection">
<!-- 						<div class="fLW5"> -->
<!-- 							<span class="bold "> &nbsp; </span> -->
<!-- 						</div> -->
						<div class="fLW18">
							<div class="mL5">
							<form:select  path="arvMedicationsList[${arvUseTypeIndex.index}].arvMedications" id="arvMedicationsList${arvUseTypeIndex.index}_arvMedications" value="${fn:split(arvMedicationsList.arvMedications,',')}"  class="standardInputMedText otherDepElement" maxlength="200"  title="ARV medications"  >
								<form:option value=""></form:option>
								<c:forEach items="${codedArvsMedications}" var="arvCategory">
									<form:option value="${arvCategory.LOOKUP_CD}">${arvCategory.ARV_DESC}</form:option>
								</c:forEach>
							</form:select>
							
							</div>
							<div class="v_error arvMedicationsList${arvUseTypeIndex.index}_arvMedications"></div> 
							
<!-- 							<script type="text/javascript">  -->
<%--  			 					arvMedications = '${acrfFormVO.arvMedicationsList[arvUseTypeIndex.index].arvMedications}'; --%>
<%--  			 					$('#arvMedicationsList${arvUseTypeIndex.index}_arvMedications').val(arvMedications.split(',')); --%>
<!-- 							</script> -->
						</div>
						<div class="fLW5">
							<form:radiobutton path="arvMedicationsList[${arvUseTypeIndex.index}].arvUseType" class="arvUseType otherElement" id="arvMedicationsList${arvUseTypeIndex.index}_arvUseType" value="HIV Tx" />				
						</div>
						<div class="fLW5">
							<form:radiobutton path="arvMedicationsList[${arvUseTypeIndex.index}].arvUseType" class="arvUseType otherElement" id="arvMedicationsList${arvUseTypeIndex.index}_arvUseType" value="PrEP" />					
						</div>
						<div class="fLW5">
							<form:radiobutton path="arvMedicationsList[${arvUseTypeIndex.index}].arvUseType" class="arvUseType otherElement" id="arvMedicationsList${arvUseTypeIndex.index}_arvUseType" value="PEP" />					
						</div>
						<div class="fLW5">
							<form:radiobutton path="arvMedicationsList[${arvUseTypeIndex.index}].arvUseType" class="arvUseType otherElement" id="arvMedicationsList${arvUseTypeIndex.index}_arvUseType" value="PMTCT" />					
						</div>
						<div class="fLW5">
							<form:radiobutton path="arvMedicationsList[${arvUseTypeIndex.index}].arvUseType" class="arvUseType otherElement" id="arvMedicationsList${arvUseTypeIndex.index}_arvUseType" value="HBV Tx" />					
						</div>
						
						<div class="fLW5">
							<form:radiobutton path="arvMedicationsList[${arvUseTypeIndex.index}].arvUseType" class="arvUseType otherElement" id="arvMedicationsList${arvUseTypeIndex.index}_arvUseType" value="OTHER" />					
						</div>
						
						<div class="fLW18">
							<form:input path="arvMedicationsList[${arvMedicationsIndex}].otherArvUseType" elementType="text" id="arvMedicationsList${arvMedicationsIndex}_otherArvUseType" title="Arv other user type" class="standardInputMedText otherDepElement otherArvReason"/>		
						</div>
						
						<div class="fLW12">
							<div><form:input path="arvMedicationsList[${arvUseTypeIndex.index}].arvMedicationsDateBegan" elementType="date" id="arvMedicationsList${arvUseTypeIndex.index}_arvMedicationsDateBegan" class="standardInputSmallText otherDepElement dPicker mask datepicker maskDate" datevalidations="partialDateForAll" title="Date began"/></div>
							<div class="v_error arvMedicationsList${arvUseTypeIndex.index}_arvMedicationsDateBegan"></div>					
						</div>
						
						<div class="fLW12">
							<div><form:input path="arvMedicationsList[${arvMedicationsIndex}].arvMedicationsDateOfLastUse" elementType="text" id="arvMedicationsList${arvMedicationsIndex}_arvMedicationsDateOfLastUse"  class="standardInputSmallText otherDepElement dPicker mask datepicker maskDate" datevalidations="partialDateForAll" title="Date of last use"/></div>
							<div class="v_error arvMedicationsList${arvUseTypeIndex.index}_arvMedicationsDateOfLastUse"></div>					
						</div>
						<form:hidden id="arvMedicationsList${arvUseTypeIndex.index}_arvSrcId" path="arvMedicationsList[${arvUseTypeIndex.index}].arvSrcId" />
						
						<div class="v_error arvMedicationsList${arvUseTypeIndex.index}_otherArvUseType"></div>
						<c:set var="arvMedicationsIndex" value="${arvMedicationsIndex+1}" />
					</div>
				</c:forEach>
				
				<div class="mT5">
					<span class="mL5 bold">(Record additional ARV medications in Comments)</span>
				</div>
				
			</div>
		</div>

	<div style="width:99%; margin-left:5px;  margin-right:5px; padding-bottom:5px" class="changeSelection">
		<div style="float:left; width:47%;"> 
			<span class="mL5"> Has this child ever taken PCP prophylaxis </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.childEverTakenPcpProphylaxis" id="rawTTHDocVO_childEverTakenPcpProphylaxis" value="Y"  title="child ever taken PCP prophylaxis"/> Yes</span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.childEverTakenPcpProphylaxis" id="rawTTHDocVO_childEverTakenPcpProphylaxis" value="N" title="child ever taken PCP prophylaxis"/> No</span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.childEverTakenPcpProphylaxis" id="rawTTHDocVO_childEverTakenPcpProphylaxis" value="U" title="child ever taken PCP prophylaxis"/> Unknown</span>	
		</div>
		<div class="floatLeft" style="width:26%; border-left:1px dotted;">
			<span class="mL5">Date began: <form:input path="rawAcrfDoc.prophylaxisBeganDate" id="rawAcrfDoc_prophylaxisBeganDate" class="standardInputSmallText dPicker mask datepicker maskDate" datevalidations="partialDateForAll" title="Date began"/> </span>
			<div class="v_error rawAcrfDoc_prophylaxisBeganDate"></div>
		</div>
		<div class="floatLeft" style="width:26%; border-left:1px dotted;">
			<span class="mL5">Date of last use: <form:input path="rawAcrfDoc.prophylaxisLastUseDate" id="rawAcrfDoc_prophylaxisLastUseDate" class="standardInputSmallText dPicker mask datepicker maskDate" datevalidations="partialDateForAll" title="Date of last use"/> </span>
			<div class="v_error rawAcrfDoc_prophylaxisLastUseDate"></div>
		</div>	
	</div>
	<div style="width:99.9%; border:1px dotted;" class="container"></div>



	<div style="width:99%; margin-left:5px;  margin-right:5px; padding-bottom:5px;">
			<span class="mL5"> This child's primary caretaker is </span>
			<span class="mL20"><form:radiobutton path="rawAcrfDoc.childPrimaryCaretaker" id="rawTTHDocVO_childPrimaryCaretaker" value="1" title="child breastfed" />  1-Biological parent  </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.childPrimaryCaretaker" id="rawTTHDocVO_childPrimaryCaretaker" value="2" title="child breastfed" /> 2-Other relative </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.childPrimaryCaretaker" id="rawTTHDocVO_childPrimaryCaretaker" value="3" title="child breastfed" /> 3-Foster/Adoptive parent, relative</span>
			<span class="mL20"><form:radiobutton path="rawAcrfDoc.childPrimaryCaretaker" id="rawTTHDocVO_childPrimaryCaretaker" value="4" title="child breastfed" /> 4-Foster/Adoptive parent, unrelated </span>
			<span class="mL20"><form:radiobutton path="rawAcrfDoc.childPrimaryCaretaker" id="rawTTHDocVO_childPrimaryCaretaker" value="7" title="child breastfed" /> 7-Social service agency </span>
			<span class="mL20"><form:radiobutton path="rawAcrfDoc.childPrimaryCaretaker" id="rawTTHDocVO_childPrimaryCaretaker" value="8" title="child breastfed" /> 8-Other (please specify in comments)  </span>
			<span class="mL20"><form:radiobutton path="rawAcrfDoc.childPrimaryCaretaker" id="rawTTHDocVO_childPrimaryCaretaker" value="9" title="child breastfed" />9-Unknown  </span>
				
	</div>

	
	<div style="width:99.9%; border:1px dotted;" class="container"></div>
</div>
<br/>

<span class="mL10 hdrFont"> XIII. Comments </span>
<div class="roundedBorder mL10" style="width:98%; height:187px; background:#DDDDDD; padding-left:2px;" >
	<span>  <form:textarea path="rawAcrfDoc.comments" id="rawAcrfDoc_comments" value="${acrfFormVO.rawAcrfDoc.comments}" ROWS="8" COLS="165" maxlength="255" title="Comments"/> </span>
	<div class="v_error rawAcrfDoc_comments"></div>
</div>

<br/>

<span class="mL10 hdrFont"> XIV. Local / Optional Fields </span>
<div class="roundedBorder mL10" style="width:98%; height:187px; background:#DDDDDD; padding-left:2px;" >
	<span>  <form:textarea path="rawAcrfDoc.localOptionalFields" id="rawAcrfDoc_localOptionalFields" value="${acrfFormVO.rawAcrfDoc.localOptionalFields}" ROWS="8" COLS="165" maxlength="4000" title="Comments"/> </span>
</div>
<div style="width:98%; background:#DDDDDD" class="roundedBorder mL10 container mT10">
	<p class="mL20 mR10"> 
		This report to CDC is authorized by law (Sections 304 and 306 of the Public Health Service Act, 42 USC 242b and 242k). Response in this case is voluntary for federal government purposes, but may be mandatory under state and local statutes. Your cooperation is necessary for the understanding and control of HIV. Information in CDC's National HIV Surveillance System that would permit identification of any individual on whom a record is maintained, is collected with a guarantee that it will be held in confidence, will be used only for the purposes stated in the assurance on file at the local health department, and will not otherwise be disclosed or released without the consent of the individual in accordance with Section 308(d) of the Public Health Service Act (42 USC 242m).
		
	</p>
</div>
<br/>