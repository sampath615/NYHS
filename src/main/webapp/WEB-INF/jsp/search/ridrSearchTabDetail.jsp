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
				<li id="listEharsPersonViewTab" style="display: none;"><a
				href="#eharsPersonDetailsTab">eHars Person View</a></li>
			<li id="listLabDocumentViewTab" style="display: none;"><a
				href="#labDocumentTab">Document View</a></li>
			
		</ul>
		<div id="searchTab" style="height:360px">
			<div id="personViewSearchFields">
				<c:choose>
					<c:when test="${stateCodes != null && stateCodes != '[]'}">
						<div id="accordion" style="width: 75%; clear: both;">
							<h3>Search</h3>
							<div>
						
									<form id="searchForm" name="searchForm">
										<div class="personDetailsDiv" id="personNameInfoDiv" style="dispaly:none">
											<div>&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->
											
														
														<div class="width30Perc floatLeft">
															<div class="standardInputDiv">
																<div class="floatLeft">
																	<label class="standardInputTitle">File type</label>
																</div>
																<div class="floatRight">
																	<select id="ridrFileType" name="ridrFileType" title="File Type" class="required standardInputMedText mR50" onchange="loadStateCodesByFileType(this)" >
																			<option value="ALL">ALL</option>
																			<option value="RIDR">RIDR</option>
																			<option value="CIDR">CIDR</option>
																	</select>
																</div>
															</div>
														</div>
														
														<div class="width30Perc floatLeft">
															<div class="standardInputDiv">
																<div class="floatLeft">
																	<label class="standardInputTitle">State</label>
																</div>
																<div class="floatRight">
																	<select id="state_Search" name="state_Search" title="STATE" class="required standardInputMedText mR50" >
																			<option value="">Select</option>
																			<c:forEach items="${stateCodes}" var="_state">
																				<option value="${_state}">${_state}</option>
																			</c:forEach>
																	</select>
																</div>
															</div>
														</div>
														<div class="width30Perc floatLeft">
															<div class="standardInputDiv">
																<a class="a_demo_search" style="color: #FFFFFF; margin-left: 5px;" onclick="getStateCodeSearchResults();" id="stateCodeSearchResultsButton"> Search </a>
															</div>
														</div>
												
											
											<div class="clearBoth" ></div>
											<div style="margin-top:20px">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->
										</div>			
										
									</form>
							</div>
						</div>
				
				</c:when>
										
				<c:otherwise>
					<div class="transactionMessage mT50"><span class="message">
			
							Currently no RIDR documents to process
		
					</span></div>
				
				</c:otherwise>
				
			</c:choose>
			</div>
		</div>
		<div id="searchResultsTb" style="clear: both; overflow : scroll">

			<div id="personViewSearchResults">
				<div id="searchContainerModal" style="width: 100%">
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
		</div>
		<div id="labDocumentTab" style="clear: both;">
		
			<div id="documentEditTransactionMessage" class="transactionMessage"><span class="message"></span></div>
			<div id="documentContent">
			
			</div>
		</div>
 	<div id="eharsPersonDetailsTab" style="clear: both; overflow: scroll">

		</div>
		
	</div>


</div>