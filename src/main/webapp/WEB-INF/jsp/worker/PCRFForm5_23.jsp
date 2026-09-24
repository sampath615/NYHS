<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- This jsp belongs to "TTH" Fourth Tab --%> 
<!-- page4 first block starts -->



	
<span class="mL10 hdrFont"> XI. Birthing Person History (for patients exposed perinatally with or without consequent infection) </span>	
<div class="roundedBorderFont mL10" style="width:98%; background:#DDDDDD" >	

	<div style="float:left; width:99%;" class="container pB5"> 

		<div style="float:left; width:50%; border-right:1px dotted;  ">
		
				<span class="mL5">Birthing Person Date of Birth</span>
				<span class="mL10"><form:input path="rawAcrfDoc.maternalDob" id="rawAcrfDoc_maternalDob" class="standardInputSmallText  dPicker mask momInfo" datevalidations="partialDateForAll" title="Birthing Person DOB" /> </span>
				
				<div class="v_error rawAcrfDoc_maternalDob"></div>
				
		</div>
		<div style="float:left; width:49%; "> 
				<span class="mL5"> Birthing Person Last Name Soundex </span>
				<span class="mL10"><form:input path="rawAcrfDoc.maternalLastNameSndx" id="rawAcrfDoc_maternalLastNameSndx" class="standardInputMedText"  title="Birthing Person Last Name Soundex" /> </span>
		</div>
	</div>
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	
	
	<div style="float:left; width:99%;" class="container pB5"> 
		<div style="float:left; width:50%; "> 
				<span class="mL5">  Birthing Person Country of Birth </span>
				<span class="mL10">
					<form:select path="rawAcrfDoc.maternalCountryOfBirth" id="rawAcrfDoc_maternalCountryOfBirth"  class="standardInputMedText" maxlength="7" title="Birthing Person Country of Birth">
						<form:option value=""></form:option>
						<form:options items="${codedCountries}"/>
						<form:option value="OTH">Other</form:option>
						<form:option value="UNK">Unknown</form:option>
					</form:select>
					 
				</span>
		</div>
		<div style="float:left; width:49%; border-right:1px dotted;  ">
		
				<span class="mL10">  Birthing Person State ID Number</span>
				<span class="mL10"><form:input path="rawAcrfDoc.maternalStateIdNumber" id="rawAcrfDoc_maternalStateIdNumber" class="standardInputText" title="Birthing Person State ID Number" maxlength="20"/> </span>
				
		</div>
		
	</div>
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%;" class="container pB5">
	
		<div style="float:left; width:50%; "> 
				<span class="mL5">  Birthing Person City/County ID Number </span>
				<span class="mL10">
					<form:input path="rawAcrfDoc.bpCityNumber" id="rawAcrfDoc_bpCityNumber" class="standardInputText" title="Birthing Person City/County ID Number" maxlength="20"/>
					 
				</span>
		</div>
	</div>
	
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%;" class="container pB5">
	
		<div style="float:left; width:50%; "> 
				<span class="mL5">  Other Birthing Person ID type </span>
				<span class="mL10">
					<form:select path="rawAcrfDoc.otherMaternalIdType" id="rawAcrfDoc_otherMaternalIdType" class="standardInputText" title="Other Birthing Person ID type" onchange="enableDepElement(this, '#rawAcrfDoc_otherMaternalId')">
						<form:option value="" label="------Select-------"></form:option>
						<c:forEach items="${acrfOtherIdList}" var="idType">
							<form:option value="${idType.OTHER_ID_TYPE}" label="${idType.OTHER_ID_TYPE_DESC}"></form:option>
						</c:forEach>
					</form:select>
					 
				</span>
		</div>
		<div style="float:left; width:49%; border-right:1px dotted;  ">
		
				<span class="mL10"> Other Birthing Person ID number</span>
				<span class="mL10"><form:input path="rawAcrfDoc.otherMaternalId" id="rawAcrfDoc_otherMaternalId" class="standardInputText" title="Other Birthing Person ID number" /> </span>
				
		</div>
	</div>
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%;" class="container pB5 "> 
		<div style="float:left; width:50%; border-right:1px dotted;  ">
		
				<span class="mL5"> Prenatal Care-Month of Pregnancy Prenatal Care Began</span>
				<span class="mL10"><form:input path="rawAcrfDoc.prenatalCareBeginMonth" id="rawAcrfDoc_prenatalCareBeginMonth" class="standardInputVerySmall"  title="Prenatal Care month began" /> (99 = Unknown, 00 = None)</span>
		</div>
		<div style="float:left; width:49%; "> 
				<span class="mL5"> Prenatal Care-Total Number of Prenatal Care Visits</span>
				<span class="mL10"><form:input path="rawAcrfDoc.prenatalCareNoVisits" id="rawAcrfDoc_prenatalCareNoVisits" class="standardInputVerySmall"  title="Total Number of Prenatal Care Visits" /> (99 = Unknown, 00 = None)</span>
				
		</div>
	</div>
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>

	<div style="float:left; width:99%;" class="container pB5 changeSelection toggleSection" dependentElement="pregBeforeDepElement"> 
		<div style="float:left;  border-right:1px dotted;   ">
				<span class="mL5 bold"> Has the birthing person ever been pregnant before this pregnancy? Include previous pregnancies that ended in a live birth, miscarriage, stillbirth, or induced abortion.</span>
				<span class="mL10"><form:radiobutton path="rawAcrfDoc.pregBefore" id="rawAcrfDoc_pregBefore" value="Y" title="Has the birthing person ever been pregnant before this pregnancy" /> Yes </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.pregBefore" id="rawAcrfDoc_pregBefore" value="N" title="Has the birthing person ever been pregnant before this pregnancy" /> No </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.pregBefore" id="rawAcrfDoc_pregBefore" value="U" title="Has the birthing person ever been pregnant before this pregnancy" /> Unknown</span>
		</div>
		

	
	<!-- <div style="width:99.9%; border:1px dotted; " class="container"></div> -->
	
	<div style="float:left; width:99%;" class="container pB5 pregBeforeDepElement"> 
		<div style="float:left;  border-right:1px dotted;   ">
				<span class="mL5 bold"> If YES, specify how many previous pregnancies</span>
				<span class="mL10"><form:input path="rawAcrfDoc.numPrevPreg" id="rawAcrfDoc_numPrevPreg" class="standardInputVerySmall nOnly" maxlength="2"  title="previous pregnancies count"/></span><br/>
				
				<table border="1" cellpadding="0" cellspacing="0" class="" style="width:85%; margin: 20px auto; background:white" id="pregOutComeBirthInfo">
		
					<thead>
					
						<tr>
<!-- 					 		<th> -->
<!-- 								<span class="mL5"></span> -->
<!-- 							</th> -->
							<th colspan = "3" >
								<span class="mL5">Pregnancy outcome (select one)</span>
							</th>
							<th>
								<span class="mL5">Year outcome occurred</span>
							</th>
						</tr>
					
						<tr>
<!-- 					 		<th class="textAlignLeft" style="width:10%"> -->
<!-- 								<span class="mL5"></span> -->
<!-- 							</th> -->
							<th class="textAlignCenter"  style="width:20%">
								<span class=""> Live birth</span>
							</th>
							<th class="textAlignCenter"   style="width:20%">
								<span class=""> Miscarriage or Stillbirth</span>
							</th>
							<th class="textAlignCenter"   style="width:20%">
								<span class=""> Induced abortion</span>
							</th>
							<th class="textAlignCenter"   style="width:30%">
								<span class=""> (9999 = Unknown)</span>
							</th>
						</tr>
					
					</thead>
					<tbody id="pregOutcomeList">
					
						<c:forEach  items="${acrfFormVO.rawAcrfDoc.bpPregOutcomeList}" var="subStance" varStatus="statusHNT">
							<tr class="pregOutcomeSection toggleSection" dependentElement="PregOutcomeDepElement">	
<!-- 							 	<td class="textAlignLeft"> -->
<%-- 									<span class="mL5">${statusHNT.index}.</span> --%>
<!-- 								</td> -->
								<td class="textAlignCenter">
									<span class=""> <form:radiobutton path="rawAcrfDoc.bpPregOutcomeList[${statusHNT.index}].pregOutcome" value="1" class="pregOutcomeData"/></span>
								</td>
								<td class="textAlignCenter">
									<span class=""> <form:radiobutton path="rawAcrfDoc.bpPregOutcomeList[${statusHNT.index}].pregOutcome" value="2" class="pregOutcomeData"/></span>
								</td>
								<td class="textAlignCenter">
									<span class=""> <form:radiobutton path="rawAcrfDoc.bpPregOutcomeList[${statusHNT.index}].pregOutcome" value="3" class="pregOutcomeData"/></span>
								</td>
								<td class="textAlignCenter PregOutcomeDepElement">
									
									<c:choose>
										<c:when test="${acrfFormVO.rawAcrfDoc.bpPregOutcomeList[statusHNT.index].pregOutcome != null && acrfFormVO.rawAcrfDoc.bpPregOutcomeList[statusHNT.index].pregOutcome != ''}">
											<span class="mL5"><form:input path="rawAcrfDoc.bpPregOutcomeList[${statusHNT.index}].pregOutcomeYear" id="rawAcrfDoc_bpPregOutcomeList${statusHNT.index}_pregOutcomeYear" class="substanceDataText otherDepElement standardInputSmallText nOnly" maxlength="4" title="Year outcome occurred" /></span>						
										</c:when>
										<c:otherwise>
											<span class="mL5"><form:input path="rawAcrfDoc.bpPregOutcomeList[${statusHNT.index}].pregOutcomeYear" id="rawAcrfDoc_bpPregOutcomeList${statusHNT.index}_pregOutcomeYear" class="substanceDataText otherDepElement standardInputSmallText nOnly readonly" maxlength="4" title="Year outcome occurred" /></span>						
										</c:otherwise>
									
									</c:choose>
								
								
									
								</td>
							</tr>
						</c:forEach>
						
<!-- 						<tr class="pregOutcomeSection toggleSection" dependentElement="PregOutcomeDepElement">	 -->
<!-- 						 	<td class="textAlignLeft"> -->
<!-- 								<span class="mL5">ii.</span> -->
<!-- 							</td> -->
<!-- 							<td class="textAlignCenter"> -->
<%-- 								<span class=""> <form:radiobutton path="rawAcrfDoc.bpPregOutcomeList[1].pregOutcome" value="1" class="pregOutcomeData"/></span> --%>
<!-- 							</td> -->
<!-- 							<td class="textAlignCenter"> -->
<%-- 								<span class=""> <form:radiobutton path="rawAcrfDoc.bpPregOutcomeList[1].pregOutcome" value="2" class="pregOutcomeData"/></span> --%>
<!-- 							</td> -->
<!-- 							<td class="textAlignCenter"> -->
<%-- 								<span class=""> <form:radiobutton path="rawAcrfDoc.bpPregOutcomeList[1].pregOutcome" value="3" class="pregOutcomeData"/></span> --%>
<!-- 							</td> -->
<!-- 							<td class="textAlignCenter PregOutcomeDepElement"> -->
<%-- 								<span class="mL5"><form:input path="rawAcrfDoc.bpPregOutcomeList[1].pregOutcomeYear" id="rawAcrfDoc_bpPregOutcomeList1_pregOutcomeYear" class="substanceDataText otherDepElement standardInputSmallText nOnly" maxlength="4" title="Year outcome occurred" /></span> --%>
<!-- 							</td> -->
<!-- 						</tr> -->
						
<!-- 						<tr class="pregOutcomeSection toggleSection" dependentElement="PregOutcomeDepElement">	 -->
<!-- 						 	<td class="textAlignLeft"> -->
<!-- 								<span class="mL5">iii.</span> -->
<!-- 							</td> -->
<!-- 							<td class="textAlignCenter"> -->
<%-- 								<span class=""> <form:radiobutton path="rawAcrfDoc.bpPregOutcomeList[2].pregOutcome" value="1" class="pregOutcomeData"/></span> --%>
<!-- 							</td> -->
<!-- 							<td class="textAlignCenter"> -->
<%-- 								<span class=""> <form:radiobutton path="rawAcrfDoc.bpPregOutcomeList[2].pregOutcome" value="2" class="pregOutcomeData"/></span> --%>
<!-- 							</td> -->
<!-- 							<td class="textAlignCenter"> -->
<%-- 								<span class=""> <form:radiobutton path="rawAcrfDoc.bpPregOutcomeList[2].pregOutcome" value="3" class="pregOutcomeData"/></span> --%>
<!-- 							</td> -->
<!-- 							<td class="textAlignCenter PregOutcomeDepElement"> -->
<%-- 								<span class="mL5"><form:input path="rawAcrfDoc.bpPregOutcomeList[2].pregOutcomeYear" id="rawAcrfDoc_bpPregOutcomeList2_pregOutcomeYear" class="substanceDataText otherDepElement standardInputSmallText nOnly" maxlength="4" title="Year outcome occurred" /></span> --%>
<!-- 							</td> -->
<!-- 						</tr> -->
						
<!-- 						<tr class="pregOutcomeSection toggleSection" dependentElement="PregOutcomeDepElement">	 -->
<!-- 						 	<td class="textAlignLeft"> -->
<!-- 								<span class="mL5">iv.</span> -->
<!-- 							</td> -->
<!-- 							<td class="textAlignCenter"> -->
<%-- 								<span class=""> <form:radiobutton path="rawAcrfDoc.bpPregOutcomeList[3].pregOutcome" value="1" class="pregOutcomeData"/></span> --%>
<!-- 							</td> -->
<!-- 							<td class="textAlignCenter"> -->
<%-- 								<span class=""> <form:radiobutton path="rawAcrfDoc.bpPregOutcomeList[3].pregOutcome" value="2" class="pregOutcomeData"/></span> --%>
<!-- 							</td> -->
<!-- 							<td class="textAlignCenter"> -->
<%-- 								<span class=""> <form:radiobutton path="rawAcrfDoc.bpPregOutcomeList[3].pregOutcome" value="3" class="pregOutcomeData"/></span> --%>
<!-- 							</td> -->
<!-- 							<td class="textAlignCenter PregOutcomeDepElement"> -->
<%-- 								<span class="mL5"><form:input path="rawAcrfDoc.bpPregOutcomeList[3].pregOutcomeYear" id="rawAcrfDoc_bpPregOutcomeList3_pregOutcomeYear" class="substanceDataText otherDepElement standardInputSmallText nOnly" maxlength="4" title="Year outcome occurred" /></span> --%>
<!-- 							</td> -->
<!-- 						</tr> -->
						
<!-- 						<tr class="pregOutcomeSection toggleSection" dependentElement="PregOutcomeDepElement">	 -->
<!-- 						 	<td class="textAlignLeft"> -->
<!-- 								<span class="mL5">v.</span> -->
<!-- 							</td> -->
<!-- 							<td class="textAlignCenter"> -->
<%-- 								<span class=""> <form:radiobutton path="rawAcrfDoc.bpPregOutcomeList[4].pregOutcome" value="1" class="pregOutcomeData"/></span> --%>
<!-- 							</td> -->
<!-- 							<td class="textAlignCenter"> -->
<%-- 								<span class=""> <form:radiobutton path="rawAcrfDoc.bpPregOutcomeList[4].pregOutcome" value="2" class="pregOutcomeData"/></span> --%>
<!-- 							</td> -->
<!-- 							<td class="textAlignCenter"> -->
<%-- 								<span class=""> <form:radiobutton path="rawAcrfDoc.bpPregOutcomeList[4].pregOutcome" value="3" class="pregOutcomeData"/></span> --%>
<!-- 							</td> -->
<!-- 							<td class="textAlignCenter PregOutcomeDepElement"> -->
<%-- 								<span class="mL5"><form:input path="rawAcrfDoc.bpPregOutcomeList[4].pregOutcomeYear" id="rawAcrfDoc_bpPregOutcomeList4_pregOutcomeYear" class="substanceDataText otherDepElement standardInputSmallText nOnly" maxlength="4" title="Year outcome occurred" /></span> --%>
<!-- 							</td> -->
<!-- 						</tr> -->
						
					</tbody>
				</table>
				
				<span class="mL5 bold"> (Record additional pregnancy outcomes in Comments)</span>
				
		</div>
		
	</div>

	</div>
	
	
	<div style="float:left; width:99%;" class="container pB5 "> 
		<div style="float:left;  border-right:1px dotted;  ">
		
				<span class="mL5"> Was a test result (with a specimen collection date within the 6 weeks on or before delivery) documented in the birthing person's labor/delivery record</span><br/>
				<span class="mL5">CD4</span>
				<span class="mL10"><form:radiobutton path="rawAcrfDoc.bpCd4Test" id="rawAcrfDoc_bpCd4Test" value="Y"  /> Yes </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpCd4Test" id="rawAcrfDoc_bpCd4Test" value="N"  /> No </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpCd4Test" id="rawAcrfDoc_bpCd4Test" value="U"  /> Unknown</span>
				
				
				<span class="mL50">Quantitative NAAT (RNA or DNA)</span>
				
				<span class="mL10"><form:radiobutton path="rawAcrfDoc.bpVlTest" id="rawAcrfDoc_bpVlTest" value="Y"  /> Yes </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpVlTest" id="rawAcrfDoc_bpVlTest" value="N"  /> No </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpVlTest" id="rawAcrfDoc_bpVlTest" value="U"  /> Unknown</span>
		</div>
	</div>
	
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%;" class="container pB5 changeSelection"> 
		<div style="float:left; width:50%; border-right:1px dotted;  ">
				<div>
					<span class="mL5"> Did birthing person receive any antiretrovirals (ARVs) prior to this pregnancy?</span>
					<span class="mL10"><form:radiobutton path="rawAcrfDoc.motherRecvArvPriorPregnancy" id="rawAcrfDoc_motherRecvArvPriorPregnancy" value="Y" title="ARVs prior to this pregnancy" /> Yes </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvArvPriorPregnancy" id="rawAcrfDoc_motherRecvArvPriorPregnancy" value="N" title="ARVs prior to this pregnancy" /> No </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvArvPriorPregnancy" id="rawAcrfDoc_motherRecvArvPriorPregnancy" value="R" title="ARVs prior to this pregnancy" /> Refused</span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvArvPriorPregnancy" id="rawAcrfDoc_motherRecvArvPriorPregnancy" value="U" title="ARVs prior to this pregnancy" /> Unknown</span>
				</div>	
				<div class="mT10">
					
					<div class="floatLeft">
					
						<span class="mL5"> Date began</span>
						<span class="mL10"><form:input path="rawAcrfDoc.arvPriorPregnancyBeganDate" id="rawAcrfDoc_arvPriorPregnancyBeganDate" class="standardInputSmallText  dPicker mask momInfo" datevalidations="partialDateForAll" title="ARVs Date began" /> </span>
						
						<div class="v_error rawAcrfDoc_arvPriorPregnancyBeganDate"></div>
					</div>
					<div class="floatLeft">
						<span class="mL10"> Date of last use</span>
						<span class="mL10"><form:input path="rawAcrfDoc.arvPriorPregnancyLastUseDate" id="rawAcrfDoc_arvPriorPregnancyLastUseDate" class="standardInputSmallText  dPicker mask momInfo" datevalidations="partialDateForAll" title="ARVs Date of last use" /> </span>
						
						<div class="v_error rawAcrfDoc_arvPriorPregnancyLastUseDate"></div>
					</div>
				</div>
		</div>
		<div style="float:left; width:49%; "> 
				<span class="mL5"> If yes, specify all ARVs</span>
				<span class="mL10">
					
					<form:select  path="rawAcrfDoc.motherRecvArvPriorPregnancyNotes" id="rawAcrfDoc_motherRecvArvPriorPregnancyNotes"   class="standardInputMedText"  title="ARVs prior to this pregnancy" multiple="true" >
						<c:forEach items="${codedArvsMedications}" var="arvCategory">
							<form:option value="${arvCategory.LOOKUP_CD}">${arvCategory.ARV_DESC}</form:option>
						</c:forEach>
					</form:select> 
					
					
				</span>
				
		</div>
	</div>
	
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%;" class="container pB5 changeSelection"> 
		<div style="float:left;  border-right:1px dotted;  " >
				<div>
					<span class="mL5"> Did birthing person receive any ARVs during this pregnancy?</span>
					<span class="mL10"><form:radiobutton path="rawAcrfDoc.motherRecvArvDuringPregnancy" id="rawAcrfDoc_motherRecvArvDuringPregnancy" value="Y" title="ARVs during pregnancy" /> Yes </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvArvDuringPregnancy" id="rawAcrfDoc_motherRecvArvDuringPregnancy" value="N" title="ARVs during pregnancy" /> No </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvArvDuringPregnancy" id="rawAcrfDoc_motherRecvArvDuringPregnancy" value="R" title="ARVs during pregnancy" /> Refused</span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvArvDuringPregnancy" id="rawAcrfDoc_motherRecvArvDuringPregnancy" value="U" title="ARVs during pregnancy" /> Unknown</span>
				</div>	
				<div class="clearBoth mT10 changeYesSelection">
					
					<div class="floatLeft">
					
						<span class="mL5"> Date began</span>
						<span class="mL10"><form:input path="rawAcrfDoc.arvDuringPregnancyBeganDate" id="rawAcrfDoc_arvDuringPregnancyBeganDate" class="standardInputSmallText  dPicker mask momInfo" datevalidations="partialDateForAll" title="ARVs Date began" /> </span>
						<div class="v_error rawAcrfDoc_arvDuringPregnancyBeganDate"></div>
					</div>
					<div class="floatLeft">
					
						<span class="mL10"> Date of last use</span>
						<span class="mL10"><form:input path="rawAcrfDoc.arvDuringPregnancyLastUseDate" id="rawAcrfDoc_arvDuringPregnancyLastUseDate" class="standardInputSmallText  dPicker mask momInfo" datevalidations="partialDateForAll" title="ARVs Date of last use" /> </span>
						<div class="v_error rawAcrfDoc_arvDuringPregnancyLastUseDate"></div>
					</div>
				</div>
		</div>
		<div class="clearBoth mT10 changeYesSelection" style="float:left;  "> 
				<span class="mL5"> If YES, specify all ARVs</span>
				<span class="mL10">
				
					
					<form:select  path="rawAcrfDoc.motherRecvArvDuringPregnancyNotes" id="rawAcrfDoc_motherRecvArvDuringPregnancyNotes"   class="standardInputMedText"  title="ARVs during pregnancy" multiple="true" >
						<c:forEach items="${codedArvsMedications}" var="arvCategory">
							<form:option value="${arvCategory.LOOKUP_CD}">${arvCategory.ARV_DESC}</form:option>
						</c:forEach>
					</form:select> 
					
			 </span>
		</div>
		<div class="clearBoth mT10 changeNoSelection" style="float:left;  "> 
			 <span class="mL5"> If NO, select reason</span>
				<span class="mL10"><form:radiobutton path="rawAcrfDoc.bpArvPregDrugRsn" id="rawAcrfDoc_bpArvPregDrugRsn" value="NO_PRENAT_CARE" />No prenatal care </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpArvPregDrugRsn" id="rawAcrfDoc_bpArvPregDrugRsn" value="HIV_NEG_DUR_PREG"  /> Birthing person known to be HIV-negative during pregnancy </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpArvPregDrugRsn" id="rawAcrfDoc_bpArvPregDrugRsn" value="U" /> Unknown</span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpArvPregDrugRsn" id="rawAcrfDoc_bpArvPregDrugRsn" value="HIV_SERO_UNK"/> HIV serostatus of birthing person unknown</span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpArvPregDrugRsn" id="rawAcrfDoc_bpArvPregDrugRsn" value="O"/> Other (specify)</span>
				<span class="mL5"><form:input path="rawAcrfDoc.bpArvPregDrugRsnOther" id="rawAcrfDoc_bpArvPregDrugRsnOther" /> </span>
						
		</div>
	</div>
	
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%;" class="container pB5 changeSelection"> 
		<div style="float:left; border-right:1px dotted;  ">
				<div>
					<span class="mL5"> Did birthing person receive any ARVs during labor/delivery?</span>
					<span class="mL10"><form:radiobutton path="rawAcrfDoc.motherRecvArvLaborPregnancy" id="rawAcrfDoc_motherRecvArvLaborPregnancy" value="Y" title="ARVs during delivery" /> Yes </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvArvLaborPregnancy" id="rawAcrfDoc_motherRecvArvLaborPregnancy" value="N" title="ARVs during delivery" /> No </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvArvLaborPregnancy" id="rawAcrfDoc_motherRecvArvLaborPregnancy" value="R" title="ARVs during delivery" /> Refused</span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvArvLaborPregnancy" id="rawAcrfDoc_motherRecvArvLaborPregnancy" value="U" title="ARVs during delivery" /> Unknown</span>
				</div>	
				<div class="mT10 changeYesSelection">
					<div class="floatLeft">
						<span class="mL5"> Date began</span>
						<span class="mL10"><form:input path="rawAcrfDoc.arvLaborPregnancyBeganDate" id="rawAcrfDoc_arvLaborPregnancyBeganDate" class="standardInputSmallText  dPicker mask momInfo" datevalidations="partialDateForAll" title="ARVs Date began" /> </span>
						<div class="v_error rawAcrfDoc_arvLaborPregnancyBeganDate"></div>
					</div>
					<div class="floatLeft">
						<span class="mL10"> Date of last use</span>
						<span class="mL10"><form:input path="rawAcrfDoc.arvLaborPregnancyLastUseDate" id="rawAcrfDoc_arvLaborPregnancyLastUseDate" class="standardInputSmallText  dPicker mask momInfo" datevalidations="partialDateForAll" title="ARVs Date of last use" /> </span>
						<div class="v_error rawAcrfDoc_arvLaborPregnancyLastUseDate"></div>
					</div>
					
				</div>
		</div>
		<div class="clearBoth mT10 changeYesSelection" style="float:left;  "> 
				<span class="mL5"> If yes, specify all ARVs</span>
				<span class="mL10">
					
					<form:select  path="rawAcrfDoc.motherRecvArvLaborPregnancyNotes" id="rawAcrfDoc_motherRecvArvLaborPregnancyNotes"   class="standardInputMedText"  title="ARVs during delivery" multiple="true" >
						<c:forEach items="${codedArvsMedications}" var="arvCategory">
							<form:option value="${arvCategory.LOOKUP_CD}">${arvCategory.ARV_DESC}</form:option>
						</c:forEach>
					</form:select> 
					
					
				 </span>
		</div>
		<div class="clearBoth mT10 changeNoSelection" style="float:left;  "> 
			 <span class="mL5"> If NO, select reason</span>
				<span class="mL10"><form:radiobutton path="rawAcrfDoc.bpArvLaborDrugRsn" id="rawAcrfDoc_bpArvLaborDrugRsn" value="STAT_CESAREAN DEL" />Precipitous delivery/STAT Cesarean delivery </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpArvLaborDrugRsn" id="rawAcrfDoc_bpArvLaborDrugRsn" value="HIV SERO_INK"  /> HIV serostatus of birthing person unknown </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpArvLaborDrugRsn" id="rawAcrfDoc_bpArvLaborDrugRsn" value="BTH_NOT_HOSP" /> Birth not in hospital</span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpArvLaborDrugRsn" id="rawAcrfDoc_bpArvLaborDrugRsn" value="TST_NEG_DUR_PREG"/> Birthing person tested HIV negative during pregnancy</span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpArvLaborDrugRsn" id="rawAcrfDoc_bpArvLaborDrugRsn" value="O"/> Other (specify)</span>
				<span class="mL5"><form:input path="rawAcrfDoc.bpArvLaborDrugRsnOther" id="rawAcrfDoc_bpArvLaborDrugRsnOther" /> </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpArvLaborDrugRsn" id="rawAcrfDoc_bpArvLaborDrugRsn" value="U"/> Unknown</span>
				
		</div>
	</div>
	
	
	<script type="text/javascript"> 
			arvMedications = '${acrfFormVO.rawAcrfDoc.motherRecvArvPriorPregnancyNotes}';
			$('#rawAcrfDoc_motherRecvArvPriorPregnancyNotes').val(arvMedications.split(','));
			arvMedications = '${acrfFormVO.rawAcrfDoc.motherRecvArvDuringPregnancyNotes}';
			$('#rawAcrfDoc_motherRecvArvDuringPregnancyNotes').val(arvMedications.split(','));
			arvMedications = '${acrfFormVO.rawAcrfDoc.motherRecvArvLaborPregnancyNotes}';
			$('#rawAcrfDoc_motherRecvArvLaborPregnancyNotes').val(arvMedications.split(','));
			
			var birthDefectsTypes = '${acrfFormVO.rawAcrfDoc.birthDefectsTypes}';
			$('#rawAcrfDoc_birthDefectsTypes').val(birthDefectsTypes.split(','));
			
			
	</script> 
	
	
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	
	<div style="float:left; width:99%;" class="container pB5 mT10"> 
		<div style="float:left;  border-right:1px dotted;  width:80%">
		
				<span class="mL5"> Was the birthing person screened for any of the following conditions during this pregnancy?</span><br/>
				
	<table id="subStanceTableScreened" class="subStanceTable" border="1" cellpadding="0" cellspacing="0" style="width:85%; margin: 20px auto; background:white">
		
		<thead>
		
			<tr>
		 		<th class="textAlignLeft" style="width:20%">
					<span class="mL5">Condition name </span>
				</th>
				<th class="textAlignCenter"   style="width:75%">
					<span class="">  </span>
				</th>
			</tr>
		
		</thead>
		<tbody>
			<c:forEach  items="${acrfFormVO.rawAcrfDoc.bpScreenConditionsList}" var="subStance" varStatus="statusHNT">
				<tr class="subStanceSection toggleSection" dependentElement="substanceDepElement">	
				 	<td class="textAlignLeft">
						<span class="mL5"> <c:out value="${subStance.subStanceName}" /></span>
						<form:hidden path="rawAcrfDoc.bpScreenConditionsList[${statusHNT.index}].subStancecode" substancecode="${subStance.refCd}" class="displayNone substanceCode" />
						<c:if test="${subStance.refCd == '17'}">
							<form:input path="rawAcrfDoc.bpScreenConditionsList[${statusHNT.index}].otherDetails" id="rawAcrfDoc_bpScreenConditionsList${statusHNT.index}_otherDetails" class="substanceDataText standardInputSmallText  " title="Other Substance" />
						</c:if>
					</td>

					<td class="textAlignCenter">
						<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpScreenConditionsList[${statusHNT.index}].screenResult" id="rawAcrfDoc_bpScreenConditionsList${statusHNT.index}_screenResult" value="Y" title="Substance Result"  class="substanceData screenResult"/> Yes</span>
						<span class="substanceDepElement">
							<c:choose>
								<c:when test="${acrfFormVO.rawAcrfDoc.bpScreenConditionsList[statusHNT.index].screenResult == 'Y'}">
									<span class="mL5">Date of diagnosis (mm/dd/yyyy)</span> <span class="mL5"><form:input path="rawAcrfDoc.bpScreenConditionsList[${statusHNT.index}].screenDate" id="rawAcrfDoc_bpScreenConditionsList${statusHNT.index}_screenDate" class="substanceDate  standardInputSmallText  dPicker mask screenedPregDate" datevalidations="partialDateForAll" title="Screen date" /></span>						
								</c:when>
								<c:otherwise>
								 	<span class="mL5">Date of diagnosis (mm/dd/yyyy)</span> <span class="mL5"><form:input path="rawAcrfDoc.bpScreenConditionsList[${statusHNT.index}].screenDate" id="rawAcrfDoc_bpScreenConditionsList${statusHNT.index}_screenDate" class="substanceDate  standardInputSmallText  dPicker mask readonly screenedPregDate" datevalidations="partialDateForAll" title="Screen date" /></span>						
								</c:otherwise>
							
							</c:choose>
						</span>
						<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpScreenConditionsList[${statusHNT.index}].screenResult" id="rawAcrfDoc_bpScreenConditionsList${statusHNT.index}_screenResult" value="N" title="Substance Result"  class="substanceData screenResult"/> No</span>
						<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpScreenConditionsList[${statusHNT.index}].screenResult" id="rawAcrfDoc_bpScreenConditionsList${statusHNT.index}_screenResult" value="U" title="Substance Result"  class="substanceData screenResult"/> Unknown</span>
						<br/>
						<span class="mL5 v_error rawAcrfDoc_bpScreenConditionsList${statusHNT.index}_screenDate"></span>
					
					</td>
				</tr>
			</c:forEach>
		</table>
				
				
		</div>
	</div>
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	
	
	<div style="float:left; width:99%;" class="container pB5 mT10"> 
		<div style="float:left;  border-right:1px dotted;  width:80%">
		
				<span class="mL5"> Were any of the following conditions diagnosed for the birthing person during this pregnancy or at the time of labor and delivery?</span><br/>
				
		<table id="subStanceTableDiagnosed" class="subStanceTable" border="1" cellpadding="0" cellspacing="0"  style="width:85%; margin: 20px auto;background:white">
		
		<thead>
		
			<tr>
		 		<th class="textAlignLeft" style="width:20%">
					<span class="mL5">Condition name </span>
				</th>
				<th class="textAlignCenter"  style="width:75%">
					<span class=""> Was Condition diagnosed?</span>
				</th>
			</tr>
		
		</thead>
		<tbody>
			<c:forEach  items="${acrfFormVO.rawAcrfDoc.bpDxScreenConditionsList}" var="subStance" varStatus="statusHNT">
				<tr class="subStanceSection toggleSection" dependentElement="substanceDepElement">	
				 	<td class="textAlignLeft">
						<span class="mL5"> <c:out value="${subStance.subStanceName}" /></span>
						<form:hidden path="rawAcrfDoc.bpDxScreenConditionsList[${statusHNT.index}].subStancecode" substancecode="${subStance.refCd}" class="displayNone substanceCode" />
					</td>
					<td class="textAlignCenter">
						<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpDxScreenConditionsList[${statusHNT.index}].screenResult" id="rawAcrfDoc_bpDxScreenConditionsList${statusHNT.index}_screenResult" value="Y" title="Substance Result" class="substanceData screenResult"/> Yes</span>
						<span class="substanceDepElement">
							<c:choose>
								<c:when test="${acrfFormVO.rawAcrfDoc.bpDxScreenConditionsList[statusHNT.index].screenResult == 'Y'}">
									<span class="mL5">Date of diagnosis (mm/dd/yyyy)</span> <span class="mL5"><form:input path="rawAcrfDoc.bpDxScreenConditionsList[${statusHNT.index}].screenDate" id="rawAcrfDoc_bpDxScreenConditionsList${statusHNT.index}_screenDate" class="standardInputSmallText dPicker mask substanceDate diagPregDate" datevalidations="partialDateForAll" title="Screen date" /></span>
								</c:when>
								<c:otherwise>
									<span class="mL5">Date of diagnosis (mm/dd/yyyy)</span> <span class="mL5"><form:input path="rawAcrfDoc.bpDxScreenConditionsList[${statusHNT.index}].screenDate" id="rawAcrfDoc_bpDxScreenConditionsList${statusHNT.index}_screenDate" class="standardInputSmallText dPicker mask substanceDate readonly diagPregDate" datevalidations="partialDateForAll" title="Screen date" /></span>
								</c:otherwise>
							
							</c:choose>
						</span>
						
						<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpDxScreenConditionsList[${statusHNT.index}].screenResult" id="rawAcrfDoc_bpDxScreenConditionsList${statusHNT.index}_screenResult" value="N" title="Substance Result" class="substanceData screenResult"/> No</span>
						<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpDxScreenConditionsList[${statusHNT.index}].screenResult" id="rawAcrfDoc_bpDxScreenConditionsList${statusHNT.index}_screenResult" value="U" title="Substance Result" class="substanceData screenResult"/> Unknown</span>
						<br/>
						<span class="mL5 v_error rawAcrfDoc_bpDxScreenConditionsList${statusHNT.index}_screenDate"></span>
					
					
					</td>
				</tr>
			</c:forEach>
		</table>
				
		</div>
	</div>
	
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%;" class="container pB5 mT10"> 
		<div style="float:left;  border-right:1px dotted;  width:100%"  class="toggleSection" dependentElement="bpUseSubstanceDepElement">
		
				<span class="mL5">Were substances used by the birthing person during this pregnancy?</span>
				<span class="mL10"><form:radiobutton path="rawAcrfDoc.bpUseSubstance" id="rawAcrfDoc_bpUseSubstance" value="Y" /> Yes </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpUseSubstance" id="rawAcrfDoc_bpUseSubstance" value="N"  /> No </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpUseSubstance" id="rawAcrfDoc_bpUseSubstance" value="U"  /> Unknown</span>
				
				
				
		<table class="subStanceTable" border="1" cellpadding="0" cellspacing="0"  style="width:85%; margin:20px auto;background:white">
		
		<thead>
		
			<tr>
		 		<th class="textAlignLeft" style="width:30%">
					<span class="mL5">Substance name </span>
				</th>
				<th class="textAlignCenter"  style="width:13%">
					<span class=""> Used and injected</span>
				</th>
				<th class="textAlignCenter"  style="width:13%">
					<span class=""> Used and did not inject</span>
				</th>
				<th class="textAlignCenter"  style="width:13%">
					<span class=""> Used and unknown if injected</span>
				</th>
				<th class="textAlignCenter"  style="width:13%">
					<span class=""> Did not use</span>
				</th>
				<th class="textAlignCenter"  style="width:13%">
					<span class=""> Unknown if used</span>
				</th>
				
				
			</tr>
		
		</thead>
		<tbody class="bpUseSubstanceDepElement">
			<c:forEach  items="${acrfFormVO.rawAcrfDoc.bpUseSubstanceList}" var="subStance" varStatus="statusHNT">
				<tr class="subStanceSection">	
				 	<td class="textAlignLeft">
						<span class="mL5"> <c:out value="${subStance.subStanceName}" /></span>
						<form:hidden path="rawAcrfDoc.bpUseSubstanceList[${statusHNT.index}].subStancecode" substancecode="${subStance.refCd}" class="displayNone substanceCode" />
						<c:if test="${subStance.refCd == '17'}">
							<form:input path="rawAcrfDoc.bpUseSubstanceList[${statusHNT.index}].otherDetails" id="rawAcrfDoc_bpUseSubstanceList${statusHNT.index}_otherDetails" class="substanceDataText standardInputSmallText  " title="Other Substance" />
						</c:if>
					</td>
					<td class="textAlignCenter">
						<span class=""> <form:radiobutton path="rawAcrfDoc.bpUseSubstanceList[${statusHNT.index}].subUseResult" value="INJ" class="substanceData"/></span>
					</td>
					<td class="textAlignCenter">
						<span class=""> <form:radiobutton path="rawAcrfDoc.bpUseSubstanceList[${statusHNT.index}].subUseResult" value="NOT_INJ" class="substanceData"/></span>
					</td>
					<td class="textAlignCenter">
						<span class=""> <form:radiobutton path="rawAcrfDoc.bpUseSubstanceList[${statusHNT.index}].subUseResult" value="INJ_UNK" class="substanceData"/></span>
					</td>
					<td class="textAlignCenter">
						<span class=""> <form:radiobutton path="rawAcrfDoc.bpUseSubstanceList[${statusHNT.index}].subUseResult" value="NOT_USE" class="substanceData"/></span>
					</td>
					<td class="textAlignCenter">
						<span class=""> <form:radiobutton path="rawAcrfDoc.bpUseSubstanceList[${statusHNT.index}].subUseResult" value="UNK" class="substanceData"/></span>
					</td>
				</tr>
			</c:forEach>
		</table>
				
				
		</div>
	</div>
	
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<span class="mL10 hdrFont"> XI. Birthing Person History (for patients exposed perinatally with or without consequent infection) </span>
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	<div class="  toggleSection" style="width:100%; "  dependentElement="bpScreenSubstanceDepElement">
	<div style="float:left; width:100%; background:#DDDDDD" class=" container pB5 roundedBorderFont"> 
		<div style="border-right:1px dotted;  ">
		
				<span class="mL5 bold"> Was a toxicology screen done on the birthing person (either during this pregnancy or at the time of delivery)?</span>
				<span class="mL10"><form:radiobutton path="rawAcrfDoc.bpScreenSubstance" id="rawAcrfDoc_bpScreenSubstance" value="Y" /> Yes </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpScreenSubstance" id="rawAcrfDoc_bpScreenSubstance" value="N" /> No </span>
				<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpScreenSubstance" id="rawAcrfDoc_bpScreenSubstance" value="U"  /> Unknown</span>
		</div>
		<div class="clearBoth"> 
				<span class="mL5">(If screening for the same substance was done on more than one occasion, record additional dates and results in Comments)</span>
		</div>
	</div>

	<div style="float:left; width:100%;background:#DDDDDD" class=" container pB5 ">
	
		<table border="1" cellpadding="0" cellspacing="0" class="subStanceTable" style="width:85%;margin: 20px auto;background:white">
		
		<thead>
		
			<tr>
		 		<th class="textAlignLeft" style="width:30%">
					<span class="mL5"> Substance name</span>
				</th>
				<th class="textAlignCenter"  style="width:18%">
					<span class=""> Not screened</span>
				</th>
				<th class="textAlignCenter"   style="width:18%">
					<span class=""> Date of screen</span>
				</th>
				<th class="textAlignCenter"   style="width:34%">
					<span class=""> Result</span>
				</th>
			</tr>
		
		</thead>
		<tbody class="bpScreenSubstanceDepElement">
			<c:forEach  items="${acrfFormVO.rawAcrfDoc.bpScreenSubstanceList}" var="subStance" varStatus="statusHNT">
				<tr class="subStanceSection">	
				 	<td class="textAlignLeft">
						<span class="mL5"> <c:out value="${subStance.subStanceName}" /></span>
						<form:hidden path="rawAcrfDoc.bpScreenSubstanceList[${statusHNT.index}].subStancecode" substancecode="${subStance.refCd}" class="displayNone substanceCode" />
						<c:if test="${subStance.refCd == '17'}">
							<form:input path="rawAcrfDoc.bpScreenSubstanceList[${statusHNT.index}].otherDetails" id="rawAcrfDoc_bpScreenSubstanceList${statusHNT.index}_otherDetails" class="substanceDataText standardInputSmallText  " title="Other Substance" />
						</c:if>
						
						
					</td>
					<td class="textAlignCenter">
						<span class=""> <form:checkbox path="rawAcrfDoc.bpScreenSubstanceList[${statusHNT.index}].screeningDone" value="N"   class="substanceData"/></span>
					</td>
					<td class="textAlignCenter">
						<span class=""> <form:input path="rawAcrfDoc.bpScreenSubstanceList[${statusHNT.index}].screenDate" id="rawAcrfDoc_bpScreenSubstanceList${statusHNT.index}_screenDate" class="substanceDate toxicDateOnDel standardInputSmallText  dPicker mask" datevalidations="partialDateForAll" title="Screen date" /></span>
						<br/>
						<span class="mL5 v_error rawAcrfDoc_bpScreenSubstanceList${statusHNT.index}_screenDate"></span>
					</td>
					<td class="textAlignCenter">
						<span class=""><form:radiobutton path="rawAcrfDoc.bpScreenSubstanceList[${statusHNT.index}].screenResult" id="rawAcrfDoc_bpScreenSubstanceList${statusHNT.index}_screenResult" value="P" title="Substance Result"   class="substanceData" /> Positive</span>
						<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpScreenSubstanceList[${statusHNT.index}].screenResult" id="rawAcrfDoc_bpScreenSubstanceList${statusHNT.index}_screenResult" value="N" title="Substance Result"   class="substanceData" /> Negative</span>
						<span class="mL5"><form:radiobutton path="rawAcrfDoc.bpScreenSubstanceList[${statusHNT.index}].screenResult" id="rawAcrfDoc_bpScreenSubstanceList${statusHNT.index}_screenResult" value="U" title="Substance Result"   class="substanceData" /> Unknown</span>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	
</div>	
	
	
	
</div>
