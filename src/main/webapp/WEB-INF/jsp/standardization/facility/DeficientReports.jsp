<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%@ include file="../../common/MainMenuIncludes.jsp"%>

<link href="${staticPath}/css/standardization/facility/newpage.css" rel="stylesheet" type="text/css" />

<script src="${staticPath}/js/standardization/facility/newPageGrand.js"></script>
<script>
	setJsVariables("${contextPath}");
</script>

</head>

<body>
	<%@ include file="../../common/Menu.jsp"%>

	<div id="DefRepoContent" style="margin-left: 150px; width: 1024px; clear: both;">
		<ul>
			<li><a id="listdefRepoTabs" href="#defRepoPageTabs">Deficient Reports</a></li>
		</ul>

		<div id="defRepoPageTabs">
			<ul>
				<li><a href="#stdWoGrdDefRepDiv" >Standard Without Grand(<span id="standardWoGrandCount" ></span>) </a></li>
				<li><a href="#grdWoStdDefRepDiv" >Grand Without Standard(<span id="grandWithoutStandardCount" ></span>)</a></li>
				<li><a href="#stdWoRawDefRepDiv" >Standard Without Raw(<span id="standardWithoutRawCount"></span>)</a></li>
			</ul>

			<div id="stdWoGrdDefRepDiv">
				<div id="standardWoGrand">
					 <table id="newPageStdOrphanTable"></table>
					<div id="newPageStdOrphanPagerDiv"></div>
				</div>
				<div style="clear: both;"></div>
				<br />

				<div id="accordion" style="width: 950px; clear: both;">
					<h3>Search For Master Facility</h3>
					<div style="height: 150px;">
						<div id="errorDiv" class="errorblock" style="display: none;">
							All search fields are blank.</div>
						<div>
							<%@ include file="search/GrandSearchCriteria.jsp"%>
						</div>
					</div>
				</div>
				<div>&nbsp;&nbsp;</div>
				
				<div id="searchResults">
					<table id="newPageGrandTable"></table>
					<div id="newPageGrandPagerDiv"></div>
					
					<div>&nbsp;&nbsp;</div>
					<div class="button-row" style="margin-bottom: 12px;">
				         <a class="a_demo_search" style ="color:#FFFFFF; margin-left: 5px;" onclick="standardize();"> Standardize </a>
				         <a class="a_demo_search" style ="color:#FFFFFF; margin-left: 5px;" onclick="newGrdFacility();"> New Master Facility </a>
				    </div> 
				</div>
			    
			</div>
			
			<div id="grdWoStdDefRepDiv">
			<div id="errorRetiregrdWoStdDiv" class="errorblock" style="display: none;">
						Records are not selected.Please Select
			</div>
				<div id="grandWithoutStandard">
					<table id="deficientGrdWoStdTable"></table>
					<div id="deficientGrdWoStdDiv"></div>
				</div>
				<div>&nbsp;&nbsp;</div>
				<div class="button-row" style="margin-bottom: 10px;">
			         <a class="a_demo_search" style ="color:#FFFFFF; margin-left: 5px;" onclick="grandWoStdRetireFun();"> Retire </a>
			    </div> 
			</div>
			<div id="stdWoRawDefRepDiv">
			<div id="errorRetirestdWoRawDiv" class="errorblock" style="display: none;">
						Records are not selected.Please Select
			</div>
				<div id="standardWithoutRaw">
					<table id="deficientStdWoRawTable"></table>
					<div id="deficientStdWoRawDiv"></div>
				</div>
				<div>&nbsp;&nbsp;</div>
				<div class="button-row" style="margin-bottom: 10px;">
					<a class="a_demo_search" style="color: #FFFFFF; margin-left: 5px;" onclick="stdWoRawRetireFun();"> Retire </a>
				</div>
			</div>
			<div id="newPageGrdDialog">
				   <jsp:include page="add/NewPageGrandDialog.jsp"></jsp:include>
			</div>
			<div id="newPageGrandStdDialog">
				   <jsp:include page="confirm/NewPageGrdStandardizeDialog.jsp"></jsp:include>
			</div>
			
			<!-- TODO -->
			<div id="deficiencyReportDialog">
				   <div id="retireGrdWoStdReportDialog" title="Deficiency Reports Retiring">
						<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>These items will be permanently Retired and cannot be recovered. Are you sure?</p>
					        <div>&nbsp;&nbsp;</div>
						    <div class="button-row">
								<a class="a_demo_search" style ="color:#FFFFFF; margin-left: 5px;" onclick="submitRetireGrdWoStd();"> Submit </a>
								<a class="a_demo_search" style ="color:#FFFFFF; margin-left: 5px;" onclick="retireGrdWoStdCancel();"> Cancel </a>
							</div>
							<div>&nbsp;&nbsp;</div>
					</div>
			</div>
			
			<!-- TODO -->
			<div id="deficiencyReportDialog">
				  <div id="retireStdWoRawReportDialog" title="Deficiency Reports Retiring">
					<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>These items will be permanently Retired and cannot be recovered. Are you sure?</p>
				        <div>&nbsp;&nbsp;</div>
					    <div class="button-row">
							<a class="a_demo_search" style ="color:#FFFFFF; margin-left: 5px;" onclick="submitRetireStdWoRaw();"> Submit </a>
							<a class="a_demo_search" style ="color:#FFFFFF; margin-left: 5px;" onclick="retireStdWoRawCancel();"> Cancel </a>
						</div>
						<div>&nbsp;&nbsp;</div>
				</div>
			</div>
			
			<form id="deficientPageReload" action="" method="post">
			</form>
			<input type="hidden" id="deficientReportType" value="${param.id}">
		</div>
	</div>
</body>