package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdStdOldDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.StdOldDocData;

/**
 * Spring Data JPA repository for the STD old document (replaces LabDocumentDAO.getStdOldDoc).
 */
public interface StdOldDocRepository extends JpaRepository<StdStdOldDoc, Integer> {

	/**
	 * An old STD (sexually transmitted disease) report for the STD old document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.StdOldDocData(stdStdOldDoc,
					coalesce(stdStdOldDoc.stdSrcId, 0),
					coalesce(stdStdOldDoc.age, 0),
					coalesce(stdStdOldDoc.raceIndian, false),
					coalesce(stdStdOldDoc.raceBlack, false),
					coalesce(stdStdOldDoc.raceAsian, false),
					coalesce(stdStdOldDoc.racePacific, false),
					coalesce(stdStdOldDoc.raceWhite, false),
					coalesce(stdStdOldDoc.raceMultiple, false),
					coalesce(stdStdOldDoc.raceOther, false),
					coalesce(stdStdOldDoc.raceUnknown, false),
					personDocAssociation.stateno,
					personDocAssociation.discardStatus,
					rawStdOldDoc.personAddress,
					rawStdOldDoc.provAddress)
			from RawStdOldDoc rawStdOldDoc
			join StdStdOldDoc stdStdOldDoc
			on stdStdOldDoc.documentId = rawStdOldDoc.documentId
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = rawStdOldDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where rawStdOldDoc.documentId = :documentId
			""")
	StdOldDocData findDocument(int documentId);
}
