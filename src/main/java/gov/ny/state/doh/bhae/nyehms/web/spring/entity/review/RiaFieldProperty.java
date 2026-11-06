package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The properties of the RIA document fields (table RIA_FIELD_PROPERTY): which are shown, and their column type
 * (Date / text) for the RIA save (RiaRecordsReviewDAO). Read only; <b>REF_COLUMN as the key is assumed</b>; all
 * columns are text.
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RIA_FIELD_PROPERTY")
public class RiaFieldProperty {

	@Id
	@Column(name = "REF_COLUMN")
	private String refColumn;

	@Column(name = "PROPERTY")
	private String property;

	@Column(name = "COLUMN_TYPE")
	private String columnType;
}
