package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A group of LOINC codes whose HIV lab results of one laboratory (PFI) are combined into one document (table
 * CODED_LOINC_GROUPING); a group is in force until its END_DATE. Read-only, read in a JPQL projection by
 * {@code LabDocumentDAO.getLabCombiningDocData} (the multi-spot document search reads the table natively).
 * <b>LOINC_GROUP_ID as the key is assumed</b>.
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_LOINC_GROUPING")
public class CodedLoincGrouping {

	@Id
	@Column(name = "LOINC_GROUP_ID")
	private Long loincGroupId;

	@Column(name = "LOINC_1")
	private String loinc1;

	@Column(name = "LOINC_2")
	private String loinc2;

	@Column(name = "LOINC_3")
	private String loinc3;

	@Column(name = "LOINC_4")
	private String loinc4;

	@Column(name = "GROUP_COUNT")
	private Long groupCount;

	@Column(name = "CATEGORY_NAME")
	private String categoryName;

	@Column(name = "PFI")
	private String pfi;

	@Column(name = "NEW_DOCUMENT")
	private String newDocument;

	@Column(name = "END_DATE")
	private Timestamp endDate;
}
