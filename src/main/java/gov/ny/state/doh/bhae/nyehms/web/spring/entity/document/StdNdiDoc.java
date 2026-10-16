package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Date;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An NDI (National Death Index) record, standardized (table STD_NDI_DOC); the columns the NDI document screen reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_NDI_DOC")
public class StdNdiDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "EHARS_IMPORT_DATE")
	private String eharsImportDate;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "STATE_RES")
	private String stateRes;

	@Column(name = "MONTH_DEATH")
	private String monthDeath;

	@Column(name = "DAY_DEATH")
	private String dayDeath;

	@Column(name = "YEAR_DEATH")
	private String yearDeath;

	@Column(name = "RECEIVED_DATE")
	private Date receivedDate;

	@Column(name = "UNIQ_NO")
	private String uniqNo;

	@Column(name = "NDI_DOD")
	private String ndiDod;

	@Column(name = "DEATH_CERT")
	private String deathCert;

	@Column(name = "STATE_DEATH")
	private String stateDeath;

	@Column(name = "ICD_CODE")
	private String icdCode;

	@Column(name = "HIVCAUSE")
	private String hivcause;

	@Column(name = "NIF1")
	private String nif1;

	@Column(name = "NIF10")
	private String nif10;

	@Column(name = "NIF11")
	private String nif11;

	@Column(name = "NIF12")
	private String nif12;

	@Column(name = "NIF13")
	private String nif13;

	@Column(name = "NIF14")
	private String nif14;

	@Column(name = "NIF15")
	private String nif15;

	@Column(name = "NIF16")
	private String nif16;

	@Column(name = "NIF17")
	private String nif17;

	@Column(name = "NIF18")
	private String nif18;

	@Column(name = "NIF19")
	private String nif19;

	@Column(name = "NIF2")
	private String nif2;

	@Column(name = "NIF20")
	private String nif20;

	@Column(name = "NIF3")
	private String nif3;

	@Column(name = "NIF4")
	private String nif4;

	@Column(name = "NIF5")
	private String nif5;

	@Column(name = "NIF6")
	private String nif6;

	@Column(name = "NIF7")
	private String nif7;

	@Column(name = "NIF8")
	private String nif8;

	@Column(name = "NIF9")
	private String nif9;

	@Column(name = "COD1")
	private String cod1;

	@Column(name = "COD10")
	private String cod10;

	@Column(name = "COD11")
	private String cod11;

	@Column(name = "COD12")
	private String cod12;

	@Column(name = "COD13")
	private String cod13;

	@Column(name = "COD14")
	private String cod14;

	@Column(name = "COD15")
	private String cod15;

	@Column(name = "COD16")
	private String cod16;

	@Column(name = "COD17")
	private String cod17;

	@Column(name = "COD18")
	private String cod18;

	@Column(name = "COD19")
	private String cod19;

	@Column(name = "COD2")
	private String cod2;

	@Column(name = "COD20")
	private String cod20;

	@Column(name = "COD3")
	private String cod3;

	@Column(name = "COD4")
	private String cod4;

	@Column(name = "COD5")
	private String cod5;

	@Column(name = "COD6")
	private String cod6;

	@Column(name = "COD7")
	private String cod7;

	@Column(name = "COD8")
	private String cod8;

	@Column(name = "COD9")
	private String cod9;

	@Column(name = "NYC_SHIP_DATE")
	private Date nycShipDate;
}
