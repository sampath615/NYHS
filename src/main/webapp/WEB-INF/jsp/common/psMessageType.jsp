<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div style="text-align:center; font-weight:bold;margin:5px">
	  	<span class="searchFacilityTxt"> Message Type :</span>
		<span>
				<select id="triggeringMessageType"  name="triggeringMessageType" class="required standardinputLongText " title="Message Type">
					<option value="">Select</option>
					<c:forEach items="${sessionScope.psMessageTypes}" var="messageType">
						<option value="${messageType.PS_MSG_TYPE}" class="${messageType.DOCUMENT_TYPE_CODE}">${messageType.PS_MSG_TYPE_DESCRIPTION}</option>
					</c:forEach>
				</select>
		</span>
</div>