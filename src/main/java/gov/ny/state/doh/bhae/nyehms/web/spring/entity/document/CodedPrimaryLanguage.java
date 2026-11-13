package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** The primary languages (table CODED_PRIMARY_LANGUAGE), read-only. <b>LANG_CODE as the key is assumed.</b> */
@Entity
@Immutable
@Getter
@Table(name = "CODED_PRIMARY_LANGUAGE")
public class CodedPrimaryLanguage {

	@Id
	@Column(name = "LANG_CODE")
	private String langCode;

	@Column(name = "LANG_DESC")
	private String langDesc;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
