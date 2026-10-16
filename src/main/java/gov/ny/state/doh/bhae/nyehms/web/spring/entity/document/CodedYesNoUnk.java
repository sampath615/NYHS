package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A code list (table CODED_YES_NO_UNK): each code and its description; only the columns the application reads.
 * <p>
 * Read-only. <b>ANS_CODE as the key is assumed</b>.
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_YES_NO_UNK")
public class CodedYesNoUnk {

	@Id
	@Column(name = "ANS_CODE")
	private String ansCode;

	@Column(name = "ANS_DESC")
	private String ansDesc;
}
