package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.List;
import java.util.Map;


public interface DocumentEditLabsDAO {	
	
	public int insertRawEditedTbDocument(Map<Object, Object> rawDocData);
	
	public int insertStdEditedTbDocument(Map<Object, Object> stdDocData);

	public int insertRawEditedStdDocument(Map<Object, Object> rawDocData);

	public int insertRawEditedAirsDocument(Map<Object, Object> rawDocData);

	public int insertStdEditedStdDocument(Map<Object, Object> stdDocData);

	public int insertStdEditedAirsDocument(Map<Object, Object> stdDocData);

	public int insertRawEditedNycIJRDocument(Map<Object, Object> rawDocData);

	public int insertStdEditedNycIJRDocument(Map<Object, Object> stdDocData);

	public int insertRawEditedNDIDocument(Map<Object, Object> rawDocData);

	public int insertRawEditedSSDMDocument(Map<Object, Object> rawDocData);

	public int insertStdEditedNDIDocument(Map<Object, Object> stdDocData);

	public int insertStdEditedSSDMDocument(Map<Object, Object> stdDocData);

	public int insertRawEditedInsVitalDocument(Map<Object, Object> rawDocData);

	public int insertStdEditedInsVitalDocument(Map<Object, Object> stdDocData);

	public int insertRawEditedCasaDocument(Map<Object, Object> rawDocData);

	public int insertStdEditedCasaDocument(Map<Object, Object> stdDocData);

	public int insertRawEditedEprfOOCDocument(Map<Object, Object> rawDocData);

	public int insertStdEditedEprfOOCDocument(Map<Object, Object> stdDocData);

	public int insertRawEditedNycLabDocument(Map<Object, Object> rawDocData);

	public int insertStdEditedNycLabDocument(Map<Object, Object> stdDocData);

	public int insertRawEditedNycEharsDocument(Map<Object, Object> rawDocData);

	public int insertStdEditedNycEharsDocument(Map<Object, Object> stdDocData);
	
	
	
	

	public int updatePersonDocAssociationData(
			Map<String, Object> inputParameterMap);

	public int updateTriggeringDocumentIdInAssignments(
			Map<String, Object> inputParameterMap);

	public List<Integer> getAssignmentsCreatedForDocument(int documentId);

	public int addEditedAssignmentActivity(
			Map<Object, Object> inputOutputParameterMap);

	public int insertRawEditedHIVLabDocument(Map<Object, Object> rawDocData);

	public int insertStdEditedHivLabDocument(Map<Object, Object> stdDocData);
	
	public int insertRawEditedClusterDocument(Map<Object, Object> rawDocData);

	public int insertStdEditedClusterDocument(Map<Object, Object> stdDocData);

	public int insertRawEditedOldCasesDocument(Map<Object, Object> rawDocData);

	public int insertStdEditedOldCasesDocument(Map<Object, Object> stdDocData);
	
	public int insertEditedTrkAliasDocument(Map<Object, Object> stdDocData);
	
}
