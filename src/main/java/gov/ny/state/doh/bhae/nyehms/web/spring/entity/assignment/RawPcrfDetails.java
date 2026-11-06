package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The substance, condition and pregnancy outcome rows of a pediatric case report form (table RAW_PCRF_DETAILS), one per
 * answer of a form section (PCRF_SECTION).
 * <p>
 * The table has no known key: a form has several rows. <b>DOCUMENT_ID is declared as the key only because an entity
 * needs one; it is not unique.</b> The rows are therefore never loaded as entities: they are read through JPQL
 * projections, inserted by JPQL inserts and deleted by bulk deletes only.
 */
@Entity
@Getter
@Table(name = "RAW_PCRF_DETAILS")
public class RawPcrfDetails {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "PCRF_SECTION")
	private String pcrfSection;

	@Column(name = "SUBS_CD")
	private String subsCd;

	@Column(name = "SCREEN_DATE")
	private Timestamp screenDate;

	@Column(name = "SCREEN_RESULT")
	private String screenResult;

	@Column(name = "SUBS_USE_RESULT")
	private String subsUseResult;

	@Column(name = "SCREENING_DONE")
	private String screeningDone;

	@Column(name = "OTHER_DETAILS")
	private String otherDetails;

	@Column(name = "COND_CD")
	private String condCd;

	@Column(name = "COND_DATE")
	private Timestamp condDate;

	@Column(name = "COND_RESULT")
	private String condResult;

	@Column(name = "PREG_OUTCOME")
	private String pregOutcome;

	@Column(name = "PREG_OUTCOME_YEAR")
	private String pregOutcomeYear;
}
