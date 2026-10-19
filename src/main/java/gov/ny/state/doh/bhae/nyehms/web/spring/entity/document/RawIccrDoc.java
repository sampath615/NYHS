package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.Immutable;
import org.springframework.data.domain.Persistable;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * An ICCR (Infection Control) report as received (table RAW_ICCR_DOC) with its tests (RAW_ICCR_TESTS): the
 * form object of the ICCR data entry screen (ICCRDataEntryDoc.jsp; the posted form is bound through the public
 * constructor), read by the ICCR document screen.
 * <p>
 * Never updated: a row is inserted once with its tests ({@code RawIccrDocRepository.saveAndFlush}, built by
 * {@code IccrDocMapping} from the posted form), so it is always new ({@link #isNew()}: save() inserts without a select first).
 * <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor   // public: the posted data entry form is bound through it
@Table(name = "RAW_ICCR_DOC")
public class RawIccrDoc implements Persistable<Integer> {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "MIDDLE_NAME")
	private String middleName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "DOB")
	@DateTimeFormat(pattern = "M/d/yyyy")   // as entered; not a valid date: left empty, as FN_TO_DATE did
	private LocalDate dob;

	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@Column(name = "PERSON_CITY")
	private String personCity;

	@Column(name = "PERSON_STATE")
	private String personState;

	@Column(name = "PERSON_ZIP")
	private String personZip;

	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@Column(name = "PERSON_PHONE")
	private String personPhone;

	@Column(name = "JURISDICTION_PROVIDING_INFO")
	private String jurisdictionProvidingInfo;

	@Column(name = "PROV_NAME")
	private String provName;

	@Column(name = "PROV_ADDRESS")
	private String provAddress;

	@Column(name = "PROV_CITY")
	private String provCity;

	@Column(name = "PROV_STATE")
	private String provState;

	@Column(name = "PROV_ZIP")
	private String provZip;

	@Column(name = "PROV_COUNTY")
	private String provCounty;

	@Column(name = "PROV_PHONE")
	private String provPhone;

	@Column(name = "RESULTS_RECEIVED")
	private String resultsReceived;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "CREATE_UID")
	private Integer createUid;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	/** The tests of the report (RAW_ICCR_TESTS), in no particular order; the same test may be listed twice. */
	@ElementCollection
	@CollectionTable(name = "RAW_ICCR_TESTS", joinColumns = @JoinColumn(name = "DOCUMENT_ID"))
	private List<RawIccrTest> iccrTestList;

	@Override
	public Integer getId() {
		return documentId;
	}

	/** Rows are only ever inserted. */
	@Override
	public boolean isNew() {
		return true;
	}
}
