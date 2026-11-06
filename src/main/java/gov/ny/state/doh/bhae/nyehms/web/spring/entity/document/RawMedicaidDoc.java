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
 * A Medicaid (or insurance death) record, as received (table RAW_MEDICAID_DOC): the columns the document copy writes
 * and the Medicaid document screen reads (MedicaidDocumentDAO).
 * <p>
 * Inserted, never changed. <b>DOCUMENT_ID as the key is assumed</b> (one row per document). An insert keeps the
 * conversions of the former insert in the database (TRIM(UPPER(?))); PROCESS_DATE is the database's SYSDATE (left
 * null).
 */
@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAW_MEDICAID_DOC")
public class RawMedicaidDoc implements Persistable<Integer> {

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	@Column(name = "PROCESS_DATE")
	private Timestamp processDate;

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private BigDecimal currentProcessStatus;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "MI")
	private String mi;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CRITERIA_1")
	private String criteria1;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CRITERIA_2")
	private String criteria2;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CRITERIA_3")
	private String criteria3;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CRITERIA_4")
	private String criteria4;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CRITERIA_5")
	private String criteria5;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CRITERIA_6")
	private String criteria6;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CRITERIA_7")
	private String criteria7;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CRITERIA_8")
	private String criteria8;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CRITERIA_9")
	private String criteria9;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CRITERIA_10")
	private String criteria10;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "BIRTH_DT")
	private String birthDt;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "SEX_CD")
	private String sexCd;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "RACE_CD")
	private String raceCd;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "ETHNIC_CD")
	private String ethnicCd;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "DEATH_DATE")
	private String deathDate;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CREATE_UID")
	private String createUid;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CIN")
	private String cin;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "POSTAL_CD")
	private String postalCd;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "MAIL_ADDR_CITY")
	private String mailAddrCity;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "MAIL_ADDR_STR")
	private String mailAddrStr;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "FIRST_NAME")
	private String firstName;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "MEDICAID_FILE")
	private String medicaidFile;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "VITAL_STATUS")
	private String vitalStatus;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "SSN")
	private String ssn;

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
