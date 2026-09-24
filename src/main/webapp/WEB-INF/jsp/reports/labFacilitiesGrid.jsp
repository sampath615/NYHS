<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table id="clinicalaboratoriesTable" class="table table-responsive table-hover">
     <thead>
         <tr>
         	 <th>Expand</th>
             <th>CLIA</th>
             <th>PFI</th>
             <th>Lab Name</th>
             <th>Address</th>
             <th>City</th>
             <th>State</th>
             <th>Zip Code</th>
         </tr>
     </thead>
     <tbody>
  <c:forEach var="clinicalLaboratoryVO" items="${labFacilities}" varStatus="counter">
  		<c:set var="clia_current" value="${clinicalLaboratoryVO.clia}" />
	    <tr >
	    	   <td>
	    	   		<c:if test="${clinicalLaboratoryVO.labFacilities != null}">
	    	   			<a class="expand" href="javascript:void(0)" title="expand"><i class="glyphicon glyphicon-minus"></i></a>
	    	   		</c:if> 
	    	   	</td>
               <td>${clia_current}</td>
               <td></td>
               <td></td>
               <td></td>
               <td></td>
               <td></td>
               <td></td>
           </tr>
           <c:forEach var="labFacility" items="${clinicalLaboratoryVO.labFacilities}" varStatus="counter">
             	<tr >
             	<td></td>
             	<td></td>
                <td>${labFacility.pfi}</td>
                <td>${labFacility.labName}</td>
                <td>${labFacility.address}</td>
                <td>${labFacility.city}</td>
                <td>${labFacility.state}</td>
                <td>${labFacility.zipCode}</td>
                
            </tr>
        </c:forEach>
	            
	</c:forEach>
 
   	</tbody>
</table>