package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.CodedLabTestType;

/** The lab test types (table CODED_LAB_TEST_TYPE). */
public interface CodedLabTestTypeRepository extends JpaRepository<CodedLabTestType, String> {

	/** The test types of a description and test type that have no unit (the parameters must not be null). */
	List<CodedLabTestType> findByDescriptionAndTestTypeAndUnitOfResultIsNull(String description, String testType);

	/** The test types of a description, test type and unit (the parameters must not be null). */
	List<CodedLabTestType> findByDescriptionAndTestTypeAndUnitOfResult(String description, String testType, String unitOfResult);

	/** All test types (each once), sorted. */
	@Query("select distinct c.testType from CodedLabTestType c order by c.testType")
	List<String> findTestTypes();

	/** The descriptions of a test type (each once), sorted. */
	@Query("select distinct c.description from CodedLabTestType c where c.testType = :testType order by c.description")
	List<String> findDescriptions(String testType);

	/** All units (each once, a null unit as a null element), sorted. */
	@Query("select distinct c.unitOfResult from CodedLabTestType c order by c.unitOfResult")
	List<String> findUnits();

	/** The units of a description and test type (each once, a null unit as a null element), sorted. */
	@Query("""
			select distinct c.unitOfResult from CodedLabTestType c
			where c.description = :description and c.testType = :testType order by c.unitOfResult
			""")
	List<String> findUnits(String description, String testType);
}
