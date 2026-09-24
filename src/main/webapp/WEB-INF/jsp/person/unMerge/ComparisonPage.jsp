<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="badlyMergedStatenoDetailsDiv">
	<div id="badylyMergedStatenoDetailsGrid">
	      <div style="padding-top:0.4em"></div>
                <div class="button-row floatRight">
          	      <a id="generateStateno" class="actionButtons a_disable fadedAddButton" onClick="createNewStatenoAndAssociateDocs(this)" style="color: #FFFFFF; margin-left: 5px;"> Generate STATENO </a>
          	</div> 
			<ul id="badlyMergedStatenoTabLinks" class="clearBoth">
				<li><a href="#badlyMergedStatenoPersonViewTab"
					id="badlyMergedStatenoPersonViewTabLink">Recent Demo View</a></li>
				<li><a href="#badlyMergedStatenoDocumentDetailsTab"
					id="badlyMergedStatenoDocumentDetailsTabLink">Document Timeline</a></li>
			</ul>
         
		<div id="badlyMergedStatenoPersonViewTab">
			<div id="personView_badlyMerged" class="personViewData"></div>
		     </div>
		     <div id="badlyMergedStatenoDocumentDetailsTab">
			   <div id="documentTimeLine_badlyMerged" class="documentDetails">
				<div id="documentDetailsDiv_badlyMerged">
					<table id="documentDetailsTable_badlyMerged"></table>
				</div>
			   </div>
		    </div>
    </div>
</div>
<div id="pageViewBorder">
 <div id="moveBtnForUnmerge" style=" padding-left:38px; padding-top:255px">
<span class="pL20 moveBtn">
	
<a class="actionButtons a_disable fadedgoButton" onclick="moveDocFunction(this)" id="move_btn" style="padding-right:20px! important"> </a>
</span>
</div>
</div>
<div id="personViewSearchDiv">
	<div id="personViewSearch_badlyMergedPage" style="width:1000px">
		<jsp:include page="../../search/personSearchTabDetail.jsp"></jsp:include>
	</div>
</div>




<!-- 

<c:forEach var="personObj" varStatus="varStatus" items="${personObjects}">
	<c:set var="personViewNumber" value="${varStatus.index+1}" />
	<c:set var="personViewObj" value="${personObj.personViewVO}" scope="request" />
	<c:set var="personRisksObj" value="${personObj.personRisksVO}" scope="request" />
	<c:set var="statenoHistoryObj" value="${personObj.statenoHistory}" scope="request" />
	<c:if test="${personViewObj != null}">
		<div id="personView<c:out value="${personViewNumber}" />" class="personViewData">
			<div id='floatContent<c:out value="${personViewNumber}" />'  style="background-color: transparent;width:1210px"  >
					
			</div>
		</div>
	</c:if>
</c:forEach> -->