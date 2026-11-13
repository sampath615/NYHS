package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** The investigation statuses (table CODED_INVESTIGATION_STATUS), read-only. <b>INVESTIGATION_STATUS_CD as the key is assumed.</b> */
@Entity
@Immutable
@Getter
@Table(name = "CODED_INVESTIGATION_STATUS")
public class CodedInvestigationStatus {

	@Id
	@Column(name = "INVESTIGATION_STATUS_CD")
	private String investigationStatusCd;

	@Column(name = "INVESTIGATION_STATUS_DESC")
	private String investigationStatusDesc;
}
