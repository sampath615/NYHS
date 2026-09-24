<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<form id="statenoFlagging">
	<div style="height: 125px;">
		<div class="stdDetailsDiv" id="stdInfoDiv">
			<div class="fLW33">
				<div class="standardInputDiv">
					<div class="fLW33">
						<span class="stateno1Label bold"><spring:message
								code="label.stateno1" /></span>
					</div>
					<div class="fLW50">
						<input id="stateno1" name="stateno1"
							class="smallInputText required" value="" type="text"
							title="<spring:message code='title.stateno1' />" maxLength=10 />
					</div>
				</div>
			</div>
			<div class="fLW33">
				<div class="standardInputDiv" style="margin-bottom: 10px">
					<div class="fLW33">
						<span class="stateno2Label bold"><spring:message
								code="label.stateno2" /></span>
					</div>
					<div class="fLW50">
						<input id="stateno2" name="stateno2"
							class="smallInputText required" value="" type="text"
							title="<spring:message code='title.stateno2' />" maxLength=10 />
					</div>
				</div>

			</div>
			<div class="fLW33">
				<div class="standardInputDiv" style="margin-bottom: 10px">
					<div class="fLW33">
						<span class="bold">Origin Categories</span>
					</div>
					
					<div class="fLW50">
						<select id="originCategories" name="originCategories" class="standardinputMedText required" value="" type="text" maxLength=10 >
							 <option value=""> -- Select --</option>
							 <c:forEach items="${originCategories}" var="category">
							 		
							 		<c:if  test="${userPermissions['141'] != null or category.ORIGIN_TYPE == 'USER' or userPermissions['244'] != null}" >
							 			<option value="${category.ORIGIN_CODE}" origin_type="${category.ORIGIN_TYPE}">${category.ORIGIN_DESC}</option>
							 		</c:if>
							 		
							</c:forEach>
						</select>	
					</div>
				</div>

			</div>
			<div class="fieldHolder75">
				<div class="standardInputDiv">
					<div class="fLW33">
						<span class="bold"><spring:message
								code="label.requestComments" /></span>

					</div>
					<div class="fLW40">
						<textarea id="requestComments_flagging" name="requestComments_flagging"
							class="standardTextAreaComments required" ROWS="3" COLS="51"
							title="<spring:message code='label.requestComments' />" maxLength=3000 ></textarea>
					</div>
					
					<div class="mT20 floatRight">
					<a class="a_demo_search" style="color: #FFFFFF; margin-left: 5px;"
						onclick="flagStatenoPairs();" id="submitStatenoPairs"> Flag as
						Same</a>
				</div>
				</div>
			</div>
	

		</div>

		<div style="clear: both;">
			<br />
		</div>
	</div>
</form>

