<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div style="width:99.9%;" class="container"></div>		


		<div style="width:100%; background-color:#DBEAF9; padding:2px; " class="container">
				<span class="hdrFont mL5"> Documentation of Tests</span>
		</div>	
			
		<div style="float:left; width:99%; padding:2px; margin-left:5px; margin-right:5px;" class="container documentationTests"> 
			<div style="float:left; padding-bottom:5px; border-bottom:1px dotted; width:99%">
			 	<div>
					<span class="mL5"> Did documented laboratory test results meet approved HIV diagnostic algorithm criteria?</span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.dxAlgorithm" id="rawAcrfDoc_dxAlgorithm" value="Y" /> Yes </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.dxAlgorithm" id="rawAcrfDoc_dxAlgorithm" value="N" /> No </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.dxAlgorithm" id="rawAcrfDoc_dxAlgorithm" value="U" /> Unknown </span>
				</div>
				<div style="padding-top:5px;">
					<span class=" mL5"> If YES, provide specimen collection date of earliest positive test result for this algorithm: <form:input path="rawAcrfDoc.dxAlgorithmDate" id="rawAcrfDoc_dxAlgorithmDate" datevalidations="partialDate" value="" class="standardInputMedText dPicker mask datepicker maskDate " maxlength="10" title="Date"  /></span>
					<div class="v_error rawAcrfDoc_dxAlgorithmDate mL5"></div>
				</div>
				<div style="padding-top:5px;">
					<span class="mL5" style="font-style: italic">
					Complete the above only if none of the following were positive for <span class="bold">HIV-1</span>: Western blot, IFA, culture, quantitative NAAT (RNA or DNA), qualitative NAAT (RNA or DNA), HIV-1/2 type-differentiating immunoassay (supplemental test), stand-alone p24 antigen, or nucleotide sequence
					
					</span>
				</div>
			</div>
			
			
			
			<div style="float:left; padding-bottom:5px; border-bottom:1px dotted; width:99%">
			 	<div>
					<span class="mL5"> Is earliest evidence of HIV infection diagnosis documented by a physician rather than by laboratory test results?</span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.hivDiagnosisByPhysician" id="rawAcrfDoc_hivDiagnosisByPhysician" value="Y" /> Yes </span>
						<span class="mL5"><form:radiobutton path="rawAcrfDoc.hivDiagnosisByPhysician" id="rawAcrfDoc_hivDiagnosisByPhysician" value="N" /> No </span>
						<span class="mL5"><form:radiobutton path="rawAcrfDoc.hivDiagnosisByPhysician" id="rawAcrfDoc_hivDiagnosisByPhysician" value="U" /> Unknown </span>
				</div>
				<div style="padding-top:5px;">
					<span class=" mL5"> If YES, provide date of diagnosis by physician <form:input path="rawAcrfDoc.phyisicianDiagnosisDate" id="rawAcrfDoc_phyisicianDiagnosisDate" datevalidations="partialDate" value="${acrfFormVO.rawAcrfDoc.phyisicianDiagnosisDate}" class="standardInputMedText dPicker mask datepicker maskDate " maxlength="10" title="Date" /></span>
					<div class="v_error rawAcrfDoc_phyisicianDiagnosisDate mL5"></div>
				</div>
			</div>
			
			<div style="float:left; padding-bottom:5px; border-bottom:1px dotted; width:99%">
			 	<div>
					<span class="mL5"> Date of last documented negative HIV test result (before HIV diagnosis date)<form:input path="rawAcrfDoc.lastNegHivDate" id="rawAcrfDoc_lastNegHivDate" datevalidations="completeDate" value="${acrfFormVO.rawAcrfDoc.lastNegHivDate}" class="standardInputMedText dPicker mask datepicker maskDate " maxlength="10" title="Date" /></span>
					<div class="v_error rawAcrfDoc_lastNegHivDate mL5"></div>
				</div>
				<div style="padding-top:5px;">
					<span class="mL5"> Specify type of test:<form:input path="rawAcrfDoc.lastNegHivTest" id="rawAcrfDoc_lastNegHivTest" value="${acrfFormVO.rawAcrfDoc.lastNegHivTest}" class="standardInputText" maxlength="60" title="Specify type of test"/></span>
				</div>
			</div>
			
			
				
		</div>

		<div style="float:left; width:100%; padding-top:10px;" class="TestingOptions">
					<div style="float:left; ">
						<span class="hdr1Font"> Testing Option (if applicable) : </span>
					
						<span class="mL20"><form:radiobutton path="rawAcrfDoc.rapidTestType" id="rawAcrfDoc_rapidTestType" value="P"/></span>
						<span class="mL2">Point-of-care test by provider </span>
						
						<span class="mL10"><form:radiobutton path="rawAcrfDoc.rapidTestType" id="rawAcrfDoc_rapidTestType" value="S" /></span>
						<span class="mL2">Self-test, result directly observed by a provider<sup>2</sup> </span>
						
						<span class="mL10"><form:radiobutton path="rawAcrfDoc.rapidTestType" id="rawAcrfDoc_rapidTestType" value="L" /></span>
						<span class="mL2">Lab test, self-collected sample </span>			
					</div>
					
					
				
				</div>
		
<!-- Page3 second section  -->
<br/>


		<div class="acrfLabSupText container clearBoth pT10">
						  	<span class="supText"><sup>2</sup></span><span class="SupDesc">Results not directly observed by a provider should be recorded in HIV Testing History.</span><br/>
							<span class="supText"><sup>3</sup></span><span class="SupDesc">Complete the overall interpretation and the analyte results.</span><br/>
							<span class="supText"><sup>4</sup></span><span class="SupDesc">Always complete the overall interpretation. Complete the analyte results when available.</span>
						</div>