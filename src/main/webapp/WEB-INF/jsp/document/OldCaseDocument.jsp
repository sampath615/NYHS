<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>

 
 <script src="${staticPath}/js/docs/oldCasesDoc.js?v=${buildDate}"></script>
 <link href="${staticPath}/css/docs/tbDoc.css?v=${buildDate}" rel="stylesheet" type="text/css" />
 
<form:form modelAttribute="stdOldCaseDocVO" id="stdOldCaseDocVO" cssClass="documentDescription documentFormVO" >		
<form:errors path="*" cssClass="errorblock" element="div"/>

	<div id="tbDoc" style="background-color: transparent">
		
		<div style="padding-left:43%; " class="headingCls">
				Old Case Document
		</div>
		
		<div style="padding-bottom:10px; text-align: right">
            <span class="mL10">Document ID: </span>
			<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
        </div>
		<div class="container w100 pB10">
 	         <div style="float:left;  width: 40%">
 	            <div >
 	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
 	                 <span class="ml10"><form:input path="MATCHED_STATENO" value="${MATCHED_STATENO}" class="standardInputSmallText" maxlength="10" readonly="true"/> </span>
 	            </div>    
 	          </div>
 		</div>
 		<jsp:include page="statenoDetails.jsp"></jsp:include>

		<!--  Person details -->
		
		<div class="container" style="border:1px dotted; width:100%; padding-bottom:5px;">	 
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Person Details</span>
			</div>
			<div class="pL10 mLRTB pB20 ">
				<div class="floatLeft">
					<span class=""> First Name</span> 
					<span class="mL20"> <form:input path="FNAME" class="standardInputSmallText readonly"  maxlength="20"/> </span>
				</div>
				<div class="floatLeft">
					<span class="mL20"> Middle Name</span>
					<span class="mL20"> <form:input path="MNAME" class="standardInputSmallText readonly" maxlength="25"/>  </span>
				</div>
				<div class="floatLeft">
					<span class="mL20"> Last Name</span>
					<span class="mL20"> <form:input path="LNAME" id="lName" title="Last Name" class="standardInputSmallText readonly" maxlength="25"/>  </span>
				</div>
				<div class="floatLeft">
					<span class="mL20"> Suffix</span>
					<span class="mL20"><form:input path="SUFFIX" class="standardInputSmallText readonly" maxlength="5"/></span>	
				</div>	
			</div>
			<div class="mR5 mT5 mL20 pB20" >
								
				<div class="floatLeft"> 
					<span class="mL20"> Date of Birth </span>
					<span class="ml10"> <form:input id="dobValidation" path="DOB_TEXT" class="dPicker standardInputSmallText readonly"  maxlength="10"/> </span>
				</div>			
				<div class="floatLeft">
					<span class=" mL30"> Date of Death </span>
					<span class="ml10"> <form:input id="dodValidation" path="DOD_TEXT" class="dPicker standardInputSmallText"  maxlength="10"/> </span>
				</div>
				<span class="floatLeft"> 
					<span class="mL30">SSN </span>	
					<span class="ml10"> <form:input path="SSN" id="OLD_CASES_SSN" class="standardInputSmallText" maxLength="9" title="SSN"/></span>
				</span>					
			</div>
			
			<div class="b1 pB10" style="margin: 15px 5px 10px 5px; clear: both">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"> <form:input path="ADDRESS" class="standardinputText readonly" maxlength="62"/>  </span>	 	        
	 	                	            	 	                    
	 	                    <span class="pL30">City</span>
	 	                    <span class="pL10"><form:input path="CITY" class="standardInputSmallText1 readonly"  maxlength="30"/></span>
	 	                    
	 	                    <span class="pL30">State </span>
	 	                    <span  class="pL10">
								<form:select id="STATE"  path ="STATE" class="standardInputSmallText1 mR50 readonly">
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.stateMap}"/>
							    </form:select>
							    
							</span>
	 	                    
	 	                    <span class="pL30">Zip </span>
	 	                    <span class="pL10"> 
	 	                       <form:input path="ZIP" class="oAdrDlg nOnly standardInputSmallText1 newZip readonly" maxlength="5" /> 
	 	                    </span> 
	 	                    
<!-- 	 	                    <span class="pL30">County </span> -->
<!-- 	 	                    <span class="pL10">  -->
<%-- 	 	                       <form:select id="personCounty"   path="personCounty" class="standardInputSmallText1"> --%>
<%-- 								        <form:option value="" label="--- Select ---" /> --%>
<%-- 										<form:options items="${sessionScope.countyMap}"/> --%>
<%-- 							    </form:select> --%>
<!-- 	 	                    </span>     	                     -->
<!-- 	 	                    <br> -->
<!-- 	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span> -->
	 	                </span>
 	            </div>
<!--  	            <div class="personInfo pT10 pTRBL10"> -->
<%--  	            	<span  class="" style="padding-left:59px"><form:input path="stdPersonAddress" class="standardinputText " maxlength="62" readonly="true"/></span> --%>
 	              	 
<!--  	              	<span class="pL30">Phone </span> -->
<!--                     <span class="pL10">  -->
<%--                        <form:input path="personPhone" class=" standardInputSmallText" maxlength="20"/> --%>
<!--                     </span>  -->
<!--  	                <br>                -->
<!--  	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span> -->
 	                    
<!--  	            </div> -->
 	        </div>
								  
		</div>
		
		<!--  Race details -->
		
		<div id="raceEthnicity" class="container" style="border:1px dotted; padding:10px;">
			<div style="float:left; width:25%; margin-left:20px; border-right:1px dotted; padding-bottom:10px;">				
				<div>
					Race
				</div>
				<div>
					<form:checkbox path="RACE_WHITE" value="1" class="readonly" /> White	
				</div>
				<div>
					<form:checkbox path="RACE_BLACK" value="1" class="readonly" /> African American
				</div>
				<div>
					<form:checkbox path="RACE_ASIAN" value="1" class="readonly" /> Asian
				</div>
				<div>
					<form:checkbox path="RACE_PACIFIC" value="1" class="readonly" /> Pacific
				</div>				
				<div>
					<form:checkbox path="RACE_INDIAN" value="1" class="readonly" /> American Indian/ Alaskan Native
				</div>				
				<div>
					<form:checkbox path="RACE_OTHER" value="1" class="readonly" /> Other 
				</div>
				<div>
					<form:checkbox path="RACE_UNKNOWN" value="1" class="readonly" /> Unknown
				</div>
			</div>
			<div style="float:left;  width:35% ; border-right:1px dotted;padding-bottom:10px;">
				<div style="padding-left:10px;">	
					Ethnicity (Hispanic or Latino)
					<br>
					<form:radiobutton path="ETHNICITY" value="01" class="readonly" /> Yes <br>
					<form:radiobutton path="ETHNICITY" value="02" class="readonly" /> No <br>
					<form:radiobutton path="ETHNICITY" value="09" class="readonly" /> Unknown	
				</div>
								
				<div style="padding:15px 0 10px 10px; border-top: dotted 1px; "> 
					<div class="floatLeft pR10" style="border-right: dotted 1px;">
						<div class=""> Current Gender </div>
						<div  class="">
							<div> <form:radiobutton path="CURRENT_GENDER" value="M" class="readonly" /> Male </div>
							<div> <form:radiobutton path="CURRENT_GENDER" value="F" class="readonly" /> Female </div>
							<div> <form:radiobutton path="CURRENT_GENDER" value="U" class="readonly" />Unknown</div>		
						</div>
					</div>
					<div class="floatLeft pL10">
						<div class=""> Transgender </div>
						<div  class="">
							<div> <form:radiobutton path="CURRENT_GENDER" value="X" class="readonly" /> MTF </div>		
							<div> <form:radiobutton path="CURRENT_GENDER" value="Y" class="readonly" /> FTM </div>		
							<div> <form:radiobutton path="CURRENT_GENDER" value="T" class="readonly" /> Unspecified </div>														
						</div>
					</div> 
				</div>
				
			</div>
			<div style="float:left;  width:35% ;padding-bottom:10px; ">
			
				<span class="pL30">DIN </span>
	 	                    <span  class="pL10">
								<form:input path="DIN" class="standardInputMedText readonly"  maxlength="7"/>
							</span>
							
						</div>
		</div>	
		
		 <div class="ClearBoth"></div>	
			<div  class="b1 pB10">
 					<span class="mL30 ">Comments </span><br/>
 					<div class="mL30">
					<span>  <form:textarea title="COMMENTS" maxlength="1000" cols="165" rows="8" path="COMMENTS" style="width:65%; height:67px; border:1px solid gray;" class="roundedBorder"></form:textarea></span>

				</div>
		
		</div>
			
		
		
		
		
		<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
		<div id="button-row" class="button-row documentButtons" align="center">
			<c:choose>
				<c:when  test="${param.hasDeleteFunctionality == true}" >	
						<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
						<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
				</c:when>
				<c:when test="${stdOldCaseDocVO.MATCHED_STATENO != null && stdOldCaseDocVO.MATCHED_STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
						<c:if test="${userPermissions['90'] != null}" >
							<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
						</c:if>
						<c:if test="${userPermissions['134'] != null}" >
							<input type="button" id="editDocument"  class="editDocumentBtnclick button rounded blue effect-3 displayNone"  value="Edit" onclick="confirmEditDocumentFromStateno()" />
						</c:if>
				</c:when>
			</c:choose>
			<input type="button" id="sButton"  class="labBtnclick button rounded blue effect-3" value="Save" onclick="saveForm()"/>	 
			<input type="button" id="cButton"  class="cancelBtnclick button rounded blue effect-3 " value="Cancel" />
		</div>
	</div>		
		<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />	
</form:form>															
