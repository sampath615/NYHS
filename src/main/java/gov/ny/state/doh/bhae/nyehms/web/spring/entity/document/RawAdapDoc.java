package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;

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
 * An ADAP record as received (table RAW_ADAP_DOC): inserted by the ADAP import (ADAPDocumentDAO.insertRawADAPDocument),
 * its street and apartment read by the ADAP document screen. The columns the former statements used.
 * <p>
 * <b>DOCUMENT_ID as the key is assumed</b> (given by the application). The conversions the former insert left to the
 * database stay there (@ColumnTransformer: TRIM(UPPER(...)) of the text columns except ADAP_FILE); PROCESS_DATE is
 * the database's SYSDATE (left null on insert). The column ID is the field rawId.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAW_ADAP_DOC")
public class RawAdapDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "TRANS")
	@ColumnTransformer(write = "trim(upper(?))")
	private String trans;

	@Column(name = "SPWA")
	@ColumnTransformer(write = "trim(upper(?))")
	private String spwa;

	@Column(name = "SMALE")
	@ColumnTransformer(write = "trim(upper(?))")
	private String smale;

	@Column(name = "SIVDU")
	@ColumnTransformer(write = "trim(upper(?))")
	private String sivdu;

	@Column(name = "SFEMALE")
	@ColumnTransformer(write = "trim(upper(?))")
	private String sfemale;

	@Column(name = "SEX")
	@ColumnTransformer(write = "trim(upper(?))")
	private String sex;

	@Column(name = "ROTHER")
	@ColumnTransformer(write = "trim(upper(?))")
	private String rother;

	@Column(name = "RACE")
	@ColumnTransformer(write = "trim(upper(?))")
	private String race;

	@Column(name = "MATER")
	@ColumnTransformer(write = "trim(upper(?))")
	private String mater;

	@Column(name = "IVDU")
	@ColumnTransformer(write = "trim(upper(?))")
	private String ivdu;

	@Column(name = "HISPANIC")
	@ColumnTransformer(write = "trim(upper(?))")
	private String hispanic;

	@Column(name = "HEALTH")
	@ColumnTransformer(write = "trim(upper(?))")
	private String health;

	@Column(name = "YRFIRST")
	@ColumnTransformer(write = "trim(upper(?))")
	private String yrfirst;

	@Column(name = "PROVNPI")
	@ColumnTransformer(write = "trim(upper(?))")
	private String provnpi;

	@Column(name = "PFIRST")
	@ColumnTransformer(write = "trim(upper(?))")
	private String pfirst;

	@Column(name = "LAST_RX")
	@ColumnTransformer(write = "trim(upper(?))")
	private String lastRx;

	@Column(name = "LAST_PC")
	@ColumnTransformer(write = "trim(upper(?))")
	private String lastPc;

	@Column(name = "DOB")
	@ColumnTransformer(write = "trim(upper(?))")
	private String dob;

	@Column(name = "MCD_NUMBER")
	@ColumnTransformer(write = "trim(upper(?))")
	private String mcdNumber;

	@Column(name = "FIRST")
	@ColumnTransformer(write = "trim(upper(?))")
	private String first;

	@Column(name = "PT1")
	@ColumnTransformer(write = "trim(upper(?))")
	private String pt1;

	@Column(name = "PLAST")
	@ColumnTransformer(write = "trim(upper(?))")
	private String plast;

	@Column(name = "LAST")
	@ColumnTransformer(write = "trim(upper(?))")
	private String last;

	@Column(name = "CITY")
	@ColumnTransformer(write = "trim(upper(?))")
	private String city;

	@Column(name = "STATE")
	@ColumnTransformer(write = "trim(upper(?))")
	private String state;

	@Column(name = "PSTATE")
	@ColumnTransformer(write = "trim(upper(?))")
	private String pstate;

	@Column(name = "PCITY")
	@ColumnTransformer(write = "trim(upper(?))")
	private String pcity;

	@Column(name = "STREET")
	@ColumnTransformer(write = "trim(upper(?))")
	private String street;

	@Column(name = "CAREOF")
	@ColumnTransformer(write = "trim(upper(?))")
	private String careof;

	@Column(name = "ADAP_FILE")
	private String adapFile;

	@Column(name = "ZIP")
	@ColumnTransformer(write = "trim(upper(?))")
	private String zip;

	@Column(name = "PZIP")
	@ColumnTransformer(write = "trim(upper(?))")
	private String pzip;

	@Column(name = "ID")
	@ColumnTransformer(write = "trim(upper(?))")
	private String rawId;

	@Column(name = "APT")
	@ColumnTransformer(write = "trim(upper(?))")
	private String apt;

	@Column(name = "SSN")
	@ColumnTransformer(write = "trim(upper(?))")
	private String ssn;

	@Column(name = "RECV_DT")
	private Timestamp recvDt;

	@Column(name = "PROCESS_DATE")
	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	private Timestamp processDate;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "PSTREET")
	@ColumnTransformer(write = "trim(upper(?))")
	private String pstreet;
}
