package gov.ny.state.doh.bhae.nyehms.web.spring.entity.upload;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;
import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A row of an uploaded CDC RIDR file (table CDC_RIDR_INFO). Written by the upload, never updated.
 * <p>
 * The file's values are stored as text, as the upload always bound them (Oracle converts the date columns). The key
 * comes from CDC_RIDR_INFO_SEQ and CREATED_DT from the database clock, as before.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "CDC_RIDR_INFO")
public class CdcRidrInfo implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cdcRidrInfoSeq")
	@SequenceGenerator(name = "cdcRidrInfoSeq", sequenceName = "CDC_RIDR_INFO_SEQ", allocationSize = 1)
	@Column(name = "RIDR_ID")
	private Long ridrId;

	@Column(name = "RECORD_NO")
	private String recordNo;

	@Column(name = "NYS_NEWLY_REPORTED_CDC")
	private String nysNewlyReportedCdc;

	@Column(name = "NYS_REPORTING_JURISDICTION")
	private String nysReportingJurisdiction;

	@Column(name = "NYS_STATENO")
	private String nysStateno;

	@Column(name = "NYS_LAST_NAME_SOUNDEX")
	private String nysLastNameSoundex;

	@Column(name = "NYS_DATE_OF_BIRTH")
	private String nysDateOfBirth;

	@Column(name = "NYS_SEX_AT_BIRTH")
	private String nysSexAtBirth;

	@Column(name = "NYS_DATE_OF_DEATH")
	private String nysDateOfDeath;

	@Column(name = "NYS_TRANSMISSION_CATEGORY")
	private String nysTransmissionCategory;

	@Column(name = "NYS_HIV_CASE_DEF_CATEGORY")
	private String nysHivCaseDefCategory;

	@Column(name = "NYS_DATE_HIV_INFECTION_DIAG")
	private String nysDateHivInfectionDiag;

	@Column(name = "NYS_RESI_HIV_INFECTION_DIAG")
	private String nysResiHivInfectionDiag;

	@Column(name = "NYS_AIDS_CASE_DEF_CATEGORY")
	private String nysAidsCaseDefCategory;

	@Column(name = "NYS_DATE_AIDS_DIAG")
	private String nysDateAidsDiag;

	@Column(name = "NYS_RESI_AT_AIDS_DIAG")
	private String nysResiAtAidsDiag;

	@Column(name = "NYS_DUPLICATE_STATUS")
	private String nysDuplicateStatus;

	@Column(name = "NEWLY_REPORTED_CDC")
	private String newlyReportedCdc;

	@Column(name = "REPORTING_JURISDICTION")
	private String reportingJurisdiction;

	@Column(name = "STATENO")
	private String stateno;

	@Column(name = "DATE_OF_DEATH")
	private String dateOfDeath;

	@Column(name = "TRANSMISSION_CATEGORY")
	private String transmissionCategory;

	@Column(name = "HIV_CASE_DEF_CATEGORY")
	private String hivCaseDefCategory;

	@Column(name = "DATE_HIV_INFECTION_DIAG")
	private String dateHivInfectionDiag;

	@Column(name = "RESI_HIV_INFECTION_DIAG")
	private String resiHivInfectionDiag;

	@Column(name = "AIDS_CASE_DEF_CATEGORY")
	private String aidsCaseDefCategory;

	@Column(name = "DATE_AIDS_DIAG")
	private String dateAidsDiag;

	@Column(name = "RESI_AT_AIDS_DIAG")
	private String resiAtAidsDiag;

	@Column(name = "DUPLICATE_STATUS")
	private String duplicateStatus;

	@Column(name = "MERGED_CDC")
	private String mergedCdc;

	/** Always the database's SYSDATE (the attribute itself is never set). */
	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	@Column(name = "CREATED_DT")
	private Timestamp createdDt;

	@Column(name = "CREATED_UID")
	private String createdUid;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Override
	public Long getId() {
		return ridrId;
	}

	/** Rows are only ever inserted. */
	@Override
	public boolean isNew() {
		return true;
	}
}
