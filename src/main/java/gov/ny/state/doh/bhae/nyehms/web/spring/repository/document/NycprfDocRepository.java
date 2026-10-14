package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycPrfDocData;

/**
 * Spring Data JPA repository for NYC PRF documents (replaces NycPRFDocumentDAO.getNycPRFDoc).
 */
public interface NycprfDocRepository extends Repository<StdNycprfDoc, Integer> {

	/**
	 * An NYC PRF document for the document screen: the standardized form, the person address as
	 * received, and the STATENO it is currently matched to (if any). Partners are not included.
	 *
	 * @param documentId document id of the NYC PRF form
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.NycPrfDocData(stdNycprfDoc,
					rawNycprfDoc.address, personDocAssociation.stateno, personDocAssociation.discardStatus,
					format(stdNycprfDoc.dob as 'MM/dd/yyyy'), format(stdNycprfDoc.fieldProcDate as 'MM/dd/yyyy'))
			from RawNycprfDoc rawNycprfDoc
			join StdNycprfDoc stdNycprfDoc
			on stdNycprfDoc.documentId = rawNycprfDoc.documentId
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = rawNycprfDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where rawNycprfDoc.documentId = :documentId
			""")
	NycPrfDocData findDocument(int documentId);
}
