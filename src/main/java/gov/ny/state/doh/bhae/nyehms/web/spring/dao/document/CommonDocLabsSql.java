package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

/**
 * The SQL of the {@link CommonDocLabsDAO} statements that stay native, taken unchanged from the former MyBatis mapper
 * ({@code #{...}} replaced by {@code ?1}; the former {@code <choose>} of the message details is the {@code %s}
 * slot, filled with the condition the document type chooses); why each one stays native is said at its DAO method.
 */
final class CommonDocLabsSql {

	private CommonDocLabsSql() {
	}

	/** The SQL of {@code getDocumentTypes}, as the former mapper had it (2 bound values). */
	static final String DOCUMENT_TYPES = """
			SELECT   DOCUMENT_TYPE_CODE
			  FROM     CODED_DOCUMENT_TYPE WHERE SURV_ASSIGN_FLAG = 'Y'

			  and DOCUMENT_TYPE_CODE IN (SELECT *
			                             FROM TABLE(get_user_permissions(?1, ?2)))

			   ORDER BY DOCUMENT_TYPE_CODE
			""";

	/** The SQL of {@code getPSMessageDetails}, as the former mapper had it; the three {@code %s} are the document condition (1 bound value). */
	static final String PS_MESSAGE_DETAILS = """
			with pslaftcommentsdata as (
			         select DOCUMENT_ID,  case when laft_reasons is not null then LAFT_REASONS || ', ' || LAFT_COMMENTS else null end as COMMENTS,  LAFT_REVIEW_BY CREATE_ID, LAFT_REVIEW_DATE CREATE_DATE from NYEHMS_STD_MESSAGE_SENT where
			         %s
			     union
			      select  comments.DOCUMENT_ID, comments.COMMENTS, comments.CREATE_ID, comments.CREATE_DATE  from PS_MESSAGE_COMMENTS comments, NYEHMS_STD_MESSAGE_SENT se  where se.document_id = comments.document_id and
			         se.%s
			)
			  SELECT se.DOCUMENT_ID,
			       se.TRIGGER_MSG_TYPE,
			       (SELECT PS_MSG_TYPE_DESCRIPTION FROM CODED_PS_MSG_TYPE WHERE PS_MSG_TYPE =se.TRIGGER_MSG_TYPE) TRIGGER_MSG_DESC,
			       se.TRIGGER_DOC_ID,
			       se.MESSAGE_ENTERED_DATE MESSAGE_ENTERED_DATE_SENT,
			       se.MESSAGE_RECEIVED_DATE MESSAGE_RECEIVED_DATE_SENT,
			       se.CDESSCASEKEY CDESSCASEKEY_SENT,
			       se.CDESSDISMISSED,
			       se.PROCESS_IND,
			       se.LAFT_REASONS,
			       se.LAFT_COMMENTS,
			       case when REVIEW_UID is not null then trim(usr.FULL_NAME) end REVIEW_UID,
			       TO_CHAR(se.REVIEW_DATE, 'MM/DD/YYYY') REVIEW_DATE,
			       se.REVIEW_COMMENTS,
			       se.CREATE_ID TRIGGER_BY,
			       re.*,
			       up_msg.DISPOSITION PS_DATA_DISPOSITION_CODE,
			       TO_CHAR(up_msg.DISPOSITIONDATE, 'MM/DD/YYYY') DISPOSITION_DATE ,
			       up_msg.LASTUPDATEDBYNAME UPDATED_BY,
			        up_msg.LASTUPDATEDATE UPDATED_DATE,
			       dispo.SENT_DISPOSITION_DESC DISPOSITION_DESC,
			       up_msg.FIELDNOTE FIELDNOTES,
			       comments.COMMENTS MESSAGE_COMMENTS,
			       case when comments.CREATE_ID is not null then trim(comments_usr.FULL_NAME) end COMMENTS_CREATE_ID,
			       TO_CHAR(comments.CREATE_DATE, 'MM/DD/YYYY HH:MI:SS AM') COMMENTS_CREATE_DATE

			       FROM NYEHMS_STD_MESSAGE_RETURN re, NYEHMS_STD_MESSAGE_SENT se, NYEHMS_USERS_VW usr, NYEHMS_USERS_VW comments_usr, sti_ps_data  up_msg, CODED_SENT_MESSAGE_DISPOSITION dispo,  pslaftcommentsdata comments
			       WHERE se.DOCUMENT_ID = re.DOCUMENT_ID(+) and se.DOCUMENT_ID = up_msg.DOCUMENT_ID(+) and up_msg.DISPOSITION =  dispo.SENT_DISPOSITION_CODE (+)
			       and se.REVIEW_UID=usr.worker_Id(+)
			       and comments.CREATE_ID=comments_usr.worker_Id(+)
			       and se.document_id = comments.document_id (+)
			       AND NVL (se.PROCESS_IND, 'XX') NOT IN ('N', 'E')
			       and se.%s
				 order by se.DOCUMENT_ID asc,  comments.CREATE_DATE asc
			""";

	/** The SQL of {@code getNycLabDoc}, as the former mapper had it (1 bound value). */
	static final String NYC_LAB_DOC = """
			select
			r1.PERSON_ADDRESS1 || ' ' || r1.PERSON_ADDRESS2 rawAddress,
			  PDOC.STATENO,
			  PDOC.DISCARD_STATUS,
			std.DOCUMENT_ID,
			std.NYEHMS_STATENO,
			std.EHARS_STATENO,
			std.CITYNO,
			std.EHARS_UID,
			std.RECEIVED_DATE,
			std.FIRST_NAME,
			std.MIDDLE_NAME,
			std.LAST_NAME,
			std.SSN,
			std.NYSID,
			std.BIRTH_SEX,
			std.DOB_TEXT,
			std.BIRTH_COUNTRY,
			std.VITAL_STATUS,
			std.DOD_TEXT,
			std.DEATH_STATE,
			std.CURRENT_GENDER,
			std.ETHNICITY,
			std.RACE_WHITE,
			std.RACE_BLACK,
			std.RACE_ASIAN,
			std.RACE_INDIAN,
			std.RACE_PACIFIC,
			std.RACE_UNKNOWN,
			std.ADDRESS_TYPE_CODE,
			std.ADDRESS_DATE_TEXT,
			std.PERSON_ADDRESS1 || ' ' || std.PERSON_ADDRESS2 PERSON_ADDRESS1,
			std.PERSON_CITY,
			std.PERSON_STATE,
			std.PERSON_COUNTY,
			std.PERSON_ZIP,
			std.PERSON_COUNTRY,
			std.EVENT_CODE,
			std.FACILITY_UID,
			std.SWM,
			std.SWF,
			std.IDU,
			std.SEX_PARTNER_IDU,
			std.SEX_PARTNER_MSM,
			std.SEX_PARTNER_HEMOPHILIAC,
			std.SEX_PARTNER_TRANSFUSION,
			std.SEX_PARTNER_TRANSPLANT,
			std.SEX_PARTNER_HIV,
			std.RECV_BLOOD_PROD,
			std.FIRST_TRANSFUSION_DATE_TEXT,
			std.LAST_TRANSFUSION_DATE_TEXT,
			std.RECV_TRANSPLANT,
			std.HEALTHCARE_WORKER,
			std.OCCUPATION,
			std.RISK_OTHER,
			std.XLAB_HIV_DATE_TEXT,
			std.COLLECTION_DATE_TEXT,
			std.LAB_TEST_NAME,
			std.LAB_RESULT,
			std.LAB_UNITS,
			std.INTERPRETATION,
			std.DOCTOR_DIAGNOSIS,
			std.DOCTOR_DIAGNOSIS_DATE_TEXT,
			std.CANDLUNG_DATE_TEXT,
			std.CANDE_DATE_TEXT,
			std.CERVCANC_DATE_TEXT,
			std.COCCI_DATE_TEXT,
			std.CRYPTOCO_DATE_TEXT,
			std.CRYPTOSP_DATE_TEXT,
			std.CMV_DATE_TEXT,
			std.CMVRET_DATE_TEXT,
			std.HIVNCEPH_DATE_TEXT,
			std.HERPES_DATE_TEXT,
			std.HISTO_DATE_TEXT,
			std.ISO_DATE_TEXT,
			std.KS_DATE_TEXT,
			std.BURKL_DATE_TEXT,
			std.LYMPHIM_DATE_TEXT,
			std.LYMPHB_DATE_TEXT,
			std.MYCOAV_DATE_TEXT,
			std.MYCOPULM_DATE_TEXT,
			std.MYCODISM_DATE_TEXT,
			std.MYCOTH_DATE_TEXT,
			std.PCP_DATE_TEXT,
			std.PNEUM_DATE_TEXT,
			std.PML_DATE_TEXT,
			std.SALMNELA_DATE_TEXT,
			std.TOXO_DATE_TEXT,
			std.WASTING_DATE_TEXT,
			std.MATCH_CODE,
			std.CURRENT_PROCESS_STATUS,
			std.CREATE_DATE,
			std.CREATE_UID,
			std.EHARS_STATUS_CD,
			std.EHARS_IMPORT_DATE
			,D.PARENT_DOCUMENT_ID
		FROM RAW_NYC_EHARS_LAB_DOC r1
		          INNER JOIN STD_NYC_EHARS_LAB_DOC std
		             ON (std.document_id = r1.document_id and  r1.DOCUMENT_ID=?1)
		             INNER JOIN document_locator D
		             ON r1.document_id=d.document_id
		          LEFT OUTER JOIN PERSON_DOC_ASSOCIATION PDOC
		             ON (r1.document_id = PDOC.document_id
		                 AND PDOC.dissassociation_date IS NULL)
			""";
}
