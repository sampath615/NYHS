package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The provider and facility addresses of an assignment (table ASSIGNMENT_ADDRESS); an address is current while
 * END_DATE is null. Read-only, through a projection (CommonDocLabsDAO), so a key that repeats does not matter.
 * <b>DOCUMENT_ID as the key is assumed</b> (it is the assignment's document; an assignment can have several rows).
 */
@Entity
@Immutable
@Getter
@Table(name = "ASSIGNMENT_ADDRESS")
public class AssignmentAddress {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "PROVIDER_ADDRESS_ID")
	private Long providerAddressId;

	@Column(name = "FACILITY_ADDRESS_ID")
	private Long facilityAddressId;

	@Column(name = "END_DATE")
	private Timestamp endDate;
}
