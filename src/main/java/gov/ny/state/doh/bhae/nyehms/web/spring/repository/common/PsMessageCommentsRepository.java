package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.PsMessageComments;

/**
 * The comments on partner services (PS) messages (table PS_MESSAGE_COMMENTS). New comments are persisted with
 * {@code JpqlStatements.insert} (the key is assumed, so they are never merged).
 */
public interface PsMessageCommentsRepository extends JpaRepository<PsMessageComments, PsMessageComments.Key> {

	/** A comment as read (a projection: duplicates of the assumed key are kept). */
	record Comment(String comments, Long createId, Timestamp createDate) {
	}

	/** The comments on the message with this document id. */
	List<Comment> findCommentsByDocumentId(Long documentId);
}
