<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 	

 	<h3 style="padding:1em"><span class="accordionHeader" style="left: 2em;top: 50%;margin-top: -8px;position: absolute;">TTH Document(s)</span></h3>
	
 	<div id="personHIVDetails<c:out value='${tthCounter.index}' />" class="container personHIVDetailsForm" >
		<div class="pL20 pT10" style="height:28px; border-bottom: dotted 1px"> 	
		 	<div class="mL20" style="width:48%; float:left">
				<span class=" pB10"> Document ID: </span>
				<span class=" pB10"><form:input path="tTHDocumentVOList[${tthCounter.index}].documentId" class="standardInputMedText mL20" readonly="true"/></span>
			</div>
			<div style="float:right; width:48%" >
				<span class=" pB10"> eHARS Import Date: </span>
				<span class=" pB10"><form:input path="tTHDocumentVOList[${tthCounter.index}].tthEharsImportDate" class="standardInputMedText mL20" readonly="true"/></span>
			</div>
		</div>

		<div id="personHIVDetailsLeft<c:out value='${tthCounter.index}' />" class="clear personHIVDetailsLeft">
			<ul>
				<li>
					<span class="labelHIV">Date of patient interview </span>
					<span class="valueHIV"> <form:input path="tTHDocumentVOList[${tthCounter.index}].tthPtInterviewDate"  class="dPicker standardInputMedText"  maxlength="15"/> </span>
				</li>
				<li>
					<span class="labelHIV">Date of first POSITIVE HIV test (enter date of current test if it is first) </span>
					<span class="valueHIV"> <form:input path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosHivDate" class="dPicker standardInputMedText"  maxlength="15"/> </span>
				</li>
				<li style="padding-top:10px">
					<span class="labelHIV">Was the FIRST positive test anonymous? </span>
					<div class="valueHIV"> 
						<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosTestAnon" value="Y" /> Yes </span>
						<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosTestAnon" value="N" /> No </span>	
						<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosTestAnon" value="U" /> Unknown </span> 
					</div>
				</li>
				<li>
					<span>Location of First positive test(Select only one)<br>Type of facility (if known):</span><br>
					<span> 
						<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosFacType" value="01" /> HIV Counseling and Testing services </span><br>
						<span style="width:33%; float:left">
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosFacType" value="02" /> TB clinic </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosFacType" value="03" /> STD clinic </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosFacType" value="04" /> Drug treatment clinic </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosFacType" value="05" /> Family planning clinic </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosFacType" value="06" /> Prenatal/OB clinic </span>
						</span>
						<span style="width:33%; float:left">
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosFacType" value="07" /> Emergency room </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosFacType" value="08" /> Prison/Jail </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosFacType" value="09" /> Private doctor </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosFacType" value="10" /> Blood Bank </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosFacType" value="11" /> Outreach/Mobile </span>
						</span>
						<span  style="width:33%; float:left">
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosFacType" value="12" /> Community health clinic </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosFacType" value="13" /> Hospital </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosFacType" value="14" /> Other </span><br>
						</span>
					</span>	
					<div class="pT10 clear">
						<span class=""> Name of facility</span>
						<span class=""><form:input path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosFacName" class="mL20 standardInputSmallText"  maxlength="100"/></span>
						<span class="mL20"> State</span> 
						<span class=""><form:input path="tTHDocumentVOList[${tthCounter.index}].tthFirstPosFacState" class="mL20 standardInputSmallText"  maxlength="10"/></span>
					</div>
				</li>
				<li class="pT10">
					<span >
						<span class="width70 left pB10">Reason for FIRST positive test (Respond to each):</span> 
						<span class="width30 left pB10">
							<span class="pLR">Yes</span>
							<span>No</span>
						</span><br>
						<span class="width70 left pB10">Patient thinks he/she was exposed in 6 months prior to test</span>
						<span class="width30 left pB10"> 
							<span class="pLR"><form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthRsonFirstPosExp" value="Y" /></span>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthRsonFirstPosExp" value="N" /></span>	
						</span>	<br>
						<span class="width70 left pB10">Patient test routinely and it was time to test again</span>
						<span class="width30 left pB10"> 
							<span class="pLR"><form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthRsonFirstPosRoutn" value="Y" /></span>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthRsonFirstPosRoutn" value="N" /></span>	
						</span>	<br>
						<span class="width70 left pB10">Patient is checking to make sure he/she is HIV negative</span>
						<span class="width30 left pB10"> 
							<span class="pLR"><form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthRsonFirstPosChkneg" value="Y" /></span>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthRsonFirstPosChkneg" value="N" /></span>	
						</span>	<br>
						<span class="width70 left pB10">Required (court ordered, insurance, military, etc.)</span>
						<span class="width30 left pB10"> 
							<span class="pLR"><form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthRsonFirstPosReqd" value="Y" /></span>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthRsonFirstPosReqd" value="N" /></span>	
						</span>	<br>
						<span class="width70 left pB10">Notified that partner was HIV positive</span>
						<span class="width30 left pB10"> 
							<span class="pLR"><form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthRsonFirstPosPrtnr" value="Y" /></span>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthRsonFirstPosPrtnr" value="N" /></span>	
						</span>	<br>
						<span class="width70 left pB10">Some other reason <form:input path="tTHDocumentVOList[${tthCounter.index}].tthRsonFirstPosOtherTxt" class="dPicker ml10 standardInputMedText"  maxlength="75"/><br><span class="labelunderneath">(specify)</span></span>
						<span class="width30 left pB10"> 
							<span class="pLR"><form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthRsonFirstPosOther" value="Y" /></span>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthRsonFirstPosOther" value="N" /></span>	
						</span>	<br>
					</span>
				</li>	
			</ul>	
		</div>			

		<div id="personHIVDetailsRight<c:out value='${tthCounter}' />" class="personHIVDetailsRight">	
			<ol type="a" start="6">						
				<li>
					<span class="">Has patient taken any antiretrovirals in the past six months? </span> <br>
					<div class=" pB20"> 
						<span class="width25 left"> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthPtArv6Mo" value="Y" /> Yes </span>
						<span class="width25 left"> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthPtArv6Mo" value="N" /> No </span>
						<span class="width25 left"> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthPtArv6Mo" value="R" /> Refused</span>
						<span class="width25 left"> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthPtArv6Mo" value="U" /> Unknown</span>
					</div>	
					<div class="clear pT10">
						<span class="pB10"> if Yes, which</span>
						<span class="mL20 pB5"><form:input path="tTHDocumentVOList[${tthCounter.index}].tthPtArvName" class="standardInputText"  maxlength="500"/></span><br>
					</div>
					<div class="clear pT10">	
						<span class="pT10 pB10"> if Yes, start date</span> 
						<span class="ml10 pB5"><form:input path="tTHDocumentVOList[${tthCounter.index}].tthPtArvStartDate" class="standardInputSmallText"  maxlength="15"/></span>
						<span class="mL20 pB10"> End date</span>
						<span class="ml10 pB5"><form:input path="tTHDocumentVOList[${tthCounter.index}].tthPtArvEndDate" class="standardInputSmallText"  maxlength="15"/></span>
					</div>
					<span class="pB5 pT10 inlineBlock"> Was the patient taking any antiretrovirals at the time of the current HIV test?</span> 
					<span class="pB10"> 
						<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthPtArvTimeOfTest" value="Y" /> Yes </span>
						<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthPtArvTimeOfTest" value="N" /> No </span>
					</span>	
				</li>

				<li>
					<span>Date of first HIV test ever (negative, indeterminate, or positive) </span>
					<span> <form:input path="tTHDocumentVOList[${tthCounter.index}].tthFirstHivTestEver" class="dPicker standardInputSmallText ml10"  maxlength="15"/> </span>
				</li>	
				<li style="height:45px">
					<span>How many times did patient test for HIV in the 2 years before the FIRST positive test? <br>
						(including the test on date under b.)</span>
					<div> 
						<span> <form:input path="tTHDocumentVOList[${tthCounter.index}].tthCountHivTeszxt2Yrs" class="dPicker standardInputTinyText"  maxlength="5"/>&nbsp;&nbsp;times</span>
						<span> (<form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthRespHivTest2Yrs" value="R" /> Refused</span>
						<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthRespHivTest2Yrs" value="U" /> Unknown)</span>
					</div>
				</li> 
				<li>
					<span>Has patient ever tested negative for HIV before? </span>
					<div> 
						<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthEverHadNeg" value="N" /> No (if checked,got to section III) </span><br>
						<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthEverHadNeg" value="Y" /> Yes (If checked, answer j)</span><br>
						<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthEverHadNeg" value="R" /> Refused </span><br>
						<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthEverHadNeg" value="U" /> Unknown </span> 
					</div>
				</li>
				<li>
					<span>Date of last NEGATIVE HIV test <form:input path="tTHDocumentVOList[${tthCounter.index}].tthLastNegDate" value=""  class="dPicker standardInputSmallText ml10"  maxlength="15"/><br>
					 Type of facility (if known):</span> <br>
					 <span> 
						<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthLastNegFacType" value="01" /> HIV Counseling and Testing services </span><br>
						<span style="width:33%; float:left">
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthLastNegFacType" value="02" /> TB clinic </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthLastNegFacType" value="03" /> STD clinic </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthLastNegFacType" value="04" /> Drug treatment clinic </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthLastNegFacType" value="05" /> Family planning clinic </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthLastNegFacType" value="06" /> Prenatal/OB clinic </span>
						</span>
						<span style="width:33%; float:left">
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthLastNegFacType" value="07" /> Emergency room </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthLastNegFacType" value="08" /> Prison/Jail </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthLastNegFacType" value="09" /> Private doctor </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthLastNegFacType" value="10" /> Blood Bank </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthLastNegFacType" value="11" /> Outreach/Mobile </span>
						</span>
						<span  style="width:33%; float:left">
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthLastNegFacType" value="12" /> Community health clinic </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthLastNegFacType" value="13" /> Hospital </span><br>
							<span> <form:radiobutton path="tTHDocumentVOList[${tthCounter.index}].tthLastNegFacType" value="14" /> Other </span><br>
						</span>
					</span>	
					<div class="pT10 clear">
						<span class=""> Name of facility</span>
						<span class=""><form:input path="tTHDocumentVOList[${tthCounter.index}].tthLastNegFacName" class="mL20 standardInputSmallText"  maxlength="100"/></span>
						<span class="mL20"> State</span> 
						<span class=""><form:input path="tTHDocumentVOList[${tthCounter.index}].tthLastNegFacState" class="mL20 standardInputSmallText"  maxlength="10"/></span>	
					</div>						
				</li>	
			
			</ul>		
		</div>	
	</div>