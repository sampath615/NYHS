<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form:form modelAttribute="stdRawCCDDocVO" id="stdRawCCDDocVO" cssClass="documentDescription">
		<!--  Person details -->
		<div class="container" style=" width:86%">	 
				<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
					<span class=" mL2">Person Details</span>
				</div>
			
				<div class="b1" style="margin: 15px 5px 10px 5px; border:1px dotted; padding: 10px" >
						<span> <spring:message code="label.firstname" /></span><span> <form:input path="personStdData.fName" class="standardInputMedText" /> </span> 
						<span class="mL10"> <spring:message code="label.middleName" /></span><span> <form:input path="personStdData.mName" class="standardInputMedText" /> </span>
						<span class="mL10"> <spring:message code="label.lastname" /></span><span> <form:input path="personStdData.lName" class="standardInputMedText" /> </span></br></br>
						<span >Date of Birth</span><span> <form:input path="personStdData.dobText" class="standardInputMedText" size="10" /> </span>
						<span class="mL10">MPI </span><span class=" "><form:input path="personStdData.mpi" class="standardinputSmallText ml10" maxlength="25" readonly="true"/></span>
				</div>
			
				<div class="b1" style="margin: 15px 5px 10px 5px">
            	<div class="personInfo pTRBL10">
                	<span class="personLabelDoc"> Address </span>
	                	<span> 	             
	                		<span class="pL10">
	                			<form:input path="personStdData.personAddress" class="standardinputText" maxlength="62" readonly="true"/>  
	                		</span>  	 
	                        <span class="pL30">City</span>
	                    		<span class="pL10">
	                    			<form:input path="personStdData.personCity" class="standardinputSmallText1" maxlength="17"/>
	                    		</span>
	                       	<span class="pL30">State </span>
	                    		<span  class="pL10">
									<form:input path="personStdData.personState" class="standardinputSmallText1" maxlength="2"/>
								</span>
	                    
	                    	<span class="pL30">Zip </span>
	                    		<span class="pL10"> 
	                       			<form:input path="personStdData.personZip" class="standardInputSmallText1" maxlength="5"/>   
	                    		</span>    
	                    
	                   		 <span class="pL30">Country </span>
	                    		<span class="pL10"> 
	                       			<form:input path="personStdData.personCountry" class="standardInputSmallText1" maxlength="10"/>   
	                    		</span>
	                    		
	                    	</br></br>
	                    	<span > Cell Phone  </span>
								<span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:input path="personStdData.personPhoneCell" class="standardInputMedText" maxlength="10" /> </span>   
							<span class="pL30"> Home Phone</span>
								<span>&nbsp;&nbsp; <form:input path="personStdData.personPhoneHome" class="standardInputMedText" maxlength="10" /> </span>
							<span class="pL30"> Work Phone</span>
								<span>&nbsp;&nbsp; <form:input path="personStdData.personPhoneWork" class="standardInputMedText" maxlength="10" /> </span>  	                    
	                    <br/>
	                    <br/>
	                    <!-- <span class="labelunderneathAddress"> Street Address</span><span class="dispAddressStdRaw"> (STD)</span> -->
	                </span>
            	</div>
           		</div>
			
		  	<div class="container" style="border:1px dotted; padding:0px 0 5px 5px; margin: 5px 5px 10px 5px;height: 125px">
		  	     <div style="float:left; width:25%; margin-left:30px; margin-top:5px; border-right:1px dotted; height: 175px;">				
					 <div>
						RACE
					</div>
				<div>
					<form:checkbox path="personStdData.raceWhite" value="1"  /> White
					<form:checkbox path="personStdData.raceBlack" value="1"/> African American
				</div>
				<div>
					
				</div>
				<div>
					<form:checkbox path="personStdData.raceAsian" value="1" /> Asian
					<form:checkbox path="personStdData.racePacific" value="1"/> Asian / Pacific Islander
				</div>
				<div>
					
				</div>
				<div>
					<form:checkbox path="personStdData.raceIndian" value="1" /> American Indian / Alaskan Native
				</div>
				<div>
					<form:checkbox path="personStdData.raceOther" value="1"/>Other
					<form:input size="200px" path="personStdData.raceOtherText" class="standardInputMedText"/>
				</div>
				<div>
					<form:checkbox path="personStdData.raceUnknown" value="1"/>Unknown
				</div>
			</div>
			
			<div class="container" style="float:left; width:20% ;border-right: dotted 1px;height: 150px ">
				<div style="padding:5px 0 5px 20px;">
					Ethnicity (Hispanic or Latino) <br/>
						 <form:radiobutton path="personStdData.ethinicity" value="01" /> <spring:message code="label.yes" /> <br>
						  <form:radiobutton path="personStdData.ethinicity" value="02" /> <spring:message code="label.no" /><br>
						  <form:radiobutton path="personStdData.ethinicity" value="09" /> <spring:message code="label.unknown" />
				</div>
			</div> 
			<div class="container" style="float:left; width:15% ;border-right: dotted 1px;height: 150px ">
				<div style="padding:5px 0 5px 20px;">
					
						<div class="floatLeft pR10" style="border-right: dotted 0px;">
							<div class=""> Current Gender </div>
							<div  class="">
								<div> <form:radiobutton path="personStdData.gender" value="M" /> Male</div>
								<div> <form:radiobutton path="personStdData.gender" value="F" /> Female</div>
								<div> <form:radiobutton path="personStdData.gender" value="U" />Unknown</div>		
							</div>	
						</div>
					
				</div>
			</div>
			<div class="container" style="float:left;height: 150px;padding-top: 3px">
				 
				<span class="mL10">Language   </span>
					<span> <form:input path="personStdData.language" class="standardInputMedText" /> </span>	
					
			</div>
			
			</div>
			
			<!-- <div class="addressccdAccordion" style="margin: 20px;">
			
			<h3 class="divHeadingCls bGHeading clearBoth">
		            <span style="left: 2em;top: 50%;margin-top: -11px;position: absolute;"> Addresses</span>                                 
		    </h3>
		    
		     <div class="AddressDetails">
			<c:forEach items="${stdRawCCDDocVO.theAddressList}" var="theAddressList" varStatus="statusHNT">
			
        	<div class="b1" style="margin: 15px 5px 10px 5px; border:1px dotted; padding: 5px" >
		        <div class="pL10">
		        
			         <div class="mLRTB pB10">            
			            <span class="pL10">Person Address</span>
			            <span class="pL10"> <form:input path="theAddressList[${statusHNT.index}].personAddress" class="standardinputText" maxlength="40"/>  </span>
			            <span class="pL10">City</span>
			            <span class="pL10"> <form:input path="theAddressList[${statusHNT.index}].city" class="standardinputMedText ml10" maxlength="20"/>  </span>
			        </div>
			        <div class="mLRTB pB10">            
			            <span class="pL10">State</span>
			            <span class="pL10"> <form:input path="theAddressList[${statusHNT.index}].state" class="standardinputMedText ml10" maxlength="20"/>  </span>
			            
			            <span class="pL10">Zip</span>
			            <span class="pL10"> <form:input path="theAddressList[${statusHNT.index}].zip" class="standardinputMedText ml10" maxlength="20"/>  </span>
			            <span class="pL10">Country</span>
			            <span class="pL10"> <form:input path="theAddressList[${statusHNT.index}].country" class="standardinputMedText ml10" maxlength="20"/>  </span>
			        </div>
			    
			</div>
	   		   </div>
	   		</c:forEach>
	   		</div>
	   		</div>-->
			</div>																				 										
</form:form>