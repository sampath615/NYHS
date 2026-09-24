<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- This jsp belongs to "RIDR" Fifth Tab --%> 
<!-- page5 first block starts -->



<div class="roundedBorderFont mL10" style="width:98%; background:#DDDDDD" >

		<div style="width:99.9%; border:1px dotted;" class="container"></div>
		
		<div style="width:100%; padding:2px; background-color:#DBEAF9" class="container">
				<div style="float:left;">
					<span class="hdr1Font mL5"> Duplicate Review Information</span>
				</div>
				<div style="float:right; margin-right:20px;">
					<span><img  class="cloningIcons" src="${staticPath}/images/plus.png" onclick="javascript:addAcrfRidr()" /> </span>
				</div>
		</div>
		
		<div style="width:99%; margin-left:5px;  margin-right:5px; padding-bottom:5px" id="completeAcrfRidrInformation">
			<c:forEach items="${acrfFormVO.acrfRidrList}" var="acrfRidrList" varStatus="arvRidrIndex">
			<div style=" width:100%; margin-right:5px; padding-bottom:5px ; border-bottom:1px solid black" class="acrfRidrInformation" index="${arvRidrIndex.index}"> 
					
						<div style="width:100%;  padding-top:3px; " class="ridrAttributes" >
							
							<div class="floatLeft" style="width:21%;">
								<div>
									<span>  Duplicate Status:
									
										<form:select  path="acrfRidrList[${arvRidrIndex.index}].DUPLICATE_STATUS" id="acrfRidrList${arvRidrIndex.index}_DUPLICATE_STATUS" class="standardInputSmallText3  DUPLICATE_STATUS_ELEMENT"   title="Duplicate Status">
											<form:option value="" label="Select"></form:option>
											<form:option value="1" label="Same As"></form:option>
											<form:option value="2" label="Different Than"></form:option>
										</form:select> 
										
										 
									</span>
								</div>
								<div class="v_error acrfRidrList${arvRidrIndex.index}_DUPLICATE_STATUS"></div>
							</div>
							
							<div class="floatLeft" style="width:25%; border-left:1px dotted;">
								<div>
								<span class="mL5 valignTop">State Code:</span> 
									<form:select  path="acrfRidrList[${arvRidrIndex.index}].STATE_CD" id="acrfRidrList${arvRidrIndex.index}_STATE_CD" class="standardInputMedText STATE_CD_ELEMENT"   title="State Code">
										<form:option value="" label="Select"></form:option>
										
										<c:forEach items="${stateCodes}" var="stateCode">
											<form:option value="${stateCode.lookup_cd}" label="${stateCode.lookup_name}" name="${stateCode.lookup_name}"></form:option>
										</c:forEach>
										
									</form:select> 
									
								</div>
								<div class="v_error mL5 acrfRidrList${arvRidrIndex.index}_STATE_CD"></div>
							</div>
							<div class="floatLeft" style="width:22%; border-left:1px dotted;">
								<div>
									<span class="mL5">STATENO: <form:input path="acrfRidrList[${arvRidrIndex.index}].STATENO" id="acrfRidrList${arvRidrIndex.index}_STATENO" maxlength="100" class="standardInputSmallText   STATENO_ELEMENT "  title="stateno"/> </span>
								</div>
								<div class="v_error mL5 acrfRidrList${arvRidrIndex.index}_STATENO"></div>
							</div>
							<div class="floatLeft" style="width:30%; border-left:1px dotted;">
								<span class="mL5" style="vertical-align: top;">Comments: </span><span> <form:textarea path="acrfRidrList[${arvRidrIndex.index}].COMMENTS" id="acrfRidrList${arvRidrIndex.index}_COMMENTS" class="standardInputSmallText COMMENTS_ELEMENT" title="Comments" style="width:250px"  /> </span>
							</div>
							
							<div style="float:left width:2%;" class="acrfRidrList${arvRidrIndex.index}_closeIcon">
								<span class="mL2" style="float:right; margin-top:4px;"><img class="cloningIcons" onclick="deleteRidrDiv(this, 'acrfRidrInformation');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span>
							</div>
							<form:hidden path="acrfRidrList[${arvRidrIndex.index}].RIDR_ROWID"  id="acrfRidrList${arvRidrIndex.index}_RIDR_ROWID" /> 
						</div><br/>
						
						<div class="container" style="width:99.9%; border:1px dotted;"></div>
					
			</div>
			</c:forEach>
		</div>

</div>