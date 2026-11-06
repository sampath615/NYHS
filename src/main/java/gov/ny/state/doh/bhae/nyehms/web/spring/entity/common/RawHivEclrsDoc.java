package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A raw eCLRS HIV lab document (table RAW_HIV_ECLRS_DOC); only the columns LoincResultDomainMappingDAO and LaftDetailsDAO read so far
 * (the table is also used by other mappers, which may add their columns here).
 * <p>
 * Read-only, used in counts and JPQL projections only. <b>DOCUMENT_ID as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "RAW_HIV_ECLRS_DOC")
public class RawHivEclrsDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "MSHLABPFI")
	private String mshlabpfi;

	@Column(name = "OBXLOINCCODE")
	private String obxloinccode;

	@Column(name = "MSHSENDINGFACILITYCLIA")
	private String mshsendingfacilityclia;
}
