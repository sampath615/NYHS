package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;
import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A CASA report, standardized (); the columns the CASA document screen reads and the import writes.
 * <p>
 * Never updated: a row is inserted once by the import ({@code CasaDocRepository.saveAndFlush}, built by
 * {@code CasaStdDocMapping}), so it is always new ({@link #isNew()}: save() inserts without a select first).
 * Columns the former SQL insert wrote as text are text here too (Oracle converts the numeric ones).
 * <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_CASA_DOC")
public class StdCasaDoc implements Persistable<Integer> {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "MEDICAID_NUMBER")
	private String medicaidNumber;

	@Column(name = "INPAT")
	private String inpat;

	@Column(name = "CLAIMCOUNT")
	private String claimcount;

	@Column(name = "OUTPAT")
	private String outpat;

	@Column(name = "DIAGALLCLAIMS")
	private String diagallclaims;

	@Column(name = "HIVHOSPICE")
	private String hivhospice;

	@Column(name = "HIVSNP")
	private String hivsnp;

	@Column(name = "ARV")
	private String arv;

	@Column(name = "OUTP_ADIOI")
	private String outpAdioi;

	@Column(name = "OUTPSERV_DIAG")
	private String outpservDiag;

	@Column(name = "SPEC_POSS")
	private String specPoss;

	@Column(name = "SPEC_ADI")
	private String specAdi;

	@Column(name = "ARV_POSS")
	private String arvPoss;

	@Column(name = "ARV_ADI")
	private String arvAdi;

	@Column(name = "ARV_OUTPAT")
	private String arvOutpat;

	@Column(name = "PROCEDURES")
	private String procedures;

	@Column(name = "OUTPAT_SELECT")
	private String outpatSelect;

	@Column(name = "FINCRG")
	private String fincrg;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "PERSON_ZIP")
	private String personZip;

	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@Column(name = "ELIG_DATE")
	private String eligDate;

	@Column(name = "NARROW")
	private String narrow;

	@Column(name = "RATE")
	private String rate;

	@Column(name = "RATE1")
	private String rate1;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "RACE_MULTIPLE")
	private String raceMultiple;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "CASA_FILE")
	private String casaFile;

	@Column(name = "EHARS_IMPORT_DATE")
	private Timestamp eharsImportDate;

	@Column(name = "MATCH_PASS")
	private String matchPass;

	@Column(name = "MATCH_CD")
	private String matchCd;

	@Column(name = "MATCHED_STATENO")
	private String matchedStateno;

	@Column(name = "REVIEW_UID")
	private String reviewUid;

	@Column(name = "EHARS_STATUS_CD")
	private String eharsStatusCd;

	@Column(name = "MATCH_WEIGHT")
	private BigDecimal matchWeight;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "QS_PARENT_DOC_ID")
	private Long qsParentDocId;

	@Column(name = "REVIEW_DT")
	private Timestamp reviewDt;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "MATCHED_PROCESS_DATE")
	private Timestamp matchedProcessDate;

	@Column(name = "FLUX_MATCH_CD")
	private String fluxMatchCd;

	@Column(name = "FLUX_POT_STATENO")
	private String fluxPotStateno;

	@Column(name = "FLUX_MATCHED_STATENO")
	private String fluxMatchedStateno;

	@Column(name = "FLUX_QS_PARENT_DOC_ID")
	private Long fluxQsParentDocId;

	@Column(name = "FLUX_MATCHED_PROCESS_DATE")
	private Timestamp fluxMatchedProcessDate;

	@Override
	public Integer getId() {
		return documentId;
	}

	/** Rows are only ever inserted. */
	@Override
	public boolean isNew() {
		return true;
	}
}
