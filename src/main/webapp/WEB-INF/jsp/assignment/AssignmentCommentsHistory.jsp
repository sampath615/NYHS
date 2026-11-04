<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		<c:forEach items="${assignmentEditActionCommentDetails}" var="assignmentActivityVO" varStatus="docCount">
			<c:if test="${assignmentActivityVO.comments != null}">	
				<p>${assignmentActivityVO.activityDate} &nbsp;&nbsp;&nbsp; <b>${assignmentActivityVO.doneBY}</b> <span class="assignmentCommentsHistoryText">${assignmentActivityVO.comments}</span></p>
			
		</c:if>
	</c:forEach>
