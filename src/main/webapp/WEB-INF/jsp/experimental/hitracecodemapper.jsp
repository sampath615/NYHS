<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<link href="${staticPath}/css/mainmenu/search.css?v=${buildDate}"   rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HIT RACE CODE MAPPER</title>
</head>
<body>
	<div class="floatRight">
		<span class="mR20 bold"><a
			href="${contextPath}/web/getAssignmentsTally">Application Home</a></span> <span
			class="mR20 bold"><a href="${contextPath}/web/manualQuartzJob">Manual
				Job</a></span>
	</div>
	<div class="clearBoth"></div>
	<c:if test="${TRANSACTION_MESSAGE != null}">

		<div id="hitRaceCodeMessage" class="transactionMessage successTransactionMessage"><span class="message"><c:out value="${TRANSACTION_MESSAGE}" /></span></div>
		<div id="hitracecodemap" style="background-color: #ebebeb; text-align: left; padding-top: 25px;padding-left : 30px; height: 225px; width: 500px; border: solid 1px #999; margin: 0 auto; margin-top: 25px;">
			<div style="margin-bottom: 10px">
				<label style="font-family: Arial, Helvetica, sans-serif; font-size: 10pt; font-weight: bold">Race Name : </label> 
				<select onchange="getHitRaceValue(this)" class="required" name="raceValueName" id="raceValueName" style="margin-left: 10px; background: none repeat scroll 0 0 #ffffff; border-color: #cccccc #999999 #999999 #cccccc; border-radius: 8px; border-style: solid; border-width: 1px; font-family: Arial, Helvetica, sans-serif; font-size: 10pt; margin-right: auto; padding: 1px; width: 350px;" title="Parameter name">
					<option value="select">Select</option>
					<c:forEach items="${hitracecodemap}" var="metaData">
						<option value="${metaData.key}">${metaData.key}</option>
					</c:forEach>
				</select>
			</div>
			<div style="margin-bottom: 10px">
				<label 	style="font-family: Arial, Helvetica, sans-serif; font-size: 10pt; font-weight: bold">	Race Value : </label> 
				<!-- <input value="" type="text" name="raceValue" id="raceValue"	class="required" style="margin-left: 10px; background: none repeat scroll 0 0 #ffffff; border-color: #cccccc #999999 #999999 #cccccc; border-radius: 8px; border-style: solid; border-width: 1px; font-family: Arial, Helvetica, sans-serif; font-size: 10pt; margin-right: auto; padding: 1px; width: 190px;" title="Parameter value" /> -->
				<select name="raceValue" id="raceValue" multiple = "true"
						style="margin-left: 10px; background: none repeat scroll 0 0 #ffffff; border-color: #cccccc #999999 #999999 #cccccc; border-radius: 8px; border-style: dotted; border-width: 1px; font-family: Arial, Helvetica, sans-serif; font-size: 10pt; padding: 1px; width: 350px;">
					<option value="select" disabled>--Choose single/multiple Race Value--</option>
					<option value="ASIAN">ASIAN</option>
					<option value="BLACK">BLACK</option>
					<option value="INDIAN">INDIAN</option>
					<option value="OTHER">OTHER</option>
					<option value="PACIFIC">PACIFIC</option>
					<option value="UNKNOWN">UNKNOWN</option>
					<option value="UNTRANSLATABLE">UNTRANSLATABLE</option>
					<option value="WHITE">WHITE</option>
				</select>
			</div>

			 <div style="margin-bottom: 10px">
				<label	style="font-family: Arial, Helvetica, sans-serif; font-size: 10pt; font-weight: bold">  Ethnicity : </label> &nbsp;&nbsp;&nbsp;
				<!-- <input value="" type="text" name="ethicity" id="ethicity" class="required" style="margin-left: 30px; background: none repeat scroll 0 0 #ffffff; border-color: #cccccc #999999 #999999 #cccccc; border-radius: 8px; border-style: solid; border-width: 1px; font-family: Arial, Helvetica, sans-serif; font-size: 10pt; margin-right: auto; padding: 1px; width: 190px;" title="Parameter value" /> -->
				<select name="ethicity" id="ethicity" style="margin-left: 10px; background: none repeat scroll 0 0 #ffffff; border-color: #cccccc #999999 #999999 #cccccc; border-radius: 8px; border-style: solid; border-width: 1px; font-family: Arial, Helvetica, sans-serif; font-size: 10pt; margin-right: auto; padding: 1px; width: 350px;">
					<option value="select">--Choose a Ethnicity Value--</option>
					<option value="01">01</option>
					<option value="02">02</option>
					<option value="09">09</option>
					<option value="99">99</option>
				</select>
			</div> 
			<br /> <a class="actionButtons a_demo_search a_demo_save " style="color: #FFFFFF; margin-left: 200px;" onclick="saveHitRaceData()">Save </a>

		</div>

		<div id="propertiesTransactionMessage"
			class="transactionMessage successTransactionMessage">
			<span class="message"></span>
		</div>
	</c:if>
	
	<%@ include file="../common/commonScriptsIncludes.html"%>
<script src="${staticPath}/js/utils/hitracecodemap.js?v=${buildDate}"></script>
<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>

</body>