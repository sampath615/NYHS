package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The test codes of a lab's local code (table LAB_LOCAL_CD_TEST_CD_MAPPING) and their validity.
 * <p>
 * Inserted by a JPQL insert and changed by bulk JPQL updates. <b>LAB_LOCAL_TEST_CD as the key, filled by the database on insert (the former insert did not set it), is assumed.</b>
 */
@Entity
@Getter
@Table(name = "LAB_LOCAL_CD_TEST_CD_MAPPING")
public class LabLocalCdTestCdMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LAB_LOCAL_TEST_CD")
	private Long labLocalTestCd;

	@Column(name = "LAB_LOCAL_CD_MAP_ID")
	private Long labLocalCdMapId;

	@Column(name = "LAB_LOCAL_TEST_DESC")
	private String labLocalTestDesc;

	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name = "START_DATE")
	private Timestamp startDate;

	@Column(name = "END_DATE")
	private Timestamp endDate;
}
