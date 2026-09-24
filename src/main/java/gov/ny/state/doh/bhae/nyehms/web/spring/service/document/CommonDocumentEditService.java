package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;


/*
 * ACRF JSP is treated as 3 parts for all CRUD operations. 
 * One is ACRF Doc table itself 2nd is ACRF Labs table and 3rd ACRF CD4 Table.
 * So we define CRUD (only select, insert, update)) operation separately for three tables even though 
 * it is single operation from user view.
 * This is done to modularize code so that each table CRUD can be handles separately in future.
 */	

public interface CommonDocumentEditService {
	
	
	public void storeEditRawAndStdDocument(Map<String, String> inputData, int documentId, String documentType, String SOFT_ERROR_ONLY, BindingResult bindingResult, UserObject userObject);
	
	public void writeRawEditedDocData(String documentType, 
			Map<Object, Object> rawDocData, int oldDocumentId, UserObject userObject);
	
	public void writeStdEditedDocData(String documentType,
			Map<Object, Object> stdDocData, int oldDocumentId);
	

	public void updatePersonDocAssociationData(
			Map<String, Object> inputParameterMap);

	public void updateTriggeringDocumentIdInAssignments(List<Integer> assignmentIds, int newDocumentId);

	public List<Integer> getAssignmentsCreatedForDocument(int documentId);

	public void addEditedAssignmentActivity(List<Integer> assignmentIds, UserObject userObject);

}
