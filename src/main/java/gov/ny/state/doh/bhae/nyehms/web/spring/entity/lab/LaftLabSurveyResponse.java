package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The uploaded LAFT survey responses (table LAFT_LAB_SURVEY_RESPONSE), one row per lab of an uploaded file; read by the
 * PL/SQL procedure LAFT_LAB_RECON_PROCESS.process_survey_response right after the upload.
 * <p>
 * Written by an HQL insert only (LaftCountReconcileDAO.insertLaftSurveyResponseData), never loaded or saved as an entity.
 * The answer columns are mapped as text because the former insert bound the uploaded file's text values as they
 * were (the database converted them as before). <b>The key (LAFT_RECON_ID, PFI) is assumed</b> (the upload rejects a
 * PFI that is repeated or already uploaded for the period).
 */
@Entity
@Getter
@Table(name = "LAFT_LAB_SURVEY_RESPONSE")
@IdClass(LaftLabSurveyResponseId.class)
public class LaftLabSurveyResponse {

	@Id
	@Column(name = "LAFT_RECON_ID")
	private String laftReconId;

	@Column(name = "SURVEY_RESPONSE_FILENAME")
	private String surveyResponseFilename;

	@Column(name = "UPLOAD_DATE")
	private Timestamp uploadDate;

	@Column(name = "WORKER_ID")
	private String workerId;

	@Id
	@Column(name = "PFI")
	private String pfi;

	@Column(name = "CLIA")
	private String clia;

	@Column(name = "WES_BLOT")
	private String wesBlot;

	@Column(name = "HIV1_2_RNA_NAT_QL")
	private String hiv12RnaNatQl;

	@Column(name = "HIV_1_RNA_NAT_QL")
	private String hiv1RnaNatQl;

	@Column(name = "HIV_2_RNA_NAT_QL")
	private String hiv2RnaNatQl;

	@Column(name = "HIV_1_RNA_COPY")
	private String hiv1RnaCopy;

	@Column(name = "HIV_1_RNA_LOG")
	private String hiv1RnaLog;

	@Column(name = "CD4_PCT")
	private String cd4Pct;

	@Column(name = "CD4_ABS")
	private String cd4Abs;

	@Column(name = "GENO_RESIST")
	private String genoResist;

	@Column(name = "DTA_SCREEN_ALERE")
	private String dtaScreenAlere;

	@Column(name = "DTA_SCREEN")
	private String dtaScreen;

	@Column(name = "BIOPLEX_HIV_1_AG")
	private String bioplexHiv1Ag;

	@Column(name = "BIOPLEX_HIV_1_AB")
	private String bioplexHiv1Ab;

	@Column(name = "BIOPLEX_HIV_2_AB")
	private String bioplexHiv2Ab;

	@Column(name = "ELECSYS_HIV1_2_AB")
	private String elecsysHiv12Ab;

	@Column(name = "DTA_DIFF")
	private String dtaDiff;

	@Column(name = "DTA_DIFF_HIV_1")
	private String dtaDiffHiv1;

	@Column(name = "DTA_DIFF_HIV_2")
	private String dtaDiffHiv2;

	@Column(name = "HIV1_DNA")
	private String hiv1Dna;

	@Column(name = "HIV_2_RNA_DNA")
	private String hiv2RnaDna;

	@Column(name = "IFA")
	private String ifa;

	@Column(name = "OPEN_ENDED_RESPONSE")
	private String openEndedResponse;

	@Column(name = "SURVEY_COMPLETED_BY")
	private String surveyCompletedBy;

	@Column(name = "SURVEY_COMPLETED_BY_EMAIL")
	private String surveyCompletedByEmail;

	@Column(name = "SURVEY_COMPLETED_BY_PHONE")
	private String surveyCompletedByPhone;

	@Column(name = "SURVEY_COMPLETED_DATE")
	private Timestamp surveyCompletedDate;
}
