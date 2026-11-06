package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The provider / facility addresses that can be a residence (table RAW_ADDR_CANBE_RES), maintained on the LAFT
 * "manage address" screen and read by the scheduler.
 * <p>
 * Inserted as an entity (ADDRESS stored as {@code upper(trim(?))}, as before; END_DATE and LAST_UPDATED_BY are left to
 * the database); changed by a bulk JPQL update. <b>ADDRESS_ID as the key, filled by the database on insert (identity
 * or trigger, as the former insert did not set it), is assumed.</b>
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAW_ADDR_CANBE_RES")
public class RawAddrCanbeRes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ADDRESS_ID")
	private Long addressId;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "FLAG_ACTIVE")
	private String flagActive;

	@Column(name = "FILTER_TYPE")
	private String filterType;

	@Column(name = "CREATE_UID")
	private String createUid;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "END_DATE", insertable = false)
	private Timestamp endDate;

	@Column(name = "LAST_UPDATED_BY", insertable = false)
	private String lastUpdatedBy;
}
