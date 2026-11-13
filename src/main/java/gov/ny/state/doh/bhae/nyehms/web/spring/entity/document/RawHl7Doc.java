package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An HL7 message as received (table RAW_HL7_DOC); only the columns the HL7 loader's status updates read and write
 * (the loader's insert into this table stays native, see HL7DocumentDAO). Changed by bulk JPQL updates only.
 * <b>DOCUMENT_ID as the key is assumed.</b>
 */
@Entity
@Getter
@Table(name = "RAW_HL7_DOC")
public class RawHl7Doc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	/** The person's MPI as the message gave it (PIDMPI). */
	@Column(name = "PIDMPI")
	private String pidmpi;

	@Column(name = "CCD_MATCH_DOC_ID")
	private Integer ccdMatchDocId;
}
