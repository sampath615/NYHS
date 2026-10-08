<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta http-equiv="cache-control" content="no-cache" />
        <title>NYEHMS HOME</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>        
<c:set var="staticPath" value="/hpn/ctrldocs/nyehms"/>

  


  <%@ include file="../common/commonStylesIncludes.html" %>
   
  <%@ include file="../common/MainMenuIncludes.jsp" %>
  
  
    </head>

    <body>
             
	<div class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-front ui-dialog-buttons ui-draggable" style="height: auto; width: 1000px; top: 233px; left: 312px; display: block;" tabindex="-1" role="dialog" id="welcomeNote" aria-describedby="confirmMessageDialog" aria-labelledby="ui-id-1">
			
			<div class="pB40">
				<div class="floatLeft grayShadowFont" >Welcome to NYEHMS</div>
				<div class="floatRight grayShadowFont" ><c:out value="${sessionScope.UserObj.userFullName}" /></div>
			</div>
			
			
			<div class="clearBoth ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix pT10">
					<span id="ui-id-1" class="ui-dialog-title">You must agree to the page disclaimer to continue use of NYEHMS</span>
					<button class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close" role="button" aria-disabled="false" title="close"><span class="ui-button-icon-primary ui-icon ui-icon-closethick"></span><span class="ui-button-text">close</span></button>
			</div>
			<div id="confirmMessageDialog" style="display: block; width: auto; min-height: 0px; max-height: none; height: auto;" class="ui-dialog-content ui-widget-content ">
				
				<c:out value="${ACKNOWLEDGEMENT_MESSAGE}" escapeXml="false"/>
				
		   </div>
		   <div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
			   	<div class="ui-dialog-buttonset">
			   		<button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false" onclick="agree()"><span class="ui-button-text">Agree</span></button>
			   		<button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false" onclick="logout()"><span class="ui-button-text">Logout</span></button>
			   	</div>
		   </div>
	</div>  
        <script src="${staticPath}/js/jquery/js/jquery-1.9.1.js"></script>
        <script src="${staticPath}/js/common/disableNavigation.js"></script>

	<script type="text/javascript">
	
		var contextPath="${contextPath}";
	
		function agree(){
		    window.location.href = contextPath + "/web/agreeNYEHMSTANDC";
		    
		    return true;
		}
		
		function logout(){
		    window.location.href = contextPath + "/web/logout";
		    
		    return false;
		}
	
	</script>	
</html>
