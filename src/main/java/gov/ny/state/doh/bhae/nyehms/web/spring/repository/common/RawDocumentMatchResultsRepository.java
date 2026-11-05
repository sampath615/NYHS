package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RawDocumentMatchResults;

/** The quality-stage match results of documents (table RAW_DOCUMENT_MATCH_RESULTS); read through counts only. */
public interface RawDocumentMatchResultsRepository extends JpaRepository<RawDocumentMatchResults, Long> {

	/** The number of 'M' / 'C' match results of the quality-stage parent documents of a document. */
	@Query("""
			select count(*) from RawDocumentMatchResults r
			where r.documentId in (select p.qsParentDocId from RawDocumentMatchResults p where p.documentId = ?1)
			and r.matchCd in ('M', 'C')""")
	long countParentMatches(Long documentId);
}
