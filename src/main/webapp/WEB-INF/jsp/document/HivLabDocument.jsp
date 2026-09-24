<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>
 <script src="${staticPath}/js/docs/labErrorDocEdit.js?v=${buildDate}"></script>
 	
<form:form modelAttribute="stdLabDocVO" id="stdLabDocVO" cssClass="documentDescription">

<form:errors path="*" cssClass="errorblock" element="div" />
	<div id="labDocTab" style="background-color: transparent">
		<div style="padding-left:43%; " class="headingCls">
				RAW LAB  DOCUMENT
		</div>
		
		
		<div style="padding-bottom:10px; text-align: right;">
            <span class="mL10">Document ID: </span>
			<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
			<form:hidden path="MSHLABPFI" id="mshLabPfi"/>
         </div>  
         <div style="padding-bottom:10px; text-align: right;">
            <span class="mL10">PFI: </span>
			<span class=" "><form:input id="PFI" path="MSHLABPFI" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
         </div>   
		
		<!--  Person details -->
		
		<div class="container" style="border:1px dotted; width:100%">	 
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Person Details</span></div>
			<div style="margin-top:2px; margin-left:8px;  margin-right:5px;">
					<span> <spring:message code="label.firstname" /></span><span> <form:input path="PIDFIRSTNAME"  class="editable standardInputMedText" /> </span> 
					<span style="margin-left:10px"> <spring:message code="label.middleName" /></span><span> <form:input path="PIDMIDDLENAME"  class="editable standardInputMedText" /> </span>
					<span style="margin-left:10px"> <spring:message code="label.lastname" /></span><span> <form:input path="PIDLASTNAME"  class=" editable standardInputMedText" /> </span>
					<fmt:formatDate value="${stdLabDocVO.PIDDATEOFBIRTH}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDateOfBirth" />
					<span style="margin-left:10px"> Date of Birth </span><form:input path="PIDDATEOFBIRTHTEXT" value="${theFormattedDateOfBirth}" class="editable dPicker standardInputMedText" /> </span>
					</br>
				
			</div>
		
							
			
		    <div style="float:left; width:99%; margin-left:5px; margin-right:5px; margin-bottom:10px" class="mT5">
		    
		    
		    
		    <div class="b1" style="margin: 15px 5px 5px 5px" id="labDocumentPrsnAddressDtls">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address1 </span>
 		                <span> 	                	
	 	                	<span class="pL10"><form:input path="PIDADDRESSLINE1" class="standardInputText widgetFields newAddress" tabindex="-1"/>  </span>
	 	                    
	 	                    
	 	                    <span class="pL20">City</span>
	 	                    <span class="pL10"> <form:input path="PIDCITY" class="oAdrDlg standardInputSmallText widgetFields newCity"  tabindex="-1"/></span>
	 	                    
	 	                    <span class="pL20">State </span>
	 	                    <span  class="pL10">
								<form:input path="PIDSTATE"  cssClass="oAdrDlg roundedBrdr widgetFields newState standardInputVerySmall "  tabindex="-1" /> 
									
							</span>
	 	                    
	 	                    <span class="pL20">Zip </span>
	 	                    <span class="pL10"> 
	 	                        <form:input path="PIDZIPCODE" class="oAdrDlg nOnly standardInputSmallText1 widgetFields newZip" maxlength="5"  tabindex="-1"/> 
								
	 	                    </span>
	 	                    <span class="pL20">County </span>
	 		                   <span class="pL10"> 
									<form:select path="PIDCOUNTYCODEORIGINAL"  cssClass="oAdrDlg roundedBrdr widgetFields newCounty"  tabindex="-1"> 
										<form:option value=""></form:option>
										<form:options items="${sessionScope.countyMap}"/>
									</form:select>
		 		               </span>
		 		               <span style="float: right;" class="mr5">
									<button value="Edit" onclick="openAddressWidgetFromErrorLabDocument('labDocumentPrsnAddressDtls');return false;" class="displayNone editAddress">Edit</button>
								</span>       	                    
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10">
 	            	<span class="personLabelDoc"> Address2 </span>
 	            	<span  class="" style="padding-left:9px"><form:input path="PIDADDRESSLINE2" class="editable standardInputMedText" /></span>
 	               	                	                 
 	                 <span class="pL10"> Home Phone</span>
 					 <span class=""> <form:input path="PIDHOMEPHONE" class="editable pNumber standardInputSmallText phoneInputText" maxlength="14" /></span>
 					 
 					  <span class="pL10">  Work Phone</span>
 					 <span class=""> <form:input path="PIDWORKPHONE" class="editable standardInputSmallText newCountry" /></span>
 					 
 					 <span class="pL10">  <spring:message code="label.country" /></span>
 					 <span class="pL10"> <form:input path="PIDCOUNTRYCODE" class="editable standardInputSmallText newCountry" /></span>
 					 
 					
 	            </div>
 	        </div>	
 	        
 	        
						
			
		</div>		  
		</div>
		
			<!--  Race details -->
			
		<div class="container" style="border:1px dotted; padding-bottom:5px;">
			<div style="float:left; width:25%; margin-left:20px; border-right:1px dotted;">				
				<div>
					<spring:message code="label.race" />  
				</div>
				<div>
					<form:checkbox path="PIDRACEWHITE" class="editable" value="Y" /> <spring:message code="label.raceWhite" />	
				</div>
				<div>
					<form:checkbox path="PIDRACEBLACK" class="editable" value="Y" /> African American
				</div>
				<div>
					<form:checkbox path="PIDRACEPACIFIC" class="editable"  value="Y" /> Pacific Islander
				</div>
				<div>
					<form:checkbox path="PIDRACEASIAN" class="editable"  value="Y" /> Asian Islander
				</div>
				<div>
					<form:checkbox path="PIDRACENATIVEAMERALASKAN" class="editable" value="Y" /> American Indian/Alaskan Native
				</div>
				<div>
					<form:checkbox path="PIDRACEOTHER" class="editable" value="Y" /> <spring:message code="label.raceOther" /> 
				</div>
				<div>
					<form:checkbox path="PIDRACEUNKNOWN" class="editable"  value="Y" /> <spring:message code="label.raceUnknown" />
				</div>
			</div>
			
			<div style="float:left; width:35% ; ">
				<div style="padding:5px 0 5px 10px;">
					<spring:message code="label.ethnicity" /> 
					(Hispanic or Latino) </br>
										<form:input path="PIDETHNICGROUPCODE"  maxlength="3"  class="standardInputMedText editable"/>  
				</div>
				<div style="border-top: dotted 1px">
					<div style="padding:10px 0 0 10px;">												
 						<div class=""> Gender </div>
						<div class="">
 							<div> <form:input path="PIDSEXCODE" maxlength="1"  class="standardInputMedText editable"/>  </div>
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
						<form:input path="PIDMEDICALRECORDNUMBER" class="standardInputMedText mL5" />
					</div>
				</div>			
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Insurance Number
					</div>
					<div style="float:left">
						<form:input path="PIDPATIENTACCOUNTNUM" class="standardInputMedText mL5" />
					</div>
				</div>
				<div class="mT5 container">
					<div style="float:left; width:45%">
						CIN
					</div>
					<div style="float:left">
						<form:input path="PIDMEDICAIDNUMBER" class="standardInputMedText mL5" />
					</div>
				</div>
				<div class="mT5 container">
					<div style="float:left; width:45%">
						<spring:message code="label.din" />
					</div>
					<div style="float:left">
						<form:input path="PIDDIN" class="standardInputMedText mL5" />
					</div>
				</div>

				<div class="mT5 container">
					<div style="float:left; width:45%">
						<spring:message code="label.nysid" />
					</div>
					<div style="float:left">
						<form:input path="PIDNYSID" class="standardInputMedText mL5" />
					</div>
				</div>				
			</div>
		</div>
		<div id="labDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
			<span class=" mL2">Lab Details</span>
		</div>
		
		<!--  Lab details -->
		
		<div style="padding-top:5px; padding-bottom:5px; border:1px dotted;" class="container">	 
			<div style="float:left; width:35%; padding-left:10px; border-right:1px dotted;">
				
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Observation Result Key 
					</div>
					<div style="float:left">
						<form:input path="OBXOBSERVATIONRESULTKEY" class="standardInputMedText mL5" />
					</div>
				</div>
				
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Collection Date
					</div>
					<div style="float:left">
						<fmt:formatDate value="${stdLabDocVO.OBRCOLLECTIONDATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedCollectionDate" />
						<form:input path="OBRCOLLECTIONDATETEXT" value="${theFormattedCollectionDate}" class="editable dPicker standardInputMedText mL5" />
					</div>
				</div>
				
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Observation Date
					</div>
					<div style="float:left">
						<fmt:formatDate value="${stdLabDocVO.OBXOBSERVATIONDATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedObservationDate" />
						<form:input path="OBXOBSERVATIONDATETEXT" value="${theFormattedObservationDate}" class="standardInputMedText mL5" />
					</div>
				</div>

				

				<div class="mT5 container">
					<div style="float:left; width:45%">
						Result Reported Date
					</div>
					<div style="float:left">
						<fmt:formatDate value="${stdLabDocVO.OBRRESULTREPORTDATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedResultReportDate" />
						<form:input path="OBRRESULTREPORTDATETEXT" value="${theFormattedResultReportDate}" class="editable dPicker standardInputMedText mL5" />
					</div>
				</div>

				<div class="mT5 container">
					<div style="float:left; width:45%">
						Accession Number
					</div>
					<div style="float:left">
						<form:input path="OBRACCESSIONNUM" class="standardInputMedText mL5" />
					</div>
				</div>	
				
				<div class="mT5 container">
					<div style="float:left; width:45%">
						HIV Vaccine Recipient
					</div>
					<div style="float:left">
						<form:input path="PIDVACCINETRIALIND" class="standardInputMedText mL5" />
					</div>
				</div>	
				
				

<!-- 				<div class="mT5 container"> -->
<!-- 					<div style="float:left; width:45%"> -->
<!-- 						HIV Vaccine Recipient -->
<!-- 					</div> -->
<!-- 					<div style="float:left"> -->
<%-- 						<form:input path="hivVaccineRecip" value="${hivVaccineRecip}" class="standardInputMedText mL5" /> --%>
<!-- 					</div> -->
<!-- 				</div>					 -->

<!-- 				<div class="mT5 container"> -->
<!-- 					<div style="float:left; width:45%"> -->
<!-- 						Reside Outside USA -->
<!-- 					</div> -->
<!-- 					<div style="float:left"> -->
<%-- 						<form:input path="resideOutsideUsa" value="${resideOutsideUsa}" class="standardInputMedText mL5" /> --%>
<!-- 					</div> -->
<!-- 				</div>	 -->
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Length of Residency in US
					</div>
					<div style="float:left">
						<form:input path="PIDLENGTHOFRESIDENCY" class="standardInputMedText mL5" />
					</div>
				</div>	
				
				
			</div>
		
			<div style="float:right; width:63%; padding-bottom:5px; padding-left:10px;">
				<div style="float:left; width:49%;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							LOINC CD
						</div>
						<div style="float:left">
							<form:input path="OBXLOINCCODE" maxlength="10" class="standardInputMedText mL5" />
						</div>
					</div>

					<div class="mT5 container">
						<div style="float:left; width:40%">
							Test Result
						</div>
						<div style="float:left">
							<form:input path="OBXOBSERVATIONRESULTTEXT" class="editable standardInputMedText mL5" />
						</div>
					</div>
					
				</div>
				<div style="float:right; width:49%; margin-bottom: 5px;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							DESCRIPTION
						</div>
						<div style="float:left">
							<form:input path="OBXLOINCDESCSHORT" maxlength="4000" class="standardInputMedText mL5" />
						</div>
					</div>
					
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Unit Measure Code
						</div>
						<div style="float:left">
							<form:select path="OBXUNITOFMEASURE" class="editable standardInputSmallText2 mL5" >
							
							
								<c:if test="${stdLabDocVO.OBXUNITOFMEASURE != null and stdLabDocVO.OBXUNITOFMEASURE != '' }">
									<form:option value="${stdLabDocVO.OBXUNITOFMEASURE}">${stdLabDocVO.OBXUNITOFMEASURE}</form:option>
								</c:if>
							
								<c:forEach items="${unitsList}" var="units">
									<form:option value="${units}">${units}</form:option>
								</c:forEach>
							
							
							</form:select>
							
							
							
							
							
							
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
							<form:input path="PIDPATIENTIDINTERNAL"   class="standardInputMedText mL5" />
						</div>
					</div>					
				</div>
				<div style="float:right; width:49%; margin-bottom: 5px;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							External ID
						</div>
						<div style="float:left">
							<form:input path="PIDPATIENTIDEXTERNAL"  class="standardInputMedText" mL5/>
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
							<form:input path="MSHSENDINGFACILITYNAME"   class="standardInputMedText mL5" />
						</div>
					</div>	
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Sending Facility CLIA
						</div>
						<div style="float:left">
							<form:input path="MSHSENDINGFACILITYCLIA"   class="standardInputMedText mL5" />
						</div>
					</div>					
				</div>
				<div style="float:right; width:49%; margin-bottom: 5px;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Referring Facility
						</div>
						<div style="float:left">
							<form:input path="PIDFACILITYIDEXTERNAL"  class="standardInputMedText mL5 " />
						</div>
					</div>
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Testing Facility
						</div>
						<div style="float:left">
							<form:input path="PIDFACILITYIDINTERNAL"  class="standardInputMedText mL5" />
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
							<form:input path="OBRSPECIMENSOURCECODE"  class="standardInputMedText mL5 editable" />
						</div>
					</div>					
				</div>
				<div style="float:right; width:49%; margin-bottom: 5px;">
					<div class="mT5 container">
						<div style="float:left; width:40%">
							Specimen Source Name
						</div>
						<div style="float:left">
							<form:input path="OBRSPECIMENSOURCENAME" maxlength="255" class="standardInputMedText mL5 editable" />
						</div>
					</div>				
				</div>			
			</div>						 
		</div>
		
		<!--  Provider details -->
			
		<div class="container" style="border:1px dotted; width:100%; padding-bottom:5px;" id="labDocumentProviderAddressDtls">	 
			
			
			
			<div class="clearBoth">
				<div id="prvDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
					<span class=" mL2">Provider Details</span>
				</div>
				
				<div style="margin-top:2px; margin-left:5px;  margin-right:5px;" class="displayNone" id="masterProvSection">
						<span class=""> Provider Name: </span><span> 
						<input name="masterProviderName" id = "masterProviderName" class="editable standardInputText " /> 
						<a href="#" class="dropdownImage" onClick="return false"></a></span>
						<input type="button" id="clearProvBtn" onclick="clearProvData();" title="Clear" class="mL10 editable" value="Clear">											
										
						 
						<br/>
				</div>
				
				<div class="mT10" style=" margin-left:5px;  margin-right:5px;">
						<span class=""> <spring:message code="label.firstname" /></span><span> <form:input path="OBRPROVIDERFIRSTNAME" maxlength="80"  class="editable standardInputMedText provFld addFname" /> </span> 
						<span style="margin-left:10px"> <spring:message code="label.middleName" /></span><span> <form:input path="OBRPROVIDERMIDDLENAME" maxlength="80"  class="editable standardInputMedText provFld addMname" /> </span>
						<span style="margin-left:10px"> <spring:message code="label.lastname" /></span><span> <form:input path="OBRPROVIDERLASTNAME" maxlength="80"  class="editable standardInputMedText provFld addLname" /> </span>
						<span style="margin-left:10px"> <spring:message code="label.suffix" /></span><span><form:input path="OBRPROVIDERNAMESUFFIX" maxlength="80" class="editable standardInputMedText provFld addSuffix" /> </span>
						<br/>
					
				</div>
			</div>
			
			
			
			<div class="b1" style="margin: 15px 5px 5px 5px">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address1 </span>
 		                <span> 	                	
	 	                	<span class="pL10"><form:input path="ZLRPROVIDERADDRESSLINE1" maxlength="106" class="oAdrDlg widgetFields standardInputText newAddress provFld addAddress" tabindex="-1"/></span>	 	                    
	 	                    
	 	                    <span class="pL30">City</span>
	 	                    <span class="pL10"> <form:input path="ZLRPROVIDERCITY" class="oAdrDlg widgetFields standardInputSmallText  newCity provFld addCity" readonly="" tabindex="-1"/> </span>
	 	                    
	 	                    <span class="pL30">State </span>
	 	                    <span  class="pL10">
								<form:input path="ZLRPROVIDERSTATE"  cssClass="oAdrDlg widgetFields roundedBrdr newState standardInputVerySmall provFld addState"  tabindex="-1" /> 
									
							</span>
	 	                    
	 	                    <span class="pL30">Zip </span>
	 	                    <span class="pL10"> 
	 	                        <form:input path="ZLRPROVIDERZIPCODE" class="oAdrDlg nOnly widgetFields standardInputSmallText1 newZip provFld addZip" readonly="" maxlength="5" tabindex="-1"/>  
	 	                    </span>
	 	                </span>
 	            </div>
 	            <div style="float: right;" class="mr5">
						<button value="Edit" onclick="openAddressWidgetFromErrorLabDocument('labDocumentProviderAddressDtls');return false;" class="displayNone editAddress">Edit</button>
					 </div>
 	                
 	            <div class="personInfo pT10 pTRBL10">
 	            	<span class="personLabelDoc"> Address2 </span>
 	            	<span  class="" style="padding-left:59px"> <form:input path="ZLRPROVIDERADDRESSLINE2" maxlength="106" class="editable standardInputText provFld" readonly="" /></span>
 	               	                 
 	                 <span class="pL30"> Phone</span>
 					 <span class="pL10"> <form:input path="OBRCALLBACKPHONE" maxlength="20" class="editable pNumber standardInputSmallText3 phoneInputText provFld addPhone"  /></span>
					 
					  <span class="pL30"> Provider NPI</span>
 					 <span class="pL10"> <form:input path="OBRPROVIDERID"  id="OBRPROVIDERID" class="standardInputSmallText3 editable provFld addNpi" maxlength="20" title="Provider NPI"/></span>
 					 
 	            </div>
 	            <br>   
 	        </div>
						
		</div>
			
		<!--  Facility details -->
			
		<div style="padding-bottom:5px; border:1px dotted;" class="container" id="labDocumentFacilityAddressDtls">
			<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class="mL2">Facility Details</span>
			</div>
			<div style="padding-left:10px; padding-top:2px;">
				Facility Name <form:input path="ZLRFACILITYNAME" maxlength="90" class="editable standardInputText" />					
			</div>	
			
			
			<div class="b1" style="margin: 15px 5px 5px 5px">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address1 </span>
 		                <span> 	                	
	 	                	<span class="pL10"><form:input path="ZLRFACILITYADDRESSLINE1" maxlength="106" class="oAdrDlg widgetFields standardInputText newAddress " tabindex="-1"/></span>	 	                    
	 	                    
	 	                    <span class="pL30">City</span>
	 	                    <span class="pL10"> <form:input path="ZLRFACILITYCITY" class="oAdrDlg widgetFields standardInputSmallText newCity " readonly="" tabindex="-1"/> </span>
	 	                    
	 	                    <span class="pL30">State </span>
	 	                    <span  class="pL10">
								 <form:input path="ZLRFACILITYSTATE"  cssClass="oAdrDlg widgetFields roundedBrdr newState standardInputVerySmall " tabindex="-1" /> 
									
							</span>
	 	                    
	 	                    <span class="pL30">Zip </span>
	 	                    <span class="pL10"> 
	 	                        <form:input path="ZLRFACILITYZIPCODE" class="oAdrDlg nOnly widgetFields  standardInputSmallText1 newZip " readonly="" maxlength="5" tabindex="-1"/>  
	 	                    </span>
	 	                </span>
 	            </div>
 	            <div style="float: right;" class="mr5">
						<button value="Edit" onclick="openAddressWidgetFromErrorLabDocument('labDocumentFacilityAddressDtls');return false;" class="displayNone editAddress">Edit</button>
					 </div>
 	                
 	            <div class="personInfo pT10 pTRBL10">
 	            	<span class="personLabelDoc"> Address2 </span>
 	            	<span  class="" style="padding-left:59px"> <form:input path="ZLRFACILITYADDRESSLINE2" maxlength="106" class="editable standardInputText " readonly="" /></span>
 	               	                 
 	                 <span class="pL30"> Phone</span>
 					 <span class="pL10"> <form:input path="ZLRFACILITYPHONE" maxlength="20" class="editable pNumber standardInputSmallText3 phoneInputText" /></span>
					 
					  <span class="pL30"> Facility NPI</span>
 					 <span class="pL10"> <form:input path="ZLRFACILITYCODE"  id="ZLRFACILITYCODE" class="standardInputSmallText3 readonly" maxlength="20" title="Facility NPI"/></span>
 					 
 					
 	            </div>
 	            <br>   
 	        </div>	
						
		</div>
		
	</div>																	 										
	
	 <div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
	 <div id="button-row" class="button-row ">
		
			<div align="center">
				
				<c:if test="${hasProviderError == true }">
					<input type="button" id="addProviderButton" class="labErrorDocAddProvider button rounded blue effect-3 "  value="Add Same as Provider Address" />
				</c:if>
				<c:if test="${hasFacilityError == true }">
					<input type="button" id="addFacilityButton" class="labErrorDocAddFacility button rounded blue effect-3 "  value="Add Same as Facility Address" />
				</c:if>
				
				
				
				<c:choose>
						<c:when  test="${stdLabDocVO.CURRENT_PROCESS_STATUS == 33}" >	
						        <input type="button" id="uButton"  class="undeleteErrorDoc button rounded blue effect-3"  value="Undelete" />  
						</c:when>
						
						<c:when test="${psMessageReasonDetails != null   &&  psMessageReasonDetails.RSN_NO_PS_MSG == 'N'   &&  psMessageReasonDetails.RSN_PS_MSG == 'N'}">
						
								<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3 displayNone"  value="Trigger Message"  onclick="confirmAddressForPsMessage('Y')"/>
								
							
				 				<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3 displayNone"  value="Reject PS Message"  onclick="rejectPsMessage()"/>
				 		
						
						</c:when>
						<c:otherwise>
								<input type="button" id="eButton" class="displayNone labErrorDocEdit button rounded blue effect-3 "  value="Edit" />
								<input type="button" id="dButton" class="displayNone deleteErrorDoc button rounded blue effect-3"  value="Delete" />
								<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Save" />	 
								<input type="button" id="rButton"  class="repriseBtnclick button rounded blue effect-3 " value="Reprise" />
								<input type="button" id="addStatenoButton"  class="addStatenoBtnclick button rounded blue effect-3 displayNone" value="Add to stateno" />
								<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" onclick="cancelEditPage()"/>						
						</c:otherwise>
						
				</c:choose>
			</div>
		
	</div>
	
		
	<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />	
</form:form>																



