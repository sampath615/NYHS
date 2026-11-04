<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<form:form id="providerSearchForm" name="providerSearchForm" modelAttribute="addressFieldVO">
  <div class="stdDetailsDiv"> 
	<div>&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->
	<div class="leftDiv">
		<div class="standardInputDiv" subProvCrit="provSrch" >
			<div style="float:left">
				<label for="subProvFName"><spring:message code="label.firstname" /></label>
			</div>
			<div style="float:right">
				<input id="subProvFName" name="providerFname"  tabindex="1" class="standardInputMedText mR50 fieldLevelHelp" value="" maxlength="100" type="text" title="<spring:message code='title.firstName' />">
			</div>
		</div>
		<div class="standardInputDiv" subProvCrit="provSrch">
			<div style="float:left">
				<label for="subProvLName"><spring:message code="label.lastname" /></label>
			</div>
			<div style="float:right">
				<input id="subProvLName" name="providerLname" tabindex="3"  class="standardInputMedText mR50 fieldLevelHelp" value="" type="text" maxlength="100" title="<spring:message code='title.lastName' />">
			</div>
		</div>
		<div class="standardInputDiv">
			<div style="float:left">
				<label for="subProvStAddrs"><spring:message code="label.streetAddress" /></label>
			</div>
			<div style="float:right">
				<input id="subProvStAddrs" name="address"  tabindex="5" class="standardInputMedText mR50 fieldLevelHelp" value="" type="text" maxlength="100" title="<spring:message code='title.streetAddress' />">
			</div>
		</div>
		<div class="standardInputDiv">
			<div style="float:left">
				<label for="subProvCity"><spring:message code="label.city"  /></label>
			</div>
			<div style="float:right">
				<input id="subProvCity" name="city"  tabindex="6" maxlength="30"  class="standardInputMedText mR50 fieldLevelHelp" type="text" title="<spring:message code='title.city' />">
			</div>
		</div>
		<div class="standardInputDiv">
			<div style="float:left">
				<label for="state"><spring:message code="label.state" /></label>
			</div>
			<div style="float:right">
				<spring:message code="title.state" var="state_title"/>
			    <form:select id="subProvState"  tabindex="7" path ="state" class="standardInputMedText mR50"  title="${state_title}">
				        <form:option value="" label="--- Select ---" />
						<form:options items="${sessionScope.stateMap}"/>
			    </form:select>
			</div>
		</div>
	</div>
	<div class="rightDiv">
	    <div class="standardInputDiv" subProvCrit="provSrch">
			<div style="float:left">
				<label for="subProvMName"><spring:message code="label.middleName" /></label>
			</div>
			<div style="float:right">
				<input id="subProvMName" name="providerMname" tabindex="2"  class="standardInputMedText mR50 fieldLevelHelp" value="" maxlength="100" type="text" title="<spring:message code='title.middleName' />">
			</div>
		</div>
		<div class="standardInputDiv">
			<div style="float:left">
				<label for="subProvPhone"><spring:message code="label.phoneNumber" /></label>
			</div>
			<div style="float:right">
				<input id="subProvPhone" name="phone"  tabindex="4" class="standardInputMedText mR50 mask phoneMask fieldLevelHelp" type="text" value="" title="<spring:message code='title.phoneNumber' />">
			</div>
		</div>
		<div class="standardInputDiv">
			<div style="float:left">
				<label for="countyFipsCd"><spring:message code="label.county" /></label>
			</div>
			<div style="float:right">
				<spring:message code="title.county" var="county_title"/>
				<form:select id="subProvCounty"  tabindex="8" path="countyFipsCd" class="standardInputMedText mR50" title="${county_title}">
				        <form:option value="" label="--- Select ---" />
						<form:options items="${sessionScope.countyMap}"/>
			    </form:select>
				<!-- <input id="subProvCounty" name="subProvCounty" class="standardInputMedText mR50" type="text"> -->
			</div>
		</div>
		<div class="standardInputDiv">
			<div style="float:left">
				<label for="subProvZip"><spring:message code="label.zip"  /></label>
			</div>
			<div style="float:right">
				<input id="subProvZip" name="zipcode"  tabindex="9" class="standardInputMedText mR50 fieldLevelHelp" type="text" title="<spring:message code='title.zip' />">
			</div>
		</div>
		
		<div class="standardInputDiv" subProvCrit="provSrch">
			<div style="float:left">
				<label for="subProvNPI">NPI</label>
			</div>
			<div style="float:right">
				<input id="subProvNPI" name="npi"  tabindex="10" class="standardInputMedText mR50 fieldLevelHelp" type="text" title="npi">
			</div>
		</div>
		
	</div>
	
	<div style="clear: both;"><br/></div> <!-- dummy div -->
</div>
</form:form>			