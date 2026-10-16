package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Date;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An NDI cause of death record, standardized (table STD_NDI_CAUSE_DOC); the columns the NDI cause of death document screen reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_NDI_CAUSE_DOC")
public class StdNdiCauseDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "EHARS_UID")
	private String eharsUid;

	@Column(name = "UNIQ_NO")
	private String uniqNo;

	@Column(name = "EHARS_STATENO")
	private String eharsStateno;

	@Column(name = "NYEHMS_STATENO")
	private String nyehmsStateno;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "DOB")
	private String dob;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_MULTIPLE")
	private String raceMultiple;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "RACE")
	private String race;

	@Column(name = "RES_STATE")
	private String resState;

	@Column(name = "DEATH_CERT")
	private String deathCert;

	@Column(name = "DEATH_STATE")
	private String deathState;

	@Column(name = "REPORT_STATE_CD")
	private String reportStateCd;

	@Column(name = "NDI_BIR_DAY")
	private String ndiBirDay;

	@Column(name = "NDI_BIR_MO")
	private String ndiBirMo;

	@Column(name = "NDI_BIR_YEAR")
	private String ndiBirYear;

	@Column(name = "NDI_FIRST")
	private String ndiFirst;

	@Column(name = "NDI_LAST")
	private String ndiLast;

	@Column(name = "NDI_MIDDLE")
	private String ndiMiddle;

	@Column(name = "NDI_RACE")
	private String ndiRace;

	@Column(name = "NDI_SEX")
	private String ndiSex;

	@Column(name = "NDI_SSN")
	private String ndiSsn;

	@Column(name = "NDI_ST_RES")
	private String ndiStRes;

	@Column(name = "NDI_DOD")
	private String ndiDod;

	@Column(name = "NDIFILE_CAT")
	private String ndifileCat;

	@Column(name = "EHARS_DOD")
	private String eharsDod;

	@Column(name = "EHARS_YEAR_DEATH")
	private String eharsYearDeath;

	@Column(name = "NDI_MONTH_DEATH")
	private String ndiMonthDeath;

	@Column(name = "NDI_DAY_DEATH")
	private String ndiDayDeath;

	@Column(name = "NDI_YEAR_DEATH")
	private String ndiYearDeath;

	@Column(name = "DOD_TEXT")
	private String dodText;

	@Column(name = "NDI_FILE")
	private String ndiFile;

	@Column(name = "EHARS_IMPORT_DATE")
	private Date eharsImportDate;

	@Column(name = "CITYNO")
	private String cityno;

	@Column(name = "CAUSE1")
	private String cause1;

	@Column(name = "NATURE_INJURY_FLAG1")
	private String natureInjuryFlag1;

	@Column(name = "CAUSE2")
	private String cause2;

	@Column(name = "NATURE_INJURY_FLAG2")
	private String natureInjuryFlag2;

	@Column(name = "CAUSE3")
	private String cause3;

	@Column(name = "NATURE_INJURY_FLAG3")
	private String natureInjuryFlag3;

	@Column(name = "CAUSE4")
	private String cause4;

	@Column(name = "NATURE_INJURY_FLAG4")
	private String natureInjuryFlag4;

	@Column(name = "CAUSE5")
	private String cause5;

	@Column(name = "NATURE_INJURY_FLAG5")
	private String natureInjuryFlag5;

	@Column(name = "CAUSE6")
	private String cause6;

	@Column(name = "NATURE_INJURY_FLAG6")
	private String natureInjuryFlag6;

	@Column(name = "CAUSE7")
	private String cause7;

	@Column(name = "NATURE_INJURY_FLAG7")
	private String natureInjuryFlag7;

	@Column(name = "CAUSE8")
	private String cause8;

	@Column(name = "NATURE_INJURY_FLAG8")
	private String natureInjuryFlag8;

	@Column(name = "CAUSE9")
	private String cause9;

	@Column(name = "NATURE_INJURY_FLAG9")
	private String natureInjuryFlag9;

	@Column(name = "CAUSE10")
	private String cause10;

	@Column(name = "NATURE_INJURY_FLAG10")
	private String natureInjuryFlag10;

	@Column(name = "CAUSE11")
	private String cause11;

	@Column(name = "NATURE_INJURY_FLAG11")
	private String natureInjuryFlag11;

	@Column(name = "CAUSE12")
	private String cause12;

	@Column(name = "NATURE_INJURY_FLAG12")
	private String natureInjuryFlag12;

	@Column(name = "CAUSE13")
	private String cause13;

	@Column(name = "NATURE_INJURY_FLAG13")
	private String natureInjuryFlag13;

	@Column(name = "CAUSE14")
	private String cause14;

	@Column(name = "NATURE_INJURY_FLAG14")
	private String natureInjuryFlag14;

	@Column(name = "CAUSE15")
	private String cause15;

	@Column(name = "NATURE_INJURY_FLAG15")
	private String natureInjuryFlag15;

	@Column(name = "CAUSE16")
	private String cause16;

	@Column(name = "NATURE_INJURY_FLAG16")
	private String natureInjuryFlag16;

	@Column(name = "CAUSE17")
	private String cause17;

	@Column(name = "NATURE_INJURY_FLAG17")
	private String natureInjuryFlag17;

	@Column(name = "CAUSE18")
	private String cause18;

	@Column(name = "NATURE_INJURY_FLAG18")
	private String natureInjuryFlag18;

	@Column(name = "CAUSE19")
	private String cause19;

	@Column(name = "NATURE_INJURY_FLAG19")
	private String natureInjuryFlag19;

	@Column(name = "CAUSE20")
	private String cause20;

	@Column(name = "NATURE_INJURY_FLAG20")
	private String natureInjuryFlag20;
}
