<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set  var="partnerIndex" value="${param.partnerIndex}" />
<div id="ptnrAccordion<c:out value='${ptnrCounter.index}' />" class="accordion" style="margin-bottom: 20px;">	
	<h3 style="padding:1em"><span class="accordionHeader" style="left: 2em;top: 50%;margin-top: -8px;position: absolute;">Partner Document(s)</span>	</h3>
	<div id="partnerDtls<c:out value='${ptnrCounter.index}' />" class="partnerDetailsContent" style="background-color: transparent; border:1px dotted;" class="container">
	
		<!--  Person details -->

		<div id="partnerFormNew<c:out value='${ptnrCounter.index}' />">
			<div class="partnerFormAMain"  style="height:390px">
				<div id="personHIVDetailsLeft<c:out value='${ptnrCounter.index}' />" class="personHIVDetailsLeft">
					<div class="pL30 pT10">
						<span class="personLabelDoc4 bold">Name </span>
						<span> 
							<span ><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.lastName" class="standardInputSmallText mL20"  maxlength="25"/></span> 
							<span ><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.firstName" class="standardInputSmallText"  maxlength="20"/></span>
							<span ><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.middleName" class="standardInputSmallText"  maxlength="25"/></span>
							<span class="labelunderneath4"> Last</span> 
							<span class="labelunderneath4"> First</span>
							<span class="labelunderneath4"> Middle</span>
						</span>
					</div>
					<div class="pL30 pT10">
						<span class="personLabelDoc4 bold">Alias Name</span>
						<span> 
							<span ><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.altName" class="standardInputSmallText mL20"  maxlength="25"/></span> 
						</span>
					</div>
					<div class="pL30 pT10">
						<span class="riskLabelField4 bold">Address</span>
						<span ><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.address" class="standardInputText newAddress"  maxlength="62"/></span>
						<span class="labelunderneath1"> Street </span><span class="" style="padding-left:140px"> Apt</span><br>
					</div>
					<div class="pL30 pT10">
						<span class="bold">City</span>
						<span class="pL10"><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.city" class="oAdrDlg standardInputSmallText1 newCity"  maxlength="30"/></span>
						<span class="pL30 bold">State</span>
						<span class="pL10"><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.state" class="oAdrDlg standardInputSmallText1 newState"  maxlength="2" /> </span>
						<span class="pL30 bold"> Zip </span>
						<span class="pL10"><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.zip" class="oAdrDlg nOnly standardInputSmallText1 newZip" maxlength="5"/></span>
					</div>			
					<div class="pL30 pT10">
						<span class="bold">E-mail Address</span>
						<span class="pL30"><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.email"  class="standardInputText"  maxlength="50"/></span>	
					</div>				
					<div class="pL30 pT10">
						
						<span class="bold">Home Phone</span>
						<span class="pL10"><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.homePhone" class="pNumber standardInputSmallText"  maxlength="20"/></span>
						<span class="pL30 bold">Cell Phone</span>
						<span class="pL10"> <form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.cellPhone" style="width:136px" class="pNumber standardInputSmallText"  maxlength="20"/></span>												
						<br>
					</div>
					<div class="pL30 pT10">
						<span class="bold">Pager</span>
						<span class="pL30"><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.pager" style="width:136px" class="pNumber standardInputSmallText"  maxlength="20"/></span>	
					</div>
					<div class="pL30 pT10">
						<span class="bold">DOB</span>
						<span class="pL10"> <form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.dobText" style="width:136px" class="pNumber standardInputSmallText"  maxlength="10"/></span>
						<span class="pL30 bold">Estimated Age</span>
						<span class="pL30"><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.age" class="pNumber standardInputSmallText1"  maxlength="3"/></span>	
						<br>
					</div>
					<div class="pL30 pT10">	
						<span class="bold"> Gender</span> 
						<span class="pL30"> 
							<span> <form:radiobutton path="partnerList[${ptnrCounter.index}].stdNycprfPartner.currentGender" value="M" /> Male </span>
							<span> <form:radiobutton path="partnerList[${ptnrCounter.index}].stdNycprfPartner.currentGender" value="F" /> Female </span>
							<span> <form:radiobutton path="partnerList[${ptnrCounter.index}].stdNycprfPartner.currentGender" value="U" />Unknown</span>
						</span>
					</div>	
					<div class="pL30 pT10">	
						<span class="bold"> Transgender</span> 
						<span class="pL30"> 
							<span> <form:radiobutton path="partnerList[${ptnrCounter.index}].stdNycprfPartner.currentGender" value="X" /> MTF </span>
							<span> <form:radiobutton path="partnerList[${ptnrCounter.index}].stdNycprfPartner.currentGender" value="Y" /> FTM </span>
							<span> <form:radiobutton path="partnerList[${ptnrCounter.index}].stdNycprfPartner.currentGender" value="T" /> Unspecified </span>
						</span>
						<br>
					</div>	
					<div class="pL30 pT10">
						<span class="fieldHolder50 bold">
							Spouse of patient?
						</span>
						<span class="fieldHolder50">
							<span> <form:radiobutton path="partnerList[${ptnrCounter.index}].stdNycprfPartner.spouse" value="Y" /> Yes </span>
							<span> <form:radiobutton path="partnerList[${ptnrCounter.index}].stdNycprfPartner.spouse" value="N" /> No </span>
						</span> <br>
					</div>
					 <br>
					<div class="clear pL30">
						<span class="fieldHolder50 bold" >
							Domestic violence screen done?
						</span>
						<span class="fieldHolder50">
							<span> <form:radiobutton path="partnerList[${ptnrCounter.index}].stdNycprfPartner.dvScreen" value="Y" /> Yes </span>
							<span> <form:radiobutton path="partnerList[${ptnrCounter.index}].stdNycprfPartner.dvScreen" value="N" /> No </span>
						</span> <br>
					</div>
					 <br>
					<div  class="clear pL30">
						<span class="fieldHolder50 bold">
							If YES, risk of domestic violence identified?
						</span>
						<span class="fieldHolder50">
							<span> <form:radiobutton path="partnerList[${ptnrCounter.index}].stdNycprfPartner.riskViolence" value="Y" /> Yes </span>
							<span> <form:radiobutton path="partnerList[${ptnrCounter.index}].stdNycprfPartner.riskViolence" value="N" /> No </span>
						</span> <br>
					</div>
					 <br>						
				</div>
				<div id="personHIVDetailsRight<c:out value='${ptnrCounter.index}' />" class="personHIVDetailsRight">					
					<div>
						<span class="bold">
							Partner assistance status
						</span>
						<span class="pL10">
							<span> <form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.partnerAssistanceStatus" id="partnerAssistanceStatus" class="standardInputSmallText1"  maxlength="2"/> </span>
						</span> 
						<span style="position:absolute;" class="informationMarkTarget">
							<img src="${staticPath}/images/icons/information.png" class="informationMark" alt="Question Mark" style="width: 16px; height: 16px; vertical-align: top;">
							
						</span>
						<span class="pL30 bold">
							Date notified
						</span>
						<span class="pL10">
							<fmt:formatDate value="${nycPRFDocVO.partnerList[ptnrCounter.index].stdNycprfPartner.informedDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedINFORMED_DATE" />
							<span> <form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.informedDate" value="${theFormattedINFORMED_DATE}" class="standardInputSmallText1"  maxlength="10"/> </span>
						</span> <br>
					</div>
					 <br>
					<div>
						<span class="bold">
							HIV status of partner:
						</span>
						<span class="pL10 fieldHolder50">
							<span> <form:radiobutton path="partnerList[${ptnrCounter.index}].stdNycprfPartner.partnerStatus" value="P" /> Positive </span>
							<span> <form:radiobutton path="partnerList[${ptnrCounter.index}].stdNycprfPartner.partnerStatus" value="N" /> Negative </span>
							<span> <form:radiobutton path="partnerList[${ptnrCounter.index}].stdNycprfPartner.partnerStatus" value="U" /> Unknown </span>
						</span> <br>
					</div>
					 <br>
					<div>
						<span class="fieldHolder50">
							<span>(Patient/partner's self-report OK) </span>
							<span class="pL30 bold"> Date tested </span>
						</span>
						<span class="fieldHolder50">
							<span> <form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.partnerTestDateText" class="standardInputSmallText"  maxlength="10"/> </span>
						</span> <br>
					</div>
					<br>
					<div id="raceEthnicity">
						<span class="container fieldHolder50 " >
							<ul>
								<li>
									<div>
										<span class="bold">Race</span>
									</div>
									<div>
										<form:checkbox path="partnerList[${ptnrCounter.index}].stdNycprfPartner.raceWhite" value="1" /> White	
									</div>
									<div>
										<form:checkbox path="partnerList[${ptnrCounter.index}].stdNycprfPartner.raceBlack" value="1" /> African American
									</div>
									<div>
										<form:checkbox path="partnerList[${ptnrCounter.index}].stdNycprfPartner.raceAsian" value="1" /> Asian
									</div>
									<div>
										<form:checkbox path="partnerList[${ptnrCounter.index}].stdNycprfPartner.raceIndian" value="1" /> American Indian/Alaskan Native
									</div>
									<div>
										<form:checkbox path="partnerList[${ptnrCounter.index}].stdNycprfPartner.racePacific" value="1" /> Pacific
									</div> 
									<div>
										<form:checkbox path="partnerList[${ptnrCounter.index}].stdNycprfPartner.raceOther" value="1" /> Other 
										<form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.raceOtherText" class="standardInputSmallText2"  maxlength="20"/>
									</div>
									<div>
										<form:checkbox path="partnerList[${ptnrCounter.index}].stdNycprfPartner.raceUnknown" value="1" /> Unknown
									</div>	
								</li>
							</ul>		
						</span>	
						<span class="fieldHolder50 borderLD" style="width:49%">
							<ul>
								<li>
									<div>
										<div><Span class="bold">Ethnicity</Span> (Check one) </br> Hispanic or Latino<br></div>
										<div>
											<form:radiobutton path="partnerList[${ptnrCounter.index}].stdNycprfPartner.ethnicity" value="01" /> Yes <br>
											<form:radiobutton path="partnerList[${ptnrCounter.index}].stdNycprfPartner.ethnicity" value="02" /> No <br>
											<form:radiobutton path="partnerList[${ptnrCounter.index}].stdNycprfPartner.ethnicity" value="09" /> Unknown <br>
										</div>
									</div>
								</li>
							</ul>
						</span>
					</div>
					<br>
					<div class="clear">						
						<span class="bold">Complexion</span>
						<span  class="p10"><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.complexion" class="standardInputSmallText1"  maxlength="20"/></span> 
						<span class="pL10 bold">Hair</span>
						<span class="pL10"><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.hair" class="standardInputSmallText1"  maxlength="15"/></span>  
						<span class="pL10 bold">Height</span>
						<span class="pL10"><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.height" class="standardInputSmallText1"  maxlength="15"/></span> 
						<span class="pL10 bold">Weight</span>
						<span class="pL10"><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.weight" class="standardInputSmallText1"  maxlength="20"/></span> 
					</div>
					<br>	
					<div>
						<span class="riskLabelField4 bold">
							Place of employment
						</span>
						<span class="">
							<span> <form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.placeOfEmployment" class="standardInputText"  maxlength="30" /> </span>
						</span> <br>
					</div>
					<br>
					<div class="">
						<span class="bold">Work hours</span>
						<span class="pL30" ><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.workHours" class="standardInputSmallText"  maxlength="25"/></span> 
						<span class="pL30 bold">Work Phone</span>
						<span class="pL30"><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.workPhone" class="standardInputSmallText"  maxlength="30"/></span> 
					</div>
					<br>
					<div class="">
						<span class="bold">Marital status</span>
						<span class="pL30" ><form:input path="partnerList[${ptnrCounter.index}].stdNycprfPartner.maritalCode" class="standardInputSmallText"  maxlength="2"/></span> 
						<span class="pL30 bold">Pregnant</span>
						<span class="pL30">
							<span> <form:checkbox path="partnerList[${ptnrCounter.index}].stdNycprfPartner.pregnant" value="Y" /> Yes </span>
						</span> 
					</div><br>
					<div>
						<span class="bold">Partner elicited by:</span>
						<span class="pL30"><form:checkbox path="" value="Y" /> HD Partner Services	</span>
					</div>							
				</div>
			</div>
		</div>			
		<input type="hidden" id="tempDelete${pCount.index}" name="stdPRFPartnerVOList[${pCount.index}].tempDelete" value="false" />
			
	</div>	
</div>
