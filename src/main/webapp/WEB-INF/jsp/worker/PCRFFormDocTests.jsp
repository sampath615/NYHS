<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="isOldAcrfValue" value="${acrfFormVO.rawAcrfDoc.isOldAcrf}" />

	


<span class="mL10 hdrFont"> IX. Laboratory Data 
					</span>
<div style="width:99.9%;" class="container"></div>	
<!-- <span class="mL10 hdrFont"> Documentation of Tests</span> -->
<div class="roundedBorderFont mL10" style="width:98%; background:#DDDDDD" >

		
		
		<div style="width:100%; background-color:#DBEAF9; padding:2px; " class="container">
				<span class="hdrFont mL5 bold"> Documentation of Tests</span>
		</div>	
			
		<div style="float:left; width:99%; padding:2px; margin-left:5px; margin-right:5px;" class="container"> 
			<div style="float:left; padding-bottom:5px; border-bottom:1px dotted; width:99%">
			 	<div>
					<span class="mL5 bold"> Did documented laboratory test results meet approved HIV diagnostic algorithm criteria?</span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.dxAlgorithm" id="rawAcrfDoc_dxAlgorithm" value="Y" /> Yes </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.dxAlgorithm" id="rawAcrfDoc_dxAlgorithm" value="N" /> No </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.dxAlgorithm" id="rawAcrfDoc_dxAlgorithm" value="U" /> Unknown </span>
				</div>
				<div style="padding-top:5px;">
					<span class=" mL5 bold"> If YES, provide specimen collection date of earliest positive test result for this algorithm: <form:input path="rawAcrfDoc.dxAlgorithmDate" id="rawAcrfDoc_dxAlgorithmDate" datevalidations="partialDate" value="" class="standardInputMedText dPicker mask datepicker maskDate " maxlength="10" title="Date"  /></span>
					<div class="v_error rawAcrfDoc_dxAlgorithmDate mL5"></div>
				</div>
				<div style="padding-top:5px;">
					<span class="mL5" style="font-style: italic">
						Complete the above only if none of the following were positive for <span class="bold">HIV-1</span>: Western blot, IFA, culture, quantitative NAAT (RNA or DNA), qualitative NAAT (RNA or DNA), HIV-1/2 type-differentiating immunoassay (supplemental test), stand-alone p24 antigen, or nucleotide sequence.	
					</span>
				</div>
			</div>
			
		</div>
			
		<div style=" width:99%; padding:2px; margin-left:5px; margin-right:5px;padding-bottom:5px; border-bottom:1px dotted;" class="container floatLeft pB5 pT5">
			
			<div style="width:90%;" class="floatLeft"> 
				<div >
					<span class="mL5 bold"> Is earliest evidence of diagnosis documented by a physician rather than by laboratory test results?</span>
				</div>
			</div>
		</div>
		
		
		<div style=" width:99%; padding:2px; margin-left:5px; margin-right:5px;padding-bottom:5px; border-bottom:1px dotted;" class="container floatLeft pB5 pT5">
			
			<div style=" width:75%;" class="floatLeft toggleSection" dependentElement="hivInfectedDepElement"> 
				<div style=" width:60%" class="floatLeft radProp" >
					<span class="mL5 bold"> HIV-infected</span>
					<span class="mL20"><form:radiobutton path="rawAcrfDoc.hivInfectedNotDocumented" id="rawAcrfDoc_hivInfectedNotDocumented" value="Y" /> Yes </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.hivInfectedNotDocumented" id="rawAcrfDoc_hivInfectedNotDocumented" value="N" /> No </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.hivInfectedNotDocumented" id="rawAcrfDoc_hivInfectedNotDocumented" value="U" /> Unknown </span>
				</div>
				<div style="width:40%"  class="floatLeft hivInfectedDepElement">
					<span class=" mL5 bold"> Date of diagnosis by physician</span>
					<span class="mL10"><form:input path="rawAcrfDoc.hivInfectedNotDocumentedDate" id="rawAcrfDoc_hivInfectedNotDocumentedDate" datevalidations="partialDate" class="standardInputMedText hivInfectedDepElement dPicker mask datepicker maskDate " maxlength="10" title="Date" /></span>
					<div class="v_error rawAcrfDoc_hivInfectedNotDocumentedDate mL5"></div>
				</div>
			</div>		
		</div>
		
		
		<div style=" width:99%; padding:2px; margin-left:5px; margin-right:5px;padding-bottom:5px; border-bottom:1px dotted;" class="container floatLeft pB5 pT5">
			
			
			<div style=" width:75%;" class="floatLeft toggleSection" dependentElement="nonHivInfectedDepElement"> 
				<div style=" width:60%" class="floatLeft radProp" >
					<span class="mL5 bold"> Not HIV-infected </span>
					<span class="mL20"><form:radiobutton path="rawAcrfDoc.nonHivInfectInformByPhy" id="rawAcrfDoc_nonHivInfectInformByPhy" value="Y" /> Yes </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.nonHivInfectInformByPhy" id="rawAcrfDoc_nonHivInfectInformByPhy" value="N" /> No </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.nonHivInfectInformByPhy" id="rawAcrfDoc_nonHivInfectInformByPhy" value="U" /> Unknown </span>
				</div>
				<div style="width:40%"  class="floatLeft nonHivInfectedDepElement">
					<span class=" mL5 bold"> Date of diagnosis by physician</span>
					<span class="mL10"><form:input path="rawAcrfDoc.nonHivInfectInformByPhyDate" id="rawAcrfDoc_nonHivInfectInformByPhyDate" datevalidations="partialDate" class="standardInputMedText nonHivInfectedDepElementdPicker dPicker mask datepicker maskDate " maxlength="10" title="Date" /></span>
					<div class="v_error rawAcrfDoc_nonHivInfectInformByPhyDate mL5"></div>
				</div>
			</div>		
		</div>
		
		
		
		
		
		
		
		
		
		
		
		
		
			
		 
<!-- 			<div style="float:left; padding-bottom:5px; border-bottom:1px dotted; width:99%;background:#DDDDDD !important;"> -->
<!-- 			 	<div> -->
<!-- 					<span class="mL5"> Did documented laboratory test results meet approved HIV diagnostic algorithm criteria?</span> -->
<%-- 					<span class="mL5"><form:radiobutton path="rawAcrfDoc.dxAlgorithm" id="rawAcrfDoc_dxAlgorithm" value="Y" /> Yes </span> --%>
<%-- 					<span class="mL5"><form:radiobutton path="rawAcrfDoc.dxAlgorithm" id="rawAcrfDoc_dxAlgorithm" value="N" /> No </span> --%>
<%-- 					<span class="mL5"><form:radiobutton path="rawAcrfDoc.dxAlgorithm" id="rawAcrfDoc_dxAlgorithm" value="U" /> Unknown </span> --%>
<!-- 				</div> -->
<!-- 				<div style="padding-top:5px;"> -->
<%-- 					<span class=" mL5"> If YES, provide specimen collection date of earliest positive test result for this algorithm: <form:input path="rawAcrfDoc.dxAlgorithmDate" id="rawAcrfDoc_dxAlgorithmDate" datevalidations="partialDate" value="" class="standardInputMedText dPicker mask datepicker maskDate " maxlength="10" title="Date"  /></span> --%>
<!-- 					<div class="v_error rawAcrfDoc_dxAlgorithmDate mL5"></div> -->
<!-- 				</div> -->
<!-- 				<div style="padding-top:5px;"> -->
<!-- 					<span class="mL5" style="font-style: italic"> -->
<!-- 					Complete the above only if none of the following were positive for <span class="bold">HIV-1</span>: Western blot, IFA, culture, quantitative NAAT (RNA or DNA), qualitative NAAT (RNA or DNA), HIV-1/2 type-differentiating immunoassay (supplemental test), stand-alone p24 antigen, or nucleotide sequence -->
					
<!-- 					</span> -->
<!-- 				</div> -->
<!-- 			</div> -->
			
			
			
<!-- 			<div style="float:left; padding-bottom:5px; border-bottom:1px dotted; width:99%; background:#DDDDDD !important;"> -->
<!-- 			 	<div> -->
					
<!-- 					<div> -->
<!-- 						<span class="mL5"> Is earliest evidence of diagnosis documented by a physician rather than by laboratory test results?</span><br/> -->
<!-- 					</div> -->
<!-- 					<div  style="padding-top:5px;"> -->
<!-- 						<span class="mL5">HIV-infected</span> -->
<%-- 						<span class="mL5"><form:radiobutton path="rawAcrfDoc.hivInfectedNotDocumented" id="rawAcrfDoc_hivInfectedNotDocumented" value="Y" /> Yes </span> --%>
<%-- 						<span class="mL5"><form:radiobutton path="rawAcrfDoc.hivInfectedNotDocumented" id="rawAcrfDoc_hivInfectedNotDocumented" value="N" /> No </span> --%>
<%-- 						<span class="mL5"><form:radiobutton path="rawAcrfDoc.hivInfectedNotDocumented" id="rawAcrfDoc_hivInfectedNotDocumented" value="U" /> Unknown </span> --%>
<%-- 						<span class=" mL20"> Date of diagnosis by physician <form:input path="rawAcrfDoc.hivInfectedNotDocumentedDate" id="rawAcrfDoc_hivInfectedNotDocumentedDate" datevalidations="partialDate" class="standardInputMedText dPicker mask datepicker maskDate " maxlength="10" title="Date" /></span> --%>
<!-- 						<div class="v_error rawAcrfDoc_hivInfectedNotDocumentedDate mL5"></div>	 -->
<!-- 					</div> -->
					
<!-- 					<div  style="padding-top:5px;"> -->
<!-- 						<span class="mL5">Not HIV-infected</span> -->
						
<%-- 						<span class="mL5"><form:radiobutton path="rawAcrfDoc.nonHivInfectInformByPhy" id="rawAcrfDoc_nonHivInfectInformByPhy" value="Y" /> Yes </span> --%>
<%-- 						<span class="mL5"><form:radiobutton path="rawAcrfDoc.nonHivInfectInformByPhy" id="rawAcrfDoc_nonHivInfectInformByPhy" value="N" /> No </span> --%>
<%-- 						<span class="mL5"><form:radiobutton path="rawAcrfDoc.nonHivInfectInformByPhy" id="rawAcrfDoc_nonHivInfectInformByPhy" value="U" /> Unknown </span> --%>
						
						
<%-- 						<span class=" mL20"> Date of diagnosis by physician <form:input path="rawAcrfDoc.nonHivInfectInformByPhyDate" id="rawAcrfDoc_nonHivInfectInformByPhyDate" datevalidations="partialDate" class="standardInputMedText dPicker mask datepicker maskDate " maxlength="10" title="Date" /></span> --%>
<!-- 						<div class="v_error rawAcrfDoc_nonHivInfectInformByPhyDate mL5"></div> -->
						
<!-- 					</div> -->
<!-- 				</div> -->
				
<!-- 			</div> -->
			
			
			
				
		</div>
		
<br/>


<div class="acrfLabSupText container clearBoth pT10 mL10">
				  	<span class="supText"><sup>2</sup></span><span class="SupDesc">Results not directly observed by a provider should be recorded in HIV Testing History.</span><br/>
					<span class="supText"><sup>3</sup></span><span class="SupDesc">Complete the overall interpretation and the analyte results.</span><br/>
					<span class="supText"><sup>4</sup></span><span class="SupDesc">Always complete the overall interpretation. Complete the analyte results when available.</span>
				</div>