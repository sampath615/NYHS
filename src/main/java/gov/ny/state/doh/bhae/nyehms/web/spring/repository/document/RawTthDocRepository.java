package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawTthDoc;

/** The HIV testing and treatment histories as entered (table RAW_TTH_DOC). */
public interface RawTthDocRepository extends JpaRepository<RawTthDoc, Integer> {

	/** The histories of any of these forms (ACRF_DOC_ID = ?1 or PRF_DOC_ID = ?2 or AIRS_DOC_ID = ?3). */
	List<RawTthDoc> findByAcrfDocIdOrPrfDocIdOrAirsDocId(Integer acrfDocId, Integer prfDocId, Integer airsDocId);
}
