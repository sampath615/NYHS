package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.io.Serializable;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The raw-to-standardized column mapping of a document type (table CODED_RAW_STD_COL_MAPPING): the pairs the column
 * viewer shows. Read-only, through a projection. <b>(DOCUMENT_TYPE_CODE, RAW_COLUMN) as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@IdClass(CodedRawStdColMapping.Key.class)
@Table(name = "CODED_RAW_STD_COL_MAPPING")
public class CodedRawStdColMapping {

	@Id
	@Column(name = "DOCUMENT_TYPE_CODE")
	private String documentTypeCode;

	@Id
	@Column(name = "RAW_COLUMN")
	private String rawColumn;

	@Column(name = "STD_COLUMN")
	private String stdColumn;

	@Column(name = "COLUMN_VIEWER_FLAG")
	private String columnViewerFlag;

	/** The assumed key. */
	@EqualsAndHashCode
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Key implements Serializable {

		private static final long serialVersionUID = 1L;

		private String documentTypeCode;
		private String rawColumn;
	}
}
