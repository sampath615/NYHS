package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The antiretrovirals of a received Medicaid record (table RAW_MEDICAID_DOC_ARVS): the columns the Medicaid document
 * screen reads and the document copy copies (MedicaidDocumentDAO).
 * <p>
 * Read in JPQL projections; written by an HQL insert ... select only. <b>The key (MEDICAID_DOC_ID, SKEY) is
 * assumed</b> (a copied document keeps the SKEY of the original).
 */
@Entity
@Getter
@Table(name = "RAW_MEDICAID_DOC_ARVS")
@IdClass(RawMedicaidDocArvs.Key.class)
public class RawMedicaidDocArvs {

	/** The assumed key. */
	public record Key(Integer medicaidDocId, Long skey) implements Serializable {
	}

	@Id
	@Column(name = "SKEY")
	private Long skey;

	@Id
	@Column(name = "MEDICAID_DOC_ID")
	private Integer medicaidDocId;

	@Column(name = "CIN")
	private String cin;

	@Column(name = "PRESCRIBED_ARVS")
	private String prescribedArvs;

	@Column(name = "ARV_MOS")
	private String arvMos;

	@Column(name = "ARV_YOS")
	private String arvYos;

	@Column(name = "PLAN_TYPE")
	private String planType;

	@Column(name = "MEDICAID_FILE")
	private String medicaidFile;

	@Column(name = "CREATE_UID")
	private String createUid;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;
}
