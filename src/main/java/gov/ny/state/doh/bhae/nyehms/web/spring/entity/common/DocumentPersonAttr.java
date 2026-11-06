package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

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
 * The person attributes of a document (table DOCUMENT_PERSON_ATTR); only DOCUMENT_ID, the column the scanning writes
 * (a new row with only the document id) and deletes by.
 * <p>
 * <b>DOCUMENT_ID as the key is assumed.</b> Inserted as a new row (no other column written, as before) and deleted by
 * a bulk statement.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "DOCUMENT_PERSON_ATTR")
public class DocumentPersonAttr implements Persistable<Integer> {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

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
