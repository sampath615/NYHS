package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A standardized HL7 message (table STD_HL7_DOC); only the columns the HL7 loader's status updates, its MPI pair check
 * and its backup delete read and write (the loader's insert into this table stays native, see HL7DocumentDAO).
 * Changed by bulk JPQL updates and deletes only. <b>DOCUMENT_ID as the key is assumed.</b>
 */
@Entity
@Getter
@Table(name = "STD_HL7_DOC")
public class StdHl7Doc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "CCD_MATCH_DOC_ID")
	private Integer ccdMatchDocId;

	@Column(name = "MPI")
	private String mpi;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "DOB_TEXT")
	private String dobText;
}
