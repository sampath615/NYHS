<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="actionContentDiv">		
				<div id="actionTabs">
					<ul id="actionsDetails">
						<li id="listActionsTab"><a href="#actionsTab">Actions</a></li>				
						<li id="listOtherActionsTab"><a href="#otherActionsTab">Viewed</a>  </li>						
					</ul>
					
					<div id="actionsTab" style="clear: both; height:76%">						
						<table id="assignmentActivity" >
							<tr>
								<th id="activityDate">Activity Date</th>
								<th id="doneBY">By</th>
								<th id="workerName">Done By Full Name</th>
								<th id="activityDesc">Activity Desc</th>
								<th id="comments">Comments</th>
							</tr>
							
						
							<c:forEach items="${assignmentEditActionCommentDetails}" var="assignmentActivityVO" varStatus="docCount">
								<tr>
									<td>${assignmentActivityVO.activityDate}</td>
									<td>${assignmentActivityVO.doneBYShortName}</td>
									<td>${assignmentActivityVO.doneBY}</td>
									<td>${assignmentActivityVO.activityDesc}</td>
									<td>${assignmentActivityVO.comments}</td>
								</tr>
							</c:forEach>
						</table>
					</div>		
											
					<div id="otherActionsTab" style="clear: both; height:76%">	
						<table id="assignmentViewed">
							<tr>
								<th id="activityDate">Activity Date</th>
								<th id="doneBY">By</th>
								<th id="workerName">Done By Full Name</th>
								<th id="activityDesc">Activity Desc</th>
								<th id="comments">Comments</th>
							</tr>
							
						
							<c:forEach items="${assignmentViewedCommentDetails}" var="assignmentActivityVO" varStatus="docCount">
								<tr>
									<td>${assignmentActivityVO.activityDate}</td>
									<td>${assignmentActivityVO.doneBYShortName}</td>
									<td>${assignmentActivityVO.doneBY}</td>
									<td>${assignmentActivityVO.activityDesc}</td>
									<td>${assignmentActivityVO.comments}</td>
								</tr>
							</c:forEach>
						</table>
					</div>									
				</div> 
			</div>	
			
			


