package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A cancer registry record as received (table RAW_CANCER_DOC); the columns the cancer document screens and the cancer
 * clerical review read and write.
 * <p>
 * The record is identified by TO_NUMBER(PATIENTIDNUMBER), a text column (as before; a record whose PATIENTIDNUMBER is
 * not a number makes those queries fail, as before). <b>PATIENTIDNUMBER as the key is assumed</b>; the review changes
 * are bulk updates. The dates of birth and death are text (MM/DD/YYYY).
 */
@Entity
@Getter
@Table(name = "RAW_CANCER_DOC")
public class RawCancerDoc {

	@Id
	@Column(name = "PATIENTIDNUMBER")
	private String patientidnumber;

	@Column(name = "NAMEFIRST")
	private String namefirst;

	@Column(name = "NAMEMIDDLE")
	private String namemiddle;

	@Column(name = "NAMELAST")
	private String namelast;

	/** '1' male, '2' female. */
	@Column(name = "SEX")
	private String sex;

	@Column(name = "DATEOFBIRTH")
	private String dateofbirth;

	@Column(name = "DATEOFDEATHCANADA")
	private String dateofdeathcanada;

	@Column(name = "SOCIALSECURITYNUMBER")
	private String socialsecuritynumber;

	@Column(name = "RACE1")
	private String race1;

	@Column(name = "SPANISHHISPANICORIGIN")
	private String spanishhispanicorigin;

	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@Column(name = "PERSON_CITY")
	private String personCity;

	@Column(name = "PERSON_STATE")
	private String personState;

	@Column(name = "PERSON_ZIP")
	private String personZip;

	/** The county's FIPS code (CODED_COUNTY.COUNTY_FIPS_CODE). */
	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@Column(name = "TELEPHONE")
	private String telephone;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	/** The Dataflux match code: 'C' (to review), then 'C_M' (matched) or 'C_N' (not a match). */
	@Column(name = "FLUX_MATCH_CD")
	private String fluxMatchCd;

	@Column(name = "FLUX_POT_STATENO")
	private String fluxPotStateno;

	@Column(name = "FLUX_MATCHED_STATENO")
	private String fluxMatchedStateno;

	@Column(name = "REVIEW_UID")
	private String reviewUid;

	@Column(name = "REVIEW_DATE")
	private Timestamp reviewDate;
}
