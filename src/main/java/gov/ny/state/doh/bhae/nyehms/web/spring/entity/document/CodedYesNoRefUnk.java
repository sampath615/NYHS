package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** The yes / no / refused / unknown answers (table CODED_YES_NO_REF_UNK), read-only. <b>ANS_CODE as the key is assumed.</b> */
@Entity
@Immutable
@Getter
@Table(name = "CODED_YES_NO_REF_UNK")
public class CodedYesNoRefUnk {

	@Id
	@Column(name = "ANS_CODE")
	private String ansCode;

	@Column(name = "ANS_DESC")
	private String ansDesc;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
