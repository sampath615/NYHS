package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

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
 * The HIV testing and treatment history (TTH) of a case report form, as entered (table RAW_TTH_DOC); inserted and read
 * by TTHDocumentDAO.
 * <p>
 * Inserted once, never changed. <b>DOCUMENT_ID as the key is assumed.</b> PRF_DOC_ID and AIRS_DOC_ID are not written
 * on insert (as before). The dates are text, as the form's VO holds them.
 */
@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAW_TTH_DOC")
public class RawTthDoc implements Persistable<Integer> {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "ACRF_DOC_ID")
	private Integer acrfDocId;

	@Column(name = "PRF_DOC_ID", insertable = false, updatable = false)
	private Integer prfDocId;

	@Column(name = "AIRS_DOC_ID", insertable = false, updatable = false)
	private Integer airsDocId;

	@Column(name = "MAIN_TTH_SOURCE")
	private String mainTthSource;

	@Column(name = "PT_INTERVIEW_DATE")
	private String ptInterviewDate;

	@Column(name = "EVER_HAD_POS")
	private String everHadPos;

	@Column(name = "FIRST_HIV_TEST_EVER")
	private String firstHivTestEver;

	@Column(name = "EVER_HAD_NEG")
	private String everHadNeg;

	@Column(name = "LAST_NEG_DATE")
	private String lastNegDate;

	@Column(name = "RESP_HIV_TEST_2YRS")
	private String respHivTest2yrs;

	@Column(name = "COUNT_HIV_TEST_2YRS")
	private Integer countHivTest2yrs;

	@Column(name = "PT_ARV_6_MO")
	private String ptArv6Mo;

	@Column(name = "PT_ARV_NAME")
	private String ptArvName;

	@Column(name = "PT_ARV_START_DATE")
	private String ptArvStartDate;

	@Column(name = "PT_ARV_END_DATE")
	private String ptArvEndDate;

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
