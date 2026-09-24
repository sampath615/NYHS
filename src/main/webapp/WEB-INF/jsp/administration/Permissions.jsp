<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="standardInputDiv"><span> <strong>PERMISSIONS</strong></span></div>
<div class="permissionDetailsDiv">
			
	<div id="userPermissions" class="floatLeft">
		<c:forEach items="${moduleAndPermissionsList}" var="moduleVO">
			
			<div style="width:290px;float:left;margin-bottom:30px" id="modulePermissions_${moduleVO.moduleId}">
				<div style="display:block">
					
					<c:if test="${moduleVO.moduleId =='16' }">
						<span><input type="checkbox" name="documentModulePermissions" id="documentModulePermissions" onClick="enableModulePermissions(this)" value="${moduleVO.moduleId}"/></span>
					</c:if>
					<font style="color: #024B72;font-family: Lucida Grande;font-size: 1.2em;opacity: 0.8;text-align:center; font-weight: bold">${moduleVO.moduleName}</font>
					
					
					
					
				</div>
				<c:forEach items="${moduleVO.nyehmsPermissions}" var="permisssionVO">
				
						
					<div class="floatLeft" >
						<c:choose>
							<c:when test="${permisssionVO.isSuggestedPermission == 'TRUE'}">
								<input id="permissionName_<c:out value='${permisssionVO.permissionId}' />" class="" value="<c:out value='${permisssionVO.permissionId}' />" type="checkbox" checked="checked" tabindex="-1"/>
							</c:when>
							<c:otherwise>
								<input id="permissionName_<c:out value='${permisssionVO.permissionId}' />" class="" value="<c:out value='${permisssionVO.permissionId}' />" type="checkbox" tabindex="-1"/>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="floatLeft" style="padding: 4px 0 0 4px; width: 260px">
						<font class="">${permisssionVO.permissionDescription}</font>
					</div>
					<div class="clearBoth" ></div>
				</c:forEach>
			</div>
		</c:forEach>		
	</div>
</div>
	