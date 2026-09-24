<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="floatLeft width100perc marginBottom10 dropdownSection">
								<div> 
									<div style="float:left; width:30%;" class="abtNTDrpDwn mL20">
										
										<span class="">Lab Name  </span>
										<span>
											<form:input path="rawAcrfLabs[${statusIndex}].labName" id="rawAcrfLabs${statusIndex}_labName" class="abtNTTest acrfLabs_Name standardInputLongText mL5 widgetFieldsReadonly"   title="Lab Name" tabindex="-1"></form:input>
											<form:hidden path="rawAcrfLabs[${statusIndex}].clia" id="rawAcrfLabs${statusIndex}_clia" class="acrfLabs_clia abtNTTest standardInputSmallText "   ></form:hidden>
										</span>		
										<div class="v_error rawAcrfLabs${statusIndex}_labName"></div>
									</div>
	
	
										
									<div style="float:left; width:30%;">
										<span class="mL10">Facility Name  </span>
										<span>
											<form:input path="rawAcrfLabs[${statusIndex}].facilityName" id="rawAcrfLabs${statusIndex}_facilityName" class=" abtNTTest acrfLabs_FacilityName standardInputLongText mL5 widgetFieldsReadonly"  title="Facility Name"  tabindex="-1"></form:input>
											<form:hidden path="rawAcrfLabs[${statusIndex}].facilityEharsUid" id="rawAcrfLabs${statusIndex}_facilityEharsUid" class="acrfLabs_FacilityEharsUid abtNTTest " ></form:hidden>
										</span>
										<div class="v_error rawAcrfLabs${statusIndex}_facilityName"></div>
									</div>
									
									
									<div style="float:left; width:8%; "> 
										<span class="mL5"> <button class="editLabData" onclick="openLabWidget(this);return false;" value="Edit">Edit</button> </span>
									</div>
								</div>
								<div class="clearBoth pT10">
								
									
									
									<span class="hdrFont mL20"> Provider </span>
								
									<span class="mL10">First Name  </span>
									<span>
										<form:input path="rawAcrfLabs[${statusIndex}].providerFname" id="rawAcrfLabs${statusIndex}_providerFname" class="abtNTTest acrfLabs_ProvFname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider First Name"  tabindex="-1"></form:input>
									</span>
<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerFname"></div> --%>
								
									<span class="mL10">Middle Name  </span>
									<span>
										<form:input path="rawAcrfLabs[${statusIndex}].providerMname" id="rawAcrfLabs${statusIndex}_providerMname" class="abtNTTest acrfLabs_ProvMname standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Middle Name"  tabindex="-1"></form:input>
									</span>
<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerMname"></div> --%>
								
									<span class="mL10">Last Name  </span>
									<span>
										<form:input path="rawAcrfLabs[${statusIndex}].providerLname" id="rawAcrfLabs${statusIndex}_providerLname" class="abtNTTest acrfLabs_ProvLname standardInputLongText mL5  widgetFieldsReadonly"  title="Provider Last Name"  tabindex="-1"></form:input>
									</span>
<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerLname"></div> --%>
								
									<span class="mL10">Suffix </span>
									<span>
										<form:input path="rawAcrfLabs[${statusIndex}].providerSuffix" id="rawAcrfLabs${statusIndex}_providerSuffix" class="abtNTTest acrfLabs_ProvSuffix standardInputSmallText mL5  widgetFieldsReadonly"  title="Provider Suffix"  tabindex="-1"></form:input>
									</span>
<%-- 									<div class="v_error rawAcrfLabs${statusQN.index}_providerSuffix"></div> --%>

									<div class="mL20 v_error rawAcrfLabs${statusIndex}_providerFname"></div>
									<div class="mL20 v_error rawAcrfLabs${statusIndex}_providerLname"></div>
								
							
								</div>
									
							</div>