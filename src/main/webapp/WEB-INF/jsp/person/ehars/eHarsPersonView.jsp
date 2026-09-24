<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page
	import="gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link href="${staticPath}/css/docs/commonForm.css" rel="stylesheet"
	type="text/css" />

<div>
	<div class="container pB10 w100 pT10">
		<div style="float: left; width: 40%">
			<div>
				<span class="ml10">eHARS NYS STATENO: </span> <span class="ml10">
				<c:out value="${eharPersonDataObj.STATENO}" /> </span>
			</div>
		</div>
	</div>

	<div class="container"
		style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
		<div id="psDetailsHeadingDiv" class="divHeadingCls"
			style="background: #DDDDDD;">
			<span class=" mL2">Person Details</span>
		</div>
		<!-- <div class="mLRTB pB20"> -->
		<div class="persondata">
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle"><spring:message
								code="label.firstname" /></font>
					</div>
					<div class="personinput">
						<input type="text" name="fname"
							value="${eharPersonDataObj['First Name']}"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle"><spring:message
								code="label.middleName" /></font>
					</div>
					<div class="personinput">
						<input type="text" name="mname"
							value="${eharPersonDataObj['Middle Name']}"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle"><spring:message
								code="label.lastname" /></font>
					</div>
					<div class="personinput">
						<input type="text" name="lname"
							value="${eharPersonDataObj['Last Name']}"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle">SSN</font>
					</div>
					<div class="personinput">
						<input type="text" name="ssn" value="${eharPersonDataObj['SSN']}"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle">Date of Birth</font>
					</div>
					<div class="personinput">
						<input type="text" name="dob"
							value="${eharPersonDataObj['Date of Birth']}"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle">Country of Birth</font>
					</div>
					<div class="personinput">
						<input type="text" name="cob"  value="${eharPersonDataObj['Country of Birth']}"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			
		<!-- 	</div>
		<div class="persondata"> -->
			
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle">Date of Death</font>
					</div>
					<div class="personinput">
						<input type="text" name="dod"
							value="${eharPersonDataObj['Date of Death']}"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle">Jurisdiction of Death</font>
					</div>
					<div class="personinput">
						<input type="text" name="jod" value="${eharPersonDataObj['Jurisdiction of Death']}"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
		</div>
		<div class="persondata">
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle">Birth Sex</font>
					</div>
					<div class="personinput">
						<input type="text" name="sex" value="${eharPersonDataObj['Sex']}" style="width:50px;"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle">Current Gender Identity</font>
					</div>
					<div class="personinput">
						<input type="text" name="currentgender" value="${eharPersonDataObj['Current Gender']}" style="width:50px;"
							 class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
		
		  <div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle">Vital Status</font>
					</div>
					<div class="personinput">
						<input type="text" name="vitalstatus"
							value="${eharPersonDataObj['Vital Status']}"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			
	</div>
		<div class="persondata">
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle">Calculated Race</font>
					</div>
					<div class="personinput">
						<input type="text"
							value="${eharPersonDataObj['Calculated Race']}"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle">Ethnicity</font>
					</div>
					<div class="personinput">
						<input type="text"
							value="${eharPersonDataObj['Ethnicity']}"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			<div class="fLW35">
				<div class="standardInputDiv">
					<div class="fLW30">
						<font class="personInputTitle">Race 1- 5</font>
					</div>
					<div class="personinput">
						<input type="text" value="${eharPersonDataObj['Race 1-5']}"
							class="standardInputText formInputText left floatLeft width336 readonly" style="width:390px;" />
					</div>
				</div>
			</div>
		</div>
		
		<div class="persondata">
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle">Meets CDC HIV Case <br>&nbsp;&nbsp;&nbsp;Definition?</font>
					</div>
					<div class="personinput">
						<input type="text"
							value="${eharPersonDataObj['Meets CDC HIV definition?']}" style="width:50px;"
							 class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle">Meets CDC HIV Case <br>&nbsp;&nbsp;&nbsp;Eligibility?</font>
					</div>
					<div class="personinput">
						<input type="text"
							value="${eharPersonDataObj['Meets CDC HIV eligibility?']}" style="width:50px;"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle">HIV Diagnosis Date</font>
					</div>
					<div class="personinput">
						<input type="text" value="${eharPersonDataObj['HIV DX Date']}"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle">HIV Diagnosis Type</font>
					</div>
					<div class="personinput">
						<input type="text" value="${eharPersonDataObj['HIV DX Type']}"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			
		</div>
		<div class="persondata">
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle">Meets CDC AIDS Case <br>&nbsp;&nbsp;&nbsp;Definition?</font>
					</div>
					<div class="personinput">
						<input type="text"
							value="${eharPersonDataObj['Meets CDC AIDS definition?']}" style="width:50px;"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle">Meets CDC AIDS Case <br>&nbsp;&nbsp;&nbsp;Eligibility?</font>
					</div>
					<div class="personinput">
						<input type="text"
							value="${eharPersonDataObj['Meets CDC AIDS eligibility?']}" style="width:50px;"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle">AIDS Diagnosis Date</font>
					</div>
					<div class="personinput">
						<input type="text" value="${eharPersonDataObj['AIDS DX Date']}"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			<div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
					<font class="personInputTitle">Physician Diagnosis Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Physician Diagnosis Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
			<%-- <div class="placeHolder1 fieldHolder51">
				<div class="standardInputDiv">
					<div class="fLW45">
						<font class="personInputTitle">HIV or AIDS Diagnosis<br>&nbsp;&nbsp;&nbsp;Date - Earlier
						</font>
					</div>
					<div class="personinput">
						<input type="text"
							value="${eharPersonDataObj['HIV or AIDS Dx Date - Earlier']}"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div> --%>
			</div>
		<div class="persondata">
			<div class="flW33">
				<div class="centerDivHolder">

					<font class="personInputTitle">Duplicate Review Summary:</font> <span>
						<input type="radio" class="readonly" name="Review_Summary"
						value="Y"
						${eharPersonDataObj['Display Summary']=='Y'?'checked':''}
						title="Review RIDR Summary" /> Yes
					</span>
					<c:if test="${eharPersonDataObj['Display Summary'] == 'Y'}">
					<span class="infoIcon" id="" onclick="loadRecordRidrSummaryHistory('${eharPersonDataObj['RIDR Values']}')"></span>
					</c:if>

					<span> <input type="radio" class="readonly"
						name="Review_Summary" value="N"
						${eharPersonDataObj['Display Summary']=='N'?'checked':''}
						title="Review RIDR Summary" /> No
					</span>

				</div>
			</div>
		 </div>
	 </div>
	<!-- </div> -->
<div class="container"
	style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
	<div id="psDetailsHeadingDiv" class="divHeadingCls"
		style="background: #DDDDDD;">
		<span class=" mL2">Current Person Address</span>
	</div>
	<div class="persondata">
		<div class="fLW40">
			<div class="standardInputDiv">
				<div style="float: left; width: 90px;">
					<font class="personInputTitle">Address</font>
				</div>
				<div class="personinput">
					<input type="text" style="width: 76%"
						value="${eharPersonDataObj['Current Residence Address']}"
						class="standardInputText formInputText left floatLeft readonly" />
				</div>
			</div>
		</div>
		<div class="fLW20">
			<div class="standardInputDiv">
				<div class="fLW20">
					<font class="personInputTitle">City</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Current Residence City']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW20">
			<div class="standardInputDiv">
				<div class="fLW20">
					<font class="personInputTitle">State</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Current Residence State']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW20">
			<div class="standardInputDiv">
				<div class="fLW20">
					<font class="personInputTitle">Zip</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Current Residence Zip']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="placeHolder1 fieldHolder51">
			<div class="standardInputDiv">
				<div class="" style="float: left;width: 90px;">
					<font class="personInputTitle">County</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Current Residence County']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="placeHolder1 fieldHolder51">
			<div class="standardInputDiv">
				<div class="fLW30">
					<font class="personInputTitle">Address Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Current Residence Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
	</div>

</div>


<div class="container" style="border: 1px dotted; width: 100%; padding-bottom: 5px;">

	<div id="psDetailsHeadingDiv" class="divHeadingCls" style="background: #DDDDDD;"> <span class="mL2">HIV Diagnosis Labs</span> </div>
	
 <div>
	  <div class="divSubHeadingCls"> <span class="mL2">HIV-1 Western Blot</span> </div>
	 
		<div class="persondata">
			<div class="fLW33">
				<div class="standardInputDiv">
					<div  class="fLW30 pB15">
						<font class="personInputTitle">Test Name</font>
					</div>
					<div class="personinput">
						<input type="text"
							value="${eharPersonDataObj['HIV-1 Western Blot Test Name']}"
							class="standardInputText formInputText left floatLeft readonly" />
					</div>
				</div>
			</div>
			<div class="fLW33">
				<div class="standardInputDiv">
					<div  class="fLW30">
						<font class="personInputTitle">Test Date</font>
					</div>
					<div class="personinput">
						<input type="text"
							value="${eharPersonDataObj['HIV-1 Western Blot Test Date']}"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
			<div class="fLW33">
				<div class="standardInputDiv">
					<div  class="fLW30">
						<font class="personInputTitle">Test Result</font>
					</div>
					<div class="personinput">
						<input type="text"
							value="${eharPersonDataObj['HIV-1 Western Blot Test Result']}"
							class="standardInputText formInputText left floatLeft width336 readonly" />
					</div>
				</div>
			</div>
		</div>
  </div>
  <div>
    <div class="divSubHeadingCls"> <span class="mL2">HIV-2 Western Blot</span> </div>
    <div class="persondata">
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30 pB15">
					<font class="personInputTitle">Test Name</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-2 Western Blot Test Name']}"
						class="standardInputText formInputText left floatLeft readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-2 Western Blot Test Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Result</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-2 Western Blot Test Result']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
    </div>
  </div>
  <div>
  <div class="divSubHeadingCls"><span class="mL2">Screening </span> </div>
	<div class="persondata">
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30 pB15">
					<font class="personInputTitle">Test Name</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Screening Test Name']}"
						class="standardInputText formInputText left floatLeft readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Screening Test Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Result</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Screening Test Result']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
     </div>
  </div>
  <div>
  <div class="divSubHeadingCls"> <span class="mL2">HIV-1 IFA</span></div>
	
	<div class="persondata">
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30 pB15">
					<font class="personInputTitle">Test Name</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-1 IFA Test Name']}"
						class="standardInputText formInputText left floatLeft readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-1 IFA Test Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Result</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-1 IFA Test Result']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
	</div>
  </div>
  <div>
   <div class="divSubHeadingCls"><span class="mL2">Differentiating</span> </div>
	<div class="persondata">
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30 pB15">
					<font class="personInputTitle">Test Name</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Differentiating Test Name']}"
						class="standardInputText formInputText left floatLeft readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Differentiating Test Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Result</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Differentiating Test Result']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
	</div>
  </div>
  <div>
  <div class="divSubHeadingCls"> <span class="mL2"> HIV-1 Qualitative Viral Load</span> </div>
	
	<div class="persondata">
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30 pB15">
					<font class="personInputTitle">Test Name</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-1 Qualitative Viral Load Test Name']}"
						class="standardInputText formInputText left floatLeft readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-1 Qualitative Viral Load Test Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Result</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-1 Qualitative Viral Load Test Result']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
	</div>
  </div>
  <div>
  <div class="divSubHeadingCls"> HIV-2 Qualitative Viral Load </span> </div>
	 
	<div class="persondata">
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30 pB15">
					<font class="personInputTitle">Test Name</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-2 Qualitative Viral Load Test Name']}"
						class="standardInputText formInputText left floatLeft readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-2 Qualitative Viral Load Test Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Result</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-2 Qualitative Viral Load Test Result']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
	</div>
  </div>
  <div>
  <div class="divSubHeadingCls"><span class="mL2">HIV-1 Quantitative Viral Load</span></div>
	
	<div class="persondata">
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Name</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-1 Quantitative Viral Load Test Name']}"
						class="standardInputText formInputText left floatLeft readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-1 Quantitative Viral Load Test Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Result</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-1 Quantitative Viral Load Test Result']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Units</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-1 Quantitative Viral Load Units']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30 pB15">
					<font class="personInputTitle">Interpretation</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-1 Quantitative Viral Load Interpretation']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
	</div>
  </div>
  <div>
  <div class="divSubHeadingCls"><span class="mL2">HIV-2 Quantitative Viral Load </span></div>
	<div class="persondata">
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Name</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-2 Quantitative Viral Load Test Name']}"
						class="standardInputText formInputText left floatLeft readonly" />
				</div>
			</div>
		</div>
	<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-2 Quantitative Viral Load Test Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Result</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-2 Quantitative Viral Load Test Result']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Units</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-2 Quantitative Viral Load Units']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30 pB15">
					<font class="personInputTitle">Interpretation</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV-2 Quantitative Viral Load Interpretation']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
	</div>
  </div>
  <div>
  <div class="divSubHeadingCls"><span style="mL2">Genotype</span></div>
	<div class="persondata">
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30 pB15">
					<font class="personInputTitle">Test Name</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Genotype Load Test Name']}"
						class="standardInputText formInputText left floatLeft readonly" />
				</div>
			</div>
		</div>
		<div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Genotype Test Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
	 <div class="fLW33">
			<div class="standardInputDiv">
				<div  class="fLW30">
					<font class="personInputTitle">Test Result</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Genotype Test Result']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
	</div>
   </div>
  
</div>

<div class="container"
	style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
	<div id="psDetailsHeadingDiv" class="divHeadingCls"
		style="background: #DDDDDD;">
		<span class=" mL2">Residence at HIV Diagnosis</span>
	</div>
	<div class="persondata">
		<div class="fLW40">
			<div class="standardInputDiv">
				<div class="" style="float: left; width: 90px;">
					<font class="personInputTitle">Address</font>
				</div>
				<div class="personinput">
					<input type="text" style="width: 76%;"
						value="${eharPersonDataObj['Residence at HIV DX Address']}"
						class="standardInputText formInputText left floatLeft readonly" />
				</div>
			</div>
		</div>
		<div class="fLW20">
			<div class="standardInputDiv">
				<div class="fLW20">
					<font class="personInputTitle">City</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Residence at HIV DX City']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW20">
			<div class="standardInputDiv">
				<div class="fLW20">
					<font class="personInputTitle">State</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Residence at HIV DX State']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW20">
			<div class="standardInputDiv">
				<div class="fLW20">
					<font class="personInputTitle">Zip</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Residence at HIV DX Zip']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="placeHolder1 fieldHolder51">
			<div class="standardInputDiv">
				<div class="" style="float:left; width:90px;">
					<font class="personInputTitle">County</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Residence at HIV DX County']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
	</div>
</div>
<div class="container"
	style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
	<div id="psDetailsHeadingDiv" class="divHeadingCls"
		style="background: #DDDDDD;">
		<span class=" mL2">Facility of HIV Diagnosis</span>
	</div>
	<div class="persondata">
		<div class="fLW40">
			<div class="standardInputDiv">
				<div class="" style="float: left; width: 102px;">
					<font class="personInputTitle">Facility Name</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV DX - Facility Name']}"
						style="width: 74%;"
						class="standardInputText formInputText left floatLeft  readonly" />
				</div>
			</div>
			</div>
			<div class="fLW20">
			<div class="standardInputDiv">
				<div class="fLW20">
					<font class="personInputTitle">County</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV DX - Facility County']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
	</div>
	<div class="persondata">
		<div class="fLW40">
			<div class="standardInputDiv">
				<div class="" style="float: left; width:102px;">
					<font class="personInputTitle">Address</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV DX - Facility Address']}"
						style="width: 74%;"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW20">
			<div class="standardInputDiv">
				<div class="fLW20">
					<font class="personInputTitle">City</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV DX - Facility City']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
        <div class="fLW20">
			<div class="standardInputDiv">
				<div class="fLW20" style="/* float:left; width:104px; */">
					<font class="personInputTitle">State</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV DX - Facility State']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW20">
			<div class="standardInputDiv">
				<div class="fLW20">
					<font class="personInputTitle">Zip</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['HIV DX - Facility Zip Code']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
			
	</div>
</div>
<div class="container"
	style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
	<div id="psDetailsHeadingDiv" class="divHeadingCls"
		style="background: #DDDDDD;">
		<span class=" mL2">AIDS Diagnosis Labs</span>
	</div>
	<div class="persondata">
		<div class="fLW25">
			<div class="standardInputDiv">
				<div class="" style="float: left; width: 90px;">
					<font class="personInputTitle">Count Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['AIDS Dianosis Count Date']}"
						class="standardInputText formInputText left floatLeft readonly" />
				</div>
			</div>
		</div>
		<div class="fLW25">
			<div class="standardInputDiv">
				<div class="fLW30" >
					<font class="personInputTitle">Count Result</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['AIDS Diagnosis Count Result']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW25">
			<div class="standardInputDiv">
				<div class="fLW30">
					<font class="personInputTitle">Percentage Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['AIDS Diagnosis Percentage Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW25">
			<div class="standardInputDiv">
				<div class="fLW30">
					<font class="personInputTitle">Percentage Result</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['AIDS Diagnosis Percentage Result']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
	</div>

</div>
<div class="container"
	style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
	<div id="psDetailsHeadingDiv" class="divHeadingCls"
		style="background: #DDDDDD;">
		<span class=" mL2">Residence at AIDS Diagnosis</span>
	</div>
	<div class="persondata">
		<div class="fLW40">
			<div class="standardInputDiv">
				<div class="" style="float: left; width: 90px;">
					<font class="personInputTitle">Address</font>
				</div>
				<div class="personinput">
					<input type="text" style="width: 76%;"
						value="${eharPersonDataObj['Residence at AIDS DX Address']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW20">
			<div class="standardInputDiv">
				<div class="fLW20">
					<font class="personInputTitle">City</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Residence at AIDS DX City']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW20">
			<div class="standardInputDiv">
				<div class="fLW20">
					<font class="personInputTitle">State</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Residence at AIDS DX State']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW20">
			<div class="standardInputDiv">
				<div class="fLW20">
					<font class="personInputTitle">Zip</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Residence at AIDS DX Zip']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="placeHolder1 fieldHolder51">
			<div class="standardInputDiv">
				<div class="" style="float: left;width: 90px;">
					<font class="personInputTitle">County</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Residence at AIDS DX County']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
	</div>
</div>
<div class="container"
	style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
	<div id="psDetailsHeadingDiv" class="divHeadingCls"
		style="background: #DDDDDD;">
		<span class=" mL2">Facility of AIDS Diagnosis</span>
	</div>
	<div class="persondata">
		<div class="flW40">
			<div class="standardInputDiv">
				<div class="" style="float: left; width: 102px;">
					<font class="personInputTitle">Facility Name</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['AIDS DX - Facility Name']}"
						class="standardInputText formInputText left floatLeft  readonly"
						style="width: 74%;" />
				</div>
			</div>
		</div>
		<div class="fLW20">
			<div class="standardInputDiv">
				<div class="fLW20" >
					<font class="personInputTitle">County</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['AIDS DX - Facility County']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		</div>
		<div class="persondata">
		<div class="fLW40">
			<div class="standardInputDiv">
				<div class="" style="float: left; width: 102px;">
					<font class="personInputTitle">Address</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['AIDS DX - Facility Address']}"
						style="width: 74%;"
						class="standardInputText formInputText left floatLeft readonly" />
				</div>
			</div>
		</div>
		<div class="fLW20">
			<div class="standardInputDiv">
				<div class="fLW20">
					<font class="personInputTitle">City</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['AIDS DX - Facility City']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW20">
			<div class="standardInputDiv">
				<div class="fLW20" style="/* float:left; width:104px; */">
					<font class="personInputTitle">State</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['AIDS DX - Facility State']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW20">
			<div class="standardInputDiv">
				<div class="fLW20">
					<font class="personInputTitle">Zip</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['AIDS DX - Facility Zip Code']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		
	</div>
</div>
<div class="container"
	style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
	<div id="psDetailsHeadingDiv" class="divHeadingCls"
		style="background: #DDDDDD;">
		<span class=" mL2">First Labs After HIV Diagnosis</span>
	</div>
	<div class="persondata">
		<div class="placeHolder1 fieldHolder51">
			<div class="standardInputDiv">
				<div  class="fLW50">
					<font class="personInputTitle">Test Type</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['CD4 Viral Load First HIV Type']}"
						class="standardInputText formInputText left floatLeft readonly" />
				</div>
			</div>
		</div>
		<div class="placeHolder1 fieldHolder51">
			<div class="standardInputDiv">
				<div  class="fLW50">
					<font class="personInputTitle">Test Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['CD4 Viral Load First HIV Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
	</div>
	<div class="persondata">
		<div class="placeHolder1 fieldHolder51">
			<div class="standardInputDiv">
				<div  class="fLW50">
					<font class="personInputTitle">First Detectable VL Test Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['First Detect Viral Load Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="placeHolder1 fieldHolder51">
			<div class="standardInputDiv">
				<div  class="fLW50">
					<font class="personInputTitle">First Detectable VL Resultt</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['First Detect Viral Load Result']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="placeHolder1 fieldHolder51">
			<div class="standardInputDiv">
				<div  class="fLW45">
					<font class="personInputTitle">First CD4 Test Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['CD4 First HIV Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="placeHolder1 fieldHolder51">
			<div class="standardInputDiv">
				<div class="fLW40">
					<font class="personInputTitle">First CD4 Result</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['CD4 First HIV Value']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
	</div>

</div>
<div class="container"
	style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
	<div id="psDetailsHeadingDiv" class="divHeadingCls"
		style="background: #DDDDDD;">
		<span class=" mL2">Most Recent Labs</span>
	</div>
	<div class="persondata">
		<div class="fLW25">
			<div class="standardInputDiv">
				<div class="fLW25">
					<font class="personInputTitle">Test Name</font>
				</div>
				<div class="personinput">
					<input type="text" style="width: 208px;"
						value="${eharPersonDataObj['Most Recent Test Name']}"
						class="standardInputText formInputText left floatLeft readonly" />
				</div>
			</div>
		</div>
		<div class="fLW25">
			<div class="standardInputDiv">
				<div class="fLW45">
					<font class="personInputTitle">Test Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Most Recent Test Date']}"
						class="standardInputText formInputText left floatLeft readonly" />
				</div>
			</div>
		</div>
		<div class="fLW25">
			<div class="standardInputDiv">
				<div class="fLW45">
					<font class="personInputTitle">Test Result</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Most Recent Test Result']}"
						class="standardInputText formInputText left floatLeft readonly" />
				</div>
			</div>
		</div>
	</div>
	<div class="persondata">
		
		<div class="fLW25">
			<div class="standardInputDiv">
				<div class="fLW40">
					<font class="personInputTitle">Viral Load Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Most Recent Viral Load Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW25">
			<div class="standardInputDiv">
				<div class="fLW45">
					<font class="personInputTitle">Viral Load Result</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Most Recent Viral Load Result']}"
						class="standardInputText formInputText left floatLeft readonly" />
				</div>
			</div>
		</div>
		<div class="fLW25">
			<div class="standardInputDiv">
				<div class="fLW45">
					<font class="personInputTitle">Undetected Viral Load Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Most Recent Non Detected Viral load Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
	</div>
	<div class="persondata">
		
		<div class="fLW25">
			<div class="standardInputDiv">
				<div class="fLW40" style="">
					<font class="personInputTitle">CD4 Count Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Most Recent CD4 Count Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW25">
			<div class="standardInputDiv">
				<div class="fLW45">
					<font class="personInputTitle">CD4 Count</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Most Recent CD4 Count']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW25">
			<div class="standardInputDiv">
				<div class="fLW45" style="">
					<font class="personInputTitle">CD4 Percentage Date</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Most Recent CD4 Percentage Date']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		<div class="fLW25">
			<div class="standardInputDiv">
				<div class="fLW40" style="">
					<font class="personInputTitle">CD4 Percentage</font>
				</div>
				<div class="personinput">
					<input type="text"
						value="${eharPersonDataObj['Most Recent CD4 Percentage']}"
						class="standardInputText formInputText left floatLeft width336 readonly" />
				</div>
			</div>
		</div>
		
	</div>

</div>

<div class="container"
	style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
	<div id="psDetailsHeadingDiv" class="divHeadingCls"
		style="background: #DDDDDD;">
		<span class="ui-icon personRiskNavigationIconn ui-icon-plus"
			onclick="loadRiskPage('personRiskNavigationIconn','personRiskSlidingDiv')"></span> <span class=" mL2">Person
			Risks</span>
	</div>
	<div id="personRiskSlidingDiv" style="display: none;">
		<div class="roundedBorder mL10"
			style="width: 98%; height: auto; background: #DDDDDD">

			<div
				style="width: 100%; padding-bottom: 5px; background-color: #DBEAF9">
				<span class="hdrFont mL5"> After 1977 and before the earliest
					known diagnosis of HIV infection, this patient had:</span>
			</div>


			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont"> Sex with male </span>
				</div>
				<div id="patient_sex_with_male"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="Sex_with_male" value="Y"
						${eharPersonDataObj['Sex with Male']=='Y'?'checked':''}
						title="Sex with male" /> Yes </span> <span class="mL5"><input
						type="radio" class="readonly" name="Sex_with_male" value="N"
						${eharPersonDataObj['Sex with Male']=='N'?'checked':''}
						title="Sex with male" /> No </span> <span class="mL5"><input
						type="radio" class="readonly" name="Sex_with_male" value="U"
						${eharPersonDataObj['Sex with Male']=='U'?'checked':''}
						title="Sex with male" /> Unknown </span>
				</div>
			</div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont"> Sex with female </span>
				</div>
				<div id="patient_sex_with_female"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="Sex_with_female" title="Sex with female" value="Y"
						${eharPersonDataObj['Sex with Female']=='Y'?'checked':''} /> Yes
					</span> <span class="mL5"><input type="radio" class="readonly"
						name="Sex_with_female" title="Sex with female" value="N"
						${eharPersonDataObj['Sex with Female']=='N'?'checked':''} /> No </span>
					<span class="mL5"><input type="radio" class="readonly"
						name="Sex_with_female" title="Sex with female" value="U"
						${eharPersonDataObj['Sex with Female']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont"> Intravenous/Injection Drug Use </span>
				</div>
				<div id="patient_injected_non_presc_drugs"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="injected_non_presc_drugs"
						title="Injected non-prescription drugs" value="Y"
						${eharPersonDataObj['Injected Non-Prescription Drug']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name="injected_non_presc_drugs"
						title="Injected non-prescription drugs" value="N"
						${eharPersonDataObj['Injected Non-Prescription Drug']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name="injected_non_presc_drugs"
						title="Injected non-prescription drugsss" value="U"
						${eharPersonDataObj['Injected Non-Prescription Drug']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>



			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont"> Received clotting factor for
						hemophilia/</span><span class="normalFont">coagulation disorder </span>
				</div>
				<div id="patient_received_clotting_factor"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="received_clotting_factor" title="Coagulation disorder"
						value="Y"
						${eharPersonDataObj['Received Clotting Factor']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name="received_clotting_factor" title="Coagulation disorder"
						value="N"
						${eharPersonDataObj['Received Clotting Factor']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name="received_clotting_factor" title="Coagulation disorder"
						value="U"
						${eharPersonDataObj['Received Clotting Factor']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div
				style="width: 100%; padding-bottom: 5px; background-color: #DBEAF9">
				<span class="hdrFont mL5"> HETEROSEXUAL relations with any of
					the following:</span>
			</div>

			<div style="width: 99.9%; border: 1px dotted;"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont"> HETEROSEXUAL contact with IDU </span>
				</div>
				<div id="patient_heterosexual_contact_with_idu"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="heterosexual_contact_with_idu"
						title="Heterosexual Contact With IDU" value="Y"
						${eharPersonDataObj['Het Contact with IDU']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name="heterosexual_contact_with_idu"
						title="Heterosexual Contact With IDU" value="N"
						${eharPersonDataObj['Het Contact with IDU']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name="heterosexual_contact_with_idu"
						title="Heterosexual Contact With IDU" value="U"
						${eharPersonDataObj['Het Contact with IDU']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont"> HETEROSEXUAL contact with bisexual
						male </span>
				</div>
				<div id="patient_heterosexual_contact_with_bisexual_male"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="heterosexual_contact_with_bisexual_male"
						title="Contact with bisexual male" value="Y"
						${eharPersonDataObj['Het Sex w Bisexual Male']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name="heterosexual_contact_with_bisexual_male"
						title="Contact with bisexual male" value="N"
						${eharPersonDataObj['Het Sex w Bisexual Male']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name="heterosexual_contact_with_bisexual_male"
						title="Contact with bisexual male" value="U"
						${eharPersonDataObj['Het Sex w Bisexual Male']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont"> HETEROSEXUAL contact with person
						with hemophilia / coagulation disorder with documented with HIV
						infection </span>
				</div>
				<div id="patient_heterosexual_contact_person_with_hemophilia"
					class="readonly"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="heterosexual_contact_person_with_hemophilia"
						title="Person with hemophilia" value="Y"
						${eharPersonDataObj['Het Sex w HIV Hemophiliac']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name="heterosexual_contact_person_with_hemophilia"
						title="Person with hemophilia" value="N"
						${eharPersonDataObj['Het Sex w HIV Hemophiliac']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name="heterosexual_contact_person_with_hemophilia"
						title="Person with hemophilia" value="U"
						${eharPersonDataObj['Het Sex w HIV Hemophiliac']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont"> HETEROSEXUAL contact with
						transfusion recipient with documented HIV infection </span>
				</div>
				<div id="patient_heterosexual_contact_with_transfusion_recipient"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="heterosexual_contact_with_transfusion_recipient"
						title="Transfusion recipient with Documented HIV infection"
						value="Y"
						${eharPersonDataObj['Het Sex w HIV Transfusion Recip']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name="heterosexual_contact_with_transfusion_recipient"
						title="Transfusion recipient with Documented HIV infection"
						value="N"
						${eharPersonDataObj['Het Sex w HIV Transfusion Recip']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name="heterosexual_contact_with_transfusion_recipient"
						title="Transfusion recipient with Documented HIV infection"
						value="U"
						${eharPersonDataObj['Het Sex w HIV Transfusion Recip']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont"> HETEROSEXUAL contact with
						transplant recipient with documented HIV infection </span>
				</div>
				<div id="patient_heterosexual_contact_with_transplant_recipient"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="heterosexual_contact_with_transplant_recipient"
						title="Transplant recipient with HIV infection" value="Y"
						${eharPersonDataObj['Het Sex w HIV Transplant Recip']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name="heterosexual_contact_with_transplant_recipient"
						title="Transplant recipient with HIV infection" value="N"
						${eharPersonDataObj['Het Sex w HIV Transplant Recip']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name="heterosexual_contact_with_transplant_recipient"
						title="Transplant recipient with HIV infection" value="U"
						${eharPersonDataObj['Het Sex w HIV Transplant Recip']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont"> HETEROSEXUAL contact with HIV
						Person </span>
				</div>
				<div id="patient_heterosexual_contact_with_hiv_person"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="heterosexual_contact_with_hiv_person"
						title="Contact with HIV person" value="Y"
						${eharPersonDataObj['Het Sex w HIV Person']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name="heterosexual_contact_with_hiv_person"
						title="Contact with HIV person" value="N"
						${eharPersonDataObj['Het Sex w HIV Person']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name="heterosexual_contact_with_hiv_person"
						title="Contact with HIV person" value="U"
						${eharPersonDataObj['Het Sex w HIV Person']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>
			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont"> Received tranfustion of
						blood/blood components (other than clotting factor) &nbsp;
						(document reason in Comments section) </span></br> <span class="normalFont"
						id="firstLastDatesReceived"> First date received <input
						type="text" value="${eharPersonDataObj['First Date']}"
						class="standardInputSmallText readonly" maxlength="10"
						title="First Date received" />
					</span>
				</div>
				<div id="patient_received_tranfustion_of_blood"
					style="float: right; width: 19%; padding-top: 15px;">
					<span class="mL5"><input type="radio" class="readonly"
						title="Received tranfustion of blood" value="Y"
						${eharPersonDataObj['Received Transfusion']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						title="Received tranfustion of blood" value="N"
						${eharPersonDataObj['Received Transfusion']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						title="Received tranfustion of blood" value="U"
						${eharPersonDataObj['Received Transfusion']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont"> Received transplant of
						tissue/organs or artificial insemination </span>
				</div>
				<div id="patient_received_transplant_of_tissue"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						title="Received transplant of tissue/organs" value="Y"
						${eharPersonDataObj['Received Transplant or AI']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						title="Received transplant of tissue/organs" value="N"
						${eharPersonDataObj['Received Transplant or AI']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						title="Received transplant of tissue/organs" value="U"
						${eharPersonDataObj['Received Transplant or AI']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">Health Care Worker Risk:</span>
				</div>
				<div id="patient_Worker_risk_in_a_healthcare"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="Worker_risk_in_a_healthcare"
						title="Health Care Worker risk in a healthcare" value="Y"
						${eharPersonDataObj['Health Care Worker Risk']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name="Worker_risk_in_a_healthcare"
						title="Health Care Worker risk in a healthcare" value="N"
						${eharPersonDataObj['Health Care Worker Risk']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name="Worker_risk_in_a_healthcare"
						title="Health Care Worker risk in a healthcare" value="U"
						${eharPersonDataObj['Health Care Worker Risk']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>


			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">No Identified Risk Factor:</span>
				</div>
				<div id="patient_with_no_identified_risk_factor"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="no_identified_risk_factor" title="No Identified Risk Factor"
						value="Y"
						${eharPersonDataObj['No Identified Risk Factor']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name="no_identified_risk_factor" title="No Identified Risk Factor"
						value="N"
						${eharPersonDataObj['No Identified Risk Factor']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name="no_identified_risk_factor" title="No Identified Risk Factor"
						value="U"
						${eharPersonDataObj['No Identified Risk Factor']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont"> Other Risks: </span>
				</div>
				<div id="patient_other_risk"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="other_risk" title="Other risks" value="Y"
						${eharPersonDataObj['Other Risks']=='Y'?'checked':''} /> Yes </span> <span
						class="mL5"><input type="radio" class="readonly"
						name="other_risk" title="Other risks" value="N"
						${eharPersonDataObj['Other Risks']=='N'?'checked':''} /> No </span> <span
						class="mL5"><input type="radio" class="readonly"
						name="other_risk" title="Other risks" value="U"
						${eharPersonDataObj['Other Risks']=='U'?'checked':''} /> Unknown
					</span>
				</div>
			</div>

			<div style="clear: both"></div>
		</div>
	</div>
</div>
<div class="container"
	style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
	<div id="psDetailsHeadingDiv" class="divHeadingCls"
		style="background: #DDDDDD;">
		<span class="ui-icon pediatricsRiskNavigationIconn ui-icon-plus"
			onclick="loadRiskPage('pediatricsRiskNavigationIconn','pediatricRiskSlidingDiv')"></span> <span class=" mL2">Pediatrics
			Risks</span>
	</div>
	<div id="pediatricRiskSlidingDiv" style="display: none;">
		<div class="roundedBorder mL10"
			style="width: 98%; height: auto; background: #DDDDDD">

			<div
				style="width: 100%; padding-bottom: 5px; background-color: #DBEAF9">
				<span class="hdrFont mL5"> After 1977 and before the earliest
					known diagnosis of HIV infection, this child's biological mother had:</span>
			</div> 
			
			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">HIV Infection Status</span>
				</div>
				<div id="mother_hiv_infection_status"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span><input type="text"
						class=" standardInputMedText mT2 readonly" name="mother_infection_status"
						title="mother infection status"
						value="${eharPersonDataObj['Mother HIV Infection Status']}" /></span>
					
				</div>

			</div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">Perinatally acquired HIV infection</span>
				</div>
				<div id="mother_peri_hiv"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5">
					<input type="radio" class="readonly" name="mother_perinatal_hiv" title="mother perinatal hiv" value="Y"
						${eharPersonDataObj['Mother Perinatally HIV infection']=='Y'?'checked':''} />Yes </span>
					<span class="mL5">
					<input type="radio" class="readonly" name="mother_perinatal_hiv" title="mother perinatal hiv" value="N"
						${eharPersonDataObj['Mother Perinatally HIV infection']=='N'?'checked':''} /> No </span> 
					<span class="mL5">
					<input type="radio" class="readonly" name="mother_perinatal_hiv" title="mother perinatal hiv" value="U"
						${eharPersonDataObj['Mother Perinatally HIV infection']=='U'?'checked':''} /> Unknown </span>
				</div>
			</div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">Injected non-prescription Drug</span>
				</div>
				<div id="mother_heterosexual_contact_with_hiv_person"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="mother_heterosexual_contact_with_hiv_person"
						title="Mother Contact with HIV person" value="Y"
						${eharPersonDataObj['Mother Injected Non-Prescription Drug']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name="mother_heterosexual_contact_with_hiv_person"
						title="Mother Contact with HIV person" value="N"
						${eharPersonDataObj['Mother Injected Non-Prescription Drug']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name="mother_heterosexual_contact_with_hiv_person"
						title="Mother Contact with HIV person"  value="U"
						${eharPersonDataObj['Mother Injected Non-Prescription Drug']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>
			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">Heterosexual contact with intravenous/injection drug use</span>
				</div>
				<div id="mother_sex_injection drug_use"
					style="float: right; width: 19%; padding-top: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						title="Mother Sex Intravenous/Injection Drug Use" value="Y"
						${eharPersonDataObj['Mother Het Contact with IDU']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						title="Mother Sex Intravenous/Injection Drug Use" value="N"
						${eharPersonDataObj['Mother Het Contact with IDU']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						title="Mother Sex Intravenous/Injection Drug Use" value="U"
						${eharPersonDataObj['Mother Het Contact with IDU']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>
			
						<div style="float: left; width: 99%; padding-bottom: 5px;"
							class="mL5 mR5 mT2">
							<div
								style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
								<span class="normalFont">Heterosexual contact with bisexual male</span>
							</div>
							<div id="mother_sex_bisexual_male"
								style="float: right; width: 19%; padding-top: 5px;">
								<span class="mL5"><input type="radio" class="readonly"
									title="Mother Sex bisexual male" value="Y"
									${eharPersonDataObj['Mother Het Contact with bisexual male']=='Y'?'checked':''} />
									Yes </span> <span class="mL5"><input type="radio" class="readonly"
									title="Mother Sex bisexual male" value="N"
									${eharPersonDataObj['Mother Het Contact with bisexual male']=='N'?'checked':''} />
									No </span> <span class="mL5"><input type="radio" class="readonly"
									title="Mother Sex bisexual male" value="U"
									${eharPersonDataObj['Mother Het Contact with bisexual male']=='U'?'checked':''} />
									Unknown </span>
							</div>
						</div>
			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">Heterosexual contact with person with hemophilia/cogulation disorder</span>
				</div>
				<div id="mother_het_sex_hemo"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						title="Mother Het Sex w HIV Hemophiliac" value="Y"
						${eharPersonDataObj['Mother Het Sex w HIV Hemophiliac']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						title="Mother Het Sex w HIV Hemophiliac" value="N"
						${eharPersonDataObj['Mother Het Sex w HIV Hemophiliac']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						title="Mother Het Sex w HIV Hemophiliac" value="U"
						${eharPersonDataObj['Mother Het Sex w HIV Hemophiliac']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>
			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">Heterosexual contact with transfusion recipient with documented HIV infection </span>
				</div>
				<div id="mother_het_sex_w_hiv_transfusion"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name= "mother_het_sex_hiv_transfusion" title="Mother Het Sex w HIV Transfusion Recip" value="Y"
						${eharPersonDataObj['Mother Het Sex w HIV Transfusion Recip']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name= "mother_het_sex_hiv_transfusion" title="Mother Het Sex w HIV Transfusion Recip" value="N"
						${eharPersonDataObj['Mother Het Sex w HIV Transfusion Recip']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name= "mother_het_sex_hiv_transfusion" title="Mother Het Sex w HIV Transfusion Recip" value="U"
						${eharPersonDataObj['Mother Het Sex w HIV Transfusion Recip']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>
            <div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">Heterosexual contact with transplant recipient with documented HIV infection</span>
				</div>
				<div id="mother_het_sex_transplant"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name ="mother_het_w_sex_transplant" title="Mother Het Sex w HIV Transplant" value="Y"
						${eharPersonDataObj['Mother Het Sex w HIV Transplant Recip']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name ="mother_het_w_sex_transplant" title="Mother Het Sex w HIV Transplant" value="N"
						${eharPersonDataObj['Mother Het Sex w HIV Transplant Recip']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name ="mother_het_w_sex_transplant" title="Mother Het Sex w HIV Transplant" value="U"
						${eharPersonDataObj['Mother Het Sex w HIV Transplant Recip']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>
			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">Sex with Male </span>
				</div>
				<div id="mother_sex_w_male"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="mother_sex_with_male" title="Mother Sex w Male" value="Y"
						${eharPersonDataObj['Mother Sex w Male']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name="mother_sex_with_male" title="Mother Sex w Male" value="N"
						${eharPersonDataObj['Mother Sex w Male']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name="mother_sex_with_male" title="Mother Sex w Male" value="U"
						${eharPersonDataObj['Mother Sex w Male']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>
			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">Received transfusion of blood/blood components (other than clotting factor) </span>
				</div>
				<div id="mother_received_transfusion"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name= "mother_transfusion" title="Mother Received Transfusion" value="Y"
						${eharPersonDataObj['Mother Received Transfusion']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name= "mother_transfusion" title="Mother Received Transfusion" value="N"
						${eharPersonDataObj['Mother Received Transfusion']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name= "mother_transfusion" title="Mother Received Transfusion" value="U"
						${eharPersonDataObj['Mother Received transfusion']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>
			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">Received transplant of tissue/organs or artificial insemination </span>
				</div>
				<div id="mother_received_transplant"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						title="Mother Received transplant" value="Y" name="mother_received_transplant"
						${eharPersonDataObj['Mother Received Transplant']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						title="Mother Received transplant" value="N" name="mother_received_transplant"
						${eharPersonDataObj['Mother Received Transplant']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						title="Mother Received transplant" value="U" name="mother_received_transplant"
						${eharPersonDataObj['Mother Received Transplant']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>

            <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
			
			<div
				style="width: 100%; padding-bottom: 5px; background-color: #DBEAF9">
				<span class="hdrFont mL5"> Before the diagnosis of HIV Infection, this child had:</span>
			</div> 
			
			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>
			

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">Sexual contact with male </span>
				</div>
				<div id="child_sex_with_male"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5">
					    <input type="radio" class="readonly" name="child sex with male" value="Y" ${eharPersonDataObj['Child Sex w Male']=='Y'?'checked':''} title="Child Sex with male" /> Yes </span> 
					<span class="mL5">
					    <input type="radio" class="readonly" name="child sex with male" value="N" ${eharPersonDataObj['Child Sex w Male']=='N'?'checked':''} title="Child Sex with male" /> No </span>
					<span class="mL5">
						<input type="radio" class="readonly" name="child sex with male" value="U" ${eharPersonDataObj['Child Sex w Male']=='U'?'checked':''}  title="Child Sex with male" /> Unknown </span>
				</div>
			</div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">Sexual contact with female </span>
				</div>
				<div id="child_sex_with_female"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="Child_Sex_w_female" title="Sex with female" value="Y"
						${eharPersonDataObj['Child Sex w femalee']=='Y'?'checked':''} /> Yes
					</span> <span class="mL5"><input type="radio" class="readonly"
						name="Child_Sex_with_female" title="Sex with female" value="N"
						${eharPersonDataObj['Child Sex w female']=='N'?'checked':''} /> No </span>
					<span class="mL5"><input type="radio" class="readonly"
						name="Child_Sex_with_female" title="Sex with female" value="U"
						${eharPersonDataObj['Child Sex w female']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">Intravenous/Injection Drug Use </span>
				</div>
				<div id="child_injected_non_presc_drugs"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="child_injected_non_presc_drugs"
						title="Child Injected non-prescription drugs" value="Y"
						${eharPersonDataObj['Child Injected Non-Prescription Drug']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name="child_injected_non_presc_drugs"
						title="Child Injected non-prescription drugs" value="N"
						${eharPersonDataObj['Child Injected Non-Prescription Drug']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name="child_injected_non_presc_drugs"
						title="Child Injected non-prescription drugs" value="U"
						${eharPersonDataObj['Child Injected Non-Prescription Drug']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>



			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">Received clotting factor for hemophilia/coagulation disorder</span>
                 <!-- for hemophilia/</span><span class="normalFont">coagulation disorder </span> -->
				</div>
				<div id="child_received_clotting_factor"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="child_received_clotting_factor" title="child received clotting factor"
						value="Y"
						${eharPersonDataObj['Child Received Clotting Factor']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name="child_received_clotting_factor" title="child received clotting factor"
						value="N"
						${eharPersonDataObj['Child Received Clotting Factor']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name="child_received_clotting_factor" title="child received clotting factor"
						value="U"
						${eharPersonDataObj['Child Received Clotting Factor']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">Received transfusion of blood/blood components (other than clotting factor) </span></br> 
						<span class="normalFont" id="childtransfusionfirstLastDatesReceived"> First date received <input
						type="text" value="${eharPersonDataObj['Child Received Transfusion First Date']}" class="standardInputMedText readonly" maxlength="10" title="Child Received Transfusion First Date received" />
					</span>
				</div>
				<div id="child_received_transfusion"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="child_transfusion"
						title="child received transfusion" value="Y"
						${eharPersonDataObj['Child Received Transfusion']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name="child_transfusion"
						title="child received transfusion" value="N"
						${eharPersonDataObj['Child Received Transfusion']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name="child_transfusion"
						title="child received transfusion" value="U"
						${eharPersonDataObj['Child Received Transfusion']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">Received transplant or tissue/organs or artificial insemination</span>
				</div>
				<div id="child_received_transplant"
					class="readonly"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="child_receive_transplant"
						title="Received Transplant" value="Y"
						${eharPersonDataObj['Child Received Transplant']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name="child_receive_transplant"
						title="Child Received Transplant" value="N"
						${eharPersonDataObj['Child Received Transplant']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name="child_receive_transplant"
						title="Child Received Transplant" value="U"
						${eharPersonDataObj['Child Received Transplant']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>

            <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
            
			<div style="float: left; width: 99%; padding-bottom: 5px;"
							class="mL5 mR5">
							<div
								style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
								<span class="normalFont"> Other Risk: </span>
							</div>
							<div id="child_other_risk"
								style="float: right; width: 19%; padding-bottom: 5px;">
								<span class="mL5"><input type="radio" class="readonly"
									name="child_other_risks" title="Child Other risks" value="Y"
									${eharPersonDataObj['Child Other Risks']=='Y'?'checked':''} /> Yes </span> <span
									class="mL5"><input type="radio" class="readonly"
									name="child_other_risks" title="Child Other Risks" value="N"
									${eharPersonDataObj['Child Other Risks']=='N'?'checked':''} /> No </span> <span
									class="mL5"><input type="radio" class="readonly"
									name="child_other_risks" title="Child Other Risks" value="U"
									${eharPersonDataObj['Child Other Risks']=='U'?'checked':''} /> Unknown
								</span>
							</div>
						</div>
			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

			<div style="float: left; width: 99%; padding-bottom: 5px;"
				class="mL5 mR5 mT2">
				<div
					style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">No identified risk factor:  </span>
				</div>
				<div id="child_ni_risk"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span class="mL5"><input type="radio" class="readonly"
						name="child_nir" title="child nir" value="Y"
						${eharPersonDataObj['Child No Identified Risk Factor']=='Y'?'checked':''} />
						Yes </span> <span class="mL5"><input type="radio" class="readonly"
						name="child_nir" title="child nir" value="N"
						${eharPersonDataObj['Child No Identified Risk Factor']=='N'?'checked':''} />
						No </span> <span class="mL5"><input type="radio" class="readonly"
						name="child_nir" title="child nir" value="U"
						${eharPersonDataObj['Child No Identified Risk Factor']=='U'?'checked':''} />
						Unknown </span>
				</div>
			</div>
			
	<div style="clear: both"></div>
		</div>
	</div>
</div>
<!--end of pediatrics risk  -->
<div class="container"
	style="border: 1px dotted; width: 100%; padding-bottom: 5px;">
	<div id="psDetailsHeadingDiv" class="divHeadingCls"
		style="background: #DDDDDD;">
		<span class="ui-icon oiNavigationIconn ui-icon-plus"
			onclick="loadRiskPage('oiNavigationIconn','oiSlidingDiv')"></span> <span class=" mL2">Opportunistic Illnesses</span>
	</div>
	<div id="oiSlidingDiv" style="display: none;">
		<div class="roundedBorder mL10"
			style="width: 98%; height: auto; background: #DDDDDD">

 
			
			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

				<div style="float: left; width: 99%; padding-bottom: 5px;"
					class="mL5 mR5 mT2">
					<div
						style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">Bacterial infection, multiple or recurrent</span>
					</div>
					<div id="bacterial_infection_recurr"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text" class=" standardInputSmallText mT2 readonly" name="bacterial_infection_recur" title="bacterial infection recurr" value="${eharPersonDataObj['Bacterial infection, multiple or recurrent']}" /> </span>
                   </div>

				</div>

		   <div style="width: 99.9%; border: 1px dotted;" class="container"></div>

				<div style="float: left; width: 99%; padding-bottom: 5px;"
					class="mL5 mR5 mT2">
					<div
						style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">Candidiasis, bronchi, trachea, or lungs</span>
					</div>
					<div id="candidiasis_bronchi"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="Candidiasis_bronchi" title="Candidiasis bronchi"
							value="${eharPersonDataObj['Candidiasis, bronchi, trachea, or lungs']}" /> </span>
                    </div>
               </div>

			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>

				<div style="float: left; width: 99%; padding-bottom: 5px;"
									class="mL5 mR5 mT2">
						<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
							<span class="normalFont">Candidiasis, esophageal</span>
						</div>
						<div id="candidiasis_esophageal"
							style="float: right; width: 19%; padding-bottom: 5px;">
							<span><input type="text"
								class=" standardInputSmallText mT2 readonly"
								name="Candidiasis_esophageal" title="Candidiasis esophageal "
								value="${eharPersonDataObj['Candidiasis, esophageal']}" /> </span>
	                    </div>
				 </div>
			
			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>
                
                <div style="float: left; width: 99%; padding-bottom: 5px;"
									class="mL5 mR5 mT2">
						<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
							<span class="normalFont">Carcinoma, invasive cervical</span>
						</div>
						<div id="carcinoma_invasive_cervical"
							style="float: right; width: 19%; padding-bottom: 5px;">
							<span><input type="text"
								class=" standardInputSmallText mT2 readonly"
								name="Carcinom_invasiv_cervical" title="Carcinoma invasive cervical"
								value="${eharPersonDataObj['Carcinoma, invasive cervical']}" /> </span>
	                    </div>
				 </div>
				 
			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>
                
                <div style="float: left; width: 99%; padding-bottom: 5px;"
									class="mL5 mR5 mT2">
						<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
							<span class="normalFont">Coccidioidomycosis, disseminated or extrapulmonary</span>
						</div>
						<div id="coccidio_dissemi_extrapulmonary"
							style="float: right; width: 19%; padding-bottom: 5px;">
							<span><input type="text"
								class=" standardInputSmallText mT2 readonly"
								name="Coccidio_dissemi_extrapulmonary" title="Coccidio dissemi extrapu"
								value="${eharPersonDataObj['Coccidioidomycosis, disseminated or extrapulmonary']}" /> </span>
	                    </div>
				 </div>
			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>
                
                <div style="float: left; width: 99%; padding-bottom: 5px;"
									class="mL5 mR5 mT2">
						<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
							<span class="normalFont">Cryptococcosis, extrapulmonary</span>
						</div>
						<div id="cryptococcosis_extrapulmonary"
							style="float: right; width: 19%; padding-bottom: 5px;">
							<span><input type="text"
								class=" standardInputSmallText mT2 readonly"
								name="Cryptococcosis_extrapulmonary" title="Cryptococcosis extrapulmonary"
								value="${eharPersonDataObj['Cryptococcosis, extrapulmonary']}" /> </span>
	                    </div>
				 </div>
			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>
                
                <div style="float: left; width: 99%; padding-bottom: 5px;"
									class="mL5 mR5 mT2">
						<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
							<span class="normalFont">Cryptosporidiosis, chronic intestinal (>1 mo. duration)</span>
						</div>
						<div id="cryptospori_chronic_intes"
							style="float: right; width: 19%; padding-bottom: 5px;">
							<span><input type="text"
								class=" standardInputSmallText mT2 readonly"
								name="Cryptospori_chronic_intes" title="Cryptospori chronic intestinal "
								value="${eharPersonDataObj['Cryptosporidiosis, chronic intestinal (>1 mo. duration)']}" /> </span>
	                    </div>
				 </div>
		   <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
                
                <div style="float: left; width: 99%; padding-bottom: 5px;"
									class="mL5 mR5 mT2">
						<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
							<span class="normalFont">Cytomegalovirus disease (other than in liver, spleen, or nodes)</span>
						</div>
						<div id="cytomegalovirus_disease"
							style="float: right; width: 19%; padding-bottom: 5px;">
							<span><input type="text"
								class=" standardInputSmallText mT2 readonly"
								name="cytomegalovirus_disease" title="cytomegalovirus disease "
								value="${eharPersonDataObj['Cytomegalovirus disease (other than in liver, spleen, or nodes)']}" /> </span>
	                    </div>
				 </div>
            <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
              
              <div style="float: left; width: 99%; padding-bottom: 5px;"
							class="mL5 mR5 mT2">
				<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
					<span class="normalFont">Cytomegalovirus retinitis (with loss of vision)</span>
				</div>
				<div id="cytomegalovirus_retinitis"
					style="float: right; width: 19%; padding-bottom: 5px;">
					<span><input type="text"
						class=" standardInputSmallText mT2 readonly"
						name="cytomegalovirus_retini" title="cytomegalovirus retinitis"
						value="${eharPersonDataObj['Cytomegalovirus retinitis (with loss of vision)']}" /> </span>
                   </div>
		      </div>
		  <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
               
               <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">HIV encephalopathy</span>
					</div>
					<div id="hiv_encephalopathy"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="hiv_encephalopathy" title="HIV encephalopathy"
							value="${eharPersonDataObj['HIV encephalopathy']}" /> </span>
                    </div>
			 </div>
		  <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
               
               <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">Herpes simplex: chronic ulcer(s) (>1 mo. duration) or bronchitis, pneumonitis, or esophagitis</span>
					</div>
					<div id="herpess_simplex"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="herpes_simplex" title="Herpes simplex"
							value="${eharPersonDataObj['Herpes simplex: chronic ulcer(s) (>1 mo. duration) or bronchitis, pneumonitis, or esophagitis']}" /> </span>
                    </div>
			 </div>
		  <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
               
               <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">Histoplasmosis, disseminated or extrapulmonary</span>
					</div>
					<div id="histopl_dissemi_extrapulmonary"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="histopl_dissemin_extrapulmonary" title="Histoplasmosis dissemi extrapulmonary"
							value="${eharPersonDataObj['Histoplasmosis, disseminated or extrapulmonary']}" /> </span>
                    </div>
			 </div>	
		 <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
               
               <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">Isosporiasis, chronic intestinal (>1 mo. duration)</span>
					</div>
					<div id="isosporiasis_chronic_intestinal"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="isosporias_chronic_intestinal" title="Isosporiasis chronic intestinal "
							value="${eharPersonDataObj['Isosporiasis, chronic intestinal (>1 mo. duration)']}" /> </span>
                    </div>
			 </div>	
		  <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
               
               <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">Kaposi's sarcoma</span>
					</div>
					<div id="kaposi_sarcoma"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="kaposi_sarcoma" title="kaposi sarcoma"
							value="${eharPersonDataObj['Kaposi\'s sarcoma']}" /> </span>
                    </div>
			 </div>	
         <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
               
               <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">Lymphoid interstitial pneumonia and/or pulmonary lymphoid</span>
					</div>
					<div id="lymphoid_interstitial_pneumonia"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="lymphoid_interstitial_pneumonia" title="lymphoid interstitial pneumonia"
							value="${eharPersonDataObj['Lymphoid interstitial pneumonia and/or pulmonary lymphoid']}" /> </span>
                    </div>
			 </div>	
		<div style="width: 99.9%; border: 1px dotted;" class="container"></div>
               
               <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">Lymphoma, Burkitt's (or equivalent term)</span>
					</div>
					<div id="lymphoma_burkitt"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="lymphoma_burkitt" title="lymphoma_burkitt"
							value="${eharPersonDataObj['Lymphoma, Burkitt\'s (or equivalent term)']}" /> </span>
                    </div>
			 </div>	
		<div style="width: 99.9%; border: 1px dotted;" class="container"></div>
               
               <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">Lymphoma, immunoblastic (or equivalent term)</span>
					</div>
					<div id="lymphoma_immunoblastic"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="lymphoma_immunoblastic" title="lymphoma immunoblastic"
							value="${eharPersonDataObj['Lymphoma, immunoblastic (or equivalent term)']}" /> </span>
                    </div>
			 </div>	
			<div style="width: 99.9%; border: 1px dotted;" class="container"></div>
               
               <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">Lymphoma, primary in brain</span>
					</div>
					<div id="lymphoma_primary_in_brain"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="lymphoma_primary_in_brain" title="lymphoma primary in brain"
							value="${eharPersonDataObj['Lymphoma, primary in brain']}" /> </span>
                    </div>
			 </div>		
			
			 <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
               
               <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">Mycobacterium avium complex or M. kansasii, disseminated or extrapulmonary</span>
					</div>
					<div id="mycobacterium_avium_complex"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="mycobacterium_avium_complex" title="mycobacterium avium complex"
							value="${eharPersonDataObj['Mycobacterium avium complex or M. kansasii, disseminated or extrapulmonary']}" /> </span>
                    </div>
			 </div>
			 <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
               
               <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">M. tuberculosis, pulmonary</span>
					</div>
					<div id="m_tuberculosis_pulmonary"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="m_tuberculosis_pulmonary" title="m tuberculosis pulmonary"
							value="${eharPersonDataObj['M. tuberculosis, pulmonary']}" /> </span>
                    </div>
			 </div>
			 <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
               
               <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">M. tuberculosis, disseminated or extrapulmonary</span>
					</div>
					<div id="m_tuberculosis_disseminate"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="m_tuberculosis_disseminate" title="m tuberculosis disseminate"
							value="${eharPersonDataObj['M. tuberculosis, disseminated or extrapulmonary']}" /> </span>
                    </div>
			 </div>
			 <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
               
               <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">Mycobacterium, of other species or unidentified species, disseminated or extrapulmonary</span>
					</div>
					<div id="mycobacterium_unidenti_extrapulmonary"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="mycobacterium_unidenti_extrapulmonary" title="mycobacterium unidenti extrapulmonary"
							value="${eharPersonDataObj['Mycobacterium, of other species or unidentified species, disseminated or extrapulmonary']}" /> </span>
                    </div>
			 </div>
			 <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
               
               <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">Pneumocystis carinii pneumonia</span>
					</div>
					<div id="pneumocystis_carinii_pneumonia"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="pneumocystis_carinii_pneumonia" title="pneumocystis carinii pneumonia"
							value="${eharPersonDataObj['Pneumocystis carinii pneumonia']}" /> </span>
                    </div>
			 </div>
			 <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
               
               <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">Pneumonia, recurrent, in 12 mo. period</span>
					</div>
					<div id="pneumonia_recurrent"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="pneumonia_recurrent" title="pneumonia recurrent"
							value="${eharPersonDataObj['Pneumonia, recurrent, in 12 mo. period']}" /> </span>
                    </div>
			 </div>
			  <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
               
               <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">Progressive multifocal leukoencephalopathy</span>
					</div>
					<div id="progressive_multi_leukoencepha"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="progressive_multi_leukoencepha" title="progressive multi leukoencepha"
							value="${eharPersonDataObj['Progressive multifocal leukoencephalopathy']}" /> </span>
                    </div>
			 </div>
			 <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
               
               <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">Salmonella septicemia, recurrent</span>
					</div>
					<div id="salmonella_septicemia_recurrent"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="salmonella_septicemia_recurrent" title="salmonella septicemia recurrent"
							value="${eharPersonDataObj['Salmonella septicemia, recurrent']}" /> </span>
                    </div>
			 </div>
			 <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
               
               <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">Toxoplasmosis of brain, onset at >1 mo. of age</span>
					</div>
					<div id="toxoplasmosis_of_brain"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="toxoplasmosis_of_brain" title="toxoplasmosis of brain"
							value="${eharPersonDataObj['Toxoplasmosis of brain, onset at >1 mo. of age']}" /> </span>
                    </div>
			 </div>
			 
			  <div style="width: 99.9%; border: 1px dotted;" class="container"></div>
			 
			 <div style="float: left; width: 99%; padding-bottom: 5px;"
								class="mL5 mR5 mT2">
					<div style="float: left; width: 80%; border-right: 1px dotted; padding-bottom: 5px;">
						<span class="normalFont">Wasting syndrome due to HIV</span>
					</div>
					<div id="wasting_syndrome_due_to_hiv"
						style="float: right; width: 19%; padding-bottom: 5px;">
						<span><input type="text"
							class=" standardInputSmallText mT2 readonly"
							name="wasting_syndrome_due_to_hiv" title="wasting syndrome due to hiv"
							value="${eharPersonDataObj['Wasting syndrome due to HIV']}" /> </span>
                    </div>
			 </div>
	<div style="clear: both"></div>
		</div>
	</div>
</div>
<!--end of pediatrics risk  -->
<!-- ending of slidingdivblock -->
</div>