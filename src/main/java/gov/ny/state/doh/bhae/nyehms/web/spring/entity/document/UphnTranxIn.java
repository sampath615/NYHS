package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A payload received from UPHN (table UPHN_TRANX_IN): its id, topic, payload name and time. Read-only; the loader
 * reads the highest id through a projection (HITDocumentDAO), the message counts read the table natively.
 * <b>UPHN_TRANX_IN_ID as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "UPHN_TRANX_IN")
public class UphnTranxIn {

	@Id
	@Column(name = "UPHN_TRANX_IN_ID")
	private Long uphnTranxInId;

	@Column(name = "TOPIC")
	private String topic;

	@Column(name = "PAYLOADNAME")
	private String payloadname;

	@Column(name = "RECEIVEDTIME")
	private Timestamp receivedtime;
}
