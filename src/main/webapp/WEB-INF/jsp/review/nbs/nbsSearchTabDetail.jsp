<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="assignmentSearchContentDiv">
	<div id="tabs" style="height: auto;" ss="bb">
		<ul id="Tabs1">
			<li id="sCriteriaTab"><a href="#searchTab" id="nbsSearchCriteriaLink">Search Criteria</a></li>
			<li id="sResultsTab" style="display: none;"><a  id="nbsSearchResultsLink"
				href="#searchResultsTb">Search Results</a></li>
			<li id="listLabDocumentViewTab" style="display: none;"><a  id="nbsDocumentView"
				href="#labDocumentTab">Document View</a></li>
		</ul>
		<div id="searchTab" style="height:360px">
			<div id="personViewSearchFields">
				<%@ include file="nbsSearchCriteria.jsp"%>
			</div>
		</div>
		<div id="searchResultsTb" style="clear: both;">
		    
			<div id="personViewSearchResults">
				<div id="searchContainerModal" style="width: 100%" class="jqGridTableSection">
					<table id="searchResultsTab"></table>
				</div>
				<div id="searchPagerDiv1">
					<table id="searchResultsTab"></table>
				</div>
			</div>
		</div>

		
		<div id="labDocumentTab" style="clear: both; width:100%" class="positionAbsolute">
		
			<div id="documentEditTransactionMessage" class="transactionMessage"><span class="message"></span></div>
			<div id="documentContent" class="clearBoth">
			
			</div>
		</div>
			
		
		
	</div>
	

</div>