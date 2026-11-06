package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The accession numbers of a CLEP audit survey (table LAFT_CLEP_AUDIT_ACCESSION_DATA), numbered by SERIAL_NO.
 * <p>
 * COLLECTION_DATE and DATE_RESOLVED are mapped as MM/DD/YYYY text (read as {@code TO_CHAR(x, 'MM/DD/YYYY')}, written as
 * {@code FN_TO_DATE(?, 'MM/DD/YYYY')}, as the former statements did). The test type flags are booleans because the
 * former statements bound and read them as the form's booleans (setBoolean / getBoolean). Inserted as entities and
 * changed by JPQL bulk updates, which spell out the same conversions
 * (HQL updates do not apply the write expressions). <b>The key (SURVEY_NUMBER, SERIAL_NO) is
 * assumed.</b>
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "LAFT_CLEP_AUDIT_ACCESSION_DATA")
@IdClass(LaftClepAuditAccessionDataId.class)
public class LaftClepAuditAccessionData {

	@Id
	@Column(name = "SURVEY_NUMBER")
	private Long surveyNumber;

	@Id
	@Column(name = "SERIAL_NO")
	private Long serialNo;

	@Column(name = "ACCESSION_NUMBER")
	private String accessionNumber;

	@ColumnTransformer(read = "to_char(COLLECTION_DATE, 'MM/DD/YYYY')", write = "FN_TO_DATE(?, 'MM/DD/YYYY')")
	@Column(name = "COLLECTION_DATE")
	private String collectionDate;

	@Column(name = "TEST_TYPE_HIV_IMM")
	private Boolean testTypeHivImm;

	@Column(name = "TEST_TYPE_HIV_NAT")
	private Boolean testTypeHivNat;

	@Column(name = "TEST_TYPE_CD4")
	private Boolean testTypeCd4;

	@Column(name = "TEST_TYPE_CUL")
	private Boolean testTypeCul;

	@Column(name = "TEST_TYPE_SUPP_IMM")
	private Boolean testTypeSuppImm;

	@Column(name = "TEST_TYPE_RES")
	private Boolean testTypeRes;

	@Column(name = "TEST_TYPE_OTH")
	private Boolean testTypeOth;

	@Column(name = "REPORTED_IN_NYEHMS")
	private String reportedInNyehms;

	@Column(name = "RESOLVED")
	private String resolved;

	@ColumnTransformer(read = "to_char(DATE_RESOLVED, 'MM/DD/YYYY')", write = "FN_TO_DATE(?, 'MM/DD/YYYY')")
	@Column(name = "DATE_RESOLVED")
	private String dateResolved;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "LAFT_STAFF")
	private String laftStaff;
}
