package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The code lists of the Black Box document screen (table CODED_STD_BLACK_BOX_REF_DATA): for each
 * coded column, its codes and their names.
 * <p>
 * Read-only and used in JPQL projections only. <b>The key (REF_COLUMN, LOOKUP_CD) is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_STD_BLACK_BOX_REF_DATA")
@IdClass(CodedStdBlackBoxRefDataId.class)
public class CodedStdBlackBoxRefData {

	@Id
	@Column(name = "REF_COLUMN")
	private String refColumn;

	@Id
	@Column(name = "LOOKUP_CD")
	private String lookupCd;

	@Column(name = "LOOKUP_NAME")
	private String lookupName;
}
