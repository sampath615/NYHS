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
 * A provider of a received Medicaid record (table RAW_MEDICAID_DOC_PROVIDERS): the columns the document copy writes
 * and the standardization copies (MedicaidDocumentDAO).
 * <p>
 * Read in HQL insert ... select and written by an HQL insert with values only (the copy writes the SKEY of the
 * copied provider, which is null when the copied document had none). The table has no known key: <b>the key
 * (MEDICAID_DOC_ID, SKEY) is assumed</b> (it is never loaded as an entity).
 */
@Entity
@Getter
@Table(name = "RAW_MEDICAID_DOC_PROVIDERS")
@IdClass(RawMedicaidDocProviders.Key.class)
public class RawMedicaidDocProviders {

	/** The assumed key. */
	public record Key(Integer medicaidDocId, Long skey) implements Serializable {
	}

	@Column(name = "CREATE_UID")
	private String createUid;

	@Column(name = "SRV_DT")
	private Timestamp srvDt;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Id
	@Column(name = "SKEY")
	private Long skey;

	@Id
	@Column(name = "MEDICAID_DOC_ID")
	private Integer medicaidDocId;

	@Column(name = "COS_CD")
	private String cosCd;

	@Column(name = "CIN")
	private String cin;

	@Column(name = "NPI")
	private String npi;

	@Column(name = "POSTAL_CD")
	private String postalCd;

	@Column(name = "PROV_ADDR_CITY")
	private String provAddrCity;

	@Column(name = "PROV_ADDR_STR")
	private String provAddrStr;

	@Column(name = "PROV_ADDR_LINE_2")
	private String provAddrLine2;

	@Column(name = "PROV_NAME")
	private String provName;
}
