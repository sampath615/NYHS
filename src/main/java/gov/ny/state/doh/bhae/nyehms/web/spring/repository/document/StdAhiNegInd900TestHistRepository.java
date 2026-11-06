package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdAhiNegInd900TestHist;

/** The negative / indeterminate test history of the AHI forms (table STD_AHI_NEG_IND_900_TEST_HIST). */
public interface StdAhiNegInd900TestHistRepository extends JpaRepository<StdAhiNegInd900TestHist, Long> {

	/** A test (a projection: the table has no key). */
	record NegInd900Test(Long ahiDocumentId, String lastNegIndDate, String negIndResult, String negIndTestTypeCode,
			String negIndTestTypeOther, String negIndLabReport, String negIndLastTestProvName, String negIndProvNameUnknown) {
	}

	/** The tests of a form, in database order. */
	List<NegInd900Test> findByAhiDocumentId(Long ahiDocumentId);
}
