package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawIccrDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.IccrTestData;

/**
 * Spring Data JPA repository for the ICCR reports as entered with their tests (RAW_ICCR_DOC, RAW_ICCR_TESTS;
 * replaces ICCRDocumentDAO.saveICCRDocumentData, saveICCRTestData and getICCRDocTestData). A report is
 * saved with its tests by saveAndFlush.
 */
public interface RawIccrDocRepository extends JpaRepository<RawIccrDoc, Integer> {

	/**
	 * The tests of an ICCR report with the names of their type and result, for the ICCR document screen.
	 * A result code is looked up without its test type, as before (a code used by several test types
	 * lists the test once per name).
	 *
	 * @param documentId document id of the report
	 * @return the tests; empty if there are none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.IccrTestData(
					format(rawIccrTest.dateOfTest as 'MM/dd/yyyy'),
					codedAcrfTest.acrfTestDesc,
					codedAcrfTestResult.acrfResultDesc,
					rawIccrTest.testResultCopies,
					rawIccrTest.testResultLogs)
			from RawIccrDoc rawIccrDoc
			join rawIccrDoc.iccrTestList rawIccrTest
			left join CodedAcrfTest codedAcrfTest
			on codedAcrfTest.acrfTestCode = rawIccrTest.testType
			left join CodedAcrfTestResult codedAcrfTestResult
			on codedAcrfTestResult.acrfResultCode = rawIccrTest.testResults
			where rawIccrDoc.documentId = :documentId
			""")
	List<IccrTestData> findTestsOfDocument(int documentId);
}
