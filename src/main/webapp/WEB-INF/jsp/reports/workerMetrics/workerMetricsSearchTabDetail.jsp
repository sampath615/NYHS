<div id="assignmentSearchContentDiv">
	<div id="tabs" style="height: auto;" ss="bb">
		<ul id="Tabs1">
			<li id="sCriteriaTab"><a href="#searchTab">Worker Metrics Reports Search Criteria</a></li>
			<li id="listreportViewTab" style="display: none;"><a
				href="#reportViewTab">Worker Metrics Reports View</a></li>
		</ul>
		<div id="searchTab" class="height325">
			<div id="personViewSearchFields">
				<%@ include file="workerMetricsSearchCriteria.jsp"%>
			</div>
		</div>
		<div id="reportViewTab" style="clear: both;">
			<%@ include file="workerMetricsDetails.jsp"%>
		</div>
	</div>

</div>