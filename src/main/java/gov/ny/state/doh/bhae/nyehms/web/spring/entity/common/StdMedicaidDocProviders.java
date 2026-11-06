package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The providers of a standardized Medicaid record (table STD_MEDICAID_DOC_PROVIDERS): the columns the Medicaid
 * document screen reads and the standardization copies from the received providers (MedicaidDocumentDAO).
 * <p>
 * Read in JPQL projections; written by an HQL insert ... select only. The table has no known key: <b>the key
 * (MEDICAID_DOC_ID, MEDICAID_NUMBER, NPI, SERVICE_DATE) is assumed</b> (it is never loaded as an entity, so a repeated
 * key does not matter).
 */
@Entity
@Getter
@Table(name = "STD_MEDICAID_DOC_PROVIDERS")
@IdClass(StdMedicaidDocProviders.Key.class)
public class StdMedicaidDocProviders {

	/** The assumed key. */
	public record Key(Integer medicaidDocId, String medicaidNumber, String npi, Timestamp serviceDate) implements Serializable {
	}

	@Id
	@Column(name = "MEDICAID_DOC_ID")
	private Integer medicaidDocId;

	@Id
	@Column(name = "MEDICAID_NUMBER")
	private String medicaidNumber;

	@Column(name = "PROV_NAME")
	private String provName;

	@Id
	@Column(name = "NPI")
	private String npi;

	@Column(name = "PROV_ADDRESS")
	private String provAddress;

	@Column(name = "PROV_CITY")
	private String provCity;

	@Column(name = "PROV_STATE")
	private String provState;

	@Column(name = "PROV_ZIP")
	private String provZip;

	@Column(name = "PROV_COUNTY")
	private String provCounty;

	@Id
	@Column(name = "SERVICE_DATE")
	private Timestamp serviceDate;

	@Column(name = "CATEGORY_OF_SERVICE_CD")
	private String categoryOfServiceCd;

	@Column(name = "CREATE_UID")
	private String createUid;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "PRIMARY_IND")
	private String primaryInd;
}
