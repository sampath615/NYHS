package gov.ny.state.doh.bhae.nyehms.web.spring.entity.scheduler;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An HIV lab interpretation code (table CODED_HIV_DOC_INTERPRETATIONS) with the test type, eHARS result and description
 * it stands for, read by the eCLRS HIV loader.
 * <p>
 * Read-only, through JPQL projections only. <b>INTERPRET_CD as the key is assumed</b> (a code that repeats would not
 * matter: no entity is loaded). The columns are text attributes: the former statement compared them with the text the
 * scheduler passes and read MAX(INTERPRET_CD) with getString.
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_HIV_DOC_INTERPRETATIONS")
public class CodedHivDocInterpretations {

	@Id
	@Column(name = "INTERPRET_CD")
	private String interpretCd;

	@Column(name = "TEST_TYPE_CD")
	private String testTypeCd;

	@Column(name = "EHARS_RESULT_CD")
	private String eharsResultCd;

	@Column(name = "INTERPRET_DESC")
	private String interpretDesc;
}
