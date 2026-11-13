package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** The sexual orientations (table CODED_SEXUAL_ORIENTATION), read-only. <b>SEX_ORIENT_CD as the key is assumed.</b> */
@Entity
@Immutable
@Getter
@Table(name = "CODED_SEXUAL_ORIENTATION")
public class CodedSexualOrientation {

	@Id
	@Column(name = "SEX_ORIENT_CD")
	private String sexOrientCd;

	@Column(name = "SEX_ORIENT_DESC")
	private String sexOrientDesc;

	/** 'Y' for the values the PRF offers. */
	@Column(name = "PRF")
	private String prf;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
