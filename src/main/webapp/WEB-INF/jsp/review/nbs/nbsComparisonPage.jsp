<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id=badlyMergedStatenoDetailsDiv>
	<div id="badylyMergedStatenoDetailsGrid">
	      <ul id="nbsDocumentTabLinks" class="clearBoth">
				<li><a href="#nbsDocumentViewTab"
					id="nbsDocumentViewTabLink">Recent Demo View</a></li>
			</ul>
         
		<div id="nbsDocumentViewTab">
			<div id="personView_nbsDocument" class="personViewData"></div>
		     </div>
    </div>
</div>
<div id="personViewSearchDiv">
	<div id="personViewSearch_nbsDocument" style="width:1000px">
		<jsp:include page="../../search/personSearchTabDetail.jsp"></jsp:include>
	</div>
</div>