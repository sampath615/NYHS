package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * An acceptable test result (table ACCPTD_TST_RESULT_DOMAIN): a result type with a text value or a range; inserted and
 * read by LoincResultDomainMappingDAO (the table is also used by SchedulerJobsDAO).
 * <p>
 * <b>Assumed:</b> ATR_DOMAIN_CD is the key and is filled by the database on insert (identity column or trigger; the
 * former insert did not set it and read it back as a generated key). The range columns (NUMBER in the database) are
 * text attributes: read with getString and bound as text, as the former statements did (the database converts).
 * {@link #lastUpdatedDateText} reads LAST_UPDATED_DATE as text (getString), as the former select read it into a
 * String property.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "ACCPTD_TST_RESULT_DOMAIN")
public class AccptdTstResultDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ATR_DOMAIN_CD")
	private Long atrDomainCd;

	@Column(name = "VALUE_TYPE")
	private String valueType;

	@Column(name = "STRING_VALUE")
	private String stringValue;

	@Column(name = "RANGE_LOWER_BOUND")
	private String rangeLowerBound;

	@Column(name = "RANGE_UPPER_BOUND")
	private String rangeUpperBound;

	@Column(name = "FLAG_ACTIVE")
	private String flagActive;

	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	/** LAST_UPDATED_DATE as JDBC getString returns it (read only). */
	@Column(name = "LAST_UPDATED_DATE", insertable = false, updatable = false)
	private String lastUpdatedDateText;
}
