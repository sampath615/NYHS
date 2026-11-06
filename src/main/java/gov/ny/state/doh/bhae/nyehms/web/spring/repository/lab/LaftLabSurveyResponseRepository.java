package gov.ny.state.doh.bhae.nyehms.web.spring.repository.lab;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab.LaftLabSurveyResponse;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab.LaftLabSurveyResponseId;

/** The uploaded LAFT survey responses (LAFT_LAB_SURVEY_RESPONSE). */
public interface LaftLabSurveyResponseRepository extends JpaRepository<LaftLabSurveyResponse, LaftLabSurveyResponseId> {

	/** The number of rows uploaded from a file of this name. */
	long countBySurveyResponseFilename(String surveyResponseFilename);
}
