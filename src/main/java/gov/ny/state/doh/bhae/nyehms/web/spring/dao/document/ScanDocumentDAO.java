package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.Map;


public interface ScanDocumentDAO {

	Map<String, String> getScanDocInfo(String docId);	
	

	public void createEntryInPersonDocAssociation(Map<Object, Object> docIdMap);

	public void createEntryInDocumentPerson(Map<Object, Object> docIdMap);

	public void addStdScanEntry(Map<Object, Object> docIdMap);
	
	public String getDocDetails(String documentId);
	public int deleteDocumentInPersonDocAssociation(Map<String, Object> inputParameterMap);
	public int deleteDocumentInDocumentPersonAttribute(Map<String, Object> inputParameterMap);
	public int deleteDocumentInDocumentLocator(Map<String, Object> inputParameterMap);
	public int updateStdScanDocTable(Map<String, Object> inputParameterMap);
	

}
