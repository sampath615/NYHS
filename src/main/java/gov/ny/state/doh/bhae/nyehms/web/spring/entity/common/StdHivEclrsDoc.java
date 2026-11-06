package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A standardized eCLRS HIV lab document (table STD_HIV_ECLRS_DOC); only the columns LaftDetailsDAO reads so far (the
 * table is also used by other mappers, which may add their columns here).
 * <p>
 * Read-only, used in JPQL projections only. <b>DOCUMENT_ID as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_HIV_ECLRS_DOC")
public class StdHivEclrsDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Long currentProcessStatus;
}
