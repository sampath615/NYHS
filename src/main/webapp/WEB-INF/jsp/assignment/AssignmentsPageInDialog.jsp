	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
	<link href="${staticPath}/css/assignments/assignments.css" rel="stylesheet" type="text/css" />
	<title>Assignment Management</title>



	<div id="floatContent"  style="background-color: transparent" >
		<div id="mainContentDiv">		
				<div id="assignmentDialogTabs">
					<ul id="Tabs">
						<li id="listAssignmentDetailsViewTabInDialog" ><a href="#assignmentDetalsTabInDialog">Assignment Details</a>  </li>
						<li id="listACRFViewTabInDialog" style="display: none;">
							<a href="#acrfDocumentTabInDialog" >ACRF</a>
							<span class="ui-icon ui-icon-close" role="presentation" title="Close ACRF" onclick="closeTabInDialog('listACRFViewTabInDialog', 'assignmentDetalsTabInDialog')">Close ACRF</span>
						</li>
					</ul>
					
					<div id="assignmentDetalsTabInDialog" style="clear: both;">	
						<div id="assignmentDetailsDataInDialog">
							<jsp:include page="AssignmentDetails.jsp"></jsp:include>
						</div>			
					</div>		
					
				<div id="acrfDocumentTabInDialog" style="clear: both;">				

				</div>						
			</div> 
		</div>	

	</div>

	<script src="${staticPath}/js/assignments/assignmentsInDialog.js?v=${buildDate}"></script>
