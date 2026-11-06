package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The LOINC codes of a lab (table LAB_LOINC_MAPPING) with their validity, test kit, instrument and viral load detection level; the columns LabReportsDAO reads and writes (the table is also used by SchedulerJobsDAO).
 * <p>
 * Inserted by a JPQL insert and changed by bulk JPQL updates. TEST_KIT, INSTRUMENT and VL_DETECTION_LEVEL are mapped as text because the former statements bound the screens' text values as they were (Oracle converts them as before if they are NUMBER columns). <b>LAB_LOINC_MAP_ID as the key, filled by the database on insert (the former insert did not set it), is assumed.</b>
 */
@Entity
@Getter
@Table(name = "LAB_LOINC_MAPPING")
public class LabLoincMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LAB_LOINC_MAP_ID")
	private Long labLoincMapId;

	@Column(name = "PFI")
	private String pfi;

	@Column(name = "LOINC_CD")
	private String loincCd;

	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name = "START_DATE")
	private Timestamp startDate;

	@Column(name = "END_DATE")
	private Timestamp endDate;

	@Column(name = "TEST_KIT")
	private String testKit;

	@Column(name = "INSTRUMENT")
	private String instrument;

	@Column(name = "VL_DETECTION_LEVEL")
	private String vlDetectionLevel;
}
