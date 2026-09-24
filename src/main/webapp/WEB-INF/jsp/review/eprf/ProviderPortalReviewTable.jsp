<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
<c:when test="${docList != null && docList != '[]'}">
				<table id="documentReviewTable">
						<thead>
							<tr>
								<th style="width:121px !important">Med Record Num</th>
								<th style="width:120px">fname</th>
								<th style="width:60px">mname</th>
								<th style="width:149px">lname</th>
								<th style="width:53px">suffix</th>
								<th style="width:80px">DOB</th>
								<th style="width:80px">ssn</th>
								<th style="width:86px">cin</th>
								<th style="width:70px">din</th>
								<th style="width:90px">nysid</th>
								<th style="width:77px">birth_sex</th>
								<th style="width:65px">gender</th>
								<th style="width:124px" class="completeTextWithPadding">race_cd</th>
								<th style="width:82px">ethniccd</th>
								<th style="width:200px">address</th>
								<th style="width:112px">city</th>
								<th style="width:46px">state</th>
								<th style="width:74px">zipcode</th>
								<th style="width:93px">phone</th>
								<th style="width:154px">provname</th>
								<th style="width:90px">prov_npi</th>
								<th style="width:91px">prov_license</th>
								<th style="width:171px">prov_addr</th>
								<th  style="width:108px">prov_city</th>
								<th  style="width:80px">prov_state</th>
								<th  style="width:81px">prov_zip</th>
								<th  style="width:103px">prov_Phone</th>
								<th  style="width:127px">prov_last_visit_dt</th>
								<th  style="width:148px">last_viral_load_date</th>
								<th  style="width:142px">last_viral_load_result</th>
								<th  style="width:130px">last_cd4_date</th>
								<th  style="width:158px">last_cd4_result_count</th>
								<th  style="width:175px">last_cd4_result_percent</th>
								<th  style="width:188px">facility_name</th>
								<th  style="width:90px">facility_npi</th>
								<th  style="width:201px">facility_addr</th>
								<th  style="width:108px">facility_city</th>
								<th  style="width:119px">facility_state</th>
								<th  style="width:101px">facility_zip</th>
								<th  style="width:133px">facility_phone</th>
								<th  style="width:124px">Pref Name</th>
								
							</tr>
						</thead>
						<tbody>	
							<c:forEach items="${docList}" var="document">
								<tr>
									<td>${document.MEDRECNO}</td>
									<td>${document.FNAME}</td>
									<td>${document.MNAME}</td>
									<td>${document.LNAME}</td>
									<td>${document.SUFFIX}</td>
									<td>${document.DOB}</td>
									<td>${document.SSN}</td>
									<td>${document.CIN}</td>
									<td>${document.DIN}</td>
									<td>${document.NYSID}</td>
									<td>${document.BIRTH_SEX}</td>
									<td>${document.GENDER}</td>
									<td><span style="white-space: normal; height : auto">${document.RACE_CD}</span></td>
									<td>${document.ETHNICCD}</td>
									<td>${document.ADDRESS}</td>
									<td>${document.CITY}</td>
									<td>${document.STATE}</td>
									<td>${document.ZIPCODE}</td>
									<td>${document.TELEPHONE}</td>
									<td>${document.PROV_NAME}</td>
									<td>${document.PROV_NPI}</td>
									<td>${document.PROV_LICENSE}</td>
									<td>${document.PROV_ADDR}</td>
									<td>${document.PROV_CITY}</td>
									<td>${document.PROV_STATE}</td>
									<td>${document.PROV_ZIP}</td>
									<td>${document.PROV_PHONE}</td>
									<td>${document.PROV_LAST_VISIT_DATE}</td>
									<td>${document.LAST_VIRAL_LOAD_DATE}</td>
									<td>${document.LAST_VIRAL_LOAD_RESULT}</td>
									<td>${document.LAST_CD4_DATE}</td>
									<td>${document.LAST_CD4_RESULT_COUNT}</td>
									<td>${document.LAST_CD4_RESULT_PERCENT}</td>
									<td>${document.FACILITY_NAME}</td>
									<td>${document.FACILITY_NPI}</td>
									<td>${document.FACILITY_ADDR}</td>
									<td>${document.FACILITY_CITY}</td>
									<td>${document.FACILITY_STATE}</td>
									<td>${document.FACILITY_ZIP}</td>
									<td>${document.FACILITY_PHONE}</td>
									<td>${document.PREF_NAME}</td>
								</tr>
							
							</c:forEach>
						</tbody>
						</table>
</c:when>
<c:otherwise>
<span class="message">
				No documents to review
		</span>
</c:otherwise>
		
</c:choose>