package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The error codes of a validation error record (table NYEHMS_PROCESS_ERRORS_DETAIL) and their bypass flag; only the
 * columns the LAFT statements read and write (also written by the scheduler and PL/SQL).
 * <p>
 * Read through JPQL projections and changed by bulk JPQL updates only. <b>The key (ERROR_ID, ERROR_CODE) is
 * assumed.</b>
 */
@Entity
@Getter
@Table(name = "NYEHMS_PROCESS_ERRORS_DETAIL")
@IdClass(NyehmsProcessErrorsDetailId.class)
public class NyehmsProcessErrorsDetail {

	@Id
	@Column(name = "ERROR_ID")
	private Long errorId;

	@Id
	@Column(name = "ERROR_CODE")
	private Long errorCode;

	@Column(name = "BYPASS_ERROR")
	private String bypassError;
}
