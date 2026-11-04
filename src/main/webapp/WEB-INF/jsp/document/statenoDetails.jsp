<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="clearBoth container pB10 w100 nonRawEditFields">
            <div style="float:left; padding-bottom:10px; width: 40%">
	            <div class="positionAbsolute">
	                <span class=" ml10">CITYNO: </span>
	                <span class="ml10 showMultiData" id="nycCITYNO_docViewer"><c:out value="${nyehmsStatenoDetails.NYC_CITYNO}" /></span>
	                
	                
	                <c:if test="${nyehmsStatenoDetails.NYC_CITYNO_LIST != null && nyehmsStatenoDetails.NYC_CITYNO_LIST != '[]' }">
						<span class="historyCharacter">*</span>
						<div
							class='displayNone stateNoHistoryData nycCITYNO_docViewer'>
							<table
								class='statenoHistory historyTable<c:out value="${personViewNumber}" />'>
								<tr>
									<th class="historyTableHeader">CITYNO</th>
								</tr>
								<c:forEach var="item" items="${nyehmsStatenoDetails.NYC_CITYNO_LIST}">
									<tr>
										<td>${item}</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</c:if>
	                
	                
	            </div>
            </div>
            <div style="float:left; padding-bottom:10px; width: 33%">
            	<div class="positionAbsolute">
	                <span class="ml10">eHARS NYC STATENO: </span>
	                <span class="ml10 showMultiData" id="nycEHARS_STATENO_docViewer"><c:out value="${nyehmsStatenoDetails.NYC_EHARS_STATENO}" /></span>
	                
	                <c:if test="${nyehmsStatenoDetails.NYC_EHARS_STATENO_LIST != null && nyehmsStatenoDetails.NYC_EHARS_STATENO_LIST != '[]' }">
						<span class="historyCharacter">*</span>
						<div
							class='displayNone stateNoHistoryData nycEHARS_STATENO_docViewer'>
							<table
								class='statenoHistory historyTable<c:out value="${personViewNumber}" />'>
								<tr>
									<th class="historyTableHeader">eHARS NYC STATENO</th>
								</tr>
								<c:forEach var="item" items="${nyehmsStatenoDetails.NYC_EHARS_STATENO_LIST}">
									<tr>
										<td>${item}</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</c:if>
	                
	            </div>                    
            </div>
            <div style="float:left; padding-bottom:10px; width: 23%; text-align: right">
            	        	
	            <div class="positionAbsolute">
	                <span class="mL10"> eHARS NYS STATENO:	 </span>
	                <span class="ml10 showMultiData" id="nysEharsStateno_docViewer"><c:out value="${nyehmsStatenoDetails.EHARS_STATENO}" /></span>

						<c:if test="${nyehmsStatenoDetails.EHARS_STATENO_LIST != null && nyehmsStatenoDetails.EHARS_STATENO_LIST != '[]' }">
							<span class="historyCharacter">*</span>
							<div
								class='displayNone stateNoHistoryData nysEharsStateno_docViewer'>
								<table
									class='statenoHistory historyTable<c:out value="${personViewNumber}" />'>
									<tr>
										<th class="historyTableHeader">eHARS NYS STATENO</th>
									</tr>
									<c:forEach var="item" items="${nyehmsStatenoDetails.EHARS_STATENO_LIST}">
										<tr>
											<td>${item}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:if>
	             
	            </div>                   
            </div>     
   		</div>
   		
   		
   		
   		
   		<c:if test="${nyehmsStatenoDetails.CREATE_ID != null}" >
	   		
	   		<div class="clearBoth container mT5  pB10 w100 nonRawEditFields">
	            <div style="float:left; padding-bottom:10px; width: 40%">
		            <div class="positionAbsolute">
		                <span class=" ml10">Discard By: </span>
		                <span class="ml10 showMultiData" id="nycCITYNO_docViewer"><c:out value="${nyehmsStatenoDetails.CREATE_ID}" /></span>
		            </div>
	            </div>
	            <div style="float:left; padding-bottom:10px; width: 33%">
	            	<div class="positionAbsolute">
		                <span class="ml10">Discard Date: </span>
		                <span class="ml10 showMultiData" id="nycEHARS_STATENO_docViewer"><c:out value="${nyehmsStatenoDetails.CREATE_DATE}" /></span>
		                
		            </div>                    
	            </div>
	            <div style="float:left; padding-bottom:10px; width: 23%; text-align: right">
	            	        	
		            <div class="positionAbsolute">
		                <span class="mL10"> Discard Reason:	 </span>
		                <span class="ml10 showMultiData" id="nysEharsStateno_docViewer"><c:out value="${nyehmsStatenoDetails.REASON_DESC}" /></span>
		                 
		            </div>                   
	            </div>     
	   		</div>
   		</c:if>