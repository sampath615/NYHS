package gov.ny.state.doh.bhae.nyehms.web.spring.dao.upload;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UploadNYCSurvRejectVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import lombok.RequiredArgsConstructor;

/**
 * The upload screens' statements: the cluster upload checks, the CDC RIDR duplicate flags, the PS confirmed STD upload
 * (staging table to documents and raw records), the NYC surveillance reject upload and the current state number
 * utility (formerly a MyBatis mapper with the same name and methods).
 * <p>
 * JPQL over the entities NyehmsUploadCluster, RawClusterDoc, StgStdConfirmedDoc, DocumentLocator, RawStdConfirmedDoc and
 * NycSurveillanceReject (counts, a bulk delete, insert-selects and an insert of values). Two statements stay native SQL
 * (unchanged, values bound): {@link #identifyCDCRidrDuplicates} and {@link #getCurrentActiveStatenoList}; see each for
 * why. The dates the former SQL set with SYSDATE are the database's SYSDATE ({@code sysdate} in JPQL).
 * <p>
 * <b>Kept as before:</b> the copy of the staged confirmed STDs converts DOB, DATE_OF_DEATH, DIAG_DATE and X900DXDATE with
 * the database's TO_DATE(..., 'MM/DD/YYYY'), so a text that is not such a date fails the whole copy.
 */
@Repository
@RequiredArgsConstructor
public class FileUploadDAO {

	private final JpqlStatements jpql;
	private final NativeStatements statements;

	/** The number of uploaded clusters with this CLUSTER_ID. */
	public int validateClusterId(String clusterId) {
		return jpql.one("select count(*) from NyehmsUploadCluster c where c.clusterId = ?1", Rows.scalar(Integer.class), clusterId);
	}

	/** The number of records of an uploaded cluster that failed (process status 39 or 40). */
	public int errorCountinUpload(int clusterIdPk) {
		return jpql.one("select count(*) from RawClusterDoc r where r.clusterIdPk = ?1 and r.currentProcessStatus in (39, 40)",
				Rows.scalar(Integer.class), clusterIdPk);
	}

	// native: a MERGE on ROWID from an analytic ROW_NUMBER() OVER (...) subquery, with Oracle date arithmetic
	// (TRUNC(date) - TRUNC(SYSDATE)); HQL has no MERGE and no ROWID
	private static final String CDC_RIDR_DUPLICATES = """
				MERGE INTO NYEHMS_OWNER.CDC_RIDR_INFO  out
			                USING (
			                        SELECT *
			                        FROM
			                        (
			                        SELECT a.rowid row_id ,  pdoc.ASSOCIATION_DATE
			                              , ROW_NUMBER() OVER (PARTITION BY pdoc.stateno, a.REPORTING_JURISDICTION, a.STATENO  ORDER BY pdoc.ASSOCIATION_DATE  DESC) rn
			                          FROM CDC_RIDR_INFO  a, RAW_ACRF_RIDR_INFO  b, RAW_ACRF_DOC  c , person_doc_association  pdoc , coded_state st
			                        WHERE
			                            a.FILE_NAME = ?1
			                            and a.RESOLVED_DATE is null
			                            and b.ACRF_DOC_ID = c.document_id
			                            and c.DOCUMENT_SOURCE  in ('A06.18', 'A06.29')
			                            and c.document_id = pdoc.document_id
			                            and pdoc.DISSASSOCIATION_DATE is null
			                            AND ABS(Trunc(pdoc.ASSOCIATION_DATE ) - Trunc(SYSDATE)) <= 90
			                            and upper(st.STATE_DESCRIPTION) = upper(a.REPORTING_JURISDICTION)
			                            and b.STATE_CD =  st.state_code
			                            and b.STATENO  =  a.STATENO
			                            and pdoc.stateno =  PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO (a.NYS_STATENO)
			                        )
			                        WHERE rn = 1
			                ) m
			                ON (out.rowid = m.row_id)
			                WHEN MATCHED THEN
			                UPDATE SET PARENT_DOC_EXISTS = 'Y', PARENT_DOC_CREATE_DT = ASSOCIATION_DATE
			""";

	/**
	 * Marks the unresolved CDC RIDR records of a file whose person has a current RIDR document (A06.18 / A06.29)
	 * associated within 90 days: PARENT_DOC_EXISTS 'Y' and PARENT_DOC_CREATE_DT (the latest association date per
	 * person, jurisdiction and state number).
	 */
	public void identifyCDCRidrDuplicates(String fileName) {
		statements.update(CDC_RIDR_DUPLICATES, NativeStatements.text(fileName));
	}

	/** Deletes all rows of the confirmed STD staging table. */
	public void deleteTempSTDConfirmTableData() throws DataAccessException {
		jpql.update("delete from StgStdConfirmedDoc");
	}

	/** Files the staged confirmed STDs as documents: DOCUMENT_TYPE_CODE 'STD_CONF', IS_ACTIVE 'Y', RECEIVED_DATE SYSDATE. */
	public void moveSTDConfirmDocumentID() throws DataAccessException {
		jpql.update("""
				insert into DocumentLocator (documentId, documentTypeCode, isActive, receivedDate)
				select s.documentId, 'STD_CONF', 'Y', sysdate from StgStdConfirmedDoc s""");
	}

	/** The columns copied from the staging table to RAW_STD_CONFIRMED_DOC (attribute names of both entities). */
	private static final String[] CONFIRMED_COLUMNS = {"documentId", "casekey", "hinserialno", "fname", "mname", "lname",
			"personAddress", "personCity", "personState", "personZip", "personCounty", "personPhone", "dob", "ethnicity", "birthSex",
			"currentGender", "dateOfDeath", "provFname", "provLname", "provPhone", "provAddress", "provCity", "provSt", "provZip",
			"raceWhite", "raceBlack", "raceIndian", "raceAsian", "racePacific", "raceUnknown", "raceOther", "diagDate", "disease",
			"riskSexMale", "riskSexFemale", "riskSexTransgender", "riskSexIdu", "riskSexDrugs", "riskSexExchanged", "riskSexAnonymous",
			"riskSexMsm", "riskSexWithoutCondom", "riskDrugsUsed", "riskDrugUsedList", "riskDrugOtherText", "riskDrugIncarcerated",
			"riskDrugParaphenalia", "riskIdu", "riskOtherText", "fieldnote", "ninehundredtest", "testtechnology", "x900positive",
			"x900dxdate", "current900testresult", "uploadId", "uploadDt", "currentProcessStatus", "filename"};

	/** The staged text dates, converted with TO_DATE(..., 'MM/DD/YYYY'). */
	private static final Set<String> TEXT_DATES = Set.of("dob", "dateOfDeath", "diagDate", "x900dxdate");

	/** The copy of the staging rows to RAW_STD_CONFIRMED_DOC, built from {@link #CONFIRMED_COLUMNS}. */
	private static final String COPY_CONFIRMED;

	static {
		List<String> selected = new ArrayList<>();
		for (String column : CONFIRMED_COLUMNS) {
			selected.add(TEXT_DATES.contains(column) ? "function('TO_DATE' as Timestamp, s." + column + ", 'MM/DD/YYYY')" : "s." + column);
		}
		COPY_CONFIRMED = "insert into RawStdConfirmedDoc (" + String.join(", ", CONFIRMED_COLUMNS) + ")\nselect "
				+ String.join(", ", selected) + "\nfrom StgStdConfirmedDoc s";
	}

	/**
	 * Copies the staged confirmed STDs to RAW_STD_CONFIRMED_DOC, the four text dates converted with the database's
	 * TO_DATE(..., 'MM/DD/YYYY') (a text that is not such a date fails the copy, as before).
	 */
	public void moveSTDConfirmFromTempToRaw() throws DataAccessException {
		jpql.update(COPY_CONFIRMED);
	}

	/**
	 * Inserts an uploaded NYC surveillance reject record, UPLOAD_DATE SYSDATE. A JPQL insert of values (not a persisted
	 * entity), so records with the same DOCUMENT_ID behave as before (the database decides).
	 *
	 * @return the number of rows inserted (1)
	 */
	public int insertNYCSurvRejectDataRow(UploadNYCSurvRejectVO row) {
		return jpql.update("""
				insert into NycSurveillanceReject (documentId, stateno, nycnumber, rejectDocumentId, dispositionCode, statusnotes,
				statusnoncase, statusUpdateDate, stateno2, uploadFilename, uploadUid, uploadDate)
				values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, sysdate)""",
				row.getDOCUMENT_ID(), row.getSTATENO(), row.getNYCNUMBER(), row.getREJECT_DOCUMENT_ID(), row.getDISPOSITION_CODE(),
				row.getSTATUSNOTES(), row.getSTATUSNONCASE(), row.getSTATUS_UPDATE_DATE(), row.getSTATENO2(), row.getUPLOAD_FILENAME(),
				row.getUPLOAD_UID());
	}

	/**
	 * The current state number of each given state number (the PL/SQL function PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO):
	 * maps with ACTUAL_STATENO and CURRENT_STATENO (null values left out), in the given order.
	 * <p>
	 * Native SQL: one {@code SELECT ... FROM DUAL} per state number joined with UNION ALL, as before (selecting a bound
	 * value with no table).
	 *
	 * @param inputMap STATENOS: the state numbers
	 * @throws InvalidDataAccessResourceUsageException for an empty or missing list (the former SQL was empty and failed)
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getCurrentActiveStatenoList(Map<String, List<String>> inputMap) {
		List<String> statenos = inputMap.get("STATENOS");
		if (statenos == null || statenos.isEmpty()) {
			throw new InvalidDataAccessResourceUsageException("No state numbers: the statement would be empty");
		}
		StringBuilder sql = new StringBuilder();
		List<Object> parameters = new ArrayList<>();
		for (Object stateno : statenos) {
			if (!parameters.isEmpty()) {
				sql.append(" Union all ");
			}
			parameters.add(stateno == null ? NativeStatements.text(null) : stateno);
			parameters.add(stateno == null ? NativeStatements.text(null) : stateno);
			sql.append("SELECT  ?").append(parameters.size() - 1).append(" ACTUAL_STATENO, PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO (?")
					.append(parameters.size()).append(") CURRENT_STATENO from DUAL ");
		}
		return (List) statements.list(sql.toString(), Rows.map(), parameters.toArray());
	}
}
