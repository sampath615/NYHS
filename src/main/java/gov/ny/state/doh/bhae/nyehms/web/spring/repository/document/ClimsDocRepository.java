package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdClimsDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.ClimsDocData;

/**
 * Spring Data JPA repository for CLIMS documents.
 */
public interface ClimsDocRepository extends JpaRepository<StdClimsDoc, Integer> {

	/**
	 * a CLIMS lab report for the CLIMS document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.ClimsDocData(stdClimsDoc,
					personDocAssociation.stateno,
					personDocAssociation.discardStatus,
					rawClimsDoc.facaddr1,
					rawClimsDoc.facaddr2,
					coalesce(stdClimsDoc.tma, stdClimsDoc.hiv1panther))
			from RawClimsDoc rawClimsDoc
			join StdClimsDoc stdClimsDoc
			on stdClimsDoc.documentId = rawClimsDoc.documentId
			join DocumentLocator documentLocator
			on documentLocator.documentId = rawClimsDoc.documentId
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = rawClimsDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where rawClimsDoc.documentId = :documentId
			""")
	ClimsDocData findDocument(int documentId);
}
