package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A pediatric case report form as standardized (table STD_PCRF_DOC): only the date it was imported to eHARS, which the
 * PCRF screen shows.
 * <p>
 * Read-only, used in JPQL projections only. <b>DOCUMENT_ID as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_PCRF_DOC")
public class StdPcrfDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "EHARS_IMPORT_DATE")
	private Timestamp eharsImportDate;
}
