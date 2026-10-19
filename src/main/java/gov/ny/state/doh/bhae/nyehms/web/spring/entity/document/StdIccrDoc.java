package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;
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
 * An ICCR (Infection Control) report, standardized (table STD_ICCR_DOC); the columns the ICCR document
 * screen reads and the import writes.
 * <p>
 * Never updated: a row is inserted once by the import ({@code IccrDocRepository.saveAndFlush}, built by
 * {@code IccrDocMapping}), so it is always new ({@link #isNew()}: save() inserts without a select first).
 * Columns the former SQL insert wrote as text are text here too (Oracle converts the numeric ones).
 * <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_ICCR_DOC")
public class StdIccrDoc implements Persistable<Integer> {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "RESULTS_RECEIVED")
	private String resultsReceived;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "DOB")
	private Timestamp dob;

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

	@Column(name = "PERSON_CENSUS_TRACT")
	private String personCensusTract;

	@Column(name = "PERSON_LATITUDE")
	private String personLatitude;

	@Column(name = "PERSON_LONGITUDE")
	private String personLongitude;

	@Column(name = "PROV_NAME")
	private String provName;

	@Column(name = "JURISDICTION_PROVIDING_INFO")
	private String jurisdictionProvidingInfo;

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

	@Column(name = "PROV_CENSUS_TRACT")
	private String provCensusTract;

	@Column(name = "PROV_LATITUDE")
	private String provLatitude;

	@Column(name = "PROV_LONGITUDE")
	private String provLongitude;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "EHARS_STATUS_CD")
	private String eharsStatusCd;

	@Column(name = "EHARS_IMPORT_DATE")
	private Timestamp eharsImportDate;

	@Column(name = "MATCH_CD")
	private String matchCd;

	@Column(name = "MATCHED_STATENO")
	private String matchedStateno;

	@Column(name = "MATCH_PASS")
	private String matchPass;

	@Column(name = "MATCH_WEIGHT")
	private String matchWeight;

	@Column(name = "MATCHED_PROCESS_DATE")
	private Timestamp matchedProcessDate;

	@Column(name = "FLUX_MATCH_CD")
	private String fluxMatchCd;

	@Column(name = "FLUX_POT_STATENO")
	private String fluxPotStateno;

	@Column(name = "FLUX_MATCHED_STATENO")
	private String fluxMatchedStateno;

	@Column(name = "FLUX_QS_PARENT_DOC_ID")
	private Long fluxQsParentDocId;

	@Column(name = "FLUX_MATCHED_PROCESS_DATE")
	private Timestamp fluxMatchedProcessDate;

	@Column(name = "REVIEW_UID")
	private String reviewUid;

	@Column(name = "REVIEW_DT")
	private Timestamp reviewDt;

	@Column(name = "CREATE_UID")
	private String createUid;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private String currentProcessStatus;

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
