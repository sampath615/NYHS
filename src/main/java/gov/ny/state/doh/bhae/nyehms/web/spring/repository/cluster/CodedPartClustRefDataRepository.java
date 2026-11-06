package gov.ny.state.doh.bhae.nyehms.web.spring.repository.cluster;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.cluster.CodedPartClustRefData;

/** The PS message reasons of the cluster partner review (CODED_PART_CLUST_REF_DATA). */
public interface CodedPartClustRefDataRepository extends JpaRepository<CodedPartClustRefData, String> {

	/** A reason (a projection: the table has no known key). */
	record Reason(String lookupName, String lookupCd) {
	}

	/** The reasons of one list, by name (a null list: callers must not pass it, it would mean IS NULL here). */
	List<Reason> findByRefColumnOrderByLookupNameAsc(String refColumn);
}
