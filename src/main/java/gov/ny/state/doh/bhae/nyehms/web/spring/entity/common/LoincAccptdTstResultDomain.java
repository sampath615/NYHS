package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.DynamicUpdate;

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
 * The association of an acceptable test result with a LOINC code and an eHARS standard result (table
 * LOINC_ACCPTD_TST_RESULT_DOMAIN): inserted, changed and read by LoincResultDomainMappingDAO (the table is also used by
 * SchedulerJobsDAO).
 * <p>
 * <b>Assumed:</b> LATRD_ID is the key and is filled by the database on insert (identity column or trigger; the former
 * insert did not set it). RESULTS_STD_CD is a text attribute (its column type is not known; it is bound as text, as
 * the callers pass it). A change writes only the changed columns.
 */
@Entity
@DynamicUpdate
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "LOINC_ACCPTD_TST_RESULT_DOMAIN")
public class LoincAccptdTstResultDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LATRD_ID")
	private Long latrdId;

	@Column(name = "ATR_DOMAIN_CD")
	private Long atrDomainCd;

	@Column(name = "LOINC_CD")
	private String loincCd;

	@Column(name = "FLAG_ACTIVE")
	private String flagActive;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name = "RESULTS_STD_CD")
	private String resultsStdCd;

	/** Activates or deactivates the association. */
	public void changeStatus(String flagActive, Long lastUpdatedBy, Timestamp lastUpdatedDate) {
		this.flagActive = flagActive;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDate = lastUpdatedDate;
	}
}
