<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
	#the-canvas {
	  border:1px solid black;
	}
</style>

<div style="padding-left:43%; " class="headingCls scanDocHeaderTitle">
	SCAN DOCUMENT
</div>

<div class="pB30 pT20">
		
					
				


		
		 <div class="clearBoth container pB5 w100" id="scanDocHeadedInfo">
            <div style="float:left; padding-bottom:10px; width: 40%">
	            <div >
	                <span class="parentIdSectionLabel bold mL10">Document Id: </span>
					<span class="parentIdSectionValue ">
						<input type="text" name="SCAN_DOCUMENT_ID" class="standardinputSmallText ml10 readonly" readonly="readonly" value="${param.docId}"/>
					</span>
	                
	            </div>    
            </div>
<!--             <div style="float:left; padding-bottom:10px; width: 30%"> -->
<!--             	<div> -->
<!-- 	                <span class="parentIdSectionLabel bold mL10">File Name: </span> -->
<!-- 					<span class="parentIdSectionValue "> -->
<%-- 						<input type="text" name="SCAN_FILE_NAME" class="standardinputSmallText ml10 readonly" readonly="readonly" value="${scanDocInfo.FILE_NAME}"/> --%>
<!-- 					</span> -->
<!-- 	            </div>     -->
<!--             </div> -->
            <div style="float:left; padding-bottom:10px; width: 30%; text-align: right">
            	        	
	            <div >
	                <span class="mL10 bold">STATENO: </span>
					<span class=" ">
						<input type="text" name="SCAN_FILE_STATENO" class="standardinputSmallText ml10 readonly" readonly="readonly" value="${scanDocInfo.STATENO}"/>
					</span>
	            </div>
            </div>     
   		</div>
		
		 

</div>


<div class="alignCenter marginBottom10" id="showScannedImage">
  <button id="prev">Previous</button>
  <button id="next">Next</button>
  &nbsp; &nbsp;
  <span>Page: <span id="page_num"></span> / <span id="page_count"></span></span>
</div>

<div class="alignCenter" id="canvasImage">
	<canvas id="the-canvas"></canvas>
</div>

<div class="mT50">&nbsp;&nbsp;&nbsp;</div> <!-- dummy div -->	
<div id="button-row" class="button-row documentButtons center">
	
	<c:if test="${userPermissions['153'] != null && scanDocInfo.STATENO != null}">
		<input type="button" id="deleteDocument" class="deleteBtnclick button rounded blue effect-3 displayNone"  value="Delete" onclick="confirmDeleteDocumentFromStateno()" />
	</c:if>
</div>
<script type="text/javascript">

var docId = '${param.docId}';

var documentType = '${param.documentType}';

var url = '';
    
url =  contextPath+'/web/getEncryptedScannedDoc.pdf?docId='+docId+'&documentType='+documentType;

var canvas = document.getElementById('the-canvas'),
ctx = canvas.getContext('2d');

// Loaded via <script> tag, create shortcut to access PDF.js exports.

/**
 * Asynchronously downloads PDF.
 */
 	pdfjsLib.getDocument(url).then(
			function(pdfDoc_) {
				    console.log("here");
				  pdfDoc = pdfDoc_;
				  document.getElementById('page_count').textContent = pdfDoc.numPages;
				
				  // Initial/first page rendering
				  pageNum = 1;
				  
				  renderPage(pageNum);
				  
				  //enableScanAssociateBtns();
				},
				function(error) {
				console.log(error);
				    showTransactionMessage(
						'#associateDocToStatenoTransactionMessage .message',
						"Unable to load the Selected Scanned Document, please contact helpline"
						);
				
						docViewerDialog.dialog("close");
				
				});

    
    
  	
	

document.getElementById('prev').addEventListener('click', onPrevPage);
document.getElementById('next').addEventListener('click', onNextPage);

</script>