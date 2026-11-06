package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The LAFT survey e-mails to the labs (table LAFT_LAB_SURVEY_TRACK, filled by the PL/SQL package
 * LAFT_LAB_RECON_PROCESS); only the columns LaftCountReconcileDAO writes.
 * <p>
 * Changed by a bulk JPQL update only. <b>SURVEY_TRACK_PK as the key is assumed.</b>
 */
@Entity
@Getter
@Table(name = "LAFT_LAB_SURVEY_TRACK")
public class LaftLabSurveyTrack {

	@Id
	@Column(name = "SURVEY_TRACK_PK")
	private Long surveyTrackPk;

	@Column(name = "SURVEY_SENT_DATE")
	private Timestamp surveySentDate;

	@Column(name = "SURVEY_SENT_BY")
	private String surveySentBy;
}
