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
 * The HIV instruments of a lab permit (table LAB_PERMIT_INSTRUMENTS) and their validity.
 * <p>
 * Inserted by a JPQL insert and changed by bulk JPQL updates. <b>LPI_ID as the key, filled by the database on insert (the former insert did not set it), is assumed.</b>
 */
@Entity
@Getter
@Table(name = "LAB_PERMIT_INSTRUMENTS")
public class LabPermitInstruments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LPI_ID")
	private Long lpiId;

	@Column(name = "LAB_PERMIT_ID")
	private Long labPermitId;

	@Column(name = "HIV_INSTRUMENT_ID")
	private Long hivInstrumentId;

	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name = "START_DATE")
	private Timestamp startDate;

	@Column(name = "END_DATE")
	private Timestamp endDate;
}
