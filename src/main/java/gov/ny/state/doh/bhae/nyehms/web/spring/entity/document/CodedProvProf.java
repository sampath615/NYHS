package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** The provider professions (table CODED_PROV_PROF), read-only. <b>PROF_CODE as the key is assumed.</b> */
@Entity
@Immutable
@Getter
@Table(name = "CODED_PROV_PROF")
public class CodedProvProf {

	@Id
	@Column(name = "PROF_CODE")
	private String profCode;

	@Column(name = "PROF_DESC")
	private String profDesc;
}
