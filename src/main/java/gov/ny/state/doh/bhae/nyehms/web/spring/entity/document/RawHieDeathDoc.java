package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;
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
 * An HIE death record, as received (table RAW_HIE_DEATH_DOC): the columns the HIE death file load writes and the
 * scheduler's duplicate and CCD checks read and change (HIEDTHDocumentDAO).
 * <p>
 * <b>DOCUMENT_ID as the key is assumed</b> (one row per document). Inserted as a new row (the date texts checked by
 * the database, as before: TO_CHAR(FN_TO_DATE(TRIM(?), 'MM/DD/YYYY'), 'MM/DD/YYYY')); the process status is changed
 * by bulk updates. MPIID is read as text (it is compared with the CCD documents' MPI).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAW_HIE_DEATH_DOC")
public class RawHieDeathDoc implements Persistable<Integer> {

	private static final String DATE_TEXT = "TO_CHAR(FN_TO_DATE(trim(?),'MM/DD/YYYY'),'MM/DD/YYYY')";

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "SOURCE_FACILITY")
	private String sourceFacility;

	@Column(name = "MEDICAL_RECORD_NUMBER")
	private String medicalRecordNumber;

	@Column(name = "MPIID")
	private String mpiid;

	@Column(name = "FNAME")
	private String fname;

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
