<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="roundedBorder mL10" style="width:98%; background:#DDDDDD;" >
	<div id="labDataContainer" class="container">
		<div id="hivNonTypeTestsDivContainer" class="container">			
			<div style="float:left; width:99%; padding-bottom:10px; margin-left:5px; margin-right:5px; margin-top:2px;">
				<div style="float:left; width:85%; margin-top:5px" class="abtNTDrpDwn mL20">
					<span class="bold">CLIA / PFI</span> 
					
					<input type="text" name="pfiName" id="pfiName" class="abtNTTest standardInputMedText" maxlength="50" title="CLIA/PFI" style="width:395px"/>
					<a href="#" class="dropdownImage" onClick="return false"></a>
					
				</div>				
			</div>
            <div style="float:left; width:99%; padding-bottom:15px; margin-left:5px; margin-right:5px; margin-top:2px;">
            	<div style="float:left; width:55%; margin-top:5px" class="abtNTDrpDwn mL20">
					<span class="bold">Filter Type</span> <c:out value="${labProcessFilterType}" />
                    <select id="filterType" name="filterType" class="required" title="Filter Type" style="margin-left:10px; width:70%">   
                    		<option value=""> Select </option>                     
                           <c:forEach items="${filterTypeConditions}" var="filterType">
								<option value="${filterType.FILTER_TYPE_CODE}">${filterType.FILTER_TYPE_DESC} </option>
							</c:forEach>
					</select>
				</div>
				<div style="float:left; margin-right:25px">
					<a href='#' class='testFiler' title='Filter Result' onClick='showFilterModalonPopup()'> Test </a>
				</div>
				<div>
                    <span><a href="#" class="addProficiencyCondition" onclick="javascript:addNonTypeHIVDiv()" title="Add Process Condition" >Add PC</a>  </span>
                </div>
            </div>
               
			<div style="float:left; width:99%; padding-bottom:5px; margin-left:5px; margin-right:5px; margin-top:2px;">
				<div class="fLW25 abtNTDrpDwn mL20">
					<span class="bold">FIELD </span>
				</div>
				
                <div class="fLW20">
                	<span class="bold">Operator		</span>
				</div>	
					
                <div class="fLW25 required standardInputSmallText2 quanCP"> 
                	<span class="bold">Value </span>
                </div>	
                                            
                <div class="fLW20"> 
                	<span class="bold">Condition</span>								
				</div>
            </div>

			<div id="hivNonTypeTestsDiv" class="hivNonTypeTestsDiv" style="float:left; width:99%; padding-bottom:5px; margin-left:5px; margin-right:5px; margin-top:2px;" > 
				
				<div  class="fLW25 abtNTDrpDwn mL20">
					<select id="testFilter0_FIELD_NAME" count="0" name="testFilterFieldName" class="abtNTTest" onchange="getResultForTestType('testFilter0_FIELD_NAME', '0', 'hivNonTypeTestsDiv', 'FIELD_NAME');">
                        <option value="FIRST NAME">FIRST NAME</option>
                        <option value="LAST NAME">LAST NAME</option>
                        <option value="MIDDLE NAME">MIDDLE NAME</option>
                        <option value="DOB">DOB</option>
                        <option value="ACCESSION NUM">ACCESSION NUM</option>
                        <option value="OBSERVATION RESULT TEXT">OBSERVATION RESULT TEXT</option>
                        <option value="INTERNAL ID">INTERNAL ID</option>
    					<option value="PATIENT ADDRESS LINE1">PATIENT ADDRESS LINE1</option>
                        <option value="PATIENT ADDRESS LINE2">PATIENT ADDRESS LINE2</option>
                        <option value="PATIENT CITY">PATIENT CITY</option>
                        <option value="PATIENT STATE">PATIENT STATE</option>
                        <option value="FACILITY NAME">FACILITY NAME</option>
                        <option value="FACILITY ADDRESS LINE1">FACILITY ADDRESS LINE1</option>
                        <option value="FACILITY ADDRESS LINE2">FACILITY ADDRESS LINE2</option>
                        <option value="FACILITY CITY">FACILITY CITY</option>
                        <option value="PROVIDER FIRST NAME">PROVIDER FIRST NAME</option>
                        <option value="PROVIDER LAST NAME">PROVIDER LAST NAME</option>
                        <option value="PROVIDER ADDRESS LINE1">PROVIDER ADDRESS LINE1</option>
                        <option value="PROVIDER ADDRESS LINE2">PROVIDER ADDRESS LINE2</option>
                        <option value="PROVIDER CITY">PROVIDER CITY</option>
                        <option value="LOINC">LOINC</option>

					</select>								
				</div>
		
                <div class="fLW20">
					<select id="testFilter0_operator" count="0" name="testFilterOperator" class="abtNTTest" onchange="getResultForTestType('testFilter0_operator', '0', 'hivNonTypeTestsDiv', 'operator');">
						
                        <option value="LIKE">LIKE</option>
                        <option value="STARTWITH">STARTWITH</option>
                        <option value="EQUAL">EQUAL</option>
                        <option value="NOTEQUAL">NOTEQUAL</option>
                        <option value="NOTSTARTWITH">NOTSTARTWITH</option>	
                        <option value="NOTLIKE">NOTLIKE</option>
						
					</select>							
				</div>	
					
                <div class="fLW25">
					
					<input id="testFilter0_value" count="0" name="testFilterValue" value="" class="standardInputSmallText2 quanCP"  maxlength="100" onchange="getResultForTestType('testFilter0_value', '0', 'hivNonTypeTestsDiv', 'value');" onkeyup="capitalize(this.id,this.value)" placeholder="Value" style="width: 90%;"/>						
				</div>	
                                            
                <div class="fLW20">
					<select id="testFilter0_condition"  count="0" name="testFilterCondition" class="abtNTTest required" onchange="getResultForTestType('testFilter0_condition', '0', 'hivNonTypeTestsDiv', 'condition');">
						<option value=""> Select </option>
						<option value="AND">AND</option>
                        <option value="OR">OR</option>
                    </select>							
				</div>	        
				
				<div class="fLW10">
					<span class="mL2" style="float:right; margin-right:10px;"><img onclick="deleteTestDiv(this, 'testFilter0_condition', 'hivNonTypeTestsDiv');" src="${staticPath}/images/icons/Delete-icon.png" title="Click to Delete" style="vertical-align: middle; width: 14px;"/></span>
				</div>
                               
			</div>
			<script>  
                calledFromAddScreen = true;
				acrfRowCnt = acrfRowCnt+1;
                if(editData.length===0) {
		  			var row = '{"PFI":1899,"FILTER_NUMBER":2,"CONDITION_ID":81,"FIELD_NAME":"FIRST NAME","CONDITION":"EQUAL","VALUE":"VIKRAM","OPERATOR":" ","processed":"U","rowNo":0}';
        			var newJsonRow= JSON.parse(row); 

		            newJsonRow.PFI='';
		            newJsonRow.FILTER_NUMBER='';
			        newJsonRow.CONDITION_ID='';
			        newJsonRow.FIELD_NAME = 'FIRST NAME';
			        newJsonRow.CONDITION = 'LIKE';
			        newJsonRow.VALUE = '';
			        newJsonRow.OPERATOR = ' ';
			        newJsonRow.processed = 'N';
			        newJsonRow.rowNo =acrfRowCnt ;
			        newJsonRow.CONDITION_ID ='' ;
       
    				console.log('** new newJsonRow data now '+ JSON.stringify(newJsonRow));
      
           			editData.push(newJsonRow);
   				}
			</script>	
			
		</div>	
	</div>
</div>	
                                    
<div style="clear: both;">
	<br />
</div>     

<div style="clear: both;">
	<br />
</div> 

<div id="button-row" class="button-row" style="margin-bottom: 5%;">
	<a class="a_demo_search a_demo_save" style="color: #FFFFFF; margin-left: 5px;" onclick="addLabProfFilter();"> Save </a> 
    <a class="a_demo_search a_demo_cancel" style="color: #FFFFFF; margin-left: 5px;" onclick="closeAddProficiencyDialog();"> Cancel </a>
</div>