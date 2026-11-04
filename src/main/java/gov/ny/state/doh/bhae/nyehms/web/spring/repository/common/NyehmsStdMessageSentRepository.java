package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.NyehmsStdMessageSent;

/** The partner services (PS) messages sent (table NYEHMS_STD_MESSAGE_SENT). */
public interface NyehmsStdMessageSentRepository extends JpaRepository<NyehmsStdMessageSent, Long> {

	/** The LAFT review of a message (a projection: the review columns only). */
	record LaftReview(String laftComments, String laftReasons, Long laftReviewBy, Timestamp laftReviewDate) {
	}

	/** The messages of a triggering document in a state (e.g. 'R', open). */
	List<NyehmsStdMessageSent> findByFollowUpDocIdAndProcessInd(Long followUpDocId, String processInd);

	/** The LAFT reviews of the messages with this document id. */
	List<LaftReview> findLaftReviewsByDocumentId(Long documentId);
}
