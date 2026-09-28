<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 
 <script src="${staticPath}/js/docs/tbDoc.js?v=${buildDate}"></script>
 <link href="${staticPath}/css/docs/tbDoc.css?v=${buildDate}" rel="stylesheet" type="text/css" />
 
<form:form modelAttribute="stdTBDocVO" id="stdTBDocVO" cssClass="documentDescription documentFormVO" >		
<form:errors path="*" cssClass="errorblock" element="div"/>

		
		<div style="padding-left:43%; " class="headingCls">
				TB DOCUMENT
		</div>
		
		
		    <div class="pB30 pT20">
		
					
			<c:if test="${stdTBDocVO.PARENT_DOCUMENT_ID != null &&  stdTBDocVO.PARENT_DOCUMENT_ID != 0}">

				<div  class="floatLeft width70Perc" id="parentDocumentIdSection">
		            <span class="parentIdSectionLabel bold mL10">Parent DocumentId: </span>
					<span class="parentIdSectionValue "><a href="#" onclick="loadDocumentDlg(${stdTBDocVO.PARENT_DOCUMENT_ID}, '${param.documentType}')">${stdTBDocVO.PARENT_DOCUMENT_ID}</a></span>
					
		         </div>	
	         </c:if>
	         
			<div  class="floatRight">
	            <span class="mL10 bold">Document ID: </span>
				<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
	         </div>	

		</div>

				
				
	
		<div class="container w100 pB10">
 	         <div style="float:left; width: 40%">
 	            <div >
 	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
 	                 <span class="ml10"><form:input path="STATENO" value="${STATENO}" class="standardInputSmallText readonly" maxlength="10" readonly="true"/> </span>
 	            </div>    
 	          </div>
 	          <div style="float:left;  width: 30%">
 	         	<div >
 	                <span class="ml10"> eHARS Import Date: </span>
 	                <fmt:formatDate value="${stdTBDocVO.EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
 			 		<span class="ml10"><form:input path="EHARS_IMPORT_DATE" value= "${theFormattedDate}" class="standardInputSmallText ml10 readonly" readonly="true"/> </span>
 	            </div>    
 	         </div>
 	         <div style="float:left; width: 30%; text-align: right">
 	            <div >
 	                <span class=" ml10">	NYC SHIP Date:  </span>
 	                <fmt:formatDate value="${stdTBDocVO.NYC_SHIP_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedNYC_SHIP_DATE_TEXT" ></fmt:formatDate>
 			 		<span class="ml10 "><form:input path="NYC_SHIP_DATE" value="${theFormattedNYC_SHIP_DATE_TEXT}" class="standardinputSmallText ml10 readonly" maxlength="6" readonly="true"/></span>
 	           </div>                  
 	         </div>     
 		</div>
 		
 		<jsp:include page="statenoDetails.jsp"></jsp:include>	

		<!--  Person details -->
		
		<div class="container" style="border:1px dotted; width:100%; padding-bottom:5px;">	 
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Person Details</span>
			</div>
			<div class="mL20 mT20 ">
				<div class="fieldHolder25">
					<span class=""> First Name</span> 
					<span class="mL20"> <form:input path="FNAME" id="TB_FNAME" class="standardInputSmallText required" maxlength="20" title="First Name"/> </span>
					<div class="v_error TB_FNAME "></div>
				</div>
				<div class="fieldHolder25">
					<span class=""> Middle Name</span>
					<span class="mL20"> <form:input path="MI" id="TB_MNAME" class="standardInputSmallText" maxlength="25"  title="Middle Name"/>  </span>
					<div class=" v_error TB_MNAME "></div>
				</div>
				<div class="fieldHolder25">
					<span class=""> Last Name</span>
					<span class="mL20"> <form:input path="LNAME"  id="TB_LNAME" title="Last Name" class="required standardInputSmallText" maxlength="25"/>  </span>
					<div class=" v_error TB_LNAME "></div>
				</div>
					
			</div>
			<div class="clearBoth  mL20 pT5 pB20" >
								
				<div class="fieldHolder25"> 
					<span class=""> Date of Birth </span>
					<span class="ml10"> <form:input id="DATE_OF_BIRTH" path="DOB_TEXT" class="dPicker mask standardInputSmallText" datevalidations="completeDate"  maxlength="10" title="Date of Birth"  /> </span>
					<div class=" v_error DATE_OF_BIRTH "></div>
				</div>
				<div class="fieldHolder25"> 
					<span class=""> Date of Death </span>
					<span class="ml10"> <form:input id="DATE_OF_DEATH" path="DOD_TEXT" class="dPicker mask standardInputSmallText" datevalidations="completeDate"  maxlength="10" title="Date of Death" /> </span>
					<div class=" v_error DATE_OF_DEATH "></div>
				</div>					
			</div>
			
			<div class="b1" style="margin: 15px 5px 10px 5px; clear: both" id="tbDocumentPersonAddressDtls">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"> <form:input path="PERSON_ADDRESS" class="standardinputText newAddress widgetAddressFieldsReadonly" maxlength="80" title="Address" />  </span>	 	        
	 	                	            	 	                    
	 	                    <span class="pL30">City</span>
	 	                    <span class="pL10"><form:input path="PERSON_CITY" class="standardInputSmallText1 newCity widgetAddressFieldsReadonly"  maxlength="30" title="City" /></span>
	 	                    
	 	                    <span class="pL30">State </span>
	 	                    <span  class="pL10">
								<form:select id="PERSON_STATE"  path ="PERSON_STATE" class="standardInputSmallText1 mR50 newState widgetAddressFieldsReadonly" title="State" >
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.stateMap}"/>
							    </form:select>
							    
							</span>
	 	                    
	 	                    <span class="pL30">Zip </span>
	 	                    <span class="pL10"> 
	 	                       <form:input path="PERSON_ZIP" class="oAdrDlg nOnly standardInputSmallText1 newZip widgetAddressFieldsReadonly" maxlength="5" title="Zip Code" /> - <form:input path="PERSON_PLUS4" title="Plus4"  class="nOnly standardInputSmallText1" maxlength="4"/>
	 	                    </span> 
	 	                    
	 	                    <span class="pL30">County </span>
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
 	            	<span  class="" style="padding-left:59px"><form:input path="rawPersonAddress" class="standardinputText readonly" maxlength="62" readonly="true" title="Raw Address" /></span>
 	              	 
 	              	<span class="pL30">Phone </span>
                    <span class="pL10"> 
                       <form:input path="PERSON_PHONE" title="Phone"  class=" standardInputSmallText" maxlength="20"/>
                    </span> 
                    <span style="float: right;" class="mr50">
							<a href="javascript:openAddressWidget('tbDocumentPersonAddressDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
					</span> 
                    
                    
 	                <br>               
 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
 	                    
 	            </div>
 	        </div>
								  
		</div>
		
		<!--  Race details -->
		
		<div id="raceEthnicity" class="container" style="border:1px dotted; padding:10px;">
			<div style="float:left; width:25%; margin-left:20px; border-right:1px dotted; padding-bottom:10px;">				
				<div>
					Race 
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
			<div style="float:left;  width:35% ; ">
				<div style="padding-left:10px;">	
					Ethnicity (Hispanic or Latino)
					<br>
					<form:radiobutton path="ETHNICITY" value="01" /> Yes <br>
					<form:radiobutton path="ETHNICITY" value="02" /> No <br>
					<form:radiobutton path="ETHNICITY" value="09" /> Unknown	
				</div>
								
				<div style="padding:15px 0 10px 10px; border-top: dotted 1px; border-bottom: dotted 1px"> 
					<div class=""> Gender </div>
					<div  class="">
						<div> <form:radiobutton path="BIRTH_SEX" value="M" /> Male </div>
						<div> <form:radiobutton path="BIRTH_SEX" value="F" /> Female </div>		
					</div>
				</div>
				
			</div>
			<div style="float:left; width:35% ; padding-left:10px; border-left:1px dotted; height: 163px" >
				<div class="mT5 container">
					<div style="float:left; width:35%">
						DIN
					</div>
					<div style="float:left">
						<form:input path="DIN" id="TB_DIN" class="standardInputMedText " title="DIN"   maxlength="7"/>
					</div>	
					<div class="v_error TB_DIN "></div>
				</div>	
				<div class="mT5 container">
					<div style="float:left; width:35%">
						Case
					</div>
					<div style="float:left">
						<form:input path="ATYPICAL_MYCO" class="standardInputMedText " title="Case"   maxlength="50"/>
					</div>
				</div>				
				<div class="mT5 container">
					<div style="float:left; width:35%">
						Disease
					</div>
					<div style="float:left">
						<form:input path="DISEASE_TYPE" class="standardInputMedText "  title="Disease"  maxlength="50" />
					</div>
				</div>			
				<div class="mT5 container">
					<div style="float:left; width:35%">
						Diagnosis Date
					</div>
					<div style="float:left">
						<fmt:formatDate value="${stdTBDocVO.DX_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormatteddxDate" ></fmt:formatDate>
						<form:input id="TB_DX_DATE" path="DX_DATE" value="${theFormatteddxDate}" class="dPicker mask standardInputMedText " datevalidations="completeDate" title="Diagnosis Date" maxlength="10" />
					</div>
					<div class="v_error TB_DX_DATE "></div>
				</div>
			
				
			</div>			
		</div>	
		
		<div style=" width:100%; border:1px dotted; padding-bottom:5px;" class="container" id="tbDocumentFacilityDtls">
			<!--  Facility details -->			
			<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Facility Details</span>					
			</div>
			<div class="mLRTB pB20 facilityDetails">
				<div style="float:left;"> 
					<span class=""> Facility Name </span>
					<span class="ml10"><form:input path="FACILITY_NAME" class="standardInputMedText mR20 facilityFields" title="Facility Name" maxlength="80"/>	</span>
				</div>
				<div style="clear:both"></div>
				<div class="b1" style="margin: 15px 5px 10px 0px">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"> <form:input path="FACILITY_ADDRESS" class="standardinputText newAddress widgetAddressFieldsReadonly facilityFields" maxlength="80" title="Address" />  </span>	 	        
	 	                	            	 	                    
	 	                    <span class="pL30">City</span>
	 	                    <span class="pL10"><form:input path="FACILITY_CITY" class="oAdrDlg standardInputSmallText1 newCity widgetAddressFieldsReadonly facilityFields" title="City" maxlength="30" /></span>
	 	                    
	 	                    <span class="pL30">State </span>
	 	                    <span  class="pL10">
								<form:select id="FACILITY_STATE"  path ="FACILITY_STATE" class="oAdrDlg standardInputSmallText1 newState widgetAddressFieldsReadonly facilityFields" title="State" >
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.stateMap}"/>
							    </form:select>
							</span>
	 	                    
	 	                    <span class="pL30">Zip </span>
	 	                    <span class="pL10"> 
	 	                       <form:input path="FACILITY_ZIP" class="oAdrDlg nOnly  standardInputSmallText1 newZip widgetAddressFieldsReadonly facilityFields" maxlength="5" title="Zip Code" /> - <form:input path="FACILITY_PLUS4" title="Plus4"  class="oAdrDlg nOnly standardInputSmallText1 providerFields" maxlength="4"/>
	 	                    </span> 
	 	                    
	 	                    <span class="pL30">County </span>
	 	                    <span class="pL10"> 
	 	                       <form:select id="FACILITY_COUNTY"   path="FACILITY_COUNTY" class="oAdrDlg standardInputSmallText1 newCounty widgetAddressFieldsReadonly facilityFields" title="County" >
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.countyMap}"/>
							    </form:select>
	 	                    </span>     	                    
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
	 	                </span>
	 	            </div>
	 	            <div class="personInfo pT10 pTRBL10">
	 	            	<span  class="" style="padding-left:59px"><form:input path="rawFacilityAddress" class="standardinputText readonly" maxlength="62" readonly="true" title="Raw Address" /></span>
	 	              	
	 	              	<span class="pL30">Phone </span>
	 	                    <span class="pL10"> 
	 	                       <form:input path="FACILITY_PHONE" class="standardInputMedText mR20 facilityFields" maxlength="20" title="Phone" />
	 	                    </span>
	 	                
	 	                  <span style="float: right;" class="mr50">
								<a href="javascript:openAddressWidget('tbDocumentFacilityDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
						 </span>  
	 	                
	 	                  	              	
	 	                <br>               
	 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
	 	                    
	 	            </div>
	 	        </div>
					
			</div>																	 
		</div>	
		<div style=" width:100%; border:1px dotted; padding-bottom:5px;" class="container" id="tbDocumentProviderDtls">
			<!--  Facility details -->			
			<div id="personDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Provider Details</span>					
			</div>
			<div class="mLRTB pB20 providerDetails">
				<div style="float:left;"> 
					<span class="mL25"> <spring:message code="label.firstname" /></span><span> <form:input path="PROV_FNAME" class="standardInputMedText providerFields" title="First Name" maxlength="48"/> </span> 
					<span style="margin-left:10px"> <spring:message code="label.lastname" /></span><span> <form:input path="PROV_LNAME" class="standardInputMedText providerFields" title="Last Name"  maxlength="48" /> </span>
					</br>
				</div>
				<div style="clear:both"></div>
				<div class="b1" style="margin: 15px 5px 10px 0px">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"> <form:input path="PROV_ADDRESS" class="standardinputText newAddress widgetAddressFieldsReadonly providerFields" maxlength="100" title="Address" />  </span>	 	        
	 	                	            	 	                    
	 	                    <span class="pL30">City</span>
	 	                    <span class="pL10"><form:input path="PROV_CITY" class="oAdrDlg standardInputSmallText1 newCity widgetAddressFieldsReadonly providerFields" title="City" maxlength="60"/></span>
	 	                    
	 	                    <span class="pL30">State </span>
	 	                    <span  class="pL10">
								<form:select id="PROV_STATE"  path ="PROV_STATE" class="oAdrDlg standardInputSmallText1 newState widgetAddressFieldsReadonly providerFields" title="State" >
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.stateMap}"/>
							    </form:select>
							</span>
	 	                    
	 	                    <span class="pL30">Zip </span>
	 	                    <span class="pL10"> 
	 	                       <form:input path="PROV_ZIP" class="oAdrDlg nOnly  standardInputSmallText1 newZip widgetAddressFieldsReadonly providerFields" maxlength="5" title="Zip Code" /> - <form:input path="PROV_PLUS4" title="Plus4"  class="oAdrDlg nOnly standardInputSmallText1 providerFields" maxlength="4"/>
	 	                    </span> 
	 	                    
	 	                    <span class="pL30">County </span>
	 	                    <span class="pL10"> 
	 	                       <form:select id="PROV_COUNTY"   path="PROV_COUNTY" class="oAdrDlg standardInputSmallText1 newCounty widgetAddressFieldsReadonly providerFields" title="County" >
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.countyMap}"/>
							    </form:select>
	 	                    </span>     	                    
	 	                    <br>
	 	                    <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
	 	                </span>
	 	            </div>
	 	            <div class="personInfo pT10 pTRBL10">
	 	            	<span  class="" style="padding-left:59px"><form:input path="rawProviderAddress" class="standardinputText readonly" maxlength="100" readonly="true" title="Raw Address" /></span>
	 	              	
	 	              	<span class="pL30">Phone </span>
 	                    <span class="pL10"> 
 	                       <form:input path="PROV_PHONE" class="standardInputMedText mR20 providerFields" maxlength="16" title="Provider Phone" />
 	                    </span>  
	 	                    
	 	                 <span style="float: right;" class="mr50">
							<a href="javascript:openAddressWidget('tbDocumentProviderDtls');" class="inlineEditButton displayNone editAddress">Edit</a>
						 </span> 	              	
	 	                <br>               
	 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
	 	                    
	 	            </div>
	 	        </div>
					
			</div>																	 
		</div>	
		<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
		<div id="button-row" class="button-row documentButtons" align="center">
		
			<c:if  test="${userPermissions['99'] != null && stdTBDocVO.STATENO != null && stdTBDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}" >
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
				<c:when test="${stdTBDocVO.STATENO != null && stdTBDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
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
</form:form>															
