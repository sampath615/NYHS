<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="clearBoth pT10 referenceElement" id="notification_USER${param.notificatonIndex}" >
			<div class="floatLeft width45Perc"><span class="font10pt bold">User : <input type="text" class="usersNotificationEmail" id="user_email${param.notificatonIndex}" class="usersNotificationEmail"/></span></div>
			<input type="hidden" class="selWorkerId" value=""/>        
			<div class="floatLeft width45Perc bold displayNone notificationSelection"  style="color:black" id=""><span><input type="radio" class="toNotification" value="T" name="notification_USER${param.notificatonIndex}_status"/></span> <span class="mL50"><input type="radio" class="ccNotification" value="C" name="notification_USER${param.notificatonIndex}_status"/></span>
			<span class="mL50">
			
				<a href="#" class="deleteButton " title="Delete Notification"  onclick="deleteNotifications('notification_USER${param.notificatonIndex}')">  </a>
				
			</span>
			
			</div>	 
		</div>
