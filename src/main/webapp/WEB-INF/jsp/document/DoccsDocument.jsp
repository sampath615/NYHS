<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>
<link href="${staticPath}/css/docs/adapForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />

<script src="${staticPath}/js/docs/doccsDoc.js?v=${buildDate}"></script>

<form:form modelAttribute="stdDoccsDocVO" id="stdDoccsDocVO" cssClass="documentDescription">		

<form:errors path="*" cssClass="errorblock" element="div"/>

	<div style="background-color: transparent">
		
		<div style="padding-left:43%; " class="headingCls">
				DOCCS &nbsp;  DOCUMENT
		</div>
			
		<div class="container paddingBottom5" >
			<div style="float: left">
				 STATENO: <form:input path="STATENO" class="standardInputMedText ml10" />
			</div>	
			<div style="float:right">
				<fmt:formatDate value="${stdDoccsDocVO.DATE_RECEIVED}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDATE_RECEIVED" />
				 DATE RECEIVED: <form:input path="DATE_RECEIVED" value= "${theFormattedDATE_RECEIVED}" class="standardInputMedText ml10" />
			</div>	
					
		</div>
		<div class="container paddingBottom5">			
			<div style="float:left">
				NYSID:<form:input path="NYSID" class="standardInputMedText ml30" readonly="true"/>
			</div>
		</div>
				
		<!--  Person details -->
		
		<div class="container accordion" style=" width:100%; padding-bottom:5px; margin-bottom: 10px">	 
			<c:forEach items="${stdDoccsDocVO.dinDoccsDocVO}" var="dinDoccsDocVO" varStatus="statusHNT">
			<h3 id="psDetailsHeadingDiv" class="divHeadingCls ${stdDoccsDocVO.dinDoccsDocVO[statusHNT.index].DOCUMENT_ID}accordion" index="${statusHNT.index}">
				<span class=" mL2" style="position:relative; margin-left:18px; top:-3px">DIN 
					<c:out value="${stdDoccsDocVO.dinDoccsDocVO[statusHNT.index].DIN}" />
				</span>	
			</h3>
			<div class="dinContainer" style="border: 1px solid #999;">
				<div class=" container w100 " style="margin:10px 0 0 0;">
		 	         <div style="float:left; padding-bottom:10px; width: 40%; ">
		 	            <div style="padding-bottom:10px">
		 	                <span class=" ml10 bold">Document ID: </span>
		 	                 <span class="ml10"><form:input  path="dinDoccsDocVO[${statusHNT.index}].DOCUMENT_ID" class="standardInputMedText" readonly="true"/> </span>
		 	            </div>
		 	            <div style="padding-bottom:10px">
		 	                <span class=" ml10 bold">Doccs File: </span>
		 	                 <span class="ml10"><form:input  path="dinDoccsDocVO[${statusHNT.index}].DOCCS_FILE" class="standardInputMedText mL15" readonly="true"/> </span>
		 	            </div>
		 	          </div>
		 	         <div style="float:right; padding-bottom:10px; width:40%; text-align: right; margin-right:10px">
		 	            <div style="padding-bottom:10px">
		 	                <span class=" ml10 bold">	eHARS Import Date: </span>
		 	                <fmt:formatDate value="${stdDoccsDocVO.dinDoccsDocVO[statusHNT.index].EHARS_IMPORT_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
		 			 		<span class="pL10"><form:input path="dinDoccsDocVO[${statusHNT.index}].EHARS_IMPORT_DATE" value="${theFormattedDate}" class="standardInputSmallText ml10" readonly="true"/> </span>
		 	           </div>                  
		 	         	<div style="">
		 	                <span class="mL10 bold">NYC SHIP Date: </span>
		 	                <fmt:formatDate value="${stdDoccsDocVO.dinDoccsDocVO[statusHNT.index].NYC_SHIP_DATE}"  type="date" pattern="MM/dd/yyyy" var="theFormattedNYC_SHIP_DATE_TEXT" ></fmt:formatDate>
		 			 		<span class="pL10 "><form:input path="dinDoccsDocVO[${statusHNT.index}].NYC_SHIP_DATE" value="${theFormattedNYC_SHIP_DATE_TEXT}" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
		 	            </div> 
		 	         </div>     
		 		</div>			
				<div style="border: dotted 1px; padding: 5px">	
					<div class="mLRTB">
		                    <span class="">
		                            <span class="pL10 bold"> First Name</span> 
		                            <span class="pL10"> <form:input path="dinDoccsDocVO[${statusHNT.index}].FNAME" class="standardInputSmallText"  maxlength="20"/> </span>
		                    </span>
		                    <span class=" ">
		                            <span class="pL20 bold"> Last Name</span>
		                            <span class="pL10"> <form:input path="dinDoccsDocVO[${statusHNT.index}].LNAME" class="standardInputSmallText" maxlength="20" /> </span>
		                    </span>
		                     <span class=" ">
		                            <span class="pL20 bold"> Middle Name</span>
		                            <span class="pL10"> <form:input path="dinDoccsDocVO[${statusHNT.index}].MNAME" class="standardInputSmallText" maxlength="20" /> </span>
		                    </span>
		                    <span class=" ">
		                            <span class="pL20 bold"> Suffix</span>
		                            <span class="pL10"> <form:input path="dinDoccsDocVO[${statusHNT.index}].SUFFIX" class="standardInputSmallText" maxlength="20" /> </span>
		                    </span>
		                                
		            </div>
		            <div class="mLRTB">		                    				
							<span class="">
								<span class="pL10 bold">	SSN	</span>
								<span class="mL40">	<form:input path="dinDoccsDocVO[${statusHNT.index}].SSN" class="standardInputSmallText " maxlength="40"/></span>
							</span>	
		                    <span class=" ">
		                            <span class="pL20 bold">Date of Birth</span>
		                            <span class="pL10"> <form:input path="dinDoccsDocVO[${statusHNT.index}].DOB" class="standardInputSmallText"  maxlength="25"/> </span>
		                    </span>
		                    <span class=" ">
		                            <span class="pL20 bold">Date of Death</span>
		                            <span class="pL10"> <form:input path="dinDoccsDocVO[${statusHNT.index}].DOD_TEXT" class="standardInputSmallText"  maxlength="3"/> </span>        
		                    </span>               
		            </div>
		            <div class="b1" style="margin-left:0px">
		            	<div class="divHeadingCls" style="background:#DDDDDD; ">
				           <span class=" mL2">Person Address</span>                  
				       </div>
			            <div class="personInfo pTRBL10">
			                <span class="personLabelDoc bold">Address </span>
			                <span> 
			                    <span class="pL10"><form:input path="dinDoccsDocVO[${statusHNT.index}].STREET"  class="standardInputText " /></span>
			                    
			                    
			                    <span class="pL30 bold">City</span>
			                    <span class="pL10"><form:input path="dinDoccsDocVO[${statusHNT.index}].PERSON_CITY"  class=" standardInputSmallText " /></span>
			                    
			                    <span class="pL30 bold">State </span>
			                    <span  class="pL10"><form:input path="dinDoccsDocVO[${statusHNT.index}].PERSON_STATE"  class=" standardInputSmallText1 " style="width:50px"/></span>
			                    
			                    <span class="pL30 bold">Zip </span>
			                    <span class="pL10"> 
			                        <form:input path="dinDoccsDocVO[${statusHNT.index}].PERSON_ZIP"  class="standardInputSmallText1 " maxlength="5"/>
			                    </span>
			                    
					            <span class="pL30 bold">County </span>
							     <span class="pL10">  
							     	 <form:select path="dinDoccsDocVO[${statusHNT.index}].PERSON_COUNTY"  cssClass="oAdrDlg newCounty standardInputSmallText1 roundedBrdr" disabled="true"> 
										<form:option value=""></form:option>
										<form:options items="${sessionScope.countyMap}"/>
									 </form:select>
							    </span> 
			                    
			                    <br>
			                    <span class="labelunderneath3"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
			                    
			                </span>
			            </div>
			            <div class="personInfo pT10 pTRBL10">
			                    <span class="" style="padding-left:58px"><form:input path="dinDoccsDocVO[${statusHNT.index}].PERSON_ADDRESS"  class="standardInputText" /></span>	                               
				                
				                <br>
				                <span class="labelunderneath3"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
					            
			            </div><br>
			        </div>
			        
			        <div class="b1" style="margin-left:0px">
		            	<div class="divHeadingCls" style="background:#DDDDDD; ">
				           <span class=" mL2">Previous Address</span>                  
				       </div>
			            <div class="personInfo pTRBL10">
			                <span class="personLabelDoc bold">Address </span>
			                <span> 
			                    <span class="pL10"><form:input path="dinDoccsDocVO[${statusHNT.index}].PREV_STREET"  class="standardInputText " /></span>
			                    
			                    
			                    <span class="pL30 bold">City</span>
			                    <span class="pL10"><form:input path="dinDoccsDocVO[${statusHNT.index}].PREV_PERSON_CITY"  class=" standardInputSmallText " /></span>
			                    
			                    <span class="pL30 bold">State </span>
			                    <span  class="pL10"><form:input path="dinDoccsDocVO[${statusHNT.index}].PREV_PERSON_STATE"  class=" standardInputSmallText1 " style="width:50px"/></span>
			                    
			                    <span class="pL30 bold">Zip </span>
			                    <span class="pL10"> 
			                        <form:input path="dinDoccsDocVO[${statusHNT.index}].PREV_PERSON_ZIP"  class="standardInputSmallText1 " maxlength="5"/>
			                    </span>
			                    <br>
			                    <span class="labelunderneath3"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
			                    
			                </span>
			            </div>
			            <div class="personInfo pT10 pTRBL10">
			                    <span class="" style="padding-left:58px"><form:input path="dinDoccsDocVO[${statusHNT.index}].PREV_PERSON_ADDRESS"  class="standardInputText" /></span>	                               
				                
				                <br>
				                <span class="labelunderneath3"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
					            
			            </div><br>
			        </div>
			        
			        <div class="b1" style="margin-left:0px">
			        	<div class="divHeadingCls" style="background:#DDDDDD; ">
				           <span class=" mL2">Legal Address</span>                  
				       </div>
			            <div class="personInfo pTRBL10">
			                <span class="personLabelDoc bold">Address </span>
			                <span> 
			                    <span class="pL10"><form:input path="dinDoccsDocVO[${statusHNT.index}].LSTREET"  class="standardInputText " /></span>
			                    
			                    
			                    <span class="pL30 bold">City</span>
			                    <span class="pL10"><form:input path="dinDoccsDocVO[${statusHNT.index}].LEGAL_PERSON_CITY"  class=" standardInputSmallText " /></span>
			                    
			                    <span class="pL30 bold">State </span>
			                    <span  class="pL10"><form:input path="dinDoccsDocVO[${statusHNT.index}].LEGAL_PERSON_STATE"  class=" standardInputSmallText1 " style="width:50px"/></span>
			                    
			                    <span class="pL30 bold">Zip </span>
			                    <span class="pL10"> 
			                        <form:input path="dinDoccsDocVO[${statusHNT.index}].LEGAL_PERSON_ZIP"  class="standardInputSmallText1 " maxlength="5"/>
			                    </span>
			                    
					            <span class="pL30 bold">County </span>
							     <span class="pL10">  
							     	 <form:select path="dinDoccsDocVO[${statusHNT.index}].LEGAL_PERSON_COUNTY_NAME"  cssClass="oAdrDlg newCounty standardInputSmallText1 roundedBrdr" disabled="true"> 
										<form:option value=""></form:option>
										<form:options items="${sessionScope.countyMap}"/>
									 </form:select>
							    </span> 
			                    
			                    <br>
			                    <span class="labelunderneath3"> Street Address</span><span class="dispAddressStdRaw"> (Raw)</span>
			                    
			                </span>
			            </div>
			            <div class="personInfo pT10 pTRBL10">
			                    <span class="" style="padding-left:58px"><form:input path="dinDoccsDocVO[${statusHNT.index}].LEGAL_PERSON_ADDRESS"  class="standardInputText" /></span>	                               
				                
				                <br>
				                <span class="labelunderneath3"> Street Address</span><span class="dispAddressStdRaw"> (Std)</span>
					            
			            </div><br>
			        </div>
					<c:choose>
						<c:when test="${stdDinDoccsDocVO.UNDER_CUSTODY == 'Y'}"> 
					        <div class="b1" style="margin-left:0px">
					           <div class="divHeadingCls" style="background:#DDDDDD; ">
						           <span class=" mL2">Facility information</span>                  
						       </div>
					            <div style="margin-top:10px">
					            	 <span class="">
				                            <span class="pL10 bold"> Current Facility Name</span> 
				                            <span class="pL10"> <form:input path="dinDoccsDocVO[${statusHNT.index}].CURRENT_FACILITY_NAME" class="standardInputSmallText"  maxlength="20"/> </span>
				                    </span>
				                </div>
					            
					            <div class="personInfo pTRBL10">
					                <span class="personLabelDoc  bold"> Current Facility Address </span>
					                <span> 
					                    <span class="pL10"><form:input path="dinDoccsDocVO[${statusHNT.index}].CURRENT_FACILITY_ADDRESS"  class="standardInputText newAddress" style="width:330px"/></span>
					                    
					                    
					                    <span class="pL30 bold">City</span>
					                    <span class="pL10"><form:input path="dinDoccsDocVO[${statusHNT.index}].CURRENT_FACILITY_CITY"  class="oAdrDlg standardInputSmallText1 newCity" /></span>
					                    
					                    <span class="pL30 bold">State </span>
					                    <span  class="pL10"><form:input path="dinDoccsDocVO[${statusHNT.index}].CURRENT_FACILITY_STATE"  class="oAdrDlg standardInputSmallText newCity" style="width:50px"/></span>
					                    
					                    <span class="pL30 bold">Zip </span>
					                    <span class="pL10"> 
					                        <form:input path="dinDoccsDocVO[${statusHNT.index}].CURRENT_FACILITY_ZIP"  class="oAdrDlg nOnly standardInputSmallText1 newZip" maxlength="5"/>
					                    </span>
					                    
					                    <span class="pL30 bold">County </span>
					                    <span class="pL10">
					                    	 <form:select path="dinDoccsDocVO[${statusHNT.index}].CURRENT_FACILITY_COUNTY"  cssClass="oAdrDlg newCounty standardInputSmallText1 roundedBrdr" disabled="true"> 
												<form:option value=""></form:option>
												<form:options items="${sessionScope.countyMap}"/>
											 </form:select>  
					                    </span>
					                </span>
					            </div>
					            <div class="personInfo pT10 pTRBL10">
					                <span class=" bold">Phone</span>
					                <span class="pL10"><form:input path="dinDoccsDocVO[${statusHNT.index}].CURRENT_FACILITY_PHONE"  class="pNumber standardInputSmallText" maxlength="14"/>&nbsp;&nbsp;</span>
					                
					                    
					            </div><br>
					        </div>
					      </c:when>
					      <c:otherwise>
					      	<div class="b1" style="margin-left:0px">
					      		<div class="divHeadingCls" style="background:#DDDDDD; ">
						           <span class=" mL2">Facility information</span>                  
						       </div>
					            <div style="margin-top:10px">
					            	 <span class="">
				                            <span class="pL10 bold"> Owning Facility Name</span> 
				                            <span class="pL10"> <form:input path="dinDoccsDocVO[${statusHNT.index}].OWNING_FACILITY_NAME" class="standardInputSmallText"  maxlength="20"/> </span>
				                    </span>
				                </div>
					            
					            <div class="personInfo pTRBL10">
					                <span class="personLabelDoc bold"> Owning Facility Address </span>
					                <span> 
					                    <span class="pL10"><form:input path="dinDoccsDocVO[${statusHNT.index}].OWNING_FACILITY_ADDRESS"  class="standardInputText newAddress" style="width:330px"/></span>
					                    
					                    
					                    <span class="pL30 bold">City</span>
					                    <span class="pL10"><form:input path="dinDoccsDocVO[${statusHNT.index}].OWNING_FACILITY_CITY"  class="oAdrDlg standardInputSmallText1 newCity" /></span>
					                    
					                    <span class="pL30 bold">State </span>
					                    <span  class="pL10"><form:input path="dinDoccsDocVO[${statusHNT.index}].OWNING_FACILITY_STATE"  class="oAdrDlg standardInputSmallText newCity" style="width:50px"/></span>
					                    
					                    <span class="pL30 bold">Zip </span>
					                    <span class="pL10"> 
					                        <form:input path="dinDoccsDocVO[${statusHNT.index}].OWNING_FACILITY_ZIP"  class="oAdrDlg nOnly standardInputSmallText1 newZip" maxlength="5"/>
					                    </span>
					                    
					                    <span class="pL30 bold">County </span>
					                    <span class="pL10">
					                    	 <form:select path="dinDoccsDocVO[${statusHNT.index}].OWNING_FACILITY_COUNTY"  cssClass="oAdrDlg newCounty standardInputSmallText1 roundedBrdr" disabled="true"> 
												<form:option value=""></form:option>
												<form:options items="${sessionScope.countyMap}"/>
											 </form:select>  
					                    </span>
					                </span>
					            </div>
					            <div class="personInfo pT10 pTRBL10">
					                <span class=" bold">Phone</span>
					                <span class="pL10"><form:input path="dinDoccsDocVO[${statusHNT.index}].OWNING_FACILITY_PHONE"  class="pNumber standardInputSmallText" maxlength="14"/>&nbsp;&nbsp;</span>
					                
					            </div><br>
					        </div>
					      
					      </c:otherwise>
					 </c:choose>
		            
		            <div id="raceEthnicity" class="container" style="border:1px dotted; padding:10px 0 5px 0;">
						<div style="float:left; width:35%; height:300px; margin-left:20px; border-right:1px dotted;">
							<div>			
								<div class=" bold"> Race</div> 
								<div>					
									<form:checkbox path="dinDoccsDocVO[${statusHNT.index}].RACE_WHITE" value="1" />White	
									<br>
									<form:checkbox path="dinDoccsDocVO[${statusHNT.index}].RACE_BLACK" value="1" /> African American
									<br>
									<form:checkbox path="dinDoccsDocVO[${statusHNT.index}].RACE_ASIAN" value="1" /> Asian
									<br>
									<form:checkbox path="dinDoccsDocVO[${statusHNT.index}].RACE_INDIAN" value="1" /> American Indian/ Alaskan Native		
									<br>			
									<form:checkbox path="dinDoccsDocVO[${statusHNT.index}].RACE_PACIFIC" value="1" /> Pacific Islander
									<br>
									<form:checkbox path="dinDoccsDocVO[${statusHNT.index}].RACE_OTHER" value="1" /> Other 
									<br>
									<form:checkbox path="dinDoccsDocVO[${statusHNT.index}].RACE_UNKNOWN" value="1" /> Unknown						
								</div>
							</div>
							
							<div class="mT10 pT10 " style="border-top:1px dotted;">
		           				
			                    <span class="bold">Country of birth</span><span class="pL20"> <form:input path="dinDoccsDocVO[${statusHNT.index}].BIRTH_COUNTRY" class="standardInputMedText" />  </span>
		                	</div>
	                		
						</div>
						
						  
			
						<div class=" " style="float:left; width:25% ;">
							<div style=" padding-left:10px;">
								<span class=" bold"><spring:message code="label.ethnicity" />  (Hispanic or Latino)</span>
								<div> 
									<form:radiobutton path="dinDoccsDocVO[${statusHNT.index}].ETHNICITY" value="01" /> <spring:message code="label.yes" /><br>
									<form:radiobutton path="dinDoccsDocVO[${statusHNT.index}].ETHNICITY" value="02" /> <spring:message code="label.no" /><br>
									<form:radiobutton path="dinDoccsDocVO[${statusHNT.index}].ETHNICITY" value="09" /> <spring:message code="label.unknown" />
								</div>
							</div>	
							<div style="padding:15px 0 10px 10px; border-top: dotted 1px; ">
								<div class="floatLeft pR10" style="border-right: dotted 1px;">
									<div class=" bold"> Current Gender </div>
									<div  class="">
										<div> <form:radiobutton path="dinDoccsDocVO[${statusHNT.index}].GENDER" value="M" /> Male </div>
										<div> <form:radiobutton path="dinDoccsDocVO[${statusHNT.index}].GENDER" value="F" /> Female </div>
										<div> <form:radiobutton path="dinDoccsDocVO[${statusHNT.index}].GENDER" value="U" />Unknown</div>		
									</div>
								</div>
								<div class="floatLeft pL10">
									<div class=""> Transgender </div>
									<div  class="">
										<div> <form:radiobutton path="dinDoccsDocVO[${statusHNT.index}].GENDER" value="X" /> MTF </div>		
										<div> <form:radiobutton path="dinDoccsDocVO[${statusHNT.index}].GENDER" value="Y" /> FTM </div>		
										<div> <form:radiobutton path="dinDoccsDocVO[${statusHNT.index}].GENDER" value="T" /> Unspecified </div>														
									</div>
								</div>  
							</div>
						</div>
						
						<!--  Medical details -->
						
						<div style="float:left; width:35%; height: 320px; padding-left:10px; padding-bottom: 12px; border-left:1px dotted;">
							<div class="mT5 container">
								<div style="float:left; width:45%" class="bold">
									DIN
								</div>
								<div style="float:left">
									 <form:input path="dinDoccsDocVO[${statusHNT.index}].DIN" class="standardInputMedText mL5" maxlength="12"/> 
								</div>
							</div>		
							<div class="mT5 container">
								<div style="float:left; width:45%"  class="bold">
									Admin Date
								</div>
								<div style="float:left">
									<form:input path="dinDoccsDocVO[${statusHNT.index}].ADMIN_DATE_TEXT" class="standardInputMedText mL5" maxlength="10"/>
								</div>
							</div>	
							<div class="mT5 container">
								<div style="float:left; width:45%"  class="bold">
									Latest Admin Date
								</div>
								<div style="float:left">
									<form:input path="dinDoccsDocVO[${statusHNT.index}].LATEST_ADMIN_DATE_TEXT" class="standardInputMedText mL5" maxlength="10"/>
								</div>
							</div>
							<div class="mT5 container">
								<div style="float:left; width:45%"  class="bold">
									Entered US Date
								</div>
								<div style="float:left">
									<form:input path="dinDoccsDocVO[${statusHNT.index}].ENTERED_US_DATE_TEXT" class="standardInputMedText mL5" maxlength="10"/>
								</div>
							</div>	
							<div class="mT5 container">
								<div style="float:left; width:45%"  class="bold">
									Earliest Release Date
								</div>
								<div style="float:left">
									<form:input path="dinDoccsDocVO[${statusHNT.index}].EARLIEST_RELEASE_DATE_TEXT" class="standardInputMedText mL5" maxlength="10"/>
								</div>
							</div>
							<div class="mT5 container">
								<div style="float:left; width:45%"  class="bold">
									Latest Release Date
								</div>
								<div style="float:left">
									<form:input path="dinDoccsDocVO[${statusHNT.index}].LATEST_RELEASE_DATE_TEXT" class="standardInputMedText mL5" maxlength="10"/>
								</div>
							</div>	
							<div class="mT5 container">
								<div style="float:left; width:45%"  class="bold">
									Last Release Date
								</div>
								<div style="float:left">
									<form:input path="dinDoccsDocVO[${statusHNT.index}].LRELDA" class=" standardInputMedText mL5" maxlength="10"/>
								</div>
							</div>	
							<div class="mT5 container">
								<div style="float:left; width:45%"  class="bold">
									Latest Discharge Date
								</div>
								<div style="float:left">
									<form:input path="dinDoccsDocVO[${statusHNT.index}].LATEST_DISCHARGE_DATE_TEXT" class="standardInputMedText mL5" maxlength="10"/>
								</div>
							</div>			
							
							<div class="mT5 container">
								<div style="float:left; width:45%"  class="bold">
									Under Custody
								</div>
								<div style="float:left">
									<form:radiobutton path="dinDoccsDocVO[${statusHNT.index}].UNDER_CUSTODY" value="Y" /> <spring:message code="label.yes" />
									<form:radiobutton path="dinDoccsDocVO[${statusHNT.index}].UNDER_CUSTODY" value="N" /> <spring:message code="label.no" />
								</div>
							</div>
							<div class="mT5 container">
								<div style="float:left; width:45%"  class="bold">
									Under Supervision
								</div>
								<div style="float:left">
									<form:radiobutton path="dinDoccsDocVO[${statusHNT.index}].UNDER_SUPERVISION" value="Y" /> <spring:message code="label.yes" />
									<form:radiobutton path="dinDoccsDocVO[${statusHNT.index}].UNDER_SUPERVISION" value="N" /> <spring:message code="label.no" />
								</div>
							</div>	
							
							<div class="mT5 container">
								<div style="float:left; width:45%"  class="bold">
									CIN
								</div>
								<div style="float:left">
									<form:input path="dinDoccsDocVO[${statusHNT.index}].MEDICAID_NUMBER" class=" standardInputMedText mL5" maxlength="10"/>
								</div>
							</div>	
							<div class="mT5 container">
								<div style="float:left; width:45%"  class="bold">
									Alien Status
								</div>
								<div style="float:left">
									<form:input path="dinDoccsDocVO[${statusHNT.index}].ALIEN_STATUS" class=" standardInputMedText mL5" maxlength="10"/>
								</div>
							</div>	
							<div class="mT5 container">
								<div style="float:left; width:45%"  class="bold">
									Residence County
								</div>
								<div style="float:left">
									<form:input path="dinDoccsDocVO[${statusHNT.index}].RESCO" class=" standardInputMedText mL5" maxlength="10"/>
								</div>
							</div>	
							
							<div class="mT5 container">
								<div style="float:left; width:45%"  class="bold">
									Parole Release County
								</div>
								<div style="float:left">
									<form:input path="dinDoccsDocVO[${statusHNT.index}].PAROLE_RELEASE_COUNTY" class=" standardInputMedText mL5" />
								</div>
							</div>				
						</div>
					</div>
					
					
				<c:if test="${not empty stdDoccsDocVO.dinDoccsDocVO[statusHNT.index].doccsProbCodes}">
							<div class="accordion" style="margin: 10px; height: auto">

								<h3 style="" class="divHeadingCls">
									<span class=" mL2"
										style="position: relative; margin-left: 18px; top: -3px">Prob
										Codes</span>
								</h3>
								<div class="prbContainer">
									<c:forEach
										items="${stdDoccsDocVO.dinDoccsDocVO[statusHNT.index].doccsProbCodes}"
										var="doccsProbCodes" varStatus="doccsProbCodesStatusHNT">

										<c:if
											test="${not empty stdDoccsDocVO.dinDoccsDocVO[statusHNT.index].doccsProbCodes[doccsProbCodesStatusHNT.index].PROBLEM_CD}">
											<div class="mLRTB">
												<span class="fieldHolder48"> <span class="fLW17 bold">
														Problem Code</span> <span class="fLW60"> <form:input
															path="dinDoccsDocVO[${statusHNT.index}].doccsProbCodes[${doccsProbCodesStatusHNT.index}].PROBLEM_CD"
															class="standardInputMedText" maxlength="20" />
												</span>

												</span> <span class="fieldHolder48 "> <span
													class="fLW25 bold"> Problem Code Date</span> <span
													class="fLW60"> <form:input
															path="dinDoccsDocVO[${statusHNT.index}].doccsProbCodes[${doccsProbCodesStatusHNT.index}].PROBLEM_CD_DATE"
															class="standardInputSmallText" maxlength="20" />
												</span>
												</span>
											</div>
										</c:if>

										<br>

										<c:if
											test="${not empty stdDoccsDocVO.dinDoccsDocVO[statusHNT.index].doccsProbCodes[doccsProbCodesStatusHNT.index].HIV_DIAG }">
											<div class="mLRTB">
												<span class="fieldHolder48"> <span class="fLW17 bold">
														HIV Diagnosis</span> <span class="fLW60"> <form:input
															path="dinDoccsDocVO[${statusHNT.index}].doccsProbCodes[${doccsProbCodesStatusHNT.index}].HIV_DIAG"
															class="standardInputSmallText" maxlength="20" />
												</span>
												</span> <span class="fieldHolder48 "> <span
													class="fLW25 bold">HIV Diagnosis Date</span> <span
													class="fLW60"> <form:input
															path="dinDoccsDocVO[${statusHNT.index}].doccsProbCodes[${doccsProbCodesStatusHNT.index}].HIV_DIAG_DATE_TEXT"
															class="standardInputSmallText" maxlength="20" />
												</span>
												</span>
											</div>
										</c:if>
										<br>

										<c:if
											test="${not empty stdDoccsDocVO.dinDoccsDocVO[statusHNT.index].doccsProbCodes[doccsProbCodesStatusHNT.index].AIDS_DIAG }">
											<div class="mLRTB">
												<span class="fieldHolder48"> <span class="fLW17 bold">
														AIDs Diagnosis</span> <span class="fLW60"> <form:input
															path="dinDoccsDocVO[${statusHNT.index}].doccsProbCodes[${doccsProbCodesStatusHNT.index}].AIDS_DIAG"
															class="standardInputSmallText" maxlength="20" />
												</span>
												</span> <span class="fieldHolder48 "> <span
													class="fLW25 bold"> AIDs Diagnosis Date</span> <span
													class="fLW60"> <form:input
															path="dinDoccsDocVO[${statusHNT.index}].doccsProbCodes[${doccsProbCodesStatusHNT.index}].AIDS_DIAG_DATE_TEXT"
															class="standardInputSmallText" maxlength="20" />
												</span>
												</span>
											</div>
										</c:if>

									</c:forEach>
								</div>

							</div>
						</c:if>
				</div>		
						
			</div>
			
			</c:forEach>
		</div>		
																		 
	</div>	
	<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
	<div id="button-row" class="button-row documentButtons" align="center">
			<c:if  test="${stdDoccsDocVO.STATENO != null && stdDoccsDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}" >
					<c:if  test="${userPermissions['98'] != null}">	
						<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3 displayNone"  value="Trigger Message"  onclick="confirmAddressForPsMessage()"/>
					</c:if>
<%-- 					<c:if  test="${userPermissions['99'] != null}"> --%>
<!-- 						<input type="button" id="tButton" class="triggetMsgBtnclick button rounded blue effect-3"  value="Trigger Assignment"  onclick="loadSurvAssignmentPopUp()"/>	 -->
<%-- 					</c:if> --%>
			
			</c:if>	
				
	 	<c:choose>
						<c:when  test="${param.hasDeleteFunctionality == true}" >	
								<input type="button" id="dButton" class="deleteBtnclick button rounded blue effect-3"  value="Delete" />
								<input type="button" id="kButton" class="keepBtnclick button rounded blue effect-3"  value="Keep Document" onclick="keepDocument()"/>
						</c:when>
						<c:when test="${userPermissions['90'] != null && stdDoccsDocVO.STATENO != null && stdDoccsDocVO.STATENO != '' && nyehmsStatenoDetails.REASON_DESC == null}">
								<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
						</c:when>
					</c:choose>

	</div>
	<input type="hidden" id="transactionResult" value="${requestScope.TransactionResult}" />	
</form:form>															
