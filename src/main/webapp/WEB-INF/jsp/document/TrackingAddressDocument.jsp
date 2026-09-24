<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties" %>
<%

pageContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));

%>
 
 
<form:form modelAttribute="stdTrkAddressDocVO" id="stdTrkAddressDocVO" cssClass="documentDescription" >		

	<div id="trkAddressDocVO" style="background-color: transparent">
		
		<div  class="headingCls alignCenter marginBottom30">
				TRACKING ADDRESS DOCUMENT
		</div>
		
		<div style="padding-bottom:10px; text-align: right">
            <span class="mL10">Document ID: </span>
			<span class=" "><form:input path="DOCUMENT_ID" class="standardinputSmallText ml10" maxlength="6" readonly="true"/></span>
        </div>
		<div class="container w100 pB10">
 	         <div style="float:left;  width: 40%">
 	            <div>
 	                <span class=" ml10"><spring:message code="label.stateNo" />: </span>
 	                 <span class="ml10"><form:input path="STATENO" class="standardInputSmallText" maxlength="10" readonly="true"/> </span>
 	            </div>    
 	          </div>
 	          <div style="float:left;  width: 30%">
 	            <div >
 	                <span class=" ml10">	Created By: </span>
 	                <span class="mL10"><form:input path="CREATEID" class="standardInputSmallText ml10" readonly="true"/> </span>
 	           </div>                    
 	         </div>
 	         
 	         <div style="float:left; width: 30%; text-align: right">
 	           <div>
 	                <span class="mL10">Created Date: </span>
 	                <fmt:formatDate value="${stdTrkAddressDocVO.CREATEDT}"  type="date" pattern="MM/dd/yyyy" var="theFormattedCREATEDT" />
 	                <span class="ml10 "><form:input path="CREATEDT" class="standardInputSmallText" maxlength="10" value="${theFormattedCREATEDT}" readonly="true"/> </span>
 	            </div> 
 	         </div> 
 	         
 		</div>	
 		
 		<jsp:include page="statenoDetails.jsp"></jsp:include>
 		

		<!--  Person details -->
		
		<div class="container" style="border:1px dotted; width:100%; padding-bottom:5px;">	 
			<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
				<span class=" mL2">Person Details</span>
			</div>
			<div class="pL10 mLRTB pB20 mT10">
				<div style="float:left">
					<span class=""> First Name</span> 
					<span class="mL20"> <form:input path="FNAME" class="standardInputSmallText" maxlength="20"/> </span>
				</div>
				<div style="float:left">
					<span class="mL20"> Middle Name</span>
					<span class="mL20"> <form:input path="MNAME" class="standardInputSmallText" maxlength="25"/>  </span>
				</div>
				<div style="float:left">
					<span class="mL20"> Last Name</span>
					<span class="mL20"> <form:input path="LNAME" id="lName" title="Last Name" class="required standardInputSmallText" maxlength="25"/>  </span>
				</div>
				<div style="float:left">
					<span class="mL20"> Suffix</span>
					<span class="mL20"><form:input path="SUFFIX" class="standardInputSmallText" maxlength="5"/></span>	
				</div>	
			</div>
			<div class="pL10 mR5 mT10 pB20" >
								
				<div style="float:left;"> 
					<span class=""> Date of Birth </span>
					<span class="ml10"> <form:input id="dobValidation" path="DOB_TEXT" class="dPicker standardInputMedText"  maxlength="10"/> </span>
				</div>		
				<div style="float:left;"> 
					<span class="mL20"> SSN </span>
					<span class="ml10"> <form:input  path="SSN" class=" standardInputMedText"  maxlength="10"/> </span>
				</div>			
						
			</div>
			
			<div class="b1" style="margin: 15px 5px 10px 5px; clear: both">
 	            <div class="personInfo pTRBL10">
 	                <span class="personLabelDoc"> Address </span>
 		                <span> 	                	
	 	                	<span class="pL10"> <form:input path="ADDRESS" class="standardinputText " maxlength="62"/>  </span>	 	        
	 	                	            	 	                    
	 	                    <span class="pL30">City</span>
	 	                    <span class="pL10"><form:input path="ADDRESS_CITY" class="standardInputSmallText1 "  maxlength="30"/></span>
	 	                    
	 	                    <span class="pL30">State </span>
	 	                    <span  class="pL10">
								<form:select id="personState"  path ="ADDRESS_STATE" class="standardInputSmallText1 mR50">
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.stateMap}"/>
							    </form:select>
							    
							</span>
	 	                    
	 	                    <span class="pL30">Zip </span>
	 	                    <span class="pL10"> 
	 	                       <form:input path="ADDRESS_ZIPCODE" class="oAdrDlg nOnly standardInputSmallText1 newZip" maxlength="5" /> - <form:input path="ADDRESS_PLUS4" class="nOnly standardInputSmallText1" maxlength="4"/>
	 	                    </span> 
	 	                    
	 	                    <span class="pL30">County </span>
	 	                    <span class="pL10"> 
	 	                       <form:select id="COUNTY_FIPS"   path="COUNTY_FIPS" class="standardInputSmallText1">
								        <form:option value="" label="--- Select ---" />
										<form:options items="${sessionScope.countyMap}"/>
							    </form:select>
	 	                    </span>     	                    
	 	                </span>
 	            </div>
 	            <div class="personInfo pT10 pTRBL10">
 	              	<span class="">Phone </span>
                    <span class="pL10"> 
                       <form:input path="HOME_PHONE" class=" standardInputSmallText" maxlength="20"/>  
                    </span> 
 	                    
 	            </div>
 	        </div>
								  
		</div>
			
		<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->
	</div>	
</form:form>															
