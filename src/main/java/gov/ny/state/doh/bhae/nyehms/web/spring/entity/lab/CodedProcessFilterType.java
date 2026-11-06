package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The lab proficiency filter types (table CODED_PROCESS_FILTER_TYPE).
 * <p>
 * Read-only. <b>FILTER_TYPE_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_PROCESS_FILTER_TYPE")
public class CodedProcessFilterType {

	@Id
	@Column(name = "FILTER_TYPE_CODE")
	private Long filterTypeCode;

	@Column(name = "FILTER_TYPE_DESC")
	private String filterTypeDesc;
}
