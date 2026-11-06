package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The sources of an AHI alert (table CODED_AHI_ALERT_SRC), read-only.
 * <p>
 * <b>AHI_ALERT_CODE as the key is assumed</b>; the list is read through a projection.
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_AHI_ALERT_SRC")
public class CodedAhiAlertSrc {

	@Id
	@Column(name = "AHI_ALERT_CODE")
	private String ahiAlertCode;

	@Column(name = "AHI_ALERT_DESC")
	private String ahiAlertDesc;
}
