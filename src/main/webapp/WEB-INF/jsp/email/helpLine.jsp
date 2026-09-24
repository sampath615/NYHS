<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Helpline</title>
        <%@ include file="../common/common_includes.jsp" %> 
        <%@ include file="../common/commonStylesIncludes.html" %> 
        <%@ include file="../common/MainMenuIncludes.jsp" %> 
        <link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
        <link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
        <link href="${staticPath}/css/jquerycombobox.css" rel="stylesheet" type="text/css" />
        <link href="${staticPath}/css/email/helpline.css?v=${buildDate}" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="${staticPath}/css/reports/toggles-light.css" type="text/css"/>
    </head>

    <body class="helpLine">
        
        <%@ include file="../common/Menu.jsp"%>
       
        <div id="emailContentDiv">
            <div id="tabs" style="height: auto;" ss="bb">
                <ul id="Tabs1">
                    <li id="sCriteriaTab"><a href="#emailViewTab">Email Helpline</a></li>
                </ul>
                <div id="emailViewTab" class="height325">
                
                	<div id="sendEmailTransactionMessage" class="transactionMessage"><span class="message"></span></div>
                
                    <jsp:include page="emailWindow.jsp"></jsp:include>
                    
					
                </div>
            </div>
        </div>
        <%@ include file="../common/commonScriptsIncludes.html"%>
        <script src="${staticPath}/js/main.js"></script>
        <script src="${staticPath}/js/jquery/jquery.collapsible.js"></script>
        <script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
        <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
        <script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
        
        <script type="text/javascript">
        	$('#emailAccordion').accordion();
        </script>
    </body>
</html>
