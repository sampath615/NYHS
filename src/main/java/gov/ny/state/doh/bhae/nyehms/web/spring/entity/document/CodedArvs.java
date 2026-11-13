package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** The antiretroviral codes (table CODED_ARVS), read-only through a distinct projection. <b>LOOKUP_CD as the key is assumed.</b> */
@Entity
@Immutable
@Getter
@Table(name = "CODED_ARVS")
public class CodedArvs {

	@Id
	@Column(name = "LOOKUP_CD")
	private String lookupCd;

	@Column(name = "ARV_DESC")
	private String arvDesc;
}
