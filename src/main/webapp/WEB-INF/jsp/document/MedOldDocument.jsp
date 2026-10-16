<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${staticPath}/js/docs/medDoc.js?v=${buildDate}"></script>

<link href="${staticPath}/css/docs/prfForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/docs/nycijrDoc.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/docs/adapForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/docs/medicaid.css?v=${buildDate}" rel="stylesheet" type="text/css" />

<form:form modelAttribute="stdMedicaidDocVO" id="stdMedicaidDocVO" cssClass="documentDescription">
<form:errors path="*" cssClass="errorblock" element="div" />

<div id="MedOldDoc">
    <div class="headingCls centerDoc">
    Insurance Document
    </div>
    <div style="padding-bottom:10px; text-align: right">
           <span class="mL10">Document ID: </span>
		<span class=" "><form:input id="DOCUMENT_ID"  path="stdMedOldDoc.documentId" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
       </div>
	<div class="container w100 pB10">
        <div style="float:left; width: 40%">
           <div>
               <span class=" ml10"><spring:message code="label.stateNo" />: </span>
                <span class="ml10"><form:input id="STATENO"  path="stateno" class="standardInputSmallText" maxlength="10" readonly="true"/> </span>
           </div>    
         </div>
         <div style="float:left; width: 30%">
           <div>
               <span class=" ml10">	eHARS Import Date: </span>
               <fmt:formatDate value="${stdMedicaidDocVO.stdMedOldDoc.eharsImportDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
	 		<span class="ml10"><form:input id="EHARS_IMPORT_DATE"  path="stdMedOldDoc.eharsImportDate" value="${theFormattedDate}" class="standardInputSmallText ml10" readonly="true"/> </span>
          </div>                  
        </div>
        <div style="float:left; width: 30%; text-align: right">
        	<div>
               <span class="mL10">NYC SHIP Date: </span>
               <fmt:formatDate value="${stdMedicaidDocVO.stdMedOldDoc.nycShipDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedNYC_SHIP_DATE" />
	 		<span class="ml10 "><form:input id="NYC_SHIP_DATE"  path="stdMedOldDoc.nycShipDate" value="${theFormattedNYC_SHIP_DATE}" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
           </div> 
        </div>     
	</div>
	
	<jsp:include page="statenoDetails.jsp"></jsp:include>
	
	
    <div class="container" id="patientInformation" style="margin-bottom: 10px; border: dotted 1px">  
        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
            <span class=" mL2">Patient Information</span>
        </div>
        <div class="mLRTB">
			<span class="">
				<span class=""> First Name</span> 
				<span class="pL10"><form:input id="FNAME"  path="stdMedOldDoc.fname" class="standardInputMedText" maxlength="13" /></span>
			</span>
			<span class=" ">
				<span class="pL30"> Last Name</span>
				<span class="pL10"><form:input  path="stdMedOldDoc.lname" id="lName" title="Last Name" class="standardInputMedText" maxlength="15" /></span>
			</span>					
			<span class=""> 
				<span class="pL30"> Middle Name</span>
				<span class="pL10"> <form:input  path="stdMedOldDoc.mname" id="mname" class=" standardInputMedText" maxlength="10" /> </span>
			</span>
			<span class=""> 
				<span class="pL30">Suffix </span>	
				<span class="pL10"> <form:input id="SUFFIX"  path="stdMedOldDoc.suffix" class="standardInputSmallText" maxLength="9" /></span>
			</span>	 
		</div>
        
        <div class="pB10">           
            <span class="pL10">DOB </span>
            <span> 
	 	        <span class="pL10"><form:input id="DOB_TEXT"  path="stdMedOldDoc.dobText" class="standardInputSmallText" maxLength="9" /> </span>  	 
            </span>
            
            <span class="pL30">Age</span>
            <span class="pL10"><form:input id="AGE"  path="stdMedOldDoc.age" class="standardInputSmallText" maxLength="9" /> </span>
            
            <span class="pL30">SSN</span>
            <span class="pL10"><form:input id="SSN"  path="stdMedOldDoc.ssn" class="standardInputSmallText" maxLength="9" /> </span>
        </div> 
        
        <div class="b1" style="margin: 15px 5px 10px 5px">
            <div class="personInfo pTRBL10">
                <span class="personLabelDoc"> Address </span>
	                <span> 	             
	                	<span class="pL10"><form:input id="raw_address"  path="rawAddress" class="standardinputText" maxlength="62" readonly="true"/>  </span>  	 
	                    
	                    <span class="pL30">City</span>
	                    <span class="pL10"><form:input id="PERSON_CITY"  path="stdMedOldDoc.personCity" class="standardinputSmallText1 ml10" maxlength="17"/></span>
	                    
	                    <span class="pL30">State </span>
	                    <span  class="pL10">
						<form:input id="PERSON_STATE"  path="stdMedOldDoc.personState" class="standardinputSmallText1 ml10" maxlength="2"/>
					</span>
	                    
	                    <span class="pL30">Zip </span>
	                    <span class="pL10"> 
	                       <form:input id="PERSON_ZIP"  path="stdMedOldDoc.personZip" class="standardInputSmallText1 ml10" maxlength="5"/> - <form:input id="PERSON_PLUS4"  path="stdMedOldDoc.personPlus4" class="standardInputSmallText1 ml10" maxlength="4"/>  
	                    </span>    
	                    
	                    <span class="pL30">County </span>
	                    <span class="pL10"> 
	                    	 <form:select id="PERSON_COUNTY"  path="stdMedOldDoc.personCounty"  cssClass="oAdrDlg newCounty standardInputSmallText1 roundedBrdr" disabled="true"> 
								<form:option value=""></form:option>
								<form:options items="${sessionScope.countyMap}"/>
							 </form:select>
	                    </span> 	                    
	                    <br>
	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
	                </span>
            </div>
            <div class="personInfo pT10 pTRBL10">
            	<span  class="" style="padding-left:59px"><form:input id="PERSON_ADDRESS"  path="stdMedOldDoc.personAddress" class="standardinputText" maxlength="62" readonly="true"/></span>
              	 
              	  <span class="pL30">Phone </span>
                   <span class="pL10"> 
                      <form:input id="PERSON_TELEPHONE"  path="stdMedOldDoc.personTelephone" class="standardInputSmallText1 ml10" maxlength="5"/> 
                   </span> 
                   
                <br>               
                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
                    
            </div>
        </div>
                
        <div id="raceEthnicity" class="container" style="border-top:1px dotted; padding:10px 0 5px 0;">
			<div style="float:left; width:35%; margin-left:20px; border-right:1px dotted;">			
				<div> Race </div> 
				<div>					
					<form:checkbox id="RACE_WHITE1"  path="stdMedOldDoc.raceWhite" value="1" />White	
					<br>
					<form:checkbox id="RACE_BLACK1"  path="stdMedOldDoc.raceBlack" value="1" /> African American
					<br>
					<form:checkbox id="RACE_ASIAN1"  path="stdMedOldDoc.raceAsian" value="1" /> Asian
					<br>
					<form:checkbox id="RACE_INDIAN1"  path="stdMedOldDoc.raceIndian" value="1" /> American Indian/ Alaskan Native		
					<br>			
					<form:checkbox id="RACE_PACIFIC1"  path="stdMedOldDoc.racePacific" value="1" /> Pacific Islander
					<br>
					<form:checkbox id="RACE_OTHER1"  path="stdMedOldDoc.raceOther" value="1" /> Other 
					<br>
					<form:checkbox id="RACE_UNKNOWN1"  path="stdMedOldDoc.raceUnknown" value="1" /> Unknown						
				</div>	
			</div>
			<div class=" " style="float:left; width:25% ;">
				<div style=" padding-left:10px;">
					<span><spring:message code="label.ethnicity" />  (Hispanic or Latino)</span>
					<div> 
						<form:radiobutton id="ETHNICITY1"  path="stdMedOldDoc.ethnicity" value="01" /> <spring:message code="label.yes" /><br>
						<form:radiobutton id="ETHNICITY2"  path="stdMedOldDoc.ethnicity" value="02" /> <spring:message code="label.no" /><br>
						<form:radiobutton id="ETHNICITY3"  path="stdMedOldDoc.ethnicity" value="09" /> <spring:message code="label.unknown" />
					</div>
				</div>	
				<div style="padding:15px 0 10px 10px; border-top: dotted 1px;">
					<div class="floatLeft pR10" style="border-right: dotted 1px;">
						<div class=""> Current Gender </div>
						<div  class="">
							<div> <form:radiobutton id="CURRENT_GENDER1"  path="stdMedOldDoc.currentGender" value="M" /> Male </div>
							<div> <form:radiobutton id="CURRENT_GENDER2"  path="stdMedOldDoc.currentGender" value="F" /> Female </div>
							<div> <form:radiobutton id="CURRENT_GENDER3"  path="stdMedOldDoc.currentGender" value="U" />Unknown</div>		
						</div>
					</div>
					<div class="floatLeft pL10">
						<div class=""> Transgender </div>
						<div  class="">
							<div> <form:radiobutton id="CURRENT_GENDER4"  path="stdMedOldDoc.currentGender" value="X" /> MTF </div>		
							<div> <form:radiobutton id="CURRENT_GENDER5"  path="stdMedOldDoc.currentGender" value="Y" /> FTM </div>		
							<div> <form:radiobutton id="CURRENT_GENDER6"  path="stdMedOldDoc.currentGender" value="T" /> Unspecified </div>														
						</div>
					</div> 
				</div>
			</div>
			
			<!--  Medical details -->
			
			<div style="float:left; width:35%; height: 142px; padding-left:10px; padding-bottom: 12px; border-left:1px dotted;">						
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Medical Record Number
					</div>
					<div style="float:left">
						<form:input id="MED_RECORD_NUMBER"  path="stdMedOldDoc.medRecordNumber" class="standardInputMedText mL5" maxlength="10"/>
					</div>
				</div>
				<div class="mT5 container">
					<div style="float:left; width:45%">
						CIN
					</div>
					<div style="float:left">
						<form:input id="MEDICAID_NUMBER"  path="stdMedOldDoc.medicaidNumber" class="standardInputMedText mL5" maxlength="10"/>
					</div>
				</div>			
				<div class="mT5 container">
					<div style="float:left; width:45%">
						HIV CRG
					</div>
					<div style="float:left">
						<form:input id="stateno"  path="stdMedOldDoc.hivCrg" class=" standardInputMedText mL5" maxlength="10"/>
					</div>
				</div>					
				<div class="mT5 container">
					<div style="float:left; width:45%">
						HIV SCREEN
					</div>
					<div style="float:left">
						<form:input id="HIV_SCREEN"  path="stdMedOldDoc.hivScreen" class="standardInputMedText mL5" maxlength="40"/>
					</div>
				</div>					
			</div>
		</div>
       

        <div class="divHeadingCls bGHeading clearBoth">
            <span class=" mL2"> Event Criteria:</span>                                 
            <span class="criteriaInfo"> </span>
        </div>
        
        <div class="eventCriteriaFields" style="border-bottom: 1px dotted;">
            <div class="" style="width:23%; float:left; border-right:1px dotted; padding:15px 0 10px 20px;">
                <div >           
                        <div><span><form:checkbox id="CRITERIA_11"  path="stdMedOldDoc.criteria1" value="Y" /> Criteria 1 </span></div>
                        <div><span><form:checkbox id="CRITERIA_21"  path="stdMedOldDoc.criteria2" value="Y" /> Criteria 2 </span></div>
                        <div><span><form:checkbox id="CRITERIA_31"  path="stdMedOldDoc.criteria3" value="Y" /> Criteria 3 </span></div>
                        <div><span><form:checkbox id="CRITERIA_41"  path="stdMedOldDoc.criteria4" value="Y" /> Criteria 4 </span></div>
                       <div><span><form:checkbox id="CRITERIA_51"  path="stdMedOldDoc.criteria5" value="Y" />  Criteria 5 </span></div>
                </div>
            </div>
             <div class="" style="width:23%; float:left; border-right:1px dotted; padding:15px 0 10px 20px;">
                <div >   
                        <div><span><form:checkbox id="CRITERIA_61"  path="stdMedOldDoc.criteria6" value="Y" /> Criteria 6 </span></div>
                        <div><span><form:checkbox id="CRITERIA_71"  path="stdMedOldDoc.criteria7" value="Y" /> Criteria 7 </span></div>
                        <div><span><form:checkbox id="CRITERIA_81"  path="stdMedOldDoc.criteria8" value="Y" /> Criteria 8 </span></div>
                        <div><span><form:checkbox id="CRITERIA_91"  path="stdMedOldDoc.criteria9" value="Y" /> Criteria 9 </span></div>
                        <div><span><form:checkbox id="CRITERIA_101"  path="stdMedOldDoc.criteria10" value="Y" /> Criteria 10 </span></div> 
                </div>
            </div>
             <div class="" style="width:23%; float:left; border-right:1px dotted; padding:15px 0 10px 20px;">
                <div >           
                    <div><span><form:checkbox id="CRITERIA_111"  path="stdMedOldDoc.criteria11" value="Y" /> Criteria 11 </span></div>
                    <div><span><form:checkbox id="CRITERIA_121"  path="stdMedOldDoc.criteria12" value="Y" /> Criteria 12 </span></div>
                    <div><span><form:checkbox id="CRITERIA_131"  path="stdMedOldDoc.criteria13" value="Y" /> Criteria 13 </span></div>
                    <div><span><form:checkbox id="CRITERIA_141"  path="stdMedOldDoc.criteria14" value="Y" /> Criteria 14 </span></div>                       
                   <div><span><form:checkbox id="CRITERIA_151"  path="stdMedOldDoc.criteria15" value="Y" />  Criteria 15 </span></div>
                </div>
            </div>
            <div class="" style="width:23%; float:left; padding:15px 0 10px 20px">
                <div >                        
                    <div><span><form:checkbox id="CRITERIA_161"  path="stdMedOldDoc.criteria16" value="Y" /> Criteria 16 </span></div>
                    <div><span><form:checkbox id="CRITERIA_171"  path="stdMedOldDoc.criteria17" value="Y" /> Criteria 17 </span></div>
                    <div><span><form:checkbox id="CRITERIA_181"  path="stdMedOldDoc.criteria18" value="Y" /> Criteria 18 </span></div>
                    <div><span><form:checkbox id="CRITERIA_191"  path="stdMedOldDoc.criteria19" value="Y" /> Criteria 19 </span></div>
                    <div><span><form:checkbox id="CRITERIA_201"  path="stdMedOldDoc.criteria20" value="Y" /> Criteria 20 </span></div>
                </div> 
            </div>
            
            
            <div id="criteriaPopUp" style="display:none">
            </div>
            <div id="criteriaContent" class="displayNone">
	            <div class="pL20" style="padding-top:15px;" >
	            	<div  style="font-weight: bold; margin: 10px 0">
	            		A recepient is considered HIV/AIDS if <b><u>any</u></b> of the following are true any time within the entire FFY.
	            	</div>
	            	
	            	<table>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 1:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText">One or more claim with a Dx starting with 042 or 043 </span>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 2:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText">One or more inpatient claim with DRGs 700-707 </span>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 3:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText">One or more claim with inpatient rate codes 2908, 2964 or 2965</span>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 4:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText">One or more claim with 044.x or 044.9 Dx <span class="andCriteria">and</span> one or more claim with an AIDS defining condition (AI code list #4)</span>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 5:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText">One or more claim with an AIDS HHC, LTC or hospice rate code</span>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 6:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText">One or more inpatient claim with DRGs 708-716</span>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 7:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText">Two or more claims with an "outpatient" rate code (AI code list #10)</span>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 8:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText">Two or more pharmacy claims with an NDC for an ARV</span>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 9:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText">Two or more referred ambulatory claims with a procedure code for ARV administration</span>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td width="100">
	            				<span class="criteriaLabel">Criteria 10:</span>
	            			</td>
	            			<td width="550">
	            				<span class="criteriaText"> One or more claims with a Dx code 647.6, 795.71, 795.8, 279.10, 279.3, 279.19, or V08.x <span class="andCriteria"> and</span> one or more claim with a DX code for an AIDS defining condition (AI code list #4)</span>
	            			</td>
	            		</tr>
	            	</table>          
                                                    
                   <div style="font-weight: bold; margin: 30px 0 10px 0"> One or more claims with a Dx code 647.6, 795.71, 795.8, 279.10, 279.3, 279.19, or V08.x <span class="andCriteria bold">and any of the following</span></div>
                  
                   <table>
                   		<tr>
                   			<td width="100">
                   				<span class="criteriaLabel">Criteria 11:</span>
                   			</td>
                   			<td width="550">
                   				<span class="criteriaText"> One or more pharmacy claim for an ARV</span>
                   			</td> 
                   		</tr>
                   		<tr>
                   			<td width="100">
                   				<span class="criteriaLabel">Criteria 12:</span>
                   			</td>
                   			<td width="550">
                   				<span class="criteriaText"> One or more procedure code for ARV administration</span>
                   			</td> 
                   		</tr>
                   		<tr>
                   			<td width="100">
                   				<span class="criteriaLabel">Criteria 13:</span>
                   			</td>
                   			<td width="550">
                   				<span class="criteriaText"> One or more claim with an "outpatient" rate code</span>
                   			</td> 
                   		</tr>
                   		<tr>
                   			<td width="100">
                   				<span class="criteriaLabel">Criteria 14:</span>
                   			</td>
                   			<td width="550">
                   				<span class="criteriaText"> One or more claim with an HIV/AIDS provider speciality code (249, 303, 308, or 355)</span>
                   			</td> 
                   		</tr>
                   </table>
                                     
                   <div style="font-weight: bold; margin: 30px 0 10px 0"> One or more claims with a rate code of 2983 (preset counseling with testing) <b> and</b> a provider speciality other than 249, 303, 308, 355 (HIV/AIDS speciality), <span class="andCriteria bold">and any of the following</span></div>
                       
                   <table>
                   		<tr>
                   			<td width="100">
                   				<span class="criteriaLabel">Criteria 15:</span>
                   			</td>
                   			<td width="550">
                   				<span class="criteriaText"> One or more claim with an HIV/AIDS provider speciality code (249, 303, 308, or 355)</span>
                   			</td> 
                   		</tr>
                   		<tr>
                   			<td width="100">
                   				<span class="criteriaLabel">Criteria 16:</span>
                   			</td>
                   			<td width="550">
                   				<span class="criteriaText"> One or more claim for an AIDS defining condition</span>
                   			</td> 
                   		</tr>
                   		<tr>
                   			<td width="100">
                   				<span class="criteriaLabel">Criteria 17:</span>
                   			</td>
                   			<td width="550">
                   				 <span class="criteriaText"> One or more claim with a Dx code 647.6, 795.71, 795.8, 279.10, 279.3, 279.19, or V08.x</span>
                   			</td> 
                   		</tr>
                   		<tr>
                   			<td width="100">
                   				<span class="criteriaLabel">Criteria 18:</span>
                   			</td>
                   			<td width="550">
                   				<span class="criteriaText"> One or more claim with an "outpatient" rate code</span>
                   			</td> 
                   		</tr>
                   		<tr>
                   			<td width="100">
                   				<span class="criteriaLabel">Criteria 19:</span>
                   			</td>
                   			<td width="550">
                   				<span class="criteriaText"> One or more pharmacy claim for an ARV</span>
                   			</td> 
                   		</tr>
                   		<tr>
                   			<td width="100">
                   				<span class="criteriaLabel">Criteria 20:</span>
                   			</td>
                   			<td width="550">
                   				<span class="criteriaText"> One or more procedure code for ARV administration</span>
                   			</td> 
                   		</tr>
                   </table>
	            </div> 
            </div>
        </div>

        <div class="divHeadingCls bGHeading clearBoth">
            <span class=" mL2"> Provider Details:</span>                                 
        </div>
        <div class="mLRTB pB10">        
                <span class="fieldHolder25">
                    <span class="fLW25">Name</span>
                    <span class="fLW60">  <input name="PROV_NAME" class="standardinputMedText ml10" maxlength="20"/>  </span>
                </span>
        </div>
         <div class="b1" style="margin: 15px 5px 10px 5px">
            <div class="personInfo pTRBL10">
                <span class="personLabelDoc"> Address </span>
	                <span> 	             
	                	<span class="pL10"><form:input id="raw_prov_address"  path="rawProvAddress" class="standardinputText" maxlength="62" readonly="true"/>  </span>  	 
	                    
	                    <span class="pL30">City</span>
	                    <span class="pL10"><form:input id="PROV_CITY"  path="stdMedOldDoc.provCity" class="standardinputSmallText1" maxlength="17"/></span>
	                    
	                    <span class="pL30">State </span>
	                    <span  class="pL10"><form:input id="PROV_STATE"  path="stdMedOldDoc.provState" class="standardinputSmallText1" maxlength="2"/></span>
	                    
	                    <span class="pL30">Zip </span>
	                    <span class="pL10"> 
	                       <form:input id="PROV_ZIP"  path="stdMedOldDoc.provZip" class="standardInputSmallText1" maxlength="5"/> - <form:input id="PROV_PLUS4"  path="stdMedOldDoc.provPlus4" class="standardInputSmallText1" maxlength="4"/>  
	                    </span>    
	                    
	                    <span class="pL30">County </span>
	                    <span class="pL10"><form:input id="PROV_COUNTY"  path="stdMedOldDoc.provCounty" class="standardInputSmallText1" maxlength="5"/> </span> 	                    
	                    <br>
	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
	                </span>
            </div>
            <div class="personInfo pT10 pTRBL10">
            	<span  class="" style="padding-left:59px"><form:input id="PROV_ADDRESS"  path="stdMedOldDoc.provAddress" class="standardinputText" maxlength="62" readonly="true"/></span>
              	 
              	  <span class="pL30">Phone </span>
                    <span class="pL10"> 
                       <form:input id="PROV_PHONE"  path="stdMedOldDoc.provPhone" class="standardInputMedText" maxlength="5"/> 
                    </span> 
                <br>               
                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
                    
            </div>
        </div> 
    
	</div>
</div>
        <input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
</form:form>
<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
		 <div id="button-row" class="button-row documentButtons center">
		 				
		 				
						<c:choose>
							<c:when  test="${param.hasDeleteFunctionality == true}" >	
									<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
									<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
							</c:when>
							<c:when test="${userPermissions['90'] != null && stdMedicaidDocVO.stateno != null && stdMedicaidDocVO.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null}">
									<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
							</c:when>
					</c:choose>

					<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Save" onclick="saveForm()"/>	 
					<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />				
		</div>