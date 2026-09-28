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
			<c:if test="${stdNYCIJRDocVO.PARENT_DOCUMENT_ID != null &&  stdNYCIJRDocVO.PARENT_DOCUMENT_ID != 0}">

				<div  class="floatLeft width70Perc" id="parentDocumentIdSection">
		            <span class="parentIdSectionLabel bold">Parent DocumentId: </span>
					<span class="parentIdSectionValue"><a href="#" onclick="loadDocumentDlg(${stdNYCIJRDocVO.PARENT_DOCUMENT_ID}, '${param.documentType}')">${stdNYCIJRDocVO.PARENT_DOCUMENT_ID}</a></span>
					
		         </div>	
	         </c:if>
	         
			<div  class="floatLeft">
	            <span class="mL10 bold">Document ID: </span>
				<span class=""><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
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
 			 			<fmt:formatDate value="${stdNYCIJRDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
						<form:input path="EHARS_IMPORT_DATE" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/>
 			 		</span>
 	           </div>                  
            </div>
            <div style="float:left; width: 30%; text-align:right">
 	         	<div >
 	                <span class="mL10">NYC SHIP Date: </span>
 	                <fmt:formatDate value="${stdNYCIJRDocVO.NYC_SHIP_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedNYC_SHIP_DATE_TEXT" ></fmt:formatDate>
					<span class="ml10 "><form:input path="NYC_SHIP_DATE" value="${theFormattedNYC_SHIP_DATE_TEXT}" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
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
                       <span class="pL5"><form:input path="FNAME" id="NYC_IJR_FNAME" class="standardInputMedText required" title="First Name" maxLength="25"/></span>
                       <div class="pL10 v_error NYC_IJR_FNAME "></div> 
                   </div>
                   <div class="fieldHolder25">
                      	<span class="pL10">Middle Name </span> 
                      	<span  class="pL5"><form:input path="MNAME" id="NYC_IJR_MNAME" class="standardInputMedText"  title="Middle Name" maxLength="25"/></span>
                      	<div class="pL10 v_error NYC_IJR_MNAME "></div>
                      </div>
                   <div class="fieldHolder25">
                      	<span class="pL10">Last Name </span> 
                      	<span  class="pL5"><form:input path="LNAME" id="NYC_IJR_LNAME" class="standardInputMedText required" title="Last Name" maxLength="25"/></span>
                      	<div class="pL10 v_error NYC_IJR_LNAME "></div>
                      </div>
                   <div class="fieldHolder25">
                			<span class="pL10">Alt Last Name </span> 
            				<span class="pL5"><form:input path="ALT_LNAME" id="NYC_IJR_ALT_LNAME" class="standardInputMedText" title="Alt Name" maxLength="25"/></span>
            				<div class="pL10 v_error NYC_IJR_ALT_LNAME "></div>
            			</div>  
                  <br><br>
                  
                  
          </div>
        <div class="clearBoth personInfo pB20 marginBottom20">
                               			<div class="fieldHolder25">
                                       		<span class="pL10">Date of Birth </span>
                                       		<span  class="pL10"><form:input path="DOB_TEXT" id="DATE_OF_BIRTH" class="dPicker mask standardInputSmallText" title="Date of Birth" maxLength="10" datevalidations="completeDate"/></span>
                                       		<div class="pL10 v_error DATE_OF_BIRTH "></div>
                                       </div>
                                </div>
        
        
        
        <div class="b1" id="nycIjrDocumentPersonDtls">
	        <div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
	           <span class=" mL2">Current Address</span>                  
	       </div>
            <div class="personInfo pTRBL10">
                <span> 
                    <span class="pL10"><form:input path="CUR_PERSON_ADDRESS" title="Current Address" class="standardInputMedText  newAddress widgetAddressFieldsReadonly" maxlength="65"/></span>
                    
                    
                    <span class="pL10">City</span>
                    <span class="pL10"><form:input path="CUR_PERSON_CITY" title="Current City"  class="oAdrDlg standardInputSmallText newCity widgetAddressFieldsReadonly" maxlength="35"/></span>
                    
                    <span class="pL10">State </span>
                    <span  class="pL10"><form:input path="CUR_PERSON_STATE" title="Current State"  class="oAdrDlg standardInputSmallText newState widgetAddressFieldsReadonly" style="width:50px" maxlength="2"/></span>
                    
                    <span class="pL10">Zip </span>
                    <span class="pL10"> 
                        <form:input path="CUR_PERSON_ZIP" title="Current Zip"  class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5" />
                    </span>
                    
					<span class="pL10">County </span>
                    	<span class="pL10">  
                    		<form:select path="CUR_PERSON_COUNTY" title="Current Count" cssClass="oAdrDlg roundedBrdr newCounty widgetAddressFieldsReadonly"> 
									<form:option value=""></form:option>
									<form:options items="${sessionScope.countyMap}"/>
							</form:select>
                    </span> 
                    
                    <span class="pL10">County Name </span>
                    <span class="pL10"> 
                        <form:input path="CUR_PERSON_COUNTY_NAME" title="Current County Name" class="oAdrDlg standardInputSmallText" maxlength="15"/>
                    </span>
                    <br>
                    <span class="pL50"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
                    
                </span>
            </div>
            <div class="personInfo pT10 pTRBL10">
                    <span class="pL10" ><form:input path="rawAddress"  class="standardInputMedText   readonly" /></span>
                    
	                <span class="pL30">Cell Phone</span>
	                <span class="pL10"><form:input path="PERSON_CELLPHONE" title="Cell Phone" style="width:115px" class="pNumber standardInputSmallText" maxlength="15"/></span>
	                
	                <span class="pL30">Home Phone </span>
	                <span class="pL10"><form:input path="CUR_PERSON_PHONE" title="Current Phone" style="width:115px" class="pNumber standardInputSmallText" maxlength="15"/></span>
	                
	                <span class="pL30">Work Phone</span>
	                <span class="pL10"><form:input path="PERSON_WORKPHONE"  title="Work Phone" style="width:115px" class="pNumber standardInputSmallText" maxlength="15"/></span>
	                
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
                    <span class="pL10"><form:input path="DX_PERSON_ADDRESS" title="Diagnosis Address"  class="standardInputText  newAddress widgetAddressFieldsReadonly" style="width:330px" /></span>
                    
                    
                    <span class="pL20">City</span>
                    <span class="pL10"><form:input path="DX_PERSON_CITY" title="Diagnosis City"  class="oAdrDlg standardInputSmallText newCity widgetAddressFieldsReadonly"  maxlength="35"/></span>
                    
                    <span class="pL20">State </span>
                    <span  class="pL10"><form:input path="DX_PERSON_STATE" title="Diagnosis State"  class="oAdrDlg standardInputSmallText newState widgetAddressFieldsReadonly" style="width:50px" maxlength="2"/></span>
                    
                    <span class="pL20">Zip </span>
                    <span class="pL10"> 
                        <form:input path="DX_PERSON_ZIP" title="Diagnosis Zipcode"  class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5"/>
                    </span>
                    <span class="pL20">County </span>
                    <span class="pL10"> 
                    	<form:select path="DX_PERSON_COUNTY" title="Diagnosis County"  cssClass="oAdrDlg roundedBrdr newCounty widgetAddressFieldsReadonly"> 
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
                <span class="pL10"><form:input path="DX_PERSON_TELEPHONE" title="Diagnosis Home Phone"  class="pNumber standardInputSmallText" maxlength="15"/>&nbsp;&nbsp;</span>
                
                <span class="pL30">Alternate Phone</span>
                <span class="pL10"><form:input path="RSH_PHONE" title="Alternate Phone"  class="pNumber standardInputSmallText" maxlength="15"/>&nbsp;&nbsp;</span>
                 
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
                        <form:checkbox path="RACE_WHITE"  value="1" /> White 
                    </div>
                    <div>
                        <form:checkbox path="RACE_BLACK"  value="1" /> African American
                    </div>
                    <div>
                        <form:checkbox path="RACE_ASIAN"  value="1" /> Asian/Pacific Islander
                    </div>
                    <div>
                        <form:checkbox path="RACE_INDIAN"  value="1" /> American Indian/Alaskan Native
                    </div>
                    <div>
                        <form:checkbox path="RACE_PACIFIC"  value="1" /> Pacific
                    </div>
                    <div>
                        <form:checkbox path="RACE_MULTIPLE"  value="1" /> Multiple
                    </div>
                    <div>
                        <form:checkbox path="RACE_OTHER"  value="1" /> Other 
                    </div>
                    <div>
                        <form:checkbox path="RACE_UNKNOWN"  value="1" /> Unknown
                    </div>           
                </div>  
                <br>
                <div class="borderField pT10 ethnicityHWIJR">   
                    <div>
                        <span class="bold">Ethnicity (Check one) </br> Hispanic or Latino<br></span>
                        <form:radiobutton path="ETHNICITY"  value="01" /> Yes<br>
                        <form:radiobutton path="ETHNICITY"  value="02" /> No<br>
                        <form:radiobutton path="ETHNICITY"  value="09" /> Unknown
                    </div>
                </div>  
                
                <div class="borderField pT10 birthCountyHWIJR">
                	<div class="floatLeft pR10" style="border-right: dotted 1px;">
						<div class=""> Current Gender </div>
						<div  class="">
							<div> <form:radiobutton path="CURRENT_GENDER" value="M" /> Male </div>
							<div> <form:radiobutton path="CURRENT_GENDER" value="F" /> Female </div>
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
            <div class="fieldHolder75">
                <div class="riskHWIJR borderField" style="padding: 10px 0 0 0">
                	<div class="pL20">
	            		<span class="fL">SSN</span>
	               		<span class="fR mR50"><form:input path="SSN" id="NYC_IJR_SSN"  title="SSN" class="pNumber standardInputMedText" maxlength="9"/></span>
	               		<div class="fL v_error NYC_IJR_SSN "></div>
	            	</div>
	            	<div class="clearBoth pL20 pT10">
	            		<span class="fL">NYC Sent stateno</span>
	               		<span class="fR mR50"><form:input path="NYC_SENT_STATENO"  title="NYC Sent stateno" class="standardInputMedText readonly" maxlength="10"/></span>
	            	</div>
	            	<div class="clearBoth pT10 pL20">
	            		<span class="fL">Patient Record Number</span>
	               		<span class="fR mR50"><form:input path="MED_RECORD_NUMBER" title="Patient Record Number"  class="standardInputMedText" maxlength="20"/></span>
	            	</div>
	            	<div class="clearBoth pT10 pL20">
	            		<span class="fL">NYSID</span>
	               		<span class="fR mR50"><form:input path="NYSID" id="NYC_IJR_NYSID" title="NYSID"  class="standardInputMedText" maxlength="9" /></span>
	               		<div class="fL v_error NYC_IJR_NYSID "></div>
	            	</div>
	            	<div class="clearBoth pT10 pL20">
	            		<span class="fL">DIN</span>
	               		<span class="fR mR50"><form:input path="DIN" id="NYC_IJR_DIN" title="DIN" class="standardInputMedText" maxlength="7"/></span>
	               		<div class="fL v_error NYC_IJR_DIN "></div>
	            	</div>
	            	
	            	<div class="clearBoth pT10 pL20">
	            		<span class="fL">AGE</span>
	               		<span class="fR mR50"><form:input path="AGE"  title="AGE" class="standardInputMedText nOnly" maxlength="3"/></span>
	            	</div>
	            	
	            	<div class="clearBoth pT10 pL20">
	            		<span class="fL">File Date</span>
	            		<fmt:formatDate value="${stdNYCIJRDocVO.FILE_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedFileDate" />
	            		<span class="fR mR50"><form:input path="FILE_DATE" id="NYC_IJR_FILE_DATE" title="File Date" value="${theFormattedFileDate}"  class="mask standardInputMedText readonly" datevalidations="completeDate" maxlength="10"/></span>
	            		<div class="v_error NYC_IJR_FILE_DATE "></div>
	            	</div>
	            	
	            	<div class="clearBoth pT10 pL20">
	            		<span class="fL">Create Date</span>
	            		<fmt:formatDate value="${stdNYCIJRDocVO.CREATE_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedCreateDate" />
	               		<span class="fR mR50"><form:input path="CREATE_DATE" title="Create Date" value="${theFormattedCreateDate}"   class="standardInputMedText readonly" maxlength="10"/></span>
	            	</div>
	            	
	            	<div class="clearBoth pT10 pL20 displayNone" id="createUidField">
	            		<span class="fL">CREATE_UID</span>
	               		<span class="fR mR50"><form:input path="CREATE_UID" title="Create UID"  class="standardInputMedText readonly" maxlength="10"/></span>
	            	</div>
	            	
	            	
	            	
	           		<div class="clearBoth mT10 pL20 borderField" style="width:391px">
	           		<br>
	                    <span class="bold">Country of birth</span><br>
	                    <form:radiobutton path="BIRTH_COUNTRY" value="US" /> US <br>
	                    <form:radiobutton path="BIRTH_COUNTRY" value="US DEPENDENCY" /> US dependency <br>
	                    <form:radiobutton path="BIRTH_COUNTRY" value="OTHER" /> Other  <br>
	                    <form:radiobutton path="BIRTH_COUNTRY" value="UNK" /> Unknown
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
                        <span class="riskValueField"><form:radiobutton path="SWM"  value="Y" /></span>
                        <span class="riskValueField"><form:radiobutton path="SWM"  value="N" /></span>
                        <span class="riskValueField"><form:radiobutton path="SWM"  value="U" /></span><br>        
                    </div>
                    <div class="pT8">;
                        <span class="riskLabelField">Sex With Female</span>
                        <span class="riskValueField"><form:radiobutton path="SWF"  value="Y" /></span>
                        <span class="riskValueField"><form:radiobutton path="SWF"  value="N" /></span>
                        <span class="riskValueField"><form:radiobutton path="SWF"  value="U" /></span>
                    </div>
                    <div class="pT8 pB5">
                        <span class="riskLabelField pT8 pB5">Injection Drug Use </span>
                        <span class="riskValueField pT8 pB5"><form:radiobutton path="IDU"  value="Y" /></span>
                        <span class="riskValueField pT8 pB5"><form:radiobutton path="IDU"  value="N" /></span>
                        <span class="riskValueField pT8 pB5"><form:radiobutton path="IDU"  value="U" /></span> 
                    </div>
                    <div class="pT10"> <u>Heterosexual sex with:</u> </div>
                    <ul>
                        <li class="">                            
                            <span class="riskLabelField1">Injection Drug User </span>
                            <span class="riskValueField1"><form:radiobutton path="SEX_PARTNER_IDU"  value="Y" /></span>
                            <span class="riskValueField1"><form:radiobutton path="SEX_PARTNER_IDU"  value="N" /></span>
                            <span class="riskValueField1"><form:radiobutton path="SEX_PARTNER_IDU"  value="U" /></span>                            
                        </li>
                        <li class="pT8">                            
                            <span class="riskLabelField1">Bisexual man </span>
                            <span class="riskValueField1"><form:radiobutton path="SEX_PARTNER_MSM"  value="Y" /></span>
                            <span class="riskValueField1"><form:radiobutton path="SEX_PARTNER_MSM"  value="N" /></span>  
                            <span class="riskValueField1"><form:radiobutton path="SEX_PARTNER_MSM"  value="U" /></span>                            
                        </li>
                        <li class="pT8">                            
                            <span class="riskLabelField1">HIV+ person </span>
                            <span class="riskValueField1"><form:radiobutton path="SEX_PARTNER_HIV"  value="Y" /></span>
                            <span class="riskValueField1"><form:radiobutton path="SEX_PARTNER_HIV"  value="N" /></span>  
                            <span class="riskValueField1"><form:radiobutton path="SEX_PARTNER_HIV"  value="U" /></span>                            
                        </li>
                    </ul>
                    <div class="pT8 pB5">
                        <span class="riskLabelField pB5">Perinatal Exposure </span>
                        <span class="riskValueField pB5"><form:radiobutton path="PERINATAL"  value="Y" /></span>
                        <span class="riskValueField pB5"><form:radiobutton path="PERINATAL"  value="N" /></span>   
                        <span class="riskValueField pB5"><form:radiobutton path="PERINATAL"  value="U" /></span>        
                    </div>  
                    <div class="pT8">
                        <span class="riskLabelField">Received Blood Products </span>
                        <span class="riskValueField"><form:radiobutton path="RECV_BLOOD_PROD"  value="Y" class="otherFieldDependentRadio" dependentField="NYC_IJR_RECV_BLOOD_PROD_DATE_TEXT,NYC_IJR_RECV_BLOOD_PROD_COUNTRY"/></span>
                        <span class="riskValueField"><form:radiobutton path="RECV_BLOOD_PROD"  value="N" class="otherFieldDependentRadio" dependentField="NYC_IJR_RECV_BLOOD_PROD_DATE_TEXT,NYC_IJR_RECV_BLOOD_PROD_COUNTRY" /></span>    
                        <span class="riskValueField"><form:radiobutton path="RECV_BLOOD_PROD"  value="U" class="otherFieldDependentRadio" dependentField="NYC_IJR_RECV_BLOOD_PROD_DATE_TEXT,NYC_IJR_RECV_BLOOD_PROD_COUNTRY" /></span>     
                    </div><br>
                    <ul><br>
                        <li class="">
                            <span class="riskLabelField2">Date </span>
                            <span class="riskValueField2"><form:input path="RECV_BLOOD_PROD_DATE_TEXT" id="NYC_IJR_RECV_BLOOD_PROD_DATE_TEXT" requiredField="Y"  title="Received Blood Product Date" class="dPicker mask standardInputSmallText" datevalidations="partialDate"/></span> <br>
                            <div class="v_error NYC_IJR_RECV_BLOOD_PROD_DATE_TEXT "></div>
                        </li>
                        <li class="pT8">
                            <span class="riskLabelField2">Country </span>
                            <span class="riskValueField2"><form:input path="RECV_BLOOD_PROD_COUNTRY" id="NYC_IJR_RECV_BLOOD_PROD_COUNTRY" title="Received Blood Product Country"  class="standardInputSmallText" /></span>
                        </li>   
                    </ul>   
                    <div>
                        <span class="riskLabelField3">Other Risk </span>
                        <span class="riskValueField"><form:radiobutton path="RISK_OTHER"  value="Y" class="otherFieldDependentRadio" dependentField="NYC_IJR_RISK_OTHER_TEXT"/></span>
                        <span class="riskValueField"><form:radiobutton path="RISK_OTHER"  value="N" class="otherFieldDependentRadio" dependentField="NYC_IJR_RISK_OTHER_TEXT" /></span>    
                        <span class="riskValueField"><form:radiobutton path="RISK_OTHER"  value="U" class="otherFieldDependentRadio" dependentField="NYC_IJR_RISK_OTHER_TEXT" /></span>                        
                    </div>  
                    <br>
                    <ul style="">
                        <li class="">
                            <span class="riskLabelField2">Other Risk Specified </span>
                            <span class="riskValueField2"><form:input path="RISK_OTHER_TEXT" id="NYC_IJR_RISK_OTHER_TEXT" requiredField="Y"  title="Risk Other Text" class="standardInputSmallText" maxlength="20"/></span>
                        </li>   
                    </ul>
                     <div>
                             <span class="riskLabelField3">Unknown </span>
                             <span class="riskValueField"><form:radiobutton path="RISK_UNKNOWN" value="Y" /></span>
                             <span class="riskValueField"><form:radiobutton path="RISK_UNKNOWN" value="N" /></span>    
                             <span class="riskValueField"><form:radiobutton path="RISK_UNKNOWN" value="U" /></span>
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
	               <form:input path="HIV_DX_DATE_TEXT" id="NYC_IJR_HIV_DX_DATE_TEXT" title="HIV Diagnosis Date" class="standardInputMedText dPicker mask" datevalidations="partialDate" maxlength="10"/>
	           </span>
           		<div class="v_error NYC_IJR_HIV_DX_DATE_TEXT "></div>
           	</div>
           	<div class="fieldHolder33">
           		<span class="pL30"> AIDS Dx Date</span>
       			<span class="pL10"><form:input path="AIDS_DX_DATE_TEXT" id="NYC_IJR_AIDS_DX_DATE_TEXT" title="AIDS Diagnosis Date" class="dPicker mask standardInputMedText" datevalidations="partialDate" maxlength="10"/></span>
       			
       			<div class="pL30 v_error NYC_IJR_AIDS_DX_DATE_TEXT "></div>
       		</div>
       		<div class="fieldHolder33">
       			<span class="pL30"> HIV Facility name</span>
       			<span class="pL10"><form:input path="HIV_FACILITY_NAME"  title="HIV Facility Name" class="standardInputMedText" maxlength="30"/></span>
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
	             <span style="padding-left:43px"><form:input path="ADMISSION_DATE_TEXT" id="NYC_IJR_ADMISSION_DATE_TEXT" title="Admission Date"  class="dPicker mask standardInputMedText" datevalidations="partialDate" maxlength="10"/></span>
	             
	             <div class="v_error NYC_IJR_ADMISSION_DATE_TEXT "></div> 
	          </div>   
	          <div class="fieldHolder49">
	             <span>PS Requested <form:checkbox path="PS_REQUESTED" class="pL10" style="margin-left:19px" value="1" /> </span> 
	             
	             <span class="pL30">Number of Partners</span>
	             <span class="pL10"><form:input path="NUMBEROFPARTNERS" title="Number of Partners" class="standardInputMedText nOnly" maxlength="2"/></span> 
	          </div>   
             <br><br>
             
             <div class="clearBoth  fieldHolder49">
	             <span class="fL">Initial HIV Dx <form:checkbox style="margin-left:19px" path="INIT_HIV_DIAG"   class="pL10 otherFieldDependentCBox"  dependentField="NYC_IJR_INIT_HIV_DIAG_DATE_TEXT" value="1" />  Date</span>
	             <span class="pL10"><form:input path="INIT_HIV_DIAG_DATE_TEXT" id="NYC_IJR_INIT_HIV_DIAG_DATE_TEXT" requiredField="Y"  title="Initial HIV Diag Date"  class="dPicker mask standardInputMedText" datevalidations="partialDate"  maxlength="10"/></span>
	             <div class="v_error NYC_IJR_INIT_HIV_DIAG_DATE_TEXT "></div>
	         </div>
	         <div class="fieldHolder49">    
             	<span>Informed of Dx <form:checkbox style="margin-left:19px"   class="pL10 otherFieldDependentCBox"  dependentField="NYC_IJR_INFORMED_HIV_DATE_TEXT"  path="INFORMED_HIV_POS" value="1" />  Date</span>
             	<span class="pL10"><form:input path="INFORMED_HIV_DATE_TEXT" title="Informed HIV Diag Date" id="NYC_IJR_INFORMED_HIV_DATE_TEXT" requiredField="Y"   class="dPicker mask standardInputMedText" datevalidations="partialDate"  maxlength="10"/></span>
             	<div class="v_error NYC_IJR_INFORMED_HIV_DATE_TEXT "></div>
             </div> 
             
             <br><br>
             <div class="clearBoth  fieldHolder49">
             	<span class="fL">Initial AIDS Dx <form:checkbox style="margin-left:10px" path="INIT_AIDS_DIAG"   class="pL10 otherFieldDependentCBox"  dependentField="NYC_IJR_INIT_AIDS_DIAG"  value="1" />  Date</span>
             	<span class="pL10"><form:input path="INIT_AIDS_DIAG_DATE_TEXT" id="NYC_IJR_INIT_AIDS_DIAG" requiredField="Y"   title="Initial AIDS Diag Date"  class="dPicker mask standardInputMedText" datevalidations="partialDate"  maxlength="10"/></span>
             	<div class="v_error NYC_IJR_INIT_AIDS_DIAG "></div>
             </div>  
             <div class="fieldHolder49">
             	<span>Index Interviewed <form:checkbox path="INDX_INTERVIEWED"    class="pL10 otherFieldDependentCBox"  dependentField="NYC_IJR_INDX_INTERVIEWED_DATE_TEXT"  value="1" />  Date</span>
             	<span class="pL10"><form:input path="INDX_INTERVIEWED_DATE_TEXT"  id="NYC_IJR_INDX_INTERVIEWED_DATE_TEXT" requiredField="Y"  title="Interviewed Date" class="dPicker mask standardInputMedText" datevalidations="partialDate"  maxlength="10"/></span>
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
           <span class="pL30 fL"><form:input path="PROV_NAME" title="Provider Name" class="standardInputText" maxlength="65"/></span> 
           
           <span class="fL pL30">Department/Clinic</span>
           <span class="pL30"><form:input path="PROV_DEPARTMENT" id="Department" class="standardInputSmallText2" maxlength="65"/></span>          
       </div>
       <div class="b1" id="nycijrDocumentProviderDtls">
       
            <div class="personInfo pTRBL10">
                <span class="personLabelDoc">Address </span>
                <span> 
                    <span class="pL10"><form:input path="PROV_ADDRESS" title="Provider Address"  class="standardInputText newAddress widgetAddressFieldsReadonly" maxlength="65"/></span>
                    
                    <span class="pL30">City</span>
                    <span class="pL10"><form:input path="PROV_CITY" title="Provider City" class="oAdrDlg standardInputSmallText newCity widgetAddressFieldsReadonly"  maxlength="35"/></span>
                    
                    <span class="pL30">State </span>
                    <span  class="pL10"><form:input path="PROV_STATE" title="Provider State" class="oAdrDlg standardInputSmallText newState widgetAddressFieldsReadonly" style="width:50px" maxlength="2"/></span>
                    
                    <span class="pL30">Zip </span>
                    <span class="pL10"> 
                        <form:input path="PROV_ZIP" title="Provider Zipcode" class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5"/>
                    </span>
                    
					<span class="pL30">County </span>
                   	 <span class="pL10">  
                   	 		<form:select path="PROV_COUNTY" title="Provider County"  cssClass="oAdrDlg roundedBrdr newCounty widgetAddressFieldsReadonly"> 
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
	                <span class="pL10"><form:input path="PROV_PHONE" title="Provider Phone"  class="pNumber standardInputSmallText" maxlength="20"/></span>
	                
	                
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
                        <span ><form:input path="COMPLETER_LNAME" title="Last Name" class="standardInputSmallText" maxlength="65"/></span> 
                        <span ><form:input path="COMPLETER_FNAME" title="First Name" class="standardInputSmallText" maxlength="65" /></span>
                        <span ><form:input path="COMPLETER_MI" title="Middle Name"  class="standardInputSmallText" maxlength="65" /></span>
                        <span class="labelunderneath4"> Last</span>
                        <span class="labelunderneath4"> First</span>
                        <span class="labelunderneath4"> Middle</span>
                    </span>
                </div>
                <div class="pL10 pT10">
                    <span class="riskLabelFieldLeft">Title</span>
                    <span ><form:input path="COMPLETER_TITLE" title="Title" class="standardInputMedText"  maxlength="65"/></span> 
                </div>

                <div class="pL10 pT10">
                    <span class="riskLabelFieldLeft">E-mail address</span>
                    <span ><form:input path="COMPLETER_EMAIL" id="NYC_IJR_COMPLETED_EMAIL" title="Email Address"  class="standardInputMedText"  maxlength="65"/></span>
                    
                     <div class="v_error NYC_IJR_COMPLETED_EMAIL "></div>
                </div>
                
                <div class="pL10 pT10">
	                <span class="riskLabelFieldLeft">Date Completed</span>
	                <fmt:formatDate value="${stdNYCIJRDocVO.COMPLETED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedCompleteDate" />
	                <span class=""><form:input path="COMPLETED_DATE" id="NYC_IJR_COMPLETED_DATE" title="Date Completed" value="${theFormattedCompleteDate}"  datevalidations="completeDate" class="dPicker mask pNumber standardInputMedText" maxlength="10"/></span>
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
                        <span ><form:input path="CONTACT_LNAME" title="Last Name" class="standardInputSmallText"  maxlength="65"/></span> 
                        <span ><form:input path="CONTACT_FNAME" title="First Name" class="standardInputSmallText"  maxlength="65"/></span>
                        <span ><form:input path="CONTACT_MNAME" title="Middle Name" class="standardInputSmallText"  maxlength="65"/></span>
                        <span class="labelunderneath4"> Last</span>
                        <span class="labelunderneath4"> First</span>
                        <span class="labelunderneath4"> Middle</span>
                    </span>
                </div>
                <div class="pL10 pT10">
                    <span class="riskLabelFieldRight">Title</span>
                    <span ><form:input path="CONTACT_TITLE" title="Title" class="standardInputSmallText"  maxlength="65"/></span> 
                </div>
                <div class="pL10 pT10">
                    <span class="riskLabelFieldRight">Telephone</span>
                    <span> 
                        <form:input path="CONTACT_PHONE" title="Phone" class="pNumber standardInputSmallText" maxlength="15"/>
                    </span>
                </div><br><br>  
                
            </div>
        </div>
         <div id="personHIVDetails" class="container">
         	<div class="personInfo pT10 pTRBL10 alignCenter">Medical Provider: To the best of my knowledge, I certify that the above information is correct.</div>
              <div class="pT10 pL10"> 
                  <span class="fL"> Has Provider Signed Form</span> 
                  <span class="pL10"> <form:radiobutton path="PROV_SIGNED" value="1" class="otherFieldDependentRadio" dependentField="NYC_IJR_PROV_SIGNED_DATE"/> Yes </span>
                  <span class="pL10"> <form:radiobutton path="PROV_SIGNED" value="0" class="otherFieldDependentRadio" dependentField="NYC_IJR_PROV_SIGNED_DATE"/> No </span>
                 
          	      <span class="pL30">Date</span>
          	      <fmt:formatDate value="${stdNYCIJRDocVO.PROV_SIGNED_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedProvSignDate" />
           	 	  <span class="pL10"><form:input path="PROV_SIGNED_DATE" id="NYC_IJR_PROV_SIGNED_DATE" requiredField="Y"  title="Signed Date" value="${theFormattedProvSignDate}"  class="dPicker mask standardInputMedText" datevalidations="completeDate" maxlength="10"/></span>
           	 	  
           	 	  
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


