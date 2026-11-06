package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The types of lab communication (table CODED_LAB_COMMUNICATION_TYPE).
 * <p>
 * Read-only. <b>COMMUNICATION_TYPE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_LAB_COMMUNICATION_TYPE")
public class CodedLabCommunicationType {

	@Id
	@Column(name = "COMMUNICATION_TYPE")
	private String communicationType;

	@Column(name = "COMMUNICATION_TYPE_DESCRIPTION")
	private String communicationTypeDescription;

	@Column(name = "END_DATE")
	private Timestamp endDate;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
