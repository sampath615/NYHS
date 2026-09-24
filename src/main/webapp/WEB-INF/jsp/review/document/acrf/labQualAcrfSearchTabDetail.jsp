<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="assignmentSearchContentDiv">
	<div id="tabs" style="height: auto;" ss="bb">
		<ul id="Tabs1">
			<li id="sCriteriaTab"><a href="#searchTab">Search Criteria</a></li>
			<li id="listLabDocumentViewTab" style="display: none;"><a
				href="#labDocumentTab">Document View</a></li>
			
		</ul>
		<div id="searchTab" style="height:360px">
			<div id="personViewSearchFields">
				
						<div id="accordion" style="width: 75%; clear: both;">
							<h3>Search</h3>
							<div>
						
									<form id="searchForm" name="searchForm">
										<div class="personDetailsDiv" id="personNameInfoDiv" style="dispaly:none">
											<div>&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->
											
														<div style="margin: 0px auto">
															<div class="width50Perc floatLeft">
																<div class="standardInputDiv">
																	<div class="floatLeft">
																		<span><label class="standardInputTitle">ACRF DocumentId</label></span>
																		<span class="mL50">
																			<input id="editAcrfDocId" name="acrfDocIdToEdit" title="ACRF DocumentId" class="required standardInputMedText" />
																		</span>
																	</div>
																</div>
															</div>
															<div class="width30Perc floatLeft">
																<div class="standardInputDiv">
																	<a class="a_demo_search" style="color: #FFFFFF; margin-left: 5px;" onclick="getAcrfDocumentSearchResults();" id="AcrfDocumentSearchResultsButton"> Search </a>
																</div>
															</div>
														</div>
												
											
											<div class="clearBoth" ></div>
											<div style="margin-top:20px">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->
										</div>			
										
									</form>
							</div>
						</div>
				
				
			</div>
		</div>

		<div id="labDocumentTab" style="clear: both;">
		
			<div id="documentEditTransactionMessage" class="transactionMessage"><span class="message"></span></div>
			<div id="documentContent">
			
			</div>
		</div>
		
	</div>


</div>