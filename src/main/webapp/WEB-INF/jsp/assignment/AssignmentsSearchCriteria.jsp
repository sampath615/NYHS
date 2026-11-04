
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<form:form modelAttribute="assignmentsHomeVO"
	action="getAssignmentsList" id="reloadedAssignmentsSearchCriteria">
	
	
	<%@ include file="AssignmentsListCriteria.jsp" %>  
	
	
</form:form>
