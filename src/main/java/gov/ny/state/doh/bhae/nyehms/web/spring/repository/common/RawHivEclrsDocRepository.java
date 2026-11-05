package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RawHivEclrsDoc;

/** The raw eCLRS HIV lab documents (table RAW_HIV_ECLRS_DOC). */
public interface RawHivEclrsDocRepository extends JpaRepository<RawHivEclrsDoc, Long> {

	/**
	 * The number of the documents whose lab PFI or LOINC code differs from the given ones (SQL rules: a NULL value
	 * differs from nothing). Pass the same documents twice (the parameters must not be null or empty).
	 */
	long countByMshlabpfiNotAndDocumentIdInOrObxloinccodeNotAndDocumentIdIn(String mshlabpfi, Collection<Long> documentIds, String obxloinccode,
			Collection<Long> sameDocumentIds);
}
