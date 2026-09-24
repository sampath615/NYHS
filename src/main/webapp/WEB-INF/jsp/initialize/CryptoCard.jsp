<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta http-equiv="Cache-Control" content="no-store" />
        <title>Crypto Card Entry</title>
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

        	<div id="image-winner-box" class="displayNone">
                <span class="cryptologinwinnerfont">  </span>
            </div>

            <div id="img">
            </div>

            <div id="info-box">
                <span class="eighteeen">Welcome!</span><br /><br />
                Login issues? Send an <a onclick="openEmail()" class="loginIssueEmail" style="cursor:pointer;">Email</a>
            </div>
        </div>
        <div class="form-pwd" id="formPwd">
            <form:form method="post" action="${contextPath}/web/cryptoCall" autocomplete="off">
                <div>
                    <div id="login-box">
                        <div id="number">
                            Enter Crypto-Card Number: <br><br>
                            <input name="cryptocardnum" id="cryptocardnum" class="required" title="cryptocard password should contain minimum 11 digits and maximum 16 digits" pattern=".{11,16}" type="password" size="30" style="border-radius:10px ;  width:195px; color: black; border: #003366 1px solid; " onfocus="setVisibility('CCinfoTitle','inline')" onBlur="setVisibility('CCinfoTitle','none')"  value="" />   
                            <a href="#" id="CCinfo" onclick="setVisibility('CCinfoTitle','inline')" onBlur="setVisibility('CCinfoTitle','none')">
                                <img src="${staticPath}/images/icons/question_mark.png" alt="Question Mark" style="width:16px; height:16px">
                            </a>    
                            <input name="send" type="submit" value="SEND" style="padding:3px 5px; margin-left:10px; border-radius:10px ; background: #108205; color: #ffffff; font-weight:bold; border: #FFF 1px solid; opacity: 1;"/>
                        </div>
                        
                        <div id="loginPageerrorMessage" class="alignCenter">
                            <c:out value="${TRANSACTION_MESSAGE}" />
                        </div>
                    </div>
                </div>
                <div id="footer">
                    NYEHMS Version 6.1.1 2013
                </div>
            </form:form>
        </div>    
        <div id="CCinfoTitle">Enter 'PIN' (XXXX) followed by Cryptocard OTP token (123-4567) i.e XXXX123-4567 </div>       
        <div class="errorMessage"></div>
        <script type="text/javascript" src="${staticPath}/js/jquery/js/jquery-1.9.1.js"></script>
        <script src="${staticPath}/js/jquery/js/jquery-ui-1.10.1.custom.js?v=1"></script>
        <script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
        <script src="${staticPath}/js/loadDialogs.js"></script>
        <script src="${staticPath}/js/common/commonUtils.js?v=1"></script>
        <script src="${staticPath}/js/initialize/CryptoCard.js?v=${buildDate}"></script> 
        <script src="${staticPath}/js/common/disableNavigation.js"></script>
    </body>
</html>