<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

			<form:form modelAttribute="userDetailsVO" id ="userDetailsDisplay">
			
			<div>
				<form:hidden path="workerId"/>
					<div style="float:left; width:99%; margin: 10px 10px 10px 10px;">
						
						<div style="float:left; width:20%">
							<label class="standardInputTitle">Last Name</label>
							<form:input path="lastName"  class="mL5 standardInputSmallText readonly" title="Last Name"/>
						</div>
						
						<div style="float:left; width:18%">
							<label class="standardInputTitle">First Name</label>
							<form:input path="firstName"  class="mL5 standardInputSmallText readonly" title="First Name"/>
						</div>
						
						<div style="float:left; width:30%">
							<label class="standardInputTitle">Bureau or LHD Name</label>
							<form:input path="bureauName"  class="mL5 standardInputText  readonly" title="Bureau or LHD Name"/>
						</div>
						
						<div style="float:left; width:30%">
							<label class="standardInputTitle">E-mail</label>
							<form:input path="emailAddress"  class="mL5 standardInputText  readonly" title="E-mail"/>
						</div>
											
					</div>
					
					<div style="float:left; width:99%; margin: 10px 10px 10px 10px;">
						
						<div style="float:left; width:20%">
							<label class="standardInputTitle">C&S Trng Date</label>
							<form:input path="lastConfidentialityAndSecurityTrainingDate"  class="mL5 standardInputSmallText readonly" title="C&S Training Date"/>
						</div>
						
						<div style="float:left; width:18%">
							<label class="standardInputTitle">C&S Trainer</label>
							<form:input path="cnfSecTrngBy"  class="mL5 standardInputSmallText readonly" title="C&S Trainer"/>
						</div>
						
						<div style="float:left; width:30%">
							<label class="standardInputTitle">C&S Att Rec </label>
							<form:input path="cnfSecTrngAtstn"  class="mL5 standardInputSmallText  readonly" title="C&S Attestation Received"/>
						</div>
						
						<div style="float:left; width:30%">
							<label class="standardInputTitle">NYEHMS User Type</label>
							<form:input path="userType"  class="mL5 standardInputSmallText  readonly" title="NYEHMS User Type"/>
						</div>
											
					</div>
					
					
					<div style="float:left; width:99%; margin: 10px 10px 10px 10px;">
						
						<div style="float:left; width:20%">
							<label class="standardInputTitle">Tele Trng Date</label>
							<form:input path="telecommuteTrainingDate"  class="mL5 standardInputSmallText readonly" title="Tele Trng Date"/>
						</div>
						
						<div style="float:left; width:18%">
							<label class="standardInputTitle">Tele Trainer</label>
							<form:input path="tlcmmtTrngBy"  class="mL5 standardInputSmallText readonly" title="Tele Trainer"/>
						</div>
						
						<div style="float:left; width:30%">
							<label class="standardInputTitle">Tele Att Rec</label>
							<form:input path="tlcmmtTrngAtstn"  class="mL5 standardInputSmallText  readonly" title="Tele Att Rec"/>
						</div>
						
						<div style="float:left; width:30%;vertical-align: top">
							<label class="standardInputTitle">VPN/Remote<br> Access</label>
							<form:input path="vpnAccess" class="mL5 standardInputSmallText readonly" style="vertical-align: top" title="VPN/Remote Access"/>
						</div>
											
					</div>

					<div style="float:left; width:99%; margin: 10px 10px 10px 10px;">
						
						<div style="float:left; width:20%">
							<label class="standardInputTitle">HCS User Id</label>
							<form:input path="legacyUid"  class="mL5 standardInputSmallText readonly" title="HCS User Id"/>
						</div>
						
						<div style="float:left; width:18%">
							<label class="standardInputTitle">Tele</label>
							<form:input path="telecommute"  class="mL5 standardInputSmallText readonly" title="Telecommute"/>
						</div>
						
						<div style="float:left; width:30%">
							<label class="standardInputTitle">PC accesss at loc</label>
							<form:input path="pcAccessAtLocation"  class="mL5 standardInputSmallText  readonly" title="PC accesss at loc"/>
						</div>
						
						<div style="float:left; width:30%;vertical-align: top">
							<label class="standardInputTitle">Remote Terminal Access</label>
							<form:input path="remoteTerminalAccess" class="mL5 standardInputSmallText readonly" style="vertical-align: top" title="Remote Terminal Access"/>
						</div>
											
					</div>
										
					<div style="float:left; width:99%; margin: 10px 10px 10px 10px;">
						<div style="float:left; width:90%">
							<label class="standardInputTitle" style="vertical-align: top" >Comments </label>
							   <form:textarea id="userComments" path="userComments" maxlength="4000" cols="200" rows="12" 
							   		style="width:80%; height:200px; border:1px solid gray;" class="mL20 roundedBorder"  readonly = "true" title="User Comments"/>
						</div>
					</div>					
					
			</div>
			

			
<%--  			<div>
					<div style="float:left; width:30%">
							<label class="standardInputTitle" style="vertical-align: top;">Comments</label>
							<form:textarea path="recruitAssessComments" id="recruitAssessComments"
								title="Comments" maxlength="4000" cols="200" rows="3" style="width:74%; height:50px; border:1px solid gray;" class="mL2 roundedBorder"></form:textarea>									 
						</div>				
			</div>	 --%>

			</form:form>