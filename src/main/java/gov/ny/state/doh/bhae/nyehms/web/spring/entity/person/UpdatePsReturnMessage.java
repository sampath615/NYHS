package gov.ny.state.doh.bhae.nyehms.web.spring.entity.person;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An updated partner services return message (table UPDATE_PS_RETURN_MESSAGE): only the document id the upload checks
 * for. Read-only, read in a JPQL projection by {@code PersonDAO.checkForExistingUpdatedMessageDocIds}.
 * <b>DOCUMENT_ID is declared as the key only because an entity needs one; it may repeat.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "UPDATE_PS_RETURN_MESSAGE")
public class UpdatePsReturnMessage {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;
}
