<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:useBean id="userInitObj" scope="session" class="gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.UserInitObject"></jsp:useBean>

    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>NYEHMS Initialization Error</title>
        <%@ include file="../common/common_includes.jsp" %> 
        <%@ include file="../common/commonStylesIncludes.html" %> 
        <%@ include file="../common/MainMenuIncludes.jsp" %> 
        <link href="${staticPath}/css/initialize/CryptoCard.css?v=${buildDate}" rel="stylesheet" type="text/css" /> 
        <link href="${staticPath}/css/email/helpline.css?v=${buildDate}" rel="stylesheet" type="text/css" />   
    </head>

    <body onload="loadajax()" class="cryptoCard">
        <!-- Modal -->
        <div id="emailViewTab" class="height325">
        	<div id="standardMessageCryptModal">
            	<jsp:include page="../email/emailWindow.jsp"></jsp:include>
            </div>
        </div>

        <div id="header">
            <div id="logo">
                <img src="${staticPath}/images/homePage/logo.fw.png"/>
            </div>

            <div id="img">
            </div>

            <div id="info-box">
                <span class="eighteeen">Welcome!</span><br /><br />
                Login issues? Send an <a style="cursor:pointer;" class="loginIssueEmail" onclick="openEmail()">Email</a>
            </div>
        </div>
       

        

        <div id="loginPageerrorMessage" class="alignCenter">
        	<h2>NYEHMS  Access Denied.</h2>
            <div class="marginTop20"><c:out value="${ErrorMessage}" /></div>
            
            
            
             <c:if test="${userInitObj.appletErrorCd == '' }">
                <c:if test="${userInitObj.initializeFailReasonsDescrption != '' }">
                   <div class="marginTop20"><c:out value="${userInitObj.initializeFailReasonsDescrption}"/></div>
                </c:if>
            </c:if>
            <c:if test="${userInitObj.appletErrorCd != '' }">

                <c:if test="${userInitObj.appletErrorCd == 1}">
                    <c:forEach items="${userInitObj.initFailReasons}" var="failId">
                        <c:if test="${failId.reasoncd == 13}">
                           <div class="marginTop20"><c:out value="${failId.description}"/></div>
                        </c:if>
                    </c:forEach>
                    <br>Either one or more required parameter is missing to run the applet. $$$$$$$$$
                </c:if>

                <c:if test="${param.errorNum == 2}">
                    <c:forEach items="${userInitObj.initFailReasons}" var="failId">
                        <c:if test="${failId.reasoncd == 11}">
                           <div class="marginTop20"> <c:out value="${failId.description}"/></div>
                        </c:if>
                    </c:forEach>
                    <br>Problem in reading the user's computer.
                </c:if>

                <c:if test="${param.errorNum == 3}">
                    <c:forEach items="${userInitObj.initFailReasons}" var="failId">
                        <c:if test="${failId.reasoncd == 14}">
                            <div class="marginTop20"><c:out value="${failId.description}"/></div>
                        </c:if>
                    </c:forEach>
                    <br>Problem determining users O/S (i.e. Running windows or not).
                </c:if>

                <c:if test="${param.errorNum == 4}">
                    <c:forEach items="${userInitObj.initFailReasons}" var="failId">
                        <c:if test="${failId.reasoncd == 15}">
                            <div class="marginTop20"><c:out value="${failId.description}"/></div>
                        </c:if>
                    </c:forEach>
                    <br>Problem determining users JRE / Java Version.
                </c:if>


            </c:if>
            
        </div>
        <table>
           
        </table>

        <script type="text/javascript" src="${staticPath}/js/jquery/js/jquery-1.9.1.js"></script>
        <script src="${staticPath}/js/jquery/js/jquery-ui-1.10.1.custom.js?v=1"></script>
        <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
        <script src="${staticPath}/js/loadDialogs.js"></script>
        <script src="${staticPath}/js/initialize/CryptoCard.js?v=${buildDate}"></script>
        <script src="${staticPath}/js/common/commonUtils.js?v=1"></script> 
    </body>
</html>