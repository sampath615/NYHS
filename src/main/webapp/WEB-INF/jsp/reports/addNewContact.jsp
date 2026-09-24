<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<div id="addContact" title="Add New Contact" style="display:none;width:80%;height:400px">
		<div class="standardInputDiv">
			<div class="addContactLabel">
				<font class="labReportTitle">Contact Type:</font>
			</div>
			<div class="">
				<select name="contactType" id="contactType" class="required standardInputSmallText" title="Contact type">
					<option value="">-- Select --</option>
				    <c:forEach items="${pfiContactTypeDetails}" var="pfiContact">
 				      <option value="${pfiContact.key}">${pfiContact.value}</option>
				    </c:forEach>
				</select>
			</div>
			<div class="standardInputDiv">
					<div class="addContactLabel">
						<label class="labReportTitle">Main Contact:</label>
					</div>
					<div class="">
						<select name="mainContact" id="mainContact" title="Main Contact" class="standardInputSmallText required">
								<option value="" label="--- Select ---" />
								<option value="Y" label="Yes" />
								<option value="N" label="No" />									
						</select>
					</div>
			</div>			
			<div class="standardInputDiv">
			<div class="addContactLabel">
				<font class="labReportTitle">Name:</font>
			</div>
			<div class="">
				<input maxlength="100" class="standardInputSmallText required" type="text" id="name" name="name" title="Contact name"/>
			</div>
		</div>
		<div class="standardInputDiv">
			<div class="addContactLabel">
				<font class="labReportTitle">Email:</font>
			</div>
			<div class="">
				<input id="email" maxlength="" class="standardInputSmallText" type="text" name="email" title="Contact email"/>
			</div>
		</div>
		<div class="standardInputDiv">
			<div class="addContactLabel">
				<font class="labReportTitle">Phone:</font>
			</div>
			<div class="">
				<input id="phone" title="Phone Nummber" class="standardInputSmallText" type="text" name="phone" maxlength="12"/>
			</div>
		</div>
		<div class="standardInputDiv">
			<div class="addContactLabel">
				<font class="labReportTitle">Phone Ext:</font>
			</div>
			<div class="">
				<input id="phoneExt" title="Phone Nummber Ext" class="standardInputSmallText" type="text" name="phoneExt" maxlength="6"/>
			</div>
		</div>
		<div class="standardInputDiv">
			<div class="addContactCheck">
				<input id="reportCard" class="reportCard" type="checkbox" name ="reportCard" value="Y"  />Compliance Report
			</div>
			<div class="addContactCheck">
				<input id="counts" class="Counts" type="checkbox" name="counts" value="Y"  />Counts
			</div>
			<div class="addContactCheck">
				<input id="qa" class="QA" type="checkbox" name="qa" value="Y"  />QA
			</div>
		</div>
		<div class="standardInputDiv">
			<div class="addContactLabel">
				<font class="labReportTitle">Notes:</font>
			</div>
			<div class="">
				<!-- <input id="notes" title="Notes" class="standardInputSmallText" type="text" name="notes" maxlength="4000"/> -->
				<textarea id="notes" name="notes" class="standardInputMedTextLarge" rows="10" cols="100" maxLength="4000"></textarea>		
			</div>
		</div>	
		
		</div>
	</div>
	