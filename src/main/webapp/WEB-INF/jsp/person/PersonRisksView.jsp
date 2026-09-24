<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="personViewNumber" value="${param.personViewNumber}" />

<form:form method="post" modelAttribute="personRisksObj" id="personRisksForm${personViewNumber}">
	
	<div class="roundedBorder mL10" style="width:98%; height: auto; background:#DDDDDD" >

	<div style="width:100%; padding-bottom:5px; background-color:#DBEAF9">
			<span class="hdrFont mL5"> After 1977 and before the earliest known diagnosis of HIV infection, this patient had:</span>
	</div>

	<div style="width:99.9%; border:1px dotted;" class="container"></div>

	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> Sex with male </span>
		</div>
		<div id="patient_sex_with_male" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="swm" id="swm${personViewNumber}" value="Y" title="Sex with male" /> Yes </span>
			<span class="mL5"><form:radiobutton path="swm" id="swm${personViewNumber}" value="N" title="Sex with male" /> No </span>
			<span class="mL5"><form:radiobutton path="swm" id="swm${personViewNumber}" value="U" title="Sex with male" /> Unknown </span>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> Sex with female </span>
		</div>
		<div id="patient_sex_with_female" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="swf" id="swf${personViewNumber}" value="Y" title="Sex with female" /> Yes </span>
			<span class="mL5"><form:radiobutton path="swf" id="swf${personViewNumber}" value="N" title="Sex with female" /> No </span>
			<span class="mL5"><form:radiobutton path="swf" id="swf${personViewNumber}" value="U" title="Sex with female" /> Unknown </span>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>

	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> Intravenous/Injection Drug Use </span>
		</div>
		<div id="patient_injected_non_presc_drugs" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="idu" id="idu${personViewNumber}" value="Y" title="Injected non-prescription drugs" /> Yes </span>
			<span class="mL5"><form:radiobutton path="idu" id="idu${personViewNumber}" value="N" title="Injected non-prescription drugs" /> No </span>
			<span class="mL5"><form:radiobutton path="idu" id="idu${personViewNumber}" value="U" title="Injected non-prescription drugsss" /> Unknown </span>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>	

	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5"> 
		<div style="float:left; width:35%;  padding-bottom:5px; "> 
			<span class="normalFont"> Received clotting factor for hemophilia/</span></br>
			<span class="normalFont">coagulation disorder </span>
		</div>
		<div style="float:left; width:45%; border-right:1px dotted;  padding-bottom:5px; ">		
			<span class="normalFont"> Specify clotting fator: </span><form:input path="recvClottingFactor" id="recvClottingFactor${personViewNumber}" value="${recvClottingFactor}" class="standardInputMedText" maxlength="1" title="Specify clotting fator"/></br>
			<span class="normalFont"> Date received (mm/dd/yyyy) 
				<form:input path="dateRecvClottingFactor" id="dateRecvClottingFactor${personViewNumber}" value="${dateRecvClottingFactor}" class="standardInputMedText dPicker mask datepicker maskDate" maxlength="10" title="Date received" />
			</span>
			
		</div>		
		<div id="patient_received_clotting_factor" style="float:right; width:19%; padding-top:13px;">
			<span class="mL5"><form:radiobutton path="cloattingFactorDisorder" id="cloattingFactorDisorder${personViewNumber}" value="Y" title="Coagulation disorder" /> Yes </span>
			<span class="mL5"><form:radiobutton path="cloattingFactorDisorder" id="cloattingFactorDisorder${personViewNumber}" value="N" title="Coagulation disorder" /> No </span>
			<span class="mL5"><form:radiobutton path="cloattingFactorDisorder" id="cloattingFactorDisorder${personViewNumber}" value="U" title="Coagulation disorder" /> Unknown </span>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>	

	<div style="width:100%; padding-bottom:5px; background-color:#DBEAF9">
			<span class="hdrFont mL5"> HETEROSEXUAL relations with any of the following:</span>
	</div>	

	<div style="width:99.9%; border:1px dotted;"></div>

	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> HETEROSEXUAL contact with intravenous/injection drug user </span>
		</div>
		<div id="patient_heterosexual_contact_injection_drug_user" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="sexPartnerIdu" id="sexPartnerIdu${personViewNumber}" value="Y" title="Intravenous/Injection drug user" /> Yes </span>
			<span class="mL5"><form:radiobutton path="sexPartnerIdu" id="sexPartnerIdu${personViewNumber}" value="N" title="Intravenous/Injection drug user" /> No </span>
			<span class="mL5"><form:radiobutton path="sexPartnerIdu" id="sexPartnerIdu${personViewNumber}" value="U" title="Intravenous/Injection drug user" /> Unknown </span>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> HETEROSEXUAL contact with bisexual male </span>
		</div>
		<div id="patient_heterosexual_contact_with_bisexual_male" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="sexPartnerMsm" id="sexPartnerMsm${personViewNumber}" value="Y" title="Contact with bisexual male" /> Yes </span>
			<span class="mL5"><form:radiobutton path="sexPartnerMsm" id="sexPartnerMsm${personViewNumber}" value="N" title="Contact with bisexual male" /> No </span>
			<span class="mL5"><form:radiobutton path="sexPartnerMsm" id="sexPartnerMsm${personViewNumber}" value="U" title="Contact with bisexual male" /> Unknown </span>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>

	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> HETEROSEXUAL contact with person with hemophilia / coagulation disorder with documented with HIV infection </span>
		</div>
		<div id="patient_heterosexual_contact_person_with_hemophilia" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="sexPartnerHemophiliac" id="sexPartnerHemophiliac${personViewNumber}" value="Y" title="Person with hemophilia" /> Yes </span>
			<span class="mL5"><form:radiobutton path="sexPartnerHemophiliac" id="sexPartnerHemophiliac${personViewNumber}" value="N" title="Person with hemophilia" /> No </span>
			<span class="mL5"><form:radiobutton path="sexPartnerHemophiliac" id="sexPartnerHemophiliac${personViewNumber}" value="U" title="Person with hemophilia" /> Unknown </span>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>

	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> HETEROSEXUAL contact with transfusion recipient with documented HIV infection </span>
		</div>
		<div id="patient_heterosexual_contact_with_transfusion_recipient" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="sexPartnerTransfusion" id="sexPartnerTransfusion${personViewNumber}" value="Y" title="Transfusion recipient with Documented HIV infection" /> Yes </span>
			<span class="mL5"><form:radiobutton path="sexPartnerTransfusion" id="sexPartnerTransfusion${personViewNumber}" value="N" title="Transfusion recipient with Documented HIV infection" /> No </span>
			<span class="mL5"><form:radiobutton path="sexPartnerTransfusion" id="sexPartnerTransfusion${personViewNumber}" value="U" title="Transfusion recipient with Documented HIV infection" /> Unknown </span>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>

	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> HETEROSEXUAL contact with transplant recipient with documented HIV infection </span>
		</div>
		<div id="patient_heterosexual_contact_with_transplant_recipient" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="sexPartnerTransplant" id="sexPartnerTransplant${personViewNumber}" value="Y" title="Transplant recipient with HIV infection" /> Yes </span>
			<span class="mL5"><form:radiobutton path="sexPartnerTransplant" id="sexPartnerTransplant${personViewNumber}" value="N" title="Transplant recipient with HIV infection" /> No </span>
			<span class="mL5"><form:radiobutton path="sexPartnerTransplant" id="sexPartnerTransplant${personViewNumber}" value="U" title="Transplant recipient with HIV infection" /> Unknown </span>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>

	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> HETEROSEXUAL contact with person with AIDS or documented HIV infection, risk not specified </span>
		</div>
		<div id="patient_heterosexual_contact_with_person_with_AIDS" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="sexPartnerHiv" id="sexPartnerHiv${personViewNumber}" value="Y" title="Risk not specified" /> Yes </span>
			<span class="mL5"><form:radiobutton path="sexPartnerHiv" id="sexPartnerHiv${personViewNumber}" value="N" title="Risk not specified" /> No </span>
			<span class="mL5"><form:radiobutton path="sexPartnerHiv" id="sexPartnerHiv${personViewNumber}" value="U" title="Risk not specified" /> Unknown </span>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> Received tranfustion of blood/blood components (other than clotting factor) &nbsp; (document reason in Comments section) </span></br>
			<span class="normalFont" id="firstLastDatesReceived"> First date received 
				<form:input path="dateRecvBloodProd" id="dateRecvBloodProd${personViewNumber}" value="${dateRecvBloodProd}" class="standardInputMedText dPicker mask datepicker maskDate" maxlength="10" title="First Date received" />
			</span>		
		</div>
		<div id="patient_received_tranfustion_of_blood" style="float:right; width:19%; padding-top:15px;">
			<span class="mL5"><form:radiobutton path="recvBloodProd" id="recvBloodProd${personViewNumber}" class="PTYes checked" value="Y" title="Received tranfustion of blood" /> Yes </span>
			<span class="mL5"><form:radiobutton path="recvBloodProd" id="recvBloodProd${personViewNumber}" class="PTNo" value="N" title="Received tranfustion of blood" /> No </span>
			<span class="mL5"><form:radiobutton path="recvBloodProd" id="recvBloodProd${personViewNumber}" class="PTUnknown" value="U" title="Received tranfustion of blood" /> Unknown </span>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>	

	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5 mT2"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> Received transplant of tissue/organs or artificial insemination </span>
		</div>
		<div id="patient_received_transplant_of_tissue" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="recvTransplant" id="recvTransplant${personViewNumber}" class="PTYes checked" value="Y" title="Received transplant of tissue/organs" /> Yes </span>
			<span class="mL5"><form:radiobutton path="recvTransplant" id="recvTransplant${personViewNumber}" class="PTNo" value="N" title="Received transplant of tissue/organs" /> No </span>
			<span class="mL5"><form:radiobutton path="recvTransplant" id="recvTransplant${personViewNumber}" class="PTUnknown" value="U" title="Received transplant of tissue/organs" /> Unknown </span>
		</div>
	</div>	

	<div style="width:99.9%; border:1px dotted; " class="container"></div>	
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px;"> 
			<span class="normalFont "> Worked in a healthcare or clinical laboratory setting : </span>
			<span class="normalFont mL20"> If occupational exposure is being investigated or considered as primary mode of exposure , specify occupation and setting: <form:input path="occupationalSetting" id="occupationalSetting${personViewNumber}" value="${occupationalSetting}" class="standardInputSmallText" maxlength="100" title="Occupation and Setting"/>
			</span>		
		</div>
		<div id="patient_Worked_in_a_healthcar" style="float:right; width:19%; padding-top:13px;">
			<span class="mL5"><form:radiobutton path="healthCareWorker" id="healthCareWorker${personViewNumber}" class="PTYes checked" value="Y" title="Worked in a healthcare" /> Yes </span>
			<span class="mL5"><form:radiobutton path="healthCareWorker" id="healthCareWorker${personViewNumber}" class="PTNo" value="N" title="Worked in a healthcare" /> No </span>
			<span class="mL5"><form:radiobutton path="healthCareWorker" id="healthCareWorker${personViewNumber}" class="PTUnknown" value="U" title="Worked in a healthcare" /> Unknown </span>
		</div>
	</div>	
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>	
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px;"> 
			<span class="normalFont ">Perinatal Exposure
			</span>		
		</div>
		<div id="patient_Worked_in_a_healthcar" style="float:right; width:19%; padding-top:13px;">
			<span class="mL5"><form:radiobutton path="perinatal" id="perinatal${personViewNumber}" class="PTYes checked" value="Y" title="Worked in a healthcare" /> Yes </span>
			<span class="mL5"><form:radiobutton path="perinatal" id="perinatal${personViewNumber}" class="PTNo" value="N" title="Worked in a healthcare" /> No </span>
			<span class="mL5"><form:radiobutton path="perinatal" id="perinatal${personViewNumber}" class="PTUnknown" value="U" title="Worked in a healthcare" /> Unknown </span>
		</div>
	</div>	
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>	
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> Other documented risk (please include detail in Comments section) </span>
		</div>
		<div id="patient_other_documented_risk" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:radiobutton path="other" id="other${personViewNumber}" class="PTYes checked" value="Y" title="Other documented risk" /> Yes </span>
			<span class="mL5"><form:radiobutton path="other" id="other${personViewNumber}" class="PTNo" value="N" title="Other documented risk" /> No </span>
			<span class="mL5"><form:radiobutton path="other" id="other${personViewNumber}" class="PTUnknown" value="U" title="Other documented risk" /> Unknown </span>
		</div>
	</div>	
	
	<div style="width:99.9%; border:1px dotted; " class="container"></div>	
	
	<div style="float:left; width:99%; padding-bottom:5px;" class="mL5 mR5"> 
		<div style="float:left; width:80%; border-right:1px dotted;  padding-bottom:5px; "> 
			<span class="normalFont"> If Yes, Please indicate Other Risks: </span>
		</div>
		<div id="patient_other_documented_risk" style="float:right; width:19%; padding-bottom:5px;">
			<span class="mL5"><form:input path="riskOtherTxt" id="riskOtherTxt${personViewNumber}" value="${riskOtherTxt}" class="standardInputText formInputText" readonly="true"/> </span>
		</div>
	</div>		
		
	<div style="clear:both"></div>	
</div>
		

</form:form>