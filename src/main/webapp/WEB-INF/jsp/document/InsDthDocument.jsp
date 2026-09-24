<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>

<link href="${staticPath}/css/docs/commonForm.css" rel="stylesheet" type="text/css" />
<script src="${staticPath}/js/docs/docViewers.js?v=${buildDate}"></script>
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
	
	#StdInsDeathDocVO .labelunderneath2{
		padding-left:120px;
	}
	
	#StdInsDeathDocVO .labelunderneath1{
		padding-left:175px;
	}
	
	#StdInsDeathDocVO .labelunderneath3{
		padding-left:160px;
	}
	
	.pT20{
		padding-top: 20px;
	}
	
	#patientInformation{ padding: 0}
	
	

</style>


<form:form modelAttribute="StdInsDeathDocVO" id="StdInsDeathDocVO" cssClass="documentDescription documentFormVO">
<form:errors path="*" cssClass="errorblock" element="div" />

<div id="labDocTab">
   <div style="padding-left:43%; " class="headingCls">
                Insurance Vital Status Document
        </div>
     
     
         <div class="pB20 pT20">
		
					
			<c:if test="${StdInsDeathDocVO.PARENT_DOCUMENT_ID != null &&  StdInsDeathDocVO.PARENT_DOCUMENT_ID != 0}">

				<div  class="floatLeft" id="parentDocumentIdSection">
		            <span class="parentIdSectionLabel bold">Parent DocumentId: </span>
					<span class=" parentIdSectionValue"><a href="#" onclick="loadDocumentDlg(${StdInsDeathDocVO.PARENT_DOCUMENT_ID}, '${param.documentType}')">${StdInsDeathDocVO.PARENT_DOCUMENT_ID}</a></span>
					
		         </div>	
	         </c:if>


		</div>
		
		
    <div class="container pB10 w100 mT10">
            <div style="float:left; width: 40%">
	            <div>
	                <span class="mL10"> <spring:message code="label.stateNo" />: </span>
	                <span class="ml10" style=""><form:input path="STATENO" class="standardInputSmallText readonly"/> </span>
	            </div>    
            </div>
            <div style="float:left; width: 30%">
 	         	<div>
 	                <span class="mL10 bold">eHARS Import Date: </span>
					<span class="mL10 ">
						<fmt:formatDate value="${StdInsDeathDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
						<form:input path="EHARS_IMPORT_DATE" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/>
					</span>
 	            </div> 
            </div>
            <div style="float:left; width: 30%;text-align:right">
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
        
        	<div class="personInfo pTRBL10">
   	     		<div class="fieldHolder25">
                <span class="pL10">First Name </span> 
                <span class="pL5"><form:input path="FNAME" id="INS_VITAL_FNAME" class="standardInputMedText required" title="First Name" maxLength="40"/></span>
                <div class="pL10 v_error INS_VITAL_FNAME "></div> 
            </div>
            <div class="fieldHolder25">
               	<span class="pL10">Middle Name </span> 
               	<span  class="pL5"><form:input path="MNAME" id="INS_VITAL_MNAME" class="standardInputMedText"  title="Middle Name" maxLength="1"/></span>
               	<div class="pL10 v_error INS_VITAL_MNAME "></div>
            </div>
            <div class="fieldHolder25">
               	<span class="pL10">Last Name </span> 
               	<span  class="pL5"><form:input path="LNAME" id="INS_VITAL_LNAME" class="standardInputMedText required" title="Last Name" maxLength="40"/></span>
               	<div class="pL10 v_error INS_VITAL_LNAME "></div>
            </div>
        
            
        </div>
        
        <div class="clearBoth personInfo pT20 pL10">
      		<div class="fieldHolder25">
               <span class=" bold">Date of Birth</span>
                <span class="pL10"><form:input path="DOB_TEXT" id="DATE_OF_BIRTH"  class="standardInputSmallText mask dPicker" datevalidations="completeDate" maxlength="10" title="Date of Birth"/></span>
                <div class="pL10 v_error DATE_OF_BIRTH "></div> 
            </div>
            <div class="fieldHolder25">
                <span class="pL10 bold">Date of Death</span>
                <fmt:formatDate value="${StdInsDeathDocVO.DATE_OF_DEATH}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDateOfDeath" />
                <span class="pL10"><form:input path="DATE_OF_DEATH" value="${theFormattedDateOfDeath}" id="DATE_OF_DEATH"   class="standardInputSmallText mask dPicker" datevalidations="completeDate" maxlength="10" title="Date of Death"/></span>
               	<div class="pL10 v_error DATE_OF_DEATH "></div>
            </div>
            <div class="fieldHolder25">
               <span class="pL10 bold">SSN</span>
                <span class="pL10"><form:input path="SSN" id="INS_VITAL_SSN"  class="standardInputSmallText" maxlength="9" title="SSN"/></span>
               	<div class="pL10 v_error INS_VITAL_SSN "></div>
            </div>
        
        </div>
        

        <!--  Race details -->
        <div class="clearBoth raceRiskReprt">
            <div class="fieldHolder32">
                <div class="container borderField pT10 " style="width: 349px;height:140px">
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
                        <form:checkbox path="RACE_INDIAN"  value="1" /> American Indian/Alaska Native
                    </div>
                    <div>
                        <form:checkbox path="RACE_PACIFIC"  value="1" /> Native Hawaiian or Other Pacific Islander
                    </div>
                    
                    <div>
                        <form:checkbox path="RACE_OTHER"  value="1" /> Other
                    </div>
                    
                    <div>
                        <form:checkbox path="RACE_UNKNOWN"  value="1" /> Unknown
                    </div>
                </div>  
                 
                 
            </div>
            <div class="fieldHolder32 ">
                <div class=" " style="width: 349px;">
                
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
             	   
             	    <div class="borderField pT10" style="height:133px; width:349px">   
             	    	<div class="mT5 container">
							<div style="float:left; width:35%">
								CIN
							</div>
							<div style="float:left">
								<form:input path="MEDICAID_NUMBER"  id="INS_VITAL_MEDICAID_NUMBER" class="standardInputMedText mL5" title="CIN" maxlength="8"/>
							</div>
							<div class="v_error INS_VITAL_MEDICAID_NUMBER "></div>
						</div>	
						
						<div class="mT5 container">
							<div style="float:left; width:35%">
								Deceased Status
							</div>
							<div style="float:left">
								<form:input path="DECEASED_STATUS"  class="standardInputMedText mL5" title="Deceased Status" maxlength="8"/>
							</div>
						</div>	
						
						<div class="mT5 container">
							<div style="float:left; width:35%">
								File Name
							</div>
							<div style="float:left">
								<form:input path="INS_DEATH_FILE_NAME"  class="standardInputMedText mL5 readonly"  maxlength="50" title="File Name"/>
							</div>
						</div>		
            
	                </div>  
	        
                </div>
            </div> 
 
        </div>     
    </div>  
    
    
    </div>     

        
    <input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
</form:form>


<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
		 <div id="button-row" class="button-row documentButtons center">
		 					<c:if test="${StdInsDeathDocVO.STATENO != null && StdInsDeathDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
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