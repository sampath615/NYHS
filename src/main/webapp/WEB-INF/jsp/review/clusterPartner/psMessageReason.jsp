<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div  style="text-align:center; font-weight:bold;margin:5px">
<span class="searchFacilityTxt" >${param.title} </span>
<span class="mL10">
<select id="psMessageReason" name="psMessageReason" class="required standardinputMedText" title="${param.title}" onChange="loadClstPSReasonElements(this)">
	<option value=""> -- Select --</option>
	<c:forEach items="${PS_REASON_LIST}" var="reason">
		<option value="${reason.LOOKUP_CD}">${reason.LOOKUP_NAME}</option>
	</c:forEach>
</select>	
</span>
</div>
<div class="displayNone mT10" id="psMessageOtherReasonElement" style="text-align:center; font-weight:bold;margin:10px">
<span class="searchFacilityTxt" >Other reason </span>
<span class="mL10">
<input id="psMessageOtherReason" name="psMessageOtherReason" class="required standardinputMedText" title="Other reason"/>
</span>

</div>