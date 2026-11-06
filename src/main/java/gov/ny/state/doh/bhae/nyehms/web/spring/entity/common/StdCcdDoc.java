package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The standardized CCD documents (table STD_CCD_DOC); only the columns the HIE death checks read (the person's MPI,
 * names, date of birth and the dataflux match code).
 * <p>
 * Read-only and read in JPQL conditions only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document). MPI is
 * read as text.
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_CCD_DOC")
public class StdCcdDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "MPI")
	private String mpi;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "FLUX_MATCH_CD")
	private String fluxMatchCd;
}
