package gov.ny.state.doh.bhae.nyehms.web.spring.repository.cluster;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.cluster.CodedPartnerRefData;

/** The code lists of the cluster partner screens (CODED_PARTNER_REF_DATA). */
public interface CodedPartnerRefDataRepository extends JpaRepository<CodedPartnerRefData, String> {

	/** A code of a list (a projection: the table has no known key). */
	record RefData(String refColumn, String lookupName, String lookupCd) {
	}

	/** All codes, by list and name. */
	List<RefData> findAllByOrderByRefColumnAscLookupNameAsc();
}
