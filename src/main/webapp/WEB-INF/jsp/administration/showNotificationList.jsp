<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div style="border:1px dotted; padding : 0 30 30 30" class="marginTop20 marginBottom20" id="manageNotifications">
	
	<div class="floatRight">
		<a href="#" class="addButton" title="addNewEntry" onclick="addNewNotification(${notificationListSize});">  </a>
	</div>
	
	<div class=" clearBoth pT10 " >
			<div class="floatLeft width45Perc"><span class="font10pt bold">User</span></div>
			        
			<div class="floatLeft width45Perc bold"  style="color:black"><span>
				To
			</span>
			<span class="mL50">
				CC		
			</span>
			
			</div>	 
		</div>

	<c:forEach items="${notificationList}" var="userObj" varStatus="docCount">
		
		<div class=" clearBoth pT10 referenceElement" id="notification_USER${docCount.index}">
			<div class="floatLeft width45Perc"><span class="font10pt bold"><c:out value="${userObj.LAST_NAME}" />,<c:out value="${userObj.FIRST_NAME}" />&nbsp;&nbsp;(<c:out value="${userObj.EMAIL_ADDR}" />)</span></div>
			<input type="hidden" class="selWorkerId" value="${userObj.WORKER_ID}"/>        
			<div class="floatLeft width45Perc bold"  style="color:black"><span>
			<c:choose>
				<c:when test="${userObj.NOTIFICATION_STATUS == 'T'}">
					<input type="radio" class="toNotification" name="notification_USER${docCount.index}_status" value="T" checked/> 
				</c:when>
				<c:otherwise>
					<input type="radio" class="toNotification" name="notification_USER${docCount.index}_status" value="T" />
				</c:otherwise>
			</c:choose>
			</span>
			<span class="mL50">
				<c:choose>
					<c:when test="${userObj.NOTIFICATION_STATUS == 'C'}">
						<input type="radio" class="ccNotification" name="notification_USER${docCount.index}_status" value="C" checked/> 
					</c:when>
					<c:otherwise>
						<input type="radio" class="ccNotification" name="notification_USER${docCount.index}_status" value="C" />
					</c:otherwise>
				</c:choose>
			</span>
			<span class="mL50">
				<a href="#" class="deleteButton" title="Delete Notification"  onclick="deleteNotifications('notification_USER${docCount.index}')">  </a>
			</span>
			</div>	 
		</div>
	</c:forEach>

	
	
	
</div>

<div id="actionButtons" class="clearboth mergeButtons mT40" align="center">                  
               <span><a class="actionButtons a_demo_search a_demo_continue"  onClick="saveEmailNotifications()" style="color: #FFFFFF; margin-left: 5px;"> Save</a></span>  
               <span><a class="actionButtons a_demo_search a_demo_cancel"  onClick="refreshNotifications()" style="color: #FFFFFF; margin-left: 5px;"> Reload</a></span>
</div>