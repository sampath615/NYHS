package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A DIN (inmate) record of a DOCCS report, as received (table RAW_DIN_DOCCS_DOC): the columns the DOCCS import reads
 * and the DIN DOCCS screen shows (DoccsDocumentDAO).
 * <p>
 * Read in JPQL; changed only by bulk updates (the process status). <b>DOCUMENT_ID as the key is assumed</b> (one row
 * per DIN document). The numbers (DOCUMENT_ID, DOCCS_DOC_ID, CURRENT_PROCESS_STATUS) are read as BigDecimal and
 * PROCESS_DATE as a timestamp, as JDBC getObject read them for the import's maps; <b>the other columns are assumed to be
 * text</b> (the import treats them as text; RECNUM is not used).
 */
@Entity
@Getter
@Table(name = "RAW_DIN_DOCCS_DOC")
public class RawDinDoccsDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private BigDecimal documentId;

	@Column(name = "DOCCS_DOC_ID")
	private BigDecimal doccsDocId;

	@Column(name = "RECNUM")
	private String recnum;

	@Column(name = "NYSID")
	private String nysid;

	@Column(name = "DIN")
	private String din;

	@Column(name = "ADMDATE")
	private String admdate;

	@Column(name = "LADMDATE")
	private String ladmdate;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "SUFFIX")
	private String suffix;

	@Column(name = "BIRTH")
	private String birth;

	@Column(name = "POB")
	private String pob;

	@Column(name = "ALIENS2")
	private String aliens2;

	@Column(name = "USDATE")
	private String usdate;

	@Column(name = "RESCO")
	private String resco;

	@Column(name = "LEGRESC")
	private String legresc;

	@Column(name = "SEX")
	private String sex;

	@Column(name = "RACE")
	private String race;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "LRELDA")
	private String lrelda;

	@Column(name = "DRUG1")
	private String drug1;

	@Column(name = "DRUG2")
	private String drug2;

	@Column(name = "DRUG3")
	private String drug3;

	@Column(name = "UC")
	private String uc;

	@Column(name = "LSTREET")
	private String lstreet;

	@Column(name = "LCITY")
	private String lcity;

	@Column(name = "LSTATE")
	private String lstate;

	@Column(name = "LZIP")
	private String lzip;

	@Column(name = "LZIP9")
	private String lzip9;

	@Column(name = "STREET")
	private String street;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIP")
	private String zip;

	@Column(name = "ZIP9")
	private String zip9;

	@Column(name = "SRP")
	private String srp;

	@Column(name = "PREV_STREET")
	private String prevStreet;

	@Column(name = "PREV_CITY")
	private String prevCity;

	@Column(name = "PREV_STATE")
	private String prevState;

	@Column(name = "PREV_ZIP9")
	private String prevZip9;

	@Column(name = "DEATH")
	private String death;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "CIN")
	private String cin;

	@Column(name = "LRELDATE")
	private String lreldate;

	@Column(name = "LDISCHRGDATE")
	private String ldischrgdate;

	@Column(name = "CURRENT_FACILITY")
	private String currentFacility;

	@Column(name = "OWNING_FACILITY")
	private String owningFacility;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private BigDecimal currentProcessStatus;

	@Column(name = "ERDATE")
	private String erdate;

	@Column(name = "DOCCS_FILE")
	private String doccsFile;

	@Column(name = "PROCESS_DATE")
	private Timestamp processDate;

	@Column(name = "PRLRELCNTY")
	private String prlrelcnty;
}
