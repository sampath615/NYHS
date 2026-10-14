package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycPrfPartnerData;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfPartner;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfPartnerId;

/**
 * Spring Data JPA repository for NYC PRF partners (replaces the MyBatis NycPRFPartnerDAO).
 */
public interface NycprfPartnerRepository extends Repository<StdNycprfPartner, StdNycprfPartnerId> {

	/**
	 * The partners listed on an NYC PRF document: the standardized partner data plus the address
	 * as received, built as the form object the NYC PRF document screen binds to. Each partner is
	 * returned once even if it was received more than once with the same address.
	 *
	 * @param nycprfDocumentId document id of the NYC PRF form
	 * @return the partners; empty if there are none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.NycPrfPartnerData(stdNycprfPartner, rawNycprfPartner)
			from StdNycprfPartner stdNycprfPartner
			join RawNycprfPartner rawNycprfPartner
			on rawNycprfPartner.patientId = stdNycprfPartner.patientId
			and rawNycprfPartner.nycprfDocumentId = :nycprfDocumentId
			""")
	List<NycPrfPartnerData> findPartnersOfDocument(long nycprfDocumentId);
}
