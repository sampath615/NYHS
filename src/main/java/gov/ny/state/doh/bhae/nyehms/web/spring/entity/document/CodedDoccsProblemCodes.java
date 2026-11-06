package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The DOCCS problem codes (table CODED_DOCCS_PROBLEM_CODES); only the columns the DIN DOCCS screen reads.
 * <p>
 * Read-only and read through JPQL projections only. <b>PROBLEM_CD as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_DOCCS_PROBLEM_CODES")
public class CodedDoccsProblemCodes {

	@Id
	@Column(name = "PROBLEM_CD")
	private String problemCd;

	@Column(name = "PROBLEM_DESCRIPTION")
	private String problemDescription;
}
