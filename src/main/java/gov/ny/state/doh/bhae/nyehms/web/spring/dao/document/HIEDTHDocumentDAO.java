package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.date;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.decimal;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.integer;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.text;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.exp.RawHIEDTHDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.exp.StdHIEDTHDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawHieDeathDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdHieDeathDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.RawHieDeathDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.StdHieDeathDocRepository;
import lombok.RequiredArgsConstructor;

/**
 * The HIE death documents: the received records, their standardized records, the screen's document and the
 * scheduler's duplicate and CCD checks (formerly a MyBatis mapper with the same name and methods), through Spring Data
 * repositories over RawHieDeathDoc, StdHieDeathDoc, StdCcdDoc and MpiChange and one JPQL query (the document as the
 * screen's VO); the PL/SQL function MPI_PAIR_EXISTS is called from JPQL.
 * <p>
 * Native (SQL unchanged): {@link #getNewRawHIEDTHList} (whole rows, {@code H.*}: the columns of RAW_HIE_DEATH_DOC
 * are not all known) and {@link #updateDuplicatesReceivedInSameFile} (picks the rows by ROWID). The inserts keep their
 * conversions in the database (the entities' write transformers); SYSDATE stays the database's.
 */
@Repository
@RequiredArgsConstructor
public class HIEDTHDocumentDAO {

	private final NativeStatements statements;
	private final JpqlStatements jpql;
	private final RawHieDeathDocRepository rawDocs;
	private final StdHieDeathDocRepository stdDocs;

	/** Inserts a received HIE death record (the dates of birth and death checked by FN_TO_DATE). */
	@Transactional
	public void insertRawHIEDTHDocData(RawHIEDTHDocVO reddVO) throws DataAccessException {
		RawHIEDTHDocVO vo = reddVO;
		rawDocs.saveAndFlush(RawHieDeathDoc.builder()
				.documentId(vo.getDocumentId())
				.sourceFacility(vo.getSourceFacility())
				.medicalRecordNumber(vo.getMRN())
				.mpiid(vo.getHXMPIID())
				.fname(vo.getFirstName())
				.lname(vo.getLastName())
				.dobText(vo.getBirthDate())
				.dodText(vo.getDeathDate())
				.deathtime(vo.getDeathTime())
				.currentProcessStatus(integer(vo.getCurrentProcessStatus()))
				.receivedDate(date(vo.getReceivedDate()))
				.hieName(vo.getHieName())
				.build());
	}

	/**
	 * Inserts the standardized record of a received record (the service's map).
	 *
	 * @return 1
	 */
	@Transactional
	public int insertHIEDTHFromRAWToSTD(Map<Object, Object> stdDocData) {
		Map<Object, Object> m = stdDocData;
		stdDocs.saveAndFlush(StdHieDeathDoc.builder()
				.documentId(integer(m.get("DOCUMENT_ID")))
				.sourceFacility(text(m.get("SOURCE_FACILITY")))
				.medicalRecordNumber(text(m.get("MEDICAL_RECORD_NUMBER")))
				.mpiid(text(m.get("MPIID")))
				.fname(text(m.get("FNAME")))
				.lname(text(m.get("LNAME")))
				.dobText(text(m.get("DOB_TEXT")))
				.dodText(text(m.get("DOD_TEXT")))
				.deathtime(text(m.get("DEATHTIME")))
				.currentProcessStatus(integer(m.get("CURRENT_PROCESS_STATUS")))
				.receivedDate(date(m.get("RECEIVED_DATE")))
				.hieName(text(m.get("HIE_NAME")))
				.matchCd(text(m.get("MATCH_CD")))
				.matchPass(text(m.get("MATCH_PASS")))
				.matchWeight(decimal(m.get("MATCH_WEIGHT")))
				.matchStateno(text(m.get("MATCH_STATENO")))
				.qsParentDocId(decimal(m.get("QS_PARENT_DOC_ID")))
				.matchedProcessDate(date(m.get("MATCHED_PROCESS_DATE")))
				.build());
		return 1;
	}

	/** The standardized document with its current person match, as the screen reads it (the former aliases). */
	private static final String STD_DOCUMENT = """
			select s.documentId as documentId, s.sourceFacility as sourceFacility, s.medicalRecordNumber as MRN, s.mpiid as mpiid,
			s.fname as firstName, s.lname as lastName, s.dobText as birthDate, s.dodText as deathDate, s.deathtime as deathTime,
			s.currentProcessStatus as currentProcessStatus, s.receivedDate as receivedDate, s.hieName as hieName,
			p.stateno as stateno, p.discardStatus as DISCARD_STATUS, s.matchCd as MATCH_CD, s.matchPass as MATCH_PASS,
			s.matchWeight as MATCH_WEIGHT, s.matchStateno as MATCH_STATENO, s.qsParentDocId as QS_PARENT_DOC_ID,
			s.matchedProcessDate as MATCHED_PROCESS_DATE, s.eharsImportDate as eharsImportDate, s.eharsStatusCd as eharsStatusCd,
			s.nycShipDate as nycShipDate
			from StdHieDeathDoc s
			left join PersonDocAssociation p on p.documentId = s.documentId and p.dissassociationDate is null
			where s.documentId = ?1""";

	/**
	 * The standardized document with its current person match, mapped to the VO by column name as MyBatis did.
	 *
	 * @return the VO, or null if there is no such document; more than one current match fails, as before
	 */
	public StdHIEDTHDocVO getStdHIEDTHDoc(int docId) {
		return jpql.one(STD_DOCUMENT, Rows.bean(StdHIEDTHDocVO.class), docId);
	}

	/**
	 * The waiting received records (status 1) that match a CCD document with a current person (by MPI, or by names and
	 * date of birth under another MPI), each once (the match of the latest CCD document), as whole rows with STATENO,
	 * MATCH_CD 'M' and RWNUM added.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<Object, Object>> getNewRawHIEDTHList() {
		// native: whole rows (H.*), the columns of RAW_HIE_DEATH_DOC are not all mapped
		return (List) statements.list("""
				Select * from (
				                         Select  H.* ,p.STATENO , 'M' MATCH_CD, ROW_NUMBER() OVER (PARTITION BY H.DOCUMENT_ID ORDER BY C.DOCUMENT_ID DESC) AS RWNUM
				                         from raw_hie_death_doc H, std_ccd_doc  C, PERSON_DOC_ASSOCIATION p
				                         where (H.MPIID = C.MPI OR (H.fname = C.fname AND H.lname = C.lname AND H.DOB_Text = C.DOB_text AND H.MPIID != C.MPI))\s
				                         and H.CURRENT_PROCESS_STATUS = 1
				                         and p.DOCUMENT_ID = C.DOCUMENT_ID
				                         and p.DISSASSOCIATION_DATE IS NULL )
				                where RWNUM = 1""", Rows.map());
	}

	/** Marks as duplicates (status 2) the waiting received records of an MPI that is already standardized. */
	@Transactional
	public void updateDuplicatesThatMoveToSTD() {
		rawDocs.markDuplicatesOfStandardized();
	}

	/**
	 * Marks as duplicates (status 2) the open received records (1, 36, 37) of an MPI not yet standardized that repeat an
	 * earlier record (by received date, then document id) with the same names, MPI, dates, record number and facility.
	 */
	@Transactional
	public void updateDuplicatesReceivedInSameFile() {
		// native: the rows are picked by ROWID
		statements.update("""
				UPDATE RAW_HIE_DEATH_DOC\s
				       SET CURRENT_PROCESS_STATUS = 2
				       WHERE ROWID IN\s
				       (
				       SELECT row_id
					   FROM (
						    SELECT ROWID AS row_id, fname, lname, document_id, mpiid ,dob_text, medical_record_number, dod_text, source_facility, RECEIVED_DATE,
						          ROW_NUMBER() over (Partition by  Fname ,Lname ,mpiid, dob_text, medical_record_number, dod_text, source_facility\s
						          ORDER BY received_date, document_id) as rn\s
						          from RAW_HIE_DEATH_DOC r\s
						          WHERE NOT EXISTS (SELECT 1 FROM STD_HIE_DEATH_DOC s WHERE s.mpiid = r.mpiid)
						          )
					    WHERE rn > 1
					     ) and current_process_status In (1,36,37)""");
	}

	/** Records the new MPI pairs found between the received records and the CCD documents (MPI_CHANGE). */
	@Transactional
	public void insertMpisPairInMPICHANGE() {
		rawDocs.insertNewMpiPairs();
	}

	/** Sets the status of the open received records (1, 36, 37) from the CCD documents (36, 37 or 1). */
	@Transactional
	public void updateCPSinRAWComparingCCD() {
		rawDocs.updateStatusFromCcd();
	}
}
