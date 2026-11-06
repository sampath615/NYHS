package gov.ny.state.doh.bhae.nyehms.web.spring.entity.scheduler;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * How a lab (PFI) codes ethnicity (table LAB_LOAD_ETHNICITY_MAPPING): the lab's code and the standard code it is
 * translated to, read by the eCLRS loaders.
 * <p>
 * Read-only, through JPQL projections only. <b>RAW_ETHNICITY_CD as the key is assumed</b> only to satisfy JPA (codes
 * repeat across labs; no entity is loaded). PFI is a text attribute: the former statement compared it with the PFI
 * text of the lab report as it was.
 */
@Entity
@Immutable
@Getter
@Table(name = "LAB_LOAD_ETHNICITY_MAPPING")
public class LabLoadEthnicityMapping {

	@Id
	@Column(name = "RAW_ETHNICITY_CD")
	private String rawEthnicityCd;

	@Column(name = "PFI")
	private String pfi;

	@Column(name = "TRANSLATED_ETHNICITY_CD")
	private String translatedEthnicityCd;
}
