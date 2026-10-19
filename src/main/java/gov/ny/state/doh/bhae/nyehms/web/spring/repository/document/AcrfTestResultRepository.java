package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.CodedAcrfTestResult;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.CodedAcrfTestResultId;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AcrfTestResult;

/**
 * Spring Data JPA repository for the results of the ACRF test types (CODED_ACRF_TEST_RESULT; replaces
 * ICCRDocumentDAO.getCodedTestTypeResult).
 */
public interface AcrfTestResultRepository extends JpaRepository<CodedAcrfTestResult, CodedAcrfTestResultId> {

	/**
	 * The results a test type can have now (not ended), for the ICCR data entry screen.
	 *
	 * @param testType ACRF_TEST_CODE of the test type
	 * @return the results, in no particular order (as before); empty if there are none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.AcrfTestResult(
					codedAcrfTestResult.acrfResultCode,
					codedAcrfTestResult.acrfResultDesc)
			from CodedAcrfTestResult codedAcrfTestResult
			where codedAcrfTestResult.endDate is null
			and codedAcrfTestResult.acrfTestCode = :testType
			""")
	List<AcrfTestResult> findCurrentResultsOfTest(String testType);
}
