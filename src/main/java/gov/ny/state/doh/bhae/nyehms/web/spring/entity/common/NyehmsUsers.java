package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The application's workers (table NYEHMS_USERS): names, profile, training dates and the e-mail notification flags
 * (one column per notification, 'T' = to, 'C' = copy).
 * <p>
 * Read through projections only (a key that repeats does not matter then) and changed by bulk JPQL updates only
 * (SearchManageProfileDAO, NyehmsWorkerRegionsViewDAO), never loaded and saved as an entity: a worker can have
 * several rows (the ended ones have an END_DATE). <b>WORKER_ID as the key is assumed.</b>
 * <p>
 * BUREAU_START_DATE and POSITION_START_DATE are mapped as text because the former statements wrote and read them as
 * VARCHAR (if they are DATE columns, Oracle converts the text as before).
 */
@Entity
@Getter
@Table(name = "NYEHMS_USERS")
public class NyehmsUsers {

	@Id
	@Column(name = "WORKER_ID")
	private Long workerId;

	@Column(name = "LEGACY_UID")
	private String legacyUid;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "MIDDLE_NAME")
	private String middleName;

	@Column(name = "EMAIL_ADDR")
	private String emailAddr;

	@Column(name = "END_DATE")
	private Timestamp endDate;

	@Column(name = "IS_LOCKED")
	private String isLocked;

	@Column(name = "IS_TRAINER")
	private String isTrainer;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	/** Set at each login (CommonDAO.setLoginTimeStamp, a bulk update). */
	@Column(name = "LAST_LOGIN_DATE")
	private Timestamp lastLoginDate;

	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name = "VPN")
	private String vpn;

	@Column(name = "VPN_APPROVE_DATE")
	private Timestamp vpnApproveDate;

	@Column(name = "TELECOMMUTE")
	private String telecommute;

	@Column(name = "TELECOMMUTE_TRNG_DATE")
	private Timestamp telecommuteTrngDate;

	@Column(name = "LAST_CONF_SECURITY_TRNG_DATE")
	private Timestamp lastConfSecurityTrngDate;

	@Column(name = "BUREAU_START_DATE")
	private String bureauStartDate;

	@Column(name = "POSITION_START_DATE")
	private String positionStartDate;

	@Column(name = "PC_ACCESS_AT_LOCATION")
	private String pcAccessAtLocation;

	@Column(name = "BUREAU_NAME")
	private String bureauName;

	@Column(name = "REMOTE_TERMINAL_ACCESS")
	private String remoteTerminalAccess;

	@Column(name = "TELECOMMUTE_TRNG_ATTEST_REC")
	private String telecommuteTrngAttestRec;

	@Column(name = "CONF_SECURITY_TRNG_ATTEST_REC")
	private String confSecurityTrngAttestRec;

	@Column(name = "TELECOMMUTE_TRNG_BY")
	private String telecommuteTrngBy;

	@Column(name = "CONF_SECURITY_TRNG_BY")
	private String confSecurityTrngBy;

	@Column(name = "ADMIN_COUNTY_REGION_CD")
	private String adminCountyRegionCd;

	// the e-mail notification flags (the notification screen's list, SearchManageProfileDAO.NOTIFICATION_COLUMNS)

	@Column(name = "ACRF_RISK")
	private String acrfRisk;

	@Column(name = "AHI")
	private String ahi;

	@Column(name = "AHI_NOTIFICATION")
	private String ahiNotification;

	@Column(name = "AIRS_AGENCY")
	private String airsAgency;

	@Column(name = "AIRS_UPLOAD_ERR")
	private String airsUploadErr;

	@Column(name = "ASSIGNMENT")
	private String assignment;

	@Column(name = "ASSIGNMENT_AHI")
	private String assignmentAhi;

	@Column(name = "COMATCH")
	private String comatch;

	@Column(name = "EHARS_NOTIFICATION")
	private String eharsNotification;

	@Column(name = "EPRF_OOC_SUMMARY")
	private String eprfOocSummary;

	@Column(name = "FILE_REMINDER")
	private String fileReminder;

	@Column(name = "HIT_NOTIFICATION")
	private String hitNotification;

	@Column(name = "MONTHLY_LOINC")
	private String monthlyLoinc;

	@Column(name = "NIGHTLY_JOB")
	private String nightlyJob;

	@Column(name = "PP_ERR")
	private String ppErr;

	@Column(name = "PP_OOC_SUB")
	private String ppOocSub;

	@Column(name = "PRF_REVIEW")
	private String prfReview;

	@Column(name = "PS_MSG_DEAD_DISPO")
	private String psMsgDeadDispo;

	@Column(name = "PS_MESSAGE")
	private String psMessage;

	@Column(name = "PS_MSG_UPLOAD")
	private String psMsgUpload;

	@Column(name = "PS_NYC_PROV_FAC")
	private String psNycProvFac;

	@Column(name = "RAS_DOC_ERR")
	private String rasDocErr;

	@Column(name = "RAS_NOTIFICATION")
	private String rasNotification;

	@Column(name = "RECORD_SEARCH_UPLOAD")
	private String recordSearchUpload;

	@Column(name = "RES_LINKING_ERR")
	private String resLinkingErr;

	@Column(name = "WEEKLY_LOINC")
	private String weeklyLoinc;

	@Column(name = "LAFT_RESOLVE_COMMENTS")
	private String laftResolveComments;
}
