<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>

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
		
					
			<c:if test="${StdNYCEHARSDocVO.PARENT_DOCUMENT_ID != null &&  StdNYCEHARSDocVO.PARENT_DOCUMENT_ID != 0}">

				<div  class="floatLeft" id="parentDocumentIdSection">
		            <span class=" parentIdSectionLabel bold">Parent DocumentId: </span>
					<span class="parentIdSectionValue "><a href="#" onclick="loadDocumentDlg(${StdNYCEHARSDocVO.PARENT_DOCUMENT_ID}, '${param.documentType}')">${StdNYCEHARSDocVO.PARENT_DOCUMENT_ID}</a></span>
					
		         </div>	
	         </c:if>


		</div>
		
		
    <div class="container pB10 w100 mT10">
            <div style="float:left; width: 40%">
	            <div>
	                <span class="ml10"> <spring:message code="label.stateNo" />: </span>
	                <span class="ml10" style=""><form:input path="STATENO" class="standardInputSmallText readonly" /> </span>
	            </div>    
            </div>
            <div style="float:left; width: 30%">
 	         	<div>
 	                <span class="mL10 bold">eHARS Import Date: </span>
					<span class="ml10 ">
						<fmt:formatDate value="${StdNYCEHARSDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
						<form:input path="EHARS_IMPORT_DATE" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/>
					</span>
 	            </div> 
  
            </div>
            <div style="float:left; width: 30%; text-align:right">
 	            <div>
 	                <span class=" ml10 bold">	Document ID: </span>
 			 		<span class="ml10"><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/>
 			 			
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
                     <span class="pL5"><form:input path="FNAME" id="NYC_DEMO_FNAME" class="standardInputMedText " title="First Name" maxLength="25"/></span>
                     <div class="pL10 v_error NYC_DEMO_FNAME "></div> 
                 </div>
                 <div class="fieldHolder25">
                    	<span class="pL10">Middle Name </span> 
                    	<span  class="pL5"><form:input path="MNAME" id="NYC_DEMO_MNAME" class="standardInputMedText"  title="Middle Name" maxLength="25"/></span>
                    	<div class="pL10 v_error NYC_DEMO_MNAME "></div>
                    </div>
                 <div class="fieldHolder25">
                    	<span class="pL10">Last Name </span> 
                    	<span  class="pL5"><form:input path="LNAME" id="NYC_DEMO_LNAME" class="standardInputMedText " title="Last Name" maxLength="30"/></span>
                    	<div class="pL10 v_error NYC_DEMO_LNAME "></div>
                    </div>
                
                <br><br>
                
                
        </div>
     
        <div class="personInfo pTRBL10">
        	<div class="fieldHolder25">
                <span class=" bold">Vital Status</span>
                <span class="pL10">
                	<form:radiobutton path="VITAL_STATUS" value="1" /> Alive
                	<form:radiobutton path="VITAL_STATUS" value="2" /> Dead
                </span>
             </div>
             <div class="fieldHolder25">
                <span class="pL10 bold">Date of Birth</span>
                <span class="pL10"><form:input path="DOB_TEXT" id="DATE_OF_BIRTH"  class="standardInputSmallText mask dPicker" datevalidations="completeDate" maxlength="10" title="Date of Birth"/></span>
                <div class="pL10 v_error DATE_OF_BIRTH "></div>
             </div>
             <div class="fieldHolder25">   
                <span class="pL10 bold">Date of Death</span>
                <span class="pL10"><form:input path="DOD_TEXT" id="DATE_OF_DEATH"  class="standardInputSmallText mask dPicker" datevalidations="completeDate" maxlength="10" title="Date of Death"/></span>
                <div class="pL10 v_error DATE_OF_DEATH "></div>
             </div>
             <div class="fieldHolder25">
                <span class="pL10 bold">SSN</span>
                <span class="pL10"><form:input path="SSN" id="NYC_DEMO_SSN" class="standardInputSmallText" maxlength="9" title="SSN"/></span>
                <div class="pL10 v_error NYC_DEMO_SSN "></div>
             </div>   
        </div>
         <br/><br/>
        <div class="clearBoth b1 pB20" id="nycEharsDocumentPersonAddressDtls">
            <div class="personInfo pTRBL10">
                <span class="personLabelDoc bold">Current Address </span>
                <span> 
                    <span class="pL10"><form:input path="PERSON_ADDRESS"  class="standardInputMedText  newAddress widgetAddressFieldsReadonly" title="Current Address" maxlength="60"/></span>
                    
                    
                    <span class="pL10 bold">City</span>
                    <span class="pL10"><form:input path="PERSON_CITY"  class="oAdrDlg standardInputSmallText newCity widgetAddressFieldsReadonly" title="City" maxlength="40"/></span>
                    
                    <span class="pL10 bold">State </span>
                    <span  class="pL10"><form:input path="PERSON_STATE"  class="oAdrDlg standardInputSmallText newState widgetAddressFieldsReadonly" style="width:50px" title="State" maxlength="2"/></span>
                    
                    <span class="pL10 bold">Zip </span>
                    <span class="pL10"> 
                        <form:input path="PERSON_ZIP"  class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5" title="Zip Code"/>
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
            		<div class="fieldHolder25">
                    	<span class="" style="padding-left:103px"><form:input path="rawAddress"  class="standardInputMedText readonly" title="Raw Current Address"/></span>
                    	
                    	<br>
	                	<span class="labelunderneath3"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
                    </div>
                    <div class="fieldHolder25">
		                <span class="pL30 bold">Address Date</span>
		                <span class="pL10"><form:input path="PERSON_ADDRESS_DATE" id="NYC_DEMO_PERSON_ADDRESS_DATE" style="width:115px" class="standardInputSmallText mask dPicker" datevalidations="partialDate" maxlength="10" title="Address Date"/></span>
		                
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
                        <form:checkbox path="RACE_WHITE"  value="1" /> White 
                    </div>
                    <div>
                        <form:checkbox path="RACE_BLACK"  value="1" /> African American
                    </div>
                    <div>
                        <form:checkbox path="RACE_ASIAN"  value="1" /> Asian
                    </div>
                    <div>
                        <form:checkbox path="RACE_INDIAN"  value="1" /> American Indian/Alaskan Native
                    </div>
                    <div>
                        <form:checkbox path="RACE_PACIFIC"  value="1" /> Pacific
                    </div>
                    <br/>
                </div> 
                <div class="borderDotted pTRBL10 "  >   
                    <div>
                        <span class="bold">Birth Sex</span> <form:radiobutton path="BIRTH_SEX"  value="M" /> Male  <form:radiobutton path="BIRTH_SEX"  value="F" /> Female
                    </div>
                    <br/>
                </div> 
                
                <div class="container borderDotted pTRBL10">
                	<div class="floatLeft ">
						<div class="bold"> Current Gender </div>
						<div  class="">
							<div> <form:radiobutton path="CURRENT_GENDER" value="M" /> Male </div>
							<div> <form:radiobutton path="CURRENT_GENDER" value="F" /> Female </div>
							<div> <form:radiobutton path="CURRENT_GENDER" value="U" />Unknown</div>		
						</div>
					</div>
					<div class="floatLeft ">
						<div class=""> &nbsp; </div>
						<div  class="">
							<div> <form:radiobutton path="CURRENT_GENDER" value="X" /> MTF </div>		
							<div> <form:radiobutton path="CURRENT_GENDER" value="Y" /> FTM </div>		
							<div> <form:radiobutton path="CURRENT_GENDER" value="T" /> Unspecified </div>														
						</div>
					</div>   
				
                </div>  
        	</div>
        	<div class="fieldHolder35">
        		<div class="container borderDotted pTRBL10">
        			<div>
        			 <span class="bold">Ethnicity  </br> Hispanic or Latino<br></span>
                      <form:radiobutton path="ETHNICITY"  value="01" /> Yes<br>
                      <form:radiobutton path="ETHNICITY"  value="02" /> No<br>
                      <form:radiobutton path="ETHNICITY"  value="09" /> Unknown
                    </div>
                      <br/>
        		</div>
        		<div class="container borderDotted pTRBL10">
        			<div>
        			 <span class="bold">Country of birth</span>
        			 <span class="mL10">
	        			 <form:select path="BIRTH_COUNTRY" cssClass="standardInputSmallText " title="Birth Country">
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
			                    <fmt:formatDate value="${StdNYCEHARSDocVO.CREATE_DT}"  type="date" pattern="MM/dd/yyyy" var="theFormattedCreateDate" />
								<form:input path="CREATE_DT" value= "${theFormattedCreateDate}" class="standardInputMedText  readonly" readonly="true"/>
							</span>
						</div>
        			</div>
        			<div class="mT5 container">
        				<div class="fieldHolder30">
								<span class="bold">HIV Diagnosis Date</span>
							</div>
							<div class="fieldHolder70">
			                    <span class="">
				                    <form:input path="HIV_DX_DT" class="standardInputSmallText  readonly" readonly="true"/>
								</span>
							</div>
        			</div>
        			<div class="mT5 container">
        				<div class="fieldHolder30">
								<span class="bold">HIV Category</span>
							</div>
							<div class="fieldHolder70">
		                    	<span class="">
			                    <form:select path="HIV_CATEG" cssClass="standardInputMedText " title="HIV Category">
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
				                    <form:input path="AIDS_DX_DT" class="standardInputSmallText  readonly" readonly="true"/>
								</span>
							</div>
        			</div>
        			<div class="mT5 container">
        				<div class="fieldHolder30">
								<span class="bold">AIDS Category</span>
							</div>
							<div class="fieldHolder70 pT5" >
			                    <span class="">
				                    <form:select path="AIDS_CATEG" cssClass="standardInputText " title="AIDS Category">
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
                        <span class="riskValueField"><form:radiobutton path="SWM"  value="Y" /></span>
                        <span class="riskValueField"><form:radiobutton path="SWM"  value="N" /></span>
                        <span class="riskValueField"><form:radiobutton path="SWM"  value="U" /></span><br>        
                    </div>
                    <div class="clearBoth pT8 pB5">;
                        <span class="riskLabelField" id="SWF_LABEL">Sex With Female</span>
                        <span class="riskValueField"><form:radiobutton path="SWF"  value="Y" /></span>
                        <span class="riskValueField"><form:radiobutton path="SWF"  value="N" /></span>
                        <span class="riskValueField"><form:radiobutton path="SWF"  value="U" /></span>
                    </div>
                    <div class="clearBoth pT8 pB5">
                        <span class="riskLabelField pT8 pB5" id="IDU_LABEL">Injected non-prescription drugs </span>
                        <span class="riskValueField pT8 pB5"><form:radiobutton path="IDU"  value="Y" /></span>
                        <span class="riskValueField pT8 pB5"><form:radiobutton path="IDU"  value="N" /></span>
                        <span class="riskValueField pT8 pB5"><form:radiobutton path="IDU"  value="U" /></span> 
                    </div>
                    <div class="clearBoth pT8 pB5">
                        <span class="riskLabelField pB5" id="CLO_FAC_LABEL">
                        	Received clotting factor for hemophilia/coagulation disorder </span> 
                        <span class="riskValueField pB5"><form:radiobutton path="RECV_CLOTTING_FACTOR"  value="Y" /></span>
                        <span class="riskValueField pB5"><form:radiobutton path="RECV_CLOTTING_FACTOR"  value="N" /></span>   
                        <span class="riskValueField pB5"><form:radiobutton path="RECV_CLOTTING_FACTOR"  value="U" /></span>        
                    </div>  
                    <div class="clearBoth pT8 pB5">
                        <span class="riskLabelField pB5" id="TRAF_LABEL">Received transfusion of blood/blood components (other than clotting factor)  </span>
                        <span class="riskValueField pB5"><form:radiobutton path="RECV_BLOOD_PROD"  value="Y" /></span>
                        <span class="riskValueField pB5"><form:radiobutton path="RECV_BLOOD_PROD"  value="N" /></span>    
                        <span class="riskValueField pB5"><form:radiobutton path="RECV_BLOOD_PROD"  value="U" /></span>     
                    </div>
                   
                    <div class="clearBoth pT8 pB5">
                        <span class="riskLabelField pB5" id="TRANS_LABEL">Received transplant of tissue/organs or artificial insemination </span>
                        <span class="riskValueField pB5"><form:radiobutton path="RECV_TRANSPLANT"  value="Y" /></span>
                        <span class="riskValueField pB5"><form:radiobutton path="RECV_TRANSPLANT"  value="N" /></span>    
                        <span class="riskValueField pB5"><form:radiobutton path="RECV_TRANSPLANT"  value="U" /></span>                        
                    </div>
                    <div class="clearBoth pT8 pB5">
                        <span class="riskLabelField pB5" id="HCW_LABEL">Worked in a healthcare or clinical laboratory setting </span>
                        <span class="riskValueField pB5"><form:radiobutton path="HEALTHCARE_WORKER"  value="Y" /></span>
                        <span class="riskValueField pB5"><form:radiobutton path="HEALTHCARE_WORKER"  value="N" /></span>    
                        <span class="riskValueField pB5"><form:radiobutton path="HEALTHCARE_WORKER"  value="U" /></span>                        
                    </div> 
                    <div class="clearBoth pT8 pB5">
                            <span class="riskLabelField" id="SEXIDU_LABEL">HETEROSEXUAL contact with intravenous/injection drug user </span>
                            <span class="riskValueField pB5"><form:radiobutton path="SEX_PARTNER_IDU"  value="Y" /></span>
                            <span class="riskValueField pB5"><form:radiobutton path="SEX_PARTNER_IDU"  value="N" /></span>
                            <span class="riskValueField pB5"><form:radiobutton path="SEX_PARTNER_IDU"  value="U" /></span>                            
                    </div> 
                    <div class="clearBoth pT8 pB5">
                                              
                            <span class="riskLabelField" id="SEX_BI_LABEL">HETEROSEXUAL contact with bisexual male </span>
                            <span class="riskValueField pB5"><form:radiobutton path="SEX_PARTNER_MSM"  value="Y" /></span>
                            <span class="riskValueField pB5"><form:radiobutton path="SEX_PARTNER_MSM"  value="N" /></span>  
                            <span class="riskValueField pB5"><form:radiobutton path="SEX_PARTNER_MSM"  value="U" /></span>                            
                    </div> 
                    <div class="clearBoth pT8 pB5">
                                               
                            <span class="riskLabelField" id="SEX_TF_LABEL">HETEROSEXUAL contact with transfusion recipient with documented HIV infection</span>
                            <span class="riskValueField pB5"><form:radiobutton path="SEX_PARTNER_TRANSFUSION"  value="Y" /></span>
                            <span class="riskValueField pB5"><form:radiobutton path="SEX_PARTNER_TRANSFUSION"  value="N" /></span>  
                            <span class="riskValueField pB5"><form:radiobutton path="SEX_PARTNER_TRANSFUSION"  value="U" /></span>                            
                    </div> 
                    <div class="clearBoth pT8 pB20">
                       
	                        <span class="riskLabelField pB5" id="SEX_TRANSP_LABEL">HETEROSEXUAL contact with transplant recipient with documented HIV infection </span>
	                        <span class="riskValueField pB5"><form:radiobutton path="SEX_PARTNER_TRANSPLANT"  value="Y" /></span>
	                        <span class="riskValueField pB5"><form:radiobutton path="SEX_PARTNER_TRANSPLANT"  value="N" /></span>    
	                        <span class="riskValueField pB5"><form:radiobutton path="SEX_PARTNER_TRANSPLANT"  value="U" /></span>                        
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
		 					<c:if test="${StdNYCEHARSDocVO.STATENO != null && StdNYCEHARSDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
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