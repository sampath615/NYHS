	
	<div id="labDocTab" style="background-color: transparent">
		<div class="container paddingBottom5" >
			<div style="float:left">
				<span class="mL20"> <spring:message code="label.id" />: <input type="text" name="" class="standardInputMedText" />
			</div>
			<div style="float:right">
				<span> <spring:message code="label.stateNo" />: <input type="text" name="" class="standardInputMedText" />
			</div>				
		</div>
		
		<!--  Person details -->
		
		<div class="container" style="border:1px dotted; width:100%">	 
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Person Details</span></div>
			<div style="margin-top:2px; margin-left:5px;  margin-right:5px;">
					<span class="hdr1Font"> Name</span>
					<span class="mL25 hdrFont"> <spring:message code="label.firstname" /></span> 
					<span class="mL165 hdrFont"> <spring:message code="label.middleName" /></span>
					<span class="mL142 hdrFont"> <spring:message code="label.lastname" /></span>
					<span class="mL160 hdrFont"> <spring:message code="label.suffix" /></span>
					</br>
					<span class="mL70"> <input type="text" name="ptntFirstName" class="standardInputMedText" /> </span>
					<span class="mL50"> <input type="text" name="ptntMiddleName" class="standardInputMedText" /> </span>
					<span class="mL50"> <input type="text" name="ptntLastName" class="standardInputMedText" /> </span>
					<span class="mL45"> <input type="text" name="ptntLastName" class="standardInputMedText" /> </span>
			</div>
			<div  class="mL5 mR5 mT5" >
				<div style="float:left; "> 
					<span class="hdrFont "> <spring:message code="label.streetAddress" /> </span>
					<span> <input type="text" name="ptntCurrentStreetAddress" class="standardInputText" /> </span>
				</div>
				<div style="float:left;"> 
					<span class="hdrFont mL15"> <spring:message code="label.birthdate" /> </span>
					<span class="mL10"> <input type="text" name="ptntCurrentStreetAddress" class="standardInputMedText" /> </span>
				</div>
				<div style="float:left; "> 
					<span class="hdrFont mL50"> <spring:message code="label.age" /> </span>
					<span class="mL10"> <input type="text" name="ptntCurrentStreetAddress" class="standardInputSmallText" /> </span>
				</div>	
			</div>			
			<div style="float:left; width:99%; margin-left:5px; margin-right:5px;" class="mT5"> 
			
				<div style="float:left; padding-bottom:5px;"> 
					<span class="mL5 hdrFont"> <spring:message code="label.city" /></span>
					<span class="mL5"> <input type="text" name="ptntCity" class="standardInputSmallText" /> </span>
				</div>
				<div style="float:left; padding-bottom:7px;"> 
					<span class="mL20 hdrFont"> <spring:message code="label.county" /></span> </span> 
					<span class="mL5"> <input type="text" name="ptntCountry" class="standardInputSmallText" /> </span>
				</div>
				
				<div style="float:left; padding-bottom:7px;"> 
					<span class="mL20 hdrFont"> State/Country</span>
					<span class="mL5"> <input type="text" name="ptntStateOrCounty" class="standardInputMedText" /> </span>
				</div>
				
				<div style="float:left;  padding-bottom:7px;"> 
					<span class="mL20 hdrFont"> <spring:message code="label.zip" /></span> 
					<span class="mL5"> <input type="text" name="ptntZipCode" class="standardInputSmallText" /> </span>
				</div>					
			</div>				
			<div style="margin-left:5px; margin-right:5px;" class="mT5">
				<div style="float:left; padding-bottom:5px;"> 
					<span class="mL5 hdrFont "> <spring:message code="label.sex" /> </span>
					<span class="mL5"> <input type="radio" name="" value="Male" /> <spring:message code="label.male" /> </span>
					<span class="mL5"> <input type="radio" name="" value="Female" /> <spring:message code="label.female" /> </span>		
				</div>
				<div style="float:left; padding-bottom:5px;"> 
					<span class="hdrFont mL20"> Transgender </span>
					<span class="mL5"> <input type="radio" name="" value="MTF" /> MTF </span>
					<span class="mL5"> <input type="radio" name="" value="FTM" /> FTM </span>		
				</div>	
				<div style="float:left; "> 
					<span class="hdrFont mL45" style="text-align:center; "> <spring:message code="label.telephone" /> <input type="text" name="ptntZipCode" class="standardInputMedText" />  </span>
				</div>		
				<div style="float:left; padding-bottom:5px;"> 
					<span class="hdrFont mL55"> <spring:message code="label.ssn" /> <input type="text" name="ptntZipCode" class="standardInputSmallText" /></span>
				</div>				
			</div>						  
		</div>
		
		<!--  Race details -->
		
		<div class="container" style="border:1px dotted; padding-bottom:5px;">
			<div style="float:left; width:25%; margin-left:20px; border-right:1px dotted;">				
				<div>
					<spring:message code="label.race" />  (Select all that apply)
				</div>
				<div>
					<input type="checkbox" name="" /> <spring:message code="label.raceAsian" />
				</div>
				<div>
					<input type="checkbox" name="" /> <spring:message code="label.raceBlack" />
				</div>
				<div>
					<input type="checkbox" name="" /> <spring:message code="label.raceIndian" />
				</div>
				<div>
					<input type="checkbox" name="" /> <spring:message code="label.racePacific" />
				</div>
				<div>
					<input type="checkbox" name="" /> <spring:message code="label.raceWhite" />	
				</div>
				<div>	
					<input type="checkbox" name="" /> <spring:message code="label.raceMultiple" />
				</div>
				<div>
					<input type="checkbox" name="" /> <spring:message code="label.raceUnknown" />
				</div>
				<div>
					<input type="checkbox" name="" /> <spring:message code="label.raceOther" />  <input type="text" name="" class="standardInputMedText" />
				</div>
			</div>
			<div style="float:left; padding-left:10px; width:35% ; ">
				<spring:message code="label.ethnicity" /> </br>
				Hispanic or Latino <input type="radio" name="" /> <spring:message code="label.yes" />
								   <input type="radio" name="" /> <spring:message code="label.no" />
								   <input type="radio" name="" /> <spring:message code="label.unknown" />	
			</div>
			
			<!--  Medical details -->
			
			<div style="float:left; width:35% ; padding-left:10px; padding-bottom: 57px; border-left:1px dotted;">
				<div class="mT2">
					Medical Record <input type="text" name="" class="standardInputMedText mL5" />
				</div>
				<div class="mT2">
					Insurance No <input type="text" name="" class="standardInputMedText mL22" />
				</div>
				<div class="mT2">
					Insurance code	<input type="text" name="" class="standardInputMedText mL10" />
				</div>
				<div class="mT2">
					<spring:message code="label.din" />	<input type="text" name="" class="standardInputMedText mL78" />
				</div>
				<div class="mT2">
					<spring:message code="label.nysid" /><input type="text" name="" class="standardInputMedText mL57" />
				</div>
			</div>
		</div>
		<div id="labDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
			<span class=" mL2">Lab Details</span>
		</div>
		
		<!--  Lab details -->
		
		<div style="padding-top:5px; border:1px dotted;" class="container">	 
			<div style="float:left; width:50%; padding-left:10px; ">
				<div class="mT2">
					Draw Date <input type="text" name="" class="standardInputMedText mL36" />
				</div>
				<div class="mT2">
					Collection Date <input type="text" name="" class="standardInputMedText mL5" />
				</div>
				<div class="mT2">
					<spring:message code="label.receiveDate" /> <input type="text" name="" class="standardInputMedText mL20" />
				</div>
				<div class="mT2">
					DIAGSTAT <input type="text" name="" class="standardInputMedText mL25" />
				</div>
				<div class="mT2">
					<span>Date Fld HARS <input type="text" name="" class="standardInputMedText" /></span>
					<span class="padding-left:5px;">Date value HARS <input type="text" name="" class="standardInputSmallText mL5" /></span>
				</div>
				<div class="mT2">
					<span>Date value HARS <input type="text" name="" class="standardInputSmallText" /></span>
					<span class="mL20">DIAG value HARS <input type="text" name="" class="standardInputSmallText" /></span>
				</div>
				
			</div>
		
			<div style="float:right; width:48%; padding-bottom:5px; padding-left:10px; border-left:1px dotted;">
				<div class="mT2">
					LOINC ID <input type="text" name="" class="standardInputMedText mL30" />
					Test Code <input type="text" name="" class="standardInputSmallText" />
				</div>
				<div class="mT2">
					DESCRIPTION <input type="text" name="" class="standardInputMedText" />
				</div>	
				<div class="mT2" style="padding-bottom:5px;">
					Test Result <input type="text" name="" class="standardInputMedText mL30" />
					Unit Measure Code <input type="text" name="" class="standardInputSmallText2" />
				</div>
				
				<div style="width:99.9%; border:1px dotted;"></div>
				
				<div class="paddingTop2">
					<span>Internal Medical Record <input type="text" name="" class="standardInputMedText mL5" /></span>
				</div>
				<div class="paddingTop2" style="padding-bottom:5px;">
					<span>External Medical Record <input type="text" name="" class="standardInputMedText" /></span>
				</div>
				
				<div style="width:99.9%; border:1px dotted;"></div>
				
				<div class="mT2">
					Sending Facility <input type="text" name="" class="standardInputMedText mL10" />
				</div>
				<div class="mT2">
					Referring Facility <input type="text" name="" class="standardInputMedText " />
				</div>
				<div class="mT2" style="padding-bottom:5px;">
					Testing Facility <input type="text" name="" class="standardInputMedText mL15" />
				</div>
				<div style="width:99.9%; border:1px dotted;"></div>
				
				<div class="mT2">
					<span>Specimen Source Code <input type="text" name="" class="standardInputMedText mL5" /> <span>
				</div>	
				<div class="mT2">
					<span>Specimen Source Name <input type="text" name="" class="standardInputMedText" /></span>
				</div>		
			</div>						 
		</div>
		
		<div style=" width:100%; border:1px dotted; padding-bottom:5px;" class="container">
			<!--  Provider details -->
			
			<div style="float:left; border-right:1px dotted; width:50%">				
				<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
					<span class=" mL2">Provider Details</span>
				</div>
				<div style="padding-left:10px; padding-top:2px;">
					Provider Name <input type="text" name="" class="standardInputMedText" />
				</div>	
				<div style="padding-left:10px; padding-top:2px;">
					<spring:message code="label.streetAddress" /> <input type="text" name="" class="standardInputText" />
				</div>
				<div style="padding-left:10px; padding-top:2px;">
					<spring:message code="label.city" /> <input type="text" name="" class="standardInputMedText mL30" />
					<spring:message code="label.state" /> <input type="text" name="" class="standardInputMedText mL30" /> 
				</div>
				<div style="padding-left:10px; padding-top:2px;">
					<spring:message code="label.zip" />  <input type="text" name="" class="standardInputMedText" />
					<spring:message code="label.telephone" /> <input type="text" name="" class="standardInputMedText" />
				</div>					
			</div>
			
			<!--  Facility details -->
			
			<div style="float:right; border-left:1px dotted; width:49.8%">
				<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
					<span class=" mL2">Facility Details</span>
				</div>
				<div style="padding-left:10px; padding-top:2px;">
					Facility Name <input type="text" name="" class="standardInputMedText" />
				</div>	
				<div style="padding-left:10px; padding-top:2px;">
					<spring:message code="label.streetAddress" /> <input type="text" name="" class="standardInputText" />
				</div>
				<div style="padding-left:10px; padding-top:2px;">
					<spring:message code="label.city" /> <input type="text" name="" class="standardInputMedText mL30" />
					<spring:message code="label.state" /> <input type="text" name="" class="standardInputMedText mL30" /> 
				</div>
				<div style="padding-left:10px; padding-top:2px;">
					<spring:message code="label.zip" />  <input type="text" name="" class="standardInputMedText" />
					<spring:message code="label.telephone" /> <input type="text" name="" class="standardInputMedText" />
				</div>	
			</div>
		</div>																		 
	</div>											
															



