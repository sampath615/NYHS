package gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A run of the Sierra job (table RAS_SIERRA_LOG): the range of lab documents parsed and the counts (SierraResponseDAO).
 * <p>
 * Inserted by HQL inserts and read with projections only, never loaded as an entity: the key is only for the mapping
 * (<b>assumed</b>). CREATE_DATE is the database's SYSDATE, as before.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAS_SIERRA_LOG")
@IdClass(RasSierraLogId.class)
public class RasSierraLog {

	@Id
	@Column(name = "DOCUMENT_ID_MIN")
	private Integer documentIdMin;

	@Id
	@Column(name = "DOCUMENT_ID_MAX")
	private Integer documentIdMax;

	@Column(name = "NO_OF_ERRORS")
	private Integer noOfErrors;

	@Column(name = "TOTAL_NO_RECORDS")
	private Integer totalNoRecords;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "CREATE_ID")
	private String createId;
}
