package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The validation error codes with their category, type and description (table CODED_PROCESS_ERRORS).
 * <p>
 * Read-only. <b>ERROR_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_PROCESS_ERRORS")
public class CodedProcessErrors {

	@Id
	@Column(name = "ERROR_CODE")
	private Long errorCode;

	@Column(name = "ERROR_CATEGORY")
	private String errorCategory;

	@Column(name = "ERROR_TYPE")
	private String errorType;

	@Column(name = "ERROR_DESC")
	private String errorDesc;
}
