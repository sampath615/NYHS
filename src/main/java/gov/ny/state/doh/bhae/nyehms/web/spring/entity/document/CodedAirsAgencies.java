package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The AIRS agencies (table CODED_AIRS_AGENCIES); only the columns the AIRS import reads.
 * <p>
 * Read-only and read through JPQL projections only. <b>AGENCY_ID as the key is assumed</b> (a retired agency may
 * repeat an id, which does not matter for projections).
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_AIRS_AGENCIES")
public class CodedAirsAgencies {

	@Id
	@Column(name = "AGENCY_ID")
	private String agencyId;

	@Column(name = "LAST_UPGRADE_DATE")
	private Timestamp lastUpgradeDate;

	@Column(name = "RETIRED_DATE")
	private Timestamp retiredDate;
}
