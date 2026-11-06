package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.CodedAhiTestType;

/** The test types of the AHI test history (table CODED_AHI_TEST_TYPE). */
public interface CodedAhiTestTypeRepository extends JpaRepository<CodedAhiTestType, String> {

	/** A code with its description (a projection: a code that repeats gives several rows, as before). */
	record TestType(String testTypeCode, String testTypeDesc) {
	}

	/** All codes, in database order. */
	List<TestType> findAllBy();
}
