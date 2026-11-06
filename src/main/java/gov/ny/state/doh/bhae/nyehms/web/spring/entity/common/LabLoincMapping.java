package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A LOINC code a lab (PFI) may report, with its validity (table LAB_LOINC_MAPPING); the columns the eCLRS loaders read
 * (the table is also used by LabReportsDAO, which may add its columns here).
 * <p>
 * Read-only, used in counts only. <b>LAB_LOINC_MAP_ID as the key is assumed.</b> PFI is a text attribute: the former
 * statement compared it with the PFI text of the lab report as it was.
 */
@Entity
@Immutable
@Getter
@Table(name = "LAB_LOINC_MAPPING")
public class LabLoincMapping {

	@Id
	@Column(name = "LAB_LOINC_MAP_ID")
	private Long labLoincMapId;

	@Column(name = "PFI")
	private String pfi;

	@Column(name = "LOINC_CD")
	private String loincCd;

	@Column(name = "START_DATE")
	private Timestamp startDate;

	@Column(name = "END_DATE")
	private Timestamp endDate;
}
