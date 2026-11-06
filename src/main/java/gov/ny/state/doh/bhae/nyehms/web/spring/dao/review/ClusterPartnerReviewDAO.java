package gov.ny.state.doh.bhae.nyehms.web.spring.dao.review;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterPartnerDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PartnerInvestigationVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.cluster.CodedPartClustRefDataRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.cluster.CodedPartnerRefDataRepository;
import lombok.RequiredArgsConstructor;

/**
 * The cluster partner review: the partners loaded for review (NYEHMS_CLUSTER_PARTNERS), their standardized documents
 * (STD_CLUSTER_PARTNERS), the PS message reasons of the cluster records and the resolve date of a cluster (formerly a
 * MyBatis mapper with the same name and methods).
 * <ul>
 * <li>Code lists: Spring Data repositories with projections.</li>
 * <li>The other reads and the updates: JPQL over the entities NyehmsClusterPartners, StdClusterPartners,
 * PersonDocAssociation, NyehmsStdMessageSent, DocumentLocator, StdClusterDoc, NyehmsUploadCluster,
 * DocumentPersonAttr and ErPersonClustersStage. The dates the former SQL set with SYSDATE are the database's SYSDATE
 * ({@code sysdate} in JPQL).</li>
 * <li>Native SQL where JPQL cannot express the statement: the random pick of a partner to review
 * ({@code dbms_random.value} order with ROWNUM) and the MERGE of the match summary.</li>
 * <li>The procedure ins_clust_partner_attr_tables: {@link NativeStatements#call}.</li>
 * </ul>
 * <b>Kept as before:</b> {@link #getPartnerInvestigationDetails} never fills FIELDNOTE (the former SQL selected
 * {@code to_char(FIELDNOTE)} without an alias, so the column matched no property).
 */
@Repository
@RequiredArgsConstructor
public class ClusterPartnerReviewDAO {

	private final JpqlStatements jpql;
	private final NativeStatements statements;
	private final CodedPartnerRefDataRepository partnerRefData;
	private final CodedPartClustRefDataRepository psMessageReasons;

	/**
	 * The clusters (CLUSTER_ID) with partners waiting for review (status 17) whose cluster is not resolved, by
	 * CLUSTER_ID; unless STATUS_CODE is 'ALL' or '' only the partners with that match code (a null STATUS_CODE finds
	 * none, as before).
	 *
	 * @param inputParameterMap STATUS_CODE
	 */
	public List<String> getClusterPartnerIdList(Map<String, String> inputParameterMap) {
		Object statusCode = inputParameterMap.get("STATUS_CODE");
		List<Object> parameters = new ArrayList<>();
		StringBuilder query = new StringBuilder("select distinct p.clusterId from NyehmsClusterPartners p where p.currentProcessStatus = 17");
		if (!ognlEquals(statusCode, "ALL") && !ognlEquals(statusCode, "")) {
			parameters.add(text(statusCode));
			query.append(" and p.fluxMatchCd = ?1");
		}
		query.append(" and exists (select 1 from NyehmsUploadCluster upload where upload.clusterId = p.clusterId and upload.resolvedDate is null)"
				+ " order by p.clusterId asc");
		return jpql.list(query.toString(), Rows.scalar(String.class), parameters.toArray());
	}

	/**
	 * The number of partners waiting for review (status 17), optionally of one match code and one cluster ('ALL', ''
	 * and null mean all).
	 *
	 * @param inOutParamaterMap filterCriteriaForStatusCode, clusterPartnerId
	 */
	public int getTotalClusterParterDocCountToReview(Map<Object, Object> inOutParamaterMap) {
		List<Object> parameters = new ArrayList<>();
		StringBuilder query = new StringBuilder("select count(p.partnerId) from NyehmsClusterPartners p where p.currentProcessStatus in (17)");
		Object statusCode = inOutParamaterMap.get("filterCriteriaForStatusCode");
		if (isSetAndNotAll(statusCode)) {
			parameters.add(text(statusCode));
			query.append(" and p.fluxMatchCd = ?").append(parameters.size());
		}
		Object clusterId = inOutParamaterMap.get("clusterPartnerId");
		if (isSetAndNotAll(clusterId)) {
			parameters.add(text(clusterId));
			query.append(" and p.clusterId = ?").append(parameters.size());
		}
		return jpql.one(query.toString(), Rows.scalar(Integer.class), parameters.toArray());
	}

	// native: a random row (ORDER BY dbms_random.value, then ROWNUM < 2); the map keys and value types are those of the
	// other match-review statements the generic review code reads
	private static final String DOCUMENTS_TO_REVIEW = """
			select DOCUMENT_ID,  MATCH_CD, FUZZY_CLUSTER_ID,  CLUSTER_ID,  POT_STATENO, DOC_TYPE from (select PARTNER_ID DOCUMENT_ID, FLUX_MATCH_CD MATCH_CD,
			'' FUZZY_CLUSTER_ID, PARTNER_CLUSTER_ID CLUSTER_ID, FLUX_POT_STATENO POT_STATENO, 'CLUST_PART' DOC_TYPE,
			dbms_random.value
			                       rand_order from nyehms_cluster_partners where  current_process_Status in (17)
			""";

	/**
	 * One partner waiting for review, chosen at random: a map with DOCUMENT_ID (the partner id), MATCH_CD, CLUSTER_ID
	 * (the partner cluster id), POT_STATENO and DOC_TYPE 'CLUST_PART' (FUZZY_CLUSTER_ID is always NULL, so absent), or
	 * null if there is none.
	 *
	 * @param inOutParamaterMap filterCriteriaForStatusCode, clusterPartnerId ('ALL', '' and null mean all)
	 */
	public Map<String, Object> getClusterPartnerDocumentsToReview(Map<Object, Object> inOutParamaterMap) {
		List<Object> parameters = new ArrayList<>();
		StringBuilder sql = new StringBuilder(DOCUMENTS_TO_REVIEW);
		Object statusCode = inOutParamaterMap.get("filterCriteriaForStatusCode");
		if (isSetAndNotAll(statusCode)) {
			parameters.add(statusCode);
			sql.append(" AND flux_MATCH_CD =  ?").append(parameters.size());
		}
		Object clusterId = inOutParamaterMap.get("clusterPartnerId");
		if (isSetAndNotAll(clusterId)) {
			parameters.add(clusterId);
			sql.append(" AND CLUSTER_ID =  ?").append(parameters.size());
		}
		sql.append(" ORDER BY rand_order ) where ROWNUM < 2");
		return statements.one(sql.toString(), Rows.map(), parameters.toArray());
	}

	/**
	 * Discards (status 14, DISCARD_DATE = SYSDATE) all partners waiting for review (status 17) of the partner cluster
	 * of a partner.
	 *
	 * @param inOutParameterMap documentId (the partner id), workerId
	 * @return the number of partners discarded
	 */
	public int discardClericalReviewDocument(Map<String, Object> inOutParameterMap) {
		return jpql.update("""
				update NyehmsClusterPartners p set p.currentProcessStatus = 14, p.discardDate = sysdate, p.discardBy = ?1
				where p.currentProcessStatus = 17
				and p.partnerClusterId = (select p2.partnerClusterId from NyehmsClusterPartners p2 where p2.partnerId = ?2)""",
				longValue(inOutParameterMap.get("workerId")), longValue(inOutParameterMap.get("documentId")));
	}

	/**
	 * Discards (status 14, REVIEW_DATE = SYSDATE) a document waiting for review (status 17) in
	 * NYEHMS_SASDB_OWNER.ER_PERSON_CLUSTERS_STAGE. Its only call is commented out.
	 *
	 * @param inOutParameterMap workerId, documentId
	 * @return the number of rows changed
	 */
	public int discardClericalReviewDocumentInStageTable(Map<String, Object> inOutParameterMap) {
		// kept as before: the former statement declared parameterType "long", so MyBatis bound both values with its
		// Long type handler and failed (ClassCastException) for any value that is not a Long, e.g. the int worker id
		Long workerId = (Long) inOutParameterMap.get("workerId");
		Long documentId = (Long) inOutParameterMap.get("documentId");
		return jpql.update("""
				update ErPersonClustersStage s set s.currentProcessStatus = 14, s.reviewUid = ?1, s.reviewDate = sysdate
				where s.currentProcessStatus = 17 and s.documentId = ?2""", workerId, documentId);
	}

	// native: a MERGE of the table with itself (every partner of the same partner cluster and cluster as the given
	// partner); kept as a MERGE so its result (and its failure for several matching source rows) stays the same
	private static final String MATCH_SUMMARY = """
			 merge into nyehms_cluster_partners b using  nyehms_cluster_partners a
			on (
			    a.partner_cluster_id = b.partner_cluster_id
			    and a.cluster_id = b.cluster_id
			    and a.partner_id = ?1
			    )
			when matched
			    then
			    update set b.current_process_status = ?2,
			    b.FLUX_MATCHED_STATENO = ?3, b.FLUX_match_cd = ?4,
			    b.REVIEW_UID = ?5, b.REVIEW_DATE = sysdate
			     where b.current_process_Status = 17 and b.FLUX_match_Cd = ?6
			""";

	/**
	 * Sets the review result (status, matched state number, new match code, reviewer, REVIEW_DATE = SYSDATE) on all
	 * partners waiting for review (status 17, match code ACTUAL_MATCH_CD) of the partner cluster of a partner.
	 *
	 * @param inOutParameterMap p_document_id, CURRENT_PROCESS_STATUS, stateno, UPDATED_MATCH_CD, p_worker_id,
	 *        ACTUAL_MATCH_CD
	 * @return the number of partners changed
	 */
	public int updateClusterPartnerMatchSummary(Map<String, Object> inOutParameterMap) {
		return statements.update(MATCH_SUMMARY, inOutParameterMap.get("p_document_id"), inOutParameterMap.get("CURRENT_PROCESS_STATUS"),
				NativeStatements.text(inOutParameterMap.get("stateno")), inOutParameterMap.get("UPDATED_MATCH_CD"),
				inOutParameterMap.get("p_worker_id"), inOutParameterMap.get("ACTUAL_MATCH_CD"));
	}

	/**
	 * Builds the cluster attribute tables of a reviewed partner and, if asked, triggers the PS message (procedure
	 * ins_clust_partner_attr_tables); puts p_code_out (Integer) and p_err_msg into the map.
	 *
	 * @param inOutParameterMap p_document_id, stateno, p_worker_id, p_triggerPsMessage
	 */
	public void processClusterparterDocumentToClusterTables(Map<String, Object> inOutParameterMap) {
		List<Object> results = statements.call("ins_clust_partner_attr_tables",
				param(inOutParameterMap.get("p_document_id"), Integer.class),
				param(inOutParameterMap.get("stateno"), String.class),
				param(inOutParameterMap.get("p_worker_id"), Integer.class),
				out(Integer.class),
				out(String.class),
				param(inOutParameterMap.get("p_triggerPsMessage"), String.class));
		inOutParameterMap.put("p_code_out", results.get(3));
		inOutParameterMap.put("p_err_msg", results.get(4));
	}

	/**
	 * A standardized cluster partner document with its associated state number (several associations fail, as before) and whether a PS message was
	 * sent for it (psMessageExist Yes / No), or null.
	 */
	public ClusterPartnerDocVO getClusterPartnerDocData(int documentId) {
		return jpql.one("""
				select pdoc.stateno as STATENO, std.documentId as DOCUMENT_ID, std.din as DIN, std.personCity as PERSON_CITY, std.fname as FNAME,
				std.mname as MNAME, std.lname as LNAME, std.homePhone as HOME_PHONE, std.cellPhone as CELL_PHONE, std.clusterId as CLUSTER_ID,
				std.personCounty as PERSON_COUNTY, std.ethnicity as ETHNICITY, std.medrecno as MEDRECNO, std.birthSex as BIRTH_SEX,
				std.personAddress as PERSON_ADDRESS, std.personState as PERSON_STATE, std.personZip as PERSON_ZIP, std.nysid as NYSID,
				std.partnerId as PARTNER_ID, std.dobText as DATE_OF_BIRTH, format(std.dateOfDeath as 'MM/dd/yyyy') as DATE_OF_DEATH,
				std.birthCountry as BIRTH_COUNTRY, std.gender as GENDER, std.rsnNoPsMsg as RSN_NO_PS_MSG, std.rsnPsMsg as RSN_PS_MSG,
				std.raceWhite as RACE_WHITE, std.raceBlack as RACE_BLACK, std.raceIndian as RACE_INDIAN, std.raceAsian as RACE_ASIAN,
				std.racePacific as RACE_PACIFIC, std.raceOther as RACE_OTHER, std.raceUnknown as RACE_UNKNOWN,
				std.partnerAssistanceStatus as PARTNER_ASSISTANCE_STATUS,
				case when exists (select 1 from NyehmsStdMessageSent m where m.followUpDocId = std.documentId) then 'Yes' else 'No' end
				as psMessageExist
				from StdClusterPartners std left join PersonDocAssociation pdoc on std.documentId = pdoc.documentId
				where std.documentId = ?1""", Rows.bean(ClusterPartnerDocVO.class), (long) documentId);
	}

	/**
	 * A cluster partner loaded for review, read as a cluster partner document (the review columns renamed as the
	 * standardized ones), or null.
	 *
	 * @param inOutParameterMap PARTNER_ID
	 */
	public ClusterPartnerDocVO getClusterPartnerReviewDocData(Map<String, Object> inOutParameterMap) {
		return jpql.one("""
				select p.din as DIN, p.city as PERSON_CITY, p.firstName as FNAME, p.middleName as MNAME, p.lastName as LNAME,
				p.homePhone as HOME_PHONE, p.cellPhone as CELL_PHONE, p.clusterId as CLUSTER_ID, p.county as PERSON_COUNTY,
				p.ethnicity as ETHNICITY, p.medrecno as MEDRECNO, p.birthSex as BIRTH_SEX, p.street as PERSON_ADDRESS, p.state as PERSON_STATE,
				p.zipcode as PERSON_ZIP, p.nysid as NYSID, p.partnerId as PARTNER_ID, format(p.dateOfBirth as 'MM/dd/yyyy') as DATE_OF_BIRTH,
				format(p.dateOfDeath as 'MM/dd/yyyy') as DATE_OF_DEATH, p.raceWhite as RACE_WHITE, p.raceBlack as RACE_BLACK,
				p.raceAmerind as RACE_INDIAN, p.raceAsian as RACE_ASIAN, p.racePacific as RACE_PACIFIC, p.raceOther as RACE_OTHER,
				p.raceUnknown as RACE_UNKNOWN, p.partnerClusterId as PARTNER_CLUSTER_ID, p.countryofbirth as BIRTH_COUNTRY,
				p.currentgender as GENDER, p.partnerassistancestatus as PARTNER_ASSISTANCE_STATUS
				from NyehmsClusterPartners p where p.partnerId = ?1""",
				Rows.bean(ClusterPartnerDocVO.class), longValue(inOutParameterMap.get("PARTNER_ID")));
	}

	private static final String INVESTIGATION_COLUMNS = """
			select p.partnerType as PARTNER_TYPE, p.partnerassistancestatus as PARTNERASSISTANCESTATUS, p.partnerkey as PARTNERKEY,
			p.partnerFlag as PARTNER_FLAG, p.partnerFrnumber as PARTNER_FRNUMBER, p.partInactiveFlag as PART_INACTIVE_FLAG,
			format(p.partInactiveDate as 'MM/dd/yyyy') as PART_INACTIVE_DATE, p.disnumber as DISNUMBER, p.legacyPartnerId as LEGACY_PARTNER_ID,
			format(p.lastHivNegDate as 'MM/dd/yyyy') as LAST_HIV_NEG_DATE, p.opStateno as OP_STATENO, p.opCdesscasekey as OP_CDESSCASEKEY,
			p.opFrnumber as OP_FRNUMBER, p.opFieldrecordkey as OP_FIELDRECORDKEY, p.opInactiveFlag as OP_INACTIVE_FLAG,
			format(p.opDateClosed as 'MM/dd/yyyy') as OP_DATE_CLOSED, format(p.dateinitiated as 'MM/dd/yyyy') as DATEINITIATED,
			format(p.interviewdate as 'MM/dd/yyyy') as INTERVIEWDATE, p.currentonprep as CURRENTONPREP, p.confirmpatientprep as CONFIRMPATIENTPREP,
			p.notificationdatepind as NOTIFICATIONDATEPIND, format(p.notificationdate as 'MM/dd/yyyy') as NOTIFICATIONDATE,
			p.condition1 as CONDITION_1, p.disposition1 as DISPOSITION1, format(p.dispositiondate1 as 'MM/dd/yyyy') as DISPOSITIONDATE1,
			p.condition2 as CONDITION_2, p.disposition2 as DISPOSITION2, format(p.dispositiondate2 as 'MM/dd/yyyy') as DISPOSITIONDATE2,
			p.condition3 as CONDITION_3, p.disposition3 as DISPOSITION3, format(p.dispositiondate3 as 'MM/dd/yyyy') as DISPOSITIONDATE3,
			p.condition4 as CONDITION_4, p.disposition4 as DISPOSITION4, format(p.dispositiondate4 as 'MM/dd/yyyy') as DISPOSITIONDATE4
			from NyehmsClusterPartners p where\s""";

	/**
	 * The investigation details of the partners of a standardized document (DOCUMENT_ID set and not '') or else of a
	 * partner cluster of a cluster. FIELDNOTE stays empty (kept as before, see the class comment).
	 *
	 * @param inputParameterMap DOCUMENT_ID, or PARTNER_CLUSTER_ID and CLUSTER_ID
	 */
	public List<PartnerInvestigationVO> getPartnerInvestigationDetails(Map<String, Object> inputParameterMap) {
		Object documentId = inputParameterMap.get("DOCUMENT_ID");
		if (documentId != null && !ognlEquals(documentId, "")) {
			return jpql.list(INVESTIGATION_COLUMNS + "p.documentId = ?1", Rows.bean(PartnerInvestigationVO.class), longValue(documentId));
		}
		return jpql.list(INVESTIGATION_COLUMNS + "p.partnerClusterId = ?1 and p.clusterId = ?2", Rows.bean(PartnerInvestigationVO.class),
				text(inputParameterMap.get("PARTNER_CLUSTER_ID")), text(inputParameterMap.get("CLUSTER_ID")));
	}

	/** The distinct FR numbers of the partners of a standardized document: maps with PARTNER_FRNUMBER (a null one as null). */
	public List<Map<String, String>> getFRNumbers(Integer document_ID) {
		List<Map<String, String>> rows = new ArrayList<>();
		for (String number : jpql.list("select distinct p.partnerFrnumber from NyehmsClusterPartners p where p.documentId = ?1",
				Rows.scalar(String.class), document_ID == null ? null : document_ID.longValue())) {
			rows.add(textMap("PARTNER_FRNUMBER", number));
		}
		return rows;
	}

	/** The partner code lists: maps with REF_COLUMN, LOOKUP_NAME, LOOKUP_CD, by list and name. */
	public List<Map<String, Object>> getClusterPartnerRefData() {
		List<Map<String, Object>> rows = new ArrayList<>();
		for (CodedPartnerRefDataRepository.RefData code : partnerRefData.findAllByOrderByRefColumnAscLookupNameAsc()) {
			Map<String, Object> row = new HashMap<>();
			putIfSet(row, "REF_COLUMN", code.refColumn());
			putIfSet(row, "LOOKUP_NAME", code.lookupName());
			putIfSet(row, "LOOKUP_CD", code.lookupCd());
			rows.add(row.isEmpty() ? null : row);
		}
		return rows;
	}

	/**
	 * The standardized cluster partner documents that have a cluster record without a no-PS-message reason, are not
	 * associated with a person and have no PS message: maps with documentType, documentId (a BigDecimal), FIRST_NAME,
	 * MIDDLE_NAME, LAST_NAME and DOB (FN_TO_CHAR of DOB_TEXT).
	 */
	public List<Map<String, Object>> getClusterNoPSRecords() {
		return jpql.list("""
				select doc.documentTypeCode as documentType, cast(std.documentId as BigDecimal) as documentId, std.fname as FIRST_NAME,
				std.mname as MIDDLE_NAME, std.lname as LAST_NAME, function('FN_TO_CHAR', std.dobText, 'MM/DD/YYYY') as DOB
				from StdClusterPartners std, DocumentLocator doc
				where doc.documentId = std.documentId
				and exists (select 1 from StdClusterDoc c where c.demoDocumentId = std.documentId and c.rsnNoPsMsg is null)
				and not exists (select 1 from PersonDocAssociation a where a.documentId = std.documentId)
				and not exists (select 1 from NyehmsStdMessageSent m where m.followUpDocId = std.documentId)""", Rows.map());
	}

	/** The partner id of a standardized cluster partner document, or null. */
	public Integer getClusterPartnerId(long documentId) {
		return jpql.one("select std.partnerId from StdClusterPartners std where std.documentId = ?1", Rows.scalar(Integer.class), documentId);
	}

	/**
	 * Sets the no-PS-message reason (REVIEW_DATE = SYSDATE) of the cluster records of a demographic document that have
	 * none.
	 *
	 * @param inOutParameterMap p_rejectReason, p_otherReason, p_worker_id, p_document_id
	 * @return the number of cluster records changed
	 */
	public int rejectClusterPSMessage(Map<String, Object> inOutParameterMap) {
		return jpql.update("""
				update StdClusterDoc c set c.rsnNoPsMsg = ?1, c.otherReason = ?2, c.reviewId = ?3, c.reviewDate = sysdate
				where c.demoDocumentId = ?4 and c.rsnNoPsMsg is null""",
				text(inOutParameterMap.get("p_rejectReason")), text(inOutParameterMap.get("p_otherReason")),
				longValue(inOutParameterMap.get("p_worker_id")), longValue(inOutParameterMap.get("p_document_id")));
	}

	/**
	 * Sets the PS message reason (REVIEW_DATE = SYSDATE) of the cluster records of a demographic document that have
	 * none.
	 *
	 * @param inOutParameterMap p_reason, p_otherReason, p_worker_id, p_cluster_documentId
	 * @return the number of cluster records changed
	 */
	public int updateClusterPSMessageReasonDetails(Map<String, Object> inOutParameterMap) {
		return jpql.update("""
				update StdClusterDoc c set c.rsnPsMsg = ?1, c.otherReason = ?2, c.reviewId = ?3, c.reviewDate = sysdate
				where c.demoDocumentId = ?4 and c.rsnPsMsg is null""",
				text(inOutParameterMap.get("p_reason")), text(inOutParameterMap.get("p_otherReason")),
				longValue(inOutParameterMap.get("p_worker_id")), longValue(inOutParameterMap.get("p_cluster_documentId")));
	}

	/**
	 * The PS message reasons of one kind: maps with LOOKUP_NAME, LOOKUP_CD, by name (none for a null or '' kind: the
	 * former "REF_COLUMN = NULL").
	 *
	 * @param inputParameterMap REASON_TYPE
	 */
	public List<Map<String, String>> getPSMessageReasons(Map<String, String> inputParameterMap) {
		Object reasonType = inputParameterMap.get("REASON_TYPE");
		List<Map<String, String>> rows = new ArrayList<>();
		if (reasonType == null || "".equals(reasonType)) {
			return rows;
		}
		for (CodedPartClustRefDataRepository.Reason reason : psMessageReasons.findByRefColumnOrderByLookupNameAsc(reasonType.toString())) {
			Map<String, String> row = new HashMap<>();
			putIfSet(row, "LOOKUP_NAME", reason.lookupName());
			putIfSet(row, "LOOKUP_CD", reason.lookupCd());
			rows.add(row.isEmpty() ? null : row);
		}
		return rows;
	}

	/**
	 * Changes the address of a standardized cluster partner document.
	 *
	 * @param inputOutputParameters p_new_address, p_new_city, p_new_county_fips, p_new_state, p_new_zip,
	 *        p_cluster_documentId
	 * @return the number of documents changed
	 */
	public int updateClusterPartnerDocumentAddress(Map<String, Object> inputOutputParameters) {
		Map<String, Object> map = inputOutputParameters;
		return jpql.update("""
				update StdClusterPartners std set std.personAddress = ?1, std.personCity = ?2, std.personCounty = ?3, std.personState = ?4,
				std.personZip = ?5 where std.documentId = ?6""",
				text(map.get("p_new_address")), text(map.get("p_new_city")), text(map.get("p_new_county_fips")), text(map.get("p_new_state")),
				text(map.get("p_new_zip")), longValue(map.get("p_cluster_documentId")));
	}

	/**
	 * Changes the address of a document's person attributes.
	 *
	 * @param inOutParameterMap p_new_address, p_new_city, p_new_county_fips, p_new_state, p_new_zip, p_cluster_documentId
	 */
	public void updateDocumentAddressInAttrTable(Map<String, Object> inOutParameterMap) {
		Map<String, Object> map = inOutParameterMap;
		Long documentId = longValue(map.get("p_cluster_documentId"));
		jpql.update("""
				update DocumentPersonAttr a set a.address = ?1, a.addressCity = ?2, a.countyFips = ?3, a.addressState = ?4, a.addressZipcode = ?5
				where a.documentId = ?6""",
				text(map.get("p_new_address")), text(map.get("p_new_city")), text(map.get("p_new_county_fips")), text(map.get("p_new_state")),
				text(map.get("p_new_zip")), documentId == null ? null : Math.toIntExact(documentId));
	}

	/**
	 * Whether the cluster record of a demographic document (optionally of one cluster: p_clusterId set and not '') has a
	 * PS message reason and a no-PS-message reason: a map with RSN_PS_MSG and RSN_NO_PS_MSG ('Y' / 'N'), or null.
	 *
	 * @param inOutParameterMap p_docId, p_clusterId
	 */
	public Map<String, String> getPsMessageDetails(Map<String, Object> inOutParameterMap) {
		List<Object> parameters = new ArrayList<>();
		parameters.add(longValue(inOutParameterMap.get("p_docId")));
		StringBuilder query = new StringBuilder("""
				select case when c.rsnPsMsg is null then 'N' else 'Y' end as RSN_PS_MSG,
				case when c.rsnNoPsMsg is null then 'N' else 'Y' end as RSN_NO_PS_MSG
				from StdClusterDoc c where c.demoDocumentId = ?1""");
		Object clusterId = inOutParameterMap.get("p_clusterId");
		if (clusterId != null && !ognlEquals(clusterId, "")) {
			Long clusterIdPk = longValue(clusterId);
			parameters.add(clusterIdPk == null ? null : Math.toIntExact(clusterIdPk));
			query.append(" and c.clusterIdPk = ?2");
		}
		Map<String, Object> row = jpql.one(query.toString(), Rows.map(), parameters.toArray());
		if (row == null) {
			return null;
		}
		Map<String, String> details = new HashMap<>();
		row.forEach((key, value) -> details.put(key, String.valueOf(value)));
		return details;
	}

	/**
	 * Sets the resolve date (to_date(p_date, 'MM/DD/YYYY'), as before) and the resolving worker of a cluster.
	 *
	 * @param inOutParameterMap p_workerId, p_date, p_clusterPartnerId (the cluster id)
	 * @return the number of clusters changed
	 */
	public int updateClusterFile(Map<String, Object> inOutParameterMap) {
		return jpql.update("""
				update NyehmsUploadCluster c set c.resolvedBy = ?1, c.resolvedDate = function('to_date', ?2, 'MM/DD/YYYY')
				where c.clusterId = ?3""",
				longValue(inOutParameterMap.get("p_workerId")), text(inOutParameterMap.get("p_date")),
				text(inOutParameterMap.get("p_clusterPartnerId")));
	}

	// ---------------------------------------------------------------------------------------------------------------

	/** The former OGNL test {@code x != null and x != '' and x != 'ALL'}. */
	private static boolean isSetAndNotAll(Object value) {
		return value != null && !ognlEquals(value, "") && !ognlEquals(value, "ALL");
	}

	/**
	 * OGNL's {@code value == 'text'} of the former dynamic SQL: null equals only null, a text compares as text; any other
	 * value (a number) is compared as a number with the text read as a number ('' is 0; a text that is no number
	 * fails, as OGNL did).
	 */
	static boolean ognlEquals(Object value, String literal) {
		if (value == null) {
			return false;
		}
		if (value instanceof String || value.equals(literal)) {
			return value.equals(literal);
		}
		if (value instanceof Number number) {
			double other = literal.isEmpty() ? 0.0 : Double.parseDouble(literal);
			return number.doubleValue() == other;
		}
		return value.toString().equals(literal);
	}

	/** An IN parameter bound with its value's type (as MyBatis bound a map value); a null with the declared type. */
	private static CallParam param(Object value, Class<?> nullType) {
		return in(value, value == null ? nullType : value.getClass());
	}

	private static Map<String, String> textMap(String key, String value) {
		if (value == null) {
			return null;
		}
		Map<String, String> map = new HashMap<>();
		map.put(key, value);
		return map;
	}

	private static <V> void putIfSet(Map<String, V> map, String key, V value) {
		if (value != null) {
			map.put(key, value);
		}
	}
}
