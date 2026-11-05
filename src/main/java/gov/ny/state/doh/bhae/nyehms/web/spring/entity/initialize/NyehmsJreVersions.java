package gov.ny.state.doh.bhae.nyehms.web.spring.entity.initialize;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The Java versions accepted at login (table NYEHMS_JRE_VERSIONS; from the removed applet login check).
 * <p>
 * Read-only and read through projections only (a key that repeats does not matter then). <b>JRE_VERSION as the key
 * is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_JRE_VERSIONS")
public class NyehmsJreVersions {

	@Id
	@Column(name = "JRE_VERSION")
	private String jreVersion;

	@Column(name = "REJECT_FLAG")
	private String rejectFlag;

	@Column(name = "EFFECTIVE_START_DATE")
	private Timestamp effectiveStartDate;

	@Column(name = "EFFECTIVE_END_DATE")
	private Timestamp effectiveEndDate;
}
