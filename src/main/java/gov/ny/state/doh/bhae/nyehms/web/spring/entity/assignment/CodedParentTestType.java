package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The lab test groups of the case report forms (table CODED_PARENT_TEST_TYPE). The former SELECT * was read into
 * these columns; the dates are read as text, as they were (getString).
 * <p>
 * Read-only, used in JPQL projections only. <b>PARENT_TEST_TYPE_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_PARENT_TEST_TYPE")
public class CodedParentTestType {

	@Id
	@Column(name = "PARENT_TEST_TYPE_CODE")
	private String parentTestTypeCode;

	@Column(name = "PARENT_TEST_TYPE_DESC")
	private String parentTestTypeDesc;

	@Column(name = "CREATE_DATE")
	private String createDate;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "END_DATE")
	private String endDate;

	@Column(name = "END_ID")
	private String endId;
}
