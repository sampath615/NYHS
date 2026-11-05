package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An adult case report form as entered (table RAW_ACRF_DOC); only the columns of its HIV testing and treatment history
 * (TTH) and the form ids, which TTHDocumentDAO.updateTTHDocData changes (the table is read and written elsewhere
 * through MyBatis).
 * <p>
 * Changed by bulk updates only. <b>DOCUMENT_ID as the key is assumed.</b>
 */
@Entity
@Getter
@Table(name = "RAW_ACRF_DOC")
public class RawAcrfDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "ACRF_DOC_ID")
	private Integer acrfDocId;

	@Column(name = "PRF_DOC_ID")
	private Integer prfDocId;

	@Column(name = "AIRS_DOC_ID")
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
}
