<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link href="${staticPath}/css/docs/commonForm.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/docs/nycijrDoc.css?v=${buildDate}" rel="stylesheet" type="text/css" />

<script src="${staticPath}/js/docs/nycDoc.js?v=${buildDate}"></script>

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
		height:63px;
	    width:266px;
	}
	
	.ethnicityHWIJR{
		height:90px;
	    width:266px;
	}
	
	.riskHWIJR{
	    height:372px;
	    width:412px;
	    float:left;
	}
	
	.reportHWIJR{
	    height:362px;
	    width:429px;
	    float:right;
	}
	
	.pL10{
		padding-left:10px;
	}
	
	#StdNYCEHARSDocVO .labelunderneath2{
		padding-left:120px;
	}
	
	#StdNYCEHARSDocVO .labelunderneath1{
		padding-left:175px;
	}
	
	#StdNYCEHARSDocVO .labelunderneath3{
		padding-left:160px;
	}
	#StdNYCEHARSDocVO .labelunderneath5{
		padding-left:245px;
	}
	 
	 #StdNYCEHARSDocVO .labelunderneath6{
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


<form:form modelAttribute="StdNYCEHARSDocVO" id="StdNYCEHARSDocVO" cssClass="documentFormVO documentDescription">
<form:errors path="*" cssClass="errorblock" element="div" />

<div id="labDocTab">
   <div style="padding-left:43%; " class="headingCls">
                NYC eHARS DOCUMENT
        </div>
    
    
     <div class="pB20 pT20">
		
					
			<c:if test="${StdNYCEHARSDocVO.parentDocumentId != null &&  StdNYCEHARSDocVO.parentDocumentId != 0}">

				<div  class="floatLeft" id="parentDocumentIdSection">
		            <span class=" parentIdSectionLabel bold">Parent DocumentId: </span>
					<span class="parentIdSectionValue "><a href="#" onclick="loadDocumentDlg(${StdNYCEHARSDocVO.parentDocumentId}, '${param.documentType}')">${StdNYCEHARSDocVO.parentDocumentId}</a></span>
					
		         </div>	
	         </c:if>


		</div>
		
		
    <div class="container pB10 w100 mT10">
            <div style="float:left; width: 40%">
	            <div>
	                <span class="ml10"> <spring:message code="label.stateNo" />: </span>
	                <span class="ml10" style=""><form:input id="STATENO"  path="stateno" class="standardInputSmallText readonly" /> </span>
	            </div>    
            </div>
            <div style="float:left; width: 30%">
 	         	<div>
 	                <span class="mL10 bold">eHARS Import Date: </span>
					<span class="ml10 ">
						<fmt:formatDate value="${StdNYCEHARSDocVO.stdNycEharsDoc.eharsImportDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
						<form:input id="EHARS_IMPORT_DATE"  path="stdNycEharsDoc.eharsImportDate" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/>
					</span>
 	            </div> 
  
            </div>
            <div style="float:left; width: 30%; text-align:right">
 	            <div>
 	                <span class=" ml10 bold">	Document ID: </span>
 			 		<span class="ml10"><form:input id="DOCUMENT_ID"  path="stdNycEharsDoc.documentId" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/>
 			 			
 			 		</span>
 	           </div>                  
 	         </div>     
    </div>
    <jsp:include page="statenoDetails.jsp"></jsp:include>

    <div class="container" id="patientInformation">  
        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
            <span class=" mL2">Patient Information</span>
        </div>
        
        <div class="personInfo">
        		<div class="fieldHolder25">
                     <span class="pL10">First Name </span> 
                     <span class="pL5"><form:input  path="stdNycEharsDoc.fname" id="NYC_DEMO_FNAME" class="standardInputMedText " title="First Name" maxLength="25"/></span>
                     <div class="pL10 v_error NYC_DEMO_FNAME "></div> 
                 </div>
                 <div class="fieldHolder25">
                    	<span class="pL10">Middle Name </span> 
                    	<span  class="pL5"><form:input  path="stdNycEharsDoc.mname" id="NYC_DEMO_MNAME" class="standardInputMedText"  title="Middle Name" maxLength="25"/></span>
                    	<div class="pL10 v_error NYC_DEMO_MNAME "></div>
                    </div>
                 <div class="fieldHolder25">
                    	<span class="pL10">Last Name </span> 
                    	<span  class="pL5"><form:input  path="stdNycEharsDoc.lname" id="NYC_DEMO_LNAME" class="standardInputMedText " title="Last Name" maxLength="30"/></span>
                    	<div class="pL10 v_error NYC_DEMO_LNAME "></div>
                    </div>
                
                <br><br>
                
                
        </div>
     
        <div class="personInfo pTRBL10">
        	<div class="fieldHolder25">
                <span class=" bold">Vital Status</span>
                <span class="pL10">
                	<form:radiobutton id="VITAL_STATUS1"  path="stdNycEharsDoc.vitalStatus" value="1" /> Alive
                	<form:radiobutton id="VITAL_STATUS2"  path="stdNycEharsDoc.vitalStatus" value="2" /> Dead
                </span>
             </div>
             <div class="fieldHolder25">
                <span class="pL10 bold">Date of Birth</span>
                <span class="pL10"><form:input  path="stdNycEharsDoc.dobText" id="DATE_OF_BIRTH"  class="standardInputSmallText mask dPicker" datevalidations="completeDate" maxlength="10" title="Date of Birth"/></span>
                <div class="pL10 v_error DATE_OF_BIRTH "></div>
             </div>
             <div class="fieldHolder25">   
                <span class="pL10 bold">Date of Death</span>
                <span class="pL10"><form:input  path="stdNycEharsDoc.dodText" id="DATE_OF_DEATH"  class="standardInputSmallText mask dPicker" datevalidations="completeDate" maxlength="10" title="Date of Death"/></span>
                <div class="pL10 v_error DATE_OF_DEATH "></div>
             </div>
             <div class="fieldHolder25">
                <span class="pL10 bold">SSN</span>
                <span class="pL10"><form:input  path="stdNycEharsDoc.ssn" id="NYC_DEMO_SSN" class="standardInputSmallText" maxlength="9" title="SSN"/></span>
                <div class="pL10 v_error NYC_DEMO_SSN "></div>
             </div>   
        </div>
         <br/><br/>
        <div class="clearBoth b1 pB20" id="nycEharsDocumentPersonAddressDtls">
            <div class="personInfo pTRBL10">
                <span class="personLabelDoc bold">Current Address </span>
                <span> 
                    <span class="pL10"><form:input id="PERSON_ADDRESS"  path="stdNycEharsDoc.personAddress"  class="standardInputMedText  newAddress widgetAddressFieldsReadonly" title="Current Address" maxlength="60"/></span>
                    
                    
                    <span class="pL10 bold">City</span>
                    <span class="pL10"><form:input id="PERSON_CITY"  path="stdNycEharsDoc.personCity"  class="oAdrDlg standardInputSmallText newCity widgetAddressFieldsReadonly" title="City" maxlength="40"/></span>
                    
                    <span class="pL10 bold">State </span>
                    <span  class="pL10"><form:input id="PERSON_STATE"  path="stdNycEharsDoc.personState"  class="oAdrDlg standardInputSmallText newState widgetAddressFieldsReadonly" style="width:50px" title="State" maxlength="2"/></span>
                    
                    <span class="pL10 bold">Zip </span>
                    <span class="pL10"> 
                        <form:input id="PERSON_ZIP"  path="stdNycEharsDoc.personZip"  class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5" title="Zip Code"/>
                    </span>
                    
					<span class="pL10 bold">County </span>
                    	<span class="pL10">  
                    		<form:select id="PERSON_COUNTY"  path="stdNycEharsDoc.personCounty"  cssClass="oAdrDlg roundedBrdr newCounty widgetAddressFieldsReadonly" title="County"> 
									<form:option value=""></form:option>
									<form:options items="${sessionScope.countyMap}"/>
							</form:select>
                    </span> 
                    
                    <br>
                    <span class="labelunderneath3"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
                    
                </span>
            </div>
            <div class="personInfo pT10 pTRBL10">
            		<div class="fieldHolder25">
                    	<span class="" style="padding-left:103px"><form:input id="rawAddress"  path="rawaddress"  class="standardInputMedText readonly" title="Raw Current Address"/></span>
                    	
                    	<br>
	                	<span class="labelunderneath3"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
                    </div>
                    <div class="fieldHolder25">
		                <span class="pL30 bold">Address Date</span>
		                <span class="pL10"><form:input  path="stdNycEharsDoc.personAddressDate" id="NYC_DEMO_PERSON_ADDRESS_DATE" style="width:115px" class="standardInputSmallText mask dPicker" datevalidations="partialDate" maxlength="10" title="Address Date"/></span>
		                
		                <div class="pL10 v_error NYC_DEMO_PERSON_ADDRESS_DATE "></div>
		             </div>
	                
	                
	                <span style="float: right;" class="mr50">
						<a href="javascript:openAddressWidget('nycEharsDocumentPersonAddressDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
					</span>  
						 
						 
	                
		            
            </div><br>
        </div>

        <!--  Race details -->
        
        <div class="clearBoth raceRiskReprt borderDotted">
        	<div class="fieldHolder25">
        		<div class="container  pTRBL10 borderDotted" >
                    <div class="bold">
                        Race
                    </div>
                    <div>
                        <form:checkbox id="RACE_WHITE1"  path="stdNycEharsDoc.raceWhite"  value="1" /> White 
                    </div>
                    <div>
                        <form:checkbox id="RACE_BLACK1"  path="stdNycEharsDoc.raceBlack"  value="1" /> African American
                    </div>
                    <div>
                        <form:checkbox id="RACE_ASIAN1"  path="stdNycEharsDoc.raceAsian"  value="1" /> Asian
                    </div>
                    <div>
                        <form:checkbox id="RACE_INDIAN1"  path="stdNycEharsDoc.raceIndian"  value="1" /> American Indian/Alaskan Native
                    </div>
                    <div>
                        <form:checkbox id="RACE_PACIFIC1"  path="stdNycEharsDoc.racePacific"  value="1" /> Pacific
                    </div>
                    <br/>
                </div> 
                <div class="borderDotted pTRBL10 "  >   
                    <div>
                        <span class="bold">Birth Sex</span> <form:radiobutton id="BIRTH_SEX1"  path="stdNycEharsDoc.birthSex"  value="M" /> Male  <form:radiobutton id="BIRTH_SEX2"  path="stdNycEharsDoc.birthSex"  value="F" /> Female
                    </div>
                    <br/>
                </div> 
                
                <div class="container borderDotted pTRBL10">
                	<div class="floatLeft ">
						<div class="bold"> Current Gender </div>
						<div  class="">
							<div> <form:radiobutton id="CURRENT_GENDER1"  path="stdNycEharsDoc.currentGender" value="M" /> Male </div>
							<div> <form:radiobutton id="CURRENT_GENDER2"  path="stdNycEharsDoc.currentGender" value="F" /> Female </div>
							<div> <form:radiobutton id="CURRENT_GENDER3"  path="stdNycEharsDoc.currentGender" value="U" />Unknown</div>		
						</div>
					</div>
					<div class="floatLeft ">
						<div class=""> &nbsp; </div>
						<div  class="">
							<div> <form:radiobutton id="CURRENT_GENDER4"  path="stdNycEharsDoc.currentGender" value="X" /> MTF </div>		
							<div> <form:radiobutton id="CURRENT_GENDER5"  path="stdNycEharsDoc.currentGender" value="Y" /> FTM </div>		
							<div> <form:radiobutton id="CURRENT_GENDER6"  path="stdNycEharsDoc.currentGender" value="T" /> Unspecified </div>														
						</div>
					</div>   
				
                </div>  
        	</div>
        	<div class="fieldHolder35">
        		<div class="container borderDotted pTRBL10">
        			<div>
        			 <span class="bold">Ethnicity  </br> Hispanic or Latino<br></span>
                      <form:radiobutton id="ETHNICITY1"  path="stdNycEharsDoc.ethnicity"  value="01" /> Yes<br>
                      <form:radiobutton id="ETHNICITY2"  path="stdNycEharsDoc.ethnicity"  value="02" /> No<br>
                      <form:radiobutton id="ETHNICITY3"  path="stdNycEharsDoc.ethnicity"  value="09" /> Unknown
                    </div>
                      <br/>
        		</div>
        		<div class="container borderDotted pTRBL10">
        			<div>
        			 <span class="bold">Country of birth</span>
        			 <span class="mL10">
	        			 <form:select id="BIRTH_COUNTRY"  path="stdNycEharsDoc.birthCountry" cssClass="standardInputSmallText " title="Birth Country">
									<form:option value=""></form:option>
									<form:options items="${sessionScope.countryMap}"/>
									<form:option value="OTH">Other</form:option>
									<form:option value="UNK">Unknown</form:option>
								</form:select>
					  </span>
					 </div>
					  <br/>
        		</div>
        		<div class="container borderDotted pTRBL10">
        			<div class="mT5 container">
        				<div class="fieldHolder30">    		
	                    	<span class="bold">Received Date</span>
	                    </div>
	                    <div class="fieldHolder70">
		                    <span class="">
			                    <fmt:formatDate value="${StdNYCEHARSDocVO.stdNycEharsDoc.createDt}"  type="date" pattern="MM/dd/yyyy" var="theFormattedCreateDate" />
								<form:input id="CREATE_DT"  path="stdNycEharsDoc.createDt" value= "${theFormattedCreateDate}" class="standardInputMedText  readonly" readonly="true"/>
							</span>
						</div>
        			</div>
        			<div class="mT5 container">
        				<div class="fieldHolder30">
								<span class="bold">HIV Diagnosis Date</span>
							</div>
							<div class="fieldHolder70">
			                    <span class="">
				                    <form:input id="HIV_DX_DT"  path="stdNycEharsDoc.hivDxDt" class="standardInputSmallText  readonly" readonly="true"/>
								</span>
							</div>
        			</div>
        			<div class="mT5 container">
        				<div class="fieldHolder30">
								<span class="bold">HIV Category</span>
							</div>
							<div class="fieldHolder70">
		                    	<span class="">
			                    <form:select id="HIV_CATEG"  path="stdNycEharsDoc.hivCateg" cssClass="standardInputMedText " title="HIV Category">
									<form:option value=""></form:option>
									
									<c:forEach items="${hivCategories}" var="category">
										<form:option value="${category.HIV_CATEG}">${category.HIV_CATEG_DESC}</form:option>
									</c:forEach>
									
									
								</form:select>
								</span>
							</div>
        			</div>
        			<div class="mT5 container">
        				<div class="fieldHolder30">
								<span class="bold">AIDS Diagnosis Date</span>
							</div>
							<div class="fieldHolder70">
			                    <span class="">
				                    <form:input id="AIDS_DX_DT"  path="stdNycEharsDoc.aidsDxDt" class="standardInputSmallText  readonly" readonly="true"/>
								</span>
							</div>
        			</div>
        			<div class="mT5 container">
        				<div class="fieldHolder30">
								<span class="bold">AIDS Category</span>
							</div>
							<div class="fieldHolder70 pT5" >
			                    <span class="">
				                    <form:select id="AIDS_CATEG"  path="stdNycEharsDoc.aidsCateg" cssClass="standardInputText " title="AIDS Category">
										<form:option value=""></form:option>
										<c:forEach items="${aidsCategories}" var="category">
											<form:option value="${category.AIDS_CATEG}">${category.AIDS_CATEG_DESC}</form:option>
										</c:forEach>
										
										
									</form:select>
								</span>
							</div>
        			</div>
        			
        			
        		</div>
        		
        	</div>
        	<div class="fLW40">
       			<div class="container borderDotted pTRBL10">
       				<div class="pT8 bold">
                        <span class="riskLabelField">Risk Details</span>
                        <span class="riskValueField">Yes</span>
                        <span class="riskValueField">No</span>
                        <span class="riskValueField">Unknown</span><br>
                    </div>
                    <div class="pT8">
                        <span class="riskLabelField" id="SWM_LABEL">Sex With Male</span>
                        <span class="riskValueField"><form:radiobutton id="SWM1"  path="stdNycEharsDoc.swm"  value="Y" /></span>
                        <span class="riskValueField"><form:radiobutton id="SWM2"  path="stdNycEharsDoc.swm"  value="N" /></span>
                        <span class="riskValueField"><form:radiobutton id="SWM3"  path="stdNycEharsDoc.swm"  value="U" /></span><br>        
                    </div>
                    <div class="clearBoth pT8 pB5">;
                        <span class="riskLabelField" id="SWF_LABEL">Sex With Female</span>
                        <span class="riskValueField"><form:radiobutton id="SWF1"  path="stdNycEharsDoc.swf"  value="Y" /></span>
                        <span class="riskValueField"><form:radiobutton id="SWF2"  path="stdNycEharsDoc.swf"  value="N" /></span>
                        <span class="riskValueField"><form:radiobutton id="SWF3"  path="stdNycEharsDoc.swf"  value="U" /></span>
                    </div>
                    <div class="clearBoth pT8 pB5">
                        <span class="riskLabelField pT8 pB5" id="IDU_LABEL">Injected non-prescription drugs </span>
                        <span class="riskValueField pT8 pB5"><form:radiobutton id="IDU1"  path="stdNycEharsDoc.idu"  value="Y" /></span>
                        <span class="riskValueField pT8 pB5"><form:radiobutton id="IDU2"  path="stdNycEharsDoc.idu"  value="N" /></span>
                        <span class="riskValueField pT8 pB5"><form:radiobutton id="IDU3"  path="stdNycEharsDoc.idu"  value="U" /></span> 
                    </div>
                    <div class="clearBoth pT8 pB5">
                        <span class="riskLabelField pB5" id="CLO_FAC_LABEL">
                        	Received clotting factor for hemophilia/coagulation disorder </span> 
                        <span class="riskValueField pB5"><form:radiobutton id="RECV_CLOTTING_FACTOR1"  path="stdNycEharsDoc.recvClottingFactor"  value="Y" /></span>
                        <span class="riskValueField pB5"><form:radiobutton id="RECV_CLOTTING_FACTOR2"  path="stdNycEharsDoc.recvClottingFactor"  value="N" /></span>   
                        <span class="riskValueField pB5"><form:radiobutton id="RECV_CLOTTING_FACTOR3"  path="stdNycEharsDoc.recvClottingFactor"  value="U" /></span>        
                    </div>  
                    <div class="clearBoth pT8 pB5">
                        <span class="riskLabelField pB5" id="TRAF_LABEL">Received transfusion of blood/blood components (other than clotting factor)  </span>
                        <span class="riskValueField pB5"><form:radiobutton id="RECV_BLOOD_PROD1"  path="stdNycEharsDoc.recvBloodProd"  value="Y" /></span>
                        <span class="riskValueField pB5"><form:radiobutton id="RECV_BLOOD_PROD2"  path="stdNycEharsDoc.recvBloodProd"  value="N" /></span>    
                        <span class="riskValueField pB5"><form:radiobutton id="RECV_BLOOD_PROD3"  path="stdNycEharsDoc.recvBloodProd"  value="U" /></span>     
                    </div>
                   
                    <div class="clearBoth pT8 pB5">
                        <span class="riskLabelField pB5" id="TRANS_LABEL">Received transplant of tissue/organs or artificial insemination </span>
                        <span class="riskValueField pB5"><form:radiobutton id="RECV_TRANSPLANT1"  path="stdNycEharsDoc.recvTransplant"  value="Y" /></span>
                        <span class="riskValueField pB5"><form:radiobutton id="RECV_TRANSPLANT2"  path="stdNycEharsDoc.recvTransplant"  value="N" /></span>    
                        <span class="riskValueField pB5"><form:radiobutton id="RECV_TRANSPLANT3"  path="stdNycEharsDoc.recvTransplant"  value="U" /></span>                        
                    </div>
                    <div class="clearBoth pT8 pB5">
                        <span class="riskLabelField pB5" id="HCW_LABEL">Worked in a healthcare or clinical laboratory setting </span>
                        <span class="riskValueField pB5"><form:radiobutton id="HEALTHCARE_WORKER1"  path="stdNycEharsDoc.healthcareWorker"  value="Y" /></span>
                        <span class="riskValueField pB5"><form:radiobutton id="HEALTHCARE_WORKER2"  path="stdNycEharsDoc.healthcareWorker"  value="N" /></span>    
                        <span class="riskValueField pB5"><form:radiobutton id="HEALTHCARE_WORKER3"  path="stdNycEharsDoc.healthcareWorker"  value="U" /></span>                        
                    </div> 
                    <div class="clearBoth pT8 pB5">
                            <span class="riskLabelField" id="SEXIDU_LABEL">HETEROSEXUAL contact with intravenous/injection drug user </span>
                            <span class="riskValueField pB5"><form:radiobutton id="SEX_PARTNER_IDU1"  path="stdNycEharsDoc.sexPartnerIdu"  value="Y" /></span>
                            <span class="riskValueField pB5"><form:radiobutton id="SEX_PARTNER_IDU2"  path="stdNycEharsDoc.sexPartnerIdu"  value="N" /></span>
                            <span class="riskValueField pB5"><form:radiobutton id="SEX_PARTNER_IDU3"  path="stdNycEharsDoc.sexPartnerIdu"  value="U" /></span>                            
                    </div> 
                    <div class="clearBoth pT8 pB5">
                                              
                            <span class="riskLabelField" id="SEX_BI_LABEL">HETEROSEXUAL contact with bisexual male </span>
                            <span class="riskValueField pB5"><form:radiobutton id="SEX_PARTNER_MSM1"  path="stdNycEharsDoc.sexPartnerMsm"  value="Y" /></span>
                            <span class="riskValueField pB5"><form:radiobutton id="SEX_PARTNER_MSM2"  path="stdNycEharsDoc.sexPartnerMsm"  value="N" /></span>  
                            <span class="riskValueField pB5"><form:radiobutton id="SEX_PARTNER_MSM3"  path="stdNycEharsDoc.sexPartnerMsm"  value="U" /></span>                            
                    </div> 
                    <div class="clearBoth pT8 pB5">
                                               
                            <span class="riskLabelField" id="SEX_TF_LABEL">HETEROSEXUAL contact with transfusion recipient with documented HIV infection</span>
                            <span class="riskValueField pB5"><form:radiobutton id="SEX_PARTNER_TRANSFUSION1"  path="stdNycEharsDoc.sexPartnerTransfusion"  value="Y" /></span>
                            <span class="riskValueField pB5"><form:radiobutton id="SEX_PARTNER_TRANSFUSION2"  path="stdNycEharsDoc.sexPartnerTransfusion"  value="N" /></span>  
                            <span class="riskValueField pB5"><form:radiobutton id="SEX_PARTNER_TRANSFUSION3"  path="stdNycEharsDoc.sexPartnerTransfusion"  value="U" /></span>                            
                    </div> 
                    <div class="clearBoth pT8 pB20">
                       
	                        <span class="riskLabelField pB5" id="SEX_TRANSP_LABEL">HETEROSEXUAL contact with transplant recipient with documented HIV infection </span>
	                        <span class="riskValueField pB5"><form:radiobutton id="SEX_PARTNER_TRANSPLANT1"  path="stdNycEharsDoc.sexPartnerTransplant"  value="Y" /></span>
	                        <span class="riskValueField pB5"><form:radiobutton id="SEX_PARTNER_TRANSPLANT2"  path="stdNycEharsDoc.sexPartnerTransplant"  value="N" /></span>    
	                        <span class="riskValueField pB5"><form:radiobutton id="SEX_PARTNER_TRANSPLANT3"  path="stdNycEharsDoc.sexPartnerTransplant"  value="U" /></span>                        
                    </div> 
       			</div>
       			<br/>
        	</div>
        
        </div>
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
            
    </div>  
    

    </div>     

        
    <input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
</form:form>



<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
		 <div id="button-row" class="button-row documentButtons center">
		 					<c:if test="${StdNYCEHARSDocVO.stateno != null && StdNYCEHARSDocVO.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null}">
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