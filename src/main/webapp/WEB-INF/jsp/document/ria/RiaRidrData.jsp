<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div style="border: 1px dotted; width: 100%; padding-bottom: 5px;" class="container">
				<div id="psDetailsHeadingDiv" class="divHeadingCls"
					style="background: #DDDDDD;">
					<span class=" mL2">Duplicate Review Information</span>
				</div>
		</div>
		
		<div style="width:99%; margin-left:5px;  margin-right:5px; padding-bottom:5px" id="completeAcrfRidrInformation">
			<form:hidden path="RIDR_INFO"/>
			<c:forEach items="${stdRiaDocVO.acrfRidrList}" var="acrfRidrList" varStatus="arvRidrIndex">
			<div style=" width:100%; margin-right:5px; padding-bottom:5px ; border-bottom:1px solid black" class="acrfRidrInformation" index="${arvRidrIndex.index}"> 
					
						<div style="width:100%;  padding-top:3px; " class="ridrAttributes" >
							
							<div class="floatLeft" style="width:30%;">
								<div>
									<span class="bold">  Duplicate Status
									
										<form:select  path="acrfRidrList[${arvRidrIndex.index}].DUPLICATE_STATUS" id="acrfRidrList${arvRidrIndex.index}_DUPLICATE_STATUS" class="standardInputSmallText3 ridrFields  DUPLICATE_STATUS_ELEMENT readonly"   title="Duplicate Status">
											<form:option value="" label="Select"></form:option>
											<form:option value="1" label="Same As"></form:option>
											<form:option value="2" label="Different Than"></form:option>
										</form:select> 
										
										 
									</span>
								</div>
							</div>
							
							<div class="floatLeft" style="width:30%; border-left:1px dotted;">
								<div>
								<span class="mL5 valignTop bold">State Code</span> 
									<form:select  path="acrfRidrList[${arvRidrIndex.index}].STATE_CD" id="acrfRidrList${arvRidrIndex.index}_STATE_CD" class="standardInputMedText readonly STATE_CD_ELEMENT ridrFields"   title="State Code">
										<form:option value="" label="Select"></form:option>
										<c:forEach items="${stateCodes}" var="stateCode">
											<form:option value="${stateCode.lookup_cd}" label="${stateCode.lookup_name}" name="${stateCode.lookup_name}"></form:option>
										</c:forEach>
										
									</form:select> 
									
								</div>
							</div>
							<div class="floatLeft" style="width:30%; border-left:1px dotted;">
								<div>
									<span class="mL5 bold">STATENO <form:input path="acrfRidrList[${arvRidrIndex.index}].STATENO" id="acrfRidrList${arvRidrIndex.index}_STATENO" maxlength="100" class="standardInputSmallText readonly ridrFields  STATENO_ELEMENT "  title="stateno"/> </span>
								</div>
							</div>
							<form:hidden path="acrfRidrList[${arvRidrIndex.index}].RIDR_ROWID"  id="acrfRidrList${arvRidrIndex.index}_RIDR_ROWID" /> 
						</div><br/>
						
						<div class="container" style="width:99.9%; border:1px dotted;"></div>
					
			</div>
			</c:forEach>
		</div>