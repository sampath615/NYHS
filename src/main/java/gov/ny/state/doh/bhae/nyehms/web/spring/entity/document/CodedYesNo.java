package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The yes / no answers (table CODED_YES_NO, not CODED_YES_NO_UNK), read-only.
 * <p>
 * <b>ANS_CODE as the key is assumed</b>; the list is read through a projection.
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_YES_NO")
public class CodedYesNo {

	@Id
	@Column(name = "ANS_CODE")
	private String ansCode;

	@Column(name = "ANS_DESC")
	private String ansDesc;
}
