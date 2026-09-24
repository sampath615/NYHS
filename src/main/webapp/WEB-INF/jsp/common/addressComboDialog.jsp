<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="addModalDlg">
	<c:if test="${hasProvRefData == 'Y'}">
		<div  id="masterProvWidget" class="mT5 marginBottom10 mL5">
				<span class=""> Provider Name: </span><span> 
				<input name="masterProviderNameDlg" id = "masterProviderNameDlg" class="editable standardInputText " /> 
				<a href="#" class="dropdownImage" onClick="return false"></a></span>
				<button type="button" id="clearProvBtn" onclick="clearProvDataInDlg();" title="Clear" class="mL10 ">Clear</button>											
								
				 
				<br/>
		</div>
	</c:if>


	<form:form modelAttribute="addressFieldVO">
		
		<div style="padding-top:3px; padding-bottom:10px;border: thin solid lightblue;" id="providerInformation" class="displayNone">
			<div class="standardInputDiv">
				<div style="float: left; width: 37%;">
					<font class="standardInputTitle">First Name</font>
				</div>
				<div style="float:left">
					<input type="text" size="35" maxlength="80" id="addMdlDlgProvFname" class="standardInputMedText addFname" title="First Name"/>
				</div>
			</div>
			<div class="standardInputDiv">
				<div style="float: left; width: 37%;">
					<font class="standardInputTitle">Last Name</font>
				</div>
				<div style="float:left">
					<input type="text" size="35" maxlength="80" id="addMdlDlgProvLname" class="standardInputMedText addLname" title="Last Name"/>
				</div>
			</div>
			<div class="standardInputDiv">
				<div style="float: left; width: 37%;">
					<font class="standardInputTitle">Middle Name</font>
				</div>
				<div style="float:left">
					<input type="text" size="35" maxlength="80" id="addMdlDlgProvMname" class="standardInputMedText addMname" title="Middle Name"/>
				</div>
			</div>
			<div class="standardInputDiv">
				<div style="float: left; width: 37%;">
					<font class="standardInputTitle">Suffix</font>
				</div>
				<div style="float:left">
					<input type="text" size="35" maxlength="80" id="addMdlDlgProvSuffix" class="standardInputMedText addSuffix" title="Suffix"/>
				</div>
			</div>
			<div class="standardInputDiv">
				<div style="float: left; width: 37%;">
					<font class="standardInputTitle">Phone</font>
				</div>
				<div style="float:left">
					<input type="text" size="35" maxlength="20" id="addMdlDlgProvPhone" class="standardInputMedText addPhone" title="Phone"/>
				</div>
			</div>
			<div id="provNPIDlgElement" class="standardInputDiv" >
				<div style="float: left; width: 37%;">
					<font class="standardInputTitle">NPI</font>
				</div>
				<div style="float:left">
					<input type="text" size="35" maxlength="80" id="addMdlDlgProvNpi" class="standardInputMedText addNpi" title="NPI"/>
				</div>
			</div>
		</div>
	
	
		<div style="padding-top:3px; padding-bottom:10px;">
			<div class="standardInputDiv">
				<div style="float: left; width: 37%;">
					<font class="standardInputTitle"><spring:message code="label.address" /></font>
				</div>
				<div style="float:left">
					<input type="text" size="35" maxlength="80" id="addMdlDlgStreet" class="standardInputText addAddress" title="Street address"/>
				</div>
			</div>
		</div>
		<div style="width: 100%; border-spacing: 3px; padding: 3px; border: thin solid lightblue; float: left;"  id="provAddressTable" class="container" >
			
			<div style="float:left; width: 80%">
				<div class="standardInputDiv">
					<div style="float: left; width: 45%;">
						<font class="standardInputTitle"><spring:message code="label.city" /></font>
					</div>
					<div style="float:left">
						<select id="addMdlDlgCity"  class="roundedBrdr addCity" Style="WIDTH:180px" onchange="citySelected('addMdlDlg')" title="City"></select>
					</div>
				</div>
				<div class="standardInputDiv">
					<div style="float: left; width: 45%;">
						<font class="standardInputTitle"><spring:message code="label.state" /></font>
					</div>
					<div style="float:left">
						<form:select path="state" id="addMdlDlgState" cssClass="roundedBrdr addState" cssStyle="WIDTH:50px" onchange="stateSelected('addMdlDlg');" title="State">
							<form:option value=""></form:option>
							<form:options items="${sessionScope.stateMap}"/>
						</form:select>
					</div>
				</div>
				<div class="standardInputDiv">
					<div style="float: left; width: 45%;">
						<font class="standardInputTitle"><spring:message code="label.zip" /></font>
					</div>
					<div style="float:left">
						<select style="WIDTH: 100px" id="addMdlDlgZip" class="roundedBrdr addZip" onchange="zipSelected('addMdlDlg')" title="Zip code"> </select>
					</div>
				</div>
				<div class="standardInputDiv" style="margin-bottom: 10px;">
					<div style="float: left; width: 45%;">
						<font class="standardInputTitle"><spring:message code="label.county" /> (for NY)</font>
					</div>
					<div style="float:left">
						<form:select path="county" id="addMdlDlgCounty" cssClass="roundedBrdr addCounty" cssStyle="WIDTH:180px" onchange="countySelected('addMdlDlg');" title="County">
							<form:option value=""></form:option>
							<form:options items="${sessionScope.countyMap}"/>
						</form:select>
					</div>
				</div>
			</div>
			
			<div style="float:right; width: 20%; padding-top: 10%;">
				<span><button class="clearAddress" id="clearAddDlgButton" onclick="clearAddress('addMdlDlg');return false;">Clear</button>
				</span>				
			</div>
		</div>
		<div style="clear: both;"></div>
		<div style="margin-top: 10px;">
			<ul id="menu-css">
				<li><a class="displayNone" id="psMessageAddressSubmitButton" href="javascript:setAddressFieldsForPsMessage();">Submit</a></li>
				<li><a id="addressSubmitButton" href="javascript:setAddressFields();">Submit</a></li>
				<li><a href="javascript:clearAddMdlDlgModal();">Reset</a></li>
			</ul>
		</div>
	</form:form>
</div>