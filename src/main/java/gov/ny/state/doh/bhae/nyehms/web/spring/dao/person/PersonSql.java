package gov.ny.state.doh.bhae.nyehms.web.spring.dao.person;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.number;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.text;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.timestamp;

import java.util.Map;

import org.hibernate.type.StandardBasicTypes;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.Param;

/**
 * The SQL of the {@link PersonDAO} statements that stay native, taken unchanged from the former MyBatis mapper
 * ({@code #{...}} replaced by {@code ?1, ?2, ...}; generated from the mapper XML); why each one stays native is said at
 * its DAO method. The {@code ...Args} methods give the values bound to a statement, in order, typed as the mapper's
 * {@code jdbcType} bound a null ({@code VARCHAR} and {@code CLOB} a text, {@code DECIMAL} a number, {@code DATE} a date,
 * {@code TIMESTAMP} a timestamp); a value that is not null is bound as it is, as MyBatis bound it by its Java type (a
 * {@code java.util.Date} with {@code jdbcType=DATE} as its day only, as MyBatis' DateOnlyTypeHandler did). A value without
 * a jdbcType is bound as it is. A statement with one simple parameter binds it as {@code ?1} wherever the mapper named it.
 */
final class PersonSql {

	private PersonSql() {
	}

	/**
	 * A value bound as {@code #{x,jdbcType=DATE}}: a {@code java.util.Date} (the class itself) as its day only (MyBatis'
	 * DateOnlyTypeHandler, setDate), any other value as it is (a Timestamp keeps its time), null as a DATE null.
	 */
	static Param date(Object value) {
		if (value != null && value.getClass() == java.util.Date.class) {
			return NativeStatements.param(new java.sql.Date(((java.util.Date) value).getTime()), StandardBasicTypes.DATE);
		}
		return NativeStatements.param(value, StandardBasicTypes.DATE);
	}

	/** The SQL of {@code getHIVDocumentDetails}, as the former mapper had it (3 bound values). */
	static final String HIV_DOCUMENT_DETAILS = """
			select DOCUMENT_ID documentId, DOCUMENT_TYPE_CODE documentType, TO_CHAR(RECIEVE_DT, 'MM/DD/YYYY') recievedDate,PROVIDER_NAME providerName,PROVIDER_ADDRESS providerAddress, ASSOCIATION_DATE associationDate, FIRST_NAME firstName, LAST_NAME lastName, DATE_OF_BIRTH dateOfBirth, SSN ssn, ETHNICITY ethnicity, RACE race, ADDRESS address, SURV_ASSIGNMENT_ID assignmentId, SURV_STATUS assignmentStatus,   TO_CHAR(COLLECTION_DATE, 'MM/DD/YYYY') collectionDate, SENT_MESSAGE_FLAG sentMessageFlag, RETURN_MESSAGE_FLAG returnMessageFlag, ACCESSION_NUMBER accessionNumber, TEST_RESULT testResult, ASSOCIATION_STATUS_CODE associationStatus, RAS_DOC_ID rasDocId, SIERRA_FLAG sierraFlag, DISCARD_STATUS discardStatus, nyc_reject_doc_ind nycRejectDocInd, DEMO_DOCUMENT_ID demoDocId, LAFT_FOLLOWUP_EXISTS laftFollowupExists from  NYEHMS_DOC_TIMELINE_HIV_VW where
			STATENO = ?1
			and doc_type_code IN (SELECT *
			                           FROM TABLE(get_user_permissions(?2, ?3)))
			order by COLLECTION_DATE desc nulls last
			""";

	/** The values of {@link #HIV_DOCUMENT_DETAILS}, in order. */
	static Object[] getHIVDocumentDetailsArgs(Map<?, ?> map) {
		return new Object[] {
				map.get("p_stateNo"), map.get("p_module_id"), map.get("p_worker_id")
		};
	}

	/** The SQL of {@code getDocumentDetailsFromStageTable}, as the former mapper had it (2 bound values). */
	static final String DOCUMENT_DETAILS_FROM_STAGE = """
			select
			stage.DOCUMENT_ID documentId,
			?1 documentType,
			PERSON_FIRST_NAME firstName,
			PERSON_LAST_NAME lastName,
			PERSON_DOB_TEXT dateOfBirth,
			DOD_TEXT dateOfDeath,
			FILE_TYPE_CD fileType,
			SSN ssn,
			DECODE( race_indian , 1 , 'IND/' ,NULL) || DECODE(  race_black , 1 , 'BLK/' ,NULL) || DECODE(  race_asian , 1 , 'ASI/' ,NULL) || DECODE(  race_pacific , 1 , 'PAC/' ,NULL) || DECODE(  race_white , 1 , 'WHT/' ,NULL) || DECODE(  race_multiple , 1 , 'MUL/' ,NULL) || DECODE(  race_other , 1 , 'OTH/' ,NULL) || DECODE(  race_unknown , 1 , 'UNK' ,NULL) race,
			e.ETHNICITY_DESCRIPTION ethnicity,
			PERSON_ADDRESS || ', ' || PERSON_CITY || ', ' || PERSON_STATE || ', ' || PERSON_ZIP address,
			CALC_COLLECTION_DATE collectionDate,
			DF_PROV_ADDRESS_STND || ', ' || DF_PROV_CITY_STND || ', ' || DF_PROV_STATE_STND || ', ' || DF_PROV_ZIP_STND providerAddress,
			DF_PROV_FNAME_STND providerName,
			case when stage.RECEIVED_DATE is null then TO_CHAR(doc.RECEIVED_DATE, 'MM/DD/YYYY')
			    else to_char(stage.RECEIVED_DATE, 'MM/DD/YYYY') end recievedDate
			from
			nyehms_Sasdb_owner.er_person_clusters_stage stage, document_locator doc, CODED_ETHNICITY e where  doc.document_id = stage.document_id and stage.DOCUMENT_ID = ?2  AND stage.ETHNICITY_CODE = e.ETHNICITY_CODE (+)
			""";

	/** The values of {@link #DOCUMENT_DETAILS_FROM_STAGE}, in order. */
	static Object[] getDocumentDetailsFromStageTableArgs(Map<?, ?> map) {
		return new Object[] {
				map.get("documentType"), map.get("documentId")
		};
	}

	/** The SQL of {@code getNBSDocumentDetails}, as the former mapper had it (1 bound values). */
	static final String NBS_DOCUMENT_DETAILS = """
				select
			nbs.DOCUMENT_ID documentId,
			'NBS' documentType,
			INFANT_FNAME  firstName,
			INFANT_LNAME  lastName,
			TO_CHAR(BIRTH_DATE, 'MM/DD/YYYY')  dateOfBirth,
			'' dateOfDeath,
			'' ssn,
			DECODE( race_indian , 1 , 'IND/' ,NULL) || DECODE(  race_black , 1 , 'BLK/' ,NULL) || DECODE(  race_asian , 1 , 'ASI/' ,NULL) ||  DECODE(  race_white , 1 , 'WHT/' ,NULL) ||  DECODE(  race_other , 1 , 'OTH/' ,NULL)  race,
			e.ETHNICITY_DESCRIPTION ethnicity,
			MOTHER_ADDRESS || ', ' || MOTHER_CITY  || ', ' || MOTHER_STATE  || ', ' || MOTHER_ZIP  address,
			'' collectionDate,
			'' providerAddress,
			'' providerName,
			'' recievedDate
			from
			STD_NBS_DOC nbs, document_locator doc, CODED_ETHNICITY e where  doc.document_id = nbs.document_id AND nbs.ETHNICITY = e.ETHNICITY_CODE (+)
			
			
			 and nbs.DOCUMENT_ID = ?1
			""";

	/** The SQL of {@code getClusterDocumentDetailsFromStageTable}, as the former mapper had it (1 bound values). */
	static final String CLUSTER_DOCUMENT_DETAILS_FROM_STAGE = """
				select
			stage.PARTNER_ID  documentId,
			'CLUST_PART' documentType,
			FIRST_NAME  firstName,
			LAST_NAME  lastName,
			TO_CHAR(DATE_OF_BIRTH, 'MM/DD/YYYY')  dateOfBirth,
			TO_CHAR(DATE_OF_DEATH , 'MM/DD/YYYY') dateOfDeath,
			'' ssn,
			DECODE( RACE_AMERIND , 1 , 'IND/' ,NULL) || DECODE(  race_black , 1 , 'BLK/' ,NULL) || DECODE(  race_asian , 1 , 'ASI/' ,NULL) || DECODE(  race_pacific , 1 , 'PAC/' ,NULL) || DECODE(  race_white , 1 , 'WHT/' ,NULL)  || DECODE(  race_other , 1 , 'OTH/' ,NULL) || DECODE(  race_unknown , 1 , 'UNK' ,NULL) race,
			e.ETHNICITY_DESCRIPTION ethnicity,
			STREET  || ', ' || CITY  || ', ' || STATE  || ', ' || ZIPCODE  address,
			'' providerAddress,
			'' providerName
			
			from
			nyehms_cluster_partners stage, CODED_ETHNICITY e where  stage.partner_id = ?1  AND stage.ETHNICITY = e.ETHNICITY_CODE (+)
			""";

	/** The SQL of {@code getLabDocumentDetails}, as the former mapper had it (2 bound values). */
	static final String LAB_DOCUMENT_DETAILS = """
			select DOCUMENT_ID documentId,   ?1 as documentType, FNAME firstName, LNAME lastName, DOB_TEXT dateOfBirth, SSN ssn, e.ETHNICITY_DESCRIPTION ethnicity,  PERSON_ADDRESS || ', ' || PERSON_CITY || ', ' || PERSON_STATE || ', ' || PERSON_ZIP address,  PROV_ADDRESS || ', ' || PROV_CITY || ', ' || PROV_STATE || ', ' || PROV_ZIP
			          AS providerAddress, pkg_trk_common.fn_get_fullname (PROV_LNAME,
			                                       PROV_FNAME,
			                                       PROV_MNAME,
			                                       NULL) as providerName, TO_CHAR(DATE_RECEIVED,'MM/DD/YYYY') recievedDate , TO_CHAR(COLLECTION_DATE,'MM/DD/YYYY') collectionDate, ACCESSION_NUMBER accessionNumber, TEST_RESULT  testResult, DECODE( race_indian , 1 , 'IND/' ,NULL) || DECODE(  race_black , 1 , 'BLK/' ,NULL) || DECODE(  race_asian , 1 , 'ASI/' ,NULL) || DECODE(  race_pacific , 1 , 'PAC/' ,NULL) || DECODE(  race_white , 1 , 'WHT/' ,NULL) || DECODE(  race_multiple , 1 , 'MUL/' ,NULL) || DECODE(  race_other , 1 , 'OTH/' ,NULL) || DECODE(  race_unknown , 1 , 'UNK' ,NULL) race   from  ${tableName} , CODED_ETHNICITY e where  DOCUMENT_ID = ?2  AND ETHNICITY = e.ETHNICITY_CODE (+)
			""";

	/** The values of {@link #LAB_DOCUMENT_DETAILS}, in order. */
	static Object[] getLabDocumentDetailsArgs(Map<?, ?> map) {
		return new Object[] {
				map.get("documentType"), map.get("documentId")
		};
	}

	/** The SQL of {@code getStatenoClusterAssociation}, as the former mapper had it (1 bound values). */
	static final String STATENO_CLUSTER_ASSOCIATION = """
			WITH stateno_in_clust AS
			(
			    SELECT nucl.cluster_id, nucl.cluster_type, pdoc.stateno curr_stateno, pdoc.document_id, scdoc.nyehms_stateno_at_upload org_stateno, nucl.cluster_identify_date clusterDate
			    FROM person_doc_association pdoc, std_cluster_doc scdoc, nyehms_upload_cluster nucl
			    WHERE pdoc.stateno = ?1
			    AND pdoc.dissassociation_date IS NULL
			    AND pdoc.document_id = scdoc.document_id
			    AND scdoc.cluster_id_pk = nucl.cluster_id_pk
			), stateno_not_in_clust AS
			(
			    SELECT ucl.cluster_id, ucl.cluster_type, ucl.cluster_identify_date clusterDate
			    FROM nyehms_upload_cluster ucl, nyehms_upload_cluster_files uclf
			    WHERE ucl.cluster_id_pk = uclf.cluster_id_pk  AND ucl.cluster_type != 'BHAE_TS' AND NOT EXISTS (SELECT 1 FROM stateno_in_clust a WHERE a.cluster_id = ucl.cluster_id)
			)
			SELECT Distinct curr_stateno, cluster_id clusterId, cluster_type clusterType, clusterDate
			FROM stateno_in_clust
			UNION
			SELECT Distinct NULL curr_stateno, cluster_id clusterId, cluster_type clusterType, clusterDate
			FROM stateno_not_in_clust
			""";
}
