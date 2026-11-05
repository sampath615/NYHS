package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;
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
 * An SSDM (Social Security Death Master) record, standardized (table STD_SSDM_DOC): the columns the SSDM document
 * screen reads and the columns the SSDM import writes (SSDMDocumentDAO).
 * <p>
 * Inserted, never changed. <b>DOCUMENT_ID as the key is assumed</b> (one row per document). An insert through
 * {@code saveAndFlush} keeps the conversions of the former insert in the database (the write transformers: TRIM/UPPER,
 * TO_CHAR(TO_DATE(...)) of the date texts); HASH_NUMBER is not written then (as before), only by the bulk import's
 * insert ... select. EHARS_IMPORT_DATE is read as a date only (the screen) and written as a date with its time.
 */
@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_SSDM_DOC")
public class StdSsdmDoc implements Persistable<Integer> {

	private static final String UPPER = "trim(upper(?))";

	/** TO_CHAR(TO_DATE(trim(?), 'MM/DD/YYYY'), 'MM/DD/YYYY'): a date text checked by the database, as before. */
	private static final String DATE_TEXT = "TO_CHAR(TO_DATE(trim(?), 'MM/DD/YYYY'), 'MM/DD/YYYY')";

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "SSDM_SRC_ID")
	private Long ssdmSrcId;

	@Column(name = "SSN")
	@ColumnTransformer(write = UPPER)
	private String ssn;

	@Column(name = "FNAME")
	@ColumnTransformer(write = UPPER)
	private String fname;

	@Column(name = "MNAME")
	@ColumnTransformer(write = UPPER)
	private String mname;

	@Column(name = "LNAME")
	@ColumnTransformer(write = UPPER)
	private String lname;

	@Column(name = "SUFFIX")
	@ColumnTransformer(write = UPPER)
	private String suffix;

	@Column(name = "DOB_TEXT")
	@ColumnTransformer(write = DATE_TEXT)
	private String dobText;

	@Column(name = "DOD_TEXT")
	@ColumnTransformer(write = DATE_TEXT)
	private String dodText;

	@Column(name = "BIRTH_YEAR")
	@ColumnTransformer(write = UPPER)
	private String birthYear;

	@Column(name = "AGE_AT_DEATH")
	private Integer ageAtDeath;

	@Column(name = "PERSON_STATE_AT_DEATH")
	@ColumnTransformer(write = UPPER)
	private String personStateAtDeath;

	@Column(name = "PERSON_ZIP_AT_DEATH")
	@ColumnTransformer(write = UPPER)
	private String personZipAtDeath;

	@Column(name = "PERSON_COUNTY_AT_DEATH")
	@ColumnTransformer(write = UPPER)
	private String personCountyAtDeath;

	/** EHARS_IMPORT_DATE as the screen reads it (a date only). */
	@Column(name = "EHARS_IMPORT_DATE", insertable = false, updatable = false)
	private Date eharsImportDate;

	/** EHARS_IMPORT_DATE as the import writes it (with its time). */
	@Column(name = "EHARS_IMPORT_DATE")
	private Timestamp eharsImportTime;

	@Column(name = "SSDM_FILE")
	@ColumnTransformer(write = "trim(?)")
	private String ssdmFile;

	@Column(name = "ORIG_LNAME")
	@ColumnTransformer(write = UPPER)
	private String origLname;

	@Column(name = "MATCH_PASS")
	@ColumnTransformer(write = UPPER)
	private String matchPass;

	@Column(name = "MATCH_CD")
	@ColumnTransformer(write = UPPER)
	private String matchCd;

	@Column(name = "REVIEW_UID")
	@ColumnTransformer(write = UPPER)
	private String reviewUid;

	@Column(name = "MATCHED_STATENO")
	@ColumnTransformer(write = UPPER)
	private String matchedStateno;

	@Column(name = "EHARS_STATUS_CD")
	@ColumnTransformer(write = UPPER)
	private String eharsStatusCd;

	@Column(name = "MATCH_WEIGHT")
	private BigDecimal matchWeight;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "QS_PARENT_DOC_ID")
	private Long qsParentDocId;

	@Column(name = "REVIEW_DATE")
	private Timestamp reviewDate;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "MATCHED_PROCESS_DATE")
	private Timestamp matchedProcessDate;

	@Column(name = "CREATE_UID")
	@ColumnTransformer(write = UPPER)
	private String createUid;

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

	/** ORA_HASH of the record's names, dates, SSN and suffix; written only by the bulk import. */
	@Column(name = "HASH_NUMBER", insertable = false, updatable = false)
	private Long hashNumber;

	@Override
	public Integer getId() {
		return documentId;
	}

	/** Always a new row (the application only inserts it). */
	@Override
	public boolean isNew() {
		return true;
	}
}
