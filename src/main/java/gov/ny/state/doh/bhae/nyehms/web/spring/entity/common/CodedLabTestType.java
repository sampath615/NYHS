package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A lab test type (table CODED_LAB_TEST_TYPE): code, description, test type and unit of the result; the columns
 * LoincResultDomainMappingDAO reads (the table is also used by LabReportsDAO and SchedulerJobsDAO).
 * <p>
 * Read-only. <b>TEST_TYPE_CD as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_LAB_TEST_TYPE")
public class CodedLabTestType {

	@Id
	@Column(name = "TEST_TYPE_CD")
	private String testTypeCd;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "TEST_TYPE")
	private String testType;

	@Column(name = "UNIT_OF_RESULT")
	private String unitOfResult;
}
