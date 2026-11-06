package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A clinical laboratory (table CLINICAL_LABORATORY); only its CLIA number, which the eCLRS loaders look up (the table is
 * also used by LabReportsDAO, which may add its columns here).
 * <p>
 * Read-only, used in counts only. <b>CLIA as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CLINICAL_LABORATORY")
public class ClinicalLaboratory {

	@Id
	@Column(name = "CLIA")
	private String clia;
}
