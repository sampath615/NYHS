<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../common/common_includes.jsp"%>
<%@ include file="../common/commonStylesIncludes.html"%>
<link href="${staticPath}/css/mainmenu/search.css?v=${buildDate}"   rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
<head>
<title>Scheduler Meta Data</title>
</head>
<body>
	<div class="floatRight">
			<span class="mR20 bold"><a href="${contextPath}/web/getAssignmentsTally">Application Home</a></span>
			<span class="mR20 bold"><a href="${contextPath}/web/manualQuartzJob">Manual Job</a></span>
		</div>	
		<div class="clearBoth"></div>
	<c:if test="${TRANSACTION_MESSAGE != null}">

		<div id="schedulerTransactionMessage" class="transactionMessage successTransactionMessage"><span class="message"><c:out value="${TRANSACTION_MESSAGE}" /></span></div>
		<div id="schedulerMetaData" style="background-color: #ebebeb; text-align:center; padding-top:25px; height: 100px; width:327px; border: solid 1px #999; margin: 0 auto; margin-top:25px;">
                      <div style="margin-bottom:10px">
                                      <label style="font-family: Arial,Helvetica,sans-serif;font-size: 10pt; font-weight: bold"> Parameter Name: </label>
                                      <select onchange="getMetaDataValue(this)"  class="required" name="metaDataName" id="metaDataName" style="margin-left: 10px; background: none repeat scroll 0 0 #ffffff;border-color: #cccccc #999999 #999999 #cccccc;border-radius: 8px;border-style: solid;border-width: 1px;font-family: Arial,Helvetica,sans-serif;font-size: 10pt; margin-right: auto;padding: 1px;width: 190px;" title="Parameter name">
                                                      <option value="">Select</option>
														<c:forEach items="${schedulerMetaData}" var="metaData">
															<option value="${metaData.key}">${metaData.key}</option>
														</c:forEach>
                                      </select>
                      </div>
                      <div>
                                      <label style="font-family: Arial,Helvetica,sans-serif;font-size: 10pt; font-weight: bold"> Parameter Value: </label>
                                      <input value="" type="text" name="metaDataValue" id="metaDataValue" class="required" style="margin-left: 10px; background: none repeat scroll 0 0 #ffffff;border-color: #cccccc #999999 #999999 #cccccc;border-radius: 8px;border-style: solid;border-width: 1px;font-family: Arial,Helvetica,sans-serif;font-size: 10pt; margin-right: auto;padding: 1px;width: 190px; " title="Parameter value"/>
                      </div>
                      <br/>
                      	<a class="actionButtons a_demo_search a_demo_save " style="color:#FFFFFF; margin-left: 5px;" onclick="saveMetaData();"> Save </a>
                      
      </div>
      
      
      
      
      <div id="propertiesTransactionMessage" class="transactionMessage successTransactionMessage"><span class="message"></span></div>
		<div id="systemProperties" style="background-color: #ebebeb; text-align:center; padding-top:25px; height: 100px; width:327px; border: solid 1px #999; margin: 100 auto; ">
                      <div style="margin-bottom:10px">
                                      <label style="font-family: Arial,Helvetica,sans-serif;font-size: 10pt; font-weight: bold"> Property Name: </label>
                                      <input   class="required" name="propertyName" id="propertyName" style="margin-left: 10px; background: none repeat scroll 0 0 #ffffff;border-color: #cccccc #999999 #999999 #cccccc;border-radius: 8px;border-style: solid;border-width: 1px;font-family: Arial,Helvetica,sans-serif;font-size: 10pt; margin-right: auto;padding: 1px;width: 190px;" title="Property name" />
                      </div>
                      <div>
                                      <label style="font-family: Arial,Helvetica,sans-serif;font-size: 10pt; font-weight: bold"> Parameter Value: </label>
                                      <input value="" type="text" name="propertyValue" id="propertyValue" class="required" style="margin-left: 10px; background: none repeat scroll 0 0 #ffffff;border-color: #cccccc #999999 #999999 #cccccc;border-radius: 8px;border-style: solid;border-width: 1px;font-family: Arial,Helvetica,sans-serif;font-size: 10pt; margin-right: auto;padding: 1px;width: 190px; " title="Parameter value"/>
                      </div>
                      <br/>
                      	<a class="actionButtons a_demo_search a_demo_save " style="color:#FFFFFF; margin-left: 5px;" onclick="saveSystemProperty();"> Save </a>
                      
      </div>
		
		
		
	</c:if>
	
	
	
<%@ include file="../common/commonScriptsIncludes.html"%>
<script src="${staticPath}/js/scheduler/schedulerMetaData.js?v=${buildDate}"></script>
<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
</body>

