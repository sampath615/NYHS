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
 * A TB record as received (table RAW_TB_DOC); the columns the TB document screen reads and the document copy
 * writes.
 * <p>
 * Never updated here: a copy is inserted once ({@code RawTbDocRepository.saveAndFlush}, built by
 * {@code TbDocMapping}), so it is always new ({@link #isNew()}). <b>DOCUMENT_ID as the key is assumed</b>.
 */
@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAW_TB_DOC")
public class RawTbDoc implements Persistable<Integer> {

	@Column(name = "STREET")
	private String street;

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "FACILITY_ADDRESS")
	private String facilityAddress;

	@Column(name = "PROVIDERSTATECODE")
	private String providerstatecode;

	@Column(name = "STATUSATDIAGNOSIS")
	private String statusatdiagnosis;

	@Column(name = "HOSP_PFI")
	private String hospPfi;

	@Column(name = "DIN")
	private String din;

	@Column(name = "PROVIDERCITY")
	private String providercity;

	@Column(name = "ATYPICAL")
	private String atypical;

	@Column(name = "DISTYPE")
	private String distype;

	@Column(name = "OI_HIST")
	private String oiHist;

	@Column(name = "ZIPCODE")
	private String zipcode;

	@Column(name = "FACILITY_ZIP")
	private String facilityZip;

	@Column(name = "PROVIDERFNAME")
	private String providerfname;

	@Column(name = "PROVIDERLNAME")
	private String providerlname;

	@Column(name = "PLUS4")
	private String plus4;

	@Column(name = "FACILITY_PLUS4")
	private String facilityPlus4;

	@Column(name = "LOCALITY")
	private String locality;

	@Column(name = "FACILITY_CITY")
	private String facilityCity;

	@Column(name = "HOSP_CHART")
	private String hospChart;

	@Column(name = "COUNTY")
	private String county;

	@Column(name = "FACILITY_COUNTY")
	private String facilityCounty;

	@Column(name = "TB_FLAG_HIST")
	private String tbFlagHist;

	@Column(name = "AREA_CD")
	private String areaCd;

	@Column(name = "LASTNAME")
	private String lastname;

	@Column(name = "MI")
	private String mi;

	@Column(name = "PROVIDERADDRESS")
	private String provideraddress;

	@Column(name = "FIRSTNAME")
	private String firstname;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "FACILITY_PHONE")
	private String facilityPhone;

	@Column(name = "TB_FILE")
	private String tbFile;

	@Column(name = "CASESTATUS")
	private String casestatus;

	@Column(name = "ETHNICITY_CD")
	private String ethnicityCd;

	@Column(name = "RACE_CD")
	private String raceCd;

	@Column(name = "STATE")
	private String state;

	@Column(name = "FACILITY_STATE")
	private String facilityState;

	@Column(name = "RACE_ASIAN_CD")
	private String raceAsianCd;

	@Column(name = "RACE_POLY_CD")
	private String racePolyCd;

	@Column(name = "PROVIDERPHONE")
	private String providerphone;

	@Column(name = "DOB_DATE")
	private String dobDate;

	@Column(name = "PROVIDERZIPCODE")
	private String providerzipcode;

	@Column(name = "DEATH_DATE")
	private String deathDate;

	@Column(name = "SEX")
	private String sex;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_AMERIND")
	private String raceAmerind;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "CLINICALMANAGEA")
	private String clinicalmanagea;

	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "SERIALNO")
	private Long serialno;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "RECEIVED_DATE")
	private Timestamp receivedDate;

	@Column(name = "DIAGNOSIS_DATE")
	private Timestamp diagnosisDate;

	@Column(name = "PROCESS_DATE")
	private Timestamp processDate;

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
