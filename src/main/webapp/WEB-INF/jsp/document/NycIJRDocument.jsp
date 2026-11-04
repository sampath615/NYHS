<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link href="${staticPath}/css/docs/commonForm.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/docs/nycijrDoc.css?v=${buildDate}" rel="stylesheet" type="text/css" />

<script src="${staticPath}/js/docs/docViewers.js?v=${buildDate}"></script>

<style>
		/*
	 * @author: gxl13
	 * This css file is used for NYC_IJR Document Look and Feel.
	 */
	.pTRBL10{
	    padding: 10px 10px 0px 10px;
	}
	
	.b1{
	    border-style: dotted;
	    border-width: 1px;
	    margin: 5px;
	}
	
	.fieldHolder20{
	    float: left;
	    width: 20%;
	}
	
	.birthCountyHWIJR{
		height:90px;
	    width:266px;
	}
	
	.ethnicityHWIJR{
		height:90px;
	    width:266px;
	}
	
	.riskHWIJR{
	    height:400px;
	    width:412px;
	    float:left;
	}
	
	.reportHWIJR{
	    height:390px;
	    width:429px;
	    float:right;
	}
	
	.HIVIJR{
	    height:106px;
	    width:429px;
	    float:right;
	}
	
	.pL10{
		padding-left:10px;
	}
	
	#stdNYCIJRDocVO .labelunderneath2{
		padding-left:120px;
	}
	
	#stdNYCIJRDocVO .labelunderneath1{
		padding-left:175px;
	}
	
	#stdNYCIJRDocVO .labelunderneath{
		padding-left:135px;
	}
	
	#stdNYCIJRDocVO .labelunderneath3{
		padding-left:160px;
	}
	#stdNYCIJRDocVO .labelunderneath5{
		padding-left:245px;
	}
	 
	 #stdNYCIJRDocVO .labelunderneath6{
	 	padding-left:205px;
	 }
	 
	.mR50{
		margin-right:50px;
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
	
	.riskLabelFieldLeft{
		float: left;
	    width: 20%;
	}
	
	.riskLabelFieldRight{
		float: left;
	    width: 13%;
	}
	.raceHW {
	    height: 165px;
	    width: 266px;
	}
	#patientInformation{ padding: 0}

</style>


<form:form modelAttribute="stdNYCIJRDocVO" id="stdNYCIJRDocVO" cssClass="documentDescription documentFormVO">
<form:errors path="*" cssClass="errorblock" element="div" />

<div id="labDocTab">
   <div style="padding-left:43%; " class="headingCls">
                NYCIJR DOCUMENT
        </div>
        
        
        
       <div class="pB30 pT20">
			<c:if test="${stdNYCIJRDocVO.parentDocumentId != null &&  stdNYCIJRDocVO.parentDocumentId != 0}">

				<div  class="floatLeft width70Perc" id="parentDocumentIdSection">
		            <span class="parentIdSectionLabel bold">Parent DocumentId: </span>
					<span class="parentIdSectionValue"><a href="#" onclick="loadDocumentDlg(${stdNYCIJRDocVO.parentDocumentId}, '${param.documentType}')">${stdNYCIJRDocVO.parentDocumentId}</a></span>
					
		         </div>	
	         </c:if>
	         
			<div  class="floatLeft">
	            <span class="mL10 bold">Document ID: </span>
				<span class=""><form:input id="DOCUMENT_ID"  path="stdNycijrDoc.documentId" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
	         </div>	

		</div>
		
	
    <div class="container pB10 w100 ">
            <div style="float:left; width: 40%">
	            <div>
	                <span class="ml10"> <spring:message code="label.stateNo" />: </span>
	                <span class="ml10" style=""><form:input path="stateNo" class="standardInputSmallText readonly"/> </span>
	            </div>    
            </div>
            <div style="float:left; width: 30%">
 	            <div>
 	                <span class=" ml10">	eHARS Import Date: </span>
 			 		<span class="ml10">
 			 			<fmt:formatDate value="${stdNYCIJRDocVO.stdNycijrDoc.eharsImportDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
						<form:input id="EHARS_IMPORT_DATE"  path="stdNycijrDoc.eharsImportDate" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/>
 			 		</span>
 	           </div>                  
            </div>
            <div style="float:left; width: 30%; text-align:right">
 	         	<div >
 	                <span class="mL10">NYC SHIP Date: </span>
 	                <fmt:formatDate value="${stdNYCIJRDocVO.stdNycijrDoc.nycShipDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedNYC_SHIP_DATE_TEXT" ></fmt:formatDate>
					<span class="ml10 "><form:input id="NYC_SHIP_DATE"  path="stdNycijrDoc.nycShipDate" value="${theFormattedNYC_SHIP_DATE_TEXT}" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
 	            </div> 
 	         </div>     
    </div>
    
    <jsp:include page="statenoDetails.jsp"></jsp:include>

    <div class="container" id="patientInformation">  
        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
            <span class=" mL2">Patient Information</span>
        </div>
        
        
        
        <div class="personInfo pT10">
          			<div class="fieldHolder25">
                       <span class="pL10">First Name </span> 
                       <span class="pL5"><form:input path="stdNycijrDoc.fname" id="NYC_IJR_FNAME" class="standardInputMedText required" title="First Name" maxLength="25"/></span>
                       <div class="pL10 v_error NYC_IJR_FNAME "></div> 
                   </div>
                   <div class="fieldHolder25">
                      	<span class="pL10">Middle Name </span> 
                      	<span  class="pL5"><form:input path="stdNycijrDoc.mname" id="NYC_IJR_MNAME" class="standardInputMedText"  title="Middle Name" maxLength="25"/></span>
                      	<div class="pL10 v_error NYC_IJR_MNAME "></div>
                      </div>
                   <div class="fieldHolder25">
                      	<span class="pL10">Last Name </span> 
                      	<span  class="pL5"><form:input path="stdNycijrDoc.lname" id="NYC_IJR_LNAME" class="standardInputMedText required" title="Last Name" maxLength="25"/></span>
                      	<div class="pL10 v_error NYC_IJR_LNAME "></div>
                      </div>
                   <div class="fieldHolder25">
                			<span class="pL10">Alt Last Name </span> 
            				<span class="pL5"><form:input path="stdNycijrDoc.altLname" id="NYC_IJR_ALT_LNAME" class="standardInputMedText" title="Alt Name" maxLength="25"/></span>
            				<div class="pL10 v_error NYC_IJR_ALT_LNAME "></div>
            			</div>  
                  <br><br>
                  
                  
          </div>
        <div class="clearBoth personInfo pB20 marginBottom20">
                               			<div class="fieldHolder25">
                                       		<span class="pL10">Date of Birth </span>
                                       		<span  class="pL10"><form:input path="stdNycijrDoc.dobText" id="DATE_OF_BIRTH" class="dPicker mask standardInputSmallText" title="Date of Birth" maxLength="10" datevalidations="completeDate"/></span>
                                       		<div class="pL10 v_error DATE_OF_BIRTH "></div>
                                       </div>
                                </div>
        
        
        
        <div class="b1" id="nycIjrDocumentPersonDtls">
	        <div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
	           <span class=" mL2">Current Address</span>                  
	       </div>
            <div class="personInfo pTRBL10">
                <span> 
                    <span class="pL10"><form:input id="CUR_PERSON_ADDRESS"  path="stdNycijrDoc.curPersonAddress" title="Current Address" class="standardInputMedText  newAddress widgetAddressFieldsReadonly" maxlength="65"/></span>
                    
                    
                    <span class="pL10">City</span>
                    <span class="pL10"><form:input id="CUR_PERSON_CITY"  path="stdNycijrDoc.curPersonCity" title="Current City"  class="oAdrDlg standardInputSmallText newCity widgetAddressFieldsReadonly" maxlength="35"/></span>
                    
                    <span class="pL10">State </span>
                    <span  class="pL10"><form:input id="CUR_PERSON_STATE"  path="stdNycijrDoc.curPersonState" title="Current State"  class="oAdrDlg standardInputSmallText newState widgetAddressFieldsReadonly" style="width:50px" maxlength="2"/></span>
                    
                    <span class="pL10">Zip </span>
                    <span class="pL10"> 
                        <form:input id="CUR_PERSON_ZIP"  path="stdNycijrDoc.curPersonZip" title="Current Zip"  class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5" />
                    </span>
                    
					<span class="pL10">County </span>
                    	<span class="pL10">  
                    		<form:select id="CUR_PERSON_COUNTY"  path="stdNycijrDoc.curPersonCounty" title="Current Count" cssClass="oAdrDlg roundedBrdr newCounty widgetAddressFieldsReadonly"> 
									<form:option value=""></form:option>
									<form:options items="${sessionScope.countyMap}"/>
							</form:select>
                    </span> 
                    
                    <span class="pL10">County Name </span>
                    <span class="pL10"> 
                        <form:input id="CUR_PERSON_COUNTY_NAME"  path="stdNycijrDoc.curPersonCountyName" title="Current County Name" class="oAdrDlg standardInputSmallText" maxlength="15"/>
                    </span>
                    <br>
                    <span class="pL50"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
                    
                </span>
            </div>
            <div class="personInfo pT10 pTRBL10">
                    <span class="pL10" ><form:input path="rawAddress"  class="standardInputMedText   readonly" /></span>
                    
	                <span class="pL30">Cell Phone</span>
	                <span class="pL10"><form:input id="PERSON_CELLPHONE"  path="stdNycijrDoc.personCellphone" title="Cell Phone" style="width:115px" class="pNumber standardInputSmallText" maxlength="15"/></span>
	                
	                <span class="pL30">Home Phone </span>
	                <span class="pL10"><form:input id="CUR_PERSON_PHONE"  path="stdNycijrDoc.curPersonPhone" title="Current Phone" style="width:115px" class="pNumber standardInputSmallText" maxlength="15"/></span>
	                
	                <span class="pL30">Work Phone</span>
	                <span class="pL10"><form:input id="PERSON_WORKPHONE"  path="stdNycijrDoc.personWorkphone"  title="Work Phone" style="width:115px" class="pNumber standardInputSmallText" maxlength="15"/></span>
	                
	                 <span style="float: right;" class="mr50">
							<a href="javascript:openAddressWidget('nycIjrDocumentPersonDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
					</span> 
	                <br>
	                <span class="pL50"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
		            
            </div><br>
        </div>

        <div class="b1" id="nycIjrDocumentDiagnosisAddressDtls">
        	<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
	           <span class=" mL2">Diagnosis Address</span>                  
	       </div>
            <div class="personInfo pTRBL10">
                <span> 
                    <span class="pL10"><form:input id="DX_PERSON_ADDRESS"  path="stdNycijrDoc.dxPersonAddress" title="Diagnosis Address"  class="standardInputText  newAddress widgetAddressFieldsReadonly" style="width:330px" /></span>
                    
                    
                    <span class="pL20">City</span>
                    <span class="pL10"><form:input id="DX_PERSON_CITY"  path="stdNycijrDoc.dxPersonCity" title="Diagnosis City"  class="oAdrDlg standardInputSmallText newCity widgetAddressFieldsReadonly"  maxlength="35"/></span>
                    
                    <span class="pL20">State </span>
                    <span  class="pL10"><form:input id="DX_PERSON_STATE"  path="stdNycijrDoc.dxPersonState" title="Diagnosis State"  class="oAdrDlg standardInputSmallText newState widgetAddressFieldsReadonly" style="width:50px" maxlength="2"/></span>
                    
                    <span class="pL20">Zip </span>
                    <span class="pL10"> 
                        <form:input id="DX_PERSON_ZIP"  path="stdNycijrDoc.dxPersonZip" title="Diagnosis Zipcode"  class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5"/>
                    </span>
                    <span class="pL20">County </span>
                    <span class="pL10"> 
                    	<form:select id="DX_PERSON_COUNTY"  path="stdNycijrDoc.dxPersonCounty" title="Diagnosis County"  cssClass="oAdrDlg roundedBrdr newCounty widgetAddressFieldsReadonly"> 
									<form:option value=""></form:option>
									<form:options items="${sessionScope.countyMap}"/>
							</form:select>
                    </span>
                    <br>
                    <span class="labelunderneath4"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
                </span>
            </div>
            <div class="personInfo pT10 pTRBL10">
            	<span  class="pL10" ><form:input path="rawDxAddress" class="standardInputText readonly" style="width:330px"/></span>
                    
                <span class="pL30">Home Phone</span>
                <span class="pL10"><form:input id="DX_PERSON_TELEPHONE"  path="stdNycijrDoc.dxPersonTelephone" title="Diagnosis Home Phone"  class="pNumber standardInputSmallText" maxlength="15"/>&nbsp;&nbsp;</span>
                
                <span class="pL30">Alternate Phone</span>
                <span class="pL10"><form:input id="RSH_PHONE"  path="stdNycijrDoc.rshPhone" title="Alternate Phone"  class="pNumber standardInputSmallText" maxlength="15"/>&nbsp;&nbsp;</span>
                 
                 <span style="float: right;" class="mr50">
					<a href="javascript:openAddressWidget('nycIjrDocumentDiagnosisAddressDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
				</span> 
                <br>               
                <span class="labelunderneath4"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
                    
            </div><br>
        </div>
        
        <!--  Race details -->
        <div class="raceRiskReprt">
            <div class="fieldHolder25">
                <div class="container borderField pT10 raceHW">
                    <div class="bold">
                        Race
                    </div>
                    <div>
                        <form:checkbox id="RACE_WHITE1"  path="stdNycijrDoc.raceWhite"  value="1" /> White 
                    </div>
                    <div>
                        <form:checkbox id="RACE_BLACK1"  path="stdNycijrDoc.raceBlack"  value="1" /> African American
                    </div>
                    <div>
                        <form:checkbox id="RACE_ASIAN1"  path="stdNycijrDoc.raceAsian"  value="1" /> Asian/Pacific Islander
                    </div>
                    <div>
                        <form:checkbox id="RACE_INDIAN1"  path="stdNycijrDoc.raceIndian"  value="1" /> American Indian/Alaskan Native
                    </div>
                    <div>
                        <form:checkbox id="RACE_PACIFIC1"  path="stdNycijrDoc.racePacific"  value="1" /> Pacific
                    </div>
                    <div>
                        <form:checkbox id="RACE_MULTIPLE1"  path="stdNycijrDoc.raceMultiple"  value="1" /> Multiple
                    </div>
                    <div>
                        <form:checkbox id="RACE_OTHER1"  path="stdNycijrDoc.raceOther"  value="1" /> Other 
                    </div>
                    <div>
                        <form:checkbox id="RACE_UNKNOWN1"  path="stdNycijrDoc.raceUnknown"  value="1" /> Unknown
                    </div>           
                </div>  
                <br>
                <div class="borderField pT10 ethnicityHWIJR">   
                    <div>
                        <span class="bold">Ethnicity (Check one) </br> Hispanic or Latino<br></span>
                        <form:radiobutton id="ETHNICITY1"  path="stdNycijrDoc.ethnicity"  value="01" /> Yes<br>
                        <form:radiobutton id="ETHNICITY2"  path="stdNycijrDoc.ethnicity"  value="02" /> No<br>
                        <form:radiobutton id="ETHNICITY3"  path="stdNycijrDoc.ethnicity"  value="09" /> Unknown
                    </div>
                </div>  
                
                <div class="borderField pT10 birthCountyHWIJR">
                	<div class="floatLeft pR10" style="border-right: dotted 1px;">
						<div class=""> Current Gender </div>
						<div  class="">
							<div> <form:radiobutton id="CURRENT_GENDER1"  path="stdNycijrDoc.currentGender" value="M" /> Male </div>
							<div> <form:radiobutton id="CURRENT_GENDER2"  path="stdNycijrDoc.currentGender" value="F" /> Female </div>
							<div> <form:radiobutton id="CURRENT_GENDER3"  path="stdNycijrDoc.currentGender" value="U" />Unknown</div>		
						</div>
					</div>
					<div class="floatLeft pL10">
						<div class=""> Transgender </div>
						<div  class="">
							<div> <form:radiobutton id="CURRENT_GENDER4"  path="stdNycijrDoc.currentGender" value="X" /> MTF </div>		
							<div> <form:radiobutton id="CURRENT_GENDER5"  path="stdNycijrDoc.currentGender" value="Y" /> FTM </div>		
							<div> <form:radiobutton id="CURRENT_GENDER6"  path="stdNycijrDoc.currentGender" value="T" /> Unspecified </div>														
						</div>
					</div>   
                </div>  
            </div>
            <div class="fieldHolder75">
                <div class="riskHWIJR borderField" style="padding: 10px 0 0 0">
                	<div class="pL20">
	            		<span class="fL">SSN</span>
	               		<span class="fR mR50"><form:input path="stdNycijrDoc.ssn" id="NYC_IJR_SSN"  title="SSN" class="pNumber standardInputMedText" maxlength="9"/></span>
	               		<div class="fL v_error NYC_IJR_SSN "></div>
	            	</div>
	            	<div class="clearBoth pL20 pT10">
	            		<span class="fL">NYC Sent stateno</span>
	               		<span class="fR mR50"><form:input id="NYC_SENT_STATENO"  path="stdNycijrDoc.nycSentStateno"  title="NYC Sent stateno" class="standardInputMedText readonly" maxlength="10"/></span>
	            	</div>
	            	<div class="clearBoth pT10 pL20">
	            		<span class="fL">Patient Record Number</span>
	               		<span class="fR mR50"><form:input id="MED_RECORD_NUMBER"  path="stdNycijrDoc.medRecordNumber" title="Patient Record Number"  class="standardInputMedText" maxlength="20"/></span>
	            	</div>
	            	<div class="clearBoth pT10 pL20">
	            		<span class="fL">NYSID</span>
	               		<span class="fR mR50"><form:input path="stdNycijrDoc.nysid" id="NYC_IJR_NYSID" title="NYSID"  class="standardInputMedText" maxlength="9" /></span>
	               		<div class="fL v_error NYC_IJR_NYSID "></div>
	            	</div>
	            	<div class="clearBoth pT10 pL20">
	            		<span class="fL">DIN</span>
	               		<span class="fR mR50"><form:input path="stdNycijrDoc.din" id="NYC_IJR_DIN" title="DIN" class="standardInputMedText" maxlength="7"/></span>
	               		<div class="fL v_error NYC_IJR_DIN "></div>
	            	</div>
	            	
	            	<div class="clearBoth pT10 pL20">
	            		<span class="fL">AGE</span>
	               		<span class="fR mR50"><form:input id="AGE"  path="stdNycijrDoc.age"  title="AGE" class="standardInputMedText nOnly" maxlength="3"/></span>
	            	</div>
	            	
	            	<div class="clearBoth pT10 pL20">
	            		<span class="fL">File Date</span>
	            		<fmt:formatDate value="${stdNYCIJRDocVO.stdNycijrDoc.fileDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedFileDate" />
	            		<span class="fR mR50"><form:input path="stdNycijrDoc.fileDate" id="NYC_IJR_FILE_DATE" title="File Date" value="${theFormattedFileDate}"  class="mask standardInputMedText readonly" datevalidations="completeDate" maxlength="10"/></span>
	            		<div class="v_error NYC_IJR_FILE_DATE "></div>
	            	</div>
	            	
	            	<div class="clearBoth pT10 pL20">
	            		<span class="fL">Create Date</span>
	            		<fmt:formatDate value="${stdNYCIJRDocVO.stdNycijrDoc.createDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedCreateDate" />
	               		<span class="fR mR50"><form:input id="CREATE_DATE"  path="stdNycijrDoc.createDate" title="Create Date" value="${theFormattedCreateDate}"   class="standardInputMedText readonly" maxlength="10"/></span>
	            	</div>
	            	
	            	<div class="clearBoth pT10 pL20 displayNone" id="createUidField">
	            		<span class="fL">CREATE_UID</span>
	               		<span class="fR mR50"><form:input id="CREATE_UID"  path="stdNycijrDoc.createUid" title="Create UID"  class="standardInputMedText readonly" maxlength="10"/></span>
	            	</div>
	            	
	            	
	            	
	           		<div class="clearBoth mT10 pL20 borderField" style="width:391px">
	           		<br>
	                    <span class="bold">Country of birth</span><br>
	                    <form:radiobutton id="BIRTH_COUNTRY1"  path="stdNycijrDoc.birthCountry" value="US" /> US <br>
	                    <form:radiobutton id="BIRTH_COUNTRY2"  path="stdNycijrDoc.birthCountry" value="US DEPENDENCY" /> US dependency <br>
	                    <form:radiobutton id="BIRTH_COUNTRY3"  path="stdNycijrDoc.birthCountry" value="OTHER" /> Other  <br>
	                    <form:radiobutton id="BIRTH_COUNTRY4"  path="stdNycijrDoc.birthCountry" value="UNK" /> Unknown
	                </div>   
                </div>
            
                <div class="reportHWIJR borderField pT10">
             	   <!--  Risk details -->
                	 <div class="pT8 bold">
                        <span class="riskLabelField">Risk Details</span>
                        <span class="riskValueField">Yes</span>
                        <span class="riskValueField">No</span>
                        <span class="riskValueField">Unknown</span><br>
                    </div>
                    <div class="pT8">
                        <span class="riskLabelField">Sex With Male</span>
                        <span class="riskValueField"><form:radiobutton id="SWM1"  path="stdNycijrDoc.swm"  value="Y" /></span>
                        <span class="riskValueField"><form:radiobutton id="SWM2"  path="stdNycijrDoc.swm"  value="N" /></span>
                        <span class="riskValueField"><form:radiobutton id="SWM3"  path="stdNycijrDoc.swm"  value="U" /></span><br>        
                    </div>
                    <div class="pT8">;
                        <span class="riskLabelField">Sex With Female</span>
                        <span class="riskValueField"><form:radiobutton id="SWF1"  path="stdNycijrDoc.swf"  value="Y" /></span>
                        <span class="riskValueField"><form:radiobutton id="SWF2"  path="stdNycijrDoc.swf"  value="N" /></span>
                        <span class="riskValueField"><form:radiobutton id="SWF3"  path="stdNycijrDoc.swf"  value="U" /></span>
                    </div>
                    <div class="pT8 pB5">
                        <span class="riskLabelField pT8 pB5">Injection Drug Use </span>
                        <span class="riskValueField pT8 pB5"><form:radiobutton id="IDU1"  path="stdNycijrDoc.idu"  value="Y" /></span>
                        <span class="riskValueField pT8 pB5"><form:radiobutton id="IDU2"  path="stdNycijrDoc.idu"  value="N" /></span>
                        <span class="riskValueField pT8 pB5"><form:radiobutton id="IDU3"  path="stdNycijrDoc.idu"  value="U" /></span> 
                    </div>
                    <div class="pT10"> <u>Heterosexual sex with:</u> </div>
                    <ul>
                        <li class="">                            
                            <span class="riskLabelField1">Injection Drug User </span>
                            <span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_IDU1"  path="stdNycijrDoc.sexPartnerIdu"  value="Y" /></span>
                            <span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_IDU2"  path="stdNycijrDoc.sexPartnerIdu"  value="N" /></span>
                            <span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_IDU3"  path="stdNycijrDoc.sexPartnerIdu"  value="U" /></span>                            
                        </li>
                        <li class="pT8">                            
                            <span class="riskLabelField1">Bisexual man </span>
                            <span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_MSM1"  path="stdNycijrDoc.sexPartnerMsm"  value="Y" /></span>
                            <span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_MSM2"  path="stdNycijrDoc.sexPartnerMsm"  value="N" /></span>  
                            <span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_MSM3"  path="stdNycijrDoc.sexPartnerMsm"  value="U" /></span>                            
                        </li>
                        <li class="pT8">                            
                            <span class="riskLabelField1">HIV+ person </span>
                            <span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_HIV1"  path="stdNycijrDoc.sexPartnerHiv"  value="Y" /></span>
                            <span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_HIV2"  path="stdNycijrDoc.sexPartnerHiv"  value="N" /></span>  
                            <span class="riskValueField1"><form:radiobutton id="SEX_PARTNER_HIV3"  path="stdNycijrDoc.sexPartnerHiv"  value="U" /></span>                            
                        </li>
                    </ul>
                    <div class="pT8 pB5">
                        <span class="riskLabelField pB5">Perinatal Exposure </span>
                        <span class="riskValueField pB5"><form:radiobutton id="PERINATAL1"  path="stdNycijrDoc.perinatal"  value="Y" /></span>
                        <span class="riskValueField pB5"><form:radiobutton id="PERINATAL2"  path="stdNycijrDoc.perinatal"  value="N" /></span>   
                        <span class="riskValueField pB5"><form:radiobutton id="PERINATAL3"  path="stdNycijrDoc.perinatal"  value="U" /></span>        
                    </div>  
                    <div class="pT8">
                        <span class="riskLabelField">Received Blood Products </span>
                        <span class="riskValueField"><form:radiobutton id="RECV_BLOOD_PROD1"  path="stdNycijrDoc.recvBloodProd"  value="Y" class="otherFieldDependentRadio" dependentField="NYC_IJR_RECV_BLOOD_PROD_DATE_TEXT,NYC_IJR_RECV_BLOOD_PROD_COUNTRY"/></span>
                        <span class="riskValueField"><form:radiobutton id="RECV_BLOOD_PROD2"  path="stdNycijrDoc.recvBloodProd"  value="N" class="otherFieldDependentRadio" dependentField="NYC_IJR_RECV_BLOOD_PROD_DATE_TEXT,NYC_IJR_RECV_BLOOD_PROD_COUNTRY" /></span>    
                        <span class="riskValueField"><form:radiobutton id="RECV_BLOOD_PROD3"  path="stdNycijrDoc.recvBloodProd"  value="U" class="otherFieldDependentRadio" dependentField="NYC_IJR_RECV_BLOOD_PROD_DATE_TEXT,NYC_IJR_RECV_BLOOD_PROD_COUNTRY" /></span>     
                    </div><br>
                    <ul><br>
                        <li class="">
                            <span class="riskLabelField2">Date </span>
                            <span class="riskValueField2"><form:input path="stdNycijrDoc.recvBloodProdDateText" id="NYC_IJR_RECV_BLOOD_PROD_DATE_TEXT" requiredField="Y"  title="Received Blood Product Date" class="dPicker mask standardInputSmallText" datevalidations="partialDate"/></span> <br>
                            <div class="v_error NYC_IJR_RECV_BLOOD_PROD_DATE_TEXT "></div>
                        </li>
                        <li class="pT8">
                            <span class="riskLabelField2">Country </span>
                            <span class="riskValueField2"><form:input path="stdNycijrDoc.recvBloodProdCountry" id="NYC_IJR_RECV_BLOOD_PROD_COUNTRY" title="Received Blood Product Country"  class="standardInputSmallText" /></span>
                        </li>   
                    </ul>   
                    <div>
                        <span class="riskLabelField3">Other Risk </span>
                        <span class="riskValueField"><form:radiobutton id="RISK_OTHER1"  path="stdNycijrDoc.riskOther"  value="Y" class="otherFieldDependentRadio" dependentField="NYC_IJR_RISK_OTHER_TEXT"/></span>
                        <span class="riskValueField"><form:radiobutton id="RISK_OTHER2"  path="stdNycijrDoc.riskOther"  value="N" class="otherFieldDependentRadio" dependentField="NYC_IJR_RISK_OTHER_TEXT" /></span>    
                        <span class="riskValueField"><form:radiobutton id="RISK_OTHER3"  path="stdNycijrDoc.riskOther"  value="U" class="otherFieldDependentRadio" dependentField="NYC_IJR_RISK_OTHER_TEXT" /></span>                        
                    </div>  
                    <br>
                    <ul style="">
                        <li class="">
                            <span class="riskLabelField2">Other Risk Specified </span>
                            <span class="riskValueField2"><form:input path="stdNycijrDoc.riskOtherText" id="NYC_IJR_RISK_OTHER_TEXT" requiredField="Y"  title="Risk Other Text" class="standardInputSmallText" maxlength="20"/></span>
                        </li>   
                    </ul>
                     <div>
                             <span class="riskLabelField3">Unknown </span>
                             <span class="riskValueField"><form:radiobutton id="RISK_UNKNOWN1"  path="stdNycijrDoc.riskUnknown" value="Y" /></span>
                             <span class="riskValueField"><form:radiobutton id="RISK_UNKNOWN2"  path="stdNycijrDoc.riskUnknown" value="N" /></span>    
                             <span class="riskValueField"><form:radiobutton id="RISK_UNKNOWN3"  path="stdNycijrDoc.riskUnknown" value="U" /></span>
                    </div>       
                </div> 
            </div> 
        </div>     
    </div>  
    <div id="personHIVDetails" class="container">   
       <div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
           <span class=" mL2">Reported NYC Info</span>                  
       </div>
       <div class="personInfo pT10 pTRBL10">
       		<div class="fieldHolder25">
	           <span class="fL"> HIV Dx Date </span>
	           <span class="pL10"> 
	               <form:input path="stdNycijrDoc.hivDxDateText" id="NYC_IJR_HIV_DX_DATE_TEXT" title="HIV Diagnosis Date" class="standardInputMedText dPicker mask" datevalidations="partialDate" maxlength="10"/>
	           </span>
           		<div class="v_error NYC_IJR_HIV_DX_DATE_TEXT "></div>
           	</div>
           	<div class="fieldHolder33">
           		<span class="pL30"> AIDS Dx Date</span>
       			<span class="pL10"><form:input path="stdNycijrDoc.aidsDxDateText" id="NYC_IJR_AIDS_DX_DATE_TEXT" title="AIDS Diagnosis Date" class="dPicker mask standardInputMedText" datevalidations="partialDate" maxlength="10"/></span>
       			
       			<div class="pL30 v_error NYC_IJR_AIDS_DX_DATE_TEXT "></div>
       		</div>
       		<div class="fieldHolder33">
       			<span class="pL30"> HIV Facility name</span>
       			<span class="pL10"><form:input id="HIV_FACILITY_NAME"  path="stdNycijrDoc.hivFacilityName"  title="HIV Facility Name" class="standardInputMedText" maxlength="30"/></span>
       		</div>
       		
               
       </div> 
   </div>
   <div id="personHIVDetails" class="container">   
       <div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
           <span class=" mL2">Reported by NYC Provider</span>                  
       </div>
       <div class="personInfo pT10 pTRBL10">
       
       	
       		 <div class="fieldHolder49">
	       		 <span class="fL">Date of Admission</span>
	             <span style="padding-left:43px"><form:input path="stdNycijrDoc.admissionDateText" id="NYC_IJR_ADMISSION_DATE_TEXT" title="Admission Date"  class="dPicker mask standardInputMedText" datevalidations="partialDate" maxlength="10"/></span>
	             
	             <div class="v_error NYC_IJR_ADMISSION_DATE_TEXT "></div> 
	          </div>   
	          <div class="fieldHolder49">
	             <span>PS Requested <form:checkbox id="PS_REQUESTED1"  path="stdNycijrDoc.psRequested" class="pL10" style="margin-left:19px" value="1" /> </span> 
	             
	             <span class="pL30">Number of Partners</span>
	             <span class="pL10"><form:input id="NUMBEROFPARTNERS"  path="numberOfPartners" title="Number of Partners" class="standardInputMedText nOnly" maxlength="2"/></span> 
	          </div>   
             <br><br>
             
             <div class="clearBoth  fieldHolder49">
	             <span class="fL">Initial HIV Dx <form:checkbox id="INIT_HIV_DIAG1"  style="margin-left:19px" path="stdNycijrDoc.initHivDiag"   class="pL10 otherFieldDependentCBox"  dependentField="NYC_IJR_INIT_HIV_DIAG_DATE_TEXT" value="1" />  Date</span>
	             <span class="pL10"><form:input path="stdNycijrDoc.initHivDiagDateText" id="NYC_IJR_INIT_HIV_DIAG_DATE_TEXT" requiredField="Y"  title="Initial HIV Diag Date"  class="dPicker mask standardInputMedText" datevalidations="partialDate"  maxlength="10"/></span>
	             <div class="v_error NYC_IJR_INIT_HIV_DIAG_DATE_TEXT "></div>
	         </div>
	         <div class="fieldHolder49">    
             	<span>Informed of Dx <form:checkbox id="INFORMED_HIV_POS1"  style="margin-left:19px"   class="pL10 otherFieldDependentCBox"  dependentField="NYC_IJR_INFORMED_HIV_DATE_TEXT"  path="stdNycijrDoc.informedHivPos" value="1" />  Date</span>
             	<span class="pL10"><form:input path="stdNycijrDoc.informedHivDateText" title="Informed HIV Diag Date" id="NYC_IJR_INFORMED_HIV_DATE_TEXT" requiredField="Y"   class="dPicker mask standardInputMedText" datevalidations="partialDate"  maxlength="10"/></span>
             	<div class="v_error NYC_IJR_INFORMED_HIV_DATE_TEXT "></div>
             </div> 
             
             <br><br>
             <div class="clearBoth  fieldHolder49">
             	<span class="fL">Initial AIDS Dx <form:checkbox id="INIT_AIDS_DIAG1"  style="margin-left:10px" path="stdNycijrDoc.initAidsDiag"   class="pL10 otherFieldDependentCBox"  dependentField="NYC_IJR_INIT_AIDS_DIAG"  value="1" />  Date</span>
             	<span class="pL10"><form:input path="stdNycijrDoc.initAidsDiagDateText" id="NYC_IJR_INIT_AIDS_DIAG" requiredField="Y"   title="Initial AIDS Diag Date"  class="dPicker mask standardInputMedText" datevalidations="partialDate"  maxlength="10"/></span>
             	<div class="v_error NYC_IJR_INIT_AIDS_DIAG "></div>
             </div>  
             <div class="fieldHolder49">
             	<span>Index Interviewed <form:checkbox id="INDX_INTERVIEWED1"  path="stdNycijrDoc.indxInterviewed"    class="pL10 otherFieldDependentCBox"  dependentField="NYC_IJR_INDX_INTERVIEWED_DATE_TEXT"  value="1" />  Date</span>
             	<span class="pL10"><form:input path="stdNycijrDoc.indxInterviewedDateText"  id="NYC_IJR_INDX_INTERVIEWED_DATE_TEXT" requiredField="Y"  title="Interviewed Date" class="dPicker mask standardInputMedText" datevalidations="partialDate"  maxlength="10"/></span>
             	<div class="v_error NYC_IJR_INDX_INTERVIEWED_DATE_TEXT "></div>
             </div> 
             
       </div>
   </div>
   
   <div id="personHIVDetails" class="container">   
       <div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
           <span class=" mL2">Provider Information</span>                  
       </div>
       <div class="personInfo pT10 pTRBL10">       		 
           <span class="fL">Name</span>
           <span class="pL30 fL"><form:input id="PROV_NAME"  path="stdNycijrDoc.provName" title="Provider Name" class="standardInputText" maxlength="65"/></span> 
           
           <span class="fL pL30">Department/Clinic</span>
           <span class="pL30"><form:input path="stdNycijrDoc.provDepartment" id="Department" class="standardInputSmallText2" maxlength="65"/></span>          
       </div>
       <div class="b1" id="nycijrDocumentProviderDtls">
       
            <div class="personInfo pTRBL10">
                <span class="personLabelDoc">Address </span>
                <span> 
                    <span class="pL10"><form:input id="PROV_ADDRESS"  path="stdNycijrDoc.provAddress" title="Provider Address"  class="standardInputText newAddress widgetAddressFieldsReadonly" maxlength="65"/></span>
                    
                    <span class="pL30">City</span>
                    <span class="pL10"><form:input id="PROV_CITY"  path="stdNycijrDoc.provCity" title="Provider City" class="oAdrDlg standardInputSmallText newCity widgetAddressFieldsReadonly"  maxlength="35"/></span>
                    
                    <span class="pL30">State </span>
                    <span  class="pL10"><form:input id="PROV_STATE"  path="stdNycijrDoc.provState" title="Provider State" class="oAdrDlg standardInputSmallText newState widgetAddressFieldsReadonly" style="width:50px" maxlength="2"/></span>
                    
                    <span class="pL30">Zip </span>
                    <span class="pL10"> 
                        <form:input id="PROV_ZIP"  path="stdNycijrDoc.provZip" title="Provider Zipcode" class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5"/>
                    </span>
                    
					<span class="pL30">County </span>
                   	 <span class="pL10">  
                   	 		<form:select id="PROV_COUNTY"  path="stdNycijrDoc.provCounty" title="Provider County"  cssClass="oAdrDlg roundedBrdr newCounty widgetAddressFieldsReadonly"> 
									<form:option value=""></form:option>
									<form:options items="${sessionScope.countyMap}"/>
							</form:select>
                    </span> 
                    
                    <br>
                    <span class="labelunderneath3"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
                    
                </span>
            </div>
            <div class="personInfo pT10 pTRBL10">
            		<span class="" style="padding-left:58px"><form:input path="rawProvAddress"  class="standardInputText readonly" /></span>
                    	                
	                <span class="pL30">Telephone </span>
	                <span class="pL10"><form:input id="PROV_PHONE"  path="stdNycijrDoc.provPhone" title="Provider Phone"  class="pNumber standardInputSmallText" maxlength="20"/></span>
	                
	                
	                  <span style="float: right;" class="mr50">
							<a href="javascript:openAddressWidget('nycijrDocumentProviderDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
					</span> 
	                <br>
	                <span class="labelunderneath3"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>	            
            </div><br>
        </div>
   </div>

    <!--  Provider details  --> 
        <div id="personHIVDetails" class="container">            
            <div id="personHIVDetailsLeft">
	            <div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
	                <span class=" mL2">Completer Information</span>                  
	            </div>               
                <div class="pL10 pT10">
                    <span class="personLabelDoc4">Name </span>
                    <span> 
                        <span ><form:input id="COMPLETER_LNAME"  path="stdNycijrDoc.completerLname" title="Last Name" class="standardInputSmallText" maxlength="65"/></span> 
                        <span ><form:input id="COMPLETER_FNAME"  path="stdNycijrDoc.completerFname" title="First Name" class="standardInputSmallText" maxlength="65" /></span>
                        <span ><form:input id="COMPLETER_MI"  path="stdNycijrDoc.completerMi" title="Middle Name"  class="standardInputSmallText" maxlength="65" /></span>
                        <span class="labelunderneath4"> Last</span>
                        <span class="labelunderneath4"> First</span>
                        <span class="labelunderneath4"> Middle</span>
                    </span>
                </div>
                <div class="pL10 pT10">
                    <span class="riskLabelFieldLeft">Title</span>
                    <span ><form:input id="COMPLETER_TITLE"  path="stdNycijrDoc.completerTitle" title="Title" class="standardInputMedText"  maxlength="65"/></span> 
                </div>

                <div class="pL10 pT10">
                    <span class="riskLabelFieldLeft">E-mail address</span>
                    <span ><form:input path="stdNycijrDoc.completerEmail" id="NYC_IJR_COMPLETED_EMAIL" title="Email Address"  class="standardInputMedText"  maxlength="65"/></span>
                    
                     <div class="v_error NYC_IJR_COMPLETED_EMAIL "></div>
                </div>
                
                <div class="pL10 pT10">
	                <span class="riskLabelFieldLeft">Date Completed</span>
	                <fmt:formatDate value="${stdNYCIJRDocVO.stdNycijrDoc.completedDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedCompleteDate" />
	                <span class=""><form:input path="stdNycijrDoc.completedDate" id="NYC_IJR_COMPLETED_DATE" title="Date Completed" value="${theFormattedCompleteDate}"  datevalidations="completeDate" class="dPicker mask pNumber standardInputMedText" maxlength="10"/></span>
	                <div class="v_error NYC_IJR_COMPLETED_DATE "></div>	
	            </div>
                <br>                      
               
            </div>
            <!-- Other Contact Details -->
            <div id="personHIVDetailsRight" style="width:50.9%">
	            <div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
	                <span class=" mL2">Contact Information</span>                  
	            </div>
                <div class="personTitleFieldRight">Office contact person for information related to partner notification (if other than the person completing the form):</div>   
                <div class="pL10 pT10">
                    <span class="personLabelDoc4">Name </span>
                    <span> 
                        <span ><form:input id="CONTACT_LNAME"  path="stdNycijrDoc.contactLname" title="Last Name" class="standardInputSmallText"  maxlength="65"/></span> 
                        <span ><form:input id="CONTACT_FNAME"  path="stdNycijrDoc.contactFname" title="First Name" class="standardInputSmallText"  maxlength="65"/></span>
                        <span ><form:input id="CONTACT_MNAME"  path="stdNycijrDoc.contactMname" title="Middle Name" class="standardInputSmallText"  maxlength="65"/></span>
                        <span class="labelunderneath4"> Last</span>
                        <span class="labelunderneath4"> First</span>
                        <span class="labelunderneath4"> Middle</span>
                    </span>
                </div>
                <div class="pL10 pT10">
                    <span class="riskLabelFieldRight">Title</span>
                    <span ><form:input id="CONTACT_TITLE"  path="stdNycijrDoc.contactTitle" title="Title" class="standardInputSmallText"  maxlength="65"/></span> 
                </div>
                <div class="pL10 pT10">
                    <span class="riskLabelFieldRight">Telephone</span>
                    <span> 
                        <form:input id="CONTACT_PHONE"  path="stdNycijrDoc.contactPhone" title="Phone" class="pNumber standardInputSmallText" maxlength="15"/>
                    </span>
                </div><br><br>  
                
            </div>
        </div>
         <div id="personHIVDetails" class="container">
         	<div class="personInfo pT10 pTRBL10 alignCenter">Medical Provider: To the best of my knowledge, I certify that the above information is correct.</div>
              <div class="pT10 pL10"> 
                  <span class="fL"> Has Provider Signed Form</span> 
                  <span class="pL10"> <form:radiobutton id="PROV_SIGNED1"  path="stdNycijrDoc.provSigned" value="1" class="otherFieldDependentRadio" dependentField="NYC_IJR_PROV_SIGNED_DATE"/> Yes </span>
                  <span class="pL10"> <form:radiobutton id="PROV_SIGNED2"  path="stdNycijrDoc.provSigned" value="0" class="otherFieldDependentRadio" dependentField="NYC_IJR_PROV_SIGNED_DATE"/> No </span>
                 
          	      <span class="pL30">Date</span>
          	      <fmt:formatDate value="${stdNYCIJRDocVO.stdNycijrDoc.provSignedDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedProvSignDate" />
           	 	  <span class="pL10"><form:input path="stdNycijrDoc.provSignedDate" id="NYC_IJR_PROV_SIGNED_DATE" requiredField="Y"  title="Signed Date" value="${theFormattedProvSignDate}"  class="dPicker mask standardInputMedText" datevalidations="completeDate" maxlength="10"/></span>
           	 	  
           	 	  
           	 	  <div class="v_error NYC_IJR_PROV_SIGNED_DATE "></div>
           	 	   
              </div>  
         </div>
    </div>     

    <div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->   
    <div id="button-row" class="button-row documentButtons">      
        <div align="center">
           <c:choose>
				<c:when  test="${param.hasDeleteFunctionality == true}" >	
						<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
						<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
				</c:when>
				<c:when test="${stdNYCIJRDocVO.stateNo != null && stdNYCIJRDocVO.stateNo != '' && nyehmsStatenoDetails.REASON_DESC == null}">
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
    </div><br>
        
    <input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
</form:form>


