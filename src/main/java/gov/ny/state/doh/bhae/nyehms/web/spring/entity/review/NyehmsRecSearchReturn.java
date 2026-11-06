package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A row of a CDC record search return file (table NYEHMS_REC_SEARCH_RETURN): what a reporting state returned for a
 * sent row, and how it was resolved (RecordSearchReviewDAO). The columns the JPQL statements use; the native
 * statements (which identify a row by its Oracle ROWID) use some more.
 * <p>
 * Inserted by an HQL insert, changed by JPQL bulk updates and read with projections only, never loaded as an entity,
 * so the key is only for the mapping (the screens use the ROWID): <b>(SENT_FILE_NAME, SENT_FILE_SEQ, STATE_CD) as the
 * key is assumed</b>; a repeated key does not matter. ACRF_DOC_ID and the worker ids are numbers, the returned values
 * text (the former insert bound them as VARCHAR).
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "NYEHMS_REC_SEARCH_RETURN")
@IdClass(NyehmsRecSearchReturnId.class)
public class NyehmsRecSearchReturn {

	@Id
	@Column(name = "SENT_FILE_NAME")
	private String sentFileName;

	@Id
	@Column(name = "SENT_FILE_SEQ")
	private Long sentFileSeq;

	@Id
	@Column(name = "STATE_CD")
	private String stateCd;

	@Column(name = "LNAME_SOUNDEX")
	private String lnameSoundex;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "STATENO")
	private String stateno;

	@Column(name = "CITYNO")
	private String cityno;

	@Column(name = "TRANS_CATEG")
	private String transCateg;

	@Column(name = "HIV_CATEG")
	private String hivCateg;

	@Column(name = "HIV_DX_DT")
	private String hivDxDt;

	@Column(name = "RSH_STATE_CD")
	private String rshStateCd;

	@Column(name = "AIDS_CATEG")
	private String aidsCateg;

	@Column(name = "AIDS_DX_DT")
	private String aidsDxDt;

	@Column(name = "RSA_STATE_CD")
	private String rsaStateCd;

	@Column(name = "DOD")
	private String dod;

	@Column(name = "RAD_STATE_CD")
	private String radStateCd;

	@Column(name = "DEATH_STATE_CD")
	private String deathStateCd;

	@Column(name = "CUR_STATE_CD")
	private String curStateCd;

	@Column(name = "CUR_ADDRESS_DT")
	private String curAddressDt;

	@Column(name = "CD4CNTPCT_VL_RECENT_DT")
	private String cd4cntpctVlRecentDt;

	@Column(name = "CARE_NOLAB_DT")
	private String careNolabDt;

	@Column(name = "RIDRDUPLICATESTATUSWARNINGNAME")
	private String ridrDuplicateStatusWarningName;

	@Column(name = "ACRF_DOC_ID")
	private Long acrfDocId;

	@Column(name = "CREATE_UID")
	private Long createUid;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "DISCARDED_DATE")
	private Timestamp discardedDate;

	@Column(name = "RIDR_SAME_AS")
	private String ridrSameAs;
}
