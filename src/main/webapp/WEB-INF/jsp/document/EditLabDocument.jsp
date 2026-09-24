<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>
 <script src="${staticPath}/js/docs/labDoc.js?v=${buildDate}"></script>
 	
<form:form modelAttribute="stdLabDocVO" id="stdLabDocVO" cssClass="documentDescription">

<form:errors path="*" cssClass="errorblock" element="div" />
	<form:hidden path="documentId"/>
	<div id="labDocTab" style="background-color: transparent">
		<div style="padding-left:43%; " class="headingCls">
				LAB &nbsp;  DOCUMENT
		</div>
		
		<div style="padding-bottom:10px; text-align: right;">
            <span class="mL10">Document ID: </span>
			<span class=" "><form:input path="documentId" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
			<form:hidden path="pfi" id="mshLabPfi"/>
         </div>    
		
		 <div class="container pB5 w100">
            <div style="float:left; padding-bottom:10px; width: 40%">
	            <div style="padding-bottom:10px">
	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
                    <span class="ml10"><form:input path="stateno" value="${stateno}" class="standardInputSmallText" style="width:180px" maxlength="10" readonly="true"/> </span>
	            </div>    
                <div style="">
	                <span class=" ml10">CITYNO: </span>
	                <span class="ml10 "><input value="${nyehmsStatenoDetails.CITYNO}" id="nycCITYNO" class="standardInputSmallText"/></span>
	            </div>
            </div>
            <div style="float:left; padding-bottom:10px; width: 30%">
            	<div style="padding-bottom:10px">
	                <span class="ml10"> eHARS NYS STATENO: </span>
                    <span class="ml10" ><input value="${nyehmsStatenoDetails.eharsStateno}" id="nysEharsStateno" class="standardInputSmallText"/></span>
	            </div>    
                <div style="">
	                <span class=" ml10">eHARS NYC STATENO: </span>
	                <span class="ml10 "><input value="${nyehmsStatenoDetails.EHARS_STATENO}" id="nycEHARS_STATENO" class="standardInputSmallText"/></span>
	            </div>                    
            </div>
            <div style="float:left; padding-bottom:10px; width: 30%; text-align: right">
            	        	
	            <div style="padding-bottom:10px">
	                <span>	eHARS Import Date: </span>
					<form:input path="eharsImportDate" class="standardInputSmallText ml10" readonly="true"/>
	            </div>
	            <div >
	                <span class="mL10">NYC Ship Date: </span>
					<span class=" "><form:input path="nycShipDate" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
	            </div>                   
            </div>     
   		</div>
				
		<!--  Person details -->
		
		<div class="container" style="border:1px dotted; width:100%">	 
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Person Details</span></div>
			<div style="margin-top:2px; margin-left:8px;  margin-right:5px;">
					<span> <spring:message code="label.firstname" /></span><span> <form:input path="fname" value="${fname}" class="standardInputMedText" /> </span> 
					<span style="margin-left:10px"> <spring:message code="label.middleName" /></span><span> <form:input path="mname" value="${mname}" class="standardInputMedText" /> </span>
					<span style="margin-left:10px"> <spring:message code="label.lastname" /></span><span> <form:input path="lname" value="${lname}" class="standardInputMedText" /> </span>
					<span style="margin-left:10px"> <spring:message code="label.suffix" /></span><form:input path="suffix" value="${suffix}" class="standardInputMedText" /> </span>
					</br>
				
			</div>
		
				<div style="float:left; width:99%; margin-left:5px; margin-right:5px;" class="mT5">                 		
				<div style="float:left; padding-top:5px;"> 
					<span style="margin-left:3px"> <spring:message code="label.ssn" /> <form:input path="ssn" value="${ssn}" class=" standardInputSmallText" /></span>
				</div>	
				<div style="float:left; padding-top:5px;"> 
					<span class="mL20"> <spring:message code="label.birthdate" /> </span>
					<span> <form:input path="dobText" value="${dobText}"  class="editable dPicker standardInputSmallText3" /> </span>
				</div>
				<div style="float:left; padding-top:5px;"> 
					<span class="mL20"> <spring:message code="label.age" /> </span>
					<span> <form:input path="age" value="${age}"  class="nOnly standardInputSmallText2" /> </span>
				</div>	
				<div style="float:left; padding-top:5px;"> 
					<span class="mL20">Birth Country</span>
					<span> <form:input path="birthCountry" value="${birthCountry}"  class="standardInputSmallText2" /> </span>
				</div>	
			</div>				
			
		    <div style="float:left; width:99%; margin-left:5px; margin-right:5px; margin-bottom:10px" class="mT5">
			
			<div class="b1" style="margin: 15px 5px 5px 5px">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"><form:input path="personAddress" value="${personAddress}" class="standardInputText newAddress" />  </span>
	 	                    
	 	                    
	 	                    <span class="pL30">City</span>
	 	                    <span class="pL10"> <form:input path="personCity" value="${personCity}" class="oAdrDlg standardInputSmallText1 newCity" disabled="true"/></span>
	 	                    
	 	                    <span class="pL30">State </span>
	 	                    <span  class="pL10">
								<form:select path="personState"  cssClass="oAdrDlg roundedBrdr newState" disabled="true"> 
									<form:option value=""></form:option>
									<form:options items="${sessionScope.stateMap}"/>
								</form:select>
							</span>
	 	                    
	 	                    <span class="pL30">Zip </span>
	 	                    <span class="pL10"> 
	 	                        <form:input path="personZip" value="${personZip}" class="oAdrDlg nOnly standardInputSmallText1 newZip" maxlength="5" disabled="true"/> - 
								<form:input path="personPlus4" value="${personPlus4}" class="nOnly standardInputSmallText1" maxlength="4"/>
	 	                    </span>
	 	                    <span class="pL30">County </span>
	 		                   <span class="pL10"> 
									<form:select path="personCounty"  cssClass="oAdrDlg roundedBrdr newCounty" disabled="true"> 
										<form:option value=""></form:option>
										<form:options items="${sessionScope.countyMap}"/>
									</form:select>
		 		               </span>       	                    
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10">
 	            	<span  class="" style="padding-left:59px"><form:input path="stdPersonAddress" value="${stdPersonAddress}" class="standardInputText" /></span>
 	               	                	                 
 	                 <span class="pL30"> Phone</span>
 					 <span class="pL10"> <form:input path="personPhone" value="${personPhone}" class="pNumber standardInputSmallText phoneInputText" maxlength="14" /></span>
 					 
 					 <span class="pL30">  <spring:message code="label.country" /></span>
 					 <span class="pL10"> <form:input path="personCountry" value="${personCountry}" class="standardInputSmallText newCountry" /></span>
 	                <br>               
 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
 	                    
 	            </div>
 	        </div>					
			
		</div>		  
		</div>
		
			<!--  Race details -->
			
		<div class="container" style="border:1px dotted; padding-bottom:5px;">
			<div style="float:left; width:25%; margin-left:20px; border-right:1px dotted; height: 156px">				
				<div>
					<spring:message code="label.race" />  (Select all that apply)
				</div>
				<div>
					<form:checkbox path="raceWhite" value="1" /> <spring:message code="label.raceWhite" />	
				</div>
				<div>
					<form:checkbox path="raceBlack" value="1" /> African American
				</div>
				<div>
					<form:checkbox path="racePacific" value="1" /> Asian/Pacific Islander
				</div>
				<div>
					<form:checkbox path="raceIndian" value="1" /> American Indian/Alaskan Native
				</div>
				<div>
					<form:checkbox path="raceOther" value="1" /> <spring:message code="label.raceOther" /> 
					<input path="" value="" class="standardInputSmallText2" />
				</div>
				<div>
					<form:checkbox path="raceUnknown" value="1" /> <spring:message code="label.raceUnknown" />
				</div>
			</div>
			<div style="float:left; width:35% ; ">
				<div style="padding:5px 0 5px 10px;">
					<spring:message code="label.ethnicity" /> 
					()Hispanic or Latino) </br>
										<form:radiobutton path="ethnicity" value="01" /> <spring:message code="label.yes" /> <br>
									   <form:radiobutton path="ethnicity" value="02" /> <spring:message code="label.no" /><br>
									   <form:radiobutton path="ethnicity" value="09" /> <spring:message code="label.unknown" />
				</div>
				<div style="border-top: dotted 1px">
					<div style="padding:10px 0 0 10px;">												
 						<div class="floatLeft pR10" style="border-right: dotted 1px;">
							<div class=""> Current Gender </div>
							<div  class="">
								<div> <form:radiobutton path="GENDER" value="M" class="editable"/> <spring:message code="label.male" /> </div>
								<div> <form:radiobutton path="GENDER" value="F" class="editable"/> <spring:message code="label.female" /> </div>
								<div> <form:radiobutton path="GENDER" value="U" class="editable"/>Unknown</div>		
							</div>
						</div>
						<div class="floatLeft pL10">
							<div class=""> Transgender </div>
							<div  class="">
								<div> <form:radiobutton path="GENDER" value="X" class="editable"/> MTF </div>		
								<div> <form:radiobutton path="GENDER" value="Y" class="editable"/> FTM </div>		
								<div> <form:radiobutton path="GENDER" value="T" class="editable"/> Unspecified </div>														
							</div>
						</div>
 					</div>				
				</div>
			</div>
			
			<!--  Medical details -->
			
			<div style="float:left; width:35% ; padding-left:10px; padding-bottom: 12px; border-left:1px dotted;">

								
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Medical Record Number
					</div>
					<div style="float:left">
						<form:input path="medRecordNumber" value="${medRecordNumber}" class="standardInputMedText mL5" />
					</div>
				</div>			
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Insurance Number
					</div>
					<div style="float:left">
						<form:input path="insuranceNumber" value="${insuranceNumber}" class="standardInputMedText mL5" />
					</div>
				</div>	
				<div class="mT5 container">
					<div style="float:left; width:45%">
						CIN
					</div>
					<div style="float:left">
						<form:input path="medicaidNumber" value="${medicaidNumber}" class="standardInputMedText mL5" />
					</div>
				</div>
				<div class="mT5 container">
					<div style="float:left; width:45%">
						<spring:message code="label.din" />
					</div>
					<div style="float:left">
						<form:input path="din" value="${din}" class="standardInputMedText mL5" />
					</div>
				</div>

				<div class="mT5 container">
					<div style="float:left; width:45%">
						<spring:message code="label.nysid" />
					</div>
					<div style="float:left">
						<form:input path="nysid" value="${nysid}" class="standardInputMedText mL5" />
					</div>
				</div>				
			</div>
		</div>
		<div id="labDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
			<span class=" mL2">Lab Details</span>
		</div>
		
		<!--  Lab details -->
		
		<div style="padding-top:5px; padding-bottom:5px; border:1px dotted;" class="container">	 
			<div style="float:left; width:35%; padding-left:10px; ">
					
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Observation Result Key
					</div>
					<div style="float:left">
						<form:input path="labSrcId" value="${labSrcId}" class="standardInputMedText mL5" />
					</div>
				</div>	
				
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Collection Date
					</div>
					<div style="float:left">
						<form:input path="collectionDate" value="${collectionDate}" class="editable dPicker standardInputMedText mL5" />
					</div>
				</div>

				<div class="mT5 container">
					<div style="float:left; width:45%">
						Observation Date
					</div>
					<div style="float:left">
						<form:input path="collectionDate" value="${collectionDate}"  class="editable dPicker standardInputMedText mL5" />
					</div>
				</div>

				<div class="mT5 container">
					<div style="float:left; width:45%">
						Result Reported Date
					</div>
					<div style="float:left">
						<form:input path="resultReportDate" value="${resultReportDate}"  class="editable dPicker standardInputMedText mL5" />
					</div>
				</div>

				<div class="mT5 container">
					<div style="float:left; width:45%">
						Accession Number
					</div>
					<div style="float:left">
						<form:input path="accessionNumber" value="${accessionNumber}" class="standardInputMedText mL5" />
					</div>
				</div>	

				<div class="mT5 container">
					<div style="float:left; width:45%">
						HIV Vaccine Recipient
					</div>
					<div style="float:left">
						<form:input path="hivVaccineRecip" value="${hivVaccineRecip}" class="standardInputMedText mL5" />
					</div>
				</div>	
			</div>
		
			<div style="float:right; width:63%; padding-bottom:5px; padding-left:10px; border-left:1px dotted;">
				<div style="float:left; width:49%;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							LOINC
						</div>
						<div style="float:left">
							<form:input path="loinc" value="${loinc}" class="editable standardInputMedText mL5" />
						</div>
					</div>

					<div class="mT5 container">
						<div style="float:left; width:40%">
							Test Result
						</div>
						<div style="float:left">
							<form:input path="testResult" value="${testResult}" class="standardInputMedText mL5" />
						</div>
					</div>
					
				</div>
				<div style="float:right; width:49%; margin-bottom: 5px;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							DESCRIPTION
						</div>
						<div style="float:left">
							<form:input path="loincDesc" value="${loincDesc}" class="standardInputMedText mL5" />
						</div>
					</div>
					
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Unit Measure Code
						</div>
						<div style="float:left">
							<form:input path="unitMeasureCode" value="${unitMeasureCode}" class="editable standardInputSmallText2 mL5" />
						</div>
					</div>					
				</div>

				<div style="width:99.9%; border:1px dotted;" class="container" ></div>

				<div style="float:left; width:49%;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Internal ID
						</div>
						<div style="float:left">
							<form:input path="personInternalId" value="${personInternalId}"  class="standardInputMedText mL5" />
						</div>
					</div>					
				</div>
				<div style="float:right; width:49%; margin-bottom: 5px;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							External ID
						</div>
						<div style="float:left">
							<form:input path="personExternalId" value="${personExternalId}" class="standardInputMedText" mL5/>
						</div>
					</div>				
				</div>		
				
				<div style="width:99.9%; border:1px dotted;" class="container"></div>

				<div style="float:left; width:49%;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Sending Facility
						</div>
						<div style="float:left">
							<form:input path="sendingFacilityName" value="${sendingFacilityName}"  class="standardInputMedText mL5" />
						</div>
					</div>	
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Sending Facility CLIA
						</div>
						<div style="float:left">
							<form:input path="sendingFacilityClia" value="${sendingFacilityClia}"  class="standardInputMedText mL5" />
						</div>
					</div>					
				</div>
				<div style="float:right; width:49%; margin-bottom: 5px;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Referring Facility
						</div>
						<div style="float:left">
							<form:input path="referringFacility" value="${referringFacility}" class="standardInputMedText mL5 " />
						</div>
					</div>
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Testing Facility
						</div>
						<div style="float:left">
							<form:input path="testingFacility" value="${testingFacility}" class="standardInputMedText mL5" />
						</div>
					</div>					
				</div>				

				<div style="width:99.9%; border:1px dotted;" class="container"></div>

				<div style="float:left; width:49%;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Specimen Source Code
						</div>
						<div style="float:left">
							<form:input path="specimenSourceCd" value="${specimenSourceCd}" class="standardInputMedText mL5" />
						</div>
					</div>					
				</div>
				<div style="float:right; width:49%; margin-bottom: 5px;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Specimen Source Name
						</div>
						<div style="float:left">
							<form:input path="specimenSourceName" value="${specimenSourceName}" class="standardInputMedText mL5 " />
						</div>
					</div>				
				</div>			
										
			</div>						 
		</div>
		
		<!--  Provider details -->
			
		<div class="container" style="border:1px dotted; width:100%; padding-bottom:5px;">	 
			<div id="prvDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Provider Details</span></div>
			<div style="margin-top:2px; margin-left:5px;  margin-right:5px;">
					<span class="mL25"> <spring:message code="label.firstname" /></span><span> <form:input path="provFname" value="${provFname}" class="standardInputMedText" /> </span> 
					<span style="margin-left:10px"> <spring:message code="label.middleName" /></span><span> <form:input path="provMname" value="${provMname}" class="standardInputMedText" /> </span>
					<span style="margin-left:10px"> <spring:message code="label.lastname" /></span><span> <form:input path="provLname" value="${provLname}" class="standardInputMedText" /> </span>
					<span style="margin-left:10px"> <spring:message code="label.suffix" /></span><form:input path="provSuffix" value="${provSuffix}" class="standardInputMedText" /> </span>
					</br>
				
			</div>
			
			<div class="b1" style="margin: 15px 5px 5px 5px">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"><form:input path="provAddress" value="${provAddress}" class="oAdrDlg standardInputText newAddress" /></span>	 	                    
	 	                    
	 	                    <span class="pL30">City</span>
	 	                    <span class="pL10"> <form:input path="provCity" value="${provCity}" class="oAdrDlg standardInputSmallText1  newCity" /> </span>
	 	                    
	 	                    <span class="pL30">State </span>
	 	                    <span  class="pL10">
								<form:input path="provState" value="${provState}" class="oAdrDlg standardInputMedText newCity wS40" />
							</span>
	 	                    
	 	                    <span class="pL30">Zip </span>
	 	                    <span class="pL10"> 
	 	                        <form:input path="provZip" value="${provZip}" class="oAdrDlg nOnly  standardInputSmallText1 newZip" maxlength="5"/> - 
								<form:input path="provPlus4" value="${provPlus4}" class="oAdrDlg nOnly standardInputSmallText1" maxlength="4"/>
	 	                    </span>
	 	                    <span class="pL30">County </span>
	 		                   <span class="pL10"> 
									<form:select id="provCounty"  tabindex="8" path="provCounty" class="oAdrDlg standardInputMedText newCounty wC100" title="${county_title}">
								        <form:option value="" label="" />
										<form:options items="${sessionScope.countyMap}"/>
							  		 </form:select>
		 		               </span>       	                    
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10">
 	            	<span  class="" style="padding-left:59px"> <form:input path="stdProvAddress" value="${stdProvAddress}" class="standardInputText" /></span>
 	               	                 
 	                 <span class="pL30"> Phone</span>
 					 <span class="pL10"> <form:input path="provPhone" value="${provPhone}" class="pNumber standardInputSmallText3 phoneInputText" maxlength="14" /></span>
 	                <br>               
 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
 	                    
 	            </div>
 	        </div>						
		</div>
			
		<!--  Facility details -->
			
		<div style="padding-bottom:5px; border:1px dotted;" class="container">
			<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class="mL2">Facility Details</span>
			</div>
			<div style="padding-left:10px; padding-top:2px;">
				Facility Name <form:input path="facilityName" value="${facilityName}" class="editable standardInputText" />					
			</div>	
			
			<div class="b1" style="margin: 15px 5px 5px 5px">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"><form:input path="facilityAddress" value="${facilityAddress}" class="editable oAdrDlg standardInputText newAddress" /></span>	 	                    
	 	                    
	 	                    <span class="pL30">City</span>
	 	                    <span class="pL10"> <form:input path="facilityCity" value="${facilityCity}" class="editable oAdrDlg standardInputSmallText1 newCity" /> </span>
	 	                    
	 	                    <span class="pL30">State </span>
	 	                    <span  class="pL10">
								 <form:input path="facilityState" value="${facilityState}" class="editable oAdrDlg standardInputMedText newState wS40" />
							</span>
	 	                    
	 	                    <span class="pL30">Zip </span>
	 	                    <span class="pL10"> 
	 	                        <form:input path="facilityZip" value="${facilityZip}" class="editable oAdrDlg nOnly  standardInputSmallText1 newZip" maxlength="5"/> - 
								<form:input path="facilityPlus4" value="${facilityPlus4}" class="editable oAdrDlg nOnly standardInputSmallText1" maxlength="4"/>
	 	                    </span>
	 	                    <span class="pL30">County </span>
	 		                   <span class="pL10"> 
									<form:select id="facilityCounty"  tabindex="8" path="facilityCounty" class="oAdrDlg standardInputMedText newCounty wC100" title="${county_title}">
								        <form:option value="" label="" />
										<form:options items="${sessionScope.countyMap}"/>
								    </form:select>
		 		               </span>       	                    
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10">
 	            	<span  class="" style="padding-left:59px"> <form:input path="stdFacilityAddress" value="${stdFacilityAddress}" class="standardInputText" /></span>
 	               	                 
 	                 <span class="pL30"> Phone</span>
 					 <span class="pL10"> <form:input path="facilityPhone" value="${facilityPhone}" class="pNumber standardInputSmallText3 phoneInputText" maxlength="14"/></span>
 	                <br>               
 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
 	                    
 	            </div>
 	        </div>						
		</div>
		
	</div>																	 										
	<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
	 <div id="button-row" class="button-row documentButtons">
		
			<div align="center">
				<c:if  test="${userPermissions['91'] != null}" >
					<input type="button" id="eButton" style="display:none" class="labEdit button rounded blue effect-3 "  value="Edit" />
				</c:if>
				<c:if  test="${userPermissions['90'] != null}" >
					<input type="button" id="dButton" style="display:none" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
				</c:if>
				<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Submit" />	 
				<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />				
			</div>
		
	</div>
	
	<div id="editLabConfirm">
		<h1 class="editLabContentMessage"></h1>
	
	</div>
	
	<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />	
</form:form>																



