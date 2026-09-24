<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%-- This jsp belongs to "PT Hist" Second Tab --%> 
<!-- first  -->
<c:if test="${acrfRevison < 2022}">
	
<div class="normalBorder mL10" style="width:98%; background:#DDDDDD" >

	<div style="width:99%; margin-right:5px;">
		<div style="float:left; width:30%; padding-bottom:5px; background-color:#DBEAF9;"> 
			<span class="hdr1Font mL5 mT2"> STATE/LOCAL USE ONLY</span>
		</div>	
<!-- 		<div style="float:right;width:69% ; padding-bottom:5px;">  -->
<!-- 			<span class="hdr1Font mL85"> - Patient identifier information is not transmitted to CDC! - </span><br/> -->
<!-- 		</div> -->
	</div>
	
	<div style="width:99%;"  class="clearBoth mL5 mR10">
		<span class="hdrFont"> Provider Name:</span> <br/>
		<div style="float:left; width:76%; padding-bottom:5px;" class="container"> 					
			<div style="float:left; width: 175px">
				<span class="mL10 hdrFont"> <spring:message code="label.firstname" /></span>  <br/> <span class="mL10 "> <form:input path="rawAcrfDoc.slProviderFname" id="rawAcrfDoc_slProviderFname"value="${acrfFormVO.rawAcrfDoc.slProviderFname}" class="standardInputSmallText " cssErrorClass="error" maxlength="25" title="First Name"/> </span>
				<div class="v_error rawAcrfDoc_slProviderFname mL10"></div>
			</div>
			<div style="float:left; width: 180px">
				<span class="mL10 hdrFont"> <spring:message code="label.middleName" /></span> <br/> <span class="mL10 "> <form:input path="rawAcrfDoc.slProviderMname" id="rawAcrfDoc_slProviderMname" value="${acrfFormVO.rawAcrfDoc.slProviderMname}" class="standardInputSmallText" cssErrorClass="error"  maxlength="25" title="Middle Name"/> </span>
				<div class="v_error rawAcrfDoc_slProviderMname mL10"></div>
			</div>
			<div style="float:left; width: 180px">
				<span class="mL10 hdrFont"> <spring:message code="label.lastname" /></span> <br/> <span class="mL10 "> <form:input path="rawAcrfDoc.slProviderLname" id="rawAcrfDoc_slProviderLname" value="${acrfFormVO.rawAcrfDoc.slProviderLname}" class="standardInputSmallText" cssErrorClass="error"  maxlength="25" title="Last Name"/> </span>
				<div class="v_error rawAcrfDoc_slProviderLname mL10"></div>
			</div>		
			<div style="float:left">
				<span class="mL10 hdrFont"> <spring:message code="label.suffix" /></span> <br/> <span class="mL10 "> <form:input path="rawAcrfDoc.slProviderSuffix" id="rawAcrfDoc_slProviderSuffix" value="${acrfFormVO.rawAcrfDoc.slProviderSuffix}" class="standardInputSmallText1" cssErrorClass="error"  maxlength="10" title="Suffix"/> </span>
			</div>	
			<div style="float:left">
				<span class="mL10 hdrFont"> <spring:message code="label.phoneNumber" />:</span> <br/> <span class="mL10 "> <form:input path="rawAcrfDoc.slProviderPhone" id="rawAcrfDoc_slProviderPhone" value="${acrfFormVO.rawAcrfDoc.slProviderPhone}" class="pNumber standardInputMedText mL5 font10pt"  maxlength="20" title="Phone Number"/> </span>
				<div class="v_error rawAcrfDoc_slProviderPhone mL10"></div>
			</div>				
		</div>
		
	</div>	
	
	
	<div style="width:99.9%;" class="container"></div>
	
	<div style="width:99%;"  class="mL5 mR5" >
		<div style="float:left; width:99%; padding-bottom:5px;"> 
			<span class="mL5 hdrFont"> Hospital/Facility:</span> 
			<span class="mL5"> <form:input path="rawAcrfDoc.slFacilityName" id="rawAcrfDoc_slFacilityName" value="${acrfFormVO.rawAcrfDoc.slFacilityName}"  class="standardInputMedText"  maxlength="80" title="Hospital/Facility"/> </span>
		</div>
	</div>
	<div style="width:99.9%;" class="container"></div>
</div>
	
	</c:if>

<div style="width:99.9%;" class="container"></div>

<!-- page2 Second block starts -->
<div class="v_error HIVFacilityTab mL10"> </div>
<div class="v_error AIDSFacilityTab mL10"> </div>
<br/>
<span class="mL10 hdrFont"> VI. Facility of Diagnosis (add additional facilities in Comments) </span>
<div id="facilityTabs" class="container" style="background-color: transparent; width:97.5%; margin-left:10px; background:#DDDDDD">
	<ul id="facilityTabs1">
		<li><a href="#HIVFacilityTab">HIV Diagnosis</a></li>				
		<li><a href="#AIDSFacilityTab">AIDS Diagnosis</a></li> 
		<li><a href="#PerinatalFacilityTab">Perinatal exposure</a></li>
	</ul>					
	<div id="HIVFacilityTab">				
		<%@ include file="../worker/FacilityOfDiagnosisHIV.jsp" %> 	
	</div>	
	<div id="AIDSFacilityTab">						
		<%@ include file="../worker/FacilityOfDiagnosisAIDS.jsp" %>
	</div>
	<div id="PerinatalFacilityTab">						
		<%@ include file="../worker/FacilityOfDiagnosisPerinatal.jsp" %>
	</div>
</div>
<div style="width:99.9%;" class="container"></div>
<!-- Page2 Third block -->

<br/>
<span class="mL10 hdrFont"> VII. Patient History (respond to all questions) &nbsp;(record all dates as mm/dd/yyyy)</span>
<div class="roundedBorder mL10" style="width:98%; height: auto; background:#DDDDDD" >
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div class="pB5" > 
			<span class="normalFont"> Birthing person's HIV infection status (select one): </span>
			
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.childMomHivInfectionStatus"  id="rawAcrfDoc_childMomHivInfectionStatus" value="R" title="Child's biological mother's HIV infection status" /> Refused HIV testing </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.childMomHivInfectionStatus"  id="rawAcrfDoc_childMomHivInfectionStatus" value="K" title="Child's biological mother's HIV infection status" /> Known to be uninfected after this child's birth </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.childMomHivInfectionStatus"  id="rawAcrfDoc_childMomHivInfectionStatus" value="B" title="Child's biological mother's HIV infection status" /> Known HIV+ before pregnancy </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.childMomHivInfectionStatus"  id="rawAcrfDoc_childMomHivInfectionStatus" value="D" title="Child's biological mother's HIV infection status" /> Known HIV+ during pregnancy </span>
			<br/>
			<span class="mL5 "><form:radiobutton path="rawAcrfDoc.childMomHivInfectionStatus"  id="rawAcrfDoc_childMomHivInfectionStatus" value="S" title="Child's biological mother's HIV infection status" /> Known HIV+ sometime before birth </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.childMomHivInfectionStatus"  id="rawAcrfDoc_childMomHivInfectionStatus" value="A" title="Child's biological mother's HIV infection status" /> Known HIV+ at delivery </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.childMomHivInfectionStatus"  id="rawAcrfDoc_childMomHivInfectionStatus" value="C" title="Child's biological mother's HIV infection status" /> Known HIV+ after child's birth </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.childMomHivInfectionStatus"  id="rawAcrfDoc_childMomHivInfectionStatus" value="T" title="Child's biological mother's HIV infection status" /> HIV+, time of diagnosis unknown </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.childMomHivInfectionStatus"  id="rawAcrfDoc_childMomHivInfectionStatus" value="U" title="Child's biological mother's HIV infection status" /> HIV status unknown </span>
			
		</div>
		<div class="v_error rawAcrfDoc_childMomHivInfectionStatus"></div>
	</div>
	
	<div style="width:99.9%; border:1px dotted;" class="container"></div>

	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		
		
		<c:choose>
			<c:when test="${acrfRevison < 2022}">
				
				<div style="float:left; width:50%; border-right:1px dotted;  padding-bottom:5px; "> 
					<span class="normalFont"> Date of birthing person's first positive test result to confirm infection </span>
					<span> <form:input path="rawAcrfDoc.motherFirstPosTestDate" id="rawAcrfDoc_motherFirstPosTestDate" datevalidations="partialDate" class="standardInputSmallText dPicker mask momInfo" maxlength="10" title="Date of mother's first positive test to confirm infection" />   </span>
					<div class="v_error rawAcrfDoc_motherFirstPosTestDate"></div>
				</div>
			
				<div id="mother_motherCounseledHivPregnancy" style="float:right; width:49%; padding-bottom:5px;">
					<span class="normalFont"> Was the biological mother counseled about HIV testing during this pregnancy, labor, or delivery?  </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherCounseledHivPregnancy"  id="rawAcrfDoc_motherCounseledHivPregnancy" value="Y" title="Sex with male" /> Yes </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherCounseledHivPregnancy"  id="rawAcrfDoc_motherCounseledHivPregnancy" value="N" title="Sex with male" /> No </span>
					<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherCounseledHivPregnancy"  id="rawAcrfDoc_motherCounseledHivPregnancy" value="U" title="Sex with male" /> Unknown </span>
					<div class="v_error mother_motherCounseledHivPregnancy"></div>
				</div>
			</c:when>
			<c:otherwise>
				<div style="float:left; width:50%; border-right:1px dotted;  padding-bottom:5px; "> 
					<span class="normalFont"> Date of birthing person's first positive test result to confirm infection </span>
					<span> <form:input path="rawAcrfDoc.motherFirstPosTestDate" id="rawAcrfDoc_motherFirstPosTestDate" datevalidations="partialDate" class="standardInputSmallText dPicker mask momInfo" maxlength="10" title="Date of mother's first positive test to confirm infection" />   </span>
					<div class="v_error rawAcrfDoc_motherFirstPosTestDate"></div>
				</div>
			
				<div id="mother_motherCounseledHivPregnancy" style="float:right; width:49%; padding-bottom:5px;">
					<div class="toggleSection" dependentElement="childBreastfedDepElement">
						<div>
							<span class="normalFont"> Child breastfed/chestfed by birthing person?  </span>
							<span class="mL5"><form:radiobutton path="rawAcrfDoc.childBreastfedBp"  id="rawAcrfDoc_childBreastfedBp" value="Y" title="Child breastfed/chestfed by birthing person?" /> Yes </span>
							<span class="mL5"><form:radiobutton path="rawAcrfDoc.childBreastfedBp"  id="rawAcrfDoc_childBreastfedBp" value="N" title="Child breastfed/chestfed by birthing person?" /> No </span>
							<span class="mL5"><form:radiobutton path="rawAcrfDoc.childBreastfedBp"  id="rawAcrfDoc_childBreastfedBp" value="U" title="Child breastfed/chestfed by birthing person?" /> Unknown </span>
							<div class="v_error mother_motherCounseledHivPregnancy"></div>
						</div>
						
						<div class="childBreastfedDepElement">
							<span class="normalFont"> (If yes)?  </span>
							<span class="mL10 normalFont">Start Date </span>
						    <span> <form:input path="rawAcrfDoc.childBreastfedStartDate" id="rawAcrfDoc_childBreastfedStartDate" datevalidations="partialDate" class="standardInputSmallText dPicker mask momInfo childBreastfedDepElement required" maxlength="10" title="Child breastfed start date" />   </span>
							<span class="mL10 normalFont">Stop Date </span>
						    <span> <form:input path="rawAcrfDoc.childBreastfedStopDate" id="rawAcrfDoc_childBreastfedStopDate" datevalidations="partialDate" class="standardInputSmallText dPicker mask momInfo childBreastfedDepElement required" maxlength="10" title="Child breastfed stop date" />   </span>
						    <div class="v_error rawAcrfDoc_childBreastfedStartDate"></div>
						    <div class="v_error rawAcrfDoc_childBreastfedStopDate"></div>
							<div class="v_error mother_childBreastfedDates"></div>
						</div>
					</div>
					
					<div>
						<span class="normalFont"> Child received premasticated/pre-chewed food from birthing person?  </span>
						<span class="mL5"><form:radiobutton path="rawAcrfDoc.childPrechewedFoodBp"  id="rawAcrfDoc_childPrechewedFoodBp" value="Y" title="Child received premasticated/pre-chewed food from birthing person?" /> Yes </span>
						<span class="mL5"><form:radiobutton path="rawAcrfDoc.childPrechewedFoodBp"  id="rawAcrfDoc_childPrechewedFoodBp" value="N" title="Child received premasticated/pre-chewed food from birthing person?" /> No </span>
						<span class="mL5"><form:radiobutton path="rawAcrfDoc.childPrechewedFoodBp"  id="rawAcrfDoc_childPrechewedFoodBp" value="U" title="Child received premasticated/pre-chewed food from birthing person?" /> Unknown </span>
						<div class="v_error mother_motherCounseledHivPregnancy"></div>
					</div>
				</div>			
			</c:otherwise>
		</c:choose>
		
		
	</div>	
	
	<div style="width:99.9%; border:1px dotted;" class="container"></div>
	
	<div style="width:100%; padding-bottom:5px; background-color:#DBEAF9">
			<span class="hdrFont mL5"> 
				<c:choose>
					<c:when test="${acrfRevison < 2022}">
						After 1977 and before the earliest known diagnosis of HIV infection, this child's biological mother had:
					</c:when>
					<c:otherwise>
						After 1977 and before the earliest known diagnosis of HIV infection, the birthing person had:
					</c:otherwise>
				</c:choose>
			
			</span><span class="ml5" id="patientHistoryInstructionNote"></span>
	</div>
	
	
	<div style="width:99.9%; border:1px dotted;" class="container"></div>

	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> Perinatally acquired HIV infection </span>
		</div>
		<div id="mother_perinatalHivInfection" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.perinatalHivInfection"  id="rawAcrfDoc_perinatalHivInfection" value="Y" title="Perinatally acquired HIV infection" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.perinatalHivInfection"  id="rawAcrfDoc_perinatalHivInfection" value="N" title="Perinatally acquired HIV infection" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.perinatalHivInfection"  id="rawAcrfDoc_perinatalHivInfection" value="U" title="Perinatally acquired HIV infection" /> Unknown </span>
			<div class="v_error mother_perinatalHivInfection"></div>
		</div>
	</div>	
	
	<div style="width:99.9%; border:1px dotted;" class="container"></div>
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> Injected nonprescription drugs </span>
		</div>
		<div id="mother_idu" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.idu"  id="rawAcrfDoc_idu" value="Y" title="Injected nonprescription drugs" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.idu"  id="rawAcrfDoc_idu" value="N" title="Injected nonprescription drugs" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.idu"  id="rawAcrfDoc_idu" value="U" title="Injected nonprescription drugs" /> Unknown </span>
			<div class="v_error mother_idu"></div>
		</div>
	</div>	
	
	
	<div style="width:99.9%; border:1px dotted;" class="container"></div>
	
	<div style="width:100%; padding-bottom:5px; background-color:#DBEAF9">
		<c:choose>
		<c:when test="${acrfRevison < 2022}">
			<span class="hdrFont mL5"> Biological mother had HETEROSEXUAL relations with any of the following:</span>
		</c:when>
		<c:otherwise>
			<span class="hdrFont mL5"> Birthing person had HETEROSEXUAL relations with any of the following:</span>		
		</c:otherwise>
	</c:choose>
			
			
	</div>
	
	<div style="width:99.9%; border:1px dotted;" class="container"></div>
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> HETEROSEXUAL contact with person who injected drugs </span>
		</div>
		<div id="patient_heterosexual_contact_injection_drug_user" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.heterosexWithIdu"  id="rawAcrfDoc_heterosexWithIdu" class="HeteroYes" value="Y" title="Intravenous/Injection drug user" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.heterosexWithIdu"  id="rawAcrfDoc_heterosexWithIdu" value="N" title="Intravenous/Injection drug user" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.heterosexWithIdu"  id="rawAcrfDoc_heterosexWithIdu" value="U" title="Intravenous/Injection drug user" /> Unknown </span>
			<div class="v_error patient_heterosexual_contact_injection_drug_user"></div>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> HETEROSEXUAL contact with bisexual male </span>
		</div>
		<div id="patient_heterosexual_contact_with_bisexual_male" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.heterosexWithMsm"  id="rawAcrfDoc_heterosexWithMsm" class="HeteroYes" value="Y" title="Contact with bisexual male" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.heterosexWithMsm"  id="rawAcrfDoc_heterosexWithMsm" value="N" title="Contact with bisexual male" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.heterosexWithMsm"  id="rawAcrfDoc_heterosexWithMsm" value="U" title="Contact with bisexual male" /> Unknown </span>
			<div class="v_error patient_heterosexual_contact_with_bisexual_male"></div>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> HETEROSEXUAL contact with person with hemophilia / coagulation disorder with documented HIV infection </span>
		</div>
		<div id="patient_heterosexual_contact_person_with_hemophilia" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.heterosexWithHemophiliac"  id="rawAcrfDoc_heterosexWithHemophiliac" class="HeteroYes" value="Y" title="Person with hemophilia" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.heterosexWithHemophiliac"  id="rawAcrfDoc_heterosexWithHemophiliac" value="N" title="Person with hemophilia" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.heterosexWithHemophiliac"  id="rawAcrfDoc_heterosexWithHemophiliac" value="U" title="Person with hemophilia" /> Unknown </span>
			<div class="v_error patient_heterosexual_contact_person_with_hemophilia"></div>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> HETEROSEXUAL contact with transfusion recipient with documented HIV infection </span>
		</div>
		<div id="patient_heterosexual_contact_with_transfusion_recipient" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.heterosexWithTransfusion"  id="rawAcrfDoc_heterosexWithTransfusion" class="HeteroYes" value="Y" title="Transfusion recipient with Documented HIV infection" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.heterosexWithTransfusion"  id="rawAcrfDoc_heterosexWithTransfusion" value="N" title="Transfusion recipient with Documented HIV infection" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.heterosexWithTransfusion"  id="rawAcrfDoc_heterosexWithTransfusion" value="U" title="Transfusion recipient with Documented HIV infection" /> Unknown </span>
			<div class="v_error patient_heterosexual_contact_with_transfusion_recipient"></div>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> HETEROSEXUAL contact with transplant recipient with documented HIV infection </span>
		</div>
		<div id="patient_heterosexual_contact_with_transplant_recipient" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.heterosexWithTransplant"  id="rawAcrfDoc_heterosexWithTransplant" class="HeteroYes" value="Y" title="Transplant recipient with HIV infection" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.heterosexWithTransplant"  id="rawAcrfDoc_heterosexWithTransplant" value="N" title="Transplant recipient with HIV infection" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.heterosexWithTransplant"  id="rawAcrfDoc_heterosexWithTransplant" value="U" title="Transplant recipient with HIV infection" /> Unknown </span>
			<div class="v_error patient_heterosexual_contact_with_transplant_recipient"></div>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> HETEROSEXUAL contact with person with documented HIV infection, risk not specified </span>
		</div>
		<div id="patient_heterosexual_contact_with_person_with_AIDS" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.heterosexWithHiv"  id="rawAcrfDoc_heterosexWithHiv" class="HeteroYes" value="Y" title="Risk not specified" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.heterosexWithHiv"  id="rawAcrfDoc_heterosexWithHiv" value="N" title="Risk not specified" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.heterosexWithHiv"  id="rawAcrfDoc_heterosexWithHiv" value="U" title="Risk not specified" /> Unknown </span>
			<div class="v_error patient_heterosexual_contact_with_person_with_AIDS"></div>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="width:100%; padding-bottom:5px; background-color:#DBEAF9">
			<span class="hdrFont mL5"> Birthing person had:</span>
	</div>
	
	<div style="width:99.9%; border:1px dotted;" class="container"></div>
	
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5"> 
		<div style="float:left; width:80%;  padding-bottom:5px; "> 
			<span class="normalFont"> Received transfusion of blood/blood components (other than clotting factor) (document reason in Comments)</span><br/>
			<span class="normalFont"> First date received: </span><form:input path="rawAcrfDoc.motherFirstRecvBloodDate" id="rawAcrfDoc_motherFirstRecvBloodDate" datevalidations="partialDate" class="standardInputMedText dPicker mask momInfo" maxlength="1" title="First blood received transfusion date "/>
			<span class="normalFont mL20"> Last date received 
				<form:input path="rawAcrfDoc.motherLastRecvBloodDate" id="rawAcrfDoc_motherLastRecvBloodDate" datevalidations="partialDate" class="standardInputMedText dPicker mask datepicker maskDate momInfo" maxlength="10" title="Last blood received transfusion date " />
			</span>
			
			<div class="v_error rawAcrfDoc_motherFirstRecvBloodDate"></div>
			<div class="v_error rawAcrfDoc_motherLastRecvBloodDate"></div>
			
			
		</div>		
		<div id="mother_motherRecvBloodTransfusion" style="float:right; width:19%; padding-top:13px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvBloodTransfusion"  id="rawAcrfDoc_motherRecvBloodTransfusion" value="Y" title="Received transfusion of blood" class="PTYes"/> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvBloodTransfusion"  id="rawAcrfDoc_motherRecvBloodTransfusion" value="N" title="Received transfusion of blood" class="PTNo"/> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvBloodTransfusion"  id="rawAcrfDoc_motherRecvBloodTransfusion" value="U" title="Received transfusion of blood" class="PTUnknown"/> Unknown </span>
			<div class="v_error mother_motherRecvBloodTransfusion"></div>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> Received transplant of tissue/organs or artificial insemination </span>
		</div>
		<div id="mother_motherRecvTransplant" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvTransplant"  id="rawAcrfDoc_motherRecvTransplant" class="PTYes" value="Y" title="Received transplant of tissue/organs" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvTransplant"  id="rawAcrfDoc_motherRecvTransplant" class="PTNo" value="N" title="Received transplant of tissue/organs" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.motherRecvTransplant"  id="rawAcrfDoc_motherRecvTransplant" class="PTUnknown" value="U" title="Received transplant of tissue/organs" /> Unknown </span>
			<div class="v_error mother_motherRecvTransplant"></div>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="width:100%; padding-bottom:5px; background-color:#DBEAF9">
			<span class="hdrFont mL5"> Before the diagnosis of HIV infection, this child had:</span>
	</div>
	
	<div style="width:99.9%; border:1px dotted;" class="container"></div>
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> Injected nonprescription drugs </span>
		</div>
		<div id="child_iduBeforeDiag" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.iduBeforeDiag"  id="rawAcrfDoc_iduBeforeDiag" value="Y" title="Injected nonprescription drugs" class="ptHistoryChecked"/> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.iduBeforeDiag"  id="rawAcrfDoc_iduBeforeDiag" value="N" title="Injected nonprescription drugs" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.iduBeforeDiag"  id="rawAcrfDoc_iduBeforeDiag" value="U" title="Injected nonprescription drugs" /> Unknown </span>
			<div class="v_error child_iduBeforeDiag"></div>
		</div>
	</div>
		
	<div style="width:99.9%; border:1px dotted;" class="container"></div>
	
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5"> 
		<div style="float:left; width:80%;  padding-bottom:5px; "> 
			<span class="normalFont"> Received clotting factor for hemophilia/coagulation disorder </span> <br/>
			<span class="normalFont"> Specify clotting factor: <form:input path="rawAcrfDoc.clottingFactorRecvBeforeDiag" id="rawAcrfDoc_clottingFactorRecvBeforeDiag" class="standardInputMedText" maxlength="100" title="Specify clotting fator"/></span>
			
			<span class="normalFont mL20"> Date received 
				<form:input path="rawAcrfDoc.clottingFactorBeforeDiagRecvDate" id="rawAcrfDoc_clottingFactorBeforeDiagRecvDate" datevalidations="partialDate" class="standardInputMedText dPicker mask datepicker maskDate" maxlength="10" title="Date received" />
				<div class="v_error rawAcrfDoc_clottingFactorBeforeDiagRecvDate"></div>
			</span>
			
		</div>		
		<div id="child_recvClottingFactorBeforeDiag" style="float:right; width:19%; padding-top:13px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.recvClottingFactorBeforeDiag"  id="rawAcrfDoc_recvClottingFactorBeforeDiag"value="Y" title="Coagulation disorder" class="ptHistoryChecked PTYes"/> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.recvClottingFactorBeforeDiag"  id="rawAcrfDoc_recvClottingFactorBeforeDiag" value="N" title="Coagulation disorder" class="PTNo"/> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.recvClottingFactorBeforeDiag"  id="rawAcrfDoc_recvClottingFactorBeforeDiag" value="U" title="Coagulation disorder" class="PTUnknown"/> Unknown </span>
			<div class="v_error child_recvClottingFactorBeforeDiag"></div>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>	
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> Received transfusion of blood/blood components (other than clotting factor) &nbsp; (document reason in Comments) </span><br/>
			<span class="normalFont" id="firstLastDatesReceived"> First date received 
			<form:input path="rawAcrfDoc.firstRecvBloodBeforeDiagDate"  id="rawAcrfDoc_firstRecvBloodBeforeDiagDate" datevalidations="partialDate" class="standardInputMedText dPicker mask datepicker maskDate" maxlength="10" title="First Date received" />
			 </span>
			 <span class="mL20 normalFont">Last date received 
			<form:input path="rawAcrfDoc.lastRecvBloodBeforeDiagDate" id="rawAcrfDoc_lastRecvBloodBeforeDiagDate" datevalidations="partialDate" class="standardInputMedText dPicker datepicker mask maskDate" maxlength="10" title="Last Date received" />
			</span>
			<div class="v_error firstLastDatesReceived"></div>
			<div class="v_error rawAcrfDoc_firstRecvBloodBeforeDiagDate"></div>
			<div class="v_error rawAcrfDoc_lastRecvBloodBeforeDiagDate"></div>		
		</div>
		<div id="child_recvBloodTransfusionBeforeDiag" style="float:right; width:19%; padding-top:15px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.recvBloodTransfusionBeforeDiag"  id="rawAcrfDoc_recvBloodTransfusionBeforeDiag" class="ptHistoryChecked PTYes" value="Y" title="Received tranfustion of blood" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.recvBloodTransfusionBeforeDiag"  id="rawAcrfDoc_recvBloodTransfusionBeforeDiag" class="PTNo" value="N" title="Received tranfustion of blood" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.recvBloodTransfusionBeforeDiag"  id="rawAcrfDoc_recvBloodTransfusionBeforeDiag" class="PTUnknown" value="U" title="Received tranfustion of blood" /> Unknown </span>
			<div class="v_error child_recvBloodTransfusionBeforeDiag"></div>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>	

	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> Received transplant of tissue/organs </span>
		</div>
		<div id="child_recvTransplantBeforeDiag" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.recvTransplantBeforeDiag"  id="rawAcrfDoc_recvTransplantBeforeDiag" class="ptHistoryChecked PTYes" value="Y" title="Received transplant of tissue/organs" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.recvTransplantBeforeDiag"  id="rawAcrfDoc_recvTransplantBeforeDiag" class="PTNo" value="N" title="Received transplant of tissue/organs" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.recvTransplantBeforeDiag"  id="rawAcrfDoc_recvTransplantBeforeDiag" class="PTUnknown" value="U" title="Received transplant of tissue/organs" /> Unknown </span>
			<div class="v_error child_recvTransplantBeforeDiag"></div>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>	
	
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> Sexual contact with male </span>
		</div>
		<div id="child_sexWithMaleBeforeDiag" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.sexWithMaleBeforeDiag"  id="rawAcrfDoc_sexWithMaleBeforeDiag" value="Y" title="Sex with male" class="ptHistoryChecked"/> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.sexWithMaleBeforeDiag"  id="rawAcrfDoc_sexWithMaleBeforeDiag" value="N" title="Sex with male" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.sexWithMaleBeforeDiag"  id="rawAcrfDoc_sexWithMaleBeforeDiag" value="U" title="Sex with male" /> Unknown </span>
			<div class="v_error child_sexWithMaleBeforeDiag"></div>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> Sexual contact with female </span>
		</div>
		<div id="child_sexWithFemaleBeforeDiag" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.sexWithFemaleBeforeDiag"  id="rawAcrfDoc_sexWithFemaleBeforeDiag" value="Y" title="Sex with female" class="ptHistoryChecked"/> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.sexWithFemaleBeforeDiag"  id="rawAcrfDoc_sexWithFemaleBeforeDiag" value="N" title="Sex with female" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.sexWithFemaleBeforeDiag"  id="rawAcrfDoc_sexWithFemaleBeforeDiag" value="U" title="Sex with female" /> Unknown </span>
			<div class="v_error child_sexWithFemaleBeforeDiag"></div>
		</div>
	</div>	
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> Been breastfed/chestfed by non-birthing person </span>
		</div>
		<div id="child_sexWithFemaleBeforeDiag" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.breastfedNonBp"  id="rawAcrfDoc_breastfedNonBp" value="Y" title="Been breastfed/chestfed by non-birthing person" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.breastfedNonBp"  id="rawAcrfDoc_breastfedNonBp" value="N" title="Been breastfed/chestfed by non-birthing person" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.breastfedNonBp"  id="rawAcrfDoc_breastfedNonBp" value="U" title="Been breastfed/chestfed by non-birthing person" /> Unknown </span>
			<div class="v_error child_breastfedNonBp"></div>
		</div>
	</div>
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> Received premasticated/pre-chewed food from non-birthing person </span>
		</div>
		<div id="child_sexWithFemaleBeforeDiag" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.prechewedFoodNonBp"  id="rawAcrfDoc_prechewedFoodNonBp" value="Y" title="pre-chewed food from non-birthing person" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.prechewedFoodNonBp"  id="rawAcrfDoc_prechewedFoodNonBp" value="N" title="pre-chewed food from non-birthing person" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.prechewedFoodNonBp"  id="rawAcrfDoc_prechewedFoodNonBp" value="U" title="pre-chewed food from non-birthing person" /> Unknown </span>
			<div class="v_error child_prechewedFoodNonBp"></div>
		</div>
	</div>
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> Other documented risk (please include detail in Comments section) </span>
		</div>
		<div id="child_otherRiskBeforeDiag" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.otherRiskBeforeDiag"  id="rawAcrfDoc_otherRiskBeforeDiag" class="PTYes ptHistoryChecked" value="Y" title="Other documented risk" /> Yes </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.otherRiskBeforeDiag"  id="rawAcrfDoc_otherRiskBeforeDiag" class="PTNo" value="N" title="Other documented risk" /> No </span>
			<span class="mL5"><form:radiobutton path="rawAcrfDoc.otherRiskBeforeDiag"  id="rawAcrfDoc_otherRiskBeforeDiag" class="PTUnknown" value="U" title="Other documented risk" /> Unknown </span>
			<div class="v_error child_otherRiskBeforeDiag"></div>
		</div>
	</div>	
	<div style="clear:both"></div>
</div>

<!-- Page2 last section  -->
<br/>


<span class="mL10 hdrFont bold"> VIII. Clinical: Opportunistic Illnesses (record all dates as mm/dd/yyyy)  
</span>
<div class="roundedBorder medfont mL10" style="width:98%; background:#DDDDDD" >

	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
<!-- 	<div style="width:100%; padding-bottom:5px; background-color:#DBEAF9"> -->
<!-- 			<span class="hdrFont mL5"> Opportunistic Illnesses:</span> -->
<!-- 	</div>	 -->

	<div style="width:99.9%; border:1px dotted;"></div>

	<%@ include file="../worker/PCRFOpportunisticIllnesses.jsp" %>
		
	<div style="width:99.9%; border:1px dotted;" class="container"></div>
	
	<div style="float:left; width:99%; padding:5px; margin-left:5px; margin-right:5px;"> 
		<span> &dagger; If a diagnosis date is entered for either tuberculosis diagnosis above, provide RVCT Case Number: <form:input path="rawAcrfDoc.tbRvctCaseNumber" id="rawAcrfDoc_tbRvctCaseNumber" class="standardInputText" maxlength="30" title="RVCT Case Number"/></span>
	</div>
	
	<div style="width:99.9%;" class="container"></div>
	
</div>

<br/>