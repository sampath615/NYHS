package gov.ny.state.doh.bhae.nyehms.web.spring.entity.scheduler;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An error of the PID update process of an eCLRS result (table PID_UPDATE_PROCESS_ERRORS): the OBX key, the error, its
 * flag, the document type and when it was created and processed.
 * <p>
 * Inserted by a JPQL insert, read through projections and changed by bulk JPQL updates only. <b>OBXOBSERVATIONRESULTKEY
 * as the key is assumed</b> (a key that repeats would not matter: no entity is loaded).
 */
@Entity
@Getter
@Table(name = "PID_UPDATE_PROCESS_ERRORS")
public class PidUpdateProcessErrors {

	@Id
	@Column(name = "OBXOBSERVATIONRESULTKEY")
	private Long obxobservationresultkey;

	@Column(name = "ERROR_DESC")
	private String errorDesc;

	@Column(name = "ACTIVE_FLAG")
	private String activeFlag;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "PROCESS_DATE")
	private Timestamp processDate;

	@Column(name = "DOCUMENT_TYPE_CODE")
	private String documentTypeCode;
}
