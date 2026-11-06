package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * A CD4 test entered on a case report form: the columns of RAW_ACRF_CD4 and RAW_PCRF_CD4.
 * <p>
 * CD4_SRC_ID is filled by the database on insert (the former insert did not write it), so it is an identity here. The
 * attribute types follow the VO the rows were read into and written from (RawAcrfCd4TstVO: CD4_COUNT an Integer,
 * CD4_PERCENT a Float, FIRST_LOW_CD4 a boolean). UPDATED_DATE is the database's SYSDATE on insert.
 */
@MappedSuperclass
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class RawCrfCd4 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CD4_SRC_ID")
	private Long cd4SrcId;

	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "CD4_COUNT")
	private Integer cd4Count;

	@Column(name = "CD4_PERCENT")
	private Float cd4Percent;

	@Column(name = "COLLECTION_DATE")
	private String collectionDate;

	@Column(name = "CLOSEST_TO_CURRENT_DX")
	private String closestToCurrentDx;

	@Column(name = "FIRST_LOW_CD4")
	private Boolean firstLowCd4;

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "FACILITY_EHARS_UID")
	private String facilityEharsUid;

	@Column(name = "CLIA")
	private String clia;

	@Column(name = "LAB_NAME")
	private String labName;

	@Column(name = "PROV_NAME")
	private String provName;

	@Column(name = "PROV_FNAME")
	private String provFname;

	@Column(name = "PROV_LNAME")
	private String provLname;

	@Column(name = "PROV_MNAME")
	private String provMname;

	@Column(name = "PROV_SUFFIX")
	private String provSuffix;

	@Column(name = "MANUFACTURER")
	private String manufacturer;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;
}
