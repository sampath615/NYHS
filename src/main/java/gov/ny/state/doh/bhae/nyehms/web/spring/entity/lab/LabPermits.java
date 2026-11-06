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
 * The permits of a lab (table LAB_PERMITS): permit code, validity, BHAE certification date and comments.
 * <p>
 * Inserted by a JPQL insert and changed by bulk JPQL updates (the dates are converted by the database's TO_DATE as before). <b>LAB_PERMIT_ID as the key, filled by the database on insert (the former insert did not set it), is assumed.</b>
 */
@Entity
@Getter
@Table(name = "LAB_PERMITS")
public class LabPermits {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LAB_PERMIT_ID")
	private Long labPermitId;

	@Column(name = "PFI")
	private String pfi;

	@Column(name = "PERMIT_CD")
	private String permitCd;

	@Column(name = "DATE_VALID_FROM")
	private Timestamp dateValidFrom;

	@Column(name = "DATE_VALID_TILL")
	private Timestamp dateValidTill;

	@Column(name = "BHAE_CERTIFICATION_DATE")
	private Timestamp bhaeCertificationDate;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;
}
