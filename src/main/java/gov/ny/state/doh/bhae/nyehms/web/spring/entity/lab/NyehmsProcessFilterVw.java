package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The lab proficiency / process filter conditions by lab (view NYEHMS_PROCESS_FILTER_VW): one row per condition of a
 * filter of a PFI ("-999" = the default conditions).
 * <p>
 * Read-only and read through JPQL projections only (a key that repeats does not matter then). <b>CONDITION_ID as the
 * key is assumed.</b> PFI is text (the scheduler passes it as text).
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_PROCESS_FILTER_VW")
public class NyehmsProcessFilterVw {

	@Id
	@Column(name = "CONDITION_ID")
	private Long conditionId;

	@Column(name = "PFI")
	private String pfi;

	@Column(name = "FILTER_NUMBER")
	private Long filterNumber;

	@Column(name = "FILTER_TYPE")
	private Long filterType;

	@Column(name = "FIELD_NAME")
	private String fieldName;

	@Column(name = "CONDITION")
	private String condition;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "OPERATOR")
	private String operator;

	@Column(name = "FILTER_FLAG_ACTIVE")
	private String filterFlagActive;
}
