<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<form:form modelAttribute="stdRawHL7DocVO" id="stdRawHL7DocVO">

	<div class="b1"	style="margin: 05px 05px 10px 5px; border: 1px dotted; padding: 9px 0px 33px 8px;">		
		
		<div class="personInfo">
        		<div class="fieldHolder30">
                     <span class=""><spring:message code="label.firstname" /> </span> 
                      <span class="pL5"> <form:input readonly="true" path="personStdData.fName" class="standardInputMedText ml10" />
                          	 <c:if test="${stdRawHL7DocVO.personStdData.fname_count gt 1}"><img src="${staticPath}/images/icons/add.png" title="Click for more data" onclick="getHL7DistinctData('FNAME', 'First Name')" /></c:if>
                     </span>
                 </div>
                 <div class="fieldHolder30">
                    	<span class=""><spring:message code="label.middleName" /> </span> 
                    	<span  class="pL5"><form:input path="personStdData.mName" class="standardInputMedText" readonly="true" />
                    		<c:if test="${stdRawHL7DocVO.personStdData.mname_count gt 1}"><span class=""><img src="${staticPath}/images/icons/add.png" title="Click for more data" onclick="getHL7DistinctData('MNAME', 'Middle Name')" /></span></c:if>
                       	</span>
                 </div>
                 <div class="fieldHolder30">
                    	<span class=""><spring:message code="label.lastname" /> </span> 
                    	<span  class="pL5"><form:input path="personStdData.lName" class="standardInputMedText ml10" readonly="true" /></span>
                    	<c:if test="${stdRawHL7DocVO.personStdData.lname_count gt 1}">
							<span class=""><img src="${staticPath}/images/icons/add.png" title="Click for more data" onclick="getHL7DistinctData('LNAME', 'Last Name')" /></span>
						</c:if>
                 </div>
                 
                 <br><br>
                 
                 <div class="fieldHolder30">
                    <span class="">Date of Birth</span> <span class="pL5"><form:input path="personStdData.dobText" class="standardInputMedText" size="10" readonly="true" />	</span> 
                    <c:if test="${stdRawHL7DocVO.personStdData.dob_count gt 1}"> <span class=""><img src="${staticPath}/images/icons/add.png" title="Click for more data" onclick="getHL7DistinctData('DATE_OF_BIRTH','DATE_OF_BIRTH')" /></span> </c:if>
                 </div>
                 
                 <div class="fieldHolder30">
                    	 <span class="">MPI </span><span class="pL5"><form:input path="personStdData.mpi" class="standardInputMedText ml55" maxlength="25" readonly="true" /></span> 
				 		<c:if	test="${stdRawHL7DocVO.personStdData.mpi_count gt 1}">	<span class=""><img	src="${staticPath}/images/icons/add.png" title="Click for more data" onclick="getHL7DistinctData('MPI','MPI')" /></span> </c:if>	
                    	
                  </div>
                  
                  <div class="fieldHolder30">
                    	 <span class="">SSN </span> 
                    	 <span class="pL5"> 
                    	 <form:input path="personStdData.ssn" class="standardInputMedText ml45" maxlength="10" readonly="true" />	</span>
				 <c:if test="${stdRawHL7DocVO.personStdData.ssn_count gt 1}"> <span class=""><img src="${staticPath}/images/icons/add.png"	title="Click for more data" onclick="getHL7DistinctData('SSN','SSN')" /></span> </c:if>
                 </div>
                
                <br><br>
                                
                 <div class="fieldHolder49">
                    	<span class="">Medical Record Number </span> 
                    	<span  class="pL5">
                    		<form:input path="personStdData.medRecordNos" class="standardInputMedText ml10" maxlength="10" readonly="true" />
                    		<c:if test="${stdRawHL7DocVO.personStdData.mrn_count gt 1}">
								<span class=""><img src="${staticPath}/images/icons/add.png" title="Click for more data" onclick="getHL7DistinctData('MED_RECORD_NUMBER', 'Medical Record Number')" /></span>
							</c:if>
                    	</span>
                 </div>
        </div>
        </div>

	<div class="container" style="border: 1px dotted; padding: 0px 0px 0px 5px; margin: 0px 0px 0px 5px; height: auto;">
		<div style="float: left; width: 25%; border-right: dotted 1px; height: auto; margin :5px;">
			<div>RACE</div>
			<div>
				<form:checkbox path="personRaceData.raceWhite" value="1"
					onclick="return false;" />
				White
				<form:checkbox path="personRaceData.raceBlack" value="1"
					onclick="return false;" />
				African American
			</div>
			<div>
				<form:checkbox path="personRaceData.raceAsian" value="1"
					onclick="return false;" />
				Asian
				<form:checkbox path="personRaceData.racePacific" value="1"
					onclick="return false;" />
				Asian/Pacific Islander
			</div>
			<div>
				<form:checkbox path="personRaceData.raceIndian" value="1"
					onclick="return false;" />
				American Indian/Alaskan Native
			</div>
			<div>
				<form:checkbox path="personRaceData.raceOther" value="1"
					onclick="return false;" />
				Other
				<form:input size="200px" path="personRaceData.raceOtherText"
					class="standardInputMedText" readonly="true" />
			</div>
			<div>
				<form:checkbox path="personRaceData.raceUnknown" value="1"
					onclick="return false;" />
				Unknown
			</div>
		</div>
		<div class="container"
			style="float: left; width: 25%; border-right: dotted 1px; height: 110px; margin :5px;">
			<div style="padding: 5px 0 5px 20px;">
				<div class="floatLeft pR10" style="border-right: dotted 0px;">
					<div class="">Current Gender</div>
					<div class="">
						<div>
							<form:radiobutton path="personStdData.gender" value="M"
								onclick="return false;" />
							Male
						</div>
						<div>
							<form:radiobutton path="personStdData.gender" value="F"
								onclick="return false;" />
							Female
						</div>
						<div>
							<form:radiobutton path="personStdData.gender" value="U"
								onclick="return false;" />
							Unknown
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="container" style="float: left; width: 25%; border-right: dotted 1px; height: 110px; margin :5px;">
						<!--<div style="padding:5px 0 5px 20px;">
							Ethnicity (Hispanic or Latino) <br/>
						 		<form:radiobutton path="personStdData.ethinicity" value="01" /> <spring:message code="label.yes" /> <br>
						  		<form:radiobutton path="personStdData.ethinicity" value="02" /> <spring:message code="label.no" /><br>
						  		<form:radiobutton path="personStdData.ethinicity" value="09" /> <spring:message code="label.unknown" />
						</div>-->
						
						<div style="padding:5px 0 5px 20px;">
                    	<span class="">Ethnicity (Hispanic or Latino)</span><span class="pL5"> <form:input path="personStdData.ethinicity" class="standardInputMedText" maxlength="10" readonly="true" />	</span> 
				 				<c:if test="${stdRawHL7DocVO.personStdData.ethnicity_count gt 1}">	<span class=""><img	src="${staticPath}/images/icons/add.png" title="Click for more data"onclick="getHL7DistinctData('ETHNICITY', 'Ethnicity')" /></span></c:if>
                 </div>
						</div>
	</div>


</form:form>
