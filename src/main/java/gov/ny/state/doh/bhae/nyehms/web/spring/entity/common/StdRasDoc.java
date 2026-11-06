package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

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
 * A resistance (RAS) document, standardized (table STD_RAS_DOC): the columns the RAS document screen reads and the
 * status the resistance-document edit changes (RASDocumentDAO).
 * <p>
 * Read with JPQL projections; CURRENT_PROCESS_STATUS is changed by a bulk JPQL update only, so the entity has no
 * change methods. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_RAS_DOC")
public class StdRasDoc {

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
	private Timestamp dob;

	@Column(name = "VSEQID")
	private Integer vseqid;

	@Column(name = "VSEQCREATIONDATE")
	private Timestamp vseqcreationdate;

	@Column(name = "RECORDS_GROUPED_COUNT")
	private Integer recordsGroupedCount;

	@Column(name = "RESISTANCE_DOC_ID_1")
	private Integer resistanceDocId1;

	@Column(name = "RESISTANCE_TEST_DATE_1")
	private Timestamp resistanceTestDate1;

	@Column(name = "RESISTANCE_DOC_ID_2")
	private Integer resistanceDocId2;

	@Column(name = "RESISTANCE_TEST_DATE_2")
	private Timestamp resistanceTestDate2;

	@Column(name = "RESISTANCE_DOC_ID_3")
	private Integer resistanceDocId3;

	@Column(name = "RESISTANCE_TEST_DATE_3")
	private Timestamp resistanceTestDate3;

	@Column(name = "GENESFOUNDTEXT")
	private String genesfoundtext;

	@Column(name = "PHYLOSUBTYPE")
	private String phylosubtype;

	@Column(name = "RESISTANCE_DRUG_CLASS_PR")
	private Integer resistanceDrugClassPr;

	@Column(name = "RESISTANCE_DRUG_CLASS_NRTI")
	private Integer resistanceDrugClassNrti;

	@Column(name = "RESISTANCE_DRUG_CLASS_NNRTI")
	private Integer resistanceDrugClassNnrti;

	@Column(name = "RESISTANCE_DRUG_CLASS_IN")
	private Integer resistanceDrugClassIn;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;
}
