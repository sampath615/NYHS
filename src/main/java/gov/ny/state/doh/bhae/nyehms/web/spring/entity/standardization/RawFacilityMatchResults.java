package gov.ny.state.doh.bhae.nyehms.web.spring.entity.standardization;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A potential match of a raw facility (table RAW_FACILITY_MATCH_RESULTS), read by RawSubFacilityDAO and written by the
 * standardization procedures. The table has no known key and a raw facility has several matches, so it is read only
 * through projections; <b>RAW_FACILITY_ID as the key is assumed</b> only to satisfy JPA (no entity is loaded).
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RAW_FACILITY_MATCH_RESULTS")
public class RawFacilityMatchResults {

	@Id
	@Column(name = "RAW_FACILITY_ID")
	private Long rawFacilityId;

	@Column(name = "POT_RAW_FACILITY_ID")
	private Long potRawFacilityId;
}
