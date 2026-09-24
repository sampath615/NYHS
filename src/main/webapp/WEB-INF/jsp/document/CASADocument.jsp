<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>
<link href="${staticPath}/css/docs/dthForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<script src="${staticPath}/js/docs/docViewers.js?v=${buildDate}"></script>
<style>
	.b1{
	    border-style: dotted;
	    border-width: 1px;
	    margin: 5px;
	}
	.pL30{
	    padding-left: 30px;
	}
	
	.pL10{
	    padding-left: 10px;
	}
	.pT20{
		padding-top: 20px;
	}
	.pTRBL10 {
	    padding: 10px 10px 0;
	}
	.labelunderneath6{
	 	padding-left:205px;
	 }
</style>

<form:form modelAttribute="stdCASADocVO" id="stdCASADocVO" cssClass="documentDescription documentFormVO">		
<form:errors path="*" cssClass="errorblock" element="div"/>

	<div id="casaDoc" style="background-color: transparent">
		
		<div style="padding-left:43%; " class="headingCls">
				CASA DOCUMENT
		</div>
		
		
		 <div class="pB30 pT20">
		
					
			<c:if test="${stdCASADocVO.PARENT_DOCUMENT_ID != null &&  stdCASADocVO.PARENT_DOCUMENT_ID != 0}">

				<div  class="floatLeft width70Perc" id="parentDocumentIdSection">
		            <span class="parentIdSectionLabel bold mL10">Parent DocumentId: </span>
					<span class="parentIdSectionValue "><a href="#" onclick="loadDocumentDlg(${stdCASADocVO.PARENT_DOCUMENT_ID}, '${param.documentType}')">${stdCASADocVO.PARENT_DOCUMENT_ID}</a></span>
					
		         </div>	
	         </c:if>
	         
			<div  class="floatRight">
	            <span class="mL10 bold">Document ID: </span>
				<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
	         </div>	

		</div>

		
		
	
		<div class="container pB10 w100 pT10">
            <div style="float:left; width: 40%">
	            <div>
	               <span class="ml10"> <spring:message code="label.stateNo" />: </span>
					<span class="ml10"><form:input path="STATENO" class="standardInputSmallText readonly" maxlength="10" readonly="true"/> </span>
	            </div>    
            </div>
            <div style="float:left; width: 30%">
            	<div>
	                <span class="mL10">	eHARS Import Date: </span>
	                <fmt:formatDate value="${stdCASADocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
					 <span class="mL10"><form:input path="EHARS_IMPORT_DATE" value= "${theFormattedDate}" class="standardInputSmallText readonly" /></span>
	            </div>    
            </div>

   		</div>
   			
   			<jsp:include page="statenoDetails.jsp"></jsp:include>
		
		<!--  Person details -->
		
		<div class="container" style="border:1px dotted; width:100%; padding-bottom:5px;">	 
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Person Details</span>
			</div>
			<div class="mLRTB pB20">
				<div class="fieldHolder25">
					<span class="fLW35"> First Name</span> 
					<span class="fLW60"> <form:input path="FNAME" id="CASA_FNAME" title="First Name" class="standardInputSmallText required" maxlength="15"/> </span>
					<div class="v_error CASA_FNAME "></div>
				</div>
				<div class="fieldHolder25">
					<span class="fLW35"> Middle Name</span>
					<span class="fLW60"> <form:input path="MNAME" title="Middle Name" class="standardInputSmallText" maxlength="1"/>  </span>
				</div>
				<div class="fieldHolder25 ">
					<span class="fLW35"> Last Name</span>
					<span class="fLW60"> <form:input path="LNAME" id="CASA_LNAME" title="Last Name" class="standardInputSmallText required" maxlength="30"/>  </span>
					<div class="v_error CASA_LNAME "></div>
				</div>
				<div class="fieldHolder25">
					<span class="fLW35"> Date of Birth</span>
					<span class="fLW60"> <form:input id="DATE_OF_BIRTH" path="DOB_TEXT" title="Date of Birth" class="dPicker mask standardInputSmallText"  datevalidations="completeDate" maxlength="15"/> </span>
					<div class="v_error DATE_OF_BIRTH "></div>
				</div>	
			</div>

			<div class="mLRTB pB20">
				<div class="fieldHolder25"> 
					<span class="fLW35">SSN </span>	
					<span class="fLW60"> <form:input path="SSN" id="CASA_SSN" title="SSN" class="required mask standardinputSmallTextAdap" maxLength="9" /></span>
					<div class="v_error CASA_SSN "></div>
				</div>	 	
				<div class="fieldHolder25"> 
					<span class="fLW35">CIN </span>	
					<span class="fLW60"> <form:input path="MEDICAID_NUMBER" id="CASA_MEDICAID_NUMBER" title="CIN" class="standardInputSmallText" maxLength="8" /></span>
					<div class="v_error CASA_MEDICAID_NUMBER "></div>
				</div>	 										
			</div>
			
			
			<!--  Race details -->
		
			<div id="raceEthnicity" class="clearBoth container" style="border:1px dotted; padding:5px 5px 0 0;">
				<div style="float:left; width:35%; border-right:1px dotted; padding:15px 0 10px 20px;">	
					<div style="padding:0 0 10px 20px;">
							Race					
	
						<div style="">
	
							<form:checkbox path="RACE_WHITE" value="1" />White	
							<br>
							<form:checkbox path="RACE_BLACK" value="1" /> African American
							<br>
							<form:checkbox path="RACE_ASIAN" value="1" /> Asian
							<br>
							<form:checkbox path="RACE_PACIFIC" value="1" /> Pacific Islander
							<br>
							<form:checkbox path="RACE_INDIAN" value="1" /> American Indian/Alaskan Native
							<br>
	
							<form:checkbox path="RACE_OTHER" value="1" /> Other
							<br>
							<form:checkbox path="RACE_UNKNOWN" value="1" /> Unknown
							<br>
							<form:checkbox path="RACE_MULTIPLE" value="1" /> Multiple
						</div>	
					</div>
				
				</div>
				
			<div style="float:left; width:35%; border-right:1px dotted; padding:15px 0 10px 20px; ">
				<div style="padding:5px 0 5px 10px;">
					<spring:message code="label.ethnicity" /> 
					(Hispanic or Latino) </br>
							<form:radiobutton path="ETHNICITY" value="01" /> Yes <br>
							<form:radiobutton path="ETHNICITY" value="02" /> No <br>
							<form:radiobutton path="ETHNICITY" value="09" /> Unknown <br>
				</div>
				<div style="border-top: dotted 1px">
					<div style="padding:10px 0 0 10px;">	
						<div class="floatLeft pR10" style="border-right: dotted 1px;">
							<div class=""> Current Gender </div>
							<div  class="">
								<div> <form:radiobutton path="GENDER" value="M" /> Male </div>
								<div> <form:radiobutton path="GENDER" value="F" /> Female </div>
								<div> <form:radiobutton path="GENDER" value="U" />Unknown</div>		
							</div>
						</div>
						<div class="floatLeft pL10">
							<div class=""> Transgender </div>
							<div  class="">
								<div> <form:radiobutton path="GENDER" value="X" /> MTF </div>		
								<div> <form:radiobutton path="GENDER" value="Y" /> FTM </div>		
								<div> <form:radiobutton path="GENDER" value="T" /> Unspecified </div>																
							</div>
						</div> 
 					</div>				
				</div>
			</div>
				
			<div style="float:left; width:25% ;" >

			<div class="mLRTB pB20">
			
                   <span class="pL30">Zip </span>
                   <span class="pL10"> 
                        <form:input path="PERSON_ZIP" title="Person Zip" class="oAdrDlg nOnly standardInputSmallText1 newZip" maxlength="5" /> 
                   </span>
                   
                   <br><br>
                   
                   <span class="pL30">County </span>
                   <span class="pL10"> 
                   
                   <form:select id="PERSON_COUNTY" title="Person County"  path="PERSON_COUNTY" class="roundedBrdr">
						        <form:option value="" label="--- Select ---" />
								<form:options items="${sessionScope.countyMap}"/>
					    </form:select>
					    
                </span>      		
			
			</div>
	        
			</div>	
						
		</div>	

        <div class="divHeadingCls bGHeading clearBoth">
            <span class=" mL2">CASA Flags:</span>    
            <span class="criteriaInfo"> </span>                             
        </div>
        
        <div class="eventCriteriaFields" style="border-bottom: 1px dotted; height: 100px">
            <div class="" style="width:18%; float:left; border-right:1px dotted; padding:15px 0 10px 20px;">
                <div >           
                        <div><span><form:checkbox path="INPAT" value="1" /> INPAT </span></div>
                        <div><span><form:checkbox path="OUTPAT" value="1" /> OUTPAT </span></div>
                        <div><span><form:checkbox path="DIAGALLCLAIMS" value="1" /> DIAGALLCLAIMS </span></div>
                </div>
            </div>
             <div class="" style="width:18%; float:left; border-right:1px dotted; padding:15px 0 10px 20px;">
                <div >   
                        <div><span><form:checkbox path="HIVHOSPICE" value="1" /> HIVHOSPICE </span></div>
                        <div><span><form:checkbox path="HIVSNP" value="1" /> HIVSNP </span></div>
                        <div><span><form:checkbox path="ARV" value="1" /> ARV </span></div>
                </div>
            </div>
             <div class="" style="width:18%; float:left; border-right:1px dotted; padding:15px 0 10px 20px;">
                <div >           
                        <div><span><form:checkbox path="OUTP_ADIOI" value="1" /> OUTP_ADIOI </span></div>
                        <div><span><form:checkbox path="OUTPSERV_DIAG" value="1" /> OUTPSERV_DIAG </span></div>
                        <div><span><form:checkbox path="SPEC_POSS" value="1" /> SPEC_POSS </span></div>
                </div>
            </div>
            <div class="" style="width:18%; float:left;border-right:1px dotted;  padding:15px 0 10px 20px">
                <div >                        
                        <div><span><form:checkbox path="SPEC_ADI" value="1" /> SPEC_ADI </span></div>
                        <div><span><form:checkbox path="ARV_POSS" value="1" /> ARV_POSS </span></div>
                        <div><span><form:checkbox path="ARV_ADI" value="1" /> ARV_ADI </span></div>
                </div> 
            </div>
            <div class="" style="width:18%; float:left; padding:15px 0 10px 20px">
                <div >                        
                        <div><span><form:checkbox path="ARV_OUTPAT" value="1" /> ARV_OUTPAT </span></div>
                        <div><span><form:checkbox path="PROCEDURES" value="1" /> PROCEDURES </span></div>
                        <div><span><form:checkbox path="OUTPAT_SELECT" value="1" /> OUTPAT_SELECT </span></div>
                </div> 
            </div>

		</div>
			
			
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">CASA Variables:</span>
			</div>
			<div class="mLRTB pB20">
				<div class="fieldHolder25">
					<span class="fLW35"> CLAIMCOUNT</span> 
					<span class="fLW60"> <form:input path="CLAIMCOUNT" title="CLAIMCOUNT" class="standardInputSmallText nOnly" maxlength="3"/> </span>
				</div>
				<div class="fieldHolder25">
					<span class="fLW35"> FINCRG</span>
					<span class="fLW60"> <form:input path="FINCRG" title="FINCRG" class="standardInputSmallText" maxlength="3"/>  </span>
				</div>
				<div class="fieldHolder25 ">
					<span class="fLW35"> ELIG_DATE</span>
					<span class="fLW60"> <form:input path="ELIG_DATE" id="CASA_ELIG_DATE" title="ELIG Date" class="standardInputSmallText dPicker mask" datevalidations="partialDate" maxlength="10"/>  </span>
					<div class="v_error CASA_ELIG_DATE "></div>
				</div>
				<div class="fieldHolder25">
					<span class="fLW35"> RATE</span>
					<span class="fLW60"> <form:input  path="RATE" title="RATE" class="standardInputSmallText" maxlength="20"/> </span>
				</div>	
			</div>

			<div class="mLRTB pB20">
				<div class="fieldHolder25">
					<span class="fLW35"> RATE1</span>
					<span class="fLW60"> <form:input  path="RATE1" title="RATE1" class="standardInputSmallText" maxlength="20"/> </span>
				</div>	
				<div class="fieldHolder25">
					<span class="fLW35"> CASA_FILE</span>
					<span class="fLW60"> <form:input path="CASA_FILE" title="Casa File" class="standardInputSmallText readonly" maxlength="15" />  </span>
				</div>
				<div class="fieldHolder25 ">
					<span class="fLW35"> NARROW</span>
					<span class="fLW60"> <form:input path="NARROW" title="Narrow" class="standardInputSmallText readonly" maxlength="8"/>  </span>
				</div>
			</div>
						
	</div>
	 <div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
		 <div id="button-row" class="button-row documentButtons center">
		 				<c:if  test="${userPermissions['99'] != null && stdCASADocVO.STATENO != null && stdCASADocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}" >	
								<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3 displayNone"  value="Trigger Assignment"  onclick="loadSurvAssignmentPopUp()"/>
							</c:if>
						<c:if test="${param.hasEditFunctionality == true}">
							<input type="button" id="eButton"  class="editBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="customDocumentEditViewerStyles()" />
						</c:if>
					<c:choose>
						<c:when  test="${param.hasDeleteFunctionality == true}" >	
								<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
								<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
						</c:when>
						<c:when test="${stdCASADocVO.STATENO != null && stdCASADocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
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
	<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
	</div>
	</div>
</form:form>															



