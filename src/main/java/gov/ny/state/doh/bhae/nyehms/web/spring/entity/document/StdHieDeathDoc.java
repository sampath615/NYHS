package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.math.BigDecimal;
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
 * An HIE death record, standardized (table STD_HIE_DEATH_DOC): the columns the HIE death document screen reads and
 * the scheduler writes (HIEDTHDocumentDAO).
 * <p>
 * Inserted, never changed. <b>DOCUMENT_ID as the key is assumed</b> (one row per document). An insert keeps the
 * conversions of the former insert in the database (the write transformers: UPPER/TRIM, the date texts checked with
 * FN_TO_DATE); EHARS_IMPORT_DATE, EHARS_STATUS_CD and NYC_SHIP_DATE are not written (as before). MPIID is read and
 * written as text (it is compared with the received records' MPIID).
 */
@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_HIE_DEATH_DOC")
public class StdHieDeathDoc implements Persistable<Integer> {

	private static final String UPPER = "upper(trim(?))";

	private static final String DATE_TEXT = "TO_CHAR(FN_TO_DATE(trim(?),'MM/DD/YYYY'),'MM/DD/YYYY')";

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@ColumnTransformer(write = UPPER)
	@Column(name = "SOURCE_FACILITY")
	private String sourceFacility;

	@ColumnTransformer(write = UPPER)
	@Column(name = "MEDICAL_RECORD_NUMBER")
	private String medicalRecordNumber;

	@Column(name = "MPIID")
	private String mpiid;

	@ColumnTransformer(write = UPPER)
	@Column(name = "FNAME")
	private String fname;

	@ColumnTransformer(write = UPPER)
	@Column(name = "LNAME")
	private String lname;

	@ColumnTransformer(write = DATE_TEXT)
	@Column(name = "DOB_TEXT")
	private String dobText;

	@ColumnTransformer(write = DATE_TEXT)
	@Column(name = "DOD_TEXT")
	private String dodText;

	@Column(name = "DEATHTIME")
	private String deathtime;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "RECEIVED_DATE")
	private Timestamp receivedDate;

	@Column(name = "HIE_NAME")
	private String hieName;

	@ColumnTransformer(write = UPPER)
	@Column(name = "MATCH_CD")
	private String matchCd;

	@ColumnTransformer(write = UPPER)
	@Column(name = "MATCH_PASS")
	private String matchPass;

	@Column(name = "MATCH_WEIGHT")
	private BigDecimal matchWeight;

	@ColumnTransformer(write = UPPER)
	@Column(name = "MATCH_STATENO")
	private String matchStateno;

	@Column(name = "QS_PARENT_DOC_ID")
	private BigDecimal qsParentDocId;

	@Column(name = "MATCHED_PROCESS_DATE")
	private Timestamp matchedProcessDate;

	@Column(name = "EHARS_IMPORT_DATE", insertable = false)
	private Timestamp eharsImportDate;

	@Column(name = "EHARS_STATUS_CD", insertable = false)
	private String eharsStatusCd;

	@Column(name = "NYC_SHIP_DATE", insertable = false)
	private Timestamp nycShipDate;

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
