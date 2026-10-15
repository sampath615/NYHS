package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycPrfPartnerData;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawNycprfPartner;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfPartner;

/**
 * Spring Data JPA repository for NYC PRF partners (replaces the MyBatis NycPRFPartnerDAO).
 */
public interface NycprfPartnerRepository extends JpaRepository<StdNycprfPartner, String> {

	/**
	 * The partners listed on an NYC PRF document: the standardized partner data plus the address
	 * as received, built as the form object the NYC PRF document screen binds to. Each partner is
	 * returned once per received address (once if it was received more than once with the same address).
	 *
	 * @param nycprfDocumentId document id of the NYC PRF form
	 * @return the partners; empty if there are none
	 */
	@Query("""
			select distinct new gov.ny.state.doh.bhae.nyehms.web.spring.record.NycPrfPartnerData(stdNycprfPartner, rawNycprfPartner.address)
			from StdNycprfPartner stdNycprfPartner
			join RawNycprfPartner rawNycprfPartner
			on rawNycprfPartner.patientId = stdNycprfPartner.patientId
			and rawNycprfPartner.nycprfDocumentId = :nycprfDocumentId
			""")
	List<NycPrfPartnerData> findPartnersOfDocument(long nycprfDocumentId);

	/**
	 * The partners as received on an NYC PRF document (RAW_NYCPRF_PARTNER), for the import.
	 *
	 * @param documentId document id of the NYC PRF form
	 * @return the received partners; empty if there are none
	 */
	@Query("""
			select rawNycprfPartner
			from RawNycprfPartner rawNycprfPartner
			where rawNycprfPartner.nycprfDocumentId = :documentId
			""")
	List<RawNycprfPartner> findReceivedPartnersOfDocument(long documentId);
}
