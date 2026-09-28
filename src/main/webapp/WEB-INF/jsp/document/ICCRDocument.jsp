<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link href="${staticPath}/css/docs/commonForm.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/docs/nycijrDoc.css?v=${buildDate}" rel="stylesheet" type="text/css" />

<script src="${staticPath}/js/docs/docViewers.js?v=${buildDate}"></script>

<form:form modelAttribute="stdICCRDocVO" id="stdICCRDocVO" cssClass="documentDescription documentFormVO">
<form:errors path="*" cssClass="errorblock" element="div" />

<div id="labDocTab">
   <div style="padding-left:43%; " class="headingCls">
                ICCR DOCUMENT
        </div>
        
        
        
       <div class="pB30 pT20">
			<c:if test="${stdICCRDocVO.PARENT_DOCUMENT_ID != null &&  stdICCRDocVO.PARENT_DOCUMENT_ID != 0}">

				<div  class="floatLeft width70Perc" id="parentDocumentIdSection">
		            <span class="parentIdSectionLabel bold">Parent DocumentId: </span>
					<span class="parentIdSectionValue"><a href="#" onclick="loadDocumentDlg(${stdICCRDocVO.PARENT_DOCUMENT_ID}, '${param.documentType}')">${stdICCRDocVO.PARENT_DOCUMENT_ID}</a></span>
					
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
 			 			<fmt:formatDate value="${stdICCRDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
						<form:input path="EHARS_IMPORT_DATE" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/>
 			 		</span>
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
                <span class="pL5"><form:input path="FNAME" id="iccrFirstName" class="standardInputMedText" title="First Name" maxLength="30"/></span>
            </div>
            <div class="fieldHolder25">
               	<span class="pL10">Last Name </span> 
               	<span  class="pL5"><form:input path="LNAME" id="iccrLastName" class="standardInputMedText" title="Last Name" maxLength="30"/></span>
            </div>            
            <div class="fieldHolder25">
               	<span class="pL10">Middle Name </span> 
               	<span  class="pL5"><form:input path="MNAME" id="iccrMiddleName" class="standardInputMedText"  title="Middle Name" maxLength="30"/></span>
            </div>
			<div class="fieldHolder25">
	       		<span class="pL10">Date of Birth </span>
	       		<span  class="pL10"><form:input path="DOB" id="iccrDOB" class="dPicker mask standardInputSmallText" title="Date of Birth" maxLength="10" datevalidations="completeDate"/></span>
	       </div>            
           <br><br>        
                  
        </div>

        <div class="clearBoth b1 " id="iccrDocumentPersonAddressDtls">
            <div class="personInfo pTRBL10">
                <span class="personLabelDoc bold">Current Address </span>
                <span> 
                    <span class="pL10"><form:input path="PERSON_ADDRESS"  class="standardInputMedText  newAddress widgetAddressFieldsReadonly" title="Person Address" maxlength="50"/></span>
                    
                    
                    <span class="pL10 bold">City</span>
                    <span class="pL10"><form:input path="PERSON_CITY"  class="oAdrDlg standardInputSmallText newCity widgetAddressFieldsReadonly" title="City" maxlength="65"/></span>
                    
                    <span class="pL10 bold">State </span>
                    <span  class="pL10"><form:input path="PERSON_STATE"  class="oAdrDlg standardInputSmallText newState widgetAddressFieldsReadonly" style="width:50px" title="State" maxlength="2"/></span>
                    
                    <span class="pL10 bold">Zip </span>
                    <span class="pL10"> 
                        <form:input path="PERSON_ZIP"  class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" title="ZipCode" maxlength="5"/>
                    </span>
                    
					<span class="pL10 bold">County </span>
                    	<span class="pL10">  
                    		<form:select path="PERSON_COUNTY"  cssClass="oAdrDlg roundedBrdr newCounty widgetAddressFieldsReadonly" title="County"> 
									<form:option value=""></form:option>
									<form:options items="${sessionScope.countyMap}"/>
							</form:select> 
                    </span> 
                    
                    <br>
                    <span style="padding-left:140px"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
                    
                </span>
            </div>
            <div class="personInfo pT10 pTRBL10">
            		<div>
	            		<div class="floatLeft">   
	                    	<span class="" style="padding-left:103px"><form:input path="rawPersonAddress"  class="standardInputMedText readonly" /></span>
	                    	
	                    	<div class="">
                    			<span style="padding-left:140px"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
                    		</div>
	                    </div>

						<div class="fieldHolder25">
				       		<span class="pL10">Phone</span>
				       		<span  class="pL10"><form:input path="PERSON_PHONE" id="iccrPersonPhone" class="standardInputSmallText" title="Person Phone" maxLength="14"/></span>
				       </div>   
					</div>
		            
            </div><br/><br/><br/>
        </div>        
        
  
   <div id="personHIVDetails" class="container">   
       <div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
           <span class=" mL2">Provider Information</span>          
       </div>
       
	       <div class="personInfo pT10">
	               
	  			<div class="fieldHolder25">
	               <span class="pL10">Provider Name </span> 
	               <span class="pL5"><form:input path="PROV_NAME" id="iccrProvName" class="standardInputMedText" title="Provider Name" maxLength="100"/></span>
	           </div>
	           <div class="fieldHolder25">
	              	<span class="pL10">Provider Phone </span> 
	              	<span  class="pL5"><form:input path="PROV_PHONE" id="iccrProvPhone" class="standardInputMedText" title="Provider Phone" maxLength="14"/></span>
	           </div>            
	           <div class="fieldHolder49">
	              	<span class="pL10">Jurisdiction providing information</span> 
	              	<span  class="pL5"><form:input path="JURISDICTION_PROVIDING_INFO" id="iccrJurisdiction" class="standardInputMedText"  title="Jurisdiction providing information" maxLength="30"/></span>
	           </div>
	          <br><br>        
	                 
	       </div>
	              
             <div class="personInfo pTRBL10">
                <span class="personLabelDoc bold">Address </span>
                <span> 
                    <span class="pL10"><form:input path="PROV_ADDRESS"  class="standardInputMedText  newAddress widgetAddressFieldsReadonly" title="Provider Address" maxlength="50"/></span>
                    
                    
                    <span class="pL10 bold">City</span>
                    <span class="pL10"><form:input path="PROV_CITY"  class="oAdrDlg standardInputSmallText newCity widgetAddressFieldsReadonly" title="City" maxlength="65"/></span>
                    
                    <span class="pL10 bold">State </span>
                    <span  class="pL10"><form:input path="PROV_STATE"  class="oAdrDlg standardInputSmallText newState widgetAddressFieldsReadonly" style="width:50px" title="State" maxlength="2"/></span>
                    
                    <span class="pL10 bold">Zip </span>
                    <span class="pL10"> 
                        <form:input path="PROV_ZIP"  class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" title="ZipCode" maxlength="5"/>
                    </span>
                    
					<span class="pL10 bold">County </span>
                    	<span class="pL10">  
                    		<form:select path="PROV_COUNTY"  cssClass="oAdrDlg roundedBrdr newCounty widgetAddressFieldsReadonly" title="County"> 
									<form:option value=""></form:option>
									<form:options items="${sessionScope.countyMap}"/>
							</form:select> 
                    </span> 
                    
                    <br>
                    <span style="padding-left:105px"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
                    
                </span>
            </div>
            <div class="personInfo pT10 pTRBL10">
            		<div>
	            		<div class="floatLeft">   
	                    	<span class="" style="padding-left:62px"><form:input path="rawProvAddress"  class="standardInputMedText readonly" /></span>
	                    	
	                    	<div class="">
                    			<span style="padding-left:105px"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
                    		</div>
	                    </div>
					</div>
		            
            </div><br/><br/><br/>
   </div>
              
           <div class="container" id="patientInformation">  
                        <div id="psCaseInfoHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                <span class=" mL2">Test Information</span>
                        </div>
                       
				<div id="testInfoDivContainer" class="container" style="float:left; width:99%; padding:2px; padding-left:10px;border-bottom:1px dotted"> 
				
					<c:forEach items="${stdICCRDocVO.iccrTestList}" var="tempObj" varStatus="statusCD">
								<div class="negIndDiv" id="negIndDiv" style="float:left; width:99%; padding-top:5px; margin-left:5px; margin-right:5px; margin-top:2px;" > 	
										
									<div>			
 										<div class="fieldHolder15">
											<span>Date of Test <form:input  path="iccrTestList[${statusCD.index}].DATE_OF_TEST"  class="standardInputSmallText1" maxlength="10" title="Date of Test"/></span>
										</div>
										<div class="fieldHolder25">
											<span >Test Type </span> 
											<span  class="pL5"><form:input path="iccrTestList[${statusCD.index}].TEST_TYPE" class="standardInputMedText"  title="Test Type" maxLength="80"/></span>
										</div>
										<div class="fieldHolder25">
											<span >Test Result </span> 
											<span  class="pL5"><form:input path="iccrTestList[${statusCD.index}].TEST_RESULTS" class="standardInputMedText"  title="Test Result" maxLength="80"/></span>
										</div>
										<div class="fieldHolder15">
											<span >Copies/mL </span> 
											<span  class="pL5"><form:input path="iccrTestList[${statusCD.index}].TEST_RESULT_COPIES" class="standardInputSmallText2"  title="Copies/mL" maxLength="20"/></span>
										</div>
										<div class="fieldHolder15">
											<span >Log  </span> 
											<span  class="pL5"><form:input path="iccrTestList[${statusCD.index}].TEST_RESULT_LOGS" class="standardInputSmallText2"  title="Log" maxLength="20"/></span>
										</div>
									</div>					
							 		
						 		</div>

					</c:forEach>
				</div>
        	</div>

                           <div class="personInfo" style="padding-top:10px;height:70px">
                              <br/>
		                             <div class="fieldHolder25">
		                                <span class="pL10">Patient received results? </span> 
										<span  class="pL5"><form:input path="RESULTS_RECEIVED" class="standardInputSmallText2"  title="Test Result" maxLength="20"/></span>                                       	
		                             </div>
  									<div class="fieldHolder75">
										<div class="fieldHolder10 mT10"><span class="pL10">Comments </span> </div>
										<div class="fieldHolder49 mT10">
										<span class="pL5">  <form:textarea title="Comments" maxlength="4000" cols="200" rows="3" path="COMMENTS" style="width:65%; height:67px; border:1px solid gray;" class="roundedBorder"></form:textarea></span>											                                   	
		                                </div>
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
 				<c:when test="${stdICCRDocVO.stateNo != null && stdICCRDocVO.stateNo != '' && nyehmsStatenoDetails.REASON_DESC == null}">
						<c:if test="${userPermissions['90'] != null}" >
							<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
						</c:if>
				</c:when>
			</c:choose>
			
        </div>   
    </div><br>
        
    <input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
</form:form>


