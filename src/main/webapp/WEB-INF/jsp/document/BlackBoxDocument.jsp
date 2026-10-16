<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="${staticPath}/css/docs/dthForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<link href="${staticPath}/css/docs/commonForm.css" rel="stylesheet" type="text/css" />
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

<form:form modelAttribute="stdBlackBoxDocVO" id="stdBlackBoxDocVO" cssClass="documentDescription">		
	<form:errors path="*" cssClass="errorblock" element="div"/>

	<div id="blackBoxDoc" style="background-color: transparent">
		
		<div style="padding-left:43%; " class="headingCls">
				BLACK BOX DOCUMENT
		</div>
		<div style="padding-bottom:10px; text-align: right">
            <span class="mL10">Document ID: </span>
			<span class=" "><form:input id="DOCUMENT_ID"  path="stdBlackBoxDoc.documentId" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
        </div>
		<div class="container pB10 w100 pT20">
            <div style="float:left; width: 40%">
	            <div >
	               <span class="ml10"> <spring:message code="label.stateNo" />: </span>
				<span class="ml10"><form:input id="STATENO"  path="stateno" class="standardInputSmallText" maxlength="10" readonly="true"/> </span>
	            </div>    
            </div>
            <div style="float:left;  width: 30%">
	            <div style="">
	                <span class="mL10">	eHARS Import Date: </span>
	                <fmt:formatDate value="${stdBlackBoxDocVO.stdBlackBoxDoc.eharsImportDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
					<span class="mL10"><form:input id="EHARS_IMPORT_DATE"  path="stdBlackBoxDoc.eharsImportDate" value= "${theFormattedDate}" class="standardInputSmallText" readonly="true"/></span>
	            </div>
            </div>
<!--             <div style="float:left; width: 30%; text-align: right"> -->
<!-- 	            <div style=""> -->
<!--  	                <span class="mL10">NYC SHIP Date: </span> -->
<%--  	                <fmt:formatDate value="${stdDeathDocVO.NYC_SHIP_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedNYC_SHIP_DATE_TEXT" ></fmt:formatDate> --%>
<%--  			 		<span class="mL10 "><form:input path="NYC_SHIP_DATE" value="${theFormattedNYC_SHIP_DATE_TEXT}" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span> --%>
<!--  	            </div>                    -->
<!--             </div>      -->
   		</div>
   		<jsp:include page="statenoDetails.jsp"></jsp:include>
		
   		<div class="container" style="border:1px dotted; width:100%; padding-bottom:5px;">	 
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Blackbox matching jurisdiction </span>
			</div>
			<div class="mLRTB pB20">
	            <div class="flw40">
		            <div >
		               <span class="fLW25 bold"> STATENO </span>
						<span class="fLW60"> <form:input id="RIDR_STATENO"  path="stdBlackBoxDoc.ridrStateno" class="standardInputSmallText  " readonly="true"/></span>
		            </div>    
	            </div>
	            <div class="flw30">
		            <div style="">
		                <span class="fLW25 bold">	State </span>
		                <span class="fLW60"> <form:input id="STATE_CODE"  path="stdBlackBoxDoc.stateCode" class="standardInputSmallText  " readonly="true"/></span>
		            </div>
	            </div>
				 <div class="flw30">
		            <div >
		               <span class="fLW25 bold"> Score </span>
					<span class="fLW60"> <form:input id="SCORE"  path="stdBlackBoxDoc.score" class="standardInputSmallText  " readonly="true"/></span>
		            </div>    
	            </div>
				 <div class="fieldHolder25">
		              
	            </div>
   			</div>
   			<div class="mLRTB pB20">
   				<div class="flw40">
		            <div >
		               <span class="fLW25 bold"> Match summary </span>
						<span class="fLW60"> <form:input id="MATCH_SUMMARY"  path="stdBlackBoxDoc.matchSummary" class="standardInputSmallText  " readonly="true"/> </span>
		            </div>      
	            </div>
	            <div class="flw30">
		            <div >
		               <span class="fLW25 bold"> Match Date </span>
						<span class="fLW60"> 
							<fmt:formatDate value="${stdBlackBoxDocVO.stdBlackBoxDoc.receivedDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedReceivedDate" />
							<form:input id="RECEIVED_DATE"  path="stdBlackBoxDoc.receivedDate" value= "${theFormattedReceivedDate}" class="standardInputMedText  " readonly="true"/>
						 </span>
		            </div>      
	            </div>
   			</div>
   		</div>
		
		
		<!--  Person details -->
		
		<div class="container" style="border:1px dotted; width:100%; padding-bottom:5px;">	 
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Person Details</span>
			</div>
			<div class="mLRTB pB20">
				<span class="fieldHolder25">
					<span class="fLW35 bold"> First Name</span> 
					<span class="fLW60"> <form:input id="FIRST_NAME"  path="stdBlackBoxDoc.firstName" class="standardInputSmallText" maxlength="15"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35 bold"> Middle Name</span>
					<span class="fLW60"> <form:input id="MIDDLE_NAME"  path="stdBlackBoxDoc.middleName" class="standardInputSmallText" maxlength="1"/>  </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35 bold"> Last Name</span>
					<span class="fLW60"> <form:input  path="stdBlackBoxDoc.lastName" id="lName" title="Last Name" class="standardInputSmallText required" maxlength="20"/>  </span>
				</span>
			</div>
<!-- 			<div class="mLRTB pB20">				 -->
<!-- 				<span class="fieldHolder25"> -->
<!-- 					<span class="fLW50"> Father's Last Name</span>  -->
<%-- 					<span class="fLW50"> <form:input path="FATHER_LNAME" class="standardInputSmallTextFname" maxlength="20"/> </span> --%>
<!-- 				</span> -->
<!-- 				<span class="fieldHolder25 "> -->
<!-- 					<span class="fLW50"> Mother's Maiden Name</span> -->
<%-- 					<span class="fLW50"> <form:input path="MOTHER_MAIDEN" class="standardInputSmallTextFname" maxlength="20"/>  </span> --%>
<!-- 				</span>										 -->
<!-- 			</div> -->
			<div class="mLRTB pB20">
				<span class="fieldHolder25 ">
					<span class="fLW35 bold"> Date of Birth</span>
					<span class="fLW60"> <form:input id="dobValidation"  path="stdBlackBoxDoc.dobText" class="dPicker standardInputSmallText" maxlength="15"/> </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35 bold"> SSN </span>
					<span class="fLW60"><form:input id="SSN"  path="stdBlackBoxDoc.ssn"  class="standardInputSmallText" maxlength="2"/></span>	
				</span>	
				<span class="fieldHolder25 ">
					<span class=" bold">Vital Status</span>
	                <span class="pL10">
	                	<form:radiobutton id="VITAL_STATUS1"  path="stdBlackBoxDoc.vitalStatus" value="1" /> Alive
	                	<form:radiobutton id="VITAL_STATUS2"  path="stdBlackBoxDoc.vitalStatus" value="2" /> Dead
	                </span>
				</span>
				<span class="fieldHolder25 ">
					<span class="fLW35 bold"> Date of Death </span>
					<span class="fLW60"><form:input id="dodValidation"  path="stdBlackBoxDoc.dodText" title="Date of death" class="dPicker required standardInputSmallText" maxlength="15"/></span>	
				</span>
			</div>

<!-- 			<div class="mLRTB pB20">		 -->
				
<!-- 				<span class="fieldHolder25 "> -->
<!-- 					<span class="fLW35"> Delivery Date</span> -->
<%-- 					<span class="fLW60"> <form:input id="ddValidation" path="DELIVERY_DT" class="standardInputSmallText" maxlength="15"/>  </span> --%>
<!-- 				</span>					 -->
<!-- 			</div> -->

			<!--  Race details -->
		
			<div id="raceEthnicity" class="container" style="border:1px dotted; padding:5px 5px 0 0;">
				<div style="float:left; width:28%;">	
					<div style="padding:0 0 10px 20px;">
	
						<div style="">
	
							<form:checkbox id="RACE_WHITE1"  path="stdBlackBoxDoc.raceWhite" value="1" />White	
							<br>
							<form:checkbox id="RACE_BLACK1"  path="stdBlackBoxDoc.raceBlack" value="1" /> African American
							<br>
							<form:checkbox id="RACE_ASIAN1"  path="stdBlackBoxDoc.raceAsian" value="1" /> Asian
							<br>
							<form:checkbox id="RACE_PACIFIC1"  path="stdBlackBoxDoc.racePacific" value="1" /> Pacific Islander
							<br>
							<form:checkbox id="RACE_INDIAN1"  path="stdBlackBoxDoc.raceIndian" value="1" /> American Indian/Alaskan Native
							<br>
	
							<form:checkbox id="RACE_OTHER1"  path="stdBlackBoxDoc.raceOther" value="1" /> Other
							<br>
							<form:checkbox id="RACE_UNKNOWN1"  path="stdBlackBoxDoc.raceUnknown" value="1" /> Unknown
						</div>	
					</div>
					<div style="padding:10px 0 10px 20px; border-top: dotted 1px;">
						<span class="bold">Ethnicity  (Hispanic or Latino)</span>
						<div style="">
							<form:radiobutton id="ETHNICITY1"  path="stdBlackBoxDoc.ethnicity" value="01" /> Yes <br>
							<form:radiobutton id="ETHNICITY2"  path="stdBlackBoxDoc.ethnicity" value="02" /> No <br>
							<form:radiobutton id="ETHNICITY3"  path="stdBlackBoxDoc.ethnicity" value="09" /> Unknown <br>
						</div>	
					</div>
					
					<div style="padding:10px 0 10px 20px; border-top: dotted 1px;">
						<span class="bold">Birth Sex</span>
						<div style="">
							<form:radiobutton id="BIRTH_SEX1"  path="stdBlackBoxDoc.birthSex" value="M" /> Male <br>
							<form:radiobutton id="BIRTH_SEX2"  path="stdBlackBoxDoc.birthSex" value="F" /> Female <br>
						</div>	
					</div>
				</div>
				
				
				<div style="float:left;  width:36% ; height: 371px; border-right: dotted 1px;  border-left:1px dotted;">
				
					<div class="clearBoth" >
						<div class="mT10 pL15" style="height:80px; border-bottom: dotted 1px;" >						
							<div class="fieldHolder30 ">
								<div class="bold"> Current Gender </div>
								<div  class="">
									<div> <form:radiobutton id="CURRENT_GENDER1"  path="stdBlackBoxDoc.currentGender" value="M" /> Male </div>
									<div> <form:radiobutton id="CURRENT_GENDER2"  path="stdBlackBoxDoc.currentGender" value="F" /> Female </div>
									<div> <form:radiobutton id="CURRENT_GENDER3"  path="stdBlackBoxDoc.currentGender" value="U" />Unknown</div>		
								</div>
							</div>
							<div class="fieldHolder30 ">
								<div class="bold"> Transgender </div>
								<div  class="">
									<div> <form:radiobutton id="CURRENT_GENDER4"  path="stdBlackBoxDoc.currentGender" value="X" /> MTF </div>		
									<div> <form:radiobutton id="CURRENT_GENDER5"  path="stdBlackBoxDoc.currentGender" value="Y" /> FTM </div>		
									<div> <form:radiobutton id="CURRENT_GENDER6"  path="stdBlackBoxDoc.currentGender" value="T" /> Unspecified </div>														
								</div>
							</div>
						</div>
					</div>
					
					<div class="clearBoth" style="padding:5px 0 10px 20px; ">
							
						
						<div class="mT5 container">
							<div class="fieldHolder30">
								<span class="bold">HIV Diagnosis Date</span>
							</div>
							<div class="fieldHolder70">
								<span class="">
					                    <form:input id="HIV_DX_DATE_TEXT"  path="stdBlackBoxDoc.hivDxDateText" class="standardInputSmallText  " readonly="true"/>
								</span>
							</div>
						</div>
				
						<div class="mT5 container">
							<div class="fieldHolder30">
								<span class="bold">HIV Category</span>
							</div>
							<div class="fieldHolder70">
								<span class="">
					                  <form:select id="HIV_CATEG"  path="stdBlackBoxDoc.hivCateg" cssClass="standardInputLongText" title="HIV Category">
											<form:option value="" ></form:option>
											<c:forEach items="${documentRefData}" var="columnData">
												<c:if test="${columnData.refColumn == 'HIV_CATEG' }">
													<form:option value="${columnData.lookupCd}" label="${columnData.lookupName}" name="${columnData.lookupName}"></form:option>
												</c:if>
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
					                    <form:input id="AIDS_DX_DATE_TEXT"  path="stdBlackBoxDoc.aidsDxDateText" class="standardInputSmallText  " readonly="true"/>
								</span>
							</div>
						</div>
					
						<div class="mT5 container">
							<div class="fieldHolder30">
								<span class="bold">AIDS Category</span>
							</div>
							<div class="fieldHolder70">
								<span class="">
					                  <form:select id="AIDS_CATEG"  path="stdBlackBoxDoc.aidsCateg" cssClass="standardInputLongText" title="AIDS Category">
											<form:option value="" ></form:option>
											<c:forEach items="${documentRefData}" var="columnData">
												<c:if test="${columnData.refColumn == 'AIDS_CATEG' }">
													<form:option value="${columnData.lookupCd}" label="${columnData.lookupName}" name="${columnData.lookupName}"></form:option>
												</c:if>
											</c:forEach>
									</form:select>
								</span>
							</div>
						</div>
						
						
						<div class="mT5 container">
							<div class="fieldHolder30">
								<span class="bold">HIV AIDS Diagnosis Date</span>
							</div>
							<div class="fieldHolder70">
								<span class="">
					                    <form:input id="HIV_AIDS_DX_DATE_TEXT"  path="stdBlackBoxDoc.hivAidsDxDateText" class="standardInputSmallText  " readonly="true"/>
								</span>
							</div>
						</div>
						
						
						
						<div class="mT5 container">
							<div class="fieldHolder30">
								<span class="bold">Transmission Category</span>
							</div>
							<div class="fieldHolder70">
								<span class="">
					                  <form:select id="TRANS_CATEG"  path="stdBlackBoxDoc.transCateg" cssClass="standardInputLongText" title="Transmission Category">
											<form:option value="" ></form:option>
											<c:forEach items="${documentRefData}" var="columnData">
												<c:if test="${columnData.refColumn == 'TRANS_CATEG' }">
													<form:option value="${columnData.lookupCd}" label="${columnData.lookupName}" name="${columnData.lookupName}"></form:option>
												</c:if>
											</c:forEach>
									</form:select>
								</span>
							</div>
						</div>
						
					</div>
									
					
				</div>
				<div style="float:left; width:35% ;" >
					<div style="padding:0 0 10px 10px;">
						
						
						<div class="mT5 container">
							<div class="fieldHolder50">
								<span class="bold">Viral Load Recent Value</span>
							</div>
							<div class="fieldHolder50">
								<span class="">
					                    <form:input id="VL_RECENT_VALUE"  path="stdBlackBoxDoc.vlRecentValue" class="standardInputSmallText  "/>
								</span>
							</div>
						</div>
						
						<div class="mT5 container">
							<div class="fieldHolder50">
								<span class="bold">Viral Load Recent date</span>
							</div>
							<div class="fieldHolder50">
								<span class="">
					                    <form:input id="VL_RECENT_DATE_TEXT"  path="stdBlackBoxDoc.vlRecentDateText" class="standardInputSmallText  "/>
								</span>
							</div>
						</div>
						
						<div class="mT5 container">
							<div class="fieldHolder50">
								<span class="bold">CD4 Count Recent value</span>
							</div>
							<div class="fieldHolder50">
								<span class="">
					                    <form:input id="CD4_RECENT_CNT_VALUE"  path="stdBlackBoxDoc.cd4RecentCntValue" class="standardInputSmallText  "/>
								</span>
							</div>
						</div>
						
						<div class="mT5 container">
							<div class="fieldHolder50">
								<span class="bold">CD4 Count Recent date</span>
							</div>
							<div class="fieldHolder50">
								<span class="">
					                    <form:input id="CD4_RECENT_CNT_DATE_TEXT"  path="stdBlackBoxDoc.cd4RecentCntDateText" class="standardInputSmallText  "/>
								</span>
							</div>
						</div>
						
						<div class="mT5 container">
							<div class="fieldHolder50">
								<span class="bold">CD4 Percent Recent value</span>
							</div>
							<div class="fieldHolder50">
								<span class="">
					                    <form:input id="CD4_RECENT_PCT_VALUE"  path="stdBlackBoxDoc.cd4RecentPctValue" class="standardInputSmallText  "/>
								</span>
							</div>
						</div>
						
						<div class="mT5 container">
							<div class="fieldHolder50">
								<span class="bold">CD4 Percent Recent date</span>
							</div>
							<div class="fieldHolder50">
								<span class="">
					                    <form:input id="CD4_RECENT_PCT_DATE_TEXT"  path="stdBlackBoxDoc.cd4RecentPctDateText" class="standardInputSmallText  "/>
								</span>
							</div>
						</div>
						
						<div class="mT5 container">
							<div class="fieldHolder50">
								<span class="bold">Diagnostic Algorithm Criteria Met</span>
							</div>
							<div class="fieldHolder50">
								<span class="">
					                    <form:radiobutton id="XLAB1"  path="stdBlackBoxDoc.xlab" value="Y" /> Yes
	                					<form:radiobutton id="XLAB2"  path="stdBlackBoxDoc.xlab" value="N" /> No
								</span>
							</div>
						</div>
						
						<div class="mT5 container">
							<div class="fieldHolder50">
								<span class="bold">Date of Diagnostic Algorithm</span>
							</div>
							<div class="fieldHolder50">
								<span class="">
					                    <form:input id="XLAB_DATE_TEXT"  path="stdBlackBoxDoc.xlabDateText" class="standardInputSmallText  "/>
								</span>
							</div>
						</div>
						
						<div class="mT5 container">
							<div class="fieldHolder50">
								<span class="bold">HIV Diagnosis Documented by Physician</span>
							</div>
							<div class="fieldHolder50">
								<span class="">
					                     <form:radiobutton id="PHYS_HIV1"  path="stdBlackBoxDoc.physHiv" value="Y" /> Yes
	                					 <form:radiobutton id="PHYS_HIV2"  path="stdBlackBoxDoc.physHiv" value="N" /> No
	                					 <form:radiobutton id="PHYS_HIV3"  path="stdBlackBoxDoc.physHiv" value="U" /> Unknown
								</span>
							</div>
						</div>
						
						<div class="mT5 container">
							<div class="fieldHolder50">
								<span class="bold">Date of Documentation by Physician</span>
							</div>
							<div class="fieldHolder50">
								<span class="">
					                    <form:input id="PHYS_HIV_DATE_TEXT"  path="stdBlackBoxDoc.physHivDateText" class="standardInputSmallText  "/>
								</span>
							</div>
						</div>
						
						<div class="mT5 container">
							<div class="fieldHolder50">
								<span class="bold">Opportunistic Infection Date</span>
							</div>
							<div class="fieldHolder50">
								<span class="">
					                    <form:input id="OI_DX_DATE_TEXT"  path="stdBlackBoxDoc.oiDxDateText" class="standardInputSmallText  "/>
								</span>
							</div>
						</div>
						
						<div class="mT5 container">
							<div class="fieldHolder30">
								<span class="bold">Opportunistic Infection</span>
							</div>
							<div class="fieldHolder70">
								<span class="">
					                    <form:select id="OI_CD"  path="stdBlackBoxDoc.oiCd" class="standardInputLongText  " >
					                    	<form:option value="" ></form:option>
											<c:forEach items="${documentRefData}" var="columnData">
												<c:if test="${columnData.refColumn == 'OI_CD' }">
													<form:option value="${columnData.lookupCd}" label="${columnData.lookupName}" name="${columnData.lookupName}"></form:option>
												</c:if>
											</c:forEach>
					                    </form:select>
								</span>
							</div>
						</div>
						
						
						
						
					</div>
				</div>			
			</div>
			
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">HIV1 Results</span>
			</div>
			<div class="clearBoth b1 pB20 marginBottom20" >
	            <div class="personInfo pTRBL10">
	            	<span class="fieldHolder33">
	                	<span class="fieldHolder25 bold"> Test Code </span>
	                	<span class="fieldHolder75">
	                			<form:select id="LAB_TEST_CD_HIV1"  path="stdBlackBoxDoc.labTestCdHiv1" class="standardInputLongText  "  title="Test Code">
			                    	<form:option value="" ></form:option>
									<c:forEach items="${documentRefData}" var="columnData">
										<c:if test="${columnData.refColumn == 'LAB_TEST_CD_HIV1' }">
											<form:option value="${columnData.lookupCd}" label="${columnData.lookupName}" name="${columnData.lookupName}"></form:option>
										</c:if>
									</c:forEach>
			                    </form:select>
	                	
	                	</span>
	                </span>    
	                <span class="fieldHolder33">
	                    <span class="fLW40 bold">Test Type</span>
	                    <span class="fLW60"> 
	                    	<form:select id="LAB_TEST_TYPE_HIV1"  path="stdBlackBoxDoc.labTestTypeHiv1" class="standardInputSmallText  "  title="Test type">
		                    	<form:option value="" ></form:option>
								<c:forEach items="${documentRefData}" var="columnData">
									<c:if test="${columnData.refColumn == 'LAB_TEST_TYPE_HIV1' }">
										<form:option value="${columnData.lookupCd}" label="${columnData.lookupName}" name="${columnData.lookupName}"></form:option>
									</c:if>
								</c:forEach>
		                    </form:select>
	                    </span>
	                </span>
	                <span class="fieldHolder20">
	                    <span class="fLW40 bold">Sample Date </span>
	                    <span  class="fLW60"><form:input id="SAMPLE_DATE_HIV1_TEXT"  path="stdBlackBoxDoc.sampleDateHiv1Text" class="standardInputSmallText1"/></span>
	                </span>
	               
	            </div>
	            <div class="clearBoth personInfo pTRBL10">
	            	<span class="fieldHolder25">
	                    <span class="fLW40 bold">Result </span>
	                    <span class="fLW60"> 
	                         <form:input id="RESULT_HIV1"  path="stdBlackBoxDoc.resultHiv1" class="oAdrDlg nOnly standardInputSmallText1 " /> 
	                    </span>
	                </span>
	            	<span class="fieldHolder25">
						<span class="fLW40 bold">Result Interpretation </span>
		                   <span class="fLW60"> 
		                   <form:input id="RESULT_INTERPRETATION_HIV1"  path="stdBlackBoxDoc.resultInterpretationHiv1"   class="standardInputSmallText1" />
		                </span>
		            </span>
		            <span class="fieldHolder25">
		                <span class="bold fLW40">Units</span>
		                   <span class="fLW60"> 
		                   <form:input id="RESULT_UNITS_HIV1"  path="stdBlackBoxDoc.resultUnitsHiv1"   class="standardInputSmallText1" />
		                </span>	            
		            </span>
	            </div>
	        </div>
	        
	        
	        
	        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">HIV2 Results</span>
			</div>
			<div class="clearBoth b1 pB20 marginBottom20" >
	            <div class="personInfo pTRBL10">
	            	<span class="fieldHolder33">
	                	<span class="fieldHolder25 bold"> Test Code </span>
	                	<span class="fieldHolder75">
	                			<form:select id="LAB_TEST_CD_HIV2"  path="stdBlackBoxDoc.labTestCdHiv2" class="standardInputLongText  "  title="Test Code">
			                    	<form:option value="" ></form:option>
									<c:forEach items="${documentRefData}" var="columnData">
										<c:if test="${columnData.refColumn == 'LAB_TEST_CD_HIV2' }">
											<form:option value="${columnData.lookupCd}" label="${columnData.lookupName}" name="${columnData.lookupName}"></form:option>
										</c:if>
									</c:forEach>
			                    </form:select>
	                	
	                	</span>
	                </span>    
	                <span class="fieldHolder33">
	                    <span class="fLW40 bold">Test Type</span>
	                    <span class="fLW60"> 
	                    	<form:select id="LAB_TEST_TYPE_HIV2"  path="stdBlackBoxDoc.labTestTypeHiv2" class="standardInputSmallText  "  title="Test type">
		                    	<form:option value="" ></form:option>
								<c:forEach items="${documentRefData}" var="columnData">
									<c:if test="${columnData.refColumn == 'LAB_TEST_TYPE_HIV2' }">
										<form:option value="${columnData.lookupCd}" label="${columnData.lookupName}" name="${columnData.lookupName}"></form:option>
									</c:if>
								</c:forEach>
		                    </form:select>
	                    </span>
	                </span>
	                <span class="fieldHolder20">
	                    <span class="fLW40 bold">Sample Date </span>
	                    <span  class="fLW60"><form:input id="SAMPLE_DATE_HIV2_TEXT"  path="stdBlackBoxDoc.sampleDateHiv2Text" class="standardInputSmallText1"/></span>
	                </span>
	               
	            </div>
	            <div class="clearBoth personInfo pTRBL10">
	            	<span class="fieldHolder25">
	                    <span class="fLW40 bold">Result </span>
	                    <span class="fLW60"> 
	                         <form:input id="RESULT_HIV2"  path="stdBlackBoxDoc.resultHiv2" class="oAdrDlg nOnly standardInputSmallText1 " /> 
	                    </span>
	                </span>
	            	<span class="fieldHolder25">
						<span class="fLW40 bold">Result Interpretation </span>
		                   <span class="fLW60"> 
		                   <form:input id="RESULT_INTERPRETATION_HIV2"  path="stdBlackBoxDoc.resultInterpretationHiv2"   class="standardInputSmallText1" />
		                </span>
		            </span>
		            <span class="fieldHolder25">
		                <span class="bold fLW40">Units</span>
		                   <span class="fLW60"> 
		                   <form:input id="RESULT_UNITS_HIV2"  path="stdBlackBoxDoc.resultUnitsHiv2"   class="standardInputSmallText1" />
		                </span>	            
		            </span>
	            </div>
	        </div>
	        
	        
	        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">HIV3 Results</span>
			</div>
			<div class="clearBoth b1 pB20 marginBottom20" >
	            <div class="personInfo pTRBL10">
	            	<span class="fieldHolder33">
	                	<span class="fieldHolder25 bold"> Test Code </span>
	                	<span class="fieldHolder75">
	                			<form:select id="LAB_TEST_CD_HIV3"  path="stdBlackBoxDoc.labTestCdHiv3" class="standardInputLongText  "  title="Test Code">
			                    	<form:option value="" ></form:option>
									<c:forEach items="${documentRefData}" var="columnData">
										<c:if test="${columnData.refColumn == 'LAB_TEST_CD_HIV3' }">
											<form:option value="${columnData.lookupCd}" label="${columnData.lookupName}" name="${columnData.lookupName}"></form:option>
										</c:if>
									</c:forEach>
			                    </form:select>
	                	
	                	</span>
	                </span>    
	                <span class="fieldHolder33">
	                    <span class="fLW40 bold">Test Type</span>
	                    <span class="fLW60"> 
	                    	<form:select id="LAB_TEST_TYPE_HIV3"  path="stdBlackBoxDoc.labTestTypeHiv3" class="standardInputSmallText  "  title="Test type">
		                    	<form:option value="" ></form:option>
								<c:forEach items="${documentRefData}" var="columnData">
									<c:if test="${columnData.refColumn == 'LAB_TEST_TYPE_HIV3' }">
										<form:option value="${columnData.lookupCd}" label="${columnData.lookupName}" name="${columnData.lookupName}"></form:option>
									</c:if>
								</c:forEach>
		                    </form:select>
	                    </span>
	                </span>
	                <span class="fieldHolder20">
	                    <span class="fLW40 bold">Sample Date </span>
	                    <span  class="fLW60"><form:input id="SAMPLE_DATE_HIV3_TEXT"  path="stdBlackBoxDoc.sampleDateHiv3Text" class="standardInputSmallText1"/></span>
	                </span>
	               
	            </div>
	            <div class="clearBoth personInfo pTRBL10">
	            	<span class="fieldHolder25">
	                    <span class="fLW40 bold">Result </span>
	                    <span class="fLW60"> 
	                         <form:input id="RESULT_HIV3"  path="stdBlackBoxDoc.resultHiv3" class="oAdrDlg nOnly standardInputSmallText1 " /> 
	                    </span>
	                </span>
	            	<span class="fieldHolder25">
						<span class="fLW40 bold">Result Interpretation </span>
		                   <span class="fLW60"> 
		                   <form:input id="RESULT_INTERPRETATION_HIV3"  path="stdBlackBoxDoc.resultInterpretationHiv3"   class="standardInputSmallText1" />
		                </span>
		            </span>
		            <span class="fieldHolder25">
		                <span class="bold fLW40">Units</span>
		                   <span class="fLW60"> 
		                   <form:input id="RESULT_UNITS_HIV3"  path="stdBlackBoxDoc.resultUnitsHiv3"   class="standardInputSmallText1" />
		                </span>	            
		            </span>
	            </div>
	        </div>
	        
	        
			
	        
	        
			
	        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">AIDS Results</span>
			</div>
			<div class="clearBoth b1 pB20" >
	            <div class="personInfo pTRBL10">
	            	<span class="fieldHolder33">
	                	<span class="fieldHolder25 bold"> Test Code </span>
	                	<span class="fieldHolder75">
	                		
	                		<form:select id="LAB_TEST_CD_AIDS"  path="stdBlackBoxDoc.labTestCdAids" class="standardInputLongText  "  title="Test Code">
		                    	<form:option value="" ></form:option>
								<c:forEach items="${documentRefData}" var="columnData">
									<c:if test="${columnData.refColumn == 'LAB_TEST_CD_AIDS' }">
										<form:option value="${columnData.lookupCd}" label="${columnData.lookupName}" name="${columnData.lookupName}"></form:option>
									</c:if>
								</c:forEach>
		                    </form:select>
	                	
	                	</span>
	                </span>
	                <span class="fieldHolder33">    
	                    <span class="fLW40 bold">Test Type</span>
	                    <span class="fLW60"> <form:input id="LAB_TEST_TYPE_AIDS"  path="stdBlackBoxDoc.labTestTypeAids"  class=" standardInputSmallText "  /></span>
					</span>
					<span class="fieldHolder20">	                    
	                    <span class="fLW40 bold">Sample Date </span>
	                    <span  class="fLW60"><form:input id="SAMPLE_DATE_AIDS_TEXT"  path="stdBlackBoxDoc.sampleDateAidsText" class="standardInputSmallText1"/></span>
					</span>
					   
	                
	            </div>
	            <div class="clearBoth personInfo pTRBL10">
	            	<span class="fieldHolder25">	                    	
	                    <span class="fLW40 bold">Result </span>
	                    <span class="fLW60"> 
	                         <form:input id="RESULT_AIDS"  path="stdBlackBoxDoc.resultAids" class="oAdrDlg nOnly standardInputSmallText1 " /> 
	                    </span>
	                </span> 
	            	<span class="fieldHolder25">
	            	  <span class="bold fLW40">Units</span>
		                   <span class="fLW60"> 
		                   <form:input id="RESULT_UNITS_AIDS"  path="stdBlackBoxDoc.resultUnitsAids"   class="standardInputSmallText1" />
		                </span>
		             </span>
	            </div>
	        </div>
	        
	        
	        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Negative Test</span>
			</div>
			<div class="clearBoth b1 pB20" >
	            <div class="personInfo pTRBL10">
	            	<span class="fieldHolder25">
	                	<span class="fLW40 bold"> Test Code </span>
	                	<span class="fLW60"><form:input id="LAB_TEST_CD_NEG" type="text"  path="stdBlackBoxDoc.labTestCdNeg" class="standardInputSmallText" title="Test Code"/>  </span>
	                </span>
	                <span class="fieldHolder25">    
	                    <span class="fLW40 bold">Test Type</span>
	                    <span class="fLW60"> <form:input id="LAB_TEST_TYPE_NEG"  path="stdBlackBoxDoc.labTestTypeNeg"  class=" standardInputSmallText "  /></span>
					</span>
					<span class="fieldHolder25">	                    
	                    <span class="fLW40 bold">Sample Date </span>
	                    <span  class="fLW60"><form:input id="SAMPLE_DT_NEG_TEXT"  path="stdBlackBoxDoc.sampleDtNegText" class="standardInputSmallText1"/></span>
	                </span>
	                <span class="fieldHolder25">
	                    <span class="fLW40 bold">Result </span>
	                    <span class="fLW60"> 
	                         <form:input id="RESULT_NEG"  path="stdBlackBoxDoc.resultNeg" class="oAdrDlg nOnly standardInputSmallText1 " /> 
	                    </span>
		            </span>
		        </div>
		        <div class="clearBoth personInfo pTRBL10">
		        	<span class="fieldHolder25">
		        		<span class="bold fLW40">Result Interpretation </span>
		                   <span class="fLW60"> 
		                   <form:input id="RESULT_INTERPRETATION_NEG"  path="stdBlackBoxDoc.resultInterpretationNeg"   class="standardInputSmallText1" />
		                </span>
		            </span>
		            <span class="fieldHolder25">
			        	 <span class="fLW40 bold ">Last TTH test Date </span>
		                 <span class="fLW60"> 
		    	              <form:input id="TTH_LAST_NEG_DATE_TEXT"  path="stdBlackBoxDoc.tthLastNegDateText"   class="standardInputSmallText" />
	            	    </span>
	            	</span>
	            	<span class="fieldHolder25">   	       
		                <span class="bold fLW40">Units</span>
			                   <span class="fLW60"> 
			                   <form:input id="RESULT_UNITS_NEG"  path="stdBlackBoxDoc.resultUnitsNeg"   class="standardInputSmallText1" />
			            </span>
			        </span>    
		                        
	                
	            </div>
	        </div>
				
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Residential Details</span>
			</div>
			
			<div class="clearBoth b1 " >
	            <div class="personInfo pTRBL10">
	                <span class="personLabelDoc bold"> Address </span>
	                <span> 
	                	<span class="pL10"><form:input id="CUR_STREET_ADDRESS" type="text"  path="curStreetAddress" class="standardInputText" maxlength="39"/>  </span>
	                    
	                    
	                    <span class="pL30 bold">City</span>
	                    <span class="pL10"> <form:input id="CUR_CITY_NAME"  path="stdBlackBoxDoc.curCityName"  class=" standardInputSmallText "  maxlength="2"/></span>
	                    
	                    <span class="pL30 bold">State </span>
	                    <span  class="pL10"><form:input id="CUR_STATE_CD"  path="stdBlackBoxDoc.curStateCd" class="standardInputSmallText1" maxlength="2"/></span>
	                    
	                    <span class="pL30 bold">Zip </span>
	                    <span class="pL10"> 
	                         <form:input id="CUR_ZIP_CD"  path="stdBlackBoxDoc.curZipCd" class="oAdrDlg nOnly standardInputSmallText1 newZip" maxlength="5" /> 
	                    </span>
	                    <span class="bold pL30">County </span>
		                   <span class="pL10"> 
		                   
<%-- 		                   <form:select id="CUR_COUNTY_NAME"   path="PERSON_COUNTY" class="standardInputSmallText1"> --%>
<%-- 								        <form:option value="" label="--- Select ---" /> --%>
<%-- 										<form:options items="${sessionScope.countyMap}"/> --%>
<%-- 							    </form:select> --%>	
		                   <form:input id="CUR_COUNTY_NAME"  path="stdBlackBoxDoc.curCountyName"   class="standardInputSmallText" />
		                </span>       	                    
	                </span>
	            </div>
	            <div class="personInfo pT10 pTRBL10">
<%-- 	            	<span  class="" style="padding-left:59px"><form:input path="PERSON_ADDRESS" class="standardInputText" readonly="true" maxlength="62"/></span> --%>
<!-- 	                <br>                -->
<!-- 	                <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span> -->
						
					
		                <span class=" bold">Address Date</span>
		                <span class="pL10"><form:input id="CUR_ADDRESS_DATE_TEXT"  path="stdBlackBoxDoc.curAddressDateText" style="width:115px" class="standardInputSmallText mask dPicker" datevalidations="partialDate" maxlength="10" title="Address Date"/></span>
		                
		                
<!-- 		                 <span class="pL30 bold">Country </span> -->
<!-- 		                    <span class="pL10">  -->
<%-- 		                         <form:input path="CUR_COUNTRY_CD" class="standardInputSmallText1 " maxlength="5" />  --%>
<!-- 		                    </span> -->
	                    
		                
		            
	   				                    
	            </div><br>


	        </div>
	        
	        
	        
	        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Residence at Disease</span>
			</div>
			
			<div class="clearBoth b1 pB20" >
	            <div class="personInfo pTRBL10">
	                <span class="personLabelDoc bold"> Address </span>
	                <span> 
	                	<span class="pL10"><form:input id="RSD_STREET_ADDRESS" type="text"  path="rsdStreetAddress" class="standardInputText" maxlength="39"/>  </span>
	                    
	                    
	                    <span class="pL30 bold">City</span>
	                    <span class="pL10"> <form:input id="RSD_CITY_NAME"  path="stdBlackBoxDoc.rsdCityName"  class=" standardInputSmallText "  maxlength="2"/></span>
	                    
	                    <span class="pL30 bold">State </span>
	                    <span  class="pL10"><form:input id="RSD_STATE_CD"  path="stdBlackBoxDoc.rsdStateCd" class="standardInputSmallText1" maxlength="2"/></span>
	                    
	                    <span class="pL30 bold">Zip </span>
	                    <span class="pL10"> 
	                         <form:input id="RSD_ZIP_CD"  path="stdBlackBoxDoc.rsdZipCd" class="oAdrDlg nOnly standardInputSmallText1 newZip" maxlength="5" /> 
	                    </span>
	                    <span class="bold pL30">County </span>
		                   <span class="pL10"> 
		                   
<%-- 		                   <form:select id="RSD_COUNTY_NAME"   path="PERSON_COUNTY" class="standardInputSmallText1"> --%>
<%-- 								        <form:option value="" label="--- Select ---" /> --%>
<%-- 										<form:options items="${sessionScope.countyMap}"/> --%>
<%-- 							    </form:select> --%>	
		                   <form:input id="RSD_COUNTY_NAME"  path="stdBlackBoxDoc.rsdCountyName"   class="standardInputSmallText" />
		                </span>       	                    
	                </span>
	            </div>
	            
	            <div class="personInfo pT10 pTRBL10">
					<div class="fieldHolder25">
		                <span class=" bold">Country</span>
		                <span class="pL10"><form:input id="RSD_COUNTRY_CD"  path="stdBlackBoxDoc.rsdCountryCd" style="width:115px" class="standardInputSmallText" title="Country"/></span>
		             </div>
	            </div><br>
	        </div>
	        
	        
	        
	        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Residence at HIV</span>
			</div>
			
			<div class="clearBoth b1 pB20" >
	            <div class="personInfo pTRBL10">
	                <span> 
	                	<span class="bold">City</span>
	                    <span class="pL10"> <form:input id="RSH_CITY_NAME"  path="stdBlackBoxDoc.rshCityName"  class=" standardInputSmallText "  maxlength="2"/></span>
	                    
	                    <span class="pL30 bold">State </span>
	                    <span  class="pL10"><form:input id="RSH_STATE_CD"  path="stdBlackBoxDoc.rshStateCd" class="standardInputSmallText1" maxlength="2"/></span>
	                    
	                    <span class="pL30 bold">Zip </span>
	                    <span class="pL10"> 
	                         <form:input id="RSH_ZIP_CD"  path="stdBlackBoxDoc.rshZipCd" class="oAdrDlg nOnly standardInputSmallText1 newZip" maxlength="5" /> 
	                    </span>
	                    <span class="bold pL30">County </span>
		                   <span class="pL10"> 
		                   <form:input id="RSH_COUNTY_NAME"  path="stdBlackBoxDoc.rshCountyName"   class="standardInputSmallText" />
		                </span>       	                    
	                    <br>
	                </span>
	            </div>
	        </div>
	        
	        <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Residence at AIDS</span>
			</div>
			
			<div class="clearBoth b1 pB20" >
	            <div class="personInfo pTRBL10">
	                <span> 
	                	<span class="bold">City</span>
	                    <span class="pL10"> <form:input id="RSA_CITY_NAME"  path="stdBlackBoxDoc.rsaCityName"  class=" standardInputSmallText "  maxlength="2"/></span>
	                    
	                    <span class="pL30 bold">State </span>
	                    <span  class="pL10"><form:input id="RSA_STATE_CD"  path="stdBlackBoxDoc.rsaStateCd" class="standardInputSmallText1" maxlength="2"/></span>
	                    
	                    <span class="pL30 bold">Zip </span>
	                    <span class="pL10"> 
	                         <form:input id="RSA_ZIP_CD"  path="stdBlackBoxDoc.rsaZipCd" class="oAdrDlg nOnly standardInputSmallText1 newZip" maxlength="5" /> 
	                    </span>
	                    <span class="bold pL30">County </span>
		                   <span class="pL10"> 
		                   <form:input id="RSA_COUNTY_NAME"  path="stdBlackBoxDoc.rsaCountyName"   class="standardInputSmallText" />
		                </span>       	                    
	                    <br>
	                </span>
	            </div>
	        </div>
	        
	        
	        <div class="ClearBoth"></div>	
			<div  class="b1 pB10">
 					<span class="mL30 ">Comments </span><br/>
 					<div class="mL30">
					<span>  <form:textarea id="COMMENTS" title="COMMENTS" maxlength="255" cols="165" rows="8"  path="stdBlackBoxDoc.comments" style="width:65%; height:67px; border:1px solid gray;" class="roundedBorder"></form:textarea></span>

				</div>
		
			</div>
	        
			
	 <div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
		 <div id="button-row" class="button-row documentButtons center">
		 					<c:choose>
								<c:when  test="${param.hasDeleteFunctionality == true}" >	
										<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
								</c:when>
								<c:when test="${userPermissions['90'] != null && stdBlackBoxDocVO.stateno != null && stdBlackBoxDocVO.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null}">
										<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
								</c:when>
							</c:choose>
							
		</div>		
	<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />
	</div>
	</div>
</form:form>															


