package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The process statuses of the lab documents (table CODED_LAB_PROCESSES_STATUS).
 * <p>
 * Read-only. <b>PROCESSES_STATUS_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_LAB_PROCESSES_STATUS")
public class CodedLabProcessesStatus {

	@Id
	@Column(name = "PROCESSES_STATUS_CODE")
	private Long processesStatusCode;

	@Column(name = "PROCESSES_STATUS_DESC")
	private String processesStatusDesc;
}
