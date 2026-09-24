<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<form:form modelAttribute="stdEPRFOOCDocVO" id="stdEPRFOOCDocVO" cssClass="documentDescription documentFormVO">
	<div id="labDocTab" style="background-color: transparent">
		<div  class="headingCls alignCenter">
			<c:choose>
				<c:when test="${stdEPRFOOCDocVO.FILE_TYPE_CD == 'O'}">
					Provider Submitted OOC Document
				</c:when>
				<c:when test="${stdEPRFOOCDocVO.FILE_TYPE_CD == 'D'}">
					Provider Submitted DNLTC Document
				</c:when>
				<c:when test="${stdEPRFOOCDocVO.FILE_TYPE_CD == 'P'}">
					Provider Submitted SNP Document
				</c:when>
				<c:otherwise>
					EPRF OOC  DOCUMENT
				</c:otherwise>
			</c:choose>
		</div>
		
		
		<div class="pB30 pT20">

			
			<c:if test="${stdEPRFOOCDocVO.PARENT_DOCUMENT_ID != null &&  stdEPRFOOCDocVO.PARENT_DOCUMENT_ID != 0}">

				<div  class="floatLeft width70Perc" id="parentDocumentIdSection">
		            <span class="parentIdSectionLabel bold ml10">Parent DocumentId: </span>
					<span class="parentIdSectionValue"><a href="#" onclick="loadDocumentDlg(${stdEPRFOOCDocVO.PARENT_DOCUMENT_ID}, '${param.documentType}')">${stdEPRFOOCDocVO.PARENT_DOCUMENT_ID}</a></span>
					
		         </div>	
	         </c:if>
	         
			<div  class="floatRight">
	            <span class="mL10 bold">Document ID: </span>
				<span class=""><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
	         </div>	

		</div>
		
	 
		
		 <div class="container pB10 w100">
            <div style="float:left; width: 40%">
	            <div>
	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
                    <span class="ml10"><form:input path="STATENO" class="standardInputSmallText readonly" style="width:180px" maxlength="10" readonly="true"/> </span>
	            </div>    
            </div>
            <div style="float:left; width: 30%">
            	    	
	            <div>
	                <span class="mL10">	eHARS Import Date: </span>
	                
	                <fmt:formatDate value="${stdEPRFOOCDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
	               <span class="mL10"> <form:input path="EHARS_IMPORT_DATE" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/></span>
					
	            </div>    
            </div>
              <div style="float:left;  width: 30%; text-align: right">
            	    
	            <div >
	                <span class="mL10">NYC Ship Date: </span>
	                <fmt:formatDate value="${stdEPRFOOCDocVO.NYC_SHIP_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedNYC_SHIP_DATE_TEXT" ></fmt:formatDate>
					<span class="mL10 "><form:input path="NYC_SHIP_DATE" value="${theFormattedNYC_SHIP_DATE_TEXT}" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
	            </div>                   
            </div>     
   		</div>
   		
   		<jsp:include page="statenoDetails.jsp"></jsp:include>
				
		<!--  Person details -->
		
		<div class="container" style="border:1px dotted; width:100%">	 
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Person Details</span>
			</div>
			
			 <div class="personInfo pT10">
          			<div class="fieldHolder25">
                       <span class="pL10">First Name </span> 
                       <span class="pL5"><form:input path="FNAME" id="EPRF_OOC_FNAME" title="First Name" class="standardInputMedText required" maxlength="100"/></span>
                       <div class="pL10 v_error EPRF_OOC_FNAME "></div> 
                   </div>
                   <div class="fieldHolder25">
                      	<span class="pL10">Middle Name </span> 
                      	<span  class="pL5"><form:input path="MNAME" id="EPRF_OOC_MNAME" title="Middle Name" class="standardInputMedText" maxlength="100"/></span>
                      	<div class="pL10 v_error EPRF_OOC_MNAME "></div>
                      </div>
                   <div class="fieldHolder25">
                      	<span class="pL10">Last Name </span> 
                      	<span  class="pL5"><form:input path="LNAME" id="EPRF_OOC_LNAME" title="Last Name" class="standardInputMedText  required" maxlength="100"/></span>
                      	<div class="pL10 v_error EPRF_OOC_LNAME "></div>
                      </div>
                   <div class="fieldHolder25">
                			<span class="pL10">Suffix </span> 
            				<span class="pL5"><form:input path="SUFFIX" id="EPRF_OOC_SUFFIX" title="Suffix" class="standardInputMedText" maxlength="5"/></span>
            				<div class="pL10 v_error EPRF_OOC_SUFFIX "></div>
            			</div>  
                  <br><br>
                  
                  
          	</div>	
				
			<div class="clearBoth personInfo pB20 ">
				
				
				<div class="fieldHolder25">
					<span style="margin-left:10px"> <spring:message code="label.ssn" /></span><span> <form:input path="SSN" title="SSN" id="EPRF_OOC_SSN" class="standardInputMedText" maxlength="9"/> </span>
					<div class="v_error EPRF_OOC_SSN "></div>
				</div>
				
				<div class="fieldHolder25">
					<span style="margin-left:10px"> <spring:message code="label.birthdate" /></span><span> <form:input path="DOB" id="DATE_OF_BIRTH" title="Date of Birth" class="standardInputMedText mask dPicker" datevalidations="completeDate" maxlength="20"/> </span>
					<div class="v_error DATE_OF_BIRTH "></div>
				</div>
				
				<c:choose>
					<c:when test="${stdEPRFOOCDocVO.FILE_TYPE_CD == 'P'}">
						<div class="fieldHolder33">
							<span class="pL10"> Patient preferred name</span><span> <form:input path="PREF_NAME" title="Preferred name" class="standardInputMedText " maxlength="100"/> </span>
						</div>
					</c:when>
					<c:otherwise>
						<form:hidden path="PREF_NAME" title="Preferred name" class="standardInputMedText " maxlength="100"/> 				
					</c:otherwise>	
				</c:choose>
				
			</div>
	
			
			
		    <div style="float:left; width:99%; margin-left:5px; margin-right:5px; margin-bottom:10px" class="mT5">
			
			<div class="b1" style="margin: 15px 5px 5px 5px" id="eprfOOCDocumentPrsnAddressDtls">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"><form:input path="PERSON_ADDRESS" title="Person Address" class="standardInputText newAddress widgetAddressFieldsReadonly" maxlength="100" />  </span>
	 	                    
	 	                    
	 	                    <span class="pL20">City</span>
	 	                    <span class="pL10"> <form:input path="PERSON_CITY" title="Person City" class="oAdrDlg standardInputSmallText newCity widgetAddressFieldsReadonly"  maxlength="30"/></span>
	 	                    
	 	                    <span class="pL20">State </span>
	 	                    <span  class="pL10">
								<form:input path="PERSON_STATE" title="Person State"  cssClass="oAdrDlg roundedBrdr newState standardInputVerySmall widgetAddressFieldsReadonly"  maxlength="2"/> 
							</span>
	 	                    
	 	                    <span class="pL20">Zip </span>
	 	                    <span class="pL10"> 
	 	                        <form:input path="PERSON_ZIP" title="Person Zip code" class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5" />  
	 	                    </span>
	 	                     <span class="pL20">County </span>
 		                   <span class="pL10"> 
								<form:select path="PERSON_COUNTY" title="Person County"  cssClass="oAdrDlg roundedBrdr newCounty widgetAddressFieldsReadonly" > 
									<form:option value=""></form:option>
									<form:options items="${sessionScope.countyMap}"/>
								</form:select>
	 		               </span>    
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10">
 	            	<span  class="" style="padding-left:59px"><form:input path="rawPersonAddress" class="standardInputText readonly" /></span>
 	               	                	                 
 	                 <span class="pL20"> Phone</span>
 					 <span class="pL10"> <form:input path="PERSON_PHONE" title="Person Phone" class="pNumber standardInputSmallText phoneInputText" maxlength="20" /></span>
					
					<span style="float: right;" class="mr50">
						<a href="javascript:openAddressWidget('eprfOOCDocumentPrsnAddressDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
					 </span>  
 					<br>               
 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
 	                    
 	            </div>
 	        </div>					
			
		</div>		  
		</div>
		
			<!--  Race details -->
			
		<div class="container" style="border:1px dotted; padding-bottom:5px;">
			<div style="float:left; width:25%; margin-left:20px; border-right:1px dotted; height: 232px">				
				<div>
					<spring:message code="label.race" />  
				</div>
				<div>
					<form:checkbox path="RACE_WHITE" value="1"  /> <spring:message code="label.raceWhite" />	
				</div>
				<div>
					<form:checkbox path="RACE_BLACK" value="1"/> African American
				</div>
				<div>
					<form:checkbox path="RACE_ASIAN" value="1" /> Asian
				</div>
				<div>
					<form:checkbox path="RACE_PACIFIC" value="1"/> Asian/Pacific Islander
				</div>
				<div>
					<form:checkbox path="RACE_INDIAN" value="1" /> American Indian/Alaskan Native
				</div>
				<div>
					<form:checkbox path="RACE_OTHER" value="1" /> <spring:message code="label.raceOther" /> 
				</div>
				<div>
					<form:checkbox path="RACE_UNKNOWN" value="1"/> <spring:message code="label.raceUnknown" />
				</div>
			</div>
			<div style="float:left; width:35% ; ">
				<div style="padding:5px 0 5px 10px;">
					<spring:message code="label.ethnicity" /> 
					(Hispanic or Latino) <br/>
										<form:radiobutton path="ETHNICITY" value="01" /> <spring:message code="label.yes" /> <br>
									   <form:radiobutton path="ETHNICITY" value="02" /> <spring:message code="label.no" /><br>
									   <form:radiobutton path="ETHNICITY" value="09" /> <spring:message code="label.unknown" />
				</div>
				<div style="border-top: dotted 1px" class="pB10 clearBoth">
					<div style="padding:10px 0 0 10px;">	
						<div class="floatLeft pR10" style="border-right: dotted 1px;">
							<div class=""> Current Gender </div>
							<div  class="">
								<div> <form:radiobutton path="CURRENT_GENDER" value="M" /> <spring:message code="label.male" /> </div>
								<div> <form:radiobutton path="CURRENT_GENDER" value="F" /> <spring:message code="label.female" /> </div>
								<div> <form:radiobutton path="CURRENT_GENDER" value="U" />Unknown</div>		
							</div>
						</div>
						<div class="floatLeft pL10">
							<div class=""> Transgender </div>
							<div  class="">
								<div> <form:radiobutton path="CURRENT_GENDER" value="X" /> MTF </div>		
								<div> <form:radiobutton path="CURRENT_GENDER" value="Y" /> FTM </div>		
								<div> <form:radiobutton path="CURRENT_GENDER" value="T" /> Unspecified </div>														
							</div>
						</div> 
 					</div>	
				</div>
				<div style="border-top: dotted 1px" class="clearBoth">
					<div style="padding:10px 0 0 10px;">	
						<div class="floatLeft pR10" >
							<div class=""> Birth Sex 
								<span class="mL10"> <form:radiobutton path="BIRTH_SEX" value="M" /> <spring:message code="label.male" /> </span>
								<span class="mL10"> <form:radiobutton path="BIRTH_SEX" value="F" /> <spring:message code="label.female" /> </span>
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
						<form:input path="MED_RECORD_NUMBER" title="Medical Record Number"  class="standardInputMedText mL5" maxlength="17"/>
					</div>
				</div>			
				<div class="mT5 container">
					<div style="float:left; width:45%">
						CIN
					</div>
					<div style="float:left">
						<form:input path="MEDICAID_NUMBER" id="EPRF_OOC_MEDICAID_NUMBER" title="CIN"  class="standardInputMedText mL5" maxlength="8"/>
					</div>
					<div class="v_error EPRF_OOC_MEDICAID_NUMBER "></div>
				</div>
				
				<div class="mT5 container">
					<div style="float:left; width:45%">
						<spring:message code="label.din" />
					</div>
					<div style="float:left">
						<form:input path="DIN" id="EPRF_OOC_DIN"  title="DIN" class="standardInputMedText mL5" maxlength="7"/>
					</div>
					<div class="v_error EPRF_OOC_DIN "></div>
				</div>

				<div class="mT5 container">
					<div style="float:left; width:45%">
						<spring:message code="label.nysid" />
					</div>
					<div style="float:left">
						<form:input path="NYSID" title="NYSID" id="EPRF_OOC_NYSID"  class="standardInputMedText mL5" maxlength="9"/>
					</div>
					<div class="v_error EPRF_OOC_NYSID "></div>
				</div>
				
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Last Viral Load Date
					</div>
					<div style="float:left">
						<fmt:formatDate value="${stdEPRFOOCDocVO.LAST_VIRAL_LOAD_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedLAST_VIRAL_LOAD_DATE" />
						<form:input path="LAST_VIRAL_LOAD_DATE" id="EPRF_OOC_LAST_VIRAL_LOAD_DATE"  title="Last Viral Load Date" value="${theFormattedLAST_VIRAL_LOAD_DATE}" class="standardInputMedText mL5 mask dPicker" datevalidations="completeDate" maxlength="10"/>
					</div>
					
					<div class="v_error EPRF_OOC_LAST_VIRAL_LOAD_DATE "></div>
				</div>	
				
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Last Viral Load result
					</div>
					<div style="float:left">
						<form:input path="LAST_VIRAL_LOAD_RESULT" title="Last Viral Load result"  class="standardInputMedText mL5" maxlength="100"/>
					</div>
				</div>	
				
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Last CD4 date
					</div>
					<div style="float:left">
						<fmt:formatDate value="${stdEPRFOOCDocVO.LAST_CD4_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedLAST_CD4_DATE" />
						<form:input path="LAST_CD4_DATE" id="EPRF_OOC_LAST_CD4_DATE" value="${theFormattedLAST_CD4_DATE}" title="Last CD4 date"  class="standardInputMedText mL5 mask dPicker" datevalidations="completeDate" maxlength="10"/>
					</div>
					<div class="v_error EPRF_OOC_LAST_CD4_DATE "></div>
				</div>	
				
				
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Last CD4 result count
					</div>
					<div style="float:left">
						<form:input path="LAST_CD4_RESULT_COUNT" title="Last CD4 result count"  class="standardInputMedText mL5 nOnly" maxlength="5"/>
					</div>
				</div>	
				
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Last CD4 result percent
					</div>
					<div style="float:left">
						<form:input path="LAST_CD4_RESULT_PERCENT" title="Last CD4 result percent" class="standardInputMedText mL5 nOnly" maxlength="3"/>
					</div>
				</div>	
					
			
				<div class="mT5 container">
					<div style="float:left; width:45%">
						Last Visit Date
					</div>
					<div style="float:left">
						<fmt:formatDate value="${stdEPRFOOCDocVO.PROV_LAST_VISIT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedPROV_LAST_VISIT_DATE" />
						<form:input path="PROV_LAST_VISIT_DATE" id="EPRF_OOC_PROV_LAST_VISIT_DATE" title="Last Visit Date" value="${theFormattedPROV_LAST_VISIT_DATE}" class=" standardInputSmallText3 phoneInputText providerFields mask dPicker" datevalidations="completeDate" maxlength="10" />
					</div>
					
					<div class="v_error EPRF_OOC_PROV_LAST_VISIT_DATE "></div>
				</div>	
			</div>
		</div>
	
	
		<div class="container" style="border:1px dotted; width:100%; padding-bottom:5px;" >	 
			<div id="prvDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Submission Details</span></div>
			<div style="margin-top:10px; margin-bottom:10px; margin-left:5px;  margin-right:5px;" class="">
					<span class="mL25"> File Type </span><span> 
						<form:select path="FILE_TYPE_CD" class="standardInputSmallText1 readonly" >
							<form:option value=""></form:option>
							<form:option value="D">DNLTC</form:option>
							<form:option value="P">SNP</form:option>			
							<form:option value="O">OOC</form:option>
						</form:select> 
					</span> 
					<span style="margin-left:10px"> Upload File Name</span><span> <form:input path="UPLOAD_FILENAME" class="standardInputText readonly" /> </span>
					<span style="margin-left:10px"> Upload File Date</span><span> 
						<fmt:formatDate value="${stdEPRFOOCDocVO.UPLOAD_DT}"  type="date" pattern="MM/dd/yyyy" var="theFormattedUPLOAD_DT" />
						<form:input path="UPLOAD_DT" value="${theFormattedUPLOAD_DT}" class="standardInputSmallText1 readonly " />
					
					 </span>
					 <span style="margin-left:10px"> Upload By</span><span> <form:input path="UPLOAD_ID" class="standardInputMedText readonly" /> </span>
					<br/>
			</div>
									
		</div>
		
		<!--  Provider details -->
			
		<div class="container" style="border:1px dotted; width:100%; padding-bottom:5px;" id="labDocumentProviderAddressDtls">	 
			<div id="prvDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Provider Details</span></div>
			<div style="margin-top:2px; margin-left:5px;  margin-right:5px;" class="providerDetails">
					<span class="mL25"> <spring:message code="label.firstname" /></span><span> <form:input path="PROV_FNAME" title="Provider First Name" class="standardInputMedText providerFields" maxlength="40"/> </span> 
					<span style="margin-left:10px"> <spring:message code="label.lastname" /></span><span> <form:input path="PROV_LNAME" title="Provider Last Name" class="standardInputMedText providerFields" maxlength="40"/> </span>
					<br/>
			</div>
			
			<div class="b1 providerDetails" style="margin: 15px 5px 5px 5px" id="eprfOOCDocumentProviderDtls">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"><form:input path="PROV_ADDRESS" class="oAdrDlg standardInputText newAddress widgetAddressFieldsReadonly providerFields" title="Provider Address" maxlength="100"/></span>	 	                    
	 	                    
	 	                    <span class="pL20">City</span>
	 	                    <span class="pL10"> <form:input path="PROV_CITY" class=" oAdrDlg standardInputSmallText  newCity widgetAddressFieldsReadonly providerFields" title="Provider City" maxlength="30"/> </span>
	 	                    
	 	                    <span class="pL20">State </span>
	 	                    <span  class="pL10">
								<form:input path="PROV_STATE" class=" oAdrDlg standardInputVerySmall  newState wS40 widgetAddressFieldsReadonly providerFields" title="Provider State"  maxlength="2"/>
							</span>
	 	                    
	 	                    <span class="pL20">Zip </span>
	 	                    <span class="pL10"> 
	 	                        <form:input path="PROV_ZIP" class=" oAdrDlg nOnly  standardInputSmallText1 newZip widgetAddressFieldsReadonly providerFields" title="Provider Zipcode" maxlength="5"/>  
	 	                    </span>
	 	                      <span class="pL20">County </span>
	 		                   <span class="pL10"> 
									<form:select id="PROV_COUNTY"  tabindex="8" path="PROV_COUNTY" title="Provider County" class=" oAdrDlg standardInputMedText newCounty wC100 widgetAddressFieldsReadonly providerFields" >
								        <form:option value="" label="" />
										<form:options items="${sessionScope.countyMap}"/>
							  		 </form:select>
		 		               </span>       	      
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10">
 	            	<span  class="" style="padding-left:59px"> <form:input path="rawProviderAddress" class=" standardInputText readonly" readonly="" /></span>
 	               	                 
 	                 <span class="pL20"> Phone</span>
 					 <span class="pL10"> <form:input path="PROV_PHONE" title="Provider Phone" class=" pNumber standardInputSmallText3 phoneInputText providerFields" maxlength="20" /></span>
 					 
 					 <span class="pL20"> NPI</span>
 					 <span class="pL10"> <form:input path="PROV_NPI" title="Provider NPI" class=" standardInputSmallText3 phoneInputText providerFields" maxlength="12" /></span>
 					 
 					 <span style="float: right;" class="mr50">
							<a href="javascript:openAddressWidget('eprfOOCDocumentProviderDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
					 </span> 
 	                <br>               
 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
 	                    
 	            </div>
 	            <div class="personInfo pT10 pTRBL10">
 					 <span class=""> License</span>
 					 <span class="pL10"> <form:input path="PROV_LICENSE" class=" standardInputSmallText3 phoneInputText providerFields" title="Provider License" maxlength="20" /></span>
 	                <br>               
 	            </div>
 	        </div>						
		</div>
			
		<!--  Facility details -->
			
		<div style="padding-bottom:5px; border:1px dotted;" class="container" id="eprfOOCDocumentFacilityAddressDtls">
			<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class="mL2">Facility Details</span>
			</div>
			<div style="padding-left:10px; padding-top:2px;" class="providerDetails">
				Facility Name <form:input path="FACILITY_NAME" class=" standardInputText facilityFields" maxlength="80" title="Facility Name"/>					
			</div>	
			
			<div class="b1 facilityDetails" style="margin: 15px 5px 5px 5px">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"><form:input path="FACILITY_ADDRESS" class="oAdrDlg standardInputText newAddress widgetAddressFieldsReadonly facilityFields" title="Facility Address" maxlength="100"/></span>	 	                    
	 	                    
	 	                    <span class="pL20">City</span>
	 	                    <span class="pL10"> <form:input path="FACILITY_CITY" class=" oAdrDlg standardInputSmallText newCity widgetAddressFieldsReadonly facilityFields" title="Facility city" maxlength="30" /> </span>
	 	                    
	 	                    <span class="pL20">State </span>
	 	                    <span  class="pL10">
								 <form:input path="FACILITY_STATE" class=" oAdrDlg standardInputVerySmall  newState wS40 widgetAddressFieldsReadonly facilityFields" title="Facility State"  maxlength="2"/>
							</span>
	 	                    
	 	                    <span class="pL20">Zip </span>
	 	                    <span class="pL10"> 
	 	                        <form:input path="FACILITY_ZIP" class=" oAdrDlg nOnly  standardInputSmallText1 newZip widgetAddressFieldsReadonly facilityFields"  maxlength="5" title="Facility Zipcode"/> 
	 	                    </span>
	 	                     <span class="pL20">County </span>
	 		                   <span class="pL10"> 
									<form:select id="FACILITY_COUNTY"  tabindex="8" path="FACILITY_COUNTY" class=" oAdrDlg standardInputMedText newCounty wC100 widgetAddressFieldsReadonly facilityFields"  title="Facility County">
								        <form:option value="" label="" />
										<form:options items="${sessionScope.countyMap}"/>
								    </form:select>
		 		               </span>       
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10">
 	            	<span  class="" style="padding-left:59px"> <form:input path="rawFacilityAddress" class=" standardInputText readonly" /></span>
 	               	                 
 	                 <span class="pL20"> Phone</span>
 					 <span class="pL10"> <form:input path="FACILITY_PHONE" class=" pNumber standardInputSmallText3 phoneInputText facilityFields" title="Facility Phone" maxlength="20"/></span>
 					 
 					 <span class="pL20"> NPI</span>
 					 <span class="pL10"> <form:input path="FACILITY_NPI" class=" standardInputSmallText3 phoneInputText facilityFields" maxlength="12" title="Facility NPI"/></span>
 					 
 					 
 					 <span style="float: right;" class="mr50">
							<a href="javascript:openAddressWidget('eprfOOCDocumentFacilityAddressDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
					</span> 
	 	                <br>               
 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
 	                    
 	            </div>
 	        </div>						
		</div>
		
	</div>																	 										
</form:form>																

<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
		 <div id="button-row" class="button-row documentButtons center">
		 				
		 				
						<c:choose>
							<c:when  test="${param.hasDeleteFunctionality == true}" >	
									<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
									<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
							</c:when>
							<c:when test="${stdEPRFOOCDocVO.STATENO != null && stdEPRFOOCDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
									<c:if test="${userPermissions['90'] != null}" >
										<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
									</c:if>
									<c:if test="${userPermissions['100'] != null}" >
										<input type="button" id="editDocument"  class="editDocumentBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="confirmEditDocumentFromStateno()" />
									</c:if>
							</c:when>
					</c:choose>

										
					<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Save" onclick="saveForm()"/>	 
					<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />				
		</div>
		
		<script src="${staticPath}/js/docs/docViewers.js?v=${buildDate}"></script>
		