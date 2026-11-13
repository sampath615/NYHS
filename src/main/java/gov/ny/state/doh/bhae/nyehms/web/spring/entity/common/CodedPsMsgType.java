package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The partner services message types (table CODED_PS_MSG_TYPE), read-only.
 * <p>
 * <b>PS_MSG_TYPE as the key is assumed</b>; the list is read through a projection.
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_PS_MSG_TYPE")
public class CodedPsMsgType {

	@Id
	@Column(name = "PS_MSG_TYPE")
	private String psMsgType;

	@Column(name = "PS_MSG_TYPE_DESCRIPTION")
	private String psMsgTypeDescription;

	@Column(name = "END_DATE")
	private Timestamp endDate;

	@Column(name = "DOCUMENT_TYPE_CODE")
	private String documentTypeCode;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
