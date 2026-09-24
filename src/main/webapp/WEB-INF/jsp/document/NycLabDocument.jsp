<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>

<script src="${staticPath}/js/docs/nycDoc.js?v=${buildDate}"></script>

<link href="${staticPath}/css/docs/commonForm.css" rel="stylesheet" type="text/css" />

<style>

	.pTRBL10{
	    padding: 10px 10px 0px 10px;
	}
	
	.b1{
	    border-style: dotted;
	    border-width: 1px;
	    margin: 5px;
	}
	
	.pL10{
		padding-left:10px;
	}
	
	#StdNYCLabDocVO .labelunderneath2{
		padding-left:120px;
	}
	
	#StdNYCLabDocVO .labelunderneath1{
		padding-left:175px;
	}
	
	#StdNYCLabDocVO .labelunderneath3{
		padding-left:160px;
	}
	
	.pL20{
		padding-left: 20px;
	}
	
	.pT20{
		padding-top: 20px;
	}
	
	.mT10{
		margin-top: 10px;
	}
	
	
	#patientInformation{ padding: 0}
	
	.riskLabel{
		float:left; 
		width:80%; 
		border-right:1px dotted;  
		padding-bottom:5px;
	}
	
	.riskValue{
		float:right; 
		width:19%; 
		padding-bottom:5px;
	}
	
	.riskDiv{
		float:left; 
		width:99%; 
		padding-bottom:5px;
		border-bottom:1px dotted;
	}

</style>


<form:form modelAttribute="StdNYCLabDocVO" id="StdNYCLabDocVO" cssClass="documentFormVO documentDescription">
<form:errors path="*" cssClass="errorblock" element="div" />

<div id="labDocTab">
   <div style="padding-left:43%; " class="headingCls">
                NYC Lab Document
        </div>
    
    
    
     <div class="pB20  pT20">
		
					
			<c:if test="${StdNYCLabDocVO.PARENT_DOCUMENT_ID != null &&  StdNYCLabDocVO.PARENT_DOCUMENT_ID != 0}">

				<div  class="floatLeft " id="parentDocumentIdSection">
		            <span class="parentIdSectionLabel bold">Parent DocumentId: </span>
					<span class=" parentIdSectionValue"><a href="#" onclick="loadDocumentDlg(${StdNYCLabDocVO.PARENT_DOCUMENT_ID}, '${param.documentType}')">${StdNYCLabDocVO.PARENT_DOCUMENT_ID}</a></span>
					
		         </div>	
	         </c:if>


		</div>
		
		
    <div class="container pB10 w100 mT10">
            <div style="float:left;  width: 40%">
	            <div>
	                <span class="ml10"> <spring:message code="label.stateNo" />: </span>
	                <span class="ml10" style=""><form:input path="STATENO" class="standardInputSmallText readonly"/> </span>
	            </div>    
            </div>
            <div style="float:left; width: 30%">
 	         	<div>
 	                <span class="mL10 bold">eHARS Import Date: </span>
					<span class=" ml10">
						<fmt:formatDate value="${StdNYCLabDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
						<form:input path="EHARS_IMPORT_DATE" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/>
					</span>
 	            </div> 
                    
            </div>
            <div style="float:right; width: 30%; text-align:right">
 	            
 	                   
				<div>
		            <span class="mL10 bold">Document ID: </span>
					<span class="ml10"><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
		         </div>
	              
 	         </div>     
    </div>
    
    <jsp:include page="statenoDetails.jsp"></jsp:include>
    
    

    <div class="container" id="patientInformation">  
        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
            <span class=" mL2">Patient Information</span>
        </div>
        
        <div class="personInfo pL10 ">
            <div class="fieldHolder25">
                     <span class=" bold">First Name </span> 
                     <span class="pL5"><form:input path="FIRST_NAME" id="NYC_LAB_FNAME" class="standardInputMedText " title="First Name" maxLength="30"/></span>
                     <div class=" v_error NYC_LAB_FNAME "></div> 
                 </div>
                 <div class="fieldHolder25">
                    	<span class="pL10 bold">Middle Name </span> 
                    	<span  class="pL5"><form:input path="MIDDLE_NAME" id="NYC_LAB_MNAME" class="standardInputMedText"  title="Middle Name" maxLength="30"/></span>
                    	<div class="pL10 v_error NYC_LAB_MNAME "></div>
                    </div>
                 <div class="fieldHolder25">
                    	<span class="pL10 bold">Last Name </span> 
                    	<span  class="pL5"><form:input path="LAST_NAME" id="NYC_LAB_LNAME" class="standardInputMedText " title="Last Name" maxLength="30"/></span>
                    	<div class="pL10 v_error NYC_LAB_LNAME "></div>
                    </div>
                
        </div>
        <div class="clearBoth personInfo pL10 pT10">
        		<div class="fieldHolder25">
	                <span class="bold">Event Code</span>
	                <span class="pL10">
	                	<form:select path="EVENT_CODE"  class="standardInputSmallText" maxlength="14">
	                		<form:option value=""></form:option>
	                		<form:option value="01">HIV</form:option>
	                		<form:option value="02">AIDS</form:option>
	                	</form:select>
	                </span>
	            </div>
	            <div class="fieldHolder25">   
                	<span class="pL10 bold">NYSID</span>
                	<span class="pL10"><form:input path="NYSID" id="NYC_LAB_NYSID"  class="standardInputSmallText" maxlength="9" title="NYSID"/></span>
                	<div class="pL10 v_error NYC_LAB_NYSID "></div>
                </div>
                
                <div class="fieldHolder25">
                	<span class="pL10 bold">SSN</span>
                	<span class="pL10"><form:input id="NYC_LAB_SSN" path="SSN"  class="standardInputSmallText" maxlength="9" title="SSN"/></span>
                	<div class="pL10 v_error NYC_LAB_SSN "></div>
                </div>
                <div class="fieldHolder25">
                	<span class="pL10 bold">Date Received</span>
                	<span class="pL10"><form:input path="RECEIVED_DATE"  class="standardInputSmallText readonly" maxlength="14" title="Date Received"/></span>
                </div>
                
              
                
                
        </div>
        
        <div class="clearBoth personInfo pL10 pT10">
        	<div class="fieldHolder25">
        		<span class=" bold">Date of Birth</span>
                <span class="pL10"><form:input path="DOB_TEXT" id="DATE_OF_BIRTH" title="Date of Birth" class="standardInputSmallText mask dPicker" datevalidations="completeDate" maxlength="10"/></span>
                <div class="v_error DATE_OF_BIRTH "></div>
            </div>
            <div class="fieldHolder25">
                <span class="pL10 bold">Vital Status</span>
                <span class="">
                	<form:radiobutton path="VITAL_STATUS" value="1" /> Alive
                	<form:radiobutton path="VITAL_STATUS" value="2" /> Dead
                </span>
           </div>
           <div class="fieldHolder25">     
                <span class="pL10 bold">Date of Death</span>
                <span class="pL10"><form:input path="DOD_TEXT" id="DATE_OF_DEATH" title="Date of Death"  class="standardInputSmallText mask dPicker" datevalidations="completeDate" maxlength="10"/></span>
                
                <div class="pL10 v_error DATE_OF_DEATH "></div>
			</div>
			<div class="fieldHolder25">                
                <span class="pL10 bold">Death State</span>
                <span class="pL10"><form:input path="DEATH_STATE" title="Death State"  class="standardInputSmallText" maxlength="2"/></span>
            </div>    
               
                
                
        </div>
        
        
        
        
         <br/><br/>
        <div class="clearBoth b1 " id="nyclabDocumentPersonAddressDtls">
            <div class="personInfo pTRBL10">
                <span class="personLabelDoc bold">Current Address </span>
                <span> 
                    <span class="pL10"><form:input path="PERSON_ADDRESS1"  class="standardInputMedText  newAddress widgetAddressFieldsReadonly" title="Person Address" maxlength="50"/></span>
                    
                    
                    <span class="pL10 bold">City</span>
                    <span class="pL10"><form:input path="PERSON_CITY"  class="oAdrDlg standardInputSmallText newCity widgetAddressFieldsReadonly" title="City" maxlength="65"/></span>
                    
                    <span class="pL10 bold">State </span>
                    <span  class="pL10"><form:input path="PERSON_STATE"  class="oAdrDlg standardInputSmallText newState widgetAddressFieldsReadonly" style="width:50px" title="State" maxlength="2"/></span>
                    
                    <span class="pL10 bold">Zip </span>
                    <span class="pL10"> 
                        <form:input path="PERSON_ZIP"  class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" title="ZipCode" maxlength="5"/>
                    </span>
                    
					<span class="pL10 bold">County </span>
                    	<span class="pL10">  
                    		<form:select path="PERSON_COUNTY"  cssClass="oAdrDlg roundedBrdr newCounty widgetAddressFieldsReadonly" title="County"> 
									<form:option value=""></form:option>
									<form:options items="${sessionScope.countyMap}"/>
							</form:select> 
                    </span> 
                    
                    <br>
                    <span class="labelunderneath3"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
                    
                </span>
            </div>
            <div class="personInfo pT10 pTRBL10">
            		<div>
	            		<div class="floatLeft">   
	                    	<span class="" style="padding-left:103px"><form:input path="rawAddress"  class="standardInputMedText readonly" /></span>
	                    	
	                    	<div class="">
                    			<span class="labelunderneath3"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
                    		</div>
	                    </div>
	                    <div class="fieldHolder20">   
	                    
	                    	<span class="pL10 bold">Country</span>
	                    	<span class="pL10"><form:input path="PERSON_COUNTRY"  class="standardInputSmallText" title="Country" maxlength="3"/></span>
	                    </div>
	                    <div class="fieldHolder20">   
	                     	<span class="pL10 bold">Address Date</span>
	                    	<span class="pL10"><form:input path="ADDRESS_DATE_TEXT" id="NYC_LAB_ADDRESS_DATE_TEXT" datevalidations="partialDate" class="standardInputSmallText1 mask dPicker" maxlength="10" title="Address Date"/></span>
	                    	
	                    	<div class="pL10 v_error NYC_LAB_ADDRESS_DATE_TEXT "></div>
	                    </div>
	                    <div class="fieldHolder25">   
	                    	<span class="pL10 bold">Address Type</span>
		                    <span class="pL10">
		                    	<form:select path="ADDRESS_TYPE_CODE"  class="standardInputSmallText" >
		                    		<form:option value=""></form:option>
		                    		<form:option value="RES">Residence</form:option>
		                    		<form:option value="RSH">Residence at HIV Diagnosis</form:option>
		                    		<form:option value="RSA">Residence at AIDS Diagnosis</form:option>
		                    	</form:select>
		                    </span>
		                </div>
		                
		                <span style="float: right;" class="mr50">
							<a href="javascript:openAddressWidget('nyclabDocumentPersonAddressDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
						</span> 
					</div>
				
                    
	                
		            
            </div><br/><br/><br/>
        </div>

        <!--  Race details -->
        <div class="clearBoth raceRiskReprt">
            <div class="fieldHolder32">
                <div class="container borderField pT10 " style="width: 349px;height:133px">
                    <div class="bold">
                        Race
                    </div>
                    
                    <div>
                        <form:checkbox path="RACE_WHITE"  value="1" /> White 
                    </div>
                    <div>
                        <form:checkbox path="RACE_BLACK"  value="1" /> Black / African American
                    </div>
                    <div>
                        <form:checkbox path="RACE_ASIAN"  value="1" /> Asian
                    </div>
                    <div>
                        <form:checkbox path="RACE_INDIAN"  value="1" /> American Indian/Alaskan Native
                    </div>
                    <div>
                        <form:checkbox path="RACE_PACIFIC"  value="1" /> Native Hawaiian or Other Pacific Islander
                    </div>
					<div>
						<form:checkbox path="RACE_UNKNOWN" value="1"/> Unknown
					</div>
                </div>  
                 
                 
            </div>
            <div class="fieldHolder32 ">
                <div class=" " style="width: 349px;">
                
                	 <div class="borderField pT10" style="height:32px; width:349px">   
	                    <div>
	                        <span class="bold">Birth Sex</span> <form:radiobutton path="BIRTH_SEX"  value="M" /> Male  <form:radiobutton path="BIRTH_SEX"  value="F" /> Female
	                    </div>
	                </div>  
	                
                	
                
	           		
	          
		          <div class="borderField pT10 " style="height:79px; width:349px">
	                	<div class="floatLeft pR10" style="border-right: dotted 1px;">
							<div class="bold"> Current Gender </div>
							<div  class="">
								<div> <form:radiobutton path="CURRENT_GENDER" value="M" /> Male </div>
								<div> <form:radiobutton path="CURRENT_GENDER" value="F" /> Female </div>
								<div> <form:radiobutton path="CURRENT_GENDER" value="U" />Unknown</div>		
							</div>
						</div>
						<div class="floatLeft pL10">
							<div class=""> &nbsp; </div>
							<div  class="">
								<div> <form:radiobutton path="CURRENT_GENDER" value="X" /> MTF </div>		
								<div> <form:radiobutton path="CURRENT_GENDER" value="Y" /> FTM </div>		
								<div> <form:radiobutton path="CURRENT_GENDER" value="T" /> Unspecified </div>														
							</div>
						</div>   
	                </div> 
	            	   
                </div>
            </div>
            
            <div class="fieldHolder32 ">
                <div class="   floatLeft" style="width:349px; height:133px">
             	   <!--  Risk details -->
             	   
             	    <div class="borderField pT10" style="height:32px; width:349px">   
	                    <div>
	                        <span class="bold">Country of birth</span> 
	                        <form:select path="BIRTH_COUNTRY" cssClass="standardInputSmallText " title="Birth Country">
								<form:option value=""></form:option>
								<form:options items="${sessionScope.countryMap}"/>
								<form:option value="OTH">Other</form:option>
								<form:option value="UNK">Unknown</form:option>
							</form:select>
	                    </div>
	                </div>  
	                
	                <div class="borderField pT10 " style="height:79px; width:349px">
	                	<div class="floatLeft pR10">
							<div class="bold"> Ethnicity  ( Hispanic or Latino) </div>
							<div  class="">
								<div> <form:radiobutton path="ETHNICITY" value="01" /> Yes </div>
								<div> <form:radiobutton path="ETHNICITY" value="02" /> No </div>
								<div> <form:radiobutton path="ETHNICITY" value="09" />Unknown</div>		
							</div>
						</div>
	                </div> 
               			

                </div>
            </div> 
 
        </div>     
    </div>  
    
    
    
    
    
    <div class="container" id="patientInformation">  
        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
            <span class=" mL2">Risk Information</span>
        </div>
    	<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Sex with male </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:radiobutton path="SWM"  value="Y"  /> Yes </span>
				<span class="mL5"><form:radiobutton path="SWM"  value="N"  /> No </span>
				<span class="mL5"><form:radiobutton path="SWM"  value="U"  /> Unknown </span>
			</div>
		</div>
		
		<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Sex With Female </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:radiobutton path="SWF"  value="Y" title="Sex with Female" /> Yes </span>
				<span class="mL5"><form:radiobutton path="SWF"  value="N" title="Sex with Female" /> No </span>
				<span class="mL5"><form:radiobutton path="SWF"  value="U" title="Sex with Female" /> Unknown </span>
			</div>
		</div>
		
		<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Injected non-prescription drugs </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:radiobutton path="IDU"  value="Y" title="Injected non-prescription drugs" /> Yes </span>
				<span class="mL5"><form:radiobutton path="IDU"  value="N" title="Injected non-prescription drugs" /> No </span>
				<span class="mL5"><form:radiobutton path="IDU"  value="U" title="Injected non-prescription drugs" /> Unknown </span>
			</div>
		</div>
		
		<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Received transfusion of blood/blood components (other than clotting factor)  </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:radiobutton path="RECV_BLOOD_PROD" class="otherFieldDependentRadio" dependentField="NYC_LAB_FIRST_TRANSFUSION_DATE_TEXT,NYC_LAB_LAST_TRANSFUSION_DATE_TEXT" value="Y"  /> Yes </span>
				<span class="mL5"><form:radiobutton path="RECV_BLOOD_PROD" class="otherFieldDependentRadio" dependentField="NYC_LAB_FIRST_TRANSFUSION_DATE_TEXT,NYC_LAB_LAST_TRANSFUSION_DATE_TEXT"  value="N"  /> No </span>
				<span class="mL5"><form:radiobutton path="RECV_BLOOD_PROD" class="otherFieldDependentRadio" dependentField="NYC_LAB_FIRST_TRANSFUSION_DATE_TEXT,NYC_LAB_LAST_TRANSFUSION_DATE_TEXT"  value="U"  /> Unknown </span>
			</div>
		</div>
		
		
		<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel "> 
					<span  id="firstLastDatesReceived" class="pL10"> First date received 
						<form:input path="FIRST_TRANSFUSION_DATE_TEXT"  id="NYC_LAB_FIRST_TRANSFUSION_DATE_TEXT" class="standardInputMedText  mask dPicker" datevalidations="partialDate" maxlength="10" title="First Date received" />
						&nbsp;&nbsp;&nbsp;&nbsp; Last date received 
						<form:input path="LAST_TRANSFUSION_DATE_TEXT" id="NYC_LAB_LAST_TRANSFUSION_DATE_TEXT" class="standardInputMedText  mask dPicker" datevalidations="partialDate" maxlength="10" title="Last Date received" />
					</span>  		
					
					<div class="pL10 v_error NYC_LAB_FIRST_TRANSFUSION_DATE_TEXT "></div>
					<div class="pL10 v_error NYC_LAB_LAST_TRANSFUSION_DATE_TEXT "></div>
			</div>
		</div>
		
		<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Received transplant of tissue/organs or artificial insemination </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:radiobutton path="RECV_TRANSPLANT"  value="Y"  /> Yes </span>
				<span class="mL5"><form:radiobutton path="RECV_TRANSPLANT"  value="N"  /> No </span>
				<span class="mL5"><form:radiobutton path="RECV_TRANSPLANT"  value="U"  /> Unknown </span>
			</div>
		</div>
		
		<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Worked in a healthcare or clinical laboratory setting </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:radiobutton path="HEALTHCARE_WORKER"  value="Y"  /> Yes </span>
				<span class="mL5"><form:radiobutton path="HEALTHCARE_WORKER"  value="N"  /> No </span>
				<span class="mL5"><form:radiobutton path="HEALTHCARE_WORKER"  value="U"  /> Unknown </span>
			</div>
		</div>
		
		<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel "> 
				<span class="pB5 pL10"> If occupational exposure is being investigated or considered as primary mode of exposure , specify occupation and setting: 
					<form:select path="OCCUPATION" id="OCCUPATION"  class="standardInputSmallText" maxlength="100" title="Occupation">
							<form:option value="" >--select--</form:option>
								<c:forEach items="${occupations}" var="occupation">
									<form:option value="${occupation.lookup_cd}" label="${occupation.lookup_name}" name="${occupation.lookup_name}"></form:option>
								</c:forEach>
							</form:select>
					</span>		
			</div>
		</div>
		
		
		
		<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> HETEROSEXUAL contact with intravenous/injection drug user </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:radiobutton path="SEX_PARTNER_IDU"  value="Y"  /> Yes </span>
				<span class="mL5"><form:radiobutton path="SEX_PARTNER_IDU"  value="N"  /> No </span>
				<span class="mL5"><form:radiobutton path="SEX_PARTNER_IDU"  value="U"  /> Unknown </span>
			</div>
		</div>
		
		<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> HETEROSEXUAL contact with bisexual male </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:radiobutton path="SEX_PARTNER_MSM"  value="Y"  /> Yes </span>
				<span class="mL5"><form:radiobutton path="SEX_PARTNER_MSM"  value="N"  /> No </span>
				<span class="mL5"><form:radiobutton path="SEX_PARTNER_MSM"  value="U"  /> Unknown </span>
			</div>
		</div>
		
		<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> HETEROSEXUAL contact with person with hemophilia / coagulation disorder with documented with HIV infection </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:radiobutton path="SEX_PARTNER_HEMOPHILIAC"  value="Y"  /> Yes </span>
				<span class="mL5"><form:radiobutton path="SEX_PARTNER_HEMOPHILIAC"  value="N"  /> No </span>
				<span class="mL5"><form:radiobutton path="SEX_PARTNER_HEMOPHILIAC"  value="U"  /> Unknown </span>
			</div>
		</div>
		
		
		<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> HETEROSEXUAL contact with person with AIDS or documented HIV infection, risk not specified </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:radiobutton path="SEX_PARTNER_HIV"  value="Y"  /> Yes </span>
				<span class="mL5"><form:radiobutton path="SEX_PARTNER_HIV"  value="N"  /> No </span>
				<span class="mL5"><form:radiobutton path="SEX_PARTNER_HIV"  value="U"  /> Unknown </span>
			</div>
		</div>
		
		
		
		<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> HETEROSEXUAL contact with transfusion recipient with documented HIV infection </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:radiobutton path="SEX_PARTNER_TRANSFUSION"  value="Y"  /> Yes </span>
				<span class="mL5"><form:radiobutton path="SEX_PARTNER_TRANSFUSION"  value="N"  /> No </span>
				<span class="mL5"><form:radiobutton path="SEX_PARTNER_TRANSFUSION"  value="U"  /> Unknown </span>
			</div>
		</div>
		
		<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> HETEROSEXUAL contact with transplant recipient with documented HIV infection </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:radiobutton path="SEX_PARTNER_TRANSPLANT"  value="Y"  /> Yes </span>
				<span class="mL5"><form:radiobutton path="SEX_PARTNER_TRANSPLANT"  value="N"  /> No </span>
				<span class="mL5"><form:radiobutton path="SEX_PARTNER_TRANSPLANT"  value="U"  /> Unknown </span>
			</div>
		</div>
		
		<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Other documented risk (please include detail in Comments section) </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:radiobutton path="RISK_OTHER"  value="Y"  /> Yes </span>
				<span class="mL5"><form:radiobutton path="RISK_OTHER"  value="N"  /> No </span>
				<span class="mL5"><form:radiobutton path="RISK_OTHER"  value="U"  /> Unknown </span>
			</div>
		</div>
		
    </div>
    
    
    <div id="personHIVDetails" class="container facilityDetails">   
	              <div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
	                      <span class=" mL2">Facility Information</span>                                  
	              </div>
	              <!--  Provider details  -->
	              <div class="pL10 pT10">
	                      <span class="">
	                              <span class="pL10">Name</span>
	                              <span class="pL10">
	                              	<form:select path="FACILITY_UID" class="standardInputText facilityFields" onchange="loadFacilityAddressDetails('#nyclab_Facility', this)">
	                              		<form:option value="" >--select--</form:option>
										<c:forEach items="${subFacilities}" var="facility">
											<form:option value="${facility.facility_uid}" label="${facility.facility_name}" name="${facility.facility_name}"></form:option>
										</c:forEach>
									</form:select>
	                              
	                              </span> 
	                      </span>    
	                     
	                      <span class="pL30">Type</span>
	                      <span class="pL10"><input name="FACILITY_TYPE" id="nyclab_FacilityTypeCode" class="standardInputText readonly" title="Facility Type" maxLength="20"/>&nbsp;&nbsp;</span>
	             
	                
	                           
	              </div><br>
	              
	              <div class="b1" style="margin: 15px 5px 10px 0px">
	       <div class="personInfo pTRBL10" id="airsDocumentFacilityAddressDtls">
	           <span class="personLabelDoc bold"> Address </span>
	           <span> 	                	
	           	<span class="pL10"> <input name="FACILITY_ADDRESS" value="${facilityDetails.ADDRESS}" id="nyclab_FacilityAddress" class="standardInputText facilityFields newAddress readonly" title="Address" maxLength="62"/>  </span>	 	        
	           	            	 	                    
	               <span class="pL20 bold">City</span>
	               <span class="pL10"><input name="FACILITY_CITY" value="${facilityDetails.CITY}" id="nyclab_FacilityCity" class="standardInputSmallText facilityFields  newCity readonly" title="City" maxLength="30"/></span>
	               
	               <span class="pL20 bold">State </span>
	               <span  class="pL10">
						<input name="FACILITY_STATE" value="${facilityDetails.STATE}" id="nyclab_FacilityState"  class="standardInputSmallText1 facilityFields  newState readonly" title="State" maxLength="2" />
					</span>
	                
	                <span class="pL20 bold">Zip </span>
	                <span class="pL10"> 
	                    <input name="FACILITY_ZIP"  value="${facilityDetails.ZIPCODE}" id="nyclab_FacilityZipcode" class="standardInputSmallText1 facilityFields  newZip readonly" title="Zipcode" maxLength="5"/> 
	               </span> 
	               
	               <span class="pL20 bold">County </span>
	               <span class="pL10"> 
	               	<input name="FACILITY_COUNTY" value="${sessionScope.countyMap[facilityDetails.COUNTY_FIPS]}" id="nyclab_FacilityCountyFips" class=" newCounty standardInputSmallText1 facilityFields  newCounty readonly" /> 
	               </span>     	                    
	           </span>
	       </div>
	       <div class="personInfo pT10 pTRBL10">
         	
	         	<span class="pL30 bold">Phone </span>
	               <span class=""> 
	                   <input name="FACILITY_PHONE" value="${facilityDetails.PHONE}" id="nyclab_FacilityPhone" class="pNumber standardInputSmallText2 facilityFields readonly" />
	                   
	                   
	               </span>  	              	
	           
	           
	                
	        </div>
	    </div>
	</div>
	
	<div class="container" id="patientInformation">  
        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
            <span class=" mL2">Opportunistic Illnesses</span>
        </div>
        
        
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Specimen collection date of earliest positive test when using approved alternative HIV testing algorithm to detect HIV infection  </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="XLAB_HIV_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate"  title="Date" maxlength="10"/> </span>
			</div>
			
			<div class="pL10 v_error XLAB_HIV_DATE_TEXT "></div>
		</div>
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Collection Date  </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="COLLECTION_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate"  title="Collection Date" maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error COLLECTION_DATE_TEXT "></div>
		</div>
		
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Lab test names </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="LAB_TEST_NAME"  class="standardInputSmallText" title="Lab Test Name" maxlength="51"/> </span>
			</div>
		</div>
		
		
		
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Lab Result  </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="LAB_RESULT"  class="standardInputSmallText"  title="Lab Result" maxlength="100"/> </span>
			</div>
		</div>
		
		
		<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Lab Units  </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="LAB_UNITS"  class="standardInputSmallText"  title="Lab Units" maxlength="4"/> </span>
			</div>
		</div>
		
		
		<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Interpretation  </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="INTERPRETATION"  class="standardInputSmallText" title="Interpretation" maxlength="1"/> </span>
			</div>
		</div>
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Doctor Diagnosis  </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:radiobutton path="DOCTOR_DIAGNOSIS"  value = "Y" /> Yes <form:radiobutton path="DOCTOR_DIAGNOSIS"  value = "N" /> No </span>
			</div>
		</div>
		
		
		
		
		
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Doctor Diagnosis Date </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="DOCTOR_DIAGNOSIS_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Doctor Diagnosis Date"   maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error DOCTOR_DIAGNOSIS_DATE_TEXT "></div>
		</div>
		
		
		
		
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Candidiasis, bronchi, trachea, or lungs </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="CANDLUNG_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Candlung Date"  maxlength="10"/> </span>
				
			</div>
			<div class="pL10 v_error CANDLUNG_DATE_TEXT "></div>
		</div>
		
		
		
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Candidiasis, esophageal </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="CANDE_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Candidiasis Date"  maxlength="10"/> </span>
				
			</div>
			<div class="pL10 v_error CANDE_DATE_TEXT "></div>
		</div>
		
		
		
		
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Carcinoma, invasive cervical </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="CERVCANC_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate"  title="Carcinoma Date"  maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error CERVCANC_DATE_TEXT "></div>
		</div>
		
		
		
		
		
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Coccidioidomycosis, disseminated or extrapulmonary </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="COCCI_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate"  title="Coccidioidomycosis Date"  maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error COCCI_DATE_TEXT "></div>
		</div>
		
		
		
		
		
		
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Cryptococcosis, extrapulmonary </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="CRYPTOCO_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Cryptococcosis Date"  maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error CRYPTOCO_DATE_TEXT "></div>
		</div>
		
		
		
		
		
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Cryptosporidiosis, chronic intestinal (>1mo. duration) </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="CRYPTOSP_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Cryptosporidiosis Date"  maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error CRYPTOSP_DATE_TEXT "></div>
		</div>
		
		
		
		
		
		
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Cytomegalovirus disease (other than inliver, spleen, or nodes) </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="CMV_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate"  title="Cytomegalovirus disease Date"  maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error CMV_DATE_TEXT "></div>
		</div>
		
		
		
		
		
		
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Cytomegalovirus retinitis (with loss of vision) </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="CMVRET_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Cytomegalovirus retinitis Date"   maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error CMVRET_DATE_TEXT "></div>
		</div>
		
		
		
		
		
		
		
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> HIV encephalopathy </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="HIVNCEPH_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="HIV encephalopathy Date"   maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error HIVNCEPH_DATE_TEXT "></div>
		</div>
		
		
		
		
		
		
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Herpes simplex: chronic ulcer(s) (>1mo. duration) or bronchitis, pneumonitis or esophagitis </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="HERPES_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Herpes Date"   maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error HERPES_DATE_TEXT "></div>
		</div>
		
		
		
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Histoplasmosis, disseminated or extrapulmonary </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="HISTO_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Histoplasmosis Date"  maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error HISTO_DATE_TEXT "></div>
		</div>
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Isosporiasis, chronic intestinal (>1 mo. duration) </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="ISO_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Isosporiasis Date"  maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error ISO_DATE_TEXT "></div>
		</div>
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Kaposi's sarcoma </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="KS_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Kaposi's Date"   maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error KS_DATE_TEXT "></div>
		</div>
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Lymphoma, Burkitts (or equivalent term) </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="BURKL_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Burkitts Date"   maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error BURKL_DATE_TEXT "></div>
		</div>
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Lymphoma, immunoblastic (or equivalent term) </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="LYMPHIM_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Lymphoma Date"   maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error LYMPHIM_DATE_TEXT "></div>
		</div>
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Lymphoma, primary in brain </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="LYMPHB_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Lymphoma Date"  maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error LYMPHB_DATE_TEXT "></div>
		</div>
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Mycobacterium avium complex or M.kansaslii,dissemina ted or extrapulmonary </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="MYCOAV_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Mycobacterium Date"   maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error MYCOAV_DATE_TEXT "></div>
		</div>
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> M. tuberculosis, pulmonary </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="MYCOPULM_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Tuberculosis, pulmonary Date"  maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error MYCOPULM_DATE_TEXT "></div>
		</div>
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> M. tuberculosis, disseminated or extrapulmonary </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="MYCODISM_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Tuberculosis, disseminated Date"  maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error MYCODISM_DATE_TEXT "></div>
		</div>
		
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Mycobacterium, of other species or unidentified species, disseminated or extrapulmonary </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="MYCOTH_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Mycobacterium Date"  maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error MYCOTH_DATE_TEXT "></div>
		</div>
		
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Pneumocystis carinii pneumonia </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="PCP_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Pneumocystis Date"  maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error PCP_DATE_TEXT "></div>
		</div>
		
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Pneumonia, recurrent, in 12 mo. Period </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="PNEUM_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Pneumonia Date"   maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error PNEUM_DATE_TEXT "></div>
		</div>
		
		
		
        <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Progressive multifocal leukoencephalopathy </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="PML_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="PML Date"  maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error PML_DATE_TEXT "></div>
		</div>
		
		
		<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Salmonella septicemia, recurrent </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="SALMNELA_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate"  title="Salmonella Date"  maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error SALMNELA_DATE_TEXT "></div>
		</div>
		
		
		<div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Toxoplasmosis of brain </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="TOXO_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Toxoplasmosis of brain Date"  maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error TOXO_DATE_TEXT "></div>
		</div>
		
		
		     <div class="riskDiv mL5 mR5 mT2"> 
			<div class="riskLabel"> 
				<span class="bold pL10"> Wasting syndrome due to HIV	 </span>
			</div>
			<div  class="riskValue">
				<span class="mL5"><form:input path="WASTING_DATE_TEXT"  class="standardInputSmallText mask dPicker" datevalidations="partialDate" title="Wasting syndrome Date"   maxlength="10"/> </span>
			</div>
			<div class="pL10 v_error WASTING_DATE_TEXT "></div>
		</div>
        
   </div>
    </div>     

        
    <input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
</form:form>




<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
		 <div id="button-row" class="button-row documentButtons center">
		 					<c:if test="${StdNYCLabDocVO.STATENO != null && StdNYCLabDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
									<c:if test="${userPermissions['90'] != null}" >
										<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
									</c:if>
									<c:if test="${userPermissions['100'] != null}" >
										<input type="button" id="editDocument"  class="editDocumentBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="confirmEditDocumentFromStateno()" />
									</c:if>
							</c:if>

						
							<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Save" onclick="saveForm()"/>	 
							<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />
									
		</div>