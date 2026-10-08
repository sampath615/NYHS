package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NycPRFPartnerVO;

/**
 * Queries of {@link NycprfPartnerRepository} that need SQL-level control.
 */
public interface NycprfPartnerQueries {

	/**
	 * The partners listed on an NYC PRF document (standardized partner data plus the
	 * address as received), in the shape the NYC PRF document screen uses.
	 *
	 * @param nycprfDocumentId document id of the NYC PRF form
	 * @return the partners; empty if there are none
	 */
	List<NycPRFPartnerVO> findPartnersOfDocument(int nycprfDocumentId);
}
