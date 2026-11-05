package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * An HIV final status of a newborn screening report (table CODED_HIV_FINAL_STATUS), listed by the NBS review.
 * <p>
 * Read-only. <b>HIV_FINAL_STATUS_CD as the key is assumed</b>; both columns are text.
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CODED_HIV_FINAL_STATUS")
public class CodedHivFinalStatus {

	@Id
	@Column(name = "HIV_FINAL_STATUS_CD")
	private String hivFinalStatusCd;

	@Column(name = "HIV_FINAL_STATUS_DESC")
	private String hivFinalStatusDesc;
}
