package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** The ethnicities (table CODED_ETHNICITY), read-only. <b>ETHNICITY_CODE as the key is assumed.</b> */
@Entity
@Immutable
@Getter
@Table(name = "CODED_ETHNICITY")
public class CodedEthnicity {

	@Id
	@Column(name = "ETHNICITY_CODE")
	private String ethnicityCode;

	@Column(name = "ETHNICITY_DESCRIPTION")
	private String ethnicityDescription;
}
