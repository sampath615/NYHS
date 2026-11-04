<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="${staticPath}/js/docs/prfDoc.js?v=${buildDate}"></script>

<link href="${staticPath}/css/docs/prfForm.css?v=${buildDate}" rel="stylesheet" type="text/css" />
<!--<c:out value="${stdRawEXPDocVO}" />-->
<form:form modelAttribute="stdRawEXPDocVO" id="stdRawEXPDocVO" cssClass="documentDescription">
       <!-- <div id="labDocTab" style="background-color: transparent"> -->
              <div style="text-align: center; " class="headingCls">
                     Health Information Exchange Death Document
              </div>
              
              <div style="padding-bottom:10px; text-align: right;">
              
            <span class="mL10 " >Document ID:</span>
                     <span class=" "><form:input path="documentId" class="standardinputSmallText ml10" size="10" readonly="true"/></span>
        </div>    
              
         <div class="container w100 nonRawEditFields pB10" >
              <div style="float:left; width: 40%">
                   <div>
                       <span class=" ml10 "><spring:message code="label.stateNo" />: </span>
                    <span class="ml10"><form:input path="stateno" class="standardInputSmallText" maxlength="10" readonly="true"/> </span>
                   </div>    
            </div>
            <div style="float:left; width: 30%">
            	<div>
                       <span class="ml10 "> eHARS Import Date: </span>
                       <fmt:formatDate value="${stdRawEXPDocVO.eharsImportDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
                    	<span class="ml10" ><form:input path="eharsImportDate" value="${theFormattedDate}" class="standardInputSmallText" maxlength="10" readonly="true"/></span>
                   </div>    
            </div>
            <div style="float:left; width: 30%; text-align: right">
                <div>
                       <span class="ml10 "> NYC SHIP DATE: </span>
                       <fmt:formatDate value="${stdRawEXPDocVO.nycShipDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedShipDate" />
                    	<span class="ml10" ><form:input path="nycShipDate" value="${theFormattedShipDate}" class="standardInputSmallText" maxlength="10" readonly="true"/></span>
                   </div> 
            </div> 
         </div>
         
         <jsp:include page="statenoDetails.jsp"></jsp:include>
         
       
              <!--  Person details -->
              
              <div class="container" style="border:1px dotted; width:100%"> 
                           <div id="psDetailsHeadingDiv" class="divHeadingCls" style="background:#DDDDDD; ">
                                  <span class=" mL2">Person Details</span>
                           </div>
                     
                           <div>
                                  <table class="b1" style="margin: 15px 5px 10px 5px; border:0px dotted;padding: 10px;font-family: sans-serif;font-size: 12px;color: #003366">
                                         <tr class="standardInputMedText" style="margin: 5px 5px 5px 15px;width:450px ;padding-bottom: 5em;height: 30px">
                                                <td width="400px">First Name : <form:input path="firstName" size="40" class="standardInputMedText" /></td>
                                                <td width="450px"> Last Name : <form:input path="lastName" size="40" class="standardInputMedText"/> </td>
                                         </tr><tr></tr>
                                         <tr class="standardInputMedText" style="margin: 5px 15px 5px 5px;width:250px; padding-bottom: 5em;height: 30px"" >
                                                <td>Date of Birth :  <form:input path="birthDate" size="40" class="standardInputMedText" /> </td>
                                                <td>Date of Death :  <form:input path="deathDate" size="40" class="standardInputMedText" /> </td>
                                                <td width="350px">MPI : <form:input path="mpiid" size="40" class="standardInputMedText" readonly="true"/></td>
                                         </tr><tr></tr>
                                         <tr class="standardInputMedText" style="margin: 5px 15px 5px 5px; width:auto; ;padding-bottom: 5em;height: 30px"">
                                                <td>Medical Record Number : <form:input path="MRN" size="40" class="standardInputMedText" /></td>
                                                <td colspan="2"><span class="label">Facility :</span><span > <form:input class="standardInputText width100perc" style="width: 65%"  readonly="true" path="sourceFacility"/></span></td>
                                         </tr><tr></tr>
                                         <tr  class="standardInputMedText" style="margin: 5px 15px 5px 5px; width:250px;padding-bottom: 5em;height: 30px"">
                                                <td>HIE Name : <form:input  path="hieName" class="standardInputMedText" /> </td>
                                                <td>                              
                                                <fmt:formatDate value="${stdRawEXPDocVO.receivedDate}"  type="date" pattern="MM/dd/yyyy" var="theFormattedReceivedDate" />
                                                Received Date : <form:input path="receivedDate" size="40" class="standardInputMedText" value="${theFormattedReceivedDate}"/> 
                                         </td>
                                         </tr><tr></tr>                           
                                  </table>
                           </div>
                           
          </div>
                                                                                                                                                                                                             
</form:form>
              
              
 
<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
		 <div id="button-row" class="button-row documentButtons center">
		 					<c:if test="${userPermissions['90'] != null && stdRawEXPDocVO.stateno != null && stdRawEXPDocVO.stateno != '' && nyehmsStatenoDetails.REASON_DESC == null}">
									<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
							</c:if>

									
		</div>