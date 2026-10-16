<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		
					
			<c:if test="${stdCASADocVO.parentDocumentId != null &&  stdCASADocVO.parentDocumentId != 0}">

				<div  class="floatLeft width70Perc" id="parentDocumentIdSection">
		            <span class="parentIdSectionLabel bold mL10">Parent DocumentId: </span>
					<span class="parentIdSectionValue "><a href="#" onclick="loadDocumentDlg(${stdCASADocVO.parentDocumentId}, '${param.documentType}')">${stdCASADocVO.parentDocumentId}</a></span>
					
		         </div>	
	         </c:if>
	         
			<div  class="floatRight">
	            <span class="mL10 bold">Document ID: </span>
				<span class=" "><form:input id="DOCUMENT_ID"  path="stdCasaDoc.documentId" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
	         </div>	

		</div>

		
		
	
		<div class="container pB10 w100 pT10">
            <div style="float:left; width: 40%">
	            <div>
	               <span class="ml10"> <spring:message code="label.stateNo" />: </span>
					<span class="ml10"><form:input id="STATENO"  path="stateno" class="standardInputSmallText readonly" maxlength="10" readonly="true"/> </span>
	            </div>    
            </div>
            <div style="float:left; width: 30%">
            	<div>
	                <span class="mL10">	eHARS Import Date: </span>
	                <fmt:formatDate value="${stdCASADocVO.stdCasaDoc.eharsImportDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
					 <span class="mL10"><form:input id="EHARS_IMPORT_DATE"  path="stdCasaDoc.eharsImportDate" value= "${theFormattedDate}" class="standardInputSmallText readonly" /></span>
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
					<span class="fLW60"> <form:input  path="stdCasaDoc.fname" id="CASA_FNAME" title="First Name" class="standardInputSmallText required" maxlength="15"/> </span>
					<div class="v_error CASA_FNAME "></div>
				</div>
				<div class="fieldHolder25">
					<span class="fLW35"> Middle Name</span>
					<span class="fLW60"> <form:input id="MNAME"  path="stdCasaDoc.mname" title="Middle Name" class="standardInputSmallText" maxlength="1"/>  </span>
				</div>
				<div class="fieldHolder25 ">
					<span class="fLW35"> Last Name</span>
					<span class="fLW60"> <form:input  path="stdCasaDoc.lname" id="CASA_LNAME" title="Last Name" class="standardInputSmallText required" maxlength="30"/>  </span>
					<div class="v_error CASA_LNAME "></div>
				</div>
				<div class="fieldHolder25">
					<span class="fLW35"> Date of Birth</span>
					<span class="fLW60"> <form:input id="DATE_OF_BIRTH"  path="stdCasaDoc.dobText" title="Date of Birth" class="dPicker mask standardInputSmallText"  datevalidations="completeDate" maxlength="15"/> </span>
					<div class="v_error DATE_OF_BIRTH "></div>
				</div>	
			</div>

			<div class="mLRTB pB20">
				<div class="fieldHolder25"> 
					<span class="fLW35">SSN </span>	
					<span class="fLW60"> <form:input  path="stdCasaDoc.ssn" id="CASA_SSN" title="SSN" class="required mask standardinputSmallTextAdap" maxLength="9" /></span>
					<div class="v_error CASA_SSN "></div>
				</div>	 	
				<div class="fieldHolder25"> 
					<span class="fLW35">CIN </span>	
					<span class="fLW60"> <form:input  path="stdCasaDoc.medicaidNumber" id="CASA_MEDICAID_NUMBER" title="CIN" class="standardInputSmallText" maxLength="8" /></span>
					<div class="v_error CASA_MEDICAID_NUMBER "></div>
				</div>	 										
			</div>
			
			
			<!--  Race details -->
		
			<div id="raceEthnicity" class="clearBoth container" style="border:1px dotted; padding:5px 5px 0 0;">
				<div style="float:left; width:35%; border-right:1px dotted; padding:15px 0 10px 20px;">	
					<div style="padding:0 0 10px 20px;">
							Race					
	
						<div style="">
	
							<form:checkbox id="RACE_WHITE1"  path="stdCasaDoc.raceWhite" value="1" />White	
							<br>
							<form:checkbox id="RACE_BLACK1"  path="stdCasaDoc.raceBlack" value="1" /> African American
							<br>
							<form:checkbox id="RACE_ASIAN1"  path="stdCasaDoc.raceAsian" value="1" /> Asian
							<br>
							<form:checkbox id="RACE_PACIFIC1"  path="stdCasaDoc.racePacific" value="1" /> Pacific Islander
							<br>
							<form:checkbox id="RACE_INDIAN1"  path="stdCasaDoc.raceIndian" value="1" /> American Indian/Alaskan Native
							<br>
	
							<form:checkbox id="RACE_OTHER1"  path="stdCasaDoc.raceOther" value="1" /> Other
							<br>
							<form:checkbox id="RACE_UNKNOWN1"  path="stdCasaDoc.raceUnknown" value="1" /> Unknown
							<br>
							<form:checkbox id="RACE_MULTIPLE1"  path="stdCasaDoc.raceMultiple" value="1" /> Multiple
						</div>	
					</div>
				
				</div>
				
			<div style="float:left; width:35%; border-right:1px dotted; padding:15px 0 10px 20px; ">
				<div style="padding:5px 0 5px 10px;">
					<spring:message code="label.ethnicity" /> 
					(Hispanic or Latino) </br>
							<form:radiobutton id="ETHNICITY1"  path="stdCasaDoc.ethnicity" value="01" /> Yes <br>
							<form:radiobutton id="ETHNICITY2"  path="stdCasaDoc.ethnicity" value="02" /> No <br>
							<form:radiobutton id="ETHNICITY3"  path="stdCasaDoc.ethnicity" value="09" /> Unknown <br>
				</div>
				<div style="border-top: dotted 1px">
					<div style="padding:10px 0 0 10px;">	
						<div class="floatLeft pR10" style="border-right: dotted 1px;">
							<div class=""> Current Gender </div>
							<div  class="">
								<div> <form:radiobutton id="GENDER1"  path="stdCasaDoc.gender" value="M" /> Male </div>
								<div> <form:radiobutton id="GENDER2"  path="stdCasaDoc.gender" value="F" /> Female </div>
								<div> <form:radiobutton id="GENDER3"  path="stdCasaDoc.gender" value="U" />Unknown</div>		
							</div>
						</div>
						<div class="floatLeft pL10">
							<div class=""> Transgender </div>
							<div  class="">
								<div> <form:radiobutton id="GENDER4"  path="stdCasaDoc.gender" value="X" /> MTF </div>		
								<div> <form:radiobutton id="GENDER5"  path="stdCasaDoc.gender" value="Y" /> FTM </div>		
								<div> <form:radiobutton id="GENDER6"  path="stdCasaDoc.gender" value="T" /> Unspecified </div>																
							</div>
						</div> 
 					</div>				
				</div>
			</div>
				
			<div style="float:left; width:25% ;" >

			<div class="mLRTB pB20">
			
                   <span class="pL30">Zip </span>
                   <span class="pL10"> 
                        <form:input id="PERSON_ZIP"  path="stdCasaDoc.personZip" title="Person Zip" class="oAdrDlg nOnly standardInputSmallText1 newZip" maxlength="5" /> 
                   </span>
                   
                   <br><br>
                   
                   <span class="pL30">County </span>
                   <span class="pL10"> 
                   
                   <form:select id="PERSON_COUNTY" title="Person County"   path="stdCasaDoc.personCounty" class="roundedBrdr">
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
                        <div><span><form:checkbox id="INPAT1"  path="stdCasaDoc.inpat" value="1" /> INPAT </span></div>
                        <div><span><form:checkbox id="OUTPAT1"  path="stdCasaDoc.outpat" value="1" /> OUTPAT </span></div>
                        <div><span><form:checkbox id="DIAGALLCLAIMS1"  path="stdCasaDoc.diagallclaims" value="1" /> DIAGALLCLAIMS </span></div>
                </div>
            </div>
             <div class="" style="width:18%; float:left; border-right:1px dotted; padding:15px 0 10px 20px;">
                <div >   
                        <div><span><form:checkbox id="HIVHOSPICE1"  path="stdCasaDoc.hivhospice" value="1" /> HIVHOSPICE </span></div>
                        <div><span><form:checkbox id="HIVSNP1"  path="stdCasaDoc.hivsnp" value="1" /> HIVSNP </span></div>
                        <div><span><form:checkbox id="ARV1"  path="stdCasaDoc.arv" value="1" /> ARV </span></div>
                </div>
            </div>
             <div class="" style="width:18%; float:left; border-right:1px dotted; padding:15px 0 10px 20px;">
                <div >           
                        <div><span><form:checkbox id="OUTP_ADIOI1"  path="stdCasaDoc.outpAdioi" value="1" /> OUTP_ADIOI </span></div>
                        <div><span><form:checkbox id="OUTPSERV_DIAG1"  path="stdCasaDoc.outpservDiag" value="1" /> OUTPSERV_DIAG </span></div>
                        <div><span><form:checkbox id="SPEC_POSS1"  path="stdCasaDoc.specPoss" value="1" /> SPEC_POSS </span></div>
                </div>
            </div>
            <div class="" style="width:18%; float:left;border-right:1px dotted;  padding:15px 0 10px 20px">
                <div >                        
                        <div><span><form:checkbox id="SPEC_ADI1"  path="stdCasaDoc.specAdi" value="1" /> SPEC_ADI </span></div>
                        <div><span><form:checkbox id="ARV_POSS1"  path="stdCasaDoc.arvPoss" value="1" /> ARV_POSS </span></div>
                        <div><span><form:checkbox id="ARV_ADI1"  path="stdCasaDoc.arvAdi" value="1" /> ARV_ADI </span></div>
                </div> 
            </div>
            <div class="" style="width:18%; float:left; padding:15px 0 10px 20px">
                <div >                        
                        <div><span><form:checkbox id="ARV_OUTPAT1"  path="stdCasaDoc.arvOutpat" value="1" /> ARV_OUTPAT </span></div>
                        <div><span><form:checkbox id="PROCEDURES1"  path="stdCasaDoc.procedures" value="1" /> PROCEDURES </span></div>
                        <div><span><form:checkbox id="OUTPAT_SELECT1"  path="stdCasaDoc.outpatSelect" value="1" /> OUTPAT_SELECT </span></div>
                </div> 
            </div>

		</div>
			
			
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">CASA Variables:</span>
			</div>
			<div class="mLRTB pB20">
				<div class="fieldHolder25">
					<span class="fLW35"> CLAIMCOUNT</span> 
					<span class="fLW60"> <form:input id="CLAIMCOUNT"  path="stdCasaDoc.claimcount" title="CLAIMCOUNT" class="standardInputSmallText nOnly" maxlength="3"/> </span>
				</div>
				<div class="fieldHolder25">
					<span class="fLW35"> FINCRG</span>
					<span class="fLW60"> <form:input id="FINCRG"  path="stdCasaDoc.fincrg" title="FINCRG" class="standardInputSmallText" maxlength="3"/>  </span>
				</div>
				<div class="fieldHolder25 ">
					<span class="fLW35"> ELIG_DATE</span>
					<span class="fLW60"> <form:input  path="stdCasaDoc.eligDate" id="CASA_ELIG_DATE" title="ELIG Date" class="standardInputSmallText dPicker mask" datevalidations="partialDate" maxlength="10"/>  </span>
					<div class="v_error CASA_ELIG_DATE "></div>
				</div>
				<div class="fieldHolder25">
					<span class="fLW35"> RATE</span>
					<span class="fLW60"> <form:input id="RATE"   path="stdCasaDoc.rate" title="RATE" class="standardInputSmallText" maxlength="20"/> </span>
				</div>	
			</div>

			<div class="mLRTB pB20">
				<div class="fieldHolder25">
					<span class="fLW35"> RATE1</span>
					<span class="fLW60"> <form:input id="RATE1"   path="stdCasaDoc.rate1" title="RATE1" class="standardInputSmallText" maxlength="20"/> </span>
				</div>	
				<div class="fieldHolder25">
					<span class="fLW35"> CASA_FILE</span>
					<span class="fLW60"> <form:input id="CASA_FILE"  path="stdCasaDoc.casaFile" title="Casa File" class="standardInputSmallText readonly" maxlength="15" />  </span>
				</div>
				<div class="fieldHolder25 ">
					<span class="fLW35"> NARROW</span>
					<span class="fLW60"> <form:input id="NARROW"  path="stdCasaDoc.narrow" title="Narrow" class="standardInputSmallText readonly" maxlength="8"/>  </span>
				</div>
			</div>
						
	</div>
	 <div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
		 <div id="button-row" class="button-row documentButtons center">
		 				<c:if  test="${userPermissions['99'] != null && stdCASADocVO.stateno != null && stdCASADocVO.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null}" >	
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
						<c:when test="${stdCASADocVO.stateno != null && stdCASADocVO.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null}">
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



