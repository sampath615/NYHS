package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The LAFT count reconciliation periods (table LAFT_LAB_RECON_LIST, filled by the PL/SQL package
 * LAFT_LAB_RECON_PROCESS); only the columns LaftCountReconcileDAO reads. The collection dates are text (MM/DD/YYYY),
 * as the former statement read and converted them.
 * <p>
 * Read-only and read through JPQL projections only (a period has several rows, so the assumed key repeats; that does
 * not matter for projections). <b>LAFT_RECON_ID as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "LAFT_LAB_RECON_LIST")
public class LaftLabReconList {

	@Id
	@Column(name = "LAFT_RECON_ID")
	private String laftReconId;

	@Column(name = "COLLECTION_START_DATE")
	private String collectionStartDate;

	@Column(name = "COLLECTION_END_DATE")
	private String collectionEndDate;
}
