<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
				<span class=" "><form:input id="DOCUMENT_ID"  path="stdStdConfirmedDoc.documentId" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
	         </div>	

		</div>

				
				
	
		<div class="container w100 pB10">
 	         <div style="float:left; width: 40%">
 	            <div >
 	                <span class=" ml10 bold"><spring:message code="label.stateNo" />: </span>
 	                 <span class="ml10"><form:input id="STATENO"  path="stateno" value="${STATENO}" class="standardInputSmallText readonly" maxlength="10" readonly="true"/> </span>
 	            </div>    
 	          </div>
 	          <div style="float:left;  width: 30%">
 	         	<div >
 	                <span class="ml10 bold"> eHARS Import Date: </span>
 	                <fmt:formatDate value="${stdConfDocVO.stdStdConfirmedDoc.eharsImportDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
 			 		<span class="ml10"><form:input id="EHARS_IMPORT_DATE"  path="stdStdConfirmedDoc.eharsImportDate" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/> </span>
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
					<span class="mL20"> <form:input  path="stdStdConfirmedDoc.fname" id="STD_CONF_FNAME" class="standardInputSmallText required" maxlength="20" title="First Name"/> </span>
					<div class="v_error STD_CONF_FNAME "></div>
				</div>
				<div class="fieldHolder25">
					<span class="bold"> Middle Name</span>
					<span class="mL20"> <form:input  path="stdStdConfirmedDoc.mname" id="STD_CONF_MNAME" class="standardInputSmallText" maxlength="25"  title="Middle Name"/>  </span>
					<div class=" v_error STD_CONF_MNAME "></div>
				</div>
				<div class="fieldHolder25">
					<span class="bold"> Last Name</span>
					<span class="mL20"> <form:input  path="stdStdConfirmedDoc.lname"  id="STD_CONF_LNAME" title="Last Name" class="required standardInputSmallText" maxlength="25"/>  </span>
					<div class=" v_error STD_CONF_LNAME "></div>
				</div>
					
			</div>
			<div class="clearBoth  mL20 pT5 pB20" >
								
				<div class="fieldHolder25"> 
					<span class="bold"> Date of Birth </span>
					<span class="ml10"> <form:input id="DATE_OF_BIRTH"  path="dob" class="dPicker mask standardInputSmallText" datevalidations="completeDate"  maxlength="10" title="Date of Birth"  /> </span>
					<div class=" v_error DATE_OF_BIRTH "></div>
				</div>
				<div class="fieldHolder25"> 
					<span class="bold"> Date of Death </span>
					<span class="ml10"> <form:input id="DATE_OF_DEATH"  path="dateOfDeath" class="dPicker mask standardInputSmallText" datevalidations="completeDate"  maxlength="10" title="Date of Death" /> </span>
					<div class=" v_error DATE_OF_DEATH "></div>
				</div>					
			</div>
			
			<div class="b1" style="margin: 15px 5px 10px 5px; clear: both" id="stdConfDocumentPersonAddressDtls">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc bold"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"> <form:input id="PERSON_ADDRESS"  path="stdStdConfirmedDoc.personAddress" class="standardinputText newAddress widgetAddressFieldsReadonly" maxlength="80" title="Address" />  </span>	 	        
	 	                	            	 	                    
	 	                    <span class="pL30 bold">City</span>
	 	                    <span class="pL10"><form:input id="PERSON_CITY"  path="stdStdConfirmedDoc.personCity" class="standardInputSmallText1 newCity widgetAddressFieldsReadonly"  maxlength="30" title="City" /></span>
	 	                    
	 	                    <span class="pL30 bold">State </span>
	 	                    <span  class="pL10">
								<form:select id="PERSON_STATE"  path="stdStdConfirmedDoc.personState" class="standardInputSmallText1 mR50 newState widgetAddressFieldsReadonly" title="State" >
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.stateMap}"/>
							    </form:select>
							    
							</span>
	 	                    
	 	                    <span class="pL30 bold">Zip </span>
	 	                    <span class="pL10"> 
	 	                       <form:input id="PERSON_ZIP"  path="stdStdConfirmedDoc.personZip" class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5" title="Zip Code" />
	 	                    </span> 
	 	                    
	 	                    <span class="pL30 bold">County </span>
	 	                    <span class="pL10"> 
	 	                       <form:select id="PERSON_COUNTY"    path="stdStdConfirmedDoc.personCounty" class="standardInputSmallText1 newCounty widgetAddressFieldsReadonly" title="County" >
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.countyMap}"/>
							    </form:select>
	 	                    </span>     	                    
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10">
 	            	<span  class="" style="padding-left:59px"><form:input id="PERSON_ADDRESS_RAW"  path="personAddressRaw" class="standardinputText readonly" maxlength="62" readonly="true" title="Raw Address" /></span>
 	              	 
 	              	<span class="pL30 bold">Phone </span>
                    <span class="pL10"> 
                       <form:input id="PERSON_PHONE"  path="stdStdConfirmedDoc.personPhone" title="Phone"  class=" standardInputSmallText" maxlength="20"/>
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
						<form:checkbox id="RACE_WHITE1"  path="stdStdConfirmedDoc.raceWhite" value="1" /> White	
					</div>
					<div>
						<form:checkbox id="RACE_BLACK1"  path="stdStdConfirmedDoc.raceBlack" value="1" /> African American
					</div>
					<div>
						<form:checkbox id="RACE_ASIAN1"  path="stdStdConfirmedDoc.raceAsian" value="1" /> Asian
					</div>
					<div>
						<form:checkbox id="RACE_PACIFIC1"  path="stdStdConfirmedDoc.racePacific" value="1" /> Pacific
					</div>				
					<div>
						<form:checkbox id="RACE_INDIAN1"  path="stdStdConfirmedDoc.raceIndian" value="1" /> American Indian/ Alaskan Native
					</div>				
					<div>
						<form:checkbox id="RACE_OTHER1"  path="stdStdConfirmedDoc.raceOther" value="1" /> Other 
					</div>
					<div>
						<form:checkbox id="RACE_UNKNOWN1"  path="stdStdConfirmedDoc.raceUnknown" value="1" /> Unknown
					</div>
				</div>
				
				
				
				<div style="border-top: dotted 1px;" class="pT10 pL10 pB10">	
					<span class="bold">Ethnicity (Hispanic or Latino)</span>
					<br>
					<form:radiobutton id="ETHNICITY1"  path="stdStdConfirmedDoc.ethnicity" value="01" /> Yes <br>
					<form:radiobutton id="ETHNICITY2"  path="stdStdConfirmedDoc.ethnicity" value="02" /> No <br>
					<form:radiobutton id="ETHNICITY3"  path="stdStdConfirmedDoc.ethnicity" value="09" /> Unknown	
				</div>
								
				<div style="border-top: dotted 1px; border-bottom: dotted 1px" class="pT10 pL10 pB10"> 
					<div > <span class="bold">Birth Sex </span></div>
					<div  class="">
						<div> <form:radiobutton id="BIRTH_SEX1"  path="stdStdConfirmedDoc.birthSex" value="M" /> Male </div>
						<div> <form:radiobutton id="BIRTH_SEX2"  path="stdStdConfirmedDoc.birthSex" value="F" /> Female </div>		
					</div>
				</div>
				<div style="padding:15px 0 10px 10px; border-top: dotted 1px;">
						<div class="bold"> Current Gender </div>
						<div  class="">
							<div> <form:radiobutton id="CURRENT_GENDER1"  path="stdStdConfirmedDoc.currentGender" value="M" /> Male </div>
							<div> <form:radiobutton id="CURRENT_GENDER2"  path="stdStdConfirmedDoc.currentGender" value="F" /> Female </div>
							<div> <form:radiobutton id="CURRENT_GENDER3"  path="stdStdConfirmedDoc.currentGender" value="U" />Unknown</div>		
						</div>
						<div class="bold"> Transgender </div>
						<div  class="">
							<div> <form:radiobutton id="CURRENT_GENDER4"  path="stdStdConfirmedDoc.currentGender" value="X" /> MTF </div>		
							<div> <form:radiobutton id="CURRENT_GENDER5"  path="stdStdConfirmedDoc.currentGender" value="Y" /> FTM </div>		
							<div> <form:radiobutton id="CURRENT_GENDER6"  path="stdStdConfirmedDoc.currentGender" value="A" /> Additional gender identity </div>														
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
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_MALE1"  path="stdStdConfirmedDoc.riskSexMale" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_MALE2"  path="stdStdConfirmedDoc.riskSexMale" value="N" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_MALE3"  path="stdStdConfirmedDoc.riskSexMale" value="D" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_MALE4"  path="stdStdConfirmedDoc.riskSexMale" value="R" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_MALE5"  path="stdStdConfirmedDoc.riskSexMale" value="U" /></span>              
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_MALE6"  path="stdStdConfirmedDoc.riskSexMale" value="O" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_MALE7"  path="stdStdConfirmedDoc.riskSexMale" value="X" /></span><br>                
                                        </div>
                                        <div class="clearBoth pT8">
                                                <span class="riskLabelField4 bold">Sex With Female</span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_FEMALE1"  path="stdStdConfirmedDoc.riskSexFemale" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_FEMALE2"  path="stdStdConfirmedDoc.riskSexFemale" value="N" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_FEMALE3"  path="stdStdConfirmedDoc.riskSexFemale" value="D" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_FEMALE4"  path="stdStdConfirmedDoc.riskSexFemale" value="R" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_FEMALE5"  path="stdStdConfirmedDoc.riskSexFemale" value="U" /></span>                                                
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_FEMALE6"  path="stdStdConfirmedDoc.riskSexFemale" value="O" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_FEMALE7"  path="stdStdConfirmedDoc.riskSexFemale" value="X" /></span>
                                        </div>
                                        <div class="clearBoth pT8 pB5">
                                                <span class="riskLabelField4 bold">Sex With Transgender </span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_TRANSGENDER1"  path="stdStdConfirmedDoc.riskSexTransgender" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_TRANSGENDER2"  path="stdStdConfirmedDoc.riskSexTransgender" value="N" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_TRANSGENDER3"  path="stdStdConfirmedDoc.riskSexTransgender" value="D" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_TRANSGENDER4"  path="stdStdConfirmedDoc.riskSexTransgender" value="R" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_TRANSGENDER5"  path="stdStdConfirmedDoc.riskSexTransgender" value="U" /></span>                                                 
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_TRANSGENDER6"  path="stdStdConfirmedDoc.riskSexTransgender" value="O" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_TRANSGENDER7"  path="stdStdConfirmedDoc.riskSexTransgender" value="X" /></span> 
                                        </div>  
                                        <div class="clearBoth pT8 pB5">
                                                <span class="riskLabelField4 bold">Sex With Anonymous </span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_ANONYMOUS1"  path="stdStdConfirmedDoc.riskSexAnonymous" value="Y" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_ANONYMOUS2"  path="stdStdConfirmedDoc.riskSexAnonymous" value="N" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_ANONYMOUS3"  path="stdStdConfirmedDoc.riskSexAnonymous" value="D" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_ANONYMOUS4"  path="stdStdConfirmedDoc.riskSexAnonymous" value="R" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_ANONYMOUS5"  path="stdStdConfirmedDoc.riskSexAnonymous" value="U" /></span>                                                 
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_ANONYMOUS6"  path="stdStdConfirmedDoc.riskSexAnonymous" value="O" /></span>
                                                <span class="riskValueField"><form:radiobutton id="RISK_SEX_ANONYMOUS7"  path="stdStdConfirmedDoc.riskSexAnonymous" value="X" /></span> 
                                        </div>  
                                        <div class="clearBoth pT10 bold"> <u>Heterosexual sex with:</u> </div>
                                        <ul>
                                                <li class="pT8">
                                                        
                                                                <span class="riskLabelField1 bold">Injection Drug User </span>
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_IDU1"  path="stdStdConfirmedDoc.riskSexIdu" value="Y" /></span>
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_IDU2"  path="stdStdConfirmedDoc.riskSexIdu" value="N" /></span>
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_IDU3"  path="stdStdConfirmedDoc.riskSexIdu" value="D" /></span> 
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_IDU4"  path="stdStdConfirmedDoc.riskSexIdu" value="R" /></span>
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_IDU5"  path="stdStdConfirmedDoc.riskSexIdu" value="U" /></span>                                                                
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_IDU6"  path="stdStdConfirmedDoc.riskSexIdu" value="O" /></span>
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_IDU7"  path="stdStdConfirmedDoc.riskSexIdu" value="X" /></span>
                                                        
                                                </li>
                                                <li class="pT8">                
                                                        
                                                                <span class="riskLabelField1 bold">Partner used Drugs </span>
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_DRUGS1"  path="stdStdConfirmedDoc.riskSexDrugs" value="Y" /></span>
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_DRUGS2"  path="stdStdConfirmedDoc.riskSexDrugs" value="N" /></span>  
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_DRUGS3"  path="stdStdConfirmedDoc.riskSexDrugs" value="D" /></span> 
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_DRUGS4"  path="stdStdConfirmedDoc.riskSexDrugs" value="R" /></span>
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_DRUGS5"  path="stdStdConfirmedDoc.riskSexDrugs" value="U" /></span>                                                                   
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_DRUGS6"  path="stdStdConfirmedDoc.riskSexDrugs" value="O" /></span>
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_DRUGS7"  path="stdStdConfirmedDoc.riskSexDrugs" value="X" /></span>   
                                                        
                                                </li>
                                                <li class="pT8">                
                                                        
                                                                <span class="riskLabelField1 bold">Bisexual male </span>
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_MSM1"  path="stdStdConfirmedDoc.riskSexMsm" value="Y" /></span>
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_MSM2"  path="stdStdConfirmedDoc.riskSexMsm" value="N" /></span>  
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_MSM3"  path="stdStdConfirmedDoc.riskSexMsm" value="D" /></span> 
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_MSM4"  path="stdStdConfirmedDoc.riskSexMsm" value="R" /></span>
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_MSM5"  path="stdStdConfirmedDoc.riskSexMsm" value="U" /></span>                                                                   
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_MSM6"  path="stdStdConfirmedDoc.riskSexMsm" value="O" /></span>
                                                                <span class="riskValueField1"><form:radiobutton id="RISK_SEX_MSM7"  path="stdStdConfirmedDoc.riskSexMsm" value="X" /></span>   
                                                        
                                                </li>
                                                     
                                        </ul>
                                        <div class="clearBoth pT8 pB5">
                                                <span class="riskLabelField4 pB5 bold">Sex Exchanged </span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_SEX_EXCHANGED1"  path="stdStdConfirmedDoc.riskSexExchanged" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_SEX_EXCHANGED2"  path="stdStdConfirmedDoc.riskSexExchanged" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_SEX_EXCHANGED3"  path="stdStdConfirmedDoc.riskSexExchanged" value="D" /></span>                
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_SEX_EXCHANGED4"  path="stdStdConfirmedDoc.riskSexExchanged" value="R" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_SEX_EXCHANGED5"  path="stdStdConfirmedDoc.riskSexExchanged" value="U" /></span>                                                
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_SEX_EXCHANGED6"  path="stdStdConfirmedDoc.riskSexExchanged" value="O" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_SEX_EXCHANGED7"  path="stdStdConfirmedDoc.riskSexExchanged" value="X" /></span>
                                        </div>
                                        
                                        <div class="clearBoth pT8 pB5">
                                                <span class="riskLabelField4 pB5 bold">Sex Without Condom </span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_SEX_WITHOUT_CONDOM1"  path="stdStdConfirmedDoc.riskSexWithoutCondom" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_SEX_WITHOUT_CONDOM2"  path="stdStdConfirmedDoc.riskSexWithoutCondom" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_SEX_WITHOUT_CONDOM3"  path="stdStdConfirmedDoc.riskSexWithoutCondom" value="D" /></span>                
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_SEX_WITHOUT_CONDOM4"  path="stdStdConfirmedDoc.riskSexWithoutCondom" value="R" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_SEX_WITHOUT_CONDOM5"  path="stdStdConfirmedDoc.riskSexWithoutCondom" value="U" /></span>                                                  
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_SEX_WITHOUT_CONDOM6"  path="stdStdConfirmedDoc.riskSexWithoutCondom" value="O" /></span>              
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_SEX_WITHOUT_CONDOM7"  path="stdStdConfirmedDoc.riskSexWithoutCondom" value="X" /></span>                
                                        </div> 
                                                                           
                                        <div class="clearBoth pT8 pB5">
                                                <span class="riskLabelField4 pB5 bold">Used Drugs </span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_DRUGS_USED1"  path="stdStdConfirmedDoc.riskDrugsUsed" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_DRUGS_USED2"  path="stdStdConfirmedDoc.riskDrugsUsed" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_DRUGS_USED3"  path="stdStdConfirmedDoc.riskDrugsUsed" value="D" /></span>                
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_DRUGS_USED4"  path="stdStdConfirmedDoc.riskDrugsUsed" value="R" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_DRUGS_USED5"  path="stdStdConfirmedDoc.riskDrugsUsed" value="U" /></span>                
                                        </div>  
                                        
                                        <div class="clearBoth pT8 pB5">
                                                <span class="riskLabelField4 pB5 bold">Used drugs while Incarcerated </span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_DRUG_INCARCERATED1"  path="stdStdConfirmedDoc.riskDrugIncarcerated" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_DRUG_INCARCERATED2"  path="stdStdConfirmedDoc.riskDrugIncarcerated" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_DRUG_INCARCERATED3"  path="stdStdConfirmedDoc.riskDrugIncarcerated" value="D" /></span>                
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_DRUG_INCARCERATED4"  path="stdStdConfirmedDoc.riskDrugIncarcerated" value="R" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_DRUG_INCARCERATED5"  path="stdStdConfirmedDoc.riskDrugIncarcerated" value="U" /></span>                
                                        </div>  
                                        <div class="clearBoth pT8 pB5">
                                                <span class="riskLabelField4 pB5 bold">Has drug Paraphenalia </span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_DRUG_PARAPHENALIA1"  path="stdStdConfirmedDoc.riskDrugParaphenalia" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_DRUG_PARAPHENALIA2"  path="stdStdConfirmedDoc.riskDrugParaphenalia" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_DRUG_PARAPHENALIA3"  path="stdStdConfirmedDoc.riskDrugParaphenalia" value="D" /></span>                
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_DRUG_PARAPHENALIA4"  path="stdStdConfirmedDoc.riskDrugParaphenalia" value="R" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_DRUG_PARAPHENALIA5"  path="stdStdConfirmedDoc.riskDrugParaphenalia" value="U" /></span>                
                                        </div> 
                                        <div class="clearBoth pT8 pB5">
                                                <span class="riskLabelField4 pB5 bold">Drug Injection </span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_IDU1"  path="stdStdConfirmedDoc.riskIdu" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_IDU2"  path="stdStdConfirmedDoc.riskIdu" value="N" /></span>       
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_IDU3"  path="stdStdConfirmedDoc.riskIdu" value="D" /></span>                
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_IDU4"  path="stdStdConfirmedDoc.riskIdu" value="R" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_IDU5"  path="stdStdConfirmedDoc.riskIdu" value="U" /></span>        
                                                        
                                        </div>  
                                        <div class="clearBoth pT8">
                                                <span class="riskLabelField4 bold">Other Risk </span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_OTHER1"  path="stdStdConfirmedDoc.riskOther" value="Y" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_OTHER2"  path="stdStdConfirmedDoc.riskOther" value="N" /></span>    
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_OTHER3"  path="stdStdConfirmedDoc.riskOther" value="D" /></span>                
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_OTHER4"  path="stdStdConfirmedDoc.riskOther" value="R" /></span>
                                                <span class="riskValueField pB5"><form:radiobutton id="RISK_OTHER5"  path="stdStdConfirmedDoc.riskOther" value="U" /></span>     
                                        </div><br>  
                                        
                                        <div class="clearBoth pT8">
											<span class="riskLabelField3 bold">&nbsp;</span>
											<span class="riskValueField3"><form:input id="RISK_OTHER_TEXT"  path="stdStdConfirmedDoc.riskOtherText" class="standardInputSmallText"  maxlength="30" title="Risk other text"/></span>
										</div>	
                                </div>
				
			</div>
			<div style="float:left; width:26% ; padding-left:10px; " >
				<div class="mT5 container">
					<div style="float:left; width:35%">
						<span class="bold">CASE Key</span>
					</div>
					<div style="float:left">
						<form:input  path="stdStdConfirmedDoc.casekey" id="STD_CONF_CASEKEY" class="standardInputMedText " title="CASE Key"   maxlength="7"/>
					</div>	
					<div class="v_error STD_CONF_CASEKEY "></div>
				</div>	
				<div class="mT5 container">
					<div style="float:left; width:35%">
						<span class="bold">HINSERIALNO</span>
					</div>
					<div style="float:left">
						<form:input  path="stdStdConfirmedDoc.hinserialno" id="STD_CONF_HINSERIALNO" class="standardInputMedText " title="HINSERIALNO"   maxlength="10"/>
					</div>	
					<div class="v_error STD_CONF_HINSERIALNO "></div>
				</div>	
				<div class="mT5 container">
					<div style="float:left; width:50%">
						<span class="bold">Drug used list</span>
						<div>
							<form:checkbox id="RISK_DRUG_USED_CRACK1"  path="stdStdConfirmedDoc.riskDrugUsedCrack" value="Y" /> Crack	
						</div>
						<div>
							<form:checkbox id="RISK_DRUG_USED_COCAINE1"  path="stdStdConfirmedDoc.riskDrugUsedCocaine" value="Y" /> Cocaine
						</div>
						<div>
							<form:checkbox id="RISK_DRUG_USED_HEROIN1"  path="stdStdConfirmedDoc.riskDrugUsedHeroin" value="Y" /> Heroin
						</div>
						<div>
							<form:checkbox id="RISK_DRUG_USED_METHAMPHETAMINES1"  path="stdStdConfirmedDoc.riskDrugUsedMethamphetamines" value="Y" /> Methamphetamines
						</div>				
						<div>
							<form:checkbox id="RISK_DRUG_USED_NITRATES_POPPERS1"  path="stdStdConfirmedDoc.riskDrugUsedNitratesPoppers" value="Y" /> Poppers
						</div>				
						<div>
							<form:checkbox id="RISK_DRUG_USED_ED_MEDICATIONS1"  path="stdStdConfirmedDoc.riskDrugUsedEdMedications" value="Y" /> ED Medications 
						</div>
						<div>
							<form:checkbox id="RISK_DRUG_USED_OTHER1"  path="stdStdConfirmedDoc.riskDrugUsedOther" value="Y" /> Other
						</div>
						<div>
							<form:checkbox id="RISK_DRUG_USED_MARIJUANA1"  path="stdStdConfirmedDoc.riskDrugUsedMarijuana" value="Y" /> Marijuana
						</div>
						<div>
							<form:checkbox id="RISK_DRUG_USED_PRESCRIPTION_PAINKILLERS1"  path="stdStdConfirmedDoc.riskDrugUsedPrescriptionPainkillers" value="Y" /> Painkillers
						</div>
						<div>
							<form:checkbox id="RISK_DRUG_USED_ALCOHOL1"  path="stdStdConfirmedDoc.riskDrugUsedAlcohol" value="Y" /> Alcohol
						</div>
						<div>
							<form:checkbox id="RISK_DRUG_USED_BENZODIAZEPINES1"  path="stdStdConfirmedDoc.riskDrugUsedBenzodiazepines" value="Y" /> Benzodiazepines
						</div>
						<div>
							<form:checkbox id="RISK_DRUG_USED_PRESCRIPTION_OPIODS1"  path="stdStdConfirmedDoc.riskDrugUsedPrescriptionOpiods" value="Y" /> Opiods
						</div>
					</div>					
				</div>
				<div class="mT5 container">
					<div style="float:left; width:35%">
						<span class="bold">Other Drugs</span>
					</div>
					<div style="float:left">
						<form:input id="RISK_DRUG_OTHER_TEXT"  path="stdStdConfirmedDoc.riskDrugOtherText" class="standardInputMedText " title="Other Drugs"   maxlength="50"/>
					</div>
				</div>

				<div class="mT5 container">
					<div style="float:left; width:35%">
						<span class="bold">900 Test</span>
					</div>
					<div style="float:left">
						<form:select id="NINEHUNDREDTEST"  path="stdStdConfirmedDoc.ninehundredtest" class="standardInputMedText " title="900 Test"   maxlength="50">
							<form:option value="" ></form:option>
							<c:forEach items="${documentRefData}" var="drugDetails">
								<c:if test="${drugDetails.refColumn == 'X900TEST' }">
									<form:option value="${drugDetails.lookupCd}" label="${drugDetails.lookupName}" name="${drugDetails.lookupName}"></form:option>
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
						<form:select id="TESTTECHNOLOGY"  path="stdStdConfirmedDoc.testtechnology" class="standardInputMedText " title="Test Technology"   maxlength="50">
							<form:option value="" ></form:option>
							<c:forEach items="${documentRefData}" var="drugDetails">
								<c:if test="${drugDetails.refColumn == 'TEST_TECHNOLOGY' }">
									<form:option value="${drugDetails.lookupCd}" label="${drugDetails.lookupName}" name="${drugDetails.lookupName}"></form:option>
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
						<form:select id="X900POSITIVE"  path="stdStdConfirmedDoc.x900positive" class="standardInputMedText " title="900 Positive"   maxlength="50">
							<form:option value="" ></form:option>
							<c:forEach items="${documentRefData}" var="drugDetails">
								<c:if test="${drugDetails.refColumn == 'X900POSITIVE' }">
									<form:option value="${drugDetails.lookupCd}" label="${drugDetails.lookupName}" name="${drugDetails.lookupName}"></form:option>
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
						<form:input id="X900DXDATE"  path="x900dxdate" class="standardInputMedText " title="900 Dignosis Date"   maxlength="50"/>
					</div>
				</div>				
				<div class="mT5 container">
					<div style="float:left; width:35%">
						<span class="bold">Current 900 Test Result</span>
					</div>
					<div style="float:left">
						<form:select id="CURRENT900TESTRESULT"  path="stdStdConfirmedDoc.current900testresult" class="standardInputMedText " title="900 Test Result">
							<form:option value="" ></form:option>
							<c:forEach items="${documentRefData}" var="drugDetails">
								<c:if test="${drugDetails.refColumn == 'CURRENT900TESTRESULT' }">
									<form:option value="${drugDetails.lookupCd}" label="${drugDetails.lookupName}" name="${drugDetails.lookupName}"></form:option>
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
						<form:select id="DISEASE"  path="stdStdConfirmedDoc.disease" class="standardInputMedText " title="Disease">
							<form:option value="" ></form:option>
							<c:forEach items="${documentRefData}" var="drugDetails">
								<c:if test="${drugDetails.refColumn == 'DISEASE' }">
									<form:option value="${drugDetails.lookupCd}" label="${drugDetails.lookupName}" name="${drugDetails.lookupName}"></form:option>
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
						<form:input id="STD_CONF_DIAG_DATE"  path="diagDate" class="dPicker mask standardInputMedText " datevalidations="completeDate" title="Diagnosis Date" maxlength="10" />
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
					<span class="mL10 bold"> <spring:message code="label.firstname" /></span><span> <form:input id="PROV_FNAME"  path="stdStdConfirmedDoc.provFname" class="standardInputMedText providerFields" title="First Name" maxlength="48"/> </span> 
					<span class="mL10 bold"> <spring:message code="label.lastname" /></span><span> <form:input id="PROV_LNAME"  path="stdStdConfirmedDoc.provLname" class="standardInputMedText providerFields" title="Last Name"  maxlength="48" /> </span>
					</br>
				</div>
				<div style="clear:both"></div>
				<div class="b1" style="margin: 15px 5px 10px 0px">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc bold"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"> <form:input id="PROV_ADDRESS"  path="stdStdConfirmedDoc.provAddress" class="standardinputText newAddress widgetAddressFieldsReadonly" maxlength="100" title="Address" />  </span>	 	        
	 	                	            	 	                    
	 	                    <span class="pL30 bold">City</span>
	 	                    <span class="pL10"><form:input id="PROV_CITY"  path="stdStdConfirmedDoc.provCity" class="oAdrDlg standardInputSmallText1 newCity widgetAddressFieldsReadonly" title="City" maxlength="60"/></span>
	 	                    
	 	                    <span class="pL30 bold">State </span>
	 	                    <span  class="pL10">
								<form:select id="PROVIDER_STATE"  path="stdStdConfirmedDoc.provState" class="oAdrDlg standardInputSmallText1 newState widgetAddressFieldsReadonly" title="State" >
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.stateMap}"/>
							    </form:select>
							</span>
	 	                    
	 	                    <span class="pL30 bold">Zip </span>
	 	                    <span class="pL10"> 
	 	                       <form:input id="PROV_ZIP"  path="stdStdConfirmedDoc.provZip" class="oAdrDlg nOnly  standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5" title="Zip Code" />
	 	                    </span> 
	 	                    
	 	                    <span class="pL30 bold">County </span>
	 	                    <span class="pL10"> 
	 	                       <form:select id="PROV_COUNTY"    path="stdStdConfirmedDoc.provCounty" class="oAdrDlg standardInputSmallText1 newCounty widgetAddressFieldsReadonly" title="County" >
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.countyMap}"/>
							    </form:select>
	 	                    </span>     	                    
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
	 	                </span>
	 	            </div>
	 	            <div class="personInfo pT10 pTRBL10">
	 	            	<span  class="" style="padding-left:59px"><form:input id="PROV_ADDRESS_RAW"  path="provAddressRaw" class="standardinputText readonly" maxlength="100" readonly="true" title="Raw Address" /></span>
	 	              	
	 	              	<span class="pL30 bold">Phone </span>
 	                    <span class="pL10"> 
 	                       <form:input id="PROV_PHONE"  path="stdStdConfirmedDoc.provPhone" class="standardInputMedText mR20" maxlength="16" title="Provider Phone" />
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
				<span>  <form:textarea  path="stdStdConfirmedDoc.fieldnote" id="FIELDNOTE" ROWS="8" COLS="165" maxlength="255" title="Field Notes" class=""/> </span>
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
				<c:when test="${stdConfDocVO.stateno != null && stdConfDocVO.stateno != ''}">
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
