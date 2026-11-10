package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The CLIA of an out-of-state laboratory by facility name (table CODED_OOS_CLIA): the eCLRS HIV import looks the real
 * CLIA up when a record arrives under the shared out-of-state CLIA. Read-only, read in a JPQL projection by
 * {@code LabDocumentDAO.getOOSCliaDetails}. <b>FACILITY_NAME as the key is assumed</b>.
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_OOS_CLIA")
public class CodedOosClia {

	@Id
	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "CLIA")
	private String clia;
}
