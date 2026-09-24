<html>
<head>
	<title>Reports</title>
	<%@ include file="../common/common_includes.jsp" %> 
	<%@ include file="../common/commonStylesIncludes.html" %> 
	<%@ include file="../common/MainMenuIncludes.jsp" %> 

	<link href="${staticPath}/css/common/datePicker/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" media="screen" href="${staticPath}/css/common/datePicker/bootstrap-datetimepicker.css">
	<script type="text/javascript" src="http://example.com/jquery-1.3.1.js"></script>
	<script type="text/javascript">
	function PrintElem(elem)
    {
        Popup($(elem).html());
    }

    function Popup(data) 
    {
        var WindowObject = window.open('', 'ReportsPrintWindow', 'width=1100,height=800,toolbars=no,scrollbars=yes,status=no,resizable=yes');
        WindowObject.document.write('<link rel="stylesheet" type="text/css" href="${staticPath}/css/reports/MedicalProviderFeedback.css"  media=\"print\"/>');
        WindowObject.document.write('</head><body >');
        WindowObject.document.write(data);
        WindowObject.document.write('</body></html>');
        WindowObject.print();
        WindowObject.close();

        return true;
    }

	</script>  

	<link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/jquerycombobox.css" rel="stylesheet" type="text/css" />
</head>



<body bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

		<div id="floatContent"  style="height: 100%;">
			<%@ include file="../common/Menu.jsp" %>
			<%@ include file="../reports/reportSearchTabDetail.jsp"%>
		</div>
	   
	    <%@ include file="../common/commonScriptsIncludes.html" %>

	    <script type="text/javascript" src="${staticPath}/js/common/jqBarGraph.1.1.js"></script>
	   

		<script src="${staticPath}/js/main.js"></script>
		<script src="${staticPath}/js/jquery/jquery.collapsible.js"></script>
		<script src="${staticPath}/js/jquery/jquerycombobox.js"></script>
		<script src="${staticPath}/js/reports/reports.js?v=${buildDate}"></script>
		<script type="text/javascript">
		  $(function() {
		    $('.effectiveDateTimePicker').datetimepicker({
		      language: 'en',
		      pickTime: false
		    });
		  });
		  $(document).ready(function() {
		  	var loadPageManageUserWithDefaultWorkerId = '<c:out value="${param.workerId}" />';
		  	if(loadPageManageUserWithDefaultWorkerId){
		  		manageUser(loadPageManageUserWithDefaultWorkerId);
		  	}
		  });
		</script> 
		<script src="${staticPath}/js/common/Validator.js?v=${buildDate}"></script>
		<script src="${staticPath}/js/common/jquery.maskedinput.js"></script>
		<script type="text/javascript" src="${staticPath}/js/common/datePicker/bootstrap.js"></script>
		<script type="text/javascript" src="${staticPath}/js/common/datePicker/bootstrap-datetimepicker.js"></script>
</body>
</html>