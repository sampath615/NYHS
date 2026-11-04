<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="assignmentSearchContentDiv">
	<div id="tabs" style="height: auto;" ss="bb">
		<ul id="Tabs1">
			<li id="sCriteriaTab"><a href="#searchTab">Search Criteria</a></li>
			<li id="sResultsTab" style="display: none;"><a
				href="#searchResultsTb">Search Results</a></li>
			<li id="listPersonViewTab" style="display: none;"><a
				href="#personDetailsTab">Recent Demo View</a></li>
			<li id="listDocumentViewTab" style="display: none;"><a
				href="#documentDetailsTab" id="documentTimeline_search">Document Timeline</a></li>
			<li id="listLabDocumentViewTab" style="display: none;"><a
				href="#labDocumentTab">Document View</a></li>
	        <li id="listEharsPersonViewTab"  style="display: none;"><a href="#eharsPersonDetailsTab">eHars Person View</a></li>
		</ul>
		<div id="searchTab" style="height:360px">
			<div id="personViewSearchFields">
				<%@ include file="../search/SearchCriteria.jsp"%>
			</div>
		</div>
		<div id="searchResultsTb" style="clear: both;">
		    
		    
			<div class="floatRight"><label class="bold"><span class="redNote">*</span>The value that occurs most frequently for the STATENO are displayed here.</label></div>
			
		    
		    <div id="statenoFlagStatus" class="clearBoth"> <img src="${staticPath}/images/icons/Flg1-icon.png"><font class="flagFont"> Indicates default status </font>
			      <img src="${staticPath}/images/icons/Flg2-icon.png"><font class="flagFont"> STATENO is selected </font>
				  <img src="${staticPath}/images/icons/Flg3-icon.png"><font class="flagFont"> STATENO has been flagged same and is currently pending Review </font>
				  <img src="${staticPath}/images/icons/Flg4-icon.png"><font class="flagFont"> "STATENO has been flagged as bad merge and is currently pending review" </font></div>
				  
			
			<div id="personViewSearchResults">
				<div id="searchContainerModal" style="width: 100%" class="jqGridTableSection">
					<table id="searchResultsTab"></table>
				</div>
				<div id="searchPagerDiv1">
					<table id="searchResultsTab"></table>
				</div>
			</div>
		</div>

		<div id="personDetailsTab" style="clear: both; overflow: scroll">
			<!-- Tab content will be build dynamically  -->
		</div>
		<div id="personTableContainerModal">
			<table id="personFieldValuesTbl"></table>
		</div>

		<div id="documentDetailsTab" style="clear: both;">
		
			<div id="associateDocToStatenoTransactionMessage" class="transactionMessage"><span class="message"></span></div>
			
			<form name="documentForm" class="documentDetails">
				<div id="documentDetailsDiv">
					<table id="documentDetails"></table>
					<div id="documentDetailsPagerDiv"></div>
				</div>
			</form>
			
								
				<div> 
					<div class="floatLeft">
						<label class="redNote">Note:</label>
					</div>
					<div class="floatLeft">
						<div><span class="textDecorationWithGray">Documents highlighted in gray font are Discard documents</span></div>
					
						<div class="mT5"><span class="textDecorationLineThrough">Documents with line through and with gray font are Deleted documents</span></div>
						
						<div class="mT5"><span >Documents will be shown based on the permissions.</span></div>
					</div>
				</div>
			
		</div>
		<div id="labDocumentTab" style="clear: both; width:100%" class="positionAbsolute">
		
			<div id="documentEditTransactionMessage" class="transactionMessage"><span class="message"></span></div>
			<div id="documentContent" class="clearBoth">
			
			</div>
		</div>
			<div id="eharsPersonDetailsTab" style="clear: both; overflow: scroll">
		
		   </div>
		
		<div id="srchContainerModal" title="All Variations">
			<table id="searchBestIndicatedTbl"></table>
		</div>
	</div>
	<c:if  test="${userPermissions['17'] != null}" >
		<div id="flaggedStNoWithOriginCode" title="Please Confirm?" class="displayNone">
			<h4 id="confirmMergeContentContent"> Do you want to flag the selected two STATENOs as same? </h4>
			<div>
				<select id="originCategories" name="originCategories" class="required standardinputMedText">
					<option value="" selected="true"> -- Select --</option>
					<c:forEach items="${originCategories}" var="category">
						<c:if  test="${userPermissions['141'] != null or category.ORIGIN_TYPE == 'USER'}" >
				 			<option value="${category.ORIGIN_CODE}" origin_type="${category.ORIGIN_TYPE}">${category.ORIGIN_DESC}</option>
				 		</c:if>
				 	</c:forEach>
				</select>	
			</div>
			
		   </br>
		   <div id="confirmMergeStNotext">
				<span style="padding-left:0">Comments </span></br>
	   			<textarea id="confirmRequestComments" class="required" cols="40" rows="4" style="border: thin solid #003366;" maxLength="3000" title="<spring:message code='label.requestComments' />"></textarea>
		  </div>   
	   </div>
	</c:if>
</div>