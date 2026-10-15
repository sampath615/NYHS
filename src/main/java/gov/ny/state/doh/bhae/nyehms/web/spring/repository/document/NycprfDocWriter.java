package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.Map;

/**
 * Writes of NYC PRF documents; part of {@link NycprfDocRepository} (implemented by
 * {@link NycprfDocWriterImpl}).
 */
public interface NycprfDocWriter {

	/**
	 * Inserts the standardized form (STD_NYCPRF_DOC) the import built from the received record
	 * (replaces NycPRFDocumentDAO.writeStdDocData).
	 *
	 * @param stdDocData value per column name; a column without a value is inserted as null
	 * @return the number of rows inserted
	 */
	int insertStdDocument(Map<Object, Object> stdDocData);
}
