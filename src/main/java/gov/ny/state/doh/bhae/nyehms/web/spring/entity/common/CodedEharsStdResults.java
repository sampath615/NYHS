package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An eHARS standard test result (table CODED_EHARS_STD_RESULTS): code, result and interpretation; the columns
 * LoincResultDomainMappingDAO reads (the table is also used by SchedulerJobsDAO).
 * <p>
 * Read-only. <b>RESULTS_STD_CD as the key is assumed</b>; it is read as text (its column type is not known).
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_EHARS_STD_RESULTS")
public class CodedEharsStdResults {

	@Id
	@Column(name = "RESULTS_STD_CD")
	private String resultsStdCd;

	@Column(name = "RESULTS_STD")
	private String resultsStd;

	@Column(name = "RESULT_INTERPRETATION")
	private String resultInterpretation;
}
