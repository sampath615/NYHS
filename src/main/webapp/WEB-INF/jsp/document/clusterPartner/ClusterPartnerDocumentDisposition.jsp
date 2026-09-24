<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container borderSSDMDoc">
			<div class="divHeadingCls bGHeading">
				<span class=" mL2"> Dispositions</span>					
			</div>	 
			<div class="pT10 mL20">
				<span class="fieldHolder33">
					<span class="fLW35"> Condition${param.currentPartnerIndexValue}  </span>
					<span class="fLW60"> 
					<form:select path="partnerInvestigationList[${currentPartnerIndex.index}].CONDITION_1" class="standardInputMedText" >
						<form:option value=""></form:option>
						<form:option value="200">200</form:option>
						<form:option value="300">300</form:option>
						<form:option value="400">400</form:option>
						<form:option value="600">600</form:option>
						<form:option value="700">700</form:option>
						<form:option value="850">850</form:option>
						<form:option value="900">900</form:option>
						<form:option value="950">950</form:option>
						<form:option value="971">971</form:option>
						<form:option value="972">972</form:option>
						<form:option value="973">973</form:option>
						<form:option value="990">990</form:option>
					</form:select>
					</span>
				</span>				
				<span class="fieldHolder33">
					<span class="fLW35"> Disposition</span> 
					<span class="fLW60"> 
						<form:select path="partnerInvestigationList[${currentPartnerIndex.index}].DISPOSITION1" class="standardInputMedText" >
							<form:option value=""></form:option>
							<c:forEach items="${dispositionCodeValues}" var="dispositionCode">
								<form:option value="${dispositionCode.DISPOSITION_CODE}" label="${dispositionCode.DISPOSITION_DESC}"></form:option>
							</c:forEach>
						</form:select> 
					</span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35">Disposition date</span> 
					<span class="fLW60"> <form:input path="partnerInvestigationList[${currentPartnerIndex.index}].DISPOSITIONDATE1"  class="standardInputMedText" /> </span>
				</span>	,
			</div>
			<div class="clearBoth pT10 mL20">
				<span class="fieldHolder33">
					<span class="fLW35"> Condition</span>
					<span class="fLW60"> 
					<form:select path="partnerInvestigationList[${currentPartnerIndex.index}].CONDITION_2" class="standardInputMedText" >
						<form:option value=""></form:option>
						<form:option value="200">200</form:option>
						<form:option value="300">300</form:option>
						<form:option value="400">400</form:option>
						<form:option value="600">600</form:option>
						<form:option value="700">700</form:option>
						<form:option value="850">850</form:option>
						<form:option value="900">900</form:option>
						<form:option value="950">950</form:option>
						<form:option value="971">971</form:option>
						<form:option value="972">972</form:option>
						<form:option value="973">973</form:option>
						<form:option value="990">990</form:option>
					</form:select>
					 </span>
				</span>				
				<span class="fieldHolder33">
					<span class="fLW35"> Disposition</span> 
					<span class="fLW60"> 
						<form:select path="partnerInvestigationList[${currentPartnerIndex.index}].DISPOSITION2" class="standardInputMedText" >
							<form:option value=""></form:option>
							<c:forEach items="${dispositionCodeValues}" var="dispositionCode">
								<form:option value="${dispositionCode.DISPOSITION_CODE}" label="${dispositionCode.DISPOSITION_DESC}"></form:option>
							</c:forEach>
						</form:select>
					</span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35">Disposition date</span> 
					<span class="fLW60"> <form:input path="partnerInvestigationList[${currentPartnerIndex.index}].DISPOSITIONDATE2"  class="standardInputMedText" /> </span>
				</span>	
			</div>
			<div class="clearBoth pT10 mL20">
				<span class="fieldHolder33">
					<span class="fLW35"> Condition</span>
					<span class="fLW60"> 
						<form:select path="partnerInvestigationList[${currentPartnerIndex.index}].CONDITION_3" class="standardInputMedText" >
						<form:option value=""></form:option>
						<form:option value="200">200</form:option>
						<form:option value="300">300</form:option>
						<form:option value="400">400</form:option>
						<form:option value="600">600</form:option>
						<form:option value="700">700</form:option>
						<form:option value="850">850</form:option>
						<form:option value="900">900</form:option>
						<form:option value="950">950</form:option>
						<form:option value="971">971</form:option>
						<form:option value="972">972</form:option>
						<form:option value="973">973</form:option>
						<form:option value="990">990</form:option>
					</form:select> 
					</span>
				</span>				
				<span class="fieldHolder33">
					<span class="fLW35"> Disposition</span> 
					<span class="fLW60"> 
						<form:select path="partnerInvestigationList[${currentPartnerIndex.index}].DISPOSITION3" class="standardInputMedText" >
							<form:option value=""></form:option>
							<c:forEach items="${dispositionCodeValues}" var="dispositionCode">
								<form:option value="${dispositionCode.DISPOSITION_CODE}" label="${dispositionCode.DISPOSITION_DESC}"></form:option>
							</c:forEach>
						</form:select> 
					</span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35">Disposition date</span> 
					<span class="fLW60"> <form:input path="partnerInvestigationList[${currentPartnerIndex.index}].DISPOSITIONDATE3"  class="standardInputMedText" /> </span>
				</span>	
			</div>
			<div class="clearBoth pT10 mL20">
				<span class="fieldHolder33">
					<span class="fLW35"> Condition</span>
					<span class="fLW60"> 
						<form:select path="partnerInvestigationList[${currentPartnerIndex.index}].CONDITION_4" class="standardInputMedText" >
						<form:option value=""></form:option>
						<form:option value="200">200</form:option>
						<form:option value="300">300</form:option>
						<form:option value="400">400</form:option>
						<form:option value="600">600</form:option>
						<form:option value="700">700</form:option>
						<form:option value="850">850</form:option>
						<form:option value="900">900</form:option>
						<form:option value="950">950</form:option>
						<form:option value="971">971</form:option>
						<form:option value="972">972</form:option>
						<form:option value="973">973</form:option>
						<form:option value="990">990</form:option>
					</form:select> 
					</span>
				</span>				
				<span class="fieldHolder33">
					<span class="fLW35"> Disposition</span> 
					<span class="fLW60"> 
						<form:select path="partnerInvestigationList[${currentPartnerIndex.index}].DISPOSITION4" class="standardInputMedText" >
							<form:option value=""></form:option>
							<c:forEach items="${dispositionCodeValues}" var="dispositionCode">
								<form:option value="${dispositionCode.DISPOSITION_CODE}" label="${dispositionCode.DISPOSITION_DESC}"></form:option>
							</c:forEach>
						</form:select> 
					</span>
				</span>
				<span class="fieldHolder33">
					<span class="fLW35">Disposition date</span> 
					<span class="fLW60"> <form:input path="partnerInvestigationList[${currentPartnerIndex.index}].DISPOSITIONDATE4"  class="standardInputMedText" /> </span>
				</span>	
			</div>
			
		</div>