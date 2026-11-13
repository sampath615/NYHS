package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The person attributes of a document (table DOCUMENT_PERSON_ATTR); only DOCUMENT_ID, the column the scanning writes
 * (a new row with only the document id) and deletes by.
 * <p>
 * <b>DOCUMENT_ID as the key is assumed.</b> Inserted as a new row (no other column written, as before) and deleted by
 * a bulk statement.
 * <p>
 * Also the name, birth date text and address columns the cluster screens read (through projections) and the cluster
 * partner address change writes (JPQL bulk update); they are not written by the insert (database defaults stay).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "DOCUMENT_PERSON_ATTR")
public class DocumentPersonAttr implements Persistable<Integer> {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "FIRST_NAME", insertable = false)
	private String firstName;

	@Column(name = "LAST_NAME", insertable = false)
	private String lastName;

	@Column(name = "DOB_TEXT", insertable = false)
	private String dobText;

	@Column(name = "ADDRESS", insertable = false)
	private String address;

	@Column(name = "ADDRESS_CITY", insertable = false)
	private String addressCity;

	@Column(name = "COUNTY_FIPS", insertable = false)
	private String countyFips;

	@Column(name = "ADDRESS_STATE", insertable = false)
	private String addressState;

	@Column(name = "ADDRESS_ZIPCODE", insertable = false)
	private String addressZipcode;

	/** Read by the partner services upload (PersonDAO: documents with a date of death); not written. */
	@Column(name = "DATE_OF_DEATH", insertable = false, updatable = false)
	private java.sql.Timestamp dateOfDeath;

	/** The provider and facility address ids an edited copy's assignments are compared with (CommonDocLabsDAO); not written. */
	@Column(name = "PROVIDER_ID", insertable = false, updatable = false)
	private Long providerId;

	@Column(name = "FACILITY_ID", insertable = false, updatable = false)
	private Long facilityId;

	@Override
	public Integer getId() {
		return documentId;
	}

	/** Always a new row (the application only inserts it). */
	@Override
	public boolean isNew() {
		return true;
	}
}
