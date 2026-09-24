<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="floatLeft width100perc">
									<div style="float:left; ">
										<span class="hdr1Font"> Testing Option (if applicable) : </span>
									
										<span class="mL20"><form:radiobutton path="rawAcrfLabs[${statusIndex}].rapidTestType" id="rawAcrfLabs${statusIndex}_rapidTestType" class="abtNTPointOfCare" value="P"/></span>
										<span class="mL2">Point-of-care test by provider </span>
										
										<span class="mL10"><form:radiobutton path="rawAcrfLabs[${statusIndex}].rapidTestType" id="rawAcrfLabs${statusIndex}_rapidTestType" class="abtNTTestOpt" value="S"/></span>
										<span class="mL2">Self-test, result directly observed by a provider<sup>2</sup></sup> </span>
										
										<span class="mL10"><form:radiobutton path="rawAcrfLabs[${statusIndex}].rapidTestType" id="rawAcrfLabs${statusIndex}_rapidTestType" class="abtNTLabTest" value="L"/></span>
										<span class="mL2">Lab test, self-collected sample </span>			
									</div>
									
									
								
								</div>