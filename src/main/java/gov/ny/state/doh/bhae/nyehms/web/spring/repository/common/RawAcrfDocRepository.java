package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RawAcrfDoc;

/** The adult case report forms as entered (table RAW_ACRF_DOC); see the entity for the columns mapped. */
public interface RawAcrfDocRepository extends JpaRepository<RawAcrfDoc, Integer> {

	/**
	 * Sets the HIV testing and treatment history columns of a form (DOCUMENT_ID = documentId and any of the three form
	 * ids).
	 *
	 * @return the number of rows changed
	 */
	@Modifying
	@Query("""
			update RawAcrfDoc a set a.mainTthSource = ?1, a.ptInterviewDate = ?2, a.everHadPos = ?3, a.firstHivTestEver = ?4,
					a.everHadNeg = ?5, a.lastNegDate = ?6, a.respHivTest2yrs = ?7, a.countHivTest2yrs = ?8, a.ptArv6Mo = ?9,
					a.ptArvName = ?10, a.ptArvStartDate = ?11, a.ptArvEndDate = ?12
			where a.documentId = ?13 and (a.acrfDocId = ?14 or a.prfDocId = ?15 or a.airsDocId = ?16)
			""")
	int updateTestTreatmentHistory(String mainTthSource, String ptInterviewDate, String everHadPos, String firstHivTestEver,
			String everHadNeg, String lastNegDate, String respHivTest2yrs, Integer countHivTest2yrs, String ptArv6Mo, String ptArvName,
			String ptArvStartDate, String ptArvEndDate, Integer documentId, Integer acrfDocId, Integer prfDocId, Integer airsDocId);
}
