package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * A CLIA number (table CLINICAL_LABORATORY): the clinical laboratories the physical facilities (LAB_PHYSICAL_FACILITY) belong to; the column LabReportsDAO reads and inserts (the table is also used by SchedulerJobsDAO).
 * <p>
 * Inserted by a JPQL insert, read through projections. <b>CLIA as the key is assumed.</b>
 */
@Entity
@Getter
@Table(name = "CLINICAL_LABORATORY")
public class ClinicalLaboratory {

	@Id
	@Column(name = "CLIA")
	private String clia;
}
