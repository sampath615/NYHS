			<div style="font-weight:bold;margin:5px; height:150px">
					<input type="hidden" id="clusterStateno" value="" />
					<div>
					  	<span class="standardInputTitle"> Cluster Id, Type, Date </span>
						<span>
								<select id="notAssocClusterDetails" class="standardInputMedText mR50 required" onChange="clearIdentification();">
								</select>
						</span>
					</div>
					
					<div class="clearBoth mT10">
						<span class="standardInputTitle"> Identification Method</span>
						<span>
							<select id="identificationMethod" name="identificationMethod" class="required standardinputMedText" title=" Identification Method" onChange="getDemoDocumentIds(this)" >
								<option value=""> -- Select --</option>
								<c:forEach items="${clusterIdentificationMethod}" var="method">
									<option value="${method.IDENTIFICATION_METHOD_CODE}">${method.IDENTIFICATION_METHOD_DESC}</option>
								</c:forEach>
							</select>	
						</span>
					</div>
					<div class="clearBoth mT10">
					
					<span class="standardInputTitle"> Identification Date</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span><input id="identificationDate" name="identificationDate"
							class="dPicker standardInputMedText required" dateValidations="completeDate" data-format="MM/dd/yyyy" type="text" title=" Identification Date"/>
						</span>
					<br><span class="standardInputTitle"> (mm/dd/yyyy)</span>
					</div>
					
					<div class="ui-widget">
						<span class="standardInputTitle"> Demographic Doc Id</span>&nbsp;&nbsp;
						<span>
							<select id="autoCompleteCombobox" name="autoCompleteCombobox" class="required standardinputMedText" title="Demographic document Id">
								<option value=""> -- Select --</option>
							</select>	
						</span>
					</div>					
			</div>
			<div class="button-row">
					<a onclick="addStatenoToCluster()" id="messageAction" style="color: #FFFFFF;display:inline-block" class="actionButtons a_demo_search a_demo_save">Add to Cluster</a>
			</div>
