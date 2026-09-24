<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>
<style>
	.riskLabelField{
		width:33% !important;
	}
	
	.riskValueField{
		width:9% !important;
	}
	
	.riskValueField1{
   		 width: 9.8%  !important;
   }
   
   .riskLabelField1 {
	    width: 25%  !important;
	}
</style>
 
 <script src="${staticPath}/js/docs/tbDoc.js?v=${buildDate}"></script>
 <link href="${staticPath}/css/docs/commonForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
 
<form:form modelAttribute="stdConfDocVO" id="stdConfDocVO" cssClass="documentDescription documentFormVO" >		
<form:errors path="*" cssClass="errorblock" element="div"/>

		
		<div style="padding-left:43%; " class="headingCls">
				STD CONFIRMED DOCUMENT
		</div>
		
		
		    <div class="pB30 pT20">
		
					
<%-- 			<c:if test="${stdConfDocVO.PARENT_DOCUMENT_ID != null &&  stdConfDocVO.PARENT_DOCUMENT_ID != 0}"> --%>

<!-- 				<div  class="floatLeft width70Perc" id="parentDocumentIdSection"> -->
<!-- 		            <span class="parentIdSectionLabel bold mL10">Parent DocumentId: </span> -->
<%-- 					<span class="parentIdSectionValue "><a href="#" onclick="loadDocumentDlg(${stdConfDocVO.PARENT_DOCUMENT_ID}, '${param.documentType}')">${stdConfDocVO.PARENT_DOCUMENT_ID}</a></span> --%>
					
<!-- 		         </div>	 -->
<%-- 	         </c:if> --%>
	         
			<div  class="floatRight">
	            <span class="mL10 bold">Document ID: </span>
				<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
	         </div>	

		</div>

				
				
	
		<div class="container w100 pB10">
 	         <div style="float:left; width: 40%">
 	            <div >
 	                <span class=" ml10 bold"><spring:message code="label.stateNo" />: </span>
 	                 <span class="ml10"><form:input path="STATENO" value="${STATENO}" class="standardInputSmallText readonly" maxlength="10" readonly="true"/> </span>
 	            </div>    
 	          </div>
 	          <div style="float:left;  width: 30%">
 	         	<div >
 	                <span class="ml10 bold"> eHARS Import Date: </span>
 	                <fmt:formatDate value="${stdConfDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
 			 		<span class="ml10"><form:input path="EHARS_IMPORT_DATE" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/> </span>
 	            </div>    
 	         </div>
<!--  	         <div style="float:left; width: 30%; text-align: right"> -->
<!--  	            <div > -->
<!--  	                <span class=" ml10">	NYC SHIP Date:  </span> -->
<%--  	                <fmt:formatDate value="${stdTBDocVO.NYC_SHIP_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedNYC_SHIP_DATE_TEXT" ></fmt:formatDate> --%>
<%--  			 		<span class="ml10 "><form:input path="NYC_SHIP_DATE" value="${theFormattedNYC_SHIP_DATE_TEXT}" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span> --%>
<!--  	           </div>                   -->
<!--  	         </div>      -->
 		</div>
 		
 		<jsp:include page="statenoDetails.jsp"></jsp:include>	

		<!--  Person details -->
		
		<div class="container" style="border:1px dotted; width:100%; padding-bottom:5px;">	 
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Person Details</span>
			</div>
			<div class="mL20 mT20 ">
				<div class="fieldHolder25">
					<span class="bold"> First Name</span> 
					<span class="mL20"> <form:input path="FNAME" id="STD_CONF_FNAME" class="standardInputSmallText required" maxlength="20" title="First Name"/> </span>
					<div class="v_error STD_CONF_FNAME "></div>
				</div>
				<div class="fieldHolder25">
					<span class="bold"> Middle Name</span>
					<span class="mL20"> <form:input path="MNAME" id="STD_CONF_MNAME" class="standardInputSmallText" maxlength="25"  title="Middle Name"/>  </span>
					<div class=" v_error STD_CONF_MNAME "></div>
				</div>
				<div class="fieldHolder25">
					<span class="bold"> Last Name</span>
					<span class="mL20"> <form:input path="LNAME"  id="STD_CONF_LNAME" title="Last Name" class="required standardInputSmallText" maxlength="25"/>  </span>
					<div class=" v_error STD_CONF_LNAME "></div>
				</div>
					
			</div>
			<div class="clearBoth  mL20 pT5 pB20" >
								
				<div class="fieldHolder25"> 
					<span class="bold"> Date of Birth </span>
					<span class="ml10"> <form:input id="DATE_OF_BIRTH" path="DOB" class="dPicker mask standardInputSmallText" datevalidations="completeDate"  maxlength="10" title="Date of Birth"  /> </span>
					<div class=" v_error DATE_OF_BIRTH "></div>
				</div>
				<div class="fieldHolder25"> 
					<span class="bold"> Date of Death </span>
					<span class="ml10"> <form:input id="DATE_OF_DEATH" path="DATE_OF_DEATH" class="dPicker mask standardInputSmallText" datevalidations="completeDate"  maxlength="10" title="Date of Death" /> </span>
					<div class=" v_error DATE_OF_DEATH "></div>
				</div>					
			</div>
			
			<div class="b1" style="margin: 15px 5px 10px 5px; clear: both" id="stdConfDocumentPersonAddressDtls">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc bold"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"> <form:input path="PERSON_ADDRESS" class="standardinputText newAddress widgetAddressFieldsReadonly" maxlength="80" title="Address" />  </span>	 	        
	 	                	            	 	                    
	 	                    <span class="pL30 bold">City</span>
	 	                    <span class="pL10"><form:input path="PERSON_CITY" class="standardInputSmallText1 newCity widgetAddressFieldsReadonly"  maxlength="30" title="City" /></span>
	 	                    
	 	                    <span class="pL30 bold">State </span>
	 	                    <span  class="pL10">
								<form:select id="PERSON_STATE"  path ="PERSON_STATE" class="standardInputSmallText1 mR50 newState widgetAddressFieldsReadonly" title="State" >
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.stateMap}"/>
							    </form:select>
							    
							</span>
	 	                    
	 	                    <span class="pL30 bold">Zip </span>
	 	                    <span class="pL10"> 
	 	                       <form:input path="PERSON_ZIP" class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5" title="Zip Code" />
	 	                    </span> 
	 	                    
	 	                    <span class="pL30 bold">County </span>
	 	                    <span class="pL10"> 
	 	                       <form:select id="PERSON_COUNTY"   path="PERSON_COUNTY" class="standardInputSmallText1 newCounty widgetAddressFieldsReadonly" title="County" >
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.countyMap}"/>
							    </form:select>
	 	                    </span>     	                    
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10">
 	            	<span  class="" style="padding-left:59px"><form:input path="PERSON_ADDRESS_RAW" class="standardinputText readonly" maxlength="62" readonly="true" title="Raw Address" /></span>
 	              	 
 	              	<span class="pL30 bold">Phone </span>
                    <span class="pL10"> 
                       <form:input path="PERSON_PHONE" title="Phone"  class=" standardInputSmallText" maxlength="20"/>
                    </span> 
                    <span style="float: right;" class="mr50">
							<a href="javascript:openAddressWidget('stdConfDocumentPersonAddressDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
					</span> 
                    
                    
 	                <br>               
 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
 	                    
 	            </div>
 	        </div>
								  
		</div>
		
		<div id="raceEthnicity" class="container" style="border:1px dotted; ">
			<div style="float:left; width:17%;  border-right:1px dotted; ">
				<div class="pL10 pB10 pT10">				
					<div >
						<span class="bold">Race</span> 
					</div>
					<div>
						<form:checkbox path="RACE_WHITE" value="1" /> White	
					</div>
					<div>
						<form:checkbox path="RACE_BLACK" value="1" /> African American
					</div>
					<div>
						<form:checkbox path="RACE_ASIAN" value="1" /> Asian
					</div>
					<div>
						<form:checkbox path="RACE_PACIFIC" value="1" /> Pacific
					</div>				
					<div>
						<form:checkbox path="RACE_INDIAN" value="1" /> American Indian/ Alaskan Native
					</div>				
					<div>
						<form:checkbox path="RACE_OTHER" value="1" /> Other 
					</div>
					<div>
						<form:checkbox path="RACE_UNKNOWN" value="1" /> Unknown
					</div>
				</div>
				
				
				
				<div style="border-top: dotted 1px;" class="pT10 pL10 pB10">	
					<span class="bold">Ethnicity (Hispanic or Latino)</span>
					<br>
					<form:radiobutton path="ETHNICITY" value="01" /> Yes <br>
					<form:radiobutton path="ETHNICITY" value="02" /> No <br>
					<form:radiobutton path="ETHNICITY" value="09" /> Unknown	
				</div>
								
				<div style="border-top: dotted 1px; border-bottom: dotted 1px" class="pT10 pL10 pB10"> 
					<div > <span class="bold">Birth Sex </span></div>
					<div  class="">
						<div> <form:radiobutton path="BIRTH_SEX" value="M" /> Male </div>
						<div> <form:radiobutton path="BIRTH_SEX" value="F" /> Female </div>		
					</div>
				</div>
				<div style="padding:15px 0 10px 10px; border-top: dotted 1px;">
						<div class="bold"> Current Gender </div>
						<div  class="">
							<div> <form:radiobutton path="CURRENT_GENDER" value="M" /> Male </div>
							<div> <form:radiobutton path="CURRENT_GENDER" value="F" /> Female </div>
							<div> <form:radiobutton path="CURRENT_GENDER" value="U" />Unknown</div>		
						</div>
						<div class="bold"> Transgender </div>
						<div  class="">
							<div> <form:radiobutton path="CURRENT_GENDER" value="X" /> MTF </div>		
							<div> <form:radiobutton path="CURRENT_GENDER" value="Y" /> FTM </div>		
							<div> <form:radiobutton path="CURRENT_GENDER" value="A" /> Additional gender identity </div>														
						</div>
				</div>				
			</div>
			<div style="float:left;  width:56% ; " class="floatLeft">
					<div class="borderField" style="height:500px; width:96.5%">
                                        <div class="pT8">
                                                <span class="riskLabelField4 bold">Risk Details</span>
                                                <span class="riskValueField bold">Yes</span>
                                                <span class="riskValueField bold">No</span>
                                                <span class="riskValueField bold">Didn't Ask</span>
                                                <span class="riskValueField bold">Refused</span>
                                                <span class="riskValueField bold">Unknown</span>                                                
                                                <span class="riskValueField bold">Oral Sex only</span>
                                                <span class="riskValueField bold">Unspecified</span><br>
                                                
                                        </div>  
                                        <div class="clearBoth pT8">
                                                <span class="riskLabelField4 bold">Sex With Male</span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_MALE" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_MALE" value="N" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_MALE" value="D" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_MALE" value="R" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_MALE" value="U" /></span>              
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_MALE" value="O" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_MALE" value="X" /></span><br>                
                                        </div>
                                        <div class="clearBoth pT8">
                                                <span class="riskLabelField4 bold">Sex With Female</span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_FEMALE" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_FEMALE" value="N" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_FEMALE" value="D" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_FEMALE" value="R" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_FEMALE" value="U" /></span>                                                
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_FEMALE" value="O" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_FEMALE" value="X" /></span>
                                        </div>
                                        <div class="clearBoth pT8 pB5">
                                                <span class="riskLabelField4 bold">Sex With Transgender </span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_TRANSGENDER" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_TRANSGENDER" value="N" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_TRANSGENDER" value="D" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_TRANSGENDER" value="R" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_TRANSGENDER" value="U" /></span>                                                 
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_TRANSGENDER" value="O" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_TRANSGENDER" value="X" /></span> 
                                        </div>  
                                        <div class="clearBoth pT8 pB5">
                                                <span class="riskLabelField4 bold">Sex With Anonymous </span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_ANONYMOUS" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_ANONYMOUS" value="N" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_ANONYMOUS" value="D" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_ANONYMOUS" value="R" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_ANONYMOUS" value="U" /></span>                                                 
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_ANONYMOUS" value="O" /></span>
                                                <span class="riskValueField"><form:radiobutton path="RISK_SEX_ANONYMOUS" value="X" /></span> 
                                        </div>  
                                        <div class="clearBoth pT10 bold"> <u>Heterosexual sex with:</u> </div>
                                        <ul>
                                                <li class="pT8">
                                                        
                                                                <span class="riskLabelField1 bold">Injection Drug User </span>
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_IDU" value="Y" /></span>
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_IDU" value="N" /></span>
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_IDU" value="D" /></span> 
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_IDU" value="R" /></span>
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_IDU" value="U" /></span>                                                                
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_IDU" value="O" /></span>
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_IDU" value="X" /></span>
                                                        
                                                </li>
                                                <li class="pT8">                
                                                        
                                                                <span class="riskLabelField1 bold">Partner used Drugs </span>
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_DRUGS" value="Y" /></span>
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_DRUGS" value="N" /></span>  
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_DRUGS" value="D" /></span> 
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_DRUGS" value="R" /></span>
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_DRUGS" value="U" /></span>                                                                   
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_DRUGS" value="O" /></span>
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_DRUGS" value="X" /></span>   
                                                        
                                                </li>
                                                <li class="pT8">                
                                                        
                                                                <span class="riskLabelField1 bold">Bisexual male </span>
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_MSM" value="Y" /></span>
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_MSM" value="N" /></span>  
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_MSM" value="D" /></span> 
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_MSM" value="R" /></span>
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_MSM" value="U" /></span>                                                                   
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_MSM" value="O" /></span>
                                                                <span class="riskValueField1"><form:radiobutton path="RISK_SEX_MSM" value="X" /></span>   
                                                        
                                                </li>
                                                     
                                        </ul>
                                        <div class="clearBoth pT8 pB5">
                                                <span class="riskLabelField4 pB5 bold">Sex Exchanged </span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_SEX_EXCHANGED" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_SEX_EXCHANGED" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_SEX_EXCHANGED" value="D" /></span>                
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_SEX_EXCHANGED" value="R" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_SEX_EXCHANGED" value="U" /></span>                                                
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_SEX_EXCHANGED" value="O" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_SEX_EXCHANGED" value="X" /></span>
                                        </div>
                                        
                                        <div class="clearBoth pT8 pB5">
                                                <span class="riskLabelField4 pB5 bold">Sex Without Condom </span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_SEX_WITHOUT_CONDOM" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_SEX_WITHOUT_CONDOM" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_SEX_WITHOUT_CONDOM" value="D" /></span>                
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_SEX_WITHOUT_CONDOM" value="R" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_SEX_WITHOUT_CONDOM" value="U" /></span>                                                  
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_SEX_WITHOUT_CONDOM" value="O" /></span>              
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_SEX_WITHOUT_CONDOM" value="X" /></span>                
                                        </div> 
                                                                           
                                        <div class="clearBoth pT8 pB5">
                                                <span class="riskLabelField4 pB5 bold">Used Drugs </span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_DRUGS_USED" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_DRUGS_USED" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_DRUGS_USED" value="D" /></span>                
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_DRUGS_USED" value="R" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_DRUGS_USED" value="U" /></span>                
                                        </div>  
                                        
                                        <div class="clearBoth pT8 pB5">
                                                <span class="riskLabelField4 pB5 bold">Used drugs while Incarcerated </span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_DRUG_INCARCERATED" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_DRUG_INCARCERATED" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_DRUG_INCARCERATED" value="D" /></span>                
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_DRUG_INCARCERATED" value="R" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_DRUG_INCARCERATED" value="U" /></span>                
                                        </div>  
                                        <div class="clearBoth pT8 pB5">
                                                <span class="riskLabelField4 pB5 bold">Has drug Paraphenalia </span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_DRUG_PARAPHENALIA" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_DRUG_PARAPHENALIA" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_DRUG_PARAPHENALIA" value="D" /></span>                
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_DRUG_PARAPHENALIA" value="R" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_DRUG_PARAPHENALIA" value="U" /></span>                
                                        </div> 
                                        <div class="clearBoth pT8 pB5">
                                                <span class="riskLabelField4 pB5 bold">Drug Injection </span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_IDU" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_IDU" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_IDU" value="D" /></span>                
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_IDU" value="R" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_IDU" value="U" /></span>        
                                                        
                                        </div>  
                                        <div class="clearBoth pT8">
                                                <span class="riskLabelField4 bold">Other Risk </span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_OTHER" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_OTHER" value="N" /></span>    
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_OTHER" value="D" /></span>                
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_OTHER" value="R" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton path="RISK_OTHER" value="U" /></span>     
                                        </div><br>  
                                        
                                        <div class="clearBoth pT8">
											<span class="riskLabelField3 bold">&nbsp;</span>
											<span class="riskValueField3"><form:input path="RISK_OTHER_TEXT" class="standardInputSmallText"  maxlength="30" title="Risk other text"/></span>
										</div>	
                                </div>
				
			</div>
			<div style="float:left; width:26% ; padding-left:10px; " >
				<div class="mT5 container">
					<div style="float:left; width:35%">
						<span class="bold">CASE Key</span>
					</div>
					<div style="float:left">
						<form:input path="CASEKEY" id="STD_CONF_CASEKEY" class="standardInputMedText " title="CASE Key"   maxlength="7"/>
					</div>	
					<div class="v_error STD_CONF_CASEKEY "></div>
				</div>	
				<div class="mT5 container">
					<div style="float:left; width:35%">
						<span class="bold">HINSERIALNO</span>
					</div>
					<div style="float:left">
						<form:input path="HINSERIALNO" id="STD_CONF_HINSERIALNO" class="standardInputMedText " title="HINSERIALNO"   maxlength="10"/>
					</div>	
					<div class="v_error STD_CONF_HINSERIALNO "></div>
				</div>	
				<div class="mT5 container">
					<div style="float:left; width:50%">
						<span class="bold">Drug used list</span>
						<div>
							<form:checkbox path="RISK_DRUG_USED_CRACK" value="Y" /> Crack	
						</div>
						<div>
							<form:checkbox path="RISK_DRUG_USED_COCAINE" value="Y" /> Cocaine
						</div>
						<div>
							<form:checkbox path="RISK_DRUG_USED_HEROIN" value="Y" /> Heroin
						</div>
						<div>
							<form:checkbox path="RISK_DRUG_USED_METHAMPHETAMINES" value="Y" /> Methamphetamines
						</div>				
						<div>
							<form:checkbox path="RISK_DRUG_USED_NITRATES_POPPERS" value="Y" /> Poppers
						</div>				
						<div>
							<form:checkbox path="RISK_DRUG_USED_ED_MEDICATIONS" value="Y" /> ED Medications 
						</div>
						<div>
							<form:checkbox path="RISK_DRUG_USED_OTHER" value="Y" /> Other
						</div>
						<div>
							<form:checkbox path="RISK_DRUG_USED_MARIJUANA" value="Y" /> Marijuana
						</div>
						<div>
							<form:checkbox path="RISK_DRUG_USED_PRESCRIPTION_PAINKILLERS" value="Y" /> Painkillers
						</div>
						<div>
							<form:checkbox path="RISK_DRUG_USED_ALCOHOL" value="Y" /> Alcohol
						</div>
						<div>
							<form:checkbox path="RISK_DRUG_USED_BENZODIAZEPINES" value="Y" /> Benzodiazepines
						</div>
						<div>
							<form:checkbox path="RISK_DRUG_USED_PRESCRIPTION_OPIODS" value="Y" /> Opiods
						</div>
					</div>					
				</div>
				<div class="mT5 container">
					<div style="float:left; width:35%">
						<span class="bold">Other Drugs</span>
					</div>
					<div style="float:left">
						<form:input path="RISK_DRUG_OTHER_TEXT" class="standardInputMedText " title="Other Drugs"   maxlength="50"/>
					</div>
				</div>

				<div class="mT5 container">
					<div style="float:left; width:35%">
						<span class="bold">900 Test</span>
					</div>
					<div style="float:left">
						<form:select path="NINEHUNDREDTEST" class="standardInputMedText " title="900 Test"   maxlength="50">
							<form:option value="" ></form:option>
							<c:forEach items="${documentRefData}" var="drugDetails">
								<c:if test="${drugDetails.REF_COLUMN == 'X900TEST' }">
									<form:option value="${drugDetails.LOOKUP_CD}" label="${drugDetails.LOOKUP_NAME}" name="${drugDetails.LOOKUP_NAME}"></form:option>
								</c:if>
							</c:forEach>
						
						</form:select>
					</div>
				</div>
				<div class="mT5 container">
					<div style="float:left; width:35%">
						<span class="bold">Test Technology</span>
					</div>
					<div style="float:left">
						<form:select path="TESTTECHNOLOGY" class="standardInputMedText " title="Test Technology"   maxlength="50">
							<form:option value="" ></form:option>
							<c:forEach items="${documentRefData}" var="drugDetails">
								<c:if test="${drugDetails.REF_COLUMN == 'TEST_TECHNOLOGY' }">
									<form:option value="${drugDetails.LOOKUP_CD}" label="${drugDetails.LOOKUP_NAME}" name="${drugDetails.LOOKUP_NAME}"></form:option>
								</c:if>
							</c:forEach>
						</form:select>
					</div>
				</div>				
				<div class="mT5 container">
					<div style="float:left; width:35%">
						<span class="bold">900 Positive</span>
					</div>
					<div style="float:left">
						<form:select path="X900POSITIVE" class="standardInputMedText " title="900 Positive"   maxlength="50">
							<form:option value="" ></form:option>
							<c:forEach items="${documentRefData}" var="drugDetails">
								<c:if test="${drugDetails.REF_COLUMN == 'X900POSITIVE' }">
									<form:option value="${drugDetails.LOOKUP_CD}" label="${drugDetails.LOOKUP_NAME}" name="${drugDetails.LOOKUP_NAME}"></form:option>
								</c:if>
							</c:forEach>
						</form:select>
					</div>
				</div>				
				<div class="mT5 container">
					<div style="float:left; width:35%">
						<span class="bold">900 Dignosis Date</span>
					</div>
					<div style="float:left">
						<form:input path="X900DXDATE" class="standardInputMedText " title="900 Dignosis Date"   maxlength="50"/>
					</div>
				</div>				
				<div class="mT5 container">
					<div style="float:left; width:35%">
						<span class="bold">Current 900 Test Result</span>
					</div>
					<div style="float:left">
						<form:select path="CURRENT900TESTRESULT" class="standardInputMedText " title="900 Test Result">
							<form:option value="" ></form:option>
							<c:forEach items="${documentRefData}" var="drugDetails">
								<c:if test="${drugDetails.REF_COLUMN == 'CURRENT900TESTRESULT' }">
									<form:option value="${drugDetails.LOOKUP_CD}" label="${drugDetails.LOOKUP_NAME}" name="${drugDetails.LOOKUP_NAME}"></form:option>
								</c:if>
							</c:forEach>
						</form:select>
					</div>
				</div>				
				<div class="mT5 container">
					<div style="float:left; width:35%">
						<span class="bold">Disease</span>
					</div>
					<div style="float:left">
						<form:select path="DISEASE" class="standardInputMedText " title="Disease">
							<form:option value="" ></form:option>
							<c:forEach items="${documentRefData}" var="drugDetails">
								<c:if test="${drugDetails.REF_COLUMN == 'DISEASE' }">
									<form:option value="${drugDetails.LOOKUP_CD}" label="${drugDetails.LOOKUP_NAME}" name="${drugDetails.LOOKUP_NAME}"></form:option>
								</c:if>
							</c:forEach>
						</form:select>
					</div>
				</div>			
				<div class="mT5 container">
					<div style="float:left; width:35%">
						<span class="bold">Diagnosis Date</span>
					</div>
					<div style="float:left">
						<form:input id="STD_CONF_DIAG_DATE" path="DIAG_DATE" class="dPicker mask standardInputMedText " datevalidations="completeDate" title="Diagnosis Date" maxlength="10" />
					</div>
					<div class="v_error STD_CONF_DIAG_DATE "></div>
				</div>
			
				
		</div>
		
		</div>
		<div style=" width:100%; border:1px dotted; padding-bottom:5px;" class="container" id="stdConfDocumentProviderDtls">
			<!--  Facility details -->			
			<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Provider Details</span>					
			</div>
			<div class="mLRTB pB20 mT10">
				<div style="float:left;"> 
					<span class="mL10 bold"> <spring:message code="label.firstname" /></span><span> <form:input path="PROV_FNAME" class="standardInputMedText providerFields" title="First Name" maxlength="48"/> </span> 
					<span class="mL10 bold"> <spring:message code="label.lastname" /></span><span> <form:input path="PROV_LNAME" class="standardInputMedText providerFields" title="Last Name"  maxlength="48" /> </span>
					</br>
				</div>
				<div style="clear:both"></div>
				<div class="b1" style="margin: 15px 5px 10px 0px">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc bold"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"> <form:input path="PROV_ADDRESS" class="standardinputText newAddress widgetAddressFieldsReadonly" maxlength="100" title="Address" />  </span>	 	        
	 	                	            	 	                    
	 	                    <span class="pL30 bold">City</span>
	 	                    <span class="pL10"><form:input path="PROV_CITY" class="oAdrDlg standardInputSmallText1 newCity widgetAddressFieldsReadonly" title="City" maxlength="60"/></span>
	 	                    
	 	                    <span class="pL30 bold">State </span>
	 	                    <span  class="pL10">
								<form:select id="PROVIDER_STATE"  path ="PROV_STATE" class="oAdrDlg standardInputSmallText1 newState widgetAddressFieldsReadonly" title="State" >
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.stateMap}"/>
							    </form:select>
							</span>
	 	                    
	 	                    <span class="pL30 bold">Zip </span>
	 	                    <span class="pL10"> 
	 	                       <form:input path="PROV_ZIP" class="oAdrDlg nOnly  standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5" title="Zip Code" />
	 	                    </span> 
	 	                    
	 	                    <span class="pL30 bold">County </span>
	 	                    <span class="pL10"> 
	 	                       <form:select id="PROV_COUNTY"   path="PROV_COUNTY" class="oAdrDlg standardInputSmallText1 newCounty widgetAddressFieldsReadonly" title="County" >
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.countyMap}"/>
							    </form:select>
	 	                    </span>     	                    
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
	 	                </span>
	 	            </div>
	 	            <div class="personInfo pT10 pTRBL10">
	 	            	<span  class="" style="padding-left:59px"><form:input path="PROV_ADDRESS_RAW" class="standardinputText readonly" maxlength="100" readonly="true" title="Raw Address" /></span>
	 	              	
	 	              	<span class="pL30 bold">Phone </span>
 	                    <span class="pL10"> 
 	                       <form:input path="PROV_PHONE" class="standardInputMedText mR20" maxlength="16" title="Provider Phone" />
 	                    </span>  
	 	                    
	 	                 <span style="float: right;" class="mr50">
							<a href="javascript:openAddressWidget('stdConfDocumentProviderDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
						 </span> 	              	
	 	                <br>               
	 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
	 	                    
	 	            </div>
	 	        </div>
					
			</div>																	 
		</div>
		
						
		<div class="mT10">
			<span class=" hdrFont"> Field Notes </span>
			<div  >
				<span>  <form:textarea path="FIELDNOTE" id="FIELDNOTE" ROWS="8" COLS="165" maxlength="255" title="Field Notes" class=""/> </span>
				<div class="v_error rawAcrfDoc_comments"></div>
			</div>
		</div>
		
			
		<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
		<div id="button-row" class="button-row documentButtons" align="center">
		
			<c:choose>
				<c:when  test="${param.hasDeleteFunctionality == true}" >	
						<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
						<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
				</c:when>
				<c:when test="${stdConfDocVO.STATENO != null && stdConfDocVO.STATENO != ''}">
						<c:if test="${userPermissions['90'] != null && nyehmsStatenoDetails.REASON_DESC == null}" >
							<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
						</c:if>
						<c:if test="${userPermissions['100'] != null}" >
<!-- 							<input type="button" id="editDocument"  class="editDocumentBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="confirmEditDocumentFromStateno()" /> -->
						</c:if>
				</c:when>
			</c:choose>

						
			<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Save" onclick="saveForm()"/>	 
			<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />
		</div>
		
		<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />	
</form:form>															
